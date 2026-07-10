package com.example.javaquest._16_clean_code.Lesson15_RefactoringBasics;

public class _Exercises_Lesson15_RefactoringBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRefactoringDefinitionInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) definicje refaktoryzacji Fowlera i dlaczego zmiana
         * zachowania programu NIE jest refaktoryzacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMethodAndVerifyBehaviorWithManualAssertions {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `double calculateCircleArea(double radius)`. Wywolaj
         * ja dla radius=5 i zapisz wynik jako "punkt odniesienia" - wypisz go z
         * etykieta "PRZED".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RefactorMethodNameAndVerifySameResult {
        /*
         * 🧪 Zadanie 3:
         * Zrefaktoryzuj metode z Zadania 2 (np. zmieniajac tylko nazwe
         * zmiennej wewnetrznej na czytelniejsza) - wywolaj ja ponownie dla tych
         * samych danych i porownaj wynik z "PRZED" z Zadania 2 (musi byc
         * identyczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ApplyBoyScoutRuleToGivenMethod {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode ze zla nazwa zmiennej (np. `x`, `tmp`, `d1`). Zastosuj
         * "zasade skauta" - zmien TYLKO nazwy zmiennych na czytelne,
         * zachowujac IDENTYCZNA logike. Zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyWhichOfThreeChangesIsRefactoring {
        /*
         * 🧪 Zadanie 5:
         * Dane sa 3 opisy zmian: (a) zmiana nazwy metody bez zmiany logiki, (b)
         * naprawienie bledu logicznego (zla formula), (c) dodanie nowego
         * parametru zmieniajacego wynik. W komentarzu wskaz, KTORA (jesli
         * ktorakolwiek) jest CZYSTA refaktoryzacja wg definicji Fowlera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMethodWithMagicNumberAndExtractNamedConstant {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode uzywajaca "magicznej liczby" (np. `price * 0.23` bez
         * wyjasnienia) - zrefaktoryzuj, wprowadzajac nazwana stala `VAT_RATE`.
         * Zweryfikuj identyczny wynik przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DemonstrateRuleOfThreeWithTwoSimilarMethods {
        /*
         * 🧪 Zadanie 7:
         * Napisz 2 PODOBNE (ale nie identyczne) metody liczace rozne rabaty. W
         * komentarzu wyjasnij, dlaczego wg "reguly trzech razy" NIE powinienes
         * ich jeszcze laczyc w 1 wspolna metode (na razie tylko 2 wystapienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddThirdSimilarMethodAndApplyRuleOfThree {
        /*
         * 🧪 Zadanie 8:
         * Dodaj TRZECIA podobna metode do Zadania 7 - teraz (zgodnie z regula
         * trzech razy) wydziel wspolna logike do 1 metody parametryzowanej,
         * ktora zastapi wszystkie trzy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListScenariosWhenNotToRefactor {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * sytuacje, w ktorych NIE nalezy refaktoryzowac kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRefactoringVsRewriteInOwnWords {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) refaktoryzacje
         * z przepisaniem kodu od zera (rewrite) - kiedy druga opcja moze byc
         * uzasadniona mimo wiekszego ryzyka.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorLongMethodInThreeVerifiedSteps {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `registerStudent(String name, int age, double
         * avgGrade)` robaca walidacje+obliczenia+formatowanie naraz (Long
         * Method). Zrefaktoryzuj ja w 3 ODDZIELNYCH, MALYCH krokach (kazdy
         * krok = osobna metoda prywatna), weryfikujac identyczny wynik PO
         * KAZDYM kroku (wypisz wynik po kroku 1, 2 i 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteCharacterizationTestForUndocumentedMethod {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `String classifyScore(int score)` z NIEOCZYWISTA
         * logika progow (np. asymetryczne przedzialy). Napisz "test
         * charakteryzujacy" - metode wywolujaca classifyScore dla min. 5
         * roznych wartosci i wypisujaca kazdy wynik (dokumentujac OBECNE
         * zachowanie, cokolwiek by ono nie bylo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RefactorClassifyScoreUsingCharacterizationTestAsSafetyNet {
        /*
         * 🧪 Zadanie 13:
         * Zrefaktoryzuj metode classifyScore z Zadania 12 (np. wprowadzajac
         * nazwane stale progow), uzywajac testu charakteryzujacego z Zadania
         * 12 jako siatki bezpieczenstwa - uruchom test PRZED i PO, porownaj
         * wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ApplyBoyScoutRuleWhileFixingUnrelatedBug {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode z drobnym bledem logicznym ORAZ zla nazwa zmiennej.
         * Napraw BLAD (to jest zmiana funkcjonalna, nie refaktoryzacja) I
         * PRZY OKAZJI (zasada skauta) popraw nazwe zmiennej (to JEST
         * refaktoryzacja) - w komentarzu oznacz, KTORA czesc zmiany byla
         * ktorym rodzajem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareBigBangVsIncrementalRefactoringRisk {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode ze smellem Long Method (min. 4 odpowiedzialnosci). W
         * komentarzu opisz plan refaktoryzacji "wielkim skokiem" (wszystko
         * naraz) vs planem "malymi krokami" (4 osobne kroki) - wskaz, w
         * KTORYM podejsciu latwiej zlokalizowac blad, gdyby po zmianie cos
         * przestalo dzialac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorDuplicatedLogicAfterThirdOccurrence {
        /*
         * 🧪 Zadanie 16:
         * Napisz 3 metody liczace roznymi drogami "czy zamowienie kwalifikuje
         * sie do darmowej wysylki" (rozne progi, ale ta sama STRUKTURA
         * warunku) - zgodnie z regula trzech razy, wydziel wspolna metode
         * generyczna `qualifiesForFreeShipping(double amount, double
         * threshold)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IdentifyMinimalSafeRefactoringStepsForGivenMethod {
        /*
         * 🧪 Zadanie 17:
         * Dana jest metoda z 3 zagniezdzonymi warunkami i 2 magicznymi
         * liczbami. W komentarzu rozpisz PLAN refaktoryzacji jako liste MIN. 3
         * malych, niezaleznie weryfikowalnych krokow (bez jeszcze ich
         * wykonywania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExecutePlanFromExercise17StepByStep {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj metode z Zadania 17 i wykonaj PLAN z tego zadania krok
         * po kroku - po KAZDYM kroku wypisz wynik dla tych samych danych
         * testowych, potwierdzajac brak regresji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DecideRefactorVsLeaveAloneForGivenScenario {
        /*
         * 🧪 Zadanie 19:
         * Dany jest opis: metoda ma smell Long Parameter List, ale jest
         * uzywana w JEDNYM miejscu w calym kodzie, dziala poprawnie od lat, a
         * caly modul zostanie WYLACZONY za miesiac. W komentarzu zdecyduj
         * (z uzasadnieniem, min. 3 zdania), czy warto ja refaktoryzowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildBeforeAfterComparisonForRealisticMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode `processReturnRequest(String orderId, String reason,
         * double amount)` z min. 3 wymieszanymi odpowiedzialnosciami. Wypisz
         * wynik "PRZED". Zrefaktoryzuj na male metody. Wypisz wynik "PO".
         * Porownaj oba stringi programowo (`.equals()`) i wypisz PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullSafetyNetForUndocumentedLegacyMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz "legacy" metode `String calculateShippingCost(double weight,
         * String country, boolean express)` z nieoczywista logika (min. 4
         * kombinacje warunkow). Napisz test charakteryzujacy pokrywajacy
         * WSZYSTKIE sensowne kombinacje wejsc (min. 6 wywolan) - wypisz
         * wszystkie wyniki jako dokumentacje OBECNEGO zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorLegacyMethodUsingSafetyNetFromExercise21 {
        /*
         * 🧪 Zadanie 22:
         * Zrefaktoryzuj metode z Zadania 21 na male, dobrze nazwane metody
         * pomocnicze (min. 3 kroki) - po KAZDYM kroku uruchom PONOWNIE caly
         * test charakteryzujacy z Zadania 21 i potwierdz IDENTYCZNE wyniki dla
         * wszystkich kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignIncrementalRefactoringPlanForGodClass {
        /*
         * 🧪 Zadanie 23:
         * Napisz "god class" `OrderEverything` z 4 wymieszanymi
         * odpowiedzialnosciami (walidacja, obliczenia, "zapis", "powiadomienie").
         * W komentarzu rozpisz PLAN refaktoryzacji jako sekwencje MIN. 4
         * malych krokow prowadzacych do finalnego podzialu na male klasy - bez
         * jeszcze wykonywania planu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExecuteIncrementalPlanVerifyingEachStep {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj PLAN z Zadania 23 krok po kroku - po KAZDYM kroku wywolaj
         * cala funkcjonalnosc od nowa i wypisz wynik, potwierdzajac brak
         * regresji przy KAZDYM przejsciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareThreeRefactoringApproachesForSameSmell {
        /*
         * 🧪 Zadanie 25:
         * Wez metode ze smellem Long Parameter List (6+ parametrow). Napisz 3
         * RÓŻNE plany refaktoryzacji (w komentarzu, bez wykonywania): (a) 1
         * duzy krok (wszystko naraz), (b) 3 srednie kroki, (c) 6 bardzo malych
         * krokow. Oceń kazdy plan pod katem ryzyka i latwosci weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSmallestSafestPlanFromExercise25 {
        /*
         * 🧪 Zadanie 26:
         * Wybierz NAJBEZPIECZNIEJSZY plan z Zadania 25 (najmniejsze kroki) i
         * wykonaj go w calosci, weryfikujac wynik po kazdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IdentifyRuleOfThreeViolationInGivenCodebaseDescription {
        /*
         * 🧪 Zadanie 27:
         * Dany jest opis: w kodzie istnieja JUZ 4 rozne miejsca z bardzo
         * podobna logika walidacji adresu e-mail, powielone kopiuj-wklej. W
         * komentarzu wyjasnij, dlaczego to WYRAZNIE przekracza "regule trzech
         * razy" i jaki jest ZWIAZANY z tym koszt (patrz tez Lesson13: DRY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAndConsolidateFourDuplicatedValidations {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj 4 metody z Zadania 27 (kazda z osobno powielona logika
         * walidacji e-mail) - a nastepnie skonsoliduj je do 1 wspolnej metody
         * `isValidEmail(String email)`, z ktorej korzystaja wszystkie 4
         * miejsca. Zweryfikuj identyczne wyniki dla min. 3 adresow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveRefactoringDecisionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) pomagajaca zdecydowac: czy refaktoryzowac teraz, czy nie,
         * jakimi krokami, i jak zweryfikowac brak regresji - laczac WSZYSTKIE
         * pojecia z tej lekcji (siatka bezpieczenstwa, male kroki, regula 3
         * razy, kiedy NIE refaktoryzowac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullRefactoringWorkflowDemo {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz REALISTYCZNA, "brudna" metode/klase
         * (min. 5 odpowiedzialnosci wymieszanych, min. 2 magiczne liczby, zla
         * nazwy zmiennych) reprezentujaca system rezerwacji sali konferencyjnej.
         * Przeprowadz PELNY, udokumentowany proces refaktoryzacji: (1) napisz
         * test charakteryzujacy jako siatke bezpieczenstwa, (2) rozpisz plan
         * malych krokow, (3) wykonaj KAZDY krok, weryfikujac test
         * charakteryzujacy po kazdym z nich, (4) wypisz finalne porownanie
         * PRZED/PO potwierdzajace 100% zgodnosc zachowania.
         */
        public static void main(String[] args) { }
    }
}
