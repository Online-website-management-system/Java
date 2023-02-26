package com.lucas.xunta.common.exception;

import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


/**
 * 全局异常处理
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/1/2 10:17
 */
@RestControllerAdvice(basePackages = {"com.lucas.xunta"})
@Slf4j
public class XuntaExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result xuntaExceptionHandler(Exception e) {
        log.info("异常 {}", e.getMessage());
        return ResultGenerator.genFailResultMsg(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result exceptionHandler(MethodArgumentNotValidException e) {
        log.info("XunTa模块参数绑定异常 {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String msg = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage()).collect(Collectors.joining(";"));
        return ResultGenerator.genFailResultMsg(msg);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Result exceptionHandler(HttpMessageNotReadableException e) {
        log.info("XunTa模块参数转换异常 {}", e.getMessage());
        return Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(e.getMessage().split(";")[0]).build();
    }

}