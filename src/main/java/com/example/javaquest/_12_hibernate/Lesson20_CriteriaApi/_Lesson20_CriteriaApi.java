package com.example.javaquest._12_hibernate.Lesson20_CriteriaApi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson20_CriteriaApi {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: CRITERIA API: typowane zapytania programistyczne ===");

        /*
         * ============================================================
         * 📦 PO CO CRITERIA API, SKORO JEST HQL?
         * ============================================================
         * HQL (Lesson18/19) świetnie sprawdza się, gdy znasz KSZTAŁT
         * zapytania z góry - piszesz je jako gotowy String. Problem
         * pojawia się przy WYSZUKIWANIU Z OPCJONALNYMI FILTRAMI (np.
         * formularz wyszukiwania książek, gdzie użytkownik MOŻE, ale
         * nie musi, podać tytuł/autora/zakres cen) - budowanie takiego
         * zapytania jako String wymagałoby ręcznej konkatenacji
         * fragmentów WHERE zależnie od tego, które filtry są podane -
         * podatne na błędy i trudne w utrzymaniu.
         *
         * Criteria API pozwala budować zapytanie PROGRAMISTYCZNIE, jako
         * obiekty Javy (nie String) - możesz WARUNKOWO (zwykłe "if")
         * dodawać kolejne predykaty do listy, bez ręcznego sklejania
         * tekstu SQL/HQL.
         */
        System.out.println("\n=== PO CO CRITERIA API ===");
        System.out.println("HQL - gdy znasz ksztalt zapytania z gory. Criteria API - gdy zapytanie ma OPCJONALNE filtry.");

        /*
         * ============================================================
         * 🔹 CriteriaBuilder, CriteriaQuery<T>, Root<T>
         * ============================================================
         * - CriteriaBuilder - "fabryka" budująca WSZYSTKIE elementy
         *   zapytania (predykaty, funkcje agregujące, sortowanie).
         * - CriteriaQuery<T> - reprezentuje CAŁE zapytanie (odpowiednik
         *   HQL-owego "select ... from ... where ...", ale jako obiekt).
         * - Root<T> - punkt startowy zapytania (odpowiednik "from Book b"
         *   - "b" to właśnie Root<Book>), z którego nawigujesz do pól
         *   przez root.get("nazwaPola").
         */
        printCriteriaBuildingBlocksTable();

        /*
         * ============================================================
         * 🔍 Predicate[] - WARUNKI WHERE BUDOWANE WARUNKOWO
         * ============================================================
         * Predicate reprezentuje POJEDYNCZY warunek (np. "price >= X").
         * Zamiast sklejać Stringi, budujesz LISTĘ predykatów zwykłym
         * kodem Javy (if (filtr != null) { predicates.add(...); }), a
         * na końcu łączysz je przez criteriaBuilder.and(predicates).
         */
        System.out.println("\n=== Predicate - WARUNKI BUDOWANE WARUNKOWO (if w Javie) ===");
        System.out.println("Lista predykatow rosnie warunkowo (if filtr != null), na koncu polaczona przez cb.and(...).");

        /*
         * ============================================================
         * 🔹 METAMODEL STATYCZNY (Book_.class) - WZMIANKA
         * ============================================================
         * Zaawansowane projekty generują (przez annotation processor
         * hibernate-jpamodelgen w trakcie kompilacji) tzw. metamodel
         * statyczny - klasy takie jak "Book_" z polami statycznymi
         * odpowiadającymi polom encji (Book_.price, Book_.title) -
         * pozwala to na PEŁNE bezpieczeństwo typów i wykrywanie literówek
         * w nazwach pól JUŻ na etapie KOMPILACJI (zamiast dopiero w
         * runtime). W TYM KURSIE, dla prostoty (bez dodatkowego pluginu
         * do generowania kodu), budujemy zapytania po NAZWACH POLA jako
         * zwykłe Stringi (root.get("price")) - w pełni działające, ale
         * bez tej dodatkowej warstwy bezpieczeństwa typów w kompilacji.
         */
        System.out.println("\n=== METAMODEL STATYCZNY (Book_.class) - wzmianka ===");
        System.out.println("Generowany przez annotation processor - pelne bezp. typow w KOMPILACJI. W kursie: root.get(\"pole\") jako String.");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: DYNAMICZNE WYSZUKIWANIE KSIĄŻEK
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);

            Session session = sessionFactory.openSession();

            System.out.println("\n=== WYSZUKIWANIE 1: tylko cena minimalna = 30 ===");
            searchBooks(session, null, 30.0, null).forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            System.out.println("\n=== WYSZUKIWANIE 2: tytul zawiera 'Pan' + cena maksymalna = 50 ===");
            searchBooks(session, "Pan", null, 50.0).forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            System.out.println("\n=== WYSZUKIWANIE 3: bez zadnych filtrow (wszystkie ksiazki) ===");
            searchBooks(session, null, null, null).forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            session.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Criteria API - budowanie zapytań PROGRAMISTYCZNIE, idealne
         *   do zapytań z OPCJONALNYMI, dynamicznymi filtrami.
         * - CriteriaBuilder (fabryka), CriteriaQuery<T> (całe zapytanie),
         *   Root<T> (punkt startowy, odpowiednik "from X").
         * - Predicate + lista budowana warunkowo (if w Javie) +
         *   criteriaBuilder.and(...) na końcu.
         * - Metamodel statyczny (Book_.class) daje pełne bezpieczeństwo
         *   typów w kompilacji - w tym kursie uproszczone przez String.
         */

        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    /**
     * Dynamiczne wyszukiwanie ksiazek - kazdy filtr jest OPCJONALNY (moze byc null).
     */
    private static List<Book> searchBooks(Session session, String titleContains, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();
        if (titleContains != null) {
            predicates.add(cb.like(root.get("title"), "%" + titleContains + "%"));
        }
        if (minPrice != null) {
            predicates.add(cb.ge(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.le(root.get("price"), maxPrice));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return session.createQuery(query).getResultList();
    }

    private static void seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(book("Pan Tadeusz", 45.0));
        session.persist(book("Pan Wolodyjowski", 55.0));
        session.persist(book("Ballady i romanse", 30.0));

        transaction.commit();
        session.close();
    }

    private static Book book(String title, double price) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson20criteria;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printCriteriaBuildingBlocksTable() {
        System.out.println("\n=== ELEMENTY CRITERIA API ===");
        String format = "%-18s | %-45s%n";
        System.out.printf(format, "Element", "Rola");
        System.out.println("-".repeat(68));
        System.out.printf(format, "CriteriaBuilder", "fabryka predykatow/funkcji/sortowania");
        System.out.printf(format, "CriteriaQuery<T>", "cale zapytanie (odpowiednik select/from/where)");
        System.out.printf(format, "Root<T>", "punkt startowy (odpowiednik 'from X b')");
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private double price;

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
    }
}
