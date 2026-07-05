package com.example.javaquest._11_buildtools.Lesson16_GradleBasics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson16_GradleBasics {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 16: GRADLE - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST GRADLE?
         * ============================================================
         * Gradle to trzecie (po Ant i Mavenie) narzędzie do budowania
         * projektów Java, które poznajemy w tym rozdziale. Gradle łączy
         * pomysły z obu poprzednich narzędzi:
         * - jak Maven - ma wbudowane KONWENCJE (standardowa struktura
         *   katalogów, gotowe pluginy do typowych zadań),
         * - jak Ant - daje PROGRAMOWALNĄ elastyczność (prawdziwy język
         *   programowania w plikach konfiguracyjnych, nie tylko XML).
         *
         * Kluczowa różnica: konfigurację Gradle piszemy w DSL (Domain
         * Specific Language - języku dedykowanym danej domenie) opartym
         * na Groovy albo Kotlinie, a NIE w XML. To sprawia, że pliki
         * konfiguracyjne Gradle są krótsze, bardziej czytelne i można
         * w nich pisać prawdziwą logikę (zmienne, warunki, pętle,
         * własne funkcje) bez naginania XML-a do tego celu.
         *
         * Dwa główne pliki konfiguracyjne projektu Gradle:
         * - build.gradle (albo build.gradle.kts) - "co i jak budować"
         *   dla KONKRETNEGO modułu/projektu (pluginy, zależności, taski) -
         *   odpowiednik pom.xml z Mavena.
         * - settings.gradle (albo settings.gradle.kts) - definiuje, jakie
         *   moduły/podprojekty WCHODZĄ w skład builda (nazwę projektu
         *   głównego oraz - przy multi-project buildach, patrz Lekcja 17 -
         *   listę podprojektów). Odpowiednika w Mavenie nie ma 1:1 (to
         *   trochę jak <modules> w pom.xml projektu-rodzica).
         */

        System.out.println("\n=== CZYM JEST GRADLE ===");
        System.out.println("Gradle = konwencje (jak Maven) + programowalny DSL (jak elastycznosc Anta).");
        System.out.println("build.gradle    - co i jak budowac (pluginy, zaleznosci, taski) - jak pom.xml.");
        System.out.println("settings.gradle - jakie moduly wchodza w sklad builda.");

        /*
         * ============================================================
         * 🔹 GROOVY DSL vs KOTLIN DSL
         * ============================================================
         * Gradle obsługuje DWA języki do pisania konfiguracji:
         *
         * - Groovy DSL (plik build.gradle) - historycznie pierwszy,
         *   wciąż bardzo popularny, składnia "skryptowa", bardzo zwarta.
         * - Kotlin DSL (plik build.gradle.kts) - nowszy, statycznie
         *   typowany (podpowiedzi w IDE, wykrywanie błędów przy pisaniu),
         *   coraz częściej domyślny wybór w nowych projektach.
         *
         * Oba opisują TĘ SAMĄ rzecz - różni je tylko składnia. Poniżej
         * ten sam fragment (plugin + jedna zależność) w obu wariantach,
         * czysto ilustracyjnie (do porównania, NIE zapisywane na dysk):
         */

        String groovySnippet = """
                plugins {
                    id 'java'
                }

                dependencies {
                    implementation 'com.google.code.gson:gson:2.11.0'
                }
                """;

        String kotlinSnippet = """
                plugins {
                    java
                }

                dependencies {
                    implementation("com.google.code.gson:gson:2.11.0")
                }
                """;

        System.out.println("\n=== GROOVY DSL (build.gradle) vs KOTLIN DSL (build.gradle.kts) ===");
        System.out.println("--- Groovy DSL ---");
        System.out.println(groovySnippet);
        System.out.println("--- Kotlin DSL ---");
        System.out.println(kotlinSnippet);
        System.out.println("Ta sama informacja, dwie skladnie. W tym kursie uzywamy Groovy DSL");
        System.out.println("(build.gradle) jako domyslnego wariantu - jest krotszy i wciaz najczesciej");
        System.out.println("spotykany w istniejacych projektach.");

        /*
         * ============================================================
         * 🔍 GRADLE WRAPPER - GWIZDEK GWARANTUJĄCY TĘ SAMĄ WERSJĘ
         * ============================================================
         * Gradle Wrapper to zestaw plików (gradlew, gradlew.bat,
         * gradle/wrapper/gradle-wrapper.jar, gradle/wrapper/gradle-wrapper.properties)
         * commitowanych DO REPOZYTORIUM projektu. Ich zadaniem jest
         * automatyczne pobranie i użycie DOKŁADNIE tej wersji Gradle,
         * której projekt wymaga - bez konieczności ręcznej instalacji
         * Gradle na każdej maszynie.
         *
         * - gradlew      -> skrypt startowy dla Linux/macOS
         * - gradlew.bat  -> skrypt startowy dla Windows
         * - gradle-wrapper.properties -> wskazuje URL i wersję Gradle
         *   do pobrania (np. distributionUrl=...gradle-8.10-bin.zip)
         * - gradle-wrapper.jar -> mały bootstrap, który pobiera i odpala
         *   właściwy Gradle
         *
         * DLACZEGO wrapper jest OBOWIĄZKOWY w praktyce (nie opcjonalny):
         * 1. Reprodukowalność - każdy programista (i każdy serwer CI)
         *    używa TEJ SAMEJ wersji Gradle, niezależnie od tego, co ma
         *    zainstalowane globalnie na swoim komputerze.
         * 2. Zero-config onboarding - nowy członek zespołu klonuje repo
         *    i odpala "./gradlew build" - nie musi NIC instalować ręcznie.
         * 3. Łatwa aktualizacja wersji Gradle dla całego zespołu na raz -
         *    zmiana jednej linii w gradle-wrapper.properties i commit.
         *
         * ⚠️ To repozytorium (JavaQuest) NIE MA wrappera Gradle - kurs
         * jest budowany Mavenem (mvnw/mvnw.cmd, patrz pom.xml w korzeniu).
         * W tej lekcji jedynie OPISUJEMY rolę wrappera koncepcyjnie -
         * nic tutaj nie instalujemy ani nie odpalamy prawdziwego Gradle.
         */

        System.out.println("\n=== GRADLE WRAPPER ===");
        System.out.println("gradlew / gradlew.bat + gradle/wrapper/*  ->  commitowane do repo.");
        System.out.println("Gwarantuja te sama wersje Gradle u kazdego programisty i na CI,");
        System.out.println("bez recznej instalacji Gradle. Analogia: to co mvnw/mvnw.cmd dla Mavena");
        System.out.println("(ten kurs wlasnie tak jest budowany - zobacz mvnw.cmd w korzeniu repo).");

        /*
         * ============================================================
         * 🔹 STRUKTURA PROJEKTU GRADLE
         * ============================================================
         * Domyślna struktura katalogów (plugin "java") jest taka sama
         * jak w Mavenie - to nie przypadek, Gradle świadomie przejął
         * tę konwencję:
         *
         *   moj-projekt/
         *   ├── build.gradle
         *   ├── settings.gradle
         *   ├── gradlew, gradlew.bat, gradle/wrapper/...
         *   ├── src/
         *   │   ├── main/
         *   │   │   ├── java/          <- kod zrodlowy
         *   │   │   └── resources/     <- pliki .properties, .xml...
         *   │   └── test/
         *   │       ├── java/          <- testy
         *   │       └── resources/
         *   └── build/                 <- WYNIKI budowania (odpowiednik target/ w Mavenie)
         *
         * Jedyna widoczna różnica względem Mavena: katalog wyników to
         * "build/" (nie "target/"). Oba są generowane i oba trafiają
         * do .gitignore.
         */

        System.out.println("\n=== STRUKTURA PROJEKTU GRADLE ===");
        System.out.println("src/main/java, src/main/resources, src/test/java, src/test/resources - jak w Mavenie.");
        System.out.println("build/  - katalog wynikowy (odpowiednik target/ w Mavenie) - nie commitujemy.");

        /*
         * ============================================================
         * 🔍 PLUGINS - CO WŁĄCZAJĄ DO BUILDU
         * ============================================================
         * Gradle sam z siebie "nic nie umie" - cała funkcjonalność
         * (kompilacja Javy, testy, pakowanie do JAR-a, uruchamianie
         * aplikacji...) pochodzi z PLUGINÓW dodawanych w blogu "plugins{}".
         *
         * - "java"                          -> dodaje taski compileJava,
         *   test, jar, javadoc... oraz konwencję src/main/java
         * - "application"                   -> rozszerza "java" o
         *   uruchamianie aplikacji (task "run") i budowanie
         *   dystrybucyjnych archiwów (mainClass, distZip, distTar)
         * - "war"                            -> dla aplikacji webowych,
         *   pakuje do .war (jak <packaging>war</packaging> w Mavenie)
         * - "org.springframework.boot"       -> plugin społecznościowy
         *   (Spring Boot) - buduje "fat jar" ze wszystkimi zależnościami,
         *   analogicznie do spring-boot-maven-plugin z pom.xml tego kursu
         */

        System.out.println("\n=== PLUGINY ===");
        System.out.println("java                     - kompilacja, testy, jar, javadoc, konwencja katalogow");
        System.out.println("application              - + task 'run', dystrybucyjne archiwa, mainClass");
        System.out.println("war                       - pakowanie do .war (aplikacje webowe)");
        System.out.println("org.springframework.boot  - 'fat jar' Spring Boot (jak spring-boot-maven-plugin)");

        /*
         * ============================================================
         * 🔹 TASKS - JEDNOSTKI PRACY W GRADLE
         * ============================================================
         * Gradle nie ma "faz cyklu życia" tak sztywnych jak Maven -
         * podstawową jednostką pracy jest TASK (odpowiednik targetu
         * w Ancie, ale z automatycznie wykrywanymi zależnościami między
         * taskami na podstawie tego, CZEGO potrzebują wejścia/wyjścia).
         *
         * Najważniejsze wbudowane taski (z pluginu "java"/"application"):
         * - clean         -> usuwa katalog build/
         * - compileJava   -> kompiluje src/main/java -> build/classes
         * - test          -> odpala testy jednostkowe (JUnit domyślnie)
         * - jar           -> pakuje skompilowany kod do .jar w build/libs
         * - build         -> "duży" task zbiorczy - wykonuje m.in. compileJava,
         *                    test, jar (odpowiednik "mvn package" + testy)
         * - run            -> (z pluginu "application") kompiluje i odpala
         *                    main() zdefiniowaną w mainClass
         *
         * Taski mają między sobą zależności (np. "jar" zależy od
         * "compileJava" - Gradle wie, że musi najpierw skompilować kod,
         * zanim może go spakować) - podobnie do <target depends="...">
         * w Ancie, ale te zależności są w dużej mierze DOMYŚLNE, bo
         * pochodzą z konwencji pluginu, nie trzeba ich ręcznie wypisywać.
         */

        System.out.println("\n=== TASKI (wbudowane, z pluginu java/application) ===");
        String[] taskNames = {"clean", "compileJava", "test", "jar", "build", "run"};
        String[] taskDescs = {
                "usuwa katalog build/",
                "kompiluje src/main/java -> build/classes",
                "odpala testy jednostkowe",
                "pakuje skompilowany kod do .jar w build/libs",
                "task zbiorczy: compileJava + test + jar (i inne)",
                "(plugin 'application') kompiluje i odpala main() z mainClass"
        };
        for (int i = 0; i < taskNames.length; i++) {
            System.out.println(taskNames[i] + " -> " + taskDescs[i]);
        }

        /*
         * ============================================================
         * 🔍 DEPENDENCIES - KONFIGURACJE ZALEŻNOŚCI
         * ============================================================
         * Podobnie jak Maven ma <scope> (compile, test, provided...),
         * Gradle ma "konfiguracje zależności" - słowa kluczowe określające,
         * GDZIE i JAK dana zależność jest widoczna:
         *
         * - implementation   -> najczęściej używana. Zależność potrzebna
         *   do kompilacji i działania modułu, ale NIE eksponowana modułom,
         *   które zależą od TEGO modułu (lepsza izolacja, szybsze buildy) -
         *   odpowiednik scope "compile" w Mavenie, ale bardziej restrykcyjny.
         * - api              -> (z pluginu "java-library") jak implementation,
         *   ale zależność JEST eksponowana dalej - odpowiednik klasycznego
         *   Mavenowego "compile" bez ograniczeń.
         * - compileOnly      -> potrzebna tylko do KOMPILACJI, nie trafia
         *   do runtime (np. adnotacje przetwarzane w compile-time) -
         *   odpowiednik scope "provided" w Mavenie.
         * - runtimeOnly      -> odwrotnie: potrzebna tylko w RUNTIME,
         *   nie do kompilacji (np. sterownik JDBC dobierany dynamicznie).
         * - testImplementation -> jak implementation, ale widoczna TYLKO
         *   dla kodu testowego (src/test/java) - odpowiednik scope "test".
         */

        System.out.println("\n=== KONFIGURACJE ZALEZNOSCI ===");
        System.out.println("implementation      - kompilacja + runtime modulu, NIE eksponowane dalej (jak 'compile', ale wezej)");
        System.out.println("api                 - jak implementation, ALE eksponowane modulom zaleznym (klasyczny 'compile')");
        System.out.println("compileOnly         - tylko do kompilacji, nie w runtime (jak Mavenowy 'provided')");
        System.out.println("runtimeOnly         - tylko w runtime, nie do kompilacji");
        System.out.println("testImplementation  - tylko dla kodu testowego (jak scope 'test')");

        /*
         * ============================================================
         * 📌 GENEROWANIE REALNEGO build.gradle + settings.gradle
         * ============================================================
         * Tak jak w lekcjach o Mavenie (11-15) - Gradle nie ma prostego
         * API do "wbudowania" silnika w JVM tak jak Ant, a odpalanie
         * ./gradlew z wewnątrz demo byłoby kruche i wymagałoby dodania
         * wrappera do tego repo (czego świadomie nie robimy, patrz
         * CLAUDE.md, sekcja _11_buildtools). Dlatego GENERUJEMY realną
         * treść plików konfiguracyjnych, zapisujemy na dysk i wyjaśniamy -
         * bez faktycznego odpalania ./gradlew.
         */

        String buildGradle = """
                plugins {
                    id 'java'
                    id 'application'
                }

                group = 'com.example'
                version = '1.0.0'

                repositories {
                    mavenCentral()
                }

                dependencies {
                    implementation 'com.google.code.gson:gson:2.11.0'
                    compileOnly 'org.projectlombok:lombok:1.18.34'
                    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
                }

                application {
                    mainClass = 'com.example.hello.Main'
                }

                test {
                    useJUnitPlatform()
                }
                """;

        String settingsGradle = """
                rootProject.name = 'hello-gradle'
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson16-gradle");
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Path settingsGradlePath = tempDir.resolve("settings.gradle");
        Files.writeString(buildGradlePath, buildGradle);
        Files.writeString(settingsGradlePath, settingsGradle);

        System.out.println("\n=== WYGENEROWANY settings.gradle (zapisany na dysku) ===");
        System.out.println("Sciezka: " + settingsGradlePath);
        System.out.println(settingsGradle);

        System.out.println("=== WYGENEROWANY build.gradle (zapisany na dysku) ===");
        System.out.println("Sciezka: " + buildGradlePath);
        System.out.println(buildGradle);

        Files.deleteIfExists(buildGradlePath);
        Files.deleteIfExists(settingsGradlePath);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Gradle = konwencje Mavena + programowalny DSL (Groovy/Kotlin).
         * - build.gradle (co/jak budowac) + settings.gradle (jakie moduly
         *   wchodza w sklad builda).
         * - Gradle Wrapper (gradlew/gradlew.bat) - obowiazkowy w praktyce,
         *   gwarantuje ta sama wersje Gradle wszedzie (jak mvnw w tym kursie).
         * - Struktura katalogow taka jak w Mavenie, tylko wyniki w build/
         *   (nie target/).
         * - Pluginy (java, application, war, org.springframework.boot)
         *   dodaja funkcjonalnosc - Gradle "goly" nic nie umie.
         * - Taski (clean, compileJava, test, jar, build, run) to jednostki
         *   pracy z automatycznie wykrywanymi zaleznosciami.
         * - Konfiguracje zaleznosci (implementation/api/compileOnly/
         *   runtimeOnly/testImplementation) okreslaja widocznosc zaleznosci -
         *   podobnie do <scope> w Mavenie, ale bardziej precyzyjnie.
         */

        System.out.println("=== KONIEC LEKCJI 16 ===");
    }
}
