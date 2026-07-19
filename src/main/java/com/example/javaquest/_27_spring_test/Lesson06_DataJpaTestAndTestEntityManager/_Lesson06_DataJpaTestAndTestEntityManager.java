package com.example.javaquest._27_spring_test.Lesson06_DataJpaTestAndTestEntityManager;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson06_DataJpaTestAndTestEntityManager {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: @DataJpaTest i TestEntityManager ===");

        /*
         * ============================================================
         * 📦 @DataJpaTest = TYLKO warstwa JPA - BEZ kontrolerow/security (analogia do Lesson05)
         * ============================================================
         * `@DataJpaTest` LADUJE TYLKO encje + repozytoria Spring Data
         * JPA (`_23_spring_data_jpa`) + IN-MEMORY baze (DOMYSLNIE H2,
         * NAWET jesli projekt MA skonfigurowana INNA baze - Spring
         * Boot PODMIENIA `DataSource` NA embedded, chyba ze JAWNIE
         * wylaczysz `@AutoConfigureTestDatabase(replace = NONE)`).
         * KAZDA metoda testowa DOMYSLNIE dziala W TRANSAKCJI Z
         * automatycznym ROLLBACKIEM PO tescie (Lesson14 pogłębi
         * temat) - baza jest CZYSTA PRZED KAZDYM testem.
         *
         * 🔍 `TestEntityManager` - "surowy" dostep DO warstwy
         * persystencji, OMIJAJACY repozytorium - PRZYDATNY DO
         * PRZYGOTOWANIA danych testowych (`persistAndFlush(...)`)
         * NIEZALEZNIE OD testowanego repozytorium.
         */
        System.out.println("@DataJpaTest = TYLKO JPA (encje+repozytoria) + in-memory baza + auto-rollback PO kazdym tescie. TestEntityManager = surowy dostep DO persystencji.");

        runAndReport(ProductRepositoryDataJpaTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@DataJpaTest` DOMYSLNIE PODMIENIA skonfigurowana baze NA
         *   embedded (H2) - NAWET jesli `application.properties`
         *   wskazuje INNA baze.
         * - `TestEntityManager.persistAndFlush(encja)` - zapisuje I
         *   OD RAZU wymusza flush (widoczne DLA KOLEJNEGO zapytania).
         * - AUTO-ROLLBACK - KAZDA metoda testowa `@Test` dziala W
         *   WLASNEJ transakcji, cofnietej PO zakonczeniu - testy SA
         *   IZOLOWANE OD SIEBIE "z automatu" (powiazanie Z
         *   `_26_integration_testing/Lesson11`).
         * - `@EnableJpaRepositories(considerNestedRepositories = true)`
         *   NADAL potrzebne DLA zagniezdzonych interfejsow repozytoriow
         *   (znana pulapka Z `_23_spring_data_jpa`).
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    @Entity(name = "TestProduct")
    static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        double price;

        Product() {
        }

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByNameContaining(String fragment);
    }

    @SpringBootApplication
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class TestApp {
    }

    // @DataJpaTest NIE MA atrybutu "classes" (w odroznieniu od @WebMvcTest, Lesson05) - SZUKA
    // @SpringBootConfiguration W PAKIECIE testu (TestApp jest w TYM SAMYM pakiecie - wystarczy).
    // WAZNE: caly projekt ma na classpath Flyway (_10_dao/_23_spring_data_jpa) - Spring Boot
    // WTEDY domyslnie ustawia "spring.jpa.hibernate.ddl-auto=none" (zamiast "create-drop" typowego
    // dla embedded bazy), wiec Hibernate NIE tworzy tabeli dla lokalnej encji "TestProduct" tej
    // lekcji - dajac "Table TEST_PRODUCT not found" (zweryfikowane empirycznie). Naprawa: jawne
    // properties wylaczajace Flyway I wlaczajace create-drop TYLKO dla tego testu.
    @DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
    static class ProductRepositoryDataJpaTest {
        @Autowired
        TestEntityManager entityManager;

        @Autowired
        ProductRepository repository;

        @Test
        void repositoryFindsProductsPersistedThroughTestEntityManager() {
            entityManager.persistAndFlush(new Product("Klawiatura mechaniczna", 299.99));
            entityManager.persistAndFlush(new Product("Klawiatura membranowa", 79.99));
            entityManager.persistAndFlush(new Product("Mysz", 49.99));

            List<Product> keyboards = repository.findByNameContaining("Klawiatura");

            assertThat(keyboards).hasSize(2);
            assertThat(repository.count()).isEqualTo(3);
            System.out.println("TestEntityManager zapisal 3 produkty, repozytorium POPRAWNIE znalazlo 2 'Klawiatura*'.");
        }

        @Test
        void eachTestMethodStartsWithCleanDatabaseDueToAutoRollback() {
            // TEN test URUCHAMIA SIE PO powyzszym, ALE baza jest ZNOWU PUSTA - dowod
            // na auto-rollback transakcji MIEDZY metodami testowymi (izolacja "z automatu").
            assertThat(repository.count()).isZero();
            System.out.println("Baza jest PUSTA na starcie TEGO testu - poprzedni test zostal COFNIETY (auto-rollback).");
        }
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
