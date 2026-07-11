package com.example.javaquest._21_spring_boot.Lesson15_CustomAutoConfigurationAndStarters;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson15_CustomAutoConfigurationAndStarters {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 15: WLASNA AUTO-KONFIGURACJA I WLASNY STARTER ===");

        /*
         * ============================================================
         * 📦 ZAMYKAMY PETLE Z Lesson04 - OD UZYTKOWNIKA DO TWORCY
         * ============================================================
         * Lesson04 pokazal `@ConditionalOnClass`/`@ConditionalOnMissingBean`
         * jako UZYTKOWNIK auto-konfiguracji. Dzisiaj piszesz PRAWDZIWA
         * klase `@AutoConfiguration` (NIE tylko `@Configuration`) -
         * DOKLADNIE TAKA, jak setki klas w
         * `spring-boot-autoconfigure.jar`, ktore CZYTALES w Lesson04.
         */
        System.out.println("@AutoConfiguration = prawdziwa 'jednostka' auto-konfiguracji Boota - dzisiaj piszesz WLASNA, dokladnie taka jak w spring-boot-autoconfigure.jar.");

        demonstrateWritingRealAutoConfigurationClass();
        demonstrateExactFileNeededForAutomaticDiscovery();
        explainStarterModuleConvention();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@AutoConfiguration` (NIE `@Configuration`) to WLASCIWA
         *   adnotacja dla klas auto-konfiguracji - meta-oznaczona
         *   `@Configuration`, ale z DODATKOWA semantyka dla Boota
         *   (kolejnosc przetwarzania, `@AutoConfigureAfter`/`Before`).
         * - Rejestracja: plik `META-INF/spring/org.springframework.boot.
         *   autoconfigure.AutoConfiguration.imports` w `src/main/resources`
         *   (DOKLADNIE ta sama nazwa/sciezka, ktora CZYTALES w Lesson04) -
         *   JEDNA linia = JEDNA klasa auto-konfiguracji.
         * - PRAWDZIWY, publikowalny "starter" to 2 MODULY: `xxx-spring-boot-
         *   autoconfigure` (logika + `@AutoConfiguration`) + `xxx-spring-
         *   boot-starter` (PRAWIE PUSTY POM, TYLKO zaleznosci - jak
         *   widziales w Lesson03).
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    interface GreetingFormatter {
        String format(String name);
    }

    // PRAWDZIWA klasa auto-konfiguracji - TA SAMA adnotacja co w setkach klas z Lesson04.
    @AutoConfiguration
    static class GreetingAutoConfiguration {
        @Bean
        @ConditionalOnClass(String.class) // String ZAWSZE jest na classpath - symulacja "jesli biblioteka X jest obecna"
        @ConditionalOnMissingBean(GreetingFormatter.class)
        GreetingFormatter defaultGreetingFormatter() {
            return name -> "[AUTO-KONFIGURACJA] Witaj, " + name + "!";
        }
    }

    @SpringBootApplication
    static class CustomAutoConfigApp {
    }

    private static void demonstrateWritingRealAutoConfigurationClass() {
        System.out.println("\n=== WLASNA, PRAWDZIWA KLASA @AutoConfiguration ===");

        System.out.println("@AutoConfiguration jest meta-oznaczone @Configuration: "
                + AutoConfiguration.class.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomAutoConfigApp.class)
                .web(WebApplicationType.NONE)
                .sources(GreetingAutoConfiguration.class)
                .run();
        try {
            GreetingFormatter formatter = context.getBean(GreetingFormatter.class);
            System.out.println(formatter.format("Ala"));
            System.out.println("-> ZACHOWUJE sie DOKLADNIE jak auto-konfiguracja z Lesson04 (bo TAKA WLASNIE jest) - jedyna roznica to REJESTRACJA (nastepny fragment).");
        } finally {
            context.close();
        }
    }

    private static void demonstrateExactFileNeededForAutomaticDiscovery() throws IOException {
        System.out.println("\n=== DOKLADNY PLIK POTRZEBNY DO AUTOMATYCZNEGO WYKRYCIA (BEZ .sources(...)) ===");

        String fullyQualifiedName = GreetingAutoConfiguration.class.getName().replace('$', '.');
        String fileContent = "# Ten plik REJESTRUJE nasza klase jako auto-konfiguracje - Boot czyta go PRZY KAZDYM starcie\n"
                + fullyQualifiedName + "\n";

        Path tempDir = Files.createTempDirectory("lesson15-custom-autoconfig");
        Path importsFile = tempDir.resolve("org.springframework.boot.autoconfigure.AutoConfiguration.imports");
        Files.writeString(importsFile, fileContent, StandardCharsets.UTF_8);

        System.out.println("Docelowa sciezka W PRAWDZIWYM projekcie: src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports");
        System.out.println("Wygenerowana zawartosc (zapisana TEZ do pliku tymczasowego " + importsFile + "):");
        System.out.println(Files.readString(importsFile));
        System.out.println("-> GDYBY ten plik byl w src/main/resources tego projektu, Boot ZNALAZLBY GreetingAutoConfiguration SAM,");
        System.out.println("   BEZ jakiegokolwiek '.sources(...)' - DOKLADNIE tak, jak 153 klasy odczytane w Lesson04 z prawdziwego JAR-a Boota.");
    }

    private static void explainStarterModuleConvention() {
        System.out.println("\n=== KONWENCJA PRAWDZIWEGO, PUBLIKOWALNEGO STARTERA: 2 MODULY ===");

        System.out.println("Modul 1: 'javaquest-greeting-spring-boot-autoconfigure' - zawiera GreetingAutoConfiguration.class + plik .imports (co widziales wyzej).");
        System.out.println("Modul 2: 'javaquest-greeting-spring-boot-starter' - PRAWIE PUSTY pom.xml, TYLKO 1 zaleznosc do Modulu 1 (jak `spring-boot-starter-web` z Lesson03).");
        System.out.println("-> uzytkownik Twojego startera dodaje TYLKO Modul 2 - Maven SCIAGA Modul 1 transytywnie, Boot ZNAJDUJE auto-konfiguracje AUTOMATYCZNIE.");
        System.out.println("   To DOKLADNIE ten sam wzorzec co `spring-boot-starter-web` + `spring-boot-autoconfigure` (prawdziwe moduly samego Springa).");
    }
}
