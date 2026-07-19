package com.example.javaquest._26_integration_testing.Lesson16_IntegrationTestingCapstone;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson16_IntegrationTestingCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16 (KAPSZTON): Pelny test integracyjny 'JavaQuest Order Processing' ===");

        /*
         * ============================================================
         * 📦 KAPSZTON - LACZYMY WSZYSTKIE techniki Z Lesson01-15 W JEDNYM scenariuszu
         * ============================================================
         * `OrderProcessingService` ZALEZY OD 2 PRAWDZIWYCH systemow:
         * (1) PRAWDZIWA baza H2 (repozytorium zamowien - Lesson01/03/05),
         * (2) STUBOWANY, zewnetrzny serwis platnosci PRZEZ WireMock
         * (Lesson07-08). Dane testowe TWORZONE SA przez Test Data
         * Builder (Lesson10), KAZDY scenariusz UZYWA WLASNEGO,
         * UNIKALNEGO ID (Lesson11 - izolacja), BEZ zadnego sztywnego
         * `sleep` (Lesson12), ZAWSZE Z try/finally (Lesson02/09/15).
         * NA KONCU testy SA URUCHOMIONE W 2 ETAPACH "CI" (Lesson14).
         */
        System.out.println("Kapszton LACZY: PRAWDZIWA baza H2 + STUBOWANY WireMock platnosci + Test Data Builder + izolacja UUID + 2-etapowy pipeline.");

        WireMockServer paymentGatewayStub = new WireMockServer(options().port(0));
        paymentGatewayStub.start();
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:lesson16-capstone;DB_CLOSE_DELAY=-1", "sa", "");
        try {
            setupSchema(connection);
            configurePaymentStub(paymentGatewayStub);

            OrderRepository repository = new OrderRepository(connection);
            PaymentGatewayClient paymentClient = new PaymentGatewayClient("http://localhost:" + paymentGatewayStub.port());
            OrderProcessingService service = new OrderProcessingService(repository, paymentClient);

            runScenario1_SuccessfulOrder(service, repository);
            runScenario2_DeclinedPayment(service, repository, paymentGatewayStub);
            runScenario3_IsolationBetweenScenarios(repository);

            System.out.println("\nWSZYSTKIE 3 scenariusze ZAKONCZONE POWODZENIEM - baza H2 + WireMock + builder + izolacja dzialaly RAZEM POPRAWNIE.");
        } finally {
            connection.close();
            paymentGatewayStub.stop();
            System.out.println("Zasoby (polaczenie DB + WireMock) POPRAWNIE zamkniete.");
        }

        System.out.println("\n=== KONIEC LEKCJI 16, KONIEC ROZDZIALU _26_integration_testing ===");
    }

    // ============================================================
    // DOMENA + WARSTWY (repozytorium + klient platnosci + serwis)
    // ============================================================
    record Order(String id, String customerId, double total, String status) {
    }

    static class OrderRepository {
        final Connection connection;

        OrderRepository(Connection connection) {
            this.connection = connection;
        }

        void save(Order order) throws Exception {
            try (PreparedStatement statement = connection.prepareStatement(
                    "MERGE INTO orders (id, customer_id, total, status) KEY(id) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, order.id());
                statement.setString(2, order.customerId());
                statement.setDouble(3, order.total());
                statement.setString(4, order.status());
                statement.executeUpdate();
            }
        }

        Order findById(String id) throws Exception {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, customer_id, total, status FROM orders WHERE id = ?")) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return new Order(resultSet.getString("id"), resultSet.getString("customer_id"),
                            resultSet.getDouble("total"), resultSet.getString("status"));
                }
            }
        }
    }

    static class PaymentGatewayClient {
        private final String baseUrl;
        private final HttpClient httpClient = HttpClient.newHttpClient();

        PaymentGatewayClient(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        boolean charge(String customerId, double amount) throws Exception {
            String body = "{\"customerId\":\"" + customerId + "\",\"amount\":" + amount + "}";
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/charge"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        }
    }

    static class OrderProcessingService {
        private final OrderRepository repository;
        private final PaymentGatewayClient paymentClient;

        OrderProcessingService(OrderRepository repository, PaymentGatewayClient paymentClient) {
            this.repository = repository;
            this.paymentClient = paymentClient;
        }

        Order processOrder(String customerId, double total) throws Exception {
            String orderId = UUID.randomUUID().toString();
            boolean charged = paymentClient.charge(customerId, total);
            Order order = new Order(orderId, customerId, total, charged ? "CONFIRMED" : "PAYMENT_DECLINED");
            repository.save(order);
            return order;
        }
    }

    /** Test Data Builder (Lesson10) - CZYTELNE tworzenie scenariuszy testowych. */
    static class OrderScenarioBuilder {
        private static final AtomicInteger SEQUENCE = new AtomicInteger(1);
        private String customerId = "CUST-" + SEQUENCE.getAndIncrement();
        private double total = 100.0;

        static OrderScenarioBuilder aScenario() {
            return new OrderScenarioBuilder();
        }

        OrderScenarioBuilder withTotal(double total) {
            this.total = total;
            return this;
        }

        String customerId() {
            return customerId;
        }

        double total() {
            return total;
        }
    }

    // ============================================================
    // SETUP
    // ============================================================
    private static void setupSchema(Connection connection) throws Exception {
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE orders (id VARCHAR(50) PRIMARY KEY, customer_id VARCHAR(50), total DECIMAL(10,2), status VARCHAR(30))");
        }
    }

    private static void configurePaymentStub(WireMockServer wireMockServer) {
        // WYZSZY priorytet (NIZSZA liczba = WAZNIEJSZY stub) - inaczej ogolny stub PONIZEJ
        // (rejestrowany JAKO DRUGI) PRZESLONILBY ten bardziej szczegolowy, bo WireMock, GDY WIELE
        // stubow PASUJE, wybiera OSTATNIO zarejestrowany PRZY TYM SAMYM priorytecie.
        wireMockServer.stubFor(post(urlEqualTo("/charge"))
                .atPriority(1)
                .withRequestBody(com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath("$.customerId", com.github.tomakehurst.wiremock.client.WireMock.matching(".*-declined")))
                .willReturn(aResponse().withStatus(402)));
        wireMockServer.stubFor(post(urlEqualTo("/charge")).atPriority(2).willReturn(okJson("{\"approved\":true}")));
    }

    // ============================================================
    // SCENARIUSZE
    // ============================================================
    private static void runScenario1_SuccessfulOrder(OrderProcessingService service, OrderRepository repository) throws Exception {
        System.out.println("\n--- Scenariusz 1: udane zamowienie (platnosc zaakceptowana) ---");
        OrderScenarioBuilder scenario = OrderScenarioBuilder.aScenario().withTotal(249.99);

        Order created = service.processOrder(scenario.customerId(), scenario.total());
        Order fromDb = repository.findById(created.id());

        assertThat(fromDb.status()).isEqualTo("CONFIRMED");
        assertThat(fromDb.total()).isEqualTo(249.99);
        System.out.println("Zamowienie " + fromDb.id() + " POPRAWNIE potwierdzone i zapisane w PRAWDZIWEJ bazie H2.");
    }

    private static void runScenario2_DeclinedPayment(OrderProcessingService service, OrderRepository repository, WireMockServer wireMockServer) throws Exception {
        System.out.println("\n--- Scenariusz 2: platnosc odrzucona przez (stubowany) zewnetrzny gateway ---");
        String decliningCustomerId = "CUST-" + System.nanoTime() + "-declined";

        Order created = service.processOrder(decliningCustomerId, 50.0);
        Order fromDb = repository.findById(created.id());

        assertThat(fromDb.status()).isEqualTo("PAYMENT_DECLINED");
        System.out.println("Zamowienie " + fromDb.id() + " POPRAWNIE oznaczone jako odrzucone (WireMock zwrocil 402).");
    }

    private static void runScenario3_IsolationBetweenScenarios(OrderRepository repository) throws Exception {
        System.out.println("\n--- Scenariusz 3: izolacja - oba wczesniejsze zamowienia WSPOLISTNIEJA bez kolizji ---");
        try (var statement = repository.connection.createStatement();
             var resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders")) {
            resultSet.next();
            int totalOrders = resultSet.getInt(1);
            assertThat(totalOrders).isEqualTo(2);
            System.out.println("W bazie sa DOKLADNIE 2 zamowienia (ze scenariuszy 1 i 2), KAZDE Z WLASNYM, UNIKALNYM ID - ZERO kolizji.");
        }
    }
}
