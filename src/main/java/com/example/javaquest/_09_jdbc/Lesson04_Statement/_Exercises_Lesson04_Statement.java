package com.example.javaquest._09_jdbc.Lesson04_Statement;

public class _Exercises_Lesson04_Statement {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateTableWithExecute {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l04ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "books" (id INT PRIMARY KEY, title VARCHAR(200)) przez
         * statement.execute(DDL). Wypisz zwrócony boolean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SingleInsertUpdateCount {
        /*
         * 🧪 Zadanie 2:
         * Na bazie "jdbc:h2:mem:l04ex02;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "books" (id, title) i wykonaj JEDEN INSERT przez
         * statement.executeUpdate(). Wypisz zwróconą liczbę zmienionych
         * wierszy (powinna być 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MultipleInsertsSumRows {
        /*
         * 🧪 Zadanie 3:
         * Na bazie "jdbc:h2:mem:l04ex03;DB_CLOSE_DELAY=-1" z tabelą
         * "books" wstaw w pętli 5 wierszy przez executeUpdate(), sumując
         * po drodze liczbę zmienionych wierszy z każdego wywołania.
         * Wypisz sumę na końcu (powinna być 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SelectAndPrintAllRows {
        /*
         * 🧪 Zadanie 4:
         * Rozszerz Zadanie 3: po wstawieniu 5 książek wykonaj
         * "SELECT id, title FROM books ORDER BY id" przez executeQuery()
         * i wypisz wszystkie wiersze w pętli while(rs.next()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExecuteBooleanMeaning {
        /*
         * 🧪 Zadanie 5:
         * Na bazie "jdbc:h2:mem:l04ex05;DB_CLOSE_DELAY=-1" z tabelą
         * "books" (z co najmniej 1 wierszem), wywołaj execute() na
         * zapytaniu SELECT i wypisz wynik (powinien być true), a potem
         * execute() na INSERT i wypisz wynik (powinien być false).
         * Skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UpdateRowsAffected {
        /*
         * 🧪 Zadanie 6:
         * Na bazie "jdbc:h2:mem:l04ex06;DB_CLOSE_DELAY=-1" z tabelą
         * "books" (id, title, available BOOLEAN) i 3 wierszami, wykonaj
         * UPDATE ustawiający available = FALSE dla jednego konkretnego id.
         * Wypisz liczbę zaktualizowanych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DeleteRowsAffected {
        /*
         * 🧪 Zadanie 7:
         * Na tej samej tabeli "books" z Zadania 6 wykonaj DELETE
         * usuwający jeden konkretny wiersz po id. Wypisz liczbę
         * usuniętych wierszy, a następnie wypisz zawartość całej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UpdateWithNoMatchingRows {
        /*
         * 🧪 Zadanie 8:
         * Na bazie "jdbc:h2:mem:l04ex08;DB_CLOSE_DELAY=-1" z tabelą
         * "books" wykonaj UPDATE z warunkiem WHERE id = 999 (nieistniejące
         * id). Wypisz liczbę zmienionych wierszy - powinna być 0, mimo że
         * zapytanie wykonało się bez błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SameStatementSequentialOperations {
        /*
         * 🧪 Zadanie 9:
         * Utwórz JEDEN obiekt Statement na bazie
         * "jdbc:h2:mem:l04ex09;DB_CLOSE_DELAY=-1" i użyj go po kolei do:
         * CREATE TABLE, INSERT (2 razy), SELECT wypisujący wynik - cały
         * czas na tym samym obiekcie Statement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConcatenationMechanicsWithConstant {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj SQL przez konkatenację Stringów z JEDNĄ, znaną z góry
         * (nie od użytkownika) stałą wartością tytułu książki, np.
         * "SELECT * FROM books WHERE title = '" + tytul + "'" - wypisz
         * zbudowany SQL i wynik jego wykonania. To ćwiczenie pokazuje
         * TYLKO mechanikę sklejania, bez realnego ryzyka (pełny temat SQL
         * Injection - Lesson14).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenericExecuteAndPrintMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę executeAndPrint(Statement statement, String sql),
         * która wywołuje execute(sql) i w zależności od zwróconego
         * boolean: jeśli true - wypisuje wszystkie wiersze
         * statement.getResultSet(); jeśli false - wypisuje
         * statement.getUpdateCount(). Przetestuj ją na CREATE TABLE,
         * INSERT i SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThreeDifferentSelectsOnSameStatement {
        /*
         * 🧪 Zadanie 12:
         * Na bazie "jdbc:h2:mem:l04ex12;DB_CLOSE_DELAY=-1" z tabelą
         * "products" (id, name, price) i 5 wierszami, użyj JEDNEGO
         * Statement do wykonania po kolei: SELECT wszystkich wierszy,
         * SELECT z WHERE price > wartość, SELECT COUNT(*) - wypisz
         * wyniki wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NewQueryClosesOldResultSet {
        /*
         * 🧪 Zadanie 13:
         * Na tym samym Statement wykonaj executeQuery() (zapamiętaj
         * ResultSet #1, NIE zamykaj go), potem wykonaj DRUGIE
         * executeQuery() na tym samym Statement. Spróbuj potem odczytać
         * (rs1.next()) ResultSet #1 - złap SQLException i wypisz
         * komunikat, potwierdzając, że nowe zapytanie automatycznie
         * zamknęło poprzedni ResultSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TimeManyExecuteUpdateCalls {
        /*
         * 🧪 Zadanie 14:
         * Zmierz (System.nanoTime) czas wstawienia 200 wierszy do tabeli
         * "items" (id, name) przez 200 pojedynczych wywołań
         * statement.executeUpdate() na TYM SAMYM obiekcie Statement.
         * Wypisz czas w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GenericSqlRunnerBranching {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę runSql(Statement statement, String sql), która
         * wywołuje execute(sql) i na podstawie zwróconego boolean
         * decyduje, co dalej zrobić: dla ResultSet - policzyć liczbę
         * wierszy; dla update - wypisać liczbę zmienionych wierszy.
         * Przetestuj ją na 4 różnych zapytaniach (DDL, INSERT, UPDATE,
         * SELECT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SafeConcatenationWithComputedValue {
        /*
         * 🧪 Zadanie 16:
         * Na tabeli "products" (id, name, price) zbuduj SQL przez
         * konkatenację z WYLICZONĄ (nie od użytkownika) wartością progu
         * ceny, np. "SELECT * FROM products WHERE price > " +
         * obliczonyPrag - wypisz zbudowany SQL oraz wynik. Skomentuj, że
         * mimo poprawnego działania, to WCIĄŻ nie jest bezpieczny wzorzec
         * dla danych OD UŻYTKOWNIKA (Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CountRowsHelperMethod {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę int countRows(Statement statement, String
         * tableName), która wykonuje "SELECT COUNT(*) FROM " + tableName
         * i zwraca wynik jako int. Przetestuj ją na tabeli z co najmniej
         * 4 wierszami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GenericPrintRowsByColumnNames {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę printRows(Statement statement, String sql,
         * String... columnNames), która wykonuje executeQuery(sql) i dla
         * każdego wiersza wypisuje wartości KAŻDEJ podanej kolumny (przez
         * rs.getObject(nazwa)). Przetestuj na tabeli z 3 kolumnami,
         * podając wszystkie 3 nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LoopInsertThenVerifyCount {
        /*
         * 🧪 Zadanie 19:
         * Na bazie "jdbc:h2:mem:l04ex19;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "tags" (id, name) i w pętli for wstaw 15 wierszy przez
         * executeUpdate(). Po pętli wykonaj SELECT COUNT(*) i porównaj
         * (println) z oczekiwaną liczbą 15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CatchSyntaxErrorFromStatement {
        /*
         * 🧪 Zadanie 20:
         * Na bazie "jdbc:h2:mem:l04ex20;DB_CLOSE_DELAY=-1" spróbuj
         * wykonać celowo niepoprawny SQL przez statement.execute(), np.
         * "SELEKt * FORM books". Złap SQLException i wypisz jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SqlScriptRunner {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę runScript(Statement statement, String[] sqlCommands),
         * która wykonuje TABLICĘ poleceń SQL po kolei przez execute(),
         * zliczając ile z nich zwróciło ResultSet a ile było
         * update/DDL. Przetestuj na tablicy: CREATE TABLE, 3x INSERT,
         * 1x SELECT - wypisz podsumowanie liczników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullCrudWithPlainStatement {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj pełny CRUD (Create/Read/Update/Delete) na tabeli
         * "notes" (id, content) używając WYŁĄCZNIE Statement (bez
         * PreparedStatement) i stałych, znanych z góry wartości. Wykonaj
         * po kolei: create table, 3 inserty, select wszystkich, update
         * jednego wiersza, delete innego, select ponownie - wypisz stan
         * po każdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_GetUpdateCountAndGetResultSetMirrorBoolean {
        /*
         * 🧪 Zadanie 23:
         * Po wywołaniu statement.execute(sql) na zapytaniu SELECT, wypisz
         * BOTH: zwrócony boolean ORAZ statement.getResultSet() (czy jest
         * różne od null) i statement.getUpdateCount() (powinno być -1
         * dla SELECT-a). Zrób to samo dla INSERT-a (boolean false,
         * getResultSet() == null, getUpdateCount() >= 0) - porównaj oba
         * przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SetMaxRowsLimit {
        /*
         * 🧪 Zadanie 24:
         * Na tabeli z 10 wierszami wywołaj statement.setMaxRows(3) PRZED
         * executeQuery("SELECT * FROM ... ORDER BY id"). Wypisz liczbę
         * wierszy faktycznie zwróconych przez ResultSet (powinno być
         * dokładnie 3, mimo że w tabeli jest 10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_QueryTimeoutSetting {
        /*
         * 🧪 Zadanie 25:
         * Wywołaj statement.setQueryTimeout(5) (limit w sekundach), a
         * potem odczytaj i wypisz statement.getQueryTimeout() - potwierdź,
         * że wartość została zapisana. Wykonaj normalne, szybkie
         * zapytanie SELECT na tym Statement i pokaż, że wykonuje się bez
         * problemu (limit nie został przekroczony).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ManualEtlWithPlainStatement {
        /*
         * 🧪 Zadanie 26:
         * Na bazie "jdbc:h2:mem:l04ex26;DB_CLOSE_DELAY=-1" utwórz dwie
         * tabele "source_books" i "target_books" (te same kolumny: id,
         * title). Wstaw dane do source_books, odczytaj je przez
         * ResultSet i dla KAŻDEGO wiersza zbuduj (przez konkatenację) i
         * wykonaj INSERT do target_books przez ten sam Statement. Zweryfikuj
         * przez SELECT z target_books.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReportWithAggregatesAndFiltering {
        /*
         * 🧪 Zadanie 27:
         * Na tabeli "orders" (id, customer, amount) z 8 wierszami wykonaj
         * i wypisz sformatowany raport: łączna suma (SELECT SUM(amount)),
         * liczba zamówień (COUNT(*)), średnia wartość (AVG(amount)),
         * oraz zamówienia o amount większym niż średnia (osobny SELECT z
         * podzapytaniem albo dwuetapowo w Javie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_StatementReuseAcrossDdlDmlDql {
        /*
         * 🧪 Zadanie 28:
         * Zademonstruj JEDEN Statement używany w pełnym cyklu życia
         * tabeli: CREATE TABLE -> kilka INSERT -> UPDATE -> DELETE ->
         * SELECT weryfikujący -> DROP TABLE -> próba SELECT po DROP
         * (złap SQLException). Wypisz krótki komunikat po każdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareExecuteVariantsSideBySide {
        /*
         * 🧪 Zadanie 29:
         * Dla tej samej listy 5 poleceń SQL (mix DDL/DML/SELECT) napisz
         * metodę porównującą: ile z nich MOŻNA by wykonać przez
         * executeUpdate() (bez wyjątku) a ile wymaga executeQuery()
         * (SELECT-y). Wypisz podsumowanie która metoda (execute,
         * executeUpdate, executeQuery) jest odpowiednia dla każdego typu
         * polecenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneInventorySystemWithPlainStatement {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini system magazynowy WYŁĄCZNIE za pomocą Statement:
         * utwórz tabelę "inventory" (id, name, price, quantity), wstaw 5
         * produktów, wygeneruj raport (łączna wartość magazynu = SUM(price*quantity),
         * najdroższy produkt, liczba produktów o quantity < 5), zaktualizuj
         * ilość jednego produktu, usuń jeden produkt, i wypisz finalny
         * stan magazynu oraz zaktualizowany raport.
         */
        public static void main(String[] args) { }
    }
}
