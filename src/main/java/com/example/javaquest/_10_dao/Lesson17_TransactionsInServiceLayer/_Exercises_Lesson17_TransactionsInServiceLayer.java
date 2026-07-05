package com.example.javaquest._10_dao.Lesson17_TransactionsInServiceLayer;

public class _Exercises_Lesson17_TransactionsInServiceLayer {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetupSchemaAndCheckAutoCommit {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson17ex01;DB_CLOSE_DELAY=-1" utworz tabele products
         * i orders/order_items jak w lekcji. Otworz Connection i wypisz
         * connection.getAutoCommit() PRZED wykonaniem jakiegokolwiek insertu -
         * powinien byc true (tryb domyslny, tak jak przed wejsciem do OrderService).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DaoAcceptsConnectionParameter {
        /*
         * 🧪 Zadanie 2:
         * Napisz OrderDao z metoda long insert(Connection connection, long productId)
         * PRZYJMUJACA Connection jako PARAMETR (nie tworzaca wlasnego, jak w lekcji).
         * Wywolaj ja dwukrotnie na tym samym, jawnie otwartym polaczeniu i sprawdz
         * przez SELECT COUNT(*), ze powstaly 2 wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ManualTransactionTwoSteps {
        /*
         * 🧪 Zadanie 3:
         * Na bazie "jdbc:h2:mem:lesson17ex03;DB_CLOSE_DELAY=-1" recznie (bez klasy
         * Service) wykonaj: setAutoCommit(false), insert do orders, insert do
         * order_items (na TYM SAMYM connection), potem commit(). Sprawdz SELECT
         * COUNT(*) z obu tabel - obie powinny miec dokladnie 1 wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrderServiceHappyPath {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj OrderService z lekcji (placeOrder wywolujace OrderDao,
         * OrderItemDao, ProductDao.decreaseStock w jednej transakcji) na bazie
         * "jdbc:h2:mem:lesson17ex04;DB_CLOSE_DELAY=-1" z produktem o stanie 10.
         * Wywolaj placeOrder(productId, 3) i wypisz stan magazynowy PO operacji
         * (powinien wynosic 7).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RollbackOnInsufficientStock {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac OrderService z Zadania 4 (produkt o stanie 10), wywolaj
         * placeOrder(productId, 999) (za duzo). Sprawdz PO tej probie: COUNT(*) z
         * orders (powinno byc 0), COUNT(*) z order_items (powinno byc 0) oraz stan
         * magazynowy (powinien wrocic do 10, NIEZMIENIONY) - dowod, ze rollback cofnal
         * WSZYSTKIE kroki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FinallyRestoresAutoCommitAfterSuccess {
        /*
         * 🧪 Zadanie 6:
         * Uzywajac OrderService z Zadania 4, wypisz connection.getAutoCommit() PRZED
         * wywolaniem placeOrder (powinno byc true), wykonaj UDANE zamowienie, a
         * nastepnie wypisz getAutoCommit() PONOWNIE - musi znowu byc true (finally
         * zadzialal), mimo ze wewnatrz metody bylo ustawione na false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FinallyRestoresAutoCommitAfterFailure {
        /*
         * 🧪 Zadanie 7:
         * Analogicznie do Zadania 6, ale wywolaj NIEUDANE zamowienie (za duza
         * ilosc, wywolujace catch+rollback). Sprawdz, ze getAutoCommit() PO
         * nieudanej probie TAKZE wraca do true - finally dziala niezaleznie od tego,
         * czy transakcja sie powiodla, czy nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoConsecutiveSuccessfulOrders {
        /*
         * 🧪 Zadanie 8:
         * Na bazie "jdbc:h2:mem:lesson17ex08;DB_CLOSE_DELAY=-1" z produktem o stanie
         * 10, wywolaj placeOrder DWA RAZY z rzedu (quantity=2 kazdy raz, na DWOCH
         * ROZNYCH, jawnie otwieranych polaczeniach jak w lekcji). Wypisz stan
         * magazynowy po kazdym wywolaniu (powinien wynosic kolejno 8, potem 6).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintStateHelperMethod {
        /*
         * 🧪 Zadanie 9:
         * Napisz wlasna wersje metody printState(url, label) z lekcji (liczba
         * zamowien, liczba pozycji zamowien, stan magazynowy produktu #1) i uzyj jej
         * PRZED i PO jednym udanym oraz jednym nieudanym zamowieniu - w sumie 4
         * wywolania printState pokazujace pelna historie zmian stanu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_OrderItemDaoConnectionParameter {
        /*
         * 🧪 Zadanie 10:
         * Napisz OrderItemDao.insert(Connection, orderId, productId, quantity) (jak w
         * lekcji, Connection jako parametr). Wywolaj ja DWUKROTNIE na TYM SAMYM
         * polaczeniu (bez commit miedzy wywolaniami), a nastepnie wywolaj JEDEN
         * commit na koncu - sprawdz SELECT COUNT(*) z order_items (powinno byc 2).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeStepTransactionWithMiddleFailure {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj OrderService o CZWARTY krok (po decreaseStock): insert do tabeli
         * order_log (id, message) - dodaj ta tabele. Zasymuluj blad W TRZECIM kroku
         * (decreaseStock, za duza ilosc) i sprawdz, ze CZWARTY krok (log) NIGDY sie
         * nie wykonal, a rollback cofnal wszystko az do (i wlacznie z) pierwszego
         * kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomExceptionInsteadOfIllegalState {
        /*
         * 🧪 Zadanie 12:
         * Zamien IllegalStateException w ProductDao.decreaseStock na WLASNY,
         * niesprawdzany wyjatek InsufficientStockException(String message).
         * Zmodyfikuj catch w OrderService, by lapal SQLException ORAZ
         * InsufficientStockException w jednym bloku multi-catch. Zweryfikuj, ze
         * rollback wciaz dziala identycznie jak w lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TransactionSpanningFourDaoCalls {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do schematu tabele users (id, name) i rozbuduj OrderService o KROK 0:
         * sprawdzenie istnienia usera (UserDao.findNameById) PRZED insertem
         * zamowienia - CALA operacja (4 kroki: user, order, order_item, stock) ma byc
         * JEDNA transakcja. Zasymuluj blad w kroku 0 (nieistniejacy user) i sprawdz,
         * ze ZADEN insert nie zostal wykonany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SeparateConnectionsPerCallProveIsolation {
        /*
         * 🧪 Zadanie 14:
         * Na bazie "jdbc:h2:mem:lesson17ex14;DB_CLOSE_DELAY=-1" z 2 produktami,
         * wywolaj placeOrder DLA KAZDEGO produktu na WLASNYM, osobno otwieranym
         * polaczeniu (2 wywolania OrderService, kazde z nowym Connection, jak w
         * strukturze lekcji). Potwierdz w komentarzu, ze kazde wywolanie ma WLASNA,
         * niezalezna transakcje (commit/rollback jednego NIE wplywa na drugie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RollbackExceptionWrappedInRuntimeException {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj sytuacje, w ktorej connection.rollback() SAM rzuca SQLException
         * (np. zamknij connection.close() PRZED wywolaniem rollback() wewnatrz catch).
         * Sprawdz, ze OrderService (jak w lekcji) lapie ten wyjatek i rzuca
         * RuntimeException("Nie udalo sie wykonac rollbacku", rollbackEx) - wypisz
         * getCause() tego RuntimeException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleOrdersSequentialStockDepletion {
        /*
         * 🧪 Zadanie 16:
         * Na bazie "jdbc:h2:mem:lesson17ex16;DB_CLOSE_DELAY=-1" z produktem o stanie
         * 10, wywolaj placeOrder w petli 5 razy z quantity=2 kazdy raz (kazde
         * wywolanie na nowym Connection). Po KAZDYM wywolaniu wypisz aktualny stan
         * magazynowy - piate wywolanie powinno wyzerowac stan do 0, a szuste (jesli
         * dodasz) powinno zostac odrzucone przez rollback.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VerifyNoPartialWritesAfterRollback {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj test z Zadania 5: po nieudanej probie zamowienia sprawdz WSZYSTKIE
         * trzy elementy stanu jednoczesnie (COUNT orders, COUNT order_items, stan
         * magazynowy) i porownaj je z wartosciami sprzed wywolania placeOrder -
         * napisz prosta metode assertNoChange(before, after) rzucajaca
         * AssertionError, jesli COKOLWIEK sie zmienilo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TransactionalDeleteAcrossTwoTables {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode cancelOrderTransactional(connection, orderId) w OrderService,
         * ktora w JEDNEJ transakcji: usuwa wiersze order_items dla danego orderId,
         * usuwa wiersz z orders, i PRZYWRACA stan magazynowy (UPDATE products SET
         * quantity_in_stock = quantity_in_stock + quantity). Przetestuj na zamowieniu
         * zlozonym przez placeOrder - po anulowaniu stan magazynowy powinien wrocic do
         * wartosci poczatkowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PartialFailureInCancelRollsBackEverything {
        /*
         * 🧪 Zadanie 19:
         * Zasymuluj blad w cancelOrderTransactional z Zadania 18 (np. przekaz
         * nieistniejace orderId do UPDATE stanu magazynowego, powodujac 0 zmienionych
         * wierszy - w tym zadaniu potraktuj to jako rzucony wyjatek recznie jesli
         * getUpdateCount()==0). Sprawdz, ze rollback cofnal WSZYSTKIE 3 operacje
         * DELETE/UPDATE tej "nieudanej" anulacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureTransactionDuration {
        /*
         * 🧪 Zadanie 20:
         * Rozbuduj placeOrder o pomiar czasu (System.nanoTime() przed
         * setAutoCommit(false), po commit/rollback) i wypisanie czasu trwania calej
         * transakcji w milisekundach. Wykonaj 3 zamowienia (roznej wielkosci) i
         * porownaj zmierzone czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FiveStepTransactionWithReportBeforeRollback {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj OrderService o piaty krok: walidacje limitu zamowienia (quantity
         * <= 50) WEWNATRZ transakcji (po insercie zamowienia, przed decreaseStock).
         * Zbierz liste nazw krokow, ktore SIE POWIODLY (np. ["order", "order_item"])
         * PRZED wystapieniem bledu, wypisz ja w catch (wraz z komunikatem bledu), a
         * potem wywolaj rollback - dowodzac, ze mimo "powodzenia" czesciowego, cala
         * transakcja jest wycofywana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_IndependentAuditLogSurvivesRollback {
        /*
         * 🧪 Zadanie 22:
         * Dodaj tabele order_log (id, message). Wykonaj NIEUDANE zamowienie (rollback
         * glownej transakcji), ale ZAPISZ informacje o probie w order_log w
         * OSOBNEJ, NIEZALEZNEJ transakcji (osobne Connection, wlasny commit) - tak,
         * aby log PRZETRWAL rollback glownej operacji. Sprawdz na koniec, ze
         * order_log ma wpis, a orders/order_items sa puste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentOrdersOnSameProductTwoThreads {
        /*
         * 🧪 Zadanie 23:
         * Na bazie "jdbc:h2:mem:lesson17ex23;DB_CLOSE_DELAY=-1" z produktem o stanie
         * 20, uruchom DWA WATKI, kazdy z WLASNYM Connection, kazdy wywolujacy
         * placeOrder(productId, 3) 5 razy w petli (10 zamowien lacznie, wymagajace
         * lacznie 30 sztuk - WIECEJ niz jest na stanie). Poczekaj na oba watki (join z
         * limitem czasu) i sprawdz, ze stan magazynowy NIGDY nie spadl pod 0 (dzieki
         * temu, ze kazda transakcja sprawdza stan PRZED odjeciem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RefactorServiceToGenericTransactionHelper {
        /*
         * 🧪 Zadanie 24:
         * Wydziel z OrderService WSPOLNY, generyczny fragment
         * (setAutoCommit(false)+try+commit+catch+rollback+finally) do osobnej metody
         * pomocniczej runInTransaction(Connection, Runnable) i przebuduj placeOrder,
         * by z niej korzystala. Przetestuj, ze zachowanie (sukces/rollback) jest
         * IDENTYCZNE jak przed refaktoryzacja, na tych samych 2 scenariuszach z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ChainedOrdersSecondDependsOnFirstAtomicity {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode placeTwoOrdersAtomically(connection, productId, qty1, qty2)
         * wykonujaca DWA zamowienia (dwa niezalezne insert+decreaseStock) w JEDNEJ,
         * WSPOLNEJ transakcji. Ustaw stan magazynowy tak, by qty1 sie powiodlo, ale
         * qty1+qty2 przekroczylo stan - sprawdz, ze DRUGIE zamowienie powoduje
         * rollback OBU (nawet tego, ktore "samo" bylo poprawne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TransactionTimeoutSimulationWithSleep {
        /*
         * 🧪 Zadanie 26:
         * Rozbuduj pomiar czasu z Zadania 20: jesli transakcja trwa dluzej niz 100ms,
         * wypisz "OSTRZEZENIE: wolna transakcja". Zademonstruj to na DWOCH
         * zamowieniach: szybkim (normalny placeOrder) i celowo spowolnionym
         * (Thread.sleep(150) wstawiony miedzy krok insertu order_item a decreaseStock,
         * WEWNATRZ tej samej transakcji, PRZED commitem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RetryOnTransientFailureWithBackoff {
        /*
         * 🧪 Zadanie 27:
         * Napisz placeOrderWithRetry(productId, quantity, maxAttempts) probujaca
         * wywolac placeOrder do maxAttempts razy, z rosnacym opoznieniem miedzy
         * probami (np. 50ms, 100ms, 150ms - Thread.sleep). Zasymuluj scenariusz, w
         * ktorym pierwsza proba zawodzi (stan=0), a MIEDZY probami inny kod "dostawia"
         * towar (reczny UPDATE), wiec DRUGA proba sie powiedzie - wypisz numer proby,
         * ktora ostatecznie zadzialala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionalReportGeneration {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode generateOrdersReport(connection) wykonujaca W JEDNEJ,
         * READ-ONLY "transakcji" (setAutoCommit(false), same SELECT-y, zawsze
         * commit/rollback na koniec) TRZY zapytania: liczba zamowien, suma ilosci
         * zamowionych sztuk, stan magazynowy produktu #1 - i sklada je w jeden
         * sformatowany String raportu. Wywolaj ja po serii kilku zamowien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiProductOrderAllOrNothing {
        /*
         * 🧪 Zadanie 29:
         * Rozbuduj OrderService o metode placeMultiProductOrder(Map<Long, Integer>
         * productIdToQuantity) wstawiajaca JEDNO zamowienie (orders) z WIELOMA
         * pozycjami (order_items), zmniejszajaca stan KAZDEGO z produktow, w JEDNEJ
         * transakcji. Ustaw dane tak, by JEDEN z produktow mial za maly stan -
         * sprawdz, ze rollback cofa WSZYSTKIE juz przetworzone pozycje, wlacznie z
         * tymi dla produktow, ktore mialy wystarczajacy stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTransactionalOrderPipelineWithLoggingAndRetry {
        /*
         * 🧪 Zadanie 30:
         * Polacz techniki z calej lekcji w jeden pipeline: OrderService z
         * transakcyjnym placeOrder (order+order_item+stock), niezaleznym audit-logiem
         * przetrwajacym rollback (Zadanie 22), pomiarem czasu (Zadanie 20) i retry
         * (Zadanie 27). Przetestuj na 3 scenariuszach: udane zamowienie, nieudane
         * (za duza ilosc, bez retry - to blad biznesowy), oraz zamowienie, ktore
         * udaje sie dopiero przy DRUGIEJ probie (zasymuluj to np. licznikiem prob w
         * kodzie testowym, ktory "donaplenia" magazyn miedzy 1. a 2. proba).
         */
        public static void main(String[] args) { }
    }
}
