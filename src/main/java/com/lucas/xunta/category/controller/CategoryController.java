package com.lucas.xunta.category.controller;


import com.lucas.xunta.category.controller.param.CategorySaveParam;
import com.lucas.xunta.category.controller.param.CategorySaveSortParam;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类控制器
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/category")
@Api(value = "类型Controller", tags = "类型Controller")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/parent_list/domain", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "根据域名查看用户所有分类",
            notes = "根据域名查看用户所有分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategoryParentListForDomain(String domain) {
        log.info("根据域名查看用户所有分类接口参数 domain:{}", domain);
        return categoryService.getCategoryParentListForDomain(domain);
    }

    @GetMapping(value = "/son_collect_list/domain", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "根据分类id和域查看子级分类和收藏网址",
            notes = "根据分类id和域查看子级分类和收藏网址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategorySonListAndCollectList(Long categoryId, String domain) {
        log.info("根据分类id和域查看子级分类和收藏网址接口参数 categoryId:{},domain:{}", categoryId, domain);
        return categoryService.getCategorySonListAndCollectList(categoryId, domain);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "根据关键词查看用户分类和收藏网址",
            notes = "根据关键词查看用户分类和收藏网址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategoryAndCollect(String keyword, String domain) {
        log.info("根据关键词查看用户分类和收藏网址接口参数 keyword：{},domain：{}", keyword, domain);
        return categoryService.getCategoryAndCollect(keyword, domain);
    }

    @GetMapping(value = "/parent_list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "查看用户所有一级分类",
            notes = "查看用户所有一级分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getParentList() {
        log.info("查看用户所有一级分类接口参数");
        return categoryService.getParentList();
    }

    @GetMapping(value = "/son_collect_list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "根据分类id查看子级分类和收藏网址",
            notes = "根据分类id查看子级分类和收藏网址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategorySonListAndCollectList(Long categoryId) {
        log.info("根据分类id查看子级分类和收藏网址接口参数 categoryId:{}", categoryId);
        return categoryService.getCategorySonListAndCollectList(categoryId);
    }

    @GetMapping(value = "/drop_down_list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "查看用户所有分类并定位当前分类索引",
            notes = "查看用户所有分类并定位当前分类索引"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategoryDropDownList(Long categoryId) {
        log.info("查看用户所有分类并定位当前分类索引接口参数 categoryId:{}", categoryId);
        return categoryService.getCategoryDropDownList(categoryId);
    }

    @GetMapping(value = "/tree_list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "查看用户所有分类",
            notes = "查看用户所有分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCategoryTreeList() {
        log.info("查看用户所有分类");
        return categoryService.getCategoryTreeList();
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改分类",
            notes = "修改分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateCategory(@RequestBody @Valid CategorySaveParam param) {
        log.info("修改分类接口参数 CategorySaveParam:{}", param);
        return categoryService.updateCategory(param);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "增加分类",
            notes = "增加分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result insertCategory(@RequestBody @Valid CategorySaveParam param) {
        log.info("增加分类接口参数 CategorySaveParam:{}", param);
        return categoryService.insertCategory(param);
    }

    @DeleteMapping(value = "/{categoryId}/{deCollectFlag}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "删除分类",
            notes = "删除分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result deleteCategory(@PathVariable Long categoryId, @PathVariable Boolean deCollectFlag) {
        log.info("删除分类接口参数");
        return categoryService.deleteCategory(categoryId, deCollectFlag);
    }

    @PutMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "排序用户分类",
            notes = "排序用户分类"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateCategorySort(@RequestBody List<CategorySaveSortParam> list) {
        log.info("排序用户分类接口参数 List<CategorySaveSortParam>:{}", list);
        return categoryService.updateCategorySort(list);
    }

}
