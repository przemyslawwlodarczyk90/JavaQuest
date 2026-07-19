package com.example.javaquest._26_integration_testing.Lesson01_WhatIsIntegrationTesting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sun.net.httpserver.HttpServer;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson01_WhatIsIntegrationTesting {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: Czym sa testy integracyjne? ===");

        /*
         * ============================================================
         * 📦 UNIT vs INTEGRATION - GRANICA TESTU jest RÓŻNA
         * ============================================================
         * Caly rozdzial `_25_unit_testing` uczyl testowania JEDNEJ
         * jednostki kodu W IZOLACJI - zaleznosci (baza/siec/plik) byly
         * ZAWSZE zastapione ATRAPA (mock/fake/stub - Lesson16 tamtego
         * rozdzialu). Test INTEGRACYJNY ROBI ODWROTNIE: SWIADOMIE
         * pozwala WSPOLPRACOWAC prawdziwym komponentom (np. Twojemu
         * kodowi + PRAWDZIWEJ bazie danych, PRAWDZIWEMU systemowi
         * plikow, PRAWDZIWEMU serwerowi HTTP) - SPRAWDZA, CZY te
         * elementy DZIALAJA POPRAWNIE RAZEM, nie TYLKO osobno.
         *
         * 🔍 3 przyklady PONIZEJ (baza H2 w trybie plikowym, PRAWDZIWY
         * plik na dysku, PRAWDZIWY serwer HTTP na `localhost`)
         * ilustruja, CO ODROZNIA test integracyjny OD jednostkowego -
         * KAZDY Z NICH FAKTYCZNIE przekracza granice procesu/systemu
         * plikow, ZAMIAST zostawac WYLACZNIE W pamieci JVM.
         */
        System.out.println("Unit test = IZOLACJA (atrapy). Integration test = PRAWDZIWA wspolpraca komponentow (baza/plik/siec).");

        demonstrateDatabaseIntegration();
        demonstrateFileSystemIntegration();
        demonstrateHttpIntegration();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Test JEDNOSTKOWY (`_25_unit_testing`) - SZYBKI, IZOLOWANY,
         *   BEZ zewnetrznych zaleznosci - sprawdza LOGIKE.
         * - Test INTEGRACYJNY - WOLNIEJSZY, uzywa PRAWDZIWYCH
         *   zaleznosci (baza/plik/siec/kolejka) - sprawdza, ZE
         *   KOMPONENTY WSPOLPRACUJA (konfiguracja, mapowanie,
         *   protokol, format danych).
         * - "Piramida testow" (zapowiedz Lesson15/`_27_spring_test`) -
         *   WIELE testow jednostkowych (podstawa), MNIEJ integracyjnych
         *   (srodek), NAJMNIEJ end-to-end (szczyt) - bo KAZDY poziom
         *   W GORE jest WOLNIEJSZY I DROZSZY W utrzymaniu.
         * - Ten rozdzial UCZY narzedzi/technik SPECYFICZNYCH DLA testow
         *   integracyjnych: Testcontainers (Lesson04-06), WireMock
         *   (Lesson07-08), zarzadzanie danymi testowymi (Lesson10),
         *   izolacja (Lesson11), flaky testy (Lesson12).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateDatabaseIntegration() throws Exception {
        System.out.println("\n--- Przyklad 1: integracja Z PRAWDZIWA baza H2 (in-memory, ale PRAWDZIWY silnik SQL) ---");
        // W ODROZNIENIU od mockowanego repozytorium (`_25_unit_testing/Lesson13`), TU laczymy sie
        // Z PRAWDZIWYM silnikiem bazodanowym H2 - zapytania SQL SA FAKTYCZNIE wykonywane.
        String jdbcUrl = "jdbc:h2:mem:lesson01;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "");
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE products (id INT PRIMARY KEY, name VARCHAR(100))");
            statement.execute("INSERT INTO products VALUES (1, 'Klawiatura')");

            try (ResultSet resultSet = statement.executeQuery("SELECT name FROM products WHERE id = 1")) {
                resultSet.next();
                String name = resultSet.getString("name");
                assertThat(name).isEqualTo("Klawiatura");
                System.out.println("Odczytano z PRAWDZIWEJ bazy: " + name + " (test integracyjny, NIE mock)");
            }
        }
    }

    private static void demonstrateFileSystemIntegration() throws IOException {
        System.out.println("\n--- Przyklad 2: integracja Z PRAWDZIWYM systemem plikow ---");
        // W ODROZNIENIU od testu jednostkowego logiki formatujacej String, TU FAKTYCZNIE
        // zapisujemy i czytamy PRAWDZIWY plik z dysku - integracja Z systemem operacyjnym.
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("lesson01-integration", ".txt");
        try {
            java.nio.file.Files.writeString(tempFile, "raport-dzienny");
            String content = java.nio.file.Files.readString(tempFile);
            assertThat(content).isEqualTo("raport-dzienny");
            System.out.println("Odczytano z PRAWDZIWEGO pliku: " + tempFile.getFileName() + " -> \"" + content + "\"");
        } finally {
            java.nio.file.Files.deleteIfExists(tempFile);
        }
    }

    private static void demonstrateHttpIntegration() throws Exception {
        System.out.println("\n--- Przyklad 3: integracja Z PRAWDZIWYM serwerem HTTP (embedded, port 0) ---");
        // W ODROZNIENIU od zamockowanego klienta HTTP, TU stawiamy PRAWDZIWY serwer
        // (com.sun.net.httpserver.HttpServer - ten sam wzorzec co _18_rest_api) i wysylamy
        // DO NIEGO PRAWDZIWE zadanie przez java.net.http.HttpClient.
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/status", exchange -> {
            byte[] response = "{\"status\":\"UP\"}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream body = exchange.getResponseBody()) {
                body.write(response);
            }
        });
        server.start();
        try {
            int port = server.getAddress().getPort();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/status")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.body()).contains("UP");
            System.out.println("Odpowiedz z PRAWDZIWEGO serwera HTTP (port " + port + "): " + response.body());
        } finally {
            server.stop(0);
        }
    }
}
