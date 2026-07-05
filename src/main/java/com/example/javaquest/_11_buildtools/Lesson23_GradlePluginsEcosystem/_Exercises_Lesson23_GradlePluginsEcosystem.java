package com.example.javaquest._11_buildtools.Lesson23_GradlePluginsEcosystem;

public class _Exercises_Lesson23_GradlePluginsEcosystem {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FatJarPurposeExplanation {
        /*
         * 🧪 Zadanie 1:
         * Wypisz jedno zdanie wyjasniajace, czym jest "fat JAR" i czemu zwykly JAR nie
         * wystarcza do odpalenia aplikacji z zaleznosciami "java -jar app.jar".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ShadowPluginApplySnippet {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj String blogu plugins{} aplikujacego Shadow Plugin (id
         * 'com.gradleup.shadow' version '8.3.5') razem z pluginem 'java' - wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_JarVsShadowJarComparisonPrint {
        /*
         * 🧪 Zadanie 3:
         * Wypisz dwie linie porownujace task 'jar' (cienki, bez zaleznosci) i
         * 'shadowJar' (tlusty, ze wszystkimi zaleznosciami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MavenShadeVsGradleShadowMapping {
        /*
         * 🧪 Zadanie 4:
         * Wypisz jedna linie mapujaca maven-shade-plugin (Lekcja 13) na Shadow Plugin w
         * Gradle - ta sama idea, inny mechanizm.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BootJarPurposeExplanation {
        /*
         * 🧪 Zadanie 5:
         * Wypisz jedno zdanie wyjasniajace, co robi task 'bootJar' i czemu ZASTEPUJE
         * (a nie dziala OBOK) zwykly task 'jar'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SpringBootPluginTrioList {
        /*
         * 🧪 Zadanie 6:
         * Wypisz 2 pluginy zawsze uzywane razem ze Spring Boot w Gradle
         * (org.springframework.boot i io.spring.dependency-management) z jednozdaniowym
         * opisem roli kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckstylePurposeExplanation {
        /*
         * 🧪 Zadanie 7:
         * Wypisz jedno zdanie wyjasniajace, do czego sluzy plugin Checkstyle (kontrola
         * stylu/jakosci kodu, np. dlugosc linii, nieuzywane importy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CheckstyleTasksNames {
        /*
         * 🧪 Zadanie 8:
         * Wypisz 2 taski dodawane przez plugin checkstyle (checkstyleMain,
         * checkstyleTest) z jednozdaniowym opisem, do czego kazdy sie odnosi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PluginPortalVsMavenCentralExplanation {
        /*
         * 🧪 Zadanie 9:
         * Wypisz jedna linie wyjasniajaca roznice miedzy repozytorium dla zaleznosci
         * aplikacji (Maven Central via repositories{}) i repozytorium dla pluginow
         * (Gradle Plugin Portal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_QualityGateComponentsList {
        /*
         * 🧪 Zadanie 10:
         * Wypisz 3 komponenty, ktore moga skladac sie na "quality gate" podwiazany do
         * taska 'check' (testy, jacocoTestCoverageVerification, checkstyleMain/Test).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullShadowJarConfigWithManifest {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj kompletny String build.gradle z pluginem Shadow, jedna zaleznoscia
         * implementation, i konfiguracja shadowJar{} (archiveClassifier + manifest z
         * Main-Class). Zapisz do pliku w temp dir i wypisz zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FullBootJarConfigWithDisabledJar {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj kompletny String build.gradle z pluginami Spring Boot (oba potrzebne),
         * konfiguracja bootJar{} (archiveFileName + mainClass), i jar { enabled = false }.
         * Wypisz go na konsoli z komentarzem wyjasniajacym KAZDA linie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CheckstyleConfigFileGenerator {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj String minimalnego checkstyle.xml z co najmniej 3 modulami reguł
         * (np. UnusedImports, LineLength, ConstantName). Zapisz go do pliku
         * config/checkstyle/checkstyle.xml w temp dir (Files.createDirectories) i wypisz
         * zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ThreePluginsInOneBuildGradle {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj JEDEN String build.gradle laczacy WSZYSTKIE 3 pluginy z lekcji (Shadow,
         * jesli sensownie razem z Spring Boot - albo jako alternatywy w komentarzu -
         * ORAZ Checkstyle) - wypisz go z komentarzami wyjasniajacymi ewentualne konflikty
         * (np. Shadow + Spring Boot rzadko razem, bo Spring Boot ma wlasny mechanizm).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_IllustrativeCheckstyleViolationOutput {
        /*
         * 🧪 Zadanie 15:
         * Wypisz WLASNY ILUSTRACYJNY output './gradlew checkstyleMain' z co najmniej 2
         * naruszeniami (np. LineLength, UnusedImports) z nazwa pliku i numerem linii -
         * jasno oznaczony jako ilustracyjny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ArchiveClassifierNamingDemo {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode buildJarFileName(String baseName, String version, String
         * classifier), ktora generuje nazwe pliku JAR (np. "app-1.0.0-all.jar" gdy
         * classifier="all", "app-1.0.0.jar" gdy classifier jest pusty/null). Przetestuj
         * na 3 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PluginVersionCompatibilityMatrix {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj Map<String, String> "plugin -> minimalna wymagana wersja Gradle" (np.
         * Shadow 8.x wymaga Gradle 8+, Checkstyle wymaga Gradle jakiegos minimum).
         * Napisz metode isCompatible(String pluginVersion, String gradleVersion), ktora
         * (uproszczonym porownaniem numerycznym) sprawdza zgodnosc - przetestuj na 3 parach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_QualityGateAggregatorSimulation {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj wynik 3 skladowych quality gate (testy: PASS/FAIL, checkstyle: liczba
         * naruszen, coverage: procent) jako proste zmienne. Napisz metode
         * evaluateOverallGate(...), ktora zwraca laczny werdykt PASS/FAIL na podstawie
         * WSZYSTKICH trzech (fail-fast na pierwszym niespelnionym) i wypisuje raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ShadowRelocationExplanation {
        /*
         * 🧪 Zadanie 19:
         * Wypisz jedno zdanie wyjasniajace mechanizm "relocate" w Shadow Plugin (zmiana
         * pakietow zagniezdzonych zaleznosci, zeby uniknac konfliktow nazw klas przy
         * laczeniu wielu bibliotek w jeden fat JAR) i zbuduj przykladowy fragment
         * shadowJar { relocate 'org.old.pkg', 'shadow.org.old.pkg' }.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullMultiPluginProjectOnDisk {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj na dysku (temp dir) kompletny projekt z build.gradle laczacym plugin
         * java + checkstyle, konfiguracja config/checkstyle/checkstyle.xml, i katalogi
         * src/main/java + src/test/java. Wypisz drzewo utworzonych plikow (Files.walk).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ShadowVsBootJarDecisionTree {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode recommendFatJarStrategy(boolean isSpringBootApp, boolean
         * needsPackageRelocation, boolean isPlainJavaApp), ktora zwraca rekomendacje
         * (Shadow/bootJar/zwykly jar) na podstawie flag - przetestuj na 4 kombinacjach,
         * z uzasadnieniem kazdej rekomendacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CheckstyleRuleSeverityClassifier {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj liste "naruszen" (rekord: reguła, plik, linia, severity: ERROR/WARNING)
         * z co najmniej 6 wpisami. Napisz metode groupBySeverity(...), ktora grupuje je i
         * decyduje (na podstawie maxWarnings=0 z lekcji), czy build POWINIEN zawiesc -
         * wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FatJarSizeEstimator {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj Map<String, Long> "zaleznosc -> rozmiar w bajtach" (min. 5 fikcyjnych
         * zaleznosci) i napisz metode estimateFatJarSize(long ownCodeSize,
         * Map<String,Long> dependencySizes), ktora sumuje wszystko i formatuje wynik w
         * czytelnych jednostkach (KB/MB). Porownaj z rozmiarem "cienkiego" JAR-a (tylko ownCodeSize).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PluginApplicationOrderSensitivity {
        /*
         * 🧪 Zadanie 24:
         * Napisz (w komentarzu + prostym kodzie symulujacym) analize scenariusza:
         * kolejnosc aplikowania pluginow w bloku plugins{} - czy MA znaczenie kolejnosc
         * miedzy 'java' i 'checkstyle'/'jacoco'? Zbuduj 2 warianty kolejnosci jako
         * Stringi build.gradle i wypisz wniosek (w tym przypadku NIE ma znaczenia - to
         * WAZNE zeby to jasno zaznaczyc, w odroznieniu od np. kolejnosci w dependencies{}
         * przy resolutionStrategy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CustomTaskWrappingShadowJar {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj String custom taska "buildAndVerifyFatJar", ktory dependsOn("shadowJar")
         * i w doLast{} wypisuje sciezke do wygenerowanego JAR-a oraz jego (symulowany)
         * rozmiar - polaczenie techniki custom tasks z Lekcji 17 z Shadow Plugin z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiEnvironmentCheckstyleSeverity {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj 2 warianty konfiguracji checkstyle{} (dev: maxWarnings = 50, wiecej
         * tolerancji; ci/prod: maxWarnings = 0, quality gate ostry) jako Stringi build.gradle,
         * przelaczane na podstawie symulowanej zmiennej "CI" (System.getenv("CI") realnie
         * odczytane). Wypisz, KTORY wariant zostalby uzyty w tym srodowisku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PluginDependencyGraphVisualization {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Map<String, List<String>> reprezentujaca zaleznosci MIEDZY taskami
         * dodanymi przez pluginy z tej lekcji (np. "shadowJar" -> ["compileJava"], "check"
         * -> ["test", "checkstyleMain", "checkstyleTest"]). Napisz metode wypisujaca
         * PELNY graf jako czytelne drzewo tekstowe (z wciecami wg poziomu zaglebienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ShadowJarConflictingDependenciesSimulator {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj liste zaleznosci (nazwy JAR-ow) z ROZNYMI wersjami tej samej biblioteki
         * transytywnie wciagnietymi do fat JAR-a. Napisz metode detectDuplicateClasses(...),
         * ktora symuluje wykrywanie duplikatow klas przy budowaniu shadowJar (typowy
         * realny problem) i wypisuje ostrzezenia, ktore JAR-y "kolidują".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CodeQualityTrendDashboard {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj historie (min. 5 punktow czasowych) 3 metryk jakosci (liczba naruszen
         * checkstyle, procent pokrycia JaCoCo z Lekcji 19, liczba testow) i napisz metode
         * generujaca prosty tekstowy "dashboard" (ASCII) prezentujacy trend KAZDEJ metryki
         * (rosnie/spada/stabilna) - laczac wszystkie pluginy jakosci poznane w rozdziale
         * Gradle w jeden raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstonePluginsEcosystemProject {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY projekt Gradle laczacy WSZYSTKIE
         * pluginy z rozdzialu (java, application, jacoco z Lekcji 19, checkstyle z tej
         * lekcji, i Shadow LUB Spring Boot - Twoj wybor, z uzasadnieniem czemu WYBRALES
         * jeden a nie drugi) w jednym spojnym build.gradle. Zapisz go razem z
         * config/checkstyle/checkstyle.xml na dysku, wypisz cala strukture plikow
         * (Files.walk) i podsumuj (komentarzem), jak WSZYSTKIE taski (test, jacocoTestReport,
         * checkstyleMain, shadowJar/bootJar) skladaja sie na jeden 'build'/'check'.
         */
        public static void main(String[] args) { }
    }
}
