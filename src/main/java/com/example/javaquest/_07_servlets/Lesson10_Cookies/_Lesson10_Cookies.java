package com.example.javaquest._07_servlets.Lesson10_Cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class _Lesson10_Cookies {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CO TO JEST COOKIE (CIASTECZKO)?
         * ============================================================
         * Cookie to mały kawałek danych (klucz=wartość + metadane), który
         * SERWER wysyła do przeglądarki w nagłówku odpowiedzi:
         *
         *   Set-Cookie: nazwa=wartosc; Max-Age=3600; Path=/
         *
         * Przeglądarka ZAPAMIĘTUJE takie ciasteczko i przy KOLEJNYCH
         * żądaniach do tego samego serwera (zgodnych z Path/domeną)
         * odsyła je automatycznie w nagłówku żądania:
         *
         *   Cookie: nazwa=wartosc
         *
         * Dzięki temu HTTP (z natury BEZSTANOWY – serwer nie pamięta nic
         * między żądaniami) może "udawać", że pamięta klienta między
         * kolejnymi żądaniami – to podstawa mechanizmów logowania,
         * koszyków zakupowych, sesji (patrz Lesson11_HttpSession) itd.
         *
         * W Jakarta Servlet API cookie reprezentuje klasa
         * jakarta.servlet.http.Cookie.
         */

        System.out.println("=== Lekcja 10: Cookies ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson10").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "cookieServlet", new CookieServlet());
        context.addServletMappingDecoded("/ciastko", "cookieServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/ciastko";

            /*
             * ============================================================
             * 🔹 SERWER: TWORZENIE I DODAWANIE COOKIE
             * ============================================================
             * new Cookie(name, value)   – tworzy ciasteczko
             * cookie.setMaxAge(sekundy) – czas życia (bez tego -> cookie
             *                             sesyjne, znika po zamknięciu
             *                             przeglądarki)
             * cookie.setPath("/")       – dla jakich ścieżek przeglądarka
             *                             ma je odsyłać
             * response.addCookie(cookie) – dopisuje nagłówek Set-Cookie
             *
             * (Zobacz metodę doGet() w CookieServlet poniżej.)
             *
             * 🔍 UWAGA NA HttpClient: w odróżnieniu od przeglądarki,
             * java.net.http.HttpClient DOMYŚLNIE NIE zarządza ciasteczkami
             * – żeby to zadziałało jak w przeglądarce (czyli żeby cookie
             * z PIERWSZEJ odpowiedzi zostało automatycznie odesłane w
             * DRUGIM żądaniu), trzeba jawnie podać CookieManager przy
             * budowaniu klienta.
             */
            System.out.println("--- Klient z CookieManager (jak przegladarka) ---");
            HttpClient client = HttpClient.newBuilder()
                    .cookieHandler(new CookieManager())
                    .build();

            System.out.println("--- Żądanie 1: serwer ustawia cookie ---");
            HttpRequest request1 = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response1.statusCode());
            System.out.println("Body: " + response1.body());
            System.out.println("Naglowek Set-Cookie z odpowiedzi: "
                    + response1.headers().firstValue("set-cookie").orElse("(brak)"));

            System.out.println("\n--- Żądanie 2: ten sam klient, cookie odeslane automatycznie ---");
            HttpRequest request2 = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response2.statusCode());
            System.out.println("Body: " + response2.body());
            // ⬆ Serwer w Body drugiej odpowiedzi POWINIEN pokazać, że
            // ODCZYTAŁ cookie ustawione w żądaniu 1 (patrz req.getCookies()
            // w servlecie) – to jest dowód, że mechanizm zadziałał.

            /*
             * ============================================================
             * 🔍 DLA PORÓWNANIA: KLIENT BEZ CookieManager
             * ============================================================
             * Nowy HttpClient BEZ cookieHandler nie pamięta niczego między
             * żądaniami – każde jego żądanie wygląda dla serwera jak od
             * zupełnie nowego, "anonimowego" klienta (brak nagłówka Cookie).
             */
            System.out.println("\n--- Żądanie 3: NOWY klient BEZ CookieManager (brak pamieci cookie) ---");
            HttpClient clientBezCookie = HttpClient.newHttpClient();
            HttpRequest request3 = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response3 = clientBezCookie.send(request3, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response3.statusCode());
            System.out.println("Body: " + response3.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Cookie = para klucz-wartość + metadane, wysyłana przez serwer
         *   (Set-Cookie), zapamiętywana przez klienta i odsyłana przy
         *   kolejnych żądaniach (Cookie).
         * - jakarta.servlet.http.Cookie: new Cookie(name, value),
         *   setMaxAge(sekundy), setPath("/").
         * - response.addCookie(cookie) – wysłanie cookie do klienta.
         * - request.getCookies() – Cookie[] (MOŻE BYĆ null, gdy klient nie
         *   przysłał żadnych cookies – zawsze sprawdzaj null przed
         *   iteracją!).
         * - java.net.http.HttpClient NIE zarządza cookies domyślnie –
         *   trzeba jawnie: HttpClient.newBuilder().cookieHandler(new
         *   CookieManager()).build(), żeby zachowywał się jak przeglądarka
         *   (pamiętał cookies między żądaniami tego samego klienta).
         */
    }

    /**
     * Serwlet demonstrujący ustawianie cookie (przy pierwszym żądaniu bez
     * cookie) oraz odczyt cookies przysłanych przez klienta.
     */
    static class CookieServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            Cookie[] cookies = req.getCookies();
            String odczytanaWartosc = null;
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("odwiedziny".equals(c.getName())) {
                        odczytanaWartosc = c.getValue();
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            if (odczytanaWartosc != null) {
                sb.append("Witaj ponownie! Odczytano cookie 'odwiedziny' = ").append(odczytanaWartosc).append("\n");
            } else {
                sb.append("Brak cookie 'odwiedziny' w zadaniu (req.getCookies() == null lub bez tego klucza).\n");
                sb.append("Ustawiam nowe cookie 'odwiedziny'.\n");

                Cookie cookie = new Cookie("odwiedziny", "tak");
                cookie.setMaxAge(3600); // 1 godzina
                cookie.setPath("/");
                resp.addCookie(cookie);
            }

            resp.getWriter().write(sb.toString());
        }
    }
}
