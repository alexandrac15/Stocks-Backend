package com.example.stocks.security;


import com.example.stocks.security.backendToken.ApiCallFilter;
import com.example.stocks.security.googleValidation.GoogleLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration


public class Filters {

    private final GoogleLoginFilter loginFilter;
    private final ApiCallFilter restFilter;

    @Autowired
    public Filters(GoogleLoginFilter loginFilter, ApiCallFilter restFilter) {
        this.loginFilter = loginFilter;
        this.restFilter = restFilter;
    }

    @Bean

    public FilterRegistrationBean loginRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/login/*"));
        return filterRegistrationBean;
    }

    @Bean

    public FilterRegistrationBean restRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(restFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/users/*"));
        return filterRegistrationBean;
    }
}
