package com.example.javaquest._22_spring_web.Lesson14_FileUpload;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class _Lesson14_FileUpload {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: Upload plikow ===");

        /*
         * ============================================================
         * 📦 UPLOAD PLIKOW PRZEZ Spring MVC - POGLEBIENIE _07_servlets/Lesson18
         * ============================================================
         * `_07_servlets/Lesson18_FileUpload` uczyl SUROWEGO Servlet API
         * (`MultipartConfigElement`, `request.getPart(...)`). Spring MVC
         * OPAKOWUJE to W PROSTSZY interfejs: `MultipartFile` jako
         * PARAMETR metody kontrolera, ZERO recznego parsowania
         * `multipart/form-data`.
         *
         * `java.net.http.HttpClient` (podobnie jak W `_07_servlets`) NIE
         * MA wbudowanego wsparcia dla `multipart/form-data` - klient W
         * tej lekcji BUDUJE tresc RECZNIE, bajt po bajcie (dokladnie
         * TAK SAMO jak w `_07_servlets/Lesson18`).
         */
        System.out.println("MultipartFile = parametr metody kontrolera reprezentujacy WGRANY plik - Spring MVC parsuje multipart/form-data ZA CIEBIE.");

        demonstrateSingleFileUpload();
        demonstrateMultipleFileUpload();
        demonstrateFileSizeLimitExceeded();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestParam("file") MultipartFile file` - JEDEN wgrany
         *   plik, Z metodami `getOriginalFilename()`/`getSize()`/
         *   `getBytes()`/`getContentType()`.
         * - `@RequestParam("files") List<MultipartFile> files` - WIELE
         *   plikow W JEDNYM zadaniu.
         * - Limit rozmiaru KONFIGUROWANY przez
         *   `spring.servlet.multipart.max-file-size` -
         *   PRZEKROCZENIE rzuca `MaxUploadSizeExceededException`
         *   (obsluzone przez `@RestControllerAdvice`, patrz Lesson09).
         * - W ODROZNIENIU od `_07_servlets/Lesson18`, TU nie trzeba
         *   RECZNIE wolac `MultipartConfigElement` NA `Wrapper` PRZED
         *   `tomcat.start()` - Spring Boot KONFIGURUJE to automatycznie
         *   przez `MultipartAutoConfiguration`.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    @RestController
    static class SingleUploadController {
        @PostMapping("/upload")
        String uploadSingle(@RequestParam("file") MultipartFile file) throws Exception {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            return "Otrzymano plik '" + file.getOriginalFilename() + "' (" + file.getSize() + " bajtow, typ: " + file.getContentType() + "), tresc: " + content;
        }
    }

    @SpringBootApplication
    static class SingleUploadApp {
    }

    private static void demonstrateSingleFileUpload() throws Exception {
        System.out.println("\n=== POJEDYNCZY PLIK - MultipartFile ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SingleUploadApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = uploadFile(port, "/upload", "notatka.txt", "Tresc przykladowej notatki.".getBytes(StandardCharsets.UTF_8));
            System.out.println("POST /upload (1 plik) -> status: " + response.statusCode() + ", body: " + response.body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class MultipleUploadController {
        @PostMapping("/upload-many")
        String uploadMany(@RequestParam("files") List<MultipartFile> files) {
            StringBuilder summary = new StringBuilder("Otrzymano " + files.size() + " plikow: ");
            for (MultipartFile file : files) {
                summary.append(file.getOriginalFilename()).append(" (").append(file.getSize()).append(" B) ");
            }
            return summary.toString();
        }
    }

    @SpringBootApplication
    static class MultipleUploadApp {
    }

    private static void demonstrateMultipleFileUpload() throws Exception {
        System.out.println("\n=== WIELE PLIKOW NARAZ - List<MultipartFile> ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MultipleUploadApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = uploadTwoFiles(port, "/upload-many");
            System.out.println("POST /upload-many (2 pliki) -> status: " + response.statusCode() + ", body: " + response.body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class LimitedUploadController {
        @PostMapping("/upload-limited")
        String uploadLimited(@RequestParam("file") MultipartFile file) {
            return "Przyjeto plik: " + file.getOriginalFilename();
        }
    }

    @RestControllerAdvice
    static class UploadExceptionAdvice {
        @ExceptionHandler(MaxUploadSizeExceededException.class)
        ResponseEntity<String> handleTooLarge(MaxUploadSizeExceededException ex) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Plik przekracza dozwolony limit rozmiaru");
        }
    }

    @SpringBootApplication
    static class LimitedUploadApp {
    }

    private static void demonstrateFileSizeLimitExceeded() throws Exception {
        System.out.println("\n=== LIMIT ROZMIARU PLIKU - MaxUploadSizeExceededException ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.servlet.multipart.max-file-size", "10KB");
        props.setProperty("spring.servlet.multipart.max-request-size", "10KB");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LimitedUploadApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            byte[] smallFile = "Maly plik.".getBytes(StandardCharsets.UTF_8);
            HttpResponse<String> okResponse = uploadFile(port, "/upload-limited", "maly.txt", smallFile);
            System.out.println("POST /upload-limited (maly plik, " + smallFile.length + " B) -> status: " + okResponse.statusCode());

            byte[] bigFile = new byte[20 * 1024];
            HttpResponse<String> tooLargeResponse = uploadFile(port, "/upload-limited", "duzy.bin", bigFile);
            System.out.println("POST /upload-limited (duzy plik, " + bigFile.length + " B, limit: 10KB) -> status: " + tooLargeResponse.statusCode() + " (oczekiwane: 413), body: " + tooLargeResponse.body());
        } finally {
            context.close();
        }
    }

    private static final String BOUNDARY = "JavaQuestBoundary";

    private static HttpResponse<String> uploadFile(int port, String path, String filename, byte[] content) throws Exception {
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        writeFilePart(body, "file", filename, content);
        writeClosingBoundary(body);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> uploadTwoFiles(int port, String path) throws Exception {
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        writeFilePart(body, "files", "raport1.txt", "Tresc raportu 1".getBytes(StandardCharsets.UTF_8));
        writeFilePart(body, "files", "raport2.txt", "Tresc raportu 2".getBytes(StandardCharsets.UTF_8));
        writeClosingBoundary(body);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void writeFilePart(ByteArrayOutputStream body, String fieldName, String filename, byte[] content) throws Exception {
        body.write(("--" + BOUNDARY + "\r\n").getBytes(StandardCharsets.UTF_8));
        body.write(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + filename + "\"\r\n").getBytes(StandardCharsets.UTF_8));
        body.write("Content-Type: text/plain\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        body.write(content);
        body.write("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    private static void writeClosingBoundary(ByteArrayOutputStream body) throws Exception {
        body.write(("--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8));
    }
}
