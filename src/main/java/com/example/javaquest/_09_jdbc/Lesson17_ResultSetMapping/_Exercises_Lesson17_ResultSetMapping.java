package com.example.javaquest._09_jdbc.Lesson17_ResultSetMapping;

public class _Exercises_Lesson17_ResultSetMapping {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicMapRowAndFindById {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l17ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "products" (id BIGINT AUTO_INCREMENT, name VARCHAR(100)) z 3
         * wierszami. Zdefiniuj record Product(long id, String name),
         * napisz mapRow(ResultSet) i użyj go w findById(Connection, long).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindAllUsingSameMapRow {
        /*
         * 🧪 Zadanie 2:
         * Używając TEJ SAMEJ metody mapRow z Zadania 1, napisz findAll(Connection)
         * zwracającą List<Product>. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MapAllStandardSqlTypes {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "records" z KAŻDYM typem z tabeli w teorii lekcji:
         * id INT, big_number BIGINT, description VARCHAR(100), is_active
         * BOOLEAN, event_date DATE, created_at TIMESTAMP, amount DECIMAL(10,2).
         * Wstaw 1 wiersz, zdefiniuj odpowiadający record i napisz mapRow
         * mapujący WSZYSTKIE 7 kolumn na odpowiednie typy Java.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareMappingByNameVsIndex {
        /*
         * 🧪 Zadanie 4:
         * Napisz DWIE wersje mapRow dla tabeli "products" (id, name): jedną
         * mapującą po NAZWACH kolumn (getLong("id")), drugą po INDEKSACH
         * (getLong(1)). Porównaj (println) wyniki obu na tym samym wierszu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MapAggregateQueryResult {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj record ProductStats(long count, BigDecimal avgPrice).
         * Napisz mapRow(ResultSet) mapujący WYNIK zapytania "SELECT
         * COUNT(*) AS count, AVG(price) AS avg_price FROM products" (JEDEN
         * wiersz agregatu, nie tabela) na ten record.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReuseMapRowForFilteredQuery {
        /*
         * 🧪 Zadanie 6:
         * Używając mapRow z Zadania 1, napisz DWIE metody: findAll() (bez
         * WHERE) i findByNameContains(Connection, String) (z "WHERE name
         * LIKE ?") - obie korzystające z TEJ SAMEJ metody mapRow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapRowWithNullableColumn {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "products" (id, name, discount DECIMAL - opcjonalna,
         * część wierszy NULL) napisz mapRow zwracający record Product(long
         * id, String name, BigDecimal discount) POPRAWNIE obsługujący
         * NULL (przez getBigDecimal, które zwraca null dla NULL - patrz
         * Lesson06). Zweryfikuj na wierszu z NULL discount.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MapRowListConvenienceMethod {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę mapRowList(ResultSet rs) THROWS SQLException,
         * która wewnętrznie iteruje while(rs.next()) i woła mapRow() dla
         * każdego wiersza, zwracając List<Product>. Porównaj jej użycie
         * z RĘCZNYM pisaniem tej samej pętli w findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintMappedFieldRuntimeTypes {
        /*
         * 🧪 Zadanie 9:
         * Na tabeli z Zadania 3 zmapuj jeden wiersz i wypisz dla KAŻDEGO
         * pola zmapowanego obiektu jego runtime typ (pole.getClass().getSimpleName()) -
         * potwierdzenie zgodności z tabelą typów z teorii lekcji (int,
         * Long/long, String, Boolean/boolean, java.sql.Date,
         * java.sql.Timestamp, BigDecimal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WrongTypeGetterThrowsOrMisbehaves {
        /*
         * 🧪 Zadanie 10:
         * Na kolumnie VARCHAR (np. name) spróbuj odczytać wartość przez
         * rs.getInt("name") (niewłaściwy typ). Złap SQLException (albo
         * zaobserwuj zwróconą wartość, jeśli H2 nie rzuca błędu dla tego
         * przypadku) i wypisz wynik - skomentuj, dlaczego dobrze napisany
         * mapRow z WŁAŚCIWYMI typami eliminuje ten problem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MapRowWithComputedField {
        /*
         * 🧪 Zadanie 11:
         * Na tabeli "users" (id, first_name, last_name) napisz mapRow
         * zwracający record UserView(long id, String fullName), gdzie
         * fullName jest WYLICZANE wewnątrz mapRow (firstName + " " +
         * lastName), a nie przechowywane w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MapRowWithNestedValueObject {
        /*
         * 🧪 Zadanie 12:
         * Na tabeli "users" (id, first_name, address_street, address_city)
         * zdefiniuj record Address(String street, String city) i record
         * User(long id, String firstName, Address address). Napisz mapRow
         * budujący NAJPIERW Address, a potem User z zagnieżdżonym Address.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DifferentMapRowPerUseCase {
        /*
         * 🧪 Zadanie 13:
         * Na tabeli "products" (id, name, price, description) napisz
         * DWA mapRow: mapRowSummary (tylko id, name - do listy) i
         * mapRowFull (wszystkie kolumny - do widoku szczegółowego).
         * Zademonstruj użycie obu na odpowiednio dopasowanych zapytaniach
         * SELECT (SELECT id, name ... vs SELECT * ...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DefensiveBigDecimalHelper {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę pomocniczą BigDecimal getRequiredBigDecimal(ResultSet
         * rs, String column), która rzuca IllegalStateException, jeśli
         * wartość jest null (kolumna TEORETYCZNIE nigdy nie powinna być
         * NULL). Użyj jej w mapRow dla kolumny "price" (NOT NULL) i
         * zademonstruj normalne mapowanie (sukces).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FindByCriteriaReusingMapRow {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę findByPriceRange(Connection, BigDecimal min,
         * BigDecimal max) używającą "WHERE price BETWEEN ? AND ?" i
         * TEJ SAMEJ metody mapRow z poprzednich zadań. Przetestuj dla
         * konkretnego zakresu cen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareGenericMapVsTypedMapRow {
        /*
         * 🧪 Zadanie 16:
         * Dla JEDNEGO wiersza tabeli "products" zbuduj DWIE reprezentacje:
         * generyczną Map<String,Object> (przez getObject dla każdej
         * kolumny wg metadata) oraz silnie typowaną Product (przez
         * mapRow). Wypisz obie i porównaj wygodę/bezpieczeństwo typów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MapRowValidatesUnexpectedNull {
        /*
         * 🧪 Zadanie 17:
         * Napisz mapRow dla kolumny name (teoretycznie NOT NULL w
         * schemacie), które WYRAŹNIE sprawdza rs.wasNull() po odczycie i
         * rzuca IllegalStateException("Nieoczekiwany NULL w kolumnie
         * name dla id=" + id), jeśli mimo to wartość jest null. Przetestuj
         * na poprawnych danych (sukces) - skomentuj, że taka walidacja
         * pomaga wychwycić niespójności danych szybko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MapRowFromJoinIntoCombinedRecord {
        /*
         * 🧪 Zadanie 18:
         * Na tabelach "orders" (id, customer_id, total) i "customers"
         * (id, name) wykonaj JOIN i napisz mapRow zwracający record
         * OrderWithCustomerName(long orderId, String customerName,
         * BigDecimal total) mapujący kolumny z OBU tabel z jednego
         * połączonego wiersza wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureMappingOverheadOnManyRows {
        /*
         * 🧪 Zadanie 19:
         * Wstaw 5000 wierszy do tabeli "products". Zmierz czas: (a)
         * odczytu WSZYSTKICH wierszy z mapowaniem przez mapRow do
         * List<Product>, (b) odczytu WSZYSTKICH wierszy BEZ mapowania
         * (tylko przejście przez while(rs.next()) bez budowania obiektów).
         * Wypisz oba czasy i skomentuj, że narzut mapowania jest zwykle
         * niewielki względem samego kosztu I/O odczytu danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FindByIdReturningOptionalViaMapRow {
        /*
         * 🧪 Zadanie 20:
         * Napisz findByIdOptional(Connection, long id) zwracającą
         * Optional<Product>, budowaną WEWNĘTRZNIE przy pomocy mapRow
         * (if (rs.next()) Optional.of(mapRow(rs)) else Optional.empty()).
         * Przetestuj dla istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericResultSetMapperInterface {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj generyczny funkcyjny interfejs ResultSetMapper<T> { T
         * map(ResultSet rs) throws SQLException; }. Napisz generyczną
         * metodę <T> List<T> queryList(Connection conn, String sql,
         * ResultSetMapper<T> mapper). Użyj jej DWA razy - dla tabeli
         * "products" (mapper -> Product) i dla tabeli "users" (mapper ->
         * User) - JEDNĄ metodą queryList dla dwóch różnych typów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenericQueryOneReturningOptional {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz Zadanie 21 o generyczną metodę <T> Optional<T> queryOne(Connection
         * conn, String sql, ResultSetMapper<T> mapper). Użyj jej do
         * znalezienia jednego produktu po id i jednego użytkownika po id -
         * tym samym mechanizmem generycznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FullTypeMappingTableDemo {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz Zadanie 3 o KOMPLETNĄ demonstrację całej tabeli typów
         * z teorii lekcji (INT->int, BIGINT->long, VARCHAR->String,
         * BOOLEAN->boolean, DATE->java.sql.Date, TIMESTAMP->java.sql.Timestamp,
         * DECIMAL->BigDecimal) - dla każdego typu wypisz nazwę kolumny SQL,
         * odpowiadający typ Java oraz zmapowaną wartość, w formie
         * sformatowanej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HandleSchemaDriftGracefully {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "products" (id, name, price), napisz mapRow
         * ZAKŁADAJĄCY dodatkową kolumnę "category" (której NIE MA w
         * tabeli). Wywołaj go i złap SQLException - wypisz przyjazny
         * komunikat sugerujący niezgodność między kodem mapującym a
         * aktualnym schematem bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LayeredMappingRawToDisplayString {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj łańcuch transformacji: mapRow (ResultSet -> Product),
         * potem metoda toDisplayString(Product) (Product -> String
         * sformatowany do wyświetlenia, np. "Klawiatura - 149.99 zl").
         * Użyj obu metod razem w findAllFormatted(Connection) zwracającej
         * List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_GenericPaginationWithMapper {
        /*
         * 🧪 Zadanie 26:
         * Napisz generyczną metodę <T> List<T> findPage(Connection conn,
         * String baseSql, int limit, int offset, ResultSetMapper<T> mapper)
         * dokładającą "LIMIT ? OFFSET ?" do zapytania. Użyj jej do
         * paginacji tabeli "products" (co najmniej 12 wierszy) po 4
         * na stronę, wypisując 3 strony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SimpleCacheAroundFindById {
        /*
         * 🧪 Zadanie 27:
         * Napisz klasę CachedProductRepository z polem Map<Long,Product>
         * cache i metodą findById(Connection, long id), która NAJPIERW
         * sprawdza cache, a dopiero potem (przy "cache miss") wykonuje
         * zapytanie SQL + mapRow, zapisując wynik do cache. Zmierz czas
         * pierwszego wywołania (miss) i drugiego (hit) dla TEGO SAMEGO id -
         * drugie powinno być zauważalnie szybsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MapRowRegressionTest {
        /*
         * 🧪 Zadanie 28:
         * Wstaw JEDEN wiersz o znanych, konkretnych wartościach. Napisz
         * metodę testMapRow(Connection), która odczytuje ten wiersz przez
         * mapRow i SPRAWDZA (println "PASS"/"FAIL") każde pole
         * zmapowanego obiektu względem oczekiwanej wartości - prosty,
         * powtarzalny test regresji mapowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MapAggregatedGroupByResult {
        /*
         * 🧪 Zadanie 29:
         * Na tabeli "employees" (id, department, salary) z co najmniej 8
         * wierszami w 3 departamentach, wykonaj "SELECT department,
         * SUM(salary) AS total_salary, COUNT(*) AS employee_count FROM
         * employees GROUP BY department". Zdefiniuj record
         * DepartmentSalarySummary(String department, BigDecimal totalSalary,
         * long employeeCount) i napisz mapRow mapujący KAŻDĄ grupę na ten
         * record, zbierając wynik do List.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneGenericRepositoryWithMapper {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj generyczną klasę Repository<T> (konstruktor: Connection,
         * String tableName, ResultSetMapper<T> mapper) z metodami
         * findById(long id), findAll(), findPage(int limit, int offset) -
         * wszystkie zbudowane WOKÓŁ przekazanego ResultSetMapper<T> (z
         * Zadania 21). Zainstancjuj ją DWA razy - dla "products" (Product)
         * i "users" (User) - i wypisz połączony raport demonstrujący pełną
         * reużywalność abstrakcji między różnymi tabelami/typami.
         */
        public static void main(String[] args) { }
    }
}
