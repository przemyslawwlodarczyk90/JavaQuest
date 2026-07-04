package com.example.javaquest._08_sql.Lesson01_DatabaseIntroduction;

public class _Exercises_Lesson01_DatabaseIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstConnectionAndTable {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 in-memory (np. "jdbc:h2:mem:ex01;DB_CLOSE_DELAY=-1").
         * Utwórz tabelę "books" (id INT PRIMARY KEY, title VARCHAR(100), author VARCHAR(100)).
         * Wstaw 2 książki: (1, 'Pan Tadeusz', 'Adam Mickiewicz') i (2, 'Lalka', 'Boleslaw Prus').
         * Wykonaj SELECT * FROM books i wypisz każdy wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimplestPossibleQuery {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą H2 in-memory. Wykonaj najprostsze możliwe zapytanie
         * "SELECT 1 + 1 AS result" (bez żadnej tabeli) i wypisz wartość kolumny "result".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CountRows {
        /*
         * 🧪 Zadanie 3:
         * Utwórz tabelę "movies" (id INT PRIMARY KEY, title VARCHAR(100)). Wstaw 5 filmów
         * o dowolnych tytułach. Wykonaj "SELECT COUNT(*) AS cnt FROM movies" i wypisz liczbę wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DbmsExamplesList {
        /*
         * 🧪 Zadanie 4:
         * Bez łączenia się z bazą: wypisz na konsoli (System.out.println) listę 5 przykładów
         * DBMS wspomnianych w lekcji (PostgreSQL, MySQL, Oracle Database, Microsoft SQL Server,
         * H2) razem z jednozdaniowym opisem każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RelationalVsNonRelationalPrint {
        /*
         * 🧪 Zadanie 5:
         * Wypisz na konsoli dwie sekcje: "RELACYJNE" z przykładami (PostgreSQL, MySQL, H2)
         * i "NIERELACYJNE" z przykładami (MongoDB - dokumentowa, Redis - klucz-wartość,
         * Neo4j - grafowa), po jednym zdaniu wyjaśnienia dla każdej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JdbcUrlAnatomy {
        /*
         * 🧪 Zadanie 6:
         * Dla URL-a "jdbc:h2:mem:students;DB_CLOSE_DELAY=-1" wypisz na konsoli rozbicie
         * na części składowe i wyjaśnienie każdej: "jdbc:h2:mem:" (tryb pamięciowy H2),
         * "students" (nazwa instancji bazy), "DB_CLOSE_DELAY=-1" (nie usuwaj bazy po
         * zamknięciu pierwszego połączenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InsertAndFilter {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "students" (id INT PRIMARY KEY, name VARCHAR(100), city VARCHAR(100)).
         * Wstaw 5 studentów z różnych miast (co najmniej 2 z Warszawy). Wykonaj zapytanie
         * "SELECT name FROM students WHERE city = 'Warszawa'" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CatchInvalidSql {
        /*
         * 🧪 Zadanie 8:
         * Spróbuj wykonać celowo błędne zapytanie SQL (np. "SELECT * FROM nieistniejaca_tabela")
         * w bloku try/catch(SQLException). Wypisz komunikat błędu (e.getMessage()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FormattedRowLoop {
        /*
         * 🧪 Zadanie 9:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, name VARCHAR(100), price DECIMAL(10,2)).
         * Wstaw 4 produkty. Odczytaj wszystkie wiersze i wypisz każdy w formacie
         * "#<id>: <name> - <price> zl".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TwoTablesNoJoinYet {
        /*
         * 🧪 Zadanie 10:
         * Utwórz dwie NIEZALEŻNE (bez klucza obcego - to poznamy w Lesson02) tabele:
         * "authors" (id, name) i "books" (id, title). Wstaw po 2 wiersze do każdej.
         * Wypisz osobno zawartość obu tabel dwoma oddzielnymi zapytaniami SELECT.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoConnectionsSameInMemoryDb {
        /*
         * 🧪 Zadanie 11:
         * Otwórz połączenie "conn1" do "jdbc:h2:mem:shared11;DB_CLOSE_DELAY=-1", utwórz
         * tabelę "notes" i wstaw jeden wiersz (1, 'pierwsza notatka'), zamknij conn1.
         * Następnie otwórz DRUGIE, niezależne połączenie "conn2" do TEGO SAMEGO URL-a
         * i odczytaj z niego dane z tabeli "notes" - udowadniając, że to wciąż ta sama
         * baza w pamięci (dzięki DB_CLOSE_DELAY=-1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LibrarySchemaNoJoin {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj mini-schemat biblioteki: "authors" (id, name) i "books" (id, author_id, title) -
         * bez ograniczenia FOREIGN KEY (poznamy je w Lesson02, tu author_id to zwykła kolumna
         * INT). Wstaw 2 autorów i po 2 książki dla każdego. Dla każdego autora wykonaj osobne
         * zapytanie SELECT title FROM books WHERE author_id = ? i wypisz jego książki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ComparisonTablePrint {
        /*
         * 🧪 Zadanie 13:
         * Wypisz tabelę porównawczą (w formie kolejnych linii println) baz relacyjnych
         * i nierelacyjnych wg 4 kryteriów: model danych, sztywność schematu, język zapytań,
         * typowe przykłady. Jedna linia na kryterium, format "kryterium: relacyjna vs NoSQL".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SchemaRigidityDemo {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "customers" (id INT PRIMARY KEY, name VARCHAR(100)). Spróbuj wstawić
         * wiersz odwołujący się do NIEISTNIEJĄCEJ kolumny "phone" (np. "INSERT INTO customers
         * (id, name, phone) VALUES (...)"). Złap SQLException i wypisz, że to demonstruje
         * sztywność schematu relacyjnego (baza NIE pozwala na nieznane kolumny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DuplicatePrimaryKeyDemo {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "invoices" (id INT PRIMARY KEY, amount DECIMAL(10,2)). Wstaw wiersz
         * (1, 100.00), a następnie spróbuj wstawić DRUGI wiersz z tym samym id=1.
         * Złap SQLException i wypisz komunikat błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConnectionFactoryMethod {
        /*
         * 🧪 Zadanie 16:
         * Napisz prywatną metodę pomocniczą openConnection(String dbName) zwracającą
         * Connection do "jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1". Użyj jej do utworzenia
         * dwóch NIEZALEŻNYCH baz ("shopA" i "shopB"), w każdej utwórz tabelę "items" z innymi
         * danymi i wypisz zawartość obu, dowodząc, że to oddzielne instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_KeyValueStyleTable {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj relacyjną tabelę "settings" (id INT PRIMARY KEY, config_key VARCHAR(50),
         * config_value VARCHAR(200)) naśladującą prosty model klucz-wartość znany z baz
         * NoSQL (np. Redis). Wstaw 3 pary klucz-wartość (np. "theme"/"dark",
         * "pageSize"/"20", "language"/"pl") i wypisz je wszystkie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_InMemoryLifetimeExplanation {
        /*
         * 🧪 Zadanie 18:
         * Utwórz bazę H2 in-memory BEZ opcji DB_CLOSE_DELAY=-1 (czyli sam
         * "jdbc:h2:mem:ex18"), utwórz tabelę, wstaw dane i odczytaj je w ramach JEDNEGO
         * otwartego połączenia (try-with-resources) - to zadziała. Dodaj komentarz
         * wyjaśniający, że po zamknięciu tego jedynego połączenia baza (i jej dane)
         * zniknęłyby całkowicie, bo domyślnie H2 in-memory żyje tylko, dopóki żyje
         * ostatnie połączenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ShopCategoriesAndProductsPreview {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj mini-schemat sklepu (bez FK, zwykłe kolumny INT): "categories" (id, name)
         * i "products" (id, category_id, name, price). Wstaw 2 kategorie ("Elektronika",
         * "Ksiazki") i po 2 produkty w każdej. Dla każdej kategorii wypisz jej produkty
         * (osobne zapytania SELECT, bez JOIN - ten poznamy w Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MultiTableSummaryReport {
        /*
         * 🧪 Zadanie 20:
         * Utwórz 3 tabele: "users" (id, name), "products" (id, name), "orders" (id, user_id,
         * product_id) - wszystkie kolumny relacji jako zwykłe INT (bez FK). Wstaw po 2 wiersze
         * do users i products oraz 3 zamówienia. Wypisz podsumowanie: liczbę wierszy w każdej
         * z 3 tabel (3 osobne zapytania COUNT(*)).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BatchDdlFromList {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę runStatements(Statement stmt, List<String> statements), która
         * wykonuje po kolei (stmt.execute) każdy element listy. Użyj jej, podając listę
         * zawierającą 2 polecenia CREATE TABLE ("authors", "books" - bez FK) i 4 polecenia
         * INSERT, budując mini-bibliotekę. Na końcu wypisz zawartość obu tabel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenericResultSetPrinter {
        /*
         * 🧪 Zadanie 22:
         * Napisz generyczną metodę printResultSet(ResultSet rs), która przy pomocy
         * rs.getMetaData() odczytuje liczbę kolumn i ich nazwy, a następnie wypisuje
         * KAŻDY wiersz w formacie "kolumna1=wartosc1, kolumna2=wartosc2, ...", niezależnie
         * od struktury tabeli. Przetestuj ją na dwóch różnych tabelach o różnej liczbie kolumn.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SequentialWritesFromTwoConnections {
        /*
         * 🧪 Zadanie 23:
         * Utwórz bazę "jdbc:h2:mem:shared23;DB_CLOSE_DELAY=-1" z tabelą "events" (id INT
         * PRIMARY KEY, source VARCHAR(50)). Otwórz połączenie "connA", wstaw nim 2 wiersze
         * (source='A') i zamknij je. Otwórz połączenie "connB" do tej samej bazy, wstaw nim
         * 2 kolejne wiersze (source='B') i zamknij je. Otwórz trzecie połączenie "connC" i
         * zweryfikuj przez SELECT COUNT(*), że w tabeli są wszystkie 4 wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MiniReportingTool {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj narzędzie raportujące: utwórz 3 tabele ("customers", "products", "orders" -
         * bez FK), wstaw przykładowe dane (min. 3 wiersze w każdej), a następnie wypisz
         * sformatowany raport tekstowy: nagłówek "=== RAPORT ===" i dla każdej tabeli linię
         * "<nazwa_tabeli>: <liczba_wierszy> wierszy".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FriendlyConnectionErrorHandling {
        /*
         * 🧪 Zadanie 25:
         * Spróbuj połączyć się z celowo niepoprawnym URL-em JDBC (np. "jdbc:h2:mem:" z
         * literówką w prefiksie, np. "jdbc:h3:mem:test" - nieznany podprotokół). Złap
         * wyjątek (SQLException) i wypisz przyjazny komunikat "Nie udalo sie polaczyc z
         * baza danych: <przyczyna>" zamiast surowego stack trace'u.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SchemaMigrationCopy {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "users_v1" (id INT PRIMARY KEY, full_name VARCHAR(100)) i wstaw
         * 3 wiersze. Utwórz drugą tabelę "users_v2" z dodatkową kolumną (id INT PRIMARY KEY,
         * full_name VARCHAR(100), created_at TIMESTAMP). Przepisz dane z users_v1 do users_v2
         * (SELECT z v1 w pętli, INSERT do v2, ustawiając created_at na aktualny znacznik
         * czasu np. LocalDateTime.now()). Zweryfikuj przez COUNT(*), że liczba wierszy się zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CategoryCountReport {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj tabelę "inventory_items" (id INT PRIMARY KEY, category VARCHAR(50), name
         * VARCHAR(100)). Wstaw 6 przedmiotów należących do 3 różnych kategorii (po 2 na
         * kategorię). Bez używania GROUP BY (poznamy w Lesson13): pobierz
         * "SELECT DISTINCT category FROM inventory_items" i dla każdej znalezionej kategorii
         * wykonaj osobne zapytanie COUNT(*), wypisując "<kategoria>: <liczba>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConstraintViolationTestSuite {
        /*
         * 🧪 Zadanie 28:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, email VARCHAR(100) NOT NULL).
         * Napisz małe "zestawy testów": spróbuj (1) wstawić poprawny wiersz - powinno się
         * udać, (2) wstawić drugi wiersz z tym samym id - powinno się nie udać (PRIMARY KEY),
         * (3) wstawić wiersz z email=NULL - powinno się nie udać (NOT NULL). Dla każdej próby
         * wypisz "OK" albo "ODRZUCONO: <komunikat>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PersistenceAcrossReconnects {
        /*
         * 🧪 Zadanie 29:
         * Zademonstruj "życie" bazy H2 in-memory przez cały czas trwania JVM: utwórz bazę
         * "jdbc:h2:mem:persist29;DB_CLOSE_DELAY=-1", w pierwszym bloku try-with-resources
         * utwórz tabelę i wstaw 3 wiersze, zamknij połączenie. W DRUGIM, osobnym bloku
         * try-with-resources otwórz połączenie do tego samego URL-a i odczytaj te same
         * 3 wiersze - dowodząc, że dane przetrwały mimo braku otwartego połączenia między
         * blokami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ShopSchemaCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny mini-schemat sklepu z lekcji: "users" (id, name, email),
         * "products" (id, name, price), "orders" (id, user_id, order_date),
         * "order_items" (id, order_id, product_id, quantity) - wszystkie relacje jako
         * zwykłe kolumny INT (FK poznamy w Lesson02). Wstaw 2 użytkowników, 3 produkty,
         * 2 zamówienia i po 2 pozycje w każdym zamówieniu. Wypisz pełny raport tekstowy:
         * dla każdego zamówienia wypisz jego id, właściciela (osobne zapytanie po user_id)
         * i listę pozycji zamówienia (osobne zapytanie po order_id) z nazwą produktu
         * (osobne zapytanie po product_id) i ilością - bez użycia JOIN.
         */
        public static void main(String[] args) { }
    }
}
