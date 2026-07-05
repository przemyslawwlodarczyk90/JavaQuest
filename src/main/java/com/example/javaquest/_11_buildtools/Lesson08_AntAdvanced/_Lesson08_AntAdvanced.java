package com.example.javaquest._11_buildtools.Lesson08_AntAdvanced;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class _Lesson08_AntAdvanced {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 8: Ant zaawansowany (import, macrodef, condition, antcall, fileset, filterset) ===\n");

        /*
         * ============================================================
         * 📦 DUZE build.xml I ICH PROBLEMY
         * ============================================================
         * Realne, wieloletnie projekty oparte na Ancie czesto maja
         * build.xml liczace setki linii - powtarzalne fragmenty (np.
         * "skompiluj modul, spakuj go, skopiuj zasoby") kopiowane wielu
         * razy z drobnymi roznicami to prosta droga do bledow przy
         * kolejnych zmianach. Mechanizmy z tej lekcji rozwiazuja
         * DOKLADNIE ten problem: <import> dzieli plik na kawalki,
         * <macrodef> eliminuje duplikacje, <condition>/<antcall>/<fileset>/
         * <filterset> dodaja logike i elastycznosc bez pisania kodu Javy.
         * (Jest jeszcze <taskdef> - wlasne, "firmowe" taski pisane jako
         * klasy Javy i rejestrowane w Ancie - w tej lekcji tylko o nim
         * wspominamy, bez uruchamiania, bo wymaga osobnej klasy Task).
         */

        Path baseDir = Files.createTempDirectory("lesson08-advanced");
        prepareSupportFiles(baseDir);

        Path commonBuildFile = baseDir.resolve("build-common.xml");
        Files.writeString(commonBuildFile, """
                <project name="common">

                    <!-- macrodef: definiujemy WLASNY task "greet" z jednym parametrem
                         (attribute). @{who} to skladnia macrodef do wstawienia wartosci
                         atrybutu - inna niz ${property}! -->
                    <macrodef name="greet">
                        <attribute name="who"/>
                        <sequential>
                            <echo message="Witaj, @{who}! (makro z build-common.xml, wywolane raz na kazdego)"/>
                        </sequential>
                    </macrodef>

                </project>
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="advanced-demo" default="use-macro" basedir=".">

                    <!-- import: wciaga cala zawartosc build-common.xml (targety,
                         macrodefy) do TEGO projektu - tak dzieli sie wielki build.xml
                         na czesci wspoldzielone miedzy modulami. -->
                    <import file="build-common.xml"/>

                    <target name="use-macro">
                        <greet who="Ala"/>
                        <greet who="Bartek"/>
                    </target>

                    <target name="detect-os">
                        <condition property="is.windows">
                            <os family="windows"/>
                        </condition>
                        <echo message="Wlasciwosc is.windows = ${is.windows}"/>
                    </target>

                    <!-- if="is.windows": ten target (jego WLASNE taski) wykona sie
                         TYLKO gdy property is.windows istnieje i nie jest "false".
                         depends="detect-os" wykonuje sie ZAWSZE, niezaleznie od if/unless. -->
                    <target name="windows-only" depends="detect-os" if="is.windows">
                        <echo message="[windows-only] Wykryto Windows - ta sekcja sie wykonala."/>
                    </target>

                    <target name="not-windows-only" depends="detect-os" unless="is.windows">
                        <echo message="[not-windows-only] To NIE jest Windows - ta sekcja sie wykonala."/>
                    </target>

                    <target name="print-name">
                        <echo message="[antcall] Przekazane imie: ${name}"/>
                    </target>

                    <!-- antcall: wywoluje inny target JAKBY ODPALONY OD NOWA, z
                         WLASNYMI parametrami (param). Dzieki temu jeden target
                         "print-name" mozemy odpalic wielokrotnie z rozna wartoscia
                         ${name} - czego NIE dalo by sie zrobic zwyklym depends=. -->
                    <target name="use-antcall">
                        <antcall target="print-name">
                            <param name="name" value="Kasia"/>
                        </antcall>
                        <antcall target="print-name">
                            <param name="name" value="Tomek"/>
                        </antcall>
                    </target>

                    <target name="copy-filtered">
                        <mkdir dir="dest"/>
                        <copy todir="dest">
                            <fileset dir="reports">
                                <include name="**/*.txt"/>
                                <exclude name="**/*draft*"/>
                            </fileset>
                        </copy>
                    </target>

                    <target name="apply-filters">
                        <mkdir dir="dest"/>
                        <copy file="template.txt" tofile="dest/config-dev.txt" overwrite="true">
                            <filterset>
                                <filter token="ENV" value="dev"/>
                                <filter token="DB_HOST" value="localhost"/>
                            </filterset>
                        </copy>
                    </target>

                </project>
                """);

        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());

        /*
         * ============================================================
         * 🔹 1) IMPORT + MACRODEF
         * ============================================================
         */
        System.out.println("--- 1) import + macrodef ---\n");
        project.executeTarget("use-macro");

        /*
         * ============================================================
         * 🔹 2) CONDITION + PRAWDZIWY target if=/unless=
         * ============================================================
         * <condition> ustawia PROPERTY (nie zmienna logiczna - w Ancie
         * property to zawsze tekst) na "true" jesli test wewnatrz sie
         * powiodl. <os family="windows"/> to jeden z wbudowanych testow
         * (sa tez <available>, <equals>, <isset>, itd.).
         */
        System.out.println("\n--- 2) condition + prawdziwy target if=/unless= ---\n");
        project.executeTarget("windows-only");
        project.executeTarget("not-windows-only");

        /*
         * ============================================================
         * 🔹 3) ANTCALL - wywolanie targetu z parametrami
         * ============================================================
         * UWAGA na pulapke: antcall odpala target w "podprojekcie" -
         * jest wolniejszy niz normalna zaleznosc (depends=) i properties
         * ustawione RAZ (globalnie) sa NIEZMIENNE - antcall nie moze
         * "nadpisac" property, ktora juz ma wartosc. Dlatego antcall
         * najlepiej uzywac do PARAMETRYZOWANEGO powtarzania targetu,
         * a nie jako zamiennika zwyklych zaleznosci miedzy targetami.
         */
        System.out.println("\n--- 3) antcall (ten sam target, dwa razy, inne parametry) ---\n");
        project.executeTarget("use-antcall");

        /*
         * ============================================================
         * 🔹 4) FILESET - include/exclude przy kopiowaniu
         * ============================================================
         */
        System.out.println("\n--- 4) fileset (copy z include/exclude) ---\n");
        project.executeTarget("copy-filtered");
        listCopiedFiles(baseDir.resolve("dest"));

        /*
         * ============================================================
         * 🔹 5) FILTERSET - podmiana tokenow w plikach
         * ============================================================
         * filterset dziala jak "znajdz i zamien" podczas kopiowania:
         * kazde wystapienie @TOKEN@ w kopiowanym pliku zostaje zamienione
         * na podana wartosc. Typowe uzycie: pliki konfiguracyjne, ktore
         * roznia sie miedzy srodowiskami (dev/test/prod).
         */
        System.out.println("\n--- 5) filterset (podmiana @TOKEN@ w pliku) ---\n");
        project.executeTarget("apply-filters");
        System.out.println("Zawartosc dest/config-dev.txt po podmianie:");
        System.out.println(Files.readString(baseDir.resolve("dest/config-dev.txt")));

        /*
         * ============================================================
         * 🔍 A CO Z <taskdef>?
         * ============================================================
         * <taskdef name="moj-task" classname="com.firma.MojTask"
         *          classpath="..."/> pozwala zarejestrowac WLASNY task -
         * klase Javy rozszerzajaca org.apache.tools.ant.Task, ktora
         * implementuje metode execute(). To mechanizm, ktorym duze firmy
         * dodaja "firmowe" kroki builda (np. wlasna walidacje configu,
         * wlasny generator kodu) bez pisania ich jako brzydki <exec>
         * wywolujacy skrypt zewnetrzny. W tej lekcji NIE uruchamiamy
         * wlasnego taska (wymagalby osobnej, skompilowanej klasy Task) -
         * to swiadome uproszczenie, cala reszta mechanizmow powyzej jest
         * jednak w 100% realna i wykonana.
         */

        System.out.println("=== KONIEC LEKCJI 8 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - <import file="..."/> wciaga inny build.xml (targety + makra)
         *   do biezacego projektu - dzieli duzy plik na czesci
         *   wspoldzielone miedzy modulami.
         * - <macrodef name="..."><attribute name="..."/><sequential>...
         *   </sequential></macrodef> definiuje WLASNY, parametryzowany
         *   task uzywajac skladni @{atrybut} - eliminuje kopiowanie tych
         *   samych fragmentow XML-a.
         * - <condition property="..."> ustawia property na podstawie
         *   testu (<os>, <available>, <equals>, <isset>...); target
         *   if="prop"/unless="prop" WARUNKOWO wykonuje WLASNE taski
         *   targetu (ale nie jego depends=).
         * - <antcall target="..."><param .../></antcall> odpala inny
         *   target z parametrami - uzywac ostroznie (podprojekt, brak
         *   nadpisywania globalnych property, wolniejszy niz depends=).
         * - <fileset><include/><exclude/></fileset> filtruje pliki przy
         *   operacjach jak <copy> - dokladny wybor "co", bez recznego
         *   wymieniania kazdego pliku.
         * - <filterset><filter token="X" value="Y"/></filterset> podmienia
         *   @X@ na Y podczas kopiowania - klasyczna technika konfiguracji
         *   per-srodowisko.
         * - <taskdef> (tylko wspomniany) pozwala dodac wlasne, "firmowe"
         *   taski jako klasy Javy.
         */
    }

    private static void prepareSupportFiles(Path baseDir) throws Exception {
        Path reports = baseDir.resolve("reports");
        Path reportsSub = reports.resolve("sub");
        Files.createDirectories(reportsSub);

        Files.writeString(reports.resolve("january.txt"), "Raport za styczen.\n");
        Files.writeString(reports.resolve("february.txt"), "Raport za luty.\n");
        Files.writeString(reports.resolve("february_draft.txt"), "Wersja robocza - NIE powinna trafic do dest.\n");
        Files.writeString(reports.resolve("notes.md"), "To nie jest .txt - nie powinno trafic do dest.\n");
        Files.writeString(reportsSub.resolve("march.txt"), "Raport za marzec (w podkatalogu).\n");

        Files.writeString(baseDir.resolve("template.txt"), """
                Environment: @ENV@
                Database host: @DB_HOST@
                """);
    }

    private static void listCopiedFiles(Path destDir) throws Exception {
        System.out.println("Pliki skopiowane do " + destDir + " (tylko *.txt, bez 'draft' w nazwie):");
        try (Stream<Path> walk = Files.walk(destDir)) {
            List<Path> files = walk.filter(Files::isRegularFile).sorted().toList();
            for (Path file : files) {
                System.out.println("  " + destDir.relativize(file));
            }
        }
    }
}
