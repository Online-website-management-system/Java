package com.lucas.xunta.user.controller;

import com.lucas.xunta.user.controller.param.*;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 用户控制器
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/10/14 18:31
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "用户Controller", tags = "用户Controller")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "用户登录",
            notes = "用户登录"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result login(@RequestBody @Valid UserLoginParam param) {
        log.info("用户登录接口参数 User:" + param);
        return userService.login(param);
    }

    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "用户注册",
            notes = "用户注册"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result register(@RequestBody @Valid UserRegistryParam param) {
        log.info("用户注册接口参数 User:" + param);
        return userService.register(param);
    }

    @PostMapping(value = "/phone_login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "短信登录",
            notes = "短信登录"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result phoneLogin(@RequestBody @Valid UserPhoneLoginParam param) {
        log.info("短信登录接口参数 UserPhoneLoginParam:" + param);
        return userService.phoneLogin(param);
    }

    @GetMapping(value = "/{type}/qq_login_url", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "获取QQ登录地址",
            notes = "获取QQ登录地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getQqLoginUrl(@PathVariable String type) {
        log.info("获取QQ登录地址接口参数type：{}", type);
        return userService.getQqLoginUrl(type);
    }

    @PostMapping(value = "/qq_login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "QQ登录",
            notes = "QQ登录"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result qqLogin(@RequestBody UserQqLoginParam param) {
        log.info("QQ登录接口参数UserQqLoginParam：{}", param);
        return userService.qqLogin(param);
    }

    @PostMapping(value = "/qq_binding", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "QQ绑定",
            notes = "QQ绑定"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result qqBinding(@RequestBody UserQqLoginParam param) {
        log.info("QQ绑定接口参数UserQqLoginParam：{}", param);
        return userService.qqBinding(param);
    }

    @DeleteMapping(value = "/qq_binding", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "解除QQ绑定",
            notes = "解除QQ绑定"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result relieveQqBinding() {
        log.info("解除QQ绑定接口参数");
        return userService.relieveQqBinding();
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "通过Token获取用户信息",
            notes = "通过Token获取用户信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getUserInfo() {
        log.info("通过Token获取用户信息接口参数");
        return userService.getUserInfo();
    }

    @PutMapping(value = "binding_phone", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "用户绑定短信",
            notes = "用户绑定短信"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateUserPhone(@RequestBody @Valid UserPhoneUpdateParam param) {
        log.info("用户绑定短信参数 UserPhoneUpdateParam:{}", param);
        return userService.updateUserPhone(param);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改用户信息",
            notes = "修改用户信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateUserInfo(@RequestBody @Valid UserInfoUpdateParam param, HttpServletRequest request, HttpServletResponse response) {
        log.info("修改用户信息参数 UserUpdateParam:{}", param);
        return userService.updateUserInfo(param);
    }

    @PutMapping(value = "/domain_role/{domainRole}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "设置用户域权限",
            notes = "设置用户域权限"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateDomainRole(@PathVariable Integer domainRole) {
        log.info("设置用户域权限参数 domainRole:{}", domainRole);
        return userService.updateDomainRole(domainRole);
    }

    @GetMapping(value = "/title", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "获取用户个人主页标题",
            notes = "获取用户个人主页标题"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getUserTitle(@NotBlank(message = "用户域不能为空") String domain) {
        log.info("获取用户个人主页标题接口参数domain：{}", domain);
        return userService.getUserTitle(domain);
    }

    @DeleteMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "注销用户",
            notes = "注销用户"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result deleteUser(@NotBlank(message = "密码不能为空") String password) {
        log.info("注销用户接口参数password：{}", password);
        return userService.deleteUser(password);
    }
}