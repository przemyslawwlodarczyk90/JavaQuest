package com.example.javaquest._12_hibernate.Lesson08_Transactions;

public class _Exercises_Lesson08_Transactions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicBeginCommit {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Wallet (id, owner, balance) na H2
         * ("jdbc:h2:mem:l08ex01;DB_CLOSE_DELAY=-1"). Otworz transakcje, zapisz obiekt,
         * wywolaj commit() - zweryfikuj zapis w NOWEJ Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RollbackDiscardsChanges {
        /*
         * 🧪 Zadanie 2:
         * Otworz transakcje, zapisz Wallet, wywolaj rollback() ZAMIAST commit().
         * Zweryfikuj w NOWEJ Session, ze obiekt NIE istnieje w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PersistWithoutTransactionError {
        /*
         * 🧪 Zadanie 3:
         * Sprobuj wywolac session.persist() BEZ wczesniejszego beginTransaction().
         * Zapisz w komentarzu, czy i jaki blad Hibernate zwraca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TwoOperationsAtomicity {
        /*
         * 🧪 Zadanie 4:
         * W JEDNEJ transakcji zapisz DWA portfele (Wallet). Jesli DRUGI zapis
         * naruszy ograniczenie (np. nullable=false na owner=null), zlap wyjatek i
         * wywolaj rollback - zweryfikuj, ze PIERWSZY portfel TEZ nie zostal zapisany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryFinallySessionCloseGuarantee {
        /*
         * 🧪 Zadanie 5:
         * Napisz wzorzec try-finally gwarantujacy zamkniecie Session NAWET jesli
         * transakcja rzuci wyjatek w trakcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleSequentialTransactions {
        /*
         * 🧪 Zadanie 6:
         * W JEDNEJ Session wykonaj DWIE kolejne, osobne transakcje (begin/commit
         * dwukrotnie) - kazda zapisujaca inny portfel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TransactionIsActiveCheck {
        /*
         * 🧪 Zadanie 7:
         * Uzyj transaction.isActive() PRZED i PO commit() - wypisz oba wyniki
         * (spodziewaj sie true przed, false po).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadOperationInsideTransaction {
        /*
         * 🧪 Zadanie 8:
         * Wykonaj find() (odczyt) W OBREBIE aktywnej transakcji (begin, find,
         * commit) - jako DOBRA PRAKTYKA, mimo ze find() technicznie moze dzialac
         * tez bez jawnej transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CommitTwiceError {
        /*
         * 🧪 Zadanie 9:
         * Wywolaj commit() DWA razy na tej samej Transaction (bez nowego begin
         * pomiedzy). Zapisz w komentarzu blad, jaki zwraca Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RollbackAfterCommitError {
        /*
         * 🧪 Zadanie 10:
         * Wywolaj commit(), a POTEM sprobuj wywolac rollback() na TEJ SAMEJ
         * Transaction. Zapisz w komentarzu, co sie dzieje.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TransferMoneyBetweenTwoWallets {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj realistyczny "przelew": w JEDNEJ transakcji odejmij 100 z
         * portfela A i dodaj 100 do portfela B - zweryfikuj sumaryczne saldo PRZED i
         * PO (powinno byc identyczne - zachowana suma).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TransferFailsHalfwayRollsBackBoth {
        /*
         * 🧪 Zadanie 12:
         * Ten sam przelew co Zadanie 11, ale z CELOWYM bledem PO odjeciu od A, a
         * PRZED dodaniem do B (np. rzucona RuntimeException). Zlap wyjatek, wywolaj
         * rollback i zweryfikuj, ze saldo A WROCILO do stanu pierwotnego (atomowosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NestedTryResourcesPattern {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode "executeInTransaction(SessionFactory, Consumer<Session>)"
         * przyjmujaca lambde z logika biznesowa - metoda SAMA zarzadza
         * begin/commit/rollback/close, kod wolajacy przekazuje TYLKO logike.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TransactionTimeoutSimulation {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj (jesli dostepne) timeout transakcji (transaction.setTimeout(N))
         * i zasymuluj DLUGA operacje (Thread.sleep) przekraczajaca ten limit -
         * zapisz w komentarzu zaobserwowane zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleEntitiesOneAtomicOperation {
        /*
         * 🧪 Zadanie 15:
         * W JEDNEJ transakcji zapisz Wallet ORAZ powiazany (bez @OneToMany, prosty
         * osobny wiersz) rekord "TransactionLog" - zapisz w komentarzu, dlaczego oba
         * MUSZA byc w tej samej transakcji (spojnosc logu operacji z rzeczywistym saldem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RollbackOnlyFlag {
        /*
         * 🧪 Zadanie 16:
         * Uzyj transaction.markRollbackOnly() (natywne Hibernate) PO wykonaniu
         * operacji, ale PRZED jawnym rollback()/commit() - zapisz w komentarzu, co
         * sie stanie przy probie commit() po oznaczeniu markRollbackOnly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ReadCommittedIsolationDemo {
        /*
         * 🧪 Zadanie 17:
         * Uruchom DWA watki (jak w _05_multithreading): watek A modyfikuje portfel w
         * NIEZATWIERDZONEJ transakcji, watek B probuje odczytac ten sam portfel -
         * zapisz w komentarzu, czy B widzi ZMIENIONA czy ORYGINALNA wartosc
         * (poziom izolacji READ_COMMITTED, domyslny w wiekszosci baz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TransactionalAnnotationSketch {
        /*
         * 🧪 Zadanie 18:
         * Bez Springa: napisz WLASNA, prosta adnotacje @MyTransactional (bez logiki -
         * tylko deklaracja) i komentarz wyjasniajacy, jak Spring @Transactional
         * (temat przyszlego rozdzialu) automatyzowalby to, co teraz robisz recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompensatingActionOnFailure {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj wzorzec "compensating action" - jesli transakcja A (zapis do
         * bazy) sie powiedzie, ale NASTEPNA operacja (symulowana, poza baza, np.
         * wyslanie powiadomienia) zawiedzie, wykonaj DODATKOWA transakcje cofajaca
         * skutki pierwszej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureTransactionOverhead {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas 1000 operacji: raz KAZDA w OSOBNEJ transakcji (begin/commit za
         * kazdym razem), raz WSZYSTKIE w JEDNEJ transakcji - zapisz oba czasy w
         * komentarzu i wyjasnij roznice (narzut na commit).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BankingSystemWithAuditLog {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj system z Wallet + TransactionLog (osobna encja) - kazdy przelew
         * (Zadanie 11) MUSI zapisac WPIS do logu W TEJ SAMEJ transakcji co zmiane
         * salda. Zademonstruj, ze blad w zapisie logu cofa TEZ zmiane salda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DeadlockSimulationTwoWallets {
        /*
         * 🧪 Zadanie 22:
         * Uruchom DWA watki wykonujace przelewy w PRZECIWNEJ kolejnosci (watek A:
         * lock Wallet1 potem Wallet2; watek B: lock Wallet2 potem Wallet1) z
         * bezpiecznym timeoutem (jak w _05_multithreading/Lesson25_Deadlock) -
         * zaobserwuj i opisz w komentarzu ewentualny deadlock/timeout.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TransactionTemplateAbstraction {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz "executeInTransaction" z Zadania 13 o obsluge WARTOSCI zwracanej
         * (Function<Session, T> zamiast Consumer) - zademonstruj uzycie do
         * zwrocenia wyniku zapytania z transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiStepSagaPattern {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj UPROSZCZONY wzorzec "saga" (kilka MALYCH, niezaleznych
         * transakcji zamiast jednej wielkiej) dla scenariusza "zloz zamowienie":
         * transakcja 1 (utworz zamowienie), transakcja 2 (zarezerwuj towar) - z
         * reczna kompensacja, jesli transakcja 2 zawiedzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LongRunningTransactionRisk {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj "dluga" transakcje (Thread.sleep 2s WEWNATRZ aktywnej
         * transakcji) i RÓWNOLEGLY watek probujacy inna operacje na TEJ SAMEJ bazie -
         * zapisz w komentarzu obserwowany wplyw na przepustowosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TransactionalBoundaryAcrossServiceMethods {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (kod) 2-warstwowa architekture: WalletRepository (operacje na
         * pojedynczym Wallet, BEZ wlasnego zarzadzania transakcja) i WalletService
         * (otwiera transakcje i woła repository) - zademonstruj przelew (Zadanie 11)
         * w tej architekturze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IsolationLevelConfiguration {
        /*
         * 🧪 Zadanie 27:
         * Skonfiguruj hibernate.connection.isolation na rozne wartosci (np. 2 =
         * READ_COMMITTED, 8 = SERIALIZABLE) i zapisz w komentarzu, czy zaobserwowales
         * roznice w zachowaniu przy prostym scenariuszu odczyt+zapis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullAuditTrailWithRollbackTest {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz system z Zadania 21 o test negatywny: sprobuj wykonac przelew
         * kwoty WIEKSZEJ niz saldo (walidacja biznesowa rzuca wyjatek PRZED
         * zapisaniem zmian) - zweryfikuj, ze ANI saldo, ANI log NIE zostaly
         * zmienione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareWithJdbcManualTransactionManagement {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj TEN SAM przelew (Zadanie 11) czystym JDBC z recznym
         * conn.setAutoCommit(false)/commit()/rollback() (jak w _09_jdbc/Lesson15) -
         * porownaj w komentarzu z podejsciem Hibernate Transaction.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTransactionalBankingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY, mini-system bankowy (Wallet +
         * TransactionLog) z: przelewami (Zadanie 11), atomowym cofaniem przy bledzie
         * (Zadanie 12/28), warstwa Service/Repository (Zadanie 26) i testem
         * wspolbieznym (Zadanie 22 lub 25) - zademonstruj wszystko w main().
         */
        public static void main(String[] args) { }
    }
}
