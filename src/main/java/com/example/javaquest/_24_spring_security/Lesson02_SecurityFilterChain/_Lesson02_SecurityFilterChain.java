package com.example.javaquest._24_spring_security.Lesson02_SecurityFilterChain;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class _Lesson02_SecurityFilterChain {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: SecurityFilterChain - WSPOLCZESNA konfiguracja Security ===");

        /*
         * ============================================================
         * 📦 JEDYNY, AKTUALNY sposob konfiguracji W Spring Security 6.x/Boot 3.x
         * ============================================================
         * Lesson01 pokazal Security "z automatu" - ZERO kodu, WSZYSTKO
         * domyslnie zabezpieczone. W praktyce prawie ZAWSZE chcesz to
         * DOSTOSOWAC (np. endpoint `/health` publiczny, `/api/**`
         * wymagajacy logowania) - robi sie to PRZEZ bean `SecurityFilterChain`,
         * budowany LAMBDA DSL na `HttpSecurity`.
         *
         * UWAGA: TO jest JEDYNY, aktualny sposob konfiguracji W wersji
         * Springa uzywanej W TYM kursie (Spring Security 6.x/Boot 3.x) -
         * starszy styl (dziedziczenie po `WebSecurityConfigurerAdapter`)
         * ZOSTAL CALKOWICIE USUNIETY W wersji 6.x (Lesson03 pokaze
         * DOKLADNIE, jak wygladal I DLACZEGO go tu NIE zobaczysz).
         */
        System.out.println("SecurityFilterChain = bean budowany PRZEZ lambda DSL na HttpSecurity - JEDYNY aktualny sposob konfiguracji Security W tym kursie (Security 6.x/Boot 3.x).");

        demonstrateCustomSecurityFilterChainWithPublicAndPrivatePaths();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Bean SecurityFilterChain filterChain(HttpSecurity http)` -
         *   METODA W klasie `@Configuration`, zwracajaca `http.build()`.
         * - `authorizeHttpRequests(auth -> auth...)` - DEFINIUJE, KTORE
         *   sciezki wymagaja uwierzytelnienia, a KTORE SA publiczne.
         * - `requestMatchers("/wzorzec/**").permitAll()` - WYLACZA
         *   wymog logowania DLA konkretnych sciezek.
         * - `.anyRequest().authenticated()` - WSZYSTKO INNE WYMAGA
         *   logowania (ZAWSZE na KONCU lancucha regul - kolejnosc MA
         *   znaczenie, pierwsza dopasowana regula wygrywa).
         * - `.httpBasic(Customizer.withDefaults())` - WLACZA HTTP Basic
         *   Auth (jak W Lesson01, ale TERAZ JAWNIE skonfigurowane).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    @RestController
    static class DemoController {
        @GetMapping("/public/info")
        String publicInfo() {
            return "Informacja publiczna - dostepna BEZ logowania";
        }

        @GetMapping("/private/secret")
        String privateSecret() {
            return "Tajne dane - TYLKO dla zalogowanych";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**").permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class SecurityFilterChainApp {
    }

    private static void demonstrateCustomSecurityFilterChainWithPublicAndPrivatePaths() throws Exception {
        System.out.println("\n=== WLASNY SecurityFilterChain - /public/** BEZ logowania, reszta WYMAGA ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");

        // Patrz Lesson01 - Properties Z SpringApplicationBuilder MAJA NIZSZY priorytet niz
        // classpath'owy application.properties, wiec nadpisujemy globalne wylaczenie Security
        // PRZEZ System property (WYZSZY priorytet).
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SecurityFilterChainApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> publicNoAuth = httpGet(port, "/public/info", null);
            System.out.println("GET /public/info BEZ poswiadczen -> status: " + publicNoAuth.statusCode()
                    + ", body: " + publicNoAuth.body() + " (oczekiwane: 200 - permitAll)");

            HttpResponse<String> privateNoAuth = httpGet(port, "/private/secret", null);
            System.out.println("GET /private/secret BEZ poswiadczen -> status: " + privateNoAuth.statusCode() + " (oczekiwane: 401)");

            HttpResponse<String> privateWithAuth = httpGet(port, "/private/secret", "user:sekret123");
            System.out.println("GET /private/secret Z poswiadczeniami -> status: " + privateWithAuth.statusCode()
                    + ", body: " + privateWithAuth.body() + " (oczekiwane: 200)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> httpGet(int port, String path, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (basicAuthCredentials != null) {
            String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
            builder.header("Authorization", "Basic " + encoded);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
