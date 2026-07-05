package com.example.javaquest._10_dao.Lesson20_ErrorHandlingAcrossLayers;

public class _Exercises_Lesson20_ErrorHandlingAcrossLayers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DataAccessExceptionBasics {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj wlasny, unchecked wyjatek DataAccessException(String message,
         * SQLException cause) (jak w lekcji, extends RuntimeException). Napisz
         * metode, ktora recznie tworzy SQLException("test") i opakowuje go w
         * DataAccessException - wypisz getMessage() oraz getCause().getClass().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DaoCatchesSqlExceptionAndWraps {
        /*
         * 🧪 Zadanie 2:
         * Na bazie "jdbc:h2:mem:lesson20ex02;DB_CLOSE_DELAY=-1" z tabela products
         * (jak w lekcji), napisz ProductDao.findNameById(long id) LAPIACE
         * SQLException i rzucajace DataAccessException (metoda NIE deklaruje
         * "throws SQLException"). Wywolaj ja dla istniejacego i nieistniejacego id -
         * pierwsze zwraca Optional z nazwa, drugie Optional.empty() (brak wiersza to
         * NIE jest blad techniczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ProductNotFoundExceptionAsBusinessError {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj ProductNotFoundException(long productId) (jak w lekcji, wlasny
         * wyjatek domenowy). Napisz ProductService.buyProduct(productId) wywolujace
         * ProductDao.findNameById i rzucajace ProductNotFoundException, jesli
         * Optional jest pusty. Przetestuj na id istniejacym i nieistniejacym,
         * lapiac ProductNotFoundException w main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunBrokenQueryDemonstratesTechnicalError {
        /*
         * 🧪 Zadanie 4:
         * Napisz ProductDao.runBrokenQuery() wykonujace CELOWO zla skladnie SQL
         * (SELECT * FROM tabela_ktora_nie_istnieje), lapiace SQLException i
         * rzucajace DataAccessException. Wywolaj ja w main, zlap DataAccessException
         * i wypisz getMessage() oraz getCause().getClass().getSimpleName() -
         * pokazujac, ze oryginalny SQLException jest zachowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ControllerNeverImportsSqlException {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode "kontrolera" handleBuyRequest(ProductService service, long
         * productId) wywolujaca service.buyProduct i lapiaca WYLACZNIE
         * ProductNotFoundException (bez importu java.sql.SQLException w tej metodzie
         * - sprawdz, ze plik faktycznie kompiluje sie bez tego importu w tej
         * metodzie). Przetestuj dla obu przypadkow (produkt istnieje/nie istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoExceptionTypesCaughtSeparately {
        /*
         * 🧪 Zadanie 6:
         * W main przetestuj 3 sytuacje LAPIAC KAZDA OSOBNYM catch: (1) produkt
         * istnieje - sukces, (2) produkt nie istnieje - ProductNotFoundException,
         * (3) wywolanie runBrokenQuery - DataAccessException. Wypisz dla kazdej rozny
         * komunikat, podkreslajacy ROZNICE miedzy bledem biznesowym a technicznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DataAccessExceptionPreservesOriginalMessage {
        /*
         * 🧪 Zadanie 7:
         * Rozbuduj DataAccessException, by jego konstruktor budowal komunikat w
         * formacie "<message> (SQLState: <state>)" gdzie <state> pochodzi z
         * cause.getSQLState(). Zademonstruj to na przykladzie zlego zapytania z
         * Zadania 4 - wypisz pelny komunikat wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleDaoMethodsAllWrapSqlException {
        /*
         * 🧪 Zadanie 8:
         * Rozbuduj ProductDao o metode void insert(String name) TAKZE lapiaca
         * SQLException i rzucajaca DataAccessException (bez "throws SQLException").
         * Wywolaj insert dla poprawnej nazwy (sukces) i dla nazwy null (naruszy NOT
         * NULL w bazie, powodujac DataAccessException) - zlap i wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ServiceDoesNotCatchDataAccessException {
        /*
         * 🧪 Zadanie 9:
         * Potwierdz zasada z lekcji: ProductService.buyProduct NIE lapie
         * DataAccessException (propaguje go dalej bez zmian). Wywolaj scenariusz, w
         * ktorym DAO rzuca DataAccessException (np. przez runBrokenQuery wywolane z
         * poziomu metody biznesowej), i sprawdz, ze wyjatek "przecieka" przez Service
         * i musi byc zlapany w main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareCheckedVsUncheckedPropagation {
        /*
         * 🧪 Zadanie 10:
         * Napisz DWIE wersje tej samej metody findProductNameOrThrow: jedna
         * deklarujaca "throws SQLException" (checked, bez opakowania), druga
         * korzystajaca z DataAccessException (unchecked). Napisz metode WYWOLUJACA
         * obie i skomentuj w kodzie, ktora wersja WYMUSZA na wywolujacym try-catch
         * albo throws, a ktora nie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_HierarchyOfCustomExceptions {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj mala hierarchie wyjatkow: BusinessException (RuntimeException,
         * bazowy), ProductNotFoundException extends BusinessException,
         * InsufficientStockException extends BusinessException. Napisz Service z
         * dwoma metodami rzucajacymi kazdy z tych wyjatkow i JEDNYM blokiem catch
         * (BusinessException e) lapiacym OBA typy naraz w main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ServiceTranslatesUniqueViolationToBusinessException {
        /*
         * 🧪 Zadanie 12:
         * Dodaj tabele users (email VARCHAR UNIQUE). Napisz UserDao.insert LAPIACE
         * SQLException - jesli e.getSQLState() to "23505" (naruszenie UNIQUE),
         * rzuca wlasny DuplicateEmailException(String email), w przeciwnym razie
         * DataAccessException (jak w lekcji, ale z rozgraniczeniem TYPU bledu SQL).
         * Przetestuj insert tego samego e-maila dwukrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LoggingTechnicalErrorsDifferentlyThanBusiness {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode handleRequest(ProductService service, long productId)
         * lapiaca ProductNotFoundException (loguje jako "[INFO] rzecz oczekiwana:
         * ...") ORAZ DataAccessException (loguje jako "[ERROR] problem techniczny:
         * ..." plus stack trace przyczyny) - w DWOCH ROZNYCH blokach catch, z rozna
         * "waga" logu. Przetestuj obie sciezki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RethrowingWithAdditionalContext {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj OrderService (nowa mini-klasa) z metoda placeOrder(orderId,
         * productId) LAPIACA DataAccessException z DAO i RZUCAJACA NOWY
         * DataAccessException z DODATKOWYM kontekstem w komunikacie (np. "Nie udalo
         * sie zlozyc zamowienia dla produktu <id>: " + oryginalny komunikat), z
         * zachowaniem oryginalnego jako cause. Wypisz pelny lancuch przyczyn
         * (getCause().getCause()...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidationExceptionSeparateFromDataAccessException {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj ValidationException(String field, String reason) (osobny typ, nie
         * dziedziczy po DataAccessException). Napisz Service.registerProduct(name,
         * price) rzucajace ValidationException dla name pustego/price<=0 PRZED
         * jakimkolwiek dostepem do DAO, oraz DataAccessException gdyby cos posz≥lo
         * nie tak w DAO. Przetestuj oba typy bledow osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CatchingMultipleExceptionTypesInOneBlock {
        /*
         * 🧪 Zadanie 16:
         * Uzywajac wyjatkow z Zadania 15, napisz handler lapiacy
         * "ValidationException | ProductNotFoundException e" w JEDNYM multi-catch
         * bloku (obie sa bledami "oczekiwanymi") ORAZ ODDZIELNIE
         * "DataAccessException e" (blad techniczny) - przetestuj na 3 przypadkach,
         * jeden na kazdy typ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SqlExceptionNeverLeaksOutsideDao {
        /*
         * 🧪 Zadanie 17:
         * Napisz test (prosty main z if/throw AssertionError) sprawdzajacy, ze
         * metoda ProductDao.findNameById NIE deklaruje "throws SQLException" (mozesz
         * to zweryfikowac przez refleksje - getDeclaredMethod(...).getExceptionTypes()
         * - albo w komentarzu wyjasnij, jak to sprawdzasz statycznie). Wypisz wynik
         * weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ErrorCodeEnumForBusinessExceptions {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj enum ErrorCode {PRODUCT_NOT_FOUND, INSUFFICIENT_STOCK,
         * DUPLICATE_EMAIL, TECHNICAL_ERROR}. Rozbuduj wlasne wyjatki, by KAZDY mial
         * pole ErrorCode code (ustawiane w konstruktorze). Napisz handler
         * mapujacy zlapany wyjatek na kod bledu i wypisujacy "[<code>] <message>" -
         * przetestuj dla 3 roznych wyjatkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WrappingCheckedExceptionFromExternalApiSimulation {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode simulateExternalPaymentCall() DEKLARUJACA "throws
         * java.io.IOException" (checked, symulujaca zewnetrzne API platnosci - bez
         * realnego IO, po prostu rzuc IOException recznie w wybranym przypadku).
         * Napisz PaymentService.processPayment() lapiace ten IOException i
         * opakowujace go w wlasny, unchecked PaymentException - pokaz, ze wyzsze
         * warstwy nie musza znac IOException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullBuyProductFlowWithAllExceptionTypes {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj kompletny przepyw buyProduct: walidacja (ValidationException),
         * sprawdzenie istnienia (ProductNotFoundException), sprawdzenie stanu
         * (InsufficientStockException), i sam zakup (DataAccessException w razie
         * bledu SQL). Wykonaj 5 wywolan, kazde naruszajace INNY typ bledu (plus
         * jedno poprawne) i wypisz, KTORY typ wyjatku zostal zlapany dla kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GlobalExceptionHandlerPattern {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase GlobalExceptionHandler z metoda String handle(Exception e)
         * mapujaca KAZDY znany typ wyjatku (ValidationException,
         * ProductNotFoundException, InsufficientStockException,
         * DataAccessException, oraz "wszystko inne") na czytelny komunikat +
         * "kod statusu" (np. jak HTTP: 400/404/409/500). Przetestuj na liscie 5
         * roznych wyjatkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExceptionTranslationLayerForMultipleSqlStates {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj DAO o metode prywatna RuntimeException translate(SQLException e) -
         * mapujaca ROZNE SQLState (np. "23505" UNIQUE, "23514" CHECK) na ROZNE, wlasne
         * wyjatki biznesowe, a wszystko inne na generyczny DataAccessException.
         * Zademonstruj na 3 roznych operacjach powodujacych 3 rozne naruszenia
         * (UNIQUE, CHECK, i zla skladnia SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RetryOnlyForTechnicalErrorsNotBusinessErrors {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode executeWithSelectiveRetry(Supplier<T> action, int maxAttempts)
         * ktora RETRY-UJE TYLKO gdy zlapany wyjatek to DataAccessException (blad
         * techniczny - moze byc "przejsciowy"), ale natychmiast propaguje dalej
         * (bez retry) kazdy wyjatek biznesowy (np. ProductNotFoundException).
         * Zademonstruj na 2 scenariuszach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AggregatingMultipleValidationErrorsIntoOneException {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj MultiValidationException(List<String> errors) zbierajaca WIELE
         * bledow walidacji naraz (nie tylko pierwszy). Napisz
         * Service.registerProduct sprawdzajace WSZYSTKIE reguly (nazwa, cena, opis)
         * i - jesli KTORAKOLWIEK zawiedzie - rzucajace JEDEN MultiValidationException
         * z LISTA wszystkich naruszonych regul. Przetestuj na produkcie naruszajacym
         * 3 reguly naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExceptionChainWithFullStackTraceInspection {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj scenariusz z TRZEMA poziomami opakowania: SQLException ->
         * DataAccessException -> wlasny OrderProcessingException (kazdy z cause
         * ustawionym na poprzedni). Napisz metode printFullExceptionChain(Throwable t)
         * wypisujaca KAZDY poziom lancucha (typ + komunikat) przechodzac przez
         * getCause() az do null. Zademonstruj na przykladzie z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DistinguishingRecoverableFromNonRecoverableErrors {
        /*
         * 🧪 Zadanie 26:
         * Dodaj do DataAccessException pole boolean recoverable (ustawiane w
         * konstruktorze) - np. blad polaczenia = recoverable=true (mozna
         * sprobowac ponownie), naruszenie CHECK = recoverable=false (dane sa zle,
         * retry nic nie zmieni). Napisz handler decydujacy o retry TYLKO na
         * podstawie tej flagi. Przetestuj na 2 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ServiceLayerCircuitBreakerSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj prosty licznik "kolejnych bledow technicznych" w Service -
         * jesli 3 wywolania Z RZEDU zakoncza sie DataAccessException, KOLEJNE
         * wywolanie ma zostac odrzucone BEZ nawet próby kontaktu z DAO (wlasny
         * ServiceUnavailableException) - to uproszczona wersja wzorca "circuit
         * breaker". Zademonstruj na sekwencji 5 wywolan, gdzie pierwsze 3 zawodza
         * technicznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullApplicationErrorReportAcrossManyOperations {
        /*
         * 🧪 Zadanie 28:
         * Wykonaj 15 operacji buyProduct z roznymi danymi wejsciowymi (mieszanka
         * sukcesow i wszystkich typow bledow z tej lekcji). Zbierz kazdy wynik do
         * struktury (np. Map<ErrorCode, Integer> licznikow) i wypisz koncowy
         * raport: liczba sukcesow oraz liczba wystapien KAZDEGO typu bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestingExceptionHandlingWithMiniTestRunner {
        /*
         * 🧪 Zadanie 29:
         * Napisz mini test-runner (jak w Lesson26_TestingDao: metody testowe rzucajace
         * AssertionError, runner lapiacy je i raportujacy PASSED/FAILED) z co
         * najmniej 5 testami weryfikujacymi zachowanie wyjatkow z tej lekcji (np.
         * "buyProduct dla nieistniejacego produktu rzuca ProductNotFoundException",
         * "SQLException NIGDY nie wycieka z DAO" itp.). Wypisz raport koncowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLayeredErrorHandlingMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletna, mala aplikacje: DAO (opakowuje SQLException w
         * DataAccessException), Service (rzuca wlasne wyjatki biznesowe:
         * ProductNotFoundException, InsufficientStockException,
         * DuplicateEmailException, ValidationException) oraz GlobalExceptionHandler
         * (Zadanie 21) uzywany jako jedyny punkt lapiacy wyjatki w "kontrolerze".
         * Zasymuluj 10 requestow o roznych wynikach i wypisz kompletny raport
         * odpowiedzi (komunikat + kod statusu) dla kazdego.
         */
        public static void main(String[] args) { }
    }
}
