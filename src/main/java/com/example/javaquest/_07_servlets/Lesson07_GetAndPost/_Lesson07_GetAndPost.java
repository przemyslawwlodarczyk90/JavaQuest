package com.example.javaquest._07_servlets.Lesson07_GetAndPost;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class _Lesson07_GetAndPost {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 GET vs POST - RÓŻNICE SEMANTYCZNE
         * ============================================================
         * W Lesson04 poznaliśmy doGet() i doPost() od strony API. Tu
         * porównujemy je od strony ZNACZENIA - kiedy używać której
         * metody i dlaczego to nie jest przypadkowy wybór.
         *
         * GET:
         * - IDEMPOTENTNY i "bezpieczny" (safe) - zgodnie ze specyfikacją
         *   HTTP, GET powinien tylko ODCZYTYWAĆ dane, nigdy ich nie
         *   zmieniać. Wysłanie tego samego GET-a wiele razy powinno dać
         *   ten sam efekt (brak efektu ubocznego) - stąd bezpiecznie
         *   można go automatycznie ponawiać, prefetchować, cache'ować.
         * - Dane przesyłane są w URL-u (query string: ?klucz=wartosc) -
         *   czyli są WIDOCZNE: w pasku adresu przeglądarki, w historii
         *   przeglądarki, w logach serwera, w logach proxy po drodze.
         * - CACHEABLE - przeglądarki i serwery pośredniczące (proxy, CDN)
         *   domyślnie mogą cache'ować odpowiedzi GET.
         * - PRAKTYCZNE ograniczenie długości - HTTP samo w sobie nie
         *   definiuje twardego limitu długości URL-a, ale przeglądarki i
         *   serwery narzucają własne (często ok. 2000-8000 znaków) -
         *   dlatego GET nie nadaje się do przesyłania dużych ilości
         *   danych.
         *
         * POST:
         * - NIE jest idempotentny Z ZAŁOŻENIA (choć konkretna
         *   implementacja MOŻE taka być) - klasyczny przykład: POST
         *   "złóż zamówienie" wysłany dwa razy tworzy DWA zamówienia.
         * - Dane przesyłane są w CIELE żądania (request body) - NIE są
         *   widoczne w URL-u ani w typowych logach serwera (choć wciąż
         *   mogą być widoczne np. w logach proxy odszyfrowujących
         *   ruch albo w narzędziach deweloperskich przeglądarki - to
         *   NIE jest mechanizm szyfrowania/ukrywania danych, tylko inne
         *   miejsce ich przesłania).
         * - NIE jest domyślnie cache'owany przez przeglądarki/proxy.
         * - Brak praktycznego ograniczenia długości ciała żądania (poza
         *   konfiguracją konkretnego serwera, np. max-request-size).
         *
         * 📌 WAŻNE ROZRÓŻNIENIE: "dane w URL-u są widoczne" NIE oznacza
         * "dane w ciele są bezpieczne/zaszyfrowane" - jedyne, co realnie
         * szyfruje dane w drodze (i URL, i ciało, i nagłówki), to HTTPS/TLS.
         * Wybór GET vs POST to kwestia SEMANTYKI i widoczności w logach/
         * historii, a NIE mechanizm bezpieczeństwa sam w sobie.
         */

        System.out.println("=== LESSON 7: GET vs POST ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson07").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "searchServlet", new SearchServlet());
        context.addServletMappingDecoded("/szukaj", "searchServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/szukaj";
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔹 GET - DANE W QUERY STRINGU (WIDOCZNE W URL-U)
             * ============================================================
             * Zwróć uwagę, że cała treść zapytania jest częścią adresu -
             * dokładnie taki URL trafiłby do historii przeglądarki i do
             * logów dostępu serwera (access log).
             */
            String getUrl = url + "?fraza=java+servlety&strona=1";
            System.out.println("--- GET " + getUrl + " ---");
            System.out.println("(dane widoczne w samym adresie URL - trafia do logow, historii, cache)");
            HttpRequest getRequest = HttpRequest.newBuilder(URI.create(getUrl)).GET().build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body: " + getResponse.body());

            /*
             * ============================================================
             * 🔍 POST - DANE W CIELE ŻĄDANIA (UKRYTE PRZED URL-EM)
             * ============================================================
             * Ten sam adres URL ("/szukaj", bez żadnych parametrów) -
             * dane trafiają w ciele, jako klasyczny formularz
             * application/x-www-form-urlencoded (dokladny format
             * poznamy w Lesson09).
             */
            System.out.println("\n--- POST " + url + " (te same dane, ale w ciele zadania) ---");
            System.out.println("(adres URL nie zdradza tresci zapytania - dane sa w body)");
            HttpRequest postRequest = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("fraza=java+servlety&strona=1"))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + postResponse.statusCode());
            System.out.println("Body: " + postResponse.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 7 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - GET: idempotentny, "bezpieczny" (tylko odczyt), dane w
         *   query stringu URL-a -> WIDOCZNE w adresie, historii,
         *   logach; domyslnie cacheowalny; praktyczny limit dlugosci
         *   (narzucony przez przegladarki/serwery, nie przez sam HTTP).
         * - POST: NIE jest idempotentny z zalozenia, dane w ciele
         *   zadania -> NIEwidoczne w samym URL-u; domyslnie NIE
         *   cacheowany; brak praktycznego limitu dlugosci ciala.
         * - Widocznosc danych w URL-u (GET) vs w ciele (POST) to kwestia
         *   MIEJSCA przesylu, NIE szyfrowania - jedynym realnym
         *   mechanizmem ochrony danych w transporcie jest HTTPS/TLS,
         *   niezaleznie od wybranej metody HTTP.
         * - Ten sam serwlet i ten sam adres URL moga obslugiwac oba
         *   przypadki jednoczesnie (doGet + doPost) - kontener sam
         *   rozroznia, ktora metoda "do..." wywolac na podstawie metody
         *   HTTP zadania (patrz Lesson04).
         */
    }

    /**
     * Serwlet wyszukiwania - ta sama logika biznesowa (odczyt parametrow
     * "fraza" i "strona") obsluzona zarowno dla GET (query string), jak
     * i POST (cialo formularza).
     */
    static class SearchServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            wyszukaj(req, resp, "GET (parametry z query stringa URL-a)");
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            wyszukaj(req, resp, "POST (parametry z ciala formularza)");
        }

        private void wyszukaj(HttpServletRequest req, HttpServletResponse resp, String opis) throws IOException {
            String fraza = req.getParameter("fraza");
            String strona = req.getParameter("strona");
            resp.setContentType("text/plain;charset=" + StandardCharsets.UTF_8);
            resp.getWriter().write("Wyszukiwanie przez " + opis + " - fraza=\"" + fraza + "\", strona=" + strona);
        }
    }
}
