package com.example.javaquest._10_dao.Lesson04_JdbcDaoImplementation;

public class _Exercises_Lesson04_JdbcDaoImplementation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SaveAndMapRowForUsers {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l04ex01;DB_CLOSE_DELAY=-1" z tabelą users (id, email,
         * first_name), zaimplementuj UserJdbcDao z metodami save(User) (zwracającą
         * obiekt z wygenerowanym id, Statement.RETURN_GENERATED_KEYS) oraz prywatną
         * mapRow(ResultSet) do zamiany wiersza na User. Zapisz 3 użytkowników i
         * wypisz każdego z wygenerowanym id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindByIdUsingMapRow {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj UserJdbcDao z Zadania 1 o findById(long id) zwracającą
         * Optional<User>, korzystającą z TEJ SAMEJ metody mapRow (bez powielania
         * logiki mapowania). Sprawdź dla istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FindAllUsingMapRow {
        /*
         * 🧪 Zadanie 3:
         * Rozbuduj UserJdbcDao o findAll() zwracającą List<User>, budowaną w pętli
         * while(rs.next()) z wywołaniem mapRow() dla każdego wiersza. Zapisz 4
         * użytkowników i wypisz wynik findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProductJdbcDaoSameTemplate {
        /*
         * 🧪 Zadanie 4:
         * Na tej samej bazie, dla tabeli products (id, name, price DECIMAL(10,2)),
         * napisz ProductJdbcDao WEDŁUG TEGO SAMEGO WZORCA co UserJdbcDao (save,
         * findById, findAll, mapRow). Zapisz 3 produkty i wypisz findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryWithResourcesForEveryStatement {
        /*
         * 🧪 Zadanie 5:
         * Przepisz metodę save(User) tak, by KAŻDY PreparedStatement i ResultSet
         * (w tym ResultSet z getGeneratedKeys()) był otwierany w try-with-resources.
         * W komentarzu wyjaśnij, co by się stało (wyciek zasobów), gdyby try-with-
         * resources pominąć dla getGeneratedKeys().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WrapSqlExceptionInRuntimeException {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj findById tak, by SQLException był łapany i opakowywany w
         * RuntimeException z czytelnym komunikatem po polsku (np. "Blad podczas
         * wyszukiwania uzytkownika"). Zasymuluj błąd, zapytując o nieistniejącą
         * kolumnę, i sprawdź treść komunikatu wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapRowReusedInThreeMethods {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj BookJdbcDao (tabela books: id, title, author) z TRZEMA
         * metodami korzystającymi z JEDNEJ metody mapRow: save, findById, findAll.
         * Zapisz 3 książki i wywołaj wszystkie trzy metody, pokazując, że mapRow
         * jest wywoływane wewnątrz każdej z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GeneratedKeysForMultipleInserts {
        /*
         * 🧪 Zadanie 8:
         * Wstaw 5 produktów przez ProductJdbcDao.save() w pętli i zbierz ich
         * wygenerowane id do List<Long>. Sprawdź, że id są rosnące i unikalne
         * (np. przez porównanie z sortowaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FindAllOrderByColumn {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj UserJdbcDao.findAllSortedByEmail() zwracającą List<User>
         * sortowaną po e-mailu (ORDER BY email w SQL, nie w Javie). Zapisz 4
         * użytkowników w "losowej" kolejności e-maili i sprawdź, że wynik jest
         * alfabetyczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareTwoDaosStructure {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj OBA DAO (UserJdbcDao i ProductJdbcDao) w jednym main i w
         * komentarzu wypisz listę podobieństw strukturalnych (te samo metody:
         * save/findById/findAll/mapRow) oraz różnic (inne kolumny, inne typy pól)
         * między nimi.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MapRowHandlesNullableColumn {
        /*
         * 🧪 Zadanie 11:
         * Tabela products z NULLABLE kolumną description VARCHAR(300). Napisz
         * mapRow obsługujące NULL w tej kolumnie poprawnie (rs.getString zwraca
         * null, gdy wartość w bazie jest NULL - Product.description może być null).
         * Zapisz produkt z opisem i bez, wypisz obie sytuacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UpdateMethodFollowingSameTemplate {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj UserJdbcDao o update(User user) w tym samym stylu co save/findById
         * (PreparedStatement, try-with-resources, RuntimeException przy błędzie).
         * Zaktualizuj e-mail istniejącego użytkownika i zweryfikuj zmianę przez
         * findById.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DeleteByIdFollowingSameTemplate {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj UserJdbcDao o deleteById(long id). Usuń jednego z 3 zapisanych
         * użytkowników i sprawdź findAll() przed i po usunięciu (powinna zmniejszyć
         * się liczba elementów listy o 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindByCustomColumnNotPrimaryKey {
        /*
         * 🧪 Zadanie 14:
         * Dodaj do ProductJdbcDao metodę findByName(String name) zwracającą
         * Optional<Product>, korzystającą z tego samego mapRow co findById. Zapisz
         * 3 produkty i przetestuj findByName dla istniejącej i nieistniejącej nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BatchInsertUsingSameDaoMethod {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę saveAll(List<User> users) w UserJdbcDao wywołującą save()
         * dla każdego elementu listy (bez próby użycia PreparedStatement.addBatch -
         * to jest kolejny temat). Zapisz listę 10 nowych użytkowników jednym
         * wywołaniem saveAll i sprawdź count() (SELECT COUNT(*)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MapRowAsStaticUtilityAcrossTwoDaos {
        /*
         * 🧪 Zadanie 16:
         * Wydziel mapRow dla User jako STATYCZNĄ metodę pomocniczą w osobnej klasie
         * UserRowMapper (poza UserJdbcDao), tak by MOGŁA być użyta przez inne klasy
         * w przyszłości. Zaktualizuj UserJdbcDao, żeby korzystał z
         * UserRowMapper.map(rs) zamiast własnej prywatnej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExceptionMessageIncludesContext {
        /*
         * 🧪 Zadanie 17:
         * Zmodyfikuj save(User) tak, by komunikat RuntimeException zawierał
         * konkretne dane wejściowe (np. "Blad podczas zapisu uzytkownika o
         * emailu: xyz@..."), a nie tylko generyczny tekst. Zasymuluj błąd
         * (np. przez zbyt długi String w VARCHAR(150)) i wypisz treść komunikatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TwoDaosSharedConnectionSameTransactionFeel {
        /*
         * 🧪 Zadanie 18:
         * Używając JEDNEGO obiektu Connection, utwórz UserJdbcDao i ProductJdbcDao
         * i wykonaj po jednym insert() na każdym. Sprawdź, że OBA insert-y są
         * widoczne w tym samym połączeniu (SELECT z obu tabel przez to samo
         * Connection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FindAllWithLimitUsingSql {
        /*
         * 🧪 Zadanie 19:
         * Dodaj do ProductJdbcDao metodę findTopNByPrice(int n) zwracającą n
         * najdroższych produktów (ORDER BY price DESC LIMIT ? w SQL H2). Zapisz 5
         * produktów o różnych cenach i sprawdź findTopNByPrice(2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorDuplicatedTryCatchIntoHelper {
        /*
         * 🧪 Zadanie 20:
         * Zauważ powtarzający się wzorzec "catch (SQLException e) { throw new
         * RuntimeException(...) }" w kilku metodach DAO. Napisz prywatną metodę
         * pomocniczą wrapSqlException(String message, SQLException e) zwracającą
         * RuntimeException i użyj jej we WSZYSTKICH metodach UserJdbcDao (rzucając
         * jej wynik przez throw).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCrudTemplateForNewEntity {
        /*
         * 🧪 Zadanie 21:
         * Od zera zaimplementuj CustomerJdbcDao dla tabeli customers (id, name,
         * email, city) z PIĘCIOMA metodami CRUD w pełni według wzorca z lekcji
         * (mapRow + save + findById + findAll + update + deleteById). Przetestuj
         * wszystkie metody na 4 klientach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_JoinBasedMapRowForTwoTables {
        /*
         * 🧪 Zadanie 22:
         * Tabele orders (id, customer_name) i order_items (id, order_id, product_name,
         * price). Napisz OrderJdbcDao.findOrderTotalsById(id) wykonujące
         * SELECT SUM(price) z JOIN, z własnym mapRow zwracającym record OrderTotal
         * (Long orderId, String customerName, BigDecimal total). Przetestuj na
         * zamówieniu z 3 pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PerformanceOfPreparedVsPlainStatement {
        /*
         * 🧪 Zadanie 23:
         * Zmierz (System.nanoTime()) czas wstawienia 500 produktów DWOMA sposobami:
         * (a) przez ProductJdbcDao.save() (PreparedStatement, tworzony na nowo przy
         * każdym wywołaniu), (b) przez JEDEN PreparedStatement wielokrotnie
         * używany w pętli (setString/setBigDecimal + executeUpdate wielokrotnie na
         * tym samym obiekcie). Porównaj czasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RobustMapRowWithDefensiveChecks {
        /*
         * 🧪 Zadanie 24:
         * Napisz mapRow dla Product, które dodatkowo waliduje, że price nie jest
         * null (rzuca IllegalStateException z czytelnym komunikatem, jeśli
         * rs.getBigDecimal("price") zwróci null) - zasymuluj taki przypadek,
         * wstawiając wiersz z NULL w price bezpośrednio przez Statement (z
         * pominięciem DAO), a potem próbując go odczytać przez findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DaoWithConfigurableTableNameForTesting {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj UserJdbcDao przyjmujący w konstruktorze DRUGI parametr -
         * nazwę tabeli (domyślnie "users", ale można podać np. "users_backup") -
         * budując SQL dynamicznie (konkatenacja Stringów DLA NAZWY TABELI, nie dla
         * wartości - te idą przez PreparedStatement). Utwórz dwie tabele i dwa DAO
         * operujące na różnych tabelach przez tę samą klasę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TransactionalSaveOfTwoRelatedEntities {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę saveOrderWithFirstItem(Connection, Order, OrderItem) łączącą
         * insert do orders i insert do order_items w JEDNYM Connection (bez
         * jawnego commit/rollback - to temat kolejnego rozdziału o transakcjach,
         * tu tylko podstawowy przepływ). Zwróć obiekt Order z uzupełnionym id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GenericMapRowViaFunctionalInterface {
        /*
         * 🧪 Zadanie 27:
         * Zdefiniuj funkcyjny interfejs RowMapper<T> { T map(ResultSet rs) throws
         * SQLException; } i napisz JEDNĄ generyczną metodę pomocniczą
         * <T> List<T> queryList(Connection, String sql, RowMapper<T>) w klasie
         * narzędziowej. Użyj jej do zaimplementowania findAll() dla UserJdbcDao I
         * ProductJdbcDao (dwa różne RowMapper, jedna metoda generyczna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ErrorRecoveryOnPartialBatchFailure {
        /*
         * 🧪 Zadanie 28:
         * W metodzie saveAll(List<User>) z Zadania 15 dodaj obsługę błędu: jeśli
         * jeden z userów ma zduplikowany e-mail (UNIQUE constraint w tabeli),
         * złap SQLException dla TEGO JEDNEGO usera, zaloguj błąd i KONTYNUUJ z
         * resztą listy (nie przerywaj całej metody). Na końcu wypisz, ile
         * użytkowników zapisano, a ile odrzucono.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DaoUnderConcurrentAccessSimulation {
        /*
         * 🧪 Zadanie 29:
         * Uruchom DWA wątki, każdy z WŁASNYM Connection do tej samej bazy H2,
         * każdy wywołujący ProductJdbcDao.save() 50 razy dla różnych produktów.
         * Poczekaj na zakończenie obu (join z limitem czasu) i sprawdź przez
         * findAll(), że wszystkie 100 produktów zostały poprawnie zapisane
         * (brak utraconych wpisów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDaoLayerForMiniShopSchema {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj i zaimplementuj DWA pełne DAO (CustomerJdbcDao, OrderJdbcDao)
         * dla mini-sklepu (customers: id, name; orders: id, customer_id, total) -
         * każdy z pełnym CRUD wg wzorca z lekcji. Zademonstruj scenariusz: dodaj 2
         * klientów, dodaj 3 zamówienia (różnym klientom), wypisz wszystkie
         * zamówienia, usuń jedno, wypisz stan końcowy obu tabel.
         */
        public static void main(String[] args) { }
    }
}
