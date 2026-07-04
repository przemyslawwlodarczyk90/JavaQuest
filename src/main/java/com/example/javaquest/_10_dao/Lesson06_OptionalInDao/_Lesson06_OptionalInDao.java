package com.example.javaquest._10_dao.Lesson06_OptionalInDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Optional;

public class _Lesson06_OptionalInDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 KIEDY ZWRACAĆ Optional Z DAO?
         * ============================================================
         * Metody DAO, które mogą zwrócić "0 ALBO 1 WYNIK" (a nie listę wielu
         * wyników), powinny zwracać Optional<T> zamiast:
         * - zwracać null, gdy nic nie znaleziono (klasyczne źródło
         *   NullPointerException, jeśli wywołujący zapomni sprawdzić),
         * - rzucać wyjątek za każdym razem, gdy nic nie znaleziono (brak
         *   wyniku to często NORMALNA sytuacja, a nie błąd - wyjątki
         *   powinny sygnalizować sytuacje WYJĄTKOWE).
         *
         * Typowe metody DAO zwracające Optional:
         * - findById(id)      -> Optional<User>
         * - findByEmail(email) -> Optional<User>
         *
         * Typowe metody DAO NIE zwracające Optional (bo z natury mogą
         * zwrócić WIELE wyników): findAll(), findByStatus() - te zwracają
         * List<T> (patrz Lesson07).
         *
         * 🔹 DLACZEGO NIE null?
         * Optional.empty() WYMUSZA na wywołującym świadomą decyzję, co
         * zrobić przy braku wyniku (kompilator "przypomina" o obsłudze,
         * bo Optional ma inne API niż T) - null pozwala o tym zapomnieć
         * i wybuchnąć NullPointerException gdzieś daleko od miejsca błędu.
         */

        String url = "jdbc:h2:mem:daolesson06;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            UserJdbcDao userDao = new UserJdbcDao(connection);
            User ania = userDao.save(new User(null, "ania@example.com", "Ania"));
            userDao.save(new User(null, "bartek@example.com", "Bartek"));

            System.out.println("=== findById ISTNIEJACEGO uzytkownika ===");
            Optional<User> znaleziony = userDao.findById(ania.id());
            System.out.println("Optional: " + znaleziony);

            System.out.println("\n=== findByEmail ISTNIEJACEGO uzytkownika ===");
            System.out.println("Optional: " + userDao.findByEmail("bartek@example.com"));

            /*
             * ============================================================
             * 🔹 TRZY STYLE OBSŁUGI Optional PO STRONIE WYWOŁUJĄCEGO
             * ============================================================
             * 1) orElseThrow() - "musi istnieć, inaczej to błąd"
             * 2) orElse(wartoscDomyslna) - "jak nie ma, uzyj wartosci domyslnej"
             * 3) ifPresent(...) - "zrob cos TYLKO jesli wynik istnieje"
             */

            System.out.println("\n=== STYL 1: orElseThrow() - dla ISTNIEJACEGO id ===");
            User pewny = userDao.findById(ania.id())
                    .orElseThrow(() -> new NoSuchElementException("Brak uzytkownika o id=" + ania.id()));
            System.out.println("Znaleziono na pewno: " + pewny);

            System.out.println("\n=== STYL 1: orElseThrow() - dla NIEISTNIEJACEGO id ===");
            try {
                userDao.findById(999L)
                        .orElseThrow(() -> new NoSuchElementException("Brak uzytkownika o id=999"));
            } catch (NoSuchElementException e) {
                System.out.println("Zlapano oczekiwany wyjatek: " + e.getMessage());
            }

            System.out.println("\n=== STYL 2: orElse(wartoscDomyslna) - dla NIEISTNIEJACEGO id ===");
            User goscZastepczy = new User(-1L, "brak@example.com", "Gosc");
            User wynik = userDao.findById(999L).orElse(goscZastepczy);
            System.out.println("Uzyto wartosci domyslnej: " + wynik);

            System.out.println("\n=== STYL 3: ifPresent(...) - reakcja TYLKO gdy jest wynik ===");
            userDao.findById(ania.id())
                    .ifPresent(user -> System.out.println("Znaleziono, wysylam powitalny e-mail do: " + user.email()));
            userDao.findById(999L)
                    .ifPresent(user -> System.out.println("TO SIE NIE WYKONA, bo brak wyniku dla id=999"));
            System.out.println("(brak wypisania powyzej dla id=999 - to oczekiwane zachowanie ifPresent)");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Optional<T> zwracamy z metod DAO, które dają "0 albo 1 wynik"
         *   (findById, findByEmail), NIGDY z metod zwracających listy
         * - Zaleta: brak nulli, wymuszona świadoma obsługa braku wyniku
         * - Trzy typowe style obsługi po stronie wywołującego:
         *   1) orElseThrow() - gdy brak wyniku to błąd
         *   2) orElse(wartoscDomyslna) - gdy chcemy wartość zastępczą
         *   3) ifPresent(...) - gdy chcemy zareagować TYLKO, gdy wynik istnieje
         */
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

    private record User(Long id, String email, String firstName) {
    }

    private static class UserJdbcDao {

        private final Connection connection;

        UserJdbcDao(Connection connection) {
            this.connection = connection;
        }

        private User mapRow(ResultSet rs) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("email"), rs.getString("first_name"));
        }

        User save(User user) {
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
                throw new RuntimeException("Blad podczas zapisu uzytkownika", e);
            }
        }

        /** 0 albo 1 wynik -> Optional. */
        Optional<User> findById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, email, first_name FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas wyszukiwania po id", e);
            }
        }

        /** Też 0 albo 1 wynik (email jest unikalny w naszej domenie) -> Optional. */
        Optional<User> findByEmail(String email) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, email, first_name FROM users WHERE email = ?")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas wyszukiwania po e-mailu", e);
            }
        }
    }
}
