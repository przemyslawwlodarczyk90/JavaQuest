package com.example.javaquest._31_spring_cloud_microservices.Lesson06_ApiGatewayIntro;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

public class _Lesson06_ApiGatewayIntro {

    // WAZNE: `OrdersController` MUSI byc SIOSTRZANA (NIE zagniezdzona WEWNATRZ `BackendApp`)
    // klasa zagniezdzona - Spring `@Configuration` PRZETWARZA WLASNE klasy CZLONKOWSKIE
    // (`getDeclaredClasses()`) jako DODATKOWYCH kandydatow konfiguracji/komponentow, JESLI maja
    // stereotyp (`@RestController` = `@Component`), NIEZALEZNIE OD `@ComponentScan` (ktorego TU
    // W OGOLE nie uzywamy!). Zagniezdzenie `OrdersController` WEWNATRZ `BackendApp` dawalo
    // PODWOJNA rejestracje (RAZ przez jawny `@Bean`, RAZ przez automatyczne przetwarzanie klas
    // czlonkowskich) -> `IllegalStateException: Ambiguous mapping` - zweryfikowane empirycznie.
    @RestController
    static class OrdersController {
        @GetMapping("/api/orders/{id}")
        String getOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            return "Zamowienie #" + id + " (odpowiedz Z PRAWDZIWEGO backendu)";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class BackendApp {
        @Bean
        OrdersController ordersController() {
            return new OrdersController();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class GatewayApp {
        @Bean
        RouterFunction<ServerResponse> routes(BackendPortHolder backendPortHolder) {
            return RouterFunctions.route()
                    .GET("/api/orders/**", http("http://localhost:" + backendPortHolder.port()))
                    .build();
        }

        @Bean
        BackendPortHolder backendPortHolder() {
            return new BackendPortHolder(BACKEND_PORT[0]);
        }
    }

    record BackendPortHolder(int port) {
    }

    // Prosty "kanal" do przekazania portu backendu DO konfiguracji Gateway - alternatywa DLA
    // wlasciwosci Spring (`@Value`), wystarczajaca DLA tego demo W JEDNYM main().
    private static final int[] BACKEND_PORT = new int[1];

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: Spring Cloud Gateway (MVC) - wprowadzenie ===");

        /*
         * ============================================================
         * 📦 GATEWAY - JEDEN punkt wejscia PRZED wieloma serwisami
         * ============================================================
         * Bez Gateway KAZDY klient MUSIALBY znac adresy WSZYSTKICH
         * mikroserwisow Z OSOBNA. Gateway TO POJEDYNCZY, publiczny
         * punkt wejscia, KTORY PRZEKIEROWUJE (routes) zadania DO
         * WLASCIWEGO serwisu NA PODSTAWIE sciezki/naglowkow.
         *
         * WAZNA decyzja techniczna (patrz `pom.xml`): uzywamy
         * `spring-cloud-starter-gateway-mvc` (wariant Servlet/blocking,
         * wprowadzony W Spring Cloud 2023.0.3), NIE klasycznego
         * `spring-cloud-starter-gateway` (WebFlux/reactive) - TEN
         * DRUGI ma auto-konfiguracje, KTORA fail-fast rzuca blad
         * "Spring MVC found on classpath, which is incompatible with
         * Spring Cloud Gateway" W KAZDEJ innej lekcji tego kursu (caly
         * kurs jest Servlet-owy/Tomcat) - zweryfikowane empirycznie
         * przy pisaniu tego rozdzialu. "gateway-mvc" wspolistnieje Z
         * Tomcatem bez konfliktu.
         *
         * `spring.cloud.gateway.mvc.enabled=false` jest USTAWIONE
         * GLOBALNIE (Gateway MVC rejestruje WLASNE filtry naglowkow
         * HTTP NIEZALEZNIE OD tego, czy zdefiniowano jakiekolwiek
         * trasy - bez wyciszenia PSULOBY to inne lekcje, np.
         * `_24_spring_security/Lesson17`, zweryfikowane empirycznie).
         * TA lekcja jawnie PRZYWRACA `true`.
         */
        System.out.println("Gateway = jeden punkt wejscia, routing na podstawie sciezki. Uzywamy 'gateway-mvc' (Servlet), nie 'gateway' (WebFlux) - zgodnosc z Tomcatem calego kursu.");

        ConfigurableApplicationContext backendContext = null;
        ConfigurableApplicationContext gatewayContext = null;
        try {
            backendContext = startBackend();
            BACKEND_PORT[0] = Integer.parseInt(backendContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Backend ('orders-service') wystartowal NA porcie " + BACKEND_PORT[0] + ".");

            gatewayContext = startGateway();
            int gatewayPort = Integer.parseInt(gatewayContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Gateway wystartowal NA porcie " + gatewayPort + ".");

            demonstrateDirectBackendCall(BACKEND_PORT[0]);
            demonstrateThroughGateway(gatewayPort);
        } finally {
            if (gatewayContext != null) {
                gatewayContext.close();
            }
            if (backendContext != null) {
                backendContext.close();
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `RouterFunctions.route().GET(sciezka, http(url)).build()` -
         *   deklaratywne trasowanie (routing) BEZ pisania wlasnego
         *   kodu proxy.
         * - Klient odpytuje TYLKO Gateway - NIE MUSI znac adresu
         *   backendu.
         * - Lesson07 pokaze filtry (naglowki/przepisywanie sciezek),
         *   Lesson08 - load balancing MIEDZY WIELOMA instancjami
         *   backendu.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static ConfigurableApplicationContext startBackend() {
        return new SpringApplicationBuilder(BackendApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--logging.level.root=WARN");
    }

    private static ConfigurableApplicationContext startGateway() {
        return new SpringApplicationBuilder(GatewayApp.class)
                .run(
                        "--spring.application.name=api-gateway",
                        "--server.port=0",
                        "--spring.cloud.gateway.mvc.enabled=true",
                        "--logging.level.root=WARN");
    }

    private static void demonstrateDirectBackendCall(int backendPort) throws Exception {
        System.out.println("\n--- Bezposrednie wywolanie backendu (BEZ Gateway) ---");
        String body = httpGet(backendPort, "/api/orders/42");
        System.out.println("GET localhost:" + backendPort + "/api/orders/42 -> " + body);
    }

    private static void demonstrateThroughGateway(int gatewayPort) throws Exception {
        System.out.println("\n--- TO SAMO zadanie PRZEZ Gateway (klient NIE ZNA adresu backendu) ---");
        String body = httpGet(gatewayPort, "/api/orders/42");
        System.out.println("GET localhost:" + gatewayPort + "/api/orders/42 -> " + body);
        System.out.println("Gateway PRZEKAZALO zadanie NA backend (BEZ zmiany sciezki W tym prostym demo) I zwrocilo odpowiedz - klient wolal TYLKO Gateway.");
        System.out.println("Lesson07 pokaze filtry, KTORE MOGA przepisywac sciezke/naglowki PO drodze.");
    }

    private static String httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() + " " + response.body();
    }
}
