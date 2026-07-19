package com.example.javaquest._25_unit_testing.Lesson15_MockitoAnnotationsAndExtension;

public class _Exercises_Lesson15_MockitoAnnotationsAndExtension {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AnnotateFieldWithMock {
        /*
         * 🧪 Zadanie 1:
         * Oznacz pole `@Mock` DLA WLASNEGO interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AnnotateFieldWithInjectMocks {
        /*
         * 🧪 Zadanie 2:
         * Oznacz pole testowanej klasy `@InjectMocks`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseMockitoAnnotationsOpenMocksInBeforeEach {
        /*
         * 🧪 Zadanie 3:
         * Zainicjalizuj mocki RECZNIE PRZEZ
         * `MockitoAnnotations.openMocks(this)` W `@BeforeEach`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseExtendWithMockitoExtension {
        /*
         * 🧪 Zadanie 4:
         * Oznacz klase testowa `@ExtendWith(MockitoExtension.class)`
         * ZAMIAST recznej inicjalizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareManualVsExtensionInitialization {
        /*
         * 🧪 Zadanie 5:
         * Napisz TEN SAM test DWOMA sposobami (Zadanie 3 vs 4) -
         * porownaj ILOSC kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InjectMocksIntoServiceWithSingleDependency {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `@InjectMocks` DLA serwisu Z JEDNA zaleznoscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InjectMocksIntoServiceWithTwoDependencies {
        /*
         * 🧪 Zadanie 7:
         * Uzyj `@InjectMocks` DLA serwisu Z DWIEMA zaleznosciami
         * (wzorem `InventoryService` Z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainConstructorVsFieldInjectionInMockito {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, W JAKIEJ KOLEJNOSCI `@InjectMocks`
         * PROBUJE wstrzykiwac (konstruktor/settery/pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteTestUsingBothMockAndInjectMocksTogether {
        /*
         * 🧪 Zadanie 9:
         * Napisz PELNY test uzywajacy `@Mock` + `@InjectMocks` +
         * `verify(...)` NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ObserveUnnecessaryStubbingException {
        /*
         * 🧪 Zadanie 10:
         * CELOWO dodaj NIEUZYWANE `when(...)` W tescie Z
         * `MockitoExtension` - zaobserwuj
         * `UnnecessaryStubbingException`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InjectMocksWithThreeOrMoreDependencies {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz serwis O TRZECIA zaleznosc I zweryfikuj poprawne
         * wstrzykniecie WSZYSTKICH TRZECH przez `@InjectMocks`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseLenientAnnotationToSuppressUnnecessaryStubbing {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@MockitoSettings(strictness = Strictness.LENIENT)` LUB
         * `lenient().when(...)` DO WYLACZENIA sprawdzania Zadania 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteTestClassWithMultipleTestMethodsSharingMocks {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase Z 4 metodami testowymi WSPOLDZIELACYMI TE SAME
         * pola `@Mock` - zweryfikuj, ze KAZDY test DOSTAJE SWIEZY mock
         * (izolacja Z Lesson04).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineMockitoExtensionWithNestedTests {
        /*
         * 🧪 Zadanie 14:
         * Polacz `MockitoExtension` Z `@Nested` (powiazanie Z
         * `_25_unit_testing/Lesson09`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InjectMocksWhereOneDependencyIsNotMocked {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj serwis Z DWIEMA zaleznosciami, GDZIE JEDNA jest
         * mockiem, a DRUGA PRAWDZIWA implementacja - zweryfikuj, ze
         * `@InjectMocks` OBSLUGUJE TAKA mieszanke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestExceptionPropagationThroughInjectedMocks {
        /*
         * 🧪 Zadanie 16:
         * Zaprogramuj JEDEN Z `@Mock` DO rzucania wyjatku I zweryfikuj
         * PROPAGACJE PRZEZ `@InjectMocks` serwis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareMockitoExtensionWithSpringExtension {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj `MockitoExtension` Z
         * `SpringExtension`/`@MockitoBean` (zapowiedz
         * `_27_spring_test/Lesson08`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteTestVerifyingNoInteractionsWithOneMockedDependency {
        /*
         * 🧪 Zadanie 18:
         * Napisz test SPRAWDZAJACY, ze SCIEZKA BLEDU serwisu NIE
         * dotyka JEDNEJ Z zaleznosci (`verifyNoInteractions`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorManualMockCreationToAnnotations {
        /*
         * 🧪 Zadanie 19:
         * Wez test Z Lesson13 (reczny `mock(...)`) I PRZEPISZ go NA
         * `@Mock`+`@InjectMocks`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentProsAndConsOfInjectMocks {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: opisz WADY `@InjectMocks` (np. CICHE
         * niepowodzenie WSTRZYKIWANIA, brak jawnosci).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomMockitoExtensionVariant {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNE rozszerzenie JUnit 5 INSPIROWANE
         * `MockitoExtension` (WLASNA, uproszczona logika
         * inicjalizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestComplexServiceWithFourMockedDependenciesAndCaptors {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj serwis Z 4 zaleznosciami (`@Mock`) + `@InjectMocks` +
         * `ArgumentCaptor` (Lesson14) NA KAZDEJ - PELNY test integracyjny
         * NA POZIOMIE jednostkowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementStaticFactoryForPreconfiguredTestFixtures {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj WLASNA klase "Fixture" TWORZACA GOTOWY zestaw
         * `@Mock`-owanych obiektow DLA CALEGO pakietu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestServiceRequiringDeepMockChain {
        /*
         * 🧪 Zadanie 24:
         * Przetestuj serwis WYMAGAJACY glebokiego lancucha wywolan NA
         * mockach (`mock.getX().getY()`) - omow, DLACZEGO TO ZLY
         * wzorzec ("Law of Demeter", powiazanie Z
         * `_17_architecture/Lesson12`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementParameterResolverProvidingPreMockedDependency {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNY `ParameterResolver` (JUnit 5) DOSTARCZAJACY
         * GOTOWY, WCZESNIEJ skonfigurowany mock JAKO PARAMETR testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestConcurrentServiceCallsWithSharedMockedDependency {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_05_multithreading` - przetestuj serwis wywolywany
         * Z WIELU watkow, WSPOLDZIELACY JEDNA, zamockowana zaleznosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigrateHandWrittenTestSuiteToMockitoAnnotationsFully {
        /*
         * 🧪 Zadanie 27:
         * Wez CALY pakiet testow Z Lesson13/14 (reczne mocki) I
         * PRZEPISZ WSZYSTKIE NA `@Mock`/`@InjectMocks`/`MockitoExtension`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCustomInjectionStrategyDocumentedInComment {
        /*
         * 🧪 Zadanie 28:
         * Opisz (W komentarzu) scenariusz, W KTORYM `@InjectMocks` NIE
         * WYSTARCZA (np. WIELE konstruktorow) I zaproponuj obejscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkMockitoExtensionOverheadAcrossManyTests {
        /*
         * 🧪 Zadanie 29:
         * Zmierz CZAS uruchomienia 50 testow Z `MockitoExtension`
         * WZGLEDEM 50 testow Z RECZNA inicjalizacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTestSuiteForLayeredServiceUsingAllLesson1315Techniques {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNY pakiet testow DLA WARSTWOWEGO serwisu
         * (powiazanie Z `_17_architecture/Lesson04`), LACZAC WSZYSTKIE
         * techniki Z Lesson13-15 NARAZ.
         */
        public static void main(String[] args) { }
    }
}
