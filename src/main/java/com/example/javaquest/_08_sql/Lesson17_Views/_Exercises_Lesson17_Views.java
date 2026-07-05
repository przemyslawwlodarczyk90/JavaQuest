package com.example.javaquest._08_sql.Lesson17_Views;

public class _Exercises_Lesson17_Views {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateSimpleView {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_view;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price
         * DECIMAL(10,2)) i wstaw 5 produktów. Utwórz widok "cheap_products" jako
         * "CREATE VIEW cheap_products AS SELECT name, price FROM products WHERE
         * price < 100". Wykonaj "SELECT * FROM cheap_products" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ViewOverJoin {
        /*
         * 🧪 Zadanie 2:
         * Utwórz "departments" (id, name) i "employees" (id, name, department_id
         * INT, salary INT). Wstaw dane. Utwórz widok "employee_overview" łączący obie
         * tabele przez JOIN (jak w lekcji), a potem wykonaj "SELECT * FROM
         * employee_overview ORDER BY employee_name" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_QueryViewWithWhere {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz widok "employee_overview" z Zadania 2. Wykonaj "SELECT
         * employee_name, salary FROM employee_overview WHERE department_name = 'IT'
         * ORDER BY salary DESC" (filtrowanie widoku jak zwykłej tabeli) i wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ViewReflectsUnderlyingDataChanges {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz widok "employee_overview" z Zadania 2. Wykonaj SELECT * FROM
         * employee_overview i wypisz wynik. Wstaw NOWEGO pracownika bezpośrednio do
         * tabeli "employees" (INSERT). Wykonaj TO SAMO zapytanie na widoku jeszcze
         * raz - zweryfikuj, że nowy pracownik POJAWIA SIĘ w widoku BEZ żadnej zmiany
         * w samym widoku (widok nie przechowuje własnych danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ViewHidingComplexity {
        /*
         * 🧪 Zadanie 5:
         * Utwórz "orders" (id, customer_name VARCHAR(100), amount DECIMAL(10,2),
         * status VARCHAR(20)) z 6 zamówieniami o różnych statusach. Utwórz widok
         * "paid_orders" jako "CREATE VIEW paid_orders AS SELECT customer_name, amount
         * FROM orders WHERE status = 'PAID'" - ukrywający szczegóły filtrowania przed
         * odbiorcą widoku. Wykonaj SELECT * FROM paid_orders i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ViewWithColumnAliases {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "products" (id, name VARCHAR(100), price DECIMAL(10,2)).
         * Utwórz widok "product_prices_pln" jako "CREATE VIEW product_prices_pln AS
         * SELECT name AS nazwa, price AS cena_pln FROM products" (widok z aliasami
         * kolumn). Wykonaj SELECT * FROM product_prices_pln i wypisz wynik używając
         * aliasów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ViewLimitedColumnsHidesSensitiveData {
        /*
         * 🧪 Zadanie 7:
         * Utwórz "employees" (id, name VARCHAR(100), salary INT, ssn VARCHAR(20)) -
         * ssn to "wrażliwe" dane. Utwórz widok "employees_public" pokazujący TYLKO
         * id i name (bez salary i ssn) - "CREATE VIEW employees_public AS SELECT id,
         * name FROM employees". Wykonaj SELECT * FROM employees_public i wypisz,
         * potwierdzając, że widok ogranicza dostęp do wybranych kolumn.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ViewVocabularyPrint {
        /*
         * 🧪 Zadanie 8:
         * Bez łączenia się z bazą: wypisz println wyjaśnienie, czym jest VIEW z
         * lekcji ("zapisane zapytanie") i wymień 3 powody, dla których warto go
         * używać (uproszczenie złożonych zapytań, ukrywanie struktury bazy,
         * ograniczenie dostępu do kolumn/wierszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DropView {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "products" i widok "expensive_products" (WHERE price >
         * 500). Wykonaj SELECT * FROM expensive_products - powinno się udać. Wykonaj
         * DROP VIEW expensive_products, a potem spróbuj wykonać TO SAMO zapytanie -
         * złap SQLException i wypisz komunikat, potwierdzając, że widok już nie
         * istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ViewIsReadOnlyForJoinBasedView {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz widok "employee_overview" z Zadania 2 (zawiera JOIN). Spróbuj
         * wykonać "INSERT INTO employee_overview VALUES (...)" - złap SQLException i
         * wypisz komunikat, potwierdzając, że widok z JOIN-em jest z lekcji
         * WYŁĄCZNIE do odczytu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ViewWithAggregation {
        /*
         * 🧪 Zadanie 11:
         * Utwórz "orders" (id, customer_id INT, amount DECIMAL(10,2)). Wstaw dane
         * dla kilku klientów. Utwórz widok "customer_totals" jako "CREATE VIEW
         * customer_totals AS SELECT customer_id, SUM(amount) AS total_spent FROM
         * orders GROUP BY customer_id" (widok z GROUP BY, podglądowo z Lesson13).
         * Wykonaj SELECT * FROM customer_totals ORDER BY total_spent DESC i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ViewOfViewNestedComposition {
        /*
         * 🧪 Zadanie 12:
         * Odtwórz widok "customer_totals" z Zadania 11. Utwórz DRUGI widok
         * "vip_customers" bazujący NA PIERWSZYM widoku: "CREATE VIEW vip_customers AS
         * SELECT customer_id, total_spent FROM customer_totals WHERE total_spent >
         * 500" (widok nad widokiem). Wykonaj SELECT * FROM vip_customers i wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ViewWithJoinAndFilterAndOrder {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "products" (id, name, category_id INT, price DECIMAL(10,2)) i
         * "categories" (id, name). Utwórz widok "products_with_category" łączący obie
         * tabele przez JOIN. Wykonaj na tym widoku zapytanie z WHERE (konkretna
         * kategoria) i ORDER BY (cena malejąco) - jak na zwykłej tabeli - i wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareViewQueryVsManualJoinQuery {
        /*
         * 🧪 Zadanie 14:
         * Odtwórz "products_with_category" z Zadania 13. Wykonaj IDENTYCZNE
         * zapytanie DWA razy: raz przez widok (SELECT * FROM
         * products_with_category), raz przez "ręczny" JOIN na oryginalnych tabelach
         * (SELECT ... FROM products p JOIN categories c ...) - porównaj oba wyniki w
         * Javie i zweryfikuj, że są IDENTYCZNE (widok to tylko "zapisany" JOIN).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ViewUpdatePropagationAfterUpdate {
        /*
         * 🧪 Zadanie 15:
         * Odtwórz widok "employee_overview" (z JOIN). Wykonaj SELECT na widoku i
         * zapisz wynagrodzenie jednego pracownika. Wykonaj UPDATE employees SET
         * salary = ... WHERE id = ... bezpośrednio na TABELI (nie na widoku).
         * Wykonaj TO SAMO zapytanie na widoku jeszcze raz i zweryfikuj, że nowa
         * pensja jest widoczna natychmiast, bez żadnej akcji na widoku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ViewAsSimplificationForRepeatedQuery {
        /*
         * 🧪 Zadanie 16:
         * Utwórz "orders" (id, customer_name, amount DECIMAL(10,2), order_date
         * DATE). Napisz metodę getRecentHighValueOrdersWithoutView(Statement stmt),
         * powtarzającą złożone zapytanie (WHERE amount > 100 AND order_date > pewna
         * data) w 2 miejscach (symulacja duplikacji logiki). Następnie utwórz widok
         * "recent_high_value_orders" z TĄ SAMĄ logiką RAZ i wywołaj go z 2 różnych
         * miejsc w kodzie - skomentuj w println korzyść z uproszczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ViewWithDistinctAndDependentJoin {
        /*
         * 🧪 Zadanie 17:
         * Utwórz "orders" (id, customer_id INT) i "customers" (id, name). Utwórz
         * widok "customers_with_orders" jako "CREATE VIEW customers_with_orders AS
         * SELECT DISTINCT c.name FROM customers c JOIN orders o ON o.customer_id =
         * c.id" (widok z JOIN + DISTINCT). Wykonaj SELECT * FROM
         * customers_with_orders i wypisz unikalnych klientów z co najmniej jednym
         * zamówieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AttemptUpdateSimpleViewWithoutJoin {
        /*
         * 🧪 Zadanie 18:
         * Utwórz "products" (id, name, price DECIMAL(10,2)). Utwórz PROSTY widok
         * (BEZ JOIN, GROUP BY, DISTINCT) "products_view" jako "CREATE VIEW
         * products_view AS SELECT id, name, price FROM products WHERE price > 0".
         * Spróbuj wykonać UPDATE products_view SET price = ... WHERE id = ... -
         * sprawdź empirycznie w H2, czy się udało (proste widoki BEZ JOIN/GROUP
         * BY/DISTINCT bywają aktualizowalne wg lekcji) - wypisz wynik i zweryfikuj
         * zmianę w oryginalnej tabeli "products".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ViewBasedAccessControlSimulation {
        /*
         * 🧪 Zadanie 19:
         * Utwórz "employees" (id, name, department VARCHAR(50), salary INT).
         * Zasymuluj "kontrolę dostępu" przez widoki: utwórz widok "hr_view" (WSZYSTKIE
         * kolumny - dla działu HR) i widok "public_view" (TYLKO id, name, department -
         * bez salary, dla "zwykłych" odbiorców). Wykonaj SELECT na obu widokach i
         * wypisz, jaką ilość informacji "widzi" każdy z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ViewConsistencyAfterSchemaChange {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "products" (id, name, price DECIMAL(10,2)) i widok "products_view"
         * (SELECT * FROM products). Wykonaj ALTER TABLE products ADD COLUMN in_stock
         * BOOLEAN DEFAULT TRUE (podglądowo z Lesson08). Wykonaj SELECT * FROM
         * products_view PONOWNIE i zweryfikuj, czy nowa kolumna jest widoczna w
         * widoku zdefiniowanym jako "SELECT *" - wypisz wynik i skomentuj obserwację
         * w println.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiTableViewWithAggregationAndFilter {
        /*
         * 🧪 Zadanie 21:
         * Utwórz "customers" (id, name), "orders" (id, customer_id INT, amount
         * DECIMAL(10,2)). Utwórz widok "customer_summary" łączący JOIN + GROUP BY +
         * HAVING: "CREATE VIEW customer_summary AS SELECT c.name, COUNT(o.id) AS
         * liczba_zamowien, SUM(o.amount) AS suma FROM customers c JOIN orders o ON
         * o.customer_id = c.id GROUP BY c.name HAVING SUM(o.amount) > 100" - wykonaj
         * SELECT * FROM customer_summary ORDER BY suma DESC i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ChainOfThreeDependentViews {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj ŁAŃCUCH 3 widoków zależnych od siebie: "orders_with_amount"
         * (JOIN orders+order_items+products licząc wartość każdego zamówienia),
         * "customer_order_totals" (bazujący na pierwszym, GROUP BY customer, SUM),
         * "vip_customers" (bazujący na drugim, WHERE suma > próg). Wykonaj SELECT *
         * FROM vip_customers na końcu i zweryfikuj, że cały łańcuch działa poprawnie
         * "pod spodem".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ViewBasedDataMigrationHelper {
        /*
         * 🧪 Zadanie 23:
         * Utwórz "orders_legacy" (id, customer_name VARCHAR(100), amount
         * DECIMAL(10,2)) z 5 wierszami. Utwórz widok "orders_legacy_normalized" jako
         * "CREATE VIEW orders_legacy_normalized AS SELECT id, TRIM(customer_name) AS
         * customer_name, amount FROM orders_legacy" (widok "czyszczący" dane w
         * locie, np. TRIM białych znaków). Wstaw wiersz z customer_name mającym
         * spacje na początku/końcu, i zweryfikuj przez SELECT z widoku, że dane są
         * "wyczyszczone" bez modyfikowania oryginalnej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ViewPerformanceVsRawQueryComparison {
        /*
         * 🧪 Zadanie 24:
         * Utwórz "products" (id, category_id INT, price DECIMAL(10,2)) z 2000
         * wierszami i "categories" (id, name) z 5 wierszami. Utwórz widok
         * "products_full" (JOIN obu tabel). Zmierz czas wykonania IDENTYCZNEGO
         * zapytania z WHERE: raz przez widok, raz przez "ręczny" JOIN na oryginalnych
         * tabelach - wypisz porównanie czasów i skomentuj w println, że zwykły
         * widok NIE przyspiesza zapytań (to wciąż to samo zapytanie "pod spodem").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ViewDrivenReportGeneratorForMultipleAudiences {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "sales" (id, region VARCHAR(50), salesperson VARCHAR(100), amount
         * DECIMAL(10,2)). Zbuduj 3 widoki dla 3 różnych "odbiorców": "sales_detail"
         * (wszystkie kolumny, dla managera), "sales_by_region" (GROUP BY region +
         * SUM, dla zarządu), "sales_by_salesperson" (GROUP BY salesperson + SUM +
         * COUNT, dla działu HR). Wykonaj SELECT na wszystkich 3 i wypisz każdy raport
         * pod osobnym nagłówkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ViewWithComplexBusinessLogicExpression {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "orders" (id, amount DECIMAL(10,2), status VARCHAR(20)). Utwórz
         * widok "orders_with_commission" liczący "prowizję" przez wyrażenie CASE:
         * "CREATE VIEW orders_with_commission AS SELECT id, amount, status, CASE
         * WHEN status = 'PAID' THEN amount * 0.05 ELSE 0 END AS commission FROM
         * orders" (biznesowa logika "zaszyta" w widoku). Wstaw dane z różnymi
         * statusami i wypisz wynik z widoku, weryfikując poprawność obliczonej
         * prowizji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReplaceViewDefinitionAndVerifyImpact {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "products" (id, name, price DECIMAL(10,2)) i widok "cheap_products"
         * (WHERE price < 100). Wykonaj SELECT na widoku i zapisz liczbę wyników.
         * Wykonaj DROP VIEW, a potem CREATE VIEW z INNYM progiem (price < 200) pod TĄ
         * SAMĄ nazwą "cheap_products" (symulacja "zmiany definicji" widoku - H2 nie
         * ma prostego ALTER VIEW dla zmiany warunku, trzeba DROP+CREATE). Wykonaj TO
         * SAMO zapytanie i zweryfikuj, że liczba wyników się zmieniła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ViewBasedInvariantValidation {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "orders" (id, amount DECIMAL(10,2)) i "order_items" (id, order_id
         * INT, unit_price DECIMAL(10,2), quantity INT). Utwórz widok
         * "order_amount_mismatch" jako zapytanie z podzapytaniem skorelowanym
         * (Lesson16) porównujące orders.amount z SUM(unit_price*quantity) z
         * order_items, filtrujące TYLKO wiersze gdzie się RÓŻNIĄ (widok jako "stały
         * detektor niespójności"). Wstaw dane z co najmniej jednym niespójnym
         * zamówieniem i zweryfikuj, że widok je znajduje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ViewCatalogIntrospectionUsingMetadata {
        /*
         * 🧪 Zadanie 29:
         * Utwórz 3 różne widoki na różnych tabelach. Napisz metodę
         * listAllViews(Connection conn), która przez conn.getMetaData().getTables(
         * null, null, "%", new String[]{"VIEW"}) (DatabaseMetaData, filtrowanie po
         * typie obiektu "VIEW") wylistuje nazwy WSZYSTKICH widoków w bazie, odróżniając
         * je od zwykłych tabel - wypisz znalezione nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMultiLayeredReportingSystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletny system raportowania sklepu przez widoki: "customers"
         * (id, name), "orders" (id, customer_id INT, order_date DATE), "order_items"
         * (id, order_id INT, product_id INT, quantity INT), "products" (id, name,
         * price DECIMAL(10,2)). Wstaw realistyczne dane (min. 3 klientów, 4
         * zamówienia, 5 produktów). Zbuduj WARSTWOWY system widoków: (1)
         * "order_values" (JOIN order_items+products, licząc wartość każdej pozycji),
         * (2) "order_totals" (bazujący na (1), GROUP BY order_id, SUM wartości
         * zamówienia), (3) "customer_lifetime_value" (bazujący na (2) + JOIN z
         * orders+customers, GROUP BY customer, SUM wszystkich jego zamówień). Wykonaj
         * SELECT na warstwie (3) i wypisz finalny raport "lifetime value" każdego
         * klienta, demonstrując, że złożona logika została rozłożona na proste,
         * czytelne warstwy.
         */
        public static void main(String[] args) { }
    }
}
