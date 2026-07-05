package com.example.javaquest._11_buildtools.Lesson28_BuildToolsInPractice;

public class _Lesson28_BuildToolsInPractice {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 28: BUILD TOOLE W PRAKTYCE ZAWODOWEJ ===");

        /*
         * ============================================================
         * 📦 BUILD LOKALNY vs BUILD NA CI
         * ============================================================
         * BUILD LOKALNY - odpalasz go na WŁASNYM komputerze (IDE albo
         * terminal), głównie żeby sprawdzić, czy Twój kod się kompiluje
         * i przechodzi testy, ZANIM wyślesz go do repozytorium (commit/push).
         *
         * BUILD NA CI (Continuous Integration) - odpala się AUTOMATYCZNIE
         * na serwerze, po każdym push/pull request, NIEZALEŻNIE od
         * konfiguracji Twojego komputera. To "obiektywna prawda" - jeśli
         * build przechodzi na CI, to przechodzi w ŚRODOWISKU KONTROLOWANYM,
         * a nie "u mnie działa" (słynny problem "works on my machine").
         *
         * Popularne serwery/platformy CI dla projektów Java:
         * - Jenkins          -> najstarszy, self-hosted, ogromna elastyczność
         *   (pluginy), ale wymaga własnej infrastruktury i administracji
         * - GitHub Actions    -> zintegrowany z GitHub, konfiguracja YAML
         *   w repo (.github/workflows/), bardzo popularny w projektach
         *   open-source i mniejszych/średnich firmach
         * - GitLab CI         -> analogicznie zintegrowany z GitLab
         *   (.gitlab-ci.yml), popularny w firmach hostujących kod na GitLab
         * - TeamCity          -> od JetBrains, popularny w firmach .NET/Java
         *   z bardziej "enterprise" podejściem (GUI konfiguracji)
         * - Bamboo            -> od Atlassian, integruje się z Jira/Bitbucket
         */

        System.out.println("\n=== BUILD LOKALNY vs BUILD NA CI ===");
        System.out.println("Lokalny: na Twoim komputerze, szybka petla zwrotna, ale 'u mnie dziala' != prawda.");
        System.out.println("CI: automatycznie po push/PR, kontrolowane srodowisko - obiektywna weryfikacja.");
        System.out.println("Platformy CI: Jenkins, GitHub Actions, GitLab CI, TeamCity, Bamboo.");

        /*
         * ============================================================
         * 🔹 ILUSTRACYJNY PRZYKŁAD: GITHUB ACTIONS
         * ============================================================
         * Poniższy YAML to REALISTYCZNY, ale ILUSTRACYJNY przykład
         * (jasno oznaczony - NIE jest to plik faktycznie odpalony przez
         * to demo, tylko tekst do nauki formatu). Typowy pipeline CI
         * dla projektu Maven: checkout kodu -> setup Javy -> build ->
         * testy -> upload artefaktu.
         */

        String githubActionsYaml = """
                # .github/workflows/build.yml (ILUSTRACYJNY przyklad, nieuzywany w tym demo)
                name: Build and Test

                on:
                  push:
                    branches: [ main ]
                  pull_request:
                    branches: [ main ]

                jobs:
                  build:
                    runs-on: ubuntu-latest
                    steps:
                      - name: Checkout kodu
                        uses: actions/checkout@v4

                      - name: Ustawienie JDK 21
                        uses: actions/setup-java@v4
                        with:
                          java-version: '21'
                          distribution: 'temurin'

                      - name: Build (bez testow integracyjnych)
                        run: ./mvnw -B compile

                      - name: Testy jednostkowe
                        run: ./mvnw -B test

                      - name: Wyslij artefakt (JAR)
                        uses: actions/upload-artifact@v4
                        with:
                          name: app-jar
                          path: target/*.jar
                """;

        System.out.println("\n=== ILUSTRACYJNY PRZYKLAD: .github/workflows/build.yml ===");
        System.out.println("(To jest przykladowy, nieodpalony YAML - wylacznie do nauki formatu pipeline'u CI.)");
        System.out.println(githubActionsYaml);
        System.out.println("Kroki: checkout -> setup-java -> build -> test -> upload-artifact.");
        System.out.println("Uzycie './mvnw' (a nie golego 'mvn') gwarantuje TA SAMA wersje Mavena na CI,");
        System.out.println("co lokalnie u kazdego programisty - patrz Maven Wrapper z Lekcji 11.");

        /*
         * ============================================================
         * 🔍 ARTEFAKTY BUILDA
         * ============================================================
         * ARTEFAKT to "produkt finalny" builda - plik (albo zestaw
         * plików), który jest WYNIKIEM procesu budowania i który
         * faktycznie chcemy dalej wykorzystać (uruchomić, wdrożyć,
         * przeanalizować):
         *
         * - JAR / WAR / ZIP  -> gotowa do uruchomienia/wdrożenia aplikacja
         *   (JAR - zwykła aplikacja Java, WAR - aplikacja webowa do
         *   kontenera servletów, ZIP - dystrybucja z dodatkowymi plikami)
         * - raporty testów   -> np. XML/HTML z wynikami JUnit (surefire-reports
         *   w Mavenie, podobnie w Ant/Gradle) - CI pokazuje je jako
         *   "ile testów przeszło/nie przeszło"
         * - raporty coverage -> np. z JaCoCo - jaki % kodu jest pokryty
         *   testami - często wymagany minimalny próg (np. 80%) do
         *   przejścia buildu na CI
         *
         * Artefakty z CI często są "przechowywane" (upload-artifact w
         * GitHub Actions, jak w YAML wyżej) - żeby móc je pobrać/
         * przeanalizować/wdrożyć bez ponownego budowania.
         */

        System.out.println("\n=== ARTEFAKTY BUILDA ===");
        System.out.println("JAR/WAR/ZIP    - finalna, gotowa do wdrozenia postac aplikacji.");
        System.out.println("Raporty testow - wyniki JUnit (ile testow przeszlo/nie przeszlo).");
        System.out.println("Raporty coverage - % kodu pokrytego testami (np. JaCoCo), czesto z progiem minimalnym.");

        /*
         * ============================================================
         * 🔹 WERSJONOWANIE: SNAPSHOT vs RELEASE
         * ============================================================
         * - WERSJA SNAPSHOT (np. "1.0.0-SNAPSHOT") -> wersja ROZWOJOWA,
         *   jeszcze NIE ostateczna. Może się zmieniać (nawet pod tym
         *   samym numerem) - Maven/Gradle traktują ją specjalnie
         *   (np. Maven ZAWSZE sprawdza, czy jest nowsza wersja SNAPSHOT
         *   w repozytorium, nie cache'uje jej na trwałe).
         * - WERSJA RELEASE (np. "1.0.0") -> wersja OSTATECZNA, RAZ
         *   opublikowana, NIGDY nie zmienia się pod tym samym numerem
         *   (immutable) - jeśli trzeba coś poprawić, wydaje się NOWĄ
         *   wersję (np. "1.0.1"), nie nadpisuje starej.
         *
         * SEMANTIC VERSIONING (SemVer) - powszechnie przyjęta konwencja
         * numerowania wersji: MAJOR.MINOR.PATCH (np. 2.5.1):
         * - MAJOR -> niekompatybilne zmiany API (coś, co może "złamać"
         *   kod korzystający z tej biblioteki)
         * - MINOR -> nowa funkcjonalność, KOMPATYBILNA wstecz
         * - PATCH -> poprawki błędów, bez nowej funkcjonalności,
         *   kompatybilne wstecz
         *
         * NUMER BUILDA vs WERSJA APLIKACJI - to DWIE różne rzeczy:
         * - wersja aplikacji (np. "2.5.1") -> zmienia się przy release'ach,
         *   widoczna dla użytkowników/klientów
         * - numer builda (np. "#4521", często z CI) -> zmienia się przy
         *   KAŻDYM uruchomieniu pipeline'u, używany do identyfikacji
         *   KONKRETNEGO uruchomienia CI (debugging, śledzenie "który
         *   dokładnie commit/build wywołał problem")
         */

        System.out.println("\n=== SNAPSHOT vs RELEASE ===");
        System.out.println("1.0.0-SNAPSHOT -> wersja rozwojowa, MOZE sie zmieniac pod tym samym numerem.");
        System.out.println("1.0.0          -> wersja release, RAZ opublikowana, NIGDY nie zmienia sie (immutable).");
        System.out.println();
        System.out.println("Semantic Versioning (MAJOR.MINOR.PATCH), np. 2.5.1:");
        System.out.println("MAJOR (2) - niekompatybilne zmiany API.");
        System.out.println("MINOR (5) - nowa funkcjonalnosc, kompatybilna wstecz.");
        System.out.println("PATCH (1) - poprawki bledow, kompatybilne wstecz.");
        System.out.println();
        System.out.println("Wersja aplikacji (2.5.1) != numer builda CI (#4521) - to dwie rozne rzeczy.");

        /*
         * ============================================================
         * 🔍 KONFIGURACJA ŚRODOWISK: dev/test/stage/prod
         * ============================================================
         * Realne aplikacje działają w RÓŻNYCH środowiskach, z RÓŻNĄ
         * konfiguracją (adresy baz danych, klucze API, poziomy logowania):
         * - dev    -> środowisko developerskie (lokalne albo dedykowany
         *   serwer deweloperski), często z danymi testowymi/mockami
         * - test   -> środowisko do testów automatycznych/manualnych QA
         * - stage  -> "przedprodukcyjne", jak najbardziej zbliżone do
         *   prod, do finalnej weryfikacji przed wdrożeniem
         * - prod   -> PRODUKCJA - realni użytkownicy, realne dane
         *
         * Mechanizmy przełączania konfiguracji między środowiskami:
         * - PROPERTIES/PROFILE - np. application-dev.properties,
         *   application-prod.properties (Spring Boot), albo profile
         *   Maven (<profiles>, patrz Lekcja 14) aktywowane np. flagą
         *   "-Pprod"
         * - ZMIENNE ŚRODOWISKOWE - wartości WSTRZYKIWANE z zewnątrz
         *   (przez system operacyjny/kontener/CI), NIE zaszyte w kodzie
         *   ani w pliku konfiguracyjnym commitowanym do repo - to
         *   NAJLEPSZE miejsce na WRAŻLIWE dane (patrz niżej: sekrety)
         */

        System.out.println("\n=== KONFIGURACJA SRODOWISK (dev/test/stage/prod) ===");
        System.out.println("dev    - lokalne/deweloperskie, dane testowe.");
        System.out.println("test   - testy automatyczne/QA.");
        System.out.println("stage  - przedprodukcyjne, jak najblizej prod.");
        System.out.println("prod   - produkcja, realni uzytkownicy.");
        System.out.println("Mechanizmy: properties/profile per srodowisko, zmienne srodowiskowe (dla sekretow).");

        /*
         * ============================================================
         * 🔹 SEKRETY - NIGDY W build.xml / pom.xml / build.gradle
         * ============================================================
         * ZASADA ŻELAZNA: hasła, klucze API, tokeny NIGDY nie powinny
         * być zaszyte "na twardo" w plikach konfiguracyjnych buildu
         * (ani w ŻADNYM pliku commitowanym do repozytorium Git!). Repo
         * (nawet prywatne) to zbyt duże ryzyko - historia Git PAMIĘTA
         * WSZYSTKO, nawet po "usunięciu" sekretu w kolejnym commicie.
         *
         * Bezpieczne alternatywy:
         * - ZMIENNE ŚRODOWISKOWE - odczytywane w runtime przez
         *   System.getenv(...), ustawiane RĘCZNIE na maszynie/w
         *   kontenerze/w konfiguracji CI (NIE w pliku repo).
         * - SECRET MANAGER - dedykowane narzędzia (np. HashiCorp Vault,
         *   AWS Secrets Manager, Azure Key Vault) - przechowują sekrety
         *   scentralizowanie, z kontrolą dostępu i audytem.
         * - KONFIGURACJA CI - większość platform CI (GitHub Actions,
         *   Jenkins, GitLab CI) ma WBUDOWANY, zaszyfrowany magazyn
         *   sekretów (np. "GitHub Secrets"), wstrzykiwany jako zmienna
         *   środowiskowa TYLKO podczas wykonania pipeline'u.
         *
         * Poniżej KONKRETNY przykład (realnie wykonywany w tym demo!):
         * odczyt "sekretu" ze zmiennej środowiskowej z przyjaznym
         * fallbackiem, gdy zmienna nie jest ustawiona - to WŁAŚNIE
         * ten wzorzec powinien zastąpić hardkodowanie hasła w configu.
         */

        String dbPassword = System.getenv("DB_PASSWORD");
        System.out.println("\n=== ODCZYT SEKRETU ZE ZMIENNEJ SRODOWISKOWEJ (realnie wykonane) ===");
        if (dbPassword != null && !dbPassword.isBlank()) {
            System.out.println("Zmienna DB_PASSWORD jest ustawiona (dlugosc: " + dbPassword.length() + " znakow).");
            System.out.println("(Nigdy nie wypisujemy samej wartosci sekretu na konsole/w logach!)");
        } else {
            System.out.println("Zmienna DB_PASSWORD NIE jest ustawiona w tym srodowisku.");
            System.out.println("To jest OCZEKIWANE w tym demo - w realnej aplikacji ustawilbys ja");
            System.out.println("przez system operacyjny / kontener / sekrety CI, NIGDY w pom.xml/build.gradle.");
        }
        System.out.println();
        System.out.println("ZLE  (NIGDY tak nie robic): <password>MojeTajneHaslo123</password> w pom.xml");
        System.out.println("DOBRZE: String password = System.getenv(\"DB_PASSWORD\"); // wstrzykniete z zewnatrz");

        /*
         * ============================================================
         * 🔍 .gitignore DLA PROJEKTOW ANT + MAVEN + GRADLE
         * ============================================================
         * Katalogi/pliki WYNIKOWE (generowane przez build) oraz pliki
         * LOKALNE dla konkretnego dewelopera NIGDY nie powinny trafiać
         * do repozytorium - .gitignore je wyklucza.
         */

        String gitignoreFragment = """
                # Wyniki budowania
                target/          # Maven
                build/           # Gradle (i czesto Ant, jesli tak nazwano katalog wynikowy)
                out/             # niektore IDE/konfiguracje Ant

                # Gradle - lokalny cache/stan
                .gradle/

                # Lokalne pliki IDE
                .idea/
                *.iml
                .vscode/
                .settings/
                .classpath
                .project

                # Lokalne properties z sekretami - NIGDY nie commitowac!
                application-local.properties
                secrets.properties
                *.local.properties
                """;

        System.out.println("\n=== .gitignore DLA PROJEKTU ANT + MAVEN + GRADLE ===");
        System.out.println(gitignoreFragment);
        System.out.println("target/ (Maven), build/ (Gradle/Ant) - katalogi WYNIKOWE, zawsze regenerowane.");
        System.out.println(".gradle/ - lokalny cache/stan demona Gradle, specyficzny dla maszyny.");
        System.out.println(".idea/, *.iml, .vscode/, .classpath, .project - ustawienia konkretnego IDE dewelopera.");
        System.out.println("*.local.properties, secrets.properties - TU najczesciej trafiaja przypadkowo sekrety!");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Build lokalny (szybka petla zwrotna) vs build na CI
         *   (obiektywna weryfikacja w kontrolowanym srodowisku).
         * - Popularne platformy CI: Jenkins, GitHub Actions, GitLab CI,
         *   TeamCity, Bamboo - typowy pipeline: checkout -> setup ->
         *   build -> test -> upload artefaktu.
         * - Artefakty: JAR/WAR/ZIP + raporty testow + raporty coverage.
         * - SNAPSHOT (rozwojowa, zmienna) vs RELEASE (ostateczna,
         *   immutable); Semantic Versioning MAJOR.MINOR.PATCH; numer
         *   builda != wersja aplikacji.
         * - Srodowiska dev/test/stage/prod - rozna konfiguracja przez
         *   properties/profile (nie-sekrety) i zmienne srodowiskowe
         *   (sekrety).
         * - Sekrety NIGDY w build.xml/pom.xml/build.gradle - zmienne
         *   srodowiskowe, secret manager, konfiguracja CI.
         * - .gitignore: target/, build/, out/, .gradle/, pliki IDE,
         *   lokalne properties z haslami.
         */

        System.out.println("\n=== KONIEC LEKCJI 28 ===");
    }
}
