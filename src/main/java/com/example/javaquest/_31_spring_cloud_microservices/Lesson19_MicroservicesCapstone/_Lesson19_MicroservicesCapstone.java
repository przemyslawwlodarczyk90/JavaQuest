package com.example.javaquest._31_spring_cloud_microservices.Lesson19_MicroservicesCapstone;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import javax.crypto.SecretKey;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Date;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.stripPrefix;

public class _Lesson19_MicroservicesCapstone {

    // WSPOLNY klucz JWT (jak Lesson16) - WSZYSTKIE instancje orders-service WERYFIKUJA NIM token.
    private static final SecretKey KLUCZ = Jwts.SIG.HS256.key().build();

    @RestController
    static class OrdersController {
        private final Tracer tracer;
        private final MeterRegistry meterRegistry;

        OrdersController(Tracer tracer, MeterRegistry meterRegistry) {
            this.tracer = tracer;
            this.meterRegistry = meterRegistry;
        }

        @GetMapping("/orders/{id}")
        ResponseEntity<String> getOrder(@PathVariable String id, @RequestHeader(value = "Authorization", required = false) String authHeader,
                                          jakarta.servlet.http.HttpServletRequest request) {
            String uzytkownik = walidujToken(authHeader);
            if (uzytkownik == null) {
                meterRegistry.counter("orders.requests", "status", "unauthorized").increment();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Brak lub nieprawidlowy token");
            }

            Span span = tracer.nextSpan().name("orders.get").tag("orderId", id).start();
            try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
                meterRegistry.counter("orders.requests", "status", "success").increment();
                int port = request.getLocalPort();
                System.out.println("    [orders-service:" + port + ", traceId=" + span.context().traceId() + "] obsluzono zadanie DLA " + uzytkownik);
                return ResponseEntity.ok("Zamowienie #" + id + " DLA " + uzytkownik + " (instancja portu " + port + ")");
            } finally {
                span.end();
            }
        }
    }

    private static String walidujToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        try {
            Claims claims = Jwts.parser().verifyWith(KLUCZ).build().parseSignedClaims(authHeader.substring(7)).getPayload();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    // MUSI byc PELNE @SpringBootApplication (Z ComponentScan) - eureka-client WYMAGA tego DO
    // poprawnego wyboru `TransportClientFactories` (pulapka zweryfikowana empirycznie W Lesson08).
    @SpringBootApplication
    static class BackendApp {
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServerApp {
    }

    // Gateway UZYWA prostego, stalego adresu (JEDNEJ instancji) - NIE laczy Gateway Z Eureka+
    // LoadBalancerem naraz (swiadome uproszczenie kapsztonu - unika DODATKOWEJ, RYZYKOWNEJ
    // kombinacji "Gateway jako eureka-client" NA rzecz przejrzystosci demo; load balancing
    // MIEDZY instancjami jest juz OSOBNO pokazany PRZEZ @LoadBalanced RestTemplate ponizej,
    // dokladnie jak W Lesson08).
    @Configuration
    @EnableAutoConfiguration
    static class GatewayApp {
        @Bean
        RouterFunction<ServerResponse> routes(BackendPortHolder holder) {
            return RouterFunctions.route()
                    .GET("/api/v1/orders/**", http("http://localhost:" + holder.port()))
                    .filter(stripPrefix(2))
                    .build();
        }

        @Bean
        BackendPortHolder backendPortHolder() {
            return new BackendPortHolder(GATEWAY_TARGET_PORT[0]);
        }
    }

    record BackendPortHolder(int port) {
    }

    private static final int[] GATEWAY_TARGET_PORT = new int[1];

    // MUSI byc PELNE @SpringBootApplication - `@LoadBalanced RestTemplate` WYMAGA eureka-client
    // (TA SAMA zasada CO BackendApp, zweryfikowana W Lesson08).
    @SpringBootApplication
    static class ClientApp {
        @Bean
        @LoadBalanced
        RestTemplate loadBalancedRestTemplate() {
            return new RestTemplate();
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 19 (KAPSZTON): JavaQuest Microservices Demo ===");
        System.out.println("Laczy: Eureka (Lesson03) + LoadBalancer (Lesson08) + Gateway+filtry (Lesson06-07) + JWT propagacja (Lesson16) + tracing/metryki (Lesson11/18).");

        System.setProperty("eureka.client.enabled", "true");
        ConfigurableApplicationContext serverContext = null;
        ConfigurableApplicationContext backend1Context = null;
        ConfigurableApplicationContext backend2Context = null;
        ConfigurableApplicationContext gatewayContext = null;
        ConfigurableApplicationContext clientContext = null;
        try {
            serverContext = startEurekaServer();
            int eurekaPort = Integer.parseInt(serverContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("\nEureka Server NA porcie " + eurekaPort + ".");

            int port1 = findFreePort();
            int port2 = findFreePort();
            backend1Context = startBackendInstance(eurekaPort, "orders-1", port1);
            backend2Context = startBackendInstance(eurekaPort, "orders-2", port2);
            System.out.println("2 instancje 'orders-service' NA portach " + port1 + " i " + port2 + ".");

            waitForRegistration(serverContext, 2);

            GATEWAY_TARGET_PORT[0] = port1;
            gatewayContext = startGateway();
            int gatewayPort = Integer.parseInt(gatewayContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("API Gateway NA porcie " + gatewayPort + " (trasuje DO instancji NA porcie " + port1 + ").");

            clientContext = startClient(eurekaPort);

            String validToken = generateToken("kasia", 60_000);
            String expiredToken = generateToken("kasia", -1_000);

            demonstrateLoadBalancingDirectly(clientContext, validToken);
            demonstrateThroughGateway(gatewayPort, validToken);
            demonstrateSecurityEnforcement(gatewayPort, expiredToken);
        } finally {
            if (clientContext != null) {
                clientContext.close();
            }
            if (gatewayContext != null) {
                gatewayContext.close();
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

        System.out.println("\n=== KONIEC LEKCJI 19 I ROZDZIALU _31_spring_cloud_microservices ===");
    }

    private static int findFreePort() throws Exception {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static String generateToken(String subject, long ttlMillis) {
        long now = System.currentTimeMillis();
        return Jwts.builder().subject(subject).issuedAt(new Date(now)).expiration(new Date(now + ttlMillis)).signWith(KLUCZ).compact();
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

    private static ConfigurableApplicationContext startBackendInstance(int eurekaPort, String instanceId, int port) {
        return new SpringApplicationBuilder(BackendApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--eureka.instance.instance-id=" + instanceId,
                        "--server.port=" + port,
                        "--eureka.client.serviceUrl.defaultZone=http://localhost:" + eurekaPort + "/eureka/",
                        "--eureka.client.fetch-registry=false",
                        "--eureka.instance.lease-renewal-interval-in-seconds=1",
                        "--eureka.instance.prefer-ip-address=true",
                        "--management.tracing.sampling.probability=1.0",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static ConfigurableApplicationContext startGateway() {
        // WAZNE: globalny System.setProperty("eureka.client.enabled", "true") (main(), potrzebny
        // DLA serwera/backendow/klienta) OBOWIAZUJE DLA WSZYSTKICH kontekstow W tym JVM, WLACZNIE
        // Z Gateway - Gateway (celowo BEZ Eureki, stary adres) DOSTAWALBY TA SAMA pulapke
        // "TransportClientFactories" CO BackendApp W Lesson08 (bo @Configuration+
        // @EnableAutoConfiguration, BEZ ComponentScan, NIE obsluguje poprawnie eureka-client).
        // Naprawa: jawny "--eureka.client.enabled=false" (argument wiersza polecen MA WYZSZY
        // priorytet NIZ System property) WYLACZA eureka-client TYLKO DLA Gateway.
        return new SpringApplicationBuilder(GatewayApp.class)
                .run(
                        "--spring.application.name=api-gateway",
                        "--server.port=0",
                        "--eureka.client.enabled=false",
                        "--spring.cloud.gateway.mvc.enabled=true",
                        "--logging.level.root=WARN");
    }

    private static ConfigurableApplicationContext startClient(int eurekaPort) {
        return new SpringApplicationBuilder(ClientApp.class)
                .run(
                        "--spring.application.name=order-client",
                        "--server.port=0",
                        "--eureka.client.serviceUrl.defaultZone=http://localhost:" + eurekaPort + "/eureka/",
                        "--eureka.client.register-with-eureka=false",
                        "--eureka.client.registry-fetch-interval-seconds=1",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static void waitForRegistration(ConfigurableApplicationContext serverContext, int expectedCount) throws InterruptedException {
        com.netflix.eureka.registry.PeerAwareInstanceRegistry registry = serverContext.getBean(com.netflix.eureka.registry.PeerAwareInstanceRegistry.class);
        Instant deadline = Instant.now().plusSeconds(20);
        while (Instant.now().isBefore(deadline)) {
            var application = registry.getApplication("ORDERS-SERVICE");
            if (application != null && application.getInstances().size() >= expectedCount) {
                System.out.println("Zarejestrowano " + application.getInstances().size() + " instancji 'orders-service' W Eurece.");
                return;
            }
            Thread.sleep(300);
        }
        System.out.println("NIE zdazyly sie zarejestrowac WSZYSTKIE instancje W ciagu 20s (rzadkie).");
    }

    private static void demonstrateLoadBalancingDirectly(ConfigurableApplicationContext clientContext, String token) throws InterruptedException {
        System.out.println("\n--- Scenariusz 1: @LoadBalanced RestTemplate (BEZPOSREDNIO, BEZ Gateway) - round-robin ---");
        RestTemplate restTemplate = clientContext.getBean(RestTemplate.class);
        var headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        var entity = new org.springframework.http.HttpEntity<Void>(headers);

        Instant deadline = Instant.now().plusSeconds(15);
        int wykonane = 0;
        while (wykonane < 4 && Instant.now().isBefore(deadline)) {
            try {
                var response = restTemplate.exchange("http://orders-service/orders/1", org.springframework.http.HttpMethod.GET, entity, String.class);
                System.out.println("Wywolanie " + (wykonane + 1) + " -> " + response.getBody());
                wykonane++;
            } catch (Exception e) {
                Thread.sleep(300);
            }
        }
    }

    private static void demonstrateThroughGateway(int gatewayPort, String token) throws Exception {
        System.out.println("\n--- Scenariusz 2: PRZEZ Gateway (/api/v1/orders/1, stripPrefix, JWT PROPAGOWANY) ---");
        HttpResponse<String> response = httpGetWithAuth(gatewayPort, "/api/v1/orders/1", token);
        System.out.println("Status: " + response.statusCode() + " | Body: " + response.body());
    }

    private static void demonstrateSecurityEnforcement(int gatewayPort, String expiredToken) throws Exception {
        System.out.println("\n--- Scenariusz 3: bezpieczenstwo - BRAK tokenu I WYGASLY token (PRZEZ Gateway) ---");
        HttpResponse<String> bezTokenu = httpGetWithAuth(gatewayPort, "/api/v1/orders/1", null);
        System.out.println("BEZ tokenu -> status: " + bezTokenu.statusCode() + " | " + bezTokenu.body());

        HttpResponse<String> wygasly = httpGetWithAuth(gatewayPort, "/api/v1/orders/1", expiredToken);
        System.out.println("WYGASLY token -> status: " + wygasly.statusCode() + " | " + wygasly.body());
    }

    private static HttpResponse<String> httpGetWithAuth(int port, String path, String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
