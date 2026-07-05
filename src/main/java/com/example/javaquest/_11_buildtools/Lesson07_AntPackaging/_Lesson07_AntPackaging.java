package com.example.javaquest._11_buildtools.Lesson07_AntPackaging;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class _Lesson07_AntPackaging {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 7: Ant i pakowanie JAR / WAR ===\n");

        /*
         * ============================================================
         * 📦 PO CO PAKOWAC?
         * ============================================================
         * Skompilowane klasy (.class) same w sobie sa niewygodne do
         * przenoszenia - to dziesiatki/setki malych plikow rozlozonych
         * w drzewie katalogow odpowiadajacym pakietom. JAR (Java ARchive)
         * pakuje je w JEDEN plik (to w rzeczywistosci zwykly plik ZIP z
         * dodatkowym folderem META-INF/MANIFEST.MF). WAR (Web ARchive) to
         * ten sam pomysl, ale dla aplikacji webowych, z wymagana strukturą
         * WEB-INF/classes (klasy) i WEB-INF/lib (biblioteki).
         *
         * Ant robi to przez taski <jar> i <war> - oba dzialaja na tej
         * samej zasadzie co <zip> (bo <jar>/<war> SA rozszerzeniami
         * <zip>), tylko doklejaja odpowiednia strukture/manifest.
         */

        executableJarDemo();
        fatJarDemo();
        warDemo();

        System.out.println("\n=== KONIEC LEKCJI 7 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - <jar> pakuje klasy+zasoby do jednego pliku; nested <manifest>
         *   z <attribute name="Main-Class" value="..."/> tworzy JAR
         *   WYKONYWALNY (java -jar app.jar).
         * - Ant NIE ma wbudowanego mechanizmu "fat jar" jak Maven Shade -
         *   trzeba go zbudowac recznie przez <zipfileset src="inny.jar">,
         *   ktory ROZPAKOWUJE zawartosc innego jara prosto do nowego
         *   archiwum (w przeciwienstwie do <fileset>, ktory dziala na
         *   plikach na dysku, <zipfileset src="..."> czyta zawartosc
         *   archiwum ZIP/JAR).
         * - Cienki ("thin") JAR bez dolaczonych zaleznosci konczy sie
         *   NoClassDefFoundError przy odpaleniu, jesli program uzywa klas
         *   z zewnetrznej biblioteki - fat JAR temu zapobiega.
         * - <war> pakuje WEB-INF/classes (atrybut/element <classes dir=...>)
         *   i WEB-INF/web.xml (atrybut webxml="...") - to WYSTARCZY, aby
         *   powstal poprawny, wdrazalny plik .war, bez potrzeby
         *   uruchamiania realnego kontenera (to robimy w rozdziale
         *   _07_servlets).
         */
    }

    /*
     * ============================================================
     * 🔹 1) EXECUTABLE JAR - <jar> + <manifest> + Main-Class
     * ============================================================
     * Manifest to plik META-INF/MANIFEST.MF wewnatrz JAR-a - metadane
     * o archiwum. Atrybut "Main-Class" mowi komendzie "java -jar" jaka
     * klase z metoda main() odpalic - bez niego "java -jar" nie wie, od
     * czego zaczac (dostalby blad "no main manifest attribute").
     */
    private static void executableJarDemo() throws Exception {
        System.out.println("--- 1) Executable JAR (Main-Class w manifescie) ---\n");

        Path baseDir = Files.createTempDirectory("lesson07-jar");

        Path mainSrcDir = baseDir.resolve("src-main/demo");
        Files.createDirectories(mainSrcDir);
        Files.writeString(mainSrcDir.resolve("Main.java"), """
                package demo;

                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello z wykonywalnego JAR-a! Main-Class dziala.");
                    }
                }
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="jar-demo" default="jar" basedir=".">
                    <property name="build.dir" value="build"/>
                    <property name="dist.dir" value="dist"/>

                    <target name="compile">
                        <mkdir dir="${build.dir}"/>
                        <javac srcdir="src-main" destdir="${build.dir}" includeantruntime="false"/>
                    </target>

                    <!-- KROK KLUCZOWY: nested <manifest><attribute .../></manifest>
                         wstrzykuje Main-Class do META-INF/MANIFEST.MF budowanego JAR-a. -->
                    <target name="jar" depends="compile">
                        <mkdir dir="${dist.dir}"/>
                        <jar destfile="${dist.dir}/app.jar" basedir="${build.dir}">
                            <manifest>
                                <attribute name="Main-Class" value="demo.Main"/>
                            </manifest>
                        </jar>
                    </target>
                </project>
                """);

        runAntTarget(baseDir, buildFile, "jar");

        Path jarFile = baseDir.resolve("dist/app.jar");
        System.out.println("\nJAR zbudowany: " + jarFile + " (istnieje: " + Files.exists(jarFile) + ")");

        // Dowod, ze to NAPRAWDE dziala - odpalamy powstaly JAR jako osobny
        // proces, dokladnie tak jak zrobilby to programista w terminalu.
        runJar(jarFile);
    }

    /*
     * ============================================================
     * 🔹 2) FAT JAR - reczny sposob na "jeden JAR ze wszystkim"
     * ============================================================
     * Maven ma wtyczke "shade"/"assembly", ktora automatycznie rozpakowuje
     * WSZYSTKIE zaleznosci do jednego JAR-a. Ant TAKIEGO gotowca nie ma -
     * ale <jar> potrafi to samo recznie przez nested <zipfileset src="...">:
     * kazdy taki element ROZPAKOWUJE zawartosc wskazanego archiwum (np.
     * innego JAR-a z biblioteka) prosto do wnetrza nowego archiwum, klasa
     * po klasie. Ponizej najpierw budujemy "thin" JAR (bez dependency) i
     * pokazujemy, ze faktycznie sie wywala (NoClassDefFoundError), a
     * potem budujemy "fat" JAR i pokazujemy, ze dziala samodzielnie.
     */
    private static void fatJarDemo() throws Exception {
        System.out.println("\n--- 2) Fat JAR (recznie dolaczona 'biblioteka' przez zipfileset) ---\n");

        Path baseDir = Files.createTempDirectory("lesson07-fatjar");

        Path libSrcDir = baseDir.resolve("src-lib/demo/util");
        Files.createDirectories(libSrcDir);
        Files.writeString(libSrcDir.resolve("Greeter.java"), """
                package demo.util;

                public class Greeter {
                    public static String greet(String name) {
                        return "Witaj, " + name + "! (pozdrowienia z osobnej biblioteki demo.util.Greeter)";
                    }
                }
                """);

        Path appSrcDir = baseDir.resolve("src-app/demo");
        Files.createDirectories(appSrcDir);
        Files.writeString(appSrcDir.resolve("MainWithDependency.java"), """
                package demo;

                import demo.util.Greeter;

                public class MainWithDependency {
                    public static void main(String[] args) {
                        System.out.println(Greeter.greet("Kursant"));
                    }
                }
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="fatjar-demo" default="fat-jar" basedir=".">
                    <property name="build.dir" value="build"/>
                    <property name="dist.dir" value="dist"/>

                    <target name="compile-lib">
                        <mkdir dir="${build.dir}/lib"/>
                        <javac srcdir="src-lib" destdir="${build.dir}/lib" includeantruntime="false"/>
                    </target>

                    <target name="lib-jar" depends="compile-lib">
                        <mkdir dir="${dist.dir}"/>
                        <jar destfile="${dist.dir}/libdep.jar" basedir="${build.dir}/lib"/>
                    </target>

                    <target name="compile-app" depends="compile-lib">
                        <mkdir dir="${build.dir}/app"/>
                        <javac srcdir="src-app" destdir="${build.dir}/app" includeantruntime="false">
                            <classpath>
                                <pathelement location="${build.dir}/lib"/>
                            </classpath>
                        </javac>
                    </target>

                    <!-- "Cienki" JAR - tylko wlasne klasy, bez biblioteki. -->
                    <target name="thin-jar" depends="compile-app">
                        <jar destfile="${dist.dir}/app-thin.jar" basedir="${build.dir}/app">
                            <manifest>
                                <attribute name="Main-Class" value="demo.MainWithDependency"/>
                            </manifest>
                        </jar>
                    </target>

                    <!-- "Tlusty" JAR - wlasne klasy (<fileset>, katalog na dysku)
                         PLUS zawartosc libdep.jar rozpakowana do wnetrza
                         (<zipfileset src="...">, zawartosc archiwum). -->
                    <target name="fat-jar" depends="compile-app,lib-jar">
                        <jar destfile="${dist.dir}/app-fat.jar">
                            <manifest>
                                <attribute name="Main-Class" value="demo.MainWithDependency"/>
                            </manifest>
                            <fileset dir="${build.dir}/app"/>
                            <zipfileset src="${dist.dir}/libdep.jar" excludes="META-INF/**"/>
                        </jar>
                    </target>
                </project>
                """);

        System.out.println(">>> Najpierw 'thin-jar' (bez dependency wewnatrz) - oczekujemy porazki przy odpaleniu:\n");
        runAntTarget(baseDir, buildFile, "thin-jar");
        runJar(baseDir.resolve("dist/app-thin.jar"));
        System.out.println("^^^ Powyzej powinien byc widoczny java.lang.NoClassDefFoundError: demo/util/Greeter\n");

        System.out.println(">>> Teraz 'fat-jar' (zipfileset wciaga zawartosc libdep.jar do jednego pliku):\n");
        runAntTarget(baseDir, buildFile, "fat-jar");
        runJar(baseDir.resolve("dist/app-fat.jar"));
        System.out.println("^^^ Ten sam kod aplikacji, ale teraz dziala samodzielnie - Greeter.class jest juz wewnatrz app-fat.jar.");
    }

    /*
     * ============================================================
     * 🔹 3) WAR - struktura WEB-INF/classes i WEB-INF/web.xml
     * ============================================================
     * WAR to JAR z ustalona struktura wymagana przez specyfikacje
     * serwletow: WEB-INF/web.xml (deskryptor aplikacji), WEB-INF/classes
     * (skompilowane klasy aplikacji) i WEB-INF/lib (biblioteki, ktorych
     * aplikacja potrzebuje, ale kontener NIE dostarcza - np. sterownik
     * bazy danych; biblioteki servlet-api same sa zwykle "provided" -
     * dostarcza je kontener, wiec NIE pakuje sie ich do WAR-a).
     * Ant task <war> (rozszerzenie <jar>) rozumie te strukture: atrybut
     * webxml="..." kopiuje wskazany plik jako WEB-INF/web.xml, a nested
     * <classes dir="..."> kopiuje caly katalog jako WEB-INF/classes/*.
     * NIE trzeba uruchamiac zadnego kontenera, aby udowodnic, ze plik
     * .war ma poprawna strukture - wystarczy go otworzyc jako ZIP.
     */
    private static void warDemo() throws Exception {
        System.out.println("\n--- 3) WAR (struktura WEB-INF/classes + WEB-INF/web.xml) ---\n");

        Path baseDir = Files.createTempDirectory("lesson07-war");

        Path webClassesSrc = baseDir.resolve("src-web/demo/web");
        Files.createDirectories(webClassesSrc);
        Files.writeString(webClassesSrc.resolve("HelloEndpoint.java"), """
                package demo.web;

                public class HelloEndpoint {
                    public String handle() {
                        return "Hello z WAR-a (klasa demo - realny kontener poznajemy w rozdziale _07_servlets)";
                    }
                }
                """);

        Path webXml = baseDir.resolve("web.xml");
        Files.writeString(webXml, """
                <?xml version="1.0" encoding="UTF-8"?>
                <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="5.0">
                    <display-name>Lesson07 WAR demo</display-name>
                </web-app>
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="war-demo" default="war" basedir=".">
                    <property name="build.dir" value="build"/>
                    <property name="dist.dir" value="dist"/>

                    <target name="compile">
                        <mkdir dir="${build.dir}/classes"/>
                        <javac srcdir="src-web" destdir="${build.dir}/classes" includeantruntime="false"/>
                    </target>

                    <target name="war" depends="compile">
                        <mkdir dir="${dist.dir}"/>
                        <war destfile="${dist.dir}/app.war" webxml="web.xml">
                            <classes dir="${build.dir}/classes"/>
                        </war>
                    </target>
                </project>
                """);

        runAntTarget(baseDir, buildFile, "war");

        Path warFile = baseDir.resolve("dist/app.war");
        System.out.println("\nWAR zbudowany: " + warFile + " (istnieje: " + Files.exists(warFile) + ")\n");

        System.out.println("Zawartosc app.war (dowod, ze <war> naprawde zbudowal poprawna strukture):");
        try (ZipFile zip = new ZipFile(warFile.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                System.out.println("  " + entry.getName());
            }
        }
    }

    // ---- Wspolne pomocnicze metody dla tej lekcji ----

    /**
     * Kanoniczny wzorzec embedded Anta (Project + ProjectHelper +
     * DefaultLogger), omowiony szczegolowo w Lekcji 3 - tu bez powtarzania
     * komentarza "dlaczego", tylko realne wykonanie targetu.
     */
    private static void runAntTarget(Path baseDir, Path buildFile, String target) {
        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());
        project.executeTarget(target);
    }

    /**
     * Odpala podany JAR jako osobny proces "java -jar ...", tak jak
     * zrobilby to programista w terminalu, i wypisuje jego output.
     */
    private static void runJar(Path jarFile) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile.toString());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output = new String(process.getInputStream().readAllBytes());
        boolean finished = process.waitFor(10, TimeUnit.SECONDS);

        System.out.println("Wyjscie z 'java -jar " + jarFile.getFileName() + "':");
        System.out.print(output);
        System.out.println("(kod wyjscia: " + (finished ? process.exitValue() : "TIMEOUT") + ")\n");
    }
}
