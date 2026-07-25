package com.example.javaquest._31_spring_cloud_microservices.Lesson07_GatewayRoutingAndFilters;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

public class _Lesson07_GatewayRoutingAndFilters {

    // Musi byc SIOSTRZANA klasa (NIE zagniezdzona wewnatrz @Configuration) - patrz komentarz W
    // Lesson06 (automatyczne przetwarzanie klas czlonkowskich @Configuration).
    @RestController
    static class OrdersController {
        @GetMapping("/orders/{id}")
        String getOrder(@PathVariable String id, @RequestHeader(value = "X-Gateway-Trace", required = false) String trace) {
            return "Zamowienie #" + id + " | naglowek X-Gateway-Trace odebrany PRZEZ backend: " + trace;
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
                    .GET("/api/v1/orders/**", http("http://localhost:" + backendPortHolder.port()))
                    .filter(stripPrefix(2))
                    .filter(addRequestHeader("X-Gateway-Trace", "javaquest-demo"))
                    .filter(addResponseHeader("X-Powered-By", "Spring-Cloud-Gateway-MVC"))
                    .build();
        }

        @Bean
        BackendPortHolder backendPortHolder() {
            return new BackendPortHolder(BACKEND_PORT[0]);
        }
    }

    record BackendPortHolder(int port) {
    }

    private static final int[] BACKEND_PORT = new int[1];

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: Filtry Gateway - stripPrefix, naglowki request/response ===");

        /*
         * ============================================================
         * 📦 FILTRY - modyfikacja zadania/odpowiedzi W DRODZE
         * ============================================================
         * Trasa (route) MOWI "gdzie" skierowac zadanie. Filtr MOWI
         * "jak je PO DRODZE zmienic" - dopisac/usunac naglowek,
         * przepisac sciezke (`stripPrefix`/`rewritePath`), ustawic
         * status, ograniczyc rozmiar itp.
         *
         * `stripPrefix(2)` USUWA 2 PIERWSZE segmenty sciezki PRZED
         * przekazaniem zadania DALEJ - klient wola "/api/v1/orders/42",
         * ALE backend WIDZI TYLKO "/orders/42" (2 segmenty: "api", "v1"
         * ZDJETE). To STANDARDOWY wzorzec: publiczne API MA WERSJONOWANA
         * sciezke (`/api/v1/...`), ALE SAM backend O TYM NIE WIE.
         */
        System.out.println("Filtry: stripPrefix (przepisanie sciezki), addRequestHeader/addResponseHeader (naglowki PO DRODZE).");

        ConfigurableApplicationContext backendContext = null;
        ConfigurableApplicationContext gatewayContext = null;
        try {
            backendContext = startBackend();
            BACKEND_PORT[0] = Integer.parseInt(backendContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Backend wystartowal NA porcie " + BACKEND_PORT[0] + " (mapowanie: /orders/{id}).");

            gatewayContext = startGateway();
            int gatewayPort = Integer.parseInt(gatewayContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Gateway wystartowal NA porcie " + gatewayPort + " (mapowanie: /api/v1/orders/**).");

            demonstrateStripPrefixAndHeaders(gatewayPort);
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
         * - `stripPrefix(n)` - usuwa N segmentow Z PRZODU sciezki
         *   PRZED przekazaniem DALEJ (wersjonowanie API BEZ obciazania
         *   backendu wiedza O tym).
         * - `addRequestHeader`/`addResponseHeader` - dopisywanie
         *   naglowkow PO DRODZE (np. tracing, informacje O Gateway).
         * - Filtry LACZA SIE lancuchowo (`.filter(...).filter(...)`) -
         *   wykonuja sie W kolejnosci deklaracji.
         * - Lesson08 doda load balancing MIEDZY WIELOMA instancjami
         *   backendu (zamiast jednego, stalego adresu).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
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

    private static void demonstrateStripPrefixAndHeaders(int gatewayPort) throws Exception {
        System.out.println("\n--- GET /api/v1/orders/42 PRZEZ Gateway (stripPrefix + naglowki) ---");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + gatewayPort + "/api/v1/orders/42")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Cialo odpowiedzi: " + response.body());
        System.out.println("Cialo POTWIERDZA, ze backend ODEBRAL naglowek 'X-Gateway-Trace' dodany PRZEZ Gateway PO DRODZE.");
        System.out.println("Naglowek odpowiedzi 'X-Powered-By': " + response.headers().firstValue("X-Powered-By").orElse("(brak)"));
        System.out.println("Backend MAPOWAL TYLKO '/orders/{id}' (BEZ '/api/v1') - stripPrefix(2) ZDJAL prefiks PRZED przekazaniem.");
    }
}
