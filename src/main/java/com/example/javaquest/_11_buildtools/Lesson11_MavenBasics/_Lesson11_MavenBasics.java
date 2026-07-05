package com.example.javaquest._11_buildtools.Lesson11_MavenBasics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson11_MavenBasics {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 11: MAVEN - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST MAVEN?
         * ============================================================
         * Maven to narzędzie do BUDOWANIA i ZARZĄDZANIA projektami Java
         * (podobnie jak Ant z poprzednich lekcji tego rozdziału), ale
         * oparte na zupełnie innej filozofii: CONVENTION OVER
         * CONFIGURATION (konwencja ponad konfiguracją).
         *
         * Ant wymaga, żeby TY opisał każdy krok budowania (target po
         * targecie, task po tasku) w build.xml - Ant "nie wie", gdzie
         * masz kod źródłowy, dopóki mu nie powiesz.
         *
         * Maven ma WBUDOWANE domyślne założenia o strukturze projektu
         * i cyklu życia budowania. Jeśli trzymasz się konwencji (a w
         * 95% projektów tak robimy), plik konfiguracyjny Maven-a
         * (pom.xml) może być zaskakująco KRÓTKI - bo większości rzeczy
         * nie trzeba w ogóle opisywać, Maven już "wie", jak je zrobić.
         *
         * Ten kurs (JavaQuest) sam jest zbudowany Mavenem - plik pom.xml
         * w korzeniu repozytorium (zobacz go!) jest prawdziwym przykładem
         * tego, o czym mówi ta lekcja.
         */

        System.out.println("\n=== CONVENTION OVER CONFIGURATION ===");
        System.out.println("Ant: TY opisujesz kazdy krok budowania (build.xml).");
        System.out.println("Maven: trzymasz sie konwencji struktury projektu, a Maven 'wie', co robic.");

        /*
         * ============================================================
         * 🔹 STANDARDOWA STRUKTURA PROJEKTU MAVEN
         * ============================================================
         * Maven zakłada domyślnie (bez żadnej konfiguracji!) taki układ
         * katalogów:
         *
         *   moj-projekt/
         *   ├── pom.xml                      <- "przepis" na projekt
         *   ├── src/
         *   │   ├── main/
         *   │   │   ├── java/                <- kod źródłowy aplikacji
         *   │   │   └── resources/           <- pliki .properties, .xml, itp.
         *   │   └── test/
         *   │       ├── java/                <- kod testów jednostkowych
         *   │       └── resources/           <- pliki potrzebne w testach
         *   └── target/                      <- WYNIKI budowania (.class, .jar...)
         *
         * Ten sam układ widzisz w KAŻDYM projekcie Maven na świecie - to
         * właśnie ta konwencja. `target/` jest GENEROWANY przez Maven-a
         * (analogicznie do "build/" w projektach Ant z wcześniejszych
         * lekcji) - nigdy nie commitujemy go do repozytorium (.gitignore).
         *
         * Ten kurs też ma taką strukturę:
         *   javaQuest/
         *   ├── pom.xml
         *   ├── src/main/java/com/example/javaquest/...   <- to, co piszesz
         *   └── target/                                    <- po `mvnw.cmd compile`
         */

        System.out.println("\n=== STANDARDOWA STRUKTURA PROJEKTU ===");
        System.out.println("src/main/java        - kod zrodlowy aplikacji");
        System.out.println("src/main/resources   - pliki konfiguracyjne, zasoby");
        System.out.println("src/test/java        - kod testow jednostkowych");
        System.out.println("src/test/resources   - zasoby potrzebne w testach");
        System.out.println("target/              - wygenerowane wyniki budowania (nie commitujemy!)");

        /*
         * ============================================================
         * 🔹 POM.XML - "PRZEPIS" NA PROJEKT
         * ============================================================
         * POM = Project Object Model. To plik pom.xml w korzeniu
         * projektu - centralny plik konfiguracyjny Maven-a, analogiczny
         * do build.xml w Ant, ale w INNEJ filozofii: opisuje CO projekt
         * JEST i CZEGO POTRZEBUJE, a nie JAK KROK PO KROKU go zbudować.
         *
         * Kluczowe elementy pom.xml:
         * - groupId       -> "przestrzeń nazw" firmy/organizacji/projektu
         *                    (konwencja: odwrócona domena, np. com.example)
         * - artifactId    -> nazwa konkretnego modułu/artefaktu (np. JavaQuest)
         * - version       -> wersja (np. 0.0.1-SNAPSHOT - "SNAPSHOT" = wersja
         *                    rozwojowa, jeszcze niewydana)
         * - packaging     -> typ wynikowego artefaktu: jar (domyślnie), war, pom...
         * - properties    -> centralizowane wartości (np. wersja Javy)
         * - dependencies  -> lista bibliotek, których projekt potrzebuje
         * - build/plugins -> pluginy rozszerzające/konfigurujące budowanie
         *
         * groupId + artifactId + version razem tworzą UNIKALNY "adres"
         * artefaktu w świecie Maven - tzw. koordynaty Maven (GAV).
         */

        System.out.println("\n=== KLUCZOWE ELEMENTY POM.XML ===");
        System.out.println("groupId + artifactId + version = unikalne koordynaty artefaktu (GAV)");
        System.out.println("packaging   - jar (domyslnie), war, pom...");
        System.out.println("properties  - centralizowane wartosci, np. wersja Javy");
        System.out.println("dependencies- biblioteki, ktorych projekt potrzebuje");
        System.out.println("build/plugins - pluginy rozszerzajace proces budowania");

        // Wygenerujmy realny, prosty pom.xml (prostsza aplikacja konsolowa
        // niż pom.xml tego kursu) i zapiszmy go na dysk.
        String pomXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

                    <modelVersion>4.0.0</modelVersion>

                    <groupId>com.example</groupId>
                    <artifactId>hello-maven</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>

                    <properties>
                        <maven.compiler.source>21</maven.compiler.source>
                        <maven.compiler.target>21</maven.compiler.target>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>

                    <dependencies>
                        <dependency>
                            <groupId>org.junit.jupiter</groupId>
                            <artifactId>junit-jupiter</artifactId>
                            <version>5.10.2</version>
                            <scope>test</scope>
                        </dependency>
                    </dependencies>

                </project>
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson11-maven");
        Path pomPath = tempDir.resolve("pom.xml");
        Files.writeString(pomPath, pomXml);

        System.out.println("\n=== WYGENEROWANY POM.XML (zapisany na dysku) ===");
        System.out.println("Sciezka: " + pomPath);
        System.out.println(pomXml);

        /*
         * ============================================================
         * 🔍 MAVEN LIFECYCLE - CYKL ŻYCIA BUDOWANIA
         * ============================================================
         * Maven definiuje domyślny CYKL ŻYCIA (lifecycle) - uporządkowaną
         * sekwencję FAZ (phases). Każda faza reprezentuje jeden krok
         * budowania. Odpalenie danej fazy AUTOMATYCZNIE wykonuje też
         * wszystkie fazy PRZED nią.
         *
         * Domyślny cykl życia "default" (najważniejsze fazy, w kolejności):
         *
         *   validate -> compile -> test -> package -> verify -> install -> deploy
         *
         * - validate  -> sprawdza, czy projekt jest poprawny (pom.xml OK,
         *                wszystkie potrzebne informacje są dostępne)
         * - compile   -> kompiluje kod źródłowy z src/main/java do target/classes
         * - test      -> odpala testy jednostkowe (src/test/java) frameworkiem
         *                testowym (np. JUnit) poprzez surefire-plugin
         * - package   -> pakuje skompilowany kod do artefaktu (np. .jar/.war)
         *                w katalogu target/
         * - verify    -> uruchamia dodatkowe sprawdzenia jakości artefaktu
         *                (np. testy integracyjne) - często pusty krok
         * - install   -> kopiuje zbudowany artefakt do LOKALNEGO repozytorium
         *                Maven (~/.m2/repository) - żeby inne lokalne projekty
         *                mogły go użyć jako zależności
         * - deploy    -> wysyła artefakt do ZDALNEGO repozytorium (np. firmowego
         *                Nexusa/Artifactory) - udostępnia go innym zespołom/CI
         */

        System.out.println("\n=== MAVEN LIFECYCLE (cykl 'default') ===");
        String[] phases = {"validate", "compile", "test", "package", "verify", "install", "deploy"};
        String[] phaseDescriptions = {
                "sprawdza poprawnosc projektu (pom.xml, wymagane dane)",
                "kompiluje src/main/java -> target/classes",
                "odpala testy jednostkowe (src/test/java)",
                "pakuje skompilowany kod do artefaktu (.jar/.war) w target/",
                "dodatkowe sprawdzenia jakosci artefaktu",
                "kopiuje artefakt do lokalnego repozytorium ~/.m2/repository",
                "wysyla artefakt do zdalnego repozytorium (Nexus/Artifactory)"
        };
        for (int i = 0; i < phases.length; i++) {
            System.out.println((i + 1) + ". " + phases[i] + " -> " + phaseDescriptions[i]);
        }
        System.out.println("\nWAZNE: odpalenie fazy 'package' automatycznie wykonuje NAJPIERW");
        System.out.println("validate, compile i test - fazy sa uporzadkowane sekwencyjnie.");

        /*
         * ============================================================
         * 📌 PHASE vs GOAL - KLUCZOWE ROZRÓŻNIENIE
         * ============================================================
         * To jedno z najważniejszych rozróżnień w Mavenie, często mylone
         * przez początkujących:
         *
         * - FAZA (phase) to punkt w cyklu życia (np. "compile", "test",
         *   "package"). Faza SAMA W SOBIE nie robi nic - to tylko "etykieta"
         *   / "miejsce" w sekwencji budowania.
         *
         * - GOAL (cel pluginu) to KONKRETNE zadanie wykonywane przez
         *   KONKRETNY plugin (np. goal "compiler:compile" z pluginu
         *   maven-compiler-plugin, albo "surefire:test" z pluginu
         *   maven-surefire-plugin). To goal FAKTYCZNIE robi pracę.
         *
         * Maven "podwiesza" (binds) domyślne goale do domyślnych faz:
         *
         *   mvn compile  -> uruchamia faze "compile", a Maven wie,
         *                    ze do tej fazy jest DOMYSLNIE podwiazany
         *                    goal "compiler:compile" z maven-compiler-plugin
         *                    -> wiec ten wlasnie goal sie wykonuje
         *
         *   mvn test     -> najpierw compile (i wszystko wczesniej),
         *                    potem faza "test" -> podwiazany domyslnie
         *                    goal "surefire:test" z maven-surefire-plugin
         *
         *   mvn package  -> wszystko wczesniej, potem faza "package" ->
         *                    podwiazany domyslnie goal "jar:jar"
         *                    z maven-jar-plugin (albo "war:war" przy
         *                    packaging=war)
         *
         *   mvn install  -> wszystko wczesniej + faza "install" ->
         *                    goal "install:install" kopiuje artefakt
         *                    do ~/.m2/repository
         *
         * Można też wywołać goal BEZPOŚREDNIO, z pominięciem calego
         * cyklu, np. "mvn compiler:compile" - odpali TYLKO ten jeden
         * goal, bez fazy validate/test itd. To rzadziej używane niz
         * odpalanie faz.
         */

        System.out.println("\n=== PHASE vs GOAL ===");
        System.out.println("FAZA (phase)  = punkt w cyklu zycia, np. 'compile' - sama nic nie robi.");
        System.out.println("GOAL (cel)    = konkretne zadanie konkretnego pluginu, np. 'compiler:compile'.");
        System.out.println();
        System.out.println("mvn compile  -> faza 'compile' -> uruchamia domyslnie podwiazany goal");
        System.out.println("                'compiler:compile' z maven-compiler-plugin");
        System.out.println("mvn test     -> faza 'test' -> goal 'surefire:test' z maven-surefire-plugin");
        System.out.println("mvn package  -> faza 'package' -> goal 'jar:jar' z maven-jar-plugin");
        System.out.println("mvn install  -> faza 'install' -> goal 'install:install'");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Maven = convention over configuration - trzymasz sie
         *   standardowej struktury (src/main/java, src/test/java...),
         *   a Maven "wie", jak zbudowac projekt bez szczegolowej recepty.
         * - pom.xml opisuje CO projekt jest i CZEGO potrzebuje
         *   (groupId/artifactId/version/packaging/dependencies/build).
         * - Domyslny cykl zycia: validate -> compile -> test -> package
         *   -> verify -> install -> deploy - kazda faza wykonuje rowniez
         *   wszystkie fazy przed nia.
         * - FAZA to miejsce w cyklu, GOAL to faktyczna praca konkretnego
         *   pluginu podwiazana do danej fazy (np. compile -> compiler:compile).
         * - To narzedzie NIE ma prostego API do "wbudowania" w JVM tak
         *   jak Ant - w praktyce uzywamy go z linii komend (mvn/mvnw) -
         *   ta lekcja i kolejne (12-15) generuja i wyjasniaja realne
         *   pliki pom.xml, ale nie odpalaja prawdziwego `mvn` z wewnatrz
         *   main() (patrz CLAUDE.md w korzeniu repo, sekcja _11_buildtools).
         */

        Files.deleteIfExists(pomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }
}
