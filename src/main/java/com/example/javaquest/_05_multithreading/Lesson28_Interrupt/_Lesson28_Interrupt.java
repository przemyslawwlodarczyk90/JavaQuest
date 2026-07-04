package com.example.javaquest._05_multithreading.Lesson28_Interrupt;

public class _Lesson28_Interrupt {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 INTERRUPT – JAK GRZECZNIE POPROSIĆ WĄTEK O ZAKOŃCZENIE?
         * ============================================================
         * Javowe wątki NIE mają bezpiecznego sposobu na "zabicie" ich
         * z zewnątrz (stara metoda Thread.stop() jest DEPRECATED i
         * niebezpieczna – może zostawić obiekty w niespójnym stanie).
         *
         * Zamiast tego Java oferuje mechanizm INTERRUPT – to sygnał
         * "proszę, zakończ się kiedy możesz", a nie wymuszenie. Wątek
         * sam decyduje, jak zareaguje na ten sygnał – zwykle sprawdzając
         * swoją flagę przerwania w pętli roboczej.
         *
         * Trzy kluczowe elementy:
         * - `thread.interrupt()`       – ustawia flagę przerwania na wątku
         * - `thread.isInterrupted()`   – sprawdza flagę, NIE CZYŚCI jej
         * - `Thread.interrupted()`     – metoda STATYCZNA, sprawdza flagę
         *                                BIEŻĄCEGO wątku i JĄ CZYŚCI (!)
         */

        System.out.println("=== 1) isInterrupted() vs Thread.interrupted() ===");
        demonstrateFlagDifference();

        System.out.println();
        System.out.println("=== 2) InterruptedException – rzucany przez sleep()/wait()/join() ===");

        /*
         * Gdy wątek jest "uśpiony" wewnątrz sleep(), wait() lub join(),
         * a ktoś wywoła na nim interrupt(), metody te NATYCHMIAST przerywają
         * oczekiwanie i rzucają checked exception InterruptedException.
         * WAŻNE: w tym momencie flaga przerwania jest JUŻ WYCZYSZCZONA przez
         * JVM (sam fakt rzucenia wyjątku "konsumuje" flagę) – dlatego dobra
         * praktyka to ręcznie ustawić ją z powrotem w catch:
         *   catch (InterruptedException e) {
         *       Thread.currentThread().interrupt(); // przywróć flagę
         *   }
         * Dzięki temu kod wyżej w stosie wywołań (np. pętla robocza) nadal
         * "widzi", że przerwanie zostało zgłoszone.
         */

        System.out.println();
        System.out.println("=== 3) Poprawny wzorzec konczenia watku przez interrupt ===");
        demonstrateGracefulShutdown();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `thread.interrupt()` – ustawia flagę przerwania na WSKAZANYM
         *   wątku (nie przerywa go siłą, to tylko "prośba").
         * - `thread.isInterrupted()` – sprawdza flagę DOWOLNEGO wątku,
         *   NIE CZYŚCI jej (można sprawdzać wielokrotnie).
         * - `Thread.interrupted()` – metoda statyczna, działa na
         *   BIEŻĄCYM wątku i CZYŚCI flagę po odczycie – łatwo o pomyłkę!
         * - `sleep()`, `wait()`, `join()` rzucają `InterruptedException`,
         *   gdy wątek jest przerwany w trakcie oczekiwania – i przy tym
         *   CZYSZCZĄ flagę przerwania.
         * - Poprawny wzorzec pętli roboczej:
         *     while (!Thread.currentThread().isInterrupted()) { ... }
         *   a w bloku catch (InterruptedException e) zawsze przywracamy
         *   flagę: Thread.currentThread().interrupt();
         * - Nigdy nie "połykaj" cicho InterruptedException – to zaciera
         *   sygnał i utrudnia poprawne kończenie wątków w większych
         *   aplikacjach.
         */
    }

    private static void demonstrateFlagDifference() throws InterruptedException {
        Thread worker = new Thread(() -> {
            // Wątek po prostu czeka - nie robi nic konkretnego, tylko
            // pozwala sobie na przerwanie z zewnątrz.
            while (!Thread.currentThread().isInterrupted()) {
                // pętla robocza - w realnym kodzie byłaby tu praca
            }
            System.out.println("Worker: zauwazylem isInterrupted() == true, koncze petle.");
        }, "Worker-flagi");

        worker.start();
        Thread.sleep(100); // dajemy workerowi chwilę na start
        worker.interrupt(); // ustawiamy flagę przerwania
        worker.join(2000);

        // isInterrupted() na już zakończonym wątku - flaga nie jest czyszczona
        // przez samo jej odczytanie (w odróżnieniu od Thread.interrupted()).
        System.out.println("worker.isInterrupted() po zakonczeniu watku: " + worker.isInterrupted()
                + " (isInterrupted() NIE czysci flagi)");

        // Teraz pokazujemy różnicę z Thread.interrupted() na WŁASNYM wątku (main).
        Thread.currentThread().interrupt(); // ustawiamy flagę na main
        System.out.println("Przed odczytem: flaga przerwania na main ustawiona.");
        boolean firstRead = Thread.interrupted(); // sprawdza I CZYŚCI flagę main
        boolean secondRead = Thread.interrupted(); // flaga już wyczyszczona -> false
        System.out.println("Thread.interrupted() pierwsze wywolanie: " + firstRead + " (odczytalo true i WYCZYSCILO flage)");
        System.out.println("Thread.interrupted() drugie wywolanie:  " + secondRead + " (flaga byla juz wyczyszczona)");
    }

    private static void demonstrateGracefulShutdown() throws InterruptedException {
        Thread worker = new Thread(() -> {
            int iteration = 0;
            try {
                // Poprawny wzorzec: sprawdzamy flagę przerwania w warunku pętli.
                while (!Thread.currentThread().isInterrupted()) {
                    iteration++;
                    System.out.println("Worker: wykonuje iteracje robocza #" + iteration);
                    Thread.sleep(150); // tu może zostać rzucony InterruptedException
                }
            } catch (InterruptedException e) {
                // sleep() WYCZYŚCIŁ flagę przerwania - musimy ją ręcznie przywrócić,
                // żeby ewentualny kod nadrzędny nadal widział, że przyszło przerwanie.
                Thread.currentThread().interrupt();
                System.out.println("Worker: zlapalem InterruptedException podczas sleep(), koncze czysto.");
            } finally {
                System.out.println("Worker: sprzatanie zasobow i zakonczenie watku.");
            }
        }, "Worker-graceful");

        worker.start();
        Thread.sleep(500); // dajemy workerowi wykonać kilka iteracji
        System.out.println("Main: wysylam interrupt() do workera...");
        worker.interrupt();
        worker.join(2000);

        System.out.println("Main: worker.isAlive() = " + worker.isAlive() + " (wątek zakonczyl sie czysto)");
    }
}
