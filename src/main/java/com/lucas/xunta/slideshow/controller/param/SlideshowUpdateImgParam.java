package com.lucas.xunta.slideshow.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 修改幻灯片参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowUpdateImgParam {
    /**
     * 图片ID
     */
    @NotNull(message = "图片ID不能为空")
    @ApiModelProperty(value = "图片ID", example = "", required = true)
    private Integer id;

    /**
     * 图片
     */
    @NotBlank(message = "图片不能为空")
    @ApiModelProperty(value = "图片", example = "", required = true)
    private String img;
}
