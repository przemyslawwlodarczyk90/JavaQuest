package com.example.javaquest._25_unit_testing.Lesson18_TestNamingAndOrganizationBestPractices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

public class _Lesson18_TestNamingAndOrganizationBestPractices {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: Nazewnictwo i organizacja testow - dobre praktyki ===");

        /*
         * ============================================================
         * 📦 NAZWA testu TO DOKUMENTACJA - powiazanie z `_16_clean_code/Lesson02_Naming`
         * ============================================================
         * `testMethod1()` LUB `test1()` NIC NIE MOWI - PRZY BLEDZIE
         * (raport CI) trzeba OTWIERAC kod, zeby zrozumiec, CO
         * zawiodlo. DOBRA nazwa OPISUJE: CO jest testowane, W JAKICH
         * WARUNKACH, JAKI jest OCZEKIWANY wynik.
         *
         * 🔍 2 POPULARNE konwencje nazewnicze:
         * - `methodName_condition_expectedResult` (np.
         *   `withdraw_amountExceedsBalance_throwsException`).
         * - "should" styl (np. `shouldThrowException_whenAmountExceedsBalance`).
         * `@DisplayName("...")` (Lesson09) POZWALA DODATKOWO opisac
         * test PELNYM zdaniem PO POLSKU W raporcie, NIEZALEZNIE OD
         * nazwy metody.
         *
         * 📌 STRUKTURA testu - wzorzec AAA (Arrange-Act-Assert, czasem
         * "Given-When-Then"): KAZDY test POWINIEN miec WYRAZNE 3 sekcje -
         * PRZYGOTOWANIE danych, WYKONANIE akcji, SPRAWDZENIE wyniku -
         * ROZDZIELONE (pustymi liniami LUB komentarzami), NIE
         * WYMIESZANE.
         */
        System.out.println("Nazwa testu = DOKUMENTACJA. AAA (Arrange-Act-Assert) = struktura KAZDEGO testu. 1 test = 1 POWOD do zawodzenia.");

        runAndReport(GoodNamingExamplesTest.class);
        runAndReport(AaaStructureTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - lista dobrych praktyk
         * ============================================================
         * - Nazwa OPISUJE zachowanie, NIE implementacje
         *   (`returnsEmptyList_whenNoOrdersExist`, NIE `testMethod3`).
         * - `@DisplayName` DLA CZYTELNEGO opisu W raporcie, ZWLASZCZA
         *   PRZY `@Nested` (Lesson09) - "zdania" ukladaja sie W
         *   SPOJNA specyfikacje.
         * - AAA/Given-When-Then - WYRAZNA struktura KAZDEGO testu.
         * - JEDNA "logiczna" rzecz NA test (NIE oznacza to "1
         *   `assertThat` na test" - MOZE ich byc kilka, JESLI
         *   sprawdzaja TEN SAM logiczny fakt).
         * - Testy GRUPOWANE `@Nested` WEDLUG scenariusza/stanu
         *   (Lesson09), NIE WEDLUG KOLEJNOSCI pisania.
         * - UNIKAJ logiki (`if`/petle) W tescie - test POWINIEN byc
         *   PROSTY DO PRZECZYTANIA "od razu", BEZ analizowania
         *   przeplywu sterowania.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    static class BankAccount {
        private double balance;

        BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        void withdraw(double amount) {
            if (amount > balance) {
                throw new IllegalStateException("Niewystarczajace srodki");
            }
            balance -= amount;
        }

        double getBalance() {
            return balance;
        }
    }

    @DisplayName("BankAccount - nazewnictwo testow")
    static class GoodNamingExamplesTest {

        // ZLA nazwa (przyklad W KOMENTARZU, CELOWO nie uzyta jako nazwa metody):
        // void test1() { ... } - NIC nie mowi o intencji.

        // DOBRA nazwa - schemat methodName_condition_expectedResult:
        @Test
        @DisplayName("withdraw: gdy kwota mniejsza niz saldo, saldo sie zmniejsza")
        void withdraw_amountBelowBalance_decreasesBalance() {
            BankAccount account = new BankAccount(100.0);
            account.withdraw(30.0);
            assertThat(account.getBalance()).isEqualTo(70.0);
        }

        @Test
        @DisplayName("withdraw: gdy kwota przekracza saldo, rzuca wyjatek")
        void withdraw_amountExceedsBalance_throwsIllegalStateException() {
            BankAccount account = new BankAccount(50.0);
            assertThatThrownBy(() -> account.withdraw(100.0))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Niewystarczajace");
        }

        @Test
        @DisplayName("withdraw: gdy kwota rowna calemu saldu, saldo spada do zera")
        void withdraw_amountEqualsBalance_resultsInZeroBalance() {
            BankAccount account = new BankAccount(20.0);
            account.withdraw(20.0);
            assertThat(account.getBalance()).isZero();
        }
    }

    @DisplayName("BankAccount - struktura Arrange-Act-Assert")
    static class AaaStructureTest {

        @Nested
        @DisplayName("Gdy konto ma dodatnie saldo")
        class GivenPositiveBalance {

            @Test
            @DisplayName("wielokrotna wyplata poprawnie zmniejsza saldo")
            void multipleWithdrawalsReduceBalanceCorrectly() {
                // ARRANGE - przygotowanie danych I obiektu testowanego.
                BankAccount account = new BankAccount(200.0);

                // ACT - WYKONANIE akcji bedacej PRZEDMIOTEM testu.
                account.withdraw(50.0);
                account.withdraw(30.0);

                // ASSERT - SPRAWDZENIE wyniku (1 logiczny fakt: koncowe saldo).
                assertThat(account.getBalance()).isEqualTo(120.0);
            }
        }

        @Nested
        @DisplayName("Gdy konto ma saldo zerowe")
        class GivenZeroBalance {

            @Test
            @DisplayName("jakakolwiek wyplata rzuca wyjatek")
            void anyWithdrawalThrowsException() {
                // ARRANGE
                BankAccount account = new BankAccount(0.0);

                // ACT + ASSERT (polaczone, bo akcja SAMA rzuca wyjatek - typowy wzorzec
                // dla testow negatywnych, patrz Lesson07).
                assertThatThrownBy(() -> account.withdraw(1.0))
                        .isInstanceOf(IllegalStateException.class);
            }
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
