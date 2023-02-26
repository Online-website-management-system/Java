package com.lucas.xunta.user.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @description: 用户手机登录参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneLoginParam {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", example = "", required = true)
    private String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", example = "", required = true)
    private String phoneCaptcha;
}
