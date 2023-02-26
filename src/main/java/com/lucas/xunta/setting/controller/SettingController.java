package com.lucas.xunta.setting.controller;


import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.setting.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 设置控制器
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Slf4j
@RestController
@RequestMapping("/setting")
@Api(value = "设置Controller", tags = "设置Controller")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "获取网站基本信息",
            notes = "获取网站基本信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getInfo() {
        log.info("获取网站基本信息接口参数");
        return settingService.getInfo();
    }
}
