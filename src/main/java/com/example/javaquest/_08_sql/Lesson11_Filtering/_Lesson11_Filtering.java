package com.example.javaquest._08_sql.Lesson11_Filtering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson11_Filtering {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 FILTROWANIE DANYCH – WHERE I OPERATORY LOGICZNE
         * ============================================================
         * W Lesson10_Select poznaliśmy WHERE w najprostszej postaci
         * (jeden prosty warunek). Tu poznajemy pełen zestaw operatorów,
         * które pozwalają budować bardziej złożone i precyzyjne warunki
         * filtrowania.
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson11;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        category VARCHAR(50),
                        price DECIMAL(10, 2),
                        description VARCHAR(200)
                    )
                    """);
            stmt.execute("INSERT INTO products VALUES (1, 'Klawiatura', 'Peryferia', 149.99, 'Mechaniczna klawiatura RGB')");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz', 'Peryferia', 79.50, 'Bezprzewodowa mysz optyczna')");
            stmt.execute("INSERT INTO products VALUES (3, 'Monitor', 'Peryferia', 899.00, NULL)");
            stmt.execute("INSERT INTO products VALUES (4, 'Laptop', 'Komputery', 3999.00, 'Wydajny laptop do pracy i gier')");
            stmt.execute("INSERT INTO products VALUES (5, 'Komputer stacjonarny', 'Komputery', 4599.00, NULL)");
            stmt.execute("INSERT INTO products VALUES (6, 'Kabel HDMI', 'Peryferia', 19.99, 'Kabel HDMI 2m')");

            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        product_id INT
                    )
                    """);
            stmt.execute("INSERT INTO orders VALUES (1, 1)");
            stmt.execute("INSERT INTO orders VALUES (2, 4)");

            /*
             * ============================================================
             * 🔹 AND, OR, NOT
             * ============================================================
             * - AND – oba warunki muszą być prawdziwe
             * - OR  – wystarczy, że jeden z warunków jest prawdziwy
             * - NOT – zaprzecza warunkowi
             * Kolejność łączenia warunków warto kontrolować nawiasami –
             * tak jak w Javie, AND ma wyższy priorytet niż OR.
             */
            System.out.println("=== AND ===");
            print(stmt.executeQuery(
                    "SELECT name, category, price FROM products WHERE category = 'Peryferia' AND price > 100"));

            System.out.println("\n=== OR ===");
            print(stmt.executeQuery(
                    "SELECT name, category FROM products WHERE category = 'Komputery' OR price < 20"));

            System.out.println("\n=== NOT ===");
            print(stmt.executeQuery(
                    "SELECT name, category FROM products WHERE NOT category = 'Peryferia'"));

            /*
             * ============================================================
             * 🔹 LIKE – dopasowanie wzorca tekstowego
             * ============================================================
             * LIKE porównuje tekst ze WZORCEM zawierającym symbole
             * specjalne:
             * - %  – dowolny ciąg znaków (również pusty)
             * - _  – dokładnie jeden dowolny znak
             *
             * Domyślnie LIKE w H2 (podobnie jak w większości silników SQL)
             * jest CASE-SENSITIVE (rozróżnia wielkość liter).
             */
            System.out.println("\n=== LIKE (nazwy zaczynajace sie na 'K') ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE name LIKE 'K%'"));

            System.out.println("\n=== LIKE z '_' (dokladnie jeden znak) ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE name LIKE '_ysz'"));

            /*
             * ============================================================
             * 🔹 ILIKE – dopasowanie wzorca BEZ rozróżniania wielkości liter
             * ============================================================
             * ⚠️ PRZENOŚNOŚĆ: ILIKE to operator wywodzący się z PostgreSQL
             * i NIE jest częścią standardu SQL – większość silników (np.
             * MySQL, SQL Server) go w ogóle nie zna.
             *
             * H2 – mimo że nie jest to szeroko znane – WSPIERA ILIKE jako
             * własne rozszerzenie (case-insensitive odpowiednik LIKE), co
             * potwierdza poniższy, realnie działający przykład. Mimo to,
             * jeśli piszesz kod, który ma działać na WIELU silnikach baz
             * danych, bezpieczniejszym, przenośnym rozwiązaniem jest jawne
             * ujednolicenie wielkości liter po obu stronach porównania:
             *     WHERE LOWER(name) LIKE LOWER('%laptop%')
             * Ten zapis zadziała praktycznie wszędzie, niezależnie od tego,
             * czy silnik zna ILIKE.
             */
            System.out.println("\n=== ILIKE (H2 wspiera, ale to NIE jest standard SQL) ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE name ILIKE '%LAPTOP%'"));

            System.out.println("\n=== Przenosny odpowiednik: LOWER(x) LIKE LOWER(...) ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE LOWER(name) LIKE LOWER('%LAPTOP%')"));

            /*
             * ============================================================
             * 🔹 BETWEEN – zakres wartości (włącznie z granicami)
             * ============================================================
             * x BETWEEN a AND b jest równoważne x >= a AND x <= b –
             * obie granice są WŁĄCZONE do wyniku.
             */
            System.out.println("\n=== BETWEEN (cena 50-900) ===");
            print(stmt.executeQuery("SELECT name, price FROM products WHERE price BETWEEN 50 AND 900"));

            /*
             * ============================================================
             * 🔹 IN – dopasowanie do listy wartości
             * ============================================================
             * x IN (a, b, c) jest równoważne x = a OR x = b OR x = c,
             * ale krócej i czytelniej.
             */
            System.out.println("\n=== IN ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE id IN (1, 4, 6)"));

            /*
             * ============================================================
             * 🔹 EXISTS – czy podzapytanie zwraca jakiekolwiek wiersze
             * ============================================================
             * EXISTS (podzapytanie) zwraca TRUE, jeśli podzapytanie
             * zwróci choćby jeden wiersz. Często używane do sprawdzania
             * "czy dla tego wiersza istnieje powiązany rekord w innej
             * tabeli" – bez potrzeby pobierania samych danych z podzapytania.
             * Więcej o podzapytaniach -> Lesson16_Subqueries.
             */
            System.out.println("\n=== EXISTS (produkty, ktore maja zamowienie) ===");
            print(stmt.executeQuery("""
                    SELECT p.name
                    FROM products p
                    WHERE EXISTS (SELECT 1 FROM orders o WHERE o.product_id = p.id)
                    """));

            /*
             * ============================================================
             * 🔹 IS NULL / IS NOT NULL
             * ============================================================
             * NULL oznacza "brak wartości" – NIE MOŻNA go porównywać
             * operatorem = (warunek `x = NULL` nigdy nie jest prawdziwy!).
             * Do sprawdzania braku/obecności wartości służą dedykowane
             * operatory IS NULL / IS NOT NULL (patrz też Lesson05_NullValues).
             */
            System.out.println("\n=== IS NULL (produkty bez opisu) ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE description IS NULL"));

            System.out.println("\n=== IS NOT NULL (produkty z opisem) ===");
            print(stmt.executeQuery("SELECT name FROM products WHERE description IS NOT NULL"));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - AND / OR / NOT łączą i negują warunki logiczne w WHERE
         * - LIKE dopasowuje tekst do wzorca (% – dowolny ciąg, _ – jeden
         *   znak), domyślnie case-sensitive
         * - ILIKE to case-insensitive odpowiednik LIKE – działa w H2 i w
         *   PostgreSQL, ale NIE jest standardem SQL i nie zadziała np. w
         *   MySQL czy SQL Server; przenośna alternatywa to
         *   LOWER(x) LIKE LOWER(...)
         * - BETWEEN a AND b sprawdza zakres WŁĄCZNIE z granicami
         * - IN (a, b, c) sprawdza przynależność do listy wartości
         * - EXISTS (podzapytanie) sprawdza, czy podzapytanie zwraca
         *   jakiekolwiek wiersze
         * - IS NULL / IS NOT NULL to jedyny poprawny sposób sprawdzania
         *   braku/obecności wartości NULL (nie `= NULL`!)
         */
    }

    private static void print(ResultSet rs) throws SQLException {
        try (rs) {
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder(" - ");
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getMetaData().getColumnLabel(i)).append("=").append(rs.getString(i));
                }
                System.out.println(sb);
            }
        }
    }
}
