package com.example.javaquest._23_spring_data_jpa.Lesson15_SpringDataJpaCapstone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson15_SpringDataJpaCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15 (KAPSZTON): Mala 'Biblioteka' laczaca WSZYSTKIE 14 poprzednich lekcji ===");

        /*
         * ============================================================
         * 📦 KAPSZTON _23_spring_data_jpa - JEDNA aplikacja, WSZYSTKIE mechanizmy
         * ============================================================
         * Domena: Autor (Author) -> wiele Ksiazek (Book), Z FLYWAY-owym
         * schematem (Lesson14, izolowana lokalizacja `db/migration-lesson15`)
         * I `ddl-auto=validate` (Hibernate TYLKO weryfikuje mapowanie,
         * NIE tworzy schematu - Flyway juz to zrobil).
         *
         * KAZDY scenariusz nizej odpowiada JEDNEJ (LUB WIECEJ) poprzedniej
         * lekcji tego rozdzialu - numery lekcji SA wprost W komentarzach.
         */
        System.out.println("Domena: Author (1) -> Book (N), schemat Z Flyway (Lesson14), Hibernate TYLKO waliduje (ddl-auto=validate).");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson15capstone;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.flyway.locations", "classpath:db/migration-lesson15");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector", QueryCountingInspector.class.getName());

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LibraryApp.class)
                .properties(props)
                .run();
        try {
            AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
            BookRepository bookRepository = context.getBean(BookRepository.class);
            PublishingService publishingService = context.getBean(PublishingService.class);

            scenarioTransactionalPublishing(publishingService);
            scenarioTransactionalRollbackOnInvalidPrice(publishingService, authorRepository);
            scenarioQueryMethodsAndCustomQueries(bookRepository);
            scenarioPaginationAndSorting(bookRepository);
            scenarioNPlusOneAndFixes(authorRepository, publishingService);
            scenarioDynamicProjections(bookRepository);
            scenarioSpecificationSearch(bookRepository);
            scenarioAuditingFields(bookRepository);
            printFlywaySchemaHistory(context.getBean(DataSource.class));
        } finally {
            context.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * - Repozytoria (Lesson2-3) + query methods (Lesson4) + custom
         *   `@Query` (Lesson5) - deklaratywny dostep DO danych, ZERO
         *   recznego SQL/JDBC.
         * - Pagination/Sorting (Lesson6) - `Page<T>`/`Sort` WBUDOWANE W
         *   kazde repozytorium.
         * - Relacje (Lesson7) + `@Transactional` (Lesson8) - spojnosc
         *   grafu obiektow W GRANICACH transakcji.
         * - N+1 (Lesson9) + `@EntityGraph` (Lesson10) - WYDAJNOSC
         *   ladowania relacji, ZMIERZONA NAPRAWDE (`StatementInspector`).
         * - Projekcje (Lesson11) - TYLKO potrzebne dane, BEZ pelnych
         *   encji.
         * - `Specification` (Lesson12) - DYNAMICZNE wyszukiwanie Z
         *   opcjonalnych kryteriow.
         * - Auditing (Lesson13) - AUTOMATYCZNE "kto/kiedy" NA kazdym
         *   zapisie.
         * - Flyway (Lesson14) - schemat POD KONTROLA WERSJI, Hibernate
         *   TYLKO go WALIDUJE.
         * Spring Data JPA = Repository (Lesson2-6) + relacje/transakcje
         * (Lesson7-8) + wydajnosc (Lesson9-11) + dynamiczne zapytania
         * (Lesson12) + metadane (Lesson13) + kontrola schematu (Lesson14) -
         * WSZYSTKO NAD tym samym Hibernate/JPA, ktory poznales W
         * `_12_hibernate`.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 - KONIEC ROZDZIALU _23_spring_data_jpa ===");
    }

    public static class QueryCountingInspector implements StatementInspector {
        static final AtomicInteger QUERY_COUNT = new AtomicInteger(0);

        @Override
        public String inspect(String sql) {
            QUERY_COUNT.incrementAndGet();
            return sql;
        }
    }

    @Entity(name = "Author")
    @Table(name = "authors")
    static class Author {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        String country;

        @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        List<Book> books = new java.util.ArrayList<>();

        Author() {
        }

        Author(String name, String country) {
            this.name = name;
            this.country = country;
        }

        void addBook(Book book) {
            books.add(book);
            book.author = this;
        }

        @Override
        public String toString() {
            return name + " (" + country + ")";
        }
    }

    // @Table(name = "books") KONIECZNE - @Entity(name="Book") ustawia TYLKO nazwe JPA
    // (uzywana W JPQL/HQL), Hibernate BEZ @Table wyprowadzilby nazwe tabeli SQL
    // Z nazwy KLASY Javy ("Book" -> "book"), a Flyway (V1) stworzyl tabele 'books' -
    // TA SAMA pulapka, KTORA zostala udokumentowana W Lesson14.
    @Entity(name = "Book")
    @Table(name = "books")
    @EntityListeners(AuditingEntityListener.class)
    static class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;
        int pages;
        BigDecimal price;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        Author author;

        @CreatedDate
        Instant createdDate;

        @LastModifiedDate
        Instant lastModifiedDate;

        @CreatedBy
        String createdBy;

        @LastModifiedBy
        String lastModifiedBy;

        Book() {
        }

        Book(String title, int pages, BigDecimal price) {
            this.title = title;
            this.pages = pages;
            this.price = price;
        }

        @Override
        public String toString() {
            return title + " (" + pages + " str., " + price + " zl)";
        }
    }

    interface BookSummary {
        String getTitle();

        BigDecimal getPrice();
    }

    interface AuthorRepository extends JpaRepository<Author, Long> {
        List<Author> findByCountry(String country);

        // Lesson9 - CELOWO plain SELECT (BEZ JOIN FETCH) - uzywane W scenariuszu N+1.
        @Query("SELECT a FROM Author a")
        List<Author> findAllCausingNPlusOne();

        // Lesson9 - naprawa PRZEZ recznie napisany JOIN FETCH W JPQL.
        @Query("SELECT DISTINCT a FROM Author a JOIN FETCH a.books")
        List<Author> findAllWithBooksJoinFetch();

        // Lesson10 - naprawa PRZEZ deklaratywny @EntityGraph, override wbudowanego findAll().
        @EntityGraph(attributePaths = {"books"})
        List<Author> findAll();
    }

    interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
        // Lesson4 - query method NAWIGUJACY zagniezdzona wlasciwosc (author.country).
        List<Book> findByAuthor_Country(String country);

        // Lesson5 - custom @Query Z parametrem nazwanym.
        @Query("SELECT b FROM Book b WHERE b.price <= :maxPrice ORDER BY b.price")
        List<Book> findAffordableBooks(@Param("maxPrice") BigDecimal maxPrice);

        // Lesson11 - dynamiczna projekcja, TYP zwracany wybiera WYWOLUJACY.
        <T> List<T> findByPriceGreaterThan(BigDecimal price, Class<T> type);
    }

    static class BookSpecifications {
        static Specification<Book> hasAuthorCountry(String country) {
            return (root, query, cb) -> cb.equal(root.get("author").get("country"), country);
        }

        static Specification<Book> priceLessThanOrEqual(BigDecimal maxPrice) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        }
    }

    static class LibrarianAuditorAware implements AuditorAware<String> {
        private static final ThreadLocal<String> CURRENT_LIBRARIAN = ThreadLocal.withInitial(() -> "system");

        static void setCurrentLibrarian(String name) {
            CURRENT_LIBRARIAN.set(name);
        }

        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.ofNullable(CURRENT_LIBRARIAN.get());
        }
    }

    @Service
    static class PublishingService {
        private final AuthorRepository authorRepository;

        @Autowired
        PublishingService(AuthorRepository authorRepository, BookRepository bookRepository) {
            this.authorRepository = authorRepository;
        }

        @Transactional
        Author publishAuthorWithBooks(String authorName, String country, List<Book> books) {
            Author author = new Author(authorName, country);
            for (Book book : books) {
                if (book.price.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Cena ksiazki musi byc dodatnia: " + book.title);
                }
                author.addBook(book);
            }
            // RuntimeException RZUCONY WYZEJ (przed save()) COFA CALA metode (Lesson8) -
            // author W OGOLE NIE trafia DO bazy, mimo ze zostal juz utworzony W PAMIECI.
            return authorRepository.save(author);
        }

        @Transactional
        int countBooksTouchingLazyCollection() {
            // Cala metoda W JEDNEJ transakcji - sesja Hibernate OTWARTA przez caly czas,
            // WLACZNIE Z dostepem DO leniwej kolekcji 'books' W petli (Lesson9).
            List<Author> authors = authorRepository.findAllCausingNPlusOne();
            int total = 0;
            for (Author a : authors) {
                total += a.books.size();
            }
            return total;
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    @EnableJpaAuditing(auditorAwareRef = "currentLibrarianAuditorAware")
    static class LibraryApp {
        @Bean
        AuditorAware<String> currentLibrarianAuditorAware() {
            return new LibrarianAuditorAware();
        }

        @Bean
        PublishingService publishingService(AuthorRepository authorRepository, BookRepository bookRepository) {
            return new PublishingService(authorRepository, bookRepository);
        }
    }

    private static void scenarioTransactionalPublishing(PublishingService service) {
        System.out.println("\n--- SCENARIUSZ 1: Relacje (Lesson7) + @Transactional (Lesson8) + Auditing (Lesson13) ---");
        LibrarianAuditorAware.setCurrentLibrarian("ania");
        Author author = service.publishAuthorWithBooks("Andrzej Sapkowski", "Polska", List.of(
                new Book("Wiedzmin", 288, new BigDecimal("39.90")),
                new Book("Ostatnie zyczenie", 248, new BigDecimal("34.90"))
        ));
        System.out.println("Opublikowano: " + author + " z " + author.books.size() + " ksiazkami (dodane przez 'ania').");

        LibrarianAuditorAware.setCurrentLibrarian("krzysztof");
        service.publishAuthorWithBooks("J.R.R. Tolkien", "Wielka Brytania", List.of(
                new Book("Hobbit", 310, new BigDecimal("44.90")),
                new Book("Wladca Pierscieni", 1200, new BigDecimal("89.90"))
        ));
        System.out.println("Opublikowano DRUGIEGO autora (dodane przez 'krzysztof').");
    }

    private static void scenarioTransactionalRollbackOnInvalidPrice(PublishingService service, AuthorRepository authorRepository) {
        System.out.println("\n--- SCENARIUSZ 2: Rollback CALEJ transakcji PRZY nieprawidlowej cenie (Lesson8) ---");
        long countBefore = authorRepository.count();
        try {
            service.publishAuthorWithBooks("Zly Autor", "Polska", List.of(new Book("Zla ksiazka", 100, new BigDecimal("-10.00"))));
            System.out.println("BLAD: oczekiwano wyjatku, ale go NIE bylo!");
        } catch (IllegalArgumentException e) {
            System.out.println("Zlapano oczekiwany wyjatek: " + e.getMessage());
        }
        long countAfter = authorRepository.count();
        System.out.println("Liczba autorow PRZED=" + countBefore + " PO=" + countAfter
                + " (rowne -> CALA transakcja ZOSTALA COFNIETA, 'Zly Autor' NIE zostal zapisany).");
    }

    private static void scenarioQueryMethodsAndCustomQueries(BookRepository bookRepository) {
        System.out.println("\n--- SCENARIUSZ 3: Query methods (Lesson4) + custom @Query (Lesson5) ---");
        List<Book> polishBooks = bookRepository.findByAuthor_Country("Polska");
        System.out.println("findByAuthor_Country(\"Polska\") -> " + polishBooks);

        List<Book> affordable = bookRepository.findAffordableBooks(new BigDecimal("40.00"));
        System.out.println("findAffordableBooks(40.00) [@Query] -> " + affordable);
    }

    private static void scenarioPaginationAndSorting(BookRepository bookRepository) {
        System.out.println("\n--- SCENARIUSZ 4: Pagination + Sorting (Lesson6) ---");
        Page<Book> page = bookRepository.findAll(PageRequest.of(0, 2, Sort.by("price").descending()));
        System.out.println("Strona 0, rozmiar 2, sortowanie PO cenie malejaco -> " + page.getContent()
                + " (total elements=" + page.getTotalElements() + ", total pages=" + page.getTotalPages() + ")");
    }

    private static void scenarioNPlusOneAndFixes(AuthorRepository authorRepository, PublishingService service) {
        System.out.println("\n--- SCENARIUSZ 5: Problem N+1 (Lesson9) + naprawy JOIN FETCH (Lesson9) i @EntityGraph (Lesson10) ---");

        QueryCountingInspector.QUERY_COUNT.set(0);
        int totalNaive = service.countBooksTouchingLazyCollection();
        System.out.println("Naiwny findAllCausingNPlusOne() + petla po 'books': " + QueryCountingInspector.QUERY_COUNT.get() + " zapytan (1+N). Suma ksiazek=" + totalNaive);

        QueryCountingInspector.QUERY_COUNT.set(0);
        List<Author> joinFetched = authorRepository.findAllWithBooksJoinFetch();
        int totalJoinFetch = joinFetched.stream().mapToInt(a -> a.books.size()).sum();
        System.out.println("JOIN FETCH (@Query): " + QueryCountingInspector.QUERY_COUNT.get() + " zapytanie (oczekiwane: 1). Suma ksiazek=" + totalJoinFetch);

        QueryCountingInspector.QUERY_COUNT.set(0);
        List<Author> entityGraphLoaded = authorRepository.findAll();
        int totalEntityGraph = entityGraphLoaded.stream().mapToInt(a -> a.books.size()).sum();
        System.out.println("@EntityGraph (findAll() override): " + QueryCountingInspector.QUERY_COUNT.get() + " zapytanie (oczekiwane: 1). Suma ksiazek=" + totalEntityGraph);
    }

    private static void scenarioDynamicProjections(BookRepository bookRepository) {
        System.out.println("\n--- SCENARIUSZ 6: Dynamiczne projekcje (Lesson11) ---");
        List<Book> fullEntities = bookRepository.findByPriceGreaterThan(new BigDecimal("30.00"), Book.class);
        List<BookSummary> summaries = bookRepository.findByPriceGreaterThan(new BigDecimal("30.00"), BookSummary.class);
        System.out.println("findByPriceGreaterThan(30.00, Book.class) -> PELNE encje, liczba=" + fullEntities.size());
        System.out.println("findByPriceGreaterThan(30.00, BookSummary.class) -> TYLKO projekcja:");
        summaries.forEach(s -> System.out.println("  " + s.getTitle() + " -> " + s.getPrice() + " zl"));
    }

    private static void scenarioSpecificationSearch(BookRepository bookRepository) {
        System.out.println("\n--- SCENARIUSZ 7: Specification - DYNAMICZNE wyszukiwanie (Lesson12) ---");
        Specification<Book> spec = Specification.where(BookSpecifications.hasAuthorCountry("Polska"))
                .and(BookSpecifications.priceLessThanOrEqual(new BigDecimal("40.00")));
        List<Book> results = bookRepository.findAll(spec);
        System.out.println("Ksiazki polskich autorow Z cena <= 40.00 zl -> " + results);
    }

    private static void scenarioAuditingFields(BookRepository bookRepository) {
        System.out.println("\n--- SCENARIUSZ 8: Auditing - KTO/KIEDY utworzyl/zmienil ksiazke (Lesson13) ---");
        Book book = bookRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        System.out.println("Pierwsza ksiazka W bazie: " + book);
        System.out.println("  createdBy=" + book.createdBy + ", createdDate=" + book.createdDate);

        LibrarianAuditorAware.setCurrentLibrarian("marek");
        book.price = book.price.subtract(new BigDecimal("5.00"));
        Book updated = bookRepository.save(book);
        System.out.println("PO aktualizacji ceny (przez 'marek'): lastModifiedBy=" + updated.lastModifiedBy
                + ", createdBy NADAL='" + updated.createdBy + "' (NIEZMIENIONE)");
    }

    private static void printFlywaySchemaHistory(DataSource dataSource) throws Exception {
        System.out.println("\n--- SCENARIUSZ 9: Historia migracji Flyway (Lesson14) ---");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT \"installed_rank\", \"version\", \"description\", \"success\" " +
                             "FROM \"flyway_schema_history\" ORDER BY \"installed_rank\"")) {
            while (rs.next()) {
                System.out.println("  #" + rs.getInt("installed_rank") + " version=" + rs.getString("version")
                        + " description='" + rs.getString("description") + "' success=" + rs.getBoolean("success"));
            }
        }
    }
}
