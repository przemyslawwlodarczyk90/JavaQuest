package com.example.javaquest._20_spring_core.Lesson08_ComponentServiceRepository;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class _Lesson08_ComponentServiceRepository {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: @Component / @Service / @Repository / @Controller ===");

        /*
         * ============================================================
         * 📦 4 STEREOTYPY, DLA KONTENERA TO SAME "@Component"
         * ============================================================
         * `@Service`, `@Repository`, `@Controller` to WYSPECJALIZOWANE
         * WARIANTY `@Component` — każda z tych adnotacji jest SAMA
         * META-OZNACZONA `@Component` (zweryfikujemy to reflekcją
         * niżej!). Z PUNKTU WIDZENIA kontenera IoC — WSZYSTKIE 4 działają
         * IDENTYCZNIE: klasa staje się beanem, komponent-scanning ją
         * znajduje. RÓŻNICA jest w SEMANTYCE (dla CZŁOWIEKA czytającego
         * kod — "to jest logika biznesowa" vs "to jest dostęp do danych")
         * oraz w JEDNYM konkretnym, DODATKOWYM zachowaniu przy
         * `@Repository` (zobaczysz je w drugiej części lekcji).
         */
        System.out.println("@Service/@Repository/@Controller to WYSPECJALIZOWANE @Component - dla kontenera IoC dzialaja IDENTYCZNIE.");

        demonstrateStereotypesAreAllMetaAnnotatedWithComponent();
        demonstrateAllStereotypesWorkIdenticallyInContainer();
        demonstrateRepositoryExceptionTranslation();

        /*
         * ============================================================
         * 🔹 KIEDY UZYWAC KTOREJ ADNOTACJI (SEMANTYKA DLA CZLOWIEKA)
         * ============================================================
         * - `@Component` — OGOLNA adnotacja, gdy klasa NIE pasuje do
         *   zadnej bardziej szczegolowej kategorii (np. narzedzie/util).
         * - `@Service` — warstwa LOGIKI BIZNESOWEJ (orkiestruje
         *   repozytoria, waliduje, podejmuje decyzje) — POGLEBIENIE:
         *   `_10_dao/Lesson02_LayeredArchitecture`.
         * - `@Repository` — warstwa DOSTEPU DO DANYCH — DODATKOWO
         *   wlacza automatyczne tlumaczenie wyjatkow (zobaczyles wyzej).
         * - `@Controller`/`@RestController` — warstwa PREZENTACJI/API —
         *   DODATKOWO wlacza obsluge przez Spring MVC (routing zadan
         *   HTTP) - PELNA lekcja w `_22_spring_web/Lesson01`.
         */
        System.out.println("\nSemantyka dla czlowieka: @Service=logika biznesowa, @Repository=dostep do danych, @Controller=warstwa API/prezentacji.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wszystkie 4 stereotypy sa META-OZNACZONE `@Component` -
         *   kontener IoC traktuje je IDENTYCZNIE przy tworzeniu beanow.
         * - `@Repository` DODAJE automatyczne tlumaczenie wyjatkow
         *   natywnych (np. sterownika bazy danych) na spojna hierarchie
         *   `DataAccessException` Springa (mechanizm sprawdzony DZIS na
         *   wlasnorecznym odpowiedniku opartym o proxy JDK).
         * - `@Controller`/`@RestController` DODAJE obsluge przez Spring
         *   MVC - bez tej adnotacji Spring nie bedzie kierowal do klasy
         *   zadan HTTP (zobaczysz w `_22_spring_web`).
         * - Wybor MIEDZY nimi to w duzej mierze CZYTELNOSC kodu dla
         *   innych programistow, NIE roznica funkcjonalna (poza tymi 2
         *   wyjatkami).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateStereotypesAreAllMetaAnnotatedWithComponent() {
        System.out.println("\n=== DOWOD: @Service/@Repository/@Controller SA META-OZNACZONE @Component ===");

        System.out.println("@Service.class ma na sobie @Component: " + Service.class.isAnnotationPresent(Component.class));
        System.out.println("@Repository.class ma na sobie @Component: " + Repository.class.isAnnotationPresent(Component.class));
        System.out.println("@Controller.class ma na sobie @Component: " + Controller.class.isAnnotationPresent(Component.class));
        System.out.println("-> to NIE 3 niezalezne mechanizmy - to 3 SPECJALIZACJE JEDNEGO mechanizmu (@Component), sprawdzone reflekcja, nie 'na slowo'.");
    }

    @Component
    static class GenericComponent {
        String identify() {
            return "Jestem @Component";
        }
    }

    @Service
    static class OrderCalculationService {
        String identify() {
            return "Jestem @Service";
        }
    }

    @Repository
    static class OrderRepository {
        String identify() {
            return "Jestem @Repository";
        }
    }

    @Controller
    static class OrderController {
        String identify() {
            return "Jestem @Controller";
        }
    }

    @Configuration
    static class StereotypeConfig {
        @Bean
        GenericComponent genericComponent() {
            return new GenericComponent();
        }

        @Bean
        OrderCalculationService orderCalculationService() {
            return new OrderCalculationService();
        }

        @Bean
        OrderRepository orderRepository() {
            return new OrderRepository();
        }

        @Bean
        OrderController orderController() {
            return new OrderController();
        }
    }

    private static void demonstrateAllStereotypesWorkIdenticallyInContainer() {
        System.out.println("\n=== WSZYSTKIE 4 STEREOTYPY DZIALAJA IDENTYCZNIE W KONTENERZE ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StereotypeConfig.class)) {
            System.out.println(context.getBean(GenericComponent.class).identify());
            System.out.println(context.getBean(OrderCalculationService.class).identify());
            System.out.println(context.getBean(OrderRepository.class).identify());
            System.out.println(context.getBean(OrderController.class).identify());
            System.out.println("-> KAZDY zostal znaleziony i utworzony DOKLADNIE tak samo - kontener NIE ROZROZNIA stereotypow przy tworzeniu beanow.");
        }
    }

    /*
     * UWAGA TECHNICZNA: prawdziwy mechanizm @Repository (PersistenceExceptionTranslationPostProcessor
     * + hierarchia DataAccessException) zyje w module spring-tx, ktorego ten rozdzial CELOWO
     * jeszcze NIE dodaje do pom.xml (pojawi sie naturalnie w _23_spring_data_jpa razem ze
     * spring-boot-starter-data-jpa). Ponizej budujemy WLASNY, uproszczony odpowiednik TEGO SAMEGO
     * mechanizmu (proxy JDK przechwytujacy i tlumaczacy wyjatki) - identyczna IDEA, zerowa nowa
     * zaleznosc, i skuteczne zapowiedz Lesson21_SpringAopFundamentals (proxy = fundament AOP).
     */
    private interface DataRepository {
        void save();
    }

    static class NativeDriverException extends RuntimeException {
        NativeDriverException(String message) {
            super(message);
        }
    }

    static class TranslatedDataAccessException extends RuntimeException {
        TranslatedDataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class FaultyRepositoryImpl implements DataRepository {
        @Override
        public void save() {
            // Symulacja niskopoziomowego wyjatku "sterownika bazy danych" - NIE zna Springa w ogole.
            throw new NativeDriverException("Utracono polaczenie z baza danych (symulacja)");
        }
    }

    private static DataRepository wrapWithExceptionTranslation(DataRepository target) {
        return (DataRepository) Proxy.newProxyInstance(
                DataRepository.class.getClassLoader(),
                new Class<?>[]{DataRepository.class},
                (proxy, method, methodArgs) -> {
                    try {
                        return method.invoke(target, methodArgs);
                    } catch (InvocationTargetException e) {
                        if (e.getTargetException() instanceof NativeDriverException nativeEx) {
                            throw new TranslatedDataAccessException("Przetlumaczony blad dostepu do danych", nativeEx);
                        }
                        throw e.getTargetException();
                    }
                });
    }

    private static void demonstrateRepositoryExceptionTranslation() {
        System.out.println("\n=== SPECJALNA CECHA @Repository: AUTOMATYCZNE TLUMACZENIE WYJATKOW (WLASNORECZNY ODPOWIEDNIK) ===");

        DataRepository repository = wrapWithExceptionTranslation(new FaultyRepositoryImpl());

        System.out.println("Wolam repository.save() (opakowane proxy), ktore rzuca WEWNETRZNIE 'NativeDriverException'...");
        try {
            repository.save();
        } catch (TranslatedDataAccessException e) {
            System.out.println("ZLAPANO: " + e.getClass().getSimpleName() + " - \"" + e.getMessage() + "\"");
            System.out.println("Przyczyna (oryginalny wyjatek): " + e.getCause().getClass().getSimpleName());
            System.out.println("-> DOKLADNIE TAK dziala prawdziwe @Repository w Springu: PersistenceExceptionTranslationPostProcessor");
            System.out.println("   opakowuje bean w proxy, ktory PRZECHWYTUJE surowy wyjatek i TLUMACZY go na spojna hierarchie");
            System.out.println("   DataAccessException (dostepna od _23_spring_data_jpa, gdy dojdzie spring-tx przez spring-boot-starter-data-jpa).");
        }
    }
}
