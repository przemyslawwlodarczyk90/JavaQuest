package com.example.javaquest._23_spring_data_jpa.Lesson08_TransactionsInSpring;

public class _Exercises_Lesson08_TransactionsInSpring {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyTransactionBoundaryBelongsInService {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_17_architecture/Lesson13`): wyjasnij,
         * dlaczego `@Transactional` POWINIEN byc NA warstwie serwisu,
         * NIE kontrolera/repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnTransactionalService {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY serwis Z metoda `@Transactional`
         * LACZACA 2+ operacje repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementRollbackDemo {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj metode `@Transactional`, KTORA rzuca
         * `RuntimeException` W POLOWIE - zweryfikuj COFNIECIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementReadOnlyTransaction {
        /*
         * 🧪 Zadanie 4:
         * Dodaj `@Transactional(readOnly = true)` DO metody TYLKO-
         * ODCZYTU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerCheckedExceptionNoRollback {
        /*
         * 🧪 Zadanie 5:
         * Rzuc CHECKED wyjatek W `@Transactional` - zweryfikuj, ze
         * DANE ZOSTALY zapisane MIMO wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementRollbackForOnCheckedException {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `rollbackFor = Exception.class` - zweryfikuj, ze TERAZ
         * checked wyjatek TEZ COFA transakcje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TriggerSelfInvocationBypassOwn {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj WLASNY przyklad self-invocation - zweryfikuj,
         * ze `@Transactional` PRZESTAJE DZIALAC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixSelfInvocationBySplittingClasses {
        /*
         * 🧪 Zadanie 8:
         * Napraw problem Z Zadania 7 PRZEZ PODZIAL NA 2 KLASY (wywolanie
         * PRZEZ WSTRZYKNIETY bean, NIE `this`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareNoOpTransactionalOnPrivateMethod {
        /*
         * 🧪 Zadanie 9:
         * Dodaj `@Transactional` NA METODZIE `private` - zweryfikuj,
         * CZY DZIALA (Spring AOP wymaga metod PUBLICZNYCH DLA proxy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDefaultRollbackRuleForRuntimeVsChecked {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij DOMYSLNA regule `@Transactional` -
         * KIEDY cofa, A KIEDY NIE.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementNoRollbackForSpecificException {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `noRollbackFor = WlasnyWyjatek.class` - zweryfikuj, ze
         * TA KONKRETNA RuntimeException NIE COFA transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPropagationRequiresNew {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `Propagation.REQUIRES_NEW` - zweryfikuj, ze WEWNETRZNA
         * metoda dziala W OSOBNEJ transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementPropagationNested {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `Propagation.NESTED` - zweryfikuj RÓZNICE wzgledem
         * `REQUIRES_NEW`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementTransactionTimeoutDemo {
        /*
         * 🧪 Zadanie 14:
         * Ustaw `@Transactional(timeout = 1)` (1 sekunda) - zasymuluj
         * DLUGA operacje I zweryfikuj TIMEOUT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementIsolationLevelDemo {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_12_hibernate/Lesson26` - zaimplementuj
         * `@Transactional(isolation = Isolation.READ_COMMITTED)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementTransactionSynchronizationCallback {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj `TransactionSynchronization` (np. logowanie PO
         * COMMIT/ROLLBACK).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementProgrammaticTransactionTemplate {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z teoria - uzyj `TransactionTemplate` ZAMIAST
         * `@Transactional` (PROGRAMOWA kontrola transakcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareDeclarativeVsProgrammaticTransactions {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: porownaj DEKLARATYWNE (`@Transactional`) Z
         * PROGRAMOWYM (`TransactionTemplate`) zarzadzaniem transakcjami -
         * kiedy KTORE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureTransactionOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz narzut `@Transactional` NA POJEDYNCZA operacje
         * repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildTransactionalPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" atrybutow `@Transactional`
         * (propagation/isolation/readOnly/rollbackFor/timeout).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementMultiRepositoryComplexTransaction {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj serwis LACZACY 3+ repozytoria W JEDNEJ
         * transakcji (zlozony scenariusz biznesowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDistributedTransactionSimulationConceptually {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wyjasnij, DLACZEGO `@Transactional` NIE
         * ROZWIAZUJE transakcji ROZPROSZONYCH (WIELE baz/serwisow) -
         * jakie SA ALTERNATYWY (Saga)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementOptimisticLockingWithinTransaction {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_12_hibernate/Lesson25` - polacz `@Transactional`
         * Z `@Version` - zweryfikuj `OptimisticLockingFailureException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAsyncTransactionalMethodPitfall {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_05_multithreading` - zademonstruj, DLACZEGO
         * `@Async` + `@Transactional` NA TEJ SAMEJ metodzie DZIALA
         * NIEOCZYWISCIE (RÓZNE watki, RÓZNE konteksty transakcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementTransactionalEventListener {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_20_spring_core/Lesson20_ApplicationEvents` - uzyj
         * `@TransactionalEventListener(phase = AFTER_COMMIT)` -
         * zweryfikuj, ze zdarzenie ODPALA SIE TYLKO PO udanym commicie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementDeadlockSimulationAndRecovery {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_05_multithreading/Lesson25_Deadlock` - zasymuluj
         * DEADLOCK 2 rownoleglych transakcji NA TYCH SAMYCH wierszach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRetryOnTransientFailure {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj RETRY (Spring `@Retryable` lub reczna petla)
         * PRZY PRZEJSCIOWYM bledzie transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAuditedTransactionBoundaryLogging {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj POCZATEK/
         * KONIEC (commit/rollback) KAZDEJ WAZNEJ transakcji biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareTransactionBoundariesAcrossArchitectureStyles {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (powiaz z `_17_architecture/Lesson17_ModularMonolith`):
         * wyjasnij, JAK granice transakcji ZMIENIAJA SIE PRZY PRZEJSCIU
         * OD monolitu DO mikroserwisow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteBankingTransferSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY system transferow bankowych - transakcje +
         * blokowanie optymistyczne (Zadanie 23) + audyt (Zadanie 28) +
         * zdarzenia PO commicie (Zadanie 25) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
