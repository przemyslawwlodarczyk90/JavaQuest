package com.example.javaquest._07_servlets.Lesson06_HttpServletResponse;

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

public class _Lesson06_HttpServletResponse {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST HttpServletResponse?
         * ============================================================
         * jakarta.servlet.http.HttpServletResponse to obiekt, którym
         * serwlet BUDUJE odpowiedź HTTP wysyłaną z powrotem do klienta:
         * kod statusu, nagłówki i ciało odpowiedzi. Kontener przekazuje
         * go jako drugi parametr do doGet()/doPost()/... - wszystko, co
         * na nim ustawimy PRZED wysłaniem odpowiedzi, trafi do klienta.
         */

        /*
         * ============================================================
         * 🔹 KODY STATUSU
         * ============================================================
         * - response.setStatus(kod) - ustawia kod odpowiedzi (np. 200,
         *   201, 204...) BEZ zmiany treści ciała - używane dla sukcesów
         *   i przypadków, gdy sami kontrolujemy zarówno status jak i body.
         * - response.sendError(kod, wiadomosc) - ustawia kod błędu (np.
         *   404, 500) I generuje standardową stronę błędu kontenera z
         *   podaną wiadomością. Po jego wywołaniu odpowiedź jest uznana
         *   za "zatwierdzoną" (committed) w sensie błędu - nie powinniśmy
         *   już pisać własnego ciała odpowiedzi.
         * Stałe kodów są dostępne jako pola HttpServletResponse, np.
         * HttpServletResponse.SC_OK (200), SC_NOT_FOUND (404),
         * SC_INTERNAL_SERVER_ERROR (500).
         */

        /*
         * ============================================================
         * 🔍 NAGŁÓWKI ODPOWIEDZI
         * ============================================================
         * - response.setHeader(nazwa, wartosc) - USTAWIA nagłówek,
         *   NADPISUJĄC każdą wcześniejszą wartość o tej samej nazwie.
         * - response.addHeader(nazwa, wartosc) - DODAJE kolejną wartość
         *   dla danej nazwy nagłówka, nie kasując poprzednich (przydatne
         *   dla nagłówków, które mogą wystąpić wielokrotnie, np.
         *   Set-Cookie).
         * 📌 Zasada: jeśli chcesz mieć PEWNOŚĆ, że nagłówek ma dokładnie
         * jedną wartość - użyj setHeader(). Jeśli świadomie chcesz
         * dodawać kolejne wartości do już istniejącego nagłówka - addHeader().
         */

        /*
         * ============================================================
         * 🔍 PRZEKIEROWANIA: sendRedirect()
         * ============================================================
         * response.sendRedirect(url) ustawia status 302 (Found) oraz
         * nagłówek Location wskazujący nowy adres URL - to mówi klientowi
         * (przeglądarce, HttpClientowi): "to, czego szukasz, jest pod
         * INNYM adresem, wyślij tam nowe żądanie". W przeciwieństwie do
         * forward (server-side, poznamy w Lesson17), redirect jest
         * WIDOCZNY dla klienta - pasek adresu w przeglądarce faktycznie
         * się zmienia, bo to klient wykonuje DRUGIE, osobne żądanie.
         */

        System.out.println("=== LESSON 6: HttpServletResponse ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson06").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "okServlet", new OkWithHeaderServlet());
        context.addServletMappingDecoded("/ok", "okServlet");
        Tomcat.addServlet(context, "errorServlet", new NotFoundServlet());
        context.addServletMappingDecoded("/brakuje", "errorServlet");
        Tomcat.addServlet(context, "redirectServlet", new RedirectServlet());
        context.addServletMappingDecoded("/stara-sciezka", "redirectServlet");
        Tomcat.addServlet(context, "celServlet", new CelServlet());
        context.addServletMappingDecoded("/nowa-sciezka", "celServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String base = "http://localhost:" + port;
            HttpClient client = HttpClient.newHttpClient();

            System.out.println("--- GET /ok (status 200 + wlasny naglowek) ---");
            HttpResponse<String> okResponse = client.send(
                    HttpRequest.newBuilder(URI.create(base + "/ok")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + okResponse.statusCode());
            System.out.println("X-Powitanie: " + okResponse.headers().firstValue("X-Powitanie").orElse("(brak)"));
            System.out.println("Body: " + okResponse.body());

            System.out.println("\n--- GET /brakuje (sendError 404) ---");
            HttpResponse<String> errorResponse = client.send(
                    HttpRequest.newBuilder(URI.create(base + "/brakuje")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + errorResponse.statusCode());
            System.out.println("Body zawiera nasz komunikat: " + errorResponse.body().contains("Zasob nie istnieje"));

            /*
             * Klient zbudowany z followRedirects(NORMAL) SAM podąża za
             * przekierowaniami (301/302/303/307/308) - dlatego poniższe
             * żądanie do /stara-sciezka w rzeczywistości zakończy się
             * odpowiedzią z /nowa-sciezka, a statusCode() pokaże już 200
             * (status finalnej odpowiedzi, nie samego przekierowania).
             */
            System.out.println("\n--- GET /stara-sciezka (sendRedirect, klient podaza za przekierowaniem) ---");
            HttpClient redirectingClient = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpResponse<String> redirectResponse = redirectingClient.send(
                    HttpRequest.newBuilder(URI.create(base + "/stara-sciezka")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("Finalny status: " + redirectResponse.statusCode());
            System.out.println("Finalny URI: " + redirectResponse.uri());
            System.out.println("Body: " + redirectResponse.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 6 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - setStatus(kod) ustawia sam kod odpowiedzi (my kontrolujemy
         *   tresc); sendError(kod, wiadomosc) ustawia kod BLEDU i
         *   generuje standardowa strone bledu kontenera.
         * - setHeader(nazwa, wartosc) NADPISUJE, addHeader(nazwa, wartosc)
         *   DODAJE kolejna wartosc dla tej samej nazwy naglowka.
         * - sendRedirect(url) = status 302 + naglowek Location - klient
         *   wykonuje DRUGIE, osobne zadanie pod nowy adres (w
         *   przeciwienstwie do server-side forward z Lesson17).
         *   HttpClient.Redirect.NORMAL sprawia, ze java.net.http.HttpClient
         *   sam podaza za takim przekierowaniem.
         * - getWriter() (tekst, PrintWriter) i getOutputStream() (bajty,
         *   ServletOutputStream) sa WZAJEMNIE WYKLUCZAJACE SIE - wywolanie
         *   OBU na tej samej odpowiedzi rzuci IllegalStateException, bo
         *   oba pisza do tego samego, jednorazowego strumienia wyjsciowego.
         * - Wybor miedzy nimi jest analogiczny do getReader() vs
         *   getInputStream() po stronie zadania (Lesson05): tekst -> writer,
         *   bajty (np. binarny plik) -> output stream.
         */
    }

    /** Zwraca 200 z niestandardowym naglowkiem odpowiedzi. */
    static class OkWithHeaderServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("X-Powitanie", "Witaj-w-JavaQuest");
            resp.setContentType("text/plain;charset=UTF-8");
            // getWriter() zwraca PrintWriter do pisania TEKSTU. Gdybysmy
            // po nim wywolali jeszcze resp.getOutputStream(), dostalibysmy
            // IllegalStateException - oba API pisza do tego samego,
            // jednorazowego strumienia wyjsciowego odpowiedzi.
            resp.getWriter().write("Odpowiedz 200 z wlasnym naglowkiem X-Powitanie.");
        }
    }

    /** Demonstruje sendError - kontener sam generuje strone bledu. */
    static class NotFoundServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Zasob nie istnieje");
        }
    }

    /** Przekierowuje na inny adres w tej samej aplikacji. */
    static class RedirectServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.sendRedirect("/nowa-sciezka");
        }
    }

    /** Cel przekierowania - zwykla odpowiedz 200. */
    static class CelServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Dotarles na nowa sciezke po przekierowaniu.");
        }
    }
}
