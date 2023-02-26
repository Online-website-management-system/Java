package com.lucas.xunta.captcha.service;

import com.lucas.xunta.common.result.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/8/11/011 12:08
 */

/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
public interface CaptchaService {
    /**
     * 发送验证码
     *
     * @param phone   手机号码
     * @param request 请求信息
     * @return 验证码发送结果
     */
    Result sendSms(String phone, HttpServletRequest request);

    /**
     * 验证验证码
     *
     * @param phone   时间号码
     * @param captcha 验证码
     * @return 验证验证码结果
     */
    Boolean verifySms(String phone, String captcha);
}
