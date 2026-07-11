package com.example.javaquest._23_spring_data_jpa.Lesson02_RepositoryInterfaces;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.Properties;

public class _Lesson02_RepositoryInterfaces {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: Hierarchia interfejsow repozytoriow ===");

        /*
         * ============================================================
         * 📦 HIERARCHIA: Repository -> CrudRepository -> PagingAndSortingRepository -> JpaRepository
         * ============================================================
         * Spring Data buduje interfejsy WARSTWAMI - KAZDY kolejny
         * DODAJE mozliwosci:
         *
         * - `Repository<T, ID>` - PUSTY interfejs "znacznikowy" (marker) -
         *   SAM W SOBIE NIC nie daje, ale Spring UZYWA go DO
         *   ROZPOZNANIA "to jest repozytorium" podczas skanowania.
         * - `CrudRepository<T, ID>` - DODAJE `save`/`findById`/`findAll`/
         *   `deleteById`/`count`/`existsById` - PODSTAWOWE operacje CRUD.
         * - `PagingAndSortingRepository<T, ID>` - DODAJE `findAll(Sort)`/
         *   `findAll(Pageable)` - SORTOWANIE i STRONICOWANIE (rozwiniete
         *   W Lesson06).
         * - `JpaRepository<T, ID>` - DODAJE `saveAndFlush`/`flush`/
         *   `deleteInBatch`/`deleteAllInBatch`/`getReferenceById` -
         *   funkcje SPECYFICZNE DLA JPA (NIE ma ich generyczny Spring
         *   Data, KTORY wspiera TEZ MongoDB/Redis/inne bazy).
         *
         * W PRAKTYCE prawie ZAWSZE dziedziczysz PO `JpaRepository` -
         * dostajesz WSZYSTKO NARAZ.
         *
         * WAZNA RÓZNICA WERSJI: PRZED Spring Data 3.0, `PagingAndSortingRepository`
         * DZIEDZICZYL PO `CrudRepository` (wiec sam W SOBIE dawal TEZ CRUD).
         * OD Spring Data 3.0 (Boot 3.x, TEN projekt) te interfejsy zostaly
         * ROZDZIELONE - `PagingAndSortingRepository` juz NIE dziedziczy
         * PO `CrudRepository` - trzeba dziedziczyc PO OBU NARAZ (patrz
         * `PagingBookRepository` nizej) - CHYBA ze dziedziczysz PO
         * `JpaRepository`, KTORY I TAK laczy WSZYSTKO.
         */
        System.out.println("Repository (marker) -> CrudRepository (CRUD) -> PagingAndSortingRepository (+sort/page) -> JpaRepository (+JPA-specific) - kazda warstwa DODAJE mozliwosci.");

        demonstrateCrudRepositoryLevel();
        demonstratePagingAndSortingRepositoryLevel();
        demonstrateJpaRepositoryLevel();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Repository<T, ID>` - marker, ZERO metod - Spring UZYWA go
         *   DO wykrycia "to jest repozytorium Spring Data".
         * - `CrudRepository` - PODSTAWA (save/find/delete/count/exists).
         * - `PagingAndSortingRepository` - DODAJE `Sort`/`Pageable`.
         * - `JpaRepository` - NAJBOGATSZY interfejs, SPECYFICZNY DLA
         *   JPA - `flush()`/`saveAndFlush()`/`deleteInBatch()` -
         *   TE metody NIE ISTNIALYBY W repozytorium DLA MongoDB.
         * - Domyslny wybor W PRAWIE KAZDYM projekcie JPA:
         *   `extends JpaRepository<Entity, IdType>`.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    @Entity(name = "Book")
    static class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;

        Book() {
        }

        Book(String title) {
            this.title = title;
        }
    }

    interface CrudOnlyBookRepository extends CrudRepository<Book, Long> {
        // Ma TYLKO: save/findById/findAll/deleteById/count/existsById - BRAK sortowania/stronicowania.
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class CrudApp {
    }

    private static void demonstrateCrudRepositoryLevel() throws Exception {
        System.out.println("\n=== POZIOM CrudRepository - save/findById/count/deleteById ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson02crud;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CrudApp.class)
                .properties(props)
                .run();
        try {
            CrudOnlyBookRepository repository = context.getBean(CrudOnlyBookRepository.class);
            repository.save(new Book("Clean Code"));
            System.out.println("Liczba ksiazek: " + repository.count());
        } finally {
            context.close();
        }
    }

    interface PagingBookRepository extends PagingAndSortingRepository<Book, Long>, CrudRepository<Book, Long> {
        // Ma WSZYSTKO z CrudRepository + findAll(Sort)/findAll(Pageable).
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class PagingApp {
    }

    private static void demonstratePagingAndSortingRepositoryLevel() throws Exception {
        System.out.println("\n=== POZIOM PagingAndSortingRepository - findAll(Sort) ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson02paging;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(PagingApp.class)
                .properties(props)
                .run();
        try {
            PagingBookRepository repository = context.getBean(PagingBookRepository.class);
            repository.save(new Book("Zulu"));
            repository.save(new Book("Alfa"));
            repository.save(new Book("Mike"));

            Iterable<Book> sorted = repository.findAll(Sort.by(Sort.Direction.ASC, "title"));
            StringBuilder titles = new StringBuilder();
            sorted.forEach(b -> titles.append(b.title).append(" "));
            System.out.println("findAll(Sort.by(\"title\")) -> " + titles.toString().trim());
            System.out.println("-> CrudRepository SAM nie ma tej metody - dopiero PagingAndSortingRepository ja DODAJE.");
        } finally {
            context.close();
        }
    }

    interface FullBookRepository extends JpaRepository<Book, Long> {
        // Ma WSZYSTKO - CrudRepository + PagingAndSortingRepository + metody SPECYFICZNE dla JPA.
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class JpaApp {
    }

    private static void demonstrateJpaRepositoryLevel() throws Exception {
        System.out.println("\n=== POZIOM JpaRepository - flush()/saveAndFlush() (metody SPECYFICZNE dla JPA) ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson02jpa;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(JpaApp.class)
                .properties(props)
                .run();
        try {
            FullBookRepository repository = context.getBean(FullBookRepository.class);
            Book book = repository.saveAndFlush(new Book("Refactoring"));
            System.out.println("saveAndFlush() -> id=" + book.id + " (dane NATYCHMIAST zapisane W bazie, BEZ czekania na koniec transakcji)");

            System.out.println("Wszystkie ksiazki: " + repository.findAll().size());
            System.out.println("-> 'findAll()' tu zwraca List<Book> (wygodniejsze), W CrudRepository zwraca Iterable<Book> (mniej konkretne).");
        } finally {
            context.close();
        }
    }
}
