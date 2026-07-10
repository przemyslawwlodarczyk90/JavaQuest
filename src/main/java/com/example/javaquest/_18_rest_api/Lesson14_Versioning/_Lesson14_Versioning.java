package com.example.javaquest._18_rest_api.Lesson14_Versioning;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class _Lesson14_Versioning {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: WERSJONOWANIE API ===");

        /*
         * ============================================================
         * 📦 DLACZEGO API TRZEBA WERSJONOWAC?
         * ============================================================
         * API ZYJE - z czasem zmieniaja sie wymagania biznesowe, ksztalt
         * danych, reguly walidacji. Problem: gdy Twoje API ma juz
         * ZEWNETRZNYCH klientow (mobilna appka, inny zespol, partner
         * biznesowy), NIE MOZESZ po prostu zmienic kontraktu - zlamiesz
         * IM dzialajacy kod. Wersjonowanie to sposob na WPROWADZANIE
         * niekompatybilnych zmian BEZ psucia istniejacych klientow.
         */

        /*
         * ============================================================
         * 🔹 ZMIANA KOMPATYBILNA vs NIEKOMPATYBILNA (BREAKING)
         * ============================================================
         * KOMPATYBILNE (nie wymagaja nowej wersji):
         *   - dodanie NOWEGO, OPCJONALNEGO pola do odpowiedzi
         *   - dodanie NOWEGO endpointu
         *   - dodanie NOWEJ, opcjonalnej wartosci enuma (ostroznie!)
         * NIEKOMPATYBILNE / BREAKING (wymagaja nowej wersji):
         *   - usuniecie pola
         *   - zmiana TYPU pola (string -> number)
         *   - zmiana NAZWY pola
         *   - zmiana znaczenia istniejacego pola
         *   - zmiana wymaganego statusu pola (opcjonalne -> wymagane)
         */
        demonstrateCompatibleVsBreakingChange();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/v1/users", exchange -> respondV1(exchange));
        server.createContext("/v2/users", exchange -> respondV2(exchange));
        server.createContext("/users-header-versioned", exchange -> respondHeaderVersioned(exchange));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateUriVersioning(client, port);
            demonstrateHeaderVersioning(client, port);
            demonstrateMediaTypeVersioning();
            demonstrateDeprecationHeaders(client, port);
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - 4 glowne strategie wersjonowania:
         *   1. URI (/v1/users)              - proste, widoczne, ALE
         *      "zanieczyszcza" URI (Lesson03: URI to tozsamosc zasobu,
         *      nie powinna zawierac metadanych typu wersja).
         *   2. Naglowek custom (X-API-Version) - czysty URI, ALE mniej
         *      widoczny (trzeba czytac dokumentacje, nie widac w linku).
         *   3. Media type (Accept: application/vnd.api.v2+json) -
         *      "najbardziej RESTful" (Lesson07: to WLASNIE do tego sluzy
         *      Content Negotiation), ALE mniej intuicyjny dla wiekszosci
         *      developerow.
         *   4. Query param (?version=2)      - proste, ale rzadziej
         *      stosowane jako GLOWNA strategia (czesciej jako fallback).
         *   W PRAKTYCE URI-wersjonowanie jest NAJPOPULARNIEJSZE (Stripe,
         *   GitHub czesciowo, wiekszosc publicznych API) - pragmatyzm
         *   wygrywa z "czystoscia" teoretyczna.
         * - Deprecation: naglowki "Deprecation"/"Sunset" ostrzegaja
         *   klientow PRZED wylaczeniem starej wersji, dajac czas na migracje.
         * - Kolejna lekcja (Lesson15): Idempotency - jak bezpiecznie
         *   ponawiac requesty (wazne np. przy migracjach klientow miedzy
         *   wersjami API).
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static void demonstrateCompatibleVsBreakingChange() {
        System.out.println("\n=== ZMIANA KOMPATYBILNA vs BREAKING ===");
        String v1 = "{\"id\":1,\"name\":\"Ala\"}";
        String v1PlusOptionalField = "{\"id\":1,\"name\":\"Ala\",\"avatarUrl\":null}"; // KOMPATYBILNE
        String v2Breaking = "{\"id\":1,\"fullName\":\"Ala Kowalska\"}"; // BREAKING - "name" zniknelo, zastapione "fullName"

        System.out.println("v1:                     " + v1);
        System.out.println("KOMPATYBILNA zmiana:     " + v1PlusOptionalField + "  (dodano OPCJONALNE pole, stare klienty dzialaja bez zmian)");
        System.out.println("BREAKING zmiana:         " + v2Breaking + "  (usunieto/zmieniono 'name' -> stare klienty SIE WYSYPIA)");
    }

    private static void respondV1(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        respond(exchange, "{\"id\":1,\"name\":\"Ala Kowalska\"}"); // v1: 1 pole "name"
    }

    private static void respondV2(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        respond(exchange, "{\"id\":1,\"firstName\":\"Ala\",\"lastName\":\"Kowalska\"}"); // v2: rozbite na 2 pola
    }

    private static void respondHeaderVersioned(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String version = exchange.getRequestHeaders().getFirst("X-API-Version");
        if ("2".equals(version)) {
            respond(exchange, "{\"id\":1,\"firstName\":\"Ala\",\"lastName\":\"Kowalska\"}");
        } else {
            respond(exchange, "{\"id\":1,\"name\":\"Ala Kowalska\"}"); // domyslnie v1, jesli naglowek brakuje
        }
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateUriVersioning(HttpClient client, int port) throws Exception {
        System.out.println("\n=== STRATEGIA 1: WERSJONOWANIE W URI ===");
        var v1 = get(client, port, "/v1/users");
        var v2 = get(client, port, "/v2/users");
        System.out.println("GET /v1/users -> " + v1.body() + "  (stary ksztalt: 'name')");
        System.out.println("GET /v2/users -> " + v2.body() + "  (nowy ksztalt: 'firstName'/'lastName')");
        System.out.println("-> OBIE wersje dzialaja ROWNOLEGLE - stary klient (v1) NIE MUSI nic zmieniac.");
    }

    private static void demonstrateHeaderVersioning(HttpClient client, int port) throws Exception {
        System.out.println("\n=== STRATEGIA 2: WERSJONOWANIE PRZEZ NAGLOWEK ===");
        HttpRequest v1Request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users-header-versioned"))
                .header("X-API-Version", "1")
                .GET().build();
        HttpRequest v2Request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users-header-versioned"))
                .header("X-API-Version", "2")
                .GET().build();
        System.out.println("GET /users-header-versioned, X-API-Version: 1 -> "
                + client.send(v1Request, HttpResponse.BodyHandlers.ofString()).body());
        System.out.println("GET /users-header-versioned, X-API-Version: 2 -> "
                + client.send(v2Request, HttpResponse.BodyHandlers.ofString()).body());
        System.out.println("-> TEN SAM URI dla obu wersji - czystsza sciezka, ale wersja NIEWIDOCZNA w samym linku.");
    }

    private static void demonstrateMediaTypeVersioning() {
        System.out.println("\n=== STRATEGIA 3: WERSJONOWANIE PRZEZ MEDIA TYPE (przypomnienie z Lesson07) ===");
        System.out.println("Accept: application/vnd.javaquest.v1+json  <- wersja zakodowana w TYPIE MEDIA");
        System.out.println("Accept: application/vnd.javaquest.v2+json");
        System.out.println("-> to NAJBARDZIEJ 'zgodne z duchem REST' podejscie (Content Negotiation, Lesson07), ale rzadziej wybierane w praktyce.");
    }

    private static void demonstrateDeprecationHeaders(HttpClient client, int port) throws Exception {
        System.out.println("\n=== OZNACZANIE WERSJI JAKO PRZESTARZALEJ (DEPRECATION) ===");
        System.out.println("Odpowiedz z v1 MOZE zawierac naglowki ostrzegajace:");
        System.out.println("  Deprecation: true");
        System.out.println("  Sunset: Sat, 31 Dec 2026 23:59:59 GMT   <- data calkowitego wylaczenia tej wersji");
        System.out.println("  Link: <https://api.javaquest.pl/docs/migration-v1-to-v2>; rel=\"deprecation\"");
        System.out.println("-> klienci v1 dostaja OSTRZEZENIE z WYPRZEDZENIEM, zamiast nagle przestac dzialac.");
    }

    private static HttpResponse<String> get(HttpClient client, int port, String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
