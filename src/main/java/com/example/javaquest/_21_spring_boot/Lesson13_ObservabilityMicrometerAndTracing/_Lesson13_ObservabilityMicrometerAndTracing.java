package com.example.javaquest._21_spring_boot.Lesson13_ObservabilityMicrometerAndTracing;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

public class _Lesson13_ObservabilityMicrometerAndTracing {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: OBSERVABILITY - MICROMETER I TRACING ===");

        /*
         * ============================================================
         * 📦 MICROMETER = "SLF4J DLA METRYK"
         * ============================================================
         * Lesson12 (Actuator) pokazal `/actuator/metrics`. Micrometer
         * to BIBLIOTEKA STOJACA za tym endpointem - fasada NAD RÓZNYMI
         * systemami metryk (Prometheus, Datadog, CloudWatch...),
         * DOKLADNIE jak SLF4J jest fasada NAD roznymi bibliotekami
         * logowania (`_13_libraries/Lesson15`). `MeterRegistry` to
         * GLOWNY punkt wejscia - JUZ jest auto-konfigurowany (dzieki
         * `spring-boot-starter-actuator`, Lesson12).
         */
        System.out.println("Micrometer = 'SLF4J dla metryk' - fasada nad Prometheus/Datadog/CloudWatch, juz auto-konfigurowana przez Actuator.");

        demonstrateCounterAndTimer();
        demonstrateObservationApiUnifiesMetricsAndTracing();

        /*
         * ============================================================
         * 🔹 WAZNA ROZNICA WERSJI: Boot 2 vs Boot 3 (Z Lesson02 _20_spring_core)
         * ============================================================
         * Spring Boot 2: Micrometer sluzyl GLOWNIE do METRYK (liczniki/
         * histogramy). Distributed tracing WYMAGAL OSOBNEGO projektu -
         * Spring Cloud Sleuth. Spring Boot 3: WPROWADZONO ujednolicone
         * `Observation` API (`io.micrometer.observation`) - JEDNO
         * wywolanie `Observation.observe(...)` generuje NARAZ metryke
         * I fragment sladu (trace span) - Spring Cloud Sleuth zostal
         * ZARCHIWIZOWANY, zastapiony przez Micrometer Tracing (osobny
         * projekt, integrujacy sie z TYM SAMYM `Observation` API).
         */
        System.out.println("\nBoot 2: Micrometer = TYLKO metryki, tracing = osobny Spring Cloud Sleuth. Boot 3: Observation API laczy OBA w 1 wywolaniu, Sleuth zarchiwizowany.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `MeterRegistry` - glowny punkt wejscia Micrometer, JUZ
         *   dostepny jako bean dzieki `spring-boot-starter-actuator`.
         * - `Counter`/`Timer` - podstawowe typy metryk - LICZNIK
         *   (tylko rosnie) i CZAS wykonania (z rozkladem).
         * - `Observation` (Boot 3+) - WYZSZY poziom abstrakcji - JEDNO
         *   API generujace metryki I slady tracingu NARAZ.
         * - Metryki widoczne przez `/actuator/metrics/<nazwa>` (Lesson12) -
         *   TEN SAM endpoint, NOWE dane.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    @SpringBootApplication
    static class ObservabilityApp {
    }

    private static void demonstrateCounterAndTimer() {
        System.out.println("\n=== Counter I Timer - PODSTAWOWE TYPY METRYK ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ObservabilityApp.class)
                .web(WebApplicationType.NONE)
                .run();
        try {
            MeterRegistry registry = context.getBean(MeterRegistry.class);

            Counter ordersProcessed = registry.counter("javaquest.orders.processed");
            ordersProcessed.increment();
            ordersProcessed.increment();
            ordersProcessed.increment(3);
            System.out.println("Counter 'javaquest.orders.processed' - wartosc: " + ordersProcessed.count() + " (oczekiwane: 5)");

            Timer processingTimer = registry.timer("javaquest.orders.processing.time");
            processingTimer.record(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            System.out.println("Timer 'javaquest.orders.processing.time' - liczba pomiarow: " + processingTimer.count()
                    + ", srednia: " + processingTimer.mean(TimeUnit.MILLISECONDS) + " ms");
            System.out.println("-> te SAME metryki bylyby widoczne przez /actuator/metrics/javaquest.orders.processed (Lesson12).");
        } finally {
            context.close();
        }
    }

    private static void demonstrateObservationApiUnifiesMetricsAndTracing() {
        System.out.println("\n=== Observation API (Boot 3+): JEDNO WYWOLANIE, METRYKA + SLAD NARAZ ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ObservabilityApp.class)
                .web(WebApplicationType.NONE)
                .run();
        try {
            ObservationRegistry observationRegistry = context.getBean(ObservationRegistry.class);

            String result = Observation.createNotStarted("javaquest.notes.save", observationRegistry)
                    .lowCardinalityKeyValue("operation", "save")
                    .observe(() -> {
                        System.out.println("  (wewnatrz obserwowanej operacji - symulacja zapisu notatki)");
                        return "notatka-zapisana";
                    });

            System.out.println("Wynik obserwowanej operacji: " + result);
            System.out.println("-> `Observation.observe(...)` OPAKOWALO kod - GDYBY byl skonfigurowany prawdziwy tracer (Micrometer Tracing + Brave/OpenTelemetry),");
            System.out.println("   TO SAMO wywolanie wygenerowaloby RAZEM metryke czasu wykonania I fragment sladu (span) - bez zadnego dodatkowego kodu.");
        } finally {
            context.close();
        }
    }
}
