package com.example.javaquest._18_rest_api.Lesson05_StatusCodes;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson05_StatusCodes {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: KODY STATUSU HTTP ===");

        /*
         * ============================================================
         * 📦 5 KLAS KODOW STATUSU (pierwsza cyfra = klasa)
         * ============================================================
         * 1xx - INFORMACYJNE   - request przyjety, przetwarzanie trwa
         * 2xx - SUKCES         - request zakonczony powodzeniem
         * 3xx - PRZEKIEROWANIE - potrzebna dodatkowa akcja klienta
         * 4xx - BLAD KLIENTA   - request byl NIEPOPRAWNY (wina klienta)
         * 5xx - BLAD SERWERA   - serwer zawiodl przy POPRAWNYM requescie
         *
         * REGULA OGOLNA: klasa kodu MUSI byc PRAWDZIWA. "200 OK" z cialem
         * {"error":"not found"} to KLASYCZNY BLAD projektowy ("lying 200")
         * - klient/proxy/monitoring NIE MOZE odroznic sukcesu od bledu bez
         * parsowania ciala - to lamie cala ideea kodow statusu.
         */
        System.out.println("Klasy: 1xx info, 2xx sukces, 3xx przekierowanie, 4xx blad klienta, 5xx blad serwera.");

        Map<Integer, String> resources = new LinkedHashMap<>();
        resources.put(1, "{\"id\":1,\"name\":\"Zasob testowy\"}");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/resources", exchange -> handleResources(exchange, resources));
        server.createContext("/legacy", exchange -> respond(exchange, 301, "", Map.of("Location", "/resources")));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateSuccessCodes(client, port);
            demonstrateClientErrorCodes(client, port);
            demonstrateServerErrorCode(client, port);
            demonstrateRedirect(client, port);
            demonstrateAuthDistinction(client, port);
            demonstrateAntiPatternLying200();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - NAJWAZNIEJSZE KODY DO ZAPAMIETANIA
         * ============================================================
         * 2xx: 200 OK, 201 Created, 202 Accepted, 204 No Content
         * 3xx: 301 Moved Permanently, 304 Not Modified (Lesson11)
         * 4xx: 400 Bad Request, 401 Unauthorized, 403 Forbidden,
         *      404 Not Found, 405 Method Not Allowed, 409 Conflict,
         *      415 Unsupported Media Type, 422 Unprocessable Entity,
         *      429 Too Many Requests (Lesson16)
         * 5xx: 500 Internal Server Error, 503 Service Unavailable
         *
         * - 401 = "NIE WIEM kim jestes" (brak/zla autentykacja)
         * - 403 = "WIEM kim jestes, ale NIE MASZ uprawnien" (autoryzacja)
         * - Kolejna lekcja (Lesson06): co dokladnie ladzie W CIELE
         *   requestu/odpowiedzi dla tych roznych scenariuszy.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void handleResources(com.sun.net.httpserver.HttpExchange exchange, Map<Integer, String> resources) throws java.io.IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        Integer id = path.equals("/resources") ? null : parseIdOrNull(path.substring("/resources/".length()));

        if (method.equals("POST")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            if (body.isBlank()) {
                respond(exchange, 400, "{\"error\":\"cialo requestu nie moze byc puste\"}", Map.of());
                return;
            }
            int newId = resources.keySet().stream().mapToInt(i -> i).max().orElse(0) + 1;
            resources.put(newId, "{\"id\":" + newId + "}");
            respond(exchange, 201, resources.get(newId), Map.of());
            return;
        }
        if (method.equals("GET") && id == null) {
            respond(exchange, 200, "[" + String.join(",", resources.values()) + "]", Map.of());
            return;
        }
        if (id == null) {
            respond(exchange, 400, "{\"error\":\"nieprawidlowe ID zasobu\"}", Map.of());
            return;
        }
        switch (method) {
            case "GET" -> {
                if (resources.containsKey(id)) {
                    respond(exchange, 200, resources.get(id), Map.of());
                } else {
                    respond(exchange, 404, "{\"error\":\"zasob o id=" + id + " nie istnieje\"}", Map.of());
                }
            }
            case "DELETE" -> {
                resources.remove(id);
                respond(exchange, 204, "", Map.of());
            }
            case "PUT" -> {
                if (id == 999) {
                    // symulacja bledu WEWNETRZNEGO serwera (np. baza danych padla)
                    respond(exchange, 500, "{\"error\":\"wewnetrzny blad serwera\"}", Map.of());
                } else {
                    respond(exchange, 200, resources.getOrDefault(id, "{}"), Map.of());
                }
            }
            default -> respond(exchange, 405, "", Map.of("Allow", "GET, POST, DELETE, PUT"));
        }
    }

    private static Integer parseIdOrNull(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String body, Map<String, String> extraHeaders) throws java.io.IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        if (!body.isEmpty()) {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        }
        extraHeaders.forEach((k, v) -> exchange.getResponseHeaders().add(k, v));
        exchange.sendResponseHeaders(status, body.isEmpty() ? -1 : bytes.length);
        if (!body.isEmpty()) {
            exchange.getResponseBody().write(bytes);
        }
        exchange.close();
    }

    private static void demonstrateSuccessCodes(HttpClient client, int port) throws Exception {
        System.out.println("\n=== 2xx SUKCES: 200 vs 201 vs 204 ===");
        var get = send(client, port, "GET", "/resources/1", null);
        System.out.println("GET  /resources/1  -> " + get.statusCode() + " (200 OK - odczyt istniejacego zasobu)");

        var post = send(client, port, "POST", "/resources", "{\"name\":\"Nowy\"}");
        System.out.println("POST /resources    -> " + post.statusCode() + " (201 Created - utworzono NOWY zasob)");

        var delete = send(client, port, "DELETE", "/resources/2", null);
        System.out.println("DELETE /resources/2-> " + delete.statusCode() + " (204 No Content - sukces, ale NIC do zwrocenia)");
    }

    private static void demonstrateClientErrorCodes(HttpClient client, int port) throws Exception {
        System.out.println("\n=== 4xx BLAD KLIENTA: 400 vs 404 vs 405 ===");
        var notFound = send(client, port, "GET", "/resources/999999", null);
        System.out.println("GET  /resources/999999 -> " + notFound.statusCode() + " (404 Not Found - zasob NIE ISTNIEJE)");

        var badRequest = send(client, port, "POST", "/resources", "");
        System.out.println("POST /resources (puste cialo) -> " + badRequest.statusCode() + " (400 Bad Request - request ZLE SFORMULOWANY)");

        var methodNotAllowed = send(client, port, "PATCH", "/resources/1", "{}");
        System.out.println("PATCH /resources/1 -> " + methodNotAllowed.statusCode()
                + " (405 Method Not Allowed - Allow: " + methodNotAllowed.headers().firstValue("allow").orElse("?") + ")");
    }

    private static void demonstrateServerErrorCode(HttpClient client, int port) throws Exception {
        System.out.println("\n=== 5xx BLAD SERWERA: WINA SERWERA, NIE KLIENTA ===");
        var serverError = send(client, port, "PUT", "/resources/999", "{}");
        System.out.println("PUT /resources/999 -> " + serverError.statusCode()
                + " (500 - request klienta byl POPRAWNY, ale serwer zawiodl WEWNETRZNIE)");
    }

    private static void demonstrateRedirect(HttpClient client, int port) throws Exception {
        System.out.println("\n=== 3xx PRZEKIEROWANIE: 301 Moved Permanently ===");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/legacy"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /legacy -> " + response.statusCode()
                + ", Location=" + response.headers().firstValue("location").orElse("(brak)")
                + "  (stary URL wskazuje na NOWY - klient powinien zapamietac to na przyszlosc)");
    }

    private static void demonstrateAuthDistinction(HttpClient client, int port) {
        System.out.println("\n=== 401 vs 403: KIM JESTES vs CO WOLNO CI ROBIC ===");
        System.out.println("401 Unauthorized - serwer NIE WIE, kim jestes (brak/nieprawidlowe dane logowania).");
        System.out.println("403 Forbidden    - serwer WIE, kim jestes, ale masz ZA MALO uprawnien do tej akcji.");
        System.out.println("-> pelna implementacja z prawdziwym uwierzytelnianiem: `_19_security_basics/Lesson01`.");
    }

    private static void demonstrateAntiPatternLying200() {
        System.out.println("\n=== ANTYWZORZEC: 'KLAMLIWE 200' ===");
        String badPattern = "HTTP 200 OK\nCialo: {\"success\": false, \"error\": \"Zasob nie znaleziony\"}";
        System.out.println("ZLE:\n" + badPattern.indent(2));
        System.out.println("-> monitoring/proxy/cache widzi 200 i mysli 'sukces' - klient MUSI parsowac cialo, zeby wiedziec inaczej.");
        System.out.println("DOBRZE: HTTP 404 Not Found + cialo {\"error\":\"Zasob nie znaleziony\"} - kod statusu MOWI PRAWDE.");
    }

    private static HttpResponse<String> send(HttpClient client, int port, String method, String path, String body) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json");
        HttpRequest request = switch (method) {
            case "GET" -> builder.GET().build();
            case "DELETE" -> builder.DELETE().build();
            case "POST" -> builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
            case "PUT" -> builder.PUT(HttpRequest.BodyPublishers.ofString(body)).build();
            case "PATCH" -> builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body)).build();
            default -> throw new IllegalArgumentException("Nieznana metoda: " + method);
        };
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
