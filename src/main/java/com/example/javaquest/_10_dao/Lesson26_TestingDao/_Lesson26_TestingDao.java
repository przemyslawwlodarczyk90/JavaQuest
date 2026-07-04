package com.example.javaquest._10_dao.Lesson26_TestingDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class _Lesson26_TestingDao {

    /** Funkcyjny interfejs testu - pozwala rzucac checked wyjatki (SQLException). */
    @FunctionalInterface
    private interface TestCase {
        void run() throws Exception;
    }

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 PO CO TESTOWAĆ WARSTWĘ DAO?
         * ============================================================
         * DAO to warstwa, która OPAKOWUJE prawdziwy SQL. Jej najczęstsze
         * błędy to: literówka w nazwie kolumny, złe mapowanie ResultSet
         * -> obiekt, źle zbudowany dynamiczny SQL (patrz Lesson23/24),
         * albo zwyczajnie zła logika w WHERE. Testy JEDNOSTKOWE z mockami
         * (np. udawany Connection) nie wykryją TAKICH błędów - nie
         * wykonują żadnego prawdziwego SQL. Dlatego DAO testuje się
         * TESTAMI INTEGRACYJNYMI (integration tests): testem, który
         * faktycznie łączy się z bazą danych i wykonuje prawdziwe
         * zapytania na prawdziwym (choć testowym) schemacie.
         */

        /*
         * ============================================================
         * 🔹 H2 IN-MEMORY JAKO BAZA TESTOWA
         * ============================================================
         * H2 w trybie in-memory (`jdbc:h2:mem:...`) to IDEALNY wybór do
         * testów integracyjnych DAO:
         * - startuje w milisekundach, nie wymaga zewnętrznego serwera,
         * - każdy test może dostać WŁASNĄ, czystą bazę (albo czystą
         *   tabelę w tej samej bazie),
         * - działa identycznie na maszynie developera i na serwerze CI,
         *   bez instalowania czegokolwiek.
         * Wadą jest to, że H2 nie jest DOKŁADNIE tą samą bazą, co
         * produkcyjna (np. PostgreSQL) - drobne różnice w składni SQL
         * albo w zachowaniu typów mogą się różnić.
         *
         * 🔍 TESTCONTAINERS - NOWOCZESNA ALTERNATYWA (wzmianka)
         * Testcontainers to biblioteka, która przy starcie testów
         * uruchamia PRAWDZIWĄ bazę danych (np. prawdziwego PostgreSQL) w
         * kontenerze Docker, dedykowanym TYLKO na czas testów, i
         * automatycznie go usuwa po zakończeniu. Zaleta: testy biegają
         * na DOKŁADNIE tej samej bazie co produkcja - zero niespodzianek
         * przy wdrożeniu. Wada: wymaga zainstalowanego Docker-a na
         * maszynie/CI i jest zauważalnie wolniejsze niż H2 in-memory
         * (sekundy zamiast milisekund na start). W tym kursie POZOSTAJEMY
         * przy H2 in-memory (szybkość, zero zależności zewnętrznych) -
         * Testcontainers to świadomy wybór do rozważenia w większych,
         * produkcyjnych projektach, gdzie różnice między bazami mają
         * znaczenie.
         */

        /*
         * ============================================================
         * 🔍 IZOLACJA TESTÓW - CZYSZCZENIE MIĘDZY TESTAMI
         * ============================================================
         * Jeśli test A wstawi wiersz do tabeli, a test B liczy wszystkie
         * wiersze i oczekuje konkretnej liczby, kolejność uruchamiania
         * testów zacznie mieć znaczenie - a to prosta droga do
         * niestabilnych ("flaky") testów. Rozwiązanie: PRZED każdym
         * testem czyścimy/odtwarzamy tabelę od zera (tu: DROP TABLE IF
         * EXISTS + CREATE TABLE w metodzie setUp(), wywoływanej na
         * początku każdego testXxx()).
         */

        System.out.println("=== URUCHAMIANIE MINI TEST-RUNNERA DLA UserDao ===\n");

        runTest("testSaveAssignsGeneratedId", _Lesson26_TestingDao::testSaveAssignsGeneratedId);
        runTest("testFindByIdReturnsSavedUser", _Lesson26_TestingDao::testFindByIdReturnsSavedUser);
        runTest("testFindByIdReturnsEmptyForMissingUser", _Lesson26_TestingDao::testFindByIdReturnsEmptyForMissingUser);
        runTest("testDeleteRemovesUser", _Lesson26_TestingDao::testDeleteRemovesUser);
        runTest("testFindAllReturnsAllInsertedRows", _Lesson26_TestingDao::testFindAllReturnsAllInsertedRows);
        runTest("test_CELOWO_FAILING_demonstracjaMechanizmu", _Lesson26_TestingDao::testCelowoFailing);

        System.out.println("\n=== WYNIK: " + passed + " passed, " + failed + " failed ===");

        /*
         * ============================================================
         * 🔹 A CO Z PRAWDZIWYM JUNIT?
         * ============================================================
         * W realnym projekcie do powyższego użylibyśmy frameworka JUnit
         * (już dodanego do pom.xml jako `spring-boot-starter-test`):
         * adnotacje `@Test`, `@BeforeEach` (zamiast ręcznego setUp()),
         * asercje `assertEquals`/`assertTrue` (zamiast ręcznych `if` +
         * `throw`), oraz gotowy raport z uruchomienia. Struktura logiczna
         * testu jest jednak DOKŁADNIE TAKA SAMA, jak w tej lekcji: 1)
         * przygotuj czysty stan bazy, 2) wykonaj operację przez DAO,
         * 3) zweryfikuj wynik. Napisanie mini-runnera "od zera" w tej
         * lekcji miało na celu pokazanie, CO tak naprawdę dzieje się pod
         * spodem, gdy JUnit "zielenieje" albo "czerwienieje" test.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DAO testujemy testami INTEGRACYJNYMI - z prawdziwym SQL na
         *   prawdziwym (testowym) schemacie, nie mockami.
         * - H2 in-memory = szybka, izolowana baza testowa, idealna do
         *   tego celu (Testcontainers = cięższa, ale bliższa produkcji
         *   alternatywa, wymaga Dockera).
         * - Każdy test czyści/odtwarza swoje dane PRZED uruchomieniem
         *   (setUp: DROP + CREATE), żeby testy nie wpływały na siebie.
         * - Mechanizm mini-test-runnera: metoda testu rzuca
         *   AssertionError, gdy warunek nie jest spełniony - runner łapie
         *   ten wyjątek i raportuje PASSED/FAILED.
         * - Świadomie uruchomiony test "CELOWO_FAILING" dowodzi, że
         *   mechanizm faktycznie wykrywa i raportuje porażki, a nie tylko
         *   zawsze drukuje PASSED.
         */
    }

    /** Wykonuje pojedynczy test i drukuje PASSED/FAILED. */
    private static void runTest(String name, TestCase test) {
        try {
            test.run();
            System.out.println("[PASSED] " + name);
            passed++;
        } catch (AssertionError e) {
            System.out.println("[FAILED] " + name + " -> " + e.getMessage());
            failed++;
        } catch (Exception e) {
            System.out.println("[FAILED] " + name + " -> nieoczekiwany wyjatek: " + e);
            failed++;
        }
    }

    // ================= TESTY =================

    private static void testSaveAssignsGeneratedId() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);

            long id = dao.save(new User(0, "Kasia"));

            assertTrue(id > 0, "wygenerowane id powinno byc > 0, bylo: " + id);
        }
    }

    private static void testFindByIdReturnsSavedUser() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);
            long id = dao.save(new User(0, "Adam"));

            Optional<User> found = dao.findById(id);

            assertTrue(found.isPresent(), "findById powinno znalezc zapisanego uzytkownika");
            assertEquals("Adam", found.get().name(), "nazwa uzytkownika");
        }
    }

    private static void testFindByIdReturnsEmptyForMissingUser() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);

            Optional<User> found = dao.findById(999L);

            assertTrue(found.isEmpty(), "findById dla nieistniejacego id powinno zwrocic Optional.empty()");
        }
    }

    private static void testDeleteRemovesUser() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);
            long id = dao.save(new User(0, "Ola"));

            dao.deleteById(id);

            assertTrue(dao.findById(id).isEmpty(), "po usunieciu findById powinno zwrocic empty");
        }
    }

    private static void testFindAllReturnsAllInsertedRows() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);
            dao.save(new User(0, "A"));
            dao.save(new User(0, "B"));
            dao.save(new User(0, "C"));

            var all = dao.findAll();

            assertEquals(3, all.size(), "liczba uzytkownikow po 3 save()");
        }
    }

    /**
     * ⚠️ TEN TEST CELOWO ZAWIERA BŁĘDNE ZAŁOŻENIE (oczekuje 5 wierszy,
     * mimo że wstawiamy tylko 2) - istnieje wyłącznie po to, by DOWIEŚĆ,
     * że nasz mini-runner NAPRAWDĘ wykrywa i poprawnie raportuje FAILED,
     * a nie tylko zawsze drukuje PASSED. W prawdziwym projekcie taki
     * test od razu zostałby poprawiony albo usunięty.
     */
    private static void testCelowoFailing() throws SQLException {
        try (Connection connection = freshConnection()) {
            UserDao dao = new UserDao(connection);
            dao.save(new User(0, "X"));
            dao.save(new User(0, "Y"));

            var all = dao.findAll();

            assertEquals(5, all.size(), "(CELOWO ZLE ZALOZENIE) liczba uzytkownikow");
        }
    }

    // ================= INFRASTRUKTURA TESTOWA =================

    /**
     * Otwiera POŁĄCZENIE do wspólnej bazy in-memory (żyjącej przez cały
     * czas trwania main() dzięki DB_CLOSE_DELAY=-1) i od razu odtwarza
     * czystą tabelę. Każdy test zamyka to połączenie w try-with-resources
     * - sama BAZA (dane) przechodzi między testami, ale POŁĄCZENIE jest
     * zawsze świeże i zawsze poprawnie zamknięte.
     */
    private static Connection freshConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:daolesson26;DB_CLOSE_DELAY=-1");
        setUp(connection);
        return connection;
    }

    /** Odtwarza czystą tabelę PRZED każdym testem - izolacja testów. */
    private static void setUp(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("""
                    CREATE TABLE users (
                        id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String context) {
        if (!expected.equals(actual)) {
            throw new AssertionError(context + " - oczekiwano <" + expected + ">, otrzymano <" + actual + ">");
        }
    }

    private record User(long id, String name) {
    }

    /** Prosty UserDao testowany w tej lekcji. */
    private static class UserDao {

        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        long save(User user) throws SQLException {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.name());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    keys.next();
                    return keys.getLong(1);
                }
            }
        }

        Optional<User> findById(long id) throws SQLException {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, name FROM users WHERE id = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(new User(rs.getLong("id"), rs.getString("name")));
                    }
                    return Optional.empty();
                }
            }
        }

        java.util.List<User> findAll() throws SQLException {
            java.util.List<User> result = new java.util.ArrayList<>();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT id, name FROM users ORDER BY id")) {
                while (rs.next()) {
                    result.add(new User(rs.getLong("id"), rs.getString("name")));
                }
            }
            return result;
        }

        void deleteById(long id) throws SQLException {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        }
    }
}
