package com.example.javaquest._26_integration_testing.Lesson01_WhatIsIntegrationTesting;

public class _Exercises_Lesson01_WhatIsIntegrationTesting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteSimpleDatabaseIntegrationCheck {
        /*
         * 🧪 Zadanie 1:
         * Napisz PROSTY test integracyjny Z PRAWDZIWA baza H2 (CREATE
         * TABLE + INSERT + SELECT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteSimpleFileSystemIntegrationCheck {
        /*
         * 🧪 Zadanie 2:
         * Napisz test ZAPISUJACY I odczytujacy PRAWDZIWY plik
         * tymczasowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteSimpleHttpServerIntegrationCheck {
        /*
         * 🧪 Zadanie 3:
         * Postaw PROSTY embedded serwer HTTP I wykonaj DO NIEGO
         * PRAWDZIWE zadanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainDifferenceBetweenUnitAndIntegrationTest {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij (WLASNYMI slowami) ROZNICE miedzy
         * testem jednostkowym A integracyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ClassifyFiveTestsFromCourseAsUnitOrIntegration {
        /*
         * 🧪 Zadanie 5:
         * Wez 5 testow Z `_25_unit_testing` I ZAKLASYFIKUJ, CZY
         * SPELNIALYBY definicje testu integracyjnego, GDYBY dotykaly
         * prawdziwych zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteTestCombiningDatabaseAndFileSystem {
        /*
         * 🧪 Zadanie 6:
         * Napisz test LACZACY baze H2 I system plikow (np. eksport
         * wyniku zapytania DO pliku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MeasureExecutionTimeOfIntegrationVsUnitTest {
        /*
         * 🧪 Zadanie 7:
         * Zmierz CZAS wykonania testu integracyjnego (Zadanie 3)
         * WZGLEDEM testu jednostkowego Z `_25_unit_testing/Lesson13`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyExternalDependenciesInGivenClass {
        /*
         * 🧪 Zadanie 8:
         * Wskaz (W komentarzu) WSZYSTKIE zewnetrzne zaleznosci
         * `OrderService` Z `_25_unit_testing/Lesson20`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteIntegrationTestForJsonHttpEndpoint {
        /*
         * 🧪 Zadanie 9:
         * Rozszerz Zadanie 3 O endpoint zwracajacy PRAWDZIWY JSON
         * (powiazanie Z `_18_rest_api`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListIntegrationTestScenariosForOwnProject {
        /*
         * 🧪 Zadanie 10:
         * Wypisz 5 scenariuszy, KTORE W hipotetycznym projekcie
         * WYMAGALYBY testu integracyjnego (NIE jednostkowego).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteIntegrationTestVerifyingTransactionRollback {
        /*
         * 🧪 Zadanie 11:
         * Napisz test SPRAWDZAJACY, ze BLAD W TRAKCIE transakcji SQL
         * COFA ZMIANY (powiazanie Z `_09_jdbc`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteIntegrationTestForConcurrentFileAccess {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_05_multithreading` - napisz test SPRAWDZAJACY
         * zachowanie PRZY WSPOLBIEZNYM zapisie DO TEGO SAMEGO pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteIntegrationTestUsingRealSchemaFromExistingChapter {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_10_dao` - napisz test integracyjny UZYWAJACY
         * PRAWDZIWEGO schematu tabeli Z tamtego rozdzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareTestPyramidWithTestingTrophy {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: porownaj "piramide testow" Z alternatywnym
         * modelem "test trophy" (Kent C. Dodds, KONCEPCYJNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteIntegrationTestForMultiStepHttpWorkflow {
        /*
         * 🧪 Zadanie 15:
         * Napisz test Z WIELOMA KOLEJNYMI zadaniami HTTP DO TEGO
         * SAMEGO embedded serwera (np. utworz -> pobierz -> usun).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DocumentWhenToPreferIntegrationOverUnitTest {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: opisz KONKRETNY scenariusz, GDZIE test
         * integracyjny WYKRYJE BLAD, KTOREGO test jednostkowy (Z
         * mockiem) NIE wykryje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteIntegrationTestCombiningHttpAndDatabase {
        /*
         * 🧪 Zadanie 17:
         * Napisz test, GDZIE embedded serwer HTTP ZAPISUJE dane DO
         * PRAWDZIWEJ bazy H2 PO otrzymaniu zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureFlakinessRiskOfEachIntegrationExample {
        /*
         * 🧪 Zadanie 18:
         * Oceń (W komentarzu) RYZYKO "flaky" DLA KAZDEGO Z 3
         * przykladow Z lekcji (baza/plik/HTTP) - zapowiedz Lesson12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorUnitTestIntoIntegrationTestVariant {
        /*
         * 🧪 Zadanie 19:
         * Wez test Z ZAMOCKOWANA zaleznoscia (`_25_unit_testing/Lesson13`)
         * I NAPISZ JEGO integracyjny odpowiednik Z PRAWDZIWA
         * implementacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignIntegrationTestSuiteForThreeLayerApp {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj (LISTA scenariuszy) pakiet testow integracyjnych
         * DLA aplikacji controller-service-repository (powiazanie Z
         * `_17_architecture/Lesson04`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementReusableEmbeddedHttpServerTestFixture {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj WLASNA, WIELOKROTNEGO uzytku "fixture" (klasa
         * pomocnicza) DO uruchamiania I zatrzymywania embedded serwera
         * HTTP W testach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDatabaseSchemaResetHelperBetweenTests {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj mechanizm CZYSZCZACY schemat H2 MIEDZY
         * testami (powiazanie Z Lesson11_TestIsolation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureAndCompareStartupCostOfEachIntegrationResource {
        /*
         * 🧪 Zadanie 23:
         * Zmierz KOSZT uruchomienia (czas) KAZDEGO Z 3 zasobow
         * integracyjnych (baza/plik/serwer) OSOBNO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementIntegrationTestHarnessCombiningAllThreeResources {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj PELNY "harness" testowy LACZACY WSZYSTKIE 3 zasoby
         * (baza+plik+serwer) W JEDNYM, spojnym scenariuszu end-to-end.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignIntegrationTestForFailureRecoveryScenario {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj test SPRAWDZAJACY odzyskiwanie PO awarii (np.
         * serwer HTTP przestaje odpowiadac W POLOWIE operacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementIntegrationTestForConfigurationLoading {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_10_dao/Lesson13_EnvironmentVariables` - napisz
         * test SPRAWDZAJACY laczenie kilku ZRODEL konfiguracji NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildTestClassificationToolByScanningAnnotationsInProject {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj PROSTE narzedzie (PRZEZ refleksje) SKANUJACE
         * pakiet `_25_unit_testing`/`_26_integration_testing` I
         * KLASYFIKUJACE klasy WEDLUG uzytych zasobow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignHybridTestUsingRealDbAndMockedHttp {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj test HYBRYDOWY - PRAWDZIWA baza + zamockowany
         * zewnetrzny serwis HTTP (zapowiedz Lesson07 WireMock).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DocumentIntegrationTestCostBenefitAnalysis {
        /*
         * 🧪 Zadanie 29:
         * Napisz (W komentarzu) ANALIZE koszt/korzysc testow
         * integracyjnych DLA WYOBRAZONEGO zespolu 5-osobowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTestingStrategyDecidingUnitVsIntegrationPerLayer {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA strategie - DLA KAZDEJ warstwy aplikacji
         * (kontroler/serwis/repozytorium, powiazanie Z
         * `_17_architecture/Lesson04`) ZDECYDUJ, CZY testowac
         * jednostkowo, CZY integracyjnie, Z UZASADNIENIEM.
         */
        public static void main(String[] args) { }
    }
}
