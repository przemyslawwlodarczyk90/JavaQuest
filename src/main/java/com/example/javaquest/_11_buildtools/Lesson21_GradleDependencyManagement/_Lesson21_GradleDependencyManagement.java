package com.example.javaquest._11_buildtools.Lesson21_GradleDependencyManagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson21_GradleDependencyManagement {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 21: GRADLE - ZARZADZANIE ZALEZNOSCIAMI (ZAAWANSOWANE) ===");
        System.out.println("Ta lekcja rozwija Lekcje 17 (dependencyInsight, konflikty wersji) - tu skupiamy");
        System.out.println("sie na TRZECH nowych mechanizmach: version catalogs, platformy/BOM i substytucje.");

        /*
         * ============================================================
         * 📦 VERSION CATALOG - CENTRALNY KATALOG WERSJI
         * ============================================================
         * Problem, ktory rozwiazuje: w multi-project buildzie (Lekcja 17)
         * KAZDY podprojekt deklarowal wersje zaleznosci OSOBNO w swoim
         * build.gradle - latwo o niezgodnosc (jeden modul uzywa gson
         * 2.10.0, drugi 2.11.0 - przez czysta literowke/nieuwage, nie
         * przez swiadoma decyzje).
         *
         * VERSION CATALOG to plik gradle/libs.versions.toml (format TOML)
         * w KORZENIU projektu - centralne miejsce definiowania WERSJI I
         * KOORDYNATOW zaleznosci. Kazdy podprojekt odwoluje sie do nich
         * przez WYGENEROWANY automatycznie typowany akcesor "libs.*"
         * (dzialajace uzupelnianie skladni w IDE!), a NIE przez surowe
         * Stringi "grupa:artefakt:wersja".
         *
         * Budowa libs.versions.toml - 4 sekcje:
         * - [versions]     -> nazwane wersje (np. gson = "2.11.0")
         * - [libraries]    -> biblioteki, odwolujace sie do wersji
         *   z [versions] po nazwie (module = "grupa:artefakt", version.ref = "...")
         * - [bundles]      -> grupy bibliotek uzywanych ZAWSZE razem
         *   (np. "testing" = ["junit-jupiter", "junit-jupiter-engine"])
         * - [plugins]      -> analogicznie dla pluginow (id + wersja)
         */

        String libsVersionsToml = """
                [versions]
                gson = "2.11.0"
                junit = "5.10.2"
                lombok = "1.18.34"

                [libraries]
                gson = { module = "com.google.code.gson:gson", version.ref = "gson" }
                junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit" }
                lombok = { module = "org.projectlombok:lombok", version.ref = "lombok" }

                [bundles]
                testing = ["junit-jupiter"]

                [plugins]
                spring-boot = { id = "org.springframework.boot", version = "3.4.4" }
                """;

        String settingsWithCatalog = """
                rootProject.name = 'moj-system'
                include("core", "app")

                dependencyResolutionManagement {
                    repositories {
                        mavenCentral()
                    }
                    // Domyslnie Gradle SAM wykrywa gradle/libs.versions.toml - powyzsza
                    // sekcja repositories{} tutaj jest wymagana od Gradle 7+ jako
                    // centralne miejsce deklaracji repozytoriow dla WSZYSTKICH podprojektow
                    // (zamiast powtarzac repositories{} w kazdym build.gradle).
                }
                """;

        String buildGradleUsingCatalog = """
                dependencies {
                    implementation libs.gson
                    compileOnly libs.lombok
                    testImplementation libs.bundles.testing
                }
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson18-catalog");
        Path gradleDir = tempDir.resolve("gradle");
        Files.createDirectories(gradleDir);
        Path tomlPath = gradleDir.resolve("libs.versions.toml");
        Path settingsPath = tempDir.resolve("settings.gradle");
        Files.writeString(tomlPath, libsVersionsToml);
        Files.writeString(settingsPath, settingsWithCatalog);

        System.out.println("\n=== gradle/libs.versions.toml (zapisany na dysku) ===");
        System.out.println("Sciezka: " + tomlPath);
        System.out.println(libsVersionsToml);

        System.out.println("=== settings.gradle (dependencyResolutionManagement) ===");
        System.out.println(settingsWithCatalog);

        System.out.println("=== build.gradle KAZDEGO podprojektu - uzycie katalogu ===");
        System.out.println(buildGradleUsingCatalog);
        System.out.println("Zauwaz: 'libs.gson' (nie String!) - IDE podpowiada dostepne wpisy, a literowka");
        System.out.println("w nazwie 'libs.gsonn' jest bledem KOMPILACJI skryptu build.gradle, nie tajemniczym");
        System.out.println("bledem resolvowania zaleznosci w polowie builda.");

        /*
         * ============================================================
         * 🔹 PLATFORMY I BOM (BILL OF MATERIALS)
         * ============================================================
         * BOM (Bill Of Materials) - specjalny artefakt (zwykle
         * packaging=pom), ktory NIE zawiera kodu, tylko listę
         * REKOMENDOWANYCH wersji dla grupy powiazanych bibliotek (np.
         * Spring Boot BOM zarzadza wersjami dziesiatkow modulow Spring,
         * tak jak <parent> spring-boot-starter-parent robi to dla
         * TEGO KURSU w pom.xml - to jest scisly odpowiednik dla Mavena).
         *
         * W Gradle importuje się BOM przez "platform(...)":
         *
         *   dependencies {
         *       implementation platform('org.springframework.boot:spring-boot-dependencies:3.4.4')
         *       implementation 'org.springframework.boot:spring-boot-starter-web' // BEZ wersji!
         *   }
         *
         * Po zaimportowaniu platformy NIE podajemy juz wersji dla
         * zaleznosci zarzadzanych przez ten BOM - platforma "narzuca"
         * spojny, przetestowany zestaw wersji dla calej rodziny
         * bibliotek na raz (unikamy recznego synchronizowania dziesiatek
         * numerkow wersji).
         *
         * "enforcedPlatform(...)" to silniejszy wariant - WYMUSZA wersje
         * z BOM-u nawet, jesli inna zaleznosc zada innej wersji (podczas
         * gdy zwykly "platform(...)" to tylko REKOMENDACJA, ktora inna,
         * bardziej "bezposrednia" deklaracja moze przebic).
         */

        String platformSnippet = """
                dependencies {
                    implementation platform('org.springframework.boot:spring-boot-dependencies:3.4.4')
                    implementation 'org.springframework.boot:spring-boot-starter-web'   // bez wersji - z BOM
                    implementation 'org.springframework.boot:spring-boot-starter-validation' // bez wersji - z BOM

                    // enforcedPlatform - WYMUSZA wersje z BOM, nawet przeciw innym zadaniom wersji
                    // implementation enforcedPlatform('org.springframework.boot:spring-boot-dependencies:3.4.4')
                }
                """;

        System.out.println("\n=== PLATFORM/BOM (build.gradle) ===");
        System.out.println(platformSnippet);
        System.out.println("Analogia do Mavena: platform(...) w Gradle = <dependencyManagement><dependency>");
        System.out.println("<type>pom</type><scope>import</scope> w pom.xml (obie mechaniki 'importuja' BOM).");
        System.out.println("Ten kurs (JavaQuest) uzywa dokladnie tego mechanizmu w Mavenie - parent");
        System.out.println("spring-boot-starter-parent w pom.xml w korzeniu repo dziala jak platforma.");

        /*
         * ============================================================
         * 🔍 DEPENDENCY SUBSTITUTION - ZAMIANA JEDNEJ ZALEZNOSCI NA INNA
         * ============================================================
         * Czasem trzeba POWIEDZIEC Gradle: "gdy ktokolwiek zada
         * biblioteki X, dawaj mu Y" - to substytucja zaleznosci.
         * Typowe zastosowania:
         * - zamiana starej, nieutrzymywanej biblioteki na jej "fork"/
         *   nastepce (np. stare "log4j" na "log4j-over-slf4j")
         * - w multi-project buildzie: zamiana zewnetrznej zaleznosci
         *   na LOKALNY podprojekt podczas developmentu (np. gdy
         *   pracujesz jednoczesnie nad biblioteka i aplikacja, ktora
         *   jej uzywa - chcesz testowac NAJNOWSZY kod lokalny, nie
         *   ostatnia opublikowana wersje z repozytorium)
         */

        String substitutionSnippet = """
                configurations.all {
                    resolutionStrategy {
                        dependencySubstitution {
                            substitute module('log4j:log4j') using module('org.slf4j:log4j-over-slf4j:2.0.13')
                            // Podczas developmentu: uzyj lokalnego podprojektu ':core' zamiast
                            // opublikowanej wersji 'com.example:core:1.0.0'
                            substitute module('com.example:core') using project(':core')
                        }
                    }
                }
                """;

        System.out.println("\n=== DEPENDENCY SUBSTITUTION (build.gradle) ===");
        System.out.println(substitutionSnippet);
        System.out.println("Typowe zastosowania: zamiana przestarzalej biblioteki, oraz podczas developmentu -");
        System.out.println("zamiana zewnetrznej zaleznosci na LOKALNY podprojekt (szybsza petla zwrotna).");

        /*
         * ============================================================
         * 🔹 STRATEGIE ROZWIĄZYWANIA KONFLIKTÓW (POZA dependencyInsight)
         * ============================================================
         * Lekcja 17 pokazala "dependencyInsight" - narzedzie DIAGNOSTYCZNE
         * (co wybrano i czemu). Tu poznajemy narzedzia, ktorymi
         * AKTYWNIE STERUJEMY tym wyborem, gdy domyslna strategia
         * "najnowsza wersja wygrywa" nas nie satysfakcjonuje:
         *
         * - force(...)      -> WYMUSZA konkretna wersje danej zaleznosci,
         *   niezaleznie od tego, czego zadaja inne moduly (uzywaj
         *   ostroznie - to "obejscie", nie rozwiazanie przyczyny).
         * - failOnVersionConflict() -> odwrotnie: build ZAWIEDZIE przy
         *   JAKIMKOLWIEK konflikcie wersji, zamiast automatycznie
         *   wybierac "najnowsza" - wymusza JAWNA decyzje programisty.
         * - exclude(...)    -> wyklucza KONKRETNA transytywna zaleznosc
         *   calkowicie (np. gdy dwie biblioteki logujace kolidują -
         *   Logback vs Log4j - i chcemy tylko jednej).
         */

        String conflictStrategiesSnippet = """
                configurations.all {
                    resolutionStrategy {
                        force 'com.google.code.gson:gson:2.11.0'
                        // failOnVersionConflict()   // odkomentuj, aby WYMUSIC jawna decyzje przy kazdym konflikcie
                    }
                }

                dependencies {
                    implementation('com.example:some-lib:1.0.0') {
                        exclude group: 'org.apache.logging.log4j', module: 'log4j-core'
                    }
                }
                """;

        System.out.println("\n=== STRATEGIE ROZWIAZYWANIA KONFLIKTOW (build.gradle) ===");
        System.out.println(conflictStrategiesSnippet);
        System.out.println("force(...)        - wymusza konkretna wersje (uzywaj oszczednie).");
        System.out.println("failOnVersionConflict() - build zawodzi przy konflikcie, wymaga jawnej decyzji.");
        System.out.println("exclude(...)      - calkowicie wyklucza transytywna zaleznosc (np. konfliktujacy logger).");

        Files.deleteIfExists(tomlPath);
        Files.deleteIfExists(settingsPath);
        Files.deleteIfExists(gradleDir);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Version catalog (gradle/libs.versions.toml) - centralne,
         *   typowane (libs.*) zarzadzanie wersjami w multi-project buildzie,
         *   deklarowane w settings.gradle (dependencyResolutionManagement).
         * - Platform/BOM (implementation platform(...)/enforcedPlatform(...))
         *   - import spojnego zestawu wersji z jednego artefaktu (analogia
         *   do <dependencyManagement>/<parent> w Mavenie).
         * - Dependency substitution - zamiana jednej zaleznosci na inna
         *   (przestarzala biblioteka -> nastepca, zdalna -> lokalny podprojekt).
         * - Strategie konfliktow: force (wymuszenie), failOnVersionConflict
         *   (jawna decyzja wymagana), exclude (wykluczenie transytywnej
         *   zaleznosci) - to uzupelnienie diagnostyki z dependencyInsight (Lekcja 17).
         */

        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }
}
