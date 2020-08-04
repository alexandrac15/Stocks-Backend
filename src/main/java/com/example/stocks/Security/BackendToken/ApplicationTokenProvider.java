package com.example.stocks.Security.BackendToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class ApplicationTokenProvider {

    private static final long EXPIRATION_TIME_SECONDS = 864000000;

    private static final String SECRET = "2D7eAnYpd523fvVA35up+q5DXhnxR0/C0i4bUqefif89jQoZj4OPYqnOZGT4/PVM" +
            "jiQnH+RKRmmVKa8Mjj8i2SI55kCBj/zPb2St1nXUC8ltWkBVPuqVH0bH6gpy8DlK" +
            "pz4DcN7PfGIIMxp9zoH9xo9RhktnW0iLsX6sIRnP5tSmNjlZE1v7Oi0zSsgyqLgl" +
            "9Gy0clfEHeaO/SdAqgQBBPsYG6Ww9bR6syW0rQ==";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    // Adauga o semnatura noua in headerele de http.  Aceasta semnatura este
    // semnatura de backend de care povesteam.
    // Pe langa semnatura se mai adauga un camp de Access-Control-Expose-Headers
    // acest camp ii zice CORS ului sa permita sa se citeasca din headere tokenul
    public static void addAuthentication(HttpServletResponse res, String userId) {
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + getNewPerishableToken(userId));
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    // Creeaza un token de tip JWT. Are o durata de viata de EXPIRATION_TIME_SECONDS secunde
    // si contine ca si informatii userId-ul obtinut din tokenul de la Google.
    // Asa putem accesa oricand in DB userul curent si sa aplicam modificari asupra lui
    // de ex: sa salvam la ce lucreaza
    private static String getNewPerishableToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.nanoTime() + EXPIRATION_TIME_SECONDS))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Extrage userId ul dintr-un request venit de la frontend
    public static Optional<String> getUserFromToken(HttpServletRequest request) {
        // Se incearca extragerea headerului care contine tokenul
        final String token = request.getHeader(HEADER_STRING);

        // Daca s-a gasit tokenul se incearca decodificarea lui
        if (token != null) {
            try {
                // Se incearca decodificarea si extragerea userId ului
                Claims body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                // Se verifica ca tokenul sa nu fie expirat
                final Instant expiration = body.getExpiration().toInstant();
                if (expiration.isBefore(Instant.now())) {
                    return Optional.empty();
                }

                // Daca s-a putut extrage userId ul si tokenul nu este expirat, all good, ii dam mai departe
                return Optional.of(body.getSubject());

            }
            catch (SignatureException e) {
                // Nu s-a putut extrage userId ul din token, cel mai probabil tokenul este corupt
            }
        }
        // Requestul nu este bun, intoarcem empty
        return Optional.empty();
    }
}
