package com.example.javaquest._05_multithreading.Lesson02_ThreadClass;

public class _Lesson02_ThreadClass {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 TWORZENIE WĄTKU PRZEZ KLASĘ Thread (extends Thread)
         * ============================================================
         * Pierwszy (historycznie) sposób tworzenia wątku w Javie:
         * 1) Stwórz klasę, która DZIEDZICZY (extends) po java.lang.Thread
         * 2) NADPISZ (@Override) metodę run() – tam umieszczasz kod,
         *    który ma się wykonać w nowym wątku
         * 3) Stwórz obiekt tej klasy i wywołaj na nim start()
         *
         * class MyThread extends Thread {
         *     @Override
         *     public void run() {
         *         // kod wykonywany w nowym wątku
         *     }
         * }
         *
         * MyThread t = new MyThread();
         * t.start(); // <- uruchamia NOWY wątek, który wykona run()
         */

        System.out.println("=== TWORZENIE WĄTKU PRZEZ extends Thread ===");
        MyThread watek = new MyThread("Watek-A");
        watek.start();

        // Dajemy chwilę na zakończenie (patrz Lesson05 - join() to lepszy
        // sposób, tu na razie proste opóźnienie by output był czytelny)
        watek.join();

        /*
         * ============================================================
         * 🔍 KLUCZOWA RÓŻNICA: run() vs start()
         * ============================================================
         * To NAJCZĘSTSZY błąd początkujących: wywołanie run() zamiast
         * start().
         *
         * run():
         * - To zwykłe wywołanie metody – jak każdej innej metody
         * - Wykonuje się SYNCHRONICZNIE w BIEŻĄCYM wątku (tu: main)
         * - NIE tworzy żadnego nowego wątku!
         *
         * start():
         * - Tworzy NOWY wątek systemowy (OS thread)
         * - Ten nowy wątek wywołuje run() WEWNĄTRZ SIEBIE
         * - Wykonanie jest ASYNCHRONICZNE względem wątku, który wywołał
         *   start() – main leci dalej, nie czekając
         *
         * Zobaczmy to na konkretnym przykładzie – porównamy nazwy
         * wątków i kolejność wypisywania.
         */

        System.out.println("\n=== PRZYKŁAD: run() – BEZ nowego wątku ===");
        MyThread wywolanieRun = new MyThread("Watek-B (przez run())");
        System.out.println("Przed wywolaniem run(), biezacy watek: " + Thread.currentThread().getName());
        wywolanieRun.run(); // <- UWAGA: to NIE tworzy nowego wątku!
        System.out.println("Po wywolaniu run(), biezacy watek: " + Thread.currentThread().getName());
        // Cała powyższa sekcja wykonuje się w 100% w wątku "main".
        // Wewnątrz run() Thread.currentThread().getName() zwróci "main",
        // a NIE "Watek-B (przez run())"!

        System.out.println("\n=== PRZYKŁAD: start() – Z nowym wątkiem ===");
        MyThread wywolanieStart = new MyThread("Watek-C (przez start())");
        System.out.println("Przed wywolaniem start(), biezacy watek: " + Thread.currentThread().getName());
        wywolanieStart.start(); // <- to TWORZY nowy wątek systemowy
        wywolanieStart.join();  // main czeka na zakończenie (patrz Lesson05)
        System.out.println("Po wywolaniu start(), biezacy watek: " + Thread.currentThread().getName());
        // Wewnątrz run() (uruchomionego przez start()) nazwa wątku
        // będzie "Watek-C (przez start())" - bo naprawdę działa w NOWYM
        // wątku, nie w main!

        /*
         * ============================================================
         * ⚠️ WAŻNE: start() MOŻNA WYWOŁAĆ TYLKO RAZ
         * ============================================================
         * Wątek, który już się zakończył (albo już wystartował), nie
         * może wystartować ponownie – rzuci IllegalThreadStateException.
         * Więcej o tym w Lesson06 (cykl życia wątku).
         */

        System.out.println("\n=== PONOWNE start() na zakonczonym watku ===");
        try {
            watek.start(); // watek "Watek-A" już się zakończył (join() wyżej)
        } catch (IllegalThreadStateException e) {
            System.out.println("Blad: " + e);
        }

        /*
         * ============================================================
         * 🔍 CIEKAWOSTKA: KOLEJNOŚĆ WYPISYWANIA JEST NIEDETERMINISTYCZNA
         * ============================================================
         * Gdy odpalimy kilka wątków przez start() BEZ czekania (join()),
         * kolejność ich wypisów na konsoli NIE jest gwarantowana – zależy
         * od schedulera systemu operacyjnego. Zobaczmy to na 3 wątkach:
         */

        System.out.println("\n=== KOLEJNOŚĆ WYKONANIA WIELU WĄTKÓW (niedeterministyczna) ===");
        MyThread w1 = new MyThread("W1");
        MyThread w2 = new MyThread("W2");
        MyThread w3 = new MyThread("W3");
        w1.start();
        w2.start();
        w3.start();
        w1.join();
        w2.join();
        w3.join();
        // Kolejność linii "... wykonuje sie w watku: W1/W2/W3" może się
        // różnić przy każdym uruchomieniu programu - to normalne i
        // oczekiwane zachowanie wielowątkowości!

        System.out.println("\n=== KONIEC LEKCJI 2 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - extends Thread + nadpisanie run() + wywołanie start() to
         *   podstawowy (choć nie zawsze najlepszy - patrz Lesson03)
         *   sposób tworzenia wątku
         * - run() wywołane bezpośrednio = zwykłe wywołanie metody,
         *   BEZ nowego wątku, wykonuje się synchronicznie
         * - start() = tworzy NOWY wątek systemowy, który wywoła run()
         *   asynchronicznie względem wątku wywołującego
         * - start() można wywołać na danym obiekcie Thread TYLKO RAZ
         *   (ponowne wywołanie -> IllegalThreadStateException)
         * - Kolejność wykonania wielu wątków uruchomionych przez start()
         *   jest niedeterministyczna (zależy od schedulera OS/JVM)
         */
    }

    /**
     * Prosty wątek demonstracyjny - dziedziczy po Thread i nadpisuje run().
     */
    static class MyThread extends Thread {

        MyThread(String name) {
            super(name); // ustawia nazwę wątku (patrz Lesson05 - getName/setName)
        }

        @Override
        public void run() {
            System.out.println(getName() + " wykonuje sie w watku: " + Thread.currentThread().getName());
        }
    }
}
