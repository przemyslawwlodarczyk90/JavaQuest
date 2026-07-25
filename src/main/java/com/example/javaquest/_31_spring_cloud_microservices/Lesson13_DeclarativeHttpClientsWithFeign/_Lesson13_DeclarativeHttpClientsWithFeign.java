package com.example.javaquest._31_spring_cloud_microservices.Lesson13_DeclarativeHttpClientsWithFeign;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

public class _Lesson13_DeclarativeHttpClientsWithFeign {

    // Musi byc SIOSTRZANA klasa (NIE zagniezdzona wewnatrz @Configuration) - patrz komentarz W
    // Lesson06/07 (automatyczne przetwarzanie klas czlonkowskich @Configuration).
    @RestController
    static class OrdersController {
        @GetMapping("/orders/{id}")
        String getOrder(@PathVariable String id) {
            return "Zamowienie #" + id + " (odpowiedz Z PRAWDZIWEGO backendu, Feign)";
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

    // Deklaratywny klient HTTP - INTERFEJS, BEZ zadnej implementacji. `url` WSKAZUJE
    // BEZPOSREDNIO NA backend (W REALNYM projekcie ZWYKLE `name` ODPOWIADA nazwie serwisu W
    // Eurece/Load Balancerze, Lesson08 - TU dla prostoty demo uzywamy jawnego URL).
    @FeignClient(name = "orders-client", url = "${orders.service.url}")
    interface OrdersClient {
        @GetMapping("/orders/{id}")
        String getOrder(@PathVariable("id") String id);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableFeignClients
    static class ClientApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: OpenFeign - deklaratywny klient HTTP ===");

        /*
         * ============================================================
         * 📦 FEIGN - INTERFEJS Java ZAMIAST recznego RestTemplate/WebClient
         * ============================================================
         * `_22_spring_web/Lesson17` uczyl `RestTemplate`/`WebClient`/
         * `RestClient` - WSZYSTKIE wymagaja RECZNEGO budowania zadania
         * (`.get().uri(...).retrieve()...`). Feign ODWRACA to podejscie:
         * PISZESZ TYLKO INTERFEJS (jak REST kontroler, ALE BEZ ciala
         * metod) - Spring Cloud OpenFeign GENERUJE PRAWDZIWA
         * IMPLEMENTACJE W czasie startu (dynamic proxy, powiazanie Z
         * `_14_advancedjava/Lesson17_DynamicProxies`).
         *
         * `@FeignClient(name=..., url=...)` + `@EnableFeignClients`
         * (NA klasie konfiguracji) - Spring Cloud SKANUJE pakiet W
         * poszukiwaniu interfejsow `@FeignClient` I rejestruje
         * WYGENEROWANA implementacje jako zwykly bean Springa.
         */
        System.out.println("Feign: piszesz INTERFEJS (jak kontroler, bez ciala metod) - Spring Cloud GENERUJE PRAWDZIWA implementacje.");

        ConfigurableApplicationContext backendContext = null;
        ConfigurableApplicationContext clientContext = null;
        try {
            backendContext = startBackend();
            int backendPort = Integer.parseInt(backendContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Backend wystartowal NA porcie " + backendPort + ".");

            clientContext = startClient(backendPort);
            demonstrateFeignClientCall(clientContext);
        } finally {
            if (clientContext != null) {
                clientContext.close();
            }
            if (backendContext != null) {
                backendContext.close();
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@FeignClient` - interfejs Java = deklaracja kontraktu HTTP,
         *   Spring Cloud GENERUJE implementacje (dynamic proxy).
         * - `@EnableFeignClients` - WLACZA skanowanie interfejsow
         *   `@FeignClient` W pakiecie.
         * - W REALNYM projekcie `name` czesto ODPOWIADA nazwie serwisu
         *   W Eurece (Lesson03) - Feign wtedy AUTOMATYCZNIE UZYWA
         *   Load Balancera (Lesson08), BEZ potrzeby jawnego `url`.
         * - Zaleta WZGLEDEM `RestTemplate`/`RestClient`: KONTRAKT jest
         *   CZYTELNY jak interfejs Javy, BEZ powtarzalnego kodu
         *   budowania zadan.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static ConfigurableApplicationContext startBackend() {
        return new SpringApplicationBuilder(BackendApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--logging.level.root=WARN");
    }

    private static ConfigurableApplicationContext startClient(int backendPort) {
        return new SpringApplicationBuilder(ClientApp.class)
                .run(
                        "--spring.application.name=feign-client-demo",
                        "--server.port=0",
                        "--orders.service.url=http://localhost:" + backendPort,
                        "--logging.level.root=WARN");
    }

    private static void demonstrateFeignClientCall(ConfigurableApplicationContext clientContext) {
        System.out.println("\n--- Wywolanie PRZEZ WYGENEROWANY klient Feign (BEZ recznego HttpClient) ---");
        OrdersClient client = clientContext.getBean(OrdersClient.class);
        String wynik = client.getOrder("42");
        System.out.println("client.getOrder(\"42\") -> " + wynik);
        System.out.println("Cala 'implementacja' TEGO wywolania TO adnotacja `@GetMapping` NA interfejsie - Feign zrobil RESZTE.");
    }
}
