package com.example.javaquest._17_architecture.Lesson15_ValidationArchitecture;

public class _Exercises_Lesson15_ValidationArchitecture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeValidationLevelsInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania, po 1 na poziom) 3 rodzaje walidacji z tej lekcji i GDZIE
         * kazdy powinien zyc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteFormatValidationForSimpleDto {
        /*
         * 🧪 Zadanie 2:
         * Napisz `record CreateProductRequestDto(String name, double price)`
         * i metode walidujaca FORMAT (name niepuste, price > 0) - bez
         * zadnego dostepu do repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteRichEntityWithDomainInvariant {
        /*
         * 🧪 Zadanie 3:
         * Napisz bogata encje `Product` z niezmiennikiem "price > 0"
         * egzekwowanym w konstruktorze (rzuca wyjatek dla ceny <= 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteBusinessRuleRequiringRepository {
        /*
         * 🧪 Zadanie 4:
         * Napisz Service z regula biznesowa "nazwa produktu musi byc
         * unikalna" WYMAGAJACA zapytania do prostego repozytorium
         * in-memory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DemonstrateAllThreeLevelsInOneFlow {
        /*
         * 🧪 Zadanie 5:
         * Polacz rozwiazania z Zadan 2-4 w 1 przeplyw: format -> niezmiennik
         * -> regula biznesowa - zademonstruj udana rejestracje produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyWhichLevelCatchesGivenInvalidInput {
        /*
         * 🧪 Zadanie 6:
         * Dla 3 przypadkow niepoprawnych danych (pusta nazwa, ujemna cena,
         * zduplikowana nazwa), okresl w komentarzu, KTORY z 3 poziomow z
         * Zadania 5 KAZDY z nich wylapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyDomainCannotPerformBusinessRuleValidation {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego encja `Product`
         * NIE MOZE sama sprawdzic "czy nazwa jest unikalna" (potrzebowalaby
         * dostepu do repozytorium - naruszenie Dependency Rule, Lesson10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteAllValidationInControllerAntiPattern {
        /*
         * 🧪 Zadanie 8:
         * Napisz "kontroler" sprawdzajacy WSZYSTKIE 3 poziomy walidacji
         * SAM (wlacznie z zapytaniem do repozytorium) - to jest anty-wzorzec
         * z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixControllerValidationAntiPatternFromExercise08 {
        /*
         * 🧪 Zadanie 9:
         * Popraw kod z Zadania 8, przenoszac KAZDY poziom walidacji do
         * WLASCIWEGO miejsca (DTO/encja/Service).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListValidationOrderingRuleFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) zasade
         * kolejnosci walidacji ("od najtanszej do najdrozszej") i uzasadnij
         * ja jednym zdaniem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFullThreeLevelValidationForBookingSystem {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj system rezerwacji sali z 3 poziomami walidacji: format
         * (DTO: data niepusta, liczba osob > 0), niezmiennik (encja
         * `Booking`: data konca PO dacie poczatku), regula biznesowa
         * (Service: sala NIE MOZE byc podwojnie zarezerwowana w tym samym
         * terminie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DemonstrateEachValidationLevelRejectingDifferentInput {
        /*
         * 🧪 Zadanie 12:
         * Dla systemu z Zadania 11, zademonstruj 3 ROZNE, niepoprawne
         * zadania - kazde odrzucone na INNYM z 3 poziomow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MeasureCostSavedByFailingFastAtFormatLevel {
        /*
         * 🧪 Zadanie 13:
         * Dodaj licznik "zapytan do repozytorium" do systemu z Zadania 11 -
         * porownaj liczbe zapytan dla zadania z BLEDEM FORMATU (odrzucone
         * najwczesniej) vs zadania poprawnego formatowo, ale naruszajacego
         * regule biznesowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignAdrJustifyingThreeLevelValidationStrategy {
        /*
         * 🧪 Zadanie 14:
         * Napisz PELNY ADR (`Lesson02`) przyjmujacy 3-poziomowa strategie
         * walidacji jako standard zespolowy - z kontekstem (np. "mielismy
         * incydent, gdzie walidacja formatu i logiki biznesowej byla
         * wymieszana w kontrolerze") i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignCollectAllFormatErrorsInsteadOfFailFast {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz walidacje formatu z Zadania 2 (lub podobnej), by
         * ZBIERALA WSZYSTKIE bledy formatu naraz (zamiast przerywac na
         * pierwszym, por. `_16_clean_code/Lesson17`: collect-all errors) -
         * zademonstruj zadanie z 2 bledami formatu jednoczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignEntityWithMultipleInvariantsCheckedTogether {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj encje z 2 NIEZALEZNYMI niezmiennikami sprawdzanymi w
         * konstruktorze (np. `DateRange`: koniec>poczatek ORAZ oba w
         * przyszlosci) - zademonstruj naruszenie kazdego z osobna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignBusinessRuleRequiringMultipleRepositoryCalls {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj regule biznesowa wymagajaca 2 ROZNYCH zapytan do
         * repozytorium (np. "czy klient istnieje" ORAZ "czy ma wystarczajacy
         * limit kredytowy") - zademonstruj kolejnosc sprawdzen (od
         * tanszego/prostszego zapytania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareValidationArchitectureWithBeanValidationFromHibernateChapter {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) mechanike
         * Bean Validation (`_12_hibernate/Lesson28`) z 3-poziomowa
         * architektura z tej lekcji - na KTORYM POZIOMIE architektonicznym
         * najlepiej pasuje Bean Validation (odpowiedz: poziom 1, format).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignServiceOrchestratingValidationAcrossAllThreeLevels {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj Service, ktory JAWNIE orkiestruje wszystkie 3 poziomy
         * (przyjmuje juz zwalidowane formatowo DTO, tworzy encje - poziom 2 -
         * potem sprawdza regule biznesowa - poziom 3) w CZYTELNEJ,
         * uporzadkowanej kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromJdbcChapterForValidationLevelPlacement {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_09_jdbc/Lesson19_Dto` - czy widoczna
         * tam walidacja (jesli jest) odpowiada KTOREMUS z 3 poziomow z tej
         * lekcji? Ktoremu?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullValidationArchitectureForECommerceCheckout {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny system checkout z 3 poziomami walidacji dla
         * KAZDEGO z min. 3 pol/regul (np. format adresu dostawy, niezmiennik
         * "suma zamowienia > 0" w encji, regula biznesowa "produkt dostepny
         * w wymaganej ilosci").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullCheckoutFlowWithAllValidationLevelsInOrder {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj PELNY przeplyw checkout z Zadania 21 w POPRAWNEJ
         * kolejnosci (format -> niezmiennik -> regula biznesowa) -
         * zademonstruj min. 4 scenariusze (1 sukces, po 1 odrzuceniu na
         * KAZDYM poziomie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignCrossFieldValidationSpanningMultipleLevels {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj regule "kwota rabatu nie moze przekroczyc ceny
         * produktu" - w komentarzu przeanalizuj, czy to niezmiennik
         * domenowy (poziom 2, jesli oba pola sa czescia tej samej encji),
         * czy regula biznesowa (poziom 3, jesli wymaga danych z innego
         * zrodla) - zaimplementuj WLASCIWA wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildValidationLevelClassifierHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `String classifyValidationLevel(boolean
         * requiresRepositoryAccess, boolean requiresOnlyOwnFields)` zwracajaca
         * "FORMAT"/"NIEZMIENNIK"/"REGULA_BIZNESOWA" na podstawie prostych
         * flag - przetestuj dla min. 4 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorMonolithicValidationMethodIntoThreeLevels {
        /*
         * 🧪 Zadanie 25:
         * Napisz 1 OGROMNA metode `validateEverything(...)` mieszajaca
         * WSZYSTKIE 3 poziomy (format+niezmiennik+regula biznesowa,
         * min. 6 sprawdzen razem) - w PELNI zrefaktoryzuj na 3 wlasciwe
         * miejsca, krok po kroku (`_16_clean_code/Lesson15`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignValidationArchitectureRespectingHexagonalPorts {
        /*
         * 🧪 Zadanie 26:
         * Polacz architekture walidacji z terminologia portow (Lesson11-12) -
         * regula biznesowa (poziom 3) powinna korzystac z portu driven, NIE
         * z konkretnej implementacji - zademonstruj podmiane implementacji
         * portu bez zmiany logiki walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForCollectAllVsFailFastValidationStrategy {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR dla decyzji "fail-fast czy collect-all dla
         * walidacji formatu w publicznym API" - z kontekstem (np. "klienci
         * API skarzyli sie na wielokrotne, pojedyncze zgloszenia bledow") i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignPerformanceOptimizedValidationOrderForExpensiveRules {
        /*
         * 🧪 Zadanie 28:
         * Dla regul biznesowych z Zadania 17 (2 zapytania), zaprojektuj
         * kolejnosc sprawdzen minimalizujaca liczbe WYKONANYCH zapytan w
         * przypadku odrzucenia na pierwszym z nich - zmierz (licznikiem)
         * roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveValidationArchitectureChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do projektowania architektury walidacji - laczac
         * WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullValidationArchitectureForHealthcareBookingSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny
         * system rezerwacji wizyt lekarskich z PELNA, 3-poziomowa
         * architektura walidacji dla min. 4 regul (rozlozonych na
         * WSZYSTKIE 3 poziomy), w POPRAWNEJ kolejnosci fail-fast.
         * Zademonstruj min. 5 scenariuszy (1 sukces + min. 1 odrzucenie na
         * KAZDYM z 3 poziomow + 1 dodatkowy przypadek brzegowy).
         */
        public static void main(String[] args) { }
    }
}
