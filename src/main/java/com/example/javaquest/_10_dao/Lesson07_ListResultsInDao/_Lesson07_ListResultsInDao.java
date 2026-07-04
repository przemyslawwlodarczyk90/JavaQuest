package com.example.javaquest._10_dao.Lesson07_ListResultsInDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _Lesson07_ListResultsInDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 List<T> JAKO TYP ZWRACANY DLA WIELU WYNIKÓW
         * ============================================================
         * Metody DAO, które z natury mogą zwrócić WIELE wyników (zero,
         * jeden lub wiele wierszy), zwracają List<T> - NIGDY Optional
         * (Optional jest dla "0 albo 1", patrz Lesson06) i NIGDY null.
         *
         * Typowe metody domenowe zwracające listy:
         * - findAll()            -> wszystkie rekordy
         * - findByStatus(status) -> rekordy o danym statusie (np. zamówienia
         *                            "NEW", "SHIPPED", "CANCELLED")
         * - findByCategory(cat)  -> produkty z danej kategorii
         *
         * 🔹 ZŁOTA ZASADA: NIGDY NIE ZWRACAJ null Z METODY ZWRACAJĄCEJ LISTĘ
         * Jeśli zapytanie SQL nie znajdzie żadnego pasującego wiersza, DAO
         * powinno zwrócić PUSTĄ LISTĘ (new ArrayList<>() / List.of()), a NIE
         * null. Dzięki temu kod wywołujący może BEZPIECZNIE użyć od razu
         * list.size(), list.isEmpty(), pętli for-each - bez wcześniejszego
         * sprawdzania "czy lista w ogóle istnieje". Zwrócenie null z metody
         * zwracającej List<T> to jeden z najczęstszych błędów prowadzących
         * do NullPointerException w kodzie klienta DAO.
         */

        String url = "jdbc:h2:mem:daolesson07;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);

            OrderJdbcDao orderDao = new OrderJdbcDao(connection);

            System.out.println("=== findByStatus(\"NEW\") - sa dopasowania ===");
            List<Order> nowe = orderDao.findByStatus("NEW");
            System.out.println(nowe);
            System.out.println("Liczba zamowien NEW: " + nowe.size());

            System.out.println("\n=== findByStatus(\"CANCELLED\") - BRAK dopasowan ===");
            List<Order> anulowane = orderDao.findByStatus("CANCELLED");
            System.out.println("Wynik: " + anulowane);
            System.out.println("Liczba zamowien CANCELLED: " + anulowane.size());

            /*
             * ============================================================
             * 🔍 WERYFIKACJA: WYNIK NIGDY NIE JEST null
             * ============================================================
             * Poniższe asercje (assert) potwierdzają, że nawet gdy brak
             * dopasowań, orderDao.findByStatus zwraca PUSTĄ LISTĘ, a nie null.
             * Uwaga: assert domyślnie jest WYŁĄCZONY w JVM (trzeba włączyć
             * flagą -ea) - tutaj dodatkowo potwierdzamy to samo przez zwykłe
             * println, żeby rezultat był widoczny niezależnie od tej flagi.
             */
            assert anulowane != null : "Lista nie powinna nigdy byc null!";
            System.out.println("\nCzy wynik dla brakujacego statusu jest null? " + (anulowane == null));
            System.out.println("Czy wynik dla brakujacego statusu jest pusta lista? " + anulowane.isEmpty());

            System.out.println("\n=== Bezpieczne uzycie wyniku BEZ sprawdzania null ===");
            // Dzięki temu, że DAO nigdy nie zwraca null, możemy od razu iterować
            // i sprawdzać rozmiar - bez zabezpieczenia "if (lista != null)"
            for (Order order : anulowane) {
                System.out.println(" - " + order); // ta petla po prostu nie wykona sie ani razu
            }
            System.out.println("Petla po pustej liscie zakonczona bez bledu (0 iteracji)");

            System.out.println("\n=== findAll() - kolejny przyklad metody zwracajacej liste ===");
            System.out.println(orderDao.findAll());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Metody DAO zwracające wiele wyników (findAll, findByStatus,
         *   findByCategory) mają typ zwracany List<T>
         * - ZŁOTA ZASADA: nigdy nie zwracaj null z metody zwracającej listę
         *   - brak dopasowań to PUSTA LISTA (new ArrayList<>()), nie null
         * - Dzięki temu kod wywołujący może bezpiecznie użyć isEmpty(),
         *   size(), pętli for-each, bez sprawdzania "czy lista istnieje"
         * - Optional jest dla "0 albo 1 wynik" (Lesson06), List<T> jest dla
         *   "0, 1 albo wiele wyników" - to dwa różne, uzupełniające się
         *   podejścia w projektowaniu API metod DAO
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        status VARCHAR(30) NOT NULL
                    )
                    """);
            stmt.execute("INSERT INTO orders (status) VALUES ('NEW')");
            stmt.execute("INSERT INTO orders (status) VALUES ('NEW')");
            stmt.execute("INSERT INTO orders (status) VALUES ('SHIPPED')");
        }
    }

    private record Order(long id, String status) {
    }

    private static class OrderJdbcDao {

        private final Connection connection;

        OrderJdbcDao(Connection connection) {
            this.connection = connection;
        }

        private Order mapRow(ResultSet rs) throws SQLException {
            return new Order(rs.getLong("id"), rs.getString("status"));
        }

        List<Order> findAll() {
            List<Order> result = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, status FROM orders ORDER BY id")) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas pobierania zamowien", e);
            }
        }

        /**
         * Zwraca zamówienia o danym statusie. Jeśli nic nie pasuje, pętla
         * while (rs.next()) po prostu ani razu się nie wykona, a result
         * pozostanie PUSTĄ LISTĄ (zainicjowaną jako new ArrayList<>()) -
         * metoda NIGDY nie zwraca null.
         */
        List<Order> findByStatus(String status) {
            List<Order> result = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, status FROM orders WHERE status = ? ORDER BY id")) {
                stmt.setString(1, status);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result.add(mapRow(rs));
                    }
                }
                return result; // pusta lista, jesli brak dopasowan - NIGDY null
            } catch (SQLException e) {
                throw new RuntimeException("Blad podczas wyszukiwania zamowien po statusie", e);
            }
        }
    }
}
