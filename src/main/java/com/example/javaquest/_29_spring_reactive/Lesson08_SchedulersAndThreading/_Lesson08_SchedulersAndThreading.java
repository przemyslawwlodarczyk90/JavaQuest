package com.example.javaquest._29_spring_reactive.Lesson08_SchedulersAndThreading;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson08_SchedulersAndThreading {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 8: Schedulery i watki w Project Reactor ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Schedulery
         * ============================================================
         * BEZ jawnej konfiguracji, Reactor WYKONUJE cala operacje NA
         * watku, ktory wywolal `subscribe()` (watek WYWOLUJACY).
         * `Scheduler` POZWALA jawnie WYBRAC, NA JAKIM watku/puli MA
         * dzialac dana czesc pipeline'u - KLUCZOWE DLA (1) operacji
         * blokujacych (JDBC, plikowe I/O) wewnatrz reaktywnego kodu,
         * (2) rownoleglosci CPU-bound. Powiazanie Z
         * `_05_multithreading` (pule watkow) - Schedulery TO
         * REAKTYWNY odpowiednik `ExecutorService`.
         */
        System.out.println("Scheduler - REAKTYWNY odpowiednik ExecutorService (_05_multithreading) - decyduje, NA JAKIM watku wykonuje sie czesc pipeline'u.");

        demonstrateDefaultThreadIsCallingThread();
        demonstrateSubscribeOnVsPublishOn();
        demonstrateBoundedElasticForBlockingCalls();
        demonstrateParallelSchedulerForCpuBoundWork();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - BEZ Schedulera, kod wykonuje sie NA watku wywolujacym
         *   `subscribe()`.
         * - `subscribeOn(scheduler)` - zmienia watek DLA CALEGO
         *   lancucha OD POCZATKU (subskrypcji), NIEZALEZNIE OD MIEJSCA
         *   wywolania W lancuchu.
         * - `publishOn(scheduler)` - zmienia watek OD MIEJSCA wywolania
         *   W DOL lancucha (moze byc uzyty WIELOKROTNIE, KAZDA zmiana
         *   dotyczy TYLKO reszty PONIZEJ).
         * - `Schedulers.boundedElastic()` - PULA DLA operacji
         *   BLOKUJACYCH (JDBC, pliki) - NIGDY NIE uzywaj domyslnego
         *   event-loop DO blokujacego kodu (zablokujesz CALY event
         *   loop!).
         * - `Schedulers.parallel()` - PULA O rozmiarze = liczba
         *   rdzeni CPU, DLA obliczen CPU-bound.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateDefaultThreadIsCallingThread() {
        System.out.println("\n--- BEZ Schedulera - kod wykonuje sie NA watku wywolujacym subscribe() ---");

        String watekGlowny = Thread.currentThread().getName();
        Mono.just("wartosc")
                .doOnNext(x -> System.out.println("  Wykonanie NA watku: " + Thread.currentThread().getName() + " (glowny: " + watekGlowny + ")"))
                .block();

        System.out.println("BEZ jawnego Schedulera, CALY pipeline dziala NA watku, ktory wywolal subscribe()/block().");
    }

    private static void demonstrateSubscribeOnVsPublishOn() throws InterruptedException {
        System.out.println("\n--- subscribeOn vs publishOn - GDZIE w lancuchu wplywaja na watek ---");

        StringBuilder log = new StringBuilder();
        Mono.fromSupplier(() -> {
                    log.append("Zrodlo NA: ").append(Thread.currentThread().getName()).append("\n");
                    return "dane";
                })
                .subscribeOn(Schedulers.boundedElastic()) // WPLYWA na CALY lancuch OD POCZATKU
                .map(x -> {
                    log.append("map() PO subscribeOn NA: ").append(Thread.currentThread().getName()).append("\n");
                    return x.toUpperCase();
                })
                .publishOn(Schedulers.parallel()) // OD TEGO MIEJSCA W DOL - INNY watek
                .map(x -> {
                    log.append("map() PO publishOn NA: ").append(Thread.currentThread().getName()).append("\n");
                    return x + "!";
                })
                .block();

        System.out.println(log);
        System.out.println("subscribeOn() ZMIENIA watek OD SAMEGO POCZATKU (nawet jesli wywolane NA KONCU lancucha) - publishOn() ZMIENIA watek TYLKO OD MIEJSCA wywolania W DOL.");
    }

    private static void demonstrateBoundedElasticForBlockingCalls() {
        System.out.println("\n--- boundedElastic() - dedykowana pula DLA operacji BLOKUJACYCH (JDBC, pliki) ---");

        List<String> wyniki = Flux.range(1, 3)
                .flatMap(numer -> Mono.fromCallable(() -> {
                            // symulacja BLOKUJACEGO wywolania (np. JDBC) - MUSI dzialac NA boundedElastic, NIGDY na event-loop!
                            Thread.sleep(20);
                            return "wynik-blokujacy-" + numer + "-NA-" + Thread.currentThread().getName().split("-")[0];
                        })
                        .subscribeOn(Schedulers.boundedElastic()))
                .collectList()
                .block();

        System.out.println("3 symulowane BLOKUJACE wywolania NA Schedulers.boundedElastic(): " + wyniki);
        System.out.println("ZASADA: KAZDA operacja blokujaca (JDBC/plik/legacy API) wewnatrz reaktywnego kodu MUSI byc opakowana .subscribeOn(Schedulers.boundedElastic()), inaczej ZABLOKUJE watki event-loop (Schedulers.parallel()) DLA WSZYSTKICH innych zadan!");

        assertThat(wyniki).hasSize(3);
        assertThat(wyniki).allMatch(s -> s.contains("boundedElastic"));
    }

    private static void demonstrateParallelSchedulerForCpuBoundWork() {
        System.out.println("\n--- parallel() - pula O rozmiarze = liczba rdzeni CPU, DLA obliczen CPU-bound ---");

        int liczbaRdzeni = Runtime.getRuntime().availableProcessors();
        System.out.println("Dostepne rdzenie CPU: " + liczbaRdzeni + " - Schedulers.parallel() domyslnie tworzy TYLE watkow.");

        Long suma = Flux.range(1, 1_000_000)
                .parallel() // dzieli strumien NA liczbe rdzeni
                .runOn(Schedulers.parallel())
                .map(liczba -> (long) liczba * liczba)
                .sequential()
                .reduce(0L, Long::sum)
                .block();

        System.out.println("Suma kwadratow 1..1_000_000 (obliczona ROWNOLEGLE NA Schedulers.parallel()): " + suma);
        assertThat(suma).isGreaterThan(0L);
    }
}
