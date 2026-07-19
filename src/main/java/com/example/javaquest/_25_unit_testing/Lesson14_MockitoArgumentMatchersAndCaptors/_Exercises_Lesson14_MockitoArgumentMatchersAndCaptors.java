package com.example.javaquest._25_unit_testing.Lesson14_MockitoArgumentMatchersAndCaptors;

public class _Exercises_Lesson14_MockitoArgumentMatchersAndCaptors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StubWithAnyString {
        /*
         * 🧪 Zadanie 1:
         * Zaprogramuj mock `when(mock.metoda(anyString()))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StubWithAnyInt {
        /*
         * 🧪 Zadanie 2:
         * Zaprogramuj mock `when(mock.metoda(anyInt()))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StubWithAnyForCustomType {
        /*
         * 🧪 Zadanie 3:
         * Zaprogramuj mock `when(mock.metoda(any(WlasnaKlasa.class)))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ObserveInvalidUseOfMatchersException {
        /*
         * 🧪 Zadanie 4:
         * CELOWO zmieszaj matcher Z "gola" wartoscia W JEDNYM wywolaniu -
         * przechwyc I opisz `InvalidUseOfMatchersException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseEqExplicitlyAlongsideAnyMatcher {
        /*
         * 🧪 Zadanie 5:
         * Napraw Zadanie 4, uzywajac `eq(...)` DLA "goldej" wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateArgumentCaptorForString {
        /*
         * 🧪 Zadanie 6:
         * Utworz `ArgumentCaptor<String>` I PRZECHWYC argument
         * pojedynczego wywolania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AssertOnCapturedValue {
        /*
         * 🧪 Zadanie 7:
         * Wykonaj 2-3 OSOBNE asercje NA przechwyconej wartosci
         * (`captor.getValue()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CaptureAllValuesFromMultipleCalls {
        /*
         * 🧪 Zadanie 8:
         * Wywolaj mocka 3 RAZY I przechwyc WSZYSTKIE wartosci
         * (`getAllValues()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseArgThatWithSimplePredicate {
        /*
         * 🧪 Zadanie 9:
         * Uzyj `argThat(...)` Z PROSTYM predykatem (np. dlugosc
         * Stringa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToUseMatcherVsExactValue {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, KIEDY uzyc matchera (`any`), a
         * KIEDY konkretnej wartosci (Lesson13).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseCaptorToVerifyObjectPassedToRepository {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z `_10_dao` - uzyj `ArgumentCaptor` DO sprawdzenia
         * OBIEKTU przekazanego DO mockowanego repozytorium `save(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CombineMultipleMatchersInSingleCall {
        /*
         * 🧪 Zadanie 12:
         * Napisz stubowanie Z 3 RONYMI matcherami W JEDNYM wywolaniu
         * metody 3-argumentowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseArgThatForComplexObjectValidation {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `argThat(...)` DO WERYFIKACJI zlozonego obiektu
         * (KILKA pol NARAZ W JEDNYM predykacie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CaptureAndSortValuesFromMultipleCalls {
        /*
         * 🧪 Zadanie 14:
         * Przechwyc WIELE wartosci I POSORTUJ je (weryfikacja Z
         * AssertJ `Lesson06`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseAdditionalMatchersNotAndOr {
        /*
         * 🧪 Zadanie 15:
         * Zbadaj `AdditionalMatchers` (`not(eq(...))`,
         * `or`/`and`) I uzyj JEDNEGO Z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestServiceWithCaptorVerifyingTransformation {
        /*
         * 🧪 Zadanie 16:
         * Napisz test SPRAWDZAJACY, ze serwis TRANSFORMUJE dane PRZED
         * przekazaniem DO zaleznosci (captor porownuje PRZED/PO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseNullableMatcherForOptionalArgument {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `nullable(Klasa.class)` DLA argumentu, KTORY MOZE byc
         * `null`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CaptureVarargsOrCollectionArgument {
        /*
         * 🧪 Zadanie 18:
         * Przechwyc argument bedacy KOLEKCJA (np. `List<String>`) I
         * zweryfikuj JEJ ZAWARTOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareArgumentCaptorWithArgThatApproach {
        /*
         * 🧪 Zadanie 19:
         * Napisz TEN SAM test DWOMA sposobami - `ArgumentCaptor` ORAZ
         * `argThat` - porownaj CZYTELNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_UseCaptorInBeforeEachSetupHelper {
        /*
         * 🧪 Zadanie 20:
         * Wydziel TWORZENIE captora DO metody pomocniczej WYWOLYWANEJ
         * Z KILKU testow (powiazanie Z `_25_unit_testing/Lesson04`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomArgumentMatcherClass {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNA klase `ArgumentMatcher<T>` (zamiast
         * lambdy W `argThat`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestBatchProcessingWithCaptorCollectingAllBatches {
        /*
         * 🧪 Zadanie 22:
         * Przetestuj logike PRZETWARZAJACA dane W partiach (batch) -
         * captor zbiera WSZYSTKIE partie przekazane DO zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CombineCaptorWithInOrderVerification {
        /*
         * 🧪 Zadanie 23:
         * Polacz `ArgumentCaptor` Z `InOrder` (Lesson13 Zadanie 12) -
         * sprawdz KOLEJNOSC I ZAWARTOSC NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestEventPublisherWithCaptorVerifyingEventPayload {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_17_architecture/Lesson18_EventDriven` - przetestuj
         * PUBLISHER zdarzen, przechwytujac PAYLOAD zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementParameterizedMatcherFactory {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj WLASNA, PARAMETRYZOWANA metode fabrykujaca matchery
         * (np. `hasLengthGreaterThan(n)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestRetryMechanismCapturingEachAttemptPayload {
        /*
         * 🧪 Zadanie 26:
         * Przetestuj mechanizm retry, przechwytujac payload KAZDEJ
         * proby OSOBNO (powiazanie Z `_26_integration_testing`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasurePerformanceImpactOfArgumentCaptor {
        /*
         * 🧪 Zadanie 27:
         * Zmierz (przyblizony) narzut uzycia `ArgumentCaptor` NA CZAS
         * wykonania duzej liczby wywolan mocka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareMockitoMatchersWithHamcrestMatchers {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj matchery Mockito (`ArgumentMatchers`)
         * Z matcherami Hamcrest (koncepcyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCaptorBasedSnapshotAssertionHelper {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj WLASNA metode pomocnicza porownujaca PRZECHWYCONY
         * obiekt Z "migawka" (snapshot) oczekiwanego stanu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMockingAndCapturingTestForMultiStepWorkflow {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj test WIELOETAPOWEGO workflow (3+ krokow), gdzie
         * KAZDY krok jest zweryfikowany matcherami I captorami NARAZ.
         */
        public static void main(String[] args) { }
    }
}
