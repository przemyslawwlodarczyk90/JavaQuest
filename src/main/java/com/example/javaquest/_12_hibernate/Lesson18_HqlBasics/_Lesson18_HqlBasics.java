package com.example.javaquest._12_hibernate.Lesson18_HqlBasics;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson18_HqlBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: HQL/JPQL - PODSTAWY: SELECT, WHERE, parametry ===");

        /*
         * ============================================================
         * 📦 HQL/JPQL OPERUJE NA ENCJACH, NIE NA TABELACH
         * ============================================================
         * Kluczowa różnica względem SQL (_08_sql): HQL (Hibernate Query
         * Language) / JPQL (JPA Query Language, praktycznie identyczna
         * składnia) odwołuje się do NAZW KLAS I PÓL ENCJI, a NIE do
         * nazw tabel i kolumn SQL. "from Book b" odnosi się do klasy
         * Java "Book" (z adnotacją @Entity), niezależnie od tego, jak
         * nazywa się jej tabela (@Table(name=...)). Podobnie
         * "b.author.name" nawiguje przez POLA Javy (author, potem
         * name), a NIE przez kolumny/JOIN-y SQL - Hibernate SAM
         * tłumaczy to na odpowiedni JOIN pod spodem.
         */
        System.out.println("\n=== HQL operuje na ENCJACH, nie na tabelach ===");
        System.out.println("'from Book b' -> klasa Java Book (nie tabela SQL). 'b.author.name' -> nawigacja przez POLA.");

        /*
         * ============================================================
         * 🔹 PODSTAWOWE ZAPYTANIE + PARAMETRY NAZWANE vs POZYCYJNE
         * ============================================================
         * - Parametry NAZWANE (:name) - czytelniejsze, zalecane w
         *   praktyce - kolejność w zapytaniu nie musi odpowiadać
         *   kolejności ustawiania wartości.
         * - Parametry POZYCYJNE (?1, ?2...) - numerowane od 1 (NIE od
         *   0!) - rzadziej używane, bardziej podatne na pomyłkę przy
         *   zmianie kolejności w zapytaniu.
         *
         * OBA typy parametrów są ZAWSZE bindowane bezpiecznie (jak
         * PreparedStatement w czystym JDBC, _09_jdbc) - HQL/JPQL jest
         * odporne na SQL injection, o ile używasz parametrów zamiast
         * sklejania Stringów.
         */
        System.out.println("\n=== PARAMETRY: :nazwane vs ?pozycyjne ===");
        System.out.println(":name (nazwane, zalecane) vs ?1 (pozycyjne, numerowane OD 1, nie od 0).");

        /*
         * ============================================================
         * 🔍 TypedQuery<T> - BEZPIECZEŃSTWO TYPÓW
         * ============================================================
         * entityManager.createQuery(hql, Book.class) zwraca
         * TypedQuery<Book> - kompilator/IDE WIE, że getResultList()
         * zwróci List<Book>, bez rzutowania. Odpowiednik natywnego
         * Hibernate Query<T> (Session.createQuery(hql, Book.class)) -
         * w tym kursie używamy Session, ale API jest identyczne z JPA
         * TypedQuery (Lesson07 - Session rozszerza EntityManager).
         */
        System.out.println("\n=== TypedQuery<T> ===");
        System.out.println("createQuery(hql, Klasa.class) -> typowany wynik, bez recznego rzutowania.");

        /*
         * ============================================================
         * 🔹 PRZYGOTOWANIE DANYCH + PODSTAWOWE OPERATORY
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);

            Session session = sessionFactory.openSession();

            // Parametr NAZWANY.
            TypedQuery<Book> byAuthorQuery = session.createQuery(
                    "from Book b where b.author.name = :authorName", Book.class);
            byAuthorQuery.setParameter("authorName", "Adam Mickiewicz");
            List<Book> booksByAuthor = byAuthorQuery.getResultList();
            System.out.println("\n=== Parametr NAZWANY (:authorName) ===");
            booksByAuthor.forEach(b -> System.out.println(" - " + b.getTitle()));

            // Parametr POZYCYJNY (?1).
            TypedQuery<Book> byPositionalQuery = session.createQuery(
                    "from Book b where b.price < ?1", Book.class);
            byPositionalQuery.setParameter(1, 40.0);
            List<Book> cheapBooks = byPositionalQuery.getResultList();
            System.out.println("\n=== Parametr POZYCYJNY (?1) - ksiazki tansze niz 40 ===");
            cheapBooks.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            // ORDER BY.
            List<Book> sortedByPrice = session.createQuery(
                    "from Book b order by b.price desc", Book.class).getResultList();
            System.out.println("\n=== ORDER BY (malejaco po cenie) ===");
            sortedByPrice.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            // LIKE.
            List<Book> likeResults = session.createQuery(
                    "from Book b where b.title like :pattern", Book.class)
                    .setParameter("pattern", "Pan%")
                    .getResultList();
            System.out.println("\n=== LIKE ('Pan%') ===");
            likeResults.forEach(b -> System.out.println(" - " + b.getTitle()));

            // IN.
            List<Book> inResults = session.createQuery(
                    "from Book b where b.title in :titles", Book.class)
                    .setParameter("titles", List.of("Pan Tadeusz", "Ballady i romanse"))
                    .getResultList();
            System.out.println("\n=== IN (:titles) ===");
            inResults.forEach(b -> System.out.println(" - " + b.getTitle()));

            // BETWEEN.
            List<Book> betweenResults = session.createQuery(
                    "from Book b where b.price between :min and :max", Book.class)
                    .setParameter("min", 20.0)
                    .setParameter("max", 50.0)
                    .getResultList();
            System.out.println("\n=== BETWEEN (:min, :max) ===");
            betweenResults.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            session.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HQL/JPQL odwołuje się do klas i pól ENCJI, nie do tabel/
         *   kolumn SQL - Hibernate sam tłumaczy nawigację po polach na
         *   odpowiednie JOIN-y.
         * - Parametry nazwane (:nazwa) - zalecane; pozycyjne (?1) -
         *   numerowane od 1.
         * - TypedQuery<T> daje bezpieczeństwo typów bez rzutowania.
         * - ORDER BY, LIKE, IN, BETWEEN działają analogicznie do SQL,
         *   ale operują na polach encji Javy.
         */

        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Author mickiewicz = new Author();
        mickiewicz.setName("Adam Mickiewicz");
        session.persist(mickiewicz);

        Author slowacki = new Author();
        slowacki.setName("Juliusz Slowacki");
        session.persist(slowacki);

        session.persist(book("Pan Tadeusz", 45.0, mickiewicz));
        session.persist(book("Ballady i romanse", 30.0, mickiewicz));
        session.persist(book("Kordian", 25.0, slowacki));
        session.persist(book("Balladyna", 60.0, slowacki));

        transaction.commit();
        session.close();
    }

    private static Book book(String title, double price, Author author) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setAuthor(author);
        return book;
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson18hql;DB_CLOSE_DELAY=-1");
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

    @Entity(name = "Author")
    @Table(name = "author")
    public static class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private double price;

        @ManyToOne
        @JoinColumn(name = "author_id")
        private Author author;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }
    }
}
