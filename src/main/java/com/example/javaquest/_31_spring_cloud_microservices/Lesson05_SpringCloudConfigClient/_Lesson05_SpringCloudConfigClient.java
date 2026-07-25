package com.example.javaquest._31_spring_cloud_microservices.Lesson05_SpringCloudConfigClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson05_SpringCloudConfigClient {

    // WAZNE: obie klasy Z ADNOTACJA zrodlowa zyja W TYM SAMYM pliku/pakiecie - implicit
    // @ComponentScan (niesiony przez @SpringBootApplication) ZNALAZLBY sasiednia klase
    // (RemoteSettings) I probowalby JA UTWORZYC TEZ W kontekscie Config Servera, gdzie
    // "greeting"/"orders.max-items" NIE ISTNIEJA - dokladnie TA SAMA pulapka udokumentowana W
    // _20_spring_core/_23_spring_data_jpa. Naprawa: @Configuration+@EnableAutoConfiguration (BEZ
    // component-scan) + jawna rejestracja przez @Bean, ZAMIAST @SpringBootApplication+@Component.
    @Configuration
    @EnableAutoConfiguration
    @EnableConfigServer
    static class ConfigServerApp {
    }

    @Configuration
    @EnableAutoConfiguration
    static class ClientApp {
        static class RemoteSettings {
            @Value("${greeting}")
            String greeting;

            @Value("${orders.max-items}")
            int maxItems;
        }

        @Bean
        RemoteSettings remoteSettings() {
            return new RemoteSettings();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== LEKCJA 5: Klient Config Servera - PRAWDZIWE zdalne pobranie konfiguracji ===");

        /*
         * ============================================================
         * 📦 KLIENT Config Servera - `spring.config.import=configserver:`
         * ============================================================
         * Od Spring Boot 2.4 klient odczytuje konfiguracje ZDALNA
         * PRZEZ te sama, ogolna mechanike CO pliki YAML lokalne -
         * wpis `spring.config.import=configserver:http://host:port`
         * (WCZESNIEJSZY, STARSZY styl: `spring.cloud.config.uri` BEZ
         * `spring.config.import` - WCIAZ dziala, ale NOWY styl jest
         * dzisiejszym standardem).
         *
         * WAZNE: `spring.cloud.config.enabled=false` jest USTAWIONE
         * GLOBALNIE W tym projekcie (`application.properties`, ten
         * sam powod CO `eureka.client.enabled=false` W Lesson03) -
         * TA lekcja jawnie PRZYWRACA `true` przez argument wiersza
         * polecen (NAJWYZSZY priorytet W Springu - Lesson03 pokazal,
         * ze `SpringApplicationBuilder.properties(...)` MA ZA NISKI
         * priorytet, zeby PRZEBIC classpath'owy `application.properties`).
         */
        System.out.println("Klient: spring.config.import=configserver:http://host:port - odczytuje zdalna konfiguracje TA SAMA mechanika CO lokalny application.yml.");

        Path configRepo = Files.createTempDirectory("lesson05-config-repo");
        writeConfigFile(configRepo, "orders-service.yml", """
                greeting: Witaj z Config Servera!
                orders:
                  max-items: 50
                """);
        System.out.println("Utworzono 'repozytorium' (backend 'native') config-servera W: " + configRepo);

        ConfigurableApplicationContext serverContext = null;
        ConfigurableApplicationContext clientContext = null;
        try {
            serverContext = startConfigServer(configRepo);
            int serverPort = Integer.parseInt(serverContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Config Server wystartowal NA porcie " + serverPort + " (backend: native, search-locations: " + configRepo + ").");

            demonstrateRawRestApi(serverPort);

            clientContext = startClient(serverPort);
            demonstrateClientReceivedRemoteConfig(clientContext);
        } finally {
            if (clientContext != null) {
                clientContext.close();
            }
            if (serverContext != null) {
                serverContext.close();
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Backend 'native' = pliki NA dysku (temp katalog W tym demo,
         *   W PRODUKCJI zwykle 'git' - Lesson04).
         * - `GET /{application}/{profile}` NA Config Serverze zwraca
         *   SCALONA konfiguracje - MOZNA ja odpytac ZWYKLYM HTTP.
         * - `spring.config.import=configserver:...` NA kliencie -
         *   `@Value`/`@ConfigurationProperties` DZIALAJA IDENTYCZNIE
         *   jak Z lokalnego pliku - klient W OGOLE "nie widzi" roznicy.
         * - `spring.cloud.config.enabled=false` GLOBALNIE W tym
         *   projekcie - kazda lekcja Config Client MUSI jawnie
         *   PRZYWROCIC `true` (argument wiersza polecen, NIE
         *   `.properties()`).
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void writeConfigFile(Path dir, String fileName, String content) throws IOException {
        Files.writeString(dir.resolve(fileName), content);
    }

    private static ConfigurableApplicationContext startConfigServer(Path configRepo) {
        String searchLocation = configRepo.toUri().toString();
        return new SpringApplicationBuilder(ConfigServerApp.class)
                .run(
                        "--spring.application.name=config-server",
                        "--server.port=0",
                        "--spring.profiles.active=native",
                        "--spring.cloud.config.server.native.search-locations=" + searchLocation,
                        "--logging.level.root=WARN");
    }

    private static ConfigurableApplicationContext startClient(int configServerPort) {
        return new SpringApplicationBuilder(ClientApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--spring.cloud.config.enabled=true",
                        "--spring.config.import=optional:configserver:http://localhost:" + configServerPort,
                        "--logging.level.root=WARN");
    }

    private static void demonstrateRawRestApi(int serverPort) throws IOException, InterruptedException {
        System.out.println("\n--- Odpytanie SUROWEGO REST API Config Servera (bez zadnego klienta Springa) ---");

        var client = java.net.http.HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder(
                        java.net.URI.create("http://localhost:" + serverPort + "/orders-service/default"))
                .GET().build();
        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        System.out.println("GET /orders-service/default -> status " + response.statusCode());
        System.out.println("Odpowiedz (fragment): " + response.body().substring(0, Math.min(200, response.body().length())) + "...");
    }

    private static void demonstrateClientReceivedRemoteConfig(ConfigurableApplicationContext clientContext) {
        System.out.println("\n--- Klient PO STARCIE - odczytana ZDALNIE konfiguracja ---");

        ClientApp.RemoteSettings settings = clientContext.getBean(ClientApp.RemoteSettings.class);
        System.out.println("@Value(\"${greeting}\") -> \"" + settings.greeting + "\"");
        System.out.println("@Value(\"${orders.max-items}\") -> " + settings.maxItems);
        System.out.println("Klient NIE MIAL tych wartosci W WLASNYM application.properties - przyszly Z ZDALNEGO Config Servera.");
    }
}
