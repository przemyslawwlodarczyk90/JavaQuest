package com.example.javaquest._21_spring_boot.Lesson08_ConfigurationProperties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

public class _Lesson08_ConfigurationProperties {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: @ConfigurationProperties ===");

        /*
         * ============================================================
         * 📦 WIELE @Value vs JEDNA KLASA @ConfigurationProperties
         * ============================================================
         * `_20_spring_core/Lesson16` uzywal pojedynczych `@Value("${klucz}")`
         * - dziala dobrze dla 1-2 wartosci, ale przy ZLOZONEJ,
         * ZAGNIEZDZONEJ konfiguracji (10+ powiazanych wartosci) staje
         * sie ROZWLEKLE i BRAK bezpieczenstwa typow miedzy nimi.
         * `@ConfigurationProperties` wiaze CALA grupe wlasciwosci
         * (kropkowany prefiks) do JEDNEJ, TYPOWANEJ klasy NARAZ -
         * JEDNO miejsce, latwe do przetestowania, latwe do
         * ZWALIDOWANIA jako calosc.
         */
        System.out.println("@Value = pojedyncze wartosci. @ConfigurationProperties = CALA grupa powiazanych wartosci w 1 typowanej klasie naraz.");

        demonstrateConfigurationPropertiesBinding();
        demonstrateValidationFailsFastOnInvalidValues();
        demonstrateManyValueFieldsVsOneConfigurationPropertiesClass();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@ConfigurationProperties(prefix = "...")` wiaze CALA GRUPE
         *   wlasciwosci (kropkowany prefiks) do POL klasy - NAZWY POL
         *   MUSZA odpowiadac (w formacie kebab-case w pliku) nazwom
         *   wlasciwosci.
         * - Klasa MUSI byc zarejestrowana (`@EnableConfigurationProperties`
         *   lub `@ConfigurationPropertiesScan`) - inaczej Boot jej NIE
         *   ZWIAZE.
         * - `@Validated` + Bean Validation (`_19_security_basics/Lesson17`)
         *   na CALEJ klasie konfiguracji - BLEDNA wartosc PRZERYWA
         *   start aplikacji (fail-fast), zamiast ujawnic sie DOPIERO w
         *   runtime.
         * - WYBOR: `@Value` dla POJEDYNCZYCH, prostych wartosci,
         *   `@ConfigurationProperties` dla ZWARTYCH, ZAGNIEZDZONYCH grup.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    // PUBLIC klasa + PUBLIC gettery/settery - Boot Binder (jak BeanWrapper w _20_spring_core/
    // Lesson19) wymaga PUBLICZNEJ pary getter/setter do powiazania wlasciwosci.
    @ConfigurationProperties(prefix = "javaquest.lesson08.pool")
    public static class PoolProperties {
        private int maxSize = 10;
        private int timeoutSeconds = 30;

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getTimeoutSeconds() {
            return timeoutSeconds;
        }

        public void setTimeoutSeconds(int timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
        }

        @Override
        public String toString() {
            return "PoolProperties{maxSize=" + maxSize + ", timeoutSeconds=" + timeoutSeconds + "}";
        }
    }

    @SpringBootApplication
    @EnableConfigurationProperties(PoolProperties.class)
    static class ConfigPropsApp {
    }

    private static void demonstrateConfigurationPropertiesBinding() {
        System.out.println("\n=== BINDOWANIE CALEJ GRUPY WLASCIWOSCI DO 1 KLASY ===");

        Properties props = new Properties();
        props.setProperty("javaquest.lesson08.pool.max-size", "25");
        props.setProperty("javaquest.lesson08.pool.timeout-seconds", "45");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ConfigPropsApp.class)
                .web(WebApplicationType.NONE)
                .properties(props)
                .run();
        try {
            PoolProperties poolProperties = context.getBean(PoolProperties.class);
            System.out.println(poolProperties);
            System.out.println("-> pole 'maxSize' powiazane z kluczem 'javaquest.lesson08.pool.max-size' (kebab-case w pliku -> camelCase w polu) - AUTOMATYCZNIE.");
        } finally {
            context.close();
        }
    }

    @ConfigurationProperties(prefix = "javaquest.lesson08.validated-pool")
    @Validated
    public static class ValidatedPoolProperties {
        @Min(1)
        @Max(100)
        private int maxSize = 10;

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }
    }

    @SpringBootApplication
    @EnableConfigurationProperties(ValidatedPoolProperties.class)
    static class ValidatedConfigPropsApp {
    }

    private static void demonstrateValidationFailsFastOnInvalidValues() {
        System.out.println("\n=== @Validated NA @ConfigurationProperties: FAIL-FAST PRZY BLEDNEJ WARTOSCI ===");

        Properties invalidProps = new Properties();
        invalidProps.setProperty("javaquest.lesson08.validated-pool.max-size", "999");

        System.out.println("Ustawiam max-size=999 (poza dozwolonym zakresem 1-100)...");
        try {
            ConfigurableApplicationContext context = new SpringApplicationBuilder(ValidatedConfigPropsApp.class)
                    .web(WebApplicationType.NONE)
                    .properties(invalidProps)
                    .run();
            System.out.println("BLAD: kontekst wystartowal, mimo NIEPRAWIDLOWEJ wartosci!");
            context.close();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            System.out.println("Kontekst NIE wystartowal (fail-fast) - przyczyna: " + root.getClass().getSimpleName());
            System.out.println("-> BLEDNA konfiguracja zostala wykryta PRZY STARCIE, ZANIM aplikacja przyjelaby JAKIKOLWIEK ruch.");
        }
    }

    private static void demonstrateManyValueFieldsVsOneConfigurationPropertiesClass() {
        System.out.println("\n=== PRZYPOMNIENIE: WIELE @Value (Lesson16 z _20_spring_core) vs 1 @ConfigurationProperties ===");

        System.out.println("Styl z Lesson16 (_20_spring_core) - WIELE osobnych pol z @Value:");
        System.out.println("  @Value(\"${javaquest.lesson08.pool.max-size}\") private int maxSize;");
        System.out.println("  @Value(\"${javaquest.lesson08.pool.timeout-seconds}\") private int timeoutSeconds;");
        System.out.println("  ...(kazda WARTOSC to OSOBNA linia, OSOBNA adnotacja, latwo o literowke w kluczu)");
        System.out.println("Styl z tej lekcji - 1 klasa @ConfigurationProperties(prefix=\"...\") grupuje WSZYSTKO naraz:");
        System.out.println("  " + PoolProperties.class.getSimpleName() + " { maxSize, timeoutSeconds } - JEDNO miejsce, JEDEN prefiks, LATWA walidacja calosci.");
        System.out.println("-> im WIECEJ powiazanych wartosci, tym WYRAZNIEJSZA przewaga @ConfigurationProperties.");
    }
}
