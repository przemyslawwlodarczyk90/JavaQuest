package com.example.javaquest._23_spring_data_jpa.Lesson09_LazyLoadingAndNPlusOne;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson09_LazyLoadingAndNPlusOne {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: Problem N+1 W Spring Data JPA ===");

        /*
         * ============================================================
         * 📦 N+1 - POGLEBIENIE _12_hibernate/Lesson15, TERAZ LICZONE NAPRAWDE
         * ============================================================
         * `_12_hibernate/Lesson15_FetchTypesAndNPlusOne` uczyl
         * KONCEPCJI (LAZY vs EAGER, problem N+1). Tu NAPRAWDE
         * LICZYMY zapytania SQL PRZEZ WLASNY `StatementInspector`
         * Hibernate (`hibernate.session_factory.statement_inspector`) -
         * ZERO zgadywania, DOKLADNA liczba zapytan WYGENEROWANYCH
         * przez repozytorium Spring Data.
         *
         * Scenariusz: `findAll()` NA `Customer` (1 zapytanie) +
         * DOSTEP DO `customer.orders` (LAZY) DLA KAZDEGO klienta W
         * PETLI (N DODATKOWYCH zapytan) = 1+N zapytan ZAMIAST 1.
         */
        System.out.println("N+1 = 1 zapytanie NA liste rodzicow + N DODATKOWYCH zapytan (po 1 NA KAZDEGO rodzica) PRZY dostepie DO leniwej kolekcji W petli.");

        demonstrateNPlusOneProblemCountingRealQueries();
        demonstrateJoinFetchFixesNPlusOne();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - N+1 WYSTEPUJE, gdy LADUJESZ liste rodzicow (1 zapytanie),
         *   POTEM W PETLI DOTYKASZ ich LAZY kolekcji (N zapytan).
         * - `StatementInspector` Hibernate to REALNY sposob ZLICZENIA
         *   zapytan W TESCIE/demo - NIE trzeba zgadywac Z `show-sql`.
         * - `JOIN FETCH` W `@Query` LADUJE rodzicow I dzieci JEDNYM
         *   zapytaniem (SQL `JOIN`) - N+1 znika CALKOWICIE.
         * - Nastepne lekcje pokaza 2 INNE sposoby rozwiazania: `@EntityGraph`
         *   (Lesson10, deklaratywny) I projekcje (Lesson11, gdy W ogole
         *   NIE potrzebujesz calej encji).
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
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
        @Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.orders")
        List<Customer> findAllWithOrdersJoinFetch();
    }

    @Service
    static class CustomerLoadingService {
        private final CustomerRepository repository;

        @Autowired
        CustomerLoadingService(CustomerRepository repository) {
            this.repository = repository;
        }

        @Transactional
        int loadAllAndSumOrdersLazily() {
            // Cala metoda dziala W JEDNEJ transakcji - sesja Hibernate pozostaje OTWARTA
            // przez CALY czas, WLACZNIE Z dostepem DO leniwej kolekcji 'orders' W petli.
            List<Customer> customers = repository.findAll();
            int totalOrders = 0;
            for (Customer customer : customers) {
                totalOrders += customer.orders.size();
            }
            return totalOrders;
        }

        @Transactional
        int loadAllWithJoinFetchAndSumOrders() {
            List<Customer> customers = repository.findAllWithOrdersJoinFetch();
            int totalOrders = 0;
            for (Customer customer : customers) {
                totalOrders += customer.orders.size();
            }
            return totalOrders;
        }
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
    static class NPlusOneApp {
        @Bean
        CustomerLoadingService customerLoadingService(CustomerRepository repository) {
            return new CustomerLoadingService(repository);
        }
    }

    private static void demonstrateNPlusOneProblemCountingRealQueries() throws Exception {
        System.out.println("\n=== PROBLEM N+1 - REALNIE ZLICZONE zapytania SQL ===");

        /*
         * WAZNE: dostep DO leniwej kolekcji ('customer.orders') MUSI nastapic W TRAKCIE
         * OTWARTEJ sesji Hibernate - repozytorium Spring Data ZAMYKA sesje PO WYJSCIU
         * Z metody, WIEC petla PO 'orders' MUSI byc W SRODKU tej samej metody
         * '@Transactional' (patrz 'CustomerLoadingService'), NIE W main() PO fakcie -
         * inaczej dostaniemy 'LazyInitializationException: no Session'.
         */
        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson09nplusone;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector", QueryCountingInspector.class.getName());

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NPlusOneApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository repository = context.getBean(CustomerRepository.class);
            CustomerLoadingService service = context.getBean(CustomerLoadingService.class);
            seedCustomersWithOrders(repository);

            QueryCountingInspector.QUERY_COUNT.set(0);
            int totalOrders = service.loadAllAndSumOrdersLazily();
            System.out.println("Po findAll() + petla dotykajaca 'customer.orders.size()' DLA 5 klientow: " + QueryCountingInspector.QUERY_COUNT.get() + " zapytan LACZNIE");
            System.out.println("-> 1 (findAll) + 5 (po 1 NA KAZDEGO klienta, PRZY dostepie DO LAZY kolekcji) = KLASYCZNY problem N+1. Suma zamowien: " + totalOrders);
        } finally {
            context.close();
        }
    }

    private static void demonstrateJoinFetchFixesNPlusOne() throws Exception {
        System.out.println("\n=== NAPRAWA: JOIN FETCH W @Query - JEDNO zapytanie ZAMIAST 1+N ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson09joinfetch;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");
        props.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector", QueryCountingInspector.class.getName());

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NPlusOneApp.class)
                .properties(props)
                .run();
        try {
            CustomerRepository repository = context.getBean(CustomerRepository.class);
            CustomerLoadingService service = context.getBean(CustomerLoadingService.class);
            seedCustomersWithOrders(repository);

            QueryCountingInspector.QUERY_COUNT.set(0);
            int totalOrders = service.loadAllWithJoinFetchAndSumOrders();
            System.out.println("Po findAllWithOrdersJoinFetch() + petla dotykajaca 'orders': " + QueryCountingInspector.QUERY_COUNT.get() + " zapytanie LACZNIE (oczekiwane: 1, NIE 1+5). Suma zamowien: " + totalOrders);
        } finally {
            context.close();
        }
    }
}
