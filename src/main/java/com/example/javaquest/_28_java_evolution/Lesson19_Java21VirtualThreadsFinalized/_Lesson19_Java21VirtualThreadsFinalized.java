package com.example.javaquest._28_java_evolution.Lesson19_Java21VirtualThreadsFinalized;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson19_Java21VirtualThreadsFinalized {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 19: Java 21 (wrzesien 2023) - watki wirtualne OSTATECZNIE STABILNE (JEP 444) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_05_multithreading/Lesson33_VirtualThreads`
         * ============================================================
         * Java 21 KONCZY droge ROZPOCZETA W Javie 19 (Lesson17 tego
         * rozdzialu): JEP 444 USTABILIZOWAL watki wirtualne PO 2
         * rundach preview (19, 20) - OD TEJ WERSJI dzialaja BEZ
         * `--enable-preview`. Java 21 TO TEZ LTS - CO oznacza, ze
         * WIEKSZOSC produkcyjnych zespolow ZOBACZYLA watki wirtualne
         * PIERWSZY raz WLASNIE TUTAJ (nieliczni eksperymentowali Z
         * preview W 19/20).
         *
         * 🔍 TA lekcja NIE POWTARZA mechaniki (Lesson17 JUZ pokazal
         * `Thread.ofVirtual()`, `newVirtualThreadPerTaskExecutor()`,
         * skale) - TU pokazujemy KONKRETNA, PRAKTYCZNA roznice: BRAK
         * flagi preview ORAZ nowy, IDIOMATYCZNY wzorzec
         * `ExecutorService.invokeAll()` Z watkami wirtualnymi. Pelna,
         * GLEBOKA teoria (carrier threads, pinning, `ThreadLocal`
         * pulapki): `_05_multithreading/Lesson33`.
         */
        System.out.println("Java 21 (wrzesien 2023, LTS): watki wirtualne (JEP 444) OSTATECZNIE STABILNE - BEZ --enable-preview. Pelna teoria: _05_multithreading/Lesson33.");

        demonstrateNoPreviewFlagNeededAnymore();
        demonstrateInvokeAllIdiomaticPattern();
        explainWhyThisMattersForRealWorldServices();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - OD Javy 21: `Thread.ofVirtual()`/`newVirtualThreadPerTaskExecutor()`
         *   DZIALAJA BEZ zadnej flagi - GOTOWE DO produkcji.
         * - `ExecutorService.invokeAll(List<Callable<T>>)` Z executorem
         *   wirtualnym - IDIOMATYCZNY sposob URUCHOMIENIA WIELU
         *   NIEZALEZNYCH zadan I ZACZEKANIA NA WSZYSTKIE.
         * - Watki wirtualne SWIETNIE nadaja sie DO zadan I/O-bound
         *   (zapytania HTTP/bazodanowe) - NIE PRZYSPIESZAJA zadan
         *   CPU-bound (Lesson17 Exercise14).
         * - Pelna, GLEBOKA teoria: `_05_multithreading/Lesson33_
         *   VirtualThreads` (WLACZNIE Z pinning, carrier threads,
         *   `ThreadLocal`).
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    private static void demonstrateNoPreviewFlagNeededAnymore() {
        System.out.println("\n--- OD Javy 21: ZERO flag preview potrzebnych ---");
        Thread watek = Thread.ofVirtual().name("lekcja19-watek").unstarted(() -> { });
        System.out.println("Thread.ofVirtual() - KOMPILUJE I DZIALA na standardowym 'javac'/'java', BEZ --enable-preview.");
        System.out.println("Porownaj z Lesson17 (Java 19-20): TAM byla to preview feature, TU (Java 21) JEST juz PELNOPRAWNA czescia jezyka.");
        assertThat(watek.isVirtual()).isTrue();
    }

    private static void demonstrateInvokeAllIdiomaticPattern() throws InterruptedException {
        System.out.println("\n--- ExecutorService.invokeAll() - idiomatyczny wzorzec z watkami wirtualnymi ---");
        List<Callable<String>> zadania = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int numer = i;
            zadania.add(() -> {
                Thread.sleep(50); // symulacja krotkiego I/O (np. zapytanie HTTP)
                return "Wynik-zadania-" + numer;
            });
        }

        Instant start = Instant.now();
        List<String> wyniki;
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = executor.invokeAll(zadania);
            wyniki = new ArrayList<>();
            for (Future<String> future : futures) {
                try {
                    wyniki.add(future.get());
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        Duration czas = Duration.between(start, Instant.now());

        System.out.println("invokeAll() na 5 zadaniach (kazde ~50ms I/O), wykonanych ROWNOLEGLE: " + wyniki);
        System.out.println("Czas calkowity: " + czas.toMillis() + "ms (bliski 50ms, NIE 250ms - 5 watkow wirtualnych dzialalo NAPRAWDE ROWNOLEGLE).");

        assertThat(wyniki).hasSize(5);
        assertThat(czas.toMillis()).isLessThan(500);
    }

    private static void explainWhyThisMattersForRealWorldServices() {
        System.out.println("\n--- Dlaczego to WAZNE dla realnych serwisow ---");
        System.out.println("Klasyczny model 'watek NA zadanie' (Tomcat/Servlet, _07_servlets) OGRANICZAL liczbe rownoleglych polaczen DO rozmiaru puli watkow (setki, NIE tysiace).");
        System.out.println("Watki wirtualne POZWALAJA NA 'watek NA zadanie' PONOWNIE - ale TERAZ skaluja sie DO milionow, bo NIE zajmuja pamieci stosu platformowego watku.");
        System.out.println("Spring Boot 3.2+ (uzywany OD `_21_spring_boot` W tym kursie) WSPIERA watki wirtualne DLA Tomcata przez `spring.threads.virtual.enabled=true`.");
    }
}
