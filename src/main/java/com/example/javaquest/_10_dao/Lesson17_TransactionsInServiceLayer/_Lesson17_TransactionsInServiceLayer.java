package com.example.javaquest._10_dao.Lesson17_TransactionsInServiceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson17_TransactionsInServiceLayer {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PROBLEM: OPERACJA BIZNESOWA = WIELE ZAPYTAŃ SQL
         * ============================================================
         * W poprzedniej lekcji `OrderService.placeOrder` wykonywał
         * DWIE osobne operacje insert (order + order_item) BEZ wspólnej
         * transakcji. Każdy insert miał WŁASNY, niejawny autocommit.
         *
         * To rodzi pytanie: co się stanie, jeśli DRUGI insert (albo trzeci
         * krok - zmniejszenie stanu magazynowego) się nie powiedzie,
         * PO TYM jak pierwszy insert już się wykonał (i zacommitował)?
         *
         * Odpowiedź: zostaniemy z NIESPÓJNYMI danymi - zamówienie #7
         * istnieje w tabeli orders, ale nie ma dla niego żadnej pozycji
         * w order_items. To jest dokładnie problem, który TRANSAKCJE
         * mają rozwiązywać.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * JEDNA operacja biznesowa (np. "złóż zamówienie") powinna być
         * JEDNĄ transakcją: albo WSZYSTKIE kroki się powiodą (jeden
         * commit), albo ŻADEN z nich nie zostawi śladu (jeden rollback).
         */

        String url = "jdbc:h2:mem:daolesson17;DB_CLOSE_DELAY=-1";
        try (Connection setupConnection = DriverManager.getConnection(url)) {
            setUpSchema(setupConnection);
        }

        /*
         * ============================================================
         * 🔍 Transakcja obejmująca WIELE DAO
         * ============================================================
         * OrderService.placeOrder poniżej:
         * 1. otwiera JEDNO połączenie i wyłącza autocommit
         * 2. przekazuje TO SAMO połączenie do WSZYSTKICH DAO (order,
         *    order_item, product)
         * 3. po wszystkich krokach woła JEDEN commit
         * 4. jeśli którykolwiek krok rzuci wyjątek - woła JEDEN rollback,
         *    cofający WSZYSTKIE kroki wykonane do tej pory w tej transakcji
         *
         * To jest kluczowa różnica względem poprzedniej lekcji: Connection
         * NIE jest już tworzone osobno w każdym DAO - jest tworzone RAZ,
         * w Service, i PRZEKAZYWANE jako parametr do metod DAO.
         */

        System.out.println("=== Sukces: wszystkie kroki OK -> jeden COMMIT ===");
        try (Connection connection = DriverManager.getConnection(url)) {
            OrderService orderService = new OrderService(connection);
            orderService.placeOrder(1L, 2);
        }
        printState(url, "Po udanym zamowieniu");

        System.out.println("\n=== Porazka w 3. kroku -> jeden ROLLBACK cofa WSZYSTKO ===");
        try (Connection connection = DriverManager.getConnection(url)) {
            OrderService orderService = new OrderService(connection);
            // zamawiamy wiecej niz jest na stanie (8 sztuk, zostalo 8 po
            // pierwszym zamowieniu, wiec 100 na pewno przekroczy stan)
            orderService.placeOrder(1L, 100);
        }
        printState(url, "Po nieudanym zamowieniu (oczekujemy BRAKU zmian)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JEDNA operacja biznesowa (placeOrder) = JEDNA transakcja =
         *   JEDNO Connection ze `setAutoCommit(false)`
         * - To SAMO Connection trzeba przekazać do WSZYSTKICH DAO
         *   biorących udział w tej operacji (order, order_items, products)
         * - Commit wołamy RAZ, na samym końcu, gdy WSZYSTKIE kroki się
         *   powiodły
         * - Rollback wołamy RAZ, w bloku catch, gdy KTÓRYKOLWIEK krok
         *   zawiódł - cofa to WSZYSTKIE zmiany zrobione od początku
         *   transakcji, nie tylko ten jeden nieudany krok
         * - Kolejna lekcja pokaże, dlaczego to podejście (Connection
         *   przekazywane jako parametr) jest KONIECZNE, a nie tylko
         *   "ładniejsze" - i co się psuje, gdy każdy DAO otwiera własne
         *   połączenie
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
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS ile FROM order_items")) {
                rs.next();
                System.out.println(" - liczba pozycji zamowien: " + rs.getInt("ile"));
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
                        product_id BIGINT NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE order_items (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        quantity INT NOT NULL
                    )
                    """);
            stmt.execute("INSERT INTO products (id, name, quantity_in_stock) VALUES (1, 'Klawiatura', 10)");
        }
    }

    /**
     * 📌 Service z PRAWDZIWĄ transakcją: jedno Connection, jeden commit
     * albo jeden rollback, obejmujący wszystkie kroki.
     */
    private static class OrderService {

        private final Connection connection;
        private final OrderDao orderDao;
        private final OrderItemDao orderItemDao;
        private final ProductDao productDao;

        OrderService(Connection connection) {
            this.connection = connection;
            this.orderDao = new OrderDao();
            this.orderItemDao = new OrderItemDao();
            this.productDao = new ProductDao();
        }

        void placeOrder(long productId, int quantity) {
            try {
                connection.setAutoCommit(false);

                // krok 1: utworz zamowienie
                long orderId = orderDao.insert(connection, productId);
                System.out.println("Krok 1 OK: utworzono zamowienie #" + orderId);

                // krok 2: dodaj pozycje zamowienia
                orderItemDao.insert(connection, orderId, productId, quantity);
                System.out.println("Krok 2 OK: dodano pozycje (produkt=" + productId + ", ilosc=" + quantity + ")");

                // krok 3: zmniejsz stan magazynowy (moze rzucic wyjatek,
                // jesli na stanie jest za malo sztuk)
                productDao.decreaseStock(connection, productId, quantity);
                System.out.println("Krok 3 OK: zmniejszono stan magazynowy");

                connection.commit();
                System.out.println("-> COMMIT: wszystkie kroki zatwierdzone");

            } catch (SQLException | IllegalStateException e) {
                System.out.println("-> Blad w trakcie transakcji: " + e.getMessage());
                try {
                    connection.rollback();
                    System.out.println("-> ROLLBACK: wszystkie kroki tej transakcji cofniete");
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException("Nie udalo sie wykonac rollbacku", rollbackEx);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException("Nie udalo sie przywrocic autocommit", e);
                }
            }
        }
    }

    /** DAO przyjmujące Connection jako PARAMETR - nie tworzy własnego! */
    private static class OrderDao {
        long insert(Connection connection, long productId) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO orders (product_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, productId);
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return keys.getLong(1);
                }
            }
        }
    }

    private static class OrderItemDao {
        void insert(Connection connection, long orderId, long productId, int quantity) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)")) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, productId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
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
