package com.example.javaquest._05_multithreading.Lesson09_Atomicity;

public class _Lesson09_Atomicity {

    // Pole wspoldzielone - do demonstracji nieatomowego count++
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 CZYM JEST OPERACJA ATOMOWA?
         * ============================================================
         * Operacja ATOMOWA (atomic operation) to operacja, która z punktu
         * widzenia innych wątków wykonuje się "za jednym zamachem" -
         * NIE MOŻNA jej przerwać ani zaobserwować w trakcie wykonywania
         * w jakimś "częściowym" stanie. Albo wykonała się CAŁA, albo
         * WCALE (nigdy "połowicznie").
         *
         * To kluczowe pojęcie dla wielowątkowości: jeśli operacja na
         * współdzielonych danych NIE jest atomowa, to inny wątek może
         * "wejść w środek" jej wykonywania i zobaczyć/nadpisać niespójny
         * stan - to prowadzi do race condition (patrz Lesson07_RaceCondition).
         */

        /*
         * ============================================================
         * 🔍 DLACZEGO count++ NIE JEST ATOMOWE?
         * ============================================================
         * `count++` wygląda jak jedna instrukcja, ale kompiluje się (i
         * wykonuje na poziomie JVM/procesora) jako TRZY oddzielne kroki:
         *
         *   1. ODCZYT  (read)   - pobierz aktualną wartość `count`
         *   2. MODYFIKACJA (modify) - dodaj 1 do pobranej wartości
         *   3. ZAPIS   (write)  - zapisz nową wartość z powrotem do `count`
         *
         * Między KAŻDYM z tych kroków scheduler może przełączyć się na
         * inny wątek! To otwiera okno na INTERLEAVING (przeplot) instrukcji
         * dwóch wątków.
         *
         * ============================================================
         * 🔍 INTERLEAVING KROK PO KROKU (dwa wątki, count = 0)
         * ============================================================
         *
         *   Watek A                  Watek B                  count
         *   --------                 --------                 -----
         *   ODCZYT count -> 0                                   0
         *                            ODCZYT count -> 0          0    <- B czyta PRZED zapisem A!
         *   MODYFIKACJA 0+1 = 1                                  0
         *                            MODYFIKACJA 0+1 = 1         0
         *   ZAPIS count = 1                                      1
         *                            ZAPIS count = 1             1  <- B NADPISUJE wynik A!
         *
         *   Wynik koncowy: count == 1
         *   Oczekiwany wynik: count == 2 (dwie inkrementacje!)
         *
         * Jedna inkrementacja "zniknęła" - to właśnie brak atomowości.
         * Zobacz Lesson07_RaceCondition po pełną demonstrację tego zjawiska
         * na wielu wątkach i wielu iteracjach.
         */

        System.out.println("=== count++ NIE jest atomowe - demo interleaving ===");
        System.out.println("Krok 1: ODCZYT, Krok 2: MODYFIKACJA (+1), Krok 3: ZAPIS");
        System.out.println("Miedzy tymi krokami scheduler MOZE przelaczyc watek -");
        System.out.println("stad mozliwosc zgubienia inkrementacji (patrz Lesson07).");

        int threadCount = 4;
        int incrementsPerThread = 200_000;
        int expected = threadCount * incrementsPerThread;
        count = 0;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    count++; // ❌ NIEATOMOWE: odczyt + modyfikacja + zapis
                }
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join(10_000); // bezpiecznik czasowy

        System.out.println("Oczekiwany wynik: " + expected);
        System.out.println("Rzeczywisty wynik: " + count);
        if (count != expected) {
            System.out.println("❌ Brakuje " + (expected - count) + " inkrementacji - dowod braku atomowosci.");
        } else {
            System.out.println("⚠️ Tym razem sie zgodzilo - to PRZYPADEK, nie gwarancja (niedeterminizm).");
        }

        /*
         * ============================================================
         * ✅ KTÓRE OPERACJE SĄ ATOMOWE Z NATURY W JAVIE?
         * ============================================================
         * Z SPECYFIKACJI JĘZYKA JAVA (JLS) wynikają pewne operacje, które
         * SĄ atomowe bez żadnej dodatkowej synchronizacji:
         *
         * 1) PRZYPISANIE do zmiennej referencyjnej (odwołania do obiektu):
         *      obiekt = nowyObiekt;   // atomowe
         *
         * 2) PRZYPISANIE do większości zmiennych PRYMITYWNYCH: boolean,
         *    byte, short, char, int, float - odczyt i zapis tych typów
         *    (32-bitowych i mniejszych) SĄ atomowe.
         *
         * 3) WYJĄTEK: long i double (typy 64-bitowe) BEZ słowa kluczowego
         *    `volatile` NIE MAJĄ gwarancji atomowości na niektórych
         *    (starszych/32-bitowych) architekturach - JVM może rozbić
         *    odczyt/zapis na DWIE operacje 32-bitowe, co teoretycznie
         *    pozwala zaobserwować "rozdarta" wartość (np. górne 32 bity
         *    z jednego zapisu, dolne z innego). Deklaracja `volatile long`
         *    / `volatile double` PRZYWRACA gwarancję atomowości odczytu/zapisu.
         *
         * ⚠️ UWAGA: "atomowy odczyt/zapis" to NIE to samo co "bezpieczny
         * wątkowo"! Samo przypisanie `x = 5;` jest atomowe, ale operacja
         * ZŁOŻONA typu `x++` (odczyt-modyfikacja-zapis) NIGDY nie jest
         * atomowa sama z siebie - niezależnie od typu zmiennej!
         */

        System.out.println("\n=== Operacje atomowe z natury ===");

        // 1) Przypisanie referencji - atomowe
        String napis = "poczatkowy";
        napis = "nowy"; // atomowe przypisanie referencji
        System.out.println("Przypisanie referencji (atomowe): " + napis);

        // 2) Przypisanie int - atomowe
        int liczba = 10;
        liczba = 20; // atomowe
        System.out.println("Przypisanie int (atomowe): " + liczba);

        // 3) long BEZ volatile - odczyt/zapis MOZE (teoretycznie) nie byc atomowy
        long licznikLong = 0L; // bez volatile
        licznikLong = 123456789012345L; // w praktyce na wiekszosci wspolczesnych JVM/CPU
        // (64-bit) jest to atomowe, ale JLS tego NIE GWARANTUJE bez volatile!
        System.out.println("Przypisanie long bez volatile (JLS nie gwarantuje atomowosci): " + licznikLong);

        /*
         * ============================================================
         * ❌ CZEGO NIE ROBIĆ / NA CO UWAŻAĆ
         * ============================================================
         * - Nie zakładaj, że skoro "to tylko jedna linijka kodu", to jest
         *   atomowa - liczy się co się dzieje na poziomie bajtkodu/CPU.
         * - `x++`, `x += y`, `x = x + 1` to ZŁOŻONE operacje (odczyt +
         *   modyfikacja + zapis) - NIGDY nie są atomowe same z siebie.
         * - `long`/`double` bez `volatile` - unikaj polegania na
         *   atomowości pojedynczego odczytu/zapisu w kodzie wielowątkowym.
         *
         * ✅ JAK ZAPEWNIĆ ATOMOWOŚĆ OPERACJI ZŁOŻONYCH?
         * - synchronized (Lesson11_Synchronized)
         * - klasy java.util.concurrent.atomic, np. AtomicInteger.incrementAndGet()
         *   (Lesson17_AtomicClasses)
         * - java.util.concurrent.locks.Lock (Lesson18_LockAndReentrantLock)
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Operacja atomowa = wykonuje się "za jednym zamachem", nie da
         *   się jej zaobserwować w stanie częściowym.
         * - `count++` to TRZY kroki: odczyt -> modyfikacja -> zapis -
         *   NIEATOMOWE, podatne na interleaving i utratę inkrementacji.
         * - Atomowe z natury: przypisanie do referencji, przypisanie do
         *   prymitywów <= 32 bitów (int, boolean, byte, short, char, float).
         * - long/double BEZ volatile - JLS NIE gwarantuje atomowości
         *   pojedynczego odczytu/zapisu.
         * - Operacje ZŁOŻONE (odczyt-modyfikacja-zapis) nigdy nie są
         *   atomowe same z siebie - potrzebują synchronized, Atomic*
         *   lub Lock.
         */
    }
}
