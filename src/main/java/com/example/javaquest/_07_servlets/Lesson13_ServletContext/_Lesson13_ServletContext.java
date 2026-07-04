package com.example.javaquest._07_servlets.Lesson13_ServletContext;

import jakarta.servlet.ServletContext;
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

public class _Lesson13_ServletContext {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 ServletContext – DANE WSPÓLNE CAŁEJ APLIKACJI
         * ============================================================
         * O ile ServletConfig (Lesson12) opisuje parametry JEDNEGO
         * serwletu, o tyle ServletContext reprezentuje CAŁĄ aplikację
         * webową (jeden "kontekst" = jedna aplikacja wdrożona w
         * kontenerze, u nas: Context context = tomcat.addContext("", null)).
         *
         * WSZYSTKIE serwlety zarejestrowane w tym samym kontekście
         * WIDZĄ TEN SAM obiekt ServletContext – to naturalny sposób na
         * współdzielenie danych/konfiguracji między różnymi serwletami
         * tej samej aplikacji.
         *
         * Dwa różne zastosowania ServletContext, które pokażemy:
         *
         * 1) PARAMETRY INIT KONTEKSTU (context.addParameter(...) przed
         *    startem) – NIEZMIENNE w trakcie działania aplikacji,
         *    odpowiednik <context-param> z web.xml. Odczyt:
         *    getServletContext().getInitParameter(name).
         *
         * 2) ATRYBUTY KONTEKSTU (getServletContext().setAttribute(...)) –
         *    W PEŁNI DYNAMICZNE, może je ustawić/zmienić KAŻDY serwlet w
         *    dowolnym momencie działania aplikacji (nie tylko przy
         *    starcie) – to współdzielony, zmienny stan "globalny" dla
         *    całej aplikacji.
         *
         * ⚠️ UWAGA: atrybuty kontekstu są widoczne dla WSZYSTKICH klientów
         * i WSZYSTKICH serwletów naraz (w odróżnieniu od HttpSession, gdzie
         * dane są per-klient) – dlatego przy współbieżnym dostępie z wielu
         * wątków (czyli normalnym trybie pracy serwera!) trzeba pamiętać
         * o bezpieczeństwie wątkowym (patrz rozdział _05_multithreading).
         */

        System.out.println("=== Lekcja 13: ServletContext (dane wspolne aplikacji) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson13").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);

        // 1) Parametr init kontekstu - wspolny dla calej aplikacji,
        // ustawiany PRZED startem, jednakowy dla wszystkich serwletow.
        context.addParameter("appName", "JavaQuest Sklep Demo");

        Tomcat.addServlet(context, "counterServlet", new VisitCounterServlet());
        context.addServletMappingDecoded("/odwiedz", "counterServlet");

        Tomcat.addServlet(context, "statsServlet", new StatsServlet());
        context.addServletMappingDecoded("/statystyki", "statsServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔹 DWA RÓŻNE SERWLETY, JEDEN WSPÓLNY LICZNIK
             * ============================================================
             * VisitCounterServlet (/odwiedz) przy każdym wywołaniu
             * INKREMENTUJE atrybut kontekstu "liczbaOdwiedzin".
             * StatsServlet (/statystyki) tylko GO ODCZYTUJE - a mimo że
             * to inny serwlet (inna klasa, inna instancja), widzi TĘ SAMĄ
             * wartość, bo obaj współdzielą ten sam ServletContext.
             */
            System.out.println("--- Wywolanie /odwiedz x3 (inkrementacja licznika w kontekscie) ---");
            for (int i = 1; i <= 3; i++) {
                HttpResponse<String> resp = wyslijGet(client, "http://localhost:" + port + "/odwiedz");
                System.out.println("Wywolanie " + i + " -> " + resp.body().trim());
            }

            System.out.println("\n--- Wywolanie /statystyki (INNY serwlet, ten sam ServletContext) ---");
            HttpResponse<String> statsResponse = wyslijGet(client, "http://localhost:" + port + "/statystyki");
            System.out.println("Body:\n" + statsResponse.body());
            // Oczekiwany wynik: statystyki pokaza liczbe odwiedzin = 3,
            // mimo ze to INNY serwlet niz ten, ktory ja inkrementowal -
            // dowod na wspoldzielenie stanu przez ServletContext.

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ServletContext reprezentuje CAŁĄ aplikację webową (jeden
         *   "Context" w Tomcacie) – wszystkie serwlety w niej widzą TEN
         *   SAM obiekt kontekstu.
         * - Parametry init kontekstu: context.addParameter(name, value)
         *   PRZED tomcat.start() (odpowiednik <context-param> w web.xml),
         *   odczyt: getServletContext().getInitParameter(name) –
         *   NIEZMIENNE po starcie aplikacji.
         * - Atrybuty kontekstu: getServletContext().setAttribute(name, obj)
         *   / getAttribute(name) – W PEŁNI DYNAMICZNE, może je zmieniać
         *   dowolny serwlet w dowolnym momencie, WSPÓŁDZIELONE przez
         *   wszystkie serwlety i wszystkich klientów naraz.
         * - Kontrast z Lesson12 (ServletConfig): config = per-servlet
         *   (własne, izolowane parametry), context = per-aplikacja
         *   (wspólne dla wszystkich serwletów).
         * - Kontrast z Lesson11 (HttpSession): sesja = per-klient (każdy
         *   klient ma SWOJĄ), kontekst = wspólny dla WSZYSTKICH klientów
         *   naraz (uważaj na współbieżny dostęp z wielu wątków!).
         */
    }

    private static HttpResponse<String> wyslijGet(HttpClient client, String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Serwlet inkrementujący wspólny (dla całej aplikacji) licznik
     * odwiedzin, trzymany jako atrybut ServletContext.
     */
    static class VisitCounterServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            ServletContext ctx = getServletContext();
            // synchronized na klasie ServletContext - proste zabezpieczenie
            // przed wyscigiem przy wspolbieznych zadaniach (patrz rozdzial
            // o wielowatkowosci - to typowy "shared mutable state").
            synchronized (ctx) {
                Integer liczba = (Integer) ctx.getAttribute("liczbaOdwiedzin");
                if (liczba == null) {
                    liczba = 0;
                }
                liczba++;
                ctx.setAttribute("liczbaOdwiedzin", liczba);
                resp.getWriter().write("Zarejestrowano odwiedziny. Aktualna liczba: " + liczba);
            }
        }
    }

    /**
     * Inny serwlet (inna klasa!), który tylko ODCZYTUJE wspólny stan
     * zapisany przez VisitCounterServlet - dowód, że ServletContext jest
     * współdzielony między RÓŻNYMI serwletami tej samej aplikacji.
     */
    static class StatsServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            ServletContext ctx = getServletContext();
            String appName = ctx.getInitParameter("appName");
            Integer liczba = (Integer) ctx.getAttribute("liczbaOdwiedzin");
            if (liczba == null) {
                liczba = 0;
            }

            resp.getWriter().write(
                    "Nazwa aplikacji (parametr kontekstu): " + appName + "\n"
                            + "Liczba odwiedzin (atrybut kontekstu, ustawiony przez INNY serwlet): "
                            + liczba + "\n");
        }
    }
}
