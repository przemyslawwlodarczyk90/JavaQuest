package com.example.javaquest._15_jvm_internals.Lesson11_LowLatencyCollectors;

public class _Exercises_Lesson11_LowLatencyCollectors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyPausesGrowWithHeapSize {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * klasyczne kolektory stop-the-world moga miec pauzy rosnace wraz
         * z rozmiarem sterty/zywego zbioru obiektow, i jaki jest cel ZGC
         * oraz Shenandoah w tej kwestii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListTwoLowLatencyCollectors {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wypisz w komentarzu nazwy DWOCH kolektorow niskich
         * pauz omowionych w lekcji oraz flagi JVM wlaczajace kazdy z nich
         * (dokladna skladnia -XX:+...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RunAllocatorDemoWithG1 {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac wzorca runProcess() z teorii lekcji, uruchom prosty
         * program alokujacy obiekty (mozesz skopiowac AllocatorDemo) z
         * domyslnym kolektorem G1 (bez zadnej dodatkowej flagi GC) oraz
         * flaga -Xlog:gc - wypisz kilka pierwszych linii logu GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainColoredPointersInOwnWords {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: opisz wlasnymi slowami (min. 3 zdania), czym sa
         * "colored pointers" w ZGC i do czego sluzy "load barrier" przy
         * kazdym odczycie referencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainBrooksPointerInOwnWords {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: opisz wlasnymi slowami (min. 3 zdania), czym jest
         * "Brooks pointer" (forwarding pointer) w Shenandoah i jak rozni
         * sie od podejscia ZGC (kolorowane bity wskaznika).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryToStartJvmWithUseZgc {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode uruchamiajaca (przez ProcessBuilder, z limitem
         * czasu 10 sekund tak jak w teorii) osobny proces "java -XX:+UseZGC
         * -version" - wypisz kod wyjscia i pierwsza linie polaczonego
         * wyjscia (stdout+stderr).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryToStartJvmWithUseShenandoahGc {
        /*
         * 🧪 Zadanie 7:
         * Analogicznie do Zadania 6, ale z flaga -XX:+UseShenandoahGC -
         * wypisz kod wyjscia i pierwsza linie wyjscia. Jesli kolektor
         * niedostepny, w komentarzu zanotuj tresc bledu JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListTradeoffsOfLowLatencyCollectors {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz w komentarzu 3 KOSZTY (tradeoffy) uzycia
         * ZGC/Shenandoah w porownaniu do G1, omowione w lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhenToPreferLowLatencyCollector {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: opisz (min. 3 zdania) konkretny, realistyczny
         * scenariusz aplikacji (np. system tradingowy, gra sieciowa), w
         * ktorym warto wybrac ZGC/Shenandoah zamiast domyslnego G1 - i
         * uzasadnij dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DetectUnsupportedOptionFromJvmOutput {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode boolean isUnsupportedOptionError(String jvmOutput),
         * ktora sprawdza, czy podany tekst (symulowane wyjscie JVM) zawiera
         * fraze "Unrecognized VM option" lub "is unsupported" (bez wzgledu
         * na wielkosc liter) - przetestuj na 2 przykladowych tekstach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompareGcLogVerbosityG1VsZgc {
        /*
         * 🧪 Zadanie 11:
         * Uruchom AllocatorDemo (lub podobny) dwukrotnie: raz z domyslnym
         * G1 i -Xlog:gc, raz z -XX:+UseZGC i -Xlog:gc (jesli dostepny) -
         * porownaj w komentarzu liczbe linii logu i format nazw faz GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureWallClockTimeOfAllocatorDemo {
        /*
         * 🧪 Zadanie 12:
         * Zmierz (System.nanoTime() wokol process.waitFor()) calkowity
         * czas dzialania AllocatorDemo z G1 oraz z ZGC (jesli dostepny) -
         * wypisz oba czasy w milisekundach i skomentuj, ze to NIE jest
         * miarodajny benchmark (za maly, za krotki program), tylko demo
         * mechanizmu pomiaru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteFriendlyFallbackMessage {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode printFriendlyFallback(String collectorName, String
         * jvmOutput), ktora wypisuje po polsku czytelny komunikat "Kolektor
         * X nie jest dostepny w tym buildzie JDK" wraz z pierwsza linia
         * bledu z jvmOutput - przetestuj na przykladowym tekscie bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RunAllocatorDemoWithLargerHeapChurn {
        /*
         * 🧪 Zadanie 14:
         * Zmodyfikuj (skopiowana) wersje AllocatorDemo tak, by alokowala
         * 5x wiecej danych (np. 100 rund zamiast 30) i uruchom ja z G1
         * oraz (jesli dostepny) ZGC - porownaj liczbe wpisow w logu GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainConcurrentVsStopTheWorldPhases {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij roznice miedzy faza WSPOLBIEZNA (concurrent
         * - dzieje sie rownolegle z aplikacja) a faza STOP-THE-WORLD w
         * kontekscie GC - podaj min. 1 przyklad kazdej z nich z tej lekcji
         * lub wczesniejszych lekcji rozdzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildCommandLineListProgrammatically {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode `static String[] buildJvmCommand(String javaExe,
         * String gcFlag, String mainClass, String classpath)`, ktora
         * sklada tablice argumentow do ProcessBuilder (javaExe, gcFlag,
         * "-Xlog:gc", "-cp", classpath, mainClass) - wypisz wynik dla
         * przykladowych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareExitCodesAcrossThreeCollectors {
        /*
         * 🧪 Zadanie 17:
         * Uruchom "java -XX:+UseG1GC -version", "java -XX:+UseZGC -version"
         * i "java -XX:+UseShenandoahGC -version" - wypisz w tabeli
         * (System.out.printf) nazwe kolektora i kod wyjscia kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainAddressSpaceOverheadOfColoredPointers {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego technika
         * colored pointers wymaga odpowiednio duzej, zarezerwowanej
         * przestrzeni adresowej (64-bitowej) i dlaczego nie dzialalaby na
         * systemach 32-bitowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SimulateTimeoutHandling {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode uruchamiajaca proces z process.waitFor(2,
         * TimeUnit.SECONDS) na programie, ktory celowo dziala dluzej niz
         * 2 sekundy (np. "java -e" nieprawidlowa flaga wywolujaca petle,
         * albo prosty program z Thread.sleep(5000)) - pokaz, ze kod
         * wykrywa timeout i wywoluje destroyForcibly().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeDecisionTableCollectorChoice {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zbuduj w komentarzu tabele decyzyjna (kolumny:
         * "Wymaganie aplikacji" / "Zalecany kolektor") z min. 4 wierszami,
         * uzywajac G1, ZGC, Shenandoah, Parallel jako opcje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildReusableGcCollectorProbe {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase GcCollectorProbe z metoda `boolean isAvailable(String
         * collectorFlag)`, ktora uruchamia "java <collectorFlag> -version"
         * z limitem czasu i zwraca true/false na podstawie kodu wyjscia i
         * tresci wyjscia (analogicznie do looksLikeUnsupportedOption z
         * teorii) - przetestuj dla -XX:+UseG1GC, -XX:+UseZGC,
         * -XX:+UseShenandoahGC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DetectAllAvailableCollectorsOnThisMachine {
        /*
         * 🧪 Zadanie 22:
         * Uzywajac klasy z Zadania 21, sprawdz WSZYSTKIE 4 kolektory
         * (Serial -XX:+UseSerialGC, Parallel -XX:+UseParallelGC, G1
         * -XX:+UseG1GC, ZGC -XX:+UseZGC) i wypisz liste dostepnych na tej
         * maszynie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareThroughputG1VsZgcUnderChurn {
        /*
         * 🧪 Zadanie 23:
         * Napisz wiekszy program alokacyjny (np. 200 rund x 20 000 obiektow
         * po 512 B) i zmierz calkowity czas jego wykonania z G1 oraz z ZGC
         * (jesli dostepny) - w komentarzu skomentuj, ktory byl szybszy i
         * czy to sie zgadza z oczekiwaniami z teorii (przepustowosc vs
         * pauzy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ParseGcLogPauseTimesIfPresent {
        /*
         * 🧪 Zadanie 24:
         * Z realnego logu -Xlog:gc (G1) wyciagnij (np. wyrazeniem
         * regularnym) wystapienia czasow pauz (fragmenty w formacie
         * "0.123ms" lub podobnym) i wypisz je jako liste - jesli format
         * sie rozni w Twojej wersji JDK, opisz w komentarzu, jak wyglada
         * naprawde.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignExperimentForPauseTimeComparison {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaprojektuj (opisz krok po kroku w komentarzu,
         * min. 5 punktow) rzetelny eksperyment porownujacy REALNE pauzy
         * (nie tylko throughput) miedzy G1 a ZGC dla konkretnej aplikacji -
         * co nalezaloby zmierzyc, jak duzy powinien byc heap, jak dlugo
         * powinien trwac test.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HandlePartiallyUnsupportedFlagCombination {
        /*
         * 🧪 Zadanie 26:
         * Sprobuj uruchomic JVM z kombinacja flag, ktora moze byc
         * niespojna na niektorych buildach (np. -XX:+UseZGC razem z
         * -XX:+UseStringDeduplication, ktora jest specyficzna dla G1) -
         * przechwyc i wypisz przyjazny komunikat, jesli JVM odmowi startu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildRetryLogicWithFallbackCollector {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode `runWithFallback(String... preferredFlags)`, ktora
         * probuje uruchomic AllocatorDemo z pierwsza flaga (np. ZGC), a
         * jesli niedostepna, automatycznie probuje NASTEPNA (np.
         * Shenandoah), a jesli i ta niedostepna - koncowy fallback: G1 (bez
         * dodatkowej flagi). Wypisz, ktory kolektor ostatecznie zostal
         * uzyty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureStartupOverheadOfEachCollector {
        /*
         * 🧪 Zadanie 28:
         * Zmierz czas samego startu JVM (np. "java <flaga> -version") dla
         * kazdego dostepnego kolektora (uzyj wynikow z Zadania 22) - wypisz
         * posortowana (od najszybszego) liste czasow startu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DocumentRealJdkBuildFindings {
        /*
         * 🧪 Zadanie 29:
         * Na podstawie wynikow Zadania 22, napisz w komentarzu krotki
         * "raport" (min. 5 zdan) opisujacy, ktory kolektor(y) sa dostepne
         * na TWOJEJ konkretnej maszynie/wersji JDK, a ktorych brakuje, i co
         * to oznacza dla wyboru kolektora w realnym projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildMiniCollectorBenchmarkHarness {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj kompletne narzedzie
         * CollectorBenchmarkHarness, ktore dla listy kolektorow (Serial,
         * Parallel, G1, ZGC, Shenandoah - o ile dostepne) uruchamia ten sam
         * program alokacyjny, mierzy calkowity czas wykonania i liczbe
         * linii logu GC, a na koniec wypisuje czytelna tabele porownawcza
         * (kolektor / dostepny? / czas / liczba zdarzen GC). Kazdy
         * uruchomiony proces MUSI miec limit czasu (waitFor + timeout).
         */
        public static void main(String[] args) { }
    }
}
