package com.lucas.xunta.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

/**
 * Druid监控配置
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/11/20 9:33
 */
@Configuration
public class DruidMonitorConfiguration {
    @Value(value = "${spring.datasource.druid.login-username}")
    private String loginUsername;
    @Value(value = "${spring.datasource.druid.login-password}")
    private String loginPassword;

    /**
     * 配置后台管理的servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> map = Maps.newHashMap();
        map.put("loginUsername", loginUsername);
        map.put("loginPassword", loginPassword);
        map.put("allow", "");
        map.put("deny", "");
        bean.setInitParameters(map);
        return bean;
    }

    /**
     * 配置Web监控的Filter
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = Maps.newHashMap();
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }


}