package com.example.javaquest._08_sql.Lesson02_RelationalModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson02_RelationalModel {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PODSTAWOWE POJĘCIA MODELU RELACYJNEGO
         * ============================================================
         * TABELA (table) – podstawowy "pojemnik" na dane w bazie
         * relacyjnej. Ma nazwę (np. "users") i sztywno zdefiniowaną
         * strukturę kolumn.
         *
         * KOLUMNA (column) – pojedyncza "cecha" przechowywanych danych,
         * np. "name", "email". Każda kolumna ma nazwę i TYP DANYCH
         * (patrz Lesson04) – wszystkie wartości w danej kolumnie muszą
         * być tego samego typu.
         *
         * WIERSZ / REKORD (row / record) – pojedynczy "egzemplarz" danych
         * w tabeli, np. jeden konkretny użytkownik. Wiersz to komplet
         * wartości dla wszystkich kolumn tabeli.
         *
         * Możesz sobie wyobrazić tabelę jak arkusz kalkulacyjny:
         * kolumny = nagłówki kolumn arkusza, wiersze = kolejne wiersze
         * danych.
         *
         * RELACJA (relation) – w teorii baz danych to synonim "tabeli"
         * (stąd nazwa "baza RELACYJNA"). W praktyce mówiąc "relacja"
         * najczęściej mamy jednak na myśli POWIĄZANIE między dwiema
         * tabelami (np. "zamówienie jest powiązane z użytkownikiem") –
         * tego znaczenia użyjemy w dalszej części lekcji.
         */

        System.out.println("=== TABELA, KOLUMNA, WIERSZ ===");
        System.out.println("Tabela = 'arkusz' o ustalonych kolumnach; wiersz = jeden rekord danych.");

        /*
         * ============================================================
         * 🔹 KLUCZ GŁÓWNY (PRIMARY KEY)
         * ============================================================
         * KLUCZ GŁÓWNY to kolumna (lub zestaw kolumn), która JEDNOZNACZNIE
         * IDENTYFIKUJE wiersz w tabeli. Baza danych sama pilnuje, żeby:
         * - wartość klucza głównego NIGDY się nie powtórzyła (unikalność),
         * - wartość klucza głównego NIGDY nie była NULL.
         *
         * Najczęstszy wzorzec: kolumna "id" typu liczbowego, generowana
         * automatycznie (AUTO_INCREMENT / IDENTITY / sekwencja). Dzięki
         * kluczowi głównemu inne tabele mogą się do wiersza ODWOŁAĆ
         * (patrz niżej: klucz obcy), zamiast kopiować całe jego dane.
         *
         * 🔹 KLUCZ OBCY (FOREIGN KEY)
         * KLUCZ OBCY to kolumna w jednej tabeli, która przechowuje
         * wartość klucza głównego z INNEJ (lub tej samej) tabeli.
         * Dzięki temu tabele są ze sobą POWIĄZANE (relacja) – np. tabela
         * "orders" (zamówienia) ma kolumnę "user_id", która wskazuje
         * na konkretny wiersz w tabeli "users".
         *
         * Baza danych pilnuje INTEGRALNOŚCI REFERENCYJNEJ: nie pozwoli
         * wstawić zamówienia z user_id, które nie istnieje w tabeli
         * users (patrz próba niżej – zakończy się błędem).
         *
         * 🔍 INDEKS (index) – w wielkim skrócie
         * INDEKS to dodatkowa struktura danych, która przyspiesza
         * WYSZUKIWANIE wierszy po danej kolumnie (podobnie jak indeks
         * na końcu książki przyspiesza znalezienie strony z danym
         * hasłem, zamiast czytać książkę od deski do deski).
         * Klucz główny ma indeks TWORZONY AUTOMATYCZNIE. Więcej o
         * indeksach – w dedykowanej lekcji (Lesson18_Indexes).
         *
         * 🔹 OGRANICZENIA DANYCH I INTEGRALNOŚĆ DANYCH
         * OGRANICZENIA (constraints) to reguły, które baza danych
         * WYMUSZA na wstawianych/aktualizowanych danych – np. PRIMARY
         * KEY (unikalność + NOT NULL), FOREIGN KEY (odwołanie musi
         * istnieć), NOT NULL, UNIQUE, CHECK (patrz Lesson06).
         *
         * INTEGRALNOŚĆ DANYCH (data integrity) to ogólna idea: dane
         * w bazie są zawsze SPÓJNE i POPRAWNE względem zdefiniowanych
         * reguł – to właśnie ograniczenia (constraints) są narzędziem,
         * którym baza tę integralność wymusza. Dzięki temu logika
         * poprawności danych nie musi być w 100% pilnowana w kodzie
         * Javy – część odpowiedzialności "przejmuje" sama baza.
         */

        System.out.println("\n=== KLUCZE I INTEGRALNOSC ===");
        System.out.println("PRIMARY KEY = jednoznaczny identyfikator wiersza (unikalny, NOT NULL).");
        System.out.println("FOREIGN KEY = kolumna wskazujaca na PRIMARY KEY innej tabeli (powiazanie).");
        System.out.println("Ograniczenia (constraints) wymuszaja integralnosc danych w samej bazie.");

        /*
         * ============================================================
         * 🔍 MINI-SCHEMAT: users, products, orders, order_items
         * ============================================================
         * Zbudujemy realny, mały schemat sklepu internetowego:
         *
         *   users            products           orders                order_items
         *   ------           --------           --------              -----------
         *   id (PK)          id (PK)            id (PK)               id (PK)
         *   name             name               user_id (FK -> users) order_id   (FK -> orders)
         *   email            price              order_date            product_id (FK -> products)
         *                                                              quantity
         *
         * "orders" odwołuje się do "users" (kto złożył zamówienie).
         * "order_items" odwołuje się i do "orders", i do "products"
         * (co i w jakiej ilości znalazło się w danym zamówieniu) –
         * to typowy wzorzec relacji wiele-do-wielu przez tabelę
         * pośrednią, który dokładniej omówimy w Lesson03_TableDesign.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson02;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT NOT NULL,
                        order_date DATE NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id)
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE order_items (
                        id INT PRIMARY KEY,
                        order_id INT NOT NULL,
                        product_id INT NOT NULL,
                        quantity INT NOT NULL,
                        FOREIGN KEY (order_id) REFERENCES orders(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
                    )
                    """);

            System.out.println("\n=== SCHEMAT UTWORZONY ===");
            System.out.println("Tabele: users, products, orders, order_items - utworzone z kluczami PK/FK.");

            // Dane przykładowe
            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski', 'jan@example.com')");
            stmt.execute("INSERT INTO users VALUES (2, 'Anna Nowak', 'anna@example.com')");

            stmt.execute("INSERT INTO products VALUES (1, 'Klawiatura', 199.99)");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz', 89.50)");

            stmt.execute("INSERT INTO orders VALUES (1, 1, '2026-01-10')");
            stmt.execute("INSERT INTO orders VALUES (2, 2, '2026-02-05')");

            stmt.execute("INSERT INTO order_items VALUES (1, 1, 1, 1)"); // zamowienie 1: 1x klawiatura
            stmt.execute("INSERT INTO order_items VALUES (2, 1, 2, 2)"); // zamowienie 1: 2x mysz
            stmt.execute("INSERT INTO order_items VALUES (3, 2, 2, 1)"); // zamowienie 2: 1x mysz

            System.out.println("\n=== SELECT * FROM users ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getString("email"));
                }
            }
            // 1 - Jan Kowalski - jan@example.com
            // 2 - Anna Nowak - anna@example.com

            System.out.println("\n=== SELECT * FROM products ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getBigDecimal("price"));
                }
            }
            // 1 - Klawiatura - 199.99
            // 2 - Mysz - 89.50

            System.out.println("\n=== SELECT * FROM orders ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " - user_id=" + rs.getInt("user_id") + " - " + rs.getDate("order_date"));
                }
            }
            // 1 - user_id=1 - 2026-01-10
            // 2 - user_id=2 - 2026-02-05

            System.out.println("\n=== SELECT * FROM order_items ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM order_items")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " - order_id=" + rs.getInt("order_id")
                            + " - product_id=" + rs.getInt("product_id") + " - quantity=" + rs.getInt("quantity"));
                }
            }
            // 1 - order_id=1 - product_id=1 - quantity=1
            // 2 - order_id=1 - product_id=2 - quantity=2
            // 3 - order_id=2 - product_id=2 - quantity=1

            /*
             * ============================================================
             * 🔍 INTEGRALNOŚĆ REFERENCYJNA W AKCJI
             * ============================================================
             * Spróbujmy wstawić zamówienie z user_id, który NIE ISTNIEJE
             * w tabeli users. Baza danych to ODRZUCI – to właśnie klucz
             * obcy (FOREIGN KEY) pilnuje integralności danych.
             */

            System.out.println("\n=== PROBA NARUSZENIA INTEGRALNOSCI (FOREIGN KEY) ===");
            try {
                stmt.execute("INSERT INTO orders VALUES (99, 999, '2026-03-01')"); // user_id=999 nie istnieje!
                System.out.println("To się nie powinno wykonać!");
            } catch (SQLException e) {
                System.out.println("Odrzucono przez baze (integralnosc referencyjna): " + e.getMessage());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Tabela = kolumny (cechy) + wiersze (rekordy danych).
         * - Klucz główny (PRIMARY KEY) jednoznacznie identyfikuje wiersz
         *   (unikalny, nigdy NULL).
         * - Klucz obcy (FOREIGN KEY) łączy wiersz jednej tabeli z wierszem
         *   innej tabeli (powiązanie/relacja) i wymusza integralność
         *   referencyjną (nie można wskazać na nieistniejący wiersz).
         * - Indeks przyspiesza wyszukiwanie po kolumnie (szczegóły:
         *   Lesson18_Indexes).
         * - Ograniczenia (constraints) to reguły wymuszane przez bazę,
         *   które razem zapewniają integralność danych.
         * - Mini-schemat sklepu: users -> orders -> order_items <- products
         *   pokazuje typowe powiązania PK/FK w praktyce.
         */
    }
}
