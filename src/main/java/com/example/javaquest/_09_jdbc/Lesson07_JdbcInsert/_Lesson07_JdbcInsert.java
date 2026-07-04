package com.example.javaquest._09_jdbc.Lesson07_JdbcInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson07_JdbcInsert {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DODAWANIE REKORDU PRZEZ PreparedStatement + executeUpdate()
         * ============================================================
         * Standardowy INSERT w JDBC to: PreparedStatement z zapytaniem
         * INSERT, ustawienie parametrów przez settery (Lesson05), a
         * potem wywołanie executeUpdate() - zwraca ono LICZBĘ zmienionych
         * wierszy. Dla pojedynczego INSERT-a ta liczba PRAWIE ZAWSZE
         * powinna wynosić dokładnie 1 - warto to sprawdzić, żeby
         * upewnić się, że operacja faktycznie coś wstawiła.
         */

        String url = "jdbc:h2:mem:jdbclesson07;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            try (Statement ddl = connection.createStatement()) {
                // GENERATED ALWAYS AS IDENTITY - H2 samo generuje kolejne
                // wartosci klucza glownego (odpowiednik AUTO_INCREMENT w
                // MySQL czy SERIAL w PostgreSQL)
                ddl.execute("""
                        CREATE TABLE orders (
                            id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            customer_name VARCHAR(100) NOT NULL,
                            total_amount DECIMAL(10,2) NOT NULL
                        )
                        """);
            }

            /*
             * ============================================================
             * 🔹 PODSTAWOWY INSERT - sprawdzenie liczby zmienionych wierszy
             * ============================================================
             */

            System.out.println("=== PODSTAWOWY INSERT ===");
            String insertSql = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, "Jan Kowalski");
                ps.setBigDecimal(2, new BigDecimal("249.99"));

                int affectedRows = ps.executeUpdate();
                System.out.println("Liczba zmienionych wierszy: " + affectedRows);
                if (affectedRows != 1) {
                    throw new IllegalStateException("Oczekiwano wstawienia dokladnie 1 wiersza, a wstawiono: " + affectedRows);
                }
                System.out.println("INSERT potwierdzony - dokladnie 1 wiersz.");
            }

            /*
             * ============================================================
             * 🔍 POBIERANIE WYGENEROWANEGO ID (Statement.RETURN_GENERATED_KEYS)
             * ============================================================
             * Gdy klucz główny jest generowany AUTOMATYCZNIE przez bazę
             * (jak tu - GENERATED ALWAYS AS IDENTITY), zwykle chcemy
             * poznać wygenerowaną wartość zaraz po wstawieniu wiersza -
             * np. żeby użyć jej do wstawienia powiązanych rekordów
             * (klucz obcy) w tej samej operacji biznesowej.
             *
             * Robimy to przez:
             * 1) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
             *    - informujemy driver, że chcemy mieć dostęp do
             *      wygenerowanych kluczy,
             * 2) ps.getGeneratedKeys() PO wykonaniu executeUpdate() -
             *    zwraca ResultSet z wygenerowanymi wartościami (typowo
             *    jedna kolumna, jeden wiersz - ID nowo wstawionego rekordu).
             */

            System.out.println("\n=== INSERT z pobraniem wygenerowanego ID ===");
            String insertWithIdSql = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
            long generatedOrderId;
            try (PreparedStatement ps = connection.prepareStatement(insertWithIdSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "Ewa Nowak");
                ps.setBigDecimal(2, new BigDecimal("599.00"));

                int affectedRows = ps.executeUpdate();
                System.out.println("Liczba zmienionych wierszy: " + affectedRows);

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderId = generatedKeys.getLong(1); // pierwsza (i jedyna) kolumna wygenerowanych kluczy
                        System.out.println("Wygenerowane ID zamowienia: " + generatedOrderId);
                    } else {
                        throw new IllegalStateException("Baza nie zwrocila wygenerowanego klucza.");
                    }
                }
            }

            /*
             * ============================================================
             * 🔹 UŻYCIE WYGENEROWANEGO ID W KOLEJNYM ZAPYTANIU
             * ============================================================
             * Typowy scenariusz: mamy tabelę "order_items" powiązaną z
             * "orders" przez klucz obcy order_id. Świeżo wygenerowane ID
             * zamówienia wykorzystujemy od razu do wstawienia pozycji
             * zamówienia - bez tego musielibyśmy dodatkowo dopytywać
             * bazę o to, jaki był ostatnio wstawiony rekord.
             */

            try (Statement ddl = connection.createStatement()) {
                ddl.execute("""
                        CREATE TABLE order_items (
                            id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            order_id INT NOT NULL REFERENCES orders(id),
                            product_name VARCHAR(100) NOT NULL
                        )
                        """);
            }

            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO order_items (order_id, product_name) VALUES (?, ?)")) {
                ps.setLong(1, generatedOrderId);
                ps.setString(2, "Klawiatura mechaniczna");
                int rows = ps.executeUpdate();
                System.out.println("\n=== INSERT pozycji zamowienia z uzyciem wygenerowanego ID (" + generatedOrderId + ") ===");
                System.out.println("Wstawiono wierszy: " + rows);
            }

            // Weryfikacja - odczytujemy zamówienie razem z jego pozycją,
            // łącząc obie tabele po order_id (JOIN znany z _08_sql)
            System.out.println("\n=== WERYFIKACJA - JOIN orders + order_items ===");
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("""
                         SELECT o.id AS order_id, o.customer_name, oi.product_name
                         FROM orders o
                         JOIN order_items oi ON oi.order_id = o.id
                         """)) {
                while (rs.next()) {
                    System.out.println("Zamowienie #" + rs.getInt("order_id") + " (" + rs.getString("customer_name")
                            + ") zawiera: " + rs.getString("product_name"));
                }
            }
        }

        System.out.println("\n=== KONIEC LEKCJI 7 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - INSERT przez PreparedStatement + executeUpdate() zwraca
         *   liczbę zmienionych wierszy - dla pojedynczego INSERT-a
         *   powinna wynosić dokładnie 1
         * - Aby pobrać automatycznie wygenerowany klucz główny:
         *   1) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
         *   2) po executeUpdate() -> ps.getGeneratedKeys() zwraca
         *      ResultSet z wygenerowaną wartością (zwykle jeden wiersz,
         *      jedna kolumna)
         * - Wygenerowane ID można od razu wykorzystać do wstawienia
         *   powiązanych rekordów (np. pozycji zamówienia z kluczem obcym)
         * - H2: GENERATED ALWAYS AS IDENTITY (odpowiednik AUTO_INCREMENT
         *   w MySQL / SERIAL w PostgreSQL) do automatycznego generowania
         *   kolejnych wartości klucza głównego
         */
    }
}
