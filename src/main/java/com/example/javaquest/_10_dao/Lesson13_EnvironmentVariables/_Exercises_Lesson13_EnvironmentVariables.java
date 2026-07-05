package com.example.javaquest._10_dao.Lesson13_EnvironmentVariables;

public class _Exercises_Lesson13_EnvironmentVariables {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ReadKnownEnvironmentVariable {
        /*
         * 🧪 Zadanie 1:
         * Odczytaj zmienną środowiskową "OS" (albo "PATH") przez System.getenv
         * ("OS") i wypisz jej wartość. Jeśli jest null, wypisz komunikat "zmienna
         * nie ustawiona w tym systemie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadNonExistentVariableReturnsNull {
        /*
         * 🧪 Zadanie 2:
         * Odczytaj System.getenv("ZMIENNA_KTOREJ_NA_PEWNO_NIE_MA_W_SYSTEMIE") i
         * sprawdź, że wynik to null (nie wyjątek). Wypisz komunikat potwierdzający.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GetOrDefaultFallbackPattern {
        /*
         * 🧪 Zadanie 3:
         * Użyj System.getenv().getOrDefault("L13EX03_DB_PASSWORD",
         * "domyslne_haslo_dev") do odczytu hasła z fallbackiem. Wypisz, czy
         * zmienna była ustawiona (containsKey) i jaka wartość została użyta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ConnectToH2UsingFallbackPassword {
        /*
         * 🧪 Zadanie 4:
         * Na bazie "jdbc:h2:mem:l13ex04;DB_CLOSE_DELAY=-1" połącz się przy
         * użyciu hasła z fallbacku (Zadanie 3) jako hasła użytkownika "sa".
         * Utwórz tabelę env_demo i wstaw jeden wiersz, potem odczytaj i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SystemGetenvVsGetPropertyComparison {
        /*
         * 🧪 Zadanie 5:
         * Odczytaj TĘ SAMĄ nazwę klucza (np. "user.name") przez
         * System.getProperty("user.name") (właściwość JVM) oraz System.getenv
         * ("USER_NAME") (zmienna środowiskowa, prawdopodobnie null w Windows).
         * Wypisz OBA wyniki i skomentuj różnicę w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListAllEnvironmentVariableNames {
        /*
         * 🧪 Zadanie 6:
         * Odczytaj System.getenv() jako Map<String, String> i wypisz TYLKO
         * NAZWY (keySet(), posortowane) wszystkich zmiennych środowiskowych
         * ustawionych w systemie, bez ich wartości (dla bezpieczeństwa - część
         * zmiennych może być wrażliwa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ContainsKeyBeforeReading {
        /*
         * 🧪 Zadanie 7:
         * Sprawdź System.getenv().containsKey("JAVA_HOME") PRZED próbą odczytu
         * jej wartości - wypisz "ustawiona"/"nieustawiona", a następnie
         * warunkowo wypisz samą wartość TYLKO jeśli jest ustawiona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FilterSpecificVariablesFromEnvMap {
        /*
         * 🧪 Zadanie 8:
         * Analogicznie do przykładu z lekcji, przefiltruj System.getenv() do
         * zmiennych, których nazwa zaczyna się od "JAVA" (Stream.filter +
         * startsWith) i wypisz wszystkie znalezione pary klucz-wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConnectionUsesEnvUrlWithFallback {
        /*
         * 🧪 Zadanie 9:
         * Odczytaj System.getenv().getOrDefault("L13EX09_DB_URL",
         * "jdbc:h2:mem:l13ex09;DB_CLOSE_DELAY=-1") jako adres bazy (fallback dla
         * developera). Połącz się i utwórz prostą tabelę - zademonstruj, że
         * program DZIAŁA "od razu po sklonowaniu repozytorium" nawet bez
         * ustawionej zmiennej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PrintEnvironmentSummaryReport {
        /*
         * 🧪 Zadanie 10:
         * Wypisz krótki raport: liczba wszystkich zmiennych środowiskowych w
         * systemie (System.getenv().size()) oraz wartości (jeśli ustawione) dla
         * "NUMBER_OF_PROCESSORS" i "OS" - dokładnie jak w przykładzie z lekcji,
         * ale z dodanym licznikiem wszystkich zmiennych.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DaoConstructorReadsEnvForConnection {
        /*
         * 🧪 Zadanie 11:
         * Napisz UserDao, którego konstruktor odczytuje URL/hasło ze zmiennych
         * środowiskowych z fallbackiem (analogicznie do ConnectionFactory z
         * Lesson11, ale zamiast stałych - System.getenv().getOrDefault). Zapisz
         * i odczytaj 2 użytkowników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DifferentBehaviorLocalVsProdSimulated {
        /*
         * 🧪 Zadanie 12:
         * Zasymuluj DWA "środowiska": ustaw fikcyjną zmienną
         * L13EX12_ENVIRONMENT (przez System.getenv - w praktyce nieustawianą w
         * teście, więc użyj fallbacku "LOCAL") i napisz logikę: jeśli
         * "LOCAL" -> użyj lokalnej bazy H2 z prostym hasłem, jeśli "PROD" -> (w
         * komentarzu, bez realnego połączenia) opisz, że hasło musiałoby
         * pochodzić z faktycznej zmiennej środowiskowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RequireEnvVariableOrFailFast {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę requireEnv(String name) rzucającą IllegalStateException
         * z czytelnym komunikatem, jeśli zmienna NIE jest ustawiona (bez
         * fallbacku - dla sekretów, które MUSZĄ być podane na produkcji).
         * Przetestuj dla zmiennej, której na pewno nie ma w systemie, i złap
         * wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DaoBehaviorDiffersByFeatureFlagEnvVariable {
        /*
         * 🧪 Zadanie 14:
         * Odczytaj zmienną "L13EX14_STRICT_MODE" z fallbackiem "false"
         * (String, potem Boolean.parseBoolean). Napisz UserDao.insert(email,
         * name) walidującą e-mail TYLKO gdy strictMode == true (bez walidacji w
         * przeciwnym razie). Zademonstruj OBA warianty (przez zmianę wartości
         * fallbacku w kodzie testowym, bo w teście nie ustawiamy realnej
         * zmiennej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleFallbacksForDifferentSecrets {
        /*
         * 🧪 Zadanie 15:
         * Odczytaj TRZY różne "sekrety" ze zmiennych środowiskowych z fallbackami:
         * DB_PASSWORD (fallback "dev123"), API_KEY (fallback "dev-key"),
         * ENCRYPTION_SALT (fallback "dev-salt"). Wypisz raport - dla każdego,
         * czy pochodzi ze zmiennej środowiskowej, czy z fallbacku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_EnvVariableDrivenConnectionPoolSizeSimulated {
        /*
         * 🧪 Zadanie 16:
         * Odczytaj "L13EX16_POOL_SIZE" z fallbackiem "5" i sparsuj do int
         * (Integer.parseInt). Wypisz wynik jako "konfiguracja rozmiaru puli" -
         * przygotowanie do Lesson14_ConnectionPool, gdzie takie ustawienie
         * będzie realnie użyte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InvalidEnvValueHandledGracefully {
        /*
         * 🧪 Zadanie 17:
         * Zasymuluj sytuację, w której fallback (albo "zmienna" symulowana
         * lokalną zmienną String) ma NIEPOPRAWNY format liczby (np. "abc" dla
         * portu). Złap NumberFormatException przy Integer.parseInt i użyj
         * WARTOŚCI DOMYŚLNEJ zamiast pozwolić programowi wyłożyć się z wyjątkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EnvVariablesUsedInServiceLayerIndirectly {
        /*
         * 🧪 Zadanie 18:
         * Napisz UserService korzystający z UserDao (którego konstruktor
         * odczytuje zmienne środowiskowe - Zadanie 11). Service NIE MA importu
         * System.getenv ani żadnej wiedzy o zmiennych środowiskowych -
         * konfiguracja jest CAŁKOWICIE ukryta w warstwie DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DocumentRequiredEnvVariablesInCode {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę printRequiredEnvVariables() wypisującą listę WSZYSTKICH
         * zmiennych środowiskowych, których aplikacja "oczekuje" (nazwa +
         * czy jest ustawiona + czy ma fallback) - przydatne jako "checklist" dla
         * administratora wdrażającego aplikację na produkcję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareConfigSourcesPriorityDemo {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj resolveDbPassword() sprawdzającą po kolei: System.getenv
         * ("L13EX20_DB_PASSWORD"), potem System.getProperty("db.password"),
         * potem wartość domyślną "fallback-haslo" - zwraca PIERWSZĄ niepustą.
         * Zademonstruj scenariusz z ustawioną właściwością JVM (System.setProperty
         * w kodzie testowym) i bez zmiennej środowiskowej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullConnectionFactoryDrivenByEnv {
        /*
         * 🧪 Zadanie 21:
         * Napisz ConnectionFactory analogiczną do Lesson11, ale URL/USER/
         * PASSWORD odczytane RAZ ze zmiennych środowiskowych z fallbackiem (w
         * statycznym bloku inicjalizującym). Zademonstruj getConnection() użyte
         * przez 2 różne DAO, obie korzystające z tej samej, "środowiskowej"
         * konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SecretsNeverLoggedInPlainText {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę logConfigSafely(String url, String password) wypisującą
         * URL w PEŁNI, ale hasło ZAWSZE zamaskowane (np. "***" niezależnie od
         * długości, nigdy prawdziwa wartość - w odróżnieniu od lekcji, gdzie
         * puste hasło i "UKRYTE" hasło były rozróżniane). Zademonstruj na 2
         * przykładach haseł o różnej długości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_EnvironmentSpecificBehaviorWithThreeTiers {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj logikę trzypoziomową na podstawie fallbackowanej zmiennej
         * "L13EX23_ENV" (LOCAL/STAGING/PROD): LOCAL - baza H2 w pamięci bez
         * hasła, STAGING/PROD - w komentarzu (bez realnego połączenia) opisz
         * różnice w konfiguracji (np. wymagane SSL, inne limity puli). Zademonstruj
         * wybór ścieżki kodu dla wartości LOCAL (bo to jedyna, którą można
         * bezpiecznie przetestować bez realnej infrastruktury).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConfigValidationBeforeApplicationStart {
        /*
         * 🧪 Zadanie 24:
         * Napisz validateEnvironmentOrExit() sprawdzającą WSZYSTKIE "wymagane"
         * zmienne (lista Stringów) - jeśli KTÓREJKOLWIEK brakuje (i NIE MA dla
         * niej fallbacku), zbierz wszystkie braki do listy i wypisz je RAZEM
         * jako raport błędów startowych aplikacji (symulacja "fail fast" przy
         * starcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DynamicReconfigurationNotSupportedDemo {
        /*
         * 🧪 Zadanie 25:
         * W komentarzu wyjaśnij i zademonstruj (przez dwukrotne wywołanie
         * System.getenv() w tej samej metodzie main, z symulacją "zmiany" przez
         * zmienną Javy, bo realnych zmiennych środowiskowych nie można zmienić z
         * wnętrza JVM), że System.getenv() odczytuje wartości USTALONE przy
         * starcie JVM - w przeciwieństwie do System.getProperty(), którą MOŻNA
         * zmienić w trakcie działania programu (System.setProperty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiEnvironmentIntegrationTestSimulation {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę runAgainstEnvironment(String urlOverride) łączącą się z
         * bazą H2 wskazaną przez PARAMETR (symulującym różne "środowiska"
         * testowe zamiast realnych zmiennych) - wykonaj IDENTYCZNY scenariusz
         * (insert + select) dla 3 różnych "środowisk" (3 różne URL-e H2
         * in-memory) i zweryfikuj, że każde jest odizolowane (dane nie mieszają
         * się między bazami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EnvVariableControlledFeatureRollout {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj "procentowy rollout" funkcji na podstawie zmiennej
         * "L13EX27_FEATURE_PERCENT" (fallback "0", parsowana jako int 0-100).
         * Napisz isFeatureEnabledForUser(long userId, int percent) (np. userId %
         * 100 < percent) i przetestuj dla listy 20 userId z różnymi wartościami
         * percent (0, 50, 100).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SecureConnectionFactoryWithEnvAndAudit {
        /*
         * 🧪 Zadanie 28:
         * Rozbuduj ConnectionFactory z Zadania 21 o AUDYT: KAŻDE wywołanie
         * getConnection() dopisuje wpis do tabeli connection_audit (id,
         * timestamp, source="ENV_CONFIG") w TEJ SAMEJ bazie. Po 5 wywołaniach
         * getConnection() wypisz zawartość connection_audit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GracefulDegradationWhenSecretMissing {
        /*
         * 🧪 Zadanie 29:
         * Napisz DAO, które przy BRAKU wymaganej zmiennej (bez fallbacku, np.
         * "API_KEY_DLA_ZEWNETRZNEGO_SERWISU") przechodzi w "tryb ograniczony"
         * (wypisuje ostrzeżenie i wyłącza funkcję zależną od tego sekretu,
         * ZAMIAST rzucać wyjątek i przerywać cały program). Zademonstruj, że
         * reszta aplikacji (podstawowe operacje CRUD na bazie) działa normalnie
         * mimo braku tego sekretu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullEnvironmentDrivenMiniApplication {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację CAŁKOWICIE kierowaną zmiennymi środowiskowymi (z
         * fallbackami dla dev): URL bazy, hasło, flaga strict mode (Zadanie 14),
         * limit rollout (Zadanie 27). Zasymuluj DWA "przebiegi" programu z
         * różnymi wartościami fallbacków (symulującymi różne środowiska - bo
         * realnych zmiennych nie można zmienić w trakcie jednego procesu JVM) i
         * wypisz raport pokazujący różnice w zachowaniu programu.
         */
        public static void main(String[] args) { }
    }
}
