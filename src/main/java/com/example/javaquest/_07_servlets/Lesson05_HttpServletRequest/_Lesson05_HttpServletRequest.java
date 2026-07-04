package com.example.javaquest._07_servlets.Lesson05_HttpServletRequest;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Enumeration;

public class _Lesson05_HttpServletRequest {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST HttpServletRequest?
         * ============================================================
         * jakarta.servlet.http.HttpServletRequest to obiekt, który
         * kontener (Tomcat) tworzy dla KAŻDEGO przychodzącego żądania
         * HTTP i przekazuje jako pierwszy parametr do doGet()/doPost()/...
         * Reprezentuje WSZYSTKO, co klient wysłał: metodę HTTP, adres
         * URL, parametry zapytania, nagłówki, ciało żądania, ciasteczka,
         * powiązaną sesję i wiele więcej.
         *
         * Ta lekcja skupia się na najczęściej używanych fragmentach tego
         * API: parametrach, nagłówkach i ciele żądania. Cookies i
         * HttpSession mają swoje dedykowane lekcje (Lesson10 i Lesson11) -
         * tu pokazujemy tylko, że istnieją, żeby całość obrazu była
         * kompletna.
         */

        /*
         * ============================================================
         * 🔹 PARAMETRY ZAPYTANIA (QUERY PARAMETERS)
         * ============================================================
         * - request.getParameter("nazwa") - zwraca WARTOŚĆ (String)
         *   pojedynczego parametru albo null, jeśli go nie było.
         *   Jeśli parametr wystąpił kilka razy, zwraca PIERWSZĄ wartość.
         * - request.getParameterMap() - zwraca Map<String, String[]> -
         *   wszystkie parametry naraz, każdy jako TABLICA wartości (bo
         *   ten sam klucz może się w URL-u powtórzyć, np. ?tag=a&tag=b).
         * - request.getParameterNames() - Enumeration<String> z samymi
         *   nazwami parametrów (starsze, "enumeration-owe" API - patrz
         *   niżej przy nagłówkach, dlaczego Enumeration a nie List).
         *
         * Co ważne: getParameter() działa TAK SAMO dla parametrów z
         * query stringa URL-a (?a=1&b=2) JAK i dla pól formularza
         * wysłanego przez POST z Content-Type
         * application/x-www-form-urlencoded (patrz Lesson09) - kontener
         * ujednolica dostęp do obu źródeł.
         */

        /*
         * ============================================================
         * 🔍 NAGŁÓWKI (HEADERS)
         * ============================================================
         * - request.getHeader("Nazwa-Naglowka") - zwraca wartość
         *   pojedynczego nagłówka (String) albo null, jeśli nie istnieje.
         *   Nazwy nagłówków HTTP są NIEWRAŻLIWE na wielkość liter, więc
         *   getHeader("content-type") i getHeader("Content-Type") zwrócą
         *   to samo.
         * - request.getHeaderNames() - Enumeration<String> ze wszystkimi
         *   nazwami nagłówków przesłanych w żądaniu. Enumeration to
         *   bardzo stary interfejs Javy (jeszcze z JDK 1.0, sprzed
         *   Collections Framework) - Servlet API go używa z powodów
         *   historycznych; w nowym kodzie zwykle owija się go w
         *   Collections.list(...), żeby dostać wygodną List<String>.
         */

        /*
         * ============================================================
         * 🔍 CIAŁO ŻĄDANIA (REQUEST BODY)
         * ============================================================
         * - request.getReader() - zwraca BufferedReader do czytania ciała
         *   żądania jako TEKST (z odpowiednim dekodowaniem znaków według
         *   nagłówka Content-Type/charset). Wygodne dla treści tekstowych
         *   (JSON, formularze, zwykły tekst).
         * - request.getInputStream() - zwraca ServletInputStream do
         *   czytania ciała jako SUROWE BAJTY (używane już w Lesson04) -
         *   potrzebne dla treści binarnych (np. przesyłanych plików,
         *   patrz Lesson18).
         * ⚠️ Można wywołać TYLKO JEDNO z tych dwóch (getReader() albo
         *   getInputStream()) na tym samym żądaniu - wywołanie drugiego
         *   po pierwszym rzuci IllegalStateException, bo oba czytają z
         *   tego samego, jednorazowego strumienia danych.
         */

        /*
         * ============================================================
         * 📌 PODGLĄD: COOKIES I SESJA (szczegóły w Lesson10 i Lesson11)
         * ============================================================
         * - request.getCookies() - zwraca tablicę Cookie[] przesłanych
         *   przez klienta ciasteczek (może być null, jeśli klient nie
         *   przesłał żadnych).
         * - request.getSession() - zwraca (lub tworzy, jeśli jeszcze nie
         *   istnieje) obiekt HttpSession powiązany z tym klientem,
         *   utrzymywany między kolejnymi żądaniami zwykle właśnie przez
         *   ciasteczko JSESSIONID.
         * Obu tematów NIE rozwijamy tutaj szerzej - to pełnoprawne
         * lekcje same w sobie, do których jeszcze wrócimy.
         */

        System.out.println("=== LESSON 5: HttpServletRequest ===\n");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson05").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "requestEchoServlet", new RequestEchoServlet());
        context.addServletMappingDecoded("/echo", "requestEchoServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            String url = "http://localhost:" + port + "/echo?imie=Ala&tag=jeden&tag=dwa";
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("X-Klient-Info", "JavaQuest-Demo")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"wiadomosc\":\"witaj serwerze\"}"))
                    .build();

            System.out.println("--- Wysylam POST z parametrami, naglowkiem i cialem ---");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 5 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HttpServletRequest to obiekt reprezentujacy CALE przychodzace
         *   zadanie HTTP - metode, URL, parametry, naglowki, cialo,
         *   cookies, sesje.
         * - Parametry: getParameter(nazwa) - pojedyncza wartosc,
         *   getParameterMap() - wszystkie jako Map<String, String[]>
         *   (bo klucz moze wystapic wielokrotnie).
         * - Naglowki: getHeader(nazwa) (niewrazliwe na wielkosc liter),
         *   getHeaderNames() zwraca stare API Enumeration<String>.
         * - Cialo zadania: getReader() (tekst) LUB getInputStream()
         *   (bajty) - NIGDY oba na raz (IllegalStateException).
         * - getCookies() i getSession() istnieja i dzialaja juz teraz,
         *   ale ich pelne omowienie jest w Lesson10 (Cookies) i Lesson11
         *   (HttpSession).
         */
    }

    /**
     * Serwlet demonstrujacy odczyt parametrow, naglowkow i ciala zadania
     * jednoczesnie, zwracajac wszystko w jednej tekstowej odpowiedzi.
     */
    static class RequestEchoServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            StringBuilder out = new StringBuilder();

            out.append("== Parametry (getParameter) ==\n");
            out.append("imie = ").append(req.getParameter("imie")).append("\n");

            out.append("\n== Parametry (getParameterMap) ==\n");
            for (var entry : req.getParameterMap().entrySet()) {
                out.append(entry.getKey()).append(" = ")
                        .append(String.join(",", entry.getValue())).append("\n");
            }

            out.append("\n== Naglowki (getHeaderNames + getHeader) ==\n");
            Enumeration<String> headerNames = req.getHeaderNames();
            for (String name : Collections.list(headerNames)) {
                out.append(name).append(": ").append(req.getHeader(name)).append("\n");
            }

            out.append("\n== Cialo zadania (getReader) ==\n");
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line).append("\n");
                }
            }

            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write(out.toString());
        }
    }
}
