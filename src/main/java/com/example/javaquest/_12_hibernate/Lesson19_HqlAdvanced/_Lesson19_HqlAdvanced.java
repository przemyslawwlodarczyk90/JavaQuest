package com.example.javaquest._12_hibernate.Lesson19_HqlAdvanced;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson19_HqlAdvanced {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: HQL/JPQL ZAAWANSOWANE: joiny, agregacje, podzapytania, projekcje DTO ===");

        /*
         * ============================================================
         * 📦 JOIN I JOIN FETCH - JAWNE STEROWANIE ŁADOWANIEM
         * ============================================================
         * - "join" (zwykły JOIN HQL) - filtruje/łączy wyniki, ale NIE
         *   dociąga automatycznie powiązanej encji do pamięci (jeśli
         *   powiązanie jest LAZY, nadal dostaniesz proxy).
         * - "join fetch" - Lesson15 już to pokazał jako rozwiązanie N+1
         *   - dociąga powiązaną encję TYM SAMYM zapytaniem SQL (JOIN),
         *   eliminując dodatkowe zapytania w pętli.
         */
        System.out.println("\n=== JOIN vs JOIN FETCH ===");
        System.out.println("join       - laczy/filtruje, NIE dociaga automatycznie (LAZY zostaje proxy).");
        System.out.println("join fetch - dociaga powiazana encje JEDNYM zapytaniem SQL (rozwiazanie N+1, Lesson15).");

        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);
            Session session = sessionFactory.openSession();

            /*
             * ============================================================
             * 🔹 FUNKCJE AGREGUJĄCE + GROUP BY/HAVING
             * ============================================================
             */
            List<Object[]> countByAuthor = session.createQuery(
                    "select a.name, count(b) from Book b join b.author a group by a.name having count(b) > 1",
                    Object[].class).getResultList();
            System.out.println("\n=== COUNT + GROUP BY + HAVING (autorzy z wiecej niz 1 ksiazka) ===");
            for (Object[] row : countByAuthor) {
                System.out.println(" - " + row[0] + ": " + row[1] + " ksiazek");
            }

            Double avgPrice = session.createQuery("select avg(b.price) from Book b", Double.class).getSingleResult();
            Double maxPrice = session.createQuery("select max(b.price) from Book b", Double.class).getSingleResult();
            System.out.println("\n=== AVG/MAX ===");
            System.out.println("Srednia cena: " + avgPrice + ", najwyzsza cena: " + maxPrice);

            /*
             * ============================================================
             * 🔍 PODZAPYTANIA (SUBQUERIES) W WHERE/EXISTS
             * ============================================================
             */
            List<Book> aboveAverage = session.createQuery(
                    "from Book b where b.price > (select avg(b2.price) from Book b2)", Book.class)
                    .getResultList();
            System.out.println("\n=== PODZAPYTANIE: ksiazki drozsze niz SREDNIA ===");
            aboveAverage.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            List<Author> authorsWithBooks = session.createQuery(
                    "from Author a where exists (select 1 from Book b where b.author = a)", Author.class)
                    .getResultList();
            System.out.println("\n=== PODZAPYTANIE: EXISTS (autorzy MAJACY choc jedna ksiazke) ===");
            authorsWithBooks.forEach(a -> System.out.println(" - " + a.getName()));

            /*
             * ============================================================
             * 🔹 PROJEKCJE DTO PRZEZ "select new ..."
             * ============================================================
             * Zamiast ładować CAŁE encje Author/Book (ze wszystkimi
             * polami i powiązaniami), "select new PelnaNazwaKlasy(...)"
             * pozwala od razu zbudować LEKKI obiekt DTO zawierający
             * TYLKO potrzebne pola - mniej danych do przesłania,
             * mniejsze obciążenie pamięci, brak ryzyka przypadkowego
             * dotknięcia LAZY powiązania poza sesją.
             */
            String dtoClassName = BookSummary.class.getName();
            List<BookSummary> summaries = session.createQuery(
                    "select new " + dtoClassName + "(b.title, a.name) from Book b join b.author a",
                    BookSummary.class).getResultList();
            System.out.println("\n=== PROJEKCJA DTO (select new BookSummary(...)) ===");
            summaries.forEach(s -> System.out.println(" - " + s.title() + " autorstwa " + s.authorName()));

            session.close();

            /*
             * ============================================================
             * 🔍 BULK UPDATE/DELETE - OPERACJE MASOWE
             * ============================================================
             * "update Book set price = price * 1.1" wykonuje SIĘ
             * BEZPOŚREDNIO w bazie danych JEDNYM zapytaniem UPDATE - z
             * pominięciem mechanizmu dirty checking i cache PIERWSZEGO
             * poziomu (Lesson17/23)! To oznacza, że obiekty JUŻ
             * załadowane w bieżącej Session NIE zostaną automatycznie
             * odświeżone - jeśli potrzebujesz ich aktualnego stanu w
             * pamięci, musisz jawnie session.refresh(obj) albo
             * session.clear() i załadować ponownie. Bulk operacje są
             * ZNACZNIE wydajniejsze niż pętla "znajdź + zmień + zapisz"
             * dla dużej liczby wierszy.
             */
            Session bulkSession = sessionFactory.openSession();
            Transaction bulkTx = bulkSession.beginTransaction();
            int updatedRows = bulkSession.createMutationQuery("update Book set price = price * 1.1").executeUpdate();
            bulkTx.commit();
            bulkSession.close();

            System.out.println("\n=== BULK UPDATE ===");
            System.out.println("Zaktualizowano " + updatedRows + " wierszy JEDNYM zapytaniem UPDATE (pominieto dirty checking).");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - join fetch (Lesson15) dociąga powiązanie jednym zapytaniem;
         *   zwykły join tego NIE robi.
         * - Funkcje agregujące (COUNT/SUM/AVG/MIN/MAX) + GROUP BY/HAVING
         *   działają analogicznie do SQL, na polach encji.
         * - Podzapytania w WHERE/EXISTS - pełna moc SQL wewnątrz HQL.
         * - "select new PelnaNazwaKlasy(...)" - lekka projekcja DTO,
         *   zamiast ładowania całych encji.
         * - Bulk update/delete - wydajne operacje masowe, ale POMIJAJĄ
         *   dirty checking i cache pierwszego poziomu (Lesson17/23).
         */

        System.out.println("\n=== KONIEC LEKCJI 19 ===");
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
        session.persist(book("Kordian", 60.0, slowacki));

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
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson19hqladvanced;DB_CLOSE_DELAY=-1");
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

    public record BookSummary(String title, String authorName) {
    }

    @Entity(name = "Author")
    @Table(name = "author")
    public static class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public String getName() {
            return name;
        }

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
