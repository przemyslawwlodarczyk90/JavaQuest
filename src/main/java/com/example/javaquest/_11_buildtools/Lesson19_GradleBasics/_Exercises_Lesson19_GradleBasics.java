package com.example.javaquest._11_buildtools.Lesson19_GradleBasics;

public class _Exercises_Lesson19_GradleBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WhatIsGradlePrint {
        /*
         * 🧪 Zadanie 1:
         * Wypisz na konsoli (System.out.println) jednozdaniowe wyjasnienie, czym jest
         * Gradle i jak lączy podejscie Anta (elastycznosc) z podejsciem Mavena (konwencje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BuildGradleVsSettingsGradle {
        /*
         * 🧪 Zadanie 2:
         * Wypisz dwie linie: pierwsza opisujaca role pliku "build.gradle", druga role
         * pliku "settings.gradle" - w jednym zdaniu kazda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GenerateMinimalBuildGradle {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj w Javie String zawierajacy minimalny build.gradle z jednym pluginem
         * "java" (bez zaleznosci). Zapisz go do pliku w katalogu tymczasowym
         * (Files.createTempDirectory) i wypisz jego zawartosc na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GroovyVsKotlinDslPrint {
        /*
         * 🧪 Zadanie 4:
         * Wypisz dwa warianty deklaracji zaleznosci "junit:junit:4.13.2" jako
         * "testImplementation" - jeden w skladni Groovy DSL, drugi w skladni Kotlin DSL
         * (uzyj text blockow Javy - trojne cudzoslowy), i porownaj je jedna linia komentarza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WrapperFilesList {
        /*
         * 🧪 Zadanie 5:
         * Wypisz na konsoli 4 pliki/katalogi skladajace sie na Gradle Wrapper (gradlew,
         * gradlew.bat, gradle/wrapper/gradle-wrapper.jar, gradle/wrapper/gradle-wrapper.properties)
         * razem z jednozdaniowym opisem roli kazdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ProjectStructurePrint {
        /*
         * 🧪 Zadanie 6:
         * Wypisz standardowa strukture katalogow projektu Gradle (src/main/java,
         * src/main/resources, src/test/java, src/test/resources, build/) - jedna
         * linia na katalog z opisem jego zawartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PluginsComparisonPrint {
        /*
         * 🧪 Zadanie 7:
         * Wypisz 4 pluginy wspomniane w lekcji (java, application, war,
         * org.springframework.boot) razem z jednozdaniowym opisem, co kazdy dodaje do builda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuiltInTasksTable {
        /*
         * 🧪 Zadanie 8:
         * Wypisz tabelke (kolejne linie println) 6 wbudowanych taskow (clean,
         * compileJava, test, jar, build, run) z jednozdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DependencyConfigurationsPrint {
        /*
         * 🧪 Zadanie 9:
         * Wypisz 5 konfiguracji zaleznosci (implementation, api, compileOnly,
         * runtimeOnly, testImplementation) z jednozdaniowym wyjasnieniem kazdej
         * i jej najblizszym odpowiednikiem <scope> z Mavena.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TargetDirComparisonPrint {
        /*
         * 🧪 Zadanie 10:
         * Wypisz jedna linie porownujaca katalog wynikowy Gradle ("build/") z katalogiem
         * wynikowym Mavena ("target/") - ze wspolna uwaga, ze oba naleza do .gitignore.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenerateApplicationBuildGradle {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj String z build.gradle uzywajacym pluginow "java" i "application",
         * z jedna zaleznoscia implementation ("com.google.code.gson:gson:2.11.0") i
         * blokiem application { mainClass = '...' }. Zapisz do pliku w temp dir i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GenerateSettingsGradleWithName {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj String settings.gradle z "rootProject.name = 'moj-projekt'" i zapisz go
         * do pliku w temp dir razem z odpowiadajacym build.gradle (plugin 'java') -
         * wypisz oba pliki na konsoli w kolejnosci settings.gradle, potem build.gradle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompileOnlyVsRuntimeOnlyDemo {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode buildDependenciesBlock(), ktora generuje fragment "dependencies {}"
         * z jedna zaleznoscia compileOnly i jedna runtimeOnly (dowolne biblioteki), a
         * nastepnie wypisz komentarz wyjasniajacy roznice miedzy nimi (kompilacja vs runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WarPluginSnippet {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj String z fragmentem build.gradle uzywajacym pluginu "war" (bez reszty
         * konfiguracji) i wypisz komentarz wyjasniajacy, kiedy warto uzyc tego pluginu
         * (aplikacje webowe, packaging .war) - porownaj z <packaging>war</packaging> w Mavenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SpringBootPluginSnippet {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj String z fragmentem build.gradle uzywajacym pluginu
         * "org.springframework.boot" oraz "io.spring.dependency-management" i wypisz
         * komentarz porownujacy go do spring-boot-maven-plugin z pom.xml tego kursu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TaskTableWithDependsOnHints {
        /*
         * 🧪 Zadanie 16:
         * Wypisz tabelke 6 taskow (clean, compileJava, test, jar, build, run) razem z
         * "od czego zalezy" (np. jar zalezy od compileJava, build zalezy od test i jar) -
         * na podstawie opisu z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleDependenciesBlock {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj String "dependencies {}" z 4 zaleznosciami: implementation gson,
         * testImplementation junit-jupiter, compileOnly lombok, runtimeOnly h2. Zapisz
         * calosc build.gradle do pliku i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GroovyVsMavenVsAntSameDependency {
        /*
         * 🧪 Zadanie 18:
         * Dla zaleznosci "com.google.code.gson:gson:2.11.0" wypisz trzy linie: jak
         * wygladalaby deklaracja w Gradle (implementation '...'), w Mavenie
         * (<dependency>...</dependency>, kilka linii XML) i jak wygladalby recznie
         * pobrany JAR w Ancie (komentarz - Ant nie ma natywnego menedzera zaleznosci,
         * patrz Lesson09_AntIvy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WrapperReproducibilityScenario {
        /*
         * 🧪 Zadanie 19:
         * Napisz (w komentarzu, bez faktycznego wykonania) scenariusz: dwaj programisci
         * maja zainstalowane rozne wersje Gradle globalnie (7.0 i 8.10). Wyjasnij (przez
         * System.out.println), jak Gradle Wrapper eliminuje ten problem, i co by sie stalo
         * bez wrappera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullBuildGradleForConsoleApp {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj kompletny, realistyczny build.gradle dla malej aplikacji konsolowej:
         * plugin java + application, group/version, repositories { mavenCentral() },
         * dependencies (implementation gson, testImplementation junit-jupiter),
         * application { mainClass = 'com.example.App' }, test { useJUnitPlatform() }.
         * Zapisz na dysk w temp dir i wypisz cala zawartosc.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ParameterizedBuildGradleGenerator {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode generateBuildGradle(String groupId, String version, List<String>
         * implementationDeps), ktora buduje kompletny String build.gradle na podstawie
         * parametrow (plugin java, group, version, sekcja dependencies z podanej listy
         * jako "implementation '...'" per element). Wywolaj ja z przykladowymi danymi
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareGeneratedPomAndBuildGradle {
        /*
         * 🧪 Zadanie 22:
         * Wygeneruj (jako dwa Stringi) minimalny pom.xml (z Lesson11) i minimalny
         * build.gradle (z tej lekcji) dla tego samego hipotetycznego projektu
         * (groupId com.example, artifactId/name hello, version 1.0.0, jedna zaleznosc
         * junit). Wypisz oba jeden pod drugim i policz liczbe linii kazdego (String.lines().count()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DependencyConfigurationValidator {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode isValidConfiguration(String name), ktora sprawdza, czy podana
         * nazwa jest jedna z 5 poznanych konfiguracji zaleznosci (implementation, api,
         * compileOnly, runtimeOnly, testImplementation). Przetestuj ja na liscie nazw
         * zawierajacej poprawne i niepoprawne (np. "compile", "provided") wpisy, wypisujac
         * wynik dla kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulatedGradleTasksOutput {
        /*
         * 🧪 Zadanie 24:
         * Wypisz ILUSTRACYJNY (WYRAZNIE oznaczony w kodzie jako nie-prawdziwe wykonanie)
         * przykladowy output komendy "./gradlew tasks" - liste 6 taskow z krotkim opisem,
         * naśladujaca format prawdziwego Gradle (nagłówek "Tasks runnable from root project").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MultiDependencyKotlinDslGenerator {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode toKotlinDsl(String configuration, String coordinates), ktora
         * zwraca linie w stylu Kotlin DSL (np. implementation("coordinates")).
         * Uzyj jej do przetlumaczenia 3 zaleznosci z Groovy DSL (implementation, testImplementation,
         * compileOnly) na Kotlin DSL i wypisz wynik obok oryginalu Groovy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProjectStructureValidator {
        /*
         * 🧪 Zadanie 26:
         * Utworz w katalogu tymczasowym pelna strukture katalogow projektu Gradle
         * (src/main/java, src/main/resources, src/test/java, src/test/resources) za pomoca
         * Files.createDirectories. Napisz metode, ktora weryfikuje (Files.isDirectory),
         * ze wszystkie 4 katalogi istnieja, i wypisuje raport OK/BRAK dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildGradleDiffExplainer {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj dwa Stringi build.gradle: wersja "v1" (tylko plugin java) i wersja "v2"
         * (plugin java + application + 2 zaleznosci). Napisz metode, ktora wypisuje
         * krotki opis roznic miedzy nimi (co zostalo dodane) - bez uzywania zewnetrznej
         * biblioteki diff, prostym porownaniem linii (String.lines()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_WrapperPropertiesGenerator {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj String gradle-wrapper.properties z wlasciwosciami distributionUrl (np.
         * wskazujacym gradle-8.10-bin.zip), distributionBase, distributionPath, zipStoreBase,
         * zipStorePath. Zapisz do pliku w temp dir i wypisz - wyjasnij komentarzem, ze
         * TEN plik (mimo ze mala tresc) jest kluczowy dla reprodukowalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullProjectSkeletonOnDisk {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj na dysku (w temp dir) mini-skeleton projektu Gradle: build.gradle,
         * settings.gradle, katalogi src/main/java i src/test/java, oraz pojedynczy plik
         * src/main/java/App.java z prosta klasa (System.out.println w main). Na koniec
         * wypisz drzewo utworzonych plikow (Files.walk).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_GradleVsMavenSummaryReport {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: wygeneruj (jako Stringi) build.gradle + settings.gradle
         * dla projektu "final-app" (plugin java+application, 2 zaleznosci, mainClass) i
         * odpowiadajacy mu pom.xml (analogiczny do tego z Lesson11, z tymi samymi 2
         * zaleznosciami). Wypisz wszystkie 3 pliki oraz krotkie podsumowanie (3 punkty),
         * co Gradle robi inaczej/lepiej wzgledem tego konkretnego przykladu.
         */
        public static void main(String[] args) { }
    }
}
