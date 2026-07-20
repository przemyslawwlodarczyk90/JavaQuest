package com.example.javaquest._29_spring_reactive.Lesson12_WebClientDeepDive;

import com.sun.net.httpserver.HttpServer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson12_WebClientDeepDive {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: WebClient w pelni reaktywnie - BEZ .block() ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_22_spring_web/Lesson17`
         * ============================================================
         * `_22_spring_web/Lesson17` pokazal `WebClient` UZYTY
         * Z `.block()` - czyli JAKO ZWYKLY, SYNCHRONICZNY klient
         * (dokladnie TAK, jak w tej lekcji dotad, dla WYGODY
         * weryfikacji W `main()`). TA lekcja pokazuje PRAWDZIWY,
         * REAKTYWNY styl uzycia - subskrypcja `.subscribe()`
         * Z callbackami, BEZ blokowania watku wywolujacego.
         */
        System.out.println("Kontrast Z `_22_spring_web/Lesson17`: TAM WebClient + .block() (synchronicznie). TU: prawdziwy styl reaktywny, BEZ .block().");

        // Wieloutkowy executor DLA serwera testowego - domyslny HttpServer obsluguje zadania
        // NA JEDNYM wewnetrznym watku, co SERIALIZOWALOBY nasze rownolegle wywolania klienta.
        // KRYTYCZNE: HttpServer.stop() NIE zamyka executora dostarczonego PRZEZ setExecutor() -
        // NIEDAEMONOWE watki puli TRZYMALYBY JVM przy zyciu W NIESKONCZONOSC (proces NIGDY by
        // sie nie zakonczyl) - MUSIMY jawnie wywolac shutdown() W finally.
        java.util.concurrent.ExecutorService serwerowyExecutor = java.util.concurrent.Executors.newFixedThreadPool(10);
        HttpServer serwerTestowy = uruchomLokalnyServerTestowy(serwerowyExecutor);
        try {
            int port = serwerTestowy.getAddress().getPort();
            WebClient client = WebClient.builder()
                    .baseUrl("http://localhost:" + port)
                    .build();

            demonstrateNonBlockingSubscribe(client);
            demonstrateParallelCallsWithoutBlocking(client);
            demonstrateErrorHandlingWithWebClient(client);
            demonstrateExchangeToForFullControl(client);
        } finally {
            serwerTestowy.stop(0);
            serwerowyExecutor.shutdown();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.subscribe(onNext, onError, onComplete)` - PRAWDZIWY
         *   styl reaktywny - watek wywolujacy NIE CZEKA NA wynik.
         * - WIELE wywolan `WebClient` MOZNA uruchomic ROWNOLEGLE BEZ
         *   pul watkow - Netty event-loop OBSLUGUJE je NATYWNIE.
         * - `.onStatus(...)`/`retrieve().bodyToMono(...).onErrorResume(...)`
         *   - obsluga bledow HTTP (4xx/5xx) W reaktywnym stylu.
         * - `.exchangeToMono(...)` - PELNA kontrola NAD odpowiedzia
         *   (status+naglowki+cialo RAZEM), gdy `retrieve()` NIE
         *   wystarcza.
         * - W REALNYM kodzie kontrolera WebFlux (Lesson10), WYNIK
         *   `WebClient` jest ZWRACANY BEZPOSREDNIO JAKO `Mono`/`Flux`
         *   Z metody kontrolera - NIGDY `.block()`.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static HttpServer uruchomLokalnyServerTestowy(java.util.concurrent.ExecutorService executor) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/dane", exchange -> {
            byte[] odpowiedz = "{\"wiadomosc\":\"Witaj reaktywnie!\"}".getBytes();
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, odpowiedz.length);
            exchange.getResponseBody().write(odpowiedz);
            exchange.close();
        });
        server.createContext("/wolne", exchange -> {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ignored) {
            }
            byte[] odpowiedz = "wolna odpowiedz".getBytes();
            exchange.sendResponseHeaders(200, odpowiedz.length);
            exchange.getResponseBody().write(odpowiedz);
            exchange.close();
        });
        server.createContext("/blad", exchange -> {
            byte[] odpowiedz = "Blad serwera".getBytes();
            exchange.sendResponseHeaders(500, odpowiedz.length);
            exchange.getResponseBody().write(odpowiedz);
            exchange.close();
        });
        server.setExecutor(executor);
        server.start();
        return server;
    }

    private static void demonstrateNonBlockingSubscribe(WebClient client) throws InterruptedException {
        System.out.println("\n--- .subscribe() - watek wywolujacy NIE CZEKA na wynik ---");

        // Rozgrzewka - PIERWSZE wywolanie Netty (bootstrap event-loop/connection pool) MA narzut
        // kilkudziesieciu-kilkuset ms, NIEZWIAZANY Z reaktywnoscia samą w sobie - odejmujemy go,
        // zeby pomiar PONIZEJ pokazywal WLASCIWY koszt subscribe() (blisko zera).
        client.get().uri("/dane").retrieve().bodyToMono(String.class).block();

        CountDownLatch zakonczono = new CountDownLatch(1);
        AtomicReference<String> wynik = new AtomicReference<>();

        long startCzasu = System.currentTimeMillis();
        client.get()
                .uri("/dane")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(odpowiedz -> {
                    wynik.set(odpowiedz);
                    System.out.println("  onNext (NA watku Netty): " + odpowiedz);
                    zakonczono.countDown();
                });

        System.out.println("subscribe() ZWROCIL NATYCHMIAST (czas: " + (System.currentTimeMillis() - startCzasu) + "ms) - watek glowny WOLNY, MOZE robic cos innego, ZANIM odpowiedz przyjdzie.");

        boolean naCzas = zakonczono.await(5, TimeUnit.SECONDS);
        System.out.println("Wynik po oczekiwaniu NA callback: " + wynik.get());

        assertThat(naCzas).isTrue();
        assertThat(wynik.get()).contains("Witaj reaktywnie");
    }

    private static void demonstrateParallelCallsWithoutBlocking(WebClient client) {
        System.out.println("\n--- Rownolegle wywolania BEZ puli watkow - Netty event-loop OBSLUGUJE je natywnie ---");

        long start = System.currentTimeMillis();
        List<String> wyniki = Flux.range(1, 5)
                .flatMap(i -> client.get().uri("/wolne").retrieve().bodyToMono(String.class))
                .collectList()
                .block(); // .block() TYLKO tu, do weryfikacji w main()

        long czas = System.currentTimeMillis() - start;
        System.out.println("5 zadan (kazde ~30ms) PRZEZ flatMap -> " + wyniki.size() + " odpowiedzi, czas: " + czas + "ms");
        System.out.println("Wszystkie 5 zadan wykonalo sie ROWNOLEGLE NA MALEJ liczbie watkow event-loop (Lesson08), NIE potrzeba 5 osobnych watkow platformowych.");

        assertThat(czas).isLessThan(200);
    }

    private static void demonstrateErrorHandlingWithWebClient(WebClient client) {
        System.out.println("\n--- Obsluga bledow HTTP (4xx/5xx) w stylu reaktywnym ---");

        String wynik = client.get()
                .uri("/blad")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    System.out.println("Zlapano blad HTTP: " + e.getStatusCode() + " - odpowiedz zastepcza.");
                    return Mono.just("WARTOSC ZASTEPCZA");
                })
                .block();

        System.out.println("Wynik: " + wynik);
        assertThat(wynik).isEqualTo("WARTOSC ZASTEPCZA");
    }

    private static void demonstrateExchangeToForFullControl(WebClient client) {
        System.out.println("\n--- exchangeToMono - pelna kontrola nad odpowiedzia (status+naglowki+cialo) ---");

        String wynik = client.get()
                .uri("/dane")
                .exchangeToMono(response -> {
                    System.out.println("Kod statusu: " + response.statusCode());
                    System.out.println("Content-Type: " + response.headers().contentType().orElse(null));
                    return response.bodyToMono(String.class);
                })
                .block();

        System.out.println("Cialo: " + wynik);
        assertThat(wynik).contains("Witaj reaktywnie");
    }
}
