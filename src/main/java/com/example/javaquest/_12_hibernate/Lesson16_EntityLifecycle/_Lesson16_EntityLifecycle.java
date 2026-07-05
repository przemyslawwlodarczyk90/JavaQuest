package com.example.javaquest._12_hibernate.Lesson16_EntityLifecycle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson16_EntityLifecycle {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: CYKL ŻYCIA ENCJI: transient, persistent, detached, removed ===");

        /*
         * ============================================================
         * 📦 CZTERY STANY ENCJI
         * ============================================================
         * - TRANSIENT (przejściowy) - zwykły obiekt Java stworzony przez
         *   "new" - Hibernate o nim NIC nie wie, nie ma jeszcze wiersza
         *   w bazie.
         * - PERSISTENT / MANAGED (zarządzany) - obiekt ŚLEDZONY przez
         *   AKTYWNĄ Session - Hibernate automatycznie wykrywa jego
         *   zmiany (dirty checking, Lesson17) i synchronizuje je z bazą.
         * - DETACHED (odłączony) - obiekt BYŁ zarządzany, ale jego
         *   Session została zamknięta (albo obiekt jawnie odłączono
         *   przez evict/clear) - dane wciąż są w pamięci Javy, ale
         *   Hibernate JUŻ go nie śledzi.
         * - REMOVED (usunięty) - obiekt oznaczony do usunięcia przez
         *   session.remove() - w obrębie aktywnej transakcji, do
         *   momentu flush/commit.
         */
        printLifecycleStatesTable();

        /*
         * ============================================================
         * 🔹 PRZEJŚCIA MIĘDZY STANAMI
         * ============================================================
         *   new Book()                      -> TRANSIENT
         *   session.persist(book)           -> TRANSIENT -> PERSISTENT
         *   session.find(Book.class, id)    -> (nowy obiekt) PERSISTENT
         *   session.close() / evict() / clear() -> PERSISTENT -> DETACHED
         *   session.merge(detachedBook)     -> DETACHED -> PERSISTENT (NOWY obiekt!)
         *   session.remove(managedBook)     -> PERSISTENT -> REMOVED
         */
        System.out.println("\n=== PRZEJSCIA MIEDZY STANAMI ===");
        System.out.println("new -> TRANSIENT -> (persist) -> PERSISTENT -> (close/evict) -> DETACHED");
        System.out.println("DETACHED -> (merge) -> PERSISTENT (nowy obiekt) | PERSISTENT -> (remove) -> REMOVED");

        /*
         * ============================================================
         * 🔍 CALLBACKS CYKLU ŻYCIA
         * ============================================================
         * JPA pozwala "podpiąć się" pod przejścia stanów przez adnotacje
         * na metodach encji - wywoływane AUTOMATYCZNIE przez Hibernate:
         * @PrePersist/@PostPersist, @PreUpdate/@PostUpdate,
         * @PreRemove/@PostRemove, @PostLoad. Częste zastosowanie:
         * automatyczne ustawianie znaczników czasu (createdAt/updatedAt)
         * - poniższy przykład to pokazuje na żywo.
         */
        System.out.println("\n=== CALLBACKS CYKLU ZYCIA (@PrePersist itd.) ===");
        System.out.println("Wywolywane AUTOMATYCZNIE przez Hibernate przy odpowiednim przejsciu stanu.");

        /*
         * ============================================================
         * 🔹 PEŁNA DEMONSTRACJA WSZYSTKICH 4 STANÓW
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            // 1. TRANSIENT
            Book book = new Book();
            book.setTitle("Wesele");
            System.out.println("\n1. TRANSIENT: obiekt stworzony przez 'new', Hibernate o nim nic nie wie. id=" + book.getId());

            // 2. PERSISTENT (managed)
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(book); // -> @PrePersist ustawia createdAt, TRANSIENT -> PERSISTENT
            transaction.commit();
            System.out.println("2. PERSISTENT: po persist()+commit() - id=" + book.getId() + ", createdAt=" + book.getCreatedAt());

            // 3. DETACHED
            session.close(); // Session zamknieta -> obiekt staje sie DETACHED
            book.setTitle("Wesele (zmiana PO zamknieciu Session)"); // ta zmiana NIE trafi do bazy bez merge!
            System.out.println("3. DETACHED: Session zamknieta, zmiana tytulu TYLKO w pamieci Javy (bez merge nie trafi do bazy).");

            // Powrot do PERSISTENT przez merge (Lesson06).
            Session mergeSession = sessionFactory.openSession();
            Transaction mergeTx = mergeSession.beginTransaction();
            Book managedAgain = mergeSession.merge(book); // -> @PreUpdate, DETACHED -> PERSISTENT (NOWY obiekt)
            mergeTx.commit();
            mergeSession.close();
            System.out.println("   Po merge(): zmiana ZAPISANA, updatedAt=" + managedAgain.getUpdatedAt());

            // 4. REMOVED
            Session removeSession = sessionFactory.openSession();
            Transaction removeTx = removeSession.beginTransaction();
            Book toRemove = removeSession.find(Book.class, book.getId());
            removeSession.remove(toRemove); // -> @PreRemove, PERSISTENT -> REMOVED
            removeTx.commit(); // faktyczny DELETE dopiero tu (flush)
            removeSession.close();
            System.out.println("4. REMOVED: po remove()+commit() - wiersz skasowany z bazy.");
        }

        /*
         * ============================================================
         * 📌 TYPOWY BŁĄD: MODYFIKACJA DETACHED ENTITY BEZ merge()
         * ============================================================
         * Krok 3 powyżej pokazał to WPROST: zmiana pola na obiekcie
         * detached (book.setTitle(...) PO session.close()) jest
         * ZUPEŁNIE niewidoczna dla bazy danych, dopóki nie wywołasz
         * session.merge(book) w NOWEJ, aktywnej Session. To jedna z
         * najczęstszych przyczyn "moja zmiana się nie zapisała" u
         * początkujących.
         */
        System.out.println("\n=== TYPOWY BLAD ===");
        System.out.println("Modyfikacja pola na obiekcie DETACHED bez pozniejszego merge() - zmiana NIGDY nie trafia do bazy!");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - 4 stany: transient (nowy, nieznany Hibernate), persistent/
         *   managed (śledzony przez aktywną Session), detached
         *   (odłączony po zamknięciu Session), removed (oznaczony do
         *   usunięcia w obrębie transakcji).
         * - Przejścia: persist (transient->persistent), close/evict
         *   (persistent->detached), merge (detached->persistent, NOWY
         *   obiekt!), remove (persistent->removed).
         * - Callbacks (@PrePersist/@PostUpdate/...) - automatyczne
         *   "haki" na przejścia stanów, częste do znaczników czasu.
         * - Modyfikacja detached bez merge() = zmiana zgubiona.
         */

        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson16lifecycle;DB_CLOSE_DELAY=-1");
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

    private static void printLifecycleStatesTable() {
        System.out.println("\n=== 4 STANY ENCJI ===");
        String format = "%-15s | %-50s%n";
        System.out.printf(format, "Stan", "Opis");
        System.out.println("-".repeat(70));
        System.out.printf(format, "TRANSIENT", "nowy obiekt (new), Hibernate go nie zna");
        System.out.printf(format, "PERSISTENT", "sledzony przez AKTYWNA Session (dirty checking)");
        System.out.printf(format, "DETACHED", "byl zarzadzany, ale Session zamknieta/evict");
        System.out.printf(format, "REMOVED", "oznaczony do usuniecia, do momentu flush/commit");
    }

    @Entity(name = "Book")
    @Table(name = "book")
    public static class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Long getId() {
            return id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        @PrePersist
        public void onPrePersist() {
            this.createdAt = LocalDateTime.now();
        }

        @PostPersist
        public void onPostPersist() {
            System.out.println("   [callback @PostPersist] Ksiazka zapisana z id=" + id);
        }

        @PreUpdate
        public void onPreUpdate() {
            this.updatedAt = LocalDateTime.now();
        }

        @PostUpdate
        public void onPostUpdate() {
            System.out.println("   [callback @PostUpdate] Ksiazka id=" + id + " zaktualizowana.");
        }

        @PreRemove
        public void onPreRemove() {
            System.out.println("   [callback @PreRemove] Ksiazka id=" + id + " zaraz zostanie usunieta.");
        }

        @PostRemove
        public void onPostRemove() {
            System.out.println("   [callback @PostRemove] Ksiazka id=" + id + " usunieta.");
        }

        @PostLoad
        public void onPostLoad() {
            System.out.println("   [callback @PostLoad] Ksiazka id=" + id + " zaladowana z bazy.");
        }
    }
}
