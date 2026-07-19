package com.example.javaquest._25_unit_testing.Lesson01_WhyUnitTests;

public class _Exercises_Lesson01_WhyUnitTests {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyUnitTestsMatter {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij WLASNYMI slowami, DLACZEGO koszt
         * bledu ROSNIE Z CZASEM (od pisania kodu, przez testera, do
         * produkcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteFirstPassingTest {
        /*
         * 🧪 Zadanie 2:
         * Napisz KLASE `StringUtils` Z metoda `reverse(String)` I
         * PRAWDZIWY test JUnit 5 (`@Test`) sprawdzajacy
         * `reverse("abc")` == "cba". Uruchom GO przez Launcher API
         * (wzorem `demonstrateRunningRealJUnitTests` W teorii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteDeliberatelyFailingTest {
        /*
         * 🧪 Zadanie 3:
         * Napisz test, KTORY CELOWO nie przechodzi - odczytaj
         * `TestExecutionSummary.getTestsFailedCount()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainFirstPrinciples {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij WSZYSTKIE 5 liter F.I.R.S.T.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyNonFirstTestExample {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: opisz przyklad testu, KTORY LAMIE zasade
         * "Independent" (zalezy OD kolejnosci uruchomienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteTestWithMultipleAssertions {
        /*
         * 🧪 Zadanie 6:
         * Napisz test Z co najmniej 3 asercjami `assertEquals`/
         * `assertTrue` W JEDNEJ metodzie `@Test`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainTestingPyramid {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: narysuj (ASCII) piramide testow I opisz
         * KAZDA warstwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountTestsInClassWithMultipleMethods {
        /*
         * 🧪 Zadanie 8:
         * Napisz klase testowa Z 5 metodami `@Test` I zweryfikuj
         * `getTestsFoundCount() == 5`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainSelfValidatingProperty {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, CO oznacza "self-validating" - CO
         * SIE ZMIENIA WZGLEDEM recznego testowania (klikanie W
         * aplikacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteTestForDivisionByZero {
        /*
         * 🧪 Zadanie 10:
         * Napisz test sprawdzajacy, ze `Calculator.divide(5, 0)`
         * RZUCA `ArithmeticException` (podpowiedz: `assertThrows`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MeasureTestExecutionTime {
        /*
         * 🧪 Zadanie 11:
         * Odczytaj czas wykonania Z `TestExecutionSummary` I zweryfikuj,
         * ze CALY zestaw testow trwa PONIZEJ 100ms (zasada "Fast").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RunMultipleTestClassesTogether {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `DiscoverySelectors.selectClass(...)` DWUKROTNIE (2
         * ROZNE klasy testowe) W JEDNYM `LauncherDiscoveryRequest`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RunTestsBySelectingPackage {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `DiscoverySelectors.selectPackage(...)` ZAMIAST
         * `selectClass(...)` - uruchom WSZYSTKIE testy W pakiecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteTestThatDependsOnAnotherTest {
        /*
         * 🧪 Zadanie 14:
         * Napisz CELOWO ZLY test (statyczne pole modyfikowane W
         * jednym tescie, odczytywane W drugim) - zaobserwuj, JAK
         * LAMIE ZASADE "Independent".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareManualTestingWithAutomated {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: oszacuj CZAS reczny (klikanie W aplikacje) vs
         * automatyczny (test) DLA sprawdzenia TEJ SAMEJ funkcjonalnosci
         * 100 RAZY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainRepeatableWithRandomData {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, DLACZEGO test UZYWAJACY
         * `new Random()` (BEZ ziarna) LAMIE zasade "Repeatable".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteTestUsingSeededRandom {
        /*
         * 🧪 Zadanie 17:
         * NAPRAW Exercise16 - uzyj `new Random(42)` (STALE ziarno) DLA
         * powtarzalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCustomTestExecutionListener {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj WLASNY `TestExecutionListener` (ZAMIAST
         * `SummaryGeneratingListener`) Z WLASNYM formatowaniem wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainTimelyPrincipleWithLegacyCode {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, DLACZEGO dopisywanie testow DO KODU
         * napisanego ROK TEMU (legacy) jest TRUDNIEJSZE NIZ pisanie
         * ICH razem Z kodem ("Timely").
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignTestingStrategyForNewFeature {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zaprojektuj STRATEGIE testowania DLA nowej
         * funkcji (jakie warstwy piramidy, ILE testow KAZDEJ warstwy).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MeasureCodeCoverageManually {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala: recznie oszacuj POKRYCIE (ile % linii/gałęzi)
         * Z Twoich testow W `CalculatorTest` (zapowiedz Lesson19
         * `CodeCoverageBasics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignTestSuiteForComplexClass {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj (I NAPISZ) PELNY zestaw testow DLA klasy Z
         * WIELOMA metodami I ROZGALEZIENIAMI (min. 8 testow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExplainMutationTestingConcept {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: wyjasnij KONCEPCJE mutation testing (PIT) -
         * "testowanie testow" (zapowiedz Lesson19).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkLauncherApiOverhead {
        /*
         * 🧪 Zadanie 24:
         * Zmierz narzut SAMEGO uruchomienia Launcher API (BEZ testow)
         * WZGLEDEM bezposredniego wywolania metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomAssertionMethod {
        /*
         * 🧪 Zadanie 25:
         * Napisz WLASNA, PONOWNIE UZYWALNA metode asercji (np.
         * `assertIsPositive(int)`) - zapowiedz AssertJ (Lesson05-07).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignTestNamingConvention {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: zaproponuj WLASNA konwencje nazewnictwa metod
         * testowych (np. `metoda_warunek_oczekiwanyWynik`) - zapowiedz
         * Lesson18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareJUnit5WithJUnit4Api {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: powiaz z `_24_spring_security/Lesson03`
         * (ewolucja API) - porownaj `@Test` JUnit 5 Z `@Test` JUnit 4
         * (rozne pakiety, roznice W adnotacjach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignRegressionTestForPastBug {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj "naprawiony bug" (metoda Z ZAKOMENTOWANYM starym,
         * blednym zachowaniem) I napisz test REGRESYJNY, KTORY
         * WYKRYLBY POWROT tego buga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_EstimateTestingRoiForTeam {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: oszacuj (na fikcyjnych liczbach) ROI
         * (zwrot Z inwestycji) pisania testow DLA 5-osobowego zespolu
         * PRZEZ 1 rok.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullTestSuiteWithSummaryReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY zestaw (min. 10 testow, MIX udanych/nieudanych)
         * I wypisz WLASNY, CZYTELNY raport podsumowujacy (procent
         * sukcesu, lista nieudanych Z przyczyna).
         */
        public static void main(String[] args) { }
    }
}
