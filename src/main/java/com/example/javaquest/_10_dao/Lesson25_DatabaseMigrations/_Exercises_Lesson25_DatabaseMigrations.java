package com.example.javaquest._10_dao.Lesson25_DatabaseMigrations;

public class _Exercises_Lesson25_DatabaseMigrations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstMigrationCreatesTable {
        /*
         * 🧪 Zadanie 1:
         * Utworz plik migracji "V1__create_books_table.sql" (w katalogu
         * odpowiadajacym "classpath:db/migration" tej lekcji, wg konwencji Flyway z
         * teorii) tworzacy tabele books (id BIGINT AUTO_INCREMENT PRIMARY KEY, title
         * VARCHAR(150)). Skonfiguruj Flyway (Flyway.configure().dataSource(url, "sa",
         * "").locations(...).load()) na bazie "jdbc:h2:mem:lesson25ex01;DB_CLOSE_DELAY=-1"
         * i wywolaj flyway.migrate(). Wypisz result.migrationsExecuted (powinno byc
         * 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SecondMigrationAltersTable {
        /*
         * 🧪 Zadanie 2:
         * Dodaj DRUGI plik migracji "V2__add_author_column.sql" (ALTER TABLE books
         * ADD COLUMN author VARCHAR(100)) w tym samym katalogu co Zadanie 1. Wywolaj
         * flyway.migrate() na TEJ SAMEJ bazie - sprawdz, ze wykonana zostala TYLKO
         * NOWA migracja (migrationsExecuted=1 przy tym wywolaniu, mimo ze lacznie sa
         * juz 2 pliki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MigrateIsIdempotentOnSecondRun {
        /*
         * 🧪 Zadanie 3:
         * Na bazie z Zadan 1-2 wywolaj flyway.migrate() PONOWNIE (trzeci raz z rzedu,
         * bez zadnego nowego pliku migracji). Sprawdz, ze result.migrationsExecuted
         * wynosi 0 - Flyway "wie" (dzieki tabeli flyway_schema_history), ze wszystkie
         * migracje sa juz zastosowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InspectFlywaySchemaHistoryTable {
        /*
         * 🧪 Zadanie 4:
         * Po migracji V1+V2 (Zadania 1-2), polacz sie z baza i wykonaj SELECT
         * "installed_rank", "version", "description", "success" FROM
         * "flyway_schema_history" ORDER BY "installed_rank" (pamietaj o
         * cudzyslowach, jak w lekcji - H2 traktuje nazwy Flyway jako case-sensitive).
         * Wypisz wszystkie wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MigrationFileNamingConventionCheck {
        /*
         * 🧪 Zadanie 5:
         * Napisz w kodzie (bez uzycia Flyway) metode boolean isValidMigrationFileName
         * (String fileName) sprawdzajaca regexem, czy nazwa pliku odpowiada
         * konwencji "V<liczba>__<opis>.sql" (dwa podkreslenia!). Przetestuj na 5
         * nazwach: 3 poprawnych (np. "V1__init.sql", "V2.1__patch.sql") i 2
         * niepoprawnych (np. "V1_init.sql" - jedno podkreslenie, "init.sql" - brak
         * prefiksu V).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DataInsertingMigration {
        /*
         * 🧪 Zadanie 6:
         * Dodaj TRZECI plik migracji "V3__seed_initial_books.sql" wstawiajacy 3
         * poczatkowe wiersze do books (INSERT INTO books...). Wywolaj migrate() na
         * bazie z Zadan 1-2 i sprawdz SELECT COUNT(*) FROM books - powinno wynosic
         * 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TargetSchemaVersionAfterMigration {
        /*
         * 🧪 Zadanie 7:
         * Po wykonaniu migracji V1, V2, V3 (Zadania 1, 2, 6) wypisz
         * result.targetSchemaVersion po WYWOLANIU migrate() - sprawdz, ze wskazuje
         * na najwyzsza zastosowana wersje (3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MigrateResultSuccessFlag {
        /*
         * 🧪 Zadanie 8:
         * Wywolaj flyway.migrate() na bazie z poprawnymi migracjami i wypisz
         * result.success (powinno byc true). Skomentuj w kodzie, kiedy (w
         * teoretycznym scenariuszu) ta flaga bylaby false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FreshDatabaseAppliesAllMigrationsAtOnce {
        /*
         * 🧪 Zadanie 9:
         * Uzywajac TYCH SAMYCH plikow migracji z Zadan 1, 2, 6, uruchom Flyway na
         * CALKOWICIE NOWEJ bazie ("jdbc:h2:mem:lesson25ex09;DB_CLOSE_DELAY=-1", ktora
         * jeszcze nie widziala zadnej migracji). Wywolaj migrate() JEDEN raz i
         * sprawdz, ze WSZYSTKIE 3 migracje zostaly wykonane naraz
         * (migrationsExecuted=3) - dowod, ze nowe srodowisko mozna zbudowac "od
         * zera".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LocationsPointingToDifferentFolder {
        /*
         * 🧪 Zadanie 10:
         * Skonfiguruj Flyway z .locations("classpath:db/migration") wskazujacym na
         * WLASNY podkatalog utworzony dla tego zadania (odrebny od folderu uzytego w
         * lekcji, np. na potrzeby izolacji cwiczenia), z jednym prostym plikiem
         * migracji tworzacym tabele "notes" (id, content). Wykonaj migrate() i
         * sprawdz, ze dziala niezaleznie od migracji z innych lekcji/cwiczen.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChecksumValidationDetectsModifiedMigration {
        /*
         * 🧪 Zadanie 11:
         * Wykonaj migracje V1 na bazie, potem (SYMULUJAC blad zespolowy) zmien
         * ZAWARTOSC pliku V1 (np. dodaj spacje/komentarz) i wywolaj migrate()
         * PONOWNIE na TEJ SAMEJ bazie. Zlap wyjatek walidacji checksum rzucany przez
         * Flyway i wypisz jego komunikat - dowod, ze Flyway wykrywa edycje juz
         * zastosowanej migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddingNewMigrationInsteadOfEditingOld {
        /*
         * 🧪 Zadanie 12:
         * Po Zadaniu 11 (blad checksum), PRZYWROC oryginalna zawartosc V1 (naprawiajac
         * blad) i dodaj NOWA migracje V4 z poprawka, ktora MIALA byc w V1 - w
         * komentarzu wyjasnij, dlaczego to jest WLASCIWE podejscie (nigdy nie edytuj
         * juz zastosowanej migracji, zawsze dodaj nowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BaselineOnExistingDatabase {
        /*
         * 🧪 Zadanie 13:
         * Recznie (bez Flyway, przez zwykle Statement) utworz tabele "legacy_data" na
         * nowej bazie H2 - symulujac "istniejaca baze produkcyjna" bez historii
         * migracji. Skonfiguruj Flyway z .baselineOnMigrate(true) i
         * .baselineVersion("1") i wywolaj migrate() z NOWYMI migracjami (V2+) -
         * sprawdz, ze Flyway "przyjmuje" istniejacy stan jako punkt startowy bez
         * próby ponownego tworzenia legacy_data.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RepeatableMigrationForViewsOrProcedures {
        /*
         * 🧪 Zadanie 14:
         * Utworz "migracje powtarzalna" o nazwie "R__create_books_view.sql" (prefiks
         * R, nie V - inny typ migracji Flyway, tworzacy CREATE OR REPLACE VIEW).
         * Wywolaj migrate() dwukrotnie - zademonstruj (w komentarzu, na podstawie
         * dokumentacji zachowania Flyway), ze migracje R__ wykonuja sie PONOWNIE,
         * gdy zmieni sie ich checksum, w przeciwienstwie do V__.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleSchemaVersionsWithBusinessDataMigration {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj sekwencje 4 migracji: V1 (tworzy tabele), V2 (dodaje kolumne
         * nullable), V3 (wypelnia UPDATE-em nowa kolumne dla istniejacych wierszy),
         * V4 (dodaje ograniczenie NOT NULL do tej kolumny - mozliwe TYLKO PO
         * wypelnieniu danych w V3). Wywolaj migrate() i sprawdz, ze CALA sekwencja
         * wykonuje sie w poprawnej kolejnosci bez bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CleanCommandResetsDatabaseInDevOnly {
        /*
         * 🧪 Zadanie 16:
         * Po wykonaniu migracji V1-V3, wywolaj flyway.clean() (usuwa WSZYSTKIE
         * obiekty schematu - dostepne w Flyway community dla wersji bez ograniczen
         * "clean disabled"). Sprawdz, ze tabele juz nie istnieja (SELECT rzuca
         * SQLException), a nastepne migrate() odtwarza je od zera. Skomentuj,
         * DLACZEGO clean() jest NIEBEZPIECZNE na produkcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InfoCommandListsPendingAndAppliedMigrations {
        /*
         * 🧪 Zadanie 17:
         * Po zastosowaniu V1-V2, dodaj NOWY plik V3 (bez jeszcze wywolania
         * migrate()). Wywolaj flyway.info().all() i wypisz dla kazdej migracji jej
         * wersje oraz stan (getState()) - sprawdz, ze V1/V2 pokazuja "SUCCESS", a V3
         * pokazuje "PENDING" (jeszcze niezastosowana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateCommandDetectsMissingAppliedMigrationFile {
        /*
         * 🧪 Zadanie 18:
         * Po zastosowaniu V1-V2, USUN plik V1 z katalogu migracji (symulujac
         * przypadkowe usuniecie pliku, ktory juz byl zastosowany na produkcji).
         * Wywolaj flyway.validate() i zlap wyjatek - wypisz jego komunikat,
         * pokazujacy, ze Flyway wykrywa BRAK pliku dla juz zastosowanej migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_OutOfOrderMigrationConfiguration {
        /*
         * 🧪 Zadanie 19:
         * Zastosuj V1, V3 (celowo z "dziura" - bez V2), potem dodaj PRZEOCZONE V2.
         * Domyslnie Flyway odmowi zastosowania V2 "po fakcie" (bo jest nizsza niz juz
         * zastosowana V3). Skonfiguruj .outOfOrder(true) i wywolaj migrate()
         * ponownie - sprawdz, ze TERAZ V2 zostaje zastosowane mimo "niesekwencyjnej"
         * kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestMigrationsAgainstFreshH2EachRun {
        /*
         * 🧪 Zadanie 20:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z co najmniej 4
         * testami: "migracje wykonuja sie bez bledow na nowej bazie", "druga
         * migracja jest idempotentna (0 nowych)", "flyway_schema_history ma
         * poprawna liczbe wpisow", "dane z migracji seed sa widoczne". Wypisz
         * PASSED/FAILED dla kazdego, kazdy test na WLASNEJ, izolowanej bazie H2.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MigrationRollbackStrategyViaCompensatingMigration {
        /*
         * 🧪 Zadanie 21:
         * Flyway community nie wspiera automatycznego "rollback" migracji (to
         * funkcja platna/Teams). Zademonstruj WLASCIWE podejscie: po zastosowaniu V1
         * (dodaje kolumne "temp_flag"), napisz V2 jako "migracje kompensujaca"
         * usuwajaca te kolumne (ALTER TABLE ... DROP COLUMN) - czyli "rollback" jest
         * realizowany jako KOLEJNA, NOWA migracja "do przodu", nie jako cofniecie.
         * Zweryfikuj stan schematu po V1 i po V2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ZeroDowntimeColumnRenamePattern {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj bezpieczny wzorzec "zmiany nazwy kolumny bez przestoju" jako
         * SERIA migracji: V1 dodaje NOWA kolumne (o docelowej nazwie) obok starej, V2
         * kopiuje dane ze starej do nowej (UPDATE), V3 (symulujac "kolejny etap
         * wdrozenia po aktualizacji calej aplikacji") usuwa STARA kolumne. Wykonaj
         * WSZYSTKIE trzy i sprawdz stan schematu po kazdym etapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MultiModuleMigrationLocationsMerged {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj Flyway z DWOMA lokalizacjami migracji naraz (.locations(loc1,
         * loc2) - dwa OSOBNE katalogi/pakiety, np. symulujace "modul podstawowy" i
         * "modul rozszerzen"), kazda z WLASNYMI plikami V1/V2 o roznych numeracjach
         * (uwaga na kolizje numerow wersji miedzy modulami!). Wywolaj migrate() i
         * wypisz kolejnosc faktycznie zastosowanych migracji z obu lokalizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ProgrammaticCallbackBeforeAfterMigrate {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj wlasny org.flywaydb.core.api.callback.Callback (albo
         * skonfiguruj .callbacks(...) z lambda-podobnym obiektem) wywolywany PRZED
         * (beforeMigrate) i PO (afterMigrate) calej operacji - wypisujacy log z
         * timestampem. Zarejestruj go w konfiguracji Flyway i sprawdz, ze oba
         * callbacki faktycznie sie wykonuja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MigrationPerformanceMeasurementForLargeSeed {
        /*
         * 🧪 Zadanie 25:
         * Napisz migracje V1 tworzaca tabele oraz V2 wstawiajaca (przez wiele
         * instrukcji INSERT w jednym pliku SQL) 500 wierszy danych testowych.
         * Zmierz (System.nanoTime() OKALAJACY wywolanie flyway.migrate()) czas
         * wykonania calej migracji i wypisz go - skomentuj, ze duze migracje danych
         * (seed) moga wplywac na czas startu aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConditionalMigrationBasedOnEnvironmentPlaceholders {
        /*
         * 🧪 Zadanie 26:
         * Napisz plik migracji uzywajacy PLACEHOLDERA Flyway (np. "${schemaPrefix}"
         * w nazwie tabeli lub w danych INSERT) i skonfiguruj Flyway z
         * .placeholders(Map.of("schemaPrefix", "dev_")) przekazujac WLASNA wartosc w
         * runtime. Wywolaj migrate() i sprawdz, ze wygenerowany SQL faktycznie
         * uzyl podstawionej wartosci (np. sprawdz nazwe utworzonej tabeli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrentApplicationInstancesRaceOnMigrate {
        /*
         * 🧪 Zadanie 27:
         * Uruchom DWA WATKI rownolegle, KAZDY tworzacy WLASNY obiekt Flyway (na TA
         * SAMA baze) i wywolujacy migrate() w tym samym czasie (symulujac 2 instancje
         * aplikacji startujace naraz). Sprawdz (dzieki wewnetrznemu mechanizmowi
         * blokowania Flyway na tabeli historii), ze migracje zostaly zastosowane
         * DOKLADNIE RAZ (bez duplikatow/bledow z powodu wyscigu) - policz koncowa
         * liczbe wpisow we flyway_schema_history.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigrationTestingWithEachTestOnOwnDatabase {
        /*
         * 🧪 Zadanie 28:
         * Napisz mini test-runner z testami integracyjnymi weryfikujacymi CALA
         * sciezke migracji od V1 do najnowszej NA CALKOWICIE NOWEJ bazie w KAZDYM
         * tescie (unikalny nazwa bazy per test, np. "jdbc:h2:mem:test_" +
         * UUID.randomUUID()) - zapewniajac pelna izolacje miedzy testami migracji.
         * Zweryfikuj co najmniej 3 testy sprawdzajace kolejne etapy schematu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DetectDriftBetweenTwoEnvironmentsUsingInfo {
        /*
         * 🧪 Zadanie 29:
         * Utworz DWIE bazy symulujace "dev" i "prod" - na jednej zastosuj WSZYSTKIE
         * migracje (V1-V4), na drugiej TYLKO CZESC (V1-V2, symulujac "zapomniane
         * wdrozenie"). Napisz metode porownujaca wyniki flyway.info().all() z obu baz
         * i wypisujaca RAPORT roznic (ktore wersje sa zastosowane na jednej, a nie
         * na drugiej) - to praktyczny sposob wykrywania "database drift" z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMigrationLifecycleMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletny cykl zycia migracji: V1 (schemat bazowy), V2 (dodanie
         * kolumny), V3 (seed danych), V4 (bezpieczna zmiana nazwy kolumny wg wzorca
         * z Zadania 22 - w jednej migracji dla uproszczenia), migracja powtarzalna
         * R__ (widok, Zadanie 14), oraz callback logujacy przebieg (Zadanie 24).
         * Wywolaj migrate() na nowej bazie, wypisz PELNY raport (flyway.info().all()
         * + zawartosc flyway_schema_history + stan danych po wszystkich krokach).
         */
        public static void main(String[] args) { }
    }
}
