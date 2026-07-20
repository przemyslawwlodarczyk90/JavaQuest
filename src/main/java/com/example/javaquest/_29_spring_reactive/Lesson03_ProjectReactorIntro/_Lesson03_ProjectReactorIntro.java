package com.example.javaquest._29_spring_reactive.Lesson03_ProjectReactorIntro;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson03_ProjectReactorIntro {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: Project Reactor - implementacja uzywana przez Spring (WebFlux) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Project Reactor
         * ============================================================
         * Project Reactor (Pivotal/VMware) TO IMPLEMENTACJA specyfikacji
         * Reactive Streams (Lesson02) - dokladnie TA implementacja,
         * NA KTOREJ zbudowany jest Spring WebFlux (Lesson09+). Reactor
         * dostarcza 2 GLOWNE typy: `Mono<T>` (0 LUB 1 element) I
         * `Flux<T>` (0..N elementow) - OBA IMPLEMENTUJA `Publisher<T>`
         * Z Reactive Streams, ale Z BOGATYM API operatorow (`map`,
         * `filter`, `flatMap`...) ZAMIAST REGCZNEGO implementowania
         * `Subscriber`/`Subscription` (jak W Lesson02).
         *
         * Kluczowe: `Mono`/`Flux` SA LENIWE (lazy) - ZADEN kod NIE
         * WYKONUJE SIE, DOPOKI ktos sie NIE ZASUBSKRYBUJE
         * (`.subscribe()`) - dokladny odpowiednik "nic sie nie dzieje
         * bez `request(n)`" Z Lesson02.
         */
        System.out.println("Project Reactor - implementacja Reactive Streams uzywana przez Spring WebFlux. Mono (0-1) i Flux (0..N) - GLOWNE typy.");

        demonstrateMonoAndFluxAreLazy();
        demonstrateSimpleOperatorChain();
        demonstrateCreatingFromDifferentSources();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Mono<T>` - reprezentuje 0 LUB 1 element (odpowiednik
         *   `Optional`/pojedynczej wartosci asynchronicznej,
         *   POWIAZANIE Z `CompletableFuture<T>` Z `_14_advancedjava`).
         * - `Flux<T>` - reprezentuje 0..N elementow (odpowiednik
         *   `Stream<T>`, ale ASYNCHRONICZNY I LENIWY).
         * - BEZ `.subscribe()` (LUB rownowaznika) ZADEN kod wewnatrz
         *   `Mono`/`Flux` SIE NIE WYKONUJE - "nic sie nie dzieje, dopoki
         *   nikt nie zasubskrybuje" (COLD publisher).
         * - Pelne API operatorow: Lesson06 (`Flux`), Lesson04-05
         *   (podstawy `Mono`/`Flux`).
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateMonoAndFluxAreLazy() {
        System.out.println("\n--- Mono/Flux SA LENIWE - kod wewnatrz NIE WYKONUJE SIE bez subscribe() ---");

        Mono<String> mono = Mono.fromSupplier(() -> {
            System.out.println("  (TO wypisze sie DOPIERO przy subscribe()!)");
            return "Witaj z Mono!";
        });

        System.out.println("Mono.fromSupplier(...) UTWORZONY - ZAUWAZ, ze powyzszy komunikat JESZCZE sie NIE wypisal.");
        System.out.println("Teraz wywolujemy .subscribe():");
        mono.subscribe(wartosc -> System.out.println("  onNext otrzymany W subscribe(): " + wartosc));

        String wynikBlokujacy = mono.block(); // .block() - WYJATEK, uzywany TYLKO tu do weryfikacji AssertJ (NIGDY w prawdziwym kodzie reaktywnym!)
        assertThat(wynikBlokujacy).isEqualTo("Witaj z Mono!");
    }

    private static void demonstrateSimpleOperatorChain() {
        System.out.println("\n--- Prosty lancuch operatorow na Flux (pelne API: Lesson06) ---");
        List<Integer> wynik = Flux.range(1, 10)
                .filter(liczba -> liczba % 2 == 0)
                .map(liczba -> liczba * liczba)
                .collectList()
                .block(); // .block() TYLKO do weryfikacji w main() - normalnie NIGDY nie blokujemy (Lesson16)

        System.out.println("Flux.range(1,10).filter(parzyste).map(kwadrat) -> " + wynik);
        assertThat(wynik).containsExactly(4, 16, 36, 64, 100);
    }

    private static void demonstrateCreatingFromDifferentSources() {
        System.out.println("\n--- Rozne sposoby tworzenia Mono/Flux ---");

        Mono<String> monoJust = Mono.just("pojedyncza wartosc");
        Flux<String> fluxFromIterable = Flux.fromIterable(List.of("a", "b", "c"));
        Flux<Long> fluxInterval = Flux.interval(Duration.ofMillis(10)).take(3);

        System.out.println("Mono.just(...) -> " + monoJust.block());
        System.out.println("Flux.fromIterable(List.of(\"a\",\"b\",\"c\")) -> " + fluxFromIterable.collectList().block());
        System.out.println("Flux.interval(10ms).take(3) -> " + fluxInterval.collectList().block() + " (emituje 0,1,2 co 10ms, take(3) OGRANICZA do 3 elementow)");

        assertThat(fluxFromIterable.collectList().block()).containsExactly("a", "b", "c");
    }
}
