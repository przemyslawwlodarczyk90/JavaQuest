package com.example.javaquest._24_spring_security.Lesson14_CorsAndCsrfInSpringSecurity;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Lesson14_CorsAndCsrfInSpringSecurity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: CORS I CSRF W Spring Security - WLASNY mechanizm ===");

        /*
         * ============================================================
         * 📦 SPRING SECURITY MA WLASNE, ZINTEGROWANE MECHANIZMY
         * ============================================================
         * `_19_security_basics/Lesson09-10` uczyly CORS/CSRF RECZNIE
         * NA surowym `HttpServer` (obsluga OPTIONS, ciasteczko tokenu
         * CSRF, itd. - WSZYSTKO recznie napisane). Spring Security MA
         * WBUDOWANY mechanizm DLA OBU - `.cors(...)` (deleguje DO
         * `CorsConfigurationSource`, dokladnie TA SAMA idea CO Spring
         * MVC W `_22_spring_web/Lesson15`) I `.csrf(...)` (WLACZONY
         * DOMYSLNIE - juz widziany W kazdym `formLogin()` OD Lesson04).
         * Ta lekcja SKUPIA SIE NA CORS (nowosc W tym rozdziale) I
         * PODSUMOWUJE CSRF Z PERSPEKTYWY API "dla SPA" (token PRZEZ
         * naglowek, NIE ukryte pole formularza).
         */
        System.out.println("Security ma WLASNY .cors(...) I .csrf(...) - integrowane Z resztą filtrow, W odroznieniu OD recznej implementacji W _19_security_basics.");

        demonstrateCorsAllowedAndDisallowedOrigin();
        demonstrateCsrfTokenViaHeaderForSpaStyleClient();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.cors(cors -> cors.configurationSource(source))` - Security
         *   DELEGUJE DO `CorsConfigurationSource` (TA SAMA klasa CO W
         *   czystym Spring MVC).
         * - ZAPAMIETANE Z `_22_spring_web/Lesson15`: Origin SPOZA listy
         *   dozwolonych DAJE 403 NA SERWERZE (NIE TYLKO blokade PO
         *   stronie przegladarki) - TA SAMA zasada dotyczy Security.
         * - CSRF DLA API konsumowanego PRZEZ JavaScript/SPA: token
         *   pobierany PRZEZ GET (np. `/api/csrf-token`), WYSYLANY W
         *   naglowku (NIE W ukrytym polu formularza jak Lesson04/08/11) -
         *   nazwa naglowka POCHODZI Z `CsrfToken.getHeaderName()`.
         * - CSRF chroni PRZED atakiem WYKORZYSTUJACYM ciasteczko sesji
         *   (`_19_security_basics/Lesson10`) - W CZYSTO bezstanowym API
         *   (Lesson12-13, JWT W naglowku, BRAK ciasteczka sesji) CSRF
         *   jest CZESTO wylaczany, bo atak CSRF POLEGA WLASNIE NA
         *   automatycznym wysylaniu ciasteczek PRZEZ przegladarke.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    @RestController
    static class CorsController {
        @GetMapping("/api/public-data")
        String publicData() {
            return "Dane dostepne CROSS-ORIGIN TYLKO Z dozwolonej domeny";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class CorsApp {
        @Bean
        CorsController corsController() {
            return new CorsController();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("https://trusted.example.com"));
            config.setAllowedMethods(List.of("GET"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, @Qualifier("corsConfigurationSource") CorsConfigurationSource corsSource) throws Exception {
            // @Qualifier KONIECZNY - Spring MVC auto-konfiguruje TEZ 'mvcHandlerMappingIntrospector'
            // (typ HandlerMappingIntrospector), KTORY RowNIEZ implementuje CorsConfigurationSource,
            // wiec wstrzykiwanie PO SAMYM TYPIE jest DWUZNACZNE (2 kandydujace beany) - zweryfikowane
            // empirycznie (UnsatisfiedDependencyException: "expected single matching bean but found 2").
            http.cors(cors -> cors.configurationSource(corsSource))
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    private static void demonstrateCorsAllowedAndDisallowedOrigin() throws Exception {
        System.out.println("\n=== CORS: dozwolona domena -> 200, NIEDOZWOLONA -> 403 (NA SERWERZE) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CorsApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> allowed = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/public-data"))
                            .header("Origin", "https://trusted.example.com")
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET Z Origin=https://trusted.example.com -> status: " + allowed.statusCode()
                    + ", Access-Control-Allow-Origin: " + allowed.headers().firstValue("Access-Control-Allow-Origin").orElse("BRAK")
                    + " (oczekiwane: 200)");

            HttpResponse<String> disallowed = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/public-data"))
                            .header("Origin", "https://zla-domena.example.com")
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("GET Z Origin=https://zla-domena.example.com -> status: " + disallowed.statusCode()
                    + " (oczekiwane: 403 - Security ODRZUCA NA SERWERZE, kontroler NIGDY nie zostaje wywolany)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    @RestController
    static class CsrfApiController {
        @GetMapping("/api/csrf-token")
        Map<String, String> csrfToken(CsrfToken token) {
            return Map.of("token", token.getToken(), "headerName", token.getHeaderName());
        }

        @PostMapping("/api/action")
        String action() {
            return "Akcja wykonana";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class CsrfApp {
        @Bean
        CsrfApiController csrfApiController() {
            return new CsrfApiController();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // CSRF WLACZONY DOMYSLNIE - BEZ jawnego .csrf(...) W ogole (dziedziczy DOMYSLNE zachowanie).
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    private static final Pattern TOKEN_PATTERN = Pattern.compile("\"token\":\"([^\"]+)\"");
    private static final Pattern HEADER_NAME_PATTERN = Pattern.compile("\"headerName\":\"([^\"]+)\"");

    private static void demonstrateCsrfTokenViaHeaderForSpaStyleClient() throws Exception {
        System.out.println("\n=== CSRF DLA API (SPA-owy styl): token W naglowku, NIE W ukrytym polu formularza ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CsrfApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newBuilder().cookieHandler(new java.net.CookieManager()).build();

            HttpResponse<String> tokenResponse = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/csrf-token")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            String token = extract(TOKEN_PATTERN, tokenResponse.body());
            String headerName = extract(HEADER_NAME_PATTERN, tokenResponse.body());
            System.out.println("GET /api/csrf-token -> naglowek DO uzycia: " + headerName + ", token ZNALEZIONY: " + (token != null));

            HttpResponse<String> withoutHeader = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/action")).POST(HttpRequest.BodyPublishers.noBody()).build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("POST /api/action BEZ naglowka CSRF -> status: " + withoutHeader.statusCode() + " (oczekiwane: 403)");

            HttpResponse<String> withHeader = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/action"))
                            .header(headerName, token)
                            .POST(HttpRequest.BodyPublishers.noBody())
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("POST /api/action Z naglowkiem CSRF -> status: " + withHeader.statusCode()
                    + ", body: " + withHeader.body() + " (oczekiwane: 200)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static String extract(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }
}
