package com.example.javaquest._25_unit_testing.Lesson15_MockitoAnnotationsAndExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class _Lesson15_MockitoAnnotationsAndExtension {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: Adnotacje Mockito (@Mock/@InjectMocks) i MockitoExtension ===");

        /*
         * ============================================================
         * 📦 OD "mock(X.class)" RECZNIE DO ADNOTACJI - MNIEJ BOILERPLATE'U
         * ============================================================
         * Lesson13/14 tworzyly mocki RECZNIE: `mock(X.class)`. PRZY
         * WIELU zaleznosciach W JEDNEJ klasie testowej STAJE SIE TO
         * POWTARZALNE - `@Mock` (NA POLU) + `@InjectMocks` (NA
         * TESTOWANYM obiekcie) POZWALAJA Mockito ZROBIC TO ZA CIEBIE:
         * UTWORZYC WSZYSTKIE mocki I WSTRZYKNAC JE (przez konstruktor,
         * jesli istnieje - powiazanie Z `_20_spring_core/Lesson10`)
         * DO obiektu `@InjectMocks`.
         *
         * 🔍 2 sposoby "URUCHOMIENIA" tych adnotacji:
         * 1. `MockitoAnnotations.openMocks(this)` W `@BeforeEach` -
         *    DZIALA WSZEDZIE (nawet BEZ JUnit 5).
         * 2. `@ExtendWith(MockitoExtension.class)` NA klasie testowej -
         *    IDIOMATYCZNY sposob W JUnit 5, Mockito ROBI TO
         *    AUTOMATYCZNIE PRZED KAZDYM testem (BEZ jawnego
         *    `@BeforeEach`).
         */
        System.out.println("@Mock + @InjectMocks = mniej boilerplate'u. MockitoExtension = automatyczna inicjalizacja mockow w JUnit 5.");

        runAndReport(ManualOpenMocksTest.class);
        runAndReport(MockitoExtensionTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Mock` NA POLU - Mockito TWORZY mocka AUTOMATYCZNIE.
         * - `@InjectMocks` NA POLU testowanego obiektu - Mockito
         *   WSTRZYKUJE WSZYSTKIE `@Mock` pola (PRIORYTET: konstruktor >
         *   settery > pola - DOKLADNIE tak jak DI W `_20_spring_core`).
         * - `MockitoAnnotations.openMocks(this)` - RECZNA inicjalizacja
         *   (typowo W `@BeforeEach`), zwraca `AutoCloseable` DO
         *   zamkniecia PO tescie (LUB uzyj try-with-resources).
         * - `@ExtendWith(MockitoExtension.class)` - AUTOMATYCZNA
         *   inicjalizacja/czyszczenie mockow PRZEZ CYKL ZYCIA JUnit 5 -
         *   PREFEROWANY styl W NOWYM kodzie.
         * - `MockitoExtension` DOMYSLNIE WERYFIKUJE TEZ "niepotrzebne
         *   stubowania" (`UnnecessaryStubbingException`) - POMAGA
         *   wykryc MARTWE `when(...)` W teście.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    record Product(String sku, double price, int quantity) {
    }

    interface InventoryRepository {
        Product findBySku(String sku);

        void save(Product product);
    }

    interface PricingCalculator {
        double calculateTotalWithTax(double price, int quantity);
    }

    static class InventoryService {
        private final InventoryRepository repository;
        private final PricingCalculator pricingCalculator;

        InventoryService(InventoryRepository repository, PricingCalculator pricingCalculator) {
            this.repository = repository;
            this.pricingCalculator = pricingCalculator;
        }

        double getTotalValue(String sku) {
            Product product = repository.findBySku(sku);
            if (product == null) {
                throw new IllegalArgumentException("Nie znaleziono produktu: " + sku);
            }
            return pricingCalculator.calculateTotalWithTax(product.price(), product.quantity());
        }

        void restock(String sku, int additionalQuantity) {
            Product existing = repository.findBySku(sku);
            Product restocked = new Product(existing.sku(), existing.price(), existing.quantity() + additionalQuantity);
            repository.save(restocked);
        }
    }

    // ============================================================
    // WARIANT 1: reczna inicjalizacja przez MockitoAnnotations.openMocks(this)
    // ============================================================
    static class ManualOpenMocksTest {
        @Mock
        InventoryRepository repositoryMock;

        @Mock
        PricingCalculator pricingCalculatorMock;

        @InjectMocks
        InventoryService inventoryService;

        AutoCloseable mocksCloser;

        @BeforeEach
        void openMocks() {
            mocksCloser = MockitoAnnotations.openMocks(this);
        }

        @Test
        void injectMocksWiresConstructorDependencies() {
            Product product = new Product("SKU-1", 10.0, 5);
            when(repositoryMock.findBySku("SKU-1")).thenReturn(product);
            when(pricingCalculatorMock.calculateTotalWithTax(10.0, 5)).thenReturn(61.5);

            double total = inventoryService.getTotalValue("SKU-1");

            assertThat(total).isEqualTo(61.5);
            verify(repositoryMock).findBySku("SKU-1");
        }
    }

    // ============================================================
    // WARIANT 2: MockitoExtension - AUTOMATYCZNA inicjalizacja przez JUnit 5
    // ============================================================
    @ExtendWith(MockitoExtension.class)
    static class MockitoExtensionTest {
        @Mock
        InventoryRepository repositoryMock;

        @Mock
        PricingCalculator pricingCalculatorMock;

        @InjectMocks
        InventoryService inventoryService;

        @Test
        void extensionInitializesMocksAutomaticallyWithoutBeforeEach() {
            Product product = new Product("SKU-2", 20.0, 2);
            when(repositoryMock.findBySku("SKU-2")).thenReturn(product);
            when(pricingCalculatorMock.calculateTotalWithTax(20.0, 2)).thenReturn(49.2);

            double total = inventoryService.getTotalValue("SKU-2");

            assertThat(total).isEqualTo(49.2);
        }

        @Test
        void restockFindsThenSavesUpdatedProduct() {
            Product existing = new Product("SKU-3", 5.0, 10);
            when(repositoryMock.findBySku("SKU-3")).thenReturn(existing);

            inventoryService.restock("SKU-3", 15);

            verify(repositoryMock).save(argThatQuantityIs(25));
        }

        private Product argThatQuantityIs(int expectedQuantity) {
            return org.mockito.ArgumentMatchers.argThat(p -> p.quantity() == expectedQuantity);
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
