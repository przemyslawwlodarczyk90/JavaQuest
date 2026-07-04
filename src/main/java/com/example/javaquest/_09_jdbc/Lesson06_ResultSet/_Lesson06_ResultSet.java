package com.example.javaquest._09_jdbc.Lesson06_ResultSet;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class _Lesson06_ResultSet {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST ResultSet?
         * ============================================================
         * `java.sql.ResultSet` to wynik zapytania SELECT - ale UWAGA:
         * to NIE jest "cała tabela wyników" wczytana od razu do pamięci.
         * To raczej KURSOR - wskaźnik na JEDEN, AKTUALNY wiersz danych,
         * który możemy przesuwać metodą next().
         *
         * Zaraz po wykonaniu executeQuery() kursor stoi PRZED pierwszym
         * wierszem (nie NA nim!). Dopiero wywołanie next() przesuwa go
         * na pierwszy wiersz i zwraca true, jeśli taki wiersz istnieje.
         * Gdy next() zwróci false - wierszy już nie ma, pętla się kończy.
         *
         *     while (resultSet.next()) {
         *         // tu odczytujemy dane z AKTUALNEGO wiersza
         *     }
         *
         * Ten model "kursora" jest wydajny - baza danych (i driver) mogą
         * przesyłać wiersze STOPNIOWO, zamiast ładować od razu miliony
         * rekordów do pamięci aplikacji.
         */

        String url = "jdbc:h2:mem:jdbclesson06;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE students (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        grade_avg DECIMAL(3,2),
                        enrolled_at TIMESTAMP,
                        scholarship_amount DECIMAL(8,2)
                    )
                    """);
            statement.executeUpdate("INSERT INTO students VALUES (1, 'Kasia Wisniewska', 4.75, '2021-10-01 08:00:00', 500.00)");
            statement.executeUpdate("INSERT INTO students VALUES (2, 'Tomasz Zielinski', 3.80, '2022-10-01 08:00:00', NULL)");
            statement.executeUpdate("INSERT INTO students VALUES (3, 'Ola Kaminska', 5.00, '2020-10-01 08:00:00', 1000.00)");

            /*
             * ============================================================
             * 🔹 GETTERY DLA RÓŻNYCH TYPÓW
             * ============================================================
             * Podobnie jak PreparedStatement ma settery, ResultSet ma
             * GETTERY dla każdego typu danych: getString(), getInt(),
             * getLong(), getBigDecimal(), getDate(), getTimestamp()...
             * Każdy przyjmuje albo NAZWĘ kolumny (czytelniejsze), albo
             * jej NUMER (indeksowany OD 1, szybszy, ale mniej czytelny).
             */

            System.out.println("=== ITEROWANIE PO WIERSZACH ResultSet ===");
            try (ResultSet rs = statement.executeQuery(
                    "SELECT id, name, grade_avg, enrolled_at, scholarship_amount FROM students ORDER BY id")) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    BigDecimal gradeAvg = rs.getBigDecimal("grade_avg");
                    Timestamp enrolledAt = rs.getTimestamp("enrolled_at");
                    BigDecimal scholarship = rs.getBigDecimal("scholarship_amount");

                    System.out.println(id + ": " + name + ", srednia: " + gradeAvg
                            + ", zapisany: " + enrolledAt + ", stypendium: " + scholarship);
                }
            }

            /*
             * ============================================================
             * ⚠️ PUŁAPKA: getInt() / prymitywy a wartość NULL
             * ============================================================
             * Znamy to już z rozdziału _08_sql: kolumny SQL mogą zawierać
             * NULL (brak wartości). Metody zwracające PRYMITYWY Javy
             * (np. getInt(), getLong(), getDouble()) NIE MOGĄ zwrócić
             * null (prymityw nie może być null) - więc gdy kolumna w
             * bazie ma wartość NULL, metody te po cichu zwracają 0 (albo
             * 0.0, false itd.)! To bardzo częsty błąd początkujących -
             * wygląda jak poprawna wartość "zero", a w rzeczywistości
             * oznacza "brak danych".
             *
             * ROZWIĄZANIE: użyj metody zwracającej TYP OBIEKTOWY
             * (np. getBigDecimal() zamiast getDouble(), albo
             * getObject("kolumna", Integer.class)) i sprawdź, czy wynik
             * to null - albo sprawdź jawnie przez rs.wasNull() ZARAZ PO
             * wywołaniu gettera.
             */

            System.out.println("\n=== PULAPKA: NULL w kolumnie scholarship_amount (Tomasz) ===");
            try (ResultSet rs = statement.executeQuery(
                    "SELECT name, scholarship_amount FROM students WHERE name = 'Tomasz Zielinski'")) {
                if (rs.next()) {
                    // getBigDecimal (typ obiektowy) poprawnie zwraca null:
                    BigDecimal scholarship = rs.getBigDecimal("scholarship_amount");
                    System.out.println("getBigDecimal() dla NULL: " + scholarship + " (poprawnie: null)");

                    // rs.wasNull() potwierdza, że ostatnio odczytana wartość byla NULL
                    System.out.println("rs.wasNull() po odczycie: " + rs.wasNull());
                }
            }

            /*
             * ============================================================
             * 🔍 MAPOWANIE WIERSZY NA OBIEKTY
             * ============================================================
             * W praktyce niemal zawsze chcemy przekształcić surowe dane z
             * ResultSet na obiekty naszej aplikacji (np. rekord Student),
             * zamiast operować bezpośrednio na "wierszach tabeli". To
             * podstawa wzorca DAO/Repository - pełne, uporządkowane
             * mapowanie (w tym obsługę wielu wierszy na listę) poznamy
             * dokładnie w Lesson17_ResultSetMapping. Tu prosty przedsmak:
             */

            System.out.println("\n=== MAPOWANIE ResultSet -> lista obiektow Student ===");
            List<Student> students = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery(
                    "SELECT id, name, grade_avg FROM students ORDER BY id")) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBigDecimal("grade_avg"));
                    students.add(student);
                }
            }
            students.forEach(System.out::println);
            System.out.println("Liczba zmapowanych obiektow: " + students.size());
        }

        System.out.println("\n=== KONIEC LEKCJI 6 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ResultSet to KURSOR na wynik SELECT, nie cała tabela naraz
         * - next() przesuwa kursor na kolejny wiersz, zwraca false gdy
         *   wierszy już nie ma (kursor startuje PRZED pierwszym wierszem)
         * - Gettery: getString(), getInt(), getLong(), getBigDecimal(),
         *   getDate(), getTimestamp()... po nazwie lub numerze kolumny
         * - PUŁAPKA: gettery prymitywne (getInt, getDouble...) zwracają
         *   0/false zamiast null, gdy kolumna ma wartość NULL - użyj
         *   typu obiektowego (getBigDecimal, getObject(...,Klasa.class))
         *   albo sprawdź rs.wasNull()
         * - Wiersze ResultSet warto mapować na obiekty domenowe naszej
         *   aplikacji (pełny, uporządkowany wzorzec - Lesson17)
         */
    }

    /**
     * Prosty rekord domenowy - cel mapowania wierszy z ResultSet.
     * Pełne, uporządkowane podejście do mapowania poznamy w Lesson17.
     */
    private record Student(int id, String name, BigDecimal gradeAvg) {
    }
}
