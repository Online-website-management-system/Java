package com.lucas.xunta.user.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @description: 修改用户手机参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneUpdateParam {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号码不能为空")
    @Size(min = 11, max = 11, message = "手机号码长度不正确")
    @Pattern(regexp = "^0?(13|14|15|16|17|18|19)[0-9]{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", example = "", required = true)
    private String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 6, message = "验证码长度不对")
    @ApiModelProperty(value = "验证码", example = "", required = true)
    private String phoneCaptcha;
}
