package com.example.javaquest._10_dao.Lesson12_DatabaseConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class _Lesson12_DatabaseConfiguration {

    public static void main(String[] args) throws SQLException, IOException {

        /*
         * ============================================================
         * 📦 PROBLEM: DANE LOGOWANIA NA SZTYWNO W KODZIE
         * ============================================================
         * W Lesson11_ConnectionFactory URL, login i hasło były ZASZYTE
         * jako stałe w kodzie źródłowym (String URL = "..."). To
         * lepsze niż powielanie ich w wielu klasach, ale wciąż ma
         * poważne wady:
         *
         * - hasło w kodzie źródłowym trafia do repozytorium Git -
         *   każdy, kto ma dostęp do kodu, widzi hasło do bazy produkcyjnej
         * - zmiana adresu bazy (np. przy migracji serwera) wymaga
         *   PRZEBUDOWANIA i ponownego wdrożenia aplikacji
         * - nie da się mieć RÓŻNYCH baz dla różnych środowisk (lokalne
         *   dla developera, testowe, produkcyjne) bez zmiany kodu
         *
         * 🔹 ROZWIĄZANIE: PLIK KONFIGURACYJNY (.properties)
         * Dane połączenia wynosimy do osobnego pliku (np. db.properties),
         * który leży OBOK skompilowanego kodu (na classpath), ale NIE
         * jest częścią logiki aplikacji. Plik można podmienić bez
         * przebudowywania kodu - inny plik na serwerze developerskim,
         * inny na produkcyjnym.
         */

        /*
         * ============================================================
         * 🔍 WCZYTYWANIE Properties Z PLIKU NA CLASSPATH
         * ============================================================
         * Plik src/main/resources/lesson12-db.properties trafia po
         * kompilacji do target/classes i jest dostępny na classpath
         * pod nazwą "lesson12-db.properties". Wczytujemy go przez
         * classloader, a nie przez ścieżkę na dysku - dzięki temu
         * działa identycznie z IDE, z Mavena i po spakowaniu do JAR-a.
         */

        Properties dbProperties = loadProperties("lesson12-db.properties");

        String url = dbProperties.getProperty("db.url");
        String username = dbProperties.getProperty("db.username");
        String password = dbProperties.getProperty("db.password");
        String driver = dbProperties.getProperty("db.driver");

        System.out.println("=== KONFIGURACJA WCZYTANA Z PLIKU .properties ===");
        System.out.println("db.url      = " + url);
        System.out.println("db.username = " + username);
        System.out.println("db.password = " + (password.isEmpty() ? "(puste)" : "***UKRYTE***"));
        System.out.println("db.driver   = " + driver);

        /*
         * ============================================================
         * 🔍 UŻYCIE KONFIGURACJI DO ZBUDOWANIA POŁĄCZENIA
         * ============================================================
         * DriverManager sam odnajdzie odpowiedni sterownik JDBC po
         * URL-u, ale warto wiedzieć, że "db.driver" z konfiguracji
         * odpowiada klasie sterownika (np. org.h2.Driver) - w starszych
         * aplikacjach trzeba ją było jawnie załadować przez
         * Class.forName(driver).
         */
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE settings_demo (id BIGINT AUTO_INCREMENT PRIMARY KEY, note VARCHAR(200))");
                stmt.execute("INSERT INTO settings_demo (note) VALUES ('Polaczenie zbudowane z pliku .properties')");
            }
            try (Statement stmt = connection.createStatement();
                 var rs = stmt.executeQuery("SELECT note FROM settings_demo")) {
                if (rs.next()) {
                    System.out.println("\nOdczyt z bazy: " + rs.getString("note"));
                }
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Dane logowania do bazy NIE powinny być na sztywno zaszyte
         *   w kodzie źródłowym - trafiają wtedy do repozytorium Git
         * - Plik .properties (db.url, db.username, db.password,
         *   db.driver) pozwala TRZYMAĆ konfigurację OSOBNO od kodu
         * - Ten sam skompilowany kod może działać z RÓŻNĄ konfiguracją
         *   na różnych środowiskach (dev/test/prod) - wystarczy
         *   podmienić plik, bez przebudowy aplikacji
         * - To wciąż nie jest ostateczne rozwiązanie - plik .properties
         *   trzymany w repozytorium ma TE SAME wady, co hasło w kodzie.
         *   W następnej lekcji (Lesson13_EnvironmentVariables) hasła
         *   wyniesiemy jeszcze dalej - do zmiennych środowiskowych
         *   systemu operacyjnego, które NIGDY nie trafiają do repozytorium
         */
    }

    /**
     * Wczytuje plik .properties z classpath (a nie z dysku) - dzięki
     * temu działa niezależnie od tego, skąd aplikacja jest uruchamiana.
     */
    private static Properties loadProperties(String classpathFileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = _Lesson12_DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream(classpathFileName)) {
            if (input == null) {
                throw new IOException("Nie znaleziono pliku na classpath: " + classpathFileName);
            }
            properties.load(input);
        }
        return properties;
    }
}
