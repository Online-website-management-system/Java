package com.lucas.xunta.collect.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 收藏排序参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectSaveSortParam {


    /**
     * 收藏ID
     */
    @NotBlank(message = "收藏ID不能为空")
    @ApiModelProperty(value = "收藏ID", example = "", required = true)
    private Long id;

    /**
     * 收藏排序
     */
    @NotBlank(message = "收藏排序不能为空")
    @ApiModelProperty(value = "收藏排序", example = "", required = true)
    private Long sort;

}
