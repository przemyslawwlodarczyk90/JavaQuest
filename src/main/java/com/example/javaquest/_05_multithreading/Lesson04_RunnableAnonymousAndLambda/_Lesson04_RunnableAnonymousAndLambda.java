package com.example.javaquest._05_multithreading.Lesson04_RunnableAnonymousAndLambda;

public class _Lesson04_RunnableAnonymousAndLambda {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 TO SAMO ZADANIE, TRZY SPOSOBY ZAPISU
         * ============================================================
         * W Lesson03 tworzyliśmy osobną klasę implementującą Runnable.
         * To poprawne, ale dla PROSTYCH, jednorazowych zadań bywa
         * "za dużo kodu" (osobny plik/klasa dla paru linijek logiki).
         *
         * Zobaczmy TO SAMO zadanie (wypisanie powitania) zapisane na
         * 3 rosnąco krótsze sposoby:
         * 1) Osobna klasa implementująca Runnable (jak w Lesson03)
         * 2) Anonimowa klasa Runnable (bez nazwy, "w locie")
         * 3) Wyrażenie lambda (najkrótszy zapis)
         */

        System.out.println("=== SPOSÓB 1: OSOBNA KLASA (jak w Lesson03) ===");
        Runnable zadanie1 = new PowitanieTask("Ala");
        Thread watek1 = new Thread(zadanie1, "Watek-1-OsobnaKlasa");
        watek1.start();
        watek1.join();

        /*
         * ============================================================
         * 🔹 SPOSÓB 2: ANONIMOWA KLASA Runnable
         * ============================================================
         * Anonimowa klasa to klasa BEZ NAZWY, zdefiniowana "w miejscu
         * użycia" - od razu tworzymy jej instancję. Przydatna, gdy
         * potrzebujemy Runnable "jednorazowo" i nie chcemy zaśmiecać
         * kodu osobnym plikiem/klasą.
         *
         * Runnable zadanie = new Runnable() {
         *     @Override
         *     public void run() {
         *         // kod zadania
         *     }
         * };
         */

        System.out.println("\n=== SPOSÓB 2: ANONIMOWA KLASA Runnable ===");
        String imie2 = "Bartek";
        Runnable zadanie2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Czesc, " + imie2 + "! (anonimowa klasa) w watku: "
                        + Thread.currentThread().getName());
            }
        };
        Thread watek2 = new Thread(zadanie2, "Watek-2-Anonimowa");
        watek2.start();
        watek2.join();

        /*
         * ============================================================
         * 🔹 SPOSÓB 3: LAMBDA
         * ============================================================
         * Runnable to INTERFEJS FUNKCYJNY (@FunctionalInterface) - ma
         * DOKŁADNIE JEDNĄ metodę abstrakcyjną (run()). Każdy interfejs
         * funkcyjny może być zapisany jako wyrażenie lambda - kompilator
         * "wie", że lambda ma zaimplementować tę jedną metodę.
         *
         * @FunctionalInterface
         * public interface Runnable {
         *     void run();
         * }
         *
         * Runnable zadanie = () -> {
         *     // kod zadania
         * };
         *
         * To DOKŁADNIE to samo co anonimowa klasa z punktu 2, ale bez
         * całego "szablonu" (new Runnable() { @Override public void
         * run() { ... } }) - sam kompilator generuje odpowiednik
         * anonimowej klasy "pod spodem" (bajtkod).
         */

        System.out.println("\n=== SPOSÓB 3: LAMBDA ===");
        String imie3 = "Celina";
        Runnable zadanie3 = () -> System.out.println("Czesc, " + imie3 + "! (lambda) w watku: "
                + Thread.currentThread().getName());
        Thread watek3 = new Thread(zadanie3, "Watek-3-Lambda");
        watek3.start();
        watek3.join();

        // Można też pominąć zmienną pośrednią i przekazać lambdę wprost:
        Thread watek3b = new Thread(() -> System.out.println("Lambda wprost do Thread, watek: "
                + Thread.currentThread().getName()), "Watek-3b-LambdaInline");
        watek3b.start();
        watek3b.join();

        /*
         * ============================================================
         * 🔍 KIEDY ANONIMOWA KLASA NADAL MA SENS?
         * ============================================================
         * Lambda jest krótsza, ale MA OGRANICZENIA - działa dobrze
         * tylko gdy implementujemy DOKŁADNIE jedną metodę bez
         * dodatkowego stanu. Anonimowa klasa nadal jest lepszym
         * wyborem, gdy:
         *
         * 1) POTRZEBUJESZ WŁASNYCH PÓL (stanu) w obiekcie zadania
         *    - Lambda nie może mieć własnych pól (nie ma "swojego"
         *      obiektu w tym sensie co klasa) - może jedynie
         *      "przechwytywać" (capture) zmienne z otoczenia (effectively
         *      final).
         *    - Anonimowa klasa MOŻE mieć własne pola, które zmieniają
         *      się w czasie życia obiektu.
         *
         * 2) POTRZEBUJESZ ZAIMPLEMENTOWAĆ WIĘCEJ NIŻ JEDNĄ METODĘ
         *    - Lambda działa tylko dla interfejsów z JEDNĄ metodą
         *      abstrakcyjną (funkcyjnych). Runnable ma tylko run(),
         *      ale gdybyśmy potrzebowali obiektu z dodatkowymi
         *      metodami pomocniczymi - anonimowa klasa to umożliwia.
         *
         * 3) POTRZEBUJESZ ODWOŁAĆ SIĘ DO "SIEBIE" (this wskazujące na
         *    instancję anonimowej klasy, a nie na klasę zewnętrzną)
         *
         * Przykład poniżej: zadanie zliczające, ile razy zostało
         * uruchomione - wymaga WŁASNEGO POLA (licznika), więc lambda
         * by tu nie wystarczyła w naturalny sposób.
         */

        System.out.println("\n=== ANONIMOWA KLASA Z WŁASNYM STANEM (licznik) ===");
        Runnable liczace = new Runnable() {
            private int licznik = 0; // własne pole - niemożliwe w czystej lambdzie

            @Override
            public void run() {
                licznik++;
                System.out.println("Uruchomienie #" + licznik + " w watku: "
                        + Thread.currentThread().getName());
            }
        };
        // Uruchamiamy to samo zadanie (ten sam obiekt!) trzykrotnie,
        // za każdym razem sekwencyjnie w nowym wątku - licznik rośnie,
        // bo to WCIĄŻ ten sam obiekt anonimowej klasy.
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(liczace, "Watek-Liczacy-" + i);
            t.start();
            t.join(); // czekamy, by kolejność uruchomień była deterministyczna
        }
        // Wypisze: Uruchomienie #1, #2, #3 (bo stan licznik jest
        // przechowywany w obiekcie anonimowej klasy między wywołaniami)

        System.out.println("\n=== KONIEC LEKCJI 4 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - To samo zadanie można zapisać na 3 sposoby, od najdłuższego
         *   do najkrótszego:
         *   1) osobna klasa implements Runnable (czytelna, wielokrotnego
         *      użytku, dobra dla złożonej logiki)
         *   2) anonimowa klasa Runnable (kod "w miejscu użycia", bez
         *      osobnego pliku, ale wciąż pełna składnia klasy)
         *   3) lambda (najkrótszy zapis, dla prostych, jednometodowych
         *      zadań)
         * - Runnable to interfejs funkcyjny (@FunctionalInterface) -
         *   dokładnie jedna metoda abstrakcyjna (run()), dlatego można
         *   go zapisać jako lambdę
         * - Lambda NIE MA własnych pól/stanu (tylko capture zmiennych
         *   z otoczenia, effectively final) i działa tylko dla
         *   interfejsów z jedną metodą abstrakcyjną
         * - Anonimowa klasa nadal ma sens, gdy potrzebujesz: własnego
         *   stanu (pól), więcej niż jednej metody, lub odwołania do
         *   "siebie" (this klasy anonimowej)
         * ✅ W praktyce: proste, bezstanowe zadania -> lambda;
         *    złożone/stanowe zadania -> osobna klasa lub anonimowa klasa
         */
    }

    /**
     * Osobna klasa implementująca Runnable (sposób 1, jak w Lesson03).
     */
    static class PowitanieTask implements Runnable {
        private final String imie;

        PowitanieTask(String imie) {
            this.imie = imie;
        }

        @Override
        public void run() {
            System.out.println("Czesc, " + imie + "! (osobna klasa) w watku: "
                    + Thread.currentThread().getName());
        }
    }
}
