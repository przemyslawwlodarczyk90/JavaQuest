package com.example.javaquest._10_dao.Lesson13_EnvironmentVariables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class _Lesson13_EnvironmentVariables {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 KROK DALEJ NIŻ PLIK .properties: ZMIENNE ŚRODOWISKOWE
         * ============================================================
         * W poprzedniej lekcji wynieśliśmy dane logowania do pliku
         * .properties - ale taki plik często i tak trafia do
         * repozytorium (albo ktoś o tym zapomni i wrzuci hasło
         * produkcyjne przez pomyłkę). ZMIENNE ŚRODOWISKOWE systemu
         * operacyjnego to kolejny krok: hasło NIGDY nie zapisujemy
         * w ŻADNYM pliku aplikacji - ustawia je administrator/CI/CD
         * bezpośrednio na serwerze, poza kodem i poza repozytorium.
         *
         * 🔹 System.getenv() vs System.getProperty()
         * - System.getenv("NAZWA") czyta zmienną ŚRODOWISKOWĄ systemu
         *   operacyjnego (ustawianą np. w powłoce, Dockerze, ustawieniach
         *   systemu Windows)
         * - System.getProperty("nazwa") czyta WŁAŚCIWOŚĆ samej maszyny
         *   wirtualnej Javy (np. przekazaną flagą -Dnazwa=wartosc) -
         *   to zupełnie inny mechanizm, choć służy do podobnych celów
         */

        System.out.println("=== ODCZYT ISTNIEJĄCEJ ZMIENNEJ ŚRODOWISKOWEJ ===");
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null) {
            System.out.println("JAVA_HOME = " + javaHome);
        } else {
            System.out.println("JAVA_HOME nie jest ustawione w tym środowisku (to normalne, zależy od maszyny)");
        }

        /*
         * ============================================================
         * 🔍 WZORZEC: FALLBACK NA WARTOŚĆ DOMYŚLNĄ
         * ============================================================
         * Na produkcji hasło do bazy ustawia administrator jako
         * zmienną środowiskową (np. DB_PASSWORD=tajneHaslo123). Lokalnie,
         * na komputerze developera, ta zmienna zwykle NIE jest ustawiona
         * - developer pracuje na lokalnej bazie testowej z prostym,
         * jawnym hasłem. Zamiast rzucać wyjątkiem przy braku zmiennej,
         * używamy Map.getOrDefault (albo Optional), żeby aplikacja
         * DZIAŁAŁA lokalnie "od razu po sklonowaniu repozytorium".
         */

        String dbPassword = System.getenv().getOrDefault("DB_PASSWORD_LESSON13", "domyslne_haslo_dev");
        System.out.println("\n=== KONFIGURACJA Z FALLBACKIEM ===");
        System.out.println("Zmienna DB_PASSWORD_LESSON13 ustawiona w systemie? "
                + System.getenv().containsKey("DB_PASSWORD_LESSON13"));
        System.out.println("Hasło użyte do połączenia: "
                + (System.getenv().containsKey("DB_PASSWORD_LESSON13") ? "***Z ZMIENNEJ ŚRODOWISKOWEJ***" : dbPassword));

        /*
         * ============================================================
         * 🔍 UŻYCIE HASŁA (Z FALLBACKIEM) DO POŁĄCZENIA Z H2
         * ============================================================
         * Dla bazy H2 in-memory pierwsze połączenie tworzy bazę i od
         * razu "rejestruje" podane hasło jako hasło użytkownika sa -
         * dlatego to działa niezawodnie niezależnie od tego, czy
         * DB_PASSWORD_LESSON13 jest ustawione w Twoim systemie, czy nie.
         * W PRAWDZIWEJ bazie danych to hasło musiałoby być wcześniej
         * ustawione przez administratora bazy - tutaj tylko demonstrujemy
         * MECHANIZM odczytu konfiguracji, nie administrację bazą danych.
         */
        String url = "jdbc:h2:mem:daolesson13;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(url, "sa", dbPassword)) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE env_demo (id BIGINT AUTO_INCREMENT PRIMARY KEY, info VARCHAR(200))");
                stmt.execute("INSERT INTO env_demo (info) VALUES ('Polaczono przy uzyciu hasla z env/fallbacku')");
            }
            System.out.println("\nPołączenie z bazą nawiązane poprawnie przy użyciu hasła z env/fallbacku.");
        }

        /*
         * ============================================================
         * 🔍 LOKALNIE vs PRODUKCYJNIE
         * ============================================================
         * - LOKALNIE (komputer developera): zmienne środowiskowe często
         *   nieustawione - aplikacja korzysta z bezpiecznych wartości
         *   domyślnych (np. lokalna baza H2/PostgreSQL bez wrażliwych danych)
         * - PRODUKCYJNIE (serwer): zmienne środowiskowe ustawia system
         *   wdrożeniowy (Docker, Kubernetes, CI/CD) - hasła i sekrety
         *   NIGDY nie trafiają do repozytorium kodu ani do plików .properties
         * - Dzięki temu TEN SAM skompilowany kod aplikacji działa
         *   bezpiecznie w obu środowiskach - różni się tylko to, SKĄD
         *   biorą się wartości w danym momencie
         */

        System.out.println("\n=== KILKA PRZYKŁADOWYCH ZMIENNYCH ŚRODOWISKOWYCH SYSTEMU ===");
        Map<String, String> env = System.getenv();
        env.keySet().stream()
                .filter(key -> key.equals("OS") || key.equals("USERNAME") || key.equals("NUMBER_OF_PROCESSORS"))
                .sorted()
                .forEach(key -> System.out.println(key + " = " + env.get(key)));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zmienne środowiskowe (System.getenv) trzymają konfigurację
         *   POZA kodem i POZA repozytorium - ustawia je system operacyjny,
         *   Docker albo narzędzie CI/CD, a nie deweloper w pliku
         * - Wzorzec System.getenv().getOrDefault("KLUCZ", "wartoscDomyslna")
         *   pozwala aplikacji działać LOKALNIE "od razu", a produkcyjnie
         *   korzystać z prawdziwych, bezpiecznych wartości
         * - System.getenv() (zmienne systemu) i System.getProperty()
         *   (właściwości JVM, np. -Dklucz=wartosc) to DWA różne mechanizmy
         * - Konfiguracja lokalna (developerska) i produkcyjna różnią się
         *   ŹRÓDŁEM wartości, ale kod aplikacji pozostaje IDENTYCZNY
         */
    }
}
