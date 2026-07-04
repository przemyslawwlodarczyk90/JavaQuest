package com.example.javaquest._05_multithreading.Lesson37_CommonMistakes;

import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson37_CommonMistakes {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 TYPOWE BŁĘDY W KODZIE WIELOWĄTKOWYM
         * ============================================================
         * Ostatnia lekcja tego rozdziału: konkretne, SKOMPILOWANE
         * przykłady najczęstszych błędów - zawsze ŹLE obok DOBRZE,
         * z odwołaniem do lekcji, gdzie dany temat omówiliśmy dokładniej.
         *
         * ⚠️ Jeden wyjątek: błąd "brak shutdown() na ExecutorService"
         * (sekcja 6) jest opisany TYLKO słownie w komentarzu - faktyczne
         * uruchomienie takiego kodu zawiesiłoby ten program NA ZAWSZE,
         * więc tu celowo tego NIE wykonujemy.
         */

        System.out.println("=== 1) run() zamiast start() (patrz Lesson02_ThreadClass) ===");
        demoRunVsStart();

        System.out.println("\n=== 2) Brak join() gdy jest potrzebny ===");
        demoMissingJoin();

        System.out.println("\n=== 3) count++ jako race condition (patrz Lesson07/Lesson09) ===");
        demoRaceCondition();

        System.out.println("\n=== 4) wait() poza synchronized (patrz Lesson15_WaitNotifyNotifyAll) ===");
        demoWaitOutsideSynchronized();

        System.out.println("\n=== 5) notify() zamiast notifyAll() gdy czeka więcej niż 1 wątek ===");
        demoNotifyVsNotifyAll();

        System.out.println("\n=== 6) Brak shutdown() na ExecutorService (TYLKO OPIS, kod NIE jest uruchamiany) ===");
        demoMissingShutdownExplanationOnly();

        System.out.println("\n=== KONIEC LEKCJI 37 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * 1. run() zamiast start() -> kod wykonuje się w BIEŻĄCYM wątku,
         *    żaden nowy wątek nie powstaje (Lesson02_ThreadClass).
         * 2. Brak join() -> main/inny wątek może odczytać wynik pracy
         *    innego wątku, ZANIM ten skończy - odczyta stary/pusty stan.
         * 3. count++ na zwykłym int to operacja read-modify-write,
         *    NIEATOMOWA -> przy wielu wątkach część inkrementacji
         *    "ginie" (Lesson07_RaceCondition, Lesson09_Atomicity).
         *    Rozwiązanie: AtomicInteger albo synchronized.
         * 4. wait() wywołany BEZ synchronized(monitor) rzuca
         *    IllegalMonitorStateException natychmiast - wait()/notify()/
         *    notifyAll() ZAWSZE wymagają posiadania monitora obiektu,
         *    na którym są wołane (Lesson15_WaitNotifyNotifyAll).
         * 5. notify() budzi TYLKO JEDEN losowo wybrany czekający wątek.
         *    Jeśli czeka ich więcej, pozostałe mogą zostać zablokowane
         *    NA ZAWSZE (jeśli nikt więcej nie zawoła notify/notifyAll).
         *    Gdy więcej niż jeden wątek może czekać na tym samym
         *    monitorze - używaj notifyAll().
         * 6. Brak shutdown() na ExecutorService -> wątki puli NIE są
         *    demonami, JVM nigdy się nie zakończy samoistnie. ZAWSZE:
         *    shutdown() + awaitTermination(timeout) (Lesson21, Lesson36).
         */
    }

    /**
     * Błąd #1: wywołanie run() zamiast start(). run() to zwykłe
     * wywołanie metody - wykonuje się SYNCHRONICZNIE w wątku, który je
     * wywołał, i NIE tworzy żadnego nowego wątku. Szczegóły: Lesson02_ThreadClass.
     */
    private static void demoRunVsStart() throws InterruptedException {
        System.out.println("-- run() wywołane bezpośrednio - BRAK nowego wątku --");
        Thread badThread = new Thread(() ->
                System.out.println("Wewnątrz run(): " + Thread.currentThread().getName()), "Watek-Zly");
        System.out.println("Wątek main przed wywołaniem: " + Thread.currentThread().getName());
        badThread.run(); // ❌ ŹLE: to zwykłe wywołanie metody - wykona się w "main", nie w "Watek-Zly"!

        System.out.println("\n-- start() - POPRAWNIE tworzy nowy wątek --");
        Thread goodThread = new Thread(() ->
                System.out.println("Wewnątrz run(): " + Thread.currentThread().getName()), "Watek-Dobry");
        goodThread.start(); // ✅ DOBRZE: tworzy nowy wątek systemowy, który wywoła run()
        goodThread.join(1000);
    }

    /**
     * Błąd #2: brak join() tam, gdzie jest potrzebny - main odczytuje
     * wynik pracy innego wątku, zanim ten zdążył go ustawić.
     */
    private static void demoMissingJoin() throws InterruptedException {
        System.out.println("-- BEZ join(): main sprawdza wynik ZANIM wątek skończy pracę --");
        int[] resultBad = {0};
        Thread workerBad = new Thread(() -> {
            sleepQuietly(200);
            resultBad[0] = 42;
        }, "WorkerBad");
        workerBad.start();
        // ❌ ŹLE: brak join() - sprawdzamy wynik natychmiast, wątek jeszcze pracuje (śpi 200ms)
        System.out.println("Wynik zaraz po start() (bez join): " + resultBad[0]
                + "  <- to jeszcze 0, NIE 42! Wątek nie zdążył skończyć.");
        workerBad.join(1000); // sprzątamy po sobie przed końcem metody

        System.out.println("\n-- Z join(): main czeka na zakończenie wątku przed odczytem wyniku --");
        int[] resultGood = {0};
        Thread workerGood = new Thread(() -> {
            sleepQuietly(200);
            resultGood[0] = 42;
        }, "WorkerGood");
        workerGood.start();
        workerGood.join(1000); // ✅ DOBRZE: czekamy na zakończenie przed odczytem wyniku
        System.out.println("Wynik po join(): " + resultGood[0] + "  <- zawsze 42");
    }

    /**
     * Błąd #3: count++ na zwykłym int to operacja read-modify-write
     * (odczyt, +1, zapis) - NIE jest atomowa. Przy wielu wątkach
     * inkrementujących równolegle część "przyrostów" gubi się (dwa
     * wątki mogą odczytać tę samą starą wartość, oba dodać 1 i oba
     * zapisać to samo - jeden przyrost przepada). Szczegóły:
     * Lesson07_RaceCondition, Lesson09_Atomicity.
     */
    private static void demoRaceCondition() throws InterruptedException {
        final int incrementsPerThread = 100_000;
        final int threadCount = 4;
        final int expected = incrementsPerThread * threadCount;

        System.out.println("-- count++ NIE jest atomowe -> race condition --");
        int[] counter = {0};
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter[0]++; // ❌ ŹLE: odczyt + inkrementacja + zapis, nieatomowe
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join(5000);
        }
        System.out.println("Oczekiwany wynik: " + expected + ", rzeczywisty: " + counter[0]
                + (counter[0] != expected
                        ? "  <- BŁĄD, stracone inkrementacje wskutek wyścigu!"
                        : "  (tym razem się zgadza, ale to LOSOWE - nie polegaj na tym)"));

        System.out.println("\n-- POPRAWKA: AtomicInteger.incrementAndGet() - operacja atomowa --");
        AtomicInteger atomicCounter = new AtomicInteger(0);
        Thread[] atomicThreads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            atomicThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    atomicCounter.incrementAndGet(); // ✅ DOBRZE: atomowa operacja
                }
            });
        }
        for (Thread t : atomicThreads) {
            t.start();
        }
        for (Thread t : atomicThreads) {
            t.join(5000);
        }
        System.out.println("Oczekiwany wynik: " + expected + ", rzeczywisty: " + atomicCounter.get()
                + (atomicCounter.get() == expected ? "  <- ZAWSZE poprawnie" : "  <- to nie powinno się zdarzyć!"));
    }

    /**
     * Błąd #4: wait() wywołany BEZ posiadania monitora (czyli poza
     * blokiem synchronized na tym samym obiekcie) rzuca natychmiast
     * IllegalMonitorStateException. Szczegóły: Lesson15_WaitNotifyNotifyAll.
     */
    private static void demoWaitOutsideSynchronized() {
        System.out.println("-- wait() wywołany BEZ synchronized -> IllegalMonitorStateException --");
        Object lock = new Object();
        try {
            lock.wait(); // ❌ ŹLE: brak synchronized(lock) - nie jesteśmy właścicielem monitora
            System.out.println("(tej linii nigdy nie zobaczymy)");
        } catch (IllegalMonitorStateException e) {
            System.out.println("Złapano oczekiwany wyjątek: " + e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n-- POPRAWKA: wait() wewnątrz synchronized(lock) --");
        synchronized (lock) {
            try {
                lock.wait(100); // ✅ DOBRZE: jesteśmy właścicielem monitora; timeout, żeby nie czekać wiecznie
                System.out.println("wait(100) zakończony bez wyjątku (upłynął timeout, nikt nie wywołał notify)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Błąd #5: notify() budzi TYLKO JEDEN, dowolnie wybrany, czekający
     * wątek. Jeśli na tym samym monitorze czeka więcej niż jeden wątek,
     * pozostałe mogą zostać zablokowane NA ZAWSZE - notify() "zapomina"
     * o nich, a warunek, na który czekają, jest już spełniony, więc
     * nikt więcej nie zawoła kolejnego notify(). Poprawka: notifyAll().
     *
     * ⚠️ BEZPIECZEŃSTWO DEMA: oba wątki czekające są DEMONAMI, a main
     * czeka na nie tylko z limitem czasu (join z timeoutem) - dokładnie
     * jak w Lesson25_Deadlock. Dzięki temu nawet jeśli któryś wątek
     * faktycznie zostanie zablokowany na zawsze, JVM i tak zakończy
     * program normalnie.
     */
    private static void demoNotifyVsNotifyAll() throws InterruptedException {
        System.out.println("-- notify() budzi tylko JEDEN z dwóch czekających wątków --");
        final Object lockOne = new Object();
        boolean[] readyOne = {false};

        Thread waiterA = new Thread(() -> {
            synchronized (lockOne) {
                while (!readyOne[0]) {
                    try {
                        lockOne.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[WaiterA] obudzony i zakończony");
            }
        }, "WaiterA");

        Thread waiterB = new Thread(() -> {
            synchronized (lockOne) {
                while (!readyOne[0]) {
                    try {
                        lockOne.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[WaiterB] obudzony i zakończony");
            }
        }, "WaiterB");

        // KLUCZOWE dla bezpieczeństwa demo: wątki daemon, żeby JVM mógł
        // zakończyć program, nawet gdyby jeden z nich został zablokowany na zawsze.
        waiterA.setDaemon(true);
        waiterB.setDaemon(true);
        waiterA.start();
        waiterB.start();
        Thread.sleep(200); // dajemy obu wątkom czas na wejście w wait()

        synchronized (lockOne) {
            readyOne[0] = true;
            lockOne.notify(); // ❌ ŹLE (gdy czeka >1 wątek): budzi TYLKO JEDEN, nie wiadomo który
        }

        waiterA.join(1000);
        waiterB.join(1000);
        System.out.println("WaiterA zakończony: " + !waiterA.isAlive());
        System.out.println("WaiterB zakończony: " + !waiterB.isAlive());
        System.out.println("Wniosek: dokładnie jeden z wątków prawdopodobnie nadal czeka - notify()");
        System.out.println("obudził tylko jednego, a drugi nigdy już nie zostanie powiadomiony.");

        System.out.println("\n-- POPRAWKA: notifyAll() budzi WSZYSTKICH czekających --");
        final Object lockTwo = new Object();
        boolean[] readyTwo = {false};

        Thread waiterC = new Thread(() -> {
            synchronized (lockTwo) {
                while (!readyTwo[0]) {
                    try {
                        lockTwo.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[WaiterC] obudzony i zakończony");
            }
        }, "WaiterC");

        Thread waiterD = new Thread(() -> {
            synchronized (lockTwo) {
                while (!readyTwo[0]) {
                    try {
                        lockTwo.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[WaiterD] obudzony i zakończony");
            }
        }, "WaiterD");

        waiterC.start();
        waiterD.start();
        Thread.sleep(200);

        synchronized (lockTwo) {
            readyTwo[0] = true;
            lockTwo.notifyAll(); // ✅ DOBRZE: budzi WSZYSTKICH czekających na tym monitorze
        }

        waiterC.join(1000);
        waiterD.join(1000);
        System.out.println("WaiterC zakończony: " + !waiterC.isAlive());
        System.out.println("WaiterD zakończony: " + !waiterD.isAlive());
    }

    /**
     * Błąd #6: brak shutdown() na ExecutorService. UWAGA: ten przykład
     * jest CELOWO tylko opisany słownie w komentarzu poniżej - kod NIE
     * jest wywoływany, bo naprawdę zawiesiłby ten program na stałe
     * (proces Javy nigdy by się nie zakończył).
     */
    private static void demoMissingShutdownExplanationOnly() {
        System.out.println("-- brak shutdown() na ExecutorService (OPIS SŁOWNY, kod poniżej NIE jest uruchamiany!) --");
        /*
         * ❌ BŁĄD (kod poniżej jest tylko ilustracją w komentarzu -
         * NIE wykonujemy go, bo zawiesiłby ten program na stałe):
         *
         *     ExecutorService pool = Executors.newFixedThreadPool(4);
         *     pool.submit(() -> System.out.println("zadanie"));
         *     // <- KONIEC main() BEZ pool.shutdown()!
         *
         * EFEKT: wątki robocze w domyślnej puli ExecutorService NIE są
         * demonami (non-daemon). JVM kończy proces dopiero, gdy
         * zakończą się WSZYSTKIE wątki non-daemon. Wątki puli czekają
         * w nieskończoność na kolejne zadania w swojej wewnętrznej
         * kolejce i same z siebie nigdy nie umierają - więc proces Javy
         * "wisi" w nieskończoność, mimo że main() logicznie już się
         * skończyło. Jedyne wyjście: ręcznie zabić proces (Ctrl+C / kill).
         *
         * ✅ POPRAWKA: zawsze pool.shutdown() + awaitTermination(timeout)
         * (patrz Lesson21_ExecutorService i Lesson36_BestPractices, zasada 6).
         */
        System.out.println("(patrz komentarz w kodzie powyżej - bez shutdown() proces Javy nigdy by się nie zakończył)");
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
