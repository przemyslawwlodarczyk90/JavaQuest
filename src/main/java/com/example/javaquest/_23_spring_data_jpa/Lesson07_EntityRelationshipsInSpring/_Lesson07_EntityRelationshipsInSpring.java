package com.example.javaquest._23_spring_data_jpa.Lesson07_EntityRelationshipsInSpring;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class _Lesson07_EntityRelationshipsInSpring {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: Relacje miedzy encjami W Spring Data ===");

        /*
         * ============================================================
         * 📦 RELACJE - POGLEBIENIE _12_hibernate/Lesson11-13, TERAZ PRZEZ REPOZYTORIA
         * ============================================================
         * Same adnotacje relacji (`@OneToMany`/`@ManyToOne`/`@ManyToMany`)
         * co w `_12_hibernate` - Spring Data JPA NIC tu NIE ZMIENIA NA
         * POZIOMIE MAPOWANIA. RÓZNICA jest W TYM, JAK Z relacji
         * KORZYSTASZ:
         * - `repository.save(customer)` Z `cascade = CascadeType.ALL`
         *   AUTOMATYCZNIE zapisuje TEZ POWIAZANE zamowienia - JEDNO
         *   wywolanie, KASKADOWY zapis.
         * - Query methods (Lesson04) MOGA NAWIGOWAC PRZEZ relacje
         *   (`findByCustomer_Name(...)` - podkreslnik = "wejdz W
         *   pole `customer`, POTEM W jego pole `name`").
         */
        System.out.println("Relacje (@OneToMany/@ManyToOne) DZIALAJA identycznie jak w _12_hibernate - Spring Data DODAJE wygodne query methods NAWIGUJACE PRZEZ nie.");

        demonstrateCascadeSaveThroughRelationship();
        demonstrateNestedPropertyQueryMethod();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Adnotacje relacji SA IDENTYCZNE jak W `_12_hibernate` -
         *   Spring Data JPA to WARSTWA NAD JPA, NIE ZASTEPUJE mapowania.
         * - `cascade = CascadeType.ALL` NA `@OneToMany` -
         *   `repository.save(parent)` zapisuje TEZ dzieci - JEDNO
         *   wywolanie repozytorium, KASKADOWA operacja.
         * - `findByPowiazanaEncja_Pole(...)` - podkreslnik (`_`)
         *   PRZECHODZI PRZEZ relacje W query methods.
         * - `@ManyToOne` DOMYSLNIE `FetchType.EAGER`, `@OneToMany`
         *   DOMYSLNIE `FetchType.LAZY` - TA SAMA zasada co
         *   `_12_hibernate/Lesson15` - pogłębione W Lesson09 tego
         *   rozdzialu (problem N+1).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    @Entity(name = "Customer")
    static class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        List<Order> orders = new ArrayList<>();

        Customer() {
        }

        Customer(String name) {
            this.name = name;
        }

        void addOrder(Order order) {
            orders.add(order);
            order.customer = this;
        }
    }

    @Entity(name = "CustomerOrder")
    static class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String item;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "customer_id")
        Customer customer;

        Order() {
        }

        Order(String item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return item;
        }
    }

    interface CustomerRepository extends JpaRepository<Customer, Long> {
    }

    interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findByCustomer_Name(String customerName);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class CascadeApp {
    }

    private static void demonstrateCascadeSaveThroughRelationship() throws Exception {
        System.out.println("\n=== KASKADOWY ZAPIS - repository.save(customer) ZAPISUJE TEZ zamowienia ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson07cascade;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CascadeApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
            OrderRepository orderRepository = context.getBean(OrderRepository.class);

            Customer customer = new Customer("Jan Kowalski");
            customer.addOrder(new Order("Laptop"));
            customer.addOrder(new Order("Monitor"));

            customerRepository.save(customer);
            System.out.println("Zapisano 1 klienta Z 2 zamowieniami PRZEZ JEDNO wywolanie customerRepository.save(...)");
            System.out.println("Liczba zamowien W bazie (przez OSOBNE repozytorium): " + orderRepository.count());
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class NavigationApp {
    }

    private static void demonstrateNestedPropertyQueryMethod() throws Exception {
        System.out.println("\n=== findByCustomer_Name(...) - NAWIGACJA PRZEZ RELACJE W query method ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson07navigation;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NavigationApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
            OrderRepository orderRepository = context.getBean(OrderRepository.class);

            Customer customer = new Customer("Anna Nowak");
            customer.addOrder(new Order("Klawiatura"));
            customer.addOrder(new Order("Mysz"));
            customerRepository.save(customer);

            List<Order> orders = orderRepository.findByCustomer_Name("Anna Nowak");
            System.out.println("findByCustomer_Name(\"Anna Nowak\") -> " + orders);
            System.out.println("-> Podkreslnik W nazwie metody ('Customer_Name') powiedzial Spring Data: wejdz W pole 'customer', POTEM W jego pole 'name'.");
        } finally {
            context.close();
        }
    }
}
