package com.example.javaquest._09_jdbc.Lesson13_JdbcExceptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson13_JdbcExceptions {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 SQLException - WYJĄTKI W JDBC
         * ============================================================
         * Ogólną teorię wyjątków w Javie (hierarchia Exception/
         * RuntimeException, try-catch, własne wyjątki) poznaliśmy w
         * _01_fundamentals/Lesson16_Exceptions. Specjalizację tego tematu
         * dla operacji na plikach widzieliśmy w _04_io/Lesson14_FileExceptions.
         * Tutaj robimy dokładnie to samo, ale dla JDBC - poznajemy
         * `SQLException`, czyli wyjątek, który niemal KAŻDA metoda JDBC
         * (Connection, Statement, PreparedStatement, ResultSet...) może
         * rzucić (to wyjątek zaznaczony - checked - stąd `throws SQLException`
         * przy tylu metodach w poprzednich lekcjach).
         *
         * `SQLException` reprezentuje BARDZO różne rodzaje problemów:
         * - błędy POŁĄCZENIA (zły adres URL, host nieosiągalny, złe
         *   dane logowania),
         * - błędy SKŁADNI SQL (literówka w zapytaniu, nieistniejąca
         *   tabela/kolumna),
         * - błędy naruszenia OGRANICZEŃ (constraints) - UNIQUE, FOREIGN
         *   KEY, NOT NULL,
         * - błędy związane z TRANSAKCJAMI (np. konflikt przy zatwierdzaniu).
         *
         * Żeby rozróżnić te przypadki, `SQLException` udostępnia (poza
         * standardowym `getMessage()`) dwie dodatkowe metody:
         * - `getSQLState()` - kod stanu SQL zgodny ze standardem
         *   (np. "23505" dla naruszenia unikalności w wielu bazach),
         * - `getErrorCode()` - kod błędu SPECYFICZNY dla danej bazy danych
         *   (ten sam błąd logiczny może mieć inny errorCode w H2, inny
         *   w PostgreSQL, inny w Oracle - to numer nadany przez
         *   konkretnego producenta bazy).
         */

        String url = "jdbc:h2:mem:jdbclesson13;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement setup = connection.createStatement()) {

            setup.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            setup.executeUpdate("INSERT INTO users VALUES (1, 'jan@example.com', 'Jan Kowalski')");
            System.out.println("Tabela 'users' przygotowana.\n");

            /*
             * ============================================================
             * 🔹 BŁĄD SKŁADNI SQL
             * ============================================================
             */
            System.out.println("=== Przyklad 1: blad skladni SQL ===");
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("SELEKT * FROM users"); // literowka - SELEKT zamiast SELECT
            } catch (SQLException e) {
                printExceptionInfo(e);
            }

            /*
             * ============================================================
             * 🔹 NARUSZENIE OGRANICZENIA UNIQUE
             * ============================================================
             */
            System.out.println("\n=== Przyklad 2: naruszenie ograniczenia UNIQUE (email) ===");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO users VALUES (2, 'jan@example.com', 'Inny Jan')");
            } catch (SQLException e) {
                printExceptionInfo(e);
            }

            /*
             * ============================================================
             * 🔹 NARUSZENIE OGRANICZENIA NOT NULL
             * ============================================================
             */
            System.out.println("\n=== Przyklad 3: naruszenie ograniczenia NOT NULL (name) ===");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO users (id, email) VALUES (3, 'ktos@example.com')");
            } catch (SQLException e) {
                printExceptionInfo(e);
            }

            /*
             * ============================================================
             * 🔹 BŁĄD ZŁEJ NAZWY TABELI (odpowiednik błędu "połączenia
             * ze złym obiektem" - w praktyce to też błąd składniowy/
             * odwołania, ale ilustruje inny errorCode niż literówka SQL)
             * ============================================================
             */
            System.out.println("\n=== Przyklad 4: odwolanie do nieistniejacej tabeli ===");
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("SELECT * FROM nonexistent_table");
            } catch (SQLException e) {
                printExceptionInfo(e);
            }

        } catch (SQLException e) {
            System.out.println("Nieoczekiwany blad przygotowania danych: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔍 BŁĘDY POŁĄCZENIA (przykład koncepcyjny)
         * ============================================================
         * Osobną kategorią są błędy POŁĄCZENIA - np. zły URL, nieistniejący
         * host, brak drivera na classpath, złe dane logowania. Poniżej
         * próbujemy połączyć się przez URL wskazujący na bazę MySQL, dla
         * której NIE mamy sterownika na classpath (w tym projekcie jest
         * tylko driver H2) - DriverManager nie znajdzie pasującego
         * drivera i od razu (bez żadnego połączenia sieciowego) rzuci
         * SQLException.
         */
        System.out.println("\n=== Przyklad 5: blad polaczenia (brak odpowiedniego drivera / zly URL) ===");
        try (Connection badConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb")) {
            System.out.println("Polaczenie nawiazane (nie powinno tu dojsc).");
        } catch (SQLException e) {
            printExceptionInfo(e);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `SQLException` to checked exception rzucany przez niemal
         *   każdą metodę JDBC - reprezentuje bardzo różne kategorie
         *   błędów: połączenia, składni SQL, naruszenia ograniczeń
         *   (UNIQUE/FK/NOT NULL), błędów transakcji.
         * - `getMessage()` - czytelny (dla człowieka) opis błędu.
         * - `getSQLState()` - ustandaryzowany kod stanu SQL (przenośny
         *   między różnymi bazami danych w pewnym zakresie).
         * - `getErrorCode()` - kod błędu specyficzny dla konkretnej bazy
         *   danych (H2, PostgreSQL, MySQL... nadają własne numery).
         * - Różne przyczyny błędu (literówka SQL, naruszenie UNIQUE,
         *   naruszenie NOT NULL, brak tabeli, zły URL) dają RÓŻNE
         *   sqlState/errorCode - można to wykorzystać do rozróżniania
         *   kategorii błędów w kodzie (zamiast parsowania getMessage()).
         * - To dokładnie ta sama idea "specjalizacji wyjątków dla danej
         *   dziedziny", jaką widzieliśmy w _04_io/Lesson14_FileExceptions -
         *   tam dla operacji na plikach, tutaj dla operacji na bazie danych.
         */
    }

    private static void printExceptionInfo(SQLException e) {
        System.out.println("  Klasa wyjatku: " + e.getClass().getSimpleName());
        System.out.println("  getMessage():  " + e.getMessage().lines().findFirst().orElse(e.getMessage()));
        System.out.println("  getSQLState(): " + e.getSQLState());
        System.out.println("  getErrorCode(): " + e.getErrorCode());
    }
}
