package com.example.javaquest._11_buildtools.Lesson05_AntClasspath;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class _Lesson05_AntClasspath {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 5: Classpath w Ancie ===\n");

        /*
         * ============================================================
         * 📦 CLASSPATH W ANCIE - <path>, <pathelement>, <fileset>
         * ============================================================
         * To JEDEN Z NAJWAZNIEJSZYCH tematow calego rozdzialu - w pracy
         * z legacy Antem wiekszosc "dziwnych" bledow (ClassNotFoundException,
         * NoSuchMethodError, NoClassDefFoundError) to w praktyce zle
         * skonfigurowany classpath.
         *
         * Ant definiuje classpath jako WLASNY, nazwany element <path>:
         *
         *   <path id="project.classpath">
         *       <fileset dir="lib" includes="*.jar"/>
         *   </path>
         *
         *  - id="project.classpath" - nadaje temu zestawowi sciezek NAZWE,
         *    do ktorej mozemy sie odwolac gdzie indziej w build.xml.
         *  - <fileset dir="lib" includes="*.jar"/> - "wszystkie pliki
         *    .jar w katalogu lib" - NIE trzeba wymieniac ich po nazwie
         *    recznie, co jest kluczowe, gdy liczba bibliotek rosnie.
         *  - <pathelement location="..."/> - alternatywa dla pojedynczego,
         *    konkretnego wpisu (plik albo katalog), gdy fileset jest
         *    "za duzym mlotkiem" na jeden element.
         *
         * Aby UZYC zdefiniowanego <path> w innym miejscu, uzywamy
         * atrybutu refid="project.classpath" (odwolanie po id) - np.
         * w <javac classpathref="project.classpath"> albo
         * <java classpathref="project.classpath">.
         */

        Path projectDir = Files.createTempDirectory("lesson05ant-classpath");
        Path srcDir = projectDir.resolve("src");
        Path libDir = projectDir.resolve("lib");
        Files.createDirectories(srcDir);
        Files.createDirectories(libDir);

        /*
         * ============================================================
         * 🔹 KROK A: BUDUJEMY WLASNA, PRAWDZIWA "BIBLIOTEKE ZEWNETRZNA"
         * ============================================================
         * Zamiast dodawac nowa zaleznosc Maven (np. Gson) do calego
         * projektu tylko na potrzeby jednej lekcji, budujemy WLASNY,
         * maly, prawdziwy JAR w locie - dokladnie tak, jak nauczylismy
         * sie w Lekcji 2 (javax.tools.JavaCompiler + JarOutputStream).
         * Z punktu widzenia Anta i tak bedzie to zupelnie zwyczajny,
         * REALNY plik .jar lezacy w katalogu lib/.
         */
        Path libSrc = Files.createTempDirectory("lesson05-libsrc");
        Path libClasses = libSrc.resolve("classes");
        Files.createDirectories(libClasses);

        Files.writeString(libSrc.resolve("StringUtils.java"), """
                public class StringUtils {
                    public static String shout(String text) {
                        return text.toUpperCase() + "!!!";
                    }
                }
                """);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, System.err,
                "-d", libClasses.toString(), libSrc.resolve("StringUtils.java").toString());

        Manifest libManifest = new Manifest();
        libManifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        Path libJar = libDir.resolve("string-utils-1.0.jar");
        try (JarOutputStream jarOut = new JarOutputStream(Files.newOutputStream(libJar), libManifest)) {
            jarOut.putNextEntry(new JarEntry("StringUtils.class"));
            jarOut.write(Files.readAllBytes(libClasses.resolve("StringUtils.class")));
            jarOut.closeEntry();
        }
        System.out.println("Zbudowano wlasna 'biblioteke zewnetrzna': " + libJar
                + " (" + Files.size(libJar) + " B)\n");

        // Kod aplikacji, ktory KORZYSTA z klasy StringUtils z powyzszego JAR-a -
        // dokladnie tak, jak korzystalby z prawdziwej biblioteki firmowej.
        Files.writeString(srcDir.resolve("App.java"), """
                public class App {
                    public static void main(String[] args) {
                        System.out.println(StringUtils.shout("dziala z biblioteki w lib"));
                    }
                }
                """);

        /*
         * ============================================================
         * 🔍 KROK B: build.xml z <path id="project.classpath">
         * ============================================================
         */
        Path buildFile = projectDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="lesson05-classpath-demo" default="run" basedir=".">

                    <property name="src.dir" value="src"/>
                    <property name="lib.dir" value="lib"/>
                    <property name="build.dir" value="build"/>

                    <path id="project.classpath">
                        <fileset dir="${lib.dir}" includes="*.jar"/>
                    </path>

                    <target name="init">
                        <mkdir dir="${build.dir}"/>
                    </target>

                    <target name="compile" depends="init">
                        <!-- classpathref="project.classpath" - kompilator MUSI widziec StringUtils
                             na classpath, inaczej App.java sie nie skompiluje ("cannot find symbol"). -->
                        <javac srcdir="${src.dir}" destdir="${build.dir}"
                               classpathref="project.classpath" includeantruntime="false"/>
                    </target>

                    <target name="run" depends="compile">
                        <!-- classpathref dziala identycznie przy <java> - klasy z lib/*.jar
                             musza byc widoczne rowniez PRZY URUCHAMIANIU, nie tylko przy kompilacji. -->
                        <java classname="App" fork="true">
                            <classpath>
                                <path refid="project.classpath"/>
                                <pathelement location="${build.dir}"/>
                            </classpath>
                        </java>
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

        System.out.println("--- DEMO A: dzialajacy classpath (JAR jest w lib/) ---\n");
        project.executeTarget("run");

        /*
         * ============================================================
         * 🔍 KROK C: TYPOWE PROBLEMY - CO SIE DZIEJE, GDY BRAKUJE JAR-A?
         * ============================================================
         * Najczestszy realny scenariusz w pracy: kolega z zespolu dodal
         * nowa biblioteke do kodu, ale ZAPOMNIAL dodac jej JAR-a do lib/
         * (albo .gitignore przypadkiem wyrzucil caly katalog lib/).
         * Efekt? "cannot find symbol" przy kompilacji.
         *
         * Zamiast tylko OPOWIEDZIEC o tym problemie, USUWAMY teraz
         * PRAWDZIWY plik JAR z lib/ i wywolujemy TEN SAM build.xml (nowy
         * Project, tak jak nowy przebieg "ant run" w terminalu) - blad,
         * ktory zobaczymy, jest calkowicie realny, nie zasymulowany.
         */
        System.out.println("\n--- DEMO B: ten sam build.xml, ale BEZ JAR-a w lib/ ---\n");

        Files.delete(libJar);
        System.out.println("Usunieto " + libJar.getFileName() + " z lib/ (symulacja 'zapomnianej biblioteki').\n");

        // Musimy wykonac clean rekompilacje - inaczej stary build/App.class
        // z poprzedniego, udanego builda "udawalby" ze wszystko dziala.
        Path buildOutDir = projectDir.resolve("build");
        deleteRecursively(buildOutDir);

        Project brokenProject = new Project();
        brokenProject.init();
        brokenProject.setBaseDir(projectDir.toFile());
        brokenProject.addBuildListener(logger);
        ProjectHelper.configureProject(brokenProject, buildFile.toFile());

        try {
            brokenProject.executeTarget("run");
            System.out.println("(Nieoczekiwanie build sie powiodl - sprawdz srodowisko.)");
        } catch (org.apache.tools.ant.BuildException e) {
            System.out.println("\nZlapano PRAWDZIWY BuildException z Anta: " + e.getMessage());
            System.out.println("""

                    📌 Co widzi student w tej sytuacji:
                    - Kompilacja <javac> pada z komunikatem w stylu
                      "cannot find symbol: class StringUtils" - kompilator NIE
                      widzi klasy, bo jej JAR zniknal z fileset "lib/*.jar".
                    - Gdyby JAR byl obecny przy KOMPILACJI, a zniknal tylko przy
                      URUCHOMIENIU (<java>), zamiast tego zobaczylibysmy w runtime
                      NoClassDefFoundError (klasa byla znana kompilatorowi, ale
                      zniknela z classpath w momencie startu JVM).
                    - Naprawa jest zawsze taka sama: upewnic sie, ze WLASCIWY plik
                      .jar (wlasciwa wersja!) fizycznie znajduje sie w katalogu,
                      na ktory wskazuje <fileset dir="lib">.""");
        }

        /*
         * ============================================================
         * 🔹 INNE TYPOWE PROBLEMY CLASSPATH (bez pelnego demo, opis)
         * ============================================================
         *  - ZLA WERSJA BIBLIOTEKI - dwie rozne wersje tego samego JAR-a
         *    w lib/ (np. "utils-1.0.jar" i "utils-2.0.jar") - Ant wezmie
         *    OBIE (fileset nie wie o "duplikatach"), a to, ktora klasa
         *    "wygra" w runtime, zalezy od kolejnosci na classpath -
         *    czesto niedeterministyczne i trudne do debugowania.
         *  - NoSuchMethodError - wystepuje, gdy klasa BYLA na classpath
         *    przy KOMPILACJI w jednej wersji (z metoda X), a w runtime
         *    podstawiono INNA wersje tego samego JAR-a, w ktorej metoda X
         *    juz nie istnieje (albo zmienila sygnature) - typowy efekt
         *    niezgodnosci wersji miedzy build-time i runtime.
         *  - DUPLIKATY KLAS - dwa JAR-y w lib/ zawierajace TA SAMA klase
         *    (np. dwie rozne biblioteki logowania z tym samym pakietem) -
         *    JVM uzyje tej, ktora znajdzie NAJPIERW na classpath, co
         *    moze byc zupelnie inna wersja niz ta, ktorej oczekujemy.
         */

        System.out.println("\n=== KONIEC LEKCJI 5 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - <path id="..."> + <fileset dir="lib" includes="*.jar"/>
         *   definiuje nazwany zestaw sciezek, uzywany dalej przez
         *   classpathref="..." (w <javac>) albo <path refid="..."/>
         *   (zagniezdzone w <java>).
         * - Ten sam classpath potrzebny jest DWA RAZY - przy kompilacji
         *   (<javac classpathref>) i przy uruchamianiu (<java> z
         *   zagniezdzonym <classpath>) - to CZESTO umykajacy szczegol.
         * - Powyzej NAPRAWDE zbudowalismy wlasny JAR jako "biblioteke
         *   zewnetrzna", pokazalismy dzialajacy build z niej korzystajacy,
         *   a potem NAPRAWDE usunelismy ten JAR i zlapalismy realny
         *   BuildException/blad kompilacji, ktory z tego wynika.
         * - Typowe problemy classpath w praktyce: brak JAR-a (cannot
         *   find symbol / ClassNotFoundException), zla wersja
         *   (NoSuchMethodError), duplikaty klas (niedeterministyczne
         *   zachowanie w zaleznosci od kolejnosci na classpath).
         * - To jeden z najprzydatniejszych tematow calego rozdzialu pod
         *   katem realnej pracy z legacy projektami Ant w firmie.
         */
    }

    private static void deleteRecursively(Path path) throws Exception {
        if (!Files.exists(path)) {
            return;
        }
        try (var stream = Files.walk(path)) {
            stream.sorted((a, b) -> b.compareTo(a)).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (Exception ignored) {
                    // demo best-effort cleanup
                }
            });
        }
    }
}
