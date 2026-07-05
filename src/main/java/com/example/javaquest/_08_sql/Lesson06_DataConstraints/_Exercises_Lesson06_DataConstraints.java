package com.example.javaquest._08_sql.Lesson06_DataConstraints;

public class _Exercises_Lesson06_DataConstraints {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrimaryKeyConstraint {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_constraints;DB_CLOSE_DELAY=-1).
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100)). Wstaw
         * wiersz z id=1, a potem spróbuj wstawić drugi z id=1 - złap SQLException i
         * wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UniqueConstraintAllowsNull {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, login VARCHAR(50) UNIQUE).
         * Wstaw 2 wiersze z login = NULL (UNIQUE dopuszcza wiele NULL-i, w
         * przeciwieństwie do PRIMARY KEY) - powinno się udać. Następnie wstaw 2 wiersze
         * z tym samym niepustym loginem - drugi powinien zostać odrzucony. Wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NotNullConstraint {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, email VARCHAR(100) NOT NULL).
         * Wstaw poprawny wiersz, a potem spróbuj wstawić wiersz z email = NULL - złap
         * SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CheckConstraintSimpleRange {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, age INT CHECK (age >= 0 AND age
         * <= 150)). Wstaw poprawny wiersz (age=30), a potem spróbuj wstawić age=200 -
         * złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefaultValueWhenNotProvided {
        /*
         * 🧪 Zadanie 5:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, status VARCHAR(20) DEFAULT
         * 'ACTIVE' NOT NULL). Wstaw wiersz BEZ podania status i wypisz go, sprawdzając
         * że dostał wartość 'ACTIVE'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ForeignKeyBasicReject {
        /*
         * 🧪 Zadanie 6:
         * Utwórz "users" (id INT PRIMARY KEY) i "orders" (id INT PRIMARY KEY, user_id
         * INT NOT NULL, FOREIGN KEY (user_id) REFERENCES users(id)). Wstaw 1
         * użytkownika. Spróbuj wstawić zamówienie z user_id=999 (nieistniejący) - złap
         * SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MultipleConstraintsOnOneColumn {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "employees" (id INT PRIMARY KEY, email VARCHAR(100) NOT NULL
         * UNIQUE). Wstaw poprawny wiersz. Spróbuj wstawić drugi wiersz z email = NULL
         * (naruszy NOT NULL) i trzeci z tym samym emailem (naruszy UNIQUE) - dla
         * każdej próby złap SQLException osobno i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_OnDeleteCascadeBasic {
        /*
         * 🧪 Zadanie 8:
         * Utwórz "authors" (id INT PRIMARY KEY) i "books" (id INT PRIMARY KEY,
         * author_id INT, FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE
         * CASCADE). Wstaw 1 autora i 2 jego książki. Usuń autora i zweryfikuj (COUNT(*)),
         * że obie książki zostały automatycznie usunięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_OnDeleteSetNullBasic {
        /*
         * 🧪 Zadanie 9:
         * Utwórz "users" (id INT PRIMARY KEY) i "reviews" (id INT PRIMARY KEY, user_id
         * INT, content VARCHAR(200), FOREIGN KEY (user_id) REFERENCES users(id) ON
         * DELETE SET NULL). Wstaw 1 użytkownika i jego recenzję. Usuń użytkownika i
         * zweryfikuj (getObject + IS NULL), że recenzja przetrwała, ale user_id jest
         * teraz NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConstraintsOverviewPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez łączenia się z bazą: wypisz na konsoli listę 6 ograniczeń z lekcji
         * (PRIMARY KEY, UNIQUE, NOT NULL, CHECK, DEFAULT, FOREIGN KEY) razem z
         * jednozdaniowym opisem każdego, w formacie "<ograniczenie>: <opis>".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CheckConstraintWithMultipleColumns {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "reservations" (id INT PRIMARY KEY, start_date DATE, end_date
         * DATE, CHECK (start_date <= end_date)). Wstaw poprawną rezerwację, a potem
         * spróbuj wstawić rezerwację, gdzie start_date jest PÓŹNIEJSZY niż end_date -
         * złap SQLException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompositeUniqueConstraint {
        /*
         * 🧪 Zadanie 12:
         * Utwórz tabelę "enrollments" (student_id INT, course_id INT,
         * UNIQUE(student_id, course_id)). Wstaw (1, 100), potem wstaw (1, 200) -
         * powinno się udać (inny course_id). Spróbuj ponownie wstawić (1, 100) -
         * powinno zgłosić błąd naruszenia UNIQUE złożonego z dwóch kolumn. Wypisz
         * wyniki wszystkich 3 prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DefaultWithExpressionLikeTimestamp {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "logs" (id INT PRIMARY KEY, message VARCHAR(200), created_at
         * TIMESTAMP DEFAULT CURRENT_TIMESTAMP). Wstaw 2 wiersze bez podania created_at
         * i wypisz je, sprawdzając, że kolumna została automatycznie wypełniona
         * aktualnym znacznikiem czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CascadeDeleteAcrossTwoLevels {
        /*
         * 🧪 Zadanie 14:
         * Utwórz "authors" (id PK) -> "books" (id PK, author_id FK ON DELETE CASCADE)
         * -> "reviews" (id PK, book_id FK REFERENCES books(id) ON DELETE CASCADE).
         * Wstaw 1 autora, 1 książkę, 2 recenzje. Usuń autora i zweryfikuj (COUNT(*)),
         * że kaskada usunęła i książkę, I recenzje (2 poziomy kaskady naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SetNullRequiresNullableColumn {
        /*
         * 🧪 Zadanie 15:
         * Spróbuj utworzyć tabelę "orders" (id INT PRIMARY KEY, user_id INT NOT NULL,
         * FOREIGN KEY (user_id) REFERENCES users_ref(id) ON DELETE SET NULL) - to
         * jest KONFLIKT (NOT NULL + ON DELETE SET NULL nie mogą współistnieć na tej
         * samej kolumnie) - złap SQLException przy CREATE TABLE (albo przy próbie
         * wywołania kaskady) i wypisz komunikat wyjaśniający konflikt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestSuiteForAllConstraintTypes {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, email VARCHAR(100) NOT NULL
         * UNIQUE, balance DECIMAL(10,2) CHECK (balance >= 0) DEFAULT 0.00). Napisz
         * małe "zestawy testów": (1) wstaw poprawny wiersz z domyślnym balance, (2)
         * wstaw drugi wiersz z tym samym id (PRIMARY KEY), (3) wstaw wiersz z email
         * NULL (NOT NULL), (4) wstaw wiersz z duplikatem email (UNIQUE), (5) wstaw
         * wiersz z balance=-50 (CHECK). Dla każdej próby wypisz "OK" albo
         * "ODRZUCONO: <komunikat>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CheckConstraintWithEnumLikeValues {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "orders" (id INT PRIMARY KEY, status VARCHAR(20) CHECK
         * (status IN ('NEW', 'PAID', 'SHIPPED', 'CANCELLED'))). Wstaw wiersze z każdym
         * poprawnym statusem, a potem spróbuj wstawić status='UNKNOWN' - złap
         * SQLException i wypisz komunikat, potwierdzając, że CHECK może symulować
         * prosty "enum" wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UpdateViolatingCheckConstraint {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance
         * >= 0)). Wstaw wiersz z balance=100. Wykonaj UPDATE zmniejszający balance o
         * 50 (powinno się udać), a potem UPDATE zmniejszający o kolejne 200 (zejście
         * pod 0 - powinno się nie udać) - złap SQLException i zweryfikuj (SELECT), że
         * balance NIE zmieniło się po nieudanej próbie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_OnDeleteCascadeVsSetNullComparison {
        /*
         * 🧪 Zadanie 19:
         * Utwórz DWIE równoległe struktury: "authors_cascade"/"books_cascade" (ON
         * DELETE CASCADE) i "authors_setnull"/"books_setnull" (ON DELETE SET NULL,
         * author_id dopuszcza NULL). Wstaw po 1 autorze i 2 książkach w każdej
         * strukturze. Usuń oba autorów i wypisz wynik: w wariancie cascade książki
         * ZNIKAJĄ, w wariancie set null książki PRZETRWAJĄ z author_id=NULL - wypisz
         * porównanie obu wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConstraintViolationBatchReport {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL,
         * sku VARCHAR(20) UNIQUE, price DECIMAL(10,2) CHECK (price >= 0)). Przygotuj
         * listę (List<String>) 6 poleceń INSERT: 2 poprawne, i po 1 naruszającemu
         * każde z 4 ograniczeń (PRIMARY KEY, NOT NULL, UNIQUE, CHECK - łącznie 4
         * błędne). Wykonaj je w pętli łapiąc SQLException dla każdego osobno i wypisz
         * raport, które się powiodły, a które nie i dlaczego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LibrarySchemaWithAllConstraintTypes {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj "authors" (id PK, name NOT NULL) i "books" (id PK, title NOT
         * NULL, author_id INT FK, isbn VARCHAR(20) UNIQUE, price DECIMAL(10,2) CHECK
         * (price >= 0), status VARCHAR(20) DEFAULT 'AVAILABLE'). Wstaw 1 autora i 1
         * poprawną książkę. Po kolei spróbuj wstawić: książkę z title=NULL, książkę z
         * duplikatem isbn, książkę z price ujemnym, książkę z nieistniejącym author_id.
         * Dla każdej próby wypisz, które ograniczenie zadziałało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CascadeChainWithMixedRules {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 3-poziomowy łańcuch z MIESZANYMI regułami: "companies" (id PK) ->
         * "departments" (id PK, company_id FK ON DELETE CASCADE) -> "employees" (id
         * PK, department_id INT FK REFERENCES departments(id) ON DELETE SET NULL,
         * department_id dopuszcza NULL). Wstaw firmę, dział, 2 pracowników. Usuń
         * firmę i zweryfikuj: dział zniknął (CASCADE), a pracownicy PRZETRWALI z
         * department_id=NULL (SET NULL zadziałało mimo że kaskada dotarła "przez"
         * usunięty dział).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConstraintDrivenStateMachine {
        /*
         * 🧪 Zadanie 23:
         * Zamodeluj prostą maszynę stanów zamówienia przez CHECK: "orders" (id PK,
         * status VARCHAR(20) CHECK (status IN ('NEW', 'PAID', 'SHIPPED', 'DELIVERED',
         * 'CANCELLED')) NOT NULL DEFAULT 'NEW'). Napisz metodę
         * tryTransition(Statement stmt, int orderId, String newStatus), która wykonuje
         * UPDATE i łapie SQLException, gdyby newStatus był poza dozwolonym zbiorem.
         * Przetestuj przejścia NEW->PAID (OK) i NEW->'INVALID_STATUS' (odrzucone).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiColumnCheckWithBusinessRule {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "discounts" (id INT PRIMARY KEY, discount_type VARCHAR(20)
         * CHECK (discount_type IN ('PERCENT', 'FIXED')), discount_value DECIMAL(10,2)
         * NOT NULL, CHECK ((discount_type = 'PERCENT' AND discount_value <= 100) OR
         * (discount_type = 'FIXED'))). Wstaw poprawny rabat procentowy (50%) i
         * poprawny rabat kwotowy (100 zł), a potem spróbuj wstawić rabat procentowy
         * 150% - złap SQLException, potwierdzając, że CHECK łączy DWIE kolumny w
         * jedną regułę biznesową.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SafeMigrationAddingNotNullToExistingTable {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "customers" (id INT PRIMARY KEY, name VARCHAR(100)) BEZ
         * ograniczenia NOT NULL na email (kolumna jeszcze nie istnieje), wstaw 3
         * wiersze. Dodaj kolumnę email (ALTER TABLE ADD COLUMN email VARCHAR(100)),
         * zaktualizuj wszystkie 3 wiersze realnymi adresami (UPDATE), a następnie
         * dodaj ograniczenie NOT NULL (ALTER TABLE ALTER COLUMN email SET NOT NULL) -
         * zweryfikuj, że się udało, bo wszystkie wiersze już mają wartość, i wypisz
         * potwierdzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ForeignKeyWithCompositeReference {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "order_items" (order_id INT, product_id INT, PRIMARY KEY(order_id,
         * product_id)) i "shipments" (id INT PRIMARY KEY, order_id INT, product_id
         * INT, FOREIGN KEY (order_id, product_id) REFERENCES order_items(order_id,
         * product_id)) - klucz obcy ZŁOŻONY z 2 kolumn wskazujący na klucz złożony.
         * Wstaw poprawną pozycję zamówienia i poprawną wysyłkę, a potem spróbuj
         * wstawić wysyłkę z poprawnym order_id, ale nieistniejącym product_id - złap
         * SQLException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConstraintPerformanceUnderBulkInsert {
        /*
         * 🧪 Zadanie 27:
         * Utwórz tabelę "transactions" (id INT PRIMARY KEY, account_id INT NOT NULL,
         * amount DECIMAL(10,2) NOT NULL CHECK (amount != 0)). Napisz metodę
         * bulkInsertWithValidation(Statement stmt, int count), która próbuje wstawić
         * "count" wierszy, z których co 10-ty jest CELOWO niepoprawny (amount=0),
         * łapiąc SQLException dla każdego nieudanego wiersza osobno (bez przerywania
         * całej pętli). Wygeneruj 30 wierszy i wypisz podsumowanie: ile się udało, ile
         * odrzucono.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullEcommerceConstraintAudit {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj mini-schemat sklepu z pełnym zestawem ograniczeń: "categories"
         * (id PK, name NOT NULL UNIQUE), "products" (id PK, category_id FK NOT NULL,
         * name NOT NULL, price CHECK (price >= 0)), "orders" (id PK, status DEFAULT
         * 'NEW' CHECK (status IN ('NEW','PAID','CANCELLED'))). Wstaw poprawne dane (2
         * kategorie, 3 produkty, 2 zamówienia). Wykonaj serię 6 celowo niepoprawnych
         * operacji łamiących różne ograniczenia (NOT NULL, UNIQUE, CHECK, FOREIGN KEY
         * x2, DEFAULT/CHECK combo). Zbierz wyniki w liście i wypisz czytelne
         * podsumowanie audytu na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CascadeVsRestrictDecisionMatrix {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj 3 warianty tej samej relacji authors/books z RÓŻNYMI regułami ON
         * DELETE: (A) domyślne zachowanie (bez ON DELETE - odrzuci DELETE autora z
         * książkami), (B) ON DELETE CASCADE (usunie książki razem z autorem), (C) ON
         * DELETE SET NULL (książki przetrwają z author_id=NULL). Dla każdego wariantu
         * wstaw autora z 2 książkami, spróbuj go usunąć i wypisz rezultat (błąd /
         * liczba pozostałych książek / stan author_id) - podsumuj różnice w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneBankingSchemaWithFullIntegrity {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletny mini-schemat bankowy: "customers" (id PK, name NOT
         * NULL, pesel VARCHAR(11) UNIQUE NOT NULL), "accounts" (id PK, customer_id FK
         * NOT NULL, account_number VARCHAR(20) UNIQUE NOT NULL, balance DECIMAL(12,2)
         * NOT NULL CHECK (balance >= 0) DEFAULT 0.00), "transactions" (id PK, account_id
         * FK NOT NULL, amount DECIMAL(12,2) NOT NULL CHECK (amount != 0), type
         * VARCHAR(10) CHECK (type IN ('DEPOSIT','WITHDRAWAL'))). Wstaw 2 klientów, po 1
         * koncie każdemu, kilka transakcji. Napisz metodę runFullConstraintAudit,
         * która testuje WSZYSTKIE 6 typów ograniczeń użytych w schemacie (min. 8
         * scenariuszy testowych: pozytywne i negatywne), zbiera wyniki w liście
         * rekordów (nazwa testu, oczekiwany rezultat, faktyczny rezultat) i wypisuje
         * końcowy raport z liczbą testów zakończonych sukcesem/porażką.
         */
        public static void main(String[] args) { }
    }
}
