package com.example.javaquest._29_spring_reactive.Lesson11_FunctionalEndpointsRouterFunction;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class _Lesson11_FunctionalEndpointsRouterFunction {

    record Zadanie(String id, String tytul, boolean gotowe) {
    }

    static class ZadanieHandler {
        private final Map<String, Zadanie> baza = new ConcurrentHashMap<>();

        ZadanieHandler() {
            baza.put("Z1", new Zadanie("Z1", "Napisac lekcje", false));
            baza.put("Z2", new Zadanie("Z2", "Przetestowac kod", true));
        }

        Mono<ServerResponse> wszystkie(ServerRequest request) {
            return ServerResponse.ok().body(reactor.core.publisher.Flux.fromIterable(baza.values()), Zadanie.class);
        }

        Mono<ServerResponse> jedno(ServerRequest request) {
            String id = request.pathVariable("id");
            Zadanie zadanie = baza.get(id);
            return zadanie != null
                    ? ServerResponse.ok().bodyValue(zadanie)
                    : ServerResponse.notFound().build();
        }

        Mono<ServerResponse> utworz(ServerRequest request) {
            return request.bodyToMono(Zadanie.class)
                    .doOnNext(z -> baza.put(z.id(), z))
                    .flatMap(z -> ServerResponse.status(201).bodyValue(z));
        }

        Mono<ServerResponse> usun(ServerRequest request) {
            String id = request.pathVariable("id");
            boolean istnialo = baza.remove(id) != null;
            return istnialo ? ServerResponse.noContent().build() : ServerResponse.notFound().build();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class ReactiveApp {
        @Bean
        ZadanieHandler zadanieHandler() {
            return new ZadanieHandler();
        }

        @Bean
        RouterFunction<ServerResponse> routerFunction(ZadanieHandler handler) {
            return route()
                    .GET("/api/zadania", accept(MediaType.APPLICATION_JSON), handler::wszystkie)
                    .GET("/api/zadania/{id}", handler::jedno)
                    .POST("/api/zadania", handler::utworz)
                    .DELETE("/api/zadania/{id}", handler::usun)
                    .build();
        }

        @Bean
        ReactiveWebServerFactory reactiveWebServerFactory() {
            return new NettyReactiveWebServerFactory();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: Functional Endpoints - RouterFunction/HandlerFunction (alternatywa dla @RestController) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - styl FUNKCYJNY zamiast adnotacji
         * ============================================================
         * Spring WebFlux oferuje DRUGI, ALTERNATYWNY styl routingu -
         * BEZ adnotacji, PRZEZ jawne LAMBDY/referencje metod:
         * `RouterFunction<ServerResponse>` (MAPUJE zadanie NA
         * `HandlerFunction`) I `HandlerFunction<ServerResponse>`
         * (PRZETWARZA `ServerRequest` NA `Mono<ServerResponse>`).
         *
         * ZALETY wobec `@RestController`: (1) CALY routing widoczny
         * W JEDNYM miejscu (`RouterFunction`), (2) LATWIEJSZE
         * testowanie (funkcje = latwiejsze mockowanie NIZ
         * refleksyjne skanowanie adnotacji), (3) BRAK "magii"
         * adnotacji - kod JEST DOKLADNIE tym, co widac.
         * WADA: WIECEJ boilerplate'u DLA prostych przypadkow.
         */
        System.out.println("RouterFunction/HandlerFunction - styl funkcyjny (BEZ adnotacji), alternatywa dla @RestController.");

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ReactiveApp.class)
                .web(WebApplicationType.REACTIVE)
                .properties("server.port=0", "logging.level.root=WARN")
                .run()) {

            Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            WebClient client = WebClient.create("http://localhost:" + port);

            demonstrateGetAll(client);
            demonstrateGetOne(client);
            demonstratePost(client);
            demonstrateDelete(client);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `RouterFunctions.route()` (builder) LUB
         *   `RouterFunctions.route(predicate, handler)` - MAPUJE
         *   PREDYKAT (sciezka+metoda) NA `HandlerFunction`.
         * - `RequestPredicates` - `GET(...)`, `POST(...)`, `accept(...)`
         *   - budowanie WARUNKOW dopasowania (podobne DO
         *   `@GetMapping`/`@RequestMapping(produces=...)`).
         * - `ServerRequest`/`ServerResponse` - NISKOPOZIOMOWE API
         *   (odpowiednik `HttpServletRequest`/`Response`, ale
         *   REAKTYWNE) - `request.pathVariable(...)`,
         *   `request.bodyToMono(...)`, `ServerResponse.ok().bodyValue(...)`.
         * - Zastosowanie W PRAKTYCE: CZESTO LACZONE Z `@RestController`
         *   W TYM SAMYM projekcie (jeden styl DLA prostych CRUD,
         *   drugi DLA bardziej ZLOZONEGO routingu).
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateGetAll(WebClient client) {
        System.out.println("\n--- GET /api/zadania - RouterFunction.GET(...) -> handler::wszystkie ---");
        var wynik = client.get().uri("/api/zadania").retrieve().bodyToFlux(Zadanie.class).collectList().block();
        System.out.println("Wynik: " + wynik);
        assertThat(wynik).hasSize(2);
    }

    private static void demonstrateGetOne(WebClient client) {
        System.out.println("\n--- GET /api/zadania/Z1 - handler::jedno, request.pathVariable(\"id\") ---");
        Zadanie zadanie = client.get().uri("/api/zadania/Z1").retrieve().bodyToMono(Zadanie.class).block();
        System.out.println("Wynik: " + zadanie);
        assertThat(zadanie.tytul()).isEqualTo("Napisac lekcje");
    }

    private static void demonstratePost(WebClient client) {
        System.out.println("\n--- POST /api/zadania - handler::utworz, request.bodyToMono(Zadanie.class) ---");
        Zadanie nowe = new Zadanie("Z3", "Opublikowac kurs", false);
        Zadanie odpowiedz = client.post().uri("/api/zadania").bodyValue(nowe).retrieve().bodyToMono(Zadanie.class).block();
        System.out.println("Utworzono: " + odpowiedz);
        assertThat(odpowiedz).isEqualTo(nowe);
    }

    private static void demonstrateDelete(WebClient client) {
        System.out.println("\n--- DELETE /api/zadania/Z2 - handler::usun, ServerResponse.noContent() ---");
        client.delete().uri("/api/zadania/Z2").retrieve().toBodilessEntity().block();
        var pozostale = client.get().uri("/api/zadania").retrieve().bodyToFlux(Zadanie.class).collectList().block();
        System.out.println("Pozostale zadania PO usunieciu Z2: " + pozostale);
        assertThat(pozostale).extracting(Zadanie::id).doesNotContain("Z2");
    }
}
