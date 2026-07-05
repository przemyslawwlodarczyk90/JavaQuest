package com.example.javaquest._08_sql.Lesson02_RelationalModel;

public class _Exercises_Lesson02_RelationalModel {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateTableWithPrimaryKey {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 w pamięci (jdbc:h2:mem:ex01_rel;DB_CLOSE_DELAY=-1).
         * Utwórz tabelę "books" (id INT PRIMARY KEY, title VARCHAR(150), author VARCHAR(100)).
         * Wstaw 3 książki i wykonaj SELECT * FROM books, wypisując każdy wiersz w formacie
         * "<id>: <title> - <author>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PrimaryKeyUniquenessViolation {
        /*
         * 🧪 Zadanie 2:
         * W bazie jdbc:h2:mem:ex02_rel utwórz tabelę "students" (id INT PRIMARY KEY,
         * name VARCHAR(100)). Wstaw studenta o id=1, a potem spróbuj wstawić DRUGIEGO
         * studenta również z id=1 - złap SQLException i wypisz jego komunikat,
         * potwierdzając, że PRIMARY KEY wymusza unikalność wiersza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ForeignKeyBasicLink {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "authors" (id INT PRIMARY KEY, name VARCHAR(100)) oraz "books"
         * (id INT PRIMARY KEY, author_id INT, title VARCHAR(150), FOREIGN KEY (author_id)
         * REFERENCES authors(id)). Wstaw 1 autora i 2 jego książki. Wykonaj SELECT title
         * FROM books WHERE author_id = ? i wypisz tytuły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ForeignKeyRejectsUnknownReference {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz schemat authors/books z Zadania 3. Spróbuj wstawić książkę z
         * author_id = 999 (nieistniejący autor) - złap SQLException i wypisz komunikat,
         * potwierdzając, że baza wymusza integralność referencyjną klucza obcego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TableColumnRowVocabularyPrint {
        /*
         * 🧪 Zadanie 5:
         * Bez łączenia się z bazą: wypisz na konsoli (println) definicje 3 pojęć z lekcji
         * ("tabela", "kolumna", "wiersz/rekord") razem z jednozdaniowym wyjaśnieniem
         * każdego, w formacie "<pojecie>: <wyjasnienie>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrimaryKeyNeverNull {
        /*
         * 🧪 Zadanie 6:
         * W bazie jdbc:h2:mem:ex06_rel utwórz tabelę "items" (id INT PRIMARY KEY,
         * name VARCHAR(100)). Spróbuj wstawić wiersz z id = NULL (np. "INSERT INTO items
         * (id, name) VALUES (NULL, 'cos')") - złap SQLException i wypisz komunikat,
         * potwierdzając, że klucz główny nigdy nie może być NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ShopMiniSchemaFourTables {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz mini-schemat sklepu z lekcji: "users" (id, name, email), "products"
         * (id, name, price), "orders" (id, user_id FK -> users, order_date), "order_items"
         * (id, order_id FK -> orders, product_id FK -> products, quantity). Wstaw 1
         * użytkownika, 2 produkty, 1 zamówienie i 2 pozycje zamówienia. Wypisz zawartość
         * wszystkich 4 tabel (4 osobne zapytania SELECT *).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConstraintsVsNoConstraintsComparison {
        /*
         * 🧪 Zadanie 8:
         * Utwórz dwie tabele: "safe_orders" (id INT PRIMARY KEY, user_id INT NOT NULL,
         * FOREIGN KEY (user_id) REFERENCES users_ref(id)) oraz osobno tabelę "users_ref"
         * (id INT PRIMARY KEY). Wstaw 1 użytkownika i poprawne zamówienie. Wypisz
         * println wyjaśniający, że constraints (PRIMARY KEY, FOREIGN KEY) to reguły, które
         * baza WYMUSZA niezależnie od kodu aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IndexOnPrimaryKeyIsAutomatic {
        /*
         * 🧪 Zadanie 9:
         * W bazie jdbc:h2:mem:ex09_rel utwórz tabelę "products" (id INT PRIMARY KEY,
         * name VARCHAR(100)) i wstaw 500 wierszy w pętli. Wykonaj
         * "EXPLAIN SELECT * FROM products WHERE id = 250" i wypisz wynik - klucz główny
         * ma indeks tworzony automatycznie, więc plan zapytania NIE powinien pokazywać
         * pełnego przeszukania tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RelationTableSynonymExplanation {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz println wyjaśniający dwa znaczenia słowa
         * "relacja" z lekcji - (1) synonim "tabeli" w teorii baz danych (stąd "baza
         * RELACYJNA"), (2) powiązanie między dwiema tabelami przez klucz obcy - i podaj
         * jeden przykład każdego znaczenia.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompositeForeignKeyChain {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz cały mini-schemat sklepu z lekcji (users, products, orders,
         * order_items, z pełnymi FOREIGN KEY). Wstaw 2 użytkowników, 3 produkty,
         * 2 zamówienia (jedno na każdego użytkownika) i po 2 pozycje w każdym zamówieniu.
         * Napisz metodę printOrderSummary(Statement stmt, int orderId), która wypisuje
         * id zamówienia i listę produktów w nim (osobne zapytania, bez JOIN - poznamy
         * w Lesson14). Wywołaj ją dla obu zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReferentialIntegrityOnDeleteAttempt {
        /*
         * 🧪 Zadanie 12:
         * Odtwórz authors/books z Zadania 3. Wstaw autora i jego książkę. Spróbuj usunąć
         * autora (DELETE FROM authors WHERE id = ...) - baza powinna odrzucić operację,
         * bo istnieje powiązana książka wskazująca na niego przez FOREIGN KEY. Złap
         * SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GenericTablePrinterUsingMetadata {
        /*
         * 🧪 Zadanie 13:
         * Napisz generyczną metodę printTable(Statement stmt, String tableName), która
         * wykonuje "SELECT * FROM " + tableName i przez ResultSetMetaData wypisuje każdy
         * wiersz w formacie "kolumna1=wartosc1, kolumna2=wartosc2, ...". Przetestuj ją na
         * mini-schemacie sklepu (co najmniej 2 różne tabele o różnej liczbie kolumn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleForeignKeysInOneTable {
        /*
         * 🧪 Zadanie 14:
         * Odtwórz tabelę "order_items" z lekcji, mającą DWA klucze obce naraz (do orders
         * i do products). Wstaw dane tak, by jedna pozycja zamówienia była poprawna, a
         * następnie spróbuj wstawić pozycję z poprawnym order_id, ale nieistniejącym
         * product_id - złap SQLException i wypisz, KTÓRY klucz obcy został naruszony
         * (na podstawie treści komunikatu błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PrimaryKeyAsLookupPerformanceDemo {
        /*
         * 🧪 Zadanie 15:
         * W bazie jdbc:h2:mem:ex15_rel utwórz tabelę "users" (id INT PRIMARY KEY,
         * email VARCHAR(150)) i wstaw 2000 wierszy w pętli. Zmierz czas (System.nanoTime())
         * wykonania 100 zapytań "SELECT * FROM users WHERE id = ?" z losowymi id z
         * zakresu 1-2000 (klucz główny ma indeks). Wypisz łączny czas w milisekundach -
         * skomentuj w println, że to pokazuje praktyczną korzyść z automatycznego
         * indeksu na PRIMARY KEY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DataIntegrityAcrossInsertUpdateDelete {
        /*
         * 🧪 Zadanie 16:
         * Odtwórz authors/books z Zadania 3. Wykonaj serię operacji: (1) INSERT poprawnego
         * autora i książki - powinno się udać, (2) UPDATE books SET author_id = 999
         * WHERE id = ... (nieistniejący autor) - powinno się nie udać, (3) DELETE FROM
         * authors WHERE id = ... (autor z książką) - powinno się nie udać. Dla każdej
         * operacji wypisz "OK" albo "ODRZUCONO: <komunikat>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LibrarySchemaWithFourEntities {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj i utwórz schemat biblioteki: "authors" (id, name), "books" (id,
         * author_id FK, title, isbn), "members" (id, name), "loans" (id, book_id FK,
         * member_id FK, loan_date). Wstaw 2 autorów, 3 książki, 2 członków i 2 wypożyczenia.
         * Wypisz dla każdego wypożyczenia: tytuł książki (osobne zapytanie po book_id)
         * i imię członka (osobne zapytanie po member_id) - bez JOIN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SelfCheckingSchemaValidator {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę countOrphans(Statement stmt), która na schemacie orders/users
         * (orders.user_id FK -> users.id) liczy, ile zamówień wskazuje na NIEISTNIEJĄCEGO
         * użytkownika - ALE zrób to bez FOREIGN KEY na tabeli orders (celowo "słaby"
         * schemat), więc baza NIE pilnuje tego sama. Wstaw 2 poprawne zamówienia i 1
         * "osierocone" (user_id wskazujące na nikogo) i zweryfikuj wynik metody (powinien
         * być 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IndexUsageBeforeAndAfterExplain {
        /*
         * 🧪 Zadanie 19:
         * W bazie jdbc:h2:mem:ex19_rel utwórz tabelę "orders" (id INT PRIMARY KEY,
         * user_id INT) i wstaw 1000 wierszy. Wykonaj "EXPLAIN SELECT * FROM orders WHERE
         * user_id = 42" (kolumna user_id BEZ indeksu - powinien pojawić się tableScan).
         * Następnie utwórz indeks CREATE INDEX idx_orders_user ON orders(user_id) i
         * powtórz to samo EXPLAIN - porównaj i wypisz oba wyniki jeden pod drugim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullShopSchemaConsistencyReport {
        /*
         * 🧪 Zadanie 20:
         * Odtwórz cały mini-schemat sklepu z lekcji (users, products, orders,
         * order_items). Wstaw po 3 wiersze do users i products, 4 zamówienia i kilka
         * pozycji zamówień. Napisz raport: dla każdej z 4 tabel wypisz jej nazwę i
         * liczbę wierszy (COUNT(*)), a na końcu wypisz, ile RAZY klucz obcy jest użyty
         * w całym schemacie (policz to w Javie na podstawie znanej struktury, nie SQL-em).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CascadingReferentialChain {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj 3-poziomowy łańcuch kluczy obcych: "categories" (id, name),
         * "products" (id, category_id FK, name), "reviews" (id, product_id FK, rating).
         * Wstaw 1 kategorię, 2 produkty, po 2 recenzje na produkt. Spróbuj usunąć
         * kategorię, która ma produkty - powinno się nie udać (FK). Spróbuj usunąć
         * produkt, który ma recenzje - powinno się nie udać (FK). Wypisz oba komunikaty
         * błędów, potwierdzając integralność na całej długości łańcucha.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BatchIntegrityTestSuite {
        /*
         * 🧪 Zadanie 22:
         * Odtwórz mini-schemat sklepu z lekcji. Przygotuj listę (List<String>) 5
         * poleceń SQL: 2 poprawne INSERT-y i 3 celowo błędne (naruszające PRIMARY KEY,
         * FOREIGN KEY do users i FOREIGN KEY do products). Wykonaj je w pętli, dla
         * każdego łapiąc SQLException osobno, i wypisz zbiorczy raport: które polecenia
         * się powiodły, a które zostały odrzucone i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ManualForeignKeySimulationWithoutConstraint {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabele "departments" (id, name) i "employees" (id, department_id INT,
         * name) - BEZ ograniczenia FOREIGN KEY (celowo, żeby zasymulować "słabą"
         * integralność). Napisz w Javie metodę validateEmployee(Statement stmt, int
         * departmentId), która przed INSERT-em RĘCZNIE sprawdza (SELECT COUNT(*)), czy
         * department_id istnieje, i rzuca własny wyjątek (IllegalArgumentException),
         * jeśli nie. Zademonstruj działanie na poprawnym i niepoprawnym department_id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceComparisonIndexedVsNot {
        /*
         * 🧪 Zadanie 24:
         * W bazie jdbc:h2:mem:ex24_rel utwórz DWIE identyczne tabele "with_index" i
         * "without_index" (id INT PRIMARY KEY, email VARCHAR(150)), każdą wypełnij 3000
         * wierszami. Dodaj indeks TYLKO na "with_index(email)". Zmierz czas (System.
         * nanoTime()) wykonania 50 zapytań "WHERE email = ?" na obu tabelach i wypisz
         * porównanie czasów - skomentuj różnicę w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SchemaDocumentationGenerator {
        /*
         * 🧪 Zadanie 25:
         * Odtwórz mini-schemat sklepu z lekcji. Napisz metodę printSchemaDoc(Connection
         * conn), która przez conn.getMetaData().getTables(...) i getColumns(...)
         * (DatabaseMetaData) automatycznie wypisuje dla każdej tabeli w schemacie jej
         * nazwę i listę kolumn z typami danych - bez ręcznego wypisywania na sztywno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OrphanDetectionAndCleanup {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "users" (id, name) i "sessions" (id, user_id INT, token VARCHAR(50)) -
         * BEZ ograniczenia FOREIGN KEY. Wstaw 2 użytkowników, 3 sesje poprawne i 2 sesje
         * "osierocone" (user_id wskazujący na nieistniejącego użytkownika, np. usunąłeś
         * usera po fakcie). Napisz zapytanie znajdujące osierocone sesje (WHERE user_id
         * NOT IN (SELECT id FROM users)) i usuń je (DELETE). Zweryfikuj przez COUNT(*).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompositeKeyIntegrityAcrossJunction {
        /*
         * 🧪 Zadanie 27:
         * Utwórz "students" (id, name), "courses" (id, name) i tabelę pośrednią
         * "enrollments" (student_id FK, course_id FK, PRIMARY KEY(student_id, course_id)).
         * Wstaw 2 studentów, 2 kursy i 3 zapisy. Spróbuj wstawić duplikat DOKŁADNIE TEJ
         * SAMEJ pary (student_id, course_id) - złap SQLException. Spróbuj wstawić zapis
         * z nieistniejącym course_id - złap SQLException. Wypisz oba komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullShopSchemaWithIntegrityAudit {
        /*
         * 🧪 Zadanie 28:
         * Odtwórz cały mini-schemat sklepu z lekcji (users, products, orders,
         * order_items) z pełnymi FOREIGN KEY. Wstaw realistyczne dane (3 użytkowników,
         * 4 produkty, 5 zamówień, kilka pozycji). Wykonaj serię 6 celowo niepoprawnych
         * operacji (łamiących różne ograniczenia: PRIMARY KEY x2, FOREIGN KEY x4 w
         * różnych tabelach). Zbierz wyniki (sukces/błąd + komunikat) w liście i wypisz
         * na końcu czytelne podsumowanie audytu integralności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DynamicSchemaBuilderFromDescription {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę createTableWithForeignKey(Statement stmt, String tableName,
         * String referencedTable), która dynamicznie generuje i wykonuje polecenie
         * CREATE TABLE z kolumnami (id INT PRIMARY KEY, ref_id INT, FOREIGN KEY (ref_id)
         * REFERENCES <referencedTable>(id)). Użyj jej, aby zbudować 3 różne tabele
         * odwołujące się do tej samej tabeli nadrzędnej "categories", i zweryfikuj, że
         * każda z nich poprawnie odrzuca nieistniejące ref_id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RelationalIntegrityCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletny schemat blogowy: "users" (id, name), "posts" (id,
         * user_id FK, title), "comments" (id, post_id FK, user_id FK, content). Wstaw
         * dane obejmujące wszystkie relacje (min. 2 users, 2 posts, 3 comments). Napisz
         * metodę generateIntegrityReport(Connection conn), która: (1) liczy wiersze w
         * każdej tabeli, (2) sprawdza (osobnymi zapytaniami, bez JOIN) czy istnieją
         * jakiekolwiek osierocone komentarze (odwołujące się do nieistniejącego posta
         * lub użytkownika - w tym schemacie nie powinno ich być, bo FK tego zabrania),
         * (3) wypisuje sformatowany raport końcowy z nagłówkiem "=== RAPORT INTEGRALNOSCI ===".
         */
        public static void main(String[] args) { }
    }
}
