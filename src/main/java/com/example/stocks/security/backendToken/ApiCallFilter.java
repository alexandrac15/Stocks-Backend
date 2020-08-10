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

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;


        Optional<String> userFromToken = getUserFromToken(request);
        if (!userFromToken.isPresent()) {

            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        addAuthentication(response, userFromToken.get());
        filterChain.doFilter(request, response);
    }
}
