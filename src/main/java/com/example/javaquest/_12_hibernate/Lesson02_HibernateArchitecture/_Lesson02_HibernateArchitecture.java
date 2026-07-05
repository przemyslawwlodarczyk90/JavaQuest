package com.example.javaquest._12_hibernate.Lesson02_HibernateArchitecture;

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

public class _Lesson02_HibernateArchitecture {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: ARCHITEKTURA HIBERNATE: SessionFactory, Session, Configuration ===");

        /*
         * ============================================================
         * 📦 SessionFactory - CIĘŻKA FABRYKA, JEDNA NA CAŁĄ APLIKACJĘ
         * ============================================================
         * SessionFactory to obiekt reprezentujący "znajomość" Hibernate
         * o Twojej bazie danych i Twoim modelu encji - trzyma m.in.
         * skompilowane mapowania encji, pulę połączeń, konfigurację
         * cache'a drugiego poziomu (Lesson24).
         *
         * Tworzenie SessionFactory jest KOSZTOWNE (parsowanie adnotacji,
         * budowanie metadanych, walidacja mapowań) - dlatego tworzy się
         * ją RAZ, przy starcie aplikacji, i trzyma przez cały czas jej
         * działania. SessionFactory JEST thread-safe - wiele wątków może
         * bezpiecznie z niej korzystać jednocześnie (każdy otwierając
         * WŁASNĄ Session).
         */
        System.out.println("\n=== SessionFactory ===");
        System.out.println("Ciezka, kosztowna w tworzeniu -> tworzona RAZ, na cala aplikacje.");
        System.out.println("Thread-safe -> bezpiecznie dzielona miedzy watkami (kazdy otwiera WLASNA Session).");

        /*
         * ============================================================
         * 🔹 Session - LEKKA, JEDNORAZOWA JEDNOSTKA PRACY
         * ============================================================
         * Session to Twoje "okno" do bazy danych na czas JEDNEJ operacji
         * biznesowej (unit of work) - np. obsługi jednego żądania HTTP,
         * jednej metody serwisu. Session:
         * - jest LEKKA w tworzeniu (w przeciwieństwie do SessionFactory),
         * - NIE jest thread-safe - NIGDY nie współdziel jednej Session
         *   między wątkami,
         * - trzyma cache pierwszego poziomu (Lesson23) - "pamięć" o
         *   encjach załadowanych w JEJ obrębie,
         * - powinna być zamykana zaraz po zakończeniu pracy (try-with-
         *   resources, bo Session implementuje AutoCloseable).
         *
         * Analogia: SessionFactory to "fabryka połączeń z bankiem"
         * (tworzysz ją raz), a Session to "pojedyncza wizyta w banku"
         * (otwierasz i zamykasz ją dla każdej sprawy z osobna).
         */
        System.out.println("\n=== Session ===");
        System.out.println("Lekka, tworzona CZESTO (per unit-of-work) -> NIE jest thread-safe.");
        System.out.println("Analogia: SessionFactory = fabryka polaczen z bankiem, Session = pojedyncza wizyta.");

        /*
         * ============================================================
         * 🔍 Configuration I StandardServiceRegistryBuilder
         * ============================================================
         * Zbudowanie SessionFactory to DWUETAPOWY proces:
         *
         * 1. Configuration - zbiera WSZYSTKIE ustawienia (adres bazy,
         *    dialekt SQL, zarejestrowane klasy encji) w jeden obiekt.
         *    Może je wczytać z pliku hibernate.cfg.xml (natywny XML
         *    Hibernate) LUB - jak w tym kursie - ustawić programowo
         *    przez setProperty(...) (Lesson03 wyjaśni to dokładniej).
         *
         * 2. ServiceRegistry (budowany przez StandardServiceRegistryBuilder)
         *    - "kontener" niskopoziomowych usług Hibernate (dostawca
         *    połączeń JDBC, dialekt SQL, integracja z cache'em...),
         *    zbudowany na podstawie properties z Configuration.
         *
         * Dopiero MAJĄC oba te elementy, wywołujemy
         * configuration.buildSessionFactory(serviceRegistry) - i
         * otrzymujemy gotową, w pełni skonfigurowaną SessionFactory.
         */
        System.out.println("\n=== Configuration + ServiceRegistry -> SessionFactory ===");
        System.out.println("1. Configuration    - zbiera ustawienia (URL bazy, dialekt, klasy encji).");
        System.out.println("2. ServiceRegistry  - kontener niskopoziomowych uslug (polaczenia, dialekt).");
        System.out.println("3. buildSessionFactory(registry) -> gotowa SessionFactory.");

        /*
         * ============================================================
         * 🔹 WARSTWY HIBERNATE: ORM API, SPI, DIALEKTY SQL
         * ============================================================
         * - ORM API (górna warstwa) - to, z czym pracujesz na co dzień:
         *   Session, Transaction, Query, encje z adnotacjami.
         * - SPI (Service Provider Interface, warstwa środkowa) -
         *   "wtyczki" konfigurujące zachowanie Hibernate (dostawca
         *   connection poolingu, dostawca cache'a drugiego poziomu -
         *   Lesson24, integracja transakcyjna).
         * - Dialekt SQL (warstwa dolna) - klasa tłumacząca ogólne
         *   operacje Hibernate na KONKRETNY dialekt danej bazy (H2,
         *   PostgreSQL, MySQL mają subtelnie inną składnię SQL, inne
         *   funkcje). Od Hibernate 6 dialekt jest zwykle WYKRYWANY
         *   automatycznie na podstawie URL/metadanych JDBC - nie trzeba
         *   go już podawać ręcznie tak często, jak w starszych wersjach.
         */
        printLayersTable();

        /*
         * ============================================================
         * 🔍 PEŁNY PRZEPŁYW: Configuration -> ServiceRegistry ->
         *     SessionFactory -> Session -> Transaction
         * ============================================================
         * Poniżej kompletny, uruchamialny przykład pokazujący WSZYSTKIE
         * etapy tego przepływu na raz, z komentarzem przy każdym kroku.
         */
        System.out.println("\n=== PELNY PRZEPLYW (krok po kroku) ===");

        // Krok 1: Configuration - zbieramy ustawienia + rejestrujemy encje.
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson02architecture;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Task.class);
        System.out.println("Krok 1: Configuration gotowa (URL bazy + zarejestrowana encja Task).");

        // Krok 2: ServiceRegistry - budujemy kontener usług na podstawie properties.
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        System.out.println("Krok 2: ServiceRegistry zbudowany (dostawca polaczen, dialekt SQL...).");

        // Krok 3: SessionFactory - ciężka fabryka, budowana RAZ.
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        System.out.println("Krok 3: SessionFactory zbudowana - gotowa do wielokrotnego uzytku.");

        try (sessionFactory) {
            // Krok 4: Session - lekka jednostka pracy, otwierana na chwilę.
            Session session = sessionFactory.openSession();
            System.out.println("Krok 4: Session otwarta (jedna 'wizyta' w bazie).");

            // Krok 5: Transaction - granica atomowej operacji (Lesson08 rozwinie ten temat).
            Transaction transaction = session.beginTransaction();
            System.out.println("Krok 5: Transaction rozpoczeta.");

            Task task = new Task();
            task.setTitle("Poznac architekture Hibernate");
            session.persist(task);

            transaction.commit();
            session.close();
            System.out.println("Krok 6: Transaction zatwierdzona (commit), Session zamknieta. Zapisano Task id=" + task.getId());
        }
        System.out.println("SessionFactory zamknieta na koniec dzialania aplikacji (try-with-resources).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SessionFactory - ciężka, thread-safe, JEDNA na całą
         *   aplikację, tworzona raz przy starcie.
         * - Session - lekka, NIE thread-safe, jedna na jednostkę pracy
         *   (unit of work), otwierana/zamykana często.
         * - Configuration zbiera ustawienia i rejestruje encje,
         *   StandardServiceRegistryBuilder buduje ServiceRegistry na
         *   ich podstawie, dopiero to razem daje SessionFactory.
         * - Warstwy: ORM API (Session/Query) -> SPI (wtyczki/usługi)
         *   -> dialekt SQL (tłumaczenie na konkretną bazę, dziś zwykle
         *   auto-wykrywany).
         * - Pełny przepływ: Configuration -> ServiceRegistry ->
         *   SessionFactory -> Session -> Transaction -> commit/close.
         */

        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void printLayersTable() {
        System.out.println("\n=== WARSTWY HIBERNATE ===");
        String format = "%-18s | %-50s%n";
        System.out.printf(format, "Warstwa", "Rola");
        System.out.println("-".repeat(75));
        System.out.printf(format, "ORM API", "Session, Transaction, Query, encje z adnotacjami");
        System.out.printf(format, "SPI", "Wtyczki: connection pooling, cache L2, integracja transakcji");
        System.out.printf(format, "Dialekt SQL", "Tlumaczenie operacji na konkretna baze (H2/PostgreSQL/MySQL)");
    }

    @Entity(name = "Task")
    @Table(name = "task")
    public static class Task {

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
