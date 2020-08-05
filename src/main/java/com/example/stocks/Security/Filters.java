package com.example.stocks.Security;


import com.example.stocks.Security.BackendToken.ApiCallFilter;
import com.example.stocks.Security.GoogleValidation.GoogleLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration

// Clasa de configurare, se foloseste pentru a-i spune spring security ul sa adauge cele 2 filtre GoogleLoginFilter si ApiCallFilter
// pentru endpointurile de /login si respectiv /controller/*
public class Filters {

    private final GoogleLoginFilter loginFilter;
    private final ApiCallFilter restFilter;

    @Autowired
    public Filters(GoogleLoginFilter loginFilter, ApiCallFilter restFilter) {
        this.loginFilter = loginFilter;
        this.restFilter = restFilter;
    }

    @Bean
    // inregistram filtrul de GoogleLoginFilter pentru /login
    public FilterRegistrationBean loginRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/login/*"));
        return filterRegistrationBean;
    }

    @Bean
    // inregistram filtrul de ApiCallFilter pentru /controller/*
    public FilterRegistrationBean restRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(restFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/users/*"));
        return filterRegistrationBean;
    }
}
