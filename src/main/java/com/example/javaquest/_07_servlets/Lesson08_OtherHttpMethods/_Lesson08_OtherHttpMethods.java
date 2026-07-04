package com.example.javaquest._07_servlets.Lesson08_OtherHttpMethods;

import jakarta.servlet.ServletException;
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

public class _Lesson08_OtherHttpMethods {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 HttpServlet A METODY HTTP – service() I TABLICA DYSPOZYCJI
         * ============================================================
         * Wiemy już (Lesson07), że HttpServlet ma doGet() i doPost().
         * W rzeczywistości HttpServlet obsługuje WIĘCEJ metod HTTP –
         * każdej z nich odpowiada osobna metoda "do...":
         *
         *   GET     -> doGet()
         *   POST    -> doPost()
         *   PUT     -> doPut()
         *   DELETE  -> doDelete()
         *   HEAD    -> doHead()    (domyślnie woła doGet() i odcina body)
         *   OPTIONS -> doOptions() (domyślnie zwraca liste dozwolonych metod)
         *   TRACE   -> doTrace()
         *
         * Cały ten "routing" po metodzie HTTP dzieje się w metodzie
         * service(HttpServletRequest, HttpServletResponse) – to ONA jest
         * wywoływana przez kontener na każde żądanie, a jej domyślna
         * implementacja (w klasie HttpServlet) sprawdza req.getMethod()
         * i deleguje do odpowiedniego "do...".
         *
         * 🔍 CO Z PATCH?
         * PATCH (częściowa aktualizacja zasobu, w odróżnieniu od PUT,
         * który podmienia CAŁY zasób) NIE WYSTĘPUJE w klasycznej
         * specyfikacji Servlet API! Nie ma metody doPatch() – PATCH
         * został dodany do HTTP już PO ustabilizowaniu Servlet API i
         * nigdy nie doczekał się oficjalnego wsparcia w HttpServlet.
         *
         * Jeśli więc wyślemy żądanie PATCH do zwykłego HttpServlet,
         * domyślna implementacja service() go NIE ROZPOZNA i odpowie
         * błędem 405 (Method Not Allowed).
         *
         * ✅ ROZWIĄZANIE: trzeba samodzielnie nadpisać service() –
         * sprawdzić ręcznie req.getMethod(), obsłużyć "PATCH" po swojemu,
         * a dla wszystkich pozostałych metod oddać sterowanie do
         * super.service(req, resp), żeby doGet/doPost/doPut/doDelete
         * nadal działały normalnie.
         */

        System.out.println("=== Lekcja 8: Inne metody HTTP (PUT, DELETE, PATCH) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson08").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "multiMethodServlet", new MultiMethodServlet());
        context.addServletMappingDecoded("/zasob", "multiMethodServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/zasob";
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔹 PUT – ZASTĄPIENIE CAŁEGO ZASOBU
             * ============================================================
             * PUT jest idempotentny: wysłanie tego samego żądania kilka
             * razy daje ten sam efekt końcowy (zasób ma taką treść, jaką
             * wysłaliśmy). Semantycznie oznacza "zastąp cały zasób tymi
             * danymi" (w przeciwieństwie do PATCH – patrz niżej).
             */
            System.out.println("--- PUT ---");
            HttpRequest putRequest = HttpRequest.newBuilder(URI.create(url + "?id=7"))
                    .PUT(HttpRequest.BodyPublishers.ofString("{\"nazwa\":\"Laptop\",\"cena\":4200}"))
                    .build();
            HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + putResponse.statusCode());
            System.out.println("Body: " + putResponse.body());

            /*
             * ============================================================
             * 🔹 DELETE – USUNIĘCIE ZASOBU
             * ============================================================
             * DELETE zwykle nie ma ciała żądania – identyfikujemy zasób
             * np. parametrem zapytania albo fragmentem ścieżki URL.
             * HttpRequest.Builder ma gotowy skrót .DELETE().
             */
            System.out.println("\n--- DELETE ---");
            HttpRequest deleteRequest = HttpRequest.newBuilder(URI.create(url + "?id=7"))
                    .DELETE()
                    .build();
            HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + deleteResponse.statusCode());
            System.out.println("Body: " + deleteResponse.body());

            /*
             * ============================================================
             * 🔍 PATCH – CZĘŚCIOWA AKTUALIZACJA (RĘCZNA OBSŁUGA!)
             * ============================================================
             * HttpRequest.Builder NIE MA metody .PATCH() (podobnie jak
             * HttpServlet nie ma doPatch()) – to nie jest przeoczenie,
             * tylko konsekwencja tego, że PATCH nigdy nie trafił do
             * "oficjalnej" listy metod obsługiwanych przez te API.
             * Żeby wysłać PATCH, używamy ogólnej metody .method(nazwa, body):
             */
            System.out.println("\n--- PATCH ---");
            HttpRequest patchRequest = HttpRequest.newBuilder(URI.create(url + "?id=7"))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"cena\":3900}"))
                    .build();
            HttpResponse<String> patchResponse = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + patchResponse.statusCode());
            System.out.println("Body: " + patchResponse.body());

            // Dla porównania: zwykłe GET nadal działa normalnie, bo nasz
            // nadpisany service() oddaje mu sterowanie przez super.service().
            System.out.println("\n--- GET (dla porownania, dzialajacy przez super.service()) ---");
            HttpRequest getRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body: " + getResponse.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HttpServlet obsługuje natywnie GET, POST, PUT, DELETE, HEAD,
         *   OPTIONS, TRACE – każdej odpowiada metoda doXxx(), a routing
         *   po metodzie HTTP dzieje się w service().
         * - PATCH NIE JEST częścią tej tabeli – nie ma doPatch().
         * - Żeby obsłużyć PATCH, trzeba nadpisać service(req, resp),
         *   sprawdzić ręcznie "PATCH".equals(req.getMethod()), obsłużyć
         *   go po swojemu, a dla reszty metod wywołać super.service(req, resp).
         * - java.net.http.HttpClient: PUT ma skrót .PUT(bodyPublisher),
         *   DELETE ma skrót .DELETE(), ale PATCH trzeba wysłać przez
         *   ogólne .method("PATCH", bodyPublisher) – tu też brak skrótu.
         * - PUT = zastąp cały zasób, PATCH = zaktualizuj częściowo,
         *   DELETE = usuń zasób.
         */
    }

    /**
     * Serwlet demonstrujący PUT i DELETE przez standardowe nadpisania
     * doPut()/doDelete(), oraz PATCH przez ręczne nadpisanie service().
     */
    static class MultiMethodServlet extends HttpServlet {

        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String body = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("PUT: zasob id=" + req.getParameter("id") + " zastapiony danymi: " + body);
        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("DELETE: usunieto zasob o id=" + req.getParameter("id"));
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("GET: standardowy odczyt zasobu (obsluzone przez super.service)");
        }

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            if ("PATCH".equals(req.getMethod())) {
                String body = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("PATCH: czesciowa aktualizacja zasobu id=" + req.getParameter("id")
                        + " danymi: " + body);
                return;
            }
            // Dla GET/POST/PUT/DELETE/... standardowe dzialanie HttpServlet
            super.service(req, resp);
        }
    }
}
