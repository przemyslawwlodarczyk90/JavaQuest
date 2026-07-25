package com.example.javaquest._31_spring_cloud_microservices.Lesson18_ObservabilityAcrossServices;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _Lesson18_ObservabilityAcrossServices {

    @Service
    static class OrdersService {
        private final Tracer tracer;
        private final MeterRegistry meterRegistry;

        OrdersService(Tracer tracer, MeterRegistry meterRegistry) {
            this.tracer = tracer;
            this.meterRegistry = meterRegistry;
        }

        String zlozZamowienie(String klient, boolean symulujBlad) {
            Span span = tracer.nextSpan().name("orders.zloz-zamowienie").tag("klient", klient).start();
            long start = System.nanoTime();
            try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
                String traceId = tracer.currentSpan().context().traceId();
                // TRZY FILARY obserwowalnosci NA JEDNYM zdarzeniu:
                // 1) LOG (Z traceId - korelacja Z tracingiem),
                // 2) METRYKA (licznik + czas trwania),
                // 3) TRACE (span, juz aktywny).
                System.out.println("  [LOG traceId=" + traceId + "] Przetwarzanie zamowienia DLA " + klient);

                if (symulujBlad) {
                    meterRegistry.counter("orders.processed", "status", "error").increment();
                    span.tag("error", "true");
                    System.out.println("  [LOG traceId=" + traceId + "] BLAD podczas przetwarzania!");
                    return "BLAD dla " + klient;
                }

                meterRegistry.counter("orders.processed", "status", "success").increment();
                return "Zamowienie ZLOZONE dla " + klient;
            } finally {
                meterRegistry.timer("orders.duration").record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
                span.end();
            }
        }
    }

    @SpringBootApplication
    static class DemoApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 18: Observability - logi + metryki + trace RAZEM ===");

        /*
         * ============================================================
         * 📦 TRZY FILARY OBSERWOWALNOSCI
         * ============================================================
         * Lesson11 uczyl TRACINGU (traceId/spanId). `_21_spring_boot/
         * Lesson12-13` uczyl Actuatora/metryk. Te DWIE lekcje (I logi,
         * znane Z `_13_libraries/Lesson15-17`) TO "trzy filary
         * obserwowalnosci":
         * - LOGI - CO SIE STALO (szczegoly zdarzenia).
         * - METRYKI - ILE/JAK CZESTO (agregaty W czasie).
         * - TRACE - GDZIE W SYSTEMIE (droga zadania PRZEZ serwisy).
         *
         * KLUCZOWE: WSZYSTKIE TRZY POWINNY byc POWIAZANE WSPOLNYM
         * identyfikatorem (`traceId` W logu) - GDY metryka pokazuje
         * WZROST bledow, `traceId` Z LOGU pozwala ZNALEZC KONKRETNY
         * slad W Zipkinie (Lesson12), a stamtad PRZEJSC DO
         * SZCZEGOLOWYCH logow TEGO zadania.
         */
        System.out.println("Logi (co) + metryki (ile) + trace (gdzie) - POWIAZANE WSPOLNYM traceId DLA pelnej obserwowalnosci.");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--management.tracing.sampling.probability=1.0",
                        "--management.endpoints.web.exposure.include=health,metrics",
                        "--logging.level.root=WARN");

        try {
            int port = Integer.parseInt(context.getEnvironment().getProperty("local.server.port"));
            OrdersService service = context.getBean(OrdersService.class);

            demonstrateCorrelatedObservability(service);
            demonstrateQueryMetricsViaActuator(port);
            demonstrateQueryHealthViaActuator(port);
        } finally {
            context.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - KAZDE zdarzenie biznesowe MOZE WYGENEROWAC log+metryke+span
         *   NARAZ - to NIE TRZY OSOBNE systemy DO wdrozenia OSOBNO,
         *   TYLKO TRZY POWIAZANE ZE SOBA widoki NA TO SAMO zdarzenie.
         * - `MeterRegistry.counter(...)`/`timer(...)` - PROGRAMOWE API
         *   Micrometera (uzywane POD SPODEM przez Actuatora).
         * - `/actuator/metrics/{nazwa}` - odczyt POJEDYNCZEJ metryki
         *   PRZEZ HTTP.
         * - Lesson19 (kapszton) POLACZY WSZYSTKIE mechanizmy tego
         *   rozdzialu (Eureka+Gateway+LoadBalancer+CircuitBreaker+
         *   tracing+metryki) W JEDNYM, spojnym demo.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void demonstrateCorrelatedObservability(OrdersService service) {
        System.out.println("\n--- 3 zamowienia (2 udane, 1 Z bledem) - logi+metryki+trace RAZEM ---");
        service.zlozZamowienie("Kasia", false);
        service.zlozZamowienie("Marek", false);
        service.zlozZamowienie("Ania", true);
    }

    private static void demonstrateQueryMetricsViaActuator(int port) throws Exception {
        System.out.println("\n--- Odczyt metryk PRZEZ /actuator/metrics ---");
        String body = httpGet(port, "/actuator/metrics/orders.processed");
        System.out.println("GET /actuator/metrics/orders.processed -> " + body);

        String durationBody = httpGet(port, "/actuator/metrics/orders.duration");
        System.out.println("GET /actuator/metrics/orders.duration -> " + durationBody);
    }

    private static void demonstrateQueryHealthViaActuator(int port) throws Exception {
        System.out.println("\n--- Odczyt stanu zdrowia PRZEZ /actuator/health ---");
        String body = httpGet(port, "/actuator/health");
        System.out.println("GET /actuator/health -> " + body);
    }

    private static String httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() + " " + response.body();
    }
}
