package com.example.javaquest._10_dao.Lesson22_PaginationInJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _Lesson22_PaginationInJdbc {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PO CO PAGINACJA?
         * ============================================================
         * Wyobraź sobie tabelę z milionem produktów i endpoint
         * "GET /products", który zwraca WSZYSTKIE wiersze naraz. To
         * katastrofa: gigantyczny transfer danych, ogromne zużycie
         * pamięci (i po stronie bazy, i po stronie aplikacji), oraz
         * bezużyteczny interfejs (nikt nie przewija miliona wierszy).
         *
         * PAGINACJA (pagination) = dzielenie wyniku na "strony" (page)
         * o ustalonym rozmiarze (size) i pobieranie TYLKO jednej strony
         * na raz. Klient prosi o "stronę 2, po 10 elementów" - baza
         * zwraca dokładnie 10 wierszy, nie milion.
         *
         * To dokładnie ten sam mechanizm, który znasz z każdej listy
         * wyników wyszukiwania w internecie ("Strona 1 2 3 ... 42").
         */

        String url = "jdbc:h2:mem:daolesson22;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);
            ProductDao productDao = new ProductDao(connection);

            System.out.println("=== Zaladowano 23 produkty do tabeli 'products' ===\n");

            /*
             * ============================================================
             * 🔹 LIMIT i OFFSET
             * ============================================================
             * SQL (w tym H2, PostgreSQL, MySQL) pozwala ograniczyć wynik
             * zapytania dwiema klauzulami dodawanymi po ORDER BY:
             *
             *   SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?
             *
             * - LIMIT n  -> zwróć NAJWYŻEJ n wierszy
             * - OFFSET m -> POMIŃ pierwsze m wierszy wyniku
             *
             * Przykład: chcemy "stronę" o numerze `page` (liczonym od 1),
             * o rozmiarze `size` elementów każda:
             *
             *   LIMIT  = size
             *   OFFSET = (page - 1) * size
             *
             * Dla page=1, size=5  -> LIMIT 5 OFFSET 0  (wiersze 1-5)
             * Dla page=2, size=5  -> LIMIT 5 OFFSET 5  (wiersze 6-10)
             * Dla page=3, size=5  -> LIMIT 5 OFFSET 10 (wiersze 11-15)
             *
             * ⚠️ ORDER BY jest OBOWIĄZKOWE przy paginacji! Bez niego baza
             * nie gwarantuje żadnej konkretnej kolejności wierszy - dwa
             * kolejne zapytania o "stronę 2" mogłyby zwrócić RÓŻNE dane
             * (albo powtórzone/pominięte wiersze między stronami).
             */

            /*
             * ============================================================
             * 🔍 SKĄD WIEDZIEĆ, ILE JEST STRON? - OSOBNE ZAPYTANIE COUNT
             * ============================================================
             * LIMIT/OFFSET zwraca tylko WYCINEK danych - same wiersze nie
             * mówią nam, ile ŁĄCZNIE jest elementów (a więc ile jest stron
             * "do przodu"). Dlatego wykonujemy DRUGIE, osobne zapytanie:
             *
             *   SELECT COUNT(*) FROM products
             *
             * (ewentualnie z tymi samymi warunkami WHERE co główne
             * zapytanie, jeśli strona jest jeszcze filtrowana - patrz
             * Lesson24_DynamicFiltering). Na tej podstawie liczymy:
             *
             *   totalPages = ceil(totalElements / size)
             *
             * To koszt dwóch zapytań zamiast jednego - w zamian dostajemy
             * kompletną informację potrzebną do zbudowania UI paginacji
             * ("Strona 3 z 5", przyciski "poprzednia/następna" itd.).
             */

            System.out.println("=== Strona 1 (size=5) ===");
            printPage(productDao.findPage(1, 5));

            System.out.println("\n=== Strona 2 (size=5) ===");
            printPage(productDao.findPage(2, 5));

            System.out.println("\n=== Strona 5 (size=5) - ostatnia, NIEPELNA strona (23 produkty / 5 = 4 pelne + reszta) ===");
            printPage(productDao.findPage(5, 5));

            System.out.println("\n=== Strona 6 (size=5) - PO ostatniej stronie, pusty content ===");
            printPage(productDao.findPage(6, 5));
            // OFFSET wykracza poza liczbę wierszy -> SQL po prostu nie
            // zwraca żadnego wiersza (nie jest to błąd), a totalPages
            // nadal informuje, ile realnie jest stron z danymi.

            System.out.println("\n=== Ta sama tabela, inny rozmiar strony (size=10) ===");
            printPage(productDao.findPage(1, 10));
            printPage(productDao.findPage(3, 10));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Paginacja = pobieranie wyniku "stronami" (page + size) zamiast
         *   wszystkich wierszy naraz - kluczowe dla dużych tabel.
         * - SQL: `ORDER BY <kolumna> LIMIT ? OFFSET ?`, gdzie
         *   OFFSET = (page - 1) * size, LIMIT = size.
         * - ORDER BY jest WYMAGANE - bez niego kolejność wierszy między
         *   stronami nie jest gwarantowana.
         * - Żeby policzyć totalPages, potrzebne jest OSOBNE zapytanie
         *   `SELECT COUNT(*) FROM ...` (LIMIT/OFFSET tego nie dają).
         * - Wynik wygodnie opakować w jeden obiekt `Page<T>` niosący
         *   zarówno dane strony (content), jak i metadane (page, size,
         *   totalElements, totalPages) - klient UI dostaje komplet
         *   informacji w jednej odpowiedzi.
         * - Strona "za daleko" (OFFSET większy niż liczba wierszy) nie
         *   jest błędem - po prostu zwraca pustą listę.
         */
    }

    private static void printPage(Page<Product> page) {
        System.out.println("Strona " + page.page() + " z " + page.totalPages()
                + " (rozmiar strony=" + page.size() + ", wszystkich elementow=" + page.totalElements() + "):");
        if (page.content().isEmpty()) {
            System.out.println("  (brak elementow na tej stronie)");
        } else {
            page.content().forEach(product -> System.out.println("  #" + product.id() + " " + product.name()));
        }
    }

    /**
     * Metadane + zawartość jednej strony wyniku - typowy kształt
     * odpowiedzi API zwracającej wynik stronicowany.
     */
    private record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages) {
    }

    private record Product(long id, String name) {
    }

    /** DAO produktów z obsługą paginacji. */
    private static class ProductDao {

        private final Connection connection;

        ProductDao(Connection connection) {
            this.connection = connection;
        }

        Page<Product> findPage(int page, int size) throws SQLException {
            if (page < 1 || size < 1) {
                throw new IllegalArgumentException("page i size musza byc >= 1, otrzymano page=" + page + ", size=" + size);
            }

            long totalElements = count();
            int offset = (page - 1) * size;

            List<Product> content = new ArrayList<>();
            String sql = "SELECT id, name FROM products ORDER BY id LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, size);
                statement.setInt(2, offset);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        content.add(new Product(rs.getLong("id"), rs.getString("name")));
                    }
                }
            }

            int totalPages = (int) Math.ceil(totalElements / (double) size);
            return new Page<>(content, page, size, totalElements, totalPages);
        }

        private long count() throws SQLException {
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM products")) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE products (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            for (int i = 1; i <= 23; i++) {
                stmt.executeUpdate("INSERT INTO products (name) VALUES ('Produkt " + i + "')");
            }
            // (Wstawianie przez petle konkatenacji jest tu OK - to STALE,
            // wygenerowane przez nas dane demo, a nie dane od uzytkownika;
            // w prawdziwym kodzie i tak uzylibysmy PreparedStatement,
            // patrz Lesson14_SqlInjection w rozdziale _09_jdbc).
        }
    }
}
