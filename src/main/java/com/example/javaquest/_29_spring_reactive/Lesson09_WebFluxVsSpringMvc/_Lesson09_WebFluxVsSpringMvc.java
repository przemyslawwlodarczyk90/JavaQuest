package com.example.javaquest._29_spring_reactive.Lesson09_WebFluxVsSpringMvc;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson09_WebFluxVsSpringMvc {

    @RestController
    static class PowitanieController {
        @GetMapping("/powitanie")
        public Mono<String> powitanie() {
            return Mono.just("Witaj z Spring WebFlux (Netty)!")
                    .doOnNext(x -> System.out.println("  Kontroler wykonuje sie NA watku: " + Thread.currentThread().getName()));
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class ReactiveApp {
        @Bean
        PowitanieController powitanieController() {
            return new PowitanieController();
        }

        // Jawnie WYMUSZAMY Netty - zweryfikowano EMPIRYCZNIE, ze na TYM projekcie (spring-boot-
        // starter-web I -webflux OBA na classpath) auto-konfiguracja domyslnie wybiera TOMCAT
        // (jego reaktywny adapter), NIE Netty, mimo obecnosci reactor-netty. Bez tego beana
        // nazwa watku w kontrolerze bylaby "http-nio-...", NIE "reactor-http-nio-...".
        @Bean
        ReactiveWebServerFactory reactiveWebServerFactory() {
            return new NettyReactiveWebServerFactory();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: WebFlux vs Spring MVC - Netty vs Tomcat, event-loop vs thread-per-request ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - PIERWSZA lekcja Z REALNYM Spring WebFlux
         * ============================================================
         * `_22_spring_web` uczyl Spring MVC (`@RestController` NA
         * embedded Tomcacie, model "watek NA zadanie" - `_07_servlets`).
         * Spring WebFlux TO REAKTYWNY odpowiednik: TE SAME adnotacje
         * (`@RestController`, `@GetMapping`), ale WEWNATRZ dziala
         * INNY silnik - domyslnie embedded NETTY (event-loop, MALA
         * liczba watkow), NIE Tomcat.
         *
         * WAZNE: projekt MA JUZ `spring-boot-starter-web` (SERVLET)
         * NA classpath OBOK `spring-boot-starter-webflux` (jak
         * ustalono W `_22_spring_web/Lesson17`) - Spring Boot WYBIERA
         * `SERVLET` domyslnie, GDY OBA sa obecne. TA lekcja WYMUSZA
         * `WebApplicationType.REACTIVE` jawnie
         * (`SpringApplicationBuilder.web(WebApplicationType.REACTIVE)`).
         *
         * DRUGA, ZASKAKUJACA pulapka odkryta EMPIRYCZNIE: nawet PO
         * wymuszeniu `WebApplicationType.REACTIVE`, Spring Boot NA
         * TYM classpath (Tomcat OBECNY przez `spring-boot-starter-
         * web`, reactor-netty OBECNY przez `-webflux`) wybral
         * TOMCAT (JEGO reaktywny adapter), NIE Netty - nazwa watku
         * W kontrolerze byla "http-nio-...", NIE "reactor-http-nio-...".
         * Naprawione przez JAWNY bean `ReactiveWebServerFactory`
         * zwracajacy `NettyReactiveWebServerFactory` - dopiero WTEDY
         * demo faktycznie uzywa Netty. Zasada NA PRZYSZLOSC: NIE
         * ZAKLADAJ, ktory serwer WYGRA, GDY WIELE implementacji jest
         * NA classpath - zweryfikuj (np. nazwa watku W logu), tak
         * jak wielokrotnie ustalono W INNYCH rozdzialach TEGO kursu.
         */
        System.out.println("Spring WebFlux - REAKTYWNY odpowiednik Spring MVC. Domyslnie Netty (event-loop) zamiast Tomcat (watek na zadanie).");

        demonstrateForcingReactiveWebApplicationType();
        explainThreadModelDifference();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spring MVC (`_22_spring_web`): `@RestController` zwraca
         *   `ResponseEntity<T>`/`T` BEZPOSREDNIO - embedded Tomcat,
         *   MODEL "watek NA zadanie" (setki watkow, KAZDY BLOKUJE
         *   sie CZEKAJAC NA odpowiedz).
         * - Spring WebFlux (TA lekcja): `@RestController` zwraca
         *   `Mono<T>`/`Flux<T>` - embedded Netty (domyslnie), MODEL
         *   event-loop (MALO watkow, ZADEN nie BLOKUJE).
         * - `WebApplicationType.SERVLET` WYGRYWA domyslnie, GDY OBA
         *   startery SA na classpath - trzeba go JAWNIE
         *   NADPISAC DLA demo reaktywnego.
         * - Pelne kontrolery reaktywne: Lesson10.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateForcingReactiveWebApplicationType() {
        System.out.println("\n--- Wymuszenie WebApplicationType.REACTIVE (domyslnie wygrywa SERVLET, bo web+webflux SA OBA na classpath) ---");

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ReactiveApp.class)
                .web(WebApplicationType.REACTIVE)
                .properties("server.port=0", "logging.level.root=WARN")
                .run()) {

            String typAplikacji = context.getEnvironment().getProperty("spring.main.web-application-type", "REACTIVE (wymuszone)");
            System.out.println("Kontekst uruchomiony JAKO: " + typAplikacji);

            Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("Serwer (Netty) nasluchuje NA porcie: " + port);

            WebClient client = WebClient.create("http://localhost:" + port);
            String odpowiedz = client.get()
                    .uri("/powitanie")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("GET /powitanie -> " + odpowiedz);
            assertThat(odpowiedz).contains("Netty");
        }
    }

    private static void explainThreadModelDifference() {
        System.out.println("\n--- Roznica modelu watkow: Tomcat (thread-per-request) vs Netty (event-loop) ---");
        System.out.println("Tomcat (Spring MVC, _07_servlets): domyslnie ~200 watkow W puli - KAZDE zadanie HTTP ZAJMUJE 1 watek NA CALY czas przetwarzania (WLACZNIE Z oczekiwaniem NA baze/API zewnetrzne).");
        System.out.println("Netty (Spring WebFlux, TA lekcja): domyslnie TYLKO tyle watkow event-loop, ILE rdzeni CPU (Lesson08: Schedulers.parallel()) - KAZDY watek OBSLUGUJE TYSIACE polaczen naraz, bo ZADEN blokujacy kod nie jest wykonywany NA tych watkach.");
        System.out.println("Konsekwencja: WebFlux MOZE obsluzyc WIECEJ jednoczesnych polaczen NA TEJ SAMEJ maszynie - ALE TYLKO, jesli CALY kod (kontroler, serwis, baza danych) jest REAKTYWNY end-to-end (Lesson13: R2DBC) - MIESZANIE Z blokujacym JDBC WYMAGA Schedulers.boundedElastic() (Lesson08), inaczej TRACISZ zalety WebFlux.");
    }
}
