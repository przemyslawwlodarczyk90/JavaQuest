package com.example.javaquest._12_hibernate.Lesson23_FirstLevelCache;

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

public class _Lesson23_FirstLevelCache {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 23: CACHE PIERWSZEGO POZIOMU (Session Cache) ===");

        /*
         * ============================================================
         * 📦 FIRST-LEVEL CACHE - WBUDOWANY, ZAWSZE WŁĄCZONY
         * ============================================================
         * Cache pierwszego poziomu to WEWNĘTRZNA "identity map" Session -
         * mapa Id -> obiekt, trzymająca WSZYSTKIE encje załadowane w
         * obrębie TEJ JEDNEJ Session. Jest WBUDOWANY w Hibernate i
         * ZAWSZE włączony - nie da się go wyłączyć ani skonfigurować -
         * to fundamentalna część działania Session (to właśnie ten
         * mechanizm umożliwia dirty checking, Lesson17).
         */
        System.out.println("\n=== FIRST-LEVEL CACHE ===");
        System.out.println("WBUDOWANY, ZAWSZE wlaczony - 'identity map' (Id -> obiekt) w obrebie JEDNEJ Session.");

        /*
         * ============================================================
         * 🔹 DEMO: DWA find() Z TYM SAMYM Id = JEDNO ZAPYTANIE SQL
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Long bookId = seedData(sessionFactory);

            Session session = sessionFactory.openSession();
            System.out.println("\n=== DWA find() z TYM SAMYM Id w JEDNEJ Session (patrz na liczbe 'select' ponizej) ===");
            Book first = session.find(Book.class, bookId);
            System.out.println("Pierwsze find() - zwrocilo: " + first.getTitle());
            Book second = session.find(Book.class, bookId);
            System.out.println("Drugie find()  - zwrocilo: " + second.getTitle() + " (BEZ dodatkowego zapytania SQL!)");
            System.out.println("Czy to TEN SAM obiekt w pamieci (first == second)? " + (first == second));
            session.close();

            /*
             * ============================================================
             * 🔍 ZASIĘG: CACHE ZNIKA WRAZ Z ZAMKNIĘCIEM Session
             * ============================================================
             * Cache pierwszego poziomu NIE jest dzielony między różnymi
             * Session - to jego kluczowa różnica względem cache'a
             * drugiego poziomu (Lesson24, dzielonego przez CAŁĄ
             * SessionFactory). Poniżej: NOWA Session, to samo Id -
             * ZAWSZE nowe zapytanie SQL, mimo że dane były już
             * odczytane w POPRZEDNIEJ Session chwilę wcześniej.
             */
            Session anotherSession = sessionFactory.openSession();
            System.out.println("\n=== NOWA Session, TO SAMO Id (zawsze NOWE zapytanie SQL) ===");
            Book third = anotherSession.find(Book.class, bookId);
            System.out.println("Znaleziono: " + third.getTitle() + " - ale to INNY obiekt w pamieci niz poprzednio.");
            System.out.println("Czy to TEN SAM obiekt co 'first' z poprzedniej Session (first == third)? " + (first == third));
            anotherSession.close();

            /*
             * ============================================================
             * 🔹 session.clear()/evict() - RĘCZNE CZYSZCZENIE
             * ============================================================
             * Przy przetwarzaniu WSADOWYM dużych zbiorów danych (np. pętla
             * ładująca i modyfikująca tysiące wierszy w JEDNEJ Session)
             * cache pierwszego poziomu ROŚNIE przez cały czas trwania
             * Session - może to doprowadzić do wyczerpania pamięci. Metody
             * czyszczące:
             * - session.evict(obiekt) - usuwa Z CACHE JEDEN konkretny obiekt
             *   (obiekt staje się detached, Lesson16).
             * - session.clear() - usuwa Z CACHE WSZYSTKIE obiekty naraz -
             *   typowy wzorzec w przetwarzaniu wsadowym: co N wierszy wywołaj
             *   flush() + clear(), żeby zwolnić pamięć.
             */
            Session batchSession = sessionFactory.openSession();
            Transaction transaction = batchSession.beginTransaction();
            for (int i = 1; i <= 5; i++) {
                Book book = new Book();
                book.setTitle("Wsadowa ksiazka " + i);
                batchSession.persist(book);
                if (i % 2 == 0) {
                    batchSession.flush();
                    batchSession.clear(); // czyszczenie cache co 2 wpisy - w praktyce co wieksza liczbe (np. 50-100)
                }
            }
            transaction.commit();
            batchSession.close();
            System.out.println("\n=== session.flush()+clear() PRZY PRZETWARZANIU WSADOWYM ===");
            System.out.println("Zapisano 5 ksiazek, czyszczac cache pierwszego poziomu co 2 wpisy (zapobiega naroslu pamieci).");
        }

        /*
         * ============================================================
         * 📌 IDENTITY (==) W JEDNEJ Session vs equals() MIĘDZY SESJAMI
         * ============================================================
         * Wewnątrz JEDNEJ Session, dwa find() z tym samym Id ZAWSZE
         * zwrócą TEN SAM obiekt w pamięci (first == second, patrz demo
         * wyżej) - to gwarancja "identity" cache'a pierwszego poziomu.
         * MIĘDZY różnymi Session identity (==) NIE jest gwarantowane -
         * dostajesz RÓŻNE obiekty (choć reprezentujące ten sam wiersz) -
         * do porównywania encji MIĘDZY sesjami należy użyć equals()
         * (typowo porównującego pole Id), NIGDY ==.
         */
        System.out.println("\n=== IDENTITY (==) vs equals() ===");
        System.out.println("W JEDNEJ Session: == dziala (ten sam obiekt). MIEDZY Session: uzywaj equals() (typowo po Id).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - First-level cache - wbudowany, zawsze włączony, "identity
         *   map" w obrębie JEDNEJ Session.
         * - Dwa find() z tym samym Id w tej samej Session = JEDNO
         *   zapytanie SQL + ten sam obiekt w pamięci.
         * - Cache znika wraz z zamknięciem Session - NIE dzielony
         *   między różnymi Session (w przeciwieństwie do L2, Lesson24).
         * - session.evict()/clear() - ręczne czyszczenie, ważne przy
         *   przetwarzaniu wsadowym dużych zbiorów.
         * - == działa TYLKO w obrębie jednej Session; między sesjami
         *   zawsze equals().
         */

        System.out.println("\n=== KONIEC LEKCJI 23 ===");
    }

    private static Long seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = new Book();
        book.setTitle("Quo Vadis");
        session.persist(book);
        transaction.commit();
        session.close();
        return book.getId();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson23firstlevelcache;DB_CLOSE_DELAY=-1");
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
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
