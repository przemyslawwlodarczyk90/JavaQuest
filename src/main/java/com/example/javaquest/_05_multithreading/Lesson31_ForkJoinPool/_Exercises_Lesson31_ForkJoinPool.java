package com.example.javaquest._05_multithreading.Lesson31_ForkJoinPool;

public class _Exercises_Lesson31_ForkJoinPool {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicRecursiveTaskSum {
        /*
         * 🧪 Zadanie 1:
         * Utwórz tablicę long[1000] z wartościami 1..1000. Zaimplementuj
         * RecursiveTask<Long> sumujące ją rekurencyjnie z progiem (threshold)
         * = 100. Uruchom przez pool.invoke() i wypisz wynik oraz porównaj
         * z wzorem na sumę 1..n.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicRecursiveActionDouble {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tablicę int[50] z wartościami 1..50. Zaimplementuj
         * RecursiveAction podwajające każdy element W MIEJSCU, z progiem = 10.
         * Uruchom przez pool.invoke() i wypisz tablicę przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InvokeVsForkJoinManual {
        /*
         * 🧪 Zadanie 3:
         * Mając prostą klasę RecursiveTask<Long> sumującą fragment tablicy
         * (np. long[500]), wywołaj ją dwoma sposobami: (a) pool.invoke(task),
         * (b) task.fork() + task.join() na osobnym zadaniu głównym. Wypisz
         * oba wyniki i potwierdź, że są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FindMaxInArray {
        /*
         * 🧪 Zadanie 4:
         * Utwórz int[2000] z losowymi wartościami (Random z ustalonym seedem).
         * Zaimplementuj RecursiveTask<Integer> znajdujące maksimum, z progiem
         * = 200 (poniżej progu liczone zwykłą pętlą). Wypisz wynik i porównaj
         * go z Arrays.stream(array).max().getAsInt().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CountMatchingPredicate {
        /*
         * 🧪 Zadanie 5:
         * Utwórz int[3000] z wartościami 1..3000. Zaimplementuj RecursiveTask
         * <Long> liczące, ile elementów jest parzystych, z progiem = 300.
         * Wypisz wynik i porównaj go z oczekiwaną wartością (1500).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InvokeAllTwoActions {
        /*
         * 🧪 Zadanie 6:
         * Utwórz int[40] z wartościami 1..40. Zaimplementuj RecursiveAction
         * zwiększające każdy element o 1 w miejscu, z progiem = 8, dzielące
         * tablicę na 2 połowy i łączące podzadania metodą invokeAll(left, right).
         * Wypisz tablicę przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CustomPoolVsCommonPoolBasics {
        /*
         * 🧪 Zadanie 7:
         * Utwórz własny ForkJoinPool(2) oraz użyj ForkJoinPool.commonPool().
         * Wypisz getParallelism() obu pul. Wywołaj tę samą prostą sumującą
         * RecursiveTask (long[1000]) na obu i wypisz oba wyniki (powinny być
         * identyczne). Pamiętaj o zamknięciu WŁASNEGO poola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ShutdownPatternDemo {
        /*
         * 🧪 Zadanie 8:
         * Utwórz własny ForkJoinPool, wywołaj na nim invoke() dla prostego
         * zadania sumującego tablicę long[500]. Następnie wywołaj shutdown()
         * i awaitTermination(5, TimeUnit.SECONDS), wypisując czy zamknięcie
         * powiodło się w limicie czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SquareEachElementTask {
        /*
         * 🧪 Zadanie 9:
         * Utwórz int[500] z wartościami 1..500. Zaimplementuj RecursiveTask
         * <int[]> zwracające NOWĄ tablicę z każdym elementem podniesionym do
         * kwadratu, z progiem = 50. Wypisz kilka pierwszych i ostatnich
         * elementów wyniku dla weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_JoinVsGetDistinction {
        /*
         * 🧪 Zadanie 10:
         * Użyj pool.submit(task) (zamiast invoke()) dla prostego zadania sumującego
         * long[1000], a następnie porównaj dwa sposoby odebrania wyniku: task.join()
         * (bez checked exception) oraz task.get() (rzuca checked
         * InterruptedException/ExecutionException, wymaga try-catch). Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MinAndMaxSimultaneously {
        /*
         * 🧪 Zadanie 11:
         * Utwórz long[5000] z losowymi wartościami. Zaimplementuj RecursiveTask
         * <long[]> zwracające tablicę dwuelementową {min, max}, z progiem = 500,
         * łącząc wyniki podzadań przez Math.min/Math.max. Wypisz wynik i
         * porównaj z sekwencyjnym obliczeniem (streamem lub pętlą).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TotalCharacterCountOverStrings {
        /*
         * 🧪 Zadanie 12:
         * Utwórz String[3000] z losowymi słowami o różnej długości. Zaimplementuj
         * RecursiveTask<Long> liczące łączną liczbę znaków wszystkich napisów,
         * z progiem = 300, dzieląc zakres indeksów. Wypisz wynik i porównaj
         * z sekwencyjnym sumowaniem length().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ParallelAbsoluteValueTransform {
        /*
         * 🧪 Zadanie 13:
         * Utwórz int[5000] z losowymi wartościami (dodatnimi i ujemnymi).
         * Zaimplementuj RecursiveAction zamieniające każdy element na jego
         * wartość bezwzględną W MIEJSCU, z progiem = 500. Zmierz czas wykonania
         * (System.nanoTime) i porównaj z czasem prostej sekwencyjnej pętli
         * robiącej to samo na kopii tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ParallelMergeSortSmall {
        /*
         * 🧪 Zadanie 14:
         * Utwórz int[3000] z losowymi wartościami. Zaimplementuj RecursiveAction
         * realizujące merge sort: powyżej progu = 200 dziel na 2 połowy i
         * scalaj wyniki, poniżej progu sortuj Arrays.sort() jako przypadek
         * bazowy. Zweryfikuj poprawność sortowania (isSorted) i wypisz wynik weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ParallelMatrixRowSums {
        /*
         * 🧪 Zadanie 15:
         * Utwórz macierz int[200][200] z losowymi wartościami. Zaimplementuj
         * RecursiveTask<long[]> zwracające sumę każdego wiersza, dzieląc zakres
         * WIERSZY (nie kolumn) z progiem = 20. Wypisz sumę pierwszych 5 wierszy
         * i porównaj z sekwencyjnym obliczeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CustomParallelismVsDefaultTiming {
        /*
         * 🧪 Zadanie 16:
         * Utwórz long[2_000_000] z wartościami 1..2000000. Uruchom tę samą
         * RecursiveTask sumującą (próg = 10 000) na własnym ForkJoinPool(2) oraz
         * na ForkJoinPool.commonPool() (domyślny poziom równoległości). Zmierz
         * i wypisz czas wykonania obu wariantów, pamiętając o zamknięciu
         * własnego poola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThresholdTuningComparison {
        /*
         * 🧪 Zadanie 17:
         * Dla tej samej tablicy long[1_000_000] uruchom RecursiveTask liczące
         * sumę kwadratów elementów z trzema różnymi progami: 10, 1000, 100000.
         * Zmierz czas wykonania dla każdego progu i wypisz tabelę porównawczą,
         * komentując wpływ zbyt małego/zbyt dużego progu na wydajność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountDivisibleBySeven {
        /*
         * 🧪 Zadanie 18:
         * Utwórz int[100_000] z wartościami 1..100000. Zaimplementuj RecursiveTask
         * <Long> liczące, ile elementów jest podzielnych przez 7, z progiem
         * = 5000. Wypisz wynik i porównaj go z obliczeniem sekwencyjnym (stream/pętla).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_InvokeAllThreeSubtasks {
        /*
         * 🧪 Zadanie 19:
         * Utwórz long[3000]. Zaimplementuj RecursiveTask<Long> sumujące tablicę
         * dzieląc ją NA TRZY (a nie dwie) części o równej długości i łącząc je
         * przez invokeAll(task1, task2, task3), a następnie sumując wyniki
         * task.join() z każdego. Wypisz wynik i porównaj z sumą sekwencyjną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CommonPoolReuseAcrossTasks {
        /*
         * 🧪 Zadanie 20:
         * Używając WYŁĄCZNIE ForkJoinPool.commonPool() (bez tworzenia własnego
         * poola), uruchom po kolei 3 różne RecursiveTask (sumowanie, znajdowanie
         * maksimum, zliczanie parzystych) na tej samej tablicy long[10000].
         * Wypisz wyniki wszystkich trzech i potwierdź, że commonPool() nie
         * wymaga jawnego zamykania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullParallelMergeSort {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj pełny równoległy merge sort jako RecursiveAction dla
         * int[10000] z losowymi wartościami: rekurencyjny podział z progiem
         * = 1000 (poniżej progu Arrays.sort() jako baza), a powyżej progu
         * własna faza scalania (merge) do tablicy pomocniczej. Zweryfikuj
         * poprawność (porównanie z Arrays.sort() na kopii) i wypisz czas
         * wykonania w porównaniu do sekwencyjnego Arrays.sort().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BoundedParallelQuicksortLikePartition {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj RecursiveAction realizujące sortowanie w stylu quicksort
         * (partycjonowanie względem pivota) dla int[8000], z progiem
         * wielkości fragmentu = 500 (poniżej progu: Arrays.sort() jako baza,
         * BEZ dalszego rekurencyjnego partycjonowania – żeby uniknąć
         * nieograniczonej rekursji przy skrajnie niesymetrycznych podziałach).
         * Zweryfikuj poprawność wyniku i wypisz czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ParallelWordFrequencyMap {
        /*
         * 🧪 Zadanie 23:
         * Utwórz String[2000] wygenerowanych losowo słów z małego słownika
         * (np. 20 możliwych słów). Zaimplementuj RecursiveTask<Map<String,Integer>>
         * liczące częstość każdego słowa, dzieląc zakres z progiem = 200 i
         * SCALAJĄC dwie mapy podzadań (sumując liczniki dla wspólnych kluczy).
         * Wypisz top 5 najczęstszych słów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ParallelPixelClamp {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj obraz jako int[200][200] z losowymi wartościami "pikseli"
         * (0-300). Zaimplementuj RecursiveAction, które W MIEJSCU przycina
         * (clamp) każdą wartość do zakresu 0-255, dzieląc pracę po wierszach
         * z progiem = 20 wierszy. Zweryfikuj (np. przez policzenie wartości
         * spoza zakresu przed i po) poprawność działania i wypisz czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NumericIntegrationApproximation {
        /*
         * 🧪 Zadanie 25:
         * Oblicz przybliżoną wartość całki funkcji f(x) = x*x na przedziale
         * [0, 10] metodą sum prostokątów dla 1 000 000 próbek, jako RecursiveTask
         * <Double> dzielącą zakres próbek z progiem = 50 000. Wypisz wynik
         * i porównaj z wartością analityczną (x^3/3 w granicach 0..10 = 333.33...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ParallelPrimeCounting {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj RecursiveTask<Long> liczące liczby pierwsze w przedziale
         * [2, 200000] metodą prostego sprawdzania podzielności, dzieląc PRZEDZIAŁ
         * (nie listę) z progiem = 10 000. Uruchom raz na ForkJoinPool(1) i raz na
         * ForkJoinPool(Runtime.getRuntime().availableProcessors()), zmierz i
         * porównaj czasy wykonania oraz zweryfikuj identyczny wynik liczbowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GenericParallelReduceUtility {
        /*
         * 🧪 Zadanie 27:
         * Napisz generyczną klasę ParallelReduce<T> rozszerzającą RecursiveTask<T>,
         * przyjmującą tablicę T[], wartość identity, BinaryOperator<T> combiner
         * oraz próg (threshold). Użyj jej trzykrotnie na tej samej tablicy
         * Integer[5000] z trzema różnymi operatorami (suma, min, max) i wypisz
         * wszystkie trzy wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BoundedFibonacciListComputation {
        /*
         * 🧪 Zadanie 28:
         * UWAGA: naiwna rekurencyjna implementacja fib(n) przez ForkJoinPool
         * dla dużych n eksploduje liczbą zadań – tego unikamy. Zamiast tego
         * utwórz tablicę int[30] indeksów 0..29 i policz RÓWNOLEGLE (RecursiveTask
         * <long[]>, próg = 5) wartość Fibonacciego dla każdego indeksu metodą
         * ITERACYJNĄ (pętla) wewnątrz przypadku bazowego – dzielenie dotyczy
         * TABLICY INDEKSÓW, nie głębokości rekursji fib(). Wypisz wynikową tablicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkSequentialForkJoinParallelStream {
        /*
         * 🧪 Zadanie 29:
         * Utwórz long[5_000_000] z wartościami 1..5000000. Zmierz i porównaj
         * czas obliczenia sumy trzema metodami: (1) zwykła sekwencyjna pętla,
         * (2) RecursiveTask przez własny ForkJoinPool (próg = 50 000),
         * (3) Arrays.stream(array).parallel().sum(). Wypisz tabelę czasów
         * i potwierdź, że wszystkie trzy metody dają identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullClampThenSumPipeline {
        /*
         * 🧪 Zadanie 30:
         * Utwórz int[100000] z losowymi wartościami (dodatnimi i ujemnymi).
         * Zbuduj pełny potok: (1) RecursiveAction zamieniające ujemne wartości
         * na 0 W MIEJSCU (próg = 5000), (2) RecursiveTask<Long> liczące sumę
         * "wyczyszczonej" tablicy (próg = 5000), oba uruchomione na WŁASNYM
         * ForkJoinPool z jawnym shutdown()+awaitTermination() na końcu. Wypisz
         * pełny raport: czas etapu 1, czas etapu 2, finalną sumę.
         */
        public static void main(String[] args) { }
    }
}
