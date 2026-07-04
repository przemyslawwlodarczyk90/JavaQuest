package com.example.javaquest._05_multithreading.Lesson03_Runnable;

public class _Lesson03_Runnable {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 TWORZENIE WĄTKU PRZEZ Runnable (implements Runnable)
         * ============================================================
         * Drugi (i zazwyczaj LEPSZY) sposób tworzenia wątku:
         * 1) Stwórz klasę, która IMPLEMENTUJE interfejs Runnable
         * 2) Zaimplementuj jedyną metodę tego interfejsu: run()
         * 3) Przekaż INSTANCJĘ tej klasy do konstruktora new Thread(...)
         * 4) Wywołaj start() na obiekcie Thread
         *
         * interface Runnable {
         *     void run(); // jedyna metoda abstrakcyjna
         * }
         *
         * class MyTask implements Runnable {
         *     @Override
         *     public void run() {
         *         // kod zadania do wykonania w wątku
         *     }
         * }
         *
         * Runnable zadanie = new MyTask();
         * Thread watek = new Thread(zadanie); // Thread "opakowuje" zadanie
         * watek.start();
         */

        System.out.println("=== TWORZENIE WĄTKU PRZEZ Runnable ===");
        Runnable zadanie = new MyTask("Zadanie-A");
        Thread watek = new Thread(zadanie, "Watek-Runnable-A");
        watek.start();
        watek.join();

        /*
         * ============================================================
         * 🔍 DLACZEGO Runnable JEST LEPSZE NIŻ extends Thread?
         * ============================================================
         * 1) ODDZIELENIE ZADANIA OD MECHANIZMU WĄTKU
         *    - Runnable opisuje CO ma zostać wykonane (zadanie/logika
         *      biznesowa).
         *    - Thread opisuje JAK to wykonać (mechanizm wątku systemowego).
         *    - Dzięki temu to samo zadanie (Runnable) można uruchomić
         *      różnymi sposobami: przez zwykły Thread, przez
         *      ExecutorService/pulę wątków (poznamy w kolejnych
         *      lekcjach), bez zmiany klasy zadania.
         *
         * 2) JAVA NIE MA WIELODZIEDZICZENIA KLAS
         *    - Klasa może extends TYLKO JEDNĄ klasę.
         *    - Jeśli Twoja klasa "zużyje" jedyny slot na extends na
         *      Thread, nie będzie mogła już dziedziczyć po żadnej innej
         *      klasie (np. własnej klasie bazowej z logiką domenową).
         *    - implements Runnable "kosztuje" nic - klasa może nadal
         *      dziedziczyć po dowolnej innej klasie I implementować
         *      dowolną liczbę innych interfejsów.
         *
         * 3) MOŻLIWOŚĆ IMPLEMENTOWANIA INNYCH INTERFEJSÓW
         *    - Klasa implementująca Runnable może JEDNOCZEŚNIE
         *      implementować inne interfejsy (np. Comparable,
         *      AutoCloseable) - bez ograniczeń wielodziedziczenia.
         *
         * 4) SEMANTYCZNIE CZYSTSZE
         *    - "To zadanie" (Runnable) to nie to samo co "to jest wątek"
         *      (extends Thread). Zadanie samo w sobie NIE JEST wątkiem -
         *      to tylko "paczka pracy do wykonania".
         *
         * ✅ REKOMENDACJA: w praktyce prawie zawsze używaj Runnable
         * (albo Callable przy pracy z ExecutorService - poznamy
         * w dalszych lekcjach), a extends Thread traktuj jako ciekawostkę
         * historyczną / rzadki przypadek.
         */

        System.out.println("\n=== TA SAMA INSTANCJA Runnable, WIELE WĄTKÓW ===");
        // Kolejna zaleta: jedną instancję Runnable można "podpiąć" pod
        // wiele różnych wątków (współdzielenie stanu zadania).
        Runnable wspolneZadanie = new MyTask("Zadanie-Wspolne");
        Thread watekX = new Thread(wspolneZadanie, "Watek-X");
        Thread watekY = new Thread(wspolneZadanie, "Watek-Y");
        watekX.start();
        watekY.start();
        watekX.join();
        watekY.join();
        // Obie linie wypiszą "Zadanie-Wspolne" ale z RÓŻNĄ nazwą wątku
        // (Watek-X / Watek-Y) - bo to ten sam Runnable, ale różne Thread.

        /*
         * ============================================================
         * 🔹 PRZYKŁAD: KLASA IMPLEMENTUJĄCA Runnable I INNY INTERFEJS
         * ============================================================
         * Poniżej klasa, która JEDNOCZEŚNIE implementuje Runnable oraz
         * Comparable - coś, co byłoby niemożliwe, gdybyśmy "zużyli"
         * dziedziczenie na extends Thread (bo Thread już implementuje
         * Runnable, ale nie Comparable, a i tak ograniczałoby nas to
         * do JEDNEJ klasy bazowej).
         */

        System.out.println("\n=== Runnable + Comparable JEDNOCZEŚNIE ===");
        PriorytetowaneZadanie zadanieWazne = new PriorytetowaneZadanie("Wazne", 1);
        PriorytetowaneZadanie zadanieDrobne = new PriorytetowaneZadanie("Drobne", 5);
        System.out.println("Porownanie priorytetow: " + zadanieWazne.compareTo(zadanieDrobne));
        // Wypisze liczbę ujemną (Wazne ma niższy numer priorytetu = ważniejsze)

        Thread watekWazny = new Thread(zadanieWazne, "Watek-Wazny");
        watekWazny.start();
        watekWazny.join();

        System.out.println("\n=== KONIEC LEKCJI 3 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Runnable to interfejs funkcyjny z jedną metodą run()
         * - Tworzymy klasę implementującą Runnable, przekazujemy jej
         *   instancję do new Thread(runnable), wywołujemy start()
         * - Zalety Runnable nad extends Thread:
         *   1) oddziela zadanie (CO robić) od mechanizmu wątku (JAK)
         *   2) nie "zużywa" jedynego slotu po extends w Javie
         *   3) pozwala jednocześnie implementować inne interfejsy
         *   4) tę samą instancję Runnable można uruchomić w wielu
         *      różnych wątkach (współdzielenie zadania/stanu)
         * ✅ Runnable to domyślny, rekomendowany wybór w praktyce
         */
    }

    /**
     * Proste zadanie implementujące Runnable.
     */
    static class MyTask implements Runnable {
        private final String nazwa;

        MyTask(String nazwa) {
            this.nazwa = nazwa;
        }

        @Override
        public void run() {
            System.out.println(nazwa + " wykonuje sie w watku: " + Thread.currentThread().getName());
        }
    }

    /**
     * Zadanie implementujące JEDNOCZEŚNIE Runnable i Comparable -
     * demonstracja, że implements nie ogranicza nas tak jak extends.
     */
    static class PriorytetowaneZadanie implements Runnable, Comparable<PriorytetowaneZadanie> {
        private final String nazwa;
        private final int priorytet; // im niższa liczba, tym ważniejsze

        PriorytetowaneZadanie(String nazwa, int priorytet) {
            this.nazwa = nazwa;
            this.priorytet = priorytet;
        }

        @Override
        public void run() {
            System.out.println(nazwa + " (priorytet=" + priorytet + ") wykonuje sie w watku: "
                    + Thread.currentThread().getName());
        }

        @Override
        public int compareTo(PriorytetowaneZadanie inne) {
            return Integer.compare(this.priorytet, inne.priorytet);
        }
    }
}
