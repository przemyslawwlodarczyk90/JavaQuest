package com.example.javaquest._13_libraries.Lesson13_OkHttpAsyncAndInterceptors;

import com.sun.net.httpserver.HttpServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class _Lesson13_OkHttpAsyncAndInterceptors {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Uruchamianie lokalnego serwera HTTP ===");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        server.createContext("/dane", exchange -> {
            String body = "Dane z serwera (lekcja 13)";
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/wolne", exchange -> {
            try {
                Thread.sleep(150); // symulacja wolnej odpowiedzi
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String body = "Odpowiedz po opoznieniu";
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/chroniony", exchange -> {
            String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
            String body = "Authorization otrzymany: " + authHeader;
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/blad", exchange -> {
            byte[] bytes = "Blad serwera".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartowal na porcie: " + port);
        String baseUrl = "http://localhost:" + port;

        try {
            /*
             * ============================================================
             * 📦 WYWOŁANIA ASYNCHRONICZNE - enqueue()
             * ============================================================
             * Obok synchronicznego execute() (Lesson12, blokuje wątek
             * wywołujący), OkHttp oferuje wykonanie ASYNCHRONICZNE przez
             * Call.enqueue(Callback). Żądanie jest wykonywane w tle, na
             * wątku z wewnętrznej puli OkHttp (Dispatcher), a wynik trafia
             * do jednej z dwóch metod interfejsu Callback:
             *   - onResponse(Call call, Response response) - żądanie
             *     dotarło do serwera i otrzymaliśmy odpowiedź (NIEZALEŻNIE
             *     od kodu statusu - 404/500 też trafiają tutaj!)
             *   - onFailure(Call call, IOException e) - problem SIECIOWY
             *     (brak połączenia, timeout, przerwane połączenie)
             *
             * enqueue() NIE blokuje wątku wywołującego - zwraca się od
             * razu, a callback jest wywoływany PÓŹNIEJ, na INNYM wątku.
             * To dokładnie ten sam problem, który znamy z
             * _05_multithreading: main() mógłby zakończyć się PRZED
             * wywołaniem callbacku, więc potrzebujemy synchronizacji.
             *
             * Rozwiązanie - CountDownLatch (poznany w _05_multithreading,
             * Lesson20_Synchronizers): main() czeka (await()) aż callback
             * wywoła countDown(), sygnalizując zakończenie.
             */

            OkHttpClient client = new OkHttpClient.Builder().build();

            Request asyncRequest = new Request.Builder()
                    .url(baseUrl + "/dane")
                    .build();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<String> resultHolder = new AtomicReference<>();

            System.out.println("\n=== Wywolanie asynchroniczne (enqueue) ===");
            System.out.println("Wysylam zadanie... (main() NIE jest zablokowany)");

            client.newCall(asyncRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (response) {
                        resultHolder.set(response.body().string());
                    } finally {
                        latch.countDown();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    resultHolder.set("BLAD: " + e.getMessage());
                    latch.countDown();
                }
            });

            System.out.println("...tutaj main() mogloby robic inne rzeczy, zanim czeka na wynik...");

            boolean completedInTime = latch.await(5, TimeUnit.SECONDS);
            System.out.println("Callback zakonczony w czasie? " + completedInTime);
            System.out.println("Wynik odebrany asynchronicznie: " + resultHolder.get());

            /*
             * ============================================================
             * 🔹 ASYNC + onFailure (problem sieciowy)
             * ============================================================
             * Żeby zademonstrować onFailure, wysyłamy żądanie na port,
             * na którym NIC nie nasłuchuje (odmowa połączenia).
             */

            Request failingRequest = new Request.Builder()
                    .url("http://localhost:1/nieistniejacy") // port 1 - nikt tam nie nasluchuje
                    .build();

            // Krotki connectTimeout, zeby demo NIE czekalo dlugo, gdyby
            // srodowisko zachowywalo sie inaczej niz "natychmiastowy refuse".
            OkHttpClient quickTimeoutClient = client.newBuilder()
                    .connectTimeout(2, TimeUnit.SECONDS)
                    .build();

            CountDownLatch failureLatch = new CountDownLatch(1);
            AtomicReference<String> failureResult = new AtomicReference<>();

            System.out.println("\n=== Wywolanie asynchroniczne z bledem sieciowym ===");
            quickTimeoutClient.newCall(failingRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    response.close();
                    failureResult.set("Niespodziewany sukces");
                    failureLatch.countDown();
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    failureResult.set("onFailure wywolane: " + e.getClass().getSimpleName());
                    failureLatch.countDown();
                }
            });

            failureLatch.await(5, TimeUnit.SECONDS);
            System.out.println("Wynik: " + failureResult.get());

            /*
             * ============================================================
             * 🔍 INTERCEPTORY - Interceptor
             * ============================================================
             * Interceptor to jeden z NAJWAŻNIEJSZYCH unikalnych mechanizmów
             * OkHttp - pozwala przechwycić KAŻDE żądanie (i odpowiedź)
             * przechodzące przez OkHttpClient, w jednym, spójnym miejscu -
             * zamiast powtarzać tę samą logikę (np. dodawanie nagłówka
             * autoryzacji) w każdym miejscu, gdzie budujemy Request.
             *
             * Interfejs Interceptor ma jedną metodę:
             *   Response intercept(Chain chain) throws IOException
             *
             * Chain reprezentuje "łańcuch" przetwarzania żądania -
             * chain.request() daje dostęp do bieżącego Request, a
             * chain.proceed(request) PRZEKAZUJE żądanie DALEJ (do kolejnego
             * interceptora albo do faktycznego wykonania) i zwraca Response.
             *
             * Interceptor rejestrujemy przez
             * OkHttpClient.Builder().addInterceptor(...) - RAZ, przy
             * budowie klienta - i od tej pory działa dla WSZYSTKICH żądań
             * wykonanych tym klientem.
             */

            Interceptor authInterceptor = chain -> {
                Request originalRequest = chain.request();
                Request requestWithAuth = originalRequest.newBuilder()
                        .header("Authorization", "Bearer demo-token-123")
                        .build();
                return chain.proceed(requestWithAuth);
            };

            /*
             * ============================================================
             * 🔹 WŁASNY INTERCEPTOR LOGUJĄCY
             * ============================================================
             * Biblioteka OkHttp ma oficjalny, dodatkowy artefakt
             * "logging-interceptor" (HttpLoggingInterceptor) - w tej
             * lekcji, żeby NIE dodawać kolejnej zależności do pom.xml,
             * piszemy WŁASNY, prosty interceptor logujący metodę, URL,
             * kod odpowiedzi i czas trwania żądania.
             */

            Interceptor loggingInterceptor = chain -> {
                Request request = chain.request();
                long startNanos = System.nanoTime();
                System.out.println("[LOG] --> " + request.method() + " " + request.url());

                Response response = chain.proceed(request);

                long durationMs = (System.nanoTime() - startNanos) / 1_000_000;
                System.out.println("[LOG] <-- " + response.code() + " (" + durationMs + " ms) " + request.url());
                return response;
            };

            OkHttpClient clientWithInterceptors = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            System.out.println("\n=== Klient z interceptorami (auth + logging) ===");

            Request protectedRequest = new Request.Builder()
                    .url(baseUrl + "/chroniony")
                    .build();

            try (Response response = clientWithInterceptors.newCall(protectedRequest).execute()) {
                System.out.println("Odpowiedz serwera: " + response.body().string());
                // Zauważ: SAMI nigdzie nie ustawialiśmy nagłówka Authorization
                // przy budowie protectedRequest - dodał go authInterceptor
                // AUTOMATYCZNIE, dla KAŻDEGO żądania przechodzącego przez
                // clientWithInterceptors.
            }

            System.out.println("\n=== Kolejne zadanie przez ten sam klient (interceptory dzialaja ponownie) ===");
            Request slowRequest = new Request.Builder()
                    .url(baseUrl + "/wolne")
                    .build();
            try (Response response = clientWithInterceptors.newCall(slowRequest).execute()) {
                System.out.println("Odpowiedz: " + response.body().string());
            }

            /*
             * ============================================================
             * 🔍 APPLICATION INTERCEPTOR vs NETWORK INTERCEPTOR
             * ============================================================
             * addInterceptor(...) rejestruje tzw. APPLICATION interceptor -
             * działa na "wysokim poziomie", widzi oryginalne żądanie
             * użytkownika i finalną odpowiedź. Wywoływany DOKŁADNIE RAZ na
             * każde wywołanie Call, nawet jeśli OkHttp wykona wewnętrznie
             * retry albo podąży za przekierowaniem.
             *
             * addNetworkInterceptor(...) rejestruje NETWORK interceptor -
             * działa "bliżej sieci", widzi żądanie/odpowiedź TUŻ PRZED/PO
             * faktycznej transmisji przez połączenie TCP. Może być
             * wywołany WIELOKROTNIE dla jednego Call (np. przy retry lub
             * przekierowaniu) i ma dostęp do informacji o połączeniu
             * (np. chain.connection()).
             *
             * Praktyczna zasada wyboru:
             *   - logowanie "co użytkownik poprosił / co dostał", dodawanie
             *     nagłówków biznesowych (autoryzacja), cache'owanie na
             *     poziomie aplikacji -> APPLICATION interceptor
             *     (addInterceptor)
             *   - obserwacja NISKOPOZIOMOWA (np. rozmiar danych faktycznie
             *     przesłanych przez sieć, kompresja) -> NETWORK interceptor
             *     (addNetworkInterceptor)
             *
             * W tej lekcji użyliśmy WYŁĄCZNIE application interceptorów -
             * to zdecydowanie częstszy przypadek w praktyce.
             */

            System.out.println("\n=== Roznica application vs network interceptor - patrz komentarz w kodzie ===");

        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - call.enqueue(Callback) - wywołanie ASYNCHRONICZNE, nie
         *   blokuje wątku wywołującego, wynik w onResponse/onFailure na
         *   wątku z puli OkHttp
         * - onResponse wywoływane dla KAŻDEJ odpowiedzi HTTP (nawet 404/500),
         *   onFailure TYLKO dla problemów sieciowych
         * - CountDownLatch - ten sam wzorzec co w _05_multithreading, żeby
         *   main() poczekał na wynik callbacku zanim się zakończy
         * - Interceptor - przechwytuje KAŻDE żądanie/odpowiedź przechodzące
         *   przez OkHttpClient - chain.request() / chain.proceed(request)
         * - addInterceptor() - application interceptor (jeden raz na Call,
         *   wysoki poziom) - typowe użycie: autoryzacja, logowanie
         * - addNetworkInterceptor() - network interceptor (bliżej sieci,
         *   może się powtórzyć przy retry/redirect)
         * - własny interceptor logujący to prosta alternatywa dla
         *   oficjalnego (osobna zależność) HttpLoggingInterceptor
         */

        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }
}
