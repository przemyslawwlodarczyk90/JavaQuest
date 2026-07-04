package com.example.javaquest._09_jdbc.Lesson15_JdbcTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson15_JdbcTransactions {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 TRANSAKCJE W JDBC - PERSPEKTYWA JAVY, NIE SQL
         * ============================================================
         * W rozdziale _08_sql (Lesson19_Transactions) poznałeś transakcje
         * od strony BAZY DANYCH: czym jest ACID, po co COMMIT/ROLLBACK,
         * czym jest SAVEPOINT. Tu zakładamy, że to już wiesz - tym razem
         * interesuje nas WYŁĄCZNIE strona JAVOWA: jak POPRAWNIE napisać
         * kod Java sterujący transakcją, żeby:
         * - żaden wyjątek nie "przeciekł" bez wycofania zmian,
         * - połączenie zawsze wróciło do spójnego stanu (autoCommit),
         * - kod dało się bezpiecznie wywołać wielokrotnie, także na
         *   połączeniu POCHODZĄCYM Z PULI (connection pool) - o pulach
         *   połączeń powiemy więcej w rozdziale o DAO, ale już teraz
         *   warto pisać kod tak, jakby Connection miało żyć dłużej niż
         *   jedna metoda i być oddawane z powrotem do puli.
         *
         * 🔹 PODSTAWOWE API (Connection)
         * - connection.setAutoCommit(false) - wyłącza domyślny tryb,
         *   w którym KAŻDE pojedyncze zapytanie jest osobną, od razu
         *   zatwierdzaną transakcją
         * - connection.commit()   - zatwierdza zmiany od ostatniego commit/rollback
         * - connection.rollback() - cofa zmiany od ostatniego commit/rollback
         * - connection.getAutoCommit() - odczyt bieżącego trybu (przyda się
         *   do PRZYWRÓCENIA oryginalnego stanu, patrz niżej)
         */

        String url = "jdbc:h2:mem:jdbclesson15;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);
            printBalances(connection, "STAN POCZATKOWY");

            /*
             * ============================================================
             * 🔹 PEŁNY WZORZEC: try/catch/finally wokół transakcji
             * ============================================================
             * Poprawny szkielet transakcji w Javie wygląda tak:
             *
             *   connection.setAutoCommit(false);
             *   try {
             *       // operacja 1
             *       // operacja 2
             *       connection.commit();
             *   } catch (SQLException e) {
             *       connection.rollback();   // cofamy WSZYSTKO
             *       throw e;                 // albo obsługujemy inaczej
             *   } finally {
             *       connection.setAutoCommit(true); // ZAWSZE przywracamy!
             *   }
             *
             * ⚠️ DLACZEGO "finally" JEST TU KLUCZOWE?
             * Jeśli zapomnisz przywrócić autoCommit, a Connection trafi
             * z powrotem do puli połączeń (albo po prostu zostanie użyte
             * gdzie indziej w aplikacji), KOLEJNY kawałek kodu, który
             * spodziewa się domyślnego autoCommit=true, zacznie po cichu
             * działać w "wiszącej" transakcji - zmiany przestaną się
             * zapisywać od razu, dopóki ktoś nie zawoła commit() ręcznie.
             * To bardzo trudny do wytropienia błąd, bo objawia się daleko
             * od miejsca, w którym powstał.
             *
             * Poniżej metoda transferMoney() pokazuje ten wzorzec w akcji,
             * z dodatkowym detalem: sam rollback() też może rzucić
             * SQLException (np. gdy połączenie zdążyło już paść) - dlatego
             * profesjonalny kod obsługuje TĘ awarię osobno, zamiast
             * pozwolić jej "zjeść" oryginalny wyjątek.
             */

            System.out.println("\n=== PRZELEW 200 zl: Konto A -> Konto B (sukces) ===");
            try {
                transferMoney(connection, 1, 2, 200);
                System.out.println("Przelew zakonczony sukcesem (COMMIT).");
            } catch (SQLException e) {
                System.out.println("Przelew nieudany: " + e.getMessage());
            }
            printBalances(connection, "PO UDANYM PRZELEWIE");

            System.out.println("\n=== PRZELEW 5000 zl: Konto A -> Konto B (blad, ROLLBACK) ===");
            try {
                transferMoney(connection, 1, 2, 5000); // wiecej niz jest na koncie A
                System.out.println("Przelew zakonczony sukcesem (nie powinno tu dojsc).");
            } catch (SQLException e) {
                System.out.println("Przelew nieudany, zmiany wycofane: " + e.getMessage());
            }
            printBalances(connection, "PO NIEUDANYM PRZELEWIE (powinno byc jak PRZED)");
            // Salda MUSZA byc identyczne jak w sekcji "PO UDANYM PRZELEWIE" -
            // rollback() w transferMoney() w calosci cofnal skutki proby,
            // WLACZNIE z ewentualnym czesciowo wykonanym UPDATE na koncie B.

            /*
             * ============================================================
             * 🔍 DRUGI PROBLEM: co jeśli SAM rollback() rzuci wyjątek?
             * ============================================================
             * To rzadka, ale realna sytuacja (np. połączenie z bazą padło
             * w trakcie transakcji). Dobra praktyka: nie "gubić" pierwotnego
             * wyjątku - dołączyć błąd z rollback() jako "suppressed"
             * (Throwable.addSuppressed), żeby log pokazał OBA problemy.
             * Zobaczysz ten wzorzec w runInTransaction() poniżej.
             */

            System.out.println("\n=== WZORZEC WIELOKROTNEGO UZYCIA: runInTransaction() ===");

            Integer nowySaldoA = runInTransaction(connection, conn -> {
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("UPDATE accounts SET balance = balance - 50 WHERE id = 1");
                    st.executeUpdate("UPDATE accounts SET balance = balance + 50 WHERE id = 2");
                }
                try (Statement st = conn.createStatement();
                     ResultSet rs = st.executeQuery("SELECT balance FROM accounts WHERE id = 1")) {
                    rs.next();
                    return rs.getInt("balance");
                }
            });
            System.out.println("Transakcja przez runInTransaction() zakonczona. Nowe saldo konta A: " + nowySaldoA);
            printBalances(connection, "PO TRANSAKCJI PRZEZ runInTransaction()");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ta lekcja to JAVOWA strona transakcji - jak pisać kod, a nie
         *   czym jest ACID (to było w _08_sql/Lesson19_Transactions)
         * - Szkielet: setAutoCommit(false) -> try { operacje; commit(); }
         *   catch (SQLException e) { rollback(); } finally { setAutoCommit(true); }
         * - "finally { setAutoCommit(true); }" jest KRYTYCZNE, zwłaszcza
         *   gdy Connection może wrócić do puli połączeń i być użyte przez
         *   zupełnie inny fragment aplikacji
         * - Sam rollback() też może rzucić SQLException - profesjonalny
         *   kod obsługuje to osobno (np. Throwable.addSuppressed), żeby
         *   nie zgubić informacji o pierwotnym błędzie
         * - Powtarzalny wzorzec transakcji warto wydzielić do jednej
         *   metody pomocniczej (np. generycznej runInTransaction()),
         *   zamiast kopiować ten sam try/catch/finally w każdym miejscu
         */
    }

    /**
     * Klasyczny wzorzec transakcji: begin -> operacja 1 -> operacja 2 -> commit,
     * z obsługą błędu przez rollback i przywróceniem autoCommit w finally.
     */
    private static void transferMoney(Connection connection, int fromId, int toId, int amount) throws SQLException {
        connection.setAutoCommit(false);
        try {
            try (PreparedStatement withdraw = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE id = ?")) {
                withdraw.setInt(1, amount);
                withdraw.setInt(2, fromId);
                withdraw.executeUpdate();
            }
            try (PreparedStatement deposit = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE id = ?")) {
                deposit.setInt(1, amount);
                deposit.setInt(2, toId);
                deposit.executeUpdate();
            }
            connection.commit(); // dopiero tutaj obie zmiany staja sie trwale
        } catch (SQLException e) {
            connection.rollback(); // cofamy WSZYSTKO - takze ewentualny udany "withdraw"
            throw e;
        } finally {
            connection.setAutoCommit(true); // zawsze przywracamy domyslny tryb
        }
    }

    /**
     * Funkcyjny interfejs reprezentujący "operację do wykonania w transakcji".
     * Dzięki niemu nie musimy kopiować tego samego try/catch/finally w każdym
     * miejscu, gdzie potrzebujemy transakcji - wystarczy przekazać lambdę.
     */
    @FunctionalInterface
    private interface TransactionalOperation<T> {
        T execute(Connection connection) throws SQLException;
    }

    /**
     * Generyczny "szablon" transakcji - przyjmuje dowolną operację i sam
     * dba o setAutoCommit/commit/rollback oraz przywrócenie stanu w finally.
     * Zwraca oryginalny stan autoCommit (getAutoCommit()), zamiast na sztywno
     * ustawiać "true" - tak, aby dobrze zachować się także wtedy, gdy
     * połączenie pochodzi z puli i mogło mieć inne ustawienia startowe.
     */
    private static <T> T runInTransaction(Connection connection, TransactionalOperation<T> operation) throws SQLException {
        boolean originalAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            T result = operation.execute(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                e.addSuppressed(rollbackException); // nie gubimy informacji o obu bledach
            }
            throw e;
        } finally {
            try {
                connection.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                // W realnym projekcie: zalogowac (logger.warn(...)). Nie przerywamy
                // dzialania programu z powodu bledu przy samym przywracaniu trybu.
            }
        }
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE accounts (
                        id INT PRIMARY KEY,
                        owner VARCHAR(100) NOT NULL,
                        balance INT NOT NULL CHECK (balance >= 0)
                    )
                    """);
            stmt.execute("INSERT INTO accounts VALUES (1, 'Konto A', 1000)");
            stmt.execute("INSERT INTO accounts VALUES (2, 'Konto B', 500)");
        }
    }

    private static void printBalances(Connection connection, String label) throws SQLException {
        System.out.println("--- " + label + " ---");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT owner, balance FROM accounts ORDER BY id")) {
            while (rs.next()) {
                System.out.println(rs.getString("owner") + ": " + rs.getInt("balance") + " zl");
            }
        }
    }
}
