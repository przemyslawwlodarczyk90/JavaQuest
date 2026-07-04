package com.example.javaquest._06_networking.Lesson12_JsonOverNetwork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;

public class _Lesson12_JsonOverNetwork {

    /*
     * Prosty rekord reprezentujący "użytkownika" zwracanego przez
     * przykładowe REST API. Gson (od wersji 2.10) potrafi deserializować
     * rekordy bezpośrednio, korzystając z ich kanonicznego konstruktora.
     */
    record User(int id, String name, String email) {
    }

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 JSON W ROLI FORMATU WYMIANY DANYCH W SIECI
         * ============================================================
         * Zdecydowana większość dzisiejszych REST API zwraca dane
         * w formacie JSON (JavaScript Object Notation) – lekki,
         * czytelny dla człowieka format tekstowy. Typowy przepływ
         * pracy klienta REST API wygląda tak:
         *
         *   1. Wyślij żądanie HTTP (GET/POST/...) pod dany adres URL
         *   2. Odbierz odpowiedź – ciało to zwykle TEKST w formacie JSON
         *   3. Sparsuj ten tekst na obiekty Java (tu: przy pomocy Gson,
         *      patrz Lesson20_Gson z rozdziału _04_io)
         *
         * W tej lekcji API, z którym "rozmawiamy", to serwer URUCHOMIONY
         * PRZEZ NAS W TYM SAMYM PROCESIE (com.sun.net.httpserver.HttpServer,
         * patrz Lesson09/Lesson11) – dzięki temu lekcja jest szybka,
         * deterministyczna i nie zależy od internetu. W PRAKTYCE serwer
         * byłby oczywiście gdzieś w internecie (np. api.example.com),
         * ale z punktu widzenia klienta kod wyglądałby DOKŁADNIE TAK SAMO
         * – zmieniłby się tylko adres URL.
         */

        System.out.println("=== Uruchamianie lokalnego \"REST API\" ===");
        String usersJson = """
                [
                  {"id":1,"name":"Anna Nowak","email":"anna@example.com"},
                  {"id":2,"name":"Jan Kowalski","email":"jan@example.com"},
                  {"id":3,"name":"Ewa Wiśniewska","email":"ewa@example.com"}
                ]
                """;

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/api/users", exchange -> {
            byte[] bytes = usersJson.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartował na porcie: " + port);

        try {
            /*
             * ============================================================
             * 🔹 KROK 1: POBRANIE JSON-A PRZEZ SIEĆ (HttpClient)
             * ============================================================
             * java.net.http.HttpClient (od Javy 11, patrz Lesson10) to
             * nowoczesne API do wysyłania żądań HTTP. Odpowiedź odbieramy
             * jako zwykły String – na tym etapie to jeszcze CZYSTY TEKST,
             * nie mamy pojęcia, że to JSON, dopóki go nie sparsujemy.
             */

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/users"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n=== Odpowiedź serwera (surowy tekst JSON) ===");
            System.out.println("Kod statusu: " + response.statusCode());
            System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("?"));
            System.out.println("Ciało odpowiedzi:");
            System.out.println(response.body());

            /*
             * ============================================================
             * 🔍 KROK 2: PARSOWANIE JSON-A NA OBIEKTY JAVA (Gson)
             * ============================================================
             * Mamy String z tekstem JSON – teraz zamieniamy go na
             * listę obiektów User przy pomocy Gson. Ponieważ chcemy
             * List<User> (typ generyczny), potrzebujemy TypeToken –
             * bez niego Gson nie wiedziałby, na jaki dokładnie typ
             * ma zdeserializować elementy listy (patrz Lesson20_Gson).
             */

            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(response.body(), userListType);

            System.out.println("\n=== Sparsowane obiekty Java (List<User>) ===");
            users.forEach(user -> System.out.println(
                    "  id=" + user.id() + ", name=" + user.name() + ", email=" + user.email()));

            System.out.println("\nTyp pierwszego elementu: " + users.get(0).getClass().getSimpleName());
            // User – nie LinkedTreeMap – dzięki TypeToken<List<User>>

            /*
             * ============================================================
             * 🔧 DLA PORÓWNANIA: SERIALIZACJA W DRUGĄ STRONĘ (Java -> JSON)
             * ============================================================
             * Tak samo jak pobieramy dane, moglibyśmy je WYSŁAĆ np. przez
             * POST – wystarczy zserializować obiekt Java do JSON-a
             * (gson.toJson(...)) i wysłać jako ciało żądania z nagłówkiem
             * Content-Type: application/json. My tylko demonstrujemy
             * samą serializację (bez faktycznego wysyłania POST-a).
             */

            User nowyUzytkownik = new User(4, "Piotr Zieliński", "piotr@example.com");
            String nowyUzytkownikJson = new GsonBuilder().setPrettyPrinting().create().toJson(nowyUzytkownik);

            System.out.println("\n=== Java -> JSON (przygotowanie do wysłania np. przez POST) ===");
            System.out.println(nowyUzytkownikJson);

        } finally {
            /*
             * ============================================================
             * ⚠️ ZAWSZE ZATRZYMUJ LOKALNY SERWER PO SOBIE!
             * ============================================================
             */
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Typowy przepływ REST API: wyślij żądanie HTTP -> odbierz
         *   ciało jako tekst -> sparsuj tekst JSON na obiekty Java
         * - HttpClient.send(..., BodyHandlers.ofString()) daje surowy
         *   tekst odpowiedzi (jeszcze nie wie, że to JSON)
         * - Gson.fromJson(json, Klasa.class) zamienia JSON na obiekt Java
         * - Do typów generycznych (List<X>, Map<K,V>) potrzeba
         *   TypeToken<List<X>>(){}.getType() – inaczej Gson zwróci
         *   surowe LinkedTreeMap/ArrayList
         * - gson.toJson(obiekt) działa w drugą stronę – Java -> JSON,
         *   przydatne przy wysyłaniu danych (np. ciało żądania POST)
         * - w lekcji użyliśmy lokalnego serwera HTTP zamiast prawdziwego
         *   API w internecie – dla szybkości i niezawodności demo;
         *   z prawdziwym, zdalnym API kod klienta wyglądałby identycznie
         */
    }
}
