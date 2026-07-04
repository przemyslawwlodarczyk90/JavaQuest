package com.example.javaquest._10_dao.Lesson28_JdbcBestPractices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class _Lesson28_JdbcBestPractices {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DOBRE PRAKTYKI JDBC/DAO - ZBIORCZE PODSUMOWANIE
         * ============================================================
         * Ta lekcja NIE wprowadza nowych mechanizmów - to krótkie
         * zestawienie zasad poznanych w rozdziałach _09_jdbc i _10_dao.
         * Dla każdej zasady: CO robić, DLACZEGO, w KTÓREJ lekcji poznaliśmy
         * szczegóły, oraz malutki kontrastowy przykład ŹLE / DOBRZE.
         */

        String url = "jdbc:h2:mem:daolesson28;DB_CLOSE_DELAY=-1";
        try (Connection setupConnection = DriverManager.getConnection(url)) {
            setUpSchema(setupConnection);
        }

        System.out.println("=== Zasada 1: uzywaj PreparedStatement, nie konkatenacji ===");
        zasada1_PreparedStatement(url);

        System.out.println("\n=== Zasada 2: try-with-resources dla Connection/Statement/ResultSet ===");
        zasada2_TryWithResources(url);

        System.out.println("\n=== Zasada 3: nie trzymaj Connection dluzej niz trzeba ===");
        zasada3_KrotkoZyjacePolaczenie(url);

        System.out.println("\n=== Zasada 4: nie buduj SQL przez konkatenacje danych uzytkownika ===");
        zasada4_BezpieczneDynamiczneSql(url);

        System.out.println("\n=== Zasada 5: transakcje dla operacji wieloetapowych ===");
        zasada5_Transakcje(url);

        System.out.println("\n=== Zasada 6: nie mieszaj SQL w kontrolerze - warstwuj architekture ===");
        zasada6_WarstwowaArchitektura();

        System.out.println("\n=== Zasada 7: nie zwracaj null z DAO ===");
        zasada7_NieZwracajNull(url);

        System.out.println("\n=== Zasada 8: konfiguracja polaczenia POZA kodem ===");
        zasada8_KonfiguracjaPozaKodem();

        System.out.println("\n=== Zasada 9: uzywaj connection poola ===");
        zasada9_ConnectionPool();

        System.out.println("\n=== KONIEC LEKCJI 28 - KONIEC ROZDZIALU _10_dao ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * 1. PreparedStatement zamiast konkatenacji Stringow
         *    -> (_09_jdbc/Lesson05_PreparedStatement, Lesson14_SqlInjection)
         *       jedyna solidna ochrona przed SQL Injection dla WARTOSCI.
         * 2. try-with-resources dla KAZDEGO Connection/Statement/ResultSet
         *    -> (_09_jdbc/Lesson12_TryWithResourcesInJdbc) gwarantuje
         *       zamkniecie zasobow nawet przy wyjatku.
         * 3. Nie trzymaj Connection dluzej niz to konieczne
         *    -> (_10_dao/Lesson11_ConnectionFactory, Lesson18_SharedConnectionAcrossDao)
         *       otwieraj pozno, zamykaj szybko - polaczenia to zasob
         *       WSPOLDZIELONY (ograniczona pula, patrz zasada 9).
         * 4. Nigdy nie buduj SQL przez konkatenacje danych uzytkownika
         *    -> (_10_dao/Lesson23_DynamicSorting, Lesson24_DynamicFiltering)
         *       dynamiczna moze byc STRUKTURA (biala lista!), WARTOSCI
         *       zawsze przez `?`.
         * 5. Transakcje dla operacji wieloetapowych
         *    -> (_09_jdbc/Lesson15_JdbcTransactions,
         *       _10_dao/Lesson17_TransactionsInServiceLayer) - kilka
         *       zapytan modyfikujacych dane musi wykonac sie ATOMOWO
         *       (wszystkie albo zadne).
         * 6. Nie mieszaj SQL w kontrolerze - warstwuj architekture
         *    -> (_10_dao/Lesson02_LayeredArchitecture, Lesson16_ServiceLayer)
         *       Controller -> Service -> DAO -> baza danych, kazda
         *       warstwa ma jedna odpowiedzialnosc.
         * 7. Nie zwracaj null z DAO
         *    -> (_10_dao/Lesson06_OptionalInDao, Lesson07_ListResultsInDao)
         *       Optional<T> dla pojedynczego wyniku, PUSTA lista (nigdy
         *       null) dla kolekcji wynikow.
         * 8. Konfiguruj polaczenie POZA kodem (pliki/zmienne srodowiskowe)
         *    -> (_10_dao/Lesson12_DatabaseConfiguration, Lesson13_EnvironmentVariables)
         *       adres bazy/haslo NIGDY na sztywno w kodzie zrodlowym.
         * 9. Uzywaj connection poola zamiast tworzyc nowe polaczenie za
         *    kazdym razem
         *    -> (_10_dao/Lesson14_ConnectionPool, Lesson15_DataSource)
         *       nawiazanie polaczenia TCP do bazy jest kosztowne - pula
         *       (np. HikariCP) trzyma gotowe, wielokrotnego uzytku
         *       polaczenia.
         *
         * To zamyka rozdzial _10_dao (wzorzec DAO, warstwy, transakcje,
         * connection pooling, paginacja/sortowanie/filtrowanie, migracje,
         * testowanie, logowanie SQL) i, poprzez te dobre praktyki, spina
         * go z materialem czystego JDBC z rozdzialu _09_jdbc.
         */
    }

    /**
     * Zasada 1: PreparedStatement zamiast konkatenacji Stringow.
     * DLACZEGO: konkatenacja pozwala danym uzytkownika "wydostac sie"
     * poza role wartosci i stac sie fragmentem SKLADNI SQL (SQL
     * Injection). Szczegoly: _09_jdbc/Lesson05_PreparedStatement,
     * Lesson14_SqlInjection.
     */
    private static void zasada1_PreparedStatement(String url) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {
            String maliciousInput = "x' OR '1'='1";

            // ❌ ZLE (tylko ilustracja, NIE wykonujemy):
            //   String sql = "SELECT * FROM users WHERE name = '" + input + "'";
            //   -> dla malicious input zwrocilby WSZYSTKIE wiersze

            // ✅ DOBRZE: PreparedStatement z parametrem `?`
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE name = ?")) {
                statement.setString(1, maliciousInput);
                try (ResultSet rs = statement.executeQuery()) {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                    }
                    System.out.println("PreparedStatement z 'zlosliwym' wejsciem zwrocil " + count
                            + " wierszy (oczekiwano: 0 - tekst potraktowany doslownie)");
                }
            }
        }
    }

    /**
     * Zasada 2: try-with-resources dla KAZDEGO zasobu JDBC. DLACZEGO:
     * Connection/Statement/ResultSet implementuja AutoCloseable - bez
     * try-with-resources wyjatek w trakcie pracy z baza latwo zostawia
     * zasob NIGDY niezamkniety (wyciek polaczen/kursorow). Szczegoly:
     * _09_jdbc/Lesson12_TryWithResourcesInJdbc.
     */
    private static void zasada2_TryWithResources(String url) throws SQLException {
        // ❌ ZLE (tylko ilustracja, NIE wykonujemy):
        //   Connection c = DriverManager.getConnection(url);
        //   Statement s = c.createStatement();
        //   ResultSet rs = s.executeQuery("SELECT 1");
        //   ... // jesli tu poleci wyjatek, c/s/rs NIGDY sie nie zamkna

        // ✅ DOBRZE: wszystkie 3 zasoby w jednym try-with-resources
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users")) {
            rs.next();
            System.out.println("Uzytkownicy w tabeli: " + rs.getInt(1)
                    + " (connection/statement/resultset zamkna sie AUTOMATYCZNIE)");
        }
    }

    /**
     * Zasada 3: nie trzymaj Connection dluzej niz to konieczne.
     * DLACZEGO: polaczenia sa ograniczonym, wspoldzielonym zasobem (patrz
     * zasada 9 - connection pool) - trzymanie polaczenia "na zapas"
     * (np. jako pole obiektu przez caly czas zycia aplikacji) blokuje je
     * dla innych czesci systemu. Szczegoly:
     * _10_dao/Lesson11_ConnectionFactory, Lesson18_SharedConnectionAcrossDao.
     */
    private static void zasada3_KrotkoZyjacePolaczenie(String url) throws SQLException {
        // ❌ ZLE (tylko ilustracja, NIE wykonujemy): trzymanie polaczenia
        // w polu klasy "na zawsze", otwartego od startu do konca aplikacji
        //   private static final Connection SHARED = DriverManager.getConnection(url);

        // ✅ DOBRZE: polaczenie otwierane TUZ PRZED uzyciem, zamykane
        // NATYCHMIAST po zakonczeniu operacji
        long before = countUsers(url);
        System.out.println("Polaczenie otworzone, uzyte i zamkniete w ramach jednej, krotkiej operacji"
                + " (uzytkownikow: " + before + ")");
    }

    private static long countUsers(String url) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users")) {
            rs.next();
            return rs.getLong(1);
        }
    }

    /**
     * Zasada 4: nigdy nie buduj SQL przez konkatenacje danych
     * uzytkownika - nawet gdy STRUKTURA zapytania musi byc dynamiczna
     * (sortowanie/filtrowanie). DLACZEGO: dynamiczna moze byc struktura
     * (ktora kolumna, ktore warunki) - ale TYLKO gdy jest zwalidowana
     * wzgledem BIALEJ LISTY, nigdy gdy pochodzi wprost od uzytkownika bez
     * walidacji. WARTOSCI zawsze przez `?`. Szczegoly:
     * _10_dao/Lesson23_DynamicSorting, Lesson24_DynamicFiltering.
     */
    private static void zasada4_BezpieczneDynamiczneSql(String url) throws SQLException {
        Set<String> allowedColumns = Set.of("id", "name");

        // ❌ ZLE (tylko ilustracja, NIE wykonujemy): kolumna sortowania
        // wprost od uzytkownika, bez walidacji
        //   String sql = "SELECT * FROM users ORDER BY " + userInput;

        // ✅ DOBRZE: kolumna zwalidowana wzgledem bialej listy
        String requestedColumn = "name";
        if (!allowedColumns.contains(requestedColumn)) {
            throw new IllegalArgumentException("Niedozwolona kolumna: " + requestedColumn);
        }
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT id, name FROM users ORDER BY " + requestedColumn)) {
            System.out.println("Posortowano bezpiecznie po zwalidowanej kolumnie '" + requestedColumn + "'");
            while (rs.next()) {
                System.out.println("  #" + rs.getLong("id") + " " + rs.getString("name"));
            }
        }
    }

    /**
     * Zasada 5: transakcje dla operacji wieloetapowych. DLACZEGO: gdy
     * logiczna operacja biznesowa skada sie z kilku zapytan
     * modyfikujacych dane (np. "przelew" = odejmij z konta A, dodaj do
     * konta B), MUSZA wykonac sie ATOMOWO - albo obie zmiany, albo zadna.
     * Bez transakcji awaria po pierwszym kroku zostawia dane w
     * NIESPOJNYM stanie. Szczegoly: _09_jdbc/Lesson15_JdbcTransactions,
     * _10_dao/Lesson17_TransactionsInServiceLayer.
     */
    private static void zasada5_Transakcje(String url) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                // ✅ DOBRZE: obie modyfikacje w JEDNEJ transakcji
                statement.executeUpdate("UPDATE users SET name = 'Kasia (VIP)' WHERE name = 'Kasia'");
                statement.executeUpdate("UPDATE users SET name = 'Adam (VIP)' WHERE name = 'Adam'");
                connection.commit();
                System.out.println("Obie modyfikacje zatwierdzone RAZEM (commit) - dane spojne");
            } catch (SQLException e) {
                connection.rollback(); // ❌ przy bledzie: WYCOFAJ obie zmiany, nie zostawiaj polowy
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    /**
     * Zasada 6: nie mieszaj SQL w kontrolerze - warstwuj architekture.
     * DLACZEGO: kontroler (warstwa API) nie powinien znac SQL-a ani
     * Connection - to zadanie DAO. Miedzy nimi stoi Service (logika
     * biznesowa). Szczegoly: _10_dao/Lesson02_LayeredArchitecture,
     * Lesson16_ServiceLayer.
     */
    private static void zasada6_WarstwowaArchitektura() {
        // ❌ ZLE (tylko ilustracja, NIE wykonujemy):
        //   class UserController {
        //       void handleGetUser(long id) {
        //           Connection c = DriverManager.getConnection(url); // <- SQL w kontrolerze!
        //           ...
        //       }
        //   }

        // ✅ DOBRZE: Controller -> Service -> DAO -> baza danych
        System.out.println("Controller -> Service -> DAO -> baza danych");
        System.out.println("(Controller nie wie NIC o SQL/Connection - zna tylko Service)");
    }

    /**
     * Zasada 7: nie zwracaj null z DAO. DLACZEGO: null wymusza na
     * KAZDYM wywolujacym pamietanie o recznym sprawdzeniu `if (x != null)`
     * - latwo o to zapomniec (NullPointerException). Optional<T> dla
     * pojedynczego wyniku wymusza SWIADOMA obsluge braku wartosci; dla
     * kolekcji zwracamy zawsze PUSTA liste, nigdy null. Szczegoly:
     * _10_dao/Lesson06_OptionalInDao, Lesson07_ListResultsInDao.
     */
    private static void zasada7_NieZwracajNull(String url) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {
            // ❌ ZLE (tylko ilustracja, NIE wykonujemy):
            //   User findById(long id) { ...; return null; /* gdy brak wiersza */ }

            // ✅ DOBRZE: Optional dla pojedynczego wyniku
            Optional<String> found = findUserNameById(connection, 999L);
            System.out.println("findById(999) -> " + found + " (Optional.empty(), NIE null)");

            // ✅ DOBRZE: pusta lista, nie null, gdy brak wynikow
            List<String> results = findUserNamesByPrefix(connection, "Nieistniejacy");
            System.out.println("findByPrefix(\"Nieistniejacy\") -> " + results
                    + " (pusta lista, NIE null, .isEmpty()=" + results.isEmpty() + ")");
        }
    }

    private static Optional<String> findUserNameById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT name FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(rs.getString("name")) : Optional.empty();
            }
        }
    }

    private static List<String> findUserNamesByPrefix(Connection connection, String prefix) throws SQLException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT name FROM users WHERE name LIKE ?")) {
            statement.setString(1, prefix + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        }
        return result; // NIGDY null - w najgorszym razie pusta lista
    }

    /**
     * Zasada 8: konfiguruj polaczenie POZA kodem. DLACZEGO: adres bazy,
     * uzytkownik i haslo RÓZNIA sie miedzy srodowiskami (dev/test/prod) -
     * zaszycie ich na sztywno w kodzie zrodlowym oznacza rekompilacje
     * przy kazdej zmianie (i wyciek hasla produkcyjnego do repozytorium
     * kodu!). Szczegoly: _10_dao/Lesson12_DatabaseConfiguration,
     * Lesson13_EnvironmentVariables.
     */
    private static void zasada8_KonfiguracjaPozaKodem() {
        // ❌ ZLE (tylko ilustracja, NIE wykonujemy):
        //   String url = "jdbc:postgresql://prod-db.example.com/app";
        //   String password = "P@ssw0rd123"; // <- haslo na sztywno w kodzie!

        // ✅ DOBRZE: dane polaczenia z pliku konfiguracyjnego / zmiennych
        // srodowiskowych, ODCZYTANE w runtime (patrz System.getenv(),
        // Properties, application.properties)
        String urlFromConfig = System.getenv().getOrDefault("DB_URL", "(przykladowo: z application.properties albo zmiennej DB_URL)");
        System.out.println("URL bazy pochodzi z konfiguracji/env, NIE z kodu: " + urlFromConfig);
    }

    /**
     * Zasada 9: uzywaj connection poola zamiast tworzyc nowe polaczenie
     * za kazdym razem. DLACZEGO: nawiazanie polaczenia TCP + uwierzytelnienie
     * do bazy danych jest KOSZTOWNE (dziesiatki milisekund) - robienie
     * tego przy KAZDYM zapytaniu to ogromne, niepotrzebne marnotrawstwo.
     * Pula (np. HikariCP - zaleznosc juz dodana w tym projekcie) trzyma
     * gotowe, wielokrotnego uzytku polaczenia. Szczegoly:
     * _10_dao/Lesson14_ConnectionPool, Lesson15_DataSource.
     */
    private static void zasada9_ConnectionPool() {
        // ❌ ZLE (tylko ilustracja, NIE wykonujemy): nowe polaczenie za
        // kazdym razem
        //   Connection c = DriverManager.getConnection(url); // za KAZDYM razem od zera

        // ✅ DOBRZE (koncepcyjnie - konfiguracja i uzycie HikariCP to
        // temat Lesson14_ConnectionPool/Lesson15_DataSource):
        //   HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        //   try (Connection c = dataSource.getConnection()) { ... } // pozyczone Z PULI, nie nowe
        System.out.println("Connection pool: polaczenia POZYCZANE z gotowej puli (getConnection()),");
        System.out.println("nie tworzone od zera przy kazdym zapytaniu - patrz Lesson14_ConnectionPool.");
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Kasia')");
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Adam')");
        }
    }
}
