package com.example.javaquest._05_multithreading.Lesson35_SafeThreadTermination;

public class _Lesson35_SafeThreadTermination {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROBLEM: JAK BEZPIECZNIE ZATRZYMAĆ WĄTEK?
         * ============================================================
         * Wątek w Javie NIE ma metody "stop mnie teraz i natychmiast",
         * której dałoby się bezpiecznie użyć. Kiedyś taka metoda istniała
         * – Thread.stop() – ale okazała się tak niebezpieczna, że została
         * @Deprecated (patrz sekcja niżej).
         *
         * Właściwe podejście: wątek sam decyduje, KIEDY skończyć pracę.
         * My (z zewnątrz) możemy mu tylko "poprosić" – ustawiając flagę
         * albo wysyłając sygnał przerwania (interrupt()). Wątek MUSI
         * sam regularnie sprawdzać, czy powinien się zakończyć, i zrobić
         * to w bezpiecznym momencie (np. między iteracjami pętli, po
         * zwolnieniu zasobów).
         *
         * Dwa uzupełniające się mechanizmy:
         * 1) flaga `volatile boolean running` – dla pętli, która aktywnie
         *    pracuje i regularnie wraca do warunku pętli,
         * 2) `interrupt()` – dla wątku, który może być ZABLOKOWANY
         *    (w sleep(), wait(), join(), operacji blokującego I/O) –
         *    sama flaga by tam nie pomogła, bo wątek nie wróci do
         *    sprawdzenia warunku pętli, dopóki się nie obudzi.
         */

        System.out.println("=== 1) Podstawowy wzorzec: flaga volatile boolean running ===");
        demoFlagPattern();

        System.out.println("\n=== 2) Kompletny, poprawny wzorzec: flaga + interrupt() ===");
        demoFlagPlusInterruptPattern();

        /*
         * ============================================================
         * ⚠️ DLACZEGO Thread.stop() / suspend() / resume() SĄ NIEBEZPIECZNE
         * ============================================================
         * Te metody istnieją w API Thread, ale są oznaczone @Deprecated
         * i NIE WOLNO ich używać w nowym kodzie:
         *
         * - Thread.stop()
         *   Zatrzymuje wątek NATYCHMIAST, w DOWOLNYM miejscu jego
         *   wykonania – nawet w środku modyfikacji obiektu współdzielonego.
         *   Zwalnia wszystkie monitory (locki), które wątek trzymał,
         *   ale robi to w LOSOWYM momencie – obiekty chronione przez te
         *   locki mogą zostać w NIESPÓJNYM stanie (np. lista z połową
         *   przeniesionych elementów). Inne wątki zobaczą ten "połamany"
         *   stan jako poprawny, bo lock został zwolniony normalnie.
         *   Efekt: subtelne, trudne do odtworzenia błędy danych.
         *
         * - Thread.suspend()
         *   Wstrzymuje wątek "w miejscu", ale NIE zwalnia locków, które
         *   trzyma. Jeśli zawieszony wątek trzymał lock potrzebny innemu
         *   wątkowi (np. temu, który miałby wywołać resume()) – mamy
         *   gotowy przepis na DEADLOCK, praktycznie identyczny do tego
         *   z Lesson25_Deadlock, ale znacznie trudniejszy do przewidzenia.
         *
         * - Thread.resume()
         *   Wznawia zawieszony wątek – ale jeśli wywołane ZANIM
         *   suspend() zdążyło zadziałać (typowy wyścig – patrz
         *   Lesson07_RaceCondition), wątek może zostać zawieszony
         *   NA ZAWSZE, bo "spóźniony" resume() nic już nie robi.
         *
         * ✅ ZAMIAST TEGO: kooperacyjne kończenie wątku – flaga
         * (Lesson14_Volatile) i/lub interrupt() (Lesson28_Interrupt).
         * Wątek sam sprząta po sobie i kończy się w bezpiecznym miejscu.
         */

        System.out.println("\n=== 3) Dlaczego Thread.stop()/suspend()/resume() są zakazane ===");
        System.out.println("(patrz komentarz w kodzie powyżej – metody @Deprecated, mogą uszkodzić");
        System.out.println(" stan współdzielony (stop()) albo doprowadzić do trwałego zakleszczenia");
        System.out.println(" (suspend()/resume()). Nigdy ich nie wywołujemy w tej lekcji.");

        System.out.println("\n=== KONIEC LEKCJI 35 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wątku NIE zatrzymuje się "z zewnątrz" na siłę – on sam
         *   musi sprawdzić sygnał i grzecznie zakończyć run().
         * - Wzorzec 1: `volatile boolean running` – pętla robocza
         *   sprawdza warunek `while (running) { ... }`, a metoda
         *   shutdown() ustawia `running = false`.
         * - Wzorzec 2: gdy wątek może być zablokowany (sleep/wait/
         *   blocking I/O), samej flagi nie wystarczy – trzeba dodatkowo
         *   wywołać `thread.interrupt()`, żeby natychmiast go obudzić
         *   (InterruptedException) i pozwolić mu sprawdzić flagę / wyjść.
         * - Kompletny, bezpieczny "graceful shutdown": ustaw flagę na
         *   false, wywołaj interrupt(), poczekaj join() z timeoutem.
         * - Thread.stop()/suspend()/resume() są @Deprecated i NIEBEZPIECZNE
         *   – stop() może zostawić dane w niespójnym stanie, suspend()
         *   może zablokować lock na zawsze → deadlock. NIGDY ich nie używaj.
         */
    }

    /**
     * Wzorzec 1: prosta pętla robocza, która NIE blokuje się na niczym
     * (żadnego sleep/wait) – wystarczy jej sama flaga `running`, bo
     * regularnie wraca do sprawdzenia warunku pętli.
     */
    private static void demoFlagPattern() throws InterruptedException {
        BusyWorker worker = new BusyWorker();
        Thread thread = new Thread(worker, "BusyWorker");

        thread.start();
        Thread.sleep(150); // dajemy workerowi chwilę na wykonanie kilku iteracji

        System.out.println("main: wywołuję graceful shutdown (flaga running = false)");
        worker.shutdown();

        thread.join(2000); // czekamy na zakończenie, ale zawsze z timeoutem
        System.out.println("main: wątek zakończony? " + !thread.isAlive()
                + ", wykonano iteracji: " + worker.getIterations());
    }

    /**
     * Worker, który "aktywnie pracuje" – nie blokuje się na niczym,
     * więc sama flaga `volatile boolean running` w pełni wystarcza,
     * by go bezpiecznie zatrzymać.
     */
    private static class BusyWorker implements Runnable {
        private volatile boolean running = true;
        private volatile int iterations = 0;

        @Override
        public void run() {
            while (running) {
                iterations++;
                // symulacja krótkiej pracy (bez sleep - to celowo "busy" worker)
                if (iterations % 1_000_000 == 0) {
                    Thread.onSpinWait(); // drobna uprzejmość dla schedulera
                }
            }
            System.out.println("[BusyWorker] zauważyłem running=false, kończę run()");
        }

        void shutdown() {
            running = false;
        }

        int getIterations() {
            return iterations;
        }
    }

    /**
     * Wzorzec 2 (KOMPLETNY, POPRAWNY): worker, który może być
     * ZABLOKOWANY w Thread.sleep() – sama flaga by nie pomogła, bo
     * wątek nie wróci do sprawdzenia warunku pętli, dopóki się nie
     * obudzi. Dlatego graceful shutdown ustawia flagę I dodatkowo
     * wywołuje interrupt(), żeby natychmiast przerwać sleep().
     */
    private static void demoFlagPlusInterruptPattern() throws InterruptedException {
        SleepyWorker worker = new SleepyWorker();
        Thread thread = new Thread(worker, "SleepyWorker");

        thread.start();
        Thread.sleep(200); // worker zdążył wejść w długi sleep(5000)

        System.out.println("main: wywołuję graceful shutdown (flaga + interrupt)");
        gracefulShutdown(worker, thread);

        System.out.println("main: wątek zakończony? " + !thread.isAlive());
    }

    /**
     * Wzorcowa metoda "graceful shutdown" wywoływana z main(): najpierw
     * ustawia flagę running=false (żeby worker po przebudzeniu wiedział,
     * że ma zakończyć), potem interrupt() (żeby natychmiast obudzić go
     * z Thread.sleep()), na końcu join() z limitem czasu.
     */
    private static void gracefulShutdown(SleepyWorker worker, Thread thread) throws InterruptedException {
        worker.shutdown();     // 1) flaga - worker sprawdzi ją po przebudzeniu
        thread.interrupt();    // 2) interrupt - budzi wątek NATYCHMIAST z sleep()
        thread.join(2000);     // 3) czekamy na zakończenie, zawsze z timeoutem
    }

    private static class SleepyWorker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                try {
                    System.out.println("[SleepyWorker] usypiam na 5 sekund...");
                    Thread.sleep(5000); // długi sen - bez interrupt() czekalibyśmy 5s
                    System.out.println("[SleepyWorker] obudziłem się normalnie, sprawdzam flagę");
                } catch (InterruptedException e) {
                    // KLUCZOWE: reagujemy na interrupt, nie ignorujemy go
                    System.out.println("[SleepyWorker] sleep() przerwany przez interrupt() - kończę pracę");
                    Thread.currentThread().interrupt(); // przywracamy status przerwania
                    running = false; // dla czytelności - i tak zaraz wyjdziemy z pętli
                }
            }
            System.out.println("[SleepyWorker] run() zakończone");
        }

        void shutdown() {
            running = false;
        }
    }
}
