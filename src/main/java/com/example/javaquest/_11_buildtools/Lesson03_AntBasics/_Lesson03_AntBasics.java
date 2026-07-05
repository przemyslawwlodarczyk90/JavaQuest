package com.example.javaquest._11_buildtools.Lesson03_AntBasics;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson03_AntBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 3: Podstawy Apache Ant ===\n");

        /*
         * ============================================================
         * 📦 CO TO JEST APACHE ANT?
         * ============================================================
         * Apache Ant to jeden z najstarszych build toolow dla Javy
         * (powstal w 1999 roku). Jego kluczowe cechy:
         *
         *  - OPARTY NA XML - caly przepis budowania zapisuje sie w pliku
         *    build.xml (zwykly XML, bez wlasnego jezyka programowania).
         *  - IMPERATYWNY - w Ancie TY piszesz KAZDY krok po kroku
         *    ("skompiluj to", "skopiuj tamto", "spakuj do JAR-a") -
         *    w przeciwienstwie do Mavena, ktory jest bardziej
         *    DEKLARATYWNY ("to jest projekt typu jar, Maven wie co
         *    robic"). Ant nie zgadnie za Ciebie kolejnosci krokow.
         *  - PELNA KONTROLA - brak "magii" i domyslnych konwencji: TY
         *    decydujesz o strukturze katalogow, nazwach targetow, kolejnosci
         *    wszystkiego. To zaleta (elastycznosc) i wada (trzeba samemu
         *    napisac wiecej) jednoczesnie.
         *  - BRAK NARZUCONEJ STRUKTURY - Maven wymusza np. src/main/java,
         *    Ant nie wymusza NICZEGO - katalogi projektu nazywasz i
         *    rozmieszczasz jak chcesz (zobaczymy to w Lekcji 4).
         *  - DLACZEGO POPULARNY W LEGACY ENTERPRISE? Wiele duzych, starych
         *    systemow firmowych (10-20+ lat) powstalo, gdy Ant byl
         *    standardem (przed Mavenem/Gradle). Migracja takich systemow
         *    na nowszy build tool to duzy, ryzykowny projekt, wiec sporo
         *    firm wciaz go uzywa i utrzymuje - stad Ant to WCIAZ realna
         *    umiejetnosc w pracy, nie tylko "historia".
         */

        /*
         * ============================================================
         * 🔹 STRUKTURA build.xml
         * ============================================================
         * Najwazniejsze elementy pliku build.xml:
         *
         *  - <project name="..." default="..." basedir=".">
         *      Korzen calego pliku. "default" to target wykonywany, gdy
         *      nie podamy zadnego jawnie. "basedir" to katalog bazowy,
         *      wzgledem ktorego liczone sa wszystkie sciezki wzgledne.
         *  - <target name="..." depends="..." description="...">
         *      Jeden ETAP procesu budowania (np. "compile", "test",
         *      "package"). "depends" to lista targetow, ktore MUSZA
         *      wykonac sie PRZED tym targetem (Ant sam ustala kolejnosc
         *      i wykonuje kazdy zalezny target tylko raz).
         *  - <task .../> (wewnatrz targetu)
         *      Konkretna AKCJA do wykonania - np. <echo>, <javac>, <jar>.
         *      Taski dzielimy na: WBUDOWANE (dostarczone z Antem, jak
         *      <javac>/<copy>/<delete>), ZEWNETRZNE (z dodatkowych JAR-ow,
         *      np. <junit> z ant-junit - poznamy w Lekcji 6) i WLASNE
         *      (napisane samemu w Javie jako klasa rozszerzajaca Task -
         *      wykraczajace poza ten rozdzial).
         *  - <property name="..." value="..."/>
         *      Definiuje zmienna tekstowa, ktora wewnatrz build.xml
         *      przywolujemy jako ${nazwa}. Wlasciwosci mozna rowniez
         *      wczytac z zewnetrznego pliku (build.properties,
         *      local.properties) przez <property file="...">.
         */

        System.out.println("--- DEMO A: najprostszy mozliwy build.xml (jeden target, <echo>) ---\n");

        /*
         * ============================================================
         * 🔍 WZORZEC: OSADZONY (EMBEDDED) APACHE ANT KROK PO KROKU
         * ============================================================
         * Ten wzorzec bedzie sie powtarzal (z coraz mniejszym
         * komentarzem) w KAZDEJ kolejnej lekcji o Ancie w tym rozdziale -
         * dokladnie tak, jak wzorzec embedded Tomcata w rozdziale
         * _07_servlets. Tu wyjasniamy go raz, dokladnie, linia po linii.
         */

        // KROK 1: katalog bazowy projektu Ant - musi istniec na dysku,
        // bo to wzgledem niego Ant rozwiazuje sciezki wzgledne.
        Path baseDir = Files.createTempDirectory("lesson03ant-hello");

        // KROK 2: zapisujemy PRAWDZIWY plik build.xml na dysku - to
        // dokladnie to samo, co napisalby programista w edytorze tekstu.
        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="lesson03-demo" default="hello" basedir=".">
                    <target name="hello" description="Wypisuje powitanie">
                        <echo message="Hello from real Apache Ant!"/>
                    </target>
                </project>
                """);

        // KROK 3: nowa instancja Project - to "silnik" Anta, jeszcze bez
        // wczytanego build.xml i bez skonfigurowanego logowania.
        Project project = new Project();
        // KROK 4: init() laduje wbudowane definicje taskow (echo, javac,
        // jar, copy, ...) - bez tego wywolania Ant nie rozpoznalby ZADNEGO
        // taska, nawet wbudowanego.
        project.init();
        // KROK 5: katalog bazowy MUSI byc ustawiony PRZED wykonaniem
        // jakiegokolwiek targetu (analogicznie do basedir w XML-u).
        project.setBaseDir(baseDir.toFile());

        // KROK 6: DefaultLogger to "gardlo", przez ktore Ant wypisuje
        // WSZYSTKIE swoje komunikaty (echo, bledy, informacje o
        // targetach). Podpinajac go do System.out/System.err, widzimy
        // PRAWDZIWY output Anta w konsoli naszej aplikacji Java.
        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        // MSG_INFO to standardowy poziom - pokazuje komunikaty <echo>,
        // start/koniec targetow, ale nie zalewa nas debugowym szumem.
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        // KROK 7: ProjectHelper.configureProject PARSUJE plik build.xml
        // i "wgrywa" jego zawartosc (targety, taski, properties) do
        // obiektu Project - dopiero po tym kroku Project "wie", jakie
        // targety istnieja.
        ProjectHelper.configureProject(project, buildFile.toFile());

        // KROK 8: executeTarget uruchamia konkretny target po nazwie.
        // project.getDefaultTarget() odczytuje atrybut default="..." z
        // <project> - to jest programowy rownowaznik wpisania w
        // terminalu samego "ant" (bez podawania nazwy targetu).
        project.executeTarget(project.getDefaultTarget());

        System.out.println("\n--- DEMO B: wiele targetow, depends, properties, prawdziwa kompilacja/pakowanie ---\n");

        /*
         * ============================================================
         * 🔹 depends I ${property} W PRAKTYCE
         * ============================================================
         * Ponizszy build.xml ma 5 targetow tworzacych PRAWDZIWY,
         * kompletny mini-proces budowania:
         *
         *   clean -> init -> compile -> run
         *                            -> jar
         *
         * "compile" ZALEZY od "init" (depends="init") - Ant sam
         * zagwarantuje, ze "init" wykona sie NAJPIERW, nawet jesli
         * poprosimy tylko o "compile". "run" i "jar" oba zaleza od
         * "compile" - jesli poprosimy o "jar", Ant wykona najpierw
         * "init", potem "compile", a na koniec "jar" (kazdy target
         * TYLKO RAZ, nawet gdyby kilka innych targetow od niego zalezalo).
         *
         * Wlasciwosci (${src.dir}, ${build.dir}, ...) pozwalaja NIE
         * powtarzac tych samych sciezek w kazdym tasku - zmiana jednej
         * linii <property> zmienia zachowanie calego build.xml.
         */

        Path projectDir = Files.createTempDirectory("lesson03ant-full");
        Path srcDir = projectDir.resolve("src");
        Files.createDirectories(srcDir);

        // Generujemy prosta klase Main.java, ktora Ant bedzie kompilowal,
        // pakowal i uruchamial - to nasz "produkt" tego mini-builda.
        Files.writeString(srcDir.resolve("Main.java"), """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Uruchomione przez PRAWDZIWY task <java> Apache Anta!");
                    }
                }
                """);

        Path fullBuildFile = projectDir.resolve("build.xml");
        Files.writeString(fullBuildFile, """
                <project name="lesson03-full-demo" default="jar" basedir=".">

                    <property name="src.dir" value="src"/>
                    <property name="build.dir" value="build"/>
                    <property name="dist.dir" value="dist"/>
                    <property name="main.class" value="Main"/>
                    <property name="jar.name" value="lesson03.jar"/>

                    <target name="clean" description="Usuwa katalogi build i dist">
                        <delete dir="${build.dir}"/>
                        <delete dir="${dist.dir}"/>
                        <echo message="Wyczyszczono build.dir i dist.dir"/>
                    </target>

                    <target name="init" depends="clean" description="Tworzy katalogi wyjsciowe">
                        <mkdir dir="${build.dir}"/>
                        <mkdir dir="${dist.dir}"/>
                        <echo message="Utworzono katalogi ${build.dir} i ${dist.dir}"/>
                    </target>

                    <target name="compile" depends="init" description="Kompiluje kod zrodlowy">
                        <javac srcdir="${src.dir}" destdir="${build.dir}" includeantruntime="false"/>
                        <echo message="Skompilowano zawartosc ${src.dir} do ${build.dir}"/>
                    </target>

                    <target name="run" depends="compile" description="Uruchamia skompilowana klase">
                        <java classname="${main.class}" classpath="${build.dir}" fork="true"/>
                    </target>

                    <target name="jar" depends="compile" description="Pakuje klasy do JAR-a">
                        <jar destfile="${dist.dir}/${jar.name}" basedir="${build.dir}">
                            <manifest>
                                <attribute name="Main-Class" value="${main.class}"/>
                            </manifest>
                        </jar>
                        <echo message="Zbudowano ${dist.dir}/${jar.name}"/>
                    </target>

                </project>
                """);

        Project fullProject = new Project();
        fullProject.init();
        fullProject.setBaseDir(projectDir.toFile());

        DefaultLogger fullLogger = new DefaultLogger();
        fullLogger.setOutputPrintStream(System.out);
        fullLogger.setErrorPrintStream(System.err);
        fullLogger.setMessageOutputLevel(Project.MSG_INFO);
        fullProject.addBuildListener(fullLogger);

        ProjectHelper.configureProject(fullProject, fullBuildFile.toFile());

        // Prosimy tylko o "run" - Ant SAM rozwiazuje lancuch zaleznosci
        // (clean -> init -> compile -> run), wykonujac kazdy target
        // dokladnie raz, w poprawnej kolejnosci.
        System.out.println(">>> ant run  (Ant sam wykona: clean -> init -> compile -> run)");
        fullProject.executeTarget("run");

        System.out.println("\n>>> ant jar  (compile jest juz nie potrzebny ponownie w tym samym Project,");
        System.out.println("     ale poprosimy o caly lancuch jeszcze raz od zera - nowy Project):");

        // Dla czystosci demo tworzymy NOWY obiekt Project (tak jakby to
        // byl nowy, osobny proces "ant jar" w terminalu) - inaczej Ant
        // pamietalby, ze "compile" juz sie wykonal w TYM SAMYM przebiegu.
        Project jarProject = new Project();
        jarProject.init();
        jarProject.setBaseDir(projectDir.toFile());
        jarProject.addBuildListener(fullLogger);
        ProjectHelper.configureProject(jarProject, fullBuildFile.toFile());
        jarProject.executeTarget("jar");

        Path builtJar = projectDir.resolve("dist").resolve("lesson03.jar");
        System.out.println("Czy PRAWDZIWY JAR faktycznie powstal na dysku? " + Files.exists(builtJar)
                + " (" + builtJar + ")");

        /*
         * ============================================================
         * 🔍 PODSTAWOWE TASKI - echo, mkdir, delete, copy, move
         * ============================================================
         * Powyzej uzylismy juz echo, mkdir, delete, javac, java i jar.
         * Ponizej krotkie demo pozostalych dwoch podstawowych taskow z
         * lekcji - copy i move - na prawdziwych plikach na dysku.
         */

        Path copyMoveDir = Files.createTempDirectory("lesson03ant-copymove");
        Path sourceFile = copyMoveDir.resolve("source.txt");
        Files.writeString(sourceFile, "Zawartosc pliku demo do copy/move.");

        Path copyMoveBuildFile = copyMoveDir.resolve("build.xml");
        Files.writeString(copyMoveBuildFile, """
                <project name="copy-move-demo" default="demo" basedir=".">
                    <target name="demo">
                        <copy file="source.txt" tofile="source-copy.txt"/>
                        <echo message="Skopiowano source.txt -> source-copy.txt"/>
                        <move file="source-copy.txt" tofile="moved/renamed.txt"/>
                        <echo message="Przeniesiono source-copy.txt -> moved/renamed.txt"/>
                    </target>
                </project>
                """);

        Project copyMoveProject = new Project();
        copyMoveProject.init();
        copyMoveProject.setBaseDir(copyMoveDir.toFile());
        copyMoveProject.addBuildListener(fullLogger);
        ProjectHelper.configureProject(copyMoveProject, copyMoveBuildFile.toFile());
        copyMoveProject.executeTarget("demo");

        Path movedFile = copyMoveDir.resolve("moved").resolve("renamed.txt");
        System.out.println("Czy plik po copy+move naprawde istnieje w nowej lokalizacji? " + Files.exists(movedFile));

        System.out.println("\n=== KONIEC LEKCJI 3 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ant = XML (build.xml) + imperatywne targety + taski, bez
         *   narzuconej struktury katalogow - pelna kontrola, ale wiecej
         *   pracy recznej niz w Mavenie/Gradle.
         * - project (name/default/basedir) -> target (name/depends/
         *   description) -> task (echo/javac/jar/copy/move/mkdir/delete/...).
         * - property + ${nazwa} pozwala parametryzowac caly build.xml
         *   jedna zmienna zamiast powtarzania sciezek wszedzie.
         * - depends buduje LANCUCH zaleznosci miedzy targetami - Ant sam
         *   ustala kolejnosc i wykonuje kazdy target tylko raz.
         * - Wzorzec embedded Anta: new Project() -> init() -> setBaseDir
         *   -> DefaultLogger (System.out/err) -> addBuildListener ->
         *   ProjectHelper.configureProject(project, buildFile) ->
         *   executeTarget(nazwa). Ten sam wzorzec (z coraz mniejszym
         *   komentarzem) wroci w Lekcjach 4-10.
         * - Powyzej NAPRAWDE wykonalismy prawdziwy Ant: skompilowal
         *   Main.java, uruchomil go, spakowal do dzialajacego JAR-a i
         *   skopiowal/przeniosl prawdziwy plik na dysku.
         */
    }
}
