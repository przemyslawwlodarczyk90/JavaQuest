package com.example.javaquest._23_spring_data_jpa.Lesson12_Specifications;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class _Lesson12_Specifications {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: Specification - DYNAMICZNE zapytania Z KRYTERIOW ===");

        /*
         * ============================================================
         * 📦 KIEDY @Query NIE WYSTARCZA - ZBYT WIELE OPCJONALNYCH FILTROW
         * ============================================================
         * `_22_spring_web/Lesson13_SortingAndFiltering` uczyl RECZNEGO
         * budowania `Predicate<T>` (java.util.function) W PAMIECI -
         * `Specification<T>` to TEN SAM POMYSL, ale NA POZIOMIE SQL
         * (Criteria API, powiazanie z `_12_hibernate/Lesson20`) - Spring
         * Data KONWERTUJE `Specification<T>` NA WARUNEK `WHERE` W
         * zapytaniu, NIE W pamieci Javy.
         *
         * Przydatne, gdy MASZ WIELE opcjonalnych filtrow (np. formularz
         * wyszukiwania Z 5+ polami, Z KTORYCH kazde MOZE, ale NIE MUSI,
         * byc wypelnione) - `@Query` Z 5 opcjonalnymi warunkami
         * (`AND (:x IS NULL OR pole = :x)`) staje sie NIECZYTELNE.
         */
        System.out.println("Specification<T> = Predicate<T> z _22_spring_web/Lesson13, ale NA POZIOMIE SQL (Criteria API), NIE W pamieci.");

        demonstrateSingleSpecification();
        demonstrateCombiningSpecificationsDynamically();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Specification<T>` - INTERFEJS FUNKCYJNY zwracajacy
         *   `Predicate` (Criteria API) NA PODSTAWIE `Root<T>`/
         *   `CriteriaBuilder`.
         * - Repozytorium MUSI dziedziczyc TEZ PO
         *   `JpaSpecificationExecutor<T>` (OBOK `JpaRepository<T, ID>`)
         *   - DOPIERO WTEDY dostepne SA metody `findAll(Specification)`.
         * - `Specification.where(...).and(...).or(...)` - LACZENIE
         *   WIELU kryteriow DYNAMICZNIE, W ZALEZNOSCI OD tego, KTORE
         *   parametry SA OBECNE.
         * - Efekt: JEDNO zapytanie SQL Z WARUNKAMI DOPASOWANYMI DO
         *   FAKTYCZNIE podanych filtrow - BEZ pisania DZIESIATEK
         *   RECZNYCH kombinacji `@Query`.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    @Entity(name = "Product")
    static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        String category;
        double price;

        Product() {
        }

        Product(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " (" + category + ", " + price + " zl)";
        }
    }

    interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    }

    static class ProductSpecifications {
        static Specification<Product> hasCategory(String category) {
            return (root, query, cb) -> cb.equal(root.get("category"), category);
        }

        static Specification<Product> priceGreaterThanOrEqual(double minPrice) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        }

        static Specification<Product> nameContains(String fragment) {
            return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + fragment.toLowerCase() + "%");
        }
    }

    static void seedCatalog(JpaRepository<Product, Long> repository) {
        repository.save(new Product("Klawiatura", "Elektronika", 199.99));
        repository.save(new Product("Mysz", "Elektronika", 49.99));
        repository.save(new Product("Monitor", "Elektronika", 899.0));
        repository.save(new Product("Biurko", "Meble", 599.0));
        repository.save(new Product("Krzeslo", "Meble", 349.0));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class SingleSpecApp {
    }

    private static void demonstrateSingleSpecification() throws Exception {
        System.out.println("\n=== POJEDYNCZA Specification ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson12single;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SingleSpecApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            List<Product> electronics = repository.findAll(ProductSpecifications.hasCategory("Elektronika"));
            System.out.println("Specification hasCategory(\"Elektronika\") -> " + electronics);
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class CombiningApp {
    }

    static Specification<Product> buildDynamicSpecification(Optional<String> category, Optional<Double> minPrice, Optional<String> nameFragment) {
        // Budujemy Specification DYNAMICZNIE - TYLKO OBECNE filtry SA dolaczane - dokladnie jak Predicate.and(...) z _22_spring_web/Lesson13.
        Specification<Product> spec = Specification.where(null);

        if (category.isPresent()) {
            spec = spec.and(ProductSpecifications.hasCategory(category.get()));
        }
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.priceGreaterThanOrEqual(minPrice.get()));
        }
        if (nameFragment.isPresent()) {
            spec = spec.and(ProductSpecifications.nameContains(nameFragment.get()));
        }
        return spec;
    }

    private static void demonstrateCombiningSpecificationsDynamically() throws Exception {
        System.out.println("\n=== DYNAMICZNE LACZENIE Specification - TYLKO OBECNE filtry ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson12combining;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CombiningApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            Specification<Product> onlyCategory = buildDynamicSpecification(Optional.of("Meble"), Optional.empty(), Optional.empty());
            System.out.println("Filtr: TYLKO category=Meble -> " + repository.findAll(onlyCategory));

            Specification<Product> categoryAndPrice = buildDynamicSpecification(Optional.of("Elektronika"), Optional.of(100.0), Optional.empty());
            System.out.println("Filtr: category=Elektronika AND price>=100 -> " + repository.findAll(categoryAndPrice));

            Specification<Product> allThree = buildDynamicSpecification(Optional.of("Elektronika"), Optional.of(50.0), Optional.of("moni"));
            System.out.println("Filtr: category=Elektronika AND price>=50 AND name~'moni' -> " + repository.findAll(allThree));
        } finally {
            context.close();
        }
    }
}
