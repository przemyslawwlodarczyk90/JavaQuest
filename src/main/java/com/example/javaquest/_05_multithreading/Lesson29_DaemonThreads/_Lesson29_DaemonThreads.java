package com.example.javaquest._05_multithreading.Lesson29_DaemonThreads;

public class _Lesson29_DaemonThreads {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 WĄTKI DEMONICZNE (DAEMON THREADS) – CZYM SĄ?
         * ============================================================
         * Domyślnie każdy nowy wątek jest wątkiem "użytkownika" (user
         * thread) – JVM czeka, aż WSZYSTKIE wątki użytkownika zakończą
         * pracę, zanim zakończy cały program.
         *
         * Wątek DEMONICZNY (daemon thread) to wątek "drugoplanowy" –
         * JVM NIE czeka na jego zakończenie. Gdy tylko WSZYSTKIE
         * pozostałe wątki są demoniczne (nie ma już żadnego wątku
         * użytkownika), JVM natychmiast kończy program, "ucinając"
         * wątki demoniczne w dowolnym momencie ich działania – bez
         * finally, bez czyszczenia zasobów, bez ostrzeżenia.
         *
         * Ustawienie: `thread.setDaemon(true)` – MUSI być wywołane
         * PRZED `thread.start()`. Wywołanie na już wystartowanym
         * wątku rzuci `IllegalThreadStateException`.
         *
         * Typowe zastosowania wątków demonicznych:
         * - czyszczenie cache w tle (np. usuwanie przeterminowanych wpisów),
         * - monitoring / zbieranie metryk działających w tle,
         * - wątki pomocnicze bibliotek (np. GC-related, scheduler ticków),
         *   które nie powinny "trzymać" JVM przy życiu same z siebie.
         *
         * Wątków demonicznych NIE należy używać tam, gdzie ważne jest
         * dokończenie pracy (np. zapis do pliku/bazy) – mogą zostać
         * przerwane w dowolnym momencie, bez gwarancji domknięcia zasobów.
         */

        System.out.println("=== 1) setDaemon(true) PRZED start() – wymog ===");
        demonstrateSetDaemonMustBeBeforeStart();

        System.out.println();
        System.out.println("=== 2) Daemon thread nie blokuje zakonczenia JVM ===");
        demonstrateDaemonDoesNotBlockShutdown();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `thread.setDaemon(true)` musi być wywołane PRZED `start()`
         *   – inaczej `IllegalThreadStateException`.
         * - JVM kończy program, gdy zostały już TYLKO wątki demoniczne
         *   (wątki użytkownika = 0) – nie czeka na zakończenie daemonów.
         * - Wątki demoniczne są "ucinane" w dowolnym momencie – NIE ma
         *   gwarancji wykonania ich `finally`, zapisania buforów itp.
         * - Typowe zastosowania: czyszczenie cache, monitoring, zadania
         *   pomocnicze działające "tak długo, jak żyje aplikacja".
         * - NIE używaj daemon threads do pracy, która MUSI się dokończyć
         *   (np. zapis krytycznych danych) – użyj zamiast tego zwykłego
         *   wątku i jawnego mechanizmu zamykania (np. shutdown hook,
         *   ExecutorService.shutdown() + awaitTermination()).
         */
    }

    private static void demonstrateSetDaemonMustBeBeforeStart() {
        Thread thread = new Thread(() -> {
            // pusta praca - wątek tylko do demonstracji ustawiania flagi
        }, "Demo-Daemon-Setup");

        thread.setDaemon(true); // OK - wątek jeszcze nie wystartował
        System.out.println("setDaemon(true) przed start() - OK, thread.isDaemon() = " + thread.isDaemon());

        thread.start();

        try {
            thread.setDaemon(false); // BŁĄD - wątek już wystartował
            System.out.println("To się nie powinno wypisać.");
        } catch (IllegalThreadStateException e) {
            System.out.println("Proba setDaemon() PO start() rzucila: "
                    + e.getClass().getSimpleName() + " (zgodnie z oczekiwaniami)");
        }

        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void demonstrateDaemonDoesNotBlockShutdown() throws InterruptedException {
        // Daemon thread z "nieskonczona" praca w tle (np. monitoring co 100ms).
        // Mimo petli while(true), program i tak zakonczy sie normalnie,
        // bo to jedyny pozostajacy watek (poza main) i jest DEMONICZNY.
        Thread backgroundMonitor = new Thread(() -> {
            int tick = 0;
            while (true) {
                tick++;
                System.out.println("[Daemon-Monitor] tick #" + tick + " - 'nieskonczona' praca w tle");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Daemon-Monitor");

        backgroundMonitor.setDaemon(true); // KLUCZOWE: przed start()
        backgroundMonitor.start();

        // User thread (main) konczy sie szybko - po zaledwie 2 "tickach"
        // monitor'a. Poniewaz backgroundMonitor jest DAEMONEM, JVM nie
        // bedzie na niego czekac - caly program zakonczy sie razem z main(),
        // mimo ze monitor mial zaplanowana "wieczna" petle.
        Thread.sleep(450);
        System.out.println("Main: konczy swoja prace. Watek uzytkownika (main) sie konczy,");
        System.out.println("a jedyny pozostaly watek to daemon -> JVM zakonczy caly program,");
        System.out.println("mimo ze Daemon-Monitor mial petle while(true) 'w nieskonczonosc'.");
        // Celowo NIE robimy join() na backgroundMonitor - to daemon, ma
        // zniknac razem z koncem programu, nie czekamy na niego.
    }
}
