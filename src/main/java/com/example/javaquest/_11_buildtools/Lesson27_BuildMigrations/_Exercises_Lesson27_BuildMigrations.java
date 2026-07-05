package com.example.javaquest._11_buildtools.Lesson27_BuildMigrations;

public class _Exercises_Lesson27_BuildMigrations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IdentifyAntTargetsPrint {
        /*
         * 🧪 Zadanie 1:
         * Dla build.xml z lekcji (targety clean, compile, jar) wypisz 3 linie: nazwa
         * targetu -> co dokladnie robi (na podstawie tresci tasku wewnatrz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CleanTargetMapping {
        /*
         * 🧪 Zadanie 2:
         * Wypisz jedna linie mapujaca target Ant "clean" na jego odpowiedniki: "mvn
         * clean" (Maven) i "./gradlew clean" (Gradle) - z komentarzem, ze oba sa WBUDOWANE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompileTargetMapping {
        /*
         * 🧪 Zadanie 3:
         * Wypisz jedna linie mapujaca target Ant "compile" (javac srcdir/destdir) na
         * faze Maven "compile" i task Gradle "compileJava".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_JarTargetMapping {
        /*
         * 🧪 Zadanie 4:
         * Wypisz jedna linie mapujaca target Ant "jar" (depends="compile") na faze
         * Maven "package" + maven-jar-plugin oraz task Gradle "jar" (dependsOn compileJava).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DirectoryStructureMigrationPrint {
        /*
         * 🧪 Zadanie 5:
         * Wypisz porownanie: plaski katalog "src" (typowy dla Anta) vs "src/main/java"
         * (konwencja Maven/Gradle) - i wyjasnij jednym zdaniem, czemu trzeba przeniesc pliki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LibJarToMavenCoordinatesExample {
        /*
         * 🧪 Zadanie 6:
         * Dla pliku "lib/gson-2.11.0.jar" wypisz jego domyslne koordynaty Maven
         * (com.google.code.gson:gson:2.11.0) jako przyklad "detektywistycznej" pracy
         * potrzebnej przy realnej migracji Ant->Maven/Gradle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ManifestMainClassMapping {
        /*
         * 🧪 Zadanie 7:
         * Wypisz porownanie deklaracji Main-Class: <manifest><attribute name="Main-Class"
         * value="..."/> (Ant) vs <archive><manifest><mainClass> (Maven, maven-jar-plugin)
         * vs application { mainClass = '...' } (Gradle).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MavenAntrunPluginPurpose {
        /*
         * 🧪 Zadanie 8:
         * Wypisz jednozdaniowe wyjasnienie, do czego sluzy maven-antrun-plugin i w jakim
         * scenariuszu migracji Ant->Maven jest przydatny (custom targety bez odpowiednika).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_GradleFilesFunctionExplanation {
        /*
         * 🧪 Zadanie 9:
         * Wypisz jednozdaniowe wyjasnienie funkcji files('lib/x.jar') w Gradle jako
         * "latki" na czas migracji, zanim znajdzie sie prawidlowe koordynaty Maven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MultiModuleVsMultiProjectTerminology {
        /*
         * 🧪 Zadanie 10:
         * Wypisz jedna linie porownujaca terminologie: "modul" + <modules> w Mavenie vs
         * "podprojekt" + include(...) w settings.gradle w Gradle.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenerateLegacyBuildXmlVariant {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj WLASNY (inny niz w lekcji) String build.xml z 4 targetami: clean,
         * compile, test (uzyj <junit> koncepcyjnie w komentarzu), jar. Wypisz go na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MigrateOwnBuildXmlToPom {
        /*
         * 🧪 Zadanie 12:
         * Na bazie build.xml z Zadania 11, wygeneruj odpowiadajacy mu String pom.xml
         * (z maven-jar-plugin, maven-surefire-plugin dla testow) i wypisz obok siebie
         * (build.xml, potem pom.xml) z komentarzami mapujacymi kazdy target.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MigrateOwnBuildXmlToGradle {
        /*
         * 🧪 Zadanie 13:
         * Na bazie tego samego build.xml z Zadania 11, wygeneruj odpowiadajacy mu String
         * build.gradle (plugin java, task test wbudowany) i wypisz z komentarzami
         * mapujacymi kazdy target na task Gradle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DependencyCoordinatesLookupTable {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj Map<String,String> "lib/*.jar -> koordynaty Maven" dla 4 fikcyjnych
         * JAR-ow z lib/ (np. commons-io, log4j, itp.) i napisz metode generateDependenciesBlock(),
         * ktora na jej podstawie generuje sekcje <dependencies> pom.xml ORAZ dependencies{}
         * build.gradle - wypisz obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ScopeToConfigurationMappingTable {
        /*
         * 🧪 Zadanie 15:
         * Wypisz kompletna tabele mapujaca 4 scope'y Maven (compile, provided, runtime,
         * test) na odpowiadajace im konfiguracje Gradle (implementation/api, compileOnly,
         * runtimeOnly, testImplementation) - jedna linia na scope.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MavenToGradleDependencyTranslator {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode translateMavenDependencyToGradle(String groupId, String
         * artifactId, String version, String scope), ktora zwraca odpowiedni String
         * Gradle (np. "implementation 'g:a:v'") na podstawie mapowania scope z Zadania
         * 15. Przetestuj na 4 przykladach z roznymi scope'ami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CustomAntTaskMigrationChallenge {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj String fragmentu build.xml z CUSTOM targetem uzywajacym <script>
         * (np. generujacym plik version.txt z data budowania). Napisz komentarz-analize:
         * jak przeniesc to do Mavena (maven-antrun-plugin albo plugin generate-resources)
         * i jak do Gradle (zwykly doLast {} custom taska) - z przykladowym kodem Gradle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultiModuleMigrationSkeleton {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj String pom.xml rodzica z <modules>core</modules><modules>app</modules>
         * (multi-module, z Lesson14) i odpowiadajacy mu String settings.gradle z
         * include("core", "app") (multi-project) - wypisz oba i skomentuj glowna roznice
         * (osobny plik settings.gradle vs modules w tym samym pom.xml).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LifecyclePhaseToGradleTaskGraphExplanation {
        /*
         * 🧪 Zadanie 19:
         * Wypisz wyjasnienie (min. 3 punkty) roznicy miedzy SEKWENCYJNYM lifecycle
         * Mavena (validate->compile->test->package) i GRAFEM ZALEZNOSCI taskow Gradle -
         * z przykladem sytuacji, gdzie graf pozwala na wieksza elastycznosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReverseGradleToMavenFeasibilityCheck {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode isFeasibleToMigrateToMaven(int customTaskCount, boolean
         * usesBuildCache, int subprojectCount), ktora na podstawie prostych progow
         * (np. customTaskCount > 3 -> false) zwraca ocene, czy migracja Gradle->Maven
         * jest realistyczna, razem z uzasadnieniem (String). Przetestuj na 3 scenariuszach.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullAntToMavenMigrationPipeline {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode migrateAntToMaven(String buildXmlContent), ktora (uproszczonym
         * parsowaniem Stringow, bez pelnego XML parsera) wykrywa nazwy targetow (szuka
         * "<target name=") i wypisuje sugerowane mapowanie kazdego na faze/plugin Maven
         * (na podstawie prostej tabeli target->faza w kodzie). Przetestuj na build.xml z
         * lekcji i na wlasnym z Zadania 11.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullAntToGradleMigrationPipeline {
        /*
         * 🧪 Zadanie 22:
         * Analogicznie do Zadania 21, napisz migrateAntToGradle(String buildXmlContent),
         * ktora generuje String szkicu build.gradle z custom taskami o TYCH SAMYCH
         * nazwach co wykryte targety Ant (jako "punkt startowy" dalszej recznej migracji) -
         * kazdy z komentarzem TODO wskazujacym, co przeniesc z oryginalnego targetu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DependencyReconciliationAcrossThreeTools {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj 3 niezalezne listy "zaleznosci wykrytych" w build.xml (nazwy JAR-ow z
         * lib/), pom.xml (parsed <dependency> - tu jako Stringi), build.gradle
         * (parsed implementation - tu jako Stringi) dla TEGO SAMEGO logicznie projektu
         * po migracji przez wszystkie etapy. Napisz metode, ktora wykrywa NIEZGODNOSCI
         * (zaleznosc obecna w jednym pliku, brakujaca w innym) i wypisuje raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MigrationRiskScoringSystem {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj system oceny ryzyka migracji: metoda scoreMigrationRisk(int
         * customTargetCount, int nonStandardDirCount, int missingMavenCentralLibCount),
         * ktora zwraca liczbowy wynik ryzyka (np. suma z wagami) i klasyfikacje
         * (NISKIE/SREDNIE/WYSOKIE). Przetestuj na co najmniej 3 roznych profilach
         * projektow (od "latwej" do "bardzo ryzykownej" migracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BidirectionalMappingTableValidator {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj DWIE mapy: Ant target -> Maven phase i Maven phase -> Gradle task (na
         * podstawie tabel z lekcji). Napisz metode chainMapping(String antTarget), ktora
         * przechodzi przez OBIE mapy i zwraca finalny task Gradle dla danego targetu Ant
         * (tranzytywne mapowanie Ant -> Maven -> Gradle). Przetestuj dla 3 targetow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CustomAntLogicComplexityClassifier {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode classifyCustomLogicComplexity(String targetXml), ktora na
         * podstawie obecnosci konkretnych tagow (<script>, <exec>, <macrodef>) w
         * fragmencie XML klasyfikuje zlozonosc migracji custom targetu jako
         * PROSTA/SREDNIA/TRUDNA wraz z sugerowanym podejsciem migracji dla kazdego
         * poziomu. Przetestuj na 3 przykladowych fragmentach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ThreeWayBuildEquivalenceTest {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj po migracji 3 rownolegle configi (build.xml, pom.xml, build.gradle) dla
         * TEGO SAMEGO malego projektu (z jedna zaleznoscia, packaging jar, jedna klasa
         * Main). Napisz metode "sprawdzajaca rownowaznosc koncepcyjna" - lista kluczowych
         * elementow (groupId/nazwa, wersja, zaleznosc, main class) i weryfikacje, ze
         * WSZYSTKIE 3 pliki zawieraja (jako podciag tekstowy) te sama wartosc dla kazdego elementu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigrationTimeEstimator {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode estimateMigrationHours(int targetCount, int customTargetCount,
         * int dependencyCount, boolean hasNonStandardStructure), ktora zwraca szacowana
         * liczbe godzin migracji na podstawie prostego wzoru (np. targetCount * 0.5h +
         * customTargetCount * 4h + dependencyCount * 0.25h + bonus za niestandardowa
         * strukture). Przetestuj na 3 roznych profilach projektow i wypisz uzasadnienie wzoru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RollbackPlanGenerator {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode generateRollbackPlan(String fromTool, String toTool), ktora
         * generuje (jako lista Stringow) plan awaryjnego powrotu do poprzedniego
         * narzedzia budowania, gdyby migracja sie nie powiodla (np. "zachowaj oryginalny
         * build.xml w katalogu legacy/", "oznacz tag w Git przed migracja", "uruchom
         * oba buildy rownolegle na CI przez okres przejsciowy"). Wywolaj dla 3 par
         * narzedzi (Ant->Maven, Ant->Gradle, Maven->Gradle).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMigrationReport {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: dla build.xml z lekcji wygeneruj KOMPLETNY raport
         * migracji obejmujacy WSZYSTKIE 3 sciezki (Ant->Maven, Ant->Gradle, i - jako
         * "co by bylo, gdyby zaczynac od Mavena" - Maven->Gradle) w jednym, spojnym,
         * sformatowanym tekscie z naglowkami sekcji, tabelami mapowan i finalna
         * rekomendacja "ktora sciezka jest najbardziej sensowna dla TEGO konkretnego
         * projektu" z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }
}
