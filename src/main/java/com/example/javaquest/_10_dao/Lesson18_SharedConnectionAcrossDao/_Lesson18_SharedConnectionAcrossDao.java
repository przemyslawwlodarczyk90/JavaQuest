package com.example.javaquest._10_dao.Lesson18_SharedConnectionAcrossDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson18_SharedConnectionAcrossDao {

    private static final String URL = "jdbc:h2:mem:daolesson18;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 NAJCZĘSTSZY BŁĄD POCZĄTKUJĄCYCH PRZY TRANSAKCJACH
         * ============================================================
         * W lekcji 17 podkreśliliśmy: metody DAO biorące udział w jednej
         * transakcji MUSZĄ dostawać TO SAMO Connection jako parametr.
         *
         * Bardzo częsty błąd wygląda tak: każdy DAO ma metodę, która SAMA
         * otwiera sobie połączenie przez `DriverManager.getConnection(url)`
         * (dokładnie tak, jak robiliśmy to w najwcześniejszych lekcjach
         * rozdziału _09_jdbc, gdzie każda metoda była niezależna).
         *
         * Kiedy taki DAO jest wołany W IZOLACJI - to działa bez zarzutu.
         * Problem pojawia się, gdy DWA takie DAO biorą udział w JEDNEJ
         * operacji biznesowej, która ma się wykonać "wszystko albo nic".
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * Rollback cofa zmiany TYLKO na tym Connection, na którym został
         * wywołany. Jeśli inny DAO użył INNEGO połączenia (a tamto miało
         * domyślny autocommit=true), jego zmiana jest JUŻ ZATWIERDZONA
         * i żaden rollback jej nie cofnie.
         */

        try (Connection setupConnection = DriverManager.getConnection(URL)) {
            setUpSchema(setupConnection);
        }

        /*
         * ============================================================
         * 🔴 ZŁA WERSJA: każdy DAO otwiera WŁASNE połączenie
         * ============================================================
         * BadOrderDao.insertOrderKeepingConnectionOpen(...) otwiera swoje
         * połączenie C1, wyłącza autocommit i wstawia wiersz do orders -
         * ale NIE commituje jeszcze (czeka na resztę operacji biznesowej).
         *
         * BadOrderItemDao.insert(...) otwiera SWOJE WŁASNE, zupełnie inne
         * połączenie C2 - z DOMYŚLNYM autocommit=true - wstawia wiersz do
         * order_items. Ponieważ autocommit=true, ten insert jest
         * ZATWIERDZANY NATYCHMIAST, zanim jeszcze wiemy, czy cała operacja
         * biznesowa się powiedzie.
         *
         * Następnie symulujemy błąd w kolejnym kroku (np. za mało towaru
         * na stanie) i wołamy rollback() - ale TYLKO na połączeniu C1.
         * Zobaczmy, co się stanie z danymi.
         */

        System.out.println("=== ZLA WERSJA: kazdy DAO ma wlasne polaczenie ===");
        runBadScenario();
        printState("Stan PO nieudanej probie (zla wersja)");

        System.out.println();

        /*
         * ============================================================
         * 🟢 DOBRA WERSJA: jedno wspólne Connection przekazywane do DAO
         * ============================================================
         * GoodOrderDao i GoodOrderItemDao mają identyczną logikę SQL, ale
         * ich metody PRZYJMUJĄ Connection jako parametr zamiast tworzyć
         * własne. Business flow otwiera JEDNO połączenie, wyłącza
         * autocommit RAZ i przekazuje je do obu DAO.
         *
         * Tym razem rollback() cofnie OBIE operacje, bo obie odbyły się
         * na tym samym Connection.
         */

        cleanUpData();
        System.out.println("=== DOBRA WERSJA: wspolne polaczenie przekazywane do obu DAO ===");
        runGoodScenario();
        printState("Stan PO nieudanej probie (dobra wersja)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Rollback działa TYLKO w zakresie JEDNEGO Connection - nie
         *   "widzi" i nie cofa zmian zrobionych na innym połączeniu
         * - Jeśli każdy DAO otwiera własne połączenie (z domyślnym
         *   autocommit=true), jego zmiany są zatwierdzane NATYCHMIAST -
         *   zanim reszta operacji biznesowej zdąży się nie powieść
         * - Efekt w ZŁEJ wersji: rollback "wygląda" na wykonany (nie ma
         *   wyjątku), ale dane są NIESPÓJNE - część operacji została
         *   trwale zapisana, część cofnięta
         * - Rozwiązanie: WSZYSTKIE DAO biorące udział w jednej operacji
         *   biznesowej muszą dostawać TO SAMO Connection jako parametr,
         *   a nie tworzyć je samodzielnie
         * - To jeden z najważniejszych i najczęściej pomijanych
         *   szczegółów przy przechodzeniu z "prostego JDBC" na architekturę
         *   warstwową z DAO i Service
         */
    }

    private static void runBadScenario() {
        BadOrderDao badOrderDao = new BadOrderDao();
        BadOrderItemDao badOrderItemDao = new BadOrderItemDao();

        Connection orderConnection = null;
        try {
            // krok 1: BadOrderDao otwiera WLASNE polaczenie C1, autocommit=false,
            // wstawia zamowienie, ale NIE commituje - polaczenie zostaje otwarte
            orderConnection = badOrderDao.insertOrderKeepingConnectionOpen(URL, 1L);
            long orderId = badOrderDao.lastInsertedId();
            System.out.println("Krok 1: wstawiono order #" + orderId + " (NIEZATWIERDZONE, wlasne polaczenie C1)");

            // krok 2: BadOrderItemDao otwiera SWOJE WLASNE polaczenie C2
            // (autocommit=true domyslnie) - ten insert zatwierdza sie NATYCHMIAST
            badOrderItemDao.insert(URL, orderId, 1L, 3);
            System.out.println("Krok 2: wstawiono order_item dla order #" + orderId
                    + " (ZATWIERDZONE NATYCHMIAST, wlasne polaczenie C2)");

            // krok 3: symulujemy blad biznesowy (np. za malo towaru na stanie)
            throw new IllegalStateException("Za malo towaru na stanie (symulacja bledu w kroku 3)");

        } catch (SQLException | IllegalStateException e) {
            System.out.println("Blad w trakcie operacji: " + e.getMessage());
            if (orderConnection != null) {
                try {
                    orderConnection.rollback();
                    System.out.println("Wykonano rollback() na polaczeniu C1 (order)...");
                    System.out.println("...ale polaczenie C2 (order_item) juz dawno zatwierdzilo swoj insert!");
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException(rollbackEx);
                }
            }
        } finally {
            if (orderConnection != null) {
                try {
                    orderConnection.close();
                } catch (SQLException e) {
                    // ignorujemy blad zamkniecia w demo
                }
            }
        }
    }

    private static void runGoodScenario() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            connection.setAutoCommit(false);

            GoodOrderDao goodOrderDao = new GoodOrderDao();
            GoodOrderItemDao goodOrderItemDao = new GoodOrderItemDao();

            try {
                long orderId = goodOrderDao.insert(connection, 1L);
                System.out.println("Krok 1: wstawiono order #" + orderId + " (wspolne polaczenie, NIEZATWIERDZONE)");

                goodOrderItemDao.insert(connection, orderId, 1L, 3);
                System.out.println("Krok 2: wstawiono order_item dla order #" + orderId
                        + " (to samo polaczenie, NIEZATWIERDZONE)");

                throw new IllegalStateException("Za malo towaru na stanie (symulacja bledu w kroku 3)");

            } catch (IllegalStateException e) {
                System.out.println("Blad w trakcie operacji: " + e.getMessage());
                connection.rollback();
                System.out.println("Wykonano rollback() na WSPOLNYM polaczeniu -> cofnieto OBA insert-y");
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void cleanUpData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM order_items");
            stmt.execute("DELETE FROM orders");
        }
    }

    private static void printState(String label) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement()) {
            System.out.println(label + ":");
            try (ResultSet rs = stmt.executeQuery("SELECT id FROM orders")) {
                StringBuilder orders = new StringBuilder();
                while (rs.next()) {
                    orders.append("#").append(rs.getLong("id")).append(" ");
                }
                System.out.println(" - orders: " + (orders.isEmpty() ? "(brak wierszy)" : orders));
            }
            try (ResultSet rs = stmt.executeQuery("SELECT order_id FROM order_items")) {
                StringBuilder items = new StringBuilder();
                while (rs.next()) {
                    items.append("order_id=").append(rs.getLong("order_id")).append(" ");
                }
                System.out.println(" - order_items: " + (items.isEmpty() ? "(brak wierszy)" : items));
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
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
        }
    }

    /** 🔴 ZŁY DAO - SAM otwiera połączenie i zostawia je otwarte (do rollbacku). */
    private static class BadOrderDao {
        private long lastId;

        Connection insertOrderKeepingConnectionOpen(String url, long userId) throws SQLException {
            Connection ownConnection = DriverManager.getConnection(url);
            ownConnection.setAutoCommit(false);
            try (PreparedStatement stmt = ownConnection.prepareStatement(
                    "INSERT INTO orders (user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, userId);
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    keys.next();
                    lastId = keys.getLong(1);
                }
            }
            return ownConnection;
        }

        long lastInsertedId() {
            return lastId;
        }
    }

    /** 🔴 ZŁY DAO - otwiera WŁASNE, ZUPEŁNIE INNE połączenie (domyślny autocommit=true). */
    private static class BadOrderItemDao {
        void insert(String url, long orderId, long productId, int quantity) throws SQLException {
            try (Connection ownConnection = DriverManager.getConnection(url);
                 PreparedStatement stmt = ownConnection.prepareStatement(
                         "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)")) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, productId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
                // autocommit=true (domyslnie) -> ten insert JUZ jest zatwierdzony
                // w momencie zamkniecia try-with-resources, bez wzgledu na to,
                // co stanie sie z "polaczeniem zamowienia" gdzie indziej
            }
        }
    }

    /** 🟢 DOBRY DAO - metoda PRZYJMUJE Connection jako parametr. */
    private static class GoodOrderDao {
        long insert(Connection connection, long userId) throws SQLException {
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

    /** 🟢 DOBRY DAO - metoda PRZYJMUJE Connection jako parametr. */
    private static class GoodOrderItemDao {
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
}
