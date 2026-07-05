package com.example.javaquest._09_jdbc.Lesson12_TryWithResourcesInJdbc;

public class _Exercises_Lesson12_TryWithResourcesInJdbc {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ThreeResourcesInOneTryBlock {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l12ex01;DB_CLOSE_DELAY=-1" z tabelą
         * "books" (id, title) i 3 wierszami, napisz JEDEN blok try (...)
         * deklarujący Connection, PreparedStatement i ResultSet naraz
         * (rozdzielone średnikami), wypisując wszystkie wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PreparedStatementInsteadOfStatement {
        /*
         * 🧪 Zadanie 2:
         * Powtórz Zadanie 1, ale użyj PreparedStatement z parametrem
         * "WHERE id = ?" zamiast zwykłego Statement bez parametrów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyIsClosedAfterBlock {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj Connection i Statement w try-with-resources (bez
         * ResultSet), zapamiętaj referencje do OBU obiektów, a PO wyjściu
         * z bloku wypisz connection.isClosed() i statement.isClosed() -
         * oba powinny być true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LoggingWrapperShowsCloseOrder {
        /*
         * 🧪 Zadanie 4:
         * Napisz prostą klasę LoggingResource implementującą
         * AutoCloseable, przyjmującą nazwę w konstruktorze i wypisującą
         * "Zamykam: " + nazwa w metodzie close(). Zadeklaruj TRZY takie
         * obiekty ("A", "B", "C") w jednym try-with-resources i
         * zaobserwuj (println) kolejność zamykania - powinna być C, B, A
         * (odwrotna do deklaracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SingleConnectionResourceOnly {
        /*
         * 🧪 Zadanie 5:
         * Otwórz TYLKO Connection w try-with-resources (bez Statement/
         * ResultSet) do "jdbc:h2:mem:l12ex05;DB_CLOSE_DELAY=-1", wypisz
         * jego URL wewnątrz bloku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoResourcesConnectionAndStatement {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj Connection i Statement (bez ResultSet) w
         * try-with-resources i wykonaj przez Statement proste
         * "CREATE TABLE" oraz "INSERT" (executeUpdate), bez potrzeby
         * osobnego ResultSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseResourceInsideBlockThenVerifyClosed {
        /*
         * 🧪 Zadanie 7:
         * Wewnątrz try-with-resources z Connection+Statement+ResultSet
         * wypisz wynik zapytania, a PO bloku (poza nim) spróbuj wywołać
         * resultSet.next() - złap SQLException i wypisz komunikat,
         * potwierdzający, że zasób jest już niedostępny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ManualFinallyVsTryWithResourcesComparison {
        /*
         * 🧪 Zadanie 8:
         * Napisz TĘ SAMĄ operację (insert + select jednego wiersza)
         * DWOMA sposobami: (a) ręcznie, z Connection/Statement/ResultSet
         * zainicjowanymi na null i zamykanymi w bloku finally z
         * warunkami != null, (b) przez try-with-resources. Porównaj
         * (println) liczbę linii kodu / złożoność obu podejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExceptionInsideBlockStillClosesResources {
        /*
         * 🧪 Zadanie 9:
         * Wewnątrz try-with-resources (Connection+Statement) wykonaj
         * operację, która celowo rzuci wyjątek (np. dzielenie przez
         * zero w Javie, PO wykonaniu zapytania). Złap wyjątek POZA
         * blokiem i wypisz connection.isClosed() - powinno być true,
         * mimo wyjątku wewnątrz bloku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CustomAutoCloseableAlongsideJdbcResources {
        /*
         * 🧪 Zadanie 10:
         * Napisz prostą klasę SimpleLogger implementującą AutoCloseable
         * (close() wypisuje "Logger zamkniety"). Zadeklaruj ją RAZEM z
         * Connection w jednym try-with-resources (Connection; SimpleLogger)
         * i zaobserwuj, że oba zasoby zostają zamknięte po zakończeniu bloku.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullLoggingWrapperTripleChain {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz LoggingResource z Zadania 4 na TRZY klasy:
         * LoggingConnection, LoggingStatement, LoggingResultSet (każda
         * opakowująca prawdziwy odpowiednik JDBC przez delegację, z
         * close() logującym nazwę klasy). Zadeklaruj wszystkie trzy w
         * JEDNYM try-with-resources i wypisz DOKŁADNĄ kolejność
         * zamykania (powinna być: ResultSet, Statement, Connection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoIndependentResultSetsFromSameStatement {
        /*
         * 🧪 Zadanie 12:
         * Na TYM SAMYM, już otwartym Connection wykonaj DWA NIEZALEŻNE
         * zapytania, każde we WŁASNYM try-with-resources zawierającym
         * Statement+ResultSet (dwa osobne obiekty Statement). Zweryfikuj,
         * że oba działają poprawnie i niezależnie od siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LayeredResourceManagement {
        /*
         * 🧪 Zadanie 13:
         * Otwórz Connection w JEDNYM (zewnętrznym) try-with-resources, a
         * wewnątrz niego wywołaj DWIE metody, każda otwierająca WŁASNY
         * Statement+ResultSet we własnym try-with-resources (ale
         * korzystająca z tego samego, przekazanego Connection). Pokaż,
         * że Connection zostaje otwarte tylko raz, a Statement/ResultSet
         * - per operacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManualCodeLeaksConnectionOnException {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę BEZ try-with-resources, która otwiera Connection,
         * wykonuje operację rzucającą wyjątek PRZED wywołaniem
         * connection.close() (i bez finally!). Złap wyjątek na zewnątrz
         * i wypisz connection.isClosed() - powinno być FALSE (zasób
         * "wyciekł"). Skomentuj, dlaczego to jest niebezpieczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SequentialTryWithResourcesBlocksSharedConnection {
        /*
         * 🧪 Zadanie 15:
         * Otwórz JEDNO Connection (poza try-with-resources, zamykane
         * ręcznie na końcu), a potem wykonaj TRZY OSOBNE operacje, każda
         * we własnym try-with-resources na Statement+ResultSet (insert,
         * update, select) - wszystkie na tym samym połączeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SuppressedExceptionFromFailingClose {
        /*
         * 🧪 Zadanie 16:
         * Napisz klasę FailingCloseable implementującą AutoCloseable,
         * której close() ZAWSZE rzuca RuntimeException("Blad zamykania").
         * W try-with-resources z tym zasobem wykonaj operację, która
         * RÓWNIEŻ rzuci wyjątek (np. dzielenie przez 0). Złap
         * "główny" wyjątek na zewnątrz i wypisz e.getSuppressed() -
         * powinien zawierać wyjątek z close().
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FlatVsNestedTryWithResourcesStyle {
        /*
         * 🧪 Zadanie 17:
         * Wykonaj TĘ SAMĄ operację (select z tabeli) DWOMA stylami: (a)
         * "płaski" - Connection+Statement+ResultSet w JEDNYM try (...),
         * (b) "zagnieżdżony" - Connection w jednym try, a wewnątrz
         * niego osobny try (...) na Statement+ResultSet. Wypisz wynik
         * obu podejść i skomentuj różnicę stylistyczną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PooledConnectionLogicalCloseSimulation {
        /*
         * 🧪 Zadanie 18:
         * Napisz klasę PooledConnection implementującą AutoCloseable,
         * przechowującą prawdziwe Connection i referencję do "puli"
         * (np. List<Connection>) - jej close() NIE zamyka fizycznie
         * połączenia, tylko ZWRACA je do puli (dodaje z powrotem do
         * listy). Użyj jej w try-with-resources i zweryfikuj po
         * zakończeniu bloku, że prawdziwe Connection jest WCIĄŻ otwarte
         * (isClosed() == false), a lista puli znowu ma ten element.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DoubleCloseInsideTryWithResourcesIsSafe {
        /*
         * 🧪 Zadanie 19:
         * Wewnątrz try-with-resources z Connection, wywołaj RĘCZNIE
         * connection.close() JUŻ w środku bloku (przed jego naturalnym
         * zakończeniem). Wypisz komunikat potwierdzający, że automatyczne
         * zamknięcie na końcu bloku (drugie close()) NIE rzuca wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExecuteQueryWithRowHandlerCallback {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj funkcyjny interfejs RowHandler { void handle(ResultSet
         * rs) throws SQLException; }. Napisz metodę executeQuery(Connection
         * conn, String sql, RowHandler handler), która WEWNĘTRZNIE
         * otwiera Statement+ResultSet w try-with-resources i dla KAŻDEGO
         * wiersza woła handler.handle(rs) - kod wywołujący nie musi
         * zarządzać zasobami. Przetestuj przekazując lambda wypisującą
         * wiersz.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FourLevelResourceChainWithCustomResource {
        /*
         * 🧪 Zadanie 21:
         * Napisz klasę ReportWriter implementującą AutoCloseable (jej
         * close() wypisuje "Raport zamkniety"). Zadeklaruj w JEDNYM
         * try-with-resources CZTERY zasoby: Connection, PreparedStatement,
         * ResultSet, ReportWriter - i zweryfikuj (przez logowanie w każdej
         * klasie z Zadania 11 + ReportWriter), że zamykanie odbywa się w
         * DOKŁADNIE odwrotnej kolejności deklaracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_EarlierResourceClosedWhenLaterAcquisitionFails {
        /*
         * 🧪 Zadanie 22:
         * Użyj LoggingConnection z Zadania 11. W try-with-resources
         * zadeklaruj LoggingConnection, a jako DRUGI zasób spróbuj
         * przygotować PreparedStatement z CELOWO niepoprawnym SQL (np.
         * "SELEKT * FORM x") - operacja prepareStatement rzuci
         * SQLException PODCZAS deklaracji zasobów. Złap wyjątek na
         * zewnątrz i wypisz log z LoggingConnection - potwierdzenie, że
         * PIERWSZY, już otwarty zasób (Connection) i tak został zamknięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ManualEquivalentOfTryWithResources {
        /*
         * 🧪 Zadanie 23:
         * Napisz RĘCZNĄ implementację (bez try-with-resources) w pełni
         * odpowiadającą gwarancjom try-with-resources dla DWÓCH zasobów
         * (Statement, ResultSet): zagnieżdżone try/finally z osobnym
         * try/catch na close() każdego zasobu oraz zbieraniem
         * potencjalnych wyjątków z close() jako suppressed na główny
         * wyjątek (addSuppressed). Porównaj (println) złożoność z
         * odpowiadającym blokiem try-with-resources.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_LeakyMethodExhaustsFixedPool {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj mały, RĘCZNY "connection pool" o rozmiarze 3 (lista
         * połączeń). Napisz "wadliwą" metodę leakyOperation(pool), która
         * "pożycza" połączenie z puli, ale NIGDY go nie oddaje (brak
         * try-with-resources/finally). Wywołaj ją 3 razy - po trzecim
         * wywołaniu pula jest PUSTA; czwarte wywołanie powinno rzucić
         * wyjątek/zwrócić błąd "brak wolnych połączeń". Wypisz stan puli
         * po każdym wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FixedLeakWithProperResourceManagement {
        /*
         * 🧪 Zadanie 25:
         * Napraw metodę z Zadania 24: safeOperation(pool) POŻYCZA
         * połączenie i ZAWSZE je oddaje (finally albo try-with-resources
         * na wrapperze z Zadania 18). Wywołaj ją 10 razy pod rząd na
         * puli o rozmiarze 3 i wypisz, że pula NIGDY się nie wyczerpuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WithConnectionHelperHidesResourceManagement {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj funkcyjny interfejs ConnectionAction { void run(Connection
         * conn) throws SQLException; }. Napisz metodę withConnection(String
         * url, ConnectionAction action), która otwiera Connection w
         * try-with-resources i przekazuje je do action.run(). Przetestuj
         * ją z TRZEMA różnymi lambdami (create table, insert, select) -
         * kod wywołujący nigdy nie zamyka Connection ręcznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BatchProcessingWithLayeredNestedResources {
        /*
         * 🧪 Zadanie 27:
         * W JEDNYM try-with-resources (Connection + PreparedStatement do
         * insertów) wstaw 500 wierszy w pętli, a co 100 wierszy wykonaj
         * WEWNĄTRZ tej samej pętli osobny, ZAGNIEŻDŻONY try-with-resources
         * na Statement+ResultSet weryfikujący aktualny COUNT(*) - pokaż,
         * że zagnieżdżone zasoby otwierają się i zamykają wielokrotnie
         * bez wpływu na zewnętrzny PreparedStatement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FiveResourceDeclarationTwoIndependentQueries {
        /*
         * 🧪 Zadanie 28:
         * Zadeklaruj w JEDNYM try-with-resources PIĘĆ zasobów: Connection,
         * Statement1, ResultSet1 (jedno zapytanie), Statement2, ResultSet2
         * (inne, niezależne zapytanie na tym samym Connection). Wypisz
         * wyniki obu zapytań i potwierdź (przez LoggingResource z Zadania
         * 11 albo komentarz), że wszystkie 5 zasobów zamykają się w
         * odwrotnej kolejności deklaracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GuaranteedCloseEvenOnRuntimeExceptionInLambda {
        /*
         * 🧪 Zadanie 29:
         * Użyj withConnection z Zadania 26, przekazując lambdę, która
         * CELOWO rzuca RuntimeException PO wykonaniu jakiejś operacji SQL.
         * Złap wyjątek na zewnątrz withConnection i (przez LoggingConnection
         * z Zadania 11 użytą wewnątrz withConnection) potwierdź, że
         * Connection ZOSTAŁO zamknięte, mimo wyjątku w środku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneBookCatalogServiceNoLeaks {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę BookCatalogService z metodami insert(title),
         * findById(id), listAll() - KAŻDA metoda otwiera WYŁĄCZNIE
         * zasoby, których faktycznie potrzebuje (żadna nie zostawia
         * niczego otwartego), z pomocą LoggingStatement/LoggingResultSet
         * z Zadania 11 zliczających łączną liczbę otwarć i zamknięć.
         * Wywołaj wszystkie 3 metody kilka razy i na końcu wypisz raport
         * potwierdzający, że liczba otwarć KAŻDEGO typu zasobu równa się
         * liczbie jego zamknięć (brak wycieków).
         */
        public static void main(String[] args) { }
    }
}
