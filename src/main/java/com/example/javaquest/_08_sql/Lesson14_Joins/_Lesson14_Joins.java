package com.example.javaquest._08_sql.Lesson14_Joins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson14_Joins {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 JOIN – ŁĄCZENIE DANYCH Z WIELU TABEL
         * ============================================================
         * To jeden z NAJWAŻNIEJSZYCH tematów w SQL – dane relacyjne
         * celowo dzielimy na wiele powiązanych tabel (patrz
         * Lesson07_Normalization, Lesson15_SqlRelationships), więc bez
         * umiejętności ich ŁĄCZENIA nie da się sensownie programować baz
         * danych. Praktycznie każde nietrywialne zapytanie w realnej
         * aplikacji korzysta z JOIN-ów.
         *
         * JOIN łączy wiersze z dwóch (lub więcej) tabel na podstawie
         * warunku (zwykle: klucz obcy = klucz główny). Rodzaje JOIN-ów
         * różnią się tym, CO robimy z wierszami, dla których NIE ma
         * dopasowania po drugiej stronie.
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson14;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100)
                    )
                    """);
            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski')");
            stmt.execute("INSERT INTO users VALUES (2, 'Anna Nowak')");
            stmt.execute("INSERT INTO users VALUES (3, 'Piotr Zielinski')"); // brak zamowien

            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT,
                        amount DECIMAL(10, 2)
                    )
                    """);
            stmt.execute("INSERT INTO orders VALUES (101, 1, 50.00)");
            stmt.execute("INSERT INTO orders VALUES (102, 1, 30.00)");
            stmt.execute("INSERT INTO orders VALUES (103, 2, 199.00)");
            // Uwaga: user_id=99 celowo NIE istnieje w tabeli users
            // (osierocone zamowienie - do demonstracji RIGHT/FULL JOIN)
            stmt.execute("INSERT INTO orders VALUES (104, 99, 15.00)");

            System.out.println("Dane wejsciowe: 3 uzytkownikow (Piotr bez zamowien), 4 zamowienia");
            System.out.println("(zamowienie 104 ma user_id=99, ktorego nie ma w tabeli users)\n");

            /*
             * ============================================================
             * 🔹 INNER JOIN
             * ============================================================
             * Zwraca TYLKO wiersze, które mają DOPASOWANIE PO OBU
             * stronach. Piotr (brak zamówień) i zamówienie 104 (brak
             * użytkownika) zostaną POMINIĘTE.
             */
            System.out.println("=== INNER JOIN ===");
            printCounted(stmt.executeQuery("""
                    SELECT u.name, o.id AS order_id, o.amount
                    FROM users u
                    INNER JOIN orders o ON o.user_id = u.id
                    """));

            /*
             * ============================================================
             * 🔹 LEFT JOIN (LEFT OUTER JOIN)
             * ============================================================
             * Zwraca WSZYSTKIE wiersze z lewej tabeli (users), nawet jeśli
             * nie mają dopasowania po prawej – wtedy kolumny z prawej
             * tabeli (orders) mają wartość NULL. Piotr POJAWI się w
             * wyniku (z NULL zamiast danych zamówienia), ale osierocone
             * zamówienie 104 nadal zostanie pominięte.
             */
            System.out.println("\n=== LEFT JOIN ===");
            printCounted(stmt.executeQuery("""
                    SELECT u.name, o.id AS order_id, o.amount
                    FROM users u
                    LEFT JOIN orders o ON o.user_id = u.id
                    """));

            /*
             * ============================================================
             * 🔹 RIGHT JOIN (RIGHT OUTER JOIN)
             * ============================================================
             * Lustrzane odbicie LEFT JOIN – zwraca WSZYSTKIE wiersze z
             * prawej tabeli (orders), nawet bez dopasowania po lewej.
             * Tu osierocone zamówienie 104 POJAWI się w wyniku (z NULL
             * zamiast danych użytkownika), a Piotr (bez zamówień) zniknie.
             */
            System.out.println("\n=== RIGHT JOIN ===");
            printCounted(stmt.executeQuery("""
                    SELECT u.name, o.id AS order_id, o.amount
                    FROM users u
                    RIGHT JOIN orders o ON o.user_id = u.id
                    """));

            /*
             * ============================================================
             * 🔹 FULL JOIN (FULL OUTER JOIN)
             * ============================================================
             * Koncepcyjnie: suma LEFT JOIN i RIGHT JOIN – zwraca
             * WSZYSTKIE wiersze z OBU tabel, dopasowane tam, gdzie to
             * możliwe, a tam gdzie nie ma dopasowania – NULL po
             * brakującej stronie. W takim wyniku powinien pojawić się i
             * Piotr (bez zamówień), i osierocone zamówienie 104 (bez
             * użytkownika).
             *
             * ⚠️ PRZENOŚNOŚĆ: FULL JOIN / FULL OUTER JOIN to NIE jest
             * funkcja obsługiwana przez każdy silnik SQL – np. MySQL
             * historycznie jej nie miał. Co ZASKAKUJĄCE, H2 (mimo że
             * wspiera LEFT/RIGHT OUTER JOIN) w wersji użytej w tym
             * projekcie (2.3.232) RÓWNIEŻ NIE OBSŁUGUJE ani "FULL JOIN",
             * ani "FULL OUTER JOIN" – obie składnie kończą się błędem
             * składniowym (sprawdzone empirycznie na tej wersji H2).
             *
             * Standardowym obejściem, działającym praktycznie wszędzie,
             * jest emulacja przez UNION (bez ALL – dzięki temu SQL sam
             * usunie duplikaty wierszy, które i tak pojawiłyby się w obu
             * częściach, czyli dopasowane pary user+order):
             *     LEFT JOIN  (wszyscy uzytkownicy + dopasowania)
             *     UNION
             *     RIGHT JOIN (wszystkie zamowienia + dopasowania)
             */
            System.out.println("\n=== FULL JOIN – emulacja przez UNION LEFT + RIGHT JOIN ===");
            printCounted(stmt.executeQuery("""
                    SELECT u.name, o.id AS order_id, o.amount
                    FROM users u
                    LEFT JOIN orders o ON o.user_id = u.id
                    UNION
                    SELECT u.name, o.id AS order_id, o.amount
                    FROM users u
                    RIGHT JOIN orders o ON o.user_id = u.id
                    """));

            /*
             * ============================================================
             * 🔹 CROSS JOIN
             * ============================================================
             * Zwraca ILOCZYN KARTEZJAŃSKI – KAŻDY wiersz lewej tabeli
             * połączony z KAŻDYM wierszem prawej tabeli, BEZ ŻADNEGO
             * warunku dopasowania. Liczba wierszy wyniku = (liczba
             * wierszy users) * (liczba wierszy orders) = 3 * 4 = 12.
             * Używany rzadko (np. generowanie wszystkich możliwych
             * kombinacji, jak rozmiary x kolory produktu) – dla dużych
             * tabel może być bardzo kosztowny.
             */
            System.out.println("\n=== CROSS JOIN (iloczyn kartezjanski, 3 x 4 = 12 wierszy) ===");
            printCounted(stmt.executeQuery("""
                    SELECT u.name, o.id AS order_id
                    FROM users u
                    CROSS JOIN orders o
                    """));

            /*
             * ============================================================
             * 🔹 SELF JOIN – tabela połączona SAMA ZE SOBĄ
             * ============================================================
             * SELF JOIN to nie osobny typ JOIN-a (technicznie to zwykły
             * INNER/LEFT JOIN), tylko przypadek, gdy tabela odwołuje się
             * do SIEBIE SAMEJ – typowo przez kolumnę wskazującą na inny
             * wiersz TEJ SAMEJ tabeli (tu: manager_id wskazujący na id
             * innego pracownika). Żeby SQL wiedział, o "którą kopię"
             * tabeli chodzi w danym miejscu, nadajemy jej DWA RÓŻNE
             * ALIASY (tu: e - pracownik, m - jego manager).
             *
             * Używamy LEFT JOIN, bo najwyższy w hierarchii pracownik
             * (CEO) nie ma managera (manager_id = NULL) i mimo to ma się
             * pojawić w wyniku.
             */
            stmt.execute("""
                    CREATE TABLE employees (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        manager_id INT
                    )
                    """);
            stmt.execute("INSERT INTO employees VALUES (1, 'Ewa Kowalska (CEO)', NULL)");
            stmt.execute("INSERT INTO employees VALUES (2, 'Marek Nowak', 1)");
            stmt.execute("INSERT INTO employees VALUES (3, 'Kasia Wisniewska', 1)");
            stmt.execute("INSERT INTO employees VALUES (4, 'Tomasz Zielinski', 2)");

            System.out.println("\n=== SELF JOIN (pracownik -> jego manager) ===");
            printCounted(stmt.executeQuery("""
                    SELECT e.name AS pracownik, m.name AS manager
                    FROM employees e
                    LEFT JOIN employees m ON e.manager_id = m.id
                    """));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - INNER JOIN  – tylko wiersze z dopasowaniem po OBU stronach
         * - LEFT JOIN   – wszystkie wiersze z lewej tabeli + dopasowanie
         *   (lub NULL) z prawej
         * - RIGHT JOIN  – wszystkie wiersze z prawej tabeli + dopasowanie
         *   (lub NULL) z lewej
         * - FULL JOIN / FULL OUTER JOIN – koncepcyjnie suma LEFT+RIGHT
         *   (wszystkie wiersze z OBU tabel, niedopasowane strony
         *   wypełnione NULL) – ALE H2 (w wersji 2.3.232 użytej w tym
         *   projekcie) NIE OBSŁUGUJE tej składni wcale (błąd składniowy!);
         *   trzeba ją emulować przez UNION LEFT JOIN + RIGHT JOIN
         * - CROSS JOIN  – iloczyn kartezjański, KAŻDY z KAŻDYM, bez
         *   warunku dopasowania (liczba wierszy = iloczyn liczby wierszy
         *   obu tabel)
         * - SELF JOIN   – tabela połączona sama ze sobą (przez dwa różne
         *   aliasy), typowo do hierarchii (np. pracownik -> manager)
         * - JOIN to fundament pracy z bazami relacyjnymi – niemal każde
         *   praktyczne zapytanie w realnej aplikacji go używa
         */
    }

    private static void printCounted(ResultSet rs) throws SQLException {
        try (rs) {
            int columnCount = rs.getMetaData().getColumnCount();
            int rows = 0;
            while (rs.next()) {
                StringBuilder sb = new StringBuilder(" - ");
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getMetaData().getColumnLabel(i)).append("=").append(rs.getString(i));
                }
                System.out.println(sb);
                rows++;
            }
            System.out.println("(liczba wierszy: " + rows + ")");
        }
    }
}
