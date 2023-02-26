package com.lucas.xunta.user.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 修改用户参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateParam {
    /**
     * 修改字段名
     */
    @NotBlank(message = "修改字段名不能为空")
    @ApiModelProperty(value = "修改字段名", example = "", required = true)
    private String field;

    /**
     * 修改原字段值
     */
    @ApiModelProperty(value = "修改原字段值", example = "")
    private String oldValue;

    /**
     * 修改新字段值
     */
    @NotBlank(message = "修改新字段值不能为空")
    @ApiModelProperty(value = "修改新字段值", example = "", required = true)
    private String value;
}
