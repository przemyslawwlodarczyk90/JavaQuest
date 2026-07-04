package com.example.javaquest._08_sql.Lesson01_DatabaseIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson01_DatabaseIntroduction {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST BAZA DANYCH?
         * ============================================================
         * BAZA DANYCH (database) – zorganizowany zbiór danych,
         * przechowywany trwale (na dysku) i zaprojektowany tak, by
         * dało się go łatwo przeszukiwać, filtrować, modyfikować oraz
         * współdzielić między wieloma aplikacjami/użytkownikami.
         *
         * To NIE jest to samo co "plik z danymi". Baza danych daje
         * dodatkowo m.in.:
         * - język zapytań (SQL) do wyszukiwania i modyfikacji danych,
         * - ograniczenia integralności (np. "email musi być unikalny"),
         * - obsługę wielu równoczesnych połączeń (wielu klientów naraz),
         * - transakcje (patrz Lesson19) – gwarancję "wszystko albo nic",
         * - odporność na awarie (dane przetrwają restart aplikacji).
         *
         * 🔹 CZYM JEST DBMS?
         * DBMS (Database Management System – system zarządzania bazą
         * danych) to PROGRAM, który faktycznie przechowuje dane na
         * dysku, wykonuje zapytania SQL, pilnuje reguł integralności
         * i obsługuje połączenia klientów. Mówiąc "baza danych" często
         * mamy na myśli właśnie DBMS + dane, którymi zarządza.
         *
         * Przykłady popularnych DBMS: PostgreSQL, MySQL, Oracle Database,
         * Microsoft SQL Server, SQLite, H2 (ten, którego użyjemy w kursie).
         */

        System.out.println("=== CZYM JEST BAZA DANYCH ===");
        System.out.println("Baza danych = zorganizowany, trwały zbior danych.");
        System.out.println("DBMS = program, ktory tym zbiorem zarzadza (np. PostgreSQL, MySQL, H2).");

        /*
         * ============================================================
         * 🔹 RELACYJNA vs NIERELACYJNA BAZA DANYCH
         * ============================================================
         * BAZY RELACYJNE (SQL, relational databases):
         * - Dane przechowywane w TABELACH (wiersze x kolumny).
         * - Tabele mogą być ze sobą POWIĄZANE (relacje) przez klucze
         *   obce (FOREIGN KEY) – np. zamówienie jest powiązane
         *   z użytkownikiem, który je złożył.
         * - Sztywny, z góry zdefiniowany SCHEMAT (struktura kolumn
         *   i ich typów musi być znana zawczasu).
         * - Język zapytań: SQL (Structured Query Language).
         * - Przykłady: PostgreSQL, MySQL, Oracle, SQL Server, H2.
         *
         * BAZY NIERELACYJNE (NoSQL – "Not Only SQL"):
         * - Różne modele danych: dokumentowy (JSON-podobne dokumenty –
         *   MongoDB), klucz-wartość (Redis), kolumnowy (Cassandra),
         *   grafowy (Neo4j).
         * - Zwykle ELASTYCZNY schemat (dokumenty w tej samej kolekcji
         *   mogą mieć różne pola).
         * - Często projektowane pod kątem ogromnej skali i wydajności
         *   przy prostszych zapytaniach (mniej złożone JOIN-y niż SQL).
         *
         * 📌 W tym rozdziale skupiamy się WYŁĄCZNIE na bazach relacyjnych
         * i języku SQL – to wciąż najczęściej spotykany rodzaj bazy
         * danych w typowych aplikacjach biznesowych.
         */

        System.out.println("\n=== RELACYJNA vs NIERELACYJNA ===");
        System.out.println("Relacyjna (SQL): tabele + relacje + sztywny schemat (PostgreSQL, MySQL, H2...)");
        System.out.println("Nierelacyjna (NoSQL): dokumenty/klucz-wartosc/grafy, elastyczny schemat (MongoDB, Redis...)");

        /*
         * ============================================================
         * 🔍 MODEL KLIENT-SERWER W BAZACH DANYCH
         * ============================================================
         * W typowym, "normalnym" scenariuszu baza danych to ODRĘBNY
         * PROCES (często na ODRĘBNEJ MASZYNIE / serwerze), zupełnie
         * niezależny od Twojej aplikacji Java.
         *
         *      [Aplikacja Java]  <--- sieć/TCP --->  [Serwer bazy danych]
         *         (klient)                              (PostgreSQL,
         *                                                 MySQL, ...)
         *
         * Aplikacja Java jest w tym modelu KLIENTEM – NAWIĄZUJE
         * POŁĄCZENIE (connection) z serwerem bazy danych (podając adres,
         * port, nazwę bazy, login, hasło) i przez to połączenie wysyła
         * zapytania SQL oraz odbiera wyniki.
         *
         * ⚠️ KLUCZOWA RZECZ DO ZAPAMIĘTANIA:
         * Baza danych to ŹRÓDŁO DANYCH ZEWNĘTRZNE względem aplikacji.
         * Aplikacja Java NIE MA bazy danych "w sobie" – ona się z nią
         * tylko ŁĄCZY. Baza danych żyje własnym życiem: działa zanim
         * aplikacja wystartuje i będzie działać dalej, gdy aplikacja
         * się zamknie. Wiele różnych aplikacji (a nawet napisanych
         * w różnych językach) może łączyć się do TEJ SAMEJ bazy danych.
         *
         * 🔹 POSTGRESQL vs MYSQL – krótkie porównanie
         * Oba to dojrzałe, bardzo popularne, relacyjne DBMS działające
         * w modelu klient-serwer:
         * - PostgreSQL – w pełni otwartoźródłowy (licencja PostgreSQL,
         *   permisywna), słynie z bogactwa funkcji SQL, ścisłego
         *   przestrzegania standardu SQL i zaawansowanych typów danych.
         * - MySQL – bardzo popularny, obecnie własność Oracle, dostępny
         *   w wersji open-source (GPL) oraz komercyjnej; historycznie
         *   nieco prostszy, szeroko stosowany w web-hostingu (np. z PHP).
         * Różnice praktyczne (drobne odstępstwa od standardu SQL, nazwy
         * niektórych funkcji) są dla początkującego mniej istotne niż
         * to, że OBA są relacyjnymi bazami danych i uczysz się tego
         * samego SQL – z drobnymi różnicami dialektu poznasz je szybko,
         * gdy zajdzie taka potrzeba w konkretnym projekcie.
         */

        System.out.println("\n=== MODEL KLIENT-SERWER ===");
        System.out.println("Aplikacja Java = KLIENT, ktory laczy sie z zewnetrznym serwerem bazy danych.");
        System.out.println("PostgreSQL i MySQL to dwa popularne, relacyjne DBMS dzialajace tak wlasnie.");

        /*
         * ============================================================
         * 🔍 H2 – "TRZECIA OPCJA", NIETYPOWA
         * ============================================================
         * W tym kursie użyjemy H2 – lekkiej, czysto-javowej, relacyjnej
         * bazy danych. H2 jest wyjątkowe, bo potrafi działać w TRYBIE
         * IN-MEMORY, WEWNĄTRZ TEGO SAMEGO PROCESU JVM co nasza
         * aplikacja Java – bez żadnego osobnego serwera, bez instalacji,
         * bez konfiguracji. Idealne do nauki i testów: każda lekcja
         * uruchamia swoją bazę "w locie" i wynik widzimy od razu.
         *
         * ⚠️ To NIE JEST typowy scenariusz produkcyjny! W realnych
         * aplikacjach baza danych to niemal zawsze ODRĘBNY serwer
         * (PostgreSQL/MySQL/inny), z którym aplikacja łączy się przez
         * sieć – dokładnie tak, jak opisano wyżej w modelu
         * klient-serwer. H2 "w pamięci" to wygodny skrót na potrzeby
         * kursu, żeby móc uczyć się SQL bez instalowania czegokolwiek.
         *
         * H2 potrafi też działać jako prawdziwy serwer sieciowy albo
         * zapisywać dane do pliku na dysku – ale w tym kursie skupiamy
         * się na najprostszym trybie: in-memory.
         */

        System.out.println("\n=== H2 – BAZA DZIALAJACA W TEJ SAMEJ JVM ===");
        System.out.println("H2 (in-memory) to wyjatek na potrzeby nauki - normalnie baza to osobny serwer!");

        /*
         * ============================================================
         * 🔹 PIERWSZE POŁĄCZENIE I ZAPYTANIE
         * ============================================================
         * Connection i Statement poznamy szczegółowo w rozdziale
         * o JDBC — tu używamy ich tylko jako narzędzia do uruchomienia SQL.
         *
         * URL "jdbc:h2:mem:lesson01;DB_CLOSE_DELAY=-1" oznacza:
         * - "jdbc:h2:mem:" -> baza H2 w pamięci (nie na dysku),
         * - "lesson01" -> nazwa tej konkretnej instancji bazy,
         * - "DB_CLOSE_DELAY=-1" -> nie usuwaj bazy po zamknięciu
         *   pierwszego połączenia (przydatne, gdyby otwierać więcej
         *   niż jedno połączenie do tej samej bazy).
         */

        System.out.println("\n=== PIERWSZE POLACZENIE Z BAZA H2 ===");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:lesson01;DB_CLOSE_DELAY=-1");
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100))");
            stmt.execute("INSERT INTO users VALUES (1, 'Jan Kowalski', 'jan@example.com')");
            stmt.execute("INSERT INTO users VALUES (2, 'Anna Nowak', 'anna@example.com')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getString("email"));
            }
            // Oczekiwany wynik:
            // 1 - Jan Kowalski - jan@example.com
            // 2 - Anna Nowak - anna@example.com
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Baza danych = zorganizowany, trwały zbiór danych.
         * - DBMS = program zarządzający bazą danych (np. PostgreSQL,
         *   MySQL, H2).
         * - Bazy relacyjne (SQL): tabele + relacje + sztywny schemat.
         * - Bazy nierelacyjne (NoSQL): elastyczne modele danych
         *   (dokumenty, klucz-wartość, grafy...).
         * - Zwykle baza danych to ODRĘBNY serwer, z którym aplikacja
         *   Java łączy się jako KLIENT (model klient-serwer) – baza
         *   jest ŹRÓDŁEM DANYCH ZEWNĘTRZNYM, nie częścią aplikacji.
         * - PostgreSQL i MySQL to dwa popularne, relacyjne DBMS-y,
         *   różniące się licencją i ekosystemem, ale uczące się tego
         *   samego SQL.
         * - H2 w trybie in-memory to wygodny wyjątek na potrzeby nauki –
         *   działa w tej samej JVM co aplikacja, bez instalacji.
         */
    }
}
