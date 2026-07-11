package com.example.javaquest._23_spring_data_jpa.Lesson06_PaginationAndSorting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Properties;

public class _Lesson06_PaginationAndSorting {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: Pageable/Sort/Page<T> - AUTOMATYCZNE stronicowanie ===");

        /*
         * ============================================================
         * 📦 TO, CO PISALES RECZNIE W _22_spring_web/Lesson12-13, TERAZ AUTOMATYCZNE
         * ============================================================
         * `_22_spring_web/Lesson12` uczyl RECZNEGO stronicowania
         * (`page`/`size` + wlasny `PageResponse<T>` + `Math.min(...)`
         * NA licie W PAMIECI). Spring Data JPA robi TO SAMO NA POZIOMIE
         * ZAPYTANIA SQL (`LIMIT`/`OFFSET`) - WYDAJNIEJSZE, bo baza
         * zwraca TYLKO potrzebne wiersze, NIE CALA tabele.
         *
         * `Pageable` (typowo `PageRequest.of(numer, rozmiar, sort)`)
         * jako PARAMETR metody repozytorium - Spring Data SAM DOLICZA
         * `LIMIT`/`OFFSET` DO wygenerowanego zapytania.
         */
        System.out.println("Pageable jako parametr metody repozytorium = Spring Data SAM generuje LIMIT/OFFSET w zapytaniu SQL.");

        demonstratePageableWithPageResult();
        demonstrateSortCombinations();
        demonstrateSliceVsPagePerformanceDifference();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Pageable pageable` jako PARAMETR metody -> Spring Data
         *   generuje `LIMIT`/`OFFSET` NA POZIOMIE SQL (NIE W pamieci
         *   Javy, jak RECZNA implementacja Z `_22_spring_web/Lesson12`).
         * - `Page<T>` - ZAWIERA `getTotalElements()`/`getTotalPages()` -
         *   ale WYMAGA DODATKOWEGO zapytania `COUNT(*)`.
         * - `Slice<T>` - BEZ `COUNT(*)` (TYLKO `hasNext()`) -
         *   WYDAJNIEJSZE, gdy NIE potrzebujesz calkowitej liczby stron
         *   (np. "nieskonczone przewijanie").
         * - `Sort.by(...).and(Sort.by(...))` - LACZENIE WIELU kryteriow
         *   sortowania.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
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
            return name + " (" + price + " zl)";
        }
    }

    interface ProductRepository extends JpaRepository<Product, Long> {
        Page<Product> findByCategory(String category, Pageable pageable);

        Slice<Product> findSliceByCategory(String category, Pageable pageable);
    }

    static void seedManyProducts(JpaRepository<Product, Long> repository) {
        for (int i = 1; i <= 25; i++) {
            repository.save(new Product("Produkt " + i, "Elektronika", 100.0 + i));
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class PageableApp {
    }

    private static void demonstratePageableWithPageResult() throws Exception {
        System.out.println("\n=== Page<T> - content + totalElements + totalPages ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson06pageable;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(PageableApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedManyProducts(repository);

            Pageable firstPage = PageRequest.of(0, 10);
            Page<Product> page = repository.findByCategory("Elektronika", firstPage);

            System.out.println("Strona 0, rozmiar 10 -> content.size()=" + page.getContent().size() + ", totalElements=" + page.getTotalElements() + ", totalPages=" + page.getTotalPages() + ", hasNext=" + page.hasNext());

            Page<Product> lastPage = repository.findByCategory("Elektronika", PageRequest.of(2, 10));
            System.out.println("Strona 2 (ostatnia) -> content.size()=" + lastPage.getContent().size() + " (25 produktow, 10 na strone -> ostatnia strona ma 5)");
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class SortApp {
    }

    private static void demonstrateSortCombinations() throws Exception {
        System.out.println("\n=== Sort.by(...).and(...) - LACZENIE WIELU kryteriow sortowania ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson06sort;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SortApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedManyProducts(repository);

            Sort sort = Sort.by(Sort.Direction.DESC, "price").and(Sort.by(Sort.Direction.ASC, "name"));
            Pageable pageable = PageRequest.of(0, 5, sort);
            Page<Product> sorted = repository.findByCategory("Elektronika", pageable);
            System.out.println("Sort.by(price DESC).and(name ASC), strona 0 rozmiar 5 -> " + sorted.getContent());
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class SliceApp {
    }

    private static void demonstrateSliceVsPagePerformanceDifference() throws Exception {
        System.out.println("\n=== Slice<T> - BEZ zapytania COUNT(*), TYLKO hasNext() ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson06slice;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.show-sql", "true");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SliceApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedManyProducts(repository);

            Slice<Product> slice = repository.findSliceByCategory("Elektronika", PageRequest.of(0, 10));
            System.out.println("Slice: content.size()=" + slice.getContent().size() + ", hasNext=" + slice.hasNext());
            System.out.println("-> Slice NIE MA 'getTotalElements()'/'getTotalPages()' - Spring Data NIE wykonuje DODATKOWEGO zapytania COUNT(*), WYDAJNIEJSZE dla 'nieskonczonego przewijania'.");
        } finally {
            context.close();
        }
    }
}
