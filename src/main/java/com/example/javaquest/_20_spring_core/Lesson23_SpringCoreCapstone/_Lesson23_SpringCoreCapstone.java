package com.example.javaquest._20_spring_core.Lesson23_SpringCoreCapstone;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class _Lesson23_SpringCoreCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 23: KAPSZTON - 'JAVAQUEST ORDER PROCESSING' ===");

        /*
         * ============================================================
         * 📦 CALY ROZDZIAL W JEDNYM, SPOJNYM DEMO
         * ============================================================
         * Mini-system przetwarzania zamowien LACZACY WSZYSTKIE 22
         * poprzednie lekcje: stereotypy (Lesson08), constructor
         * injection (Lesson10), @Qualifier/@Primary (Lesson12), profile
         * (Lesson15), @Value (Lesson16), cykl zycia (Lesson18),
         * zdarzenia (Lesson20), AOP (Lesson21-22). CELOWO BEZ Spring
         * Boota - goly `AnnotationConfigApplicationContext`, zebys
         * DOCENIL, ile automatyzuje `_21_spring_boot`, ktory poznasz
         * jako NASTEPNY krok.
         */
        System.out.println("Mini-system zamowien laczacy WSZYSTKIE mechanizmy tego rozdzialu - BEZ Spring Boota (celowo, zobaczysz roznice w nastepnym rozdziale).");

        runScenario("dev");
        runScenario("prod");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * Przeszedles od "co to jest Spring" (Lesson01) przez kontener,
         * DI (3 style), konfiguracje (3 style), zakresy, profile,
         * wlasciwosci, SpEL, cykl zycia, mechanizmy WEWNETRZNE kontenera
         * (BeanPostProcessor), zdarzenia, az po AOP - WSZYSTKO, na czym
         * stoi reszta ekosystemu Springa. Kolejny krok: `_21_spring_boot` -
         * zobaczysz, jak WIELE z tego, co dzisiaj robiles RECZNIE
         * (rejestrowanie beanow, aktywacja profili, itd.), Spring Boot
         * ROBI ZA CIEBIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 23, KONIEC ROZDZIALU _20_spring_core ===");
    }

    record Order(String id, double amount) {
    }

    interface OrderRepository {
        void save(Order order);
    }

    @Repository
    static class InMemoryOrderRepository implements OrderRepository {
        private final List<Order> storage = new ArrayList<>();

        @PostConstruct
        void connect() {
            System.out.println("  [InMemoryOrderRepository] 'polaczenie' otwarte (profil dev - baza w pamieci)");
        }

        @Override
        public void save(Order order) {
            storage.add(order);
            System.out.println("    [InMemoryOrderRepository] zapisano " + order.id() + " (" + storage.size() + " zamowien lacznie w pamieci)");
        }

        @PreDestroy
        void disconnect() {
            System.out.println("  [InMemoryOrderRepository] 'polaczenie' zamkniete (dev)");
        }
    }

    @Repository
    static class ProdOrderRepository implements OrderRepository {
        @PostConstruct
        void connect() {
            System.out.println("  [ProdOrderRepository] 'polaczenie' z PRAWDZIWA baza otwarte (profil prod, symulacja)");
        }

        @Override
        public void save(Order order) {
            System.out.println("    [ProdOrderRepository] INSERT INTO orders(id, amount) VALUES ('" + order.id() + "', " + order.amount() + ")");
        }

        @PreDestroy
        void disconnect() {
            System.out.println("  [ProdOrderRepository] 'polaczenie' zamkniete (prod)");
        }
    }

    interface NotificationSender {
        void notify(String message);
    }

    @Component
    static class EmailNotificationSender implements NotificationSender {
        @Override
        public void notify(String message) {
            System.out.println("    [EmailNotificationSender] " + message);
        }
    }

    @Component
    static class SmsNotificationSender implements NotificationSender {
        @Override
        public void notify(String message) {
            System.out.println("    [SmsNotificationSender] " + message);
        }
    }

    record OrderProcessedEvent(String orderId, double finalAmount) {
    }

    @Component
    static class OrderProcessedListener {
        @EventListener
        void onOrderProcessed(OrderProcessedEvent event) {
            System.out.println("    [OrderProcessedListener] zdarzenie odebrane - zamowienie " + event.orderId() + ", kwota koncowa: " + event.finalAmount());
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Audited {
    }

    // Pelna nazwa WYMAGANA w wyrazeniu pointcut ponizej - patrz notatka w Lesson21/22 (parser
    // AspectJ nie rozwiazuje zagniezdzonej adnotacji po prostej nazwie, pointcut po cichu NIE
    // dopasowuje NICZEGO bez tego).
    private static final String AUDITED = "com.example.javaquest._20_spring_core.Lesson23_SpringCoreCapstone._Lesson23_SpringCoreCapstone.Audited";

    @Service
    static class OrderProcessingService {
        private final OrderRepository repository;
        private final NotificationSender notificationSender;
        private final ApplicationEventPublisher publisher;

        @Value("${order.discount.threshold:1000}")
        private double discountThreshold;

        @Autowired
        OrderProcessingService(OrderRepository repository,
                                @Qualifier("smsNotificationSender") NotificationSender notificationSender,
                                ApplicationEventPublisher publisher) {
            this.repository = repository;
            this.notificationSender = notificationSender;
            this.publisher = publisher;
        }

        @Audited
        String processOrder(String id, double amount) {
            boolean discounted = amount > discountThreshold;
            double finalAmount = discounted ? amount * 0.9 : amount;

            repository.save(new Order(id, finalAmount));
            notificationSender.notify("Zamowienie " + id + " przyjete, kwota: " + finalAmount + (discounted ? " (10% rabatu za przekroczenie progu " + discountThreshold + ")" : ""));
            publisher.publishEvent(new OrderProcessedEvent(id, finalAmount));

            return "Przetworzono " + id;
        }
    }

    @Aspect
    static class AuditAspect {
        @Around("@annotation(" + AUDITED + ")")
        Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("  [AuditAspect] START " + joinPoint.getSignature().getName() + joinPoint.getArgs()[0]);
            Object result = joinPoint.proceed();
            System.out.println("  [AuditAspect] KONIEC " + joinPoint.getSignature().getName());
            return result;
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class CapstoneConfig {
        @Bean
        @Profile("dev")
        OrderRepository inMemoryOrderRepository() {
            return new InMemoryOrderRepository();
        }

        @Bean
        @Profile("prod")
        OrderRepository prodOrderRepository() {
            return new ProdOrderRepository();
        }

        @Bean
        @Primary
        NotificationSender emailNotificationSender() {
            return new EmailNotificationSender();
        }

        @Bean
        NotificationSender smsNotificationSender() {
            return new SmsNotificationSender();
        }

        @Bean
        OrderProcessedListener orderProcessedListener() {
            return new OrderProcessedListener();
        }

        @Bean
        OrderProcessingService orderProcessingService(OrderRepository repository,
                                                        @Qualifier("smsNotificationSender") NotificationSender notificationSender,
                                                        ApplicationEventPublisher publisher) {
            return new OrderProcessingService(repository, notificationSender, publisher);
        }

        @Bean
        AuditAspect auditAspect() {
            return new AuditAspect();
        }
    }

    private static void runScenario(String profile) {
        System.out.println("\n" + "=".repeat(20) + " SCENARIUSZ: PROFIL '" + profile + "' " + "=".repeat(20));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profile);
        context.register(CapstoneConfig.class);
        context.refresh();

        OrderProcessingService service = context.getBean(OrderProcessingService.class);

        System.out.println("\n--- Zamowienie PONIZEJ progu rabatu ---");
        System.out.println(service.processOrder("ORD-100", 250.0));

        System.out.println("\n--- Zamowienie POWYZEJ progu rabatu (> 1000) ---");
        System.out.println(service.processOrder("ORD-101", 1500.0));

        System.out.println("\nZamykam kontekst profilu '" + profile + "' (obserwuj @PreDestroy repozytorium):");
        context.close();
    }
}
