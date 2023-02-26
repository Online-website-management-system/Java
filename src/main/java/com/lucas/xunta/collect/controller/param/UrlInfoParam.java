package com.lucas.xunta.collect.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 爬取网站信息参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfoParam {
    /**
     * 网站地址
     */
    @NotBlank(message = "网站地址不能为空")
    @ApiModelProperty(value = "网站地址", example = "https://dribbble.com/", required = true)
    private String url;
}
