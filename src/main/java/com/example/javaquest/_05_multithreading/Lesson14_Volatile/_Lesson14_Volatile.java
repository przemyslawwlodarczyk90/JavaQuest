package com.example.javaquest._05_multithreading.Lesson14_Volatile;

public class _Lesson14_Volatile {

    // Demo 1: volatile NIE daje atomowości
    private static volatile int volatileCounter = 0;

    // Demo 2: volatile flaga - typowe, poprawne zastosowanie
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 SŁOWO KLUCZOWE volatile – CO GWARANTUJE?
         * ============================================================
         * `volatile` to modyfikator pola, który gwarantuje WIDOCZNOŚĆ
         * (visibility) zmian między wątkami (patrz: Lesson08_VisibilityProblem).
         *
         * Bez volatile, wątek A może nie zobaczyć zmiany wartości pola
         * dokonanej przez wątek B (bo odczytuje wartość z pamięci podręcznej
         * CPU/rejestru zamiast z pamięci głównej). `volatile` wymusza:
         * - każdy ZAPIS do pola trafia bezpośrednio do pamięci głównej,
         * - każdy ODCZYT pola pobiera aktualną wartość z pamięci głównej.
         *
         * To rozwiązuje problem widoczności opisany w Lesson08.
         *
         * ⚠️ ALE: `volatile` NIE GWARANTUJE ATOMOWOŚCI operacji złożonych
         * (typu odczytaj-zmodyfikuj-zapisz, np. `counter++`)!
         */

        /*
         * ============================================================
         * ⚠️ CZĘSTY BŁĄD: volatile int counter i counter++
         * ============================================================
         * `counter++` to tak naprawdę TRZY operacje:
         *   1. odczytaj counter
         *   2. dodaj 1
         *   3. zapisz counter
         *
         * volatile gwarantuje, że KAŻDA z tych operacji z osobna jest
         * widoczna natychmiast dla innych wątków - ale nie gwarantuje,
         * że cała trójka wykona się jako JEDNA, niepodzielna operacja.
         * Dwa wątki mogą odczytać tę samą wartość, oba dodać 1 i oba
         * zapisać tę samą (za niską) wartość - tracimy inkrementacje!
         *
         * To wciąż RACE CONDITION, mimo volatile!
         */

        System.out.println("=== Demo: volatile NIE gwarantuje atomowości ===");
        int threadsCount = 8;
        int iterations = 10_000;
        int expected = threadsCount * iterations;

        Thread[] threads = new Thread[threadsCount];
        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    volatileCounter++; // ❌ złożona operacja - nie jest atomowa!
                }
            });
        }
        for (Thread th : threads) th.start();
        for (Thread th : threads) th.join(10_000); // bounded join

        System.out.println("Oczekiwany wynik: " + expected);
        System.out.println("Rzeczywisty wynik: " + volatileCounter);
        if (volatileCounter != expected) {
            System.out.println("-> Race condition potwierdzona: wynik jest NIŻSZY " +
                    "niż oczekiwany, mimo że pole jest volatile!");
        } else {
            System.out.println("-> Tym razem się udało (możliwe przy małej rywalizacji), " +
                    "ale to i tak NIE jest bezpieczne - błąd może wystąpić przy kolejnym uruchomieniu.");
        }
        System.out.println("Rozwiązanie: synchronized (Lesson07/Lesson09) albo AtomicInteger (Lesson17).");

        System.out.println();

        /*
         * ============================================================
         * ✅ KIEDY volatile WYSTARCZY? PROSTE FLAGI STEROJĄCE
         * ============================================================
         * Jeśli operacja na polu to TYLKO odczyt albo TYLKO zapis (nigdy
         * odczytaj-zmodyfikuj-zapisz w kilku wątkach naraz), volatile
         * wystarcza w zupełności. Klasyczny przykład: flaga `running` /
         * `shutdownRequested`, którą jeden wątek ustawia (zapis), a inny
         * tylko sprawdza w pętli (odczyt).
         */

        System.out.println("=== Demo: volatile flaga running (poprawne zastosowanie) ===");
        running = true;
        Thread worker = new Thread(() -> {
            long loops = 0;
            // Bezpieczeństwo: dodatkowy górny limit na wypadek błędu w demie
            while (running && loops < 200_000_000L) {
                loops++;
            }
            System.out.println("Worker: zakończono po " + loops + " iteracjach (running=" + running + ")");
        });
        worker.start();

        Thread.sleep(150); // niech worker trochę popracuje
        System.out.println("main: ustawiam running = false");
        running = false; // dzięki volatile widoczne dla wątku worker "od razu"

        worker.join(5_000); // bounded join - bezpieczeństwo
        System.out.println("main: worker żyje? " + worker.isAlive());

        /*
         * ============================================================
         * 🔍 TABELA PORÓWNAWCZA: volatile vs synchronized
         * ============================================================
         * | Cecha                          | volatile           | synchronized          |
         * |---------------------------------|--------------------|-----------------------|
         * | Widoczność zmian (visibility)   | ✅ tak             | ✅ tak                |
         * | Atomowość operacji złożonych    | ❌ nie             | ✅ tak                |
         * | Wzajemne wykluczanie (mutex)     | ❌ nie             | ✅ tak                |
         * | Wydajność                       | szybsze            | wolniejsze (blokada)  |
         * | Może blokować wątek             | nie                | tak (czeka na lock)   |
         * | Typowe zastosowanie             | flagi on/off       | liczniki, listy, itp. |
         *
         * Zasada:
         * - Prosty odczyt/zapis pojedynczej flagi/referencji -> volatile wystarcza
         * - Operacja odczytaj-zmodyfikuj-zapisz (np. licznik) -> synchronized
         *   albo klasa Atomic* (Lesson17_AtomicClasses)
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - volatile = gwarancja WIDOCZNOŚCI zmian między wątkami
         * - volatile NIE daje atomowości - counter++ na polu volatile
         *   nadal jest race condition!
         * - volatile świetnie nadaje się do prostych flag sterujących
         *   (running, shutdownRequested), gdzie jeden wątek zapisuje,
         *   a inne tylko odczytują
         * - Do operacji złożonych (inkrementacja, dodawanie do kolekcji)
         *   potrzeba synchronized albo klas Atomic*
         */
    }
}
