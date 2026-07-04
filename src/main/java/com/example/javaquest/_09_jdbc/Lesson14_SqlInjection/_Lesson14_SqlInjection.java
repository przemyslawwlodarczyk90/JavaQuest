package com.example.javaquest._09_jdbc.Lesson14_SqlInjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson14_SqlInjection {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST SQL INJECTION?
         * ============================================================
         * SQL Injection (wstrzyknięcie SQL) to jeden z najgroźniejszych
         * i najbardziej znanych ataków na aplikacje bazodanowe. Polega na
         * tym, że atakujący wpisuje w pole formularza (np. login, email,
         * pole wyszukiwania) fragment tekstu, który - jeśli aplikacja
         * buduje zapytanie SQL przez PROSTĄ KONKATENACJĘ Stringów - zostaje
         * potraktowany jako część SKŁADNI SQL, a nie jako zwykłe dane.
         *
         * W efekcie atakujący może np. ominąć logowanie, odczytać dane
         * innych użytkowników, a w skrajnych przypadkach - zmodyfikować
         * albo usunąć całą zawartość bazy danych.
         *
         * Źródło problemu jest zawsze to samo: aplikacja miesza ze sobą
         * KOD SQL (stały fragment zapytania) z DANYMI (tekst wpisany przez
         * użytkownika), sklejając je w jeden String.
         */

        String url = "jdbc:h2:mem:jdbclesson14;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement setup = connection.createStatement()) {

            setup.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        password VARCHAR(100) NOT NULL
                    )
                    """);
            setup.executeUpdate("INSERT INTO users VALUES (1, 'jan@example.com', 'Jan Kowalski', 'sekret123')");
            setup.executeUpdate("INSERT INTO users VALUES (2, 'anna@example.com', 'Anna Nowak', 'haslo456')");
            setup.executeUpdate("INSERT INTO users VALUES (3, 'admin@example.com', 'Administrator', 'admin_pass_999')");
            System.out.println("Tabela 'users' przygotowana (3 wiersze, w tym haslo administratora).\n");

            /*
             * ============================================================
             * 🔹 PODATNY KOD - KONKATENACJA STRINGÓW
             * ============================================================
             * Poniższa metoda `findByEmailUnsafe` wygląda niewinnie - to
             * typowy kod "wyszukaj użytkownika po adresie e-mail", jaki
             * mógłby napisać ktoś, kto nie zna zagrożenia SQL Injection.
             * Zapytanie budowane jest przez konkatenację:
             *
             *     "SELECT * FROM users WHERE email = '" + email + "'"
             *
             * Dopóki `email` to normalny adres e-mail, wszystko działa
             * poprawnie. Problem pojawia się, gdy `email` zawiera
             * fragment SQL.
             */
            System.out.println("=== Normalne uzycie (bezpieczny przypadek) ===");
            printResults(findByEmailUnsafe(connection, "jan@example.com"));

            /*
             * ============================================================
             * 🔥 PRAWDZIWY ATAK - WSTRZYKNIĘCIE `' OR '1'='1`
             * ============================================================
             * Atakujący, zamiast prawdziwego adresu e-mail, wpisuje:
             *
             *     anything' OR '1'='1
             *
             * Po sklejeniu ze stałym fragmentem zapytania powstaje:
             *
             *     SELECT * FROM users WHERE email = 'anything' OR '1'='1'
             *
             * Warunek `'1'='1'` jest ZAWSZE prawdziwy, więc `WHERE`
             * przepuszcza WSZYSTKIE wiersze z tabeli - łącznie z hasłem
             * administratora, mimo że atakujący nie znał żadnego
             * prawdziwego adresu e-mail.
             */
            System.out.println("\n=== ATAK: SQL Injection na metode NIEBEZPIECZNA (Statement + konkatenacja) ===");
            String maliciousInput = "anything' OR '1'='1";
            System.out.println("Wstrzykniety 'email': " + maliciousInput);
            var leaked = findByEmailUnsafe(connection, maliciousInput);
            System.out.println("Liczba zwroconych wierszy: " + leaked.size() + " (oczekiwano: WSZYSTKIE, czyli 3)");
            printResults(leaked);
            if (leaked.size() == 3) {
                System.out.println(">>> ATAK SIE POWIODL: wyciekly dane WSZYSTKICH uzytkownikow, wlacznie z haslem admina! <<<");
            } else {
                System.out.println(">>> Atak NIE powiodl sie zgodnie z oczekiwaniem - sprawdz implementacje.");
            }

            /*
             * ============================================================
             * 🔍 OCHRONA - PreparedStatement
             * ============================================================
             * `findByEmailSafe` używa PreparedStatement z parametrem `?`.
             * Wartość przekazana przez `setString()` jest ZAWSZE traktowana
             * jako DOSŁOWNY TEKST danych, NIGDY jako fragment składni SQL -
             * driver bazy danych wysyła zapytanie i parametry ODDZIELNIE,
             * więc żadna kombinacja apostrofów, słów kluczowych SQL (OR,
             * UNION, DROP...) w danych wejściowych nie może "wydostać się"
             * poza rolę zwykłej wartości parametru.
             */
            System.out.println("\n=== TEN SAM ATAK na metode BEZPIECZNA (PreparedStatement) ===");
            var safeResult = findByEmailSafe(connection, maliciousInput);
            System.out.println("Liczba zwroconych wierszy: " + safeResult.size() + " (oczekiwano: 0)");
            printResults(safeResult);
            if (safeResult.isEmpty()) {
                System.out.println(">>> ATAK NIE POWIODL SIE: PreparedStatement potraktowal caly string jako zwykly tekst. <<<");
            } else {
                System.out.println(">>> NIEOCZEKIWANE: PreparedStatement powinien byl zwrocic 0 wierszy!");
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SQL Injection to atak polegający na wstrzyknięciu fragmentu
         *   składni SQL przez dane wejściowe (np. formularz logowania),
         *   gdy aplikacja buduje zapytania przez KONKATENACJĘ Stringów:
         *       ŹLE:     "SELECT * FROM users WHERE email = '" + email + "'"
         *       DOBRZE:  "SELECT * FROM users WHERE email = ?"
         * - Klasyczny atak `' OR '1'='1` zamienia warunek WHERE w warunek
         *   zawsze prawdziwy, ujawniając wszystkie wiersze tabeli.
         * - `PreparedStatement` z parametrami `?` i metodami `setXxx()`
         *   CAŁKOWICIE eliminuje ten problem - dane są zawsze przesyłane
         *   oddzielnie od treści zapytania i nigdy nie są interpretowane
         *   jako SQL.
         * - Wniosek praktyczny: NIGDY nie buduj zapytań SQL przez
         *   konkatenację Stringów z danymi pochodzącymi od użytkownika -
         *   zawsze używaj PreparedStatement (albo mechanizmów wyższego
         *   poziomu, jak ORM-y, które i tak pod spodem używają
         *   PreparedStatement).
         */
    }

    /**
     * NIEBEZPIECZNA metoda - buduje zapytanie przez konkatenację Stringów.
     * Podatna na SQL Injection. NIGDY nie pisz takiego kodu w prawdziwej
     * aplikacji - metoda istnieje wyłącznie w celach edukacyjnych.
     */
    static java.util.List<String> findByEmailUnsafe(Connection connection, String email) throws SQLException {
        java.util.List<String> results = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users WHERE email = '" + email + "'";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                results.add(rs.getInt("id") + " | " + rs.getString("email") + " | "
                        + rs.getString("name") + " | " + rs.getString("password"));
            }
        }
        return results;
    }

    /**
     * BEZPIECZNA metoda - używa PreparedStatement z parametrem.
     * Odporna na SQL Injection.
     */
    static java.util.List<String> findByEmailSafe(Connection connection, String email) throws SQLException {
        java.util.List<String> results = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(rs.getInt("id") + " | " + rs.getString("email") + " | "
                            + rs.getString("name") + " | " + rs.getString("password"));
                }
            }
        }
        return results;
    }

    private static void printResults(java.util.List<String> rows) {
        if (rows.isEmpty()) {
            System.out.println("  (brak wynikow)");
        } else {
            rows.forEach(row -> System.out.println("  " + row));
        }
    }
}
