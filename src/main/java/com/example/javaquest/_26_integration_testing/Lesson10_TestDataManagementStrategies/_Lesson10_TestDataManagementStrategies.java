package com.example.javaquest._26_integration_testing.Lesson10_TestDataManagementStrategies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson10_TestDataManagementStrategies {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: Strategie zarzadzania danymi testowymi (fixtures/buildery) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: reczne budowanie danych testowych ROZRASTA sie I DUBLUJE
         * ============================================================
         * Test integracyjny CZESTO WYMAGA zlozonego stanu poczatkowego
         * (klient + jego zamowienia + produkty NA magazynie). Reczne
         * `new Customer(...)`+`new Order(...)` W KAZDYM tescie
         * PROWADZI DO powtorzenia I kruchosci (dodanie NOWEGO
         * WYMAGANEGO pola LAMIE WSZYSTKIE testy NARAZ). 2 wzorce
         * ROZWIAZUJACE TEN problem:
         * 1. TEST DATA BUILDER (wzorzec Buildera - powiazanie Z
         *    `_02_oop/Lesson15_DesignPatterns`) - fluentne API Z
         *    SENSOWNYMI wartosciami DOMYSLNYMI, NADPISYWANYMI TYLKO
         *    TAM, gdzie test TEGO WYMAGA.
         * 2. FIXTURE/SEEDER - GOTOWA metoda/klasa WSTAWIAJACA znany
         *    zestaw danych DO bazy PRZED testem (np. "3 produkty +
         *    1 klient Z zamowieniem").
         */
        System.out.println("Test Data Builder (fluentne API Z wartosciami domyslnymi) + Fixture/Seeder (gotowy zestaw danych w bazie) = mniej powtorzenia w testach.");

        demonstrateTestDataBuilder();
        demonstrateDatabaseSeeder();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Builder DAJE testowi CZYTELNOSC ("customer Z limitem
         *   kredytowym 0" zamiast 6 parametrow konstruktora) I
         *   ODPORNOSC NA zmiany (NOWE pole = 1 miejsce zmiany W
         *   builderze, NIE W KAZDYM tescie).
         * - Seeder/Fixture ODDZIELA "przygotowanie swiata" OD
         *   "logiki testu" - test CZYTA sie jako: PRZYGOTUJ dane ->
         *   WYWOLAJ akcje -> SPRAWDZ wynik (AAA Z
         *   `_25_unit_testing/Lesson18`).
         * - UNIKAJ WSPOLDZIELONYCH, "magicznych" danych testowych
         *   (np. "zawsze ID=1") MIEDZY testami - KAZDY test
         *   POWINIEN generowac WLASNE, UNIKALNE dane (zapowiedz
         *   Lesson11_TestIsolation).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    record Customer(String id, String name, String email, int loyaltyPoints) {
    }

    /** Test Data Builder - fluentne API Z SENSOWNYMI wartosciami domyslnymi. */
    static class CustomerTestDataBuilder {
        private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

        private String id = "CUST-" + SEQUENCE.getAndIncrement();
        private String name = "Domyslny Klient";
        private String email = "klient@example.com";
        private int loyaltyPoints = 0;

        static CustomerTestDataBuilder aCustomer() {
            return new CustomerTestDataBuilder();
        }

        CustomerTestDataBuilder withName(String name) {
            this.name = name;
            return this;
        }

        CustomerTestDataBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        CustomerTestDataBuilder withLoyaltyPoints(int points) {
            this.loyaltyPoints = points;
            return this;
        }

        Customer build() {
            return new Customer(id, name, email, loyaltyPoints);
        }
    }

    private static void demonstrateTestDataBuilder() {
        System.out.println("\n--- Test Data Builder - CZYTELNE tworzenie danych testowych ---");

        // Test 1: NIE obchodza mnie SZCZEGOLY klienta - uzywam wartosci domyslnych.
        Customer anyCustomer = CustomerTestDataBuilder.aCustomer().build();
        assertThat(anyCustomer.name()).isEqualTo("Domyslny Klient");

        // Test 2: interesuje mnie TYLKO liczba punktow lojalnosciowych - RESZTA jest domyslna.
        Customer vipCustomer = CustomerTestDataBuilder.aCustomer()
                .withLoyaltyPoints(5000)
                .build();
        assertThat(vipCustomer.loyaltyPoints()).isEqualTo(5000);
        assertThat(vipCustomer.name()).isEqualTo("Domyslny Klient");

        // Test 3: buduje PELNI dostosowanego klienta.
        Customer fullyCustomized = CustomerTestDataBuilder.aCustomer()
                .withName("Jan Kowalski")
                .withEmail("jan.kowalski@example.com")
                .withLoyaltyPoints(100)
                .build();
        assertThat(fullyCustomized.name()).isEqualTo("Jan Kowalski");

        // KAZDY builder wygenerowal UNIKALNE ID - brak kolizji miedzy "testami".
        assertThat(anyCustomer.id()).isNotEqualTo(vipCustomer.id()).isNotEqualTo(fullyCustomized.id());
        System.out.println("3 rozne klientow zbudowane fluentnym API, kazdy Z UNIKALNYM ID: "
                + anyCustomer.id() + ", " + vipCustomer.id() + ", " + fullyCustomized.id());
    }

    /** Fixture/Seeder - GOTOWY zestaw danych wstawiony DO PRAWDZIWEJ bazy PRZED testem. */
    static class TestDataSeeder {
        private final Connection connection;

        TestDataSeeder(Connection connection) {
            this.connection = connection;
        }

        void seedProductCatalog() throws SQLException {
            try (var statement = connection.createStatement()) {
                statement.execute("CREATE TABLE catalog_products (id INT PRIMARY KEY, name VARCHAR(100), stock INT)");
                statement.execute("INSERT INTO catalog_products VALUES (1, 'Klawiatura', 10)");
                statement.execute("INSERT INTO catalog_products VALUES (2, 'Mysz', 25)");
                statement.execute("INSERT INTO catalog_products VALUES (3, 'Monitor', 3)");
            }
        }
    }

    private static void demonstrateDatabaseSeeder() throws Exception {
        System.out.println("\n--- Fixture/Seeder - znany zestaw danych w PRAWDZIWEJ bazie PRZED testem ---");
        String jdbcUrl = "jdbc:h2:mem:lesson10;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")) {
            new TestDataSeeder(connection).seedProductCatalog();

            // Test CZYTA sie teraz jako: dane JUZ SA (seed), TYLKO sprawdzam logike.
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM catalog_products WHERE stock < 5");
                 ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int lowStockCount = resultSet.getInt(1);
                assertThat(lowStockCount).isEqualTo(1);
                System.out.println("Po zasianiu (seed) 3 produktow, dokladnie " + lowStockCount + " ma niski stan magazynowy (< 5).");
            }
        }
    }
}
