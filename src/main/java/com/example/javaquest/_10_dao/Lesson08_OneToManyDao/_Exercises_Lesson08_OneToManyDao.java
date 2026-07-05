package com.example.javaquest._10_dao.Lesson08_OneToManyDao;

public class _Exercises_Lesson08_OneToManyDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateOneToManySchema {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l08ex01;DB_CLOSE_DELAY=-1" utwórz tabele authors
         * (id, name) i books (id, title, author_id REFERENCES authors(id)). Wstaw
         * jednego autora i 2 jego książki zwykłym Statement, potem odczytaj przez
         * JOIN i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TwoQueriesStrategyBasic {
        /*
         * 🧪 Zadanie 2:
         * Dla schematu z Zadania 1, napisz AuthorDao.findByIdWithBooksUsingTwoQueries
         * (long authorId) - najpierw SELECT z authors, potem SELECT z books WHERE
         * author_id = ?. Zwróć record Author(Long id, String name, List<String>
         * bookTitles). Przetestuj na jednym autorze z 3 książkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_JoinStrategyBasic {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj TĘ SAMĄ funkcjonalność co w Zadaniu 2, ale jednym
         * zapytaniem JOIN z ręcznym grupowaniem w Javie (jak w lekcji). Porównaj
         * wynik z Zadaniem 2 (powinien być identyczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OneToManyForOrdersAndItems {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz schemat orders/order_items z lekcji na osobnej bazie i
         * zaimplementuj findByIdWithItemsUsingTwoQueries dla NOWEGO zamówienia
         * (inne dane niż w lekcji: 2 pozycje, inne nazwy produktów). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EmptyManySideHandling {
        /*
         * 🧪 Zadanie 5:
         * Wstaw autora BEZ żadnej książki. Wywołaj findByIdWithBooksUsingTwoQueries
         * dla tego autora i sprawdź, że lista bookTitles jest PUSTA (nie null,
         * bez wyjątku) - JOIN by w tym przypadku nie zwrócił żadnego wiersza,
         * dlatego druga strategia (dwa zapytania) jest tu naturalniejsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NonExistentParentThrowsException {
        /*
         * 🧪 Zadanie 6:
         * Wywołaj findByIdWithBooksUsingTwoQueries dla NIEISTNIEJĄCEGO id autora
         * (np. 999) i sprawdź, że metoda rzuca IllegalStateException (wzorem
         * lekcji) z czytelnym komunikatem, zamiast zwrócić null lub pustego
         * autora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountItemsPerParent {
        /*
         * 🧪 Zadanie 7:
         * Dla 3 różnych autorów z różną liczbą książek (0, 1, 3), wywołaj
         * findByIdWithBooksUsingTwoQueries dla każdego i wypisz "Autor X ma N
         * ksiazek" na podstawie bookTitles.size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UserOrdersOneToManyPattern {
        /*
         * 🧪 Zadanie 8:
         * Tabele users (id, name) i orders (id, user_id, total). Napisz
         * UserDao.findByIdWithOrdersUsingTwoQueries(long userId) zwracającą
         * record User(Long id, String name, List<BigDecimal> orderTotals).
         * Przetestuj dla użytkownika z 2 zamówieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ManualGroupingLogicExplained {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj findByIdWithItemsUsingJoin i w komentarzu w kodzie
         * DOKŁADNIE opisz, co się stanie z wartością zmiennej customerName przy
         * każdej iteracji pętli while(rs.next()) dla zamówienia z 3 pozycjami
         * (dlaczego jest odczytywana wielokrotnie, mimo że to ta sama wartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareResultsOfBothStrategies {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj OBIE strategie (JOIN i dwa zapytania) dla tego samego
         * zamówienia z 4 pozycjami i porównaj wyniki przez .equals() na obiektach
         * Order (record) - wypisz "IDENTYCZNE" albo "ROZNE".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_JoinStrategyWithMultipleParents {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj strategię JOIN, by obsługiwała WIELE zamówień naraz (nie
         * WHERE o.id = ?, ale bez warunku) - findAllWithItemsUsingJoin() zwracająca
         * List<Order>, z grupowaniem po order_id przy pomocy Map<Long, Order> w
         * trakcie iteracji po ResultSet. Przetestuj na 3 zamówieniach z różną
         * liczbą pozycji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoQueriesStrategyForAllParents {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj findAllWithItemsUsingTwoQueries() (odpowiednik Zadania 11,
         * ale strategią dwóch zapytań: 1 SELECT dla wszystkich orders, potem PO
         * JEDNYM SELECT dla pozycji KAŻDEGO zamówienia w pętli - to jest właśnie
         * problem "N+1 zapytań" wspomniany w lekcji). Porównaj wynik z Zadaniem 11.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MeasureNPlusOneVsJoinPerformance {
        /*
         * 🧪 Zadanie 13:
         * Wstaw 50 zamówień, każde z 3 pozycjami. Zmierz (System.nanoTime()) czas
         * wywołania findAllWithItemsUsingJoin() (Zadanie 11) oraz
         * findAllWithItemsUsingTwoQueries() (Zadanie 12, z problemem N+1). Wypisz
         * porównanie i skomentuj, gdzie widać różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NestedOneToManyThreeLevels {
        /*
         * 🧪 Zadanie 14:
         * Tabele authors (id, name), books (id, title, author_id), chapters (id,
         * book_id, title). Zaimplementuj (strategią dwóch zapytań na każdym
         * poziomie) findAuthorWithBooksAndChapters(authorId) zwracającą pełne
         * drzewo: autor -> lista książek -> każda z listą rozdziałów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FilteredOneToManyQuery {
        /*
         * 🧪 Zadanie 15:
         * Rozbuduj order_items o kolumnę quantity. Napisz
         * findByIdWithItemsAboveQuantity(orderId, int minQuantity) zwracającą
         * zamówienie z TYLKO tymi pozycjami, których quantity > minQuantity
         * (filtr w WHERE drugiego zapytania). Przetestuj dla zamówienia z 4
         * pozycjami o różnych ilościach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AggregateOverManySideWithoutFetchingAll {
        /*
         * 🧪 Zadanie 16:
         * Napisz calculateOrderTotal(long orderId) wykonujące SELECT SUM(price *
         * quantity) FROM order_items WHERE order_id = ? - liczy sumę PO STRONIE
         * BAZY, bez pobierania wszystkich pozycji do Javy. Porównaj wynik z
         * ręcznym zsumowaniem items pobranych przez findByIdWithItemsUsingTwoQueries.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DeleteParentCascadesOrNot {
        /*
         * 🧪 Zadanie 17:
         * Zbadaj zachowanie DELETE FROM orders WHERE id = ? dla zamówienia
         * MAJĄCEGO pozycje w order_items (bez ON DELETE CASCADE w definicji
         * klucza obcego) - złap SQLException (naruszenie klucza obcego) i wypisz
         * czytelny komunikat. Następnie usuń NAJPIERW pozycje, POTEM zamówienie,
         * i pokaż, że to się powodzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddItemToExistingOrder {
        /*
         * 🧪 Zadanie 18:
         * Napisz addItemToOrder(long orderId, String productName, int quantity,
         * BigDecimal price) - INSERT do order_items z podanym order_id. Wywołaj
         * dla istniejącego zamówienia i sprawdź przez
         * findByIdWithItemsUsingTwoQueries, że nowa pozycja jest widoczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_OneToManyWithOrderByOnChildTable {
        /*
         * 🧪 Zadanie 19:
         * Zmodyfikuj findByIdWithItemsUsingTwoQueries, by pozycje były sortowane
         * ORDER BY price DESC (najdroższe najpierw), a nie po id. Wstaw pozycje w
         * "przypadkowej" kolejności cenowej i sprawdź poprawność sortowania
         * wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ChooseStrategyBasedOnSingleVsBulk {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę Order getOrder(OrderDao dao, long id, boolean bulkContext)
         * - jeśli bulkContext == false (pojedyncze zamówienie), użyj JOIN; jeśli
         * true (symulacja pobierania w kontekście listy wielu zamówień), użyj
         * dwóch zapytań. W komentarzu uzasadnij tę decyzję zgodnie z zasadą z
         * podsumowania lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullOneToManyServiceWithBothStrategiesSwappable {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj OrderDao z metodą findByIdWithItems(long id, FetchStrategy
         * strategy) - enum FetchStrategy { JOIN, TWO_QUERIES } - wybierającą
         * odpowiednią wewnętrzną implementację na podstawie parametru. Przetestuj
         * dla obu wartości enum na tym samym zamówieniu i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReportAggregatingManySideAcrossAllParents {
        /*
         * 🧪 Zadanie 22:
         * Wstaw 5 zamówień z różną liczbą pozycji. Napisz generateOrdersReport()
         * łączącą findAllWithItemsUsingJoin (Zadanie 11) z obliczeniem sumy
         * wartości KAŻDEGO zamówienia (suma price*quantity jego pozycji) - wypisz
         * pełny raport: id zamówienia, liczba pozycji, suma wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HandlingOrphanedChildRows {
        /*
         * 🧪 Zadanie 23:
         * Wyłącz na chwilę więz integralności (albo skonstruuj scenariusz przez
         * INSERT bezpośredni z author_id wskazującym na usuniętego autora, jeśli
         * H2 to pozwoli bez REFERENCES) LUB zasymuluj "osierocone" dane inaczej -
         * napisz findOrphanedBooks() (SELECT książek, których author_id NIE
         * istnieje w authors) i wypisz wynik. Jeśli REFERENCES w H2 nie pozwala
         * na taki stan, zamiast tego zademonstruj w komentarzu, DLACZEGO klucz
         * obcy chroni przed tym scenariuszem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BulkFetchWithInClauseOptimization {
        /*
         * 🧪 Zadanie 24:
         * Zamiast N+1 zapytań (Zadanie 12), zaimplementuj findAllWithItemsUsingInClause()
         * - JEDEN SELECT id z orders, potem JEDEN SELECT * FROM order_items WHERE
         * order_id IN (...) (wszystkie id naraz), grupowanie w Javie przez
         * Collectors.groupingBy. Zmierz czas i porównaj z podejściem N+1 dla 50
         * zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TransactionalInsertOfParentAndChildren {
        /*
         * 🧪 Zadanie 25:
         * Napisz placeOrderWithItems(Connection, String customerName, List<OrderItem>
         * items) wstawiającą zamówienie i WSZYSTKIE jego pozycje w JEDNEJ
         * transakcji (setAutoCommit(false) + commit/rollback). Zasymuluj błąd
         * przy wstawianiu jednej z pozycji (np. quantity < 0 z CHECK) i sprawdź,
         * że CAŁE zamówienie (nagłówek + wszystkie pozycje) zostało cofnięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RecursiveLikeOneToManySelfReference {
        /*
         * 🧪 Zadanie 26:
         * Tabela categories (id, name, parent_id REFERENCES categories(id) NULL) -
         * relacja jeden-do-wielu DO SIEBIE SAMEJ (drzewo kategorii). Napisz
         * findChildrenOf(Long parentId) (WHERE parent_id = ? albo IS NULL dla
         * korzenia). Wstaw 2-poziomowe drzewo (1 korzeń, 3 dzieci) i wypisz
         * dzieci korzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrentModificationOfManySideDuringRead {
        /*
         * 🧪 Zadanie 27:
         * Uruchom DWA wątki z WŁASNYMI Connection: jeden co jakiś czas dodaje
         * nowe pozycje do istniejącego zamówienia (addItemToOrder), drugi
         * wielokrotnie wywołuje findByIdWithItemsUsingTwoQueries dla TEGO SAMEGO
         * zamówienia i wypisuje liczbę pozycji. Poczekaj na zakończenie obu
         * (join z limitem czasu) i sprawdź, że program kończy się bez wyjątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ComparingResultEqualityIgnoringOrder {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj obie strategie tak, by JOIN zwracał pozycje w innej
         * kolejności niż dwa zapytania (np. różne ORDER BY). Napisz metodę
         * porównującą wyniki IGNORUJĄC kolejność elementów listy (np. przez
         * konwersję do Set po polach istotnych) i wypisz, czy dane są
         * "logicznie identyczne" mimo różnej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LazyVsEagerFetchSimulation {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj DWIE wersje findById: findByIdEager (od razu z pozycjami,
         * strategia dwóch zapytań) i findByIdLazy (zwraca Order z PUSTĄ listą
         * items, a osobna metoda loadItemsFor(Order) dociąga pozycje NA
         * ŻĄDANIE). Zademonstruj scenariusz, gdzie lazy jest szybsze, bo
         * pozycje NIGDY nie są potrzebne (np. tylko wypisujemy nazwę klienta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullOneToManyMiniInventorySystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini-system: warehouses (id, name) i stock_items (id,
         * warehouse_id, product_name, quantity) - jeden magazyn ma wiele pozycji
         * stanu. WarehouseDao z findByIdWithStockUsingTwoQueries i
         * findAllWithStockUsingJoin. Zasymuluj 3 magazyny z różną liczbą pozycji
         * (0, 2, 5), wypisz pełny raport stanu WSZYSTKICH magazynów (obiema
         * strategiami, porównując wyniki) oraz łączną liczbę sztuk we WSZYSTKICH
         * magazynach.
         */
        public static void main(String[] args) { }
    }
}
