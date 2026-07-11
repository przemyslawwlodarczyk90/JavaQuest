package com.example.javaquest._20_spring_core.Lesson20_ApplicationEvents;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class _Lesson20_ApplicationEvents {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: ZDARZENIA APLIKACJI (ApplicationEvent / @EventListener) ===");

        /*
         * ============================================================
         * 📦 LUZNE POWIAZANIE PRZEZ ZDARZENIA
         * ============================================================
         * Lesson07 zapowiedzial `context.publishEvent(...)` jako JEDNA
         * z dodatkowych mozliwosci `ApplicationContext`. Dzisiaj
         * pelna lekcja: publisher (`ApplicationEventPublisher`,
         * wstrzykiwany jak KAZDY inny bean) OGLASZA zdarzenie, a
         * DOWOLNA liczba listenerow (`@EventListener` lub
         * `ApplicationListener<T>`) MOZE zareagowac - BEZ ZADNEJ
         * bezposredniej zaleznosci miedzy publisherem a listenerami.
         * To DOKLADNIE ten sam wzorzec, co ręczny publisher/listener z
         * `_17_architecture/Lesson18_EventDrivenCommunicationBetweenModules`
         * - tu kontener Springa "podpina" listenery ZA CIEBIE.
         */
        System.out.println("Publisher OGLASZA zdarzenie, DOWOLNA liczba listenerow reaguje - BEZ bezposredniej zaleznosci miedzy nimi.");

        demonstrateBasicPublishSubscribe();
        demonstrateMultipleListenersForSameEvent();
        demonstratePlainPojoEventsSinceSpring42();
        demonstrateEventAsDecouplingMechanism();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ApplicationEventPublisher` publikuje, `@EventListener`
         *   (na metodzie, DOWOLNY typ parametru) lub `ApplicationListener<T>`
         *   (interfejs, WYMAGA `T extends ApplicationEvent`) subskrybuje.
         * - WIELU listenerow MOZE reagowac na TO SAMO zdarzenie - ZERO
         *   zmian po stronie publishera przy dodawaniu KOLEJNEGO.
         * - Od Spring 4.2 zdarzenia MOGA byc ZWYKLYMI POJO (nie musza
         *   dziedziczyc po `ApplicationEvent`) - `@EventListener`
         *   obsluguje OBA warianty.
         * - To NAJLEPSZY sposob rozwiazania cyklicznej zaleznosci
         *   (Lesson13) - ZAMIAST A wywolujacego B bezposrednio, A
         *   publikuje zdarzenie, B (i KTOKOLWIEK inny) subskrybuje.
         */
        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    static class OrderPlacedEvent extends ApplicationEvent {
        private final String orderId;

        OrderPlacedEvent(Object source, String orderId) {
            super(source);
            this.orderId = orderId;
        }

        String getOrderId() {
            return orderId;
        }
    }

    @Component
    static class OrderService {
        private final org.springframework.context.ApplicationEventPublisher publisher;

        OrderService(org.springframework.context.ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        void placeOrder(String orderId) {
            System.out.println("  [OrderService] zamowienie " + orderId + " zlozone - publikuje zdarzenie...");
            publisher.publishEvent(new OrderPlacedEvent(this, orderId));
        }
    }

    @Component
    static class EmailNotificationListener implements ApplicationListener<OrderPlacedEvent> {
        @Override
        public void onApplicationEvent(OrderPlacedEvent event) {
            System.out.println("  [EmailNotificationListener] wysylam e-mail o zamowieniu " + event.getOrderId());
        }
    }

    @Configuration
    static class BasicConfig {
        @Bean
        OrderService orderService(org.springframework.context.ApplicationEventPublisher publisher) {
            return new OrderService(publisher);
        }

        @Bean
        EmailNotificationListener emailNotificationListener() {
            return new EmailNotificationListener();
        }
    }

    private static void demonstrateBasicPublishSubscribe() {
        System.out.println("\n=== PODSTAWOWY PUBLISH/SUBSCRIBE ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class)) {
            OrderService service = context.getBean(OrderService.class);
            service.placeOrder("ORD-001");
            System.out.println("-> OrderService NIE WIE nic o EmailNotificationListener - zaden import, zadna zaleznosc miedzy nimi.");
        }
    }

    @Component
    static class SmsNotificationListener {
        @EventListener
        void onOrderPlaced(OrderPlacedEvent event) {
            System.out.println("  [SmsNotificationListener] wysylam SMS o zamowieniu " + event.getOrderId());
        }
    }

    @Component
    static class InventoryUpdateListener {
        @EventListener
        void onOrderPlaced(OrderPlacedEvent event) {
            System.out.println("  [InventoryUpdateListener] aktualizuje stan magazynu dla zamowienia " + event.getOrderId());
        }
    }

    @Configuration
    static class MultipleListenersConfig {
        @Bean
        OrderService orderService(org.springframework.context.ApplicationEventPublisher publisher) {
            return new OrderService(publisher);
        }

        @Bean
        SmsNotificationListener smsNotificationListener() {
            return new SmsNotificationListener();
        }

        @Bean
        InventoryUpdateListener inventoryUpdateListener() {
            return new InventoryUpdateListener();
        }
    }

    private static void demonstrateMultipleListenersForSameEvent() {
        System.out.println("\n=== WIELU LISTENEROW REAGUJACYCH NA TO SAMO ZDARZENIE ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MultipleListenersConfig.class)) {
            OrderService service = context.getBean(OrderService.class);
            service.placeOrder("ORD-002");
            System.out.println("-> DWA NIEZALEZNE listenery zareagowaly na TO SAMO zdarzenie - dodanie 3. listenera NIE wymagaloby ZMIANY w OrderService.");
        }
    }

    record UserRegisteredEvent(String email) {
        // Zwykly rekord (POJO), BEZ dziedziczenia po ApplicationEvent - mozliwe od Spring 4.2 (2015),
        // tak jak zapowiedziano w Lesson07.
    }

    @Component
    static class WelcomeEmailListener {
        @EventListener
        void onUserRegistered(UserRegisteredEvent event) {
            System.out.println("  [WelcomeEmailListener] witam nowego uzytkownika: " + event.email());
        }
    }

    @Component
    static class UserRegistrationService {
        private final org.springframework.context.ApplicationEventPublisher publisher;

        UserRegistrationService(org.springframework.context.ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        void register(String email) {
            System.out.println("  [UserRegistrationService] rejestruje: " + email);
            publisher.publishEvent(new UserRegisteredEvent(email));
        }
    }

    @Configuration
    static class PlainPojoEventConfig {
        @Bean
        UserRegistrationService userRegistrationService(org.springframework.context.ApplicationEventPublisher publisher) {
            return new UserRegistrationService(publisher);
        }

        @Bean
        WelcomeEmailListener welcomeEmailListener() {
            return new WelcomeEmailListener();
        }
    }

    private static void demonstratePlainPojoEventsSinceSpring42() {
        System.out.println("\n=== ZDARZENIE JAKO ZWYKLY POJO (BEZ ApplicationEvent, Spring 4.2+) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PlainPojoEventConfig.class)) {
            UserRegistrationService service = context.getBean(UserRegistrationService.class);
            service.register("ala@przyklad.pl");
            System.out.println("-> 'UserRegisteredEvent' to ZWYKLY rekord - @EventListener i tak go dostarczyl, PRZED 4.2 wymagaloby to dziedziczenia po ApplicationEvent.");
        }
    }

    // Symulacja "cyklu" z Lesson13, TERAZ rozwiazanego przez zdarzenia zamiast bezposredniej zaleznosci.
    record StockLowEvent(String productId, int remaining) {
    }

    @Component
    static class WarehouseService {
        private final org.springframework.context.ApplicationEventPublisher publisher;
        private final List<String> reorderLog = new ArrayList<>();

        WarehouseService(org.springframework.context.ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        void sell(String productId, int remaining) {
            if (remaining < 5) {
                publisher.publishEvent(new StockLowEvent(productId, remaining));
            }
        }

        List<String> getReorderLog() {
            return reorderLog;
        }
    }

    @Component
    static class PurchasingService {
        @EventListener
        void onStockLow(StockLowEvent event) {
            System.out.println("  [PurchasingService] niski stan '" + event.productId() + "' (" + event.remaining() + " szt.) - skladam zamowienie uzupelniajace!");
        }
    }

    @Configuration
    static class DecouplingConfig {
        @Bean
        WarehouseService warehouseService(org.springframework.context.ApplicationEventPublisher publisher) {
            return new WarehouseService(publisher);
        }

        @Bean
        PurchasingService purchasingService() {
            return new PurchasingService();
        }
    }

    private static void demonstrateEventAsDecouplingMechanism() {
        System.out.println("\n=== ZDARZENIA JAKO ROZWIAZANIE PROBLEMU Z LESSON13 (CYKLICZNE ZALEZNOSCI) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DecouplingConfig.class)) {
            WarehouseService warehouse = context.getBean(WarehouseService.class);
            warehouse.sell("SKU-42", 3);
            System.out.println("-> WarehouseService i PurchasingService NIE MAJA zadnej bezposredniej zaleznosci konstruktorowej miedzy soba -");
            System.out.println("   gdyby bylo odwrotnie (PurchasingService tez potrzebowalby WarehouseService), miesci Lesson13 pokazalby cykl. TU go NIE MA.");
        }
    }
}
