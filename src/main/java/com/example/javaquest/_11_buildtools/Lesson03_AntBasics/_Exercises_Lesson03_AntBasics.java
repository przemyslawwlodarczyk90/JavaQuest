package com.example.javaquest._11_buildtools.Lesson03_AntBasics;

public class _Exercises_Lesson03_AntBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleEchoTarget {
        /*
         * 🧪 Zadanie 1:
         * Wygeneruj build.xml z JEDNYM targetem "start" zawierajacym jeden task
         * <echo message="Moj pierwszy target Anta"/>. Uruchom go embedowanym Antem
         * (Project + ProjectHelper.configureProject + executeTarget) i zweryfikuj, ze
         * komunikat pojawil sie na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefaultTargetAttribute {
        /*
         * 🧪 Zadanie 2:
         * Wygeneruj build.xml z ATRYBUTEM default="powitanie" w <project> i targetem
         * "powitanie" (echo). Uruchom go wywolujac project.executeTarget(project.getDefaultTarget())
         * (bez podawania nazwy na sztywno) i zweryfikuj, ze wykonal sie wlasciwy target.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BuiltInTasksList {
        /*
         * 🧪 Zadanie 3:
         * Bez uzywania Anta: wypisz na konsoli liste 8 podstawowych, wbudowanych taskow
         * Anta wspomnianych w lekcji (echo, mkdir, delete, copy, move, javac, java, jar)
         * razem z jednozdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MkdirTask {
        /*
         * 🧪 Zadanie 4:
         * Wygeneruj build.xml z targetem uzywajacym <mkdir dir="output"/>. Uruchom go
         * embedowanym Antem i zweryfikuj przez Files.exists/Files.isDirectory, ze katalog
         * "output" naprawde powstal na dysku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TaskCategoriesExplanation {
        /*
         * 🧪 Zadanie 5:
         * Bez uzywania Anta: wypisz 3 kategorie taskow z lekcji (wbudowane, zewnetrzne,
         * wlasne) z jednym przykladem kazdej (np. wbudowany: <echo>, zewnetrzny: <junit> z
         * ant-junit, wlasny: klasa Java rozszerzajaca org.apache.tools.ant.Task).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DeleteTask {
        /*
         * 🧪 Zadanie 6:
         * Utworz plik i katalog na dysku, a potem wygeneruj build.xml z targetem
         * uzywajacym <delete file="..."/> i <delete dir="..."/> do ich usuniecia. Uruchom
         * embedowanym Antem i zweryfikuj (Files.exists), ze oba naprawde zniknely.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PropertyBasicUsage {
        /*
         * 🧪 Zadanie 7:
         * Wygeneruj build.xml z <property name="message" value="Witaj z property!"/> i
         * targetem uzywajacym <echo message="${message}"/>. Uruchom go embedowanym Antem i
         * zweryfikuj, ze na konsoli pojawila sie wartosc property, nie sam tekst "${message}".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TargetDescriptionAttribute {
        /*
         * 🧪 Zadanie 8:
         * Wygeneruj build.xml z 3 targetami, kazdy z atrybutem description="...". Po
         * skonfigurowaniu Project (configureProject), przejdz przez
         * project.getTargets().values() i wypisz nazwe + opis kazdego targetu (bez ich
         * wykonywania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TwoIndependentTargets {
        /*
         * 🧪 Zadanie 9:
         * Wygeneruj build.xml z DWOMA niezaleznymi targetami (bez depends miedzy nimi),
         * kazdy z innym komunikatem <echo>. Uruchom NAJPIERW jeden, potem drugi (dwa
         * odrebne wywolania executeTarget na tym samym Project) i zweryfikuj oba komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImperativeVsDeclarativeExplanation {
        /*
         * 🧪 Zadanie 10:
         * Bez uzywania Anta: wypisz na konsoli krotkie porownanie (2-3 linie) "Ant jest
         * imperatywny" vs "Maven jest deklaratywny", z jednym konkretnym przykladem
         * roznicy (np. Ant: musisz sam napisac <javac>, Maven: samo wie, ze projekt typu
         * jar wymaga kompilacji).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DependsChainThreeTargets {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj build.xml z 3 targetami A, B (depends="A"), C (depends="B"), kazdy z
         * innym <echo>. Wywolaj executeTarget("C") i zweryfikuj (po kolejnosci komunikatow
         * na konsoli), ze Ant wykonal A, potem B, potem C - w tej kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DiamondDependencyRunsOnce {
        /*
         * 🧪 Zadanie 12:
         * Wygeneruj build.xml w ukladzie "diamentu": init (bez depends), compileA
         * (depends="init"), compileB (depends="init"), all (depends="compileA,compileB").
         * Wywolaj executeTarget("all") i zweryfikuj, ze target "init" wykonal sie TYLKO
         * RAZ (policz wystapienia jego komunikatu <echo> na konsoli/w logu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultiplePropertiesComposition {
        /*
         * 🧪 Zadanie 13:
         * Wygeneruj build.xml z 3 property (np. "app.name", "app.version",
         * "app.author") i targetem skladajacym je w jeden komunikat <echo
         * message="${app.name} v${app.version} by ${app.author}"/>. Zweryfikuj poprawne
         * podstawienie wszystkich trzech wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PropertiesFromExternalFile {
        /*
         * 🧪 Zadanie 14:
         * Wygeneruj plik build.properties (np. "greeting=Witaj z pliku wlasciwosci") oraz
         * build.xml z <property file="build.properties"/> i targetem uzywajacym
         * ${greeting}. Uruchom embedowanym Antem i zweryfikuj poprawne wczytanie
         * wlasciwosci z ZEWNETRZNEGO pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompileTwoClassesWithJavac {
        /*
         * 🧪 Zadanie 15:
         * Wygeneruj 2 pliki .java (niezalezne klasy) i build.xml z targetem "compile"
         * uzywajacym <javac srcdir="src" destdir="build" includeantruntime="false"/>.
         * Uruchom embedowanym Antem i zweryfikuj (Files.exists), ze OBA pliki .class
         * powstaly w katalogu build.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RunCompiledClassWithJavaTask {
        /*
         * 🧪 Zadanie 16:
         * Rozszerz build.xml z Zadania 15 o target "run" (depends="compile") uzywajacy
         * <java classname="..." classpath="build" fork="true"/> do uruchomienia jednej ze
         * skompilowanych klas. Zweryfikuj, ze jej output pojawil sie na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildExecutableJarWithManifest {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz build.xml o target "jar" (depends="compile") uzywajacy <jar
         * destfile="dist/app.jar" basedir="build"> z zagniezdzonym <manifest><attribute
         * name="Main-Class" value="..."/></manifest>. Uruchom embedowanym Antem i
         * zweryfikuj, ze plik JAR naprawde powstal (Files.exists + Files.size > 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CopyFilesetToOutputDir {
        /*
         * 🧪 Zadanie 18:
         * Wygeneruj katalog "resources" z 3 plikami tekstowymi. Wygeneruj build.xml z
         * targetem uzywajacym <copy todir="output"><fileset dir="resources"/></copy>.
         * Uruchom embedowanym Antem i zweryfikuj, ze wszystkie 3 pliki znalazly sie w
         * katalogu "output".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CleanRebuildCycle {
        /*
         * 🧪 Zadanie 19:
         * Wygeneruj build.xml z targetami "clean" (usuwa "build"), "init" (mkdir "build",
         * depends="clean") i "compile" (depends="init"). Uruchom cala sciezke do "compile"
         * DWA RAZY pod rzad (jak przy powtornym, "czystym" buildzie) i zweryfikuj, ze za
         * kazdym razem katalog "build" istnieje na koniec i jest odtworzony od zera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CustomBuildListenerCounter {
        /*
         * 🧪 Zadanie 20:
         * Zamiast (albo obok) DefaultLogger, napisz WLASNA klase implementujaca
         * org.apache.tools.ant.BuildListener, ktora zlicza, ile razy wywolano
         * targetStarted(BuildEvent). Podpisz ja pod project.addBuildListener(...), wykonaj
         * build.xml z 4 targetami w lancuchu depends i wypisz koncowa liczbe wywolan.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullPipelineCleanInitCompileRunJar {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj (jak w teorii lekcji) kompletny build.xml z targetami
         * clean -> init -> compile -> run oraz compile -> jar, dla wlasnego, innego niz w
         * lekcji, przykladu klasy Main. Wywolaj TYLKO "jar" i zweryfikuj, ze Ant sam
         * wykonal cala sciezke zaleznosci oraz ze plik JAR dziala po odpaleniu
         * "java -jar" (ProcessBuilder).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ParametrizedBuildViaProperties {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode buildAndRun(String appName, String greeting), ktora generuje
         * build.xml z property "app.name" i "app.greeting" wstrzykiwanymi jako PARAMETRY
         * metody (String.format), kompiluje i uruchamia prosta klase wypisujaca
         * "${app.name}: ${app.greeting}". Wywolaj ja dwa razy z roznymi argumentami i
         * zweryfikuj rozny output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FailingTargetStopsChain {
        /*
         * 🧪 Zadanie 23:
         * Wygeneruj build.xml z targetem "compile" kompilujacym plik .java z CELOWYM
         * bledem skladniowym, oraz targetem "run" (depends="compile"). Wywolaj
         * executeTarget("run") w bloku try/catch(BuildException) i zweryfikuj, ze target
         * "run" NIE wykonal sie (build zatrzymal sie na bledzie kompilacji w "compile").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ReusableBuildXmlTemplate {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode generateBuildXml(String mainClassName, String jarName) zwracajaca
         * String z pelnym build.xml (na wzor lekcji: clean/init/compile/run/jar). Uzyj jej
         * do wygenerowania i wykonania DWOCH roznych, niezaleznych mini-projektow (rozne
         * klasy Main, rozne nazwy JAR-ow) w dwoch osobnych katalogach tymczasowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConditionalTargetWithAvailable {
        /*
         * 🧪 Zadanie 25:
         * Wygeneruj build.xml z targetem uzywajacym <available file="..." property="fileFound"/>
         * do sprawdzenia, czy dany plik istnieje, a potem drugim targetem z
         * <echo message="Plik istnieje: ${fileFound}"/>. Przetestuj dla pliku, ktory
         * istnieje, i dla pliku, ktory nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiModuleLikeAntProject {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj mini "multi-modul" w Ancie: 2 osobne build.xml (moduleA/build.xml,
         * moduleB/build.xml), gdzie moduleB kompiluje kod zalezny od JUZ skompilowanych
         * klas moduleA (classpath wskazujacy na build/ modulu A). Wykonaj najpierw build
         * modulu A (embedowany Project #1), potem modulu B (embedowany Project #2) i
         * zweryfikuj sukces calosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LoggerCapturingBuildOutputToString {
        /*
         * 🧪 Zadanie 27:
         * Zamiast System.out, podepnij DefaultLogger do WLASNEGO PrintStream zapisujacego
         * do ByteArrayOutputStream (przechwycenie calego logu Anta jako String). Uruchom
         * build.xml z kilkoma targetami i na koniec wypisz przechwycony log jako jeden,
         * sformatowany raport z prefiksem "[ANT LOG] " przed kazda linia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DynamicTargetSelection {
        /*
         * 🧪 Zadanie 28:
         * Wygeneruj build.xml z 4 targetami (np. dev, test, staging, prod - kazdy z innym
         * <echo>). Napisz metode runEnvironmentTarget(Project project, String envName), ktora
         * wybiera i wykonuje odpowiedni target na podstawie parametru String (symulujac
         * wybor srodowiska w realnym pipeline CI/CD).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AntPropertiesImmutabilityDemo {
        /*
         * 🧪 Zadanie 29:
         * Wygeneruj build.xml, w ktorym target ustawia <property name="x" value="pierwsza"/>,
         * a POTEM (w tym samym albo kolejnym targecie) probuje ustawic
         * <property name="x" value="druga"/>. Wypisz finalna wartosc ${x} i skomentuj w
         * kodzie, ze w Ancie property sa domyslnie IMMUTABLE (pierwsze ustawienie wygrywa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_AntBasicsCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny mini-projekt "kalkulator": 2 klasy pomocnicze + Main korzystajacy
         * z obu, build.xml z pelnym lancuchem clean -> init -> compile -> jar oraz osobnym
         * targetem "run", wlasny BuildListener zliczajacy wszystkie wykonane targety, a na
         * koniec wypisz raport koncowy: liczbe wykonanych targetow, sciezke do wynikowego
         * JAR-a i to, czy "java -jar" na nim zakonczyl sie kodem 0.
         */
        public static void main(String[] args) { }
    }
}
