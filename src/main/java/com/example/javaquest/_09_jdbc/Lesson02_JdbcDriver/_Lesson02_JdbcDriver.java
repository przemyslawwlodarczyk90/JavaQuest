package com.example.javaquest._09_jdbc.Lesson02_JdbcDriver;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class _Lesson02_JdbcDriver {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST DRIVER (STEROWNIK)?
         * ============================================================
         * DRIVER to konkretna implementacja interfejsów JDBC (`java.sql.
         * Driver`, `java.sql.Connection` itd.) dostarczana przez twórców
         * danej bazy danych. To on wie, jak fizycznie porozumieć się z
         * daną bazą - jakim protokołem sieciowym, w jakim formacie binarnym
         * itd. Bez drivera JDBC API to tylko puste interfejsy - "umowa",
         * ale bez żadnej faktycznej implementacji.
         *
         * Każda baza danych ma swój własny driver, dostarczany zwykle
         * jako zwykła zależność (plik .jar):
         *
         * - H2         -> com.h2database:h2 (używamy w TYM kursie -
         *                 embedded, in-memory, zero instalacji)
         * - PostgreSQL -> org.postgresql:postgresql
         * - MySQL      -> com.mysql:mysql-connector-j
         *
         * W prawdziwym projekcie łączącym się z PRAWDZIWYM serwerem
         * PostgreSQL dodalibyśmy do pom.xml np.:
         *
         *   <dependency>
         *       <groupId>org.postgresql</groupId>
         *       <artifactId>postgresql</artifactId>
         *       <version>42.7.3</version>
         *   </dependency>
         *
         * a do MySQL:
         *
         *   <dependency>
         *       <groupId>com.mysql</groupId>
         *       <artifactId>mysql-connector-j</artifactId>
         *       <version>8.4.0</version>
         *   </dependency>
         *
         * Sam KOD naszej aplikacji (Connection, Statement, ResultSet...)
         * pozostałby niemal identyczny - zmieniłby się głównie URL
         * połączenia (patrz Lesson03) i właśnie ta zależność Maven.
         */

        System.out.println("=== DRIVERY - KTORYCH UZYWA SIE W PRAKTYCE ===");
        System.out.println("H2:         com.h2database:h2 (nasz kurs)");
        System.out.println("PostgreSQL: org.postgresql:postgresql");
        System.out.println("MySQL:      com.mysql:mysql-connector-j");

        /*
         * ============================================================
         * 🔹 CLASSPATH I DriverManager
         * ============================================================
         * `DriverManager` to klasa z java.sql, która zarządza dostępnymi
         * driverami - to ona, na podstawie URL-a połączenia (np.
         * "jdbc:h2:mem:..." albo "jdbc:postgresql://..."), wybiera
         * WŁAŚCIWY driver zarejestrowany w programie i za jego pomocą
         * nawiązuje połączenie.
         *
         * Żeby driver mógł zostać użyty, jego .jar musi znaleźć się na
         * CLASSPATH aplikacji (u nas - Maven pobiera go automatycznie z
         * repozytorium na podstawie zależności w pom.xml).
         *
         * 🔍 AUTOMATYCZNE ŁADOWANIE STEROWNIKÓW (JDBC 4.0+)
         * Od Javy 6 / JDBC 4.0 sterowniki są ładowane AUTOMATYCZNIE dzięki
         * mechanizmowi Service Provider Interface (SPI). Każdy zgodny
         * driver zawiera w swoim .jarze plik:
         *
         *     META-INF/services/java.sql.Driver
         *
         * z nazwą klasy implementującej interfejs Driver (np.
         * "org.h2.Driver"). JVM, przeglądając classpath, automatycznie
         * znajduje i rejestruje taki driver w DriverManager - NIE trzeba
         * już ręcznie tego robić.
         *
         * W BARDZO STARYM kodzie (sprzed JDBC 4.0, czyli sprzed Javy 6)
         * można spotkać ręczne ładowanie drivera:
         *
         *     Class.forName("org.h2.Driver");
         *
         * Ta linijka wymuszała załadowanie klasy (a przez to - jej
         * rejestrację w DriverManager, bo klasy Driver rejestrują się
         * same w bloku statycznym). Dziś jest to NIEPOTRZEBNE - zostaje
         * w starym kodzie z przyzwyczajenia albo z powodu bardzo starych
         * wersji Javy/driverów.
         */

        System.out.println("\n=== ZAREJESTROWANE DRIVERY (DriverManager.getDrivers()) ===");
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        int count = 0;
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            System.out.println(" - " + driver.getClass().getName()
                    + " (major=" + driver.getMajorVersion() + ", minor=" + driver.getMinorVersion() + ")");
            count++;
        }
        System.out.println("Liczba zarejestrowanych driverow: " + count);
        // W tym projekcie na classpath jest tylko H2, wiec zobaczymy:
        // - org.h2.Driver (major=..., minor=...)
        // Liczba zarejestrowanych driverow: 1
        // Gdyby na classpath byl tez np. driver PostgreSQL, pojawilby sie
        // TU DRUGI wpis - bez zadnej dodatkowej konfiguracji z naszej strony.

        /*
         * ============================================================
         * 🔍 DriverManager SAM WYBIERA WŁAŚCIWY DRIVER
         * ============================================================
         * Gdy wywołujemy DriverManager.getConnection(url, ...), JDBC
         * przegląda WSZYSTKIE zarejestrowane drivery i pyta każdego:
         * "czy rozpoznajesz ten URL?" (metoda Driver.acceptsURL(url)).
         * Pierwszy driver, który odpowie "tak", zostaje użyty do
         * nawiązania połączenia. Dzięki temu, mając na classpath kilka
         * driverów naraz, JDBC automatycznie kieruje połączenie do
         * właściwego z nich - w zależności od prefiksu URL-a
         * ("jdbc:h2:...", "jdbc:postgresql:...", "jdbc:mysql:...").
         */

        System.out.println("\n=== KONIEC LEKCJI 2 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Driver = konkretna implementacja JDBC API dla danej bazy
         *   danych (H2, PostgreSQL, MySQL...), dostarczana jako zależność
         * - W tym kursie: com.h2database:h2. W realnym projekcie z
         *   PostgreSQL/MySQL dodalibyśmy odpowiednio
         *   org.postgresql:postgresql lub com.mysql:mysql-connector-j
         * - Driver musi być na CLASSPATH aplikacji
         * - DriverManager zarządza zarejestrowanymi driverami i na
         *   podstawie prefiksu URL-a wybiera właściwy z nich
         * - Od JDBC 4.0 (Java 6+) drivery ładują się AUTOMATYCZNIE przez
         *   mechanizm SPI (META-INF/services/java.sql.Driver) - ręczne
         *   Class.forName("...") jest dziś NIEPOTRZEBNE (spotykane
         *   tylko w bardzo starym kodzie)
         */
    }
}
