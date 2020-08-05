package com.example.stocks.security.backendToken;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.stocks.security.backendToken.ApplicationTokenProvider.addAuthentication;
import static com.example.stocks.security.backendToken.ApplicationTokenProvider.getUserFromToken;

@Component
public class ApiCallFilter implements Filter {
    @Override
    // Filtrul pentru localhost:8080/controller/*.
    // Extrage tokenul dat anterior de catre backend si il valideaza ca fiind corect,
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Requestul de la frontend
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // Raspunsul catre frontend
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Se incearca extragerea si validarea tokenului
        Optional<String> userFromToken = getUserFromToken(request);
        if (!userFromToken.isPresent()) {
            // Daca nu este cu succes se intoarce 401.
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // Daca validarea este ok, se apeleaza in continuare endpointul de controller
        addAuthentication(response, userFromToken.get());
        filterChain.doFilter(request, response);
    }
}
