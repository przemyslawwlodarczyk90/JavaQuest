package com.example.javaquest._11_buildtools.Lesson17_MavenPublishing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson17_MavenPublishing {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 17: MAVEN - PUBLIKOWANIE ARTEFAKTOW ===");
        System.out.println("Lesson11 poznal 'mvn install' (kopia do ~/.m2 na TWOIM komputerze).");
        System.out.println("Ta lekcja idzie dalej: jak wyslac artefakt na ZDALNE repozytorium, zeby inni mogli go pobrac.");

        Path tempDir = Files.createTempDirectory("javaquest-lesson17-maven");

        /*
         * ============================================================
         * 📦 distributionManagement - GDZIE PUBLIKOWAC
         * ============================================================
         * Sekcja <distributionManagement> w pom.xml mowi Mavenowi, POD
         * JAKI adres wyslac artefakt przy "mvn deploy" (w odroznieniu od
         * "mvn install", ktory zawsze celuje w lokalne ~/.m2/repository).
         * Typowo definiuje sie DWA repozytoria - jedno dla wersji
         * SNAPSHOT (niestabilne, czeste buildy), drugie dla wersji
         * RELEASE (stabilne, wydane).
         */
        String distMgmtSnippet = """
                <distributionManagement>
                    <repository>
                        <id>company-releases</id>
                        <url>https://repo.example.com/releases</url>
                    </repository>
                    <snapshotRepository>
                        <id>company-snapshots</id>
                        <url>https://repo.example.com/snapshots</url>
                    </snapshotRepository>
                </distributionManagement>
                """;
        System.out.println("\n=== distributionManagement W pom.xml ===");
        System.out.println(distMgmtSnippet);
        System.out.println("Maven SAM wybiera repository/snapshotRepository na podstawie wersji artefaktu -");
        System.out.println("'1.0.0' -> repository (releases), '1.0.0-SNAPSHOT' -> snapshotRepository.");

        /*
         * ============================================================
         * 🔹 settings.xml - POSWIADCZENIA (NIGDY W pom.xml!)
         * ============================================================
         * pom.xml jest zwykle w repozytorium Git, wiec NIE MOZE
         * zawierac hasel/tokenow - trafiloby to do historii commitow.
         * Poswiadczenia do repozytoriow zdalnych zyja w
         * ~/.m2/settings.xml (plik LOKALNY, poza kontrola wersji), w
         * sekcji <servers>. <id> servera w settings.xml MUSI byc
         * IDENTYCZNE jak <id> repository/snapshotRepository w pom.xml -
         * to po tym Maven je ze soba laczy.
         */
        String settingsXmlSnippet = """
                <!-- ~/.m2/settings.xml - NIGDY nie commituj tego pliku do repo! -->
                <settings>
                    <servers>
                        <server>
                            <id>company-releases</id>
                            <username>${env.NEXUS_USER}</username>
                            <password>${env.NEXUS_PASSWORD}</password>
                        </server>
                        <server>
                            <id>company-snapshots</id>
                            <username>${env.NEXUS_USER}</username>
                            <password>${env.NEXUS_PASSWORD}</password>
                        </server>
                    </servers>
                </settings>
                """;
        Path settingsPath = tempDir.resolve("settings.xml.example");
        Files.writeString(settingsPath, settingsXmlSnippet);

        System.out.println("\n=== settings.xml - POSWIADCZENIA (zapisany na dysku jako przyklad) ===");
        System.out.println("Sciezka: " + settingsPath);
        System.out.println(settingsXmlSnippet);
        System.out.println("<id> w settings.xml MUSI byc taki sam jak <id> w distributionManagement -");
        System.out.println("to jedyny sposob, w jaki Maven wie, KTOREGO usera/hasla uzyc dla danego URL.");
        System.out.println("${env.NEXUS_USER} - odczyt ze zmiennej srodowiskowej, nie hardkodowanie hasla w pliku.");

        /*
         * ============================================================
         * 🔍 maven-deploy-plugin - "mvn deploy"
         * ============================================================
         * Faza "deploy" (ostatnia w cyklu default, PO "install" - patrz
         * Lesson11) wywoluje maven-deploy-plugin, ktory wysyla juz
         * zbudowany artefakt (.jar/.war) POD adres z
         * distributionManagement, korzystajac z poswiadczen z
         * settings.xml. To NIE dubluje "install" - install kopiuje
         * TYLKO lokalnie (~/.m2), deploy wysyla przez siec.
         */
        printPhaseFlow();

        /*
         * ============================================================
         * 🔹 SNAPSHOT vs RELEASE - CYKL ZYCIA WERSJI
         * ============================================================
         * Wersja konczaca sie na "-SNAPSHOT" (np. 1.0.0-SNAPSHOT) to
         * "wersja robocza" - Maven pozwala ja NADPISYWAC wielokrotnie w
         * repozytorium zdalnym (kazdy "mvn deploy" podmienia poprzedni
         * build). Wersja RELEASE (np. 1.0.0, bez sufiksu) jest
         * TRAKTOWANA JAKO NIEZMIENNA - wiekszosc repozytoriow (Nexus,
         * Artifactory, Maven Central) ODRZUCA ponowny "deploy" tego
         * samego numeru release - trzeba podbic wersje.
         */
        String versionFlowSnippet = """
                <!-- W trakcie prac: -->
                <version>1.2.0-SNAPSHOT</version>
                <!-- mvn deploy -> nadpisuje poprzedni SNAPSHOT w repo, dowolnie wiele razy -->

                <!-- Przy wydaniu (release): -->
                <version>1.2.0</version>
                <!-- mvn deploy -> WYSYLA RAZ. Kolejny "mvn deploy" z tym samym numerem
                     zostanie ODRZUCONY przez wiekszosc repozytoriow (release = niezmienny). -->
                """;
        System.out.println("\n=== SNAPSHOT vs RELEASE ===");
        System.out.println(versionFlowSnippet);

        /*
         * ============================================================
         * 🔹 PODPISYWANIE GPG - WYMOG MAVEN CENTRAL
         * ============================================================
         * Publikacja na Maven Central (publiczne, globalne repozytorium
         * uzywane przez KAZDY projekt Maven na swiecie przez domyslne
         * <mirrors>) wymaga DODATKOWO podpisu kryptograficznego kazdego
         * artefaktu kluczem GPG - dowod, ze paczka pochodzi od
         * zadeklarowanego autora i nie zostala podmieniona "po drodze".
         * Odpowiada za to maven-gpg-plugin, podpiety pod fazę "verify".
         */
        String gpgSnippet = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-gpg-plugin</artifactId>
                    <executions>
                        <execution>
                            <id>sign-artifacts</id>
                            <phase>verify</phase>
                            <goals>
                                <goal>sign</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                """;
        System.out.println("\n=== maven-gpg-plugin - PODPISYWANIE ARTEFAKTOW ===");
        System.out.println(gpgSnippet);
        System.out.println("Wynik: obok pliku .jar powstaja .jar.asc, .pom.asc itd. - podpisy GPG kazdego pliku.");
        System.out.println("Bez podpisu Maven Central ODRZUCI cala publikacje.");

        /*
         * ============================================================
         * 📌 CENTRAL PUBLISHING - NOWY PORTAL MAVEN CENTRAL
         * ============================================================
         * Historycznie publikacja na Maven Central szla przez OSSRH
         * (Nexus Staging, "close" + "release" repozytorium recznie w
         * przegladarce). Od 2024 Sonatype wprowadzil Central Publishing
         * Portal (central-publishing-maven-plugin) - prostszy przeplyw:
         * "mvn deploy" wysyla PROSTO na portal, ktory automatycznie
         * waliduje (POM, podpisy GPG, javadoc/sources jar) i publikuje.
         */
        String centralPublishingSnippet = """
                <plugin>
                    <groupId>org.sonatype.central</groupId>
                    <artifactId>central-publishing-maven-plugin</artifactId>
                    <version>0.7.0</version>
                    <extensions>true</extensions>
                    <configuration>
                        <publishingServerId>central</publishingServerId>
                        <autoPublish>true</autoPublish>
                    </configuration>
                </plugin>
                """;
        Path pomPath = tempDir.resolve("pom.xml");
        String fullPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>com.example</groupId>
                    <artifactId>publishing-demo</artifactId>
                    <version>1.0.0</version>

                    <build>
                        <plugins>
                %s
                        </plugins>
                    </build>
                </project>
                """.formatted(indent(centralPublishingSnippet, 12));
        Files.writeString(pomPath, fullPom);

        System.out.println("\n=== central-publishing-maven-plugin (zapisany na dysku) ===");
        System.out.println("Sciezka: " + pomPath);
        System.out.println(centralPublishingSnippet);
        System.out.println("autoPublish=true -> po walidacji publikacja jest automatyczna (bez recznego 'release' w UI).");

        /*
         * ============================================================
         * 🔍 WYMAGANE ARTEFAKTY DO PUBLIKACJI NA MAVEN CENTRAL
         * ============================================================
         * Maven Central odrzuci deploy, jesli w paczce brakuje
         * KTOREGOKOLWIEK z ponizszych elementow - to najczestszy powod
         * "failed validation" u poczatkujacych.
         */
        printRequiredArtifactsTable();

        /*
         * ============================================================
         * 🔹 ILUSTRACYJNY PRZYKLAD OUTPUTU "mvn deploy"
         * ============================================================
         * Ponizszy fragment to ILUSTRACYJNY (nie realnie wywolany "mvn")
         * przyklad tego, co widac w konsoli po "mvn deploy" - patrz
         * CLAUDE.md, sekcja _11_buildtools, dla wyjasnienia, czemu
         * lekcje Maven nie odpalaja realnego 'mvn'.
         */
        String illustrativeOutput = """
                [INFO] --- gpg:3.2.1:sign (sign-artifacts) @ publishing-demo ---
                [INFO] Signing publishing-demo-1.0.0.jar with default secret key.
                [INFO] --- deploy:3.1.1:deploy (default-deploy) @ publishing-demo ---
                Uploading to company-releases: https://repo.example.com/releases/...
                [INFO] BUILD SUCCESS
                """;
        System.out.println("\n=== ILUSTRACYJNY OUTPUT 'mvn deploy' ===");
        System.out.println("(To NIE jest realny output - ten kod NIE odpala prawdziwego 'mvn', patrz CLAUDE.md.)");
        System.out.println(illustrativeOutput);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - distributionManagement w pom.xml - GDZIE publikowac (osobne
         *   repository dla release, snapshotRepository dla SNAPSHOT).
         * - settings.xml (~/.m2, POZA kontrola wersji) - poswiadczenia w
         *   <servers>, powiazane po <id> z distributionManagement.
         * - "mvn deploy" (faza PO install) - wysyla przez siec, w
         *   odroznieniu od "install" (tylko lokalne ~/.m2).
         * - SNAPSHOT = nadpisywalny, RELEASE = niezmienny (wiekszosc
         *   repozytoriow odrzuci ponowny deploy tego samego numeru).
         * - Maven Central wymaga DODATKOWO: podpisow GPG
         *   (maven-gpg-plugin), javadoc.jar, sources.jar i pelnych
         *   metadanych w pom.xml (nazwa, opis, licencja, developerzy, scm).
         * - central-publishing-maven-plugin - nowoczesny sposob
         *   publikacji na Maven Central (zastapil reczny Nexus Staging).
         */

        Files.deleteIfExists(settingsPath);
        Files.deleteIfExists(pomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void printPhaseFlow() {
        System.out.println("\n=== CYKL FAZ: ... -> package -> ... -> install -> deploy ===");
        System.out.println("validate -> compile -> test -> package -> verify -> install -> deploy");
        System.out.println("install -> kopiuje artefakt TYLKO do lokalnego ~/.m2/repository (Twoj komputer).");
        System.out.println("deploy   -> WYSYLA artefakt przez siec do repozytorium zdalnego (inni moga go pobrac).");
    }

    private static void printRequiredArtifactsTable() {
        String format = "%-28s | %-45s%n";
        System.out.printf(format, "Artefakt", "Jak uzyskac");
        System.out.println("-".repeat(78));
        System.out.printf(format, "*.jar (glowny)", "domyslny wynik 'mvn package'");
        System.out.printf(format, "*-sources.jar", "maven-source-plugin, goal 'jar-no-fork'");
        System.out.printf(format, "*-javadoc.jar", "maven-javadoc-plugin, goal 'jar'");
        System.out.printf(format, "*.jar.asc + *.pom.asc", "maven-gpg-plugin, goal 'sign'");
        System.out.printf(format, "pelne metadane w pom.xml", "name, description, url, licenses, developers, scm");
    }

    private static String indent(String xml, int spaces) {
        String pad = " ".repeat(spaces);
        return xml.trim().lines()
                .map(line -> pad + line)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
