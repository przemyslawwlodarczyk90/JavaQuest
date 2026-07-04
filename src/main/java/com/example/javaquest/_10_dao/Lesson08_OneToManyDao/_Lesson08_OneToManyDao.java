package com.example.javaquest._10_dao.Lesson08_OneToManyDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _Lesson08_OneToManyDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 RELACJA JEDEN-DO-WIELU (ONE-TO-MANY)
         * ============================================================
         * W realnych aplikacjach dane rzadko żyją w jednej, samotnej
         * tabeli. Bardzo częsty przypadek to relacja JEDEN-DO-WIELU:
         * - JEDNO zamówienie (order) ma WIELE pozycji zamówienia (items)
         * - JEDEN użytkownik (user) ma WIELE zamówień (orders)
         * - JEDEN produkt może mieć WIELE opinii (reviews)
         *
         * W bazie relacyjnej realizuje się to kluczem obcym po stronie
         * "wielu": tabela order_items ma kolumnę order_id wskazującą,
         * do którego zamówienia należy dana pozycja.
         *
         * 🔹 GŁÓWNY OBIEKT vs DANE POWIĄZANE
         * DAO dla takiej relacji musi odpowiedzieć na pytanie: czy razem
         * z GŁÓWNYM obiektem (Order) chcemy od razu pobrać jego dane
         * POWIĄZANE (List<OrderItem>)? Jeśli tak - mamy do wyboru dwie
         * strategie pobierania danych.
         */

        /*
         * ============================================================
         * 🔍 STRATEGIA 1: JEDEN SELECT Z JOIN
         * ============================================================
         * Łączymy tabele orders i order_items jednym zapytaniem SQL
         * (JOIN) i dostajemy JEDEN ResultSet, w którym wiersz zamówienia
         * jest POWTÓRZONY dla każdej jego pozycji (tzw. "spłaszczenie").
         * Musimy więc ręcznie POGRUPOWAĆ wiersze po stronie Javy,
         * budując jeden obiekt Order i dokładając mu kolejne OrderItem.
         *
         * Zalety: JEDNO zapytanie do bazy (jeden "przejazd" po sieci).
         * Wady: kod grupujący wyniki jest mniej czytelny, a dane
         * zamówienia (np. nazwa klienta) są bezsensownie powtórzone
         * w każdym wierszu ResultSetu.
         *
         * ============================================================
         * 🔍 STRATEGIA 2: DWA OSOBNE ZAPYTANIA
         * ============================================================
         * Najpierw pobieramy sam nagłówek zamówienia (SELECT * FROM
         * orders WHERE id = ?), a potem OSOBNYM zapytaniem pobieramy
         * jego pozycje (SELECT * FROM order_items WHERE order_id = ?).
         *
         * Zalety: każde zapytanie jest proste i czytelne, żadnych
         * powtórzeń danych, łatwo testować osobno.
         * Wady: DWA "przejazdy" do bazy zamiast jednego - przy bazie
         * w innej sieci (nie in-memory jak nasza H2) to dodatkowe
         * opóźnienie. Przy wielu zamówieniach pobieranych naraz
         * (np. liście 100 zamówień) to może urosnąć do tzw. problemu
         * "N+1 zapytań" - warto o tym pamiętać przy projektowaniu.
         *
         * 📌 W praktyce: JOIN opłaca się, gdy pobierasz JEDNO zamówienie
         * albo gdy wydajność ma krytyczne znaczenie. Dwa zapytania są
         * prostsze w utrzymaniu i wystarczające w większości aplikacji
         * o umiarkowanym ruchu. Poniżej zaimplementujemy OBIE wersje
         * i porównamy, że dają IDENTYCZNY wynik.
         */

        String url = "jdbc:h2:mem:daolesson08;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);
            seedData(connection);

            OrderDao orderDao = new OrderDao(connection);

            System.out.println("=== STRATEGIA 1: JOIN + grupowanie w Javie ===");
            Order orderViaJoin = orderDao.findByIdWithItemsUsingJoin(1L);
            System.out.println(orderViaJoin);

            System.out.println("\n=== STRATEGIA 2: dwa osobne zapytania ===");
            Order orderViaTwoQueries = orderDao.findByIdWithItemsUsingTwoQueries(1L);
            System.out.println(orderViaTwoQueries);

            System.out.println("\n=== PORÓWNANIE WYNIKÓW ===");
            boolean sameResult = orderViaJoin.equals(orderViaTwoQueries);
            System.out.println("Czy obie strategie dały ten sam wynik? " + sameResult);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Relacja jeden-do-wielu to klucz obcy po stronie "wielu"
         *   (order_items.order_id wskazuje na orders.id)
         * - Strategia JOIN: jedno zapytanie SQL, ale wynik trzeba ręcznie
         *   pogrupować po stronie Javy (wiersze główne się powtarzają)
         * - Strategia dwóch zapytań: prostszy, czytelniejszy kod, ale
         *   dwa "przejazdy" do bazy zamiast jednego
         * - Obie strategie powinny dawać IDENTYCZNY wynik biznesowy -
         *   różnią się tylko sposobem pobierania danych, nie danymi
         * - Wybór strategii to kompromis między wydajnością a czytelnością,
         *   zależny od skali aplikacji i konkretnego zapytania
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_name VARCHAR(150) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE order_items (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_id BIGINT NOT NULL REFERENCES orders(id),
                        product_name VARCHAR(150) NOT NULL,
                        quantity INT NOT NULL,
                        price DECIMAL(10,2) NOT NULL
                    )
                    """);
        }
    }

    private static void seedData(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO orders (id, customer_name) VALUES (1, 'Ania Kowalska')");
            stmt.execute("""
                    INSERT INTO order_items (order_id, product_name, quantity, price) VALUES
                    (1, 'Klawiatura', 1, 149.99),
                    (1, 'Mysz', 2, 79.50),
                    (1, 'Podkladka pod mysz', 1, 29.00)
                    """);
        }
    }

    /**
     * Pozycja zamówienia - obiekt "po stronie wielu" relacji.
     */
    record OrderItem(Long id, String productName, int quantity, double price) {
    }

    /**
     * Zamówienie razem z listą jego pozycji - obiekt "po stronie jeden"
     * wraz z powiązanymi danymi.
     */
    record Order(Long id, String customerName, List<OrderItem> items) {

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Zamowienie #").append(id).append(" (").append(customerName).append(")");
            for (OrderItem item : items) {
                sb.append("\n   - ").append(item.quantity()).append("x ").append(item.productName())
                        .append(" @ ").append(item.price()).append(" zl");
            }
            return sb.toString();
        }
    }

    /**
     * DAO demonstrujące OBIE strategie pobierania danych powiązanych
     * jeden-do-wielu: JOIN z ręcznym grupowaniem oraz dwa osobne zapytania.
     */
    static class OrderDao {

        private final Connection connection;

        OrderDao(Connection connection) {
            this.connection = connection;
        }

        /**
         * 🔍 STRATEGIA 1: jedno zapytanie JOIN, ręczne grupowanie wyników.
         * Wiersz zamówienia powtarza się dla każdej jego pozycji - musimy
         * wykryć "pierwsze wystąpienie" i budować listę pozycji obok niego.
         */
        Order findByIdWithItemsUsingJoin(Long orderId) throws SQLException {
            String sql = """
                    SELECT o.id AS order_id, o.customer_name,
                           i.id AS item_id, i.product_name, i.quantity, i.price
                    FROM orders o
                    JOIN order_items i ON i.order_id = o.id
                    WHERE o.id = ?
                    ORDER BY i.id
                    """;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, orderId);
                try (ResultSet rs = stmt.executeQuery()) {
                    String customerName = null;
                    List<OrderItem> items = new ArrayList<>();

                    while (rs.next()) {
                        // dane zamówienia powtarzają się w każdym wierszu -
                        // wystarczy je odczytać raz (albo za każdym razem,
                        // to zawsze te same wartości dla tego samego orderId)
                        customerName = rs.getString("customer_name");

                        items.add(new OrderItem(
                                rs.getLong("item_id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price")));
                    }

                    if (customerName == null) {
                        throw new IllegalStateException("Zamowienie o id " + orderId + " nie istnieje");
                    }

                    return new Order(orderId, customerName, items);
                }
            }
        }

        /**
         * 🔍 STRATEGIA 2: dwa osobne, proste zapytania - najpierw nagłówek
         * zamówienia, potem jego pozycje. Żadnego ręcznego grupowania.
         */
        Order findByIdWithItemsUsingTwoQueries(Long orderId) throws SQLException {
            String customerName;

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT customer_name FROM orders WHERE id = ?")) {
                stmt.setLong(1, orderId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("Zamowienie o id " + orderId + " nie istnieje");
                    }
                    customerName = rs.getString("customer_name");
                }
            }

            List<OrderItem> items = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, product_name, quantity, price FROM order_items WHERE order_id = ? ORDER BY id")) {
                stmt.setLong(1, orderId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        items.add(new OrderItem(
                                rs.getLong("id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price")));
                    }
                }
            }

            return new Order(orderId, customerName, items);
        }
    }
}
