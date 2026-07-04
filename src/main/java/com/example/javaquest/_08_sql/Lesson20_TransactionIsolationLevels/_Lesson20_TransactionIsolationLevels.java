package com.example.javaquest._08_sql.Lesson20_TransactionIsolationLevels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson20_TransactionIsolationLevels {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 POZIOMY IZOLACJI TRANSAKCJI
         * ============================================================
         * Gdy KILKA transakcji działa na tych samych danych w tym
         * samym czasie, mogą pojawić się nieoczekiwane efekty uboczne.
         * POZIOM IZOLACJI to ustawienie, które mówi bazie, JAK BARDZO
         * transakcje mają być od siebie odizolowane - im wyższy
         * poziom, tym więcej "anomalii" baza eliminuje, ale zwykle
         * kosztem wydajności (więcej blokad/więcej pracy przy
         * utrzymywaniu spójnych migawek danych).
         *
         * SQL definiuje 4 standardowe poziomy (od najsłabszego do
         * najsilniejszego):
         * - READ UNCOMMITTED - najsłabszy, pozwala na wszystkie anomalie
         * - READ COMMITTED   - domyślny w wielu bazach (też w H2)
         * - REPEATABLE READ  - silniejszy, eliminuje "znikające" zmiany
         * - SERIALIZABLE     - najsilniejszy, transakcje zachowują się
         *                      tak, jakby wykonywały się jedna po drugiej
         *
         * 🔍 3 KLASYCZNE ANOMALIE
         * - DIRTY READ (brudny odczyt) - transakcja A widzi zmiany
         *   transakcji B, zanim B zrobiło COMMIT. Jeśli B potem zrobi
         *   ROLLBACK, A "widziało" dane, które nigdy naprawdę nie
         *   istniały.
         * - NON-REPEATABLE READ (odczyt niepowtarzalny) - transakcja A
         *   odczytuje ten sam wiersz DWA razy w trakcie swojej
         *   transakcji i za drugim razem widzi INNĄ wartość, bo
         *   transakcja B zdążyła w międzyczasie zmienić i zatwierdzić
         *   dane.
         * - PHANTOM READ (odczyt widmowy) - transakcja A wykonuje ten
         *   sam warunek WHERE dwa razy i za drugim razem widzi
         *   DODATKOWE (lub brakujące) wiersze, bo transakcja B dodała
         *   (lub usunęła) pasujące wiersze między odczytami.
         *
         * 🔹 JDBC: Connection.setTransactionIsolation(...)
         * Poziom izolacji ustawiamy metodą setTransactionIsolation()
         * ze stałymi Connection.TRANSACTION_*.
         */

        String url = "jdbc:h2:mem:lesson20;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection setupConn = DriverManager.getConnection(url);
             Statement setupStmt = setupConn.createStatement()) {
            setupStmt.execute("CREATE TABLE accounts (id INT PRIMARY KEY, balance INT NOT NULL)");
            setupStmt.execute("INSERT INTO accounts VALUES (1, 1000)");
        }

        /*
         * ============================================================
         * 🔹 UWAGA METODOLOGICZNA
         * ============================================================
         * Poniższe demo NIE używa osobnych wątków - otwieramy po
         * prostu DWIE osobne Connection do TEJ SAMEJ bazy w pamięci
         * i wywołujemy na nich metody NA PRZEMIAN z jednego wątku
         * main. To w zupełności wystarcza, żeby zobaczyć, co widzi
         * "druga transakcja" - z punktu widzenia bazy to i tak dwie
         * niezależne transakcje, niezależnie od tego, czy fizycznie
         * działają na dwóch wątkach, czy na jednym.
         */

        System.out.println("=== 1) DIRTY READ pod READ_UNCOMMITTED ===");
        try (Connection connA = DriverManager.getConnection(url);
             Connection connB = DriverManager.getConnection(url)) {

            connA.setAutoCommit(false);
            connB.setAutoCommit(false);
            connB.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            try (Statement stmtA = connA.createStatement()) {
                stmtA.executeUpdate("UPDATE accounts SET balance = 1 WHERE id = 1");
                System.out.println("Transakcja A: zmienila balance na 1, ALE JESZCZE NIE zrobila COMMIT");
            }

            try (Statement stmtB = connB.createStatement();
                 ResultSet rs = stmtB.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                rs.next();
                System.out.println("Transakcja B (READ_UNCOMMITTED) widzi balance = " + rs.getInt("balance")
                        + " -> to DIRTY READ: widzi niezatwierdzona zmiane A!");
            }

            connA.rollback(); // A sie wycofuje - jej zmiana NIGDY nie zaistniala naprawde
            connB.commit();
            System.out.println("Transakcja A zrobila ROLLBACK - B 'widzialo' dane, ktore ostatecznie nigdy nie powstaly.");
            // ✅ H2 (z domyslnym MVStore) NAPRAWDE honoruje READ_UNCOMMITTED
            // i pozwala zobaczyc dirty read w tym scenariuszu - to nie
            // jest tylko teoria, sprawdzone empirycznie na H2 2.3.232.
        }
        resetBalance(url);

        System.out.println("\n=== 2) NON-REPEATABLE READ pod READ_COMMITTED (domyslny poziom) ===");
        try (Connection connA = DriverManager.getConnection(url);
             Connection connB = DriverManager.getConnection(url)) {

            connA.setAutoCommit(false);
            connA.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // to i tak domyslny poziom w H2
            connB.setAutoCommit(false);

            try (Statement stmtA = connA.createStatement();
                 ResultSet rs = stmtA.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                rs.next();
                System.out.println("Transakcja A, 1. odczyt: balance = " + rs.getInt("balance"));
            }

            try (Statement stmtB = connB.createStatement()) {
                stmtB.executeUpdate("UPDATE accounts SET balance = 777 WHERE id = 1");
                connB.commit();
                System.out.println("Transakcja B zmienila balance na 777 i zrobila COMMIT (w trakcie trwania transakcji A!)");
            }

            try (Statement stmtA = connA.createStatement();
                 ResultSet rs = stmtA.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                rs.next();
                System.out.println("Transakcja A, 2. odczyt (ta sama transakcja!): balance = " + rs.getInt("balance")
                        + " -> to NON-REPEATABLE READ: ten sam SELECT dal INNY wynik!");
            }
            connA.commit();
        }
        resetBalance(url);

        System.out.println("\n=== 3) REPEATABLE_READ eliminuje non-repeatable read ===");
        try (Connection connA = DriverManager.getConnection(url);
             Connection connB = DriverManager.getConnection(url)) {

            connA.setAutoCommit(false);
            connA.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connB.setAutoCommit(false);

            try (Statement stmtA = connA.createStatement();
                 ResultSet rs = stmtA.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                rs.next();
                System.out.println("Transakcja A, 1. odczyt: balance = " + rs.getInt("balance"));
            }

            try (Statement stmtB = connB.createStatement()) {
                stmtB.executeUpdate("UPDATE accounts SET balance = 999 WHERE id = 1");
                connB.commit();
                System.out.println("Transakcja B zmienila balance na 999 i zrobila COMMIT.");
            }

            try (Statement stmtA = connA.createStatement();
                 ResultSet rs = stmtA.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                rs.next();
                System.out.println("Transakcja A, 2. odczyt (ta sama transakcja): balance = " + rs.getInt("balance")
                        + " -> pod REPEATABLE_READ widzi SWOJĄ, spójną migawkę - żadnego non-repeatable read!");
            }
            connA.commit();
        }
        resetBalance(url);

        /*
         * ============================================================
         * 🔍 UCZCIWA UWAGA O H2 A PHANTOM READ / SERIALIZABLE
         * ============================================================
         * W standardzie SQL REPEATABLE READ teoretycznie WCIĄŻ pozwala
         * na phantom read (nowe wiersze pasujące do WHERE mogłyby się
         * "dopisać" przy drugim odczycie), a dopiero SERIALIZABLE ma
         * to eliminować. W PRAKTYCE H2 (silnik MVStore, domyślny od
         * H2 2.x) realizuje REPEATABLE_READ i SERIALIZABLE przez
         * migawkę (snapshot) całej transakcji ustalaną przy pierwszym
         * odczycie - w efekcie u H2 REPEATABLE_READ w prostych testach
         * z SELECT COUNT(*) już zapobiega też phantom readom, więc
         * różnicy między REPEATABLE_READ a SERIALIZABLE nie da się tu
         * pokazać prostym doświadczeniem (potrzebny byłby bardziej
         * złożony scenariusz z tzw. "write skew"). To ISTOTNA różnica
         * względem np. PostgreSQL, gdzie REPEATABLE READ i SERIALIZABLE
         * różnią się właśnie wykrywaniem konfliktów zapisu (write
         * skew) - w H2 granica między nimi jest w praktyce znacznie
         * mniej widoczna. Poniżej i tak pokazujemy, jak API wygląda -
         * ustawienie poziomu jest identyczne niezależnie od silnika bazy.
         */

        System.out.println("\n=== 4) Ustawienie SERIALIZABLE (najsilniejszy poziom) ===");
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            System.out.println("Poziom izolacji ustawiony na SERIALIZABLE (stala = "
                    + Connection.TRANSACTION_SERIALIZABLE + ").");
            System.out.println("Odczytany z powrotem poziom izolacji: " + conn.getTransactionIsolation());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - READ UNCOMMITTED - widać niezatwierdzone zmiany innych
         *   transakcji (dirty read) - najsłabsza izolacja
         * - READ COMMITTED - widać tylko zatwierdzone zmiany, ale dwa
         *   odczyty w TEJ SAMEJ transakcji mogą dać różny wynik
         *   (non-repeatable read) - to domyślny poziom w H2
         * - REPEATABLE READ - transakcja trzyma się jednej, spójnej
         *   migawki danych przez cały swój czas trwania
         * - SERIALIZABLE - najsilniejszy poziom, transakcje zachowują
         *   się tak, jakby wykonywały się sekwencyjnie, jedna po drugiej
         * - conn.setTransactionIsolation(Connection.TRANSACTION_*) -
         *   sposób ustawienia poziomu w JDBC (identyczny dla każdej bazy)
         * - H2 (MVStore) NAPRAWDĘ honoruje READ_UNCOMMITTED (dirty read
         *   jest tu realnie obserwowalny) oraz różnicę READ_COMMITTED
         *   vs REPEATABLE_READ (non-repeatable read) - ale różnicy
         *   REPEATABLE_READ vs SERIALIZABLE (phantom read / write skew)
         *   nie da się w H2 pokazać prostym przykładem tak łatwo, jak
         *   w PostgreSQL czy Oracle
         */
    }

    private static void resetBalance(String url) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("UPDATE accounts SET balance = 1000 WHERE id = 1");
        }
    }
}
