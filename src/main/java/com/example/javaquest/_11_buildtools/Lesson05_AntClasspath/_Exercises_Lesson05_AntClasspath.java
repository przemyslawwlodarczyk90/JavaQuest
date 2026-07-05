package com.example.javaquest._11_buildtools.Lesson05_AntClasspath;

public class _Exercises_Lesson05_AntClasspath {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimplePathDefinition {
        /*
         * 🧪 Zadanie 1:
         * Wygeneruj build.xml z <path id="my.classpath"><pathelement location="lib"/></path>
         * i targetem uzywajacym <echo> do wypisania statycznego komunikatu potwierdzajacego,
         * ze build.xml sie sparsowal (configureProject bez bledow). Zweryfikuj brak wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ClasspathElementsList {
        /*
         * 🧪 Zadanie 2:
         * Bez uzywania Anta: wypisz na konsoli 3 sposoby definiowania elementow classpath w
         * Ancie (<pathelement location>, <fileset dir includes>, refid do innego <path>) z
         * jednozdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_LibDirWithOneRealJar {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj (jak w teorii, wzorzec z Lekcji 2) prosty, prawdziwy JAR z jedna klasa
         * pomocnicza i zapisz go w katalogu lib/. Wygeneruj build.xml z
         * <path id="cp"><fileset dir="lib" includes="*.jar"/></path> i targetem <echo>
         * potwierdzajacym poprawne skonfigurowanie projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompileWithClasspathref {
        /*
         * 🧪 Zadanie 4:
         * Skompiluj klase korzystajaca z biblioteki z Zadania 3 uzywajac
         * <javac classpathref="cp">. Zweryfikuj (Files.exists), ze plik .class powstal bez
         * bledow kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RunWithNestedClasspath {
        /*
         * 🧪 Zadanie 5:
         * Uruchom skompilowana klase z Zadania 4 uzywajac <java classname="..."> z
         * zagniezdzonym <classpath><path refid="cp"/><pathelement location="build"/></classpath>.
         * Zweryfikuj poprawny output programu na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WindowsVsLinuxSeparatorExplanation {
        /*
         * 🧪 Zadanie 6:
         * Bez uzywania Anta: wypisz wyjasnienie, czemu w Ancie (w przeciwienstwie do
         * recznego wpisywania classpath w terminalu) NIE trzeba martwic sie o separator
         * ";" vs ":" - <path>/<pathelement>/<fileset> sa niezalezne od systemu operacyjnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MissingJarCompileFailure {
        /*
         * 🧪 Zadanie 7:
         * Wygeneruj klase App.java korzystajaca z nieistniejacej klasy "Helper" (bez
         * budowania zadnego JAR-a z ta klasa). Sprobuj skompilowac ja z <javac classpathref="cp">
         * gdzie "cp" wskazuje na PUSTY katalog lib/. Zlap BuildException i wypisz jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoJarsInLibFileset {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj DWA rozne, male JAR-y (z dwoma roznymi klasami pomocniczymi) i zapisz obie
         * w lib/. Wygeneruj build.xml z <fileset dir="lib" includes="*.jar"/> i skompiluj
         * klase korzystajaca z OBU bibliotek jednoczesnie, zweryfikuj sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PathIdAndRefidReuse {
        /*
         * 🧪 Zadanie 9:
         * Wygeneruj build.xml z JEDNYM <path id="shared.classpath">, ktory jest uzyty
         * (refid) w DWOCH roznych targetach ("compile" i "run"). Zweryfikuj, ze oba targety
         * dzialaja poprawnie korzystajac z tej samej definicji classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ClasspathTaskErrorTypesList {
        /*
         * 🧪 Zadanie 10:
         * Bez uzywania Anta: wypisz 3 typowe bledy zwiazane z classpath wymienione w lekcji
         * (ClassNotFoundException, NoSuchMethodError, NoClassDefFoundError) razem z krotkim
         * opisem, KIEDY (na jakim etapie) typowo sie pojawiaja.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RemoveJarAfterSuccessfulBuild {
        /*
         * 🧪 Zadanie 11:
         * Odtworz caly wzorzec z teorii lekcji: dzialajacy build z JAR-em w lib/, a potem
         * usun ten JAR i wykonaj CLEAN rekompilacje (nowy Project, wyczyszczony katalog
         * build/) - zweryfikuj, ze druga kompilacja pada z bledem "cannot find symbol".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RuntimeOnlyMissingJar {
        /*
         * 🧪 Zadanie 12:
         * Skompiluj klase z JAR-em OBECNYM w lib/ (kompilacja sie powiedzie i zapamietaj
         * skompilowany build/), a POTEM usun JAR z lib/ i sprobuj TYLKO uruchomic (<java>,
         * bez ponownej kompilacji) - zweryfikuj realny NoClassDefFoundError w runtime
         * (klasa byla znana przy kompilacji, zniknela w runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DuplicateClassesInTwoJars {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj DWA JAR-y zawierajace klasy o TEJ SAMEJ nazwie (np. obie "Version" z
         * inna implementacja metody), zapisz obie w lib/. Skompiluj i uruchom kod
         * korzystajacy z klasy "Version" - wypisz, KTORA implementacja "wygrala" (na
         * podstawie kolejnosci plikow w fileset) i skomentuj niedeterministycznosc tego
         * zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WrongLibraryVersionNoSuchMethodError {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj DWIE wersje tej samej "biblioteki" - v1 (z metoda foo()) i v2 (bez metody
         * foo(), ale z inna metoda). Skompiluj klase App korzystajaca z foo() z v1 na
         * classpath, a POTEM zamien plik JAR w lib/ na v2 (bez rekompilacji App) i uruchom -
         * zweryfikuj realny NoSuchMethodError w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExcludePatternInFileset {
        /*
         * 🧪 Zadanie 15:
         * Zapisz w lib/ 3 pliki .jar, z ktorych jeden ma w nazwie "-sources" (np.
         * "utils-1.0-sources.jar" - symulujac JAR z samymi zrodlami, ktorego nie chcemy na
         * classpath). Uzyj <fileset dir="lib" includes="*.jar" excludes="*-sources.jar"/> i
         * zweryfikuj (przez <pathconvert> + <echo> albo osobny <echo refid="cp">), ze
         * wykluczony plik NIE trafil na classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PathconvertToInspectClasspath {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj <path id="cp"> z 2 elementami. Uzyj tasku <pathconvert property="cp.str"
         * refid="cp"/>, a potem <echo message="${cp.str}"/> do wypisania FAKTYCZNEJ,
         * rozwiazanej postaci classpath (z separatorem systemowym) - zweryfikuj, ze
         * zawiera oba elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SeparateCompileAndRuntimeClasspaths {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj DWA rozne <path>: "compile.classpath" (z biblioteka A) i
         * "runtime.classpath" (z biblioteka A ORAZ dodatkowa biblioteka B, potrzebna tylko w
         * runtime). Skompiluj klase uzywajac compile.classpath i uruchom uzywajac
         * runtime.classpath - zweryfikuj, ze obie fazy dzialaja poprawnie mimo roznych
         * classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_JarWithTransitiveDependencyMissing {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj klase "ServiceA" uzywajaca klasy "ServiceB" (obie w JEDNYM JAR-ze w lib/),
         * gdzie ServiceB w runtime probuje uzyc trzeciej, NIEISTNIEJACEJ klasy "ServiceC"
         * (ktorej nigdy nie skompilowano/nie dodano do lib/). Skompiluj i uruchom kod
         * wywolujacy ServiceA - zweryfikuj, ze blad (NoClassDefFoundError) pojawia sie
         * DOPIERO w momencie faktycznego wywolania metody uzywajacej ServiceC, nie wczesniej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ClasspathOrderMattersDemo {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj 2 JAR-y z ta sama klasa "Config" (rozna zawartosc metody). Zdefiniuj DWA
         * <path> z tymi samymi plikami, ale w ODWROTNEJ kolejnosci
         * (<pathelement location="a.jar"/><pathelement location="b.jar"/> vs odwrotnie).
         * Uruchom kod korzystajacy z Config dla obu wariantow i zweryfikuj, ze wynik ZALEZY
         * od kolejnosci elementow na classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FriendlyClasspathErrorReport {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode runWithFriendlyClasspathErrors(Project project, String target), ktora
         * wywoluje executeTarget w try/catch(BuildException), a w catch analizuje komunikat
         * bledu (contains "cannot find symbol" / "ClassNotFoundException" / itp.) i wypisuje
         * PRZYJAZNY komunikat po polsku sugerujacy prawdopodobna przyczyne (brakujacy JAR w
         * lib/, zla wersja). Przetestuj na scenariuszu z brakujacym JAR-em.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ClasspathDiagnosticTool {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode diagnoseClasspath(Path libDir), ktora skanuje katalog lib/ (Files.list),
         * dla kazdego znalezionego JAR-a wypisuje jego nazwe i rozmiar, a na koniec ostrzega
         * (println), jesli w katalogu wystepuja 2 pliki o bardzo podobnych nazwach (np.
         * rozne tylko numerem wersji) - potencjalny konflikt wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildFailsThenSelfHeals {
        /*
         * 🧪 Zadanie 22:
         * Wykonaj build, ktory PADA z powodu brakujacego JAR-a w lib/ (zlap BuildException).
         * "Napraw" sytuacje - dograj wlasciwy JAR do lib/ programowo (Files.copy z innego
         * katalogu) i wykonaj build PONOWNIE (nowy Project) - zweryfikuj sukces drugiego
         * przebiegu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MultiLibDirectoriesMerged {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj <path id="cp"> skladajacy sie z DWOCH osobnych <fileset> - jeden dla
         * "lib/internal" (wlasne biblioteki firmowe) i jeden dla "lib/external" (biblioteki
         * zewnetrzne). Skompiluj i uruchom klase korzystajaca z bibliotek z OBU katalogow
         * jednoczesnie i zweryfikuj sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ClasspathRegressionTestSuite {
        /*
         * 🧪 Zadanie 24:
         * Napisz mala "regresyjna" metode testowa runClasspathScenario(String scenarioName,
         * Runnable setup, boolean expectedToSucceed), ktora przygotowuje lib/ zgodnie z
         * "setup", wykonuje build i porownuje wynik (sukces/blad) z "expectedToSucceed".
         * Wywolaj ja dla 3 scenariuszy: (1) wszystko OK, (2) brakujacy JAR, (3) uzupelniony
         * z powrotem JAR - wypisz raport PASS/FAIL dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManifestClassPathVsAntPath {
        /*
         * 🧪 Zadanie 25:
         * Porownaj DWA podejscia do tego samego problemu: (1) atrybut Class-Path w manifescie
         * JAR-a aplikacji (z Lekcji 2) vs (2) <path>/<fileset> w Ancie. Zbuduj oba podejscia
         * dla tej samej pary "aplikacja + biblioteka" i wypisz, w czym sie roznia
         * (statyczny manifest vs dynamiczny fileset skanowany za kazdym buildem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LargeLibDirectoryPerformance {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj 30 malych, roznych plikow-atrap .jar w lib/ (nie musza byc prawdziwymi,
         * poprawnymi JAR-ami - wystarczy, ze fileset je zlapie). Zmierz czas
         * (System.nanoTime) rozwiazania <path> z <fileset dir="lib" includes="*.jar"/> przy
         * takiej liczbie plikow i wypisz wynik w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConditionalClasspathByEnvironment {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj build.xml z DWOMA roznymi <path> ("cp.dev" z biblioteka-dev.jar, "cp.prod"
         * z biblioteka-prod.jar) i property "env" wybierajacym, ktory <path> uzyc (przez
         * dynamiczne ustawienie property PRZED configureProject w zaleznosci od parametru
         * metody). Zweryfikuj rozne zachowanie dla "dev" i "prod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ClasspathAuditReportGenerator {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode generateClasspathAuditReport(Path libDir), ktora dla KAZDEGO pliku
         * .jar w lib/ otwiera go jako java.util.jar.JarFile i wypisuje liste zawartych w nim
         * plikow .class (nazwy klas) - symulujac prosty "audyt" tego, co faktycznie
         * dostarcza kazda biblioteka na classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_NoSuchMethodErrorPreciseDemo {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj biblioteke "MathLib" wersja 1 z metoda "int add(int,int)". Skompiluj wobec
         * niej klase "Calc" wywolujaca add(). Zbuduj WERSJE 2 tej samej klasy MathLib, w
         * ktorej add() przyjmuje TRZY argumenty (zmieniona sygnatura), zamien JAR w lib/ na
         * wersje 2 (bez rekompilacji Calc) i uruchom - zweryfikuj precyzyjnie, ze
         * NoSuchMethodError wskazuje na BRAKUJACA (stara) sygnature metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_AntClasspathCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny scenariusz demonstrujacy WSZYSTKIE problemy z lekcji po kolei w
         * jednym raporcie: (1) dzialajacy build z poprawnym classpath, (2) usuniety JAR ->
         * blad kompilacji, (3) przywrocony, ale w ZLEJ wersji JAR -> NoSuchMethodError w
         * runtime, (4) naprawiony (poprawna wersja) -> sukces. Dla kazdego z 4 krokow
         * wypisz linie raportu "KROK N: <opis> -> <SUKCES|BLAD: komunikat>".
         */
        public static void main(String[] args) { }
    }
}
