package com.example.javaquest._16_clean_code.Lesson22_CodeReviewBestPracticesAndCapstone;

public class _Exercises_Lesson22_CodeReviewBestPracticesAndCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyCodeReviewComplementsTestsAndStaticAnalysis {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego code review jest potrzebne, mimo ze mamy juz
         * testy i statyczna analize (Lesson20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RewriteVagueReviewCommentAsConcreteFeedback {
        /*
         * 🧪 Zadanie 2:
         * Dany jest ogolnikowy komentarz z review: "Ten kod jest brzydki."
         * Przepisz go (w komentarzu) na KONKRETNY feedback odwolujacy sie do
         * min. 1 zasady z tego rozdzialu (np. nazewnictwo, rozmiar metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ClassifyThreeReviewCommentsAsMustFixOrNit {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: dla 3 podanych uwag z review - (a) "brak walidacji
         * hasla przed zapisem do bazy", (b) "wolalbym nazwe `total` zamiast
         * `sum`", (c) "ta metoda ma 8 parametrow" - oznacz kazda jako MUST
         * FIX lub NIT/sugestia, z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IdentifyNamingViolationInGivenSnippet {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode z 3 zmiennymi o zlych nazwach (`x`, `tmp`, `d1`).
         * Napisz "komentarz recenzenta" wskazujacy WSZYSTKIE 3 problemy
         * (odwolujac sie do Lesson02: Naming).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixNamingIssuesFromExercise04 {
        /*
         * 🧪 Zadanie 5:
         * Popraw metode z Zadania 4 zgodnie z "komentarzem recenzenta" -
         * zweryfikuj identyczny wynik dzialania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifySrpViolationAsReviewer {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase `ReportEverything` laczaca 3 odpowiedzialnosci (dane +
         * formatowanie + "wysylka"). Napisz "komentarz recenzenta" wskazujacy
         * naruszenie SRP (Lesson07) i sugerujacy podzial.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ApplyReviewSuggestionFromExercise06 {
        /*
         * 🧪 Zadanie 7:
         * Zastosuj sugestie z Zadania 6 - rozbij klase na min. 2 male,
         * spojne klasy (Lesson06/Lesson16: Extract Class).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WritePraiseCommentForWellDesignedCode {
        /*
         * 🧪 Zadanie 8:
         * Napisz 1 DOBRZE zaprojektowana metode (mala, dobra nazwa, 0-2
         * parametry) - napisz "komentarz recenzenta" CHWALACY konkretny
         * aspekt (nie tylko "dobra robota", ale KONKRETNIE co i dlaczego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareSmallVsLargePullRequestForReviewability {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) PR zmieniajacy
         * 1 metode w 1 pliku vs PR zmieniajacy 15 plikow naraz - ktory
         * latwiej dokladnie zrecenzowac i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListChapterPrinciplesReviewerShouldCheck {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 6
         * zasad z CALEGO rozdzialu `_16_clean_code`, ktore dobry recenzent
         * powinien miec "w tyle glowy" podczas review.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConductFullReviewOfRealisticDirtyMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `processSignup(String e, String p, int a)` laczaca
         * zle nazwy + brak walidacji + Long Method (min. 3 odpowiedzialnosci).
         * Napisz PELNY "komentarz recenzenta" wskazujacy WSZYSTKIE problemy,
         * z odwolaniem do konkretnych lekcji rozdzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ApplyAllReviewCommentsFromExercise11 {
        /*
         * 🧪 Zadanie 12:
         * Zastosuj WSZYSTKIE uwagi z Zadania 11 - napisz w pelni poprawiona
         * wersje metody (dobre nazwy, walidacja fail-fast, rozbicie na male
         * metody). Zweryfikuj identyczny wynik dla poprawnych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyMultipleSolidViolationsAsReviewer {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase `PaymentProcessor`, ktora: tworzy `new
         * SqlPaymentGateway()` w srodku (DIP, Lesson11) ORAZ rozgalezia
         * logike przez if/else po typie platnosci (OCP, Lesson08). Napisz
         * "komentarz recenzenta" wskazujacy OBA naruszenia osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RefactorPaymentProcessorAddressingBothSolidViolations {
        /*
         * 🧪 Zadanie 14:
         * Zrefaktoryzuj klase z Zadania 13, wprowadzajac abstrakcje
         * PaymentGateway (DIP) ORAZ hierarchie implementacji zamiast if/else
         * (OCP, Replace Conditional with Polymorphism - Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConductReviewFocusedOnNullSafety {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode zwracajaca null zamiast Optional/pustej kolekcji w 2
         * roznych miejscach. Napisz "komentarz recenzenta" (Lesson18) dla
         * OBU miejsc z konkretna sugestia naprawy dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ApplyNullSafetyReviewCommentsFromExercise15 {
        /*
         * 🧪 Zadanie 16:
         * Zastosuj obie sugestie z Zadania 15 - zwroc Optional tam, gdzie brak
         * wyniku jest normalny, i pusta kolekcje tam, gdzie zwracana jest
         * kolekcja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConductReviewFocusedOnExceptionDesign {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode z `catch (Exception e)` (zbyt szeroki) i ogolnikowym
         * komunikatem wyjatku. Napisz "komentarz recenzenta" (Lesson17)
         * wskazujacy OBA problemy z konkretna sugestia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ApplyExceptionDesignReviewCommentsFromExercise17 {
        /*
         * 🧪 Zadanie 18:
         * Zastosuj sugestie z Zadania 17 - zawez typ catch i dodaj kontekst do
         * komunikatu wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SplitLargePullRequestIntoThreeSmallerLogicalOnes {
        /*
         * 🧪 Zadanie 19:
         * Wyobraz sobie 1 duza zmiane laczaca: nowa funkcje, refaktoryzacje
         * niepowiazanej metody, i poprawke literowki w komentarzu. W
         * komentarzu opisz, jak podzieliles by to na 3 OSOBNE, male PR-y do
         * latwiejszego review.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConductPeerReviewStyleAuditOfRealMethodFromEarlierChapter {
        /*
         * 🧪 Zadanie 20:
         * Wybierz 1 metode z dowolnej wczesniejszej lekcji kursu (np.
         * `_10_dao`). Napisz PELNY "komentarz recenzenta" oceniajacy ja wg
         * min. 4 zasad z tego rozdzialu, z jasnym rozroznieniem MUST FIX vs
         * NIT dla kazdej uwagi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConductFullReviewOfCapstoneBeforeClassFromTheory {
        /*
         * 🧪 Zadanie 21:
         * Odtworz klase `OrderProcessingEverything` z teorii lekcji (lub
         * napisz podobna wlasna). Napisz WLASNY, PELNY "komentarz recenzenta"
         * wskazujacy WSZYSTKIE naruszenia (min. 6), KAZDE z odwolaniem do
         * konkretnej lekcji rozdzialu - PRZED spojrzeniem na wersje "po" z
         * teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareOwnReviewWithTheoryVersion {
        /*
         * 🧪 Zadanie 22:
         * Porownaj swoj "komentarz recenzenta" z Zadania 21 z lista naruszen
         * wymieniona w komentarzu przy klasie `OrderProcessingEverything` w
         * teorii - w komentarzu wypisz, co ZNALAZLES tak samo, i co (jesli
         * cokolwiek) PRZEOCZYLES.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignOwnRealisticGodClassWithSixDifferentViolations {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj WLASNA (inna niz w teorii), realistyczna "brudna" klase
         * (np. system biblioteki, hotelu, czy sklepu) naruszajaca NARAZ 6
         * ROZNYCH zasad z tego rozdzialu (wybierz sam, roznych niz w
         * kapsztonie z teorii). Udokumentuj KAZDE naruszenie komentarzem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FullyRefactorOwnGodClassFromExercise23 {
        /*
         * 🧪 Zadanie 24:
         * W PELNI zrefaktoryzuj klase z Zadania 23, naprawiajac WSZYSTKICH 6
         * naruszen - zademonstruj identyczny wynik funkcjonalny "przed" i
         * "po" (porownanie programowe, nie tylko wizualne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SimulateMultiRoundReviewWithIncrementalFixes {
        /*
         * 🧪 Zadanie 25:
         * Napisz "brudna" metode. Zasymuluj 3 KOLEJNE rundy review: runda 1
         * (komentarz recenzenta + poprawka), runda 2 (recenzent zauwaza
         * KOLEJNY, wczesniej niewidoczny problem + poprawka), runda 3
         * (recenzent akceptuje, "LGTM"). Wypisz wersje metody po KAZDEJ
         * rundzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignReviewChecklistAsExecutableValidator {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode `List<String> quickReviewChecklist(int methodLines,
         * int paramCount, boolean hasNullChecks, boolean hasGenericCatch)`
         * zwracajaca liste automatycznie wykrytych "sygnalow ostrzegawczych"
         * (np. "Long Method" gdy methodLines>30). Przetestuj dla min. 3
         * roznych kombinacji wejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConductReviewCoveringNamingCommentsMethodsAndClasses {
        /*
         * 🧪 Zadanie 27:
         * Napisz klase naruszajaca NARAZ: Naming (Lesson02), zle komentarze
         * (Lesson03 - komentarz opisujacy OCZYWISTA rzecz), Methods (Lesson04
         * - zbyt wiele parametrow), Classes (Lesson06 - niska spojnosc).
         * Napisz "komentarz recenzenta" dla WSZYSTKICH 4 obszarow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullyAddressAllFourAreasFromExercise27 {
        /*
         * 🧪 Zadanie 28:
         * Napraw WSZYSTKIE 4 obszary z Zadania 27 - zademonstruj finalna,
         * poprawiona wersje klasy z identycznym zachowaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveTeamCodeReviewGuideline {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu KOMPLETNY, wlasny "przewodnik
         * code review" dla zespolu (min. 8 punktow) laczacy: co sprawdzac
         * (odwolania do lekcji rozdzialu), jak dawac feedback (MUST
         * FIX/nit/pytania/pochwaly), jak przyjmowac feedback, oraz zasade
         * malych PR-ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFinalCapstoneApplyingEntireChapterToNewDomain {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie CALEGO ROZDZIALU: zaprojektuj i zaimplementuj
         * KOMPLETNY, realistyczny system (INNY niz przyklady z teorii - np.
         * system zarzadzania biblioteka, hotelem, lub sklepem internetowym)
         * od PODSTAW, stosujac SWIADOMIE jak najwiecej zasad z calego
         * rozdzialu `_16_clean_code` naraz: dobre nazewnictwo (Lesson02),
         * male metody (Lesson04), male spojne klasy (Lesson06-07), min. 2
         * zasady SOLID (Lesson08-11), luzne sprzezenie (Lesson12), brak
         * duplikacji (Lesson13), zero code smelli (Lesson14), przemyslany
         * projekt wyjatkow (Lesson17), bezpieczna obsluge null (Lesson18),
         * niezmiennosc tam, gdzie to sensowne (Lesson19). Napisz KROTKI
         * "raport code review" (komentarz) potwierdzajacy zgodnosc z KAZDA z
         * tych zasad, i zademonstruj dzialanie calego systemu.
         */
        public static void main(String[] args) { }
    }
}
