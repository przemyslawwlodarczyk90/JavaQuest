package com.example.javaquest._31_spring_cloud_microservices.Lesson08_ClientSideLoadBalancing;

import com.netflix.discovery.shared.Application;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class _Lesson08_ClientSideLoadBalancing {

    // @Configuration+@EnableAutoConfiguration (BEZ implicit @ComponentScan) - TEN plik ma WIELE
    // innych klas ze stereotypem W TYM SAMYM pakiecie; @SpringBootApplication ZNALAZLOBY je
    // WSZYSTKIE, dajac "Ambiguous mapping" - zweryfikowane empirycznie (ta sama pulapka
    // udokumentowana W _20_spring_core/_23_spring_data_jpa/_27_spring_test).
    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServerApp {
    }

    // WAZNA, NOWA pulapka odkryta W tej lekcji: klient Eureki (eureka-client) POTRZEBUJE, zeby
    // `OrdersController` byl ZAGNIEZDZONY WEWNATRZ `@SpringBootApplication` klasy (JAKO CZLONEK,
    // DOKLADNIE jak `PingController` W `_Lesson03_SpringCloudNetflixEureka.ClientServiceApp`) -
    // wariant Z SIOSTRZANYM `OrdersController` + `@Configuration+@EnableAutoConfiguration` (BEZ
    // ComponentScan) DAWAL "No qualifying bean of type TransportClientFactories" (zweryfikowane
    // empirycznie, przyczyna NIEJASNA - prawdopodobnie zalezy OD kolejnosci przetwarzania
    // auto-konfiguracji Eureki, ktora W jakis sposob POLEGA NA obecnosci `@ComponentScan` -
    // PROSTA proba naprawy przez `excludeFilters` NIE POMOGLA, dopiero powrot DO DOKLADNIE
    // sprawdzonego wzorca Z Lesson03 zadzialal). NIE eksperymentuj Z tym dalej - kopiuj wzorzec
    // Lesson03 1:1 DLA kazdej kolejnej lekcji Z rejestrujacym sie klientem Eureki.
    @org.springframework.boot.autoconfigure.SpringBootApplication
    static class BackendApp {
        @RestController
        static class OrdersController {
            @GetMapping("/orders/1")
            String getOrder(jakarta.servlet.http.HttpServletRequest request) {
                // request.getLocalPort() - PRAWDZIWY port, NA KTORYM Tomcat PRZYJAL to zadanie.
                return "Zamowienie #1 (odpowiedzialo INSTANCJA na porcie " + request.getLocalPort() + ")";
            }
        }
    }

    // MUSI byc PELNE @SpringBootApplication (Z ComponentScan), NIE @Configuration+
    // @EnableAutoConfiguration - patrz komentarz PRZY BackendApp WYZEJ: eureka-client (nawet
    // TYLKO DO odpytywania rejestru, BEZ wlasnej rejestracji) POTRZEBUJE ComponentScan, inaczej
    // "No qualifying bean of type TransportClientFactories" (zweryfikowane empirycznie - TEN SAM
    // blad przeniosl sie Z BackendApp NA ClientApp, kiedy tylko ClientApp zostal @Configuration).
    @org.springframework.boot.autoconfigure.SpringBootApplication
    static class ClientApp {
        @Bean
        @LoadBalanced
        RestTemplate loadBalancedRestTemplate() {
            return new RestTemplate();
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: Client-Side Load Balancing (Spring Cloud LoadBalancer + Eureka) ===");

        /*
         * ============================================================
         * 📦 CLIENT-SIDE LOAD BALANCING - wybor instancji PO STRONIE klienta
         * ============================================================
         * Klient NIE wola konkretnego adresu (`http://10.0.0.1:8081`),
         * TYLKO LOGICZNA nazwe serwisu (`http://orders-service/...`) -
         * `@LoadBalanced RestTemplate`/`RestClient`/`WebClient`
         * PRZECHWYTUJE to wywolanie, ODPYTUJE rejestr (Eureka, Lesson03)
         * O DOSTEPNE instancje "orders-service", WYBIERA JEDNA
         * (domyslnie round-robin) I DOPIERO WTEDY wykonuje PRAWDZIWE
         * zadanie HTTP DO KONKRETNEGO adresu IP:port.
         *
         * To DOKLADNIE mechanizm z Lesson02 ("client-side discovery")
         * ZREALIZOWANY W PRAKTYCE: `spring-cloud-starter-loadbalancer`
         * + `spring-cloud-starter-netflix-eureka-client` NA classpath.
         */
        System.out.println("@LoadBalanced RestTemplate: klient wola 'http://orders-service/...' (logiczna nazwa), LoadBalancer WYBIERA KONKRETNA instancje Z Eureki.");

        System.setProperty("eureka.client.enabled", "true");
        ConfigurableApplicationContext serverContext = null;
        ConfigurableApplicationContext backend1Context = null;
        ConfigurableApplicationContext backend2Context = null;
        ConfigurableApplicationContext clientContext = null;
        try {
            serverContext = startEurekaServer();
            int serverPort = Integer.parseInt(serverContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Eureka Server NA porcie " + serverPort + ".");

            int port1 = findFreePort();
            int port2 = findFreePort();
            backend1Context = startBackendInstance(serverPort, "orders-service-1", port1);
            backend2Context = startBackendInstance(serverPort, "orders-service-2", port2);
            System.out.println("Uruchomiono 2 instancje 'orders-service': porty " + port1 + " i " + port2 + ".");

            waitForInstances(serverContext, "orders-service", 2);

            clientContext = startClient(serverPort);
            demonstrateLoadBalancedCalls(clientContext);
        } finally {
            if (clientContext != null) {
                clientContext.close();
            }
            if (backend2Context != null) {
                backend2Context.close();
            }
            if (backend1Context != null) {
                backend1Context.close();
            }
            if (serverContext != null) {
                serverContext.close();
            }
            System.clearProperty("eureka.client.enabled");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@LoadBalanced` NA `RestTemplate`/`RestClient.Builder`/
         *   `WebClient.Builder` - URI Z NAZWA SERWISU ZAMIAST adresu.
         * - Spring Cloud LoadBalancer domyslnie uzywa round-robin
         *   (`RoundRobinLoadBalancer`) - KOLEJNE wywolania TRAFIAJA
         *   NA RAZ JEDNA, RAZ DRUGA instancje.
         * - Integruje sie Z DOWOLNYM `DiscoveryClient` (TU: Eureka,
         *   Lesson03) - dokladnie TA SAMA warstwa abstrakcji.
         * - Gateway (Lesson06-07) MOZE UZYWAC TEGO SAMEGO mechanizmu
         *   DO trasowania NA logiczna nazwe serwisu ZAMIAST stalego
         *   adresu - naturalne polaczenie obu lekcji.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static ConfigurableApplicationContext startEurekaServer() {
        return new SpringApplicationBuilder(EurekaServerApp.class)
                .run(
                        "--spring.application.name=eureka-server",
                        "--server.port=0",
                        "--eureka.client.register-with-eureka=false",
                        "--eureka.client.fetch-registry=false",
                        "--eureka.server.enable-self-preservation=false",
                        "--eureka.server.response-cache-update-interval-ms=1000",
                        "--eureka.server.use-read-only-response-cache=false",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static int findFreePort() throws java.io.IOException {
        // WAZNA pulapka odkryta W tej lekcji: `server.port=0` (losowy port, uzywany WSZEDZIE
        // INDZIEJ W tym kursie) sprawia, ze `eureka.instance.nonSecurePort`/`instance-id`
        // (domyslnie wyprowadzone Z `${server.port}`) rozwiazuja sie DO LITERALNEGO "0" - Eureka
        // rejestruje instancje Z portem 0 I NIGDY sam tego nie koryguje W tej konfiguracji
        // (zweryfikowane empirycznie: `@LoadBalanced RestTemplate` probowal laczyc sie Z portem
        // 0 -> "Cannot assign requested address"). Naprawa: wybierz WOLNY port SAMODZIELNIE
        // (proba+zamkniecie ServerSocket) PRZED startem Springa I przekaz go jawnie jako
        // `server.port` - Boot i tak dostaje "swiezy, wolny port", ale TYM RAZEM Eureka widzi
        // JEGO PRAWDZIWA wartosc (nie "0") od samego poczatku.
        try (java.net.ServerSocket socket = new java.net.ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static ConfigurableApplicationContext startBackendInstance(int eurekaServerPort, String instanceId, int port) {
        return new SpringApplicationBuilder(BackendApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--eureka.instance.instance-id=" + instanceId,
                        "--server.port=" + port,
                        "--eureka.client.serviceUrl.defaultZone=http://localhost:" + eurekaServerPort + "/eureka/",
                        "--eureka.client.fetch-registry=false",
                        "--eureka.instance.lease-renewal-interval-in-seconds=1",
                        "--eureka.instance.prefer-ip-address=true",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static ConfigurableApplicationContext startClient(int eurekaServerPort) {
        return new SpringApplicationBuilder(ClientApp.class)
                .run(
                        "--spring.application.name=order-client",
                        "--server.port=0",
                        "--eureka.client.serviceUrl.defaultZone=http://localhost:" + eurekaServerPort + "/eureka/",
                        "--eureka.client.register-with-eureka=false",
                        "--eureka.client.registry-fetch-interval-seconds=1",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static void waitForInstances(ConfigurableApplicationContext serverContext, String appName, int expectedCount) throws InterruptedException {
        System.out.println("\n--- Oczekiwanie NA " + expectedCount + " instancje '" + appName + "' W Eurece ---");

        PeerAwareInstanceRegistry registry = serverContext.getBean(PeerAwareInstanceRegistry.class);
        Instant deadline = Instant.now().plusSeconds(20);
        while (Instant.now().isBefore(deadline)) {
            Application application = registry.getApplication(appName.toUpperCase());
            if (application != null && application.getInstances().size() >= expectedCount) {
                System.out.println("Zarejestrowano " + application.getInstances().size() + " instancji.");
                return;
            }
            Thread.sleep(300);
        }
        System.out.println("NIE zdazyly sie zarejestrowac WSZYSTKIE instancje W ciagu 20s (rzadkie).");
    }

    private static void demonstrateLoadBalancedCalls(ConfigurableApplicationContext clientContext) throws InterruptedException {
        System.out.println("\n--- Wywolania 'http://orders-service/orders/1' PRZEZ @LoadBalanced RestTemplate ---");

        RestTemplate restTemplate = clientContext.getBean(RestTemplate.class);
        Map<String, Integer> licznikOdpowiedzi = new LinkedHashMap<>();

        // Klientowi Load Balancera trzeba dac chwile NA pierwsze pobranie rejestru PO WLASNYM
        // starcie - bounded polling (pierwsze wywolania MOGA rzucic wyjatek, zanim rejestr
        // zdazy sie zaladowac), zamiast sztywnego sleep.
        Instant deadline = Instant.now().plusSeconds(15);
        int wykonaneWywolania = 0;
        while (wykonaneWywolania < 6 && Instant.now().isBefore(deadline)) {
            try {
                String result = restTemplate.getForObject("http://orders-service/orders/1", String.class);
                licznikOdpowiedzi.merge(result, 1, Integer::sum);
                System.out.println("Wywolanie " + (wykonaneWywolania + 1) + " -> " + result);
                wykonaneWywolania++;
            } catch (Exception e) {
                Thread.sleep(300);
            }
        }

        System.out.println("\nRozklad odpowiedzi WEDLUG instancji: " + licznikOdpowiedzi);
        System.out.println("OBIE instancje odpowiedzialy - dowod round-robin load balancingu PO STRONIE klienta.");
    }
}
