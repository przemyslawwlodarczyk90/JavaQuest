package com.example.javaquest._26_integration_testing.Lesson11_TestIsolationAndIdempotency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson11_TestIsolationAndIdempotency {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: Izolacja testow i idempotentnosc ===");

        /*
         * ============================================================
         * 📦 IZOLACJA: test NIE MOZE "widziec" danych z INNEGO testu
         * ============================================================
         * Lesson02 wspomnial IZOLACJE jako 1 Z 4 wyzwan. TU
         * pokazujemy KONKRETNE techniki: (1) UNIKALNE dane NA test
         * (UUID/sekwencja - Lesson10 juz to pokazal buildersem), (2)
         * CZYSZCZENIE stanu MIEDZY testami (TRUNCATE/DELETE), (3)
         * IDEMPOTENTNOSC - test URUCHOMIONY 2x Z RZEDU (BEZ czyszczenia
         * MIEDZY) MUSI dac TEN SAM wynik - JESLI NIE, test ZALEZY OD
         * "poprzedniego stanu swiata", CO jest ZRODLEM "flaky" bledow
         * (Lesson12).
         */
        System.out.println("Izolacja = testy NIE 'widza' swoich danych. Idempotentnosc = URUCHOMIONY 2x test daje TEN SAM wynik.");

        demonstrateIsolationProblem();
        demonstrateIsolationFix();
        demonstrateIdempotency();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PROBLEM: test A wstawia wiersz "o ID=1", test B ZAKLADA
         *   "jest DOKLADNIE 1 wiersz W tabeli" - PRZY URUCHOMIENIU
         *   RAZEM test B PADA (widzi TEZ wiersz OD A).
         * - NAPRAWA 1 (UNIKALNE dane): KAZDY test uzywa
         *   `UUID.randomUUID()`/dedykowanego prefiksu ZAMIAST stalego
         *   ID - testy NIE KOLIDUJA, NAWET dzielac TABELE.
         * - NAPRAWA 2 (czyszczenie): `TRUNCATE TABLE`/`DELETE FROM`
         *   W `@BeforeEach` - KAZDY test zaczyna OD zera (kosztem
         *   szybkosci - Lesson02).
         * - IDEMPOTENTNOSC: test SPRAWDZAJACY "ILE jest wierszy PO
         *   akcji" (LICZBA WZGLEDNA: +1) jest BARDZIEJ odporny NIZ
         *   "jest DOKLADNIE N wierszy" (LICZBA BEZWZGLEDNA) - TA
         *   DRUGA forma zaklada CZYSTY STAN POCZATKOWY.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateIsolationProblem() throws Exception {
        System.out.println("\n--- PROBLEM: dane 'przeciekaja' miedzy testami dzielacymi baze ---");
        String jdbcUrl = "jdbc:h2:mem:lesson11-shared;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tasks (id VARCHAR(50) PRIMARY KEY, title VARCHAR(100))");

            simulateTestInsertingFixedId(connection, "1", "Zadanie od 'testu A'");
            // "test B" ZAKLADA, ze W TABELI jest DOKLADNIE JEGO 1 wiersz - ALE test A JUZ TAM COS wstawil.
            simulateTestInsertingFixedId(connection, "2", "Zadanie od 'testu B'");

            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM tasks")) {
                resultSet.next();
                int totalRows = resultSet.getInt(1);
                System.out.println("Po 'tescie A' i 'tescie B' W TABELI jest " + totalRows + " wiersze (test B, gdyby zakladal '1 wiersz', ZAWIODLBY).");
            }
        }
    }

    private static void simulateTestInsertingFixedId(Connection connection, String fixedId, String title) throws Exception {
        try (var statement = connection.prepareStatement("INSERT INTO tasks VALUES (?, ?)")) {
            statement.setString(1, fixedId);
            statement.setString(2, title);
            statement.executeUpdate();
        }
    }

    private static void demonstrateIsolationFix() throws Exception {
        System.out.println("\n--- NAPRAWA: UNIKALNE ID (UUID) - ZERO kolizji, nawet na WSPOLDZIELONEJ tabeli ---");
        String jdbcUrl = "jdbc:h2:mem:lesson11-isolated;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tasks (id VARCHAR(50) PRIMARY KEY, title VARCHAR(100))");

            String taskIdFromTestA = simulateIsolatedTest(connection, "Zadanie od 'testu A'");
            String taskIdFromTestB = simulateIsolatedTest(connection, "Zadanie od 'testu B'");

            assertThat(taskIdFromTestA).isNotEqualTo(taskIdFromTestB);

            // KAZDY "test" sprawdza TYLKO WLASNY wiersz (PO WLASNYM, UNIKALNYM ID) - IZOLACJA zachowana,
            // NIEZALEZNIE OD tego, ILE INNYCH testow JUZ wstawilo dane DO tej samej tabeli.
            try (var query = connection.prepareStatement("SELECT title FROM tasks WHERE id = ?")) {
                query.setString(1, taskIdFromTestA);
                try (ResultSet resultSet = query.executeQuery()) {
                    resultSet.next();
                    assertThat(resultSet.getString("title")).isEqualTo("Zadanie od 'testu A'");
                }
            }
            System.out.println("Oba 'testy' zweryfikowaly WYLACZNIE WLASNE dane (PO UNIKALNYM UUID), BEZ interferencji.");
        }
    }

    private static String simulateIsolatedTest(Connection connection, String title) throws Exception {
        String id = UUID.randomUUID().toString();
        try (var statement = connection.prepareStatement("INSERT INTO tasks VALUES (?, ?)")) {
            statement.setString(1, id);
            statement.setString(2, title);
            statement.executeUpdate();
        }
        return id;
    }

    private static void demonstrateIdempotency() throws Exception {
        System.out.println("\n--- IDEMPOTENTNOSC: test URUCHOMIONY 2x Z RZEDU (bez czyszczenia) daje TEN SAM wniosek ---");
        String jdbcUrl = "jdbc:h2:mem:lesson11-idempotent;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tasks (id VARCHAR(50) PRIMARY KEY, title VARCHAR(100))");

            // ZLA wersja (KOMENTARZ): "assertThat(countRows(connection)).isEqualTo(1)" ZALEZALOBY OD
            // tego, ILE RAZY test JUZ sie wczesniej wykonal na TEJ SAMEJ (dzielonej) bazie.

            // DOBRA wersja: sprawdzamy PRZYROST (+1), NIE liczbe BEZWZGLEDNA - test jest
            // IDEMPOTENTNY, DZIALA POPRAWNIE NIEZALEZNIE OD tego, ILE razy zostal juz uruchomiony.
            for (int run = 1; run <= 3; run++) {
                int before = countRows(connection);
                simulateIsolatedTest(connection, "Powtorzenie #" + run);
                int after = countRows(connection);

                assertThat(after).isEqualTo(before + 1);
                System.out.println("Uruchomienie #" + run + ": " + before + " -> " + after + " wierszy (przyrost +1, ZAWSZE poprawny).");
            }
        }
    }

    private static int countRows(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM tasks")) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
}
