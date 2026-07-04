package com.example.javaquest._05_multithreading.Lesson32_CompletableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class _Lesson32_CompletableFuture {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CompletableFuture – ASYNCHRONICZNE ŁAŃCUCHY OPERACJI
         * ============================================================
         * CompletableFuture<T> (java.util.concurrent) to znacznie
         * wygodniejsza alternatywa dla zwykłego Future<T>. Zwykły
         * Future tylko "trzyma" wynik, na który trzeba czekać blokująco
         * (future.get()). CompletableFuture pozwala natomiast BUDOWAĆ
         * ŁAŃCUCHY przekształceń, które wykonają się automatycznie, gdy
         * poprzedni krok się zakończy – bez ręcznego blokowania wątku.
         *
         * Najważniejsze metody:
         * - supplyAsync(supplier)   -> uruchamia zadanie ZWRACAJĄCE wynik
         *                              asynchronicznie (na osobnym wątku).
         * - thenApply(fn)           -> przekształca wynik (T -> R), zwraca
         *                              nowy CompletableFuture<R>.
         * - thenAccept(consumer)    -> "konsumuje" wynik (T -> void), koniec
         *                              łańcucha zwraca CompletableFuture<Void>.
         * - thenCompose(fn)         -> jak thenApply, ale gdy funkcja SAMA
         *                              zwraca CompletableFuture<R> (spłaszcza
         *                              zagnieżdżenie – patrz niżej).
         * - exceptionally(fn)       -> obsługuje wyjątek z dowolnego
         *                              wcześniejszego kroku łańcucha.
         * - thenCombine(other, fn)  -> łączy wyniki DWÓCH niezależnych
         *                              CompletableFuture w jeden.
         */

        System.out.println("=== 1) Łańcuch: pobierz dane -> przetwórz -> sformatuj (z obsługą błędu) ===");
        demoChainWithExceptionally(false);
        System.out.println();
        demoChainWithExceptionally(true); // wymuszamy błąd, żeby zadziałał exceptionally()

        System.out.println("\n=== 2) thenApply() vs thenCompose() – różnica w zagnieżdżeniu ===");
        demoThenApplyVsThenCompose();

        System.out.println("\n=== 3) thenCombine() – łączenie dwóch niezależnych obliczeń ===");
        demoThenCombine();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - supplyAsync(supplier)         -> start asynchronicznego zadania z wynikiem.
         * - thenApply(T -> R)              -> przekształć wynik, zostań w łańcuchu CompletableFuture.
         * - thenAccept(T -> void)          -> "skonsumuj" wynik na końcu (bez zwracania nowej wartości).
         * - thenCompose(T -> CompletableFuture<R>) -> gdy krok SAM zwraca CompletableFuture,
         *   inaczej dostalibyśmy CompletableFuture<CompletableFuture<R>> (thenApply by to zagnieździło,
         *   thenCompose "spłaszcza" – analogicznie do flatMap() vs map() w Stream/Optional).
         * - exceptionally(Throwable -> T)  -> "łapie" wyjątek z dowolnego wcześniejszego etapu
         *   i pozwala podstawić wartość zastępczą, żeby łańcuch mógł kontynuować.
         * - thenCombine(other, (a,b) -> R) -> łączy wyniki DWÓCH niezależnych CompletableFuture,
         *   które mogły liczyć się równolegle.
         * - get()/join() blokują wątek czekający na wynik – w prawdziwych aplikacjach stara się
         *   ich unikać i zostawać w świecie asynchronicznym (kolejne thenX), ale na końcu
         *   programu/testu trzeba czasem poczekać na wynik, żeby go zobaczyć.
         */
    }

    /**
     * Łańcuch: "pobierz dane" (supplyAsync) -> "przetwórz" (thenApply, może
     * rzucić wyjątek gdy simulateFailure=true) -> "sformatuj" (thenApply) ->
     * exceptionally() łapie błąd z KTÓREGOKOLWIEK wcześniejszego kroku.
     */
    private static void demoChainWithExceptionally(boolean simulateFailure) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> chain = CompletableFuture
                .supplyAsync(() -> fetchRawData()) // krok 1: "pobierz dane" z zewnętrznego źródła
                .thenApply(rawData -> processData(rawData, simulateFailure)) // krok 2: przetwórz (może rzucić wyjątek)
                .thenApply(processed -> "WYNIK -> " + processed) // krok 3: sformatuj wynik
                .exceptionally(ex -> "BŁĄD podczas przetwarzania: " + ex.getCause().getMessage()); // krok 4: fallback

        String result = chain.get(5, TimeUnit.SECONDS); // czekamy na finalny wynik (blokująco, tylko do demonstracji)
        System.out.println("(simulateFailure=" + simulateFailure + ") -> " + result);
    }

    private static String fetchRawData() {
        System.out.println("[" + Thread.currentThread().getName() + "] pobieram surowe dane...");
        sleepQuiet(50);
        return "42,17,8,23";
    }

    private static int processData(String rawData, boolean simulateFailure) {
        System.out.println("[" + Thread.currentThread().getName() + "] przetwarzam dane: " + rawData);
        if (simulateFailure) {
            throw new IllegalStateException("nieprawidłowy format danych wejściowych");
        }
        return List.of(rawData.split(",")).stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    /**
     * thenApply() na funkcji zwracającej CompletableFuture dałoby
     * CompletableFuture<CompletableFuture<Integer>> – niewygodne zagnieżdżenie.
     * thenCompose() "spłaszcza" to do CompletableFuture<Integer>.
     */
    private static void demoThenApplyVsThenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> base = CompletableFuture.supplyAsync(() -> 10);

        // thenApply + funkcja zwracająca CompletableFuture -> ZAGNIEŻDŻONY typ
        CompletableFuture<CompletableFuture<Integer>> nested = base.thenApply(_Lesson32_CompletableFuture::asyncDouble);
        Integer nestedResult = nested.get().get(); // trzeba "odpakować" DWA razy
        System.out.println("thenApply (zagnieżdżone)   -> trzeba 2x get(), wynik = " + nestedResult);

        // thenCompose + ta sama funkcja -> SPŁASZCZONY typ, jeden poziom
        CompletableFuture<Integer> flattened = base.thenCompose(_Lesson32_CompletableFuture::asyncDouble);
        Integer flattenedResult = flattened.get(); // wystarczy 1x get()
        System.out.println("thenCompose (spłaszczone)  -> wystarczy 1x get(), wynik = " + flattenedResult);
    }

    /** Zwraca CompletableFuture<Integer> – symuluje kolejne asynchroniczne wywołanie. */
    private static CompletableFuture<Integer> asyncDouble(int value) {
        return CompletableFuture.supplyAsync(() -> value * 2);
    }

    /**
     * thenCombine() – dwa NIEZALEŻNE obliczenia (np. cena produktu i kurs
     * waluty pobierane z różnych serwisów) liczone równolegle, a potem
     * połączone w jeden wynik, gdy OBA się zakończą.
     */
    private static void demoThenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> priceInUsd = CompletableFuture.supplyAsync(() -> {
            sleepQuiet(80);
            System.out.println("[" + Thread.currentThread().getName() + "] pobrano cenę produktu: 100.0 USD");
            return 100.0;
        });

        CompletableFuture<Double> usdToPlnRate = CompletableFuture.supplyAsync(() -> {
            sleepQuiet(60);
            System.out.println("[" + Thread.currentThread().getName() + "] pobrano kurs USD/PLN: 4.0");
            return 4.0;
        });

        CompletableFuture<String> combined = priceInUsd.thenCombine(usdToPlnRate,
                (price, rate) -> "Cena w PLN: " + (price * rate));

        System.out.println(combined.get());
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
