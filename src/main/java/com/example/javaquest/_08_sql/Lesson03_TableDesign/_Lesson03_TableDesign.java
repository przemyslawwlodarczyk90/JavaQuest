package com.example.javaquest._08_sql.Lesson03_TableDesign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson03_TableDesign {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 KIEDY OSOBNA TABELA, A KIEDY ZWYKŁA KOLUMNA?
         * ============================================================
         * To jedno z pierwszych pytań przy projektowaniu schematu.
         * Ogólna zasada:
         *
         * - ZWYKŁA KOLUMNA, gdy dana wartość:
         *   - jest PROSTA i ATOMOWA (jedna wartość, np. imię, cena),
         *   - zawsze WYSTĘPUJE DOKŁADNIE RAZ dla danego wiersza,
         *   - nie ma sensu jej używać samodzielnie / odpytywać osobno.
         *
         * - OSOBNA TABELA, gdy dana wartość:
         *   - może WYSTĘPOWAĆ WIELOKROTNIE dla tego samego "rodzica"
         *     (np. użytkownik może mieć KILKA adresów dostawy),
         *   - ma WŁASNE, sensowne cechy i tożsamość (np. produkt ma
         *     nazwę, cenę, kategorię – to więcej niż jedna wartość),
         *   - jest WSPÓŁDZIELONA między wieloma rekordami (np. wiele
         *     zamówień odwołuje się do tych samych produktów – dlatego
         *     produkt to osobna tabela, a nie kolumny powtórzone
         *     w każdym zamówieniu).
         *
         * 📌 Przykład: adres dostawy jako kolumna w "users" byłby OK,
         * gdyby użytkownik miał TYLKO JEDEN adres. Ale gdy chcemy
         * pozwolić na wiele adresów (dom, praca, rodzice...) – adres
         * musi stać się OSOBNĄ tabelą "addresses" powiązaną z "users".
         */

        System.out.println("=== KOLUMNA vs OSOBNA TABELA ===");
        System.out.println("Kolumna: prosta, pojedyncza wartosc. Tabela: wiele wystapien / wlasna tozsamosc.");

        /*
         * ============================================================
         * 🔹 RODZAJE RELACJI MIĘDZY TABELAMI
         * ============================================================
         * 1) JEDEN-DO-JEDNEGO (1:1)
         *    Jeden wiersz tabeli A odpowiada DOKŁADNIE JEDNEMU wierszowi
         *    tabeli B (i na odwrót). Rzadsze niż 1:N – zwykle stosowane,
         *    gdy chcemy oddzielić rzadko używane / wrażliwe / opcjonalne
         *    dane od głównej tabeli (np. users <-> user_profiles).
         *    Technicznie: klucz obcy w tabeli B jest jednocześnie UNIKALNY
         *    (UNIQUE) – to właśnie wymusza "co najwyżej jeden" po stronie B.
         *
         * 2) JEDEN-DO-WIELU (1:N) – najczęstsza relacja
         *    Jeden wiersz tabeli A może mieć WIELE powiązanych wierszy
         *    w tabeli B, ale każdy wiersz B wskazuje na JEDNEGO rodzica A.
         *    Przykład: jeden użytkownik (users) może mieć WIELE zamówień
         *    (orders), ale każde zamówienie należy do JEDNEGO użytkownika.
         *    Technicznie: zwykły klucz obcy (bez UNIQUE) w tabeli "wielu".
         *
         * 3) WIELE-DO-WIELU (M:N)
         *    Wiele wierszy tabeli A może być powiązanych z wieloma
         *    wierszami tabeli B (i na odwrót). Przykład: jedno zamówienie
         *    może zawierać WIELE produktów, a jeden produkt może
         *    występować w WIELU zamówieniach.
         *    Relacji M:N NIE da się zapisać jedną kolumną klucza obcego
         *    po żadnej ze stron – potrzebna jest TABELA POŚREDNIA
         *    (junction table / linking table).
         *
         * 🔍 TABELA POŚREDNIA
         * To dodatkowa tabela, która ma DWA klucze obce – po jednym do
         * każdej z łączonych tabel. Każdy wiersz tabeli pośredniej to
         * "jedno konkretne powiązanie" (np. "ten produkt w tym
         * zamówieniu"). Często ma też własne dodatkowe kolumny (np.
         * "quantity" – ile sztuk produktu w danym zamówieniu).
         */

        System.out.println("\n=== RELACJE: 1:1, 1:N, M:N ===");
        System.out.println("1:1 - users <-> user_profiles (klucz obcy + UNIQUE)");
        System.out.println("1:N - users -> orders (zwykly klucz obcy)");
        System.out.println("M:N - orders <-> products, przez tabele posrednia orders_products");

        /*
         * ============================================================
         * 🔹 DOBRE NAZEWNICTWO TABEL I KOLUMN
         * ============================================================
         * Powszechnie przyjęte konwencje (i te stosujemy w tym kursie):
         *
         * - NAZWY TABEL: LICZBA MNOGA, snake_case, małe litery.
         *   Tabela przechowuje ZBIÓR wielu rekordów, więc "users",
         *   "orders", "products" – a NIE "user", "order", "product".
         *
         * - NAZWY KOLUMN: snake_case, małe litery, liczba pojedyncza
         *   (kolumna opisuje JEDNĄ cechę wiersza), np. "name", "email",
         *   "order_date".
         *
         * - KLUCZE OBCE: nazwa kolumny to zwykle nazwa tabeli docelowej
         *   w liczbie POJEDYNCZEJ + sufiks "_id", np. kolumna wskazująca
         *   na tabelę "users" nazywa się "user_id", na "products" ->
         *   "product_id".
         *
         * - KLUCZ GŁÓWNY: najczęściej po prostu "id" (proste, spójne
         *   w każdej tabeli, zawsze wiadomo czego szukać).
         *
         * Konsekwentne nazewnictwo znacznie ułatwia czytanie zapytań SQL
         * (zwłaszcza z JOIN-ami – patrz Lesson14) i pracę całego zespołu.
         */

        System.out.println("\n=== KONWENCJA NAZEWNICTWA ===");
        System.out.println("Tabele: liczba mnoga, snake_case (users, order_items).");
        System.out.println("Kolumny: liczba pojedyncza, snake_case, klucze obce z sufiksem _id (user_id).");

        /*
         * ============================================================
         * 🔍 BUDUJEMY SCHEMAT: users, addresses (1:N), orders,
         * products, categories, orders_products (M:N, tabela pośrednia)
         * ============================================================
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson03;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);

            // 1:N - jeden user moze miec wiele adresow
            stmt.execute("""
                    CREATE TABLE addresses (
                        id INT PRIMARY KEY,
                        user_id INT NOT NULL,
                        city VARCHAR(100) NOT NULL,
                        street VARCHAR(100) NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id)
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE categories (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);

            // 1:N - jedna kategoria moze miec wiele produktow
            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        category_id INT NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (category_id) REFERENCES categories(id)
                    )
                    """);

            // 1:N - jeden user moze miec wiele zamowien
            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id)
                    )
                    """);

            // M:N - tabela posrednia: jedno zamowienie <-> wiele produktow
            stmt.execute("""
                    CREATE TABLE orders_products (
                        order_id INT NOT NULL,
                        product_id INT NOT NULL,
                        quantity INT NOT NULL,
                        PRIMARY KEY (order_id, product_id),
                        FOREIGN KEY (order_id) REFERENCES orders(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
                    )
                    """);

            System.out.println("\n=== SCHEMAT UTWORZONY ===");
            System.out.println("users -addresses(1:N), categories -products(1:N), orders<->products(M:N przez orders_products)");

            // Dane przykładowe
            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski')");

            stmt.execute("INSERT INTO addresses VALUES (1, 1, 'Warszawa', 'Marszalkowska 1')");
            stmt.execute("INSERT INTO addresses VALUES (2, 1, 'Krakow', 'Rynek Glowny 5')");

            stmt.execute("INSERT INTO categories VALUES (1, 'Elektronika')");

            stmt.execute("INSERT INTO products VALUES (1, 1, 'Klawiatura', 199.99)");
            stmt.execute("INSERT INTO products VALUES (2, 1, 'Mysz', 89.50)");

            stmt.execute("INSERT INTO orders VALUES (1, 1)");

            stmt.execute("INSERT INTO orders_products VALUES (1, 1, 1)"); // 1x klawiatura
            stmt.execute("INSERT INTO orders_products VALUES (1, 2, 2)"); // 2x mysz

            System.out.println("\n=== Adresy Jana Kowalskiego (1:N) ===");
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT city, street FROM addresses WHERE user_id = 1")) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("city") + ", " + rs.getString("street"));
                }
            }
            // - Warszawa, Marszalkowska 1
            // - Krakow, Rynek Glowny 5

            System.out.println("\n=== Produkty w zamowieniu 1 (M:N przez orders_products) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT p.name, op.quantity
                    FROM orders_products op
                    JOIN products p ON p.id = op.product_id
                    WHERE op.order_id = 1
                    """)) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("name") + " x" + rs.getInt("quantity"));
                }
            }
            // - Klawiatura x1
            // - Mysz x2
            // (JOIN poznamy dokladnie w Lesson14_Joins - tu tylko podglad rezultatu relacji M:N)
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zwykła kolumna: prosta, atomowa wartość, występująca raz.
         * - Osobna tabela: wielokrotność, własna tożsamość, współdzielenie.
         * - 1:1 - klucz obcy + UNIQUE (co najwyżej jeden powiązany wiersz).
         * - 1:N - zwykły klucz obcy po stronie "wielu" (najczęstsza relacja).
         * - M:N - wymaga tabeli pośredniej z dwoma kluczami obcymi.
         * - Konwencja nazewnictwa: tabele w liczbie mnogiej (users),
         *   kolumny w liczbie pojedynczej snake_case (order_date),
         *   klucze obce z sufiksem _id (user_id, product_id).
         */
    }
}
