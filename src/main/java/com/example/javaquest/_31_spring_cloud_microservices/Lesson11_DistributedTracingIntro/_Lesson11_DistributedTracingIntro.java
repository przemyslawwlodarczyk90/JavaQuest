package com.example.javaquest._31_spring_cloud_microservices.Lesson11_DistributedTracingIntro;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

public class _Lesson11_DistributedTracingIntro {

    @Service
    static class OrdersService {
        private final Tracer tracer;
        private final PaymentsService paymentsService;

        OrdersService(Tracer tracer, PaymentsService paymentsService) {
            this.tracer = tracer;
            this.paymentsService = paymentsService;
        }

        String zlozZamowienie(String klient) {
            Span span = tracer.nextSpan().name("orders.zloz-zamowienie").start();
            try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
                System.out.println("  [orders-service] traceId=" + traceId() + " spanId=" + spanId() + " - przetwarzanie zamowienia DLA " + klient);
                String wynikPlatnosci = paymentsService.pobierzPlatnosc(klient);
                return "Zamowienie ZLOZONE dla " + klient + " | " + wynikPlatnosci;
            } finally {
                span.end();
            }
        }

        private String traceId() {
            Span current = tracer.currentSpan();
            return current == null ? "(brak)" : current.context().traceId();
        }

        private String spanId() {
            Span current = tracer.currentSpan();
            return current == null ? "(brak)" : current.context().spanId();
        }
    }

    @Service
    static class PaymentsService {
        private final Tracer tracer;

        PaymentsService(Tracer tracer) {
            this.tracer = tracer;
        }

        String pobierzPlatnosc(String klient) {
            // NOWY span - DZIECKO biezacego (orders.zloz-zamowienie) - TEN SAM traceId, INNY spanId.
            Span span = tracer.nextSpan().name("payments.pobierz-platnosc").start();
            try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
                Span current = tracer.currentSpan();
                System.out.println("  [payments-service] traceId=" + current.context().traceId() + " spanId=" + current.context().spanId() + " - pobieranie platnosci OD " + klient);
                return "platnosc POBRANA";
            } finally {
                span.end();
            }
        }
    }

    @SpringBootApplication
    static class DemoApp {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: Distributed Tracing - wprowadzenie (Micrometer Tracing) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: JEDNO zadanie, WIELE serwisow, WIELE logow
         * ============================================================
         * Zadanie klienta PRZECHODZI PRZEZ WIELE mikroserwisow (Gateway
         * -> orders-service -> payments-service -> ...). KAZDY serwis
         * PISZE WLASNE logi, NIEZALEZNIE - BEZ wspolnego identyfikatora
         * NIE DA SIE polaczyc TYCH logow W JEDNA, spojna "historie"
         * jednego zadania.
         *
         * Distributed tracing ROZWIAZUJE to: KAZDE zadanie DOSTAJE
         * `traceId` (WSPOLNY DLA CALEGO zadania, PRZEZ WSZYSTKIE
         * serwisy) I `spanId` (UNIKALNY DLA KAZDEGO "kroku"/serwisu
         * W TYM zadaniu). `spanId` DZIECKA wskazuje NA `spanId`
         * rodzica (parent-child) - odtwarzajac PELNE DRZEWO wywolan.
         *
         * `micrometer-tracing-bridge-brave` (juz W `pom.xml`, dodany
         * DLA Lesson09 W `_21_spring_boot`) DAJE `Tracer` - abstrakcje
         * Micrometera NAD konkretna implementacja (Brave, dawniej
         * Sleuth).
         */
        System.out.println("traceId = WSPOLNY DLA CALEGO zadania (przez wiele serwisow). spanId = UNIKALNY DLA KAZDEGO kroku, wskazuje NA rodzica.");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApp.class)
                .run(
                        "--spring.application.name=orders-service",
                        "--server.port=0",
                        "--management.tracing.sampling.probability=1.0",
                        "--logging.level.root=WARN");

        try {
            demonstrateTraceIdConsistency(context);
            demonstrateMultipleIndependentTraces(context);
        } finally {
            context.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `traceId` - TEN SAM DLA CALEGO zadania (WSZYSTKIE serwisy).
         * - `spanId` - INNY DLA KAZDEGO "kroku" (metody/serwisu).
         * - `management.tracing.sampling.probability=1.0` - PROBKUJ
         *   100% zadan (W PRODUKCJI zwykle NIZSZA wartosc - narzut).
         * - Micrometer Tracing = ABSTRAKCJA (jak SLF4J DLA logowania) -
         *   Brave TO implementacja POD SPODEM.
         * - Lesson12 doda EKSPORT tych spanow DO Zipkina (wizualizacja
         *   PELNEGO drzewa wywolan W przegladarce).
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateTraceIdConsistency(ConfigurableApplicationContext context) {
        System.out.println("\n--- JEDNO zadanie PRZEZ 2 'serwisy' - TEN SAM traceId, INNY spanId ---");
        OrdersService ordersService = context.getBean(OrdersService.class);
        String wynik = ordersService.zlozZamowienie("Kasia");
        System.out.println("Wynik: " + wynik);
        System.out.println("Zauwaz: traceId W obu logach IDENTYCZNY - spanId ROZNY (rodzic/dziecko).");
    }

    private static void demonstrateMultipleIndependentTraces(ConfigurableApplicationContext context) {
        System.out.println("\n--- DWA NIEZALEZNE zadania - DWA ROZNE traceId ---");
        OrdersService ordersService = context.getBean(OrdersService.class);
        ordersService.zlozZamowienie("Marek");
        ordersService.zlozZamowienie("Ania");
        System.out.println("Zauwaz: traceId ROZNI SIE MIEDZY tymi dwoma NIEZALEZNYMI zadaniami.");
    }
}
