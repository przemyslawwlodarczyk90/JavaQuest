package com.example.javaquest._24_spring_security.Lesson08_FormLogin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.CookieManager;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Lesson08_FormLogin {

    private static final Pattern CSRF_TOKEN_PATTERN = Pattern.compile("name=\"_csrf\"[^>]*value=\"([^\"]+)\"");

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: formLogin() - JAWNA konfiguracja logowania formularzem ===");

        /*
         * ============================================================
         * 📦 OD DOMYSLNEGO ZACHOWANIA (Lesson04) DO JAWNEJ KONTROLI
         * ============================================================
         * Lesson04 pokazal formLogin "z automatu" - przekierowanie DO
         * ORYGINALNIE zadanego zasobu PO zalogowaniu. W praktyce
         * CZESTO chcesz TO NADPISAC: ZAWSZE przekieruj NA `/dashboard`
         * (NIEZALEZNIE OD tego, co uzytkownik probowal otworzyc), LUB
         * WLASNY URL DLA nieudanego logowania. Powiazanie Z
         * `_19_security_basics/Lesson04` - tam robiles TO recznie
         * (generowanie ID sesji, przekierowanie); TU konfigurujesz TO
         * DEKLARATYWNIE, W lambda DSL.
         */
        System.out.println("formLogin(form -> form...) - JAWNA kontrola NAD przekierowaniami PO udanym/nieudanym logowaniu.");

        demonstrateCustomSuccessAndFailureUrls();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.defaultSuccessUrl(url, true)` - drugi argument `true` =
         *   ZAWSZE przekieruj TU (ignorujac oryginalnie zadany zasob).
         *   `false` (domyslne) = przekieruj TU TYLKO gdy NIE bylo
         *   oryginalnego celu (np. uzytkownik wszedl BEZPOSREDNIO NA
         *   `/login`).
         * - `.failureUrl(url)` - dokad przekierowac PO NIEUDANYM
         *   logowaniu (domyslnie: `/login?error`).
         * - `.loginPage(url)` (NIE uzyte tu) - WLASNA strona logowania
         *   ZAMIAST wygenerowanej (wymaga silnika szablonow - zapowiedz
         *   Lesson11).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    @RestController
    static class DemoController {
        @GetMapping("/api/dashboard")
        String dashboard() {
            return "Panel glowny - PO udanym logowaniu";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .formLogin(form -> form
                            .defaultSuccessUrl("/api/dashboard", true)
                            .failureUrl("/login?failed"));
            return http.build();
        }
    }

    @SpringBootApplication
    static class FormLoginApp {
    }

    private static void demonstrateCustomSuccessAndFailureUrls() throws Exception {
        System.out.println("\n=== ZAWSZE przekieruj NA /api/dashboard PO sukcesie, NA /login?failed PO porazce ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(FormLoginApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient successClient = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();

            HttpResponse<String> loginPage = successClient.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            String csrfToken = extractCsrfToken(loginPage.body());

            HttpResponse<Void> successLogin = successClient.send(loginRequest(port, "user", "sekret123", csrfToken), HttpResponse.BodyHandlers.discarding());
            System.out.println("POST /login Z poprawnymi danymi -> status: " + successLogin.statusCode()
                    + ", Location: " + successLogin.headers().firstValue("Location").orElse("BRAK")
                    + " (oczekiwane: KONCZY SIE NA /api/dashboard, defaultSuccessUrl NADPISUJE oryginalny cel)");

            HttpClient failureClient = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
            HttpResponse<String> loginPage2 = failureClient.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            String csrfToken2 = extractCsrfToken(loginPage2.body());

            HttpResponse<Void> failedLogin = failureClient.send(loginRequest(port, "user", "zlehaslo", csrfToken2), HttpResponse.BodyHandlers.discarding());
            System.out.println("POST /login Z BLEDNYM haslem -> status: " + failedLogin.statusCode()
                    + ", Location: " + failedLogin.headers().firstValue("Location").orElse("BRAK")
                    + " (oczekiwane: /login?failed - WLASNY URL Z failureUrl(...))");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpRequest loginRequest(int port, String username, String password, String csrfToken) {
        String formBody = "username=" + username + "&password=" + password
                + "&_csrf=" + URLEncoder.encode(csrfToken, StandardCharsets.UTF_8);
        return HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formBody))
                .build();
    }

    private static String extractCsrfToken(String html) {
        Matcher matcher = CSRF_TOKEN_PATTERN.matcher(html);
        return matcher.find() ? matcher.group(1) : null;
    }
}
