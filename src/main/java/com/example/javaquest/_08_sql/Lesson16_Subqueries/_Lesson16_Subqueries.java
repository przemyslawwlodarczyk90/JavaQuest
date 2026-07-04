package com.example.javaquest._08_sql.Lesson16_Subqueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson16_Subqueries {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PODZAPYTANIE (SUBQUERY)
         * ============================================================
         * Podzapytanie to zapytanie SQL UMIESZCZONE WEWNĄTRZ innego
         * zapytania. Pozwala użyć wyniku jednego SELECT-a jako danych
         * wejściowych dla drugiego – bez pisania osobnych zapytań
         * i łączenia wyników ręcznie w Javie.
         *
         * Podzapytanie może wystąpić w 3 miejscach:
         * - w WHERE   -> filtrowanie na podstawie wyniku podzapytania
         * - w FROM    -> podzapytanie traktowane jak "tymczasowa tabela"
         * - w SELECT  -> dodatkowa kolumna liczona osobnym zapytaniem
         *   dla każdego wiersza
         */

        String url = "jdbc:h2:mem:lesson16;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT REFERENCES users(id),
                        amount INT NOT NULL
                    )
                    """);

            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski')");
            stmt.execute("INSERT INTO users VALUES (2, 'Anna Nowak')");
            stmt.execute("INSERT INTO users VALUES (3, 'Piotr Wisniewski')"); // ten user nie zlozy zadnego zamowienia

            stmt.execute("INSERT INTO orders VALUES (1, 1, 100)");
            stmt.execute("INSERT INTO orders VALUES (2, 1, 250)");
            stmt.execute("INSERT INTO orders VALUES (3, 2, 50)");

            /*
             * ============================================================
             * 🔹 PODZAPYTANIE W WHERE + EXISTS
             * ============================================================
             * EXISTS sprawdza, czy podzapytanie zwróciło CHOĆ JEDEN
             * wiersz (nie interesuje nas konkretna wartość, tylko sam
             * fakt istnienia dopasowania). To zwykle NAJSZYBSZY sposób
             * na sprawdzenie "czy dla tego wiersza istnieje powiązany
             * rekord w innej tabeli" – baza może przerwać przeszukiwanie
             * podzapytania od razu po znalezieniu pierwszego dopasowania.
             */

            System.out.println("=== Uzytkownicy z co najmniej 1 zamowieniem (WHERE + EXISTS) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT u.name
                    FROM users u
                    WHERE EXISTS (
                        SELECT 1 FROM orders o WHERE o.user_id = u.id
                    )
                    ORDER BY u.name
                    """)) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("name"));
                }
            }

            /*
             * ============================================================
             * 🔹 NOT EXISTS – odwrotność
             * ============================================================
             * NOT EXISTS znajduje wiersze, dla których podzapytanie NIE
             * zwróciło żadnego wyniku – tu: użytkownicy BEZ zamówień.
             */

            System.out.println("\n=== Uzytkownicy BEZ zamowien (WHERE + NOT EXISTS) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT u.name
                    FROM users u
                    WHERE NOT EXISTS (
                        SELECT 1 FROM orders o WHERE o.user_id = u.id
                    )
                    ORDER BY u.name
                    """)) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("name"));
                }
            }

            /*
             * ============================================================
             * 🔹 PODZAPYTANIE W FROM
             * ============================================================
             * Podzapytanie w FROM jest traktowane jak zwykła tabela –
             * MUSI mieć alias (tu: liczba_zamowien). Najpierw liczymy
             * liczbę zamówień DLA KAŻDEGO usera (podzapytanie), a
             * dopiero potem liczymy AVG() z tych już zagregowanych
             * wartości – to co innego niż AVG(amount) z samej tabeli
             * orders!
             */

            System.out.println("\n=== Srednia liczba zamowien na uzytkownika (podzapytanie w FROM) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT AVG(zamowienia_na_uzytkownika.ile) AS srednia
                    FROM (
                        SELECT u.id, COUNT(o.id) AS ile
                        FROM users u
                        LEFT JOIN orders o ON o.user_id = u.id
                        GROUP BY u.id
                    ) AS zamowienia_na_uzytkownika
                    """)) {
                if (rs.next()) {
                    System.out.println("Srednia liczba zamowien/uzytkownika: " + rs.getDouble("srednia"));
                }
            }
            // Uwaga: LEFT JOIN w podzapytaniu jest kluczowy - dzieki niemu
            // Piotr Wisniewski (0 zamowien) tez wchodzi do sredniej jako 0,
            // a nie jest calkowicie pominiety.

            /*
             * ============================================================
             * 🔹 PODZAPYTANIE W SELECT
             * ============================================================
             * Podzapytanie w SELECT musi zwracać DOKŁADNIE JEDNĄ wartość
             * (jeden wiersz, jedna kolumna) dla każdego wiersza
             * zewnętrznego zapytania - to tzw. "correlated subquery"
             * (podzapytanie skorelowane), bo odwołuje się do u.id z
             * zapytania zewnętrznego.
             */

            System.out.println("\n=== Liczba zamowien kazdego usera (podzapytanie w SELECT) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT
                        u.name,
                        (SELECT COUNT(*) FROM orders o WHERE o.user_id = u.id) AS liczba_zamowien
                    FROM users u
                    ORDER BY u.name
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getString("name") + " -> " + rs.getInt("liczba_zamowien") + " zamowien");
                }
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Podzapytanie (subquery) = SELECT wewnątrz innego SELECT-a
         * - W WHERE -> najczęściej z EXISTS / NOT EXISTS, żeby sprawdzić
         *   istnienie (lub brak) powiązanych wierszy w innej tabeli
         * - W FROM -> podzapytanie jako "tymczasowa tabela" (wymaga
         *   aliasu), przydatne do agregacji nad już zagregowanymi danymi
         * - W SELECT -> podzapytanie skorelowane zwracające jedną
         *   wartość na wiersz zewnętrznego zapytania (musi zwrócić
         *   dokładnie 1 wiersz i 1 kolumnę)
         * - EXISTS zwykle jest szybszy niż IN dla dużych zbiorów danych,
         *   bo baza może przerwać sprawdzanie po pierwszym dopasowaniu
         */
    }
}
