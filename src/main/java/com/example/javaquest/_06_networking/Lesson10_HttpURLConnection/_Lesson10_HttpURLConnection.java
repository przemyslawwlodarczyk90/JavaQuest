package com.example.javaquest._06_networking.Lesson10_HttpURLConnection;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class _Lesson10_HttpURLConnection {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST HttpURLConnection?
         * ============================================================
         * HttpURLConnection to podklasa URLConnection (Lesson09)
         * SPECJALIZOWANA pod protokół HTTP/HTTPS. Dodaje rzeczy typowe
         * dla HTTP, których nie ma w ogólnym URLConnection:
         * - setRequestMethod("GET"/"POST"/"PUT"/"DELETE"...)
         * - getResponseCode() / getResponseMessage() – kod i opis statusu
         * - setDoOutput(true) – zezwolenie na wysłanie ciała żądania
         *   (np. przy POST)
         *
         * Gdy wywołujemy url.openConnection() na adresie http/https,
         * zwrócony obiekt JEST już HttpURLConnection – wystarczy go
         * zrzutować.
         */

        System.out.println("=== Uruchamianie lokalnego serwera HTTP ===");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        server.createContext("/echo", exchange -> {
            String method = exchange.getRequestMethod();

            if ("GET".equalsIgnoreCase(method)) {
                /*
                 * GET -> zwracamy prosta powitalna wiadomosc.
                 */
                String responseBody = "Witaj! To jest odpowiedz na GET.";
                byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream out = exchange.getResponseBody()) {
                    out.write(bytes);
                }

            } else if ("POST".equalsIgnoreCase(method)) {
                /*
                 * POST -> odczytujemy cialo zadania i odsylamy je z powrotem
                 * (echo), z kodem 201 Created.
                 */
                String requestBody;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    requestBody = sb.toString();
                }

                String responseBody = "Otrzymano (echo): " + requestBody;
                byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(201, bytes.length); // 201 Created
                try (OutputStream out = exchange.getResponseBody()) {
                    out.write(bytes);
                }

            } else {
                // Metoda nieobslugiwana w tym demo
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
                exchange.close();
            }
        });

        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartowal na porcie: " + port);

        try {
            /*
             * ============================================================
             * 🔹 ŻĄDANIE GET
             * ============================================================
             * Domyślną metodą HttpURLConnection jest GET – nie trzeba jej
             * jawnie ustawiać, ale robimy to dla czytelności.
             */

            System.out.println("\n=== Zadanie GET ===");
            URI getUri = new URI("http://localhost:" + port + "/echo");
            HttpURLConnection getConnection = (HttpURLConnection) getUri.toURL().openConnection();
            getConnection.setRequestMethod("GET");

            int getResponseCode = getConnection.getResponseCode();
            System.out.println("Kod odpowiedzi: " + getResponseCode + " (" + getConnection.getResponseMessage() + ")");

            String getBody = readBody(getConnection);
            System.out.println("Cialo odpowiedzi: " + getBody);
            getConnection.disconnect();

            /*
             * ============================================================
             * 🔍 ŻĄDANIE POST – WYSYŁANIE CIAŁA ŻĄDANIA
             * ============================================================
             * Aby wysłać dane w żądaniu (np. POST/PUT), trzeba:
             * 1) setRequestMethod("POST")
             * 2) setDoOutput(true) – domyślnie false, bo GET nie wysyła
             *    ciała; to WŁĄCZA możliwość zapisu do getOutputStream()
             * 3) zapisać bajty do getOutputStream()
             * 4) DOPIERO teraz odczytać odpowiedź (getResponseCode(),
             *    getInputStream()) – to wyzwala faktyczne wysłanie żądania
             */

            System.out.println("\n=== Zadanie POST ===");
            HttpURLConnection postConnection = (HttpURLConnection) getUri.toURL().openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            postConnection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

            String requestPayload = "Pozdrowienia z klienta HttpURLConnection!";
            byte[] payloadBytes = requestPayload.getBytes(StandardCharsets.UTF_8);
            try (OutputStream out = postConnection.getOutputStream()) {
                out.write(payloadBytes);
            }

            int postResponseCode = postConnection.getResponseCode();
            System.out.println("Kod odpowiedzi: " + postResponseCode + " (" + postConnection.getResponseMessage() + ")");
            // Oczekiwane: 201 (Created) - tak ustawilismy w handlerze dla POST

            String postBody = readBody(postConnection);
            System.out.println("Cialo odpowiedzi: " + postBody);
            postConnection.disconnect();

        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HttpURLConnection = URLConnection wyspecjalizowany pod HTTP
         * - setRequestMethod("GET"/"POST"/...) – ustawia metodę HTTP
         * - getResponseCode() / getResponseMessage() – kod statusu i opis
         * - GET: nie wymaga setDoOutput – domyslnie nie wysylamy ciala
         * - POST/PUT: wymaga setDoOutput(true) + zapisu bajtow do
         *   getOutputStream() PRZED odczytem odpowiedzi
         * - warto jawnie wywolac disconnect() po zakonczeniu pracy,
         *   zeby zwolnic zasoby polaczenia (np. gniazdo z puli keep-alive)
         * - lokalny com.sun.net.httpserver.HttpServer (port 0) pozwolil
         *   przetestowac to samo API bez zaleznosci od zewnetrznej sieci
         */
    }

    /**
     * Odczytuje cale cialo odpowiedzi jako String, korzystajac z
     * odpowiedniego strumienia w zaleznosci od kodu statusu:
     * - getInputStream() dla odpowiedzi "sukcesu" (2xx, 3xx)
     * - getErrorStream() dla odpowiedzi bledow (4xx, 5xx) - InputStream
     *   w takim przypadku rzucilby wyjatek IOException!
     */
    private static String readBody(HttpURLConnection connection) throws Exception {
        var stream = connection.getResponseCode() >= 400
                ? connection.getErrorStream()
                : connection.getInputStream();

        if (stream == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
