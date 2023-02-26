package com.lucas.xunta.captcha.dto;

import lombok.*;

/**
 * <p>
 * 发送验证码DTO
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
public class SendSmsDTO {
    /**
     * 模块名
     */
    private String moduleName;


    /**
     * 来源IP
     */
    private String sourceIp;

    /**
     * 用户手机号码
     */
    private String phone;
    /**
     * 短信签名内容
     */
    private String signName;
    /**
     * 模板ID
     */
    private String templateId;


}
