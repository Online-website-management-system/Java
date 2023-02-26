package com.lucas.xunta.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lucas.xunta.category.dao.CategoryMapper;
import com.lucas.xunta.category.entity.Category;
import com.lucas.xunta.category.service.CategoryService;
import com.lucas.xunta.file.dto.UploadDTO;
import com.lucas.xunta.common.security.utils.JwtTokenUtil;
import com.lucas.xunta.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/7/7 14:47
 */


@Slf4j
public class EntityUtils {
    /**
     * 将参数实体封装到请求体中
     *
     * @param objParam 参数实体
     * @return 返回已封装号的请求体HttpEntity<String>
     */
    public static HttpEntity<String> prepareHttpEntity(Object objParam) {
        if (objParam == null) {
            return null;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 转为json字符串
            String param = JSONObject.toJSONString(objParam);
            return new HttpEntity<>(param, headers);
        } catch (Exception e) {
            // 序列号参数失败
            log.info("序列号参数失败：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 将文件封装到multipart/form-data请求体中
     *
     * @param dto 文件实体
     * @return 返回已封装的multipart/form-data请求体
     */
    public static MultiValueMap<String, Object> prepareFileEntity(UploadDTO dto) {
        if (dto == null) {
            return null;
        }
        try {
            // 获取文件类型实例
            MultiValueMap<String, Object> fileEntity = new LinkedMultiValueMap<>();
            // 循环取出文件
            for (MultipartFile item : dto.getFileList()) {
                ByteArrayResource file = new ByteArrayResource(item.getBytes()) {
                    @Override
                    public String getFilename() {
                        return item.getOriginalFilename();
                    }
                };
                // 放入multipart/form-data
                fileEntity.add("fileList", file);
            }
            // 放入multipart/form-data
            fileEntity.add("moduleName", dto.getModuleName());
            fileEntity.add("sourceIp", dto.getSourceIp());
            return fileEntity;
        } catch (Exception e) {
            // 提取文件失败
            log.info("提取文件失败：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 用户登录时封装返回信息
     *
     * @param jwtTokenUtil jwt工具类
     * @param user         用户信息
     * @return 返回封装好的用户信息
     */
    public static Map<String, Object> loginInfoVO(JwtTokenUtil jwtTokenUtil, User user) {
        // 返回Token
        String userJwt = user.getId() + "-" + user.getRoles();
        String token = jwtTokenUtil.generateToken(userJwt);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return data;
    }

    /**
     * 传入分类id（集合）查询所有子级分类id
     *
     * @param categoryMapper 分类Mapper
     * @param categoryIdList 分类id集合
     * @param allCategoryId  子级分类id集合
     * @return 子级分类id集合
     */
    public static List<Long> getSonCategoryIdList(CategoryMapper categoryMapper, List<Long> categoryIdList, List<Long> allCategoryId) {
        for (Long categoryId : categoryIdList) {
            List<Long> list = categoryMapper.listById(categoryId);
            if (CollectionUtils.isNotEmpty(list)) {
                allCategoryId.addAll(list);
                getSonCategoryIdList(categoryMapper, list, allCategoryId);
            }
        }
        return allCategoryId;
    }

    /**
     * 获取树形分类集合（无限树排列）
     *
     * @param categoryService 分类Service
     * @param categoryList    父级列表
     * @return 树形分类集合
     */
    public static List<Category> getTreeCategoryList(CategoryService categoryService, List<Category> categoryList) {
        for (Category category : categoryList) {
            List<Category> categories = categoryService.list(
                    new QueryWrapper<Category>()
                            .select("id", "category_id", "name", "rank", "sort")
                            .eq("category_id", category.getId())
                            .eq("rank", "son").orderByAsc("sort", "id")
            );
            // 如果查到的分类不为空则继续往下级查直至无子级返回
            if (CollectionUtils.isNotEmpty(categories)) {
                category.setCategoryList(getTreeCategoryList(categoryService, categories));
            }
        }
        return categoryList;
    }

    /**
     * 获取子级分类集合
     *
     * @param categoryMapper     分类Mapper
     * @param categoryList       父级分类集合
     * @param returnCategoryList 根据父级分类集合查到的子级分类集合
     * @return 子级分类集合
     */
    public static List<Category> getCategorySonList(CategoryMapper categoryMapper, List<Category> categoryList, List<Category> returnCategoryList) {
        for (Category category : categoryList) {
            List<Category> categories = categoryMapper.listByIdAndParentName(category.getId(), category.getName() + ">");
            if (CollectionUtils.isNotEmpty(categories)) {
                returnCategoryList.addAll(categories);
                getCategorySonList(categoryMapper, categories, returnCategoryList);
            }
        }
        return returnCategoryList;
    }

    /**
     * 根据分类ID查询出所属用户ID
     *
     * @param categoryService 分类Service
     * @param category        分类实体
     * @return 用户ID
     */
    public static Long getParentCategoryId(CategoryService categoryService, Category category) {
        if (category.getRank().equals("son")) {
            Category one = categoryService.getOne(new QueryWrapper<Category>().select("category_id", "rank").eq("id", category.getCategoryId()));
            return getParentCategoryId(categoryService, one);
        }
        return category.getCategoryId();
    }

}
