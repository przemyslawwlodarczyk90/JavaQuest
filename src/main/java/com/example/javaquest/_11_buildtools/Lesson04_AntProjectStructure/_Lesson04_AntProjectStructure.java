package com.example.javaquest._11_buildtools.Lesson04_AntProjectStructure;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson04_AntProjectStructure {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 4: Typowa struktura projektu Ant ===\n");

        /*
         * ============================================================
         * 📦 TYPOWA STRUKTURA PROJEKTU ANT
         * ============================================================
         * Ant (w przeciwienstwie do Mavena) NIE wymusza zadnej struktury
         * katalogow - to TY decydujesz, jak nazwiesz i rozlozysz katalogi.
         * W praktyce jednak ustalila sie pewna KONWENCJA (nie wymog!),
         * powtarzana w wiekszosci projektow enterprise:
         *
         *   moj-projekt/
         *   +-- build.xml            (przepis budowania)
         *   +-- build.properties     (opcjonalne, zewnetrzne wlasciwosci)
         *   +-- src/                 (kod zrodlowy produkcyjny, .java)
         *   +-- test/                (kod zrodlowy testow, .java)
         *   +-- resources/           (zasoby: .properties, config, itp.)
         *   +-- lib/                 (biblioteki zewnetrzne, pliki .jar)
         *   +-- build/               (skompilowane klasy - .class)
         *   |   +-- classes/         (klasy produkcyjne)
         *   |   +-- test-classes/    (klasy testowe)
         *   +-- dist/                (gotowe paczki wynikowe - JAR/WAR)
         *
         * Kluczowe ROZDZIELENIE odpowiedzialnosci miedzy katalogami:
         *  - src vs build/classes - KOD ZRODLOWY nigdy nie miesza sie z
         *    WYNIKAMI KOMPILACJI. Dzieki temu "clean" moze bezpiecznie
         *    usunac caly build/ i dist/ bez ryzyka utraty zrodel.
         *  - lib - biblioteki SA (jak w tej lekcji), ale NIE MUSZA byc
         *    czescia repozytorium kodu (czesto .gitignore) - to zaleznosci
         *    zewnetrzne, nie "nasz" kod.
         *  - resources - pliki, ktore NIE SA kodem Java, ale MUSZA
         *    wyladowac RAZEM z klasami w build/classes, aby aplikacja
         *    mogla je znalezc w runtime (np. przez getResourceAsStream).
         *  - dist - to co faktycznie WYSYLAMY dalej (na serwer, do
         *    repozytorium artefaktow) - finalny JAR/WAR, nic wiecej.
         */

        // KROK 1: budujemy REALNA strukture katalogow na dysku, dokladnie
        // jak wyzej - to nie jest symulacja, to prawdziwe katalogi.
        Path projectDir = Files.createTempDirectory("lesson04ant-structure");
        Path srcDir = projectDir.resolve("src");
        Path resourcesDir = projectDir.resolve("resources");
        Path libDir = projectDir.resolve("lib");
        Path buildDir = projectDir.resolve("build");
        Path distDir = projectDir.resolve("dist");

        Files.createDirectories(srcDir);
        Files.createDirectories(resourcesDir);
        Files.createDirectories(libDir);
        // build/ i dist/ NIE tworzymy recznie - to zrobi za nas target
        // "init" w build.xml, tak jak w prawdziwym projekcie.

        System.out.println("Utworzona struktura projektu w: " + projectDir);
        System.out.println("  src/       -> " + Files.exists(srcDir));
        System.out.println("  resources/ -> " + Files.exists(resourcesDir));
        System.out.println("  lib/       -> " + Files.exists(libDir));

        // KROK 2: kod zrodlowy - klasa AppConfig, ktora w runtime wczyta
        // plik config.properties z classpath (a wiec z build/classes, GDZIE
        // TRAFI po skopiowaniu przez target "copy-resources").
        Files.writeString(srcDir.resolve("AppConfig.java"), """
                import java.io.IOException;
                import java.io.InputStream;
                import java.util.Properties;

                public class AppConfig {
                    public static void main(String[] args) throws IOException {
                        Properties props = new Properties();
                        try (InputStream in = AppConfig.class.getResourceAsStream("/config.properties")) {
                            if (in == null) {
                                System.out.println("BLAD: config.properties nie znaleziono na classpath!");
                                return;
                            }
                            props.load(in);
                        }
                        System.out.println("app.name = " + props.getProperty("app.name"));
                        System.out.println("app.env = " + props.getProperty("app.env"));
                    }
                }
                """);

        // KROK 3: zasob konfiguracyjny - typowy plik .properties, ktory w
        // realnym projekcie NIE jest kodem Java, ale musi trafic razem z
        // klasami do build/classes, aby getResourceAsStream go znalazl.
        Files.writeString(resourcesDir.resolve("config.properties"), """
                app.name=JavaQuest Demo
                app.env=lokalne-demo-lekcji-4
                """);

        System.out.println("\nWygenerowano src/AppConfig.java oraz resources/config.properties.\n");

        /*
         * ============================================================
         * 🔹 build.xml - clean -> init -> compile -> copy-resources -> jar -> run
         * ============================================================
         * Kluczowy nowy element wobec Lekcji 3: target "copy-resources"
         * uzywa <copy todir="..."><fileset dir="..."/></copy>, aby
         * skopiowac WSZYSTKIE pliki z resources/ do build/classes - DOKLADNIE
         * tam, gdzie klasy .java zostaly wczesniej skompilowane. Dzieki temu
         * jeden katalog (build/classes) staje sie kompletnym "obrazem"
         * tego, co ostatecznie trafi do JAR-a: klasy + zasoby razem.
         */

        Path buildFile = projectDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="lesson04-structure-demo" default="jar" basedir=".">

                    <property name="src.dir" value="src"/>
                    <property name="resources.dir" value="resources"/>
                    <property name="build.dir" value="build"/>
                    <property name="classes.dir" value="build/classes"/>
                    <property name="dist.dir" value="dist"/>
                    <property name="main.class" value="AppConfig"/>
                    <property name="jar.name" value="lesson04-app.jar"/>

                    <target name="clean" description="Usuwa build i dist - odtwarzanie od zera">
                        <delete dir="${build.dir}"/>
                        <delete dir="${dist.dir}"/>
                    </target>

                    <target name="init" depends="clean" description="Tworzy katalogi wyjsciowe">
                        <mkdir dir="${classes.dir}"/>
                        <mkdir dir="${dist.dir}"/>
                    </target>

                    <target name="compile" depends="init" description="Kompiluje src/ do build/classes">
                        <javac srcdir="${src.dir}" destdir="${classes.dir}" includeantruntime="false"/>
                    </target>

                    <target name="copy-resources" depends="compile" description="Kopiuje zasoby do build/classes">
                        <copy todir="${classes.dir}">
                            <fileset dir="${resources.dir}"/>
                        </copy>
                    </target>

                    <target name="jar" depends="copy-resources" description="Pakuje klasy + zasoby do JAR-a">
                        <jar destfile="${dist.dir}/${jar.name}" basedir="${classes.dir}">
                            <manifest>
                                <attribute name="Main-Class" value="${main.class}"/>
                            </manifest>
                        </jar>
                    </target>

                    <target name="run" depends="jar" description="Uruchamia zbudowany JAR">
                        <java jar="${dist.dir}/${jar.name}" fork="true"/>
                    </target>

                </project>
                """);

        Project project = new Project();
        project.init();
        project.setBaseDir(projectDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());

        System.out.println(">>> ant run  (clean -> init -> compile -> copy-resources -> jar -> run)\n");
        project.executeTarget("run");

        /*
         * ============================================================
         * 🔍 WERYFIKACJA: CZY ZASOBY NAPRAWDE TRAFILY DO classes/?
         * ============================================================
         * Nie wierzymy na slowo logowi Anta - sprawdzamy NAPRAWDE na
         * dysku, czy plik config.properties znajduje sie w tym samym
         * katalogu co skompilowana klasa AppConfig.class.
         */
        Path classesDir = projectDir.resolve("build").resolve("classes");
        Path copiedConfig = classesDir.resolve("config.properties");
        Path compiledClass = classesDir.resolve("AppConfig.class");

        System.out.println("\n--- Weryfikacja na dysku ---");
        System.out.println("AppConfig.class istnieje w build/classes?      " + Files.exists(compiledClass));
        System.out.println("config.properties istnieje w build/classes?    " + Files.exists(copiedConfig));

        Path builtJar = projectDir.resolve("dist").resolve("lesson04-app.jar");
        System.out.println("Finalny JAR istnieje w dist/?                   " + Files.exists(builtJar));

        /*
         * ============================================================
         * 🔹 CLEAN BUILD - ODTWARZANIE OD ZERA
         * ============================================================
         * "Clean build" to uruchomienie targetu "clean" PRZED reszta
         * builda - gwarantuje, ze NIC ze starego builda (stare, mozliwe
         * ze juz nieaktualne pliki .class, stare JAR-y) nie zostanie
         * przypadkowo uzyte. To wazne np. gdy usunelismy jakas klase z
         * kodu zrodlowego - bez clean, jej STARY plik .class moglby
         * zostac w build/classes i "udawac", ze wciaz istnieje.
         *
         * Ponizej NAPRAWDE wywolujemy sam "clean" i sprawdzamy, ze
         * katalogi build/ i dist/ (razem z calym ich zawartosciam)
         * faktycznie znikaja z dysku.
         */
        System.out.println("\n--- Demo: clean build ---");
        System.out.println("Przed clean: build/ istnieje = " + Files.exists(buildDir)
                + ", dist/ istnieje = " + Files.exists(distDir));

        project.executeTarget("clean");

        System.out.println("Po clean:    build/ istnieje = " + Files.exists(buildDir)
                + ", dist/ istnieje = " + Files.exists(distDir));

        System.out.println("\n=== KONIEC LEKCJI 4 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ant nie wymusza struktury katalogow, ale konwencja src/,
         *   test/, resources/, lib/, build/, dist/ jest powszechna w
         *   projektach enterprise.
         * - Kluczowe rozdzielenie: kod zrodlowy (src) nigdy nie miesza
         *   sie z wynikami kompilacji (build) - to gwarantuje
         *   bezpieczny "clean".
         * - Zasoby (resources/) trzeba WYRAZNIE skopiowac do tego samego
         *   katalogu co skompilowane klasy (<copy><fileset .../></copy>),
         *   inaczej getResourceAsStream ich nie znajdzie w runtime.
         * - Powyzej NAPRAWDE zweryfikowalismy na dysku (Files.exists),
         *   ze config.properties trafil do build/classes razem z
         *   AppConfig.class, oraz ze "clean" naprawde usunal build/ i
         *   dist/ w calosci.
         * - Clean build = target "clean" wykonany PRZED reszta - gwarancja,
         *   ze build startuje od zera, bez ryzyka "duchow" ze starych
         *   kompilacji.
         */
    }
}
