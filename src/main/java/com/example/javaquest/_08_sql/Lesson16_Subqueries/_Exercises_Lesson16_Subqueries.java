package com.example.javaquest._08_sql.Lesson16_Subqueries;

public class _Exercises_Lesson16_Subqueries {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExistsBasicSubquery {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_subq;DB_CLOSE_DELAY=-1). Utwórz
         * "users" (id INT PRIMARY KEY, name VARCHAR(100)) i "orders" (id INT PRIMARY
         * KEY, user_id INT, amount DECIMAL(10,2)). Wstaw 3 użytkowników, z których
         * tylko 2 mają zamówienia. Wykonaj "SELECT name FROM users u WHERE EXISTS
         * (SELECT 1 FROM orders o WHERE o.user_id = u.id)" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NotExistsBasicSubquery {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz users/orders z Zadania 1. Wykonaj "SELECT name FROM users u WHERE
         * NOT EXISTS (SELECT 1 FROM orders o WHERE o.user_id = u.id)" i wypisz
         * użytkowników BEZ zamówień (dopełnienie Zadania 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SubqueryInSelectCorrelated {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz users/orders z Zadania 1. Wykonaj "SELECT name, (SELECT COUNT(*)
         * FROM orders o WHERE o.user_id = u.id) AS liczba_zamowien FROM users u" -
         * podzapytanie skorelowane w SELECT - i wypisz liczbę zamówień każdego
         * użytkownika (włącznie z 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SubqueryInFromBasic {
        /*
         * 🧪 Zadanie 4:
         * Utwórz "products" (id, price DECIMAL(10,2)) i wstaw 5 produktów. Wykonaj
         * "SELECT AVG(p.price) AS srednia_z_podzapytania FROM (SELECT price FROM
         * products WHERE price > 50) AS p" - podzapytanie w FROM traktowane jak
         * tabela (wymaga aliasu) - i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InWithSubqueryListOfValues {
        /*
         * 🧪 Zadanie 5:
         * Utwórz "products" (id, name) i "orders" (id, product_id INT). Wstaw
         * produkty i zamówienia tylko dla części z nich. Wykonaj "SELECT name FROM
         * products WHERE id IN (SELECT product_id FROM orders)" i wypisz produkty,
         * które KIEDYKOLWIEK zostały zamówione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SubqueryReturningSingleScalarValue {
        /*
         * 🧪 Zadanie 6:
         * Utwórz "products" (id, name, price DECIMAL(10,2)) i wstaw 6 produktów.
         * Wykonaj "SELECT name, price FROM products WHERE price > (SELECT
         * AVG(price) FROM products)" - podzapytanie zwracające JEDNĄ wartość (średnią
         * ceny) użytą w porównaniu - i wypisz produkty droższe niż przeciętna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SubqueryWithMinMax {
        /*
         * 🧪 Zadanie 7:
         * Utwórz "products" (id, name, price DECIMAL(10,2)) i wstaw 5 produktów.
         * Wykonaj "SELECT name FROM products WHERE price = (SELECT MAX(price) FROM
         * products)" i wypisz nazwę najdroższego produktu (podzapytanie zwracające
         * skalarną wartość MAX).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ThreePlacesOfSubqueriesOverviewPrint {
        /*
         * 🧪 Zadanie 8:
         * Bez łączenia się z bazą: wypisz println wyjaśniające 3 miejsca, gdzie może
         * wystąpić podzapytanie z lekcji (WHERE, FROM, SELECT) z jednym przykładem
         * zastosowania każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NotInWithSubquery {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz products/orders z Zadania 5. Wykonaj "SELECT name FROM products
         * WHERE id NOT IN (SELECT product_id FROM orders WHERE product_id IS NOT
         * NULL)" i wypisz produkty, które NIGDY nie zostały zamówione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExistsVsInEquivalentResult {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz users/orders z Zadania 1. Wykonaj DWA równoważne zapytania:
         * jedno z EXISTS (jak w Zadaniu 1), drugie z "WHERE id IN (SELECT user_id
         * FROM orders)" - porównaj w Javie (Set<String>), że dają IDENTYCZNY zestaw
         * nazwisk.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SubqueryInFromWithGroupBy {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz users/orders (kilku użytkowników, różna liczba zamówień). Wykonaj
         * "SELECT AVG(zamowienia_na_uzytkownika.ile) AS srednia FROM (SELECT u.id,
         * COUNT(o.id) AS ile FROM users u LEFT JOIN orders o ON o.user_id = u.id
         * GROUP BY u.id) AS zamowienia_na_uzytkownika" (jak w lekcji) i wypisz
         * średnią liczbę zamówień na użytkownika, uwzględniając też tych z 0
         * zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CorrelatedSubqueryWithMultipleConditions {
        /*
         * 🧪 Zadanie 12:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2), status VARCHAR(20)). Wstaw dane z różnymi statusami
         * zamówień. Wykonaj "SELECT name, (SELECT COUNT(*) FROM orders o WHERE
         * o.customer_id = c.id AND o.status = 'PAID') AS zaplacone FROM customers c"
         * - podzapytanie skorelowane z DODATKOWYM warunkiem - i wypisz liczbę
         * zapłaconych zamówień każdego klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExistsWithCorrelationOnMultipleColumns {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "students" (id, name) i "enrollments" (student_id INT, course_id
         * INT, grade INT). Wstaw dane. Wykonaj "SELECT name FROM students s WHERE
         * EXISTS (SELECT 1 FROM enrollments e WHERE e.student_id = s.id AND e.grade >=
         * 5)" i wypisz studentów z co najmniej jedną oceną >= 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SubqueryInSelectWithCoalescePattern {
        /*
         * 🧪 Zadanie 14:
         * Utwórz "products" (id, name) i "reviews" (id, product_id INT, rating INT).
         * Wstaw produkty, część bez żadnej recenzji. Wykonaj "SELECT name, (SELECT
         * AVG(rating) FROM reviews r WHERE r.product_id = p.id) AS srednia_ocena FROM
         * products p" i wypisz - zaobserwuj, że produkty bez recenzji dostają NULL w
         * srednia_ocena (podzapytanie zwraca NULL, gdy nie ma wierszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NestedSubqueriesTwoLevels {
        /*
         * 🧪 Zadanie 15:
         * Utwórz "customers" (id, name), "orders" (id, customer_id INT), "order_items"
         * (id, order_id INT, product_id INT). Wstaw dane. Wykonaj "SELECT name FROM
         * customers c WHERE EXISTS (SELECT 1 FROM orders o WHERE o.customer_id = c.id
         * AND EXISTS (SELECT 1 FROM order_items oi WHERE oi.order_id = o.id))" -
         * podzapytanie ZAGNIEŻDŻONE w podzapytaniu (2 poziomy) - wypisz klientów,
         * którzy mają zamówienie z co najmniej jedną pozycją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SubqueryInFromForTopValuesPerGroup {
        /*
         * 🧪 Zadanie 16:
         * Utwórz "products" (id, category VARCHAR(50), price DECIMAL(10,2)). Wstaw
         * dane w 3 kategoriach. Wykonaj "SELECT category, MAX(price) AS max_price
         * FROM products GROUP BY category" jako podzapytanie w FROM, a NASTĘPNIE
         * JOIN-em (podglądowo) połącz wynik z oryginalną tabelą products, żeby
         * znaleźć KONKRETNE produkty będące najdroższymi w swojej kategorii - wypisz
         * te produkty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AllOperatorEquivalentUsingSubquery {
        /*
         * 🧪 Zadanie 17:
         * Utwórz "products" (id, price DECIMAL(10,2)) i wstaw 6 produktów. Napisz w
         * Javie odpowiednik operatora ALL (poza standardowym zakresem H2 w tej
         * lekcji): znajdź produkty droższe niż WSZYSTKIE produkty pewnej kategorii,
         * przez "WHERE price > (SELECT MAX(price) FROM products WHERE category =
         * 'Peryferia')" (skalarne MAX symuluje ALL) - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SubqueryDrivenDataCleanup {
        /*
         * 🧪 Zadanie 18:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT) - BEZ
         * ograniczenia FOREIGN KEY. Wstaw dane z kilkoma osieroconymi zamówieniami.
         * Wykonaj DELETE FROM orders WHERE customer_id NOT IN (SELECT id FROM
         * customers) - usunięcie osieroconych wierszy przez podzapytanie w WHERE - i
         * zweryfikuj (COUNT(*)) wynik przed/po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareSubqueryVsManualJavaLoop {
        /*
         * 🧪 Zadanie 19:
         * Utwórz "users" (id, name) i "orders" (id, user_id INT). Wstaw dane. Wykonaj
         * SQL-owe "WHERE EXISTS (...)" (jak w Zadaniu 1) i porównaj wynik z
         * RĘCZNYM odpowiednikiem w Javie: odczytaj OBIE tabele do list, i w Javie
         * (bez SQL-a) sprawdź dla każdego usera, czy istnieje jakieś zamówienie z
         * jego id (pętla zagnieżdżona albo Set) - zweryfikuj, że oba podejścia dają
         * IDENTYCZNY wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SubqueryPerformanceExistsVsInVsJoin {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "customers" (id) z 1000 wierszami i "orders" (id, customer_id INT)
         * z 3000 wierszami. Zmierz czas wykonania 3 RÓWNOWAŻNYCH zapytań znajdujących
         * klientów z zamówieniami: EXISTS, IN (SELECT...), i JOIN + DISTINCT
         * (podglądowo z Lesson14) - wypisz porównanie czasów wszystkich 3 podejść.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiLevelCorrelatedSubqueryReport {
        /*
         * 🧪 Zadanie 21:
         * Utwórz "customers" (id, name), "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw dane dla 5 klientów. Wykonaj JEDNO zapytanie z 3
         * podzapytaniami skorelowanymi w SELECT naraz: liczba zamówień, suma
         * zamówień, najwyższe zamówienie - każde jako niezależne "(SELECT ... WHERE
         * o.customer_id = c.id)" - wypisz kompletny raport per klient.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SubqueryBasedRankingWithoutWindowFunctions {
        /*
         * 🧪 Zadanie 22:
         * Utwórz "products" (id, name, price DECIMAL(10,2)) i wstaw 8 produktów.
         * Napisz zapytanie liczące "pozycję w rankingu cenowym" każdego produktu przez
         * podzapytanie skorelowane zliczające, ile INNYCH produktów jest droższych:
         * "SELECT name, price, (SELECT COUNT(*) FROM products p2 WHERE p2.price >
         * p1.price) + 1 AS ranking FROM products p1 ORDER BY ranking" - symulacja
         * RANK() bez window functions (poza zakresem kursu) - wypisz pełny ranking.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FindCustomersWithAboveAverageOrderValue {
        /*
         * 🧪 Zadanie 23:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw dane dla kilku klientów z różną liczbą i wartością
         * zamówień. Napisz zapytanie znajdujące klientów, których ŚREDNIA wartość
         * zamówienia (per klient) jest WYŻSZA niż OGÓLNA średnia wartość WSZYSTKICH
         * zamówień w systemie - połącz podzapytanie w FROM (średnia per klient) z
         * podzapytaniem skalarnym (średnia globalna) w JEDNYM zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SubqueryDrivenBusinessRuleValidation {
        /*
         * 🧪 Zadanie 24:
         * Utwórz "products" (id, name, stock INT) i "orders" (id, product_id INT,
         * quantity INT) - BEZ automatycznej walidacji przez CHECK. Napisz metodę
         * validateOrder(Statement stmt, int productId, int requestedQuantity), która
         * przez podzapytanie SELECT stock FROM products WHERE id = ? sprawdza, czy
         * requestedQuantity nie przekracza dostępnego stock - jeśli przekracza,
         * rzuca własny wyjątek. Przetestuj z poprawnym i niepoprawnym zamówieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FindGapsUsingNotExistsPattern {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "calendar_days" (day_number INT PRIMARY KEY) z wierszami 1-30 i
         * "events" (id, day_number INT). Wstaw zdarzenia tylko dla NIEKTÓRYCH dni.
         * Wykonaj "SELECT day_number FROM calendar_days d WHERE NOT EXISTS (SELECT 1
         * FROM events e WHERE e.day_number = d.day_number)" - klasyczny wzorzec
         * "znajdź brakujące dni" przez NOT EXISTS - wypisz dni BEZ żadnego zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SubqueryForAllExceptOneCondition {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw dane, gdzie WSZYSTKIE zamówienia jednego konkretnego
         * klienta przekraczają 500 zł, a innych klientów - nie. Wykonaj zapytanie
         * znajdujące klientów, których WSZYSTKIE zamówienia przekraczają 500 zł, przez
         * technikę "NOT EXISTS (SELECT 1 FROM orders o WHERE o.customer_id = c.id AND
         * o.amount <= 500)" (kwantyfikator "dla wszystkich" przez podwójne
         * przeczenie) - wypisz wynik i wyjaśnij logikę w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SubqueryInFromForPivotLikeReport {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "sales" (id, region VARCHAR(50), category VARCHAR(50), amount
         * DECIMAL(10,2)). Wstaw dane dla 2 regionów i 3 kategorii. Wykonaj
         * podzapytanie w FROM grupujące po (region, category) + SUM(amount), a
         * następnie w Javie (po odczycie tego już zagregowanego wyniku) zbuduj
         * "pivot" - tabelę z regionami jako wierszami i kategoriami jako kolumnami
         * (Map<String, Map<String, BigDecimal>>) i wypisz sformatowaną tabelę
         * przestawną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RecursiveLikeAncestorSearchWithoutCte {
        /*
         * 🧪 Zadanie 28:
         * Utwórz self-referencing "categories" (id, name, parent_id INT) z 3-poziomową
         * hierarchią. Napisz metodę findAllDescendants(Statement stmt, int
         * categoryId), która ZACZYNA od podzapytania "SELECT id FROM categories WHERE
         * parent_id = ?" (bezpośrednie dzieci), a potem REKURENCYJNIE (w Javie, bez
         * CTE - poza zakresem kursu) wywołuje się dla każdego znalezionego dziecka,
         * budując pełne drzewo potomków. Zademonstruj na kategorii głównej,
         * zwracając WSZYSTKICH potomków (dzieci i wnuki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SubqueryConsistencyCheckAcrossAggregations {
        /*
         * 🧪 Zadanie 29:
         * Utwórz "orders" (id, amount DECIMAL(10,2)) i "order_items" (id, order_id
         * INT, unit_price DECIMAL(10,2), quantity INT). Wstaw dane, gdzie
         * orders.amount POWINNO równać się sumie (unit_price*quantity) jego pozycji.
         * Napisz zapytanie z podzapytaniem skorelowanym w SELECT liczącym "prawdziwą"
         * sumę z order_items, porównaj ją (w Javie, po odczycie) z zapisanym
         * orders.amount i wypisz zamówienia, gdzie te dwie wartości SIĘ RÓŻNIĄ
         * (wykrywacz niespójności danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSubqueryDrivenAnalyticsReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj schemat: "customers" (id, name), "orders" (id, customer_id INT,
         * amount DECIMAL(10,2), order_date DATE), "products" (id, name, price
         * DECIMAL(10,2)), "order_items" (id, order_id INT, product_id INT, quantity
         * INT). Wstaw kompletne, realistyczne dane (min. 4 klientów, 6 zamówień, 5
         * produktów). Napisz KOMPLETNY raport analityczny wykorzystujący WSZYSTKIE 3
         * miejsca podzapytań naraz: (1) w WHERE z EXISTS - klienci z zamówieniem
         * powyżej średniej wartości, (2) w FROM - zagregowana tabela tymczasowa
         * "sprzedaz_per_produkt" użyta do znalezienia bestsellera, (3) w SELECT -
         * skorelowane podzapytanie liczące liczbę zamówień KAŻDEGO klienta. Wypisz
         * wszystkie 3 sekcje raportu pod czytelnymi nagłówkami.
         */
        public static void main(String[] args) { }
    }
}
