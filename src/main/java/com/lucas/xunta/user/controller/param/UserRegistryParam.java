package com.lucas.xunta.user.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @description: 用户注册参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistryParam {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 6, message = "用户名最低 6 位数")
    @ApiModelProperty(value = "用户名", example = "123456", required = true)
    private String username;

    /**
     * 用户密码md5
     */
    @NotBlank(message = "用户密码不能为空")
    @Size(min = 6, message = "密码最低 6 位数")
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;

    /**
     * 用户重复密码md5
     */
    @NotBlank(message = "重复密码不能为空")
    @Size(min = 6, message = "重复密码最低 6 位数")
    @ApiModelProperty(value = "重复密码", example = "123456", required = true)
    private String repetition;
}
