package com.example.javaquest._10_dao.Lesson19_UnitOfWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson19_UnitOfWork {

    private static final String URL = "jdbc:h2:mem:daolesson19;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST UNIT OF WORK?
         * ============================================================
         * W lekcjach 17-18 ręcznie pisaliśmy ten sam szablon w każdej
         * metodzie Service, która potrzebowała transakcji:
         *
         *   connection.setAutoCommit(false);
         *   try {
         *       ... kilka wywolan DAO na tym samym connection ...
         *       connection.commit();
         *   } catch (Exception e) {
         *       connection.rollback();
         *   } finally {
         *       connection.setAutoCommit(true);
         *   }
         *
         * UNIT OF WORK to nazwa na ten właśnie wzorzec: JEDNA operacja
         * biznesowa = JEDNA transakcja = JEDNA "jednostka pracy", która
         * albo w całości się powiedzie (commit), albo w całości zostanie
         * cofnięta (rollback).
         *
         * Zamiast przepisywać ten szablon w każdej metodzie Service,
         * możemy wydzielić go RAZ do małej, reużywalnej klasy pomocniczej.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * Unit of Work to opakowanie na "otwórz transakcję, wykonaj blok
         * kodu, zacommituj albo wycofaj" - tak, żeby Service nie musiał
         * za każdym razem pisać tego ręcznie.
         */

        try (Connection setupConnection = DriverManager.getConnection(URL)) {
            setUpSchema(setupConnection);
        }

        /*
         * ============================================================
         * 🔍 Generyczna klasa UnitOfWork
         * ============================================================
         * `UnitOfWork.execute(...)` przyjmuje funkcyjny interfejs SqlWork<T>
         * (blok kodu, który dostaje Connection i zwraca wynik typu T).
         * Cała logika otwierania transakcji, commitu i rollbacku jest
         * napisana RAZ, wewnątrz UnitOfWork - Service tylko z niej korzysta.
         */

        UnitOfWork unitOfWork = new UnitOfWork(URL);
        OrderService orderService = new OrderService(unitOfWork);

        System.out.println("=== Sukces: cala praca wykonana w jednej jednostce pracy ===");
        long orderId = orderService.placeOrder(1L, 2);
        System.out.println("Zwrocone id zamowienia: " + orderId);
        printState(URL, "Stan po udanym zamowieniu");

        System.out.println("\n=== Porazka: rollback wykonany automatycznie przez UnitOfWork ===");
        try {
            orderService.placeOrder(1L, 999);
        } catch (RuntimeException e) {
            System.out.println("Service otrzymal wyjatek: " + e.getMessage());
        }
        printState(URL, "Stan po nieudanym zamowieniu (oczekujemy braku zmian)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Unit of Work formalizuje wzorzec z lekcji 17/18: jedna
         *   operacja biznesowa = jedna transakcja = jeden commit albo
         *   jeden rollback
         * - Generyczna klasa `UnitOfWork` przyjmuje blok kodu (SqlWork<T>)
         *   i SAMA zajmuje się otwarciem połączenia, wyłączeniem
         *   autocommit, commitem/rollbackiem i zamknięciem połączenia
         * - Service pisze WYŁĄCZNIE logikę biznesową wewnątrz przekazanego
         *   bloku kodu - cały "boilerplate" transakcyjny jest ukryty
         * - To ten sam mechanizm co w lekcji 17-18, tylko wydzielony do
         *   wielokrotnego użytku - unikamy kopiowania try/catch/finally
         *   w każdej metodzie Service, która potrzebuje transakcji
         */
    }

    private static void printState(String url, String label) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            System.out.println(label + ":");
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS ile FROM orders")) {
                rs.next();
                System.out.println(" - liczba zamowien: " + rs.getInt("ile"));
            }
            try (ResultSet rs = stmt.executeQuery("SELECT quantity_in_stock FROM products WHERE id = 1")) {
                rs.next();
                System.out.println(" - stan magazynowy produktu #1: " + rs.getInt("quantity_in_stock"));
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE products (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        quantity_in_stock INT NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        product_id BIGINT NOT NULL,
                        quantity INT NOT NULL
                    )
                    """);
            stmt.execute("INSERT INTO products (id, name, quantity_in_stock) VALUES (1, 'Klawiatura', 10)");
        }
    }

    /** Funkcyjny interfejs opisujący "kawałek pracy", który ma się wykonać w JEDNEJ transakcji. */
    @FunctionalInterface
    private interface SqlWork<T> {
        T execute(Connection connection) throws SQLException;
    }

    /**
     * 📌 UNIT OF WORK - generyczna klasa pomocnicza: otwiera transakcję,
     * wykonuje przekazany blok kodu, commituje albo wycofuje, zawsze
     * zamyka połączenie.
     */
    private static class UnitOfWork {
        private final String url;

        UnitOfWork(String url) {
            this.url = url;
        }

        <T> T execute(SqlWork<T> work) {
            try (Connection connection = DriverManager.getConnection(url)) {
                connection.setAutoCommit(false);
                try {
                    T result = work.execute(connection);
                    connection.commit();
                    return result;
                } catch (Exception e) {
                    connection.rollback();
                    throw new RuntimeException("Transakcja wycofana (rollback): " + e.getMessage(), e);
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Nie udalo sie otworzyc polaczenia", e);
            }
        }
    }

    /** Service korzysta z UnitOfWork - nie pisze rocznie setAutoCommit/commit/rollback. */
    private static class OrderService {
        private final UnitOfWork unitOfWork;
        private final OrderDao orderDao = new OrderDao();
        private final ProductDao productDao = new ProductDao();

        OrderService(UnitOfWork unitOfWork) {
            this.unitOfWork = unitOfWork;
        }

        long placeOrder(long productId, int quantity) {
            return unitOfWork.execute(connection -> {
                productDao.decreaseStock(connection, productId, quantity);
                long orderId = orderDao.insert(connection, productId, quantity);
                System.out.println("(wewnatrz UnitOfWork) utworzono zamowienie #" + orderId
                        + " i zmniejszono stan magazynowy");
                return orderId;
            });
        }
    }

    private static class OrderDao {
        long insert(Connection connection, long productId, int quantity) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO orders (product_id, quantity) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, productId);
                stmt.setInt(2, quantity);
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return keys.getLong(1);
                }
            }
        }
    }

    private static class ProductDao {
        void decreaseStock(Connection connection, long productId, int quantity) throws SQLException {
            try (PreparedStatement checkStmt = connection.prepareStatement(
                    "SELECT quantity_in_stock FROM products WHERE id = ?")) {
                checkStmt.setLong(1, productId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    rs.next();
                    int available = rs.getInt("quantity_in_stock");
                    if (available < quantity) {
                        throw new IllegalStateException("Za malo produktu na stanie (dostepne: "
                                + available + ", zadano: " + quantity + ")");
                    }
                }
            }
            try (PreparedStatement updateStmt = connection.prepareStatement(
                    "UPDATE products SET quantity_in_stock = quantity_in_stock - ? WHERE id = ?")) {
                updateStmt.setInt(1, quantity);
                updateStmt.setLong(2, productId);
                updateStmt.executeUpdate();
            }
        }
    }
}
