package com.example.javaquest._10_dao.Lesson20_ErrorHandlingAcrossLayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class _Lesson20_ErrorHandlingAcrossLayers {

    private static final String URL = "jdbc:h2:mem:daolesson20;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PROBLEM: SQLException JEST CHECKED I "JDBC-OWY"
         * ============================================================
         * `SQLException` to wyjątek typu CHECKED - każda metoda, która go
         * rzuca (albo woła coś, co go rzuca), musi go zadeklarować przez
         * `throws SQLException` albo obsłużyć. Widzieliśmy to w KAŻDEJ
         * dotychczasowej lekcji.
         *
         * Problem pojawia się, gdy SQLException "przecieka" przez WARSTWY:
         * DAO -> Service -> Kontroler -> UI. Warstwa Kontrolera/UI nie
         * powinna wiedzieć NIC o JDBC - a mimo to musiałaby importować
         * `java.sql.SQLException` i wiedzieć, jak go obsłużyć, tylko po
         * to, żeby kod się skompilował.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * DAO powinno łapać SQLException i OPAKOWYWAĆ go we WŁASNY,
         * unchecked wyjątek (np. DataAccessException), żeby wyższe
         * warstwy nie musiały znać szczegółów JDBC.
         */

        try (Connection connection = DriverManager.getConnection(URL)) {
            setUpSchema(connection);

            ProductDao productDao = new ProductDao(connection);
            ProductService productService = new ProductService(productDao);

            /*
             * ============================================================
             * 🔍 DWA RODZAJE BŁĘDÓW: TECHNICZNE vs BIZNESOWE
             * ============================================================
             * - Błąd TECHNICZNY: coś nie tak z bazą danych (zerwane
             *   połączenie, zła składnia SQL, naruszenie ograniczenia).
             *   W naszym kodzie reprezentuje go DataAccessException -
             *   opakowuje SQLException, jest unchecked.
             * - Błąd BIZNESOWY: dane są poprawne z punktu widzenia bazy,
             *   ale reguła biznesowa mówi "nie" (np. "produkt nie
             *   istnieje", "produkt niedostępny"). Reprezentuje go
             *   ProductNotFoundException - WŁASNY wyjątek domenowy,
             *   nie ma nic wspólnego z SQL.
             *
             * Warstwa Service łapie te dwa rodzaje wyjątków OSOBNO i
             * reaguje na nie inaczej - bo oznaczają zupełnie coś innego.
             */

            System.out.println("=== Przypadek 1: produkt istnieje ===");
            productService.buyProduct(1L);

            System.out.println("\n=== Przypadek 2: blad biznesowy (produkt nie istnieje) ===");
            try {
                productService.buyProduct(999L);
            } catch (ProductNotFoundException e) {
                System.out.println("Kontroler/UI dostaje czytelny blad biznesowy: " + e.getMessage());
            }

            System.out.println("\n=== Przypadek 3: blad techniczny (zly SQL w DAO) ===");
            try {
                productDao.runBrokenQuery();
            } catch (DataAccessException e) {
                System.out.println("Kontroler/UI dostaje DataAccessException (bez SQLException!): "
                        + e.getMessage());
                System.out.println("Oryginalna przyczyna (SQLException) ukryta w getCause(): "
                        + e.getCause().getClass().getSimpleName());
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DAO łapie `SQLException` (checked, "JDBC-owy") i opakowuje go
         *   we WŁASNY `DataAccessException` (unchecked, dziedziczy po
         *   RuntimeException) - wyższe warstwy nie muszą go deklarować
         *   ani znać
         * - Błędy TECHNICZNE (problem z bazą) reprezentuje
         *   DataAccessException - Service zwykle nie próbuje ich
         *   "naprawiać", tylko np. loguje i pokazuje ogólny komunikat
         * - Błędy BIZNESOWE (np. "produkt nie istnieje") to OSOBNE,
         *   dedykowane wyjątki domenowe (np. ProductNotFoundException) -
         *   Service reaguje na nie inaczej, bo to nie jest awaria, tylko
         *   normalny wynik logiki biznesowej
         * - Zasada: `SQLException` NIGDY nie powinien "wyciekać" poza
         *   warstwę DAO - Controller/UI nie powinien wiedzieć, że pod
         *   spodem w ogóle jest JDBC
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE products (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("INSERT INTO products (id, name) VALUES (1, 'Klawiatura')");
        }
    }

    /** WŁASNY unchecked wyjątek reprezentujący BŁĄD TECHNICZNY dostępu do danych. */
    private static class DataAccessException extends RuntimeException {
        DataAccessException(String message, SQLException cause) {
            super(message, cause);
        }
    }

    /** WŁASNY wyjątek reprezentujący BŁĄD BIZNESOWY - nie ma nic wspólnego z SQL. */
    private static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(long productId) {
            super("Produkt o id=" + productId + " nie istnieje");
        }
    }

    /**
     * DAO: łapie SQLException i opakowuje go w DataAccessException.
     * Metody DAO NIE deklarują już `throws SQLException`.
     */
    private static class ProductDao {
        private final Connection connection;

        ProductDao(Connection connection) {
            this.connection = connection;
        }

        Optional<String> findNameById(long id) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT name FROM products WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(rs.getString("name")) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new DataAccessException("Blad podczas wyszukiwania produktu id=" + id, e);
            }
        }

        /** Celowo zła składnia SQL - do zademonstrowania błędu TECHNICZNEGO. */
        void runBrokenQuery() {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeQuery("SELECT * FROM tabela_ktora_nie_istnieje");
            } catch (SQLException e) {
                throw new DataAccessException("Blad techniczny podczas zapytania do bazy", e);
            }
        }
    }

    /**
     * Service: łapie BŁĄD BIZNESOWY (brak produktu) i rzuca dedykowany
     * wyjątek domenowy. DataAccessException (błąd techniczny) świadomie
     * NIE jest tu łapany - propaguje dalej do wywołującego bez zmian,
     * bo Service i tak nie umiałby go "naprawić".
     */
    private static class ProductService {
        private final ProductDao productDao;

        ProductService(ProductDao productDao) {
            this.productDao = productDao;
        }

        void buyProduct(long productId) {
            Optional<String> name = productDao.findNameById(productId);
            if (name.isEmpty()) {
                throw new ProductNotFoundException(productId);
            }
            System.out.println("Kupiono produkt: " + name.get());
        }
    }
}
