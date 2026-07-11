package com.example.javaquest._20_spring_core.Lesson14_BeanScopes;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

public class _Lesson14_BeanScopes {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: ZAKRESY BEANOW (BEAN SCOPES) ===");

        /*
         * ============================================================
         * 📦 SINGLETON (DOMYSLNY) vs PROTOTYPE
         * ============================================================
         * Lesson06 pokazal, ze `getBean()` DWA razy zwraca TA SAMA
         * instancje - to zakres `singleton` (DOMYSLNY, JEDNA instancja
         * na CALY kontener). `prototype` to PRZECIWIENSTWO - KAZDE
         * `getBean()` (lub KAZDE wstrzykniecie) tworzy NOWA instancje.
         * Dzisiaj: OBA zakresy NA ZYWO, plus KLASYCZNA pulapka -
         * prototype wstrzykniety do singletona.
         */
        System.out.println("singleton (domyslny) = 1 instancja na caly kontener. prototype = NOWA instancja przy KAZDYM zadaniu.");

        demonstrateSingletonScope();
        demonstratePrototypeScope();
        demonstratePrototypeInjectedIntoSingletonTrap();
        demonstrateObjectProviderFixesTheTrap();

        /*
         * ============================================================
         * 🔹 INNE ZAKRESY (WYMAGAJA `_22_spring_web`)
         * ============================================================
         * - `request` — 1 instancja NA JEDNO zadanie HTTP.
         * - `session` — 1 instancja NA SESJE uzytkownika.
         * - `application` — 1 instancja na CALY `ServletContext`
         *   (podobne do singletona, ale zwiazane z aplikacja webowa).
         * Te 3 zakresy WYMAGAJA dzialajacego Spring MVC - zobaczysz je
         * w praktyce dopiero w `_22_spring_web`, gdy pojawi sie
         * kontekst zadania HTTP.
         */
        System.out.println("\nrequest/session/application - zakresy web-owe, wymagaja dzialajacego Spring MVC - pelne demo w _22_spring_web.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `singleton` (domyslny) = 1 instancja na kontener.
         * - `prototype` = NOWA instancja przy KAZDYM `getBean()`.
         * - PULAPKA: prototype WSTRZYKNIETY (przez konstruktor/pole) do
         *   singletona jest tworzony TYLKO RAZ (przy tworzeniu
         *   singletona) - "zamraza sie", tracac sens zakresu prototype.
         * - ROZWIAZANIE: `ObjectProvider<T>` (lub `@Lookup`) - pobiera
         *   SWIEZA instancje przy KAZDYM wywolaniu `getObject()`, NIE
         *   przy wstrzyknieciu.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    static class SingletonCounter {
        private int value = 0;

        int incrementAndGet() {
            return ++value;
        }
    }

    @Configuration
    static class SingletonConfig {
        @Bean
        SingletonCounter singletonCounter() {
            return new SingletonCounter();
        }
    }

    private static void demonstrateSingletonScope() {
        System.out.println("\n=== SINGLETON (DOMYSLNY): 1 INSTANCJA NA KONTENER ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SingletonConfig.class)) {
            SingletonCounter first = context.getBean(SingletonCounter.class);
            SingletonCounter second = context.getBean(SingletonCounter.class);

            first.incrementAndGet();
            System.out.println("first == second: " + (first == second) + " (TA SAMA instancja)");
            System.out.println("Wartosc widziana przez 'second' PO incrementAndGet() na 'first': " + second.incrementAndGet() + " (widzi ZMIANY dokonane przez 'first')");
        }
    }

    static class PrototypeCounter {
        private static int instancesCreated = 0;
        private final int instanceId;

        PrototypeCounter() {
            instanceId = ++instancesCreated;
        }

        int getInstanceId() {
            return instanceId;
        }
    }

    @Configuration
    static class PrototypeConfig {
        @Bean
        @Scope("prototype")
        PrototypeCounter prototypeCounter() {
            return new PrototypeCounter();
        }
    }

    private static void demonstratePrototypeScope() {
        System.out.println("\n=== PROTOTYPE: NOWA INSTANCJA PRZY KAZDYM getBean() ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeConfig.class)) {
            PrototypeCounter first = context.getBean(PrototypeCounter.class);
            PrototypeCounter second = context.getBean(PrototypeCounter.class);
            PrototypeCounter third = context.getBean(PrototypeCounter.class);

            System.out.println("ID instancji: " + first.getInstanceId() + ", " + second.getInstanceId() + ", " + third.getInstanceId() + " (3 RÓZNE instancje!)");
            System.out.println("first == second: " + (first == second) + " (ZAWSZE false dla prototype)");
        }
    }

    // UWAGA: ta klasa jest SINGLETONEM (domyslnie), ale "wstrzykuje" prototype BEZPOSREDNIO -
    // klasyczna, czesto niezauwazona pulapka.
    static class BrokenSingletonHoldingPrototype {
        private final PrototypeCounter capturedAtConstructionTime;

        BrokenSingletonHoldingPrototype(PrototypeCounter capturedAtConstructionTime) {
            this.capturedAtConstructionTime = capturedAtConstructionTime;
        }

        int useCounter() {
            return capturedAtConstructionTime.getInstanceId();
        }
    }

    @Configuration
    static class TrapConfig {
        @Bean
        @Scope("prototype")
        PrototypeCounter prototypeCounter() {
            return new PrototypeCounter();
        }

        @Bean
        BrokenSingletonHoldingPrototype brokenSingletonHoldingPrototype(PrototypeCounter prototypeCounter) {
            return new BrokenSingletonHoldingPrototype(prototypeCounter);
        }
    }

    private static void demonstratePrototypeInjectedIntoSingletonTrap() {
        System.out.println("\n=== PULAPKA: PROTOTYPE WSTRZYKNIETY DO SINGLETONA 'ZAMRAZA SIE' ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TrapConfig.class)) {
            BrokenSingletonHoldingPrototype service = context.getBean(BrokenSingletonHoldingPrototype.class);

            System.out.println("Wywoluje useCounter() TRZYKROTNIE na TYM SAMYM (singleton) serwisie:");
            System.out.println("  " + service.useCounter());
            System.out.println("  " + service.useCounter());
            System.out.println("  " + service.useCounter());
            System.out.println("-> ZAWSZE TO SAMO ID! Prototype zostal wstrzykniety RAZ, przy tworzeniu singletona - potem juz sie NIE ODNAWIA.");
            System.out.println("   To NAJCZESTSZA pulapka zakresu prototype - kursant OCZEKUJE swiezej instancji przy KAZDYM uzyciu, a dostaje ZAMROZONA.");
        }
    }

    static class FixedServiceUsingObjectProvider {
        private final ObjectProvider<PrototypeCounter> provider;

        FixedServiceUsingObjectProvider(ObjectProvider<PrototypeCounter> provider) {
            this.provider = provider;
        }

        int useFreshCounter() {
            // getObject() pobiera SWIEZA instancje TERAZ, NIE przy wstrzykiwaniu konstruktora.
            return provider.getObject().getInstanceId();
        }
    }

    @Configuration
    static class FixedConfig {
        @Bean
        @Scope("prototype")
        PrototypeCounter prototypeCounter() {
            return new PrototypeCounter();
        }

        @Bean
        FixedServiceUsingObjectProvider fixedServiceUsingObjectProvider(ObjectProvider<PrototypeCounter> provider) {
            return new FixedServiceUsingObjectProvider(provider);
        }
    }

    private static void demonstrateObjectProviderFixesTheTrap() {
        System.out.println("\n=== ROZWIAZANIE: ObjectProvider<T> POBIERA SWIEZA INSTANCJE PRZY KAZDYM WYWOLANIU ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FixedConfig.class)) {
            FixedServiceUsingObjectProvider service = context.getBean(FixedServiceUsingObjectProvider.class);

            System.out.println("Wywoluje useFreshCounter() TRZYKROTNIE na TYM SAMYM (singleton) serwisie:");
            System.out.println("  " + service.useFreshCounter());
            System.out.println("  " + service.useFreshCounter());
            System.out.println("  " + service.useFreshCounter());
            System.out.println("-> TERAZ kazde wywolanie daje NOWE ID - ObjectProvider.getObject() prosi kontener o SWIEZY prototype ZA KAZDYM razem, nie tylko raz przy starcie.");
        }
    }
}
