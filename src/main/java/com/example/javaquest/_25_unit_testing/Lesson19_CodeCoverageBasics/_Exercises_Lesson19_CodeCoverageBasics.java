package com.example.javaquest._25_unit_testing.Lesson19_CodeCoverageBasics;

public class _Exercises_Lesson19_CodeCoverageBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InstrumentAndRunSimpleClassWithJacoco {
        /*
         * 🧪 Zadanie 1:
         * Powtorz przyklad Z lekcji DLA WLASNEJ, PROSTEJ klasy Z 1
         * metoda BEZ galezi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AchieveFullLineCoverage {
        /*
         * 🧪 Zadanie 2:
         * Zmien wywolanie W `run()` TAK, zeby OSIAGNAC 100% pokrycia
         * linii DLA klasy Z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ObserveZeroCoverageWhenMethodNeverCalled {
        /*
         * 🧪 Zadanie 3:
         * Zaobserwuj 0% pokrycia DLA metody, KTORA W OGOLE nie
         * zostala wywolana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareLineCoverageWithBranchCoverage {
        /*
         * 🧪 Zadanie 4:
         * Porownaj (na WYDRUKU) pokrycie LINII Z pokryciem GALEZI DLA
         * TEJ SAMEJ metody `if/else`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddSwitchStatementAndMeasureBranchCoverage {
        /*
         * 🧪 Zadanie 5:
         * Dodaj metode Z `switch` (3+ galezie) I zmierz JEJ pokrycie
         * galezi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyHighCoverageIsNotProofOfCorrectness {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, DLACZEGO 100% pokrycia NIE
         * GWARANTUJE braku bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteMethodWithUnreachableCodeAndObserveCoverage {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode Z NIEOSIAGALNYM (logicznie) fragmentem kodu I
         * zaobserwuj, ze pokrycie TO WYKRYWA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MeasureMethodCoverageAcrossMultipleMethods {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DRUGA metode DO klasy testowanej I zmierz pokrycie
         * METOD (`getMethodCounter()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintPercentageCoverageForEachCounterType {
        /*
         * 🧪 Zadanie 9:
         * Wypisz PROCENTOWE pokrycie DLA WSZYSTKICH 5 typow licznikow
         * JaCoCo (instrukcje/galezie/linie/metody/zlozonosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainRealMavenPluginVsCoreApiUsedHere {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij ROZNICE miedzy `jacoco-maven-plugin`
         * (JVM agent) A `org.jacoco.core` API uzytym W tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InstrumentClassWithLoopAndMeasureCoverage {
        /*
         * 🧪 Zadanie 11:
         * Zinstrumentuj klase Z PETLA (`for`/`while`) I zmierz JEJ
         * pokrycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InstrumentClassWithExceptionHandlingAndMeasureCoverage {
        /*
         * 🧪 Zadanie 12:
         * Zinstrumentuj klase Z `try/catch` I ZMIERZ, CZY blok
         * `catch` jest pokryty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CombineJacocoWithJUnitLauncherFromLesson01 {
        /*
         * 🧪 Zadanie 13:
         * Polacz JaCoCo Z Launcher API (Lesson01) - zinstrumentuj
         * klase, URUCHOM ja PRZEZ PRAWDZIWY test JUnit, zmierz
         * pokrycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyUncoveredLinesAndWriteTestToCoverThem {
        /*
         * 🧪 Zadanie 14:
         * Znajdz NIEPOKRYTE linie W klasie Z lekcji I NAPISZ test
         * (Lesson01-18), KTORY je pokrywa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureCoverageOfClassWithNestedConditionals {
        /*
         * 🧪 Zadanie 15:
         * Zmierz pokrycie klasy Z ZAGNIEZDZONYMI warunkami (`if`
         * WEWNATRZ `if`) - PORTOWNAJ liczbe galezi Z liczba linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GenerateSimpleHtmlCoverageReportManually {
        /*
         * 🧪 Zadanie 16:
         * Wygeneruj PROSTY, WLASNY raport HTML (kolorowanie linii
         * zielony/zolty/czerwony) NA PODSTAWIE danych JaCoCo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareCoverageBeforeAndAfterAddingTest {
        /*
         * 🧪 Zadanie 17:
         * Zmierz pokrycie PRZED I PO dodaniu NOWEGO testu - pokaz
         * KONKRETNY WZROST procentowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainCoverageThresholdsInCiPipelines {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz, JAK skonfigurowac `jacoco-maven-
         * plugin` DO NIEPOWODZENIA builda PONIZEJ progu pokrycia
         * (powiazanie Z `_11_buildtools`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureCoverageImpactOfEarlyReturnStatements {
        /*
         * 🧪 Zadanie 19:
         * Zbadaj wplyw WCZESNYCH `return` NA pokrycie galezi W
         * metodzie Z WIELOMA warunkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignCoverageGoalForRealisticServiceClass {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj (I OPISZ) REALISTYCZNY cel pokrycia DLA klasy
         * serwisowej Z `_17_architecture/Lesson04` (NIE "100% wszedzie").
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InstrumentAndCoverClassWithInheritance {
        /*
         * 🧪 Zadanie 21:
         * Zinstrumentuj HIERARCHIE klas (rodzic/dziecko) I zmierz
         * pokrycie KAZDEJ Z NICH OSOBNO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCoverageDeltaReportBetweenTwoRuns {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj mechanizm porownujacy pokrycie Z DWOCH
         * ODDZIELNYCH URUCHOMIEN (przed/po refaktoryzacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureCoverageOfLambdaExpressionsAndMethodReferences {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_14_advancedjava/Lesson08-10` - zmierz pokrycie
         * kodu UZYWAJACEGO lambd/referencji DO metod (SPRAWDZ, JAK
         * JaCoCo je RAPORTUJE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMutationTestingLiteComparingCoverageOfMutant {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_25_unit_testing/Lesson17` - "zmutuj" metode I
         * zmierz, CZY pokrycie SIE ZMIENIA (spoiler: pokrycie NIE
         * WYKRYWA mutacji - TYLKO wykonanie, NIE POPRAWNOSC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_InstrumentMultipleClassesInSingleSession {
        /*
         * 🧪 Zadanie 25:
         * Zinstrumentuj I zmierz pokrycie 3 ROZNYCH klas W JEDNEJ
         * sesji `RuntimeData`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildCustomCoverageThresholdEnforcer {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj WLASNY "gatekeeper" RZUCAJACY wyjatek, JESLI
         * pokrycie SPADNIE PONIZEJ zadanego progu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureCoverageOfRecursiveMethod {
        /*
         * 🧪 Zadanie 27:
         * Zmierz pokrycie METODY REKURENCYJNEJ (powiazanie Z
         * `_01_fundamentals`) - sprawdz, CZY warunek bazowy JEST
         * pokryty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareJacocoWithAlternativeCoverageTools {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj JaCoCo Z Cobertura/OpenClover
         * (KONCEPCYJNIE - inne podejscie DO instrumentacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCoverageAwareTestPrioritization {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj mechanizm PRIORYTETYZUJACY URUCHAMIANIE testow
         * WEDLUG tego, ILE NOWEGO kodu POKRYWAJA (koncepcja "test
         * impact analysis").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullCoverageStrategyForLayeredApplication {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA strategie pokrycia DLA WARSTWOWEJ
         * aplikacji (powiazanie Z `_17_architecture`) - ROZNE cele
         * pokrycia DLA domeny/serwisow/kontrolerow.
         */
        public static void main(String[] args) { }
    }
}
