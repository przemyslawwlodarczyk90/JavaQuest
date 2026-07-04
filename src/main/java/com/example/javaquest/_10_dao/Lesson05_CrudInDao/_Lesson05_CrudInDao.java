package com.example.javaquest._10_dao.Lesson05_CrudInDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Lesson05_CrudInDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CRUD W DAO
         * ============================================================
         * CRUD to skrót od czterech podstawowych operacji na danych:
         * - CREATE  (utworzenie)  -> save/insert
         * - READ    (odczyt)      -> findById/findAll
         * - UPDATE  (aktualizacja)-> update
         * - DELETE  (usuniecie)   -> deleteById
         *
         * Prawie każde DAO w praktyce implementuje te cztery operacje jako
         * fundament, a do tego dokłada dwie przydatne metody pomocnicze:
         * - existsById(id) -> czy dany rekord w ogóle istnieje (bez
         *   pobierania całego obiektu - szybsze niż findById, gdy chcemy
         *   tylko sprawdzić istnienie)
         * - count() -> ile rekordów jest w tabeli (np. do paginacji albo
         *   statystyk)
         *
         * W tej lekcji rozbudowujemy UserJdbcDao z poprzednich lekcji
         * o WSZYSTKIE operacje CRUD + existsById + count.
         */

        String url = "jdbc:h2:mem:daolesson05;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            UserJdbcDao userDao = new UserJdbcDao(connection);

            System.out.println("=== CREATE ===");
            User ania = userDao.save(new User(null, "ania@example.com", "Ania"));
            User bartek = userDao.save(new User(null, "bartek@example.com", "Bartek"));
            User celina = userDao.save(new User(null, "celina@example.com", "Celina"));
            System.out.println("Utworzono: " + ania);
            System.out.println("Utworzono: " + bartek);
            System.out.println("Utworzono: " + celina);

            System.out.println("\n=== READ (findById / findAll) ===");
            System.out.println("findById(" + ania.id() + "): " + userDao.findById(ania.id()));
            System.out.println("findAll(): " + userDao.findAll());

            System.out.println("\n=== existsById ===");
            System.out.println("existsById(" + bartek.id() + "): " + userDao.existsById(bartek.id()));
            System.out.println("existsById(999): " + userDao.existsById(999L));

            System.out.println("\n=== count ===");
            System.out.println("count(): " + userDao.count());

            System.out.println("\n=== UPDATE ===");
            User zaktualizowana = new User(celina.id(), "celina.nowak@example.com", "Celina Nowak");
            userDao.update(zaktualizowana);
            System.out.println("Po update: " + userDao.findById(celina.id()));

            System.out.println("\n=== DELETE ===");
            userDao.deleteById(bartek.id());
            System.out.println("existsById(" + bartek.id() + ") po usunieciu: " + userDao.existsById(bartek.id()));
            System.out.println("count() po usunieciu: " + userDao.count());
            System.out.println("findAll() po usunieciu: " + userDao.findAll());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CRUD = Create, Read, Update, Delete - fundamentalne operacje
         *   każdego DAO
         * - Create -> save, Read -> findById/findAll, Update -> update,
         *   Delete -> deleteById
         * - existsById(id) - sprawdza istnienie rekordu (np. SELECT COUNT(*)
         *   i rs.getInt(1) > 0, albo SELECT 1 ... i rs.next()) - często
         *   szybsze niż pobieranie całego obiektu przez findById
         * - count() - zwraca liczbę wszystkich rekordów w tabeli
         *   (SELECT COUNT(*) FROM ...)
         * - Te operacje razem tworzą "komplet" typowego DAO, na którym
         *   bazują niemal wszystkie kolejne lekcje tego rozdziału
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

    /** UserJdbcDao rozbudowany o pełny komplet operacji CRUD + existsById + count. */
    private static class UserJdbcDao {

        private final Connection connection;

        UserJdbcDao(Connection connection) {
            this.connection = connection;
        }

        private User mapRow(ResultSet rs) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("email"), rs.getString("first_name"));
        }

        // ---------- CREATE ----------
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

        // ---------- READ ----------
        Optional<User> findById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, email, first_name FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas wyszukiwania uzytkownika", e);
            }
        }

        List<User> findAll() {
            List<User> result = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, email, first_name FROM users ORDER BY id")) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas pobierania uzytkownikow", e);
            }
        }

        // ---------- UPDATE ----------
        void update(User user) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET email = ?, first_name = ? WHERE id = ?")) {
                stmt.setString(1, user.email());
                stmt.setString(2, user.firstName());
                stmt.setLong(3, user.id());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas aktualizacji uzytkownika", e);
            }
        }

        // ---------- DELETE ----------
        void deleteById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas usuwania uzytkownika", e);
            }
        }

        // ---------- POMOCNICZE: existsById, count ----------
        boolean existsById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas sprawdzania istnienia uzytkownika", e);
            }
        }

        long count() {
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
                rs.next();
                return rs.getLong(1);
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas liczenia uzytkownikow", e);
            }
        }
    }
}
