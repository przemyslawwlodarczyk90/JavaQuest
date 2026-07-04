package com.example.javaquest._09_jdbc.Lesson09_JdbcUpdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson09_JdbcUpdate {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 UPDATE W JDBC
         * ============================================================
         * Aktualizacja rekordów w JDBC wygląda bardzo podobnie do
         * INSERT-a z Lesson07 - używamy PreparedStatement, tyle że
         * zapytanie SQL to UPDATE, a nie INSERT. Kluczowa różnica,
         * na którą trzeba zwrócić uwagę w tej lekcji, to WYNIK
         * `executeUpdate()`.
         *
         * `executeUpdate()` zwraca `int` - LICZBĘ wierszy, które
         * faktycznie zostały zmienione przez zapytanie. To bardzo
         * przydatna informacja:
         * - jeśli wynosi 1 (albo więcej) - operacja faktycznie coś
         *   zmieniła w bazie,
         * - jeśli wynosi 0 - zapytanie wykonało się BEZ BŁĘDU, ale
         *   NIC nie zaktualizowało (np. dlatego, że WHERE id = ?
         *   nie pasował do żadnego istniejącego wiersza).
         *
         * To bardzo ważne rozróżnienie - brak wyjątku nie znaczy, że
         * operacja "się udała" w sensie biznesowym. Dopiero sprawdzenie
         * wyniku `executeUpdate()` daje pewność.
         */

        String url = "jdbc:h2:mem:jdbclesson09;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement setup = connection.createStatement()) {

            setup.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL
                    )
                    """);
            setup.executeUpdate("INSERT INTO users VALUES (1, 'Jan Kowalski', 'jan@example.com')");
            setup.executeUpdate("INSERT INTO users VALUES (2, 'Anna Nowak', 'anna@example.com')");
            System.out.println("Tabela 'users' przygotowana (2 wiersze).\n");

            /*
             * ============================================================
             * 🔹 UPDATE PO ID - REKORD ISTNIEJE
             * ============================================================
             */
            System.out.println("=== UPDATE rekordu o id=1 (istnieje) ===");
            int updated = updateEmail(connection, 1, "jan.kowalski@newmail.com");
            System.out.println("Liczba zaktualizowanych wierszy: " + updated);
            printUser(connection, 1);

            /*
             * ============================================================
             * 🔹 UPDATE PO ID - REKORD NIE ISTNIEJE
             * ============================================================
             * To samo zapytanie, ale z ID, którego nie ma w tabeli.
             * `executeUpdate()` NIE rzuci wyjątku - po prostu zwróci 0,
             * bo warunek WHERE nie dopasował żadnego wiersza.
             */
            System.out.println("\n=== UPDATE rekordu o id=999 (NIE istnieje) ===");
            int updatedMissing = updateEmail(connection, 999, "ktos@example.com");
            System.out.println("Liczba zaktualizowanych wierszy: " + updatedMissing);
            System.out.println(updatedMissing == 0
                    ? "-> 0 oznacza, ze taki rekord nie istnial - kod aplikacji"
                    + " powinien to sprawdzic i np. rzucic wlasny wyjatek"
                    + " 'UserNotFoundException'."
                    : "-> nieoczekiwany wynik!");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - UPDATE w JDBC wykonuje się analogicznie do INSERT-a -
         *   przez PreparedStatement.executeUpdate().
         * - `executeUpdate()` zwraca int - liczbę faktycznie zmienionych
         *   wierszy. To NIE jest informacja "czy był błąd", tylko
         *   "ile wierszy dotknęła operacja".
         * - Wynik 0 przy UPDATE po ID zwykle oznacza, że rekord o takim
         *   ID nie istnieje - warto to sprawdzać i reagować (np. rzucić
         *   dedykowany wyjątek albo zwrócić informację "nie znaleziono").
         * - Ignorowanie wyniku executeUpdate() to częsty błąd - kod
         *   "wygląda" na poprawny, ale cicho nie robi tego, czego
         *   oczekujemy.
         */
    }

    /**
     * Aktualizuje adres e-mail użytkownika o podanym ID.
     * Zwraca liczbę zmienionych wierszy (0 lub 1, bo id jest kluczem głównym).
     */
    static int updateEmail(Connection connection, int id, String newEmail) throws SQLException {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEmail);
            statement.setInt(2, id);
            return statement.executeUpdate();
        }
    }

    private static void printUser(Connection connection, int id) throws SQLException {
        String sql = "SELECT name, email FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Po update: " + rs.getString("name") + " -> " + rs.getString("email"));
                }
            }
        }
    }
}
