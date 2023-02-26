package com.lucas.xunta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.user.controller.param.*;
import com.lucas.xunta.user.entity.User;
import com.lucas.xunta.common.result.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface UserService extends IService<User> {
    Result phoneLogin(UserPhoneLoginParam param);

    /**
     * 用户登录
     *
     * @param param 参数实体
     * @return 登录信息
     */
    Result login(UserLoginParam param);

    /**
     * 获取qq登录网址
     *
     * @param type 类型有绑定和登录
     * @return qq登录网址
     */
    Result getQqLoginUrl(String type);

    /**
     * 用户注册
     *
     * @param param 参数实体
     * @return 注册信息
     */
    Result register(UserRegistryParam param);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    Result getUserInfo();

    /**
     * 根据域名获取用户标题
     *
     * @param domain 域
     * @return 用户标题
     */
    Result getUserTitle(String domain);

    /**
     * 删除用户
     *
     * @param password 用户密码
     * @return 删除结果
     */
    Result deleteUser(String password);

    /**
     * qq登录
     *
     * @param param qq登录信息
     * @return 登录结果
     */
    Result qqLogin(UserQqLoginParam param);

    /**
     * qq绑定
     *
     * @param param qq绑定信息
     * @return 绑定结果
     */
    Result qqBinding(UserQqLoginParam param);

    /**
     * 解除绑定
     *
     * @return 解除绑定结果
     */
    Result relieveQqBinding();

    /**
     * 修改用户域权限
     *
     * @param domainRole 权限
     * @return 修改结果
     */
    Result updateDomainRole(Integer domainRole);

    /**
     * 修改用户信息
     *
     * @param param 用户信息实体
     * @return 修改结果
     */
    Result updateUserInfo(UserInfoUpdateParam param);

    /**
     * 修改用户手机
     *
     * @param param 手机信息实体
     * @return 修改结果
     */
    Result updateUserPhone(UserPhoneUpdateParam param);


}
