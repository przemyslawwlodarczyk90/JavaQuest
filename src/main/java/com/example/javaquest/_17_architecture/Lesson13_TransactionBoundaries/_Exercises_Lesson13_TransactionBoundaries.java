package com.example.javaquest._17_architecture.Lesson13_TransactionBoundaries;

public class _Exercises_Lesson13_TransactionBoundaries {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainOneUseCaseOneTransactionRuleInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) zasade "1 przypadek uzycia = 1 transakcja".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimulateTransactionWrappingTwoOperations {
        /*
         * 🧪 Zadanie 2:
         * Uzywajac symulowanej `SimulatedTransaction` (jak w teorii lekcji),
         * napisz Use Case wykonujacy 2 operacje (np. "zapisz recenzje" +
         * "zaktualizuj srednia ocene") w 1 wspolnej granicy transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteRepositoryLevelBoundaryAntiPattern {
        /*
         * 🧪 Zadanie 3:
         * Napisz kod symulujacy 2 operacje "commitujace" OSOBNO (bez wspolnej
         * granicy) - jak w anty-wzorcu "granica za nisko" z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixRepositoryLevelBoundaryFromExercise03 {
        /*
         * 🧪 Zadanie 4:
         * Popraw kod z Zadania 3, opakowujac OBIE operacje w 1 wspolna
         * granice transakcji na poziomie Use Case.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyControllerLevelBoundaryAntiPattern {
        /*
         * 🧪 Zadanie 5:
         * Napisz "kontroler" otwierajacy transakcje i wolajacy 2
         * NIEPOWIAZANE serwisy w jej ramach - w komentarzu wskaz naruszenie
         * z teorii (granica za wysoko).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FixControllerLevelBoundaryFromExercise05 {
        /*
         * 🧪 Zadanie 6:
         * Popraw kod z Zadania 5, przenoszac KAZDA transakcje do WLASCIWEGO,
         * osobnego Use Case - kontroler przestaje "wiedziec" o transakcjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifySlowExternalCallInsideTransactionBoundary {
        /*
         * 🧪 Zadanie 7:
         * Napisz kod symulujacy wywolanie "wolnego zewnetrznego API"
         * WEWNATRZ granicy transakcji (miedzy 2 operacjami na Repository) - w
         * komentarzu wyjasnij ryzyko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixSlowExternalCallFromExercise07 {
        /*
         * 🧪 Zadanie 8:
         * Popraw kod z Zadania 7, przenoszac wywolanie zewnetrzne POZA
         * granice transakcji bazodanowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DesignRollbackScenarioForFailedStep {
        /*
         * 🧪 Zadanie 9:
         * Rozszerz `SimulatedTransaction` o obsluge bledu w 1 z operacji -
         * zademonstruj `rollback()` gdy 2. z 3 operacji "zawodzi" (rzuca
         * wyjatek), potwierdzajac ze ZADNA operacja nie zostala zastosowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListTransactionBoundaryAntiPatternsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) 2
         * anty-wzorce granic transakcji z tej lekcji i 1 pulapke.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignRealisticMultiStepUseCaseWithProperBoundary {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj Use Case "transfer srodkow miedzy kontami" z 3 krokami
         * (walidacja salda, odjecie z konta zrodlowego, dodanie do
         * docelowego) - WSZYSTKIE 3 kroki w 1 granicy transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DemonstrateRollbackWhenMiddleStepFailsInExercise11 {
        /*
         * 🧪 Zadanie 12:
         * Zasymuluj awarie 2. kroku (np. "konto docelowe nie istnieje") w
         * Use Case z Zadania 11 - zademonstruj, ze konto zrodlowe NIE
         * zostalo pomniejszone (rollback calosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAdrJustifyingTransactionBoundaryPlacement {
        /*
         * 🧪 Zadanie 13:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "granica transakcji na
         * poziomie Use Case, nie Repository ani Controller" - z
         * realistycznym kontekstem i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignPortsAndAdaptersStyleTransactionParticipation {
        /*
         * 🧪 Zadanie 14:
         * Korzystajac z terminologii Lesson11-12, zaprojektuj port driven
         * `AccountRepositoryPort`, ktorego metody UCZESTNICZA w transakcji
         * rozpoczetej przez Use Case (port driving) - adapter NIE
         * rozpoczyna/konczy transakcji samodzielnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureLockDurationDifferenceWithAndWithoutExternalCallInside {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj (przez `Thread.sleep` lub po prostu komentarz z
         * przyblizonym czasem) roznice w 'czasie trwania transakcji' miedzy
         * wersja Z wywolaniem zewnetrznym WEWNATRZ granicy i wersja BEZ -
         * wypisz porownanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignUseCaseCombiningTwoRepositoriesInOneBoundary {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj Use Case "zloz recenzje i przyznaj punkty
         * lojalnosciowe" uzywajacy 2 ROZNYCH repozytoriow w 1 WSPOLNEJ
         * granicy transakcji - zademonstruj udany przeplyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IdentifyTooBroadTransactionSpanningMultipleUseCases {
        /*
         * 🧪 Zadanie 17:
         * Napisz kod, w ktorym 1 transakcja OBEJMUJE 2 NIEPOWIAZANE
         * przypadki uzycia (np. "zloz zamowienie" + "zaktualizuj profil
         * uzytkownika") - w komentarzu wyjasnij, dlaczego to zbyt szeroka
         * granica, nawet jesli technicznie "dziala".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SplitTooBroadTransactionFromExercise17 {
        /*
         * 🧪 Zadanie 18:
         * Rozbij transakcje z Zadania 17 na 2 OSOBNE, niezalezne granice - w
         * komentarzu wyjasnij, dlaczego to jest poprawniejsze, nawet jesli
         * "wygodniej" bylo miec 1 duza transakcje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignReadOnlyOperationWithoutTransactionBoundary {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj metode TYLKO-ODCZYTU (np. "pobierz historie
         * zamowien") - w komentarzu wyjasnij, dlaczego taka operacja
         * ZAZWYCZAJ NIE POTRZEBUJE pelnej granicy transakcji zapisu
         * (choc w niektorych bazach nadal korzysta z transakcji tylko-do-odczytu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromDaoChapterForTransactionBoundaryPlacement {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_10_dao/Lesson17_TransactionsInServiceLayer` -
         * potwierdz, ze granica transakcji tam zyje DOKLADNIE w warstwie
         * Service (Use Case), zgodnie z zasada z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullOrderFulfillmentUseCaseWithProperTransactionBoundary {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny Use Case "realizacja zamowienia" z 4 krokami
         * (walidacja stanu, rezerwacja, zapis zamowienia, aktualizacja
         * limitu kredytowego klienta) - WSZYSTKIE 4 w 1 granicy transakcji,
         * z pelna obsluga rollback dla KAZDEGO mozliwego punktu awarii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DemonstrateAllFourFailurePointsWithProperRollback {
        /*
         * 🧪 Zadanie 22:
         * Dla Use Case z Zadania 21, zademonstruj PO KOLEI awarie na
         * KAZDYM z 4 krokow (4 osobne scenariusze) - potwierdz, ze w
         * KAZDYM przypadku WSZYSTKIE wczesniejsze kroki zostaly wycofane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignSagaLikePatternForCrossSystemConsistencyPreview {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (koncepcyjnie, z prostym kodem) sytuacje, gdzie
         * spojnosc MUSI objac 2 ROZNE systemy (baza + symulowane zewnetrzne
         * API platnosci) - zamiast 1 transakcji, zaprojektuj 2 KROKI z
         * "kompensacja" (cofnieciem) drugiego kroku, gdyby pierwszy sie
         * powiodl, a drugi nie - to wprowadzenie do wzorca Saga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildTransactionBoundaryAuditHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> auditTransactionBoundaries(int
         * repositoriesCommittingIndependently, boolean controllerOwnsTransaction,
         * boolean externalCallInsideBoundary)` zwracajaca liste wykrytych
         * problemow na podstawie prostych flag - przetestuj dla min. 3
         * kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorRealisticSystemWithAllThreeAntiPatternsAtOnce {
        /*
         * 🧪 Zadanie 25:
         * Napisz REALISTYCZNY system NARUSZAJACY WSZYSTKIE 3 problemy z tej
         * lekcji naraz (granica za nisko, za wysoko, wolne wywolanie
         * wewnatrz) - w PELNI zrefaktoryzuj, krok po kroku
         * (`_16_clean_code/Lesson15`), weryfikujac po kazdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignTransactionBoundaryRespectingHexagonalPortStructure {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj system w PELNI zgodny z terminologia Lesson11-12: port
         * driving `PlaceOrderUseCase` zarzadza granica transakcji, min. 2
         * porty driven UCZESTNICZA (repozytorium zamowien, repozytorium
         * magazynu) - zademonstruj pelny przeplyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForHandlingCrossSystemConsistencyWithoutDistributedTransactions {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR uzasadniajacy REZYGNACJE z rozproszonych
         * transakcji (2-fazowy commit) na rzecz wzorca kompensacji/Saga
         * (Zadanie 23) dla integracji z zewnetrznym systemem platnosci - z
         * kontekstem i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareTransactionScopeForBatchVsInteractiveUseCase {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj 2 Use Case: (a) interaktywny (1 zamowienie na raz), (b)
         * wsadowy (przetwarzanie 100 zamowien) - w komentarzu opisz, czy (b)
         * powinien byc 1 WIELKA transakcja, czy 100 MALYCH - uzasadnij
         * (podpowiedz: bardzo dluga transakcja tez ma koszty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveTransactionBoundaryDesignChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do projektowania granic transakcji w realnym systemie -
         * laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullTransactionalSystemForBankingApp {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * realistyczny system bankowy (min. 3 Use Case: otwarcie konta,
         * transfer srodkow, zamkniecie konta) z: granica transakcji
         * DOKLADNIE na poziomie kazdego Use Case, ZERO wywolan zewnetrznych
         * wewnatrz granic, pelna obsluga rollback dla WSZYSTKICH mozliwych
         * awarii. Zademonstruj wszystkie 3 Use Case, WLACZNIE z min. 2
         * scenariuszami rollback.
         */
        public static void main(String[] args) { }
    }
}
