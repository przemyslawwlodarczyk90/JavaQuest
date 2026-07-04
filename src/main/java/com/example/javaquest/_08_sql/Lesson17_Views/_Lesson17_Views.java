package com.example.javaquest._08_sql.Lesson17_Views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson17_Views {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST WIDOK (VIEW)?
         * ============================================================
         * VIEW to "ZAPISANE ZAPYTANIE" – zapytanie SQL, któremu
         * nadajemy nazwę i które od tej pory możemy odpytywać TAK, JAK
         * ZWYKŁĄ TABELĘ (SELECT * FROM nazwa_widoku). Widok NIE
         * przechowuje własnych danych (w standardowym, "zwykłym"
         * widoku) – za każdym razem, gdy go odpytujemy, baza na nowo
         * wykonuje zapytanie, które jest jego definicją.
         *
         * 🔹 PO CO TWORZYĆ WIDOK?
         * - UPRASZCZA złożone zapytania (np. wielokrotny JOIN kilku
         *   tabel) – piszemy skomplikowane zapytanie RAZ, przy tworzeniu
         *   widoku, a potem używamy go jak prostej tabeli
         * - UKRYWA szczegóły struktury bazy przed użytkownikiem widoku
         *   (np. przed inną aplikacją, innym zespołem) – widzą tylko
         *   wynik, nie muszą znać wszystkich tabel źródłowych
         * - Może ograniczać dostęp do wybranych kolumn/wierszy
         *   (np. widok bez kolumny z pensją pracownika)
         * - Pozwala na SPÓJNOŚĆ – wiele miejsc w kodzie używa tej samej,
         *   raz zdefiniowanej logiki zapytania
         *
         * 🔍 OGRANICZENIA WIDOKÓW
         * - Zwykły widok (nie: MATERIALIZED VIEW) NIE przyspiesza
         *   zapytań – to wciąż to samo zapytanie wykonywane od nowa,
         *   tylko pod wygodną nazwą
         * - Aktualizowanie danych PRZEZ widok (UPDATE/INSERT na widoku)
         *   działa tylko dla PROSTYCH widoków (bez JOIN, GROUP BY,
         *   DISTINCT) – nasz widok z JOIN-em poniżej jest wyłącznie
         *   do ODCZYTU
         * - Widoku nie da się bezpośrednio zaindeksować (to tabela pod
         *   spodem ma indeksy – patrz Lesson18_Indexes)
         * - Niektóre bazy oferują dodatkowo MATERIALIZED VIEW – widok,
         *   który FIZYCZNIE przechowuje wynik zapytania i trzeba go
         *   ręcznie/okresowo odświeżać (szybszy odczyt, kosztem
         *   nieaktualności danych) – to jednak temat spoza tej lekcji
         */

        String url = "jdbc:h2:mem:lesson17;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE departments (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                    """);
            stmt.execute("""
                    CREATE TABLE employees (
                        id INT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        department_id INT REFERENCES departments(id),
                        salary INT NOT NULL
                    )
                    """);

            stmt.execute("INSERT INTO departments VALUES (1, 'IT')");
            stmt.execute("INSERT INTO departments VALUES (2, 'Ksiegowosc')");

            stmt.execute("INSERT INTO employees VALUES (1, 'Jan Kowalski', 1, 9000)");
            stmt.execute("INSERT INTO employees VALUES (2, 'Anna Nowak', 1, 8500)");
            stmt.execute("INSERT INTO employees VALUES (3, 'Piotr Wisniewski', 2, 7000)");

            /*
             * ============================================================
             * 🔹 TWORZENIE WIDOKU
             * ============================================================
             * Widok laczacy pracownikow z nazwami ich dzialow - bez
             * niego kazdy, kto chcialby te dane, musialby sam pisac
             * JOIN. Dzieki widokowi robimy to RAZ.
             */

            System.out.println("=== CREATE VIEW employee_overview ===");
            stmt.execute("""
                    CREATE VIEW employee_overview AS
                    SELECT e.id, e.name AS employee_name, d.name AS department_name, e.salary
                    FROM employees e
                    JOIN departments d ON d.id = e.department_id
                    """);
            System.out.println("Widok utworzony.");

            /*
             * ============================================================
             * 🔹 UŻYWANIE WIDOKU JAK ZWYKŁEJ TABELI
             * ============================================================
             * Od tej pory piszemy SELECT ... FROM employee_overview
             * DOKŁADNIE tak samo, jakby to była prawdziwa tabela - baza
             * "pod spodem" wykona zapisany JOIN.
             */

            System.out.println("\n=== SELECT * FROM employee_overview ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM employee_overview ORDER BY employee_name")) {
                while (rs.next()) {
                    System.out.println(rs.getString("employee_name") + " | "
                            + rs.getString("department_name") + " | "
                            + rs.getInt("salary") + " zl");
                }
            }

            // Mozemy tez filtrowac i sortowac widok jak zwykla tabele:
            System.out.println("\n=== Widok + WHERE (tylko dzial IT) ===");
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT employee_name, salary
                    FROM employee_overview
                    WHERE department_name = 'IT'
                    ORDER BY salary DESC
                    """)) {
                while (rs.next()) {
                    System.out.println(rs.getString("employee_name") + " -> " + rs.getInt("salary") + " zl");
                }
            }

            // Nowy pracownik w tabeli zrodlowej od razu "pojawia sie" w
            // widoku - widok nie przechowuje wlasnej kopii danych.
            stmt.execute("INSERT INTO employees VALUES (4, 'Katarzyna Zielinska', 2, 7500)");
            System.out.println("\n=== Widok po dodaniu nowego pracownika (bez zadnej zmiany w widoku!) ===");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM employee_overview ORDER BY employee_name")) {
                while (rs.next()) {
                    System.out.println(rs.getString("employee_name") + " | " + rs.getString("department_name"));
                }
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - VIEW = nazwane, zapisane zapytanie SQL - odpytujemy je jak
         *   zwykłą tabelę (SELECT * FROM nazwa_widoku)
         * - Widok NIE przechowuje własnych danych - każde odpytanie na
         *   nowo wykonuje zapytanie z jego definicji
         * - Upraszcza złożone zapytania (JOIN wielu tabel) i ukrywa
         *   szczegóły struktury bazy przed odbiorcą widoku
         * - Zwykły widok nie przyspiesza zapytań (to nadal to samo
         *   zapytanie) i zwykle jest tylko do ODCZYTU, gdy zawiera JOIN,
         *   GROUP BY czy DISTINCT
         * - MATERIALIZED VIEW (inny temat, nie w tej lekcji) fizycznie
         *   przechowuje wynik i wymaga odświeżania, ale za to jest
         *   szybszy w odczycie
         */
    }
}
