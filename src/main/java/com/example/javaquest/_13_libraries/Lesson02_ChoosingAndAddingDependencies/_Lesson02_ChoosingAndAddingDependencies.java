package com.example.javaquest._13_libraries.Lesson02_ChoosingAndAddingDependencies;

public class _Lesson02_ChoosingAndAddingDependencies {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: WYBIERANIE I DODAWANIE ZALEZNOSCI ===");

        /*
         * ============================================================
         * 📦 SZYBKI RECAP: DODAWANIE ZALEZNOSCI W MAVEN I GRADLE
         * ============================================================
         * - Pelna teoria Maven/Gradle byla w rozdziale _11_buildtools -
         *   tutaj tylko krotkie przypomnienie skladni, zeby miec punkt
         *   odniesienia dla reszty lekcji.
         * - Maven (pom.xml) - zaleznosc to blok <dependency> w sekcji
         *   <dependencies>:
         *
         *     <dependency>
         *         <groupId>com.google.guava</groupId>
         *         <artifactId>guava</artifactId>
         *         <version>33.4.0-jre</version>
         *     </dependency>
         *
         * - Gradle (build.gradle, Groovy DSL) - zaleznosc to linia w
         *   bloku dependencies { }:
         *
         *     dependencies {
         *         implementation 'com.google.guava:guava:33.4.0-jre'
         *     }
         *
         * - Zauwaz: to TA SAMA trojka wspolrzednych (coordinates) w obu
         *   narzedziach - groupId:artifactId:version (w Gradle zapisane
         *   jako jeden String rozdzielony dwukropkami).
         */
        printMavenVsGradleSnippet();

        /*
         * ============================================================
         * 🔹 CZYTANIE WPISU NA MAVEN CENTRAL / MVNREPOSITORY.COM
         * ============================================================
         * - Kazda biblioteka opublikowana w Maven Central ma unikalne
         *   "wspolrzedne": groupId (najczesciej odwrocona domena autora,
         *   np. "com.google.guava"), artifactId (nazwa modulu, np.
         *   "guava"), version (konkretne wydanie, np. "33.4.0-jre").
         * - Serwis mvnrepository.com (nieoficjalna, ale bardzo popularna
         *   wyszukiwarka Maven Central) dla kazdej biblioteki pokazuje:
         *     * liste WSZYSTKICH wydanych wersji (od najnowszej) - warto
         *       sprawdzic, jak CZESTO biblioteka jest aktualizowana,
         *     * sekcje "Used By" - ile INNYCH artefaktow w Maven Central
         *       deklaruje ja jako zaleznosc (silny sygnal popularnosci -
         *       np. commons-lang3 ma "Used By" liczone w dziesiatkach
         *       tysiecy),
         *     * licencje, rozmiar plikow JAR, wymagana wersje Javy,
         *     * gotowy fragment XML/Gradle do skopiowania.
         * - Oficjalna wyszukiwarka to search.maven.org - mniej
         *   "przyjazna", ale to ZRODLO PRAWDY (to samo repozytorium, do
         *   ktorego laduja Twoje `mvn install`/`mvn deploy`).
         */
        printMvnrepositoryFields();

        /*
         * ============================================================
         * 🔍 mvn dependency:tree - WYKRYWANIE KONFLIKTOW I DUPLIKATOW
         * ============================================================
         * - Komenda `mvn dependency:tree` (z Mavena, rozdzial
         *   _11_buildtools) wypisuje PELNE drzewo zaleznosci projektu -
         *   nie tylko te, ktore sam dodales w pom.xml (BEZPOSREDNIE),
         *   ale takze te, ktore ONE ze soba ciagna (TRANSITYWNE).
         * - Przykladowy fragment outputu (uproszczony, jak wygladalby
         *   dla TEGO projektu):
         *
         *   [INFO] com.example:JavaQuest:jar:0.0.1-SNAPSHOT
         *   [INFO] +- org.springframework.boot:spring-boot-starter-web:jar:3.4.4:compile
         *   [INFO] |  +- org.springframework.boot:spring-boot-starter:jar:3.4.4:compile
         *   [INFO] |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:3.4.4:compile
         *   [INFO] |  |  |  +- ch.qos.logback:logback-classic:jar:1.5.12:compile
         *   [INFO] |  |  |  +- org.slf4j:slf4j-api:jar:2.0.16:compile
         *   [INFO] |  |  |  \- org.apache.logging.log4j:log4j-to-slf4j:jar:2.24.1:compile
         *   [INFO] |  |  \- org.yaml:snakeyaml:jar:2.3:compile
         *   [INFO] +- com.google.guava:guava:jar:33.4.0-jre:compile
         *   [INFO] \- (Zawiera) (Wersja X)
         *
         * - Gdy DWIE galezie drzewa chca DWOCH ROZNYCH wersji tej samej
         *   biblioteki (np. jedna zaleznosc ciagnie guava:30.0, druga
         *   guava:33.4.0-jre), Maven stosuje regule "nearest wins"
         *   (wygrywa wersja BLIZSZA korzeniowi drzewa, a przy remisie -
         *   ta zadeklarowana WCZESNIEJ w pom.xml) - ale to bywa
         *   zaskakujace, dlatego `dependency:tree` jest narzedziem
         *   PIERWSZEGO WYBORU przy diagnozowaniu blednych
         *   NoSuchMethodError/ClassNotFoundException w runtime, ktore
         *   czesto oznaczaja WLASNIE konflikt wersji.
         */
        printDependencyTreeExample();

        /*
         * ============================================================
         * 📦 ZALEZNOSCI TRANSITYWNE (TRANSITIVE DEPENDENCIES)
         * ============================================================
         * - Gdy dodajesz bibliteke X, Maven/Gradle AUTOMATYCZNIE dociaga
         *   rowniez WSZYSTKIE zaleznosci, ktorych X sama potrzebuje
         *   (i ich zaleznosci, i tak dalej - caly "graf zaleznosci").
         * - Ciekawostka z TEGO projektu: `spring-boot-starter-web`
         *   (dodany do pom.xml na starcie kursu, dla innych celow) w
         *   praktyce PRZYNOSI TRANSYTYWNIE m.in. logback-classic i
         *   slf4j-api (logowanie) oraz snakeyaml (parsowanie YAML) -
         *   dlatego w tym rozdziale mozemy uzywac SLF4J/Logback/
         *   SnakeYAML BEZ dodawania jakiejkolwiek nowej zaleznosci w
         *   pom.xml - one juz sa dostepne na classpath.
         * - To miecz obosieczny: wygodne (mniej pisania), ale oznacza,
         *   ze TWOJ projekt "dziedziczy" WSZYSTKIE ryzyka (rozmiar, CVE)
         *   zaleznosci transitywnych, nawet jesli nigdy nie napisales
         *   ich nazwy w pom.xml.
         */
        printTransitiveDependencyDemo();

        /*
         * ============================================================
         * 🔹 <scope> W MAVENIE - KIEDY ZALEZNOSC JEST POTRZEBNA
         * ============================================================
         * - Scope okresla, na jakim ETAPIE budowania/uruchamiania
         *   zaleznosc jest dostepna na classpath:
         *     * compile (DOMYSLNY) - dostepna wszedzie: kompilacja,
         *       testy, runtime. Wiekszosc zaleznosci (np. guava, okhttp
         *       w tym projekcie).
         *     * provided - potrzebna do KOMPILACJI, ale NIE trafia do
         *       finalnego artefaktu (bo "dostarczy" ja srodowisko
         *       uruchomieniowe albo dziala TYLKO w czasie kompilacji).
         *       PRZYKLAD Z TEGO PROJEKTU: Lombok - jego adnotacje
         *       (@Getter/@Setter/@Builder) sa przetwarzane WYLACZNIE
         *       podczas kompilacji (generuja bajtkod), Lombok nie jest
         *       potrzebny w runtime, wiec ma scope "provided" w
         *       pom.xml tego projektu.
         *     * runtime - NIE potrzebna do kompilacji (kod jej nie
         *       importuje wprost), ale potrzebna w trakcie DZIALANIA
         *       (np. sterownik JDBC laczony dynamicznie).
         *     * test - dostepna TYLKO przy kompilacji/uruchamianiu
         *       testow (np. JUnit) - nie trafia do finalnego JAR-a.
         */
        printScopeTable();

        /*
         * ============================================================
         * 🔍 <exclusions> - WYKLUCZANIE NIECHCIANEJ ZALEZNOSCI TRANSYTYWNEJ
         * ============================================================
         * - Czasem biblioteka X ciagnie transytywnie biblioteke Y, ktorej
         *   NIE CHCESZ (bo masz wlasna, inna wersje Y, albo Y ma znana
         *   podatnosc, albo po prostu jest zbedna).
         * - Rozwiazanie: <exclusions> wewnatrz <dependency>:
         *
         *     <dependency>
         *         <groupId>org.example</groupId>
         *         <artifactId>some-library</artifactId>
         *         <version>1.0.0</version>
         *         <exclusions>
         *             <exclusion>
         *                 <groupId>com.unwanted</groupId>
         *                 <artifactId>old-logging-framework</artifactId>
         *             </exclusion>
         *         </exclusions>
         *     </dependency>
         *
         * - Klasyczny, realny przyklad z ekosystemu Spring Boot: wiele
         *   starterow historycznie wykluczalo domyslny log4j na rzecz
         *   logback (albo odwrotnie) wlasnie w ten sposob - zeby uniknac
         *   DWOCH konkurencyjnych implementacji logowania na classpath
         *   jednoczesnie.
         */
        printExclusionsExample();

        /*
         * ============================================================
         * 🔍 BOM (BILL OF MATERIALS) - CENTRALNE ZARZADZANIE WERSJAMI
         * ============================================================
         * - BOM to specjalny plik POM typu "pom" (bez wlasnego kodu),
         *   ktory definiuje SPOJNY zestaw wersji dla GRUPY powiazanych
         *   bibliotek - importujesz go RAZ, a potem deklarujesz
         *   zaleznosci z tej grupy BEZ PODAWANIA WERSJI (BOM ja narzuca).
         * - PRZYKLAD Z TEGO PROJEKTU: `spring-boot-starter-parent`
         *   (zadeklarowany jako <parent> w pom.xml tego projektu) pelni
         *   role BOM-u dla calego ekosystemu Spring Boot. Dzieki temu
         *   zaleznosci takie jak HikariCP, Flyway czy Hibernate (dodane
         *   w rozdzialach _10_dao/_12_hibernate) sa zadeklarowane w
         *   pom.xml BEZ znacznika <version> - konkretna wersja jest
         *   WYLICZANA na podstawie wersji Spring Boota (3.4.4) i
         *   gwarantuje, ze wszystkie te biblioteki dzialaja ze soba
         *   PRZETESTOWANE w ZGODNYCH wersjach.
         * - Korzysc: przy aktualizacji Spring Boota (np. z 3.4.4 na
         *   3.5.0) WSZYSTKIE zarzadzane wersje aktualizuja sie RAZEM,
         *   spojnie - bez recznego przegladania dziesiatek numerkow
         *   wersji z osobna.
         * - W czystym Mavenie (bez rodzica typu "starter-parent") BOM
         *   importuje sie przez <dependencyManagement><dependencies>
         *   z <scope>import</scope> - to zaawansowany temat z
         *   _11_buildtools, tu wystarczy rozpoznac WZORZEC na przykladzie
         *   tego projektu.
         */
        printBomExplanation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Skladnia dodawania zaleznosci (Maven <dependency> / Gradle
         *   implementation) to tylko punkt startowy - PRAKTYCZNA
         *   umiejetnosc to czytanie wpisu na Maven Central/mvnrepository,
         *   analiza drzewa zaleznosci i swiadome zarzadzanie scope/
         *   exclusions/BOM.
         * - `mvn dependency:tree` to pierwsze narzedzie przy
         *   diagnozowaniu konfliktow wersji w runtime.
         * - Transitive dependencies to miecz obosieczny - sprawdzaj, co
         *   NAPRAWDE trafia na Twoj classpath (nie tylko to, co sam
         *   wpisales w pom.xml).
         * - <scope>provided</scope> (Lombok), <exclusions> i BOM
         *   (spring-boot-starter-parent) to trzy najczestsze "dzwignie"
         *   do swiadomego zarzadzania zaleznosciami w realnych projektach.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void printMavenVsGradleSnippet() {
        System.out.println("\n=== MAVEN vs GRADLE - TA SAMA ZALEZNOSC ===");
        System.out.println("Maven (pom.xml):");
        System.out.println("  <dependency>");
        System.out.println("      <groupId>com.google.guava</groupId>");
        System.out.println("      <artifactId>guava</artifactId>");
        System.out.println("      <version>33.4.0-jre</version>");
        System.out.println("  </dependency>");
        System.out.println("Gradle (build.gradle):");
        System.out.println("  implementation 'com.google.guava:guava:33.4.0-jre'");
    }

    private static void printMvnrepositoryFields() {
        System.out.println("\n=== CO SPRAWDZIC NA MVNREPOSITORY.COM ===");
        String format = "%-18s | %-50s%n";
        System.out.printf(format, "Sekcja", "Co mowi");
        System.out.println("-".repeat(72));
        System.out.printf(format, "Versions", "Jak czesto biblioteka jest aktualizowana");
        System.out.printf(format, "Used By", "Ile innych artefaktow jej uzywa (popularnosc)");
        System.out.printf(format, "License", "Apache 2.0 / MIT / GPL - warunki uzycia");
        System.out.printf(format, "Files", "Rozmiar JAR-a (wplyw na rozmiar aplikacji)");
    }

    private static void printDependencyTreeExample() {
        System.out.println("\n=== PRZYKLADOWY FRAGMENT 'mvn dependency:tree' ===");
        System.out.println("[INFO] com.example:JavaQuest:jar:0.0.1-SNAPSHOT");
        System.out.println("[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:3.4.4:compile");
        System.out.println("[INFO] |  \\- org.springframework.boot:spring-boot-starter:jar:3.4.4:compile");
        System.out.println("[INFO] |     +- ch.qos.logback:logback-classic:jar:1.5.12:compile");
        System.out.println("[INFO] |     +- org.slf4j:slf4j-api:jar:2.0.16:compile");
        System.out.println("[INFO] |     \\- org.yaml:snakeyaml:jar:2.3:compile");
        System.out.println("[INFO] \\- com.google.guava:guava:jar:33.4.0-jre:compile");
    }

    private static void printTransitiveDependencyDemo() {
        System.out.println("\n=== ZALEZNOSCI TRANSYTYWNE - CIEKAWOSTKA Z TEGO PROJEKTU ===");
        System.out.println("spring-boot-starter-web (bezposrednia)");
        System.out.println("  -> ciagnie logback-classic + slf4j-api (transitywnie, logowanie)");
        System.out.println("  -> ciagnie snakeyaml (transitywnie, parsowanie YAML)");
        System.out.println("Efekt: SLF4J/Logback/SnakeYAML dostepne BEZ dodawania nowej zaleznosci w pom.xml.");
    }

    private static void printScopeTable() {
        System.out.println("\n=== <scope> W MAVENIE ===");
        String format = "%-12s | %-20s | %-30s%n";
        System.out.printf(format, "Scope", "Dostepny w", "Przyklad");
        System.out.println("-".repeat(68));
        System.out.printf(format, "compile", "compile+test+runtime", "guava, okhttp (domyslny)");
        System.out.printf(format, "provided", "compile+test (NIE runtime)", "lombok (ten projekt)");
        System.out.printf(format, "runtime", "test+runtime (NIE compile)", "sterownik JDBC");
        System.out.printf(format, "test", "TYLKO test", "biblioteki testowe");
    }

    private static void printExclusionsExample() {
        System.out.println("\n=== <exclusions> - PRZYKLAD ===");
        System.out.println("<dependency>");
        System.out.println("    <groupId>org.example</groupId>");
        System.out.println("    <artifactId>some-library</artifactId>");
        System.out.println("    <version>1.0.0</version>");
        System.out.println("    <exclusions>");
        System.out.println("        <exclusion>");
        System.out.println("            <groupId>com.unwanted</groupId>");
        System.out.println("            <artifactId>old-logging-framework</artifactId>");
        System.out.println("        </exclusion>");
        System.out.println("    </exclusions>");
        System.out.println("</dependency>");
    }

    private static void printBomExplanation() {
        System.out.println("\n=== BOM - spring-boot-starter-parent W TYM PROJEKCIE ===");
        System.out.println("<parent>");
        System.out.println("    <groupId>org.springframework.boot</groupId>");
        System.out.println("    <artifactId>spring-boot-starter-parent</artifactId>");
        System.out.println("    <version>3.4.4</version>");
        System.out.println("</parent>");
        System.out.println("Efekt: HikariCP, Flyway, Hibernate itd. deklarowane BEZ <version> -");
        System.out.println("konkretna wersja jest narzucana centralnie przez BOM (spojnosc calego zestawu).");
    }
}
