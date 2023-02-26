package com.lucas.xunta.config;


import com.lucas.xunta.common.security.entrypoint.JwtAuthenticationEntryPoint;
import com.lucas.xunta.common.security.filter.JwtAuthFilter;
import com.lucas.xunta.common.security.handler.SecurityAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: LucasMeng Email:a@wk2.cn
 * @Date: 2019/12/18 22:07
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Value("${jwt.antMatchers}")
    private String antMatchers;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] patterns = antMatchers.split(";");
        http
                //关闭CSRF
                .csrf().disable()
                //关闭自带登录
                .formLogin().disable()
                .httpBasic().disable()
                .cors().and()
                //请求认证
                .authorizeRequests()
                //放行请求
                .antMatchers(patterns).permitAll()
                .antMatchers(HttpMethod.GET, "/csrf").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .anyRequest()
//               .permitAll()
                .authenticated()
                //session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .disable()
                //异常处理
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(securityAccessDeniedHandler);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new SecurityPasswordEncoder();
    }*/
}
