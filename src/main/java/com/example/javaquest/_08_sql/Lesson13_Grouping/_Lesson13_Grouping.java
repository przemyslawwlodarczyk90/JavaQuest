package com.example.javaquest._08_sql.Lesson13_Grouping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson13_Grouping {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 GROUP BY – GRUPOWANIE WIERSZY I FUNKCJE AGREGUJĄCE
         * ============================================================
         * GROUP BY grupuje wiersze o TEJ SAMEJ wartości wskazanej
         * kolumny (lub kolumn) w jedną "zbiorczą" grupę, do której można
         * zastosować FUNKCJE AGREGUJĄCE (liczące jedną wartość z wielu
         * wierszy grupy):
         * - COUNT(*)   – liczba wierszy w grupie
         * - SUM(kol)   – suma wartości w grupie
         * - AVG(kol)   – średnia wartości w grupie
         * - MIN(kol)   – najmniejsza wartość w grupie
         * - MAX(kol)   – największa wartość w grupie
         *
         * Connection i Statement poznamy szczegółowo w rozdziale o JDBC
         * – tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         */

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson13;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        user_id INT,
                        amount DECIMAL(10, 2)
                    )
                    """);
            stmt.execute("INSERT INTO orders VALUES (1, 100, 50.00)");
            stmt.execute("INSERT INTO orders VALUES (2, 100, 30.00)");
            stmt.execute("INSERT INTO orders VALUES (3, 100, 120.00)");
            stmt.execute("INSERT INTO orders VALUES (4, 200, 200.00)");
            stmt.execute("INSERT INTO orders VALUES (5, 200, 15.00)");
            stmt.execute("INSERT INTO orders VALUES (6, 300, 999.00)");

            /*
             * ============================================================
             * 🔹 GROUP BY + COUNT – liczba zamówień na użytkownika
             * ============================================================
             * Każdy user_id trafia do osobnej grupy – COUNT(*) liczy,
             * ile wierszy (zamówień) znalazło się w każdej grupie.
             *
             * ⚠️ WAŻNE: w SELECT razem z funkcją agregującą można podać
             * TYLKO kolumny, po których grupujemy (tu: user_id) – SQL nie
             * pozwoli wybrać np. "amount" bez agregacji, bo nie wiadomo,
             * KTÓRY wiersz grupy miałby reprezentować tę kolumnę.
             */
            System.out.println("=== GROUP BY user_id + COUNT(*) ===");
            print(stmt.executeQuery("""
                    SELECT user_id, COUNT(*) AS liczba_zamowien
                    FROM orders
                    GROUP BY user_id
                    """));

            /*
             * ============================================================
             * 🔹 SUM, AVG, MIN, MAX w jednym zapytaniu
             * ============================================================
             */
            System.out.println("\n=== GROUP BY user_id + SUM/AVG/MIN/MAX ===");
            print(stmt.executeQuery("""
                    SELECT user_id,
                           SUM(amount) AS suma,
                           AVG(amount) AS srednia,
                           MIN(amount) AS minimalna,
                           MAX(amount) AS maksymalna
                    FROM orders
                    GROUP BY user_id
                    """));

            /*
             * ============================================================
             * 🔹 HAVING – filtrowanie WYNIKÓW GRUPOWANIA
             * ============================================================
             * WHERE filtruje POJEDYNCZE wiersze PRZED grupowaniem, a
             * HAVING filtruje już ZAGREGOWANE grupy PO grupowaniu – dlatego
             * HAVING może odnosić się do funkcji agregujących (np.
             * COUNT(*), SUM(...)), a WHERE nie może (bo w momencie
             * filtrowania WHERE agregaty jeszcze nie istnieją).
             *
             * Przykład: użytkownicy, którzy złożyli więcej niż 1 zamówienie.
             */
            System.out.println("\n=== HAVING COUNT(*) > 1 ===");
            print(stmt.executeQuery("""
                    SELECT user_id, COUNT(*) AS liczba_zamowien
                    FROM orders
                    GROUP BY user_id
                    HAVING COUNT(*) > 1
                    """));

            /*
             * ============================================================
             * 🔹 WHERE + GROUP BY + HAVING razem
             * ============================================================
             * Kolejność logicznego wykonania: najpierw WHERE (filtruje
             * pojedyncze wiersze), potem GROUP BY (grupuje to, co zostało),
             * potem HAVING (filtruje gotowe grupy).
             *
             * Przykład: suma zamówień powyżej 20 zł, tylko dla
             * użytkowników, których suma takich zamówień przekracza 100 zł.
             */
            System.out.println("\n=== WHERE + GROUP BY + HAVING ===");
            print(stmt.executeQuery("""
                    SELECT user_id, SUM(amount) AS suma
                    FROM orders
                    WHERE amount > 20
                    GROUP BY user_id
                    HAVING SUM(amount) > 100
                    """));
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - GROUP BY grupuje wiersze o tej samej wartości kolumny (kolumn)
         * - Funkcje agregujące (COUNT, SUM, AVG, MIN, MAX) liczą jedną
         *   wartość z wielu wierszy grupy
         * - W SELECT z GROUP BY można wybrać tylko kolumny grupujące oraz
         *   wyrażenia agregujące – nie "surowe" kolumny spoza GROUP BY
         * - WHERE filtruje POJEDYNCZE wiersze PRZED grupowaniem
         * - HAVING filtruje GOTOWE GRUPY PO grupowaniu – tylko HAVING może
         *   odwoływać się do funkcji agregujących w warunku
         * - Kolejność logiczna: WHERE -> GROUP BY -> HAVING
         */
    }

    private static void print(ResultSet rs) throws SQLException {
        try (rs) {
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder(" - ");
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getMetaData().getColumnLabel(i)).append("=").append(rs.getString(i));
                }
                System.out.println(sb);
            }
        }
    }
}
