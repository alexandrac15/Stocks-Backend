package com.example.stocks;

import com.example.stocks.service.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)

public class SecurityConfigvechi extends WebSecurityConfigurerAdapter {
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
        http.csrf().disable();
       // http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
               // .authorizeRequests().anyRequest();
                //.antMatchers("/logout_success")
                //.permitAll()
                //.antMatchers(HttpMethod.OPTIONS, "/*").permitAll()
              //  .anyRequest()
               // .authenticated()

                //.and()
               // .oauth2Login()

               // .userInfoEndpoint()
               // .oidcUserService(oidcUserService)
              //  .and()
              //  .successHandler(successHandler);




    }
    //@Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS"));
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


 }
