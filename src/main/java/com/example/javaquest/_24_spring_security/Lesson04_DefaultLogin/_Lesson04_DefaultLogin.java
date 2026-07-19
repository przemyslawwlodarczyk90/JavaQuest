package com.example.javaquest._24_spring_security.Lesson04_DefaultLogin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Lesson04_DefaultLogin {

    private static final Pattern CSRF_TOKEN_PATTERN = Pattern.compile("name=\"_csrf\"[^>]*value=\"([^\"]+)\"");

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: Domyslna strona logowania Spring Boot ===");

        /*
         * ============================================================
         * 📦 DOMYSLNIE WLACZONE SA OBA: HTTP Basic (Lesson01) I formularz logowania
         * ============================================================
         * Gdy NIE definiujesz WLASNEGO `SecurityFilterChain` (jak W
         * Lesson01), Spring Boot domyslnie wlacza JEDNOCZESNIE `httpBasic()`
         * (Lesson01) ORAZ `formLogin()` - PRAWDZIWA, wygenerowana strona
         * HTML pod `/login`, na ktora przegladarka PRZEKIEROWUJE
         * niezalogowanego uzytkownika. To PUNKT WYJSCIA - "co dostajesz
         * za darmo", ZANIM zaczniesz cokolwiek konfigurowac recznie.
         */
        System.out.println("Bez wlasnego SecurityFilterChain: Boot wlacza JEDNOCZESNIE httpBasic() (Lesson01) I formLogin() (formularz HTML pod /login).");

        demonstrateDefaultLoginPageAndFormLogin();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `/login` (GET) - domyslnie WYGENEROWANA strona HTML (bez
         *   ani jednego pliku szablonu napisanego przez Ciebie).
         * - Formularz zawiera UKRYTE pole `_csrf` - Security domyslnie
         *   WYMAGA tokenu CSRF DLA zadan zmieniajacych stan (POST) -
         *   pogłębione W Lesson14.
         * - POST `/login` Z poprawnymi `username`/`password`/`_csrf`
         *   (TA SAMA sesja/cookie CO przy GET `/login`) -> przekierowanie
         *   302 DO oryginalnie zadanego zasobu.
         * - PO zalogowaniu, kolejne zadania W TEJ SAMEJ sesji (cookie
         *   `JSESSIONID`) SA juz uwierzytelnione - w odroznieniu OD HTTP
         *   Basic (Lesson01), gdzie poswiadczenia trzeba wysylac PRZY
         *   KAZDYM zadaniu.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    @RestController
    static class HelloController {
        @GetMapping("/api/hello")
        String hello() {
            return "Witaj po zalogowaniu formularzem!";
        }
    }

    @SpringBootApplication
    static class DefaultLoginApp {
    }

    private static void demonstrateDefaultLoginPageAndFormLogin() throws Exception {
        System.out.println("\n=== GET /login (strona HTML) -> POST /login (formularz) -> dostep PO zalogowaniu ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DefaultLoginApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            // cookieHandler - TA SAMA sesja (JSESSIONID) MUSI byc uzyta DLA GET /login, POST
            // /login I kolejnych zadan - dokladnie ten sam wzorzec co
            // _19_security_basics/Lesson04 (recznie napisane sesje).
            HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();

            HttpResponse<String> loginPage = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            boolean containsSignIn = loginPage.body().toLowerCase().contains("sign in");
            System.out.println("GET /login -> status: " + loginPage.statusCode() + " (oczekiwane: 200), zawiera 'Sign in': " + containsSignIn);

            String csrfToken = extractCsrfToken(loginPage.body());
            System.out.println("Token CSRF wyciagniety Z ukrytego pola formularza: " + (csrfToken != null ? "ZNALEZIONY" : "BRAK"));

            String formBody = "username=user&password=sekret123&_csrf=" + URLEncoder.encode(csrfToken, StandardCharsets.UTF_8);
            HttpRequest loginRequest = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();
            HttpResponse<Void> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.discarding());
            System.out.println("POST /login Z poprawnymi danymi -> status: " + loginResponse.statusCode()
                    + " (oczekiwane: 302), Location: " + loginResponse.headers().firstValue("Location").orElse("BRAK"));

            HttpResponse<String> afterLogin = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/hello")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/hello PO zalogowaniu (TA SAMA sesja) -> status: " + afterLogin.statusCode()
                    + ", body: " + afterLogin.body() + " (oczekiwane: 200 - BEZ ponownego podawania poswiadczen)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static String extractCsrfToken(String html) {
        Matcher matcher = CSRF_TOKEN_PATTERN.matcher(html);
        return matcher.find() ? matcher.group(1) : null;
    }
}
