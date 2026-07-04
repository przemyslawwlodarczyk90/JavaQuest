package com.example.javaquest._09_jdbc.Lesson16_BatchProcessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson16_BatchProcessing {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST PRZETWARZANIE WSADOWE (BATCH)?
         * ============================================================
         * Do tej pory każdy INSERT/UPDATE wykonywaliśmy pojedynczo:
         * executeUpdate() wysyła ZAPYTANIE DO BAZY, baza je wykonuje,
         * odsyła wynik - i tak dla KAŻDEGO wiersza z osobna. Przy wielu
         * operacjach (np. import 10 000 rekordów z pliku CSV) oznacza to
         * tysiące osobnych "podróży" do bazy danych (round-trip) - a każda
         * taka podróż ma swój narzut czasowy, niezależnie od tego, jak
         * mała jest sama operacja.
         *
         * BATCH (wsad) to sposób na zebranie wielu poleceń SQL w JEDNĄ
         * paczkę i wysłanie ich do bazy JEDNYM wywołaniem. Baza wykonuje
         * je jedno po drugim po swojej stronie, ale komunikacja Java <->
         * baza odbywa się raz, a nie N razy.
         *
         * 🔹 KLUCZOWE METODY (Statement / PreparedStatement)
         * - addBatch()      - dodaje kolejne polecenie do "paczki"
         *                     (dla PreparedStatement: dodaje aktualne
         *                     wartości parametrów, bez wykonywania zapytania)
         * - executeBatch()  - wysyła CAŁĄ paczkę do bazy i wykonuje ją;
         *                     zwraca int[] - liczbę zmienionych wierszy
         *                     dla KAŻDEGO polecenia w paczce, w kolejności
         * - clearBatch()    - czyści paczkę bez jej wykonywania (np. gdy
         *                     chcemy zrezygnować z zebranych operacji)
         */

        String url = "jdbc:h2:mem:jdbclesson16;DB_CLOSE_DELAY=-1";
        int liczbaRekordow = 5000; // wystarczajaco duzo, by roznica byla widoczna, ale demo konczy sie w sekundy

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);

            /*
             * ============================================================
             * 🔹 WARIANT 1: N POJEDYNCZYCH executeUpdate() (bez batcha)
             * ============================================================
             * Każdy wiersz to osobne wywołanie executeUpdate() - baza
             * dostaje polecenia jedno po drugim, autoCommit domyślnie
             * włączony (każdy INSERT to osobna, natychmiast zatwierdzana
             * mini-transakcja).
             */

            System.out.println("=== WARIANT 1: " + liczbaRekordow + " pojedynczych executeUpdate() ===");
            long startPojedynczo = System.nanoTime();

            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO numbers_single (id, num_value) VALUES (?, ?)")) {
                for (int i = 1; i <= liczbaRekordow; i++) {
                    insert.setInt(1, i);
                    insert.setInt(2, i * i);
                    insert.executeUpdate(); // od razu wysylane do bazy, jeden po drugim
                }
            }

            long czasPojedynczoMs = (System.nanoTime() - startPojedynczo) / 1_000_000;
            System.out.println("Czas wykonania (pojedynczo): " + czasPojedynczoMs + " ms");

            /*
             * ============================================================
             * 🔹 WARIANT 2: BATCH - addBatch() + executeBatch()
             * ============================================================
             * Zamiast wykonywać każdy INSERT od razu, dodajemy go do
             * paczki (addBatch()) i wysyłamy WSZYSTKIE naraz jednym
             * executeBatch(). Dodatkowo wyłączamy autoCommit i zatwierdzamy
             * całość jednym commit() na końcu - to standardowa praktyka
             * przy batchu, bo unika też N osobnych zatwierdzeń transakcji.
             */

            System.out.println("\n=== WARIANT 2: " + liczbaRekordow + " operacji przez addBatch()+executeBatch() ===");
            long startBatch = System.nanoTime();

            connection.setAutoCommit(false);
            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO numbers_batch (id, num_value) VALUES (?, ?)")) {
                for (int i = 1; i <= liczbaRekordow; i++) {
                    insert.setInt(1, i);
                    insert.setInt(2, i * i);
                    insert.addBatch(); // TYLKO dodaje do paczki, nie wysyla jeszcze nic
                }
                int[] wynikiBatcha = insert.executeBatch(); // jedno wywolanie do bazy dla calej paczki
                connection.commit();
                System.out.println("Liczba polecen w paczce: " + wynikiBatcha.length);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

            long czasBatchMs = (System.nanoTime() - startBatch) / 1_000_000;
            System.out.println("Czas wykonania (batch): " + czasBatchMs + " ms");

            /*
             * ============================================================
             * 🔍 PORÓWNANIE WYNIKÓW
             * ============================================================
             * Poniżej wypisujemy ZMIERZONE (nie wymyślone!) czasy obu
             * wariantów. Dokładne wartości zależą od maszyny, obciążenia
             * i tego, że H2 działa w pamięci (co samo w sobie jest już
             * bardzo szybkie) - ale kierunek jest zazwyczaj jasny: mniej
             * "podróży" do bazy = mniejszy narzut.
             */

            System.out.println("\n=== PODSUMOWANIE POMIARU ===");
            System.out.println("Pojedynczo: " + czasPojedynczoMs + " ms");
            System.out.println("Batch:      " + czasBatchMs + " ms");
            if (czasBatchMs < czasPojedynczoMs) {
                System.out.println("-> Batch okazal sie szybszy o " + (czasPojedynczoMs - czasBatchMs) + " ms.");
            } else {
                System.out.println("-> Na tej maszynie batch nie wypadl szybciej w tym pomiarze "
                        + "(np. baza w pamieci moze zacierac roznice) - ale to dalej ZALECANA praktyka "
                        + "przy prawdziwej bazie sieciowej, gdzie narzut komunikacji jest znacznie wiekszy.");
            }

            printCount(connection, "numbers_single");
            printCount(connection, "numbers_batch");

            /*
             * ============================================================
             * 🔹 clearBatch() - rezygnacja z zebranej paczki
             * ============================================================
             * Jeśli w trakcie zbierania paczki zorientujemy się, że coś
             * poszło nie tak (np. błąd walidacji części danych) i NIE
             * chcemy wykonywać żadnej z dotąd dodanych operacji, wołamy
             * clearBatch() - paczka jest czyszczona, executeBatch() nic
             * by już nie wykonał.
             */

            System.out.println("\n=== clearBatch(): rezygnacja z zebranych operacji ===");
            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO numbers_batch (id, num_value) VALUES (?, ?)")) {
                insert.setInt(1, 999_001);
                insert.setInt(2, 1);
                insert.addBatch();
                insert.setInt(1, 999_002);
                insert.setInt(2, 2);
                insert.addBatch();
                System.out.println("Dodano 2 operacje do paczki, po czym wolamy clearBatch()...");
                insert.clearBatch();
                int[] wynik = insert.executeBatch(); // pusta paczka - nic sie nie wykona
                System.out.println("Liczba faktycznie wykonanych polecen po clearBatch(): " + wynik.length);
            }
            printCount(connection, "numbers_batch"); // liczba wierszy bez zmian - potwierdza clearBatch()
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Batch = zebranie wielu poleceń SQL w jedną paczkę i wysłanie
         *   ich do bazy JEDNYM wywołaniem, zamiast osobno dla każdego
         * - addBatch() - dodaje polecenie/parametry do paczki (nic nie
         *   wykonuje od razu)
         * - executeBatch() - wysyła i wykonuje całą paczkę, zwraca liczbę
         *   zmienionych wierszy dla każdego polecenia (int[])
         * - clearBatch() - czyści paczkę bez jej wykonania
         * - Dobra praktyka: przy batchu wyłączyć autoCommit i zatwierdzić
         *   całość jednym commit() po executeBatch()
         * - Realny zysk wydajności jest tym większy, im większy narzut
         *   pojedynczej komunikacji z bazą (sieć, prawdziwy serwer bazy
         *   danych) - w bazie w pamięci różnica bywa mniejsza, ale sama
         *   TECHNIKA pozostaje tą samą, zalecaną praktyką przy masowych
         *   operacjach
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE numbers_single (id INT PRIMARY KEY, num_value INT NOT NULL)");
            stmt.execute("CREATE TABLE numbers_batch (id INT PRIMARY KEY, num_value INT NOT NULL)");
        }
    }

    private static void printCount(Connection connection, String table) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            var rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + table);
            rs.next();
            System.out.println("Liczba wierszy w " + table + ": " + rs.getInt("total"));
            rs.close();
        }
    }
}
