package com.example.javaquest._22_spring_web.Lesson11_ContentNegotiationAndMessageConverters;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class _Lesson11_ContentNegotiationAndMessageConverters {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: Negocjacja tresci i HttpMessageConverter ===");

        /*
         * ============================================================
         * 📦 JAK SPRING MVC WYBIERA FORMAT ODPOWIEDZI - POGLEBIENIE _18_rest_api/Lesson07
         * ============================================================
         * `_18_rest_api/Lesson07_ContentNegotiation` uczyl KONCEPCJI
         * (naglowki `Accept`/`Content-Type`) NA surowym `HttpServer`.
         * Tu pokazujemy MECHANIZM Springa: `HttpMessageConverter` to
         * "tlumacz" MIEDZY obiektem Javy A bajtami HTTP - Jackson
         * (`MappingJackson2HttpMessageConverter`) jest DOMYSLNY dla
         * JSON, ale MOZNA dodac WLASNE konwertery DLA innych formatow.
         *
         * `produces`/`consumes` NA `@GetMapping`/`@PostMapping`
         * OGRANICZAJA, KTORY format metoda OBSLUGUJE - jesli klient
         * zazada CZEGOS spoza tej listy (`Accept`), Spring MVC zwraca
         * 406 Not Acceptable; jesli WYSLE CIALO W NIEOBSLUGIWANYM
         * formacie (`Content-Type`), zwraca 415 Unsupported Media Type.
         */
        System.out.println("HttpMessageConverter = 'tlumacz' miedzy obiektem Javy a bajtami HTTP - Jackson jest DOMYSLNY dla JSON.");

        demonstrateProducesRestrictsAcceptableFormats();
        demonstrateCustomMessageConverterForCsv();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `HttpMessageConverter` = mechanizm "pod maska" `@RequestBody`/
         *   zwracanych obiektow - Jackson (JSON) jest zarejestrowany
         *   DOMYSLNIE, gdy jest na classpath (`_21_spring_boot/Lesson04`).
         * - `produces = "..."` NA metodzie = KTORE formaty ODPOWIEDZI sa
         *   OBSLUGIWANE - niedopasowany `Accept` = 406 Not Acceptable.
         * - `consumes = "..."` NA metodzie = KTORE formaty CIALA
         *   zadania sa OBSLUGIWANE - niedopasowany `Content-Type` = 415
         *   Unsupported Media Type.
         * - WLASNY `HttpMessageConverter` (dziedziczacy
         *   `AbstractHttpMessageConverter<T>`) ROZSZERZA Springa O NOWY
         *   format (tu: CSV) - zarejestrowany PRZEZ `WebMvcConfigurer`.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    record Product(String id, String name, double price) {
    }

    @RestController
    static class NegotiationController {
        @GetMapping(value = "/products/P1", produces = MediaType.APPLICATION_JSON_VALUE)
        Product getProduct() {
            return new Product("P1", "Klawiatura", 249.99);
        }
    }

    @SpringBootApplication
    static class NegotiationApp {
    }

    private static void demonstrateProducesRestrictsAcceptableFormats() throws Exception {
        System.out.println("\n=== produces OGRANICZA OBSLUGIWANE FORMATY - NIEDOPASOWANY Accept = 406 ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NegotiationApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> jsonResponse = httpGetWithAccept(port, "/products/P1", "application/json");
            System.out.println("GET /products/P1 (Accept: application/json) -> status: " + jsonResponse.statusCode() + ", body: " + jsonResponse.body());

            HttpResponse<String> xmlResponse = httpGetWithAccept(port, "/products/P1", "application/xml");
            System.out.println("GET /products/P1 (Accept: application/xml) -> status: " + xmlResponse.statusCode() + " (oczekiwane: 406, endpoint 'produces' TYLKO application/json)");
        } finally {
            context.close();
        }
    }

    // WLASNY HttpMessageConverter - uczy mechanizmu ROZSZERZANIA Springa o NOWY format (CSV).
    static class ProductCsvMessageConverter extends AbstractHttpMessageConverter<Product> {

        ProductCsvMessageConverter() {
            super(MediaType.valueOf("text/csv"));
        }

        @Override
        protected boolean supports(Class<?> clazz) {
            return Product.class.isAssignableFrom(clazz);
        }

        @Override
        protected Product readInternal(Class<? extends Product> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            String line = new String(inputMessage.getBody().readAllBytes(), StandardCharsets.UTF_8).trim();
            String[] parts = line.split(",");
            return new Product(parts[0], parts[1], Double.parseDouble(parts[2]));
        }

        @Override
        protected void writeInternal(Product product, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            String csv = product.id() + "," + product.name() + "," + product.price();
            outputMessage.getBody().write(csv.getBytes(StandardCharsets.UTF_8));
        }
    }

    @RestController
    static class CsvController {
        @GetMapping(value = "/products/P1", produces = "text/csv")
        Product getProductAsCsv() {
            return new Product("P1", "Klawiatura", 249.99);
        }
    }

    static class CsvWebConfig implements WebMvcConfigurer {
        @Override
        public void extendMessageConverters(List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
            // Dopisujemy WLASNY konwerter DO listy DOMYSLNYCH konwerterow Springa (Jackson JSON pozostaje).
            converters.add(new ProductCsvMessageConverter());
        }
    }

    @org.springframework.context.annotation.Configuration
    static class CsvConfig {
        @org.springframework.context.annotation.Bean
        WebMvcConfigurer webMvcConfigurer() {
            return new CsvWebConfig();
        }
    }

    @SpringBootApplication
    static class CsvApp {
    }

    private static void demonstrateCustomMessageConverterForCsv() throws Exception {
        System.out.println("\n=== WLASNY HttpMessageConverter - ROZSZERZENIE Springa O NOWY FORMAT (text/csv) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CsvApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> csvResponse = httpGetWithAccept(port, "/products/P1", "text/csv");
            System.out.println("GET /products/P1 (Accept: text/csv) -> status: " + csvResponse.statusCode() + ", Content-Type: " + csvResponse.headers().firstValue("Content-Type").orElse("BRAK") + ", body: " + csvResponse.body());
            System.out.println("-> Ten sam obiekt Product zostal zserializowany DO CSV, NIE JSON - dzieki WLASNEMU HttpMessageConverter.");
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGetWithAccept(int port, String path, String acceptHeader) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Accept", acceptHeader)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
