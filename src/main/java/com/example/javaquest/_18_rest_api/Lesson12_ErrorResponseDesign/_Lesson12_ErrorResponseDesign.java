package com.example.javaquest._18_rest_api.Lesson12_ErrorResponseDesign;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.Executors;

public class _Lesson12_ErrorResponseDesign {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: PROJEKTOWANIE ODPOWIEDZI BLEDOW ===");

        /*
         * ============================================================
         * 📦 DLACZEGO SPOJNY FORMAT BLEDU JEST WAZNY?
         * ============================================================
         * Klient API musi umiec OBSLUZYC blad PROGRAMISTYCZNIE (nie tylko
         * pokazac go czlowiekowi) - potrzebuje PRZEWIDYWALNEJ struktury,
         * niezaleznie od tego, KTORY endpoint zawiodl. Bez tego kazdy
         * blad wymaga OSOBNEJ logiki parsowania po stronie klienta.
         */
        System.out.println("Cel: KAZDY blad w API ma TA SAMA strukture, niezaleznie od endpointu/przyczyny.");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/orders", _Lesson12_ErrorResponseDesign::handleOrders);
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateRfc7807Format(client, port);
            demonstrateConsistentShapeAcrossDifferentErrors(client, port);
            demonstrateNoLeakingInternalDetails();
            demonstrateCorrelationId(client, port);
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - RFC 7807 "Problem Details for HTTP APIs" definiuje standardowy
         *   ksztalt: type, title, status, detail, instance - MOZESZ dodac
         *   wlasne pola, ale te 5 tworzy solidna baze.
         * - TEN SAM ksztalt bledu dla WSZYSTKICH endpointow API - klient
         *   pisze JEDNA logike obslugi bledow, nie osobna per endpoint.
         * - NIGDY nie ujawniaj stack trace'ow, nazw klas wewnetrznych,
         *   zapytan SQL w odpowiedzi bledu produkcyjnej - to wyciek
         *   informacji uzytecznych dla atakujacego (pelniej w
         *   `_19_security_basics`).
         * - "correlation ID" (unikalny identyfikator requestu) w kazdym
         *   bledzie - pozwala poprosic uzytkownika o ten ID i odnalezc
         *   DOKLADNIE ten request w logach serwera.
         * - Kolejna lekcja (Lesson13): specjalny przypadek bledow -
         *   bledy WALIDACJI z wieloma naruszeniami naraz.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void handleOrders(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String path = exchange.getRequestURI().getPath();
        String correlationId = UUID.randomUUID().toString();

        if (path.equals("/orders/999")) {
            sendProblem(exchange, 404, "https://api.javaquest.pl/errors/not-found", "Zasob nie znaleziony",
                    "Zamowienie o id=999 nie istnieje.", path, correlationId);
        } else if (path.equals("/orders/crash")) {
            // symulacja NIEOCZEKIWANEGO bledu wewnetrznego - NIGDY nie ujawniamy szczegolow!
            sendProblem(exchange, 500, "https://api.javaquest.pl/errors/internal", "Wewnetrzny blad serwera",
                    "Wystapil nieoczekiwany blad. Skontaktuj sie z pomoca techniczna, podajac identyfikator requestu.",
                    path, correlationId);
        } else if (path.equals("/orders/bad")) {
            sendProblem(exchange, 400, "https://api.javaquest.pl/errors/bad-request", "Nieprawidlowe zadanie",
                    "Brakuje wymaganego pola 'customerId'.", path, correlationId);
        } else {
            sendProblem(exchange, 200, null, null, null, null, null); // placeholder - poza zakresem tej lekcji
        }
    }

    private static void sendProblem(com.sun.net.httpserver.HttpExchange exchange, int status, String type,
                                     String title, String detail, String instance, String correlationId) throws java.io.IOException {
        if (status == 200) {
            byte[] okBytes = "{\"ok\":true}".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, okBytes.length);
            exchange.getResponseBody().write(okBytes);
            exchange.close();
            return;
        }

        String json = String.format("""
                {"type":"%s","title":"%s","status":%d,"detail":"%s","instance":"%s","correlationId":"%s"}""",
                type, title, status, detail, instance, correlationId);

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/problem+json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateRfc7807Format(HttpClient client, int port) throws Exception {
        System.out.println("\n=== RFC 7807 'PROBLEM DETAILS' - STANDARDOWY KSZTALT BLEDU ===");
        var response = get(client, port, "/orders/999");
        System.out.println("GET /orders/999 -> " + response.statusCode()
                + ", Content-Type=" + response.headers().firstValue("content-type").orElse("?"));
        System.out.println("Cialo: " + response.body());
        System.out.println("-> pola: type (link do dokumentacji tego bledu), title, status, detail, instance (KTORY endpoint zawiodl).");
    }

    private static void demonstrateConsistentShapeAcrossDifferentErrors(HttpClient client, int port) throws Exception {
        System.out.println("\n=== TEN SAM KSZTALT DLA ROZNYCH RODZAJOW BLEDOW ===");
        var notFound = get(client, port, "/orders/999");
        var badRequest = get(client, port, "/orders/bad");
        var serverError = get(client, port, "/orders/crash");

        System.out.println("404: " + notFound.body());
        System.out.println("400: " + badRequest.body());
        System.out.println("500: " + serverError.body());
        System.out.println("-> WSZYSTKIE maja DOKLADNIE te same klucze JSON - klient parsuje je JEDNYM kawalkiem kodu.");
    }

    private static void demonstrateNoLeakingInternalDetails() {
        System.out.println("\n=== NIGDY NIE UJAWNIAJ SZCZEGOLOW WEWNETRZNYCH ===");
        String leaky = "{\"error\":\"java.sql.SQLException: connection refused at OrderRepository.java:42, query: SELECT * FROM orders WHERE...\"}";
        String safe = "{\"type\":\"...\",\"title\":\"Wewnetrzny blad serwera\",\"detail\":\"Sprobuj ponownie pozniej lub skontaktuj sie z pomoca.\"}";
        System.out.println("ZLE (wyciek stack trace'u/SQL do klienta): " + leaky);
        System.out.println("DOBRZE (ogolny komunikat, szczegoly TYLKO w logach serwera): " + safe);
        System.out.println("-> szczegoly techniczne MUSZA trafic do LOGOW serwera (z correlation ID), NIGDY bezposrednio do klienta.");
    }

    private static void demonstrateCorrelationId(HttpClient client, int port) throws Exception {
        System.out.println("\n=== CORRELATION ID: MOSTEK MIEDZY UZYTKOWNIKIEM A LOGAMI SERWERA ===");
        var response = get(client, port, "/orders/crash");
        System.out.println("Odpowiedz zawiera 'correlationId' - uzytkownik moze go PODAC supportowi,");
        System.out.println("a Ty odnajdziesz DOKLADNIE ten request w logach serwera po tym samym ID: " + response.body());
    }

    private static HttpResponse<String> get(HttpClient client, int port, String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
