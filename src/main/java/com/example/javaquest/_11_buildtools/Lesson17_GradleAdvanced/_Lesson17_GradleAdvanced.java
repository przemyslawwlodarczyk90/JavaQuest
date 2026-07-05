package com.example.javaquest._11_buildtools.Lesson17_GradleAdvanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson17_GradleAdvanced {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 17: GRADLE - ZAGADNIENIA ZAAWANSOWANE ===");

        /*
         * ============================================================
         * 📦 CUSTOM TASKS - WŁASNE TASKI
         * ============================================================
         * Oprócz wbudowanych tasków (clean, compileJava, test, jar...)
         * możemy definiować WŁASNE taski, wykonujące dowolny kod Groovy/
         * Kotlin. To jedna z największych przewag Gradle nad Mavenem -
         * Maven ograniczony jest do gotowych goali pluginów, a Gradle
         * pozwala napisać "dowolny mini-program" jako task.
         *
         * Podstawowa składnia (Groovy DSL):
         *
         *   tasks.register("printProjectInfo") {
         *       doLast {
         *           println "Projekt: ${project.name}, wersja: ${project.version}"
         *       }
         *   }
         *
         * - doFirst {}  -> kod wykonywany na SAMYM POCZĄTKU wykonania taska
         * - doLast {}   -> kod wykonywany na SAMYM KOŃCU wykonania taska
         *   (najczęściej używane - "właściwa praca" taska)
         * - dependsOn   -> deklaruje, że dany task wymaga wykonania innych
         *   tasków PRZED sobą, np.:
         *
         *   tasks.register("printProjectInfo") {
         *       dependsOn("compileJava")
         *       doLast {
         *           println "Kod skompilowany, projekt: ${project.name}"
         *       }
         *   }
         */

        String customTaskSnippet = """
                tasks.register("printProjectInfo") {
                    dependsOn("compileJava")
                    doFirst {
                        println "Zaczynam zbieranie informacji o projekcie..."
                    }
                    doLast {
                        println "Projekt: ${project.name}, wersja: ${project.version}"
                        println "Kompilacja Javy zostala wczesniej wykonana (dependsOn)."
                    }
                }
                """;

        System.out.println("\n=== CUSTOM TASK (build.gradle) ===");
        System.out.println(customTaskSnippet);
        System.out.println("dependsOn  -> ten task NIE wykona sie, zanim nie wykona sie 'compileJava'.");
        System.out.println("doFirst/doLast -> kod na poczatku/koncu wykonania taska.");

        /*
         * ============================================================
         * 🔹 SOURCE SETS - ŹRÓDŁA POZA main/test
         * ============================================================
         * Gradle domyślnie definiuje dwa "source sety": "main" (kod
         * produkcyjny, src/main/java) i "test" (kod testowy, src/test/java).
         * Source set to logiczna grupa: kod źródłowy + resources +
         * własny classpath.
         *
         * Można zdefiniować DODATKOWE source sety, np. dla testów
         * integracyjnych, osobnych od testów jednostkowych:
         *
         *   sourceSets {
         *       integrationTest {
         *           java.srcDir 'src/integrationTest/java'
         *           resources.srcDir 'src/integrationTest/resources'
         *       }
         *   }
         *
         * To pozwala mieć np. testy jednostkowe (szybkie, uruchamiane
         * przy każdym build) i integracyjne (wolniejsze, uruchamiane
         * osobnym taskiem) w tym samym module, ale rozdzielone.
         */

        String sourceSetsSnippet = """
                sourceSets {
                    integrationTest {
                        java.srcDir 'src/integrationTest/java'
                        resources.srcDir 'src/integrationTest/resources'
                    }
                }
                """;

        System.out.println("\n=== SOURCE SETS (dodatkowy katalog testow integracyjnych) ===");
        System.out.println(sourceSetsSnippet);
        System.out.println("main/test to source sety domyslne - mozna dodawac wlasne (np. integrationTest).");

        /*
         * ============================================================
         * 🔍 MULTI-PROJECT BUILD - WIELE PODPROJEKTOW
         * ============================================================
         * Odpowiednik multi-module builda z Mavena (Lekcja 14), ale
         * z inną terminologią: "podprojekty" (subprojects) zamiast
         * "modułów". Definiuje się je w settings.gradle:
         *
         *   rootProject.name = 'moj-system'
         *   include("core", "app")
         *
         * Każdy podprojekt ma swój WŁASNY build.gradle w podkatalogu
         * o tej samej nazwie (core/build.gradle, app/build.gradle).
         * W GŁÓWNYM build.gradle (root) można konfigurować WSZYSTKIE
         * podprojekty na raz dwoma blokami:
         *
         * - allprojects {}    -> konfiguracja stosowana do WSZYSTKICH
         *   projektów (włącznie z rootem) - np. wspólne repositories.
         * - subprojects {}    -> konfiguracja stosowana WYŁĄCZNIE do
         *   podprojektów (NIE do roota) - np. wspólny plugin "java".
         *
         * Podprojekty mogą zależeć od siebie nawzajem przez notację
         * project(":nazwa"), np. app zależy od core:
         *
         *   dependencies {
         *       implementation project(":core")
         *   }
         */

        String settingsMultiProject = """
                rootProject.name = 'moj-system'
                include("core", "app")
                """;

        String rootBuildGradle = """
                allprojects {
                    repositories {
                        mavenCentral()
                    }
                }

                subprojects {
                    apply plugin: 'java'

                    dependencies {
                        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
                    }
                }
                """;

        String appBuildGradle = """
                plugins {
                    id 'application'
                }

                dependencies {
                    implementation project(":core")
                }

                application {
                    mainClass = 'com.example.app.Main'
                }
                """;

        System.out.println("\n=== MULTI-PROJECT BUILD ===");
        System.out.println("--- settings.gradle (root) ---");
        System.out.println(settingsMultiProject);
        System.out.println("--- build.gradle (root) ---");
        System.out.println(rootBuildGradle);
        System.out.println("--- app/build.gradle (podprojekt 'app', zalezny od 'core') ---");
        System.out.println(appBuildGradle);

        /*
         * ============================================================
         * 🔹 DEPENDENCY INSIGHT - ANALIZA ZALEŻNOŚCI I KONFLIKTY WERSJI
         * ============================================================
         * W dużych projektach różne zależności mogą (transytywnie)
         * ciągnąć RÓŻNE wersje TEJ SAMEJ biblioteki (np. jedna zależność
         * chce gson 2.8.0, inna gson 2.11.0). Gradle domyślnie stosuje
         * strategię "najnowsza wersja wygrywa" (podobnie do Mavenowego
         * "nearest definition wins", ale z innym algorytmem).
         *
         * Komenda "./gradlew dependencies" wypisuje CAŁE drzewo
         * zależności modułu. Komenda "./gradlew dependencyInsight
         * --dependency <nazwa>" pokazuje DOKŁADNIE, skąd pochodzi
         * konkretna zależność i KTÓRA jej wersja została wybrana i
         * DLACZEGO.
         *
         * Poniżej ILUSTRACYJNY przykład (ręcznie napisany String,
         * naśladujący format prawdziwego outputu - NIE jest to
         * faktyczne wywołanie ./gradlew, ten wrapper nie istnieje
         * w tym repo):
         */

        String dependencyInsightExample = """
                > Task :app:dependencyInsight
                com.google.code.gson:gson:2.11.0 (selected by rule)
                   Selected by rule : Gson requested from module-a: 2.8.0 -> 2.11.0 (conflict resolution)

                com.google.code.gson:gson:2.8.0 -> 2.11.0
                \\--- moduleA (requested 2.8.0)
                \\--- moduleB (requested 2.11.0, winner)

                (*) - dependency selected because it is newer than all other requested versions
                """;

        System.out.println("\n=== ILUSTRACYJNY PRZYKLAD './gradlew dependencyInsight --dependency gson' ===");
        System.out.println("(To NIE jest realny output - ten wrapper nie istnieje w tym repo. Format");
        System.out.println(" nasladuje prawdziwy Gradle, wylacznie w celach edukacyjnych.)");
        System.out.println(dependencyInsightExample);

        /*
         * ============================================================
         * 🔍 BUILD CACHE I INCREMENTAL BUILD
         * ============================================================
         * Dwa mechanizmy wydajnościowe, dla których Gradle jest znany:
         *
         * - INCREMENTAL BUILD - Gradle śledzi WEJŚCIA i WYJŚCIA każdego
         *   taska (np. compileJava: wejście = pliki .java, wyjście =
         *   pliki .class). Jeśli od ostatniego builda NIC się nie
         *   zmieniło (te same wejścia, te same wyjścia na dysku), task
         *   jest oznaczany jako "UP-TO-DATE" i NIE jest wykonywany
         *   ponownie - build jest dramatycznie szybszy.
         *
         * - BUILD CACHE - idzie o krok dalej: wyniki tasków (np.
         *   skompilowane .class) mogą być zapamiętane w cache'u NAWET
         *   między różnymi checkoutami/branchami/maszynami (przy
         *   skonfigurowanym zdalnym cache). Jeśli te same wejścia
         *   dadzą (deterministycznie) te same wyjścia, Gradle może
         *   po prostu SKOPIOWAĆ wynik z cache'u, zamiast wykonywać
         *   task od nowa - nawet po "./gradlew clean".
         *
         * Efekt praktyczny: drugi i kolejne buildy tego samego kodu
         * (bez zmian) są bliskie natychmiastowe, bo większość tasków
         * ma status UP-TO-DATE albo FROM-CACHE.
         */

        String incrementalBuildExample = """
                > Task :compileJava UP-TO-DATE
                > Task :processResources UP-TO-DATE
                > Task :classes UP-TO-DATE
                > Task :jar UP-TO-DATE
                > Task :build UP-TO-DATE

                BUILD SUCCESSFUL in 412ms
                5 actionable tasks: 5 up-to-date
                """;

        System.out.println("\n=== ILUSTRACYJNY PRZYKLAD DRUGIEGO BUILDA (incremental build) ===");
        System.out.println("(Znowu ILUSTRACYJNY output - format prawdziwego Gradle, nieodpalony naprawde.)");
        System.out.println(incrementalBuildExample);
        System.out.println("UP-TO-DATE = Gradle wykryl, ze wejscia/wyjscia taska sie nie zmienily -");
        System.out.println("wiec pominal jego wykonanie. To jest sedno incremental build.");

        /*
         * ============================================================
         * 🔹 GRADLE PROPERTIES I ZMIENNE ŚRODOWISKOWE
         * ============================================================
         * Gradle pozwala parametryzować build wieloma sposobami,
         * podobnie do properties/profiles w Mavenie:
         *
         * - gradle.properties  -> plik w korzeniu projektu (albo w
         *   ~/.gradle/gradle.properties dla ustawień globalnych) z
         *   parami klucz=wartość, np. "myapp.version=1.2.3". Dostępne
         *   w build.gradle jako zwykłe zmienne (np. project.myapp.version).
         * - project properties -> przekazywane z linii komend flagą
         *   "-P", np. "./gradlew build -PmyProp=wartosc", odczytywane
         *   w build.gradle jako project.findProperty("myProp").
         * - system properties  -> flaga "-D", np. "-Dorg.gradle.debug=true",
         *   odczytywane jak zwykłe Javowe System.getProperty(...).
         * - environment variables -> zwykłe zmienne środowiskowe systemu
         *   operacyjnego, odczytywane przez System.getenv(...) - Gradle
         *   ich nie "opakowuje" specjalnie, ale to częsty sposób
         *   przekazywania sekretów do builda (patrz też Lekcja 20).
         */

        String gradleProperties = """
                # gradle.properties
                myapp.version=1.2.3
                org.gradle.jvmargs=-Xmx2g
                org.gradle.parallel=true
                org.gradle.caching=true
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson17-gradle");
        Path gradlePropsPath = tempDir.resolve("gradle.properties");
        Files.writeString(gradlePropsPath, gradleProperties);

        System.out.println("\n=== gradle.properties (zapisany na dysku) ===");
        System.out.println("Sciezka: " + gradlePropsPath);
        System.out.println(gradleProperties);
        System.out.println("Uzycie w build.gradle: version = project.property('myapp.version')");
        System.out.println("Uzycie z linii komend: ./gradlew build -PmyProp=wartosc  (project property)");
        System.out.println("Uzycie system property: ./gradlew build -Dorg.gradle.debug=true");
        System.out.println("Zmienna srodowiskowa (w Javie): System.getenv(\"DB_PASSWORD\")");

        Files.deleteIfExists(gradlePropsPath);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Custom tasks (tasks.register, dependsOn, doFirst/doLast) -
         *   Gradle pozwala dopisać DOWOLNY kod jako task, czego Maven
         *   nie umożliwia bez pisania własnego pluginu.
         * - Source sets - main/test domyslnie, mozna dodac wlasne
         *   (np. integrationTest) z osobnymi katalogami zrodel.
         * - Multi-project build: settings.gradle (include), allprojects{}
         *   (wszystkie), subprojects{} (bez roota), project(":nazwa")
         *   do zaleznosci miedzy podprojektami.
         * - dependencyInsight pomaga zrozumiec KTÓRA wersja zaleznosci
         *   zostala wybrana i DLACZEGO przy konflikcie wersji.
         * - Build cache + incremental build (UP-TO-DATE/FROM-CACHE) -
         *   Gradle pomija powtarzanie pracy, ktorej wynik sie nie zmienil.
         * - gradle.properties/project properties (-P)/system properties
         *   (-D)/zmienne srodowiskowe - rozne sposoby parametryzacji builda.
         */

        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }
}
