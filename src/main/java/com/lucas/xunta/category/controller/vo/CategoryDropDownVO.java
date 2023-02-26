package com.lucas.xunta.category.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 下拉选择分类VO
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryDropDownVO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名name
     */
    private String text;
}