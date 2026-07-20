package com.example.javaquest._29_spring_reactive.Lesson10_AnnotatedControllersInWebFlux;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson10_AnnotatedControllersInWebFlux {

    record Ksiazka(String id, String tytul, String autor) {
    }

    @RestController
    @RequestMapping("/api/ksiazki")
    static class KsiazkaController {
        private final Map<String, Ksiazka> baza = new ConcurrentHashMap<>();

        KsiazkaController() {
            baza.put("K1", new Ksiazka("K1", "Wiedzmin", "Sapkowski"));
            baza.put("K2", new Ksiazka("K2", "Solaris", "Lem"));
        }

        @GetMapping
        public Flux<Ksiazka> wszystkie() {
            return Flux.fromIterable(baza.values());
        }

        @GetMapping("/{id}")
        public Mono<ResponseEntity<Ksiazka>> jedna(@PathVariable String id) {
            Ksiazka ksiazka = baza.get(id);
            return ksiazka != null
                    ? Mono.just(ResponseEntity.ok(ksiazka))
                    : Mono.just(ResponseEntity.notFound().build());
        }

        @PostMapping
        public Mono<ResponseEntity<Ksiazka>> dodaj(@RequestBody Mono<Ksiazka> nowaKsiazka) {
            return nowaKsiazka.map(k -> {
                baza.put(k.id(), k);
                return ResponseEntity.status(HttpStatus.CREATED).body(k);
            });
        }

        @GetMapping(value = "/strumien", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Ksiazka> strumien() {
            return Flux.fromIterable(baza.values()).delayElements(Duration.ofMillis(20));
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class ReactiveApp {
        @Bean
        KsiazkaController ksiazkaController() {
            return new KsiazkaController();
        }

        @Bean
        ReactiveWebServerFactory reactiveWebServerFactory() {
            return new NettyReactiveWebServerFactory();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: Kontrolery adnotowane w WebFlux - @RestController zwracajacy Mono/Flux ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - kontrolery @RestController W WebFlux
         * ============================================================
         * SKLADNIA jest IDENTYCZNA Z `_22_spring_web` (TE SAME
         * adnotacje: `@RestController`, `@GetMapping`, `@PathVariable`,
         * `@RequestBody`) - JEDYNA roznica: TYPY ZWRACANE. Spring MVC
         * zwraca `T`/`ResponseEntity<T>` BEZPOSREDNIO, Spring WebFlux
         * zwraca `Mono<T>`/`Flux<T>` - Spring SAM subskrybuje sie DO
         * tych typow I obsluguje serializacje/pisanie DO odpowiedzi
         * ASYNCHRONICZNIE, W MIARE JAK dane sa GOTOWE.
         */
        System.out.println("Skladnia IDENTYCZNA z Spring MVC (_22_spring_web) - jedyna roznica: typy zwracane (Mono/Flux zamiast T/ResponseEntity<T>).");

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ReactiveApp.class)
                .web(WebApplicationType.REACTIVE)
                .properties("server.port=0", "logging.level.root=WARN")
                .run()) {

            Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            WebClient client = WebClient.create("http://localhost:" + port);

            demonstrateGetCollectionReturningFlux(client);
            demonstrateGetSingleReturningMono(client);
            demonstratePostWithRequestBodyMono(client);
            demonstrateServerSentEvents(client);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Flux<T>` JAKO typ zwracany metody `@GetMapping` - Spring
         *   SAM streamuje elementy DO klienta W MIARE ICH gotowosci.
         * - `Mono<ResponseEntity<T>>` - PELNA kontrola statusu/naglowkow
         *   (jak `_22_spring_web/Lesson06`), ale ASYNCHRONICZNIE.
         * - `@RequestBody Mono<T>` - cialo zadania SAMO jest `Mono`
         *   (deserializacja JSON ASYNCHRONICZNA, NIE blokuje watku).
         * - `produces = MediaType.TEXT_EVENT_STREAM_VALUE` - Server-
         *   Sent Events, `Flux` Z `delayElements` streamuje dane
         *   W CZASIE (WLASCIWY use case DLA WebFlux: dane strumieniowe).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void demonstrateGetCollectionReturningFlux(WebClient client) {
        System.out.println("\n--- GET /api/ksiazki - kontroler zwraca Flux<Ksiazka> ---");
        List<Ksiazka> ksiazki = client.get()
                .uri("/api/ksiazki")
                .retrieve()
                .bodyToFlux(Ksiazka.class)
                .collectList()
                .block();

        System.out.println("Wynik: " + ksiazki);
        assertThat(ksiazki).hasSize(2);
    }

    private static void demonstrateGetSingleReturningMono(WebClient client) {
        System.out.println("\n--- GET /api/ksiazki/K1 - kontroler zwraca Mono<ResponseEntity<Ksiazka>> ---");
        Ksiazka ksiazka = client.get()
                .uri("/api/ksiazki/K1")
                .retrieve()
                .bodyToMono(Ksiazka.class)
                .block();

        System.out.println("Wynik: " + ksiazka);
        assertThat(ksiazka.tytul()).isEqualTo("Wiedzmin");

        System.out.println("--- GET /api/ksiazki/NIEISTNIEJACA - 404 (ResponseEntity.notFound()) ---");
        try {
            client.get().uri("/api/ksiazki/NIEISTNIEJACA").retrieve().bodyToMono(Ksiazka.class).block();
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException.NotFound e) {
            System.out.println("Zlapano 404 (oczekiwane): " + e.getStatusCode());
        }
    }

    private static void demonstratePostWithRequestBodyMono(WebClient client) {
        System.out.println("\n--- POST /api/ksiazki - @RequestBody Mono<Ksiazka> ---");
        Ksiazka nowa = new Ksiazka("K3", "Diuna", "Herbert");

        Ksiazka odpowiedz = client.post()
                .uri("/api/ksiazki")
                .bodyValue(nowa)
                .retrieve()
                .bodyToMono(Ksiazka.class)
                .block();

        System.out.println("Utworzono: " + odpowiedz);
        assertThat(odpowiedz).isEqualTo(nowa);
    }

    private static void demonstrateServerSentEvents(WebClient client) {
        System.out.println("\n--- GET /api/ksiazki/strumien - Server-Sent Events (text/event-stream) ---");
        List<Ksiazka> zStrumienia = client.get()
                .uri("/api/ksiazki/strumien")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Ksiazka.class)
                .collectList()
                .block();

        System.out.println("Elementy Z strumienia (kazdy Z opoznieniem 20ms): " + zStrumienia);
        System.out.println("(3 elementy - K3 z POPRZEDNIEGO demo POST juz jest W bazie, ten sam mutowalny stan Map W kontrolerze.)");
        assertThat(zStrumienia).hasSize(3);
    }
}
