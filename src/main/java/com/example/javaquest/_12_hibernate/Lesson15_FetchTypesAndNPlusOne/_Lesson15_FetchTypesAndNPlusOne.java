package com.example.javaquest._12_hibernate.Lesson15_FetchTypesAndNPlusOne;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson15_FetchTypesAndNPlusOne {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: FETCH TYPES I PROBLEM N+1: LAZY vs EAGER ===");

        /*
         * ============================================================
         * 📦 FetchType.LAZY vs FetchType.EAGER
         * ============================================================
         * - LAZY (leniwe) - powiązany obiekt/kolekcja NIE ładuje się
         *   razem z rodzicem - Hibernate podstawia PROXY, a prawdziwe
         *   zapytanie SQL wykonuje się DOPIERO przy pierwszym dostępie
         *   do tego pola (np. author.getBooks().size()).
         * - EAGER (chętne) - powiązany obiekt/kolekcja ładuje się OD
         *   RAZU, w tym samym momencie co rodzic (osobnym zapytaniem
         *   albo przez JOIN, w zależności od strategii).
         */
        System.out.println("\n=== LAZY vs EAGER ===");
        System.out.println("LAZY  - proxy, ladowane DOPIERO przy pierwszym uzyciu.");
        System.out.println("EAGER - ladowane OD RAZU, razem z rodzicem.");

        /*
         * ============================================================
         * 🔹 DOMYŚLNE FETCH TYPES W JPA
         * ============================================================
         * Standard JPA definiuje RÓŻNE domyślne wartości dla różnych
         * typów relacji - częsta pułapka: zakładanie, że WSZYSTKO jest
         * domyślnie LAZY (nieprawda dla @ManyToOne/@OneToOne).
         */
        printDefaultFetchTypesTable();

        /*
         * ============================================================
         * 🔍 PROBLEM N+1 - DEMO NA ŻYWO
         * ============================================================
         * N+1 to jeden z najbardziej znanych problemów wydajnościowych
         * ORM: 1 zapytanie ładuje listę N rodziców, a potem PĘTLA po
         * nich - dla KAŻDEGO z osobna - odpytuje bazę o jego LAZY
         * powiązanie (kolejne N zapytań) - razem N+1 zapytań zamiast 1.
         * Poniżej REALNA demonstracja z hibernate.show_sql=true -
         * policz linie "select" w konsoli.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);

            System.out.println("\n=== DEMO PROBLEMU N+1 (patrz na liczbe zapytan 'select' w logu) ===");
            Session session = sessionFactory.openSession();
            List<Author> authors = session.createQuery("from Author", Author.class).getResultList(); // 1 zapytanie
            int totalBooks = 0;
            for (Author author : authors) {
                totalBooks += author.getBooks().size(); // KAZDE wywolanie -> osobne zapytanie SQL (LAZY)!
            }
            session.close();
            System.out.println("Zaladowano " + authors.size() + " autorow + " + authors.size() + " DODATKOWYCH zapytan o ich ksiazki = N+1.");
            System.out.println("Lacznie ksiazek: " + totalBooks);

            /*
             * ============================================================
             * 🔹 ROZWIĄZANIE: JOIN FETCH
             * ============================================================
             * "join fetch" w HQL/JPQL (Lesson19 rozwinie HQL zaawansowany)
             * mówi Hibernate: "dociągnij powiązanie JEDNYM zapytaniem
             * (przez SQL JOIN), zamiast osobnym zapytaniem na każdy
             * wiersz". Poniżej to samo zadanie, ale JEDNYM zapytaniem.
             */
            System.out.println("\n=== ROZWIAZANIE: JOIN FETCH (JEDNO zapytanie) ===");
            Session fetchSession = sessionFactory.openSession();
            List<Author> authorsWithBooks = fetchSession
                    .createQuery("select distinct a from Author a join fetch a.books", Author.class)
                    .getResultList();
            int totalBooksFetched = authorsWithBooks.stream().mapToInt(a -> a.getBooks().size()).sum();
            fetchSession.close();
            System.out.println("Zaladowano " + authorsWithBooks.size() + " autorow + ich ksiazki JEDNYM zapytaniem (join fetch).");
            System.out.println("Lacznie ksiazek: " + totalBooksFetched);
        }

        /*
         * ============================================================
         * 🔍 INNE ROZWIĄZANIA: @EntityGraph, ŚWIADOMA ZMIANA NA EAGER
         * ============================================================
         * - @EntityGraph (adnotacja JPA) - deklaratywny sposób
         *   zdefiniowania, KTÓRE powiązania dociągnąć razem z zapytaniem,
         *   bez zmiany samego HQL/JPQL - przydatne, gdy różne metody
         *   potrzebują różnego "kształtu" dociągania tej samej encji.
         * - Zmiana na EAGER "na sztywno" w mapowaniu - ODRADZANA jako
         *   generalne rozwiązanie N+1, bo wymusza ładowanie powiązania
         *   ZAWSZE, nawet gdy w danym miejscu w ogóle nie jest potrzebne
         *   - może "przeładować" niepotrzebne dane w zupełnie innych
         *   częściach aplikacji.
         */
        System.out.println("\n=== INNE ROZWIAZANIA ===");
        System.out.println("@EntityGraph - deklaratywne, elastyczne dociaganie wybranych powiazan.");
        System.out.println("Zmiana na EAGER na sztywno - ODRADZANA jako general fix (przeladuje dane WSZEDZIE).");

        /*
         * ============================================================
         * 📌 PRAKTYCZNA RADA
         * ============================================================
         * Domyślnie wybieraj LAZY (dla @OneToMany/@ManyToMany to i tak
         * domyślne zachowanie; dla @ManyToOne/@OneToOne warto ustawić
         * jawnie fetch = FetchType.LAZY) - a gdy KONKRETNE miejsce w
         * kodzie potrzebuje powiązania, dociągnij je ŚWIADOMIE przez
         * "join fetch" albo @EntityGraph w TYM JEDNYM zapytaniu.
         */
        System.out.println("\n=== PRAKTYCZNA RADA ===");
        System.out.println("LAZY jako domyslny wybor + swiadome 'join fetch' tam, gdzie konkretnie potrzebujesz danych.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - LAZY - proxy, ładowane na żądanie; EAGER - ładowane od razu.
         * - Domyślnie: @ManyToOne/@OneToOne = EAGER, @OneToMany/
         *   @ManyToMany = LAZY (częsta pułapka założeń).
         * - N+1: 1 zapytanie na listę rodziców + N zapytań w pętli po
         *   LAZY powiązaniach - poznasz to po liczbie "select" w logu.
         * - Rozwiązania: "join fetch" w HQL/JPQL, @EntityGraph -
         *   unikaj sztywnej zmiany na EAGER jako generalnego fixa.
         */

        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        for (int i = 1; i <= 3; i++) {
            Author author = new Author();
            author.setName("Autor " + i);
            for (int j = 1; j <= 2; j++) {
                Book book = new Book();
                book.setTitle("Ksiazka " + i + "." + j);
                author.addBook(book);
            }
            session.persist(author);
        }

        transaction.commit();
        session.close();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson15fetch;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Author.class);
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printDefaultFetchTypesTable() {
        System.out.println("\n=== DOMYSLNE FETCH TYPES W JPA ===");
        String format = "%-15s | %-15s%n";
        System.out.printf(format, "Relacja", "Domyslny fetch");
        System.out.println("-".repeat(35));
        System.out.printf(format, "@ManyToOne", "EAGER");
        System.out.printf(format, "@OneToOne", "EAGER");
        System.out.printf(format, "@OneToMany", "LAZY");
        System.out.printf(format, "@ManyToMany", "LAZY");
    }

    @Entity(name = "Author")
    @Table(name = "author")
    public static class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        private List<Book> books = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Book> getBooks() {
            return books;
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

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }
    }
}
