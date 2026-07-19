package com.example.javaquest._25_unit_testing.Lesson13_MockitoBasics;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class _Lesson13_MockitoBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: Mockito - podstawy (mock/when/verify) ===");

        /*
         * ============================================================
         * 📦 PO CO mockowac? IZOLACJA testowanej jednostki OD jej zaleznosci
         * ============================================================
         * Test JEDNOSTKOWY (Lesson01) MA testowac JEDNA jednostke KODU
         * W IZOLACJI. Jesli `OrderService` ZALEZY OD `PaymentGateway`
         * (prawdziwe polaczenie sieciowe - powiazanie Z
         * `_06_networking`), test `OrderService` NIE MOZE zalezec OD
         * DZIALAJACEGO serwera platnosci - Mockito TWORZY "atrapy"
         * (mocki) TYCH zaleznosci, KTORYMI STERUJEMY W PELNI.
         *
         * 🔍 3 KROKI klasycznego uzycia Mockito:
         * 1. `mock(Interfejs.class)` - TWORZY atrape.
         * 2. `when(mock.metoda(...)).thenReturn(wartosc)` - PROGRAMUJE
         *    zachowanie atrapy.
         * 3. `verify(mock).metoda(...)` - SPRAWDZA, ze atrapa ZOSTALA
         *    WYWOLANA W OCZEKIWANY sposob.
         */
        System.out.println("mock(X.class) = atrapa zaleznosci. when(...).thenReturn(...) = programowanie zachowania. verify(mock) = sprawdzenie interakcji.");

        runAndReport(MockitoBasicsTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `mock(Interfejs.class)` - DZIALA NA interfejsach I klasach
         *   (przez bajtkod wygenerowany runtime'owo, biblioteka
         *   `byte-buddy` - TRANSYTYWNA zaleznosc `mockito-core`).
         * - `when(mock.x()).thenReturn(y)` - "STUBOWANIE" - PROGRAMUJE,
         *   CO ma zwrocic wywolanie.
         * - `when(mock.x()).thenThrow(new WyjatekX())` - PROGRAMUJE
         *   RZUCENIE wyjatku.
         * - `verify(mock).x()` - SPRAWDZA, ze `x()` ZOSTALO wywolane
         *   DOKLADNIE RAZ (domyslnie).
         * - `verify(mock, times(2)).x()` / `verify(mock, never()).x()` -
         *   PRECYZYJNA liczba wywolan.
         * - `verifyNoInteractions(mock)` - SPRAWDZA, ze mock W OGOLE
         *   NIE ZOSTAL uzyty.
         * - Mock BEZ zaprogramowanego `when(...)` zwraca DOMYSLNA
         *   wartosc dla typu (`null`/`0`/`false`/PUSTA kolekcja) -
         *   NIGDY `NullPointerException` SAM Z SIEBIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    interface PaymentGateway {
        boolean charge(String accountId, double amount);

        double getBalance(String accountId);
    }

    record OrderResult(boolean success, String message) {
    }

    static class OrderService {
        private final PaymentGateway paymentGateway;

        OrderService(PaymentGateway paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        OrderResult placeOrder(String accountId, double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Kwota musi byc dodatnia");
            }
            boolean charged = paymentGateway.charge(accountId, amount);
            if (!charged) {
                return new OrderResult(false, "Platnosc odrzucona");
            }
            return new OrderResult(true, "Zamowienie zlozone");
        }
    }

    static class MockitoBasicsTest {

        @Test
        void mockReturnsStubbedValueWhenProgrammed() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            when(gatewayMock.charge("ACC-1", 100.0)).thenReturn(true);

            OrderService service = new OrderService(gatewayMock);
            OrderResult result = service.placeOrder("ACC-1", 100.0);

            assertThat(result.success()).isTrue();
            assertThat(result.message()).isEqualTo("Zamowienie zlozone");
        }

        @Test
        void mockReturnsFalseAndOrderIsRejected() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            when(gatewayMock.charge("ACC-2", 50.0)).thenReturn(false);

            OrderService service = new OrderService(gatewayMock);
            OrderResult result = service.placeOrder("ACC-2", 50.0);

            assertThat(result.success()).isFalse();
            assertThat(result.message()).isEqualTo("Platnosc odrzucona");
        }

        @Test
        void verifyChargeWasCalledExactlyOnce() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            when(gatewayMock.charge("ACC-3", 25.0)).thenReturn(true);

            OrderService service = new OrderService(gatewayMock);
            service.placeOrder("ACC-3", 25.0);

            verify(gatewayMock, times(1)).charge("ACC-3", 25.0);
        }

        @Test
        void verifyChargeNeverCalledWhenAmountInvalid() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            OrderService service = new OrderService(gatewayMock);

            assertThatThrownBy(() -> service.placeOrder("ACC-4", -10.0))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(gatewayMock, never()).charge("ACC-4", -10.0);
            verifyNoInteractions(gatewayMock);
        }

        @Test
        void unstubbedMockMethodReturnsDefaultValueNotNull() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            // BRAK `when(...)` DLA getBalance - Mockito zwraca DOMYSLNA wartosc dla double = 0.0.
            double balance = gatewayMock.getBalance("ACC-UNKNOWN");
            assertThat(balance).isZero();
        }

        @Test
        void mockCanBeUsedForInterfaceWithMultipleImplementationsInList() {
            PaymentGateway gatewayMock = mock(PaymentGateway.class);
            when(gatewayMock.charge("A", 10.0)).thenReturn(true);
            when(gatewayMock.charge("B", 10.0)).thenReturn(false);

            List<Boolean> results = List.of(
                    gatewayMock.charge("A", 10.0),
                    gatewayMock.charge("B", 10.0)
            );

            assertThat(results).containsExactly(true, false);
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
