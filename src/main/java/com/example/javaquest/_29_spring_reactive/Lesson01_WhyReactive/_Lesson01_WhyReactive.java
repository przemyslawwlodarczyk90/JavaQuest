package com.example.javaquest._29_spring_reactive.Lesson01_WhyReactive;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson01_WhyReactive {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: Dlaczego programowanie reaktywne? Blocking vs non-blocking I/O, backpressure ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL - _29_spring_reactive
         * ============================================================
         * Ten rozdzial DOMYKA watek zostawiony otwarty W
         * `_22_spring_web/Lesson17_HttpClientsRestTemplateWebClientRestClient`
         * - TAM `WebClient` byl uzywany Z `.block()` (a wiec
         * NIE-reaktywnie, jako "zwykly" klient synchroniczny). TU
         * poznajemy PRAWDZIWY styl reaktywny - BEZ `.block()`.
         *
         * 🔍 PROBLEM, KTORY ROZWIAZUJE reaktywnosc: "C10K problem"
         * (Dan Kegel, 1999) - JAK obsluzyc 10 000+ jednoczesnych
         * polaczen sieciowych NA JEDNEJ maszynie? Klasyczny model
         * "1 watek NA 1 zadanie" (Tomcat/Servlet, `_07_servlets`)
         * SKALUJE SIE zle - kazdy watek platformowy ZUZYWA ~1MB stosu,
         * WIEC 10 000 watkow = 10GB PAMIECI, ZANIM COKOLWIEK zrobia.
         *
         * UWAGA: watki wirtualne (`_28_java_evolution/Lesson17,19`)
         * ROZWIAZALY TEN SAM problem INNYM sposobem (TANIE watki,
         * NADAL synchroniczny styl pisania kodu) - reaktywnosc
         * ROZWIAZUJE go PRZEZ CALKOWICIE INNY model programowania
         * (asynchroniczny, event-driven, MALA liczba watkow
         * OBSLUGUJACA WIELE zadan naraz) - Lesson16 tego rozdzialu
         * PORUWNA oba podejscia WPROST.
         */
        System.out.println("Problem: 10 000+ jednoczesnych polaczen NA JEDNEJ maszynie (\"C10K problem\", Dan Kegel 1999).");

        demonstrateBlockingIoWastesThreads();
        demonstrateNonBlockingIdeaConceptually();
        explainBackpressure();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - BLOCKING I/O: watek CZEKA (bezczynnie) NA odpowiedz Z
         *   dysku/sieci/bazy - watek jest "zajety", ale NIC nie robi.
         * - NON-BLOCKING I/O: watek WYSYLA zadanie I OD RAZU wraca DO
         *   puli - GDY odpowiedz przyjdzie, WYWOLYWANY jest callback
         *   (NA jednym Z MALEJ liczby watkow event-loop).
         * - BACKPRESSURE: mechanizm, W ktorym KONSUMENT informuje
         *   PRODUCENTA, ILE danych jest W STANIE PRZYJAC NARAZ -
         *   ZAPOBIEGA zalaniu wolnego konsumenta PRZEZ szybkiego
         *   producenta (`OutOfMemoryError` bez tego mechanizmu).
         * - Reactive Streams (specyfikacja stojaca ZA tym wszystkim):
         *   Lesson02.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateBlockingIoWastesThreads() throws Exception {
        System.out.println("\n--- Blocking I/O - watek CZEKA bezczynnie na kazde zadanie ---");
        int liczbaZadan = 20;

        Instant start = Instant.now();
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < liczbaZadan; i++) {
                int indeks = i;
                futures.add(executor.submit(() -> {
                    Thread.sleep(50); // symulacja BLOKUJACEGO wywolania I/O (np. JDBC, plik)
                    return indeks;
                }));
            }
            int suma = 0;
            for (Future<Integer> f : futures) {
                suma += f.get();
            }
            System.out.println("Suma: " + suma);
        }
        Duration czas = Duration.between(start, Instant.now());
        System.out.println(liczbaZadan + " zadan NA 4 watkach platformowych (BLOCKING, 50ms/zadanie kazde) - czas: " + czas.toMillis() + "ms");
        System.out.println("Z TYLKO 4 watkami, 20 zadan MUSI czekac W KOLEJCE (20/4 * 50ms = ~250ms) - watek NIC NIE ROBI podczas oczekiwania, TYLKO zajmuje miejsce W puli.");

        assertThat(czas.toMillis()).isGreaterThanOrEqualTo(200);
    }

    private static void demonstrateNonBlockingIdeaConceptually() {
        System.out.println("\n--- Non-blocking I/O - idea (pelna implementacja: Lesson03-05, Project Reactor) ---");
        System.out.println("Zamiast: watek.wykonaj(zadanie).CZEKAJ_NA_WYNIK() -> BLOKUJE watek");
        System.out.println("Reaktywnie: watek.wyslijZadanie(zadanie, callback).WROC_NATYCHMIAST() -> watek WOLNY dla INNYCH zadan");
        System.out.println("GDY wynik jest gotowy (np. dane przyszly Z sieci), event loop WYWOLUJE callback NA jednym Z MALEJ liczby watkow (zazwyczaj = liczba rdzeni CPU).");
        System.out.println("Efekt: KILKA watkow MOZE obsluzyc TYSIACE jednoczesnych 'oczekujacych' operacji I/O, bo ZADEN watek nie jest 'zablokowany' W oczekiwaniu.");
        System.out.println("Cena: kod STAJE SIE trudniejszy DO CZYTANIA (lancuchy callbackow/operatorow zamiast liniowego kodu) - Lesson03-06 pokaza, JAK Project Reactor to LAGODZI.");
    }

    private static void explainBackpressure() {
        System.out.println("\n--- Backpressure - konsument KONTROLUJE tempo producenta ---");
        System.out.println("Scenariusz BEZ backpressure: producent generuje 1 000 000 elementow/s, konsument PRZETWARZA 100/s -> BUFOR ROSNIE bez konca -> OutOfMemoryError.");
        System.out.println("Scenariusz Z backpressure: konsument MOWI producentowi 'wyslij mi TYLKO 100 na raz' - producent RESPEKTUJE te 'zadanie' (request(n)) I NIE wysyla wiecej, DOPOKI konsument nie POPROSI o kolejne.");
        System.out.println("TO WLASNIE backpressure ROZROZNIA 'Reactive Streams' OD zwyklych callbackow/Observable (RxJava 1 PRZED tym mechanizmem) - pelna specyfikacja: Lesson02.");
    }
}
