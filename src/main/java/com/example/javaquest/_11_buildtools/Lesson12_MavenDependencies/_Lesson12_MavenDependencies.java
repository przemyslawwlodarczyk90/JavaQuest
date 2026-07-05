package com.example.javaquest._11_buildtools.Lesson12_MavenDependencies;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class _Lesson12_MavenDependencies {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 12: MAVEN - ZALEZNOSCI (DEPENDENCIES) ===");

        /*
         * ============================================================
         * 📦 DEPENDENCY - PODSTAWOWY ELEMENT pom.xml
         * ============================================================
         * Zamiast ręcznie ściągać pliki .jar bibliotek i dodawać je do
         * classpath (jak trzeba by robić w Ant - patrz Lesson05_AntClasspath),
         * w Mavenie deklarujesz zależność jako WPIS w pom.xml, a Maven SAM
         * ściąga odpowiedni plik .jar (i JEGO zależności - patrz niżej)
         * z internetu.
         *
         * Każda zależność to trójka koordynatów (te samy GAV, co poznaliśmy
         * w Lesson11 dla WŁASNEGO projektu):
         *
         *   <dependency>
         *       <groupId>com.google.code.gson</groupId>
         *       <artifactId>gson</artifactId>
         *       <version>2.11.0</version>
         *   </dependency>
         *
         * To DOKŁADNIE ta zależność, która jest w pom.xml TEGO kursu
         * (zobacz korzeń repozytorium) - dodana dla lekcji o JSON
         * w rozdziale _04_io.
         */

        System.out.println("\n=== ANATOMIA <dependency> ===");
        System.out.println("groupId + artifactId + version = ktora biblioteka i w jakiej wersji.");
        System.out.println("Maven SAM sciaga jar (i jego zaleznosci) z repozytorium - nie robisz tego recznie.");

        /*
         * ============================================================
         * 🔹 SCOPE - ZAKRES ZALEŻNOŚCI
         * ============================================================
         * Scope określa, GDZIE dana zależność jest dostępna - czy tylko
         * przy kompilacji, czy też w testach, czy trafia do finalnego
         * artefaktu.
         *
         * - compile  (domyślny) -> dostępna WSZĘDZIE: kompilacja, testy,
         *                          runtime, ORAZ pakowana do finalnego jara/wara.
         * - provided             -> dostępna przy kompilacji i testach,
         *                          ale NIE pakowana do artefaktu - bo
         *                          "dostawca" (np. kontener serwletów)
         *                          zapewni ją sam w runtime. Przykład:
         *                          jakarta.servlet-api (zobaczysz w Lesson15).
         * - runtime              -> NIE potrzebna przy kompilacji (kod się
         *                          nie odwołuje do jej klas bezpośrednio),
         *                          ale potrzebna w runtime, np. sterownik
         *                          JDBC ładowany dynamicznie.
         * - test                 -> dostępna TYLKO przy kompilacji/uruchamianiu
         *                          testów (src/test/java) - nigdy w głównym
         *                          kodzie i NIGDY w finalnym artefakcie.
         *                          Przykład: spring-boot-starter-test w
         *                          pom.xml tego kursu.
         * - system               -> jak compile, ale plik .jar wskazywany
         *                          RĘCZNIE ścieżką na dysku (<systemPath>) -
         *                          bardzo rzadko używane, zależność NIE
         *                          pochodzi z żadnego repozytorium.
         * - import               -> używany WYŁĄCZNIE wewnątrz
         *                          <dependencyManagement> do importowania
         *                          całego BOM-u (Bill Of Materials) innego
         *                          projektu (zobaczysz w Lesson14).
         */

        System.out.println("\n=== SCOPE - PORÓWNANIE WPŁYWU NA CLASSPATH ===");
        printScopeTable();

        String pomWithScopes = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>com.example</groupId>
                    <artifactId>scopes-demo</artifactId>
                    <version>1.0.0</version>

                    <dependencies>
                        <!-- compile (domyslny) - trafia wszedzie, w tym do finalnego jara -->
                        <dependency>
                            <groupId>com.google.code.gson</groupId>
                            <artifactId>gson</artifactId>
                            <version>2.11.0</version>
                        </dependency>

                        <!-- provided - dostawca (kontener servletowy) zapewni to sam w runtime -->
                        <dependency>
                            <groupId>jakarta.servlet</groupId>
                            <artifactId>jakarta.servlet-api</artifactId>
                            <version>6.0.0</version>
                            <scope>provided</scope>
                        </dependency>

                        <!-- test - dostepne tylko w src/test/java, nigdy w finalnym artefakcie -->
                        <dependency>
                            <groupId>org.junit.jupiter</groupId>
                            <artifactId>junit-jupiter</artifactId>
                            <version>5.10.2</version>
                            <scope>test</scope>
                        </dependency>
                    </dependencies>
                </project>
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson12-maven");
        Path pomPath = tempDir.resolve("pom.xml");
        Files.writeString(pomPath, pomWithScopes);

        System.out.println("\n=== WYGENEROWANY POM.XML Z 3 SCOPE'AMI (zapisany na dysku) ===");
        System.out.println("Sciezka: " + pomPath);
        System.out.println(pomWithScopes);

        /*
         * ============================================================
         * 🔍 MAVEN CENTRAL I LOKALNE REPOZYTORIUM ~/.m2
         * ============================================================
         * Skąd Maven bierze te pliki .jar? Domyślnie z MAVEN CENTRAL
         * (repo1.maven.org) - publicznego, ogromnego repozytorium
         * bibliotek open-source, do którego "wypycha" swoje wydania
         * większość projektów Java na świecie.
         *
         * Przy PIERWSZYM użyciu zależności Maven:
         * 1. Sprawdza LOKALNE repozytorium (cache) w katalogu
         *    ~/.m2/repository (na Windows: C:\\Users\\<user>\\.m2\\repository).
         * 2. Jeśli nie ma jej tam - ściąga z Maven Central (albo innego
         *    skonfigurowanego repozytorium, np. firmowego Nexusa) i
         *    ZAPISUJE w ~/.m2/repository na przyszłość.
         * 3. Przy KOLEJNYCH budowaniach (nawet w innych projektach!)
         *    używa już lokalnej kopii - stąd dużo szybsze kolejne "mvn compile".
         *
         * Struktura ~/.m2/repository odzwierciedla GAV jako ścieżkę:
         *   ~/.m2/repository/<groupId z / zamiast .>/<artifactId>/<version>/
         *   np. com/google/code/gson/gson/2.11.0/gson-2.11.0.jar
         */

        System.out.println("\n=== MAVEN CENTRAL I LOKALNE REPOZYTORIUM ~/.m2 ===");
        System.out.println("1. Maven sprawdza lokalny cache ~/.m2/repository.");
        System.out.println("2. Jesli brak - sciaga z Maven Central (repo1.maven.org) i zapisuje lokalnie.");
        System.out.println("3. Kolejne budowania (nawet innych projektow) uzywaja juz lokalnej kopii.");

        String m2Path = System.getProperty("user.home") + File.separator + ".m2" + File.separator + "repository";
        Path m2Repo = Path.of(m2Path);
        System.out.println("\nLokalne repozytorium na TEJ maszynie: " + m2Path);
        if (Files.isDirectory(m2Repo)) {
            System.out.println("Katalog istnieje! Kilka przykladowych podkatalogow groupId (jesli sa):");
            try (var stream = Files.list(m2Repo)) {
                stream.filter(Files::isDirectory)
                        .sorted()
                        .limit(5)
                        .forEach(p -> System.out.println("  - " + p.getFileName()));
            } catch (IOException e) {
                System.out.println("  (nie udalo sie odczytac zawartosci: " + e.getMessage() + ")");
            }
        } else {
            System.out.println("Katalog ~/.m2/repository nie istnieje na tej maszynie/w tym srodowisku -");
            System.out.println("to normalne, jesli Maven nigdy tu nie budowal projektu z pobieraniem zaleznosci.");
        }

        /*
         * ============================================================
         * 🔹 ZALEŻNOŚCI PRZECHODNIE (TRANSITIVE DEPENDENCIES)
         * ============================================================
         * Gdy deklarujesz zależność A, Maven automatycznie ściąga też
         * WSZYSTKIE zależności, których A samo potrzebuje (i ich zależności,
         * i tak dalej - cały "graf" zależności). To nazywamy zależnościami
         * PRZECHODNIMI (transitive) - Ty deklarujesz tylko A, resztę Maven
         * dociąga sam.
         *
         * Przykład z pom.xml tego kursu: deklarujemy tylko
         * spring-boot-starter-web, a Maven ściąga przy tym dziesiątki
         * bibliotek transytywnych (Tomcat embedded, Jackson, Spring MVC...) -
         * dokładnie stąd mamy dostęp do tomcat-embed-core w rozdziale
         * _07_servlets, mimo że NIE dodawaliśmy go jako osobnej,
         * bezpośredniej zależności!
         */

        System.out.println("\n=== ZALEZNOSCI PRZECHODNIE (TRANSITIVE) ===");
        System.out.println("Deklarujesz TYLKO biblioteke A -> Maven dociaga WSZYSTKIE zaleznosci A (i ich zaleznosci).");
        System.out.println("Przyklad z tego kursu: spring-boot-starter-web -> transytywnie dociaga");
        System.out.println("tomcat-embed-core, ktorego uzywamy w rozdziale _07_servlets bez deklarowania go samemu.");

        /*
         * ============================================================
         * ⚠️ KONFLIKTY WERSJI - DEPENDENCY MEDIATION
         * ============================================================
         * Co jeśli TWÓJ projekt zależy od biblioteki A, a A zależy od B
         * w wersji 1.0, ale jednocześnie Twój projekt zależy też od
         * biblioteki C, która zależy od TEJ SAMEJ biblioteki B, ale w
         * wersji 2.0?
         *
         *   Twój projekt
         *    ├── A -> B:1.0
         *    └── C -> B:2.0
         *
         * To KONFLIKT WERSJI. Maven musi wybrać JEDNĄ wersję B do
         * finalnego classpath. Reguła Maven-a to "dependency mediation" -
         * w uproszczeniu: WYGRYWA NAJBLIŻSZA zależność w drzewie (licząc
         * "głębokość" od korzenia projektu), a przy remisie głębokości -
         * ta zadeklarowana WCZEŚNIEJ w pom.xml.
         *
         * Jeśli Twój projekt deklaruje B BEZPOŚREDNIO (głębokość 1), ta
         * deklaracja ZAWSZE wygrywa z wersją przechodnią (głębokość 2+) -
         * to najprostszy sposób wymuszenia konkretnej wersji.
         *
         * 🔧 EXCLUSIONS - jawne wykluczenie zależności przechodniej
         * Czasem chcemy całkowicie WYKLUCZYĆ jakąś zależność przechodnią
         * (np. bo powoduje konflikt albo problem bezpieczeństwa) - służy
         * do tego <exclusions>:
         */

        String exclusionExample = """
                <dependency>
                    <groupId>com.example</groupId>
                    <artifactId>A</artifactId>
                    <version>1.0.0</version>
                    <exclusions>
                        <!-- A samo w sobie ciagnie B:1.0, ale my chcemy sami
                             zdecydowac, jakiej wersji B uzywamy, wiec je wykluczamy -->
                        <exclusion>
                            <groupId>com.example</groupId>
                            <artifactId>B</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>

                <!-- ...i deklarujemy B sami, w wersji, ktorej potrzebujemy -->
                <dependency>
                    <groupId>com.example</groupId>
                    <artifactId>B</artifactId>
                    <version>2.0.0</version>
                </dependency>
                """;

        System.out.println("\n=== KONFLIKT WERSJI - PRZYKLAD ===");
        System.out.println("Projekt -> A -> B:1.0");
        System.out.println("Projekt -> C -> B:2.0");
        System.out.println("Dependency mediation: wygrywa NAJBLIZSZA zaleznosc w drzewie (najmniejsza glebokosc),");
        System.out.println("a przy remisie - zadeklarowana WCZESNIEJ w pom.xml.");
        System.out.println("\n=== ROZWIAZANIE KONFLIKTU PRZEZ <exclusions> ===");
        System.out.println(exclusionExample);

        /*
         * ============================================================
         * 🔍 DEPENDENCY TREE - ANALIZA DRZEWA ZALEŻNOŚCI
         * ============================================================
         * Skoro zależności przechodnie są "niewidoczne" w pom.xml (nie
         * deklarujesz ich jawnie), jak sprawdzić, co NAPRAWDĘ trafia do
         * classpath i skąd się wzięło?
         *
         *   mvn dependency:tree
         *
         * Ten goal (z maven-dependency-plugin) wypisuje CAŁE drzewo
         * zależności projektu - każdą bezpośrednią zależność i to, co
         * ona przyciąga transytywnie, z zaznaczonymi wersjami. To
         * NAJWAŻNIEJSZE narzędzie diagnostyczne przy konfliktach wersji -
         * pokazuje np. "(version managed from 1.0 to 2.0)" gdy Maven
         * musiał rozstrzygnąć konflikt.
         *
         * Przykładowy fragment wyniku (poglądowo):
         *   com.example:my-app:jar:1.0.0
         *   +- com.example:A:jar:1.0.0:compile
         *   |  \\- com.example:B:jar:1.0.0:compile (wersja przed rozstrzygnieciem)
         *   \\- com.example:C:jar:1.0.0:compile
         *      \\- com.example:B:jar:2.0.0:compile (TA wersja ostatecznie wygrala)
         */

        System.out.println("\n=== DEPENDENCY TREE ===");
        System.out.println("Komenda 'mvn dependency:tree' wypisuje CALE drzewo zaleznosci (bezposrednie + przechodnie).");
        System.out.println("To glowne narzedzie do diagnozowania konfliktow wersji - pokazuje, ktora wersja");
        System.out.println("'wygrala' i (czesto) dlaczego.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - <dependency> = groupId + artifactId + version (+ opcjonalny scope).
         * - scope compile (domyslny) trafia wszedzie i do finalnego artefaktu;
         *   provided - tylko kompilacja/testy, NIE do artefaktu (dostawca da ja
         *   w runtime); test - tylko src/test/java; runtime - tylko w dzialaniu,
         *   nie przy kompilacji; system - recznie wskazany plik; import -
         *   tylko w dependencyManagement, do importu BOM-u.
         * - Zaleznosci PRZECHODNIE - Maven sam dociąga zaleznosci Twoich
         *   zaleznosci (i tak dalej), bez Twojej ingerencji.
         * - Konflikty wersji rozstrzyga dependency mediation (najblizsza
         *   w drzewie wygrywa) - a <exclusions> pozwala jawnie wykluczyc
         *   niechciana zaleznosc przechodnia.
         * - 'mvn dependency:tree' to podstawowe narzedzie do analizy i
         *   diagnozy konfliktow wersji w realnym projekcie.
         * - ~/.m2/repository to REALNY katalog na dysku - lokalny cache
         *   wszystkich kiedykolwiek pobranych/zainstalowanych artefaktow.
         */

        Files.deleteIfExists(pomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void printScopeTable() {
        String format = "%-10s | %-18s | %-18s | %-18s%n";
        System.out.printf(format, "SCOPE", "COMPILE CLASSPATH", "TEST CLASSPATH", "W FINALNYM ARTEFAKCIE");
        System.out.println("-".repeat(72));
        System.out.printf(format, "compile", "TAK", "TAK", "TAK");
        System.out.printf(format, "provided", "TAK", "TAK", "NIE");
        System.out.printf(format, "runtime", "NIE", "TAK", "TAK");
        System.out.printf(format, "test", "NIE", "TAK", "NIE");
        System.out.printf(format, "system", "TAK", "TAK", "TAK (recznie)");
        System.out.printf(format, "import", Arrays.toString(new String[]{"tylko w"}), "dependencyManagement", "N/A");
    }
}
