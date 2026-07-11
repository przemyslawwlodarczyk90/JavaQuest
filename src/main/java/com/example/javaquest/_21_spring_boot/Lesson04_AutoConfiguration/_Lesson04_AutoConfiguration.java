package com.example.javaquest._21_spring_boot.Lesson04_AutoConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class _Lesson04_AutoConfiguration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: AUTO-KONFIGURACJA ===");

        /*
         * ============================================================
         * 📦 JAK BOOT WIE, CO SKONFIGUROWAC?
         * ============================================================
         * Lesson03 pokazal, ze starter DOSTARCZA biblioteki NA
         * CLASSPATH. Auto-konfiguracja to DRUGA polowa "magii" - Boot
         * SPRAWDZA, CO jest na classpath (przez adnotacje `@Conditional*`)
         * i NA TEJ PODSTAWIE decyduje, JAKIE beany zarejestrowac. Np.
         * "jesli `ObjectMapper` (Jackson) jest na classpath I uzytkownik
         * SAM nie zdefiniowal wlasnego - zarejestruj DOMYSLNY". Dzisiaj
         * piszesz WLASNA, uproszczona wersje TEGO SAMEGO mechanizmu.
         */
        System.out.println("Auto-konfiguracja = 'jesli X jest na classpath I uzytkownik NIE zdefiniowal wlasnego beana - zarejestruj DOMYSLNY'.");

        demonstrateConditionalOnClass();
        demonstrateConditionalOnMissingBeanLetsUserOverride();
        demonstrateConditionalOnProperty();
        demonstrateAutoConfigurationRegistrationFileIsReal();
        demonstrateRealAutoConfiguredBeanFromJackson();

        /*
         * ============================================================
         * 🔹 STARY vs NOWY MECHANIZM REJESTRACJI (Z Lesson02 _20_spring_core)
         * ============================================================
         * PRZED Spring Boot 2.7: klasy auto-konfiguracji byly wypisane w
         * `META-INF/spring.factories` pod kluczem `EnableAutoConfiguration`.
         * OD Boot 2.7 (ten kurs: 3.4.4): sa wypisane w PROSTYM pliku
         * tekstowym `META-INF/spring/org.springframework.boot.autoconfigure.
         * AutoConfiguration.imports` (jedna klasa na linie, adnotacja
         * `@AutoConfiguration` na kazdej) - WLASNIE ODCZYTALES ten plik
         * NAPRAWDE, z realnego JAR-a na classpath tego projektu.
         */
        System.out.println("\nStary mechanizm: META-INF/spring.factories. Nowy (od Boot 2.7, ten kurs): META-INF/spring/...AutoConfiguration.imports (widziales REALNY plik wyzej).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@ConditionalOnClass` - "zarejestruj TYLKO jesli TA klasa
         *   jest na classpath" (dlatego dodanie startera "automatycznie
         *   wlacza" cos - klasa POJAWILA SIE na classpath).
         * - `@ConditionalOnMissingBean` - "zarejestruj TYLKO jesli
         *   UZYTKOWNIK jeszcze NIE zdefiniowal WLASNEGO" - Twoj `@Bean`
         *   ZAWSZE wygrywa z auto-konfiguracja.
         * - `@ConditionalOnProperty` - "zarejestruj TYLKO jesli
         *   wlasciwosc ma OKRESLONA wartosc" - feature flagi.
         * - Realna auto-konfiguracja Boota (spring-boot-autoconfigure.jar)
         *   dziala DOKLADNIE tak samo, jak Twoje demo - setki klas z
         *   TYMI SAMYMI adnotacjami `@Conditional*`.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    // Symulacja "auto-konfiguracji" - klasa BEZ zadnego jawnego rejestrowania w main(),
    // dolaczana jak ZWYKLA klasa @Configuration.
    @Configuration
    static class JacksonStyleAutoConfig {
        @Bean
        @ConditionalOnClass(ObjectMapper.class)
        String jacksonStatusMessage() {
            return "ObjectMapper (Jackson) ZNALEZIONY na classpath - auto-konfiguracja SIE WLACZYLA";
        }
    }

    @Configuration
    static class ImaginaryLibraryAutoConfig {
        @Bean
        @ConditionalOnClass(name = "com.wymyslona.biblioteka.NieistniejacaKlasa")
        String imaginaryStatusMessage() {
            return "Ta wiadomosc NIGDY sie nie pojawi - klasa nie istnieje na classpath";
        }
    }

    @SpringBootApplication
    static class ConditionalOnClassApp {
    }

    private static void demonstrateConditionalOnClass() {
        System.out.println("\n=== @ConditionalOnClass: REJESTRUJ TYLKO JESLI KLASA JEST NA CLASSPATH ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ConditionalOnClassApp.class)
                .web(WebApplicationType.NONE)
                .sources(JacksonStyleAutoConfig.class, ImaginaryLibraryAutoConfig.class)
                .run();
        try {
            System.out.println("Bean 'jacksonStatusMessage' istnieje: " + context.containsBean("jacksonStatusMessage"));
            System.out.println("Bean 'imaginaryStatusMessage' istnieje: " + context.containsBean("imaginaryStatusMessage"));
            System.out.println("-> DOKLADNIE taki mechanizm sprawia, ze Boot 'wie', kiedy skonfigurowac Tomcata/Jacksona/baze danych - bo ich klasy SA (lub NIE SA) na classpath.");
        } finally {
            context.close();
        }
    }

    interface MessageFormatter {
        String format(String message);
    }

    @Configuration
    static class DefaultFormatterAutoConfig {
        @Bean
        @ConditionalOnMissingBean(MessageFormatter.class)
        MessageFormatter defaultMessageFormatter() {
            return message -> "[DOMYSLNY] " + message;
        }
    }

    @Configuration
    static class UserDefinedFormatterConfig {
        @Bean
        MessageFormatter customMessageFormatter() {
            return message -> "[UZYTKOWNIKA] " + message;
        }
    }

    @SpringBootApplication
    static class ConditionalOnMissingBeanApp {
    }

    private static void demonstrateConditionalOnMissingBeanLetsUserOverride() {
        System.out.println("\n=== @ConditionalOnMissingBean: TWOJ BEAN ZAWSZE WYGRYWA Z AUTO-KONFIGURACJA ===");

        System.out.println("--- Scenariusz A: TYLKO auto-konfiguracja (uzytkownik NIC nie zdefiniowal) ---");
        ConfigurableApplicationContext contextA = new SpringApplicationBuilder(ConditionalOnMissingBeanApp.class)
                .web(WebApplicationType.NONE)
                .sources(DefaultFormatterAutoConfig.class)
                .run();
        try {
            System.out.println(contextA.getBean(MessageFormatter.class).format("witaj"));
        } finally {
            contextA.close();
        }

        System.out.println("--- Scenariusz B: uzytkownik ZDEFINIOWAL WLASNY bean (auto-konfiguracja 'ustepuje') ---");
        ConfigurableApplicationContext contextB = new SpringApplicationBuilder(ConditionalOnMissingBeanApp.class)
                .web(WebApplicationType.NONE)
                .sources(UserDefinedFormatterConfig.class, DefaultFormatterAutoConfig.class)
                .run();
        try {
            System.out.println(contextB.getBean(MessageFormatter.class).format("witaj"));
            System.out.println("-> w Scenariuszu B jest TYLKO JEDEN bean MessageFormatter - Twoj wlasny - auto-konfiguracja 'wycofala sie' bez zadnego konfliktu.");
        } finally {
            contextB.close();
        }
    }

    @Configuration
    static class FeatureToggleConfig {
        @Bean
        @ConditionalOnProperty(name = "feature.new-checkout.enabled", havingValue = "true")
        String newCheckoutFeature() {
            return "NOWY proces checkout WLACZONY";
        }
    }

    @SpringBootApplication
    static class ConditionalOnPropertyApp {
    }

    private static void demonstrateConditionalOnProperty() {
        System.out.println("\n=== @ConditionalOnProperty: FEATURE FLAGA STEROWANA WLASCIWOSCIA ===");

        System.out.println("--- BEZ ustawionej wlasciwosci ---");
        ConfigurableApplicationContext contextOff = new SpringApplicationBuilder(ConditionalOnPropertyApp.class)
                .web(WebApplicationType.NONE)
                .sources(FeatureToggleConfig.class)
                .run();
        try {
            System.out.println("Bean 'newCheckoutFeature' istnieje: " + contextOff.containsBean("newCheckoutFeature"));
        } finally {
            contextOff.close();
        }

        System.out.println("--- Z USTAWIONA wlasciwoscia 'feature.new-checkout.enabled=true' ---");
        Properties props = new Properties();
        props.setProperty("feature.new-checkout.enabled", "true");
        ConfigurableApplicationContext contextOn = new SpringApplicationBuilder(ConditionalOnPropertyApp.class)
                .web(WebApplicationType.NONE)
                .properties(props)
                .sources(FeatureToggleConfig.class)
                .run();
        try {
            System.out.println("Bean 'newCheckoutFeature' istnieje: " + contextOn.containsBean("newCheckoutFeature"));
        } finally {
            contextOn.close();
        }
    }

    private static void demonstrateAutoConfigurationRegistrationFileIsReal() {
        System.out.println("\n=== REALNY PLIK REJESTRACJI AUTO-KONFIGURACJI (spring-boot-autoconfigure.jar NA CLASSPATH) ===");

        String resourcePath = "META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports";
        try (InputStream in = _Lesson04_AutoConfiguration.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.out.println("Nie znaleziono pliku '" + resourcePath + "' na classpath - pomijam ten fragment demo.");
                return;
            }
            List<String> lines = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .filter(line -> !line.isBlank() && !line.startsWith("#"))
                    .toList();

            System.out.println("Plik '" + resourcePath + "' ISTNIEJE naprawde na classpath tego projektu.");
            System.out.println("Liczba ZAREJESTROWANYCH klas auto-konfiguracji: " + lines.size());
            System.out.println("Pierwsze 5 (z setek):");
            lines.stream().limit(5).forEach(line -> System.out.println("  " + line));
            System.out.println("-> to NIE jest opis 'na sluch' - to REALNA zawartosc pliku, ktory Spring Boot faktycznie czyta przy KAZDYM starcie aplikacji.");
        } catch (IOException e) {
            System.out.println("Blad odczytu pliku: " + e.getMessage());
        }
    }

    @SpringBootApplication
    static class RealAutoConfiguredApp {
    }

    private static void demonstrateRealAutoConfiguredBeanFromJackson() {
        System.out.println("\n=== PRAWDZIWA AUTO-KONFIGURACJA BOOTA: BEAN ObjectMapper 'ZA DARMO' ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RealAutoConfiguredApp.class)
                .web(WebApplicationType.NONE)
                .run();
        try {
            boolean hasObjectMapper = context.getBeanNamesForType(ObjectMapper.class).length > 0;
            System.out.println("Bean typu ObjectMapper istnieje w kontekscie: " + hasObjectMapper);
            System.out.println("-> Ty NIGDY nie napisales '@Bean ObjectMapper objectMapper()' - JacksonAutoConfiguration (prawdziwa klasa Boota) zrobila to ZA CIEBIE,");
            System.out.println("   bo wykryla Jacksona na classpath (dzieki `spring-boot-starter-web`, Lesson03) - DOKLADNIE mechanizm z Twoich wlasnorecznych demo wyzej.");
        } finally {
            context.close();
        }
    }
}
