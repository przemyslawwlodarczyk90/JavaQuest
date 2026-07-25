package com.example.javaquest._31_spring_cloud_microservices.Lesson10_Resilience4jIntegration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson10_Resilience4jIntegration {

    @Service
    static class DownstreamService {
        private final AtomicInteger licznikWywolanCb = new AtomicInteger();
        private final AtomicInteger licznikProbRetry = new AtomicInteger();

        @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "cbDemo", fallbackMethod = "cbFallback")
        String wolajZCircuitBreakerem(int id) {
            licznikWywolanCb.incrementAndGet();
            // Symulacja: wywolania 1-3 ZAWSZE PADAJA (Serwis "downstream" niedostepny).
            if (licznikWywolanCb.get() <= 3) {
                throw new IllegalStateException("Downstream niedostepny (wywolanie " + id + ")");
            }
            return "OK (wywolanie " + id + ")";
        }

        String cbFallback(int id, Throwable t) {
            return "FALLBACK dla wywolania " + id + " (" + t.getClass().getSimpleName() + ")";
        }

        @Retry(name = "retryDemo", fallbackMethod = "retryFallback")
        String wolajZRetry() {
            int proba = licznikProbRetry.incrementAndGet();
            if (proba < 3) {
                throw new IllegalStateException("Chwilowy blad (proba " + proba + ")");
            }
            return "OK PO " + proba + " probach";
        }

        String retryFallback(Throwable t) {
            return "FALLBACK (wszystkie proby wyczerpane): " + t.getMessage();
        }

        @RateLimiter(name = "limiterDemo", fallbackMethod = "rateLimiterFallback")
        String wolajZRateLimiterem(int id) {
            return "Zadanie " + id + " WYKONANE";
        }

        String rateLimiterFallback(int id, Throwable t) {
            return "ODRZUCONE PRZEZ rate limiter (zadanie " + id + "): " + t.getClass().getSimpleName();
        }
    }

    @SpringBootApplication
    static class DemoApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: Resilience4j - PRAWDZIWA implementacja circuit breakera/retry/rate limitera ===");

        /*
         * ============================================================
         * 📦 Resilience4j - implementacja Z Lesson09
         * ============================================================
         * `spring-cloud-starter-circuitbreaker-resilience4j` DAJE
         * adnotacje (`@CircuitBreaker`/`@Retry`/`@RateLimiter`) - Spring
         * TWORZY PROXY (dokladnie TA SAMA technika CO `@Async`
         * `_30_spring_messaging_and_async/Lesson01`, Spring AOP
         * `_20_spring_core/Lesson21`) WOKOL beana, PRZECHWYTUJE
         * wywolanie METODY I stosuje wzorzec odpornosciowy.
         *
         * Konfiguracja (progi/okna/timeouty) PRZEZ WLASCIWOSCI
         * `resilience4j.circuitbreaker.instances.<nazwa>.*` (I ANALOGICZNIE
         * DLA retry/ratelimiter) - `name` W adnotacji WSKAZUJE, KTORA
         * konfiguracje uzyc.
         */
        System.out.println("@CircuitBreaker/@Retry/@RateLimiter - PROXY (jak @Async) stosuje wzorzec odpornosciowy WOKOL metody.");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApp.class)
                .run(
                        "--spring.application.name=resilience4j-demo",
                        "--server.port=0",
                        // Circuit breaker: okno 4 probek, prog 50% bledow, 2s W stanie OPEN.
                        "--resilience4j.circuitbreaker.instances.cbDemo.sliding-window-type=COUNT_BASED",
                        "--resilience4j.circuitbreaker.instances.cbDemo.sliding-window-size=4",
                        "--resilience4j.circuitbreaker.instances.cbDemo.minimum-number-of-calls=4",
                        "--resilience4j.circuitbreaker.instances.cbDemo.failure-rate-threshold=50",
                        "--resilience4j.circuitbreaker.instances.cbDemo.wait-duration-in-open-state=2s",
                        "--resilience4j.circuitbreaker.instances.cbDemo.permitted-number-of-calls-in-half-open-state=2",
                        // Retry: max 3 proby, 50ms miedzy nimi.
                        "--resilience4j.retry.instances.retryDemo.max-attempts=3",
                        "--resilience4j.retry.instances.retryDemo.wait-duration=50ms",
                        // Rate limiter: max 2 zadania NA okres 10s, BEZ oczekiwania (odrzuc od razu PO limicie).
                        "--resilience4j.ratelimiter.instances.limiterDemo.limit-for-period=2",
                        "--resilience4j.ratelimiter.instances.limiterDemo.limit-refresh-period=10s",
                        "--resilience4j.ratelimiter.instances.limiterDemo.timeout-duration=0",
                        "--logging.level.root=WARN");

        try {
            DownstreamService service = context.getBean(DownstreamService.class);
            demonstrateCircuitBreaker(service);
            demonstrateRetry(service);
            demonstrateRateLimiter(service);
            demonstrateCircuitBreakerRegistry(context);
        } finally {
            context.close();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@CircuitBreaker(name=..., fallbackMethod=...)` - PRAWDZIWA
         *   implementacja stanow Z Lesson09 (CLOSED/OPEN/HALF_OPEN).
         * - `@Retry` - AUTOMATYCZNE ponawianie DO okreslonej liczby prob.
         * - `@RateLimiter` - ogranicza liczbe wywolan NA okres czasu,
         *   rzuca `RequestNotPermitted` PO przekroczeniu.
         * - Fallback method MUSI miec TA SAMA sygnature parametrow +
         *   dodatkowy `Throwable` NA KONCU.
         * - `CircuitBreakerRegistry` pozwala PROGRAMOWO odpytac stan
         *   circuit breakera (przydatne DO metryk/monitoringu, Lesson18).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void demonstrateCircuitBreaker(DownstreamService service) {
        System.out.println("\n--- @CircuitBreaker: 3 pierwsze wywolania PADAJA, potem breaker sie OTWIERA ---");
        for (int i = 1; i <= 5; i++) {
            String wynik = service.wolajZCircuitBreakerem(i);
            System.out.println("Wywolanie " + i + " -> " + wynik);
        }
    }

    private static void demonstrateRetry(DownstreamService service) {
        System.out.println("\n--- @Retry: 2 pierwsze proby PADAJA, 3-cia sie UDAJE (max-attempts=3) ---");
        String wynik = service.wolajZRetry();
        System.out.println("Wynik PO retry -> " + wynik);
    }

    private static void demonstrateRateLimiter(DownstreamService service) throws InterruptedException {
        System.out.println("\n--- @RateLimiter: limit 2 zadania/10s - 3-cie zadanie ODRZUCONE ---");
        for (int i = 1; i <= 3; i++) {
            String wynik = service.wolajZRateLimiterem(i);
            System.out.println("Zadanie " + i + " -> " + wynik);
        }
    }

    private static void demonstrateCircuitBreakerRegistry(ConfigurableApplicationContext context) {
        System.out.println("\n--- Programowe odpytanie stanu circuit breakera PRZEZ CircuitBreakerRegistry ---");
        CircuitBreakerRegistry registry = context.getBean(CircuitBreakerRegistry.class);
        CircuitBreaker cb = registry.circuitBreaker("cbDemo");
        System.out.println("Stan 'cbDemo': " + cb.getState());
        System.out.println("Metryki: bledy=" + cb.getMetrics().getNumberOfFailedCalls()
                + ", sukcesy=" + cb.getMetrics().getNumberOfSuccessfulCalls());
    }
}
