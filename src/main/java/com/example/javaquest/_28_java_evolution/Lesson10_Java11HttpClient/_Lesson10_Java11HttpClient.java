package com.example.javaquest._28_java_evolution.Lesson10_Java11HttpClient;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson10_Java11HttpClient {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: Java 11 - java.net.http.HttpClient (standaryzowany) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_06_networking/Lesson09-12`
         * ============================================================
         * `java.net.http.HttpClient` ZACZAL zycie JAKO API incubator
         * W Javie 9 (2017, pakiet `jdk.incubator.http`), ZOSTAL
         * PRZEPROJEKTOWANY I USTANDARYZOWANY W Javie 11 (2018, pakiet
         * FINALNY `java.net.http`) - TO WLASNIE TA wersja jest uzywana
         * W CALYM tym kursie (`_06_networking/Lesson09_URLConnection`
         * az DO `Lesson12_JsonOverNetwork`, ORAZ `_18_rest_api`,
         * `_19_security_basics`).
         *
         * 🔍 TA lekcja NIE POWTARZA API krok PO kroku (`_06_networking`
         * JUZ to zrobil) - TU demo POKAZUJE KONTRAST Z jeszcze
         * STARSZYM `HttpURLConnection` (Java 1.1!, wciaz obecny W JDK,
         * ale NIEWYGODNY) I DODATKOWO wsparcie HTTP/2 "z pudelka",
         * KTOREGO `HttpURLConnection` NIGDY nie dostal.
         */
        System.out.println("Java 11 (2018): java.net.http.HttpClient ustandaryzowany (incubator od Javy 9). Pelne API: _06_networking/Lesson09-12.");

        HttpServer serwerTestowy = uruchomLokalnyServerTestowy();
        try {
            int port = serwerTestowy.getAddress().getPort();
            demonstrateOldVsNewApi(port);
            demonstrateHttp2SupportOutOfTheBox(port);
            demonstrateAsyncSendWithoutBlockingThread(port);
        } finally {
            serwerTestowy.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `HttpURLConnection` (Java 1.1, 1997) - STARE API, IMPERATYWNE
         *   (otwieranie polaczenia, reczne czytanie strumienia,
         *   brak wsparcia HTTP/2, brak natywnej asynchronicznosci).
         * - `java.net.http.HttpClient` (Java 11, 2018) - NOWE API,
         *   BUILDER-owe (`HttpRequest.newBuilder()...build()`),
         *   NATYWNE wsparcie HTTP/2 (negocjowane automatycznie),
         *   `sendAsync()` zwracajacy `CompletableFuture` (BEZ blokowania
         *   watku - powiazanie Z `_14_advancedjava/Lesson32`).
         * - Pelna teoria/przyklady: `_06_networking/Lesson09-12`.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static HttpServer uruchomLokalnyServerTestowy() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/hello", exchange -> {
            byte[] odpowiedz = "Witaj z lokalnego serwera testowego!".getBytes();
            exchange.sendResponseHeaders(200, odpowiedz.length);
            exchange.getResponseBody().write(odpowiedz);
            exchange.close();
        });
        server.start();
        return server;
    }

    private static void demonstrateOldVsNewApi(int port) throws Exception {
        System.out.println("\n--- STARE API: java.net.HttpURLConnection (Java 1.1, 1997) ---");
        java.net.URL url = java.net.URI.create("http://localhost:" + port + "/hello").toURL();
        java.net.HttpURLConnection stareConnection = (java.net.HttpURLConnection) url.openConnection();
        stareConnection.setRequestMethod("GET");
        int stareKodStatusu = stareConnection.getResponseCode();
        String stareCialo;
        try (var in = stareConnection.getInputStream()) {
            stareCialo = new String(in.readAllBytes());
        }
        stareConnection.disconnect();
        System.out.println("HttpURLConnection: kod=" + stareKodStatusu + ", cialo=\"" + stareCialo + "\"");
        System.out.println("Wady: imperatywne API, reczne strumienie, BRAK wsparcia HTTP/2, BRAK natywnej asynchronicznosci.");

        System.out.println("\n--- NOWE API: java.net.http.HttpClient (Java 11, 2018) ---");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/hello"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("HttpClient: kod=" + response.statusCode() + ", cialo=\"" + response.body() + "\"");
        System.out.println("Zalety: builder API, wsparcie HTTP/2 z pudelka, natywna asynchronicznosc (sendAsync).");

        assertThat(stareKodStatusu).isEqualTo(response.statusCode());
        assertThat(stareCialo).isEqualTo(response.body());
    }

    private static void demonstrateHttp2SupportOutOfTheBox(int port) throws Exception {
        System.out.println("\n--- HttpClient NEGOCJUJE HTTP/2, JESLI serwer go WSPIERA (fallback DO HTTP/1.1) ---");
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/hello"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Zadana wersja: HTTP_2, faktycznie uzyta (negocjacja): " + response.version());
        System.out.println("Nasz lokalny com.sun.net.httpserver.HttpServer wspiera TYLKO HTTP/1.1 - klient AUTOMATYCZNIE spadl do HTTP/1.1 (bez bledu).");
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private static void demonstrateAsyncSendWithoutBlockingThread(int port) throws Exception {
        System.out.println("\n--- sendAsync() - CompletableFuture, watek NIE jest blokowany podczas oczekiwania ---");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/hello"))
                .build();

        var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("client.sendAsync(...) zwrocil natychmiast CompletableFuture<HttpResponse<String>> - watek glowny MOZE robic cos innego W MIEDZYCZASIE.");

        HttpResponse<String> response = future.get();
        System.out.println("Po future.get(): kod=" + response.statusCode());
        System.out.println("Pelna teoria CompletableFuture: _14_advancedjava/Lesson32_CompletableFuture.");

        assertThat(response.statusCode()).isEqualTo(200);
    }
}
