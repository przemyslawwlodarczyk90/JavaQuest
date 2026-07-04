package com.example.javaquest._08_sql.Lesson08_DDL;

public class _Exercises_Lesson08_DDL {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateSimpleTable {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 w pamięci (jdbc:h2:mem:ex01_ddl;DB_CLOSE_DELAY=-1).
         * Utwórz tabelę "books" z kolumnami: id INT PRIMARY KEY, title VARCHAR(150),
         * year INT. Wstaw 3 dowolne książki i wypisz zawartość tabeli (SELECT *).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PrimaryKeyDuplicate {
        /*
         * 🧪 Zadanie 2:
         * W bazie jdbc:h2:mem:ex02_ddl utwórz tabelę "students" (id INT PRIMARY KEY,
         * name VARCHAR(100)). Wstaw wiersz o id=1, a następnie spróbuj wstawić
         * drugi wiersz również z id=1 – złap SQLException i wypisz jego komunikat,
         * potwierdzając, że PRIMARY KEY wymusza unikalność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddColumnWithDefault {
        /*
         * 🧪 Zadanie 3:
         * W bazie jdbc:h2:mem:ex03_ddl utwórz tabelę "products" (id INT PRIMARY KEY,
         * name VARCHAR(100)) i wstaw 2 wiersze. Następnie wykonaj
         * ALTER TABLE products ADD COLUMN in_stock BOOLEAN DEFAULT TRUE
         * i wypisz tabelę – sprawdź, jaką wartość dostały istniejące wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DropColumn {
        /*
         * 🧪 Zadanie 4:
         * W bazie jdbc:h2:mem:ex04_ddl utwórz tabelę "cars" (id INT PRIMARY KEY,
         * brand VARCHAR(50), old_note VARCHAR(200)), wstaw 2 wiersze.
         * Wykonaj ALTER TABLE cars DROP COLUMN old_note i wypisz strukturę
         * wyniku SELECT * (kolumna old_note nie powinna się już pojawić).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AlterColumnType {
        /*
         * 🧪 Zadanie 5:
         * W bazie jdbc:h2:mem:ex05_ddl utwórz tabelę "items" (id INT PRIMARY KEY,
         * quantity INT), wstaw kilka wierszy z wartościami quantity. Wykonaj
         * ALTER TABLE items ALTER COLUMN quantity SET DATA TYPE BIGINT
         * i wypisz dane po zmianie typu, aby potwierdzić, że się nie zgubiły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TruncateTable {
        /*
         * 🧪 Zadanie 6:
         * W bazie jdbc:h2:mem:ex06_ddl utwórz tabelę "logs" (id INT PRIMARY KEY,
         * message VARCHAR(200)), wstaw 5 wierszy. Wykonaj TRUNCATE TABLE logs,
         * wypisz liczbę wierszy (powinno być 0), a następnie wstaw nowy wiersz,
         * aby potwierdzić, że struktura tabeli nadal istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DropTableVerify {
        /*
         * 🧪 Zadanie 7:
         * W bazie jdbc:h2:mem:ex07_ddl utwórz tabelę "temp_data" (id INT PRIMARY KEY),
         * wstaw 1 wiersz, a potem wykonaj DROP TABLE temp_data. Spróbuj wykonać
         * SELECT * FROM temp_data w bloku try/catch i wypisz, czy tabela nadal
         * istnieje (na podstawie złapanego SQLException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NotNullConstraint {
        /*
         * 🧪 Zadanie 8:
         * W bazie jdbc:h2:mem:ex08_ddl utwórz tabelę "users" (id INT PRIMARY KEY,
         * email VARCHAR(100) NOT NULL). Wstaw poprawny wiersz, a następnie spróbuj
         * wstawić wiersz z email = NULL – złap SQLException i wypisz komunikat błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UniqueConstraint {
        /*
         * 🧪 Zadanie 9:
         * W bazie jdbc:h2:mem:ex09_ddl utwórz tabelę "accounts" (id INT PRIMARY KEY,
         * login VARCHAR(50) UNIQUE). Wstaw wiersz z login='admin', a potem spróbuj
         * wstawić kolejny wiersz z tym samym loginem – złap SQLException i wypisz
         * jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CheckConstraint {
        /*
         * 🧪 Zadanie 10:
         * W bazie jdbc:h2:mem:ex10_ddl utwórz tabelę "accounts" (id INT PRIMARY KEY,
         * balance DECIMAL(10,2) CHECK (balance >= 0)). Wstaw poprawny wiersz
         * (balance=100.00), a potem spróbuj wstawić wiersz z balance = -50.00 –
         * złap SQLException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddColumnPreservesData {
        /*
         * 🧪 Zadanie 11:
         * W bazie jdbc:h2:mem:ex11_ddl utwórz tabelę "employees" (id INT PRIMARY KEY,
         * name VARCHAR(100)), wstaw 3 wiersze. Dodaj kolumnę
         * ALTER TABLE employees ADD COLUMN bonus DECIMAL(10,2) DEFAULT 0.00,
         * a następnie zaktualizuj bonus jednego pracownika (UPDATE). Wypisz
         * całą tabelę, pokazując, że stare wiersze dostały domyślną wartość,
         * a zaktualizowany wiersz ma nową.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TruncateVsDeleteComparison {
        /*
         * 🧪 Zadanie 12:
         * W bazie jdbc:h2:mem:ex12_ddl utwórz dwie identyczne tabele "table_truncate"
         * i "table_delete" (id INT PRIMARY KEY, value VARCHAR(50)), każdą wypełnij
         * 5 wierszami. Wyczyść pierwszą przez TRUNCATE TABLE, a drugą przez
         * DELETE FROM (bez WHERE). Wypisz liczbę wierszy w obu po operacji
         * (powinno być 0 w obu) oraz krótki komentarz w konsoli o różnicy
         * między tymi poleceniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleConstraintsViolations {
        /*
         * 🧪 Zadanie 13:
         * W bazie jdbc:h2:mem:ex13_ddl utwórz tabelę "employees" (id INT PRIMARY KEY,
         * email VARCHAR(100) NOT NULL UNIQUE, salary DECIMAL(10,2) CHECK (salary >= 0)).
         * Wstaw jeden poprawny wiersz, a następnie po kolei spróbuj wstawić:
         * wiersz z email=NULL, wiersz z duplikatem email, wiersz z salary=-100.
         * Dla każdej próby złap SQLException osobno i wypisz, które ograniczenie
         * zostało złamane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AlterColumnTypeWithExistingData {
        /*
         * 🧪 Zadanie 14:
         * W bazie jdbc:h2:mem:ex14_ddl utwórz tabelę "measurements" (id INT PRIMARY KEY,
         * value INT), wstaw kilka wierszy z małymi wartościami (np. 10, 20, 30).
         * Zmień typ kolumny value na DECIMAL(10,2) (ALTER COLUMN ... SET DATA TYPE),
         * a następnie wstaw nowy wiersz z wartością ułamkową (np. 15.75). Wypisz
         * całą tabelę, pokazując, że stare i nowe dane współistnieją poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SchemaEvolutionSequence {
        /*
         * 🧪 Zadanie 15:
         * W bazie jdbc:h2:mem:ex15_ddl utwórz tabelę "articles" (id INT PRIMARY KEY,
         * title VARCHAR(150)). Wykonaj po kolei: ADD COLUMN draft BOOLEAN DEFAULT TRUE,
         * ADD COLUMN views INT DEFAULT 0, DROP COLUMN draft. Po każdym kroku
         * wypisz aktualną listę kolumn (np. przez ResultSetMetaData po
         * SELECT * FROM articles), aby zademonstrować ewolucję struktury tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RecreateTableDifferentSchema {
        /*
         * 🧪 Zadanie 16:
         * W bazie jdbc:h2:mem:ex16_ddl utwórz tabelę "config" (id INT PRIMARY KEY,
         * value VARCHAR(50)), wstaw 2 wiersze. Wykonaj DROP TABLE config,
         * a następnie utwórz NOWĄ tabelę "config" z inną strukturą
         * (key VARCHAR(50) PRIMARY KEY, value VARCHAR(200), updated_at VARCHAR(20)).
         * Wstaw dane pasujące do nowej struktury i wypisz zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BasicForeignKey {
        /*
         * 🧪 Zadanie 17:
         * W bazie jdbc:h2:mem:ex17_ddl utwórz tabelę "categories" (id INT PRIMARY KEY,
         * name VARCHAR(50)) i tabelę "products" (id INT PRIMARY KEY,
         * category_id INT, name VARCHAR(100),
         * FOREIGN KEY (category_id) REFERENCES categories(id)).
         * Wstaw 1 kategorię, wstaw poprawny produkt wskazujący na tę kategorię,
         * a następnie spróbuj wstawić produkt z category_id, które nie istnieje –
         * złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultiColumnCheck {
        /*
         * 🧪 Zadanie 18:
         * W bazie jdbc:h2:mem:ex18_ddl utwórz tabelę "reservations" (id INT PRIMARY KEY,
         * start_date DATE, end_date DATE, CHECK (start_date <= end_date)).
         * Wstaw poprawną rezerwację (np. '2026-01-10' do '2026-01-15'), a potem
         * spróbuj wstawić rezerwację, gdzie start_date jest PÓŹNIEJSZY niż
         * end_date – złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TruncateThenReinsertWithConstraints {
        /*
         * 🧪 Zadanie 19:
         * W bazie jdbc:h2:mem:ex19_ddl utwórz tabelę "invoices" (id INT PRIMARY KEY,
         * amount DECIMAL(10,2) CHECK (amount > 0)), wstaw 3 poprawne wiersze.
         * Wykonaj TRUNCATE TABLE invoices, a następnie spróbuj wstawić nowy
         * wiersz z amount = -10 – złap SQLException, potwierdzając, że
         * ograniczenie CHECK nadal obowiązuje po TRUNCATE. Na koniec wstaw
         * poprawny wiersz i wypisz zawartość tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullDdlLifecycleReport {
        /*
         * 🧪 Zadanie 20:
         * W bazie jdbc:h2:mem:ex20_ddl przeprowadź pełny cykl życia tabeli "demo"
         * (id INT PRIMARY KEY, name VARCHAR(50)): CREATE TABLE, INSERT 3 wierszy,
         * ALTER TABLE ADD COLUMN active BOOLEAN DEFAULT TRUE, ALTER TABLE ALTER
         * COLUMN name SET DATA TYPE VARCHAR(100), ALTER TABLE DROP COLUMN active,
         * TRUNCATE TABLE, DROP TABLE. Po każdym kroku wypisz krótki log
         * (np. "Krok N: ..." + stan tabeli, jeśli istnieje).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LibrarySchemaWithAllConstraints {
        /*
         * 🧪 Zadanie 21:
         * W bazie jdbc:h2:mem:ex21_ddl zaprojektuj i utwórz tabele "authors"
         * (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL) oraz "books"
         * (id INT PRIMARY KEY, title VARCHAR(150) NOT NULL, author_id INT,
         * isbn VARCHAR(20) UNIQUE, price DECIMAL(10,2) CHECK (price >= 0),
         * FOREIGN KEY (author_id) REFERENCES authors(id)). Wstaw 1 autora
         * i 1 poprawną książkę, a następnie po kolei spróbuj wstawić: książkę
         * z title=NULL, książkę z duplikatem isbn, książkę z price ujemnym,
         * książkę z nieistniejącym author_id. Dla każdej próby wypisz, które
         * ograniczenie zadziałało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SchemaMigrationViaCopy {
        /*
         * 🧪 Zadanie 22:
         * W bazie jdbc:h2:mem:ex22_ddl utwórz tabelę "users_v1" (id INT PRIMARY KEY,
         * full_name VARCHAR(150)), wstaw 3 wiersze z pełnymi imionami i nazwiskami.
         * Utwórz nową tabelę "users_v2" (id INT PRIMARY KEY, first_name VARCHAR(75),
         * last_name VARCHAR(75)). Przenieś dane z users_v1 do users_v2, dzieląc
         * full_name na dwie części w Javie (np. po spacji) i wstawiając je do
         * users_v2. Na koniec wykonaj DROP TABLE users_v1 i wypisz zawartość
         * users_v2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompositeUniqueConstraint {
        /*
         * 🧪 Zadanie 23:
         * W bazie jdbc:h2:mem:ex23_ddl utwórz tabelę "enrollments" (student_id INT,
         * course_id INT, UNIQUE(student_id, course_id)). Wstaw wiersz
         * (student_id=1, course_id=100), a potem: wstaw (1, 200) – powinno się
         * udać (inny course_id); spróbuj wstawić ponownie (1, 100) – powinno
         * zgłosić błąd naruszenia UNIQUE złożonego z dwóch kolumn. Wypisz wynik
         * obu prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BatchDdlExecutionReport {
        /*
         * 🧪 Zadanie 24:
         * W bazie jdbc:h2:mem:ex24_ddl przygotuj listę (np. List<String>) 6 poleceń
         * DDL/DML: poprawny CREATE TABLE, poprawny INSERT, celowo błędny
         * INSERT (np. zła liczba kolumn), poprawny ALTER TABLE ADD COLUMN,
         * celowo błędny ALTER TABLE (np. DROP COLUMN nieistniejącej kolumny),
         * poprawny DROP TABLE. Wykonaj je w pętli, dla każdego łapiąc
         * SQLException osobno, i wypisz raport: które polecenia się powiodły,
         * a które nie (z komunikatem błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SafeDropUsingMetadata {
        /*
         * 🧪 Zadanie 25:
         * W bazie jdbc:h2:mem:ex25_ddl utwórz tabelę "sessions" (id INT PRIMARY KEY).
         * Napisz metodę tableExists(Connection conn, String tableName), która
         * sprawdza istnienie tabeli przez conn.getMetaData().getTables(...)
         * (DatabaseMetaData). Użyj jej, aby bezpiecznie wykonać DROP TABLE
         * tylko jeśli tabela istnieje – zademonstruj działanie zarówno dla
         * tabeli, która istnieje, jak i dla nazwy tabeli, która nigdy nie
         * została utworzona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiColumnCheckAcrossTables {
        /*
         * 🧪 Zadanie 26:
         * W bazie jdbc:h2:mem:ex26_ddl utwórz tabelę "events" (id INT PRIMARY KEY,
         * name VARCHAR(100) NOT NULL, start_time INT CHECK (start_time >= 0 AND
         * start_time <= 23), end_time INT CHECK (end_time >= 0 AND end_time <= 23),
         * CHECK (start_time < end_time)). Wstaw poprawne wydarzenie oraz po kolei
         * kilka niepoprawnych (start_time=25, end_time wcześniejszy niż
         * start_time) – dla każdego złap i wypisz błąd.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AddMandatoryColumnSafely {
        /*
         * 🧪 Zadanie 27:
         * W bazie jdbc:h2:mem:ex27_ddl utwórz tabelę "customers" (id INT PRIMARY KEY,
         * name VARCHAR(100)), wstaw 3 wiersze bez adresu e-mail. Dodaj nową,
         * "obowiązkową" kolumnę e-mail w dwóch krokach: najpierw
         * ADD COLUMN email VARCHAR(100) DEFAULT 'brak@example.com', potem
         * (po zaktualizowaniu realnych adresów przez UPDATE dla części wierszy)
         * spróbuj dodać ograniczenie NOT NULL przez ALTER TABLE ... ALTER COLUMN
         * email SET NOT NULL i wypisz, czy się udało oraz finalną zawartość
         * tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_VersionedSchemaWithReport {
        /*
         * 🧪 Zadanie 28:
         * W bazie jdbc:h2:mem:ex28_ddl zbuduj małą "wersjonowaną" tabelę "settings"
         * (id INT PRIMARY KEY, key VARCHAR(50), value VARCHAR(100)) – wersja 1.
         * Wstaw 3 wiersze. "Migracja" do wersji 2: ADD COLUMN category
         * VARCHAR(50) DEFAULT 'general', zaktualizuj kategorię dla jednego
         * wiersza. "Migracja" do wersji 3: DROP COLUMN value, ADD COLUMN
         * value_json VARCHAR(200). Po każdej wersji wypisz zrzut tabeli
         * z nagłówkiem "=== Wersja N ===".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ForeignKeyCascadeDelete {
        /*
         * 🧪 Zadanie 29:
         * W bazie jdbc:h2:mem:ex29_ddl utwórz tabelę "authors" (id INT PRIMARY KEY,
         * name VARCHAR(100)) oraz "books" (id INT PRIMARY KEY, author_id INT,
         * title VARCHAR(150), FOREIGN KEY (author_id) REFERENCES authors(id)
         * ON DELETE CASCADE). Wstaw 1 autora i 2 jego książki. Usuń autora
         * (DELETE FROM authors WHERE id = ...) i wypisz zawartość tabeli books,
         * potwierdzając, że powiązane książki zostały automatycznie usunięte
         * dzięki ON DELETE CASCADE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullRelationalSchemaWithSummary {
        /*
         * 🧪 Zadanie 30:
         * W bazie jdbc:h2:mem:ex30_ddl zaprojektuj 3 powiązane tabele:
         * "categories" (id INT PRIMARY KEY, name VARCHAR(50) NOT NULL UNIQUE),
         * "products" (id INT PRIMARY KEY, category_id INT NOT NULL,
         * name VARCHAR(100) NOT NULL, price DECIMAL(10,2) CHECK (price >= 0),
         * FOREIGN KEY (category_id) REFERENCES categories(id)),
         * "reviews" (id INT PRIMARY KEY, product_id INT NOT NULL,
         * rating INT CHECK (rating BETWEEN 1 AND 5),
         * FOREIGN KEY (product_id) REFERENCES products(id)).
         * Wstaw poprawne dane (2 kategorie, 3 produkty, kilka recenzji),
         * a następnie wykonaj serię 5 celowo niepoprawnych operacji (łamiących
         * różne ograniczenia: NOT NULL, UNIQUE, CHECK, FOREIGN KEY x2).
         * Zbierz wyniki (sukces/błąd + komunikat) w liście i wypisz na końcu
         * czytelne podsumowanie wszystkich 5 prób.
         */
        public static void main(String[] args) { }
    }
}
