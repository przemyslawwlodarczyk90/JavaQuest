package com.example.javaquest._25_unit_testing.Lesson07_AssertJForExceptionsAndCustomAssertions;

import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.catchThrowable;

public class _Lesson07_AssertJForExceptionsAndCustomAssertions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: AssertJ dla wyjatkow + WLASNE asercje domenowe ===");

        /*
         * ============================================================
         * 📦 3 STYLE sprawdzania wyjatkow W AssertJ
         * ============================================================
         * Lesson05 pokazal `assertThatThrownBy(...)`. AssertJ MA
         * jeszcze 2 style: `assertThatExceptionOfType(Klasa.class)
         * .isThrownBy(...)` (bardziej deklaratywny) I `catchThrowable(...)`
         * (przechwytuje wyjatek DO ZMIENNEJ, zeby sprawdzic GO
         * PozNIEJ, WIELOMA osobnymi asercjami).
         *
         * 🔍 WLASNE asercje - DRUGA POLOWA tej lekcji - `AbstractAssert`
         * pozwala ZBUDOWAC WLASNY, DOMENOWY jezyk sprawdzen
         * (`assertThatOrder(order).isPaid()`) - CZYTELNIEJSZY NIZ
         * generyczne `.hasFieldOrPropertyWithValue(...)`.
         */
        System.out.println("assertThatThrownBy / assertThatExceptionOfType / catchThrowable = 3 style sprawdzania wyjatkow. AbstractAssert = WLASNE, domenowe asercje.");

        runAndReport(ExceptionAssertionsTest.class);
        runAndReport(CustomAssertionTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `assertThatThrownBy(() -> ...)` - NAJCZESCIEJ uzywany styl.
         * - `assertThatExceptionOfType(X.class).isThrownBy(() -> ...)` -
         *   TYP wyjatku NA POCZATKU zdania (czasem CZYTELNIEJSZE).
         * - `catchThrowable(...)` - PRZECHWYTUJE wyjatek DO zmiennej -
         *   przydatne, GDY chcesz WIELE OSOBNYCH asercji NA wyjatku.
         * - `assertThatNoException().isThrownBy(...)` - JAWNIE
         *   sprawdza, ze NIC NIE zostalo rzucone (dokumentuje intencje).
         * - `AbstractAssert<WlasnaKlasaAssert, TypDomenowy>` +
         *   `isXxx()` metody zwracajace `this` (`myself`) - fundament
         *   WLASNYCH, PONOWNIE UZYWALNYCH asercji.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    static class InsufficientFundsException extends RuntimeException {
        InsufficientFundsException(String message) {
            super(message);
        }
    }

    static class BankAccount {
        private double balance;

        BankAccount(double balance) {
            this.balance = balance;
        }

        void withdraw(double amount) {
            if (amount > balance) {
                throw new InsufficientFundsException("Brak srodkow: saldo=" + balance + ", kwota=" + amount);
            }
            balance -= amount;
        }
    }

    static class ExceptionAssertionsTest {
        @Test
        void assertThatThrownByStyle() {
            BankAccount account = new BankAccount(100.0);
            assertThatThrownBy(() -> account.withdraw(200.0))
                    .isInstanceOf(InsufficientFundsException.class)
                    .hasMessageContaining("Brak srodkow");
        }

        @Test
        void assertThatExceptionOfTypeStyle() {
            BankAccount account = new BankAccount(50.0);
            assertThatExceptionOfType(InsufficientFundsException.class)
                    .isThrownBy(() -> account.withdraw(999.0));
        }

        @Test
        void catchThrowableStyle() {
            BankAccount account = new BankAccount(10.0);
            Throwable thrown = catchThrowable(() -> account.withdraw(20.0));
            assertThat(thrown).isInstanceOf(InsufficientFundsException.class);
            assertThat(thrown.getMessage()).contains("saldo=10.0");
        }

        @Test
        void assertThatNoExceptionStyle() {
            BankAccount account = new BankAccount(100.0);
            assertThatNoException().isThrownBy(() -> account.withdraw(50.0));
        }
    }

    record Order(String id, double total, boolean paid) {
    }

    static class OrderAssert extends AbstractAssert<OrderAssert, Order> {
        OrderAssert(Order actual) {
            super(actual, OrderAssert.class);
        }

        static OrderAssert assertThatOrder(Order order) {
            return new OrderAssert(order);
        }

        OrderAssert isPaid() {
            isNotNull();
            if (!actual.paid()) {
                failWithMessage("Oczekiwano zamowienia OPLACONEGO, ale <%s> NIE jest oplacone", actual.id());
            }
            return this;
        }

        OrderAssert hasTotalGreaterThan(double amount) {
            isNotNull();
            if (actual.total() <= amount) {
                failWithMessage("Oczekiwano sumy WIEKSZEJ niz <%s>, ale byla <%s>", amount, actual.total());
            }
            return this;
        }
    }

    static class CustomAssertionTest {
        @Test
        void customDomainAssertionsReadLikeSentences() {
            Order order = new Order("ORD-1", 250.0, true);
            // Wlasna, DOMENOWA skladnia - CZYTELNIEJSZA NIZ generyczne hasFieldOrPropertyWithValue(...).
            OrderAssert.assertThatOrder(order).isPaid().hasTotalGreaterThan(100.0);
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
