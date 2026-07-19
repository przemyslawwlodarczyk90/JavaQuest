package com.example.javaquest._25_unit_testing.Lesson10_TestOrderingAndRepeatedTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson10_TestOrderingAndRepeatedTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: Kolejnosc testow (@Order) i @RepeatedTest ===");

        /*
         * ============================================================
         * 📦 DOMYSLNIE kolejnosc testow NIE JEST GWARANTOWANA (Lesson01: "Independent")
         * ============================================================
         * Zasada F.I.R.S.T. (Lesson01) MOWI: testy POWINNY byc
         * NIEZALEZNE OD kolejnosci. `@TestMethodOrder`+`@Order` ISTNIEJA
         * DLA WYJATKOWYCH sytuacji, GDZIE kolejnosc MA znaczenie
         * dydaktyczne/diagnostyczne (np. W TEJ LEKCJI, zeby POKAZAC
         * WYNIK W PRZEWIDYWALNEJ kolejnosci) - W PRAWDZIWYCH testach
         * jednostkowych WYMUSZANIE kolejnosci CZESTO oznacza, ze testy
         * SA (NIEPRAWIDLOWO) OD SIEBIE ZALEZNE.
         *
         * `@RepeatedTest(n)` - URUCHAMIA TA SAMA metode n RAZY -
         * przydatne DO wykrywania testow "flaky" (Lesson01: "Repeatable" -
         * jesli test CZASAMI zawodzi, `@RepeatedTest` to UJAWNI).
         */
        System.out.println("@Order = WYJATEK OD normalnej zasady 'kolejnosc bez znaczenia'. @RepeatedTest(n) = URUCHOM TA SAMA metode n razy - wykrywanie 'flaky' testow.");

        runAndReport(OrderedStepsTest.class);
        runAndReport(RepeatedTestShowcase.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)` NA
         *   klasie + `@Order(n)` NA metodach - WYMUSZA kolejnosc.
         * - Inne `MethodOrderer`: `MethodName` (alfabetycznie),
         *   `Random` (CELOWO losowo - WYKRYWA UKRYTE zaleznosci miedzy
         *   testami!), `DisplayName`.
         * - `@RepeatedTest(5)` - 5 WYWOLAN, KAZDE LICZONE osobno W
         *   raporcie.
         * - `RepetitionInfo` (WSTRZYKIWANY jako parametr) - dostep DO
         *   numeru BIEZACEGO powtorzenia (`getCurrentRepetition()`)
         *   I calkowitej liczby (`getTotalRepetitions()`).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    static class OrderedStepsTest {
        static List<String> executionLog = new ArrayList<>();

        @Test
        @Order(1)
        void step1_createAccount() {
            executionLog.add("createAccount");
            assertThat(executionLog).containsExactly("createAccount");
        }

        @Test
        @Order(2)
        void step2_depositFunds() {
            executionLog.add("depositFunds");
            assertThat(executionLog).containsExactly("createAccount", "depositFunds");
        }

        @Test
        @Order(3)
        void step3_withdrawFunds() {
            executionLog.add("withdrawFunds");
            assertThat(executionLog).containsExactly("createAccount", "depositFunds", "withdrawFunds");
            System.out.println("Kolejnosc wykonania (WYMUSZONA @Order): " + executionLog);
        }
    }

    static class RepeatedTestShowcase {
        static AtomicInteger callCounter = new AtomicInteger(0);

        @RepeatedTest(5)
        void incrementsCounterEachRepetition(RepetitionInfo repetitionInfo) {
            int callNumber = callCounter.incrementAndGet();
            assertThat(callNumber).isEqualTo(repetitionInfo.getCurrentRepetition());
            assertThat(repetitionInfo.getTotalRepetitions()).isEqualTo(5);
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
