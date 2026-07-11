package com.example.javaquest._23_spring_data_jpa.Lesson11_Projections;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Properties;

public class _Lesson11_Projections {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: Projekcje - TYLKO potrzebne pola, NIE cala encja ===");

        /*
         * ============================================================
         * 📦 PROJEKCJE - MNIEJ DANYCH Z BAZY, GDY NIE POTRZEBUJESZ CALEJ ENCJI
         * ============================================================
         * KAZDA metoda repozytorium DOTAD zwracala PELNA encje - CZASEM
         * potrzebujesz TYLKO 2-3 pola (np. liste "nazwa+cena" DO
         * rozwijanej listy W UI). Projekcje POZWALAJA repozytorium
         * zwrocic MNIEJ danych, CZESTO Z WYDAJNIEJSZYM zapytaniem SQL
         * (SELECT TYLKO potrzebnych kolumn, NIE `SELECT *`).
         *
         * 3 rodzaje:
         * 1) INTERFEJS ZAMKNIETY - metody `getXxx()` ODPOWIADAJACE
         *    POLOM encji 1:1.
         * 2) INTERFEJS OTWARTY - `@Value("#{target.pole}")` Z WLASNA
         *    logika (np. LACZENIE pol).
         * 3) KLASA (DTO) - konstruktor Z PARAMETRAMI odpowiadajacymi
         *    polom - Spring Data SAM dopasowuje.
         */
        System.out.println("Projekcje = repozytorium zwraca TYLKO potrzebne pola (interfejs ZAMKNIETY/OTWARTY LUB klase DTO), NIE cala encje.");

        demonstrateClosedInterfaceProjection();
        demonstrateOpenInterfaceProjectionWithSpel();
        demonstrateClassBasedDtoProjection();
        demonstrateDynamicProjection();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - INTERFEJS ZAMKNIETY (`getXxx()` = NAZWA pola encji) -
         *   Spring Data GENERUJE proxy, MOZE zoptymalizowac SQL DO
         *   TYLKO potrzebnych kolumn.
         * - INTERFEJS OTWARTY (`@Value("#{target.pole}")`) - WLASNA
         *   logika SpEL, ALE Hibernate MUSI ZALADOWAC CALA encje
         *   NAJPIERW (BRAK optymalizacji SQL).
         * - PROJEKCJA KLASOWA (DTO Z konstruktorem) - Spring Data
         *   dopasowuje PARAMETRY konstruktora DO NAZW pol Z zapytania.
         * - DYNAMICZNA projekcja (`<T> List<T> metoda(..., Class<T>
         *   type)`) - WYWOLUJACY DECYDUJE, CZY chce CALA encje CZY
         *   projekcje, W TYM SAMYM miejscu W kodzie.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    // 'public' WYMAGANE: interfejs OTWARTY (SummaryProjection) uzywa SpEL ('#{target.name}'),
    // ktore (jak w _20_spring_core/Lesson17) rozwiazuje pola/gettery TYLKO gdy sa PUBLICZNE.
    @Entity(name = "Product")
    public static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;
        public String name;
        public String category;
        public double price;

        Product() {
        }

        Product(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }
    }

    // 1) INTERFEJS ZAMKNIETY - getName()/getPrice() ODPOWIADAJA POLOM 'name'/'price' encji Product.
    interface NameAndPriceProjection {
        String getName();

        double getPrice();
    }

    // 2) INTERFEJS OTWARTY - WLASNA logika przez SpEL, operujaca NA CALYM obiekcie ('target').
    interface SummaryProjection {
        @Value("#{target.name + ' (' + target.category + ')'}")
        String getSummary();
    }

    // 3) PROJEKCJA KLASOWA (DTO) - konstruktor Z PARAMETRAMI dopasowanymi PO nazwie DO pol zapytania.
    record PriceOnlyDto(String name, double price) {
    }

    interface ProductRepository extends JpaRepository<Product, Long> {
        List<NameAndPriceProjection> findByCategory(String category);

        List<SummaryProjection> findSummaryByCategory(String category);

        List<PriceOnlyDto> findPriceDtoByCategory(String category);

        <T> List<T> findByPriceGreaterThan(double price, Class<T> type);
    }

    static void seedCatalog(JpaRepository<Product, Long> repository) {
        repository.save(new Product("Klawiatura", "Elektronika", 199.99));
        repository.save(new Product("Mysz", "Elektronika", 49.99));
        repository.save(new Product("Biurko", "Meble", 599.0));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class ClosedApp {
    }

    private static void demonstrateClosedInterfaceProjection() throws Exception {
        System.out.println("\n=== INTERFEJS ZAMKNIETY - getName()/getPrice() ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson11closed;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClosedApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            List<NameAndPriceProjection> results = repository.findByCategory("Elektronika");
            for (NameAndPriceProjection p : results) {
                System.out.println("Projekcja zamknieta -> name=" + p.getName() + ", price=" + p.getPrice());
            }
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class OpenApp {
    }

    private static void demonstrateOpenInterfaceProjectionWithSpel() throws Exception {
        System.out.println("\n=== INTERFEJS OTWARTY - @Value(\"#{target.pole}\") Z WLASNA logika SpEL ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson11open;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(OpenApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            List<SummaryProjection> summaries = repository.findSummaryByCategory("Elektronika");
            for (SummaryProjection s : summaries) {
                System.out.println("Projekcja otwarta -> summary=" + s.getSummary());
            }
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class DtoApp {
    }

    private static void demonstrateClassBasedDtoProjection() throws Exception {
        System.out.println("\n=== PROJEKCJA KLASOWA (DTO) - konstruktor dopasowany PO nazwach pol ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson11dto;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DtoApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            List<PriceOnlyDto> dtos = repository.findPriceDtoByCategory("Meble");
            System.out.println("Projekcja klasowa (rekord PriceOnlyDto) -> " + dtos);
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class DynamicApp {
    }

    private static void demonstrateDynamicProjection() throws Exception {
        System.out.println("\n=== DYNAMICZNA PROJEKCJA - WYWOLUJACY WYBIERA TYP W MIEJSCU WYWOLANIA ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson11dynamic;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DynamicApp.class)
                .properties(props)
                .run();
        try {
            ProductRepository repository = context.getBean(ProductRepository.class);
            seedCatalog(repository);

            List<Product> fullEntities = repository.findByPriceGreaterThan(100, Product.class);
            System.out.println("findByPriceGreaterThan(100, Product.class) -> PELNE encje, liczba: " + fullEntities.size());

            List<NameAndPriceProjection> projections = repository.findByPriceGreaterThan(100, NameAndPriceProjection.class);
            System.out.println("findByPriceGreaterThan(100, NameAndPriceProjection.class) -> TYLKO projekcja, liczba: " + projections.size());
            System.out.println("-> TA SAMA metoda repozytorium, DWA RÓZNE typy zwracane - o TYM DECYDUJE WYWOLUJACY.");
        } finally {
            context.close();
        }
    }
}
