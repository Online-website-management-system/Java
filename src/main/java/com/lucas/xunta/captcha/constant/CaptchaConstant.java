package com.lucas.xunta.captcha.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 验证码配置常量类
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:37
 **/

@Configuration
@Data
/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
public class CaptchaConstant {
    /**
     * 模块名
     */
    @Value("${spring.application.name}")
    private String moduleName;
    /**
     * 发送短信URL
     */
    @Value("${xunta.captcha.send-url}")
    private String sendUrl;
    /**
     * 验证短信URL
     */
    @Value("${xunta.captcha.verify-url}")
    private String verifyUrl;
}
