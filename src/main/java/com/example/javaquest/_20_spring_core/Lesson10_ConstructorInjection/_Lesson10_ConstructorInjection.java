package com.example.javaquest._20_spring_core.Lesson10_ConstructorInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

public class _Lesson10_ConstructorInjection {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: CONSTRUCTOR INJECTION - REKOMENDOWANY STYL ===");

        /*
         * ============================================================
         * 📦 DLACZEGO KONSTRUKTOR TO DOMYSLNY WYBOR
         * ============================================================
         * Lesson04 pokazal 3 style DI. Ta lekcja idzie GLEBIEJ w
         * KONSTRUKTOR - REKOMENDOWANY przez oficjalna dokumentacje
         * Springa styl. Powody: (1) pola MOGA byc `final` - obiekt
         * NIEZMIENNY po utworzeniu, (2) obiekt NIGDY nie istnieje w
         * NIEKOMPLETNYM stanie - wszystkie zaleznosci sa DOSTEPNE od
         * PIERWSZEJ linii kodu klasy, (3) latwe testowanie BEZ Springa -
         * `new MojaKlasa(fakeZaleznosc)` dziala od razu, (4) kontener
         * WYKRYWA cykliczne zaleznosci NATYCHMIAST (Lesson13), zamiast
         * ukrywac je (jak przy polu/setterze).
         */
        System.out.println("Konstruktor = REKOMENDOWANY styl DI: 'final', brak niekompletnego stanu, latwe testowanie, natychmiastowe wykrywanie cykli.");

        demonstrateImplicitInjectionSinceSpring43();
        demonstrateExplicitAutowiredStillWorksAndIsNeededForMultipleConstructors();
        demonstrateImmutabilityAndTestabilityWithoutSpring();

        /*
         * ============================================================
         * 🔹 KIEDY @Autowired NA KONSTRUKTORZE JEST WYMAGANE
         * ============================================================
         * Lesson02 zapowiedzial: od Spring 4.3 `@Autowired` jest
         * OPCJONALNE przy DOKLADNIE JEDNYM konstruktorze. Ale gdy klasa
         * ma WIECEJ NIZ JEDEN konstruktor, MUSISZ oznaczyc TEN, ktory
         * Spring ma uzyc - inaczej `BeanCreationException` ("no default
         * constructor found" lub podobny, w zaleznosci od sytuacji).
         */
        System.out.println("\nPrzy WIELU konstruktorach @Autowired jest WYMAGANE na TYM, ktory Spring ma uzyc - inaczej blad przy starcie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Konstruktor to REKOMENDOWANY, domyslny styl DI w Springu.
         * - 1 konstruktor + brak @Autowired = dziala od Spring 4.3.
         * - Wiele konstruktorow = @Autowired WYMAGANE na wlasciwym.
         * - Najwieksza zaleta: obiekt mozna testowac BEZ Springa,
         *   przekazujac zaleznosci recznie przez `new`.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    interface PriceCalculator {
        double calculate(int quantity, double unitPrice);
    }

    @Component
    static class SimplePriceCalculator implements PriceCalculator {
        @Override
        public double calculate(int quantity, double unitPrice) {
            return quantity * unitPrice;
        }
    }

    @Component
    static class ImplicitInjectionOrderService {
        private final PriceCalculator calculator;

        // BEZ @Autowired - dziala, bo to JEDYNY konstruktor (Spring 4.3+).
        ImplicitInjectionOrderService(PriceCalculator calculator) {
            this.calculator = calculator;
        }

        double total(int quantity, double unitPrice) {
            return calculator.calculate(quantity, unitPrice);
        }
    }

    // Filtr WYKLUCZAJACY inne klasy @Configuration z tego pliku (jak MultiConstructorConfig
    // ponizej) - inaczej @ComponentScan znalazlby JE TEZ (@Configuration jest @Component!) i
    // uruchomil ich metody @Bean, dajac DUPLIKAT beana PriceCalculator (patrz notatka w CLAUDE.md
    // o tej samej pulapce w Lesson07/08).
    @Configuration
    @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
    static class AppConfig {
    }

    private static void demonstrateImplicitInjectionSinceSpring43() {
        System.out.println("\n=== 1 KONSTRUKTOR, BEZ @Autowired (Spring 4.3+) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            ImplicitInjectionOrderService service = context.getBean(ImplicitInjectionOrderService.class);
            System.out.println("Suma: " + service.total(3, 19.99));
            System.out.println("-> zadziala BEZ jakiejkolwiek adnotacji na konstruktorze - Spring SAM rozpoznaje JEDYNY konstruktor.");
        }
    }

    static class ExplicitMultiConstructorService {
        private final PriceCalculator calculator;
        private final String label;

        // Konstruktor UZYWANY przez Springa - MUSI byc oznaczony, bo jest ich WIECEJ NIZ 1.
        @Autowired
        ExplicitMultiConstructorService(PriceCalculator calculator) {
            this.calculator = calculator;
            this.label = "domyslna etykieta";
        }

        // Konstruktor pomocniczy dla RECZNEGO uzycia (np. w testach) - Spring go IGNORUJE.
        ExplicitMultiConstructorService(PriceCalculator calculator, String label) {
            this.calculator = calculator;
            this.label = label;
        }

        String describe() {
            return "[" + label + "] suma przykladowa: " + calculator.calculate(2, 9.99);
        }
    }

    @Configuration
    static class MultiConstructorConfig {
        @org.springframework.context.annotation.Bean
        SimplePriceCalculator simplePriceCalculator() {
            return new SimplePriceCalculator();
        }

        @org.springframework.context.annotation.Bean
        ExplicitMultiConstructorService explicitMultiConstructorService(PriceCalculator calculator) {
            return new ExplicitMultiConstructorService(calculator);
        }
    }

    private static void demonstrateExplicitAutowiredStillWorksAndIsNeededForMultipleConstructors() {
        System.out.println("\n=== WIELE KONSTRUKTOROW: @Autowired WYMAGANE NA WLASCIWYM ===");

        ExplicitMultiConstructorService manual = new ExplicitMultiConstructorService(new SimplePriceCalculator(), "recznie utworzony");
        System.out.println("Recznie (bez Springa, przez 'new'): " + manual.describe());

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MultiConstructorConfig.class)) {
            ExplicitMultiConstructorService fromSpring = context.getBean(ExplicitMultiConstructorService.class);
            System.out.println("Przez Springa (via @Bean, @Autowired na 1-argumentowym konstruktorze): " + fromSpring.describe());
            System.out.println("-> Spring UZYL konstruktora oznaczonego @Autowired - DRUGI konstruktor pozostal dostepny TYLKO do recznego uzycia (np. w testach).");
        }
    }

    private static void demonstrateImmutabilityAndTestabilityWithoutSpring() {
        System.out.println("\n=== NIEZMIENNOSC I TESTOWALNOSC BEZ SPRINGA ===");

        // Testowa, "falszywa" implementacja - BEZ zadnej adnotacji Springa, BEZ kontenera.
        PriceCalculator fakeCalculator = (quantity, unitPrice) -> 999.0;
        ImplicitInjectionOrderService testableService = new ImplicitInjectionOrderService(fakeCalculator);

        System.out.println("Utworzono obiekt z 'fake' zaleznoscia, BEZ jakiegokolwiek ApplicationContext:");
        System.out.println("  wynik testowy: " + testableService.total(1, 1.0) + " (oczekiwane: 999.0, z fake implementacji)");
        System.out.println("-> DOKLADNIE tak wygladaja testy jednostkowe klas z constructor injection - ZERO potrzeby uruchamiania calego Springa.");
    }
}
