package com.example.javaquest._10_dao.Lesson24_DynamicFiltering;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class _Lesson24_DynamicFiltering {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 PO CO DYNAMICZNE FILTROWANIE?
         * ============================================================
         * Typowa wyszukiwarka w aplikacji (np. lista zamówień w panelu
         * administracyjnym) ma WIELE kryteriów, z których użytkownik
         * wypełnia TYLKO NIEKTÓRE:
         *
         *   status:      "PAID" (albo puste - "dowolny status")
         *   data od:     2024-01-01 (albo puste - "bez dolnego limitu")
         *   cena od/do:  100.00 - 500.00 (albo puste)
         *
         * Zapytanie SQL musi więc zawierać w klauzuli WHERE TYLKO te
         * warunki, dla których użytkownik faktycznie podał wartość -
         * struktura zapytania zmienia się w zależności od tego, co
         * przyszło w wywołaniu.
         */

        String url = "jdbc:h2:mem:daolesson24;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);
            OrderDao orderDao = new OrderDao(connection);

            /*
             * ============================================================
             * 🔹 NAIWNE PODEJŚCIA (i dlaczego odrzucamy je)
             * ============================================================
             * A) Osobna metoda/zapytanie SQL dla KAŻDEJ kombinacji filtrów
             *    - przy 4 opcjonalnych kryteriach to 2^4 = 16 kombinacji
             *      zapytań do napisania i utrzymania. Nie skaluje się.
             *
             * B) Jedno "uniwersalne" zapytanie z warunkami typu:
             *      WHERE (? IS NULL OR status = ?)
             *        AND (? IS NULL OR order_date >= ?)
             *    Działa, ale ma wadę: baza zawsze "widzi" WSZYSTKIE
             *    warunki (nawet te nieaktywne), co często przeszkadza
             *    optymalizatorowi zapytań w dobraniu właściwego indeksu.
             *
             * ✅ PODEJŚCIE Z TEJ LEKCJI: budujemy WHERE DYNAMICZNIE w Javie
             *    - do zapytania trafiają TYLKO te warunki, które są
             *      faktycznie potrzebne. Struktura SQL "rośnie" w
             *      zależności od tego, które parametry są różne od null.
             */

            /*
             * ============================================================
             * 🔍 KLUCZOWA ZASADA BEZPIECZEŃSTWA
             * ============================================================
             * Zapytanie jest dynamiczne TYLKO co do STRUKTURY (czy dana
             * klauzula AND w ogóle się pojawia). Nazwy kolumn w tej
             * lekcji są ZAWSZE STAŁE, wpisane wprost w kodzie (status,
             * order_date, amount) - nie pochodzą od użytkownika, więc nie
             * ma tu problemu z Lesson23_DynamicSorting (biała lista dla
             * nazw kolumn).
             *
             * WARTOŚCI natomiast (status, daty, kwoty) pochodzą OD
             * UŻYTKOWNIKA i ZAWSZE trafiają do zapytania przez `?` +
             * PreparedStatement - NIGDY przez konkatenację. Budujemy
             * jednocześnie dwie równoległe struktury:
             *   - StringBuilder z tekstem SQL (placeholdery `?`)
             *   - List<Object> z wartościami, w TEJ SAMEJ kolejności,
             *     w jakiej pojawiają się placeholdery
             */

            System.out.println("=== Brak filtrow (wszystkie parametry null) - zwraca WSZYSTKIE zamowienia ===");
            print(orderDao.search(null, null, null, null));

            System.out.println("\n=== Filtr: tylko status = 'PAID' ===");
            print(orderDao.search("PAID", null, null, null));

            System.out.println("\n=== Filtr: status = 'NEW' + data od 2024-02-01 ===");
            print(orderDao.search("NEW", LocalDate.of(2024, 2, 1), null, null));

            System.out.println("\n=== Filtr: tylko zakres kwoty 100.00 - 300.00 ===");
            print(orderDao.search(null, null, new BigDecimal("100.00"), new BigDecimal("300.00")));

            System.out.println("\n=== Filtr: WSZYSTKIE kryteria naraz ===");
            print(orderDao.search("PAID", LocalDate.of(2024, 1, 1), new BigDecimal("50.00"), new BigDecimal("500.00")));

            System.out.println("\n=== Proba SQL Injection PRZEZ WARTOSC filtra 'status' ===");
            String maliciousStatus = "PAID' OR '1'='1";
            var attack = orderDao.search(maliciousStatus, null, null, null);
            System.out.println("Wstrzykniety 'status': " + maliciousStatus);
            System.out.println("Liczba wynikow: " + attack.size() + " (oczekiwano: 0, bo PreparedStatement");
            System.out.println("traktuje cala wartosc jako zwykly, doslowny tekst parametru)");
            print(attack);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Dynamiczne filtrowanie = zapytanie z wieloma OPCJONALNYMI
         *   kryteriami, gdzie każde `null` oznacza "nie filtruj po tym".
         * - Klauzula WHERE budowana jest DYNAMICZNIE: dodajemy fragment
         *   `AND kolumna = ?` TYLKO gdy odpowiedni parametr wywołania
         *   jest różny od null.
         * - Budujemy RÓWNOLEGLE: StringBuilder (tekst SQL z `?`) i
         *   List<Object> (wartości parametrów) - w identycznej kolejności.
         * - Nazwy kolumn są tu stałe (wpisane w kodzie) - dynamiczna jest
         *   tylko STRUKTURA (które warunki się pojawiają). Gdyby trzeba
         *   było też dynamicznie wybierać KOLUMNĘ filtra od użytkownika,
         *   obowiązywałaby biała lista z Lesson23_DynamicSorting.
         * - WARTOŚCI filtrów zawsze wiążemy przez `?` (setObject/setString/
         *   setBigDecimal...) - nigdy przez konkatenację - dlatego atak
         *   SQL Injection przez wartość filtra (np. status) nie działa,
         *   dokładnie tak jak w Lesson14_SqlInjection (_09_jdbc).
         */
    }

    private static void print(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("  (brak wynikow)");
        }
        orders.forEach(o -> System.out.println("  #" + o.id() + " status=" + o.status()
                + " data=" + o.orderDate() + " kwota=" + o.amount()));
    }

    private record Order(long id, String status, LocalDate orderDate, BigDecimal amount) {
    }

    /** DAO zamowien z dynamicznym budowaniem WHERE na podstawie opcjonalnych filtrow. */
    private static class OrderDao {

        private final Connection connection;

        OrderDao(Connection connection) {
            this.connection = connection;
        }

        /**
         * Wyszukuje zamowienia spelniajace WSZYSTKIE podane (niepuste)
         * kryteria. Kazdy parametr moze byc null - oznacza to "nie
         * filtruj po tym kryterium".
         */
        List<Order> search(String status, LocalDate fromDate, BigDecimal minAmount, BigDecimal maxAmount) throws SQLException {
            StringBuilder sql = new StringBuilder("SELECT id, status, order_date, amount FROM orders WHERE 1 = 1");
            List<Object> params = new ArrayList<>();

            if (status != null) {
                sql.append(" AND status = ?");
                params.add(status);
            }
            if (fromDate != null) {
                sql.append(" AND order_date >= ?");
                params.add(Date.valueOf(fromDate));
            }
            if (minAmount != null) {
                sql.append(" AND amount >= ?");
                params.add(minAmount);
            }
            if (maxAmount != null) {
                sql.append(" AND amount <= ?");
                params.add(maxAmount);
            }
            sql.append(" ORDER BY id");

            List<Order> result = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        result.add(new Order(
                                rs.getLong("id"),
                                rs.getString("status"),
                                rs.getDate("order_date").toLocalDate(),
                                rs.getBigDecimal("amount")
                        ));
                    }
                }
            }
            return result;
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        status VARCHAR(20) NOT NULL,
                        order_date DATE NOT NULL,
                        amount DECIMAL(10, 2) NOT NULL
                    )
                    """);
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('NEW', '2024-01-10', 80.00)");
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('PAID', '2024-01-15', 250.00)");
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('PAID', '2024-02-05', 120.00)");
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('NEW', '2024-02-20', 450.00)");
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('CANCELLED', '2024-03-01', 60.00)");
            stmt.executeUpdate("INSERT INTO orders (status, order_date, amount) VALUES ('PAID', '2024-03-12', 310.00)");
        }
    }
}
