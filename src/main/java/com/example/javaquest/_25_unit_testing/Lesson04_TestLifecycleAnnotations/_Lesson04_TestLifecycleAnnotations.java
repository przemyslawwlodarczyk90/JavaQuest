package com.example.javaquest._25_unit_testing.Lesson04_TestLifecycleAnnotations;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class _Lesson04_TestLifecycleAnnotations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Cykl zycia testu - @BeforeEach/@AfterEach/@BeforeAll/@AfterAll ===");

        /*
         * ============================================================
         * 📦 KAZDA metoda @Test DOSTAJE WLASNA, SWIEZA instancje klasy
         * ============================================================
         * DOMYSLNIE (`TestInstance.Lifecycle.PER_METHOD`) JUnit 5
         * TWORZY NOWA instancje klasy testowej DLA KAZDEJ metody
         * `@Test` - TO gwarantuje "Independent" Z zasady F.I.R.S.T.
         * (Lesson01): 1 test NIE MOZE "zabrudzic" pol obiektu I
         * WPLYNAC NA kolejny test.
         *
         * `@BeforeEach`/`@AfterEach` - WOLANE PRZED/PO KAZDYM tescie
         * (metody INSTANCJI - MAJA dostep DO pol obiektu).
         * `@BeforeAll`/`@AfterAll` - WOLANE RAZ, PRZED/PO WSZYSTKICH
         * testach W klasie - MUSZA byc `static` (bo NIE MA "jednej"
         * instancji, KTORA przetrwalaby CALA klase w domyslnym
         * trybie PER_METHOD).
         */
        System.out.println("@BeforeEach/@AfterEach = PRZED/PO KAZDYM tescie. @BeforeAll/@AfterAll = RAZ, dla CALEJ klasy (musza byc static, chyba ze PER_CLASS).");

        runAndReport(LifecycleOrderTest.class);
        runAndReport(PerClassLifecycleTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kolejnosc: `@BeforeAll` -> (`@BeforeEach` -> `@Test` ->
         *   `@AfterEach`) x N -> `@AfterAll`.
         * - DOMYSLNIE `@BeforeAll`/`@AfterAll` MUSZA byc `static` -
         *   `@TestInstance(Lifecycle.PER_CLASS)` NA klasie ZMIENIA TO
         *   (JEDNA instancja DLA CALEJ klasy, MOZNA uzyc metod
         *   NIE-statycznych) - ale WTEDY pola INSTANCJI SA
         *   WSPOLDZIELONE miedzy testami (NIE resetuja sie automatycznie -
         *   TY musisz je resetowac W `@BeforeEach`, jesli trzeba).
         * - `@BeforeEach`/`@AfterEach` TO IDEALNE miejsce NA
         *   przygotowanie/sprzatanie zasobow (np. otwarcie/zamkniecie
         *   polaczenia Z baza testowa).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    static class LifecycleOrderTest {
        static List<String> callOrder = new ArrayList<>();
        int counter;

        @BeforeAll
        static void beforeAll() {
            callOrder.add("beforeAll");
        }

        @BeforeEach
        void beforeEach() {
            callOrder.add("beforeEach");
            counter = 10;
        }

        @Test
        void firstTest() {
            callOrder.add("firstTest");
            assertEquals(10, counter);
            counter = 999;
        }

        @Test
        void secondTest() {
            callOrder.add("secondTest");
            // KAZDY test DOSTAJE NOWA instancje - counter ZNOWU jest 10, NIE 999 Z firstTest.
            assertEquals(10, counter);
        }

        @AfterEach
        void afterEach() {
            callOrder.add("afterEach");
        }

        @AfterAll
        static void afterAll() {
            callOrder.add("afterAll");
            System.out.println("Kolejnosc wywolan (PER_METHOD): " + callOrder);
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    static class PerClassLifecycleTest {
        int sharedCounter = 0;

        @BeforeAll
        void beforeAllNotStatic() {
            // Z PER_CLASS, @BeforeAll MOZE byc NIE-statyczne (JEDNA instancja DLA CALEJ klasy).
            sharedCounter = 100;
        }

        @Test
        void incrementsSharedState() {
            sharedCounter++;
            assertTrue(sharedCounter >= 101);
        }

        @Test
        void sharedStateSurvivesBetweenTests() {
            // W PER_CLASS pola SA WSPOLDZIELONE miedzy testami - w odroznieniu OD PER_METHOD.
            sharedCounter++;
            assertTrue(sharedCounter >= 101);
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
