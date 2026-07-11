package com.example.javaquest._23_spring_data_jpa.Lesson03_CrudRepositoryJpaRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Properties;

public class _Lesson03_CrudRepositoryJpaRepository {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: save/find/delete W PRAKTYCE ===");

        /*
         * ============================================================
         * 📦 JAK NAPRAWDE DZIALA save() - PULAPKA "INSERT vs UPDATE"
         * ============================================================
         * `save(entity)` W Spring Data JPA to NIE ZAWSZE INSERT -
         * "pod maska" (`SimpleJpaRepository.save`) sprawdza, CZY encja
         * jest "nowa" (`isNew` - domyslnie: CZY pole `@Id` jest `null`):
         * - `@Id` == `null` -> `entityManager.persist(entity)` (INSERT).
         * - `@Id` != `null` -> `entityManager.merge(entity)` (UPDATE,
         *   powiazanie z `_12_hibernate/Lesson06_CrudOperations`).
         *
         * To OZNACZA: wywolanie `save(...)` NA obiekcie, KTORY JUZ MA
         * ustawione `@Id` (np. pobranym wczesniej Z bazy, ZMODYFIKOWANYM
         * i ZAPISANYM PONOWNIE) NIE tworzy NOWEGO wiersza - AKTUALIZUJE
         * istniejacy.
         */
        System.out.println("save(entity) = INSERT (persist) gdy ID jest null, UPDATE (merge) gdy ID jest juz ustawione - TA SAMA metoda, DWA rozne zachowania.");

        demonstrateSaveDecidesInsertOrUpdateByIdPresence();
        demonstrateDeleteByIdOnMissingEntityThrows();
        demonstrateJpaSpecificBatchOperations();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `save(T entity)` - INSERT gdy `@Id == null`, UPDATE (merge)
         *   gdy `@Id != null` - JEDNA metoda, ZALEZNA OD stanu obiektu.
         * - `deleteById(id)` NA NIEISTNIEJACYM ID - JAVADOC `CrudRepository`
         *   DOPUSZCZA `EmptyResultDataAccessException`, ale ZWERYFIKOWANE
         *   EMPIRYCZNIE na tej wersji Spring Data JPA (Boot 3.4.4):
         *   `deleteById` na BRAKUJACYM ID NIE RZUCA wyjatku (cichy
         *   no-op) - implementacja `SimpleJpaRepository` ZMIENIALA SIE
         *   na przestrzeni wersji, WIEC NIE polegaj NA JAVADOCU bez
         *   testu NA WLASNEJ wersji.
         * - `JpaRepository` DODAJE metody WSADOWE (`saveAllAndFlush`,
         *   `deleteAllInBatch`) - GENERUJA MNIEJ zapytan SQL niz
         *   odpowiedniki Z `CrudRepository` wywolane W PETLI.
         * - Powiazanie Z `_12_hibernate/Lesson06_CrudOperations` -
         *   `persist`/`merge`/`remove` TO DOKLADNIE TE SAME operacje
         *   JPA, TYLKO WYWOLANE ZA CIEBIE przez Spring Data.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    @Entity(name = "Product")
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
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class InsertUpdateApp {
    }

    private static void demonstrateSaveDecidesInsertOrUpdateByIdPresence() throws Exception {
        System.out.println("\n=== save() - INSERT gdy ID puste, UPDATE gdy ID juz ustawione ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson03insertupdate;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(InsertUpdateApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);

            Product newProduct = new Product("Klawiatura", 199.99);
            Product saved = repository.save(newProduct);
            System.out.println("save(nowyProdukt, id=null) -> INSERT, przypisane id=" + saved.id + ", liczba wierszy: " + repository.count());

            saved.price = 179.99;
            repository.save(saved);
            System.out.println("save(istniejacyProdukt, id=" + saved.id + ") -> UPDATE, liczba wierszy WCIAZ: " + repository.count() + " (NIE przybylo nowego wiersza)");

            Product reloaded = repository.findById(saved.id).orElseThrow();
            System.out.println("Cena PO aktualizacji: " + reloaded.price + " (oczekiwane: 179.99)");
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class DeleteApp {
    }

    private static void demonstrateDeleteByIdOnMissingEntityThrows() throws Exception {
        System.out.println("\n=== deleteById() NA NIEISTNIEJACYM ID - ZACHOWANIE ZALEZNE OD WERSJI ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson03delete;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DeleteApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);

            try {
                repository.deleteById(999L);
                System.out.println("deleteById(999) na NIEISTNIEJACYM ID -> NIE rzucilo wyjatku (roznie w zaleznosci od wersji Spring Data)");
            } catch (EmptyResultDataAccessException ex) {
                System.out.println("deleteById(999) na NIEISTNIEJACYM ID -> RZUCILO EmptyResultDataAccessException: " + ex.getMessage());
            }

            Product product = repository.save(new Product("Mysz", 49.99));
            repository.delete(product);
            System.out.println("delete(managedEntity) -> zadnego wyjatku, produkt USUNIETY, liczba wierszy: " + repository.count());
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class BatchApp {
    }

    private static void demonstrateJpaSpecificBatchOperations() throws Exception {
        System.out.println("\n=== METODY WSADOWE SPECYFICZNE DLA JpaRepository ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson03batch;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(BatchApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);

            repository.saveAllAndFlush(java.util.List.of(
                    new Product("Monitor", 899.0),
                    new Product("Podkladka", 39.0),
                    new Product("Kabel", 19.0)));
            System.out.println("saveAllAndFlush(3 produkty) -> liczba wierszy: " + repository.count());

            repository.deleteAllInBatch();
            System.out.println("deleteAllInBatch() -> JEDNO zapytanie DELETE zamiast N osobnych, liczba wierszy PO: " + repository.count());
        } finally {
            context.close();
        }
    }
}
