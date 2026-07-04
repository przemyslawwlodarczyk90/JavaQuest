package com.example.javaquest._05_multithreading.Lesson19_ReadWriteLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _Lesson19_ReadWriteLock {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROBLEM: ZWYKŁY LOCK BLOKUJE TAKŻE CZYTELNIKÓW NAWZAJEM
         * ============================================================
         * Znamy już `synchronized` i `ReentrantLock` – zapewniają wzajemne
         * wykluczanie (mutual exclusion): tylko JEDEN wątek naraz może
         * wykonywać chroniony kod.
         *
         * To bezpieczne, ale często NADMIAROWE. Wyobraź sobie cache
         * konfiguracji aplikacji:
         * - odczyty (read) są BARDZO CZĘSTE i NIE zmieniają danych,
         * - zapisy (write) są RZADKIE (np. przeładowanie configu raz na jakiś czas).
         *
         * Wiele wątków czytających JEDNOCZEŚNIE nie stwarza żadnego problemu
         * – dopóki nikt w tym czasie nie modyfikuje danych. Zwykły
         * ReentrantLock tego nie wie – serializuje WSZYSTKIE wątki,
         * także same odczyty, co niepotrzebnie ogranicza przepustowość.
         *
         * ✅ ROZWIĄZANIE: ReadWriteLock (ReentrantReadWriteLock)
         * - readLock()  – blokada "do odczytu": WIELU czytelników może
         *                 trzymać ją jednocześnie (współdzielona / shared)
         * - writeLock() – blokada "do zapisu": TYLKO JEDEN pisarz, i to
         *                 wykluczający się ze WSZYSTKIMI czytelnikami
         *                 (wyłączna / exclusive)
         *
         * Zasady ReentrantReadWriteLock:
         * - wielu czytelników + 0 pisarzy → OK, wszyscy czytają równolegle
         * - 1 pisarz + 0 czytelników      → OK, pisze sam
         * - pisarz + czytelnicy razem     → ❌ NIEMOŻLIWE, ktoś czeka
         * - wielu pisarzy razem           → ❌ NIEMOŻLIWE, piszą po kolei
         *
         * Kiedy to ma sens?
         * - dane rzadko modyfikowane, ale często czytane (cache, konfiguracja,
         *   słowniki, tablice referencyjne)
         * - NIE ma sensu przy częstych zapisach – narzut zarządzania dwiema
         *   blokadami może wtedy przewyższyć korzyści
         */

        System.out.println("=== 1) ReentrantLock – czytelnicy blokują się WZAJEMNIE ===");
        runWithPlainLock();

        System.out.println("\n=== 2) ReentrantReadWriteLock – czytelnicy czytają RÓWNOLEGLE ===");
        runWithReadWriteLock();

        System.out.println("\n=== 3) Pisarz wyklucza czytelników (i innych pisarzy) ===");
        runWriterBlocksReaders();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ReentrantLock            → jedna blokada dla wszystkich,
         *                               czytelnicy niepotrzebnie się blokują
         * - ReentrantReadWriteLock    → dwie blokady: readLock() (współdzielona)
         *                               i writeLock() (wyłączna)
         * - Wielu czytelników jednocześnie = wyższa przepustowość odczytu
         * - Pisarz zawsze ma wyłączny dostęp – żaden czytelnik ani inny
         *   pisarz nie działa w tym samym czasie
         * - Świetne dla: cache konfiguracji, dane referencyjne, słowniki
         *   rzadko aktualizowane, a często odpytywane
         * - Słabe dla: dane modyfikowane równie często jak czytane –
         *   wtedy zwykły ReentrantLock (lub inne mechanizmy) mogą wystarczyć
         * - Zawsze zwalniaj blokadę w finally – niezależnie, czy to
         *   readLock(), czy writeLock()!
         */
    }

    /**
     * Trzech "czytelników" korzysta z tego samego ReentrantLock.
     * Ponieważ blokada jest wyłączna, czytania wykonują się PO KOLEI
     * (serializacja) – łączny czas ≈ suma czasów pojedynczych odczytów.
     */
    private static void runWithPlainLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        long start = System.nanoTime();

        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            int readerId = i;
            pool.submit(() -> simulateRead(lock, readerId));
        }

        shutdownAndWait(pool);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Łączny czas 3 odczytów (ReentrantLock): " + elapsedMs + " ms (~serializacja, ok. 3x150ms)");
    }

    private static void simulateRead(Lock lock, int readerId) {
        lock.lock();
        try {
            System.out.println("[Czytelnik-" + readerId + "] zaczyna czytać o t=" + elapsedFromClassLoad() + "ms");
            sleepQuietly(150);
            System.out.println("[Czytelnik-" + readerId + "] kończy czytać");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Trzech czytelników korzysta z ReentrantReadWriteLock.readLock().
     * Blokada odczytu jest WSPÓŁDZIELONA – wszyscy trzej mogą czytać
     * jednocześnie. Łączny czas ≈ czas JEDNEGO odczytu, a nie sumy.
     */
    private static void runWithReadWriteLock() throws InterruptedException {
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock readLock = rwLock.readLock();
        long start = System.nanoTime();

        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            int readerId = i;
            pool.submit(() -> simulateRead(readLock, readerId));
        }

        shutdownAndWait(pool);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Łączny czas 3 odczytów (readLock): " + elapsedMs + " ms (~równolegle, ok. 150ms, NIE 450ms)");
    }

    /**
     * Pokazuje wyłączność writeLock(): pisarz blokuje zarówno nowych
     * czytelników, jak i innych pisarzy, dopóki nie skończy zapisu.
     */
    private static void runWriterBlocksReaders() throws InterruptedException {
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();

        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Pisarz startuje pierwszy i trzyma writeLock przez 200ms
        pool.submit(() -> {
            writeLock.lock();
            try {
                System.out.println("[Pisarz] rozpoczyna zapis (blokuje wszystkich czytelników)");
                sleepQuietly(200);
                System.out.println("[Pisarz] kończy zapis");
            } finally {
                writeLock.unlock();
            }
        });

        sleepQuietly(30); // dajemy pisarzowi chwilę na przejęcie blokady jako pierwszy

        // Dwaj czytelnicy próbują czytać w międzyczasie – muszą poczekać na pisarza
        for (int i = 1; i <= 2; i++) {
            int readerId = i;
            pool.submit(() -> {
                System.out.println("[Czytelnik-" + readerId + "] próbuje wejść (może czekać na pisarza)");
                readLock.lock();
                try {
                    System.out.println("[Czytelnik-" + readerId + "] czyta (pisarz już skończył)");
                } finally {
                    readLock.unlock();
                }
            });
        }

        shutdownAndWait(pool);
        System.out.println("Wniosek: czytelnicy weszli dopiero PO zakończeniu zapisu.");
    }

    private static final long CLASS_LOAD_NANOS = System.nanoTime();

    private static long elapsedFromClassLoad() {
        return (System.nanoTime() - CLASS_LOAD_NANOS) / 1_000_000;
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Wzorcowe, bezpieczne zamykanie ExecutorService – zawsze z timeoutem,
     * nigdy bez awaitTermination (inaczej JVM mógłby "wisieć" w nieskończoność).
     */
    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
