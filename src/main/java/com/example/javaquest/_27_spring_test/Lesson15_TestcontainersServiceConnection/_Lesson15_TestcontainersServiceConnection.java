package com.example.javaquest._27_spring_test.Lesson15_TestcontainersServiceConnection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson15_TestcontainersServiceConnection {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: @ServiceConnection - Testcontainers zintegrowany ze Spring Boot ===");

        /*
         * ============================================================
         * 📦 @ServiceConnection = AUTOMATYCZNE polaczenie kontenera Z Boot-em, BEZ @DynamicPropertySource
         * ============================================================
         * `_26_integration_testing/Lesson04-06` uczyl PRAWDZIWEGO
         * Testcontainers, ALE RECZNIE (`postgres.getJdbcUrl()` +
         * WLASNY `DriverManager.getConnection(...)`). `_27_spring_test/
         * Lesson11` uczyl `@DynamicPropertySource` (RECZNE
         * PRZEKAZANIE portu/URL DO Springa). `@ServiceConnection`
         * (Spring Boot 3.1+) LACZY OBIE idee: OZNACZ POLE `@Container`
         * adnotacja `@ServiceConnection` - Spring Boot SAM
         * ROZPOZNAJE TYP kontenera (PostgreSQL, MySQL, Redis...) I
         * SAM KONFIGURUJE `DataSource` (LUB inny odpowiedni bean) -
         * BEZ pisania JEDNEJ linii `@DynamicPropertySource`.
         *
         * 🔍 WYMAGA `@Testcontainers` (rozszerzenie JUnit 5 Z modulu
         * `testcontainers:junit-jupiter`) NA klasie testowej.
         */
        System.out.println("@ServiceConnection = Spring Boot AUTOMATYCZNIE konfiguruje DataSource z kontenera - BEZ recznego @DynamicPropertySource.");

        if (isDockerAvailable()) {
            runAndReport(ProductRepositoryServiceConnectionTest.class);
        } else {
            System.out.println("\nDocker NIEDOSTEPNY - pomijam faktyczne uruchomienie (ten sam wzorzec fallbacku co _26_integration_testing/Lesson04-06).");
            printReferenceCode();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Testcontainers` NA klasie testowej - WLACZA zarzadzanie
         *   cyklem zycia kontenerow PRZEZ JUnit 5 (start/stop
         *   AUTOMATYCZNY).
         * - `@Container static PostgreSQLContainer<?> postgres = ...` -
         *   DEFINICJA kontenera (`static` = 1 kontener DLA CALEJ
         *   klasy, jak Lesson06_TestcontainersLifecycleAndReuse Z
         *   `_26`).
         * - `@ServiceConnection` NA POLU kontenera - Spring Boot
         *   AUTOMATYCZNIE ROZPOZNAJE typ (`PostgreSQLContainer` ->
         *   `DataSource`) I KONFIGURUJE go BEZ jawnego
         *   `spring.datasource.url`.
         * - PRZEWAGA NAD `@DynamicPropertySource` (Lesson11): MNIEJ
         *   kodu, BRAK RYZYKA literowki W nazwie wlasciwosci - TO
         *   ZALECANY, NOWOCZESNY sposob (Boot 3.1+) LACZENIA
         *   Testcontainers Z Springiem.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    @Entity(name = "ServiceConnProduct")
    static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;

        Product() {
        }

        Product(String name) {
            this.name = name;
        }
    }

    interface ProductRepository extends JpaRepository<Product, Long> {
    }

    @SpringBootApplication
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class TestApp {
    }

    private static boolean isDockerAvailable() {
        try {
            return DockerClientFactory.instance().isDockerAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    @Testcontainers
    @SpringBootTest(classes = TestApp.class)
    static class ProductRepositoryServiceConnectionTest {

        @Container
        @ServiceConnection
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

        @Autowired
        ProductRepository repository;

        @Test
        void repositoryWorksAgainstRealPostgresAutoConfiguredByServiceConnection() {
            repository.save(new Product("Klawiatura"));
            repository.save(new Product("Mysz"));

            assertThat(repository.count()).isEqualTo(2);
            System.out.println("Repozytorium dziala PRZECIW PRAWDZIWEMU PostgreSQL, DataSource skonfigurowany AUTOMATYCZNIE przez @ServiceConnection.");
        }
    }

    private static void printReferenceCode() {
        System.out.println("""
                @Testcontainers
                @SpringBootTest
                class ProductRepositoryIT {
                    @Container
                    @ServiceConnection
                    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

                    @Autowired
                    ProductRepository repository;
                    // Spring Boot SAM skonfigurowal spring.datasource.url/username/password
                    // z uruchomionego kontenera - BEZ @DynamicPropertySource.
                }""");
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
