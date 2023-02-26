package com.lucas.xunta.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.captcha.service.CaptchaService;
import com.lucas.xunta.user.controller.param.*;
import com.lucas.xunta.common.utils.EntityUtils;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.category.dao.CategoryMapper;
import com.lucas.xunta.category.entity.Category;
import com.lucas.xunta.category.service.CategoryService;
import com.lucas.xunta.common.constant.ErrorConstant;
import com.lucas.xunta.common.security.utils.JwtTokenUtil;
import com.lucas.xunta.user.constant.UserConstant;
import com.lucas.xunta.user.controller.vo.QqInfoVO;
import com.lucas.xunta.user.controller.vo.QqLoginVO;
import com.lucas.xunta.user.dao.UserMapper;
import com.lucas.xunta.user.entity.User;
import com.lucas.xunta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserConstant constant;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CaptchaService captchaService;


    /**
     * 用户登录
     *
     * @param param 参数实体
     * @return 登录信息
     */
    @Override
    public Result login(UserLoginParam param) {
        // 用户登录
        User user = userService.getOne(
                new LambdaQueryWrapper<User>()
                        .select(User::getId, User::getRoles, User::getTitle, User::getDomain)
                        .eq(User::getUsername, param.getUsername())
                        .eq(User::getPassword, DigestUtils.md5DigestAsHex(param.getPassword().getBytes()))
        );
        // 如果存在用户信息则返回
        if (user != null) {
            return ResultGenerator.genSuccessResult(EntityUtils.loginInfoVO(jwtTokenUtil, user));
        }
        // 不存在用户信息登录失败
        return ResultGenerator.genFailResultMsg(ErrorConstant.LOGIN_ERROR);
    }

    @Transactional
    @Override
    public Result phoneLogin(UserPhoneLoginParam param) {
        // 判断验证码
        if (!captchaService.verifySms(param.getPhone(), param.getPhoneCaptcha())) {
            // 验证码错误
            return ResultGenerator.genFailResultMsg(ErrorConstant.CAPTCHA_ERROR);
        }
        // 查询用户
        User user = userService.getOne(
                new LambdaQueryWrapper<User>()
                        .select(User::getId, User::getRoles, User::getTitle, User::getDomain)
                        .eq(User::getPhone, param.getPhone())
        );
        //  存在用户则登录
        if (user != null) {
            return ResultGenerator.genSuccessResult(EntityUtils.loginInfoVO(jwtTokenUtil, user));
        }
        // 不存在用户则注册
        user = User.builder().username(param.getPhone()).phone(param.getPhone()).build();
        // 如果注册成功
        if (userMapper.insert(user) > 0) {
            // 循化设置用户名和用户域（直到用户名和域都不存在）
            for (int i = 0; ; i++) {
                // 用户名和域为随机数
                String usernameAndDomain = RandomStringUtils.randomAlphanumeric(6);
                User casualUser = userService.getOne(
                        new LambdaQueryWrapper<User>()
                                .select(User::getId)
                                .eq(User::getDomain, "/" + usernameAndDomain)
                                .or()
                                .eq(User::getUsername, usernameAndDomain)
                );
                if (casualUser == null // 说明用户名和域都不存在
                        &&
                        userService.update(
                                new LambdaUpdateWrapper<User>()
                                        .eq(User::getId, user.getId())
                                        .set(User::getUsername, usernameAndDomain)
                                        .set(User::getDomain, "/" + usernameAndDomain)
                        )
                ) {
                    // 注册成功查询返回用户信息
                    user = userService.getOne(
                            new LambdaQueryWrapper<User>()
                                    .select(User::getId, User::getRoles, User::getTitle, User::getDomain)
                                    .eq(User::getId, user.getId())
                    );
                    // 返回处理后的用户信息
                    return ResultGenerator.genSuccessResult(EntityUtils.loginInfoVO(jwtTokenUtil, user));
                }
            }
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.LOGIN_PHONE_ERROR);
    }

    /**
     * 获取qq登录网址
     *
     * @param type 类型有绑定和登录
     * @return qq登录网址
     */
    @Override
    public Result getQqLoginUrl(String type) {
        // QQ登录API
        String url = "";
        if ("login".equals(type)) {
            url = MessageFormat.format(
                    constant.getQqLoginUrl(),
                    constant.getAppid(),
                    constant.getAppkey(),
                    constant.getType(),
                    constant.getLoginRedirectUrl()
            );
        } else {
            url = MessageFormat.format(
                    constant.getQqLoginUrl(),
                    constant.getAppid(),
                    constant.getAppkey(),
                    constant.getType(),
                    constant.getBindingRedirectUrl()
            );
        }
        log.info("QQ登录接口 请求地址为 {}", url);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.info("接收参数 {}", responseEntity);
        } catch (HttpServerErrorException e) {
            log.info("QQ登录请求失败： {}", e.getResponseBodyAsString());
            return ResultGenerator.genFailResultMsg(e.getResponseBodyAsString());
        }
        QqLoginVO vo = JSONObject.parseObject(responseEntity.getBody(), QqLoginVO.class);
        // 0为请求成功，返回信息
        if ("0".equals(vo.getCode())) {
            return ResultGenerator.genSuccessResult(vo);
        }
        // 请求失败，返回失败信息
        return ResultGenerator.genFailResultMsg(vo.getMsg());
    }

    /**
     * qq登录
     *
     * @param param qq登录信息
     * @return 登录结果
     */
    @Transactional
    @Override
    public Result qqLogin(UserQqLoginParam param) {
        // 获取qq登录信息接口地址
        String url = MessageFormat.format(
                constant.getQqCallbackUrl(),
                constant.getAppid(),
                constant.getAppkey(),
                constant.getType(),
                param.getCode()
        );
        log.info("通过QQCode获取用户信息接口 请求地址为 {}", url);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.info("接收参数 {}", responseEntity);
        } catch (HttpServerErrorException e) {
            log.info("通过QQCode获取用户信息请求失败： {}", e.getResponseBodyAsString());
            return ResultGenerator.genFailResultMsg(e.getResponseBodyAsString());
        }
        // 将获取到的信息放入vo
        QqInfoVO vo = JSONObject.parseObject(responseEntity.getBody(), QqInfoVO.class);
        // 0代表获取成功
        if ("0".equals(vo.getCode())) {
            // 登录
            User user = userService.getOne(
                    new LambdaQueryWrapper<User>()
                            .select(User::getId, User::getRoles, User::getTitle, User::getDomain)
                            .eq(User::getQqUid, vo.getSocialUid())
            );
            // 如果存在用户信息
            if (user != null) {
                // 返回处理后的用户信息
                return ResultGenerator.genSuccessResult(EntityUtils.loginInfoVO(jwtTokenUtil, user));
            }
            // 如果不存在qq信息则注册
            for (int i = 0; ; i++) { // 循化设置用户名和用户域（直到用户名和域都不存在）
                // 随机数为用户名和域
                String username = RandomStringUtils.randomAlphanumeric(6);
                User casualUser = userService.getOne(
                        new LambdaQueryWrapper<User>()
                                .select(User::getId)
                                .eq(User::getUsername, username)
                                .or()
                                .eq(User::getDomain, username)
                );
                user = User.builder().username(username).qqUid(vo.getSocialUid()).domain("/" + username).build();
                // 说明用户名不存在 增加用户信息
                if (casualUser == null && userMapper.insert(user) > 0) {
                    // 注册成功查询返回用户信息
                    user = userService.getOne(
                            new LambdaQueryWrapper<User>()
                                    .select(User::getId, User::getRoles, User::getTitle, User::getDomain)
                                    .eq(User::getQqUid, vo.getSocialUid())
                    );
                    return ResultGenerator.genSuccessResult(EntityUtils.loginInfoVO(jwtTokenUtil, user));
                }
            }
        }
        return ResultGenerator.genSuccessResult(false);
    }

    /**
     * qq绑定
     *
     * @param param qq绑定信息
     * @return 绑定结果
     */
    @Override
    public Result qqBinding(UserQqLoginParam param) {
        // 获取qq登录信息接口地址
        String url = MessageFormat.format(
                constant.getQqCallbackUrl(),
                constant.getAppid(),
                constant.getAppkey(),
                constant.getType(),
                param.getCode()
        );
        log.info("通过QQCode获取用户信息接口 请求地址为 {}", url);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.info("接收参数 {}", responseEntity);
        } catch (HttpServerErrorException e) {
            log.info("通过QQCode获取用户信息请求失败： {}", e.getResponseBodyAsString());
            return ResultGenerator.genFailResultMsg(e.getResponseBodyAsString());
        }
        // 将获取到的信息放入vo
        QqInfoVO vo = JSONObject.parseObject(responseEntity.getBody(), QqInfoVO.class);
        // 0代表获取成功
        if ("0".equals(vo.getCode())) {
            // 登录
            User user = userService.getOne(
                    new LambdaQueryWrapper<User>()
                            .select(User::getId)
                            .eq(User::getQqUid, vo.getSocialUid())
            );
            // 如果存在用户信息
            if (user != null) {
                // 返回处理后的用户信息
                return ResultGenerator.genFailResultMsg(ErrorConstant.QQ_REPETITION_BINDING_ERROR);
            }
            // 获取本人ID
            // 获取到当前登录用户ID
            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            // 修改
            if (userService.update(new LambdaUpdateWrapper<User>().set(User::getQqUid, vo.getSocialUid()).eq(User::getId, userId))) {
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.QQ_BINDING_ERROR);
    }

    /**
     * 解除绑定
     *
     * @return 解除绑定结果
     */
    @Transactional
    @Override
    public Result relieveQqBinding() {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 修改
        if (userService.update(new LambdaUpdateWrapper<User>().set(User::getQqUid, "").eq(User::getId, userId))) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.QQ_RELIEVE_BINDING_ERROR);
    }

    /**
     * 用户注册
     *
     * @param param 参数实体
     * @return 注册信息
     */
    @Transactional
    @Override
    public Result register(UserRegistryParam param) {
        // 两次密码不一致
        if (!param.getPassword().equals(param.getRepetition())) {
            return ResultGenerator.genFailResultMsg(ErrorConstant.REGISTER_PASSWORD_ERROR);
        }
        // 查询用户名是否被占用
        User newUser = userService.getOne(
                new LambdaQueryWrapper<User>()
                        .select(User::getId)
                        .eq(User::getUsername, param.getUsername())
        );
        // 如果没有被占用
        if (newUser == null) {
            // 注册
            User user = User.builder()
                    .username(param.getUsername())
                    .password(DigestUtils.md5DigestAsHex(param.getPassword().getBytes()))
                    .build();
            if (userService.save(user)) { // 注册成功
                // 设置用户域
                for (int i = 0; ; i++) {
                    // 随机数设置为域
                    StringBuffer domain = new StringBuffer("/" + RandomStringUtils.randomAlphanumeric(6));
                    User casualUser = userService.getOne(new LambdaQueryWrapper<User>().select(User::getId).eq(User::getDomain, domain.toString()));
                    if (casualUser == null // 说明域不存在
                            &&
                            userService.update(
                                    new LambdaUpdateWrapper<User>()
                                            .eq(User::getUsername, param.getUsername())
                                            .set(User::getDomain, domain.toString())) // 修改为这个域
                    ) {
                        return ResultGenerator.genSuccessResult();
                    }
                }
            }
        }
        // 注册失败
        return ResultGenerator.genFailResultMsg(ErrorConstant.REGISTER_USERNAME_ERROR);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Override
    public Result getUserInfo() {
        // 获取用户信息
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 查到用户信息
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        // 去掉域里面的 /
        if (user.getDomain() != null && !user.getDomain().equals("")) {
            user.setDomain(user.getDomain().split("/")[1]);
        }
        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * 根据域名获取用户标题
     *
     * @param domain 域
     * @return 用户标题
     */
    @Override
    public Result getUserTitle(String domain) {
        User user = userService.getOne(
                new LambdaQueryWrapper<User>()
                        .select(User::getTitle)
                        .eq(User::getDomain, domain)
        );
        // 如果存在用户
        if (user != null) {
            return ResultGenerator.genSuccessResult(user.getTitle());
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除用户
     *
     * @param password 用户密码
     * @return 删除结果
     */
    @Transactional
    @Override
    public Result deleteUser(String password) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 先验证密码
        User user = userService.getOne(new LambdaQueryWrapper<User>().select(User::getPassword).eq(User::getId, userId));
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_PASSWORD_ERROR);
        }
        // 找到所有一级分类ID
        List<Category> categoryList = categoryService.list(
                new LambdaQueryWrapper<Category>()
                        .select(Category::getId)
                        .eq(Category::getCategoryId, userId)
                        .eq(Category::getRank, "parent")
        );
        if (CollectionUtils.isNotEmpty(categoryList)) {
            // 提取一级分类ID
            List<Long> categoryIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
            // 找出所有分类ID
            List<Long> sonCategoryIdList = EntityUtils.getSonCategoryIdList(categoryMapper, categoryIds, new ArrayList<>());
            // 所有分类ID
            categoryIds.addAll(sonCategoryIdList);
            // 删除所有分类
            categoryService.removeByIds(categoryIds);
        }
        // 删除用户，收藏，账号
        if (userMapper.deleteUser(userId) > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 修改用户域权限
     *
     * @param domainRole 权限
     * @return 修改结果
     */
    @Override
    public Result updateDomainRole(Integer domainRole) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (userService.update(new LambdaUpdateWrapper<User>().set(User::getDomainRole, domainRole).eq(User::getId, userId))) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 修改用户信息
     *
     * @param param 用户信息实体
     * @return 修改结果
     */
    @Transactional
    @Override
    public Result updateUserInfo(UserInfoUpdateParam param) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 如果是用户名，检查是否存在
        if (param.getField().equals("username")) {
            if (param.getValue().length() < 6) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_USERNAME_PASSWORD_SIZE_ERROR);
            }
            User casualUser = userService.getOne(
                    new LambdaQueryWrapper<User>()
                            .select(User::getId)
                            .eq(User::getUsername, param.getValue())
            );
            if (casualUser != null) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_USERNAME_ERROR);
            }
        } else if (param.getField().equals("password")) {// 如果是密码，先对比原密码，再MD5加密
            if (param.getValue().length() < 6) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_USERNAME_PASSWORD_SIZE_ERROR);
            }
            User casualUser = userService.getOne(new LambdaQueryWrapper<User>().select(User::getPassword).eq(User::getId, userId));
            // 对比原密码，传入密码需要md5加密
            if (casualUser != null && !casualUser.getPassword().equals(DigestUtils.md5DigestAsHex(param.getOldValue().getBytes()))) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_PASSWORD_ERROR);
            }
            // 新密码md5加密
            param.setValue(DigestUtils.md5DigestAsHex(param.getValue().getBytes()));
        } else if (param.getField().equals("domain")) {// 如果是域，检查是否存在
            param.setValue("/" + param.getValue());
            User casualUser = userService.getOne(
                    new LambdaQueryWrapper<User>()
                            .select(User::getId)
                            .eq(User::getDomain, param.getValue())
            );
            if (casualUser != null) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_USER_REPETITION_DOMAIN_ERROR);
            }
        } else if (param.getField().equals("title")) {
            // 修改标题
        } else {
            return ResultGenerator.genFailResultMsg(ErrorConstant.ROLE_ERROR);
        }
        // 修改
        if (userService.update(new UpdateWrapper<User>().set(param.getField(), param.getValue()).eq("id", userId))) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 修改用户手机
     *
     * @param param 手机信息实体
     * @return 修改结果
     */
    @Transactional
    @Override
    public Result updateUserPhone(UserPhoneUpdateParam param) {
        // 判断验证码
        if (!captchaService.verifySms(param.getPhone(), param.getPhoneCaptcha())) {
            // 验证码错误
            return ResultGenerator.genFailResultMsg(ErrorConstant.CAPTCHA_ERROR);
        }
        // 如果该手机号被绑定，提示
        User user = userService.getOne(new LambdaQueryWrapper<User>().select(User::getId).eq(User::getPhone, param.getPhone()));
        if (user != null) {
            return ResultGenerator.genFailResultMsg(ErrorConstant.PHONE_RELIEVE_BINDING_ERROR);
        }
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (userService.update(new LambdaUpdateWrapper<User>().set(User::getPhone, param.getPhone()).eq(User::getId, userId))) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genSuccessResult(ErrorConstant.OPERATION_ERROR);
    }

}
