package com.example.javaquest._23_spring_data_jpa.Lesson05_CustomQueries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Properties;

public class _Lesson05_CustomQueries {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: Custom queries (@Query) ===");

        /*
         * ============================================================
         * 📦 GDY NAZWA METODY NIE WYSTARCZA - @Query
         * ============================================================
         * Lesson04 pokazal "query methods" GENEROWANE Z NAZWY metody -
         * ALE dla ZLOZONYCH zapytan (joiny, agregacje, podzapytania)
         * nazwa metody stalaby sie NIECZYTELNIE DLUGA. `@Query` pozwala
         * napisac zapytanie RECZNIE - JPQL (domyslnie, jak HQL Z
         * `_12_hibernate/Lesson18`) LUB SQL (`nativeQuery = true`,
         * gdy potrzebujesz SKLADNI SPECYFICZNEJ DLA konkretnej bazy).
         *
         * `@Modifying` + `@Query` DLA `UPDATE`/`DELETE` WYMAGA
         * `@Transactional` - BEZ tego Spring RZUCA WYJATEK (zapytania
         * MODYFIKUJACE MUSZA dzialac W KONTEKSCIE transakcji).
         */
        System.out.println("@Query = RECZNE JPQL/SQL, gdy nazwa metody (Lesson04) staje sie ZBYT ZLOZONA lub NIEWYSTARCZAJACA.");

        demonstrateJpqlAndNativeQuery();
        demonstrateModifyingQueryRequiresTransactional();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Query("SELECT ... FROM Encja e WHERE ...")` - JPQL
         *   (operuje NA ENCJACH/polach Javy, NIE na tabelach/kolumnach
         *   SQL) - DOMYSLNY tryb.
         * - `@Query(value = "SELECT ...", nativeQuery = true)` - CZYSTY
         *   SQL (operuje NA TABELACH/kolumnach bazy) - UZYWAJ, gdy
         *   potrzebujesz FUNKCJI SPECYFICZNYCH DLA konkretnej bazy.
         * - `@Param("nazwa")` - parametr NAZWANY (`:nazwa` W zapytaniu) -
         *   CZYTELNIEJSZE niz POZYCYJNE (`?1`).
         * - `@Modifying` + `@Transactional` - WYMAGANE RAZEM DLA
         *   `UPDATE`/`DELETE` - BEZ `@Transactional`, Spring RZUCA
         *   `InvalidDataAccessApiUsageException`.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
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

    interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p WHERE p.category = :category ORDER BY p.price DESC")
        List<Product> findByCategoryCustomJpql(@Param("category") String category);

        @Query(value = "SELECT * FROM product WHERE price > ?1", nativeQuery = true)
        List<Product> findExpensiveNative(double minPrice);

        @Modifying
        @Query("UPDATE Product p SET p.price = p.price * :factor WHERE p.category = :category")
        int applyDiscountToCategory(@Param("category") String category, @Param("factor") double factor);
    }

    static void seedCatalog(JpaRepository<Product, Long> repository) {
        repository.save(new Product("Klawiatura", "Elektronika", 199.99));
        repository.save(new Product("Mysz", "Elektronika", 49.99));
        repository.save(new Product("Monitor", "Elektronika", 899.0));
        repository.save(new Product("Biurko", "Meble", 599.0));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class QueryApp {
    }

    private static void demonstrateJpqlAndNativeQuery() throws Exception {
        System.out.println("\n=== @Query Z JPQL I @Query(nativeQuery = true) Z SQL ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson05jpqlnative;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(QueryApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            System.out.println("@Query JPQL findByCategoryCustomJpql(\"Elektronika\") -> " + repository.findByCategoryCustomJpql("Elektronika"));
            System.out.println("@Query nativeQuery findExpensiveNative(500) -> " + repository.findExpensiveNative(500));
            System.out.println("-> JPQL operuje NA POLACH ENCJI (p.category), SQL natywny NA KOLUMNACH TABELI (price) - RÓZNA skladnia, TEN SAM wynik koncowy.");
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class ModifyingApp {
    }

    private static void demonstrateModifyingQueryRequiresTransactional() throws Exception {
        System.out.println("\n=== @Modifying WYMAGA @Transactional (BEZ TEGO - WYJATEK) ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson05modifying;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ModifyingApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            try {
                repository.applyDiscountToCategory("Elektronika", 0.9);
                System.out.println("applyDiscountToCategory(...) BEZ @Transactional -> NIE zawiodlo (niektore konfiguracje JEDNAK dopuszczaja pojedyncza operacje)");
            } catch (Exception ex) {
                System.out.println("applyDiscountToCategory(...) BEZ @Transactional -> WYJATEK: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            }

            PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            int updated = transactionTemplate.execute(status -> repository.applyDiscountToCategory("Elektronika", 0.9));
            System.out.println("applyDiscountToCategory(...) W JAWNEJ transakcji (TransactionTemplate) -> zaktualizowano " + updated + " wierszy");

            System.out.println("Po rabacie: " + repository.findByCategoryCustomJpql("Elektronika"));
        } finally {
            context.close();
        }
    }
}
