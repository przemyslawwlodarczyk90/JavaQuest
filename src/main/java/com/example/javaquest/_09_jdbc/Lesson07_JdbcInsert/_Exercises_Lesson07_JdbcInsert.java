package com.example.javaquest._09_jdbc.Lesson07_JdbcInsert;

public class _Exercises_Lesson07_JdbcInsert {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicInsertReturnsOneRow {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l07ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "orders" (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
         * customer_name VARCHAR(100), total_amount DECIMAL(10,2)). Wstaw
         * jeden wiersz przez PreparedStatement.executeUpdate() i wypisz
         * liczbę zmienionych wierszy (powinna być 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ValidateExactlyOneRowInserted {
        /*
         * 🧪 Zadanie 2:
         * Rozszerz Zadanie 1: po executeUpdate() sprawdź (if) czy wynik
         * to dokładnie 1 - jeśli nie, rzuć IllegalStateException z
         * komunikatem "Oczekiwano 1 wiersza, wstawiono: " + affectedRows.
         * Wypisz komunikat sukcesu, jeśli warunek jest spełniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InsertWithGeneratedKey {
        /*
         * 🧪 Zadanie 3:
         * Na tabeli "orders" (id GENERATED ALWAYS AS IDENTITY, ...) wstaw
         * wiersz przez PreparedStatement utworzony z
         * Statement.RETURN_GENERATED_KEYS, odczytaj wygenerowane id przez
         * getGeneratedKeys() i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InsertThreeRowsPrintEachGeneratedId {
        /*
         * 🧪 Zadanie 4:
         * Wstaw 3 zamówienia (różni klienci, różne kwoty) przez
         * PreparedStatement z RETURN_GENERATED_KEYS, wypisując
         * wygenerowane id każdego z nich - powinny być kolejnymi liczbami
         * (1, 2, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateIdentityTableAndVerify {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "products" (id INT GENERATED ALWAYS AS IDENTITY
         * PRIMARY KEY, name VARCHAR(100)). Wstaw 2 produkty BEZ
         * podawania id (kolumna wygenerowana automatycznie) i odczytaj
         * je przez SELECT, wypisując wygenerowane id razem z nazwą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InsertOrderAndItemUsingGeneratedId {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabele "orders" (id IDENTITY, customer_name) i
         * "order_items" (id IDENTITY, order_id INT REFERENCES
         * orders(id), product_name). Wstaw zamówienie, odczytaj jego
         * wygenerowane id, i użyj go OD RAZU do wstawienia jednej
         * pozycji zamówienia. Zweryfikuj przez JOIN SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LoopInsertCollectGeneratedIds {
        /*
         * 🧪 Zadanie 7:
         * Wstaw 5 zamówień w pętli, zbierając WSZYSTKIE wygenerowane id
         * do List<Long>. Po pętli wypisz całą listę id oraz jej rozmiar
         * (powinien być 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifyJoinAfterGeneratedIdInsert {
        /*
         * 🧪 Zadanie 8:
         * Na tabelach "orders" i "order_items" (jak w Zadaniu 6) wstaw 2
         * zamówienia, każde z 2 pozycjami (używając wygenerowanych id).
         * Wykonaj JOIN SELECT łączący obie tabele i wypisz wynik -
         * każde zamówienie z jego pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentityAutoIncrementsSequentially {
        /*
         * 🧪 Zadanie 9:
         * Wstaw 4 wiersze do tabeli z kolumną IDENTITY (bez podawania
         * id) i zweryfikuj, że wygenerowane id są kolejnymi liczbami
         * (1, 2, 3, 4) - wypisz je i porównaj z oczekiwaną sekwencją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GetGeneratedKeysOnTableWithoutIdentity {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tabelę BEZ kolumny IDENTITY (np. id INT PRIMARY KEY,
         * podajesz id sam). Wstaw wiersz z RETURN_GENERATED_KEYS i
         * spróbuj odczytać getGeneratedKeys() - wypisz, czy ResultSet ma
         * jakiekolwiek wiersze (rs.next()) - zaobserwuj, że dla kolumny
         * bez auto-generacji zwykle nie ma nic do zwrócenia.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InsertOrderAndReturnIdMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę long insertOrderAndReturnId(Connection conn,
         * String customerName, BigDecimal total), która wstawia
         * zamówienie i zwraca wygenerowane id. Wywołaj ją 3 razy z
         * różnymi danymi i wypisz zwrócone id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InsertOrderWithMultipleItemsLoop {
        /*
         * 🧪 Zadanie 12:
         * Używając metody z Zadania 11, wstaw jedno zamówienie, a
         * następnie w pętli wstaw 3 pozycje zamówienia (order_items),
         * każdą używającą tego samego wygenerowanego order_id. Zweryfikuj
         * przez SELECT COUNT(*) FROM order_items WHERE order_id = ?.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentityContinuesAfterDelete {
        /*
         * 🧪 Zadanie 13:
         * Wstaw 3 wiersze do tabeli z kolumną IDENTITY, zapamiętując
         * ich id. USUŃ (DELETE) wiersz z NAJWYŻSZYM id. Wstaw NOWY
         * wiersz i wypisz jego wygenerowane id - sprawdź, czy sekwencja
         * kontynuuje dalej (NIE reużywa usuniętego id).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FailedInsertDoesNotBreakSequence {
        /*
         * 🧪 Zadanie 14:
         * Wstaw 1 poprawny wiersz (zapamiętaj id). Spróbuj wstawić DRUGI
         * wiersz naruszający ograniczenie (np. NOT NULL na wymaganej
         * kolumnie) - złap SQLException. Wstaw TRZECI, poprawny wiersz i
         * wypisz jego wygenerowane id - skomentuj (println), czy
         * nieudana próba "zjadła" numer sekwencji, czy nie (zależnie od
         * zachowania H2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AssertInsertedOneHelperMethod {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę pomocniczą assertInsertedOne(int affectedRows),
         * rzucającą IllegalStateException, jeśli affectedRows != 1.
         * Użyj jej po KAŻDYM z 3 wstawień w tej lekcji (np. do orders),
         * demonstrując reużywalność walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TwoLevelForeignKeyChain {
        /*
         * 🧪 Zadanie 16:
         * Utwórz 3 tabele: "customers" (id IDENTITY, name), "orders" (id
         * IDENTITY, customer_id REFERENCES customers(id)), "order_items"
         * (id IDENTITY, order_id REFERENCES orders(id), product_name).
         * Wstaw klienta, użyj jego id do wstawienia zamówienia, użyj id
         * zamówienia do wstawienia pozycji - trzypoziomowy łańcuch kluczy
         * generowanych. Zweryfikuj pełnym JOIN-em wszystkich 3 tabel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CollectMultipleGeneratedIdsIntoList {
        /*
         * 🧪 Zadanie 17:
         * Wstaw 6 zamówień w pętli, zbierając wygenerowane id do
         * List<Long>. Sprawdź, że lista ma 6 UNIKALNYCH elementów
         * (np. przez porównanie rozmiaru listy z rozmiarem Set
         * utworzonego z tej listy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReturnGeneratedKeysByColumnName {
        /*
         * 🧪 Zadanie 18:
         * Użyj wariantu connection.prepareStatement(sql, new
         * String[]{"id"}) (wskazanie NAZWY kolumny, a nie ogólnego
         * RETURN_GENERATED_KEYS) do wstawienia wiersza i odczytania
         * wygenerowanego id. Porównaj (println) z podejściem z
         * Zadania 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FullRoundTripVerification {
        /*
         * 🧪 Zadanie 19:
         * Wstaw zamówienie z konkretnymi danymi (klient, kwota), odczytaj
         * jego wygenerowane id, a następnie wykonaj SELECT WHERE id = ?
         * i porównaj (println) odczytane dane z tymi, które wstawiono -
         * potwierdzenie pełnej zgodności "round-trip".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SumOfInsertedTotalsJavaVsSql {
        /*
         * 🧪 Zadanie 20:
         * Wstaw 5 zamówień z różnymi total_amount, zbierając wszystkie
         * wartości w List<BigDecimal>. Zsumuj je w Javie i porównaj z
         * wynikiem SQL "SELECT SUM(total_amount) FROM orders" - wypisz
         * obie wartości i potwierdź, że są równe.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreeLevelCascadingGeneratedKeys {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz łańcuch z Zadania 16: wstaw 2 klientów, każdy z 2
         * zamówieniami, każde zamówienie z 2 pozycjami - wszystko przez
         * kaskadowe użycie wygenerowanych id (bez ŻADNEGO ręcznego
         * podawania id). Wypisz pełny raport (klient -> zamówienia ->
         * pozycje) przez zagnieżdżone zapytania albo JOIN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BatchInsertWithGeneratedKeys {
        /*
         * 🧪 Zadanie 22:
         * Używając PreparedStatement.addBatch() + executeBatch() na
         * tabeli z kolumną IDENTITY, wstaw 5 wierszy w JEDNEJ paczce, a
         * następnie odczytaj WSZYSTKIE wygenerowane id przez
         * getGeneratedKeys() (H2 wspiera to dla batcha) - wypisz je
         * wszystkie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CreateOrderWithItemsRepositoryMethod {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę createOrderWithItems(Connection conn, String
         * customerName, List<String> productNames), która wstawia
         * zamówienie, a potem wszystkie pozycje (każdą z osobnym
         * executeUpdate, wykorzystując wygenerowane order_id), zwracając
         * finalne order_id. Przetestuj dla zamówienia z 4 pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TimingOverheadOfGeneratedKeysRetrieval {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas wstawienia 200 wierszy DWOMA sposobami: (a) BEZ
         * pobierania wygenerowanego id (normalny INSERT), (b) Z
         * pobieraniem (RETURN_GENERATED_KEYS + getGeneratedKeys() po
         * każdym insercie). Wypisz oba czasy i różnicę - skomentuj, że
         * narzut jest zwykle minimalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ForeignKeyViolationOnInsert {
        /*
         * 🧪 Zadanie 25:
         * Spróbuj wstawić do "order_items" pozycję z order_id
         * wskazującym na NIEISTNIEJĄCE zamówienie (np. 9999). Złap
         * SQLException (naruszenie klucza obcego) i wypisz jego
         * komunikat oraz getSQLState().
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CreateAndFetchRepositoryPattern {
        /*
         * 🧪 Zadanie 26:
         * Napisz klasę OrderRepository z metodą create(String
         * customerName, BigDecimal total), która wstawia zamówienie,
         * pobiera wygenerowane id i OD RAZU odczytuje pełny wiersz przez
         * SELECT (metoda "create-and-fetch"), zwracając np. String
         * opisu zamówienia. Przetestuj dla 2 różnych zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrentInsertsUniqueGeneratedIds {
        /*
         * 🧪 Zadanie 27:
         * Uruchom 2 wątki, każdy z WŁASNYM Connection do TEJ SAMEJ bazy
         * ("jdbc:h2:mem:l07ex27;DB_CLOSE_DELAY=-1"), każdy wstawiający 10
         * zamówień i zbierający swoje wygenerowane id do własnej listy.
         * Po zakończeniu obu wątków (join z limitem czasu) połącz obie
         * listy i sprawdź, że WSZYSTKIE 20 id są UNIKALNE (brak
         * kolizji między wątkami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValidateDataIntegrityAfterMultipleInserts {
        /*
         * 🧪 Zadanie 28:
         * Wstaw 10 zamówień z rosnącymi kwotami (10.00, 20.00, ..., 100.00)
         * zbierając wygenerowane id w kolejności wstawiania. Zweryfikuj
         * przez SELECT ORDER BY id, że kolejność odczytu odpowiada
         * kolejności wstawiania (id i kwoty rosną zgodnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RollbackSimulationWithoutTransactionsNote {
        /*
         * 🧪 Zadanie 29:
         * Wstaw zamówienie i JEDNĄ pozycję, po czym RĘCZNIE (bez
         * transakcji - ten temat to Lesson15) usuń oba wiersze przez
         * DELETE, symulując "cofnięcie" operacji. Zweryfikuj przez SELECT
         * COUNT(*), że oba wiersze faktycznie zniknęły, i skomentuj
         * (println), czym różni się to podejście od prawdziwego
         * rollback() (transakcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneOrderManagementDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj pełne demo zarządzania zamówieniami: tabele customers,
         * orders, order_items (kaskadowe klucze generowane). Wstaw 3
         * klientów, każdego z 1-3 zamówieniami, każde z 1-3 pozycjami
         * (dane generowane w pętli). Wypisz finalny, sformatowany raport
         * (JOIN wszystkich 3 tabel) pogrupowany po kliencie, z sumą
         * wartości zamówień każdego klienta wyliczoną w Javie.
         */
        public static void main(String[] args) { }
    }
}
