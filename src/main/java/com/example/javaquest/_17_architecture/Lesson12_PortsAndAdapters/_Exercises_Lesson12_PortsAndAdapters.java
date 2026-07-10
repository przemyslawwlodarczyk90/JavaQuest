package com.example.javaquest._17_architecture.Lesson12_PortsAndAdapters;

public class _Exercises_Lesson12_PortsAndAdapters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainContractTestingInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), czym jest "contract testing" i po co uruchamiac TEN SAM
         * test dla roznych adapterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DesignPackageStructureForGivenFeature {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: narysuj (jako tekst w komentarzu, jak w teorii)
         * strukture pakietow `in`/`out`/`application` dla funkcji
         * "Powiadomienia".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteSimpleContractTestForOnePort {
        /*
         * 🧪 Zadanie 3:
         * Zaprojektuj port `CounterPort` (increment/getValue) i napisz
         * metode `contractTest(CounterPort port)` sprawdzajaca podstawowy
         * kontrakt (increment zwieksza wartosc o 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementTwoAdaptersPassingSameContractTest {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj 2 rozne adaptery `CounterPort` z Zadania 3 -
         * uruchom contract test z Zadania 3 dla OBU i wypisz PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyCompositionRootInGivenCodeSnippet {
        /*
         * 🧪 Zadanie 5:
         * Dany jest kod z `new KonkretnyAdapter()` w JEDNYM, centralnym
         * miejscu (metoda main-podobna). W komentarzu wskaz, dlaczego TO jest
         * composition root.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteLeakyAdapterWithBusinessLogic {
        /*
         * 🧪 Zadanie 6:
         * Napisz adapter zawierajacy WLASNA regule biznesowa (np. walidacje
         * formatu) zamiast tylko tlumaczyc dane - to jest anty-wzorzec z
         * teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FixLeakyAdapterFromExercise06 {
        /*
         * 🧪 Zadanie 7:
         * Popraw adapter z Zadania 6, przenoszac regule biznesowa do
         * warstwy aplikacji (Service) - adapter ma TYLKO tlumaczyc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignSinglePortGroupingCoherentOperations {
        /*
         * 🧪 Zadanie 8:
         * Zaprojektuj 1 port `InventoryPort` grupujacy 3 SPOJNE operacje
         * (reserve/release/checkAvailability) - w komentarzu wyjasnij,
         * dlaczego NIE dzielisz go na 3 osobne porty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyTooGranularPortDesign {
        /*
         * 🧪 Zadanie 9:
         * Dany jest opis: system ma OSOBNY port dla KAZDEJ z 6 metod
         * dotyczacych 1 zasobu (np. `SaveOrderPort`, `FindOrderPort`,
         * `DeleteOrderPort`...). W komentarzu oceń, czy to nadmierne
         * rozdrobnienie i zaproponuj konsolidacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListPracticalGuidelinesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * praktyczne wskazowki z tej lekcji (struktura pakietow, contract
         * testing, composition root).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFullPackageStructureForThreePortSystem {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj (jako tekst + symulacja zagniezdzonymi klasami) pelna
         * strukture `in`/`out`/`application` dla systemu z 1 portem driving i
         * 2 portami driven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteContractTestCoveringMultipleBehaviors {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz test kontraktowy z Zadania 3-4 o DODATKOWE zachowania
         * (np. "increment wielokrotny daje poprawna sume") - uruchom
         * rozszerzony test dla 3 roznych implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignCompositionRootSelectingAdapterByConfiguration {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj composition root wybierajacy adapter na podstawie
         * "konfiguracji" (np. String parametr "dev"/"prod") - zademonstruj
         * wybor ROZNEGO adaptera dla kazdej wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignPortGroupingByExternalSystemNotByMethod {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj 2 porty driven dla 2 ROZNYCH zewnetrznych systemow
         * (np. `PaymentPort` i `ShippingPort`) - kazdy z 2-3 SPOJNYMI
         * metodami - w komentarzu uzasadnij podzial WEDLUG SYSTEMU, nie
         * wedlug metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrForPackageNamingConventionInOut {
        /*
         * 🧪 Zadanie 15:
         * Napisz PELNY ADR (`Lesson02`) przyjmujacy konwencje pakietow
         * `adapter.in.*`/`adapter.out.*` jako standard zespolowy - z
         * uzasadnieniem i konsekwencjami (np. latwiejsze wdrazanie nowych
         * czlonkow zespolu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorMultiResponsibilityPortIntoFocusedPorts {
        /*
         * 🧪 Zadanie 16:
         * Napisz 1 "boski" port z 8 NIEPOWIAZANYMI metodami (np. mieszajacy
         * zamowienia, platnosci i powiadomienia) - rozbij na 3 male, spojne
         * porty (ISP, `_16_clean_code/Lesson10`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementContractTestDetectingBrokenAdapterImplementation {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj adapter portu z CELOWYM bledem (np. `existsById`
         * zawsze zwraca `false`, nawet po zapisie) - uruchom contract test z
         * Zadania 3/12 i zademonstruj, ze WYKRYWA ten blad (FAIL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignTwoDrivingAdaptersWithDifferentInputParsingLogic {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj 2 adaptery driving dla TEGO SAMEGO portu - jeden
         * parsuje "surowy" String (symulujac JSON), drugi przyjmuje juz
         * gotowe argumenty (symulujac CLI) - obie wersje delegujace do TEJ
         * SAMEJ logiki aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureImpactOfMovingNewCallsToCompositionRoot {
        /*
         * 🧪 Zadanie 19:
         * Napisz system z rozrzuconymi wywolaniami `new KonkretnyAdapter()`
         * w KILKU miejscach - zrefaktoryzuj tak, by WSZYSTKIE trafily do 1
         * composition root. W komentarzu opisz korzysc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromDaoChapterForPortAdapterReadiness {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_10_dao/Lesson11_ConnectionFactory` -
         * czy to juz jest (koncepcyjnie) "composition root" dla polaczen z
         * baza danych? Uzasadnij.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullContractTestSuiteForRealisticRepositoryPort {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj port `ProductRepositoryPort` (save/findById/deleteById)
         * z PELNYM zestawem testow kontraktowych (min. 4 scenariusze:
         * zapis+odczyt, usuniecie, odczyt nieistniejacego, podwojny zapis) -
         * uruchom dla 2 roznych adapterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementThirdAdapterAndVerifyAgainstExistingContractSuite {
        /*
         * 🧪 Zadanie 22:
         * Dodaj TRZECI adapter do systemu z Zadania 21 (np. symulujacy cache)
         * - uruchom ISTNIEJACY zestaw testow kontraktowych bez zadnej
         * modyfikacji testow, tylko z nowym adapterem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignCompositionRootWithFallbackAdapterOnFailure {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj composition root, ktory PRoBUJE wybrac "preferowany"
         * adapter, a w razie jego niedostepnosci (symulowanej) uzywa
         * adaptera zapasowego (fallback) - zademonstruj obie sciezki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildPortDesignReviewChecklistAsExecutableValidator {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> reviewPortDesign(int methodCount,
         * boolean groupedByExternalSystem, boolean adapterContainsBusinessLogic)`
         * zwracajaca liste ostrzezen na podstawie prostych metryk projektu
         * portu - przetestuj dla min. 3 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorRealisticSystemToProperPortAdapterOrganization {
        /*
         * 🧪 Zadanie 25:
         * Napisz REALISTYCZNY system z: `new` rozrzuconym w wielu miejscach,
         * adapterem z logika biznesowa, i 1 "boskim" portem - w PELNI
         * zrefaktoryzuj do: composition root, czystych adapterow, malych
         * spojnych portow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignMultiEnvironmentCompositionRootForRealisticApp {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj composition root obslugujacy 3 "srodowiska" (dev/test/
         * prod), KAZDE z INNYM zestawem adapterow dla WSZYSTKICH portow
         * systemu (min. 2 porty) - zademonstruj wybor pelnego zestawu dla
         * kazdego srodowiska.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForAdoptingContractTestingAsTeamStandard {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR przyjmujacy contract testing jako STANDARD
         * zespolowy dla WSZYSTKICH nowych portow - z kontekstem (np. "mielismy
         * incydent, gdzie nowy adapter zachowywal sie inaczej niz stary") i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignPortHierarchyRespectingBothIspAndSingleExternalSystemRule {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj system z 4 zewnetrznymi systemami (baza, platnosci,
         * powiadomienia, analityka) - dla KAZDEGO 1 port, z metodami
         * ograniczonymi do tego, czego RZECZYWISCIE potrzebuje aplikacja
         * (ISP) - zademonstruj wszystkie 4 porty w uzyciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensivePortsAndAdaptersPracticalGuideline {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu KOMPLETNY, praktyczny
         * przewodnik (min. 7 punktow) do implementacji portow i adapterow w
         * realnym projekcie - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullPracticalPortsAndAdaptersSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * PRAKTYCZNY system (np. system zarzadzania subskrypcjami) z:
         * pelna struktura pakietow (opisana tekstem), min. 3 portami (mix
         * driving/driven), PELNYM zestawem testow kontraktowych dla KAZDEGO
         * portu driven (min. 2 adaptery na port), 1 composition root
         * wybierajacym WSZYSTKIE adaptery na podstawie "srodowiska", i ZERO
         * logiki biznesowej w KTORYMKOLWIEK adapterze. Zademonstruj pelny,
         * dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
