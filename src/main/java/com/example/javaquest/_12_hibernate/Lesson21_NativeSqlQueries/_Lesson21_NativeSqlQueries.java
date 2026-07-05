package com.example.javaquest._12_hibernate.Lesson21_NativeSqlQueries;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.service.ServiceRegistry;

public class _Lesson21_NativeSqlQueries {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 21: NATYWNE ZAPYTANIA SQL: createNativeQuery ===");

        /*
         * ============================================================
         * 📦 KIEDY HQL NIE WYSTARCZA
         * ============================================================
         * HQL/JPQL (Lesson18/19) pokrywa większość typowych potrzeb, ale
         * czasem trzeba zejść do CZYSTEGO SQL:
         * - funkcje specyficzne dla konkretnego silnika bazy (np.
         *   funkcje H2/PostgreSQL, których HQL nie zna),
         * - bardzo złożone raporty analityczne (window functions,
         *   CTE/WITH, zaawansowane agregacje) - czasem prościej i
         *   wydajniej napisać wprost SQL, niż wymuszać to na HQL,
         * - hinty wydajnościowe silnika bazy (np. wymuszenie konkretnego
         *   indeksu) - HQL nie ma na to standardowej składni.
         *
         * Hibernate NIE zabrania czystego SQL - createNativeQuery
         * pozwala wykonać DOWOLNY SQL, z zachowaniem integracji z
         * mechanizmami Hibernate (mapowanie wyniku na encje, cache).
         */
        System.out.println("\n=== KIEDY SIEGAC PO NATYWNE SQL ===");
        System.out.println("Funkcje specyficzne dla silnika bazy, bardzo zlozone raporty, hinty wydajnosciowe.");

        /*
         * ============================================================
         * 🔹 createNativeQuery(sql, EntityClass) - MAPOWANIE NA ENCJĘ
         * ============================================================
         * Gdy zapytanie SQL zwraca WSZYSTKIE kolumny odpowiadające
         * encji (SELECT * FROM book...), można poprosić Hibernate o
         * automatyczne zmapowanie wyniku NA TĘ encję - dokładnie jak
         * przy zwykłym HQL, tylko zapytanie jest już CZYSTYM SQL.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            seedData(sessionFactory);
            Session session = sessionFactory.openSession();

            List<Book> allBooks = session.createNativeQuery("select * from book", Book.class).getResultList();
            System.out.println("\n=== createNativeQuery(sql, Book.class) - mapowanie na encje ===");
            allBooks.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getPrice() + ")"));

            /*
             * ============================================================
             * 🔍 @SqlResultSetMapping + @ColumnResult - MAPOWANIE NA DTO
             * ============================================================
             * Gdy zapytanie NIE zwraca pełnej encji (np. tylko wybrane
             * kolumny albo wynik agregacji), @SqlResultSetMapping z
             * @ColumnResult pozwala zdefiniować NAZWANE mapowanie
             * kolumn wyniku - używane potem po nazwie w
             * createNativeQuery(sql, "nazwaMapowania") zamiast po klasie
             * encji. Dla mapowania na WŁASNY obiekt DTO (nie Object[])
             * użyłbyś dodatkowo @ConstructorResult, wskazując KTÓRA
             * kolumna trafia do KTÓREGO parametru konstruktora tego DTO.
             */
            List<Object[]> summaryRaw = session.createNativeQuery(
                            "select title, price from book where price > 40", "BookTitlePriceMapping")
                    .getResultList();
            System.out.println("\n=== @SqlResultSetMapping + @ColumnResult (nazwane mapowanie kolumn) ===");
            for (Object[] row : summaryRaw) {
                System.out.println(" - " + row[0] + ": " + row[1]);
            }

            /*
             * ============================================================
             * 🔹 PRZYKŁAD FUNKCJI SQL SPECYFICZNEJ DLA H2
             * ============================================================
             * H2 (podobnie jak inne silniki) ma WŁASNE, nieustandaryzowane
             * funkcje - np. FORMATDATETIME (formatowanie daty wg wzorca)
             * czy funkcje matematyczne spoza standardu ANSI SQL. HQL,
             * jako warstwa abstrakcji NAD wieloma silnikami, ich nie zna -
             * jedyna droga to native SQL.
             */
            NativeQuery<String> upperTitlesQuery = session.createNativeQuery(
                    "select upper(title) from book order by title", String.class);
            List<String> upperTitles = upperTitlesQuery.getResultList();
            System.out.println("\n=== FUNKCJA SQL (UPPER - dostepna tez w HQL, ale tu przez native SQL) ===");
            upperTitles.forEach(t -> System.out.println(" - " + t));

            session.close();
        }

        /*
         * ============================================================
         * 🔍 RYZYKO: UTRATA PRZENOŚNOŚCI
         * ============================================================
         * Zapytanie napisane w czystym SQL jest ZWIĄZANE z konkretnym
         * dialektem bazy danych - to, co działa w H2, może wymagać
         * PRZEPISANIA przy migracji na PostgreSQL/MySQL/Oracle (inna
         * składnia funkcji, inne słowa kluczowe). HQL, jako warstwa
         * abstrakcji Hibernate, tłumaczy TEN SAM kod na odpowiedni
         * dialekt AUTOMATYCZNIE - to główna przewaga HQL nad native SQL,
         * warta utraty w zamian za elastyczność tylko wtedy, gdy
         * REALNIE potrzebujesz czegoś, czego HQL nie oferuje.
         */
        System.out.println("\n=== RYZYKO: UTRATA PRZENOSNOSCI ===");
        System.out.println("Native SQL jest zwiazany z KONKRETNYM silnikiem bazy - migracja moze wymagac przepisania zapytan.");
        System.out.println("HQL tlumaczy TEN SAM kod na rozny dialekt automatycznie - to glowna przewaga nad native SQL.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Native SQL - dla funkcji specyficznych dla silnika bazy,
         *   złożonych raportów, hintów wydajnościowych.
         * - createNativeQuery(sql, Encja.class) - mapowanie wyniku na
         *   pełną encję (gdy zapytanie zwraca wszystkie jej kolumny).
         * - @SqlResultSetMapping + @ColumnResult/@ConstructorResult -
         *   mapowanie na DTO, gdy wynik jest częściowy/inny.
         * - Ryzyko: utrata przenośności między silnikami baz danych -
         *   świadomy kompromis za elastyczność, tylko gdy naprawdę
         *   potrzebna.
         */

        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }

    private static void seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(book("Pan Tadeusz", 45.0));
        session.persist(book("Ballady i romanse", 30.0));
        session.persist(book("Kordian", 60.0));

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
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson21nativesql;DB_CLOSE_DELAY=-1");
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
    @SqlResultSetMapping(
            name = "BookTitlePriceMapping",
            columns = {
                    @ColumnResult(name = "title"),
                    @ColumnResult(name = "price", type = Double.class)
            }
    )
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
