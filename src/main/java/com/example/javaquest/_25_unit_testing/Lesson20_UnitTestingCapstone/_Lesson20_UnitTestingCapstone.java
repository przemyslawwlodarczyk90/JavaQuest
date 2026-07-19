package com.example.javaquest._25_unit_testing.Lesson20_UnitTestingCapstone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class _Lesson20_UnitTestingCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20 (KAPSZTON): pelny pakiet testow OrderService ===");

        /*
         * ============================================================
         * 📦 KAPSZTON - LACZYMY WSZYSTKIE techniki Z Lesson01-19 W JEDNYM serwisie
         * ============================================================
         * `OrderService` ZALEZY OD 3 interfejsow (inwentarz/platnosci/
         * audyt) - DOKLADNIE takiej klasy dotyczy CALY ten rozdzial.
         * Pakiet testow ponizej wykorzystuje: JUnit 5 (Lesson01-04),
         * AssertJ (Lesson05-07), `@ParameterizedTest` (Lesson08),
         * `@Nested`+`@DisplayName` (Lesson09), `@RepeatedTest`
         * (Lesson10), `@Tag` (Lesson12), Mockito `@Mock`/`@InjectMocks`/
         * `MockitoExtension` (Lesson13-15), `ArgumentCaptor` (Lesson14),
         * swiadome PRZYPADKI brzegowe (Lesson17) I nazewnictwo WEDLUG
         * konwencji `metoda_warunek_wynik` (Lesson18) - a NA KONCU
         * uruchamiamy WSZYSTKO PRZEZ Launcher API (Lesson01), ZARÓWNO
         * bez filtra, JAK I Z filtrem tagow (Lesson12).
         */
        System.out.println("Ten kapszton LACZY: JUnit5 + AssertJ + Mockito + Nested/Tag/Parameterized + przypadki brzegowe W JEDNYM pakiecie testow OrderService.");

        runAllTests();
        runOnlyFastTaggedTests();

        System.out.println("\n=== KONIEC LEKCJI 20, KONIEC ROZDZIALU _25_unit_testing ===");
    }

    // ============================================================
    // DOMENA
    // ============================================================
    record OrderLine(String sku, int quantity, double unitPrice) {
    }

    record OrderResult(boolean success, String message, double total) {
    }

    interface InventoryRepository {
        boolean isInStock(String sku, int quantity);
    }

    interface PaymentGateway {
        boolean charge(String customerId, double amount);
    }

    interface AuditLogger {
        void log(String message);
    }

    static class OrderService {
        private final InventoryRepository inventory;
        private final PaymentGateway paymentGateway;
        private final AuditLogger auditLogger;

        OrderService(InventoryRepository inventory, PaymentGateway paymentGateway, AuditLogger auditLogger) {
            this.inventory = inventory;
            this.paymentGateway = paymentGateway;
            this.auditLogger = auditLogger;
        }

        OrderResult placeOrder(String customerId, List<OrderLine> lines) {
            if (lines == null || lines.isEmpty()) {
                throw new IllegalArgumentException("Zamowienie musi miec co najmniej 1 pozycje");
            }
            for (OrderLine line : lines) {
                if (line.quantity() <= 0) {
                    throw new IllegalArgumentException("Ilosc musi byc dodatnia: " + line.sku());
                }
                if (line.unitPrice() < 0) {
                    throw new IllegalArgumentException("Cena nie moze byc ujemna: " + line.sku());
                }
            }
            for (OrderLine line : lines) {
                if (!inventory.isInStock(line.sku(), line.quantity())) {
                    return new OrderResult(false, "Brak na magazynie: " + line.sku(), 0.0);
                }
            }

            double total = lines.stream().mapToDouble(l -> l.quantity() * l.unitPrice()).sum();

            if (!paymentGateway.charge(customerId, total)) {
                return new OrderResult(false, "Platnosc odrzucona", total);
            }

            auditLogger.log("Zamowienie potwierdzone dla " + customerId + ", suma=" + total);
            return new OrderResult(true, "Zamowienie potwierdzone", total);
        }
    }

    // ============================================================
    // TESTY
    // ============================================================
    @DisplayName("OrderService - kapszton rozdzialu _25_unit_testing")
    @ExtendWith(MockitoExtension.class)
    static class OrderServiceCapstoneTest {

        @Mock
        InventoryRepository inventoryMock;

        @Mock
        PaymentGateway paymentGatewayMock;

        @Mock
        AuditLogger auditLoggerMock;

        @InjectMocks
        OrderService orderService;

        @Nested
        @DisplayName("Gdy dane wejsciowe sa nieprawidlowe (Lesson17: przypadki brzegowe)")
        class GivenInvalidInput {

            @ParameterizedTest
            @NullAndEmptySource
            @Tag("fast")
            void placeOrder_nullOrEmptyLines_throwsIllegalArgumentException(List<OrderLine> invalidLines) {
                assertThatThrownBy(() -> orderService.placeOrder("CUST-1", invalidLines))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ParameterizedTest
            @CsvSource({"0, 10.0", "-1, 10.0", "5, -1.0"})
            @Tag("fast")
            void placeOrder_invalidQuantityOrPrice_throwsIllegalArgumentException(int quantity, double price) {
                List<OrderLine> lines = List.of(new OrderLine("SKU-1", quantity, price));
                assertThatThrownBy(() -> orderService.placeOrder("CUST-1", lines))
                        .isInstanceOf(IllegalArgumentException.class);

                // Zaden przypadek nie powinien nawet dotrzec do magazynu (walidacja wczesniej).
                verify(inventoryMock, never()).isInStock(anyString(), org.mockito.ArgumentMatchers.anyInt());
            }
        }

        @Nested
        @DisplayName("Gdy produkt jest niedostepny na magazynie")
        class GivenOutOfStock {

            @Test
            @Tag("fast")
            @DisplayName("placeOrder: brak na magazynie -> success=false, platnosc NIGDY nie jest probowana")
            void placeOrder_itemOutOfStock_returnsFailureWithoutCharging() {
                List<OrderLine> lines = List.of(new OrderLine("SKU-2", 2, 20.0));
                when(inventoryMock.isInStock("SKU-2", 2)).thenReturn(false);

                OrderResult result = orderService.placeOrder("CUST-2", lines);

                assertThat(result.success()).isFalse();
                assertThat(result.message()).contains("SKU-2");
                verify(paymentGatewayMock, never()).charge(anyString(), anyDouble());
            }
        }

        @Nested
        @DisplayName("Gdy platnosc zostaje odrzucona")
        class GivenPaymentDeclined {

            @Test
            @Tag("fast")
            @DisplayName("placeOrder: platnosc odrzucona -> success=false, audit NIGDY nie jest wolany")
            void placeOrder_paymentDeclined_returnsFailureWithoutAuditLog() {
                List<OrderLine> lines = List.of(new OrderLine("SKU-3", 1, 50.0));
                when(inventoryMock.isInStock("SKU-3", 1)).thenReturn(true);
                when(paymentGatewayMock.charge("CUST-3", 50.0)).thenReturn(false);

                OrderResult result = orderService.placeOrder("CUST-3", lines);

                assertThat(result.success()).isFalse();
                assertThat(result.message()).isEqualTo("Platnosc odrzucona");
                verify(auditLoggerMock, never()).log(anyString());
            }
        }

        @Nested
        @DisplayName("Gdy zamowienie konczy sie sukcesem (happy path)")
        class GivenHappyPath {

            @Test
            @Tag("fast")
            @DisplayName("placeOrder: wszystko dostepne i platnosc zaakceptowana -> success=true, audit zawiera sume")
            void placeOrder_allInStockAndPaymentAccepted_returnsSuccessAndLogsAudit() {
                List<OrderLine> lines = List.of(
                        new OrderLine("SKU-4", 2, 15.0),
                        new OrderLine("SKU-5", 1, 30.0));
                when(inventoryMock.isInStock("SKU-4", 2)).thenReturn(true);
                when(inventoryMock.isInStock("SKU-5", 1)).thenReturn(true);
                when(paymentGatewayMock.charge("CUST-4", 60.0)).thenReturn(true);

                OrderResult result = orderService.placeOrder("CUST-4", lines);

                assertThat(result.success()).isTrue();
                assertThat(result.total()).isEqualTo(60.0);

                ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
                verify(auditLoggerMock).log(messageCaptor.capture());
                assertThat(messageCaptor.getValue()).contains("CUST-4").contains("60.0");
            }

            @RepeatedTest(3)
            @Tag("slow")
            @DisplayName("placeOrder: obliczenie sumy jest deterministyczne przy powtorzeniach")
            void placeOrder_totalCalculation_isDeterministicAcrossRepetitions() {
                List<OrderLine> lines = List.of(new OrderLine("SKU-6", 3, 10.0));
                when(inventoryMock.isInStock("SKU-6", 3)).thenReturn(true);
                when(paymentGatewayMock.charge("CUST-5", 30.0)).thenReturn(true);

                OrderResult result = orderService.placeOrder("CUST-5", lines);

                assertThat(result.total()).isEqualTo(30.0);
            }
        }
    }

    private static void runAllTests() {
        System.out.println("\n--- Uruchomienie 1: WSZYSTKIE testy kapsztonu (bez filtra) ---");
        run(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(OrderServiceCapstoneTest.class))
                .build());
    }

    private static void runOnlyFastTaggedTests() {
        System.out.println("\n--- Uruchomienie 2: TYLKO tag \"fast\" (Lesson12: TagFilter) ---");
        run(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(OrderServiceCapstoneTest.class))
                .filters(TagFilter.includeTags("fast"))
                .build());
    }

    private static void run(LauncherDiscoveryRequest request) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println("Znalezione: " + summary.getTestsFoundCount() + ", udane: " + summary.getTestsSucceededCount());
    }
}
