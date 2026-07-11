package com.example.javaquest._20_spring_core.Lesson18_LifecycleCallbacks;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class _Lesson18_LifecycleCallbacks {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: CALLBACKI CYKLU ZYCIA BEANA ===");

        /*
         * ============================================================
         * 📦 3 SPOSOBY REAGOWANIA NA "NARODZINY" I "SMIERC" BEANA
         * ============================================================
         * Bean ma cykl zycia: STWORZENIE -> WSTRZYKNIECIE zaleznosci ->
         * (opcjonalnie) INICJALIZACJA -> UZYWANIE -> (przy zamknieciu
         * kontekstu) NISZCZENIE. Spring daje 3 sposoby "podpiecia sie"
         * pod te momenty - JAKARTA `@PostConstruct`/`@PreDestroy`
         * (REKOMENDOWANE, standard NIEZALEZNY od Springa), interfejsy
         * `InitializingBean`/`DisposableBean` (STARSZE, Spring-specific),
         * atrybuty `initMethod`/`destroyMethod` na `@Bean` (dla klas,
         * ktorych kodu NIE jestes wlascicielem - jak w Lesson09).
         */
        System.out.println("3 sposoby: @PostConstruct/@PreDestroy (JAKARTA, REKOMENDOWANE) vs InitializingBean/DisposableBean (starsze) vs initMethod/destroyMethod.");

        demonstrateJakartaAnnotations();
        demonstrateInitializingBeanAndDisposableBean();
        demonstrateInitMethodDestroyMethodForExternalStyleClass();
        demonstrateOrderWhenMultipleMechanismsCombined();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@PostConstruct`/`@PreDestroy` (jakarta.annotation, JSR-250) -
         *   REKOMENDOWANE - dzialaja NIEZALEZNIE od Springa (ten sam
         *   standard w Jakarta EE), czytelne, adnotacje NA metodzie.
         * - `InitializingBean`/`DisposableBean` - STARSZE, Spring-specific
         *   (wymagaja implementacji interfejsu Springa) - dzis
         *   ODRADZANE na rzecz adnotacji, ale WCIAZ spotykane w
         *   starszym kodzie.
         * - `initMethod`/`destroyMethod` na `@Bean` - JEDYNA opcja dla
         *   klas BEZ dostepu do kodu (zewnetrzne biblioteki, jak
         *   Lesson09).
         * - Kolejnosc PRZY WSZYSTKICH naraz: `@PostConstruct` ->
         *   `InitializingBean.afterPropertiesSet()` -> custom
         *   `initMethod`.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    static class JakartaStyleResource {
        @PostConstruct
        void openConnection() {
            System.out.println("  [@PostConstruct] otwieram 'polaczenie' (PO wstrzykienciu zaleznosci, PRZED uzyciem)");
        }

        void use() {
            System.out.println("  uzywam zasobu");
        }

        @PreDestroy
        void closeConnection() {
            System.out.println("  [@PreDestroy] zamykam 'polaczenie' (PRZED zniszczeniem beana, przy zamknieciu kontekstu)");
        }
    }

    @Configuration
    static class JakartaConfig {
        @Bean
        JakartaStyleResource jakartaStyleResource() {
            return new JakartaStyleResource();
        }
    }

    private static void demonstrateJakartaAnnotations() {
        System.out.println("\n=== @PostConstruct / @PreDestroy (JAKARTA, JSR-250) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JakartaConfig.class)) {
            JakartaStyleResource resource = context.getBean(JakartaStyleResource.class);
            resource.use();
            System.out.println("Zamykam kontekst (try-with-resources) - obserwuj, KIEDY pojawi sie @PreDestroy:");
        }
    }

    static class SpringInterfaceStyleResource implements InitializingBean, DisposableBean {
        @Override
        public void afterPropertiesSet() {
            System.out.println("  [InitializingBean.afterPropertiesSet()] inicjalizacja (starszy, Spring-specific sposob)");
        }

        void use() {
            System.out.println("  uzywam zasobu");
        }

        @Override
        public void destroy() {
            System.out.println("  [DisposableBean.destroy()] sprzatanie (starszy, Spring-specific sposob)");
        }
    }

    @Configuration
    static class SpringInterfaceConfig {
        @Bean
        SpringInterfaceStyleResource springInterfaceStyleResource() {
            return new SpringInterfaceStyleResource();
        }
    }

    private static void demonstrateInitializingBeanAndDisposableBean() {
        System.out.println("\n=== InitializingBean / DisposableBean (STARSZY, Spring-specific sposob) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringInterfaceConfig.class)) {
            SpringInterfaceStyleResource resource = context.getBean(SpringInterfaceStyleResource.class);
            resource.use();
            System.out.println("Zamykam kontekst - obserwuj destroy():");
        }
    }

    // Symulacja klasy Z ZEWNETRZNEJ biblioteki (jak w Lesson09) - NIE MOZESZ dodac jej adnotacji.
    static class ExternalStyleConnectionPool {
        void start() {
            System.out.println("  [initMethod='start'] pula polaczen WYSTARTOWANA");
        }

        void shutdown() {
            System.out.println("  [destroyMethod='shutdown'] pula polaczen ZATRZYMANA");
        }
    }

    @Configuration
    static class ExternalStyleConfig {
        @Bean(initMethod = "start", destroyMethod = "shutdown")
        ExternalStyleConnectionPool externalStyleConnectionPool() {
            return new ExternalStyleConnectionPool();
        }
    }

    private static void demonstrateInitMethodDestroyMethodForExternalStyleClass() {
        System.out.println("\n=== @Bean(initMethod=..., destroyMethod=...) DLA KLASY 'Z ZEWNETRZNEJ BIBLIOTEKI' ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExternalStyleConfig.class)) {
            context.getBean(ExternalStyleConnectionPool.class);
            System.out.println("Zamykam kontekst - obserwuj shutdown():");
        }
    }

    static class AllThreeMechanismsResource implements InitializingBean, DisposableBean {
        @PostConstruct
        void step1PostConstruct() {
            System.out.println("  1. @PostConstruct");
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("  2. InitializingBean.afterPropertiesSet()");
        }

        void step3CustomInit() {
            System.out.println("  3. custom initMethod");
        }

        @PreDestroy
        void stepAPreDestroy() {
            System.out.println("  A. @PreDestroy");
        }

        @Override
        public void destroy() {
            System.out.println("  B. DisposableBean.destroy()");
        }

        void stepCCustomDestroy() {
            System.out.println("  C. custom destroyMethod");
        }
    }

    @Configuration
    static class AllThreeConfig {
        @Bean(initMethod = "step3CustomInit", destroyMethod = "stepCCustomDestroy")
        AllThreeMechanismsResource allThreeMechanismsResource() {
            return new AllThreeMechanismsResource();
        }
    }

    private static void demonstrateOrderWhenMultipleMechanismsCombined() {
        System.out.println("\n=== KOLEJNOSC, GDY WSZYSTKIE 3 MECHANIZMY SA UZYTE NARAZ ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AllThreeConfig.class)) {
            context.getBean(AllThreeMechanismsResource.class);
            System.out.println("Inicjalizacja zakonczona (kolejnosc: 1 -> 2 -> 3, jak widac wyzej). Zamykam kontekst (oczekiwana kolejnosc: A -> B -> C):");
        }
    }
}
