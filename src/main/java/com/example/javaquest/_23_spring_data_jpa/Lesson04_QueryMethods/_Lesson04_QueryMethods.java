package com.example.javaquest._23_spring_data_jpa.Lesson04_QueryMethods;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Properties;

public class _Lesson04_QueryMethods {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: Query methods (zapytania Z NAZWY metody) ===");

        /*
         * ============================================================
         * 📦 ZAPYTANIA GENEROWANE Z NAZWY METODY - "MAGIA" Spring Data
         * ============================================================
         * Spring Data PARSUJE NAZWE metody interfejsu (`findByCategory`,
         * `countByPriceGreaterThan`) I GENERUJE Z NIEJ zapytanie JPQL
         * AUTOMATYCZNIE - ZERO adnotacji, ZERO SQL/HQL napisanego
         * recznie (kontrast Z `_12_hibernate/Lesson18_HqlBasics`, gdzie
         * KAZDE zapytanie pisales SAM).
         *
         * Skladnia: `findBy` + NAZWA POLA + OPCJONALNY operator
         * (`GreaterThan`/`Containing`/`In`/`IsNull`...) + OPCJONALNIE
         * `And`/`Or` LACZACE WIELE warunkow.
         */
        System.out.println("findByXxx(...) = Spring PARSUJE nazwe metody i GENERUJE zapytanie JPQL SAM - bez pisania HQL/SQL.");

        demonstrateBasicAndCombinedConditions();
        demonstrateComparisonAndContainsOperators();
        demonstrateOrderingLimitingAndCountingVariants();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `findByPole(...)` - rowne (`=`).
         * - `findByPoleAndInnePole(...)` / `...Or...` - LACZENIE
         *   warunkow.
         * - `findByPoleGreaterThan/LessThan/Between(...)` - porownania
         *   liczbowe/dat.
         * - `findByPoleContaining/StartingWith/EndingWith(...)` - `LIKE`.
         * - `findByPoleIgnoreCase(...)` - NIEWRAZLIWE na wielkosc liter.
         * - `findTopNByOrderByPoleDesc(...)` - LIMIT + sortowanie.
         * - `countByPole(...)`/`existsByPole(...)`/`deleteByPole(...)` -
         *   TA SAMA skladnia, INNY prefiks = INNA OPERACJA.
         * - Nastepna lekcja (`Lesson05_CustomQueries`) pokaze, CO
         *   ZROBIC, gdy zapytanie jest ZBYT ZLOZONE DLA samej nazwy
         *   metody - `@Query` Z RECZNYM JPQL/SQL.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    @Entity(name = "Product")
    static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        String category;
        double price;
        boolean inStock;

        Product() {
        }

        Product(String name, String category, double price, boolean inStock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.inStock = inStock;
        }

        @Override
        public String toString() {
            return name + " (" + category + ", " + price + " zl, dostepny=" + inStock + ")";
        }
    }

    interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByCategory(String category);

        List<Product> findByCategoryAndInStock(String category, boolean inStock);

        List<Product> findByCategoryOrPriceGreaterThan(String category, double price);
    }

    static void seedCatalog(JpaRepository<Product, Long> repository) {
        repository.save(new Product("Klawiatura", "Elektronika", 199.99, true));
        repository.save(new Product("Mysz", "Elektronika", 49.99, false));
        repository.save(new Product("Monitor", "Elektronika", 899.0, true));
        repository.save(new Product("Biurko", "Meble", 599.0, true));
        repository.save(new Product("Krzeslo", "Meble", 349.0, false));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class BasicApp {
    }

    private static void demonstrateBasicAndCombinedConditions() throws Exception {
        System.out.println("\n=== findByPole / findByPoleAndInnePole / findByPoleOrInnePole ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson04basic;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(BasicApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            System.out.println("findByCategory(\"Meble\") -> " + repository.findByCategory("Meble"));
            System.out.println("findByCategoryAndInStock(\"Elektronika\", true) -> " + repository.findByCategoryAndInStock("Elektronika", true));
            System.out.println("findByCategoryOrPriceGreaterThan(\"Meble\", 800) -> " + repository.findByCategoryOrPriceGreaterThan("Meble", 800));
        } finally {
            context.close();
        }
    }

    interface ComparisonRepository extends JpaRepository<Product, Long> {
        List<Product> findByPriceGreaterThan(double price);

        List<Product> findByPriceBetween(double min, double max);

        List<Product> findByNameContainingIgnoreCase(String fragment);

        List<Product> findByCategoryIn(List<String> categories);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class ComparisonApp {
    }

    private static void demonstrateComparisonAndContainsOperators() throws Exception {
        System.out.println("\n=== GreaterThan / Between / Containing IgnoreCase / In ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson04comparison;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ComparisonApp.class)
                .properties(props)
                .run();
        try {
            ComparisonRepository repository = context.getBean(ComparisonRepository.class);
            seedCatalog(repository);

            System.out.println("findByPriceGreaterThan(500) -> " + repository.findByPriceGreaterThan(500));
            System.out.println("findByPriceBetween(100, 400) -> " + repository.findByPriceBetween(100, 400));
            System.out.println("findByNameContainingIgnoreCase(\"MYSZ\") -> " + repository.findByNameContainingIgnoreCase("MYSZ"));
            System.out.println("findByCategoryIn([\"Meble\"]) -> " + repository.findByCategoryIn(List.of("Meble")));
        } finally {
            context.close();
        }
    }

    interface OrderingRepository extends JpaRepository<Product, Long> {
        List<Product> findTop2ByOrderByPriceDesc();

        long countByCategory(String category);

        boolean existsByName(String name);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class OrderingApp {
    }

    private static void demonstrateOrderingLimitingAndCountingVariants() throws Exception {
        System.out.println("\n=== findTopN...OrderBy... / countBy... / existsBy... ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson04ordering;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(OrderingApp.class)
                .properties(props)
                .run();
        try {
            OrderingRepository repository = context.getBean(OrderingRepository.class);
            seedCatalog(repository);

            System.out.println("findTop2ByOrderByPriceDesc() -> " + repository.findTop2ByOrderByPriceDesc());
            System.out.println("countByCategory(\"Elektronika\") -> " + repository.countByCategory("Elektronika"));
            System.out.println("existsByName(\"Biurko\") -> " + repository.existsByName("Biurko"));
            System.out.println("existsByName(\"Nieistniejacy\") -> " + repository.existsByName("Nieistniejacy"));
        } finally {
            context.close();
        }
    }
}
