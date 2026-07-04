package com.example.javaquest._08_sql.Lesson18_Indexes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson18_Indexes {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST INDEKS?
         * ============================================================
         * INDEKS to dodatkowa struktura danych (najczęściej drzewo
         * B-tree), którą baza buduje NA WYBRANEJ KOLUMNIE (lub kilku
         * kolumnach), żeby szybciej ZNAJDOWAĆ wiersze - bez indeksu
         * baza musi przejrzeć KAŻDY wiersz tabeli po kolei (tzw. "table
         * scan" / pełne przeszukanie tabeli), żeby sprawdzić, czy
         * spełnia warunek WHERE.
         *
         * 🔍 ANALOGIA
         * Indeks w bazie danych to jak indeks (skorowidz) na końcu
         * grubej książki: zamiast czytać całą książkę strona po
         * stronie w poszukiwaniu słowa, zaglądasz do skorowidza i od
         * razu wiesz, na której stronie szukać.
         *
         * 🔹 PO CO INDEKS?
         * Przyspiesza:
         * - SELECT z warunkiem WHERE na zaindeksowanej kolumnie
         * - JOIN (baza szybciej znajduje pasujące wiersze po obu
         *   stronach złączenia)
         * - ORDER BY / GROUP BY na zaindeksowanej kolumnie (dane są
         *   już w indeksie w posortowanej kolejności)
         *
         * Kosztem:
         * - DODATKOWEGO MIEJSCA na dysku (indeks to osobna struktura,
         *   trzymana obok danych tabeli)
         * - SPOWOLNIENIA INSERT/UPDATE/DELETE - każda zmiana danych w
         *   zaindeksowanej kolumnie wymaga też zaktualizowania indeksu
         *
         * 📌 WNIOSEK: indeksy warto zakładać na kolumnach, po których
         * CZĘSTO filtrujemy/sortujemy/łączymy tabele (np. klucze obce),
         * a NIE na każdej kolumnie "na wszelki wypadek" - w tabeli z
         * dużą liczbą zapisów (INSERT/UPDATE) nadmiar indeksów może
         * wyraźnie spowolnić zapisywanie danych.
         */

        String url = "jdbc:h2:mem:lesson18;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        age INT NOT NULL
                    )
                    """);

            // Wypelniamy tabele 1000 wierszami, zeby roznica byla widoczna
            // w planie zapytania (EXPLAIN).
            for (int i = 1; i <= 1000; i++) {
                stmt.execute("INSERT INTO users VALUES (" + i + ", 'user" + i + "@test.com', " + (i % 80) + ")");
            }
            System.out.println("Wstawiono 1000 wierszy do users.");

            /*
             * ============================================================
             * 🔹 PLAN ZAPYTANIA PRZED INDEKSEM
             * ============================================================
             * H2 wspiera polecenie EXPLAIN, ktore pokazuje, JAK baza
             * zamierza wykonac zapytanie - bez indeksu na "email"
             * zobaczymy adnotacje "tableScan" (pelne przeszukanie
             * tabeli wiersz po wierszu).
             */

            System.out.println("\n=== EXPLAIN SELECT ... WHERE email = ... (BEZ indeksu) ===");
            try (ResultSet rs = stmt.executeQuery(
                    "EXPLAIN SELECT * FROM users WHERE email = 'user500@test.com'")) {
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }
            }
            // W planie powinno pojawic sie: /* PUBLIC.USERS.tableScan */
            // - to znaczy, ze baza sprawdza KAZDY wiersz po kolei.

            /*
             * ============================================================
             * 🔹 INDEKS NA JEDNEJ KOLUMNIE
             * ============================================================
             */

            System.out.println("\n=== CREATE INDEX na kolumnie email ===");
            stmt.execute("CREATE INDEX idx_users_email ON users(email)");
            System.out.println("Indeks utworzony.");

            System.out.println("\n=== EXPLAIN SELECT ... WHERE email = ... (PO indeksie) ===");
            try (ResultSet rs = stmt.executeQuery(
                    "EXPLAIN SELECT * FROM users WHERE email = 'user500@test.com'")) {
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }
            }
            // Teraz w planie powinna pojawic sie nazwa naszego indeksu
            // (IDX_USERS_EMAIL) zamiast tableScan - baza uzyla indeksu,
            // zeby od razu "przeskoczyc" do pasujacego wiersza, zamiast
            // przegladac wszystkie 1000.

            /*
             * ============================================================
             * 🔹 INDEKS WIELOKOLUMNOWY (COMPOSITE INDEX)
             * ============================================================
             * Indeks może obejmować WIĘCEJ NIŻ JEDNĄ kolumnę - przydaje
             * się, gdy często filtrujemy/sortujemy po tej SAMEJ parze
             * kolumn naraz (np. wiek + email). Kolejność kolumn w
             * indeksie wielokolumnowym MA ZNACZENIE - indeks (age, email)
             * najlepiej wspiera zapytania filtrujące najpierw po age.
             */

            System.out.println("\n=== CREATE INDEX wielokolumnowy (age, email) ===");
            stmt.execute("CREATE INDEX idx_users_age_email ON users(age, email)");
            System.out.println("Indeks wielokolumnowy utworzony.");

            /*
             * ============================================================
             * 🔹 UNIQUE INDEX
             * ============================================================
             * UNIQUE INDEX to indeks, ktory DODATKOWO wymusza unikalnosc
             * wartosci w danej kolumnie (podobnie jak ograniczenie
             * UNIQUE) - baza odrzuci probe wstawienia duplikatu.
             * Roznica wzgledem zwyklego ograniczenia UNIQUE jest w
             * praktyce kosmetyczna - obie tworza indeks pod spodem.
             */

            System.out.println("\n=== CREATE UNIQUE INDEX na email ===");
            stmt.execute("CREATE UNIQUE INDEX idx_users_email_unique ON users(email)");
            System.out.println("Unikalny indeks utworzony.");

            System.out.println("\n=== Proba wstawienia duplikatu email ===");
            try {
                stmt.execute("INSERT INTO users VALUES (1001, 'user500@test.com', 30)");
                System.out.println("Wstawiono duplikat - to NIE powinno sie zdarzyc!");
            } catch (SQLException e) {
                System.out.println("Baza odrzucila duplikat (jak powinna): " + e.getMessage());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Indeks = dodatkowa struktura (B-tree) przyspieszająca
         *   wyszukiwanie wierszy po danej kolumnie
         * - Bez indeksu baza wykonuje "table scan" - sprawdza każdy
         *   wiersz po kolei (widoczne w EXPLAIN)
         * - Indeks przyspiesza: SELECT z WHERE, JOIN, ORDER BY/GROUP BY
         *   na zaindeksowanej kolumnie
         * - Indeks spowalnia: INSERT/UPDATE/DELETE (trzeba zaktualizować
         *   też indeks) i zajmuje dodatkowe miejsce na dysku
         * - Indeks wielokolumnowy (composite) wspiera zapytania po
         *   kilku kolumnach naraz - kolejność kolumn w indeksie ma
         *   znaczenie
         * - UNIQUE INDEX dodatkowo wymusza unikalność wartości, tak
         *   jak ograniczenie UNIQUE
         * - Polecenie EXPLAIN pokazuje, czy baza użyje indeksu, czy
         *   zrobi pełne przeszukanie tabeli
         */
    }
}
