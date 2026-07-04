package com.example.javaquest._08_sql.Lesson15_SqlRelationships;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson15_SqlRelationships {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 RELACJE MIĘDZY TABELAMI
         * ============================================================
         * Baza relacyjna nazywa się "relacyjna" właśnie dlatego, że
         * tabele mogą być ze sobą POWIĄZANE (relacja = związek).
         * Powiązanie realizujemy przez KLUCZ OBCY (FOREIGN KEY) –
         * kolumnę, która wskazuje na klucz główny (PRIMARY KEY) innej
         * (albo tej samej) tabeli.
         *
         * Rozróżniamy 4 rodzaje relacji:
         * - JEDEN DO JEDNEGO (1:1)
         * - JEDEN DO WIELU (1:N)
         * - WIELE DO JEDNEGO (N:1) – to ta sama relacja co 1:N, tylko
         *   patrzymy na nią z drugiej strony
         * - WIELE DO WIELU (N:M) – wymaga dodatkowej TABELI POŚREDNIEJ
         *
         * 🔍 INTEGRALNOŚĆ REFERENCYJNA (referential integrity)
         * To gwarancja bazy danych, że klucz obcy zawsze wskazuje na
         * ISTNIEJĄCY wiersz (albo NULL, jeśli kolumna na to pozwala).
         * Bez tego moglibyśmy mieć np. zamówienie przypisane do
         * nieistniejącego użytkownika – "dane osierocone" (orphan data).
         * Bazę pilnuje tego sama, gdy zdefiniujemy FOREIGN KEY –
         * odrzuci próbę wstawienia "złego" wskazania.
         */

        String url = "jdbc:h2:mem:lesson15;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            /*
             * ============================================================
             * 🔹 RELACJA 1:1 (JEDEN DO JEDNEGO)
             * ============================================================
             * Każdy wiersz w tabeli A odpowiada DOKŁADNIE JEDNEMU
             * wierszowi w tabeli B (i na odwrót). Typowy przykład:
             * użytkownik i jego profil (dane rzadziej używane,
             * wydzielone do osobnej tabeli).
             *
             * Sposób implementacji: klucz obcy w tabeli "podrzędnej"
             * (user_profiles) jest jednocześnie jej kluczem głównym
             * (PRIMARY KEY = FOREIGN KEY) – to gwarantuje, że jeden
             * użytkownik może mieć NAJWYŻEJ JEDEN profil.
             */

            System.out.println("=== RELACJA 1:1 – users <-> user_profiles ===");
            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE user_profiles (
                        user_id INT PRIMARY KEY REFERENCES users(id),
                        bio VARCHAR(255)
                    )
                    """);

            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski')");
            stmt.execute("INSERT INTO users VALUES (2, 'Anna Nowak')");
            stmt.execute("INSERT INTO user_profiles VALUES (1, 'Lubie Jave i kawe')");
            stmt.execute("INSERT INTO user_profiles VALUES (2, 'Fanka ksiazek SF')");

            try (ResultSet rs = stmt.executeQuery("""
                    SELECT u.name, p.bio
                    FROM users u
                    JOIN user_profiles p ON p.user_id = u.id
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getString("name") + " -> " + rs.getString("bio"));
                }
            }

            /*
             * ============================================================
             * 🔹 RELACJA 1:N (JEDEN DO WIELU) i N:1 (WIELE DO JEDNEGO)
             * ============================================================
             * To NAJCZĘSTSZA relacja w bazach danych. Jeden autor może
             * napisać WIELE książek, ale każda książka ma DOKŁADNIE
             * JEDNEGO autora (w tym uproszczonym modelu).
             *
             * - Z perspektywy authors -> books: to relacja 1:N
             *   (jeden autor, wiele książek)
             * - Z perspektywy books -> authors: to DOKŁADNIE TA SAMA
             *   relacja, tylko widziana odwrotnie: N:1 (wiele książek,
             *   jeden autor)
             *
             * Implementacja: kolumna author_id w tabeli "wielu" (books)
             * jest kluczem obcym wskazującym na klucz główny tabeli
             * "jeden" (authors). Klucz obcy NIE musi być unikalny –
             * wiele wierszy books może mieć ten sam author_id.
             */

            System.out.println("\n=== RELACJA 1:N / N:1 – authors -> books ===");
            stmt.execute("""
                    CREATE TABLE authors (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE books (
                        id INT PRIMARY KEY,
                        title VARCHAR(150) NOT NULL,
                        author_id INT REFERENCES authors(id)
                    )
                    """);

            stmt.execute("INSERT INTO authors VALUES (1, 'Andrzej Sapkowski')");
            stmt.execute("INSERT INTO authors VALUES (2, 'Stanislaw Lem')");
            stmt.execute("INSERT INTO books VALUES (1, 'Wiedzmin', 1)");
            stmt.execute("INSERT INTO books VALUES (2, 'Miecz przeznaczenia', 1)");
            stmt.execute("INSERT INTO books VALUES (3, 'Solaris', 2)");

            try (ResultSet rs = stmt.executeQuery("""
                    SELECT a.name AS autor, b.title AS ksiazka
                    FROM authors a
                    JOIN books b ON b.author_id = a.id
                    ORDER BY a.name, b.title
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getString("autor") + " -> " + rs.getString("ksiazka"));
                }
            }

            // 🔍 Integralnosc referencyjna w akcji: probujemy dodac ksiazke
            // z nieistniejacym author_id (999) - baza to odrzuci.
            System.out.println("\n=== TEST INTEGRALNOSCI REFERENCYJNEJ ===");
            try {
                stmt.execute("INSERT INTO books VALUES (4, 'Ksiazka widmo', 999)");
                System.out.println("Wstawiono - to NIE powinno sie zdarzyc!");
            } catch (SQLException e) {
                System.out.println("Baza odrzucila wstawienie (jak powinna): " + e.getMessage());
            }

            /*
             * ============================================================
             * 🔹 RELACJA N:M (WIELE DO WIELU) – TABELA POŚREDNIA
             * ============================================================
             * Przykład: produkt może należeć do WIELU kategorii,
             * a kategoria może zawierać WIELE produktów (np. "Laptop"
             * pasuje jednocześnie do kategorii "Elektronika" i
             * "Praca zdalna").
             *
             * Relacyjna baza danych NIE POZWALA bezpośrednio zapisać
             * takiej relacji jedną kolumną klucza obcego – potrzebna
             * jest TABELA POŚREDNIA (junction table / tabela łącząca),
             * która przechowuje PARY kluczy obcych: (product_id,
             * category_id). Klucz główny tabeli pośredniej to zwykle
             * KLUCZ ZŁOŻONY (composite key) z obu tych kolumn – to on
             * gwarantuje, że ta sama para nie powtórzy się dwukrotnie.
             */

            System.out.println("\n=== RELACJA N:M – products <-> categories (products_categories) ===");
            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE categories (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE products_categories (
                        product_id INT REFERENCES products(id),
                        category_id INT REFERENCES categories(id),
                        PRIMARY KEY (product_id, category_id)
                    )
                    """);

            stmt.execute("INSERT INTO products VALUES (1, 'Laptop')");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz bezprzewodowa')");
            stmt.execute("INSERT INTO categories VALUES (1, 'Elektronika')");
            stmt.execute("INSERT INTO categories VALUES (2, 'Praca zdalna')");
            stmt.execute("INSERT INTO categories VALUES (3, 'Akcesoria')");

            // Laptop nalezy do DWOCH kategorii, mysz do dwoch innych - to
            // wlasnie pokazuje sens relacji N:M.
            stmt.execute("INSERT INTO products_categories VALUES (1, 1)"); // Laptop - Elektronika
            stmt.execute("INSERT INTO products_categories VALUES (1, 2)"); // Laptop - Praca zdalna
            stmt.execute("INSERT INTO products_categories VALUES (2, 1)"); // Mysz - Elektronika
            stmt.execute("INSERT INTO products_categories VALUES (2, 3)"); // Mysz - Akcesoria

            try (ResultSet rs = stmt.executeQuery("""
                    SELECT p.name AS produkt, c.name AS kategoria
                    FROM products p
                    JOIN products_categories pc ON pc.product_id = p.id
                    JOIN categories c ON c.id = pc.category_id
                    ORDER BY p.name, c.name
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getString("produkt") + " -> " + rs.getString("kategoria"));
                }
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - 1:1 (jeden do jednego) -> klucz obcy jest jednocześnie
         *   kluczem głównym tabeli podrzędnej (np. users <-> user_profiles)
         * - 1:N (jeden do wielu) / N:1 (wiele do jednego) -> klucz obcy
         *   w tabeli "po stronie wielu" wskazuje na tabelę "po stronie
         *   jeden" (np. authors -> books); to ta sama relacja widziana
         *   z dwóch stron
         * - N:M (wiele do wielu) -> wymaga TABELI POŚREDNIEJ z dwoma
         *   kluczami obcymi (np. products_categories), zwykle z kluczem
         *   złożonym z obu kolumn
         * - Klucz obcy (FOREIGN KEY) wymusza INTEGRALNOŚĆ REFERENCYJNĄ –
         *   baza nie pozwoli wstawić wiersza wskazującego na
         *   nieistniejący rekord w tabeli nadrzędnej
         */
    }
}
