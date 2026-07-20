package com.example.javaquest._28_java_evolution.Lesson16_Java18UtfDefaultAndSimpleWebServer;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson16_Java18UtfDefaultAndSimpleWebServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== LEKCJA 16: Java 18 (marzec 2022) - UTF-8 domyslne kodowanie, Simple Web Server ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - 2 zmiany Javy 18
         * ============================================================
         * 1. JEP 400 - `UTF-8` jest TERAZ DOMYSLNYM kodowaniem znakow
         *    DLA CALEGO JDK (`Charset.defaultCharset()`, `new String(
         *    bytes)`, `Files.readString()` BEZ jawnego charsetu, itd.)
         *    NIEZALEZNIE OD platformy/locale systemu operacyjnego.
         *    PRZED Java 18, DOMYSLNY charset ZALEZAL OD systemu (NA
         *    Windows CZESTO byl to `windows-1252`/`Cp1250`, CO
         *    powodowalo BLEDY "dziala NA MOIM Linuxie, NIE dziala NA
         *    Windows kolegi").
         * 2. JEP 408 - `Simple Web Server` - PROSTY, statyczny
         *    serwer plikow WBUDOWANY W JDK (`jwebserver` Z linii
         *    komend, LUB API `com.sun.net.httpserver.SimpleFileServer`
         *    programowo) - WYGODNY DO szybkich demo/testow, BEZ
         *    zadnej zewnetrznej zaleznosci.
         *
         * 🔍 Powiazanie: `_04_io/Lesson12_Charset` uczyl OGOLNEJ
         * teorii kodowania znakow - TA lekcja pokazuje KONKRETNA
         * ZMIANE DOMYSLNEGO zachowania JDK WPROWADZONA W Javie 18.
         */
        System.out.println("Java 18 (marzec 2022): UTF-8 domyslnym kodowaniem CALEGO JDK (JEP 400), Simple Web Server (JEP 408).");

        demonstrateUtf8AsDefaultCharset();
        demonstrateSimpleWebServerProgrammatically();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Charset.defaultCharset()` ZWRACA `UTF-8` NA KAZDEJ
         *   platformie OD Javy 18 (BEZ zaleznosci OD locale systemu).
         * - PRZED Java 18: `new String(bytes)` BEZ jawnego charsetu
         *   MOGL dac ROZNY wynik NA Windows/Linux/macOS - NIEPRZEWIDYWALNE.
         * - `com.sun.net.httpserver.SimpleFileServer` - PROSTY
         *   serwer statycznych plikow, dostepny PROGRAMOWO (JAK
         *   uzyty JUZ W tej lekcji) LUB Z linii komend (`jwebserver`).
         * - Pelna teoria charsetow (kodowania wielobajtowe, konwersje):
         *   `_04_io/Lesson12_Charset`.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static void demonstrateUtf8AsDefaultCharset() throws IOException {
        System.out.println("\n--- UTF-8 jako domyslny charset CALEGO JDK (JEP 400) ---");
        Charset domyslny = Charset.defaultCharset();
        System.out.println("Charset.defaultCharset() = " + domyslny.name());
        System.out.println("OD Javy 18 JEST TO ZAWSZE 'UTF-8', NIEZALEZNIE OD systemu operacyjnego/locale - PRZED Java 18 na Windows CZESTO bylo to np. 'windows-1252'.");

        assertThat(domyslny).isEqualTo(StandardCharsets.UTF_8);

        Path plikTymczasowy = Files.createTempFile("lesson16-utf8", ".txt");
        try {
            String tekstZPolskimiZnakami = "Zazolc gesla jazn - UTF-8 obsluguje polskie znaki bez problemu.";
            Files.writeString(plikTymczasowy, tekstZPolskimiZnakami);

            byte[] surowe = Files.readAllBytes(plikTymczasowy);
            String odczytanyBezJawnegoCharsetu = new String(surowe);
            System.out.println("new String(bytes) BEZ jawnego charsetu -> zdekodowany JAKO: " + domyslny.name() + " (dzieki JEP 400, ZAWSZE spojnie).");

            assertThat(odczytanyBezJawnegoCharsetu).isEqualTo(tekstZPolskimiZnakami);
        } finally {
            Files.deleteIfExists(plikTymczasowy);
        }

        System.out.println("Pelna teoria charsetow/kodowan wielobajtowych: _04_io/Lesson12_Charset.");
    }

    private static void demonstrateSimpleWebServerProgrammatically() throws IOException, InterruptedException {
        System.out.println("\n--- Simple Web Server (JEP 408) - wbudowany serwer statycznych plikow ---");
        Path katalogStatyczny = Files.createTempDirectory("lesson16-webroot");
        try {
            Path plikIndex = katalogStatyczny.resolve("index.html");
            Files.writeString(plikIndex, "<html><body><h1>JavaQuest - Simple Web Server</h1></body></html>");

            HttpServer serwer = SimpleFileServer.createFileServer(
                    new InetSocketAddress("localhost", 0),
                    katalogStatyczny,
                    SimpleFileServer.OutputLevel.NONE);
            serwer.start();
            try {
                int port = serwer.getAddress().getPort();
                System.out.println("SimpleFileServer.createFileServer(...) uruchomiony NA porcie " + port + ", serwuje pliki Z: " + katalogStatyczny);
                System.out.println("Z linii komend TEN SAM efekt daje: 'jwebserver -p " + port + " -d " + katalogStatyczny + "' (BEZ pisania JAKIEGOKOLWIEK kodu Javy!).");

                HttpClient klient = HttpClient.newHttpClient();
                HttpRequest zadanie = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/index.html"))
                        .build();
                HttpResponse<String> odpowiedz = klient.send(zadanie, HttpResponse.BodyHandlers.ofString());

                System.out.println("GET /index.html -> kod=" + odpowiedz.statusCode() + ", cialo=\"" + odpowiedz.body() + "\"");
                assertThat(odpowiedz.statusCode()).isEqualTo(200);
                assertThat(odpowiedz.body()).contains("JavaQuest - Simple Web Server");
            } finally {
                serwer.stop(0);
            }
        } finally {
            Files.walk(katalogStatyczny)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(sciezka -> {
                        try {
                            Files.deleteIfExists(sciezka);
                        } catch (IOException ignored) {
                            // sprzatanie katalogu tymczasowego - blad pomijalny w demo
                        }
                    });
        }

        System.out.println("Zastosowanie: szybkie demo/testy STATYCZNYCH stron (np. wynikow `_18_rest_api/Lesson18` OpenAPI) BEZ instalowania Nginx/Apache.");
    }
}
