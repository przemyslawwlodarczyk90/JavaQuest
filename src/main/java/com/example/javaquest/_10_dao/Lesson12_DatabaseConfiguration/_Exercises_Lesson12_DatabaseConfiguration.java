package com.example.javaquest._10_dao.Lesson12_DatabaseConfiguration;

public class _Exercises_Lesson12_DatabaseConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LoadPropertiesFileFromClasspath {
        /*
         * 🧪 Zadanie 1:
         * Stwórz plik src/main/resources/l12ex01.properties z kluczami db.url,
         * db.username, db.password, db.driver (wzorem lesson12-db.properties).
         * Napisz metodę loadProperties(String) (jak w lekcji) i wczytaj ten plik,
         * wypisując wszystkie 4 wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ConnectUsingLoadedProperties {
        /*
         * 🧪 Zadanie 2:
         * Używając Properties wczytanych w Zadaniu 1, zbuduj Connection przez
         * DriverManager.getConnection(url, username, password). Utwórz tabelę
         * settings_demo i wstaw jeden wiersz, potem odczytaj go i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MissingPropertiesFileThrowsIOException {
        /*
         * 🧪 Zadanie 3:
         * Wywołaj loadProperties("nieistniejacy-plik.properties") i złap
         * IOException (rzucany zgodnie ze wzorcem z lekcji: "Nie znaleziono
         * pliku na classpath"). Wypisz komunikat wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PasswordMaskedInLogs {
        /*
         * 🧪 Zadanie 4:
         * Wczytaj plik .properties z niepustym db.password i wypisz go w konsoli
         * ZAMASKOWANE (jak w lekcji: "***UKRYTE***" jeśli niepuste, "(puste)"
         * jeśli puste). Przetestuj dla obu przypadków (2 osobne pliki
         * .properties albo 2 obiekty Properties).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetPropertyWithDefaultFallback {
        /*
         * 🧪 Zadanie 5:
         * Wczytaj plik .properties BEZ klucza "db.driver" (usuń go z pliku albo
         * zbuduj Properties bez niego). Użyj
         * properties.getProperty("db.driver", "org.h2.Driver") z wartością
         * domyślną i wypisz wynik - pokaż, że brak klucza nie rzuca wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoDifferentPropertiesFilesForTwoEnvironments {
        /*
         * 🧪 Zadanie 6:
         * Stwórz DWA pliki .properties (np. l12ex06-dev.properties i
         * l12ex06-test.properties) z RÓŻNYMI wartościami db.url. Wczytaj OBA i
         * połącz się z KAŻDĄ bazą po kolei, tworząc w każdej inną tabelę -
         * pokaż, że TEN SAM kod łączący się różni się TYLKO nazwą wczytanego
         * pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PropertiesUsedInsideDaoConstructor {
        /*
         * 🧪 Zadanie 7:
         * Napisz UserDao przyjmujący w konstruktorze Properties (wczytane z
         * pliku) i budujący własne Connection na podstawie db.url/username/
         * password przy KAŻDYM wywołaniu metody. Zaimplementuj insert i findAll,
         * przetestuj na 2 użytkownikach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_StoreAndLoadPropertiesProgrammatically {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj obiekt Properties w Javie (setProperty dla db.url, db.username,
         * db.password) i zapisz go do pliku na dysku metodą properties.store(...)
         * w katalogu tymczasowym (Files.createTempFile). Odczytaj go z powrotem
         * (load z InputStream na tej ścieżce) i porównaj wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PropertiesFileWithComments {
        /*
         * 🧪 Zadanie 9:
         * Stwórz plik .properties z komentarzami (linie zaczynające się od "#")
         * opisującymi każdy klucz. Wczytaj go i pokaż, że Properties.load()
         * IGNORUJE komentarze - wypisz same wartości bez komentarzy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ClassLoaderVsFileSystemLoading {
        /*
         * 🧪 Zadanie 10:
         * Porównaj DWA sposoby wczytania tego samego pliku: (a) przez
         * getClassLoader().getResourceAsStream(nazwa) - jak w lekcji, (b) przez
         * bezpośrednią ścieżkę na dysku (FileInputStream do pełnej ścieżki
         * pliku w target/classes). Wypisz w komentarzu, czemu podejście (a) jest
         * bardziej przenośne (działa niezależnie od miejsca uruchomienia).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServiceLayerUnawareOfPropertiesSource {
        /*
         * 🧪 Zadanie 11:
         * Napisz UserService przyjmujący UserDao (skonstruowany z Properties poza
         * Service - patrz Zadanie 7). Service NIE MA importu ani referencji do
         * klasy Properties - operuje wyłącznie na DAO. Zademonstruj rejestrację 2
         * użytkowników przez Service.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultiplePropertiesForMultipleDataSources {
        /*
         * 🧪 Zadanie 12:
         * Wczytaj DWA różne pliki .properties opisujące DWIE różne "bazy" H2
         * in-memory (np. baza główna i baza archiwalna). Zbuduj DWA Connection
         * (jeden dla każdej konfiguracji) i wykonaj insert do KAŻDEJ - pokaż,
         * że dane w obu bazach są niezależne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ValidatePropertiesBeforeConnecting {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę validateDbProperties(Properties) sprawdzającą, że
         * db.url, db.username i db.driver są NIEPUSTE (db.password MOŻE być
         * pusty) - rzuca IllegalStateException z listą brakujących kluczy, jeśli
         * czegoś nie ma. Przetestuj na kompletnym i niekompletnym pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PropertiesOverriddenBySystemProperty {
        /*
         * 🧪 Zadanie 14:
         * Wczytaj db.url z pliku .properties, ale jeśli ustawiona jest właściwość
         * JVM "-Ddb.url=..." (System.getProperty("db.url")), użyj JEJ zamiast
         * wartości z pliku (fallback: plik, override: system property). Zademonstruj
         * scenariusz z i bez ustawionej właściwości (System.setProperty w
         * kodzie testowym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConfigurationObjectWrappingProperties {
        /*
         * 🧪 Zadanie 15:
         * Napisz record DbConfig(String url, String username, String password,
         * String driver) z fabryką statyczną DbConfig.fromProperties(Properties)
         * budującą go z wczytanego pliku. Użyj DbConfig (silnie typowanego)
         * ZAMIAST przekazywania surowego Properties między metodami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PropertiesForConnectionPoolSettings {
        /*
         * 🧪 Zadanie 16:
         * Dodaj do pliku .properties dodatkowe klucze pool.maxSize i
         * pool.connectionTimeoutMs. Wczytaj je jako int (Integer.parseInt na
         * wartości z Properties) i wypisz - to przygotowanie do konfiguracji
         * puli połączeń z Lesson14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DetectMisconfiguredUrlEarly {
        /*
         * 🧪 Zadanie 17:
         * Wczytaj plik .properties z URL-em zawierającym literówkę (np.
         * "jdbc:h2:mem:l12ex17;DB_CLOSE_DELAYY=-1"). Złap SQLException przy
         * próbie połączenia i wypisz czytelny komunikat wskazujący, że problem
         * może leżeć w konfiguracji, a nie w kodzie aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SwapPropertiesFileWithoutRecompiling {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę loadPropertiesByName(String environment) budującą nazwę
         * pliku dynamicznie (np. "l12ex18-" + environment + ".properties").
         * Przygotuj DWA pliki (dev/test) i zademonstruj wybór jednego z nich
         * przez PARAMETR programu (String[] args), bez zmiany skompilowanego kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConnectionFactoryUsingPropertiesInternally {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj ConnectionFactory z Lesson11_ConnectionFactory (albo napisz
         * podobną), by URL/USER/PASSWORD wczytywała RAZ z pliku .properties (w
         * statycznym bloku inicjalizującym) ZAMIAST mieć je jako stałe wpisane w
         * kodzie. Zademonstruj getConnection() korzystające z tak wczytanej
         * konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ComparePropertiesVsHardcodedConstants {
        /*
         * 🧪 Zadanie 20:
         * Napisz DWIE wersje tej samej operacji (connect + create table + insert):
         * jedną z URL zaszytym jako stała String w kodzie (jak w Lesson11
         * "Before"), drugą z URL wczytanym z pliku .properties. W komentarzu
         * wypisz, co trzeba by zmienić w KAŻDEJ wersji, żeby zmienić bazę na
         * produkcyjną bez przebudowy aplikacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullConfigDrivenDaoFactory {
        /*
         * 🧪 Zadanie 21:
         * Napisz DaoFactory.createUserDao() wczytujące plik .properties (RAZ, przy
         * pierwszym wywołaniu - statyczne pole cache) i budujące UserJdbcDao z
         * odpowiednim Connection. Wywołaj createUserDao() wielokrotnie i sprawdź,
         * że plik .properties jest wczytywany TYLKO RAZ (log przy wczytaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PropertiesDrivenMultiTenantSetup {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj konfigurację "multi-tenant": jeden plik .properties z listą
         * nazw najemców (tenant.names=firmaA,firmaB) i wzorcem URL zawierającym
         * placeholder (tenant.url.pattern=jdbc:h2:mem:l12ex22_%s;DB_CLOSE_DELAY=-1).
         * Zbuduj Connection dla KAŻDEGO najemcy (String.format z nazwą) i utwórz w
         * każdej bazie tabelę - pokaż, że dane są izolowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConfigurationChangeAtRuntimeWithoutRestart {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj mechanizm "przeładowania" konfiguracji: metoda
         * reloadProperties() ponownie wczytuje plik .properties do pola
         * statycznego. Zmodyfikuj plik NA DYSKU (Files.writeString) między dwoma
         * wywołaniami reloadProperties() i pokaż, że druga wartość różni się od
         * pierwszej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EncryptedPasswordInPropertiesSimulation {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj "zaszyfrowane" hasło w pliku .properties (proste kodowanie,
         * np. Base64.getEncoder().encodeToString) - wartość db.password w pliku
         * jest zakodowana. Napisz decodePassword(String encoded) odkodowujące ją
         * PRZED użyciem do połączenia. Zademonstruj, że hasło w pliku NIE jest
         * czytelne "na pierwszy rzut oka", a program wciąż działa poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PropertiesValidationWithDetailedReport {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj validateDbProperties (Zadanie 13) o SZCZEGÓŁOWY raport:
         * zamiast rzucać wyjątek przy pierwszym błędzie, zbierz WSZYSTKIE
         * problemy (brakujące klucze, pusty url, url bez prefiksu "jdbc:") do
         * List<String> i wypisz raport z KOMPLETNĄ listą błędów na raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LayeredConfigurationPriority {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj priorytet konfiguracji (od najwyższego): system property
         * (-D) > zmienna środowiskowa > plik .properties > wartość domyślna
         * wpisana w kodzie. Napisz metodę resolveDbUrl() sprawdzającą wszystkie
         * cztery źródła po kolei i zwracającą pierwsze niepuste. Przetestuj
         * scenariusze z różną kombinacją ustawionych/nieustawionych źródeł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PropertiesFileVersioningCompatibilityCheck {
        /*
         * 🧪 Zadanie 27:
         * Dodaj do pliku .properties klucz "config.version=1". Napisz
         * checkConfigVersion(Properties, int expectedVersion) - jeśli wersja się
         * nie zgadza (albo brakuje klucza), wypisz ostrzeżenie "Plik konfiguracyjny
         * moze byc nieaktualny" ZAMIAST przerywać program. Przetestuj dla
         * zgodnej i niezgodnej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConfigDrivenConnectionUsedByThreeDaos {
        /*
         * 🧪 Zadanie 28:
         * Wczytaj JEDEN plik .properties i zbuduj z niego JEDNO Connection.
         * Przekaż to samo Connection do trzech różnych DAO (User, Product,
         * Order) i wykonaj po jednej operacji na każdym - pokaż, że konfiguracja
         * jest wczytana RAZ, a używana przez wiele DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PropertiesBasedFeatureToggle {
        /*
         * 🧪 Zadanie 29:
         * Dodaj do pliku .properties klucz "feature.auditLog.enabled=true".
         * Napisz UserDao.insert(), która WARUNKOWO (na podstawie tej flagi z
         * konfiguracji) dodatkowo wstawia wpis do tabeli audit_log. Zademonstruj
         * zachowanie z flagą true i false (dwa różne pliki .properties albo dwie
         * wartości Properties).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullConfigDrivenMiniApplication {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację CAŁKOWICIE kierowaną plikiem .properties: URL/user/
         * password bazy, nazwa środowiska (dev/prod - wpływa na to, czy tworzone
         * są dodatkowe dane testowe), flaga feature.auditLog.enabled (jak w
         * Zadaniu 29). Zasymuluj uruchomienie aplikacji dla DWÓCH różnych plików
         * konfiguracyjnych i wypisz raport pokazujący, jak zachowanie programu
         * się różni w zależności od konfiguracji, bez zmiany ani jednej linijki
         * kodu Javy między uruchomieniami.
         */
        public static void main(String[] args) { }
    }
}
