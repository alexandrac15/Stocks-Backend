package com.example.stocks.security.backendToken;

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

    private static final long EXPIRATION_TIME_SECONDS = 86400;

    private static final String SECRET = "2D7eAnYpd523fvVA35up+q5DXhnxR0/C0i4bUqefif89jQoZj4OPYqnOZGT4/PVM" +
            "jiQnH+RKRmmVKa8Mjj8i2SI55kCBj/zPb2St1nXUC8ltWkBVPuqVH0bH6gpy8DlK" +
            "pz4DcN7PfGIIMxp9zoH9xo9RhktnW0iLsX6sIRnP5tSmNjlZE1v7Oi0zSsgyqLgl" +
            "9Gy0clfEHeaO/SdAqgQBBPsYG6Ww9bR6syW0rQ==";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";


    public static void addAuthentication(HttpServletResponse res, String userId) {
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + getNewPerishableToken(userId));
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }


    private static String getNewPerishableToken(String userId) {
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() + EXPIRATION_TIME_SECONDS);
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date((new Date()).getTime() + EXPIRATION_TIME_SECONDS))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    public static Optional<String> getUserFromToken(String token) {

        if (token != null) {
            try {

                Claims body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();


                final Instant expiration = body.getExpiration().toInstant();
                if (expiration.isBefore(Instant.now())) {
                    return Optional.empty();
                }


                return Optional.of(body.getSubject());

            }
            catch (SignatureException e) {

            }
        }

        return Optional.empty();
    }
}
