package com.example.javaquest._05_multithreading.Lesson27_Starvation;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class _Lesson27_Starvation {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 STARVATION (GŁODZENIE WĄTKU) – CZYM JEST?
         * ============================================================
         * Starvation to sytuacja, w której KONKRETNY wątek prawie
         * nigdy (albo wcale) nie dostaje dostępu do zasobu (np. blokady,
         * CPU), ponieważ inne wątki – bardziej "chciwe", częstsze albo
         * mające wyższy priorytet – nieustannie go wyprzedzają.
         *
         * W odróżnieniu od deadlocka (wątki zablokowane na zawsze) i
         * livelocka (wątki aktywne, ale bez postępu WSZYSTKICH stron),
         * przy starvation WIĘKSZOŚĆ wątków normalnie robi postęp –
         * problem dotyczy jednego lub kilku "pechowych" wątków, które
         * są systematycznie pomijane.
         *
         * Typowe przyczyny:
         * - zwykły `synchronized` w Javie NIE gwarantuje sprawiedliwości
         *   (fairness) – kolejność przyznawania blokady oczekującym
         *   wątkom jest niezdefiniowana, JVM może faworyzować wątki,
         *   które akurat "częściej pytają",
         * - wątki o niskim priorytecie (Thread.setPriority) rzadziej
         *   dostają czas procesora, gdy konkuruje z nimi wiele wątków
         *   o wysokim priorytecie,
         * - źle zaprojektowane kolejki/algorytmy przydziału zasobów.
         *
         * ⚠️ UWAGA BEZPIECZEŃSTWA: demonstrację uruchamiamy przez
         * OGRANICZONY CZAS (kilka sekund), a nie w nieskończoność –
         * mierzymy, ile razy każdy wątek zdążył wejść do sekcji
         * krytycznej w tym czasie.
         */

        System.out.println("=== 1) Zwykly synchronized – brak gwarancji sprawiedliwosci ===");
        runWithPlainSynchronized();

        System.out.println();
        System.out.println("=== 2) ReentrantLock(true) – fair lock, rownomierny rozklad ===");
        runWithFairLock();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Starvation: konkretny wątek prawie nigdy nie dostaje
         *   dostępu do zasobu, bo inne wątki go stale wyprzedzają.
         * - Zwykły `synchronized` i domyślny `ReentrantLock()` (niesprawiedliwy,
         *   "non-fair") NIE gwarantują kolejności FIFO – "chciwe" wątki
         *   mogą systematycznie wygrywać wyścig o blokadę.
         * - Rozwiązanie: `new ReentrantLock(true)` – tryb "fair" –
         *   przydziela blokadę wątkom w kolejności zgłoszenia (w
         *   przybliżeniu FIFO), kosztem nieco niższej przepustowości.
         * - Inne pomoce: unikanie skrajnie różnych priorytetów wątków,
         *   sprawiedliwe kolejki zadań, ograniczanie czasu trzymania
         *   blokady przez "chciwe" wątki.
         * ⚠️ Fair lock nie jest "darmowy" – zwykle jest wolniejszy niż
         *    non-fair, więc używaj go świadomie, tylko gdy sprawiedliwość
         *    faktycznie się liczy.
         */
    }

    private static final int GREEDY_THREADS = 4;
    private static final long RUN_TIME_MS = 1500;

    /**
     * Kilku "chciwych" wątków non-stop rywalizuje o ten sam obiekt-lock.
     * Jeden konkretny wątek ("Ofiara") startuje na równi z resztą, ale
     * ponieważ zwykły synchronized nie gwarantuje kolejności, w praktyce
     * bywa systematycznie wyprzedzany przez pozostałe wątki.
     */
    private static void runWithPlainSynchronized() throws InterruptedException {
        Object lock = new Object();
        AtomicInteger[] counters = new AtomicInteger[GREEDY_THREADS];
        for (int i = 0; i < GREEDY_THREADS; i++) {
            counters[i] = new AtomicInteger(0);
        }

        Thread[] threads = new Thread[GREEDY_THREADS];
        long deadline = System.currentTimeMillis() + RUN_TIME_MS;

        for (int i = 0; i < GREEDY_THREADS; i++) {
            int idx = i;
            String name = (idx == 0) ? "Ofiara" : "Chciwy-" + idx;
            threads[i] = new Thread(() -> {
                while (System.currentTimeMillis() < deadline) {
                    synchronized (lock) {
                        counters[idx].incrementAndGet();
                        // Krótka "praca" w sekcji krytycznej.
                        busyWaitNanos(50_000);
                    }
                    // Ofiara od razu wraca do kolejkowania się o blokadę
                    // (bez sztucznych opóźnień) – tak jak reszta.
                }
            }, name);
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join(RUN_TIME_MS + 2000);
        }

        for (int i = 0; i < GREEDY_THREADS; i++) {
            String name = (i == 0) ? "Ofiara" : "Chciwy-" + i;
            System.out.println(name + ": wszedl do sekcji krytycznej " + counters[i].get() + " razy");
        }
        System.out.println("(Przy zwyklym synchronized rozklad wejsc bywa bardzo nierowny –");
        System.out.println(" 'Ofiara' moze miec zauwazalnie mniej wejsc niz pozostale watki.)");
    }

    /**
     * Ta sama rywalizacja, ale z ReentrantLock(true) – trybem "fair",
     * który przyznaje blokadę w kolejności zgłoszenia. Rozkład liczby
     * wejść powinien być znacznie bardziej równomierny między wątkami.
     */
    private static void runWithFairLock() throws InterruptedException {
        ReentrantLock fairLock = new ReentrantLock(true); // true = fair
        AtomicInteger[] counters = new AtomicInteger[GREEDY_THREADS];
        for (int i = 0; i < GREEDY_THREADS; i++) {
            counters[i] = new AtomicInteger(0);
        }

        Thread[] threads = new Thread[GREEDY_THREADS];
        long deadline = System.currentTimeMillis() + RUN_TIME_MS;

        for (int i = 0; i < GREEDY_THREADS; i++) {
            int idx = i;
            String name = (idx == 0) ? "Ofiara" : "Chciwy-" + idx;
            threads[i] = new Thread(() -> {
                while (System.currentTimeMillis() < deadline) {
                    fairLock.lock();
                    try {
                        counters[idx].incrementAndGet();
                        busyWaitNanos(50_000);
                    } finally {
                        fairLock.unlock();
                    }
                }
            }, name);
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join(RUN_TIME_MS + 2000);
        }

        for (int i = 0; i < GREEDY_THREADS; i++) {
            String name = (i == 0) ? "Ofiara" : "Chciwy-" + i;
            System.out.println(name + ": wszedl do sekcji krytycznej " + counters[i].get() + " razy");
        }
        System.out.println("(Z fair lockiem liczby wejsc powinny byc znacznie bardziej wyrownane.)");
    }

    /**
     * Prosta zajęta pętla symulująca krótką pracę w sekcji krytycznej,
     * bez usypiania wątku (żeby nie zwalniać locka przez sleep).
     */
    private static void busyWaitNanos(long nanos) {
        long start = System.nanoTime();
        while (System.nanoTime() - start < nanos) {
            // celowo pusta – symulacja krótkiej pracy
        }
    }
}
