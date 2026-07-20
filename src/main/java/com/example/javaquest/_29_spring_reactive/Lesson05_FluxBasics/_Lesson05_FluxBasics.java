package com.example.javaquest._29_spring_reactive.Lesson05_FluxBasics;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson05_FluxBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: Flux - podstawy (0..N elementow) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Flux w SZCZEGOLE
         * ============================================================
         * `Flux<T>` reprezentuje STRUMIEN 0..N wartosci ASYNCHRONICZNIE
         * W CZASIE - odpowiednik `Stream<T>` (`_03_collections`), ale
         * Z 2 KLUCZOWYMI roznicami: (1) ASYNCHRONICZNY (elementy MOGA
         * przychodzic W CZASIE, np. co sekunde Z zewnetrznego zrodla),
         * (2) OBSLUGUJE backpressure (Lesson01-02).
         */
        System.out.println("Flux<T> - strumien 0..N wartosci asynchronicznie w czasie. Odpowiednik Stream<T>, ale ASYNCHRONICZNY + backpressure.");

        demonstrateCreationMethods();
        demonstrateFiniteVsInfiniteFlux();
        demonstrateCollectingResults();
        demonstrateCombiningMultipleFlux();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Flux.just(...)`, `Flux.fromIterable(...)`, `Flux.range(...)`
         *   - tworzenie SKONCZONEGO strumienia Z GOTOWYCH danych.
         * - `Flux.interval(Duration)` - NIESKONCZONY strumien
         *   (WYMAGA `.take(n)` LUB podobnego OGRANICZENIA, inaczej
         *   NIGDY sie NIE skonczy).
         * - `collectList()`/`collectList().block()` - ZBIERZ WSZYSTKIE
         *   elementy DO listy (TYLKO DO weryfikacji W `main()` -
         *   normalnie subskrybuje sie strumien NA BIEZACO).
         * - `Flux.merge(...)`/`Flux.concat(...)`/`Flux.zip(...)` -
         *   laczenie WIELU strumieni.
         * - Pelne operatory (map/filter/flatMap/reduce): Lesson06.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateCreationMethods() {
        System.out.println("\n--- Sposoby tworzenia Flux ---");

        Flux<String> zJust = Flux.just("a", "b", "c");
        Flux<Integer> zRange = Flux.range(1, 5);
        Flux<String> zIterable = Flux.fromIterable(List.of("x", "y", "z"));

        System.out.println("Flux.just(\"a\",\"b\",\"c\") -> " + zJust.collectList().block());
        System.out.println("Flux.range(1,5) -> " + zRange.collectList().block());
        System.out.println("Flux.fromIterable(List.of(\"x\",\"y\",\"z\")) -> " + zIterable.collectList().block());

        assertThat(zJust.collectList().block()).containsExactly("a", "b", "c");
    }

    private static void demonstrateFiniteVsInfiniteFlux() {
        System.out.println("\n--- SKONCZONY vs NIESKONCZONY Flux ---");

        List<Long> skonczonyZInterval = Flux.interval(Duration.ofMillis(20))
                .take(5) // BEZ tego, Flux.interval() NIGDY by sie nie skonczyl!
                .collectList()
                .block();

        System.out.println("Flux.interval(20ms).take(5) -> " + skonczonyZInterval + " (BEZ take(), strumien bylby NIESKONCZONY - typowy blad poczatkujacych)");
        assertThat(skonczonyZInterval).hasSize(5);
    }

    private static void demonstrateCollectingResults() {
        System.out.println("\n--- Zbieranie wynikow: collectList, reduce, count ---");

        List<Integer> lista = Flux.range(1, 10).collectList().block();
        Integer suma = Flux.range(1, 10).reduce(0, Integer::sum).block();
        Long liczbaElementow = Flux.range(1, 10).count().block();

        System.out.println("Flux.range(1,10).collectList() -> " + lista);
        System.out.println("Flux.range(1,10).reduce(0, Integer::sum) -> " + suma);
        System.out.println("Flux.range(1,10).count() -> " + liczbaElementow);

        assertThat(suma).isEqualTo(55);
        assertThat(liczbaElementow).isEqualTo(10L);
    }

    private static void demonstrateCombiningMultipleFlux() {
        System.out.println("\n--- Laczenie wielu Flux: merge, concat, zip ---");

        Flux<String> flux1 = Flux.just("A1", "A2");
        Flux<String> flux2 = Flux.just("B1", "B2");

        List<String> polaczonyConcat = Flux.concat(flux1, flux2).collectList().block();
        System.out.println("Flux.concat(flux1, flux2) -> " + polaczonyConcat + " (KOLEJNOSC ZACHOWANA - flux2 zaczyna sie DOPIERO PO zakonczeniu flux1)");

        List<String> zZip = Flux.just("A1", "A2").zipWith(Flux.just("B1", "B2"), (a, b) -> a + "+" + b).collectList().block();
        System.out.println("flux1.zipWith(flux2, (a,b) -> a+\"+\"+b) -> " + zZip + " (PAROWANIE element PO elemencie)");

        assertThat(polaczonyConcat).containsExactly("A1", "A2", "B1", "B2");
        assertThat(zZip).containsExactly("A1+B1", "A2+B2");
    }
}
