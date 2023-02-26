package com.lucas.xunta.captcha.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lucas.xunta.captcha.dto.SendSmsDTO;
import com.lucas.xunta.captcha.service.CaptchaService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.captcha.constant.CaptchaConstant;
import com.lucas.xunta.common.constant.ErrorConstant;
import com.lucas.xunta.common.utils.EntityUtils;
import com.lucas.xunta.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/8/11/011 12:08
 */
@Slf4j
@Service
/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CaptchaConstant captchaConstant;
    @Value(value = "${xunta.captcha.sign-name}")
    private String signName;
    @Value(value = "${xunta.captcha.template-id}")
    private String templateId;

    /**
     * 发送验证码
     *
     * @param phone   手机号码
     * @param request 请求信息
     * @return 验证码发送结果
     */
    @Override
    public Result sendSms(String phone, HttpServletRequest request) {
        log.info("根据手机号码生成短信验证码请求URL {}", captchaConstant.getSendUrl());
        ResponseEntity<String> responseEntity = null;
        try {
            // 封装发送短信请求参数
            SendSmsDTO dto = SendSmsDTO.builder()
                    .moduleName(captchaConstant.getModuleName())
                    .sourceIp(HttpUtils.getRealIP(request))
                    .phone(phone)
                    .signName(signName)
                    .templateId(templateId)
                    .build();
            // 放入请求实体
            HttpEntity httpEntity = EntityUtils.prepareHttpEntity(dto);
            responseEntity = restTemplate.exchange(captchaConstant.getSendUrl(), HttpMethod.POST, httpEntity, String.class);
            log.info("根据手机号码生成短信验证码接收参数 {}", responseEntity);
        } catch (HttpServerErrorException e) {
            log.info("根据手机号码生成短信验证码请求失败： {}", e.getResponseBodyAsString());
            return ResultGenerator.genFailResultMsg(e.getResponseBodyAsString());
        }
        Result vo = JSONObject.parseObject(responseEntity.getBody(), Result.class);
        // 200为成功
        if (vo.getCode() == 200) {
            return ResultGenerator.genSuccessResult(vo.getData());
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.SEND_SMS_ERROR);
    }

    /**
     * 验证验证码
     *
     * @param phone   时间号码
     * @param captcha 验证码
     * @return 验证验证码结果
     */
    @Override
    public Boolean verifySms(String phone, String captcha) {
        // 拼接验证验证码接口地址
        String url = MessageFormat.format(
                captchaConstant.getVerifyUrl(),
                phone,
                captcha
        );
        log.info("验证验证码接口请求URL {}", url);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.info("验证验证码接口接收参数 {}", responseEntity);
        } catch (HttpServerErrorException e) {
            log.info("验证验证码接口请求失败： {}", e.getResponseBodyAsString());
            return false;
        }
        Result vo = JSONObject.parseObject(responseEntity.getBody(), Result.class);
        // 200为成功
        if (vo.getCode() == 200) {
            return true;
        }
        return false;
    }
}
