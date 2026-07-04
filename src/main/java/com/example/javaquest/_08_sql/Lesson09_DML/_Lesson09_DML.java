package com.example.javaquest._08_sql.Lesson09_DML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson09_DML {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DML – DATA MANIPULATION LANGUAGE
         * ============================================================
         * DML to grupa poleceń SQL, które operują na REKORDACH (danych)
         * wewnątrz już istniejącej tabeli – w odróżnieniu od DDL (patrz
         * Lesson08_DDL), które operuje na STRUKTURZE bazy (CREATE, ALTER,
         * DROP, TRUNCATE).
         *
         * Do DML należą 4 podstawowe polecenia:
         * - SELECT  – odczytuje dane (nie zmienia ich)
         * - INSERT  – dodaje nowe wiersze
         * - UPDATE  – modyfikuje istniejące wiersze
         * - DELETE  – usuwa wiersze
         *
         * To jest lekcja PRZEGLĄDOWA – każde z tych 4 poleceń poznamy
         * znacznie głębiej w kolejnych lekcjach:
         * - SELECT (wybór kolumn, aliasy, DISTINCT, LIMIT...) -> Lesson10_Select
         * - filtrowanie (WHERE, LIKE, BETWEEN, IN...)          -> Lesson11_Filtering
         * - sortowanie (ORDER BY)                              -> Lesson12_Sorting
         * - grupowanie (GROUP BY, HAVING)                      -> Lesson13_Grouping
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson09;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        price DECIMAL(10, 2)
                    )
                    """);

            /*
             * ============================================================
             * 🔹 INSERT – dodawanie wierszy
             * ============================================================
             * INSERT INTO tabela (kolumny) VALUES (wartości) dodaje nowy
             * wiersz. Jeśli podamy wartości dla WSZYSTKICH kolumn w
             * kolejności ich definicji, można pominąć listę kolumn (tak
             * jak w Lesson08) – ale jawne podanie kolumn jest bezpieczniejsze
             * (nie zepsuje się, jeśli ktoś kiedyś zmieni kolejność kolumn).
             */
            System.out.println("=== INSERT ===");
            stmt.execute("INSERT INTO products (id, name, price) VALUES (1, 'Klawiatura', 149.99)");
            stmt.execute("INSERT INTO products (id, name, price) VALUES (2, 'Mysz', 79.50)");
            stmt.execute("INSERT INTO products (id, name, price) VALUES (3, 'Monitor', 899.00)");
            System.out.println("Wstawiono 3 wiersze.");
            printAll(stmt);

            /*
             * ============================================================
             * 🔹 SELECT – odczyt danych
             * ============================================================
             * SELECT to jedyne z DML, które NIE zmienia danych – tylko je
             * odczytuje. Najprostsza postać: SELECT * FROM tabela (gwiazdka
             * oznacza "wszystkie kolumny"). Szczegóły (wybór konkretnych
             * kolumn, aliasy, DISTINCT, LIMIT/OFFSET) -> Lesson10_Select.
             */
            System.out.println("\n=== SELECT ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getInt("id") + ": " + rs.getString("name")
                            + " (" + rs.getBigDecimal("price") + " zl)");
                }
            }

            /*
             * ============================================================
             * 🔹 UPDATE – modyfikacja istniejących wierszy
             * ============================================================
             * UPDATE tabela SET kolumna = wartosc WHERE warunek.
             *
             * ⚠️ UWAGA: UPDATE bez WHERE zmieni WSZYSTKIE wiersze w
             * tabeli! To jeden z najczęstszych "wypadków" w SQL – zawsze
             * sprawdzaj warunek WHERE przed uruchomieniem UPDATE (najlepiej
             * najpierw jako SELECT z tym samym WHERE, żeby zobaczyć, które
             * wiersze zostaną dotknięte).
             */
            System.out.println("\n=== UPDATE ===");
            stmt.execute("UPDATE products SET price = 129.99 WHERE id = 1");
            System.out.println("Zaktualizowano cene produktu o id=1.");
            printAll(stmt);

            /*
             * ============================================================
             * 🔹 DELETE – usuwanie wierszy
             * ============================================================
             * DELETE FROM tabela WHERE warunek.
             *
             * ⚠️ UWAGA: analogicznie jak przy UPDATE – DELETE FROM tabela
             * (bez WHERE) usunie WSZYSTKIE wiersze (ale zostawi strukturę
             * tabeli – w odróżnieniu od DROP TABLE, które usuwa też
             * strukturę, i TRUNCATE TABLE, patrz Lesson08_DDL).
             */
            System.out.println("\n=== DELETE ===");
            stmt.execute("DELETE FROM products WHERE id = 2");
            System.out.println("Usunieto produkt o id=2.");
            printAll(stmt);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DML (Data Manipulation Language) = operacje na REKORDACH
         *   (danych), w odróżnieniu od DDL (struktura bazy)
         * - SELECT  – odczytuje dane, jako jedyne z DML ich nie zmienia
         * - INSERT  – dodaje nowe wiersze
         * - UPDATE  – modyfikuje istniejące wiersze (UWAGA na WHERE!)
         * - DELETE  – usuwa wiersze (UWAGA na WHERE!)
         * - Każde z tych poleceń ma swoją głębszą lekcję dalej w rozdziale
         *   (SELECT, filtrowanie, sortowanie, grupowanie)
         */
    }

    private static void printAll(Statement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                System.out.println(" - id=" + rs.getInt("id") + " | name=" + rs.getString("name")
                        + " | price=" + rs.getBigDecimal("price"));
            }
        }
    }
}
