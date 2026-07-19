package com.example.javaquest._26_integration_testing.Lesson08_StubbingAndVerifyingHttpCalls;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson08_StubbingAndVerifyingHttpCalls {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: Weryfikacja wywolan HTTP i symulacja bledow (WireMock) ===");

        /*
         * ============================================================
         * 📦 OD stubowania DO WERYFIKACJI - "byl mock, byl spy, teraz obie role naraz"
         * ============================================================
         * Lesson07 uczyl TYLKO stubowania (CO WireMock ODPOWIE).
         * TA lekcja idzie DALEJ - `verify(...)` SPRAWDZA, CZY Twoj
         * klient WYSLAL zadanie Z OCZEKIWANA TRESCIA/naglowkami -
         * DOKLADNIE ta sama idea co `verify(mock)...` W Mockito
         * (`_25_unit_testing/Lesson13`), ALE NA POZIOMIE PRAWDZIWEGO
         * protokolu HTTP, NIE obiektu Javy.
         *
         * 🔍 DRUGI temat: symulacja BLEDOW (500, opoznienia) - test
         * integracyjny MOZE (I POWINIEN) sprawdzic, CZY Twoj kod
         * POPRAWNIE reaguje NA AWARIE zewnetrznego serwisu - CZEGO
         * NIE DA sie latwo zrobic Z prawdziwym, zewnetrznym API
         * (NIE MOZESZ "poprosic" prawdziwego dostawcy platnosci O
         * zwrocenie 500 NA zadanie).
         */
        System.out.println("verify(...) = sprawdzenie, CO klient FAKTYCZNIE wyslal. Symulacja bledow (500/opoznienia) = testowanie ODPORNOSCI kodu.");

        demonstrateRequestVerification();
        demonstrateErrorSimulation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `verify(postRequestedFor(urlEqualTo("/orders"))
         *   .withRequestBody(equalToJson("...")))` - SPRAWDZA TRESC
         *   WYSLANEGO zadania.
         * - `verify(exactly(2), getRequestedFor(...))` - SPRAWDZA
         *   LICZBE wywolan (analogia Z Mockito `times(2)`).
         * - `.willReturn(serverError())` - symuluje 500 Internal
         *   Server Error.
         * - `.willReturn(aResponse().withFixedDelay(3000))` -
         *   symuluje WOLNA odpowiedz (TEST timeoutu klienta).
         * - Ten wzorzec (stub + verify) POZWALA testowac ZAROWNO
         *   "happy path", JAK I sciezki BLEDOW zewnetrznej integracji -
         *   BEZ dostepu DO prawdziwego, zewnetrznego serwisu.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    static class OrderNotificationClient {
        private final String baseUrl;
        private final HttpClient httpClient;

        OrderNotificationClient(String baseUrl, Duration timeout) {
            this.baseUrl = baseUrl;
            this.httpClient = HttpClient.newBuilder().connectTimeout(timeout).build();
        }

        void notifyOrderCreated(String orderId, double total) throws Exception {
            String body = "{\"orderId\":\"" + orderId + "\",\"total\":" + total + "}";
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/orders"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .timeout(Duration.ofSeconds(1))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 500) {
                throw new IllegalStateException("Zewnetrzny serwis powiadomien odpowiedzial bledem: " + response.statusCode());
            }
        }

        String fetchOrderStatus(String orderId) throws Exception {
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/orders/" + orderId + "/status")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
    }

    private static void demonstrateRequestVerification() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(options().port(0));
        wireMockServer.start();
        try {
            int port = wireMockServer.port();
            wireMockServer.stubFor(post(urlEqualTo("/orders")).willReturn(aResponse().withStatus(202)));
            wireMockServer.stubFor(get(urlEqualTo("/orders/ORD-1/status")).willReturn(okJson("{\"status\":\"SHIPPED\"}")));

            OrderNotificationClient client = new OrderNotificationClient("http://localhost:" + port, Duration.ofSeconds(2));
            client.notifyOrderCreated("ORD-1", 149.99);
            client.notifyOrderCreated("ORD-1", 149.99);

            System.out.println("\nWyslano 2x powiadomienie o zamowieniu ORD-1.");

            // WERYFIKACJA: sprawdzamy TRESC wyslanego zadania I LICZBE wywolan.
            wireMockServer.verify(exactly(2), postRequestedFor(urlEqualTo("/orders"))
                    .withRequestBody(equalToJson("{\"orderId\":\"ORD-1\",\"total\":149.99}")));
            System.out.println("verify() potwierdzil: DOKLADNIE 2 zadania POST /orders z oczekiwana trescia JSON.");

            String status = client.fetchOrderStatus("ORD-1");
            assertThat(status).contains("SHIPPED");
            wireMockServer.verify(getRequestedFor(urlEqualTo("/orders/ORD-1/status")));
            System.out.println("verify() potwierdzil: zadanie GET /orders/ORD-1/status FAKTYCZNIE zostalo wyslane.");
        } finally {
            wireMockServer.stop();
        }
    }

    private static void demonstrateErrorSimulation() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(options().port(0));
        wireMockServer.start();
        try {
            int port = wireMockServer.port();
            System.out.println("\n--- Symulacja bledu 500 ---");
            wireMockServer.stubFor(post(urlEqualTo("/orders")).willReturn(serverError()));

            OrderNotificationClient client = new OrderNotificationClient("http://localhost:" + port, Duration.ofSeconds(2));
            assertThatThrownBy(() -> client.notifyOrderCreated("ORD-2", 50.0))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("500");
            System.out.println("Klient POPRAWNIE wykryl blad 500 zasymulowany przez WireMock.");

            System.out.println("\n--- Symulacja timeoutu (opozniona odpowiedz) ---");
            wireMockServer.resetAll();
            wireMockServer.stubFor(post(urlEqualTo("/orders"))
                    .willReturn(aResponse().withStatus(202).withFixedDelay(1500)));

            OrderNotificationClient impatientClient = new OrderNotificationClient("http://localhost:" + port, Duration.ofSeconds(2));
            assertThatThrownBy(() -> impatientClient.notifyOrderCreated("ORD-3", 20.0))
                    .isInstanceOf(java.net.http.HttpTimeoutException.class);
            System.out.println("Klient POPRAWNIE przerwal zadanie po przekroczeniu timeoutu (1s), gdy WireMock opoznil odpowiedz o 1.5s.");
        } finally {
            wireMockServer.stop();
        }
    }
}
