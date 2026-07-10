package com.example.javaquest._15_jvm_internals.Lesson06_HeapStackMetaspace;

public class _Exercises_Lesson06_HeapStackMetaspace {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainHeapVsStackVsMetaspace {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * czym rozni sie HEAP, STACK watku i METASPACE - co kazdy z nich
         * przechowuje i kto nim "zarzadza" (GC czy sam watek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PrintHeapMemoryUsage {
        /*
         * 🧪 Zadanie 2:
         * Uzyskaj MemoryMXBean przez ManagementFactory.getMemoryMXBean() i
         * wypisz init/used/committed/max dla getHeapMemoryUsage() w bajtach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PrintNonHeapMemoryUsage {
        /*
         * 🧪 Zadanie 3:
         * Analogicznie do Zadania 2, ale dla getNonHeapMemoryUsage() -
         * wypisz init/used/committed/max.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CountMemoryPools {
        /*
         * 🧪 Zadanie 4:
         * Pobierz ManagementFactory.getMemoryPoolMXBeans() i wypisz TYLKO
         * liczbe pul pamieci zwroconych przez Twoja JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListAllMemoryPoolNames {
        /*
         * 🧪 Zadanie 5:
         * Wypisz nazwe KAZDEJ puli pamieci (MemoryPoolMXBean.getName()) w
         * osobnej linii, poprzedzona myslnikiem "- ".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FilterHeapPoolsOnly {
        /*
         * 🧪 Zadanie 6:
         * Z listy pul pamieci wypisz TYLKO te typu HEAP (MemoryType.HEAP) -
         * pomin pule NON_HEAP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TriggerAndCatchStackOverflow {
        /*
         * 🧪 Zadanie 7:
         * Napisz wlasna, prywatna metode bezargumentowa wywolujaca sama
         * siebie bez warunku stopu, wywolaj ja w bloku try-catch i zlap
         * StackOverflowError - wypisz komunikat "Stos przepelniony!".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountRecursionDepthBeforeOverflow {
        /*
         * 🧪 Zadanie 8:
         * Rozszerz metode z Zadania 7 o statyczny licznik glebokosci
         * (inkrementowany przy kazdym wywolaniu) - po zlapaniu
         * StackOverflowError wypisz osiagnieta glebokosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainXssFlag {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wyjasnij, do czego sluzy flaga -Xss
         * i jaki bedzie skutek jej ZMNIEJSZENIA (np. do -Xss128k) dla
         * rekurencyjnego kodu z Zadania 7/8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainMaxMetaspaceSizeFlag {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wyjasnij, czym rozni sie Metaspace od
         * starego PermGen (Java < 8) i co sie stanie, gdy przekroczysz limit
         * ustawiony przez -XX:MaxMetaspaceSize.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FormatMemoryUsageAsPercentage {
        /*
         * 🧪 Zadanie 11:
         * Dla getHeapMemoryUsage() policz i wypisz procent zajetosci
         * (used / committed * 100), sformatowany do 2 miejsc po przecinku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FindPoolWithHighestUsage {
        /*
         * 🧪 Zadanie 12:
         * Przejdz przez wszystkie MemoryPoolMXBean i znajdz TA pule, ktora ma
         * najwyzsza wartosc getUsage().getUsed() - wypisz jej nazwe i wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareInitVsMaxForEachPool {
        /*
         * 🧪 Zadanie 13:
         * Dla kazdej puli pamieci wypisz roznice miedzy max a init (jesli max
         * != -1) - pokazuje to, o ile pula MOZE jeszcze urosnac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureStackOverflowDepthMultipleRuns {
        /*
         * 🧪 Zadanie 14:
         * Uruchom demonstracje StackOverflowError z Zadania 8 TRZY razy pod
         * rzad (w petli) i wypisz osiagniete glebokosci - w komentarzu
         * skomentuj, czy sa identyczne czy sie roznia i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteMethodWithLargeStackFrame {
        /*
         * 🧪 Zadanie 15:
         * Napisz rekurencyjna metode z DUZA liczba lokalnych zmiennych typu
         * long (np. 20 zmiennych) i porownaj osiagnieta glebokosc
         * StackOverflowError z metoda z Zadania 7 (bez dodatkowych zmiennych) -
         * w komentarzu wyjasnij zaobserwowana roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SimulateDeepValidCallChain {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode rekurencyjna liczaca sume liczb od 1 do n (np.
         * n=10000) POPRAWNIE (z warunkiem stopu) - sprawdz, czy dla podanego n
         * dochodzi do StackOverflowError, czy nie, i wypisz wynik lub komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PrintMemoryUsageBeforeAndAfterAllocation {
        /*
         * 🧪 Zadanie 17:
         * Wypisz used z getHeapMemoryUsage(), zaalokuj tablice 10_000_000
         * elementow typu int, wypisz used ponownie - w komentarzu opisz
         * zaobserwowana roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ClassifyPoolsByType {
        /*
         * 🧪 Zadanie 18:
         * Zlicz i wypisz, ile pul pamieci jest typu HEAP, a ile NON_HEAP w
         * Twojej JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhyEachThreadHasOwnStack {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: w komentarzu wyjasnij, dlaczego kazdy watek MUSI
         * miec wlasny stos (odwolaj sie do rozdzialu _05_multithreading -
         *  co by sie stalo, gdyby watki dzielily jeden wspolny stos).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildMemoryReportUtility {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode `static void printMemoryReport()`, ktora wypisuje w
         * jednym czytelnym bloku: heap used/max, non-heap used/max oraz
         * liczbe pul pamieci - wywolaj ja raz.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MonitorHeapUsageOverBoundedLoop {
        /*
         * 🧪 Zadanie 21:
         * W petli 5 iteracji: zaalokuj 1_000_000 malych obiektow (np. tablic
         * byte[16]) i za kazdym razem wypisz aktualne used z
         * getHeapMemoryUsage() - zaobserwuj trend rosnacy (przed ewentualnym GC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildRecursiveDepthProbe {
        /*
         * 🧪 Zadanie 22:
         * Napisz narzedzie diagnostyczne: metode `static long probeMaxDepth()`,
         * ktora rekurencyjnie wywoluje sama siebie, liczy glebokosc, lapie
         * StackOverflowError i ZWRACA (nie tylko wypisuje) osiagnieta wartosc -
         * wywolaj ja 2 razy i porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareShallowVsDeepStackFrameMethods {
        /*
         * 🧪 Zadanie 23:
         * Napisz DWIE rekurencyjne metody - jedna bez lokalnych zmiennych,
         * druga z tablica lokalna np. `long[] buffer = new long[50];` -
         * zmierz i porownaj osiagnieta glebokosc rekurencji dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DetectApproximateMetaspaceUsage {
        /*
         * 🧪 Zadanie 24:
         * Znajdz w getMemoryPoolMXBeans() pule o nazwie zawierajacej "Metaspace"
         * i wypisz jej used/committed/max - jesli nie znaleziono, wypisz
         * czytelny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SimulateManyClassesToGrowMetaspace {
        /*
         * 🧪 Zadanie 25:
         * Uzywajac znanego z Lesson04_CustomClassLoaders mechanizmu (lub
         * java.lang.reflect.Proxy), zaladuj/wygeneruj kilkadziesiat roznych
         * klas w petli i porownaj used Metaspace PRZED i PO - skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildThreadedStackDepthComparison {
        /*
         * 🧪 Zadanie 26:
         * Uruchom probe glebokosci rekurencji (jak w Zadaniu 22) w GLOWNYM
         * watku oraz w osobnym watku utworzonym z jawnie ustawionym rozmiarem
         * stosu (konstruktor `new Thread(ThreadGroup, Runnable, String, long
         * stackSize)`) - porownaj osiagniete glebokosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildMemoryPoolSnapshotDiffTool {
        /*
         * 🧪 Zadanie 27:
         * Napisz narzedzie, ktore robi "migawke" (snapshot) used wszystkich
         * pul pamieci PRZED i PO wykonaniu przekazanego Runnable (np.
         * alokujacego duzo obiektow), a nastepnie wypisuje roznice dla
         * kazdej puli, w ktorej used sie zmienilo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainStackOverflowInRealAlgorithms {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: opisz (min. 5 zdan) realny scenariusz z praktyki
         * (np. rekurencyjne przetwarzanie drzewa kategorii w sklepie
         * internetowym), w ktorym StackOverflowError moze wystapic na
         * produkcji, i zaproponuj 2 sposoby uniknięcia problemu (np. wersja
         * iteracyjna, limit glebokosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConvertRecursiveToIterativeAndCompare {
        /*
         * 🧪 Zadanie 29:
         * Wez metode rekurencyjna z Zadania 16 (suma 1..n) i napisz jej
         * ITERACYJNY odpowiednik (petla) - porownaj dla duzego n (np.
         * 10_000_000), ktora wersja dziala bezpiecznie, a ktora rzuca
         * StackOverflowError.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullJvmMemoryDiagnosticReport {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz kompletne narzedzie diagnostyczne
         * laczace elementy z tej lekcji - wypisz heap/non-heap usage, liste
         * wszystkich pul z procentowa zajetoscia (used/max), oraz wynik
         * proby glebokiej rekurencji (zlapany StackOverflowError z
         * glebokoscia) - calosc jako jeden czytelny raport tekstowy.
         */
        public static void main(String[] args) { }
    }
}
