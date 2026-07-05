package com.example.javaquest._11_buildtools.Lesson02_JavacJavaJarClasspath;

public class _Exercises_Lesson02_JavacJavaJarClasspath {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CompileSingleClassInMemory {
        /*
         * 🧪 Zadanie 1:
         * Wygeneruj (Files.writeString) plik "Hello.java" z klasa wypisujaca "Hello!" w
         * main(). Skompiluj go przy pomocy ToolProvider.getSystemJavaCompiler().run(...) do
         * katalogu tymczasowego i wypisz kod wyjscia kompilacji (0 = sukces).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunCompiledClassWithClassLoader {
        /*
         * 🧪 Zadanie 2:
         * Skompiluj klase z Zadania 1, a nastepnie uruchom ja przy pomocy URLClassLoader +
         * Class.forName("Hello", true, loader) + refleksyjne wywolanie metody main.
         * Zweryfikuj, ze jej output pojawil sie na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PrintJavaHomeAndVersion {
        /*
         * 🧪 Zadanie 3:
         * Wypisz System.getProperty("java.home") oraz System.getProperty("java.version"),
         * a potem zbuduj z java.home pelna sciezke do pliku wykonywalnego "javac" (uzywajac
         * File.separator) i wypisz ja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClasspathSeparatorDemo {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj String reprezentujacy classpath z 3 elementow ("classes", "lib/a.jar",
         * "lib/b.jar") POPRAWNIE rozdzielonych File.pathSeparator (nie na sztywno wpisanym
         * znakiem). Wypisz wynikowy String oraz nazwe systemu operacyjnego
         * (System.getProperty("os.name")).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompilationErrorCapture {
        /*
         * 🧪 Zadanie 5:
         * Wygeneruj plik .java z CELOWYM bledem skladniowym (np. brakujacy srednik).
         * Skompiluj go compiler.run(...) i przechwyc/wypisz kod wyjscia (rozny od 0) oraz
         * komunikat bledu wypisany przez kompilator na strumien err.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BuildTrivialJar {
        /*
         * 🧪 Zadanie 6:
         * Skompiluj prosta klase "Ping" (main wypisuje "pong"). Zbuduj z niej plik JAR
         * (JarOutputStream + Manifest z Main-Class="Ping") i wypisz jego rozmiar w bajtach
         * (Files.size).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunJarViaProcessBuilder {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj JAR jak w Zadaniu 6, a potem uruchom go realna komenda "java -jar
         * <sciezka>.jar" przez ProcessBuilder (inheritIO), poczekaj na zakonczenie
         * (waitFor) i wypisz kod wyjscia procesu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ManifestAttributesList {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj obiekt Manifest z 3 atrybutami (Manifest-Version, Main-Class,
         * Implementation-Title) i wypisz wszystkie atrybuty glownej sekcji manifestu w
         * petli (manifest.getMainAttributes().entrySet()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TwoIndependentClassesCompiledTogether {
        /*
         * 🧪 Zadanie 9:
         * Wygeneruj 2 NIEZALEZNE od siebie klasy (kazda z osobna metoda main). Skompiluj
         * OBIE w JEDNYM wywolaniu compiler.run(...) (podajac oba pliki jako argumenty) i
         * wypisz, ze oba pliki .class powstaly w katalogu wyjsciowym (Files.exists).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListGeneratedClassFiles {
        /*
         * 🧪 Zadanie 10:
         * Skompiluj 3 rozne klasy do jednego katalogu wyjsciowego, a potem przy pomocy
         * Files.list(katalog) wypisz nazwy WSZYSTKICH plikow .class, ktore faktycznie
         * powstaly.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DependentClassesCorrectOrder {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj klasy "Calculator" i "CalculatorApp" (App uzywa Calculator). Skompiluj
         * OBIE w jednym wywolaniu compiler.run (kompilator sam rozwiazuje zaleznosci miedzy
         * plikami podanymi jednoczesnie) i uruchom CalculatorApp przez URLClassLoader.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ClassNotFoundExceptionDemo {
        /*
         * 🧪 Zadanie 12:
         * Stworz URLClassLoader wskazujacy na PUSTY katalog (bez zadnych .class). Sprobuj
         * Class.forName("Cokolwiek", true, loader) i zlap prawdziwy ClassNotFoundException,
         * wypisujac jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NoClassDefFoundErrorDemo {
        /*
         * 🧪 Zadanie 13:
         * Skompiluj klasy "Engine" i "Car" (Car uzywa Engine). USUN Engine.class z katalogu
         * wyjsciowego (Files.delete), a potem sprobuj uruchomic Car przez URLClassLoader -
         * zlap wyjatek z invoke(), wyciagnij realna przyczyne (getCause()) i sprawdz, ze to
         * NoClassDefFoundError.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_JarWithMultipleClasses {
        /*
         * 🧪 Zadanie 14:
         * Skompiluj 2 powiazane klasy (np. "Util" i "MainApp" uzywajacy Util) i spakuj OBA
         * pliki .class do jednego JAR-a (Main-Class=MainApp). Uruchom JAR przez "java -jar"
         * (ProcessBuilder) i zweryfikuj poprawny kod wyjscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_JarWithoutMainClassFails {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj JAR BEZ atrybutu Main-Class w manifescie (tylko Manifest-Version). Sprobuj
         * odpalic go "java -jar" przez ProcessBuilder i przechwyc/wypisz komunikat bledu,
         * ktory Java zwraca w takim przypadku ("no main manifest attribute").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ClassInPackageCompiledAndRun {
        /*
         * 🧪 Zadanie 16:
         * Wygeneruj klase z deklaracja "package demo;" (np. demo.Runner) w odpowiedniej
         * strukturze katalogow (src/demo/Runner.java). Skompiluj ja z -d, a potem uruchom
         * przez URLClassLoader uzywajac PELNEJ nazwy klasy "demo.Runner".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ClasspathWithTwoDirectories {
        /*
         * 🧪 Zadanie 17:
         * Skompiluj klase "Base" do katalogu A i klase "Derived" (uzywajaca Base) do
         * katalogu B, ustawiajac przy kompilacji Derived opcje "-cp" wskazujaca na katalog
         * A. Uruchom Derived przez URLClassLoader z URL-ami wskazujacymi na OBA katalogi
         * (A i B) naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompilerDiagnosticsCollection {
        /*
         * 🧪 Zadanie 18:
         * Uzyj JavaCompiler.getTask(...) z DiagnosticCollector<JavaFileObject> (zamiast
         * prostego compiler.run(...)) do skompilowania pliku z bledem. Po nieudanej
         * kompilacji wypisz liste wszystkich zebranych diagnostyk (linia + komunikat)
         * z DiagnosticCollector.getDiagnostics().
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RebuildJarAfterCodeChange {
        /*
         * 🧪 Zadanie 19:
         * Skompiluj i spakuj do JAR-a klase wypisujaca "wersja 1". Uruchom JAR i zapisz
         * jego output. Zmien plik zrodlowy na "wersja 2", zrekompiluj, zbuduj JAR PONOWNIE
         * (nadpisujac stary plik) i ponownie uruchom - zweryfikuj, ze output sie zmienil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_UnsupportedClassVersionDemo {
        /*
         * 🧪 Zadanie 20:
         * Skompiluj prosta klase z opcja "--release" ustawiona na WYZSZA wersje Javy niz ta,
         * na ktorej dziala JVM demo (jesli to niemozliwe/nie zadziala w tym srodowisku,
         * zamiast tego: wypisz w komentarzu wyjasnienie, kiedy w praktyce powstaje realny
         * blad UnsupportedClassVersionError - klasa skompilowana na nowszym JDK, uruchomiona
         * na starszym).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericCompileAndRunHelper {
        /*
         * 🧪 Zadanie 21:
         * Napisz generyczna metode compileAndRun(String className, String sourceCode,
         * Path workDir), ktora zapisuje podany kod do pliku <className>.java, kompiluje go
         * i uruchamia przez URLClassLoader. Przetestuj ja na 3 roznych, niezaleznych
         * klasach podanych jako String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiJarClasspathResolution {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 2 osobne JAR-y: jeden z klasa "LibA" (metoda statyczna), drugi z klasa
         * "LibB" (metoda statyczna). Skompiluj trzecia klase "AppUsingBoth" uzywajaca OBU,
         * podajac przy kompilacji "-cp" ze sciezka do obu JAR-ow (polaczona
         * File.pathSeparator), a potem uruchom AppUsingBoth przez URLClassLoader
         * wskazujacy na oba JAR-y.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RemoveOneJarCausesFailure {
        /*
         * 🧪 Zadanie 23:
         * Powtorz sytuacje z Zadania 22, ale przy URUCHAMIANIU podaj URLClassLoader
         * wskazujacy TYLKO na jeden z dwoch JAR-ow (usuwajac drugi z classpath). Zlap i
         * wypisz realny NoClassDefFoundError/ClassNotFoundException, ktory z tego wynika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_JarWithResourceFile {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj JAR zawierajacy nie tylko plik .class, ale rowniez plik zasobu (np.
         * "info.txt" z tekstem) dodany jako kolejny JarEntry. Skompiluj i uruchom klase,
         * ktora wewnatrz swojego main() wczytuje ten zasob przez
         * getClass().getResourceAsStream("/info.txt") i wypisuje jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompileTestAndMainSeparateClasspaths {
        /*
         * 🧪 Zadanie 25:
         * Skompiluj klase produkcyjna "Service" do katalogu "classes/main" oraz klase
         * "ServiceTest" (uzywajaca Service, symulujaca test) do OSOBNEGO katalogu
         * "classes/test", kompilujac ServiceTest z "-cp" wskazujacym na "classes/main".
         * Uruchom ServiceTest przez URLClassLoader z URL-ami do OBU katalogow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ManifestClassPathAttribute {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj 2 pliki: JAR biblioteki "lib.jar" (z klasa LibHelper) i JAR aplikacji
         * "app.jar" (z klasa App uzywajaca LibHelper), gdzie manifest app.jar zawiera
         * atrybut Class-Path: "lib.jar" (wzgledna sciezka do drugiego JAR-a, w tym samym
         * katalogu). Uruchom "java -jar app.jar" przez ProcessBuilder i zweryfikuj, ze
         * dziala BEZ podawania -cp recznie (dzieki atrybutowi w manifescie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DynamicClassReloadSimulation {
        /*
         * 🧪 Zadanie 27:
         * Skompiluj klase "Config" z metoda zwracajaca String "v1". Wczytaj ja NOWYM
         * URLClassLoader i wypisz wynik. Zmien plik zrodlowy tak, aby metoda zwracala "v2",
         * zrekompiluj do TEGO SAMEGO katalogu i wczytaj DRUGIM, NOWYM URLClassLoader (nie
         * tym samym!) - wypisz, ze druga wersja zwraca "v2", tlumaczac w komentarzu, czemu
         * trzeba NOWEGO class loadera (klasy sa cachowane per loader).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildFatJarManually {
        /*
         * 🧪 Zadanie 28:
         * Skompiluj 2 niezalezne klasy pomocnicze i 1 klase Main uzywajaca obu. Zbuduj
         * JEDEN JAR ("fat jar") zawierajacy WSZYSTKIE 3 pliki .class plus manifest z
         * Main-Class=Main. Uruchom go "java -jar" i zweryfikuj sukces - to demonstruje ide
         * "fat/uber jar" spotykana pozniej w Mavenie/Gradle (shade/shadow plugin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompileWithWarningsAsErrors {
        /*
         * 🧪 Zadanie 29:
         * Skompiluj kod korzystajacy z API oznaczonego jako @Deprecated (np. wlasna klasa z
         * przestarzala metoda) najpierw NORMALNIE (ostrzezenie, kompilacja przechodzi), a
         * potem z opcja "-Xlint:deprecation" i "-Werror" (ostrzezenia jako bledy) - wypisz
         * rozne kody wyjscia obu kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny mini-pipeline: wygeneruj 4 pliki .java (3 klasy pomocnicze o
         * zaleznosciach lancuchowych + 1 Main uzywajacy wszystkich), skompiluj je jednym
         * wywolaniem compiler.run, zbuduj z wszystkich .class jeden wykonywalny JAR,
         * uruchom go realna komenda "java -jar" (ProcessBuilder) i na koniec ROZMYSLNIE
         * usun jeden z plikow .class ze skompilowanego katalogu (NIE z JAR-a) demonstrujac,
         * ze sam JAR pozostaje samodzielny/dziala niezaleznie od katalogu zrodlowego klas.
         */
        public static void main(String[] args) { }
    }
}
