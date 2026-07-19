package com.example.javaquest._25_unit_testing.Lesson13_MockitoBasics;

public class _Exercises_Lesson13_MockitoBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateMockOfInterface {
        /*
         * 🧪 Zadanie 1:
         * Utworz mock DOWOLNEGO WLASNEGO interfejsu przez `mock(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StubMethodWithThenReturn {
        /*
         * 🧪 Zadanie 2:
         * Zaprogramuj mock `when(...).thenReturn(...)` DLA metody
         * zwracajacej `String`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StubMethodWithThenThrow {
        /*
         * 🧪 Zadanie 3:
         * Zaprogramuj mock `when(...).thenThrow(...)` I zweryfikuj
         * przez `assertThatThrownBy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VerifyMethodCalledOnce {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj `verify(mock).metoda()` PO JEDNYM wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyMethodCalledTimesN {
        /*
         * 🧪 Zadanie 5:
         * Zweryfikuj `verify(mock, times(3)).metoda()` PO TRZECH
         * wywolaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyMethodNeverCalled {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj `verify(mock, never()).metoda()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseUnstubbedMockAndObserveDefaultValue {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj NIEZAPROGRAMOWANA metode mocka I sprawdz DOMYSLNA
         * wartosc (`int`/`boolean`/kolekcja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MockClassInsteadOfInterface {
        /*
         * 🧪 Zadanie 8:
         * Utworz mock ZWYKLEJ KLASY (NIE interfejsu) I zweryfikuj, ze
         * DZIALA IDENTYCZNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InjectMockManuallyIntoServiceConstructor {
        /*
         * 🧪 Zadanie 9:
         * Wstrzyknij mock RECZNIE (przez konstruktor) DO klasy
         * serwisowej, wzorem `OrderService` Z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMockAndNotRealDependency {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO test JEDNOSTKOWY POWINIEN
         * mockowac zaleznosci ZAMIAST uzywac PRAWDZIWYCH.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_StubMultipleCallsWithDifferentReturnValues {
        /*
         * 🧪 Zadanie 11:
         * Zaprogramuj `when(...).thenReturn(a, b, c)` (KOLEJNE
         * wywolania zwracaja INNE wartosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VerifyInteractionOrderWithInOrder {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `InOrder` DO sprawdzenia KOLEJNOSCI wywolan 2 metod NA
         * MOCKU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseArgumentMatchersAnyForFlexibleStubbing {
        /*
         * 🧪 Zadanie 13:
         * Zaprogramuj mock `when(mock.metoda(anyString()))` (zapowiedz
         * Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestServiceWithTwoMockedDependencies {
        /*
         * 🧪 Zadanie 14:
         * Napisz test serwisu ZALEZNEGO OD DWOCH mockowanych
         * interfejsow NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_VerifyNoMoreInteractionsAfterExpectedCalls {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `verifyNoMoreInteractions(mock)` PO sprawdzeniu
         * OCZEKIWANYCH wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_StubVoidMethodToThrowException {
        /*
         * 🧪 Zadanie 16:
         * Zaprogramuj metode `void` MOCKA DO rzucania wyjatku
         * (`doThrow(...).when(mock).metoda()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareMockWithRealImplementationBehavior {
        /*
         * 🧪 Zadanie 17:
         * Porownaj (W komentarzu) zachowanie MOCKA Z PRAWDZIWA
         * implementacja TEGO SAMEGO interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestOrderServiceRejectionPath {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz `OrderService` Z lekcji O NOWA metode (`cancelOrder`)
         * I NAPISZ dla niej test Z mockiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MockRepositoryInterfaceForDaoLikeClass {
        /*
         * 🧪 Zadanie 19:
         * Powiaz z `_10_dao` - zamockuj interfejs REPOZYTORIUM I
         * napisz test klasy serwisowej korzystajacej Z NIEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainMockVsSpyDifference {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij ROZNICE miedzy `mock(...)` A
         * `spy(...)` (zapowiedz Lesson15/16).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomAnswerForComplexStubbing {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `Answer<T>` DLA mocka (zamiast prostego
         * `thenReturn`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestRetryLogicWithMockThrowingThenSucceeding {
        /*
         * 🧪 Zadanie 22:
         * Zaprogramuj mock RZUCAJACY wyjatek PRZY 2 PIERWSZYCH
         * wywolaniach, a PRZY 3 zwracajacy sukces - przetestuj logike
         * retry.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MockGenericInterfaceWithTypeParameters {
        /*
         * 🧪 Zadanie 23:
         * Zamockuj interfejs GENERYCZNY (np. `Repository<T, ID>`) I
         * zweryfikuj typowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestExceptionTranslationLayerWithMock {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_20_spring_core/Lesson08` - przetestuj WARSTWE
         * TLUMACZACA wyjatki, mockujac ZRODLOWY wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementMockBasedContractTestSkeleton {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj SZKIELET testu kontraktowego (powiazanie Z
         * `_26_integration_testing/Lesson13`) uzywajacego mocka JAKO
         * "fake serwera".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestConcurrentMockUsageAcrossThreads {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_05_multithreading` - przetestuj uzycie mocka Z
         * WIELU watkow NARAZ (I omow BEZPIECZENSTWO watkowe Mockito).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementMockFactoryHelperForTestSuite {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj WLASNA "fabryke" mockow (metoda pomocnicza tworzaca
         * PRZYGOTOWANY mock Z DOMYSLNYM stubowaniem) DLA CALEGO
         * pakietu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareMockitoWithManualHandWrittenStub {
        /*
         * 🧪 Zadanie 28:
         * Napisz TEN SAM test DWOMA sposobami - Mockito ORAZ RECZNIE
         * napisana klasa-atrapa - porownaj ILOSC kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementMockResetBetweenTestPhases {
        /*
         * 🧪 Zadanie 29:
         * Uzyj `Mockito.reset(mock)` DO wyczyszczenia stubowan
         * MIEDZY DWIEMA FAZAMI JEDNEGO testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignMockingStrategyForLargeServiceWithManyDependencies {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj (I NAPISZ) test DUZEGO serwisu Z 4 MOCKOWANYMI
         * zaleznosciami NARAZ - zweryfikuj interakcje MIEDZY WSZYSTKIMI.
         */
        public static void main(String[] args) { }
    }
}
