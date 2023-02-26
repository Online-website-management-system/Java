package com.lucas.xunta.captcha.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/8/11/011 14:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2021-03-11
 */
public class PhoneCaptchaParam {
    @ApiModelProperty(value = "手机号", example = "17673849540", required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty(value = "图片验证码", example = "1234", required = true)
    @NotEmpty(message = "图片验证码不能为空")
    private String imgCode;
    @ApiModelProperty(value = "验证码token（唯一标识）", example = "1234", required = true)
    @NotEmpty(message = "验证码token不能为空")
    private String imgToken;
}
