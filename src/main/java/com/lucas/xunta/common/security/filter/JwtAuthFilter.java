package com.lucas.xunta.common.security.filter;

import com.google.common.collect.Lists;
import com.lucas.xunta.common.security.utils.JwtTokenUtil;
import com.lucas.xunta.user.service.UserService;
import com.lucas.xunta.common.constant.ErrorConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 鉴权
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/7/9 10:16
 */
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenUtil.getJwtFromRequest(request);
        String requestURI = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        boolean flag = matcher.match("/login", requestURI);
        if (StringUtils.isBlank(token) || flag) {
            filterChain.doFilter(request, response);
            return;
        }
        // 将Token解译
        String userJwt = jwtTokenUtil.getUsernameFromToken(token);
        if (StringUtils.isNotBlank(userJwt)) {
            if (!jwtTokenUtil.isTokenExpired(token)) {
                // 提取jwt内信息 0为ID，1为权限
                String user[] = userJwt.split("-");
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user[1]);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user[0], Long.parseLong(user[0]), Lists.newArrayList(grantedAuthority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(ErrorConstant.ROLE_ERROR);
        response.getWriter().close();
    }
}