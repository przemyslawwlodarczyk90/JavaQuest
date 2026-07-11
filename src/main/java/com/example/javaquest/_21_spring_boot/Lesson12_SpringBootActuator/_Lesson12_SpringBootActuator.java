package com.example.javaquest._21_spring_boot.Lesson12_SpringBootActuator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson12_SpringBootActuator {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: SPRING BOOT ACTUATOR ===");

        /*
         * ============================================================
         * 📦 ENDPOINTY OPERACYJNE "ZA DARMO"
         * ============================================================
         * `spring-boot-starter-actuator` (DODANY do `pom.xml` w tym
         * kroku) dodaje GOTOWE endpointy HTTP do INSPEKCJI dzialajacej
         * aplikacji - `/actuator/health` (czy aplikacja ZYJE i moze
         * przyjmowac ruch?), `/actuator/info` (metadane aplikacji),
         * `/actuator/metrics` (liczniki/gauge - pamiec, watki, itd.).
         * TO WSZYSTKO dziala BEZ napisania choc jednej linii kodu -
         * DOKLADNIE ta sama filozofia co auto-konfiguracja (Lesson04).
         */
        System.out.println("spring-boot-starter-actuator = gotowe endpointy operacyjne (/health, /info, /metrics) BEZ napisania linii kodu.");

        demonstrateHealthEndpoint();
        demonstrateMetricsEndpointRequiresExplicitExposure();
        demonstrateCustomHealthIndicator();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `/actuator/health` - WYSTAWIONY DOMYSLNIE - "czy aplikacja
         *   jest zdrowa?" (status UP/DOWN) - uzywany przez
         *   orkiestratory (Kubernetes liveness/readiness probes).
         * - Wiekszosc INNYCH endpointow (`/metrics`, `/env`, `/beans`)
         *   MUSI byc JAWNIE wystawiona przez
         *   `management.endpoints.web.exposure.include=...` - ZE
         *   WZGLEDOW BEZPIECZENSTWA (niektore ujawniaja wrazliwe dane).
         * - WLASNY `HealthIndicator` pozwala DOLACZYC WLASNA logike
         *   "zdrowia" (np. "czy baza danych odpowiada?") do standardowego
         *   `/actuator/health`.
         * - Nastepna lekcja (`Lesson13_ObservabilityMicrometerAndTracing`)
         *   pokaze, jak Actuator LACZY sie z Micrometer (metryki) i
         *   distributed tracing.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    @SpringBootApplication
    static class ActuatorApp {
    }

    private static String httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return "status=" + response.statusCode() + ", body=" + response.body();
    }

    private static void demonstrateHealthEndpoint() throws Exception {
        System.out.println("\n=== /actuator/health - WYSTAWIONY DOMYSLNIE ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ActuatorApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /actuator/health -> " + httpGet(port, "/actuator/health"));
            System.out.println("-> status 'UP' oznacza: aplikacja zyje i MOZE przyjmowac ruch - TEN endpoint jest WYSTAWIONY BEZ zadnej konfiguracji.");
        } finally {
            context.close();
        }
    }

    private static void demonstrateMetricsEndpointRequiresExplicitExposure() throws Exception {
        System.out.println("\n=== /actuator/metrics - WYMAGA JAWNEGO WYSTAWIENIA (BEZPIECZENSTWO) ===");

        Properties propsWithoutExposure = new Properties();
        propsWithoutExposure.setProperty("server.port", "0");

        ConfigurableApplicationContext contextA = new SpringApplicationBuilder(ActuatorApp.class)
                .properties(propsWithoutExposure)
                .run();
        try {
            int port = contextA.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("--- BEZ jawnego wystawienia ---");
            System.out.println("GET /actuator/metrics -> " + httpGet(port, "/actuator/metrics"));
        } finally {
            contextA.close();
        }

        Properties propsWithExposure = new Properties();
        propsWithExposure.setProperty("server.port", "0");
        propsWithExposure.setProperty("management.endpoints.web.exposure.include", "health,info,metrics");

        ConfigurableApplicationContext contextB = new SpringApplicationBuilder(ActuatorApp.class)
                .properties(propsWithExposure)
                .run();
        try {
            int port = contextB.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("--- Z jawnym 'management.endpoints.web.exposure.include=health,info,metrics' ---");
            String result = httpGet(port, "/actuator/metrics");
            System.out.println("GET /actuator/metrics -> status=" + result.substring(0, result.indexOf(",")).replace("status=", "") + " (lista dostepnych metryk, skrocono)");
            System.out.println("-> DOPIERO PO jawnym wystawieniu endpoint jest DOSTEPNY - Boot swiadomie NIE ujawnia wszystkiego 'domyslnie'.");
        } finally {
            contextB.close();
        }
    }

    static class DiskSpaceCheck {
        boolean hasEnoughSpace() {
            return true; // symulacja - w realnej aplikacji sprawdzalby np. File.getUsableSpace()
        }
    }

    static class CustomHealthIndicator implements org.springframework.boot.actuate.health.HealthIndicator {
        private final DiskSpaceCheck diskSpaceCheck;

        CustomHealthIndicator(DiskSpaceCheck diskSpaceCheck) {
            this.diskSpaceCheck = diskSpaceCheck;
        }

        @Override
        public org.springframework.boot.actuate.health.Health health() {
            if (diskSpaceCheck.hasEnoughSpace()) {
                return org.springframework.boot.actuate.health.Health.up()
                        .withDetail("wolneMiejsceNaDysku", "wystarczajace (symulacja)")
                        .build();
            }
            return org.springframework.boot.actuate.health.Health.down()
                    .withDetail("wolneMiejsceNaDysku", "KRYTYCZNIE MALO")
                    .build();
        }
    }

    @SpringBootApplication
    static class CustomHealthApp {
        @org.springframework.context.annotation.Bean
        DiskSpaceCheck diskSpaceCheck() {
            return new DiskSpaceCheck();
        }

        @org.springframework.context.annotation.Bean
        CustomHealthIndicator customHealthIndicator(DiskSpaceCheck diskSpaceCheck) {
            return new CustomHealthIndicator(diskSpaceCheck);
        }
    }

    private static void demonstrateCustomHealthIndicator() throws Exception {
        System.out.println("\n=== WLASNY HealthIndicator - DOLACZONY DO /actuator/health ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("management.endpoint.health.show-details", "always");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomHealthApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /actuator/health -> " + httpGet(port, "/actuator/health"));
            System.out.println("-> WLASNY bean 'customHealthIndicator' zostal AUTOMATYCZNIE dolaczony do wyniku (widoczny wyzej jako klucz 'custom' w 'components') -");
            System.out.println("   wystarczy zaimplementowac interfejs HealthIndicator i zarejestrowac bean, ZERO dodatkowej konfiguracji.");
        } finally {
            context.close();
        }
    }
}
