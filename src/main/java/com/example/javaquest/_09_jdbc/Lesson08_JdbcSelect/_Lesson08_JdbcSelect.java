package com.example.javaquest._09_jdbc.Lesson08_JdbcSelect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Lesson08_JdbcSelect {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 SELECT W JDBC - DWA GŁÓWNE SCENARIUSZE
         * ============================================================
         * W poprzednich lekcjach poznaliśmy PreparedStatement (Lesson05)
         * oraz ResultSet (Lesson06), a w Lesson07 użyliśmy ich do wstawiania
         * danych (INSERT). Teraz zajmiemy się odczytem danych - SELECT-em.
         *
         * W praktyce niemal każda aplikacja potrzebuje dwóch wariantów
         * odczytu:
         * 1) Pobranie JEDNEGO rekordu (np. po ID) - "znajdź użytkownika
         *    o id=5". Zapytanie zwraca 0 albo 1 wiersz.
         * 2) Pobranie LISTY rekordów (np. wszystkich użytkowników albo
         *    tych spełniających jakiś warunek) - zapytanie zwraca 0, 1
         *    lub wiele wierszy.
         *
         * Oba warianty różnią się tym, JAK iterujemy po ResultSet:
         * - dla jednego rekordu używamy pojedynczego `if (rs.next())`,
         * - dla listy rekordów używamy pętli `while (rs.next())`,
         *   budując po drodze listę obiektów.
         */

        String url = "jdbc:h2:mem:jdbclesson08;DB_CLOSE_DELAY=-1";

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
            setup.executeUpdate("INSERT INTO users VALUES (3, 'Piotr Wisniewski', 'piotr@example.com')");
            System.out.println("Tabela 'users' przygotowana (3 wiersze).\n");

            /*
             * ============================================================
             * 🔹 MAPOWANIE ResultSet -> OBIEKT
             * ============================================================
             * Zamiast operować na "surowych" wartościach z ResultSet w
             * całym kodzie, dobrą praktyką jest zmapowanie jednego wiersza
             * na prosty obiekt (tu: rekord `User`). Dzięki temu reszta
             * aplikacji pracuje na czytelnych obiektach, a nie na
             * kolumnach o nazwach String ("id", "name", "email").
             *
             * Metoda `mapRow` poniżej robi dokładnie to - bierze "bieżący"
             * wiersz ResultSet (ten, na którym aktualnie stoi kursor) i
             * zwraca z niego obiekt User.
             */

            System.out.println("=== findById(2) - JEDEN rekord ===");
            Optional<User> found = findById(connection, 2);
            System.out.println(found.orElse(null));

            System.out.println("\n=== findById(999) - rekord nie istnieje ===");
            Optional<User> notFound = findById(connection, 999);
            System.out.println("Znaleziono: " + notFound.isPresent());

            System.out.println("\n=== findAll() - LISTA rekordów ===");
            List<User> all = findAll(connection);
            all.forEach(System.out::println);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Pobranie JEDNEGO rekordu: SELECT ... WHERE id = ? +
         *   pojedyncze `if (rs.next())` - jeśli warunek się nie spełni,
         *   znaczy że rekord nie istnieje (dobrze reprezentować to jako
         *   `Optional<T>` zamiast zwracać null).
         * - Pobranie LISTY rekordów: SELECT (bez warunku albo z warunkiem
         *   zwracającym wiele wierszy) + pętla `while (rs.next())`
         *   budująca `List<T>`.
         * - Warto wydzielić mapowanie "wiersz ResultSet -> obiekt" do
         *   osobnej metody (`mapRow`), żeby nie duplikować kodu między
         *   metodą zwracającą jeden obiekt a metodą zwracającą listę.
         * - Więcej o samym mapowaniu ResultSet na obiekty domenowe -
         *   w dalszych lekcjach tego rozdziału (Lesson17_ResultSetMapping).
         */
    }

    /**
     * Pobiera JEDEN rekord po ID. Zwraca Optional.empty(), jeśli
     * użytkownik o podanym ID nie istnieje.
     */
    static Optional<User> findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, name, email FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    /**
     * Pobiera WSZYSTKIE rekordy z tabeli users jako listę.
     */
    static List<User> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
    }

    record User(int id, String name, String email) {
    }
}
