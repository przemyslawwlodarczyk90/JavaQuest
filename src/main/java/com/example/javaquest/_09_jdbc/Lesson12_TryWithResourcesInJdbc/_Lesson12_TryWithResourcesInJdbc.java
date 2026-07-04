package com.example.javaquest._09_jdbc.Lesson12_TryWithResourcesInJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson12_TryWithResourcesInJdbc {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 TRY-WITH-RESOURCES W JDBC
         * ============================================================
         * Ogólny mechanizm try-with-resources (interfejs `AutoCloseable`,
         * automatyczne wywołanie `close()` na końcu bloku) poznaliśmy już
         * szczegółowo w rozdziale _04_io (Lesson13_TryWithResources) przy
         * okazji strumieni plikowych. W tej lekcji NIE powtarzamy tamtej
         * teorii - skupiamy się na tym, co jest SPECYFICZNE dla JDBC.
         *
         * W JDBC mamy do czynienia z TRZEMA zasobami, które implementują
         * `AutoCloseable` i które niemal zawsze używamy razem, zagnieżdżone
         * jeden w drugim:
         * - Connection        - połączenie z bazą danych,
         * - Statement / PreparedStatement - obiekt zapytania,
         * - ResultSet         - wynik zapytania SELECT.
         *
         * Wszystkie trzy MUSZĄ zostać zamknięte - to nie jest "dobra
         * praktyka", to konieczność. Niezamknięte Connection to w
         * prawdziwej aplikacji wyciek połączenia z puli connection poolu
         * (np. HikariCP) - pula ma ograniczoną liczbę połączeń, więc
         * wystarczy kilka takich wycieków, żeby cała aplikacja "zawiesiła
         * się" w oczekiwaniu na wolne połączenie.
         */

        String url = "jdbc:h2:mem:jdbclesson12;DB_CLOSE_DELAY=-1";

        try (Connection setupConnection = DriverManager.getConnection(url);
             Statement setup = setupConnection.createStatement()) {
            setup.execute("""
                    CREATE TABLE books (
                        id INT PRIMARY KEY,
                        title VARCHAR(200) NOT NULL
                    )
                    """);
            setup.executeUpdate("INSERT INTO books VALUES (1, 'Pan Tadeusz')");
            setup.executeUpdate("INSERT INTO books VALUES (2, 'Lalka')");
        }

        /*
         * ============================================================
         * 🔹 TRZY ZASOBY ZAGNIEŻDŻONE NARAZ
         * ============================================================
         * Try-with-resources pozwala zadeklarować wiele zasobów w JEDNYM
         * bloku `try (...)`, rozdzielonych średnikiem. Każdy kolejny
         * zasób może korzystać z poprzedniego (tak jak PreparedStatement
         * potrzebuje Connection, a ResultSet potrzebuje PreparedStatement).
         */
        System.out.println("=== Zagniezdzone try-with-resources: Connection + PreparedStatement + ResultSet ===");
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement("SELECT id, title FROM books ORDER BY id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ": " + resultSet.getString("title"));
            }

            /*
             * ============================================================
             * 🔍 KOLEJNOŚĆ ZAMYKANIA - ODWROTNA DO OTWIERANIA
             * ============================================================
             * Zasoby w try-with-resources są zamykane w kolejności
             * ODWROTNEJ do tej, w jakiej zostały otwarte (zadeklarowane).
             * Tutaj otwieraliśmy w kolejności: Connection -> PreparedStatement
             * -> ResultSet. Java zamknie je więc w kolejności:
             * ResultSet -> PreparedStatement -> Connection.
             *
             * To ma sens logicznie: ResultSet "żyje" tylko dzięki
             * PreparedStatement, które z kolei "żyje" tylko dzięki
             * Connection - więc zamykanie zaczyna się od zasobu
             * "najbardziej zależnego", a kończy na najbardziej
             * "podstawowym".
             */
        }
        System.out.println("Wszystkie trzy zasoby zamkniete (w kolejnosci: ResultSet, PreparedStatement, Connection).\n");

        /*
         * ============================================================
         * 🔍 JAK WYGLĄDAŁBY KOD BEZ TRY-WITH-RESOURCES? (DLA KONTRASTU)
         * ============================================================
         * Przed Javą 7 (i bez try-with-resources) trzeba było zamykać
         * zasoby RĘCZNIE w bloku `finally`, i to w odpowiedniej kolejności,
         * z osobną obsługą wyjątków dla KAŻDEGO z nich (bo zamknięcie
         * jednego zasobu mogłoby rzucić wyjątek i "zjeść" zamykanie
         * kolejnych). Wyglądałoby to mniej więcej tak:
         *
         *     Connection connection = null;
         *     PreparedStatement statement = null;
         *     ResultSet resultSet = null;
         *     try {
         *         connection = DriverManager.getConnection(url);
         *         statement = connection.prepareStatement(sql);
         *         resultSet = statement.executeQuery();
         *         // ... przetwarzanie wyniku ...
         *     } finally {
         *         if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { logError(e); }
         *         if (statement != null) try { statement.close(); } catch (SQLException e) { logError(e); }
         *         if (connection != null) try { connection.close(); } catch (SQLException e) { logError(e); }
         *     }
         *
         * Ten kod jest DUŻO dłuższy, powtarzalny i podatny na błędy:
         * łatwo przez przypadek pominąć zamknięcie jednego z zasobów
         * (zwłaszcza gdy dochodzi więcej logiki biznesowej pomiędzy),
         * a jeśli sam wyjątek rzuci się np. przy tworzeniu Connection,
         * trzeba bardzo uważać na warunki `!= null`. Try-with-resources
         * (jak w przykładzie wyżej) załatwia to WSZYSTKO automatycznie,
         * poprawnie i w jednej linijce na zasób.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - W JDBC prawie zawsze zamykamy RAZEM trzy zasoby: Connection,
         *   Statement/PreparedStatement i ResultSet - wszystkie
         *   implementują AutoCloseable.
         * - Try-with-resources pozwala zadeklarować je wszystkie w jednym
         *   bloku `try (...)`, oddzielone średnikiem.
         * - Kolejność zamykania jest ODWROTNA do kolejności otwierania:
         *   ResultSet -> Statement/PreparedStatement -> Connection.
         * - Niezamknięte Connection to w realnej aplikacji (z connection
         *   poolem) wyciek zasobu, który może "zagłodzić" pulę połączeń.
         * - Ręczne zamykanie zasobów w bloku `finally` (bez
         *   try-with-resources) jest dużo bardziej rozwlekłe i podatne na
         *   błędy - łatwo pominąć zamknięcie jednego z zasobów.
         */
    }
}
