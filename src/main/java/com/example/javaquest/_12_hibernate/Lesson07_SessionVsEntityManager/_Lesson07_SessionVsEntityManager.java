package com.example.javaquest._12_hibernate.Lesson07_SessionVsEntityManager;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
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

public class _Lesson07_SessionVsEntityManager {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: Session (Hibernate natywny) vs EntityManager (JPA standard) ===");

        /*
         * ============================================================
         * 📦 EntityManager - STANDARD JPA, PRZENOŚNY MIĘDZY DOSTAWCAMI
         * ============================================================
         * EntityManager to interfejs zdefiniowany przez STANDARD JPA
         * (pakiet jakarta.persistence). Kod napisany WYŁĄCZNIE przy
         * użyciu EntityManager (bez rzutowania na klasy Hibernate)
         * teoretycznie zadziała z KAŻDĄ implementacją JPA - Hibernate,
         * EclipseLink, OpenJPA - bez zmiany ani linijki kodu biznesowego
         * (zmieniłaby się tylko konfiguracja/zależność Maven).
         *
         * To duża zaleta w projektach, gdzie przenośność między
         * dostawcami JPA jest istotna (rzadkie w praktyce - większość
         * projektów zostaje z Hibernate na zawsze - ale to WŁAŚNIE
         * powód, dla którego JPA jako standard istnieje).
         */
        System.out.println("\n=== EntityManager (JPA) ===");
        System.out.println("Standard - kod dziala z KAZDA implementacja JPA (Hibernate, EclipseLink...).");

        /*
         * ============================================================
         * 🔹 Session - NATYWNE, BOGATSZE API HIBERNATE
         * ============================================================
         * org.hibernate.Session to interfejs Hibernate-specific -
         * WAŻNE: od Hibernate 5+ interfejs Session ROZSZERZA
         * EntityManager (Session extends EntityManager) - czyli KAŻDA
         * Session JEST JUŻ EntityManagerem, ale ma DODATKOWO metody,
         * których w samym JPA nie ma, np.:
         * - session.load(Klasa, id) - zwraca LENIWY PROXY (nie odpytuje
         *   od razu bazy, rzuca wyjątek dopiero przy pierwszym użyciu,
         *   jeśli wiersz nie istnieje) - inaczej niż find(), które
         *   odpytuje bazę NATYCHMIAST i zwraca null, gdy brak wiersza.
         * - session.save(obj) - zwraca wygenerowany klucz od razu
         *   (Lesson06).
         * - session.setFlushMode(...) - kontrola nad flush (Lesson17).
         * - dostęp do natywnych Query/Criteria z rozszerzeniami
         *   specyficznymi dla Hibernate.
         */
        System.out.println("\n=== Session (Hibernate natywny) ===");
        System.out.println("Session ROZSZERZA EntityManager (Session extends EntityManager od Hibernate 5+).");
        System.out.println("Dodatkowe metody: load() (leniwy proxy), save(), setFlushMode()...");

        /*
         * ============================================================
         * 🔍 unwrap() - WYCIĄGANIE NATYWNEGO API Z EntityManager
         * ============================================================
         * Jeśli Twój kod trzyma zmienną typu EntityManager (bo np.
         * dostał ją z frameworka trzymającego się czystego JPA), a
         * potrzebujesz funkcji Hibernate-specific (np. session.load()),
         * możesz "wyciągnąć" natywną Session przez unwrap():
         *
         *   Session nativeSession = entityManager.unwrap(Session.class);
         *
         * To świadome, JAWNE przejście z powrotem na API konkretnego
         * dostawcy - kompilator/czytelnik kodu widzi dokładnie, w
         * którym miejscu tracisz przenośność między implementacjami JPA.
         */
        System.out.println("\n=== unwrap() ===");
        System.out.println("entityManager.unwrap(Session.class) - jawne przejscie na natywne API Hibernate.");

        /*
         * ============================================================
         * 🔹 KIEDY JPA, A KIEDY HIBERNATE-SPECIFIC?
         * ============================================================
         * - Trzymaj się CZYSTEGO JPA (EntityManager, persist/find/merge/
         *   remove, JPQL), gdy: piszesz bibliotekę/kod mający działać z
         *   różnymi dostawcami, albo po prostu nie potrzebujesz niczego
         *   spoza standardu - JPA pokrywa 90%+ typowych potrzeb.
         * - Sięgnij po Hibernate-specific (Session, HQL-specific
         *   rozszerzenia, @Cache Hibernate'owe adnotacje z Lesson24),
         *   gdy potrzebujesz KONKRETNEJ funkcji, której JPA nie ma
         *   (np. session.load() z leniwym proxy, drobne rozszerzenia
         *   HQL względem JPQL - Lesson18/19 pokażą różnice).
         *
         * 📌 W praktyce większość projektów Spring Boot + Hibernate
         * używa GŁÓWNIE JPA (Spring Data JPA operuje na JpaRepository,
         * które "pod spodem" używa EntityManager) - i tylko OKAZJONALNIE
         * sięga po unwrap() dla konkretnej funkcji Hibernate.
         */
        printWhenToUseTable();

        /*
         * ============================================================
         * 🔍 ZESTAWIENIE API SIDE-BY-SIDE + DZIAŁAJĄCY PRZYKŁAD
         * ============================================================
         */
        printApiComparisonTable();

        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Product product = new Product();
            product.setName("Klawiatura mechaniczna");
            session.persist(product);
            transaction.commit();

            // session JEST JUZ EntityManagerem - mozemy uzyc metody JPA wprost.
            EntityManager entityManagerView = session;
            Product foundByJpaApi = entityManagerView.find(Product.class, product.getId());
            System.out.println("\n=== TA SAMA Session UZYTA JAKO EntityManager (JPA) ===");
            System.out.println("find() (metoda JPA) zwrocilo: " + foundByJpaApi.getName());

            // ...i mozemy jej uzyc jako natywnej Session (Hibernate-specific).
            Product loadedByNativeApi = session.load(Product.class, product.getId());
            System.out.println("\n=== TA SAMA Session UZYTA NATYWNIE (Hibernate) ===");
            System.out.println("load() (metoda natywna Hibernate) zwrocilo proxy dla: " + loadedByNativeApi.getName());

            session.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - EntityManager - standard JPA, przenośny między dostawcami.
         * - Session - natywne API Hibernate, ROZSZERZA EntityManager
         *   (każda Session JEST EntityManagerem + ma dodatkowe metody).
         * - unwrap(Klasa) - jawne przejście z EntityManager na natywne
         *   API konkretnego dostawcy.
         * - Domyślnie trzymaj się JPA; sięgaj po Session tylko po
         *   konkretną funkcję, której standard nie oferuje.
         */

        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson07sessionvsem;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Product.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printWhenToUseTable() {
        System.out.println("\n=== KIEDY JPA, A KIEDY HIBERNATE-SPECIFIC ===");
        String format = "%-30s | %-40s%n";
        System.out.printf(format, "Sytuacja", "Wybor");
        System.out.println("-".repeat(72));
        System.out.printf(format, "Biblioteka/kod przenosny", "Czysty JPA (EntityManager)");
        System.out.printf(format, "Potrzebna konkretna funkcja Hibernate", "unwrap(Session.class) + API natywne");
        System.out.printf(format, "Typowy kod biznesowy (90% przypadkow)", "JPA wystarcza w zupelnosci");
    }

    private static void printApiComparisonTable() {
        System.out.println("\n=== API SIDE-BY-SIDE ===");
        String format = "%-25s | %-20s | %-20s%n";
        System.out.printf(format, "Funkcja", "EntityManager (JPA)", "Session (Hibernate)");
        System.out.println("-".repeat(70));
        System.out.printf(format, "Zapis nowej encji", "persist()", "persist() / save()");
        System.out.printf(format, "Odczyt natychmiastowy", "find()", "get()");
        System.out.printf(format, "Odczyt leniwy (proxy)", "getReference()", "load()");
        System.out.printf(format, "Aktualizacja detached", "merge()", "merge()");
    }

    @Entity(name = "Product")
    @Table(name = "product")
    public static class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
