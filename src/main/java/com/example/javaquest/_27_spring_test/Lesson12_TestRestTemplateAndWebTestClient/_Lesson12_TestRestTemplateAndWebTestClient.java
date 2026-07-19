package com.example.javaquest._27_spring_test.Lesson12_TestRestTemplateAndWebTestClient;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

public class _Lesson12_TestRestTemplateAndWebTestClient {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: TestRestTemplate i WebTestClient ===");

        /*
         * ============================================================
         * 📦 2 GOTOWE klienty HTTP DO testow Z PRAWDZIWYM serwerem (RANDOM_PORT, Lesson03)
         * ============================================================
         * - `TestRestTemplate` - OPARTY NA starszym `RestTemplate`
         *   (`_22_spring_web/Lesson17` - "tryb utrzymaniowy"), ALE
         *   DALEJ SZEROKO uzywany W testach Boota - PROSTY,
         *   SYNCHRONICZNY, GOTOWY "z pudelka" W `@SpringBootTest
         *   (webEnvironment = RANDOM_PORT)`.
         * - `WebTestClient` - OPARTY NA reaktywnym `WebClient`
         *   (`_22_spring_web/Lesson17`+`_29_spring_reactive`), ALE
         *   MOZE testowac ZWYKLE (NIE-reaktywne) API Spring MVC TEZ -
         *   DAJE NOWOCZESNE, fluentne API (`.get().uri(...).exchange()
         *   .expectStatus().isOk()`).
         *
         * 🔍 WYMAGA `@AutoConfigureWebTestClient` (WebTestClient NIE
         * jest DOMYSLNIE auto-konfigurowany, W ODROZNIENIU OD
         * `TestRestTemplate`, KTORY jest).
         */
        System.out.println("TestRestTemplate = PROSTY, synchroniczny klient (auto-konfigurowany). WebTestClient = NOWOCZESNE, fluentne API (wymaga @AutoConfigureWebTestClient).");

        runAndReport(BothClientsTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `TestRestTemplate.getForObject(url, Klasa.class)` -
         *   PROSTE, ZWIEZLE API.
         * - `TestRestTemplate.getForEntity(url, Klasa.class)` - PELNA
         *   `ResponseEntity` (status+naglowki+body).
         * - `WebTestClient.get().uri(...).exchange().expectStatus()
         *   .isOk().expectBody(Klasa.class).isEqualTo(...)` -
         *   fluentne, LANCUCHOWE API.
         * - REKOMENDACJA: `TestRestTemplate` DLA prostych, SZYBKICH
         *   testow REST (`_18_rest_api`/`_22_spring_web`);
         *   `WebTestClient` GDY test dotyka API reaktywnego
         *   (`_29_spring_reactive`) LUB PREFERUJESZ fluentny styl.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    @RestController
    static class ProductController {
        @GetMapping("/products/{id}")
        Map<String, Object> getProduct(@PathVariable String id) {
            return Map.of("id", id, "name", "Klawiatura", "price", 199.99);
        }
    }

    @SpringBootApplication
    static class TestApp {
    }

    @SpringBootTest(classes = TestApp.class, webEnvironment = RANDOM_PORT)
    @AutoConfigureWebTestClient
    static class BothClientsTest {
        @LocalServerPort
        int port;

        @Autowired
        TestRestTemplate testRestTemplate;

        @Autowired
        WebTestClient webTestClient;

        @Test
        void testRestTemplateFetchesRealResponseFromRandomPortServer() {
            @SuppressWarnings("unchecked")
            Map<String, Object> body = testRestTemplate.getForObject("http://localhost:" + port + "/products/P1", Map.class);

            assertThat(body).containsEntry("id", "P1").containsEntry("name", "Klawiatura");
            System.out.println("TestRestTemplate GET /products/P1 -> " + body);
        }

        @Test
        void webTestClientFetchesSameEndpointWithFluentApi() {
            webTestClient.get().uri("/products/P2")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo("P2")
                    .jsonPath("$.name").isEqualTo("Klawiatura");

            System.out.println("WebTestClient GET /products/P2 -> status OK, JSON pola id/name zweryfikowane fluentnym API.");
        }
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
