package com.example.javaquest._09_jdbc.Lesson05_PreparedStatement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class _Lesson05_PreparedStatement {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST PreparedStatement?
         * ============================================================
         * `java.sql.PreparedStatement` to "przygotowane" (skompilowane
         * po stronie bazy danych) zapytanie SQL z MIEJSCAMI NA PARAMETRY,
         * oznaczonymi znakiem zapytania `?`. Zamiast sklejać wartości
         * bezpośrednio w tekście SQL (jak w Statement), PODAJEMY je
         * osobno, przez dedykowane metody typu setXxx(indeks, wartość).
         *
         * Tworzymy je z Connection, podając SQL z placeholderami:
         *
         *     PreparedStatement ps = connection.prepareStatement(
         *         "INSERT INTO users (name, age) VALUES (?, ?)");
         *     ps.setString(1, "Jan");   // parametr nr 1 (indeksy OD 1, nie od 0!)
         *     ps.setInt(2, 30);         // parametr nr 2
         *     ps.executeUpdate();
         *
         * 🔍 DLACZEGO TO CHRONI PRZED SQL INJECTION?
         * Kluczowa różnica względem Statement: driver bazy danych
         * traktuje wartości podane przez setXxx() WYŁĄCZNIE jako DANE
         * (surowe wartości), NIGDY jako fragment składni SQL. Nawet jeśli
         * użytkownik wpisze coś w stylu: Jan' OR '1'='1 - zostanie to
         * potraktowane jako dosłowny, jeden ciąg znaków do wyszukania czy
         * wstawienia, a NIE jako część logiki zapytania. To eliminuje
         * SQL Injection u samych podstaw (pełny temat w Lesson14).
         */

        String url = "jdbc:h2:mem:jdbclesson05;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            try (var ddl = connection.createStatement()) {
                ddl.execute("""
                        CREATE TABLE employees (
                            id INT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            salary DECIMAL(10,2) NOT NULL,
                            birth_date DATE,
                            hired_at TIMESTAMP
                        )
                        """);
            }

            /*
             * ============================================================
             * 🔹 SETTERY DLA RÓŻNYCH TYPÓW
             * ============================================================
             * PreparedStatement ma dedykowaną metodę set... dla każdego
             * typu danych - to eliminuje ręczne formatowanie tekstu SQL
             * (np. cudzysłowy wokół Stringów czy formatowanie dat).
             */

            String insertSql = "INSERT INTO employees (id, name, salary, birth_date, hired_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

                ps.setInt(1, 1);
                ps.setString(2, "Anna Kowalska");
                ps.setBigDecimal(3, new BigDecimal("8500.00"));
                // java.time.LocalDate -> java.sql.Date przez Date.valueOf(...)
                LocalDate birthDate = LocalDate.of(1990, 5, 12);
                ps.setDate(4, Date.valueOf(birthDate));
                // java.time.LocalDateTime -> java.sql.Timestamp przez Timestamp.valueOf(...)
                LocalDateTime hiredAt = LocalDateTime.of(2022, 3, 1, 9, 0);
                ps.setTimestamp(5, Timestamp.valueOf(hiredAt));

                int rows = ps.executeUpdate();
                System.out.println("=== INSERT #1 przez PreparedStatement ===");
                System.out.println("Wstawiono wierszy: " + rows);
            }

            // Drugi wiersz - ten sam PreparedStatement obiekt MOŻNA by
            // ponownie użyć (zmieniając tylko parametry), tu dla
            // przejrzystości tworzymy nowy blok try-with-resources
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, 2);
                ps.setString(2, "Piotr Nowak");
                ps.setBigDecimal(3, new BigDecimal("7200.50"));
                ps.setDate(4, Date.valueOf(LocalDate.of(1988, 11, 23)));
                // setObject() - alternatywa dla setTimestamp/setDate, przydatna
                // gdy typ nie jest znany w danym miejscu na etapie kompilacji
                ps.setObject(5, Timestamp.valueOf(LocalDateTime.of(2023, 6, 15, 8, 30)), Types.TIMESTAMP);

                int rows = ps.executeUpdate();
                System.out.println("\n=== INSERT #2 przez PreparedStatement (z setObject) ===");
                System.out.println("Wstawiono wierszy: " + rows);
            }

            /*
             * ============================================================
             * 🔹 PARAMETRY W WHERE - odczyt z zabezpieczeniem
             * ============================================================
             */

            System.out.println("\n=== SELECT z parametrem (bezpieczne wyszukiwanie po nazwisku) ===");
            String selectSql = "SELECT id, name, salary, birth_date, hired_at FROM employees WHERE name = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setString(1, "Anna Kowalska");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + ": " + rs.getString("name")
                                + ", pensja: " + rs.getBigDecimal("salary")
                                + ", data urodzenia: " + rs.getDate("birth_date")
                                + ", zatrudniony: " + rs.getTimestamp("hired_at"));
                    }
                }
            }

            // Gdyby ktoś "wstrzyknął" wartość: Anna Kowalska' OR '1'='1
            // parametr byłby traktowany dosłownie jako jeden ciąg znaków
            // do wyszukania - zapytanie po prostu nic by nie znalazło,
            // zamiast zwrócić WSZYSTKICH pracowników.
            System.out.println("\n=== SELECT z probą 'wstrzykniecia' (bezpiecznie potraktowane jako zwykly tekst) ===");
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setString(1, "Anna Kowalska' OR '1'='1");
                try (ResultSet rs = ps.executeQuery()) {
                    int found = 0;
                    while (rs.next()) {
                        found++;
                    }
                    System.out.println("Znalezionych pracownikow: " + found + " (0 - parametr potraktowany jako doslowny tekst, nie SQL)");
                }
            }
        }

        System.out.println("\n=== KONIEC LEKCJI 5 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PreparedStatement = "przygotowane" zapytanie SQL z
         *   placeholderami `?`, tworzone przez connection.prepareStatement(sql)
         * - Wartości podajemy przez dedykowane settery: setString(),
         *   setInt(), setLong(), setBigDecimal(), setDate(), setTimestamp(),
         *   albo uniwersalne setObject(indeks, wartosc, typSQL)
         * - Indeksy parametrów zaczynają się OD 1, nie od 0
         * - java.time.LocalDate -> java.sql.Date przez Date.valueOf(...)
         * - java.time.LocalDateTime -> java.sql.Timestamp przez Timestamp.valueOf(...)
         * - Driver traktuje wartości z setterów WYŁĄCZNIE jako DANE,
         *   NIGDY jako fragment składni SQL - to eliminuje SQL Injection
         * - PreparedStatement jest też CZYTELNIEJszy niż ręczna
         *   konkatenacja Stringów ze Statement
         * - PreparedStatement powinien być GŁÓWNYM sposobem wykonywania
         *   zapytań w JDBC - Statement rezerwujemy dla prostego SQL bez
         *   żadnych zmiennych wartości (np. czyste DDL)
         */
    }
}
