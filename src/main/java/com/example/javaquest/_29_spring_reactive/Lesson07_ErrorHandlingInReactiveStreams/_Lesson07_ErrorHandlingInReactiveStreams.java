package com.example.javaquest._29_spring_reactive.Lesson07_ErrorHandlingInReactiveStreams;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson07_ErrorHandlingInReactiveStreams {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: Obsluga bledow w strumieniach reaktywnych ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - obsluga bledow W Reactorze
         * ============================================================
         * W Reactive Streams (Lesson02), BLAD jest SYGNALEM
         * TERMINALNYM (`onError`) - PO nim strumien SIE KONCZY (BEZ
         * dalszych `onNext`). Reactor dostarcza BOGATY zestaw
         * operatorow DO obslugi tego sygnalu - odpowiednik `try-catch`
         * (`_01_fundamentals/Lesson16`) DLA swiata reaktywnego.
         */
        System.out.println("Blad w strumieniu reaktywnym = sygnal terminalny (onError) - Reactor daje operatory DO jego obslugi (odpowiednik try-catch).");

        demonstrateOnErrorReturn();
        demonstrateOnErrorResume();
        demonstrateOnErrorMap();
        demonstrateRetryWithBackoff();
        demonstrateErrorInFlatMapDoesNotStopOtherElements();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `onErrorReturn(wartosc)` - PRZY bledzie, ZWROC STALA
         *   wartosc zastepcza.
         * - `onErrorResume(fn)` - PRZY bledzie, PRZELACZ NA INNY
         *   Mono/Flux (np. zapytanie DO cache'u ZAMIAST bazy).
         * - `onErrorMap(fn)` - PRZEKSZTALC 1 wyjatek W INNY (np.
         *   techniczny wyjatek NA biznesowy - powiazanie Z
         *   `_16_clean_code/Lesson17_ExceptionDesign`).
         * - `retry(n)`/`retryWhen(Retry.backoff(...))` - PONOW
         *   probe PO bledzie, Z OPCJONALNYM wykladniczym opoznieniem.
         * - Blad W `flatMap` DLA JEDNEGO elementu `Flux` KONCZY CALY
         *   strumien (chyba ze uzyjesz `onErrorContinue` - Z OSTROZNOSCIA,
         *   Reactor dokumentacja OSTRZEGA przed naduzywaniem).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateOnErrorReturn() {
        System.out.println("\n--- onErrorReturn - stala wartosc zastepcza przy bledzie ---");

        String wynik = Mono.<String>error(new RuntimeException("Blad polaczenia"))
                .onErrorReturn("wartosc domyslna")
                .block();

        System.out.println("Mono.error(...).onErrorReturn(\"wartosc domyslna\") -> " + wynik);
        assertThat(wynik).isEqualTo("wartosc domyslna");
    }

    private static void demonstrateOnErrorResume() {
        System.out.println("\n--- onErrorResume - przelaczenie NA INNY Mono/Flux przy bledzie ---");

        String wynik = pobierzZGlownegoZrodla()
                .onErrorResume(blad -> {
                    System.out.println("Glowne zrodlo zawiodlo (" + blad.getMessage() + ") - PRZELACZAMY NA zapasowe.");
                    return pobierzZZapasowegoZrodla();
                })
                .block();

        System.out.println("Wynik koncowy: " + wynik);
        assertThat(wynik).isEqualTo("dane z zapasowego zrodla");
    }

    private static Mono<String> pobierzZGlownegoZrodla() {
        return Mono.error(new RuntimeException("Glowne zrodlo niedostepne"));
    }

    private static Mono<String> pobierzZZapasowegoZrodla() {
        return Mono.just("dane z zapasowego zrodla");
    }

    private static void demonstrateOnErrorMap() {
        System.out.println("\n--- onErrorMap - przeksztalcenie 1 wyjatku w inny (techniczny -> biznesowy) ---");

        try {
            Mono.error(new java.net.ConnectException("Connection refused"))
                    .onErrorMap(techniczny -> new IllegalStateException("Usluga zewnetrzna niedostepna", techniczny))
                    .block();
        } catch (IllegalStateException oczekiwany) {
            System.out.println("Zlapano: " + oczekiwany.getClass().getSimpleName() + " (\"" + oczekiwany.getMessage() + "\"), przyczyna: " + oczekiwany.getCause().getClass().getSimpleName());
            assertThat(oczekiwany.getCause()).isInstanceOf(java.net.ConnectException.class);
        }

        System.out.println("Powiazanie: _16_clean_code/Lesson17_ExceptionDesign - opakowanie technicznego wyjatku W BIZNESOWY, Z ZACHOWANIEM 'cause' (lancuchowanie).");
    }

    private static void demonstrateRetryWithBackoff() {
        System.out.println("\n--- retryWhen(Retry.backoff(...)) - ponawianie z WYKLADNICZYM opoznieniem ---");

        AtomicInteger licznikProb = new AtomicInteger();

        String wynik = Mono.defer(() -> {
                    int proba = licznikProb.incrementAndGet();
                    System.out.println("Proba #" + proba);
                    if (proba < 3) {
                        return Mono.error(new RuntimeException("Chwilowy blad (proba " + proba + ")"));
                    }
                    return Mono.just("sukces PRZY probie #" + proba);
                })
                .retryWhen(Retry.backoff(5, Duration.ofMillis(10)))
                .block();

        System.out.println("Wynik po retry: " + wynik);
        assertThat(wynik).isEqualTo("sukces PRZY probie #3");
        assertThat(licznikProb.get()).isEqualTo(3);
    }

    private static void demonstrateErrorInFlatMapDoesNotStopOtherElements() {
        System.out.println("\n--- Blad W 1 elemencie flatMap KONCZY CALY strumien (bez onErrorContinue) ---");

        List<String> wynikiBezObslugi = null;
        try {
            wynikiBezObslugi = Flux.just(1, 2, 0, 4)
                    .flatMap(liczba -> Mono.just(10 / liczba)) // dzielenie przez 0 dla liczba=0!
                    .map(String::valueOf)
                    .collectList()
                    .block();
        } catch (ArithmeticException oczekiwany) {
            System.out.println("BEZ obslugi bledu: CALY strumien PRZERWANY na elemencie '0' - wyjatek: " + oczekiwany.getMessage());
        }
        assertThat(wynikiBezObslugi).isNull();

        List<String> wynikiZObsluga = Flux.just(1, 2, 0, 4)
                .flatMap(liczba -> Mono.just(liczba)
                        .map(x -> 10 / x)
                        .map(String::valueOf)
                        .onErrorReturn("BLAD"))
                .collectList()
                .block();

        System.out.println("Z onErrorReturn PRZY KAZDYM elemencie: " + wynikiZObsluga + " (POZOSTALE elementy PRZETWORZONE, TYLKO problematyczny zastapiony)");
        assertThat(wynikiZObsluga).containsExactly("10", "5", "BLAD", "2");
    }
}
