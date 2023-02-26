package com.lucas.xunta.category.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @description: 修改分类排序参数
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveSortParam {


    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    @ApiModelProperty(value = "分类ID", example = "", required = true)
    private Long id;

    /**
     * 分类排序
     */
    @NotNull(message = "分类排序不能为空")
    @ApiModelProperty(value = "分类排序", example = "", required = true)
    private Long sort;

}
