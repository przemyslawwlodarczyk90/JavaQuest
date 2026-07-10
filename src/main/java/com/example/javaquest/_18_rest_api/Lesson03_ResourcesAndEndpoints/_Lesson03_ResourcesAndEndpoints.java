package com.example.javaquest._18_rest_api.Lesson03_ResourcesAndEndpoints;

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

public class _Lesson03_ResourcesAndEndpoints {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: ZASOBY I ENDPOINTY ===");

        /*
         * ============================================================
         * 📦 ZASOB TO RZECZOWNIK, NIE CZASOWNIK
         * ============================================================
         * Fundamentalna zasada projektowania URI w REST: URI identyfikuje
         * ZASOB (rzeczownik - "co to jest"), a NIE AKCJE (czasownik -
         * "co zrobic") - to, JAKA AKCJE wykonac, wyraza METODA HTTP
         * (Lesson04), NIE sciezka.
         *
         *   ZLE (URI jak wywolanie funkcji):
         *     POST /getUser?id=42
         *     POST /createUser
         *     POST /deleteUser?id=42
         *
         *   DOBRZE (URI = zasob, metoda = akcja):
         *     GET    /users/42
         *     POST   /users
         *     DELETE /users/42
         */
        demonstrateVerbBasedVsNounBasedUri();

        /*
         * ============================================================
         * 🔹 KOLEKCJA vs POJEDYNCZY ZASOB
         * ============================================================
         * - /users      -> KOLEKCJA wszystkich uzytkownikow
         * - /users/42   -> POJEDYNCZY, KONKRETNY uzytkownik (id=42)
         * Ten sam wzorzec (URI kolekcji vs URI elementu) powtarza sie dla
         * KAZDEGO zasobu w API - to jest fundament spojnosci ("uniform
         * interface" z Lesson02).
         */
        demonstrateCollectionVsSingleResource();

        /*
         * ============================================================
         * 🔍 ZASOBY ZAGNIEZDZONE (relacje miedzy zasobami)
         * ============================================================
         * - /users/42/orders       -> zamowienia UZYTKOWNIKA 42
         * - /users/42/orders/7     -> KONKRETNE zamowienie 7 uzytkownika 42
         * Zagniezdzenie wyraza relacje "nalezy do" ("orders sa CZESCIA
         * users/42"). WAZNE: nie zagniezdzaj zbyt gleboko (regula
         * praktyczna: max 2-3 poziomy) - /users/42/orders/7/items/3/reviews/9
         * jest juz nieczytelne i krucha (kazda zmiana w hierarchii wymaga
         * zmiany calej sciezki). Zbyt gleboko zagniezdzone zasoby czesto
         * lepiej wyplaszczyc do wlasnego, "plaskiego" zasobu z parametrem
         * filtrujacym: GET /reviews?itemId=3.
         */
        demonstrateNestedResources();

        /*
         * ============================================================
         * 🔍 KONWENCJE NAZEWNICTWA URI (praktyczny standard branzowy)
         * ============================================================
         * - RZECZOWNIKI W LICZBIE MNOGIEJ dla kolekcji: /users, NIE /user
         *   (spojnosc: ZAWSZE mnoga, nawet dla /users/42 - to wciaz "element
         *   kolekcji users").
         * - MALE LITERY, myslniki (kebab-case) dla wielu slow:
         *   /order-items, NIE /orderItems ani /OrderItems ani /order_items.
         * - BEZ rozszerzen plikow w URI: /users/42, NIE /users/42.json
         *   (format wybiera naglowek Accept - Lesson07, NIE rozszerzenie).
         * - BEZ koncowego ukosnika: /users, NIE /users/ (niespojnosc,
         *   niektore serwery traktuja je jako 2 rozne zasoby).
         * - ID zasobu jako SEGMENT sciezki, NIE jako parametr query:
         *   /users/42, NIE /users?id=42 (id jest CZESCIA tozsamosci
         *   zasobu, query sluzy do filtrowania/sortowania - Lesson09/10).
         */
        demonstrateNamingConventions();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - URI = rzeczownik (zasob), metoda HTTP = czasownik (akcja) -
         *   NIGDY na odwrot.
         * - Kolekcja (/users) vs element (/users/42) - ten sam wzorzec dla
         *   kazdego zasobu w API.
         * - Zagniezdzenie wyraza relacje "nalezy do" - max 2-3 poziomy,
         *   glebiej -> rozwaz wyplaszczenie z parametrem filtrujacym.
         * - Konwencje: liczba mnoga, male litery + kebab-case, bez
         *   rozszerzen plikow, bez koncowego ukosnika, ID w sciezce nie w query.
         * - Kolejna lekcja (Lesson04): jak WLASCIWIE dobrac metode HTTP do
         *   operacji na tych zasobach.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateVerbBasedVsNounBasedUri() throws Exception {
        System.out.println("\n=== ZASOB (RZECZOWNIK) vs AKCJA (CZASOWNIK) W URI ===");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());

        // ZLY styl - URI jak nazwa funkcji, akcja "wciagnieta" do sciezki.
        server.createContext("/getUser", exchange -> respondJson(exchange, "{\"id\":42,\"name\":\"Ala\"}"));

        // DOBRY styl - URI = zasob, metoda HTTP niesie znaczenie akcji.
        server.createContext("/users", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/users/42")) {
                respondJson(exchange, "{\"id\":42,\"name\":\"Ala\"}");
            } else {
                respondJson(exchange, "[{\"id\":42,\"name\":\"Ala\"}]");
            }
        });

        server.start();
        int port = server.getAddress().getPort();
        try {
            HttpClient client = HttpClient.newHttpClient();
            String badStyle = get(client, port, "/getUser?id=42");
            String goodStyle = get(client, port, "/users/42");
            System.out.println("ZLY styl  (czasownik w URI): GET /getUser?id=42  -> " + badStyle);
            System.out.println("DOBRY styl (rzeczownik+metoda): GET /users/42    -> " + goodStyle);
            System.out.println("-> oba zwracaja TE SAME dane, ale tylko drugi jest zgodny z konwencja REST.");
        } finally {
            server.stop(0);
        }
    }

    private static void demonstrateCollectionVsSingleResource() throws Exception {
        System.out.println("\n=== KOLEKCJA (/users) vs POJEDYNCZY ZASOB (/users/42) ===");

        Map<Integer, String> users = new LinkedHashMap<>();
        users.put(1, "Ala Kowalska");
        users.put(2, "Jan Nowak");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/users", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/users")) {
                respondJson(exchange, users.toString());
            } else {
                String idPart = path.substring("/users/".length());
                int id = Integer.parseInt(idPart);
                String name = users.get(id);
                if (name == null) {
                    exchange.sendResponseHeaders(404, -1);
                    exchange.close();
                } else {
                    respondJson(exchange, "{\"id\":" + id + ",\"name\":\"" + name + "\"}");
                }
            }
        });
        server.start();
        int port = server.getAddress().getPort();
        try {
            HttpClient client = HttpClient.newHttpClient();
            System.out.println("GET /users    -> " + get(client, port, "/users") + "  (KOLEKCJA)");
            System.out.println("GET /users/2  -> " + get(client, port, "/users/2") + "  (POJEDYNCZY ELEMENT)");
        } finally {
            server.stop(0);
        }
    }

    private static void demonstrateNestedResources() throws Exception {
        System.out.println("\n=== ZASOBY ZAGNIEZDZONE: /users/{id}/orders ===");

        Map<Integer, String> ordersByUser = Map.of(42, "[{\"orderId\":7,\"total\":150.0}]");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/users", exchange -> {
            String path = exchange.getRequestURI().getPath();
            // "/users/42/orders" -> segmenty: ["", "users", "42", "orders"]
            String[] segments = path.split("/");
            if (segments.length == 4 && segments[3].equals("orders")) {
                int userId = Integer.parseInt(segments[2]);
                respondJson(exchange, ordersByUser.getOrDefault(userId, "[]"));
            } else {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
            }
        });
        server.start();
        int port = server.getAddress().getPort();
        try {
            HttpClient client = HttpClient.newHttpClient();
            System.out.println("GET /users/42/orders -> " + get(client, port, "/users/42/orders")
                    + "  (zamowienia NALEZACE DO uzytkownika 42)");
            System.out.println("-> gdyby to bylo /users/42/orders/7/items/3/reviews/9 - juz za gleboko, rozwaz /reviews?itemId=3.");
        } finally {
            server.stop(0);
        }
    }

    private static void demonstrateNamingConventions() {
        System.out.println("\n=== SZYBKI TEST KONWENCJI NAZEWNICTWA URI ===");

        record UriCheck(String uri, boolean poprawny, String uzasadnienie) {
        }

        java.util.List<UriCheck> checks = java.util.List.of(
                new UriCheck("/users", true, "liczba mnoga, male litery"),
                new UriCheck("/user", false, "liczba pojedyncza - powinno byc /users"),
                new UriCheck("/order-items", true, "kebab-case dla wielu slow"),
                new UriCheck("/orderItems", false, "camelCase - powinno byc /order-items"),
                new UriCheck("/users/42.json", false, "rozszerzenie pliku - format wybiera naglowek Accept"),
                new UriCheck("/users/", false, "zbedny koncowy ukosnik - niespojne z /users"),
                new UriCheck("/users?id=42", false, "id w query zamiast w sciezce - powinno byc /users/42")
        );

        checks.forEach(c -> System.out.println("  " + (c.poprawny() ? "OK  " : "ZLE ") + c.uri() + "  -> " + c.uzasadnienie()));
    }

    private static String get(HttpClient client, int port, String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() + " " + response.body();
    }

    private static void respondJson(com.sun.net.httpserver.HttpExchange exchange, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
