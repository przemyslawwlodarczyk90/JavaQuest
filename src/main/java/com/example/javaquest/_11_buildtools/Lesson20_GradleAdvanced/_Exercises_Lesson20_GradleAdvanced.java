package com.example.javaquest._11_buildtools.Lesson20_GradleAdvanced;

public class _Exercises_Lesson20_GradleAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimplestCustomTask {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj String zawierajacy definicje najprostszego mozliwego custom taska
         * w build.gradle: tasks.register("hello") { doLast { println "Hello!" } }.
         * Wypisz go na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DoFirstVsDoLastExplanation {
        /*
         * 🧪 Zadanie 2:
         * Wypisz dwie linie wyjasniajace roznice miedzy doFirst {} i doLast {} w kontekscie
         * custom taska Gradle (kiedy dokladnie sie wykonuja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DependsOnSnippet {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj String custom taska "packageInfo", ktory zaleznosc dependsOn("jar") i w
         * doLast wypisuje "JAR zostal juz zbudowany". Wypisz go na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MainVsTestSourceSetPrint {
        /*
         * 🧪 Zadanie 4:
         * Wypisz dwie linie opisujace domyslne source sety Gradle: "main" (src/main/java)
         * i "test" (src/test/java) - po jednym zdaniu na kazdy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IncludeSettingsGradleSnippet {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj String settings.gradle z rootProject.name = 'sklep' i
         * include("core", "web") - wypisz go na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AllprojectsVsSubprojectsExplanation {
        /*
         * 🧪 Zadanie 6:
         * Wypisz dwie linie wyjasniajace roznice miedzy allprojects {} (dotyczy roota
         * i podprojektow) i subprojects {} (dotyczy tylko podprojektow, bez roota).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ProjectDependencyNotation {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj String fragmentu "dependencies {}" z jedna zaleznoscia
         * "implementation project(':core')" - wypisz go i dodaj komentarz wyjasniajacy
         * skladnie project(":nazwa") jako odwolanie do podprojektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GradlePropertiesFileContent {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj String gradle.properties z 2 wlasciwosciami (np. app.version=2.0.0,
         * org.gradle.parallel=true) - wypisz go na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ProjectPropertyVsSystemPropertyPrint {
        /*
         * 🧪 Zadanie 9:
         * Wypisz dwie linie porownujace flagi -P (project property) i -D (system
         * property) w Gradle - po jednym przykladzie uzycia dla kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EnvironmentVariableReadDemo {
        /*
         * 🧪 Zadanie 10:
         * Odczytaj (System.getenv) realna zmienna srodowiskowa "PATH" (lub "Path" na
         * Windows) i wypisz jej dlugosc w znakach (nie cala wartosc) razem z komentarzem,
         * ze to ten sam mechanizm, ktorym Gradle/build moze odczytac sekrety.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultiStepCustomTask {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj String custom taska "printProjectInfo" z dependsOn("compileJava"),
         * doFirst (log startu) i doLast (wypisanie project.name i project.version) -
         * jak w przykladzie z lekcji, ale z INNYMI nazwami zmiennych/komunikatow. Zapisz
         * go do build.gradle w temp dir i wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomSourceSetSnippet {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj String bloku sourceSets {} definiujacego dodatkowy source set
         * "integrationTest" (java.srcDir 'src/integrationTest/java'). Wypisz go i dodaj
         * komentarz, w jakim scenariuszu (testy integracyjne osobno od jednostkowych)
         * jest to przydatne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreeModuleMultiProjectSettings {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj String settings.gradle z 3 podprojektami: include("core", "service",
         * "app"). Zbuduj TEZ Stringi 3 malych build.gradle (po jednym per podprojekt) -
         * core (plugin java), service (plugin java, implementation project(':core')),
         * app (plugin application, implementation project(':service')). Wypisz wszystkie 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DependencyInsightIllustrativeOutput {
        /*
         * 🧪 Zadanie 14:
         * Wypisz WLASNY ilustracyjny (jasno oznaczony jako nieprawdziwe wykonanie) output
         * "./gradlew dependencyInsight --dependency jackson-databind" pokazujacy konflikt
         * wersji 2.15.0 vs 2.17.0 i informacje, ktora wersja "wygrala" i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UpToDateVsExecutedComparisonPrint {
        /*
         * 🧪 Zadanie 15:
         * Wypisz dwa ilustracyjne bloki outputu: pierwszy build (wszystkie taski
         * wykonane, bez UP-TO-DATE), drugi build bez zmian w kodzie (wszystkie taski
         * UP-TO-DATE) - z komentarzem wyjasniajacym mechanizm incremental build.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GradlePropertiesReaderSimulation {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj Map<String,String> symulujaca odczytane gradle.properties (np.
         * app.version=2.0.0, org.gradle.jvmargs=-Xmx2g) i napisz metode
         * getProperty(Map<String,String> props, String key, String fallback), ktora
         * zwraca wartosc albo fallback. Przetestuj ja na istniejacym i nieistniejacym kluczu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TaskDependencyGraphPrint {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj mala mape Map<String, List<String>> reprezentujaca grafu zaleznosci
         * taskow (np. "build" -> ["test","jar"], "jar" -> ["compileJava"], "test" ->
         * ["compileTestJava"]) i napisz metode, ktora dla podanego taska wypisuje CALY
         * lancuch zaleznosci (rekurencyjnie) w kolejnosci wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildCacheHitMissSimulation {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj (bez prawdziwego Gradle) prosty "build cache": Map<String,String>
         * gdzie kluczem jest hash "wejscia" taska, a wartoscia wynik. Napisz metode
         * runTaskWithCache(String input), ktora sprawdza cache, wypisuje "CACHE HIT"
         * albo "CACHE MISS" (i wtedy dodaje do mapy), zademonstruj wywolaniami z tym
         * samym i innym inputem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EnvVsPropertyPriorityDemo {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode resolveConfigValue(String envVar, String systemPropKey, String
         * fallback), ktora sprawdza po kolei: System.getenv(envVar), potem
         * System.getProperty(systemPropKey), a jak obu brak - fallback. Przetestuj ja z
         * realna zmienna srodowiskowa (np. "PATH"/"Path") i nieistniejaca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullAdvancedBuildGradleForMultiProject {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj kompletny zestaw plikow multi-project builda (settings.gradle + root
         * build.gradle z allprojects/subprojects + build.gradle podprojektow "core" i
         * "app") zawierajacy TEZ jeden custom task w app/build.gradle (np.
         * "printProjectInfo" z dependsOn("compileJava")). Zapisz wszystkie pliki w
         * odpowiedniej strukturze katalogow w temp dir (Files.createDirectories) i
         * wypisz drzewo utworzonych plikow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomTaskGeneratorMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode generateCustomTask(String name, List<String> dependsOnTasks,
         * String doLastMessage), ktora buduje String definicji custom taska (z
         * dependsOn dla wszystkich podanych taskow i doLast wypisujacym podana
         * wiadomosc). Wywolaj ja 3 razy z roznymi parametrami i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiProjectDependencyGraphValidator {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj Map<String, List<String>> reprezentujaca zaleznosci miedzy podprojektami
         * (np. "app" -> ["service"], "service" -> ["core"], "core" -> []). Napisz metode
         * detectCycle(Map<String,List<String>> graph), ktora wykrywa (DFS), czy istnieje
         * CYKL zaleznosci (co w Gradle powodowaloby blad konfiguracji) i wypisuje wynik
         * dla poprawnego grafu i dla grafu z celowo dodanym cyklem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_VersionConflictResolutionSimulator {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj liste "zadanych" wersji tej samej zaleznosci przez rozne moduly (np.
         * ["2.8.0", "2.11.0", "2.9.5"]) i napisz metode resolveNewestWins(List<String>
         * versions), ktora zwraca najwyzsza wersje wg porownania numerycznego
         * (major.minor.patch, bez zewnetrznej biblioteki) - symulujac domyslna
         * strategie Gradle "najnowsza wygrywa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildCacheStatsReport {
        /*
         * 🧪 Zadanie 24:
         * Rozszerz symulacje cache z Zadania 18 o licznik trafien/braków (hits/misses).
         * Wykonaj serie 10 "wywolan taska" z powtarzajacymi sie i unikalnymi inputami,
         * a na koniec wypisz raport: liczba hitow, liczba missow, procent trafien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GradlePropertiesToSystemPropertiesBridge {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode, ktora "wczytuje" symulowana mape gradle.properties i dla
         * kazdego wpisu WYWOLUJE System.setProperty(klucz, wartosc) (realnie ustawiajac
         * system property w tej samej JVM). Zweryfikuj przez System.getProperty(...), ze
         * wartosci sa faktycznie widoczne po "wczytaniu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TaskExecutionOrderSimulator {
        /*
         * 🧪 Zadanie 26:
         * Na bazie grafu zaleznosci taskow z Zadania 17, napisz metode topologicalOrder(),
         * ktora zwraca poprawna kolejnosc wykonania WSZYSTKICH taskow (topological sort,
         * bez zewnetrznej biblioteki) - taki, w jakiej faktycznie wykonalby je Gradle -
         * i wypisz wynik dla grafu z co najmniej 5 taskami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiModuleBuildGradleGeneratorFramework {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode generateMultiProjectSkeleton(String rootName,
         * Map<String,List<String>> moduleDependencies), ktora na podstawie mapy
         * modul->zaleznosci-od-modulow generuje KOMPLET Stringow (settings.gradle +
         * build.gradle per modul z odpowiednimi "implementation project(':x')"). Wywolaj
         * z przykladem 3-4 modulow i wypisz wszystkie wygenerowane pliki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PropertyPrecedenceChainDemo {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj i zademonstruj PELNY lancuch pierwszenstwa konfiguracji (jak
         * naprawde dzialaloby w Gradle): project property (-P, tu symulowane jako Map),
         * potem system property (System.getProperty), potem zmienna srodowiskowa
         * (System.getenv), potem wartosc domyslna z gradle.properties (symulowana Map).
         * Napisz metode resolve(...) sprawdzajaca je w tej kolejnosci i przetestuj 4
         * scenariusze (kazdy poziom "wygrywa" w innym tescie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildPerformanceComparisonReport {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj (jako dane w tablicy/liscie) ILUSTRACYJNE czasy 3 buildow tego samego
         * projektu: "clean build" (najdluzszy), "build" bez zmian (incremental,
         * najkrotszy dzieki UP-TO-DATE), "build" z cache z innej maszyny (sredni, dzieki
         * FROM-CACHE). Wypisz raport porownawczy z procentowym przyspieszeniem wzgledem
         * pierwszego buildu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAdvancedGradleProject {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY multi-project projekt Gradle (3
         * podprojekty jak w Zadaniu 13), z co najmniej 1 custom taskiem w kazdym
         * podprojekcie, gradle.properties z 3 wlasciwosciami, i settings.gradle. Zapisz
         * WSZYSTKIE pliki na dysku w poprawnej strukturze katalogow (Files.createDirectories
         * + Files.writeString), wypisz drzewo katalogow (Files.walk) i na koniec posprzataj
         * (usun caly temp dir rekurencyjnie).
         */
        public static void main(String[] args) { }
    }
}
