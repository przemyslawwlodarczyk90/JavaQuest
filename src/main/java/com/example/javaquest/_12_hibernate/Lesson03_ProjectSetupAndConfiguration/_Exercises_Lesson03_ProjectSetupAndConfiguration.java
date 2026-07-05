package com.example.javaquest._12_hibernate.Lesson03_ProjectSetupAndConfiguration;

public class _Exercises_Lesson03_ProjectSetupAndConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MinimalConfigurationProperties {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj SessionFactory z ABSOLUTNYM minimum wlasciwosci (driver_class, url,
         * username, password, hbm2ddl.auto) - BEZ show_sql/format_sql. Zapisz jeden
         * obiekt i potwierdz, ze dziala mimo braku logowania SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ShowSqlVsFormatSql {
        /*
         * 🧪 Zadanie 2:
         * Uruchom TEN SAM zapis dwa razy: raz z show_sql=true, format_sql=false, raz
         * z obydwoma true. Zapisz w komentarzu roznice w czytelnosci wypisanego SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_HbmDdlNoneRequiresExistingSchema {
        /*
         * 🧪 Zadanie 3:
         * Ustaw hbm2ddl.auto=none i sprobuj zapisac obiekt BEZ recznego utworzenia
         * tabeli. Zapisz w komentarzu blad SQL (tabela nie istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_HbmDdlValidateAfterManualCreate {
        /*
         * 🧪 Zadanie 4:
         * Recznie (czystym JDBC Statement) utworz tabele pasujaca do Twojej encji,
         * potem zbuduj SessionFactory z hbm2ddl.auto=validate. Zapisz w komentarzu,
         * ze Hibernate NIE zmienil schematu, tylko go zweryfikowal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HbmDdlUpdateAddsColumn {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj SessionFactory z hbm2ddl.auto=update dla encji z 2 polami, zapisz
         * obiekt. Dodaj TRZECIE pole do encji i uruchom ponownie z update - zapisz
         * w komentarzu, ze nowa kolumna zostala dodana bez utraty danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateDropCleansUpAfterClose {
        /*
         * 🧪 Zadanie 6:
         * Uzyj hbm2ddl.auto=create-drop, zapisz obiekt, zamknij SessionFactory.
         * Otworz NOWA SessionFactory na TA SAMA baze i sprawdz (recznym SQL), czy
         * tabela wciaz istnieje - zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ProgrammaticVsXmlConfigComparisonPrint {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wypisz TEN SAM zestaw ustawien (url, driver, hbm2ddl.auto)
         * zapisany jako: (a) Configuration#setProperty (jak w kursie), (b) fragment
         * hibernate.cfg.xml (jako String do wyswietlenia) - porownaj obie formy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_H2FileBasedInsteadOfMemory {
        /*
         * 🧪 Zadanie 8:
         * Zmien URL z "jdbc:h2:mem:..." na "jdbc:h2:file:./data/l03ex08" (baza na
         * dysku, nie w pamieci). Zapisz obiekt, zamknij SessionFactory, otworz NOWA
         * wskazujaca na TEN SAM plik i odczytaj dane - potwierdz, ze przetrwaly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MultipleAddAnnotatedClassCalls {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj Configuration wywolujac addAnnotatedClass() DLA KAZDEJ z 4 roznych,
         * prostych encji. Zapisz po jednym obiekcie kazdej i policz w komentarzu,
         * ile tabel powstalo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConnectionCredentialsFromEnv {
        /*
         * 🧪 Zadanie 10:
         * Wzorem _10_dao/Lesson13_EnvironmentVariables: odczytaj adres bazy H2 ze
         * zmiennej srodowiskowej (System.getenv), z fallbackiem na wartosc domyslna,
         * jesli zmienna nie jest ustawiona.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_HbmDdlUpdateDoesNotDropColumns {
        /*
         * 🧪 Zadanie 11:
         * Zapisz obiekt z encja o 3 polach (update). USUN jedno pole z klasy encji i
         * uruchom ponownie z hbm2ddl.auto=update. Sprawdz recznym SQL, czy stara
         * kolumna WCIAZ istnieje w tabeli (update nigdy nie usuwa kolumn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleDataSourcesOneApp {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj DWIE SessionFactory w JEDNYM programie, kazda wskazujaca inna baze
         * H2, z ROZNYMI encjami. Wykonaj operacje na obu w tym samym main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigurationBuilderPattern {
        /*
         * 🧪 Zadanie 13:
         * Napisz WLASNA klase "HibernateConfigBuilder" z metodami fluent (np.
         * .withUrl(...).withHbmDdl(...).build()) opakowujaca budowe Configuration -
         * uzyj jej zamiast bezposredniego wolania setProperty w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LoggingSqlToFile {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj przekierowanie wygenerowanego SQL (show_sql) do pliku zamiast
         * konsoli (np. przez PrintStream na plik, albo konfiguracje loggera). Zapisz
         * kilka operacji i zweryfikuj zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateFailsOnMismatch {
        /*
         * 🧪 Zadanie 15:
         * Recznie utworz tabele z INNYMI typami kolumn niz oczekuje Twoja encja (np.
         * VARCHAR zamiast INT dla liczby). Zbuduj SessionFactory z hbm2ddl.auto=validate
         * i zapisz w komentarzu blad walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SwitchDialectExplicitly {
        /*
         * 🧪 Zadanie 16:
         * Dodaj JAWNIE wlasciwosc hibernate.dialect=org.hibernate.dialect.H2Dialect
         * (mimo ze Hibernate 6 zwykle wykrywa go automatycznie) - zapisz w
         * komentarzu, czy zachowanie sie zmienilo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ProfileBasedConfigSwitch {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode buildSessionFactory(String profile) zwracajaca RUZNA
         * konfiguracje (np. inna nazwe bazy H2) w zaleznosci od przekazanego
         * "dev"/"test" - wzorzec inspirowany profilami Maven z _11_buildtools/Lesson14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConnectionPoolPropertiesExplained {
        /*
         * 🧪 Zadanie 18:
         * Skonfiguruj hibernate.connection.pool_size i hibernate.connection.autocommit
         * jawnie. Wypisz w komentarzu, co kazda z tych dwoch wlasciwosci kontroluje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MigrationFromNoneToUpdateScenario {
        /*
         * 🧪 Zadanie 19:
         * Zasymuluj scenariusz "istniejaca baza produkcyjna": recznie utworz tabele,
         * zapisz kilka wierszy czystym SQL, potem podepnij Hibernate z
         * hbm2ddl.auto=none i odczytaj te dane przez session.find()/HQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareThreeDdlModes {
        /*
         * 🧪 Zadanie 20:
         * Uruchom TEN SAM kod (ta sama encja, ten sam zapis) trzykrotnie, za kazdym
         * razem z innym hbm2ddl.auto (create, update, validate na juz istniejacej
         * bazie) - zapisz w komentarzu obserwowane roznice w logu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullConfigWithHikariLikeSettings {
        /*
         * 🧪 Zadanie 21:
         * Skonfiguruj SessionFactory z dodatkowymi wlasciwosciami puli polaczen
         * (hibernate.connection.pool_size, hibernate.connection.autocommit=false) i
         * porownaj w komentarzu z konfiguracja HikariCP z _10_dao/Lesson14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiModuleConfigurationSharing {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj (kod + komentarz) sposob WSPOLDZIELENIA JEDNEJ konfiguracji
         * (adres bazy, poswiadczenia) miedzy WIELOMA klasami buildSessionFactory w
         * roznych pakietach - np. przez wspolna klase Constants albo plik properties.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DetectDialectAutomatically {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj SessionFactory BEZ jawnego hibernate.dialect i wypisz na konsoli
         * (przez sessionFactory.unwrap lub log startowy), jaki dialekt Hibernate
         * wykryl automatycznie dla H2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FailFastOnMissingDriver {
        /*
         * 🧪 Zadanie 24:
         * Podaj bledna nazwe klasy sterownika JDBC i zaobserwuj, na KTORYM dokladnie
         * etapie (Configuration, ServiceRegistry, czy buildSessionFactory) Hibernate
         * zglasza blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConfigurationForTestVsProdComparison {
        /*
         * 🧪 Zadanie 25:
         * Napisz DWIE metody buildSessionFactory - jedna "testowa" (H2 in-memory,
         * create-drop) i jedna "produkcyjna" (H2 file-based, validate) - wypisz w
         * komentarzu, ktore ustawienia ROZNIA sie miedzy nimi i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RuntimePropertyOverride {
        /*
         * 🧪 Zadanie 26:
         * Napisz kod pozwalajacy NADPISAC pojedyncza wlasciwosc konfiguracji (np. URL
         * bazy) przez argument programu (args[0]), z fallbackiem na wartosc domyslna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConfigurationImmutabilityCheck {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj SessionFactory, a POTEM sprobuj zmienic properties na oryginalnym
         * obiekcie Configuration i zbudowac DRUGA SessionFactory z tego samego
         * obiektu. Zapisz w komentarzu, czy zmiana miala wplyw na juz istniejaca
         * pierwsza SessionFactory (powinna byc niezalezna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SchemaExportToFile {
        /*
         * 🧪 Zadanie 28:
         * Uzyj wlasciwosci zapisujacej wygenerowany DDL do pliku
         * (jakarta.persistence.schema-generation.scripts.action=create +
         * scripts.create-target) zamiast bezposrednio do bazy - otworz wygenerowany
         * plik i wypisz jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullMigrationPlanFromDaoToHibernateConfig {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz plan (min. 5 krokow, w komentarzu) migracji
         * konfiguracji polaczenia z bazy z _10_dao/Lesson12_DatabaseConfiguration
         * (properties + DataSource) na konfiguracje Hibernate z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullConfigurationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: napisz KOMPLETNA klase konfiguracyjna
         * "AppSessionFactoryProvider" z: leniwa inicjalizacja, obsluga profili
         * (dev/test/prod z Zadania 17), shutdown hookiem i metoda healthCheck
         * (proste zapytanie weryfikujace polaczenie) - polacz wszystkie wczesniejsze
         * wzorce w jedna, spojna klase.
         */
        public static void main(String[] args) { }
    }
}
