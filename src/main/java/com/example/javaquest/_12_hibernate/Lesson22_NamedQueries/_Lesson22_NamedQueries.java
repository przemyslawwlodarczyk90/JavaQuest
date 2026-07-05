package com.example.javaquest._12_hibernate.Lesson22_NamedQueries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson22_NamedQueries {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 22: NAZWANE ZAPYTANIA: @NamedQuery, @NamedNativeQuery ===");

        /*
         * ============================================================
         * 📦 @NamedQuery - HQL ZDEFINIOWANY RAZ, PRZY ENCJI
         * ============================================================
         * @NamedQuery pozwala zdefiniować zapytanie HQL JEDEN RAZ, jako
         * adnotację NA KLASIE encji (nie w kodzie metody serwisu) - z
         * unikalną NAZWĄ (konwencja: "NazwaEncji.opisAkcji"). Kluczowa
         * zaleta względem zwykłego session.createQuery(hqlString): HQL
         * w @NamedQuery jest PARSOWANY I WALIDOWANY już PRZY STARCIE
         * SessionFactory (fail-fast!) - literówka w nazwie pola zostanie
         * wykryta NATYCHMIAST przy uruchomieniu aplikacji, a NIE dopiero
         * przy pierwszym wywołaniu tego zapytania w runtime (co mogłoby
         * nastąpić długo po starcie, np. dopiero gdy user kliknie
         * konkretny przycisk).
         */
        System.out.println("\n=== @NamedQuery ===");
        System.out.println("HQL zdefiniowany RAZ przy encji, z unikalna nazwa - WALIDOWANY juz przy starcie SessionFactory.");

        /*
         * ============================================================
         * 🔹 @NamedNativeQuery - ANALOGICZNIE DLA SQL NATYWNEGO
         * ============================================================
         * Ten sam pomysł, ale dla czystego SQL (Lesson21) zamiast HQL -
         * przydatne, gdy chcesz scentralizować RÓWNIEŻ natywne zapytania
         * SQL przy encji, zamiast rozrzucać je po kodzie serwisów.
         * ⚠️ Walidacja natywnego SQL przy starcie jest OGRANICZONA -
         * Hibernate nie "rozumie" SQL tak głęboko jak HQL, więc błędy
         * składniowe w SQL mogą i tak ujawnić się dopiero przy
         * wykonaniu, nie zawsze przy starcie.
         */
        System.out.println("\n=== @NamedNativeQuery ===");
        System.out.println("Analogicznie dla SQL natywnego - walidacja przy starcie bardziej ograniczona niz dla HQL.");

        /*
         * ============================================================
         * 🔍 UŻYCIE W KODZIE: createNamedQuery
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);
            Session session = sessionFactory.openSession();

            List<Book> byAuthor = session.createNamedQuery("Book.findByAuthor", Book.class)
                    .setParameter("author", "Adam Mickiewicz")
                    .getResultList();
            System.out.println("\n=== createNamedQuery(\"Book.findByAuthor\", Book.class) ===");
            byAuthor.forEach(b -> System.out.println(" - " + b.getTitle()));

            List<Book> cheap = session.createNamedQuery("Book.findCheaperThan", Book.class)
                    .setParameter("maxPrice", 40.0)
                    .getResultList();
            System.out.println("\n=== createNamedQuery(\"Book.findCheaperThan\", Book.class) ===");
            cheap.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            List<Book> nativeResult = session.createNamedQuery("Book.findAllNative", Book.class).getResultList();
            System.out.println("\n=== createNamedQuery(\"Book.findAllNative\", Book.class) - @NamedNativeQuery ===");
            nativeResult.forEach(b -> System.out.println(" - " + b.getTitle()));

            session.close();
        }

        /*
         * ============================================================
         * 🔹 KIEDY NAZWANE ZAPYTANIA SĄ LEPSZE, A KIEDY NIE
         * ============================================================
         * DOBRE dla zapytań POWTARZALNYCH, WSPÓŁDZIELONYCH przez wiele
         * miejsc w kodzie (np. "znajdź książki danego autora" wołane
         * przez kilka serwisów) - jedna definicja, fail-fast walidacja,
         * łatwe do znalezienia (wszystkie zapytania encji w jednym
         * miejscu, przy jej klasie).
         *
         * SŁABE dla zapytań DYNAMICZNYCH, z opcjonalnymi/zmiennymi
         * warunkami (Lesson20 Criteria API) - @NamedQuery ma SZTYWNĄ,
         * ustaloną raz treść HQL, nie da się jej "dobudować" warunkowo
         * w runtime tak jak Predicate w Criteria API.
         */
        System.out.println("\n=== KIEDY NAZWANE ZAPYTANIA, A KIEDY NIE ===");
        System.out.println("DOBRE: zapytania powtarzalne/wspoldzielone. SLABE: zapytania dynamiczne (patrz Lesson20 Criteria API).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @NamedQuery - HQL zdefiniowany raz przy encji, walidowany
         *   fail-fast przy starcie SessionFactory.
         * - @NamedNativeQuery - analogicznie dla SQL natywnego, z
         *   ograniczoną walidacją przy starcie.
         * - createNamedQuery("Encja.nazwa", Klasa.class) - użycie w
         *   kodzie, identyczne API jak zwykłe zapytania.
         * - Dobre dla zapytań powtarzalnych/współdzielonych; dla
         *   zapytań dynamicznych lepszy jest Criteria API (Lesson20).
         */

        System.out.println("\n=== KONIEC LEKCJI 22 ===");
    }

    private static void seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(book("Pan Tadeusz", 45.0, "Adam Mickiewicz"));
        session.persist(book("Ballady i romanse", 30.0, "Adam Mickiewicz"));
        session.persist(book("Kordian", 60.0, "Juliusz Slowacki"));

        transaction.commit();
        session.close();
    }

    private static Book book(String title, double price, String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setAuthor(author);
        return book;
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson22namedqueries;DB_CLOSE_DELAY=-1");
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

    @Entity(name = "Book")
    @Table(name = "book")
    @NamedQueries({
            @NamedQuery(name = "Book.findByAuthor", query = "from Book b where b.author = :author"),
            @NamedQuery(name = "Book.findCheaperThan", query = "from Book b where b.price < :maxPrice")
    })
    @NamedNativeQuery(name = "Book.findAllNative", query = "select * from book order by title", resultClass = Book.class)
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private double price;
        private String author;

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

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
