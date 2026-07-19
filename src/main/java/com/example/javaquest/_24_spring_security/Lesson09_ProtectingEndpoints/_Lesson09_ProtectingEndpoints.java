package com.example.javaquest._24_spring_security.Lesson09_ProtectingEndpoints;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class _Lesson09_ProtectingEndpoints {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: authorizeHttpRequests() W PRAKTYCE - regula PO regule ===");

        /*
         * ============================================================
         * 📦 REALISTYCZNY ZESTAW REGUL - PUBLICZNY ODCZYT, CHRONIONY ZAPIS
         * ============================================================
         * Lesson02 pokazal 1 wzorzec publiczny + 1 chroniony. Lesson07
         * pokazal role. Ta lekcja LACZY WSZYSTKO W REALISTYCZNYM
         * scenariuszu API sklepu: KATALOG produktow (GET) jest
         * PUBLICZNY, ale MODYFIKACJA katalogu (POST) wymaga roli
         * ADMIN, zamowienia wymagaja TYLKO zalogowania (dowolna rola),
         * a WSZYSTKO INNE (fallback) jest JAWNIE ZABRONIONE
         * (`denyAll()`) - BEZPIECZNIEJSZE DOMYSLNE zachowanie NIZ
         * `authenticated()` NA koncu (patrz Lesson02/07).
         */
        System.out.println("Realistyczny zestaw regul: GET /api/products publiczny, POST wymaga ADMIN, /api/orders/** wymaga zalogowania, RESZTA zabroniona (denyAll fallback).");

        demonstrateLayeredAuthorizationRules();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `requestMatchers(HttpMethod.GET, "/api/products").permitAll()` -
         *   PUBLICZNY odczyt.
         * - `requestMatchers(HttpMethod.POST, "/api/products").hasRole(...)` -
         *   CHRONIONY zapis, INNA regula DLA TEJ SAMEJ sciezki, ale
         *   INNEJ metody HTTP.
         * - `requestMatchers("/api/orders/**").authenticated()` -
         *   dowolny zalogowany uzytkownik.
         * - `.anyRequest().denyAll()` - JAWNY fallback ZAMIAST
         *   `authenticated()` - BEZPIECZNIEJSZE "domyslnie ZABRONIONE"
         *   (nawet zalogowany uzytkownik NIE dostanie dostepu DO
         *   nieprzewidzianej sciezki).
         * - KOLEJNOSC regul MA znaczenie - PIERWSZA dopasowana WYGRYWA,
         *   `anyRequest()` MUSI byc OSTATNIA.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    @RestController
    static class ShopController {
        @GetMapping("/api/products")
        String listProducts() {
            return "[\"Laptop\", \"Mysz\", \"Klawiatura\"]";
        }

        @PostMapping("/api/products")
        String addProduct() {
            return "Produkt dodany";
        }

        @RequestMapping("/api/orders/{id}")
        String orderDetails() {
            return "Szczegoly zamowienia";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder encoder) {
            return new InMemoryUserDetailsManager(
                    User.withUsername("klient").password(encoder.encode("haslo1")).roles("USER").build(),
                    User.withUsername("sklepadmin").password(encoder.encode("haslo2")).roles("USER", "ADMIN").build()
            );
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable()) // API bezstanowe testowane BEZ przegladarki - CSRF pogłębione W Lesson14.
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                            .requestMatchers("/api/orders/**").authenticated()
                            .anyRequest().denyAll())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class ShopApp {
    }

    private static void demonstrateLayeredAuthorizationRules() throws Exception {
        System.out.println("\n=== 6 scenariuszy: publiczny GET, chroniony POST (rola), zalogowani, fallback denyAll ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ShopApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("1) GET /api/products BEZ poswiadczen -> status: "
                    + request(port, "GET", "/api/products", null).statusCode() + " (oczekiwane: 200 - publiczny katalog)");

            System.out.println("2) POST /api/products BEZ poswiadczen -> status: "
                    + request(port, "POST", "/api/products", null).statusCode() + " (oczekiwane: 401)");

            System.out.println("3) POST /api/products jako ADMIN -> status: "
                    + request(port, "POST", "/api/products", "sklepadmin:haslo2").statusCode() + " (oczekiwane: 200)");

            System.out.println("4) POST /api/products jako zwykly USER -> status: "
                    + request(port, "POST", "/api/products", "klient:haslo1").statusCode() + " (oczekiwane: 403 - zalogowany, ale BEZ roli ADMIN)");

            System.out.println("5) GET /api/orders/5 jako zwykly USER -> status: "
                    + request(port, "GET", "/api/orders/5", "klient:haslo1").statusCode() + " (oczekiwane: 200 - wystarczy BYC zalogowanym)");

            System.out.println("6) GET /api/nieznana-sciezka NAWET jako ADMIN -> status: "
                    + request(port, "GET", "/api/nieznana-sciezka", "sklepadmin:haslo2").statusCode()
                    + " (oczekiwane: 403 - denyAll() fallback, ZADNA rola NIE POMOZE NA nieprzewidzianej sciezce)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> request(int port, String method, String path, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .method(method, HttpRequest.BodyPublishers.noBody());
        if (basicAuthCredentials != null) {
            String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
            builder.header("Authorization", "Basic " + encoded);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
