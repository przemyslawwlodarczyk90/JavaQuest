package com.example.javaquest._05_multithreading.Lesson05_ThreadBasicMethods;

public class _Lesson05_ThreadBasicMethods {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PODSTAWOWE METODY KLASY Thread
         * ============================================================
         * Klasa `Thread` udostępnia zestaw metod do zarządzania wątkami
         * i introspekcji (sprawdzania ich stanu). W tej lekcji poznasz
         * najważniejsze z nich: getName()/setName(), Thread.currentThread(),
         * sleep(), join(), isAlive() oraz getPriority()/setPriority().
         */

        /*
         * ============================================================
         * 🔹 getName() / setName() I Thread.currentThread()
         * ============================================================
         * Każdy wątek ma NAZWĘ (String) – domyślnie "Thread-0", "Thread-1"...
         * (main wątek nazywa się "main"). Nazwę można odczytać (getName())
         * i ustawić (setName() albo przez konstruktor Thread(Runnable, String)).
         *
         * `Thread.currentThread()` to metoda STATYCZNA zwracająca referencję
         * do wątku, który AKTUALNIE wykonuje ten kod – bardzo przydatna
         * w logach/debugowaniu, żeby wiedzieć "kto" (który wątek) wypisuje
         * daną linijkę.
         */

        System.out.println("=== getName() / Thread.currentThread() ===");
        System.out.println("Watek main nazywa sie: " + Thread.currentThread().getName());

        Thread worker = new Thread(() -> {
            System.out.println("Wewnatrz watku: " + Thread.currentThread().getName());
        });
        worker.setName("Watek-Roboczy");
        System.out.println("Nazwa ustawiona przed startem: " + worker.getName());
        worker.start();
        worker.join(2_000); // bezpiecznik: czekamy max 2s, patrz sekcja join() niżej

        /*
         * ============================================================
         * 🔹 sleep() – wstrzymanie wątku na określony czas
         * ============================================================
         * `Thread.sleep(millis)` to metoda STATYCZNA, którą wywołuje wątek
         * SAM NA SOBIE – zawiesza wykonywanie AKTUALNEGO wątku na (co
         * najmniej) podaną liczbę milisekund. W tym czasie wątek nie zużywa
         * CPU (stan TIMED_WAITING – patrz Lesson06_ThreadLifecycleAndStates).
         *
         * sleep() rzuca CHECKED wyjątek InterruptedException – trzeba go
         * obsłużyć (albo zadeklarować throws, jak robimy tu w main()).
         *
         * ⚠️ sleep() NIE zwalnia żadnych blokad (monitorów synchronized),
         * które wątek aktualnie trzyma!
         */

        System.out.println("\n=== sleep() ===");
        long before = System.currentTimeMillis();
        Thread.sleep(200);
        long after = System.currentTimeMillis();
        System.out.println("main spal ok. " + (after - before) + " ms");

        /*
         * ============================================================
         * 🔹 join() – czekanie na zakończenie innego wątku
         * ============================================================
         * `t.join()` wywołany na wątku `t` z INNEGO wątku (np. z main)
         * blokuje WYWOŁUJĄCY wątek, dopóki `t` się nie zakończy
         * (stan TERMINATED). To podstawowy sposób "poczekania" na wynik
         * pracy wątku potomnego przed użyciem tego wyniku.
         *
         * Wariant z limitem czasu: `t.join(millis)` czeka NAJWYŻEJ tyle
         * milisekund – jeśli wątek się nie zakończy w tym czasie, join()
         * po prostu wraca (bez wyjątku!), a wątek może nadal działać.
         * Dlatego po join(millis) często sprawdza się isAlive(), żeby
         * wiedzieć, czy naprawdę się zakończył, czy tylko upłynął timeout.
         */

        System.out.println("\n=== join() – main CZEKA na wynik watku potomnego ===");

        long[] wynikObliczen = new long[1]; // "schowek" na wynik z watku potomnego

        Thread liczacy = new Thread(() -> {
            System.out.println("Watek liczacy: zaczynam obliczenia...");
            try {
                Thread.sleep(300); // symulacja dluzszej pracy
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long suma = 0;
            for (int i = 1; i <= 1_000_000; i++) {
                suma += i;
            }
            wynikObliczen[0] = suma;
            System.out.println("Watek liczacy: obliczenia zakonczone.");
        }, "Watek-Liczacy");

        liczacy.start();
        System.out.println("main: wywolalem start(), teraz czekam przez join()...");
        liczacy.join(); // BEZ timeoutu - ale bezpieczne, bo watek na pewno się kończy (brak pętli nieskończonej)
        System.out.println("main: watek potomny zakonczony, moge bezpiecznie odczytac wynik.");
        System.out.println("Wynik obliczen: " + wynikObliczen[0]);
        // Gdyby main NIE wywolal join(), powyzszy System.out.println mogłby
        // wypisać 0 (bo watek liczacy jeszcze by nie skonczyl pracy) - to
        // klasyczny błąd początkujących.

        System.out.println("\n=== join(timeout) – wariant z limitem czasu ===");
        Thread wolny = new Thread(() -> {
            try {
                Thread.sleep(1_000); // "wolne" zadanie - 1 sekunda
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Watek-Wolny");
        wolny.start();
        wolny.join(100); // czekamy tylko 100ms - na pewno za krotko
        System.out.println("Po join(100ms) watek wciaz zyje? " + wolny.isAlive());
        wolny.join(2_000); // teraz czekamy do skutku (z bezpiecznikiem 2s)
        System.out.println("Po join(2000ms) watek wciaz zyje? " + wolny.isAlive());

        /*
         * ============================================================
         * 🔹 isAlive() – czy wątek jeszcze działa?
         * ============================================================
         * `isAlive()` zwraca true od momentu wywołania start() aż do
         * faktycznego zakończenia run() (stan TERMINATED). Zwraca false
         * zarówno PRZED start(), jak i PO zakończeniu wątku.
         */

        System.out.println("\n=== isAlive() ===");
        Thread krotki = new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("Przed start(): isAlive() = " + krotki.isAlive()); // false
        krotki.start();
        System.out.println("Zaraz po start(): isAlive() = " + krotki.isAlive()); // zwykle true
        krotki.join(2_000);
        System.out.println("Po join(): isAlive() = " + krotki.isAlive()); // false

        /*
         * ============================================================
         * 🔹 getPriority() / setPriority()
         * ============================================================
         * Każdy wątek ma priorytet (int od Thread.MIN_PRIORITY=1 do
         * Thread.MAX_PRIORITY=10, domyślnie Thread.NORM_PRIORITY=5).
         * Priorytet ustawiamy przez setPriority(int).
         *
         * ⚠️ UWAGA – BARDZO WAŻNE:
         * Priorytet to TYLKO SUGESTIA dla schedulera systemu operacyjnego,
         * a NIE gwarancja kolejności czy czasu wykonania! Scheduler MOŻE
         * (ale nie musi) preferować wątki o wyższym priorytecie. Zachowanie
         * jest zależne od OS/JVM i NIE wolno na nim polegać do zapewnienia
         * poprawności programu (np. synchronizacji) – tylko do "podpowiedzi"
         * co do relatywnej ważności wątków.
         */

        System.out.println("\n=== getPriority() / setPriority() ===");
        Thread priorytetowy = new Thread(() -> { });
        System.out.println("Domyslny priorytet: " + priorytetowy.getPriority()); // 5 (NORM_PRIORITY)
        priorytetowy.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Priorytet po ustawieniu MAX: " + priorytetowy.getPriority()); // 10
        System.out.println("⚠️ To tylko SUGESTIA dla schedulera - NIE gwarancja kolejnosci!");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - getName()/setName() - nazwa watku (do logowania/debugowania)
         * - Thread.currentThread() - referencja do biezacego watku (statyczna)
         * - sleep(millis) - usypia BIEZACY watek na dany czas (rzuca
         *   InterruptedException, nie zwalnia trzymanych blokad)
         * - join() - czeka (bez limitu) na zakonczenie WSKAZANEGO watku
         * - join(millis) - czeka NAJWYZEJ tyle ms (bez wyjatku po timeout -
         *   trzeba samemu sprawdzic isAlive())
         * - isAlive() - true od start() do faktycznego zakonczenia run()
         * - getPriority()/setPriority() - priorytet 1-10 (domyslnie 5) -
         *   to tylko SUGESTIA dla schedulera, NIE gwarancja
         */
    }
}
