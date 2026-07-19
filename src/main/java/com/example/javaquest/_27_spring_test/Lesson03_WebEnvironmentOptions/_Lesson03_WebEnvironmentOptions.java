package com.example.javaquest._27_spring_test.Lesson03_WebEnvironmentOptions;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

public class _Lesson03_WebEnvironmentOptions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: @SpringBootTest - opcje webEnvironment ===");

        /*
         * ============================================================
         * 📦 4 WARTOSCI `webEnvironment` - CZY (i JAK) stawiac PRAWDZIWY serwer
         * ============================================================
         * - `MOCK` (DOMYSLNA) - kontekst Web BEZ prawdziwego serwera
         *   HTTP - zadania SYMULOWANE (`MockMvc` - Lesson05), NIE
         *   PRZECHODZA PRZEZ prawdziwy port TCP - NAJSZYBSZE.
         * - `RANDOM_PORT` - PRAWDZIWY embedded Tomcat NA LOSOWYM,
         *   WOLNYM porcie (`@LocalServerPort` DAJE numer) - test
         *   MOZE uzyc `TestRestTemplate`/`HttpClient` DO PRAWDZIWYCH
         *   zadan HTTP (Lesson12).
         * - `DEFINED_PORT` - PRAWDZIWY serwer NA porcie Z
         *   `application.properties` (RYZYKO konfliktu - RZADKO
         *   uzywane W testach).
         * - `NONE` - BRAK jakiegokolwiek Weba (NAWET `MockMvc`) -
         *   DLA aplikacji/testow BEZ warstwy HTTP (np. TYLKO
         *   logika/baza).
         */
        System.out.println("MOCK=symulowany Web (bez portu). RANDOM_PORT=PRAWDZIWY serwer NA losowym porcie. DEFINED_PORT=staly port. NONE=brak Weba.");

        runAndReport(RandomPortTest.class);
        runAndReport(NoWebTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@SpringBootTest(webEnvironment = RANDOM_PORT)` +
         *   `@LocalServerPort int port` - PRAWDZIWY serwer, PRAWDZIWY
         *   port, ZERO ryzyka konfliktu (system wybiera WOLNY - TA
         *   SAMA zasada "port 0" co W CALYM kursie).
         * - `TestRestTemplate` - GOTOWY klient HTTP AUTOMATYCZNIE
         *   skonfigurowany NA `localhost:<port>` (Lesson12 pokaze
         *   WIECEJ).
         * - `NONE` - GDY test NIE POTRZEBUJE Weba WCALE - SZYBSZY
         *   start (BRAK inicjalizacji Tomcata/DispatcherServlet).
         * - WYBOR `webEnvironment` TO KOMPROMIS: `MOCK` (SZYBKI, ALE
         *   NIE testuje PRAWDZIWEGO stosu sieciowego) vs
         *   `RANDOM_PORT` (WOLNIEJSZY, ALE REALISTYCZNY - PRAWDZIWE
         *   zadanie HTTP PRZEZ PRAWDZIWY port).
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    @RestController
    static class PingController {
        @GetMapping("/ping")
        String ping() {
            return "pong";
        }
    }

    @SpringBootApplication
    static class WebApp {
    }

    @SpringBootTest(classes = WebApp.class, webEnvironment = RANDOM_PORT)
    static class RandomPortTest {
        @LocalServerPort
        int port;

        @Autowired
        TestRestTemplate restTemplate;

        @Test
        void randomPortStartsRealServerReachableByRealHttpClient() {
            assertThat(port).isGreaterThan(0);
            String response = restTemplate.getForObject("http://localhost:" + port + "/ping", String.class);
            assertThat(response).isEqualTo("pong");
        }
    }

    @SpringBootApplication
    static class NoWebApp {
    }

    @SpringBootTest(classes = NoWebApp.class, webEnvironment = NONE)
    static class NoWebTest {
        @Autowired
        ApplicationContext context;

        @Test
        void noWebEnvironmentSkipsWebInfrastructure() {
            assertThat(context.getEnvironment().getProperty("server.port")).isNull();
            assertThat(context).isNotNull();
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
