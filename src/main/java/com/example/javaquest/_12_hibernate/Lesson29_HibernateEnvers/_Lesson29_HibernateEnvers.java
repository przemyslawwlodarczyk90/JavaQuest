package com.example.javaquest._12_hibernate.Lesson29_HibernateEnvers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.hibernate.service.ServiceRegistry;

public class _Lesson29_HibernateEnvers {

    private static final ThreadLocal<String> CURRENT_USER = ThreadLocal.withInitial(() -> "system");

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 29: AUDYTOWANIE ZMIAN: Hibernate Envers ===");

        /*
         * ============================================================
         * 📦 PO CO AUDYTOWANIE?
         * ============================================================
         * Zwykłe encje (Lesson01-28) trzymają TYLKO AKTUALNY stan
         * wiersza - poprzednie wartości są BEZPOWROTNIE tracone po
         * każdym UPDATE. Audytowanie odpowiada na pytania: KTO, KIEDY i
         * CO dokładnie zmienił - wymóg COMPLIANCE w wielu systemach
         * (finansowych, medycznych, RODO/GDPR) oraz nieoceniona pomoc
         * przy debugowaniu "kto zepsuł te dane i kiedy".
         */
        System.out.println("\n=== PO CO AUDYTOWANIE ===");
        System.out.println("Historia WSZYSTKICH zmian (kto/kiedy/co) - wymog compliance, pomoc w debugowaniu.");

        /*
         * ============================================================
         * 🔹 @Audited - AUTOMATYCZNA TABELA HISTORII (_AUD)
         * ============================================================
         * Hibernate Envers (moduł hibernate-envers, dodany do pom.xml
         * tego kursu) automatycznie tworzy DRUGĄ tabelę o nazwie
         * "<tabela>_AUD" dla każdej encji oznaczonej @Audited - z KOPIĄ
         * WSZYSTKICH pól ORAZ dodatkowymi kolumnami: numer rewizji
         * (REV) i typ operacji (REVTYPE: 0=ADD, 1=MOD, 2=DEL). Każdy
         * INSERT/UPDATE/DELETE na oryginalnej tabeli automatycznie
         * dopisuje NOWY wiersz do tabeli "_AUD" - Ty NIE piszesz do niej
         * ręcznie ani jednej linii kodu.
         */
        System.out.println("\n=== @Audited ===");
        System.out.println("Envers automatycznie tworzy tabele 'account_AUD' - kazdy INSERT/UPDATE/DELETE dopisuje rewizje.");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: HISTORIA ZMIAN SALDA KONTA
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {

            CURRENT_USER.set("alice");
            Long accountId = createAccount(sessionFactory, 100.0);
            System.out.println("\n=== REWIZJA 1 (autor: alice) - utworzono konto z saldem 100.0 ===");

            CURRENT_USER.set("bob");
            updateBalance(sessionFactory, accountId, 150.0);
            System.out.println("=== REWIZJA 2 (autor: bob) - zmieniono saldo na 150.0 ===");

            CURRENT_USER.set("alice");
            updateBalance(sessionFactory, accountId, 200.0);
            System.out.println("=== REWIZJA 3 (autor: alice) - zmieniono saldo na 200.0 ===");

            /*
             * ============================================================
             * 🔍 AuditReader - ODCZYT HISTORII REWIZJI
             * ============================================================
             * AuditReaderFactory.get(session) zwraca AuditReader -
             * bramę do odczytu historii: getRevisions(Klasa, id) zwraca
             * listę WSZYSTKICH numerów rewizji dla danego wiersza,
             * find(Klasa, id, numerRewizji) odtwarza stan encji Z TEJ
             * KONKRETNEJ rewizji (nie z aktualnej tabeli!).
             */
            Session session = sessionFactory.openSession();
            AuditReader auditReader = AuditReaderFactory.get(session);

            List<Number> revisions = auditReader.getRevisions(Account.class, accountId);
            System.out.println("\n=== AuditReader.getRevisions() - wszystkie numery rewizji dla tego konta ===");
            System.out.println("Rewizje: " + revisions);

            Number firstRevision = revisions.get(0);
            Account atFirstRevision = auditReader.find(Account.class, accountId, firstRevision);
            System.out.println("\n=== ODCZYT STANU 'SPRZED 2 ZMIAN' (pierwsza rewizja, numer " + firstRevision + ") ===");
            System.out.println("Saldo w tamtym momencie: " + atFirstRevision.getBalance() + " (aktualne saldo w bazie to juz 200.0!)");

            /*
             * ============================================================
             * 🔹 WŁASNE METADANE REWIZJI: KTO WYKONAŁ ZMIANĘ
             * ============================================================
             * Domyślnie Envers zna tylko numer rewizji i znacznik czasu.
             * @RevisionEntity + RevisionListener pozwala DOŁOŻYĆ własne
             * metadane (np. "changedBy" - kto wykonał zmianę) - Envers
             * AUTOMATYCZNIE tworzy dodatkową tabelę rewizji z tym polem
             * i wywołuje RevisionListener PRZED zapisem każdej rewizji.
             */
            for (Number revision : revisions) {
                CustomRevisionEntity revisionEntity = auditReader.findRevision(CustomRevisionEntity.class, revision);
                System.out.println("Rewizja " + revision + " wykonana przez: " + revisionEntity.getChangedBy());
            }

            session.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Audytowanie - historia WSZYSTKICH zmian (kto/kiedy/co),
         *   wymóg compliance, pomoc w debugowaniu.
         * - @Audited - Envers automatycznie tworzy tabelę "_AUD" z
         *   pełną historią rewizji, bez ręcznego kodu.
         * - AuditReader.getRevisions()/find() - lista rewizji i odczyt
         *   stanu encji z KONKRETNEJ, historycznej rewizji.
         * - @RevisionEntity + RevisionListener - własne metadane
         *   rewizji (np. kto wykonał zmianę), automatycznie wypełniane
         *   przy każdej rewizji.
         */

        System.out.println("\n=== KONIEC LEKCJI 29 ===");
    }

    private static Long createAccount(SessionFactory sessionFactory, double initialBalance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = new Account();
        account.setBalance(initialBalance);
        session.persist(account);
        transaction.commit();
        session.close();
        return account.getId();
    }

    private static void updateBalance(SessionFactory sessionFactory, Long accountId, double newBalance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = session.find(Account.class, accountId);
        account.setBalance(newBalance);
        transaction.commit();
        session.close();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson29envers;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(CustomRevisionEntity.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static class CurrentUserRevisionListener implements RevisionListener {
        @Override
        public void newRevision(Object revisionEntity) {
            ((CustomRevisionEntity) revisionEntity).setChangedBy(CURRENT_USER.get());
        }
    }

    @Entity(name = "CustomRevisionEntity")
    @Table(name = "custom_revision_entity")
    @RevisionEntity(CurrentUserRevisionListener.class)
    public static class CustomRevisionEntity extends DefaultRevisionEntity {
        private String changedBy;

        public String getChangedBy() {
            return changedBy;
        }

        public void setChangedBy(String changedBy) {
            this.changedBy = changedBy;
        }
    }

    @Entity(name = "Account")
    @Table(name = "account")
    @Audited
    public static class Account {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private double balance;

        public Long getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
