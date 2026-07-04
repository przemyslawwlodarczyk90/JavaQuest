package com.example.javaquest._07_servlets.Lesson14_Filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class _Lesson14_Filters {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST FILTR (jakarta.servlet.Filter)?
         * ============================================================
         * FILTR to komponent, ktory wpina sie w POTOK przetwarzania
         * zadania HTTP – MIEDZY klientem a docelowym serwletem (a takze
         * po drodze z powrotem, przy odpowiedzi). Filtry mozna laczyc
         * w LANCUCH (chain) – zadanie przechodzi kolejno przez filtr1,
         * filtr2, ..., dopiero na koncu trafia do serwletu, a potem
         * odpowiedz wraca przez ten sam lancuch w odwrotnej kolejnosci.
         *
         * Typowe zastosowania filtrow:
         * - logowanie / mierzenie czasu obslugi zadania,
         * - uwierzytelnianie i autoryzacja (sprawdzenie tokenu/sesji
         *   ZANIM zadanie w ogole dotrze do serwletu),
         * - kompresja odpowiedzi (np. gzip),
         * - ujednolicanie kodowania znakow (setCharacterEncoding),
         * - walidacja / modyfikacja naglowkow.
         *
         * Kluczowa roznica wzgledem serwletu: filtr NIE jest przypisany
         * do jednego konkretnego adresu URL (choc moze byc) – zwykle
         * mapuje sie go na WZORZEC ("/*"), wiec dziala dla calej
         * aplikacji, niezaleznie od tego, ktory serwlet finalnie
         * obsluzy zadanie.
         */

        /*
         * ============================================================
         * 🔹 INTERFEJS Filter – TRZY METODY
         * ============================================================
         * jakarta.servlet.Filter definiuje:
         *
         * 1) init(FilterConfig config) – wywolywane RAZ, przy starcie
         *    filtru (analogicznie do init() serwletu).
         *
         * 2) doFilter(ServletRequest req, ServletResponse resp,
         *             FilterChain chain) – serce filtru, wywolywane
         *    dla KAZDEGO pasujacego zadania.
         *
         * 3) destroy() – wywolywane RAZ, przy zamykaniu filtru.
         *
         * ⚠️ NAJWAZNIEJSZA ZASADA CALEJ LEKCJI:
         * Wewnatrz doFilter() TRZEBA jawnie wywolac
         * chain.doFilter(request, response), zeby przekazac sterowanie
         * DALEJ – albo do kolejnego filtru w lancuchu, albo (jesli to
         * ostatni filtr) do docelowego serwletu.
         *
         * Jesli ZAPOMNISZ wywolac chain.doFilter(...) – to klasyczny,
         * bardzo czesty blad poczatkujacych: zadanie po prostu "utyka"
         * w tym filtrze. Serwlet docelowy NIGDY nie zostanie wywolany,
         * a klient dostanie pusta (albo niekompletna) odpowiedz, bez
         * zadnego wyjatku czy bledu wskazujacego przyczyne – bo z punktu
         * widzenia kontenera wszystko przebieglo "poprawnie" (metoda
         * doFilter po prostu sie zakonczyla).
         *
         * Kod, ktory demonstrowalby ten blad, wygladalby tak (CELOWO
         * tego NIE uruchamiamy w tej lekcji):
         *
         *   public void doFilter(ServletRequest req, ServletResponse resp,
         *                         FilterChain chain) {
         *       System.out.println("Filtr dziala...");
         *       // BRAK: chain.doFilter(req, resp);
         *       // -> serwlet nigdy sie nie wykona!
         *   }
         *
         * Dlatego doFilter() prawie zawsze ma ksztalt: "kod PRZED",
         * potem chain.doFilter(...), potem "kod PO" (opcjonalnie).
         */

        /*
         * ============================================================
         * 🔍 REJESTRACJA FILTRU BEZ web.xml – FilterDef + FilterMap
         * ============================================================
         * Tak jak serwlety rejestrowalismy programowo przez
         * Tomcat.addServlet(...), tak filtry rejestrujemy przez dwie
         * klasy z pakietu org.apache.tomcat.util.descriptor.web:
         *
         * - FilterDef – definicja filtru: nazwa + instancja filtru.
         * - FilterMap – mapowanie: ktora nazwa filtru na jaki wzorzec
         *   URL (addURLPattern) lub jaka nazwe serwletu (addServletName).
         *
         * Oba obiekty dodajemy do kontekstu metodami
         * context.addFilterDef(filterDef) i context.addFilterMap(filterMap).
         * To dokladny odpowiednik tego, co w web.xml zapisalibysmy jako
         * <filter> + <filter-mapping>.
         */

        System.out.println("=== Lekcja 14: Filtry (Filter) ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson14").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "helloServlet");

        // --- Rejestracja filtru: FilterDef (definicja) + FilterMap (mapowanie) ---
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("timingFilter");
        filterDef.setFilter(new TimingFilter());
        context.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("timingFilter");
        filterMap.addURLPattern("/*"); // filtr dziala dla WSZYSTKICH adresow
        context.addFilterMap(filterMap);

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Osadzony Tomcat wystartowal na porcie: " + port);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/hello"))
                    .GET()
                    .build();

            System.out.println("\n--- Wysylam zadanie GET /hello (przez TimingFilter) ---");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Odpowiedz serwera ---");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 14 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Filtr (jakarta.servlet.Filter) przechwytuje zadanie PRZED
         *   (i odpowiedz PO) dotarciu do serwletu docelowego, dziala
         *   zwykle dla wzorca URL, a nie jednego konkretnego serwletu.
         * - Metody cyklu zycia: init() (raz) -> doFilter() (za kazdym
         *   zadaniem) -> destroy() (raz) – analogicznie do serwletu.
         * - ⚠️ ZAWSZE trzeba wywolac chain.doFilter(request, response)
         *   wewnatrz doFilter() – w przeciwnym razie zadanie "utyka"
         *   w filtrze, a serwlet docelowy nigdy sie nie wykona.
         * - Typowy wzorzec: kod PRZED chain.doFilter(...), samo wywolanie
         *   chain.doFilter(...), kod PO – tak jak w naszym TimingFilter,
         *   ktory mierzy czas obslugi zadania.
         * - Bez web.xml rejestrujemy filtr programowo: FilterDef
         *   (nazwa + instancja) + FilterMap (nazwa -> wzorzec URL),
         *   dodane do kontekstu przez addFilterDef()/addFilterMap().
         */
    }

    /**
     * Prosty serwlet, ktory ma zostac "opakowany" przez TimingFilter.
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
     * Filtr mierzacy czas obslugi zadania. Loguje czas PRZED wywolaniem
     * chain.doFilter(...) oraz PO jego zakonczeniu (czyli PO tym, jak
     * serwlet docelowy juz skonczyl obsluge i odpowiedz jest gotowa).
     */
    static class TimingFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) {
            System.out.println("[TimingFilter] init() - filtr wystartowal");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            long start = System.currentTimeMillis();
            System.out.println("[TimingFilter] PRZED chain.doFilter() - zadanie wchodzi do lancucha");

            // KLUCZOWE wywolanie - przekazuje sterowanie dalej (tu: do serwletu,
            // bo to jedyny filtr w tym przykladzie).
            chain.doFilter(request, response);

            long elapsed = System.currentTimeMillis() - start;
            System.out.println("[TimingFilter] PO chain.doFilter() - serwlet skonczyl obsluge, "
                    + "czas: " + elapsed + " ms");
        }

        @Override
        public void destroy() {
            System.out.println("[TimingFilter] destroy() - filtr zatrzymany");
        }
    }
}
