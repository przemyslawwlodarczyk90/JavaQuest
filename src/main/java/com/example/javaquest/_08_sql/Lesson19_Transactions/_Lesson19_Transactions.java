package com.example.javaquest._08_sql.Lesson19_Transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class _Lesson19_Transactions {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST TRANSAKCJA?
         * ============================================================
         * TRANSAKCJA to grupa jednej lub więcej operacji SQL (INSERT,
         * UPDATE, DELETE), która musi zostać wykonana W CAŁOŚCI albo
         * WCALE - nie ma stanu pośredniego. Klasyczny przykład to
         * PRZELEW PIENIĘDZY: odjęcie kwoty z konta A i dodanie jej do
         * konta B MUSZĄ się udać OBA razem - gdyby po odjęciu z konta A
         * nastąpiła awaria przed dodaniem do konta B, pieniądze by
         * "zniknęły". Transakcja gwarantuje, że tak się nie stanie.
         *
         * 🔍 ACID - 4 własności transakcji
         * - ATOMICITY (atomowość) - transakcja wykonuje się w całości
         *   albo wcale, jak pojedyncza, niepodzielna operacja.
         * - CONSISTENCY (spójność) - transakcja przenosi bazę z jednego
         *   POPRAWNEGO stanu w drugi POPRAWNY stan (nie łamie reguł
         *   integralności, ograniczeń itd.).
         * - ISOLATION (izolacja) - transakcje wykonywane równolegle nie
         *   powinny "widzieć" swoich niedokończonych zmian nawzajem
         *   (więcej o tym w Lesson20_TransactionIsolationLevels).
         * - DURABILITY (trwałość) - po zatwierdzeniu (COMMIT) zmiany są
         *   trwałe, nawet w razie awarii zasilania czy restartu bazy.
         *
         * 🔹 KOMENDY STERUJĄCE TRANSAKCJĄ
         * - COMMIT   - zatwierdza wszystkie zmiany w transakcji na stałe
         * - ROLLBACK - cofa WSZYSTKIE zmiany od początku transakcji
         * - SAVEPOINT - "punkt kontrolny" w środku transakcji, do
         *   którego można się cofnąć ROLLBACKiem BEZ cofania całej
         *   transakcji
         */

        String url = "jdbc:h2:mem:lesson19;DB_CLOSE_DELAY=-1";

        // Connection i Statement poznamy szczegółowo w rozdziale o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE accounts (
                        id INT PRIMARY KEY,
                        owner VARCHAR(100) NOT NULL,
                        balance INT NOT NULL CHECK (balance >= 0)
                    )
                    """);
            stmt.execute("INSERT INTO accounts VALUES (1, 'Konto A', 1000)");
            stmt.execute("INSERT INTO accounts VALUES (2, 'Konto B', 500)");

            printBalances(stmt, "STAN POCZATKOWY");

            /*
             * ============================================================
             * 🔹 PRZYPADEK 1: UDANY PRZELEW (COMMIT)
             * ============================================================
             * conn.setAutoCommit(false) wyłącza domyślny tryb, w którym
             * KAŻDE pojedyncze zapytanie jest osobną, natychmiast
             * zatwierdzaną transakcją. Od teraz sami decydujemy, kiedy
             * zatwierdzić (commit) grupę zapytań.
             */

            System.out.println("\n=== PRZELEW 200 zl: Konto A -> Konto B (sukces, COMMIT) ===");
            conn.setAutoCommit(false);
            try {
                stmt.executeUpdate("UPDATE accounts SET balance = balance - 200 WHERE id = 1");
                stmt.executeUpdate("UPDATE accounts SET balance = balance + 200 WHERE id = 2");
                conn.commit();
                System.out.println("Przelew zatwierdzony (COMMIT).");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Blad - przelew wycofany: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

            printBalances(stmt, "PO UDANYM PRZELEWIE");

            /*
             * ============================================================
             * 🔹 PRZYPADEK 2: NIEUDANY PRZELEW (ROLLBACK)
             * ============================================================
             * Próbujemy przelać więcej niż jest na koncie A (2000 zł,
             * a konto A ma tylko 800 zł po poprzednim przelewie).
             * Ograniczenie CHECK (balance >= 0) sprawi, że drugi UPDATE
             * (odjęcie od konta A) rzuci wyjątkiem - wtedy wołamy
             * conn.rollback(), żeby cofnąć WSZYSTKIE zmiany z tej
             * transakcji (łącznie z ewentualnym już wykonanym dodaniem
             * do konta B) i wrócić do stanu SPRZED przelewu.
             */

            System.out.println("\n=== PRZELEW 2000 zl: Konto A -> Konto B (blad, ROLLBACK) ===");
            conn.setAutoCommit(false);
            try {
                stmt.executeUpdate("UPDATE accounts SET balance = balance + 2000 WHERE id = 2");
                stmt.executeUpdate("UPDATE accounts SET balance = balance - 2000 WHERE id = 1"); // to zejdzie ponizej 0 -> CHECK rzuci wyjatek
                conn.commit();
                System.out.println("Przelew zatwierdzony (nie powinno tu dojsc).");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Blad wykryty (" + e.getMessage() + ") - transakcja wycofana (ROLLBACK).");
            } finally {
                conn.setAutoCommit(true);
            }

            printBalances(stmt, "PO NIEUDANYM PRZELEWIE (powinno byc jak PRZED)");
            // Salda MUSZA byc takie same jak w sekcji "PO UDANYM PRZELEWIE"
            // powyzej - ROLLBACK w calosci cofnal skutki nieudanej proby,
            // wlacznie z tym, ze konto B NIE dostalo dodanych 2000 zl.

            /*
             * ============================================================
             * 🔹 SAVEPOINT - czesciowe wycofanie
             * ============================================================
             * SAVEPOINT pozwala cofnac sie tylko do WYBRANEGO punktu w
             * transakcji, a nie do samego jej poczatku. Przydatne, gdy
             * transakcja sklada sie z kilku niezaleznych krokow i tylko
             * jeden z nich moze zawiesc.
             */

            System.out.println("\n=== SAVEPOINT: czesciowe wycofanie w ramach jednej transakcji ===");
            conn.setAutoCommit(false);
            try {
                stmt.executeUpdate("UPDATE accounts SET balance = balance - 100 WHERE id = 1");
                Savepoint afterFirstStep = conn.setSavepoint("po_odjeciu_od_A");
                System.out.println("Krok 1 wykonany (odjeto 100 od Konta A), ustawiono SAVEPOINT.");

                try {
                    // Symulujemy blad w drugim kroku - probujemy przelac
                    // kwote, ktora zejdzie ponizej zera na koncie docelowym
                    // (tu celowo wywolujemy blad recznie, by pokazac SAVEPOINT)
                    stmt.executeUpdate("UPDATE accounts SET balance = balance - 999999 WHERE id = 2");
                } catch (SQLException e) {
                    System.out.println("Krok 2 nie powiodl sie - cofamy TYLKO do SAVEPOINT (nie do poczatku!).");
                    conn.rollback(afterFirstStep);
                }

                conn.commit(); // zatwierdzamy to, co zostalo po cofnieciu do savepointu (czyli krok 1)
                System.out.println("Transakcja zatwierdzona - krok 1 zostal, krok 2 zostal wycofany.");
            } finally {
                conn.setAutoCommit(true);
            }

            printBalances(stmt, "PO TRANSAKCJI Z SAVEPOINT (Konto A powinno miec o 100 mniej)");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Transakcja = grupa operacji SQL wykonywana w całości albo
         *   wcale (klasyczny przykład: przelew między kontami)
         * - ACID: Atomicity (całość albo nic), Consistency (baza
         *   zawsze w poprawnym stanie), Isolation (transakcje nie
         *   widzą nawzajem swoich niedokończonych zmian), Durability
         *   (po COMMIT zmiany są trwałe)
         * - conn.setAutoCommit(false) - wyłącza domyślne automatyczne
         *   zatwierdzanie każdego pojedynczego zapytania
         * - conn.commit() - zatwierdza wszystkie zmiany transakcji
         * - conn.rollback() - cofa WSZYSTKIE zmiany transakcji do stanu
         *   sprzed jej rozpoczęcia
         * - conn.setSavepoint() + conn.rollback(savepoint) - pozwala
         *   cofnąć się tylko do WYBRANEGO punktu w środku transakcji,
         *   zamiast do samego jej początku
         */
    }

    private static void printBalances(Statement stmt, String label) throws SQLException {
        System.out.println("--- " + label + " ---");
        try (ResultSet rs = stmt.executeQuery("SELECT owner, balance FROM accounts ORDER BY id")) {
            while (rs.next()) {
                System.out.println(rs.getString("owner") + ": " + rs.getInt("balance") + " zl");
            }
        }
    }
}
