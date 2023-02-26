package com.lucas.xunta.common.result;

/**
 * 响应结果生成工具
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/11/14 9:46
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return Result.builder().code(ResultCode.SUCCESS.code()).message(DEFAULT_SUCCESS_MESSAGE).build();
    }

    public static Result genSuccessResult(Object data) {
        return Result.builder().code(ResultCode.SUCCESS.code()).message(DEFAULT_SUCCESS_MESSAGE).data(data).build();
    }

    public static Result genFailResultMsg(String message) {
        return Result.builder().code(ResultCode.FAIL.code()).message(message).build();
    }

    public static Result genSuccessResultMsg(String message) {
        return Result.builder().code(ResultCode.SUCCESS.code()).message(message).build();
    }

    public static Result genFailResult(String message) {
        return Result.builder().code(ResultCode.FAIL.code()).message(message).build();
    }

    public static Result genUnauthorizedResult(String message) {
        return Result.builder().code(ResultCode.UNAUTHORIZED.code()).message(message).build();
    }
}