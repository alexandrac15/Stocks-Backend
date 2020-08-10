package com.example.stocks.security.googleValidation;


import com.example.stocks.security.backendToken.ApplicationTokenProvider;
import com.example.stocks.domain.User;
import com.example.stocks.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class GoogleLoginFilter implements Filter {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    UserRepository repo;

    // Filtrul pentru localhost:8080/login.
    // Extrage tokenul de Google trimis din frontend, il valideaza, genereaza un token nou de backend pe care il trimite inapoi la frontend
    // pentru a fi folosit in apeluri de endpointuri de controller
    // Daca acest flow esueaza, se trimite 401 catre frontend
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        String idToken = ((HttpServletRequest) servletRequest).getHeader("X-ID-TOKEN");


        HttpServletResponse response = (HttpServletResponse) servletResponse;


        if (idToken != null) {
            final GoogleIdToken.Payload payload;
            try {

                Optional<User> optionalReturnedUser = googleTokenVerifier.validateToken(idToken);


                if (optionalReturnedUser.isPresent()) {


                    User returnedUser = optionalReturnedUser.get();
                    repo.save(returnedUser);


                    ApplicationTokenProvider.addAuthentication(response, returnedUser.getGoogleUserId());



                    return;
                }
            } catch (Exception e) {

            }
        }


        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

