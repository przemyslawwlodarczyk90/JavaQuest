package com.example.javaquest._26_integration_testing.Lesson06_TestcontainersLifecycleAndReuse;

import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

public class _Lesson06_TestcontainersLifecycleAndReuse {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: Cykl zycia kontenerow Testcontainers i ponowne uzycie ===");

        /*
         * ============================================================
         * 📦 3 STRATEGIE cyklu zycia kontenera - KOMPROMIS SZYBKOSC vs IZOLACJA
         * ============================================================
         * 1. KONTENER NA KAZDY TEST (`start()` W `@BeforeEach`,
         *    `stop()` W `@AfterEach`) - MAKSYMALNA izolacja (KAZDY
         *    test dostaje CALKOWICIE SWIEZA baze), ALE NAJWOLNIEJSZE
         *    (koszt startu * liczba testow).
         * 2. KONTENER NA KLASE testowa (statyczne pole, `@Container`
         *    + `@Testcontainers` W realnym JUnit 5, TU zasymulowane
         *    reczna metoda statyczna) - JEDEN start DLA WSZYSTKICH
         *    testow W klasie - SZYBSZE, ALE testy MOGA "widziec"
         *    dane POZOSTAWIONE przez POPRZEDNI test (Lesson11:
         *    izolacja WYMAGA wtedy recznego czyszczenia MIEDZY
         *    testami).
         * 3. "SINGLETON CONTAINER" (`withReuse(true)` +
         *    `testcontainers.reuse.enable=true` W
         *    `~/.testcontainers.properties`) - JEDEN kontener
         *    WSPOLDZIELONY MIEDZY WIELOMA KLASAMI testowymi W CALYM
         *    URUCHOMIENIU (NIE zatrzymywany automatycznie PO
         *    tescie/klasie) - NAJSZYBSZE DLA duzych pakietow testow,
         *    ALE WYMAGA jawnego wlaczenia I recznej dyscypliny
         *    czyszczenia danych.
         */
        System.out.println("3 strategie: kontener NA TEST (max izolacja, wolne), NA KLASE (kompromis), SINGLETON/reuse (najszybsze, wymaga dyscypliny sprzatania).");

        if (isDockerAvailable()) {
            demonstratePerClassLifecycle();
            demonstrateReuseFlag();
        } else {
            System.out.println("\nDocker NIEDOSTEPNY - pomijam faktyczne uruchomienie, ale wzorce ponizej sa PRAWDZIWYM kodem.");
            printReferencePatterns();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kontener NA test: `new PostgreSQLContainer<>(...)` W
         *   metodzie testowej/`@BeforeEach` - WOLNE, ALE ZERO
         *   ryzyka "przeciekania" danych miedzy testami.
         * - Kontener NA klase: `static final PostgreSQLContainer<?>`
         *   uruchomiony RAZ (JUnit 5: `@Testcontainers` + `@Container
         *   static`) - test MUSI SAM zadbac O CZYSZCZENIE danych
         *   miedzy metodami (np. `TRUNCATE TABLE` W `@BeforeEach`).
         * - `withReuse(true)`: kontener PRZEZYWA NAWET zakonczenie
         *   JVM (NASTEPNE uruchomienie testow ZNAJDZIE go I UZYJE
         *   PONOWNIE zamiast tworzyc NOWY) - WYMAGA jawnej zgody W
         *   `~/.testcontainers.properties` (`testcontainers.reuse.
         *   enable=true`) - BEZPIECZENSTWO: NIE jest domyslnie
         *   wlaczone, bo kontener MOZE "przeciekac" dane MIEDZY
         *   ZUPELNIE NIEZWIAZANYMI uruchomieniami testow.
         * - REKOMENDACJA: zacznij OD "kontener na klase" (dobry
         *   kompromis) - siegnij PO `reuse` TYLKO PRZY realnym
         *   problemie Z czasem CALEGO pakietu testow.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static boolean isDockerAvailable() {
        try {
            return DockerClientFactory.instance().isDockerAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    /** Symulacja wzorca "kontener na klase" - JEDEN start, WIELE "testow" go uzywajacych. */
    private static void demonstratePerClassLifecycle() {
        System.out.println("\n--- Strategia: kontener URUCHOMIONY RAZ, wspoldzielony przez wiele 'testow' ---");
        try (PostgreSQLContainer<?> sharedContainer = new PostgreSQLContainer<>("postgres:16-alpine")) {
            long start = System.currentTimeMillis();
            sharedContainer.start();
            long startupMillis = System.currentTimeMillis() - start;
            System.out.println("Kontener wystartowal RAZ w " + startupMillis + " ms.");

            for (int testNumber = 1; testNumber <= 3; testNumber++) {
                simulateTestUsingContainer(sharedContainer, testNumber);
            }
            System.out.println("WSZYSTKIE 3 'testy' uzyly TEGO SAMEGO kontenera - TYLKO 1 koszt startu.");
        } catch (Exception e) {
            System.out.println("Blad: " + e.getMessage());
        }
    }

    private static void simulateTestUsingContainer(PostgreSQLContainer<?> container, int testNumber) {
        try (var connection = java.sql.DriverManager.getConnection(
                container.getJdbcUrl(), container.getUsername(), container.getPassword());
             var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS visits (id SERIAL PRIMARY KEY, test_number INT)");
            statement.execute("INSERT INTO visits (test_number) VALUES (" + testNumber + ")");
            System.out.println("  'test " + testNumber + "' zapisal wiersz do WSPOLDZIELONEJ tabeli.");
        } catch (Exception e) {
            System.out.println("  Blad w tescie " + testNumber + ": " + e.getMessage());
        }
    }

    private static void demonstrateReuseFlag() {
        System.out.println("\n--- Flaga withReuse(true) - kontener PRZEZYWA zakonczenie JVM (WYMAGA jawnej zgody) ---");
        PostgreSQLContainer<?> reusableContainer = new PostgreSQLContainer<>("postgres:16-alpine")
                .withReuse(true);
        System.out.println("skonfigurowano withReuse(true) - BEZ wlaczonej flagi 'testcontainers.reuse.enable=true' " +
                "w ~/.testcontainers.properties, Testcontainers I TAK potraktuje kontener jako jednorazowy " +
                "(bezpieczny domyslny fallback) - NIE uruchamiamy go tutaj, zeby NIE zostawic 'wiszacego' " +
                "kontenera po tej lekcji.");
    }

    private static void printReferencePatterns() {
        System.out.println("""
                // Kontener NA KLASE (JUnit 5, realny kod):
                @Testcontainers
                class ProductRepositoryIT {
                    @Container
                    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

                    @BeforeEach
                    void cleanTable() { /* TRUNCATE TABLE products */ }
                }

                // Reuse MIEDZY uruchomieniami (wymaga ~/.testcontainers.properties: testcontainers.reuse.enable=true):
                new PostgreSQLContainer<>("postgres:16-alpine").withReuse(true).start();""");
    }
}
