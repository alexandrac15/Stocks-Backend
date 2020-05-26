package com.example.stocks;

import com.example.stocks.domain.User;
import com.example.stocks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


    public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Autowired
        private UserRepository userRepository;

        private String homeUrl = "http://localhost:8080/home";

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            if (response.isCommitted()) {
                return;
            }
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            Map attributes = oidcUser.getAttributes();
            String email = (String) attributes.get("email");
           //String name = (String) attributes.get("name");
           // User user = userRepository.findByEmail(email);
            request.getSession().setAttribute("email", email);

           // request.getSession().setAttribute("name", );
            request.getSession().setMaxInactiveInterval(10*60);

            getRedirectStrategy().sendRedirect(request, response, homeUrl);
        }

    }

