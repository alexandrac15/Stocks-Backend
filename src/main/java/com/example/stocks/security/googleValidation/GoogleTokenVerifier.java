package com.example.stocks.security.googleValidation;


import com.example.stocks.domain.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
public class GoogleTokenVerifier {

    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();


    private static final String CLIENT_ID = "" ;


    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();


    public Optional<User> validateToken(String token){


        GoogleIdToken idToken = null;
        try {
            // Se verifica tokenul primit
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Daca tokenul a fost validat cu succes, putem extrage datele userului
        if (idToken != null) {

            User returnedUser = new User();

            Payload payload = idToken.getPayload();

            // Logheaza id-ul dat de Google
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);
            returnedUser.setGoogleUserId(userId);

            // Salveaza datele relevante despre user
            returnedUser.setEmail(payload.getEmail());
            returnedUser.setName((String) payload.get("family_name"));


            // Intoarce datele obtinute
            return Optional.of(returnedUser);

        }

        else {
            System.out.println("Invalid ID token.");
            return Optional.empty();
        }
    }

}
