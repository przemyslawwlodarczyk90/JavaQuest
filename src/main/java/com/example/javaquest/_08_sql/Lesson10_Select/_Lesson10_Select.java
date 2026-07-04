package com.example.javaquest._08_sql.Lesson10_Select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson10_Select {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 SELECT – ODCZYT DANYCH
         * ============================================================
         * SELECT to najczęściej używane polecenie SQL – odczytuje dane
         * z tabeli, ale ich NIE zmienia (patrz Lesson09_DML). W tej
         * lekcji poznajemy różne warianty klauzuli SELECT: wybór kolumn,
         * aliasy, DISTINCT, podstawowe WHERE oraz LIMIT/OFFSET.
         *
         * Głębsze filtrowanie (AND, OR, LIKE, BETWEEN, IN, EXISTS...)
         * poznamy w Lesson11_Filtering, a sortowanie wyników w
         * Lesson12_Sorting.
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson10;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        category VARCHAR(50),
                        price DECIMAL(10, 2)
                    )
                    """);
            stmt.execute("INSERT INTO products VALUES (1, 'Klawiatura', 'Peryferia', 149.99)");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz', 'Peryferia', 79.50)");
            stmt.execute("INSERT INTO products VALUES (3, 'Monitor', 'Peryferia', 899.00)");
            stmt.execute("INSERT INTO products VALUES (4, 'Laptop', 'Komputery', 3999.00)");
            stmt.execute("INSERT INTO products VALUES (5, 'Komputer stacjonarny', 'Komputery', 4599.00)");
            stmt.execute("INSERT INTO products VALUES (6, 'Kabel HDMI', 'Peryferia', 19.99)");

            /*
             * ============================================================
             * 🔹 SELECT * – wszystkie kolumny
             * ============================================================
             * Gwiazdka (*) oznacza "wszystkie kolumny tabeli". Wygodne do
             * szybkiego podglądu danych, ale w kodzie produkcyjnym lepiej
             * wymieniać kolumny jawnie (patrz niżej) – SELECT * jest
             * wrażliwy na zmiany struktury tabeli (dodanie kolumny może
             * niespodziewanie zmienić wynik zapytania w aplikacji).
             */
            System.out.println("=== SELECT * (wszystkie kolumny) ===");
            print(stmt.executeQuery("SELECT * FROM products"));

            /*
             * ============================================================
             * 🔹 Wybór konkretnych kolumn
             * ============================================================
             * Wymieniamy tylko te kolumny, które nas interesują – czytelniej
             * i wydajniej (baza nie musi przesyłać zbędnych danych).
             */
            System.out.println("\n=== Konkretne kolumny (name, price) ===");
            print(stmt.executeQuery("SELECT name, price FROM products"));

            /*
             * ============================================================
             * 🔹 Aliasy (AS)
             * ============================================================
             * AS pozwala nadać kolumnie (lub wyrażeniu) inną nazwę w
             * wyniku zapytania – przydatne przy wyrażeniach obliczanych
             * albo żeby wynik był czytelniejszy dla odbiorcy (np. aplikacji
             * klienckiej). Słowo AS jest opcjonalne (można pominąć).
             */
            System.out.println("\n=== Aliasy (AS) ===");
            print(stmt.executeQuery("""
                    SELECT name AS nazwa_produktu, price AS cena_pln
                    FROM products
                    """));

            System.out.println("\n=== Alias na wyrazeniu obliczanym ===");
            print(stmt.executeQuery("""
                    SELECT name, price, price * 1.23 AS cena_z_vat
                    FROM products
                    """));

            /*
             * ============================================================
             * 🔹 DISTINCT – unikalne wartości
             * ============================================================
             * DISTINCT usuwa duplikaty z wyniku. Działa na CAŁYM zestawie
             * wybranych kolumn – jeśli wybierzemy kilka kolumn, DISTINCT
             * usunie tylko wiersze identyczne pod względem WSZYSTKICH
             * wybranych kolumn.
             */
            System.out.println("\n=== DISTINCT (unikalne kategorie) ===");
            print(stmt.executeQuery("SELECT DISTINCT category FROM products"));

            /*
             * ============================================================
             * 🔹 Podstawowe WHERE
             * ============================================================
             * WHERE filtruje wiersze na podstawie warunku logicznego.
             * Głębsze operatory (AND, OR, LIKE, BETWEEN, IN, IS NULL...)
             * poznamy w Lesson11_Filtering – tu tylko najprostszy przypadek.
             */
            System.out.println("\n=== WHERE (price > 100) ===");
            print(stmt.executeQuery("SELECT name, price FROM products WHERE price > 100"));

            /*
             * ============================================================
             * 🔹 LIMIT – ograniczenie liczby wierszy
             * ============================================================
             * LIMIT n zwraca co najwyżej n pierwszych wierszy wyniku.
             * Bez ORDER BY kolejność wierszy NIE jest gwarantowana przez
             * standard SQL – w praktyce warto łączyć LIMIT z ORDER BY
             * (patrz Lesson12_Sorting), żeby wynik był deterministyczny.
             */
            System.out.println("\n=== LIMIT 3 ===");
            print(stmt.executeQuery("SELECT id, name FROM products ORDER BY id LIMIT 3"));

            /*
             * ============================================================
             * 🔹 OFFSET – pomijanie wierszy (paginacja)
             * ============================================================
             * OFFSET n pomija pierwsze n wierszy wyniku. LIMIT + OFFSET
             * razem to klasyczny mechanizm PAGINACJI (stronicowania) –
             * np. strona 2 po 3 produkty: LIMIT 3 OFFSET 3.
             */
            System.out.println("\n=== LIMIT 3 OFFSET 3 (strona 2, po 3 wiersze) ===");
            print(stmt.executeQuery("SELECT id, name FROM products ORDER BY id LIMIT 3 OFFSET 3"));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SELECT * zwraca wszystkie kolumny – wygodne do podglądu, ale
         *   w produkcyjnym kodzie lepiej wymieniać kolumny jawnie
         * - Wybór konkretnych kolumn: SELECT kol1, kol2 FROM tabela
         * - Aliasy (AS) nadają kolumnom/wyrażeniom czytelniejsze nazwy
         *   w wyniku (AS jest opcjonalne)
         * - DISTINCT usuwa duplikaty (na podstawie WSZYSTKICH wybranych
         *   kolumn)
         * - WHERE filtruje wiersze (szczegóły -> Lesson11_Filtering)
         * - LIMIT ogranicza liczbę zwracanych wierszy
         * - OFFSET pomija pierwsze n wierszy – razem z LIMIT tworzą
         *   klasyczną paginację
         */
    }

    private static void print(ResultSet rs) throws SQLException {
        try (rs) {
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder(" - ");
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getMetaData().getColumnLabel(i)).append("=").append(rs.getString(i));
                }
                System.out.println(sb);
            }
        }
    }
}
