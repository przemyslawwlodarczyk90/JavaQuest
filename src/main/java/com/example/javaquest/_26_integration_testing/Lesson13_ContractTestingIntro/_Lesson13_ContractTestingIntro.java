package com.example.javaquest._26_integration_testing.Lesson13_ContractTestingIntro;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Predicate;

public class _Lesson13_ContractTestingIntro {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: Wprowadzenie do testow kontraktowych (Contract Testing) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: WireMock (Lesson07-08) TESTUJE konsumenta - ALE CO Z DOSTAWCA?
         * ============================================================
         * WireMock ZAKLADA, ze zewnetrzne API ZWROCI KONKRETNY ksztalt
         * odpowiedzi ("stub") - ALE SKAD wiadomo, ze PRAWDZIWY dostawca
         * (INNY zespol, INNE repozytorium) FAKTYCZNIE zwraca TAKI
         * ksztalt? Testy KONTRAKTOWE (Consumer-Driven Contracts, CDC)
         * ROZWIAZUJA to: KONSUMENT definiuje "kontrakt" (oczekiwany
         * ksztalt zadania/odpowiedzi), a DOSTAWCA URUCHAMIA TEN SAM
         * kontrakt PRZECIW WLASNEJ, PRAWDZIWEJ implementacji - JESLI
         * OBIE strony przechodza test, MOZNA byc PEWNYM zgodnosci -
         * BEZ potrzeby uruchamiania OBU serwisow RAZEM (end-to-end).
         *
         * 🔍 W PRAWDZIWYM projekcie uzylbys biblioteki Pact
         * (`au.com.dius.pact`) - TU pokazujemy IDEE WLASNYM, prostym
         * mechanizmem (kontrakt = predykat NA strukturze JSON),
         * zeby zrozumiec KONCEPCJE PRZED poznaniem konkretnego
         * narzedzia.
         */
        System.out.println("Contract test = KONSUMENT definiuje oczekiwany ksztalt odpowiedzi, DOSTAWCA weryfikuje TEN SAM kontrakt na WLASNEJ implementacji.");

        demonstrateConsumerDefinedContract();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - KONSUMENT (klient API) definiuje kontrakt: "oczekuje pola
         *   `id` (String), `price` (liczba), `inStock` (boolean)".
         * - DOSTAWCA (autor API) URUCHAMIA TEN SAM kontrakt PRZECIW
         *   WLASNEMU, PRAWDZIWEMU serwerowi - JESLI kontrakt sie
         *   ZMIENI (zespol dostawcy usunie pole), test dostawcy
         *   NATYCHMIAST to WYKRYJE - PRZED wdrozeniem, NIE dopiero
         *   NA produkcji.
         * - PRZEWAGA nad end-to-end: OBA zespoly testuja NIEZALEZNIE
         *   OD SIEBIE (BEZ potrzeby uruchamiania calego systemu
         *   RAZEM) - SZYBSZE, MNIEJ kruche.
         * - Pact (`au.com.dius.pact`) TO STANDARDOWA biblioteka DO
         *   tego W realnych projektach - generuje PLIK kontraktu
         *   (JSON) WYMIENIANY miedzy zespolami (LUB PRZEZ "Pact
         *   Broker").
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    /** "Kontrakt" - definicja OCZEKIWANEGO ksztaltu odpowiedzi, WSPOLNA DLA konsumenta i dostawcy. */
    record ApiContract(String description, Predicate<Map<String, Object>> expectedShape) {
        void verify(Map<String, Object> actualResponse) {
            if (!expectedShape.test(actualResponse)) {
                throw new AssertionError("Odpowiedz NIE SPELNIA kontraktu '" + description + "': " + actualResponse);
            }
        }
    }

    private static final ApiContract PRODUCT_CONTRACT = new ApiContract(
            "GET /products/{id} zwraca id (String), price (Number), inStock (Boolean)",
            body -> body.get("id") instanceof String
                    && body.get("price") instanceof Number
                    && body.get("inStock") instanceof Boolean
    );

    private static void demonstrateConsumerDefinedContract() throws Exception {
        System.out.println("\n--- Strona 1: KONSUMENT weryfikuje TEN SAM kontrakt na (symulowanym) stubie ---");
        Map<String, Object> stubbedResponseFromConsumerPerspective = Map.of(
                "id", "P1", "price", 199.99, "inStock", true);
        PRODUCT_CONTRACT.verify(stubbedResponseFromConsumerPerspective);
        System.out.println("Konsument: stub SPELNIA kontrakt '" + PRODUCT_CONTRACT.description() + "'.");

        System.out.println("\n--- Strona 2: DOSTAWCA uruchamia TEN SAM kontrakt PRZECIW WLASNEMU, PRAWDZIWEMU serwerowi ---");
        HttpServer providerServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        providerServer.createContext("/products/P1", exchange -> {
            byte[] response = "{\"id\":\"P1\",\"price\":199.99,\"inStock\":true}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream body = exchange.getResponseBody()) {
                body.write(response);
            }
        });
        providerServer.start();
        try {
            int port = providerServer.getAddress().getPort();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/products/P1")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> actualBody = parseSimpleJson(response.body());
            PRODUCT_CONTRACT.verify(actualBody);
            System.out.println("Dostawca: PRAWDZIWY serwer TEZ SPELNIA kontrakt - OBIE strony sa ZGODNE, bez uruchamiania end-to-end.");

            System.out.println("\n--- Strona 3: ZLAMANY kontrakt - dostawca USUWA pole 'inStock' (regresja) ---");
            Map<String, Object> brokenResponse = Map.of("id", "P1", "price", 199.99);
            try {
                PRODUCT_CONTRACT.verify(brokenResponse);
                throw new IllegalStateException("Kontrakt POWINIEN byl zostac zlamany");
            } catch (AssertionError e) {
                System.out.println("Kontrakt POPRAWNIE wykryl regresje PRZED wdrozeniem: " + e.getMessage());
            }
        } finally {
            providerServer.stop(0);
        }
    }

    /** Bardzo uproszczony parser JSON (TYLKO na potrzeby tej demonstracji - w praktyce uzyj Jacksona/Gson, _04_io). */
    private static Map<String, Object> parseSimpleJson(String json) {
        String trimmed = json.trim().replaceAll("[{}\"]", "");
        Map<String, Object> result = new java.util.HashMap<>();
        for (String pair : trimmed.split(",")) {
            String[] keyValue = pair.split(":", 2);
            String key = keyValue[0].trim();
            String rawValue = keyValue[1].trim();
            Object value;
            if (rawValue.equals("true") || rawValue.equals("false")) {
                value = Boolean.parseBoolean(rawValue);
            } else if (rawValue.matches("-?\\d+(\\.\\d+)?")) {
                value = Double.parseDouble(rawValue);
            } else {
                value = rawValue;
            }
            result.put(key, value);
        }
        return result;
    }
}
