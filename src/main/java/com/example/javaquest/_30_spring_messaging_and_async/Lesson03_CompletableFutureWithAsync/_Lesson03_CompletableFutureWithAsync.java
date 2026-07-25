package com.example.javaquest._30_spring_messaging_and_async.Lesson03_CompletableFutureWithAsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson03_CompletableFutureWithAsync {

    @Service
    static class CenaService {
        @Async
        public CompletableFuture<Double> pobierzCeneKsiegarnia(String tytul) {
            symulujOpoznienie(80);
            System.out.println("  Cena Z Ksiegarni pobrana NA watku: " + Thread.currentThread().getName());
            return CompletableFuture.completedFuture(49.99);
        }

        @Async
        public CompletableFuture<Double> pobierzCeneAllegro(String tytul) {
            symulujOpoznienie(60);
            System.out.println("  Cena Z Allegro pobrana NA watku: " + Thread.currentThread().getName());
            return CompletableFuture.completedFuture(45.50);
        }

        @Async
        public CompletableFuture<Double> pobierzCeneZBledem(String tytul) {
            symulujOpoznienie(30);
            CompletableFuture<Double> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Sklep niedostepny"));
            return future;
        }

        private void symulujOpoznienie(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @SpringBootApplication
    @EnableAsync
    static class CompletableFutureApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: @Async zwracajacy CompletableFuture - odbieranie wyniku asynchronicznego wywolania ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - @Async + CompletableFuture
         * ============================================================
         * Lesson01 pokazal `@Async void` - "fire-and-forget", BEZ
         * mozliwosci ODEBRANIA wyniku. `@Async` MOZE TEZ zwracac
         * `CompletableFuture<T>` (powiazanie Z `_14_advancedjava/
         * Lesson32_CompletableFuture`) - Spring OPAKOWUJE wynik
         * metody W `CompletableFuture` AUTOMATYCZNIE, wywolujacy
         * MOZE POLACZYC WIELE takich wywolan (np. `thenCombine`) DO
         * AGREGACJI wynikow Z WIELU rownoleglych zrodel.
         */
        System.out.println("@Async CompletableFuture<T> - Spring automatycznie opakowuje wynik, MOZNA laczyc WIELE wywolan (thenCombine, allOf).");

        try (ConfigurableApplicationContext context = SpringApplication.run(CompletableFutureApp.class, "--server.port=0", "--logging.level.root=WARN")) {
            CenaService serwis = context.getBean(CenaService.class);

            demonstrateParallelCallsCombinedWithThenCombine(serwis);
            demonstrateExceptionHandlingWithExceptionally(serwis);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Async` metoda ZWRACAJACA `CompletableFuture<T>` - Spring
         *   WYWOLUJE metode NA executorze (Lesson02), a WYNIK
         *   opakowuje W `CompletableFuture`.
         * - `thenCombine(...)` - LACZY WYNIKI 2 rownoleglych wywolan
         *   `@Async` W 1 wartosc.
         * - `.exceptionally(...)` - obsluga bledu Z `@Async` metody
         *   (analogicznie DO `onErrorResume` Z `_29_spring_reactive/
         *   Lesson07`).
         * - `CompletableFuture.allOf(...)` - CZEKAJ NA WSZYSTKIE
         *   rownolegle wywolania NARAZ.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateParallelCallsCombinedWithThenCombine(CenaService serwis) throws ExecutionException, InterruptedException {
        System.out.println("\n--- 2 rownolegle @Async wywolania, POLACZONE thenCombine - znajdz TANSZA cene ---");

        long start = System.currentTimeMillis();

        CompletableFuture<Double> cenaKsiegarnia = serwis.pobierzCeneKsiegarnia("Diuna");
        CompletableFuture<Double> cenaAllegro = serwis.pobierzCeneAllegro("Diuna");

        CompletableFuture<Double> tanszaCena = cenaKsiegarnia.thenCombine(cenaAllegro, Math::min);

        Double wynik = tanszaCena.get();
        long czas = System.currentTimeMillis() - start;

        System.out.println("Tansza cena: " + wynik + " PLN, czas calkowity: " + czas + "ms (BLISKI max(80,60)=80ms, NIE 80+60=140ms - OBA wywolania BYLY rownolegle).");

        assertThat(wynik).isEqualTo(45.50);
        assertThat(czas).isLessThan(140);
    }

    private static void demonstrateExceptionHandlingWithExceptionally(CenaService serwis) throws ExecutionException, InterruptedException {
        System.out.println("\n--- Obsluga bledu z @Async CompletableFuture uzywajac .exceptionally() ---");

        CompletableFuture<Double> zFallbackiem = serwis.pobierzCeneZBledem("Ksiazka")
                .exceptionally(blad -> {
                    System.out.println("Zlapano blad: " + blad.getCause().getMessage() + " - zwracam cene domyslna.");
                    return 0.0;
                });

        Double wynik = zFallbackiem.get();
        System.out.println("Wynik po fallback: " + wynik);
        assertThat(wynik).isEqualTo(0.0);
    }
}
