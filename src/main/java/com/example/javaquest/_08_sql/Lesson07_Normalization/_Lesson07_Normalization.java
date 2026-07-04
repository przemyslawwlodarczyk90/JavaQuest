package com.example.javaquest._08_sql.Lesson07_Normalization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson07_Normalization {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PO CO NORMALIZOWAĆ DANE?
         * ============================================================
         * NORMALIZACJA to proces PROJEKTOWANIA tabel tak, by unikać
         * ZBĘDNEJ DUPLIKACJI danych i wynikających z niej problemów:
         *
         * 1) MARNOWANIE MIEJSCA - te same dane (np. nazwa produktu)
         *    powtórzone w tysiącach wierszy zamiast raz.
         *
         * 2) ANOMALIE AKTUALIZACJI (update anomaly) - gdy dana się
         *    zmieni (np. cena produktu), trzeba zaktualizować WSZYSTKIE
         *    powtórzone wystąpienia. Łatwo coś przeoczyć - część
         *    wierszy zostanie z NIEAKTUALNĄ wartością.
         *
         * 3) ANOMALIE WSTAWIANIA (insert anomaly) - żeby dodać nowy
         *    produkt, czasem trzeba "na siłę" dodać go razem z jakimś
         *    zamówieniem, bo produkt nie istnieje jako samodzielny byt.
         *
         * 4) ANOMALIE USUWANIA (delete anomaly) - usuwając JEDYNE
         *    zamówienie zawierające dany produkt, przypadkiem tracimy
         *    WSZYSTKIE informacje o tym produkcie (bo nigdzie indziej
         *    nie były zapisane).
         *
         * Rozwiązaniem jest rozbicie danych na WIĘCEJ, mniejszych,
         * dobrze zaprojektowanych tabel, połączonych kluczami obcymi
         * (patrz Lesson02, Lesson03).
         */

        System.out.println("=== PO CO NORMALIZOWAC ===");
        System.out.println("Unikanie duplikacji danych i anomalii: aktualizacji, wstawiania, usuwania.");

        /*
         * ============================================================
         * 🔹 POSTACIE NORMALNE (1NF, 2NF, 3NF) - w skrócie
         * ============================================================
         * 1NF (pierwsza postać normalna):
         * - Każda kolumna przechowuje WARTOŚĆ ATOMOWĄ (pojedynczą,
         *   niepodzielną) - NIE listy, NIE kilka wartości w jednym polu.
         *   Źle: kolumna "phones" = "123456789, 987654321" (2 numery
         *   w jednej komórce). Dobrze: osobna tabela "phones" z jednym
         *   numerem na wiersz, powiązana kluczem obcym z użytkownikiem.
         * - Brak powtarzających się grup kolumn (np. phone1, phone2,
         *   phone3 - to sygnał, że to powinna być osobna tabela).
         *
         * 2NF (druga postać normalna):
         * - Spełnia 1NF ORAZ każda kolumna niebędąca kluczem zależy od
         *   CAŁEGO klucza głównego (istotne przy kluczach ZŁOŻONYCH,
         *   z wielu kolumn). Przykład naruszenia: tabela z kluczem
         *   złożonym (order_id, product_id) ma kolumnę "product_name" -
         *   ta zależy TYLKO od product_id, nie od całego klucza -
         *   nazwa produktu powinna być w osobnej tabeli "products".
         *
         * 3NF (trzecia postać normalna):
         * - Spełnia 2NF ORAZ żadna kolumna niebędąca kluczem nie zależy
         *   od INNEJ kolumny niebędącej kluczem (tzw. zależność
         *   przechodnia). Przykład naruszenia: tabela "orders" ma
         *   kolumny "user_id" i "user_email" - email zależy od
         *   user_id, a nie bezpośrednio od klucza zamówienia - email
         *   powinien być tylko w tabeli "users", nie duplikowany
         *   w "orders".
         *
         * 📌 W praktyce: 3NF ("trzy proste tabele zamiast jednej szerokiej
         * z duplikatami") pokrywa większość codziennych potrzeb -
         * dokładnie to pokażemy w demo niżej.
         */

        System.out.println("\n=== 1NF / 2NF / 3NF w skrocie ===");
        System.out.println("1NF: wartosci atomowe, brak list w kolumnie.");
        System.out.println("2NF: kolumny zaleza od CALEGO klucza (przy kluczach zlozonych).");
        System.out.println("3NF: kolumny zaleza TYLKO od klucza, nie od innych kolumn (brak zaleznosci przechodnich).");

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson07;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            /*
             * ============================================================
             * 🔍 ZŁY PROJEKT (brak normalizacji) - duplikacja danych
             * ============================================================
             */

            stmt.execute("""
                    CREATE TABLE orders_bad (
                        id INT PRIMARY KEY,
                        user_name VARCHAR(100) NOT NULL,
                        user_email VARCHAR(100) NOT NULL,
                        product_name VARCHAR(100) NOT NULL,
                        product_price DECIMAL(10, 2) NOT NULL
                    )
                    """);

            System.out.println("\n=== ZLY PROJEKT: orders_bad (duplikacja danych) ===");

            // Jan Kowalski sklada 3 zamowienia - jego dane i dane produktow
            // powtarzaja sie w KAZDYM wierszu!
            stmt.execute("INSERT INTO orders_bad VALUES (1, 'Jan Kowalski', 'jan@example.com', 'Klawiatura', 199.99)");
            stmt.execute("INSERT INTO orders_bad VALUES (2, 'Jan Kowalski', 'jan@example.com', 'Mysz', 89.50)");
            stmt.execute("INSERT INTO orders_bad VALUES (3, 'Jan Kowalski', 'jan@example.com', 'Monitor', 899.00)");

            System.out.println("\n=== Cala zawartosc orders_bad ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM orders_bad")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | " + rs.getString("user_name") + " | "
                            + rs.getString("user_email") + " | " + rs.getString("product_name")
                            + " | " + rs.getBigDecimal("product_price"));
                }
            }
            // 1 | Jan Kowalski | jan@example.com | Klawiatura | 199.99
            // 2 | Jan Kowalski | jan@example.com | Mysz | 89.50
            // 3 | Jan Kowalski | jan@example.com | Monitor | 899.00
            // ^ "Jan Kowalski" i "jan@example.com" powtorzone 3 razy!
            // Gdyby Jan zmienil email, trzeba by zaktualizowac 3 wiersze
            // (anomalia aktualizacji) - latwo cos przeoczyc.

            try (ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) AS cnt, COUNT(DISTINCT user_email) AS unique_emails FROM orders_bad")) {
                if (rs.next()) {
                    System.out.println("\nLiczba wierszy: " + rs.getInt("cnt")
                            + ", unikalnych emaili: " + rs.getInt("unique_emails"));
                }
            }
            // Liczba wierszy: 3, unikalnych emaili: 1
            // -> ten sam email zduplikowany 3-krotnie w tabeli!

            /*
             * ============================================================
             * 🔍 LEPSZY PROJEKT (znormalizowany) - users/orders/products/order_items
             * ============================================================
             */

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
                        FOREIGN KEY (user_id) REFERENCES users(id)
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE order_items (
                        order_id INT NOT NULL,
                        product_id INT NOT NULL,
                        PRIMARY KEY (order_id, product_id),
                        FOREIGN KEY (order_id) REFERENCES orders(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
                    )
                    """);

            System.out.println("\n=== LEPSZY PROJEKT: users, products, orders, order_items ===");

            // Dane Jana - JEDEN raz w users
            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski', 'jan@example.com')");

            // Produkty - JEDEN raz w products
            stmt.execute("INSERT INTO products VALUES (1, 'Klawiatura', 199.99)");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz', 89.50)");
            stmt.execute("INSERT INTO products VALUES (3, 'Monitor', 899.00)");

            // Te same 3 zamowienia co wyzej, ale bez zadnej duplikacji danych usera/produktow
            stmt.execute("INSERT INTO orders VALUES (1, 1)");
            stmt.execute("INSERT INTO orders VALUES (2, 1)");
            stmt.execute("INSERT INTO orders VALUES (3, 1)");

            stmt.execute("INSERT INTO order_items VALUES (1, 1)"); // zamowienie 1 -> klawiatura
            stmt.execute("INSERT INTO order_items VALUES (2, 2)"); // zamowienie 2 -> mysz
            stmt.execute("INSERT INTO order_items VALUES (3, 3)"); // zamowienie 3 -> monitor

            System.out.println("\n=== Te same 3 zamowienia, zero duplikacji danych usera/produktu ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT o.id AS order_id, u.name, u.email, p.name AS product_name, p.price
                    FROM orders o
                    JOIN users u ON u.id = o.user_id
                    JOIN order_items oi ON oi.order_id = o.id
                    JOIN products p ON p.id = oi.product_id
                    ORDER BY o.id
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getInt("order_id") + " | " + rs.getString("name") + " | "
                            + rs.getString("email") + " | " + rs.getString("product_name")
                            + " | " + rs.getBigDecimal("price"));
                }
            }
            // 1 | Jan Kowalski | jan@example.com | Klawiatura | 199.99
            // 2 | Jan Kowalski | jan@example.com | Mysz | 89.50
            // 3 | Jan Kowalski | jan@example.com | Monitor | 899.00
            // (JOIN dokladnie poznamy w Lesson14 - tu tylko wynik koncowy)
            // Dane usera fizycznie zapisane RAZ (w users), nie 3 razy!

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM users")) {
                if (rs.next()) {
                    System.out.println("\nLiczba fizycznych wierszy z danymi Jana w calym schemacie: " + rs.getInt("cnt"));
                }
            }
            // Liczba fizycznych wierszy z danymi Jana w calym schemacie: 1
            // -> zamiast 3-krotnej duplikacji (jak w orders_bad), dane Jana istnieja RAZ.
        }

        /*
         * ============================================================
         * 🔹 KIEDY NORMALIZACJA SZKODZI?
         * ============================================================
         * Normalizacja nie jest "zawsze im więcej, tym lepiej":
         *
         * - Zbyt duża liczba tabel oznacza WIĘCEJ JOIN-ów w każdym
         *   zapytaniu - a JOIN-y (zwłaszcza wielokrotne, na dużych
         *   tabelach) mają swój koszt wydajnościowy.
         * - W systemach analitycznych / raportowych (dużo odczytu,
         *   mało zapisu) czasem CELOWO stosuje się DENORMALIZACJĘ
         *   (świadome wprowadzenie kontrolowanej duplikacji), żeby
         *   przyspieszyć odczyt kosztem odrobiny redundancji.
         * - Dla małych, rzadko zmienianych danych (np. tabela
         *   słownikowa z 3 stałymi wartościami) nadmierne rozbijanie
         *   na kolejne tabele bywa przesadą - dodaje złożoność bez
         *   realnej korzyści.
         *
         * 📌 Praktyczna zasada: normalizuj do 3NF jako domyślny,
         * bezpieczny punkt startowy - świadomie denormalizuj TYLKO gdy
         * masz zmierzony problem wydajnościowy, który to rozwiązuje.
         */

        System.out.println("\n=== KIEDY NORMALIZACJA SZKODZI ===");
        System.out.println("Zbyt wiele tabel = zbyt wiele JOIN-ow = koszt wydajnosciowy.");
        System.out.println("Denormalizacja bywa celowa w systemach analitycznych (odczyt >> zapis).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Normalizacja eliminuje zbędną duplikację i anomalie:
         *   aktualizacji, wstawiania, usuwania.
         * - 1NF: wartości atomowe, brak list w jednej kolumnie.
         * - 2NF: kolumny zależą od CAŁEGO klucza głównego.
         * - 3NF: kolumny zależą TYLKO od klucza, nie od innych kolumn
         *   (brak zależności przechodnich).
         * - Zły projekt (orders_bad) duplikuje dane usera/produktu
         *   w KAŻDYM wierszu zamówienia - ryzyko niespójności.
         * - Dobry projekt (users/products/orders/order_items) trzyma
         *   każdą informację w JEDNYM miejscu, łączonym kluczami obcymi.
         * - Nadmierna normalizacja ma koszt (więcej JOIN-ów) -
         *   denormalizacja bywa świadomym, uzasadnionym wyborem.
         */
    }
}
