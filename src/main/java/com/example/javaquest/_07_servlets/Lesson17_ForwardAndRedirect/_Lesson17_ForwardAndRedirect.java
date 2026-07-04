package com.example.javaquest._07_servlets.Lesson17_ForwardAndRedirect;

import jakarta.servlet.RequestDispatcher;
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

public class _Lesson17_ForwardAndRedirect {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 FORWARD vs REDIRECT – DWA SPOSOBY "PRZEKAZANIA DALEJ"
         * ============================================================
         * Czesto serwlet nie chce sam generowac calej odpowiedzi, tylko
         * przekazac obsluge zadania innemu zasobowi (innemu serwletowi,
         * JSP itp.). Sa na to dwa fundamentalnie rozne sposoby:
         *
         * 1) FORWARD (RequestDispatcher.forward) – przekazanie PO STRONIE
         *    SERWERA, w ramach JEDNEGO cyklu zadanie-odpowiedz HTTP.
         * 2) REDIRECT (response.sendRedirect) – serwer odsyla klienta
         *    z powrotem z odpowiedzia 302 i naglowkiem Location, a to
         *    PRZEGLADARKA (klient) wykonuje NOWE, DRUGIE zadanie HTTP
         *    pod wskazany adres.
         *
         * Ta roznica ma realne konsekwencje, ktore zaraz zademonstrujemy.
         */

        /*
         * ============================================================
         * 🔹 FORWARD – RequestDispatcher, jeden cykl HTTP
         * ============================================================
         * request.getRequestDispatcher("/sciezka").forward(request, response):
         * - dzieje sie CALKOWICIE po stronie serwera, w tym samym watku,
         *   w ramach JEDNEGO zadania HTTP (klient wysyla jedno zadanie
         *   i dostaje JEDNA odpowiedz - nie wie nawet, ze "po drodze"
         *   zaangazowany byl inny serwlet),
         * - adres URL w przegladarce klienta SIE NIE ZMIENIA (bo z
         *   punktu widzenia klienta to wciaz to samo, jedno zadanie),
         * - atrybuty ustawione na request PRZED forward() (request.setAttribute)
         *   SA WIDOCZNE w serwlecie, do ktorego przekazujemy sterowanie
         *   (bo to caly czas TEN SAM obiekt HttpServletRequest) - to
         *   standardowy sposob przekazywania danych "w locie" miedzy
         *   serwletami bez uzycia sesji czy parametrow URL.
         */

        /*
         * ============================================================
         * 🔍 REDIRECT – sendRedirect, dwa cykle HTTP
         * ============================================================
         * response.sendRedirect(url):
         * - serwer odsyla odpowiedz ze statusem 302 (Found) i naglowkiem
         *   Location: <url>, a NASTEPNIE konczy obsluge TEGO zadania,
         * - to PRZEGLADARKA decyduje, czy i kiedy wykonac kolejne zadanie
         *   pod adres z Location - w efekcie powstaja DWA odrebne zadania
         *   HTTP, KAZDE z osobnym obiektem HttpServletRequest,
         * - adres URL w przegladarce ZMIENIA SIE na nowy,
         * - atrybuty ustawione na pierwszym request PRZEPADAJA - drugie
         *   zadanie ma zupelnie NOWY obiekt request (dane trzeba by
         *   przekazac np. parametrem w URL albo przez sesje).
         *
         * java.net.http.HttpClient DOMYSLNIE NIE podaza za przekierowaniami
         * (polityka NEVER) - dostaniemy "z rak" surowa odpowiedz 302 z
         * naglowkiem Location, bez automatycznego wykonania drugiego
         * zadania. Zeby klient SAM wykonal to drugie zadanie (tak jak
         * robi to przegladarka), trzeba jawnie ustawic:
         *
         *   HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()
         */

        System.out.println("=== Lekcja 17: forward() vs sendRedirect() ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson17").toString());
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "forwardSourceServlet", new ForwardSourceServlet());
        context.addServletMappingDecoded("/forward-source", "forwardSourceServlet");
        Tomcat.addServlet(context, "forwardTargetServlet", new ForwardTargetServlet());
        context.addServletMappingDecoded("/forward-target", "forwardTargetServlet");
        Tomcat.addServlet(context, "redirectSourceServlet", new RedirectSourceServlet());
        context.addServletMappingDecoded("/redirect-source", "redirectSourceServlet");
        Tomcat.addServlet(context, "redirectTargetServlet", new RedirectTargetServlet());
        context.addServletMappingDecoded("/redirect-target", "redirectTargetServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String base = "http://localhost:" + port;
            System.out.println("Osadzony Tomcat wystartowal na porcie: " + port);

            /*
             * --- DEMO 1: FORWARD ---
             * Jedno zadanie HTTP, jeden HttpClient bez zadnej specjalnej
             * konfiguracji - klient nawet nie "wie", ze po drodze serwer
             * wykonal forward() do innego serwletu.
             */
            System.out.println("\n--- DEMO 1: FORWARD (/forward-source -> /forward-target) ---");
            HttpClient plainClient = HttpClient.newHttpClient();
            HttpRequest forwardRequest = HttpRequest.newBuilder(URI.create(base + "/forward-source"))
                    .GET()
                    .build();
            HttpResponse<String> forwardResponse =
                    plainClient.send(forwardRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + forwardResponse.statusCode());
            System.out.println("Body: " + forwardResponse.body());
            System.out.println("(Jedno zadanie HTTP - URL w 'przegladarce' pozostalby /forward-source)");

            /*
             * --- DEMO 2a: REDIRECT bez podazania za przekierowaniem ---
             * Domyslny HttpClient (polityka Redirect.NEVER) pokazuje nam
             * SUROWA odpowiedz 302 z naglowkiem Location - dokladnie to,
             * co serwer faktycznie odeslal, ZANIM cokolwiek "podazy dalej".
             */
            System.out.println("\n--- DEMO 2a: REDIRECT, klient BEZ podazania za przekierowaniem ---");
            HttpRequest redirectRequest = HttpRequest.newBuilder(URI.create(base + "/redirect-source"))
                    .GET()
                    .build();
            HttpResponse<String> rawRedirectResponse =
                    plainClient.send(redirectRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + rawRedirectResponse.statusCode() + " (302 = Found/przekierowanie)");
            System.out.println("Naglowek Location: "
                    + rawRedirectResponse.headers().firstValue("Location").orElse("(brak)"));
            System.out.println("Body (puste lub prawie puste, bo klient NIE poszedl dalej): '"
                    + rawRedirectResponse.body() + "'");

            /*
             * --- DEMO 2b: REDIRECT z followRedirects(NORMAL) ---
             * Ten sam adres, ale klient skonfigurowany tak, by SAM wykonac
             * drugie zadanie HTTP pod adres z Location - dokladnie tak,
             * jak zrobilaby to przegladarka.
             */
            System.out.println("\n--- DEMO 2b: REDIRECT, klient Z podazaniem za przekierowaniem ---");
            HttpClient followingClient = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpResponse<String> followedResponse =
                    followingClient.send(redirectRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + followedResponse.statusCode() + " (200 - to juz odpowiedz DRUGIEGO zadania)");
            System.out.println("Finalny URI: " + followedResponse.uri());
            System.out.println("Body: " + followedResponse.body());

        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 17 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - FORWARD (request.getRequestDispatcher(sciezka).forward(req, resp)):
         *   jeden cykl HTTP, po stronie serwera, URL w przegladarce sie
         *   nie zmienia, atrybuty ustawione PRZED forward() sa widoczne
         *   PO forward() (ten sam obiekt request).
         * - REDIRECT (response.sendRedirect(url)): serwer odpowiada 302 +
         *   Location, klient (przegladarka) wykonuje DRUGIE, oddzielne
         *   zadanie HTTP - URL w przegladarce SIE ZMIENIA, a atrybuty z
         *   pierwszego zadania PRZEPADAJA (nowy obiekt request).
         * - java.net.http.HttpClient domyslnie NIE podaza za przekierowaniami
         *   (Redirect.NEVER) - trzeba jawnie ustawic
         *   .followRedirects(HttpClient.Redirect.NORMAL), inaczej dostaniemy
         *   surowa odpowiedz 302 zamiast finalnej tresci.
         * - Kiedy uzyc czego: FORWARD, gdy logika pozostaje "wewnatrz"
         *   serwera (np. serwlet kontrolera przekazuje do widoku); REDIRECT,
         *   gdy chcemy, zeby przegladarka miala WLASCIWY, nowy adres w
         *   pasku URL (np. klasyczny wzorzec Post/Redirect/Get po
         *   wyslaniu formularza, zeby odswiezenie strony nie wysylalo
         *   formularza ponownie).
         */
    }

    /** Ustawia atrybut na request, po czym przekazuje sterowanie przez forward(). */
    static class ForwardSourceServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            req.setAttribute("wiadomosc", "Dane ustawione w ForwardSourceServlet PRZED forward()");
            System.out.println("[ForwardSourceServlet] ustawiam atrybut, przekazuje forward() do /forward-target");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/forward-target");
            dispatcher.forward(req, resp);
        }
    }

    /** Odczytuje atrybut ustawiony przez ForwardSourceServlet - to WCIAZ ten sam request. */
    static class ForwardTargetServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String wiadomosc = (String) req.getAttribute("wiadomosc");
            System.out.println("[ForwardTargetServlet] odczytany atrybut: " + wiadomosc);

            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Odpowiedz z ForwardTargetServlet. Atrybut z poprzedniego serwletu: "
                    + wiadomosc);
        }
    }

    /** Wysyla klientowi przekierowanie 302 na /redirect-target. */
    static class RedirectSourceServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("[RedirectSourceServlet] wysylam sendRedirect na /redirect-target");
            resp.sendRedirect("/redirect-target");
        }
    }

    /** Cel przekierowania - obsluguje CALKIEM NOWE zadanie HTTP wyslane przez klienta. */
    static class RedirectTargetServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("[RedirectTargetServlet] obsluguje DRUGIE, nowe zadanie HTTP");
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Odpowiedz z RedirectTargetServlet - to nowe zadanie HTTP.");
        }
    }
}
