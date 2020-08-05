package com.example.stocks.security;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true) // < poti scoate debug ul, e folositor cateodata dar daca vrei sa dai deploy la app trebuie sa il scoti
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Configurarile de spring security, nimic interesant
    protected void configure(HttpSecurity http) throws Exception {
        http
               .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    // Configurarile de CORS
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH"));

        configuration.setAllowCredentials(true);
        // Pentru ca punem niste headere de mana este important sa permitem sa treaca prin CORS TOATE headerele altfel da eroare 401
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
