package com.example.javaquest._15_jvm_internals.Lesson09_GarbageCollectorAlgorithms;

public class _Exercises_Lesson09_GarbageCollectorAlgorithms {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThroughputLatencyFootprintTradeoff {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) 3 sprzeczne cele kolektorow GC (throughput, latency,
         * footprint) i dlaczego nie da sie zoptymalizowac wszystkich naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListCollectorFlags {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wypisz w komentarzu flagi JVM wlaczajace kazdy z 5
         * kolektorow omowionych w lekcji (Serial, Parallel, G1, ZGC, Shenandoah).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhySerialIsSingleThreaded {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij, dlaczego Serial GC uzywa TYLKO JEDNEGO
         * watku i w jakim scenariuszu (np. maly kontener z 1 rdzeniem) to
         * NIE jest wada.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IdentifyDefaultCollectorSinceJava9 {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: napisz, ktory kolektor jest DOMYSLNY od Javy 9 i
         * jakiego kompromisu (throughput/latency/footprint) sie od niego
         * oczekuje jako "rozsadny wybor domyslny".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RunProgramWithExplicitSerialGcFlag {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac wzorca runProcess() z teorii lekcji, uruchom prosty
         * program (np. Twoja wlasna wersja GcDemoApp) z flaga
         * -XX:+UseSerialGC i -Xlog:gc - wypisz przechwycony output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RunProgramWithExplicitParallelGcFlag {
        /*
         * 🧪 Zadanie 6:
         * Analogicznie do Zadania 5, ale z flaga -XX:+UseParallelGC -
         * wypisz przechwycony output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunProgramWithExplicitG1GcFlag {
        /*
         * 🧪 Zadanie 7:
         * Analogicznie do Zadania 5, ale z flaga -XX:+UseG1GC - wypisz
         * przechwycony output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareOutputLineCounts {
        /*
         * 🧪 Zadanie 8:
         * Uruchom te sama demonstracje z 3 roznymi kolektorami (jak w
         * teorii) i policz, ile linii zawierajacych "GC" pojawilo sie w
         * kazdym z 3 przechwyconych outputow - wypisz porownanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhenToPreferParallelGc {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: opisz konkretny scenariusz produkcyjny (np.
         * nocny job ETL przetwarzajacy pliki), w ktorym Parallel GC bylby
         * lepszym wyborem niz G1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToPreferLowLatencyCollector {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: opisz konkretny scenariusz produkcyjny (np.
         * system transakcyjny z wymaganym SLA opoznien < 10ms), w ktorym
         * ZGC/Shenandoah bylyby lepszym wyborem niz Parallel GC.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildOwnGcDemoAppWithConfigurableAllocationCount {
        /*
         * 🧪 Zadanie 11:
         * Zmodyfikuj kod generujacy zrodlo GcDemoApp z teorii tak, zeby
         * liczba alokowanych obiektow byla parametrem (np. przekazanym
         * przez args w wygenerowanym programie) - uruchom z 2 roznymi
         * wartosciami i porownaj logi GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureWallClockTimePerCollector {
        /*
         * 🧪 Zadanie 12:
         * Zmierz (System.nanoTime() wokol runProcess()) calkowity czas
         * wykonania procesu potomnego dla kazdego z 3 kolektorow - wypisz
         * porownanie czasow w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExtractGcPauseCountFromLogOutput {
        /*
         * 🧪 Zadanie 13:
         * Z przechwyconego outputu -Xlog:gc dla KAZDEGO kolektora policz
         * (np. metoda String.lines().filter(...)) liczbe linii zawierajacych
         * slowo "Pause" - wypisz porownanie miedzy kolektorami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddHeapSizeFlagsAndObserveDifference {
        /*
         * 🧪 Zadanie 14:
         * Uruchom demonstracje z Lesson09 dodajac flagi -Xmx16m -Xms16m do
         * komendy (maly heap wymusza czestsze GC) - porownaj liczbe linii
         * logu z domyslnym rozmiarem sterty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhySourceFileLaunchWorks {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij (min. 3 zdania), czym jest "source-file
         * launch" (`java Plik.java` bez osobnej kompilacji) i od ktorej
         * wersji Javy jest dostepny (podpowiedz: JEP 330, Java 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareParallelGcWithExplicitThreadCount {
        /*
         * 🧪 Zadanie 16:
         * Uruchom demonstracje z Parallel GC dodajac flage
         * -XX:ParallelGCThreads=1 oraz osobno -XX:ParallelGCThreads=4 -
         * porownaj czas wykonania (System.nanoTime()) miedzy obiema wersjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildTableComparingThreeRunsSideBySide {
        /*
         * 🧪 Zadanie 17:
         * Uruchom wszystkie 3 kolektory z teorii i zbuduj tabele
         * (System.out.printf) z kolumnami: kolektor, czas wykonania,
         * liczba linii z "GC" w logu, kod wyjscia procesu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainG1DefaultChoiceRationale {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjasnij (min. 4 zdania), dlaczego G1 zostal
         * wybrany jako domyslny kolektor JVM zamiast Parallel (ktory byl
         * domyslny wczesniej, przed Java 9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestInvalidCollectorFlagCombination {
        /*
         * 🧪 Zadanie 19:
         * Sprobuj uruchomic proces potomny z JEDNOCZESNIE 2 sprzecznymi
         * flagami (np. -XX:+UseSerialGC -XX:+UseG1GC) - przechwyc i wypisz
         * output/blad JVM (kod wyjscia rozny od 0) zamiast zakladac sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentObservedLogFormatDifferences {
        /*
         * 🧪 Zadanie 20:
         * Po uruchomieniu wszystkich 3 kolektorow, w komentarzu (na
         * podstawie faktycznie zaobserwowanego outputu) opisz min. 2
         * konkretne roznice w formacie/tresci logu -Xlog:gc miedzy nimi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildParameterizedGcBenchmarkHarness {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj narzedzie (metode), ktore przyjmuje liste nazw flag
         * kolektorow (np. List<String>), uruchamia dla kazdej ten sam
         * wygenerowany program, mierzy czas i liczbe linii logu GC, po czym
         * wypisuje pelny raport porownawczy dla WSZYSTKICH przekazanych
         * kolektorow (nie tylko 3 z teorii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CorrelateHeapSizeWithGcFrequency {
        /*
         * 🧪 Zadanie 22:
         * Uruchom TEN SAM program (np. z G1) z 3 roznymi rozmiarami sterty
         * (-Xmx16m, -Xmx64m, -Xmx256m) - zmierz liczbe linii logu GC dla
         * kazdego rozmiaru i wypisz zaleznosc (mniejszy heap -> wiecej GC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildAllocationIntensiveWorkloadVariants {
        /*
         * 🧪 Zadanie 23:
         * Wygeneruj DWIE wersje programu potomnego: jedna alokujaca duzo
         * MALYCH krotko zyjacych obiektow, druga alokujaca MNIEJ, ale
         * WIEKSZYCH i DLUZEJ zyjacych (trzymanych w statycznej liscie) -
         * porownaj logi GC dla obu wariantow z tym samym kolektorem (G1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRetryLogicForFlakyChildProcess {
        /*
         * 🧪 Zadanie 24:
         * Rozszerz runProcess() o logike ponawiania (retry) - jesli proces
         * przekroczy limit czasu (timeout), sprobuj ponownie max 2 razy,
         * zanim ostatecznie zglosisz porazke - przetestuj na normalnym,
         * szybko konczacym sie procesie (retry nie powinien byc potrzebny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildGcLogParserExtractingPauseDurations {
        /*
         * 🧪 Zadanie 25:
         * Napisz prosty parser (np. wyrazenie regularne) wyciagajacy z
         * linii logu -Xlog:gc czas trwania pauzy (format zalezny od JDK,
         * np. "... 1.234ms") - zsumuj wyciagniete czasy dla kazdego
         * kolektora i porownaj laczny czas pauz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCollectorsUnderMemoryPressure {
        /*
         * 🧪 Zadanie 26:
         * Uruchom kazdy z 3 kolektorow z BARDZO malym heapem (-Xmx8m) i
         * wieksza liczba alokacji (np. 1_000_000 obiektow) - zaobserwuj i
         * opisz w komentarzu, czy ktorys z procesow zakonczyl sie
         * OutOfMemoryError (kod wyjscia != 0) i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignDecisionMatrixForRealApplication {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wybierz KONKRETNY typ aplikacji z wczesniejszych
         * rozdzialow kursu (np. serwer servletowy z _07_servlets, DAO z
         * _10_dao) i napisz uzasadnienie (min. 5 zdan), ktory kolektor GC
         * bylby dla niej najlepszym wyborem domyslnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildMultiRunStatisticalComparison {
        /*
         * 🧪 Zadanie 28:
         * Uruchom KAZDY z 3 kolektorow 3 RAZY (9 uruchomien lacznie,
         * wszystkie z ograniczonym timeoutem) - zbierz czasy wykonania i
         * wypisz srednia oraz odchylenie (min-max) dla kazdego kolektora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExplainTradeoffsInWrittenReportFormat {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz krotki dokument decyzyjny (min. 8 zdan) w
         * formie "ADR" (Architecture Decision Record) uzasadniajacy wybor
         * kolektora GC dla fikcyjnej aplikacji e-commerce z duzym ruchem w
         * godzinach szczytu - uwzglednij throughput, latency i footprint.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullGcAlgorithmComparisonReport {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz kompletne narzedzie, ktore
         * uruchamia ten sam wygenerowany program z 3 kolektorami (Serial/
         * Parallel/G1), mierzy czas wykonania i liczbe linii logu GC dla
         * kazdego, a nastepnie wypisuje JEDEN spojny raport koncowy w
         * formie tabeli z rekomendacja (na podstawie zebranych danych,
         * nie tylko teorii), ktory kolektor "wygral" w tym konkretnym
         * bounded, krotkim scenariuszu testowym.
         */
        public static void main(String[] args) { }
    }
}
