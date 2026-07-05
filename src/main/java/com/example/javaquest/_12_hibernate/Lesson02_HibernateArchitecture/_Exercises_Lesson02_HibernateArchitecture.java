package com.example.javaquest._12_hibernate.Lesson02_HibernateArchitecture;

public class _Exercises_Lesson02_HibernateArchitecture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BuildSessionFactoryStepByStep {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj SessionFactory na H2 ("jdbc:h2:mem:l02ex01;DB_CLOSE_DELAY=-1") w
         * KROKACH z osobnymi println po kazdym etapie (Configuration ->
         * ServiceRegistry -> SessionFactory), jak w lekcji, dla WLASNEJ encji Task.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReuseSessionFactoryTwice {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj JEDNA SessionFactory i otworz DWIE kolejne, osobne Session (jedna po
         * drugiej, zamykane po uzyciu) - w kazdej zapisz jeden obiekt. Zapisz w
         * komentarzu, ze to TA SAMA SessionFactory obslugujaca wiele Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SessionNotThreadSafeComment {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wypisz na konsoli wyjasnienie (2-3 zdania), dlaczego Session
         * NIE jest thread-safe, a SessionFactory JEST - z odniesieniem do
         * pierwszego levelu cache (Lesson23).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MeasureSessionFactoryBuildTime {
        /*
         * 🧪 Zadanie 4:
         * Zmierz (System.nanoTime()) czas budowy SessionFactory z 1 encja vs czas
         * otwarcia Session z tej SessionFactory. Zapisz oba czasy w komentarzu -
         * porownaj, ktora operacja jest znaczaco drozsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryWithResourcesSessionFactory {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj SessionFactory i uzyj jej w try-with-resources (SessionFactory
         * implementuje AutoCloseable). Zapisz jeden obiekt wewnatrz bloku try.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleAnnotatedClasses {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj Configuration z TRZEMA roznymi, niepowiazanymi encjami (addAnnotatedClass
         * trzykrotnie). Zapisz po jednym obiekcie kazdej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplicitServiceRegistryBuilder {
        /*
         * 🧪 Zadanie 7:
         * Napisz kod budujacy ServiceRegistry OSOBNO (StandardServiceRegistryBuilder),
         * a potem PRZEKAZ go do configuration.buildSessionFactory(registry) -
         * dokladnie jak w lekcji, ale dla WLASNEJ encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CloseSessionFactoryTwiceError {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj SessionFactory, zamknij ja (close()), a potem sprobuj OTWORZYC z niej
         * nowa Session. Zapisz w komentarzu blad, jaki Hibernate zwraca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ArchitectureDiagramPrint {
        /*
         * 🧪 Zadanie 9:
         * Bez bazy danych: wypisz (jako println, 5 linii) diagram przeplywu z lekcji:
         * Configuration -> ServiceRegistry -> SessionFactory -> Session -> Transaction.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BankAnalogyPrint {
        /*
         * 🧪 Zadanie 10:
         * Bez bazy danych: wypisz WLASNYMI slowami (rozwiniete, min. 3 zdania)
         * analogie "SessionFactory = fabryka polaczen z bankiem, Session = pojedyncza
         * wizyta" z lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoSessionFactoriesDifferentDbs {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj DWIE ROZNE SessionFactory wskazujace na DWIE rozne bazy H2
         * ("l02ex11a" i "l02ex11b"), z ta sama encja. Zapisz rozne dane w kazdej i
         * potwierdz, ze sa od siebie niezalezne (odczyt z jednej nie widzi danych z drugiej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SessionFactoryAsSingletonPattern {
        /*
         * 🧪 Zadanie 12:
         * Napisz klase-holder (statyczne pole + metoda getInstance()) trzymajaca
         * JEDNA, globalna SessionFactory (wzorzec singleton) - i uzyj jej z DWOCH
         * roznych metod w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConcurrentSessionsFromOneFactory {
        /*
         * 🧪 Zadanie 13:
         * Uruchom DWA prawdziwe watki (jak w _05_multithreading), kazdy otwierajacy
         * WLASNA Session z TEJ SAMEJ SessionFactory i zapisujacy jeden obiekt.
         * Zweryfikuj, ze oba zapisy sie powiodly (dowod thread-safety SessionFactory).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConfigurationPropertiesInspection {
        /*
         * 🧪 Zadanie 14:
         * Po zbudowaniu Configuration, wypisz na konsoli WSZYSTKIE ustawione
         * properties (configuration.getProperties()) - iterujac po Map.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SessionFactoryStatisticsEnabled {
        /*
         * 🧪 Zadanie 15:
         * Wlacz hibernate.generate_statistics=true w Configuration. Po kilku
         * operacjach persist/find wypisz sessionFactory.getStatistics() -
         * konkretnie liczbe wykonanych zapytan (getQueryExecutionCount()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LazyVsEagerSessionFactoryInit {
        /*
         * 🧪 Zadanie 16:
         * Bez bazy danych: wypisz wyjasnienie, dlaczego SessionFactory jest budowana
         * ZAWSZE "chetnie" (eager) - w odroznieniu od LAZY loadingu powiazan
         * (Lesson15), ktory jest tematem zupelnie innym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SessionFactoryPerModuleDesign {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj (jako komentarz) strukture aplikacji z WIELOMA modulami
         * (np. "orders", "users"), z KTOREJ jednej wspolnej SessionFactory (a nie po
         * jednej na modul) - wyjasnij dlaczego jedna SessionFactory na cala aplikacje
         * jest typowym wyborem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandlingSessionFactoryBuildFailure {
        /*
         * 🧪 Zadanie 18:
         * Celowo podaj zla nazwe klasy sterownika JDBC
         * (hibernate.connection.driver_class = "zla.klasa.Sterownika"). Opakuj budowe
         * SessionFactory w try-catch i wypisz czytelny komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultipleTransactionsOneSession {
        /*
         * 🧪 Zadanie 19:
         * W JEDNEJ Session wykonaj DWIE kolejne, osobne transakcje (begin/commit,
         * potem znowu begin/commit) - kazda zapisujaca inny obiekt. Zapisz w
         * komentarzu, ze Session moze obslugiwac wiele transakcji sekwencyjnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RollbackWithoutCommit {
        /*
         * 🧪 Zadanie 20:
         * Rozpocznij transakcje, zapisz obiekt (persist), ale zamiast commit()
         * wywolaj rollback(). Sprawdz w NOWEJ Session, ze obiekt NIE zostal
         * zapisany w bazie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomServiceRegistryBuilderSettings {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj ServiceRegistry z DODATKOWA wlasciwoscia niestandardowa (np.
         * hibernate.jdbc.batch_size=20) przekazana przez applySettings - zweryfikuj w
         * logu SQL, ze batchowanie dziala przy zapisie wielu obiektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GracefulShutdownPattern {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj wzorzec "graceful shutdown" - Runtime.getRuntime().addShutdownHook
         * zamykajacy SessionFactory przy zamknieciu aplikacji (np. Ctrl+C). Zapisz w
         * komentarzu, po co taki hook w realnej aplikacji dlugozyjacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SessionFactoryHealthCheck {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode "healthCheck(SessionFactory)" wykonujaca proste zapytanie
         * (np. "select 1") i zwracajaca boolean - wzorzec uzywany w realnych
         * aplikacjach do sprawdzania, czy polaczenie z baza wciaz dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiTenantSketch {
        /*
         * 🧪 Zadanie 24:
         * Bez pelnej implementacji: wypisz szkic (jako komentarz, min. 4 zdania)
         * koncepcji multi-tenancy w Hibernate - osobna SessionFactory (albo osobny
         * schemat) na kazdego "najemce" (tenant) systemu SaaS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SessionFactorySerializableCheck {
        /*
         * 🧪 Zadanie 25:
         * Sprawdz (Class.getInterfaces() lub dokumentacja), czy SessionFactory
         * implementuje java.io.Serializable - i wypisz w komentarzu, do czego
         * mogloby sie to przydac (np. przechowywanie referencji w JNDI).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConnectionPoolSizeExperiment {
        /*
         * 🧪 Zadanie 26:
         * Skonfiguruj hibernate.connection.pool_size na rozne wartosci (1 vs 10) i
         * uruchom 5 rownoleglych watkow otwierajacych Session naraz - zapisz w
         * komentarzu obserwacje (np. czy przy pool_size=1 watki musialy czekac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CustomNamingStrategySketch {
        /*
         * 🧪 Zadanie 27:
         * Skonfiguruj hibernate.physical_naming_strategy na wlasna klase implementujaca
         * PhysicalNamingStrategy, zamieniajaca nazwy pol camelCase na snake_case w
         * generowanym SQL - zweryfikuj w show_sql.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullBootstrapWithoutHelperMethod {
        /*
         * 🧪 Zadanie 28:
         * Napisz caly proces bootstrapowania SessionFactory BEZ wydzielonej metody
         * pomocniczej (jak buildSessionFactory() w lekcji) - wprost w main(), z
         * komentarzem przy kazdym kroku, dla WLASNEGO zestawu 2 powiazanych encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareArchitectureWithJdbcConnectionPool {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz porownanie (min. 4 punkty) architektury Hibernate
         * (SessionFactory/Session) z architektura polaczen z _10_dao (DataSource/
         * Connection, Lesson14/15) - co jest odpowiednikiem czego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullArchitectureCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj aplikacje z WLASNA klasa "HibernateBootstrap"
         * (statyczna metoda getSessionFactory() z leniwa inicjalizacja + shutdown
         * hook), uzyj jej z 3 roznych miejsc w kodzie do wykonania 3 roznych operacji
         * (zapis, odczyt, usuniecie) na jednej wspolnej encji.
         */
        public static void main(String[] args) { }
    }
}
