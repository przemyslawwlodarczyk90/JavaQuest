package com.example.javaquest._20_spring_core.Lesson11_FieldInjectionWhyAvoid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

public class _Lesson11_FieldInjectionWhyAvoid {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: DLACZEGO UNIKAC WSTRZYKIWANIA DO POLA ===");

        /*
         * ============================================================
         * 📦 NAJKROTSZY ZAPIS, NAJWIECEJ UKRYTYCH WAD
         * ============================================================
         * `@Autowired private X x;` to NAJMNIEJ kodu do napisania -
         * dlatego jest WSZECHOBECNE w starych tutorialach i legacy
         * projektach. Ale ta "wygoda" ma CENE, ktora ujawnia sie
         * DOPIERO pozniej: (1) pole NIE MOZE byc `final`, (2) zaleznosc
         * jest UKRYTA (nie widac jej w zadnej sygnaturze), (3) obiekt
         * DZIALA (bez wyjatku) nawet BEZ Springa - do momentu, gdy
         * UZYJESZ pola, (4) MASKUJE cykliczne zaleznosci zamiast je
         * wykrywac. Dzisiaj zobaczysz KAZDY z tych problemow NA ZYWO.
         */
        System.out.println("Field injection = najkrotszy zapis, ale ukryte zaleznosci + brak 'final' + maskowanie bledow projektowych.");

        explainWhyFieldCannotBeFinal();
        demonstrateHiddenDependencyInConstructorSignature();
        demonstrateNullPointerWhenTestedWithoutContainer();
        demonstrateFieldInjectionSilentlyAllowsCircularDependency();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Pole `@Autowired` NIE MOZE byc `final` - Spring NAJPIERW
         *   tworzy obiekt (konstruktorem BEZARGUMENTOWYM), DOPIERO POTEM
         *   ustawia pole refleksja - `final` wymaga przypisania w
         *   konstruktorze, co tu nie zachodzi.
         * - Zaleznosc jest UKRYTA - sygnatura konstruktora (albo jej
         *   brak) NIC nie mowi o tym, czego klasa POTRZEBUJE.
         * - Obiekt utworzony przez `new` (np. w tescie) "dziala" do
         *   PIERWSZEGO uzycia niezainicjalizowanego pola -
         *   `NullPointerException` w NIESPODZIEWANYM miejscu.
         * - Field injection MASKUJE cykliczne zaleznosci (dziala "po
         *   cichu"), podczas gdy constructor injection (Lesson13)
         *   wykrywa je NATYCHMIAST, GLOSNO, przy starcie.
         * - WNIOSEK: field injection MOZNA spotkac w legacy kodzie -
         *   ROZPOZNAJ go i wiedz, DLACZEGO warto go migrowac na
         *   constructor injection (Lesson10) przy okazji.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void explainWhyFieldCannotBeFinal() {
        System.out.println("\n=== DLACZEGO POLE @Autowired NIE MOZE BYC 'final' ===");

        System.out.println("Kolejnosc dzialan Springa dla field injection:");
        System.out.println("  1. Spring wywoluje BEZARGUMENTOWY konstruktor klasy (obiekt istnieje, pole JESZCZE puste)");
        System.out.println("  2. Spring, przez REFLEKSJE (setAccessible(true)), USTAWIA pole PO fakcie");
        System.out.println("Java wymaga, zeby pole 'final' bylo przypisane W KONSTRUKTORZE (lub przy deklaracji) -");
        System.out.println("skoro krok 2 zachodzi PO konstruktorze, DEKLARACJA 'private final X x;' z @Autowired NIE SKOMPILUJE SIE.");
        System.out.println("-> to WLASNIE dlatego constructor injection (Lesson10) jest jedynym stylem POZWALAJACYM na 'final'.");
    }

    interface Notifier {
        void notify(String message);
    }

    @Component
    static class ConsoleNotifier implements Notifier {
        @Override
        public void notify(String message) {
            System.out.println("  [Powiadomienie] " + message);
        }
    }

    // Field injection - zaleznosc UKRYTA, brak jej w konstruktorze.
    @Component
    static class FieldInjectedOrderService {
        @Autowired
        private Notifier notifier;

        void placeOrder(String product) {
            notifier.notify("Zamowienie: " + product);
        }
    }

    // Constructor injection - zaleznosc WIDOCZNA wprost w sygnaturze.
    @Component
    static class ConstructorInjectedOrderService {
        private final Notifier notifier;

        ConstructorInjectedOrderService(Notifier notifier) {
            this.notifier = notifier;
        }

        void placeOrder(String product) {
            notifier.notify("Zamowienie: " + product);
        }
    }

    private static void demonstrateHiddenDependencyInConstructorSignature() {
        System.out.println("\n=== ZALEZNOSC UKRYTA (POLE) vs WIDOCZNA (KONSTRUKTOR) - DOWOD REFLEKSJA ===");

        Constructor<?>[] fieldInjectedConstructors = FieldInjectedOrderService.class.getDeclaredConstructors();
        Constructor<?>[] constructorInjectedConstructors = ConstructorInjectedOrderService.class.getDeclaredConstructors();

        System.out.println("FieldInjectedOrderService - konstruktor(y) widoczne w API klasy:");
        for (Constructor<?> c : fieldInjectedConstructors) {
            System.out.println("  " + c + " (parametrow: " + c.getParameterCount() + ") -> NIC nie mowi o zaleznosci od Notifier!");
        }

        System.out.println("ConstructorInjectedOrderService - konstruktor(y) widoczne w API klasy:");
        for (Constructor<?> c : constructorInjectedConstructors) {
            System.out.println("  " + c + " (parametrow: " + c.getParameterCount() + ") -> JASNO widac zaleznosc od Notifier.");
        }
        System.out.println("-> ktokolwiek czyta/uzywa tej klasy (np. IDE podpowiadajace konstruktor) widzi PRAWDE tylko przy constructor injection.");
    }

    private static void demonstrateNullPointerWhenTestedWithoutContainer() {
        System.out.println("\n=== TEST 'BEZ SPRINGA' - NullPointerException W NIESPODZIEWANYM MIEJSCU ===");

        FieldInjectedOrderService testedWithoutSpring = new FieldInjectedOrderService();
        System.out.println("Obiekt UTWORZONY przez 'new' - BEZ zadnego bledu (konstruktor bezargumentowy nie wymaga niczego)...");
        try {
            testedWithoutSpring.placeOrder("Laptop");
        } catch (NullPointerException e) {
            System.out.println("...ale UZYCIE metody rzuca NullPointerException - dopiero TERAZ, DALEKO od miejsca utworzenia obiektu!");
        }
        System.out.println("-> w tescie constructor injection (Lesson10) TEN sam blad byl niemozliwy - konstruktor WYMUSZAL podanie zaleznosci OD RAZU.");
    }

    @Component
    static class ServiceA {
        @Autowired
        private ServiceB serviceB;

        String describe() {
            return "ServiceA -> " + (serviceB != null ? "MA polaczenie do ServiceB" : "BRAK polaczenia");
        }
    }

    @Component
    static class ServiceB {
        @Autowired
        private ServiceA serviceA;

        String describe() {
            return "ServiceB -> " + (serviceA != null ? "MA polaczenie do ServiceA" : "BRAK polaczenia");
        }
    }

    @Configuration
    @ComponentScan
    static class CircularConfig {
    }

    private static void demonstrateFieldInjectionSilentlyAllowsCircularDependency() {
        System.out.println("\n=== FIELD INJECTION MASKUJE CYKLICZNA ZALEZNOSC (DZIALA 'PO CICHU') ===");

        System.out.println("ServiceA zalezy od ServiceB, ServiceB zalezy od ServiceA - klasyczny cykl, prawdopodobnie BLAD projektowy...");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CircularConfig.class)) {
            ServiceA a = context.getBean(ServiceA.class);
            ServiceB b = context.getBean(ServiceB.class);
            System.out.println(a.describe());
            System.out.println(b.describe());
            System.out.println("-> ZADZIALALO - field injection POZWOLIL na cykl (Spring tworzy oba obiekty konstruktorem, DOPIERO POTEM wypelnia pola).");
            System.out.println("   To NIE jest pochwala - to OSTRZEZENIE: cykl to zwykle ZLY znak w projekcie, a field injection go UKRYWA zamiast wykryc.");
            System.out.println("   Lesson13_CircularDependencies pokaze, ze TEN SAM cykl przez constructor injection zakonczylby sie GLOSNYM bledem od razu.");
        }
    }
}
