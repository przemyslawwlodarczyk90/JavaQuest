package com.example.javaquest._26_integration_testing.Lesson12_FlakyTestsAndHowToFixThem;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson12_FlakyTestsAndHowToFixThem {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: Testy 'flaky' - przyczyny i naprawy ===");

        /*
         * ============================================================
         * 📦 "FLAKY" test = ten sam kod, RAZ przechodzi, RAZ nie - BEZ zmiany w kodzie produkcyjnym
         * ============================================================
         * 5 NAJCZESTSZYCH przyczyn (I NAPRAW ponizej, KAZDA Z DZIALAJACYM
         * demo): (1) sztywny `Thread.sleep` zamiast pollingu, (2)
         * ZALEZNOSC OD "biezacego" czasu systemowego (`Instant.now()`
         * BEZ wstrzykiwalnego zegara), (3) BRAK retry PRZY
         * NATURALNIE niestabilnej operacji (siec), (4) ZALEZNOSC OD
         * kolejnosci wykonania testow (Lesson11), (5) WSPOLDZIELONY
         * ZASOB (port/plik) MIEDZY ROWNOLEGLYMI testami.
         */
        System.out.println("5 przyczyn 'flaky': sztywny sleep, zaleznosc od czasu 'teraz', brak retry, kolejnosc testow, wspoldzielony zasob.");

        demonstratePollingInsteadOfSleep();
        demonstrateInjectableClockInsteadOfNow();
        demonstrateRetryForNaturallyUnstableOperation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Thread.sleep(stalyCzas)` -> POLLING Z TIMEOUTEM
         *   (`awaitUntil(warunek, timeout)` - powtorzone Z Lesson02,
         *   BO to NAJCZESTSZA przyczyna "flaky" W praktyce).
         * - `Instant.now()`/`System.currentTimeMillis()` W LOGICE
         *   produkcyjnej -> WSTRZYKIWALNY `Clock` (`java.time.Clock`,
         *   `Clock.fixed(...)` W testach) - DETERMINIZM zamiast
         *   zaleznosci OD prawdziwego zegara.
         * - Operacja NATURALNIE niestabilna (siec/zewnetrzne API) ->
         *   RETRY Z LIMITEM prob (NIE nieskonczony) - powiazanie Z
         *   `_25_unit_testing/Lesson13` Zadanie 22.
         * - Biblioteka "Awaitility" (`org.awaitility:awaitility`) TO
         *   PRODUKCYJNY, gotowy odpowiednik `awaitUntil(...)` Z tej
         *   lekcji - warto ZNAC, GDY projekt NA NIA pozwala.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    /** WLASNY, uproszczony odpowiednik biblioteki Awaitility - POLLING Z TIMEOUTEM zamiast sleep. */
    private static void awaitUntil(BooleanSupplier condition, Duration timeout) throws InterruptedException {
        Instant deadline = Instant.now().plus(timeout);
        while (!condition.getAsBoolean()) {
            if (Instant.now().isAfter(deadline)) {
                throw new AssertionError("Warunek nie zostal spelniony w limicie czasu " + timeout);
            }
            Thread.sleep(10);
        }
    }

    private static void demonstratePollingInsteadOfSleep() throws Exception {
        System.out.println("\n--- Naprawa 1: POLLING zamiast sztywnego Thread.sleep(staly czas) ---");
        AtomicInteger backgroundResult = new AtomicInteger(-1);

        Thread backgroundWork = new Thread(() -> {
            try {
                // Symulacja OPERACJI o ZMIENNYM czasie trwania (np. wiadomosc w kolejce, zapis do bazy).
                Thread.sleep(20 + (int) (Math.random() * 60));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            backgroundResult.set(42);
        });
        backgroundWork.setDaemon(true);
        backgroundWork.start();

        // ZAMIAST zgadywac "wystarczajaco dlugi" sleep, CZEKAMY DOKLADNIE tyle, ile TRZEBA.
        awaitUntil(() -> backgroundResult.get() != -1, Duration.ofSeconds(2));
        assertThat(backgroundResult.get()).isEqualTo(42);
        System.out.println("Polling POCZEKAL DOKLADNIE tyle, ile bylo trzeba - test STABILNY NIEZALEZNIE OD predkosci watku w tle.");
    }

    static class SubscriptionService {
        private final java.time.Clock clock;

        SubscriptionService(java.time.Clock clock) {
            this.clock = clock;
        }

        boolean isExpired(Instant subscriptionEndsAt) {
            return clock.instant().isAfter(subscriptionEndsAt);
        }
    }

    private static void demonstrateInjectableClockInsteadOfNow() {
        System.out.println("\n--- Naprawa 2: wstrzykiwalny Clock zamiast Instant.now() 'na sztywno' ---");
        // ZLY wzorzec (KOMENTARZ): metoda produkcyjna wywolujaca `Instant.now()` BEZPOSREDNIO
        // W SRODKU jest NIETESTOWALNA deterministycznie - wynik ZALEZY OD "biezacej" chwili.

        // DOBRY wzorzec: `Clock` PRZEKAZANY z zewnatrz - test uzywa `Clock.fixed(...)`,
        // wiec WYNIK jest ZAWSZE TAKI SAM, NIEZALEZNIE OD tego, KIEDY test sie uruchomil.
        Instant fixedNow = Instant.parse("2026-01-15T12:00:00Z");
        java.time.Clock fixedClock = java.time.Clock.fixed(fixedNow, java.time.ZoneOffset.UTC);
        SubscriptionService service = new SubscriptionService(fixedClock);

        Instant subscriptionEndsInPast = Instant.parse("2026-01-10T00:00:00Z");
        Instant subscriptionEndsInFuture = Instant.parse("2026-02-01T00:00:00Z");

        assertThat(service.isExpired(subscriptionEndsInPast)).isTrue();
        assertThat(service.isExpired(subscriptionEndsInFuture)).isFalse();
        System.out.println("Test Z Clock.fixed(...) daje IDENTYCZNY wynik DZISIAJ, JUTRO I ZA ROK - ZERO zaleznosci OD 'teraz'.");
    }

    /** Symuluje operacje sieciowa, ktora zawodzi 2 pierwsze proby, a 3-cia sie udaje. */
    static class FlakyRemoteCall {
        private int attemptCount = 0;

        String call() {
            attemptCount++;
            if (attemptCount < 3) {
                throw new RuntimeException("Symulowany chwilowy blad sieci (proba " + attemptCount + ")");
            }
            return "Sukces po " + attemptCount + " probach";
        }
    }

    private static void demonstrateRetryForNaturallyUnstableOperation() {
        System.out.println("\n--- Naprawa 3: RETRY Z LIMITEM dla operacji NATURALNIE niestabilnej ---");
        FlakyRemoteCall remoteCall = new FlakyRemoteCall();

        String result = callWithRetry(remoteCall::call, 5);
        assertThat(result).isEqualTo("Sukces po 3 probach");
        System.out.println("callWithRetry POPRAWNIE ponowil wywolanie AZ DO sukcesu (limit 5, potrzebne 3).");
    }

    private static String callWithRetry(Supplier<String> operation, int maxAttempts) {
        RuntimeException lastError = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return operation.get();
            } catch (RuntimeException e) {
                lastError = e;
            }
        }
        throw lastError;
    }
}
