package com.example.javaquest._08_sql.Lesson04_SqlDataTypes;

public class _Exercises_Lesson04_SqlDataTypes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IntegerAndBigintColumns {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_types;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "counters" (id INTEGER PRIMARY KEY, small_value INTEGER, big_value
         * BIGINT). Wstaw wiersz z small_value=100 i big_value=9000000000 (poza
         * zakresem INTEGER). Odczytaj oba przez rs.getInt() i rs.getLong() i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VarcharVsTextLengthLimit {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tabelę "notes" (id INT PRIMARY KEY, short_note VARCHAR(10), long_note
         * TEXT). Wstaw wiersz z long_note zawierającym 500 znaków (np. "a".repeat(500))
         * i spróbuj wstawić short_note dłuższy niż 10 znaków - złap SQLException i
         * wypisz komunikat, potwierdzając limit VARCHAR(n).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DecimalForMoneyNeverDouble {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)). Wstaw
         * cenę 19.99 przez PreparedStatement.setBigDecimal(...) (java.math.BigDecimal).
         * Odczytaj przez rs.getBigDecimal("price") i wypisz. Dodaj komentarz println
         * wyjaśniający, czemu NIE użyto double.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BooleanColumn {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, in_stock BOOLEAN). Wstaw 3
         * produkty z różnymi wartościami in_stock (true/false). Wykonaj SELECT name
         * odpowiednik i wypisz produkty, dla których in_stock = true (WHERE in_stock =
         * TRUE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DateTimeTimestampBasics {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "events" (id INT PRIMARY KEY, event_date DATE, event_time
         * TIME, created_at TIMESTAMP). Wstaw jeden wiersz z konkretną datą (LocalDate),
         * godziną (LocalTime) i znacznikiem czasu (LocalDateTime) przez
         * PreparedStatement.setObject(...). Odczytaj wszystkie 3 wartości przez
         * rs.getObject(kolumna, KlasaJava.class) i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UuidPrimaryKey {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "sessions" (id UUID PRIMARY KEY, user_name VARCHAR(100)).
         * Wygeneruj 2 identyfikatory przez UUID.randomUUID(), wstaw 2 wiersze i
         * odczytaj je przez rs.getObject("id", UUID.class), wypisując oba UUID-y.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SqlToJavaTypeMappingTable {
        /*
         * 🧪 Zadanie 7:
         * Bez łączenia się z bazą: wypisz na konsoli tabelę mapowania typów SQL na
         * typy Java z lekcji (println po jednej linii na wiersz): INTEGER->int,
         * BIGINT->long, VARCHAR->String, DECIMAL->BigDecimal, BOOLEAN->boolean,
         * DATE->LocalDate, TIME->LocalTime, TIMESTAMP->LocalDateTime, UUID->UUID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_JsonColumnBasicInsertAndRead {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę "settings" (id INT PRIMARY KEY, config JSON). Wstaw wiersz z
         * literałem JSON '{"theme":"light","fontSize":14}'. Odczytaj kolumnę przez
         * rs.getString("config") i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InvalidJsonRejected {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz tabelę "settings" (id INT PRIMARY KEY, config JSON) z Zadania 8.
         * Spróbuj wstawić wiersz z JSON 'to nie jest poprawny json' - złap
         * SQLException i wypisz komunikat, potwierdzając, że literał JSON '...'
         * waliduje składnię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DoubleVsBigDecimalRoundingDemo {
        /*
         * 🧪 Zadanie 10:
         * Bez bazy danych: w czystej Javie policz 0.1 + 0.2 jako double i wypisz
         * wynik (pokaże błąd zaokrąglenia binarnego, np. 0.30000000000000004).
         * Następnie policz to samo przy użyciu new BigDecimal("0.1").add(new
         * BigDecimal("0.2")) i wypisz dokładny wynik 0.3 - skomentuj różnicę w println.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullProductTableAllTypes {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz tabelę "products" z lekcji, ze WSZYSTKIMI typami naraz (id UUID,
         * name VARCHAR, description TEXT, price DECIMAL(10,2), in_stock BOOLEAN,
         * added_date DATE, daily_check_time TIME, created_at TIMESTAMP,
         * stock_quantity BIGINT). Wstaw 2 kompletne produkty przez PreparedStatement z
         * odpowiednimi setterami (setBigDecimal, setBoolean, setObject, setLong) i
         * wypisz wszystkie odczytane wartości z poprawnym mapowaniem typów Java.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DecimalPrecisionAndScaleLimits {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "invoices" (id INT PRIMARY KEY, amount DECIMAL(6,2)) -
         * precyzja 6 cyfr, 2 po przecinku (max 9999.99). Wstaw poprawną wartość
         * 1234.56, a następnie spróbuj wstawić wartość przekraczającą precyzję (np.
         * 99999.99) - złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_VarcharTruncationVsRejection {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "usernames" (id INT PRIMARY KEY, username VARCHAR(5)).
         * Spróbuj wstawić username = "abcdef" (6 znaków, przekracza limit 5) - złap
         * SQLException. Następnie wstaw poprawny "abcde" (dokładnie 5 znaków) - powinno
         * się udać. Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TimestampArithmeticInJava {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "logs" (id INT PRIMARY KEY, created_at TIMESTAMP). Wstaw 3
         * wiersze z różnymi znacznikami czasu (LocalDateTime, np. rozłożone na 3 dni).
         * Odczytaj wszystkie jako LocalDateTime i w Javie (nie SQL-em) policz różnicę w
         * dniach między najstarszym i najnowszym wpisem (java.time.temporal.ChronoUnit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BooleanDefaultAndExplicitComparison {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "tasks" (id INT PRIMARY KEY, title VARCHAR(100), done BOOLEAN
         * DEFAULT FALSE). Wstaw 3 zadania: jedno z jawnym done=TRUE, dwa bez podania
         * wartości done (użyją DEFAULT). Wypisz wszystkie zadania z ich statusem, a
         * potem osobno wypisz TYLKO niedokończone (WHERE done = FALSE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UuidVsIntegerPrimaryKeyComparison {
        /*
         * 🧪 Zadanie 16:
         * Utwórz DWIE tabele: "orders_int" (id INT PRIMARY KEY, note VARCHAR(50)) i
         * "orders_uuid" (id UUID PRIMARY KEY, note VARCHAR(50)). Wstaw po 3 wiersze do
         * każdej (w orders_int id generowane ręcznie 1,2,3, w orders_uuid przez
         * UUID.randomUUID()). Wypisz obie tabele i skomentuj w println różnicę:
         * krótszy/czytelniejszy INT vs globalnie unikalny UUID (przydatny w systemach
         * rozproszonych, bez pytania bazy o kolejną wartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_JsonColumnWithMultipleRowsAndFiltering {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "user_settings" (id INT PRIMARY KEY, user_name VARCHAR(50),
         * config JSON). Wstaw 3 wiersze z różnymi konfiguracjami JSON (np. różne
         * theme). Odczytaj WSZYSTKIE wiersze jako String i w Javie (bez parsowania
         * JSON specjalną biblioteką - proste String.contains) znajdź te, których
         * config zawiera "\"theme\":\"dark\"" i wypisz ich user_name.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MixedTypesInsertWithNullableColumns {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "profiles" (id INT PRIMARY KEY, nickname VARCHAR(50), birth_date
         * DATE, last_login TIMESTAMP). Wstaw jeden kompletny profil i jeden profil z
         * birth_date i last_login = NULL (użytkownik nigdy się nie zalogował, nie
         * podał daty urodzenia). Odczytaj oba przez rs.getObject(...) (nie
         * rs.getDate/getTimestamp) i wypisz z obsługą NULL ("brak danych" gdy null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DecimalRoundingModeInJava {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "prices" (id INT PRIMARY KEY, net_price DECIMAL(10,2)). Wstaw
         * cenę netto 100.00. Odczytaj ją jako BigDecimal, policz cenę brutto (net_price
         * * 1.23) i zaokrąglij wynik do 2 miejsc po przecinku metodą
         * BigDecimal.setScale(2, RoundingMode.HALF_UP) - wypisz cenę netto i brutto.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TableWithEveryTypeAndTypeSafetyReport {
        /*
         * 🧪 Zadanie 20:
         * Odtwórz kompletną tabelę "products" ze WSZYSTKIMI typami z lekcji (jak w
         * Zadaniu 11). Wstaw 3 produkty. Napisz metodę printTypeSafetyReport(ResultSet
         * rs), która przez rs.getMetaData() dla każdej kolumny wypisuje jej nazwę SQL
         * i typ SQL (getColumnTypeName(i)) - porównaj wynik z tabelą mapowań z Zadania 7.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MoneyCalculationPipelineWithBigDecimal {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "order_items" (id INT PRIMARY KEY, unit_price DECIMAL(10,2),
         * quantity INT). Wstaw 4 pozycje zamówienia z różnymi cenami i ilościami.
         * Napisz metodę calculateTotal(Statement stmt), która odczytuje WSZYSTKIE
         * wiersze, dla każdego liczy unit_price.multiply(BigDecimal.valueOf(quantity))
         * i sumuje WSZYSTKO na jednym BigDecimal accumulatorze (bez SUM() w SQL -
         * poznamy w Lesson13) - wypisz łączną wartość zamówienia z dokładnością do 2
         * miejsc po przecinku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_UuidBasedDistributedIdSimulation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj 2 "niezależne serwisy" wstawiające dane do WSPÓLNEJ tabeli
         * "events" (id UUID PRIMARY KEY, source VARCHAR(20), payload VARCHAR(100)) -
         * każdy serwis (osobna metoda w Javie) generuje SWOJE id przez
         * UUID.randomUUID() bez pytania bazy o kolejną wartość, po czym oba wstawiają
         * po 3 zdarzenia NIEZALEŻNIE. Zweryfikuj (COUNT(*) i COUNT(DISTINCT id)), że
         * mimo niezależnego generowania id nie doszło do żadnego konfliktu (6 unikalnych
         * wierszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TimestampBasedAuditLog {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "audit_log" (id INT PRIMARY KEY, action VARCHAR(50), occurred_at
         * TIMESTAMP). Napisz metodę logAction(Statement stmt, int id, String action),
         * która wstawia wiersz z occurred_at = LocalDateTime.now() (wywołane wewnątrz
         * metody, nie z zewnątrz). Zaloguj 3 różne akcje z krótkimi przerwami
         * (Thread.sleep(50) między nimi) i odczytaj je posortowane po occurred_at (bez
         * ORDER BY - poznamy w Lesson12, posortuj listę w Javie po odczycie), weryfikując
         * że kolejność czasowa się zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_JsonColumnValidationWorkflow {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "configs" (id INT PRIMARY KEY, raw_config VARCHAR(300), config
         * JSON). Napisz metodę tryInsertJson(Statement stmt, int id, String jsonText),
         * która wstawia jednocześnie do raw_config (jako zwykły String, BEZ walidacji)
         * i do config (jako literał JSON '...', Z walidacją). Przetestuj ją z 2
         * poprawnymi i 1 niepoprawnym tekstem JSON, zbierając wyniki (sukces/błąd) w
         * liście i wypisując raport na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MixedPrecisionDecimalReport {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "currency_rates" (id INT PRIMARY KEY, currency VARCHAR(3),
         * rate DECIMAL(12,6)) - 6 miejsc po przecinku (kursy walut potrzebują większej
         * precyzji niż pieniądze "zwykłe"). Wstaw 3 kursy z różną liczbą cyfr po
         * przecinku (np. 4.123456, 0.85, 1.1). Odczytaj je jako BigDecimal i wypisz z
         * pełną precyzją (BigDecimal.toPlainString()) oraz zaokrąglone do 2 miejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DateRangeOverlapDetectionInJava {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "bookings" (id INT PRIMARY KEY, start_date DATE, end_date
         * DATE). Wstaw 4 rezerwacje z różnymi (czasem nakładającymi się) zakresami
         * dat. Odczytaj wszystkie jako LocalDate i napisz metodę w Javie (bez SQL-owego
         * porównania zakresów), która sprawdza WSZYSTKIE pary rezerwacji i wypisuje,
         * które z nich się nakładają czasowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TypeConversionPitfallsCatalog {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "mixed" (id INT PRIMARY KEY, int_col INT, decimal_col
         * DECIMAL(10,2), bool_col BOOLEAN). Wstaw wiersz z int_col=NULL. Napisz
         * metodę demonstratePitfalls(ResultSet rs), która pokazuje RÓŻNICĘ między
         * rs.getInt("int_col") (zwróci 0, pułapka) a (Integer) rs.getObject("int_col")
         * (zwróci prawdziwy null) - wypisz obie wartości i rs.wasNull() po getInt().
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullTypeCoverageStressTest {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "stress_test" z KOMPLETNYM zestawem 9 typów z lekcji (jak w
         * Zadaniu 11). Napisz metodę generateAndInsertRows(Statement conn, int count),
         * która w pętli generuje "count" losowych, ale poprawnych wierszy (losowe
         * ceny, daty, boolean, UUID) i wstawia je przez PreparedStatement. Wygeneruj
         * 20 wierszy, a potem odczytaj wszystkie i zweryfikuj (COUNT(*) == 20), że
         * żaden insert się nie wysypał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_JsonVsNormalizedColumnsTradeoffDemo {
        /*
         * 🧪 Zadanie 29:
         * Zamodeluj DWA warianty przechowania preferencji użytkownika: (A) tabela
         * "prefs_json" (id, user_name, prefs JSON) z całą konfiguracją w jednej
         * kolumnie JSON, (B) tabela "prefs_normalized" (id, user_name, theme VARCHAR,
         * page_size INT, language VARCHAR) - osobne kolumny. Wstaw te same dane w
         * obu wariantach dla 3 użytkowników. Zademonstruj: w wariancie A trzeba
         * odczytać cały String i "ręcznie" wyciągnąć wartość (np. String.contains),
         * w wariancie B można filtrować SQL-em (WHERE theme = 'dark') - policz, ile
         * userów ma theme='dark' każdym z obu sposobów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneFinancialLedgerAllTypes {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj tabelę "ledger_entries" wykorzystującą przemyślanie WSZYSTKIE
         * typy z lekcji: id UUID PRIMARY KEY, account_name VARCHAR(100), description
         * TEXT, amount DECIMAL(12,2) (pieniądze - NIGDY double), is_debit BOOLEAN,
         * entry_date DATE, entry_time TIME, recorded_at TIMESTAMP, reference_count
         * BIGINT, metadata JSON. Wstaw 5 realistycznych wpisów księgowych (mix
         * debit/credit) przez PreparedStatement z poprawnymi typami Java. Napisz
         * metodę generateLedgerReport(Statement stmt), która odczytuje wszystkie
         * wpisy, sumuje osobno kwoty debetowe i kredytowe (BigDecimal, bez SQL SUM) i
         * wypisuje bilans końcowy (suma debetów minus suma kredytów) z poprawną
         * precyzją.
         */
        public static void main(String[] args) { }
    }
}
