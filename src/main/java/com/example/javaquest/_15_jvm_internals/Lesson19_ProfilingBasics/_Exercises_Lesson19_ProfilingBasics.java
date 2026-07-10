package com.example.javaquest._15_jvm_internals.Lesson19_ProfilingBasics;

public class _Exercises_Lesson19_ProfilingBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSamplingVsInstrumentation {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjaśnij własnymi
         * słowami różnicę między sampling profilingiem a instrumentacją,
         * odwołując się do narzutu wydajnościowego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_SingleStackTraceSnapshot {
        /*
         * 🧪 Zadanie 2:
         * Uruchom wątek roboczy wykonujący prostą pętlę obliczeniową przez
         * 1 sekundę. Pobierz JEDEN snapshot jego stack trace przez
         * Thread.getAllStackTraces() w połowie działania i wypisz go w
         * całości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintTopFrameOnly {
        /*
         * 🧪 Zadanie 3:
         * Powtórz Zadanie 2, ale wypisz TYLKO element o indeksie 0
         * (aktualnie wykonywana metoda) z tablicy StackTraceElement[].
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_CountSamplesOverFixedWindow {
        /*
         * 🧪 Zadanie 4:
         * Zbierz próbki co 50ms przez dokładnie 1 sekundę (bounded pętla) z
         * wątku roboczego, licząc TYLKO ile próbek udało się zebrać (bez
         * jeszcze klasyfikowania metod). Wypisz łączną liczbę próbek.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_BuildKeyFromClassAndMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę `String frameKey(StackTraceElement element)`
         * zwracającą "NazwaKlasy.nazwaMetody". Przetestuj ją na kilku
         * przykładowych elementach pobranych z realnego stack trace.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_SingleMethodHotLoop {
        /*
         * 🧪 Zadanie 6:
         * Uruchom wątek roboczy wykonujący TYLKO jedną metodę w pętli przez
         * 1 sekundę (bez wywołań zagnieżdżonych), próbkuj co 30ms i
         * zweryfikuj, że 100% próbek wskazuje na tę samą metodę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_TwoMethodsAlternating {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj wątek roboczy naprzemiennie wywołujący dwie metody (A przez
         * 500ms, potem B przez 500ms). Próbkuj co 30ms przez cały czas i
         * wypisz ranking - powinny być widoczne w przybliżeniu 2 grupy
         * próbek.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_HashMapCounterBasics {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj HashMap<String, Integer> i użyj merge(key, 1,
         * Integer::sum) do zliczenia 10 ręcznie wpisanych (bez próbkowania)
         * przykładowych kluczy metod, tak żeby przetestować mechanikę
         * zliczania przed użyciem jej w prawdziwym profilerze.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_SortRankingDescending {
        /*
         * 🧪 Zadanie 9:
         * Mając gotową mapę Map<String, Integer> z 5 przykładowymi wpisami
         * (ręcznie utworzoną), posortuj ją malejąco po wartości i wypisz
         * jako listę "miejsce. klucz -> liczba".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_BoundedSamplingLoopSafety {
        /*
         * 🧪 Zadanie 10:
         * Napisz pętlę próbkującą z DWOMA warunkami bezpieczeństwa:
         * worker.isAlive() ORAZ twardy limit czasu (np. 3 sekundy). Udowodnij
         * (przez pomiar czasu przed/po), że pętla kończy się w granicach
         * limitu nawet gdyby wątek roboczy działał dłużej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeLevelCallChainRanking {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz z lekcji łańcuch methodA->methodB->methodC (methodC robi
         * realną pracę CPU), spróbkuj przez 1.5 sekundy i wypisz PEŁNY
         * ranking wszystkich 3 metod z procentowym udziałem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_VaryingSamplingInterval {
        /*
         * 🧪 Zadanie 12:
         * Uruchom TO SAMO obciążenie (1.5s) trzy razy z różnym interwałem
         * próbkowania (10ms, 50ms, 200ms) i porównaj (println) liczbę
         * zebranych próbek oraz to, czy ranking najgorętszej metody
         * pozostaje ten sam mimo różnej rozdzielczości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_MultipleWorkerThreadsSampledTogether {
        /*
         * 🧪 Zadanie 13:
         * Uruchom 3 wątki robocze wykonujące RÓŻNE metody, próbkuj
         * WSZYSTKIE naraz (iterując po całej mapie z
         * Thread.getAllStackTraces(), nie tylko po jednym wątku) i wypisz
         * osobny ranking dla każdego wątku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_TopNFramesInsteadOfJustTop {
        /*
         * 🧪 Zadanie 14:
         * Zamiast zliczać tylko element [0], zliczaj TOP 3 klatki stosu
         * (indeksy 0, 1, 2) dla każdej próbki z osobnymi licznikami. Wypisz
         * 3 osobne rankingi - jeden na każdą "głębokość".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_DetectIdleVsBusyPeriods {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj wątek roboczy, który naprzemiennie "pracuje" (pętla CPU)
         * przez 300ms i "odpoczywa" (Thread.sleep) przez 300ms, powtórz 3
         * razy. Podczas próbkowania rozróżnij (po stanie Thread.State w
         * ThreadInfo lub po stack trace) okresy pracy od okresów bezczynności.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_PercentageThresholdHighlighting {
        /*
         * 🧪 Zadanie 16:
         * Rozszerz ranking z lekcji: dowolna metoda odpowiadająca za więcej
         * niż 50% próbek powinna być oznaczona w wypisywanym raporcie
         * dopiskiem "[GORĄCY PUNKT]".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CompareTwoImplementationsPerformance {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj dwie wersje tej samej funkcji (np. sumowanie
         * kwadratów - wersja "naiwna" i wersja z drobną optymalizacją).
         * Sprofiluj obie osobno (po 1 sekundzie każda) i porównaj (println),
         * która zbiera więcej próbek w danej metodzie względem czasu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_SamplingOverheadMeasurement {
        /*
         * 🧪 Zadanie 18:
         * Zmierz czas wykonania tego samego obciążenia (2 000 000 iteracji)
         * z aktywnym próbkowaniem (co 10ms) i bez próbkowania. Wypisz oba
         * czasy i skomentuj (println), czy sampling profiling faktycznie ma
         * niski narzut w Twoim pomiarze.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_ExportRankingToCsvFile {
        /*
         * 🧪 Zadanie 19:
         * Po zebraniu rankingu metod, zapisz go jako plik CSV
         * ("metoda,liczbaProbek,procent") do katalogu tymczasowego
         * (nawiązanie do CSV z _04_io/Lesson23_CSV). Wypisz ścieżkę pliku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_RecursiveMethodProfiling {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj metodę rekurencyjną (np. liczącą silnie/Fibonacciego bez
         * memoizacji, o umiarkowanej głębokości) wywoływaną w pętli przez
         * 1 sekundę. Sprofiluj ją i wypisz, czy w próbkach widoczna jest
         * GŁĘBOKOŚĆ rekurencji (długość stack trace w danej próbce).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FlameGraphStyleTextReport {
        /*
         * 🧪 Zadanie 21:
         * Zamiast liczyć tylko top klatkę, zlicz CAŁE ŚCIEŻKI wywołań (np.
         * "methodA;methodB;methodC" łączone średnikiem, konwencja formatu
         * "collapsed stacks" używanego przez narzędzia do flame graphów).
         * Wypisz ranking pełnych ścieżek zamiast pojedynczych metod.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_AdaptiveSamplingRate {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj profiler, który PRZYSPIESZA próbkowanie (z 100ms do
         * 20ms) w momencie, gdy wykryje zmianę "gorącej" metody między
         * kolejnymi próbkami (sygnał potencjalnie interesującego fragmentu),
         * a zwalnia z powrotem, gdy metoda jest stabilna przez 5 próbek.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_CompareSamplingAgainstJfrMethodProfiling {
        /*
         * 🧪 Zadanie 23:
         * Sprofiluj to samo obciążenie WŁASNYM sampling profilerem z tej
         * lekcji ORAZ (nawiązanie do Lesson18) nagraj je jednocześnie przez
         * jdk.jfr.Recording z włączonym "jdk.ExecutionSample" (jeśli
         * dostępne w Twojej JVM). Porównaj (println) czy oba wskazują na tę
         * samą "gorącą" metodę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_MultiThreadedContentionProfiling {
        /*
         * 🧪 Zadanie 24:
         * Uruchom 4 wątki robocze rywalizujące o WSPÓLNĄ blokadę
         * (synchronized) częstsze niż potrzeba (celowo zbyt gruboziarnisty
         * lock). Sprofiluj je i wykaż (przez zliczanie stanów BLOCKED w
         * próbkach ThreadInfo, nawiązanie do Lesson17), że znaczna część
         * czasu jest spędzana na oczekiwaniu, nie na realnej pracy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_ProfilerAsReusableUtilityClass {
        /*
         * 🧪 Zadanie 25:
         * Zamień kod z lekcji w reużywalną klasę `SamplingProfiler` z
         * metodami `start(Thread target)`, `stop()`, `printRanking()`.
         * Przetestuj ją na 2 różnych obciążeniach uruchamianych jedno po
         * drugim, używając tej samej instancji profilera (po reset między
         * użyciami).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_DetectPerformanceRegressionBetweenVersions {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj "wersję 1" i "wersję 2" tej samej funkcji, gdzie
         * wersja 2 jest CELOWO wolniejsza (np. dodatkowa niepotrzebna praca).
         * Sprofiluj obie i wypisz automatyczny werdykt "regresja wydajności
         * wykryta" na podstawie porównania liczby próbek/czasu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_SamplingDuringSimulatedGcPause {
        /*
         * 🧪 Zadanie 27:
         * Podczas profilowania wywołaj też okresowo System.gc() (co 300ms)
         * i zaobserwuj (przez brakujące/nietypowe próbki albo przez
         * MemoryMXBean, nawiązanie do Lesson06) wpływ GC na ciągłość
         * próbkowania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_ExportFlameGraphCompatibleFile {
        /*
         * 🧪 Zadanie 28:
         * Zapisz wynik z Zadania 21 (ścieżki wywołań + liczby) do pliku
         * tekstowego w dokładnym formacie "collapsed stacks"
         * (ścieżka<spacja>liczba, jedna linia na wpis) akceptowanym przez
         * popularne narzędzia do generowania flame graphów. Wypisz ścieżkę
         * pliku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CrossReferenceWithThreadDump {
        /*
         * 🧪 Zadanie 29:
         * Podczas profilowania wykonaj RÓWNIEŻ pełny thread dump
         * (ThreadMXBean.dumpAllThreads, nawiązanie do Lesson17) dokładnie w
         * połowie okna próbkowania. Zweryfikuj, że stack trace z thread
         * dumpu dla wątku roboczego pokrywa się z jedną z próbek zebranych
         * przez Twój profiler w tym samym momencie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullProfilingCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny raport: sprofiluj obciążenie z 3 metodami o
         * różnym "ciężarze" (np. 70%/20%/10% czasu), wygeneruj (1) ranking
         * tekstowy, (2) plik CSV (Zadanie 19), (3) plik w formacie collapsed
         * stacks (Zadanie 28), (4) krótkie podsumowanie tekstowe
         * wskazujące metodę do optymalizacji w pierwszej kolejności. Wypisz
         * ścieżki wszystkich wygenerowanych plików.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
