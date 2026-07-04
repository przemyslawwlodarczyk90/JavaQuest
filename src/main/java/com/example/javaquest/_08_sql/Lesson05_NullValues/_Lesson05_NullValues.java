package com.example.javaquest._08_sql.Lesson05_NullValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class _Lesson05_NullValues {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST NULL?
         * ============================================================
         * NULL w SQL oznacza BRAK WARTOŚCI – "nie wiadomo" / "nie
         * dotyczy" / "nie podano". To NIE jest to samo co:
         * - liczba 0            (0 to KONKRETNA wartość liczbowa),
         * - pusty String ""     (to KONKRETNY, istniejący, po prostu
         *                         pusty ciąg znaków),
         * - false                (to KONKRETNA wartość logiczna).
         *
         * NULL to "dziura" w danych – kolumna dla tego wiersza po prostu
         * NIE MA żadnej wartości. Przykład: kolumna "middle_name" (drugie
         * imię) – wiele osób go nie ma, więc NULL jest tu semantycznie
         * poprawną reprezentacją "nie dotyczy", a NIE pusty String.
         *
         * ⚠️ NULL "zaraża" wyrażenia: NULL + 5 = NULL, NULL = NULL daje
         * w SQL wynik NULL (nie true!) – dlatego do porównań z NULL
         * NIGDY nie używamy "=", tylko specjalnych operatorów (niżej).
         */

        System.out.println("=== CZYM JEST NULL ===");
        System.out.println("NULL = brak wartosci. To NIE 0, NIE pusty String, NIE false.");

        /*
         * ============================================================
         * 🔹 IS NULL / IS NOT NULL / ograniczenie NOT NULL
         * ============================================================
         * Do sprawdzania, czy kolumna ma wartość NULL, używamy operatorów
         * IS NULL i IS NOT NULL (NIE "= NULL" - to zawsze da wynik NULL,
         * czyli "nieznany", a nie true/false!).
         *
         * Ograniczenie NOT NULL (patrz też Lesson06_DataConstraints)
         * na poziomie definicji kolumny CAŁKOWICIE ZABRANIA wstawienia
         * NULL do tej kolumny - baza odrzuci taką próbę błędem.
         */

        System.out.println("\n=== IS NULL / IS NOT NULL / NOT NULL ===");
        System.out.println("Porownanie 'kolumna = NULL' NIGDY nie dziala - uzywaj IS NULL / IS NOT NULL.");

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson05;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        middle_name VARCHAR(100),
                        age INT
                    )
                    """);
            // "name" jest NOT NULL (wymagane), "middle_name" i "age"
            // mogą pozostać NULL (opcjonalne / nieznane).

            stmt.execute("INSERT INTO users (id, name, middle_name, age) VALUES (1, 'Jan Kowalski', 'Piotr', 30)");
            stmt.execute("INSERT INTO users (id, name, middle_name, age) VALUES (2, 'Anna Nowak', NULL, NULL)");
            // Anna nie ma drugiego imienia i nie podala wieku - oba NULL.

            System.out.println("\n=== Uzytkownicy BEZ drugiego imienia (IS NULL) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE middle_name IS NULL")) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("name"));
                }
            }
            // - Anna Nowak

            System.out.println("\n=== Uzytkownicy Z drugim imieniem (IS NOT NULL) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE middle_name IS NOT NULL")) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("name"));
                }
            }
            // - Jan Kowalski

            System.out.println("\n=== PROBA WSTAWIENIA NULL DO KOLUMNY NOT NULL ===");
            try {
                stmt.execute("INSERT INTO users (id, name) VALUES (3, NULL)");
                System.out.println("To sie nie powinno wykonac!");
            } catch (SQLException e) {
                System.out.println("Odrzucono przez ograniczenie NOT NULL: " + e.getMessage());
            }

            /*
             * ============================================================
             * 🔍 PUŁAPKA: getInt() NA KOLUMNIE NULL ZWRACA 0!
             * ============================================================
             * To jedna z najczęstszych pułapek dla początkujących w JDBC.
             * getInt() (i podobne metody na typach prymitywnych: getLong,
             * getDouble...) NIE MOŻE zwrócić null (typy prymitywne w Javie
             * nie mają wartości null!) - gdy kolumna faktycznie ma NULL,
             * JDBC po cichu zwraca 0. To wygląda tak, jakby ktoś podał
             * wiek 0 - ale to NIE PRAWDA, po prostu wiek nie był podany!
             *
             * Jak to bezpiecznie odróżnić?
             * 1) rs.wasNull() - wywołane ZARAZ PO getInt(), mówi, czy
             *    OSTATNIO odczytana wartość faktycznie była NULL-em.
             * 2) rs.getObject("age") - dla typów referencyjnych zwraca
             *    prawdziwe null (Integer, nie int) - bezpieczniejsze.
             */

            System.out.println("\n=== PULAPKA: getInt() na NULL zwraca 0 ===");
            try (ResultSet rs = stmt.executeQuery("SELECT name, age FROM users ORDER BY id")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int ageAsInt = rs.getInt("age");
                    boolean wasNull = rs.wasNull(); // MUSI byc wywolane zaraz po getInt()!
                    System.out.println(name + ": getInt()=" + ageAsInt + ", wasNull()=" + wasNull);
                }
            }
            // Jan Kowalski: getInt()=30, wasNull()=false
            // Anna Nowak: getInt()=0, wasNull()=true   <- 0 to FALSZYWY wiek, prawda to "brak danych"!

            System.out.println("\n=== BEZPIECZNY ODCZYT PRZEZ getObject() + Optional ===");
            try (ResultSet rs = stmt.executeQuery("SELECT name, age FROM users ORDER BY id")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    Integer ageOrNull = (Integer) rs.getObject("age"); // prawdziwy null, gdy brak wartosci
                    Optional<Integer> age = Optional.ofNullable(ageOrNull);
                    System.out.println(name + ": age=" + age.map(String::valueOf).orElse("brak danych"));
                }
            }
            // Jan Kowalski: age=30
            // Anna Nowak: age=brak danych
        }

        /*
         * ============================================================
         * 🔹 Optional JAKO KONCEPCJA (nawiązanie do Javy)
         * ============================================================
         * Optional<T> w Javie to odpowiednik idei NULL w SQL, ale na
         * poziomie TYPÓW: zamiast "wartość może być null (i o tym nie
         * wiadomo patrząc na typ)", Optional<Integer> WPROST mówi w API
         * "ta wartość może nie istnieć - musisz to obsłużyć". Gdy
         * odczytujesz dane z bazy do obiektów Javy, dobrą praktyką jest
         * mapowanie potencjalnie NULL-owych kolumn SQL na Optional<T>
         * w warstwie serwisowej aplikacji - zamiast przekazywać dalej
         * "gołe" null, które łatwo przeoczyć i doprowadzić do
         * NullPointerException.
         */

        System.out.println("\n=== Optional jako odpowiednik NULL na poziomie typow Javy ===");
        System.out.println("Optional<Integer> jawnie mowi w API: ta wartosc moze nie istniec.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - NULL = brak wartości, to NIE 0, NIE "", NIE false.
         * - Porównania z NULL: TYLKO przez IS NULL / IS NOT NULL.
         * - NOT NULL (constraint) - zabrania wstawienia NULL do kolumny.
         * - ⚠️ PUŁAPKA: getInt()/getLong()/getDouble() na kolumnie NULL
         *   po cichu zwracają 0/0L/0.0 - ZAWSZE sprawdzaj rs.wasNull()
         *   zaraz po odczycie, albo użyj getObject() (zwraca prawdziwy
         *   null dla typów referencyjnych, np. Integer).
         * - Optional<T> w Javie to typowy odpowiednik idei NULL - jawnie
         *   zaznacza w API, że wartość może nie istnieć.
         */
    }
}
