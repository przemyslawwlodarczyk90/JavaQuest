package com.example.javaquest._10_dao.Lesson07_ListResultsInDao;

public class _Exercises_Lesson07_ListResultsInDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FindAllReturnsList {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l07ex01;DB_CLOSE_DELAY=-1" z tabelą orders (id,
         * status), zaimplementuj OrderJdbcDao.findAll() zwracającą List<Order>.
         * Wstaw 3 zamówienia i wypisz wynik findAll() oraz jego size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindByStatusWithMatches {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj OrderJdbcDao o findByStatus(String status). Wstaw 2 zamówienia
         * "NEW" i 1 "SHIPPED", wywołaj findByStatus("NEW") i wypisz wynik razem z
         * jego size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FindByStatusNoMatchesReturnsEmptyList {
        /*
         * 🧪 Zadanie 3:
         * Wywołaj findByStatus("CANCELLED") na tabeli, w której NIE MA żadnego
         * zamówienia o tym statusie. Sprawdź, że wynik nie jest null (porównanie
         * == null musi dać false) i że isEmpty() zwraca true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IterateEmptyListWithoutError {
        /*
         * 🧪 Zadanie 4:
         * Weź wynik findByStatus dla nieistniejącego statusu i przejdź go pętlą
         * for-each - wypisz komunikat PRZED i PO pętli, żeby udowodnić, że pętla
         * wykonuje się 0 razy bez rzucania żadnego wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FindAllOnEmptyTable {
        /*
         * 🧪 Zadanie 5:
         * Na PUSTEJ tabeli orders (bez żadnego INSERT) wywołaj findAll() i
         * sprawdź, że wynik to pusta lista, nie null. Wypisz size() (powinno być 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ProductFindByCategoryList {
        /*
         * 🧪 Zadanie 6:
         * Tabela products (id, name, category). Napisz ProductJdbcDao.findByCategory
         * (String category) zwracającą List<Product>. Wstaw 5 produktów w 2
         * kategoriach, wywołaj findByCategory dla jednej z nich i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SizeUsedForSummaryReport {
        /*
         * 🧪 Zadanie 7:
         * Wywołaj findByStatus dla KAŻDEGO z 3 różnych statusów ("NEW", "SHIPPED",
         * "CANCELLED") i wypisz raport: "Status X: N zamowien" dla każdego,
         * korzystając wyłącznie z .size() zwróconych list.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ForEachLoopOverNonEmptyList {
        /*
         * 🧪 Zadanie 8:
         * Wstaw 4 zamówienia o statusie "NEW", wywołaj findByStatus("NEW") i
         * wypisz każdy element pętlą for-each z numerem porządkowym (1., 2., ...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CombineFindAllAndFindByStatus {
        /*
         * 🧪 Zadanie 9:
         * Wstaw mix zamówień o różnych statusach. Wywołaj findAll() i
         * findByStatus("NEW") i sprawdź (przez porównanie size()), że druga lista
         * jest podzbiorem pierwszej (mniejsza lub równa liczba elementów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SafeUsageWithoutNullCheck {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę printOrders(List<Order> orders) w main, która OD RAZU
         * używa orders.isEmpty() i pętli for-each BEZ jakiegokolwiek sprawdzenia
         * "if (orders != null)". Wywołaj ją dla wyniku z dopasowaniami i bez -
         * pokaż, że metoda działa bezpiecznie w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FindByStatusOrderedByColumn {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj findByStatus, by wynik był sortowany ORDER BY id DESC
         * (najnowsze najpierw). Wstaw 4 zamówienia "NEW" w kolejności i sprawdź,
         * że findByStatus("NEW") zwraca je w odwrotnej kolejności id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CountViaListSizeVsCountViaSql {
        /*
         * 🧪 Zadanie 12:
         * Porównaj DWA sposoby liczenia zamówień o statusie "NEW": (a)
         * findByStatus("NEW").size(), (b) osobne zapytanie SELECT COUNT(*) WHERE
         * status = ?. Zweryfikuj, że dają identyczny wynik, ale skomentuj, że (b)
         * jest bardziej efektywne, gdy interesuje nas TYLKO liczba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_StreamFilterOnJavaSideVsSqlSide {
        /*
         * 🧪 Zadanie 13:
         * Wywołaj findAll() i przefiltruj wynik w Javie (Stream.filter) do
         * zamówień o statusie "NEW". Porównaj wynik z findByStatus("NEW")
         * (filtrowanie w SQL) - powinny być identyczne. Skomentuj, które
         * podejście jest bardziej efektywne przy dużych tabelach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleStatusesCombinedQuery {
        /*
         * 🧪 Zadanie 14:
         * Napisz findByStatuses(List<String> statuses) zwracającą List<Order> dla
         * WIELU statusów naraz (SQL: WHERE status IN (...), zbudowany dynamicznie
         * z odpowiednią liczbą znaków zapytania w PreparedStatement). Przetestuj
         * dla listy ["NEW", "SHIPPED"].
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EmptyListPassedToBusinessLogic {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę calculateTotalRevenue(List<Order> orders) (Order z
         * dodatkowym polem amount) sumującą kwoty. Wywołaj ją dla wyniku
         * findByStatus dla nieistniejącego statusu (pusta lista) i sprawdź, że
         * zwraca 0 (albo BigDecimal.ZERO) BEZ rzucania wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ListVsOptionalDecisionExercise {
        /*
         * 🧪 Zadanie 16:
         * Dla tabeli orders zaimplementuj DWIE metody: findById(long) (Optional,
         * bo max 1 wynik) i findByStatus(String) (List, bo wiele wyników). W
         * komentarzu wyjaśnij, dlaczego pierwsza NIE powinna zwracać List, a
         * druga NIE powinna zwracać Optional.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PaginatedListUsingLimitOffset {
        /*
         * 🧪 Zadanie 17:
         * Wstaw 20 zamówień. Napisz findPage(int pageNumber, int pageSize)
         * zwracającą List<Order> przy użyciu LIMIT/OFFSET w SQL H2. Wypisz stronę
         * 1 (10 elementów) i stronę 2 (kolejne 10) i zweryfikuj, że razem dają
         * wszystkie 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SortListInJavaAfterFetching {
        /*
         * 🧪 Zadanie 18:
         * Wywołaj findAll() (bez ORDER BY w SQL) i posortuj wynik w Javie przy
         * użyciu List.sort() lub Comparator (np. po statusie alfabetycznie).
         * Wypisz wynik przed i po sortowaniu, żeby zobaczyć różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GroupListByStatusInJava {
        /*
         * 🧪 Zadanie 19:
         * Wywołaj findAll() i pogrupuj wynik w Javie (Collectors.groupingBy) po
         * statusie, budując Map<String, List<Order>>. Wypisz liczbę zamówień w
         * każdej grupie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ListResultUsedInReportGeneration {
        /*
         * 🧪 Zadanie 20:
         * Napisz generateStatusReport(OrderJdbcDao dao) łączącą 3 wywołania
         * findByStatus (dla "NEW", "SHIPPED", "CANCELLED") w jeden sformatowany
         * String raportu z liczbą zamówień każdego statusu i sumą wszystkich
         * (porównaną z findAll().size()).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullListPipelineWithFilterMapCollect {
        /*
         * 🧪 Zadanie 21:
         * Dla tabeli orders z dodatkowym polem amount, wywołaj findAll(), a
         * następnie za pomocą Stream API: przefiltruj tylko "NEW", zmapuj do
         * kwot, zsumuj (reduce/sum) i wypisz wynik - CAŁA logika biznesowa poza
         * DAO, DAO dostarcza tylko surową listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NeverNullContractVerifiedAcrossMethods {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę testową verifyNeverNull(OrderJdbcDao dao) sprawdzającą
         * (przez porównania != null wypisywane w konsoli) że findAll(),
         * findByStatus("cokolwiek") i findByStatus("NIEISTNIEJACY_STATUS") - dla
         * WSZYSTKICH trzech wywołań na pustej i niepustej tabeli - nigdy nie
         * zwracają null. Wypisz "kontrakt OK" na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LargeResultSetMemoryConsideration {
        /*
         * 🧪 Zadanie 23:
         * Wstaw 5000 zamówień (pętla insertów) i zmierz (System.nanoTime() oraz
         * Runtime.getRuntime().totalMemory()-freeMemory() przed/po) czas i
         * przybliżone zużycie pamięci wywołania findAll() ładującego WSZYSTKIE
         * wiersze do listy na raz. Skomentuj w kodzie, dlaczego dla bardzo
         * dużych tabel to podejście bywa problematyczne (wstęp do streamowania
         * wyników, poza zakresem tej lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ListComparisonBetweenTwoQueries {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj findByStatusUsingJoin (JOIN z tabelą customers) i
         * findByStatusUsingSubquery (WHERE customer_id IN (SELECT...)) dające TEN
         * SAM zestaw zamówień. Porównaj obie listy (np. po zbiorze id, ignorując
         * kolejność) i wypisz, czy są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImmutableListReturnedFromDao {
        /*
         * 🧪 Zadanie 25:
         * Zmodyfikuj findAll(), by zwracała List.copyOf(result) (niemodyfikowalną
         * kopię) zamiast bezpośrednio ArrayList. Spróbuj wywołać .add(...) na
         * wyniku i złap UnsupportedOperationException - wyjaśnij w komentarzu,
         * czemu to chroni DAO przed modyfikacją "z zewnątrz" bez wiedzy DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BatchStatusUpdateBasedOnListQuery {
        /*
         * 🧪 Zadanie 26:
         * Pobierz findByStatus("NEW"), a następnie dla KAŻDEGO zamówienia z tej
         * listy wywołaj updateStatus(id, "PROCESSING") (osobna metoda DAO).
         * Zweryfikuj przez ponowne findByStatus("NEW") (powinna być pusta) i
         * findByStatus("PROCESSING") (powinna zawierać przeniesione zamówienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ListResultCachingWithInvalidation {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj prosty cache listy: findAllCached(dao) zwraca zapisaną
         * wcześniej listę (pole statyczne/instancyjne), jeśli istnieje, inaczej
         * woła dao.findAll() i zapisuje wynik. Po KAŻDYM insert/update/delete na
         * DAO wywołaj invalidateCache() (wyczyść zapisaną listę). Zademonstruj
         * cache "trafiony" i "unieważniony po modyfikacji".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConcurrentReadsOfListWhileWriting {
        /*
         * 🧪 Zadanie 28:
         * Uruchom DWA wątki z WŁASNYMI Connection: jeden 30x wywołuje
         * findByStatus("NEW") i wypisuje size(), drugi 30x wstawia nowe
         * zamówienie "NEW". Poczekaj na zakończenie obu (join z limitem czasu) i
         * sprawdź finalne findAll().size() - upewnij się, że program kończy się
         * bez wyjątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ListResultDrivenValidationSummary {
        /*
         * 🧪 Zadanie 29:
         * Wstaw 15 zamówień o różnych statusach (w tym kilka niepoprawnych,
         * np. status "" - dozwolone na poziomie bazy, bo DAO nie waliduje).
         * Napisz walidację PO STRONIE JAVY (nie SQL) filtrującą findAll() do
         * zamówień z NIEPRAWIDŁOWYM statusem (pustym albo null) i wypisz ich
         * listę jako "błędy danych do naprawienia".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullReportingModuleUsingOnlyListQueries {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-moduł raportowania zamówień: OrderJdbcDao z findAll(),
         * findByStatus(status), findByStatuses(list). ReportService generujący
         * pełny raport tekstowy: łączna liczba zamówień, liczba per status
         * (dla wszystkich statusów obecnych w danych, wykrytych dynamicznie przez
         * Stream na wyniku findAll(), NIE zaszytych na sztywno), oraz procent
         * każdego statusu względem całości. Przetestuj na 30 zamówieniach o
         * mieszanych statusach.
         */
        public static void main(String[] args) { }
    }
}
