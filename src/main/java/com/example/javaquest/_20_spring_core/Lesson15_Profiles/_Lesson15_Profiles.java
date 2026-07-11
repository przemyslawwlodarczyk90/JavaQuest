package com.example.javaquest._20_spring_core.Lesson15_Profiles;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public class _Lesson15_Profiles {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: PROFILE (@Profile) ===");

        /*
         * ============================================================
         * 📦 RÓZNE BEANY DLA RÓZNYCH SRODOWISK
         * ============================================================
         * Realna aplikacja czesto potrzebuje INNEGO zachowania w
         * zaleznosci od srodowiska - lokalny development (gadatliwe
         * logi, baza in-memory) vs produkcja (zoptymalizowane,
         * prawdziwa baza). `@Profile("nazwa")` na beanie oznacza:
         * "stworz mnie TYLKO, gdy TEN profil jest aktywny". Aktywacja
         * przez `Environment.setActiveProfiles(...)` (dzis, na tym
         * niskim poziomie) lub `spring.profiles.active` we
         * `application.yml` (pelne potraktowanie w
         * `_21_spring_boot/Lesson06`).
         */
        System.out.println("@Profile(\"nazwa\") = 'stworz mnie TYLKO gdy TEN profil jest aktywny' - inny bean dla dev, inny dla prod.");

        demonstrateDevProfileActive();
        demonstrateProdProfileActive();
        demonstrateDefaultProfileFallbackWhenNoneActive();
        demonstrateMultipleProfilesActiveSimultaneously();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Profile("x")` na beanie = warunkowa rejestracja zalezna od
         *   AKTYWNYCH profili srodowiska.
         * - Profil "default" to SPECJALNA nazwa - bean nim oznaczony
         *   powstaje TYLKO gdy ZADEN INNY profil nie jest aktywny.
         * - Profile SIE SUMUJA (union) - mozna aktywowac WIELE naraz,
         *   wszystkie pasujace beany powstana RAZEM.
         * - W Spring Boot (`_21_spring_boot/Lesson06`) aktywacja
         *   odbywa sie przez `application-{profil}.yml` +
         *   `spring.profiles.active` - TA SAMA idea, wygodniejszy zapis.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    interface Greeting {
        String message();
    }

    static class DevGreeting implements Greeting {
        @Override
        public String message() {
            return "Wersja DEV - szczegolowe logi wlaczone, dane testowe";
        }
    }

    static class ProdGreeting implements Greeting {
        @Override
        public String message() {
            return "Wersja PROD - zoptymalizowana, minimalne logowanie";
        }
    }

    static class DefaultGreeting implements Greeting {
        @Override
        public String message() {
            return "Domyslny profil - ZADEN profil nie zostal jawnie aktywowany";
        }
    }

    static class QaOnlyMarker {
        String info() {
            return "Bean specyficzny dla profilu 'qa' - utworzony";
        }
    }

    @Configuration
    static class ProfileConfig {
        @Bean
        @Profile("dev")
        Greeting devGreeting() {
            return new DevGreeting();
        }

        @Bean
        @Profile("prod")
        Greeting prodGreeting() {
            return new ProdGreeting();
        }

        @Bean
        @Profile("default")
        Greeting defaultGreeting() {
            return new DefaultGreeting();
        }

        @Bean
        @Profile("qa")
        QaOnlyMarker qaOnlyMarker() {
            return new QaOnlyMarker();
        }
    }

    private static AnnotationConfigApplicationContext createContextWithProfiles(String... profiles) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        if (profiles.length > 0) {
            context.getEnvironment().setActiveProfiles(profiles);
        }
        context.register(ProfileConfig.class);
        context.refresh();
        return context;
    }

    private static void demonstrateDevProfileActive() {
        System.out.println("\n=== PROFIL 'dev' AKTYWNY ===");

        try (AnnotationConfigApplicationContext context = createContextWithProfiles("dev")) {
            Greeting greeting = context.getBean(Greeting.class);
            System.out.println(greeting.message());
            System.out.println("-> TYLKO DevGreeting powstal - ProdGreeting/DefaultGreeting NIE ISTNIEJA w tym kontekscie w ogole.");
        }
    }

    private static void demonstrateProdProfileActive() {
        System.out.println("\n=== PROFIL 'prod' AKTYWNY ===");

        try (AnnotationConfigApplicationContext context = createContextWithProfiles("prod")) {
            Greeting greeting = context.getBean(Greeting.class);
            System.out.println(greeting.message());
            System.out.println("-> TA SAMA klasa Greeting, ale TERAZ dostajemy ZUPELNIE INNA implementacje - o TO chodzi w profilach.");
        }
    }

    private static void demonstrateDefaultProfileFallbackWhenNoneActive() {
        System.out.println("\n=== ZADEN PROFIL NIE AKTYWNY: FALLBACK NA 'default' ===");

        try (AnnotationConfigApplicationContext context = createContextWithProfiles()) {
            Greeting greeting = context.getBean(Greeting.class);
            System.out.println(greeting.message());
            System.out.println("-> profil 'default' to SPECJALNA nazwa Springa dla 'brak jawnie aktywowanego profilu' - dziala TYLKO w tej sytuacji.");
        }
    }

    private static void demonstrateMultipleProfilesActiveSimultaneously() {
        System.out.println("\n=== KILKA PROFILI NARAZ: 'dev' + 'qa' ===");

        try (AnnotationConfigApplicationContext context = createContextWithProfiles("dev", "qa")) {
            Greeting greeting = context.getBean(Greeting.class);
            QaOnlyMarker qaMarker = context.getBean(QaOnlyMarker.class);
            System.out.println(greeting.message());
            System.out.println(qaMarker.info());
            System.out.println("-> OBA beany (gated przez 'dev' i przez 'qa') powstaly RAZEM - profile SIE SUMUJA, to NIE jest wybor 'albo-albo'.");
        }
    }
}
