package com.example.javaquest._31_spring_cloud_microservices.Lesson12_DistributedTracingWithZipkin;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class _Lesson12_DistributedTracingWithZipkin {

    private static final String ZIPKIN_HOST = "localhost";
    private static final int ZIPKIN_PORT = 9411;

    @Service
    static class OrdersService {
        private final Tracer tracer;

        OrdersService(Tracer tracer) {
            this.tracer = tracer;
        }

        String zlozZamowienie(String klient) {
            Span span = tracer.nextSpan().name("orders.zloz-zamowienie").tag("klient", klient).start();
            try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
                try {
                    Thread.sleep(20); // symulacja pracy - WIDOCZNE jako czas trwania spanu W Zipkinie
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Zamowienie ZLOZONE dla " + klient;
            } finally {
                span.end();
            }
        }
    }

    @SpringBootApplication
    static class DemoApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: Distributed Tracing Z Zipkinem ===");

        /*
         * ============================================================
         * 📦 ZIPKIN - wizualizacja drzewa wywolan Z Lesson11
         * ============================================================
         * Lesson11 pokazal `traceId`/`spanId` W SUROWEJ postaci (tekst
         * W konsoli). W PRAWDZIWYM systemie Z DZIESIATKAMI serwisow
         * czytanie surowych ID jest NIEPRAKTYCZNE - Zipkin (albo
         * Jaeger, Tempo) ZBIERA spany OD WSZYSTKICH serwisow I
         * WIZUALIZUJE PELNE drzewo wywolan W przegladarce (kto wolal
         * kogo, jak dlugo trwal KAZDY krok).
         *
         * `zipkin-reporter-brave` (juz W `pom.xml`) automatycznie
         * WYSYLA zebrane spany DO Zipkina PRZEZ HTTP
         * (`http://localhost:9411/api/v2/spans`) - domyslny adres.
         */
        System.out.println("Zipkin ZBIERA spany OD wszystkich serwisow I wizualizuje PELNE drzewo wywolan w przegladarce.");

        boolean zipkinDostepny = isZipkinReachable();
        if (!zipkinDostepny) {
            System.out.println("\nZipkin NIEDOSTEPNY NA localhost:9411 (Docker niedostepny NA tej maszynie) - pokazuje KOD, ktory");
            System.out.println("FAKTYCZNIE wyslalby spany, Z przyjaznym komunikatem ZAMIAST bledu.");
        }

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--management.tracing.sampling.probability=1.0",
                        "--management.zipkin.tracing.endpoint=http://" + ZIPKIN_HOST + ":" + ZIPKIN_PORT + "/api/v2/spans",
                        "--logging.level.root=WARN");

        try {
            OrdersService service = context.getBean(OrdersService.class);
            String wynik = service.zlozZamowienie("Kasia");
            System.out.println("\nWynik: " + wynik);
            System.out.println("Span WYSLANY (proba) DO " + ZIPKIN_HOST + ":" + ZIPKIN_PORT + "/api/v2/spans.");

            // Krotka chwila NA asynchroniczne wyslanie spanu PRZEZ reporter PRZED zamknieciem kontekstu.
            Thread.sleep(500);

            if (zipkinDostepny) {
                demonstrateQueryZipkinApi();
            } else {
                demonstrateWhatWouldHappenWithZipkin();
            }
        } finally {
            context.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `management.zipkin.tracing.endpoint` - adres, NA KTORY
         *   Micrometer Tracing (Brave reporter) WYSYLA zebrane spany.
         * - Zipkin UI (`http://localhost:9411/zipkin/`) pozwala
         *   PRZESZUKAC slady PO nazwie serwisu/tagu/czasie trwania.
         * - Zipkin API (`GET /api/v2/trace/{traceId}`) pozwala
         *   PROGRAMOWO odczytac PELNY slad (JSON).
         * - W REALNYM wdrozeniu, Zipkin ZWYKLE dziala jako WSPOLNY,
         *   centralny serwis DLA CALEGO systemu (podobnie DO Config
         *   Servera Z Lesson04-05) - WSZYSTKIE mikroserwisy wysylaja
         *   DO NIEGO swoje spany.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static boolean isZipkinReachable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ZIPKIN_HOST, ZIPKIN_PORT), 500);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void demonstrateQueryZipkinApi() throws Exception {
        System.out.println("\n--- Odpytanie Zipkin API O ostatnie slady ---");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://" + ZIPKIN_HOST + ":" + ZIPKIN_PORT + "/api/v2/services"))
                .timeout(Duration.ofSeconds(3))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /api/v2/services -> " + response.statusCode() + " " + response.body());
    }

    private static void demonstrateWhatWouldHappenWithZipkin() {
        System.out.println("\n--- Co BYLOBY widoczne W Zipkin UI (Z dzialajacym Zipkinem) ---");
        System.out.println("http://localhost:9411/zipkin/ -> lista sladow DLA 'orders-service'.");
        System.out.println("Klikniecie W slad -> drzewo spanow Z czasem trwania KAZDEGO kroku ('orders.zloz-zamowienie', ~20ms).");
        System.out.println("GET http://localhost:9411/api/v2/trace/{traceId} -> PELNY slad jako JSON (programowy dostep).");
    }
}
