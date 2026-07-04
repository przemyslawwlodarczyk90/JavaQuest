package com.example.javaquest._10_dao.Lesson02_LayeredArchitecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson02_LayeredArchitecture {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 ARCHITEKTURA WARSTWOWA (LAYERED ARCHITECTURE)
         * ============================================================
         * Typowa aplikacja korzystająca z bazy danych dzieli kod na
         * WARSTWY, z których każda ma jedną, wyraźną odpowiedzialność:
         *
         *   Controller/UI  ->  Service  ->  DAO/Repository  ->  JDBC  ->  Database
         *
         * - CONTROLLER / UI: przyjmuje "żądanie" (np. z formularza, z API
         *   REST, z linii poleceń), wyciąga z niego dane wejściowe i
         *   przekazuje je do warstwy Service. Nie wie NIC o bazie danych.
         * - SERVICE: warstwa logiki biznesowej - waliduje dane, podejmuje
         *   decyzje ("czy ten e-mail jest juz zajety?", "czy zamowienie
         *   kwalifikuje sie do rabatu?"), koordynuje wywolania DAO.
         * - DAO / REPOSITORY: warstwa dostępu do danych - jedyne miejsce,
         *   które wie, jak wygląda SQL, jak wygląda tabela w bazie.
         * - JDBC: mechanizm, którym DAO faktycznie rozmawia z bazą
         *   (Connection, PreparedStatement, ResultSet - poznane w _09_jdbc).
         * - DATABASE: sama baza danych (u nas: H2).
         *
         * 🔹 KLUCZOWE ZASADY (kto o czym NIE POWINIEN wiedzieć)
         * - Controller NIE POWINIEN znać Connection ani SQL-a.
         * - Service NIE POWINIEN ręcznie pisać SQL-a - deleguje to do DAO.
         * - DAO NIE POWINNO decydować o logice biznesowej (np. "czy e-mail
         *   jest poprawny" to decyzja Service, a nie DAO).
         *
         * Każda warstwa rozmawia TYLKO z warstwą bezpośrednio pod sobą.
         * Dzięki temu można podmienić jedną warstwę (np. DAO z JDBC na
         * DAO z Hibernate), nie ruszając warstw powyżej.
         */

        String url = "jdbc:h2:mem:daolesson02;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            /*
             * ============================================================
             * 🔹 BUDUJEMY 3 WARSTWY DLA JEDNEJ OPERACJI: REJESTRACJA UZYTKOWNIKA
             * ============================================================
             * UserDao       - zna SQL, nie zna reguł biznesowych
             * UserService    - zna reguły biznesowe, nie zna SQL (deleguje do DAO)
             * "Controller"  - symulowany tu jako metoda handleRegisterUserRequest,
             *                 udaje np. metodę obsługującą żądanie HTTP - zna
             *                 tylko Service, nie zna nawet istnienia DAO
             */

            UserDao userDao = new UserDao(connection);
            UserService userService = new UserService(userDao);

            System.out.println("=== PRZEPLYW PRZEZ WARSTWY: REJESTRACJA UZYTKOWNIKA ===");
            String response1 = handleRegisterUserRequest(userService, "ania@example.com", "Ania");
            System.out.println("Odpowiedz do klienta: " + response1);

            System.out.println();
            String response2 = handleRegisterUserRequest(userService, "niepoprawny-email", "Zenek");
            System.out.println("Odpowiedz do klienta: " + response2);

            System.out.println();
            String response3 = handleRegisterUserRequest(userService, "ania@example.com", "Ania2");
            System.out.println("Odpowiedz do klienta: " + response3);

            /*
             * ============================================================
             * 🔍 CO SIĘ WŁAŚNIE STAŁO? (prześledź logi powyżej)
             * ============================================================
             * 1) handleRegisterUserRequest ("Controller") dostaje surowe
             *    dane (np. z formularza) i przekazuje je do UserService -
             *    NIE wie nic o Connection ani SQL.
             * 2) UserService.registerUser sprawdza REGUŁY BIZNESOWE (format
             *    e-maila, czy juz istnieje) - a do sprawdzenia "czy juz
             *    istnieje" i do zapisu ZLECA pracę UserDao.
             * 3) UserDao.existsByEmail / UserDao.insert to jedyne miejsca w
             *    całym programie, które zawierają SQL.
             *
             * Gdyby jutro trzeba było dodać REST API zamiast wywołania z
             * main(), wystarczy napisać NOWY controller wołający ten sam
             * UserService - ANI Service, ANI DAO nie trzeba zmieniać.
             */

            System.out.println("\n=== STAN TABELI PO WSZYSTKICH ŻĄDANIACH ===");
            printAllUsers(connection);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Architektura warstwowa: Controller/UI -> Service -> DAO -> JDBC -> Database
         * - Controller: obsługuje żądanie, nie zna SQL ani Connection
         * - Service: logika biznesowa, deleguje dostęp do danych do DAO
         * - DAO: jedyna warstwa znająca SQL, nie podejmuje decyzji biznesowych
         * - Każda warstwa zna TYLKO warstwę bezpośrednio pod sobą - to
         *   ułatwia podmianę implementacji i pisanie testów warstwa po warstwie
         */
    }

    /**
     * Symuluje warstwę Controller/UI - np. metodę obsługującą żądanie HTTP
     * POST /users/register. W realnej aplikacji dane wejściowe pochodziłyby
     * z formularza/JSON-a, tutaj są parametrami metody dla uproszczenia.
     * Controller NIE zna Connection ani SQL - zna tylko UserService.
     */
    private static String handleRegisterUserRequest(UserService userService, String email, String firstName) {
        System.out.println("[Controller] Odebrano zadanie rejestracji: " + email);
        RegisterUserResult result = userService.registerUser(email, firstName);
        if (result.success()) {
            return "201 Created - uzytkownik #" + result.userId() + " zarejestrowany";
        }
        return "400 Bad Request - " + result.errorMessage();
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

    /** Wynik operacji biznesowej rejestracji - zwracany przez warstwę Service do Controllera. */
    private record RegisterUserResult(boolean success, long userId, String errorMessage) {
        static RegisterUserResult ok(long userId) {
            return new RegisterUserResult(true, userId, null);
        }

        static RegisterUserResult failure(String errorMessage) {
            return new RegisterUserResult(false, -1, errorMessage);
        }
    }

    /**
     * WARSTWA SERVICE - logika biznesowa rejestracji uzytkownika.
     * Zna reguły biznesowe (poprawny e-mail, brak duplikatów), ale SAMA
     * nie pisze ani jednej linijki SQL - deleguje to do UserDao.
     */
    private static class UserService {

        private final UserDao userDao;

        UserService(UserDao userDao) {
            this.userDao = userDao;
        }

        RegisterUserResult registerUser(String email, String firstName) {
            System.out.println("[Service] Waliduje dane i sprawdza reguly biznesowe");

            if (email == null || !email.contains("@")) {
                return RegisterUserResult.failure("niepoprawny format e-maila: " + email);
            }

            try {
                if (userDao.existsByEmail(email)) {
                    return RegisterUserResult.failure("e-mail juz zajety: " + email);
                }
                long id = userDao.insert(email, firstName);
                return RegisterUserResult.ok(id);
            } catch (SQLException e) {
                // W realnej aplikacji: przetlumaczenie na wyjatek warstwy serwisowej
                // (patrz _09_jdbc/Lesson - obsluga wyjatkow SQL)
                throw new RuntimeException("Blad podczas rejestracji uzytkownika", e);
            }
        }
    }

    /**
     * WARSTWA DAO - jedyne miejsce znające SQL. Nie podejmuje ŻADNYCH decyzji
     * biznesowych (nie sprawdza formatu e-maila) - tylko wykonuje to, o co
     * poprosi warstwa Service.
     */
    private static class UserDao {

        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        boolean existsByEmail(String email) throws SQLException {
            System.out.println("[DAO] SELECT COUNT(*) FROM users WHERE email = '" + email + "'");
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE email = ?")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    return rs.getInt(1) > 0;
                }
            }
        }

        long insert(String email, String firstName) throws SQLException {
            System.out.println("[DAO] INSERT INTO users (email, first_name) VALUES ('" + email + "', '" + firstName + "')");
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (email, first_name) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, email);
                stmt.setString(2, firstName);
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return keys.getLong(1);
                }
            }
        }
    }
}
