package com.example.javaquest._25_unit_testing.Lesson04_TestLifecycleAnnotations;

public class _Exercises_Lesson04_TestLifecycleAnnotations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainFourLifecycleAnnotations {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij WSZYSTKIE 4 adnotacje cyklu zycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteTestWithBeforeEachSetup {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase testowa Z `@BeforeEach` PRZYGOTOWUJACYM
         * WSPOLNY obiekt DLA 3 testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ObserveFreshInstancePerTest {
        /*
         * 🧪 Zadanie 3:
         * ZMODYFIKUJ pole W jednym tescie I zweryfikuj, ze KOLEJNY test
         * WIDZI WARTOSC WYJSCIOWA (nowa instancja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteTestWithAfterEachCleanup {
        /*
         * 🧪 Zadanie 4:
         * Dodaj `@AfterEach` WYPISUJACY komunikat "sprzatanie PO
         * tescie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteStaticBeforeAllAndAfterAll {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `@BeforeAll`/`@AfterAll` (statyczne) LICZACE, ILE
         * RAZY zostaly wywolane (oczekiwane: PO 1 RAZIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryNonStaticBeforeAllAndObserveError {
        /*
         * 🧪 Zadanie 6:
         * SPROBUJ zrobic `@BeforeAll` NIE-statyczne BEZ
         * `@TestInstance(PER_CLASS)` - zaobserwuj blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UsePerClassLifecycle {
        /*
         * 🧪 Zadanie 7:
         * Dodaj `@TestInstance(Lifecycle.PER_CLASS)` I NAPRAW Exercise6.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhySharedStateIsRisky {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij RYZYKO WSPOLDZIELONEGO stanu W
         * `PER_CLASS` (zapowiedz Exercise14 - LAMANIE "Independent").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TrackCallOrderInList {
        /*
         * 🧪 Zadanie 9:
         * Zbierz KOLEJNOSC wywolan (jak W teorii) DO statycznej listy
         * I wypisz JA NA KONIEC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteResourceOpenCloseInLifecycle {
        /*
         * 🧪 Zadanie 10:
         * Zasymuluj "otwarcie polaczenia" W `@BeforeEach` I "zamkniecie"
         * W `@AfterEach` (wypisywanie komunikatow).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteTestWithMultipleBeforeEachMethods {
        /*
         * 🧪 Zadanie 11:
         * Dodaj 2 metody `@BeforeEach` W JEDNEJ klasie - zweryfikuj
         * KOLEJNOSC wywolania (NIE gwarantowana JUnit-em, chyba ze
         * `@Order`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementNestedClassWithOwnLifecycle {
        /*
         * 🧪 Zadanie 12:
         * Zapowiedz Lesson09 (`@Nested`) - dodaj ZAGNIEZDZONA klase Z
         * WLASNYM `@BeforeEach`, sprawdz DZIEDZICZENIE lifecycle Z
         * KLASY ZEWNETRZNEJ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteBeforeEachThatThrowsException {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj wyjatek W `@BeforeEach` - zweryfikuj, ze SAM
         * `@Test` W OGOLE NIE zostaje wywolany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstratePerClassStateLeakage {
        /*
         * 🧪 Zadanie 14:
         * Napisz 2 testy W `PER_CLASS`, KTORE (BEZ resetu W
         * `@BeforeEach`) WPLYWAJA NA siebie - zaobserwuj problem
         * "Independent" Z Lesson01.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixStateLeakageWithBeforeEachReset {
        /*
         * 🧪 Zadanie 15:
         * NAPRAW Exercise14 - dodaj `@BeforeEach` RESETUJACY stan W
         * `PER_CLASS`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureLifecycleOverheadPerClassVsPerMethod {
        /*
         * 🧪 Zadanie 16:
         * Porownaj CZAS wykonania 100 testow W `PER_METHOD` (100
         * instancji) Z `PER_CLASS` (1 instancja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDatabaseConnectionSimulationInLifecycle {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_09_jdbc` - zasymuluj OTWARCIE/ZAMKNIECIE polaczenia
         * H2 W `@BeforeAll`/`@AfterAll` (RAZ), Z transakcja
         * ROLLBACK W `@AfterEach` (per test).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhyAfterEachRunsEvenOnFailure {
        /*
         * 🧪 Zadanie 18:
         * Zweryfikuj EMPIRYCZNIE, ze `@AfterEach` WYKONUJE SIE NAWET
         * gdy SAM `@Test` RZUCA wyjatek (gwarancja sprzatania zasobow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCounterAcrossAllLifecyclePhases {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj licznik wywolan KAZDEJ fazy (beforeAll/beforeEach/
         * test/afterEach/afterAll) I wypisz PODSUMOWANIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareWithSpringBeanLifecycle {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: powiaz z `_20_spring_core/Lesson18_
         * LifecycleCallbacks` - porownaj `@BeforeEach`/`@AfterEach` Z
         * `@PostConstruct`/`@PreDestroy`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomLifecycleExtension {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNE rozszerzenie `BeforeEachCallback`/
         * `AfterEachCallback` (JUnit 5 Extension API) ZAMIAST adnotacji
         * NA klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareExtensionApiWithLifecycleAnnotations {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: porownaj `@BeforeEach` (W klasie) Z WLASNYM
         * `Extension` (PONOWNIE UZYWALNY W WIELU klasach) - KIEDY ktory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementTimeoutAcrossLifecycle {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNE rozszerzenie MIERZACE CALKOWITY czas
         * (beforeEach+test+afterEach) I OSTRZEGAJACE, gdy przekracza
         * limit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSharedResourceAcrossTestClasses {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj WSPOLDZIELONY zasob (np. jeden kontener bazy
         * danych) miedzy WIELOMA KLASAMI testowymi (zapowiedz
         * `_26_integration_testing/Lesson06`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRetryLogicViaExtension {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNE rozszerzenie PONAWIAJACE test AUTOMATYCZNIE
         * PRZY niepowodzeniu (max 3 proby) - DLA testow "niestabilnych"
         * (zapowiedz `_26_integration_testing/Lesson12`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureMemoryFootprintOfPerMethodInstances {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_15_jvm_internals` - zmierz ZUZYCIE pamieci PRZY
         * 10000 instancji `PER_METHOD` WZGLEDEM 1 instancji `PER_CLASS`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementConditionalSetupBasedOnTestName {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj `@BeforeEach` UZALEZNIONY OD nazwy metody
         * (`TestInfo` jako parametr) - INNE przygotowanie DLA roznych
         * testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignLifecycleForExpensiveResourceInitialization {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj strategie DLA KOSZTOWNEJ inicjalizacji (np.
         * ladowanie duzego pliku) - `@BeforeAll` (RAZ) vs
         * `@BeforeEach` (kompromis izolacja/wydajnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGlobalExtensionRegisteredViaServiceLoader {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_14_advancedjava/Lesson26` - zarejestruj WLASNE
         * rozszerzenie GLOBALNIE (`junit-platform.properties`,
         * auto-detection), BEZ jawnej adnotacji `@ExtendWith` W KAZDEJ
         * klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullLifecycleAwareTestSuiteWithCleanup {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY zestaw testow (min. 5) Z REALISTYCZNYM
         * scenariuszem (np. testy repozytorium) I PELNYM cyklem zycia
         * (otwarcie zasobu RAZ, transakcja PER test, zamkniecie RAZ NA
         * KONIEC).
         */
        public static void main(String[] args) { }
    }
}
