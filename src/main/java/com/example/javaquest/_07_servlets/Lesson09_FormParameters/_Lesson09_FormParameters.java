package com.example.javaquest._07_servlets.Lesson09_FormParameters;

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
import java.nio.file.Files;
import java.util.Map;

public class _Lesson09_FormParameters {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 SKĄD BIORĄ SIĘ "PARAMETRY FORMULARZA"?
         * ============================================================
         * Klasyczny formularz HTML wygląda tak:
         *
         *   <form method="POST" action="/rejestracja">
         *       <input type="text" name="imie">
         *       <input type="checkbox" name="kolor" value="niebieski">
         *       <input type="checkbox" name="kolor" value="zielony">
         *   </form>
         *
         * Kiedy przeglądarka wysyła taki formularz, koduje pola w ciało
         * (body) żądania HTTP jako:
         *
         *   imie=Jan&kolor=niebieski&kolor=zielony
         *
         * z nagłówkiem: Content-Type: application/x-www-form-urlencoded
         *
         * Kontener serwletów (Tomcat) WIDZI ten nagłówek i AUTOMATYCZNIE
         * parsuje takie ciało – dokładnie tak samo, jak parsuje parametry
         * z query stringa w URL-u (np. ?imie=Jan). Dla serwletu to jest
         * NIEROZRÓŻNIALNE: request.getParameter("imie") zadziała
         * identycznie, niezależnie czy "imie" przyszło w URL-u, czy w
         * ciele POST-a typu x-www-form-urlencoded.
         *
         * 🔹 W tej lekcji NIE potrzebujemy prawdziwej przeglądarki ani
         * pliku HTML – po prostu ręcznie zbudujemy taki sam ciąg znaków
         * (symulując to, co przeglądarka wysłałaby za nas) i wyślemy go
         * jako POST przez HttpClient z odpowiednim Content-Type. Z punktu
         * widzenia serwletu wygląda to dokładnie tak samo jak prawdziwy
         * formularz.
         */

        System.out.println("=== Lekcja 9: Parametry formularza (request.getParameter) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson09").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "formServlet", new FormServlet());
        context.addServletMappingDecoded("/rejestracja", "formServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/rejestracja";
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔹 getParameter(name) – POJEDYNCZA WARTOŚĆ
             * ============================================================
             * Zwraca PIERWSZĄ wartość parametru o danej nazwie (String),
             * albo null, jeśli parametr nie występuje w żądaniu.
             */
            System.out.println("--- Formularz z pojedynczymi polami ---");
            String bodyProste = "imie=Jan&wiek=25";
            HttpRequest requestProsty = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyProste))
                    .build();
            HttpResponse<String> responseProsty =
                    client.send(requestProsty, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + responseProsty.statusCode());
            System.out.println("Body:\n" + responseProsty.body());

            /*
             * ============================================================
             * 🔹 getParameterValues(name) – WIELE WARTOŚCI (checkboxy!)
             * ============================================================
             * Checkboxy (i multi-select) mogą wysłać TĘ SAMĄ nazwę
             * parametru wielokrotnie – np. kolor=niebieski&kolor=zielony.
             * getParameter("kolor") zwróciłby wtedy TYLKO pierwszą wartość
             * ("niebieski") – reszta by "zniknęła". Żeby dostać WSZYSTKIE
             * wartości, trzeba użyć getParameterValues(name), które zwraca
             * String[] (albo null, jeśli parametr nie istnieje).
             */
            System.out.println("\n--- Formularz z checkboxami (wiele wartości tej samej nazwy) ---");
            String bodyMulti = "imie=Ania&wiek=30&kolor=niebieski&kolor=zielony&kolor=czerwony";
            HttpRequest requestMulti = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyMulti))
                    .build();
            HttpResponse<String> responseMulti =
                    client.send(requestMulti, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + responseMulti.statusCode());
            System.out.println("Body:\n" + responseMulti.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Formularz HTML wysyła pola jako ciało typu
         *   application/x-www-form-urlencoded (np. "imie=Jan&kolor=a&kolor=b").
         * - Tomcat parsuje takie ciało automatycznie, dokładnie tak samo
         *   jak parametry z query stringa w URL-u.
         * - request.getParameter(name) -> String, PIERWSZA wartość (albo null).
         * - request.getParameterValues(name) -> String[], WSZYSTKIE wartości
         *   danej nazwy (kluczowe dla checkboxów/multi-selectów!) (albo null).
         * - request.getParameterMap() -> Map<String, String[]> ze WSZYSTKIMI
         *   parametrami żądania (przydatne do iteracji po wszystkich polach
         *   formularza bez znajomości ich nazw z góry).
         */
    }

    /**
     * Serwlet demonstrujący odczyt parametrów formularza: pojedynczych
     * (getParameter) oraz wielokrotnych (getParameterValues), a także
     * pełnej mapy parametrów (getParameterMap).
     */
    static class FormServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            StringBuilder sb = new StringBuilder();

            String imie = req.getParameter("imie");
            String wiek = req.getParameter("wiek");
            sb.append("imie (getParameter) = ").append(imie).append("\n");
            sb.append("wiek (getParameter) = ").append(wiek).append("\n");

            String[] kolory = req.getParameterValues("kolor");
            if (kolory != null) {
                sb.append("kolor (getParameterValues) = ").append(String.join(", ", kolory)).append("\n");
                sb.append("  -> getParameter(\"kolor\") dalby tylko PIERWSZA wartosc: ")
                        .append(req.getParameter("kolor")).append("\n");
            } else {
                sb.append("kolor (getParameterValues) = brak\n");
            }

            sb.append("\nPelna mapa parametrow (getParameterMap):\n");
            Map<String, String[]> paramMap = req.getParameterMap();
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                sb.append("  ").append(entry.getKey()).append(" = ")
                        .append(String.join(", ", entry.getValue())).append("\n");
            }

            resp.getWriter().write(sb.toString());
        }
    }
}
