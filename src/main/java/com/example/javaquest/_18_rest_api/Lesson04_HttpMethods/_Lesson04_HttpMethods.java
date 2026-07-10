package com.example.javaquest._18_rest_api.Lesson04_HttpMethods;

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

public class _Lesson04_HttpMethods {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: METODY HTTP JAKO CZASOWNIKI REST ===");

        Map<Integer, String> books = new LinkedHashMap<>();
        books.put(1, "{\"id\":1,\"title\":\"Effective Java\"}");
        books.put(2, "{\"id\":2,\"title\":\"Clean Code\"}");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/books", exchange -> handleBooks(exchange, books));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            /*
             * ============================================================
             * 📦 6 GLOWNYCH METOD HTTP W REST API
             * ============================================================
             * GET     - pobierz zasob (kolekcje lub element), NIE modyfikuje stanu
             * POST    - utworz NOWY zasob w kolekcji (serwer nadaje ID)
             * PUT     - ZASTAP CALY zasob podanym reprezentacja (pelna zamiana)
             * PATCH   - zmodyfikuj CZESC zasobu (czesciowa aktualizacja)
             * DELETE  - usun zasob
             * HEAD    - jak GET, ale BEZ ciala odpowiedzi (tylko naglowki)
             * OPTIONS - zapytaj, jakie metody sa dozwolone dla danego zasobu
             */
            demonstrateGet(client, port);
            demonstratePost(client, port);
            demonstratePutVsPatch(client, port);
            demonstrateDelete(client, port);
            demonstrateHead(client, port);
            demonstrateOptions(client, port);

            /*
             * ============================================================
             * 🔹 SAFE (BEZPIECZNE) METODY
             * ============================================================
             * Metoda jest BEZPIECZNA, jesli NIE zmienia stanu serwera -
             * wywolanie jej NIGDY nie powinno miec efektow ubocznych
             * (poza np. logowaniem/statystykami).
             *   BEZPIECZNE:    GET, HEAD, OPTIONS
             *   NIEBEZPIECZNE: POST, PUT, PATCH, DELETE
             * Konsekwencja praktyczna: przegladarki/proxy/crawlery MOGA
             * swobodnie wykonywac metody bezpieczne (np. prefetch linkow)
             * bez ryzyka - NIGDY nie rob GET, ktory np. usuwa dane!
             */
            System.out.println("\nBEZPIECZNE (nie zmieniaja stanu): GET, HEAD, OPTIONS");
            System.out.println("NIEBEZPIECZNE (moga zmienic stan): POST, PUT, PATCH, DELETE");

            /*
             * ============================================================
             * 🔍 IDEMPOTENTNE METODY (pelny szczegol w Lesson15)
             * ============================================================
             * Metoda jest IDEMPOTENTNA, jesli wywolanie jej WIELOKROTNIE
             * z tymi samymi parametrami daje TAKI SAM efekt koncowy, jak
             * wywolanie jej RAZ (choc same odpowiedzi moga sie roznic,
             * np. 2. DELETE moze zwrocic 404 zamiast 204).
             *   IDEMPOTENTNE:     GET, HEAD, OPTIONS, PUT, DELETE
             *   NIE-IDEMPOTENTNE: POST (kazde wywolanie tworzy NOWY zasob),
             *                     PATCH (ZALEZY od implementacji - patrz
             *                     Lesson15 dla szczegolow)
             */
            demonstrateIdempotencyOfPut(client, port);
            demonstrateNonIdempotencyOfPost(client, port);

        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - TABELA METOD
         * ============================================================
         * Metoda  | Bezpieczna | Idempotentna | Ma cialo requestu?
         * --------|------------|--------------|-------------------
         * GET     | TAK        | TAK          | zwykle NIE
         * HEAD    | TAK        | TAK          | NIE
         * OPTIONS | TAK        | TAK          | zwykle NIE
         * POST    | NIE        | NIE          | TAK
         * PUT     | NIE        | TAK          | TAK (CALY zasob)
         * PATCH   | NIE        | ZALEZY       | TAK (CZESC zasobu)
         * DELETE  | NIE        | TAK          | zwykle NIE
         *
         * Kolejna lekcja (Lesson05): jakie kody statusu odpowiadaja
         * kazdej z tych metod w roznych scenariuszach (sukces/blad).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void handleBooks(com.sun.net.httpserver.HttpExchange exchange, Map<Integer, String> books) throws java.io.IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        Integer id = path.equals("/books") ? null : Integer.parseInt(path.substring("/books/".length()));

        switch (method) {
            case "GET" -> {
                if (id == null) {
                    respond(exchange, 200, "[" + String.join(",", books.values()) + "]", true);
                } else if (books.containsKey(id)) {
                    respond(exchange, 200, books.get(id), true);
                } else {
                    respond(exchange, 404, "", false);
                }
            }
            case "HEAD" -> {
                if (id != null && books.containsKey(id)) {
                    respond(exchange, 200, books.get(id), false); // naglowki jak GET, BEZ ciala
                } else {
                    respond(exchange, 404, "", false);
                }
            }
            case "POST" -> {
                int newId = books.keySet().stream().mapToInt(i -> i).max().orElse(0) + 1;
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                books.put(newId, "{\"id\":" + newId + "," + body.replaceFirst("^\\{", ""));
                respond(exchange, 201, books.get(newId), true);
            }
            case "PUT" -> {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                books.put(id, "{\"id\":" + id + "," + body.replaceFirst("^\\{", ""));
                respond(exchange, 200, books.get(id), true);
            }
            case "PATCH" -> {
                // Uproszczenie demonstracyjne: PATCH tu tylko podmienia caly rekord
                // (w realnym API czesciowa aktualizacja np. przez JSON Merge Patch).
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                books.put(id, "{\"id\":" + id + "," + body.replaceFirst("^\\{", ""));
                respond(exchange, 200, books.get(id), true);
            }
            case "DELETE" -> {
                books.remove(id);
                respond(exchange, 204, "", false);
            }
            case "OPTIONS" -> {
                exchange.getResponseHeaders().add("Allow", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
                respond(exchange, 204, "", false);
            }
            default -> respond(exchange, 405, "", false);
        }
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String body, boolean withBody) throws java.io.IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        if (withBody) {
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
        } else {
            exchange.sendResponseHeaders(status, -1);
        }
        exchange.close();
    }

    private static void demonstrateGet(HttpClient client, int port) throws Exception {
        System.out.println("\n=== GET - POBIERZ ZASOB (bez efektow ubocznych) ===");
        HttpResponse<String> response = send(client, port, "GET", "/books", null);
        System.out.println("GET /books -> " + response.statusCode() + " " + response.body());
    }

    private static void demonstratePost(HttpClient client, int port) throws Exception {
        System.out.println("\n=== POST - UTWORZ NOWY ZASOB (serwer nadaje ID) ===");
        HttpResponse<String> response = send(client, port, "POST", "/books", "{\"title\":\"Refactoring\"}");
        System.out.println("POST /books -> " + response.statusCode() + " " + response.body()
                + "  (201 Created - nowy zasob dostal ID nadane przez SERWER)");
    }

    private static void demonstratePutVsPatch(HttpClient client, int port) throws Exception {
        System.out.println("\n=== PUT (calkowita zamiana) vs PATCH (czesciowa aktualizacja) ===");
        HttpResponse<String> put = send(client, port, "PUT", "/books/1", "{\"title\":\"Effective Java 3rd Edition\"}");
        System.out.println("PUT /books/1 -> " + put.statusCode() + " " + put.body() + "  (ZASTAPIONO caly zasob)");

        HttpResponse<String> patch = send(client, port, "PATCH", "/books/2", "{\"title\":\"Clean Code (Updated)\"}");
        System.out.println("PATCH /books/2 -> " + patch.statusCode() + " " + patch.body() + "  (zmodyfikowano - w praktyce tylko WYBRANE pola)");
    }

    private static void demonstrateDelete(HttpClient client, int port) throws Exception {
        System.out.println("\n=== DELETE - USUN ZASOB ===");
        HttpResponse<String> response = send(client, port, "DELETE", "/books/2", null);
        System.out.println("DELETE /books/2 -> " + response.statusCode() + "  (204 No Content - usuniete, brak ciala odpowiedzi)");
    }

    private static void demonstrateHead(HttpClient client, int port) throws Exception {
        System.out.println("\n=== HEAD - JAK GET, ALE BEZ CIALA ===");
        HttpResponse<String> response = send(client, port, "HEAD", "/books/1", null);
        System.out.println("HEAD /books/1 -> " + response.statusCode()
                + ", Content-Type=" + response.headers().firstValue("content-type").orElse("(brak)")
                + ", dlugosc odebranego ciala=" + response.body().length() + " (powinno byc 0)");
    }

    private static void demonstrateOptions(HttpClient client, int port) throws Exception {
        System.out.println("\n=== OPTIONS - JAKIE METODY SA DOZWOLONE? ===");
        HttpResponse<String> response = send(client, port, "OPTIONS", "/books", null);
        System.out.println("OPTIONS /books -> " + response.statusCode()
                + ", Allow=" + response.headers().firstValue("allow").orElse("(brak)"));
    }

    private static void demonstrateIdempotencyOfPut(HttpClient client, int port) throws Exception {
        System.out.println("\n=== IDEMPOTENCJA PUT: WIELOKROTNE WYWOLANIE = TEN SAM EFEKT KONCOWY ===");
        send(client, port, "PUT", "/books/1", "{\"title\":\"Idempotent Title\"}");
        send(client, port, "PUT", "/books/1", "{\"title\":\"Idempotent Title\"}");
        HttpResponse<String> third = send(client, port, "PUT", "/books/1", "{\"title\":\"Idempotent Title\"}");
        System.out.println("3x PUT /books/1 z tymi samymi danymi -> stan koncowy TAKI SAM: " + third.body());
    }

    private static void demonstrateNonIdempotencyOfPost(HttpClient client, int port) throws Exception {
        System.out.println("\n=== BRAK IDEMPOTENCJI POST: KAZDE WYWOLANIE TWORZY NOWY ZASOB ===");
        HttpResponse<String> first = send(client, port, "POST", "/books", "{\"title\":\"Duplikat\"}");
        HttpResponse<String> second = send(client, port, "POST", "/books", "{\"title\":\"Duplikat\"}");
        System.out.println("1. POST -> " + first.body());
        System.out.println("2. POST (te same dane) -> " + second.body() + "  (INNY zasob, INNE ID - to jest NIE-idempotentnosc)");
    }

    private static HttpResponse<String> send(HttpClient client, int port, String method, String path, String body) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json");
        HttpRequest request = switch (method) {
            case "GET" -> builder.GET().build();
            case "HEAD" -> builder.method("HEAD", HttpRequest.BodyPublishers.noBody()).build();
            case "DELETE" -> builder.DELETE().build();
            case "OPTIONS" -> builder.method("OPTIONS", HttpRequest.BodyPublishers.noBody()).build();
            case "POST" -> builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
            case "PUT" -> builder.PUT(HttpRequest.BodyPublishers.ofString(body)).build();
            case "PATCH" -> builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body)).build();
            default -> throw new IllegalArgumentException("Nieznana metoda: " + method);
        };
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
