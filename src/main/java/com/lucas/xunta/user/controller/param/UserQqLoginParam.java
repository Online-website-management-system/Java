package com.lucas.xunta.user.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户QQ参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQqLoginParam {
    /**
     * 用户名
     */
    @NotBlank(message = "CODE码不能为空")
    @ApiModelProperty(value = "CODE码", example = "", required = true)
    private String code;
}
