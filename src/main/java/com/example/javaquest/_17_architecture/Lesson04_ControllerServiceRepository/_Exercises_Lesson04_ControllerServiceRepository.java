package com.example.javaquest._17_architecture.Lesson04_ControllerServiceRepository;

public class _Exercises_Lesson04_ControllerServiceRepository {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainResponsibilityOfEachLayerInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania, po 1 na warstwe) dokladna odpowiedzialnosc Controller,
         * Service i Repository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteThinControllerDelegatingToService {
        /*
         * 🧪 Zadanie 2:
         * Napisz "chudy" kontroler (metoda symulujaca zadanie) delegujacy
         * CALA logike (np. rejestracja produktu w magazynie) do warstwy
         * Service - kontroler ma TYLKO tlumaczyc wejscie/wyjscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteFatControllerContainingBusinessRule {
        /*
         * 🧪 Zadanie 3:
         * Napisz "gruby" kontroler SAM sprawdzajacy regule biznesowa (np. "czy
         * cena jest dodatnia") zamiast delegowac do Service - to jest
         * anty-wzorzec z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixFatControllerFromExercise03 {
        /*
         * 🧪 Zadanie 4:
         * Popraw kod z Zadania 3, przenoszac regule do warstwy Service.
         * Zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DesignServiceOrchestratingTwoRepositories {
        /*
         * 🧪 Zadanie 5:
         * Zaprojektuj Service koordynujacy 2 rozne repozytoria (np.
         * `ProductRepository` i `AuditLogRepository`) w 1 metodzie -
         * kontroler zna TYLKO Service.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyAnemicServicePassThroughSignal {
        /*
         * 🧪 Zadanie 6:
         * Napisz Service, ktorego KAZDA metoda TYLKO przekazuje wywolanie do 1
         * repozytorium (bez zadnej wlasnej logiki). W komentarzu oceń, czy to
         * sygnal problemu, czy normalna sytuacja dla prostego CRUD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteControllerBypassingServiceToUseRepositoryDirectly {
        /*
         * 🧪 Zadanie 7:
         * Napisz kontroler uzywajacy Repository BEZPOSREDNIO (pomijajac
         * Service) "bo to prosty odczyt" - to jest anty-wzorzec
         * "przeciekajace repozytorium" z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixRepositoryLeakingFromExercise07 {
        /*
         * 🧪 Zadanie 8:
         * Popraw kod z Zadania 7, kierujac WSZYSTKIE wywolania przez Service
         * (nawet ten "prosty odczyt") - zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DesignRepositoryWithZeroBusinessDecisions {
        /*
         * 🧪 Zadanie 9:
         * Zaprojektuj Repository z metodami CZYSTO technicznymi (save/
         * findById/count) - w komentarzu potwierdz, ze ZADNA z metod nie
         * podejmuje decyzji "czy wolno" (to zadanie Service).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListTwoAntiPatternsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) OBA
         * anty-wzorce z tej lekcji z 1-zdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignTwoControllersSharingOneServiceForSameRule {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj 2 ROZNE "kontrolery" (np. REST-podobny i CLI-podobny)
         * dla systemu rezerwacji sali - OBA delegujace do TEGO SAMEGO
         * Service. Zademonstruj, ze regula biznesowa (np. "sala max 20 osob")
         * dziala IDENTYCZNIE z obu wejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureDuplicationCostOfFatControllerWithSecondEntryPoint {
        /*
         * 🧪 Zadanie 12:
         * Napisz "gruby" kontroler z regula biznesowa - dodaj DRUGI punkt
         * wejscia (inny kontroler) potrzebujacy TEJ SAMEJ reguly. W komentarzu
         * policz, ile razy regula musiala zostac POWIELONA vs ile razy
         * bylaby potrzebna, gdyby zyla w Service.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignServiceAsTransactionBoundaryPreview {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj Service z metoda wywolujaca 2 operacje na 2 roznych
         * repozytoriach, ktore POWINNY sie udac lub zawiesc RAZEM (np.
         * "zdejmij ze stanu magazynowego" + "zapisz zamowienie") - w
         * komentarzu opisz (bez implementowania jeszcze prawdziwych transakcji,
         * to Lesson13), dlaczego ta koordynacja NALEZY do Service, nie do
         * Controllera ani Repository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyInconsistentArchitectureFromPartialRepositoryLeak {
        /*
         * 🧪 Zadanie 14:
         * Napisz system, gdzie 3 z 4 operacji przechodza przez Service, a 1
         * (dla "wygody") uzywa Repository bezposrednio. Zademonstruj, jak
         * TA JEDNA operacja "gubi" walidacje, ktora maja pozostale 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorInconsistentArchitectureFromExercise14 {
        /*
         * 🧪 Zadanie 15:
         * Popraw system z Zadania 14, kierujac WSZYSTKIE 4 operacje przez
         * Service - zweryfikuj, ze walidacja teraz dziala SPOJNIE wszedzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignServiceOrchestratingValidationAndTwoRepositories {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj Service dla "przeniesienia srodkow miedzy kontami" -
         * musi: zwalidowac (saldo wystarczajace), wywolac repository konta
         * zrodlowego, wywolac repository konta docelowego - wszystko w 1
         * metodzie Service, kontroler zna TYLKO ta 1 metode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareThinVsFatControllerForSameFeatureSideBySide {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj TA SAMA funkcjonalnosc (np. "dodaj recenzje produktu, z
         * limitem 1 recenzji na uzytkownika") jako (a) gruby kontroler, (b)
         * chudy kontroler + Service. Wywolaj OBIE dla tego samego scenariusza i
         * porownaj czytelnosc/lokalizacje logiki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignRepositoryInterfaceUsedByMultipleServices {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj 1 `ProductRepository` uzywane przez 2 ROZNE serwisy
         * (np. `InventoryService` i `PricingService`) - zademonstruj, ze
         * Repository NIE zawiera zadnej logiki specyficznej dla KTOREGOKOLWIEK
         * z tych 2 zastosowan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AuditRealCodeFromDaoChapterForControllerServiceBoundaries {
        /*
         * 🧪 Zadanie 19:
         * Wybierz 1 fragment z `_10_dao` (inny niz Lesson02) i w komentarzu
         * oceń, czy respektuje granice Controller/Service/Repository z tej
         * lekcji (czy jest tam odpowiednik "grubego kontrolera" lub
         * "przeciekajacego repozytorium").
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignAdrJustifyingServiceLayerForSimpleCrud {
        /*
         * 🧪 Zadanie 20:
         * Napisz ADR (`Lesson02`) uzasadniajacy DECYZJE "czy dla prostego CRUD
         * (bez zadnej logiki biznesowej) warto miec osobna warstwe Service, czy
         * kontroler moze wolac Repository bezposrednio" - rozwaz OBIE strony.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticECommerceCheckoutAcrossThreeLayers {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny przeplyw "checkout" sklepu internetowego:
         * kontroler (tlumaczy zadanie), Service (waliduje koszyk, liczy sume,
         * koordynuje `InventoryRepository` i `OrderRepository`), Repository
         * (tylko przechowywanie). Zademonstruj udany i nieudany checkout
         * (np. brak towaru).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorRealisticFatControllerIntoProperArchitecture {
        /*
         * 🧪 Zadanie 22:
         * Napisz REALISTYCZNY "gruby" kontroler dla systemu z Zadania 21 (CALA
         * logika checkout w kontrolerze) - w PELNI zrefaktoryzuj na wlasciwe 3
         * warstwy, zachowujac identyczne zachowanie dla obu scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignMultiControllerSystemWithSharedServiceLayer {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj system z 3 ROZNYMI "kontrolerami" (REST-podobny,
         * CLI-podobny, "wewnetrzny batch job"-podobny) dla TEGO SAMEGO systemu
         * zamowien - wszystkie 3 dziela DOKLADNIE te sama warstwe Service.
         * Zademonstruj spojne dzialanie reguly biznesowej ze WSZYSTKICH 3 wejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildArchitectureConsistencyCheckerForLayerViolations {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> auditArchitecture(int controllersWithBusinessLogic,
         * int controllersUsingRepositoryDirectly, int totalControllers)`
         * zwracajaca liste ostrzezen na podstawie prostych liczb - przetestuj
         * dla min. 3 roznych kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignServiceLayerHandlingCrossCuttingLoggingConsistently {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj Service, ktory dla WSZYSTKICH swoich metod jednolicie
         * loguje wejscie/wyjscie (przekroj poprzeczny) - zademonstruj, ze
         * DZIEKI temu, ze WSZYSTKIE operacje przechodza przez Service, logowanie
         * jest SPOJNE (w odroznieniu od systemu z czesciowym repository leak).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareArchitectureQualityAcrossTwoTeamsSimulatedDescriptions {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz w komentarzu 2 KROTKIE "opisy zespolu" - jeden
         * konsekwentnie stosujacy granice Controller/Service/Repository,
         * drugi z regularnymi "skrotami" (gruby kontroler, repository leak) -
         * opisz (min. 4 zdania), jak KAZDY zespol poradzi sobie za rok z nowym
         * wymaganiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignServiceDelegatingToMultipleRepositoriesWithPartialFailureHandling {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj Service koordynujacy 3 repozytoria dla zlozonej operacji
         * (np. "zloz zamowienie": rezerwacja stanu, zapis zamowienia, zapis
         * faktury) - zaimplementuj (bez prawdziwych transakcji, to Lesson13)
         * prosta obsluge, gdy 2. krok zawiedzie (np. rzucajac wyjatek z jasnym
         * komunikatem, `_16_clean_code/Lesson17`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrForChoosingWhereValidationLivesAcrossLayers {
        /*
         * 🧪 Zadanie 28:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "gdzie w architekturze
         * Controller/Service/Repository ma zyc walidacja formatu wejscia
         * (np. 'pole nie moze byc puste') a gdzie walidacja reguł biznesowych" -
         * to wprowadzenie do Lesson15 (ValidationArchitecture).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveControllerServiceRepositoryChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do audytu granic Controller/Service/Repository w realnym
         * projekcie - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneMultiEntryPointSystemWithStrictBoundaries {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny system
         * (np. system zarzadzania zgloszeniami serwisowymi) z: min. 2 ROZNYMI
         * "kontrolerami" dzielacymi 1 warstwe Service, Service koordynujacym
         * min. 2 repozytoria, ZERO logiki biznesowej w kontrolerach, ZERO
         * bezposredniego dostepu do repozytoriow z kontrolerow. Zademonstruj
         * pelny, spojny przeplyw z OBU punktow wejscia.
         */
        public static void main(String[] args) { }
    }
}
