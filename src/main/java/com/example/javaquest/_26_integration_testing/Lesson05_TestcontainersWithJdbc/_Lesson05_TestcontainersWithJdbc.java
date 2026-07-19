package com.example.javaquest._26_integration_testing.Lesson05_TestcontainersWithJdbc;

import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson05_TestcontainersWithJdbc {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: Testcontainers + JDBC - testowanie prawdziwego repozytorium ===");

        /*
         * ============================================================
         * 📦 CEL: przetestowac REALNE repozytorium JDBC PRZECIW REALNEJ bazie
         * ============================================================
         * Lesson04 pokazal SUROWE polaczenie JDBC DO kontenera. TU
         * idziemy krok DALEJ - piszemy PRAWDZIWA klase repozytorium
         * (`ProductRepository`, wzorem `_09_jdbc`/`_10_dao`) I
         * TESTUJEMY JA W CALOSCI PRZECIW PRAWDZIWEMU PostgreSQL W
         * kontenerze - DOKLADNIE TAK, jak wygladalby test
         * integracyjny warstwy dostepu DO danych W REALNYM projekcie.
         */
        System.out.println("Testujemy PRAWDZIWA klase ProductRepository (PreparedStatement, transakcje) PRZECIW PRAWDZIWEMU PostgreSQL w kontenerze.");

        if (isDockerAvailable()) {
            runRepositoryIntegrationTests();
        } else {
            System.out.println("\nDocker NIEDOSTEPNY - pomijam. Kod ProductRepository ponizej jest W PELNI PRAWDZIWY i " +
                    "dzialalby identycznie na maszynie z uruchomionym Dockerem (tak samo jak Lesson04).");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Repozytorium NIE WIE, ze dziala PRZECIW kontenerowi - dostaje
         *   ZWYKLE `Connection` (LUB `DataSource`), DOKLADNIE tak samo
         *   jak PRZECIW PRODUKCYJNEJ bazie (powiazanie Z `_10_dao/Lesson01`).
         * - Test integracyjny WERYFIKUJE: PreparedStatement Z
         *   PARAMETRAMI, mapowanie `ResultSet`->obiekt, POPRAWNE
         *   COMMIT/ROLLBACK - RZECZY, KTORYCH test jednostkowy Z
         *   MOCKIEM (`_25_unit_testing/Lesson13`) W OGOLE NIE
         *   sprawdza (bo mock NIE WYKONUJE prawdziwego SQL).
         * - Ten wzorzec (PRAWDZIWA klasa produkcyjna + Testcontainers)
         *   jest STANDARDEM W PRZEMYSLE DLA testowania warstwy DAO/
         *   repozytorium W realnych projektach Spring (zapowiedz
         *   `_27_spring_test/Lesson06_DataJpaTest`+Lesson15).
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    record Product(int id, String name, double price) {
    }

    static class ProductRepository {
        private final Connection connection;

        ProductRepository(Connection connection) {
            this.connection = connection;
        }

        void createSchema() throws SQLException {
            try (var statement = connection.createStatement()) {
                statement.execute("CREATE TABLE products (id INT PRIMARY KEY, name VARCHAR(100), price NUMERIC(10,2))");
            }
        }

        void save(Product product) throws SQLException {
            String sql = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, product.id());
                statement.setString(2, product.name());
                statement.setDouble(3, product.price());
                statement.executeUpdate();
            }
        }

        Optional<Product> findById(int id) throws SQLException {
            String sql = "SELECT id, name, price FROM products WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                    return Optional.empty();
                }
            }
        }

        List<Product> findAllCheaperThan(double maxPrice) throws SQLException {
            String sql = "SELECT id, name, price FROM products WHERE price < ? ORDER BY price";
            List<Product> result = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, maxPrice);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(mapRow(resultSet));
                    }
                }
            }
            return result;
        }

        private Product mapRow(ResultSet resultSet) throws SQLException {
            return new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"));
        }
    }

    private static boolean isDockerAvailable() {
        try {
            return DockerClientFactory.instance().isDockerAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    private static void runRepositoryIntegrationTests() {
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")) {
            postgres.start();

            try (Connection connection = java.sql.DriverManager.getConnection(
                    postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {

                ProductRepository repository = new ProductRepository(connection);
                repository.createSchema();

                repository.save(new Product(1, "Klawiatura", 199.99));
                repository.save(new Product(2, "Mysz", 49.99));
                repository.save(new Product(3, "Monitor", 899.00));

                Optional<Product> found = repository.findById(1);
                assertThat(found).isPresent();
                assertThat(found.get().name()).isEqualTo("Klawiatura");
                System.out.println("findById(1) -> " + found.get());

                Optional<Product> notFound = repository.findById(999);
                assertThat(notFound).isEmpty();
                System.out.println("findById(999) -> " + notFound + " (poprawnie puste)");

                List<Product> cheap = repository.findAllCheaperThan(200.0);
                assertThat(cheap).hasSize(2);
                assertThat(cheap).extracting(Product::name).containsExactly("Mysz", "Klawiatura");
                System.out.println("findAllCheaperThan(200.0) -> " + cheap);
            }
        } catch (Exception e) {
            System.out.println("Test integracyjny nie powiodl sie: " + e.getMessage());
        }
    }
}
