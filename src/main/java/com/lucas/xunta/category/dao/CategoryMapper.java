package com.lucas.xunta.category.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucas.xunta.category.controller.param.CategorySaveParam;
import com.lucas.xunta.category.controller.param.CategorySaveSortParam;
import com.lucas.xunta.category.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 类型 Mapper 接口
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 增加分类
     *
     * @param param 分类参数
     * @return 受影响的行
     */
    Integer insertCategory(@Param("param") CategorySaveParam param);

    /**
     * 修改分类
     *
     * @param param 分类参数
     * @return 受影响的行
     */
    Integer updateCategory(@Param("param") CategorySaveParam param);

    /**
     * 根据分类categoryId查询分类
     *
     * @param categoryId 父类id
     * @return 分类id集合
     */
    List<Long> listById(@Param("categoryId") Long categoryId);

    /**
     * 根据categoryId查询子级分类（拼接上级name）
     *
     * @param categoryId 父类id
     * @param parentName 上级父类名称拼接
     * @return 分类集合
     */
    List<Category> listByIdAndParentName(@Param("categoryId") Long categoryId, @Param("parentName") String parentName);

    /**
     * 排序
     *
     * @param list 排序集合
     * @return 受影响的行
     */
    Integer updateCategorySort(@Param("list") List<CategorySaveSortParam> list);
}
