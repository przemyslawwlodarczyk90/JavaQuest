package com.example.javaquest._29_spring_reactive.Lesson15_TestingReactiveCodeWithStepVerifier;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson15_TestingReactiveCodeWithStepVerifier {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: Testowanie kodu reaktywnego - StepVerifier (reactor-test) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - StepVerifier
         * ============================================================
         * Powiazanie Z `_25_unit_testing` - TAM poznane JUnit5/AssertJ
         * do TESTOWANIA zwyklego kodu. `.block()` UZYWANY W tej lekcji
         * DOTAD (do weryfikacji W `main()`) DZIALA, ale UKRYWA
         * INFORMACJE O SEKWENCJI zdarzen (KOLEJNOSC onNext, DOKLADNY
         * sygnal onComplete/onError). `StepVerifier` (Z `reactor-test`)
         * TO WYSPECJALIZOWANE narzedzie TESTOWANIA strumieni
         * reaktywnych KROK PO KROKU - WERYFIKUJE DOKLADNIE, CO I W
         * JAKIEJ kolejnosci zostalo wyemitowane.
         */
        System.out.println("StepVerifier (reactor-test) - TESTOWANIE strumieni KROK PO KROKU, ZAMIAST .block() + AssertJ NA koncowym wyniku.");

        demonstrateBasicStepVerifier();
        demonstrateVerifyingErrorSignal();
        demonstrateVerifyingEmptyCompletion();
        demonstrateVirtualTimeForDelayedOperations();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `StepVerifier.create(publisher)` - rozpoczyna weryfikacje.
         * - `.expectNext(x)` - oczekuj KONKRETNEJ wartosci (MOZNA
         *   wywolac WIELOKROTNIE, W kolejnosci emisji).
         * - `.expectComplete()`/`.expectError(Class)` - oczekuj
         *   sygnalu terminalnego.
         * - `.verify()`/`.verify(Duration)` - URUCHOM weryfikacje
         *   (subskrybuje sie, BLOKUJE do zakonczenia LUB timeout).
         * - `StepVerifier.withVirtualTime(...)` - TESTUJ kod Z
         *   `delayElements`/`Flux.interval` BEZ CZEKANIA realnego
         *   czasu (przewijanie zegara wirtualnego).
         * - W REALNYM projekcie testy `@Test` uzywalyby TYCH SAMYCH
         *   wywolan `StepVerifier` (powiazanie Z `_25_unit_testing`
         *   wzorcem `runAndReport` Launcher API).
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void demonstrateBasicStepVerifier() {
        System.out.println("\n--- Podstawowy StepVerifier - expectNext + expectComplete ---");

        Flux<Integer> flux = Flux.just(1, 2, 3).map(x -> x * 10);

        StepVerifier.create(flux)
                .expectNext(10)
                .expectNext(20)
                .expectNext(30)
                .expectComplete()
                .verify(Duration.ofSeconds(2));

        System.out.println("StepVerifier.create(Flux.just(1,2,3).map(x*10)).expectNext(10,20,30).expectComplete().verify() -> PRZESZLO (bez wyjatku = sukces).");
    }

    private static void demonstrateVerifyingErrorSignal() {
        System.out.println("\n--- Weryfikacja sygnalu onError ---");

        Mono<String> monoZBledem = Mono.error(new IllegalArgumentException("Nieprawidlowy argument"));

        StepVerifier.create(monoZBledem)
                .expectErrorMatches(blad -> blad instanceof IllegalArgumentException
                        && blad.getMessage().equals("Nieprawidlowy argument"))
                .verify(Duration.ofSeconds(2));

        System.out.println("StepVerifier.create(Mono.error(...)).expectErrorMatches(...).verify() -> PRZESZLO - dokladnie zweryfikowano TYP I tresc bledu.");
    }

    private static void demonstrateVerifyingEmptyCompletion() {
        System.out.println("\n--- Weryfikacja PUSTEGO strumienia (BEZ zadnego onNext) ---");

        Flux<String> pusty = Flux.<String>empty();

        StepVerifier.create(pusty)
                .expectNextCount(0)
                .verifyComplete();

        System.out.println("StepVerifier.create(Flux.empty()).expectNextCount(0).verifyComplete() -> PRZESZLO.");

        System.out.println("\n--- Weryfikacja LICZBY elementow BEZ sprawdzania kazdej wartosci ---");
        StepVerifier.create(Flux.range(1, 100))
                .expectNextCount(100)
                .verifyComplete();
        System.out.println("StepVerifier.create(Flux.range(1,100)).expectNextCount(100).verifyComplete() -> PRZESZLO (100 elementow, bez wypisywania kazdego).");
    }

    private static void demonstrateVirtualTimeForDelayedOperations() {
        System.out.println("\n--- Virtual Time - testowanie delayElements() BEZ czekania realnego czasu ---");

        long startCzasu = System.currentTimeMillis();

        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(1)).take(3))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(1))
                .expectNext(0L)
                .thenAwait(Duration.ofHours(1))
                .expectNext(1L)
                .thenAwait(Duration.ofHours(1))
                .expectNext(2L)
                .verifyComplete();

        long czasTestu = System.currentTimeMillis() - startCzasu;
        System.out.println("Flux.interval(1h).take(3) PRZETESTOWANY (3x 1-godzinne odstepy) W " + czasTestu + "ms REALNEGO czasu - VirtualTimeScheduler 'przewija' zegar zamiast czekac.");

        assertThat(czasTestu).isLessThan(2000);
    }
}
