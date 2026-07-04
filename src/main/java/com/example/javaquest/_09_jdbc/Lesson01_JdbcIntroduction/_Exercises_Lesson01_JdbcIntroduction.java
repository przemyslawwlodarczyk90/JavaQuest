package com.example.javaquest._09_jdbc.Lesson01_JdbcIntroduction;

public class _Exercises_Lesson01_JdbcIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ConnectAndPrintUrl {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 in-memory pod URL-em "jdbc:h2:mem:l01ex01;DB_CLOSE_DELAY=-1"
         * (DriverManager.getConnection). Wypisz adres, z którym się połączono, odczytany
         * przez connection.getMetaData().getURL().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateColorsTable {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą "jdbc:h2:mem:l01ex02;DB_CLOSE_DELAY=-1". Utwórz tabelę
         * "colors" (id INT PRIMARY KEY, name VARCHAR(50)) przez statement.execute(DDL).
         * Wypisz zwróconą przez execute() wartość boolean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InsertThreeRows {
        /*
         * 🧪 Zadanie 3:
         * W bazie "jdbc:h2:mem:l01ex03;DB_CLOSE_DELAY=-1" utwórz tabelę "colors"
         * (id INT PRIMARY KEY, name VARCHAR(50)) i wstaw 3 wiersze przez
         * statement.executeUpdate(). Dla każdego INSERT-a wypisz liczbę zmienionych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SelectAllRows {
        /*
         * 🧪 Zadanie 4:
         * Rozszerz Zadanie 3: po wstawieniu 3 kolorów wykonaj
         * "SELECT id, name FROM colors ORDER BY id" przez executeQuery() i wypisz
         * każdy wiersz (id oraz name) w pętli while(resultSet.next()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MoviesEndToEnd {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj mini przepływ jak w przykładzie z lekcji, ale dla tabeli "movies"
         * (id INT PRIMARY KEY, title VARCHAR(200), year INT): CREATE TABLE, wstaw
         * 2 filmy, odczytaj i wypisz wszystkie wiersze. Użyj bazy
         * "jdbc:h2:mem:l01ex05;DB_CLOSE_DELAY=-1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryWithResourcesOrder {
        /*
         * 🧪 Zadanie 6:
         * Wypisz komunikat "PRZED blokiem", otwórz Connection do
         * "jdbc:h2:mem:l01ex06;DB_CLOSE_DELAY=-1" w try-with-resources, wewnątrz wypisz
         * "W BLOKU - polaczenie otwarte", a zaraz PO bloku wypisz "PO BLOKU - polaczenie zamkniete",
         * żeby zaobserwować kolejność automatycznego zamykania zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InMemoryDataDisappears {
        /*
         * 🧪 Zadanie 7:
         * Otwórz połączenie do "jdbc:h2:mem:l01ex07" (BEZ DB_CLOSE_DELAY=-1), utwórz
         * tabelę "temp_data" i zamknij połączenie. Otwórz NOWE połączenie do tej samej
         * nazwy bazy i spróbuj odczytać z tabeli "temp_data" - złap SQLException
         * i wypisz jego komunikat, pokazując że dane (i schemat) zniknęły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExecuteVsExecuteUpdate {
        /*
         * 🧪 Zadanie 8:
         * W bazie "jdbc:h2:mem:l01ex08;DB_CLOSE_DELAY=-1" wywołaj execute() na CREATE TABLE
         * i wypisz zwrócony boolean, a następnie executeUpdate() na INSERT i wypisz
         * zwrócony int (liczbę wierszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ManualRowCounter {
        /*
         * 🧪 Zadanie 9:
         * W bazie "jdbc:h2:mem:l01ex09;DB_CLOSE_DELAY=-1" utwórz tabelę, wstaw 5 wierszy,
         * a następnie wykonaj SELECT i policz wiersze RĘCZNIE (zmienna licznik zwiększana
         * w pętli while(rs.next())), wypisując na końcu sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConnectAndPingMethod {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę boolean connectAndPing(String dbName), która otwiera połączenie
         * do "jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1", wykonuje "SELECT 1" i zwraca
         * true, jeśli się udało (false przy złapanym SQLException). Wywołaj ją dla
         * unikalnej nazwy bazy i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FilterAndCountQueries {
        /*
         * 🧪 Zadanie 11:
         * W bazie "jdbc:h2:mem:l01ex11;DB_CLOSE_DELAY=-1" stwórz tabelę "products"
         * (id, name, price), wstaw 5 wierszy o różnych cenach. Wykonaj: SELECT z WHERE
         * price > wartość, SELECT z ORDER BY price DESC oraz SELECT COUNT(*) - wypisz
         * wyniki wszystkich trzech zapytań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReadSingleAggregateValue {
        /*
         * 🧪 Zadanie 12:
         * W bazie "jdbc:h2:mem:l01ex12;DB_CLOSE_DELAY=-1" wstaw 4 wiersze do tabeli
         * "orders" (id, amount). Wykonaj "SELECT COUNT(*) FROM orders" przez executeQuery(),
         * odczytaj pojedynczą wartość z ResultSet (rs.next(); rs.getInt(1)) i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DataPersistsWithCloseDelay {
        /*
         * 🧪 Zadanie 13:
         * Użyj nazwy bazy "jdbc:h2:mem:l01ex13;DB_CLOSE_DELAY=-1". Otwórz połączenie #1,
         * utwórz tabelę i wstaw 2 wiersze, zamknij połączenie. Otwórz połączenie #2 do
         * TEJ SAMEJ nazwy, wykonaj SELECT i potwierdź (wypisz), że oba wiersze nadal istnieją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DataLostWithoutCloseDelay {
        /*
         * 🧪 Zadanie 14:
         * Użyj nazwy bazy "jdbc:h2:mem:l01ex14" (BEZ DB_CLOSE_DELAY=-1). Otwórz połączenie #1,
         * utwórz tabelę i wstaw wiersz, zamknij połączenie. Otwórz połączenie #2 do TEJ SAMEJ
         * nazwy i spróbuj odczytać - złap SQLException (tabela nie istnieje) i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GenericPrintTable {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę printTable(Connection conn, String tableName), która wykonuje
         * "SELECT * FROM " + tableName i wypisuje URL połączenia (getMetaData().getURL())
         * oraz wszystkie wiersze zwrócone przez ResultSet (użyj getObject dla każdej kolumny
         * po indeksie 1..N, gdzie N to liczba kolumn - możesz założyć znaną z góry liczbę 2).
         * Przetestuj na tabeli z 2 kolumnami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TimeFullFlow {
        /*
         * 🧪 Zadanie 16:
         * Zmierz (System.nanoTime) czas wykonania: otwarcie połączenia do
         * "jdbc:h2:mem:l01ex16;DB_CLOSE_DELAY=-1", utworzenie tabeli, wstawienie 100 wierszy
         * w pętli i wykonanie SELECT * odczytującego wszystkie wiersze. Wypisz zmierzony czas w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TwoIndependentTables {
        /*
         * 🧪 Zadanie 17:
         * W bazie "jdbc:h2:mem:l01ex17;DB_CLOSE_DELAY=-1" utwórz DWIE niezależne tabele:
         * "books" (id, title) i "members" (id, name) - bez klucza obcego. Wstaw po 2 wiersze
         * do każdej, a następnie wypisz zawartość obu tabel osobnymi zapytaniami SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CatchNonexistentTableError {
        /*
         * 🧪 Zadanie 18:
         * Połącz się z bazą "jdbc:h2:mem:l01ex18;DB_CLOSE_DELAY=-1" i spróbuj wykonać
         * "SELECT * FROM nieistniejaca_tabela". Złap wyjątek SQLException i wypisz
         * e.getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CatchInvalidSyntaxError {
        /*
         * 🧪 Zadanie 19:
         * Połącz się z bazą "jdbc:h2:mem:l01ex19;DB_CLOSE_DELAY=-1" i wykonaj celowo
         * niepoprawny składniowo SQL (np. "SELECT * FRO products"). Złap SQLException
         * i wypisz przyjazny komunikat błędu wraz z e.getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReusableSetupMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę setupDatabase(Connection conn), która tworzy tabelę "books"
         * i wstawia do niej 3 przykładowe wiersze (jak w Zadaniu 5 z podglądu z lekcji).
         * Wywołaj ją, a następnie osobno wykonaj SELECT i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DatabaseDiagnostics {
        /*
         * 🧪 Zadanie 21:
         * Połącz się z bazą "jdbc:h2:mem:l01ex21;DB_CLOSE_DELAY=-1" i przez
         * connection.getMetaData() wypisz: nazwę produktu bazy (getDatabaseProductName()),
         * wersję produktu (getDatabaseProductVersion()), nazwę drivera (getDriverName())
         * oraz wersję drivera (getDriverVersion()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PortableLogicAcrossDbNames {
        /*
         * 🧪 Zadanie 22:
         * Wydziel CAŁĄ logikę JDBC (CREATE TABLE, INSERT, SELECT, wypisanie wyniku) do
         * jednej metody runDemo(String jdbcUrl), przyjmującej TYLKO url jako parametr.
         * Wywołaj ją dwa razy z różnymi nazwami baz H2 ("jdbc:h2:mem:l01ex22a;..." i
         * "jdbc:h2:mem:l01ex22b;..."), pokazując że ten sam kod działa niezależnie od
         * konkretnej instancji bazy (idea przenośności JDBC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LoopInsertAndStats {
        /*
         * 🧪 Zadanie 23:
         * W bazie "jdbc:h2:mem:l01ex23;DB_CLOSE_DELAY=-1" utwórz tabelę "items" (id, name).
         * W pętli for (i=1..10) wstaw wiersz "Item " + i przez executeUpdate. Następnie
         * odczytaj wszystkie wiersze przez ResultSet i policz w Javie: liczbę wierszy oraz
         * długość najdłuższej nazwy - wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ComposedLayerMethods {
        /*
         * 🧪 Zadanie 24:
         * Napisz 3 osobne metody: executeDdl(Connection c, String sql),
         * insertRow(Connection c, String sql) (zwraca int - wiersze zmienione) oraz
         * queryAll(Connection c, String sql) (zwraca List<String> - np. sklejone kolumny
         * jako tekst). Skomponuj je, by utworzyć tabelę, wstawić 3 wiersze i wypisać
         * wynik queryAll.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SameStatementSequentialQueries {
        /*
         * 🧪 Zadanie 25:
         * Utwórz JEDEN obiekt Statement i użyj go do wykonania po kolei 3 RÓŻNYCH
         * zapytań SELECT (wszystkie wiersze, wiersze z filtrem, agregat COUNT(*)) -
         * za każdym razem w NOWYM bloku try-with-resources na ResultSet, ale z tym samym
         * Statement. Wypisz wyniki wszystkich trzech i skomentuj (println), że wykonanie
         * nowego zapytania na tym samym Statement automatycznie zamyka poprzedni ResultSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RobustMultiCloseWithSuppressed {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę, która ręcznie (bez try-with-resources) otwiera Statement i
         * Connection, a w bloku finally zamyka OBA zasoby w odwrotnej kolejności,
         * upewniając się przez try/catch wewnątrz finally, że zamknięcie jednego zasobu
         * nie przeszkodzi w próbie zamknięcia drugiego. Przetestuj happy path (bez błędów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TotalBalanceJavaVsSql {
        /*
         * 🧪 Zadanie 27:
         * W bazie "jdbc:h2:mem:l01ex27;DB_CLOSE_DELAY=-1" utwórz tabelę "accounts"
         * (id, name, balance DECIMAL) i wstaw 5 kont z różnymi saldami. Policz sumę sald
         * RĘCZNIE w Javie iterując ResultSet, a następnie policz sumę przez SQL
         * "SELECT SUM(balance) FROM accounts" - wypisz obie wartości i potwierdź, że są równe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ManualEtlBetweenDatabases {
        /*
         * 🧪 Zadanie 28:
         * Utwórz dwie oddzielne bazy in-memory: "jdbc:h2:mem:l01ex28src;DB_CLOSE_DELAY=-1"
         * (źródło) i "jdbc:h2:mem:l01ex28dst;DB_CLOSE_DELAY=-1" (cel), obie z identyczną
         * tabelą "notes" (id, content). Wstaw dane tylko do źródła, odczytaj je przez
         * ResultSet i wstaw (executeUpdate) do bazy docelowej - zweryfikuj przez SELECT
         * z bazy docelowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SimpleRepositoryPattern {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj klasę SimpleRepository z polem Connection i metodami:
         * create() (tworzy tabelę "notes" id, content), insertOne(String content)
         * i findAll() (zwraca List<String> zawartości). Użyj jej w main() do
         * utworzenia bazy, wstawienia 3 notatek i wypisania wyniku findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystko: w bazie "jdbc:h2:mem:l01ex30;DB_CLOSE_DELAY=-1" utwórz 2 tabele,
         * zasiej dane w pętli, wykonaj kilka zapytań (wszystkie wiersze, filtrowane,
         * agregat), wypisz sformatowany raport tekstowy oraz zmierz i wypisz całkowity
         * czas wykonania (System.nanoTime na początku i na końcu programu).
         */
        public static void main(String[] args) { }
    }
}
