package com.example.javaquest._10_dao.Lesson23_DynamicSorting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class _Lesson23_DynamicSorting {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PO CO DYNAMICZNE SORTOWANIE?
         * ============================================================
         * W realnej aplikacji użytkownik interfejsu zwykle sam wybiera,
         * po czym posortować listę wyników - klika nagłówek kolumny
         * "Nazwa" albo "E-mail" w tabeli, albo przekazuje parametr URL:
         *
         *   GET /users?sort=name&dir=desc
         *
         * Kolumna i kierunek sortowania NIE są znane w momencie pisania
         * kodu - są parametrem przekazanym w runtime. Zapytanie SQL musi
         * więc zbudować `ORDER BY` DYNAMICZNIE, na podstawie tego, co
         * przyszło od klienta.
         */

        String url = "jdbc:h2:mem:daolesson23;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);
            UserDao userDao = new UserDao(connection);

            /*
             * ============================================================
             * 🔍 DLACZEGO NIE MOŻNA SPARAMETRYZOWAĆ NAZWY KOLUMNY PRZEZ "?"
             * ============================================================
             * Z PreparedStatement (Lesson05_PreparedStatement w _09_jdbc)
             * znasz już wzorzec:
             *
             *   "SELECT * FROM users WHERE id = ?"   <- "?" zastępuje WARTOŚĆ
             *
             * Parametr `?` w JDBC działa TYLKO tam, gdzie SQL oczekuje
             * WARTOŚCI (literału) - np. po prawej stronie `=`, w `LIMIT`,
             * w `VALUES(...)`. Nie zadziała jednak tutaj:
             *
             *   "SELECT * FROM users ORDER BY ?"   <- BŁĄD w praktyce!
             *
             * Baza danych nie potrafi "podstawić" nazwy kolumny (ani słowa
             * ASC/DESC) pod placeholder `?` - to nie jest wartość danych,
             * tylko fragment SKŁADNI zapytania (identyfikator kolumny).
             * Sterownik JDBC albo zgłosi błąd, albo (w najgorszym razie)
             * potraktuje to jako literał tekstowy i posortuje po nim
             * dosłownie - w każdym razie NIE zadziała to tak, jak
             * oczekujemy.
             *
             * Jedyne wyjście: nazwę kolumny i kierunek sortowania trzeba
             * WSTAWIĆ do tekstu SQL jako fragment identyfikatora/słowa
             * kluczowego - a to oznacza, że NIE możemy ich bezpiecznie
             * sparametryzować przez `?`.
             */

            /*
             * ============================================================
             * 🔥 ZAGROŻENIE: SQL INJECTION PRZEZ NAZWĘ KOLUMNY
             * ============================================================
             * Skoro nazwa kolumny musi trafić do SQL przez konkatenację
             * Stringów, to NAIWNA implementacja:
             *
             *   String sql = "SELECT * FROM users ORDER BY " + column + " " + direction;
             *
             * jest DOKŁADNIE tak samo podatna na SQL Injection, jak
             * konkatenacja wartości z Lesson14_SqlInjection (_09_jdbc) -
             * tylko wektor ataku jest inny: zamiast wartości parametru,
             * atakujący wstrzykuje przez PARAMETR SORTOWANIA, np.:
             *
             *   column = "id; DROP TABLE users; --"
             *
             * albo (bardziej subtelnie, bez średnika, bo H2/JDBC zwykle
             * nie wykonuje wielu instrukcji na raz):
             *
             *   column = "(SELECT password_hash FROM admin_users LIMIT 1)"
             *
             * ⚠️ JEDYNA bezpieczna obrona: WALIDACJA WZGLĘDEM BIAŁEJ LISTY
             * (whitelist) znanych, dozwolonych wartości - NIGDY nie ufamy
             * surowemu tekstowi od użytkownika jako nazwie kolumny.
             */

            System.out.println("=== Sortowanie po dozwolonych kolumnach ===");
            System.out.println("\n-- ORDER BY name ASC --");
            print(userDao.findAllSorted("name", "ASC"));

            System.out.println("\n-- ORDER BY name DESC --");
            print(userDao.findAllSorted("name", "DESC"));

            System.out.println("\n-- ORDER BY email ASC --");
            print(userDao.findAllSorted("email", "ASC"));

            System.out.println("\n=== Proba uzycia NIEDOZWOLONEJ kolumny ===");
            try {
                userDao.findAllSorted("password_hash", "ASC");
            } catch (IllegalArgumentException e) {
                System.out.println("Odrzucono: " + e.getMessage());
            }

            System.out.println("\n=== Proba SQL Injection przez parametr sortowania ===");
            String maliciousColumn = "id; DROP TABLE users; --";
            try {
                userDao.findAllSorted(maliciousColumn, "ASC");
                System.out.println(">>> NIEOCZEKIWANE: atak powinien zostac odrzucony!");
            } catch (IllegalArgumentException e) {
                System.out.println("Wstrzykniety 'column': " + maliciousColumn);
                System.out.println("Odrzucono PRZED zbudowaniem SQL: " + e.getMessage());
                System.out.println(">>> Tabela 'users' bezpieczna - whitelist zablokowala atak.");
            }

            System.out.println("\n=== Proba niedozwolonego kierunku sortowania ===");
            try {
                userDao.findAllSorted("name", "ASC; DROP TABLE users; --");
            } catch (IllegalArgumentException e) {
                System.out.println("Odrzucono: " + e.getMessage());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Dynamiczne sortowanie = kolumna i kierunek ORDER BY podane
         *   jako parametr w runtime (np. wybór użytkownika w UI).
         * - `?` (PreparedStatement) NIE działa dla nazw kolumn/kierunku -
         *   to fragmenty SKŁADNI SQL, nie wartości danych.
         * - Skoro trzeba je wstawić przez konkatenację Stringów, powstaje
         *   ryzyko SQL Injection PRZEZ PARAMETR SORTOWANIA - identyczne
         *   zagrożenie jak w Lesson14_SqlInjection (_09_jdbc), tylko inny
         *   wektor ataku.
         * - JEDYNA bezpieczna obrona: WALIDACJA względem BIAŁEJ LISTY
         *   (Set) znanych, dozwolonych wartości - zarówno dla kolumny,
         *   jak i dla kierunku (ASC/DESC) - PRZED zbudowaniem SQL.
         * - Jeśli wartość spoza whitelisty - rzucamy wyjątek (tu:
         *   IllegalArgumentException) i NIGDY nie budujemy zapytania
         *   z niezwalidowanym fragmentem.
         */
    }

    private static void print(List<User> users) {
        users.forEach(u -> System.out.println("  #" + u.id() + " " + u.name() + " <" + u.email() + ">"));
    }

    private record User(long id, String name, String email) {
    }

    /** DAO uzytkownikow z bezpiecznym, dynamicznym sortowaniem. */
    private static class UserDao {

        // BIALA LISTA dozwolonych kolumn sortowania - jedyne wartosci,
        // ktore wolno wstawic do ORDER BY.
        private static final Set<String> ALLOWED_COLUMNS = Set.of("id", "name", "email");
        private static final Set<String> ALLOWED_DIRECTIONS = Set.of("ASC", "DESC");

        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        List<User> findAllSorted(String column, String direction) throws SQLException {
            String safeColumn = validateColumn(column);
            String safeDirection = validateDirection(direction);

            // Bezpieczne dopiero PO walidacji obu wartosci wzgledem
            // bialej listy - kazda mozliwa wartosc "safeColumn" i
            // "safeDirection" jest jedna ze SKONCZONEGO, znanego z gory
            // zbioru, wiec konkatenacja nie moze juz wprowadzic zadnej
            // "obcej" skladni SQL.
            String sql = "SELECT id, name, email FROM users ORDER BY " + safeColumn + " " + safeDirection;

            List<User> result = new ArrayList<>();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(new User(rs.getLong("id"), rs.getString("name"), rs.getString("email")));
                }
            }
            return result;
        }

        private String validateColumn(String column) {
            if (column == null || !ALLOWED_COLUMNS.contains(column)) {
                throw new IllegalArgumentException(
                        "Niedozwolona kolumna sortowania: '" + column + "'. Dozwolone: " + ALLOWED_COLUMNS);
            }
            return column;
        }

        private String validateDirection(String direction) {
            String normalized = direction == null ? "" : direction.trim().toUpperCase();
            if (!ALLOWED_DIRECTIONS.contains(normalized)) {
                throw new IllegalArgumentException(
                        "Niedozwolony kierunek sortowania: '" + direction + "'. Dozwolone: " + ALLOWED_DIRECTIONS);
            }
            return normalized;
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL,
                        password_hash VARCHAR(255) NOT NULL
                    )
                    """);
            stmt.executeUpdate("INSERT INTO users (name, email, password_hash) VALUES ('Kasia', 'kasia@example.com', 'h1')");
            stmt.executeUpdate("INSERT INTO users (name, email, password_hash) VALUES ('Adam', 'adam@example.com', 'h2')");
            stmt.executeUpdate("INSERT INTO users (name, email, password_hash) VALUES ('Ola', 'ola@example.com', 'h3')");
        }
    }
}
