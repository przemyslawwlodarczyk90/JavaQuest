package com.example.javaquest._08_sql.Lesson18_Indexes;

public class _Exercises_Lesson18_Indexes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateBasicIndex {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_idx;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150), age INT) i wstaw
         * 500 wierszy w pętli. Wykonaj "CREATE INDEX idx_users_email ON
         * users(email)" i zweryfikuj (bez błędu), że polecenie się wykonało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWithoutIndex {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) i wstaw 500
         * wierszy. BEZ tworzenia żadnego indeksu na email, wykonaj "EXPLAIN SELECT *
         * FROM users WHERE email = 'user250@test.com'" i wypisz plan zapytania -
         * powinien pojawić się "tableScan".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWithIndex {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "users" z Zadania 2 z 500 wierszami. Utwórz "CREATE INDEX
         * idx_users_email ON users(email)". Wykonaj TO SAMO "EXPLAIN SELECT * FROM
         * users WHERE email = 'user250@test.com'" i wypisz - powinna pojawić się
         * nazwa indeksu zamiast tableScan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrimaryKeyHasAutomaticIndex {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) i wstaw 500
         * wierszy. Wykonaj "EXPLAIN SELECT * FROM users WHERE id = 250" (kolumna z
         * PRIMARY KEY - ma indeks automatycznie) i wypisz plan - NIE powinien
         * pojawić się tableScan, mimo że sami nie utworzyliśmy żadnego indeksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UniqueIndexRejectsDuplicate {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) i wstaw 3
         * wiersze z różnymi emailami. Wykonaj "CREATE UNIQUE INDEX
         * idx_users_email_unique ON users(email)". Spróbuj wstawić NOWY wiersz z
         * DUPLIKATEM istniejącego emaila - złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompositeIndexOnTwoColumns {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, age INT, email VARCHAR(150)) i
         * wstaw 500 wierszy. Wykonaj "CREATE INDEX idx_users_age_email ON
         * users(age, email)" (indeks wielokolumnowy) i zweryfikuj bez błędu, że
         * polecenie się wykonało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DropIndex {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) i indeks
         * "idx_users_email". Wykonaj "DROP INDEX idx_users_email" i zweryfikuj, że
         * "EXPLAIN SELECT * FROM users WHERE email = '...'" PO usunięciu indeksu
         * pokazuje ponownie tableScan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IndexAnalogyExplanationPrint {
        /*
         * 🧪 Zadanie 8:
         * Bez łączenia się z bazą: wypisz println analogię z lekcji (indeks w bazie
         * danych jak skorowidz na końcu książki) i wyjaśnij, kiedy warto zakładać
         * indeks (kolumny często używane w WHERE/JOIN/ORDER BY) a kiedy nie
         * (kolumny rzadko odpytywane, tabele z bardzo częstymi zapisami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IndexCostOnInsertPerformance {
        /*
         * 🧪 Zadanie 9:
         * Utwórz DWIE identyczne tabele "with_index" i "without_index" (id INT
         * PRIMARY KEY, email VARCHAR(150)). Dodaj indeks TYLKO na "with_index(email)".
         * Zmierz czas (System.nanoTime()) wstawienia 1000 wierszy do OBU tabel -
         * wypisz porównanie czasów, komentując w println, że indeks spowalnia
         * zapisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainOnJoinColumn {
        /*
         * 🧪 Zadanie 10:
         * Utwórz "orders" (id INT PRIMARY KEY, user_id INT) z 500 wierszami. Wykonaj
         * "EXPLAIN SELECT * FROM orders WHERE user_id = 42" BEZ indeksu na user_id -
         * wypisz plan. Utwórz indeks na user_id i powtórz EXPLAIN - porównaj oba
         * wyniki jeden pod drugim.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MeasureQueryTimeBeforeAfterIndex {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) z 5000
         * wierszami. Zmierz czas (System.nanoTime()) wykonania 50 zapytań "WHERE
         * email = ?" PRZED utworzeniem indeksu, następnie utwórz indeks na email i
         * zmierz czas TYCH SAMYCH 50 zapytań PO indeksie - wypisz porównanie i
         * policz przyspieszenie (stara wartość / nowa wartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompositeIndexColumnOrderMatters {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, age INT, email VARCHAR(150)) z
         * 2000 wierszami. Utwórz indeks "idx_age_email ON users(age, email)". Wykonaj
         * "EXPLAIN SELECT * FROM users WHERE age = 30" (filtrowanie TYLKO po
         * pierwszej kolumnie indeksu - powinno użyć indeksu) i "EXPLAIN SELECT * FROM
         * users WHERE email = 'user1@test.com'" (filtrowanie TYLKO po drugiej kolumnie
         * - prawdopodobnie NIE użyje tego indeksu efektywnie) - wypisz oba plany i
         * skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UniqueIndexVsUniqueConstraintComparison {
        /*
         * 🧪 Zadanie 13:
         * Utwórz DWIE tabele: "users_constraint" (id INT PRIMARY KEY, email
         * VARCHAR(150) UNIQUE) i "users_index" (id INT PRIMARY KEY, email
         * VARCHAR(150)) z CREATE UNIQUE INDEX na email. Wstaw poprawne dane do obu, a
         * potem spróbuj wstawić duplikat emaila do OBU - oba powinny odrzucić
         * operację. Wypisz komunikaty błędów z obu i skomentuj w println, że
         * różnica jest w praktyce kosmetyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IndexOnForeignKeySpeedsUpJoin {
        /*
         * 🧪 Zadanie 14:
         * Utwórz "customers" (id INT PRIMARY KEY) z 500 wierszami i "orders" (id INT
         * PRIMARY KEY, customer_id INT) z 3000 wierszami (customer_id BEZ indeksu).
         * Zmierz czas wykonania JOIN-a (podglądowo z Lesson14) między tymi tabelami z
         * WHERE na konkretnym kliencie. Utwórz indeks na orders(customer_id) i
         * zmierz czas TEGO SAMEGO zapytania - wypisz porównanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TableScanOnNonIndexedRangeQuery {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) z 2000
         * wierszami. Wykonaj "EXPLAIN SELECT * FROM products WHERE price BETWEEN 100
         * AND 200" (zapytanie zakresowe, Lesson11) BEZ indeksu na price - wypisz
         * plan. Utwórz indeks na price i powtórz - wypisz i porównaj obie odpowiedzi
         * EXPLAIN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleSingleColumnIndexesVsOneComposite {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, age INT, city VARCHAR(50)) z
         * 2000 wierszami. Zbuduj DWA warianty indeksowania: (A) DWA oddzielne
         * indeksy jednokolumnowe (na age i na city osobno), (B) JEDEN indeks
         * złożony (age, city). Wykonaj "EXPLAIN SELECT * FROM users WHERE age = 30
         * AND city = 'Warszawa'" w OBU wariantach (usuwając indeksy między testami) i
         * wypisz oba plany, komentując różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IndexImpactOnOrderByPerformance {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100)) z 3000
         * wierszami. Zmierz czas wykonania "SELECT * FROM products ORDER BY name"
         * (Lesson12) BEZ indeksu na name, następnie utwórz indeks na name i zmierz
         * czas TEGO SAMEGO zapytania - wypisz porównanie i skomentuj w println, że
         * indeks pomaga też przy sortowaniu (dane już są w porządku indeksu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DynamicIndexCreationBasedOnUsagePattern {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "logs" (id INT PRIMARY KEY, level VARCHAR(10), message
         * VARCHAR(200)) z 3000 wierszami. Napisz metodę
         * ensureIndexExists(Connection conn, String tableName, String columnName),
         * która przez DatabaseMetaData.getIndexInfo(...) sprawdza, czy indeks na
         * podanej kolumnie już istnieje, i tworzy go TYLKO jeśli nie istnieje.
         * Wywołaj ją DWA razy dla tej samej kolumny "level" - drugie wywołanie NIE
         * powinno próbować utworzyć duplikatu indeksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IndexOnBooleanColumnLowSelectivity {
        /*
         * 🧪 Zadanie 19:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, in_stock BOOLEAN) z 2000
         * wierszami, gdzie 1900 ma in_stock=true, a tylko 100 ma false (niska
         * "selektywność" kolumny boolean - typowy przypadek, gdzie indeks bywa mniej
         * pomocny). Utwórz indeks na in_stock i wykonaj EXPLAIN dla WHERE in_stock =
         * true (dużo wyników) i WHERE in_stock = false (mało wyników) - wypisz oba
         * plany i skomentuj w println, kiedy indeks pomaga bardziej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RebuildingIndexAfterBulkDataChange {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) z 1000
         * wierszami i indeksem na price. Wykonaj masowy UPDATE zmieniający WSZYSTKIE
         * ceny (Lesson09_DML), a następnie zweryfikuj przez EXPLAIN, że indeks
         * WCIĄŻ jest używany po zmianie danych (indeks aktualizuje się automatycznie
         * przy każdej modyfikacji, nie trzeba go "przebudowywać" ręcznie w H2) -
         * wypisz plan zapytania po masowej zmianie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullIndexingStrategyForRealisticSchema {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj schemat sklepu: "customers" (id, email VARCHAR(150)), "orders"
         * (id, customer_id INT, order_date DATE), z 2000 klientami i 10000
         * zamówieniami. Zaproponuj i utwórz PRZEMYŚLANY zestaw indeksów (na
         * customers.email - częste logowanie po emailu, na orders.customer_id -
         * częste JOIN-y, na orders.order_date - częste raporty po zakresie dat).
         * Wykonaj EXPLAIN dla 3 typowych zapytań (po emailu, po JOIN, po zakresie
         * dat) PRZED i PO utworzeniu WSZYSTKICH indeksów - wypisz kompletne
         * porównanie 6 planów zapytań (3 przed, 3 po).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureIndexOverheadOnBulkInserts {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "events" (id INT PRIMARY KEY, event_type VARCHAR(50),
         * created_at TIMESTAMP). Zmierz czas wstawienia 5000 wierszy BEZ ŻADNEGO
         * indeksu (poza PRIMARY KEY). Wyczyść tabelę (TRUNCATE, Lesson08), utwórz 3
         * indeksy (na event_type, na created_at, i złożony na obu), i zmierz czas
         * wstawienia TYCH SAMYCH 5000 wierszy PONOWNIE - wypisz porównanie i policz
         * procentowy narzut czasowy każdego dodatkowego indeksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SelectivityAnalysisBeforeIndexDecision {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, country VARCHAR(50), email
         * VARCHAR(150)) z 5000 wierszami, gdzie country ma tylko 5 unikalnych
         * wartości (niska selektywność), a email jest unikalny dla każdego wiersza
         * (wysoka selektywność). Napisz metodę measureSelectivity(Statement stmt,
         * String table, String column), licząca COUNT(DISTINCT column) / COUNT(*) -
         * wywołaj ją dla obu kolumn, wypisz wyniki i na tej podstawie zaproponuj
         * (w println), na której kolumnie indeks przyniesie WIĘKSZĄ korzyść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompositeIndexCoveringMultipleQueryPatterns {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "orders" (id INT PRIMARY KEY, customer_id INT, status
         * VARCHAR(20), order_date DATE) z 5000 wierszami. Zaprojektuj JEDEN indeks
         * złożony (customer_id, status, order_date), który wspiera WSZYSTKIE 3
         * typowe wzorce zapytań: (A) WHERE customer_id = ?, (B) WHERE customer_id =
         * ? AND status = ?, (C) WHERE customer_id = ? AND status = ? AND order_date
         * > ?. Wykonaj EXPLAIN dla wszystkich 3 wzorców i zweryfikuj, że KAŻDY z nich
         * korzysta z tego samego indeksu (kolejność kolumn w indeksie ma znaczenie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_IndexMaintenanceCostUnderFrequentUpdates {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "inventory" (id INT PRIMARY KEY, sku VARCHAR(50), quantity
         * INT) z 1000 wierszami i indeksem na sku. Zmierz czas wykonania 2000 UPDATE
         * (symulacja częstych zmian stanu magazynowego) na kolumnie quantity, którą
         * indeks NIE dotyczy - powinno być szybko. Następnie zmierz czas 2000 UPDATE
         * zmieniających sku (kolumna Z indeksem) - powinno być WOLNIEJ, bo każda
         * zmiana wymaga aktualizacji indeksu. Wypisz porównanie i skomentuj wniosek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DetectMissingIndexesFromSlowQueryPattern {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "orders" (id INT PRIMARY KEY, customer_id INT, product_id INT) z
         * 5000 wierszami, BEZ żadnych indeksów poza PRIMARY KEY. Napisz metodę
         * analyzeQuery(Statement stmt, String sql), która wykonuje "EXPLAIN " + sql
         * i sprawdza (String.contains) w wyniku, czy zawiera "tableScan" - jeśli tak,
         * wypisuje ostrzeżenie z SUGESTIĄ utworzenia indeksu na kolumnie z warunku
         * WHERE (proste parsowanie sql w Javie, żeby wyciągnąć nazwę kolumny).
         * Przetestuj na 2 zapytaniach: jednym po customer_id, jednym po product_id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BenchmarkIndexTypesComparison {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, sku VARCHAR(50), category
         * VARCHAR(50)) z 3000 wierszami, gdzie sku jest unikalny, a category ma
         * tylko 5 wartości. Utwórz UNIQUE INDEX na sku i zwykły (nie-unikalny) INDEX
         * na category. Zmierz i porównaj czas wyszukiwania po sku (WHERE sku = ?,
         * dokładnie 1 wynik) vs po category (WHERE category = ?, setki wyników) -
         * wypisz porównanie czasów i wyjaśnij w println, czemu oba typy zapytań są
         * przyspieszone, ale w różny sposób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_IndexAwareQueryRewriteForBetterPerformance {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(150)) z 3000
         * wierszami i indeksem na email. Zmierz czas DWÓCH logicznie równoważnych
         * zapytań: (A) "WHERE UPPER(email) = UPPER(?)" (funkcja na kolumnie
         * zaindeksowanej - może "zepsuć" wykorzystanie indeksu), (B) "WHERE email =
         * ?" z parametrem już znormalizowanym w Javie do właściwej wielkości liter
         * (bez funkcji SQL na kolumnie) - wykonaj EXPLAIN dla obu i porównaj plany
         * oraz czas wykonania, komentując w println wniosek o wpływie funkcji na
         * użycie indeksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullIndexLifecycleWithMonitoring {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, category VARCHAR(50)) z 3000
         * wierszami. Napisz KOMPLETNY cykl życia indeksu z monitorowaniem: (1)
         * zmierz czas zapytania PRZED indeksem, (2) utwórz indeks i zmierz czas jego
         * tworzenia, (3) zmierz czas TEGO SAMEGO zapytania PO indeksie, (4) usuń
         * indeks (DROP INDEX) i zmierz czas zapytania PONOWNIE (powinien wrócić do
         * poziomu z punktu 1). Wypisz KOMPLETNY raport z wszystkimi zmierzonymi
         * czasami i wnioskami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneIndexingDecisionFramework {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj realistyczny schemat: "customers" (id, email VARCHAR(150),
         * country VARCHAR(50)), "orders" (id, customer_id INT, status VARCHAR(20),
         * order_date DATE, amount DECIMAL(10,2)) z 3000 klientami i 15000
         * zamówieniami. Napisz KOMPLETNY framework decyzyjny w Javie: metoda
         * recommendIndexes(Statement stmt), która dla LISTY typowych zapytań
         * aplikacji (min. 5 różnych: po emailu, po JOIN z customer_id, po statusie, po
         * zakresie dat, po kombinacji customer_id+status) wykonuje EXPLAIN PRZED
         * jakimikolwiek indeksami, wykrywa tableScan, i dla każdego przypadku
         * PROPONUJE konkretny indeks (nazwa kolumny/kolumn). Następnie utwórz
         * WSZYSTKIE zaproponowane indeksy i wykonaj PONOWNIE wszystkie 5 zapytań z
         * EXPLAIN, wypisując końcowy raport "PRZED/PO" z pomiarem czasu dla każdego
         * z 5 zapytań.
         */
        public static void main(String[] args) { }
    }
}
