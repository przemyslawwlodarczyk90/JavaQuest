package com.example.javaquest._20_spring_core.Lesson07_ApplicationContext;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.io.Resource;

import java.util.Locale;

public class _Lesson07_ApplicationContext {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: ApplicationContext ===");

        /*
         * ============================================================
         * 📦 BeanFactory (PODSTAWA) vs ApplicationContext (ROZSZERZENIE)
         * ============================================================
         * `BeanFactory` to NAJBARDZIEJ PODSTAWOWY interfejs kontenera —
         * umie tylko tworzyć i wiązać beany, i to LENIWIE (lazy) — bean
         * powstaje DOPIERO przy pierwszym `getBean()`. `ApplicationContext`
         * DZIEDZICZY po `BeanFactory` i DOKŁADA: (1) CHĘTNE (eager)
         * tworzenie singletonów — WSZYSTKIE powstają PRZY STARCIE, nie
         * na żądanie, (2) publikowanie zdarzeń (`ApplicationEventPublisher`
         * — zapowiedź Lesson20), (3) internacjonalizację (`MessageSource`),
         * (4) ładowanie zasobów (`ResourceLoader`). W PRAKTYCE zawsze
         * używasz `ApplicationContext` (np. `AnnotationConfigApplicationContext`)
         * — `BeanFactory` "z gołymi rękami" pokazujemy dziś TYLKO, żeby
         * zobaczyć RÓŻNICĘ.
         */
        System.out.println("BeanFactory = podstawa (leniwe tworzenie). ApplicationContext = BeanFactory + eventy + i18n + eager singletons.");

        demonstrateLazyBeanFactoryVsEagerApplicationContext();
        demonstrateApplicationContextExtraFeatures();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ApplicationContext extends BeanFactory` — to ROZSZERZENIE,
         *   nie alternatywa.
         * - Goły `BeanFactory` tworzy singletony LENIWIE (przy pierwszym
         *   `getBean()`) — `ApplicationContext` tworzy je CHĘTNIE, PRZY
         *   STARCIE (metoda `refresh()`).
         * - `ApplicationContext` to TEŻ `MessageSource` (i18n),
         *   `ApplicationEventPublisher` (eventy, Lesson20) i
         *   `ResourceLoader` (pliki/zasoby) — WSZYSTKO w JEDNYM obiekcie.
         * - W praktyce ZAWSZE używasz `ApplicationContext` — surowy
         *   `BeanFactory` widzisz tylko w kodzie NISKIEGO poziomu/samego
         *   Springa.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    static class LoggingBean {
        LoggingBean() {
            System.out.println("  >>> KONSTRUKTOR LoggingBean WYWOLANY <<<");
        }
    }

    @Configuration
    static class AppConfig {
        @Bean
        LoggingBean loggingBean() {
            return new LoggingBean();
        }
    }

    private static void demonstrateLazyBeanFactoryVsEagerApplicationContext() {
        System.out.println("\n=== LENIWY BeanFactory vs CHETNY ApplicationContext ===");

        System.out.println("--- Goly BeanFactory (DefaultListableBeanFactory) ---");
        DefaultListableBeanFactory rawFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(rawFactory);
        reader.register(LoggingBean.class);
        System.out.println("Definicja ZAREJESTROWANA - zauwaz: BRAK printa z konstruktora powyzej (bean JESZCZE nie istnieje).");
        System.out.println("Wywoluje getBean() teraz:");
        rawFactory.getBean(LoggingBean.class);
        System.out.println("-> print z konstruktora pojawil sie DOPIERO PRZY getBean() - to LENIWE (lazy) tworzenie.");

        System.out.println("\n--- AnnotationConfigApplicationContext ---");
        System.out.println("Tworze kontekst...");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("Kontekst UTWORZONY - print z konstruktora LoggingBean JUZ SIE POJAWIL WYZEJ, przed jakimkolwiek getBean()!");
            context.getBean(LoggingBean.class);
            System.out.println("-> to getBean() TYLKO zwrocilo JUZ istniejacy singleton - ApplicationContext tworzy WSZYSTKIE singletony CHETNIE, w metodzie refresh() przy starcie.");
        }
    }

    record OrderPlacedEvent(String orderId) {
        // Zwykly rekord (POJO), BEZ dziedziczenia po ApplicationEvent - mozliwe od Spring 4.2 (2015).
        // PRZED 4.2 KAZDE zdarzenie MUSIALO dziedziczyc po ApplicationEvent - kolejny przyklad
        // "stare vs nowe" z Lesson02.
    }

    static class OrderEventListener {
        // @EventListener (w odroznieniu od interfejsu ApplicationListener<E extends ApplicationEvent>)
        // przyjmuje DOWOLNY typ POJO wprost - Spring opakowuje go po cichu w PayloadApplicationEvent
        // i sam go rozpakowuje przy dostarczeniu do tej metody.
        @EventListener
        void onOrderPlaced(OrderPlacedEvent event) {
            System.out.println("  [Listener] Otrzymano zdarzenie: zamowienie " + event.orderId());
        }
    }

    // CELOWO BEZ @ComponentScan - gdyby skanowal caly pakiet, znalazlby TEZ 'AppConfig' z
    // poprzedniego demo (@Configuration jest SAMO w sobie @Component!) i uruchomilby JEJ
    // metode @Bean ponownie - jawna rejestracja ponizej unika tej "przypadkowej" kolizji.
    @Configuration
    static class EventAndMessageConfig {
        @Bean
        OrderEventListener orderEventListener() {
            return new OrderEventListener();
        }

        @Bean
        StaticMessageSource messageSource() {
            StaticMessageSource source = new StaticMessageSource();
            source.addMessage("greeting", Locale.forLanguageTag("pl"), "Witaj, {0}!");
            source.addMessage("greeting", Locale.US, "Hello, {0}!");
            return source;
        }
    }

    private static void demonstrateApplicationContextExtraFeatures() {
        System.out.println("\n=== DODATKOWE MOZLIWOSCI ApplicationContext (KTORYCH BeanFactory NIE MA) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventAndMessageConfig.class)) {

            System.out.println("--- 1. Publikowanie zdarzen (ApplicationEventPublisher, pelna lekcja: Lesson20) ---");
            context.publishEvent(new OrderPlacedEvent("ORD-123"));

            System.out.println("\n--- 2. Internacjonalizacja (MessageSource) ---");
            String polish = context.getMessage("greeting", new Object[]{"Ala"}, Locale.forLanguageTag("pl"));
            String english = context.getMessage("greeting", new Object[]{"Ala"}, Locale.US);
            System.out.println("  PL: " + polish);
            System.out.println("  EN: " + english);

            System.out.println("\n--- 3. Ladowanie zasobow (ResourceLoader) ---");
            Resource resource = context.getResource("classpath:application.properties");
            System.out.println("  context.getResource(\"classpath:application.properties\") -> istnieje: " + resource.exists());

            System.out.println("\n-> WSZYSTKIE 3 mozliwosci sa WBUDOWANE w KAZDY ApplicationContext - goly BeanFactory nie ma ZADNEJ z nich.");
        }
    }
}
