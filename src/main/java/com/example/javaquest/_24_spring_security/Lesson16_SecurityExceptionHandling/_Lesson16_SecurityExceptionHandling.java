package com.example.javaquest._24_spring_security.Lesson16_SecurityExceptionHandling;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class _Lesson16_SecurityExceptionHandling {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: WLASNY AuthenticationEntryPoint I AccessDeniedHandler ===");

        /*
         * ============================================================
         * 📦 ZAMYKAMY LUKE Z LESSON12 - SPOJNE, JSON-owe 401/403
         * ============================================================
         * Lesson12 (WLASNY filtr JWT) MIAL WADE: BRAK tokenu dawal 403
         * (NIE 401), bo Security NIE MIAL zarejestrowanego ZADNEGO
         * `AuthenticationEntryPoint` (`httpBasic()`/`formLogin()` GO
         * dostarczaja, ale MY ich NIE uzywalismy). Lesson15
         * (`.oauth2ResourceServer(...)`) NAPRAWIL TO automatycznie.
         * TA lekcja pokazuje, jak NAPRAWIC TO RECZNIE (DLA WLASNEGO
         * filtra Z Lesson12) - I DODATKOWO zwraca SPOJNY JSON (RFC 7807,
         * powiazanie Z `_18_rest_api/Lesson12_ErrorResponseDesign`)
         * ZAMIAST domyslnej, pustej odpowiedzi Security.
         *
         * 2 rozne wyjatki, 2 rozni obsluzyciele:
         * - `AuthenticationEntryPoint` - "NIE WIEM kim jestes" (401).
         * - `AccessDeniedHandler` - "WIEM kim jestes, ale NIE MASZ
         *   uprawnien" (403).
         */
        System.out.println("AuthenticationEntryPoint = 401 ('nie wiem kim jestes'). AccessDeniedHandler = 403 ('wiem, ale NIE WOLNO Ci').");

        demonstrateJsonEntryPointAndAccessDeniedHandler();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.exceptionHandling(handling -> handling
         *   .authenticationEntryPoint(...).accessDeniedHandler(...))` -
         *   JEDNO miejsce konfiguracji OBU obsluzycieli.
         * - `AuthenticationEntryPoint.commence(...)` - WOLANY, gdy
         *   `AuthenticationException` (BRAK/NIEPRAWIDLOWE poswiadczenia).
         * - `AccessDeniedHandler.handle(...)` - WOLANY, gdy
         *   `AccessDeniedException` (uwierzytelniony, ale BEZ uprawnien).
         * - Powiazanie Z `_18_rest_api/Lesson12` - format `{"type",
         *   "title", "status", "detail"}` (RFC 7807) POWTORZONY TERAZ
         *   NA POZIOMIE Security (NIE `@RestControllerAdvice`, KTORY
         *   dziala TYLKO WEWNATRZ Spring MVC - wyjatki Security
         *   POWSTAJA WCZESNIEJ, W FILTRACH, przed Spring MVC W ogole).
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    static class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"type\":\"about:blank\",\"title\":\"Brak uwierzytelnienia\",\"status\":401,\"detail\":\""
                    + authException.getMessage() + "\"}");
        }
    }

    static class JsonAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"type\":\"about:blank\",\"title\":\"Brak uprawnien\",\"status\":403,\"detail\":\""
                    + accessDeniedException.getMessage() + "\"}");
        }
    }

    static class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final SecretKey key;

        JwtAuthenticationFilter(SecretKey key) {
            this.key = key;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                try {
                    Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(header.substring(7)).getPayload();
                    String role = claims.get("role", String.class);
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                    var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException ignored) {
                    // Token nieprawidlowy - BRAK ustawienia Authentication -> AuthenticationEntryPoint.
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @RestController
    static class DemoController {
        @GetMapping("/api/secure")
        String secure() {
            return "Dane chronione";
        }

        @GetMapping("/api/admin")
        String admin() {
            return "Panel administracyjny";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class ExceptionHandlingApp {
        @Bean
        DemoController demoController() {
            return new DemoController();
        }

        @Bean
        SecretKey jwtKey() {
            return Jwts.SIG.HS256.key().build();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, SecretKey key) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .exceptionHandling(handling -> handling
                            .authenticationEntryPoint(new JsonAuthenticationEntryPoint())
                            .accessDeniedHandler(new JsonAccessDeniedHandler()))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/admin").hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .addFilterBefore(new JwtAuthenticationFilter(key), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    private static void demonstrateJsonEntryPointAndAccessDeniedHandler() throws Exception {
        System.out.println("\n=== WLASNE, JSON-owe 401 (BRAK tokenu) I 403 (BRAK roli) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ExceptionHandlingApp.class)
                .properties(props)
                .run();
        try {
            SecretKey key = context.getBean(SecretKey.class);
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String userToken = Jwts.builder().subject("adam").claim("role", "USER")
                    .expiration(new Date(System.currentTimeMillis() + 60_000)).signWith(key).compact();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> withoutToken = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/secure")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/secure BEZ tokenu -> status: " + withoutToken.statusCode()
                    + ", body: " + withoutToken.body() + " (oczekiwane: 401, JSON Z 'title':'Brak uwierzytelnienia')");

            HttpResponse<String> forbiddenAdmin = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/admin"))
                            .header("Authorization", "Bearer " + userToken).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/admin Z tokenem USER (BEZ roli ADMIN) -> status: " + forbiddenAdmin.statusCode()
                    + ", body: " + forbiddenAdmin.body() + " (oczekiwane: 403, JSON Z 'title':'Brak uprawnien')");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }
}
