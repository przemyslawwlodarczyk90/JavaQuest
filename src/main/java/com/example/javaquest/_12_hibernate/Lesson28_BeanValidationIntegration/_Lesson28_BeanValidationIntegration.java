package com.example.javaquest._12_hibernate.Lesson28_BeanValidationIntegration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson28_BeanValidationIntegration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 28: INTEGRACJA Z BEAN VALIDATION: Hibernate Validator ===");

        /*
         * ============================================================
         * 📦 JAKARTA BEAN VALIDATION (JSR 380) I HIBERNATE VALIDATOR
         * ============================================================
         * Jakarta Bean Validation to STANDARD (podobnie jak JPA) -
         * zbiór adnotacji (@NotNull, @Size, @Email...) i interfejsów
         * do deklaratywnej walidacji obiektów Javy. Hibernate Validator
         * to REFERENCYJNA (najpopularniejsza) implementacja tego
         * standardu - już OBECNA w tym projekcie transytywnie przez
         * zależność spring-boot-starter-validation (NIE trzeba jej
         * dodawać ponownie).
         *
         * Ważne: Hibernate Validator to ODDZIELNY projekt od Hibernate
         * ORM (ten sam zespół/organizacja, ale INNA odpowiedzialność -
         * walidacja danych, nie mapowanie obiektowo-relacyjne) - Hibernate
         * ORM integruje się z NIM automatycznie, jeśli jest na classpath.
         */
        System.out.println("\n=== Jakarta Bean Validation + Hibernate Validator ===");
        System.out.println("Bean Validation = standard adnotacji. Hibernate Validator = referencyjna implementacja (juz w projekcie).");

        /*
         * ============================================================
         * 🔹 ADNOTACJE WALIDACYJNE NA POLACH ENCJI
         * ============================================================
         * @NotNull, @Size(min=/max=), @Email, @Min/@Max - deklaratywne
         * reguły poprawności, umieszczone WPROST na polach encji -
         * czytelna, scentralizowana definicja "co znaczy poprawny
         * Customer", bez rozpraszania walidacji po serwisach.
         */
        printValidationAnnotationsTable();

        /*
         * ============================================================
         * 🔍 AUTOMATYCZNA WALIDACJA PRZY persist/update
         * ============================================================
         * Gdy Hibernate Validator jest na classpath, Hibernate ORM
         * AUTOMATYCZNIE waliduje encję JUŻ PRZY persist()/merge() (a
         * także przy update) - PRZED wysłaniem INSERT/UPDATE do bazy.
         * Jeśli którekolwiek ograniczenie jest naruszone, rzucany jest
         * ConstraintViolationException Z
         * PEŁNĄ LISTĄ naruszonych reguł, ZANIM jakikolwiek SQL trafi do
         * bazy. To chroni bazę przed zapisaniem niepoprawnych danych
         * bez potrzeby ręcznego wywoływania walidatora w każdym miejscu
         * kodu, które zapisuje encje.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Customer validCustomer = new Customer();
            validCustomer.setName("Jan Kowalski");
            validCustomer.setEmail("jan.kowalski@example.com");
            validCustomer.setAge(30);
            session.persist(validCustomer);
            transaction.commit();
            session.close();

            System.out.println("\n=== UDANY ZAPIS (wszystkie reguly spelnione) ===");
            System.out.println("Zapisano klienta id=" + validCustomer.getId());

            Session invalidSession = sessionFactory.openSession();
            Transaction invalidTx = invalidSession.beginTransaction();
            Customer invalidCustomer = new Customer();
            invalidCustomer.setName(""); // narusza @Size(min = 2)
            invalidCustomer.setEmail("nie-jest-emailem"); // narusza @Email
            invalidCustomer.setAge(-5); // narusza @Min(0)

            System.out.println("\n=== PROBA ZAPISU NIEPOPRAWNEJ ENCJI ===");
            try {
                invalidSession.persist(invalidCustomer); // walidacja Hibernate uruchamia sie JUZ TUTAJ, nie dopiero przy commit
                invalidTx.commit();
            } catch (ConstraintViolationException e) {
                Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
                System.out.println("ConstraintViolationException - " + violations.size() + " naruszonych regul:");
                for (ConstraintViolation<?> violation : violations) {
                    System.out.println(" - " + violation.getPropertyPath() + ": " + violation.getMessage());
                }
                invalidTx.rollback();
            } finally {
                invalidSession.close();
            }
        }

        /*
         * ============================================================
         * 🔹 WALIDACJA RĘCZNA - Validator/ValidatorFactory
         * ============================================================
         * Bean Validation działa też CAŁKOWICIE NIEZALEŻNIE od
         * Hibernate/JPA - możesz zwalidować DOWOLNY obiekt Javy (np.
         * dane odebrane z formularza, ZANIM w ogóle zbudujesz z nich
         * encję) przez Validator uzyskany z ValidatorFactory. To
         * przydatne dla walidacji "wcześniej", zanim dane trafią
         * gdziekolwiek blisko bazy danych.
         */
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Customer manualCheck = new Customer();
            manualCheck.setName("A"); // za krotkie
            manualCheck.setEmail("zly-email");
            manualCheck.setAge(200); // za duzo

            Set<ConstraintViolation<Customer>> violations = validator.validate(manualCheck);
            System.out.println("\n=== WALIDACJA RECZNA (Validator, BEZ Hibernate/bazy danych) ===");
            System.out.println("Znaleziono " + violations.size() + " naruszen (bez zadnego zapytania SQL):");
            for (ConstraintViolation<Customer> violation : violations) {
                System.out.println(" - " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Jakarta Bean Validation - standard adnotacji walidacyjnych;
         *   Hibernate Validator - referencyjna implementacja (już w
         *   projekcie przez spring-boot-starter-validation).
         * - @NotNull/@Size/@Email/@Min/@Max na polach encji -
         *   deklaratywne, scentralizowane reguły poprawności.
         * - Hibernate AUTOMATYCZNIE waliduje przy flush - naruszenie
         *   rzuca ConstraintViolationException PRZED wysłaniem SQL.
         * - Validator/ValidatorFactory pozwala walidować DOWOLNY obiekt
         *   Javy, całkowicie NIEZALEŻNIE od Hibernate/bazy danych.
         */

        System.out.println("\n=== KONIEC LEKCJI 28 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson28validation;DB_CLOSE_DELAY=-1");
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

    private static void printValidationAnnotationsTable() {
        System.out.println("\n=== ADNOTACJE WALIDACYJNE ===");
        String format = "%-15s | %-40s%n";
        System.out.printf(format, "Adnotacja", "Znaczenie");
        System.out.println("-".repeat(60));
        System.out.printf(format, "@NotNull", "pole nie moze byc null");
        System.out.printf(format, "@Size", "min/max dlugosc Stringa/kolekcji");
        System.out.printf(format, "@Email", "pole musi wygladac jak adres e-mail");
        System.out.printf(format, "@Min/@Max", "minimalna/maksymalna wartosc liczbowa");
    }

    @Entity(name = "Customer")
    @Table(name = "customer")
    public static class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @Size(min = 2, max = 100)
        private String name;

        @NotNull
        @Email
        private String email;

        @Min(0)
        @Max(150)
        private int age;

        public Long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
