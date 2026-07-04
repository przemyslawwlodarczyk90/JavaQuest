package com.example.javaquest._05_multithreading.Lesson23_ScheduledExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson23_ScheduledExecutorService {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 ScheduledExecutorService – ZADANIA "W CZASIE"
         * ============================================================
         * Zwykły ExecutorService uruchamia zadanie "jak najszybciej".
         * Czasem potrzebujemy czegoś więcej:
         * - wykonaj zadanie za X sekund (opóźnienie / delay)
         * - wykonaj zadanie CYKLICZNIE, co jakiś czas (np. odświeżanie
         *   cache co 10s, healthcheck co minutę)
         *
         * Do tego służy ScheduledExecutorService – rozszerzenie
         * ExecutorService o metody planujące zadania w czasie:
         *
         * - schedule(task, delay, unit)
         *     → wykona zadanie JEDNORAZOWO po upływie `delay`
         * - scheduleAtFixedRate(task, initialDelay, period, unit)
         *     → wykonuje zadanie CYKLICZNIE co `period`, licząc od
         *       MOMENTU ROZPOCZĘCIA poprzedniego wywołania
         * - scheduleWithFixedDelay(task, initialDelay, delay, unit)
         *     → wykonuje zadanie CYKLICZNIE, ale odstęp `delay` liczony
         *       jest od MOMENTU ZAKOŃCZENIA poprzedniego wywołania
         *
         * Tworzymy go przez: Executors.newScheduledThreadPool(n)
         */

        System.out.println("=== 1) schedule() – jednorazowe zadanie z opóźnieniem ===");
        demoDelayedSchedule();

        System.out.println("\n=== 2) scheduleAtFixedRate() vs scheduleWithFixedDelay() ===");
        demoFixedRateVsFixedDelay();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ScheduledExecutorService  → ExecutorService + planowanie w czasie
         * - Executors.newScheduledThreadPool(n) → tworzy pulę do tego celu
         * - schedule(task, delay, unit)          → JEDNORAZOWO, po opóźnieniu
         * - scheduleAtFixedRate(task, init, period, unit)
         *     → odstęp liczony od STARTU poprzedniego wywołania;
         *       jeśli zadanie trwa DŁUŻEJ niż `period`, kolejne wywołanie
         *       odpala się od razu po zakończeniu poprzedniego (bez nakładania
         *       się, ale bez zachowania odstępu)
         * - scheduleWithFixedDelay(task, init, delay, unit)
         *     → odstęp liczony od KOŃCA poprzedniego wywołania – zawsze
         *       ta sama "przerwa" między zakończeniem a kolejnym startem,
         *       niezależnie od tego, jak długo trwało zadanie
         * - Zadanie cykliczne NIGDY nie kończy się samo – MUSISZ je
         *   zatrzymać (np. future.cancel(false) po N powtórzeniach) i
         *   ZAWSZE zamknąć pulę przez shutdown()+awaitTermination(),
         *   inaczej program będzie "wisiał" w nieskończoność.
         */
    }

    /**
     * schedule() – zadanie jednorazowe, wykonane dopiero po upływie
     * zadanego opóźnienia (delay), nie wcześniej.
     */
    private static void demoDelayedSchedule() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long start = System.nanoTime();

        System.out.println("[main] zaplanowano zadanie za 200ms, o t=0ms");
        ScheduledFuture<?> future = scheduler.schedule(() -> {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[Zadanie] wykonane po ~" + elapsedMs + "ms (oczekiwano ~200ms)");
        }, 200, TimeUnit.MILLISECONDS);

        awaitFutureQuietly(future);
        shutdownAndWait(scheduler);
    }

    /**
     * Różnica między scheduleAtFixedRate a scheduleWithFixedDelay,
     * gdy samo zadanie trwa dłużej niż zadany okres/opóźnienie.
     * Mierzymy realne momenty startu kolejnych wywołań.
     */
    private static void demoFixedRateVsFixedDelay() throws InterruptedException {
        int taskDurationMs = 100; // zadanie "pracuje" 100ms
        int periodMs = 150;       // planowany okres/odstęp: 150ms

        System.out.println("-- scheduleAtFixedRate: period=" + periodMs + "ms, zadanie trwa " + taskDurationMs + "ms --");
        System.out.println("   (odstęp START->START ~ okresowi, bo zadanie krótsze niż period)");
        runFixedRate(taskDurationMs, periodMs, 3);

        System.out.println("-- scheduleWithFixedDelay: delay=" + periodMs + "ms, zadanie trwa " + taskDurationMs + "ms --");
        System.out.println("   (odstęp KONIEC->START zawsze = delay, więc START->START ~ delay + czas zadania)");
        runFixedDelay(taskDurationMs, periodMs, 3);
    }

    private static void runFixedRate(int taskDurationMs, int periodMs, int repeats) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long start = System.nanoTime();
        AtomicInteger count = new AtomicInteger(0);

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            int run = count.incrementAndGet();
            long startedAtMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[FixedRate] wywołanie #" + run + " startuje o t=" + startedAtMs + "ms");
            sleepQuietly(taskDurationMs);
        }, 0, periodMs, TimeUnit.MILLISECONDS);

        // Poczekaj aż zadanie wykona się `repeats` razy, potem zatrzymaj cykl
        while (count.get() < repeats) {
            sleepQuietly(20);
        }
        future.cancel(false); // zatrzymujemy powtarzanie – inaczej trwałoby wiecznie

        shutdownAndWait(scheduler);
    }

    private static void runFixedDelay(int taskDurationMs, int delayMs, int repeats) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long start = System.nanoTime();
        AtomicInteger count = new AtomicInteger(0);

        ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(() -> {
            int run = count.incrementAndGet();
            long startedAtMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[FixedDelay] wywołanie #" + run + " startuje o t=" + startedAtMs + "ms");
            sleepQuietly(taskDurationMs);
        }, 0, delayMs, TimeUnit.MILLISECONDS);

        while (count.get() < repeats) {
            sleepQuietly(20);
        }
        future.cancel(false); // zatrzymujemy powtarzanie zadania cyklicznego

        shutdownAndWait(scheduler);
    }

    private static void awaitFutureQuietly(ScheduledFuture<?> future) {
        try {
            future.get();
        } catch (Exception e) {
            System.out.println("Błąd podczas oczekiwania na zadanie: " + e);
        }
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Wzorcowe, bezpieczne zamykanie ExecutorService (także ScheduledExecutorService,
     * bo to jego podinterfejs) – zawsze z timeoutem w awaitTermination.
     */
    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
