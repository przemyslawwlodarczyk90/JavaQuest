package com.example.javaquest._24_spring_security.Lesson10_MethodSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class _Lesson10_MethodSecurity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: @PreAuthorize - autoryzacja NA POZIOMIE METODY ===");

        /*
         * ============================================================
         * 📦 OD URL DO METODY - JESZCZE DOKLADNIEJSZA KONTROLA
         * ============================================================
         * Lesson09 chronilo CALE sciezki URL. `@PreAuthorize` chroni
         * POJEDYNCZA METODE - dziala PRZEZ Spring AOP (proxy, dokladnie
         * ten sam mechanizm CO `_20_spring_core/Lesson21-22`), NIE
         * przez filtr servletowy. Uzywa WYRAZEN SpEL
         * (`_20_spring_core/Lesson17_SpelBasics`) - MOZESZ odwolac sie
         * DO parametrow metody (`#nazwaParametru`) I DO biezacego
         * uzytkownika (`authentication.name`).
         *
         * `@EnableMethodSecurity` (Lesson03 - NOWY zamiennik
         * przestarzalego `@EnableGlobalMethodSecurity`) WLACZA TEN
         * mechanizm, Z `prePostEnabled=true` JUZ DOMYSLNIE.
         */
        System.out.println("@PreAuthorize = autoryzacja NA METODZIE (Spring AOP), NIE NA URL - wyrazenia SpEL, dostep DO parametrow I authentication.name.");

        demonstrateMethodLevelAuthorization();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@PreAuthorize("hasRole('ADMIN')")` - SPRAWDZANE PRZED
         *   wykonaniem metody.
         * - `@PreAuthorize("#username == authentication.name")` -
         *   dostep DO parametru metody (`#username`) I biezacego
         *   uzytkownika (`authentication.name`) - "TYLKO WLASCICIEL".
         * - `AccessDeniedException` (rzucony PRZEZ `@PreAuthorize`)
         *   jest PRZECHWYCONY PRZEZ ten sam filtr Security CO
         *   autoryzacja URL (Lesson09) - DAJE 403, NIEZALEZNIE OD
         *   TEGO, GDZIE (URL czy metoda) decyzja zostala PODJETA.
         * - Powiazanie Z `_20_spring_core/Lesson22` -
         *   self-invocation (`this.metoda()`) OMIJA proxy AOP, WIEC
         *   TEZ OMIJA `@PreAuthorize` - TA SAMA pulapka CO PRZY
         *   `@Transactional`.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    @Service
    static class ProductService {
        @PreAuthorize("hasRole('ADMIN')")
        String deleteProduct(String id) {
            return "Usunieto produkt " + id;
        }

        @PreAuthorize("hasRole('USER')")
        String viewProduct(String id) {
            return "Podglad produktu " + id;
        }

        @PreAuthorize("#username == authentication.name")
        String viewOwnProfile(String username) {
            return "Profil uzytkownika " + username;
        }
    }

    @RestController
    static class ProductController {
        private final ProductService service;

        @Autowired
        ProductController(ProductService service) {
            this.service = service;
        }

        @GetMapping("/api/products/{id}")
        String view(@PathVariable String id) {
            return service.viewProduct(id);
        }

        @DeleteMapping("/api/products/{id}")
        String delete(@PathVariable String id) {
            return service.deleteProduct(id);
        }

        @GetMapping("/api/profile/{username}")
        String profile(@PathVariable String username) {
            return service.viewOwnProfile(username);
        }
    }

    @Configuration
    @EnableMethodSecurity
    static class SecurityConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder encoder) {
            return new InMemoryUserDetailsManager(
                    User.withUsername("user1").password(encoder.encode("haslo1")).roles("USER").build(),
                    User.withUsername("admin1").password(encoder.encode("haslo2")).roles("USER", "ADMIN").build()
            );
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class MethodSecurityApp {
    }

    private static void demonstrateMethodLevelAuthorization() throws Exception {
        System.out.println("\n=== @PreAuthorize hasRole(...) I porownanie parametru Z authentication.name ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MethodSecurityApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("1) GET /api/products/5 jako 'user1' -> status: "
                    + request(port, "GET", "/api/products/5", "user1:haslo1").statusCode() + " (oczekiwane: 200 - hasRole('USER'))");

            System.out.println("2) DELETE /api/products/5 jako 'user1' -> status: "
                    + request(port, "DELETE", "/api/products/5", "user1:haslo1").statusCode() + " (oczekiwane: 403 - metoda wymaga hasRole('ADMIN'))");

            System.out.println("3) DELETE /api/products/5 jako 'admin1' -> status: "
                    + request(port, "DELETE", "/api/products/5", "admin1:haslo2").statusCode() + " (oczekiwane: 200)");

            System.out.println("4) GET /api/profile/user1 jako 'user1' (WLASNY profil) -> status: "
                    + request(port, "GET", "/api/profile/user1", "user1:haslo1").statusCode() + " (oczekiwane: 200 - #username == authentication.name)");

            System.out.println("5) GET /api/profile/admin1 jako 'user1' (CUDZY profil) -> status: "
                    + request(port, "GET", "/api/profile/admin1", "user1:haslo1").statusCode() + " (oczekiwane: 403 - #username != authentication.name)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> request(int port, String method, String path, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .header("Authorization", "Basic " + encoded)
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
