package com.lucas.xunta.common.security.entrypoint;

import com.alibaba.fastjson.JSONObject;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.constant.ErrorConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        log.info("AuthenticationException : {}", authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result failedResult = Result.builder().code(HttpStatus.UNAUTHORIZED.value()).message(ErrorConstant.ROLE_ERROR).build();
        response.getWriter().write(JSONObject.toJSONString(failedResult));
        response.getWriter().close();
    }
}
