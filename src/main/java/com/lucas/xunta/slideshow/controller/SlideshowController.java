package com.lucas.xunta.slideshow.controller;


import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.security.constant.SecurityConstant;
import com.lucas.xunta.slideshow.controller.param.SlideshowUpdateImgParam;
import com.lucas.xunta.slideshow.service.SlideshowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 幻灯片前端控制器
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/slideshow")
@Api(value = "幻灯片Controller", tags = "幻灯片Controller")
public class SlideshowController {
    @Autowired
    private SlideshowService slideshowService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "幻灯片列表",
            notes = "幻灯片列表"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getList() {
        log.info("幻灯片列表接口参数 :");
        return slideshowService.getList();
    }

    @Secured({SecurityConstant.ROLE_ADMIN})
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改轮播图",
            notes = "修改轮播图"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateImg(@RequestBody @Valid SlideshowUpdateImgParam param) {
        log.info("修改轮播图参数 SlideshowUpdateImgParam:{}", param);
        return null;
    }
}
