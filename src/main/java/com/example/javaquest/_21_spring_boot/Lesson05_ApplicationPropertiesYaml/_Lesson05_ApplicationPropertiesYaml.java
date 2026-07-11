package com.example.javaquest._21_spring_boot.Lesson05_ApplicationPropertiesYaml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson05_ApplicationPropertiesYaml {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: application.properties / application.yml ===");

        /*
         * ============================================================
         * 📦 KONFIGURACJA "ZA DARMO" - BEZ @PropertySource
         * ============================================================
         * `_20_spring_core/Lesson16` uzywal `@PropertySource("classpath:
         * plik.properties")` - MUSIALES jawnie wskazac plik. Spring Boot
         * AUTOMATYCZNIE laduje `application.properties` LUB
         * `application.yml` z `src/main/resources` - BEZ zadnej
         * adnotacji. Ten projekt JUZ MA taki plik (widziales go
         * posrednio w `_10_dao`/`_12_hibernate` itd.) - dzisiaj
         * przyjrzysz mu sie z perspektywy Spring Boota.
         */
        System.out.println("application.properties/application.yml w src/main/resources laduja sie AUTOMATYCZNIE - bez @PropertySource.");

        demonstrateThisProjectAlreadyHasApplicationProperties();
        demonstrateYamlNestedStructureFlattensToDottedKeys();
        demonstratePropertiesVsYamlSameResultDifferentSyntax();

        /*
         * ============================================================
         * 🔹 .properties vs .yml - KIEDY KTORY
         * ============================================================
         * `.properties` - PLASKA lista `klucz=wartosc` - prosta,
         * jednoznaczna, ale ROZWLEKLA przy zagniezdzonej konfiguracji
         * (`app.db.pool.max-size=10`). `.yml` - HIERARCHICZNY (wciecia),
         * CZYTELNIEJSZY dla ZLOZONEJ konfiguracji, ale WRAZLIWY na
         * BLEDY wciec (YAML uzywa SPACJI, NIE tabulatorow). Boot
         * ROZUMIE OBA - mozesz nawet miec OBA pliki naraz (choc TO
         * rzadko ma sens).
         */
        System.out.println("\n.properties = plaskie klucz=wartosc, proste. .yml = hierarchiczne (wciecia), czytelniejsze przy zlozonej konfiguracji, ale wrazliwe na bledy wciec.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `application.properties`/`application.yml` w `src/main/
         *   resources` ladujace sie AUTOMATYCZNIE - to POGLEBIENIE
         *   `@PropertySource` z `_20_spring_core/Lesson16`.
         * - YAML zagniezdzony (`app: db: pool: max-size: 10`) to TO
         *   SAMO co plaski klucz `app.db.pool.max-size=10` - Boot
         *   SPLASZCZA hierarchie do kropkowanych kluczy WEWNETRZNIE.
         * - `Environment.getProperty(...)`/`@Value` DZIALAJA
         *   IDENTYCZNIE niezaleznie od formatu pliku.
         * - Nastepna lekcja (`Lesson06_ProfilesInSpringBoot`) pokaze
         *   `application-{profil}.yml` - POGLEBIENIE `@Profile` z
         *   `_20_spring_core/Lesson15`.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateThisProjectAlreadyHasApplicationProperties() {
        System.out.println("\n=== TEN PROJEKT JUZ MA application.properties (ZAUWAZ - BEZ ZADNEJ ADNOTACJI) ===");

        Path resourcesPath = Path.of("src/main/resources/application.properties");
        System.out.println("Plik '" + resourcesPath + "' istnieje: " + Files.exists(resourcesPath));
        System.out.println("-> ten plik ZAWSZE byl czesc tego projektu (od pierwszych rozdzialow) - Boot laduje go SAM, bez zadnej konfiguracji z Twojej strony.");
    }

    static class YamlLikeConfig {
        @Value("${javaquest.lesson05.db.pool.max-size:10}")
        private int maxPoolSize;

        @Value("${javaquest.lesson05.db.pool.timeout-seconds:30}")
        private int timeoutSeconds;

        int getMaxPoolSize() {
            return maxPoolSize;
        }

        int getTimeoutSeconds() {
            return timeoutSeconds;
        }
    }

    @SpringBootApplication
    static class YamlDemoApp {
        @org.springframework.context.annotation.Bean
        YamlLikeConfig yamlLikeConfig() {
            return new YamlLikeConfig();
        }
    }

    private static void demonstrateYamlNestedStructureFlattensToDottedKeys() {
        System.out.println("\n=== ZAGNIEZDZONA STRUKTURA YAML = TE SAME KROPKOWANE KLUCZE WEWNETRZNIE ===");
        System.out.println("Gdyby application.yml mial:");
        System.out.println("  javaquest:");
        System.out.println("    lesson05:");
        System.out.println("      db:");
        System.out.println("        pool:");
        System.out.println("          max-size: 10");
        System.out.println("          timeout-seconds: 30");
        System.out.println("To WEWNETRZNIE Spring 'widzi' to DOKLADNIE tak samo jak plaskie klucze:");
        System.out.println("  javaquest.lesson05.db.pool.max-size=10");
        System.out.println("  javaquest.lesson05.db.pool.timeout-seconds=30");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(YamlDemoApp.class)
                .web(WebApplicationType.NONE)
                .run();
        try {
            YamlLikeConfig config = context.getBean(YamlLikeConfig.class);
            System.out.println("Odczytane (przez @Value, brak pliku wiec wartosci DOMYSLNE): maxPoolSize=" + config.getMaxPoolSize() + ", timeoutSeconds=" + config.getTimeoutSeconds());
            System.out.println("-> @Value uzywa TEJ SAMEJ, kropkowanej sciezki - format pliku (.properties/.yml) jest NIEWIDOCZNY na TYM poziomie.");
        } finally {
            context.close();
        }
    }

    @SpringBootApplication
    static class EnvironmentDemoApp {
    }

    private static void demonstratePropertiesVsYamlSameResultDifferentSyntax() {
        System.out.println("\n=== .properties I .yml PROWADZA DO TEGO SAMEGO 'Environment' ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(EnvironmentDemoApp.class)
                .web(WebApplicationType.NONE)
                .run();
        try {
            Environment env = context.getEnvironment();
            System.out.println("spring.application.name (z REALNEGO application.properties, jesli ustawiony) = " + env.getProperty("spring.application.name", "(brak - to normalne, nie kazdy projekt to ustawia)"));
            System.out.println("-> niezaleznie, CZY wlasciwosc pochodzi z .properties, .yml, zmiennej srodowiskowej, czy argumentu CLI -");
            System.out.println("   `Environment.getProperty(...)` (z _20_spring_core/Lesson16) dziala IDENTYCZNIE dla WSZYSTKICH zrodel.");
        } finally {
            context.close();
        }
    }
}
