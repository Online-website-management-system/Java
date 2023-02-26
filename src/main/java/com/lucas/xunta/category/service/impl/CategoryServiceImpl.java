package com.lucas.xunta.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.category.controller.param.CategorySaveParam;
import com.lucas.xunta.category.controller.param.CategorySaveSortParam;
import com.lucas.xunta.collect.dao.CollectMapper;
import com.lucas.xunta.collect.entity.Collect;
import com.lucas.xunta.collect.service.CollectService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.common.constant.ConfigConstant;
import com.lucas.xunta.common.constant.ErrorConstant;
import com.lucas.xunta.user.entity.User;
import com.lucas.xunta.user.service.UserService;
import com.lucas.xunta.common.utils.EntityUtils;
import com.lucas.xunta.category.controller.vo.CategoryDropDownVO;
import com.lucas.xunta.category.controller.vo.CategoryParentVO;
import com.lucas.xunta.category.controller.vo.CategorySonCollectVO;
import com.lucas.xunta.category.dao.CategoryMapper;
import com.lucas.xunta.category.entity.Category;
import com.lucas.xunta.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 类型服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private UserService userService;

    /**
     * 根据域名查看用户所有分类
     *
     * @param domain 域
     * @return 用户所有分类
     */
    @Override
    public Result getCategoryParentListForDomain(String domain) {
        // 默认为系统id
        Long userId = ConfigConstant.SYSTEM_USER_ID;
        // 如果域不是首页
        if (!"/".equals(domain)) {
            // 获取当前域的用户信息
            User user = userService.getOne(
                    new LambdaQueryWrapper<User>().select(User::getId, User::getDomainRole).eq(User::getDomain, domain)
            );
            if (user != null) {
                try {
                    // 获取用户ID和当前域的用户ID比较（如果当前用户未登录则会报错跳转catch）
                    userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
                    // 如果域不相等
                    if (userId.intValue() != user.getId().intValue()) {
                        // 返回false（前端则提示当前个人主页并不是用户的个人主页，需要退出登录）
                        return ResultGenerator.genSuccessResult(false);
                    }
                } catch (Exception e) {
                    // domainRole为0时代表用户并没有开放个人主页，返回401未登录
                    if (user.getDomainRole() == 0) {
                        return Result.builder().code(HttpStatus.UNAUTHORIZED.value()).message(ErrorConstant.ROLE_ERROR).build();
                    } else {
                        // 赋值userId方便后面获取数据
                        userId = user.getId();
                    }
                }
            }
        }
        // 通过userId获取用户的一级分类列表
        List<Category> categoryList = categoryService.list(
                new QueryWrapper<Category>()
                        .select("id", "name", "sort")
                        .eq("category_id", userId)
                        .eq("rank", "parent").orderByAsc("sort", "id")
        );
        // 判端一级分类是否为空
        if (CollectionUtils.isNotEmpty(categoryList)) {
            // 如果不为空拆解封装为前端可用的VO然后返回
            List<CategoryParentVO> categoryParentVOS = categoryList.stream().map(category -> {
                CategoryParentVO categoryParentVO = new CategoryParentVO();
                categoryParentVO.setId(category.getId());
                categoryParentVO.setSort(category.getSort());
                categoryParentVO.setText(category.getName());
                return categoryParentVO;
            }).collect(Collectors.toList());
            return ResultGenerator.genSuccessResult(categoryParentVOS);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据分类id和域查看子级分类和收藏网址
     *
     * @param categoryId 分类id
     * @param domain     域
     * @return 子级分类和收藏网址
     */
    @Override
    public Result getCategorySonListAndCollectList(Long categoryId, String domain) {
        Long userId = ConfigConstant.SYSTEM_USER_ID;
        if (!"/".equals(domain)) {
            User user = userService.getOne(
                    new LambdaQueryWrapper<User>().select(User::getId, User::getDomainRole).eq(User::getDomain, domain)
            );
            if (user != null) {
                userId = user.getId();
                if (user.getDomainRole() == 0) {
                    try {
                        userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
                    } catch (Exception e) {
                        return Result.builder().code(HttpStatus.UNAUTHORIZED.value()).message(ErrorConstant.ROLE_ERROR).build();
                    }
                }
            }
        }
        if (categoryId != null) {
            // 查到子级分类
            List<Category> categoryList = categoryService.list(
                    new QueryWrapper<Category>()
                            .select("id", "name", "sort")
                            .eq("category_id", categoryId)
                            .eq("rank", "son").orderByAsc("sort", "id")
            );
            CategorySonCollectVO categorySonCollectVO = CategorySonCollectVO.builder().categoryList(categoryList).build();
            // 查到分类内的收藏
            LambdaQueryWrapper<Collect> collectQueryWrapper = new LambdaQueryWrapper<>(); // 先实例化方便后面判断
            collectQueryWrapper.eq(Collect::getUserId, userId); // 用户id限制
            if (categoryId.intValue() == 0) { // 如果是查询首页
                collectQueryWrapper.and(wrapper -> wrapper
                        .eq(Collect::getHome, 1)
                        .or()
                        .eq(Collect::getCategoryId, categoryId)
                );
            } else {
                collectQueryWrapper.eq(Collect::getCategoryId, categoryId);
            }
            collectQueryWrapper.orderByAsc(Collect::getSort, Collect::getId); // 排序
            List<Collect> collectList = collectService.list(collectQueryWrapper); // 查询收藏
            categorySonCollectVO.setCollectList(collectList);
            if (CollectionUtils.isNotEmpty(categoryList) || CollectionUtils.isNotEmpty(collectList)) {
                return ResultGenerator.genSuccessResult(categorySonCollectVO);
            }
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据关键词查看用户分类和收藏网址
     *
     * @param keyword 关键词
     * @param domain  域
     * @return 分类和收藏网址
     */
    @Override
    public Result getCategoryAndCollect(String keyword, String domain) {
        // 默认为系统id
        Long userId = ConfigConstant.SYSTEM_USER_ID;
        // 如果域不是首页
        if (!"/".equals(domain)) {
            // 获取当前域的用户信息
            User user = userService.getOne(
                    new LambdaQueryWrapper<User>().select(User::getId, User::getDomainRole).eq(User::getDomain, domain)
            );
            if (user != null) {
                // 将当前域用户id赋值userID方便后续查询
                userId = user.getId();
                // 如果用户并没有开放权限
                if (user.getDomainRole() == 0) {
                    try {
                        // 查看当前用户Token信息内的userId（如果未登录会报错）
                        userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
                    } catch (Exception e) {
                        // 返回401提示用户登录
                        return Result.builder().code(HttpStatus.UNAUTHORIZED.value()).message(ErrorConstant.ROLE_ERROR).build();
                    }
                }
            }
        }
        // 根据关键词查到收藏
        List<Collect> collectList = collectService.list(
                new LambdaQueryWrapper<Collect>()
                        .eq(Collect::getUserId, userId) //ID
                        .and(
                                wrapper -> wrapper
                                        .like(Collect::getTitle, keyword)
                                        .or()
                                        .like(Collect::getIntroduce, keyword)// 默认查询系统收藏
                                        .or()
                                        .like(Collect::getUrl, keyword)
                        )
                        .orderByAsc(Collect::getSort, Collect::getId)
        );
        return ResultGenerator.genSuccessResult(CategorySonCollectVO.builder().collectList(collectList).build());
    }

    /**
     * 查看用户所有一级分类
     *
     * @return 用户所有一级分类
     */
    @Override
    public Result getParentList() {
        // 根据Token获取用户id然后查到一级分类信息
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<Category> categoryList = categoryService.list(
                new QueryWrapper<Category>()
                        .select("id", "name", "sort")
                        .eq("category_id", userId)
                        .eq("rank", "parent").orderByAsc("sort", "id")
        );
        return ResultGenerator.genSuccessResult(categoryList);
    }

    /**
     * 根据分类id查看子级分类和收藏网址
     *
     * @param categoryId 分类id
     * @return 子级分类和收藏网址
     */
    @Override
    public Result getCategorySonListAndCollectList(Long categoryId) {
        // 根据Token获取用户id然后根据categoryId查到二级分类和收藏信息
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (categoryId != null) {
            // 查到子级分类
            List<Category> categoryList = categoryService.list(
                    new QueryWrapper<Category>()
                            .select("id", "name", "sort")
                            .eq("category_id", categoryId)
                            .eq("rank", "son").orderByAsc("sort", "id")
            );
            CategorySonCollectVO categorySonCollectVO = CategorySonCollectVO.builder().categoryList(categoryList).build();
            // 查到分类内的收藏
            LambdaQueryWrapper<Collect> collectQueryWrapper = new LambdaQueryWrapper<>(); // 先实例化方便后面判断
            collectQueryWrapper.eq(Collect::getUserId, userId); // 用户id限制
            if (categoryId.intValue() == 0) { // 如果是查询首页
                collectQueryWrapper.and(wrapper -> wrapper
                        .eq(Collect::getHome, 1)
                        .or()
                        .eq(Collect::getCategoryId, categoryId)
                );
            } else {
                collectQueryWrapper.eq(Collect::getCategoryId, categoryId);
            }
            collectQueryWrapper.orderByAsc(Collect::getSort, Collect::getId); // 排序
            List<Collect> collectList = collectService.list(collectQueryWrapper); // 查询收藏
            categorySonCollectVO.setCollectList(collectList);
            if (CollectionUtils.isNotEmpty(categoryList) || CollectionUtils.isNotEmpty(collectList)) {
                return ResultGenerator.genSuccessResult(categorySonCollectVO);
            }
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查看用户所有分类并定位当前分类索引
     *
     * @param categoryId 当前分类
     * @return 用户所有分类并定位当前分类索引
     */
    @Override
    public Result getCategoryDropDownList(Long categoryId) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 查询到一级分类
        List<Category> categoryList = categoryService.list(
                new QueryWrapper<Category>()
                        .select("id", "name", "sort")
                        .eq("category_id", userId)
                        .eq("rank", "parent").orderByAsc("sort", "id")
        );
        // 如果有一级分类
        if (CollectionUtils.isNotEmpty(categoryList)) {
            // 查询所有子级分类
            List<Category> sonCategoryList = EntityUtils.getCategorySonList(categoryMapper, categoryList, new ArrayList<>());
            // 子级分类拼接到一级分类
            categoryList.addAll(sonCategoryList);
            // 转成前端需要的格式便利放入VO
            List<CategoryDropDownVO> categoryDropDownVOList = categoryList
                    .stream()
                    .map(category -> {
                        CategoryDropDownVO categoryDropDownVO = new CategoryDropDownVO();
                        categoryDropDownVO.setId(category.getId());
                        categoryDropDownVO.setText(category.getName());
                        return categoryDropDownVO;
                    })
                    .collect(Collectors.toList());
            // 假设选中下标为0
            int defaultIndex = 0;
            // 如果选中分类不为空也不为0
            if (categoryId != null && categoryId != 0L) {
                for (int i = 0; i < categoryDropDownVOList.size(); i++) {
                    // 便利找到选中分类下标则赋值
                    if (categoryDropDownVOList.get(i).getId().equals(categoryId)) {
                        defaultIndex = ++i;
                    }
                }
            }
            // values 分类列表，defaultIndex 选中分类下标
            Map<String, Object> categoryDropDownMap = new HashMap<>();
            categoryDropDownMap.put("list", categoryDropDownVOList);
            categoryDropDownMap.put("defaultIndex", defaultIndex);
            return ResultGenerator.genSuccessResult(categoryDropDownMap);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查看用户所有分类
     *
     * @return 用户所有分类
     */
    @Override
    public Result getCategoryTreeList() {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 查看用户所有分类（父子级）
        List<Category> categoryList = categoryService.list(
                new QueryWrapper<Category>()
                        .select("id", "category_id", "name", "rank", "sort")
                        .eq("category_id", userId)
                        .eq("rank", "parent").orderByAsc("sort", "id")
        );
        return ResultGenerator.genSuccessResult(EntityUtils.getTreeCategoryList(categoryService, categoryList));
    }

    /**
     * 增加分类
     *
     * @param param 增加分类参数
     * @return 增加结果
     */
    @Transactional
    @Override
    public Result insertCategory(CategorySaveParam param) {
        // 获取当前用户ID方便后续操作
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 如果父级分类id=0代表为一级分类，设置父级id为用户id，类型为父
        if (param.getCategoryId() == 0) {
            param.setCategoryId(userId);
            param.setRank("parent");
        } else { // 如果父级分类id！=0代表为子级分类，设置类型为子，通过其父级查到一级分类是否=当前用户id
            param.setRank("son");
            Long parentCategoryId = EntityUtils.getParentCategoryId(categoryService, Category.builder().categoryId(param.getCategoryId()).rank(param.getRank()).build());
            // 一级分类id不等于当前用户id则违规操作
            if (userId.longValue() != parentCategoryId.longValue()) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
            }
        }
        // 增加
        if (categoryMapper.insertCategory(param) > 0) {
            //增加成功后修改排序为ID保证ID不重复
            categoryService.update(new LambdaUpdateWrapper<Category>()
                    .set(Category::getSort, param.getId())
                    .eq(Category::getId, param.getId())
            );
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 修改分类
     *
     * @param param 增加分类参数
     * @return 修改结果
     */
    @Override
    public Result updateCategory(CategorySaveParam param) {
        // 获取当前用户ID方便后续操作
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 如果父级分类id=0代表为一级分类，设置父级id为用户id，类型为父
        if (param.getCategoryId() == 0) {
            param.setCategoryId(userId);
            param.setRank("parent");
        } else { // 如果父级分类id！=0代表为子级分类，设置类型为子，通过其父级查到一级分类是否=当前用户id
            param.setRank("son");
            Long parentCategoryId = EntityUtils.getParentCategoryId(categoryService, Category.builder().categoryId(param.getCategoryId()).rank(param.getRank()).build());
            // 一级分类id不等于当前用户id则违规操作
            if (userId.longValue() != parentCategoryId.longValue()) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
            }
            // 查询是否选择自己的子级分类做父类
            List<Long> categoryIds = new ArrayList<>(Collections.singletonList(param.getId()));
            // 获得自己所有子类ID
            List<Long> ids = EntityUtils.getSonCategoryIdList(categoryMapper, categoryIds, new ArrayList<>());
            categoryIds.addAll(ids);
            if (categoryIds.contains(param.getCategoryId())) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_CATEGORY_CHOOSE_ERROR);
            }
        }
        // 修改
        if (categoryMapper.updateCategory(param) > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 删除分类
     *
     * @param categoryId    分类id
     * @param deCollectFlag 是否删除其下收藏
     * @return 删除结果
     */
    @Override
    public Result deleteCategory(Long categoryId, Boolean deCollectFlag) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 查询出该分类的UserID
        Category category = categoryService.getOne(new QueryWrapper<Category>().select("category_id", "rank").eq("id", categoryId));
        Long parentCategoryId = EntityUtils.getParentCategoryId(categoryService, category);
        if (!userId.equals(parentCategoryId)) {
            // 非本人分类无法操作
            return ResultGenerator.genFailResultMsg(ErrorConstant.ROLE_ERROR);
        }
        // 如果该分类属于本人分类
        List<Long> categoryIds = Collections.singletonList(categoryId);
        // ids为选择删除分类id（包含子级）
        List<Long> ids = EntityUtils.getSonCategoryIdList(categoryMapper, categoryIds, new ArrayList<>());
        ids.add(categoryId);
        if (categoryService.removeByIds(ids)) {
            // 已删除分类，判断是否需要删除收藏
            if (deCollectFlag) {
                collectMapper.removeForCategoryId(ids);
            } else {
                // 不删除分类，全部放到首页
                collectMapper.updateForCategoryId(ids);
            }
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 排序用户分类
     *
     * @param list 用户分类列表（两个）
     * @return 排序结果
     */
    @Override
    public Result updateCategorySort(List<CategorySaveSortParam> list) {
        // 从用户的token信息中获取到用户的userId
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        for (int i = 0; i < list.size(); i++) {
            // 查询出两个分类的user是不是当前用户
            Long parentCategoryId = EntityUtils.getParentCategoryId(categoryService, categoryService.getOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getRank, Category::getCategoryId)
                    .eq(Category::getId, list.get(i).getId())));
            // 如果不是
            if (parentCategoryId.longValue() != userId.longValue()) {
                return ResultGenerator.genFailResultMsg(ErrorConstant.ROLE_ERROR);
            }
        }
        // 排序
        Integer integer = categoryMapper.updateCategorySort(list);
        if (integer > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }


}
