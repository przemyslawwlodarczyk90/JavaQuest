package com.example.javaquest._09_jdbc.Lesson11_CreateAndDropTableFromJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson11_CreateAndDropTableFromJava {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 TWORZENIE I KASOWANIE TABEL Z POZIOMU JAVY
         * ============================================================
         * Do tej pory w każdej lekcji tworzyliśmy tabelę przez
         * `statement.execute("CREATE TABLE ...")` - to zwykłe polecenie
         * DDL (Data Definition Language), wykonywane tak samo jak każde
         * inne zapytanie SQL. W tej lekcji przyjrzymy się temu świadomie:
         * jak tworzyć i usuwać tabele z kodu Javy, i - co ważniejsze -
         * KIEDY to w ogóle ma sens robić.
         */

        String url = "jdbc:h2:mem:jdbclesson11;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            /*
             * ============================================================
             * 🔹 CREATE TABLE z poziomu Javy
             * ============================================================
             */
            System.out.println("=== CREATE TABLE ===");
            statement.execute("""
                    CREATE TABLE products (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL
                    )
                    """);
            System.out.println("Tabela 'products' utworzona.");

            statement.executeUpdate("INSERT INTO products VALUES (1, 'Klawiatura', 149.99)");
            statement.executeUpdate("INSERT INTO products VALUES (2, 'Monitor', 899.00)");
            System.out.println("Wstawiono 2 wiersze.\n");

            System.out.println("=== Zawartosc tabeli 'products' ===");
            try (ResultSet rs = statement.executeQuery("SELECT id, name, price FROM products ORDER BY id")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + ": " + rs.getString("name") + " - " + rs.getBigDecimal("price") + " zl");
                }
            }

            /*
             * ============================================================
             * 🔍 KIEDY TWORZENIE TABEL Z JAVY MA SENS?
             * ============================================================
             * Tworzenie/kasowanie tabel bezpośrednio z kodu aplikacji jest
             * przydatne przede wszystkim w scenariuszach TYMCZASOWYCH i
             * KONTROLOWANYCH przez nas samych:
             *
             * - TESTY JEDNOSTKOWE / INTEGRACYJNE - przed każdym testem
             *   (albo klasą testów) tworzymy "świeżą" tabelę w bazie
             *   testowej (często w pamięci, jak nasze H2), a po teście ją
             *   kasujemy albo cała baza znika razem z JVM.
             * - DEMO / PROTOTYPOWANIE - szybkie sprawdzenie pomysłu, nauka
             *   (dokładnie tak, jak robimy w tym kursie), gdzie nie zależy
             *   nam na trwałości danych ani na współdzieleniu schematu
             *   z innymi osobami/aplikacjami.
             * - Narzędzia jednorazowe, skrypty migracyjne "ad-hoc" pisane
             *   pod jedno konkretne zadanie.
             *
             * 🔍 DLACZEGO W PRAWDZIWYCH PROJEKTACH PRODUKCYJNYCH TAK SIĘ
             * NIE ROBI?
             * ============================================================
             * W większym, długo żyjącym projekcie produkcyjnym ręczne
             * `CREATE TABLE` / `ALTER TABLE` wywoływane z kodu aplikacji
             * (albo, co gorsza, ręcznie przez kogoś na produkcyjnej bazie)
             * ma poważne wady:
             *
             * - BRAK WERSJONOWANIA SCHEMATU - nie ma jednoznacznej historii
             *   "kto, kiedy i jaką zmianę wprowadził w strukturze bazy";
             *   schemat bazy przestaje być zsynchronizowany z historią
             *   kodu w repozytorium.
             * - BRAK POWTARZALNOŚCI - to, co zadziała na bazie danego
             *   developera, niekoniecznie odtworzy się identycznie na
             *   środowisku testowym, stagingowym czy produkcyjnym.
             * - BEZPIECZEŃSTWO PRODUKCYJNE - przypadkowe uruchomienie
             *   `CREATE TABLE` / `DROP TABLE` na bazie, która już ma
             *   realne dane produkcyjne, może być katastrofalne w
             *   skutkach (utrata danych, przestój aplikacji).
             *
             * Dlatego w prawdziwych projektach do zarządzania schematem
             * bazy danych używa się narzędzi do MIGRACJI, takich jak
             * Flyway albo Liquibase. Migracja to ponumerowany, wersjonowany
             * skrypt SQL (albo opis zmiany), trzymany w repozytorium kodu
             * razem z aplikacją - narzędzie migracyjne pilnuje, żeby każda
             * migracja uruchomiła się dokładnie raz, w odpowiedniej
             * kolejności, na każdym środowisku. Pełny temat migracji
             * (Flyway/Liquibase) poznamy w rozdziale poświęconym warstwie
             * DAO/dostępu do danych - tu chcemy tylko zasygnalizować, ŻE
             * taki mechanizm istnieje i po co.
             */
            System.out.println("\n=== DROP TABLE ===");
            statement.execute("DROP TABLE products");
            System.out.println("Tabela 'products' usunieta.");

            // Dowod, ze tabela faktycznie zniknela - proba SELECT-a
            // zakonczy sie SQLException (tabela nie istnieje).
            try {
                statement.executeQuery("SELECT * FROM products");
            } catch (SQLException e) {
                System.out.println("Proba SELECT po DROP TABLE zakonczona bledem (tak jak oczekiwano):");
                System.out.println("  " + e.getMessage().lines().findFirst().orElse(e.getMessage()));
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Tabele można tworzyć/usuwać z poziomu Javy przez
         *   `Statement.execute("CREATE TABLE ...")` / `"DROP TABLE ..."` -
         *   to zwykłe polecenia DDL, wykonywane tak samo jak SELECT/INSERT.
         * - Ma to sens w testach, demach, prototypach - czyli tam, gdzie
         *   sami w pełni kontrolujemy cykl życia bazy danych.
         * - W projektach produkcyjnych schemat bazy zarządzany jest przez
         *   MIGRACJE (np. Flyway, Liquibase) - wersjonowane, powtarzalne,
         *   bezpieczne skrypty zmian, a nie ręczny DDL rozsiany po kodzie
         *   aplikacji. Szczegóły migracji - w rozdziale o warstwie DAO.
         */
    }
}
