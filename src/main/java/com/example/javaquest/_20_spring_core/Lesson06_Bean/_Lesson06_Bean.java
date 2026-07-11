package com.example.javaquest._20_spring_core.Lesson06_Bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class _Lesson06_Bean {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: CZYM JEST 'BEAN'? ===");

        /*
         * ============================================================
         * 📦 BEAN = OBIEKT ZARZĄDZANY PRZEZ KONTENER
         * ============================================================
         * "Bean" to NIE jakiś specjalny typ Javy — to ZWYKŁY obiekt
         * (POJO — Plain Old Java Object), z JEDNĄ różnicą: kontener
         * Springa GO STWORZYŁ i ZARZĄDZA jego cyklem życia. Ta sama
         * klasa może istnieć jako "zwykły obiekt" (utworzony przez
         * `new`) i jako "bean" (utworzony przez kontener) — RÓŻNICA
         * jest w TYM, KTO go stworzył i KTO nim zarządza, NIE w samej
         * klasie. Demo niżej pokazuje to WPROST na tej samej klasie.
         */
        System.out.println("Bean = zwykly obiekt Javy (POJO), ktorym zarzadza KONTENER - roznica jest w TYM KTO go stworzyl, nie w samej klasie.");

        demonstratePlainObjectVsManagedBean();
        demonstrateBeanDefinitionMetadata();
        demonstrateBeanNamingConventions();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bean = obiekt, ktorego cyklem zycia (utworzenie, wstrzykniecie
         *   zaleznosci, ewentualne zniszczenie) zarzadza kontener - NIE
         *   specjalny typ jezyka.
         * - Kontener przechowuje METADANE o kazdym beanie (BeanDefinition) -
         *   typ, zakres (scope, Lesson14), nazwe - ZANIM w ogole stworzy
         *   instancje.
         * - Domyslna nazwa beana to (dla klasy NAJWYZSZEGO poziomu) jej
         *   nazwa z MALA pierwsza litera - dla klas ZAGNIEZDZONYCH (jak w
         *   tym pliku) dochodzi prefiks zewnetrznej klasy - mozna to
         *   zawsze NADPISAC jawnie przez `@Component("nazwa")`.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    static class Counter {
        private int value = 0;

        void increment() {
            value++;
        }

        int getValue() {
            return value;
        }
    }

    @Component
    static class ManagedCounter extends Counter {
    }

    @Configuration
    @ComponentScan
    static class AppConfig {
    }

    private static void demonstratePlainObjectVsManagedBean() {
        System.out.println("\n=== TA SAMA KLASA: 'ZWYKLY OBIEKT' vs 'BEAN' ===");

        Counter plainA = new Counter();
        Counter plainB = new Counter();
        plainA.increment();
        System.out.println("2 zwykle obiekty (przez 'new') - CALKOWICIE niezalezne od siebie:");
        System.out.println("  plainA.getValue()=" + plainA.getValue() + ", plainB.getValue()=" + plainB.getValue() + " (rozne instancje, rozny stan)");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            ManagedCounter beanA = context.getBean(ManagedCounter.class);
            ManagedCounter beanB = context.getBean(ManagedCounter.class);
            beanA.increment();

            System.out.println("\n2 pobrania TEGO SAMEGO beana z kontenera (domyslny zakres 'singleton', patrz Lesson14):");
            System.out.println("  beanA.getValue()=" + beanA.getValue() + ", beanB.getValue()=" + beanB.getValue() + " (TA SAMA instancja: " + (beanA == beanB) + ")");
            System.out.println("-> kontener zwrocil DOKLADNIE TEN SAM obiekt za drugim razem - 'new' NIGDY by tego nie zrobil.");
        }
    }

    private static void demonstrateBeanDefinitionMetadata() {
        System.out.println("\n=== METADANE BEANA (BeanDefinition) - ZANIM POWSTANIE INSTANCJA ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // UWAGA (pulapka warta zapamietania): dla ZAGNIEZDZONEJ klasy statycznej (jak
            // ManagedCounter w TYM pliku) domyslna nazwa beana NIE jest prostym "managedCounter" -
            // to "outer.Inner" (Spring zamienia '$' na '.') zdekapitalizowane TYLKO na pierwszym
            // znaku calego lancucha - dlatego znajdujemy nazwe DYNAMICZNIE, zamiast zgadywac.
            String beanName = context.getBeanNamesForType(ManagedCounter.class)[0];
            BeanDefinition definition = context.getBeanFactory().getBeanDefinition(beanName);

            System.out.println("Rzeczywista nazwa beana (znaleziona dynamicznie): '" + beanName + "'");
            System.out.println("Klasa: " + definition.getBeanClassName());
            System.out.println("Zakres (scope): " + (definition.getScope().isEmpty() ? "singleton (domyslny)" : definition.getScope()));
            System.out.println("-> kontener przechowuje te METADANE osobno od SAMEJ instancji - dlatego wie, JAK stworzyc bean, ZANIM go faktycznie stworzy.");
            System.out.println("-> PULAPKA: dla klasy ZAGNIEZDZONEJ nazwa beana to NIE prosta 'managedCounter' (tak jak dla klasy najwyzszego poziomu),");
            System.out.println("   tylko '" + beanName + "' - Spring uzywa PELNEJ nazwy z zewnetrzna klasa. W REALNYM projekcie (bez zagniezdzania) tego nie zobaczysz.");
        }
    }

    @Component
    static class DefaultNamedGreeter {
    }

    @Component("customGreeterName")
    static class CustomNamedGreeter {
    }

    private static void demonstrateBeanNamingConventions() {
        System.out.println("\n=== NAZEWNICTWO BEANOW: DOMYSLNE vs JAWNE ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            String defaultName = context.getBeanNamesForType(DefaultNamedGreeter.class)[0];
            System.out.println("Klasa 'DefaultNamedGreeter' bez jawnej nazwy -> domyslna nazwa beana: '" + defaultName + "'");
            System.out.println("Klasa 'CustomNamedGreeter' z @Component(\"customGreeterName\") -> nazwa beana: '"
                    + (context.containsBean("customGreeterName") ? "customGreeterName" : "??? nie znaleziono") + "' (JAWNA nazwa NIE zalezy od zagniezdzenia)");
        }
    }
}
