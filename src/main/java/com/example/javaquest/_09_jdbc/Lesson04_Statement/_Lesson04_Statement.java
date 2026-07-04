package com.example.javaquest._09_jdbc.Lesson04_Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson04_Statement {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST Statement?
         * ============================================================
         * `java.sql.Statement` to najprostszy sposób wykonania zapytania
         * SQL przez JDBC. Tworzymy go z Connection:
         *
         *     Statement stmt = connection.createStatement();
         *
         * Statement wykonuje SQL jako ZWYKŁY TEKST (String) - bez
         * możliwości bezpiecznego "wstawiania" zmiennych jako parametrów
         * (to potrafi dopiero PreparedStatement - Lesson05). Ma trzy
         * główne metody wykonawcze:
         *
         * - executeQuery(sql)  - dla SELECT, zwraca ResultSet z wynikami
         * - executeUpdate(sql) - dla INSERT/UPDATE/DELETE (i DDL: CREATE,
         *                        DROP...), zwraca liczbę zmienionych wierszy (int)
         * - execute(sql)       - uniwersalna metoda, zwraca boolean:
         *                        true jeśli wynikiem jest ResultSet (SELECT),
         *                        false jeśli to aktualizacja/DDL
         */

        String url = "jdbc:h2:mem:jdbclesson04;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            /*
             * ============================================================
             * 🔹 execute() - metoda uniwersalna
             * ============================================================
             * Używamy jej, gdy nie wiemy z góry (albo nie chcemy się
             * rozstrzygać), czy zapytanie zwróci wynik, czy nie - typowo
             * do DDL (CREATE TABLE) albo gdy wykonujemy SQL "z zewnątrz"
             * (np. wczytany z pliku), o którym nie mamy pewności.
             */

            boolean createReturnsResultSet = statement.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10,2) NOT NULL
                    )
                    """);
            System.out.println("=== execute() na CREATE TABLE ===");
            System.out.println("execute() zwrocilo: " + createReturnsResultSet + " (false = brak ResultSet, to DDL)");

            /*
             * ============================================================
             * 🔹 executeUpdate() - dla INSERT/UPDATE/DELETE
             * ============================================================
             * Zwraca LICZBĘ WIERSZY, których dotyczyła operacja. Bardzo
             * przydatne do weryfikacji, czy operacja faktycznie coś
             * zmieniła (np. czy UPDATE trafił w istniejący wiersz).
             */

            System.out.println("\n=== executeUpdate() na INSERT ===");
            int inserted1 = statement.executeUpdate("INSERT INTO products VALUES (1, 'Klawiatura', 149.99)");
            int inserted2 = statement.executeUpdate("INSERT INTO products VALUES (2, 'Mysz', 79.50)");
            int inserted3 = statement.executeUpdate("INSERT INTO products VALUES (3, 'Monitor', 899.00)");
            System.out.println("Wiersze wstawione: " + inserted1 + ", " + inserted2 + ", " + inserted3 + " (kazdy INSERT = 1 wiersz)");

            System.out.println("\n=== executeUpdate() na UPDATE ===");
            int updatedRows = statement.executeUpdate("UPDATE products SET price = 129.99 WHERE id = 1");
            System.out.println("Liczba zaktualizowanych wierszy: " + updatedRows);

            /*
             * ============================================================
             * 🔹 executeQuery() - dla SELECT
             * ============================================================
             * Zwraca ResultSet - "kursor" wskazujący na wiersze wyniku
             * (szczegółowo poznamy go w Lesson06). Tu tylko podgląd.
             */

            System.out.println("\n=== executeQuery() na SELECT ===");
            try (ResultSet resultSet = statement.executeQuery("SELECT id, name, price FROM products ORDER BY id")) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + ": " + resultSet.getString("name")
                            + " - " + resultSet.getBigDecimal("price") + " zl");
                }
            }

            /*
             * ============================================================
             * ⚠️ OGRANICZENIA Statement - BRAK PARAMETRYZACJI
             * ============================================================
             * Statement wykonuje SQL jako gotowy String - jeśli chcemy
             * "wstawić" do zapytania jakąś wartość (np. nazwę produktu),
             * MUSIMY skleić ją ręcznie przez konkatenację Stringów.
             *
             * Poniższy przykład używa WYŁĄCZNIE STAŁYCH, znanych z góry
             * wartości (nie danych od użytkownika) - pokazujemy TYLKO
             * mechanikę konkatenacji, nie realny atak.
             */

            System.out.println("\n=== KONKATENACJA SQL (mechanika, NIE realny input uzytkownika) ===");
            String productName = "Mysz"; // w prawdziwym, niebezpiecznym scenariuszu ta wartość mogłaby pochodzić od użytkownika
            String sql = "SELECT id, name, price FROM products WHERE name = '" + productName + "'";
            System.out.println("Zbudowany SQL: " + sql);

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println("Znaleziono: " + resultSet.getString("name") + " - " + resultSet.getBigDecimal("price") + " zl");
                }
            }

            /*
             * ⚠️ RYZYKO SQL INJECTION
             * Gdyby `productName` pochodziło BEZPOŚREDNIO od użytkownika
             * (np. z formularza) i zawierało np.: Mysz' OR '1'='1
             * to sklejony SQL zmieniłby swoje ZNACZENIE:
             *
             *   SELECT id, name, price FROM products WHERE name = 'Mysz' OR '1'='1'
             *
             * - warunek '1'='1' jest ZAWSZE prawdziwy, więc zapytanie
             * zwróciłoby WSZYSTKIE wiersze z tabeli, zamiast tylko tych
             * pasujących do "Mysz". To najprostszy przykład SQL Injection
             * - atakujący "dopisuje" własną logikę SQL przez dane
             * wejściowe. Statement NIE chroni przed tym w żaden sposób -
             * to jego fundamentalne ograniczenie. Pełny temat SQL
             * Injection (i dlaczego PreparedStatement go eliminuje)
             * poznamy dokładnie w Lesson14 - a już w następnej lekcji
             * (Lesson05) poznamy PreparedStatement, które w praktyce
             * powinno być GŁÓWNYM sposobem wykonywania zapytań w JDBC.
             */
        }

        System.out.println("\n=== KONIEC LEKCJI 4 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Statement wykonuje SQL jako zwykły String, tworzony przez
         *   connection.createStatement()
         * - executeQuery(sql)  -> ResultSet (dla SELECT)
         * - executeUpdate(sql) -> int, liczba zmienionych wierszy
         *   (dla INSERT/UPDATE/DELETE/DDL)
         * - execute(sql)       -> boolean, uniwersalna metoda
         *   (true = jest ResultSet, false = nie ma)
         * - Statement NIE wspiera parametryzacji - wartości trzeba
         *   sklejać ręcznie przez konkatenację Stringów
         * - To otwiera drzwi na SQL INJECTION, gdy sklejane wartości
         *   pochodzą od użytkownika - pełny temat w Lesson14
         * - W praktyce: PreparedStatement (Lesson05) powinien być
         *   GŁÓWNYM narzędziem do wykonywania zapytań w JDBC
         */
    }
}
