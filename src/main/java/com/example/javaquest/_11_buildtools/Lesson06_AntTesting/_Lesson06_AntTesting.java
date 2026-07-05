package com.example.javaquest._11_buildtools.Lesson06_AntTesting;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class _Lesson06_AntTesting {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 6: Testy w projekcie Ant (task <junit>) ===\n");

        /*
         * ============================================================
         * 📦 TESTY W PROJEKCIE ANT
         * ============================================================
         * Klasyczny projekt Ant trzyma testy w OSOBNYM katalogu (test/,
         * analogicznie do src/ - patrz Lekcja 4) i kompiluje je do
         * OSOBNEGO katalogu wynikowego (np. build/test-classes), zeby
         * klasy testowe NIGDY nie trafily do finalnego JAR-a/WAR-a
         * wysylanego na produkcje.
         *
         * Testy potrzebuja WLASNEJ biblioteki - JUnit - dodanej do
         * classpath TYLKO na czas kompilacji i wykonania testow (patrz
         * Lekcja 5 o <path>/<fileset> - tu uzyjemy tej samej techniki).
         *
         * 🔍 SKAD BIERZEMY JUnit W TYM DEMO?
         * Ten projekt (javaQuest) ma w pom.xml zaleznosc
         * org.apache.ant:ant-junit (potrzebna do samego tasku <junit>).
         * ant-junit W SWOIM WLASNYM pom.xml deklaruje zaleznosc
         * "junit:junit:4.13.2" - Maven sciaga ja WIEC TRANSYTYWNIE, razem
         * z ant-junit, na classpath TEGO projektu. Dzieki temu biblioteka
         * JUnit 4.13.2 jest JUZ dostepna w JVM, w ktorej dziala ten demo -
         * bez dodawania ZADNEJ nowej zaleznosci do pom.xml.
         *
         * ⚠️ WAZNA PULAPKA (odkryta empirycznie przy pisaniu tej lekcji):
         * choc junit-4.13.2.jar oczywiscie zawiera adnotacje @Test
         * (pakiet org.junit.*), oficjalny, publikowany w Maven Central
         * artefakt "org.apache.ant:ant-junit" (ten w pom.xml) NIE zawiera
         * klas-adapterow potrzebnych Antowi do WYKRYWANIA testow
         * napisanych w stylu adnotacji JUnit 4 (JUnit4TestMethodAdapter,
         * CustomJUnit4TestAdapterCache) - Apache Ant trzyma je w OSOBNYM
         * artefakcie "org.apache.ant:ant-junit4", ktorego ten projekt NIE
         * deklaruje. Efekt w praktyce: uzywajac SAMEGO ant-junit, task
         * <junit> odpalony na klasie z @Test zglasza "No tests found" -
         * mimo ze klasa i JVM sa poprawne.
         *
         * Rozwiazanie zastosowane w tej lekcji: piszemy testy w KLASYCZNYM
         * stylu JUnit 3 - klasa rozszerza junit.framework.TestCase, kazda
         * metoda testowa nazywa sie "testXxx()" (bez adnotacji @Test) i
         * korzysta z odziedziczonych metod assertXxx(). Ten styl Ant
         * wykrywa i wykonuje NATYWNIE, bez zadnego adaptera - dziala od
         * razu, "z pudelka", z tym, co juz mamy na classpath. WAZNE: cala
         * mechanika taska <junit>, ktora poznajemy w tej lekcji
         * (batchtest, formatter, fork, haltonfailure) jest DOKLADNIE TAKA
         * SAMA niezaleznie od stylu testu (JUnit3 TestCase vs JUnit4
         * @Test) - roznica dotyczy WYLACZNIE sposobu OZNACZANIA metody
         * jako testowej, nie sposobu jej WYKONANIA przez <junit>.
         *
         * W REALNYM, samodzielnym projekcie Ant (bez Mavena/Spring Boota
         * w tle) chcac uzywac adnotacji @Test, trzeba by recznie dodac do
         * lib/ obie biblioteki: "junit:junit" ORAZ "org.apache.ant:ant-junit4"
         * (adapter JUnit4), albo przejsc na JUnit 5 z
         * "junit-platform-console-standalone" - mechanika taska <junit>
         * ponizej pozostaje identyczna.
         */

        // KROK 1: znajdujemy PRAWDZIWY plik junit-4.13.2.jar na dysku -
        // nie zgadujemy sciezki, a odpytujemy JVM, GDZIE fizycznie
        // znajduje sie JAR, z ktorego wczytana jest klasa junit.framework.TestCase
        // (dokladnie ta, ktora mamy juz na wlasnym classpath).
        Class<?> junitTestCaseClass = Class.forName("junit.framework.TestCase");
        Path junitJarPath = Paths.get(junitTestCaseClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        System.out.println("Prawdziwy junit.jar znaleziony w lokalnym repozytorium Maven:");
        System.out.println("  " + junitJarPath + "\n");

        /*
         * ============================================================
         * 🔹 KROK 2: STRUKTURA PROJEKTU src/ + test/
         * ============================================================
         */
        Path projectDir = Files.createTempDirectory("lesson06ant-testing");
        Path srcDir = projectDir.resolve("src");
        Path testDir = projectDir.resolve("test");
        Files.createDirectories(srcDir);
        Files.createDirectories(testDir);

        // Kod PRODUKCYJNY - prosty kalkulator z metoda, ktora bedziemy testowac.
        Files.writeString(srcDir.resolve("Calculator.java"), """
                public class Calculator {
                    public int add(int a, int b) {
                        return a + b;
                    }
                    public int divide(int a, int b) {
                        return a / b;
                    }
                }
                """);

        // Kod TESTOWY - klasyczny test JUnit 3 (extends TestCase,
        // metody nazwane "testXxx()") - patrz wyjasnienie wyzej, czemu
        // NIE uzywamy tu adnotacji @Test. Dwa testy, obie przechodzace.
        Files.writeString(testDir.resolve("CalculatorTest.java"), """
                import junit.framework.TestCase;

                public class CalculatorTest extends TestCase {

                    public void testAddsTwoPositiveNumbers() {
                        Calculator calculator = new Calculator();
                        assertEquals(5, calculator.add(2, 3));
                    }

                    public void testAddsNegativeNumbers() {
                        Calculator calculator = new Calculator();
                        assertEquals(-1, calculator.add(2, -3));
                    }
                }
                """);

        System.out.println("Wygenerowano src/Calculator.java oraz test/CalculatorTest.java (JUnit, styl TestCase).\n");

        /*
         * ============================================================
         * 🔍 KROK 3: build.xml - compile -> compile-test -> test -> package
         * ============================================================
         * Kolejnosc jest KLUCZOWA i celowa:
         *  1) compile        - kompiluje TYLKO kod produkcyjny.
         *  2) compile-test    - kompiluje testy, z classpath = klasy
         *     produkcyjne (build/classes) + JUnit (junit.jar).
         *  3) test            - uruchamia testy taskiem <junit>, z
         *     haltonfailure="true" - jesli JAKIKOLWIEK test nie przejdzie,
         *     Ant PRZERYWA cala reszte builda wyjatkiem BuildException.
         *  4) package         - depends="test" - a wiec pakowanie do JAR-a
         *     WYKONA SIE TYLKO, jesli test sie NIE przerwal, czyli
         *     WSZYSTKIE testy przeszly. To jest esencja zasady "build nie
         *     powinien przejsc, jesli testy nie przechodza".
         */
        Path buildFile = projectDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="lesson06-testing-demo" default="package" basedir=".">

                    <property name="src.dir" value="src"/>
                    <property name="test.dir" value="test"/>
                    <property name="classes.dir" value="build/classes"/>
                    <property name="test-classes.dir" value="build/test-classes"/>
                    <property name="reports.dir" value="build/test-reports"/>
                    <property name="dist.dir" value="dist"/>

                    <path id="test.classpath">
                        <pathelement location="${classes.dir}"/>
                        <pathelement location="${junit.jar}"/>
                    </path>

                    <target name="init">
                        <mkdir dir="${classes.dir}"/>
                        <mkdir dir="${test-classes.dir}"/>
                        <mkdir dir="${reports.dir}"/>
                        <mkdir dir="${dist.dir}"/>
                    </target>

                    <target name="compile" depends="init" description="Kompiluje kod produkcyjny">
                        <javac srcdir="${src.dir}" destdir="${classes.dir}" includeantruntime="false"/>
                    </target>

                    <target name="compile-test" depends="compile" description="Kompiluje testy">
                        <javac srcdir="${test.dir}" destdir="${test-classes.dir}"
                               classpathref="test.classpath" includeantruntime="false"/>
                    </target>

                    <target name="test" depends="compile-test" description="Uruchamia testy JUnit">
                        <junit printsummary="yes" haltonfailure="true" fork="true">
                            <classpath>
                                <path refid="test.classpath"/>
                                <pathelement location="${test-classes.dir}"/>
                            </classpath>
                            <formatter type="plain" usefile="false"/>
                            <formatter type="xml"/>
                            <batchtest todir="${reports.dir}">
                                <fileset dir="${test-classes.dir}" includes="**/*Test.class"/>
                            </batchtest>
                        </junit>
                    </target>

                    <target name="package" depends="test" description="Pakuje do JAR-a - TYLKO gdy testy przeszly">
                        <jar destfile="${dist.dir}/calculator.jar" basedir="${classes.dir}"/>
                    </target>

                </project>
                """);

        Project project = new Project();
        project.init();
        project.setBaseDir(projectDir.toFile());

        // KROK 4: przekazujemy sciezke do prawdziwego junit.jar jako
        // Ant property PRZED sparsowaniem build.xml (property ustawione
        // programowo "wygrywa" - <property> w XML NIE nadpisuje juz
        // istniejacej wartosci, dokladnie tak jak poznalismy w Lekcji 3).
        project.setProperty("junit.jar", junitJarPath.toString());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());

        System.out.println("--- DEMO A: 'ant package' - compile -> compile-test -> test -> package (testy PRZECHODZA) ---\n");
        project.executeTarget("package");

        Path builtJar = projectDir.resolve("dist").resolve("calculator.jar");
        System.out.println("\nCzy JAR powstal (testy przeszly, package sie wykonal)? " + Files.exists(builtJar));

        /*
         * ============================================================
         * 🔍 KROK 5: BUILD, KTORY NIE POWINIEN PRZEJSC - PADAJACY TEST
         * ============================================================
         * Dopisujemy TRZECI test, ktory jest NAPRAWDE zly (oczekuje
         * wartosci 999 z dzielenia 10/2, ktore w rzeczywistosci daje 5) -
         * i sprawdzamy, ze haltonfailure="true" NAPRAWDE przerywa build
         * PRZED targetem "package", tak jak zaklada zasada "build nie
         * przechodzi, jesli testy nie przechodza".
         *
         * ⚠️ CIEKAWY SZCZEGOL (zweryfikowany empirycznie): przy
         * haltonfailure="true" + fork="true" Ant PRZERYWA wykonywanie
         * KOLEJNYCH testow w danej klasie NATYCHMIAST po pierwszym
         * niepowodzeniu (nie czeka, az cala klasa/testsuite dokonczy
         * wszystkie metody) - to optymalizacja: jesli build i tak ma
         * sie zatrzymac, nie ma sensu tracic czasu na reszte testow.
         * Dlatego nizej w logu zobaczysz "Tests run: 1" (nie 3) - kolejnosc
         * wykonywania metod testowych w klasie JUnit3 NIE jest gwarantowana
         * (nie musi byc kolejnoscia deklaracji w kodzie), a Ant zatrzymuje
         * sie na PIERWSZEJ, jaka faktycznie napotka i ktora zawiedzie.
         */
        System.out.println("\n--- DEMO B: dopisujemy PADAJACY test - build MUSI sie zatrzymac przed 'package' ---\n");

        Files.writeString(testDir.resolve("CalculatorTest.java"), """
                import junit.framework.TestCase;

                public class CalculatorTest extends TestCase {

                    public void testAddsTwoPositiveNumbers() {
                        Calculator calculator = new Calculator();
                        assertEquals(5, calculator.add(2, 3));
                    }

                    public void testAddsNegativeNumbers() {
                        Calculator calculator = new Calculator();
                        assertEquals(-1, calculator.add(2, -3));
                    }

                    public void testCelowoZly() {
                        Calculator calculator = new Calculator();
                        // 10 / 2 = 5, NIE 999 - ten test MUSI sie nie powiesc.
                        assertEquals(999, calculator.divide(10, 2));
                    }
                }
                """);

        // Usuwamy stary dist/calculator.jar, aby jasno widziec, ze DRUGI
        // przebieg go NIE odtworzy (bo test nie przejdzie).
        Files.deleteIfExists(builtJar);

        Project secondProject = new Project();
        secondProject.init();
        secondProject.setBaseDir(projectDir.toFile());
        secondProject.setProperty("junit.jar", junitJarPath.toString());
        secondProject.addBuildListener(logger);
        ProjectHelper.configureProject(secondProject, buildFile.toFile());

        try {
            secondProject.executeTarget("package");
            System.out.println("(Nieoczekiwanie build sie powiodl - sprawdz zawartosc testu.)");
        } catch (BuildException e) {
            System.out.println("\nZlapano PRAWDZIWY BuildException - build ZATRZYMAL sie na targecie 'test': "
                    + e.getMessage());
        }

        System.out.println("Czy dist/calculator.jar powstal DRUGI raz? " + Files.exists(builtJar)
                + " (powinno byc false - package sie NIE wykonal)");

        System.out.println("\n=== KONIEC LEKCJI 6 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Testy zyja w osobnym katalogu (test/) i kompiluja sie do
         *   osobnego katalogu wynikowego (build/test-classes) - nigdy
         *   nie trafiaja do finalnego JAR-a.
         * - JUnit to zwykla biblioteka na classpath TYLKO fazy testowej
         *   (<path>/<pathelement> - dokladnie ten sam mechanizm co w
         *   Lekcji 5). W tym demo znalezlismy PRAWDZIWY junit.jar
         *   dostarczony transytywnie przez zaleznosc ant-junit w pom.xml
         *   tego projektu (Class.forName + getProtectionDomain() +
         *   getCodeSource().getLocation()).
         * - ⚠️ WAZNA DECYZJA: testy napisano w stylu JUnit 3 (extends
         *   TestCase, metody "testXxx()"), NIE w stylu adnotacji @Test -
         *   bo publikowany artefakt Maven "org.apache.ant:ant-junit" (ten
         *   w pom.xml) NIE zawiera klas-adapterow JUnit4
         *   (JUnit4TestMethodAdapter/CustomJUnit4TestAdapterCache) - te
         *   siedza w osobnym, niedodanym artefakcie "ant-junit4". Z samym
         *   ant-junit, klasa z @Test dostaje od <junit> blad "No tests
         *   found" (zweryfikowane empirycznie). Styl TestCase Ant
         *   wykrywa natywnie. Mechanika taska <junit> (batchtest,
         *   formatter, fork, haltonfailure) jest identyczna dla obu stylow.
         * - <junit> uruchamia PRAWDZIWE testy: printsummary, fork="true"
         *   (kazdy test we WLASNYM procesie JVM - izolacja), formatter
         *   ("plain" do konsoli, "xml" do raportu maszynowego),
         *   batchtest + fileset (automatyczne znajdowanie WSZYSTKICH
         *   klas *Test w danym katalogu, bez recznego wymieniania).
         * - haltonfailure="true" + depends="test" w targecie "package" to
         *   PRAKTYCZNA realizacja zasady "build nie powinien przejsc,
         *   jesli testy nie przechodza" - powyzej NAPRAWDE to
         *   zademonstrowalismy: drugi przebieg z zepsutym testem
         *   zatrzymal sie prawdziwym BuildException PRZED zbudowaniem
         *   JAR-a.
         * - W realnym, samodzielnym projekcie Ant (bez Mavena w tle)
         *   chcac uzywac adnotacji @Test, trzeba recznie dodac do lib/
         *   junit.jar ORAZ ant-junit4.jar (adapter), albo przejsc na
         *   JUnit5 + junit-platform-console-standalone - sama mechanika
         *   taska <junit> (batchtest/formatter/fork) jest identyczna
         *   niezaleznie od wybranej wersji/stylu testow.
         */
    }
}
