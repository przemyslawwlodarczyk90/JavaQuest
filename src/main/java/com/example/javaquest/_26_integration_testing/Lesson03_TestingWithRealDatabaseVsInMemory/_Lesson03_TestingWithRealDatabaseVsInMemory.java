package com.example.javaquest._26_integration_testing.Lesson03_TestingWithRealDatabaseVsInMemory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson03_TestingWithRealDatabaseVsInMemory {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: Testowanie na prawdziwej bazie vs in-memory (H2) ===");

        /*
         * ============================================================
         * 📦 H2 in-memory (`_08_sql`-`_12_hibernate` w calym kursie) - SZYBKIE, ALE NIE IDENTYCZNE Z PRODUKCJA
         * ============================================================
         * Caly kurs (od `_08_sql`) uzywa H2 in-memory jako "PRAWDZIWEJ"
         * bazy DLA testow/demo - I TO jest UZASADNIONY wybor DLA
         * WIEKSZOSCI przypadkow (SZYBKI start, ZERO zewnetrznej
         * infrastruktury). ALE H2 NIE JEST IDENTYCZNY Z
         * PostgreSQL/MySQL - RÓZNICE MOGA ukrywac bledy, KTORE
         * ujawnia sie DOPIERO NA produkcji:
         * - DIALEKT SQL (funkcje, typy danych, skladnia niektorych
         *   klauzul - RÓZNI sie MIEDZY silnikami).
         * - ZACHOWANIE blokad/wspolbieznosci (`_12_hibernate/Lesson26`
         *   JUZ udokumentowal, ze H2/MVStore NIE honoruje W PELNI
         *   `lock.timeout` tak jak zrobilby to PostgreSQL/MySQL).
         * - OGRANICZENIA/TRIGGERY specyficzne DLA silnika.
         * - DOKLADNE zachowanie TYPOW (np. precyzja `DECIMAL`,
         *   strefy czasowe `TIMESTAMP`).
         *
         * 🔍 Testcontainers (Lesson04-06) ROZWIAZUJE TEN problem -
         * URUCHAMIA PRAWDZIWY silnik (np. PostgreSQL) W kontenerze
         * Docker NA POTRZEBY testu - "najlepsze Z obu swiatow"
         * (realizm PRODUKCJI + automatyzacja/izolacja testu).
         */
        System.out.println("H2 in-memory = SZYBKIE, ale RÓZNI sie dialektem/blokadami OD PostgreSQL/MySQL. Testcontainers (Lesson04-06) = PRAWDZIWY silnik W kontenerze.");

        demonstrateDialectDifference();
        demonstrateTradeoffSummary();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - H2 in-memory: BLYSKAWICZNY start (milisekundy), ZERO
         *   zaleznosci zewnetrznych, DOBRY DLA WIEKSZOSCI testow
         *   logiki DAO/repozytorium (`_09_jdbc`-`_10_dao`,
         *   `_23_spring_data_jpa`).
         * - PRAWDZIWA baza (Testcontainers): WOLNIEJSZY start
         *   (sekundy), WYMAGA Dockera, ALE 100% ZGODNOSC Z
         *   PRODUKCJA - UZYWAJ DLA: SQL specyficznego DLA silnika,
         *   testowania migracji (`_10_dao/Lesson25_Flyway`), zlozonych
         *   scenariuszy blokad/wspolbieznosci.
         * - PRAKTYCZNA rekomendacja: H2 DLA WIEKSZOSCI testow (SZYBKIE
         *   sprzezenie zwrotne), Testcontainers DLA "kotwiczacych"
         *   testow integracyjnych URUCHAMIANYCH RZADZIEJ (np. przed
         *   mergem - powiazanie Z Lesson14_CiCdIntegration).
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateDialectDifference() throws SQLException {
        System.out.println("\n--- Przyklad: funkcja specyficzna DLA H2, KTOREJ NIE MA W PostgreSQL/MySQL ---");
        String jdbcUrl = "jdbc:h2:mem:lesson03;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "");
             Statement statement = connection.createStatement()) {

            // H2 rozumie skladnie "MERGE INTO" (upsert) - PostgreSQL uzywa "INSERT ... ON CONFLICT",
            // MySQL uzywa "INSERT ... ON DUPLICATE KEY UPDATE" - TA SAMA operacja logiczna,
            // TRZY ROZNE dialekty SQL. Kod napisany I przetestowany TYLKO na H2 MOZE
            // nie dzialac bez zmian na innym silniku.
            statement.execute("CREATE TABLE settings (id INT PRIMARY KEY, setting_value VARCHAR(50))");
            statement.execute("MERGE INTO settings KEY(id) VALUES (1, 'pierwsza-wartosc')");
            statement.execute("MERGE INTO settings KEY(id) VALUES (1, 'zaktualizowana-wartosc')");

            try (ResultSet resultSet = statement.executeQuery("SELECT setting_value FROM settings WHERE id = 1")) {
                resultSet.next();
                String value = resultSet.getString("setting_value");
                assertThat(value).isEqualTo("zaktualizowana-wartosc");
                System.out.println("H2 'MERGE INTO' (upsert) zadzialal: " + value);
                System.out.println("UWAGA: ta SAMA skladnia RZUCI blad skladniowy na PostgreSQL/MySQL - inny dialekt!");
            }

            // Przyklad funkcji WYLACZNIE H2 - RZUCA blad, GDYBY probowac jej uzyc na innym silniku.
            assertThatThrownBy(() -> statement.executeQuery("SELECT NIEISTNIEJACA_FUNKCJA_SPECYFICZNA()"))
                    .isInstanceOf(SQLException.class);
        }
    }

    private static void demonstrateTradeoffSummary() {
        System.out.println("\n--- Tabela kompromisow: H2 in-memory vs PRAWDZIWA baza (Testcontainers) ---");
        System.out.printf("%-30s | %-20s | %-20s%n", "Kryterium", "H2 in-memory", "Testcontainers");
        System.out.println("-".repeat(75));
        System.out.printf("%-30s | %-20s | %-20s%n", "Czas startu", "~10-50 ms", "~1-5 sekund");
        System.out.printf("%-30s | %-20s | %-20s%n", "Wymaga Dockera", "NIE", "TAK");
        System.out.printf("%-30s | %-20s | %-20s%n", "Zgodnosc z produkcja", "CZESCIOWA", "PELNA");
        System.out.printf("%-30s | %-20s | %-20s%n", "Dobre DO", "logiki DAO/repo", "migracji, blokad, dialektu");
    }
}
