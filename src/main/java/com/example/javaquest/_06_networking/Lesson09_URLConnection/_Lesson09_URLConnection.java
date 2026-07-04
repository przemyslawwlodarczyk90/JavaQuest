package com.example.javaquest._06_networking.Lesson09_URLConnection;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson09_URLConnection {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST URLConnection?
         * ============================================================
         * URLConnection to abstrakcyjna klasa reprezentująca "aktywne
         * połączenie" pomiędzy naszą aplikacją a zasobem wskazanym przez
         * URL. W przeciwieństwie do samego URL/URI (Lesson08 – tylko
         * parsowanie adresu), URLConnection faktycznie ŁĄCZY SIĘ z siecią.
         *
         * Typowy przepływ pracy:
         *   1. URL url = uri.toURL();
         *   2. URLConnection connection = url.openConnection(); // NIE łączy się jeszcze!
         *   3. odczyt nagłówków / getInputStream() -> DOPIERO TERAZ następuje
         *      faktyczne połączenie (leniwe, "lazy connect")
         *
         * Dla adresów http/https zwrócony obiekt to w rzeczywistości
         * HttpURLConnection (patrz Lesson10) – URLConnection to jego
         * bardziej ogólna klasa bazowa.
         */

        /*
         * ============================================================
         * 🔹 DLACZEGO LOKALNY SERWER W TEJ LEKCJI?
         * ============================================================
         * Żeby lekcja była szybka, deterministyczna i nie zależała od
         * internetu (ani od tego, czy jakaś zewnętrzna strona akurat
         * działa), uruchamiamy WŁASNY, malutki serwer HTTP w tym samym
         * procesie JVM – przy użyciu wbudowanej w JDK klasy
         * com.sun.net.httpserver.HttpServer. Serwer nasłuchuje na
         * porcie 0, co oznacza "niech system operacyjny sam wybierze
         * wolny port" (unikamy konfliktów portów).
         *
         * To DOKŁADNIE ten sam URLConnection API, którego użylibyśmy
         * do połączenia się z prawdziwą stroną w internecie – zmienia
         * się tylko adres (localhost zamiast zdalnego hosta).
         */

        System.out.println("=== Uruchamianie lokalnego serwera HTTP ===");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/hello", exchange -> {
            String responseBody = "Witaj z lokalnego serwera HTTP!";
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.getResponseHeaders().add("X-Lekcja", "09-URLConnection");
            exchange.sendResponseHeaders(200, bytes.length);

            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();

        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartowal na porcie: " + port);

        try {
            /*
             * ============================================================
             * 🔍 OTWIERANIE POŁĄCZENIA – url.openConnection()
             * ============================================================
             * openConnection() TYLKO TWORZY obiekt połączenia – nie łączy
             * się jeszcze z serwerem! Dopiero wywołanie metody, która
             * potrzebuje danych z serwera (np. getInputStream(),
             * getHeaderFields(), getResponseCode()) wyzwala faktyczne
             * połączenie sieciowe.
             */

            URI uri = new URI("http://localhost:" + port + "/hello");
            URLConnection connection = uri.toURL().openConnection();

            System.out.println("\n=== Typ zwroconego obiektu ===");
            System.out.println(connection.getClass().getName());
            // sun.net.www.protocol.http.HttpURLConnection – dla http(s)
            // URLConnection.openConnection() zawsze zwraca konkretny
            // podtyp HttpURLConnection (patrz Lesson10)

            /*
             * ============================================================
             * 🔹 ODCZYT NAGŁÓWKÓW ODPOWIEDZI
             * ============================================================
             * getHeaderFields() – zwraca WSZYSTKIE nagłówki jako
             * Map<String, List<String>> (jeden nagłówek może mieć wiele
             * wartości, stąd lista). Klucz null w tej mapie to specjalny
             * wpis zawierający linię statusu (np. "HTTP/1.1 200 OK").
             *
             * getHeaderField(name) – wygodny skrót do odczytu JEDNEJ
             * (pierwszej) wartości konkretnego nagłówka.
             */

            System.out.println("\n=== Wszystkie naglowki odpowiedzi ===");
            Map<String, List<String>> headers = connection.getHeaderFields();
            headers.forEach((name, values) ->
                    System.out.println((name == null ? "(status)" : name) + " = " + values));

            System.out.println("\n=== Pojedyncze naglowki przez getHeaderField ===");
            System.out.println("Content-Type: " + connection.getHeaderField("Content-Type"));
            System.out.println("X-Lekcja:     " + connection.getHeaderField("X-Lekcja"));
            System.out.println("Content-Length: " + connection.getHeaderField("Content-Length"));

            /*
             * ============================================================
             * 📋 ODCZYT CIALA ODPOWIEDZI – getInputStream()
             * ============================================================
             * getInputStream() zwraca strumień bajtów z treścią odpowiedzi.
             * Warto opakować go w BufferedReader, żeby wygodnie czytać
             * tekst linia po linii (patrz Lesson03_BufferedReaderWriter
             * z rozdzialu _04_io).
             */

            System.out.println("\n=== Cialo odpowiedzi (getInputStream) ===");
            StringBuilder body = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }
            System.out.println("Odebrana tresc: " + body);

        } finally {
            /*
             * ============================================================
             * ⚠️ ZAWSZE ZATRZYMUJ SERWER PO SOBIE!
             * ============================================================
             * stop(0) – zamyka serwer natychmiast (0 sekund oczekiwania
             * na dokonczenie biezacych polaczen). W bloku finally, zeby
             * serwer zostal zatrzymany nawet jesli powyzej wystapi wyjatek.
             */
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - URLConnection reprezentuje polaczenie z zasobem wskazanym
         *   przez URL – powstaje przez url.openConnection()
         * - openConnection() NIE laczy sie od razu – polaczenie jest
         *   "leniwe" (lazy) – nastepuje dopiero przy odczycie danych
         * - getHeaderFields() -> wszystkie naglowki (Map<String, List<String>>)
         * - getHeaderField(name) -> wartosc jednego naglowka
         * - getInputStream() -> strumien z cialem odpowiedzi
         * - dla http/https zwrocony obiekt to w rzeczywistosci
         *   HttpURLConnection (podtyp URLConnection) – patrz Lesson10
         * - w tej lekcji uzylismy lokalnego com.sun.net.httpserver.HttpServer
         *   (port 0 = system wybiera wolny port) – to samo API dziala
         *   identycznie z prawdziwym serwerem w internecie
         */
    }
}
