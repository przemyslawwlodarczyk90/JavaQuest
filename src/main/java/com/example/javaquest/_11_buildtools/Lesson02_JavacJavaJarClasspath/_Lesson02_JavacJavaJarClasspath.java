package com.example.javaquest._11_buildtools.Lesson02_JavacJavaJarClasspath;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class _Lesson02_JavacJavaJarClasspath {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 2: javac, java, JAR i classpath ===\n");

        /*
         * ============================================================
         * 📦 javac - KOMPILATOR JAVY
         * ============================================================
         * javac przeksztalca pliki .java (kod zrodlowy, czytelny dla
         * czlowieka) w pliki .class (bajtkod, czytelny dla JVM). Kazda
         * klasa publiczna trafia do WLASNEGO pliku .class o tej samej
         * nazwie. Najwazniejsza opcja: -d <katalog> - mowi javac, GDZIE
         * odlozyc wygenerowane .class (domyslnie: tam gdzie plik .java).
         * javac umie kompilowac jeden plik, wiele plikow na raz, a nawet
         * CALE drzewo katalogow (przez -sourcepath albo podajac wiele
         * plikow jako argumenty).
         *
         * W tej lekcji, ZAMIAST wywolywac javac jako zewnetrzny proces
         * (jak w Lekcji 1 - ProcessBuilder), uzyjemy PROGRAMOWEGO API
         * kompilatora: javax.tools.JavaCompiler. To DOKLADNIE ten sam
         * kompilator co polecenie "javac" w terminalu, tylko wywolywany
         * z wnetrza kodu Javy - bez startowania nowego procesu systemowego.
         */

        Path workDir = Files.createTempDirectory("lesson02-javac-java-jar");
        Path srcDir = workDir.resolve("src");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        // KROK 1: generujemy plik zrodlowy Greeter.java - klasa z metoda
        // main, ktora bedziemy kompilowac i uruchamiac programowo.
        Path greeterSrc = srcDir.resolve("Greeter.java");
        Files.writeString(greeterSrc, """
                public class Greeter {
                    public static void main(String[] args) {
                        System.out.println("Witaj z klasy skompilowanej w locie przez javax.tools.JavaCompiler!");
                    }
                }
                """);

        // KROK 2: javax.tools.ToolProvider.getSystemJavaCompiler() zwraca
        // instancje "systemowego" kompilatora - tego samego, ktory stoi
        // za komenda "javac". Zwraca null, jesli program dziala na JRE
        // bez kompilatora (np. bardzo okrojone srodowisko) - w normalnym
        // JDK zawsze dostaniemy dzialajacy obiekt.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - upewnij sie, ze uzywasz JDK, nie tylko JRE.");
        }

        // KROK 3: compiler.run(in, out, err, arguments...) dziala jak
        // wywolanie javac z linii komend - kazdy String w arguments to
        // jeden argument (dokladnie tak, jak by trafil po "javac ").
        // Zwraca kod wyjscia: 0 = sukces, != 0 = blad kompilacji.
        int compileResult = compiler.run(null, System.out, System.err,
                "-d", classesDir.toString(),
                greeterSrc.toString());
        System.out.println("Wynik kompilacji Greeter.java (0 = sukces): " + compileResult + "\n");

        /*
         * ============================================================
         * 🔹 java - URUCHAMIANIE SKOMPILOWANEJ KLASY
         * ============================================================
         * Polecenie "java NazwaKlasy" szuka pliku NazwaKlasy.class na
         * classpath, wczytuje go do JVM i wywoluje jego metode:
         *
         *     public static void main(String[] args)
         *
         * Jesli klasa jest w pakiecie (np. com.example.Foo), trzeba
         * podac PELNA nazwe klasy (z pakietem), a katalog na classpath
         * musi byc katalogiem NADRZEDNYM wobec struktury pakietow
         * (np. dla com/example/Foo.class classpath to katalog zawierajacy
         * podkatalog "com").
         *
         * Ponizej uruchomimy skompilowana klase Greeter PROGRAMOWO -
         * bez nowego procesu systemowego - przy pomocy URLClassLoader,
         * ktory umie wczytac klasy z podanego katalogu/JAR-a tak, jakby
         * byly na classpath tego JVM.
         */

        // KROK 4: URLClassLoader potrzebuje URL-a do katalogu z klasami
        // (koniecznie z koncowym "/" dla katalogow - stad toUri().toURL(),
        // ktore dodaje go automatycznie).
        URL[] classpathUrls = { classesDir.toUri().toURL() };
        try (URLClassLoader classLoader = new URLClassLoader(classpathUrls, _Lesson02_JavacJavaJarClasspath.class.getClassLoader())) {

            // KROK 5: Class.forName wczytuje klase po nazwie przy pomocy
            // podanego class loadera - to programowy odpowiednik tego, co
            // "java Greeter" robi za nas automatycznie w terminalu.
            Class<?> greeterClass = Class.forName("Greeter", true, classLoader);

            // KROK 6: refleksyjnie pobieramy metode "main(String[])" i
            // wywolujemy ja - drugi argument invoke to TABLICA argumentow
            // dla metody main, ale musimy ja zawinac w Object[], bo w
            // przeciwnym razie Java rozpakowalaby String[] jako VARARGI
            // invoke(...) - stad "(Object) new String[0]".
            greeterClass.getMethod("main", String[].class)
                    .invoke(null, (Object) new String[0]);
        }

        /*
         * ============================================================
         * 🔍 CLASSPATH - GDZIE JVM SZUKA KLAS
         * ============================================================
         * Classpath to lista miejsc (katalogow i/lub plikow JAR), w
         * ktorych JVM szuka klas potrzebnych do dzialania programu.
         * Mozna go podac:
         *  - flaga -cp / -classpath przy "javac" i "java",
         *  - zmienna srodowiskowa CLASSPATH (rzadko uzywana, latwo o
         *    konflikt miedzy projektami),
         *  - atrybutem Class-Path w manifescie JAR-a.
         *
         * SEPARATOR miedzy wpisami classpath jest ZALEZNY OD SYSTEMU:
         *  - Windows: ";" (srednik)      np. "classes;lib\\gson.jar"
         *  - Linux/macOS: ":" (dwuklodek) np. "classes:lib/gson.jar"
         * W Javie ten separator jest dostepny programowo jako
         * File.pathSeparator - NIGDY nie warto go wpisywac na sztywno,
         * jesli kod ma dzialac na wielu systemach.
         *
         * Gdy JVM NIE znajdzie potrzebnej klasy na classpath, zobaczymy
         * jeden z dwoch wyjatkow:
         *  - ClassNotFoundException - gdy kod PROGRAMOWO prosi o klase po
         *    nazwie (Class.forName, tak jak wyzej) i jej nie znajduje.
         *  - NoClassDefFoundError - gdy klasa byla dostepna PRZY KOMPILACJI,
         *    ale w RUNTIME zniknela z classpath (np. usunieto JAR).
         *
         * Ponizej wywolamy OBA te bledy NAPRAWDE - nie tylko o nich
         * mowimy.
         */

        System.out.println("\n--- Demo: ClassNotFoundException ---");
        // KROK 7: proba wczytania klasy, ktora NIGDY nie istniala w tym
        // katalogu klas - realny ClassNotFoundException.
        try (URLClassLoader emptyLoader = new URLClassLoader(classpathUrls, null)) {
            Class.forName("TaKlasaNigdyNieIstniala", true, emptyLoader);
        } catch (ClassNotFoundException e) {
            System.out.println("Zlapano prawdziwy ClassNotFoundException: " + e.getMessage());
        }

        System.out.println("\n--- Demo: NoClassDefFoundError ---");
        // KROK 8: kompilujemy DWIE powiazane klasy (Worker uzywa Helper),
        // a potem USUWAMY plik Helper.class z katalogu klas PRZED
        // uruchomieniem - klasa byla dostepna przy kompilacji, ale
        // zniknela w runtime. To wywoluje NoClassDefFoundError.
        Path workerSrc = srcDir.resolve("Worker.java");
        Path helperSrc = srcDir.resolve("Helper.java");
        Files.writeString(helperSrc, """
                public class Helper {
                    public static String help() {
                        return "pomoc od Helper";
                    }
                }
                """);
        Files.writeString(workerSrc, """
                public class Worker {
                    public static void main(String[] args) {
                        System.out.println(Helper.help());
                    }
                }
                """);
        compiler.run(null, System.out, System.err, "-d", classesDir.toString(),
                helperSrc.toString(), workerSrc.toString());

        Path helperClassFile = classesDir.resolve("Helper.class");
        Files.deleteIfExists(helperClassFile);
        System.out.println("Usunieto Helper.class z katalogu klas (Worker.class wciaz go 'wymaga').");

        try (URLClassLoader loaderWithoutHelper = new URLClassLoader(classpathUrls, null)) {
            Class<?> workerClass = Class.forName("Worker", true, loaderWithoutHelper);
            workerClass.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
        } catch (Exception e) {
            // invoke() zawija bledy uruchomieniowe klasy docelowej w
            // InvocationTargetException - prawdziwy NoClassDefFoundError
            // znajdziemy jako jego "cause".
            Throwable realCause = e.getCause() != null ? e.getCause() : e;
            System.out.println("Zlapano: " + realCause.getClass().getSimpleName() + " - " + realCause.getMessage());
        }

        /*
         * ============================================================
         * 🔹 JAR - JAVA ARCHIVE
         * ============================================================
         * JAR to zwykly plik ZIP ze specjalna struktura: zawiera
         * skompilowane klasy .class (z zachowana struktura pakietow),
         * opcjonalnie zasoby, oraz katalog META-INF/ z plikiem
         * MANIFEST.MF opisujacym metadane archiwum.
         *
         * Manifest moze zawierac atrybut Main-Class - wskazuje, ktora
         * klasa ma zostac uruchomiona, gdy JAR wywolamy jako
         * "java -jar plik.jar" (bez podawania nazwy klasy recznie).
         * JAR z takim atrybutem to EXECUTABLE JAR (wykonywalny JAR).
         *
         * Zbudujemy teraz REALNY, wykonywalny JAR programowo przy pomocy
         * java.util.jar.JarOutputStream, a potem odpalimy go PRAWDZIWA
         * komenda "java -jar" przez ProcessBuilder.
         */

        System.out.println("\n--- Budowanie wykonywalnego JAR-a ---");

        // KROK 9: recompilujemy Greeter.java do CZYSTEGO katalogu -
        // chcemy w JAR-ze tylko jedna, konkretna klase.
        Path jarClassesDir = workDir.resolve("jar-classes");
        Files.createDirectories(jarClassesDir);
        compiler.run(null, System.out, System.err, "-d", jarClassesDir.toString(), greeterSrc.toString());

        // KROK 10: Manifest to mapa atrybutow - Manifest-Version jest
        // wymagany zawsze, Main-Class wlacza tryb "executable jar".
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Greeter");

        Path jarPath = workDir.resolve("greeter.jar");
        try (JarOutputStream jarOut = new JarOutputStream(Files.newOutputStream(jarPath), manifest)) {
            // KROK 11: kazdy plik .class trafia do archiwum jako osobny
            // JarEntry o nazwie odpowiadajacej sciezce wewnatrz JAR-a
            // (dla klas w pakietach to np. "com/example/Foo.class").
            Path classFile = jarClassesDir.resolve("Greeter.class");
            jarOut.putNextEntry(new JarEntry("Greeter.class"));
            jarOut.write(Files.readAllBytes(classFile));
            jarOut.closeEntry();
        }
        System.out.println("Zbudowano wykonywalny JAR: " + jarPath + " (rozmiar: " + Files.size(jarPath) + " B)");

        // KROK 12: uruchamiamy PRAWDZIWA komenda "java -jar" jako osobny
        // proces systemowy - to jest to, co uzytkownik koncowy wpisalby
        // w terminalu, majac tylko plik greeter.jar (bez zrodel!).
        String javaExe = System.getProperty("java.home") + java.io.File.separator + "bin" + java.io.File.separator + "java";
        ProcessBuilder jarProcess = new ProcessBuilder(javaExe, "-jar", jarPath.toString());
        jarProcess.inheritIO();
        Process process = jarProcess.start();
        int exitCode = process.waitFor();
        System.out.println("Proces 'java -jar greeter.jar' zakonczony z kodem: " + exitCode);

        /*
         * ============================================================
         * 🔍 WAR - WEB APPLICATION ARCHIVE (krotko)
         * ============================================================
         * WAR to "kuzyn" JAR-a przeznaczony dla aplikacji webowych
         * uruchamianych w kontenerze servletow (np. Tomcat). Struktura:
         *
         *   moja-app.war
         *   +-- WEB-INF/
         *       +-- web.xml           (deskryptor - mapowania URL -> servlet)
         *       +-- classes/          (skompilowane klasy aplikacji, bez JAR-a)
         *       +-- lib/               (biblioteki zewnetrzne jako pliki .jar)
         *
         * Deployment WAR-a polega na skopiowaniu tego pliku do katalogu
         * "webapps" kontenera (np. Tomcata) - kontener sam go rozpakuje
         * i wystawi pod adresem URL. Ten kurs poglebia mechanike
         * servletow/WAR-ow w calym rozdziale _07_servlets (tam uzywamy
         * embedded Tomcata programowo) - tutaj WAR wspominamy tylko
         * jako KONTEKST: to kolejny format archiwum na bazie tej samej
         * idei co JAR, tylko z inna, wymuszona struktura katalogow.
         */

        System.out.println("\n=== KONIEC LEKCJI 2 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - javac (tu: javax.tools.JavaCompiler) kompiluje .java -> .class,
         *   opcja -d wskazuje katalog wyjsciowy.
         * - java (tu: URLClassLoader + Class.forName + refleksja) wczytuje
         *   i uruchamia sklompilowane klasy; realna komenda "java -jar"
         *   uruchamia wykonywalny JAR ze wskazanym Main-Class.
         * - Classpath to lista miejsc, gdzie JVM szuka klas - separator
         *   zalezny od systemu (";" Windows, ":" Linux/macOS,
         *   File.pathSeparator w kodzie).
         * - Brak klasy na classpath -> ClassNotFoundException (szukanie
         *   programowe po nazwie) albo NoClassDefFoundError (klasa byla
         *   przy kompilacji, zniknela w runtime) - OBA wywolane realnie
         *   powyzej.
         * - JAR = ZIP + META-INF/MANIFEST.MF; atrybut Main-Class robi z
         *   niego wykonywalny JAR odpalany "java -jar".
         * - WAR rozwija te sama idee dla aplikacji webowych (WEB-INF/
         *   classes, WEB-INF/lib, web.xml) - szczegoly w rozdziale
         *   _07_servlets.
         * - Wzorce z tej lekcji (kompilacja w locie, URLClassLoader,
         *   budowanie JAR-a, wywolywanie ClassNotFoundException/
         *   NoClassDefFoundError) wroca w dalszych lekcjach tego
         *   rozdzialu (m.in. w lekcji o troubleshootingu).
         */
    }
}
