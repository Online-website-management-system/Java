package com.lucas.xunta.category.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 增加/修改分类参数
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveParam {

    @ApiModelProperty(value = "ID", example = "")
    private Long id;

    @NotNull(message = "分类父ID不能为空")
    @ApiModelProperty(value = "分类父ID", example = "", required = true)
    private Long categoryId;

    @NotBlank(message = "分类名称不能为空")
    @ApiModelProperty(value = "分类名称", example = "", required = true)
    private String name;

    @ApiModelProperty(value = "分类类别", example = "")
    private String rank;
}
