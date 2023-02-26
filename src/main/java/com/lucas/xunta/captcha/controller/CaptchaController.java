package com.lucas.xunta.captcha.controller;

import com.lucas.xunta.captcha.controller.param.SendSmsParam;
import com.lucas.xunta.captcha.service.CaptchaService;
import com.lucas.xunta.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
@RestController
@Slf4j
@CrossOrigin
@Api(value = "验证码Controller", tags = "验证码Controller")
@RequestMapping("/captcha")
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @PostMapping(value = "/send_sms", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "根据手机号码生成短信验证码",
            notes = "根据手机号码生成短信验证码"
    )
    public Result sendSms(@RequestBody @Valid SendSmsParam param, HttpServletRequest request) {
        log.info("根据手机号码生成短信验证码接口入参 phone：{}", param.getPhone());
        return captchaService.sendSms(param.getPhone(), request);
    }
}
