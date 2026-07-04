package com.example.javaquest._10_dao.Lesson15_DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

public class _Lesson15_DataSource {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST DataSource?
         * ============================================================
         * `javax.sql.DataSource` to INTERFEJS z pakietu standardowego JDBC.
         * Reprezentuje "fabrykę połączeń" - obiekt, który potrafi dać nam
         * `Connection` przez metodę `getConnection()`.
         *
         * To WSZYSTKO, czego wymaga interfejs - jak dokładnie połączenie
         * powstaje (czy jest nowe, czy pożyczone z puli, czy nawiązywane
         * do lokalnej bazy czy przez sieć) to już szczegół IMPLEMENTACJI,
         * którego kod korzystający z DataSource w ogóle nie musi znać.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * DataSource to interfejs "daj mi połączenie", za którym może
         * kryć się cokolwiek - od zwykłego DriverManagera po zaawansowaną
         * pulę połączeń jak HikariCP.
         */

        /*
         * ============================================================
         * 🔹 DriverManager vs DataSource
         * ============================================================
         * DriverManager.getConnection(url) to STATYCZNA metoda:
         * - za każdym wywołaniem NAWIĄZUJE NOWE fizyczne połączenie z bazą
         * - nie ma pojęcia "pula" - nie ma jak reużyć wcześniej otwartego
         *   połączenia
         * - dobre do nauki, do prostych skryptów, ale KOSZTOWNE na produkcji
         *   (nawiązanie połączenia TCP + uwierzytelnienie to operacja,
         *   która trwa - typowo kilkadziesiąt milisekund)
         *
         * DataSource to OBIEKT (instancja klasy implementującej interfejs):
         * - metoda `getConnection()` NIE musi tworzyć nowego połączenia
         * - konkretna implementacja (np. HikariCP, którą poznałeś w
         *   poprzedniej lekcji) może trzymać PULĘ już otwartych połączeń
         *   i po prostu "pożyczać" jedno z nich
         * - kod aplikacji wygląda identycznie niezależnie od tego, czy
         *   pod spodem jest pula, czy coś prostszego
         *
         * `HikariDataSource` (z poprzedniej lekcji) IMPLEMENTUJE interfejs
         * `javax.sql.DataSource` - dlatego wszędzie tam, gdzie kod oczekuje
         * DataSource, możemy podać HikariDataSource.
         */

        System.out.println("=== DriverManager: kazde wywolanie = nowe polaczenie ===");
        String url = "jdbc:h2:mem:daolesson15;DB_CLOSE_DELAY=-1";
        try (Connection c1 = DriverManager.getConnection(url);
             Connection c2 = DriverManager.getConnection(url)) {
            System.out.println("c1 == c2 (to samo polaczenie)? " + (c1 == c2));
            System.out.println("-> DriverManager zawsze da NOWY obiekt Connection.");
        }

        /*
         * ============================================================
         * 🔍 Kod korzystający z DataSource jako PARAMETRU
         * ============================================================
         * Poniższa metoda `countStatements` przyjmuje `DataSource`, a NIE
         * `Connection`. Dzięki temu metoda SAMA otwiera i zamyka połączenie
         * (try-with-resources), a wywołujący nie musi się martwić, skąd
         * dokładnie to połączenie pochodzi.
         *
         * Zobaczmy, że ta sama metoda działa identycznie z DWOMA różnymi
         * implementacjami DataSource:
         * 1) HikariDataSource - prawdziwa pula połączeń
         * 2) SimpleDriverManagerDataSource - nasz własny, bardzo prosty
         *    wrapper na DriverManagerze, napisany TYLKO po to, żeby
         *    pokazać, że DataSource to "zwykły interfejs" - da się go
         *    zaimplementować samodzielnie w kilku liniach.
         */

        System.out.println("\n=== Ta sama metoda, dwie rozne implementacje DataSource ===");

        try (HikariDataSource hikariDataSource = createHikariDataSource(url)) {
            setUpSchemaIfNeeded(hikariDataSource);
            System.out.println("[HikariDataSource] " + describe(hikariDataSource));
            runQuery(hikariDataSource);
        }

        DataSource plainDataSource = new SimpleDriverManagerDataSource(url);
        System.out.println("[SimpleDriverManagerDataSource] " + describe(plainDataSource));
        runQuery(plainDataSource);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `javax.sql.DataSource` to interfejs standardowej biblioteki JDBC
         *   z jedną kluczową metodą: `getConnection()`
         * - `DriverManager` to statyczna metoda - zawsze tworzy NOWE
         *   fizyczne połączenie; brak pojęcia puli
         * - `DataSource` to obiekt - konkretna implementacja (np. HikariCP)
         *   MOŻE reużywać połączeń z puli, ale nie MUSI - to szczegół
         *   implementacji ukryty za interfejsem
         * - Pisząc kod aplikacyjny (DAO, Service), warto przyjmować
         *   `DataSource` jako zależność zamiast `Connection` na sztywno -
         *   dzięki temu można podmienić implementację (pula <-> prosty
         *   wrapper <-> mock w testach) bez zmiany reszty kodu
         * - `HikariDataSource` implementuje `DataSource`, więc wszędzie
         *   tam, gdzie kod oczekuje DataSource, można podać HikariCP
         */
    }

    private static HikariDataSource createHikariDataSource(String url) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setMaximumPoolSize(5);
        config.setPoolName("Lesson15Pool");
        return new HikariDataSource(config);
    }

    private static void setUpSchemaIfNeeded(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS demo_items (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            stmt.execute("MERGE INTO demo_items (id, name) KEY (id) VALUES (1, 'Przykladowy element')");
        }
    }

    private static String describe(DataSource dataSource) {
        return "typ: " + dataSource.getClass().getSimpleName();
    }

    /**
     * Metoda "biznesowa" pisana pod interfejs DataSource - nieważne, skąd
     * dokładnie pochodzi połączenie.
     */
    private static void runQuery(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            var rs = stmt.executeQuery("SELECT COUNT(*) AS ile FROM demo_items");
            rs.next();
            System.out.println("   liczba wierszy w demo_items: " + rs.getInt("ile"));
        }
    }

    /**
     * 🧪 Własna, MINIMALNA implementacja DataSource oparta o DriverManager.
     * Pokazuje, że DataSource to "zwykły interfejs" - nie ma tu żadnej
     * magii ani puli połączeń, tylko delegacja do DriverManager.getConnection().
     * W prawdziwym projekcie taką klasę pisze się BARDZO rzadko (po to
     * właśnie istnieje HikariCP), ale jako ćwiczenie edukacyjne świetnie
     * pokazuje, "co tak naprawdę robi" interfejs DataSource.
     */
    private static class SimpleDriverManagerDataSource implements DataSource {

        private final String url;

        SimpleDriverManagerDataSource(String url) {
            this.url = url;
        }

        @Override
        public Connection getConnection() throws SQLException {
            // Uwaga: to NADAL nowe polaczenie za kazdym razem - brak puli!
            return DriverManager.getConnection(url);
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return DriverManager.getConnection(url, username, password);
        }

        @Override
        public PrintWriter getLogWriter() {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) {
            // brak potrzeby implementacji na potrzeby tej lekcji
        }

        @Override
        public void setLoginTimeout(int seconds) {
            // brak potrzeby implementacji na potrzeby tej lekcji
        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nieobslugiwane w tej prostej implementacji");
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            throw new SQLException("nieobslugiwane w tej prostej implementacji");
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) {
            return false;
        }
    }
}
