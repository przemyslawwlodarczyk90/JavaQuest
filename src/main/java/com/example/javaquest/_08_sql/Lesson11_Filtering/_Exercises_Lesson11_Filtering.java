package com.example.javaquest._08_sql.Lesson11_Filtering;

public class _Exercises_Lesson11_Filtering {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AndOperator {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_filter;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), category
         * VARCHAR(50), price DECIMAL(10,2)) i wstaw 6 produktów. Wykonaj "SELECT name
         * FROM products WHERE category = 'Peryferia' AND price > 50" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OrOperator {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "products" z Zadania 1. Wykonaj "SELECT name FROM products
         * WHERE category = 'Komputery' OR price < 30" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NotOperator {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "products". Wykonaj "SELECT name FROM products WHERE NOT
         * category = 'Peryferia'" i wypisz wynik - powinny się pojawić wszystkie
         * produkty NIE będące w kategorii 'Peryferia'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LikeStartsWith {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz tabelę "products" z co najmniej 2 nazwami zaczynającymi się na tę
         * samą literę. Wykonaj "SELECT name FROM products WHERE name LIKE 'K%'" i
         * wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LikeSingleCharacterWildcard {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz tabelę "products" z produktem "Mysz". Wykonaj "SELECT name FROM
         * products WHERE name LIKE '_ysz'" (dokładnie jeden znak przed "ysz") i
         * wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BetweenRange {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz tabelę "products" z różnymi cenami. Wykonaj "SELECT name, price
         * FROM products WHERE price BETWEEN 50 AND 500" i wypisz wynik, weryfikując,
         * że obie granice są WŁĄCZONE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InListOfValues {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz tabelę "products" z co najmniej 6 wierszami. Wykonaj "SELECT name
         * FROM products WHERE id IN (1, 3, 5)" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IsNullFiltering {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę "products" (id, name, description VARCHAR(200)) i wstaw 4
         * produkty, 2 z description = NULL. Wykonaj "SELECT name FROM products WHERE
         * description IS NULL" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IsNotNullFiltering {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz tabelę "products" z description z Zadania 8. Wykonaj "SELECT name
         * FROM products WHERE description IS NOT NULL" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CombiningAndOrWithParentheses {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz tabelę "products" z lekcji (6 produktów w 2 kategoriach, różne
         * ceny). Wykonaj "SELECT name FROM products WHERE (category = 'Peryferia' AND
         * price < 100) OR category = 'Komputery'" i wypisz wynik - zwróć uwagę na
         * nawiasy kontrolujące kolejność łączenia warunków.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LikeContainsSubstring {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "products" (id, name, description VARCHAR(200)) i wstaw 5
         * produktów z różnymi opisami. Wykonaj "SELECT name FROM products WHERE
         * description LIKE '%bezprzewodow%'" (dowolne znaki z obu stron) i wypisz
         * dopasowane produkty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CaseInsensitiveSearchPortablePattern {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "products" (id, name VARCHAR(100)) i wstaw produkty z
         * MIESZANĄ wielkością liter (np. "Laptop", "KLAWIATURA"). Wykonaj
         * przenośny, case-insensitive wzorzec z lekcji "WHERE LOWER(name) LIKE
         * LOWER('%laptop%')" i wypisz wynik - porównaj (opisowo w println) z ILIKE,
         * które NIE jest standardem SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExistsWithCorrelatedSubquery {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "products" (id, name) i "orders" (id, product_id INT). Wstaw 4
         * produkty, ale zamówienia tylko dla 2 z nich. Wykonaj "SELECT p.name FROM
         * products p WHERE EXISTS (SELECT 1 FROM orders o WHERE o.product_id =
         * p.id)" i wypisz produkty, które mają PRZYNAJMNIEJ jedno zamówienie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NotExistsFindingUnmatched {
        /*
         * 🧪 Zadanie 14:
         * Odtwórz products/orders z Zadania 13. Wykonaj "SELECT p.name FROM products
         * p WHERE NOT EXISTS (SELECT 1 FROM orders o WHERE o.product_id = p.id)" i
         * wypisz produkty BEZ żadnego zamówienia (dopełnienie wyniku z Zadania 13).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InWithSubqueryInsteadOfLiteralList {
        /*
         * 🧪 Zadanie 15:
         * Utwórz "products" (id, name, category VARCHAR(50)) i "featured_categories"
         * (category VARCHAR(50)). Wstaw kilka produktów w różnych kategoriach i 2
         * "wyróżnione" kategorie. Wykonaj "SELECT name FROM products WHERE category
         * IN (SELECT category FROM featured_categories)" - IN z podzapytaniem
         * (miejsce, gdzie IN i EXISTS się łączą) - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleLikePatternsCombinedWithOr {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "products" (id, name VARCHAR(100)) i wstaw 6 produktów.
         * Wykonaj "SELECT name FROM products WHERE name LIKE 'K%' OR name LIKE 'M%'"
         * (nazwy zaczynające się na K LUB M) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_NotInExcludingValues {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50)) z produktami w 4
         * kategoriach. Wykonaj "SELECT name, category FROM products WHERE category
         * NOT IN ('Peryferia', 'Akcesoria')" i wypisz produkty pozostałych kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BetweenWithDates {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "orders" (id, order_date DATE) i wstaw 5 zamówień z różnymi
         * datami (styczeń-marzec 2026). Wykonaj "SELECT id, order_date FROM orders
         * WHERE order_date BETWEEN '2026-01-15' AND '2026-02-15'" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ComplexConditionWithNullAndComparison {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 5 produktów, jeden z category = NULL. Wykonaj
         * "SELECT name FROM products WHERE category != 'Peryferia' OR category IS
         * NULL" - poprawny wzorzec omijający pułapkę NULL w porównaniu (patrz
         * Lesson05_NullValues) - wypisz wynik i skomentuj, czemu sam "category !=
         * 'Peryferia'" pominąłby wiersz z NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExistsPerformanceVsInDiscussion {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT) z 1000
         * klientami i 3000 zamówieniami. Napisz i wykonaj DWA równoważne zapytania
         * znajdujące klientów z zamówieniami: jedno z EXISTS (podzapytanie), drugie z
         * IN (SELECT customer_id FROM orders) - zmierz czas obu (System.nanoTime())
         * i wypisz porównanie, komentując w println, czemu EXISTS bywa szybszy dla
         * dużych zbiorów (baza przerywa po pierwszym dopasowaniu).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiConditionCustomerSegmentation {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "customers" (id, name, age INT, city VARCHAR(50), total_spent
         * DECIMAL(10,2)) i wstaw 10 klientów z różnymi kombinacjami danych. Napisz
         * zapytanie segmentujące "VIP" klientów: (age BETWEEN 25 AND 45) AND (city IN
         * ('Warszawa', 'Krakow', 'Wroclaw')) AND (total_spent > 1000 OR total_spent IS
         * NULL) - połączenie AND/OR/BETWEEN/IN/IS NULL w jednym złożonym warunku.
         * Wypisz wynikową listę VIP klientów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DynamicFilterBuilderFromCriteria {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2)) i wstaw 10 produktów. Napisz metodę buildAndRunFilter
         * (Statement stmt, String categoryOrNull, BigDecimal minPriceOrNull), która
         * DYNAMICZNIE buduje klauzulę WHERE (dodając "category = ?" i/lub "price >=
         * ?" TYLKO jeśli odpowiedni parametr nie jest null, łącząc przez AND) używając
         * PreparedStatement z odpowiednią liczbą "?". Przetestuj z 3 różnymi
         * kombinacjami parametrów (oba podane, tylko kategoria, brak żadnego -
         * wszystkie produkty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LikePatternInjectionAwareness {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "products" (id, name VARCHAR(100)) i wstaw produkty, w tym
         * jeden z nazwą zawierającą znak specjalny "%" (np. "Rabat 50% Voucher").
         * Napisz metodę searchByName(Statement stmt, String rawSearchTerm), która
         * BEZPIECZNIE escape'uje znaki specjalne LIKE (% i _) w rawSearchTerm PRZED
         * zbudowaniem wzorca "%<escaped>%" (użyj PreparedStatement + ESCAPE '\' w
         * SQL), żeby wyszukiwanie dosłownego "%" działało poprawnie. Zademonstruj
         * wyszukiwanie frazy zawierającej znak "%".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NestedExistsAcrossThreeTables {
        /*
         * 🧪 Zadanie 24:
         * Utwórz "customers" (id, name), "orders" (id, customer_id INT), "order_items"
         * (id, order_id INT, product_id INT). Wstaw dane tak, by niektórzy klienci
         * mieli zamówienia BEZ pozycji, a inni z pozycjami. Wykonaj zapytanie z
         * ZAGNIEŻDŻONYM EXISTS: znajdź klientów, którzy mają PRZYNAJMNIEJ jedno
         * zamówienie z PRZYNAJMNIEJ jedną pozycją ("WHERE EXISTS (SELECT 1 FROM
         * orders o WHERE o.customer_id = c.id AND EXISTS (SELECT 1 FROM order_items
         * oi WHERE oi.order_id = o.id))"). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FilterCombinationTestMatrix {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50), price
         * DECIMAL(10,2), description VARCHAR(200)) z 12 zróżnicowanymi wierszami
         * (część z description = NULL). Napisz metodę runFilterMatrix(Statement
         * stmt), która wykonuje PO KOLEI 6 różnych zapytań (AND, OR, NOT, LIKE,
         * BETWEEN+IN razem, IS NULL+OR) i dla każdego wypisuje nagłówek z opisem
         * warunku oraz liczbę dopasowanych wierszy - zbuduj z tego czytelny raport
         * porównawczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExistsVsJoinPreviewEquivalence {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "authors" (id, name) i "books" (id, author_id INT, title). Wstaw
         * dane z autorami mającymi 0, 1 lub więcej książek. Wykonaj DWA równoważne
         * zapytania zwracające autorów z co najmniej jedną książką: (A) z EXISTS, (B)
         * z "WHERE id IN (SELECT DISTINCT author_id FROM books)" - porównaj w Javie
         * (Set<String> z obu wyników), że dają IDENTYCZNY zestaw nazwisk, mimo różnej
         * składni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ComplexBusinessRuleFilterWithAllOperators {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "job_applications" (id, candidate_name VARCHAR(100),
         * years_experience INT, desired_salary DECIMAL(10,2), status VARCHAR(20),
         * notes VARCHAR(200)) i wstaw 10 kandydatów z realistycznymi, zróżnicowanymi
         * danymi (część z notes = NULL). Napisz zapytanie kwalifikujące kandydatów do
         * rozmowy: (years_experience BETWEEN 2 AND 10) AND (desired_salary <= 12000)
         * AND (status IN ('NEW', 'REVIEWING')) AND (notes IS NULL OR notes NOT LIKE
         * '%odrzucony%') - użyj WSZYSTKICH operatorów z lekcji naraz w jednym
         * zapytaniu i wypisz kwalifikujących się kandydatów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SubqueryFilterWithAggregateThreshold {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "customers" (id, name) i "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Wstaw kilku klientów z różną liczbą i wartością zamówień.
         * Wykonaj zapytanie znajdujące klientów, których PRZYNAJMNIEJ JEDNO zamówienie
         * przekracza 500 zł: "WHERE EXISTS (SELECT 1 FROM orders o WHERE
         * o.customer_id = c.id AND o.amount > 500)" - a następnie osobne zapytanie
         * znajdujące klientów, których WSZYSTKIE zamówienia przekraczają 500 zł (przez
         * "WHERE NOT EXISTS (SELECT 1 FROM orders o WHERE o.customer_id = c.id AND
         * o.amount <= 500)" - klasyczna technika "NOT EXISTS przeczenia" dla
         * kwantyfikatora "dla wszystkich"). Wypisz i porównaj oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullTextLikeSearchAcrossMultipleColumns {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "products" (id, name VARCHAR(100), description VARCHAR(300),
         * tags VARCHAR(200)) i wstaw 10 produktów z realistycznymi, zróżnicowanymi
         * danymi. Napisz metodę searchAnywhere(Statement stmt, String term), która
         * wykonuje "WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ? OR
         * LOWER(tags) LIKE ?" (przez PreparedStatement, term opakowany w %...%,
         * zunifikowany do lowercase) - symulacja prostego "wyszukiwania po całym
         * produkcie". Przetestuj wyszukiwanie frazy, która trafia TYLKO w description
         * jednego produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAdvancedProductSearchEngine {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj tabelę "products" (id, name VARCHAR(100), category VARCHAR(50),
         * price DECIMAL(10,2), in_stock BOOLEAN, description VARCHAR(300)) z 15
         * zróżnicowanymi produktami (różne kategorie, ceny, dostępność, część z
         * description = NULL). Zaprojektuj KOMPLETNY "search engine" w Javie:
         * metoda search(Statement stmt, String nameContainsOrNull, List<String>
         * categoriesOrNull, BigDecimal minPriceOrNull, BigDecimal maxPriceOrNull,
         * Boolean inStockOnly), która dynamicznie łączy WSZYSTKIE podane kryteria
         * przez AND (LIKE dla nazwy, IN dla kategorii, BETWEEN-podobne dla ceny min/max,
         * = TRUE dla dostępności) - budując zapytanie tylko z kryteriów, które NIE są
         * null, przez PreparedStatement. Przetestuj metodę z 4 różnymi kombinacjami
         * parametrów (od "wszystko" do "wszystkie kryteria naraz") i wypisz wyniki
         * każdego wywołania z nagłówkiem opisującym użyte kryteria.
         */
        public static void main(String[] args) { }
    }
}
