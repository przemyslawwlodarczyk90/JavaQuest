package com.example.javaquest._12_hibernate.Lesson27_InheritanceMapping;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson27_InheritanceMapping {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 27: MAPOWANIE DZIEDZICZENIA: SINGLE_TABLE, JOINED, TABLE_PER_CLASS ===");

        /*
         * ============================================================
         * 📦 InheritanceType.SINGLE_TABLE
         * ============================================================
         * JEDNA tabela SQL dla CAŁEJ hierarchii klas - zawiera kolumny
         * WSZYSTKICH podklas naraz. Dodatkowa kolumna dyskryminatora
         * (@DiscriminatorColumn, domyślnie "DTYPE") mówi, KTÓRA konkretna
         * podklasa reprezentuje dany wiersz. Zalety: NAJSZYBSZE
         * zapytania (zero JOIN-ów, nawet przy zapytaniach po całej
         * hierarchii). Wada: kolumny specyficzne dla JEDNEJ podklasy są
         * NULL dla wierszy innych podklas - może prowadzić do "chudej"
         * tabeli z wieloma pustymi kolumnami przy bogatej hierarchii.
         */
        SessionFactory singleTableFactory = buildSingleTableSessionFactory();
        try (singleTableFactory) {
            Session session = singleTableFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CardPayment card = new CardPayment();
            card.setAmount(100.0);
            card.setCardNumber("4111-XXXX-XXXX-1111");
            session.persist(card);

            BankTransferPayment transfer = new BankTransferPayment();
            transfer.setAmount(250.0);
            transfer.setIban("PL61109010140000071219812874");
            session.persist(transfer);

            transaction.commit();

            List<Payment> allPayments = session.createQuery("from Payment", Payment.class).getResultList();
            System.out.println("\n=== SINGLE_TABLE: zapytanie 'from Payment' (JEDNA tabela, bez JOIN-ow) ===");
            allPayments.forEach(p -> System.out.println(" - " + p.getClass().getSimpleName() + ", kwota=" + p.getAmount()));
            session.close();
        }

        /*
         * ============================================================
         * 🔹 InheritanceType.JOINED
         * ============================================================
         * OSOBNA tabela dla KAŻDEJ klasy w hierarchii - tabela nadklasy
         * ma kolumny WSPÓLNE, a tabele podklas mają TYLKO swoje WŁASNE
         * kolumny + klucz obcy (jednocześnie klucz główny) wskazujący
         * wiersz w tabeli nadklasy. Zalety: pełna NORMALIZACJA (brak
         * pustych kolumn - każda kolumna istnieje tylko tam, gdzie
         * faktycznie ma sens). Wada: odczyt PEŁNEGO obiektu podklasy
         * wymaga JOIN-a między tabelą nadklasy a tabelą podklasy - przy
         * głębokich hierarchiach wielopoziomowych może to być kosztowne.
         */
        SessionFactory joinedFactory = buildJoinedSessionFactory();
        try (joinedFactory) {
            Session session = joinedFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CardPaymentJoined card = new CardPaymentJoined();
            card.setAmount(100.0);
            card.setCardNumber("4111-XXXX-XXXX-1111");
            session.persist(card);

            transaction.commit();

            PaymentJoined loaded = session.find(PaymentJoined.class, card.getId());
            System.out.println("\n=== JOINED: odczyt wymaga JOIN miedzy tabela nadklasy i podklasy ===");
            System.out.println("Odczytano: " + loaded.getClass().getSimpleName() + ", kwota=" + loaded.getAmount());
            session.close();
        }

        /*
         * ============================================================
         * 🔍 InheritanceType.TABLE_PER_CLASS
         * ============================================================
         * OSOBNA, PEŁNA tabela dla KAŻDEJ KONKRETNEJ (nieabstrakcyjnej)
         * klasy - każda zawiera WSZYSTKIE kolumny (wspólne z nadklasy +
         * własne), BEZ żadnych JOIN-ów. Zalety: najszybszy odczyt
         * POJEDYNCZEJ konkretnej podklasy (jedna, samodzielna tabela).
         * Wada: zapytanie po CAŁEJ hierarchii ("from Payment") wymaga od
         * Hibernate wykonania UNION między tabelami wszystkich podklas -
         * kosztowne przy wielu podklasach. Klucze główne WYMAGAJĄ
         * strategii działającej SPÓJNIE dla całej hierarchii (np.
         * GenerationType.TABLE) - IDENTITY osobno w każdej tabeli
         * mogłoby dać KOLIDUJĄCE Id między różnymi podklasami.
         */
        SessionFactory tablePerClassFactory = buildTablePerClassSessionFactory();
        try (tablePerClassFactory) {
            Session session = tablePerClassFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CardPaymentTpc card = new CardPaymentTpc();
            card.setAmount(100.0);
            card.setCardNumber("4111-XXXX-XXXX-1111");
            session.persist(card);

            BankTransferPaymentTpc transfer = new BankTransferPaymentTpc();
            transfer.setAmount(250.0);
            transfer.setIban("PL61109010140000071219812874");
            session.persist(transfer);

            transaction.commit();

            List<PaymentTpc> allPayments = session.createQuery("from PaymentTpc", PaymentTpc.class).getResultList();
            System.out.println("\n=== TABLE_PER_CLASS: zapytanie po hierarchii wymaga UNION miedzy tabelami ===");
            allPayments.forEach(p -> System.out.println(" - " + p.getClass().getSimpleName() + ", kwota=" + p.getAmount() + ", id=" + p.getId()));
            session.close();
        }

        printStrategyComparisonTable();

        /*
         * ============================================================
         * 🔹 @MappedSuperclass - WSPÓŁDZIELENIE PÓL BEZ HIERARCHII ENCJI
         * ============================================================
         * @MappedSuperclass to zupełnie INNY mechanizm niż powyższe
         * trzy strategie - klasa oznaczona @MappedSuperclass w OGÓLE
         * NIE JEST encją (nie ma własnej tabeli, nie można jej
         * zapytać/zapisać samodzielnie) - służy WYŁĄCZNIE do
         * WSPÓŁDZIELENIA pól (np. id, createdAt) między NIEZALEŻNYMI
         * encjami. Każda encja dziedzicząca z @MappedSuperclass dostaje
         * te pola jako WŁASNE kolumny w SWOJEJ WŁASNEJ, samodzielnej
         * tabeli - to NIE jest "trzecia" strategia dziedziczenia, tylko
         * sposób na uniknięcie powielania kodu pól.
         */
        System.out.println("\n=== @MappedSuperclass ===");
        System.out.println("NIE jest encja - wspoldzieli pola (id, createdAt) miedzy NIEZALEZNYMI encjami, kazda z WLASNA tabela.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SINGLE_TABLE - jedna tabela, dyskryminator, najszybsze
         *   zapytania, ale potencjalnie dużo kolumn NULL.
         * - JOINED - osobna tabela na klasę, pełna normalizacja, ale
         *   JOIN-y przy odczycie.
         * - TABLE_PER_CLASS - osobna, pełna tabela na konkretną klasę,
         *   brak JOIN-ów, ale UNION przy zapytaniu po całej hierarchii.
         * - @MappedSuperclass - inny mechanizm: współdzielenie pól, BEZ
         *   wspólnej tabeli/hierarchii encji.
         */

        System.out.println("\n=== KONIEC LEKCJI 27 ===");
    }

    private static void printStrategyComparisonTable() {
        System.out.println("\n=== POROWNANIE 3 STRATEGII ===");
        String format = "%-16s | %-20s | %-25s%n";
        System.out.printf(format, "Strategia", "Tabele", "Kompromis");
        System.out.println("-".repeat(68));
        System.out.printf(format, "SINGLE_TABLE", "1 (cala hierarchia)", "szybko, ale duzo NULL");
        System.out.printf(format, "JOINED", "1 na kazda klase", "normalizacja, ale JOIN-y");
        System.out.printf(format, "TABLE_PER_CLASS", "1 na konkretna klase", "brak JOIN-ow, ale UNION przy hierarchii");
    }

    private static SessionFactory buildSingleTableSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson27singletable;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(CardPayment.class);
        configuration.addAnnotatedClass(BankTransferPayment.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static SessionFactory buildJoinedSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson27joined;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(PaymentJoined.class);
        configuration.addAnnotatedClass(CardPaymentJoined.class);
        configuration.addAnnotatedClass(BankTransferPaymentJoined.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static SessionFactory buildTablePerClassSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson27tpc;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(PaymentTpc.class);
        configuration.addAnnotatedClass(CardPaymentTpc.class);
        configuration.addAnnotatedClass(BankTransferPaymentTpc.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    // ---------- SINGLE_TABLE ----------

    @Entity(name = "Payment")
    @Table(name = "payment_single")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "payment_type")
    public abstract static class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private double amount;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    @Entity(name = "CardPayment")
    @DiscriminatorValue("CARD")
    public static class CardPayment extends Payment {
        private String cardNumber;

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    @Entity(name = "BankTransferPayment")
    @DiscriminatorValue("BANK_TRANSFER")
    public static class BankTransferPayment extends Payment {
        private String iban;

        public void setIban(String iban) {
            this.iban = iban;
        }
    }

    // ---------- JOINED ----------

    @Entity(name = "PaymentJoined")
    @Table(name = "payment_joined")
    @Inheritance(strategy = InheritanceType.JOINED)
    public abstract static class PaymentJoined {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private double amount;

        public Long getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    @Entity(name = "CardPaymentJoined")
    @Table(name = "card_payment_joined")
    public static class CardPaymentJoined extends PaymentJoined {
        private String cardNumber;

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    @Entity(name = "BankTransferPaymentJoined")
    @Table(name = "bank_transfer_payment_joined")
    public static class BankTransferPaymentJoined extends PaymentJoined {
        private String iban;

        public void setIban(String iban) {
            this.iban = iban;
        }
    }

    // ---------- TABLE_PER_CLASS ----------

    @Entity(name = "PaymentTpc")
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    public abstract static class PaymentTpc {
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        private Long id;

        private double amount;

        public Long getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    @Entity(name = "CardPaymentTpc")
    @Table(name = "card_payment_tpc")
    public static class CardPaymentTpc extends PaymentTpc {
        private String cardNumber;

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    @Entity(name = "BankTransferPaymentTpc")
    @Table(name = "bank_transfer_payment_tpc")
    public static class BankTransferPaymentTpc extends PaymentTpc {
        private String iban;

        public void setIban(String iban) {
            this.iban = iban;
        }
    }

    // ---------- @MappedSuperclass (mechanizm niezalezny od powyzszych) ----------

    @MappedSuperclass
    public abstract static class BaseAuditableEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDateTime createdAt;

        public Long getId() {
            return id;
        }
    }
}
