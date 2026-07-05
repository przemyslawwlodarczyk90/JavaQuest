package com.example.javaquest._08_sql.Lesson19_Transactions;

public class _Exercises_Lesson19_Transactions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicCommitTransaction {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_tx;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1 konto z
         * balance=1000. Wyłącz autocommit (setAutoCommit(false)), wykonaj UPDATE
         * zmniejszający balance o 100, wywołaj conn.commit() i zweryfikuj (SELECT),
         * że zmiana jest trwała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicRollbackTransaction {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "accounts" z 1 kontem (balance=1000). Wyłącz autocommit,
         * wykonaj UPDATE zmniejszający balance o 500, ale wywołaj conn.rollback()
         * (nie commit) - zweryfikuj (SELECT), że balance WRÓCIŁO do 1000 (zmiana
         * została wycofana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AutoCommitDefaultBehavior {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1 konto.
         * BEZ wyłączania autocommit (domyślny tryb) wykonaj UPDATE - zweryfikuj, że
         * zmiana jest NATYCHMIAST widoczna i trwała, bez wywoływania commit() jawnie
         * (każde pojedyncze zapytanie jest własną, automatycznie zatwierdzaną
         * transakcją).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MultiStatementTransactionCommit {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 2 konta
         * (1000 i 500). Wyłącz autocommit, wykonaj DWA UPDATE naraz (przelew 200 z A
         * do B) i JEDEN wspólny commit() - zweryfikuj, że OBA salda zmieniły się
         * poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RollbackOnCaughtException {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance
         * >= 0)) i wstaw 1 konto z balance=100. Wyłącz autocommit, w try/catch
         * wykonaj UPDATE, który zejdzie pod 0 (naruszy CHECK) - w catch wywołaj
         * conn.rollback() i wypisz komunikat błędu, a potem zweryfikuj (SELECT), że
         * balance NIE zmieniło się.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TransferMoneySuccessScenario {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz klasyczny scenariusz przelewu z lekcji: "accounts" (id, owner,
         * balance INT) z 2 kontami (1000 i 500). Wykonaj UDANY przelew 200 zł: dwa
         * UPDATE + commit() w try/catch (jak w lekcji). Wypisz salda PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TransferMoneyFailureScenario {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz scenariusz z Zadania 6. Spróbuj wykonać przelew 2000 zł z konta,
         * które ma tylko 800 zł (po Zadaniu 6) - CHECK (balance >= 0) powinien
         * odrzucić UPDATE. Złap SQLException, wywołaj rollback() i zweryfikuj, że
         * OBA salda są takie jak PRZED nieudaną próbą (żadne konto nie dostało/nie
         * straciło pieniędzy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AcidVocabularyPrint {
        /*
         * 🧪 Zadanie 8:
         * Bez łączenia się z bazą: wypisz println definicje 4 własności ACID z
         * lekcji (Atomicity, Consistency, Isolation, Durability) w formacie
         * "<literka>: <opis>", z jednym przykładem dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SetAutoCommitBackToTrueAfterTransaction {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1
         * konto. Wyłącz autocommit, wykonaj UPDATE i commit() w try/finally, gdzie
         * finally PRZYWRACA autocommit na true (conn.setAutoCommit(true)) - jak w
         * lekcji. Zweryfikuj, że KOLEJNY UPDATE (poza jawną transakcją) jest
         * natychmiast trwały bez wywoływania commit().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CommitVsRollbackVocabularyPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz println wyjaśniające 3 komendy sterujące
         * transakcją z lekcji (COMMIT, ROLLBACK, SAVEPOINT) w formacie "<komenda>:
         * <opis>".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SavepointPartialRollback {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz scenariusz przelewu z SAVEPOINT z lekcji: "accounts" (id, owner,
         * balance INT CHECK (balance >= 0)) z 2 kontami. Wyłącz autocommit, wykonaj
         * krok 1 (odjęcie od konta A), ustaw SAVEPOINT, spróbuj krok 2 (odjęcie
         * kwoty powodującej błąd na koncie B) - złap wyjątek, wykonaj
         * rollback(savepoint) (cofnięcie TYLKO kroku 2), a potem commit() - krok 1
         * powinien ZOSTAĆ zatwierdzony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleSavepointsInOneTransaction {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1
         * konto z balance=1000. Wyłącz autocommit, wykonaj 3 kolejne UPDATE (odjęcie
         * 100 każdy), ustawiając SAVEPOINT po każdym z nich (savepoint1, savepoint2,
         * savepoint3). Wykonaj rollback(savepoint2) - powinien cofnąć TYLKO trzeci
         * UPDATE (do stanu po savepoint2), a potem commit() - zweryfikuj finalne
         * saldo (balance - 200, nie -300).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TransactionAtomicityWithMultipleTables {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "orders" (id INT PRIMARY KEY, customer_name VARCHAR(100)) i
         * "order_items" (id INT PRIMARY KEY, order_id INT, product_name VARCHAR(100)).
         * Wyłącz autocommit, wstaw zamówienie i 2 jego pozycje w JEDNEJ transakcji,
         * ale spróbuj wstawić TRZECIĄ pozycję z celowo błędnym order_id (naruszenie
         * FOREIGN KEY, jeśli dodasz ograniczenie) - złap błąd, rollback() całej
         * transakcji i zweryfikuj (COUNT(*)), że ANI zamówienie, ANI żadna pozycja
         * nie zostały zapisane (atomowość między tabelami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReadYourOwnUncommittedChangesWithinTransaction {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1
         * konto. Na TYM SAMYM Connection wyłącz autocommit, wykonaj UPDATE (bez
         * commit jeszcze), a potem wykonaj SELECT na TYM SAMYM połączeniu -
         * zweryfikuj, że własna transakcja WIDZI swoje niezatwierdzone zmiany (to
         * normalne - dopiero INNE połączenia/transakcje nie widzą niezatwierdzonych
         * zmian, patrz Lesson20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BatchInsertWithSingleCommitForPerformance {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "logs" (id INT PRIMARY KEY, message VARCHAR(100)). Zmierz
         * czas wstawienia 500 wierszy z AUTOCOMMIT WŁĄCZONYM (każdy INSERT to
         * osobna transakcja - domyślne zachowanie), a potem wyczyść tabelę i zmierz
         * czas wstawienia TYCH SAMYCH 500 wierszy z autocommit WYŁĄCZONYM + JEDNYM
         * commit() na końcu - wypisz porównanie czasów i skomentuj w println wniosek
         * o wydajności grupowania operacji w jedną transakcję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RollbackRestoresMultipleTablesConsistently {
        /*
         * 🧪 Zadanie 16:
         * Utwórz "inventory" (id INT PRIMARY KEY, quantity INT CHECK (quantity >=
         * 0)) i "sales_log" (id INT PRIMARY KEY, item_id INT, quantity_sold INT).
         * Wyłącz autocommit, w JEDNEJ transakcji zaloguj sprzedaż (INSERT do
         * sales_log) i zmniejsz stan magazynowy (UPDATE inventory) - jeśli UPDATE
         * naruszy CHECK (sprzedano więcej niż jest na stanie), złap błąd i
         * rollback() - zweryfikuj, że WPIS w sales_log ZNIKNĄŁ razem z nieudaną
         * zmianą inventory (obie operacje wycofane razem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TransactionWrapperHelperMethod {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance
         * >= 0)). Napisz generyczną metodę runInTransaction(Connection conn,
         * SqlExceptionRunnable action) (własny funkcyjny interfejs z metodą run()
         * throws SQLException), która automatycznie: wyłącza autocommit, wykonuje
         * action.run(), commit() przy sukcesie, rollback() przy wyjątku, i
         * PRZYWRACA autocommit w finally. Użyj jej do wykonania udanego i nieudanego
         * przelewu, weryfikując poprawność w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedTryWithSavepointForOptionalStep {
        /*
         * 🧪 Zadanie 18:
         * Utwórz "orders" (id INT PRIMARY KEY, status VARCHAR(20)) i "notifications"
         * (id INT PRIMARY KEY, order_id INT). Wyłącz autocommit, wykonaj INSERT
         * zamówienia (KRYTYCZNY krok), ustaw SAVEPOINT, a potem spróbuj wstawić
         * powiadomienie z celowo błędnym order_id (OPCJONALNY krok - jego
         * niepowodzenie NIE powinno cofać całego zamówienia) - w catch wykonaj
         * rollback(savepoint), a potem commit() - zweryfikuj, że zamówienie ISTNIEJE,
         * mimo że powiadomienie się nie udało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_VerifyDurabilityAfterCommitAcrossConnections {
        /*
         * 🧪 Zadanie 19:
         * Utwórz bazę "jdbc:h2:mem:ex19_tx;DB_CLOSE_DELAY=-1" z tabelą "accounts"
         * (id INT PRIMARY KEY, balance INT). W PIERWSZYM połączeniu wyłącz
         * autocommit, wykonaj UPDATE i commit(), a potem ZAMKNIJ to połączenie. W
         * DRUGIM, NOWYM połączeniu do tej samej bazy odczytaj balance - zweryfikuj,
         * że zmiana PRZETRWAŁA (durability) mimo zamknięcia oryginalnego połączenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareTransactionWithVsWithoutOnFailure {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0)) z
         * 2 kontami. Zademonstruj PROBLEM braku transakcji: wykonaj przelew (odjęcie
         * od A, dodanie do B) z autocommit WŁĄCZONYM (domyślne, KAŻDY UPDATE to
         * osobna transakcja) - jeśli drugi UPDATE zawiedzie, PIERWSZY jest już
         * trwały (niepożądany stan pośredni!). Następnie POPRAWNIE - z wyłączonym
         * autocommit i rollback() w catch. Wypisz salda po OBU wariantach i
         * skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiAccountBatchTransferWithFullAtomicity {
        /*
         * 🧪 Zadanie 21:
         * Utwórz "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0)) z
         * 5 kontami. Napisz metodę executeBatchTransfers(Connection conn,
         * List<Transfer> transfers) (Transfer = rekord z fromId, toId, amount),
         * która wykonuje WSZYSTKIE przelewy z listy w JEDNEJ transakcji - jeśli
         * KTÓRYKOLWIEK się nie powiedzie, WSZYSTKIE (nawet już wykonane) zostają
         * wycofane. Przetestuj z listą 4 przelewów, z których ostatni jest celowo
         * niewykonalny, i zweryfikuj, że WSZYSTKIE salda wróciły do stanu
         * początkowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TransactionWithSavepointRecoveryLoop {
        /*
         * 🧪 Zadanie 22:
         * Utwórz "orders" (id INT PRIMARY KEY, amount DECIMAL(10,2) CHECK (amount >
         * 0)). Napisz metodę insertOrdersWithRecovery(Connection conn, List<Integer>
         * ids, List<BigDecimal> amounts), która wyłącza autocommit i dla KAŻDEJ
         * pozycji z listy: ustawia SAVEPOINT, próbuje INSERT, a jeśli zawiedzie
         * (np. amount <= 0), wykonuje rollback DO TEGO SAVEPOINT i kontynuuje z
         * następną pozycją (bez przerywania całej transakcji) - na końcu jeden
         * wspólny commit(). Przetestuj z listą, gdzie część pozycji jest
         * niepoprawna, i zweryfikuj, że poprawne pozycje zostały zapisane, a
         * niepoprawne pominięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LongRunningTransactionWithPeriodicSavepoints {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "batch_processing" (id INT PRIMARY KEY, status VARCHAR(20)).
         * Napisz metodę processBatchWithCheckpoints(Connection conn, int totalItems,
         * int checkpointEvery), która wyłącza autocommit, przetwarza "totalItems"
         * elementów (INSERT), i co "checkpointEvery" elementów wywołuje
         * conn.commit() (żeby nie trzymać JEDNEJ ogromnej transakcji przez cały
         * czas). Przetestuj z totalItems=20, checkpointEvery=5 (4 commity po
         * drodze) i zweryfikuj (COUNT(*)), że wszystkie 20 wierszy zostało zapisane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TransactionalRetryOnTransientFailure {
        /*
         * 🧪 Zadanie 24:
         * Utwórz "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0)).
         * Napisz metodę transferWithRetry(Connection conn, int fromId, int toId, int
         * amount, int maxRetries), która próbuje wykonać przelew w transakcji, a w
         * razie SQLException wykonuje rollback() i PONAWIA próbę do maxRetries razy
         * (symulacja odzyskiwania po przejściowym błędzie - tu użyj sztucznego
         * licznika, który "naprawia" warunek po 2 próbach, żeby zademonstrować
         * udane odzyskanie). Wypisz log każdej próby i finalny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompensatingTransactionAcrossTwoResources {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "orders" (id INT PRIMARY KEY, status VARCHAR(20)) i "inventory" (id
         * INT PRIMARY KEY, quantity INT CHECK (quantity >= 0)) - traktowane jako
         * "dwa oddzielne zasoby" w JEDNYM connection (symulacja rozproszonej
         * transakcji, poza pełnym zakresem JDBC). Napisz metodę
         * placeOrderWithCompensation, która: (1) wstawia zamówienie, (2) zmniejsza
         * stan magazynowy, (3) JEŚLI krok 2 zawiedzie, wykonuje KOMPENSUJĄCY UPDATE
         * status zamówienia na 'FAILED' (a NIE rollback całości - inny wzorzec niż
         * ROLLBACK, bardziej "biznesowy") - zademonstruj scenariusz nieudany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TransactionAuditTrailWithFullHistory {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0)) i
         * "transaction_log" (id INT PRIMARY KEY AUTO_INCREMENT, description
         * VARCHAR(200), result VARCHAR(10)). Napisz metodę
         * transferWithAudit(Connection conn, int fromId, int toId, int amount),
         * która WEWNĄTRZ tej samej transakcji co przelew wstawia też wpis do
         * transaction_log z result='SUCCESS' - jeśli przelew się nie powiedzie, CAŁA
         * transakcja (łącznie z logiem) jest wycofana, a metoda w NOWEJ, osobnej
         * transakcji wstawia wpis result='FAILED'. Zademonstruj oba scenariusze i
         * wypisz finalny log.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_NestedSavepointsWithComplexRecoveryLogic {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "orders" (id INT PRIMARY KEY, amount DECIMAL(10,2)) i "order_items"
         * (id INT PRIMARY KEY, order_id INT, product_name VARCHAR(100)). Napisz
         * metodę placeComplexOrder(Connection conn), która: ustawia savepoint1,
         * wstawia zamówienie, ustawia savepoint2, wstawia 3 pozycje, gdzie DRUGA
         * jest celowo niepoprawna - w catch wykonuje rollback(savepoint2) (cofnięcie
         * TYLKO pozycji, zamówienie zostaje), wstawia POPRAWIONĄ wersję drugiej
         * pozycji, i na końcu commit() - zweryfikuj finalny stan (zamówienie + 3
         * POPRAWNE pozycje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureTransactionOverheadVsAutocommit {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "metrics" (id INT PRIMARY KEY, value INT). Zmierz czas
         * wykonania 1000 pojedynczych UPDATE z AUTOCOMMIT (każdy to osobna
         * transakcja) vs 1000 UPDATE w JEDNEJ transakcji (jeden commit na końcu) vs
         * 1000 UPDATE w 10 transakcjach po 100 (10 commitów) - wypisz porównanie
         * WSZYSTKICH 3 podejść i skomentuj w println kompromis między granularnością
         * transakcji a wydajnością.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullBankingSystemWithTransactionalIntegrity {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj mini-system bankowy: "accounts" (id INT PRIMARY KEY, owner
         * VARCHAR(100), balance INT CHECK (balance >= 0)) i "transaction_history"
         * (id INT PRIMARY KEY AUTO_INCREMENT, account_id INT, delta INT, reason
         * VARCHAR(100)). Napisz KOMPLETNE API transakcyjne: deposit(conn, id,
         * amount), withdraw(conn, id, amount) - z walidacją, że nie zejdzie pod 0 -
         * i transfer(conn, fromId, toId, amount) (kombinacja withdraw+deposit w JEDNEJ
         * transakcji), każda metoda loguje operację do transaction_history W TEJ
         * SAMEJ transakcji. Zademonstruj serię 5 operacji (mix udanych i nieudanych)
         * i wypisz finalne salda oraz KOMPLETNĄ historię transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneOrderFulfillmentWithFullTransactionalWorkflow {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletny workflow realizacji zamówienia: "customers" (id,
         * name), "products" (id, name, stock INT CHECK (stock >= 0)), "orders" (id,
         * customer_id INT, status VARCHAR(20) DEFAULT 'PENDING'), "order_items" (id,
         * order_id INT, product_id INT, quantity INT), "payments" (id, order_id INT,
         * amount DECIMAL(10,2), status VARCHAR(20)). Napisz metodę
         * fulfillOrder(Connection conn, int customerId, Map<Integer, Integer>
         * productQuantities), która w JEDNEJ transakcji: (1) wstawia zamówienie, (2)
         * dla każdego produktu wstawia pozycję zamówienia I zmniejsza stan
         * magazynowy, (3) wstawia płatność, (4) aktualizuje status zamówienia na
         * 'CONFIRMED' - jeśli KTÓRYKOLWIEK krok zawiedzie (np. niewystarczający
         * stan), WSZYSTKO jest wycofane i status zamówienia ustawiany (w NOWEJ
         * transakcji) na 'FAILED'. Zademonstruj udany i nieudany scenariusz, wypisując
         * kompletny stan wszystkich 5 tabel po każdym z nich.
         */
        public static void main(String[] args) { }
    }
}
