package com.example.javaquest._10_dao.Lesson27_SqlLogging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson27_SqlLogging {

    // Prog czasowy (w milisekundach), powyzej ktorego oznaczamy zapytanie
    // jako "wolne" w logu - w prawdziwym projekcie taki prog konfiguruje
    // sie np. przez plik wlasciwosci, tutaj to stala dla prostoty demo.
    private static final long SLOW_QUERY_THRESHOLD_MS = 100;

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PO CO LOGOWAĆ ZAPYTANIA SQL?
         * ============================================================
         * Gdy aplikacja działa na produkcji i użytkownik zgłasza "strona
         * ładuje się wolno" albo "widzę złe dane", pierwsze pytanie
         * zawsze brzmi: JAKIE dokładnie zapytanie SQL zostało wykonane,
         * z JAKIMI parametrami, i ILE to trwało? Bez logowania tych
         * informacji jesteśmy zdani na zgadywanie. Dobre logowanie SQL
         * pozwala:
         * - zobaczyć DOKŁADNY tekst zapytania (czy na pewno wygenerował
         *   się taki SQL, jakiego się spodziewaliśmy - szczególnie ważne
         *   przy dynamicznym SQL z Lesson23/24),
         * - zobaczyć DOKŁADNE wartości parametrów (czy `?` zostały
         *   zbindowane z właściwymi danymi),
         * - zmierzyć CZAS wykonania KAŻDEGO zapytania osobno - kluczowe
         *   przy diagnozowaniu, KTÓRE dokładnie zapytanie jest "wąskim
         *   gardłem" (bottleneck) całej operacji.
         */

        String url = "jdbc:h2:mem:daolesson27;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);

            /*
             * ============================================================
             * 🔹 WRAPPER "executeLogged" - JEDNOLITY FORMAT LOGU
             * ============================================================
             * Zamiast ręcznie dopisywać `System.out.println` w każdej
             * metodzie DAO, opakowujemy WYKONANIE zapytania w jedną
             * wspólną metodę pomocniczą - `executeLogged`. Loguje ona,
             * w JEDNYM, ujednoliconym formacie:
             *   [SQL] <tresc zapytania> | params=[...] | time=Xms | rows=Y
             * niezależnie OD TEGO, jakie konkretnie zapytanie wykonujemy.
             * Dzięki temu KAŻDE zapytanie w aplikacji jest logowane
             * identycznie - łatwo je potem przeszukiwać/filtrować w
             * logach produkcyjnych.
             */

            System.out.println("=== Kilka SZYBKICH zapytan ===");
            executeLogged(connection, "INSERT INTO products (name, price) VALUES (?, ?)", "Klawiatura", 199.99);
            executeLogged(connection, "INSERT INTO products (name, price) VALUES (?, ?)", "Mysz", 89.50);
            executeLogged(connection, "SELECT * FROM products WHERE price < ?", 300.00);
            executeLogged(connection, "UPDATE products SET price = ? WHERE name = ?", 179.99, "Klawiatura");

            /*
             * ============================================================
             * 🔍 SYMULACJA "WOLNEGO" ZAPYTANIA
             * ============================================================
             * W prawdziwej aplikacji zapytanie bywa wolne z powodu braku
             * indeksu, dużej tabeli, złego planu wykonania albo
             * zablokowanych zasobów bazy. Żeby DETERMINISTYCZNIE
             * zademonstrować, że nasz pomiar czasu faktycznie WYKRYWA
             * różnicę, symulujemy "wolne" zapytanie krótkim, ograniczonym
             * opóźnieniem (Thread.sleep) WEWNĄTRZ metody DAO - w realnym
             * kodzie tego oczywiście by nie było, tu służy wyłącznie do
             * tego, by demo dawało powtarzalny wynik.
             */

            System.out.println("\n=== Jedno CELOWO WOLNE zapytanie (symulacja) ===");
            executeSlowLogged(connection, "SELECT * FROM products WHERE name = ?", "Mysz");

            System.out.println("\n=== Podsumowanie: log wykryl wolne zapytanie na podstawie progu "
                    + SLOW_QUERY_THRESHOLD_MS + "ms ===");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Logowanie SQL = zapisywanie treści zapytania, wartości
         *   parametrów i czasu wykonania - w JEDNOLITYM formacie, w
         *   jednym, wspólnym miejscu (np. metoda `executeLogged`).
         * - Pomiar czasu: `System.nanoTime()` PRZED i PO wykonaniu
         *   zapytania (nanoTime jest odporne na zmiany zegara systemowego,
         *   w przeciwieństwie do currentTimeMillis - dobre do mierzenia
         *   UPŁYWU czasu).
         * - Ustalony PRÓG czasowy (tu: 100ms) pozwala automatycznie
         *   oznaczać w logu zapytania "podejrzanie wolne" - ułatwia to
         *   przeszukiwanie logów produkcyjnych pod kątem wąskich gardeł.
         * - W prawdziwym projekcie taki wrapper zwykle zastępuje się
         *   gotowym narzędziem (np. logger SQL wbudowany w framework typu
         *   Hibernate, albo appender p6spy) - mechanizm pod spodem jest
         *   jednak DOKŁADNIE taki, jak w tej lekcji.
         */
    }

    /**
     * Wykonuje zapytanie SQL (SELECT albo modyfikujące) logując do
     * System.out: treść SQL, parametry i czas wykonania w ms. Zwraca
     * liczbę wierszy (dla SELECT: liczbę zwróconych wierszy, dla
     * INSERT/UPDATE/DELETE: liczbę zmienionych wierszy).
     */
    private static int executeLogged(Connection connection, String sql, Object... params) throws SQLException {
        return executeTimed(connection, sql, 0, params);
    }

    /**
     * Wariant "executeLogged" z dodanym sztucznym opóźnieniem - symuluje
     * wolne zapytanie (brakujący indeks, duża tabela...), żeby pokazać,
     * że pomiar czasu i próg SLOW_QUERY_THRESHOLD_MS faktycznie działają.
     * Opóźnienie jest CELOWO wliczone w mierzony czas (dzieje się PO
     * starcie pomiaru) - inaczej pomiar wcale by go nie wykrył.
     */
    private static int executeSlowLogged(Connection connection, String sql, Object... params) throws SQLException {
        return executeTimed(connection, sql, 150, params);
    }

    /**
     * Wspólny rdzeń pomiaru i logowania - opcjonalne `artificialDelayMs`
     * (symulacja wolnej operacji, np. brakujący indeks) wykonuje się PO
     * starcie stopera, więc wliczy się do zmierzonego czasu.
     */
    private static int executeTimed(Connection connection, String sql, long artificialDelayMs, Object... params) throws SQLException {
        long startNanos = System.nanoTime();

        if (artificialDelayMs > 0) {
            try {
                Thread.sleep(artificialDelayMs); // symulacja "wolnej" operacji - ograniczone, krotkie opoznienie
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int rowCount;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            boolean isSelect = sql.trim().toUpperCase().startsWith("SELECT");
            if (isSelect) {
                try (ResultSet rs = statement.executeQuery()) {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                    }
                    rowCount = count;
                }
            } else {
                rowCount = statement.executeUpdate();
            }
        }

        long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;
        logLine(sql, params, elapsedMs, rowCount);
        return rowCount;
    }

    private static void logLine(String sql, Object[] params, long elapsedMs, int rowCount) {
        String paramsText = java.util.Arrays.toString(params);
        String warning = elapsedMs > SLOW_QUERY_THRESHOLD_MS ? "  <-- ⚠️ WOLNE ZAPYTANIE (powyzej "
                + SLOW_QUERY_THRESHOLD_MS + "ms)" : "";
        System.out.println("[SQL] " + sql + " | params=" + paramsText
                + " | time=" + elapsedMs + "ms | rows=" + rowCount + warning);
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE products (
                        id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name  VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL
                    )
                    """);
        }
    }
}
