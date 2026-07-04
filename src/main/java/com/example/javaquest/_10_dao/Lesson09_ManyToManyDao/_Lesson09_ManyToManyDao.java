package com.example.javaquest._10_dao.Lesson09_ManyToManyDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _Lesson09_ManyToManyDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 RELACJA WIELE-DO-WIELU (MANY-TO-MANY)
         * ============================================================
         * Czasem obiekty łączą się w obie strony na raz:
         * - JEDEN produkt może należeć do WIELU kategorii, a JEDNA
         *   kategoria zawiera WIELE produktów (Product <-> Category)
         * - JEDEN użytkownik może mieć WIELE ról, a JEDNA rola może
         *   mieć WIELU użytkowników (User <-> Role)
         *
         * Relacyjne bazy danych NIE potrafią bezpośrednio zapisać takiej
         * relacji (klucz obcy wskazuje tylko w JEDNĄ stronę). Rozwiązaniem
         * jest TABELA POŚREDNIA (ang. join table / junction table) - osobna
         * tabela, która przechowuje WYŁĄCZNIE pary kluczy obcych łączących
         * obie strony relacji, np. products_categories(product_id, category_id).
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * Tabela pośrednia nie przechowuje "danych" - przechowuje same
         * POWIĄZANIA. Usunięcie wiersza z tabeli pośredniej NIE usuwa
         * produktu ani kategorii, usuwa tylko relację między nimi.
         */

        String url = "jdbc:h2:mem:daolesson09;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);
            long laptopId = insertProduct(connection, "Laptop");
            long mouseId = insertProduct(connection, "Mysz");
            long electronicsId = insertCategory(connection, "Elektronika");
            long accessoriesId = insertCategory(connection, "Akcesoria");
            long promoId = insertCategory(connection, "Promocja");

            ProductCategoryDao dao = new ProductCategoryDao(connection);

            /*
             * ============================================================
             * 🔍 INSERT DO TABELI POŚREDNIEJ = DODANIE POWIĄZANIA
             * ============================================================
             * Dodanie kategorii do produktu to zwykły INSERT do tabeli
             * products_categories - NIE zmieniamy przy tym ani wiersza
             * w products, ani w categories. Ten sam produkt można w ten
             * sposób powiązać z DOWOLNĄ liczbą kategorii.
             */
            System.out.println("=== DODAWANIE POWIĄZAŃ (INSERT do tabeli pośredniej) ===");
            dao.addCategoryToProduct(laptopId, electronicsId);
            dao.addCategoryToProduct(laptopId, promoId);
            dao.addCategoryToProduct(mouseId, accessoriesId);
            System.out.println("Powiazania dodane.");

            System.out.println("\n=== KATEGORIE LAPTOPA ===");
            printCategories(dao.findCategoriesForProduct(laptopId));

            /*
             * ============================================================
             * 🔍 DELETE Z TABELI POŚREDNIEJ = USUNIĘCIE POWIĄZANIA
             * ============================================================
             * Usuwamy relację "Laptop należy do Promocji", ale sam
             * produkt "Laptop" i sama kategoria "Promocja" NADAL
             * istnieją w swoich tabelach - usuwamy TYLKO wiersz łączący.
             */
            System.out.println("\n=== USUWANIE POWIĄZANIA Laptop-Promocja ===");
            dao.removeCategoryFromProduct(laptopId, promoId);

            System.out.println("\n=== KATEGORIE LAPTOPA PO USUNIĘCIU POWIĄZANIA ===");
            printCategories(dao.findCategoriesForProduct(laptopId));

            System.out.println("\n=== DOWÓD: PRODUKT I KATEGORIA NADAL ISTNIEJĄ ===");
            System.out.println("Liczba produktów w bazie: " + countRows(connection, "products"));
            System.out.println("Liczba kategorii w bazie: " + countRows(connection, "categories"));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Relacja wiele-do-wielu wymaga TABELI POŚREDNIEJ (join table)
         *   z dwoma kluczami obcymi - po jednym do każdej ze stron relacji
         * - Dodanie powiązania = INSERT do tabeli pośredniej
         * - Usunięcie powiązania = DELETE z tabeli pośredniej - encje
         *   po obu stronach relacji POZOSTAJĄ nietknięte
         * - Odczyt "kategorii produktu" albo "produktów kategorii" to
         *   zapytanie JOIN przechodzące przez tabelę pośrednią
         * - Typowe przykłady: Product<->Category, User<->Role,
         *   Student<->Course
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE products (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(150) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE categories (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(150) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE products_categories (
                        product_id BIGINT NOT NULL REFERENCES products(id),
                        category_id BIGINT NOT NULL REFERENCES categories(id),
                        PRIMARY KEY (product_id, category_id)
                    )
                    """);
        }
    }

    private static long insertProduct(Connection connection, String name) throws SQLException {
        return insertNamed(connection, "products", name);
    }

    private static long insertCategory(Connection connection, String name) throws SQLException {
        return insertNamed(connection, "categories", name);
    }

    private static long insertNamed(Connection connection, String table, String name) throws SQLException {
        String sql = "INSERT INTO " + table + " (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    private static long countRows(Connection connection, String table) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table)) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static void printCategories(List<String> categories) {
        if (categories.isEmpty()) {
            System.out.println(" (brak kategorii)");
            return;
        }
        for (String category : categories) {
            System.out.println(" - " + category);
        }
    }

    /**
     * DAO zarządzające relacją wiele-do-wielu Product <-> Category
     * poprzez tabelę pośrednią products_categories.
     */
    static class ProductCategoryDao {

        private final Connection connection;

        ProductCategoryDao(Connection connection) {
            this.connection = connection;
        }

        /**
         * Dodaje powiązanie produkt-kategoria (INSERT do tabeli pośredniej).
         * Nie tworzy ani nie zmienia samego produktu ani kategorii.
         */
        void addCategoryToProduct(Long productId, Long categoryId) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)")) {
                stmt.setLong(1, productId);
                stmt.setLong(2, categoryId);
                stmt.executeUpdate();
            }
        }

        /**
         * Usuwa powiązanie produkt-kategoria (DELETE z tabeli pośredniej).
         * Sam produkt i sama kategoria pozostają w swoich tabelach.
         */
        void removeCategoryFromProduct(Long productId, Long categoryId) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM products_categories WHERE product_id = ? AND category_id = ?")) {
                stmt.setLong(1, productId);
                stmt.setLong(2, categoryId);
                stmt.executeUpdate();
            }
        }

        /**
         * Odczyt "drugiej strony" relacji: nazwy wszystkich kategorii
         * powiązanych z danym produktem - zapytanie JOIN przez tabelę
         * pośrednią.
         */
        List<String> findCategoriesForProduct(Long productId) throws SQLException {
            String sql = """
                    SELECT c.name
                    FROM categories c
                    JOIN products_categories pc ON pc.category_id = c.id
                    WHERE pc.product_id = ?
                    ORDER BY c.name
                    """;

            List<String> result = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, productId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result.add(rs.getString("name"));
                    }
                }
            }
            return result;
        }
    }
}
