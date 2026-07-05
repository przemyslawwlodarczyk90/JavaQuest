package com.example.javaquest._12_hibernate.Lesson12_OneToManyAndManyToOne;

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
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson12_OneToManyAndManyToOne {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: RELACJE JEDEN-DO-WIELU I WIELE-DO-JEDNEGO ===");

        /*
         * ============================================================
         * 📦 @ManyToOne - STRONA POSIADAJĄCA (WŁAŚCICIEL RELACJI)
         * ============================================================
         * @ManyToOne to strona relacji, która MA kolumnę klucza obcego
         * (@JoinColumn) w SWOJEJ tabeli - np. tabela "book" ma kolumnę
         * "author_id" wskazującą wiersz w tabeli "author". To WŁAŚNIE
         * ta strona jest "właścicielem" relacji w rozumieniu Hibernate -
         * TO ONA decyduje, jaki FK trafi do bazy przy zapisie.
         *
         * Praktyczna zasada: @ManyToOne PRAWIE ZAWSZE powinno mieć
         * jawny @JoinColumn(name = "...") - żeby nazwa kolumny klucza
         * obcego była czytelna i przewidywalna, a nie domyślna
         * (wygenerowana z nazwy pola).
         */
        System.out.println("\n=== @ManyToOne (strona posiadajaca) ===");
        System.out.println("Ma kolumne FK w SWOJEJ tabeli (np. book.author_id) - ZAWSZE z jawnym @JoinColumn.");

        /*
         * ============================================================
         * 🔹 @OneToMany(mappedBy=...) - STRONA ODWROTNA (KOLEKCJA)
         * ============================================================
         * @OneToMany po stronie "jeden" (np. Author.books: List<Book>)
         * to strona ODWROTNA (inverse) - NIE ma własnej kolumny FK,
         * tylko WSKAZUJE (przez mappedBy = "author") na pole @ManyToOne
         * po drugiej stronie, które FAKTYCZNIE steruje kluczem obcym.
         *
         * ⚠️ Typowy błąd początkujących: dodanie elementu TYLKO do
         * kolekcji strony odwrotnej (author.getBooks().add(book)) BEZ
         * ustawienia book.setAuthor(author) - Hibernate zapisze
         * WYŁĄCZNIE to, co widzi po stronie WŁAŚCICIELA relacji
         * (@ManyToOne) - sama kolekcja po stronie mappedBy jest tylko
         * "widokiem", nie steruje SQL-em. Rozwiązanie: metody pomocnicze
         * (patrz niżej).
         */
        System.out.println("\n=== @OneToMany(mappedBy=...) (strona odwrotna) ===");
        System.out.println("BEZ wlasnej kolumny FK - to tylko 'widok' na relacje sterowana przez @ManyToOne.");
        System.out.println("PULAPKA: dodanie TYLKO do kolekcji (bez ustawienia @ManyToOne po drugiej stronie) NIE zapisze relacji!");

        /*
         * ============================================================
         * 🔍 orphanRemoval=true - AUTOMATYCZNE USUWANIE OSIEROCONYCH
         * ============================================================
         * orphanRemoval=true na @OneToMany oznacza: jeśli element
         * ZOSTANIE USUNIĘTY Z KOLEKCJI rodzica (np. author.getBooks().
         * remove(book)) - Hibernate automatycznie USUNIE ten wiersz Z
         * BAZY przy najbliższym flush/commit, nawet jeśli NIKT nie
         * wywołał jawnie session.remove(book). To różni się od
         * CascadeType.REMOVE (Lesson14), który reaguje TYLKO na
         * usunięcie CAŁEGO rodzica - orphanRemoval reaguje też na samo
         * "odłączenie" JEDNEGO dziecka od kolekcji, bez usuwania rodzica.
         */
        System.out.println("\n=== orphanRemoval=true ===");
        System.out.println("Usuniecie elementu Z KOLEKCJI rodzica -> automatyczne DELETE tego wiersza w bazie.");

        /*
         * ============================================================
         * 🔹 METODY POMOCNICZE - SPÓJNOŚĆ OBU STRON W PAMIĘCI
         * ============================================================
         * Żeby uniknąć pułapki opisanej wyżej, DOBRA PRAKTYKA to metody
         * pomocnicze na stronie "jeden" (Author), które AKTUALIZUJĄ OBIE
         * strony relacji naraz:
         *
         *   void addBook(Book book) {
         *       books.add(book);
         *       book.setAuthor(this);
         *   }
         *   void removeBook(Book book) {
         *       books.remove(book);
         *       book.setAuthor(null);
         *   }
         *
         * Dzięki temu KOD WOŁAJĄCY (author.addBook(newBook)) nigdy nie
         * musi pamiętać o ręcznym ustawieniu obu stron osobno.
         */
        System.out.println("\n=== METODY POMOCNICZE (addBook/removeBook) ===");
        System.out.println("Aktualizuja OBIE strony relacji naraz - kod wolajacy nie musi pamietac o tym recznie.");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: Author (JEDEN) I Book (WIELE)
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Author author = new Author();
            author.setName("Henryk Sienkiewicz");
            session.persist(author);

            Book book1 = new Book();
            book1.setTitle("Potop");
            author.addBook(book1);
            session.persist(book1);

            Book book2 = new Book();
            book2.setTitle("Krzyzacy");
            author.addBook(book2);
            session.persist(book2);

            transaction.commit();
            session.close();

            Session readSession = sessionFactory.openSession();
            Author loadedAuthor = readSession.find(Author.class, author.getId());
            System.out.println("\n=== ODCZYT Author + kolekcja Book (@OneToMany) ===");
            System.out.println(loadedAuthor.getName() + " napisal " + loadedAuthor.getBooks().size() + " ksiazek:");
            loadedAuthor.getBooks().forEach(b -> System.out.println(" - " + b.getTitle()));

            // Usuniecie ksiazki Z KOLEKCJI -> orphanRemoval usunie ja tez z bazy.
            Transaction removeTx = readSession.beginTransaction();
            Book toRemove = loadedAuthor.getBooks().get(0);
            loadedAuthor.removeBook(toRemove);
            removeTx.commit();
            readSession.close();

            Session verifySession = sessionFactory.openSession();
            Author afterRemoval = verifySession.find(Author.class, author.getId());
            System.out.println("\n=== PO orphanRemoval (usunieto '" + toRemove.getTitle() + "') ===");
            System.out.println("Pozostalo ksiazek: " + afterRemoval.getBooks().size());
            verifySession.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @ManyToOne - strona posiadająca, ma FK, zawsze z jawnym
         *   @JoinColumn.
         * - @OneToMany(mappedBy=...) - strona odwrotna, sama kolekcja
         *   NIE steruje SQL-em - liczy się stan po stronie @ManyToOne.
         * - orphanRemoval=true - usunięcie z kolekcji = automatyczny
         *   DELETE, niezależnie od usunięcia całego rodzica.
         * - Metody pomocnicze (addBook/removeBook) utrzymują spójność
         *   OBU stron relacji w pamięci Javy.
         */

        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson12onetomany;DB_CLOSE_DELAY=-1");
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

        @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Book> books = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
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

        public void removeBook(Book book) {
            books.remove(book);
            book.setAuthor(null);
        }
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @ManyToOne
        @JoinColumn(name = "author_id")
        private Author author;

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
}
