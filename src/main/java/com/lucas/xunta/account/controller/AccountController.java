package com.lucas.xunta.account.controller;


import com.lucas.xunta.account.entity.Account;
import com.lucas.xunta.account.service.AccountService;
import com.lucas.xunta.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网址账号密码信息控制器
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/account")
@Api(value = "网址账密信息Controller", tags = "网址账密信息Controller")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{collectId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "通过收藏ID查询网址账密信息",
            notes = "通过收藏ID查询网址账密信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result getAccountForCollectId(@PathVariable Long collectId) {
        log.info("通过收藏ID查询网址账密信息接口参数 CollectId:" + collectId);
        return accountService.getAccountForCollectId(collectId);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "修改网址账密信息",
            notes = "修改网址账密信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "Not Found，请求的资源不存在")})
    public Result updateAccount(@RequestBody Account account) {
        log.info("修改网址账密信息接口参数 Account:" + account);
        return accountService.updateAccount(account);
    }
}
