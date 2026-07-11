package com.example.javaquest._23_spring_data_jpa.Lesson01_WhatIsSpringDataJpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Properties;

public class _Lesson01_WhatIsSpringDataJpa {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: Czym jest Spring Data JPA ===");

        /*
         * ============================================================
         * 📦 SPRING DATA JPA - CIENKA WARSTWA NAD TYM, CO JUZ ZNASZ
         * ============================================================
         * Masz juz CALY `_12_hibernate` (30 lekcji: `SessionFactory`,
         * `Session`, HQL, transakcje, cache, blokady...) - Spring Data
         * JPA NIE ZASTEPUJE Hibernate, tylko GO AUTOMATYZUJE: TWOJ kod
         * definiuje TYLKO INTERFEJS (np. `BookRepository extends
         * JpaRepository<Book, Long>`), a Spring W CZASIE URUCHOMIENIA
         * GENERUJE prawdziwa implementacje (dynamiczny proxy), KTORA
         * "pod maska" I TAK uzywa `EntityManager`/Hibernate.
         *
         * W `_12_hibernate` musiales RECZNIE napisac DAO Z metodami
         * `save`/`findById`/`delete` (uzywajac `Session`). TU - ZERO
         * implementacji, TYLKO deklaracja interfejsu.
         */
        System.out.println("Spring Data JPA = TY piszesz TYLKO interfejs, Spring GENERUJE implementacje w czasie uruchomienia (na bazie EntityManager/Hibernate, ktore juz znasz).");

        demonstrateZeroImplementationRepository();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spring Data JPA = warstwa ABSTRAKCJI NAD JPA/Hibernate -
         *   NIE zastepuje wiedzy z `_12_hibernate`, tylko ja
         *   WYKORZYSTUJE automatycznie.
         * - `JpaRepository<Entity, IdType>` - interfejs, KTOREGO
         *   Spring Boot AUTOMATYCZNIE tworzy IMPLEMENTACJE (dynamiczny
         *   proxy) - ZERO recznego kodu DAO.
         * - `@EnableJpaRepositories` jest AUTOMATYCZNIE wlaczone przez
         *   `spring-boot-starter-data-jpa` (auto-konfiguracja, patrz
         *   `_21_spring_boot/Lesson04`) - skanuje interfejsy
         *   dziedziczace PO `Repository`/`CrudRepository`/`JpaRepository`.
         * - Nastepne lekcje pokaza HIERARCHIE interfejsow (Lesson02),
         *   RÓZNICE `CrudRepository` vs `JpaRepository` (Lesson03) i
         *   "query methods" GENEROWANE Z NAZWY metody (Lesson04).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
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

    interface BookRepository extends JpaRepository<Book, Long> {
        // ZERO kodu - Spring Data JPA WYGENERUJE implementacje TEGO interfejsu W CALOSCI.
    }

    @SpringBootApplication
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class RepositoryApp {
    }

    private static void demonstrateZeroImplementationRepository() throws Exception {
        System.out.println("\n=== ZERO KODU IMPLEMENTACJI - Spring GENERUJE repozytorium Z SAMEGO interfejsu ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson01;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RepositoryApp.class)
                .properties(props)
                .run();
        try {
            BookRepository repository = context.getBean(BookRepository.class);

            System.out.println("Rzeczywista klasa repozytorium: " + repository.getClass().getName());
            System.out.println("-> To NIE jest klasa, ktora napisales - to DYNAMICZNY PROXY wygenerowany przez Spring Data JPA W CZASIE URUCHOMIENIA.");

            Book saved = repository.save(new Book("Effective Java"));
            System.out.println("Zapisano: id=" + saved.id + ", title=" + saved.title);

            long count = repository.count();
            System.out.println("Liczba ksiazek w bazie: " + count);

            repository.findById(saved.id).ifPresent(book -> System.out.println("Znaleziono po ID: " + book.title));
        } finally {
            context.close();
        }
    }
}
