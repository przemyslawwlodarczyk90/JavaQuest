package com.example.javaquest._07_servlets.Lesson15_Listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
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

public class _Lesson15_Listeners {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM SA LISTENERY (LISTENERS)?
         * ============================================================
         * LISTENER to klasa implementujaca jeden z interfejsow-obserwatorow
         * z Servlet API, ktora kontener powiadamia o okreslonych
         * ZDARZENIACH w cyklu zycia aplikacji webowej (np. "aplikacja
         * wlasnie wystartowala", "sesja uzytkownika wlasnie wygasla").
         * To klasyczny wzorzec obserwatora (observer pattern) – zamiast
         * odpytywac kontener "czy juz sie coś stalo?", rejestrujesz
         * listener, a kontener sam go wywola we wlasciwym momencie.
         *
         * Najczesciej uzywany: ServletContextListener – powiadamia o
         * starcie i zatrzymaniu CALEJ aplikacji webowej (ServletContext).
         */

        /*
         * ============================================================
         * 🔹 ServletContextListener – contextInitialized / contextDestroyed
         * ============================================================
         * Interfejs ma dwie metody (obie domyslnie puste - to interfejs
         * z metodami default, wiec mozna nadpisac tylko te, ktore
         * potrzebujemy):
         *
         * - contextInitialized(ServletContextEvent sce) – wywolywane RAZ,
         *   gdy aplikacja (kontekst) startuje – ZANIM jakikolwiek serwlet
         *   obsluzy pierwsze zadanie. Typowe uzycie: inicjalizacja puli
         *   polaczen do bazy, wczytanie konfiguracji, zaladowanie cache.
         *
         * - contextDestroyed(ServletContextEvent sce) – wywolywane RAZ,
         *   gdy aplikacja jest zatrzymywana. Typowe uzycie: zwolnienie
         *   zasobow zaalokowanych w contextInitialized (np. zamkniecie
         *   puli polaczen).
         *
         * sce.getServletContext() daje dostep do ServletContext (znamy
         * go z Lesson13) – mozna np. ustawic w nim atrybut globalny dla
         * calej aplikacji juz w momencie startu.
         */

        /*
         * ============================================================
         * 🔍 REJESTRACJA LISTENERA – addApplicationListener(String!)
         * ============================================================
         * ⚠️ WAZNA PULAPKA: Context.addApplicationListener(...) przyjmuje
         * NAZWE KLASY (String), a NIE gotowa instancje listenera!
         *
         *   context.addApplicationListener(MyContextListener.class.getName());
         *
         * Dlaczego tak? Bo w standardowym modelu (web.xml / skanowanie
         * adnotacji @WebListener – zobaczymy w Lesson16) kontener SAM
         * tworzy instancje listenera przez refleksje, korzystajac z
         * publicznego, bezargumentowego konstruktora. To pozwala
         * kontenerowi kontrolowac cykl zycia listenera tak samo jak
         * serwletow, niezaleznie od tego, czy rejestracja pochodzi z
         * web.xml, adnotacji, czy (jak tutaj) z kodu.
         *
         * Konsekwencja praktyczna: klasa listenera MUSI miec dostepny
         * publiczny konstruktor bezargumentowy (domyslny, niejawny
         * konstruktor tez sie liczy - tak jak w MyContextListener ponizej).
         */

        System.out.println("=== Lekcja 15: Listenery (ServletContextListener) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson15").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "helloServlet");

        // Rejestracja listenera po NAZWIE KLASY - kontener utworzy instancje sam.
        context.addApplicationListener(MyContextListener.class.getName());

        try {
            System.out.println("--- Startuje Tomcat (tu wywola sie contextInitialized) ---");
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Osadzony Tomcat wystartowal na porcie: " + port);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/hello"))
                    .GET()
                    .build();

            System.out.println("\n--- Wysylam zadanie GET /hello ---");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Odpowiedz serwera ---");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            System.out.println("\n--- Zatrzymuje Tomcat (tu wywola sie contextDestroyed) ---");
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 15 ===");
        System.out.println("Kolejnosc logow powyzej: contextInitialized -> doGet -> contextDestroyed");

        /*
         * ============================================================
         * 🔎 A CO Z LISTENERAMI SESJI?
         * ============================================================
         * Oprocz ServletContextListener istnieje tez HttpSessionListener
         * (sessionCreated / sessionDestroyed) – powiadamia o tworzeniu
         * i wygasaniu/uniewazenianiu pojedynczej sesji HTTP (znamy sesje
         * z Lesson11). Rejestruje sie go dokladnie tak samo (przez
         * addApplicationListener z nazwa klasy) – nie pokazujemy tu
         * osobnego dzialajacego przykladu, bo mechanizm rejestracji i
         * wywolania jest identyczny jak dla ServletContextListener,
         * tylko zdarzenia dotycza sesji, a nie calej aplikacji.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Listener = klasa-obserwator powiadamiana przez kontener o
         *   zdarzeniach cyklu zycia aplikacji (lub sesji).
         * - ServletContextListener: contextInitialized() przy starcie
         *   aplikacji, contextDestroyed() przy jej zatrzymaniu.
         * - Rejestracja: context.addApplicationListener(NazwaKlasy) –
         *   przyjmuje String (nazwe klasy), NIE instancje – kontener
         *   sam tworzy obiekt przez refleksje (potrzebny publiczny
         *   konstruktor bezargumentowy).
         * - Kolejnosc zdarzen w tej lekcji: contextInitialized (start
         *   Tomcata) -> doGet (obsluga zadania) -> contextDestroyed
         *   (zatrzymanie Tomcata).
         * - HttpSessionListener dziala analogicznie, ale dla zdarzen
         *   tworzenia/niszczenia pojedynczej sesji HTTP.
         */
    }

    /**
     * Prosty serwlet obslugujacy jedno zadanie GET - do zademonstrowania,
     * ze contextInitialized() wywoluje sie ZANIM to zadanie trafi do
     * serwletu, a contextDestroyed() dopiero PO zatrzymaniu Tomcata.
     */
    static class HelloServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("[HelloServlet] doGet() - obsluguje zadanie");
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello from servlet!");
        }
    }

    /**
     * Listener kontekstu aplikacji. WAZNE: musi miec publiczny konstruktor
     * bezargumentowy (tu domyslny, niejawny), bo Tomcat tworzy go sam
     * przez refleksje na podstawie nazwy klasy przekazanej do
     * addApplicationListener().
     */
    public static class MyContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            System.out.println("[MyContextListener] contextInitialized() - aplikacja startuje");
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            System.out.println("[MyContextListener] contextDestroyed() - aplikacja sie zatrzymuje");
        }
    }
}
