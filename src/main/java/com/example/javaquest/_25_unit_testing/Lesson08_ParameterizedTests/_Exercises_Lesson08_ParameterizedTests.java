package com.example.javaquest._25_unit_testing.Lesson08_ParameterizedTests;

public class _Exercises_Lesson08_ParameterizedTests {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainParameterizedTestPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, PO CO uzywa sie
         * `@ParameterizedTest`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteValueSourceIntTest {
        /*
         * 🧪 Zadanie 2:
         * Napisz `@ParameterizedTest` Z `@ValueSource(ints = {...})`
         * sprawdzajacy parzystosc liczb.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteValueSourceStringTest {
        /*
         * 🧪 Zadanie 3:
         * Napisz `@ValueSource(strings = {...})` sprawdzajacy DLUGOSC
         * Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteCsvSourceTest {
        /*
         * 🧪 Zadanie 4:
         * Napisz `@CsvSource` Z 3 kolumnami (2 argumenty WEJSCIOWE +
         * 1 oczekiwany wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainCsvSourceFormat {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij format `@CsvSource` (przecinki,
         * cudzyslowy DLA wartosci ZAWIERAJACYCH przecinek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMethodSourceTest {
        /*
         * 🧪 Zadanie 6:
         * Napisz `@MethodSource` odwolujace sie DO metody statycznej
         * W TEJ SAMEJ klasie (BEZ pelnej kwalifikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountTestInvocationsInSummary {
        /*
         * 🧪 Zadanie 7:
         * Uruchom parametryzowany test Z 5 wartosciami I zweryfikuj
         * `getTestsFoundCount() == 5`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteFailingParameterizedTestForOneValue {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DO `@ValueSource` WARTOSC, KTORA CELOWO NIE PRZEJDZIE
         * asercji - zaobserwuj, KTORE KONKRETNE wywolanie zawiodlo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithManualLoopInSingleTest {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO petla `for` WEWNATRZ
         * JEDNEGO `@Test` (BEZ `@ParameterizedTest`) jest GORSZYM
         * rozwiazaniem (1 porazka ZATRZYMUJE CALA petle, BRAK
         * osobnego raportu NA wartosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UseEmptySourceAndNullSource {
        /*
         * 🧪 Zadanie 10:
         * Dodaj `@EmptySource`/`@NullSource` DO testu sprawdzajacego
         * zachowanie DLA pustego/null Stringa.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteMethodSourceReturningComplexObjects {
        /*
         * 🧪 Zadanie 11:
         * Napisz `@MethodSource` zwracajace `Stream<Arguments>` Z
         * OBIEKTAMI (NIE prymitywami) jako argumentami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseEnumSource {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@EnumSource(TwojEnum.class)` DO przetestowania
         * WSZYSTKICH wartosci enum (powiazanie Z `_02_oop/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FilterEnumSourceValues {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `@EnumSource(names = {...})` DO ograniczenia
         * testowanych wartosci enum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CustomizeDisplayNameForParameterizedTest {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `@ParameterizedTest(name = "...")` Z WLASNYM formatem
         * nazwy wywolania (np. Z placeholderami `{0}`/`{1}`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseCsvFileSource {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `@CsvFileSource(resources = "...")` Z danymi Z pliku
         * `.csv` (powiazanie Z `_04_io`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteArgumentsConverterExample {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj WLASNY `ArgumentsAggregator`/konwerter DLA
         * ZLOZONEGO typu argumentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CombineParameterizedTestWithAssertJChain {
        /*
         * 🧪 Zadanie 17:
         * Napisz `@ParameterizedTest` UZYWAJACY LANCUCHA asercji
         * AssertJ (Lesson05) NA KAZDYM parametrze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestBoundaryValuesWithValueSource {
        /*
         * 🧪 Zadanie 18:
         * Napisz `@ValueSource` testujacy WARTOSCI GRANICZNE (0, -1,
         * `Integer.MAX_VALUE`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteMethodSourceSharedAcrossMultipleTests {
        /*
         * 🧪 Zadanie 19:
         * Uzyj TEJ SAMEJ metody `@MethodSource` W 2 ROZNYCH testach
         * parametryzowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignParameterizedTestForValidationLogic {
        /*
         * 🧪 Zadanie 20:
         * Powiaz z `_19_security_basics/Lesson17` - napisz
         * `@ParameterizedTest` sprawdzajacy WALIDACJE hasla (min 8
         * znakow, cyfra, wielka litera) Z 10 przypadkami.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomArgumentsProvider {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `ArgumentsProvider` (`@ArgumentsSource`)
         * ZAMIAST `@MethodSource`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRepeatableArgumentsSource {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj WLASNA, PONOWNIE UZYWALNA adnotacje LACZACA
         * `@ParameterizedTest` + `@ArgumentsSource` W JEDNA (composed
         * annotation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestCartesianProductOfParameters {
        /*
         * 🧪 Zadanie 23:
         * Uzyj `@MethodSource` DO wygenerowania ILOCZYNU KARTEZJANSKIEGO
         * 2 list argumentow (WSZYSTKIE kombinacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkParameterizedVsSeparateMethods {
        /*
         * 🧪 Zadanie 24:
         * Porownaj CZAS wykonania 100 przypadkow jako
         * `@ParameterizedTest` Z 100 OSOBNYMI metodami `@Test`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDataDrivenTestFromExternalJsonFile {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_04_io/Lesson19-20` - wczytaj dane testowe Z
         * pliku JSON I uzyj ICH jako `@MethodSource`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignFuzzTestingWithRandomizedValueSource {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj `@MethodSource` GENERUJACE LOSOWE (ale SEED-owane,
         * powiazanie Z Lesson01 Zadanie 17) dane WEJSCIOWE - PROSTA
         * forma "fuzz testing".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementPropertyBasedTestingConcept {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wyjasnij KONCEPCJE "property-based testing"
         * (np. biblioteka jqwik) - CZYM ROZNI SIE OD zwyklego
         * `@ParameterizedTest`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestWithCombinedValueSourceAndMethodSource {
        /*
         * 🧪 Zadanie 28:
         * Uzyj `@ParameterizedTest` Z `@ArgumentsSources` (WIELE
         * ZRODEL NARAZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementConditionalArgumentsFiltering {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj WLASNY `ArgumentsProvider` FILTRUJACY dane NA
         * PODSTAWIE `ExtensionContext` (np. tag testu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullParameterizedTestSuiteForPricingEngine {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj "silnik cenowy" (rabaty WEDLUG progow ilosciowych) I
         * PELNY zestaw testow parametryzowanych POKRYWAJACY WSZYSTKIE
         * progi + przypadki brzegowe (min. 15 przypadkow).
         */
        public static void main(String[] args) { }
    }
}
