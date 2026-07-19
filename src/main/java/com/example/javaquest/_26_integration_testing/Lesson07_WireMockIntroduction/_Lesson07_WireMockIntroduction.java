package com.example.javaquest._26_integration_testing.Lesson07_WireMockIntroduction;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson07_WireMockIntroduction {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: WireMock - stubowanie zewnetrznych API HTTP ===");

        /*
         * ============================================================
         * 📦 PROBLEM: jak testowac kod ZALEZNY OD zewnetrznego API, KTOREGO NIE KONTROLUJESZ?
         * ============================================================
         * Testcontainers (Lesson04-06) rozwiazuje problem "PRAWDZIWEJ
         * bazy danych". A CO Z zewnetrznym, PLATNYM/limitowanym API
         * pogody, kursow walut, dostawcy platnosci? NIE MOZESZ
         * uruchomic GO W kontenerze Docker (to CUDZY serwis W
         * chmurze). WireMock ROZWIAZUJE TO INACZEJ: STAWIA WLASNY,
         * embedowany serwer HTTP, KTORY UDAJE zewnetrzne API -
         * TWOJ kod LACZY sie Z `localhost`, ALE MYSLI, ze rozmawia Z
         * PRAWDZIWYM zewnetrznym serwisem.
         *
         * 🔍 ROZNICA WZGLEDEM Mockito (`_25_unit_testing/Lesson13`):
         * Mockito mockuje OBIEKT JAVY (interfejs klienta HTTP).
         * WireMock mockuje SAM PROTOKOL HTTP (prawdziwe zadanie/
         * odpowiedz PRZEZ SIEC/localhost) - test SPRAWDZA WIEC TEZ,
         * CZY Twoj klient HTTP POPRAWNIE BUDUJE zadanie (naglowki,
         * URL, body), CO Mockito W OGOLE nie weryfikuje.
         */
        System.out.println("WireMock = PRAWDZIWY embedded serwer HTTP UDAJACY zewnetrzne API. Rozniej od Mockito - testuje CALY protokol HTTP, nie tylko obiekt Javy.");

        demonstrateBasicStubbing();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `new WireMockServer(options().port(0))` - embedowany
         *   serwer, port 0 = system wybiera WOLNY (ten sam wzorzec co
         *   `com.sun.net.httpserver.HttpServer` W calym kursie).
         * - `stubFor(get(urlEqualTo("/sciezka")).willReturn(...))` -
         *   DEFINIUJE, CO serwer ODPOWIE NA dane zadanie.
         * - `okJson("...")` / `aResponse().withStatus(500)` /
         *   `notFound()` - GOTOWE budowniczy typowych odpowiedzi.
         * - TWOJ kod produkcyjny (klient HTTP) LACZY sie Z
         *   `http://localhost:<port>` - W PRODUKCJI zamiast tego URL
         *   wskazywalby NA prawdziwe zewnetrzne API (konfiguracja
         *   PRZEZ `application.properties`/zmienna srodowiskowa -
         *   powiazanie Z `_21_spring_boot/Lesson05`).
         * - Lesson08 pojdzie DALEJ - WERYFIKACJA zadan (`verify(...)`),
         *   symulacja bledow/opoznien, KOLEJNE stuby.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    /** Prosty klient - reprezentuje kod PRODUKCYJNY, ktory normalnie lączyłby sie z zewnetrznym API. */
    static class WeatherApiClient {
        private final String baseUrl;
        private final HttpClient httpClient = HttpClient.newHttpClient();

        WeatherApiClient(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        String getCurrentTemperature(String city) throws Exception {
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/weather?city=" + city)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new IllegalArgumentException("Nieznane miasto: " + city);
            }
            return response.body();
        }
    }

    private static void demonstrateBasicStubbing() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(options().port(0));
        wireMockServer.start();
        try {
            int port = wireMockServer.port();
            System.out.println("\nWireMock (udajacy zewnetrzne 'Weather API') wystartowal na porcie " + port);

            // Definiujemy: "kiedy przyjdzie GET /weather?city=Warszawa, odpowiedz JSON-em".
            wireMockServer.stubFor(get(urlEqualTo("/weather?city=Warszawa"))
                    .willReturn(okJson("{\"city\":\"Warszawa\",\"temperatureCelsius\":22}")));

            // Definiujemy TEZ przypadek bledu (nieznane miasto -> 404).
            wireMockServer.stubFor(get(urlEqualTo("/weather?city=Atlantyda"))
                    .willReturn(notFound()));

            WeatherApiClient client = new WeatherApiClient("http://localhost:" + port);

            String response = client.getCurrentTemperature("Warszawa");
            assertThat(response).contains("\"temperatureCelsius\":22");
            System.out.println("Odpowiedz ze STUBOWANEGO API (Warszawa): " + response);

            try {
                client.getCurrentTemperature("Atlantyda");
                throw new AssertionError("Powinien byl zostac rzucony wyjatek");
            } catch (IllegalArgumentException e) {
                System.out.println("Poprawnie obsluzono 404 dla nieznanego miasta: " + e.getMessage());
            }
        } finally {
            wireMockServer.stop();
        }
    }
}
