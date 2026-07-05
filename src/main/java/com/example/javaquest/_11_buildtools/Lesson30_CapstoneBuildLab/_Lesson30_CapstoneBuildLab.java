package com.example.javaquest._11_buildtools.Lesson30_CapstoneBuildLab;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * LEKCJA 30: "JAVAQUEST BUILD LAB" - KAPSTON CALEGO ROZDZIALU _11_buildtools.
 *
 * Ten sam mini-projekt (model/service/repository + config.properties + Gson +
 * "testy" + JAR) zbudowany w TRZECH wersjach - Ant (REALNIE wykonany w tym
 * demo), Maven i Gradle (wygenerowane jako tresc plikow, z komentarzem -
 * zgodnie ze strategia calego rozdzialu, patrz CLAUDE.md).
 */
public class _Lesson30_CapstoneBuildLab {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 30: JAVAQUEST BUILD LAB - KAPSTON ROZDZIALU ===");
        System.out.println("Ten sam mini-projekt (model/service/repository, config.properties, Gson, testy,");
        System.out.println("JAR) zbudowany w 3 wersjach: Ant (REALNIE, ponizej), Maven i Gradle (tekst + opis).");

        /*
         * ============================================================
         * 📦 PROJEKT: "BUILD LAB" - CO BUDUJEMY
         * ============================================================
         * - model:      Produkt (id, nazwa, cena)
         * - repository: ProduktRepository (in-memory lista produktow)
         * - service:    ProduktService (logika biznesowa - suma cen)
         * - Main        - odczytuje config.properties (nazwa/wersja apki),
         *                 dodaje produkty, wypisuje raport + JSON (Gson)
         * - "test"      - SelfCheckMain, prosta samo-weryfikacja logiki
         *                 serwisu (patrz wyjasnienie projektowe nizej)
         * - pakowanie   - JAR z Main-Class w manifescie, runnable
         *
         * 📌 DECYZJA PROJEKTOWA (test bez frameworka JUnit w LIVE demo):
         * Brief wspomina JUnit jako czesc tresci tej lekcji - i pom.xml/
         * build.gradle nizej DEKLARUJA zaleznosc testowa (junit-jupiter)
         * zgodnie z tym briefem. Ale do REALNEGO, embedowanego uruchomienia
         * w tym demo (Ant) uzywamy prostej klasy SelfCheckMain (main() z
         * recznymi asercjami + System.exit(1) przy niepowodzeniu) zamiast
         * prawdziwego <junitlauncher>/<junit> taska Anta - bo wymagaloby to
         * dolaczenia biblioteki JUnit na classpath TEGO embedowanego builda
         * (dodatkowa zmienna, ktora moglaby uczynic demo bardziej kruchym
         * na roznych maszynach/JDK). SelfCheckMain jest odpalany jako REALNY
         * target Ant (<java ... failonerror="true">) - to wciaz prawdziwe
         * wykonanie testu, tylko bez samego frameworka JUnit. Sekcje
         * Maven/Gradle nizej pokazuja, jak wygladalby TEN SAM projekt z
         * prawdziwym JUnit 5 (surefire/test{}).
         */

        System.out.println("\n=== STRUKTURA PROJEKTU 'BUILD LAB' ===");
        System.out.println("model/Produkt, repository/ProduktRepository, service/ProduktService, Main,");
        System.out.println("config.properties (zasob), Gson (zewnetrzna biblioteka), SelfCheckMain ('test'),");
        System.out.println("JAR z Main-Class - ten SAM projekt, 3 razy, innym narzedziem budowania.");

        try {
            buildAndRunAntVersion();
        } catch (Exception e) {
            System.out.println("\n(Live-demo wersji Ant nie powiodlo sie technicznie na tej maszynie: " + e);
            System.out.println(" Kontynuujemy lekcje - sekcje Maven/Gradle nizej sa czysto opisowe i");
            System.out.println(" nie zaleza od powodzenia powyzszego live-demo.)");
        }

        printMavenVersion();
        printGradleVersion();
        printCrossToolMapping();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU _11_buildtools
         * ============================================================
         * - Ant (Lekcje 03-10): imperatywny, embedowany REALNY silnik w
         *   tym kursie, pelna kontrola, zero konwencji.
         * - Maven (Lekcje 11-15): deklaratywny, konwencja + lifecycle,
         *   generowany pom.xml, bez embedowania (brak prostego API).
         * - Gradle (Lekcje 16-22): hybryda, DSL Groovy/Kotlin, generowany
         *   build.gradle, bez embedowania (brak wrappera w tym repo) -
         *   ROZBUDOWANY blok: podstawy/advanced, zarzadzanie zaleznosciami
         *   (version catalogs/BOM), testy+JaCoCo, ekosystem pluginow
         *   (Shadow/Spring Boot/Checkstyle), publikowanie (maven-publish),
         *   troubleshooting/wydajnosc (daemon/cache/toolchain).
         * - Lekcje 23-26: porownanie, migracje, praktyka zawodowa,
         *   troubleshooting ogolny (JVM+Ant, z REALNIE wywolanymi bledami).
         * - Lekcja 30 (ta): TEN SAM projekt zbudowany 3 narzedziami na raz -
         *   najlepszy dowod, ze to WYBOR NARZEDZIA, nie WYBOR MOZLIWOSCI -
         *   wszystkie 3 potrafia zbudowac dokladnie to samo.
         */

        System.out.println("\n=== KONIEC LEKCJI 30 - KONIEC ROZDZIALU _11_buildtools ===");
    }

    // ============================================================
    // 🔹 WERSJA ANT - REALNIE ZBUDOWANA I ODPALONA W TYM DEMO
    // ============================================================

    private static void buildAndRunAntVersion() throws Exception {
        System.out.println("\n\n########## WERSJA ANT (REALNIE WYKONANA) ##########");

        Path root = Files.createTempDirectory("javaquest-buildlab-ant");
        Path srcMain = root.resolve("src/main/java/com/example/buildlab");
        Path srcModel = srcMain.resolve("model");
        Path srcRepo = srcMain.resolve("repository");
        Path srcService = srcMain.resolve("service");
        Path srcTest = root.resolve("src/test/java/com/example/buildlab");
        Path resources = root.resolve("src/main/resources");
        Files.createDirectories(srcModel);
        Files.createDirectories(srcRepo);
        Files.createDirectories(srcService);
        Files.createDirectories(srcTest);
        Files.createDirectories(resources);

        Files.writeString(srcModel.resolve("Produkt.java"), """
                package com.example.buildlab.model;

                public class Produkt {
                    private final int id;
                    private final String nazwa;
                    private final double cena;

                    public Produkt(int id, String nazwa, double cena) {
                        this.id = id;
                        this.nazwa = nazwa;
                        this.cena = cena;
                    }

                    public int getId() { return id; }
                    public String getNazwa() { return nazwa; }
                    public double getCena() { return cena; }

                    @Override
                    public String toString() {
                        return "Produkt{id=" + id + ", nazwa='" + nazwa + "', cena=" + cena + "}";
                    }
                }
                """);

        Files.writeString(srcRepo.resolve("ProduktRepository.java"), """
                package com.example.buildlab.repository;

                import com.example.buildlab.model.Produkt;
                import java.util.ArrayList;
                import java.util.List;

                public class ProduktRepository {
                    private final List<Produkt> produkty = new ArrayList<>();

                    public void zapisz(Produkt p) {
                        produkty.add(p);
                    }

                    public List<Produkt> znajdzWszystkie() {
                        return new ArrayList<>(produkty);
                    }
                }
                """);

        Files.writeString(srcService.resolve("ProduktService.java"), """
                package com.example.buildlab.service;

                import com.example.buildlab.model.Produkt;
                import com.example.buildlab.repository.ProduktRepository;
                import java.util.List;

                public class ProduktService {
                    private final ProduktRepository repository;

                    public ProduktService(ProduktRepository repository) {
                        this.repository = repository;
                    }

                    public void dodajProdukt(int id, String nazwa, double cena) {
                        repository.zapisz(new Produkt(id, nazwa, cena));
                    }

                    public double obliczSumaCen() {
                        double suma = 0;
                        for (Produkt p : repository.znajdzWszystkie()) {
                            suma += p.getCena();
                        }
                        return suma;
                    }

                    public List<Produkt> pobierzWszystkie() {
                        return repository.znajdzWszystkie();
                    }
                }
                """);

        Files.writeString(srcMain.resolve("Main.java"), """
                package com.example.buildlab;

                import com.example.buildlab.model.Produkt;
                import com.example.buildlab.repository.ProduktRepository;
                import com.example.buildlab.service.ProduktService;
                import com.google.gson.Gson;
                import com.google.gson.GsonBuilder;

                import java.io.InputStream;
                import java.util.List;
                import java.util.Properties;

                public class Main {
                    public static void main(String[] args) throws Exception {
                        Properties config = new Properties();
                        try (InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
                            if (in != null) {
                                config.load(in);
                            }
                        }
                        System.out.println("=== " + config.getProperty("app.name", "BuildLab")
                                + " v" + config.getProperty("app.version", "?") + " ===");

                        ProduktRepository repository = new ProduktRepository();
                        ProduktService service = new ProduktService(repository);
                        service.dodajProdukt(1, "Klawiatura", 150.0);
                        service.dodajProdukt(2, "Monitor", 800.0);
                        service.dodajProdukt(3, "Myszka", 60.0);

                        List<Produkt> wszystkie = service.pobierzWszystkie();
                        System.out.println("Produkty:");
                        for (Produkt p : wszystkie) {
                            System.out.println(" - " + p);
                        }
                        System.out.println("Suma cen: " + service.obliczSumaCen());

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        System.out.println("JSON (Gson):");
                        System.out.println(gson.toJson(wszystkie));
                    }
                }
                """);

        Files.writeString(srcTest.resolve("SelfCheckMain.java"), """
                package com.example.buildlab;

                import com.example.buildlab.repository.ProduktRepository;
                import com.example.buildlab.service.ProduktService;

                public class SelfCheckMain {
                    public static void main(String[] args) {
                        ProduktRepository repo = new ProduktRepository();
                        ProduktService service = new ProduktService(repo);
                        service.dodajProdukt(1, "A", 10.0);
                        service.dodajProdukt(2, "B", 20.0);

                        double suma = service.obliczSumaCen();
                        if (Math.abs(suma - 30.0) > 0.0001) {
                            System.out.println("TEST NIEPOWODZENIE: oczekiwano 30.0, otrzymano " + suma);
                            System.exit(1);
                        }
                        if (service.pobierzWszystkie().size() != 2) {
                            System.out.println("TEST NIEPOWODZENIE: oczekiwano 2 produktow");
                            System.exit(1);
                        }
                        System.out.println("WSZYSTKIE TESTY (uproszczone, bez frameworka JUnit) PRZESZLY.");
                    }
                }
                """);

        Files.writeString(resources.resolve("config.properties"), """
                app.name=JavaQuest Build Lab
                app.version=1.0.0
                env=dev
                """);

        // Gson potrzebny do kompilacji/uruchomienia Main - lokalizujemy go w lokalnym
        // repozytorium Maven (~/.m2/repository), bo pom.xml tego kursu juz go deklaruje
        // (jest tam z pewnoscia obecny, bo caly ten kurs jest budowany Mavenem).
        Path gsonJar = Paths.get(System.getProperty("user.home"),
                ".m2", "repository", "com", "google", "code", "gson", "gson", "2.11.0", "gson-2.11.0.jar");

        if (!Files.exists(gsonJar)) {
            System.out.println("\n(Nie znaleziono gson-2.11.0.jar w lokalnym ~/.m2 - pomijam live-build");
            System.out.println(" Ant, bo wymaga tej biblioteki. To NIE powinno sie zdarzyc w tym repo,");
            System.out.println(" bo pom.xml juz deklaruje gson - ale zabezpieczamy sie na wszelki wypadek.)");
            return;
        }

        String gsonJarPath = gsonJar.toString().replace('\\', '/');

        String buildXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project name="build-lab" default="dist" basedir=".">

                    <property name="src.dir" value="src/main/java"/>
                    <property name="resources.dir" value="src/main/resources"/>
                    <property name="test.dir" value="src/test/java"/>
                    <property name="build.dir" value="build"/>
                    <property name="classes.dir" value="${build.dir}/classes"/>
                    <property name="test.classes.dir" value="${build.dir}/test-classes"/>
                    <property name="dist.dir" value="${build.dir}/dist"/>
                    <property name="gson.jar" value="%s"/>

                    <target name="clean">
                        <delete dir="${build.dir}"/>
                    </target>

                    <target name="init">
                        <mkdir dir="${classes.dir}"/>
                        <mkdir dir="${test.classes.dir}"/>
                        <mkdir dir="${dist.dir}"/>
                    </target>

                    <target name="compile" depends="init">
                        <javac srcdir="${src.dir}" destdir="${classes.dir}" includeantruntime="false" encoding="UTF-8">
                            <classpath>
                                <pathelement location="${gson.jar}"/>
                            </classpath>
                        </javac>
                    </target>

                    <target name="copy-resources" depends="init">
                        <copy todir="${classes.dir}">
                            <fileset dir="${resources.dir}"/>
                        </copy>
                    </target>

                    <target name="compile-test" depends="compile">
                        <javac srcdir="${test.dir}" destdir="${test.classes.dir}" includeantruntime="false" encoding="UTF-8">
                            <classpath>
                                <pathelement location="${classes.dir}"/>
                                <pathelement location="${gson.jar}"/>
                            </classpath>
                        </javac>
                    </target>

                    <target name="test" depends="compile-test, copy-resources">
                        <java classname="com.example.buildlab.SelfCheckMain" fork="true" failonerror="true">
                            <classpath>
                                <pathelement location="${test.classes.dir}"/>
                                <pathelement location="${classes.dir}"/>
                                <pathelement location="${gson.jar}"/>
                            </classpath>
                        </java>
                    </target>

                    <target name="jar" depends="test">
                        <jar destfile="${dist.dir}/buildlab.jar" basedir="${classes.dir}">
                            <manifest>
                                <attribute name="Main-Class" value="com.example.buildlab.Main"/>
                                <attribute name="Class-Path" value="gson-2.11.0.jar"/>
                            </manifest>
                        </jar>
                    </target>

                    <target name="dist" depends="jar">
                        <copy file="${gson.jar}" todir="${dist.dir}"/>
                    </target>

                    <target name="run" depends="dist">
                        <java jar="${dist.dir}/buildlab.jar" fork="true"/>
                    </target>

                </project>
                """.formatted(gsonJarPath);

        Path buildFile = root.resolve("build.xml");
        Files.writeString(buildFile, buildXml);

        System.out.println("\n--- build.xml wygenerowany dla wersji Ant (targety: clean/init/compile/");
        System.out.println("    copy-resources/compile-test/test/jar/run/dist) ---");
        System.out.println(buildXml);

        Project project = new Project();
        project.setBaseDir(root.toFile());
        project.init();
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        project.addReference("ant.projectHelper", helper);
        helper.parse(project, buildFile.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        System.out.println("\n--- REALNE wykonanie embedowanego Anta: project.executeTarget(\"dist\") ---");
        System.out.println("(To wykonuje CALY lancuch: init -> compile -> copy-resources -> compile-test");
        System.out.println(" -> test (SelfCheckMain) -> jar -> dist - naprawde, silnikiem org.apache.tools.ant)");
        project.executeTarget("dist");

        // Ant zbudowal realny, dzialajacy build/dist/buildlab.jar (+ gson jar obok).
        // Teraz odpalamy go PRAWDZIWYM procesem javy przez ProcessBuilder - "java -jar".
        Path jarFile = root.resolve("build/dist/buildlab.jar");
        System.out.println("\n--- Odpalanie zbudowanego JAR-a PRAWDZIWYM procesem (ProcessBuilder) ---");
        System.out.println("JAR: " + jarFile);

        String javaExeName = System.getProperty("os.name", "").toLowerCase().contains("win") ? "java.exe" : "java";
        Path javaBin = Paths.get(System.getProperty("java.home"), "bin", javaExeName);

        ProcessBuilder pb = new ProcessBuilder(javaBin.toString(), "-jar", jarFile.toString());
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            System.out.println(">>> Output wyprodukowanego, REALNIE odpalonego JAR-a: >>>");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        int exitCode = process.waitFor();
        System.out.println(">>> Koniec outputu JAR-a. Kod wyjscia procesu: " + exitCode + " <<<");
    }

    // ============================================================
    // 🔹 WERSJA MAVEN - GENEROWANA JAKO TEKST (bez embedowania)
    // ============================================================

    private static void printMavenVersion() {
        System.out.println("\n\n########## WERSJA MAVEN (wygenerowany tekst, opis) ##########");

        String pomXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>com.example</groupId>
                    <artifactId>build-lab</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>

                    <properties>
                        <maven.compiler.source>21</maven.compiler.source>
                        <maven.compiler.target>21</maven.compiler.target>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>

                    <dependencies>
                        <dependency>
                            <groupId>com.google.code.gson</groupId>
                            <artifactId>gson</artifactId>
                            <version>2.11.0</version>
                        </dependency>
                        <dependency>
                            <groupId>org.junit.jupiter</groupId>
                            <artifactId>junit-jupiter</artifactId>
                            <version>5.10.2</version>
                            <scope>test</scope>
                        </dependency>
                    </dependencies>

                    <build>
                        <plugins>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-compiler-plugin</artifactId>
                            </plugin>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-surefire-plugin</artifactId>
                            </plugin>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-shade-plugin</artifactId>
                                <version>3.6.0</version>
                                <executions>
                                    <execution>
                                        <phase>package</phase>
                                        <goals><goal>shade</goal></goals>
                                        <configuration>
                                            <transformers>
                                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                                    <mainClass>com.example.buildlab.Main</mainClass>
                                                </transformer>
                                            </transformers>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                        </plugins>
                    </build>

                    <profiles>
                        <profile>
                            <id>dev</id>
                            <activation>
                                <activeByDefault>true</activeByDefault>
                            </activation>
                            <properties>
                                <app.env>dev</app.env>
                            </properties>
                        </profile>
                        <profile>
                            <id>prod</id>
                            <properties>
                                <app.env>prod</app.env>
                            </properties>
                        </profile>
                    </profiles>
                </project>
                """;

        System.out.println(pomXml);
        System.out.println("Uruchomienie: 'mvn package' (domyslnie profil 'dev') albo 'mvn package -Pprod'.");
        System.out.println("maven-shade-plugin (Lekcja 13) buduje fat JAR z Gson wewnatrz, podwiazany do fazy 'package'.");
        System.out.println("Ten sam SelfCheckMain z wersji Ant tutaj bylby PRAWDZIWYM testem JUnit 5,");
        System.out.println("odpalanym automatycznie przez maven-surefire-plugin w fazie 'test'.");
    }

    // ============================================================
    // 🔹 WERSJA GRADLE - GENEROWANA JAKO TEKST (bez embedowania)
    // ============================================================

    private static void printGradleVersion() {
        System.out.println("\n\n########## WERSJA GRADLE (wygenerowany tekst, opis) ##########");

        String settingsGradle = """
                rootProject.name = 'build-lab'

                dependencyResolutionManagement {
                    repositories {
                        mavenCentral()
                    }
                    // W realnym projekcie: gradle/libs.versions.toml z wpisem
                    // gson = { module = "com.google.code.gson:gson", version.ref = "gson" }
                    // (patrz Lekcja 18 - version catalog) - tu, dla zwiezlosci kapstonu,
                    // uzywamy go w build.gradle przez akcesor "libs.gson".
                }
                """;

        String buildGradle = """
                plugins {
                    id 'java'
                    id 'application'
                    id 'com.gradleup.shadow' version '8.3.5'   // fat JAR - patrz Lekcja 20
                }

                group = 'com.example'
                version = '1.0.0'

                dependencies {
                    implementation libs.gson                              // z version catalog (Lekcja 18)
                    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
                }

                application {
                    mainClass = 'com.example.buildlab.Main'
                }

                test {
                    useJUnitPlatform()   // patrz Lekcja 19 - tu odpalilby PRAWDZIWY JUnit 5 SelfCheckTest
                }

                shadowJar {
                    archiveClassifier.set('all')
                    manifest {
                        attributes 'Main-Class': 'com.example.buildlab.Main'
                    }
                }

                tasks.register('printInfo') {
                    doLast {
                        println "Projekt: ${project.name}, wersja: ${project.version}"
                        println "Main-Class: com.example.buildlab.Main"
                        println "Zbuduj fat JAR: ./gradlew shadowJar"
                    }
                }
                """;

        System.out.println("--- settings.gradle ---");
        System.out.println(settingsGradle);
        System.out.println("--- build.gradle ---");
        System.out.println(buildGradle);
        System.out.println("Uruchomienie: './gradlew build' (kompilacja+testy+jar), './gradlew shadowJar'");
        System.out.println("(fat JAR z Gson wewnatrz - Lekcja 20), './gradlew run' (odpalenie przez plugin");
        System.out.println("'application'), './gradlew printInfo' (custom task - Lekcja 17).");
        System.out.println("(To wykorzystuje wrapper ./gradlew, ktorego - jak wyjasniono w Lekcji 16 - ten");
        System.out.println(" konkretny kurs NIE ma; to czysto ILUSTRACYJNE polecenia dla TEGO kapstonu.)");
    }

    // ============================================================
    // 🔍 MAPOWANIE MIĘDZY 3 WERSJAMI - PODSUMOWANIE POROWNAWCZE
    // ============================================================

    private static void printCrossToolMapping() {
        System.out.println("\n\n########## MAPOWANIE MIEDZY 3 WERSJAMI TEGO SAMEGO PROJEKTU ##########");
        printRow("KONCEPCJA", "ANT", "MAVEN", "GRADLE");
        System.out.println("-".repeat(110));
        printRow("Zaleznosc Gson", "<pathelement location=jar>", "<dependency>gson</dependency>", "implementation libs.gson");
        printRow("Kompilacja", "target 'compile' (<javac>)", "faza 'compile' (wbudowana)", "task 'compileJava' (wbudowany)");
        printRow("Zasoby", "target 'copy-resources'", "faza 'process-resources'", "task 'processResources'");
        printRow("Testy", "target 'test' (<java>, wlasny)", "faza 'test' (surefire, JUnit5)", "task 'test' (useJUnitPlatform)");
        printRow("Pakowanie (fat jar)", "target 'jar'+'dist' (recznie)", "maven-shade-plugin (Lekcja 13)", "Shadow plugin (Lekcja 20)");
        printRow("Uruchomienie", "target 'run' / ProcessBuilder", "'java -jar target/*.jar'", "task 'run' (plugin application)");
        printRow("Wlasna logika", "cale build.xml (imperatywnie)", "maven-antrun-plugin (Lekcja 19)", "custom task 'printInfo' (Lekcja 17)");
        System.out.println();
        System.out.println("WNIOSEK KONCOWY: TEN SAM projekt, TA SAMA logika biznesowa (model/service/");
        System.out.println("repository), TRZY calkowicie rozne 'jezyki' opisu builda - dowod, ze wybor");
        System.out.println("Ant/Maven/Gradle to wybor FILOZOFII I ERGONOMII, a nie wybor MOZLIWOSCI.");
    }

    private static void printRow(String concept, String ant, String maven, String gradle) {
        System.out.printf("%-20s | %-28s | %-30s | %-30s%n", concept, ant, maven, gradle);
    }
}
