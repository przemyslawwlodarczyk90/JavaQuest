package com.example.javaquest._08_sql.Lesson10_Select;

public class _Exercises_Lesson10_Select {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SelectAllColumns {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_select;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), category
         * VARCHAR(50), price DECIMAL(10,2)) i wstaw 5 produktów. Wykonaj "SELECT *
         * FROM products" i wypisz wszystkie kolumny każdego wiersza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SelectSpecificColumns {
        /*
         * 🧪 Zadanie 2:
         * Odtwórz tabelę "products" z Zadania 1. Wykonaj "SELECT name, price FROM
         * products" (tylko 2 z 4 kolumn) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SimpleAlias {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "products". Wykonaj "SELECT name AS nazwa, price AS cena
         * FROM products" i wypisz wynik, odwołując się do kolumn wynikowych przez
         * aliasy (rs.getString("nazwa"), rs.getBigDecimal("cena")).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AliasOnComputedExpression {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz tabelę "products". Wykonaj "SELECT name, price, price * 1.23 AS
         * cena_z_vat FROM products" i wypisz nazwę, cenę netto i cenę z VAT dla
         * każdego produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DistinctSingleColumn {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz tabelę "products" z 5 produktami w 2 kategoriach. Wykonaj "SELECT
         * DISTINCT category FROM products" i wypisz unikalne kategorie (powinno być
         * mniej wyników niż wierszy w tabeli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BasicWhereCondition {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz tabelę "products" z 5 produktami o różnych cenach. Wykonaj "SELECT
         * name, price FROM products WHERE price > 100" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LimitBasic {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz tabelę "products" z 6 produktami. Wykonaj "SELECT id, name FROM
         * products ORDER BY id LIMIT 3" i wypisz TYLKO pierwsze 3 wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_LimitOffsetPagination {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz tabelę "products" z 6 produktami. Zasymuluj "stronę 2" po 3
         * produkty: wykonaj "SELECT id, name FROM products ORDER BY id LIMIT 3
         * OFFSET 3" i wypisz wynik (powinny być produkty 4-6).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SelectStarVsExplicitColumnsComparison {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz tabelę "products". Wykonaj OBA warianty: "SELECT * FROM products"
         * i "SELECT id, name FROM products" - dla każdego wypisz LICZBĘ kolumn w
         * wyniku (rs.getMetaData().getColumnCount()) i skomentuj w println, czemu
         * jawne wymienienie kolumn jest bezpieczniejsze w kodzie produkcyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MultipleAliasesInOneQuery {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz tabelę "products". Wykonaj zapytanie z 3 aliasami naraz: "SELECT
         * name AS nazwa_produktu, category AS kategoria, price AS cena_pln FROM
         * products" i wypisz każdy wiersz, odwołując się do wszystkich 3 aliasów.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DistinctOnMultipleColumns {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "sales" (id INT PRIMARY KEY, region VARCHAR(50), product
         * VARCHAR(50)) i wstaw 6 wierszy, gdzie niektóre kombinacje (region, product)
         * się powtarzają. Wykonaj "SELECT DISTINCT region, product FROM sales" i
         * wypisz - zweryfikuj, że DISTINCT działa na CAŁYM zestawie wybranych kolumn
         * (usuwa tylko wiersze identyczne pod względem OBU kolumn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WhereWithComputedComparison {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "products" (id, price DECIMAL(10,2), discount DECIMAL(10,2))
         * i wstaw 5 produktów. Wykonaj "SELECT name, price, discount FROM products
         * WHERE price - discount < 50" (warunek na WYRAŻENIU obliczanym, nie na
         * gotowej kolumnie) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PaginationLoopThroughAllPages {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "products" z 10 produktami. Napisz metodę printPage(Statement
         * stmt, int pageSize, int pageNumber), która wykonuje SELECT z odpowiednim
         * LIMIT/OFFSET dla danej strony (numerowanej od 1). Wywołaj ją dla WSZYSTKICH
         * stron po 4 produkty (strony 1, 2, 3 - ostatnia niepełna) i wypisz każdą
         * stronę z nagłówkiem "=== Strona N ===".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AliasReusedInJavaLogic {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "employees" (id, name, salary INT). Wstaw 4 pracowników.
         * Wykonaj "SELECT name, salary, salary * 12 AS annual_salary FROM employees"
         * i w Javie (po odczycie) znajdź i wypisz pracownika z NAJWYŻSZYM
         * annual_salary (bez ORDER BY/MAX - poznamy w Lesson12/Lesson13, porównuj
         * ręcznie w pętli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WhereWithStringEquality {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "products" (id, name, category VARCHAR(50)) i wstaw 6
         * produktów w 3 kategoriach. Wykonaj "SELECT name FROM products WHERE
         * category = 'Peryferia'" i wypisz wynik - zwróć uwagę na dokładne
         * dopasowanie (case-sensitive) wartości tekstowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombiningLimitWithWhere {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "products" (id, price DECIMAL(10,2)) i wstaw 8 produktów o
         * różnych cenach. Wykonaj "SELECT id, price FROM products WHERE price > 50
         * ORDER BY id LIMIT 2" - połączenie WHERE, ORDER BY (podglądowo, dokładnie w
         * Lesson12) i LIMIT w jednym zapytaniu, i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DistinctCountViaJavaSet {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "orders" (id, customer_name VARCHAR(100)) i wstaw 8
         * zamówień od kilku (powtarzających się) klientów. Wykonaj "SELECT DISTINCT
         * customer_name FROM orders" i policz LICZBĘ unikalnych klientów (rozmiar
         * wyniku), porównując ją z LICZBĄ WSZYSTKICH wierszy (SELECT COUNT(*) FROM
         * orders) - wypisz obie liczby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AliasWithSpecialCharactersHandling {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "products" (id, name, price DECIMAL(10,2)). Wykonaj "SELECT
         * name AS "nazwa produktu", price AS "cena (PLN)" FROM products" - aliasy w
         * cudzysłowie z odstępami/znakami specjalnymi (H2 to wspiera). Wypisz wynik,
         * odwołując się do tych aliasów w Javie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GenericColumnPrinterUsingMetadata {
        /*
         * 🧪 Zadanie 19:
         * Utwórz DWIE różne tabele (np. "products" i "customers" o różnej liczbie
         * kolumn). Napisz generyczną metodę printAnyQuery(Statement stmt, String
         * sql), która wykonuje PODANE zapytanie i przez ResultSetMetaData wypisuje
         * każdy wiersz w formacie "kolumna1=wartosc1, kolumna2=wartosc2, ...".
         * Przetestuj ją na SELECT z aliasami z obu tabel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TopNQueryWithoutOrderByWarning {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "products" z 5 produktami. Wykonaj "SELECT * FROM products
         * LIMIT 2" (BEZ ORDER BY) DWA razy pod rząd i porównaj wyniki w Javie - w
         * H2 z małą tabelą kolejność często będzie ta sama, ale wypisz println
         * wyjaśniający, czemu w OGÓLNYM przypadku (duże tabele, inne silniki SQL) NIE
         * wolno polegać na kolejności bez jawnego ORDER BY.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullPaginationSystemWithTotalCount {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "products" z 23 produktami. Napisz kompletny system
         * paginacji: metoda getPage(Statement stmt, int pageSize, int pageNumber),
         * która zwraca listę nazw produktów danej strony (LIMIT/OFFSET), oraz metoda
         * getTotalPages(Statement stmt, int pageSize), która liczy
         * Math.ceil((double) totalRows / pageSize) na podstawie SELECT COUNT(*).
         * Wypisz WSZYSTKIE strony (po 5 produktów) z numerem strony i informacją
         * "Strona X z Y".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DynamicColumnSelectionBasedOnUserInput {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "products" (id, name, category, price). Napisz metodę
         * selectColumns(Statement stmt, List<String> columnNames), która dynamicznie
         * buduje zapytanie "SELECT <kolumny rozdzielone przecinkiem> FROM products"
         * (uwaga: nazwy kolumn muszą pochodzić z ZAUFANEJ, ograniczonej listy - NIE z
         * bezpośredniego wejścia użytkownika, żeby uniknąć SQL injection - dodaj
         * walidację, że każda nazwa jest w zbiorze dozwolonych kolumn tabeli).
         * Przetestuj z listami ["name"], ["name", "price"], i z NIEDOZWOLONĄ nazwą
         * kolumny (powinna zostać odrzucona przez walidację, nie trafić do SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DistinctWithComputedExpressionAlias {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "products" (id, price DECIMAL(10,2)) i wstaw 8 produktów, z
         * których kilka ma ceny dające ten sam wynik po zaokrągleniu do dziesiątek
         * (np. 101 i 105 -> "10X"). Wykonaj zapytanie z DISTINCT na wyrażeniu
         * obliczanym z aliasem (np. "SELECT DISTINCT CAST(price / 10 AS INT) AS
         * price_bucket FROM products") i wypisz unikalne "koszyki cenowe".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EfficientLargeOffsetPaginationDiscussion {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "products" z 2000 wierszami. Zmierz czas (System.nanoTime())
         * wykonania zapytania z MAŁYM OFFSET (LIMIT 10 OFFSET 10) i z DUŻYM OFFSET
         * (LIMIT 10 OFFSET 1900) - wypisz porównanie czasów. Skomentuj w println,
         * czemu duży OFFSET bywa kosztowny (baza musi "przejść" przez pominięte
         * wiersze) i że w praktyce dla dużych zbiorów danych stosuje się alternatywy
         * (np. paginację "keyset" po ostatnim id - poza zakresem tej lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReportBuilderWithMultipleAliasedQueries {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "sales" (id, product_name VARCHAR(100), quantity INT,
         * unit_price DECIMAL(10,2)) i wstaw 6 wierszy. Napisz metodę
         * generateSalesReport(Statement stmt), która wykonuje zapytanie z 3 aliasami
         * (product_name, quantity, quantity * unit_price AS revenue) i formatuje
         * wynik jako tabelę tekstową z nagłówkami kolumn (println z dopasowanym
         * odstępem, np. String.format("%-20s %10s %15s", ...)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DeduplicationCheckBeforeInsert {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "subscribers" (id INT PRIMARY KEY, email VARCHAR(100)) i
         * wstaw 5 subskrybentów, gdzie 2 maile są duplikatami (bez ograniczenia
         * UNIQUE - celowo). Wykonaj "SELECT DISTINCT email FROM subscribers" i
         * porównaj rozmiar wyniku z COUNT(*) - jeśli różne, wypisz ostrzeżenie
         * "Wykryto duplikaty" i wylistuj (przez GROUP BY-podobną logikę w Javie,
         * Map<String, Integer>) które adresy się powtarzają i ile razy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SelectWithMultipleComputedAliasesForReport {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "employees" (id, name, salary INT). Wstaw 5 pracowników.
         * Wykonaj zapytanie z 4 aliasami naraz: salary AS gross, salary * 0.8 AS
         * net_estimate, salary * 12 AS annual_gross, salary * 12 * 0.8 AS
         * annual_net_estimate. Wypisz sformatowany raport płacowy dla każdego
         * pracownika z wszystkimi 4 wartościami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_LimitBasedSamplingForLargeTable {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "logs" z 5000 wierszami (id, message VARCHAR(50)). Napisz
         * metodę sampleRandomRows(Statement stmt, int sampleSize), która pobiera
         * "próbkę" danych bez czytania całej tabeli do Javy - wykonaj kilka zapytań z
         * LIMIT 1 OFFSET <losowy_numer> (losowy numer z zakresu wielkości tabeli) i
         * zbierz sampleSize losowych wierszy. Wypisz pobraną próbkę (np. 5 wierszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ColumnProjectionPerformanceComparison {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "products" (id, name VARCHAR(100), description VARCHAR(2000),
         * price DECIMAL(10,2)) i wstaw 1000 wierszy, gdzie description ma zawsze 2000
         * znaków (duża kolumna). Zmierz czas wykonania i odczytania wyniku dla
         * "SELECT * FROM products" (wszystkie kolumny) vs "SELECT id, price FROM
         * products" (tylko potrzebne kolumny) - wypisz porównanie czasów i
         * skomentuj w println, czemu jawne wymienienie kolumn bywa też wydajniejsze,
         * nie tylko bezpieczniejsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneQueryBuilderWithAliasesDistinctAndPagination {
        /*
         * 🧪 Zadanie 30:
         * Utwórz tabelę "products" (id, name VARCHAR(100), category VARCHAR(50),
         * price DECIMAL(10,2)) i wstaw 15 produktów w 4 kategoriach z różnymi
         * cenami. Napisz KOMPLETNY, konfigurowalny "query builder" w Javie: metoda
         * runReport(Statement stmt, boolean distinctCategories, int pageSize, int
         * pageNumber), która: (1) jeśli distinctCategories=true, zwraca unikalne
         * kategorie (DISTINCT) z paginacją, (2) jeśli false, zwraca WSZYSTKIE produkty
         * z aliasami (name AS nazwa, price AS cena, price*1.23 AS cena_z_vat) z tą
         * samą paginacją. Zademonstruj obie ścieżki działania metody z różnymi
         * parametrami i wypisz wyniki obu wywołań.
         */
        public static void main(String[] args) { }
    }
}
