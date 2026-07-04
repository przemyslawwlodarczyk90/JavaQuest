package com.example.javaquest._07_servlets.Lesson04_HttpServlet;

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

public class _Lesson04_HttpServlet {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 KLASA HttpServlet
         * ============================================================
         * jakarta.servlet.http.HttpServlet to abstrakcyjna klasa bazowa,
         * po której dziedziczy niemal KAŻDY serwlet pisany w praktyce.
         * Rozszerza ona ogólniejszą klasę GenericServlet (a ta z kolei
         * implementuje interfejs Servlet) i dodaje zrozumienie protokołu
         * HTTP: rozpoznaje metody GET/POST/PUT/DELETE/... i sama, w
         * gotowej implementacji service(), rozdziela żądanie do
         * odpowiedniej metody "doXxx()".
         *
         * Dlatego pisząc własny serwlet, praktycznie NIGDY nie
         * implementujemy interfejsu Servlet ani nie nadpisujemy service()
         * bezpośrednio (choć technicznie można - zobaczymy wyjątek od tej
         * reguły w Lesson08 przy okazji PATCH) - zamiast tego nadpisujemy
         * wybrane metody doXxx() odpowiadające metodom HTTP, które nasz
         * serwlet ma obsługiwać.
         */

        /*
         * ============================================================
         * 🔹 doGet() I doPost() - NAJCZĘŚCIEJ UŻYWANE METODY
         * ============================================================
         * protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         * protected void doPost(HttpServletRequest req, HttpServletResponse resp)
         *
         * Obie rzucają IOException (problemy z I/O przy czytaniu/pisaniu
         * strumieni) oraz ServletException (błąd na poziomie serwletu).
         * Domyślna implementacja w HttpServlet (gdy jej NIE nadpiszemy)
         * po prostu odpowiada błędem 405 (Method Not Allowed) - to
         * sensowne zachowanie "z pudełka": jeśli serwlet nie obsługuje
         * danej metody, klient powinien dostać jasny sygnał, a nie cichą
         * pustą odpowiedź 200.
         *
         * Nic nie stoi na przeszkodzie, by w JEDNYM serwlecie nadpisać
         * OBIE metody - każda robiąc coś zupełnie innego. To bardzo
         * naturalny wzorzec: ten sam adres URL ("/greeting" poniżej) może
         * zachowywać się inaczej w zależności od metody HTTP, z jaką
         * przyjdzie żądanie.
         */

        System.out.println("=== LESSON 4: HttpServlet - doGet() i doPost() ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson04").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "greetingServlet", new GreetingServlet());
        context.addServletMappingDecoded("/greeting", "greetingServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/greeting";
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔍 WYWOŁANIE GET - doGet() ZWRACA PROSTE POWITANIE
             * ============================================================
             */
            System.out.println("--- GET /greeting ---");
            HttpRequest getRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body: " + getResponse.body());

            /*
             * ============================================================
             * 🔍 WYWOŁANIE POST - doPost() ECHUJE CIAŁO ŻĄDANIA
             * ============================================================
             * HttpRequest.BodyPublishers.ofString(...) tworzy ciało
             * żądania POST z podanego tekstu. Po stronie serwletu to
             * ciało czytamy metodą req.getInputStream() (surowe bajty) -
             * dokładne API do czytania żądania (parametry, nagłówki,
             * ciało jako Reader) poznamy w Lesson05.
             */
            System.out.println("\n--- POST /greeting ---");
            HttpRequest postRequest = HttpRequest.newBuilder(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString("Czesc z klienta!"))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + postResponse.statusCode());
            System.out.println("Body: " + postResponse.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 4 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HttpServlet to abstrakcyjna klasa bazowa dla praktycznie
         *   wszystkich serwletów - rozumie protokół HTTP i sama kieruje
         *   żądanie do odpowiedniej metody doXxx() na podstawie metody
         *   HTTP (przez wewnętrzną, gotową implementację service()).
         * - doGet(req, resp) i doPost(req, resp) to dwie najczęściej
         *   nadpisywane metody - obie przyjmują HttpServletRequest i
         *   HttpServletResponse, obie mogą rzucać IOException i
         *   ServletException.
         * - Jeden serwlet może obsługiwać wiele metod HTTP pod tym samym
         *   adresem URL - każda metoda doXxx() realizuje inną logikę dla
         *   tego samego endpointu.
         * - Jeśli metoda HTTP nie ma nadpisanej odpowiadającej jej metody
         *   doXxx(), domyślna implementacja HttpServlet odpowiada 405
         *   (Method Not Allowed).
         * - Szczegółowe API żądania (parametry, nagłówki, ciało) - Lesson05.
         *   Szczegółowe API odpowiedzi (statusy, nagłówki, przekierowania) -
         *   Lesson06.
         */
    }

    /**
     * Serwlet z dwiema roznymi implementacjami - GET zwraca statyczne
     * powitanie, POST echuje przeslane w ciele zadania dane.
     */
    static class GreetingServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Witaj! To jest odpowiedz na GET.");
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String body = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Otrzymalem POST z tresc: \"" + body + "\"");
        }
    }
}
