package com.example.javaquest.platform.auth;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Tworzy i weryfikuje tokeny JWT (HS256), w ktorych "subject" to e-mail uzytkownika. */
@Component
class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey key;
    private final Duration validity;
    private final String issuer;

    JwtService(@Value("${auth.jwt.secret:}") String secret,
               @Value("${auth.jwt.expiration-days}") long expirationDays,
               @Value("${auth.jwt.issuer}") String issuer) {
        this.key = Keys.hmacShaKeyFor(resolveSecret(secret));
        this.validity = Duration.ofDays(expirationDays);
        this.issuer = issuer;
    }

    private static byte[] resolveSecret(String secret) {
        if (secret.isBlank()) {
            // Swiadomie nie ma tu domyslnego, "wpisanego na sztywno" sekretu - taki wyladowalby w repo
            // i pozwalalby falszowac tokeny. Losowy sekret na czas zycia procesu jest bezpieczny, ale
            // niewygodny (restart = ponowne logowanie), dlatego ostrzegamy.
            log.warn("Brak auth.jwt.secret (javaquest-platform-secrets.properties) - losuje sekret na czas "
                    + "tego uruchomienia, po restarcie wszyscy zostana wylogowani.");
            byte[] random = new byte[48];
            new SecureRandom().nextBytes(random);
            return random;
        }
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("auth.jwt.secret musi miec co najmniej 32 znaki (HS256).");
        }
        return bytes;
    }

    String createToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(validity)))
                .signWith(key)
                .compact();
    }

    /** E-mail z waznego tokenu albo pusty Optional (zly podpis, wygasniecie, zly issuer, smieci). */
    Optional<String> extractEmail(String token) {
        try {
            return Optional.ofNullable(Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
