package com.example.javaquest._10_dao.Lesson14_ConnectionPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson14_ConnectionPool {

    private static final int ITERATIONS = 20;

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PROBLEM: TWORZENIE Connection JEST KOSZTOWNE
         * ============================================================
         * DriverManager.getConnection(...) za każdym razem nawiązuje
         * NOWE połączenie z bazą danych od zera: otwiera gniazdo
         * sieciowe (TCP), przechodzi uwierzytelnienie (login/hasło),
         * negocjuje parametry sesji. Dla bazy w tej samej sieci lokalnej
         * to może być kilka-kilkanaście milisekund, dla bazy w chmurze
         * (inny region, inny kontynent) - nawet setki milisekund.
         *
         * Jeśli aplikacja obsługuje setki żądań na sekundę i za KAŻDYM
         * razem tworzy nowe połączenie od zera, ten koszt szybko staje
         * się wąskim gardłem całej aplikacji.
         *
         * 🔹 ROZWIĄZANIE: PULA POŁĄCZEŃ (connection pool)
         * Pula połączeń tworzy z góry pewną liczbę połączeń i TRZYMA
         * je otwarte. Kod aplikacji "pożycza" połączenie z puli
         * (getConnection()), używa go, a potem "oddaje" (close()) -
         * connection.close() na połączeniu z puli NIE zamyka fizycznego
         * połączenia z bazą, tylko zwraca je do puli, gotowe do
         * ponownego użycia przez kogoś innego.
         */

        /*
         * ============================================================
         * 🔍 HikariCP - NAJPOPULARNIEJSZA PULA POŁĄCZEŃ W JAVIE
         * ============================================================
         * HikariCP (biblioteka com.zaxxer:HikariCP, już dodana do tego
         * projektu w pom.xml) implementuje interfejs javax.sql.DataSource
         * - standardowy sposób pobierania Connection w Javie, niezależny
         * od konkretnej biblioteki puli.
         *
         * Kluczowe ustawienia konfiguracji puli:
         * - jdbcUrl / username / password - jak się łączyć z bazą
         * - maximumPoolSize - ile NAJWIĘCEJ fizycznych połączeń pula
         *   utrzyma naraz (za dużo = niepotrzebnie obciąża bazę,
         *   za mało = wątki aplikacji czekają w kolejce po połączenie)
         * - connectionTimeout - jak długo wątek aplikacji CZEKA na wolne
         *   połączenie z puli, zanim dostanie wyjątek (zabezpieczenie
         *   przed wiecznym oczekiwaniem, gdy pula jest przeciążona)
         */

        String url = "jdbc:h2:mem:daolesson14;DB_CLOSE_DELAY=-1";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername("sa");
        config.setPassword("");
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(3000); // ms - max czas oczekiwania na wolne polaczenie
        config.setPoolName("Lesson14Pool");

        System.out.println("=== KONFIGURACJA PULI HikariCP ===");
        System.out.println("jdbcUrl           = " + config.getJdbcUrl());
        System.out.println("maximumPoolSize   = " + config.getMaximumPoolSize());
        System.out.println("connectionTimeout = " + config.getConnectionTimeout() + " ms");

        try (HikariDataSource dataSource = new HikariDataSource(config)) {

            setUpSchema(dataSource);

            /*
             * ============================================================
             * 🔍 UŻYCIE PULI: getConnection() / close() WIELOKROTNIE
             * ============================================================
             * Za każdym razem "pożyczamy" połączenie z puli w bloku
             * try-with-resources - close() na końcu bloku ZWRACA je do
             * puli (nie zamyka fizycznie), więc kolejna pożyczka może
             * dostać to samo, już otwarte połączenie.
             */
            System.out.println("\n=== POBIERANIE POŁĄCZEŃ Z PULI HikariCP (" + ITERATIONS + "x) ===");
            long hikariNanos = measureConnections(ITERATIONS, dataSource::getConnection);
            System.out.println("Suma czasu pobrania+użycia+zwrotu połączenia z puli: "
                    + toMillis(hikariNanos) + " ms");

            /*
             * ============================================================
             * 🔍 PORÓWNANIE: SUROWY DriverManager (BEZ PULI) WIELOKROTNIE
             * ============================================================
             * Dla porównania - to samo zadanie, ale za każdym razem
             * nawiązujemy zupełnie NOWE, surowe połączenie przez
             * DriverManager, bez żadnej puli.
             */
            System.out.println("\n=== NAWIĄZYWANIE SUROWYCH POŁĄCZEŃ BEZ PULI (" + ITERATIONS + "x) ===");
            long driverManagerNanos = measureConnections(ITERATIONS, () -> DriverManager.getConnection(url, "sa", ""));
            System.out.println("Suma czasu nawiązania+użycia+zamknięcia surowego połączenia: "
                    + toMillis(driverManagerNanos) + " ms");

            System.out.println("\n=== WNIOSEK ===");
            System.out.println("""
                    Dla lokalnej bazy H2 w pamięci różnica bywa niewielka albo
                    wręcz niezauważalna - nawiązanie połączenia z bazą "obok" nie
                    kosztuje prawie nic. PRAWDZIWA przewaga puli połączeń ujawnia
                    się przy bazach sieciowych (inny host, inny kontener, chmura),
                    gdzie każde nowe połączenie to osobny uścisk dłoni TCP i
                    uwierzytelnienie - tam pula oszczędza realne dziesiątki albo
                    setki milisekund NA KAŻDYM zapytaniu. Ta lekcja pokazuje przede
                    wszystkim DZIAŁAJĄCE API puli połączeń, a nie efektowny wykres.""");

        } // dataSource.close() - pula zamykana automatycznie, wszystkie fizyczne
          // połączenia w niej trzymane zostają poprawnie zwolnione

        System.out.println("\nPula HikariCP zamknięta.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Tworzenie Connection od zera jest kosztowne (TCP + logowanie)
         *   - przy dużym ruchu staje się wąskim gardłem aplikacji
         * - Pula połączeń (np. HikariCP) utrzymuje gotowe, otwarte
         *   połączenia - "pożyczasz" je (getConnection()) i "oddajesz"
         *   (close()), zamiast tworzyć i niszczyć za każdym razem
         * - HikariDataSource implementuje javax.sql.DataSource -
         *   standardowy, przenośny sposób pobierania Connection w Javie
         * - Kluczowe ustawienia: jdbcUrl/username/password (jak się łączyć),
         *   maximumPoolSize (ile połączeń naraz), connectionTimeout (jak
         *   długo czekać na wolne połączenie)
         * - Pulę NALEŻY zamknąć (dataSource.close()) przy zamykaniu
         *   aplikacji - najlepiej przez try-with-resources, tak jak tutaj
         */
    }

    private static void setUpSchema(HikariDataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE pool_demo (id BIGINT AUTO_INCREMENT PRIMARY KEY, amount INT)");
        }
    }

    /**
     * Wykonuje ITERATIONS razy: pobierz połączenie -> wykonaj proste
     * zapytanie -> zamknij (zwróć) połączenie. Zwraca łączny czas w ns.
     */
    private static long measureConnections(int iterations, ConnectionSupplier supplier) throws SQLException {
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            try (Connection connection = supplier.get();
                 Statement stmt = connection.createStatement()) {
                stmt.execute("INSERT INTO pool_demo (amount) VALUES (" + i + ")");
            }
        }
        return System.nanoTime() - start;
    }

    private static double toMillis(long nanos) {
        return nanos / 1_000_000.0;
    }

    @FunctionalInterface
    private interface ConnectionSupplier {
        Connection get() throws SQLException;
    }
}
