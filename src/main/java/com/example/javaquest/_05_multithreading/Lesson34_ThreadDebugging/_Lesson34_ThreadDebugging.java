package com.example.javaquest._05_multithreading.Lesson34_ThreadDebugging;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class _Lesson34_ThreadDebugging {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 DEBUGOWANIE WĄTKÓW – DLACZEGO TO TRUDNIEJSZE NIŻ ZWYKŁY KOD?
         * ============================================================
         * W programie jednowątkowym stack trace i logi mają jasną,
         * liniową historię zdarzeń. W programie wielowątkowym dziesiątki
         * wątków wykonują się NIEZALEŻNIE i RÓWNOLEGLE, a ich logi
         * przeplatają się ze sobą w konsoli w nieprzewidywalnej kolejności.
         * Bez dodatkowych informacji trudno stwierdzić, KTÓRY wątek
         * wypisał daną linijkę albo gdzie dokładnie coś "utknęło".
         */

        System.out.println("=== 1) Nazywanie wątków – czytelne logi i stack trace ===");
        demoNamedThreads();

        System.out.println("\n=== 2) Logowanie Thread.currentThread().getName() ===");
        demoLoggingCurrentThreadName();

        System.out.println("\n=== 3) Thread dump – Thread.getAllStackTraces() jako odpowiednik jstack ===");
        demoThreadDump();

        /*
         * ============================================================
         * 🔍 CO TO JEST "THREAD DUMP"?
         * ============================================================
         * Thread dump to migawka (snapshot) stanu WSZYSTKICH wątków w
         * danej JVM w konkretnym momencie – dla każdego wątku pokazuje
         * jego nazwę, stan (RUNNABLE, BLOCKED, WAITING, TIMED_WAITING...)
         * oraz pełny stos wywołań (stack trace), czyli DOKŁADNIE w którym
         * miejscu kodu wątek się teraz znajduje.
         *
         * To NARZĘDZIE DIAGNOSTYCZNE numer jeden przy problemach typu:
         * - aplikacja "zawiesiła się" (deadlock, zakleszczenie),
         * - wątek utknął w nieskończonej pętli albo długo czeka na I/O,
         * - trzeba sprawdzić, ile wątków w ogóle działa i co robią.
         *
         * Poza kodem Java, thread dump najczęściej pobiera się narzędziami
         * zewnętrznymi dołączonymi do JDK:
         * - `jstack <PID>`  – narzędzie z linii poleceń, dołącza się do
         *   działającego procesu JVM po jego PID i wypisuje pełny dump
         *   wszystkich wątków (włącznie z wykrywaniem deadlocków!).
         * - `jcmd <PID> Thread.print` – nowsza alternatywa dla jstack.
         * W SAMYM kodzie Javy prostym odpowiednikiem jest
         * Thread.getAllStackTraces() – zwraca Map<Thread, StackTraceElement[]>
         * dla wszystkich aktywnych wątków w bieżącej JVM (patrz przykład niżej).
         */

        /*
         * ============================================================
         * 🔍 NARZĘDZIA GRAFICZNE (do dalszej samodzielnej eksploracji)
         * ============================================================
         * - VisualVM – darmowe narzędzie (dołączane niegdyś do JDK, dziś
         *   osobny projekt) z zakładką "Threads", pokazującą oś czasu
         *   stanów każdego wątku (RUNNABLE/BLOCKED/WAITING) na wykresie,
         *   plus przycisk do zrobienia pełnego thread dumpa jednym klikiem.
         * - Debugger w IntelliJ IDEA – zakładka "Threads & Frames" podczas
         *   sesji debugowania: lista wszystkich wątków, ich stan, oraz
         *   możliwość przełączania się między stosami wywołań każdego
         *   z nich, żeby zobaczyć dokładnie gdzie każdy wątek "stoi".
         * Oba narzędzia są nieocenione przy diagnozowaniu deadlocków,
         * "głodzenia" wątków (starvation) i wycieków wątków w większych
         * aplikacjach – warto je poznać praktycznie poza tą lekcją.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Nazywaj wątki (`new Thread(runnable, "nazwa")` albo
         *   `Thread.ofVirtual().name("nazwa")`) – bez tego domyślne nazwy
         *   typu "Thread-3" / "pool-1-thread-2" są bezużyteczne w logach.
         * - Loguj Thread.currentThread().getName() w kluczowych miejscach
         *   kodu wielowątkowego – to podstawowy, "ręczny" sposób śledzenia
         *   który wątek co robi.
         * - Thread dump = migawka stanu i stack trace WSZYSTKICH wątków JVM
         *   w danej chwili – narzędzie nr 1 przy diagnozowaniu zawieszeń.
         * - `jstack <PID>` / `jcmd <PID> Thread.print` – zewnętrzne narzędzia
         *   CLI z JDK do pobrania thread dumpa działającego procesu.
         * - Thread.getAllStackTraces() – prosty odpowiednik jstack z
         *   poziomu samego kodu Java (bez narzędzi zewnętrznych).
         * - VisualVM / debugger IntelliJ – narzędzia graficzne do
         *   wizualnej analizy wątków i stack trace'ów w czasie rzeczywistym.
         */
    }

    /**
     * Bez nazwy wątki dostają domyślne nazwy typu "Thread-0", "Thread-1" –
     * trudno powiązać logi z konkretnym zadaniem biznesowym. Nadanie
     * czytelnej nazwy od razu porządkuje logi/stack trace.
     */
    private static void demoNamedThreads() throws InterruptedException {
        Thread unnamed = new Thread(() -> System.out.println("Wątek bez nadanej nazwy: " + Thread.currentThread().getName()));
        Thread named = new Thread(() -> System.out.println("Wątek z nadaną nazwą:    " + Thread.currentThread().getName()),
                "PlatnoscWorker-1");

        unnamed.start();
        named.start();
        unnamed.join(1000);
        named.join(1000);
    }

    /**
     * W praktyce w każdej istotnej linijce logu wielowątkowego kodu warto
     * dopisywać nazwę bieżącego wątku – dzięki temu w splątanych logach
     * z wielu wątków od razu widać, KTO co zrobił.
     */
    private static void demoLoggingCurrentThreadName() throws InterruptedException {
        int workerCount = 3;
        CountDownLatch latch = new CountDownLatch(workerCount);
        ExecutorService pool = Executors.newFixedThreadPool(workerCount);

        for (int i = 1; i <= workerCount; i++) {
            int taskId = i;
            pool.submit(() -> {
                try {
                    log("rozpoczynam zadanie #" + taskId);
                    sleepQuiet(20L * taskId);
                    log("kończę zadanie #" + taskId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        shutdownAndWait(pool);
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }

    /**
     * Thread.getAllStackTraces() – proste, czysto-Javowe narzędzie
     * (bez zewnętrznych programów) zwracające stack trace WSZYSTKICH
     * aktywnych wątków bieżącej JVM. To co robi `jstack` z zewnątrz,
     * to wywołanie robi "od środka" programu.
     */
    private static void demoThreadDump() throws InterruptedException {
        // Odpalamy kilka wątków w tle, żeby dump pokazał coś więcej niż tylko main
        ExecutorService pool = Executors.newFixedThreadPool(2, r -> new Thread(r, "BackgroundWorker"));
        pool.submit(() -> sleepQuiet(300));
        pool.submit(() -> sleepQuiet(300));

        Thread.sleep(50); // dajemy workerom chwilę na start, żeby na pewno pojawiły się w dumpie

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        System.out.println("Liczba aktywnych wątków w JVM: " + allStackTraces.size());

        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackTrace = entry.getValue();
            System.out.println("- Wątek '" + thread.getName() + "' (stan: " + thread.getState() + ")"
                    + (stackTrace.length > 0 ? ", szczyt stosu: " + stackTrace[0] : ", brak stosu (bezczynny)"));
        }

        shutdownAndWait(pool);
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
