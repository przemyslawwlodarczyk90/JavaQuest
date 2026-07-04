package com.example.javaquest._07_servlets.Lesson11_HttpSession;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class _Lesson11_HttpSession {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 PO CO HttpSession, SKORO SĄ JUŻ COOKIES?
         * ============================================================
         * Cookies (Lesson10) świetnie nadają się do przechowywania MAŁYCH,
         * prostych danych PO STRONIE KLIENTA (tekst wysyłany tam i z
         * powrotem przy każdym żądaniu). Ale co, jeśli chcemy trzymać
         * WIĘKSZE / bardziej złożone dane (obiekty, listy, liczniki) PO
         * STRONIE SERWERA, powiązane z konkretnym klientem?
         *
         * Do tego służy HttpSession – obiekt utrzymywany PRZEZ SERWER
         * (w pamięci kontenera), z możliwością przechowania DOWOLNYCH
         * atrybutów (Object), a nie tylko Stringów jak w Cookie.
         *
         * 🔍 JAK TO DZIAŁA "POD SPODEM"?
         * Serwer i tak potrzebuje cookie, żeby rozpoznać, KTÓRY klient
         * wysyła żądanie! Kontener automatycznie tworzy specjalne cookie
         * o nazwie JSESSIONID z identyfikatorem sesji – to jedyne, co
         * faktycznie podróżuje między klientem a serwerem. Reszta danych
         * (nasze atrybuty) fizycznie siedzi w pamięci serwera, w mapie
         * powiązanej z tym identyfikatorem.
         *
         * Dlatego – DOKŁADNIE jak w Lesson10 – klient HttpClient MUSI mieć
         * CookieManager, żeby JSESSIONID było zapamiętywane i odsyłane
         * między żądaniami. Bez tego każde żądanie dostawałoby NOWĄ sesję!
         */

        System.out.println("=== Lekcja 11: HttpSession ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson11").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "sessionServlet", new SessionServlet());
        context.addServletMappingDecoded("/licznik", "sessionServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/licznik";

            HttpClient client = HttpClient.newBuilder()
                    .cookieHandler(new CookieManager())
                    .build();

            /*
             * ============================================================
             * 🔹 request.getSession() – POBIERZ ALBO STWÓRZ SESJĘ
             * ============================================================
             * getSession() (bez argumentów, lub getSession(true)) zwraca
             * ISTNIEJĄCĄ sesję klienta, a jeśli klient jeszcze jej nie ma
             * - TWORZY nową. Zobacz doGet() w SessionServlet: pierwsze
             * żądanie tworzy sesję i licznik odwiedzin = 1.
             */
            System.out.println("--- Żądanie 1: nowa sesja, licznik = 1 ---");
            HttpResponse<String> response1 = wykonajZadanie(client, url);
            System.out.println("Body:\n" + response1.body());

            System.out.println("\n--- Żądanie 2: ta sama sesja (dzieki cookie JSESSIONID), licznik = 2 ---");
            HttpResponse<String> response2 = wykonajZadanie(client, url);
            System.out.println("Body:\n" + response2.body());

            System.out.println("\n--- Żądanie 3: ta sama sesja, licznik = 3 ---");
            HttpResponse<String> response3 = wykonajZadanie(client, url);
            System.out.println("Body:\n" + response3.body());

            // Weryfikacja: ID sesji z zadan 1-3 powinno byc TAKIE SAMO,
            // a licznik rosnie 1 -> 2 -> 3. To DOWÓD, ze stan przetrwal
            // miedzy oddzielnymi zadaniami HTTP.
            String id1 = wyciagnijId(response1.body());
            String id2 = wyciagnijId(response2.body());
            String id3 = wyciagnijId(response3.body());
            System.out.println("\nWeryfikacja ID sesji: " + id1 + " / " + id2 + " / " + id3);
            System.out.println("Wszystkie identyczne? " + (id1.equals(id2) && id2.equals(id3)));

            /*
             * ============================================================
             * 🔍 request.getSession(false) – TYLKO ODCZYT, BEZ TWORZENIA
             * ============================================================
             * getSession(false) zwraca istniejącą sesję, ale jeśli klient
             * jej NIE MA – zwraca null (zamiast tworzyć nową). To DOBRA
             * PRAKTYKA, gdy tylko CHCEMY SPRAWDZIĆ czy sesja istnieje
             * (np. "czy użytkownik jest zalogowany?"), a nie chcemy
             * przypadkiem tworzyć zbędnych, pustych sesji dla każdego,
             * kto tylko odwiedził stronę bez logowania.
             *
             * 🔍 session.invalidate() – UNIEWAŻNIENIE SESJI
             * ============================================================
             * Kończy sesję po stronie serwera (np. przy wylogowaniu) –
             * wszystkie atrybuty przepadają. Kolejne getSession() dla
             * tego samego klienta stworzy zupełnie NOWĄ sesję (nowe ID).
             */
            System.out.println("\n--- Żądanie 4: uniewazniamy sesje (invalidate), potem nowe zadanie ---");
            HttpRequest invalidateRequest = HttpRequest.newBuilder(URI.create(url + "?invalidate=true"))
                    .GET().build();
            HttpResponse<String> responseInvalidate =
                    client.send(invalidateRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Body:\n" + responseInvalidate.body());

            System.out.println("\n--- Żądanie 5: po invalidate, nowa sesja, licznik znow = 1 ---");
            HttpResponse<String> response5 = wykonajZadanie(client, url);
            System.out.println("Body:\n" + response5.body());
            String id5 = wyciagnijId(response5.body());
            System.out.println("Nowe ID rozne od poprzedniego? " + !id5.equals(id1));

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HttpSession = dane po stronie SERWERA, powiązane z klientem
         *   przez cookie JSESSIONID (tworzone automatycznie przez kontener).
         * - request.getSession() / getSession(true) – zwraca istniejącą
         *   sesję albo tworzy nową.
         * - request.getSession(false) – zwraca istniejącą sesję albo null,
         *   NIGDY nie tworzy nowej (dobra praktyka przy samym odczycie).
         * - session.setAttribute(name, obj) / getAttribute(name) –
         *   przechowywanie DOWOLNYCH obiektów (nie tylko String jak Cookie).
         * - session.getId() – unikalny identyfikator sesji.
         * - session.invalidate() – kończy sesję (usuwa wszystkie atrybuty,
         *   kolejne getSession() da nowe ID).
         * - Wymaga CookieManager po stronie klienta (tak jak Lesson10),
         *   inaczej JSESSIONID nie będzie odsyłane i każde żądanie
         *   dostanie nową sesję.
         */
    }

    private static HttpResponse<String> wykonajZadanie(HttpClient client, String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String wyciagnijId(String body) {
        for (String line : body.split("\n")) {
            if (line.startsWith("ID sesji:")) {
                return line.substring("ID sesji:".length()).trim();
            }
        }
        return "(brak)";
    }

    /**
     * Serwlet demonstrujący tworzenie/odczyt sesji, licznik odwiedzin
     * trzymany jako atrybut sesji, oraz unieważnianie sesji.
     */
    static class SessionServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
            resp.setContentType("text/plain;charset=UTF-8");

            if ("true".equals(req.getParameter("invalidate"))) {
                HttpSession existing = req.getSession(false);
                if (existing != null) {
                    String staryId = existing.getId();
                    existing.invalidate();
                    resp.getWriter().write("Sesja uniewazniona. Stare ID sesji: " + staryId + "\n");
                } else {
                    resp.getWriter().write("Brak sesji do uniewaznienia.\n");
                }
                return;
            }

            HttpSession session = req.getSession(); // stworzy, jesli nie istnieje
            Integer licznik = (Integer) session.getAttribute("licznikOdwiedzin");
            if (licznik == null) {
                licznik = 0;
            }
            licznik++;
            session.setAttribute("licznikOdwiedzin", licznik);

            resp.getWriter().write(
                    "ID sesji: " + session.getId() + "\n"
                            + "Licznik odwiedzin: " + licznik + "\n"
                            + "Nowa sesja (isNew): " + session.isNew() + "\n");
        }
    }
}
