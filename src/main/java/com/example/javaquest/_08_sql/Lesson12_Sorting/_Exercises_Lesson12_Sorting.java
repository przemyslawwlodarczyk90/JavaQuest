package com.example.javaquest._08_sql.Lesson12_Sorting;

public class _Exercises_Lesson12_Sorting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OrderByAscendingDefault {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_sort;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)) i wstaw 5 produktów z różnymi cenami w PRZYPADKOWEJ
         * kolejności wstawiania. Wykonaj "SELECT name, price FROM products ORDER BY
         * price" (bez ASC/DESC - domyślnie rosnąco) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OrderByDescending {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "products" z Zadania 1. Wykonaj "SELECT name, price FROM
         * products ORDER BY price DESC" i wypisz wynik - powinien być odwrotny do
         * Zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OrderByTextColumn {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "products" z 5 nazwami produktów. Wykonaj "SELECT name FROM
         * products ORDER BY name ASC" i wypisz wynik alfabetycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrderByTwoColumns {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 6 produktów w 2 kategoriach. Wykonaj "SELECT name,
         * category, price FROM products ORDER BY category ASC, price DESC" i wypisz
         * wynik - zwróć uwagę, że w ramach tej samej kategorii ceny są malejące.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrderByWithLimit {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz tabelę "products" z 6 produktami o różnych cenach. Wykonaj "SELECT
         * name, price FROM products ORDER BY price DESC LIMIT 3" i wypisz 3
         * najdroższe produkty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DefaultNullOrderingObservation {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "products" (id, name, price DECIMAL(10,2)) i wstaw 4
         * produkty, jeden z price = NULL. Wykonaj "SELECT name, price FROM products
         * ORDER BY price ASC" i wypisz wynik - zaobserwuj, gdzie w wyniku znajduje
         * się wiersz z NULL (domyślne zachowanie H2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NullsLastExplicit {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz tabelę "products" z price = NULL z Zadania 6. Wykonaj "SELECT
         * name, price FROM products ORDER BY price ASC NULLS LAST" i wypisz wynik -
         * wiersz z NULL powinien być na KOŃCU, niezależnie od domyślnego zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NullsFirstExplicit {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz tabelę "products" z price = NULL z Zadania 6. Wykonaj "SELECT
         * name, price FROM products ORDER BY price DESC NULLS FIRST" i wypisz wynik -
         * wiersz z NULL powinien być na POCZĄTKU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_OrderByColumnNotInSelect {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 5 produktów. Wykonaj "SELECT name FROM products
         * ORDER BY price DESC" (sortowanie po kolumnie, która NIE jest w SELECT) i
         * wypisz nazwy w kolejności od najdroższego produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UnsortedVsSortedComparison {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tabelę "products" (id, name, price DECIMAL(10,2)) i wstaw 5
         * produktów w losowej kolejności cen. Wykonaj DWA zapytania: "SELECT name
         * FROM products" (bez ORDER BY) i "SELECT name FROM products ORDER BY
         * price" - wypisz oba wyniki jeden pod drugim i skomentuj w println, że tylko
         * drugie gwarantuje deterministyczną kolejność.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OrderByThreeColumnsPriority {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "employees" (id, department VARCHAR(50), name VARCHAR(100),
         * salary INT) i wstaw 8 pracowników w 3 działach z powtarzającymi się
         * pensjami. Wykonaj "SELECT department, name, salary FROM employees ORDER BY
         * department ASC, salary DESC, name ASC" (3 poziomy sortowania) i wypisz
         * wynik, weryfikując kolejność priorytetów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_OrderByComputedExpression {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "order_items" (id, unit_price DECIMAL(10,2), quantity INT) i
         * wstaw 5 wierszy. Wykonaj "SELECT unit_price, quantity, unit_price *
         * quantity AS total FROM order_items ORDER BY total DESC" (sortowanie po
         * WYRAŻENIU obliczanym z aliasem) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_OrderByWithFilteringCombined {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 8 produktów. Wykonaj "SELECT name, price FROM
         * products WHERE category = 'Peryferia' ORDER BY price ASC" (WHERE +
         * ORDER BY razem, poznane w Lesson11) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TopNPerCategoryManualInJava {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 10 produktów w 3 kategoriach. Wykonaj "SELECT name,
         * category, price FROM products ORDER BY category ASC, price DESC" i w
         * Javie (bez SQL - bez window functions, poza zakresem kursu) znajdź i
         * wypisz TYLKO najdroższy produkt Z KAŻDEJ kategorii (śledząc zmianę
         * kategorii w pętli po odczycie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MixedAscDescPerColumn {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "students" (id, name VARCHAR(100), grade INT, age INT) i
         * wstaw 6 studentów. Wykonaj "SELECT name, grade, age FROM students ORDER BY
         * grade DESC, age ASC" (jedna kolumna malejąco, druga rosnąco - NIEZALEŻNE
         * kierunki) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NullsFirstVsLastSideBySide {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "tasks" (id, title VARCHAR(100), deadline DATE) i wstaw 5
         * zadań, 2 z deadline = NULL (brak terminu). Wykonaj DWA zapytania: "ORDER BY
         * deadline ASC NULLS FIRST" i "ORDER BY deadline ASC NULLS LAST" - wypisz oba
         * wyniki jeden pod drugim, porównując pozycję zadań bez terminu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SortStabilityWithDuplicateKeys {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "products" (id, name VARCHAR(100), price DECIMAL(10,2)) i
         * wstaw 6 produktów, gdzie 3 mają IDENTYCZNĄ cenę. Wykonaj "SELECT id, name,
         * price FROM products ORDER BY price ASC, id ASC" (drugi klucz sortowania
         * jako "tie-breaker" gwarantujący deterministyczną kolejność produktów o tej
         * samej cenie) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OrderByWithLikeFilterCombined {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "products" (id, name VARCHAR(100), price DECIMAL(10,2)) i
         * wstaw 8 produktów, kilka z nazwą zaczynającą się na tę samą literę. Wykonaj
         * "SELECT name, price FROM products WHERE name LIKE 'K%' ORDER BY price
         * DESC" (LIKE z Lesson11 + ORDER BY razem) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManualComparatorEquivalentInJava {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "products" (id, name VARCHAR(100), category VARCHAR(50),
         * price DECIMAL(10,2)) i wstaw 6 produktów. Odczytaj WSZYSTKIE wiersze BEZ
         * ORDER BY do listy obiektów w Javie, a następnie posortuj tę listę w Javie
         * przy pomocy Comparator.comparing(category).thenComparing(price,
         * reverseOrder()) - porównaj wynik z odpowiadającym mu zapytaniem SQL "ORDER
         * BY category ASC, price DESC" i zweryfikuj, że dają IDENTYCZNY porządek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PaginatedSortedResultsAcrossPages {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "products" (id, name, price DECIMAL(10,2)) i wstaw 10
         * produktów z różnymi cenami. Napisz metodę printSortedPage(Statement stmt,
         * int pageSize, int pageNumber), która łączy ORDER BY price ASC z LIMIT/OFFSET
         * (paginacja z Lesson10, ale teraz Z sortowaniem, żeby wynik był
         * deterministyczny między stronami). Wypisz WSZYSTKIE 3 strony (po 4
         * produkty) i zweryfikuj, że produkty na stronie 2 mają wyższą cenę niż na
         * stronie 1 (bo sortowanie rosnące).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiCriteriaLeaderboard {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "players" (id, name VARCHAR(100), score INT, time_seconds
         * INT) i wstaw 8 graczy, gdzie kilku ma IDENTYCZNY score. Zbuduj ranking
         * gracza: "ORDER BY score DESC, time_seconds ASC" (przy tym samym wyniku,
         * wygrywa krótszy czas) - wypisz TOP 5 z numeracją miejsca (1., 2., 3.,...)
         * obliczoną w Javie na podstawie kolejności wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NullAwareRankingWithBusinessMeaning {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "candidates" (id, name VARCHAR(100), interview_score INT) i
         * wstaw 8 kandydatów, 2 z interview_score = NULL (jeszcze nieprzeprowadzona
         * rozmowa). Zbuduj ranking z jawnym NULLS LAST ("ORDER BY interview_score
         * DESC NULLS LAST" - kandydaci bez oceny na końcu, NIE traktowani jako
         * "najlepsi" ani "najgorsi" przez domyślne zachowanie NULL) i wypisz pełną
         * listę z komentarzem, czemu ten konkretny biznesowy przypadek wymaga
         * jawnego NULLS LAST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SortPerformanceWithAndWithoutIndex {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) i wstaw
         * 2000 wierszy. Zmierz czas (System.nanoTime()) wykonania "SELECT * FROM
         * users ORDER BY email" BEZ indeksu na email, następnie utwórz CREATE INDEX
         * (podglądowo, dokładnie w Lesson18_Indexes) na email i zmierz czas TEGO
         * SAMEGO zapytania PO indeksie - wypisz porównanie czasów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ComplexReportWithMultiLevelSortingAndAliases {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "sales" (id, region VARCHAR(50), salesperson VARCHAR(100),
         * amount DECIMAL(10,2), sale_date DATE) i wstaw 10 wierszy w 3 regionach.
         * Wykonaj zapytanie z aliasami (Lesson10) i wielopoziomowym sortowaniem:
         * "SELECT region, salesperson, amount AS revenue FROM sales ORDER BY region
         * ASC, revenue DESC, sale_date ASC" - wypisz sformatowany raport pogrupowany
         * wizualnie po regionie (nagłówek regionu wypisywany TYLKO gdy się zmienia
         * względem poprzedniego wiersza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StableSortWithTiebreakerChain {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "products" (id, category VARCHAR(50), brand VARCHAR(50),
         * price DECIMAL(10,2)) i wstaw 12 produktów z WIELOMA powtórzeniami
         * kombinacji category+brand+price (celowo dużo duplikatów, żeby wymusić
         * potrzebę "tie-breakera"). Zbuduj sortowanie z ŁAŃCUCHEM 4 kolumn: "ORDER BY
         * category ASC, brand ASC, price DESC, id ASC" - wypisz pełny wynik i
         * zweryfikuj w Javie, że KAŻDA para kolejnych wierszy jest poprawnie
         * uporządkowana według tego łańcucha (napisz metodę weryfikującą to
         * programowo, nie tylko wizualnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CustomSortOrderViaCaseExpression {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "tickets" (id, title VARCHAR(100), priority VARCHAR(10)) i
         * wstaw 8 zgłoszeń z priorytetami 'LOW', 'MEDIUM', 'HIGH', 'CRITICAL' -
         * alfabetyczne sortowanie tych wartości NIE odpowiada logicznemu porządkowi
         * ważności. Zbuduj zapytanie z "ORDER BY CASE priority WHEN 'CRITICAL' THEN 1
         * WHEN 'HIGH' THEN 2 WHEN 'MEDIUM' THEN 3 WHEN 'LOW' THEN 4 END ASC" -
         * niestandardowy porządek sortowania - i wypisz zgłoszenia w poprawnej
         * kolejności ważności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PaginationConsistencyUnderConcurrentInserts {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)) i wstaw 10 produktów. Odczytaj "stronę 1" (ORDER BY id ASC
         * LIMIT 5 OFFSET 0), następnie WSTAW nowy produkt z id, które trafiłoby
         * logicznie na "stronę 1" (np. id=0, mniejsze od wszystkich), po czym odczytaj
         * "stronę 2" (LIMIT 5 OFFSET 5) - zademonstruj i skomentuj w println problem
         * "przesunięcia" wyników paginacji, gdy dane się zmieniają MIĘDZY odczytami
         * kolejnych stron (mimo stabilnego ORDER BY po jednej kolumnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SortByAggregatedValuePreview {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw kilku klientów z różną liczbą zamówień. Odczytaj
         * wszystkie zamówienia, w Javie (bez GROUP BY - poznamy w Lesson13) policz
         * SUMĘ amount dla każdego klienta (Map<Integer, BigDecimal>), a następnie
         * posortuj tę mapę wpisów malejąco po sumie (Comparator na
         * Map.Entry.comparingByValue) i wypisz ranking klientów wg łącznej wartości
         * zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_NullHandlingAcrossMultipleSortColumns {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "projects" (id, name VARCHAR(100), priority INT, deadline
         * DATE) i wstaw 8 projektów, gdzie NIEZALEŻNIE priority i deadline mogą być
         * NULL w różnych kombinacjach. Zbuduj sortowanie "ORDER BY priority DESC
         * NULLS LAST, deadline ASC NULLS LAST" - jawna kontrola NULL na OBU poziomach
         * sortowania naraz - wypisz wynik i skomentuj, dlaczego przy dwóch
         * niezależnie nullowalnych kolumnach trzeba to kontrolować na KAŻDYM poziomie
         * osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSortableReportingDashboard {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj tabelę "employees" (id, name VARCHAR(100), department VARCHAR(50),
         * salary INT, hire_date DATE, performance_score INT) z 15 zróżnicowanymi
         * wierszami (część z performance_score = NULL - nowi pracownicy jeszcze bez
         * oceny). Zaprojektuj KOMPLETNY, konfigurowalny "dashboard sortujący" w
         * Javie: metoda generateReport(Statement stmt, String sortBy, boolean
         * descending), która dynamicznie buduje ORDER BY na podstawie WALIDOWANEJ
         * (przeciw SQL injection - dozwolona lista kolumn) nazwy kolumny i
         * kierunku, z jawnym NULLS LAST niezależnie od kolumny. Wywołaj metodę dla 4
         * różnych kombinacji (sortowanie po salary DESC, po hire_date ASC, po
         * performance_score DESC z NULLS LAST, po department ASC) i wypisz każdy
         * raport z nagłówkiem opisującym użyte sortowanie.
         */
        public static void main(String[] args) { }
    }
}
