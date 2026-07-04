package com.example.javaquest._10_dao.Lesson16_ServiceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class _Lesson16_ServiceLayer {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST WARSTWA SERVICE?
         * ============================================================
         * Do tej pory mieliśmy dwie warstwy:
         * - DAO (dostęp do danych - SQL, Connection, ResultSet)
         * - kod wywołujący DAO bezpośrednio (np. main albo "kontroler")
         *
         * W realnej aplikacji między nimi pojawia się TRZECIA warstwa:
         * WARSTWA SERVICE (czasem nazywana "warstwą logiki biznesowej").
         *
         * Architektura warstwowa wygląda wtedy tak:
         *
         *   Kontroler / main()  ->  Service  ->  DAO  ->  baza danych
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * Service ODPOWIADA ZA LOGIKĘ BIZNESOWĄ i ORKIESTRACJĘ wielu DAO
         * w jednej operacji, DAO odpowiada TYLKO za pojedyncze operacje
         * na jednej tabeli/encji.
         */

        /*
         * ============================================================
         * 🔍 PO CO ODDZIELNA WARSTWA, SKORO DAO JUŻ ISTNIEJE?
         * ============================================================
         * Wyobraź sobie operację "złóż zamówienie". Żeby to zrobić, trzeba:
         * 1. sprawdzić, czy użytkownik istnieje (UserDao)
         * 2. sprawdzić, czy produkt istnieje i czy jest go wystarczająco
         *    dużo na stanie (ProductDao)
         * 3. sprawdzić, że zamawiana ilość > 0 (walidacja - reguła
         *    biznesowa, NIE zapytanie SQL!)
         * 4. utworzyć zamówienie (OrderDao)
         * 5. utworzyć pozycję zamówienia (OrderItemDao)
         *
         * Gdzie umieścić ten kod? W KAŻDYM DAO z osobna? Nie - żaden
         * pojedynczy DAO nie powinien wiedzieć o istnieniu pozostałych.
         * UserDao zna tylko tabelę users, ProductDao tylko products, itd.
         *
         * Właśnie tu wchodzi Service: `OrderService` ZNA wszystkie
         * potrzebne DAO i ORKIESTRUJE ich wywołania w odpowiedniej
         * kolejności, dopilnowując reguł biznesowych po drodze.
         */

        String url = "jdbc:h2:mem:daolesson16;DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            UserDao userDao = new UserDao(connection);
            ProductDao productDao = new ProductDao(connection);
            OrderDao orderDao = new OrderDao(connection);
            OrderItemDao orderItemDao = new OrderItemDao(connection);

            OrderService orderService = new OrderService(userDao, productDao, orderDao, orderItemDao);

            /*
             * ============================================================
             * 🟢 Wywołanie operacji biznesowej przez Service
             * ============================================================
             * Kod wywołujący (tu: main) NIE wie NIC o SQL. Woła jedną
             * metodę `placeOrder`, a CAŁA logika (walidacja + orkiestracja
             * DAO) jest ukryta wewnątrz OrderService.
             */

            System.out.println("=== Poprawne zamowienie ===");
            orderService.placeOrder(1L, 1L, 2);

            System.out.println("\n=== Zamowienie z niepoprawna iloscia (0) ===");
            orderService.placeOrder(1L, 1L, 0);

            System.out.println("\n=== Zamowienie na nieistniejacy produkt ===");
            orderService.placeOrder(1L, 999L, 1);

            System.out.println("\n=== Zamowienie od nieistniejacego uzytkownika ===");
            orderService.placeOrder(999L, 1L, 1);

            System.out.println("\n=== Stan zamowien po probach ===");
            printOrders(connection);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Warstwa Service leży MIĘDZY kontrolerem/main() a warstwą DAO
         * - Service ORKIESTRUJE wiele DAO w jednej operacji biznesowej
         *   (np. OrderService korzysta z UserDao, ProductDao, OrderDao,
         *   OrderItemDao)
         * - Service zawiera LOGIKĘ BIZNESOWĄ i WALIDACJĘ (np. "ilość musi
         *   być dodatnia", "produkt musi istnieć") - DAO tego NIE robi
         * - Pojedynczy DAO nie powinien znać innych DAO ani reguł
         *   biznesowych - to zadanie Service
         * - W tej lekcji operacja placeOrder NIE jest jeszcze
         *   transakcyjna (każdy insert to osobne autocommit) - o tym,
         *   dlaczego to problem i jak go naprawić, mówi kolejna lekcja
         */
    }

    private static void printOrders(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT o.id, o.user_id, oi.product_id, oi.quantity FROM orders o "
                             + "JOIN order_items oi ON oi.order_id = o.id ORDER BY o.id")) {
            while (rs.next()) {
                System.out.println(" - zamowienie #" + rs.getLong("id") + " uzytkownik="
                        + rs.getLong("user_id") + " produkt=" + rs.getLong("product_id")
                        + " ilosc=" + rs.getInt("quantity"));
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
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
                        user_id BIGINT NOT NULL
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
            stmt.execute("INSERT INTO users (id, name) VALUES (1, 'Ania')");
            stmt.execute("INSERT INTO products (id, name, quantity_in_stock) VALUES (1, 'Klawiatura', 10)");
        }
    }

    /**
     * 📌 WARSTWA SERVICE - logika biznesowa + orkiestracja wielu DAO.
     * Nie zawiera ani jednej linijki SQL - cały dostęp do danych
     * odbywa się przez DAO.
     */
    private static class OrderService {

        private final UserDao userDao;
        private final ProductDao productDao;
        private final OrderDao orderDao;
        private final OrderItemDao orderItemDao;

        OrderService(UserDao userDao, ProductDao productDao, OrderDao orderDao, OrderItemDao orderItemDao) {
            this.userDao = userDao;
            this.productDao = productDao;
            this.orderDao = orderDao;
            this.orderItemDao = orderItemDao;
        }

        void placeOrder(long userId, long productId, int quantity) throws SQLException {
            // 1) walidacja biznesowa - NIE wymaga zapytania do bazy
            if (quantity <= 0) {
                System.out.println("Odrzucono: ilosc musi byc dodatnia (podano " + quantity + ")");
                return;
            }

            // 2) walidacja obecnosci danych - wymaga zapytan (przez DAO!)
            Optional<String> user = userDao.findNameById(userId);
            if (user.isEmpty()) {
                System.out.println("Odrzucono: uzytkownik #" + userId + " nie istnieje");
                return;
            }

            Optional<Product> product = productDao.findById(productId);
            if (product.isEmpty()) {
                System.out.println("Odrzucono: produkt #" + productId + " nie istnieje");
                return;
            }

            if (product.get().quantityInStock() < quantity) {
                System.out.println("Odrzucono: za malo produktu na stanie (dostepne: "
                        + product.get().quantityInStock() + ", zadano: " + quantity + ")");
                return;
            }

            // 3) orkiestracja DAO - kazde wywolanie to osobna operacja (na razie
            //    bez wspolnej transakcji, patrz PODSUMOWANIE powyzej)
            long orderId = orderDao.insert(userId);
            orderItemDao.insert(orderId, productId, quantity);

            System.out.println("Zamowienie #" + orderId + " zlozone przez " + user.get()
                    + ": produkt=" + product.get().name() + " ilosc=" + quantity);
        }
    }

    private record Product(long id, String name, int quantityInStock) {
    }

    /** DAO - zna TYLKO tabelę users, nic o zamówieniach ani produktach. */
    private static class UserDao {
        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        Optional<String> findNameById(long id) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT name FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(rs.getString("name")) : Optional.empty();
                }
            }
        }
    }

    /** DAO - zna TYLKO tabelę products. */
    private static class ProductDao {
        private final Connection connection;

        ProductDao(Connection connection) {
            this.connection = connection;
        }

        Optional<Product> findById(long id) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, name, quantity_in_stock FROM products WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(new Product(rs.getLong("id"), rs.getString("name"),
                            rs.getInt("quantity_in_stock")));
                }
            }
        }
    }

    /** DAO - zna TYLKO tabelę orders. */
    private static class OrderDao {
        private final Connection connection;

        OrderDao(Connection connection) {
            this.connection = connection;
        }

        long insert(long userId) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO orders (user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, userId);
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    return keys.getLong(1);
                }
            }
        }
    }

    /** DAO - zna TYLKO tabelę order_items. */
    private static class OrderItemDao {
        private final Connection connection;

        OrderItemDao(Connection connection) {
            this.connection = connection;
        }

        void insert(long orderId, long productId, int quantity) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)")) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, productId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
            }
        }
    }
}
