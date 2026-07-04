package com.example.javaquest._09_jdbc.Lesson10_JdbcDelete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson10_JdbcDelete {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DELETE W JDBC
         * ============================================================
         * Usuwanie rekordów w JDBC działa analogicznie do UPDATE-u z
         * Lesson09 - PreparedStatement + executeUpdate(), a wynik to
         * LICZBA usuniętych wierszy. Zasada jest ta sama: 0 oznacza,
         * że nic nie zostało usunięte (bo np. taki wiersz nie istniał).
         */

        String url = "jdbc:h2:mem:jdbclesson10;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement setup = connection.createStatement()) {

            setup.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL,
                        active BOOLEAN NOT NULL DEFAULT TRUE
                    )
                    """);
            setup.executeUpdate("INSERT INTO users VALUES (1, 'Jan Kowalski', 'jan@example.com', TRUE)");
            setup.executeUpdate("INSERT INTO users VALUES (2, 'Anna Nowak', 'anna@example.com', TRUE)");
            setup.executeUpdate("INSERT INTO users VALUES (3, 'Piotr Wisniewski', 'piotr@example.com', TRUE)");
            System.out.println("Tabela 'users' przygotowana (3 wiersze).\n");

            /*
             * ============================================================
             * 🔹 HARD DELETE - fizyczne usunięcie wiersza
             * ============================================================
             * "Twardy" DELETE fizycznie usuwa wiersz z tabeli - dane
             * przepadają bezpowrotnie (chyba że mamy backup / transakcję
             * do wycofania).
             */
            System.out.println("=== HARD DELETE: usuwanie id=3 ===");
            int deleted = hardDeleteById(connection, 3);
            System.out.println("Liczba usunietych wierszy: " + deleted);
            printAllUsers(connection);

            System.out.println("\n=== HARD DELETE: proba usuniecia id=999 (nie istnieje) ===");
            int deletedMissing = hardDeleteById(connection, 999);
            System.out.println("Liczba usunietych wierszy: " + deletedMissing);

            /*
             * ============================================================
             * 🔹 SOFT DELETE - logiczne "usunięcie"
             * ============================================================
             * Zamiast fizycznego DELETE, oznaczamy wiersz jako nieaktywny
             * (np. kolumna `active = false`). Wiersz fizycznie zostaje w
             * tabeli, ale aplikacja traktuje go jako "usunięty".
             *
             * Zalety SOFT DELETE względem HARD DELETE:
             * - zachowanie historii - wiadomo, że dany użytkownik kiedyś
             *   istniał (np. do celów audytu, raportów, zgodności z prawem),
             * - możliwość "przywrócenia" rekordu (wystarczy znów ustawić
             *   active = true, nic nie trzeba odtwarzać z backupu),
             * - mniej problemów z ograniczeniami klucza obcego (FK) -
             *   jeśli inne tabele odwołują się do usuwanego wiersza,
             *   fizyczny DELETE mógłby zakończyć się błędem naruszenia FK
             *   (albo skasować powiązane dane kaskadowo, co bywa
             *   niebezpieczne); soft delete tego problemu nie ma, bo
             *   wiersz nadal fizycznie istnieje.
             *
             * Wady: tabela rośnie w nieskończoność (dane "usunięte" nigdy
             * nie znikają fizycznie), a każde zapytanie SELECT musi
             * pamiętać o dopisaniu warunku `WHERE active = TRUE`, żeby nie
             * pokazywać "usuniętych" rekordów.
             */
            System.out.println("\n=== SOFT DELETE: dezaktywacja id=1 ===");
            int softDeleted = softDeleteById(connection, 1);
            System.out.println("Liczba zdezaktywowanych wierszy: " + softDeleted);
            printAllUsers(connection);

            System.out.println("\n=== SELECT tylko aktywnych uzytkownikow ===");
            printActiveUsers(connection);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DELETE w JDBC: PreparedStatement + executeUpdate(), wynik to
         *   liczba usuniętych wierszy (0 = nic nie usunięto).
         * - HARD DELETE - fizyczne, nieodwracalne usunięcie wiersza z
         *   tabeli.
         * - SOFT DELETE - "logiczne" usunięcie przez UPDATE flagi
         *   (np. `active = FALSE`) zamiast fizycznego DELETE - zachowuje
         *   historię, umożliwia przywrócenie, unika problemów z kluczami
         *   obcymi.
         * - W realnych aplikacjach (zwłaszcza tam, gdzie liczy się historia
         *   danych - systemy finansowe, medyczne, e-commerce) soft delete
         *   jest bardzo częstym wyborem zamiast twardego DELETE.
         */
    }

    static int hardDeleteById(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    static int softDeleteById(Connection connection, int id) throws SQLException {
        String sql = "UPDATE users SET active = FALSE WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private static void printAllUsers(Connection connection) throws SQLException {
        String sql = "SELECT id, name, active FROM users ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            System.out.println("Zawartosc tabeli 'users':");
            while (rs.next()) {
                System.out.println("  " + rs.getInt("id") + ": " + rs.getString("name")
                        + " (active=" + rs.getBoolean("active") + ")");
            }
        }
    }

    private static void printActiveUsers(Connection connection) throws SQLException {
        String sql = "SELECT id, name FROM users WHERE active = TRUE ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                System.out.println("  " + rs.getInt("id") + ": " + rs.getString("name"));
            }
        }
    }
}
