package com.example.javaquest._24_spring_security.Lesson15_OAuth2LoginAndResourceServerIntro;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Properties;

public class _Lesson15_OAuth2LoginAndResourceServerIntro {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: OAuth2 Login I OAuth2 Resource Server - NATYWNE wsparcie Springa ===");

        /*
         * ============================================================
         * 📦 SPRING-OWA REALIZACJA KONCEPCJI Z _19_security_basics/Lesson06
         * ============================================================
         * `_19_security_basics/Lesson06_OAuth2AndOpenIdConnectIntro` uczyl
         * KONCEPCJI (authorization code flow) NA WLASNORECZNIE zbudowanym,
         * uproszczonym "authorization server". Spring MA 2 GOTOWE moduly:
         *
         * 1) `spring-boot-starter-oauth2-client` - "Zaloguj sie PRZEZ
         *    Google" (delegowane uwierzytelnianie, TWOJA aplikacja NIGDY
         *    NIE widzi hasla uzytkownika Google).
         * 2) `spring-boot-starter-oauth2-resource-server` (DODANY DO
         *    `pom.xml` W TEJ lekcji) - NATYWNA walidacja tokenow JWT
         *    (`NimbusJwtDecoder`) - TO JEST DOKLADNA ZAPOWIEDZ Z Lesson12:
         *    "Lesson15 pokaze NATYWNE wsparcie, ZASTEPUJACE recznie
         *    napisany filtr".
         *
         * Lesson12 pisales WLASNY `OncePerRequestFilter` - TU Spring
         * ROBI TO SAM, DEKLARATYWNIE (`.oauth2ResourceServer(...)`).
         */
        System.out.println("Lesson12 = WLASNY filtr JWT. Lesson15 = TA SAMA idea, ale NATYWNIE (.oauth2ResourceServer(...), NimbusJwtDecoder) - ZERO wlasnego filtra.");

        demonstrateNativeOAuth2ResourceServerReplacesLesson12Filter();
        explainOAuth2LoginConceptually();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
         *   .decoder(decoder).jwtAuthenticationConverter(converter)))` -
         *   JEDNA linijka ZASTEPUJACA CALY `OncePerRequestFilter` Z
         *   Lesson12.
         * - `NimbusJwtDecoder.withSecretKey(key).build()` - DLA tokenow
         *   HMAC (jak nasze); W PRAWDZIWEJ integracji Z zewnetrznym
         *   authorization serverem czesciej uzywa sie `withJwkSetUri(...)`
         *   (pobiera KLUCZE PUBLICZNE automatycznie).
         * - `JwtGrantedAuthoritiesConverter`/WLASNY konwerter - MAPUJE
         *   "claims" tokenu NA `GrantedAuthority` (Lesson07).
         * - ZASKAKUJACA, POZYTYWNA ROZNICA WZGLEDEM Lesson12: BRAK
         *   tokenu daje TERAZ POPRAWNE 401 (NIE 403 jak W Lesson12) -
         *   `.oauth2ResourceServer(...)` REJESTRUJE WLASNY
         *   `AuthenticationEntryPoint` (`BearerTokenAuthenticationEntryPoint`)
         *   automatycznie, W odroznieniu OD "goleg" recznego filtra
         *   Z Lesson12, KTORY tego NIE robil.
         * - OAuth2 Login ("Zaloguj sie PRZEZ Google") wymaga PRAWDZIWEGO
         *   `client-id`/`client-secret` zarejestrowanego W konsoli
         *   dostawcy - NIE da sie tego uruchomic W izolowanym demo BEZ
         *   internetu/konta - lekcja pokazuje STRUKTURE konfiguracji I
         *   PRZEPLYW, BEZ faktycznego wykonania.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    @RestController
    static class ResourceController {
        @GetMapping("/api/secure")
        String secure() {
            return "Dane chronione PRZEZ natywny OAuth2 Resource Server";
        }

        @GetMapping("/api/admin")
        String admin() {
            return "Panel administracyjny - TYLKO rola ADMIN";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class ResourceServerApp {
        @Bean
        ResourceController resourceController() {
            return new ResourceController();
        }

        @Bean
        SecretKey jwtKey() {
            return Jwts.SIG.HS256.key().build();
        }

        @Bean
        JwtDecoder jwtDecoder(SecretKey key) {
            return NimbusJwtDecoder.withSecretKey(key).build();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder decoder) throws Exception {
            // WLASNY konwerter - NASZE tokeny (jak W Lesson12/13) niosa claim "role" (np. "USER"),
            // NIE standardowy OAuth2 "scope" - JwtGrantedAuthoritiesConverter DOMYSLNIE czyta
            // "scope"/"scp", WIEC musimy go SKONFIGUROWAC NA WLASNY claim.
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            authoritiesConverter.setAuthoritiesClaimName("role");
            authoritiesConverter.setAuthorityPrefix("ROLE_");

            JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
            authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/admin").hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                            .decoder(decoder)
                            .jwtAuthenticationConverter(authenticationConverter)));
            return http.build();
        }
    }

    private static void demonstrateNativeOAuth2ResourceServerReplacesLesson12Filter() throws Exception {
        System.out.println("\n=== .oauth2ResourceServer(...) ZASTEPUJE WLASNY filtr Z Lesson12 ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ResourceServerApp.class)
                .properties(props)
                .run();
        try {
            SecretKey key = context.getBean(SecretKey.class);
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            String userToken = Jwts.builder().subject("piotr").claim("role", "USER")
                    .expiration(new Date(System.currentTimeMillis() + 60_000)).signWith(key).compact();
            String adminToken = Jwts.builder().subject("kierownik").claim("role", "ADMIN")
                    .expiration(new Date(System.currentTimeMillis() + 60_000)).signWith(key).compact();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> secureWithUserToken = get(client, port, "/api/secure", userToken);
            System.out.println("GET /api/secure Z tokenem USER -> status: " + secureWithUserToken.statusCode() + " (oczekiwane: 200)");

            HttpResponse<String> secureWithoutToken = get(client, port, "/api/secure", null);
            System.out.println("GET /api/secure BEZ tokenu -> status: " + secureWithoutToken.statusCode()
                    + " (oczekiwane: 401 - W ODROZNIENIU OD Lesson12/403, natywny Resource Server MA WLASNY AuthenticationEntryPoint)");

            HttpResponse<String> adminWithUserToken = get(client, port, "/api/admin", userToken);
            System.out.println("GET /api/admin Z tokenem USER -> status: " + adminWithUserToken.statusCode() + " (oczekiwane: 403 - brak roli ADMIN)");

            HttpResponse<String> adminWithAdminToken = get(client, port, "/api/admin", adminToken);
            System.out.println("GET /api/admin Z tokenem ADMIN -> status: " + adminWithAdminToken.statusCode() + " (oczekiwane: 200)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> get(HttpClient client, int port, String path, String bearerToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (bearerToken != null) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private static void explainOAuth2LoginConceptually() {
        System.out.println("\n=== OAuth2 Login (\"Zaloguj sie PRZEZ Google\") - KONCEPCYJNIE (BEZ realnego providera) ===");

        /*
         * ============================================================
         * 🔹 STRUKTURA KONFIGURACJI (application.yml) - JAK WYGLADALABY U DOSTAWCY REALNEGO
         * ============================================================
         * spring:
         *   security:
         *     oauth2:
         *       client:
         *         registration:
         *           google:
         *             client-id: TWOJ-CLIENT-ID.apps.googleusercontent.com
         *             client-secret: TWOJ-CLIENT-SECRET
         *             scope: openid,profile,email
         *
         * TYLE - Spring Boot auto-konfiguruje CALY przeplyw (endpoint
         * `/oauth2/authorization/google`, callback `/login/oauth2/code/google`,
         * wymiane kodu NA token, pobranie danych uzytkownika) - ZERO
         * WLASNEGO kodu (w odroznieniu OD `_19_security_basics/Lesson06`,
         * gdzie CALY przeplyw byl RECZNY).
         *
         * 🔍 PRZEPLYW (authorization code flow) - KROK PO KROKU
         * ============================================================
         * 1. Uzytkownik klika "Zaloguj sie przez Google" -> przegladarka
         *    idzie NA `/oauth2/authorization/google`.
         * 2. Spring PRZEKIEROWUJE NA REALNY serwer Google
         *    (accounts.google.com) Z `client_id`+`redirect_uri`.
         * 3. Uzytkownik LOGUJE SIE U GOOGLE (Twoja aplikacja NIGDY nie
         *    widzi jego hasla Google!) I WYRAZA ZGODE NA udostepnienie
         *    danych.
         * 4. Google PRZEKIEROWUJE Z POWROTEM NA `/login/oauth2/code/google?code=...`.
         * 5. Spring (W TLE, serwer-DO-serwera) WYMIENIA `code` NA
         *    `access_token`/`id_token` U Google.
         * 6. Spring TWORZY `OAuth2User`/`OidcUser` Z danymi (imie,
         *    email, zdjecie) I UWIERZYTELNIA uzytkownika W sesji.
         *
         * DLACZEGO NIE URUCHAMIAMY tego NAPRAWDE: WYMAGA prawdziwego
         * `client-id`/`client-secret` zarejestrowanego W Google Cloud
         * Console (konto, konfiguracja, redirect URI wskazujacy NA
         * TEN projekt) - NIEMOZLIWE W izolowanym, deterministycznym
         * demo BEZ internetu/kont zewnetrznych, W odroznieniu OD
         * Resource Servera powyzej (KTORY jest CALKOWICIE lokalny -
         * WLASNY klucz HMAC, ZERO zaleznosci OD internetu).
         */
        System.out.println("OAuth2 Login WYMAGA prawdziwego client-id/client-secret U dostawcy (Google) - pokazano STRUKTURE application.yml I PRZEPLYW, BEZ realnego wykonania.");
        System.out.println("Kontrast Z Resource Serverem POWYZEJ: TEN jest W PELNI lokalny (WLASNY klucz HMAC) - dlatego MOGLISMY go faktycznie URUCHOMIC.");
    }
}
