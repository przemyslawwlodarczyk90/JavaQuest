package com.example.javaquest._26_integration_testing.Lesson15_IntegrationTestingBestPractices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson15_IntegrationTestingBestPractices {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: Dobre praktyki testow integracyjnych - podsumowanie ===");

        /*
         * ============================================================
         * 📦 7 zasad Z CALEGO rozdzialu - JEDNO miejsce, ZEBY je zobaczyc RAZEM
         * ============================================================
         * 1. TESTUJ GRANICE, NIE WSZYSTKO (Lesson01) - test integracyjny
         *    NA KAZDA zaleznosc = ZBYT WOLNO, TESTUJ TYLKO miejsca,
         *    GDZIE komponenty sie STYKAJA.
         * 2. IZOLUJ dane (Lesson11) - UNIKALNE ID, BEZ WSPOLDZIELONEGO
         *    stanu miedzy testami.
         * 3. SPRZATAJ ZAWSZE (Lesson02/09) - try/finally NIEZALEZNIE
         *    OD wyniku testu.
         * 4. UNIKAJ sztywnego `sleep` (Lesson12) - POLLING Z
         *    timeoutem.
         * 5. STUBUJ zewnetrzne API (Lesson07-08), NIE laczmy sie Z
         *   PRAWDZIWYM internetem W testach.
         * 6. UZYWAJ buildera/seedera (Lesson10) DLA CZYTELNYCH danych
         *    testowych.
         * 7. SEGREGUJ etapami CI (Lesson14) - "fast" CZESTO,
         *    "integration" RZADZIEJ.
         */
        System.out.println("7 zasad: testuj GRANICE, izoluj dane, ZAWSZE sprzataj, unikaj sleep, stubuj zewnetrzne API, uzywaj buildera, segreguj etapami CI.");

        demonstrateAntiPatternVsBestPractice();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - lista kontrolna PRZED napisaniem testu integracyjnego
         * ============================================================
         * [ ] Czy TA zaleznosc FAKTYCZNIE wymaga testu integracyjnego,
         *     CZY test jednostkowy Z mockiem (`_25_unit_testing`)
         *     WYSTARCZY?
         * [ ] Czy dane testowe SA IZOLOWANE (UUID/prefiks)?
         * [ ] Czy WSZYSTKIE zasoby (baza/plik/serwer) SA sprzatane W
         *     `finally`?
         * [ ] Czy test jest DETERMINISTYCZNY (BEZ sztywnego sleep,
         *     BEZ zaleznosci OD "teraz")?
         * [ ] Czy zewnetrzne API SA STUBOWANE (WireMock), NIE
         *     PRAWDZIWE?
         * [ ] Czy test jest OTAGOWANY poprawnie (`@Tag("integration")`)
         *     DLA WLASCIWEGO etapu CI?
         */
        System.out.println("\n=== KONIEC LEKCJI 15, KONIEC ROZDZIALU (przed kapsztonem) ===");
    }

    private static void demonstrateAntiPatternVsBestPractice() throws Exception {
        System.out.println("\n--- PRZED/PO: test integracyjny repozytorium zamowien ---");

        System.out.println("PRZED (anty-wzorzec - NIE rob tak): reczny SQL, staly ID='1', BRAK sprzatania,");
        System.out.println("       `Thread.sleep(500)` 'na wszelki wypadek', zaleznosc OD kolejnosci innych testow.");

        System.out.println("\nPO (dobra praktyka - demo ponizej): builder Z UNIKALNYM ID, try/finally, ZERO sleep.");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:lesson15;DB_CLOSE_DELAY=-1", "sa", "");
        try {
            try (var statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS orders (id VARCHAR(50) PRIMARY KEY, total DECIMAL(10,2))");
            }

            String orderId = UUID.randomUUID().toString();
            try (var insert = connection.prepareStatement("INSERT INTO orders VALUES (?, ?)")) {
                insert.setString(1, orderId);
                insert.setDouble(2, 149.99);
                insert.executeUpdate();
            }

            try (var query = connection.prepareStatement("SELECT total FROM orders WHERE id = ?")) {
                query.setString(1, orderId);
                try (var resultSet = query.executeQuery()) {
                    resultSet.next();
                    assertThat(resultSet.getDouble("total")).isEqualTo(149.99);
                }
            }
            System.out.println("Test zakonczony DETERMINISTYCZNIE, BEZ sleep, Z IZOLOWANYM ID: " + orderId);
        } finally {
            connection.close();
            System.out.println("Zasob (polaczenie) POPRAWNIE zamkniety W finally.");
        }
    }
}
