package com.example.javaquest._12_hibernate.Lesson06_CrudOperations;

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

public class _Lesson06_CrudOperations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: OPERACJE CRUD: persist/find/merge/remove vs save/get/update/delete ===");

        /*
         * ============================================================
         * 📦 JPA EntityManager: persist/find/merge/remove
         * ============================================================
         * Standard JPA definiuje 4 podstawowe operacje CRUD (Create,
         * Read, Update, Delete) na interfejsie EntityManager - a
         * ponieważ (Lesson02) Hibernate Session ROZSZERZA EntityManager,
         * dokładnie te same metody działają też na Session:
         *
         * - persist(obiekt)  -> CREATE - zapisuje NOWY obiekt (transient,
         *   Lesson16) jako wiersz w bazie.
         * - find(Klasa, id)  -> READ - odczytuje wiersz po kluczu
         *   głównym, zwraca zarządzany obiekt (albo null, jeśli brak).
         * - merge(obiekt)    -> UPDATE - dla obiektu ODŁĄCZONEGO
         *   (detached, Lesson16) - zwraca NOWY, zarządzany obiekt z
         *   scalonym stanem (Twój przekazany obiekt POZOSTAJE detached!).
         * - remove(obiekt)   -> DELETE - obiekt MUSI być zarządzany
         *   (managed) - żeby usunąć detached, najpierw trzeba go
         *   scalić przez merge().
         */
        System.out.println("\n=== JPA: persist/find/merge/remove ===");
        System.out.println("persist -> CREATE, find -> READ, merge -> UPDATE (detached), remove -> DELETE (managed).");

        /*
         * ============================================================
         * 🔹 HIBERNATE NATYWNY: save/get/update/delete
         * ============================================================
         * Przed standaryzacją JPA, Hibernate miał WŁASNE nazwy tych
         * samych operacji - wciąż dostępne na Session (Lesson07 rozwinie
         * temat Session vs EntityManager):
         * - save(obiekt)   -> podobne do persist, ale ZWRACA wygenerowany
         *   klucz od razu (Serializable) zamiast void.
         * - get(Klasa, id) -> podobne do find, zwraca null gdy brak
         *   (load() dla porównania rzuca wyjątek i zwraca leniwy proxy).
         * - update(obiekt) -> podobne do merge, ale NIE zwraca nowego
         *   obiektu - dołącza PRZEKAZANY obiekt do Session (rzuci
         *   wyjątek, jeśli w tej Session jest JUŻ INNY zarządzany
         *   obiekt o tym samym Id - patrz niżej).
         * - delete(obiekt) -> odpowiednik remove.
         *
         * 📌 W NOWYM kodzie preferuj metody JPA (persist/find/merge/
         * remove) - są standardem i działają też z inną implementacją
         * JPA. Metody natywne wciąż spotkasz w starszym kodzie.
         */
        printCrudMappingTable();

        /*
         * ============================================================
         * 🔍 merge() vs update() - KLUCZOWA RÓŻNICA
         * ============================================================
         * To jedna z częstszych pułapek początkujących:
         *
         * - update(detachedObj) - mówi Hibernate "zaufaj mi, TEN
         *   konkretny obiekt ma być teraz zarządzany" - JEŚLI Session
         *   ma już INNY zarządzany obiekt o tym samym Id (np. z
         *   wcześniejszego find() w tej samej Session), rzuci wyjątek
         *   (NonUniqueObjectException) - bo powstałyby DWA zarządzane
         *   obiekty reprezentujące ten sam wiersz.
         * - merge(detachedObj) - BEZPIECZNIEJSZE: Hibernate SAM sprawdza,
         *   czy w Session jest już zarządzany obiekt o tym Id - jeśli
         *   tak, KOPIUJE stan Twojego obiektu NA NIEGO i zwraca WŁAŚNIE
         *   TEN zarządzany obiekt (Twój oryginalny argument POZOSTAJE
         *   detached, niezmieniony przez Hibernate).
         *
         * ⚠️ Efekt praktyczny: po merge() zawsze używaj ZWRÓCONEGO
         * obiektu, NIE oryginalnego argumentu:
         *   Book managed = session.merge(detachedBook); // OK
         *   detachedBook.setTitle(...); // TO nie trafi juz do bazy!
         */
        System.out.println("\n=== merge() vs update() ===");
        System.out.println("update(obj) - rzuca wyjatek, jesli Session ma juz INNY zarzadzany obiekt o tym Id.");
        System.out.println("merge(obj)  - bezpieczne: zwraca NOWY zarzadzany obiekt, oryginalny argument zostaje detached.");
        System.out.println("PULAPKA: po merge() uzywaj ZWROCONEGO obiektu, nie oryginalnego argumentu!");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD CRUD NA JEDNEJ ENCJI (Book)
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            // CREATE
            Session createSession = sessionFactory.openSession();
            Transaction createTx = createSession.beginTransaction();
            Book book = new Book();
            book.setTitle("Zbrodnia i kara");
            book.setPrice(39.90);
            createSession.persist(book);
            createTx.commit();
            createSession.close();
            System.out.println("\n=== CREATE (persist) ===");
            System.out.println("Zapisano ksiazke id=" + book.getId());

            // READ
            Session readSession = sessionFactory.openSession();
            Book found = readSession.find(Book.class, book.getId());
            readSession.close();
            System.out.println("\n=== READ (find) ===");
            System.out.println("Odczytano: " + found.getTitle() + ", cena=" + found.getPrice());

            // UPDATE (przez detached entity + merge)
            found.setPrice(34.90); // "found" jest juz detached (Session zamknieta powyzej)
            Session updateSession = sessionFactory.openSession();
            Transaction updateTx = updateSession.beginTransaction();
            Book managedAfterMerge = updateSession.merge(found);
            updateTx.commit();
            updateSession.close();
            System.out.println("\n=== UPDATE (merge na detached entity) ===");
            System.out.println("Nowa cena po merge: " + managedAfterMerge.getPrice());

            // DELETE
            Session deleteSession = sessionFactory.openSession();
            Transaction deleteTx = deleteSession.beginTransaction();
            Book toDelete = deleteSession.find(Book.class, book.getId());
            deleteSession.remove(toDelete);
            deleteTx.commit();
            deleteSession.close();

            Session verifySession = sessionFactory.openSession();
            Book afterDelete = verifySession.find(Book.class, book.getId());
            verifySession.close();
            System.out.println("\n=== DELETE (remove) ===");
            System.out.println("Po usunieciu, find() zwraca: " + afterDelete + " (null = skutecznie usunieto).");
        }

        /*
         * ============================================================
         * 📌 TYPOWE PUŁAPKI POCZĄTKUJĄCYCH
         * ============================================================
         * - Zapomniany commit() - persist()/remove() same w sobie NIE
         *   wysyłają SQL do bazy natychmiast (Lesson17 wyjaśni flush) -
         *   bez commit() transakcji zmiany mogą w ogóle nie trafić do
         *   bazy (albo zostać wycofane przy rollbacku).
         * - Modyfikacja obiektu PO zamknięciu Session (detached) bez
         *   późniejszego merge() - zmiana zostaje TYLKO w pamięci Javy,
         *   NIGDY nie trafia do bazy (Lesson16 rozwinie stany encji).
         * - Użycie update() zamiast merge() na obiekcie, który mógł już
         *   być załadowany gdzie indziej w tej samej Session -
         *   NonUniqueObjectException.
         */
        System.out.println("\n=== TYPOWE PULAPKI ===");
        System.out.println("1. Brak commit() - zmiany moga nie trafic do bazy.");
        System.out.println("2. Modyfikacja detached entity BEZ merge() - zmiana zostaje tylko w pamieci Javy.");
        System.out.println("3. update() zamiast merge() na potencjalnie juz zaladowanym obiekcie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JPA: persist (Create), find (Read), merge (Update na
         *   detached), remove (Delete, wymaga managed).
         * - Hibernate natywne: save/get/update/delete - starsze,
         *   nadal dostępne, ale JPA jest zalecanym standardem.
         * - merge() zwraca NOWY zarządzany obiekt - zawsze używaj
         *   zwróconej referencji, nie oryginalnego argumentu.
         * - Bez commit() transakcji zmiany mogą nie trafić do bazy.
         */

        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson06crud;DB_CLOSE_DELAY=-1");
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

    private static void printCrudMappingTable() {
        System.out.println("\n=== JPA vs HIBERNATE NATYWNE - ODPOWIEDNIKI ===");
        String format = "%-12s | %-22s | %-30s%n";
        System.out.printf(format, "Operacja", "JPA (zalecane)", "Hibernate natywne (starsze)");
        System.out.println("-".repeat(70));
        System.out.printf(format, "Create", "persist(obj)", "save(obj) -> zwraca Serializable");
        System.out.printf(format, "Read", "find(Klasa, id)", "get(Klasa, id) / load(Klasa, id)");
        System.out.printf(format, "Update", "merge(obj) -> nowy obiekt", "update(obj) -> moze rzucic wyjatek");
        System.out.printf(format, "Delete", "remove(obj)", "delete(obj)");
    }

    @Entity(name = "Book")
    @Table(name = "book")
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
