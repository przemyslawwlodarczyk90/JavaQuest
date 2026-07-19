package com.example.javaquest._25_unit_testing.Lesson14_MockitoArgumentMatchersAndCaptors;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.mockito.ArgumentCaptor;

import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class _Lesson14_MockitoArgumentMatchersAndCaptors {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Mockito - matchery argumentow i ArgumentCaptor ===");

        /*
         * ============================================================
         * 📦 MATCHERY - GDY nie zalezy Ci NA KONKRETNEJ wartosci argumentu
         * ============================================================
         * Lesson13 stubowal/weryfikowal Z KONKRETNYMI wartosciami
         * (`charge("ACC-1", 100.0)`). `anyString()`/`anyDouble()`/
         * `any(Klasa.class)` POZWALAJA dopasowac DOWOLNA wartosc DANEGO
         * typu - przydatne, GDY test NIE dba O KONKRETNY argument, TYLKO
         * O SAM FAKT wywolania Z argumentem TEGO typu.
         *
         * ⚠️ ZELAZNA zasada Mockito: JESLI UZYJESZ CHOC JEDNEGO matchera
         * (`any()`/`eq()`) W wywolaniu, WSZYSTKIE POZOSTALE argumenty
         * TEJ SAMEJ metody TEZ MUSZA byc matcherami (NIE MOZNA mieszac
         * "goldej" wartosci Z matcherem) - inaczej
         * `InvalidUseOfMatchersException`.
         *
         * 🔍 `ArgumentCaptor` - GDY chcesz PRZECHWYCIC argument, Z
         * KTORYM mock ZOSTAL wywolany, I sprawdzic GO OSOBNYMI
         * asercjami (np. sprawdzic POLA obiektu przekazanego DO mocka).
         */
        System.out.println("any()/anyString()/eq() = dopasowanie ELASTYCZNE. ArgumentCaptor = PRZECHWYCENIE argumentu do OSOBNEJ analizy.");

        runAndReport(ArgumentMatchersTest.class);
        runAndReport(ArgumentCaptorTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `anyString()`/`anyInt()`/`anyDouble()`/`any(Klasa.class)` -
         *   dopasowanie DOWOLNEJ wartosci DANEGO typu.
         * - `eq(wartosc)` - JAWNY matcher "dokladnie TA wartosc" (uzyj,
         *   GDY MIESZASZ Z INNYMI matcherami W TYM SAMYM wywolaniu).
         * - `argThat(predykat)` - WLASNY, DOWOLNY warunek dopasowania.
         * - `ArgumentCaptor<T> captor = ArgumentCaptor.forClass(T.class)` +
         *   `verify(mock).metoda(captor.capture())` +
         *   `captor.getValue()` - PRZECHWYCENIE argumentu DO analizy PO
         *   WYWOLANIU.
         * - `captor.getAllValues()` - WSZYSTKIE przechwycone argumenty,
         *   GDY metoda byla wywolana WIELOKROTNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    record EmailMessage(String to, String subject, String body) {
    }

    interface EmailSender {
        void send(EmailMessage message);

        boolean isValidRecipient(String email, double priorityScore);
    }

    static class NotificationService {
        private final EmailSender emailSender;

        NotificationService(EmailSender emailSender) {
            this.emailSender = emailSender;
        }

        void notifyUser(String email, String eventName) {
            EmailMessage message = new EmailMessage(email, "Powiadomienie: " + eventName,
                    "Wystapilo zdarzenie: " + eventName);
            emailSender.send(message);
        }
    }

    static class ArgumentMatchersTest {
        @Test
        void anyStringMatchesAnyArgument() {
            EmailSender senderMock = mock(EmailSender.class);
            when(senderMock.isValidRecipient(anyString(), anyDouble())).thenReturn(true);

            assertThat(senderMock.isValidRecipient("cokolwiek@example.com", 5.0)).isTrue();
            assertThat(senderMock.isValidRecipient("inny@example.com", 99.9)).isTrue();
        }

        @Test
        void eqCombinedWithAnyRequiresEqOnAllArguments() {
            EmailSender senderMock = mock(EmailSender.class);
            // MIESZANIE matchera (anyDouble) Z "gola" wartoscia BYLOBY BLEDEM -
            // DRUGI argument TEZ MUSI byc matcherem (eq(...)).
            when(senderMock.isValidRecipient(eq("vip@example.com"), anyDouble())).thenReturn(true);

            assertThat(senderMock.isValidRecipient("vip@example.com", 1.0)).isTrue();
            assertThat(senderMock.isValidRecipient("inny@example.com", 1.0)).isFalse();
        }

        @Test
        void argThatMatchesCustomPredicate() {
            EmailSender senderMock = mock(EmailSender.class);
            when(senderMock.isValidRecipient(argThat(email -> email != null && email.endsWith("@firma.pl")), anyDouble()))
                    .thenReturn(true);

            assertThat(senderMock.isValidRecipient("jan@firma.pl", 1.0)).isTrue();
            assertThat(senderMock.isValidRecipient("jan@gmail.com", 1.0)).isFalse();
        }

        @Test
        void verifyWithAnyMatcherOnComplexArgument() {
            EmailSender senderMock = mock(EmailSender.class);
            NotificationService service = new NotificationService(senderMock);

            service.notifyUser("user@example.com", "logowanie");

            verify(senderMock, times(1)).send(any(EmailMessage.class));
        }
    }

    static class ArgumentCaptorTest {
        @Test
        void captorCapturesSingleArgumentForDetailedAssertions() {
            EmailSender senderMock = mock(EmailSender.class);
            NotificationService service = new NotificationService(senderMock);

            service.notifyUser("klient@example.com", "zamowienie-zlozone");

            ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
            verify(senderMock).send(captor.capture());

            EmailMessage captured = captor.getValue();
            assertThat(captured.to()).isEqualTo("klient@example.com");
            assertThat(captured.subject()).contains("zamowienie-zlozone");
            assertThat(captured.body()).contains("Wystapilo zdarzenie");
        }

        @Test
        void captorCapturesAllValuesAcrossMultipleCalls() {
            EmailSender senderMock = mock(EmailSender.class);
            NotificationService service = new NotificationService(senderMock);

            service.notifyUser("a@example.com", "event-1");
            service.notifyUser("b@example.com", "event-2");
            service.notifyUser("c@example.com", "event-3");

            ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
            verify(senderMock, times(3)).send(captor.capture());

            List<EmailMessage> allMessages = captor.getAllValues();
            assertThat(allMessages).hasSize(3);
            assertThat(allMessages).extracting(EmailMessage::to)
                    .containsExactly("a@example.com", "b@example.com", "c@example.com");
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
