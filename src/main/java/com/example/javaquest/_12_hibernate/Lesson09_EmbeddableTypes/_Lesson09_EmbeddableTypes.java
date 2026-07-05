package com.example.javaquest._12_hibernate.Lesson09_EmbeddableTypes;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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

public class _Lesson09_EmbeddableTypes {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: TYPY WBUDOWANE: @Embeddable, @Embedded ===");

        /*
         * ============================================================
         * 📦 VALUE OBJECTS BEZ WŁASNEJ TOŻSAMOŚCI
         * ============================================================
         * Nie każda klasa w modelu domenowym MUSI być osobną encją z
         * własną tabelą i kluczem głównym. Value object (obiekt
         * wartości) - np. Address (ulica, miasto, kod pocztowy) albo
         * Money (kwota, waluta) - to klasa, która NIE MA własnej
         * tożsamości (identity) - dwa adresy o tych samych polach są
         * "takie same" (porównanie przez equals na WARTOŚCIACH, nie
         * przez klucz główny jak w encji).
         *
         * Value object ŻYJE zawsze JAKO CZĘŚĆ jakiejś encji - jego
         * pola są po prostu DODATKOWYMI kolumnami w tabeli TEJ encji,
         * a nie osobną tabelą.
         */
        System.out.println("\n=== VALUE OBJECT (np. Address) ===");
        System.out.println("Brak wlasnej tozsamosci - zyje jako CZESC innej encji, jego pola to kolumny TEJ encji.");

        /*
         * ============================================================
         * 🔹 @Embeddable I @Embedded
         * ============================================================
         * - @Embeddable na klasie komponentu (np. Address) - mówi
         *   Hibernate "ta klasa NIE JEST osobną encją, jej pola mają
         *   być WŁĄCZONE do tabeli encji, która jej użyje".
         * - @Embedded na polu encji, które go używa (np.
         *   Customer.address) - mówi Hibernate "tu WŁĄCZ pola z
         *   powyższej klasy Embeddable".
         *
         * Efekt: klasa Customer z polem "@Embedded Address address" i
         * polami street/city/zipCode wewnątrz Address wygeneruje JEDNĄ
         * tabelę "customer" z kolumnami: id, name, street, city, zip_code
         * - BEZ osobnej tabeli "address" i BEZ klucza obcego.
         */
        System.out.println("\n=== @Embeddable + @Embedded ===");
        System.out.println("@Embeddable (na klasie komponentu) + @Embedded (na polu encji) -> WSPOLNA tabela, bez FK.");

        /*
         * ============================================================
         * 🔍 @AttributeOverrides - NADPISYWANIE NAZW KOLUMN
         * ============================================================
         * Problem: co jeśli JEDNA encja ma DWA pola tego samego typu
         * @Embeddable (np. Customer ma zarówno "homeAddress", jak i
         * "billingAddress" typu Address)? Bez dodatkowej konfiguracji
         * Hibernate próbowałby wygenerować DWIE kolumny o tej samej
         * nazwie "street" - konflikt! @AttributeOverride pozwala
         * NADPISAĆ nazwę kolumny dla KONKRETNEGO użycia komponentu.
         */
        System.out.println("\n=== @AttributeOverrides ===");
        System.out.println("Rozwiazuje konflikt nazw kolumn, gdy JEDNA encja uzywa TEGO SAMEGO @Embeddable dwa razy.");

        /*
         * ============================================================
         * 🔹 ZALETY: REUŻYWALNOŚĆ I HERMETYZACJA LOGIKI
         * ============================================================
         * Embeddable to zwykła klasa Java - może mieć WŁASNE metody
         * biznesowe (np. Address.formatOneLine() zwracające sformatowany
         * jednoliniowy adres) - logika żyje TAM, gdzie dane, zamiast
         * rozproszona po serwisach. Ta sama klasa Address może być
         * reużyta w WIELU różnych encjach (Customer, Supplier, Warehouse)
         * bez powielania pól street/city/zipCode w każdej z nich.
         */
        System.out.println("\n=== ZALETY ===");
        System.out.println("Reuzywalnosc miedzy encjami + hermetyzacja logiki (metody biznesowe NA komponencie).");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: Customer Z @Embedded Address
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Address address = new Address();
            address.setStreet("Marszalkowska 1");
            address.setCity("Warszawa");
            address.setZipCode("00-001");

            Customer customer = new Customer();
            customer.setName("Firma ABC Sp. z o.o.");
            customer.setAddress(address);
            session.persist(customer);

            transaction.commit();
            session.close();

            Session readSession = sessionFactory.openSession();
            Customer loaded = readSession.find(Customer.class, customer.getId());
            readSession.close();

            System.out.println("\n=== ODCZYT Customer + wbudowany Address ===");
            System.out.println(loaded.getName() + " -> " + loaded.getAddress().formatOneLine());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Value object (np. Address) nie ma własnej tożsamości - jego
         *   pola to po prostu dodatkowe kolumny encji, która go używa.
         * - @Embeddable (klasa komponentu) + @Embedded (pole encji) -
         *   BEZ osobnej tabeli, BEZ klucza obcego.
         * - @AttributeOverride - rozwiązuje konflikt nazw kolumn przy
         *   wielokrotnym użyciu tego samego komponentu w jednej encji.
         * - Zalety: reużywalność między encjami + hermetyzacja logiki
         *   biznesowej (metody na klasie komponentu).
         */

        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson09embeddable;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Customer.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Embeddable
    public static class Address {

        private String street;
        private String city;

        @Column(name = "zip_code")
        private String zipCode;

        public void setStreet(String street) {
            this.street = street;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String formatOneLine() {
            return street + ", " + zipCode + " " + city;
        }
    }

    @Entity(name = "Customer")
    @Table(name = "customer")
    public static class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "street", column = @Column(name = "street")),
                @AttributeOverride(name = "city", column = @Column(name = "city")),
                @AttributeOverride(name = "zipCode", column = @Column(name = "zip_code"))
        })
        private Address address;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }
}
