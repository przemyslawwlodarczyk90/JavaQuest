package com.example.javaquest._21_spring_boot.Lesson16_SpringBootCapstone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class _Lesson16_SpringBootCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: KAPSZTON - 'JAVAQUEST NOTES SERVICE' ===");

        /*
         * ============================================================
         * 📦 CALY ROZDZIAL W JEDNYM, DZIALAJACYM MINI-SERWISIE
         * ============================================================
         * Mala aplikacja Boot LACZACA: startery (web+actuator, Lesson03),
         * auto-konfiguracje (Lesson04, dzialajaca "pod spodem" bez
         * Twojej wiedzy), `@ConfigurationProperties` (Lesson08), profile
         * (Lesson06), `CommandLineRunner` (Lesson07), Actuator + WLASNY
         * `HealthIndicator` (Lesson12) - WSZYSTKO w JEDNYM, REALNIE
         * dzialajacym serwisie REST z prawdziwym, wbudowanym Tomcatem.
         */
        System.out.println("Mini-serwis notatek laczacy WSZYSTKIE mechanizmy tego rozdzialu - z prawdziwym, wbudowanym Tomcatem.");

        runScenario("dev");
        runScenario("prod");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * Przeszedles od "co to jest Spring Boot" (Lesson01, porownanie
         * z `_20_spring_core`) przez startery, auto-konfiguracje,
         * konfiguracje (properties/yaml/@ConfigurationProperties),
         * profile, runnery, logowanie, obsluge bledow, Actuator,
         * observability, budowanie jara/natywny obraz, az po WLASNA
         * auto-konfiguracje. Kolejny krok: `_22_spring_web` - REST API
         * NA SPRINGU (masz juz `_18_rest_api` protokolowo i
         * `_07_servlets` na surowym Servlet API).
         */
        System.out.println("\n=== KONIEC LEKCJI 16, KONIEC ROZDZIALU _21_spring_boot ===");
    }

    record Note(String id, String content) {
    }

    @ConfigurationProperties(prefix = "javaquest.notes")
    public static class NotesProperties {
        private int maxNotes = 100;
        private String welcomeMessage = "Witaj w JavaQuest Notes";

        public int getMaxNotes() {
            return maxNotes;
        }

        public void setMaxNotes(int maxNotes) {
            this.maxNotes = maxNotes;
        }

        public String getWelcomeMessage() {
            return welcomeMessage;
        }

        public void setWelcomeMessage(String welcomeMessage) {
            this.welcomeMessage = welcomeMessage;
        }
    }

    @Repository
    static class NotesRepository {
        private final List<Note> notes = new ArrayList<>();
        private final NotesProperties properties;

        NotesRepository(NotesProperties properties) {
            this.properties = properties;
        }

        void add(Note note) {
            if (notes.size() >= properties.getMaxNotes()) {
                throw new IllegalStateException("Osiagnieto limit notatek: " + properties.getMaxNotes());
            }
            notes.add(note);
        }

        List<Note> findAll() {
            return List.copyOf(notes);
        }
    }

    @RestController
    static class NotesController {
        private final NotesRepository repository;
        private final NotesProperties properties;

        NotesController(NotesRepository repository, NotesProperties properties) {
            this.repository = repository;
            this.properties = properties;
        }

        @GetMapping("/notes")
        List<Note> listNotes() {
            return repository.findAll();
        }

        @GetMapping("/welcome")
        String welcome() {
            return properties.getWelcomeMessage();
        }
    }

    @Component
    @Profile("dev")
    static class DevSeedDataRunner implements CommandLineRunner {
        private final NotesRepository repository;

        DevSeedDataRunner(NotesRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... args) {
            repository.add(new Note("1", "[DEV] Notatka testowa numer 1"));
            repository.add(new Note("2", "[DEV] Notatka testowa numer 2"));
            System.out.println("  [DevSeedDataRunner] zaladowano 2 notatki testowe (profil dev)");
        }
    }

    @Component
    @Profile("prod")
    static class ProdStartupRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("  [ProdStartupRunner] start bez danych testowych (profil prod) - czekam na PRAWDZIWE dane");
        }
    }

    @Component
    static class NotesHealthIndicator implements HealthIndicator {
        private final NotesRepository repository;
        private final NotesProperties properties;

        NotesHealthIndicator(NotesRepository repository, NotesProperties properties) {
            this.repository = repository;
            this.properties = properties;
        }

        @Override
        public Health health() {
            int used = repository.findAll().size();
            int max = properties.getMaxNotes();
            Health.Builder builder = used < max ? Health.up() : Health.down();
            return builder.withDetail("notatki", used + "/" + max).build();
        }
    }

    @SpringBootApplication
    @EnableConfigurationProperties(NotesProperties.class)
    static class NotesApp {
    }

    private static String httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static void runScenario(String profile) throws Exception {
        System.out.println("\n" + "=".repeat(20) + " SCENARIUSZ: PROFIL '" + profile + "' " + "=".repeat(20));

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("management.endpoints.web.exposure.include", "health");
        props.setProperty("management.endpoint.health.show-details", "always");
        props.setProperty("javaquest.notes.max-notes", "5");
        props.setProperty("javaquest.notes.welcome-message", "Witaj w JavaQuest Notes (profil: " + profile + ")");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NotesApp.class)
                .profiles(profile)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("GET /welcome -> " + httpGet(port, "/welcome"));
            System.out.println("GET /notes -> " + httpGet(port, "/notes"));
            System.out.println("GET /actuator/health -> " + httpGet(port, "/actuator/health"));
        } finally {
            context.close();
        }
    }
}
