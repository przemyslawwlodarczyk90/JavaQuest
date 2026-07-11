package com.example.javaquest._22_spring_web.Lesson15_CorsInSpring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson15_CorsInSpring {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: CORS w Springu ===");

        /*
         * ============================================================
         * 📦 CORS PRZEZ Spring MVC - POGLEBIENIE _19_security_basics/Lesson09
         * ============================================================
         * `_19_security_basics/Lesson09_Cors` uczyl KONCEPCJI CORS
         * (Same-Origin Policy, preflight OPTIONS) NA surowym
         * `HttpServer`, gdzie naglowki `Access-Control-*` byly
         * ustawiane RECZNIE. Spring MVC daje TRZY GOTOWE mechanizmy:
         *
         * 1) `@CrossOrigin` NA metodzie/klasie - NAJPROSTSZE, TYLKO DLA
         *    JEDNEGO kontrolera/endpointu.
         * 2) `WebMvcConfigurer.addCorsMappings(...)` - GLOBALNA
         *    konfiguracja DLA CALEJ aplikacji, W JEDNYM miejscu.
         * 3) `CorsConfigurationSource` bean - NAJBARDZIEJ elastyczne,
         *    UZYWANE TEZ przez Spring Security (`_24_spring_security`).
         */
        System.out.println("Spring MVC automatyzuje CORS - preflight OPTIONS i naglowki Access-Control-* generowane SAME, bez recznego kodu.");

        demonstrateCrossOriginAnnotationOnMethod();
        demonstrateGlobalCorsConfiguration();
        demonstrateDisallowedOriginRejected();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@CrossOrigin(origins = "...")` NA metodzie/klasie - SZYBKIE,
         *   ale ROZPROSZONE (LATWO zapomniec NA innym kontrolerze).
         * - `WebMvcConfigurer.addCorsMappings(...)` - JEDNO miejsce DLA
         *   CALEJ aplikacji - ZALECANE DLA WIEKSZOSCI projektow.
         * - Spring SAM obsluguje preflight `OPTIONS` (generuje
         *   `Access-Control-Allow-Origin`/`-Methods`/`-Headers`) - NIE
         *   trzeba pisac WLASNEGO handlera `OPTIONS` (w odroznieniu od
         *   `_19_security_basics/Lesson09`).
         * - Origin SPOZA dozwolonej listy = odpowiedz BEZ naglowka
         *   `Access-Control-Allow-Origin` - przegladarka SAMA
         *   ZABLOKUJE dostep do odpowiedzi PO STRONIE klienta (serwer
         *   TECHNICZNIE zwraca 200, ale JS W przegladarce NIE ODCZYTA
         *   wyniku).
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    @RestController
    static class AnnotationCorsController {
        @CrossOrigin(origins = "https://frontend.example.com")
        @GetMapping("/api/data")
        String getData() {
            return "Dane API";
        }
    }

    @SpringBootApplication
    static class AnnotationCorsApp {
    }

    private static void demonstrateCrossOriginAnnotationOnMethod() throws Exception {
        System.out.println("\n=== @CrossOrigin NA METODZIE ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AnnotationCorsApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGetWithOrigin(port, "/api/data", "https://frontend.example.com");
            System.out.println("GET /api/data (Origin: https://frontend.example.com) -> status: " + response.statusCode() + ", Access-Control-Allow-Origin: " + response.headers().firstValue("Access-Control-Allow-Origin").orElse("BRAK"));
        } finally {
            context.close();
        }
    }

    @RestController
    static class GlobalCorsController {
        @GetMapping("/api/orders")
        String getOrders() {
            return "Lista zamowien";
        }
    }

    static class GlobalCorsWebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("https://frontend.example.com", "https://admin.example.com")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .maxAge(3600);
        }
    }

    @org.springframework.context.annotation.Configuration
    static class GlobalCorsConfig {
        @org.springframework.context.annotation.Bean
        WebMvcConfigurer globalCorsWebMvcConfigurer() {
            return new GlobalCorsWebConfig();
        }
    }

    @SpringBootApplication
    static class GlobalCorsApp {
    }

    private static void demonstrateGlobalCorsConfiguration() throws Exception {
        System.out.println("\n=== WebMvcConfigurer.addCorsMappings - GLOBALNA KONFIGURACJA ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(GlobalCorsApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> preflight = httpOptionsPreflightRequest(port, "/api/orders", "https://admin.example.com");
            System.out.println("OPTIONS /api/orders (preflight, Origin: https://admin.example.com) -> status: " + preflight.statusCode());
            System.out.println("Access-Control-Allow-Origin: " + preflight.headers().firstValue("Access-Control-Allow-Origin").orElse("BRAK"));
            System.out.println("Access-Control-Allow-Methods: " + preflight.headers().firstValue("Access-Control-Allow-Methods").orElse("BRAK"));
            System.out.println("-> Spring WYGENEROWAL odpowiedz na preflight OPTIONS AUTOMATYCZNIE, bez WLASNEGO kodu obslugi.");
        } finally {
            context.close();
        }
    }

    @RestController
    static class RestrictedCorsController {
        @GetMapping("/api/secure")
        String getSecureData() {
            return "Wrazliwe dane";
        }
    }

    static class RestrictedCorsWebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/secure")
                    .allowedOrigins("https://trusted.example.com");
        }
    }

    @org.springframework.context.annotation.Configuration
    static class RestrictedCorsConfig {
        @org.springframework.context.annotation.Bean
        WebMvcConfigurer restrictedCorsWebMvcConfigurer() {
            return new RestrictedCorsWebConfig();
        }
    }

    @SpringBootApplication
    static class RestrictedCorsApp {
    }

    private static void demonstrateDisallowedOriginRejected() throws Exception {
        System.out.println("\n=== ORIGIN SPOZA DOZWOLONEJ LISTY - BRAK naglowka Access-Control-Allow-Origin ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RestrictedCorsApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGetWithOrigin(port, "/api/secure", "https://zlosliwa-strona.example.com");
            System.out.println("GET /api/secure (Origin: https://zlosliwa-strona.example.com) -> status: " + response.statusCode() + " (serwer ODPOWIADA, ale...)");
            System.out.println("Access-Control-Allow-Origin: " + response.headers().firstValue("Access-Control-Allow-Origin").orElse("BRAK (przegladarka ZABLOKUJE odczyt odpowiedzi PO STRONIE klienta)"));
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGetWithOrigin(int port, String path, String origin) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Origin", origin)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpOptionsPreflightRequest(int port, String path, String origin) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Origin", origin)
                .header("Access-Control-Request-Method", "GET")
                .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
