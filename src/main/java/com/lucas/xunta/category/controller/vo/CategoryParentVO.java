package com.lucas.xunta.category.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 分类父类VO
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryParentVO {
    /**
     * 分类ID
     */
    private Long id;
    /**
     * 分类名称
     */
    private String text;
    /**
     * 分类排序
     */
    private Long sort;
}