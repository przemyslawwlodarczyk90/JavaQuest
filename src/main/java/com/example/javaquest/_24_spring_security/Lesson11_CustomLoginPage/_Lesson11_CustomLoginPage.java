package com.example.javaquest._24_spring_security.Lesson11_CustomLoginPage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

public class _Lesson11_CustomLoginPage {

    private static final Pattern CSRF_TOKEN_PATTERN = Pattern.compile("name=\"_csrf\"[^>]*value=\"([^\"]+)\"");

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: Wlasna strona logowania (ZAMIAST wygenerowanej) ===");

        /*
         * ============================================================
         * 📦 .loginPage("/wlasna-sciezka") ZASTEPUJE domyslnie wygenerowana strone
         * ============================================================
         * Lesson04/08 uzywaly DOMYSLNEJ, wygenerowanej strony logowania
         * Boota. W realnej aplikacji chcesz WLASNY wyglad (branding).
         * `.loginPage("/login")` MOWI Security: "NIE generuj WLASNEJ
         * strony - PRZEKIEROWUJ niezalogowanych uzytkownikow DO TEJ
         * sciezki, KTORA TY obslugujesz". Ten projekt NIE MA silnika
         * szablonow (Thymeleaf/JSP), WIEC strona jest zwracana jako
         * SUROWY HTML PRZEZ `@ResponseBody` (`_22_spring_web/Lesson01`) -
         * W PRAWDZIWYM projekcie bylby to najczesciej szablon.
         *
         * KLUCZOWE: token CSRF WCIAZ musi znalezc sie W formularzu -
         * Spring wstrzykuje GO jako `CsrfToken` (parametr metody
         * kontrolera, ROZPOZNAWANY automatycznie PRZEZ Spring MVC),
         * SAM go NIE generujesz.
         */
        System.out.println(".loginPage(\"/login\") = ZASTAP domyslnie wygenerowana strone WLASNA - musisz SAM wstrzyknac token CSRF (CsrfToken jako parametr kontrolera).");

        demonstrateCustomLoginPageFullFlow();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.formLogin(form -> form.loginPage("/login").permitAll())` -
         *   WLASNA sciezka logowania, AUTOMATYCZNIE oznaczona jako
         *   publiczna (`permitAll()`).
         * - `CsrfToken` jako parametr metody kontrolera - Spring MVC
         *   WSTRZYKUJE token WYGENEROWANY WCZESNIEJ PRZEZ `CsrfFilter`
         *   (dziala DALEJ, NIEZALEZNIE OD tego, ze sciezka jest
         *   `permitAll()` - permitAll dotyczy TYLKO uwierzytelnienia,
         *   NIE wylacza reszty filtrow).
         * - REALNY projekt uzylby silnika szablonow (Thymeleaf) - tu
         *   CELOWO uproszczone DO surowego HTML, zeby NIE dodawac
         *   nowej zaleznosci.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    @RestController
    static class LoginPageController {
        @GetMapping("/login")
        @ResponseBody
        String customLoginPage(CsrfToken csrfToken) {
            return "<html><body>"
                    + "<h1>Niestandardowa strona logowania - JavaQuest</h1>"
                    + "<form method=\"post\" action=\"/login\">"
                    + "<input type=\"text\" name=\"username\"/>"
                    + "<input type=\"password\" name=\"password\"/>"
                    + "<input type=\"hidden\" name=\"" + csrfToken.getParameterName() + "\" value=\"" + csrfToken.getToken() + "\"/>"
                    + "<button type=\"submit\">Zaloguj</button>"
                    + "</form></body></html>";
        }

        @GetMapping("/api/dashboard")
        String dashboard() {
            return "Panel glowny - PO zalogowaniu WLASNA strona";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .formLogin(form -> form.loginPage("/login").permitAll());
            return http.build();
        }
    }

    @SpringBootApplication
    static class CustomLoginApp {
    }

    private static void demonstrateCustomLoginPageFullFlow() throws Exception {
        System.out.println("\n=== GET /login (WLASNY HTML) -> POST /login -> dostep PO zalogowaniu ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomLoginApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();

            HttpResponse<String> loginPage = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            boolean containsCustomTitle = loginPage.body().contains("Niestandardowa strona logowania");
            System.out.println("GET /login -> status: " + loginPage.statusCode() + " (oczekiwane: 200), zawiera WLASNY naglowek: " + containsCustomTitle);

            String csrfToken = extractCsrfToken(loginPage.body());
            System.out.println("Token CSRF wstrzykniety PRZEZ CsrfToken parametr kontrolera: " + (csrfToken != null ? "ZNALEZIONY" : "BRAK"));

            String formBody = "username=user&password=sekret123&_csrf=" + URLEncoder.encode(csrfToken, StandardCharsets.UTF_8);
            HttpRequest loginRequest = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();
            HttpResponse<Void> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.discarding());
            System.out.println("POST /login -> status: " + loginResponse.statusCode() + " (oczekiwane: 302)");

            HttpResponse<String> afterLogin = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/dashboard")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/dashboard PO zalogowaniu -> status: " + afterLogin.statusCode() + ", body: " + afterLogin.body());
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
