package com.example.javaquest._16_clean_code.Lesson16_RefactoringCatalog;

public class _Exercises_Lesson16_RefactoringCatalog {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllEightTechniquesWithOneLineDescription {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) 8
         * technik refaktoringu z tej lekcji, kazda z 1-zdaniowym opisem, co
         * robi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ApplyExtractMethodToGivenLongMethod {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `calculateInvoiceTotal(double amount, int quantity)`
         * laczaca obliczenie sumy, rabatu ilosciowego i VAT w 1 metodzie.
         * Zastosuj Extract Method, wydzielajac KAZDY krok do osobnej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ApplyExtractClassToGivenLargeClass {
        /*
         * 🧪 Zadanie 3:
         * Napisz klase `ProductWithEverything` laczaca dane produktu I
         * formatowanie etykiety cenowej I formatowanie opisu SEO (3
         * odpowiedzialnosci). Zastosuj Extract Class, wydzielajac
         * PriceLabelFormatter i SeoDescriptionFormatter jako osobne klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ApplyIntroduceParameterObjectToGivenMethod {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `createUserAccount(String firstName, String lastName,
         * String email, String phone)` (4 parametry). Zastosuj Introduce
         * Parameter Object, wprowadzajac record `ContactInfo`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ApplyRenameMethodToPoorlyNamedMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `doIt(List<Integer> data)` liczaca srednia arytmetyczna.
         * Zastosuj Rename Method - zmien nazwe na `calculateAverage` (bez
         * zmiany logiki) i porownaj czytelnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ApplyReplaceMagicNumberToGivenCalculation {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode liczaca cene biletu z "magiczna liczba" 0.5 (rabat 50%
         * dla dzieci) bez zadnego wyjasnienia. Zastosuj Replace Magic Number
         * with Symbolic Constant, wprowadzajac `CHILD_DISCOUNT_RATE`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ApplyGuardClausesToGivenNestedConditional {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode z 3 zagniezdzonymi warunkami sprawdzajaca
         * kwalifikowalnosc do pozyczki (wiek, dochod, historia kredytowa).
         * Zastosuj Guard Clauses, splaszczajac zagniezdzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ApplyMoveMethodToGivenFeatureEnvyExample {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode zewnetrzna liczaca "cene koncowa produktu" siegajaca
         * do 3 pol klasy Product (price, taxRate, discountRate). Zastosuj Move
         * Method, przenoszac logike DO klasy Product jako jej wlasna metoda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyWhichTechniqueFixesWhichSmellFromLesson14 {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: dopasuj w komentarzu (bez podgladania teorii) KAZDA z
         * 8 technik z tej lekcji do JEDNEGO ze smelli z Lesson14, ktory
         * naprawia (moze byc wiecej niz 1 technika na smell).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ApplyReplaceConditionalWithPolymorphismToSimpleExample {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode liczaca "opłate za typ czlonkostwa" (BASIC/PREMIUM)
         * przez if/else. Zastosuj Replace Conditional with Polymorphism -
         * interfejs MembershipFee z 2 implementacjami.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChainExtractMethodAndReplaceMagicNumberTogether {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `calculateLatePenalty(int daysLate, double
         * baseAmount)` z Long Method + magiczna liczba (0.05 = 5% za dzien).
         * Zastosuj NAJPIERW Extract Method (wydziel obliczenie), POTEM Replace
         * Magic Number - w tej kolejnosci, jako 2 osobne, male kroki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ApplyExtractClassToSplitGodClassIntoTwoParts {
        /*
         * 🧪 Zadanie 12:
         * Napisz "god class" `ReportEverything` laczaca dane raportu I
         * logike eksportu do CSV I logike eksportu do JSON (3
         * odpowiedzialnosci). Zastosuj Extract Class DWUKROTNIE - rozbij na 3
         * male klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ApplyIntroduceParameterObjectWithValidationInConstructor {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode `bookFlight(String passengerName, String passportNumber,
         * String seatClass, int seatNumber)` (4 parametry). Zastosuj Introduce
         * Parameter Object z WALIDACJA w konstruktorze recordu (np.
         * passportNumber niepusty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ApplyReplaceConditionalWithPolymorphismForShippingCost {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode liczaca "koszt wysylki" wg typu przesylki (STANDARD/
         * EXPRESS/OVERNIGHT) przez switch. Zastosuj Replace Conditional with
         * Polymorphism - interfejs ShippingCostPolicy z 3 implementacjami.
         * Dodaj CZWARTY typ (np. INTERNATIONAL) BEZ modyfikacji istniejacych
         * klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ApplyGuardClausesToDeeplyNestedValidation {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode walidujaca formularz rejestracji z 4 ZAGNIEZDZONYMI
         * warunkami (username, email, password, age). Zastosuj Guard Clauses -
         * kazdy niepoprawny przypadek zwraca NATYCHMIAST, bez zagniezdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ApplyMoveMethodAcrossThreeRelatedClasses {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode zewnetrzna liczaca "czy zamowienie przekracza limit
         * wagowy magazynu" siegajaca do pol klas Order i Warehouse. Zastosuj
         * Move Method, przenoszac logike do klasy Warehouse (ktora ma limit)
         * jako `canAccept(Order order)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ApplyRenameToMisleadingBooleanMethod {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode `boolean check(int age)` sprawdzajaca czy wiek
         * uprawnia do emerytury (>=65) - nazwa "check" jest niejasna. Zastosuj
         * Rename Method na `isEligibleForRetirement`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CombineIntroduceParameterObjectAndExtractMethod {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode `calculateHotelBill(String guestName, int nights,
         * double roomRate, double taxRate, boolean breakfastIncluded)` (5
         * parametrow + logika obliczen razem). Zastosuj OBIE techniki: Introduce
         * Parameter Object (BookingDetails) ORAZ Extract Method (rozbij
         * obliczenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifyMultipleApplicableTechniquesForOneMethod {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode `processTransaction(String accountId, String type,
         * double amount, boolean isInternational)` (Long Method + Long
         * Parameter List + magiczna liczba oplaty). W komentarzu wskaz
         * WSZYSTKIE techniki z tej lekcji, ktore da sie tu zastosowac (min. 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ApplyAllIdentifiedTechniquesFromExercise19 {
        /*
         * 🧪 Zadanie 20:
         * Zastosuj WSZYSTKIE techniki wskazane w Zadaniu 19 do metody z tego
         * zadania, KROK PO KROKU (kazda technika = osobny, weryfikowany krok).
         * Wypisz wynik po kazdym kroku, potwierdzajac brak regresji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorRealisticGodClassUsingFourTechniques {
        /*
         * 🧪 Zadanie 21:
         * Napisz realistyczna "god class" `OrderSystemEverything` laczaca:
         * dane zamowienia (Long Parameter List w konstruktorze), obliczenia z
         * magicznymi liczbami, warunek typu klienta przez if/else, oraz
         * zagniezdzona walidacje. Zastosuj WSZYSTKIE 4 pasujace techniki
         * (Introduce Parameter Object, Replace Magic Number, Replace
         * Conditional with Polymorphism, Guard Clauses) - w OSOBNYCH, malych
         * krokach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_VerifyEachStepOfExercise21WithIdenticalOutput {
        /*
         * 🧪 Zadanie 22:
         * Dla KAZDEGO z 4 krokow refaktoryzacji z Zadania 21, wywolaj cala
         * funkcjonalnosc PRZED i PO danym kroku i porownaj wyniki
         * programowo (`.equals()`) - wypisz PASS/FAIL dla kazdego kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignPluginStyleSystemUsingReplaceConditionalWithPolymorphism {
        /*
         * 🧪 Zadanie 23:
         * Napisz system liczenia "podatku dochodowego" wg 4 progow podatkowych
         * przez zagniezdzone if/else z magicznymi liczbami progow. Zastosuj
         * Replace Conditional with Polymorphism (hierarchia TaxBracket) ORAZ
         * Replace Magic Number (nazwane stale progow) razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AddNewTaxBracketWithoutModifyingExistingCode {
        /*
         * 🧪 Zadanie 24:
         * Korzystajac z rozwiazania z Zadania 23, dodaj NOWY prog podatkowy
         * jako NOWA klase, BEZ modyfikacji zadnej z istniejacych klas -
         * zademonstruj dzialanie calego systemu z nowym progiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorRealMethodFromEarlierChapterUsingCatalogTechniques {
        /*
         * 🧪 Zadanie 25:
         * Wybierz 1 metode z dowolnej wczesniejszej lekcji kursu (np.
         * `_10_dao`) ze smellem (Long Method/Long Parameter List/magiczna
         * liczba). Skopiuj ja i zrefaktoryzuj, stosujac min. 2 techniki z tej
         * lekcji - wypisz porownanie PRZED/PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignCompleteBeforeAfterShowcaseForFiveTechniques {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj JEDNA realistyczna klase (np. system zamowien w kawiarni)
         * naruszajaca 5 ROZNYCH smelli naraz (po 1 na kazda z 5 wybranych
         * technik) - napisz jej PELNA, poprawiona wersje stosujaca WSZYSTKIE 5
         * technik. Zademonstruj identyczne dzialanie przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildDecisionTreeForChoosingRightTechnique {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: napisz w komentarzu "drzewo decyzyjne" (min. 5
         * galezi) pomagajace wybrac WLASCIWA technike z tej lekcji na
         * podstawie ROZPOZNANEGO smellu (np. "jesli widzisz powtarzany
         * if/else po typie -> Replace Conditional with Polymorphism").
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RefactorLegacyMethodPreservingBehaviorWithCharacterizationTest {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode z NIEOCZYWISTA logika (min. 3 smelle naraz) i test
         * charakteryzujacy (patrz Lesson15) pokrywajacy min. 5 przypadkow.
         * Zastosuj min. 3 techniki z tej lekcji, weryfikujac test
         * charakteryzujacy PO KAZDEJ z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveRefactoringCatalogReferenceCard {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu WLASNA "karte referencyjna" (min.
         * 8 wpisow - po 1 na kazda technike) zawierajaca: nazwe techniki,
         * smell ktory naprawia, i 1-zdaniowy opis KROKOW wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneMultiTechniqueRefactoringOfRealisticSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz REALISTYCZNY, "brudny" system
         * rezerwacji stolikow w restauracji laczacy WSZYSTKIE 8 smelli
         * mozliwych do naprawienia technikami z tej lekcji (Long Method, Large
         * Class, Long Parameter List, powtarzany warunek po typie, zla nazwa,
         * magiczna liczba, zagniezdzone warunki, feature envy). Zastosuj
         * WSZYSTKIE 8 technik krok po kroku, weryfikujac brak regresji po
         * kazdym kroku, i wypisz finalne porownanie PRZED/PO.
         */
        public static void main(String[] args) { }
    }
}
