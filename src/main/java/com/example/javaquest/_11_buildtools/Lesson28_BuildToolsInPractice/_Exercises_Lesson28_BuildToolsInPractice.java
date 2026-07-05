package com.example.javaquest._11_buildtools.Lesson28_BuildToolsInPractice;

public class _Exercises_Lesson28_BuildToolsInPractice {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LocalVsCiBuildExplanation {
        /*
         * 🧪 Zadanie 1:
         * Wypisz dwie linie wyjasniajace roznice miedzy buildem lokalnym i buildem na CI,
         * z odniesieniem do problemu "u mnie dziala".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CiPlatformsList {
        /*
         * 🧪 Zadanie 2:
         * Wypisz 5 platform CI wspomnianych w lekcji (Jenkins, GitHub Actions, GitLab CI,
         * TeamCity, Bamboo) z jednozdaniowym opisem kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TypicalPipelineStepsPrint {
        /*
         * 🧪 Zadanie 3:
         * Wypisz 5 typowych krokow pipeline'u CI z lekcji (checkout, setup-java, build,
         * test, upload-artifact) w kolejnosci wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ArtifactTypesList {
        /*
         * 🧪 Zadanie 4:
         * Wypisz 3 typy artefaktow builda (JAR/WAR/ZIP, raporty testow, raporty coverage)
         * z jednozdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SnapshotVsReleaseExplanation {
        /*
         * 🧪 Zadanie 5:
         * Wypisz dwie linie wyjasniajace roznice miedzy wersja SNAPSHOT (1.0.0-SNAPSHOT)
         * i RELEASE (1.0.0) - kluczowe slowo: immutable.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SemVerPartsExplanation {
        /*
         * 🧪 Zadanie 6:
         * Dla wersji "3.7.2" wypisz rozbicie na MAJOR (3), MINOR (7), PATCH (2) z
         * jednozdaniowym wyjasnieniem, co zmienia sie przy zwiekszeniu kazdej czesci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EnvironmentsListPrint {
        /*
         * 🧪 Zadanie 7:
         * Wypisz 4 srodowiska (dev, test, stage, prod) z jednozdaniowym opisem
         * przeznaczenia kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SecretsNeverHardcodedRule {
        /*
         * 🧪 Zadanie 8:
         * Wypisz jedna linie z ZASADA "sekrety nigdy w build.xml/pom.xml/build.gradle"
         * i jedna linie z lista 3 bezpiecznych alternatyw (zmienne srodowiskowe, secret
         * manager, konfiguracja CI).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReadEnvironmentVariableSafely {
        /*
         * 🧪 Zadanie 9:
         * Odczytaj (System.getenv) zmienna "DB_PASSWORD" - jesli null, wypisz przyjazny
         * komunikat "zmienna nie ustawiona" (NIE stack trace, NIE null), jesli ustawiona -
         * wypisz TYLKO jej dlugosc w znakach, nigdy samej wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GitignoreLinesExplanation {
        /*
         * 🧪 Zadanie 10:
         * Wypisz 5 linii z .gitignore z lekcji (target/, build/, .gradle/, .idea/,
         * *.local.properties) z jednozdaniowym wyjasnieniem, czemu kazda NIE powinna
         * trafic do repozytorium.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenerateOwnGithubActionsYaml {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj WLASNY (inny niz w lekcji) ILUSTRACYJNY String YAML GitHub Actions dla
         * projektu Gradle (zamiast Maven) - checkout, setup-java, "./gradlew build", upload
         * artefaktu z build/libs/*.jar. Jasno oznacz w komentarzu, ze to ilustracyjny przyklad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SemVerComparator {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode compareSemVer(String v1, String v2), ktora porownuje dwie wersje
         * w formacie MAJOR.MINOR.PATCH numerycznie (bez zewnetrznej biblioteki) i zwraca
         * -1/0/1. Przetestuj na co najmniej 4 parach wersji (w tym rozne dlugosci, np. "1.2" vs "1.2.0").
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SnapshotDetector {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode isSnapshot(String version), ktora sprawdza, czy podana wersja
         * konczy sie na "-SNAPSHOT". Przetestuj na liscie zawierajacej wersje SNAPSHOT i
         * release, wypisujac klasyfikacje kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildNumberVsVersionDemo {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj przykladowa mape "build #4521" -> "wersja aplikacji 2.5.1", "build
         * #4522" -> "wersja aplikacji 2.5.1" (ten sam release, kolejny build CI - np. po
         * poprawce testu bez zmiany kodu produkcyjnego) i wypisz komentarz wyjasniajacy,
         * czemu to sa DWIE rozne rzeczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EnvironmentConfigSwitcher {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj Map<String, Map<String,String>> z konfiguracja per srodowisko (dev/
         * test/stage/prod), kazde z wlasciwosciami "db.url" i "log.level". Napisz metode
         * getConfigForEnvironment(String env), ktora zwraca odpowiednia mape (albo
         * wyjatek dla nieznanego srodowiska) i przetestuj na wszystkich 4 + jednym nieprawidlowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SecretMaskingUtility {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode maskSecret(String secret), ktora zwraca zamaskowana wersje
         * (np. pokazuje tylko pierwsze 2 i ostatnie 2 znaki, resztę zastepuje "*") -
         * uzyteczna do BEZPIECZNEGO logowania faktu istnienia sekretu bez ujawniania go.
         * Przetestuj na kilku przykladowych "sekretach" (Stringi w kodzie testowym, NIE
         * prawdziwe hasla).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ArtifactNamingConventionGenerator {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode buildArtifactName(String appName, String version, String
         * buildNumber), ktora generuje nazwe artefaktu w formacie
         * "appName-version-build.buildNumber.jar" (np. "myapp-2.5.1-build.4521.jar").
         * Przetestuj z kilkoma kombinacjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GitignoreValidator {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj liste "plikow w repo" (np. "target/App.class", "src/Main.java",
         * "secrets.properties", ".idea/workspace.xml") i liste wzorcow .gitignore z
         * lekcji. Napisz metode shouldBeIgnored(String path, List<String> patterns), ktora
         * (prostym porownaniem prefiksu/sufiksu, bez pelnej biblioteki glob) sprawdza,
         * czy dany plik POWINIEN byc zignorowany, i wypisz raport dla kazdego pliku z listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CoverageThresholdChecker {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode checkCoverageThreshold(double actualCoveragePercent, double
         * minimumRequiredPercent), ktora zwraca, czy build powinien PRZEJSC czy zostac
         * ODRZUCONY (jak typowy gate na CI, np. JaCoCo z minimalnym progiem 80%).
         * Przetestuj na 3 wartosciach (powyzej, rownej, ponizej progu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullCiPipelineSimulationLog {
        /*
         * 🧪 Zadanie 20:
         * Zasymuluj (println, bez realnego CI) przebieg pipeline'u CI krok po kroku
         * (checkout -> setup-java -> build -> test -> coverage check -> upload artifact),
         * z realistycznymi komunikatami dla kazdego kroku i CALKOWITYM czasem trwania
         * (suma wymyslonych czasow poszczegolnych krokow) na koncu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SemVerBumpCalculator {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode bumpVersion(String version, String bumpType) gdzie bumpType to
         * "major"/"minor"/"patch", ktora zwraca nastepna wersje wg zasad SemVer (np.
         * bump "minor" na "2.5.1" -> "2.6.0" - PATCH resetuje sie do 0). Przetestuj dla
         * wszystkich 3 typow bump'a na kilku wersjach wejsciowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiEnvironmentDeploymentSimulator {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj symulacje "deploymentu" przez kolejne srodowiska (dev -> test -> stage
         * -> prod), gdzie kazdy krok wymaga "zielonego" builda z poprzedniego etapu -
         * napisz metode deployPipeline(boolean[] stageResults), ktora zatrzymuje sie na
         * pierwszym niepowodzeniu i wypisuje, do ktorego srodowiska deployment sie udal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SecretRotationAuditSimulator {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj liste "sekretow" z data ostatniej rotacji (np. LocalDate) i napisz
         * metode findSecretsNeedingRotation(List<SecretRecord> secrets, int
         * maxAgeInDays), ktora zwraca te, ktore przekroczyly maksymalny wiek (dobra
         * praktyka bezpieczenstwa - okresowa rotacja sekretow). Zdefiniuj prosty rekord
         * SecretRecord(String name, LocalDate lastRotated) i przetestuj na 5 wpisach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ArtifactRetentionPolicySimulator {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj liste artefaktow z data utworzenia i napisz metode
         * applyRetentionPolicy(List<Artifact> artifacts, int keepLastN), ktora
         * symuluje typowa polityke CI "zachowaj tylko N najnowszych artefaktow, usun
         * starsze" - zwraca liste do usuniecia. Przetestuj na 10 artefaktach z keepLastN=3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConfigurationDriftDetector {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj dwie mapy konfiguracji: "oczekiwana" (np. z stage) i "faktyczna" (np. z
         * prod) - z kilkoma kluczami. Napisz metode detectDrift(Map<String,String>
         * expected, Map<String,String> actual), ktora wykrywa i wypisuje wszystkie
         * roznice (brakujace klucze, dodatkowe klucze, rozne wartosci) - przydatne do
         * wykrywania niezamierzonych roznic miedzy srodowiskami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildMetadataInjector {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode generateBuildInfoProperties(String version, String
         * gitCommitHash, String buildTimestamp), ktora generuje String w formacie .properties
         * (typowy plik "build-info.properties" generowany przez CI i pakowany do JAR-a,
         * zawierajacy metadane builda). Zapisz go do pliku w temp dir, odczytaj z
         * powrotem przez java.util.Properties i wypisz wszystkie wlasciwosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiToolCiMatrixSimulator {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj (jako println, bez realnego CI) "matrix build" na CI, ktory
         * testuje projekt na 3 wersjach JDK (17, 21, 23) i 2 systemach operacyjnych
         * (ubuntu, windows) - wypisz 6 kombinacji (3x2) jako osobne "joby" ze statusem
         * (wszystkie PASS, oprocz jednej celowo FAIL) i podsumowanie na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SecretLeakScanner {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode scanForHardcodedSecrets(String fileContent), ktora prostym
         * wyszukiwaniem wzorcow (np. regex na "password\\s*=\\s*[\"'][^\"']+[\"']" albo
         * "api[_-]?key") wykrywa PODEJRZANE linie zawierajace prawdopodobnie zahardkodowany
         * sekret. Przetestuj na przykladowej tresci pliku config (Stringu w kodzie) z
         * kilkoma zlymi i dobrymi liniami, wypisujac raport znalezionych podejrzen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ReleaseChecklistValidator {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj checkliste kroków przed wydaniem release'u (np. "wersja bez -SNAPSHOT",
         * "wszystkie testy przechodza", "CHANGELOG zaktualizowany", "coverage >= 80%",
         * "brak sekretow w kodzie") jako liste warunkow boolowskich. Napisz metode
         * validateReleaseReadiness(...), ktora sprawdza wszystkie i wypisuje raport
         * GOTOWY/NIEGOTOWY z lista niespelnionych warunkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstonePracticeReport {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY, sformatowany raport "gotowosci
         * produkcyjnej" projektu laczacy: wygenerowany plik build-info.properties
         * (Zadanie 26), wynik walidacji release checklist (Zadanie 29), wykryte sekrety
         * w hipotetycznym configu (Zadanie 28) i sugerowany .gitignore (z lekcji).
         * Wypisz raport w jednej spojnej, czytelnej strukturze z sekcjami i podsumowaniem
         * na koncu (GOTOWY DO WYDANIA / WYMAGA POPRAWEK).
         */
        public static void main(String[] args) { }
    }
}
