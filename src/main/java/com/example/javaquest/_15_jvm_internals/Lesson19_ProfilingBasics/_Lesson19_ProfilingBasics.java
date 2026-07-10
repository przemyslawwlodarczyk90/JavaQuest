package com.example.javaquest._15_jvm_internals.Lesson19_ProfilingBasics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _Lesson19_ProfilingBasics {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 19: PROFILOWANIE - SAMPLING VS INSTRUMENTACJA ===");

        /*
         * ============================================================
         * 📦 DWIE GLOWNE STRATEGIE PROFILOWANIA
         * ============================================================
         * - SAMPLING PROFILING (probkowanie): profiler co jakis czas
         *   (np. co 10-50ms) pyta WSZYSTKIE watki "co teraz robisz?"
         *   (pobiera ich stack trace) i zlicza statystyke, jaka metoda
         *   NAJCZESCIEJ pojawia sie na SZCZYCIE stosu. Zaleta: BARDZO
         *   niski narzut (mozna zostawic wlaczone dluzej, nawet na
         *   produkcji - podobnie jak JFR z Lesson18, ktory tez uzywa
         *   probkowania pod maska). Wada: statystyczne przyblizenie -
         *   krotkie, rzadkie wywolania moga zostac "przeoczone" miedzy
         *   probkami.
         * - INSTRUMENTACJA (instrumentation profiling): profiler
         *   WSTRZYKUJE dodatkowy kod pomiarowy do bajtkodu KAZDEJ metody
         *   (np. "zapisz czas wejscia/wyjscia"), zwykle przez Java Agent
         *   (java.lang.instrument.Instrumentation) modyfikujacy klasy
         *   w locie. Zaleta: DOKLADNE liczby (dokladny czas kazdego
         *   wywolania, dokladna liczba wywolan). Wada: duzo wiekszy
         *   narzut (kazde wywolanie kazdej zinstrumentowanej metody jest
         *   spowolnione) - rzadko uzywane na produkcji, glownie do
         *   jednorazowej, dokladnej analizy w srodowisku testowym.
         *
         * Realne narzedzia: async-profiler, JProfiler, YourKit czesto
         * uzywaja sampling jako trybu domyslnego (niski narzut), z
         * opcjonalna instrumentacja dla wybranych, "podejrzanych" metod.
         */
        System.out.println("Sampling = tania statystyka co jakis czas. Instrumentacja = dokladny, ale drogi pomiar.\n");

        /*
         * ============================================================
         * 🔹 MINI SAMPLING PROFILER - REALNIE DZIALAJACA DEMONSTRACJA
         * ============================================================
         * Ponizej budujemy prawdziwy, dzialajacy (choc bardzo uproszczony)
         * sampling profiler:
         *  1. Watek roboczy wykonuje obliczenia (3 metody wywolujace sie
         *     nawzajem w petli) przez ok. 1.5 sekundy.
         *  2. Watek glowny co ~30ms pobiera stack trace WSZYSTKICH watkow
         *     (Thread.getAllStackTraces()), znajduje watek roboczy po
         *     referencji i zapisuje "kto jest na szczycie stosu" (index 0
         *     tablicy StackTraceElement[] = aktualnie wykonywana metoda).
         *  3. Po zakonczeniu probkowania wypisujemy ranking najczesciej
         *     "zlapanych na goracym uczynku" metod - to i jest sedno
         *     sampling profilingu.
         */
        demonstrateSamplingProfiler();

        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    // --- Metody robocze tworzace realny, wzajemnie wywolujacy sie "hot path" ---

    private static void workloadMethodA(long endTimeMillis) {
        while (System.currentTimeMillis() < endTimeMillis) {
            workloadMethodB(endTimeMillis);
            if (System.currentTimeMillis() >= endTimeMillis) {
                return;
            }
        }
    }

    private static void workloadMethodB(long endTimeMillis) {
        workloadMethodC(endTimeMillis);
    }

    private static void workloadMethodC(long endTimeMillis) {
        // Sztuczne obciazenie CPU - suma kwadratow, zeby metoda faktycznie
        // "zajela" procesor na chwile zamiast natychmiast wrocic.
        long sum = 0;
        for (int i = 0; i < 200_000; i++) {
            sum += (long) i * i;
        }
        if (sum < 0) { // nigdy sie nie zdarzy - tylko zeby JIT nie wyrzucil petli jako "martwej"
            System.out.println("unreachable: " + sum);
        }
    }

    private static void demonstrateSamplingProfiler() throws InterruptedException {
        System.out.println("=== Mini sampling profiler - probkowanie co ~30ms przez ~1.5s ===");

        long workloadBudgetMillis = 1500;
        Thread worker = new Thread(() -> {
            long end = System.currentTimeMillis() + workloadBudgetMillis;
            workloadMethodA(end);
        }, "Lesson19-Worker");
        worker.setDaemon(true);

        Map<String, Integer> hitCounts = new HashMap<>();
        int totalSamples = 0;

        worker.start();

        long samplingStart = System.currentTimeMillis();
        long samplingMaxMillis = 2000; // twardy limit probkowania, na wypadek gdyby worker dzialal dluzej

        while (worker.isAlive() && (System.currentTimeMillis() - samplingStart) < samplingMaxMillis) {
            Map<Thread, StackTraceElement[]> allStacks = Thread.getAllStackTraces();
            StackTraceElement[] workerStack = allStacks.get(worker);

            if (workerStack != null && workerStack.length > 0) {
                StackTraceElement top = workerStack[0]; // index 0 = aktualnie wykonywana metoda
                String key = top.getClassName() + "." + top.getMethodName();
                hitCounts.merge(key, 1, Integer::sum);
                totalSamples++;
            }

            sleepQuiet(30);
        }

        // Bounded join - profiler musi normalnie zakonczyc dzialanie razem z workerem.
        worker.join(2000);

        System.out.println("Zebrano " + totalSamples + " probek w czasie dzialania watku roboczego.");
        printRanking(hitCounts, totalSamples);
    }

    private static void printRanking(Map<String, Integer> hitCounts, int totalSamples) {
        System.out.println("\n=== RANKING 'goracych' metod (najczesciej na szczycie stosu) ===");

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(hitCounts.entrySet());
        sorted.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue).reversed());

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sorted) {
            double percent = totalSamples == 0 ? 0.0 : (entry.getValue() * 100.0 / totalSamples);
            System.out.printf("  %d. %-70s %4d probek (%.1f%%)%n", rank, entry.getKey(), entry.getValue(), percent);
            rank++;
        }

        if (sorted.isEmpty()) {
            System.out.println("  (brak probek - watek roboczy zakonczyl sie zbyt szybko lub sampler nie zdazyl zlapac zadnej probki)");
        }
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
