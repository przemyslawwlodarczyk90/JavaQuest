package com.example.javaquest._12_hibernate.Lesson24_SecondLevelCacheAndQueryCache;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.Statistics;

public class _Lesson24_SecondLevelCacheAndQueryCache {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 24: CACHE DRUGIEGO POZIOMU I CACHE ZAPYTAŃ ===");

        /*
         * ============================================================
         * 📦 SECOND-LEVEL CACHE - OPCJONALNY, WSPÓŁDZIELONY MIĘDZY SESJAMI
         * ============================================================
         * W przeciwieństwie do first-level cache (Lesson23, wbudowany,
         * per-Session), second-level cache (L2) jest OPCJONALNY (trzeba
         * go jawnie skonfigurować i włączyć) i WSPÓŁDZIELONY przez CAŁĄ
         * SessionFactory - czyli między WSZYSTKIMI Session, nawet
         * zamkniętymi i otwartymi na nowo. To pozwala uniknąć
         * odpytywania bazy dla danych, które są odczytywane CZĘSTO, a
         * zmieniają się RZADKO.
         */
        System.out.println("\n=== SECOND-LEVEL CACHE ===");
        System.out.println("OPCJONALNY, wspoldzielony przez CALA SessionFactory (miedzy WSZYSTKIMI Session).");

        /*
         * ============================================================
         * 🔹 KONFIGURACJA: hibernate-jcache + Ehcache
         * ============================================================
         * Hibernate NIE MA własnej implementacji cache'a - deleguje do
         * standardu JCache (JSR-107) przez moduł hibernate-jcache, a
         * FAKTYCZNYM dostawcą cache'a jest Ehcache (zależności
         * hibernate-jcache + org.ehcache:ehcache, dodane do pom.xml
         * tego kursu specjalnie na potrzeby tej lekcji).
         */
        printCacheConfigTable();

        /*
         * ============================================================
         * 🔍 @Cacheable + @Cache(usage=...) - KTÓRE ENCJE TRAFIAJĄ DO L2
         * ============================================================
         * Samo włączenie L2 w konfiguracji NIE oznacza, że WSZYSTKIE
         * encje automatycznie do niego trafiają - każda encja musi być
         * JAWNIE oznaczona:
         * - @Cacheable (adnotacja JPA) - włącza cache dla tej encji.
         * - @Cache(usage = CacheConcurrencyStrategy...) (Hibernate) -
         *   określa STRATEGIĘ współbieżności: READ_WRITE (bezpieczne dla
         *   odczytu+zapisu, z blokadami - najczęstszy wybór),
         *   READ_ONLY (najszybsze, ale TYLKO dla danych, które NIGDY się
         *   nie zmieniają po zapisie), NONSTRICT_READ_WRITE (luźniejsza
         *   spójność, wyższa wydajność).
         */
        System.out.println("\n=== @Cacheable + @Cache(usage=...) ===");
        System.out.println("Musisz JAWNIE oznaczyc kazda encje - L2 NIE dziala automatycznie dla wszystkich.");

        /*
         * ============================================================
         * 🔹 DEMO: L2 CACHE MIĘDZY RÓŻNYMI Session
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Long bookId = seedData(sessionFactory);
            Statistics statistics = sessionFactory.getStatistics();

            Session firstSession = sessionFactory.openSession();
            Book firstLoad = firstSession.find(Book.class, bookId);
            firstSession.close();
            System.out.println("\n=== PIERWSZE find() (NOWA Session, dane jeszcze NIE w L2) ===");
            System.out.println("Zaladowano: " + firstLoad.getTitle() + " | trafien L2: " + statistics.getSecondLevelCacheHitCount());

            Session secondSession = sessionFactory.openSession();
            Book secondLoad = secondSession.find(Book.class, bookId);
            secondSession.close();
            System.out.println("\n=== DRUGIE find() (INNA, NOWA Session - patrz brak zapytania SQL ponizej) ===");
            System.out.println("Zaladowano: " + secondLoad.getTitle() + " | trafien L2: " + statistics.getSecondLevelCacheHitCount() + " (>0 = dane wziete Z CACHE, nie z bazy!)");

            /*
             * ============================================================
             * 🔍 QUERY CACHE - CACHE WYNIKÓW ZAPYTAŃ
             * ============================================================
             * Query cache to ODDZIELNY mechanizm od cache'a encji - cache'uje
             * WYNIK KONKRETNEGO zapytania (listę Id pasujących wierszy).
             * Wymaga: hibernate.cache.use_query_cache=true ORAZ jawnego
             * query.setCacheable(true) na KAŻDYM zapytaniu, które ma być
             * cache'owane (domyślnie WYŁĄCZONE nawet gdy L2 jest włączone).
             */
            Session queryCacheSession = sessionFactory.openSession();
            List<Book> firstQueryResult = queryCacheSession.createQuery("from Book b where b.price < :maxPrice", Book.class)
                    .setParameter("maxPrice", 100.0)
                    .setCacheable(true)
                    .getResultList();
            queryCacheSession.close();

            Session secondQueryCacheSession = sessionFactory.openSession();
            List<Book> secondQueryResult = secondQueryCacheSession.createQuery("from Book b where b.price < :maxPrice", Book.class)
                    .setParameter("maxPrice", 100.0)
                    .setCacheable(true)
                    .getResultList();
            secondQueryCacheSession.close();

            System.out.println("\n=== QUERY CACHE (to samo zapytanie, DWIE rozne Session) ===");
            System.out.println("Wynik 1: " + firstQueryResult.size() + " ksiazek, Wynik 2: " + secondQueryResult.size() + " ksiazek.");
            System.out.println("Trafien query cache: " + statistics.getQueryCacheHitCount());
        }

        /*
         * ============================================================
         * 📌 KIEDY L2 POMAGA, A KIEDY SZKODZI
         * ============================================================
         * POMAGA: dane odczytywane CZĘSTO, zmieniane RZADKO (np. lista
         * krajów, kategorii produktów, konfiguracja aplikacji) - ogromna
         * redukcja zapytań do bazy.
         *
         * SZKODZI (albo nie pomaga): dane zmieniające się CZĘSTO (np.
         * stan magazynowy w czasie rzeczywistym) - cache musiałby być
         * ciągle unieważniany, a przy NONSTRICT_READ_WRITE ryzykujesz
         * odczyt NIEAKTUALNYCH danych między unieważnieniem a odczytem.
         * W systemach ROZPROSZONYCH (wiele instancji aplikacji) L2 musi
         * być DODATKOWO skonfigurowany jako cache rozproszony (np.
         * Ehcache z klastrowaniem/Hazelcast) - inaczej każda instancja
         * ma WŁASNY, niezsynchronizowany cache.
         */
        System.out.println("\n=== KIEDY L2 POMAGA, A KIEDY SZKODZI ===");
        System.out.println("POMAGA: dane czesto czytane, rzadko zmieniane. SZKODZI: dane czesto zmieniane / wiele instancji bez cache rozproszonego.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Second-level cache - opcjonalny, współdzielony przez CAŁĄ
         *   SessionFactory (w przeciwieństwie do L1 z Lesson23).
         * - Konfiguracja przez hibernate-jcache + Ehcache (dostawca
         *   JSR-107), hibernate.cache.use_second_level_cache=true.
         * - @Cacheable + @Cache(usage=...) - jawne oznaczenie KAŻDEJ
         *   encji, która ma trafiać do L2 (nic nie dzieje się domyślnie).
         * - Query cache - OSOBNY mechanizm, cache'uje wyniki zapytań,
         *   wymaga setCacheable(true) na każdym zapytaniu.
         * - L2 pomaga przy danych rzadko zmienianych, może szkodzić przy
         *   częstych zmianach albo w systemach rozproszonych bez
         *   dodatkowej konfiguracji.
         */

        System.out.println("\n=== KONIEC LEKCJI 24 ===");
    }

    private static Long seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = new Book();
        book.setTitle("Ogniem i mieczem");
        book.setPrice(55.0);
        session.persist(book);
        transaction.commit();
        session.close();
        return book.getId();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson24secondlevelcache;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.generate_statistics", "true");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "true");
        configuration.setProperty("hibernate.cache.use_query_cache", "true");
        configuration.setProperty("hibernate.cache.region.factory_class", "jcache");
        configuration.setProperty("hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
        configuration.setProperty("hibernate.javax.cache.missing_cache_strategy", "create");
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printCacheConfigTable() {
        System.out.println("\n=== KONFIGURACJA L2 (hibernate-jcache + Ehcache) ===");
        String format = "%-45s | %-30s%n";
        System.out.printf(format, "Wlasciwosc", "Wartosc");
        System.out.println("-".repeat(78));
        System.out.printf(format, "hibernate.cache.use_second_level_cache", "true");
        System.out.printf(format, "hibernate.cache.region.factory_class", "jcache");
        System.out.printf(format, "hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
    }

    @Entity(name = "Book")
    @Table(name = "book")
    @Cacheable
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private double price;

        public Long getId() {
            return id;
        }

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
