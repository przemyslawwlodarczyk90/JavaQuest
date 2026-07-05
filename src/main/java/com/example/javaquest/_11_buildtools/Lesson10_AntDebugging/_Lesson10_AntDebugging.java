package com.example.javaquest._11_buildtools.Lesson10_AntDebugging;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson10_AntDebugging {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 10: Debugowanie budow Anta ===\n");

        /*
         * ============================================================
         * 📦 URUCHAMIANIE ANTA Z TERMINALA (przypomnienie/kontekst)
         * ============================================================
         * W realnym projekcie (poza tym kursem, gdzie embedujemy Anta w
         * JVM) Anta odpala sie z terminala komenda "ant":
         *
         *   ant                      - odpala target domyslny (default=...)
         *   ant compile               - odpala konkretny target "compile"
         *   ant clean compile jar     - odpala kilka targetow po kolei
         *   ant -f custom-build.xml   - uzywa INNEGO pliku niz build.xml
         *
         * Ponizej (w tej lekcji) NIE wywolujemy zewnetrznego programu
         * "ant" (nie jest zainstalowany w tym srodowisku) - caly czas
         * uzywamy embedded Anta (Project/ProjectHelper), tak jak w calym
         * tym rozdziale. To, co widzimy na ekranie (linie z DefaultLoggera),
         * to DOKLADNIE ten sam mechanizm logowania, ktory uzywa prawdziwy
         * "ant" z linii komend - roznica jest tylko w tym, KTO go odpala
         * (JVM naszego demo, a nie zewnetrzny proces).
         */

        Path baseDir = Files.createTempDirectory("lesson10-debug");
        Path buildFile = writeBuildFile(baseDir);

        verbosityComparisonDemo(baseDir, buildFile);
        echoDebuggingDemo(baseDir, buildFile);
        deliberateFailuresDemo(baseDir, buildFile);
        cliFlagsAside();

        System.out.println("=== KONIEC LEKCJI 10 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Poziom logowania (DefaultLogger.setMessageOutputLevel) steruje
         *   iloscia szczegolow: MSG_INFO (domyslny "ant"), MSG_VERBOSE
         *   (odpowiednik "ant -v" - wiecej informacji o krokach wewnetrznych),
         *   MSG_DEBUG (odpowiednik "ant -debug" - NAJWIECEJ szczegolow,
         *   przydatne tylko gdy naprawde trzeba, bo output jest ogromny).
         * - <echo> to najprostsza, uniwersalna technika debugowania -
         *   wypisanie wartosci property (${basedir}, wlasny classpath,
         *   wersja Javy) w dowolnym miejscu builda, bez zadnych narzedzi.
         * - Ant domyslnie NIE rzuca bledu za samo uzycie niezdefiniowanej
         *   property - wstawia jej nazwe jako literalny tekst "${...}".
         *   To CZESTY, cichy blad - dopiero KOLEJNY task (np. javac
         *   dostajacy literalny "${...}" jako sciezke) faktycznie
         *   zawodzi z realnym BuildException.
         * - Zla sciezka (srcdir), zly target w depends= i nieistniejaca
         *   property to trzy klasyczne, realne bledy zademonstrowane w
         *   tej lekcji - kazdy z prawdziwym, zlapanym komunikatem
         *   BuildException.
         * - "ant -v"/"ant -debug"/"ant -diagnostics" (nie do odpalenia w
         *   tym embedded demo) dodaja odpowiednio: wiecej zdarzen
         *   wewnetrznych, jeszcze wiecej (wlacznie z classloaderami), oraz
         *   caly, niezalezny od konkretnego builda raport diagnostyczny
         *   o samym srodowisku (JVM, OS, zmienne, classpath).
         */
    }

    private static Path writeBuildFile(Path baseDir) throws Exception {
        Path srcDir = baseDir.resolve("src/demo");
        Files.createDirectories(srcDir);
        Files.writeString(srcDir.resolve("Sample.java"), """
                package demo;

                public class Sample {
                    public static int answer() {
                        return 42;
                    }
                }
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="debug-demo" default="build-something" basedir=".">
                    <property name="src.dir" value="src"/>
                    <property name="build.dir" value="build"/>
                    <property name="classpath.demo" value="${build.dir}:${src.dir}"/>

                    <target name="build-something">
                        <mkdir dir="${build.dir}"/>
                        <javac srcdir="${src.dir}" destdir="${build.dir}" includeantruntime="false"/>
                        <echo message="Build zakonczony - klasy w ${build.dir}"/>
                    </target>

                    <target name="show-debug-info">
                        <echo message="basedir = ${basedir}"/>
                        <echo message="src.dir = ${src.dir}"/>
                        <echo message="build.dir = ${build.dir}"/>
                        <echo message="classpath.demo = ${classpath.demo}"/>
                        <echo message="java.version (auto z System properties) = ${java.version}"/>
                    </target>

                    <!-- BLAD 1: uzycie NIEISTNIEJACEJ property jako sciezki dla javac.
                         Ant nie rzuca bledu za samo uzycie ${missing.property} - dopiero
                         javac, dostajac literalny tekst "${missing.property}" jako
                         katalog, zglasza realny problem. -->
                    <target name="fail-missing-property">
                        <javac srcdir="${missing.property}" destdir="${build.dir}" includeantruntime="false"/>
                    </target>

                    <!-- BLAD 2: sciezka, ktora naprawde nie istnieje na dysku. -->
                    <target name="fail-bad-srcdir">
                        <javac srcdir="totally-missing-source-dir" destdir="${build.dir}" includeantruntime="false"/>
                    </target>
                </project>
                """);

        return buildFile;
    }

    /**
     * BLAD 3 (depends na nieistniejacym targecie) musi zyc w OSOBNYM
     * build.xml. Powod: Ant sortuje topologicznie WSZYSTKIE targety w
     * projekcie (nie tylko te potrzebne do wywolanego targetu) przy
     * KAZDYM project.executeTarget(...) - jeden zepsuty depends= w
     * pliku wywalalby WSZYSTKIE inne targety z tego samego Projectu,
     * nawet zupelnie z nim niezwiazane. To samo w sobie jest cenna
     * lekcja o debugowaniu: jeden zepsuty target moze "zatruc" caly plik.
     */
    private static Path writeBrokenDependsBuildFile(Path baseDir) throws Exception {
        Path buildFile = baseDir.resolve("build-broken-depends.xml");
        Files.writeString(buildFile, """
                <project name="broken-depends-demo" default="broken-depends" basedir=".">
                    <!-- BLAD 3: depends na targecie, ktory nie istnieje w projekcie
                         (literowka w nazwie albo target zostal usuniety/przeniesiony). -->
                    <target name="broken-depends" depends="ghost-target-that-does-not-exist">
                        <echo message="Ten echo nigdy sie nie wykona."/>
                    </target>
                </project>
                """);
        return buildFile;
    }

    /*
     * ============================================================
     * 🔹 1) TEN SAM BUILD, TRZY POZIOMY SZCZEGOLOWOSCI
     * ============================================================
     */
    private static void verbosityComparisonDemo(Path baseDir, Path buildFile) {
        System.out.println("--- 1) Ten sam build, 3 poziomy szczegolowosci logow ---\n");
        runAtLevel(baseDir, buildFile, "build-something", Project.MSG_INFO, "MSG_INFO    (odpowiednik zwyklego 'ant')");
        runAtLevel(baseDir, buildFile, "build-something", Project.MSG_VERBOSE, "MSG_VERBOSE (odpowiednik 'ant -v')");
        runAtLevel(baseDir, buildFile, "build-something", Project.MSG_DEBUG, "MSG_DEBUG   (odpowiednik 'ant -debug')");
        System.out.println("(Zwroc uwage, jak przy 2. i 3. przebiegu przybywa linii - to samo javac,");
        System.out.println(" ale logger opowiada o kolejnych krokach wewnetrznych coraz szczegolowiej;");
        System.out.println(" na 2./3. przebiegu javac moze nawet nic nie kompilowac - klasy sa juz aktualne,");
        System.out.println(" co samo w sobie jest widoczne tylko na wyzszym poziomie logowania.)\n");
    }

    private static void runAtLevel(Path baseDir, Path buildFile, String target, int level, String label) {
        System.out.println(">>> " + label + " <<<");

        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(level);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());
        project.executeTarget(target);

        System.out.println();
    }

    /*
     * ============================================================
     * 🔹 2) ECHO JAKO TECHNIKA DEBUGOWANIA
     * ============================================================
     * Zanim zaczniesz szukac zlozonych przyczyn ("dlaczego build nie
     * widzi tego pliku?", "jaka wartosc ma ta property w tym miejscu?"),
     * najprostsza i najszybsza technika to WSTAWIC <echo> wypisujace
     * podejrzane property w danym momencie builda.
     */
    private static void echoDebuggingDemo(Path baseDir, Path buildFile) {
        System.out.println("--- 2) echo jako technika debugowania ---\n");

        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());
        project.executeTarget("show-debug-info");

        System.out.println();
    }

    /*
     * ============================================================
     * 🔍 3) TRZY REALNE BLEDY BUDOWANIA I JAK JE CZYTAC
     * ============================================================
     */
    private static void deliberateFailuresDemo(Path baseDir, Path buildFile) throws Exception {
        System.out.println("--- 3) 3 realne bledy budowania (BuildException) i jak je czytac ---\n");

        attemptAndReport(baseDir, buildFile, "fail-missing-property",
                "Blad 1: uzycie NIEISTNIEJACEJ property ${missing.property}.\n" +
                        "Wskazowka: jesli w komunikacie/blednej sciezce widzisz LITERALNY tekst\n" +
                        "\"${...}\", to znak, ze ta property nigdy nie zostala ustawiona.");

        attemptAndReport(baseDir, buildFile, "fail-bad-srcdir",
                "Blad 2: <javac srcdir=\"...\"> wskazuje na katalog, ktory nie istnieje na dysku.");

        Path brokenDependsFile = writeBrokenDependsBuildFile(baseDir);
        attemptAndReport(baseDir, brokenDependsFile, "broken-depends",
                "Blad 3: target zalezy (depends=) od targetu, ktory nie istnieje w build.xml\n" +
                        "(literowka w nazwie albo target zostal usuniety/przeniesiony do innego pliku).");
    }

    private static void attemptAndReport(Path baseDir, Path buildFile, String target, String explanation) {
        System.out.println(explanation);

        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());

        try {
            project.executeTarget(target);
            System.out.println("(Nieoczekiwanie sie udalo - w tej lekcji mial to byc blad.)");
        } catch (BuildException e) {
            System.out.println("ZLAPANO BuildException: " + e.getMessage());
        }
        System.out.println();
    }

    /*
     * ============================================================
     * 🔍 4) ant -v / ant -debug / ant -diagnostics (PROZA, NIE DO ODPALENIA)
     * ============================================================
     * Ponizej TYLKO opis (nie da sie tego uruchomic bez prawdziwego
     * pliku wykonywalnego "ant" zainstalowanego w systemie):
     *
     * - "ant -v" (verbose) - jak nasz przebieg z MSG_VERBOSE: dodaje info
     *   o wersji Anta i JVM na starcie, o tym, ktore build.xml/importy
     *   zostaly wczytane, dodatkowe komunikaty o "uptodate" (dlaczego
     *   dany task zostal/nie zostal wykonany ponownie).
     * - "ant -debug" - jak nasz przebieg z MSG_DEBUG, ale jeszcze bardziej
     *   szczegolowy niz to, co widzielismy: dorzuca dodatkowo informacje
     *   o classloaderach uzywanych do wczytywania taskow, pelne sciezki
     *   przeszukiwane przy szukaniu klas/zasobow. Uzywane tylko gdy
     *   naprawde trzeba - output moze byc OGROMNY.
     * - "ant -diagnostics" - to COS INNEGO niz poprzednie dwa: nie
     *   wykonuje ZADNEGO targetu, tylko wypisuje STATYCZNY raport o samym
     *   SRODOWISKU: wersja JVM, system operacyjny, zmienne srodowiskowe,
     *   pelny classpath Anta, liste zainstalowanych "optional tasks"
     *   (czy dany task w ogole jest dostepny w tej instalacji Anta).
     *   To pierwsze, co warto sprawdzic przy problemie typu "u mnie
     *   dziala, u kolegi nie" - zanim zaczniesz debugowac SAM build.xml.
     */
    private static void cliFlagsAside() {
        System.out.println("--- 4) ant -v / ant -debug / ant -diagnostics (opis, bez uruchamiania) ---\n");
        System.out.println("ant -v            : jak nasz przebieg MSG_VERBOSE (wersje, importy, powody 'uptodate').");
        System.out.println("ant -debug        : jak nasz przebieg MSG_DEBUG + informacje o classloaderach.");
        System.out.println("ant -diagnostics  : STATYCZNY raport o srodowisku (JVM/OS/env/classpath/optional tasks),");
        System.out.println("                    nie wykonuje zadnego targetu - to nie to samo co poziom logowania.");
        System.out.println();
    }
}
