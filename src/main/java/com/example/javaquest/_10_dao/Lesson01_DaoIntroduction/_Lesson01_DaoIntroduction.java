package com.example.javaquest._10_dao.Lesson01_DaoIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson01_DaoIntroduction {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST DAO?
         * ============================================================
         * DAO (Data Access Object) to WZORZEC PROJEKTOWY, którego zadaniem
         * jest ODDZIELENIE kodu odpowiedzialnego za KONTAKT Z BAZĄ DANYCH
         * (SQL, PreparedStatement, ResultSet - wszystko, co poznałeś w
         * poprzednim rozdziale _09_jdbc) od kodu odpowiedzialnego za
         * LOGIKĘ BIZNESOWĄ aplikacji (walidacje, reguły, decyzje).
         *
         * DAO to zwykła klasa (albo interfejs + implementacja), która
         * udostępnia metody typu "znajdź uzytkownika", "zapisz uzytkownika",
         * "usun uzytkownika" - a CAŁA "brudna robota" związana z SQL
         * dzieje się WEWNĄTRZ tych metod, ukryta przed resztą aplikacji.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * DAO powinno odpowiadać za KONTAKT Z BAZĄ, a NIE za logikę
         * biznesową. DAO nie powinno wiedzieć "dlaczego" coś zapisujemy,
         * tylko "jak" to zapisać.
         */

        String url = "jdbc:h2:mem:daolesson01;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            /*
             * ============================================================
             * 🔴 ŹLE: logika biznesowa wymieszana z SQL w jednej metodzie
             * ============================================================
             * Poniższa metoda registerUserBadWay robi WSZYSTKO na raz:
             * - waliduje format e-maila (logika biznesowa)
             * - sprawdza, czy e-mail juz istnieje w bazie (zapytanie SQL)
             * - wstawia nowy wiersz (kolejne zapytanie SQL)
             * - decyduje, co wypisac uzytkownikowi (logika prezentacji)
             *
             * Efekt: metoda jest długa, trudna do przetestowania (wymaga
             * zawsze prawdziwego polaczenia z baza, nawet zeby przetestowac
             * SAMA walidacje e-maila!) i trudna do ponownego uzycia. Gdyby
             * jutro trzeba było zamienic baze SQL na plik albo na inna baze,
             * trzeba by przepisac TĘ SAMĄ metodę, która zawiera też logikę
             * walidacji - łatwo coś przy okazji zepsuć.
             */

            System.out.println("=== ZLA PRAKTYKA: logika biznesowa + SQL wymieszane ===");
            registerUserBadWay(connection, "ania@example.com", "Ania");
            registerUserBadWay(connection, "niepoprawny-email", "Zenek");
            registerUserBadWay(connection, "ania@example.com", "Ania2"); // duplikat

            /*
             * ============================================================
             * 🟢 DOBRZE: SQL wydzielony do prostego DAO
             * ============================================================
             * SimpleUserDao poniżej odpowiada TYLKO za rozmowę z bazą danych:
             * "czy istnieje e-mail?", "wstaw nowy wiersz". Nie wie NIC o
             * regułach biznesowych (np. że e-mail musi zawierać znak '@').
             *
             * Logika biznesowa (walidacja formatu e-maila, decyzja czy
             * rejestracja się powiedzie) została wydzielona do OSOBNEJ
             * metody registerUserGoodWay, która KORZYSTA z DAO, ale
             * sama nie zna ani jednej linijki SQL.
             *
             * Korzyści:
             * - walidację e-maila można przetestować BEZ bazy danych
             * - SimpleUserDao można podmienić (np. na wersję testową
             *   trzymającą dane w pamięci) bez dotykania logiki biznesowej
             * - każda metoda robi JEDNĄ rzecz i jest krótka, czytelna
             */

            System.out.println("\n=== DOBRA PRAKTYKA: SQL wydzielone do DAO ===");
            SimpleUserDao userDao = new SimpleUserDao(connection);
            registerUserGoodWay(userDao, "bartek@example.com", "Bartek");
            registerUserGoodWay(userDao, "niepoprawny-email", "Zenek");
            registerUserGoodWay(userDao, "bartek@example.com", "Bartek2"); // duplikat

            System.out.println("\n=== STAN TABELI PO WSZYSTKICH PROBACH ===");
            printAllUsers(connection);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DAO (Data Access Object) = wzorzec oddzielający kod SQL od
         *   logiki biznesowej aplikacji
         * - DAO odpowiada za KONTAKT Z BAZĄ (jak zapisać/odczytać dane),
         *   NIE za logikę biznesową (dlaczego, kiedy, na jakich warunkach)
         * - Mieszanie SQL z logiką biznesową w jednej metodzie utrudnia
         *   testowanie, ponowne użycie i późniejszą zmianę źródła danych
         * - Wydzielenie prostego DAO (np. SimpleUserDao) sprawia, że
         *   logika biznesowa staje się krótka, czytelna i niezależna od SQL
         * - To dopiero wstęp - w kolejnych lekcjach DAO stanie się
         *   pełnoprawnym interfejsem w architekturze warstwowej
         */
    }

    /**
     * 🔴 ZŁY PRZYKŁAD: logika biznesowa (walidacja, komunikaty) wymieszana
     * z kodem SQL (sprawdzenie duplikatu, insert) w JEDNEJ metodzie.
     */
    private static void registerUserBadWay(Connection connection, String email, String firstName)
            throws SQLException {

        // logika biznesowa: walidacja formatu e-maila
        if (!email.contains("@")) {
            System.out.println("Rejestracja odrzucona - niepoprawny e-mail: " + email);
            return;
        }

        // SQL: sprawdzenie czy e-mail juz istnieje
        try (PreparedStatement checkStmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM users WHERE email = ?")) {
            checkStmt.setString(1, email);
            try (ResultSet rs = checkStmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    System.out.println("Rejestracja odrzucona - e-mail juz istnieje: " + email);
                    return;
                }
            }
        }

        // SQL: wstawienie nowego wiersza
        try (PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO users (email, first_name) VALUES (?, ?)")) {
            insertStmt.setString(1, email);
            insertStmt.setString(2, firstName);
            insertStmt.executeUpdate();
        }

        // logika prezentacji
        System.out.println("Zarejestrowano uzytkownika: " + firstName + " (" + email + ")");
    }

    /**
     * 🟢 DOBRY PRZYKŁAD: logika biznesowa wydzielona, korzysta z DAO
     * zamiast samodzielnie pisać SQL. Ta metoda dałoby się przetestować
     * bez żadnej bazy danych, wystarczyłoby podmienić dao na atrapę.
     */
    private static void registerUserGoodWay(SimpleUserDao userDao, String email, String firstName)
            throws SQLException {

        if (!email.contains("@")) {
            System.out.println("Rejestracja odrzucona - niepoprawny e-mail: " + email);
            return;
        }

        if (userDao.existsByEmail(email)) {
            System.out.println("Rejestracja odrzucona - e-mail juz istnieje: " + email);
            return;
        }

        userDao.insert(email, firstName);
        System.out.println("Zarejestrowano uzytkownika: " + firstName + " (" + email + ")");
    }

    private static void printAllUsers(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, email, first_name FROM users ORDER BY id")) {
            while (rs.next()) {
                System.out.println(" - #" + rs.getLong("id") + " " + rs.getString("first_name")
                        + " <" + rs.getString("email") + ">");
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        first_name VARCHAR(100) NOT NULL
                    )
                    """);
        }
    }

    /**
     * Prosty DAO - odpowiada WYŁĄCZNIE za rozmowę z bazą danych.
     * Nie zna żadnej reguły biznesowej (np. jak wygląda poprawny e-mail).
     */
    private static class SimpleUserDao {

        private final Connection connection;

        SimpleUserDao(Connection connection) {
            this.connection = connection;
        }

        boolean existsByEmail(String email) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE email = ?")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    return rs.getInt(1) > 0;
                }
            }
        }

        void insert(String email, String firstName) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (email, first_name) VALUES (?, ?)")) {
                stmt.setString(1, email);
                stmt.setString(2, firstName);
                stmt.executeUpdate();
            }
        }
    }
}
