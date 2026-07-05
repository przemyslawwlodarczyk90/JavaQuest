package com.example.javaquest._11_buildtools.Lesson24_GradlePublishing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson24_GradlePublishing {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 24: GRADLE - PUBLIKOWANIE ARTEFAKTOW (maven-publish) ===");

        /*
         * ============================================================
         * 📦 DLACZEGO "maven-publish" A NIE "gradle-publish"?
         * ============================================================
         * Zaskakujaca dla poczatkujacych nazwa - plugin do publikowania
         * artefaktow w Gradle nazywa sie "maven-publish"! To NIE oznacza,
         * ze publikujemy DO Mavena - oznacza, ze uzywamy FORMATU
         * repozytorium Maven (ten sam format, w ktorym leza wszystkie
         * biblioteki na Maven Central, ktore pobieramy przez
         * "implementation 'g:a:v'" w KAZDEJ lekcji tego rozdzialu).
         *
         * To bardzo wazna wskazowka o calym ekosystemie: MAVEN REPOSITORY
         * FORMAT (struktura katalogow groupId/artifactId/version/ +
         * plik .pom z metadanymi) jest UNIWERSALNYM standardem, z ktorego
         * korzystaja WSZYSTKIE narzedzia (Maven, Gradle, Ant+Ivy z
         * Lekcji 09) - nie tylko Maven "jako program". Gradle publikuje
         * SWOJE artefakty w TYM SAMYM formacie, zeby byly pobieralne
         * przez KAZDE z tych narzedzi.
         */

        System.out.println("\n=== CZYM JEST 'maven-publish' ===");
        System.out.println("'maven-publish' = plugin publikujacy artefakty w FORMACIE repozytorium Maven -");
        System.out.println("TYM SAMYM, z ktorego Gradle/Maven/Ant+Ivy POBIERAJA kazda zaleznosc.");
        System.out.println("To NIE jest 'publikowanie do Mavena' - to uniwersalny STANDARD formatu repo.");

        /*
         * ============================================================
         * 🔹 KONFIGURACJA PLUGINU I PUBLIKACJI
         * ============================================================
         * Blok "publishing { }" ma dwie kluczowe sekcje:
         *
         * - publications { }  -> CO publikujemy - definiujemy "publikacje"
         *   (najczesciej jedna, typu "MavenPublication"), ktora mowi
         *   Gradle: "wez komponent 'java' (czyli JAR + wygenerowany
         *   automatycznie plik .pom z metadanymi na podstawie
         *   dependencies{})" i opcjonalnie dodajemy dodatkowe artefakty
         *   (np. JAR z kodem zrodlowym, JAR z javadoc).
         * - repositories { }  -> GDZIE publikujemy - jedno lub wiecej
         *   miejsc docelowych (patrz nizej: lokalne vs zdalne).
         */

        String publishingPluginSnippet = """
                plugins {
                    id 'java'
                    id 'maven-publish'
                }

                group = 'com.example'
                version = '1.0.0'

                publishing {
                    publications {
                        mavenJava(MavenPublication) {
                            from components.java   // JAR + automatycznie wygenerowany .pom z dependencies{}

                            pom {
                                name = 'Moja Biblioteka'
                                description = 'Przykladowa biblioteka publikowana przez Gradle'
                                url = 'https://example.com/moja-biblioteka'
                            }
                        }
                    }

                    repositories {
                        mavenLocal()   // publikuj do lokalnego ~/.m2/repository (jak 'mvn install')

                        maven {
                            name = 'CompanyRepo'
                            url = uri('https://repo.example.com/releases')
                            credentials {
                                username = System.getenv('REPO_USER')
                                password = System.getenv('REPO_PASSWORD')
                            }
                        }
                    }
                }
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson21-publish");
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, publishingPluginSnippet);

        System.out.println("\n=== KONFIGURACJA PUBLIKOWANIA (build.gradle, zapisany na dysku) ===");
        System.out.println("Sciezka: " + buildGradlePath);
        System.out.println(publishingPluginSnippet);

        /*
         * ============================================================
         * 🔍 LOKALNE (mavenLocal) vs ZDALNE REPOZYTORIUM
         * ============================================================
         * - mavenLocal()  -> publikuje do TWOJEGO lokalnego repozytorium
         *   Maven (~/.m2/repository - TEN SAM katalog, ktorego uzywa
         *   Maven poleceniem "mvn install"!). Odpowiednik Gradle:
         *   "./gradlew publishToMavenLocal". Uzywane GLOWNIE podczas
         *   developmentu - testujesz lokalnie, jak Twoja biblioteka
         *   zachowuje sie jako zaleznosc w INNYM projekcie, bez
         *   publikowania jej "na zewnatrz" (widoczne tylko na Twoim
         *   komputerze).
         * - repozytorium ZDALNE (maven { url = ... }) -> publikuje do
         *   firmowego (np. Nexus/Artifactory) albo publicznego
         *   repozytorium - widoczne dla INNYCH programistow/zespolow/CI.
         *   WYMAGA credentials (login/haslo/token) - a te credentials
         *   NIGDY nie sa zaszyte w build.gradle (patrz Lekcja 20 -
         *   sekcja o sekretach z ROZDZIALU o build tools in practice) -
         *   zawsze przez zmienne srodowiskowe (System.getenv(...), jak
         *   w przykladzie wyzej) albo gradle.properties poza repo Git.
         */

        System.out.println("\n=== LOKALNE vs ZDALNE REPOZYTORIUM ===");
        System.out.println("mavenLocal()  -> ~/.m2/repository (TEN SAM katalog co 'mvn install'!) - do testow lokalnych.");
        System.out.println("maven { url } -> firmowe/publiczne repo (Nexus/Artifactory) - widoczne dla innych,");
        System.out.println("                  WYMAGA credentials z ZMIENNYCH SRODOWISKOWYCH (nigdy zaszytych w pliku!).");

        /*
         * ============================================================
         * 🔹 WERSJONOWANIE: KWALIFIKATORY SNAPSHOT-PODOBNE W GRADLE
         * ============================================================
         * Maven ma FORMALNY, wbudowany koncept "-SNAPSHOT" (Lekcja 20/25
         * tego rozdzialu) - specjalnie rozpoznawany przez caly mechanizm
         * repozytoriow (zawsze re-sprawdzany, nie cache'owany trwale).
         *
         * Gradle NIE MA tak formalnego mechanizmu - "-SNAPSHOT" (albo
         * inny kwalifikator, np. "-alpha", "-rc1") to zwykla KONWENCJA
         * NAZEWNICZA w Stringu wersji, BEZ wbudowanej logiki cache'owania
         * jak w Mavenie (choc repozytoria - np. Nexus - CZESTO
         * implementuja podobne zachowanie dla wersji zawierajacych
         * "SNAPSHOT" niezaleznie od narzedzia budujacego, wiec w
         * praktyce konwencja wciaz "dziala" na poziomie repozytorium).
         *
         * Typowy wzorzec w Gradle: WERSJA jest wyliczana DYNAMICZNIE na
         * podstawie tego, czy build dzieje sie na branchu release'owym
         * czy nie:
         */

        String versioningSnippet = """
                def isReleaseBuild = System.getenv('CI_BRANCH') == 'main'

                version = isReleaseBuild ? '1.2.0' : '1.2.0-SNAPSHOT'

                // Alternatywnie - wersja z numerem builda CI (patrz Lekcja 25 - numer builda != wersja aplikacji):
                // version = '1.2.0-SNAPSHOT+build.' + (System.getenv('CI_BUILD_NUMBER') ?: 'local')
                """;

        System.out.println("\n=== WERSJONOWANIE W GRADLE (build.gradle) ===");
        System.out.println(versioningSnippet);
        System.out.println("Gradle nie ma FORMALNEGO mechanizmu SNAPSHOT jak Maven - to KONWENCJA nazewnicza,");
        System.out.println("choc wiele repozytoriow (Nexus/Artifactory) i tak ja honoruje po stronie serwera.");

        /*
         * ============================================================
         * 🔍 ILUSTRACYJNY PRZYKŁAD: "./gradlew publish"
         * ============================================================
         */

        String publishOutput = """
                > Task :compileJava
                > Task :jar
                > Task :generatePomFileForMavenJavaPublication
                > Task :publishMavenJavaPublicationToMavenLocalRepository
                (opublikowano do: ~/.m2/repository/com/example/moja-biblioteka/1.0.0/)
                > Task :publishMavenJavaPublicationToCompanyRepoRepository
                (opublikowano do: https://repo.example.com/releases/com/example/moja-biblioteka/1.0.0/)

                BUILD SUCCESSFUL in 3s
                5 actionable tasks: 5 executed
                """;

        System.out.println("\n=== ILUSTRACYJNY OUTPUT './gradlew publish' ===");
        System.out.println("(To NIE jest realny output - ten wrapper nie istnieje w tym repo. Wylacznie");
        System.out.println(" do zilustrowania, co faktycznie sie dzieje przy publikowaniu do 2 repozytoriow naraz.)");
        System.out.println(publishOutput);

        Files.deleteIfExists(buildGradlePath);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Plugin 'maven-publish' publikuje artefakty w UNIWERSALNYM
         *   FORMACIE repozytorium Maven (uzywanym przez Maven, Gradle,
         *   Ant+Ivy) - nazwa NIE oznacza "publikowania do Mavena".
         * - publishing { publications { } repositories { } } - publications
         *   definiuje CO (JAR + auto-generowany .pom z dependencies{}),
         *   repositories definiuje GDZIE.
         * - mavenLocal() (lokalne ~/.m2/repository, jak 'mvn install')
         *   vs zdalne repo (Nexus/Artifactory, wymaga credentials z
         *   zmiennych srodowiskowych, NIGDY zaszytych w build.gradle).
         * - Gradle NIE MA formalnego mechanizmu SNAPSHOT jak Maven -
         *   "-SNAPSHOT" to konwencja nazewnicza, czesto honorowana przez
         *   samo repozytorium (Nexus/Artifactory), a nie przez Gradle
         *   "z definicji".
         */

        System.out.println("\n=== KONIEC LEKCJI 24 ===");
    }
}
