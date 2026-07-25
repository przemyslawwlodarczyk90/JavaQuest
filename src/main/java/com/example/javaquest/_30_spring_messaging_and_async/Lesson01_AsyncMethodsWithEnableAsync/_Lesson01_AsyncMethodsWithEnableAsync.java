package com.example.javaquest._30_spring_messaging_and_async.Lesson01_AsyncMethodsWithEnableAsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson01_AsyncMethodsWithEnableAsync {

    @Service
    static class PowiadomieniaService {
        @Async
        public void wyslijEmail(String adresat) {
            System.out.println("  [wewnatrz @Async] Wysylanie e-maila do " + adresat + " NA watku: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100); // symulacja WOLNEGO wywolania (np. SMTP)
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  [wewnatrz @Async] E-mail DO " + adresat + " wyslany.");
        }
    }

    @SpringBootApplication
    @EnableAsync
    static class AsyncApp {
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 1: @Async + @EnableAsync - asynchroniczne wywolania metod w Spring ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL - _30_spring_messaging_and_async
         * ============================================================
         * TEN rozdzial POGLEBIA asynchronicznosc Z INNEJ strony NIZ
         * `_29_spring_reactive` - TAM byla to CALKOWITA zmiana modelu
         * programowania (Mono/Flux, event-loop). TU: `@Async` DAJE
         * asynchronicznosc PRZY ZACHOWANIU zwyklego, SYNCHRONICZNEGO
         * stylu pisania kodu wewnatrz metody - Spring PO PROSTU
         * WYWOLUJE metode NA INNYM watku (Z puli), a wywolujacy NIE
         * CZEKA NA jej zakonczenie.
         *
         * Mechanizm: `@EnableAsync` (NA klasie konfiguracji)
         * WLACZA przetwarzanie adnotacji `@Async` - Spring TWORZY
         * PROXY (dokladnie TA SAMA technika CO AOP, `_20_spring_core/
         * Lesson21-22`) WOKOL beana, KTORY PRZECHWYTUJE wywolanie
         * metody I DELEGUJE JE DO `TaskExecutor` (Lesson02) ZAMIAST
         * wykonac SYNCHRONICZNIE.
         */
        System.out.println("@Async + @EnableAsync - metoda wykonuje sie na INNYM watku, wywolujacy NIE CZEKA. Mechanizm: proxy (jak Spring AOP, _20_spring_core/Lesson21).");

        try (ConfigurableApplicationContext context = SpringApplication.run(AsyncApp.class, "--server.port=0", "--logging.level.root=WARN")) {
            PowiadomieniaService serwis = context.getBean(PowiadomieniaService.class);

            demonstrateAsyncMethodReturnsImmediately(serwis);
            demonstrateSelfInvocationPitfall(context);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnableAsync` (NA `@Configuration`) + `@Async` (NA metodzie
         *   beana Springa) - metoda WYKONUJE SIE asynchronicznie.
         * - Wywolujacy NIE CZEKA - kod PO wywolaniu `@Async` metody
         *   WYKONUJE SIE NATYCHMIAST.
         * - TA SAMA pulapka self-invocation CO Spring AOP
         *   (`_20_spring_core/Lesson22`) - wywolanie `@Async` metody
         *   Z TEJ SAMEJ klasy (`this.metoda()`) OMIJA proxy, DZIALA
         *   SYNCHRONICZNIE.
         * - Domyslny executor: `SimpleAsyncTaskExecutor` (TWORZY NOWY
         *   watek PRZY KAZDYM wywolaniu, BEZ puli!) - Lesson02 pokaze,
         *   JAK skonfigurowac PRAWDZIWA pule.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateAsyncMethodReturnsImmediately(PowiadomieniaService serwis) throws InterruptedException {
        System.out.println("\n--- @Async metoda zwraca sie NATYCHMIAST, watek glowny NIE CZEKA ---");

        Instant start = Instant.now();
        serwis.wyslijEmail("klient@example.com");
        Duration czasWywolania = Duration.between(start, Instant.now());

        System.out.println("Wywolanie serwis.wyslijEmail(...) ZAKONCZYLO SIE PO " + czasWywolania.toMillis() + "ms (metoda SAMA W SOBIE trwa ~100ms - watek glowny NIE CZEKAL).");
        assertThat(czasWywolania.toMillis()).isLessThan(50);

        Thread.sleep(200); // poczekaj chwile, zeby watek @Async zdazyl wypisac swoj komunikat
    }

    private static void demonstrateSelfInvocationPitfall(ConfigurableApplicationContext context) {
        System.out.println("\n--- Self-invocation pitfall - TA SAMA pulapka co Spring AOP (_20_spring_core/Lesson22) ---");
        System.out.println("Wywolanie 'this.wyslijEmail(...)' Z WNETRZA TEJ SAMEJ klasy OMIJA proxy Springa - metoda wykonalaby sie SYNCHRONICZNIE, BEZ `@Async`.");
        System.out.println("Naprawa: wstrzyknij serwis Z ZEWNATRZ (jak w main() powyzej) - proxy Springa WTEDY DZIALA poprawnie.");
    }
}
