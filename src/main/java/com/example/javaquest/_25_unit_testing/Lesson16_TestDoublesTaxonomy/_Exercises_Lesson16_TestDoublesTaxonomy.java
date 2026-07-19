package com.example.javaquest._25_unit_testing.Lesson16_TestDoublesTaxonomy;

public class _Exercises_Lesson16_TestDoublesTaxonomy {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IdentifyDummyInExistingTest {
        /*
         * 🧪 Zadanie 1:
         * Wskaz (W komentarzu) DUMMY W jednym Z testow Z Lesson13-15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSimpleFakeRepository {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY FAKE repozytorium (`HashMap` W srodku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteTestUsingFakeRepository {
        /*
         * 🧪 Zadanie 3:
         * Napisz test uzywajacy FAKE Z Zadania 2 (BEZ Mockito).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteStubWithMockitoWhenThenReturn {
        /*
         * 🧪 Zadanie 4:
         * Napisz STUB (mock + `when(...).thenReturn(...)`, BEZ
         * `verify`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteSpyWrappingRealObject {
        /*
         * 🧪 Zadanie 5:
         * Napisz SPY (`spy(prawdziwyObiekt)`) I zweryfikuj JEDNO
         * wywolanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMockVerifyingInteraction {
        /*
         * 🧪 Zadanie 6:
         * Napisz MOCK Z `verify(...)` sprawdzajacym INTERAKCJE
         * (powiazanie Z Lesson13).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDifferenceBetweenStubAndSpy {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij ROZNICE miedzy STUBEM A SPY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDifferenceBetweenFakeAndStub {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij ROZNICE miedzy FAKE A STUB.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ClassifyFiveExamplesFromRealCode {
        /*
         * 🧪 Zadanie 9:
         * Znajdz W tym kursie (`_08_sql`-`_10_dao`) 5 PRZYKLADOW I
         * ZAKLASYFIKUJ kazdy DO jednej Z 5 kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImplementFakeClockForTimeDependentCode {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj FAKE `Clock` (interfejs `now()`) DO testowania
         * kodu ZALEZNEGO OD czasu (powiazanie Z `_01_fundamentals/Lesson07`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementFakeEmailSenderCollectingSentMessages {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj FAKE `EmailSender` ZBIERAJACY WYSLANE
         * wiadomosci DO listy (DO PODGLADU W tescie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteSpyThatPartiallyStubsOneMethod {
        /*
         * 🧪 Zadanie 12:
         * Napisz SPY, GDZIE JEDNA metoda jest zastubowana
         * (`doReturn(...).when(spy).x()`), a RESZTA dziala NAPRAWDE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExplainWhenWhenThenReturnFailsOnSpy {
        /*
         * 🧪 Zadanie 13:
         * Zademonstruj (I wyjasnij), DLACZEGO
         * `when(spy.metoda()).thenReturn(...)` MOZE byc PROBLEMATYCZNE
         * NA spy (metoda MOZE sie FAKTYCZNIE wykonac PODCZAS
         * stubowania) - I DLACZEGO `doReturn(...).when(spy)` jest
         * BEZPIECZNIEJSZE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementDummyThatThrowsIfActuallyUsed {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj "STRICT DUMMY" - mock, KTORY RZUCA wyjatek,
         * JESLI zostanie FAKTYCZNIE uzyty (dowod, ze rzeczywiscie jest
         * "dummy").
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorMockBasedTestToUseFakeInstead {
        /*
         * 🧪 Zadanie 15:
         * Wez test Z MOCKIEM (Lesson13) I PRZEPISZ go NA FAKE - omow
         * (W komentarzu) KOMPROMISY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteTestSuiteMixingFakeAndMockTogether {
        /*
         * 🧪 Zadanie 16:
         * Napisz test uzywajacy FAKE DLA JEDNEJ zaleznosci I MOCKA DLA
         * DRUGIEJ NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementInMemoryEventBusAsFake {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_20_spring_core/Lesson20_ApplicationEvents` -
         * zaimplementuj FAKE event bus (in-memory, prosty
         * publisher/listener).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DiscussWhenToPreferFakeOverMockInTeam {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz, KIEDY zespol POWINIEN preferowac FAKE
         * NAD MOCKIEM (I ODWROTNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementFakeThatSimulatesFailureAfterNCalls {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj FAKE SYMULUJACY awarie PO N udanych
         * wywolaniach (powiazanie Z `_26_integration_testing/Lesson12`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareTestReadabilityAcrossAllFiveDoubleTypes {
        /*
         * 🧪 Zadanie 20:
         * Napisz TEN SAM scenariusz testowy 5 RAZY (dummy/fake/stub/
         * spy/mock) I porownaj CZYTELNOSC/DLUGOSC kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullInMemoryDatabaseFakeWithTransactions {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj ROZBUDOWANY FAKE bazy danych Z prosta obsluga
         * "transakcji" (commit/rollback W pamieci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementContractTestVerifyingFakeMatchesRealBehavior {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj test KONTRAKTOWY (powiazanie Z
         * `_26_integration_testing/Lesson13`) URUCHAMIANY NA FAKE ORAZ
         * NA PRAWDZIWEJ implementacji - potwierdz IDENTYCZNE
         * zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSpyDetectingUnexpectedExtraCalls {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj mechanizm (SPY + licznik) WYKRYWAJACY, ze
         * metoda zostala wywolana WIECEJ razy NIZ oczekiwano.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignTestDoubleSelectionDecisionTree {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj (jako komentarz/ASCII diagram) "drzewo decyzyjne"
         * POMAGAJACE wybrac WLASCIWY rodzaj atrapy DLA danego
         * scenariusza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFakeMessageQueueWithOrderingGuarantee {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_30_spring_messaging_and_async` - zaimplementuj
         * FAKE kolejke wiadomosci GWARANTUJACA kolejnosc FIFO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestPerformanceDifferenceBetweenFakeAndRealDependency {
        /*
         * 🧪 Zadanie 26:
         * Zmierz roznice CZASU wykonania testu Z FAKE WZGLEDEM
         * PRAWDZIWEJ (symulowanej wolnej) zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementHybridDoubleActingAsFakeAndSpy {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WLASNA klase LACZACA cechy FAKE (dziala
         * naprawde) I SPY (zapamietuje wywolania) BEZ Mockito.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DocumentAntiPatternOverusingMocksInsteadOfFakes {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: opisz ANTY-WZORZEC "nadmiernego mockowania"
         * (test SPRAWDZA implementacje, NIE zachowanie) I ZAPROPONUJ
         * naprawe PRZEZ FAKE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementTestDoubleFactoryChoosingTypeByAnnotation {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj WLASNA "fabryke" atrap WYBIERAJACA rodzaj (fake/stub/
         * mock) NA PODSTAWIE parametru/adnotacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTestingStrategyClassifyingEveryDependencyType {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA strategie testowania DLA WARSTWOWEJ
         * aplikacji (powiazanie Z `_17_architecture`), PRZYPISUJAC
         * KAZDEJ zaleznosci WLASCIWY rodzaj atrapy testowej Z
         * UZASADNIENIEM.
         */
        public static void main(String[] args) { }
    }
}
