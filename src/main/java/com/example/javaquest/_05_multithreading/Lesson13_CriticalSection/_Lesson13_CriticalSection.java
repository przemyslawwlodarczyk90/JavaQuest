package com.example.javaquest._05_multithreading.Lesson13_CriticalSection;

public class _Lesson13_CriticalSection {

    // Wspólny stan używany przez demo "ŹLE" (szeroki synchronized)
    private static long sharedCounterWide = 0;
    private static final Object lockWide = new Object();

    // Wspólny stan używany przez demo "DOBRZE" (wąski synchronized)
    private static long sharedCounterNarrow = 0;
    private static final Object lockNarrow = new Object();

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 SEKCJA KRYTYCZNA (CRITICAL SECTION) – CO TO JEST?
         * ============================================================
         * Sekcja krytyczna to fragment kodu, który operuje na stanie
         * WSPÓLNYM (dzielonym) dla wielu wątków i wymaga ochrony przed
         * jednoczesnym dostępem – inaczej dojdzie do race condition
         * (niedeterministyczny, błędny wynik).
         *
         * WAŻNE: sekcją krytyczną jest TYLKO ten fragment kodu, który
         * faktycznie czyta/modyfikuje stan współdzielony. Cała reszta
         * pracy (obliczenia lokalne, logika niekorzystająca ze stanu
         * dzielonego) NIE jest sekcją krytyczną i nie powinna być objęta
         * blokiem `synchronized`.
         *
         * Przykład: jeśli wątek robi:
         *   1) skomplikowane obliczenie lokalne (nie dotyka stanu dzielonego)
         *   2) inkrementację współdzielonego licznika
         * to sekcją krytyczną jest TYLKO krok 2!
         */

        /*
         * ============================================================
         * ⚠️ ŹLE – ZBYT SZEROKI BLOK synchronized
         * ============================================================
         * Częsty błąd początkujących: "dla bezpieczeństwa" obejmuje się
         * synchronized CAŁĄ metodę/pętlę, łącznie z niekrytyczną, kosztowną
         * pracą. Efekt: wątki stoją w kolejce i czekają na siebie nawzajem
         * nawet tam, gdzie wcale nie muszą – tracimy korzyści z wielowątkowości.
         *
         * synchronized (lock) {
         *     double wynik = kosztowneObliczenia(i); // ❌ NIE dotyka stanu dzielonego!
         *     sharedCounter++;                        // ✅ to jest jedyna sekcja krytyczna
         * }
         */

        /*
         * ============================================================
         * ✅ DOBRZE – MINIMALNY BLOK synchronized
         * ============================================================
         * synchronized obejmuje TYLKO operację na stanie dzielonym:
         *
         * double wynik = kosztowneObliczenia(i); // poza synchronized – równolegle!
         * synchronized (lock) {
         *     sharedCounter++;                    // tylko to jest chronione
         * }
         *
         * Dzięki temu kosztowna, niekrytyczna praca wykonuje się RÓWNOLEGLE
         * na wszystkich wątkach, a blokada jest trzymana tylko przez chwilę.
         */

        System.out.println("=== Pomiar: SZEROKI synchronized (ŹLE) ===");
        long wideMillis = runWideSynchronizedDemo();
        System.out.println("Wynik licznika (szeroki): " + sharedCounterWide);
        System.out.println("Czas: " + wideMillis + " ms");

        System.out.println();

        System.out.println("=== Pomiar: WĄSKI synchronized (DOBRZE) ===");
        long narrowMillis = runNarrowSynchronizedDemo();
        System.out.println("Wynik licznika (wąski): " + sharedCounterNarrow);
        System.out.println("Czas: " + narrowMillis + " ms");

        System.out.println();
        System.out.println("=== Podsumowanie pomiaru ===");
        System.out.println("Szeroki synchronized:  " + wideMillis + " ms");
        System.out.println("Wąski synchronized:    " + narrowMillis + " ms");
        System.out.println(
                "Uwaga: dokładne czasy zależą od maszyny/JIT, ale zwykle wąski blok\n" +
                "synchronized jest szybszy (lub przynajmniej nie wolniejszy), bo pozwala\n" +
                "kosztownej, niekrytycznej pracy wykonywać się równolegle, a blokadę\n" +
                "trzyma tylko przez czas niezbędny na modyfikację stanu dzielonego.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Sekcja krytyczna = tylko ten kod, który dotyka stanu współdzielonego
         * - Minimalizuj zakres synchronized – im krótsza sekcja krytyczna,
         *   tym mniejsza rywalizacja (contention) między wątkami
         * - Kosztowne obliczenia, I/O, logikę niezależną od stanu dzielonego
         *   trzymaj POZA blokiem synchronized
         * - Zbyt szeroki synchronized nie jest "bardziej bezpieczny" –
         *   jest tak samo poprawny, ale wolniejszy (wątki czekają bez potrzeby)
         * - Zasada: "synchronized tak wąsko, jak to możliwe, tak szeroko,
         *   jak to konieczne"
         */
    }

    /**
     * Demo ŹLE: synchronized obejmuje też niekrytyczną, kosztowną pracę.
     */
    private static long runWideSynchronizedDemo() throws InterruptedException {
        sharedCounterWide = 0;
        int threadsCount = 4;
        int iterations = 20_000;

        Thread[] threads = new Thread[threadsCount];
        long start = System.nanoTime();

        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    synchronized (lockWide) {
                        // ŹLE: niekrytyczna praca WEWNĄTRZ sekcji krytycznej
                        double result = expensiveNonCriticalWork(i);
                        sharedCounterWide++;
                        if (result < -1) {
                            // niedoścignione - tylko żeby JIT nie wyrzucił obliczeń
                            sharedCounterWide--;
                        }
                    }
                }
            });
        }

        for (Thread th : threads) th.start();
        for (Thread th : threads) th.join(10_000); // bounded join - bezpieczeństwo

        return (System.nanoTime() - start) / 1_000_000;
    }

    /**
     * Demo DOBRZE: synchronized obejmuje TYLKO modyfikację stanu dzielonego.
     */
    private static long runNarrowSynchronizedDemo() throws InterruptedException {
        sharedCounterNarrow = 0;
        int threadsCount = 4;
        int iterations = 20_000;

        Thread[] threads = new Thread[threadsCount];
        long start = System.nanoTime();

        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    // DOBRZE: kosztowna praca POZA synchronized - liczy się równolegle
                    double result = expensiveNonCriticalWork(i);

                    synchronized (lockNarrow) {
                        // tylko to jest sekcją krytyczną
                        sharedCounterNarrow++;
                        if (result < -1) {
                            sharedCounterNarrow--;
                        }
                    }
                }
            });
        }

        for (Thread th : threads) th.start();
        for (Thread th : threads) th.join(10_000); // bounded join - bezpieczeństwo

        return (System.nanoTime() - start) / 1_000_000;
    }

    /**
     * Symulacja kosztownej, ale niekrytycznej pracy (nie dotyka stanu dzielonego).
     */
    private static double expensiveNonCriticalWork(int seed) {
        double acc = 0;
        for (int i = 1; i <= 200; i++) {
            acc += Math.sqrt((double) seed * i + 1);
        }
        return acc;
    }
}
