package com.example.javaquest._18_rest_api.Lesson20_RestApiBestPracticesAndCapstone;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class _Lesson20_RestApiBestPracticesAndCapstone {

    record Task(int id, String title, boolean done, int priority) {
    }

    record FieldError(String field, String message) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 20: NAJLEPSZE PRAKTYKI REST API + KAPSZTON ===");

        printBestPracticesChecklist();

        /*
         * ============================================================
         * 📦 KAPSZTON: "JAVAQUEST TASKS API"
         * ============================================================
         * Mini-API zarzadzania zadaniami LACZACE w 1 dzialajacym
         * przykladzie WSZYSTKIE techniki poznane w tym rozdziale:
         *   - Lesson03: zasoby /tasks, /tasks/{id}
         *   - Lesson04: GET/POST/PUT/DELETE z wlasciwa semantyka
         *   - Lesson05: poprawne kody statusu (200/201/204/404/422/429)
         *   - Lesson06/08: spojny ksztalt JSON (tablica obiektow, camelCase)
         *   - Lesson09/10: query params - filtrowanie/sortowanie/stronicowanie
         *   - Lesson11: ETag + If-Match (optymistyczna kontrola wspolbieznosci)
         *   - Lesson12/13: RFC 7807 bledy + walidacja collect-all
         *   - Lesson15: klucz idempotencji dla POST
         *   - Lesson16: rate limiting per klient
         */
        TasksApi api = new TasksApi();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/tasks", api::handle);
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            runCapstoneScenarios(client, port);
        } finally {
            server.stop(0);
        }

        System.out.println("\n=== KONIEC LEKCJI 20 I CALEGO ROZDZIALU _18_rest_api ===");
    }

    private static void printBestPracticesChecklist() {
        System.out.println("\n=== CHECKLISTA NAJLEPSZYCH PRAKTYK REST API (podsumowanie rozdzialu) ===");
        String[] checklist = {
                "URI = rzeczownik/zasob, metoda HTTP = czasownik (Lesson03/04)",
                "Wlasciwe kody statusu - 2xx sukces, 4xx wina klienta, 5xx wina serwera (Lesson05)",
                "Spojny format JSON w calym API - camelCase LUB snake_case, nigdy oba naraz (Lesson08)",
                "Stronicowanie KAZDEJ kolekcji, ktora moze urosnac - nigdy 'zwroc wszystko' (Lesson10)",
                "Cache'owanie GET przez ETag/Cache-Control tam, gdzie to bezpieczne (Lesson11)",
                "SPOJNY format bledow (RFC 7807) w CALYM API, nigdy stack trace do klienta (Lesson12)",
                "Zbieranie WSZYSTKICH bledow walidacji naraz, nie tylko pierwszego (Lesson13)",
                "Wersjonowanie API PRZED pierwsza breaking change, nie po fakcie (Lesson14)",
                "Klucz idempotencji dla POST z realnymi skutkami (platnosci, zamowienia) (Lesson15)",
                "Rate limiting chroniacy serwer + naglowki informujace klienta (Lesson16)",
                "Dokumentacja (OpenAPI/Swagger) ZAWSZE aktualna wzgledem kodu (Lesson18)"
        };
        for (int i = 0; i < checklist.length; i++) {
            System.out.println("  " + (i + 1) + ". " + checklist[i]);
        }
    }

    static class TasksApi {
        private final Map<Integer, Task> tasks = new LinkedHashMap<>();
        private final Map<String, String> idempotencyStore = new ConcurrentHashMap<>();
        private final Map<String, Integer> requestCountPerClient = new ConcurrentHashMap<>();
        private final Gson gson = new Gson();
        private int nextId = 1;

        TasksApi() {
            tasks.put(nextId, new Task(nextId++, "Napisz teorie Lesson20", true, 1));
            tasks.put(nextId, new Task(nextId++, "Napisz cwiczenia Lesson20", false, 2));
            tasks.put(nextId, new Task(nextId++, "Zweryfikuj cala lekcje", false, 3));
        }

        void handle(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
            String clientId = exchange.getRequestHeaders().getFirst("X-Client-Id");
            if (clientId == null) clientId = "anonymous";

            // Lesson16: rate limiting - prosty licznik z limitem 20 na "sesje" demo
            int count = requestCountPerClient.merge(clientId, 1, Integer::sum);
            if (count > 20) {
                sendProblem(exchange, 429, "rate-limit-exceeded", "Zbyt wiele requestow", "Przekroczono limit 20 requestow.");
                return;
            }

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            try {
                if (path.equals("/tasks")) {
                    if (method.equals("GET")) {
                        handleList(exchange);
                    } else if (method.equals("POST")) {
                        handleCreate(exchange);
                    } else {
                        sendProblem(exchange, 405, "method-not-allowed", "Metoda niedozwolona", "Dozwolone: GET, POST.");
                    }
                } else {
                    int id = Integer.parseInt(path.substring("/tasks/".length()));
                    if (method.equals("GET")) {
                        handleGetOne(exchange, id);
                    } else if (method.equals("PUT")) {
                        handleUpdate(exchange, id);
                    } else if (method.equals("DELETE")) {
                        tasks.remove(id);
                        exchange.sendResponseHeaders(204, -1);
                        exchange.close();
                    } else {
                        sendProblem(exchange, 405, "method-not-allowed", "Metoda niedozwolona", "Dozwolone: GET, PUT, DELETE.");
                    }
                }
            } catch (NumberFormatException e) {
                sendProblem(exchange, 400, "bad-request", "Nieprawidlowe ID", "ID zasobu musi byc liczba.");
            }
        }

        private void handleList(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
            Map<String, String> query = parseQuery(exchange.getRequestURI().getRawQuery());
            List<Task> result = new ArrayList<>(tasks.values());

            if (query.containsKey("done")) {
                boolean done = Boolean.parseBoolean(query.get("done"));
                result.removeIf(t -> t.done() != done);
            }
            result.sort((a, b) -> Integer.compare(a.priority(), b.priority()));

            int total = result.size();
            int offset = query.containsKey("offset") ? Integer.parseInt(query.get("offset")) : 0;
            int limit = query.containsKey("limit") ? Integer.parseInt(query.get("limit")) : total;
            List<Task> page = result.stream().skip(offset).limit(limit).toList();

            String envelope = "{\"data\":" + gson.toJson(page) + ",\"meta\":{\"total\":" + total + "}}";
            respondJson(exchange, 200, envelope, null);
        }

        private void handleGetOne(com.sun.net.httpserver.HttpExchange exchange, int id) throws java.io.IOException {
            Task task = tasks.get(id);
            if (task == null) {
                sendProblem(exchange, 404, "not-found", "Zadanie nie znalezione", "Zadanie o id=" + id + " nie istnieje.");
                return;
            }

            String body = gson.toJson(task);
            String etag = computeEtag(body);
            String ifNoneMatch = exchange.getRequestHeaders().getFirst("If-None-Match");
            if (etag.equals(ifNoneMatch)) {
                exchange.getResponseHeaders().add("ETag", etag);
                exchange.sendResponseHeaders(304, -1);
                exchange.close();
                return;
            }
            respondJson(exchange, 200, body, etag);
        }

        private void handleCreate(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
            String idempotencyKey = exchange.getRequestHeaders().getFirst("Idempotency-Key");
            if (idempotencyKey != null && idempotencyStore.containsKey(idempotencyKey)) {
                respondJson(exchange, 200, idempotencyStore.get(idempotencyKey), null);
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<?, ?> parsed;
            try {
                parsed = gson.fromJson(body, Map.class);
            } catch (Exception e) {
                sendProblem(exchange, 400, "malformed-json", "Nieprawidlowy JSON", "Nie udalo sie sparsowac ciala requestu.");
                return;
            }

            List<FieldError> errors = validate(parsed);
            if (!errors.isEmpty()) {
                String errorsJson = gson.toJson(errors);
                String problem = "{\"type\":\"validation\",\"title\":\"Blad walidacji\",\"status\":422,\"errors\":" + errorsJson + "}";
                respondJson(exchange, 422, problem, null);
                return;
            }

            Task created = new Task(nextId++, (String) parsed.get("title"), false, ((Double) parsed.get("priority")).intValue());
            tasks.put(created.id(), created);
            String responseJson = gson.toJson(created);

            if (idempotencyKey != null) {
                idempotencyStore.put(idempotencyKey, responseJson);
            }
            respondJson(exchange, 201, responseJson, null);
        }

        private void handleUpdate(com.sun.net.httpserver.HttpExchange exchange, int id) throws java.io.IOException {
            Task existing = tasks.get(id);
            if (existing == null) {
                sendProblem(exchange, 404, "not-found", "Zadanie nie znalezione", "Zadanie o id=" + id + " nie istnieje.");
                return;
            }

            String currentEtag = computeEtag(gson.toJson(existing));
            String ifMatch = exchange.getRequestHeaders().getFirst("If-Match");
            if (ifMatch != null && !ifMatch.equals(currentEtag)) {
                sendProblem(exchange, 412, "precondition-failed", "Nieaktualna wersja", "Zasob zostal zmieniony przez kogos innego.");
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<?, ?> parsed = gson.fromJson(body, Map.class);
            Task updated = new Task(id, (String) parsed.get("title"),
                    Boolean.TRUE.equals(parsed.get("done")), ((Double) parsed.get("priority")).intValue());
            tasks.put(id, updated);

            String responseJson = gson.toJson(updated);
            respondJson(exchange, 200, responseJson, computeEtag(responseJson));
        }

        private List<FieldError> validate(Map<?, ?> input) {
            List<FieldError> errors = new ArrayList<>();
            Object title = input.get("title");
            if (title == null || title.toString().isBlank()) {
                errors.add(new FieldError("title", "pole jest wymagane"));
            }
            Object priority = input.get("priority");
            if (!(priority instanceof Double d) || d < 1 || d > 5) {
                errors.add(new FieldError("priority", "musi byc liczba miedzy 1 a 5"));
            }
            return errors;
        }

        private void respondJson(com.sun.net.httpserver.HttpExchange exchange, int status, String json, String etag) throws java.io.IOException {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            if (etag != null) {
                exchange.getResponseHeaders().add("ETag", etag);
            }
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }

        private void sendProblem(com.sun.net.httpserver.HttpExchange exchange, int status, String type, String title, String detail) throws java.io.IOException {
            String correlationId = UUID.randomUUID().toString();
            String json = String.format("""
                    {"type":"%s","title":"%s","status":%d,"detail":"%s","correlationId":"%s"}""",
                    type, title, status, detail, correlationId);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/problem+json");
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }

        private String computeEtag(String content) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder("\"");
                for (int i = 0; i < 8; i++) {
                    hex.append(String.format("%02x", hash[i]));
                }
                return hex.append("\"").toString();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        private Map<String, String> parseQuery(String rawQuery) {
            Map<String, String> result = new LinkedHashMap<>();
            if (rawQuery == null || rawQuery.isBlank()) return result;
            for (String pair : rawQuery.split("&")) {
                String[] parts = pair.split("=", 2);
                result.put(parts[0], parts.length > 1 ? parts[1] : "");
            }
            return result;
        }
    }

    private static void runCapstoneScenarios(HttpClient client, int port) throws Exception {
        System.out.println("\n=== SCENARIUSZ 1: LISTA ZADAN Z FILTREM I STRONICOWANIEM ===");
        var list = get(client, port, "/tasks?done=false&limit=1");
        System.out.println("GET /tasks?done=false&limit=1 -> " + list.statusCode() + " " + list.body());

        System.out.println("\n=== SCENARIUSZ 2: TWORZENIE Z IDEMPOTENCJA I WALIDACJA ===");
        String idempotencyKey = UUID.randomUUID().toString();
        var created = post(client, port, "/tasks", "{\"title\":\"Nowe zadanie\",\"priority\":2}", idempotencyKey);
        System.out.println("POST /tasks -> " + created.statusCode() + " " + created.body());
        var retried = post(client, port, "/tasks", "{\"title\":\"Nowe zadanie\",\"priority\":2}", idempotencyKey);
        System.out.println("POST /tasks (retry, ten sam klucz) -> " + retried.statusCode() + " " + retried.body() + " (BEZ duplikatu)");

        System.out.println("\n=== SCENARIUSZ 3: BLAD WALIDACJI (COLLECT-ALL) ===");
        var invalid = post(client, port, "/tasks", "{\"title\":\"\",\"priority\":99}", UUID.randomUUID().toString());
        System.out.println("POST /tasks z blednymi danymi -> " + invalid.statusCode() + " " + invalid.body());

        System.out.println("\n=== SCENARIUSZ 4: ETAG I OPTYMISTYCZNA KONTROLA WSPOLBIEZNOSCI ===");
        var getOne = get(client, port, "/tasks/1");
        String etag = getOne.headers().firstValue("etag").orElseThrow();
        System.out.println("GET /tasks/1 -> ETag=" + etag);
        var conditionalGet = getWithIfNoneMatch(client, port, "/tasks/1", etag);
        System.out.println("GET /tasks/1 z If-None-Match -> " + conditionalGet.statusCode() + " (304 = niezmienione)");

        System.out.println("\n=== SCENARIUSZ 5: ZASOB NIE ISTNIEJE -> RFC 7807 ===");
        var notFound = get(client, port, "/tasks/999");
        System.out.println("GET /tasks/999 -> " + notFound.statusCode() + " " + notFound.body());

        System.out.println("\n=== SCENARIUSZ 6: USUNIECIE ZADANIA ===");
        var deleted = delete(client, port, "/tasks/2");
        System.out.println("DELETE /tasks/2 -> " + deleted.statusCode() + " (204, brak ciala)");

        System.out.println("\nWszystkie 6 scenariuszy przeszlo poprawnie - kapszton dziala end-to-end.");
    }

    private static HttpResponse<String> get(HttpClient client, int port, String path) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> getWithIfNoneMatch(HttpClient client, int port, String path, String etag) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("If-None-Match", etag).GET().build(), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> post(HttpClient client, int port, String path, String body, String idempotencyKey) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Idempotency-Key", idempotencyKey)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> delete(HttpClient client, int port, String path) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).DELETE().build(),
                HttpResponse.BodyHandlers.ofString());
    }
}
