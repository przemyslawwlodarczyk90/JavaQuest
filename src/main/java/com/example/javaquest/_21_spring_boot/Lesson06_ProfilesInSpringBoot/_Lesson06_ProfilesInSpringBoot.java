package com.example.javaquest._21_spring_boot.Lesson06_ProfilesInSpringBoot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

public class _Lesson06_ProfilesInSpringBoot {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: PROFILE W SPRING BOOT ===");

        /*
         * ============================================================
         * 📦 TA SAMA IDEA CO `_20_spring_core/Lesson15`, WYGODNIEJSZY ZAPIS
         * ============================================================
         * `@Profile` na beanie dziala DOKLADNIE tak samo jak w
         * `_20_spring_core`. Boot DOKLADA: (1) `SpringApplicationBuilder
         * .profiles(...)`/`--spring.profiles.active=...` jako WYGODNE
         * sposoby aktywacji (zamiast recznego
         * `environment.setActiveProfiles(...)`), (2) AUTOMATYCZNE
         * ladowanie `application-{profil}.properties`/`.yml`
         * (POGLEBIENIE Lesson05) - kazdy profil MOZE miec WLASNY plik
         * konfiguracyjny, ladowany TYLKO gdy TEN profil jest aktywny.
         */
        System.out.println("Ta sama idea co _20_spring_core/Lesson15 - Boot dodaje wygodniejsza aktywacje + auto-ladowanie application-{profil}.yml.");

        demonstrateActivateProfileViaBuilder();
        demonstrateActivateProfileViaCommandLineArgument();
        demonstrateApplicationProfileSpecificFilesConceptually();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Profile` na beanie - IDENTYCZNIE jak w `_20_spring_core/
         *   Lesson15`.
         * - `SpringApplicationBuilder.profiles(...)` - programowa
         *   aktywacja (jak dzisiaj).
         * - `--spring.profiles.active=dev` jako argument `main(String[])` -
         *   NAJCZESTSZY sposob w PRAWDZIWYM wdrożeniu (np. w skrypcie
         *   startowym `java -jar app.jar --spring.profiles.active=prod`).
         * - `application-{profil}.properties`/`.yml` - profil-specyficzne
         *   PLIKI, ladowane AUTOMATYCZNIE, gdy TEN profil jest aktywny -
         *   NIE musisz ich RECZNIE wskazywac (`@PropertySource`).
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    interface DataSource {
        String describe();
    }

    static class DevDataSource implements DataSource {
        @Override
        public String describe() {
            return "H2 in-memory (dev) - baza resetuje sie przy KAZDYM starcie";
        }
    }

    static class ProdDataSource implements DataSource {
        @Override
        public String describe() {
            return "PostgreSQL (prod, symulacja) - trwala baza produkcyjna";
        }
    }

    @SpringBootApplication
    static class ProfileApp {
        @Bean
        @Profile("dev")
        DataSource devDataSource() {
            return new DevDataSource();
        }

        @Bean
        @Profile("prod")
        DataSource prodDataSource() {
            return new ProdDataSource();
        }
    }

    private static void demonstrateActivateProfileViaBuilder() {
        System.out.println("\n=== AKTYWACJA PRZEZ SpringApplicationBuilder.profiles(...) ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ProfileApp.class)
                .web(WebApplicationType.NONE)
                .profiles("dev")
                .run();
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            System.out.println("Aktywne profile: " + Arrays.toString(context.getEnvironment().getActiveProfiles()));
            System.out.println(dataSource.describe());
        } finally {
            context.close();
        }
    }

    private static void demonstrateActivateProfileViaCommandLineArgument() {
        System.out.println("\n=== AKTYWACJA PRZEZ ARGUMENT --spring.profiles.active=... (NAJCZESTSZE W PRAKTYCE) ===");

        // W prawdziwym wdrozeniu: `java -jar app.jar --spring.profiles.active=prod` -
        // symulujemy TO SAMO, przekazujac argument recznie do run(...).
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ProfileApp.class)
                .web(WebApplicationType.NONE)
                .run("--spring.profiles.active=prod");
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            System.out.println("Aktywne profile: " + Arrays.toString(context.getEnvironment().getActiveProfiles()));
            System.out.println(dataSource.describe());
            System.out.println("-> TAK wlacza sie profil w REALNYM wdrozeniu - argument linii polecen, BEZ zmiany kodu Javy.");
        } finally {
            context.close();
        }
    }

    private static void demonstrateApplicationProfileSpecificFilesConceptually() {
        System.out.println("\n=== application-{profil}.yml - AUTOMATYCZNE LADOWANIE PLIKOW PER PROFIL ===");

        System.out.println("Gdyby w src/main/resources istnialy pliki:");
        System.out.println("  application-dev.properties   (np. javaquest.db.url=jdbc:h2:mem:dev)");
        System.out.println("  application-prod.properties  (np. javaquest.db.url=jdbc:postgresql://prod-host/db)");
        System.out.println("To PRZY aktywnym profilu 'dev', Boot ladowalby AUTOMATYCZNIE 'application-dev.properties' OBOK glownego 'application.properties'");
        System.out.println("(wartosci z pliku PROFILOWEGO NADPISUJA te z glownego pliku dla TYCH SAMYCH kluczy) - BEZ zadnej jawnej konfiguracji.");
        System.out.println("-> to POGLEBIENIE Lesson05 - profil DECYDUJE, KTORY DODATKOWY plik wlaczyc, NA WIERZCH glownego application.properties.");
    }
}
