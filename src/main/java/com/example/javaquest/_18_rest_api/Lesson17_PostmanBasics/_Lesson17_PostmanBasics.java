package com.example.javaquest._18_rest_api.Lesson17_PostmanBasics;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class _Lesson17_PostmanBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 17: PODSTAWY POSTMANA ===");

        /*
         * ============================================================
         * 📦 CZYM JEST POSTMAN I DLACZEGO TA LEKCJA WYGLADA INACZEJ?
         * ============================================================
         * Postman to GRAFICZNE narzedzie (aplikacja desktopowa/webowa) do
         * RECZNEGO testowania API HTTP - alternatywa dla pisania kodu
         * `HttpClient` za kazdym razem, gdy chcesz "poklikac" jakis
         * endpoint. To NARZEDZIE ZEWNETRZNE (jak Ant/Maven/Gradle z
         * `_11_buildtools`) - nie da sie go "wywolac z main()".
         *
         * Ta lekcja jest wiec HYBRYDOWA: `main()` uruchamia PRAWDZIWY,
         * dzialajacy lokalny serwer, ktory MOZESZ faktycznie otworzyc w
         * Postmanie (adres i port sa wypisywane na konsole), a
         * KONKRETNE cwiczenia z klikaniem w Postmanie sa opisane w
         * pliku `_Exercises_...` - tak samo jak lekcje Maven/Gradle w
         * `_11_buildtools`.
         */
        System.out.println("Postman = zewnetrzne narzedzie GUI. Ta lekcja: prawdziwy lokalny serwer DO POTESTOWANIA w Postmanie.");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/api/articles", _Lesson17_PostmanBasics::handleArticles);
        server.start();
        int port = server.getAddress().getPort();

        try {
            System.out.println("\n=== SERWER DZIALA - MOZESZ GO PRZETESTOWAC W POSTMANIE ===");
            System.out.println("Baza URL do wklejenia w Postmanie: http://localhost:" + port);
            System.out.println("Endpointy: GET /api/articles, GET /api/articles/1, POST /api/articles");
            System.out.println("(Uwaga: ten port jest EFEMERYCZNY - zmienia sie przy kazdym uruchomieniu; ");
            System.out.println(" w cwiczeniach uruchom lekcje SAM i uzyj WYPISANEGO portu w Postmanie.)");

            demonstrateWhatPostmanDoesAutomatically(port);
            demonstrateCollectionConcept(port);
            demonstrateEnvironmentConcept();
            demonstrateChainingRequestsWithVariables();
            demonstratePreRequestAndTestScripts();

            // Kilka automatycznych wywolan - to DOKLADNIE to, co zrobiles(-abys) recznie w Postmanie
            HttpClient client = HttpClient.newHttpClient();
            var getAll = client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/articles")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("\n=== TO, CO ZROBILBYS 'GET' W POSTMANIE, ROBIMY TU AUTOMATYCZNIE ===");
            System.out.println("GET /api/articles -> " + getAll.statusCode() + " " + getAll.body());

        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Postman = GUI do reczengo/eksploracyjnego testowania API -
         *   request (metoda+URL+naglowki+cialo) + odpowiedz w 1 oknie,
         *   bez pisania kodu.
         * - KOLEKCJA (Collection) = zapisany, uporzadkowany zestaw
         *   requestow (np. "wszystkie endpointy /articles") - mozna
         *   EKSPORTOWAC/IMPORTOWAC jako plik JSON.
         * - SRODOWISKO (Environment) = zestaw zmiennych (np. {{baseUrl}},
         *   {{authToken}}) rozny dla dev/staging/produkcji - TA SAMA
         *   kolekcja dziala wszedzie, zmieniasz tylko srodowisko.
         * - Requesty moga LANCUCHOWO korzystac z odpowiedzi poprzednich
         *   (np. zapisz token z logowania do zmiennej, uzyj go w kolejnym
         *   requescie) - przez "Tests"/"Scripts" (JavaScript).
         * - Postman NIE zastepuje testow automatycznych (JUnit) - to
         *   narzedzie do EKSPLORACJI i RECZNEJ weryfikacji podczas
         *   rozwoju API, choc kolekcje MOZNA tez uruchamiac automatycznie
         *   (Postman Runner / Newman w CI).
         * - Kolejna lekcja (Lesson18): OpenAPI/Swagger - jak
         *   udokumentowac API w sposob, ktory Postman (i inne narzedzia)
         *   moze zaimportowac AUTOMATYCZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void handleArticles(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (method.equals("GET") && path.equals("/api/articles")) {
            respond(exchange, 200, "[{\"id\":1,\"title\":\"Wprowadzenie do Postmana\"}]");
        } else if (method.equals("GET") && path.equals("/api/articles/1")) {
            respond(exchange, 200, "{\"id\":1,\"title\":\"Wprowadzenie do Postmana\"}");
        } else if (method.equals("POST") && path.equals("/api/articles")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            respond(exchange, 201, "{\"id\":2,\"received\":" + (body.isBlank() ? "false" : "true") + "}");
        } else {
            respond(exchange, 404, "{\"error\":\"nie znaleziono\"}");
        }
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateWhatPostmanDoesAutomatically(int port) {
        System.out.println("\n=== CO ROBI POSTMAN 'POD MASKA'? ===");
        System.out.println("Kiedy klikasz 'Send' w Postmanie dla GET http://localhost:" + port + "/api/articles,");
        System.out.println("Postman robi DOKLADNIE to samo, co nasz kod `HttpClient` w kazdej poprzedniej lekcji -");
        System.out.println("buduje request HTTP, wysyla go, i pokazuje Ci odpowiedz (status, naglowki, cialo) w czytelnym widoku.");
    }

    private static void demonstrateCollectionConcept(int port) {
        System.out.println("\n=== KOLEKCJA POSTMANA - PRAWDZIWA STRUKTURA JSON ===");
        /*
         * Ponizej REALNY, minimalny przyklad kolekcji w formacie Postman
         * Collection Format v2.1 - dokladnie taki plik moglbys
         * zaimportowac do Postmana (Import -> Raw text / plik .json).
         */
        String collectionJson = """
                {
                  "info": { "name": "JavaQuest Lesson17", "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json" },
                  "item": [
                    {
                      "name": "Pobierz wszystkie artykuly",
                      "request": { "method": "GET", "url": "{{baseUrl}}/api/articles" }
                    },
                    {
                      "name": "Utworz nowy artykul",
                      "request": {
                        "method": "POST",
                        "url": "{{baseUrl}}/api/articles",
                        "body": { "mode": "raw", "raw": "{\\"title\\":\\"Nowy tytul\\"}" }
                      }
                    }
                  ]
                }""";
        System.out.println("Przykladowa kolekcja (mozesz ja zapisac do pliku .json i zaimportowac do Postmana):");
        System.out.println(collectionJson.indent(2));
        System.out.println("Wstaw \"baseUrl\" = \"http://localhost:" + port + "\" jako zmienna srodowiskowa, zeby dzialalo.");
    }

    private static void demonstrateEnvironmentConcept() {
        System.out.println("\n=== SRODOWISKA (ENVIRONMENTS) - TA SAMA KOLEKCJA, ROZNE MIEJSCA ===");
        String envJson = """
                {
                  "name": "Lokalne (dev)",
                  "values": [
                    { "key": "baseUrl", "value": "http://localhost:8080" },
                    { "key": "authToken", "value": "dev-token-123" }
                  ]
                }""";
        System.out.println("Przykladowe srodowisko 'dev':");
        System.out.println(envJson.indent(2));
        System.out.println("-> zmieniajac AKTYWNE srodowisko (dev/staging/prod) w Postmanie, TA SAMA kolekcja wysyla requesty w INNE miejsce.");
    }

    private static void demonstrateChainingRequestsWithVariables() {
        System.out.println("\n=== LANCUCHOWANIE REQUESTOW PRZEZ ZMIENNE (TESTS/SCRIPTS) ===");
        String testScript = """
                // W zakladce "Tests" requestu logowania (Postman, JavaScript):
                const response = pm.response.json();
                pm.environment.set("authToken", response.token);
                // -> kolejny request moze uzyc {{authToken}} w naglowku Authorization""";
        System.out.println(testScript.indent(2));
        System.out.println("-> logujesz sie RAZ, token jest AUTOMATYCZNIE zapisywany i uzywany we WSZYSTKICH kolejnych requestach kolekcji.");
    }

    private static void demonstratePreRequestAndTestScripts() {
        System.out.println("\n=== PRE-REQUEST SCRIPTS vs TESTS ===");
        System.out.println("Pre-request Script - kod URUCHAMIANY PRZED wyslaniem requestu (np. wygeneruj timestamp/podpis).");
        System.out.println("Tests               - kod URUCHAMIANY PO otrzymaniu odpowiedzi (np. sprawdz status==200,");
        System.out.println("                       zapisz wartosc do zmiennej dla kolejnego requestu - jak wyzej).");
        System.out.println("-> to podstawa AUTOMATYZACJI calych scenariuszy (np. 'zaloguj -> stworz zamowienie -> sprawdz status') w 1 kolekcji.");
    }
}
