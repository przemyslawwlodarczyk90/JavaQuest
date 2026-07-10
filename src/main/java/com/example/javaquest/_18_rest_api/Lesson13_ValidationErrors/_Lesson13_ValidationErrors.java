package com.example.javaquest._18_rest_api.Lesson13_ValidationErrors;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson13_ValidationErrors {

    record FieldError(String field, String message, Object rejectedValue) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: BLEDY WALIDACJI ===");

        /*
         * ============================================================
         * 📦 DLACZEGO BLEDY WALIDACJI SA SPECJALNYM PRZYPADKIEM?
         * ============================================================
         * Zwykly blad (Lesson12) opisuje 1 problem. Blad walidacji
         * CZESTO ma WIELE jednoczesnych naruszen (np. brakujacy email
         * ORAZ ujemny wiek ORAZ za krotkie haslo) - klient (zwlaszcza UI
         * formularza) chce je WSZYSTKIE naraz, zeby pokazac uzytkownikowi
         * KOMPLETNA liste bledow za 1 razem, zamiast kazac mu poprawiac
         * je 1 po 1 przy kazdym kolejnym submicie.
         *
         * To bezposrednio nawiazuje do warstwy walidacji na granicy API
         * z `_17_architecture/Lesson15_ValidationArchitecture` - ta
         * lekcja pokazuje, jak taka walidacja WYGLADA W ODPOWIEDZI HTTP.
         */
        System.out.println("Blad walidacji = WIELE jednoczesnych naruszen naraz, nie tylko pierwsze napotkane.");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/users", _Lesson13_ValidationErrors::handleCreateUser);
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateFailFastVsCollectAll();
            demonstrateValidationErrorShape(client, port);
            demonstrateSuccessfulValidation(client, port);
            demonstrateCrossFieldValidation();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Blad walidacji rozszerza podstawowy ksztalt bledu (Lesson12)
         *   o tablice "errors": [{field, message, rejectedValue}, ...].
         * - ZBIERAJ wszystkie naruszenia naraz (collect-all), nie
         *   przerywaj na pierwszym (fail-fast) - lepsze doswiadczenie
         *   klienta formularza.
         * - Walidacja pola (1 wartosc) vs walidacja miedzy-polowa
         *   (relacja miedzy wieloma polami, np. "dataOd < dataDo") -
         *   ta druga NIE ma jednego "field", moze uzyc specjalnej
         *   wartosci (np. "_global"/"form").
         * - Status kodu: 400 (request ZLE SFORMULOWANY, np. zly typ JSON)
         *   vs 422 (POPRAWNY JSON, ale narusza regule biznesowa/format
         *   pola) - por. Lesson05.
         * - Kolejna lekcja (Lesson14): jak wersjonowac API, gdy z czasem
         *   zmieniaja sie reguly walidacji/ksztalt zasobow.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static void demonstrateFailFastVsCollectAll() {
        System.out.println("\n=== FAIL-FAST vs COLLECT-ALL ===");

        record Input(String email, int age, String password) {
        }
        Input badInput = new Input("", -5, "123");

        // FAIL-FAST: zatrzymuje sie na PIERWSZYM naruszeniu
        List<String> failFastErrors = new ArrayList<>();
        if (badInput.email().isBlank()) {
            failFastErrors.add("email jest wymagany");
        } else if (badInput.age() < 0) {
            failFastErrors.add("wiek nie moze byc ujemny");
        } // reszta warunkow NIGDY nie sprawdzona, bo uzylismy else-if

        // COLLECT-ALL: sprawdza WSZYSTKIE warunki niezaleznie
        List<String> collectAllErrors = new ArrayList<>();
        if (badInput.email().isBlank()) {
            collectAllErrors.add("email jest wymagany");
        }
        if (badInput.age() < 0) {
            collectAllErrors.add("wiek nie moze byc ujemny");
        }
        if (badInput.password().length() < 8) {
            collectAllErrors.add("haslo musi miec min. 8 znakow");
        }

        System.out.println("FAIL-FAST (tylko 1 blad):    " + failFastErrors);
        System.out.println("COLLECT-ALL (wszystkie naraz): " + collectAllErrors);
        System.out.println("-> COLLECT-ALL daje uzytkownikowi KOMPLETNY obraz za 1 requestem.");
    }

    private static void handleCreateUser(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Map<?, ?> parsed = gson.fromJson(body, Map.class);

        List<FieldError> errors = validate(parsed);

        if (!errors.isEmpty()) {
            StringBuilder errorsJson = new StringBuilder("[");
            for (int i = 0; i < errors.size(); i++) {
                FieldError e = errors.get(i);
                if (i > 0) errorsJson.append(",");
                errorsJson.append(String.format("{\"field\":\"%s\",\"message\":\"%s\",\"rejectedValue\":\"%s\"}",
                        e.field(), e.message(), e.rejectedValue()));
            }
            errorsJson.append("]");

            String problemJson = String.format("""
                    {"type":"https://api.javaquest.pl/errors/validation","title":"Blad walidacji","status":422,"errors":%s}""",
                    errorsJson);

            byte[] bytes = problemJson.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/problem+json");
            exchange.sendResponseHeaders(422, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
            return;
        }

        byte[] okBytes = "{\"status\":\"utworzono\"}".getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(201, okBytes.length);
        exchange.getResponseBody().write(okBytes);
        exchange.close();
    }

    /**
     * Zbiera WSZYSTKIE naruszenia naraz (collect-all) - kazdy warunek
     * sprawdzany NIEZALEZNIE, bez wczesnego wyjscia.
     */
    private static List<FieldError> validate(Map<?, ?> input) {
        List<FieldError> errors = new ArrayList<>();

        Object email = input.get("email");
        if (email == null || email.toString().isBlank()) {
            errors.add(new FieldError("email", "pole jest wymagane", email));
        } else if (!email.toString().contains("@")) {
            errors.add(new FieldError("email", "musi byc poprawnym adresem email", email));
        }

        Object age = input.get("age");
        if (age == null) {
            errors.add(new FieldError("age", "pole jest wymagane", null));
        } else if (age instanceof Double d && d < 0) {
            errors.add(new FieldError("age", "nie moze byc ujemny", age));
        }

        Object password = input.get("password");
        if (password == null || password.toString().length() < 8) {
            errors.add(new FieldError("password", "musi miec minimum 8 znakow", password));
        }

        return errors;
    }

    private static void demonstrateValidationErrorShape(HttpClient client, int port) throws Exception {
        System.out.println("\n=== KSZTALT ODPOWIEDZI BLEDU WALIDACJI ===");
        String badBody = "{\"email\":\"\",\"age\":-3,\"password\":\"123\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .POST(HttpRequest.BodyPublishers.ofString(badBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /users z 3 naruszeniami naraz -> " + response.statusCode());
        System.out.println("Cialo: " + response.body());
        System.out.println("-> WSZYSTKIE 3 bledy zwrocone w 1 odpowiedzi, klient moze pokazac je RAZEM w formularzu.");
    }

    private static void demonstrateSuccessfulValidation(HttpClient client, int port) throws Exception {
        System.out.println("\n=== POPRAWNE DANE -> BRAK BLEDOW WALIDACJI ===");
        String goodBody = "{\"email\":\"ala@example.com\",\"age\":25,\"password\":\"bezpieczneHaslo123\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .POST(HttpRequest.BodyPublishers.ofString(goodBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /users z poprawnymi danymi -> " + response.statusCode() + " " + response.body());
    }

    private static void demonstrateCrossFieldValidation() {
        System.out.println("\n=== WALIDACJA MIEDZY-POLOWA (CROSS-FIELD) ===");
        record DateRange(String startDate, String endDate) {
        }
        DateRange invalid = new DateRange("2026-08-01", "2026-07-01"); // koniec PRZED poczatkiem

        List<FieldError> errors = new ArrayList<>();
        if (invalid.endDate().compareTo(invalid.startDate()) < 0) {
            // ten blad NIE dotyczy 1 konkretnego pola - obejmuje relacje MIEDZY nimi
            errors.add(new FieldError("_global", "endDate nie moze byc wczesniejszy niz startDate", null));
        }
        System.out.println("Blad miedzy-polowy: " + errors);
        System.out.println("-> uzyto \"_global\" jako 'field', bo blad dotyczy RELACJI, nie 1 konkretnej wartosci.");
    }
}
