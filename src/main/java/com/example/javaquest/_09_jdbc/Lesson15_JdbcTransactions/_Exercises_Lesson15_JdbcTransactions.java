package com.example.javaquest._09_jdbc.Lesson15_JdbcTransactions;

public class _Exercises_Lesson15_JdbcTransactions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateAccountsTableAndAutoCommitDefault {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą "jdbc:h2:mem:lesson15ex01;DB_CLOSE_DELAY=-1". Utwórz tabelę
         * accounts (id INT PRIMARY KEY, owner VARCHAR(100), balance INT) i wstaw 2 wiersze:
         * (1, 'Konto A', 1000) oraz (2, 'Konto B', 500). Zaraz po otwarciu połączenia wypisz
         * wynik connection.getAutoCommit() - powinien być true (tryb domyślny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ManualCommitAfterInsert {
        /*
         * 🧪 Zadanie 2:
         * Na bazie "jdbc:h2:mem:lesson15ex02;DB_CLOSE_DELAY=-1" z tabelą accounts jak
         * w Zadaniu 1, wywołaj connection.setAutoCommit(false), wstaw wiersz (3, 'Konto C', 300),
         * a następnie connection.commit(). Wykonaj SELECT COUNT(*) i sprawdź, że wiersz
         * jest widoczny (czyli commit faktycznie zatwierdził zmianę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ManualRollbackAfterInsert {
        /*
         * 🧪 Zadanie 3:
         * Na bazie "jdbc:h2:mem:lesson15ex03;DB_CLOSE_DELAY=-1" z tabelą accounts,
         * wywołaj setAutoCommit(false), wstaw wiersz (4, 'Konto D', 100), a następnie
         * connection.rollback() (bez commit). Wykonaj SELECT COUNT(*) WHERE id = 4
         * i sprawdź, że wiersza NIE MA - rollback cofnął wstawienie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RollbackAfterFailedUpdate {
        /*
         * 🧪 Zadanie 4:
         * Tabela accounts ma ograniczenie CHECK (balance >= 0). Na bazie
         * "jdbc:h2:mem:lesson15ex04;DB_CLOSE_DELAY=-1" wstaw konto (1,'Konto A', 100) z
         * autoCommit(false), następnie spróbuj UPDATE accounts SET balance = balance - 500
         * WHERE id = 1 (naruszy CHECK). Złap SQLException, wywołaj rollback() i wypisz
         * treść wyjątku oraz saldo konta PO rollbacku (powinno być niezmienione: 100).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TransferMoneySuccess {
        /*
         * 🧪 Zadanie 5:
         * Na bazie "jdbc:h2:mem:lesson15ex05;DB_CLOSE_DELAY=-1" z kontami (1,'A',1000) i
         * (2,'B',500), napisz metodę transferMoney(Connection, fromId, toId, amount)
         * analogiczną do tej z lekcji (setAutoCommit(false) -> 2 UPDATE -> commit ->
         * finally setAutoCommit(true)). Wykonaj przelew 300 z konta A do B i wypisz
         * salda obu kont po przelewie (A=700, B=800).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TransferMoneyInsufficientFunds {
        /*
         * 🧪 Zadanie 6:
         * Używając metody transferMoney z Zadania 5 na bazie
         * "jdbc:h2:mem:lesson15ex06;DB_CLOSE_DELAY=-1" (konta A=1000, B=500), spróbuj
         * przelać 10 000 (więcej niż jest na koncie A - CHECK balance >= 0 odrzuci UPDATE).
         * Złap wyjątek, wypisz komunikat i sprawdź, że oba salda pozostały NIEZMIENIONE
         * (A=1000, B=500) - potwierdzenie, że rollback cofnął CAŁĄ transakcję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FinallyRestoresAutoCommit {
        /*
         * 🧪 Zadanie 7:
         * Na bazie "jdbc:h2:mem:lesson15ex07;DB_CLOSE_DELAY=-1", przed wywołaniem
         * transferMoney() (Zadanie 5) wypisz connection.getAutoCommit() (true).
         * Wykonaj udany przelew, a PO jego zakończeniu wypisz getAutoCommit() ponownie -
         * musi znowu być true, mimo że wewnątrz metody był ustawiony na false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleInsertsSingleCommit {
        /*
         * 🧪 Zadanie 8:
         * Na bazie "jdbc:h2:mem:lesson15ex08;DB_CLOSE_DELAY=-1" z pustą tabelą accounts,
         * ustaw autoCommit(false) i wstaw 5 kont (id 1-5, różne salda) używając 5 osobnych
         * executeUpdate(), ale wywołaj commit() dopiero RAZ, na końcu, po wszystkich
         * wstawieniach. Sprawdź SELECT COUNT(*) - powinno być 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CommitWithoutErrorCheck {
        /*
         * 🧪 Zadanie 9:
         * Na bazie "jdbc:h2:mem:lesson15ex09;DB_CLOSE_DELAY=-1", otwórz Connection w
         * try-with-resources, wykonaj prostą transakcję (1 INSERT + commit) i sprawdź,
         * że po wyjściu z bloku try-with-resources (zamknięcie connection) dane nadal
         * istnieją, jeśli otworzysz NOWE połączenie do tej samej bazy w pamięci
         * (dzięki DB_CLOSE_DELAY=-1 baza żyje dalej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TwoSeparateTransactions {
        /*
         * 🧪 Zadanie 10:
         * Na bazie "jdbc:h2:mem:lesson15ex10;DB_CLOSE_DELAY=-1" wykonaj DWIE niezależne
         * transakcje na tym samym connection: pierwsza wstawia konto (1,'A',1000) i
         * commituje, druga aktualizuje saldo konta 1 (+200) i też commituje. Po KAŻDEJ
         * transakcji wypisz aktualne saldo konta 1, żeby pokazać, że stan bazy narasta
         * kolejno.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenericRunInTransactionMethod {
        /*
         * 🧪 Zadanie 11:
         * Na bazie "jdbc:h2:mem:lesson15ex11;DB_CLOSE_DELAY=-1" napisz WŁASNĄ, uproszczoną
         * wersję generycznej metody runInTransaction(Connection, TransactionalOperation<T>)
         * z lekcji (setAutoCommit(false) -> operacja -> commit -> finally
         * setAutoCommit(true)). Użyj jej do wykonania INSERT konta oraz zwrócenia jego
         * id jako wyniku T.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThreeWayTransferAtomicity {
        /*
         * 🧪 Zadanie 12:
         * Na bazie "jdbc:h2:mem:lesson15ex12;DB_CLOSE_DELAY=-1" z kontami A=1000, B=500,
         * C=200, wykonaj w JEDNEJ transakcji łańcuch: A->B 100, potem B->C 5000 (za dużo,
         * naruszy CHECK). Zapewnij, że błąd w DRUGIM kroku cofa RÓWNIEŻ pierwszy (udany)
         * krok - po rollbacku wszystkie 3 salda muszą wrócić do wartości początkowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SuppressedRollbackException {
        /*
         * 🧪 Zadanie 13:
         * Na bazie "jdbc:h2:mem:lesson15ex13;DB_CLOSE_DELAY=-1" zasymuluj sytuację, w
         * której zarówno operacja SQL, jak i następujący po niej rollback() kończą się
         * błędem (np. zamknij connection.close() PRZED wywołaniem rollback() wewnątrz
         * bloku catch, co spowoduje SQLException z rollback()). Dołącz wyjątek z
         * rollbacku do oryginalnego przez e.addSuppressed(...) i wypisz oba
         * (e.getMessage() oraz e.getSuppressed()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PartialUpdateRollback {
        /*
         * 🧪 Zadanie 14:
         * Na bazie "jdbc:h2:mem:lesson15ex14;DB_CLOSE_DELAY=-1" z kontem A=1000, wykonaj
         * w jednej transakcji: pierwszy UPDATE (balance - 100, poprawny), drugi UPDATE
         * odwołujący się do NIEISTNIEJĄCEJ kolumny (błędny SQL, rzuci SQLException).
         * Złap wyjątek, wywołaj rollback() i sprawdź, że saldo konta A wróciło do 1000 -
         * czyli PIERWSZY (poprawny) UPDATE też został cofnięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TransactionalOperationInterfaceReuse {
        /*
         * 🧪 Zadanie 15:
         * Na bazie "jdbc:h2:mem:lesson15ex15;DB_CLOSE_DELAY=-1" zdefiniuj własny
         * funkcyjny interfejs TransactionalOperation<T> (jak w lekcji) i użyj TEJ SAMEJ
         * metody runInTransaction do wykonania DWÓCH różnych operacji: raz INSERT konta,
         * raz SELECT liczący konta (COUNT(*)) - pokaż, że jedna metoda obsługuje różne
         * typy wyniku T dzięki generyczności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PreserveOriginalAutoCommitState {
        /*
         * 🧪 Zadanie 16:
         * Na bazie "jdbc:h2:mem:lesson15ex16;DB_CLOSE_DELAY=-1" ustaw RĘCZNIE
         * connection.setAutoCommit(false) PRZED wywołaniem runInTransaction (Zadanie 11).
         * Upewnij się, że Twoja metoda odczytuje getAutoCommit() PRZED zmianą i
         * przywraca DOKŁADNIE ten stan (false) w finally, zamiast na sztywno ustawiać
         * true. Wypisz getAutoCommit() przed i po wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RetryOnFailureLoop {
        /*
         * 🧪 Zadanie 17:
         * Na bazie "jdbc:h2:mem:lesson15ex17;DB_CLOSE_DELAY=-1" napisz pętlę próbującą
         * wykonać transferMoney(A, B, 5000) (celowo za dużo - zawsze się nie powiedzie)
         * maksymalnie 3 razy (retry). Po każdej nieudanej próbie wypisz numer próby i
         * komunikat błędu, a po 3 nieudanych próbach wypisz "Rezygnuje po 3 probach.".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BatchOfTransfersOneTransaction {
        /*
         * 🧪 Zadanie 18:
         * Na bazie "jdbc:h2:mem:lesson15ex18;DB_CLOSE_DELAY=-1" z 3 kontami (A=1000,
         * B=500, C=200), wykonaj listę przelewów List<int[]>{{1,2,100},{2,3,50},{1,3,900}}
         * (trzeci przelew jest za duży) w JEDNEJ transakcji - jeśli KTÓRYKOLWIEK się nie
         * powiedzie, CAŁA lista ma zostać cofnięta. Sprawdź salda po nieudanej próbie
         * (muszą być identyczne jak na początku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_VerifyIsolationWithinSameConnection {
        /*
         * 🧪 Zadanie 19:
         * Na bazie "jdbc:h2:mem:lesson15ex19;DB_CLOSE_DELAY=-1", wewnątrz jednej
         * transakcji (setAutoCommit(false)) wstaw nowe konto, ale PRZED wywołaniem
         * commit() wykonaj na TYM SAMYM connection zapytanie SELECT sprawdzające, czy
         * nowe konto jest widoczne. Wypisz wynik - własna, niezatwierdzona jeszcze
         * transakcja widzi swoje zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CustomExceptionWrappingSQLException {
        /*
         * 🧪 Zadanie 20:
         * Na bazie "jdbc:h2:mem:lesson15ex20;DB_CLOSE_DELAY=-1" zdefiniuj własny,
         * niesprawdzany wyjątek TransferFailedException(String message, Throwable cause)
         * opakowujący SQLException. Zmodyfikuj wywołanie runInTransaction tak, by w
         * przypadku błędu SQL łapał SQLException i rzucał TransferFailedException -
         * zademonstruj, że wyjątek poprawnie propaguje się na zewnątrz metody main.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TransactionalBatchTransferReport {
        /*
         * 🧪 Zadanie 21:
         * Na bazie "jdbc:h2:mem:lesson15ex21;DB_CLOSE_DELAY=-1" z 5 kontami, wykonaj w
         * JEDNEJ transakcji serię 5 przelewów między różnymi parami kont (kilka celowo
         * za dużych). Zbierz raport (Map lub List) z wynikiem KAŻDEGO przelewu
         * (sukces/porażka + komunikat), ale pamiętaj, że cała transakcja jest
         * all-or-nothing - jeśli którykolwiek się nie powiedzie, wypisz raport PRZED
         * rzuceniem wyjątku dalej i wywołaniem rollback().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NestedTryWithResourcesAndTransaction {
        /*
         * 🧪 Zadanie 22:
         * Na bazie "jdbc:h2:mem:lesson15ex22;DB_CLOSE_DELAY=-1" napisz metodę, która
         * wewnątrz jednej transakcji otwiera i zamyka (try-with-resources) TRZY różne
         * PreparedStatement (insert, update, select weryfikujący) - upewnij się, że
         * wszystkie trzy poprawnie się zamykają nawet gdy trzeci (select) rzuci
         * wyjątek, a cała transakcja i tak zostanie wycofana przez rollback().
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TransactionalMoneyLaunderingAudit {
        /*
         * 🧪 Zadanie 23:
         * Na bazie "jdbc:h2:mem:lesson15ex23;DB_CLOSE_DELAY=-1" dodaj tabelę audit_log
         * (id, message). Wykonaj przelew, który się NIE powiedzie (rollback), ale ZAPISZ
         * wpis w audit_log informujący o próbie - w OSOBNEJ, NIEZALEŻNEJ transakcji
         * (osobny commit), tak aby log przetrwał mimo cofnięcia głównej operacji.
         * Sprawdź na końcu, że audit_log zawiera wpis, a salda kont są niezmienione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_GenericRepositoryWithTransactionalSave {
        /*
         * 🧪 Zadanie 24:
         * Na bazie "jdbc:h2:mem:lesson15ex24;DB_CLOSE_DELAY=-1" napisz klasę
         * AccountRepository z metodą transferMoney(fromId, toId, amount) wewnętrznie
         * korzystającą z generycznego runInTransaction (Zadanie 11). Przetestuj ją dla
         * udanego i nieudanego przelewu, wypisując salda po każdej próbie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareAutoCommitPerformanceOverhead {
        /*
         * 🧪 Zadanie 25:
         * Na bazie "jdbc:h2:mem:lesson15ex25;DB_CLOSE_DELAY=-1" zmierz (System.nanoTime())
         * czas wstawienia 200 wierszy do tabeli accounts DWOMA sposobami: (a) z domyślnym
         * autoCommit=true (każdy INSERT to osobna mini-transakcja), (b) w JEDNEJ
         * transakcji (setAutoCommit(false) + 1 commit na końcu). Wypisz oba czasy i
         * różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RollbackOnBusinessRuleViolation {
        /*
         * 🧪 Zadanie 26:
         * Na bazie "jdbc:h2:mem:lesson15ex26;DB_CLOSE_DELAY=-1" dodaj w Javie (PRZED
         * wykonaniem jakiegokolwiek SQL) regułę biznesową: pojedynczy przelew nie może
         * przekraczać 2000. Jeśli przekracza, rzuć własny wyjątek natychmiast, BEZ
         * dotykania bazy danych. Zademonstruj próbę przelewu 3000 - sprawdź, że żadne
         * zapytanie SQL nie zostało wykonane (np. przez licznik wywołań albo brak
         * zmiany w tabeli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiStepOrderCreationTransaction {
        /*
         * 🧪 Zadanie 27:
         * Na bazie "jdbc:h2:mem:lesson15ex27;DB_CLOSE_DELAY=-1" utwórz tabele orders
         * (id, total) i order_items (id, order_id, product_name, quantity) oraz products
         * (id, name, stock). W JEDNEJ transakcji: wstaw zamówienie, wstaw 2 pozycje
         * zamówienia i zaktualizuj stan magazynowy (stock - quantity, z CHECK stock>=0).
         * Jeśli któraś pozycja przekracza dostępny stan, CAŁA operacja (insert
         * zamówienia + wszystkie pozycje) ma zostać cofnięta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionTemplateWithTimeoutSimulation {
        /*
         * 🧪 Zadanie 28:
         * Na bazie "jdbc:h2:mem:lesson15ex28;DB_CLOSE_DELAY=-1" rozszerz generyczny
         * runInTransaction (Zadanie 11) o pomiar czasu wykonania operacji (nanoTime).
         * Jeśli operacja trwała dłużej niż 50 ms, wypisz ostrzeżenie "Wolna transakcja!".
         * Zademonstruj to na dwóch operacjach: szybkiej (zwykły UPDATE) i celowo
         * spowolnionej (Thread.sleep(100) wewnątrz operacji przed commitem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConcurrentTransferSimulationWithTwoThreads {
        /*
         * 🧪 Zadanie 29:
         * Na bazie "jdbc:h2:mem:lesson15ex29;DB_CLOSE_DELAY=-1" z kontami A=1000, B=1000,
         * uruchom DWA wątki, każdy z WŁASNYM Connection do tej samej bazy: jeden
         * przelewa 100 z A do B, drugi 100 z B do A, obydwa po 20 razy w pętli. Poczekaj
         * na zakończenie obu wątków (join, z rozsądnym limitem czasu) i zweryfikuj, że
         * SUMA sald (A+B) pozostała niezmieniona (2000) - niezależnie od kolejności
         * wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTransactionFrameworkWithLoggingAndMetrics {
        /*
         * 🧪 Zadanie 30:
         * Na bazie "jdbc:h2:mem:lesson15ex30;DB_CLOSE_DELAY=-1" połącz techniki z całej
         * lekcji w jeden mini "transaction manager": generyczny runInTransaction +
         * logowanie do audit_log w OSOBNEJ transakcji (przetrwa rollback głównej) +
         * pomiar czasu każdej transakcji + retry (do 3 prób) przy konkretnym typie
         * błędu. Przetestuj na 3 scenariuszach: udany przelew, nieudany przelew (za duża
         * kwota, brak retry bo błąd biznesowy), oraz przelew, który udaje się dopiero
         * przy drugiej próbie (zasymuluj to licznikiem prób w kodzie testowym).
         */
        public static void main(String[] args) { }
    }
}
