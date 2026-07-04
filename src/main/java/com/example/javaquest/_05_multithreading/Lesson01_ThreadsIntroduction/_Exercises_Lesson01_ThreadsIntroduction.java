package com.example.javaquest._05_multithreading.Lesson01_ThreadsIntroduction;

public class _Exercises_Lesson01_ThreadsIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MainThreadName {
        /*
         * 🧪 Zadanie 1:
         * Wypisz nazwę bieżącego wątku przez Thread.currentThread().getName()
         * i sprawdź (println z porównaniem equals), czy jest równa "main".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListAllJvmThreads {
        /*
         * 🧪 Zadanie 2:
         * Użyj ManagementFactory.getThreadMXBean() i getAllThreadIds(), by wypisać
         * nazwy wszystkich wątków aktualnie działających w JVM oraz ich łączną liczbę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AvailableProcessors {
        /*
         * 🧪 Zadanie 3:
         * Wypisz liczbę dostępnych rdzeni (Runtime.getRuntime().availableProcessors())
         * i porównaj ją (println) z liczbą wątków JVM policzoną w Zadaniu 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimpleThreadHello {
        /*
         * 🧪 Zadanie 4:
         * Utwórz Thread z lambdą wypisującą "Witam ze watku!" wraz z nazwą bieżącego
         * wątku (Thread.currentThread().getName()). Uruchom go (start()) i poczekaj
         * na zakończenie (join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NamedThread {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek z nazwą "Watek-Zadaniowy" (konstruktor Thread(Runnable, String)).
         * Wypisz jego getName() przed startem, uruchom (start()+join()) i wypisz nazwę
         * jeszcze raz wewnątrz run().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoThreadsSequentialNames {
        /*
         * 🧪 Zadanie 6:
         * Utwórz dwa wątki o nazwach "A" i "B", każdy wypisuje swoją nazwę. Uruchom
         * je PO KOLEI, wywołując join() zaraz po starcie każdego, tak by kolejność
         * wypisów była deterministyczna (najpierw A, potem B).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ThreadCountBeforeAfter {
        /*
         * 🧪 Zadanie 7:
         * Policz liczbę wątków w JVM (ThreadMXBean.getThreadCount()) PRZED utworzeniem
         * dodatkowego wątku roboczego (sleep 200ms), tuż PO jego starcie oraz PO join().
         * Wypisz wszystkie trzy wartości i zaobserwuj, że po zakończeniu liczba wraca
         * do stanu sprzed utworzenia wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SequentialTasksTiming {
        /*
         * 🧪 Zadanie 8:
         * Zmierz czas wykonania dwóch zadań Thread.sleep(100) wykonanych SEKWENCYJNIE,
         * jedno po drugim, w wątku main (bez tworzenia dodatkowych wątków). Wypisz
         * zmierzony czas (oczekiwane ok. 200 ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConcurrentTasksTiming {
        /*
         * 🧪 Zadanie 9:
         * Te same dwa zadania (sleep(100)) uruchom teraz w DWÓCH osobnych wątkach
         * uruchomionych NARAZ (start obu, potem join obu). Zmierz czas (oczekiwane
         * ok. 100 ms) i porównaj (println) z wynikiem z Zadania 8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ThreadNameFiltering {
        /*
         * 🧪 Zadanie 10:
         * Z listy wątków JVM (ThreadMXBean) wypisz osobno: wątki, których nazwa
         * zawiera "main", oraz resztę (np. GC, Reference Handler itp.) – proste
         * filtrowanie po fragmencie nazwy (String.contains).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_IoBoundSimulation {
        /*
         * 🧪 Zadanie 11:
         * Zasymuluj 3 "zapytania sieciowe" (Thread.sleep(150) każde) wykonane
         * SEKWENCYJNIE i zmierz czas. Następnie wykonaj je WSPÓŁBIEŻNIE (3 osobne
         * wątki) i również zmierz czas. Wypisz oba czasy oraz przyspieszenie
         * (speedup = czas sekwencyjny / czas współbieżny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CpuBoundSumComparison {
        /*
         * 🧪 Zadanie 12:
         * Policz sumę liczb od 1 do 50_000_000 w jednym wątku (main) i zmierz czas.
         * Następnie podziel ten zakres na 4 równe części, policz każdą w osobnym
         * wątku (wynik cząstkowy zapisany do własnego indeksu tablicy long[4]),
         * zsumuj wyniki po joinach i zmierz czas. Porównaj oba czasy i skomentuj
         * (println), czy przyspieszenie zależy od availableProcessors().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreadNameReport {
        /*
         * 🧪 Zadanie 13:
         * Utwórz 5 wątków o nazwach "Watek-1".."Watek-5", każdy zasypia inną, z góry
         * ustaloną liczbę milisekund (np. tablica {50, 200, 100, 150, 80}). Poczekaj
         * na wszystkie (join), a następnie wypisz nazwę i czas snu każdego wątku
         * W KOLEJNOŚCI ICH INDEKSU (nie kolejności faktycznego zakończenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureThreadCreationOverhead {
        /*
         * 🧪 Zadanie 14:
         * Zmierz czas utworzenia i uruchomienia (start()+join()) 1000 wątków, z
         * których każdy wykonuje pustą lambdę. Wypisz łączny czas oraz średni czas
         * przypadający na jeden wątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CountActiveThreadsDuringWork {
        /*
         * 🧪 Zadanie 15:
         * Sprawdź Thread.activeCount() PRZED uruchomieniem dodatkowego wątku
         * wykonującego dłuższą pracę (pętla licząca sumę do 500_000_000), a następnie
         * ponownie sprawdź Thread.activeCount() krótko po jego starcie (po sleep(20)
         * w main). Wypisz obie wartości i porównaj je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProcessesInfoViaRuntime {
        /*
         * 🧪 Zadanie 16:
         * Wypisz krótki raport na podstawie klasy Runtime: availableProcessors(),
         * freeMemory(), totalMemory() oraz maxMemory() (wszystkie w bajtach lub
         * przeliczone na MB).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThreadGroupNamesSummary {
        /*
         * 🧪 Zadanie 17:
         * Pobierz listę nazw wątków JVM (ThreadMXBean) i pogrupuj je (np. do
         * Map<String, Integer>) według pierwszego "słowa" nazwy (fragment przed
         * pierwszą spacją lub myślnikiem). Wypisz liczność każdej grupy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SequentialVsConcurrentSpeedupTable {
        /*
         * 🧪 Zadanie 18:
         * Dla rosnącej liczby "zadań" (2, 4, 8), każde symulowane jako
         * Thread.sleep(100), zbuduj tabelę: liczba zadań, czas sekwencyjny, czas
         * współbieżny (osobne wątki uruchamiane naraz), przyspieszenie. Wypisz
         * całą tabelę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MainThreadIsNotAlone {
        /*
         * 🧪 Zadanie 19:
         * Za pomocą ThreadMXBean.getThreadCount() sprawdź, że nawet zaraz po
         * wejściu do main() liczba wątków w JVM jest większa niż 1. Wypisz
         * komunikat potwierdzający to założenie z teorii lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CpuVsIoBoundDecisionHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę recommendThreading(String taskType, int coresAvailable)
         * zwracającą String z rekomendacją: dla taskType "IO" – "watki pomagaja
         * nawet na 1 rdzeniu", dla "CPU" – "watki pomagaja tylko przy wielu
         * rdzeniach". Wywołaj ją dla obu typów, przekazując availableProcessors().
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_KitchenSimulationThreeDishes {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj przykład z lekcji (kucharz gotujący dania) na 3 dania o różnym
         * czasie trwania (sleep 100ms, 150ms, 200ms). Policz czas wykonania
         * SEKWENCYJNEGO (jedno po drugim) oraz WSPÓŁBIEŻNEGO (3 wątki naraz) i
         * wypisz oba, wraz z informacją, które danie skończyło się jako ostatnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ThreadPerTaskVsBatch {
        /*
         * 🧪 Zadanie 22:
         * Dla 20 "zadań" (sleep(20) każde) porównaj: a) uruchomienie w 20 osobnych
         * wątkach naraz, b) wykonanie sekwencyjne w jednym wątku (main). Wypisz oba
         * zmierzone czasy i skomentuj (println) narzut tworzenia wątków dla bardzo
         * krótkich zadań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_JvmThreadSnapshotDiff {
        /*
         * 🧪 Zadanie 23:
         * Zrób "migawkę" nazw wątków JVM PRZED uruchomieniem 5 dodatkowych wątków
         * roboczych (każdy sleep(300)) oraz PO ich zakończeniu (join wszystkich).
         * Porównaj obie listy (powinny być identyczne po zakończeniu) i wypisz
         * różnicę zaobserwowaną W TRAKCIE ich działania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SpeedupVsCoresReport {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas współbieżnego wykonania liczby zadań (sleep 100ms) równej
         * dokładnie availableProcessors() oraz podwojonej tej liczby. Zbuduj raport
         * pokazujący, jak przyspieszenie zależy od liczby zadań względem liczby
         * dostępnych rdzeni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CpuBoundParallelSum {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj równoległe liczenie sumy tablicy 10_000_000 liczb losowych
         * (int[], Random z ustalonym seedem), dzieląc pracę na availableProcessors()
         * wątków – każdy liczy sumę częściową do własnego pola tablicy wyników.
         * Zsumuj wyniki po joinach i porównaj z sumą liczoną sekwencyjnie (muszą
         * być równe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ThreadNameHistogramLive {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 6 wątków roboczych o powtarzających się prefiksach nazw (np.
         * "Worker-A-1", "Worker-A-2", "Worker-B-1"...), każdy śpiący 200ms. W main,
         * w pętli co 50ms (2-3 iteracje, z bezpiecznikiem), odczytaj listę wątków
         * JVM (ThreadMXBean) i zbuduj histogram prefiksów aktywnych w danym
         * momencie wątków roboczych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrencyVsParallelismDemo {
        /*
         * 🧪 Zadanie 27:
         * Uruchom 8 "zadań obliczeniowych" (krótka pętla licząca sumę do 10_000_000)
         * jako 8 wątków. Zmierz czas całkowity, policz "teoretyczny czas przy pełnej
         * równoległości" (czas jednego takiego zadania) i porównaj z
         * availableProcessors(), wypisując wniosek (println), czy system osiągnął
         * pełną równoległość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GracefulTimingReportBuilder {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę buildTimingReport(int taskCount, int taskDurationMs)
         * zwracającą String z raportem (czas sekwencyjny, czas współbieżny,
         * przyspieszenie, liczba dostępnych rdzeni) dla podanej liczby zadań
         * (symulowanych sleep). Wywołaj ją dla kombinacji: 2/100, 4/100, 8/50.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ThreadLifecycleCountsSnapshot {
        /*
         * 🧪 Zadanie 29:
         * Utwórz 10 wątków (każdy: sleep(100)). Wystartuj tylko pierwsze 5 z nich
         * i poczekaj (join), aż się zakończą. Wypisz, ile wątków zostało
         * uruchomionych, a ile nigdy nie wystartowało (na podstawie własnej listy,
         * bez używania Thread.State – to poznamy w kolejnej lekcji). Następnie
         * wystartuj pozostałe 5 i poczekaj na wszystkie (join z timeoutem 2000ms),
         * potwierdzając komunikatem, że wszystkie zadania się zakończyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullConcurrencyExperimentReport {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji: zbuduj eksperyment porównujący sekwencyjne
         * i współbieżne wykonanie N = availableProcessors() * 2 zadań o mieszanym
         * charakterze (połowa "IO" – sleep(100), połowa "CPU" – pętla licząca sumę
         * do 20_000_000). Zmierz łączne czasy dla obu podejść i wypisz końcowy
         * raport z wnioskiem, które zadania (IO czy CPU) zyskały więcej na
         * współbieżności.
         */
        public static void main(String[] args) { }
    }
}
