package com.example.javaquest._05_multithreading.Lesson22_CallableAndFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class _Lesson22_CallableAndFuture {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 CALLABLE – RUNNABLE, KTÓRY POTRAFI COŚ ZWRÓCIĆ
         * ============================================================
         * Runnable.run() – nie zwraca wyniku (void) i nie może rzucić
         * checked exception (tylko unchecked, np. RuntimeException).
         *
         * Callable<V>.call() – zwraca wynik typu V i MOŻE zadeklarować
         * `throws Exception` (dowolny checked wyjątek). Idealny gdy
         * zadanie ma policzyć/pobrać coś i to coś oddać wywołującemu.
         *
         * interface Callable<V> {
         *     V call() throws Exception;
         * }
         *
         * ExecutorService.submit(Callable<V>) zwraca Future<V> –
         * "obietnicę" wyniku, który pojawi się W PRZYSZŁOŚCI, gdy
         * zadanie się zakończy.
         *
         * Future<V>:
         * - get()                → BLOKUJE bieżący wątek, aż wynik będzie
         *                          gotowy (albo zadanie rzuci wyjątek)
         * - get(timeout, unit)   → blokuje, ale MAKSYMALNIE do timeoutu –
         *                          po jego przekroczeniu rzuca TimeoutException
         * - isDone()             → true, jeśli zadanie się zakończyło
         *                          (sukcesem, wyjątkiem albo anulowaniem)
         * - cancel(mayInterrupt) → próbuje anulować zadanie
         * - isCancelled()        → true, jeśli zostało anulowane
         */

        System.out.println("=== 1) Callable vs Runnable – podstawy ===");
        demoCallableBasics();

        System.out.println("\n=== 2) get() blokujący i z timeoutem ===");
        demoGetBlockingAndTimeout();

        System.out.println("\n=== 3) Wyjątek wewnątrz zadania -> ExecutionException ===");
        demoExecutionException();

        System.out.println("\n=== 4) isDone() i cancel() ===");
        demoIsDoneAndCancel();

        System.out.println("\n=== 5) Wiele zadań liczących sumy zakresów, zbieranie wyników ===");
        demoMultipleTasksCollectResults();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Runnable  → run(): void, brak checked exceptions
         * - Callable<V> → call(): V, może rzucić checked Exception
         * - submit(Callable<V>) → Future<V> – "obietnica" wyniku
         * - future.get()          → blokuje do zakończenia zadania
         * - future.get(t, unit)   → blokuje maks. t, inaczej TimeoutException
         * - wyjątek RZUCONY WEWNĄTRZ zadania nie "wybucha" w wątku puli –
         *   zostaje OPAKOWANY w ExecutionException i wyrzucony dopiero
         *   przy wywołaniu get(). Oryginalny wyjątek -> exception.getCause()
         * - isDone()   → sprawdza zakończenie (sukces/wyjątek/anulowanie)
         *   bez blokowania wątku
         * - cancel(true)  → próbuje przerwać trwające zadanie (interrupt)
         * - cancel(false) → anuluje tylko jeśli zadanie jeszcze się NIE zaczęło
         * - typowy wzorzec: submit wiele Callable do listy Future, potem
         *   w pętli wywołać get() na każdym, by zebrać wszystkie wyniki
         */
    }

    private static void demoCallableBasics() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Callable<Integer> callable = () -> {
            System.out.println("[Callable] liczę coś na " + Thread.currentThread().getName());
            return 21 * 2;
        };

        Future<Integer> future = pool.submit(callable);
        try {
            Integer result = future.get();
            System.out.println("Wynik Callable: " + result);
        } catch (ExecutionException e) {
            System.out.println("Błąd wykonania: " + e.getCause());
        }

        shutdownAndWait(pool);
    }

    private static void demoGetBlockingAndTimeout() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        System.out.println("-- get() bez timeoutu: blokuje aż zadanie skończy 200ms pracy --");
        Future<String> future1 = pool.submit(() -> {
            Thread.sleep(200);
            return "gotowe po 200ms";
        });
        try {
            long start = System.nanoTime();
            String result = future1.get(); // czeka tyle, ile potrzeba
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Wynik: " + result + " (czekaliśmy ~" + elapsedMs + "ms)");
        } catch (ExecutionException e) {
            System.out.println("Błąd: " + e.getCause());
        }

        System.out.println("-- get(timeout) krótszy niż czas zadania -> TimeoutException --");
        Future<String> future2 = pool.submit(() -> {
            Thread.sleep(300);
            return "gotowe po 300ms";
        });
        try {
            String result = future2.get(50, TimeUnit.MILLISECONDS); // za mało czasu!
            System.out.println("Wynik: " + result);
        } catch (TimeoutException e) {
            System.out.println("Złapano TimeoutException – zadanie jeszcze nie skończyło pracy");
        } catch (ExecutionException e) {
            System.out.println("Błąd: " + e.getCause());
        } finally {
            future2.cancel(true); // sprzątamy, nie interesuje nas już ten wynik
        }

        shutdownAndWait(pool);
    }

    /**
     * Wyjątek rzucony WEWNĄTRZ zadania (Callable/Runnable w submit) nie
     * "wylatuje" natychmiast w wątku roboczym – jest zapamiętany przez
     * Future i rzucony dopiero przy get(), opakowany w ExecutionException.
     * Prawdziwą przyczynę odzyskujemy przez getCause().
     */
    private static void demoExecutionException() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        Future<Integer> future = pool.submit(() -> {
            System.out.println("[Zadanie] zaraz rzucę wyjątek...");
            if (true) {
                throw new IllegalStateException("Coś poszło nie tak wewnątrz zadania!");
            }
            return 42;
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Złapano ExecutionException: " + e.getMessage());
            System.out.println("Oryginalny wyjątek (getCause()): " + e.getCause());
        }

        shutdownAndWait(pool);
    }

    private static void demoIsDoneAndCancel() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<String> quickTask = pool.submit(() -> "szybki wynik");
        sleepQuietly(50); // dajemy czas na wykonanie
        System.out.println("Szybkie zadanie zakończone? isDone()=" + quickTask.isDone());

        Future<String> longTask = pool.submit(() -> {
            Thread.sleep(1000);
            return "długi wynik";
        });
        System.out.println("Długie zadanie zakończone od razu? isDone()=" + longTask.isDone());

        boolean cancelled = longTask.cancel(true); // przerywamy zanim skończy
        System.out.println("Anulowano długie zadanie? " + cancelled + ", isCancelled()=" + longTask.isCancelled());

        shutdownAndWait(pool);
    }

    /**
     * Kilka zadań Callable liczy sumę różnych zakresów liczb. Zbieramy
     * wszystkie Future do listy, a potem w pętli wywołujemy get(), by
     * odebrać wyniki w kolejności zgłoszenia zadań.
     */
    private static void demoMultipleTasksCollectResults() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        List<Future<Long>> futures = new ArrayList<>();
        int[][] ranges = {{1, 1000}, {1001, 2000}, {2001, 3000}, {3001, 4000}};

        for (int[] range : ranges) {
            int from = range[0];
            int to = range[1];
            Callable<Long> sumTask = () -> {
                long sum = 0;
                for (int n = from; n <= to; n++) {
                    sum += n;
                }
                return sum;
            };
            futures.add(pool.submit(sumTask));
        }

        long total = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                long partialSum = futures.get(i).get();
                System.out.println("Suma zakresu " + ranges[i][0] + "-" + ranges[i][1] + " = " + partialSum);
                total += partialSum;
            } catch (ExecutionException e) {
                System.out.println("Błąd w zadaniu " + i + ": " + e.getCause());
            }
        }
        System.out.println("Suma łączna (1..4000): " + total);

        shutdownAndWait(pool);
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
