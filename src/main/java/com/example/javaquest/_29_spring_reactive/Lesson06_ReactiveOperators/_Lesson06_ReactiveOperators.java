package com.example.javaquest._29_spring_reactive.Lesson06_ReactiveOperators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson06_ReactiveOperators {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: Operatory reaktywne - map, flatMap, filter, zip, merge, concat ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - operatory W SZCZEGOLE
         * ============================================================
         * Lesson04/05 pokazaly PODSTAWOWE operatory NA `Mono`/`Flux` -
         * TA lekcja pogliebia NAJWAZNIEJSZE Z NICH, ZE SZCZEGOLNYM
         * naciskiem NA roznice `map` vs `flatMap` (KLASYCZNA pulapka
         * poczatkujacych W reaktywnosci) I operatory LACZACE WIELE
         * strumieni.
         */
        System.out.println("Operatory: map/flatMap (transformacja), filter (odsiewanie), zip/merge/concat (laczenie strumieni).");

        demonstrateMapVsFlatMap();
        demonstrateFlatMapForAsyncCalls();
        demonstrateConcatMapPreservesOrder();
        demonstrateThenAndThenMany();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `map(Function<T,R>)` - SYNCHRONICZNA transformacja
         *   (T -> R), BEZ mozliwosci zwrocenia NOWEGO `Mono`/`Flux`.
         * - `flatMap(Function<T, Mono<R>>)` - ASYNCHRONICZNA
         *   transformacja, GDY funkcja SAMA zwraca `Mono`/`Flux`
         *   (np. KOLEJNE zapytanie sieciowe/bazodanowe) - WYNIKI MOGA
         *   przyjsc W DOWOLNEJ kolejnosci (rownolegle wykonanie).
         * - `concatMap` - JAK `flatMap`, ale ZACHOWUJE kolejnosc
         *   (kosztem rownoleglosci).
         * - `then()`/`thenMany()` - "ZIGNORUJ wynik POPRZEDNIEGO
         *   Mono/Flux, PRZEJDZ DO nastepnego kroku".
         * - Pelna teoria obslugi bledow (onErrorResume/retry):
         *   Lesson07.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateMapVsFlatMap() {
        System.out.println("\n--- map vs flatMap - NAJWAZNIEJSZA roznica reaktywnosci ---");

        // map: T -> R (SYNCHRONICZNA transformacja, WYNIK JUZ gotowy)
        List<Integer> zMap = Flux.just(1, 2, 3)
                .map(liczba -> liczba * 10)
                .collectList()
                .block();
        System.out.println("map(x -> x*10): T -> R, SYNCHRONICZNA transformacja -> " + zMap);

        // flatMap: T -> Mono<R>/Flux<R> (funkcja zwraca KOLEJNY Publisher, np. zapytanie sieciowe)
        List<Integer> zFlatMap = Flux.just(1, 2, 3)
                .flatMap(liczba -> pobierzZAsynchronicznegoZrodla(liczba))
                .collectList()
                .block();
        System.out.println("flatMap(x -> Mono<R>): T -> Mono<R>, ASYNCHRONICZNE wywolanie (np. zapytanie DB/HTTP) -> " + zFlatMap);

        System.out.println("BLAD POCZATKUJACYCH: proba uzycia map() z funkcja zwracajaca Mono<R> DAJE 'Mono<Mono<R>>' (podwojnie zagniezdzone) - ZAWSZE uzywaj flatMap, GDY funkcja SAMA zwraca Publisher.");

        assertThat(zMap).containsExactly(10, 20, 30);
        assertThat(zFlatMap).containsExactlyInAnyOrder(100, 200, 300);
    }

    private static Mono<Integer> pobierzZAsynchronicznegoZrodla(int wejscie) {
        return Mono.just(wejscie * 100).delayElement(Duration.ofMillis(10));
    }

    private static void demonstrateFlatMapForAsyncCalls() {
        System.out.println("\n--- flatMap - rownolegle wykonanie WIELU asynchronicznych wywolan ---");

        long start = System.currentTimeMillis();
        List<String> wyniki = Flux.just("uzytkownik1", "uzytkownik2", "uzytkownik3")
                .flatMap(_Lesson06_ReactiveOperators::symulujZapytanieHttp)
                .collectList()
                .block();
        long czas = System.currentTimeMillis() - start;

        System.out.println("3 symulowane zapytania HTTP (po 50ms kazde) PRZEZ flatMap -> " + wyniki + ", czas: " + czas + "ms");
        System.out.println("flatMap wykonuje wywolania ROWNOLEGLE (subskrybuje sie DO wszystkich naraz) - czas BLISKI 50ms, NIE 150ms.");

        assertThat(wyniki).hasSize(3);
        assertThat(czas).isLessThan(150);
    }

    private static Mono<String> symulujZapytanieHttp(String uzytkownik) {
        return Mono.just("dane-dla-" + uzytkownik).delayElement(Duration.ofMillis(50));
    }

    private static void demonstrateConcatMapPreservesOrder() {
        System.out.println("\n--- concatMap - JAK flatMap, ale ZACHOWUJE kolejnosc (kosztem rownoleglosci) ---");

        List<Integer> zConcatMap = Flux.just(3, 1, 2)
                .concatMap(liczba -> Mono.just(liczba * 10).delayElement(Duration.ofMillis(10)))
                .collectList()
                .block();

        System.out.println("Flux.just(3,1,2).concatMap(x -> Mono.just(x*10)) -> " + zConcatMap + " (kolejnosc WEJSCIOWA ZACHOWANA: 30,10,20 - NIE posortowane, TYLKO W kolejnosci wejscia)");

        assertThat(zConcatMap).containsExactly(30, 10, 20);
    }

    private static void demonstrateThenAndThenMany() {
        System.out.println("\n--- then/thenMany - ZIGNORUJ wynik, PRZEJDZ DALEJ ---");

        String wynik = Mono.just("krok1")
                .doOnNext(x -> System.out.println("Wykonano: " + x))
                .then(Mono.just("krok2")) // ZIGNORUJ wynik "krok1", ZWROC "krok2"
                .block();

        System.out.println(".then(Mono.just(\"krok2\")) -> " + wynik + " (wynik POPRZEDNIEGO Mono ZIGNOROWANY, uzyty tylko JEGO SYGNAL zakonczenia)");

        assertThat(wynik).isEqualTo("krok2");
    }
}
