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

        // Incercam sa extragem din headerele trimise de frontend tokenul de Google
        String idToken = ((HttpServletRequest) servletRequest).getHeader("X-ID-TOKEN");

        // Raspunsul pe care il vom intoarce spre frontend
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Daca am reusit sa gasim tokenul de google, vrem sa il validam
        if (idToken != null) {
            final GoogleIdToken.Payload payload;
            try {
                // Vedem daca tokenul trimis de catre frontend in header este recunoscut de Google ca fiind bun
                Optional<User> optionalReturnedUser = googleTokenVerifier.validateToken(idToken);

                // Daca Google ul a recunoscut tokenul, atunci salvam userul si generam un token de-al nostru pentru a fi folosit
                // la apelul de endpointuri din backend
                if (optionalReturnedUser.isPresent()) {

                    // Luam datele userului si le salvam in DB daca nu exista deja.
                    User returnedUser = optionalReturnedUser.get();
                    repo.save(returnedUser);

                    // Generam un token de backend, care va fi folosit pentru a apela toate API urile pe care le expunem in controller
                    ApplicationTokenProvider.addAuthentication(response, returnedUser.getGoogleUserId());


                    // Aici vom intoarce catre frontend noul token pe care l-am generat si vrem sa fie folosit.
                    return;
                }
            } catch (Exception e) {
                // Eroare, nu facem nimic, lasam eroarea sa treaca mai departe si sa intoarca 401 cartre frontend
            }
        }

        // Intoarcem 401 catre frontend
        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

