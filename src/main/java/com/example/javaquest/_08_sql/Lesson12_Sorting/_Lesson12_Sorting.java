package com.example.javaquest._08_sql.Lesson12_Sorting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson12_Sorting {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 ORDER BY – SORTOWANIE WYNIKÓW
         * ============================================================
         * Bez ORDER BY kolejność zwracanych wierszy NIE jest gwarantowana
         * przez standard SQL – silnik może zwrócić je w dowolnej
         * kolejności (często zgodnej z fizycznym ułożeniem na dysku czy
         * kolejnością wstawiania, ale to szczegół implementacyjny, na
         * którym NIE wolno polegać).
         *
         * ORDER BY sortuje wynik zapytania po jednej lub kilku kolumnach.
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson12;DB_CLOSE_DELAY=-1");
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
            stmt.execute("INSERT INTO products VALUES (7, 'Podkladka', 'Peryferia', NULL)");

            /*
             * ============================================================
             * 🔹 ORDER BY ... ASC (domyślnie rosnąco)
             * ============================================================
             * ASC (ascending) sortuje rosnąco – to domyślne zachowanie
             * ORDER BY, więc słowo ASC można pominąć.
             */
            System.out.println("=== ORDER BY price ASC (rosnaco, domyslnie) ===");
            print(stmt.executeQuery("SELECT name, price FROM products ORDER BY price ASC"));

            /*
             * ============================================================
             * 🔹 ORDER BY ... DESC (malejąco)
             * ============================================================
             */
            System.out.println("\n=== ORDER BY price DESC (malejaco) ===");
            print(stmt.executeQuery("SELECT name, price FROM products ORDER BY price DESC"));

            /*
             * ============================================================
             * 🔹 Sortowanie po kilku kolumnach
             * ============================================================
             * Kolumny sortujące wymieniamy po przecinku – kolejność ma
             * znaczenie! Najpierw sortujemy po pierwszej kolumnie, a
             * DOPIERO wśród wierszy o tej samej wartości pierwszej
             * kolumny sortujemy po drugiej, itd. Każda kolumna może mieć
             * NIEZALEŻNY kierunek (ASC/DESC).
             */
            System.out.println("\n=== ORDER BY category ASC, price DESC ===");
            print(stmt.executeQuery("SELECT name, category, price FROM products ORDER BY category ASC, price DESC"));

            /*
             * ============================================================
             * 🔹 Sortowanie wartości NULL
             * ============================================================
             * Standard SQL pozwala jawnie sterować, gdzie w wyniku trafią
             * wiersze z NULL, przez NULLS FIRST / NULLS LAST. H2 wspiera
             * tę składnię (nie wszystkie silniki ją znają – np. MySQL do
             * niedawna jej nie miał i wymagał obejścia przez
             * CASE WHEN kolumna IS NULL THEN ...).
             *
             * Bez jawnego NULLS FIRST/LAST, domyślne zachowanie zależy od
             * silnika SQL – w H2 przy ORDER BY ... ASC wartości NULL
             * trafiają na POCZĄTEK wyniku (traktowane jako "najmniejsze").
             */
            System.out.println("\n=== ORDER BY price ASC – domyslne zachowanie NULL w H2 ===");
            print(stmt.executeQuery("SELECT name, price FROM products ORDER BY price ASC"));

            System.out.println("\n=== ORDER BY price ASC NULLS LAST (jawnie: NULL na koniec) ===");
            print(stmt.executeQuery("SELECT name, price FROM products ORDER BY price ASC NULLS LAST"));

            System.out.println("\n=== ORDER BY price DESC NULLS FIRST (jawnie: NULL na poczatek) ===");
            print(stmt.executeQuery("SELECT name, price FROM products ORDER BY price DESC NULLS FIRST"));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bez ORDER BY kolejność wierszy NIE jest gwarantowana
         * - ORDER BY kolumna ASC sortuje rosnąco (ASC to wartość domyślna)
         * - ORDER BY kolumna DESC sortuje malejąco
         * - Można sortować po kilku kolumnach naraz (kolejność w ORDER BY
         *   ma znaczenie – pierwsza kolumna jest nadrzędna)
         * - NULLS FIRST / NULLS LAST pozwalają jawnie wskazać, gdzie mają
         *   trafić wiersze z wartością NULL w posortowanym kolumnie (H2 to
         *   wspiera – nie każdy silnik SQL zna tę składnię)
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
