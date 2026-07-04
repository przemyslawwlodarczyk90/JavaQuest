package com.example.javaquest._08_sql.Lesson08_DDL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson08_DDL {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DDL – DATA DEFINITION LANGUAGE
         * ============================================================
         * DDL to grupa poleceń SQL, które operują na STRUKTURZE bazy
         * danych – a nie na samych danych (to robi DML, patrz Lesson09).
         *
         * Do DDL należą m.in.:
         * - CREATE DATABASE  – tworzy nową bazę danych
         * - CREATE TABLE     – tworzy nową tabelę
         * - ALTER TABLE      – zmienia strukturę istniejącej tabeli
         *                      (dodaje/usuwa kolumny, zmienia typy...)
         * - DROP TABLE       – usuwa całą tabelę (razem z danymi!)
         * - TRUNCATE TABLE   – czyści WSZYSTKIE dane z tabeli, ale
         *                      zostawia samą strukturę tabeli
         *
         * 🔹 CREATE DATABASE – gdzie ono jest w naszych przykładach?
         * W "prawdziwym" silniku SQL (np. PostgreSQL, MySQL) zwykle
         * najpierw łączymy się do serwera bazodanowego i wykonujemy:
         *     CREATE DATABASE sklep;
         * żeby stworzyć nową, pustą bazę danych, do której dopiero
         * potem się podłączamy.
         *
         * H2 w trybie in-memory (`jdbc:h2:mem:...`) tworzy bazę danych
         * NIEJAWNIE, w momencie pierwszego połączenia z danym URL-em –
         * nie musimy (i nie możemy) wywołać jawnego CREATE DATABASE.
         * To wygodne do nauki i demo, ale warto pamiętać, że w realnym
         * projekcie ten krok zwykle wykonuje administrator bazy danych
         * (DBA) lub skrypt migracyjny, zanim aplikacja w ogóle się
         * połączy.
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson08;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            System.out.println("=== CREATE TABLE ===");

            /*
             * ============================================================
             * 🔹 CREATE TABLE
             * ============================================================
             * Tworzy nową tabelę – definiujemy nazwy kolumn, ich TYPY
             * danych oraz opcjonalnie ograniczenia (constraints), np.
             * PRIMARY KEY (klucz główny, jednoznacznie identyfikujący
             * wiersz).
             */
            stmt.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        price DECIMAL(10, 2)
                    )
                    """);
            System.out.println("Tabela 'products' utworzona.");

            stmt.execute("INSERT INTO products VALUES (1, 'Klawiatura', 149.99)");
            stmt.execute("INSERT INTO products VALUES (2, 'Mysz', 79.50)");
            printAll(stmt, "products");

            /*
             * ============================================================
             * 🔹 ALTER TABLE – dodawanie kolumny
             * ============================================================
             * ALTER TABLE modyfikuje strukturę JUŻ ISTNIEJĄCEJ tabeli.
             * ADD COLUMN dodaje nową kolumnę – wszystkie istniejące
             * wiersze dostaną w niej wartość NULL (chyba że podamy
             * DEFAULT).
             */
            System.out.println("\n=== ALTER TABLE ... ADD COLUMN ===");
            stmt.execute("ALTER TABLE products ADD COLUMN quantity INT DEFAULT 0");
            System.out.println("Dodano kolumne 'quantity'.");
            printAll(stmt, "products");

            /*
             * ============================================================
             * 🔹 ALTER TABLE – zmiana typu kolumny
             * ============================================================
             * ALTER COLUMN ... SET DATA TYPE (w H2) pozwala zmienić typ
             * istniejącej kolumny. Trzeba uważać – nie każda zmiana typu
             * jest "bezpieczna" (np. zmiana VARCHAR -> INT może się nie
             * udać, jeśli dane nie dają się skonwertować).
             *
             * Uwaga składniowa: dokładna składnia ALTER COLUMN różni się
             * między silnikami SQL:
             * - H2 / PostgreSQL: ALTER TABLE t ALTER COLUMN c SET DATA TYPE typ
             * - MySQL:            ALTER TABLE t MODIFY COLUMN c typ
             * Zawsze sprawdzaj dokumentację konkretnego silnika!
             */
            System.out.println("\n=== ALTER TABLE ... ALTER COLUMN (zmiana typu) ===");
            stmt.execute("ALTER TABLE products ALTER COLUMN quantity SET DATA TYPE BIGINT");
            System.out.println("Zmieniono typ kolumny 'quantity' na BIGINT.");

            /*
             * ============================================================
             * 🔹 ALTER TABLE – usuwanie kolumny
             * ============================================================
             */
            System.out.println("\n=== ALTER TABLE ... DROP COLUMN ===");
            stmt.execute("ALTER TABLE products DROP COLUMN quantity");
            System.out.println("Usunieto kolumne 'quantity'.");
            printAll(stmt, "products");

            /*
             * ============================================================
             * 🔹 TRUNCATE TABLE
             * ============================================================
             * TRUNCATE usuwa WSZYSTKIE wiersze z tabeli, ale zostawia
             * samą strukturę (kolumny, typy, ograniczenia) – tabela dalej
             * istnieje, jest tylko pusta.
             *
             * Różnica względem DELETE FROM products (bez WHERE), które
             * daje ten sam efekt "logiczny" (pusta tabela), ale:
             * - TRUNCATE jest zwykle znacznie SZYBSZE (nie loguje
             *   usuwania wiersz po wierszu),
             * - TRUNCATE zwykle resetuje liczniki AUTO_INCREMENT,
             * - DELETE jest częścią DML i można go cofnąć w transakcji
             *   równie łatwo jak TRUNCATE w wielu silnikach – szczegóły
             *   zależą od konkretnej bazy danych.
             */
            System.out.println("\n=== TRUNCATE TABLE ===");
            stmt.execute("TRUNCATE TABLE products");
            System.out.println("Tabela 'products' wyczyszczona (struktura zostaje).");
            printAll(stmt, "products");

            /*
             * ============================================================
             * 🔹 DROP TABLE
             * ============================================================
             * DROP TABLE usuwa CAŁĄ tabelę – łącznie ze strukturą i
             * wszelkimi danymi. To nieodwracalna operacja (chyba że
             * jesteśmy w transakcji i zrobimy ROLLBACK przed COMMIT).
             */
            System.out.println("\n=== DROP TABLE ===");
            stmt.execute("DROP TABLE products");
            System.out.println("Tabela 'products' usunieta calkowicie.");

            boolean tabelaIstnieje = true;
            try {
                stmt.executeQuery("SELECT * FROM products");
            } catch (SQLException e) {
                tabelaIstnieje = false;
            }
            System.out.println("Czy tabela 'products' nadal istnieje? " + tabelaIstnieje);

            /*
             * ============================================================
             * 🔹 CONSTRAINTS (OGRANICZENIA) PRZY TWORZENIU TABELI
             * ============================================================
             * Constraints to reguły pilnujące poprawności danych na
             * poziomie bazy (niezależnie od tego, co "zapomni" sprawdzić
             * aplikacja):
             * - PRIMARY KEY  – unikalny identyfikator wiersza
             * - NOT NULL     – kolumna nie może być pusta
             * - UNIQUE       – wartości w kolumnie muszą być unikalne
             * - CHECK        – dowolny warunek logiczny (np. cena >= 0)
             * - FOREIGN KEY  – wiąże kolumnę z kluczem głównym innej
             *                  tabeli (szerzej: Lesson15_SqlRelationships)
             */
            System.out.println("\n=== CREATE TABLE z CONSTRAINTAMI ===");
            stmt.execute("""
                    CREATE TABLE employees (
                        id INT PRIMARY KEY,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        salary DECIMAL(10, 2) CHECK (salary >= 0)
                    )
                    """);
            stmt.execute("INSERT INTO employees VALUES (1, 'jan.kowalski@firma.pl', 5000.00)");
            System.out.println("Wstawiono poprawny wiersz do 'employees'.");

            try {
                stmt.execute("INSERT INTO employees VALUES (2, 'jan.kowalski@firma.pl', 4000.00)");
            } catch (SQLException e) {
                System.out.println("Blad (zlamano UNIQUE na 'email'): " + e.getMessage());
            }

            try {
                stmt.execute("INSERT INTO employees VALUES (3, 'anna@firma.pl', -100.00)");
            } catch (SQLException e) {
                System.out.println("Blad (zlamano CHECK na 'salary'): " + e.getMessage());
            }

            printAll(stmt, "employees");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DDL (Data Definition Language) = operacje na STRUKTURZE bazy
         *   danych: CREATE, ALTER, DROP, TRUNCATE
         * - CREATE DATABASE tworzy nową bazę – w H2 in-memory dzieje się
         *   to niejawnie przy pierwszym połączeniu z danym URL-em
         * - CREATE TABLE definiuje kolumny, typy danych i ograniczenia
         * - ALTER TABLE modyfikuje istniejącą tabelę (ADD COLUMN, DROP
         *   COLUMN, ALTER COLUMN ... SET DATA TYPE – składnia zależy od
         *   silnika SQL)
         * - TRUNCATE TABLE czyści dane, ale zostawia strukturę tabeli
         * - DROP TABLE usuwa tabelę CAŁKOWICIE (struktura + dane)
         * - Constraints (PRIMARY KEY, NOT NULL, UNIQUE, CHECK, FOREIGN
         *   KEY) pilnują poprawności danych na poziomie bazy danych
         */
    }

    private static void printAll(Statement stmt, String table) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder(" - ");
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getMetaData().getColumnName(i)).append("=").append(rs.getString(i));
                }
                System.out.println(sb);
            }
        }
    }
}
