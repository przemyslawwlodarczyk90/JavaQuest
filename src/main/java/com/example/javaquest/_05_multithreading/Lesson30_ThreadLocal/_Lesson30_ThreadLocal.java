package com.example.javaquest._05_multithreading.Lesson30_ThreadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson30_ThreadLocal {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 THREADLOCAL<T> – OSOBNA KOPIA DANYCH DLA KAŻDEGO WĄTKU
         * ============================================================
         * `ThreadLocal<T>` pozwala każdemu wątkowi mieć WŁASNĄ, niezależną
         * kopię wartości, mimo że wszystkie wątki odwołują się do TEGO
         * SAMEGO obiektu `ThreadLocal`. To jak "szuflada z etykietą wątku"
         * – każdy wątek widzi tylko swoją szufladę.
         *
         * Podstawowe operacje:
         * - `get()`    – zwraca wartość dla BIEŻĄCEGO wątku (lub wynik
         *                `initialValue()`/dostawcy, jeśli jeszcze nie ustawiono)
         * - `set(val)` – ustawia wartość TYLKO dla bieżącego wątku
         * - `remove()` – usuwa wartość bieżącego wątku (WAŻNE przy pulach wątków!)
         *
         * Kiedy używać?
         * - per-wątkowy `SimpleDateFormat` – ta klasa NIE jest thread-safe,
         *   a tworzenie nowej instancji za każdym razem bywa kosztowne;
         *   ThreadLocal daje po jednej instancji NA WĄTEK, bezpiecznie
         *   współdzielonej wewnątrz tego samego wątku.
         * - per-wątkowy "request ID" / kontekst żądania (np. w serwerach
         *   web) – każdy wątek obsługujący żądanie ma swój identyfikator,
         *   dostępny globalnie bez przekazywania go jako parametr wszędzie.
         *
         * ⚠️ RYZYKO: WYCIEKI PAMIĘCI W THREAD POOLACH
         * W puli wątków (np. ExecutorService) te same wątki są WIELOKROTNIE
         * reużywane do różnych zadań. Jeśli zadanie ustawi wartość w
         * ThreadLocal i NIE wywoła `remove()`, ta wartość "przeżyje" zadanie
         * i będzie nadal widoczna w KOLEJNYM zadaniu wykonywanym przez ten
         * sam wątek – to zarówno bug (wyciek danych między zadaniami), jak
         * i wyciek pamięci (obiekt jest wciąż referencjonowany przez wątek
         * z puli, który żyje bardzo długo, więc GC nie może go zwolnić).
         *
         * ✅ DOBRY WZORZEC: zawsze `remove()` w `finally`:
         *     threadLocal.set(wartosc);
         *     try {
         *         // praca korzystająca z threadLocal.get()
         *     } finally {
         *         threadLocal.remove();
         *     }
         */

        System.out.println("=== 1) Kazdy watek ma wlasna, niezalezna wartosc ===");
        demonstrateIndependentValues();

        System.out.println();
        System.out.println("=== 2) Per-watkowy SimpleDateFormat ===");
        demonstratePerThreadDateFormat();

        System.out.println();
        System.out.println("=== 3) Ryzyko wycieku w thread poolu + poprawny wzorzec remove() w finally ===");
        demonstrateThreadPoolLeakAndFix();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ThreadLocal<T>` daje KAŻDEMU wątkowi osobną kopię wartości,
         *   mimo współdzielenia tego samego obiektu ThreadLocal.
         * - `get()`/`set()` operują na wartości BIEŻĄCEGO wątku, `remove()`
         *   usuwa ją (i pozwala GC odzyskać pamięć).
         * - Świetne dla: per-wątkowy `SimpleDateFormat`, request-id,
         *   kontekst użytkownika w serwerach obsługujących wiele żądań
         *   równolegle wieloma wątkami.
         * - W THREAD POOLACH wątki są reużywane między zadaniami – brak
         *   `remove()` po zadaniu = "przeciek" starej wartości do
         *   kolejnego zadania na tym samym wątku ORAZ wyciek pamięci
         *   (wątek z puli żyje długo, trzyma referencję na zawsze).
         * - ZASADA: zawsze `set()` na początku, `remove()` w `finally` –
         *   szczególnie istotne przy `ExecutorService` i innych pulach
         *   wątków wielokrotnego użytku.
         */
    }

    private static void demonstrateIndependentValues() throws InterruptedException {
        ThreadLocal<Integer> perThreadValue = ThreadLocal.withInitial(() -> 0);

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            // Każdy wątek ustawia WŁASNĄ wartość - nie nadpisuje innych wątków.
            int myValue = name.hashCode() % 100;
            perThreadValue.set(myValue);

            // Symulacja pracy - w międzyczasie inne wątki też ustawiają swoje wartości.
            sleepQuiet(50);

            System.out.println(name + ": moja wartosc w ThreadLocal = " + perThreadValue.get()
                    + " (ustawiona przeze mnie: " + myValue + ")");
        };

        Thread t1 = new Thread(task, "Watek-A");
        Thread t2 = new Thread(task, "Watek-B");
        Thread t3 = new Thread(task, "Watek-C");

        t1.start();
        t2.start();
        t3.start();

        t1.join(2000);
        t2.join(2000);
        t3.join(2000);

        System.out.println("Kazdy watek widzial TYLKO swoja wartosc, mimo wspoldzielenia");
        System.out.println("tego samego obiektu 'perThreadValue' (ThreadLocal).");
    }

    private static void demonstratePerThreadDateFormat() throws InterruptedException {
        // SimpleDateFormat NIE jest thread-safe - współdzielenie jednej
        // instancji między wątkami mogłoby dawać błędne/uszkodzone wyniki.
        // ThreadLocal daje po jednej niezależnej instancji NA WĄTEK.
        ThreadLocal<SimpleDateFormat> perThreadFormat =
                ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        Runnable task = () -> {
            SimpleDateFormat sdf = perThreadFormat.get(); // własna instancja tego wątku
            String formatted = sdf.format(new Date());
            System.out.println(Thread.currentThread().getName() + ": sformatowana data = " + formatted);
        };

        Thread t1 = new Thread(task, "Formatter-1");
        Thread t2 = new Thread(task, "Formatter-2");

        t1.start();
        t2.start();
        t1.join(2000);
        t2.join(2000);

        System.out.println("Kazdy watek uzyl WLASNEJ instancji SimpleDateFormat - brak ryzyka");
        System.out.println("wspoldzielenia niethreadsafe obiektu miedzy watkami.");
    }

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    private static void demonstrateThreadPoolLeakAndFix() throws InterruptedException {
        // Pula z JEDNYM wątkiem - dzięki temu wszystkie "zadania" na pewno
        // wykonają się na tym SAMYM wątku, więc widać reużycie wątku
        // między zadaniami (typowe dla ExecutorService).
        ExecutorService pool = Executors.newFixedThreadPool(1);
        AtomicInteger taskCounter = new AtomicInteger(0);

        System.out.println("--- Zadanie BEZ remove() - wyciek starej wartosci do kolejnego zadania ---");
        Runnable leakyTask = () -> {
            int taskId = taskCounter.incrementAndGet();
            String existing = REQUEST_ID.get();
            System.out.println("Zadanie #" + taskId + " widzi na starcie REQUEST_ID = " + existing
                    + (existing != null ? " (!!! WYCIEK z poprzedniego zadania !!!)" : " (brak, OK)"));
            REQUEST_ID.set("request-" + taskId);
            System.out.println("Zadanie #" + taskId + " ustawilo REQUEST_ID = " + REQUEST_ID.get());
            // BRAK remove() tutaj - celowo, by pokazac problem.
        };

        pool.submit(leakyTask);
        pool.submit(leakyTask);
        shutdownAndWait(pool);

        System.out.println();
        System.out.println("--- Ta sama sytuacja, ale z poprawnym wzorcem remove() w finally ---");

        ExecutorService fixedPool = Executors.newFixedThreadPool(1);
        AtomicInteger fixedCounter = new AtomicInteger(0);

        Runnable properTask = () -> {
            int taskId = fixedCounter.incrementAndGet();
            String existing = REQUEST_ID.get();
            System.out.println("Zadanie #" + taskId + " widzi na starcie REQUEST_ID = " + existing
                    + (existing == null ? " (brak, OK - poprzednie zadanie posprzatalo)" : " (!!! wyciek !!!)"));
            try {
                REQUEST_ID.set("request-" + taskId);
                System.out.println("Zadanie #" + taskId + " ustawilo REQUEST_ID = " + REQUEST_ID.get());
            } finally {
                REQUEST_ID.remove(); // KLUCZOWE: sprzatamy po sobie
            }
        };

        fixedPool.submit(properTask);
        fixedPool.submit(properTask);
        shutdownAndWait(fixedPool);

        System.out.println("Wniosek: 'remove() w finally' zapobiega przeciekaniu wartosci");
        System.out.println("miedzy kolejnymi zadaniami na tym samym, reuzywanym watku z puli.");
    }

    private static void sleepQuiet(long millis) {
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
