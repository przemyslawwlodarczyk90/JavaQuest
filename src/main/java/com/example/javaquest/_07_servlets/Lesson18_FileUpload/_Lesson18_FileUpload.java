package com.example.javaquest._07_servlets.Lesson18_FileUpload;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class _Lesson18_FileUpload {

    private static final String BOUNDARY = "----JavaQuestBoundary";

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 UPLOAD PLIKOW – multipart/form-data I Part
         * ============================================================
         * Zwykle zadanie POST wysyla dane jako jeden "plaski" strumien
         * bajtow (np. application/json albo application/x-www-form-urlencoded).
         * Kiedy formularz musi wyslac PLIK (razem z ewentualnymi innymi
         * polami tekstowymi), uzywa sie typu tresci multipart/form-data –
         * cialo zadania jest podzielone na CZESCI (parts), kazda oddzielona
         * "granica" (boundary), z wlasnymi naglowkami (Content-Disposition,
         * Content-Type) i wlasna zawartoscia.
         *
         * Servlet API reprezentuje kazda taka czesc jako obiekt
         * jakarta.servlet.http.Part, dostepny przez:
         *   request.getPart("nazwaCzesci")   - jedna, konkretna czesc
         *   request.getParts()                - wszystkie czesci
         *
         * Part udostepnia m.in. getInputStream() (zawartosc czesci),
         * getSubmittedFileName() (oryginalna nazwa pliku wyslana przez
         * klienta) i getSize().
         */

        /*
         * ============================================================
         * 🔹 WYMOG: MultipartConfigElement NA SERWLECIE
         * ============================================================
         * Zeby request.getPart(...)/getParts() w ogole zadzialaly (a nie
         * rzucily wyjatkiem), serwlet MUSI miec jawnie skonfigurowana
         * obsluge multipart. W klasycznym web.xml/adnotacji zrobilaby to
         * adnotacja @MultipartConfig na klasie serwletu. W naszym
         * embedded Tomcacie, bez struktury webapp (patrz caveat w
         * Lesson16), robimy to programowo na obiekcie Wrapper zwracanym
         * przez Tomcat.addServlet(...):
         *
         *   Wrapper wrapper = Tomcat.addServlet(context, "nazwa", new MojServlet());
         *   wrapper.setMultipartConfigElement(
         *           new MultipartConfigElement(katalogTymczasowy));
         *
         * ⚠️ To trzeba zrobic PRZED tomcat.start() – konfiguracja
         * multipart jest odczytywana przy przetwarzaniu zadania, ale
         * ustawienie jej na Wrapperze przed startem to najprostszy,
         * pewny sposob, zeby na pewno bylo gotowe zanim przyjdzie
         * pierwsze zadanie.
         *
         * MultipartConfigElement(String location) – "location" to
         * katalog, w ktorym serwer moze tymczasowo zapisywac wieksze
         * czesci pliku (bufor na dysku) – uzywamy tego samego katalogu
         * tymczasowego, co baseDir Tomcata.
         */

        /*
         * ============================================================
         * 🔍 KLIENT: HttpClient NIE MA WBUDOWANEGO BUILDERA multipart
         * ============================================================
         * java.net.http.HttpClient (w odroznieniu od niektorych bibliotek
         * HTTP wyzszego poziomu) nie oferuje gotowej metody typu
         * ".multipart(...)" – cialo multipart/form-data trzeba zbudowac
         * RECZNIE, jako tablice bajtow o dokladnie takiej strukturze,
         * jakiej oczekuje serwer:
         *
         *   --<boundary>\r\n
         *   Content-Disposition: form-data; name="file"; filename="test.txt"\r\n
         *   Content-Type: text/plain\r\n
         *   \r\n
         *   <bajty pliku>\r\n
         *   --<boundary>--\r\n
         *
         * Robimy to w prywatnej metodzie pomocniczej buildMultipartBody(...)
         * (ponizej), zeby main() zostal czytelny. Naglowek zadania musi
         * dokladnie odpowiadac uzytej granicy:
         *
         *   Content-Type: multipart/form-data; boundary=<boundary>
         */

        System.out.println("=== Lekcja 18: Upload plikow (multipart/form-data, Part) ===\n");

        String tempDir = Files.createTempDirectory("lesson18").toString();

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tempDir);
        tomcat.getConnector().setPort(0); // port 0 = system wybiera wolny port

        Context context = tomcat.addContext("", null);
        Wrapper wrapper = Tomcat.addServlet(context, "uploadServlet", new UploadServlet());
        // KLUCZOWE - musi byc ustawione PRZED tomcat.start().
        wrapper.setMultipartConfigElement(new MultipartConfigElement(tempDir));
        context.addServletMappingDecoded("/upload", "uploadServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Osadzony Tomcat wystartowal na porcie: " + port);

            byte[] fileContent = "To jest zawartosc testowego pliku tekstowego.".getBytes(StandardCharsets.UTF_8);
            byte[] multipartBody = buildMultipartBody("file", "test.txt", "text/plain", fileContent);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                    .build();

            System.out.println("\n--- Wysylam POST /upload z plikiem test.txt (" + fileContent.length + " bajtow) ---");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Odpowiedz serwera ---");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 18 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Upload plikow = zadanie z naglowkiem Content-Type:
         *   multipart/form-data, w ktorym cialo jest podzielone na
         *   "czesci" (parts) oddzielone granica (boundary).
         * - Servlet API: request.getPart("nazwa") / request.getParts(),
         *   kazda czesc to obiekt Part (getInputStream(), getSubmittedFileName(),
         *   getSize()).
         * - Serwlet MUSI miec skonfigurowany MultipartConfigElement -
         *   inaczej PRZED tomcat.start() trzeba to zrobic na Wrapperze
         *   zwroconym przez Tomcat.addServlet(...):
         *   wrapper.setMultipartConfigElement(new MultipartConfigElement(katalogTymczasowy)).
         * - HttpClient nie ma wbudowanego wsparcia dla multipart - cialo
         *   trzeba zbudowac recznie jako bajty (boundary + naglowki czesci
         *   + zawartosc + zamykajaca granica) i wyslac przez
         *   HttpRequest.BodyPublishers.ofByteArray(...).
         * - W tej lekcji round-trip zostal zweryfikowany naprawde -
         *   serwlet odczytuje realna nazwe pliku i realne bajty wyslane
         *   przez klienta i odsyla je z powrotem w odpowiedzi.
         */
    }

    /**
     * Buduje cialo zadania multipart/form-data z JEDNA czescia typu plik.
     * Trzyma logike budowania bajtow poza main(), zeby nie zasmiecac
     * glownego toku demonstracji.
     */
    private static byte[] buildMultipartBody(String fieldName, String fileName, String contentType, byte[] fileBytes)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(("--" + BOUNDARY + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"\r\n")
                .getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Type: " + contentType + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(fileBytes);
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8));

        return out.toByteArray();
    }

    /**
     * Serwlet odczytujacy przeslany plik przez Part i odsylajacy z
     * powrotem jego nazwe oraz zawartosc - to dowod, ze dane realnie
     * "przejechaly" przez cale zadanie HTTP.
     */
    static class UploadServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            try {
                Part filePart = req.getPart("file");
                String submittedFileName = filePart.getSubmittedFileName();
                byte[] content = filePart.getInputStream().readAllBytes();
                String contentAsText = new String(content, StandardCharsets.UTF_8);

                System.out.println("[UploadServlet] odebrano plik: " + submittedFileName
                        + " (" + content.length + " bajtow)");

                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("Odebrano plik '" + submittedFileName + "' ("
                        + content.length + " bajtow). Zawartosc: " + contentAsText);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Blad przetwarzania uploadu: " + e.getMessage());
            }
        }
    }
}
