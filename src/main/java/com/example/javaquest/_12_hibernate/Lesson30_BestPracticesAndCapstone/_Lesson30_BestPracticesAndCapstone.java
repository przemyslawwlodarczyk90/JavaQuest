package com.example.javaquest._12_hibernate.Lesson30_BestPracticesAndCapstone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson30_BestPracticesAndCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 30: DOBRE PRAKTYKI I PROJEKT PODSUMOWUJACY ===");

        /*
         * ============================================================
         * 📦 TYPOWE PUŁAPKI Z CAŁEGO ROZDZIAŁU
         * ============================================================
         */
        printPitfallsTable();

        /*
         * ============================================================
         * 🔹 DOBRE PRAKTYKI
         * ============================================================
         * - DTO do ODCZYTU tam, gdzie nie potrzebujesz pełnej encji
         *   (Lesson19: "select new DTO(...)") - mniej danych, brak
         *   ryzyka przypadkowego dotknięcia LAZY powiązania poza sesją.
         * - JAWNE "join fetch" (Lesson15/19) zamiast polegania na EAGER
         *   "na sztywno" - świadome dociąganie TYLKO tam, gdzie potrzeba.
         * - KRÓTKIE transakcje - im dłużej trzymasz otwartą Transaction,
         *   tym dłużej trzymasz zasoby bazy (połączenia, blokady,
         *   Lesson26) - otwieraj transakcję jak najpóźniej, zamykaj jak
         *   najszybciej.
         */
        System.out.println("\n=== DOBRE PRAKTYKI ===");
        System.out.println("DTO do odczytu, jawne join fetch zamiast EAGER wszedzie, mozliwie KROTKIE transakcje.");

        /*
         * ============================================================
         * 🔍 WYDAJNOŚĆ: BATCH INSERTS/UPDATES, L2 CACHE
         * ============================================================
         * - hibernate.jdbc.batch_size (np. 50) - grupuje wiele INSERT/
         *   UPDATE w JEDNO zapytanie sieciowe do bazy zamiast wysyłać
         *   każdy osobno - ogromna różnica przy masowym zapisie.
         * - L2 cache (Lesson24) - unikaj dla danych CZĘSTO zmienianych
         *   (ryzyko nieaktualnego cache) - stosuj dla danych rzadko
         *   zmienianych, często odczytywanych.
         */
        System.out.println("\n=== WYDAJNOSC ===");
        System.out.println("hibernate.jdbc.batch_size grupuje INSERT/UPDATE. L2 cache - TYLKO dla danych rzadko zmienianych.");

        /*
         * ============================================================
         * 🔹 CZYSTY JDBC vs DAO RĘCZNY vs HIBERNATE/JPA
         * ============================================================
         */
        printApproachComparisonTable();

        /*
         * ============================================================
         * 🔹 PROJEKT PODSUMOWUJĄCY: MINI-BIBLIOTEKA (Author/Book/Loan)
         * ============================================================
         * Poniższy, w pełni działający program łączy WSZYSTKIE elementy
         * tego rozdziału w jednym, spójnym scenariuszu: asocjacje
         * (@OneToMany/@ManyToOne), optymistyczne blokowanie (@Version),
         * walidację (Bean Validation), HQL z join fetch (unikanie N+1),
         * oraz second-level cache.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            // 1. Utworzenie autora z ksiazkami (cascade PERSIST, Lesson14).
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Author sienkiewicz = new Author();
            sienkiewicz.setName("Henryk Sienkiewicz");

            Book potop = new Book();
            potop.setTitle("Potop");
            sienkiewicz.addBook(potop);

            Book ogniem = new Book();
            ogniem.setTitle("Ogniem i mieczem");
            sienkiewicz.addBook(ogniem);

            session.persist(sienkiewicz);
            transaction.commit();
            session.close();
            System.out.println("\n=== KROK 1: Autor + 2 ksiazki zapisane (cascade PERSIST) ===");

            // 2. Wypozyczenie ksiazki - walidacja Bean Validation (borrowerName).
            Session loanSession = sessionFactory.openSession();
            Transaction loanTx = loanSession.beginTransaction();
            Loan loan = new Loan();
            loan.setBook(potop);
            loan.setBorrowerName("Jan Kowalski");
            loan.setLoanDate(LocalDate.now());
            loanSession.persist(loan);
            loanTx.commit();
            loanSession.close();
            System.out.println("=== KROK 2: Wypozyczono '" + potop.getTitle() + "' dla " + loan.getBorrowerName() + " ===");

            // 3. HQL z join fetch - lista wszystkich wypozyczen BEZ problemu N+1 (Lesson15/19).
            Session readSession = sessionFactory.openSession();
            List<Loan> allLoans = readSession.createQuery(
                    "select l from Loan l join fetch l.book b join fetch b.author", Loan.class).getResultList();
            System.out.println("\n=== KROK 3: Wszystkie wypozyczenia (JOIN FETCH, JEDNO zapytanie) ===");
            allLoans.forEach(l -> System.out.println(" - " + l.getBorrowerName() + " wypozyczyl '" + l.getBook().getTitle()
                    + "' autorstwa " + l.getBook().getAuthor().getName()));
            readSession.close();

            // 4. Optymistyczne blokowanie przy zwrocie ksiazki (@Version, Lesson25).
            Session returnSession = sessionFactory.openSession();
            Transaction returnTx = returnSession.beginTransaction();
            Loan loanToReturn = returnSession.find(Loan.class, loan.getId());
            loanToReturn.setReturnDate(LocalDate.now());
            returnTx.commit();
            returnSession.close();
            System.out.println("\n=== KROK 4: Zwrocono ksiazke (version przed zapisem=" + loan.getVersion()
                    + ", po zapisie=" + loanToReturn.getVersion() + ") ===");

            // 5. Second-level cache (Lesson24) - druga, NOWA Session odczytuje TA SAMA ksiazke.
            Session cacheSession1 = sessionFactory.openSession();
            Book cachedRead1 = cacheSession1.find(Book.class, potop.getId());
            cacheSession1.close();

            Session cacheSession2 = sessionFactory.openSession();
            Book cachedRead2 = cacheSession2.find(Book.class, potop.getId());
            cacheSession2.close();
            System.out.println("\n=== KROK 5: Dwie NOWE Session odczytuja te sama ksiazke ('" + cachedRead2.getTitle()
                    + "') - druga trafia do L2 cache (patrz statystyki w Lesson24) ===");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CAŁEGO ROZDZIAŁU
         * ============================================================
         * - Hibernate/JPA automatyzuje mapowanie obiektowo-relacyjne
         *   (Lesson01), ale wymaga ŚWIADOMOŚCI mechanizmów pod spodem:
         *   cykl życia encji, dirty checking, fetch types, cache.
         * - Najczęstsze źródło problemów wydajnościowych: N+1 (Lesson15)
         *   - zawsze sprawdzaj wygenerowany SQL (show_sql) przy
         *   podejrzeniu problemów z wydajnością.
         * - Współbieżność: @Version (optymistyczne, domyślny wybór) lub
         *   LockModeType (pesymistyczne, dla krytycznych operacji).
         * - Nie każdy problem trzeba rozwiązywać przez Hibernate -
         *   native SQL (Lesson21) i bulk operations (Lesson19) mają
         *   swoje miejsce dla złożonych raportów/operacji masowych.
         */

        System.out.println("\n=== KONIEC LEKCJI 30 - KONIEC ROZDZIALU _12_hibernate ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson30capstone;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.jdbc.batch_size", "50");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "true");
        configuration.setProperty("hibernate.cache.region.factory_class", "jcache");
        configuration.setProperty("hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
        configuration.setProperty("hibernate.javax.cache.missing_cache_strategy", "create");
        configuration.addAnnotatedClass(Author.class);
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Loan.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printPitfallsTable() {
        System.out.println("\n=== TYPOWE PULAPKI Z CALEGO ROZDZIALU ===");
        String format = "%-35s | %-35s%n";
        System.out.printf(format, "Pulapka", "Gdzie w rozdziale");
        System.out.println("-".repeat(72));
        System.out.printf(format, "Problem N+1", "Lesson15 (fetch types)");
        System.out.printf(format, "Modyfikacja detached bez merge()", "Lesson06/Lesson16 (cykl zycia)");
        System.out.printf(format, "Brak @Version przy wspolbieznosci", "Lesson25 (optymistyczne)");
        System.out.printf(format, "Fetch EAGER 'na wszelki wypadek'", "Lesson15 (fetch types)");
    }

    private static void printApproachComparisonTable() {
        System.out.println("\n=== JDBC vs DAO RECZNY vs HIBERNATE/JPA ===");
        String format = "%-20s | %-45s%n";
        System.out.printf(format, "Podejscie", "Kiedy wybrac");
        System.out.println("-".repeat(68));
        System.out.printf(format, "Czysty JDBC (_09)", "Pelna kontrola nad SQL, proste, male projekty");
        System.out.printf(format, "DAO reczny (_10)", "Warstwa abstrakcji nad JDBC, wciaz pelna kontrola SQL");
        System.out.printf(format, "Hibernate/JPA (_12)", "Typowy CRUD na obiektach domenowych, wieksze projekty");
    }

    @Entity(name = "Author")
    @Table(name = "author")
    public static class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
        private List<Book> books = new ArrayList<>();

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addBook(Book book) {
            books.add(book);
            book.setAuthor(this);
        }
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        private Author author;

        @Version
        private int version;

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }
    }

    @Entity(name = "Loan")
    @Table(name = "loan")
    public static class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        private Book book;

        @NotNull
        @Size(min = 2, max = 100)
        private String borrowerName;

        private LocalDate loanDate;
        private LocalDate returnDate;

        @Version
        private int version;

        public Long getId() {
            return id;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        public void setLoanDate(LocalDate loanDate) {
            this.loanDate = loanDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }

        public int getVersion() {
            return version;
        }
    }
}
