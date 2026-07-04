package com.example.javaquest._07_servlets.Lesson01_ServletApiIntroduction;

import jakarta.servlet.ServletConfig;
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
import java.nio.file.Files;

public class _Lesson01_ServletApiIntroduction {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST SERWLET?
         * ============================================================
         * SERWLET (servlet) to zwykła klasa Javy, która potrafi obsłużyć
         * żądanie HTTP i wygenerować odpowiedź HTTP. To fundament, na
         * którym zbudowane są prawie wszystkie frameworki webowe w Javie
         * (Spring MVC, JSF i inne) – nawet jeśli na co dzień piszesz kod
         * w Springu, "pod spodem" i tak działają serwlety.
         *
         * Serwlet NIE jest samodzielnym programem – sam w sobie nie
         * potrafi nasłuchiwać na porcie TCP, parsować HTTP ani zarządzać
         * połączeniami. Serwlet żyje WEWNĄTRZ kontenera serwletów
         * (servlet container), który wykonuje całą "brudną robotę"
         * sieciową za niego.
         *
         * W Javie serwlet to klasa implementująca interfejs
         * jakarta.servlet.Servlet – w praktyce prawie zawsze dziedziczysz
         * po gotowej klasie jakarta.servlet.http.HttpServlet (poznamy ją
         * dokładnie w Lesson04), która już rozumie protokół HTTP.
         *
         * ⚠️ UWAGA NA PAKIET: od Jakarta EE 9 (i Tomcat 10+) pakiet nazywa
         * się jakarta.servlet.* – NIE javax.servlet.* (starsze wersje).
         * Więcej o tym w Lesson03.
         */

        /*
         * ============================================================
         * 🔹 CZYM JEST KONTENER SERWLETÓW?
         * ============================================================
         * KONTENER SERWLETÓW (servlet container / servlet engine) to
         * program, który:
         * 1) Otwiera gniazdo sieciowe (socket) i nasłuchuje na porcie TCP.
         * 2) Parsuje surowe bajty HTTP na wygodne obiekty
         *    HttpServletRequest / HttpServletResponse.
         * 3) MAPUJE adresy URL na konkretne instancje serwletów
         *    (np. "/hello" -> HelloServlet).
         * 4) Zarządza CYKLEM ŻYCIA serwletu (tworzy go, woła metody
         *    cyklu życia, w końcu go niszczy).
         * 5) Zarządza pulą wątków obsługujących równoległe żądania.
         *
         * Najpopularniejsze kontenery serwletów: Apache Tomcat, Jetty,
         * Undertow (poznamy je dokładniej w Lesson02).
         *
         * Dzięki kontenerowi Ty, jako autor serwletu, piszesz WYŁĄCZNIE
         * logikę biznesową ("co zrobić z żądaniem") – nie musisz
         * obsługiwać gniazd, wątków ani parsowania protokołu HTTP.
         */

        /*
         * ============================================================
         * 🔍 CYKL ŻYCIA SERWLETU
         * ============================================================
         * Kontener zarządza serwletem przez 3 kluczowe metody:
         *
         * 1) init(ServletConfig config) – wywoływane RAZ, przy pierwszym
         *    użyciu serwletu (lub przy starcie aplikacji). Tu robimy
         *    jednorazową inicjalizację (np. otwarcie połączenia z bazą,
         *    wczytanie konfiguracji).
         *
         * 2) service(request, response) – wywoływane PRZY KAŻDYM żądaniu
         *    HTTP trafiającym do tego serwletu. Klasa HttpServlet ma już
         *    gotową implementację service(), która sama sprawdza metodę
         *    HTTP (GET/POST/PUT/DELETE...) i przekazuje wykonanie dalej
         *    do odpowiedniej metody: doGet(), doPost(), doPut(), doDelete()
         *    itd. Dlatego w praktyce prawie NIGDY nie nadpisujemy
         *    service() bezpośrednio – nadpisujemy doGet()/doPost().
         *
         * 3) destroy() – wywoływane RAZ, tuż przed usunięciem serwletu
         *    z pamięci (np. przy zatrzymywaniu serwera). Tu zwalniamy
         *    zasoby zarezerwowane w init() (np. zamykamy połączenia).
         *
         * Kolejność dla całego życia serwletu:
         *   init() -> service() (wielokrotnie, raz na żądanie) -> destroy()
         *
         * 📌 WAŻNE: kontener tworzy zwykle JEDNĄ instancję serwletu na
         * całą aplikację i używa jej dla wszystkich żądań (różne wątki
         * z puli wątków wołają service() na tej samej instancji) – dlatego
         * pola serwletu muszą być bezpieczne wątkowo (patrz rozdział
         * _05_multithreading), jeśli w ogóle ich używamy.
         */

        System.out.println("=== LESSON 1: Servlet API – wprowadzenie ===");

        // Uruchamiamy osadzonego (embedded) Tomcata w main() – bez zewnętrznej
        // instalacji serwera. Szczegóły tego wzorca (dlaczego port 0, dlaczego
        // try/finally itd.) omawiamy dokładnie w Lesson03 – tu skupiamy się
        // na samym cyklu życia serwletu.
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson01").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "helloServlet");

        try {
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
            // destroy() serwletu zostanie wywolane wlasnie w trakcie stop()/destroy() kontenera
            System.out.println("\n--- Zatrzymuje Tomcat (destroy() serwletu wywola sie teraz) ---");
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 1 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Serwlet = klasa Javy obslugujaca zadania HTTP, zyjaca WEWNATRZ
         *   kontenera serwletow (nie dziala samodzielnie).
         * - Kontener serwletow (np. Tomcat) odpowiada za: gniazda sieciowe,
         *   parsowanie HTTP, mapowanie URL -> serwlet, pule watkow, cykl
         *   zycia serwletu.
         * - Cykl zycia serwletu: init() (raz) -> service() (za kazdym
         *   zadaniem, deleguje do doGet/doPost/...) -> destroy() (raz).
         * - HttpServlet dostarcza gotowa implementacje service(), ktora
         *   sama rozpoznaje metode HTTP i woła odpowiednia metode doXxx().
         * - Jedna instancja serwletu obsluguje WIELE zadan (wielowatkowo) -
         *   pola serwletu musza byc bezpieczne watkowo.
         * - Powyzszy log pokazuje realna kolejnosc: init -> doGet (przez
         *   service) -> destroy.
         */
    }

    /**
     * Prosty serwlet demonstrujacy 3 metody cyklu zycia: init(), doGet()
     * (wolane przez service()) i destroy().
     */
    static class HelloServlet extends HttpServlet {

        @Override
        public void init(ServletConfig config) throws ServletException {
            super.init(config);
            System.out.println("[HelloServlet] init() - wywolane RAZ, przy starcie serwletu");
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("[HelloServlet] doGet() - wywolane przez service() dla kazdego zadania GET");
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello from servlet!");
        }

        @Override
        public void destroy() {
            System.out.println("[HelloServlet] destroy() - wywolane RAZ, przy zamykaniu serwletu");
            super.destroy();
        }
    }
}
