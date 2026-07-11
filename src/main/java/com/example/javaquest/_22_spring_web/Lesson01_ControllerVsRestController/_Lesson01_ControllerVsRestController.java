package com.example.javaquest._22_spring_web.Lesson01_ControllerVsRestController;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson01_ControllerVsRestController {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: @Controller vs @RestController ===");

        /*
         * ============================================================
         * 📦 REST NA SPRINGU - MASZ JUZ CALA TEORIE
         * ============================================================
         * `_18_rest_api` nauczyl Cie PROJEKTOWANIA REST (zasoby, metody,
         * statusy, JSON) na czystym `HttpServer`. `_07_servlets` pokazal
         * SUROWE Servlet API. Ten rozdzial pokazuje, jak Spring MVC
         * (auto-konfigurowany przez `spring-boot-starter-web`, JUZ
         * obecny w tym projekcie od poczatku) ROBI to za Ciebie -
         * routing, serializacje JSON, walidacje - WSZYSTKO deklaratywnie,
         * przez adnotacje.
         */
        System.out.println("REST na Springu = to, co znasz z _18_rest_api/_07_servlets, ale routing/JSON/walidacja SA zautomatyzowane.");

        demonstrateControllerReturnsViewNameByDefault();
        demonstrateControllerWithResponseBodyReturnsRawData();
        demonstrateRestControllerIsShortcut();

        /*
         * ============================================================
         * 🔹 @Controller (MVC, WIDOKI) vs @RestController (REST, DANE)
         * ============================================================
         * `@Controller` — DOMYSLNIE zwracana wartosc metody to NAZWA
         * WIDOKU (np. szablonu Thymeleaf) do WYRENDEROWANIA - klasyczny
         * "server-side rendering". `@ResponseBody` na METODZIE (lub
         * KLASIE) ZMIENIA to zachowanie - zwracana wartosc trafia
         * WPROST do ciala odpowiedzi (przez Jackson, jesli to obiekt -
         * JSON). `@RestController` = `@Controller` + `@ResponseBody`
         * NA CALEJ klasie - SKROT dla API zwracajacych WYLACZNIE dane
         * (NIE widoki).
         */
        System.out.println("\n@Controller (bez @ResponseBody) = zwraca NAZWE WIDOKU. @RestController = @Controller+@ResponseBody na calej klasie = zwraca DANE (JSON).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Controller` bez `@ResponseBody` - zwracany String to
         *   NAZWA WIDOKU (MVC, server-side rendering).
         * - `@Controller`+`@ResponseBody` na METODZIE - zwracana
         *   wartosc trafia WPROST do ciala odpowiedzi.
         * - `@RestController` - SKROT: `@ResponseBody` na CALEJ klasie -
         *   KAZDA metoda zwraca DANE, nie widok.
         * - Ten rozdzial (REST API) uzywa WYLACZNIE `@RestController` -
         *   `@Controller` (bez `@ResponseBody`) wymaga silnika
         *   szablonow (Thymeleaf/JSP), POZA zakresem tego kursu.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    @Controller
    static class ViewNameController {
        @GetMapping("/greeting-view")
        String greeting() {
            // BEZ @ResponseBody: Spring MVC potraktuje "greeting-view-template" jako NAZWE WIDOKU
            // do wyszukania i wyrenderowania (np. przez silnik szablonow) - NIE jako tresc odpowiedzi.
            return "greeting-view-template";
        }
    }

    @SpringBootApplication
    static class ViewNameApp {
    }

    private static void demonstrateControllerReturnsViewNameByDefault() throws Exception {
        System.out.println("\n=== @Controller BEZ @ResponseBody: ZWRACANY STRING TO NAZWA WIDOKU ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ViewNameApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/greeting-view");
            System.out.println("GET /greeting-view -> status: " + response.statusCode() + " (BLAD - brak widoku 'greeting-view-template' do wyrenderowania)");
            System.out.println("-> Spring PROBOWAL znalezc WIDOK o tej nazwie (np. plik szablonu) - BEZ silnika szablonow konczy sie to bledem, NIE zwroceniem tekstu 'greeting-view-template'.");
        } finally {
            context.close();
        }
    }

    @Controller
    static class ResponseBodyController {
        @GetMapping("/greeting-data")
        @ResponseBody
        String greeting() {
            // Z @ResponseBody: TERAZ zwracany String trafia WPROST do ciala odpowiedzi HTTP.
            return "Witaj z @Controller + @ResponseBody!";
        }
    }

    @SpringBootApplication
    static class ResponseBodyApp {
    }

    private static void demonstrateControllerWithResponseBodyReturnsRawData() throws Exception {
        System.out.println("\n=== @Controller + @ResponseBody NA METODZIE: ZWRACANA WARTOSC = CIALO ODPOWIEDZI ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ResponseBodyApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/greeting-data");
            System.out.println("GET /greeting-data -> status: " + response.statusCode() + ", body: " + response.body());
            System.out.println("-> TERAZ zwrocony String TRAFIL BEZPOSREDNIO do ciala odpowiedzi - @ResponseBody 'wylaczylo' szukanie widoku.");
        } finally {
            context.close();
        }
    }

    record Greeting(String message, String author) {
    }

    @RestController
    static class ApiController {
        @GetMapping("/api/greeting")
        Greeting greeting() {
            // @RestController = @ResponseBody na CALEJ klasie - KAZDA metoda automatycznie
            // zwraca dane. Zwracany REKORD zostanie zserializowany do JSON przez Jacksona
            // (auto-konfigurowanego, znanego z _04_io/Lesson21 i _20_spring_core/Lesson04).
            return new Greeting("Witaj z @RestController!", "JavaQuest");
        }
    }

    @SpringBootApplication
    static class RestControllerApp {
    }

    private static void demonstrateRestControllerIsShortcut() throws Exception {
        System.out.println("\n=== @RestController: SKROT DLA CALEJ KLASY, AUTOMATYCZNA SERIALIZACJA JSON ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RestControllerApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/api/greeting");
            System.out.println("GET /api/greeting -> status: " + response.statusCode() + ", body: " + response.body());
            System.out.println("-> zwrocony REKORD Javy zostal AUTOMATYCZNIE zserializowany do JSON - ZERO recznego kodu serializacji (Jackson auto-konfigurowany).");
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
