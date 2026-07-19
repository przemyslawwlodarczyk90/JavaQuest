package com.example.javaquest._26_integration_testing.Lesson04_TestcontainersIntroduction;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.DockerClientFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson04_TestcontainersIntroduction {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Wprowadzenie do Testcontainers ===");

        /*
         * ============================================================
         * 📦 Testcontainers - PRAWDZIWE kontenery Docker STEROWANE Z Javy
         * ============================================================
         * Lesson03 pokazal PROBLEM (H2 != produkcja). Testcontainers
         * (biblioteka `org.testcontainers`) ROZWIAZUJE go, URUCHAMIAJAC
         * PRAWDZIWY silnik bazy danych (PostgreSQL/MySQL/MongoDB/...)
         * W KONTENERZE Docker, PROGRAMOWO Z POZIOMU testu Java - BEZ
         * recznego "docker run", BEZ zewnetrznych skryptow. Kontener
         * jest TWORZONY PRZED testem I NISZCZONY PO nim (LUB
         * wielokrotnie uzywany - Lesson06).
         *
         * 🔍 WYMAGANIE: dzialajacy Docker Desktop/Engine NA maszynie.
         * TA lekcja (jak `_06_networking` PRZY braku internetu,
         * `_15_jvm_internals/Lesson11` PRZY braku Shenandoah)
         * SPRAWDZA dostepnosc Dockera I POKAZUJE PRZYJAZNY fallback,
         * GDY jest niedostepny - KOD JEST NAPRAWDE PRAWDZIWY (NIE
         * atrapa), TYLKO opakowany W try/catch NA WYPADEK braku
         * srodowiska.
         */
        System.out.println("Testcontainers = PRAWDZIWY silnik bazy W kontenerze Docker, sterowany z Javy. Wymaga dzialajacego Dockera.");

        boolean dockerAvailable = checkDockerAvailability();

        if (dockerAvailable) {
            demonstratePostgresContainer();
        } else {
            System.out.println("\nDocker NIEDOSTEPNY na tej maszynie - pomijam faktyczne uruchomienie kontenera.");
            System.out.println("PONIZEJ pelny, PRAWDZIWY kod, ktory URUCHOMILBY sie identycznie na maszynie z dzialajacym Dockerem:");
            printExampleCodeForReference();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `new PostgreSQLContainer<>("postgres:16-alpine")` -
         *   DEFINICJA kontenera (obraz Docker, TAG wersji).
         * - `.start()` - FAKTYCZNIE sciaga (jesli trzeba) I uruchamia
         *   kontener - BLOKUJE, AZ baza jest GOTOWA na polaczenia.
         * - `.getJdbcUrl()`/`.getUsername()`/`.getPassword()` -
         *   Testcontainers PRZYDZIELA LOSOWY, WOLNY port - te metody
         *   ZWRACAJA GOTOWY URL polaczenia.
         * - `.stop()` (LUB try-with-resources, `PostgreSQLContainer`
         *   implementuje `AutoCloseable`) - NISZCZY kontener PO
         *   tescie.
         * - `DockerClientFactory.instance().isDockerAvailable()` -
         *   PROGRAMOWE sprawdzenie dostepnosci Dockera (uzyte W tej
         *   lekcji DO fallbacku).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static boolean checkDockerAvailability() {
        try {
            boolean available = DockerClientFactory.instance().isDockerAvailable();
            System.out.println("Docker dostepny: " + available);
            return available;
        } catch (Exception e) {
            System.out.println("Docker NIEDOSTEPNY (wyjatek przy sprawdzaniu): " + e.getClass().getSimpleName());
            return false;
        }
    }

    private static void demonstratePostgresContainer() {
        System.out.println("\n--- Uruchamiam PRAWDZIWY kontener PostgreSQL przez Testcontainers ---");
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")) {
            postgres.start();

            System.out.println("Kontener wystartowal. JDBC URL: " + postgres.getJdbcUrl());

            try (Connection connection = DriverManager.getConnection(
                    postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
                 Statement statement = connection.createStatement()) {

                statement.execute("CREATE TABLE greeting (id INT PRIMARY KEY, message VARCHAR(100))");
                statement.execute("INSERT INTO greeting VALUES (1, 'Witaj z PRAWDZIWEGO PostgreSQL!')");

                try (ResultSet resultSet = statement.executeQuery("SELECT message FROM greeting WHERE id = 1")) {
                    resultSet.next();
                    String message = resultSet.getString("message");
                    assertThat(message).isEqualTo("Witaj z PRAWDZIWEGO PostgreSQL!");
                    System.out.println("Odczytano z PRAWDZIWEGO PostgreSQL (w kontenerze Docker): " + message);
                }
            }
        } catch (Exception e) {
            System.out.println("Nie udalo sie uruchomic kontenera: " + e.getMessage());
        }
    }

    private static void printExampleCodeForReference() {
        System.out.println("""
                try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")) {
                    postgres.start();
                    try (Connection connection = DriverManager.getConnection(
                            postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
                        // ... prawdziwe zapytania SQL na prawdziwym PostgreSQL ...
                    }
                }""");
    }
}
