package com.example.javaquest._20_spring_core.Lesson09_ConfigurationAndBeanAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class _Lesson09_ConfigurationAndBeanAnnotation {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: @Configuration + @Bean ===");

        /*
         * ============================================================
         * 📦 KIEDY @Component NIE WYSTARCZY
         * ============================================================
         * `@Component` (Lesson06-08) dziala TYLKO dla klas, KTORYCH
         * KOD jest Twoj - musisz miec mozliwosc dodania adnotacji
         * WEWNATRZ klasy. Co z klasa z ZEWNETRZNEJ biblioteki (np.
         * `ObjectMapper` z Jacksona, `RestTemplate`) - NIE MOZESZ
         * dodac do niej `@Component`, bo nie jest Twoja! Rozwiazanie:
         * `@Configuration` + `@Bean` NA METODZIE - Ty JAWNIE piszesz
         * kod tworzacy obiekt (`new ObcaKlasa(...)`), a Spring
         * zarzadza WYNIKIEM tej metody jak KAZDYM innym beanem.
         */
        System.out.println("@Component wymaga dostepu do kodu klasy. @Configuration+@Bean dziala dla DOWOLNEJ klasy - takze z zewnetrznej biblioteki.");

        demonstrateBeanMethodForExternalStyleClass();
        demonstrateBeanMethodParameterInjection();
        demonstrateFullModeProxyingEnsuresSingleton();
        demonstrateLiteModeDoesNotIntercept();

        /*
         * ============================================================
         * 🔹 FULL MODE (DOMYSLNY) vs LITE MODE (proxyBeanMethods=false)
         * ============================================================
         * DOMYSLNIE Spring OPAKOWUJE (CGLIB) KAZDA klase `@Configuration`
         * w proxy - dzieki temu "reczne" wywolanie jednej metody `@Bean`
         * Z WNETRZA drugiej (`car()` wolajace `engine()`) jest
         * PRZECHWYCONE i zwraca ISTNIEJACY singleton, a NIE tworzy nowej
         * instancji (mimo ze to "zwykle" wywolanie metody Javy!). To
         * KOSZTUJE (CGLIB, refleksja) - dlatego Spring Boot w WLASNYCH
         * klasach auto-konfiguracyjnych CZESTO uzywa
         * `@Configuration(proxyBeanMethods = false)` ("lite mode") dla
         * SZYBSZEGO startu, gdy WIE, ze klasa NIE wywoluje swoich metod
         * `@Bean` nawzajem.
         */
        System.out.println("\nFull mode (domyslny) = CGLIB proxy przechwytuje wywolania miedzy @Bean. Lite mode (proxyBeanMethods=false) = szybszy start, BEZ przechwytywania.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Configuration`+`@Bean` = jedyny sposob na zarejestrowanie
         *   beana z klasy, ktorej kodu NIE jestes wlascicielem.
         * - Metoda `@Bean` moze przyjmowac INNE beany jako PARAMETRY -
         *   Spring sam je wstrzyknie (REKOMENDOWANY sposob laczenia
         *   beanow miedzy soba).
         * - W trybie "full" (domyslnym) Spring PRZECHWYTUJE "reczne"
         *   wywolania metod `@Bean` wewnatrz klasy `@Configuration` -
         *   gwarantuje to singleton NAWET przy takim wywolaniu.
         * - `proxyBeanMethods = false` ("lite mode") wylacza to
         *   przechwytywanie - szybszy start, ale odpowiedzialnosc za
         *   NIE-wywolywanie metod `@Bean` nawzajem spada na Ciebie.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    // Symulacja klasy z ZEWNETRZNEJ biblioteki - NIE MOZESZ dodac jej @Component.
    static class ExternalLibraryClient {
        private final String apiKey;

        ExternalLibraryClient(String apiKey) {
            this.apiKey = apiKey;
        }

        String describe() {
            return "ExternalLibraryClient skonfigurowany z kluczem: " + apiKey;
        }
    }

    @Configuration
    static class ExternalStyleConfig {
        @Bean
        ExternalLibraryClient externalLibraryClient() {
            // Ty JAWNIE piszesz logike budowy - dokladnie tak, jak zrobilbys to z klasa z Maven Central.
            return new ExternalLibraryClient("demo-api-key-123");
        }
    }

    private static void demonstrateBeanMethodForExternalStyleClass() {
        System.out.println("\n=== @Bean DLA KLASY 'Z ZEWNETRZNEJ BIBLIOTEKI' ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExternalStyleConfig.class)) {
            ExternalLibraryClient client = context.getBean(ExternalLibraryClient.class);
            System.out.println(client.describe());
            System.out.println("-> klasa NIE MA zadnej adnotacji Springa - a jednak jest PELNOPRAWNYM beanem w kontenerze.");
        }
    }

    static class Engine {
        private final int horsePower;

        Engine(int horsePower) {
            this.horsePower = horsePower;
        }

        int getHorsePower() {
            return horsePower;
        }
    }

    static class Car {
        private final Engine engine;

        Car(Engine engine) {
            this.engine = engine;
        }

        Engine getEngine() {
            return engine;
        }
    }

    @Configuration
    static class ParameterInjectionConfig {
        @Bean
        Engine engine() {
            return new Engine(150);
        }

        @Bean
        Car car(Engine engine) {
            // REKOMENDOWANY sposob: zaleznosc jako PARAMETR metody - Spring SAM ja wstrzykuje.
            return new Car(engine);
        }
    }

    private static void demonstrateBeanMethodParameterInjection() {
        System.out.println("\n=== ZALEZNOSC JAKO PARAMETR METODY @Bean (REKOMENDOWANE) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ParameterInjectionConfig.class)) {
            Engine engineBean = context.getBean(Engine.class);
            Car car = context.getBean(Car.class);
            System.out.println("Moc silnika: " + car.getEngine().getHorsePower() + " KM");
            System.out.println("car.getEngine() to TEN SAM obiekt co samodzielny bean Engine: " + (car.getEngine() == engineBean));
        }
    }

    @Configuration
    static class FullModeConfig {
        static int engineCreationCount = 0;

        @Bean
        Engine engine() {
            engineCreationCount++;
            System.out.println("  [FullModeConfig] engine() WYWOLANE (licznik: " + engineCreationCount + ")");
            return new Engine(200);
        }

        @Bean
        Car car() {
            // UWAGA: to "zwykle" wywolanie metody Javy, NIE parametr - w CZYSTEJ Javie
            // stworzyloby NOWY obiekt Engine. W Springu (full mode) - zobaczysz nizej, ze NIE.
            return new Car(engine());
        }
    }

    private static void demonstrateFullModeProxyingEnsuresSingleton() {
        System.out.println("\n=== FULL MODE (DOMYSLNY): CGLIB PRZECHWYTUJE 'RECZNE' WYWOLANIA MIEDZY @Bean ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FullModeConfig.class)) {
            Engine standaloneEngine = context.getBean(Engine.class);
            Car car = context.getBean(Car.class);

            System.out.println("Metoda engine() zostala WYWOLANA (przez JVM) " + FullModeConfig.engineCreationCount + " raz(y), mimo 2 'zapotrzebowan' (Engine bean + car() wywolujace engine()).");
            System.out.println("car.getEngine() == standaloneEngine: " + (car.getEngine() == standaloneEngine) + " -> TA SAMA instancja, mimo 'recznego' wywolania w kodzie car()!");
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class LiteModeConfig {
        static int engineCreationCount = 0;

        @Bean
        Engine engine() {
            engineCreationCount++;
            return new Engine(200);
        }

        @Bean
        Car car() {
            // W LITE MODE to NAPRAWDE zwykle wywolanie metody Javy - TWORZY NOWY obiekt Engine!
            return new Car(engine());
        }
    }

    private static void demonstrateLiteModeDoesNotIntercept() {
        System.out.println("\n=== LITE MODE (proxyBeanMethods=false): BRAK PRZECHWYTYWANIA ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LiteModeConfig.class)) {
            Engine standaloneEngine = context.getBean(Engine.class);
            Car car = context.getBean(Car.class);

            System.out.println("Metoda engine() zostala WYWOLANA " + LiteModeConfig.engineCreationCount + " raz(y) - w lite mode KAZDE 'reczne' wywolanie TWORZY NOWY obiekt.");
            System.out.println("car.getEngine() == standaloneEngine: " + (car.getEngine() == standaloneEngine) + " -> to DWIE ROZNE instancje Engine, prawdopodobnie NIE tego chciales!");
            System.out.println("-> WNIOSEK: w lite mode uzywaj WYLACZNIE parametrow metody (jak w demonstrateBeanMethodParameterInjection), NIGDY 'recznych' wywolan.");
        }
    }
}
