package com.example.javaquest._11_buildtools.Lesson29_BuildToolsTroubleshooting;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson29_BuildToolsTroubleshooting {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 29: TROUBLESHOOTING BUILD TOOLI ===");
        System.out.println("Ta lekcja REALNIE wywoluje kilka klasycznych bledow JVM/Ant, uzywajac tych");
        System.out.println("samych sztuczek (JavaCompiler + URLClassLoader) co Lekcja 02, oraz embedowanego");
        System.out.println("silnika Ant z lekcji 03-10. Kazde demo jest odizolowane w try/catch, zeby");
        System.out.println("niepowodzenie jednego nie przerwalo calej lekcji.");

        /*
         * ============================================================
         * 📦 DLACZEGO TA LEKCJA JEST INNA
         * ============================================================
         * Wiekszosc bledow build tooli opisujemy SLOWNIE (Maven/Gradle
         * nie sa embedowane w tym kursie - patrz CLAUDE.md). Ale bledy
         * czysto JVM-owe (ClassNotFoundException, NoClassDefFoundError,
         * NoSuchMethodError, UnsupportedClassVersionError) oraz bledy
         * Anta (BuildException) MOZEMY wywolac NAPRAWDE, bo mamy pod
         * reka prawdziwy kompilator (javax.tools.JavaCompiler) i
         * prawdziwy silnik Ant (org.apache.tools.ant.Project) w tym
         * samym procesie JVM. Zamiast czytac O TYCH bledach, ZOBACZYSZ
         * je na wlasne oczy - z prawdziwym stack trace'em/komunikatem.
         */

        demoClassNotFoundException();
        demoNoClassDefFoundError();
        demoNoSuchMethodError();
        demoUnsupportedClassVersionError();
        demoAntTargetNotFound();
        demoAntBadClasspath();
        printIllustrativeMavenErrors();
        printIllustrativeGradleErrors();
        printAntSpecificKnownIssues();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ClassNotFoundException - probujesz jawnie wczytac klase
         *   (Class.forName/loadClass), ktorej NIE MA na classpath.
         * - NoClassDefFoundError - klasa byla dostepna w compile-time
         *   (inna klasa sie do niej odwoluje), ale w RUNTIME jej nie ma
         *   (np. usunieto JAR, zla wersja artefaktu na produkcji).
         * - NoSuchMethodError - klasa istnieje w runtime, ale w INNEJ
         *   (niezgodnej binarnie) wersji, bez metody, ktora byla
         *   dostepna w compile-time - klasyczny "dependency hell".
         * - UnsupportedClassVersionError - .class skompilowany na
         *   nowszej Javie niz ta, na ktorej próbujemy go odpalic.
         * - BuildException w Ancie - nieistniejacy target, zly
         *   classpath (brakujacy JAR), bledy kompilacji - wszystko to
         *   sa REALNE bledy, ktore properly widzielismy wlasnie powyzej.
         * - Bledy Maven/Gradle (nie embedowane w kursie) - poznane
         *   opisowo, ale odpowiadaja analogicznym problemom co powyzej
         *   (zly classpath/dependency = brakujaca zaleznosc, incompatible
         *   Java version = UnsupportedClassVersionError "po stronie Mavena/
         *   Gradle", itd.)
         */

        System.out.println("\n=== KONIEC LEKCJI 29 ===");
    }

    // ============================================================
    // POMOCNICZE METODY (reuzywane z wzorca Lekcji 02)
    // ============================================================

    private static Path compileSource(Path workDir, String className, String sourceCode) throws IOException {
        Path srcFile = workDir.resolve(className + ".java");
        Files.writeString(srcFile, sourceCode);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, System.out, System.err, "-d", workDir.toString(), srcFile.toString());
        if (result != 0) {
            throw new IllegalStateException("Kompilacja klasy " + className + " nie powiodla sie (kod: " + result + ")");
        }
        return workDir.resolve(className + ".class");
    }

    private static void runMainViaReflection(Path classesDir, String className) throws Exception {
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classesDir.toUri().toURL()})) {
            Class<?> clazz = Class.forName(className, true, loader);
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[0]);
        }
    }

    // ============================================================
    // 🔹 BŁĄD JVM #1: ClassNotFoundException
    // ============================================================

    private static void demoClassNotFoundException() {
        System.out.println("\n=== BLAD JVM #1: ClassNotFoundException (REALNIE WYWOLANY) ===");
        try {
            Path workDir = Files.createTempDirectory("lesson21-cnfe");
            String source = "public class Znikajaca { public static void main(String[] a) { " +
                    "System.out.println(\"Dzialam!\"); } }";
            compileSource(workDir, "Znikajaca", source);

            // Krok 1: normalne wczytanie - dowod, ze klasa istnieje i dziala
            try (URLClassLoader loader1 = new URLClassLoader(new URL[]{workDir.toUri().toURL()})) {
                Class<?> clazz = Class.forName("Znikajaca", true, loader1);
                System.out.println("Krok 1 - klasa wczytana OK: " + clazz.getName());
            }

            // Krok 2: usuwamy .class z dysku - klasa fizycznie "znika"
            Files.delete(workDir.resolve("Znikajaca.class"));
            System.out.println("Krok 2 - usunieto Znikajaca.class z dysku.");

            // Krok 3: próba wczytania PONOWNIE (nowy classloader - bez cache)
            try (URLClassLoader loader2 = new URLClassLoader(new URL[]{workDir.toUri().toURL()})) {
                Class.forName("Znikajaca", true, loader2);
                System.out.println("(nieoczekiwane - klasa zaladowala sie mimo usuniecia pliku)");
            } catch (ClassNotFoundException e) {
                System.out.println("Krok 3 - ZLAPANY REALNY wyjatek: " + e.getClass().getName());
                System.out.println("Komunikat: " + e.getMessage());
                System.out.println("Wyjasnienie: jawnie prosilismy o wczytanie klasy po nazwie (Class.forName),");
                System.out.println("a jej .class NIE MA na classpath tego classloadera.");
            }
        } catch (Exception e) {
            System.out.println("Demo ClassNotFoundException nie powiodlo sie technicznie: " + e);
        }
    }

    // ============================================================
    // 🔹 BŁĄD JVM #2: NoClassDefFoundError
    // ============================================================

    private static void demoNoClassDefFoundError() {
        System.out.println("\n=== BLAD JVM #2: NoClassDefFoundError (REALNIE WYWOLANY) ===");
        try {
            Path workDir = Files.createTempDirectory("lesson21-ncdfe");
            String helperSource = "public class Pomocnik { public static String pozdrow() { " +
                    "return \"Czesc z Pomocnika!\"; } }";
            String mainSource = "public class ZObslugaPomocnika { public static void main(String[] a) { " +
                    "System.out.println(Pomocnik.pozdrow()); } }";
            compileSource(workDir, "Pomocnik", helperSource);
            compileSource(workDir, "ZObslugaPomocnika", mainSource);

            // Krok 1: uruchamiamy normalnie, z obiema klasami na miejscu
            runMainViaReflection(workDir, "ZObslugaPomocnika");
            System.out.println("Krok 1 - uruchomienie z obiema klasami OK.");

            // Krok 2: usuwamy TYLKO Pomocnik.class (klasa glowna, ZObslugaPomocnika.class, zostaje!)
            Files.delete(workDir.resolve("Pomocnik.class"));
            System.out.println("Krok 2 - usunieto Pomocnik.class (helper) - ZObslugaPomocnika.class zostaje.");

            // Krok 3: ponowne uruchomienie - klasa glowna sie wczyta, ale wywolanie
            // Pomocnik.pozdrow() zawiedzie w momencie linkowania tej referencji
            try {
                runMainViaReflection(workDir, "ZObslugaPomocnika");
                System.out.println("(nieoczekiwane - brak bledu mimo usuniecia zaleznosci)");
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                System.out.println("Krok 3 - ZLAPANY REALNY blad: " + cause.getClass().getName());
                System.out.println("Komunikat: " + cause.getMessage());
                System.out.println("Wyjasnienie: ZObslugaPomocnika.class ISTNIEJE i wczytuje sie OK, ale");
                System.out.println("gdy JVM probuje polinkowac odwolanie do klasy Pomocnik (przy wywolaniu");
                System.out.println("Pomocnik.pozdrow()), nie znajduje juz jej .class - to typowy scenariusz");
                System.out.println("'brakujacy JAR na produkcji', ktory byl obecny w compile-time.");
            }
        } catch (Exception e) {
            System.out.println("Demo NoClassDefFoundError nie powiodlo sie technicznie: " + e);
        }
    }

    // ============================================================
    // 🔹 BŁĄD JVM #3 (BONUS): NoSuchMethodError
    // ============================================================

    private static void demoNoSuchMethodError() {
        System.out.println("\n=== BLAD JVM #3 (BONUS): NoSuchMethodError (REALNIE WYWOLANY) ===");
        try {
            Path workDir = Files.createTempDirectory("lesson21-nsme");
            String serwisV1 = "public class Serwis { public static String v1() { return \"v1\"; } " +
                    "public static String metodaUsuwanaWNowejWersji() { return \"jestem tu (jeszcze)\"; } }";
            String wolajacySource = "public class Wolajacy { public static void main(String[] a) { " +
                    "System.out.println(Serwis.metodaUsuwanaWNowejWersji()); } }";
            compileSource(workDir, "Serwis", serwisV1);
            compileSource(workDir, "Wolajacy", wolajacySource);

            runMainViaReflection(workDir, "Wolajacy");
            System.out.println("Krok 1 - uruchomienie z pierwotna wersja 'Serwis' OK.");

            // "Wydanie nowej wersji" Serwis - BEZ tej metody. Wolajacy.class NIE jest
            // przekompilowany (tak jak w praktyce: biblioteka X zaktualizowana, ale
            // Twoja aplikacja skompilowana przeciw STAREJ wersji jej API).
            String serwisV2 = "public class Serwis { public static String v1() { return \"v1\"; } }";
            compileSource(workDir, "Serwis", serwisV2); // nadpisuje Serwis.class

            try {
                runMainViaReflection(workDir, "Wolajacy");
                System.out.println("(nieoczekiwane - brak bledu mimo niezgodnosci binarnej)");
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                System.out.println("Krok 2 - ZLAPANY REALNY blad: " + cause.getClass().getName());
                System.out.println("Komunikat: " + cause.getMessage());
                System.out.println("Wyjasnienie: 'Wolajacy' skompilowano przeciw STAREJ wersji 'Serwis'");
                System.out.println("(z metoda metodaUsuwanaWNowejWersji()), a w runtime podlozono NOWSZA");
                System.out.println("wersje Serwis.class, ktora tej metody juz nie ma - klasyczny przypadek");
                System.out.println("niezgodnych wersji bibliotek na classpath (np. dwa moduly Maven/Gradle");
                System.out.println("w roznych wersjach tej samej zaleznosci).");
            }
        } catch (Exception e) {
            System.out.println("Demo NoSuchMethodError nie powiodlo sie technicznie: " + e);
        }
    }

    // ============================================================
    // 🔹 BŁĄD JVM #4: UnsupportedClassVersionError (z zabezpieczeniem)
    // ============================================================

    private static void demoUnsupportedClassVersionError() {
        System.out.println("\n=== BLAD JVM #4: UnsupportedClassVersionError (REALNIE WYWOLANY, z zabezpieczeniem) ===");
        try {
            Path workDir = Files.createTempDirectory("lesson21-ucve");
            String source = "public class ZPrzyszlosci { public static void main(String[] a) { " +
                    "System.out.println(\"to nigdy nie powinno sie wykonac\"); } }";
            Path classFile = compileSource(workDir, "ZPrzyszlosci", source);

            byte[] bytes = Files.readAllBytes(classFile);
            // Format pliku .class: bajty 0-3 = magic number (CAFEBABE), bajty 4-5 =
            // minor version, bajty 6-7 = MAJOR VERSION (big-endian). Wpisujemy tam
            // absurdalnie wysoka wartosc - "wersja z przyszlosci", ktorej zaden dzisiejszy
            // JDK nie rozpozna.
            int fakeMajorVersion = 9999;
            bytes[6] = (byte) ((fakeMajorVersion >> 8) & 0xFF);
            bytes[7] = (byte) (fakeMajorVersion & 0xFF);
            Files.write(classFile, bytes);
            System.out.println("Spatchowano bajty 6-7 (major version) pliku .class na wartosc " + fakeMajorVersion + ".");

            try {
                runMainViaReflection(workDir, "ZPrzyszlosci");
                System.out.println("(nieoczekiwane - JVM zaladowala klase mimo absurdalnej wersji)");
            } catch (UnsupportedClassVersionError e) {
                System.out.println("ZLAPANY REALNY blad: " + e.getClass().getName());
                System.out.println("Komunikat: " + e.getMessage());
                System.out.println("Wyjasnienie: dokladnie TAK wyglada realny blad, gdy .class zostal");
                System.out.println("skompilowany na NOWSZEJ wersji Javy niz ta, na ktorej go odpalamy");
                System.out.println("(np. 'mvn package' na JDK 21, a wdrozenie na serwer z JRE 11).");
            } catch (InvocationTargetException e) {
                System.out.println("(nieoczekiwana sciezka - blad zlapany przy invoke, nie przy load): " + e.getCause());
            }
        } catch (Throwable t) {
            // Zabezpieczenie zgodnie z zasadami bezpieczenstwa demo w tym rozdziale:
            // jesli z jakiegos powodu (inna implementacja JVM, inna dokladna struktura
            // pliku .class w konkretnej wersji JDK) nie da sie tego odtworzyc identycznie
            // na maszynie, na ktorej dziala ten kod, NIE wywalamy calej lekcji - tlumaczymy
            // koncepcyjnie, zamiast pokazac (mniej wartosciowy) surowy stack trace awarii.
            System.out.println("Nie udalo sie odtworzyc DOKLADNIE tego bledu na tej JVM (" + t.getClass().getSimpleName() + ": " + t.getMessage() + ").");
            System.out.println("Koncepcyjnie: UnsupportedClassVersionError oznacza, ze .class zostal");
            System.out.println("skompilowany na NOWSZEJ wersji Javy niz ta, na ktorej próbujemy go uruchomic -");
            System.out.println("typowy blad przy niezgodnosci wersji JDK build vs JDK runtime/serwera.");
        }
    }

    // ============================================================
    // 🔹 BŁĄD ANT #1: nieistniejący target
    // ============================================================

    private static void demoAntTargetNotFound() {
        System.out.println("\n=== BLAD ANT #1: nieistniejacy target (REALNIE WYWOLANY, prawdziwy silnik Ant) ===");
        try {
            Path tempDir = Files.createTempDirectory("lesson21-ant-target");
            String buildXml = """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <project name="demo-target" default="compile" basedir=".">
                        <target name="compile">
                            <echo message="Kompiluje (w tym demo nic naprawde nie kompiluje)..."/>
                        </target>
                    </project>
                    """;
            Path buildFile = tempDir.resolve("build.xml");
            Files.writeString(buildFile, buildXml);

            Project project = new Project();
            project.setBaseDir(tempDir.toFile());
            project.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            project.addReference("ant.projectHelper", helper);
            helper.parse(project, buildFile.toFile());

            DefaultLogger logger = new DefaultLogger();
            logger.setOutputPrintStream(System.out);
            logger.setErrorPrintStream(System.err);
            logger.setMessageOutputLevel(Project.MSG_INFO);
            project.addBuildListener(logger);

            try {
                project.executeTarget("packageXyz"); // ten target NIE istnieje w build.xml
                System.out.println("(nieoczekiwane - brak bledu przy nieistniejacym targecie)");
            } catch (BuildException e) {
                System.out.println("ZLAPANY REALNY BuildException: " + e.getMessage());
                System.out.println("Wyjasnienie: kazdy target odwolany w kodzie/z linii komend MUSI istniec");
                System.out.println("w build.xml - literowka w nazwie targetu to jeden z najczestszych bledow Ant.");
            }
        } catch (Exception e) {
            System.out.println("Demo Ant target-not-found nie powiodlo sie technicznie: " + e);
        }
    }

    // ============================================================
    // 🔹 BŁĄD ANT #2: zły classpath (brak JAR-a -> błąd kompilacji)
    // ============================================================

    private static void demoAntBadClasspath() {
        System.out.println("\n=== BLAD ANT #2: zly classpath - brak JAR-a (REALNIE WYWOLANY) ===");
        try {
            Path tempDir = Files.createTempDirectory("lesson21-ant-classpath");
            Path srcDir = tempDir.resolve("src");
            Files.createDirectories(srcDir);

            // Kod, ktory odwoluje sie do biblioteki (Gson), ktorej NIE dodajemy do classpath Ant.
            String badSource = "import com.google.gson.Gson;\n" +
                    "public class UzywaGson {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(new Gson().toJson(\"test\"));\n" +
                    "    }\n" +
                    "}\n";
            Files.writeString(srcDir.resolve("UzywaGson.java"), badSource);

            String buildXml = """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <project name="demo-classpath" default="compile" basedir=".">
                        <target name="compile">
                            <mkdir dir="classes"/>
                            <javac srcdir="src" destdir="classes" includeantruntime="false"/>
                        </target>
                    </project>
                    """;
            Path buildFile = tempDir.resolve("build.xml");
            Files.writeString(buildFile, buildXml);

            Project project = new Project();
            project.setBaseDir(tempDir.toFile());
            project.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            project.addReference("ant.projectHelper", helper);
            helper.parse(project, buildFile.toFile());

            DefaultLogger logger = new DefaultLogger();
            logger.setOutputPrintStream(System.out);
            logger.setErrorPrintStream(System.err);
            logger.setMessageOutputLevel(Project.MSG_ERR); // tylko bledy - output kompilatora Anta bywa dlugi
            project.addBuildListener(logger);

            try {
                project.executeTarget("compile"); // <javac> zawiedzie: "package com.google.gson does not exist"
                System.out.println("(nieoczekiwane - kompilacja powinna byla zawiesc bez Gson na classpath)");
            } catch (BuildException e) {
                System.out.println("ZLAPANY REALNY BuildException: " + e.getMessage());
                System.out.println("Wyjasnienie: kod importuje com.google.gson.Gson, ale <javac> nie ma");
                System.out.println("zadeklarowanego classpath z JAR-em Gson - Ant (poprzez javac) zglasza");
                System.out.println("blad kompilacji ('package does not exist') - dokladnie tak samo, jak");
                System.out.println("Maven zglosilby 'cannot find symbol'/'package does not exist' przy");
                System.out.println("brakujacej zaleznosci w pom.xml.");
            }
        } catch (Exception e) {
            System.out.println("Demo Ant bad-classpath nie powiodlo sie technicznie: " + e);
        }
    }

    // ============================================================
    // 🔍 ILUSTRACYJNE (NIE wykonane naprawdę) błędy Maven/Gradle
    // ============================================================

    private static void printIllustrativeMavenErrors() {
        System.out.println("\n=== ILUSTRACYJNE BLEDY MAVEN (opisowe - Maven NIE jest embedowany w kursie) ===");
        System.out.println("(Ponizsze komunikaty NIE zostaly naprawde wygenerowane przez 'mvn' - to");
        System.out.println(" spisane z pamieci, typowe komunikaty, oznaczone jasno jako ilustracyjne.)");

        printErrorExample(
                "Dependency not found",
                "Could not find artifact com.example:foo:jar:1.0.0 in central (https://repo.maven.apache.org/maven2)",
                "Zla wersja/koordynaty w <dependency>, literowka w groupId/artifactId, albo biblioteka");

        printErrorExample(
                "Conflict wersji (widoczny w mvn dependency:tree)",
                "[WARNING] \n" +
                "com.example:app:jar:1.0.0\n" +
                "+- com.google.code.gson:gson:jar:2.8.0:compile\n" +
                "\\- com.example:other-lib:jar:1.0.0:compile\n" +
                "   \\- (gson:2.11.0 omitted for conflict with 2.8.0)",
                "Dwie zaleznosci ciagna rozne wersje tej samej biblioteki - Maven wybiera jedna (nearest wins)");

        printErrorExample(
                "cannot find symbol / package does not exist",
                "[ERROR] MyClass.java:[3,20] package com.google.gson does not exist",
                "Brakujaca zaleznosc w pom.xml (albo zly scope, np. 'provided' gdy potrzebny 'compile')");

        printErrorExample(
                "Unsupported class file major version",
                "Unsupported class file major version 65",
                "Projekt skompilowany na JDK 21 (major 65), odpalany na starszym JRE (np. 17, major 61)");

        printErrorExample(
                "Failed tests",
                "Tests run: 12, Failures: 1, Errors: 0, Skipped: 0\n" +
                "[ERROR] Failures: \n" +
                "[ERROR]   CalculatorTest.testDivideByZero:15 expected: <Infinity> but was: <NaN>",
                "Build zatrzymuje sie na fazie 'test' (surefire) - trzeba naprawic test lub kod");

        printErrorExample(
                "Plugin version missing",
                "[ERROR] Plugin org.apache.maven.plugins:maven-shade-plugin has an unresolvable version",
                "Plugin uzyty bez wersji, a rodzic/BOM tez jej nie zarzadza - Maven nie wie, KTORA wziac");
    }

    private static void printIllustrativeGradleErrors() {
        System.out.println("\n=== ILUSTRACYJNE BLEDY GRADLE (opisowe - Gradle NIE jest embedowany w kursie) ===");
        System.out.println("(Ponizsze komunikaty NIE zostaly naprawde wygenerowane przez './gradlew' - ten");
        System.out.println(" wrapper nie istnieje w tym repo. To ilustracyjne, typowe komunikaty.)");

        printErrorExample(
                "Wrapper nie działa",
                "gradlew: Permission denied  /  'gradlew' is not recognized as an internal or external command",
                "Brak uprawnien wykonania (chmod +x gradlew na Linux/macOS) albo odpalenie zlego skryptu (gradlew vs gradlew.bat)");

        printErrorExample(
                "Dependency conflict",
                "> Task :app:compileJava FAILED\n" +
                "Could not resolve com.example:lib:1.0.0 due to conflict with com.example:lib:2.0.0",
                "Rozne moduly wymagaja niezgodnych wersji - do rozwiazania przez dependencyInsight (Lekcja 17)");

        printErrorExample(
                "Task not found",
                "Task 'buld' not found in root project 'my-app'. Some candidates are: 'build'.",
                "Literowka w nazwie taska z linii komend (Gradle podsuwa podobne nazwy)");

        printErrorExample(
                "Incompatible Java version",
                "Could not determine the dependencies of task ':compileJava'.\n" +
                "> Failed to calculate the value: invalid source release 21 for Gradle 7.x",
                "Zainstalowana wersja Gradle nie wspiera nowszej wersji JDK, ktorej uzywa build (albo odwrotnie)");

        printErrorExample(
                "Cache problems",
                "> Task :app:compileJava\n" +
                "Execution failed for task ':app:compileJava'.\n" +
                "> Could not read workspace metadata from ~/.gradle/caches/.../metadata.bin",
                "Uszkodzony lokalny cache Gradle - zwykle naprawia './gradlew build --refresh-dependencies' albo usuniecie ~/.gradle/caches");
    }

    private static void printAntSpecificKnownIssues() {
        System.out.println("\n=== INNE ZNANE PROBLEMY ANT (opisowe, uzupelnienie realnych demo powyzej) ===");
        printErrorExample(
                "Property nie istnieje",
                "(Ant NIE zglasza bledu - nierozwiniete ${nieznana.property} zostaje jako LITERALNY tekst!)",
                "To pulapka: literowka we wlasciwosci NIE przerywa builda, tylko tworzy dziwna wartosc/plik/sciezke");

        printErrorExample(
                "Zła kolejność depends",
                "BUILD FAILED - Class not found / plik nie istnieje przy pakowaniu do JAR-a",
                "Target 'jar' odpalony bez depends='compile' (albo z bledna kolejnoscia) - kompilacja sie NIE wykonala przed pakowaniem");

        printErrorExample(
                "Różne ścieżki Windows/Linux",
                "BUILD FAILED - basedir does not exist: src\\main\\java (albo odwrotnie: src/main/java nie znaleziono)",
                "Ant NA OGOL sam normalizuje separatory / i \\, ale RECZNIE sklejane sciezki Stringami w <script> juz nie - unikaj tego");

        printErrorExample(
                "Złe encoding",
                "Znaki takie jak 'zólw' pojawiają się jako 'zÃ³lw' w skompilowanych .properties/logach",
                "Brak <javac encoding=\"UTF-8\"/> (albo niezgodny encoding pliku zrodlowego z tym, co zaklada system operacyjny)");
    }

    private static void printErrorExample(String title, String simulatedOutput, String explanation) {
        System.out.println("\n--- " + title + " ---");
        System.out.println(simulatedOutput);
        System.out.println("Przyczyna: " + explanation);
    }
}
