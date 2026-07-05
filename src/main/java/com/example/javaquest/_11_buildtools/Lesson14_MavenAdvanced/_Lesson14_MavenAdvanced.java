package com.example.javaquest._11_buildtools.Lesson14_MavenAdvanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson14_MavenAdvanced {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 14: MAVEN - TEMATY ZAAWANSOWANE ===");

        /*
         * ============================================================
         * 📦 PROPERTIES - CENTRALIZACJA WARTOŚCI
         * ============================================================
         * <properties> to sekcja pom.xml pozwalająca zdefiniować WŁASNE
         * "zmienne" (np. wersję Javy, wersję konkretnej biblioteki) w
         * JEDNYM miejscu i odwoływać się do nich w wielu miejscach pliku
         * przez ${nazwa.property}. Zamiast wpisywać "21" w pięciu różnych
         * miejscach (i zapominać zaktualizować jedno z nich), zmieniasz
         * JEDNĄ linijkę.
         *
         * Przykład z pom.xml TEGO kursu:
         *   <properties>
         *       <java.version>21</java.version>
         *   </properties>
         * (spring-boot-starter-parent automatycznie użyje tej wartości
         * do skonfigurowania kompilatora).
         */

        System.out.println("\n=== PROPERTIES - CENTRALIZACJA WARTOSCI ===");
        System.out.println("<properties><java.version>21</java.version></properties>");
        System.out.println("...i pozniej odwolanie: ${java.version} - zmiana w JEDNYM miejscu wystarczy.");

        /*
         * ============================================================
         * 🔹 PROFILES - RÓŻNE KONFIGURACJE DLA RÓŻNYCH ŚRODOWISK
         * ============================================================
         * <profiles> pozwala zdefiniować ALTERNATYWNE zestawy ustawień
         * (właściwości, zależności, pluginy), które są aktywowane
         * WARUNKOWO - np. inny profil dla środowiska "dev" (baza
         * lokalna, verbose logging) i inny dla "prod" (baza produkcyjna,
         * minimalne logi).
         *
         * Aktywacja profilu:
         *   mvn package -Pprod        <- explicit, flaga -P
         *   <activeByDefault>true</activeByDefault>  <- aktywny bez flagi
         *   <activation><property>...</property></activation> <- warunkowo
         */

        String profilesExample = """
                <profiles>
                    <profile>
                        <id>dev</id>
                        <activation>
                            <activeByDefault>true</activeByDefault>
                        </activation>
                        <properties>
                            <db.url>jdbc:h2:mem:devdb</db.url>
                            <log.level>DEBUG</log.level>
                        </properties>
                    </profile>
                    <profile>
                        <id>prod</id>
                        <properties>
                            <db.url>jdbc:postgresql://prod-host:5432/app</db.url>
                            <log.level>WARN</log.level>
                        </properties>
                    </profile>
                </profiles>
                """;

        System.out.println("\n=== PROFILES - dev vs prod ===");
        System.out.println("Aktywacja: 'mvn package -Pprod' (jawnie) albo <activeByDefault>true</activeByDefault>.");
        System.out.println(profilesExample);

        /*
         * ============================================================
         * 🔍 PARENT POM - DZIEDZICZENIE KONFIGURACJI
         * ============================================================
         * Projekt Maven może mieć RODZICA (<parent>) - inny pom.xml,
         * od którego DZIEDZICZY properties, dependencyManagement,
         * pluginManagement, i inne ustawienia. To dokładnie ten
         * mechanizm, z którego korzysta pom.xml TEGO kursu:
         *
         *   <parent>
         *       <groupId>org.springframework.boot</groupId>
         *       <artifactId>spring-boot-starter-parent</artifactId>
         *       <version>3.4.4</version>
         *       <relativePath/>
         *   </parent>
         *
         * Dzięki temu NIE musimy podawać wersji dla HikariCP czy
         * flyway-core w naszych <dependencies> - spring-boot-starter-parent
         * (poprzez swój WŁASNY <dependencyManagement>) już zdefiniował,
         * jakich wersji użyć. <relativePath/> (puste) oznacza "szukaj
         * tego parenta w repozytorium Maven, NIE w lokalnym systemie
         * plików" - używane, gdy parent jest zewnętrznym artefaktem, a
         * nie modułem TEGO SAMEGO projektu wielomodułowego (przy
         * projekcie multi-module <relativePath> wskazuje ../pom.xml -
         * zobacz niżej).
         */

        System.out.println("\n=== PARENT POM - DZIEDZICZENIE ===");
        System.out.println("pom.xml tego kursu ma <parent>spring-boot-starter-parent</parent> -");
        System.out.println("dziedziczy stamtad wersje HikariCP/flyway-core bez podawania ich samemu.");

        /*
         * ============================================================
         * 🔹 dependencyManagement vs dependencies
         * ============================================================
         * To kluczowe rozróżnienie w dużych/wielomodułowych projektach:
         *
         * - <dependencyManagement><dependencies> -> tylko DEKLARUJE,
         *   jakiej wersji użyć DLA konkretnej biblioteki, GDYBY któryś
         *   moduł jej potrzebował. SAMO w sobie NIE dodaje zależności
         *   do żadnego modułu - to "cennik", nie "zamówienie".
         *
         * - <dependencies> (bez Management) -> FAKTYCZNIE dodaje
         *   zależność do modułu. Jeśli wersja jest już ustalona przez
         *   dependencyManagement (z parenta albo z tego samego pom.xml),
         *   można pominąć <version> - odziedziczy się automatycznie.
         *
         * PRZED (bez dependencyManagement) - każdy moduł sam podaje wersję:
         *   core/pom.xml:   <dependency>...<artifactId>gson</artifactId><version>2.11.0</version></dependency>
         *   app/pom.xml:    <dependency>...<artifactId>gson</artifactId><version>2.11.0</version></dependency>
         *   (ryzyko: ktoś w jednym module wpisze inną wersję przez przypadek!)
         *
         * PO (z dependencyManagement w parencie) - wersja podana RAZ:
         *   parent/pom.xml: <dependencyManagement><dependencies>
         *                       <dependency>...gson...<version>2.11.0</version></dependency>
         *                   </dependencies></dependencyManagement>
         *   core/pom.xml:   <dependency>...<artifactId>gson</artifactId></dependency>  <- BEZ version!
         *   app/pom.xml:    <dependency>...<artifactId>gson</artifactId></dependency>  <- BEZ version!
         */

        System.out.println("\n=== dependencyManagement vs dependencies ===");
        System.out.println("dependencyManagement = 'cennik' (deklaruje wersje, NIE dodaje zaleznosci).");
        System.out.println("dependencies         = 'zamowienie' (faktycznie dodaje zaleznosc do modulu).");
        System.out.println("Modul moze pominac <version>, jesli jest juz ustalona w dependencyManagement.");

        /*
         * ============================================================
         * 🔹 pluginManagement
         * ============================================================
         * Analogiczny mechanizm dla PLUGINÓW: <build><pluginManagement>
         * <plugins> deklaruje wersję/konfigurację pluginu DLA modułów
         * dzieci, ale plugin FAKTYCZNIE zadziała w module tylko, jeśli
         * ten moduł go też wymieni w swoim <build><plugins> (bez
         * <version> - odziedziczonej z pluginManagement).
         */

        System.out.println("\n=== pluginManagement ===");
        System.out.println("Jak dependencyManagement, ale dla pluginow - deklaruje wersje/config,");
        System.out.println("plugin uzyty faktycznie tylko, gdy modul go wymieni we wlasnym <plugins>.");

        /*
         * ============================================================
         * 🏗️ MULTI-MODULE PROJECT - PROJEKT WIELOMODUŁOWY
         * ============================================================
         * Duże aplikacje dzieli się na wiele MODUŁÓW (osobnych pod-
         * projektów Maven), każdy ze swoim pom.xml, ale wszystkie
         * "spięte" jednym PARENT POM z packaging="pom" (parent NIE
         * generuje własnego kodu - jest czystym "agregatorem").
         *
         * Typowy podział: core (logika domenowa, bez zależności od
         * innych modułów), persistence (dostęp do bazy danych, zależy
         * od core), web (REST API / servlety, zależy od core i
         * persistence).
         *
         * Zbudujemy teraz REALNĄ strukturę 2-modułową (core + app) na
         * dysku - z trzema plikami pom.xml.
         */

        System.out.println("\n=== MULTI-MODULE PROJECT ===");
        System.out.println("Parent (packaging=pom, agregator) + moduly (core, persistence, web...).");
        System.out.println("Typowy podzial: core (logika) <- persistence (baza) <- web (API), zaleznosci 'w gore'.");

        Path root = Files.createTempDirectory("javaquest-lesson14-multimodule");
        Path parentDir = root.resolve("parent");
        Path coreDir = parentDir.resolve("core");
        Path appDir = parentDir.resolve("app");
        Files.createDirectories(coreDir);
        Files.createDirectories(appDir);

        String centralVersion = "2.11.0"; // np. wersja gson, centralizowana w parencie

        String parentPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>com.example</groupId>
                    <artifactId>multi-module-demo</artifactId>
                    <version>1.0.0</version>
                    <packaging>pom</packaging>

                    <modules>
                        <module>core</module>
                        <module>app</module>
                    </modules>

                    <properties>
                        <java.version>21</java.version>
                        <gson.version>%s</gson.version>
                    </properties>

                    <dependencyManagement>
                        <dependencies>
                            <dependency>
                                <groupId>com.google.code.gson</groupId>
                                <artifactId>gson</artifactId>
                                <version>${gson.version}</version>
                            </dependency>
                            <!-- Wersja modulu core jest centralizowana tutaj -
                                 modul app nie musi jej podawac przy deklaracji zaleznosci -->
                            <dependency>
                                <groupId>com.example</groupId>
                                <artifactId>core</artifactId>
                                <version>${project.version}</version>
                            </dependency>
                        </dependencies>
                    </dependencyManagement>
                </project>
                """.formatted(centralVersion);

        String corePom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <parent>
                        <groupId>com.example</groupId>
                        <artifactId>multi-module-demo</artifactId>
                        <version>1.0.0</version>
                        <relativePath>../pom.xml</relativePath>
                    </parent>

                    <artifactId>core</artifactId>
                    <packaging>jar</packaging>

                    <dependencies>
                        <!-- Wersja gson odziedziczona z dependencyManagement parenta - BEZ <version>! -->
                        <dependency>
                            <groupId>com.google.code.gson</groupId>
                            <artifactId>gson</artifactId>
                        </dependency>
                    </dependencies>
                </project>
                """;

        String appPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <parent>
                        <groupId>com.example</groupId>
                        <artifactId>multi-module-demo</artifactId>
                        <version>1.0.0</version>
                        <relativePath>../pom.xml</relativePath>
                    </parent>

                    <artifactId>app</artifactId>
                    <packaging>jar</packaging>

                    <dependencies>
                        <!-- Zaleznosc na modul core - grupId/artifactId sie zgadzaja,
                             wersja odziedziczona z dependencyManagement parenta (${project.version}) -->
                        <dependency>
                            <groupId>com.example</groupId>
                            <artifactId>core</artifactId>
                        </dependency>
                    </dependencies>
                </project>
                """;

        Files.writeString(parentDir.resolve("pom.xml"), parentPom);
        Files.writeString(coreDir.resolve("pom.xml"), corePom);
        Files.writeString(appDir.resolve("pom.xml"), appPom);

        System.out.println("\n=== WYGENEROWANA STRUKTURA WIELOMODULOWA (zapisana na dysku) ===");
        System.out.println(parentDir);
        System.out.println("├── pom.xml            (packaging=pom, <modules>core, app</modules>)");
        System.out.println("├── core/");
        System.out.println("│   └── pom.xml        (<parent> -> multi-module-demo, zaleznosc na gson)");
        System.out.println("└── app/");
        System.out.println("    └── pom.xml        (<parent> -> multi-module-demo, zaleznosc na core)");

        System.out.println("\n--- parent/pom.xml ---");
        System.out.println(parentPom);
        System.out.println("--- parent/core/pom.xml ---");
        System.out.println(corePom);
        System.out.println("--- parent/app/pom.xml ---");
        System.out.println(appPom);

        System.out.println("=== JAK TO BY SIE ZBUDOWALO (poza tym demo) ===");
        System.out.println("W terminalu, w katalogu " + parentDir + ", polecenie 'mvn install'");
        System.out.println("zbudowaloby modul core PRZED app (Maven sam ustala kolejnosc modulow");
        System.out.println("na podstawie ich wzajemnych zaleznosci - tzw. reactor build order).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - properties centralizuje wartosci (np. wersje Javy/bibliotek)
         *   w jednym miejscu, uzywane przez ${nazwa}.
         * - profiles pozwalaja na alternatywne zestawy ustawien (dev/
         *   test/prod), aktywowane przez -P<id> albo activeByDefault.
         * - parent POM pozwala dziedziczyc properties/dependencyManagement/
         *   pluginManagement - tak jak pom.xml tego kursu dziedziczy po
         *   spring-boot-starter-parent.
         * - dependencyManagement DEKLARUJE wersje (nie dodaje zaleznosci),
         *   dependencies FAKTYCZNIE dodaje zaleznosc do modulu - moze
         *   pominac <version>, jesli jest juz ustalona wyzej.
         * - pluginManagement to ten sam mechanizm dla pluginow.
         * - Multi-module project = parent (packaging=pom) + <modules> +
         *   podmoduly z <parent><relativePath>../pom.xml</relativePath>,
         *   zaleznosci MIEDZY modulami dzialaja jak zwykle <dependency>
         *   (bo Maven po 'mvn install' zna kazdy modul jako artefakt).
         */

        Files.deleteIfExists(coreDir.resolve("pom.xml"));
        Files.deleteIfExists(appDir.resolve("pom.xml"));
        Files.deleteIfExists(parentDir.resolve("pom.xml"));
        Files.deleteIfExists(coreDir);
        Files.deleteIfExists(appDir);
        Files.deleteIfExists(parentDir);
        Files.deleteIfExists(root);

        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }
}
