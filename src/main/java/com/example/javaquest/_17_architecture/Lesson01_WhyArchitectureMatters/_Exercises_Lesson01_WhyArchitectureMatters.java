package com.example.javaquest._17_architecture.Lesson01_WhyArchitectureMatters;

public class _Exercises_Lesson01_WhyArchitectureMatters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainArchitectureVsCleanCodeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy "czystym kodem" (`_16_clean_code`) a
         * "architektura" - na jakim POZIOMIE dziala kazde z tych pojec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteBigBallOfMudMethodForInvoicing {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `handleInvoice(String customer, double amount, String
         * country)` mieszajaca NARAZ: logike podatku (if/else po kraju),
         * "zapis do bazy" (Map), i formatowanie wyniku - wszystko w 1 metodzie,
         * jak w teorii lekcji (Big Ball of Mud).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyWhatWouldBreakIfTaxRuleChanges {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: dla metody z Zadania 2, w komentarzu opisz, CO
         * musialbys zmienic, gdyby firma dodala NOWY kraj z innym podatkiem -
         * czy zmiana jest izolowana, czy "rozlana" po calej metodzie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SeparateTaxLogicIntoOwnInterface {
        /*
         * 🧪 Zadanie 4:
         * Wydziel logike podatku z Zadania 2 do interfejsu `TaxPolicy` z 1
         * implementacja - `InvoiceService` przyjmuje ja przez konstruktor
         * (por. Lesson11 z `_16_clean_code`: DIP). Zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddNewCountryWithoutModifyingInvoiceService {
        /*
         * 🧪 Zadanie 5:
         * Dodaj NOWA implementacje `TaxPolicy` dla innego kraju (Zadanie 4) -
         * zademonstruj, ze `InvoiceService` NIE wymaga ZADNEJ zmiany, zeby
         * obsluzyc nowy kraj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ClassifyThreeDecisionsAsCheapOrExpensiveToReverse {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: dla 3 decyzji - (a) nazwa zmiennej lokalnej, (b) wybor
         * bazy danych, (c) algorytm sortowania w 1 metodzie - oceń KAZDA jako
         * "tania" lub "droga" do odwrocenia, z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestBusinessLogicWithoutAnyInfrastructure {
        /*
         * 🧪 Zadanie 7:
         * Napisz prosta regule biznesowa (np. `boolean isEligibleForFreeShipping
         * (double orderTotal)`) jako CZYSTA funkcje, bez zadnej zaleznosci od
         * bazy/sieci. Wywolaj ja dla 3 roznych wartosci i wypisz wyniki -
         * zademonstruj, ze logike da sie sprawdzic BEZ infrastruktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyInfrastructureLeakingIntoBusinessLogic {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode liczaca rabat, ktora PRZY OKAZJI woluje
         * `System.out.println("SELECT * FROM...")` (symulujac SQL wymieszany z
         * logika). W komentarzu wyjasnij, dlaczego to utrudnia testowanie
         * SAMEJ logiki rabatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RemoveInfrastructureLeakFromExercise08 {
        /*
         * 🧪 Zadanie 9:
         * Popraw metode z Zadania 8, usuwajac odwolanie do "SQL" - logika
         * rabatu ma teraz ZERO zaleznosci od infrastruktury. Zweryfikuj
         * identyczny wynik liczbowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListArchitectureGoalsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * cele dobrej architektury omowione w tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildBigBallOfMudVersionOfLibrarySystem {
        /*
         * 🧪 Zadanie 11:
         * Napisz "Big Ball of Mud" system biblioteki: 1 klasa mieszajaca
         * przechowywanie ksiazek, logike dostepnosci ORAZ formatowanie
         * powiadomien o zaleglosci - wszystko w kilku metodach odwolujacych
         * sie do tych samych, wspoldzielonych pol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorLibrarySystemIntoSeparatedComponents {
        /*
         * 🧪 Zadanie 12:
         * Zrefaktoryzuj system z Zadania 11 na min. 3 oddzielne, luzno
         * sprzezone komponenty (np. BookCatalog, AvailabilityChecker,
         * OverdueNotifier) - zademonstruj identyczne dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateChangingStorageTechnologyInBothVersions {
        /*
         * 🧪 Zadanie 13:
         * Dla OBU wersji systemu bibliotecznego (Zadanie 11 i 12), zasymuluj
         * "zmiane technologii przechowywania" (np. z HashMap na List<Book>) -
         * w komentarzu porownaj, ile kodu trzeba zmienic w KAZDEJ wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignBusinessRuleTestableInIsolation {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj regule biznesowa `calculateLateFee(int daysOverdue)` (bez
         * zadnej zaleznosci od bazy/UI) - napisz min. 4 wywolania z roznymi
         * wartosciami i zweryfikuj wyniki, dowodzac ze regula jest w PELNI
         * testowalna w izolacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_IdentifyExpensiveVsCheapDecisionsInRealisticSystem {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: dla systemu e-commerce wymysl 3 decyzje TANIE do
         * zmiany i 3 decyzje DROGIE do zmiany - uzasadnij kazda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignPolicyInterfaceAllowingMultipleImplementations {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj interfejs `ShippingCostPolicy` (calculate(double weight))
         * z 2 implementacjami (np. Standard, Express) - napisz kod klienta
         * przyjmujacy dowolna implementacje i dzialajacy identycznie dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureChangeCostWithAndWithoutIsolation {
        /*
         * 🧪 Zadanie 17:
         * Napisz 2 wersje TEJ SAMEJ funkcjonalnosci (np. liczenie prowizji): (a)
         * logika wymieszana z "zapisem" i "logowaniem", (b) logika w OSOBNEJ,
         * izolowanej klasie. Dodaj NOWA regule prowizji w OBU wersjach - w
         * komentarzu porownaj koszt zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainBigDesignUpFrontVsEmergentArchitecture {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania) roznice miedzy
         * "Big Design Up Front" a swiadomym, stopniowym podejsciem do
         * architektury promowanym w tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AuditRealClassFromEarlierChapterForInfrastructureLeak {
        /*
         * 🧪 Zadanie 19:
         * Wybierz 1 klase z dowolnej wczesniejszej lekcji kursu (np.
         * `_09_jdbc`). W komentarzu oceń, czy logika biznesowa jest tam
         * "wymieszana" z detalami technicznymi (JDBC/SQL), czy odseparowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignSimpleLayeredVersionOfGivenFeature {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj prosty system rezerwacji stolika w restauracji z 3
         * WYRAZNIE oddzielonymi elementami: reguła dostepnosci (logika),
         * przechowywanie rezerwacji (dane), formatowanie potwierdzenia
         * (prezentacja). Zademonstruj pelny przeplyw.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRealisticBigBallOfMudOrderSystem {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj realistyczny "Big Ball of Mud" system zamowien - 1 klasa
         * laczaca walidacje, obliczenia rabatu, obliczenia podatku, "zapis",
         * i "wyslanie e-maila" (min. 5 wymieszanych odpowiedzialnosci w
         * powiazanych metodach dzielacych stan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullyRefactorOrderSystemIntoSeparatedArchitecture {
        /*
         * 🧪 Zadanie 22:
         * Zrefaktoryzuj system z Zadania 21 na min. 5 osobnych, luzno
         * sprzezonych komponentow (interfejsy + implementacje, wstrzykiwane
         * przez konstruktor) - zademonstruj identyczny wynik funkcjonalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulateThreeIndependentChangesAfterRefactoring {
        /*
         * 🧪 Zadanie 23:
         * Na bazie rozwiazania z Zadania 22, zasymuluj 3 NIEZALEZNE zmiany
         * (nowa regula rabatu, nowy sposob "zapisu", nowy format
         * powiadomienia) - kazda WYLACZNIE we WLASCIWEJ, 1 klasie, bez
         * dotykania pozostalych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignTestSuiteCoveringOnlyBusinessRulesInIsolation {
        /*
         * 🧪 Zadanie 24:
         * Dla systemu z Zadania 22, napisz "zestaw testowy" (metoda wywolujaca
         * WSZYSTKIE reguly biznesowe z min. 3 roznymi zestawami danych KAZDA)
         * dzialajacy WYLACZNIE na logice biznesowej, bez angazowania
         * "zapisu"/"powiadomien".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareCostOfAddingFeatureInBothArchitectures {
        /*
         * 🧪 Zadanie 25:
         * Dodaj NOWA funkcje (np. "rabat lojalnosciowy") ROWNOLEGLE do OBU
         * wersji z Zadania 21/22 - w komentarzu policz liczbe ZMIENIONYCH
         * metod/klas w kazdej wersji i porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignArchitectureIsolatingUncertainThirdPartyChoice {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj system wysylki powiadomien, gdzie NIE WIADOMO jeszcze,
         * czy firma uzyje e-maila czy SMS-a (niepewnosc). Zaprojektuj
         * abstrakcje (interfejs) TAK, by ta decyzja mogla zapasc PozNIEJ, bez
         * przepisywania reszty systemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DemonstrateDeferredDecisionByAddingImplementationLater {
        /*
         * 🧪 Zadanie 27:
         * Korzystajac z abstrakcji z Zadania 26, "podejmij decyzje pozniej" -
         * dodaj KONKRETNA implementacje (np. EmailNotifier) i podlacz ja do
         * reszty systemu BEZ zadnej zmiany w kodzie, ktory juz istnial.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeRealSystemFromEarlierChapterForArchitecturalDecisions {
        /*
         * 🧪 Zadanie 28:
         * Wybierz 1 wiekszy fragment kursu (np. `_10_dao`) i w komentarzu
         * wypisz min. 3 decyzje architektoniczne, ktore tam widac (np. DAO
         * jako abstrakcja nad JDBC, warstwa serwisu nad DAO) - dla kazdej
         * oceń, czy byla "tania" czy "droga" do podjecia/zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveArchitectureAssessmentChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 5
         * punktow) do oceny, czy dany system ma "swiadoma architekture", czy
         * dryfuje w strone Big Ball of Mud.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneDemonstratingAllLessonPrinciplesTogether {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj maly system (np.
         * system zamawiania jedzenia) demonstrujacy WSZYSTKIE zasady z tej
         * lekcji naraz: logika biznesowa BEZ zaleznosci od infrastruktury,
         * min. 2 decyzje "odlozone" za abstrakcjami (interfejsami), oraz
         * dowod (test w `main()`), ze zmiana KAZDEJ z tych decyzji jest
         * izolowana do 1 miejsca.
         */
        public static void main(String[] args) { }
    }
}
