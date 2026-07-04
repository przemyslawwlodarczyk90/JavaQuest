package com.example.javaquest._10_dao.Lesson11_ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson11_ConnectionFactory {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PROBLEM: URL/LOGIN/HASŁO POWIELONE W KAŻDYM DAO
         * ============================================================
         * Do tej pory każdy DAO w naszych lekcjach dostawał gotowe
         * Connection z zewnątrz (od main()). To dobre dla ćwiczeń, ale
         * w prawdziwej aplikacji ktoś w KOŃCU musi to Connection
         * UTWORZYĆ - i jeśli robi to KAŻDY DAO z osobna, adres bazy,
         * login i hasło powtarzają się w wielu miejscach kodu.
         *
         * Efekt: gdy baza danych się zmieni (inny host, inne hasło po
         * rotacji), trzeba pamiętać, żeby poprawić WSZYSTKIE miejsca.
         * Łatwo o pomyłkę - jedno zapomniane miejsce i aplikacja łączy
         * się częściowo ze starą, częściowo z nową bazą.
         */

        System.out.println("=== PRZED: każdy DAO sam buduje połączenie ===");
        demonstrateDuplicatedConnectionCode();

        /*
         * ============================================================
         * 🔹 ROZWIĄZANIE: CONNECTION FACTORY
         * ============================================================
         * ConnectionFactory to JEDNA klasa, która jako jedyna zna adres
         * bazy, login i hasło (są PRYWATNYMI stałymi). Każdy DAO, który
         * potrzebuje połączenia, woła:
         *
         *     Connection connection = ConnectionFactory.getConnection();
         *
         * i nie musi wiedzieć NIC o URL-u, loginie ani haśle. Zmiana
         * bazy danych to teraz zmiana w JEDNYM miejscu.
         */

        System.out.println("\n=== PO: jedna fabryka połączeń dla wszystkich DAO ===");
        UserDaoAfter userDao = new UserDaoAfter();
        ProductDaoAfter productDao = new ProductDaoAfter();

        userDao.setUp();
        productDao.setUp();

        userDao.insert("Ania");
        productDao.insert("Klawiatura");

        System.out.println("Uzytkownicy: " + userDao.findAll());
        System.out.println("Produkty: " + productDao.findAll());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bez fabryki połączeń URL/login/hasło powtarzają się w każdym
         *   DAO - trudno to utrzymać i łatwo o pomyłkę przy zmianie bazy
         * - ConnectionFactory centralizuje tworzenie Connection w JEDNEJ
         *   klasie - reszta aplikacji wywołuje tylko
         *   ConnectionFactory.getConnection()
         * - DAO korzystające z fabryki nie znają ani adresu bazy, ani
         *   danych logowania - te szczegóły są UKRYTE
         * - To wciąż nie jest "produkcyjne" rozwiązanie (dane logowania
         *   są zaszyte w kodzie jako stałe) - w kolejnych lekcjach
         *   (Lesson12_DatabaseConfiguration, Lesson13_EnvironmentVariables)
         *   wyniesiemy je NA ZEWNĄTRZ kodu
         */
    }

    /**
     * Symulacja "starego" podejścia: dwa DAO, każdy z WŁASNĄ, powieloną
     * stałą URL. W realnym projekcie takich klas bywają dziesiątki.
     */
    private static void demonstrateDuplicatedConnectionCode() throws SQLException {
        UserDaoBefore userDaoBefore = new UserDaoBefore();
        ProductDaoBefore productDaoBefore = new ProductDaoBefore();

        System.out.println("UserDaoBefore.DB_URL      = " + UserDaoBefore.DB_URL);
        System.out.println("ProductDaoBefore.DB_URL   = " + ProductDaoBefore.DB_URL);
        System.out.println("^ ten sam URL wpisany RĘCZNIE w dwóch miejscach - łatwo o rozjazd");

        userDaoBefore.setUp();
        productDaoBefore.setUp();
    }

    /**
     * 🔴 PRZED: DAO samodzielnie zna adres bazy danych.
     */
    private static class UserDaoBefore {
        static final String DB_URL = "jdbc:h2:mem:daolesson11_before;DB_CLOSE_DELAY=-1";

        void setUp() throws SQLException {
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            }
        }
    }

    /**
     * 🔴 PRZED: kolejny DAO, ta sama stała URL powielona RĘCZNIE.
     */
    private static class ProductDaoBefore {
        static final String DB_URL = "jdbc:h2:mem:daolesson11_before;DB_CLOSE_DELAY=-1";

        void setUp() throws SQLException {
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS products (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            }
        }
    }

    /**
     * 🟢 PO: jedyne miejsce w aplikacji, które zna adres bazy, login
     * i hasło. Wszystkie DAO korzystają WYŁĄCZNIE z tej klasy.
     */
    static class ConnectionFactory {

        private static final String URL = "jdbc:h2:mem:daolesson11_after;DB_CLOSE_DELAY=-1";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        private ConnectionFactory() {
            // klasa narzędziowa - nie tworzymy instancji
        }

        static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    /**
     * 🟢 PO: DAO nie zna ani URL-a, ani hasła - korzysta z ConnectionFactory.
     */
    private static class UserDaoAfter {

        void setUp() throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            }
        }

        void insert(String name) throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (name) VALUES (?)")) {
                stmt.setString(1, name);
                stmt.executeUpdate();
            }
        }

        String findAll() throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT name FROM users ORDER BY id")) {
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    if (!sb.isEmpty()) {
                        sb.append(", ");
                    }
                    sb.append(rs.getString("name"));
                }
                return sb.toString();
            }
        }
    }

    /**
     * 🟢 PO: kolejny DAO, korzystający z TEJ SAMEJ fabryki - żadnej
     * powtórzonej stałej URL.
     */
    private static class ProductDaoAfter {

        void setUp() throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS products (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            }
        }

        void insert(String name) throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO products (name) VALUES (?)")) {
                stmt.setString(1, name);
                stmt.executeUpdate();
            }
        }

        String findAll() throws SQLException {
            try (Connection connection = ConnectionFactory.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT name FROM products ORDER BY id")) {
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    if (!sb.isEmpty()) {
                        sb.append(", ");
                    }
                    sb.append(rs.getString("name"));
                }
                return sb.toString();
            }
        }
    }
}
