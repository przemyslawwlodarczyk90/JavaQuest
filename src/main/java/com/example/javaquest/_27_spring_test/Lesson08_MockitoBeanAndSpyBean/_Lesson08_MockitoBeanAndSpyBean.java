package com.example.javaquest._27_spring_test.Lesson08_MockitoBeanAndSpyBean;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class _Lesson08_MockitoBeanAndSpyBean {

    public static void main(String[] args) {

        // @MockitoSpyBean NA KONKRETNEJ klasie (VisitCounter, ponizej) wymaga retransformacji
        // bajtkodu przez Byte Buddy - na JDK 25 bez tej flagi konczy sie bledem "Java 25 is not
        // supported" (ta sama pulapka co _25_unit_testing/Lesson16 - patrz CLAUDE.md).
        System.setProperty("net.bytebuddy.experimental", "true");

        System.out.println("=== LEKCJA 8: @MockitoBean i @MockitoSpyBean ===");

        /*
         * ============================================================
         * 📦 @MockitoBean/@MockitoSpyBean - ZASTEPUJE PRAWDZIWY bean W kontekscie Springa MOCKIEM/SPY
         * ============================================================
         * `_25_unit_testing/Lesson13-15` uczyl `mock(...)`/`spy(...)`
         * BEZ Springa - obiekt byl RECZNIE wstrzykiwany DO klasy pod
         * testem. `@MockitoBean` idzie DALEJ: PODMIENIA bean W
         * PRAWDZIWYM `ApplicationContext` Springa NA mocka - INNE
         * beany ZALEZNE OD niego (np. kontroler Z Lesson05)
         * OTRZYMUJA AUTOMATYCZNIE ten mock PRZEZ Dependency Injection,
         * BEZ zadnej recznej "sklejki".
         *
         * ⚠️ WAZNA HISTORIA WERSJI (powiazanie Z `_20_spring_core/
         * Lesson02`): `@MockBean`/`@SpyBean` (starsze, W pakiecie
         * `org.springframework.boot.test.mock.mockito`) SA
         * DEPRECATED OD Spring Boot **3.4.0** (do usuniecia W 4.0.0),
         * ZASTAPIONE PRZEZ `@MockitoBean`/`@MockitoSpyBean` (pakiet
         * `org.springframework.test.context.bean.override.mockito`,
         * dostepne OD Boot 3.2) - TEN kurs (Boot 3.4.4) UCZY OD RAZU
         * NOWEGO API.
         */
        System.out.println("@MockitoBean = PODMIENIA prawdziwy bean W kontekscie Springa NA mocka. @MockBean/@SpyBean (stare) SA DEPRECATED od Boot 3.4.0.");

        runAndReport(NotificationServiceTest.class);
        runAndReport(VisitCounterSpyTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@MockitoBean EmailSender emailSender;` W POLU testowym -
         *   Spring PODMIENIA PRAWDZIWY bean `EmailSender` NA mocka W
         *   CALYM kontekscie.
         * - `@MockitoSpyBean` - jak `@MockitoBean`, ALE OWIJA
         *   PRAWDZIWY bean (analogia Z `spy(...)` Z
         *   `_25_unit_testing/Lesson16`) - PRAWDZIWA logika DZIALA,
         *   `verify(...)` NADAL mozliwe.
         * - RÓZNICA WZGLEDEM `@MockitoBean` W `@WebMvcTest` (Lesson05):
         *   TAM mock byl JEDYNYM sposobem dostarczenia zaleznosci
         *   (bo `@WebMvcTest` NIE laduje serwisow); TU (`@SpringBootTest`)
         *   PODMIENIAMY SWIADOMIE PRAWDZIWY, ISTNIEJACY bean.
         * - `@MockitoBean` DZIALA TEZ NA `@DataJpaTest`/`@JsonTest`
         *   (Lesson06-07) - IDENTYCZNA mechanika WSZEDZIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    interface EmailSender {
        void send(String to, String message);
    }

    static class RealEmailSender implements EmailSender {
        @Override
        public void send(String to, String message) {
            throw new UnsupportedOperationException("Prawdziwe wysylanie email NIE MOZE dzialac w testach - dlatego uzywamy @MockitoBean.");
        }
    }

    static class NotificationService {
        private final EmailSender emailSender;

        NotificationService(EmailSender emailSender) {
            this.emailSender = emailSender;
        }

        void notifyUser(String email, String eventName) {
            emailSender.send(email, "Wydarzenie: " + eventName);
        }
    }

    @SpringBootApplication
    static class TestApp {
        @Bean
        EmailSender emailSender() {
            return new RealEmailSender();
        }

        @Bean
        NotificationService notificationService(EmailSender emailSender) {
            return new NotificationService(emailSender);
        }
    }

    @SpringBootTest(classes = TestApp.class)
    static class NotificationServiceTest {
        @MockitoBean
        EmailSender emailSender;

        @Autowired
        NotificationService notificationService;

        @Test
        void mockitoBeanReplacesRealBeanThrowingUnsupportedOperation() {
            // BEZ @MockitoBean, RealEmailSender.send(...) RZUCILBY UnsupportedOperationException -
            // @MockitoBean PODMIENILO go NA mocka, wiec zadne PRAWDZIWE wysylanie NIE nastepuje.
            notificationService.notifyUser("kursant@example.com", "logowanie");

            verify(emailSender).send("kursant@example.com", "Wydarzenie: logowanie");
            System.out.println("@MockitoBean POPRAWNIE podmienilo RealEmailSender w calym kontekscie Springa - verify() potwierdzil wywolanie.");
        }

        @Test
        void mockitoBeanCanBeStubbedLikeAnyOtherMock() {
            // emailSender W kontekscie testowym jest TERAZ mockiem Mockito (nie RealEmailSender) -
            // wywolanie send(...) NIE RZUCA juz UnsupportedOperationException, bo mock domyslnie
            // NIC nie robi (void metoda) - dowod, ze podmiana FAKTYCZNIE zaszla.
            notificationService.notifyUser("test@example.com", "rejestracja");
            verify(emailSender).send("test@example.com", "Wydarzenie: rejestracja");
            System.out.println("Wywolanie NIE rzucilo wyjatku RealEmailSender - potwierdzenie, ze @MockitoBean jest AKTYWNY.");
        }
    }

    static class VisitCounter {
        private int visits = 0;

        int registerVisit() {
            visits++;
            return visits;
        }
    }

    @SpringBootApplication
    static class SpyTestApp {
        @Bean
        VisitCounter visitCounter() {
            return new VisitCounter();
        }
    }

    @SpringBootTest(classes = SpyTestApp.class)
    static class VisitCounterSpyTest {
        @MockitoSpyBean
        VisitCounter visitCounter;

        @Test
        void spyBeanExecutesRealLogicWhileAllowingVerification() {
            // @MockitoSpyBean OWIJA PRAWDZIWY bean - visits FAKTYCZNIE sie inkrementuje
            // (PRAWDZIWA logika), ALE mozemy TEZ zweryfikowac interakcje jak na mocku.
            int first = visitCounter.registerVisit();
            int second = visitCounter.registerVisit();

            assertThat(first).isEqualTo(1);
            assertThat(second).isEqualTo(2);
            verify(visitCounter, org.mockito.Mockito.times(2)).registerVisit();
            System.out.println("@MockitoSpyBean: PRAWDZIWA logika liczenia zadzialala (1, 2) I verify() potwierdzil 2 wywolania.");
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
