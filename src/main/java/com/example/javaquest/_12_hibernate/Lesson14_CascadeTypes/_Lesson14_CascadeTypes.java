package com.example.javaquest._12_hibernate.Lesson14_CascadeTypes;

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

public class _Lesson14_CascadeTypes {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: KASKADOWANIE OPERACJI: CascadeType ===");

        /*
         * ============================================================
         * 📦 CO PROPAGUJE KAŻDY CascadeType
         * ============================================================
         * Cascade (kaskadowanie) mówi Hibernate: "gdy wykonasz TĘ
         * operację na RODZICU, wykonaj JĄ TEŻ automatycznie na
         * WSZYSTKICH powiązanych DZIECKACH".
         *
         * - PERSIST - zapis rodzica automatycznie zapisuje TEŻ nowe
         *   dzieci (bez tego musiałbyś RĘCZNIE wywołać session.persist()
         *   na każdym dziecku osobno).
         * - MERGE   - scalenie (merge) rodzica scala TEŻ dzieci.
         * - REMOVE  - usunięcie rodzica usuwa TEŻ WSZYSTKIE dzieci
         *   (⚠️ patrz pułapka niżej).
         * - REFRESH - odświeżenie rodzica ze świeżymi danymi z bazy
         *   odświeża TEŻ dzieci.
         * - DETACH  - odłączenie rodzica od Session odłącza TEŻ dzieci.
         * - ALL     - skrót oznaczający WSZYSTKIE powyższe naraz.
         */
        printCascadeTypesTable();

        /*
         * ============================================================
         * 🔹 DOMYŚLNIE BRAK KASKADOWANIA
         * ============================================================
         * Bez jawnego "cascade = ..." na adnotacji @OneToMany/@ManyToOne/
         * @OneToOne/@ManyToMany, Hibernate NIE kaskaduje NICZEGO -
         * musiałbyś ręcznie wywołać session.persist() na KAŻDYM dziecku
         * osobno. To świadoma decyzja projektowa Hibernate: kaskadowanie
         * to "dodatkowa moc", którą trzeba jawnie WŁĄCZYĆ, żeby uniknąć
         * przypadkowego, niezamierzonego kasowania/zapisywania danych
         * "przy okazji" innej operacji.
         */
        System.out.println("\n=== BRAK KASKADOWANIA DOMYSLNIE ===");
        System.out.println("Bez jawnego cascade=... musisz RECZNIE persist() kazde dziecko osobno.");

        /*
         * ============================================================
         * 🔍 PUŁAPKA: CascadeType.REMOVE NA @ManyToOne
         * ============================================================
         * CascadeType.REMOVE ma SENS na @OneToMany (rodzic -> dzieci, np.
         * Author -> Book) - usunięcie autora logicznie usuwa jego
         * książki. ALE ta sama adnotacja na @ManyToOne (dziecko ->
         * rodzic, np. Book -> Author) oznaczałaby coś ABSURDALNEGO:
         * usunięcie JEDNEJ książki skasowałoby CAŁEGO autora (i przez
         * to WSZYSTKIE jego pozostałe książki, jeśli tamta strona też ma
         * cascade REMOVE)! To częsty, bardzo groźny błąd konfiguracyjny -
         * CascadeType.REMOVE prawie NIGDY nie powinno pojawić się po
         * stronie @ManyToOne.
         */
        System.out.println("\n=== PULAPKA: CascadeType.REMOVE na @ManyToOne ===");
        System.out.println("Usuniecie JEDNEGO dziecka (Book) skasowaloby CALEGO rodzica (Author) - PRAWIE NIGDY nie rob tego!");

        /*
         * ============================================================
         * 🔹 orphanRemoval vs CascadeType.REMOVE
         * ============================================================
         * Subtelna, ale ważna różnica (Lesson12 wprowadził orphanRemoval):
         * - CascadeType.REMOVE reaguje TYLKO na jawne usunięcie CAŁEGO
         *   rodzica (session.remove(author)) - wtedy kasuje też dzieci.
         * - orphanRemoval=true reaguje DODATKOWO na samo "odłączenie"
         *   JEDNEGO dziecka Z KOLEKCJI rodzica (author.getBooks().
         *   remove(book)) - bez usuwania całego rodzica - i TAKŻE
         *   skasuje ten jeden, osierocony wiersz.
         *
         * W praktyce na kolekcjach "silnie posiadanych" (dziecko nie ma
         * sensu bez rodzica, np. OrderItem bez Order) często włącza się
         * OBA naraz: cascade = ALL, orphanRemoval = true.
         */
        System.out.println("\n=== orphanRemoval vs CascadeType.REMOVE ===");
        System.out.println("CascadeType.REMOVE - reaguje na usuniecie CALEGO rodzica.");
        System.out.println("orphanRemoval=true - reaguje TAKZE na odlaczenie JEDNEGO dziecka z kolekcji.");

        /*
         * ============================================================
         * 🔹 DEMONSTRACJA KAŻDEGO TYPU KASKADY NA Author-Book
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            // CascadeType.PERSIST - zapis rodzica automatycznie zapisuje nowe dzieci.
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Author author = new Author();
            author.setName("Boleslaw Prus");
            Book book = new Book();
            book.setTitle("Lalka");
            author.addBook(book);

            session.persist(author); // BEZ recznego session.persist(book)!

            transaction.commit();
            session.close();

            System.out.println("\n=== CascadeType.PERSIST ===");
            System.out.println("Zapisano autora + ksiazke '" + book.getTitle() + "' JEDNYM persist() na rodzicu (id ksiazki=" + book.getId() + ").");

            // CascadeType.REMOVE - usuniecie rodzica kasuje TEZ dzieci.
            Session removeSession = sessionFactory.openSession();
            Transaction removeTx = removeSession.beginTransaction();
            Author loadedAuthor = removeSession.find(Author.class, author.getId());
            removeSession.remove(loadedAuthor);
            removeTx.commit();
            removeSession.close();

            Session verifySession = sessionFactory.openSession();
            Book afterRemoval = verifySession.find(Book.class, book.getId());
            verifySession.close();

            System.out.println("\n=== CascadeType.REMOVE ===");
            System.out.println("Po usunieciu autora, find() na Book zwraca: " + afterRemoval + " (null = ksiazka skasowana kaskadowo).");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PERSIST/MERGE/REMOVE/REFRESH/DETACH - propagują odpowiednią
         *   operację z rodzica na dzieci; ALL = wszystkie naraz.
         * - Domyślnie BRAK kaskadowania - trzeba je jawnie włączyć.
         * - CascadeType.REMOVE na @ManyToOne to niebezpieczna pułapka -
         *   usunięcie dziecka skasowałoby całego rodzica.
         * - orphanRemoval (Lesson12) reaguje na odłączenie JEDNEGO
         *   dziecka z kolekcji, CascadeType.REMOVE - tylko na usunięcie
         *   całego rodzica.
         */

        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson14cascade;DB_CLOSE_DELAY=-1");
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

    private static void printCascadeTypesTable() {
        System.out.println("\n=== CascadeType - CO PROPAGUJE ===");
        String format = "%-10s | %-50s%n";
        System.out.printf(format, "Typ", "Co propaguje z rodzica na dzieci");
        System.out.println("-".repeat(65));
        System.out.printf(format, "PERSIST", "zapis nowych dzieci razem z rodzicem");
        System.out.printf(format, "MERGE", "scalanie (merge) dzieci razem z rodzicem");
        System.out.printf(format, "REMOVE", "usuniecie dzieci razem z rodzicem (UWAGA na @ManyToOne!)");
        System.out.printf(format, "REFRESH", "odswiezenie dzieci ze swiezymi danymi z bazy");
        System.out.printf(format, "DETACH", "odlaczenie dzieci od Session razem z rodzicem");
        System.out.printf(format, "ALL", "wszystkie powyzsze naraz");
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

        public void setName(String name) {
            this.name = name;
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

        // UWAGA: swiadomie BEZ cascade=REMOVE po stronie @ManyToOne - patrz pulapka w tresci lekcji.
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

        public void setAuthor(Author author) {
            this.author = author;
        }
    }
}
