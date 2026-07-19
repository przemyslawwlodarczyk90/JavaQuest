package com.example.javaquest._26_integration_testing.Lesson04_TestcontainersIntroduction;

public class _Exercises_Lesson04_TestcontainersIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CheckDockerAvailabilityProgrammatically {
        /*
         * 🧪 Zadanie 1:
         * Sprawdz PROGRAMOWO dostepnosc Dockera (`DockerClientFactory`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StartAndStopPostgresContainer {
        /*
         * 🧪 Zadanie 2:
         * Uruchom I zatrzymaj kontener PostgreSQL (JESLI Docker
         * dostepny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConnectToContainerAndRunSimpleQuery {
        /*
         * 🧪 Zadanie 3:
         * Polacz sie Z kontenerem I wykonaj PROSTE zapytanie SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintJdbcUrlUsernameAndPassword {
        /*
         * 🧪 Zadanie 4:
         * Wypisz `getJdbcUrl()`/`getUsername()`/`getPassword()`
         * kontenera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseTryWithResourcesForContainer {
        /*
         * 🧪 Zadanie 5:
         * Uzyj try-with-resources DO automatycznego zamkniecia
         * kontenera (powiazanie Z `_04_io/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainDifferenceFromH2Approach {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij ROZNICE miedzy Lesson03 (H2) A
         * TA lekcja (Testcontainers).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryDifferentPostgresImageTag {
        /*
         * 🧪 Zadanie 7:
         * Zmien TAG obrazu (np. `postgres:15-alpine`) I zweryfikuj
         * DZIALANIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_HandleDockerUnavailableGracefully {
        /*
         * 🧪 Zadanie 8:
         * Napisz kod OBSLUGUJACY brak Dockera BEZ wywalenia calego
         * programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MeasureContainerStartupTime {
        /*
         * 🧪 Zadanie 9:
         * Zmierz CZAS uruchomienia kontenera (JESLI Docker dostepny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAlternativeContainerModulesAvailable {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz 5 INNYCH modulow Testcontainers (np.
         * MySQL/MongoDB/Kafka/RabbitMQ/Redis).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CreateTableAndInsertMultipleRowsInContainer {
        /*
         * 🧪 Zadanie 11:
         * Utworz tabele I WSTAW WIELE wierszy W kontenerze PostgreSQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RunFlywayMigrationAgainstContainer {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_10_dao/Lesson25` - URUCHOM realna migracje
         * Flyway PRZECIW kontenerowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestPostgresSpecificFeatureNotInH2 {
        /*
         * 🧪 Zadanie 13:
         * Napisz test uzywajacy funkcji SPECYFICZNEJ DLA PostgreSQL
         * (np. `RETURNING`, KTOREJ H2 obsluguje inaczej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConfigureContainerWithEnvironmentVariables {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj kontener PRZEZ `.withEnv(...)`/`.withDatabaseName(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRetryWhenContainerNotYetReady {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj retry PRZY laczeniu sie Z kontenerem TUZ PO
         * starcie (powiazanie Z Lesson02).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareStartupCostAcrossMultipleContainerStarts {
        /*
         * 🧪 Zadanie 16:
         * Uruchom I zatrzymaj kontener 3 RAZY - zmierz roznice CZASU
         * (cache obrazu Docker).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteRepositoryTestAgainstRealPostgresContainer {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_10_dao` - przetestuj PRAWDZIWE repozytorium
         * JDBC PRZECIW kontenerowi PostgreSQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainRyukContainerRole {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz role kontenera "Ryuk" (auto-sprzatanie
         * Testcontainers).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestContainerLogsAccess {
        /*
         * 🧪 Zadanie 19:
         * Odczytaj LOGI kontenera PRZEZ `getLogs()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFallbackDecisionTreeForCiWithoutDocker {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj (W komentarzu) strategie DLA CI BEZ Dockera -
         * KIEDY pomijac testy, KIEDY je BLOKOWAC.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomWaitStrategyForContainer {
        /*
         * 🧪 Zadanie 21:
         * Skonfiguruj WLASNA `WaitStrategy` (np. czekanie NA
         * KONKRETNY log) zamiast domyslnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RunMultipleContainersSimultaneously {
        /*
         * 🧪 Zadanie 22:
         * Uruchom DWA RÓZNE kontenery NARAZ (np. PostgreSQL +
         * symulowany drugi serwis) I zweryfikuj OBA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementNetworkedContainersCommunication {
        /*
         * 🧪 Zadanie 23:
         * Zbadaj `Network.newNetwork()` DO komunikacji MIEDZY DWOMA
         * kontenerami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildTestContainerFactoryForMultipleEngines {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj FABRYKE zwracajaca RÓZNE kontenery (Postgres/MySQL)
         * WEDLUG parametru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureResourceUsageOfRunningContainer {
        /*
         * 🧪 Zadanie 25:
         * Zbadaj (koncepcyjnie/przez `docker stats`) ZUZYCIE
         * pamieci/CPU DZIALAJACEGO kontenera testowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementContainerHealthCheckBeforeRunningTests {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj WLASNY health-check PRZED uruchomieniem
         * testow NA kontenerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignCiConfigurationRunningTestcontainersInDocker {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj (W komentarzu) konfiguracje CI URUCHAMIAJACA
         * Testcontainers "Docker-in-Docker" (powiazanie Z
         * `_11_buildtools`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementContainerReuseAcrossMultipleTestClasses {
        /*
         * 🧪 Zadanie 28:
         * Zbadaj `.withReuse(true)` (zapowiedz Lesson06) I OPISZ
         * kompromisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildAbstractionLayerHidingTestcontainersDetails {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj WARSTWE abstrakcji (interfejs) UKRYWAJACA szczegoly
         * Testcontainers PRZED resztaq pakietu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullIntegrationTestInfrastructureUsingTestcontainers {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA infrastrukture testowa (WIELE kontenerow +
         * siec + fallback) DLA aplikacji Z `_17_architecture/Lesson20`.
         */
        public static void main(String[] args) { }
    }
}
