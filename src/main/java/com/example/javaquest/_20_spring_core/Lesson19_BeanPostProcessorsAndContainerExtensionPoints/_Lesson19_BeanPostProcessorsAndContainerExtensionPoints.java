package com.example.javaquest._20_spring_core.Lesson19_BeanPostProcessorsAndContainerExtensionPoints;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class _Lesson19_BeanPostProcessorsAndContainerExtensionPoints {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: BeanPostProcessor I BeanFactoryPostProcessor - JAK DZIALA KONTENER 'POD SPODEM' ===");

        /*
         * ============================================================
         * 📦 MECHANIZM, NA KTORYM STOI (NIEMAL) WSZYSTKO
         * ============================================================
         * `@Autowired` (Lesson04/10), `@Value` (Lesson16), `@PostConstruct`
         * (Lesson18), a nawet AOP (Lesson21-22) - WSZYSTKIE dzialaja
         * dzieki DWOM interfejsom ROZSZERZAJACYM kontener:
         * `BeanFactoryPostProcessor` (modyfikuje DEFINICJE beanow ZANIM
         * ktokolwiek zostanie stworzony) i `BeanPostProcessor` (opakowuje/
         * modyfikuje KAZDY bean PODCZAS jego tworzenia, wokol
         * inicjalizacji). Dzisiaj piszesz WLASNE wersje OBU, zeby
         * zobaczyc, jak nisko-poziomowy jest ten mechanizm - i
         * odkrywasz, ze mechanizmy z Lesson04/16/18 to WLASNIE TAKIE
         * BeanPostProcessory, wbudowane w Springa.
         */
        System.out.println("BeanFactoryPostProcessor = modyfikuje DEFINICJE beanow PRZED stworzeniem. BeanPostProcessor = opakowuje KAZDY bean WOKOL inicjalizacji.");

        demonstrateCustomBeanPostProcessorOrderingAroundPostConstruct();
        demonstrateBeanFactoryPostProcessorModifiesDefinitionBeforeCreation();
        demonstrateBuiltInPostProcessorsPowerFamiliarFeatures();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `BeanFactoryPostProcessor` dziala NAJWCZESNIEJ - widzi
         *   TYLKO metadane (`BeanDefinition`), ZANIM cokolwiek powstanie.
         * - `BeanPostProcessor` dziala WOKOL KAZDEGO beana - `before`
         *   PRZED `@PostConstruct`/`InitializingBean`/`initMethod`
         *   (Lesson18), `after` PO nich wszystkich.
         * - `@Autowired` (`AutowiredAnnotationBeanPostProcessor`) i
         *   `@PostConstruct`/`@PreDestroy` (`CommonAnnotationBeanPostProcessor`)
         *   to WBUDOWANE `BeanPostProcessor` - "magia" Springa to
         *   ZASTOSOWANIE TEGO SAMEGO mechanizmu, ktory dzisiaj napisales
         *   recznie.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    static class MonitoredResource {
        @PostConstruct
        void init() {
            System.out.println("    [@PostConstruct] MonitoredResource.init()");
        }
    }

    static class LoggingBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (bean instanceof MonitoredResource) {
                System.out.println("  [BeanPostProcessor] PRZED inicjalizacja: " + beanName);
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) {
            if (bean instanceof MonitoredResource) {
                System.out.println("  [BeanPostProcessor] PO inicjalizacji: " + beanName);
            }
            return bean;
        }
    }

    @Configuration
    static class BeanPostProcessorConfig {
        @Bean
        static LoggingBeanPostProcessor loggingBeanPostProcessor() {
            // UWAGA: metody @Bean zwracajace BeanPostProcessor MUSZA byc 'static' - kontener
            // MUSI je wykryc i uruchomic WCZESNIE, ZANIM zacznie tworzyc INNE, zwykle beany
            // (ktore ten post-processor ma juz "widziec") - 'static' pozwala Springowi je
            // rozpoznac bez tworzenia CALEJ instancji klasy @Configuration najpierw.
            return new LoggingBeanPostProcessor();
        }

        @Bean
        MonitoredResource monitoredResource() {
            return new MonitoredResource();
        }
    }

    private static void demonstrateCustomBeanPostProcessorOrderingAroundPostConstruct() {
        System.out.println("\n=== WLASNY BeanPostProcessor: KOLEJNOSC WZGLEDEM @PostConstruct (Lesson18) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class)) {
            context.getBean(MonitoredResource.class);
            System.out.println("-> Kolejnosc: BeanPostProcessor.before -> @PostConstruct -> BeanPostProcessor.after - post-processor 'OPAKOWUJE' cala inicjalizacje.");
        }
    }

    // PUBLIC klasa I PUBLICZNE getter/setter - introspekcja JavaBean (java.beans.Introspector,
    // ktorego Spring uzywa do rozpoznawania "wlasciwosci" dla PropertyValues) dziala, jak SpEL w
    // Lesson17, TYLKO na PUBLICZNYCH elementach - pakietowa widocznosc (domyslna gdzie indziej w
    // tym kursie) dala mylacy blad "property is not writable", mimo POPRAWNEJ pary getter/setter.
    public static class ConfigurableGreeter {
        private String greeting = "(niezmodyfikowane)";

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }
    }

    static class GreetingModifyingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            BeanDefinition definition = beanFactory.getBeanDefinition("configurableGreeter");
            MutablePropertyValues propertyValues = definition.getPropertyValues();
            propertyValues.add("greeting", "ZMODYFIKOWANE przez BeanFactoryPostProcessor, ZANIM bean powstal!");
            System.out.println("  [BeanFactoryPostProcessor] zmodyfikowalem DEFINICJE beana 'configurableGreeter' - bean JESZCZE nie istnieje.");
        }
    }

    @Configuration
    static class BeanFactoryPostProcessorConfig {
        @Bean
        static GreetingModifyingBeanFactoryPostProcessor greetingModifyingBeanFactoryPostProcessor() {
            return new GreetingModifyingBeanFactoryPostProcessor();
        }

        @Bean
        ConfigurableGreeter configurableGreeter() {
            return new ConfigurableGreeter();
        }
    }

    private static void demonstrateBeanFactoryPostProcessorModifiesDefinitionBeforeCreation() {
        System.out.println("\n=== WLASNY BeanFactoryPostProcessor: MODYFIKACJA DEFINICJI PRZED STWORZENIEM ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanFactoryPostProcessorConfig.class)) {
            ConfigurableGreeter greeter = context.getBean(ConfigurableGreeter.class);
            System.out.println("Wynik getGreeting(): " + greeter.getGreeting());
            System.out.println("-> wartosc pola zostala USTAWIONA przez setter (setGreeting), mimo ze @Bean w ogole go NIE wywolal - BeanFactoryPostProcessor dopisal to do DEFINICJI.");
        }
    }

    @Configuration
    static class PlainConfig {
        @Bean
        MonitoredResource monitoredResource() {
            return new MonitoredResource();
        }
    }

    private static void demonstrateBuiltInPostProcessorsPowerFamiliarFeatures() {
        System.out.println("\n=== WBUDOWANE BeanPostProcessor - 'MAGIA' Springa TO TEN SAM MECHANIZM ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PlainConfig.class)) {
            Map<String, BeanPostProcessor> processors = context.getBeansOfType(BeanPostProcessor.class);
            System.out.println("Liczba WBUDOWANYCH BeanPostProcessor w zwyklym kontekscie: " + processors.size());
            for (String name : processors.keySet()) {
                System.out.println("  - " + name + " (" + processors.get(name).getClass().getSimpleName() + ")");
            }
            System.out.println("-> 'AutowiredAnnotationBeanPostProcessor' realizuje @Autowired (Lesson04/10), 'CommonAnnotationBeanPostProcessor' realizuje @PostConstruct/@PreDestroy (Lesson18).");
            System.out.println("   To NIE jest 'magia' - to DOKLADNIE TEN SAM mechanizm, ktory sam napisales wyzej w tej lekcji.");
        }
    }
}
