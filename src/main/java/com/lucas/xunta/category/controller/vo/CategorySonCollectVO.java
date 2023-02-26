package com.lucas.xunta.category.controller.vo;

import com.lucas.xunta.collect.entity.Collect;
import com.lucas.xunta.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 子类类型和收藏网址VO
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategorySonCollectVO {
    /**
     * 子级分类列表
     */
    private List<Category> categoryList;
    /**
     * 收藏列表
     */
    private List<Collect> collectList;
}