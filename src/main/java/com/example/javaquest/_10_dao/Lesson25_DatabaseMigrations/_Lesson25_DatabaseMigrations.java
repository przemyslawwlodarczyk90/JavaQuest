package com.example.javaquest._10_dao.Lesson25_DatabaseMigrations;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson25_DatabaseMigrations {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DLACZEGO NIE TWORZYĆ TABEL RĘCZNIE (zwłaszcza na produkcji)?
         * ============================================================
         * We wszystkich dotychczasowych lekcjach _09_jdbc i _10_dao
         * tworzyliśmy tabele "ręcznie" - wywołując CREATE TABLE z kodu
         * Javy przy starcie programu (setUpSchema). To świetnie się
         * sprawdza w lekcjach/demo, ale w prawdziwym projekcie rodzi
         * poważne problemy:
         *
         * - Jak upewnić się, że baza na LAPTOPIE developera, na serwerze
         *   TESTOWYM i na PRODUKCJI mają DOKŁADNIE ten sam schemat?
         * - Co się stanie, gdy trzeba dodać nową kolumnę do tabeli, w
         *   której są już MILIONY wierszy na produkcji - kto i jak
         *   odpali `ALTER TABLE` na żywej bazie, i w jakiej kolejności
         *   względem wdrożenia nowej wersji aplikacji?
         * - Jak dowiedzieć się, JAKA wersja schematu jest aktualnie na
         *   danym środowisku, i czy ktoś przypadkiem nie zapomniał
         *   odpalić jednej ze zmian?
         *
         * Ręczne zarządzanie schematem (albo - jeszcze gorzej - klikanie
         * zmian w GUI klienta bazy danych) nie skaluje się i prowadzi do
         * tzw. "database drift": schemat na produkcji z czasem zaczyna
         * się RÓŻNIĆ od tego na środowisku deweloperskim, bo ktoś
         * zapomniał zsynchronizować zmianę.
         */

        /*
         * ============================================================
         * 🔹 WERSJONOWANIE SCHEMATU - MIGRACJE
         * ============================================================
         * Rozwiązanie: schemat bazy danych traktujemy tak samo jak kod
         * źródłowy - jako serię MAŁYCH, PONUMEROWANYCH, NIEZMIENNYCH
         * (immutable) kroków zwanych MIGRACJAMI. Każda migracja to plik
         * SQL (albo skrypt w innym formacie) opisujący JEDNĄ zmianę
         * schematu: "utwórz tabelę X", "dodaj kolumnę Y", "utwórz
         * indeks Z". Migracje trzymamy w repozytorium kodu (razem z
         * aplikacją) i uruchamiamy w USTALONEJ KOLEJNOŚCI, automatycznie,
         * przy starcie aplikacji (albo jako osobny krok wdrożenia).
         *
         * Narzędzie do migracji (tu: Flyway) PAMIĘTA, które migracje
         * zostały już zastosowane na danej bazie (w specjalnej tabeli
         * historii) - dzięki temu:
         * - nigdy nie wykona tej samej migracji dwa razy,
         * - dokładnie wiadomo, w jakiej "wersji" jest dana baza,
         * - nowe środowisko (np. baza testowa) można zbudować od zera,
         *   odtwarzając WSZYSTKIE migracje po kolei - identyczny wynik
         *   wszędzie.
         */

        /*
         * ============================================================
         * 🔍 FLYWAY - KONWENCJA NAZEWNICTWA PLIKÓW MIGRACJI
         * ============================================================
         * Flyway (biblioteka użyta w tym projekcie - `flyway-core`,
         * zależność już dodana w pom.xml) domyślnie szuka plików migracji
         * na classpath, w katalogu `db/migration`
         * (czyli w projekcie Maven: `src/main/resources/db/migration/`).
         * Nazwa pliku ma ŚCISŁY format:
         *
         *     V<wersja>__<opis>.sql
         *
         * - `V`          - prefiks migracji "wersjonowanej" (najczęstszy typ)
         * - `<wersja>`   - liczba (może być z kropkami, np. 1, 2, 1.1, 2024.01.15)
         * - `__`         - DWA podkreślenia (to nie literówka - Flyway
         *                  rozróżnia pojedyncze i podwójne podkreślenie!)
         * - `<opis>`     - dowolny opis słowny (podkreślenia zamiast spacji)
         *
         * Przykłady poprawnych nazw:
         *     V1__create_users_table.sql
         *     V2__add_email_column.sql
         *     V3__add_index_on_email.sql
         *
         * Do tej lekcji dodane zostały DOKŁADNIE takie dwa pliki w
         * `src/main/resources/db/migration/`:
         *     V1__create_users_table.sql  - tworzy tabele users (id, first_name)
         *     V2__add_email_column.sql    - dodaje kolumne email + uzupelnia dane
         *
         * ⚠️ RAZ ZASTOSOWANA migracja jest NIEZMIENNA (immutable) - Flyway
         * liczy sumę kontrolną (checksum) każdego pliku i przy kolejnym
         * uruchomieniu WYKRYJE, jeśli ktoś edytował już zastosowany plik
         * (i zgłosi błąd walidacji). Zmianę robimy zawsze przez DODANIE
         * NOWEGO pliku (V3, V4...), nigdy przez edycję V1/V2.
         */

        String url = "jdbc:h2:mem:daolesson25;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String password = "";

        System.out.println("=== URUCHAMIANIE MIGRACJI FLYWAY ===");

        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("classpath:db/migration")
                .load();

        MigrateResult result = flyway.migrate();

        System.out.println("Sukces: " + result.success);
        System.out.println("Liczba wykonanych migracji: " + result.migrationsExecuted);
        System.out.println("Docelowa wersja schematu: " + result.targetSchemaVersion);

        /*
         * ============================================================
         * 🔍 TABELA flyway_schema_history - JAK FLYWAY ŚLEDZI WERSJE
         * ============================================================
         * Przy pierwszym uruchomieniu Flyway sam tworzy w bazie tabelę
         * `flyway_schema_history` - to w niej zapisuje historię
         * WSZYSTKICH zastosowanych migracji (numer wersji, opis,
         * checksum, kto/kiedy zastosował, czy się powiodła, ile trwała).
         * Przy KAŻDYM kolejnym uruchomieniu Flyway najpierw odczytuje tę
         * tabelę, żeby wiedzieć, które migracje są JUŻ zastosowane - i
         * wykonuje TYLKO te nowe, których jeszcze nie ma w historii.
         */

        System.out.println("\n=== ZAWARTOSC TABELI flyway_schema_history ===");
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     // Flyway tworzy te tabele (i jej kolumny) z nazwami w
                     // CUDZYSLOWIE (lowercase, "case sensitive") - w H2 trzeba
                     // wiec rowniez zacytowac nazwy w zapytaniu, inaczej silnik
                     // szuka (domyslnie duzych liter) odpowiednikow, ktorych nie ma.
                     "SELECT \"installed_rank\", \"version\", \"description\", \"success\" "
                             + "FROM \"flyway_schema_history\" ORDER BY \"installed_rank\"")) {
            while (rs.next()) {
                System.out.println("  #" + rs.getInt("installed_rank")
                        + " wersja=" + rs.getString("version")
                        + " opis='" + rs.getString("description") + "'"
                        + " sukces=" + rs.getBoolean("success"));
            }
        }

        /*
         * ============================================================
         * 🔹 DOWÓD, ŻE SCHEMAT REALNIE SIĘ ZMIENIŁ
         * ============================================================
         * Sprawdźmy zawartość tabeli `users` PO obu migracjach - powinna
         * mieć kolumnę `email` (dodaną w V2), wypełnioną dla wierszy
         * wstawionych jeszcze w V1.
         */

        System.out.println("\n=== ZAWARTOSC TABELI 'users' PO MIGRACJACH V1 + V2 ===");
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, first_name, email FROM users ORDER BY id")) {
            while (rs.next()) {
                System.out.println("  #" + rs.getLong("id") + " " + rs.getString("first_name")
                        + " <" + rs.getString("email") + ">");
            }
        }

        /*
         * ============================================================
         * 🔍 CO SIĘ STANIE PRZY PONOWNYM URUCHOMIENIU migrate()?
         * ============================================================
         * Gdybyśmy teraz wywołali `flyway.migrate()` jeszcze raz (np. przy
         * kolejnym starcie aplikacji), Flyway sprawdzi historię, zobaczy,
         * że V1 i V2 są już zastosowane, i NIC nie zrobi (0 nowych
         * migracji) - bezpieczne do wywoływania przy KAŻDYM starcie
         * aplikacji.
         */

        System.out.println("\n=== PONOWNE wywolanie migrate() (V1+V2 juz zastosowane) ===");
        MigrateResult secondRun = flyway.migrate();
        System.out.println("Liczba NOWO wykonanych migracji: " + secondRun.migrationsExecuted + " (oczekiwano: 0)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ręczne tworzenie/zmienianie tabel na produkcji prowadzi do
         *   "database drift" - rozjazdu schematu między środowiskami.
         * - Migracje = wersjonowane, niezmienne kroki zmiany schematu,
         *   trzymane w repozytorium kodu obok aplikacji.
         * - Flyway (`org.flywaydb:flyway-core`, już w pom.xml) szuka
         *   plików `V<wersja>__<opis>.sql` w `classpath:db/migration`
         *   (czyli `src/main/resources/db/migration/`).
         * - API: `Flyway.configure().dataSource(url, user, pass)
         *   .locations(...).load()`, potem `.migrate()`.
         * - Flyway śledzi zastosowane migracje w tabeli
         *   `flyway_schema_history` - każde kolejne `migrate()` wykonuje
         *   TYLKO nowe, jeszcze niezastosowane pliki.
         * - Migracja RAZ zastosowana jest NIEZMIENNA - zmianę robimy
         *   zawsze przez DODANIE nowego pliku (V3...), nigdy przez
         *   edycję już zastosowanego V1/V2.
         */
    }
}
