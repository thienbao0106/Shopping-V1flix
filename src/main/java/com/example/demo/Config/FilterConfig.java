package com.example.demo.Config;

import com.example.demo.Auth.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> jwtFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> filter = new FilterRegistrationBean<RequestResponseLoggingFilter>();
        filter.setFilter(new RequestResponseLoggingFilter());
        // provide endpoints which needs to be restricted.
        // All Endpoints would be restricted if unspecified
        filter.addUrlPatterns("/users", "/products/*");
        return filter;
    }
}
