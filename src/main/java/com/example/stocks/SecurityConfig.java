package com.example.stocks;

import com.example.stocks.service.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOidcUserService oidcUserService;

//
//    @Bean
//    public AuthenticationSuccessHandler mySuccessHandler(){
//        return new CustomAuthenticationSuccessHandler ();
//    }

    @Bean
    public CustomAuthenticationSuccessHandler authSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
    @Autowired
    CustomAuthenticationSuccessHandler successHandler;
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/logout_success")
               // .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .oauth2Login()

                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                .and()
                .successHandler(successHandler);



    }


 }
