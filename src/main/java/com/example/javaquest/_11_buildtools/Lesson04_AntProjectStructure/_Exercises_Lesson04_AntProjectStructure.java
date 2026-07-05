package com.example.javaquest._11_buildtools.Lesson04_AntProjectStructure;

public class _Exercises_Lesson04_AntProjectStructure {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateStandardDirectoryLayout {
        /*
         * 🧪 Zadanie 1:
         * Utworz na dysku (Files.createDirectories) standardowa strukture katalogow z
         * lekcji: src/, test/, resources/, lib/, build/, dist/. Wypisz dla kazdego, czy
         * powstal (Files.exists).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DirectoryPurposeList {
        /*
         * 🧪 Zadanie 2:
         * Bez uzywania Anta: wypisz na konsoli 6 katalogow z konwencji projektu Ant (src,
         * test, resources, lib, build, dist) razem z jednozdaniowym opisem przeznaczenia
         * kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InitTargetCreatesDirs {
        /*
         * 🧪 Zadanie 3:
         * Wygeneruj build.xml z targetem "init" uzywajacym dwoch <mkdir> (dla "build" i
         * "dist"). Uruchom embedowanym Antem i zweryfikuj (Files.isDirectory), ze OBA
         * katalogi naprawde powstaly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SingleResourceCopy {
        /*
         * 🧪 Zadanie 4:
         * Utworz plik resources/app.properties z jedna linia tekstu. Wygeneruj build.xml z
         * targetem uzywajacym <copy file="resources/app.properties" todir="build/classes"/>.
         * Uruchom embedowanym Antem i zweryfikuj, ze plik znalazl sie w build/classes.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WhySrcAndBuildAreSeparate {
        /*
         * 🧪 Zadanie 5:
         * Bez uzywania Anta: wypisz wyjasnienie (2-3 linie), czemu kod zrodlowy (src) NIE
         * powinien nigdy trafiac do tego samego katalogu co skompilowane klasy (build) -
         * odniesienie do bezpieczenstwa operacji "clean".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LibDirWithOneJar {
        /*
         * 🧪 Zadanie 6:
         * Utworz katalog lib/ i skopiuj/zapisz do niego jakikolwiek plik binarny
         * udajacy JAR (np. plik "placeholder.jar" z dowolna tresc bajtowa). Wygeneruj
         * build.xml z targetem uzywajacym <echo> wypisujacym liczbe plikow .jar w lib/
         * (mozesz to policzyc w Javie PRZED wygenerowaniem build.xml i wstrzyknac liczbe do
         * komunikatu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompileToDedicatedClassesDir {
        /*
         * 🧪 Zadanie 7:
         * Wygeneruj klase .java w src/ i build.xml z targetem "compile" kompilujacym do
         * build/classes/ (NIE do build/ bezposrednio). Uruchom embedowanym Antem i
         * zweryfikuj poprawna sciezke wynikowego pliku .class.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DistDirOnlyFinalArtifacts {
        /*
         * 🧪 Zadanie 8:
         * Wygeneruj build.xml budujacy JAR do dist/app.jar. Po wykonaniu, wypisz
         * zawartosc katalogu dist/ (Files.list) i zweryfikuj, ze zawiera WYLACZNIE finalny
         * JAR, bez zadnych plikow .class czy .java.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestDirSeparateFromSrc {
        /*
         * 🧪 Zadanie 9:
         * Utworz src/Calculator.java (klasa produkcyjna) i test/CalculatorManualTest.java
         * (osobna klasa w INNYM katalogu, symulujaca test). Wygeneruj build.xml z DWOMA
         * osobnymi targetami kompilacji: "compile" (src -> build/classes) i "compile-test"
         * (test -> build/test-classes). Zweryfikuj, ze oba katalogi wynikowe sa ROZNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BuildPropertiesFileConvention {
        /*
         * 🧪 Zadanie 10:
         * Wygeneruj plik build.properties z 2 wpisami (np. "src.dir=src",
         * "build.dir=build") i build.xml wczytujacy go przez <property file="build.properties"/>.
         * Uzyj obu wlasciwosci w targecie <mkdir dir="${build.dir}"/> i zweryfikuj, ze
         * katalog o nazwie z pliku properties naprawde powstal.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullLayoutWithConfigResource {
        /*
         * 🧪 Zadanie 11:
         * Odtworz caly wzorzec z teorii lekcji (src/AppConfig.java + resources/config.properties
         * + build.xml z clean/init/compile/copy-resources/jar), ale z WLASNYMI, innymi
         * kluczami konfiguracyjnymi. Uruchom "jar" i zweryfikuj (Files.exists), ze zasob
         * trafil do build/classes.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleResourceFilesCopy {
        /*
         * 🧪 Zadanie 12:
         * Utworz katalog resources/ z 4 roznymi plikami (.properties, .txt, .xml, .json).
         * Wygeneruj build.xml z <copy todir="build/classes"><fileset dir="resources"/></copy>
         * i zweryfikuj, ze WSZYSTKIE 4 pliki (niezaleznie od rozszerzenia) trafily do celu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FilesetIncludeExcludePatterns {
        /*
         * 🧪 Zadanie 13:
         * Utworz resources/ z plikami *.properties i *.tmp wymieszanymi (np. 2 pliki
         * kazdego typu). Wygeneruj build.xml z <fileset dir="resources"
         * includes="**\/*.properties"/> w tasku <copy> i zweryfikuj, ze pliki .tmp NIE
         * zostaly skopiowane, a .properties - tak.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CleanRemovesOnlyGeneratedDirs {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj projekt (compile + jar), a POTEM wykonaj "clean". Zweryfikuj, ze build/ i
         * dist/ zostaly usuniete, ALE src/ i resources/ (kod zrodlowy) wciaz istnieja i
         * nie zostaly naruszone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RebuildAfterSourceChange {
        /*
         * 🧪 Zadanie 15:
         * Skompiluj i spakuj klase wypisujaca "wersja 1", uruchom "clean", zmien plik
         * zrodlowy na "wersja 2", zbuduj CAŁY pipeline od zera (clean -> init -> compile ->
         * copy-resources -> jar -> run) i zweryfikuj, ze output zawiera "wersja 2".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConfigPerEnvironmentResources {
        /*
         * 🧪 Zadanie 16:
         * Utworz resources-dev/config.properties i resources-prod/config.properties z
         * roznymi wartosciami. Napisz metode buildForEnv(String env), ktora generuje
         * build.xml kopiujacy zasoby z WLASCIWEGO katalogu (resources-${env}) w zaleznosci
         * od parametru, i zweryfikuj rozna zawartosc po zbudowaniu dla "dev" i dla "prod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LibDirWithRealGeneratedJar {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj (jak w Lekcji 2/3) prawdziwy, maly JAR z jedna klasa pomocnicza i zapisz
         * go w katalogu lib/ projektu. Wygeneruj build.xml z targetem wypisujacym przez
         * <echo> nazwy WSZYSTKICH plikow .jar znajdujacych sie w lib/ (mozesz to
         * przygotowac w Javie i wstrzyknac do komunikatu, albo uzyc <fileset> + <pathconvert>).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedResourcesDirectoryStructure {
        /*
         * 🧪 Zadanie 18:
         * Utworz zagniezdzona strukture resources/config/app.properties i
         * resources/i18n/messages_pl.properties. Skopiuj CALA strukture
         * (<copy todir="build/classes"><fileset dir="resources"/></copy> zachowuje
         * podkatalogi) i zweryfikuj, ze oba pliki znalazly sie we WLASCIWYCH podkatalogach
         * wewnatrz build/classes.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DistArtifactNamingConvention {
        /*
         * 🧪 Zadanie 19:
         * Wygeneruj build.xml z property "app.name" i "app.version", gdzie nazwa finalnego
         * JAR-a sklada sie z obu (np. "${app.name}-${app.version}.jar"). Zbuduj projekt dla
         * 2 roznych wersji (property nadpisywane z Javy przed configureProject) i zweryfikuj
         * 2 rozne nazwy plikow w dist/.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_VerifyClassesAndResourcesColocated {
        /*
         * 🧪 Zadanie 20:
         * Po zbudowaniu projektu z teorii lekcji, napisz metode
         * verifySameDirectory(Path classFile, Path resourceFile), ktora sprawdza (getParent())
         * czy oba pliki znajduja sie w TYM SAMYM katalogu, i wypisz wynik weryfikacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullProjectGeneratorMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz generyczna metode scaffoldAntProject(Path root, String mainClassSource,
         * String resourceFileName, String resourceContent), ktora tworzy CALA strukture
         * katalogow, pliki zrodlowe, zasoby i build.xml, a nastepnie wykonuje "jar" przez
         * embedowanego Anta. Przetestuj ja dwukrotnie z roznymi argumentami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_IncrementalResourceUpdateDetection {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj projekt (jar), zapisz czas modyfikacji (Files.getLastModifiedTime) pliku
         * skopiowanego zasobu w build/classes. Zmien zawartosc zasobu ZRODLOWEGO w
         * resources/ i zbuduj PONOWNIE (bez clean) - zweryfikuj, ze skopiowana wersja w
         * build/classes zostala zaktualizowana (nowa zawartosc, nowy czas modyfikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LibVersionConflictSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zapisz w lib/ DWA pliki o tej samej "nazwie logicznej", ale rozny content,
         * symulujace 2 rozne wersje tej samej biblioteki (np. "utils-old.jar" i
         * "utils-new.jar"). Wygeneruj build.xml, ktory <echo>-uje ktora wersja zostanie
         * uzyta na podstawie prostego <fileset> + sortowania nazw, i skomentuj w kodzie,
         * czemu w praktyce takie duplikaty sa niebezpieczne (poznamy szczegoly w Lekcji 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ParallelStructureTwoModules {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj 2 rownolegle "moduly" (module-core/ i module-app/), kazdy z WLASNA,
         * kompletna struktura src/build/dist. Module-app zalezy od skompilowanych klas
         * module-core (classpath). Wykonaj build OBU (2 osobne Project) w poprawnej
         * kolejnosci i zweryfikuj koncowy sukces uruchomienia module-app.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ResourceFilteringWithPropertyReplacement {
        /*
         * 🧪 Zadanie 25:
         * Wygeneruj resources/config.properties z tokenem "@app.version@" wewnatrz
         * wartosci. Uzyj tasku <copy> z zagniezdzonym <filterset> (podstawienie tokenu na
         * realna wartosc property przy kopiowaniu) i zweryfikuj, ze skopiowany plik w
         * build/classes zawiera JUZ podstawiona wartosc, nie surowy token.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CleanBuildIdempotencyTest {
        /*
         * 🧪 Zadanie 26:
         * Wykonaj PELNY cykl clean -> init -> compile -> copy-resources -> jar TRZY RAZY
         * pod rzad na tym samym projekcie (bez zmiany kodu) i zweryfikuj, ze kazdy z 3
         * przebiegow daje IDENTYCZNY wynik (ta sama liczba plikow w build/classes, ten sam
         * rozmiar finalnego JAR-a) - to demonstruje idempotentnosc poprawnego clean builda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_StructureValidationBeforeBuild {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode validateProjectStructure(Path root), ktora PRZED uruchomieniem
         * Anta sprawdza (Files.exists/Files.isDirectory), czy istnieja wymagane katalogi
         * (src, resources) i zwraca liste brakujacych. Przetestuj ja na kompletnym
         * projekcie (brak bledow) i na projekcie z usunietym katalogiem resources/ (lista
         * z jednym brakujacym elementem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ArchivedBuildHistoryDirNaming {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz konwencje dist/ o wersjonowanie: kazde uruchomienie targetu "jar"
         * powinno wygenerowac JAR z ROSNACYM numerem buildu w nazwie (np. app-build1.jar,
         * app-build2.jar) - zaimplementuj to przekazujac wartosc property "build.number"
         * do embedowanego Anta z Javy (project.setProperty PRZED configureProject) i
         * zweryfikuj 3 rozne nazwy plikow po 3 przebiegach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SymmetricSrcTestResourcesLayout {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj strukture, w ktorej ISTNIEJE zarowno resources/ (dla src) jak i
         * test-resources/ (dla test/) - kazdy kopiowany do INNEGO katalogu wynikowego
         * (build/classes vs build/test-classes) osobnymi targetami "copy-resources" i
         * "copy-test-resources". Zweryfikuj, ze zasoby produkcyjne i testowe NIGDY sie nie
         * mieszaja miedzy katalogami wynikowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ProjectStructureCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, realistyczny mini-projekt Ant: src/ (3 klasy, w tym Main),
         * test/ (1 klasa symulujaca test), resources/ (2 pliki konfiguracyjne), lib/
         * (1 wygenerowany wlasny JAR uzywany przez Main), build.xml z pelnym lancuchem
         * clean/init/compile/compile-test/copy-resources/jar/run. Wykonaj caly build,
         * zweryfikuj strukture wynikowa (build/classes, build/test-classes, dist/) i
         * wypisz koncowy raport "=== STRUKTURA PROJEKTU ZWERYFIKOWANA ===" z lista
         * wszystkich sprawdzonych sciezek i wynikow (true/false).
         */
        public static void main(String[] args) { }
    }
}
