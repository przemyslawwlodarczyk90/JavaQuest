package com.example.javaquest._25_unit_testing.Lesson20_UnitTestingCapstone;

public class _Exercises_Lesson20_UnitTestingCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddQuantityValidationTest {
        /*
         * 🧪 Zadanie 1:
         * Dodaj test SPRAWDZAJACY `placeOrder` Z ILOSCIA rowna
         * `Integer.MAX_VALUE` (przypadek brzegowy, Lesson17).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteTestForSingleLineOrder {
        /*
         * 🧪 Zadanie 2:
         * Napisz test DLA zamowienia Z DOKLADNIE JEDNA pozycja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteTestForOrderWithManyLines {
        /*
         * 🧪 Zadanie 3:
         * Napisz test DLA zamowienia Z 5+ pozycjami - zweryfikuj
         * POPRAWNA sume.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddDisplayNameToAllTestsInGivenOutOfStock {
        /*
         * 🧪 Zadanie 4:
         * Dodaj `@DisplayName` DO KAZDEGO testu W
         * `GivenOutOfStock` (jesli brakuje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RunOnlySlowTaggedTestsAndObserveCount {
        /*
         * 🧪 Zadanie 5:
         * Uruchom testy kapsztonu Z `TagFilter.includeTags("slow")` I
         * zweryfikuj LICZBE URUCHOMIONYCH.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyPaymentGatewayCalledWithCorrectTotal {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `verify(paymentGatewayMock).charge(...)` Z DOKLADNA,
         * OCZEKIWANA suma.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestMultipleOutOfStockLinesReturnsFirstFailure {
        /*
         * 🧪 Zadanie 7:
         * Napisz test Z 2 pozycjami NIEDOSTEPNYMI NARAZ - zweryfikuj,
         * KTORA (pierwsza) jest zglaszana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddTestForZeroTotalOrder {
        /*
         * 🧪 Zadanie 8:
         * Napisz test DLA zamowienia, GDZIE `unitPrice=0` DLA
         * WSZYSTKICH pozycji (total=0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyInjectMocksWorksWithThreeDependencies {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, JAK `@InjectMocks` ZNALAZL
         * WLASCIWY konstruktor DLA `OrderService` Z 3 zaleznosciami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RunCapstoneTestsAndCountBySection {
        /*
         * 🧪 Zadanie 10:
         * Policz (PRZEZ Launcher API) ILE testow jest W KAZDEJ Z 4
         * grup `@Nested`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddCancelOrderMethodWithOwnTestSuite {
        /*
         * 🧪 Zadanie 11:
         * Dodaj metode `cancelOrder(String orderId)` DO
         * `OrderService` I NAPISZ DLA NIEJ WLASNY pakiet testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddDiscountCodeParameterWithParameterizedTests {
        /*
         * 🧪 Zadanie 12:
         * Dodaj OPCJONALNY parametr "kod rabatowy" DO `placeOrder` I
         * PRZETESTUJ go `@ParameterizedTest`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ReplaceInventoryMockWithFakeImplementation {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z Lesson16 - ZASTAP `inventoryMock` WLASNYM FAKE
         * (`HashMap` W srodku) W JEDNYM Z testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddAssumptionSkippingSlowTestsOnCi {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z Lesson11 - dodaj `Assumptions.assumeFalse(...)`
         * POMIJAJACY `@RepeatedTest` NA symulowanym CI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestConcurrentOrderPlacementForSameCustomer {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_05_multithreading` - napisz test SYMULUJACY 2
         * ROWNOLEGLE zamowienia TEGO SAMEGO klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddCoverageMeasurementForOrderServicePlaceOrder {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z Lesson19 - zinstrumentuj `OrderService.placeOrder`
         * I ZMIERZ pokrycie PO uruchomieniu WSZYSTKICH testow
         * kapsztonu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RefactorOrderServiceTestNamingToConsistentConvention {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z Lesson18 - sprawdz WSZYSTKIE nazwy metod
         * testowych W kapsztonie POD KATEM SPOJNEJ konwencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddArgumentCaptorVerifyingAllInventoryChecks {
        /*
         * 🧪 Zadanie 18:
         * Uzyj `ArgumentCaptor` DO przechwycenia WSZYSTKICH wywolan
         * `isInStock` PRZY zamowieniu Z WIELOMA pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestOrderServiceWithRealInMemoryImplementationsNoMocks {
        /*
         * 🧪 Zadanie 19:
         * Napisz WARIANT testow kapsztonu BEZ Mockito - WYLACZNIE Z
         * WLASNYMI implementacjami in-memory (Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureAndReportTotalTestSuiteExecutionTime {
        /*
         * 🧪 Zadanie 20:
         * Zmierz CALKOWITY czas wykonania kapsztonu (Launcher API) I
         * WYPISZ raport.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AddInventoryReservationWithTimeoutAndTestExpiry {
        /*
         * 🧪 Zadanie 21:
         * Dodaj mechanizm "rezerwacji" produktu Z WYGASNieciem CZASOWYM
         * I NAPISZ PELNY pakiet testow (powiazanie Z
         * `_01_fundamentals/Lesson07`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementEventPublishingOnOrderConfirmedAndTestListener {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_17_architecture/Lesson18` - dodaj publikacje
         * zdarzenia `OrderConfirmed` I PRZETESTUJ interakcje Z
         * subskrybentem (mock).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RefactorToHexagonalPortsAndAdaptersAndRetest {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_17_architecture/Lesson12_PortsAndAdapters` -
         * przeorganizuj `OrderService` NA porty/adaptery I DOSTOSUJ
         * testy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRetryLogicForPaymentGatewayAndTestAllAttempts {
        /*
         * 🧪 Zadanie 24:
         * Dodaj retry (3 proby) DLA `paymentGateway.charge(...)` I
         * NAPISZ testy DLA WSZYSTKICH scenariuszy (sukces NA 1/2/3
         * probie, porazka WSZYSTKICH).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildFullMutationTestingCheckForOrderService {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z Lesson17/19 - RECZNIE "zmutuj" 3 miejsca W
         * `OrderService` I zweryfikuj, ze WSZYSTKIE mutacje SA
         * WYKRYWANE przez ISTNIEJACE testy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignContractTestBetweenOrderServiceAndRealAdapters {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj test KONTRAKTOWY (zapowiedz
         * `_26_integration_testing/Lesson13`) DLA `PaymentGateway` -
         * ten sam zestaw testow URUCHAMIANY NA mocku I NA (symulowanej)
         * prawdziwej implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFullAuditTrailWithHashChainAndTestTamperDetection {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19_SecureLoggingAndAuditing` -
         * rozszerz `AuditLogger` O lancuch skrotow I PRZETESTUJ
         * wykrywanie manipulacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildPerformanceRegressionTestUsingRepeatedTest {
        /*
         * 🧪 Zadanie 28:
         * Uzyj `@RepeatedTest(100)` DO wykrycia REGRESJI
         * WYDAJNOSCIOWEJ (SREDNI czas wykonania PONIZEJ progu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignFullTestPyramidForOrderServiceAcrossThreeChapters {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (W komentarzu) PELNA "piramide testow" DLA
         * `OrderService`: jednostkowe (TEN rozdzial) + integracyjne
         * (`_26`) + Spring (`_27`) - CO NALEZY DO KTOREJ warstwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteRetrospectiveOnWhatMakesThisTestSuiteGoodOrBad {
        /*
         * 🧪 Zadanie 30:
         * Bez terminala: napisz KRYTYCZNA retrospektywe (W komentarzu)
         * PAKIETU testow Z tej lekcji - CO jest DOBRE, CO MOZNA
         * POPRAWIC, wzorem `_16_clean_code/Lesson22_CodeReview`.
         */
        public static void main(String[] args) { }
    }
}
