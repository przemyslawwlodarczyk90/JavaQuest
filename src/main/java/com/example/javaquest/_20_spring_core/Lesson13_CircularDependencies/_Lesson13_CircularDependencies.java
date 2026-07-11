package com.example.javaquest._20_spring_core.Lesson13_CircularDependencies;

import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

public class _Lesson13_CircularDependencies {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: CYKLICZNE ZALEZNOSCI ===");

        /*
         * ============================================================
         * 📦 CYKL = ZWYKLE BLAD PROJEKTOWY, NIE "FUNKCJA"
         * ============================================================
         * Lesson11 pokazal, ze field injection POZWALA na cykl A<->B
         * (dziala "po cichu"). Ta lekcja pokazuje DRUGA strone medalu:
         * constructor injection (Lesson10, REKOMENDOWANY styl) ZAWSZE
         * ODRZUCA taki cykl - GLOSNO, NATYCHMIAST, przy starcie
         * kontekstu. To NIE jest "wada" constructor injection - to
         * ZALETA: cykl to (niemal zawsze) sygnal zlego podzialu
         * odpowiedzialnosci miedzy klasami, a constructor injection
         * WYMUSZA naprawienie GO od razu, zamiast pozwolic mu "dzialac"
         * ukryty w kodzie miesiacami.
         */
        System.out.println("Constructor injection ZAWSZE odrzuca cykl A<->B - GLOSNO i NATYCHMIAST, w odroznieniu od field injection (Lesson11).");

        demonstrateConstructorCycleAlwaysFails();
        demonstrateDisablingCircularReferencesMakesFieldCycleFailToo();

        /*
         * ============================================================
         * 🔹 WAZNA ROZNICA WERSJI (Z Lesson02)
         * ============================================================
         * Spring Framework (niezaleznie od wersji) domyslnie POZWALA
         * "goleu" `AnnotationConfigApplicationContext` na rozwiazywanie
         * cykli field/setter injection (przez wczesna referencje do
         * obiektu w budowie). Spring Boot **2.6** (2021) zmienil
         * DOMYSLNE zachowanie NA POZIOMIE `SpringApplication` -
         * `spring.main.allow-circular-references` jest TAM domyslnie
         * `false`. Oznacza to: aplikacja uruchomiona przez
         * `SpringApplication.run(...)` (czyli KAZDA typowa aplikacja
         * Boot, `_21_spring_boot`+) ODRZUCI TAKZE field injection cykl,
         * chyba ze JAWNIE wlaczysz stara flage. Demo wyzej pokazuje
         * DOKLADNIE TEN mechanizm (recznie, na niskim poziomie, bo ten
         * rozdzial jeszcze nie uzywa `SpringApplication`).
         */
        System.out.println("\nSpring Boot 2.6+ (SpringApplication) domyslnie WYLACZA circular references NAWET dla field injection - inaczej niz goly Spring Framework.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Cykl A<->B to zazwyczaj sygnal ZLEGO podzialu
         *   odpowiedzialnosci - NIE "normalna" sytuacja do obejscia.
         * - Constructor injection WYKRYWA cykl NATYCHMIAST
         *   (`BeanCurrentlyInCreationException`) - to ZALETA, nie wada.
         * - Field/setter injection HISTORYCZNIE "rozwiazywal" cykl po
         *   cichu (wczesna referencja) - Spring Boot 2.6+ domyslnie TO
         *   WYLACZA.
         * - Prawdziwe rozwiazanie cyklu: PRZEPROJEKTUJ (wydziel wspolna
         *   zaleznosc do 3. klasy, uzyj zdarzen z `Lesson20_
         *   ApplicationEvents` zamiast bezposredniego wywolania) -
         *   NIGDY nie "obchodz" cyklu, jesli mozesz go WYELIMINOWAC.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    static class ConstructorCycleA {
        private final ConstructorCycleB b;

        ConstructorCycleA(ConstructorCycleB b) {
            this.b = b;
        }
    }

    static class ConstructorCycleB {
        private final ConstructorCycleA a;

        ConstructorCycleB(ConstructorCycleA a) {
            this.a = a;
        }
    }

    // UWAGA: rejestracja WYLACZNIE przez jawne @Bean, CELOWO bez @ComponentScan - ten plik ma
    // WIELE demo z WLASNYMI klasami @Component-podobnymi; @ComponentScan (nawet z filtrem
    // wykluczajacym @Configuration) zgarnalby TEZ klasy z INNEGO demo w tym samym pliku (sa w tym
    // samym pakiecie!), fałszujac obserwowany wynik - patrz notatka w CLAUDE.md.
    @Configuration
    static class ConstructorCycleConfig {
        @org.springframework.context.annotation.Bean
        ConstructorCycleA constructorCycleA(ConstructorCycleB b) {
            return new ConstructorCycleA(b);
        }

        @org.springframework.context.annotation.Bean
        ConstructorCycleB constructorCycleB(ConstructorCycleA a) {
            return new ConstructorCycleB(a);
        }
    }

    private static void demonstrateConstructorCycleAlwaysFails() {
        System.out.println("\n=== CONSTRUCTOR INJECTION: CYKL ZAWSZE ZAWODZI (GLOSNO, NATYCHMIAST) ===");

        System.out.println("ConstructorCycleA potrzebuje ConstructorCycleB W KONSTRUKTORZE, ConstructorCycleB potrzebuje ConstructorCycleA W KONSTRUKTORZE...");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConstructorCycleConfig.class)) {
            System.out.println("BLAD: kontekst wystartowal, mimo ze POWINIEN byl sie nie udac!");
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            System.out.println("Kontekst NIE wystartowal - przyczyna: " + root.getClass().getSimpleName());
            System.out.println("-> " + (root instanceof BeanCurrentlyInCreationException ? "DOKLADNIE ten wyjatek, ktorego sie spodziewalismy - LOGICZNA niemoznosc: zeby stworzyc A, trzeba NAJPIERW miec B, a zeby stworzyc B, trzeba NAJPIERW miec A." : "inny wyjatek."));
        }
    }

    static class FieldCycleA {
        @Autowired
        private FieldCycleB b;
    }

    static class FieldCycleB {
        @Autowired
        private FieldCycleA a;
    }

    // Jawne @Bean (bez parametrow - pola wypelnia PO fakcie AutowiredAnnotationBeanPostProcessor,
    // dokladnie tak samo jak przy component-scanningu) - znowu CELOWO bez @ComponentScan, zeby NIE
    // zgarnac przy okazji ConstructorCycleA/B z demo wyzej (ten sam plik, ten sam pakiet!).
    @Configuration
    static class FieldCycleConfig {
        @org.springframework.context.annotation.Bean
        FieldCycleA fieldCycleA() {
            return new FieldCycleA();
        }

        @org.springframework.context.annotation.Bean
        FieldCycleB fieldCycleB() {
            return new FieldCycleB();
        }
    }

    private static void demonstrateDisablingCircularReferencesMakesFieldCycleFailToo() {
        System.out.println("\n=== RECZNE WYLACZENIE 'allowCircularReferences' (JAK DOMYSLNIE ROBI SPRING BOOT 2.6+) ===");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(FieldCycleConfig.class);
        ((AbstractAutowireCapableBeanFactory) context.getBeanFactory()).setAllowCircularReferences(false);

        System.out.println("Ten sam TYP cyklu co w Lesson11, ale TERAZ z 'allowCircularReferences=false' (tak jak Spring Boot 2.6+ ustawia DOMYSLNIE)...");
        try {
            context.refresh();
            System.out.println("BLAD: kontekst wystartowal, mimo wylaczonej flagi!");
            context.close();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            System.out.println("Kontekst NIE wystartowal - przyczyna: " + root.getClass().getSimpleName());
            System.out.println("-> DOKLADNIE ta sama para klas, ktora w Lesson11 (domyslna flaga, 'goly' Spring) DZIALALA - tu, z flaga wylaczona, ZAWODZI.");
            System.out.println("   To WLASNIE roznica miedzy 'golym' Spring Frameworkiem a domyslnym zachowaniem `SpringApplication` od Boot 2.6.");
        }
    }
}
