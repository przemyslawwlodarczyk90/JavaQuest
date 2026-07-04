package com.example.javaquest._05_multithreading.Lesson31_ForkJoinPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class _Lesson31_ForkJoinPool {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 ForkJoinPool – "DZIEL I ZWYCIĘŻAJ" DLA WSPÓŁBIEŻNOŚCI
         * ============================================================
         * ForkJoinPool to specjalny rodzaj puli wątków (z java.util.concurrent),
         * zaprojektowany do zadań, które można rekurencyjnie DZIELIĆ na
         * mniejsze podzadania, rozwiązywać je RÓWNOLEGLE, a potem ŁĄCZYĆ
         * (scalać) wyniki. To klasyczny wzorzec "divide and conquer"
         * (dziel i zwyciężaj), znany z algorytmów typu merge sort.
         *
         * Idea:
         * 1. Jeśli zadanie jest "duże" -> podziel je na 2 (lub więcej)
         *    mniejsze podzadania (fork).
         * 2. Rozwiąż podzadania rekurencyjnie – jeśli nadal są duże,
         *    dziel dalej, aż dojdziesz do progu (threshold), przy którym
         *    taniej jest po prostu policzyć wynik bezpośrednio.
         * 3. Poczekaj na wyniki podzadań i połącz je (join).
         *
         * Dwie główne klasy zadań:
         * - RecursiveTask<V>   – ZWRACA wynik (np. sumę fragmentu tablicy).
         * - RecursiveAction    – NIE zwraca wyniku (np. modyfikuje tablicę
         *                        w miejscu – sortowanie, przemnożenie itd).
         */

        System.out.println("=== 1) RecursiveTask<V> – równoległe sumowanie tablicy ===");
        demoRecursiveTaskSum();

        System.out.println("\n=== 2) RecursiveAction – równoległe podwojenie elementów tablicy w miejscu ===");
        demoRecursiveActionDouble();

        System.out.println("\n=== 3) commonPool() vs własny ForkJoinPool ===");
        demoCommonPoolVsCustomPool();

        /*
         * ============================================================
         * 🔍 WORK STEALING – JAK TO DZIAŁA "POD SPODEM"?
         * ============================================================
         * ForkJoinPool działa wg algorytmu WORK STEALING:
         * - Każdy wątek roboczy ma WŁASNĄ kolejkę zadań (deque).
         * - Wątek pobiera zadania z WŁASNEJ kolejki od "głowy" (LIFO) –
         *   to zadania, które sam właśnie utworzył przez fork().
         * - Gdy wątek skończy swoje zadania i jego kolejka jest pusta,
         *   próbuje "ukraść" (steal) zadanie z KOŃCA kolejki INNEGO,
         *   zajętego wątku (FIFO od tamtej strony).
         * Dzięki temu żaden wątek nie stoi bezczynnie, dopóki gdziekolwiek
         * jest jeszcze praca do zrobienia – obciążenie samo się balansuje,
         * bez centralnego "kierownika" rozdzielającego zadania.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ForkJoinPool to pula wątków pod wzorzec "dziel i zwyciężaj":
         *   fork() dzieli zadanie, join() czeka na wynik podzadania.
         * - RecursiveTask<V>  -> zadanie ZWRACAJĄCE wynik (np. suma, min, max).
         * - RecursiveAction   -> zadanie BEZ wyniku (np. modyfikacja w miejscu).
         * - Zawsze ustawiaj PRÓG (threshold) – poniżej niego liczymy wynik
         *   bezpośrednio, zamiast dzielić dalej (narzut dzielenia > zysk).
         * - Work stealing: bezczynne wątki "kradną" zadania z kolejek
         *   zajętych wątków – automatyczny, zdecentralizowany balans obciążenia.
         * - ForkJoinPool.commonPool() to WSPÓLNA, globalna pula (używana
         *   też np. przez równoległe Stream API) – wygodna na szybko, ale
         *   dzielona z całą aplikacją. Własny `new ForkJoinPool(n)` daje
         *   izolację i kontrolę nad liczbą wątków dla krytycznych zadań.
         * - Pulę WŁASNĄ zawsze trzeba zamknąć: shutdown()/awaitTermination
         *   (commonPool() zarządzany jest przez JVM i nie wymaga zamykania).
         */
    }

    /**
     * RecursiveTask<Long> – rekurencyjne, równoległe sumowanie dużej tablicy
     * liczb. Tablica jest dzielona na połowy, aż fragment jest mniejszy niż
     * THRESHOLD – wtedy liczymy sumę bezpośrednio (sekwencyjnie).
     */
    private static void demoRecursiveTaskSum() {
        int size = 10_000_000;
        long[] numbers = new long[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1L; // 1, 2, 3, ..., size
        }

        ForkJoinPool pool = new ForkJoinPool();
        try {
            SumTask rootTask = new SumTask(numbers, 0, numbers.length);
            long start = System.nanoTime();
            long result = pool.invoke(rootTask); // fork+join dzieje się wewnątrz
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;

            long expected = (long) size * (size + 1) / 2; // wzór na sumę 1..n
            System.out.println("Suma " + size + " liczb = " + result
                    + " (oczekiwane: " + expected + "), czas: " + elapsedMs + " ms");
        } finally {
            shutdownAndWait(pool);
        }
    }

    /** Próg, poniżej którego liczymy sumę bezpośrednio zamiast dzielić dalej. */
    private static final int SUM_THRESHOLD = 10_000;

    private static class SumTask extends RecursiveTask<Long> {
        private final long[] array;
        private final int start;
        private final int end; // wyłącznie (exclusive)

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= SUM_THRESHOLD) {
                // Zadanie wystarczająco małe -> liczymy bezpośrednio (bez dalszego dzielenia)
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }

            // Dzielimy zadanie na 2 połowy
            int mid = start + length / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            leftTask.fork();              // lewą połowę oddajemy do puli (inny wątek może ją podjąć/ukraść)
            long rightResult = rightTask.compute(); // prawą liczymy w BIEŻĄCYM wątku (unikamy zbędnego fork())
            long leftResult = leftTask.join();       // czekamy na wynik lewej połowy

            return leftResult + rightResult;
        }
    }

    /**
     * RecursiveAction – NIE zwraca wyniku, tylko modyfikuje tablicę W MIEJSCU:
     * każdy element zostaje podwojony. Podział taki sam jak w RecursiveTask,
     * ale wynikiem `compute()` jest void.
     */
    private static void demoRecursiveActionDouble() {
        int size = 20;
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = i + 1; // 1..20
        }
        System.out.println("Przed:  " + java.util.Arrays.toString(values));

        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(new DoubleAction(values, 0, values.length));
        } finally {
            shutdownAndWait(pool);
        }
        System.out.println("Po x2:  " + java.util.Arrays.toString(values));
    }

    private static final int DOUBLE_THRESHOLD = 5;

    private static class DoubleAction extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;

        DoubleAction(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int length = end - start;
            if (length <= DOUBLE_THRESHOLD) {
                for (int i = start; i < end; i++) {
                    array[i] = array[i] * 2;
                }
                return;
            }

            int mid = start + length / 2;
            DoubleAction leftAction = new DoubleAction(array, start, mid);
            DoubleAction rightAction = new DoubleAction(array, mid, end);

            // invokeAll() forkuje i joinuje obie akcje za jednym razem
            invokeAll(leftAction, rightAction);
        }
    }

    /**
     * ForkJoinPool.commonPool() – współdzielona, globalna pula (leniwie
     * tworzona przez JVM, rozmiar domyślnie = liczba rdzeni - 1). Wygodna,
     * bo nie trzeba jej ręcznie zamykać, ale zadania dzielą ją z innymi
     * mechanizmami (np. parallelStream()). Własny pool daje izolację.
     */
    private static void demoCommonPoolVsCustomPool() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("commonPool() - poziom równoległości: " + commonPool.getParallelism()
                + " (współdzielony w całej JVM, NIE zamykamy go ręcznie)");

        long[] data = new long[1_000_000];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        long sumViaCommonPool = commonPool.invoke(new SumTask(data, 0, data.length));
        System.out.println("Suma policzona przez commonPool(): " + sumViaCommonPool);

        // Własny pool -> pełna kontrola nad liczbą wątków, ale TRZEBA go zamknąć
        ForkJoinPool customPool = new ForkJoinPool(2); // np. tylko 2 wątki robocze
        try {
            long sumViaCustomPool = customPool.invoke(new SumTask(data, 0, data.length));
            System.out.println("Suma policzona przez wlasny pool (2 watki): " + sumViaCustomPool);
        } finally {
            shutdownAndWait(customPool); // WŁASNY pool zawsze zamykamy
        }
    }

    private static void shutdownAndWait(ForkJoinPool pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            pool.shutdownNow();
        }
    }
}
