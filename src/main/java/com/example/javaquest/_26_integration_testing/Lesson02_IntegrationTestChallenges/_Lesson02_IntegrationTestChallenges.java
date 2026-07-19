package com.example.javaquest._26_integration_testing.Lesson02_IntegrationTestChallenges;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.net.httpserver.HttpServer;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson02_IntegrationTestChallenges {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: Wyzwania testow integracyjnych ===");

        /*
         * ============================================================
         * 📦 4 GLOWNE wyzwania - dlaczego testy integracyjne SA "trudniejsze"
         * ============================================================
         * 1. SZYBKOSC - test integracyjny zawsze robi WIECEJ (start
         *    bazy/serwera, prawdziwe I/O) - SETKI takich testow
         *    ZNACZACO SPOWALNIAJA build (powiazanie Z Lesson01).
         * 2. NIESTABILNOSC (flaky) - CZAS, KOLEJNOSC, WSPOLDZIELONY
         *    stan MIEDZY testami MOGA sprawic, ze TEN SAM test RAZ
         *    przechodzi, RAZ NIE (zapowiedz Lesson12).
         * 3. ZLOZONOSC setup/teardown - trzeba URUCHOMIC I
         *    ZATRZYMAC prawdziwe zasoby (serwer/baza/plik) W
         *    WLASCIWEJ kolejnosci, NAWET gdy test ZAWIEDZIE (`finally`/
         *    try-with-resources - powiazanie Z `_04_io/Lesson13`).
         * 4. IZOLACJA - testy NIE MOGA "widziec" swoich WZAJEMNYCH
         *    danych (WSPOLDZIELONA baza/plik MIEDZY testami = ZRODLO
         *    trudnych DO zdiagnozowania bledow - zapowiedz Lesson11).
         */
        System.out.println("4 wyzwania: SZYBKOSC, NIESTABILNOSC (flaky), ZLOZONOSC setup/teardown, IZOLACJA miedzy testami.");

        demonstrateSpeedChallenge();
        demonstrateFlakinessChallenge();
        demonstrateSetupTeardownComplexity();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SZYBKOSC: URUCHAMIAJ testy integracyjne RZADZIEJ NIZ
         *   jednostkowe (np. TYLKO przed mergem - powiazanie Z
         *   `_25_unit_testing/Lesson12_Tag`+Lesson14_CiCdIntegration).
         * - NIESTABILNOSC: UNIKAJ `Thread.sleep(staleCzas)` DO
         *   "czekania AZ cos sie stanie" - uzywaj POLLINGU Z
         *   TIMEOUTEM (zapowiedz Lesson12).
         * - SETUP/TEARDOWN: ZAWSZE `try/finally` LUB `@AfterEach` -
         *   NIGDY nie zostawiaj "wiszacego" zasobu PRZY zawiedzionym
         *   tescie (Lesson01 przyklady WSZYSTKIE uzywaly try/finally).
         * - IZOLACJA: KAZDY test POWINIEN dzialac NA WLASNYCH danych
         *   (unikalne ID/prefiks, LUB SWIEZA baza NA test - Testcontainers
         *   Lesson04-06 ROZWIAZUJE TO "z automatu" PRZEZ swiezy
         *   kontener NA test/klase).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateSpeedChallenge() {
        System.out.println("\n--- Wyzwanie 1: SZYBKOSC - integracyjny jest ZAWSZE wolniejszy ---");
        long unitLikeStart = System.nanoTime();
        int sum = 2 + 2; // "test jednostkowy" - czysta logika, BEZ I/O.
        long unitLikeMicros = (System.nanoTime() - unitLikeStart) / 1_000;

        long integrationLikeStart = System.nanoTime();
        try {
            // Symulacja "prawdziwej" zaleznosci (np. polaczenie sieciowe) - CELOWE opoznienie.
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long integrationLikeMillis = (System.nanoTime() - integrationLikeStart) / 1_000_000;

        System.out.println("\"test jednostkowy\" (2+2=" + sum + "): " + unitLikeMicros + " mikrosekund");
        System.out.println("\"test integracyjny\" (symulowane I/O): " + integrationLikeMillis + " milisekund");
        System.out.println("Roznica rzedu wielkosci - PRZY SETKACH testow integracyjnych, build MOZE trwac MINUTY zamiast SEKUND.");
    }

    private static void demonstrateFlakinessChallenge() throws Exception {
        System.out.println("\n--- Wyzwanie 2: NIESTABILNOSC (flaky) - zle napisany test asynchronicznej operacji ---");
        AtomicInteger backgroundCounter = new AtomicInteger(0);

        // Symulujemy operacje ASYNCHRONICZNA (np. wiadomosc W kolejce - powiazanie Z
        // `_30_spring_messaging_and_async`) - inny watek AKTUALIZUJE licznik PO opoznieniu.
        Thread backgroundTask = new Thread(() -> {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            backgroundCounter.incrementAndGet();
        });
        backgroundTask.setDaemon(true);
        backgroundTask.start();

        // ZLY wzorzec (CELOWO pokazany, ZAKOMENTOWANY): natychmiastowe sprawdzenie
        // "assertThat(backgroundCounter.get()).isEqualTo(1)" ZAWIODLOBY, bo watek W TLE
        // jeszcze NIE ZDAZYL zaktualizowac licznika - to KLASYCZNY "flaky test".
        System.out.println("Natychmiast po starcie watku: licznik = " + backgroundCounter.get() + " (MOZE byc 0 - 'flaky' jesli sprawdzone TERAZ)");

        // DOBRY wzorzec - POLLING Z TIMEOUTEM zamiast sztywnego Thread.sleep(staleCzas)
        // (peLNA implementacja tego wzorca - zapowiedz Lesson12_FlakyTestsAndHowToFixThem).
        long deadline = System.currentTimeMillis() + 2000;
        while (backgroundCounter.get() != 1 && System.currentTimeMillis() < deadline) {
            Thread.sleep(5);
        }
        assertThat(backgroundCounter.get()).isEqualTo(1);
        System.out.println("PO pollingu Z timeoutem: licznik = " + backgroundCounter.get() + " (STABILNE, NIEZALEZNIE od predkosci maszyny)");
    }

    private static void demonstrateSetupTeardownComplexity() throws Exception {
        System.out.println("\n--- Wyzwanie 3: ZLOZONOSC setup/teardown - WIELE zasobow W WLASCIWEJ kolejnosci ---");
        HttpServer server = null;
        try {
            // SETUP - kazdy kolejny krok MOZE sie NIE UDAC, wiec KAZDY WYMAGA sprzatniecia
            // WSZYSTKICH poprzednio otwartych zasobow (nie TYLKO ostatniego).
            server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
            server.createContext("/ping", exchange -> {
                byte[] response = "pong".getBytes();
                exchange.sendResponseHeaders(200, response.length);
                try (OutputStream body = exchange.getResponseBody()) {
                    body.write(response);
                }
            });
            server.start();
            int port = server.getAddress().getPort();

            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/ping")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.body()).isEqualTo("pong");
            System.out.println("Test wykonany POPRAWNIE, odpowiedz: " + response.body());
        } finally {
            // TEARDOWN - MUSI sie wykonac NAWET jesli powyzej wystapil wyjatek, inaczej
            // port ZOSTAJE zajety, a KOLEJNE testy W TYM SAMYM procesie moga sie WYSYPAC.
            if (server != null) {
                server.stop(0);
                System.out.println("Serwer POPRAWNIE zatrzymany W bloku finally (NAWET gdyby test zawiodl wczesniej).");
            }
        }
    }
}
