package com.example.javaquest._08_sql.Lesson06_DataConstraints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson06_DataConstraints {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CO TO SĄ OGRANICZENIA (CONSTRAINTS)?
         * ============================================================
         * OGRANICZENIA to reguły zdefiniowane NA POZIOMIE TABELI, które
         * baza danych SAMA wymusza przy każdym INSERT/UPDATE - niezależnie
         * od tego, jaka aplikacja (i w jakim języku) się z nią łączy.
         * To fundament integralności danych (patrz Lesson02).
         *
         * Główne rodzaje, które poznamy w tej lekcji:
         * - PRIMARY KEY    - jednoznaczny identyfikator wiersza.
         * - UNIQUE         - wartość musi być unikalna (ale MOŻE być NULL).
         * - NOT NULL       - wartość nie może być NULL.
         * - CHECK          - dowolny warunek logiczny na wartości.
         * - DEFAULT        - wartość domyślna, gdy nie podano jawnie.
         * - FOREIGN KEY    - odwołanie do innej tabeli + reguły ON DELETE/
         *                    ON UPDATE (co zrobić z dziećmi, gdy rodzic
         *                    zniknie/zmieni klucz).
         */

        System.out.println("=== OGRANICZENIA (CONSTRAINTS) - PRZEGLAD ===");
        System.out.println("PRIMARY KEY, UNIQUE, NOT NULL, CHECK, DEFAULT, FOREIGN KEY (+ ON DELETE/UPDATE)");

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson06;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            /*
             * ============================================================
             * 🔹 PRIMARY KEY, UNIQUE, NOT NULL, CHECK, DEFAULT
             * ============================================================
             */

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        age INT CHECK (age >= 0 AND age <= 150),
                        status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL
                    )
                    """);
            // - id: PRIMARY KEY (unikalny + NOT NULL automatycznie)
            // - email: NOT NULL (wymagany) + UNIQUE (nie moze sie powtorzyc)
            // - age: CHECK - dopuszczalny tylko sensowny zakres wieku
            // - status: DEFAULT 'ACTIVE' - gdy nie podamy wartosci, uzyje domyslnej

            System.out.println("\n=== TABELA users Z OGRANICZENIAMI UTWORZONA ===");

            stmt.execute("INSERT INTO users (id, email, age) VALUES (1, 'jan@example.com', 30)");
            // status nie podany -> uzyje DEFAULT 'ACTIVE'

            System.out.println("\n=== DEFAULT w akcji ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = 1")) {
                if (rs.next()) {
                    System.out.println("email=" + rs.getString("email") + ", status=" + rs.getString("status"));
                }
            }
            // email=jan@example.com, status=ACTIVE

            System.out.println("\n=== PROBA DUPLIKATU UNIQUE (email) ===");
            try {
                stmt.execute("INSERT INTO users (id, email, age) VALUES (2, 'jan@example.com', 25)");
                System.out.println("To sie nie powinno wykonac!");
            } catch (SQLException e) {
                System.out.println("Odrzucono - naruszenie UNIQUE: " + e.getMessage());
            }

            System.out.println("\n=== PROBA NULL W KOLUMNIE NOT NULL (email) ===");
            try {
                stmt.execute("INSERT INTO users (id, email, age) VALUES (3, NULL, 40)");
                System.out.println("To sie nie powinno wykonac!");
            } catch (SQLException e) {
                System.out.println("Odrzucono - naruszenie NOT NULL: " + e.getMessage());
            }

            System.out.println("\n=== PROBA NARUSZENIA CHECK (age = 200) ===");
            try {
                stmt.execute("INSERT INTO users (id, email, age) VALUES (4, 'ktos@example.com', 200)");
                System.out.println("To sie nie powinno wykonac!");
            } catch (SQLException e) {
                System.out.println("Odrzucono - naruszenie CHECK: " + e.getMessage());
            }

            /*
             * ============================================================
             * 🔍 ON DELETE CASCADE - usunięcie rodzica kasuje dzieci
             * ============================================================
             * ON DELETE CASCADE oznacza: gdy usuniemy wiersz z tabeli
             * "rodzica", baza AUTOMATYCZNIE usunie wszystkie powiązane
             * wiersze "dzieci" (te, które wskazywały na niego kluczem
             * obcym). Przydatne np. dla danych, które nie mają sensu bez
             * rodzica (np. pozycje zamówienia bez samego zamówienia).
             */

            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                    )
                    """);

            stmt.execute("INSERT INTO orders VALUES (100, 1)");
            stmt.execute("INSERT INTO orders VALUES (101, 1)");

            System.out.println("\n=== PRZED USUNIECIEM: zamowienia usera 1 ===");
            try (ResultSet rs = stmt.executeQuery("SELECT id FROM orders WHERE user_id = 1")) {
                while (rs.next()) {
                    System.out.println(" - zamowienie id=" + rs.getInt("id"));
                }
            }
            // - zamowienie id=100
            // - zamowienie id=101

            stmt.execute("DELETE FROM users WHERE id = 1");

            System.out.println("\n=== PO USUNIECIU usera 1 (ON DELETE CASCADE) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM orders WHERE user_id = 1")) {
                if (rs.next()) {
                    System.out.println("Liczba pozostalych zamowien usera 1: " + rs.getInt("cnt"));
                }
            }
            // Liczba pozostalych zamowien usera 1: 0  <- kaskadowo usuniete razem z userem!

            /*
             * ============================================================
             * 🔍 ON DELETE SET NULL - usunięcie rodzica "odpina" dzieci
             * ============================================================
             * ON DELETE SET NULL oznacza: gdy usuniemy rodzica, powiązana
             * kolumna klucza obcego w dzieciach zostanie ustawiona na
             * NULL (dziecko PRZETRWA, ale "traci" powiązanie z rodzicem).
             * Wymaga, żeby kolumna klucza obcego DOPUSZCZAŁA NULL
             * (czyli NIE była NOT NULL).
             *
             * Przykład: recenzja produktu, której autor (user) usunął
             * konto - recenzja może zostać (jako "użytkownik usunięty"),
             * zamiast znikać razem z kontem.
             */

            stmt.execute("INSERT INTO users (id, email, age) VALUES (5, 'anna@example.com', 28)");

            stmt.execute("""
                    CREATE TABLE reviews (
                        id INT PRIMARY KEY,
                        user_id INT,
                        content VARCHAR(200) NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
                    )
                    """);
            // user_id celowo BEZ NOT NULL - musi umozliwiac SET NULL

            stmt.execute("INSERT INTO reviews VALUES (1, 5, 'Swietny produkt!')");

            System.out.println("\n=== PRZED USUNIECIEM: recenzja usera 5 ===");
            try (ResultSet rs = stmt.executeQuery("SELECT user_id, content FROM reviews WHERE id = 1")) {
                if (rs.next()) {
                    System.out.println("user_id=" + rs.getInt("user_id") + ", content=" + rs.getString("content"));
                }
            }
            // user_id=5, content=Swietny produkt!

            stmt.execute("DELETE FROM users WHERE id = 5");

            System.out.println("\n=== PO USUNIECIU usera 5 (ON DELETE SET NULL) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT user_id, content FROM reviews WHERE id = 1")) {
                if (rs.next()) {
                    Object userId = rs.getObject("user_id"); // getObject - zeby zobaczyc prawdziwy null (patrz Lesson05)
                    System.out.println("user_id=" + userId + ", content=" + rs.getString("content"));
                }
            }
            // user_id=null, content=Swietny produkt!  <- recenzja PRZETRWALA, ale straciala powiazanie
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PRIMARY KEY - jednoznaczny identyfikator (unikalny, NOT NULL).
         * - UNIQUE - wartość musi być unikalna (ale może być NULL).
         * - NOT NULL - zabrania NULL w kolumnie.
         * - CHECK - dowolny warunek logiczny na wartości wiersza.
         * - DEFAULT - wartość domyślna, gdy nie podano jawnie.
         * - FOREIGN KEY + ON DELETE CASCADE - usunięcie rodzica kasuje
         *   powiązane dzieci.
         * - FOREIGN KEY + ON DELETE SET NULL - usunięcie rodzica "odpina"
         *   dzieci (ustawia klucz obcy na NULL), dzieci przetrwają.
         * - Naruszenie dowolnego ograniczenia kończy się SQLException -
         *   to baza, nie kod Javy, jest ostatnią linią obrony integralności.
         */
    }
}
