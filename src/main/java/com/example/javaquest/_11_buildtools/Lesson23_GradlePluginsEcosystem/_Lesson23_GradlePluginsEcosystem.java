package com.example.javaquest._11_buildtools.Lesson23_GradlePluginsEcosystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson23_GradlePluginsEcosystem {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 23: EKOSYSTEM PLUGINOW GRADLE W PRAKTYCE ===");

        /*
         * ============================================================
         * 📦 SHADOW PLUGIN - "FAT JAR" W GRADLE
         * ============================================================
         * Przypomnienie problemu z Lekcji 13 (Maven Shade Plugin): zwykly
         * JAR z Twoim kodem NIE zawiera kodu bibliotek, od ktorych
         * zalezysz (Gson, itp.) - one sa tylko "wskazane" w manifescie
         * i musza byc fizycznie obecne na classpath w runtime. "Fat JAR"
         * (uber-JAR) to JAR zawierajacy WSZYSTKO - Twoj kod + kod
         * WSZYSTKICH zaleznosci - jeden plik, gotowy do "java -jar
         * app.jar" bez zadnych dodatkowych ustawien classpath.
         *
         * W swiecie Gradle to zadanie realizuje PLUGIN SPOLECZNOSCIOWY
         * (nie wbudowany w JDK/Gradle) - Shadow Plugin. Aplikuje się go
         * przez blok "plugins{}" z ID pluginu i wersja (Gradle sam
         * pobiera go z Gradle Plugin Portal - odpowiednika Maven Central,
         * ale dla PLUGINOW, nie bibliotek):
         *
         *   plugins {
         *       id 'java'
         *       id 'com.gradleup.shadow' version '8.3.5'
         *   }
         *
         * Po zaaplikowaniu pojawia sie nowy task "shadowJar" (ZAMIAST
         * zwykłego "jar" do budowania fat JAR-a - "jar" wciaz buduje
         * "cienki" JAR, "shadowJar" buduje "tlusty"). Mozna go
         * skonfigurowac (np. Main-Class w manifescie, ewentualne
         * "relocate" pakietow, zeby uniknac konfliktow nazw klas).
         */

        String shadowPluginSnippet = """
                plugins {
                    id 'java'
                    id 'com.gradleup.shadow' version '8.3.5'
                }

                dependencies {
                    implementation 'com.google.code.gson:gson:2.11.0'
                }

                shadowJar {
                    archiveClassifier.set('all')   // wynikowy plik: app-1.0.0-all.jar
                    manifest {
                        attributes 'Main-Class': 'com.example.App'
                    }
                }
                """;

        System.out.println("\n=== SHADOW PLUGIN - FAT JAR (build.gradle) ===");
        System.out.println(shadowPluginSnippet);
        System.out.println("Task 'shadowJar' (NIE 'jar'!) buduje fat JAR ze wszystkimi zaleznosciami wewnatrz.");
        System.out.println("Odpowiednik: maven-shade-plugin z Lekcji 13 (Maven) - TA SAMA idea, inny mechanizm:");
        System.out.println("Maven: <plugin>maven-shade-plugin</plugin> podwiazany do fazy 'package'.");
        System.out.println("Gradle: dodatkowy, ODDZIELNY task 'shadowJar' (obok zwyklego 'jar') z pluginu spolecznosciowego.");

        /*
         * ============================================================
         * 🔹 ILUSTRACYJNY PRZYKŁAD: "./gradlew shadowJar"
         * ============================================================
         */

        String shadowJarOutput = """
                > Task :compileJava
                > Task :processResources
                > Task :classes
                > Task :shadowJar
                (wynik: build/libs/app-1.0.0-all.jar - 4.2 MB, zawiera gson + Twoj kod)
                > Task :jar
                (wynik: build/libs/app-1.0.0.jar - 12 KB, TYLKO Twoj kod, bez gson)

                BUILD SUCCESSFUL in 2s
                """;

        System.out.println("\n=== ILUSTRACYJNY OUTPUT './gradlew shadowJar' ===");
        System.out.println("(To NIE jest realny output - ten wrapper nie istnieje w tym repo. Wylacznie");
        System.out.println(" do zilustrowania roznicy rozmiarow miedzy 'jar' i 'shadowJar'.)");
        System.out.println(shadowJarOutput);

        /*
         * ============================================================
         * 🔍 PLUGIN org.springframework.boot - bootJar
         * ============================================================
         * Poznany "z lotu ptaka" w Lekcji 16 - teraz blizej. Plugin
         * "org.springframework.boot" (uzywany RAZEM z
         * "io.spring.dependency-management", ktory zarzadza wersjami -
         * odpowiednik BOM-a/parenta z Lekcji 18/pom.xml tego kursu)
         * DODAJE task "bootJar" - Spring Boot ma WLASNY mechanizm fat
         * JAR-a (nie potrzebuje Shadow Plugin!). "bootJar" tworzy
         * specyficzna strukture JAR-a (BOOT-INF/classes, BOOT-INF/lib) z
         * WLASNYM "launcherem" (JarLauncher), ktory umie odpalic
         * aplikacje mimo tej niestandardowej struktury.
         *
         * "bootJar" ZASTĘPUJE zwykly task "jar" (a nie dodaje sie do
         * niego OBOK, jak "shadowJar" robi to wzgledem "jar") - domyslnie
         * "jar" jest WYLACZONY (enabled = false) w projekcie ze
         * pluginem Spring Boot, bo zwykly, "cienki" JAR Spring Boot
         * aplikacji i tak nie da sie odpalic bez fat JAR-a (Spring Boot
         * NIE ma "trybu cienkiego JAR-a + reczny classpath" jak zwykla
         * aplikacja Java).
         */

        String springBootPluginSnippet = """
                plugins {
                    id 'java'
                    id 'org.springframework.boot' version '3.4.4'
                    id 'io.spring.dependency-management' version '1.1.6'
                }

                dependencies {
                    implementation 'org.springframework.boot:spring-boot-starter-web'
                }

                bootJar {
                    archiveFileName = 'moja-aplikacja.jar'
                    mainClass = 'com.example.MojaAplikacjaApplication'
                }

                jar {
                    enabled = false   // Spring Boot: 'cienki' jar i tak sie nie odpali - wylaczamy go
                }
                """;

        System.out.println("\n=== PLUGIN org.springframework.boot - bootJar (build.gradle) ===");
        System.out.println(springBootPluginSnippet);
        System.out.println("bootJar ZASTEPUJE 'jar' (nie dziala OBOK niego, jak shadowJar) - Spring Boot ma");
        System.out.println("WLASNY mechanizm fat JAR-a (BOOT-INF/classes, BOOT-INF/lib + JarLauncher).");
        System.out.println("Analogia Maven: spring-boot-maven-plugin z pom.xml tego kursu robi TO SAMO");
        System.out.println("(zobacz <plugin><artifactId>spring-boot-maven-plugin</artifactId> w pom.xml).");

        /*
         * ============================================================
         * 🔍 PLUGIN JAKOSCI KODU: CHECKSTYLE
         * ============================================================
         * Poza "buduj i pakuj", pluginy Gradle czesto sluza do
         * AUTOMATYCZNEJ kontroli jakosci/stylu kodu - integrowanej
         * DIRECTLY w proces budowania (nie jako osobny, "reczny" krok).
         *
         * Checkstyle - klasyczny, konfigurowalny linter stylu kodu
         * (nazewnictwo, dlugosc linii, importy, brakujace javadoc...).
         * Po dodaniu pluginu "checkstyle" pojawia sie task
         * "checkstyleMain" (dla src/main) i "checkstyleTest" (dla
         * src/test), ZAZWYCZAJ podwiazane do standardowego taska "check"
         * (razem z testami i - jesli dodany - jacocoTestCoverageVerification
         * z Lekcji 19) - tworzac JEDEN, zbiorczy "quality gate".
         *
         * Checkstyle wymaga pliku konfiguracyjnego z REGULAMI (najczesciej
         * config/checkstyle/checkstyle.xml) - ponizej minimalny przyklad.
         */

        String checkstylePluginSnippet = """
                plugins {
                    id 'java'
                    id 'checkstyle'
                }

                checkstyle {
                    toolVersion = '10.18.1'
                    configFile = file('config/checkstyle/checkstyle.xml')
                    maxWarnings = 0     // 0 ostrzezen tolerowanych - kazde ostrzezenie = blad builda
                }

                check {
                    dependsOn checkstyleMain, checkstyleTest
                }
                """;

        String checkstyleXmlMinimal = """
                <?xml version="1.0"?>
                <!DOCTYPE module PUBLIC
                    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
                    "https://checkstyle.org/dtds/configuration_1_3.dtd">
                <module name="Checker">
                    <module name="TreeWalker">
                        <module name="UnusedImports"/>
                        <module name="LineLength">
                            <property name="max" value="120"/>
                        </module>
                    </module>
                </module>
                """;

        System.out.println("\n=== PLUGIN CHECKSTYLE - JAKOSC KODU (build.gradle + config) ===");
        System.out.println(checkstylePluginSnippet);
        System.out.println("--- config/checkstyle/checkstyle.xml (minimalny) ---");
        System.out.println(checkstyleXmlMinimal);
        System.out.println("checkstyleMain/checkstyleTest -> podwiazane do 'check' -> JEDEN quality gate razem");
        System.out.println("z testami (Lekcja 19) i pokryciem kodu (JaCoCo, Lekcja 19).");

        /*
         * ============================================================
         * 📌 GRADLE PLUGIN PORTAL vs MAVEN CENTRAL - SKĄD BIORĄ SIĘ PLUGINY
         * ============================================================
         * Warto odnotowac ROZROZNIENIE: zaleznosci APLIKACJI (implementation
         * 'g:a:v') sa rozwiazywane z repozytoriow skonfigurowanych w
         * "repositories{}" (typowo mavenCentral()) - a PLUGINY (id '...'
         * version '...' w bloku "plugins{}") sa domyslnie rozwiazywane z
         * ODDZIELNEGO repozytorium: Gradle Plugin Portal
         * (plugins.gradle.org) - osobna infrastruktura, osobna
         * "wyszukiwarka" pluginow spolecznosciowych.
         */

        System.out.println("\n=== SKAD BIORA SIE PLUGINY: GRADLE PLUGIN PORTAL ===");
        System.out.println("Zaleznosci aplikacji (implementation)  -> repositories{} (typowo mavenCentral()).");
        System.out.println("Pluginy (plugins{} id '...' version)   -> Gradle Plugin Portal (plugins.gradle.org),");
        System.out.println("                                          OSOBNE repozytorium, specyficzne dla pluginow.");

        // Zapisujemy jeden z przykladow na dysk, jak w poprzednich lekcjach o Gradle.
        Path tempDir = Files.createTempDirectory("javaquest-lesson20-plugins");
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, shadowPluginSnippet);
        System.out.println("\n(Przykladowy build.gradle z Shadow Plugin zapisany tymczasowo w: " + buildGradlePath + ")");
        Files.deleteIfExists(buildGradlePath);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Shadow Plugin (com.gradleup.shadow) - task 'shadowJar' buduje
         *   fat JAR OBOK zwyklego 'jar' - odpowiednik maven-shade-plugin.
         * - org.springframework.boot - task 'bootJar' ZASTEPUJE 'jar',
         *   WLASNY mechanizm fat JAR-a Spring Boot (BOOT-INF struktura) -
         *   odpowiednik spring-boot-maven-plugin z pom.xml tego kursu.
         * - Checkstyle - plugin jakosci kodu, taski checkstyleMain/
         *   checkstyleTest podwiazane do 'check' - quality gate na styl kodu.
         * - Pluginy pochodza z INNEGO repozytorium (Gradle Plugin Portal)
         *   niz zaleznosci aplikacji (Maven Central via repositories{}).
         */

        System.out.println("\n=== KONIEC LEKCJI 23 ===");
    }
}
