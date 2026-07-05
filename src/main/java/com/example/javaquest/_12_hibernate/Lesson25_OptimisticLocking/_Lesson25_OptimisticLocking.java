package com.example.javaquest._12_hibernate.Lesson25_OptimisticLocking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson25_OptimisticLocking {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 25: BLOKOWANIE OPTYMISTYCZNE: @Version ===");

        /*
         * ============================================================
         * 📦 ZAŁOŻENIE OPTYMISTYCZNE
         * ============================================================
         * Blokowanie optymistyczne zakłada, że konflikty (dwóch
         * użytkowników modyfikujących TEN SAM wiersz jednocześnie) są
         * RZADKIE - więc NIE blokujemy wiersza z góry (co kosztowałoby
         * wydajność przy KAŻDYM odczycie), tylko SPRAWDZAMY dopiero W
         * MOMENCIE ZAPISU, czy ktoś inny nie zmienił danych w
         * międzyczasie. Jeśli tak - zapis zostaje ODRZUCONY.
         */
        System.out.println("\n=== ZALOZENIE OPTYMISTYCZNE ===");
        System.out.println("Konflikty sa RZADKIE - nie blokujemy z gory, sprawdzamy dopiero PRZY ZAPISIE.");

        /*
         * ============================================================
         * 🔹 @Version - AUTOMATYCZNY LICZNIK WERSJI
         * ============================================================
         * Pole oznaczone @Version (typowo Integer/Long, rzadziej
         * Timestamp) jest AUTOMATYCZNIE zarządzane przez Hibernate:
         * - startuje od 0 (albo 1) przy pierwszym zapisie,
         * - przy KAŻDYM UPDATE Hibernate dołącza do zapytania SQL
         *   warunek "WHERE id = ? AND version = ?" ORAZ inkrementuje
         *   wartość w SET version = version + 1.
         * - jeśli baza zwróci 0 zmodyfikowanych wierszy (bo version w
         *   bazie JUŻ się zmienił - ktoś inny zdążył zapisać wcześniej),
         *   Hibernate wykrywa to i rzuca wyjątek.
         */
        System.out.println("\n=== @Version ===");
        System.out.println("Hibernate dolacza 'WHERE id=? AND version=?' do UPDATE + inkrementuje version automatycznie.");

        /*
         * ============================================================
         * 🔍 OptimisticLockException
         * ============================================================
         * Rzucany, gdy UPDATE/DELETE nie trafił w ŻADEN wiersz, bo
         * warunek "version = ?" się nie zgadzał (ktoś inny zmienił
         * wiersz między Twoim odczytem a zapisem). To sygnał "Twoje
         * dane są NIEAKTUALNE - odśwież je i spróbuj ponownie", a NIE
         * błąd techniczny/bug.
         */
        System.out.println("\n=== OptimisticLockException ===");
        System.out.println("Rzucany, gdy 'version' w bazie NIE zgadza sie z wersja, ktora mial Twoj obiekt przy odczycie.");

        /*
         * ============================================================
         * 🔹 DEMO: DWIE 'RÓWNOLEGŁE' SESJE MODYFIKUJĄCE TEN SAM WIERSZ
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Long accountId = seedData(sessionFactory);

            // Obie "rownolegle" sesje odczytuja TEN SAM wiersz, z TA SAMA wersja (0).
            Session sessionA = sessionFactory.openSession();
            Account accountViewedByA = sessionA.find(Account.class, accountId);
            System.out.println("\n=== Sesja A odczytala konto, version=" + accountViewedByA.getVersion() + " ===");

            Session sessionB = sessionFactory.openSession();
            Account accountViewedByB = sessionB.find(Account.class, accountId);
            System.out.println("=== Sesja B odczytala TO SAMO konto, version=" + accountViewedByB.getVersion() + " ===");

            // Sesja A modyfikuje i ZATWIERDZA jako PIERWSZA - sukces, version rosnie do 1.
            Transaction txA = sessionA.beginTransaction();
            accountViewedByA.setBalance(accountViewedByA.getBalance() + 100.0);
            txA.commit();
            sessionA.close();
            System.out.println("\n=== Sesja A: zapisala zmiane (saldo +100), nowa version w bazie = 1 ===");

            // Sesja B probuje zapisac na PODSTAWIE STAREJ wersji (0) - KONFLIKT.
            System.out.println("\n=== Sesja B: probuje zapisac na podstawie STAREJ wersji (0) ===");
            try {
                Transaction txB = sessionB.beginTransaction();
                accountViewedByB.setBalance(accountViewedByB.getBalance() + 50.0);
                txB.commit();
                System.out.println("(nieoczekiwane - zapis powinien sie nie udac)");
            } catch (OptimisticLockException e) {
                System.out.println("OptimisticLockException: " + e.getClass().getSimpleName() + " - ktos inny juz zmienil ten wiersz!");
            } finally {
                sessionB.close();
            }

            /*
             * ============================================================
             * 📌 STRATEGIA OBSŁUGI KONFLIKTU: RETRY Z ODŚWIEŻENIEM
             * ============================================================
             * Typowa strategia naprawy: PO złapaniu OptimisticLockException,
             * ODŚWIEŻ dane (nowy find() - dostaniesz aktualną wersję z
             * bazy) i SPRÓBUJ zastosować swoją zmianę PONOWNIE na
             * świeżych danych - zamiast po prostu pokazać użytkownikowi
             * błąd bez żadnej automatycznej naprawy.
             */
            Session retrySession = sessionFactory.openSession();
            Transaction retryTx = retrySession.beginTransaction();
            Account freshAccount = retrySession.find(Account.class, accountId); // ŚWIEŻA wersja (1)
            freshAccount.setBalance(freshAccount.getBalance() + 50.0); // ta sama zmiana biznesowa, ale na aktualnych danych
            retryTx.commit();
            retrySession.close();
            System.out.println("\n=== RETRY: odswiezono dane (version=1) i ponowiono zapis - tym razem sukces ===");
            System.out.println("Finalne saldo: " + freshAccount.getBalance());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Blokowanie optymistyczne - zakłada rzadkie konflikty, brak
         *   blokady z góry, sprawdzenie dopiero przy zapisie.
         * - @Version - automatyczny licznik, Hibernate dołącza warunek
         *   "version = ?" do UPDATE i inkrementuje go po zapisie.
         * - OptimisticLockException - sygnał, że dane były nieaktualne
         *   w momencie zapisu (ktoś inny zdążył wcześniej).
         * - Strategia naprawy: retry z odświeżeniem danych (nowy find()
         *   + ponowna próba zapisu na aktualnej wersji).
         */

        System.out.println("\n=== KONIEC LEKCJI 25 ===");
    }

    private static Long seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = new Account();
        account.setOwner("Jan Kowalski");
        account.setBalance(1000.0);
        session.persist(account);
        transaction.commit();
        session.close();
        return account.getId();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson25optimistic;DB_CLOSE_DELAY=-1");
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

        @Version
        private int version;

        public Long getId() {
            return id;
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

        public int getVersion() {
            return version;
        }
    }
}
