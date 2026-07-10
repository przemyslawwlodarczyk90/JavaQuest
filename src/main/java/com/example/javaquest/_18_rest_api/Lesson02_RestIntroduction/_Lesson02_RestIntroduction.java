package com.example.javaquest._18_rest_api.Lesson02_RestIntroduction;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson02_RestIntroduction {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: CZYM JEST REST? ===");

        /*
         * ============================================================
         * 📦 SKAD SIE WZIAL REST?
         * ============================================================
         * REST (REpresentational State Transfer) to NIE protokol ani
         * biblioteka - to STYL ARCHITEKTONICZNY opisany przez Roya
         * Fieldinga w jego pracy doktorskiej z 2000 roku. Fielding byl
         * jednym ze wspoltworcow samego HTTP - REST to w duzej mierze
         * "nazwanie i usystematyzowanie" tego, co HTTP juz umozliwial
         * (Lesson01), a nie wynalezienie czegos nowego od zera.
         */
        System.out.println("REST = styl architektoniczny (Roy Fielding, 2000), NIE protokol ani biblioteka.");

        /*
         * ============================================================
         * 🔹 6 OGRANICZEN (CONSTRAINTS) REST
         * ============================================================
         * 1. Client-Server    - rozdzial UI (klient) od przechowywania
         *    danych (serwer) - moga sie rozwijac NIEZALEZNIE.
         * 2. Stateless        - kazdy request niesie WSZYSTKO co potrzebne
         *    (Lesson01) - serwer nie trzyma stanu sesji klienta.
         * 3. Cacheable        - odpowiedzi MUSZA jawnie deklarowac, czy
         *    wolno je cache'owac (Lesson11: Cache-Control/ETag).
         * 4. Uniform Interface- SPOJNY sposob komunikacji (szczegoly nizej)
         *    - to NAJWAZNIEJSZE i najbardziej charakterystyczne ograniczenie.
         * 5. Layered System   - klient nie musi wiedziec, czy laczy sie
         *    bezposrednio z serwerem, czy przez proxy/load balancer/gateway.
         * 6. Code-on-Demand   (OPCJONALNE) - serwer moze wyslac kod
         *    wykonywalny (np. JavaScript) do klienta - rzadko stosowane
         *    w API, typowe dla stron WWW.
         */
        demonstrateClientServerSeparation();
        demonstrateStatelessness();
        demonstrateCacheableConstraint();

        /*
         * ============================================================
         * 🔍 UNIFORM INTERFACE - 4 PODOGRANICZENIA
         * ============================================================
         * a) Identyfikacja zasobow przez URI       (Lesson03)
         * b) Manipulacja przez REPREZENTACJE        (Lesson06/08 - np. JSON,
         *    NIE bezposrednio przez wewnetrzny model danych serwera)
         * c) Wiadomosci SAMOOPISUJACE SIE            (Content-Type mowi, jak
         *    interpretowac cialo - Lesson07)
         * d) HATEOAS (Hypermedia As The Engine Of    (patrz nizej -
         *    Application State)                       Richardson Maturity Model)
         */
        System.out.println("\nUniform Interface = zasoby+URI, reprezentacje (JSON), samoopisujace sie wiadomosci, HATEOAS.");

        /*
         * ============================================================
         * 📌 RICHARDSON MATURITY MODEL - "JAK BARDZO RESTFUL JEST TWOJE API?"
         * ============================================================
         * Model Leonarda Richardsona (2008) porzadkuje API w 4 poziomy:
         *   Poziom 0 - "Bagno POX" (Plain Old XML) - 1 endpoint, 1 metoda
         *              (zwykle POST), cala logika w ciele - jak zdalne
         *              wywolanie funkcji (RPC) przebrane za HTTP.
         *   Poziom 1 - Zasoby - osobne URI dla roznych zasobow (/users,
         *              /orders), ale wciaz 1 metoda HTTP (np. zawsze POST).
         *   Poziom 2 - Czasowniki HTTP - osobne URI + WLASCIWE uzycie metod
         *              HTTP (GET/POST/PUT/DELETE) i kodow statusu -
         *              WIEKSZOSC "RESTful" API w praktyce konczy TU.
         *   Poziom 3 - HATEOAS - odpowiedzi zawieraja LINKI do mozliwych
         *              kolejnych akcji (jak strona WWW z linkami) - klient
         *              NIE musi z gory znac wszystkich URL, "odkrywa" je
         *              z odpowiedzi. RZADKO spotykane w praktyce - wiekszosc
         *              publicznych API (GitHub, Stripe czesciowo) zatrzymuje
         *              sie na poziomie 2, bo poziom 3 komplikuje klienta
         *              bez proporcjonalnej korzysci dla wiekszosci przypadkow.
         */
        demonstrateMaturityLevels();

        /*
         * ============================================================
         * 🔍 "REST" vs "RESTFUL" W PRAKTYCE
         * ============================================================
         * Wiekszosc API nazywanych "REST" w branzy to w rzeczywistosci
         * Poziom 2 modelu Richardsona - NIE spelniaja W PELNI wszystkich
         * ograniczen Fieldinga (zwlaszcza HATEOAS). To NIE jest "zle" -
         * to swiadomy, powszechnie akceptowany kompromis: pelne HATEOAS
         * daje elastycznosc, ktorej wiekszosc zespolow po prostu nie
         * potrzebuje, kosztem wiekszej zlozonosci klienta. Ten kurs (jak
         * wiekszosc branzy) uczy stylu Poziomu 2 jako pragmatycznego
         * standardu - ale ZNAJOMOSC calego modelu pozwala Ci SWIADOMIE
         * ocenic, na ktorym poziomie jest Twoje API i dlaczego.
         */
        System.out.println("\nWiekszosc realnych 'REST' API w branzy = Poziom 2 Richardsona (zasoby+czasowniki), bez pelnego HATEOAS.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - REST = styl architektoniczny (Fielding, 2000), oparty na
         *   6 ograniczeniach: client-server, stateless, cacheable,
         *   uniform interface, layered system, code-on-demand (opcjonalne).
         * - Uniform Interface to najwazniejsze ograniczenie: zasoby+URI,
         *   reprezentacje, samoopisujace sie wiadomosci, HATEOAS.
         * - Richardson Maturity Model (0-3) pozwala ocenic, JAK BARDZO
         *   "RESTful" jest dane API - wiekszosc realnych API to Poziom 2.
         * - Kolejne lekcje rozwijaja KAZDY z tych elementow osobno:
         *   Lesson03 (zasoby/URI), Lesson04 (czasowniki HTTP), Lesson11
         *   (cacheable w praktyce, ETag/Cache-Control).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateClientServerSeparation() throws Exception {
        System.out.println("\n=== 1. CLIENT-SERVER: NIEZALEZNY ROZWOJ OBU STRON ===");

        /*
         * ============================================================
         * 🔹 Serwer NIE WIE nic o kliencie poza tym, co jest w requescie
         * (naglowki/URI/cialo) - moglby to byc przegladarka, aplikacja
         * mobilna, inny serwer, skrypt curl - serwer traktuje je IDENTYCZNIE,
         * dopoki mowia tym samym "jezykiem" (HTTP + uzgodniony format danych).
         */
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/api/status", exchange -> {
            byte[] body = "{\"status\":\"OK\"}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.start();
        int port = server.getAddress().getPort();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/status"))
                    .header("User-Agent", "curl-like-klient")
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Serwer odpowiada IDENTYCZNIE, niezaleznie kto pyta: " + response.body());
        } finally {
            server.stop(0);
        }
    }

    private static void demonstrateStatelessness() {
        System.out.println("\n=== 2. STATELESS: PRZYPOMNIENIE Z LEKCJI 1 ===");
        System.out.println("Kazdy request niesie WSZYSTKO, co serwer potrzebuje (tozsamosc, dane) - serwer nic nie pamieta miedzy requestami.");
        System.out.println("Zaleta: latwiej skalowac poziomo - KAZDY serwer w klastrze moze obsluzyc DOWOLNY request, bo zaden nie trzyma 'prywatnego' stanu klienta.");
    }

    private static void demonstrateCacheableConstraint() throws Exception {
        System.out.println("\n=== 3. CACHEABLE: ODPOWIEDZ MUSI JAWNIE DEKLAROWAC, CZY WOLNO JA CACHOWAC ===");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/api/countries", exchange -> {
            // Lista krajow zmienia sie RZADKO - jawnie mowimy klientowi/proxy,
            // ze wolno mu przechowac ta odpowiedz przez 60 sekund bez pytania serwera ponownie.
            byte[] body = "[\"Polska\",\"Niemcy\",\"Czechy\"]".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Cache-Control", "public, max-age=60");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.start();
        int port = server.getAddress().getPort();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/api/countries"))
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Cache-Control z odpowiedzi: " + response.headers().firstValue("cache-control").orElse("(brak)"));
            System.out.println("-> mechanika cache'owania na poziomie HTTP wraca dokladnie w Lesson11.");
        } finally {
            server.stop(0);
        }
    }

    private static void demonstrateMaturityLevels() {
        System.out.println("\n=== RICHARDSON MATURITY MODEL - PRZYKLADY KAZDEGO POZIOMU ===");

        // Poziom 0: 1 endpoint, cala "logika" w ciele requestu - jak RPC.
        Map<String, Object> level0Request = new HashMap<>();
        level0Request.put("action", "getUser");
        level0Request.put("userId", 42);
        System.out.println("Poziom 0 (Bagno POX): POST /api  cialo=" + level0Request
                + "  <- WSZYSTKO idzie na 1 URL, 'co zrobic' jest zakodowane W CIELE.");

        // Poziom 1: osobne zasoby, ale wciaz zla metoda (np. zawsze POST).
        System.out.println("Poziom 1 (Zasoby):    POST /api/users/42/get  <- osobne URI per zasob, ale METODA dalej zle uzyta (powinno byc GET).");

        // Poziom 2: wlasciwa metoda + wlasciwy status.
        System.out.println("Poziom 2 (Czasowniki): GET /api/users/42 -> 200 OK  <- URI = zasob, METODA HTTP = operacja. Wiekszosc API konczy TUTAJ.");

        // Poziom 3: odpowiedz zawiera linki do mozliwych kolejnych akcji.
        String level3Response = """
                {
                  "id": 42,
                  "name": "Ala Kowalska",
                  "_links": {
                    "self":   { "href": "/api/users/42" },
                    "orders": { "href": "/api/users/42/orders" },
                    "delete": { "href": "/api/users/42", "method": "DELETE" }
                  }
                }""";
        System.out.println("Poziom 3 (HATEOAS): odpowiedz NIESIE ze soba mozliwe kolejne kroki:");
        System.out.println(level3Response.indent(2));
        System.out.println("-> klient 'odkrywa' dostepne akcje z odpowiedzi, zamiast znac je z gory z dokumentacji.");
    }
}
