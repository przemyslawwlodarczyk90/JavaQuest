package com.example.javaquest._09_jdbc.Lesson11_CreateAndDropTableFromJava;

public class _Exercises_Lesson11_CreateAndDropTableFromJava {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicCreateTable {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l11ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "books" (id INT PRIMARY KEY, title VARCHAR(200)) przez
         * statement.execute(DDL). Wypisz zwrócony boolean (powinien być
         * false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicDropTableAndVerify {
        /*
         * 🧪 Zadanie 2:
         * Na tabeli "books" z Zadania 1 wykonaj DROP TABLE books, a
         * potem spróbuj wykonać "SELECT * FROM books" - złap
         * SQLException i wypisz jego komunikat, potwierdzając, że
         * tabela faktycznie zniknęła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateTableWithMultipleColumnTypes {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "products" z kolumnami różnych typów: id INT,
         * name VARCHAR(100), price DECIMAL(10,2), in_stock BOOLEAN,
         * added_on DATE. Wypisz komunikat potwierdzający utworzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateInsertSelectRoundTrip {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "products" (jak w Zadaniu 3), wstaw 2 wiersze i
         * odczytaj je przez SELECT, wypisując wynik - pełny "round trip"
         * od utworzenia tabeli do odczytu danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DropNonexistentTableThrows {
        /*
         * 🧪 Zadanie 5:
         * Spróbuj wykonać "DROP TABLE nieistniejaca_tabela" na świeżo
         * otwartej bazie (bez uprzedniego utworzenia takiej tabeli).
         * Złap SQLException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateTableIfNotExists {
        /*
         * 🧪 Zadanie 6:
         * Wykonaj "CREATE TABLE IF NOT EXISTS books (id INT PRIMARY KEY,
         * title VARCHAR(200))" DWA razy pod rząd na tej samej bazie -
         * pierwsze wywołanie tworzy tabelę, drugie NIE powinno rzucić
         * wyjątku (dzięki IF NOT EXISTS). Wypisz potwierdzenie po obu
         * wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DropTableIfExists {
        /*
         * 🧪 Zadanie 7:
         * Wykonaj "DROP TABLE IF EXISTS books" DWA razy pod rząd - raz
         * gdy tabela istnieje (usuwa ją) i raz gdy JUŻ nie istnieje (NIE
         * rzuca wyjątku, w przeciwieństwie do zwykłego DROP TABLE z
         * Zadania 5). Wypisz potwierdzenie po obu wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RecreateTableLosesOldData {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę "books" (id, title), wstaw 2 wiersze, wykonaj
         * DROP TABLE, a potem utwórz JĄ PONOWNIE z tą samą definicją.
         * Wykonaj SELECT COUNT(*) na nowej tabeli - powinno być 0
         * (stare dane zniknęły razem z tabelą).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SelfContainedScratchTableMethod {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę runScratchDemo(Connection), która w JEDNYM
         * wywołaniu: tworzy tabelę "scratch", wstawia 3 wiersze, odczytuje
         * je, a na końcu SAMA usuwa tabelę (DROP) - kompletny, samo-
         * czyszczący się "tymczasowy" eksperyment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AlterTableAddColumn {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tabelę "books" (id, title) z 2 wierszami, a potem
         * wykonaj "ALTER TABLE books ADD COLUMN rating INT". Wykonaj
         * SELECT * i wypisz wynik - nowa kolumna powinna mieć wartość
         * NULL dla istniejących wierszy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CreateTableIfNotExistsHelper {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę createTableIfNotExists(Connection conn, String
         * tableName, String ddl), która wykonuje "CREATE TABLE IF NOT
         * EXISTS " + tableName + " " + ddl. Użyj jej do utworzenia 2
         * różnych tabel, wywołując metodę dla każdej DWA razy (bez
         * wyjątku przy drugim wywołaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DropTableIfExistsHelper {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę dropTableIfExists(Connection conn, String
         * tableName) wykonującą "DROP TABLE IF EXISTS " + tableName.
         * Użyj jej do bezpiecznego usunięcia 2 tabel - jednej, która
         * istnieje, i jednej, która nigdy nie została utworzona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateTestLifecycleResetBetweenRuns {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę resetBooksTable(Connection), która wykonuje
         * dropTableIfExists("books") + createTable("books"). Wywołaj ją
         * TRZY razy, wstawiając między wywołaniami różne dane - po
         * każdym "resecie" zweryfikuj SELECT COUNT(*) = 0 (świeża
         * tabela, symulacja resetu przed każdym testem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AlterTableAddColumnWithDefault {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "books" (id, title) z 3 wierszami, a potem
         * wykonaj "ALTER TABLE books ADD COLUMN available BOOLEAN
         * DEFAULT TRUE". Wykonaj SELECT i sprawdź, że WSZYSTKIE
         * istniejące wiersze automatycznie dostały wartość TRUE (nie NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AlterTableDropColumn {
        /*
         * 🧪 Zadanie 15:
         * Na tabeli "books" (id, title, rating INT) wykonaj "ALTER TABLE
         * books DROP COLUMN rating". Spróbuj wykonać SELECT rating FROM
         * books - złap SQLException potwierdzający, że kolumna faktycznie
         * zniknęła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CreateMultipleTablesAndDropInReverseOrder {
        /*
         * 🧪 Zadanie 16:
         * Utwórz 3 tabele: "authors" (id), "books" (id, author_id
         * REFERENCES authors(id)), "reviews" (id, book_id REFERENCES
         * books(id)). Usuń je w KOLEJNOŚCI ODWROTNEJ do tworzenia
         * (reviews, potem books, potem authors) - bez błędu naruszenia
         * klucza obcego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WrongDropOrderViolatesForeignKey {
        /*
         * 🧪 Zadanie 17:
         * Na tych samych 3 tabelach z Zadania 16 spróbuj usunąć
         * "authors" JAKO PIERWSZĄ (przed books i reviews) - złap
         * SQLException (naruszenie klucza obcego, tabela authors jest
         * referencjonowana). Wypisz komunikat i skomentuj, że kolejność
         * DROP TABLE ma znaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ResetTwoTableSchemaForTests {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę resetSchema(Connection), która usuwa (w
         * poprawnej kolejności) i odtwarza od zera schemat 2 powiązanych
         * tabel ("authors", "books"). Wywołaj ją, wstaw dane testowe,
         * wywołaj ją PONOWNIE i zweryfikuj, że obie tabele są znowu puste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CreateTableAsSelectCopy {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "books" z 4 wierszami, a potem wykonaj "CREATE
         * TABLE books_backup AS SELECT * FROM books" - skopiuj strukturę
         * I dane do nowej tabeli w jednym poleceniu. Zweryfikuj, że
         * books_backup ma te same 4 wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DdlAlwaysReturnsFalseFromExecute {
        /*
         * 🧪 Zadanie 20:
         * Wykonaj po kolei CREATE TABLE, ALTER TABLE ADD COLUMN i DROP
         * TABLE przez statement.execute(), zapisując zwrócony boolean
         * dla każdego. Wypisz wszystkie trzy wartości i potwierdź, że
         * dla operacji DDL execute() ZAWSZE zwraca false (brak ResultSet).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimpleMigrationTracker {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "schema_version" (version INT PRIMARY KEY).
         * Zdefiniuj listę 3 "migracji" (String[] z DDL: create table
         * books, alter add column, create table reviews). Napisz metodę
         * applyMigrations(Connection, String[] migrations), która dla
         * KAŻDEJ migracji sprawdza w schema_version, czy jej numer
         * (indeks) był już zastosowany - jeśli nie, wykonuje DDL i
         * zapisuje numer. Wywołaj ją DWA razy pod rząd i potwierdź, że
         * druga próba NIE powtarza już zastosowanych migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GuardedDropRequiresConfirmation {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę dropTableSafely(Connection conn, String
         * tableName, boolean confirmed), która rzuca
         * IllegalStateException("Usuniecie tabeli wymaga potwierdzenia"),
         * jeśli confirmed == false, BEZ dotykania bazy danych. Wywołaj
         * ją raz z confirmed=false (złap wyjątek) i raz z confirmed=true
         * (tabela faktycznie usunięta) - symulacja "zabezpieczenia"
         * przed przypadkowym DROP TABLE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestSchemaManagerSetUpTearDown {
        /*
         * 🧪 Zadanie 23:
         * Napisz klasę TestSchemaManager z metodami setUp(Connection)
         * (tworzy tabelę "books" i wstawia 2 wiersze testowe) i
         * tearDown(Connection) (usuwa tabelę). Zademonstruj DWA "testy"
         * (dwie niezależne sekwencje setUp -> logika testowa ->
         * tearDown) na tej samej bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SchemaEvolutionAcrossVersions {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj ewolucję schematu w 3 "wersjach": v1 - CREATE TABLE
         * books (id, title); v2 - ALTER TABLE books ADD COLUMN
         * published_year INT; v3 - ALTER TABLE books ADD COLUMN rating
         * DECIMAL(3,1), a potem ALTER TABLE books DROP COLUMN
         * published_year (zamiana na inne pole). Po każdej "wersji"
         * wypisz aktualną strukturę (SELECT * i nazwy kolumn z metadata).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TimingCreateDropCycleOverhead {
        /*
         * 🧪 Zadanie 25:
         * Zmierz (System.nanoTime) czas 100-krotnego cyklu CREATE TABLE +
         * DROP TABLE (ta sama definicja, ta sama nazwa) na jednym
         * Statement. Wypisz łączny i średni czas jednego cyklu w ms -
         * skomentuj, dlaczego wykonywanie DDL "na każde żądanie" w
         * prawdziwej aplikacji byłoby złym pomysłem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TableExistsCheckMethod {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę boolean tableExists(Connection conn, String
         * tableName) używającą connection.getMetaData().getTables(null,
         * null, tableName.toUpperCase(), null) i sprawdzającą, czy
         * ResultSet ma jakiś wiersz. Przetestuj dla tabeli, która
         * istnieje, i dla tabeli, która nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ListAllTablesViaMetaData {
        /*
         * 🧪 Zadanie 27:
         * Utwórz 3 tabele o różnych nazwach. Użyj
         * connection.getMetaData().getTables(null, null, "%", new
         * String[]{"TABLE"}) do wypisania nazw WSZYSTKICH tabel w
         * bazie. Usuń jedną z nich i wypisz listę ponownie, pokazując
         * zmianę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RenameTableViaAlter {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "books_old" z 2 wierszami, a potem wykonaj
         * "ALTER TABLE books_old RENAME TO books_new". Zweryfikuj, że
         * SELECT z "books_new" widzi te same dane, a SELECT z
         * "books_old" rzuca SQLException (nazwa już nie istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CascadingTeardownThreeLinkedTables {
        /*
         * 🧪 Zadanie 29:
         * Rozszerz Zadanie 16/17: napisz metodę
         * teardownSchemaInCorrectOrder(Connection, String... tableNamesInDependencyOrder),
         * która usuwa podane tabele w kolejności PODANEJ (od najbardziej
         * zależnej do najmniej), używając dropTableIfExists. Przetestuj
         * na 3 tabelach z łańcuchem kluczy obcych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSchemaBootstrapper {
        /*
         * 🧪 Zadanie 30:
         * Napisz klasę SchemaBootstrapper z metodami bootstrap(Connection)
         * (idempotentnie tworzy schemat 3 powiązanych tabel przez CREATE
         * TABLE IF NOT EXISTS, w poprawnej kolejności ze względu na
         * klucze obce), smokeTest(Connection) (wstawia i odczytuje po
         * jednym wierszu w każdej tabeli, weryfikując że wszystko
         * działa) oraz teardown(Connection) (usuwa wszystkie 3 tabele w
         * odwrotnej kolejności). Wywołaj bootstrap() DWA razy pod rząd
         * (bez błędu), potem smokeTest(), potem teardown() - z
         * potwierdzeniem (getTables()) że po teardown schemat jest pusty.
         */
        public static void main(String[] args) { }
    }
}
