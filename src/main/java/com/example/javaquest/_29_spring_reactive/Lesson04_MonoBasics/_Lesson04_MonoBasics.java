package com.example.javaquest._29_spring_reactive.Lesson04_MonoBasics;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson04_MonoBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Mono - podstawy (0 lub 1 element) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Mono w SZCZEGOLE
         * ============================================================
         * `Mono<T>` reprezentuje ASYNCHRONICZNA obliczenie, KTORE
         * ZWROCI 0 LUB 1 wartosc. Semantycznie PODOBNY DO
         * `Optional<T>` (0 lub 1) LUB `CompletableFuture<T>`
         * (`_14_advancedjava/Lesson32`) - ale W ODROZNIENIU OD
         * `CompletableFuture` (EAGER - zaczyna sie wykonywac OD RAZU
         * po utworzeniu), `Mono` jest LENIWY (Lesson03).
         */
        System.out.println("Mono<T> - 0 lub 1 wartosc asynchronicznie. Podobienstwo do Optional/CompletableFuture, ale LENIWY.");

        demonstrateCreationMethods();
        demonstrateEmptyMonoHandling();
        demonstrateErrorHandlingBasics();
        demonstrateChainingTransformations();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Mono.just(x)` - Mono Z GOTOWA wartoscia.
         * - `Mono.empty()` - Mono BEZ wartosci (odpowiednik
         *   `Optional.empty()`).
         * - `Mono.error(ex)` - Mono, ktory ZAKONCZY SIE bledem.
         * - `Mono.fromSupplier/fromCallable` - LENIWE obliczenie
         *   wartosci.
         * - `map`/`flatMap`/`filter` - transformacje (jak NA Stream,
         *   ale ASYNCHRONICZNE).
         * - `defaultIfEmpty`/`switchIfEmpty` - obsluga PUSTEGO Mono.
         * - Pelne obsluga bledow (retry, onErrorResume): Lesson07.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void demonstrateCreationMethods() {
        System.out.println("\n--- Sposoby tworzenia Mono ---");

        Mono<String> zWartoscia = Mono.just("wartosc");
        Mono<String> puste = Mono.empty();
        Mono<String> zSuppliera = Mono.fromSupplier(() -> "obliczona wartosc");
        Mono<String> zOptional = Mono.justOrEmpty(Optional.of("z optional"));

        System.out.println("Mono.just(...) -> " + zWartoscia.block());
        System.out.println("Mono.empty().block() -> " + puste.block() + " (null - PUSTY Mono)");
        System.out.println("Mono.fromSupplier(...) -> " + zSuppliera.block());
        System.out.println("Mono.justOrEmpty(Optional.of(\"z optional\")) rozpakowane -> " + zOptional.block());

        assertThat(zWartoscia.block()).isEqualTo("wartosc");
        assertThat(puste.block()).isNull();
    }

    private static void demonstrateEmptyMonoHandling() {
        System.out.println("\n--- Obsluga PUSTEGO Mono - defaultIfEmpty / switchIfEmpty ---");

        Mono<String> puste = Mono.empty();

        String zDomyslna = puste.defaultIfEmpty("wartosc domyslna").block();
        System.out.println("Mono.empty().defaultIfEmpty(\"wartosc domyslna\") -> " + zDomyslna);

        String zeSwitchem = puste.switchIfEmpty(Mono.just("wartosc Z INNEGO Mono")).block();
        System.out.println("Mono.empty().switchIfEmpty(Mono.just(...)) -> " + zeSwitchem);
        System.out.println("Roznica: defaultIfEmpty daje STALA wartosc, switchIfEmpty pozwala WYWOLAC INNY Mono (np. zapytanie DO INNEGO zrodla).");

        assertThat(zDomyslna).isEqualTo("wartosc domyslna");
        assertThat(zeSwitchem).isEqualTo("wartosc Z INNEGO Mono");
    }

    private static void demonstrateErrorHandlingBasics() {
        System.out.println("\n--- Podstawy obslugi bledow (pelna teoria: Lesson07) ---");

        Mono<String> zBledem = Mono.error(new IllegalStateException("Cos poszlo nie tak"));

        String odzyskany = zBledem
                .onErrorResume(blad -> {
                    System.out.println("Zlapano blad: " + blad.getMessage() + " - ODZYSKUJEMY wartoscia zastepcza.");
                    return Mono.just("wartosc awaryjna");
                })
                .block();

        System.out.println("Wynik PO odzyskaniu: " + odzyskany);
        assertThat(odzyskany).isEqualTo("wartosc awaryjna");
    }

    private static void demonstrateChainingTransformations() {
        System.out.println("\n--- Lancuch transformacji: map, filter, flatMap ---");

        Mono<Integer> wynik = Mono.just(5)
                .map(liczba -> liczba * 2)
                .filter(liczba -> liczba > 5)
                .flatMap(liczba -> Mono.just(liczba + 100));

        Integer wartosc = wynik.block();
        System.out.println("Mono.just(5).map(x2).filter(>5).flatMap(+100) -> " + wartosc);
        assertThat(wartosc).isEqualTo(110);

        System.out.println("\n--- filter() moze zamienic Mono w PUSTY (jesli warunek NIE jest spelniony) ---");
        Integer odrzucony = Mono.just(3)
                .map(liczba -> liczba * 2) // = 6
                .filter(liczba -> liczba > 10) // 6 > 10 = false -> Mono STAJE SIE puste
                .block();
        System.out.println("Mono.just(3).map(x2).filter(>10) -> " + odrzucony + " (null - filter ODRZUCIL wartosc)");
        assertThat(odrzucony).isNull();
    }
}
