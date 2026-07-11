package com.example.javaquest._22_spring_web.Lesson17_HttpClientsRestTemplateWebClientRestClient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Properties;

public class _Lesson17_HttpClientsRestTemplateWebClientRestClient {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 17: RestTemplate vs WebClient vs RestClient ===");

        /*
         * ============================================================
         * 📦 EWOLUCJA KLIENTA HTTP W SPRINGU - "STARE VS NOWE" (zweryfikowane WebSearch 2026-07-11)
         * ============================================================
         * Kurs uzywa `java.net.http.HttpClient` (JDK) DO TESTOWANIA
         * wlasnych API W WIEKSZOSCI lekcji `_22_spring_web` - to
         * WYSTARCZA DO testow, ale W REALNYM kodzie aplikacji (KIEDY
         * TWOJ serwis MUSI wywolac INNY serwis) Spring oferuje WLASNE
         * klienty, KTORE EWOLUOWALY przez lata:
         *
         * 1) `RestTemplate` (Spring Framework 3.0, 2009) - NAJSTARSZY,
         *    SYNCHRONICZNY/blokujacy. Od pewnego czasu W "trybie
         *    utrzymaniowym" (BEZ nowych funkcji), ale WCIAZ POWSZECHNIE
         *    spotykany W ISTNIEJACYM kodzie - MUSISZ go ROZPOZNAWAC.
         * 2) `WebClient` (Spring Framework 5, 2017) - REAKTYWNY, czesc
         *    WebFlux. Moze dzialac TEZ synchronicznie (`.block()`), ale
         *    to "reaktywne narzedzie uzyte niereaktywnie" - narzut BEZ
         *    korzysci reaktywnosci W zwyklej aplikacji Spring MVC.
         * 3) `RestClient` (Spring Framework **6.1**, 2023, wsparcie W
         *    Boot **3.2**) - NOWY, SYNCHRONICZNY klient - plynne API
         *    JAK `WebClient`, ale BEZ narzutu reaktywnego -
         *    ZALECANY DOMYSLNY wybor DLA zwyklych, blokujacych aplikacji
         *    Spring MVC od Boot 3.2 wzwyz (projekt jest na Boot 3.4.4).
         */
        System.out.println("RestTemplate (2009, stary) -> WebClient (2017, reaktywny) -> RestClient (2023, Boot 3.2+, NOWY zalecany domyslny) - to samo zadanie, 3 rozne API.");

        demonstrateIdenticalRequestWithAllThreeClients();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `RestTemplate` - ROZPOZNAWAJ w STARYM kodzie, ale NIE
         *   uzywaj W NOWYM (brak nowych funkcji, docelowo zastapiony).
         * - `WebClient` - wybierz, GDY TWOJA aplikacja jest REAKTYWNA
         *   (Spring WebFlux, `Mono`/`Flux` W CALYM stosie) - W zwyklej
         *   aplikacji Spring MVC to NIEPOTRZEBNY narzut.
         * - `RestClient` - DOMYSLNY wybor DLA nowego kodu W zwyklej,
         *   SYNCHRONICZNEJ aplikacji Spring MVC (jak WIEKSZOSC projektow
         *   W tym kursie) - Boot 3.2+.
         * - WSZYSTKIE 3 klienty UZYWAJA tych samych
         *   `ClientHttpRequestFactory`/`HttpMessageConverter` "pod
         *   maska" (patrz Lesson11) - ROZNICA jest W STYLU API I
         *   MODELU wykonania (blokujacy vs reaktywny).
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    record Greeting(String message) {
    }

    @RestController
    static class GreetingController {
        @GetMapping("/greeting")
        Greeting greet() {
            return new Greeting("Witaj z JavaQuest!");
        }
    }

    @SpringBootApplication
    static class GreetingApp {
    }

    private static void demonstrateIdenticalRequestWithAllThreeClients() throws Exception {
        System.out.println("\n=== IDENTYCZNE ZADANIE GET /greeting - 3 RÓZNE klienty ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(GreetingApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String baseUrl = "http://localhost:" + port;

            // 1) RestTemplate (2009) - NAJSTARSZY, synchroniczny.
            RestTemplate restTemplate = new RestTemplate();
            Greeting fromRestTemplate = restTemplate.getForObject(baseUrl + "/greeting", Greeting.class);
            System.out.println("1) RestTemplate (2009, 'legacy', synchroniczny)      -> " + fromRestTemplate);

            // 2) WebClient (2017) - reaktywny, uzyty tu synchronicznie przez .block().
            WebClient webClient = WebClient.create(baseUrl);
            Greeting fromWebClient = webClient.get()
                    .uri("/greeting")
                    .retrieve()
                    .bodyToMono(Greeting.class)
                    .block();
            System.out.println("2) WebClient (2017, reaktywny, uzyty przez .block()) -> " + fromWebClient);

            // 3) RestClient (Spring Framework 6.1 / Boot 3.2, 2023) - NOWY, synchroniczny, zalecany.
            RestClient restClient = RestClient.create(baseUrl);
            Greeting fromRestClient = restClient.get()
                    .uri("/greeting")
                    .retrieve()
                    .body(Greeting.class);
            System.out.println("3) RestClient (2023, Boot 3.2+, NOWY ZALECANY)       -> " + fromRestClient);

            System.out.println("-> WSZYSTKIE 3 zwrocily TEN SAM wynik - roznica jest W STYLU API i modelu wykonania, NIE W REZULTACIE.");
        } finally {
            context.close();
        }
    }
}
