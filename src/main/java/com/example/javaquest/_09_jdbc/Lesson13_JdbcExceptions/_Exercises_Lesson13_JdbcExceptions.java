package com.example.javaquest._09_jdbc.Lesson13_JdbcExceptions;

public class _Exercises_Lesson13_JdbcExceptions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CatchSyntaxErrorException {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l13ex01;DB_CLOSE_DELAY=-1" spróbuj
         * wykonać "SELEKT * FROM books" (literówka). Złap SQLException i
         * wypisz e.getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CatchUniqueViolation {
        /*
         * 🧪 Zadanie 2:
         * Na tabeli "users" (id, email VARCHAR(150) UNIQUE) z 1 wierszem
         * spróbuj wstawić DRUGI wiersz z TYM SAMYM email. Złap
         * SQLException i wypisz getSQLState() oraz getErrorCode().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CatchNotNullViolation {
        /*
         * 🧪 Zadanie 3:
         * Na tabeli "users" (id, name VARCHAR(100) NOT NULL) spróbuj
         * wstawić wiersz BEZ podania name (INSERT INTO users (id) VALUES
         * (?)). Złap SQLException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CatchTableNotFoundError {
        /*
         * 🧪 Zadanie 4:
         * Spróbuj wykonać "SELECT * FROM nieistniejaca_tabela_xyz".
         * Złap SQLException i wypisz getMessage() oraz getSQLState().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CatchConnectionUrlError {
        /*
         * 🧪 Zadanie 5:
         * Spróbuj DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb")
         * (brak drivera PostgreSQL na classpath). Złap SQLException i
         * wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintAllThreeExceptionFields {
        /*
         * 🧪 Zadanie 6:
         * Dla dowolnego złapanego SQLException (np. naruszenie UNIQUE)
         * wypisz WSZYSTKIE trzy informacje: getMessage(), getSQLState(),
         * getErrorCode() w jednym, sformatowanym komunikacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareSqlStateAcrossErrorTypes {
        /*
         * 🧪 Zadanie 7:
         * Wywołaj DWA różne błędy (np. naruszenie UNIQUE i błąd składni
         * SQL), złap oba SQLException i wypisz ich getSQLState() jedno
         * pod drugim - porównaj (println), czy się różnią.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareErrorCodeAcrossErrorTypes {
        /*
         * 🧪 Zadanie 8:
         * Analogicznie do Zadania 7, porównaj getErrorCode() dla DWÓCH
         * różnych typów błędów (np. naruszenie NOT NULL i odwołanie do
         * nieistniejącej tabeli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExamineExceptionClassName {
        /*
         * 🧪 Zadanie 9:
         * Dla złapanego SQLException (np. naruszenie UNIQUE) wypisz
         * e.getClass().getName() - zaobserwuj, że H2 rzuca SWOJĄ,
         * konkretną podklasę SQLException (np.
         * JdbcSQLIntegrityConstraintViolationException), nie generyczny
         * SQLException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CheckedExceptionMustBeHandled {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę executeOrThrow(Connection, String sql), która
         * DEKLARUJE "throws SQLException" (nie łapie go), oraz metodę
         * executeAndCatch(Connection, String sql), która ŁAPIE
         * SQLException wewnętrznie. Wywołaj obie z poprawnym SQL i
         * wypisz, że działają identycznie dla przypadku bez błędu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ReusablePrintExceptionInfoMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę printExceptionInfo(SQLException e) (jak w
         * lekcji) i użyj jej do wypisania informacji o TRZECH różnych
         * błędach (składnia, UNIQUE, brak tabeli) - bez powtarzania kodu
         * wypisywania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ClassifyErrorBySqlStatePrefix {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę String classify(SQLException e), która na
         * podstawie PREFIKSU getSQLState() (np. "23" -> "naruszenie
         * ograniczenia", "42" -> "blad skladni SQL", inny -> "inny
         * blad") zwraca czytelną kategorię. Przetestuj na 3 różnych
         * błędach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CatchForeignKeyViolation {
        /*
         * 🧪 Zadanie 13:
         * Na tabelach "users" (id) i "orders" (id, user_id REFERENCES
         * users(id)) spróbuj wstawić zamówienie z user_id wskazującym
         * na nieistniejącego użytkownika. Złap SQLException i wypisz
         * pełne informacje (przez metodę z Zadania 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CatchCheckConstraintViolation {
        /*
         * 🧪 Zadanie 14:
         * Na tabeli "accounts" (id, balance INT CHECK (balance >= 0))
         * spróbuj wstawić wiersz z ujemnym balance. Złap SQLException i
         * wypisz pełne informacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WrapWithCauseAndPrintBoth {
        /*
         * 🧪 Zadanie 15:
         * Złap SQLException (np. z naruszenia UNIQUE) i opakuj go w
         * nowy RuntimeException("Rejestracja nie powiodla sie", e).
         * Wypisz getMessage() OBU wyjątków oraz sprawdź, że
         * outer.getCause() == e (referencja do oryginalnego SQLException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RetryOnlyForSpecificErrorCode {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę, która próbuje wykonać operację i PONAWIA próbę
         * TYLKO jeśli SQLException ma określony getErrorCode() (symulacja
         * "błędu przejściowego" - zdefiniuj sobie dowolny numer), a dla
         * INNYCH kodów błędu (np. błąd składni) rzuca dalej BEZ ponawiania.
         * Zademonstruj oba scenariusze na 2 różnych błędach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SqlStateToFriendlyDescriptionMap {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj Map<String, String> mapującą PREFIKSY sqlState (np.
         * "23", "42") na przyjazne opisy. Napisz metodę translate(SQLException),
         * która zwraca opis z mapy (albo "Nieznany blad") na podstawie
         * pierwszych 2 znaków sqlState. Przetestuj na 3 różnych błędach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BatchUpdateExceptionChaining {
        /*
         * 🧪 Zadanie 18:
         * Na tabeli "users" (id, email UNIQUE) użyj addBatch()+executeBatch()
         * z 4 wierszami, z których 1 narusza UNIQUE. Złap
         * java.sql.BatchUpdateException i wypisz getUpdateCounts()
         * (tablica int[]) oraz przejdź przez getNextException() w pętli,
         * wypisując KAŻDY połączony wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CustomCheckedWrapperException {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj własny SPRAWDZANY wyjątek DataAccessException(String
         * message, Throwable cause) extends Exception. Napisz metodę,
         * która łapie SQLException i rzuca DataAccessException z nim
         * jako cause. Zademonstruj wywołanie tej metody i wypisanie
         * getMessage() oraz getCause().getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_OriginalExceptionNotMaskedByResourceClose {
        /*
         * 🧪 Zadanie 20:
         * Wykonaj operację powodującą SQLException (np. błąd składni)
         * WEWNĄTRZ try-with-resources z Connection+Statement. Złap
         * wyjątek POZA blokiem i wypisz jego komunikat - potwierdź, że
         * to WCIĄŻ oryginalny błąd składni (a nie coś związanego z
         * zamykaniem zasobów).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DispatchHandlerBySqlStateCategory {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę handleSqlException(SQLException e), która na
         * podstawie klasyfikacji z Zadania 12 wykonuje RÓŻNE akcje: dla
         * "naruszenie ograniczenia" - wypisuje przyjazny komunikat
         * biznesowy; dla "blad skladni SQL" - wypisuje komunikat + loguje
         * jako błąd programisty (do poprawy w kodzie); dla innych -
         * rzuca dalej jako RuntimeException. Przetestuj na 3 różnych
         * błędach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullBatchUpdateExceptionAnalysis {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz Zadanie 18: dla paczki 5 insertów (2 naruszające
         * UNIQUE) po złapaniu BatchUpdateException przeanalizuj
         * getUpdateCounts() - wypisz, które indeksy w tablicy mają
         * Statement.EXECUTE_FAILED, a które mają wartość >= 0 (sukces).
         * Podsumuj: "X z Y operacji w paczce powiodlo sie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CustomExceptionHierarchyFromSqlState {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj hierarchię: DataAccessException (bazowy, checked),
         * DuplicateEmailException extends DataAccessException,
         * RecordNotFoundException extends DataAccessException. Napisz
         * metodę repozytorium insertUser(...), która łapie SQLException i
         * na podstawie sqlState rzuca ODPOWIEDNIĄ konkretną podklasę.
         * Przetestuj wywołując z duplikatem email i sprawdź, że kod
         * wołający może złapać KONKRETNIE DuplicateEmailException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExceptionTranslatorLayer {
        /*
         * 🧪 Zadanie 24:
         * Napisz samodzielną metodę translateException(SQLException e)
         * zwracającą odpowiedni obiekt z hierarchii z Zadania 23 (bez
         * rzucania - tylko tworzącą i zwracającą obiekt wyjątku do
         * ewentualnego rzucenia przez wołający kod). Przetestuj dla 2
         * różnych błędów SQL, wypisując zwrócony typ klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PrintFullExceptionCauseChain {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj WIELOPOZIOMOWY wyjątek: SQLException opakowany w
         * DataAccessException, opakowany w kolejny, ogólny
         * RuntimeException("Rejestracja nieudana"). Napisz metodę
         * printCauseChain(Throwable t), która wypisuje KAŻDY poziom
         * (getMessage() + getClass().getSimpleName()) przechodząc przez
         * getCause() aż do null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PartialSuccessReportOnLoopInserts {
        /*
         * 🧪 Zadanie 26:
         * W pętli wstaw 10 wierszy (kilka z celowo zduplikowanym email,
         * naruszającym UNIQUE), łapiąc SQLException dla KAŻDEGO
         * insertu OSOBNO (bez przerywania pętli) i zbierając wynik
         * (sukces/porażka + komunikat) do listy. Po pętli wypisz raport:
         * ile insertów się powiodło, ile nie, z komunikatami błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SqlStatePortableAcrossDatabasesDemo {
        /*
         * 🧪 Zadanie 27:
         * Wywołaj naruszenie UNIQUE na H2 i wypisz getSQLState() - powinno
         * zaczynać się od "23" (standard SQL dla naruszeń integralności,
         * WSPÓLNY dla większości baz danych). Wypisz (println) komentarz
         * wyjaśniający, że getErrorCode() BYŁBY inny w PostgreSQL/MySQL
         * dla TEGO SAMEGO logicznego błędu, a getSQLState() zwykle nie -
         * to dlaczego sqlState jest bardziej "przenośny" między bazami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RetryFrameworkWithClassification {
        /*
         * 🧪 Zadanie 28:
         * Napisz generyczną metodę executeWithRetry(Supplier<Integer>
         * action, int maxAttempts), która łapie SQLException, klasyfikuje
         * go (metoda z Zadania 12) i PONAWIA próbę TYLKO dla kategorii
         * "naruszenie ograniczenia" (symulacja - w realnym świecie
         * raczej retry dla błędów połączenia/timeoutu, nie constraint,
         * ale tu chodzi o mechanikę), a dla "blad skladni SQL" rzuca
         * OD RAZU, bez ponawiania (błąd programisty się nie "naprawi"
         * sam). Zademonstruj obie ścieżki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManualCompensationAfterMidSequenceFailure {
        /*
         * 🧪 Zadanie 29:
         * Wstaw zamówienie (orders), a potem spróbuj wstawić pozycję
         * (order_items) z celowo błędnymi danymi (np. NOT NULL violation
         * na wymaganej kolumnie). Złap SQLException, wypisz jego pełne
         * informacje, a następnie RĘCZNIE "skompensuj" operację -
         * usuń (DELETE) wcześniej wstawione zamówienie, żeby nie
         * zostawić "osieroconych" danych (BEZ używania transakcji -
         * ten temat to Lesson15). Zweryfikuj SELECT COUNT(*), że
         * zamówienie zostało usunięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneErrorHandlingDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę ErrorHandlingDemo łączącą: printExceptionInfo
         * (Zadanie 11), classify (Zadanie 12), tłumaczenie na hierarchię
         * własnych wyjątków (Zadanie 23/24), analizę BatchUpdateException
         * (Zadanie 22) oraz retry z klasyfikacją (Zadanie 28). Przepuść
         * przez nią 5 różnych scenariuszy błędów (składnia, UNIQUE, NOT
         * NULL, FK, brak tabeli) i wypisz finalny raport: dla każdego -
         * kategorię, sqlState, errorCode i to, czy błąd został
         * "obsłużony" (przyjazny komunikat) czy "nieobsłużony"
         * (przekazany dalej).
         */
        public static void main(String[] args) { }
    }
}
