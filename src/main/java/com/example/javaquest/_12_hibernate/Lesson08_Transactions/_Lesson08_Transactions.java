package com.example.javaquest._12_hibernate.Lesson08_Transactions;

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

public class _Lesson08_Transactions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: TRANSAKCJE: granice, EntityTransaction, atomowość ===");

        /*
         * ============================================================
         * 📦 DLACZEGO KAŻDA MODYFIKACJA WYMAGA TRANSAKCJI
         * ============================================================
         * Transakcja gwarantuje ATOMOWOŚĆ ("wszystko albo nic") -
         * jeśli w jednej operacji biznesowej modyfikujesz WIELE
         * wierszy/tabel (np. przelew: odejmij z konta A, dodaj na
         * koncie B), a coś pójdzie nie tak W POŁOWIE, transakcja
         * pozwala WYCOFAĆ (rollback) WSZYSTKIE dotychczasowe zmiany -
         * baza wraca do stanu SPRZED operacji, bez "połowicznych"
         * modyfikacji.
         *
         * Hibernate WYMAGA aktywnej transakcji dla KAŻDEJ operacji
         * zapisu (persist/merge/remove) - próba wykonania ich bez
         * transakcji rzuca wyjątek. Sam odczyt (find) technicznie może
         * się odbyć bez jawnej transakcji, ale DOBRA PRAKTYKA to
         * ZAWSZE pracować w obrębie transakcji, nawet przy odczycie.
         */
        System.out.println("\n=== ATOMOWOSC ===");
        System.out.println("Transakcja = 'wszystko albo nic' - blad w polowie -> rollback WSZYSTKICH zmian.");
        System.out.println("Hibernate WYMAGA aktywnej transakcji dla persist/merge/remove.");

        /*
         * ============================================================
         * 🔹 JPA EntityTransaction: begin/commit/rollback
         * ============================================================
         * Standard JPA definiuje EntityTransaction - uzyskujesz go z
         * EntityManager przez entityManager.getTransaction(). Typowy
         * przepływ w aplikacji standalone (bez kontenera zarządzającego
         * transakcjami):
         *
         *   EntityTransaction tx = entityManager.getTransaction();
         *   tx.begin();
         *   try {
         *       // operacje na encjach...
         *       tx.commit();
         *   } catch (Exception e) {
         *       tx.rollback();
         *       throw e;
         *   }
         */
        System.out.println("\n=== JPA EntityTransaction ===");
        System.out.println("entityManager.getTransaction() -> begin()/commit()/rollback() - jawne zarzadzanie.");

        /*
         * ============================================================
         * 🔍 HIBERNATE Transaction (NATYWNE) - ANALOGICZNE API
         * ============================================================
         * Session.beginTransaction() zwraca org.hibernate.Transaction -
         * API analogiczne do JPA EntityTransaction (begin/commit/
         * rollback), używane w reszcie tego rozdziału konsekwentnie -
         * bo pracujemy na Session (Lesson07).
         */
        System.out.println("\n=== Hibernate Transaction (natywne) ===");
        System.out.println("session.beginTransaction() -> Transaction.commit()/rollback() - ten sam wzorzec.");

        /*
         * ============================================================
         * 🔹 BEZPIECZNY WZORZEC: try-with-resources + rollback przy wyjątku
         * ============================================================
         * Poniżej demo POKAZUJĄCE atomowość w praktyce - transakcja z
         * DWOMA operacjami, z których druga celowo zawodzi (naruszenie
         * unikalności) - obie operacje zostają WYCOFANE.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            // Scenariusz 1: udana transakcja z DWOMA operacjami.
            Session session1 = sessionFactory.openSession();
            Transaction tx1 = session1.beginTransaction();
            try {
                Account accountA = new Account();
                accountA.setOwner("Jan Kowalski");
                accountA.setBalance(100.0);
                session1.persist(accountA);

                Account accountB = new Account();
                accountB.setOwner("Anna Nowak");
                accountB.setBalance(50.0);
                session1.persist(accountB);

                tx1.commit();
                System.out.println("\n=== SCENARIUSZ 1: udana transakcja (2 operacje) ===");
                System.out.println("Zapisano oba konta - commit powodzenia.");
            } catch (RuntimeException e) {
                tx1.rollback();
                throw e;
            } finally {
                session1.close();
            }

            // Scenariusz 2: transakcja z BŁĘDEM w drugiej operacji -> rollback OBU.
            Session session2 = sessionFactory.openSession();
            Transaction tx2 = session2.beginTransaction();
            try {
                Account accountC = new Account();
                accountC.setOwner("Piotr Wisniewski");
                accountC.setBalance(200.0);
                session2.persist(accountC);

                // Celowy blad: naruszenie logiki biznesowej (saldo ujemne) -
                // w realnym kodzie byłaby to walidacja/wyjątek domenowy.
                if (accountC.getBalance() > 0) {
                    throw new IllegalStateException("Symulowany blad w polowie transakcji!");
                }

                tx2.commit();
            } catch (RuntimeException e) {
                tx2.rollback();
                System.out.println("\n=== SCENARIUSZ 2: blad w trakcie transakcji -> ROLLBACK ===");
                System.out.println("Wyjatek: " + e.getMessage() + " - konto Piotra NIE zostalo zapisane.");
            } finally {
                session2.close();
            }

            // Weryfikacja: ile kont faktycznie jest w bazie po obu scenariuszach.
            Session verifySession = sessionFactory.openSession();
            long count = verifySession.createQuery("select count(a) from Account a", Long.class)
                    .getSingleResult();
            verifySession.close();
            System.out.println("\n=== WERYFIKACJA ===");
            System.out.println("Liczba kont w bazie: " + count + " (oczekiwane: 2 - konto Piotra wycofane rollbackiem).");
        }

        /*
         * ============================================================
         * 📌 ZARZĄDZANIE DEKLARATYWNE (Spring @Transactional) - WZMIANKA
         * ============================================================
         * W aplikacjach Spring (Spring Boot to osobny, ostatni rozdział
         * tego kursu) transakcje zwykle zarządza się DEKLARATYWNIE -
         * adnotacja @Transactional na metodzie serwisu automatycznie
         * otwiera transakcję na wejściu i commituje/wycofuje ją na
         * wyjściu, BEZ ręcznego pisania begin()/commit()/rollback().
         * Kod w TYM rozdziale używa jawnego zarządzania (Configuration/
         * Session/Transaction), bo pracujemy w standalone main() - to
         * jednak DOKŁADNIE ten sam mechanizm transakcyjny "pod spodem".
         */
        System.out.println("\n=== SPRING @Transactional (wzmianka) ===");
        System.out.println("W Spring Boot transakcje zarzadzane DEKLARATYWNIE (@Transactional) - temat przyszlego rozdzialu.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Transakcja = atomowość ("wszystko albo nic") - kluczowa dla
         *   spójności danych przy wielu operacjach naraz.
         * - JPA: entityManager.getTransaction().begin()/commit()/
         *   rollback(). Hibernate natywnie: analogiczne API na
         *   session.beginTransaction().
         * - Wzorzec bezpieczny: try/catch z rollback() przy wyjątku,
         *   finally z zamknięciem Session.
         * - Spring @Transactional - deklaratywne zarządzanie transakcją
         *   (temat przyszłego rozdziału Spring Boot).
         */

        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson08transactions;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Account.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Entity(name = "Account")
    @Table(name = "account")
    public static class Account {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String owner;
        private double balance;

        public Long getId() {
            return id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
