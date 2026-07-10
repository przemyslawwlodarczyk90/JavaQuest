package com.example.javaquest._16_clean_code.Lesson01_WhatIsCleanCode;

public class _Exercises_Lesson01_WhatIsCleanCode {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineCleanCodeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu napisz wlasnymi slowami (min. 3 zdania),
         * czym jest "clean code", odwolujac sie do definicji Roberta C.
         * Martina i Martina Fowlera z teorii tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainBrokenWindowsTheory {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij w komentarzu (min. 4 zdania) teorie
         * "wybitych szyb" (broken windows theory) w kontekscie kodu i podaj
         * wlasny przyklad sytuacji z projektu, gdzie "jedna brzydka rzecz"
         * zachecila do dolozenia kolejnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RewriteNestedIfsAsEarlyReturns {
        /*
         * 🧪 Zadanie 3:
         * Dany jest fragment:
         *   boolean check(int age) {
         *       boolean ok;
         *       if (age >= 0) {
         *           if (age < 150) { ok = true; } else { ok = false; }
         *       } else { ok = false; }
         *       return ok;
         *   }
         * Przepisz go na wersje z "early return" (bez zagniezdzonych if-else)
         * i zweryfikuj, ze zwraca ten sam wynik dla age = -5, 30, 200.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IdentifyProblemsInMessyMethod {
        /*
         * 🧪 Zadanie 4:
         * Przeanalizuj metode demonstrateMessyOrderValidation() z teorii
         * lekcji (plik _Lesson01_WhatIsCleanCode.java) - w komentarzu wypisz
         * min. 4 konkretne problemy czytelnosci, ktore w niej widzisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CalculateCostOfBadCodeOverTime {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: zalozmy, ze naprawa buga w czystym kodzie zajmuje
         * 1h, a w brudnym kodzie czas rosnie o 20% z kazdym miesiacem
         * zaniedbania. Policz w komentarzu (recznie lub w kodzie), ile
         * czasu zajmie naprawa buga po 6 miesiacach zaniedbania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMessyValidationForDiscountCode {
        /*
         * 🧪 Zadanie 6:
         * Napisz CELOWO brzydka metode `boolean sprawdz(String c, int p)`,
         * ktora sprawdza, czy kod rabatowy c (np. "WIOSNA10") jest poprawny
         * dla progu punktow p (np. p > 100) - uzyj jednoliterowych nazw i
         * zagniezdzonych if-ow, tak jak w przykladzie z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RewriteDiscountValidationCleanly {
        /*
         * 🧪 Zadanie 7:
         * Przepisz metode z Zadania 6 na czysta wersje: nazwij metode i
         * parametry zgodnie z ich rola (np. isDiscountCodeValid(String
         * discountCode, int loyaltyPoints)), wydziel warunki do osobnych,
         * nazwanych metod pomocniczych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareReadingTimeOfBothVersions {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: porownaj metody z Zadan 6 i 7 - w komentarzu opisz
         * (subiektywnie), ile czasu potrzeba na zrozumienie kazdej z nich i
         * dlaczego roznica jest istotna przy pracy zespolowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListSymptomsOfDirtyCode {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz w komentarzu min. 5 "objawow" brudnego kodu
         * (np. dlugie metody, jednoliterowe nazwy, brak struktury) - te
         * objawy beda pogłebiane w kolejnych lekcjach rozdzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCodeIsReadMoreThanWritten {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij w komentarzu (min. 3 zdania), dlaczego
         * kod jest czytany czesciej niz pisany i jak ta obserwacja powinna
         * wplywac na sposob, w jaki piszesz kod na co dzien.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorFourLevelNestedValidation {
        /*
         * 🧪 Zadanie 11:
         * Dany jest fragment z 4 poziomami zagniezdzonych if-ow sprawdzajacy
         * rejestracje uzytkownika (imie niepuste, wiek >= 18, email zawiera
         * "@", haslo dlugosci >= 8). Napisz go najpierw w wersji zagniezdzonej,
         * potem zrefaktoryzuj do serii early-returnow z nazwanymi metodami
         * pomocniczymi (np. isAdult, hasValidEmail).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureCyclomaticComplexityInformally {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala: dla metody demonstrateMessyOrderValidation() z
         * teorii policz "recznie" liczbe niezaleznych sciezek wykonania
         * (kazdy if to +1 do zlozonosci) i porownaj z liczba sciezek w
         * wersji isOrderValid() z metodami pomocniczymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteOrderValidatorWithConfigurableRules {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase OrderRules z metodami statycznymi
         * isAmountValid(double), isItemCountValid(int),
         * isCountrySupported(String) - kazda z jasna nazwa i jednym
         * warunkiem - i uzyj ich razem w jednej metodzie validate(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyBrokenWindowInGivenSnippet {
        /*
         * 🧪 Zadanie 14:
         * Dany fragment:
         *   // TODO tymczasowo, napraw pozniej
         *   int x1 = calc(a, b, 0);
         *   int x2 = calc(a, b, 1); // kopia powyzszej linii ze zmiana 1 argumentu
         * W komentarzu opisz, jakie "wybite szyby" (broken windows) tu widzisz
         * i co konkretnie zachecilyby kolejnego programiste do dolozenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EstimateOnboardingTimeCleanVsMessy {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: napisz krotki (min. 5 zdan) scenariusz nowego
         * programisty dolaczajacego do 2 projektow - jednego z czystym
         * kodem, drugiego z bagnem - i opisz roznice w czasie potrzebnym na
         * zrozumienie pierwszego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteUnitTestFriendlyValidation {
        /*
         * 🧪 Zadanie 16:
         * Przepisz metode isOrderValid(...) z teorii tak, aby kazdy warunek
         * (np. hasCustomerName) byl osobna metoda `public static` mozliwa
         * do przetestowania niezaleznie - wywolaj kazda z min. 2 przypadkami
         * (prawda/falsz) i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareTwoRealCodeSamplesFromCourse {
        /*
         * 🧪 Zadanie 17:
         * Znajdz (przegladajac wczesniejsze rozdzialy kursu) 1 fragment
         * kodu, ktory Twoim zdaniem jest szczegolnie czytelny, i 1, ktory
         * mogłby byc czytelniejszy - w komentarzu uzasadnij wybor kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_QuantifyTechnicalDebtInterest {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: uzywajac metafory "dlugu technicznego" (odsetki od
         * dlugu), napisz krotki raport (min. 4 zdania), jak "nieodsetkowany"
         * (nigdy niesplacany) dlug techniczny wplywa na tempo zespolu po
         * roku, dwoch latach, trzech latach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorLongParameterListIntoObject {
        /*
         * 🧪 Zadanie 19:
         * Dana jest metoda
         *   boolean validate(String n, double a, int q, String s, String e, String p)
         * z 6 parametrami. Zaproponuj (opisz w komentarzu i naszkicuj kod)
         * klase OrderRequest grupujaca te dane, tak by metoda przyjmowala
         * jeden obiekt zamiast 6 parametrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteBusinessCaseForRefactoringSprint {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz krotkie uzasadnienie biznesowe (min. 6 zdan,
         * jak do menedzera projektu) dla przeznaczenia jednego sprintu na
         * refaktoryzacje istniejacego, dzialajacego, ale brudnego modulu -
         * odwolaj sie do kosztu narastajacego w czasie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderValidatorFromScratch {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj i napisz od zera klase OrderValidator z metoda
         * `ValidationResult validate(Order order)`, gdzie ValidationResult
         * przechowuje `boolean valid` i `List<String> errors` (zbierajac
         * WSZYSTKIE naruszone reguly, nie tylko pierwsza) - przetestuj na
         * 3 roznych zamowieniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureReadabilityWithColleagueSimulation {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wybierz metode demonstrateMessyOrderValidation() z
         * teorii i napisz, KROK PO KROKU, jak "obcy" programista musialby ja
         * analizowac (jakie pytania by sobie zadawal), zeby zrozumiec, co
         * ona robi - min. 8 krokow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignMetricForCodeCleanliness {
        /*
         * 🧪 Zadanie 23:
         * Zaproponuj (w komentarzu) wlasna, prosta metryke "czystosci kodu"
         * (np. na podstawie: dlugosc metod, glebokosc zagniezdzenia, liczba
         * parametrow) i zaimplementuj metode
         * `static int roughDirtinessScore(int nestingDepth, int paramCount, int lineCount)`
         * liczaca punkty "brudu" wedlug Twojej metryki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RefactorMessyValidationUsingYourMetric {
        /*
         * 🧪 Zadanie 24:
         * Uzyj metryki z Zadania 23, aby policzyc "brud" metody
         * demonstrateMessyOrderValidation() (przyblizone dane wejsciowe: 6
         * poziomow zagniezdzenia, 4 parametry, ~25 linii) i metody
         * isOrderValid() (1 poziom, 4 parametry, ~10 linii) - porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteBeforeAfterCaseStudyReport {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: napisz krotkie studium przypadku (min. 8 zdan) w
         * formacie "PRZED / PO" opisujace fikcyjny modul, ktory zostal
         * zrefaktoryzowany - uwzglednij liczbe bugow przed/po, czas
         * wdrozenia nowej osoby przed/po, czas potrzebny na dodanie nowej
         * funkcji przed/po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IdentifyTradeoffsOfOverEngineering {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: "clean code" moze byc przesadzony (np. zbyt wiele
         * malych metod utrudnia sledzenie przeplywu). Opisz w komentarzu
         * (min. 5 zdan) granice miedzy "czystym kodem" a "over-engineeringiem"
         * - ten temat wroci w Lekcji 13 (YAGNI).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildValidationPipelineOfRules {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj interfejs funkcyjny
         * `interface OrderRule { String checkOrFail(Order order); }`
         * zwracajacy null gdy OK lub komunikat bledu gdy nie - zbuduj liste
         * takich regul i uruchom je wszystkie na jednym zamowieniu, zbierajac
         * bledy do listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareCostOfBugInProdVsDev {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz raport (min. 6 zdan) porownujacy koszt
         * naprawy buga wykrytego podczas pisania kodu, podczas code review,
         * podczas testow, i po wdrozeniu na produkcje - i jak brudny kod
         * zwieksza prawdopodobienstwo, ze blad dotrwa do ostatniego etapu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PresentCleanCodeToSkepticalTeam {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz krotka "prezentacje" (min. 8 zdan) dla
         * zespolu, ktory uwaza, ze "clean code to strata czasu, liczy sie
         * tylko dzialajacy produkt" - uzyj argumentow z tej lekcji (broken
         * windows, koszt narastajacy w czasie, szybkosc dostarczania w
         * dlugim okresie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneRewriteFullMessyModule {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz wlasny, kompletny "brudny" modul
         * (min. 30 linii, wlasny wybrany temat biznesowy, np. obliczanie
         * ceny koszyka z rabatami) z typowymi problemami z tej lekcji
         * (jednoliterowe nazwy, zagniezdzenia, duplikacja), a nastepnie
         * zrefaktoryzuj go do czystej wersji - obie wersje musza dawac
         * IDENTYCZNY wynik dla tych samych danych wejsciowych.
         */
        public static void main(String[] args) { }
    }
}
