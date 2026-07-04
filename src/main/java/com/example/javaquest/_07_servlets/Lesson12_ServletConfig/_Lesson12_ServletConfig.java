package com.example.javaquest._07_servlets.Lesson12_ServletConfig;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Enumeration;

public class _Lesson12_ServletConfig {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 ServletConfig – KONFIGURACJA PER-SERWLET
         * ============================================================
         * ServletConfig przechowuje parametry inicjalizacyjne WŁAŚCIWE
         * TYLKO DLA JEDNEGO KONKRETNEGO SERWLETU. Jeśli mamy 3 serwlety
         * w aplikacji, każdy może mieć SWOJE WŁASNE, niezależne
         * parametry init – nawet o tej samej nazwie, z różnymi wartościami
         * dla różnych serwletów, bo są ze sobą NIEPOWIĄZANE.
         *
         * To jest kluczowa różnica względem ServletContext (Lesson13),
         * gdzie parametry i atrybuty są WSPÓLNE dla całej aplikacji
         * (wszystkich serwletów naraz). Zapamiętaj:
         *
         *   ServletConfig  -> parametry JEDNEGO serwletu (per-servlet)
         *   ServletContext -> dane WSPÓLNE całej aplikacji (per-app)
         *
         * W prawdziwej aplikacji (web.xml albo adnotacja @WebServlet)
         * parametry init serwletu deklaruje się np. tak (web.xml):
         *
         *   <servlet>
         *       <servlet-name>powitanie</servlet-name>
         *       <servlet-class>...</servlet-class>
         *       <init-param>
         *           <param-name>welcomeMessage</param-name>
         *           <param-value>Witaj w sklepie!</param-value>
         *       </init-param>
         *   </servlet>
         *
         * W naszym demo z embedded Tomcat robimy to programowo: metoda
         * Tomcat.addServlet(...) zwraca obiekt Wrapper, reprezentujący
         * rejestrację serwletu w kontenerze – na NIM wywołujemy
         * addInitParameter(name, value) PRZED tomcat.start().
         */

        System.out.println("=== Lekcja 12: ServletConfig (parametry per-serwlet) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson12").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);

        // Serwlet 1: parametry init "welcomeMessage" i "maxItems"
        Wrapper welcomeWrapper = Tomcat.addServlet(context, "welcomeServlet", new WelcomeServlet());
        welcomeWrapper.addInitParameter("welcomeMessage", "Witaj w sklepie internetowym!");
        welcomeWrapper.addInitParameter("maxItems", "50");
        context.addServletMappingDecoded("/powitanie", "welcomeServlet");

        // Serwlet 2: TA SAMA nazwa parametru "welcomeMessage", ale INNA
        // wartosc - dowod, ze konfiguracje sa od siebie NIEZALEZNE.
        Wrapper adminWrapper = Tomcat.addServlet(context, "adminServlet", new WelcomeServlet());
        adminWrapper.addInitParameter("welcomeMessage", "Panel administratora");
        context.addServletMappingDecoded("/admin", "adminServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            HttpClient client = HttpClient.newHttpClient();

            /*
             * ============================================================
             * 🔹 ODCZYT W SERWLECIE: getServletConfig().getInitParameter(...)
             * ============================================================
             * Wewnątrz serwletu dostęp do parametrów jest możliwy na dwa
             * (równoważne) sposoby:
             *   this.getServletConfig().getInitParameter("nazwa")  -- jawnie
             *   this.getInitParameter("nazwa")                      -- skrot
             * (HttpServlet ma metode-wygode getInitParameter, ktora po
             * prostu deleguje do getServletConfig().getInitParameter(...)).
             * Zobacz WelcomeServlet.doGet() ponizej.
             */
            System.out.println("--- /powitanie (welcomeServlet) ---");
            HttpResponse<String> response1 = wyslijGet(client, "http://localhost:" + port + "/powitanie");
            System.out.println("Body:\n" + response1.body());

            System.out.println("--- /admin (adminServlet, INNA wartosc tego samego klucza) ---");
            HttpResponse<String> response2 = wyslijGet(client, "http://localhost:" + port + "/admin");
            System.out.println("Body:\n" + response2.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ServletConfig = parametry inicjalizacyjne DLA JEDNEGO serwletu
         *   ("per-servlet") – różne serwlety mogą mieć te same nazwy
         *   parametrów z różnymi wartościami, bez konfliktu.
         * - W embedded Tomcat: Wrapper wrapper = Tomcat.addServlet(...);
         *   wrapper.addInitParameter(name, value) PRZED tomcat.start().
         * - W prawdziwej aplikacji: <init-param> w web.xml albo
         *   @WebServlet(initParams = @WebInitParam(...)).
         * - Wewnątrz serwletu: getServletConfig().getInitParameter(name)
         *   albo skrót getInitParameter(name).
         * - getServletConfig().getInitParameterNames() – Enumeration<String>
         *   z nazwami WSZYSTKICH parametrów tego serwletu (przydatne do
         *   iteracji, gdy nie znamy nazw z góry).
         * - Kontrast z Lesson13 (ServletContext): tam parametry/atrybuty są
         *   WSPÓLNE dla całej aplikacji, a nie tylko jednego serwletu.
         */
    }

    private static HttpResponse<String> wyslijGet(HttpClient client, String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Serwlet demonstrujący odczyt parametrów init właściwych TYLKO jemu
     * (ServletConfig) - dwie różne rejestracje tej samej klasy servletu
     * (welcomeServlet i adminServlet) dostają RÓŻNE wartości parametrów.
     */
    static class WelcomeServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            ServletConfig config = getServletConfig();
            String welcomeMessage = config.getInitParameter("welcomeMessage");
            // Skrot HttpServlet -> to samo co powyzej:
            String welcomeMessageSkrot = getInitParameter("welcomeMessage");

            StringBuilder sb = new StringBuilder();
            sb.append("welcomeMessage (getServletConfig) = ").append(welcomeMessage).append("\n");
            sb.append("welcomeMessage (skrot getInitParameter) = ").append(welcomeMessageSkrot).append("\n");

            sb.append("Wszystkie nazwy parametrow init tego serwletu: ");
            Enumeration<String> names = config.getInitParameterNames();
            java.util.List<String> nameList = Collections.list(names);
            sb.append(String.join(", ", nameList)).append("\n");

            resp.getWriter().write(sb.toString());
        }
    }
}
