package com.aoher.filter;

import com.aoher.filter.model.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

public class JwtFilterBean {

    @Value("${services.auth}")
    private String authService;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/protected-resource", "/logout");

        return registrationBean;
    }
}
