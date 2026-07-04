package com.example.javaquest._07_servlets.Lesson16_ServletAnnotations;

import jakarta.servlet.annotation.WebServlet;
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

public class _Lesson16_ServletAnnotations {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 DEKLARATYWNA REJESTRACJA: @WebServlet, @WebFilter, @WebListener
         * ============================================================
         * Przez cala te czesc kursu rejestrowalismy serwlety, filtry
         * (Lesson14) i listenery (Lesson15) PROGRAMOWO – wywolujac
         * Tomcat.addServlet(...), context.addFilterDef(...) itd.
         * bezposrednio w kodzie main().
         *
         * W prawdziwym, standardowym wdrozeniu (WAR na "prawdziwym"
         * Tomcacie/Jetty, nie w kodzie embedded) mozna zamiast tego
         * uzyc adnotacji wprost na klasie:
         *
         *   @WebServlet("/hello")
         *   public class HelloServlet extends HttpServlet { ... }
         *
         *   @WebFilter("/*")
         *   public class TimingFilter implements Filter { ... }
         *
         *   @WebListener
         *   public class MyContextListener implements ServletContextListener { ... }
         *
         * Kontener PODCZAS WDROZENIA aplikacji SKANUJE wszystkie klasy
         * w WEB-INF/classes (i w plikach .jar w WEB-INF/lib) w poszukiwaniu
         * tych adnotacji i SAM rejestruje serwlety/filtry/listenery -
         * bez jednej linijki web.xml i bez rejestracji w kodzie.
         * To odpowiednik <servlet>+<servlet-mapping> (albo FilterDef/
         * FilterMap, albo <listener>) zapisany bezposrednio przy klasie.
         */

        /*
         * ============================================================
         * ⚠️ WAZNY CAVEAT DOTYCZACY TEJ LEKCJI I CALEGO KURSU
         * ============================================================
         * Wszystkie nasze lekcje uruchamiaja "goly" embedded Tomcat
         * stworzony przez `new Tomcat()` + `tomcat.addContext("", null)`.
         * Taki kontekst NIE MA skonfigurowanego skanowania adnotacji ani
         * klasycznej struktury katalogow webapp (WEB-INF/classes,
         * WEB-INF/lib) - skanowanie adnotacji wymaga pelnego kontekstu
         * webapp (np. utworzonego przez tomcat.addWebapp(...) wskazujace
         * na prawdziwy docBase na dysku, z odpowiednim resource setem
         * i "container SCI" - ServletContainerInitializer), co jest
         * znacznie ciezsze niz to, czego potrzebujemy do nauki API.
         *
         * DLATEGO w ponizszej dzialajacej demonstracji SERWLET JEST NADAL
         * rejestrowany RECZNIE (Tomcat.addServlet + addServletMappingDecoded),
         * DOKLADNIE tak jak we wczesniejszych lekcjach. Adnotacja
         * @WebServlet("/hello") jest tu obecna na klasie WYLACZNIE jako
         * METADANE do odczytania i porownania - w tej lekcji NIKT jej
         * nie skanuje automatycznie. To NIE jest pomylka ani niedopatrzenie,
         * tylko swiadomy wybor: pokazujemy skladnie i sens adnotacji,
         * ale realne "spiecie" adnotacja -> rejestracja demonstrujemy
         * recznie, odczytujac adnotacje przez refleksje i porownujac
         * jej wartosc ze sciezka uzyta w addServletMappingDecoded(...).
         *
         * W prawdziwym wdrozeniu WAR-a na standalone Tomcat/Jetty ten
         * reczny krok byby zbedny - kontener zrobilby to sam podczas
         * startu aplikacji.
         */

        System.out.println("=== Lekcja 16: Adnotacje serwletowe (@WebServlet i inne) ===\n");

        // Odczytujemy adnotacje z klasy PRZED rejestracja - pokazujemy, ze to
        // zwykle metadane dostepne przez refleksje, niezaleznie od tego, czy
        // ktos je "przeskanuje" automatycznie.
        WebServlet annotation = HelloServlet.class.getAnnotation(WebServlet.class);
        String annotatedPath = annotation.value()[0];
        System.out.println("Sciezka zadeklarowana w @WebServlet na klasie HelloServlet: " + annotatedPath);

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson16").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);

        // Rejestracja WCIAZ RECZNA (brak skanowania adnotacji w golym embedded
        // Tomcacie) - ale celowo uzywamy TEJ SAMEJ sciezki, co w adnotacji,
        // zeby pokazac zgodnosc miedzy "deklaracja" a "faktyczna rejestracja".
        Tomcat.addServlet(context, "helloServlet", new HelloServlet());
        context.addServletMappingDecoded(annotatedPath, "helloServlet");
        System.out.println("Serwlet zarejestrowany recznie pod sciezka: " + annotatedPath
                + " (zgodna z adnotacja)");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Osadzony Tomcat wystartowal na porcie: " + port);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + annotatedPath))
                    .GET()
                    .build();

            System.out.println("\n--- Wysylam zadanie GET " + annotatedPath + " ---");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Odpowiedz serwera ---");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 16 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @WebServlet("/sciezka"), @WebFilter("/wzorzec"), @WebListener
         *   to deklaratywne odpowiedniki rejestracji, ktore normalnie
         *   robilibysmy recznie (Tomcat.addServlet, FilterDef/FilterMap,
         *   addApplicationListener) albo w web.xml.
         * - W PRAWDZIWYM wdrozeniu WAR-a kontener SAM skanuje klasy i
         *   rejestruje serwlety/filtry/listenery na podstawie tych
         *   adnotacji - bez zadnej rekcznej rejestracji.
         * - W tej lekcji (i calym kursie) uzywamy GOLEGO embedded Tomcata
         *   bez pelnej struktury webapp, wiec skanowanie adnotacji NIE
         *   dziala - dlatego demo rejestruje serwlet recznie, a adnotacja
         *   sluzy tylko jako czytelna metadana do porownania ze sciezka
         *   uzyta w rejestracji.
         * - @WebServlet.value() zwraca tablice String[] (mozna podac
         *   wiele sciezek dla jednego serwletu), dlatego uzylismy
         *   annotation.value()[0].
         */
    }

    /**
     * Serwlet oznaczony adnotacja @WebServlet - w prawdziwym wdrozeniu
     * WAR-a to WYSTARCZYLOBY do jego rejestracji. Tutaj adnotacja jest
     * tylko odczytywana i porownywana z reczna rejestracja (patrz caveat
     * w komentarzach powyzej).
     */
    @WebServlet("/hello")
    static class HelloServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("[HelloServlet] doGet() - obsluguje zadanie");
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello from servlet with @WebServlet!");
        }
    }
}
