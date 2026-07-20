package com.example.javaquest._29_spring_reactive.Lesson14_ReactiveSecurity;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson14_ReactiveSecurity {

    @RestController
    static class ChronionyController {
        @GetMapping("/publiczny")
        public Mono<String> publiczny() {
            return Mono.just("Kazdy moze to zobaczyc");
        }

        @GetMapping("/prywatny")
        public Mono<String> prywatny() {
            return Mono.just("Tylko zalogowany uzytkownik to widzi");
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableWebFluxSecurity
    static class ReactiveSecurityApp {
        @Bean
        ChronionyController chronionyController() {
            return new ChronionyController();
        }

        @Bean
        ReactiveWebServerFactory reactiveWebServerFactory() {
            return new NettyReactiveWebServerFactory();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        MapReactiveUserDetailsService userDetailsService(PasswordEncoder encoder) {
            var uzytkownik = User.withUsername("kasia")
                    .password(encoder.encode("tajne123"))
                    .roles("USER")
                    .build();
            return new MapReactiveUserDetailsService(uzytkownik);
        }

        @Bean
        SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
            return http
                    .authorizeExchange(exchange -> exchange
                            .pathMatchers("/publiczny").permitAll()
                            .anyExchange().authenticated())
                    .httpBasic(org.springframework.security.config.Customizer.withDefaults())
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .build();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Reactive Security - SecurityWebFilterChain (odpowiednik SecurityFilterChain dla WebFlux) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_24_spring_security`
         * ============================================================
         * `_24_spring_security` uczyl `SecurityFilterChain` (Servlet
         * Filter Chain, `_07_servlets/Lesson14`). Spring Security ma
         * ODDZIELNY, REAKTYWNY stos DLA WebFlux: `SecurityWebFilterChain`
         * (ZAMIAST `SecurityFilterChain`), `ServerHttpSecurity`
         * (ZAMIAST `HttpSecurity`), `authorizeExchange` (ZAMIAST
         * `authorizeHttpRequests`), `MapReactiveUserDetailsService`
         * (ZAMIAST `InMemoryUserDetailsManager`).
         *
         * TA SAMA idea (uwierzytelnienie/autoryzacja), TA SAMA
         * KONWENCJA `@EnableWebFluxSecurity` (ZAMIAST
         * `@EnableWebSecurity`) - ale CALY stos jest NIEBLOKUJACY.
         */
        System.out.println("SecurityWebFilterChain (WebFlux) = REAKTYWNY odpowiednik SecurityFilterChain (_24_spring_security). Ta sama idea, inny stos API.");

        // Globalne wylaczenie ReactiveSecurityAutoConfiguration (application.properties) MUSI
        // byc JAWNIE PRZYWROCONE tutaj - TEN SAM wzorzec co _24_spring_security dla servletowej
        // wersji (System.setProperty MA WYZSZY priorytet niz classpath'owy application.properties).
        System.setProperty("spring.autoconfigure.exclude", "");
        try {
            runDemo();
        } finally {
            System.clearProperty("spring.autoconfigure.exclude");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnableWebFluxSecurity` (ZAMIAST `@EnableWebSecurity`
         *   Z `_24_spring_security`).
         * - `ServerHttpSecurity`/`SecurityWebFilterChain` (ZAMIAST
         *   `HttpSecurity`/`SecurityFilterChain`).
         * - `authorizeExchange()`+`pathMatchers()` (ZAMIAST
         *   `authorizeHttpRequests()`+`requestMatchers()`).
         * - `MapReactiveUserDetailsService` (ZAMIAST
         *   `InMemoryUserDetailsManager`).
         * - `PasswordEncoder`/BCrypt - DOKLADNIE TE SAME klasy CO
         *   W servletowym Spring Security - hashowanie hasel NIE MA
         *   "reaktywnej" wersji (CPU-bound, NIE I/O-bound).
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static void runDemo() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ReactiveSecurityApp.class)
                .web(WebApplicationType.REACTIVE)
                .properties("server.port=0", "logging.level.root=WARN")
                .run()) {

            Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            WebClient client = WebClient.create("http://localhost:" + port);

            demonstratePublicEndpointAccessibleWithoutAuth(client);
            demonstratePrivateEndpointRequiresAuth(client);
            demonstratePrivateEndpointWithValidCredentials(client);
        }
    }

    private static void demonstratePublicEndpointAccessibleWithoutAuth(WebClient client) {
        System.out.println("\n--- GET /publiczny - BEZ uwierzytelnienia (permitAll) ---");
        String odpowiedz = client.get().uri("/publiczny").retrieve().bodyToMono(String.class).block();
        System.out.println("Odpowiedz: " + odpowiedz);
        assertThat(odpowiedz).contains("Kazdy moze");
    }

    private static void demonstratePrivateEndpointRequiresAuth(WebClient client) {
        System.out.println("\n--- GET /prywatny - BEZ uwierzytelnienia -> 401 ---");
        try {
            client.get().uri("/prywatny").retrieve().bodyToMono(String.class).block();
        } catch (WebClientResponseException.Unauthorized e) {
            System.out.println("Zlapano 401 (oczekiwane): " + e.getStatusCode());
        }
    }

    private static void demonstratePrivateEndpointWithValidCredentials(WebClient client) {
        System.out.println("\n--- GET /prywatny - Z Basic Auth (kasia:tajne123) -> 200 ---");
        String basicAuth = Base64.getEncoder().encodeToString("kasia:tajne123".getBytes());

        String odpowiedz = client.get()
                .uri("/prywatny")
                .header("Authorization", "Basic " + basicAuth)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Odpowiedz: " + odpowiedz);
        assertThat(odpowiedz).contains("Tylko zalogowany");
    }
}
