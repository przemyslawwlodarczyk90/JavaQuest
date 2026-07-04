package com.example.javaquest._10_dao.Lesson03_DaoInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class _Lesson03_DaoInterface {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DLACZEGO DAO JAKO INTERFEJS?
         * ============================================================
         * W poprzednich lekcjach DAO było zwykłą klasą. W praktyce niemal
         * ZAWSZE definiuje się DAO jako INTERFEJS, a konkretną technologię
         * (JDBC, Hibernate, plik, baza NoSQL...) chowa się w OSOBNEJ klasie
         * implementującej ten interfejs. Dwa główne powody:
         *
         * 1) MOŻLIWOŚĆ PODMIANY IMPLEMENTACJI
         *    Reszta aplikacji (Service, Controller) programuje przeciwko
         *    interfejsowi UserDao, a nie przeciwko konkretnej klasie. Jeśli
         *    jutro JDBC zostanie zamienione na Hibernate (poznasz go w
         *    kolejnym rozdziale), wystarczy dostarczyć NOWĄ implementację
         *    interfejsu - kod korzystający z UserDao nie zmienia się wcale.
         *
         * 2) ŁATWE TESTOWANIE
         *    W testach jednostkowych warstwy Service nie chcemy uruchamiać
         *    prawdziwej bazy danych. Wystarczy podstawić PROSTĄ, testową
         *    implementację interfejsu (np. trzymającą dane w HashMap) -
         *    bez zmiany ani jednej linijki kodu testowanego Service.
         *
         * 🔹 KONTRAKT DAO DLA UŻYTKOWNIKA (User)
         */

        System.out.println("=== INTERFEJS UserDao ===");
        System.out.println("""
                public interface UserDao {
                    Optional<User> findById(Long id);
                    List<User> findAll();
                    User save(User user);
                    void update(User user);
                    void deleteById(Long id);
                }""");

        /*
         * ============================================================
         * 🔹 DWIE RÓŻNE IMPLEMENTACJE TEGO SAMEGO INTERFEJSU
         * ============================================================
         * - UserJdbcDao      - prawdziwa implementacja, laczy sie z baza H2
         *                      przez JDBC (PreparedStatement/ResultSet)
         * - UserInMemoryDao  - implementacja testowa, trzyma userow w HashMap
         *                      w pamieci, BEZ zadnej bazy danych
         *
         * Kod ponizej (demoUseUserDao) operuje WYŁĄCZNIE na interfejsie
         * UserDao - nie wie i nie musi wiedzieć, z ktora implementacja
         * rozmawia. Uruchomimy go DWA razy, raz z kazda implementacja,
         * i zobaczymy IDENTYCZNE zachowanie.
         */

        String url = "jdbc:h2:mem:daolesson03;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            System.out.println("\n=== URUCHOMIENIE Z UserJdbcDao (prawdziwa baza H2) ===");
            UserDao jdbcDao = new UserJdbcDao(connection);
            demoUseUserDao(jdbcDao);

            System.out.println("\n=== URUCHOMIENIE Z UserInMemoryDao (HashMap, bez bazy) ===");
            UserDao inMemoryDao = new UserInMemoryDao();
            demoUseUserDao(inMemoryDao);
        }

        /*
         * ============================================================
         * 🔍 ZWRÓĆ UWAGĘ
         * ============================================================
         * Metoda demoUseUserDao przyjmuje parametr typu UserDao (interfejs),
         * NIE UserJdbcDao ani UserInMemoryDao. Dzięki temu dokładnie ten sam
         * kod dał się uruchomić raz na prawdziwej bazie H2, raz na zwykłej
         * mapie w pamięci - z identycznym rezultatem po stronie wywołującego.
         * To właśnie jest sedno programowania "przeciwko interfejsowi,
         * a nie implementacji".
         */

        System.out.println("\n=== KONIEC LEKCJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DAO definiuje się jako INTERFEJS (kontrakt: jakie operacje są
         *   dostępne), a konkretną technologię chowa w klasie implementującej
         * - Zaleta 1: możliwość PODMIANY implementacji (JDBC -> Hibernate)
         *   bez zmiany kodu, który z DAO korzysta
         * - Zaleta 2: łatwe TESTOWANIE - w testach podstawiamy prostą
         *   implementację (np. in-memory) zamiast prawdziwej bazy danych
         * - Kod korzystający z DAO powinien programować przeciwko
         *   interfejsowi (UserDao), NIGDY przeciwko konkretnej klasie
         *   (UserJdbcDao)
         */
    }

    /**
     * Kod korzystający z DAO WYŁĄCZNIE przez interfejs - nie wie, czy pod spodem
     * jest prawdziwa baza H2, czy zwykła HashMap w pamięci.
     */
    private static void demoUseUserDao(UserDao userDao) {
        User saved = userDao.save(new User(null, "ania@example.com", "Ania"));
        System.out.println("Zapisano: " + saved);

        userDao.save(new User(null, "bartek@example.com", "Bartek"));

        System.out.println("findById(" + saved.id() + "): " + userDao.findById(saved.id()));
        System.out.println("findById(999): " + userDao.findById(999L));

        System.out.println("findAll(): " + userDao.findAll());

        User updated = new User(saved.id(), saved.email(), "Ania Nowak");
        userDao.update(updated);
        System.out.println("Po update: " + userDao.findById(saved.id()));

        userDao.deleteById(saved.id());
        System.out.println("Po deleteById: " + userDao.findById(saved.id()));
        System.out.println("findAll() po usunieciu: " + userDao.findAll());
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        first_name VARCHAR(100) NOT NULL
                    )
                    """);
        }
    }

    /** Prosty, niezmienny obiekt domenowy - identyczny niezależnie od implementacji DAO. */
    private record User(Long id, String email, String firstName) {
    }

    /**
     * KONTRAKT DAO - jedyne, co widzi warstwa Service. Nie wie nic o SQL
     * ani o HashMap - to szczegół implementacyjny ukryty w klasach poniżej.
     */
    private interface UserDao {
        Optional<User> findById(Long id);

        List<User> findAll();

        User save(User user);

        void update(User user);

        void deleteById(Long id);
    }

    /** Implementacja #1 - prawdziwa, oparta na JDBC i bazie H2. */
    private static class UserJdbcDao implements UserDao {

        private final Connection connection;

        UserJdbcDao(Connection connection) {
            this.connection = connection;
        }

        private User mapRow(ResultSet rs) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("email"), rs.getString("first_name"));
        }

        @Override
        public Optional<User> findById(Long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, email, first_name FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<User> findAll() {
            List<User> result = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, email, first_name FROM users ORDER BY id")) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public User save(User user) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (email, first_name) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.email());
                stmt.setString(2, user.firstName());
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return new User(keys.getLong(1), user.email(), user.firstName());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(User user) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET email = ?, first_name = ? WHERE id = ?")) {
                stmt.setString(1, user.email());
                stmt.setString(2, user.firstName());
                stmt.setLong(3, user.id());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void deleteById(Long id) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Implementacja #2 - testowa, trzyma dane w zwykłej HashMap w pamięci,
     * BEZ żadnej bazy danych ani JDBC. Typowa "atrapa" (test double) używana
     * w testach jednostkowych warstwy Service, żeby nie uruchamiać prawdziwej
     * bazy przy każdym teście.
     */
    private static class UserInMemoryDao implements UserDao {

        private final Map<Long, User> storage = new HashMap<>();
        private final AtomicLong idSequence = new AtomicLong(1);

        @Override
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public List<User> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public User save(User user) {
            long id = idSequence.getAndIncrement();
            User withId = new User(id, user.email(), user.firstName());
            storage.put(id, withId);
            return withId;
        }

        @Override
        public void update(User user) {
            storage.put(user.id(), user);
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }
    }
}
