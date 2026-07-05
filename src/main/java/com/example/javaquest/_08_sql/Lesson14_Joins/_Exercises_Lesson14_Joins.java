package com.example.javaquest._08_sql.Lesson14_Joins;

public class _Exercises_Lesson14_Joins {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InnerJoinBasic {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_join;DB_CLOSE_DELAY=-1). Utwórz
         * "users" (id INT PRIMARY KEY, name VARCHAR(100)) i "orders" (id INT PRIMARY
         * KEY, user_id INT, amount DECIMAL(10,2)). Wstaw 2 użytkowników i 3
         * zamówienia. Wykonaj "SELECT u.name, o.amount FROM users u INNER JOIN orders
         * o ON o.user_id = u.id" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LeftJoinIncludesUnmatched {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz users/orders z Zadania 1, ale dodaj TRZECIEGO użytkownika BEZ
         * żadnego zamówienia. Wykonaj "SELECT u.name, o.amount FROM users u LEFT JOIN
         * orders o ON o.user_id = u.id" i wypisz wynik - trzeci użytkownik powinien
         * się pojawić z NULL w kolumnie amount.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RightJoinIncludesUnmatchedOrders {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz users/orders z Zadania 1, ale dodaj zamówienie z user_id, który NIE
         * istnieje w users (osierocone zamówienie, bez ograniczenia FOREIGN KEY).
         * Wykonaj "SELECT u.name, o.id, o.amount FROM users u RIGHT JOIN orders o ON
         * o.user_id = u.id" i wypisz wynik - osierocone zamówienie powinno się
         * pojawić z NULL w name.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CrossJoinCartesianProduct {
        /*
         * 🧪 Zadanie 4:
         * Utwórz "sizes" (id, label VARCHAR(10)) z 2 rozmiarami i "colors" (id, name
         * VARCHAR(20)) z 3 kolorami. Wykonaj "SELECT s.label, c.name FROM sizes s
         * CROSS JOIN colors c" i wypisz wynik - powinno być 2*3=6 wierszy (wszystkie
         * kombinacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SelfJoinManagerHierarchy {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "employees" (id INT PRIMARY KEY, name VARCHAR(100),
         * manager_id INT). Wstaw 1 pracownika bez managera (manager_id=NULL) i 2
         * podwładnych wskazujących na niego. Wykonaj "SELECT e.name AS pracownik, m.name
         * AS manager FROM employees e LEFT JOIN employees m ON e.manager_id = m.id"
         * (self join z dwoma aliasami) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JoinWithAdditionalWhereFilter {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz users/orders z Zadania 1 z 3 zamówieniami różnej wartości. Wykonaj
         * "SELECT u.name, o.amount FROM users u INNER JOIN orders o ON o.user_id =
         * u.id WHERE o.amount > 50" (JOIN + WHERE razem, poznane w Lesson11) i wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_JoinThreeColumnsSelected {
        /*
         * 🧪 Zadanie 7:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, order_date
         * DATE). Wstaw dane i wykonaj JOIN pokazujący nazwisko klienta, id zamówienia
         * i datę zamówienia w formacie "<name> | zamowienie #<id> | <order_date>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountRowsFromEachJoinType {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz users/orders z osieroconym zamówieniem i użytkownikiem bez
         * zamówień (jak w Zadaniach 2 i 3 połączonych). Wykonaj INNER JOIN, LEFT
         * JOIN i RIGHT JOIN na tych samych danych i dla każdego wypisz LICZBĘ
         * zwróconych wierszy - porównaj 3 liczby i skomentuj różnicę w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_JoinWithAliasesForReadability {
        /*
         * 🧪 Zadanie 9:
         * Utwórz "products" (id, name) i "reviews" (id, product_id INT, rating INT).
         * Wykonaj JOIN z krótkimi aliasami tabel ("p" i "r") - "SELECT p.name,
         * r.rating FROM products p JOIN reviews r ON r.product_id = p.id" - i wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_JoinVocabularyOverviewPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz na konsoli krótkie opisy 5 typów JOIN z
         * lekcji (INNER, LEFT, RIGHT, CROSS, SELF) w formacie "<typ>: <opis>".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeTableJoinChain {
        /*
         * 🧪 Zadanie 11:
         * Utwórz "authors" (id, name), "books" (id, author_id INT, title,
         * publisher_id INT), "publishers" (id, name). Wstaw dane i wykonaj JOIN
         * łączący WSZYSTKIE 3 tabele naraz, wypisując tytuł, autora i wydawcę każdej
         * książki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LeftJoinFindingEntitiesWithoutRelation {
        /*
         * 🧪 Zadanie 12:
         * Utwórz "authors" (id, name) i "books" (id, author_id INT, title). Wstaw
         * kilku autorów, w tym co najmniej jednego BEZ żadnej książki. Wykonaj "SELECT
         * a.name FROM authors a LEFT JOIN books b ON b.author_id = a.id WHERE b.id IS
         * NULL" (LEFT JOIN + WHERE ... IS NULL = klasyczny wzorzec "znajdź brakujące
         * powiązania") i wypisz autorów bez żadnej książki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_JoinWithGroupByAggregation {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "authors" (id, name) i "books" (id, author_id INT, title). Wstaw 2
         * autorów z różną liczbą książek. Wykonaj "SELECT a.name, COUNT(b.id) AS
         * liczba_ksiazek FROM authors a LEFT JOIN books b ON b.author_id = a.id GROUP
         * BY a.name" (JOIN + GROUP BY z Lesson13 razem) i wypisz liczbę książek
         * każdego autora (włącznie z 0, jeśli autor nie ma książek - dzięki LEFT
         * JOIN).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManyToManyJunctionTableJoin {
        /*
         * 🧪 Zadanie 14:
         * Utwórz "students" (id, name), "courses" (id, name) i tabelę pośrednią
         * "students_courses" (student_id INT, course_id INT). Wstaw 3 studentów, 2
         * kursy i kilka przypisań. Wykonaj JOIN łączący WSZYSTKIE 3 tabele (students
         * JOIN students_courses JOIN courses), wypisując "<student> - <kurs>" dla
         * każdej pary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleLeftJoinsWithOptionalRelations {
        /*
         * 🧪 Zadanie 15:
         * Utwórz "employees" (id, name, department_id INT, manager_id INT),
         * "departments" (id, name). Wstaw pracowników, gdzie niektórzy nie mają
         * działu (NULL) i niektórzy nie mają managera (NULL). Wykonaj zapytanie z
         * DWOMA LEFT JOIN naraz (do departments i self-join do employees jako manager)
         * i wypisz dla każdego pracownika: imię, nazwę działu (lub "brak działu") i
         * imię managera (lub "brak managera").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FullJoinEmulationViaUnion {
        /*
         * 🧪 Zadanie 16:
         * Odtwórz users/orders z osieroconym zamówieniem i użytkownikiem bez
         * zamówień. Wykonaj emulację FULL JOIN przez UNION z lekcji: LEFT JOIN UNION
         * RIGHT JOIN (H2 2.3.232 nie wspiera FULL JOIN/FULL OUTER JOIN wcale) i wypisz
         * wynik, weryfikując, że pojawiają się WSZYSCY użytkownicy I wszystkie
         * zamówienia (z NULL po brakującej stronie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_JoinWithBetweenAndLikeFilters {
        /*
         * 🧪 Zadanie 17:
         * Utwórz "products" (id, name VARCHAR(100), category_id INT) i "categories"
         * (id, name VARCHAR(50)). Wstaw dane i wykonaj JOIN z warunkiem filtrującym
         * łączącym LIKE (Lesson11) na nazwie produktu i konkretną kategorię - "SELECT
         * p.name, c.name AS category FROM products p JOIN categories c ON c.id =
         * p.category_id WHERE p.name LIKE 'K%'" - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SelfJoinFindingPairsWithSameAttribute {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "products" (id, name VARCHAR(100), category VARCHAR(50)).
         * Wstaw 5 produktów, kilka w tej samej kategorii. Wykonaj self join
         * znajdujący WSZYSTKIE PARY różnych produktów z tej samej kategorii: "SELECT
         * p1.name, p2.name FROM products p1 JOIN products p2 ON p1.category =
         * p2.category AND p1.id < p2.id" (warunek p1.id < p2.id unika duplikatów i
         * parowania produktu z samym sobą) - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_JoinResultUsedForUpdateDecision {
        /*
         * 🧪 Zadanie 19:
         * Utwórz "customers" (id, name, vip BOOLEAN DEFAULT FALSE) i "orders" (id,
         * customer_id INT, amount DECIMAL(10,2)). Wstaw klientów i zamówienia. Wykonaj
         * JOIN + GROUP BY, żeby znaleźć klientów z SUM(amount) > 500 (w Javie, po
         * odczycie wyniku zapytania), a następnie dla KAŻDEGO z nich wykonaj UPDATE
         * customers SET vip = TRUE WHERE id = ? - zweryfikuj przez SELECT, że
         * właściwi klienci zostali oznaczeni jako VIP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareInnerVsLeftJoinRowCountsSystematically {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "categories" (id, name) i "products" (id, category_id INT, name).
         * Wstaw 4 kategorie, z których jedna nie ma żadnego produktu, i 6 produktów
         * rozłożonych między pozostałe 3 kategorie. Wykonaj INNER JOIN i LEFT JOIN
         * między categories i products, dla obu policz liczbę wierszy i liczbę
         * UNIKALNYCH kategorii w wyniku (Set<String> w Javie) - wypisz porównanie,
         * pokazując, że tylko LEFT JOIN "widzi" kategorię bez produktów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FourTableJoinFullOrderReport {
        /*
         * 🧪 Zadanie 21:
         * Utwórz "customers" (id, name), "orders" (id, customer_id INT, order_date
         * DATE), "order_items" (id, order_id INT, product_id INT, quantity INT),
         * "products" (id, name, price DECIMAL(10,2)). Wstaw kompletne dane (2
         * klientów, 3 zamówienia, kilka pozycji, 4 produkty). Wykonaj JOIN łączący
         * WSZYSTKIE 4 tabele naraz, wypisując dla każdej pozycji zamówienia: klienta,
         * datę zamówienia, nazwę produktu, ilość i wartość (quantity * price).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiLevelSelfJoinOrganizationChart {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz self-referencing "employees" (id, name, manager_id) do 3 poziomów
         * hierarchii (dyrektor -> kierownik -> pracownik). Wykonaj DWUKROTNY self
         * join (alias dla managera i osobny alias dla "managera managera"), wypisując
         * dla każdego pracownika jego bezpośredniego przełożonego I przełożonego
         * przełożonego (lub "brak", gdy nie istnieje na danym poziomie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_JoinWithHavingOnAggregatedRelation {
        /*
         * 🧪 Zadanie 23:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw dane dla 5 klientów z różną liczbą i wartością
         * zamówień. Wykonaj JOIN + GROUP BY + HAVING (Lesson13) łączące SUM(amount) >
         * 300 I COUNT(*) >= 2 naraz, żeby znaleźć "wartościowych, lojalnych" klientów
         * - wypisz wynik z pełnymi statystykami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FullOuterJoinEmulationWithDeduplicationVerification {
        /*
         * 🧪 Zadanie 24:
         * Odtwórz users/orders z osieroconym zamówieniem, użytkownikiem bez
         * zamówień, ORAZ co najmniej jednym poprawnie dopasowanym parowaniem. Wykonaj
         * emulację FULL JOIN przez UNION (bez ALL, jak w lekcji) i zweryfikuj w Javie,
         * że UNION poprawnie USUNĄŁ duplikat dopasowanej pary, która wystąpiłaby w
         * OBU częściach (LEFT i RIGHT JOIN) - policz i wypisz oczekiwaną liczbę
         * wierszy (użytkownicy + osierocone zamówienia, bez podwójnego liczenia
         * dopasowań) i porównaj z faktycznym wynikiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_JoinBasedDataQualityAudit {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "orders" (id, customer_id INT) i "customers" (id, name) - BEZ
         * ograniczenia FOREIGN KEY (celowo). Wstaw dane z kilkoma osieroconymi
         * zamówieniami (customer_id nieistniejący). Napisz metodę auditOrphans
         * (Statement stmt), która przez RIGHT JOIN (albo LEFT JOIN z odwrotną
         * kolejnością tabel) znajduje WSZYSTKIE osierocone zamówienia (customer
         * name = NULL) i wypisuje raport "znaleziono N osieroconych zamowien" z
         * listą ich id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CrossJoinForCombinatorialGeneration {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "sizes" (id, label VARCHAR(10)) z 3 rozmiarami, "colors" (id, name
         * VARCHAR(20)) z 3 kolorami i pustą tabelę "product_variants" (size_label
         * VARCHAR(10), color_name VARCHAR(20)). Użyj CROSS JOIN, aby WYGENEROWAĆ
         * WSZYSTKIE 9 kombinacji rozmiar+kolor (typowy przypadek użycia CROSS JOIN -
         * generowanie wariantów produktu) i wstaw je do product_variants przez
         * "INSERT INTO product_variants SELECT s.label, c.name FROM sizes s CROSS
         * JOIN colors c". Wypisz wygenerowane warianty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_JoinPerformanceWithAndWithoutIndexOnForeignKey {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "customers" (id INT PRIMARY KEY, name VARCHAR(100)) z 1000 wierszami
         * i "orders" (id INT PRIMARY KEY, customer_id INT, amount DECIMAL(10,2)) z
         * 5000 wierszami (customer_id BEZ indeksu). Zmierz czas wykonania JOIN-a
         * between tymi tabelami (z warunkiem WHERE na konkretnym kliencie), następnie
         * utwórz CREATE INDEX na orders(customer_id) (podglądowo, dokładnie w
         * Lesson18) i zmierz czas TEGO SAMEGO zapytania - wypisz porównanie czasów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RecursiveLikeHierarchyTraversalWithSelfJoinLimitation {
        /*
         * 🧪 Zadanie 28:
         * Odtwórz self-referencing "employees" (id, name, manager_id) z 4 poziomami
         * hierarchii. Zademonstruj OGRANICZENIE zwykłego self join: pokaż, że JOIN
         * "na sztywno" (2-3 aliasy) pokazuje TYLKO ustaloną z góry liczbę poziomów -
         * napisz WŁAŚCIWĄ, elastyczną wersję w Javie: metoda findFullChain(Statement
         * stmt, int employeeId), która pętlą (osobne zapytania, nie JOIN) wspina się
         * po manager_id do samego szczytu, niezależnie od głębokości hierarchii, i
         * wypisuje pełny łańcuch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ComplexReportCombiningAllJoinTypes {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj schemat: "customers" (id, name), "orders" (id, customer_id INT), z
         * co najmniej jednym klientem bez zamówień i jednym osieroconym zamówieniem.
         * Napisz metodę generateFullReport(Statement stmt), która wykonuje PO KOLEI:
         * (1) INNER JOIN - "dopasowane" pary, (2) LEFT JOIN - klienci wraz z brakami,
         * (3) RIGHT JOIN - zamówienia wraz z brakami, (4) UNION-ową emulację FULL
         * JOIN - łącząc rezultaty w JEDEN raport z 4 sekcjami, każda z osobnym
         * nagłówkiem i liczbą wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneEcommerceAnalyticsWithAllJoinPatterns {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletny schemat sklepu: "customers" (id, name), "orders" (id,
         * customer_id INT, order_date DATE), "order_items" (id, order_id INT,
         * product_id INT, quantity INT), "products" (id, name, category_id INT,
         * price DECIMAL(10,2)), "categories" (id, name) - z co najmniej jednym
         * klientem bez zamówień i jednym produktem bez żadnej sprzedaży. Wstaw pełne,
         * realistyczne dane (min. 3 klientów, 4 zamówienia, 5 produktów w 2
         * kategoriach). Napisz KOMPLETNY raport analityczny wykorzystujący WSZYSTKIE
         * poznane typy JOIN naraz: (1) 5-tabelowy INNER JOIN - pełne szczegóły
         * każdego zamówienia, (2) LEFT JOIN - klienci BEZ zamówień, (3) LEFT JOIN +
         * GROUP BY - suma wydatków per klient (włącznie z 0 dla klientów bez
         * zamówień), (4) LEFT JOIN products->order_items - produkty, które NIGDY nie
         * zostały zamówione. Wypisz wszystkie 4 sekcje pod czytelnymi nagłówkami.
         */
        public static void main(String[] args) { }
    }
}
