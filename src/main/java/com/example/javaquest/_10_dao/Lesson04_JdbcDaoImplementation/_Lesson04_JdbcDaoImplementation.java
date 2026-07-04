package com.example.javaquest._10_dao.Lesson04_JdbcDaoImplementation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Lesson04_JdbcDaoImplementation {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 KONKRETNA IMPLEMENTACJA DAO PRZEZ JDBC
         * ============================================================
         * W poprzedniej lekcji poznałeś UserDao jako interfejs. Teraz
         * skupiamy się na tym, JAK dokładnie wygląda w środku implementacja
         * oparta na JDBC - łącząc wszystko, co poznałeś w rozdziale _09_jdbc:
         * - PreparedStatement do bezpiecznego przekazywania parametrów,
         * - ResultSet do odczytu wyniku zapytania,
         * - wydzieloną metodę mapRow() do zamiany wiersza na obiekt Java
         *   (dokładnie ten sam wzorzec co w _09_jdbc/Lesson17_ResultSetMapping),
         * - try-with-resources do automatycznego zamykania zasobów.
         *
         * DAO oparte na JDBC to zwykle NAJWIĘKSZA (najdłuższa) klasa w
         * warstwie dostępu do danych - bo to właśnie tutaj "ląduje" cały
         * SQL. To normalne i oczekiwane - dzięki temu SQL jest zebrany w
         * JEDNYM miejscu, zamiast rozproszony po całej aplikacji.
         */

        String url = "jdbc:h2:mem:daolesson04;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            System.out.println("=== UserJdbcDao - pelna implementacja przez JDBC ===");
            UserJdbcDao userDao = new UserJdbcDao(connection);

            var ania = userDao.save(new User(null, "ania@example.com", "Ania"));
            var bartek = userDao.save(new User(null, "bartek@example.com", "Bartek"));
            System.out.println("Zapisano: " + ania);
            System.out.println("Zapisano: " + bartek);

            System.out.println("findById(" + ania.id() + "): " + userDao.findById(ania.id()));
            System.out.println("findAll(): " + userDao.findAll());

            /*
             * ============================================================
             * 🔹 KOLEJNE DAO NA TYM SAMYM WZORCU: ProductJdbcDao
             * ============================================================
             * Ten sam schemat (PreparedStatement + ResultSet + mapRow)
             * powtarza się przy KAŻDYM DAO, niezależnie od tabeli. Poniżej
             * pełna implementacja ProductJdbcDao dla tabeli "products" -
             * zwróć uwagę, jak bardzo przypomina strukturę UserJdbcDao,
             * mimo że dotyczy zupełnie innej encji domenowej.
             */

            System.out.println("\n=== ProductJdbcDao - drugi przyklad tego samego wzorca ===");
            ProductJdbcDao productDao = new ProductJdbcDao(connection);

            var keyboard = productDao.save(new Product(null, "Klawiatura mechaniczna", new BigDecimal("299.00")));
            var mouse = productDao.save(new Product(null, "Mysz bezprzewodowa", new BigDecimal("129.50")));
            System.out.println("Zapisano: " + keyboard);
            System.out.println("Zapisano: " + mouse);

            System.out.println("findById(" + keyboard.id() + "): " + productDao.findById(keyboard.id()));
            System.out.println("findAll(): " + productDao.findAll());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Implementacja DAO przez JDBC to miejsce, gdzie ląduje cały SQL:
         *   PreparedStatement (bezpieczne parametry), ResultSet (odczyt
         *   wyniku), mapRow() (zamiana wiersza na obiekt Java)
         * - Wzorzec jest IDENTYCZNY niezależnie od tabeli/encji - UserJdbcDao
         *   i ProductJdbcDao różnią się nazwami kolumn i pól, ale mają tę
         *   samą strukturę metod (save, findById, findAll)
         * - Każdy PreparedStatement/ResultSet jest zamykany automatycznie
         *   przez try-with-resources (poznane w _09_jdbc/Lesson13)
         * - Dzięki takiej implementacji cała reszta aplikacji (Service,
         *   Controller) może korzystać z DAO przez interfejs, nie martwiąc
         *   się ani jedną linijką SQL
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
            stmt.execute("""
                    CREATE TABLE products (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(150) NOT NULL,
                        price DECIMAL(10,2) NOT NULL
                    )
                    """);
        }
    }

    private record User(Long id, String email, String firstName) {
    }

    private record Product(Long id, String name, BigDecimal price) {
    }

    /** Pełna implementacja DAO dla encji User, oparta na czystym JDBC. */
    private static class UserJdbcDao {

        private final Connection connection;

        UserJdbcDao(Connection connection) {
            this.connection = connection;
        }

        /** Mapuje BIEŻĄCY wiersz ResultSet (po rs.next()) na obiekt User. */
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
    }

    /**
     * Ten sam wzorzec co UserJdbcDao, ale dla encji Product. Zwróć uwagę na
     * strukturalne podobieństwo obu klas - to typowy "szablon" DAO opartego
     * na JDBC, który będziesz powtarzać dla każdej kolejnej tabeli.
     */
    private static class ProductJdbcDao {

        private final Connection connection;

        ProductJdbcDao(Connection connection) {
            this.connection = connection;
        }

        private Product mapRow(ResultSet rs) throws SQLException {
            return new Product(rs.getLong("id"), rs.getString("name"), rs.getBigDecimal("price"));
        }

        Product save(Product product) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO products (name, price) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, product.name());
                stmt.setBigDecimal(2, product.price());
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return new Product(keys.getLong(1), product.name(), product.price());
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas zapisu produktu", e);
            }
        }

        Optional<Product> findById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, name, price FROM products WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas wyszukiwania produktu", e);
            }
        }

        List<Product> findAll() {
            List<Product> result = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, name, price FROM products ORDER BY id")) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas pobierania produktow", e);
            }
        }
    }
}
