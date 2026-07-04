package com.example.javaquest._08_sql.Lesson04_SqlDataTypes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class _Lesson04_SqlDataTypes {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 TYPY DANYCH W SQL – PRZEGLĄD
         * ============================================================
         * Każda kolumna w tabeli SQL ma ZDEFINIOWANY TYP DANYCH – baza
         * pilnuje, żeby wszystkie wartości w tej kolumnie do niego
         * pasowały. Najważniejsze typy (składnia H2, zbliżona do
         * standardu SQL i większości innych DBMS-ów):
         *
         * LICZBY CAŁKOWITE:
         * - INTEGER (INT)  – liczba całkowita 32-bitowa.
         * - BIGINT          – liczba całkowita 64-bitowa (większy zakres).
         *
         * TEKST:
         * - VARCHAR(n)      – tekst o zmiennej długości, MAX n znaków.
         * - TEXT (CLOB w H2 to alias) – tekst o (praktycznie)
         *   nieograniczonej długości, bez limitu jak VARCHAR(n).
         *
         * LICZBY DZIESIĘTNE (DOKŁADNE):
         * - DECIMAL(p, s) / NUMERIC(p, s) – liczba dziesiętna o DOKŁADNEJ
         *   precyzji: p = łączna liczba cyfr, s = liczba cyfr po przecinku.
         *   DECIMAL i NUMERIC to w praktyce SYNONIMY.
         *
         * WARTOŚCI LOGICZNE:
         * - BOOLEAN         – true/false.
         *
         * DATA I CZAS:
         * - DATE            – tylko data (rok-miesiąc-dzień), bez godziny.
         * - TIME            – tylko godzina (bez daty).
         * - TIMESTAMP       – data + godzina razem.
         *
         * IDENTYFIKATORY:
         * - UUID            – 128-bitowy globalnie unikalny identyfikator
         *   (Universally Unique Identifier), np. do kluczy głównych
         *   w systemach rozproszonych (nie trzeba pytać bazy o kolejną
         *   wartość licznika – UUID generuje się niezależnie).
         *
         * DANE PÓŁSTRUKTURALNE:
         * - JSON            – H2 wspiera kolumnę typu JSON: przechowuje
         *   tekst w formacie JSON. Poprawność składni jest sprawdzana,
         *   gdy wartość wstawiamy przez literał "JSON '...'" (patrz demo
         *   niżej). To znacznie uboższe wsparcie niż np. JSONB
         *   w PostgreSQL (tam JSON można indeksować i odpytywać
         *   operatorami SQL) – tu traktujemy to jako "tekst z opcjonalną
         *   walidacją składni", nic więcej.
         */

        System.out.println("=== TYPY DANYCH SQL - PRZEGLAD ===");
        System.out.println("INTEGER/BIGINT, VARCHAR/TEXT, DECIMAL/NUMERIC, BOOLEAN, DATE/TIME/TIMESTAMP, UUID, JSON");

        /*
         * ============================================================
         * 🔹 MAPOWANIE SQL -> JAVA
         * ============================================================
         * Typ SQL          | Typ Java (JDBC)
         * -----------------|--------------------------------
         * INTEGER          | int / Integer
         * BIGINT           | long / Long
         * VARCHAR / TEXT   | String
         * DECIMAL/NUMERIC  | java.math.BigDecimal
         * BOOLEAN          | boolean / Boolean
         * DATE             | java.time.LocalDate
         * TIME             | java.time.LocalTime
         * TIMESTAMP        | java.time.LocalDateTime (lub Instant, gdy
         *                  | interesuje nas konkretny moment w czasie
         *                  | niezależny od strefy)
         * UUID             | java.util.UUID
         *
         * ⚠️ NAJWAŻNIEJSZA ZASADA W TEJ LEKCJI:
         * Do przechowywania PIENIĘDZY zawsze używaj DECIMAL/NUMERIC
         * w SQL i BigDecimal w Javie – NIGDY double/float!
         * double/float to liczby ZMIENNOPRZECINKOWE BINARNE – nie da
         * się w nich dokładnie zapisać wielu ułamków dziesiętnych
         * (np. 0.1 + 0.2 != 0.3 w reprezentacji binarnej). Przy
         * pieniądzach takie błędy zaokrągleń są niedopuszczalne.
         * BigDecimal operuje na DOKŁADNEJ reprezentacji dziesiętnej –
         * dlatego to jedyny bezpieczny wybór do kwot pieniężnych.
         */

        System.out.println("\n=== MAPOWANIE SQL -> JAVA ===");
        System.out.println("DECIMAL/NUMERIC -> BigDecimal (ZAWSZE dla pieniedzy, NIGDY double!)");
        System.out.println("DATE -> LocalDate, TIME -> LocalTime, TIMESTAMP -> LocalDateTime, UUID -> java.util.UUID");

        /*
         * ============================================================
         * 🔍 DEMO: TABELA Z RÓŻNYMI TYPAMI DANYCH
         * ============================================================
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson04;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE products (
                        id UUID PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        description TEXT,
                        price DECIMAL(10, 2) NOT NULL,
                        in_stock BOOLEAN NOT NULL,
                        added_date DATE NOT NULL,
                        daily_check_time TIME NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        stock_quantity BIGINT NOT NULL
                    )
                    """);

            System.out.println("\n=== TABELA products (rozne typy) UTWORZONA ===");

            UUID productId = UUID.randomUUID();
            BigDecimal price = new BigDecimal("199.99"); // BigDecimal - NIGDY double dla pieniedzy!
            LocalDate addedDate = LocalDate.of(2026, 1, 15);
            LocalTime checkTime = LocalTime.of(9, 30);
            LocalDateTime createdAt = LocalDateTime.of(2026, 1, 15, 12, 0, 0);

            var insert = conn.prepareStatement("""
                    INSERT INTO products
                        (id, name, description, price, in_stock, added_date, daily_check_time, created_at, stock_quantity)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """);
            insert.setObject(1, productId);
            insert.setString(2, "Klawiatura mechaniczna");
            insert.setString(3, "Klawiatura z przelacznikami blue");
            insert.setBigDecimal(4, price);
            insert.setBoolean(5, true);
            insert.setObject(6, addedDate);
            insert.setObject(7, checkTime);
            insert.setObject(8, createdAt);
            insert.setLong(9, 500L);
            insert.executeUpdate();
            insert.close();
            // PreparedStatement poznamy szczegolowo w rozdziale o JDBC -
            // tu tylko jako wygodny sposob na wstawienie roznych typow danych.

            System.out.println("\n=== ODCZYT Z POPRAWNYM MAPOWANIEM TYPOW ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
                while (rs.next()) {
                    UUID id = rs.getObject("id", UUID.class);
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    BigDecimal readPrice = rs.getBigDecimal("price");
                    boolean inStock = rs.getBoolean("in_stock");
                    LocalDate readAddedDate = rs.getObject("added_date", LocalDate.class);
                    LocalTime readCheckTime = rs.getObject("daily_check_time", LocalTime.class);
                    LocalDateTime readCreatedAt = rs.getObject("created_at", LocalDateTime.class);
                    long stockQuantity = rs.getLong("stock_quantity");

                    System.out.println("id (UUID): " + id);
                    System.out.println("name: " + name);
                    System.out.println("description: " + description);
                    System.out.println("price (BigDecimal): " + readPrice);
                    System.out.println("in_stock: " + inStock);
                    System.out.println("added_date (LocalDate): " + readAddedDate);
                    System.out.println("daily_check_time (LocalTime): " + readCheckTime);
                    System.out.println("created_at (LocalDateTime): " + readCreatedAt);
                    System.out.println("stock_quantity (long): " + stockQuantity);
                }
            }
            // id (UUID): <losowy UUID>
            // name: Klawiatura mechaniczna
            // description: Klawiatura z przelacznikami blue
            // price (BigDecimal): 199.99
            // in_stock: true
            // added_date (LocalDate): 2026-01-15
            // daily_check_time (LocalTime): 09:30
            // created_at (LocalDateTime): 2026-01-15T12:00
            // stock_quantity (long): 500

            /*
             * ============================================================
             * 🔍 KOLUMNA JSON W H2
             * ============================================================
             * H2 wspiera kolumnę typu JSON – przechowuje tekst jako wartość
             * JSON. Walidacja poprawności składni JSON następuje, gdy
             * wartość podamy przez LITERAŁ "JSON '...'" (jawnie mówimy
             * bazie "to ma być JSON, sprawdź to") - zwykły String bez tego
             * prefiksu H2 może przyjąć bez weryfikacji składni, więc do
             * bezpiecznego wstawiania warto używać właśnie literału JSON
             * (albo walidować JSON po stronie Javy, np. Gson/Jackson -
             * poznane już w rozdziale o I/O, zanim dane trafią do bazy).
             * To i tak dużo uboższe wsparcie niż JSONB w PostgreSQL
             * (bez indeksowania po polach JSON, bez operatorów SQL do
             * wyciągania pojedynczych kluczy).
             */

            stmt.execute("CREATE TABLE settings (id INT PRIMARY KEY, config JSON)");
            stmt.execute("INSERT INTO settings VALUES (1, JSON '{\"theme\":\"dark\",\"pageSize\":20}')");

            System.out.println("\n=== KOLUMNA JSON (H2 - podstawowe wsparcie) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT config FROM settings WHERE id = 1")) {
                if (rs.next()) {
                    System.out.println("config: " + rs.getString("config"));
                }
            }
            // config: {"theme":"dark","pageSize":20}

            System.out.println("\n=== PROBA WSTAWIENIA NIEPOPRAWNEGO JSON (literal JSON '...') ===");
            try {
                stmt.execute("INSERT INTO settings VALUES (2, JSON 'to nie jest json')");
                System.out.println("To sie nie powinno wykonac!");
            } catch (SQLException e) {
                System.out.println("Odrzucono - niepoprawny JSON: " + e.getMessage());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - INTEGER/BIGINT - liczby całkowite (int/long w Javie).
         * - VARCHAR(n)/TEXT - tekst o ograniczonej/nieograniczonej długości.
         * - DECIMAL/NUMERIC - dokładne liczby dziesiętne -> BigDecimal.
         * - BOOLEAN - wartości logiczne.
         * - DATE/TIME/TIMESTAMP -> LocalDate/LocalTime/LocalDateTime.
         * - UUID - globalnie unikalny identyfikator -> java.util.UUID.
         * - JSON (H2) - tekst z podstawową walidacją składni, znacznie
         *   uboższy niż JSONB w PostgreSQL.
         * - ⚠️ Pieniądze zawsze jako DECIMAL/BigDecimal - NIGDY jako
         *   double/float (błędy zaokrągleń binarnych).
         */
    }
}
