package com.example.javaquest._10_dao.Lesson21_ValidationBeforeSave;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson21_ValidationBeforeSave {

    private static final String URL = "jdbc:h2:mem:daolesson21;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DWIE LINIE OBRONY: WALIDACJA W SERVICE I W BAZIE
         * ============================================================
         * Poprawne dane w aplikacji chronią DWIE niezależne warstwy:
         *
         * 1) WALIDACJA W SERVICE (logika biznesowa) - wykonuje się PRZED
         *    wysłaniem czegokolwiek do bazy. Np. "cena musi być dodatnia",
         *    "email nie może być pusty". Zaletą jest to, że użytkownik
         *    dostaje czytelny komunikat NATYCHMIAST, bez zbędnego
         *    zapytania SQL.
         *
         * 2) WALIDACJA W BAZIE (ograniczenia/constraints: NOT NULL,
         *    UNIQUE, CHECK) - to OSTATNIA LINIA OBRONY. Zadziała nawet,
         *    jeśli walidacja w Service z jakiegoś powodu coś przepuści
         *    (np. błąd w kodzie, wyścig między wątkami, inny serwis
         *    zapisujący bezpośrednio do tej samej bazy).
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * Walidacja w Service to szybka, wygodna "pierwsza linia obrony",
         * ale ograniczenia bazy danych to jedyna gwarancja, że NIEPOPRAWNE
         * dane NIGDY nie trafią do tabeli - dlatego Service powinien
         * łapać też błędy z bazy i tłumaczyć je na czytelne komunikaty.
         */

        try (Connection connection = DriverManager.getConnection(URL)) {
            setUpSchema(connection);

            UserDao userDao = new UserDao(connection);
            UserService userService = new UserService(userDao);

            /*
             * ============================================================
             * 🔍 Przypadek 1: walidacja w Service łapie błąd ZANIM
             * cokolwiek trafi do bazy
             * ============================================================
             */
            System.out.println("=== Przypadek 1: pusty e-mail (walidacja w Service) ===");
            System.out.println(userService.registerUser("", "Ania"));

            System.out.println("\n=== Przypadek 2: ujemna 'reputacja' - walidacja w Service ===");
            System.out.println(userService.registerUser("zenek@example.com", "Zenek", -5));

            /*
             * ============================================================
             * 🔍 Przypadek 3: poprawna rejestracja
             * ============================================================
             */
            System.out.println("\n=== Przypadek 3: poprawne dane ===");
            System.out.println(userService.registerUser("bartek@example.com", "Bartek"));

            /*
             * ============================================================
             * 🔴 Przypadek 4: duplikat e-maila - walidacja w Service NIE
             * WYSTARCZY (bez dodatkowego zapytania SELECT trudno to
             * wykryć z wyprzedzeniem, a i tak mogłby wystąpić wyścig
             * dwóch równoległych żądań). Baza (ograniczenie UNIQUE) łapie
             * to jako OSTATNIA LINIA OBRONY - Service tłumaczy surowy
             * błąd bazy na czytelny komunikat.
             * ============================================================
             */
            System.out.println("\n=== Przypadek 4: duplikat e-maila - UNIQUE w bazie jako ostatnia linia obrony ===");
            System.out.println(userService.registerUser("bartek@example.com", "Bartek2"));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Walidacja w Service (przed zapisem) to logika biznesowa:
         *   szybka, czytelna, NIE wymaga zapytania do bazy dla prostych
         *   reguł (np. "cena > 0", "pole nie jest puste")
         * - Ograniczenia bazy danych (NOT NULL, UNIQUE, CHECK) to
         *   OSTATNIA LINIA OBRONY - zadziałają zawsze, nawet jeśli
         *   walidacja w Service coś przepuści
         * - Niektórych reguł (np. unikalność e-maila przy współbieżnych
         *   żądaniach) NIE da się w 100% niezawodnie sprawdzić wyłącznie
         *   w Service - baza z ograniczeniem UNIQUE jest tu konieczna
         * - Service powinien łapać surowe błędy z bazy (np. naruszenie
         *   UNIQUE) i TŁUMACZYĆ je na czytelny komunikat dla użytkownika -
         *   NIGDY nie pokazujemy użytkownikowi surowego SQLException
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        name VARCHAR(100) NOT NULL,
                        reputation INT NOT NULL DEFAULT 0 CHECK (reputation >= 0)
                    )
                    """);
        }
    }

    private static class UserDao {
        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        void insert(String email, String name, int reputation) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (email, name, reputation) VALUES (?, ?, ?)")) {
                stmt.setString(1, email);
                stmt.setString(2, name);
                stmt.setInt(3, reputation);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Service: WALIDUJE dane przed zapisem (pierwsza linia obrony), a
     * dodatkowo łapie błędy z bazy (druga/ostatnia linia obrony) i
     * tłumaczy je na czytelne komunikaty.
     */
    private static class UserService {
        private final UserDao userDao;

        UserService(UserDao userDao) {
            this.userDao = userDao;
        }

        String registerUser(String email, String name) {
            return registerUser(email, name, 0);
        }

        String registerUser(String email, String name, int reputation) {
            // 1) WALIDACJA W SERVICE - przed jakimkolwiek kontaktem z baza
            if (email == null || email.isBlank()) {
                return "Blad walidacji: e-mail nie moze byc pusty";
            }
            if (!email.contains("@")) {
                return "Blad walidacji: niepoprawny format e-maila: " + email;
            }
            if (reputation < 0) {
                return "Blad walidacji: reputacja nie moze byc ujemna (podano " + reputation + ")";
            }

            // 2) DOPIERO TERAZ kontakt z baza - a i tak moze sie nie udac
            // (np. duplikat e-maila, ktorego nie sprawdzalismy osobnym SELECT-em)
            try {
                userDao.insert(email, name, reputation);
                return "Zarejestrowano uzytkownika: " + name + " <" + email + ">";
            } catch (SQLException e) {
                // 3) OSTATNIA LINIA OBRONY zadziałała w bazie - tłumaczymy
                // surowy blad na czytelny komunikat, NIE pokazujemy SQLException
                if (isUniqueViolation(e)) {
                    return "Rejestracja odrzucona: e-mail '" + email + "' jest juz uzywany";
                }
                return "Rejestracja odrzucona: wystapil nieoczekiwany blad techniczny";
            }
        }

        private boolean isUniqueViolation(SQLException e) {
            // H2 zwraca SQLState "23505" dla naruszenia ograniczenia UNIQUE
            return "23505".equals(e.getSQLState());
        }
    }
}
