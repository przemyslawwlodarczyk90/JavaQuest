package com.example.javaquest._11_buildtools.Lesson19_BuildMigrations;

public class _Lesson19_BuildMigrations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: MIGRACJE MIEDZY BUILD TOOLAMI ===");

        /*
         * ============================================================
         * 📦 PUNKT WYJŚCIA: REALISTYCZNY, LEGACY build.xml
         * ============================================================
         * Załóżmy typowy, mały, ale realistyczny projekt Ant - podobny
         * do tych z lekcji 03-08 tego rozdziału - z 3 targetami: clean,
         * compile, jar (jar zależy od compile). To jest nasz "punkt
         * startowy" migracji - build.xml, który mógłby istnieć w firmie
         * od wielu lat.
         */

        String legacyBuildXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project name="legacy-app" default="jar" basedir=".">

                    <property name="src.dir" value="src"/>
                    <property name="build.dir" value="build"/>
                    <property name="classes.dir" value="${build.dir}/classes"/>
                    <property name="dist.dir" value="${build.dir}/dist"/>

                    <target name="clean" description="Usuwa katalog build">
                        <delete dir="${build.dir}"/>
                    </target>

                    <target name="compile" description="Kompiluje kod zrodlowy">
                        <mkdir dir="${classes.dir}"/>
                        <javac srcdir="${src.dir}" destdir="${classes.dir}" includeantruntime="false"/>
                    </target>

                    <target name="jar" depends="compile" description="Pakuje do JAR-a">
                        <mkdir dir="${dist.dir}"/>
                        <jar destfile="${dist.dir}/legacy-app.jar" basedir="${classes.dir}">
                            <manifest>
                                <attribute name="Main-Class" value="com.example.legacy.Main"/>
                            </manifest>
                        </jar>
                    </target>

                </project>
                """;

        System.out.println("\n=== PUNKT WYJSCIA: build.xml (Ant, legacy) ===");
        System.out.println(legacyBuildXml);

        /*
         * ============================================================
         * 🔹 MIGRACJA ANT -> MAVEN
         * ============================================================
         * Krok 1: ANALIZA build.xml - identyfikujemy targety i co
         * faktycznie robią:
         * - clean   -> usuwa build/          (Maven ma to WBUDOWANE - "mvn clean")
         * - compile -> kompiluje src -> classes  (Maven: faza "compile", WBUDOWANA)
         * - jar     -> pakuje classes do .jar, z Main-Class w manifeście
         *   (Maven: faza "package" + maven-jar-plugin, Main-Class przez
         *   konfigurację <archive><manifest><mainClass>)
         *
         * Krok 2: MAPOWANIE struktury katalogów - "src" (płaski, wszystko
         * w jednym katalogu) trzeba PRZENIEŚĆ do "src/main/java" (Maven
         * wymaga tej konwencji, chyba że nadpiszemy <sourceDirectory> -
         * ale to strzela sobie w kolano, lepiej się dopasować).
         *
         * Krok 3: ZALEŻNOŚCI (lib/*.jar -> <dependencies>) - jeśli Ant
         * miał JAR-y w lib/, trzeba dla KAŻDEGO znaleźć jego koordynaty
         * Maven (groupId:artifactId:version) i dodać jako <dependency> -
         * to często najbardziej czasochłonny krok, zwłaszcza dla starych/
         * niestandardowych bibliotek, które mogą NIE ISTNIEĆ w Maven
         * Central (trzeba je zainstalować do lokalnego/firmowego repo).
         *
         * Krok 4: PROBLEM CUSTOM TASKÓW - jeśli build.xml miał
         * niestandardowe targety (np. generowanie kodu, transformacje
         * XSLT, wywołania zewnętrznych skryptów), Maven NIE MA
         * bezpośredniego odpowiednika "swobodnego kodu" jak Ant -
         * trzeba znaleźć ISTNIEJĄCY plugin robiący to samo, albo NAPISAĆ
         * WŁASNY plugin Maven (dużo więcej pracy niż jeden target Ant),
         * albo użyć maven-antrun-plugin (uruchamia... fragmenty Anta
         * wewnątrz Mavena - częste, pragmatyczne rozwiązanie przejściowe).
         *
         * Krok 5: NIESTANDARDOWA STRUKTURA - jeśli projekt Ant ma
         * nietypowy układ katalogów (nie da się łatwo dopasować do
         * konwencji), to jest SYGNAŁ OSTRZEGAWCZY - migracja do Mavena
         * będzie bolesna, może się nie opłacać (patrz "Gradle -> Maven"
         * niżej, gdzie ograniczenia są podobne).
         */

        String migratedPomXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>com.example</groupId>
                    <artifactId>legacy-app</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>

                    <properties>
                        <maven.compiler.source>21</maven.compiler.source>
                        <maven.compiler.target>21</maven.compiler.target>
                    </properties>

                    <build>
                        <plugins>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-jar-plugin</artifactId>
                                <configuration>
                                    <archive>
                                        <manifest>
                                            <mainClass>com.example.legacy.Main</mainClass>
                                        </manifest>
                                    </archive>
                                </configuration>
                            </plugin>
                        </plugins>
                    </build>
                </project>
                """;

        System.out.println("\n=== ANT -> MAVEN: MAPOWANIE TARGET -> LIFECYCLE/PLUGIN ===");
        System.out.println("target 'clean'          -> wbudowana faza 'clean' (mvn clean) - nic do pisania");
        System.out.println("target 'compile'        -> wbudowana faza 'compile' - nic do pisania");
        System.out.println("target 'jar' (depends='compile') -> faza 'package' + maven-jar-plugin");
        System.out.println("                                     (dependsOn 'compile' jest AUTOMATYCZNE - lifecycle)");
        System.out.println("<manifest><attribute Main-Class>  -> <archive><manifest><mainClass> w konfiguracji pluginu");
        System.out.println("src (plaski katalog)               -> PRZENIESC do src/main/java (konwencja Mavena)");
        System.out.println("lib/*.jar                          -> znalezc koordynaty Maven, dodac <dependency>");
        System.out.println("custom targety (jesli byly)         -> istniejacy plugin, wlasny plugin, albo maven-antrun-plugin");

        System.out.println("\n=== PO MIGRACJI: pom.xml ===");
        System.out.println(migratedPomXml);

        /*
         * ============================================================
         * 🔍 MIGRACJA ANT -> GRADLE
         * ============================================================
         * Filozofia migracji jest INNA niż Ant->Maven, bo Gradle jest
         * bliższy Antowi duchem (programowalność) - migracja bywa
         * ŁATWIEJSZA w utrzymaniu "niestandardowej logiki":
         *
         * - Każdy target Ant -> odpowiadający mu Gradle TASK (tasks.register)
         * - depends="..." w Ant       -> dependsOn(...) w Gradle
         * - <javac srcdir="src">      -> sourceSets { main { java.srcDir 'src' } }
         *   (albo po prostu przenosimy pliki do src/main/java i korzystamy
         *   z domyślnego source setu - czystsze rozwiązanie)
         * - lib/*.jar                 -> dependencies { implementation files('lib/x.jar') }
         *   (Gradle POZWALA odwołać się do lokalnego pliku JAR bez
         *   repozytorium - "files(...)" - to przydatna "łatka" na czas
         *   migracji, zanim znajdziemy właściwe koordynaty Maven dla
         *   każdej starej biblioteki)
         * - custom logika Ant (<script>, niestandardowe kroki) -> DUŻO
         *   łatwiej przenieść niż do Mavena, bo Gradle task to zwykły
         *   kod Groovy/Kotlin - często wystarczy przepisać logikę "1:1"
         *   do doLast {} bloku.
         */

        String migratedBuildGradle = """
                plugins {
                    id 'java'
                    id 'application'
                }

                sourceSets {
                    main {
                        java.srcDir 'src'
                    }
                }

                dependencies {
                    implementation files('lib/some-legacy-lib.jar')
                }

                application {
                    mainClass = 'com.example.legacy.Main'
                }

                tasks.named('jar') {
                    archiveFileName = 'legacy-app.jar'
                }
                """;

        System.out.println("\n=== ANT -> GRADLE: MAPOWANIE TARGET -> TASK ===");
        System.out.println("target 'clean'   -> wbudowany task 'clean' (plugin 'java') - nic do pisania");
        System.out.println("target 'compile' -> wbudowany task 'compileJava' - nic do pisania (po ew. przeniesieniu src)");
        System.out.println("target 'jar' (depends='compile') -> wbudowany task 'jar' (dependsOn AUTOMATYCZNY na compileJava)");
        System.out.println("lib/*.jar bez repo   -> dependencies { implementation files('lib/x.jar') } (lokalny plik!)");
        System.out.println("custom <script>/logika -> zwykly kod Groovy/Kotlin w doLast {} custom taska (LATWA migracja)");

        System.out.println("\n=== PO MIGRACJI: build.gradle ===");
        System.out.println(migratedBuildGradle);

        /*
         * ============================================================
         * 🔹 MIGRACJA MAVEN -> GRADLE
         * ============================================================
         * To najczęstsza migracja "w drugą stronę" w praktyce (firmy
         * przechodzące z Mavena na Gradle, rzadziej odwrotnie).
         * Kluczowe różnice do przemapowania:
         *
         * - <dependencies>  -> dependencies { } (składnia inna, ale
         *   1:1 mapowanie: <dependency><groupId>g</groupId><artifactId>a</artifactId>
         *   <version>v</version></dependency> -> "implementation 'g:a:v'";
         *   scope "compile" -> "implementation"/"api", "provided" ->
         *   "compileOnly", "test" -> "testImplementation")
         * - <plugins>       -> plugins { id '...' } (ale UWAGA: pluginy
         *   Maven i Gradle to RÓŻNE ekosystemy - trzeba znaleźć
         *   ODPOWIEDNIK w świecie Gradle, nie da się "przekopiować" 1:1;
         *   np. maven-compiler-plugin nie ma odpowiednika, bo Gradle
         *   ma to wbudowane w plugin "java")
         * - LIFECYCLE (fazy) vs TASKS - trzeba przemyśleć, że fazy
         *   Mavena (validate->compile->test->package...) to SEKWENCJA,
         *   a taski Gradle to GRAF ZALEŻNOŚCI (bardziej elastyczny) -
         *   zazwyczaj mapowanie 1:1 istnieje (package -> build/jar),
         *   ale kolejność może się różnić w bardziej złożonych buildach.
         * - MULTI-MODULE (Maven, <modules>) vs MULTI-PROJECT (Gradle,
         *   include w settings.gradle) - koncepcyjnie identyczne, ale
         *   Gradle wymaga OSOBNEGO pliku settings.gradle (Maven ma to
         *   w tym samym pom.xml rodzica).
         */

        System.out.println("\n=== MAVEN -> GRADLE: MAPOWANIE ===");
        System.out.println("<dependencies>/<dependency>     -> dependencies { implementation 'g:a:v' }");
        System.out.println("scope 'compile'                 -> implementation (albo 'api' jesli eksponowana dalej)");
        System.out.println("scope 'provided'                -> compileOnly");
        System.out.println("scope 'test'                     -> testImplementation");
        System.out.println("<plugins>                        -> plugins { id '...' } (INNY ekosystem, szukaj odpowiednika!)");
        System.out.println("faza 'package'                  -> task 'build'/'jar' (Gradle: graf zaleznosci, nie sekwencja)");
        System.out.println("<modules> (multi-module, rodzic) -> include(...) w OSOBNYM settings.gradle (multi-project)");

        /*
         * ============================================================
         * 🔍 MIGRACJA GRADLE -> MAVEN: KIEDY MA SENS I OGRANICZENIA
         * ============================================================
         * Rzadszy kierunek w praktyce, ale zdarza się (np. firmowa
         * standaryzacja na Mavenie, wymóg audytu/compliance preferujący
         * bardziej "sztywne" narzędzie). Kiedy ma sens:
         * - Projekt Gradle NIE UŻYWA zaawansowanych custom tasks/logiki
         *   Groovy/Kotlin (tylko standardowe pluginy java/application) -
         *   migracja jest w takim przypadku prosta (odwrotność mapowania
         *   wyżej).
         * - Firma potrzebuje JEDNOLITEGO narzędzia we wszystkich
         *   zespołach, a większość projektów już jest na Mavenie.
         *
         * OGRANICZENIA (kiedy NIE warto/się nie da łatwo):
         * - Jeśli build.gradle ma DUŻO custom tasks z logiką Groovy/
         *   Kotlin (pętle, warunki, generowanie plików w locie) - Maven
         *   NIE MA odpowiednika "swobodnego kodu" (trzeba pisać plugin
         *   Maven w Javie, dużo więcej pracy niż jeden Gradle task).
         * - Build cache/incremental build z Gradle NIE MA odpowiednika
         *   o takiej samej wydajności w Mavenie - migracja może
         *   ZNACZĄCO wydłużyć czas budowania.
         * - Multi-project buildy z zawiłymi zależnościami między
         *   podprojektami (project(":x")) wymagają starannego
         *   przemodelowania na multi-module Maven (<modules>, wersje
         *   zarządzane w rodzicu).
         */

        System.out.println("\n=== GRADLE -> MAVEN: KIEDY MA SENS / OGRANICZENIA ===");
        System.out.println("MA SENS, gdy: projekt uzywa tylko standardowych pluginow, firma standaryzuje na Mavenie.");
        System.out.println("OGRANICZENIA: custom tasks z logika Groovy/Kotlin (brak odpowiednika bez wlasnego pluginu),");
        System.out.println("utrata build cache/incremental build (wydajnosc), zlozone multi-project -> multi-module.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ant -> Maven: analiza targetow, mapowanie na lifecycle/pluginy,
         *   przeniesienie struktury katalogow do konwencji, znalezienie
         *   koordynatow Maven dla lib/*.jar, custom targety = najwiekszy
         *   problem (maven-antrun-plugin jako "protez").
         * - Ant -> Gradle: targety -> taski (1:1, dependsOn), lokalne
         *   JAR-y przez files(...), custom logika Anta LATWIEJ przenosi
         *   sie do Gradle (bo Gradle rowniez jest programowalny).
         * - Maven -> Gradle: dependencies/scope -> implementation/api/
         *   compileOnly/testImplementation, pluginy trzeba mapowac na
         *   INNY ekosystem (nie 1:1), multi-module -> multi-project
         *   (settings.gradle).
         * - Gradle -> Maven: sensowne dla prostych projektow bez custom
         *   logiki; problematyczne przy zaawansowanych custom taskach i
         *   buildach zaleznych od cache/incremental build.
         */

        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }
}
