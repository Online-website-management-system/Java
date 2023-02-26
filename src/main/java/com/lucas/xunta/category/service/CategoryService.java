package com.lucas.xunta.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.category.controller.param.CategorySaveParam;
import com.lucas.xunta.category.controller.param.CategorySaveSortParam;
import com.lucas.xunta.category.entity.Category;
import com.lucas.xunta.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 类型服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface CategoryService extends IService<Category> {
    /**
     * 根据域名查看用户所有分类
     *
     * @param domain 域
     * @return 用户所有分类
     */
    Result getCategoryParentListForDomain(String domain);

    /**
     * 根据分类id和域查看子级分类和收藏网址
     *
     * @param categoryId 分类id
     * @param domain     域
     * @return 子级分类和收藏网址
     */
    Result getCategorySonListAndCollectList(Long categoryId, String domain);

    /**
     * 根据关键词查看用户分类和收藏网址
     *
     * @param keyword 关键词
     * @param domain  域
     * @return 分类和收藏网址
     */
    Result getCategoryAndCollect(String keyword, String domain);

    /**
     * 查看用户所有一级分类
     *
     * @return 用户所有一级分类
     */
    Result getParentList();

    /**
     * 根据分类id查看子级分类和收藏网址
     *
     * @param categoryId 分类id
     * @return 子级分类和收藏网址
     */
    Result getCategorySonListAndCollectList(Long categoryId);

    /**
     * 查看用户所有分类并定位当前分类索引
     *
     * @param categoryId 当前分类
     * @return 用户所有分类并定位当前分类索引
     */
    Result getCategoryDropDownList(Long categoryId);

    /**
     * 查看用户所有分类
     *
     * @return 用户所有分类
     */
    Result getCategoryTreeList();

    /**
     * 增加分类
     *
     * @param param 增加分类参数
     * @return 增加结果
     */
    Result insertCategory(CategorySaveParam param);

    /**
     * 修改分类
     *
     * @param param 增加分类参数
     * @return 修改结果
     */
    Result updateCategory(CategorySaveParam param);

    /**
     * 删除分类
     *
     * @param categoryId    分类id
     * @param deCollectFlag 是否删除其下收藏
     * @return 删除结果
     */
    Result deleteCategory(Long categoryId, Boolean deCollectFlag);

    /**
     * 排序用户分类
     *
     * @param list 用户分类列表（两个）
     * @return 排序结果
     */
    Result updateCategorySort(List<CategorySaveSortParam> list);
}
