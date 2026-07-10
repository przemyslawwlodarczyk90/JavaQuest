package com.example.javaquest._18_rest_api.Lesson10_PaginationSortingFiltering;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson10_PaginationSortingFiltering {

    record Employee(int id, String name, String department, double salary) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: STRONICOWANIE, SORTOWANIE, FILTROWANIE ===");

        List<Employee> employees = buildSampleData();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/employees", exchange -> handleEmployees(exchange, employees));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            /*
             * ============================================================
             * 📦 3 NIEZALEZNE MECHANIZMY, KTORE CZESTO WYSTEPUJA RAZEM
             * ============================================================
             * - STRONICOWANIE (pagination) - jaka CZESC kolekcji zwrocic
             * - SORTOWANIE (sorting)       - w JAKIEJ KOLEJNOSCI
             * - FILTROWANIE (filtering)    - KTORE elementy w ogole wziac
             * Wszystkie 3 to query params (Lesson09) dzialajace na
             * KOLEKCJI (/employees), nigdy na pojedynczym zasobie.
             */
            demonstratePagination(client, port);
            demonstrateSorting(client, port);
            demonstrateFiltering(client, port);
            demonstrateCombiningAllThree(client, port);
            demonstrateCursorBasedPagination();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Stronicowanie offset/limit (?offset=0&limit=10) - proste, ale
         *   "przesuwa sie" przy zmianach danych miedzy stronami.
         * - Stronicowanie page/size (?page=1&size=10) - podobne, bardziej
         *   przyjazne dla UI ("strona 3 z 10").
         * - Stronicowanie kursorowe (?cursor=xyz) - stabilne przy
         *   zmieniajacych sie danych, typowe dla duzych/"nieskonczonych"
         *   list (feed w social media).
         * - Sortowanie: ?sort=pole (rosnaco), ?sort=-pole (malejaco,
         *   minus jako prefiks), ?sort=pole1,-pole2 (wiele kryteriow).
         * - Filtrowanie: ?pole=wartosc (rownosc) lub rozszerzona skladnia
         *   operatorow (?salary[gte]=5000) dla porownan.
         * - Odpowiedz stronicowana ZAWSZE niesie metadane (total, strona,
         *   rozmiar strony) - w "kopercie" (Lesson06).
         * - Kolejna lekcja (Lesson11): jak HTTP cache'uje odpowiedzi -
         *   WAZNE, zeby stronicowane/filtrowane odpowiedzi byly
         *   cache'owane OSTROZNIE (zmieniajace sie dane!).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static List<Employee> buildSampleData() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "Ala Kowalska", "IT", 9500));
        list.add(new Employee(2, "Jan Nowak", "Sprzedaz", 6200));
        list.add(new Employee(3, "Piotr Wisniewski", "IT", 11000));
        list.add(new Employee(4, "Ewa Zielinska", "HR", 7100));
        list.add(new Employee(5, "Tomasz Lewandowski", "IT", 8300));
        list.add(new Employee(6, "Kasia Wojcik", "Sprzedaz", 5900));
        return list;
    }

    private static void handleEmployees(com.sun.net.httpserver.HttpExchange exchange, List<Employee> employees) throws java.io.IOException {
        Map<String, String> query = parseSimpleQuery(exchange.getRequestURI().getRawQuery());
        List<Employee> result = new ArrayList<>(employees);

        // FILTROWANIE
        if (query.containsKey("department")) {
            result.removeIf(e -> !e.department().equals(query.get("department")));
        }
        if (query.containsKey("minSalary")) {
            double min = Double.parseDouble(query.get("minSalary"));
            result.removeIf(e -> e.salary() < min);
        }

        // SORTOWANIE
        if (query.containsKey("sort")) {
            String sortSpec = query.get("sort");
            boolean descending = sortSpec.startsWith("-");
            String field = descending ? sortSpec.substring(1) : sortSpec;
            Comparator<Employee> comparator = switch (field) {
                case "name" -> Comparator.comparing(Employee::name);
                case "salary" -> Comparator.comparingDouble(Employee::salary);
                default -> Comparator.comparingInt(Employee::id);
            };
            result.sort(descending ? comparator.reversed() : comparator);
        }

        int totalAfterFiltering = result.size();

        // STRONICOWANIE (na juz przefiltrowanej i posortowanej liscie)
        int offset = query.containsKey("offset") ? Integer.parseInt(query.get("offset")) : 0;
        int limit = query.containsKey("limit") ? Integer.parseInt(query.get("limit")) : result.size();
        List<Employee> page = result.stream().skip(offset).limit(limit).toList();

        StringBuilder items = new StringBuilder("[");
        for (int i = 0; i < page.size(); i++) {
            Employee e = page.get(i);
            if (i > 0) items.append(",");
            items.append("{\"id\":").append(e.id()).append(",\"name\":\"").append(e.name())
                    .append("\",\"department\":\"").append(e.department())
                    .append("\",\"salary\":").append(e.salary()).append("}");
        }
        items.append("]");

        String envelope = "{\"data\":" + items + ",\"meta\":{\"total\":" + totalAfterFiltering
                + ",\"offset\":" + offset + ",\"limit\":" + limit + "}}";

        byte[] bytes = envelope.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static Map<String, String> parseSimpleQuery(String rawQuery) {
        Map<String, String> result = new java.util.LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return result;
        }
        for (String pair : rawQuery.split("&")) {
            String[] parts = pair.split("=", 2);
            result.put(parts[0], parts.length > 1 ? parts[1] : "");
        }
        return result;
    }

    private static void demonstratePagination(HttpClient client, int port) throws Exception {
        System.out.println("\n=== STRONICOWANIE: offset/limit ===");
        var response = get(client, port, "/employees?offset=0&limit=2");
        System.out.println("GET /employees?offset=0&limit=2 -> " + response.body());
        var response2 = get(client, port, "/employees?offset=2&limit=2");
        System.out.println("GET /employees?offset=2&limit=2 -> " + response2.body());
        System.out.println("-> 'meta.total' pozostaje takie samo (calkowita liczba PO filtrach), niezaleznie od strony.");
    }

    private static void demonstrateSorting(HttpClient client, int port) throws Exception {
        System.out.println("\n=== SORTOWANIE: ?sort=pole vs ?sort=-pole ===");
        var ascending = get(client, port, "/employees?sort=salary&limit=3");
        System.out.println("GET /employees?sort=salary (rosnaco) -> " + ascending.body());
        var descending = get(client, port, "/employees?sort=-salary&limit=3");
        System.out.println("GET /employees?sort=-salary (malejaco) -> " + descending.body());
    }

    private static void demonstrateFiltering(HttpClient client, int port) throws Exception {
        System.out.println("\n=== FILTROWANIE: rownosc i porownanie ===");
        var byDept = get(client, port, "/employees?department=IT");
        System.out.println("GET /employees?department=IT -> " + byDept.body());
        var bySalary = get(client, port, "/employees?minSalary=8000");
        System.out.println("GET /employees?minSalary=8000 -> " + bySalary.body());
    }

    private static void demonstrateCombiningAllThree(HttpClient client, int port) throws Exception {
        System.out.println("\n=== WSZYSTKIE 3 MECHANIZMY NARAZ ===");
        var response = get(client, port, "/employees?department=IT&sort=-salary&offset=0&limit=2");
        System.out.println("GET /employees?department=IT&sort=-salary&offset=0&limit=2 -> " + response.body());
        System.out.println("-> kolejnosc logiczna: NAJPIERW filtruj, POTEM sortuj, NA KONCU stronicuj (inaczej strony 'przesuwaja sie').");
    }

    private static void demonstrateCursorBasedPagination() {
        System.out.println("\n=== ALTERNATYWA: STRONICOWANIE KURSOROWE ===");
        System.out.println("Zamiast ?offset=20&limit=10, API zwraca ?cursor=<nieprzezroczysty-token> wskazujacy 'gdzie skonczylismy'.");
        System.out.println("Zaleta: STABILNE nawet gdy dane sa dodawane/usuwane MIEDZY zapytaniami o kolejne strony (typowe w duzych/'live' listach).");
        System.out.println("Wada: nie da sie 'skoczyc' bezposrednio na strone 5 (tylko 'nastepna'/'poprzednia').");
    }

    private static HttpResponse<String> get(HttpClient client, int port, String pathAndQuery) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + pathAndQuery))
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
