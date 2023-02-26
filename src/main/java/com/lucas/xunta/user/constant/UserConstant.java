package com.lucas.xunta.user.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 用户配置常量类
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:35
 **/
@Configuration
@Data
public class UserConstant {
    /**
     * 获取qq登录url
     */
    @Value("${xunta.login.qq.login-url}")
    private String qqLoginUrl;
    /**
     * qq登录url回调code验证url
     */
    @Value("${xunta.login.qq.callback-url}")
    private String qqCallbackUrl;
    /**
     * qq登录接口
     */
    @Value("${xunta.login.qq.signature.appid}")
    private String appid;
    /**
     * qq登录接口
     */
    @Value("${xunta.login.qq.signature.appkey}")
    private String appkey;
    /**
     * qq登录接口
     */
    @Value("${xunta.login.qq.signature.type}")
    private String type;
    /**
     * 本站qq登录回调url
     */
    @Value("${xunta.login.qq.signature.login-redirect-url}")
    private String loginRedirectUrl;
    /**
     * 本站qq绑定录回调url
     */
    @Value("${xunta.login.qq.signature.binding-redirect-url}")
    private String bindingRedirectUrl;


}
