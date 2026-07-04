package com.example.javaquest._05_multithreading.Lesson09_Atomicity;

public class _Exercises_Lesson09_Atomicity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TwoThreadsIncrementDemo {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj `static int count = 0`. Uruchom 2 wątki, każdy wykonujący
         * count++ 50_000 razy (join(10_000)). Wypisz oczekiwany (100_000) i rzeczywisty
         * wynik – potwierdź lub zaprzecz utracie inkrementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FourThreadsSmallerLoad {
        /*
         * 🧪 Zadanie 2:
         * Powtórz eksperyment z 4 wątkami, każdy wykonujący count++ 25_000 razy
         * (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EightThreadsAmplifiedLoss {
        /*
         * 🧪 Zadanie 3:
         * Powtórz eksperyment z 8 wątkami, każdy wykonujący count++ 25_000 razy
         * (join(10_000)). Porównaj różnicę (expected - actual) z wynikiem z Zadania 2
         * i skomentuj, czy więcej wątków zwykle powiększa utratę inkrementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AtomicReferenceReassignment {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj `static volatile String label = "A"`. Uruchom wątek zamieniający
         * label na przemian między "A" i "B" 100_000 razy, oraz drugi wątek odczytujący
         * label 100_000 razy i sprawdzający, czy wartość to ZAWSZE "A" lub "B" (nigdy null
         * ani inny "zlepek"). Użyj join(10_000) i wypisz wynik weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LongFieldWithoutVolatile {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj `static long value = 0L` (bez volatile). Jeden wątek ustawia
         * value = 123456789012345L, drugi wątek (po Thread.sleep(50)) odczytuje i wypisuje
         * value. Skomentuj w printcie, że JLS nie gwarantuje atomowości odczytu/zapisu
         * long bez volatile, mimo że w praktyce na współczesnych JVM zwykle działa poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AssignmentVsCompoundSideBySide {
        /*
         * 🧪 Zadanie 6:
         * Porównaj dwa scenariusze z 4 wątkami x 50_000 operacji: (a) każdy wątek
         * wykonuje `x = 5;` na WŁASNYM polu lokalnym (brak współdzielenia – zawsze
         * poprawne), (b) 4 wątki wykonują `count++` na WSPÓLNYM polu (błędne). Wypisz
         * oba wyniki i skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompoundPlusEqualsFive {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj `static int total = 0`. Uruchom 4 wątki wykonujące `total += 5`
         * po 20_000 razy każdy (join(10_000)). Wypisz oczekiwany (400_000) i rzeczywisty
         * wynik, potwierdzając że += jest równie nieatomowe jak ++.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DoubleCompoundSumLoss {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj `static double sum = 0.0`. Uruchom 4 wątki, każdy wykonujący
         * `sum += 1.5` 20_000 razy (join(10_000)). Wypisz oczekiwaną (120_000.0)
         * i rzeczywistą sumę – pokaż utratę aktualizacji także dla typu double.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SingleThreadVsSplitAcrossThreads {
        /*
         * 🧪 Zadanie 9:
         * Wykonaj count++ 1_000_000 razy w JEDNYM wątku (bez współbieżności) i sprawdź,
         * że wynik jest zawsze poprawny. Następnie podziel te same 1_000_000 inkrementacji
         * pomiędzy 4 wątki (po 250_000 każdy) i porównaj wyniki – pokaż, że problem
         * dotyczy WSPÓŁBIEŻNOŚCI, a nie samej liczby operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ThreadCountVsResultTable {
        /*
         * 🧪 Zadanie 10:
         * Dla liczby wątków 1, 2, 4, 8 (przy stałej sumie 200_000 inkrementacji
         * rozdzielonej równo między wątki) uruchom eksperyment count++ i wypisz tabelę:
         * liczba wątków | oczekiwany wynik | rzeczywisty wynik | różnica.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UnsafeBankAccountDeposit {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj klasę UnsafeBankAccount z polem `int balance` i metodą
         * `void deposit(int amount) { balance += amount; }` (bez synchronizacji).
         * Uruchom 4 wątki, każdy wpłacający 10 jednostek 20_000 razy (join(10_000)).
         * Wypisz oczekiwane i rzeczywiste saldo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UnsafeStatsCollectorSumCount {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj klasę UnsafeStatsCollector z polami `long sum, long count` i metodą
         * record(int value) zwiększającą oba pola (bez synchronizacji). Uruchom 4 wątki
         * rejestrujące wartość 7 po 20_000 razy każdy (join(10_000)). Wypisz oczekiwane
         * i rzeczywiste sum/count, sprawdzając czy sum == count * 7.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ManualInterleavingDemo {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj ręcznie 3 kroki count++ (odczyt do zmiennej lokalnej,
         * Thread.yield(), modyfikacja +1, zapis) w metodzie wywoływanej przez 2 wątki
         * po 1000 razy każdy (join(5_000)), celowo wymuszając Thread.yield() między
         * krokami by zwiększyć szansę na przeplot. Wypisz oczekiwany i rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IncrementVsExplicitAddOne {
        /*
         * 🧪 Zadanie 14:
         * Porównaj `count++` i `count = count + 1` uruchomione przez 4 wątki
         * (po 25_000 razy każdy, join(10_000)) na dwóch osobnych, współdzielonych
         * polach. Wypisz oba wyniki i potwierdź, że oba zawodzą tak samo (to ta sama
         * operacja odczyt-modyfikacja-zapis na poziomie bajtkodu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_IndependentFieldsNoRace {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj dwa niezależne pola `int counterA` i `int counterB`. Uruchom wątek A
         * inkrementujący WYŁĄCZNIE counterA i wątek B inkrementujący WYŁĄCZNIE counterB
         * (po 100_000 razy każdy, join(10_000)). Wypisz oba wyniki i potwierdź poprawność –
         * brak współdzielenia = brak problemu z atomowością.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SharedStringBuilderAppend {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj współdzielony `StringBuilder sb = new StringBuilder()`. Uruchom
         * 4 wątki, każdy wykonujący sb.append("x") 10_000 razy (join(10_000)). Wypisz
         * oczekiwaną (40_000) i rzeczywistą długość sb.length() – pokaż, że problem
         * nieatomowości dotyczy też metod obiektów, nie tylko prymitywów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VolatileLongCompoundStillBroken {
        /*
         * 🧪 Zadanie 17:
         * Zadeklaruj `volatile long counter = 0`. Uruchom 4 wątki wykonujące counter++
         * po 25_000 razy każdy (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik
         * i wyjaśnij (print), że volatile gwarantuje widoczność (Lesson08), ale NIE
         * atomowość operacji złożonej ++.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RepeatExperimentFiveTimes {
        /*
         * 🧪 Zadanie 18:
         * Uruchom 5 razy pod rząd ten sam test (2 wątki x 50_000 count++, join(5_000)),
         * resetując count do 0 przed każdą próbą. Dla każdej próby wypisz, czy wynik
         * się zgadzał z oczekiwanym, a na końcu podsumuj liczbę udanych/nieudanych prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ReferenceSwapUnderRace {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj niemutowalny record Snapshot(int id). Zadeklaruj
         * `static volatile Snapshot current`. Uruchom 4 wątki, każdy 10_000 razy
         * podmieniający current na NOWY Snapshot z unikalnym id (join(10_000)).
         * Po zakończeniu odczytaj current i potwierdź, że to KOMPLETNY, poprawny
         * obiekt Snapshot (nigdy null ani "uszkodzony") – demonstracja atomowości
         * przypisania referencji mimo wyścigu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_UnsafeMinMaxTracker {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj klasę UnsafeMinMaxTracker z polem `int max = Integer.MIN_VALUE`
         * i metodą `void offer(int value) { if (value > max) max = value; }` (bez
         * synchronizacji – check-then-act nieatomowe). Uruchom 4 wątki podające wartości
         * z ustalonej (seed) losowej tablicy (join(10_000)). Porównaj wynik max z wartością
         * obliczoną sekwencyjnie z tych samych danych i skomentuj ewentualne rozbieżności.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_IncrementDecrementInvariantBroken {
        /*
         * 🧪 Zadanie 21:
         * Zadeklaruj `static int balance = 0`. Uruchom 4 wątki, z których każdy
         * na przemian wykonuje balance++ i (co 10-ty raz) balance-- przez 40_000
         * iteracji (join(10_000)). Oblicz oczekiwany wynik na podstawie liczby operacji
         * i porównaj z rzeczywistym – pokaż, że złożony niezmiennik też może zostać złamany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiFieldStatisticsRace {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj klasę Statistics z polami `int count, long total, int max` (bez
         * synchronizacji). Wygeneruj ustaloną (seed) tablicę 40_000 losowych int i podziel
         * ją między 4 wątki aktualizujące statystyki (join(10_000)). Oblicz oczekiwane
         * count/total/max sekwencyjnie na tych samych danych i porównaj z rzeczywistymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ErrorGrowthAcrossThreadCounts {
        /*
         * 🧪 Zadanie 23:
         * Dla liczby wątków 1, 2, 4, 8, 16 (stała suma 320_000 inkrementacji count++
         * rozdzielona równo) uruchom eksperyment i wypisz tabelę: liczba wątków | błąd
         * (expected - actual) | procent błędu. Skomentuj ogólny trend (niedeterministyczny,
         * ale zwykle rosnący wraz z liczbą wątków).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TrialStatisticsSummary {
        /*
         * 🧪 Zadanie 24:
         * Uruchom 50 prób małego wyścigu (2 wątki x 100 count++ każdy, join(1_000))
         * i zlicz, ile prób dało poprawny wynik (200), a ile błędny. Wypisz podsumowanie
         * statystyczne (liczba/procent poprawnych vs błędnych prób) ilustrujące
         * niedeterminizm.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LazyReferenceRaceWastedWork {
        /*
         * 🧪 Zadanie 25:
         * Zadeklaruj `static volatile ExpensiveObject instance = null` (record z polem
         * int, tworzenie symuluje "kosztowną" pracę np. Thread.sleep(5)). Uruchom
         * 4 wątki, z których każdy (bez synchronizacji) sprawdza czy instance==null,
         * jeśli tak - tworzy NOWY obiekt i przypisuje go do instance (join(5_000)).
         * Policz (przez dodatkowy licznik) ile obiektów faktycznie zbudowano vs ile
         * wątków "wygrało" ostateczne przypisanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ThreeCounterImplementationsComparison {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj 3 warianty licznika inkrementowanego przez 4 wątki x 50_000: (a) zwykły
         * `int` bez volatile, (b) `volatile int`, (c) wynik obliczony SEKWENCYJNIE jako
         * punkt odniesienia. Uruchom (a) i (b) współbieżnie (join(10_000)), porównaj oba
         * wyniki z (c) i wypisz tabelę porównawczą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_InventoryOversellingRisk {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj `static int stock = 100_000`. Uruchom 4 wątki "kupujące" wykonujące
         * `stock--` po 20_000 razy każdy (join(10_000), bez synchronizacji). Wypisz
         * oczekiwany (0) i rzeczywisty stan magazynu, komentując ryzyko biznesowe
         * (oversell/undersell) wynikające z braku atomowości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_NegativeStockDetectionAcrossRuns {
        /*
         * 🧪 Zadanie 28:
         * Powtórz eksperyment z Zadania 27 dziesięciokrotnie (resetując stock=1000,
         * 4 wątki x 250 stock-- każdy za każdym razem, join(5_000)). Dla każdego przebiegu
         * sprawdź, czy stock po zakończeniu jest ujemny (niepoprawny stan biznesowy)
         * i policz, w ilu z 10 przebiegów tak się stało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GenericAtomicityTestHarness {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę `boolean testAtomicity(Runnable operation, int threadCount,
         * int opsPerThread, LongSupplier currentValue, long expected)` uruchamiającą
         * podaną operację równolegle (join z limitem czasu) i zwracającą, czy wynik
         * zgadza się z expected. Użyj jej do przetestowania DWÓCH różnych operacji
         * (np. count++ i count += 3 na osobnych polach) i wypisz wynik PASS/FAIL dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ChatCounterCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj symulację "licznika wiadomości czatu": współdzielone (bez synchronizacji)
         * pola `int messagesSent` i `long totalCharacters`. Podziel ustaloną tablicę
         * przykładowych Stringów (wiadomości) między 4 wątki "nadawców" - każdy wątek
         * dla każdej swojej wiadomości robi messagesSent++ i totalCharacters += wiadomosc.length()
         * (join(10_000)). Porównaj oba liczniki z wartościami obliczonymi sekwencyjnie
         * i wypisz raport, które (jeśli któreś) uległy "uszkodzeniu".
         */
        public static void main(String[] args) { }
    }
}
