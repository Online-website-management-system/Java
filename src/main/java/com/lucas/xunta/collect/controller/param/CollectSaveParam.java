package com.lucas.xunta.collect.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 保存收藏参数实体
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectSaveParam {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 收藏地址
     */
    @NotBlank(message = "收藏地址不能为空")
    @ApiModelProperty(value = "收藏地址", example = "https://dribbble.com/", required = true)
    private String url;

    /**
     * 收藏标题
     */
    @ApiModelProperty(value = "收藏标题", example = "Dribbble")
    private String title;

    /**
     * 收藏logo
     */
    @ApiModelProperty(value = "收藏logo", example = "http://chuangzaoshi.com/assets/images/D/dribbble.png")
    private String logo;

    /**
     * 收藏介绍
     */
    @ApiModelProperty(value = "收藏介绍", example = "全球UI设计师作品秀社区")
    private String introduce;

    /**
     * 是否首页
     */
    @NotNull(message = "是否首页不能为空")
    @ApiModelProperty(value = "是否首页", example = "1", required = true)
    private Integer home;

}
