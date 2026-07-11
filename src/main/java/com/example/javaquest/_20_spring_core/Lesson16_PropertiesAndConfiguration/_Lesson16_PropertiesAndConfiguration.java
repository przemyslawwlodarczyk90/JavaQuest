package com.example.javaquest._20_spring_core.Lesson16_PropertiesAndConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;

public class _Lesson16_PropertiesAndConfiguration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: WLASCIWOSCI I KONFIGURACJA (@Value, Environment, PropertySource) ===");

        /*
         * ============================================================
         * 📦 KONFIGURACJA POZA KODEM
         * ============================================================
         * Do tej pory wszystkie wartosci byly "na sztywno" w kodzie
         * (hardcoded). Realne aplikacje potrzebuja konfiguracji
         * ZEWNETRZNEJ - inny URL bazy na dev, inny na prod - BEZ
         * rekompilacji. Spring rozwiazuje to przez `Environment`
         * (abstrakcja NAD zrodlami wlasciwosci: pliki, zmienne
         * srodowiskowe, argumenty JVM) i `@Value("${klucz}")`
         * (wstrzykniecie POJEDYNCZEJ wartosci).
         */
        System.out.println("Environment = abstrakcja NAD wieloma zrodlami konfiguracji. @Value(\"${klucz}\") = wstrzykniecie POJEDYNCZEJ wartosci.");

        demonstrateValueAnnotationWithDefaultAndOverride();
        demonstrateEnvironmentAbstractionDirectly();
        demonstratePropertySourcePrecedenceOrder();

        /*
         * ============================================================
         * 🔹 KOLEJNOSC ZRODEL WLASCIWOSCI (PRECEDENCJA)
         * ============================================================
         * `Environment` przeszukuje ZAREJESTROWANE `PropertySource` W
         * KOLEJNOSCI - PIERWSZE trafienie WYGRYWA. W Spring Boot
         * (`_21_spring_boot/Lesson05`) ta kolejnosc jest DUZO bogatsza
         * (argumenty CLI > zmienne srodowiskowe > `application.yml` >
         * wartosci domyslne w kodzie) - dzisiejsze demo pokazuje SAMA
         * ZASADE na 2 prostych zrodlach.
         */
        System.out.println("\nKolejnosc PropertySource ma znaczenie - PIERWSZE zarejestrowane zrodlo z danym kluczem WYGRYWA (zobaczysz w _21_spring_boot pelna hierarchie).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Value("${klucz}")` wstrzykuje POJEDYNCZA wartosc - MOZNA
         *   podac wartosc DOMYSLNA (`${klucz:domyslna}`).
         * - `Environment.getProperty(...)` to bardziej PROGRAMOWY dostep
         *   do TYCH SAMYCH wartosci - przydatny poza wstrzykiwaniem.
         * - `PropertySource` MOZNA rejestrowac PROGRAMOWO (`@PropertySource`
         *   lub recznie) - Spring przeszukuje je W KOLEJNOSCI.
         * - Pelna hierarchia zrodel (CLI/env/YAML) to temat
         *   `_21_spring_boot/Lesson05_ApplicationPropertiesYaml`.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    static class AppInfo {
        @Value("${app.name:Domyslna Nazwa Aplikacji}")
        private String appName;

        @Value("${app.version}")
        private String appVersion;

        String describe() {
            return appName + " v" + appVersion;
        }
    }

    @Configuration
    @PropertySource("classpath:lesson16-app.properties")
    static class ValueConfig {
        @Bean
        AppInfo appInfo() {
            return new AppInfo();
        }
    }

    private static void demonstrateValueAnnotationWithDefaultAndOverride() {
        System.out.println("\n=== @Value(\"${klucz}\") - Z WARTOSCIA DOMYSLNA I BEZ ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueConfig.class)) {
            AppInfo info = context.getBean(AppInfo.class);
            System.out.println(info.describe());
            System.out.println("-> 'app.name' MIALO wartosc domyslna w kodzie (\"Domyslna Nazwa Aplikacji\"), ale plik .properties JA NADPISAL.");
            System.out.println("   'app.version' NIE MA wartosci domyslnej - MUSIALA pochodzic z pliku, inaczej byloby BeanCreationException.");
        }
    }

    @Configuration
    @PropertySource("classpath:lesson16-app.properties")
    static class EnvironmentConfig {
    }

    private static void demonstrateEnvironmentAbstractionDirectly() {
        System.out.println("\n=== Environment.getProperty(...) - PROGRAMOWY DOSTEP BEZ @Value ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EnvironmentConfig.class)) {
            Environment env = context.getEnvironment();
            System.out.println("app.name = " + env.getProperty("app.name"));
            System.out.println("app.version = " + env.getProperty("app.version"));
            System.out.println("app.brak.takiego.klucza = " + env.getProperty("app.brak.takiego.klucza", "WARTOSC_DOMYSLNA_Z_KODU"));
            System.out.println("-> Environment jest dostepny WSZEDZIE (np. w metodach @Bean) - @Value dziala TYLKO na polach/parametrach.");
        }
    }

    private static void demonstratePropertySourcePrecedenceOrder() {
        System.out.println("\n=== KOLEJNOSC PropertySource DECYDUJE, KTORA WARTOSC WYGRYWA ===");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        MutablePropertySources sources = context.getEnvironment().getPropertySources();

        // Dodane JAKO PIERWSZE (najwyzszy priorytet) - symulacja "argumentow linii polecen".
        sources.addFirst(new MapPropertySource("symulowane-argumenty-cli", Map.of("app.env", "PRODUKCJA (z 'CLI')")));
        // Dodane NA KONIEC (najnizszy priorytet) - symulacja "domyslnych wartosci w pliku".
        sources.addLast(new MapPropertySource("symulowany-plik-domyslny", Map.of("app.env", "development (z 'pliku domyslnego')")));

        context.refresh();
        System.out.println("app.env (2 zrodla z ROZNA wartoscia TEGO SAMEGO klucza) = " + context.getEnvironment().getProperty("app.env"));
        System.out.println("-> zrodlo dodane 'addFirst' (wyzszy priorytet, jak argumenty CLI) WYGRALO z tym dodanym 'addLast' (jak plik z wartosciami domyslnymi).");
        context.close();
    }
}
