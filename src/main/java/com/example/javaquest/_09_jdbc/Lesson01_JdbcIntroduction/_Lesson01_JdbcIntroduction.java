package com.example.javaquest._09_jdbc.Lesson01_JdbcIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson01_JdbcIntroduction {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST JDBC?
         * ============================================================
         * JDBC (Java DataBase Connectivity) to STANDARD (specyfikacja)
         * Javy opisujący, jak program napisany w Javie ma się komunikować
         * z relacyjną bazą danych. To NIE jest konkretna biblioteka od
         * jednego producenta - to zestaw INTERFEJSÓW w pakiecie
         * `java.sql` (i `javax.sql`), które są częścią samej Javy (JDK).
         *
         * Najważniejsze interfejsy JDBC, które poznamy w tym rozdziale:
         * - Connection        - połączenie z konkretną bazą danych
         * - Statement         - wykonywanie zapytań SQL "na sucho"
         * - PreparedStatement - wykonywanie sparametryzowanych zapytań SQL
         * - ResultSet         - wynik zapytania SELECT (kursor na dane)
         *
         * W poprzednim rozdziale (_08_sql) używaliśmy JDBC jako CIENKIEGO
         * narzędzia, żeby móc uruchamiać SQL i uczyć się teorii baz
         * danych. W TYM rozdziale JDBC samo w sobie jest TEMATEM -
         * poznamy dokładnie, jak działa, co robi "pod maską" i jak
         * używać go poprawnie i bezpiecznie w prawdziwych aplikacjach.
         *
         * 🔹 PO CO ISTNIEJE TAKI STANDARD?
         * Gdyby każda baza danych (PostgreSQL, MySQL, Oracle, H2...)
         * miała swoje unikalne, niekompatybilne API, to zmiana bazy
         * danych w projekcie oznaczałaby przepisanie całego kodu
         * dostępu do danych. Dzięki JDBC aplikacja pisze kod WYŁĄCZNIE
         * przeciwko interfejsom z `java.sql` - a to, jak dokładnie
         * połączyć się z konkretną bazą, "za kulisami" załatwia STEROWNIK
         * (driver) tej bazy (więcej o tym w Lesson02).
         */

        System.out.println("=== JDBC - PODGLĄD SCHEMATU ===");
        System.out.println("""
                Aplikacja Java
                      |
                      v
                JDBC API (java.sql: Connection, Statement, ResultSet...)
                      |
                      v
                Driver (np. H2, PostgreSQL, MySQL - tłumaczy JDBC API
                        na komunikaty zrozumiałe dla KONKRETNEJ bazy)
                      |
                      v
                Baza danych (silnik bazy danych, gdzie faktycznie
                             przechowywane są dane)
                """);

        /*
         * ============================================================
         * 🔹 DRIVER JAKO "TŁUMACZ"
         * ============================================================
         * Aplikacja Java rozmawia zawsze tym samym "językiem" - JDBC API.
         * Driver konkretnej bazy tłumaczy te wywołania na protokół sieciowy
         * lub format zrozumiały dla danego silnika bazy danych. W tym
         * kursie używamy drivera H2 (baza w pamięci, zero instalacji) -
         * ale gdybyśmy podmienili go na driver PostgreSQL czy MySQL, KOD
         * naszej aplikacji (Connection, Statement, ResultSet...) zostałby
         * praktycznie NIEZMIENIONY - zmieniłby się tylko URL połączenia
         * i sam driver na classpath. To jest właśnie siła standardu JDBC.
         */

        /*
         * ============================================================
         * 🔍 PIERWSZY, KOMPLETNY PRZYKŁAD END-TO-END
         * ============================================================
         * Poniżej "podgląd całości" - pełna ścieżka od połączenia z bazą
         * aż po zamknięcie zasobów. Każdy krok (URL, Connection,
         * Statement, ResultSet, zamykanie) omówimy szczegółowo w
         * kolejnych lekcjach - tu chodzi o zobaczenie całego obrazka.
         */

        String url = "jdbc:h2:mem:jdbclesson01;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            System.out.println("=== POŁĄCZENIE NAWIĄZANE ===");
            System.out.println("Polaczono z: " + connection.getMetaData().getURL());

            // 1) CREATE TABLE
            statement.execute("""
                    CREATE TABLE books (
                        id INT PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        author VARCHAR(100) NOT NULL
                    )
                    """);
            System.out.println("Tabela 'books' utworzona.");

            // 2) INSERT
            statement.executeUpdate("INSERT INTO books VALUES (1, 'Pan Tadeusz', 'Adam Mickiewicz')");
            statement.executeUpdate("INSERT INTO books VALUES (2, 'Lalka', 'Boleslaw Prus')");
            statement.executeUpdate("INSERT INTO books VALUES (3, 'Ferdydurke', 'Witold Gombrowicz')");
            System.out.println("Wstawiono 3 wiersze.");

            // 3) SELECT
            System.out.println("\n=== ZAWARTOSC TABELI 'books' ===");
            try (ResultSet resultSet = statement.executeQuery("SELECT id, title, author FROM books ORDER BY id")) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    System.out.println(id + ": \"" + title + "\" - " + author);
                }
            }

            // 4) zasoby (Statement, Connection) zamkną się automatycznie
            // dzięki try-with-resources po wyjściu z bloku (patrz Lesson12)
        }

        System.out.println("\n=== POLACZENIE ZAMKNIETE ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JDBC (Java DataBase Connectivity) to standard Javy (interfejsy
         *   w java.sql) opisujący komunikację z relacyjnymi bazami danych
         * - Aplikacja programuje przeciwko interfejsom JDBC (Connection,
         *   Statement, PreparedStatement, ResultSet), NIGDY bezpośrednio
         *   przeciwko konkretnej bazie danych
         * - Driver (sterownik) tłumaczy wywołania JDBC API na komunikaty
         *   zrozumiałe dla konkretnej bazy (H2, PostgreSQL, MySQL...)
         * - Schemat: Aplikacja Java -> JDBC API -> Driver -> Baza danych
         * - Typowy przepływ pracy z JDBC: połączenie (Connection) ->
         *   wykonanie zapytania (Statement/PreparedStatement) -> odczyt
         *   wyniku (ResultSet) -> zamknięcie zasobów
         * - Każdy z tych elementów poznamy szczegółowo w kolejnych
         *   lekcjach tego rozdziału
         */
    }
}
