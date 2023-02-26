package com.lucas.xunta.collect.controller;


import com.lucas.xunta.collect.service.CollectService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.collect.controller.param.CollectSaveParam;
import com.lucas.xunta.collect.controller.param.CollectSaveSortParam;
import com.lucas.xunta.collect.controller.param.UrlInfoParam;
import com.lucas.xunta.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 收藏控制器
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/collect")
@Api(value = "收藏Controller", tags = "收藏Controller")
@Slf4j
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private FileService fileService;

    @PostMapping(value = "/{collectId}/visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "增加收藏访问",
            notes = "增加收藏访问"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result addVisit(@PathVariable Long collectId) {
        log.info("增加收藏访问接口参数 collectId:{}", collectId);
        return collectService.addVisit(collectId);
    }

    @PostMapping(value = "/{collectId}/{home}/home", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改收藏首页显示",
            notes = "修改收藏首页显示"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateCollectHome(@PathVariable Long collectId, @PathVariable Integer home) {
        log.info("修改收藏首页显示接口参数 collectId:{},home:{}", collectId, home);
        return collectService.updateCollectHome(collectId, home);
    }

    @DeleteMapping(value = "/{collectId}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "删除用户收藏",
            notes = "删除用户收藏"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result removeCollect(@PathVariable Long collectId) {
        log.info("删除用户收藏接口参数 collectId:{}", collectId);
        return collectService.removeCollect(collectId);
    }

    @GetMapping(value = "/{collectId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "通过收藏ID查看收藏",
            notes = "通过收藏ID查看收藏"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getCollect(@PathVariable Long collectId) {
        log.info("通过收藏ID查看收藏接口参数 collectId:{}", collectId);
        return collectService.getCollect(collectId);
    }


    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "增加用户收藏",
            notes = "增加用户收藏"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result insertCollect(@RequestBody @Valid CollectSaveParam param) {
        log.info("增加用户收藏接口参数 CollectSaveParam:{}", param);
        return collectService.insertCollect(param);
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改用户收藏",
            notes = "修改用户收藏"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateCollect(@RequestBody @Valid CollectSaveParam param) {
        log.info("修改用户收藏接口参数 CollectSaveParam:{}", param);
        return collectService.updateCollect(param);
    }

    @PutMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "排序用户收藏",
            notes = "排序用户收藏"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateCollectSort(@RequestBody List<CollectSaveSortParam> list) {
        log.info("排序用户收藏接口参数 List<CollectSaveSortParam>:{}", list);
        return collectService.updateCollectSort(list);
    }

    @PostMapping(value = "/upload_logo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(
            value = "上传收藏logo",
            notes = "上传收藏logo"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result uploadLogo(
            @RequestParam(value = "fileList")
            @NotNull(message = "LOGO不能为空") List<MultipartFile> fileList, HttpServletRequest request) {
        log.info("上传收藏logo接口参数 List<MultipartFile>：{}", fileList);
        return fileService.upload(fileList, request);
    }

    @PostMapping(value = "/url_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "获取目标网站相关信息",
            notes = "获取目标网站相关信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getUrlInfo(@RequestBody @Valid UrlInfoParam param) {
        log.info("获取目标网站相关信息接口参数 CollectInfoParam:{}", param);
        return collectService.getUrlInfo(param);
    }
}
