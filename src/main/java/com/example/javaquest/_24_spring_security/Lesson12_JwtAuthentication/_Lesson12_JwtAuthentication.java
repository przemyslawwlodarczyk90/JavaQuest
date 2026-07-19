package com.example.javaquest._24_spring_security.Lesson12_JwtAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.Map;
import java.util.Properties;

public class _Lesson12_JwtAuthentication {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: Uwierzytelnianie JWT W Spring Security ===");

        /*
         * ============================================================
         * 📦 WLASNY FILTR ZAMIAST sesji (Lesson04/08/11)
         * ============================================================
         * `_19_security_basics/Lesson05_JwtIntroduction` nauczyl Cie
         * MECHANIKI JWT (`Jwts.builder()...signWith(key).compact()`,
         * `Jwts.parser().verifyWith(key)...`) - TA SAMA biblioteka
         * (`io.jsonwebtoken:jjwt-*`) jest TU UZYTA PONOWNIE, ale
         * OPAKOWANA W `OncePerRequestFilter` (WLASNY filtr servletowy,
         * powiazanie Z `_07_servlets/Lesson14`), KTORY:
         *   1. Odczytuje naglowek `Authorization: Bearer <token>`,
         *   2. Weryfikuje podpis I wygasniecie,
         *   3. Jesli poprawny - RECZNIE ustawia `Authentication` W
         *      `SecurityContextHolder` (TAK, jakby uzytkownik
         *      "zalogowal sie" sesyjnie, ale BEZ zadnej sesji).
         *
         * W odroznieniu OD `formLogin()`/`httpBasic()` (poprzednie
         * lekcje), TU Security NIE MA WBUDOWANEGO mechanizmu JWT -
         * MUSISZ go NAPISAC SAM (dokladnie TAK, jak poznales W
         * `_19_security_basics`). Lesson15 pokaze NATYWNE wsparcie
         * (`NimbusJwtDecoder`/OAuth2 Resource Server) - ZASTEPUJACE
         * caly TEN recznie napisany filtr.
         */
        System.out.println("WLASNY OncePerRequestFilter czyta 'Authorization: Bearer <token>', weryfikuje JWT (JJWT, jak _19_security_basics/Lesson05) I ustawia Authentication recznie.");

        demonstrateJwtLoginAndProtectedAccess();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `POST /api/login` - endpoint PUBLICZNY, sprawdza haslo
         *   (BCrypt, Lesson06), zwraca JWT (NIE ciasteczko sesji).
         * - Klient wysyla token W naglowku `Authorization: Bearer <token>`
         *   PRZY KAZDYM kolejnym zadaniu.
         * - `.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)` -
         *   WLASNY filtr WSTAWIONY W konkretne miejsce lancucha
         *   Security (PRZED domyslnym filtrem logowania formularzem).
         * - BRAK sesji (`csrf().disable()`, BEZ `formLogin()`/`httpBasic()`) -
         *   PELNE zrozumienie "bezstanowosci" JWT przyjdzie W Lesson13.
         * - ZASKAKUJACY, ZWERYFIKOWANY EMPIRYCZNIE fakt: BEZ tokenu (LUB Z
         *   NIEPRAWIDLOWYM) dostajesz 403, NIE 401 - bo BEZ
         *   `.httpBasic(...)`/`.formLogin(...)` Security NIE MA zarejestrowanego
         *   ZADNEGO `AuthenticationEntryPoint` (odpowiedzialnego ZA wysylanie 401)
         *   I spada NA domyslny `Http403ForbiddenEntryPoint`. Lesson16 pokaze
         *   WLASNY `AuthenticationEntryPoint` naprawiajacy TEN kod statusu.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
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
                String token = header.substring("Bearer ".length());
                try {
                    Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
                    String username = claims.getSubject();
                    String role = claims.get("role", String.class);
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                    var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException e) {
                    // Token nieprawidlowy/wygasly - CELOWO NIE ustawiamy Authentication - zadanie
                    // pojdzie DALEJ jako NIEUWIERZYTELNIONE, a autoryzacja (authenticated()) DA 401.
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @RestController
    static class AuthController {
        private final SecretKey key;
        private final PasswordEncoder encoder;
        private final Map<String, String> passwordHashes;
        private final Map<String, String> roles = Map.of("maria", "USER");

        AuthController(SecretKey key, PasswordEncoder encoder) {
            this.key = key;
            this.encoder = encoder;
            this.passwordHashes = Map.of("maria", encoder.encode("haslo123"));
        }

        @PostMapping("/api/login")
        ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
            String storedHash = passwordHashes.get(username);
            if (storedHash == null || !encoder.matches(password, storedHash)) {
                return ResponseEntity.status(401).body("Nieprawidlowe dane logowania");
            }
            String token = Jwts.builder()
                    .subject(username)
                    .claim("role", roles.get(username))
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 5 * 60_000))
                    .signWith(key)
                    .compact();
            return ResponseEntity.ok(token);
        }

        @GetMapping("/api/secure")
        String secure() {
            return "Dane dostepne TYLKO Z WAZNYM tokenem JWT";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        SecretKey jwtKey() {
            return Jwts.SIG.HS256.key().build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, SecretKey jwtKey) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated())
                    .addFilterBefore(new JwtAuthenticationFilter(jwtKey), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    @SpringBootApplication
    static class JwtApp {
    }

    private static void demonstrateJwtLoginAndProtectedAccess() throws Exception {
        System.out.println("\n=== POST /api/login (dostaje JWT) -> GET /api/secure Z tokenem Bearer ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(JwtApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> loginResponse = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/login"))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString("username=maria&password=haslo123"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            String token = loginResponse.body();
            System.out.println("POST /api/login -> status: " + loginResponse.statusCode() + " (oczekiwane: 200), token (skrocony): "
                    + token.substring(0, Math.min(30, token.length())) + "...");

            HttpResponse<String> secureWithToken = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/secure"))
                            .header("Authorization", "Bearer " + token)
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/secure Z tokenem -> status: " + secureWithToken.statusCode()
                    + ", body: " + secureWithToken.body() + " (oczekiwane: 200)");

            HttpResponse<String> secureWithoutToken = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/secure")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            // ZWERYFIKOWANE EMPIRYCZNIE: status TO 403, NIE 401 - BEZ '.httpBasic(...)'/'.formLogin(...)'
            // (celowo POMINIETYCH TU - JWT jest JEDYNYM mechanizmem logowania) Security NIE MA
            // zarejestrowanego ZADNEGO 'AuthenticationEntryPoint', WIEC uzywa DOMYSLNEGO
            // 'Http403ForbiddenEntryPoint' zamiast standardowego wyzwania 401 - Lesson16
            // (SecurityExceptionHandling) pokaze, jak WLASNY 'AuthenticationEntryPoint' zwraca
            // POPRAWNE 401 DLA API JWT.
            System.out.println("GET /api/secure BEZ tokenu -> status: " + secureWithoutToken.statusCode()
                    + " (oczekiwane: 403 - BRAK skonfigurowanego AuthenticationEntryPoint bez httpBasic/formLogin, zapowiedz Lesson16)");

            String tamperedToken = token.substring(0, token.length() - 5) + "AAAAA";
            HttpResponse<String> secureWithTamperedToken = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/secure"))
                            .header("Authorization", "Bearer " + tamperedToken)
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/secure Z ZMANIPULOWANYM tokenem -> status: " + secureWithTamperedToken.statusCode()
                    + " (oczekiwane: 403 - TA SAMA przyczyna: token odrzucony PRZEZ filtr, ale BRAK entry pointu DLA 401)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }
}
