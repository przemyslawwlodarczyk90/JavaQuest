package com.example.javaquest._11_buildtools.Lesson13_MavenPlugins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson13_MavenPlugins {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 13: MAVEN - PLUGINY ===");

        /*
         * ============================================================
         * 📦 CZYM SĄ PLUGINY MAVEN?
         * ============================================================
         * Sam Maven "z pudełka" robi zaskakująco MAŁO - nie umie
         * kompilować kodu, nie umie odpalać testów, nie umie budować
         * jara. Cała faktyczna PRACA jest wykonywana przez PLUGINY -
         * zewnętrzne moduły, które Maven ściąga (tak jak zależności!)
         * i którym "podwiesza" (binds) konkretne GOALE do konkretnych
         * FAZ cyklu życia (patrz Lesson11 - phase vs goal).
         *
         * Wiele pluginów jest dołączanych DOMYŚLNIE (masz do nich dostęp
         * bez deklarowania ich w pom.xml - np. maven-compiler-plugin
         * jest domyślnie podwiązany do fazy "compile"), ale żeby je
         * SKONFIGUROWAĆ (zmienić domyślne ustawienia), trzeba je
         * jawnie zadeklarować w <build><plugins> w pom.xml - tak jak
         * robi to pom.xml TEGO kursu z maven-compiler-plugin (source/
         * target 21) i spring-boot-maven-plugin.
         *
         * W tej lekcji skonfigurujemy 6 najczęściej używanych pluginów.
         */

        System.out.println("\n=== MAVEN SAM Z SIEBIE ROBI MALO - PLUGINY ROBIA KONKRETNE ZADANIA ===");
        System.out.println("compiler-plugin -> kompiluje, surefire-plugin -> odpala testy,");
        System.out.println("jar-plugin -> pakuje jar, war-plugin -> pakuje war, shade-plugin -> fat jar,");
        System.out.println("exec-plugin -> odpala aplikacje przez Maven.");

        Path tempDir = Files.createTempDirectory("javaquest-lesson13-maven");
        Path pomPath = tempDir.resolve("pom.xml");

        /*
         * ============================================================
         * 🔹 1) MAVEN-COMPILER-PLUGIN
         * ============================================================
         * Odpowiada za kompilację kodu (goal "compiler:compile" podwiązany
         * do fazy compile, "compiler:testCompile" do test-compile).
         * Kluczowa konfiguracja: source/target (starszy styl, osobno wersja
         * "źródła" i "bajtkodu docelowego") albo nowszy, prostszy
         * <release> (jedna wartość dla obu - polecane od Javy 9+).
         */
        String compilerPlugin = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>21</source>
                        <target>21</target>
                        <!-- Nowszy, prostszy zapis (rownowazny): <release>21</release> -->
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== 1) maven-compiler-plugin ===");
        System.out.println("Goal: compiler:compile (faza compile), compiler:testCompile (faza test-compile).");
        System.out.println(compilerPlugin);

        /*
         * ============================================================
         * 🔹 2) MAVEN-SUREFIRE-PLUGIN
         * ============================================================
         * Odpowiada za odpalanie testów jednostkowych w fazie "test"
         * (goal "surefire:test"). Domyślnie znajduje i odpala wszystkie
         * klasy w src/test/java o nazwach *Test, Test*, *Tests, *TestCase.
         * Generuje raporty w target/surefire-reports/ (pliki .txt i .xml).
         */
        String surefirePlugin = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <!-- Domyslne ustawienia zwykle wystarczaja - jawna deklaracja
                             przydaje sie np. do wymuszenia konkretnej wersji pluginu -->
                        <skipTests>false</skipTests>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== 2) maven-surefire-plugin ===");
        System.out.println("Goal: surefire:test (faza test). Raporty w target/surefire-reports/.");
        System.out.println(surefirePlugin);

        /*
         * ============================================================
         * 🔹 3) MAVEN-JAR-PLUGIN
         * ============================================================
         * Odpowiada za budowanie pliku .jar w fazie "package" (goal
         * "jar:jar") - to WŁAŚNIE ten plugin (domyślnie, nawet bez
         * jawnej deklaracji) tworzy target/<artifactId>-<version>.jar.
         * Jawna deklaracja pozwala np. ustawić Main-Class w manifeście,
         * żeby jar był odpalany przez "java -jar".
         *
         * ⚠️ WAŻNE: maven-jar-plugin pakuje TYLKO kod TWOJEGO projektu -
         * NIE dołącza zależności! Jeśli aplikacja potrzebuje np. gson
         * w runtime, a odpalisz "java -jar" na takim jarze bez gson na
         * classpath, dostaniesz NoClassDefFoundError.
         */
        String jarPlugin = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-jar-plugin</artifactId>
                    <configuration>
                        <archive>
                            <manifest>
                                <mainClass>com.example.App</mainClass>
                            </manifest>
                        </archive>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== 3) maven-jar-plugin ===");
        System.out.println("Goal: jar:jar (faza package). Buduje JAR TYLKO z kodu projektu - BEZ zaleznosci!");
        System.out.println(jarPlugin);

        /*
         * ============================================================
         * 🔹 4) MAVEN-WAR-PLUGIN
         * ============================================================
         * Odpowiada za budowanie pliku .war (Web Application Archive) w
         * fazie "package" - używany przy packaging="war", czyli
         * projektach aplikacji servletowych (patrz rozdział _07_servlets
         * i Lesson15 tego rozdziału). Domyślnie szuka zasobów webowych
         * (WEB-INF/web.xml itd.) w src/main/webapp - konfigurowalne
         * przez <warSourceDirectory>.
         */
        String warPlugin = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-war-plugin</artifactId>
                    <configuration>
                        <warSourceDirectory>src/main/webapp</warSourceDirectory>
                        <failOnMissingWebXml>false</failOnMissingWebXml>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== 4) maven-war-plugin ===");
        System.out.println("Goal: war:war (faza package). Uzywany przy packaging=war (aplikacje servletowe).");
        System.out.println(warPlugin);

        /*
         * ============================================================
         * 🔹 5) MAVEN-SHADE-PLUGIN
         * ============================================================
         * Buduje "FAT JAR" (zwany też "uber-jar") - jar zawierający NIE
         * TYLKO kod projektu, ale też WSZYSTKIE jego zależności
         * rozpakowane i połączone w jeden plik. To rozwiązuje problem
         * z maven-jar-plugin: taki jar można odpalić samodzielnie
         * ("java -jar app.jar") na maszynie, która nie ma ~/.m2 ani
         * internetu, bo WSZYSTKO potrzebne jest już w środku.
         *
         * <transformers><transformer implementation="...ManifestResourceTransformer">
         * dodaje/scala odpowiednie wpisy manifestu (m.in. Main-Class) z
         * uwzględnieniem manifestów z bibliotek zależności.
         */
        String shadePlugin = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-shade-plugin</artifactId>
                    <version>3.6.0</version>
                    <executions>
                        <execution>
                            <phase>package</phase>
                            <goals>
                                <goal>shade</goal>
                            </goals>
                            <configuration>
                                <transformers>
                                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                        <mainClass>com.example.App</mainClass>
                                    </transformer>
                                </transformers>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
                """;
        System.out.println("\n=== 5) maven-shade-plugin ===");
        System.out.println("Goal: shade:shade (dowiazany rownie do fazy package). Buduje FAT JAR - kod + WSZYSTKIE zaleznosci.");
        System.out.println(shadePlugin);

        /*
         * ============================================================
         * ⚔️ maven-jar-plugin vs maven-shade-plugin - PORÓWNANIE
         * ============================================================
         * To bardzo częsty punkt zamieszania u początkujących:
         *
         *   maven-jar-plugin  -> jar zawiera TYLKO klasy Twojego projektu.
         *                        Żeby go odpalić, trzeba osobno dostarczyć
         *                        zależności na classpath (np.
         *                        "java -cp app.jar:gson.jar:... App").
         *
         *   maven-shade-plugin -> jar zawiera Twój kod ORAZ rozpakowaną
         *                        zawartość WSZYSTKICH zależności w jednym
         *                        pliku. Odpalasz go samodzielnie:
         *                        "java -jar app.jar" - i to wystarczy,
         *                        bez dodatkowego classpath.
         *
         * Fat jar jest większy (zawiera kopie bibliotek), ale wygodniejszy
         * do dystrybucji - jeden plik = cała aplikacja.
         */

        System.out.println("\n=== POROWNANIE: plain JAR (jar-plugin) vs FAT JAR (shade-plugin) ===");
        System.out.printf("%-25s | %-30s%n", "maven-jar-plugin", "maven-shade-plugin");
        System.out.println("-".repeat(60));
        System.out.printf("%-25s | %-30s%n", "tylko kod projektu", "kod projektu + WSZYSTKIE zaleznosci");
        System.out.printf("%-25s | %-30s%n", "maly plik", "duzy plik (zawiera kopie bibliotek)");
        System.out.printf("%-25s | %-30s%n", "wymaga classpath recznie", "'java -jar' - samodzielny, gotowy do dystrybucji");

        /*
         * ============================================================
         * 🔹 6) EXEC-MAVEN-PLUGIN
         * ============================================================
         * Pozwala odpalić aplikację Java BEZPOŚREDNIO przez Maven, bez
         * ręcznego budowania jara i wołania "java -jar" - przydatne przy
         * szybkiej iteracji podczas developmentu:
         *
         *   mvn exec:java
         *
         * Goal "exec:java" (z org.codehaus.mojo:exec-maven-plugin) odpala
         * klasę wskazaną w <mainClass>, korzystając z classpath już
         * zbudowanego przez Mavena (włącznie z zależnościami).
         */
        String execPlugin = """
                <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>exec-maven-plugin</artifactId>
                    <version>3.2.0</version>
                    <configuration>
                        <mainClass>com.example.App</mainClass>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== 6) exec-maven-plugin ===");
        System.out.println("Goal: exec:java - odpala aplikacje przez Maven ('mvn exec:java'), bez recznego 'java -jar'.");
        System.out.println(execPlugin);

        // Złóżmy wszystkie 6 pluginów w jeden pełny pom.xml i zapiszmy na dysk.
        String fullPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>com.example</groupId>
                    <artifactId>plugins-demo</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>

                    <build>
                        <plugins>
                            %s
                            %s
                            %s
                            %s
                            %s
                            %s
                        </plugins>
                    </build>
                </project>
                """.formatted(
                indent(compilerPlugin), indent(surefirePlugin), indent(jarPlugin),
                indent(warPlugin), indent(shadePlugin), indent(execPlugin));

        Files.writeString(pomPath, fullPom);
        System.out.println("\n=== KOMPLETNY POM.XML Z 6 PLUGINAMI (zapisany na dysku) ===");
        System.out.println("Sciezka: " + pomPath);
        System.out.println("(pelna trzesc wypisana wyzej sekcja po sekcji)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Maven sam z siebie robi malo - pluginy wykonuja faktyczna
         *   prace (kazdy plugin dostarcza jeden lub wiecej "goali").
         * - maven-compiler-plugin -> kompilacja (source/target/release).
         * - maven-surefire-plugin -> testy jednostkowe + raporty.
         * - maven-jar-plugin -> buduje JAR (tylko kod projektu, bez
         *   zaleznosci) - moze ustawic Main-Class w manifeście.
         * - maven-war-plugin -> buduje WAR (aplikacje servletowe).
         * - maven-shade-plugin -> buduje FAT JAR (kod + WSZYSTKIE
         *   zaleznosci w jednym pliku, gotowy do "java -jar" bez
         *   dodatkowego classpath).
         * - exec-maven-plugin -> odpala aplikacje bezposrednio przez
         *   Maven ("mvn exec:java"), przydatne w developmencie.
         * - Kluczowa roznica: plain jar (maly, wymaga recznego classpath)
         *   vs fat jar / shade (duzy, samodzielny, latwy do dystrybucji).
         */

        Files.deleteIfExists(pomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static String indent(String xml) {
        return xml.trim().lines()
                .map(line -> "        " + line)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
