package com.example.javaquest._16_clean_code.Lesson20_StaticAnalysisTools;

public class _Exercises_Lesson20_StaticAnalysisTools {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainStaticAnalysisInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), czym rozni sie analiza STATYCZNA (bez uruchamiania
         * programu) od zwyklego testowania (uruchamiania programu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunPmdOnGivenCodeWithUnusedVariable {
        /*
         * 🧪 Zadanie 2:
         * Zapisz do tymczasowego pliku `.java` klase z 1 nieuzywana zmienna
         * lokalna. Uruchom na niej PMD (wzorem `_Lesson20_StaticAnalysisTools`,
         * ruleset `category/java/bestpractices.xml`) i wypisz WSZYSTKIE
         * znalezione naruszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RunPmdOnCleanCodeAndObserveNoViolations {
        /*
         * 🧪 Zadanie 3:
         * Zapisz do tymczasowego pliku `.java` PROSTA, poprawna klase (bez
         * zadnych smelli) i uruchom na niej PMD z tym samym rulesetem co w
         * Zadaniu 2 - zweryfikuj, ze lista naruszen jest PUSTA (lub bardzo
         * krotka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunSpotBugsOnGivenCodeWithStringEqualityBug {
        /*
         * 🧪 Zadanie 4:
         * Zapisz i SKOMPILUJ (jak w teorii lekcji) klase z metoda porownujaca
         * 2 Stringi przez `==`. Uruchom na niej SpotBugs i wypisz znalezione
         * bledy (oczekiwany typ: `ES_COMPARING_PARAMETER_STRING_WITH_EQ`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RunSpotBugsOnCleanCompiledCode {
        /*
         * 🧪 Zadanie 5:
         * Zapisz, skompiluj i przeanalizuj SpotBugsem PROSTA, poprawna klase -
         * zweryfikuj, ze liczba znalezionych bledow jest 0 (lub bliska 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareViolationCountsBetweenDirtyAndCleanCode {
        /*
         * 🧪 Zadanie 6:
         * Uruchom PMD na OBU wersjach kodu z Zadan 2 i 3 (brudna i czysta) -
         * wypisz obie liczby naruszen OBOK siebie jako porownanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyWhichSmellFromLesson14MatchesPmdViolation {
        /*
         * 🧪 Zadanie 7:
         * Uruchom PMD na kodzie z metoda majaca 6 parametrow (Long Parameter
         * List, Lesson14). W komentarzu wskaz, KTORY smell z Lesson14
         * odpowiada zglaszanym przez PMD naruszeniom (jesli PMD cos znajdzie w
         * uzywanym rulesecie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PrintViolationLineNumbersAndDescriptions {
        /*
         * 🧪 Zadanie 8:
         * Uruchom PMD na kodzie z min. 2 roznymi naruszeniami na ROZNYCH
         * liniach - wypisz dla kazdego naruszenia: numer linii, nazwe reguly i
         * pelny opis (getDescription()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainDifferenceBetweenPmdAndSpotBugsInOwnWords {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), na czym polega roznica miedzy analiza kodu ZRODLOWEGO
         * (PMD) a analiza BYTECODE'U (SpotBugs).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFalsePositiveScenarioFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymysl i opisz w komentarzu 1 wlasny przyklad
         * sytuacji, w ktorej narzedzie statycznej analizy mogloby zglosic
         * FALSZYWY ALARM (kod POPRAWNY, ale wygladajacy podejrzanie dla
         * automatu).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RunPmdWithDifferentRuleCategory {
        /*
         * 🧪 Zadanie 11:
         * Uruchom PMD na tym samym "brudnym" kodzie z teorii lekcji, ale z
         * INNYM rulesetem (`category/java/errorprone.xml` zamiast
         * `bestpractices.xml`) - porownaj liczbe i rodzaj znalezionych
         * naruszen z wynikiem oryginalnej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LoadMultipleRuleSetsAtOnce {
        /*
         * 🧪 Zadanie 12:
         * Uzywajac `pmd.newRuleSetLoader().loadFromResources(...)`, zaladuj
         * NARAZ 2 rulesety (`bestpractices.xml` i `codestyle.xml`) - dodaj
         * OBA do analizy przez `addRuleSet` (w petli) i wypisz LACZNA liczbe
         * naruszen z obu rulesetow razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteCodeTriggeringSpecificPmdRule {
        /*
         * 🧪 Zadanie 13:
         * Napisz kod celowo lamiacy 1 KONKRETNA regule z
         * `category/java/bestpractices.xml` (np. `SystemPrintln` - uzycie
         * `System.out.println`) - uruchom PMD i zweryfikuj, ze WLASNIE ta
         * regula zostala zglosza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteCodeTriggeringSpecificSpotBugsDetector {
        /*
         * 🧪 Zadanie 14:
         * Napisz kod celowo lamiacy INNY (niz w teorii) detektor SpotBugs -
         * np. nieuzywana zmienna prywatna typu pola z adnotacja, albo metoda
         * `equals()` bez odpowiadajacej `hashCode()`. Skompiluj, przeanalizuj
         * i zweryfikuj zgloszony typ bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FilterViolationsByRuleName {
        /*
         * 🧪 Zadanie 15:
         * Uruchom PMD na kodzie z min. 3 roznymi naruszeniami. Uzywajac
         * `report.filterViolations(...)`, wypisz TYLKO naruszenia o KONKRETNEJ
         * nazwie reguly (np. tylko "UnusedPrivateField").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureAnalysisTimeForPmdVsSpotBugs {
        /*
         * 🧪 Zadanie 16:
         * Uzywajac `System.nanoTime()`, zmierz przyblizony czas trwania
         * analizy PMD i OSOBNO analizy SpotBugs dla TEGO SAMEGO kodu -
         * wypisz oba czasy i w komentarzu skomentuj, ktora analiza byla
         * szybsza i dlaczego (patrz teoria: brak potrzeby kompilacji dla PMD).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FixDirtyCodeAndVerifyViolationsDisappear {
        /*
         * 🧪 Zadanie 17:
         * Uruchom PMD na "brudnym" kodzie z teorii lekcji (BAD_CODE_SOURCE) -
         * zapisz liczbe naruszen. NAPRAW kod (usun nieuzywane pole, zastap ==
         * przez equals, usun martwy zapis) i uruchom PMD PONOWNIE - potwierdz,
         * ze liczba naruszen SPADLA (najlepiej do zera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RunSpotBugsWithDifferentPriorityThreshold {
        /*
         * 🧪 Zadanie 18:
         * Uruchom SpotBugs na tym samym kodzie DWUKROTNIE - raz z progiem
         * `Priorities.LOW_PRIORITY`, raz z `Priorities.HIGH_PRIORITY` -
         * porownaj liczbe znalezionych bledow w obu przypadkach i w
         * komentarzu wyjasnij, dlaczego wyzszy prog zwraca MNIEJ wynikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildSimpleReportSummarizingViolationsByRule {
        /*
         * 🧪 Zadanie 19:
         * Uruchom PMD na kodzie z min. 4 naruszeniami z ROZNYCH regul. Zbuduj
         * `Map<String, Integer>` zliczajaca, ile RAZY kazda regula zostala
         * naruszona, i wypisz podsumowanie posortowane malejaco wg liczby
         * wystapien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealFileFromEarlierChapterWithPmd {
        /*
         * 🧪 Zadanie 20:
         * Skopiuj zawartosc 1 pliku `.java` z dowolnej WCZESNIEJSZEJ lekcji
         * kursu (np. `_10_dao`) do zmiennej String, zapisz do pliku
         * tymczasowego i uruchom na niej PMD z `bestpractices.xml` - wypisz
         * WSZYSTKIE znalezione naruszenia (jesli sa).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildCombinedPmdAndSpotBugsReport {
        /*
         * 🧪 Zadanie 21:
         * Uruchom OBA narzedzia (PMD i SpotBugs) na TYM SAMYM kodzie i
         * zbuduj 1 LACZNY raport tekstowy (String) laczacy wyniki OBU -
         * kazdy wpis oznaczony etykieta "[PMD]" lub "[SpotBugs]".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignQualityGateFailingBuildOnTooManyViolations {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode `boolean passesQualityGate(int violationCount, int
         * maxAllowed)` symulujaca "brame jakosci" (quality gate) uzywana w CI -
         * uruchom PMD na kodzie z teorii, przekaz WYNIK do tej metody z
         * roznymi progami `maxAllowed` i wypisz PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareViolationsAcrossThreeCodeVersions {
        /*
         * 🧪 Zadanie 23:
         * Napisz 3 wersje TEJ SAMEJ funkcjonalnosci o rosnacej jakosci (np.
         * "bardzo brudna", "czesciowo poprawiona", "czysta") - uruchom PMD na
         * KAZDEJ z 3 wersji i wypisz TREND liczby naruszen (powinien maleć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteCustomPmdRuleSetFromScratch {
        /*
         * 🧪 Zadanie 24:
         * Uzywajac `pmd.newRuleSetLoader().loadFromString(...)`, zdefiniuj
         * WLASNY, minimalny XML rulesetu odwolujacy sie TYLKO do 1-2
         * konkretnych regul (np. `SystemPrintln` i `UnusedLocalVariable` z
         * `category/java/bestpractices.xml` przez `<rule ref="...">`) - uruchom
         * analize z tym niestandardowym rulesetem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnalyzeMultipleFilesInOneRun {
        /*
         * 🧪 Zadanie 25:
         * Zapisz 3 ROZNE pliki `.java` (kazdy z innym naruszeniem) do WSPOLNEGO
         * katalogu tymczasowego. Dodaj WSZYSTKIE 3 do jednej analizy PMD
         * (przez `pmd.files().addFile(...)` 3 razy) i wypisz naruszenia
         * pogrupowane WG PLIKU (uzyj `violation.getFileId()`/
         * `getLocation().getFileId()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureFalsePositiveRateOnKnownGoodCode {
        /*
         * 🧪 Zadanie 26:
         * Wybierz (lub napisz) 3 ROZNE, ale poprawne klasy - uruchom PMD i
         * SpotBugs na kazdej. Jesli KTOKOLWIEK z narzedzi zglosi cokolwiek,
         * w komentarzu oceń, czy to realny problem, czy falszywy alarm
         * (odwolaj sie do teorii lekcji o false positives).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignSuppressionCommentForKnownFalsePositive {
        /*
         * 🧪 Zadanie 27:
         * Napisz kod, ktory CELOWO wyglada podejrzanie dla PMD (np. bardzo
         * krotka nazwa zmiennej w petli `for`), ale jest w pelni poprawny w
         * kontekscie. Dodaj `// NOPMD` (mechanizm tlumienia PMD) w
         * odpowiednim miejscu i zademonstruj, ze naruszenie znika z raportu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildFullPipelineCompileAnalyzeReportForSingleFile {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj JEDNA metode `runFullQualityCheck(String sourceCode)`
         * laczaca CALY przeplyw: zapis do pliku tymczasowego, kompilacja,
         * analiza PMD, analiza SpotBugs, i wypisanie SPOJNEGO, sformatowanego
         * raportu koncowego z liczba naruszen kazdego typu. Wywolaj ja dla 2
         * roznych probek kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveStaticAnalysisAdoptionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 5
         * punktow) do WDROZENIA statycznej analizy w realnym projekcie -
         * laczac tresci z tej lekcji (wybor narzedzi, integracja z CI,
         * obsluga false positives, quality gate, relacja do code review).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneAutomatedQualityCheckForRealisticModule {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz REALISTYCZNY, "brudny" moduł (min. 4
         * rozne problemy laczace smelle z Lesson14 i realne bledy jak z
         * SpotBugs) i zbuduj dla niego KOMPLETNY, automatyczny "quality
         * check": kompilacja, analiza PMD (min. 2 rulesety), analiza
         * SpotBugs, agregacja WSZYSTKICH wynikow w 1 raporcie, oraz decyzja
         * PASS/FAIL na podstawie progu naruszen (jak w Zadaniu 22).
         * Zademonstruj dzialanie na oryginalnym "brudnym" module ORAZ na jego
         * naprawionej wersji.
         */
        public static void main(String[] args) { }
    }
}
