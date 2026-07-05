package com.example.javaquest._08_sql.Lesson09_DML;

public class _Exercises_Lesson09_DML {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InsertMultipleRows {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_dml;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)). Wstaw 4 produkty przez 4 osobne polecenia INSERT i wypisz
         * wszystkie (SELECT *).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SelectAllRows {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "products" z Zadania 1 z 3 produktami. Wykonaj "SELECT *
         * FROM products" i wypisz każdy wiersz w formacie "<id>: <name> - <price> zl".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UpdateSingleRow {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)) i wstaw 3 produkty. Wykonaj UPDATE zmieniający cenę JEDNEGO
         * produktu (WHERE id = ?) i wypisz zawartość tabeli PRZED i PO aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DeleteSingleRow {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "products" i wstaw 3 produkty. Wykonaj DELETE usuwający JEDEN
         * produkt (WHERE id = ?) i wypisz zawartość tabeli PRZED i PO usunięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InsertWithExplicitColumnList {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100), email
         * VARCHAR(100)). Wstaw 2 wiersze, JAWNIE podając listę kolumn ("INSERT INTO
         * users (id, name, email) VALUES (...)") i wypisz je - w println wyjaśnij,
         * czemu jawne podanie kolumn jest bezpieczniejsze niż pominięcie listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UpdateWithoutWhereDangerDemo {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) i wstaw
         * 3 produkty z różnymi cenami. Wykonaj (celowo, do demonstracji) UPDATE BEZ
         * WHERE ustawiający wszystkim tę samą cenę - wypisz tabelę PRZED i PO,
         * pokazując, że WSZYSTKIE wiersze zostały zmienione (typowy "wypadek" z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DeleteWithoutWhereDangerDemo {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "products" i wstaw 3 produkty. Wykonaj (celowo) DELETE FROM
         * products BEZ WHERE - wypisz COUNT(*) PRZED i PO, pokazując, że tabela jest
         * teraz pusta, ALE jej struktura nadal istnieje (w przeciwieństwie do DROP
         * TABLE - poznaliśmy w Lesson08_DDL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SelectDoesNotModifyData {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę "products" i wstaw 2 produkty. Wykonaj DWA identyczne
         * zapytania SELECT * FROM products, jedno po drugim, i porównaj (w Javie)
         * wyniki - zweryfikuj, że są identyczne, bo SELECT (w przeciwieństwie do
         * INSERT/UPDATE/DELETE) NIGDY nie zmienia danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FourDmlCommandsInSequence {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "tasks" (id INT PRIMARY KEY, title VARCHAR(100), done
         * BOOLEAN). Wykonaj po kolei WSZYSTKIE 4 polecenia DML: INSERT 2 zadań,
         * SELECT * (wypisz), UPDATE jednego zadania na done=true, DELETE drugiego
         * zadania. Po każdym kroku wypisz krótki log "Krok N: <opis>" i aktualny stan
         * tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DmlVsDdlPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz println wyjaśniający różnicę między DML
         * (SELECT, INSERT, UPDATE, DELETE - operacje na REKORDACH) i DDL (CREATE,
         * ALTER, DROP, TRUNCATE - operacje na STRUKTURZE, poznane w Lesson08_DDL) -
         * podaj przykład każdego rodzaju polecenia.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConditionalUpdateMultipleRows {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 5 produktów w 2 kategoriach. Wykonaj UPDATE
         * zwiększający cenę o 10% WSZYSTKICH produktów jednej kategorii (WHERE
         * category = ?) i zweryfikuj (SELECT), że dotknięte zostały tylko te wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SafePatternSelectBeforeUpdate {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) i wstaw
         * 6 produktów. Zademonstruj "bezpieczny wzorzec" z lekcji: najpierw wykonaj
         * SELECT z TYM SAMYM warunkiem WHERE, jaki planujesz użyć w UPDATE (np. price
         * < 100), wypisz, KTÓRE wiersze zostaną dotknięte, a DOPIERO POTEM wykonaj
         * faktyczny UPDATE z tym warunkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InsertUpdateDeleteReturningAffectedRows {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "products" i wstaw 5 produktów. Użyj Statement.executeUpdate(...)
         * (nie execute) dla INSERT, UPDATE i DELETE, i dla każdego wypisz LICZBĘ
         * zmodyfikowanych wierszy (wartość zwracaną przez executeUpdate) - wykonaj
         * UPDATE dotykający 2 wierszy i DELETE dotykający 1 wiersza, weryfikując, że
         * zwrócone liczby się zgadzają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BatchInsertFromList {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)). Przygotuj listę (List<String>) 5 poleceń INSERT i wykonaj
         * je w pętli (stmt.execute dla każdego). Wypisz łączną liczbę wierszy po
         * wszystkich wstawieniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UpdateMultipleColumnsAtOnce {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2), in_stock BOOLEAN) i wstaw 3 produkty. Wykonaj JEDEN UPDATE
         * zmieniający NA RAZ dwie kolumny jednego produktu (price i in_stock,
         * rozdzielone przecinkiem w SET) i wypisz zmieniony wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DeleteBasedOnComputedCondition {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) i wstaw
         * 6 produktów z różnymi cenami. Wykonaj DELETE usuwający produkty droższe niż
         * 500 zł (WHERE price > 500) i wypisz, ile wierszy zostało usuniętych oraz
         * jaka jest zawartość tabeli po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InsertUpdateInterleaved {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "inventory" (id INT PRIMARY KEY, name VARCHAR(100), quantity
         * INT). Zasymuluj magazyn: INSERT nowego produktu (quantity=10), UPDATE
         * zwiększające quantity o 5 (dostawa), UPDATE zmniejszające quantity o 3
         * (sprzedaż) - po każdym kroku wypisz aktualną wartość quantity, śledząc
         * historię zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConditionalDeleteWithSubconditionAndOr {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 6 produktów w 3 kategoriach. Wykonaj DELETE
         * usuwający produkty, które są W KATEGORII 'Elektronika' LUB mają cenę poniżej
         * 20 zł (WHERE category = 'Elektronika' OR price < 20) - wypisz liczbę
         * usuniętych wierszy i zawartość po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UpsertLikePatternManualCheck {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "settings" (config_key VARCHAR(50) PRIMARY KEY, config_value
         * VARCHAR(200)). Napisz metodę upsert(Statement stmt, String key, String
         * value), która RĘCZNIE sprawdza (SELECT COUNT(*)), czy klucz już istnieje - i
         * wykonuje UPDATE, jeśli tak, albo INSERT, jeśli nie (H2 wsparcie dla MERGE
         * poznamy może w innym rozdziale - tu robimy to "ręcznie"). Przetestuj metodę:
         * pierwsze wywołanie z nowym kluczem (INSERT), drugie z tym samym kluczem
         * (UPDATE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TrackChangesWithBeforeAfterSnapshots {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 3
         * konta. Napisz metodę snapshot(Statement stmt), zwracającą Map<Integer,
         * Integer> (id -> balance) całej tabeli. Zrób snapshot PRZED serią operacji
         * (2 UPDATE i 1 DELETE), wykonaj operacje, zrób snapshot PO, i wypisz różnice
         * (jakie konta zmieniły saldo, jakie zostały usunięte).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCrudLifecycleWithLogging {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)). Napisz mini-CRUD API w Javie: metody create(Statement,
         * int, String, BigDecimal), read(Statement, int), updatePrice(Statement, int,
         * BigDecimal), delete(Statement, int) - każda z logowaniem (println) co
         * właśnie robi i z jakim skutkiem (liczba dotkniętych wierszy). Zademonstruj
         * pełny cykl życia jednego produktu przez wszystkie 4 operacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BulkUpdateWithBusinessRuleValidation {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2) CHECK
         * (price >= 0)) i wstaw 5 produktów. Napisz metodę applyDiscountPercent
         * (Statement stmt, int percent), która wykonuje UPDATE zmniejszający WSZYSTKIE
         * ceny o podany procent. Przetestuj ją z percent=20 (powinno się udać) i
         * percent=150 (spowodowałoby ujemne ceny - CHECK powinien odrzucić UPDATE,
         * złap SQLException) - zweryfikuj, że przy nieudanej próbie ŻADNA cena się
         * nie zmieniła (CHECK działa per-wiersz, ale sprawdź to empirycznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CascadingManualDeleteWithoutForeignKey {
        /*
         * 🧪 Zadanie 23:
         * Utwórz "orders" (id INT PRIMARY KEY) i "order_items" (id INT PRIMARY KEY,
         * order_id INT) - BEZ ograniczenia FOREIGN KEY (celowo). Wstaw 1 zamówienie i
         * 3 jego pozycje. Napisz metodę deleteOrderWithItems(Statement stmt, int
         * orderId), która RĘCZNIE usuwa najpierw wszystkie order_items, a potem samo
         * zamówienie (symulacja ON DELETE CASCADE w kodzie Javy, bez wsparcia bazy) -
         * zweryfikuj przez COUNT(*), że obie tabele są puste po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TransactionalMultiStepUpdateSimulation {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance
         * >= 0)) i wstaw 2 konta (1000 i 500). Napisz metodę transfer(Statement stmt,
         * int fromId, int toId, int amount), która wykonuje DWA UPDATE (odjęcie i
         * dodanie) - JEŚLI drugi UPDATE (dodanie) się powiedzie, ale PIERWSZY (odjęcie)
         * naruszyłby CHECK, złap SQLException i RĘCZNIE wykonaj kompensujący UPDATE
         * odwracający pierwszą operację (transakcje poznamy dokładnie w Lesson19 -
         * tu robimy to "ręcznie" przez kompensację). Przetestuj przelew, który się
         * nie powinien udać (za duża kwota).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DataMigrationBetweenTwoTablesInBatches {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "orders_archive_source" (id INT PRIMARY KEY, data VARCHAR(100)) z 20
         * wierszami i pustą "orders_archive_target" (id INT PRIMARY KEY, data
         * VARCHAR(100)). Napisz metodę migrateBatch(Statement stmt, int batchSize),
         * która przenosi dane w PACZKACH (np. po 5 wierszy: SELECT LIMIT/OFFSET -
         * podglądowo, dokładnie poznamy w Lesson10 - INSERT do target, DELETE ze
         * source dla przeniesionej paczki), aż source będzie puste. Wypisz log
         * postępu po każdej paczce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IdempotentInsertWithDuplicateProtection {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "processed_events" (event_id INT PRIMARY KEY, description
         * VARCHAR(100)). Napisz metodę processEventIdempotently(Statement stmt, int
         * eventId, String description), która próbuje wykonać INSERT, ale łapie
         * SQLException naruszenia PRIMARY KEY i traktuje to jako "już przetworzone,
         * ignoruj" (a nie błąd krytyczny) - zademonstruj wywołanie tej metody 3 razy z
         * TYM SAMYM eventId i zweryfikuj (COUNT(*)), że wiersz istnieje tylko raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConditionalBulkArchival {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "orders" (id INT PRIMARY KEY, status VARCHAR(20), amount DECIMAL(10,2))
         * i pustą "orders_archive" (id INT PRIMARY KEY, amount DECIMAL(10,2)). Wstaw
         * 8 zamówień z różnymi statusami (część 'DELIVERED', część 'NEW'). Napisz
         * metodę archiveDelivered(Statement stmt), która dla WSZYSTKICH zamówień o
         * statusie 'DELIVERED' wykonuje INSERT do orders_archive, a potem DELETE z
         * orders - zweryfikuj (COUNT(*) w obu tabelach), że dane "przeniosły się"
         * poprawnie i nic nie zginęło ani nie zduplikowało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditTrailOnEveryModification {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) i "price_audit"
         * (id INT PRIMARY KEY AUTO_INCREMENT, product_id INT, old_price DECIMAL(10,2),
         * new_price DECIMAL(10,2)). Napisz metodę updatePriceWithAudit(Statement
         * stmt, int productId, BigDecimal newPrice), która PRZED wykonaniem UPDATE
         * odczytuje starą cenę (SELECT), wykonuje UPDATE, a potem wstawia wiersz do
         * price_audit z obiema wartościami. Wykonaj 3 zmiany ceny tego samego
         * produktu i wypisz cały log audytowy na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BulkDataFixupWithVerificationReport {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)) i wstaw 10 produktów, z czego 3 mają BŁĘDNĄ (ujemną) cenę
         * (symulacja błędu importu danych - kolumna celowo BEZ ograniczenia CHECK, żeby
         * to było możliwe). Napisz metodę fixNegativePrices(Statement stmt), która
         * znajduje wszystkie wiersze z price < 0 (SELECT), dla każdego wykonuje UPDATE
         * ustawiające price = ABS(price), i zwraca listę id poprawionych wierszy.
         * Wypisz raport przed/po naprawie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneOrderProcessingWorkflow {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-schemat: "products" (id INT PRIMARY KEY, name VARCHAR(100),
         * price DECIMAL(10,2), stock INT), "orders" (id INT PRIMARY KEY, status
         * VARCHAR(20) DEFAULT 'NEW'), "order_items" (id INT PRIMARY KEY, order_id INT,
         * product_id INT, quantity INT). Wstaw 4 produkty z zapasami magazynowymi.
         * Napisz KOMPLETNY workflow przetwarzania zamówienia w Javie, wyłącznie
         * poleceniami DML (bez transakcji - poznamy w Lesson19): (1) placeOrder -
         * INSERT zamówienia + INSERT pozycji dla 3 wybranych produktów, (2)
         * reserveStock - dla każdej pozycji UPDATE zmniejszające stock produktu (jeśli
         * stock byłby niewystarczający, wypisz ostrzeżenie i NIE wykonuj tego UPDATE),
         * (3) markAsShipped - UPDATE statusu zamówienia na 'SHIPPED'. Wypisz pełny log
         * każdego kroku i finalny stan wszystkich 3 tabel.
         */
        public static void main(String[] args) { }
    }
}
