package com.example.javaquest._23_spring_data_jpa.Lesson14_MigrationsWithFlyway;

public class _Exercises_Lesson14_MigrationsWithFlyway {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyFlywayAutoRunsInSpringBoot {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, DLACZEGO Flyway URUCHAMIA SIE SAM W
         * Spring Boot (jakie 2 warunki musza byc spelnione).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateFirstMigrationFile {
        /*
         * 🧪 Zadanie 2:
         * Utworz plik `V1__create_products_table.sql` W `classpath:
         * db/migration/cwiczenia`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConfigureFlywayLocationsProperty {
        /*
         * 🧪 Zadanie 3:
         * Ustaw `spring.flyway.locations` NA WLASNY podfolder.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BootContextAndVerifyTableCreated {
        /*
         * 🧪 Zadanie 4:
         * Uruchom kontekst I zweryfikuj (PRZEZ `DataSource`), ze tabela
         * ISTNIEJE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryFlywaySchemaHistoryTable {
        /*
         * 🧪 Zadanie 5:
         * Odpytaj `flyway_schema_history` I wypisz WSZYSTKIE wpisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddSecondMigrationFile {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `V2__...sql` DODAJACY nowa kolumne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainVersionNamingConvention {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij konwencje nazewnictwa
         * `V<wersja>__<opis>.sql`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyEditingAppliedMigrationIsForbidden {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, DLACZEGO NIE WOLNO edytowac JUZ
         * zastosowanej migracji (checksumy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SetDdlAutoToNoneAlongsideFlyway {
        /*
         * 🧪 Zadanie 9:
         * Ustaw `ddl-auto=none` I zweryfikuj, ze Hibernate NIC NIE
         * tworzy (TYLKO Flyway zarzadza schematem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SetDdlAutoToValidateAndConfirmStartup {
        /*
         * 🧪 Zadanie 10:
         * Ustaw `ddl-auto=validate`, zmapuj encje NA tabele Flyway I
         * potwierdz UDANY start aplikacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BreakEntityMappingAndObserveValidateFailure {
        /*
         * 🧪 Zadanie 11:
         * CELOWO dodaj DO encji pole BEZ odpowiadajacej kolumny W
         * tabeli - zaobserwuj BLAD startu Z `ddl-auto=validate`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareValidateVsUpdateVsCreateDrop {
        /*
         * 🧪 Zadanie 12:
         * Porownaj `validate`/`update`/`create-drop` W KONTEKSCIE
         * WSPOLPRACY Z Flyway - KTORA opcja jest BEZPIECZNA, a KTORE
         * NIE (I DLACZEGO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateChecksumMismatch {
        /*
         * 🧪 Zadanie 13:
         * Zastosuj migracje, POTEM zmien JUZ zastosowany plik `.sql` I
         * uruchom aplikacje PONOWNIE - zaobserwuj
         * `FlywayValidateException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseFlywayRepairProgrammatically {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z `_10_dao/Lesson25` - uzyj `Flyway.configure()...
         * load().repair()` DO naprawy checksumy PO Exercise13.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddRepeatableMigration {
        /*
         * 🧪 Zadanie 15:
         * Dodaj migracje POWTARZALNA (`R__opis.sql`, np. widok/procedura) -
         * zweryfikuj, ze uruchamia sie PONOWNIE PO zmianie tresci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SeparateMigrationsPerModule {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj 2 NIEZALEZNE podfoldery migracji (np. `catalog`/
         * `billing`) DLA 2 "modulow" (powiazanie Z
         * `_17_architecture/Lesson17_ModularMonolith`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestMigrationIdempotencyOnRestart {
        /*
         * 🧪 Zadanie 17:
         * Uruchom TEN SAM kontekst DWUKROTNIE (2 osobne
         * `ConfigurableApplicationContext`) NA TEJ SAMEJ bazie -
         * zweryfikuj, ze DRUGIE uruchomienie NIE POWTARZA migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddDataSeedingMigrationVsCommandLineRunner {
        /*
         * 🧪 Zadanie 18:
         * Porownaj wstawianie danych POCZATKOWYCH PRZEZ migracje `.sql`
         * (`INSERT`) Z `CommandLineRunner` (`_21_spring_boot/Lesson07`) -
         * KIEDY ktore podejscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureFlywayBaselineOnMigrate {
        /*
         * 🧪 Zadanie 19:
         * Ustaw `spring.flyway.baseline-on-migrate=true` I wyjasnij,
         * KIEDY jest to POTRZEBNE (baza JUZ ISTNIEJACA PRZED Flyway).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_QueryFlywaySchemaHistoryForFailedMigration {
        /*
         * 🧪 Zadanie 20:
         * Zasymuluj MIGRACJE Z BLEDNYM SQL - zweryfikuj wpis
         * `success=false` W `flyway_schema_history`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignMigrationPlanForColumnRename {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj BEZPIECZNA migracje ZMIENIAJACA nazwe kolumny W
         * PRODUKCYJNEJ bazie (bez przestoju) - 2-etapowa strategia
         * (dodaj nowa, backfill, USUN stara W OSOBNEJ migracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareFlywayVsHibernateDdlAutoInCiCd {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wyjasnij, DLACZEGO `ddl-auto=create-drop`/
         * `update` jest NIEBEZPIECZNE W SRODOWISKU CI/CD Z REALNA baza
         * (powiazanie Z `_17_architecture`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFlywayJavaMigration {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj migracje W JAVIE (`BaseJavaMigration`) DO
         * zlozonej transformacji danych, KTOREJ nie da sie ladnie
         * wyrazic W czystym SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CombineFlywayWithProfiles {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_20_spring_core/Lesson15`/`_21_spring_boot/Lesson06` -
         * skonfiguruj INNY zestaw migracji (np. Z danymi demo) DLA
         * profilu `dev` NIZ `prod`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureMigrationStartupOverhead {
        /*
         * 🧪 Zadanie 25:
         * Zmierz, o ILE dluzej startuje kontekst Z 20 migracjami NIZ Z
         * 2 - oszacuj wplyw NA czas startu aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignRollbackStrategyWithoutFlywayTeams {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: Flyway Community NIE MA automatycznego
         * "undo" - zaprojektuj STRATEGIE cofniecia zlej migracji
         * (nowa migracja "kompensujaca" ZAMIAST fizycznego rollbacku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IntegrateFlywayValidationIntoStartupHealthCheck {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_21_spring_boot/Lesson12_SpringBootActuator` -
         * sprawdz, CO Actuator `/actuator/flyway` (LUB `/actuator/health`)
         * pokazuje O stanie migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareFlywayVsLiquibase {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj Flyway (SQL-first) Z Liquibase
         * (XML/YAML/JSON changelogi) - kompromisy OBU podejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMultiSchemaMigrationStrategy {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj strategie migracji DLA aplikacji Z WIELOMA
         * schematami/bazami (np. per-tenant) - `spring.flyway.schemas`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullMigrationLifecycleDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DEMO: V1 (utworzenie) -> V2 (ewolucja) -> V3 (nowa
         * tabela powiazana FK) -> zapytanie potwierdzajace CALY
         * lancuch migracji W `flyway_schema_history`.
         */
        public static void main(String[] args) { }
    }
}
