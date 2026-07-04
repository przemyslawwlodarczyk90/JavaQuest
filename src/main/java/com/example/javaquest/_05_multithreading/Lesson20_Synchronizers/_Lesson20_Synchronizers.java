package com.example.javaquest._05_multithreading.Lesson20_Synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson20_Synchronizers {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 SYNCHRONIZATORY z java.util.concurrent
         * ============================================================
         * Ten temat NIE jest z oryginalnego sylabusu, ale to jedne
         * z najczęściej spotykanych klas przy koordynacji wątków w
         * praktyce – warto je znać obok Lock/ReadWriteLock.
         *
         * Trzy podstawowe "synchronizatory":
         * - CountDownLatch → jednorazowa "brama": czekaj aż licznik
         *   spadnie do zera (np. main czeka aż N workerów skończy).
         * - CyclicBarrier  → punkt spotkania: N wątków czeka na SIEBIE
         *   NAWZAJEM, zanim wszystkie ruszą dalej RAZEM. Może być użyty
         *   wielokrotnie (stąd "cyclic").
         * - Semaphore      → ogranicza liczbę wątków mających jednoczesny
         *   dostęp do zasobu (pula N "biletów"/slotów).
         */

        System.out.println("=== 1) CountDownLatch – czekanie na zakończenie N zadań ===");
        demoCountDownLatch();

        System.out.println("\n=== 2) CyclicBarrier – punkt synchronizacji wieloetapowej symulacji ===");
        demoCyclicBarrier();

        System.out.println("\n=== 3) Semaphore – ograniczona pula slotów do zasobu ===");
        demoSemaphore();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CountDownLatch(n)  → countDown() zmniejsza licznik, await()
         *   czeka aż licznik osiągnie 0. JEDNORAZOWY – nie da się zresetować.
         *   Typowe użycie: main/koordynator czeka aż N workerów zakończy
         *   inicjalizację/pracę.
         * - CyclicBarrier(n, akcjaPoBarierze) → await() blokuje wątek aż
         *   wszystkie n wątków go wywoła; wtedy WSZYSTKIE ruszają razem
         *   (opcjonalnie wykonując wspólną akcję). Bariera resetuje się
         *   automatycznie – można jej użyć w kolejnych "rundach"/etapach.
         * - Semaphore(n) → acquire()/release() kontrolują liczbę wątków
         *   mających dostęp jednocześnie (maks. n na raz). Nadmiarowe
         *   wątki czekają na acquire() aż ktoś zwolni slot przez release().
         *
         * Wszystkie trzy pochodzą z java.util.concurrent i są dużo
         * wygodniejsze niż ręczne synchronized/wait/notify do tych zadań.
         */
    }

    /**
     * CountDownLatch – main odpala 4 "workerów", którzy inicjalizują się
     * asynchronicznie (symulowane przez sleep), i CZEKA aż wszyscy skończą,
     * zanim wypisze komunikat "system gotowy".
     */
    private static void demoCountDownLatch() throws InterruptedException {
        int workerCount = 4;
        CountDownLatch initLatch = new CountDownLatch(workerCount);

        ExecutorService pool = Executors.newFixedThreadPool(workerCount);
        for (int i = 1; i <= workerCount; i++) {
            int workerId = i;
            pool.submit(() -> {
                try {
                    System.out.println("[Worker-" + workerId + "] inicjalizuje się...");
                    Thread.sleep(50L * workerId); // różny czas startu
                    System.out.println("[Worker-" + workerId + "] gotowy!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    initLatch.countDown(); // zawsze zmniejsz licznik, nawet przy błędzie
                }
            });
        }

        System.out.println("[main] czeka aż wszyscy workerzy skończą inicjalizację...");
        boolean allReady = initLatch.await(5, TimeUnit.SECONDS); // timeout – bezpiecznik
        System.out.println("[main] wszyscy gotowi? " + allReady + " -> system gotowy do pracy!");

        shutdownAndWait(pool);
    }

    /**
     * CyclicBarrier – symulacja wieloetapowa: 3 "gracze" muszą ukończyć
     * ETAP 1, poczekać na siebie nawzajem, dopiero wtedy wszyscy razem
     * przechodzą do ETAPU 2. Bariera jest re-używana między etapami.
     */
    private static void demoCyclicBarrier() throws InterruptedException {
        int playerCount = 3;
        AtomicInteger stageCounter = new AtomicInteger(1);
        CyclicBarrier barrier = new CyclicBarrier(playerCount,
                () -> System.out.println(">>> Wszyscy gracze ukończyli etap " + stageCounter.getAndIncrement() + " – ruszamy dalej razem! <<<"));

        ExecutorService pool = Executors.newFixedThreadPool(playerCount);
        for (int i = 1; i <= playerCount; i++) {
            int playerId = i;
            pool.submit(() -> {
                try {
                    for (int stage = 1; stage <= 2; stage++) {
                        Thread.sleep(30L * playerId); // każdy gracz kończy etap w innym tempie
                        System.out.println("[Gracz-" + playerId + "] ukończył etap " + stage + ", czeka na resztę");
                        barrier.await(5, TimeUnit.SECONDS); // czeka aż WSZYSCY dojdą do bariery
                    }
                    System.out.println("[Gracz-" + playerId + "] zakończył całą symulację");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException | java.util.concurrent.TimeoutException e) {
                    System.out.println("[Gracz-" + playerId + "] bariera przerwana/timeout: " + e);
                }
            });
        }

        shutdownAndWait(pool);
    }

    /**
     * Semaphore(2) – tylko 2 "sloty" dostępu do zasobu (np. 2 równoległe
     * połączenia do zewnętrznego API), a chętnych wątków jest 5. Reszta
     * czeka na acquire() aż ktoś zwolni slot przez release().
     */
    private static void demoSemaphore() throws InterruptedException {
        int totalSlots = 2;
        int totalThreads = 5;
        Semaphore semaphore = new Semaphore(totalSlots);

        ExecutorService pool = Executors.newFixedThreadPool(totalThreads);
        for (int i = 1; i <= totalThreads; i++) {
            int taskId = i;
            pool.submit(() -> {
                try {
                    System.out.println("[Zadanie-" + taskId + "] czeka na wolny slot (dostępne: " + semaphore.availablePermits() + ")");
                    semaphore.acquire();
                    try {
                        System.out.println("[Zadanie-" + taskId + "] otrzymało slot i korzysta z zasobu");
                        Thread.sleep(100);
                        System.out.println("[Zadanie-" + taskId + "] zwalnia slot");
                    } finally {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        shutdownAndWait(pool);
        System.out.println("Wszystkie zadania obsłużone przez max " + totalSlots + " równoległe sloty naraz.");
    }

    /**
     * Wzorcowe, bezpieczne zamykanie ExecutorService – zawsze z timeoutem.
     */
    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
