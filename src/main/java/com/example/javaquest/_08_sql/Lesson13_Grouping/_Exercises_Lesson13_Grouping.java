package com.example.javaquest._08_sql.Lesson13_Grouping;

public class _Exercises_Lesson13_Grouping {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GroupByCount {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_group;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "orders" (id INT PRIMARY KEY, user_id INT, amount DECIMAL(10,2)) i
         * wstaw 6 zamówień od 3 użytkowników (po 2 każdy). Wykonaj "SELECT user_id,
         * COUNT(*) AS liczba_zamowien FROM orders GROUP BY user_id" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GroupBySum {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "orders" z Zadania 1. Wykonaj "SELECT user_id, SUM(amount)
         * AS suma FROM orders GROUP BY user_id" i wypisz łączną wartość zamówień
         * każdego użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GroupByAvg {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "orders" z Zadania 1. Wykonaj "SELECT user_id, AVG(amount)
         * AS srednia FROM orders GROUP BY user_id" i wypisz średnią wartość zamówienia
         * każdego użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GroupByMinMax {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz tabelę "orders" z Zadania 1. Wykonaj "SELECT user_id, MIN(amount)
         * AS minimalna, MAX(amount) AS maksymalna FROM orders GROUP BY user_id" i
         * wypisz najmniejsze i największe zamówienie każdego użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HavingBasicFilter {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw 7
         * zamówień, gdzie jeden użytkownik ma 1 zamówienie, a inni - więcej. Wykonaj
         * "SELECT user_id, COUNT(*) AS ile FROM orders GROUP BY user_id HAVING
         * COUNT(*) > 1" i wypisz - powinien pominąć użytkownika z tylko 1 zamówieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WhereBeforeGroupBy {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw 8
         * zamówień z różnymi kwotami. Wykonaj "SELECT user_id, COUNT(*) AS ile FROM
         * orders WHERE amount > 20 GROUP BY user_id" (WHERE filtruje wiersze PRZED
         * grupowaniem) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GroupByProductCategory {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "products" (id, name VARCHAR(100), category VARCHAR(50),
         * price DECIMAL(10,2)) i wstaw 6 produktów w 3 kategoriach. Wykonaj "SELECT
         * category, COUNT(*) AS liczba FROM products GROUP BY category" i wypisz,
         * ile produktów ma każda kategoria.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CannotSelectNonGroupedColumn {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw
         * kilka wierszy. Spróbuj wykonać ("celowo" błędne) zapytanie "SELECT user_id,
         * amount FROM orders GROUP BY user_id" (amount BEZ agregacji, nie w GROUP BY)
         * - złap SQLException i wypisz komunikat, potwierdzając regułę z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AllFiveAggregatesTogether {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw 6
         * zamówień. Wykonaj JEDNO zapytanie łączące WSZYSTKIE 5 funkcji agregujących:
         * "SELECT user_id, COUNT(*), SUM(amount), AVG(amount), MIN(amount),
         * MAX(amount) FROM orders GROUP BY user_id" i wypisz kompletny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GroupByAggregateVocabularyPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz println definicje 5 funkcji agregujących z
         * lekcji (COUNT, SUM, AVG, MIN, MAX) w formacie "<funkcja>: <opis>" oraz
         * krótkie wyjaśnienie różnicy między WHERE i HAVING.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_HavingWithSumThreshold {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw
         * zamówienia dla 4 użytkowników z różną łączną wartością. Wykonaj "SELECT
         * user_id, SUM(amount) AS suma FROM orders GROUP BY user_id HAVING
         * SUM(amount) > 100" i wypisz tylko użytkowników, których łączna wartość
         * zamówień przekracza 100.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WhereGroupByHavingTogether {
        /*
         * 🧪 Zadanie 12:
         * Odtwórz tabelę "orders" z Zadania 11. Wykonaj kompletne "SELECT user_id,
         * SUM(amount) AS suma FROM orders WHERE amount > 20 GROUP BY user_id HAVING
         * SUM(amount) > 100" (WHERE + GROUP BY + HAVING razem, jak w lekcji) i wypisz
         * wynik, komentując kolejność logicznego wykonania w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GroupByTwoColumns {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "sales" (id, region VARCHAR(50), category VARCHAR(50),
         * amount DECIMAL(10,2)) i wstaw 10 wierszy w 2 regionach i 2 kategoriach.
         * Wykonaj "SELECT region, category, SUM(amount) AS suma FROM sales GROUP BY
         * region, category" (grupowanie po DWÓCH kolumnach naraz) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CountDistinctInGroupBy {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "orders" (id, user_id INT, product_name VARCHAR(100)) i
         * wstaw 8 zamówień, niektórzy użytkownicy zamawiają ten sam produkt
         * wielokrotnie. Wykonaj "SELECT user_id, COUNT(DISTINCT product_name) AS
         * unikalne_produkty FROM orders GROUP BY user_id" i wypisz liczbę UNIKALNYCH
         * produktów zamówionych przez każdego użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_HavingWithCountAndAvgCombined {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "reviews" (id, product_id INT, rating INT) i wstaw recenzje
         * dla kilku produktów z różną liczbą i średnią ocen. Wykonaj "SELECT
         * product_id, COUNT(*) AS liczba_recenzji, AVG(rating) AS srednia_ocena FROM
         * reviews GROUP BY product_id HAVING COUNT(*) >= 2 AND AVG(rating) >= 4" i
         * wypisz produkty spełniające OBA kryteria.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GroupByWithOrderByCombined {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw
         * zamówienia dla 5 użytkowników. Wykonaj "SELECT user_id, SUM(amount) AS suma
         * FROM orders GROUP BY user_id ORDER BY suma DESC" (GROUP BY + ORDER BY z
         * Lesson12 razem) i wypisz ranking użytkowników wg łącznej wartości zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_GroupByWithNullValues {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "products" (id, category VARCHAR(50), price DECIMAL(10,2)) i
         * wstaw 6 produktów, 2 z category = NULL. Wykonaj "SELECT category,
         * COUNT(*) AS liczba FROM products GROUP BY category" i wypisz - zaobserwuj,
         * że NULL traktowany jest jako WŁASNA, osobna grupa (nie jest pomijany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AggregateWithoutGroupByWholeTable {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "orders" (id, amount DECIMAL(10,2)) i wstaw 6 zamówień.
         * Wykonaj "SELECT COUNT(*) AS total, SUM(amount) AS suma, AVG(amount) AS
         * srednia FROM orders" (funkcje agregujące BEZ GROUP BY - traktują CAŁĄ
         * tabelę jako jedną grupę) i wypisz podsumowanie globalne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManualGroupingInJavaVsSql {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw 8
         * zamówień. Wykonaj SQL-owe "GROUP BY user_id + SUM(amount)" i zapisz wynik.
         * Następnie odczytaj WSZYSTKIE surowe wiersze (bez GROUP BY) i policz TĘ SAMĄ
         * sumę RĘCZNIE w Javie (Map<Integer, BigDecimal> akumulator) - porównaj oba
         * wyniki i zweryfikuj, że są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_HavingVsWhereMisuseDemo {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw
         * dane. Spróbuj ("celowo" błędne) wykonać "SELECT user_id, COUNT(*) AS ile
         * FROM orders WHERE COUNT(*) > 1 GROUP BY user_id" (funkcja agregująca w
         * WHERE - to jest błąd, agregaty nie istnieją jeszcze na etapie WHERE) - złap
         * SQLException i wypisz komunikat, a potem wykonaj POPRAWNĄ wersję z HAVING.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiLevelGroupingWithHavingAndOrder {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "sales" (id, region VARCHAR(50), salesperson VARCHAR(100),
         * amount DECIMAL(10,2)) i wstaw 12 wierszy w 3 regionach z kilkoma
         * sprzedawcami. Wykonaj zapytanie grupujące po (region, salesperson) z
         * HAVING SUM(amount) > 200 i ORDER BY suma DESC (WHERE+GROUP BY+HAVING+ORDER
         * BY - kompletny "pipeline" SQL) i wypisz najlepszych sprzedawców w każdym
         * regionie spełniających próg.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TopCategoryPerRegionUsingGroupedResults {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "sales" (id, region VARCHAR(50), category VARCHAR(50),
         * amount DECIMAL(10,2)) i wstaw dane dla 3 regionów i 3 kategorii. Wykonaj
         * "GROUP BY region, category + SUM(amount)" i w Javie (po odczycie
         * pogrupowanego wyniku, bez window functions - poza zakresem kursu) znajdź
         * NAJLEPSZĄ kategorię (najwyższa suma) w KAŻDYM regionie i wypisz ranking
         * "region -> najlepsza kategoria (suma)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RunningTotalsComparisonSqlVsJava {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "orders" (id, user_id INT, order_date DATE, amount
         * DECIMAL(10,2)) i wstaw 10 zamówień dla 4 użytkowników na różne daty.
         * Wykonaj GROUP BY user_id + SUM(amount) i porównaj wynik z sumą policzoną
         * RĘCZNIE w Javie (dla weryfikacji poprawności). Następnie policz DODATKOWO
         * (SQL-em) COUNT(*) i AVG(amount) w tym samym zapytaniu grupującym i wypisz
         * kompletny raport per użytkownik: liczba zamówień, suma, średnia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HavingWithMultipleAggregateConditions {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "products" (id, category VARCHAR(50), price DECIMAL(10,2),
         * stock INT) i wstaw 12 produktów w 4 kategoriach z różnymi cenami i
         * zapasami. Wykonaj zapytanie grupujące po category z warunkiem HAVING
         * łączącym 3 agregaty naraz: "HAVING COUNT(*) >= 2 AND AVG(price) > 100 AND
         * SUM(stock) < 50" - znajdź kategorie spełniające WSZYSTKIE 3 kryteria naraz
         * i wypisz je z pełnymi statystykami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GroupByWithComputedBucketExpression {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "products" (id, name VARCHAR(100), price DECIMAL(10,2)) i
         * wstaw 10 produktów z różnymi cenami. Zbuduj "koszyki cenowe" przez
         * grupowanie po WYRAŻENIU: "SELECT CASE WHEN price < 50 THEN 'niska' WHEN
         * price < 500 THEN 'srednia' ELSE 'wysoka' END AS przedzial, COUNT(*) AS
         * liczba FROM products GROUP BY przedzial" - grupowanie po dynamicznie
         * obliczonej kategorii, nie po istniejącej kolumnie - wypisz rozkład produktów
         * po przedziałach cenowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CrossValidateAggregatesAgainstRawData {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "orders" (id, user_id INT, amount DECIMAL(10,2)) i wstaw 15
         * zamówień dla 5 użytkowników. Napisz metodę validateAggregates(Statement
         * stmt), która: (1) wykonuje GROUP BY + SUM/COUNT/AVG per user_id, (2) dla
         * KAŻDEGO user_id NIEZALEŻNIE wykonuje osobne zapytania z tymi samymi
         * agregatami, ale z WHERE user_id = ? (bez GROUP BY), (3) porównuje wyniki z
         * obu metod i wypisuje "ZGODNE" albo "NIEZGODNE" dla każdego użytkownika -
         * powinno być zawsze "ZGODNE", to weryfikacja poprawności GROUP BY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FunnelAnalysisWithGroupByAndHaving {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "user_events" (id, user_id INT, event_type VARCHAR(20)) i
         * wstaw zdarzenia typu 'VIEW', 'ADD_TO_CART', 'PURCHASE' dla kilku
         * użytkowników (nie każdy dotarł do każdego etapu). Wykonaj zapytanie
         * liczące, ile UNIKALNYCH użytkowników wykonało każdy typ zdarzenia:
         * "SELECT event_type, COUNT(DISTINCT user_id) AS unikalni_uzytkownicy FROM
         * user_events GROUP BY event_type" - zbuduj z tego prosty "lejek konwersji"
         * (funnel) i wypisz go w kolejności VIEW -> ADD_TO_CART -> PURCHASE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectOutliersUsingGroupStatistics {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "transactions" (id, account_id INT, amount DECIMAL(10,2)) i
         * wstaw dane dla 3 kont, z których jedno ma znacznie WYŻSZĄ średnią kwotę
         * transakcji niż inne (symulacja podejrzanej aktywności). Wykonaj GROUP BY
         * account_id + AVG(amount) + MAX(amount), a potem w Javie (po odczycie
         * pogrupowanego wyniku) policz OGÓLNĄ średnią ze wszystkich średnich kont i
         * wypisz konta, których AVG(amount) przekracza OGÓLNĄ średnią o więcej niż
         * 50% - "wykrywacz anomalii" na bazie zagregowanych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_HierarchicalGroupingWithSubtotalsInJava {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "sales" (id, region VARCHAR(50), country VARCHAR(50), amount
         * DECIMAL(10,2)) i wstaw dane dla 2 regionów, każdy z 2 krajami. Wykonaj
         * grupowanie po (region, country) + SUM(amount), a następnie w Javie policz
         * DODATKOWO sumy CZĄSTKOWE per region (agregując wyniki SQL-owego grupowania
         * w Javie, symulując "subtotal" znany z arkuszy kalkulacyjnych) - wypisz
         * hierarchiczny raport: region -> kraje ze sumami -> suma całego regionu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSalesAnalyticsDashboard {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj tabelę "sales" (id, region VARCHAR(50), category VARCHAR(50),
         * salesperson VARCHAR(100), amount DECIMAL(10,2), sale_date DATE) z 20
         * zróżnicowanymi wierszami (3 regiony, 3 kategorie, 5 sprzedawców, różne
         * daty). Zbuduj KOMPLETNY dashboard analityczny wykonujący 5 RÓŻNYCH zapytań
         * grupujących: (1) suma sprzedaży per region z HAVING > pewien próg, (2)
         * COUNT(DISTINCT salesperson) per region, (3) top kategoria per region (jak w
         * Zadaniu 22), (4) średnia wartość transakcji per sprzedawca z HAVING
         * COUNT(*) >= 3, (5) globalne podsumowanie (bez GROUP BY) - COUNT/SUM/AVG/MIN/
         * MAX całej tabeli. Wypisz wszystkie 5 raportów pod czytelnymi nagłówkami
         * jako jeden spójny "=== DASHBOARD SPRZEDAZOWY ===".
         */
        public static void main(String[] args) { }
    }
}
