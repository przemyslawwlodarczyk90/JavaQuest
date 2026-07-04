package com.example.javaquest._05_multithreading.Lesson21_ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class _Lesson21_ExecutorService {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROBLEM: RĘCZNE TWORZENIE WĄTKÓW NIE SKALUJE SIĘ
         * ============================================================
         * Do tej pory tworzyliśmy wątki ręcznie: `new Thread(...).start()`.
         * To działa dla kilku wątków, ale w praktycznej aplikacji, która
         * obsługuje setki/tysiące krótkich zadań (np. żądania HTTP,
         * zapytania do bazy), tworzenie NOWEGO wątku za każdym razem jest:
         *
         * - KOSZTOWNE – tworzenie wątku systemowego to narzut (pamięć na
         *   stos, rejestracja w OS), a tworzenie i niszczenie go po
         *   jednym krótkim zadaniu to marnotrawstwo.
         * - NIEKONTROLOWANE – nic nie ogranicza liczby jednocześnie
         *   działających wątków. 10 000 żądań = 10 000 wątków = OutOfMemoryError
         *   albo tak silne przełączanie kontekstu (context switching), że
         *   system zwalnia zamiast przyspieszać.
         * - TRUDNE W ZARZĄDZANIU – kto pilnuje, że wątek się zakończył?
         *   Jak zebrać wynik? Jak obsłużyć wyjątek?
         *
         * ✅ ROZWIĄZANIE: ExecutorService – PULA WĄTKÓW (thread pool)
         * Zamiast tworzyć wątek na każde zadanie, tworzymy ograniczoną
         * pulę wątków WIELOKROTNEGO UŻYTKU. Zadania trafiają do kolejki,
         * a wolne wątki z puli je odbierają i wykonują. Wątek po
         * zakończeniu zadania NIE ginie – wraca do puli i czeka na kolejne.
         *
         * Korzyści:
         * - reużywanie wątków → brak kosztu ciągłego tworzenia/niszczenia
         * - kontrolowana liczba wątków → przewidywalne zużycie zasobów
         * - łatwe API do zbierania wyników (Future) i zarządzania cyklem życia
         */

        System.out.println("=== 1) Rodzaje puli wątków ===");
        explainPoolTypes();

        System.out.println("\n=== 2) execute() vs submit() ===");
        demoExecuteVsSubmit();

        System.out.println("\n=== 3) shutdown() vs shutdownNow() ===");
        demoShutdownVsShutdownNow();

        System.out.println("\n=== 4) Wzorcowe zamykanie puli (awaitTermination) ===");
        demoGracefulShutdownPattern();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ExecutorService = pula wątków wielokrotnego użytku, zamiast
         *   `new Thread()` za każdym razem – taniej, bezpieczniej,
         *   z kontrolą nad liczbą równoległych zadań.
         * - Executors.newFixedThreadPool(n)   → stała liczba wątków,
         *   nadmiar zadań czeka w kolejce. Dobre dla przewidywalnego
         *   obciążenia i ochrony przed przeciążeniem.
         * - Executors.newCachedThreadPool()   → liczba wątków rośnie
         *   wg potrzeb, nieużywane są zwalniane po 60s. Dobre dla wielu
         *   KRÓTKICH zadań o zmiennym natężeniu, ale RYZYKOWNE przy
         *   nagłym zalewie zadań (brak górnego limitu wątków!).
         * - Executors.newSingleThreadExecutor() → dokładnie 1 wątek,
         *   zadania wykonują się POJEDYNCZO, po kolei (FIFO). Dobre gdy
         *   zależy nam na kolejności i braku równoległości.
         * - submit(task)  → zwraca Future<T>, można pobrać wynik/wyjątek.
         * - execute(task) → nic nie zwraca (void), "wystrzel i zapomnij".
         * - shutdown()    → NIE przyjmuje nowych zadań, ale kończy te
         *   już zakolejkowane/wykonywane.
         * - shutdownNow()  → próbuje PRZERWAĆ (interrupt) trwające zadania
         *   i zwraca listę tych, które nie zdążyły się zacząć.
         * - Zawsze: shutdown() + awaitTermination(timeout), a jeśli pula
         *   nie zdąży się zamknąć w czasie – dopiero wtedy shutdownNow().
         *   Inaczej JVM może "wisieć" w nieskończoność na wątkach non-daemon.
         */
    }

    /**
     * Trzy podstawowe fabryki z Executors – różnice w praktyce.
     */
    private static void explainPoolTypes() throws InterruptedException {
        /*
         * newFixedThreadPool(n)     → n wątków na stałe, kolejka na resztę zadań
         * newCachedThreadPool()     → wątki tworzone na żądanie, bez górnego limitu,
         *                             nieużywane wątki giną po 60s bezczynności
         * newSingleThreadExecutor() → 1 wątek, zadania wykonują się sekwencyjnie
         */

        System.out.println("-- newFixedThreadPool(2): 4 zadania, tylko 2 na raz --");
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 4; i++) {
            int taskId = i;
            fixedPool.execute(() -> {
                System.out.println("[Fixed] zadanie " + taskId + " wykonuje " + Thread.currentThread().getName());
                sleepQuietly(80);
            });
        }
        shutdownAndWait(fixedPool);

        System.out.println("-- newSingleThreadExecutor(): 3 zadania, zawsze ten sam 1 wątek, po kolei --");
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            singlePool.execute(() -> System.out.println("[Single] zadanie " + taskId + " na " + Thread.currentThread().getName()));
        }
        shutdownAndWait(singlePool);

        System.out.println("-- newCachedThreadPool(): wątki tworzone na żądanie --");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            cachedPool.execute(() -> System.out.println("[Cached] zadanie " + taskId + " na " + Thread.currentThread().getName()));
        }
        shutdownAndWait(cachedPool);
    }

    /**
     * execute() – zwraca void, nie dowiemy się nic o wyniku ani błędzie
     * bez własnej obsługi. submit() – zwraca Future<T>, przez które
     * możemy pobrać wynik (get()) albo poczekać na zakończenie.
     */
    private static void demoExecuteVsSubmit() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // execute() – "wystrzel i zapomnij", brak Future
        pool.execute(() -> System.out.println("[execute] zadanie wykonane, ale nic nie zwracamy"));

        // submit() – zwraca Future, tu Future<?> bo Runnable zwraca "nic" (null po zakończeniu)
        Future<?> future = pool.submit(() -> System.out.println("[submit] zadanie wykonane, mamy Future"));

        try {
            future.get(); // czekamy aż zadanie się skończy (Runnable -> wynik null)
            System.out.println("[submit] future.get() zakończone, isDone=" + future.isDone());
        } catch (Exception e) {
            System.out.println("Błąd podczas get(): " + e);
        }

        shutdownAndWait(pool);
    }

    /**
     * shutdown() – grzecznie kończy pulę: nowe zadania odrzucone, ale
     * te w trakcie/w kolejce dokończą się normalnie.
     * shutdownNow() – agresywnie: próbuje przerwać (interrupt) zadania
     * w trakcie wykonywania i zwraca listę tych, które nawet nie zdążyły
     * wystartować.
     */
    private static void demoShutdownVsShutdownNow() throws InterruptedException {
        System.out.println("-- shutdown(): zadania w trakcie/w kolejce się kończą --");
        ExecutorService pool1 = Executors.newFixedThreadPool(1);
        pool1.submit(() -> {
            System.out.println("[shutdown-demo] zadanie 1 startuje (krótkie)");
            sleepQuietly(50);
            System.out.println("[shutdown-demo] zadanie 1 kończy się normalnie");
        });
        pool1.submit(() -> System.out.println("[shutdown-demo] zadanie 2 z kolejki też się wykona"));
        pool1.shutdown(); // nie przyjmuje NOWYCH zadań, ale te zakolejkowane dokończy
        boolean finished1 = pool1.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Pula zakończona po shutdown()? " + finished1);

        System.out.println("-- shutdownNow(): próba przerwania długiego zadania --");
        ExecutorService pool2 = Executors.newFixedThreadPool(1);
        pool2.submit(() -> {
            try {
                System.out.println("[shutdownNow-demo] zadanie startuje długi sleep(1000ms)");
                Thread.sleep(1000);
                System.out.println("[shutdownNow-demo] to się NIE powinno wypisać");
            } catch (InterruptedException e) {
                System.out.println("[shutdownNow-demo] zadanie przerwane (InterruptedException) – kończy się wcześniej");
                Thread.currentThread().interrupt();
            }
        });
        sleepQuietly(50); // dajemy zadaniu chwilę na wystartowanie
        var notStarted = pool2.shutdownNow(); // próbuje przerwać trwające zadanie
        boolean finished2 = pool2.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Pula zakończona po shutdownNow()? " + finished2
                + ", niewystartowane zadania: " + notStarted.size());
    }

    /**
     * Wzorcowy, bezpieczny sposób zamykania ExecutorService – ZAWSZE tak
     * kończ pulę wątków, inaczej wątki non-daemon mogą trzymać JVM
     * "przy życiu" w nieskończoność.
     */
    private static void demoGracefulShutdownPattern() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            pool.submit(() -> {
                System.out.println("[Graceful] zadanie " + taskId + " wykonuje się");
                sleepQuietly(60);
            });
        }
        shutdownAndWait(pool);
        System.out.println("Pula zamknięta poprawnie, wszystkie zadania zakończone.");
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Wzorcowe, bezpieczne zamykanie ExecutorService:
     * 1. shutdown() – przestań przyjmować nowe zadania
     * 2. awaitTermination(timeout) – poczekaj na dokończenie z limitem czasu
     * 3. jeśli nie zdążyła – dopiero wtedy shutdownNow() jako "plan B"
     */
    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
