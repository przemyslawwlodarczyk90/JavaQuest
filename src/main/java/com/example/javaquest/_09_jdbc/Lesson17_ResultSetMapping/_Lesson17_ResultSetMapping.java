package com.example.javaquest._09_jdbc.Lesson17_ResultSetMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _Lesson17_ResultSetMapping {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 OD WIERSZA W BAZIE DO OBIEKTU JAVA
         * ============================================================
         * ResultSet reprezentuje surowy wynik zapytania SQL - tabelę
         * wierszy i kolumn. W prawdziwym kodzie Java prawie nigdy nie
         * chcemy operować bezpośrednio na ResultSet poza miejscem, gdzie
         * go odczytujemy - wygodniej jest zamienić KAŻDY wiersz na zwykły
         * obiekt Java (np. rekord User), z którym reszta aplikacji
         * pracuje jak z normalnym obiektem, bez wiedzy o SQL.
         *
         * Ten proces nazywamy MAPOWANIEM (mapping):
         * - KOLUMNA w wyniku zapytania -> POLE obiektu Java
         * - TYP SQL (np. BIGINT, VARCHAR) -> TYP Java (np. long, String)
         *
         * 🔹 TABELA TYPÓW (najczęstsze odpowiedniki)
         * - INT / INTEGER      -> int            (rs.getInt(...))
         * - BIGINT             -> long            (rs.getLong(...))
         * - VARCHAR / TEXT     -> String          (rs.getString(...))
         * - BOOLEAN            -> boolean         (rs.getBoolean(...))
         * - DATE               -> java.sql.Date   (rs.getDate(...))
         * - TIMESTAMP          -> java.sql.Timestamp (rs.getTimestamp(...))
         * - DECIMAL / NUMERIC  -> java.math.BigDecimal (rs.getBigDecimal(...))
         */

        String url = "jdbc:h2:mem:jdbclesson17;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);

            /*
             * ============================================================
             * 🔹 mapRow() - WYDZIELONA METODA MAPUJĄCA
             * ============================================================
             * Zła praktyka: powtarzać ten sam kod mapowania (rs.getLong,
             * rs.getString...) w każdym miejscu, gdzie wykonujemy zapytanie
             * SELECT na tabeli users. Dobra praktyka: napisać JEDNĄ metodę
             * mapRow(ResultSet), która dla BIEŻĄCEGO wiersza ResultSet
             * (czyli już PO wywołaniu rs.next()) zwraca gotowy obiekt User:
             *
             *   User user = new User(
             *       resultSet.getLong("id"),
             *       resultSet.getString("email"),
             *       resultSet.getString("first_name")
             *   );
             *
             * Dzięki temu, jeśli kiedyś zmieni się struktura tabeli albo
             * klasy User, poprawiamy mapowanie w JEDNYM miejscu, a nie
             * we wszystkich zapytaniach, które zwracają userów.
             */

            System.out.println("=== findById(2) - mapowanie pojedynczego wiersza ===");
            User znaleziony = findById(connection, 2);
            System.out.println(znaleziony);

            System.out.println("\n=== findAll() - mapowanie wielu wierszy do listy ===");
            List<User> wszyscy = findAll(connection);
            wszyscy.forEach(System.out::println);
            System.out.println("Liczba uzytkownikow: " + wszyscy.size());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Mapowanie = zamiana wiersza ResultSet na zwykły obiekt Java
         *   (kolumna -> pole, typ SQL -> typ Java)
         * - Dobra praktyka: wydzielić OSOBNĄ metodę mapRow(ResultSet),
         *   zamiast powtarzać rs.getXxx(...) w każdym zapytaniu
         * - mapRow() operuje na BIEŻĄCYM wierszu (wołane PO rs.next())
         * - Ta sama metoda mapRow() może być użyta zarówno przy
         *   mapowaniu JEDNEGO wiersza (findById), jak i WIELU wierszy
         *   w pętli budującej listę (findAll)
         */
    }

    /**
     * Prosty, niezmienny obiekt reprezentujący wiersz z tabeli users.
     * To jeszcze nie "model domenowy" w pełnym sensie (patrz Lesson18) -
     * tu chodzi wyłącznie o efekt mapowania ResultSet -> obiekt.
     */
    private record User(long id, String email, String firstName) {
    }

    /**
     * Mapuje BIEŻĄCY wiersz ResultSet (po wywołaniu rs.next()) na obiekt User.
     * Wydzielenie tej logiki do jednej metody to dobra praktyka - unikamy
     * powtarzania mapowania w każdym miejscu, gdzie odczytujemy userów.
     */
    private static User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("first_name")
        );
    }

    private static User findById(Connection connection, long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT id, email, first_name FROM users WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                throw new IllegalStateException("Brak uzytkownika o id=" + id);
            }
        }
    }

    private static List<User> findAll(Connection connection) throws SQLException {
        List<User> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, email, first_name FROM users ORDER BY id")) {
            while (rs.next()) {
                result.add(mapRow(rs)); // ta sama metoda mapujaca co przy pojedynczym wierszu
            }
        }
        return result;
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        first_name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("INSERT INTO users (email, first_name) VALUES ('ania@example.com', 'Ania')");
            stmt.execute("INSERT INTO users (email, first_name) VALUES ('bartek@example.com', 'Bartek')");
            stmt.execute("INSERT INTO users (email, first_name) VALUES ('celina@example.com', 'Celina')");
        }
    }
}
