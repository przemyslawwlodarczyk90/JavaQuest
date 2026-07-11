package com.example.javaquest._23_spring_data_jpa.Lesson10_EntityGraph;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson10_EntityGraph {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: @EntityGraph - DEKLARATYWNA alternatywa dla JOIN FETCH ===");

        /*
         * ============================================================
         * 📦 @EntityGraph - JOIN FETCH BEZ PISANIA WLASNEGO JPQL
         * ============================================================
         * Lesson09 naprawil N+1 PRZEZ RECZNE napisanie `JOIN FETCH` W
         * `@Query`. `@EntityGraph` daje TEN SAM efekt (JEDNO zapytanie
         * SQL Z JOIN), ale DEKLARATYWNIE - BEZ pisania JPQL W OGOLE:
         *
         * 1) AD-HOC: `@EntityGraph(attributePaths = {"orders"})`
         *    BEZPOSREDNIO NA metodzie repozytorium (np. NA
         *    `findAll()`/query method) - NAJPROSTSZY sposob.
         * 2) NAZWANY: `@NamedEntityGraph` NA encji (jak
         *    `@NamedQuery` Z `_12_hibernate/Lesson22`) - DEFINIUJESZ RAZ,
         *    UZYWASZ W WIELU miejscach PRZEZ NAZWE.
         */
        System.out.println("@EntityGraph = JOIN FETCH BEZ pisania JPQL - deklaratywne wskazanie, KTORE relacje ZALADOWAC EAGER dla TEGO KONKRETNEGO zapytania.");

        demonstrateAdHocEntityGraph();
        demonstrateNamedEntityGraph();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EntityGraph(attributePaths = {"pole1", "pole2"})` NA
         *   metodzie repozytorium - AD-HOC, SZYBKIE rozwiazanie.
         * - `@NamedEntityGraph` NA encji + `@EntityGraph(value =
         *   "nazwa")` NA repozytorium - PONOWNIE UZYWALNE, ZDEFINIOWANE
         *   W JEDNYM miejscu.
         * - EFEKT KONCOWY jest TAKI SAM jak `JOIN FETCH` Z Lesson09 -
         *   JEDNO zapytanie SQL ZAMIAST 1+N - RÓZNICA jest TYLKO W
         *   SPOSOBIE zapisu (deklaratywny vs jawny JPQL).
         * - WYBOR miedzy `JOIN FETCH` A `@EntityGraph` to KWESTIA
         *   PREFERENCJI - `@EntityGraph` UNIKA duplikacji, gdy MASZ
         *   JUZ inne `@Query` DLA TEJ SAMEJ metody (nie mozesz
         *   dodac `JOIN FETCH` DO `findById` WBUDOWANEGO, ale MOZESZ
         *   go PRZESLONIC `@EntityGraph`).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    public static class QueryCountingInspector implements StatementInspector {
        static final AtomicInteger QUERY_COUNT = new AtomicInteger(0);

        @Override
        public String inspect(String sql) {
            QUERY_COUNT.incrementAndGet();
            return sql;
        }
    }

    @Entity(name = "Customer")
    @NamedEntityGraph(name = "Customer.withOrders", attributeNodes = @NamedAttributeNode("orders"))
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

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id")
        Customer customer;

        Order() {
        }

        Order(String item) {
            this.item = item;
        }
    }

    interface CustomerRepository extends JpaRepository<Customer, Long> {
        @EntityGraph(attributePaths = {"orders"})
        List<Customer> findAll();

        @EntityGraph(value = "Customer.withOrders")
        List<Customer> findByName(String name);
    }

    static void seedCustomersWithOrders(CustomerRepository repository) {
        for (int i = 1; i <= 5; i++) {
            Customer customer = new Customer("Klient " + i);
            customer.addOrder(new Order("Zamowienie A" + i));
            customer.addOrder(new Order("Zamowienie B" + i));
            repository.save(customer);
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class AdHocApp {
    }

    private static void demonstrateAdHocEntityGraph() throws Exception {
        System.out.println("\n=== AD-HOC @EntityGraph(attributePaths = {\"orders\"}) NA findAll() ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson10adhoc;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector", QueryCountingInspector.class.getName());

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AdHocApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository repository = context.getBean(CustomerRepository.class);
            seedCustomersWithOrders(repository);

            QueryCountingInspector.QUERY_COUNT.set(0);
            List<Customer> customers = repository.findAll();
            int totalOrders = 0;
            for (Customer customer : customers) {
                totalOrders += customer.orders.size();
            }
            System.out.println("findAll() Z @EntityGraph + dostep do 'orders' -> " + QueryCountingInspector.QUERY_COUNT.get() + " zapytanie LACZNIE (oczekiwane: 1). Suma zamowien: " + totalOrders);
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class NamedApp {
    }

    private static void demonstrateNamedEntityGraph() throws Exception {
        System.out.println("\n=== @NamedEntityGraph NA ENCJI + @EntityGraph(value = \"nazwa\") NA repozytorium ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson10named;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector", QueryCountingInspector.class.getName());

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NamedApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository repository = context.getBean(CustomerRepository.class);
            seedCustomersWithOrders(repository);

            QueryCountingInspector.QUERY_COUNT.set(0);
            List<Customer> found = repository.findByName("Klient 3");
            int totalOrders = 0;
            for (Customer customer : found) {
                totalOrders += customer.orders.size();
            }
            System.out.println("findByName(\"Klient 3\") Z @NamedEntityGraph -> " + QueryCountingInspector.QUERY_COUNT.get() + " zapytanie LACZNIE (oczekiwane: 1). Zamowienia: " + totalOrders);
        } finally {
            context.close();
        }
    }
}
