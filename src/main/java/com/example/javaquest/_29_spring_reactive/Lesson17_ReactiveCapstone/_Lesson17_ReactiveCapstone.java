package com.example.javaquest._29_spring_reactive.Lesson17_ReactiveCapstone;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson17_ReactiveCapstone {

    record Ksiazka(Long id, String tytul, String autor) {
    }

    @RestController
    @RequestMapping("/api/ksiazki")
    static class KsiazkaController {
        private final DatabaseClient databaseClient;

        KsiazkaController(DatabaseClient databaseClient) {
            this.databaseClient = databaseClient;
        }

        @GetMapping
        public Flux<Ksiazka> wszystkie() {
            return databaseClient.sql("SELECT id, tytul, autor FROM ksiazki ORDER BY id")
                    .map((row, meta) -> new Ksiazka(row.get("id", Long.class), row.get("tytul", String.class), row.get("autor", String.class)))
                    .all();
        }

        @GetMapping("/{id}")
        public Mono<Ksiazka> jedna(@PathVariable long id) {
            return databaseClient.sql("SELECT id, tytul, autor FROM ksiazki WHERE id = :id")
                    .bind("id", id)
                    .map((row, meta) -> new Ksiazka(row.get("id", Long.class), row.get("tytul", String.class), row.get("autor", String.class)))
                    .one();
        }

        @PostMapping
        public Mono<Ksiazka> utworz(@RequestBody Mono<Ksiazka> nowaKsiazka) {
            return nowaKsiazka.flatMap(k ->
                    databaseClient.sql("INSERT INTO ksiazki (tytul, autor) VALUES (:tytul, :autor)")
                            .bind("tytul", k.tytul())
                            .bind("autor", k.autor())
                            .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                            .map((row, meta) -> row.get("id", Long.class))
                            .one()
                            .map(nowyId -> new Ksiazka(nowyId, k.tytul(), k.autor())));
        }

        @DeleteMapping("/{id}")
        public Mono<Void> usun(@PathVariable long id) {
            return databaseClient.sql("DELETE FROM ksiazki WHERE id = :id")
                    .bind("id", id)
                    .then();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableWebFluxSecurity
    static class ReactiveCapstoneApp {
        @Bean
        ConnectionFactory connectionFactory() {
            return ConnectionFactories.get("r2dbc:h2:mem:///lesson17capstone;DB_CLOSE_DELAY=-1");
        }

        @Bean
        KsiazkaController ksiazkaController(DatabaseClient databaseClient) {
            return new KsiazkaController(databaseClient);
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
            var bibliotekarz = User.withUsername("bibliotekarz")
                    .password(encoder.encode("sekret123"))
                    .roles("ADMIN")
                    .build();
            return new MapReactiveUserDetailsService(bibliotekarz);
        }

        @Bean
        SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
            return http
                    .authorizeExchange(exchange -> exchange
                            .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/ksiazki/**").permitAll()
                            .anyExchange().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .build();
        }

        @Bean
        org.springframework.boot.CommandLineRunner initSchema(DatabaseClient databaseClient) {
            return args -> databaseClient.sql(
                            "CREATE TABLE ksiazki (id BIGINT AUTO_INCREMENT PRIMARY KEY, tytul VARCHAR(200), autor VARCHAR(200))")
                    .then()
                    .block();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17 (KAPSZTON): JavaQuest Reactive Bookshelf - WebFlux + R2DBC + Security ===");

        /*
         * ============================================================
         * 🏁 KAPSZTON ROZDZIALU "_29_spring_reactive"
         * ============================================================
         * Ten kapszton LACZY WSZYSTKIE mechanizmy poznane W tym
         * rozdziale W JEDNYM, DZIALAJACYM mini-API "Reactive Bookshelf":
         * - `@RestController` zwracajacy `Mono`/`Flux` NA Netty
         *   (Lesson09-10),
         * - `DatabaseClient` NAD R2DBC (Lesson13) DO PRAWDZIWEGO
         *   dostepu DO bazy H2 in-memory, W PELNI reaktywnie,
         * - `SecurityWebFilterChain` (Lesson14) chroniacy operacje
         *   ZAPISU (POST/DELETE), publiczny odczyt (GET),
         * - `StepVerifier` (Lesson15) DO bezposredniej weryfikacji
         *   pipeline'u reaktywnego (BEZ przechodzenia przez HTTP).
         *
         * 6 scenariuszy W `main()` demonstruje PELNY cykl zycia:
         * publiczny odczyt, proba zapisu BEZ auth (401), zapis Z auth
         * (201), odczyt PO zapisie, usuniecie BEZ auth (401),
         * usuniecie Z auth (204).
         */
        System.out.println("Laczy: WebFlux kontrolery (Lesson09-10) + R2DBC/DatabaseClient (Lesson13) + SecurityWebFilterChain (Lesson14) + StepVerifier (Lesson15).");

        System.setProperty("spring.autoconfigure.exclude", "");
        try {
            runCapstoneDemo();
        } finally {
            System.clearProperty("spring.autoconfigure.exclude");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU "_29_spring_reactive"
         * ============================================================
         * Rozdzial przeszedl droge OD teorii (Lesson01-02: C10K,
         * Reactive Streams) PRZEZ Project Reactor (Lesson03-08) DO
         * pelnego stosu Spring WebFlux (Lesson09-15) I ZAKONCZYL
         * PRAKTYCZNA decyzja architektoniczna (Lesson16): reaktywnosc
         * NIE JEST "zawsze lepszym wyborem" - TO NARZEDZIE DO
         * KONKRETNYCH problemow (wysoka wspolbieznosc I/O-bound,
         * streaming, agregacja gateway).
         *
         * Nastepny rozdzial (`_30_spring_messaging_and_async`) buduje
         * NA idei asynchronicznosci Z INNEJ strony: `@Async`/
         * `@Scheduled`/kolejki wiadomosci (RabbitMQ/Kafka).
         */
        System.out.println("\n=== KONIEC LEKCJI 17 I ROZDZIALU _29_spring_reactive ===");
    }

    private static void runCapstoneDemo() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ReactiveCapstoneApp.class)
                .web(WebApplicationType.REACTIVE)
                .properties("server.port=0", "logging.level.root=WARN")
                .run()) {

            Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            WebClient client = WebClient.create("http://localhost:" + port);
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString("bibliotekarz:sekret123".getBytes());

            demonstrateScenario1PublicReadEmpty(client);
            long nowyId = demonstrateScenario2WriteWithoutAuthFails(client);
            long utworzonyId = demonstrateScenario3WriteWithAuthSucceeds(client, basicAuth);
            demonstrateScenario4ReadAfterWrite(client);
            demonstrateScenario5DeleteWithoutAuthFails(client, utworzonyId);
            demonstrateScenario6DeleteWithAuthSucceeds(client, basicAuth, utworzonyId);
            demonstrateStepVerifierOnDirectPipeline(context);
        }
    }

    private static void demonstrateScenario1PublicReadEmpty(WebClient client) {
        System.out.println("\n--- Scenariusz 1: GET /api/ksiazki (publiczne, PUSTA biblioteka) ---");
        List<Ksiazka> ksiazki = client.get().uri("/api/ksiazki").retrieve().bodyToFlux(Ksiazka.class).collectList().block();
        System.out.println("Wynik: " + ksiazki);
        assertThat(ksiazki).isEmpty();
    }

    private static long demonstrateScenario2WriteWithoutAuthFails(WebClient client) {
        System.out.println("\n--- Scenariusz 2: POST /api/ksiazki BEZ auth -> 401 ---");
        try {
            client.post().uri("/api/ksiazki").bodyValue(new Ksiazka(null, "Wiedzmin", "Sapkowski")).retrieve().bodyToMono(Ksiazka.class).block();
        } catch (WebClientResponseException.Unauthorized e) {
            System.out.println("Zlapano 401 (oczekiwane): " + e.getStatusCode());
        }
        return -1;
    }

    private static long demonstrateScenario3WriteWithAuthSucceeds(WebClient client, String basicAuth) {
        System.out.println("\n--- Scenariusz 3: POST /api/ksiazki Z auth -> 201 ---");
        Ksiazka utworzona = client.post()
                .uri("/api/ksiazki")
                .header("Authorization", basicAuth)
                .bodyValue(new Ksiazka(null, "Wiedzmin", "Sapkowski"))
                .retrieve()
                .bodyToMono(Ksiazka.class)
                .block();
        System.out.println("Utworzono: " + utworzona);
        assertThat(utworzona.id()).isNotNull();
        return utworzona.id();
    }

    private static void demonstrateScenario4ReadAfterWrite(WebClient client) {
        System.out.println("\n--- Scenariusz 4: GET /api/ksiazki PO zapisie ---");
        List<Ksiazka> ksiazki = client.get().uri("/api/ksiazki").retrieve().bodyToFlux(Ksiazka.class).collectList().block();
        System.out.println("Wynik: " + ksiazki);
        assertThat(ksiazki).hasSize(1);
    }

    private static void demonstrateScenario5DeleteWithoutAuthFails(WebClient client, long id) {
        System.out.println("\n--- Scenariusz 5: DELETE /api/ksiazki/" + id + " BEZ auth -> 401 ---");
        try {
            client.delete().uri("/api/ksiazki/" + id).retrieve().toBodilessEntity().block();
        } catch (WebClientResponseException.Unauthorized e) {
            System.out.println("Zlapano 401 (oczekiwane): " + e.getStatusCode());
        }
    }

    private static void demonstrateScenario6DeleteWithAuthSucceeds(WebClient client, String basicAuth, long id) {
        System.out.println("\n--- Scenariusz 6: DELETE /api/ksiazki/" + id + " Z auth -> 204 ---");
        client.delete().uri("/api/ksiazki/" + id).header("Authorization", basicAuth).retrieve().toBodilessEntity().block();

        List<Ksiazka> ksiazki = client.get().uri("/api/ksiazki").retrieve().bodyToFlux(Ksiazka.class).collectList().block();
        System.out.println("Biblioteka PO usunieciu: " + ksiazki);
        assertThat(ksiazki).isEmpty();
    }

    private static void demonstrateStepVerifierOnDirectPipeline(ConfigurableApplicationContext context) {
        System.out.println("\n--- Bonus: StepVerifier (Lesson15) NA bezposrednim pipeline DatabaseClient (BEZ HTTP) ---");
        DatabaseClient databaseClient = context.getBean(DatabaseClient.class);

        Mono<Long> wstawienieINowyId = databaseClient.sql("INSERT INTO ksiazki (tytul, autor) VALUES (:tytul, :autor)")
                .bind("tytul", "Solaris")
                .bind("autor", "Lem")
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                .map((row, meta) -> row.get("id", Long.class))
                .one();

        StepVerifier.create(wstawienieINowyId)
                .expectNextMatches(id -> id != null && id > 0)
                .verifyComplete();

        System.out.println("StepVerifier zweryfikowal BEZPOSREDNIO pipeline DatabaseClient (INSERT + RETURN GENERATED id > 0) - bez posredniczenia HTTP.");
    }
}
