package com.example.javaquest._12_hibernate.Lesson04_FirstEntityAndBasicMapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson04_FirstEntityAndBasicMapping {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: PIERWSZA ENCJA: @Entity, @Table, @Id, @Column ===");

        /*
         * ============================================================
         * 📦 @Entity - MINIMALNE WYMAGANIA
         * ============================================================
         * Żeby zwykła klasa Java stała się "encją" (czyli klasą, którą
         * Hibernate potrafi zapisać do/odczytać z bazy), musi spełnić
         * kilka warunków:
         * - adnotacja @Entity na klasie,
         * - BEZARGUMENTOWY konstruktor (Hibernate tworzy obiekty przez
         *   refleksję, potem woła settery/ustawia pola bezpośrednio),
         * - dokładnie JEDNO pole oznaczone @Id (klucz główny, Lesson05
         *   rozwinie strategie jego generowania),
         * - klasa NIE MOŻE być "final" (Hibernate czasem tworzy
         *   dynamiczne podklasy - proxy - do leniwego ładowania,
         *   Lesson15 pokaże to przy okazji fetch types).
         *
         * W tym kursie encje żyją jako STATIC NESTED CLASS wewnątrz
         * pliku lekcji (ten sam wzorzec co np. record OrderItem w
         * _10_dao/Lesson08) - to w pełni poprawne dla Hibernate, o ile
         * klasa jest "static" (nie wewnętrzna, niestatyczna).
         */
        System.out.println("\n=== WYMAGANIA @Entity ===");
        System.out.println("1. Adnotacja @Entity.");
        System.out.println("2. Bezargumentowy konstruktor.");
        System.out.println("3. Dokladnie jedno pole @Id.");
        System.out.println("4. Klasa NIE moze byc 'final'.");

        /*
         * ============================================================
         * 🔹 @Table(name=...) - JAWNE MAPOWANIE NA TABELĘ
         * ============================================================
         * Bez @Table Hibernate użyłby nazwy KLASY jako nazwy tabeli - a
         * ponieważ nasza encja jest zagnieżdżoną klasą statyczną, ta
         * domyślna nazwa wyglądałaby brzydko (np. zawierałaby znak "$").
         * @Table(name = "book") pozwala jawnie nadać CZYSTĄ nazwę
         * tabeli SQL, niezależną od struktury pakietów/klas w Javie.
         */
        System.out.println("\n=== @Table(name=...) ===");
        System.out.println("Bez @Table Hibernate uzylby nazwy klasy Javy jako nazwy tabeli - brzydko dla klas zagniezdzonych.");
        System.out.println("@Table(name = \"book\") -> jawna, czysta nazwa tabeli SQL.");

        /*
         * ============================================================
         * ⚠️ WAŻNE: @Entity(name = "...") DLA KLAS ZAGNIEŻDŻONYCH
         * ============================================================
         * Analogiczny problem dotyczy SAMEJ nazwy encji używanej w HQL/
         * JPQL (Lesson18+): dla klasy zagnieżdżonej (jak nasze encje w
         * tym kursie) domyślna nazwa encji bywa PEŁNĄ, BINARNĄ nazwą
         * Javy (np. "_Lesson04_FirstEntityAndBasicMapping$Book" - ze
         * znakiem "$"), a NIE czystym "Book". Zapytanie "from Book"
         * zakończyłoby się wtedy błędem "Could not resolve root entity
         * 'Book'"! Dlatego KAŻDA encja w tym rozdziale ma jawne
         * @Entity(name = "Book") - to wymusza czystą, prostą nazwę,
         * niezależną od zagnieżdżenia klasy. W REALNYM projekcie, gdzie
         * encje zwykle są osobnymi, NIE zagnieżdżonymi plikami .java,
         * ten problem w ogóle nie występuje - to specyfika sposobu, w
         * jaki zbudowany jest ten kurs (jedna klasa na plik lekcji).
         */
        System.out.println("\n=== @Entity(name=...) - WAZNE DLA KLAS ZAGNIEZDZONYCH ===");
        System.out.println("Bez tego HQL 'from Book' moglby nie znalezc encji (domyslna nazwa = pelna nazwa z '$').");
        System.out.println("Dlatego KAZDA encja w tym rozdziale ma jawne @Entity(name = \"...\") - patrz klasa Book nizej.");

        /*
         * ============================================================
         * 🔍 @Column - MAPOWANIE PÓL NA KOLUMNY
         * ============================================================
         * Podobnie jak z @Table - bez @Column pole "title" automatycznie
         * zmapowałoby się na kolumnę "title" (Hibernate używa nazwy pola
         * jako domyślnej nazwy kolumny). @Column pozwala to NADPISAĆ i
         * dodać ograniczenia:
         * - name    - inna nazwa kolumny niż nazwa pola w Javie,
         * - nullable = false - kolumna NOT NULL (walidowana przez baze,
         *   NIE przez samą Javę - patrz Lesson28 o Bean Validation dla
         *   walidacji PRZED wysłaniem do bazy),
         * - length  - maksymalna długość dla VARCHAR (domyślnie 255).
         */
        System.out.println("\n=== @Column ===");
        System.out.println("name/nullable/length - kontrola nad kolumna SQL wygenerowana dla danego pola.");

        /*
         * ============================================================
         * 🔹 PIERWSZY ZAPIS I ODCZYT - PEŁNY PRZYKŁAD
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Book book = new Book();
            book.setTitle("Pan Tadeusz");
            book.setPages(320);
            session.persist(book);

            transaction.commit();
            session.close();

            System.out.println("\n=== ZAPIS (session.persist) ===");
            System.out.println("Zapisano ksiazke o wygenerowanym id=" + book.getId());

            // Nowa Session do odczytu - symuluje "kolejne zadanie" w aplikacji.
            Session readSession = sessionFactory.openSession();
            Book loaded = readSession.find(Book.class, book.getId());
            readSession.close();

            System.out.println("\n=== ODCZYT (session.find) ===");
            System.out.println("Odczytana ksiazka: " + loaded.getTitle() + " (" + loaded.getPages() + " stron).");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @Entity + bezargumentowy konstruktor + jedno pole @Id +
         *   klasa nie-final - minimalne wymagania encji Hibernate.
         * - @Table(name=...) - jawna nazwa tabeli (ważne dla klas
         *   zagnieżdżonych, żeby uniknąć brzydkich nazw z "$").
         * - @Column(name=/nullable=/length=) - kontrola nad kolumną SQL.
         * - session.persist(obiekt) - zapis, session.find(Klasa, id) -
         *   odczyt po kluczu głównym (Lesson06 rozwinie pełen CRUD).
         */

        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson04entity;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "title", nullable = false, length = 200)
        private String title;

        @Column(name = "pages")
        private int pages;

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }
}
