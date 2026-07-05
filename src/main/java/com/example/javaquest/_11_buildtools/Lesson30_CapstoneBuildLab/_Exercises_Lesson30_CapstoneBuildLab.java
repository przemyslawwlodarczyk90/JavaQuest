package com.example.javaquest._11_buildtools.Lesson30_CapstoneBuildLab;

public class _Exercises_Lesson30_CapstoneBuildLab {

    // 🟢 POZIOM 1 – WERSJA ANT, KROK PO KROKU (1-10)

    static class Exercise01_CreateProjectStructure {
        /*
         * 🧪 Zadanie 1:
         * Utworz na dysku (Files.createDirectories, w katalogu tymczasowym) strukture
         * katalogow dla "Build Lab": src/main/java/com/example/buildlab (+ podpakiety
         * model, repository, service), src/main/resources, src/test/java/com/example/buildlab.
         * Wypisz drzewo utworzonych katalogow (Files.walk).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteProduktModel {
        /*
         * 🧪 Zadanie 2:
         * Napisz (jako String, zapisany do pliku Produkt.java w strukturze z Zadania 1)
         * klase modelu Produkt (id, nazwa, cena) z lekcji - identyczna albo z WLASNYMI,
         * dodatkowymi polami (np. kategoria). Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteProduktRepository {
        /*
         * 🧪 Zadanie 3:
         * Napisz (jako String, zapisany do pliku) klase ProduktRepository z lekcji
         * (in-memory lista, zapisz/znajdzWszystkie) - mozesz dodac WLASNA metode (np.
         * znajdzPoId(int id)). Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteProduktService {
        /*
         * 🧪 Zadanie 4:
         * Napisz (jako String, zapisany do pliku) klase ProduktService z lekcji
         * (dodajProdukt, obliczSumaCen, pobierzWszystkie) - dodaj WLASNA metode biznesowa
         * (np. znajdzNajdrozszy()). Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteConfigProperties {
        /*
         * 🧪 Zadanie 5:
         * Napisz (jako String, zapisany do src/main/resources/config.properties) plik
         * konfiguracyjny z app.name, app.version, env - z WLASNYMI wartosciami (inna
         * nazwa apki niz w lekcji). Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMainWithGson {
        /*
         * 🧪 Zadanie 6:
         * Napisz (jako String, zapisany do pliku) klase Main, ktora odczytuje
         * config.properties, dodaje co najmniej 4 produkty (INNE niz w lekcji), wypisuje
         * raport, i serializuje liste produktow do JSON przy pomocy Gson. Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteSelfCheckMain {
        /*
         * 🧪 Zadanie 7:
         * Napisz (jako String, zapisany do pliku) klase SelfCheckMain (jak w lekcji, ale
         * z WLASNYMI, INNYMI asercjami - np. sprawdzenie znajdzNajdrozszy() z Zadania 4)
         * zwracajaca kod wyjscia 1 przy niepowodzeniu. Wypisz zawartosc pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuildAntCleanInitCompile {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj String build.xml z TYLKO 3 pierwszymi targetami z lekcji: clean, init,
         * compile (z classpath do gson.jar - zlokalizuj go w ~/.m2/repository jak w
         * lekcji). Osadz go embedowanym Antem (Project/ProjectHelper/DefaultLogger) i
         * wykonaj REALNIE target "compile" na plikach z Zadan 2-4. Wypisz output Anta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExtendAntCopyResourcesCompileTestTest {
        /*
         * 🧪 Zadanie 9:
         * Rozszerz build.xml z Zadania 8 o targety copy-resources, compile-test, test
         * (uzywajac SelfCheckMain z Zadania 7) - z TAKIMI SAMYMI depends jak w lekcji.
         * Wykonaj REALNIE target "test" (co wywola cala pozostala sciezke zaleznosci) i
         * wypisz output - w tym komunikat SelfCheckMain o powodzeniu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExtendAntJarDistRun {
        /*
         * 🧪 Zadanie 10:
         * Dokoncz build.xml z Zadania 9 o targety jar (z Main-Class i Class-Path w
         * manifescie), dist (kopiujacy gson jar obok) i run. Wykonaj REALNIE target
         * "dist", a potem odpal wynikowy JAR PRAWDZIWYM ProcessBuilderem (jak w lekcji) -
         * wypisz jego output. To Twoja WLASNA, kompletna wersja Ant kapstonu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – WERSJA MAVEN, KROK PO KROKU (11-20)

    static class Exercise11_GeneratePomSkeleton {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj String minimalnego pom.xml dla "build-lab" (groupId/artifactId/version,
         * packaging jar, wlasciwosci compiler source/target 21) - BEZ jeszcze zaleznosci
         * i pluginow. Wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddGsonAndJUnitDependencies {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz pom.xml z Zadania 11 o sekcje <dependencies> z gson (2.11.0) i
         * junit-jupiter (5.10.2, scope test) - jak w lekcji. Wypisz caly plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddCompilerAndSurefirePlugins {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz pom.xml z Zadania 12 o <build><plugins> z maven-compiler-plugin i
         * maven-surefire-plugin (bez konfiguracji - domyslne ustawienia, jak w lekcji).
         * Wypisz caly plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddShadePluginConfig {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz pom.xml z Zadania 13 o maven-shade-plugin z ManifestResourceTransformer
         * ustawiajacym mainClass na com.example.buildlab.Main (jak w lekcji) - podwiazany
         * do fazy 'package'. Wypisz caly plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddDevProdProfiles {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz pom.xml z Zadania 14 o <profiles> z 'dev' (activeByDefault) i 'prod'
         * (z wlasciwoscia app.env) - jak w lekcji. Wypisz KOMPLETNY, finalny pom.xml
         * (odpowiadajacy 1:1 wersji z teorii lekcji, ale zbudowany PRZEZ CIEBIE krok po kroku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MavenJUnit5TestClassForSameLogic {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj String KLASY TESTOWEJ JUnit 5 (adnotacje @Test, asercje
         * org.junit.jupiter.api.Assertions) sprawdzajacej TA SAMA logike co SelfCheckMain
         * z Zadania 7 (suma cen, liczba produktow) - ale prawdziwym frameworkiem JUnit 5,
         * jak wskazano w komentarzu teorii lekcji, ze Maven/Gradle moglyby to odpalic
         * naprawde. Wypisz kod testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MvnCommandsCheatsheet {
        /*
         * 🧪 Zadanie 17:
         * Wypisz "sciagawke" komend mvn dla tego projektu: 'mvn compile', 'mvn test',
         * 'mvn package' (domyslny profil dev), 'mvn package -Pprod', 'mvn clean package' -
         * kazda z jednozdaniowym opisem, co faktycznie sie stanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MavenVsAntTargetMappingForThisProject {
        /*
         * 🧪 Zadanie 18:
         * Wypisz TABELE mapujaca KAZDY target z Twojej wersji Ant (Zadania 8-10:
         * clean/init/compile/copy-resources/compile-test/test/jar/dist/run) na
         * odpowiadajacy element Maven (faza lifecycle albo plugin) - dla TEGO KONKRETNEGO
         * projektu (nie ogolnie, jak w Lekcji 27 - tu z odniesieniem do WLASNYCH plikow
         * napisanych w tym zadaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ReferenceRichenMavenTestingPublishing {
        /*
         * 🧪 Zadanie 19:
         * Rozszerz pom.xml z Zadania 15 o konfiguracje maven-surefire-plugin z
         * WLASNYMI ustawieniami (np. <includes> tylko klas koncoczych sie na "Test") -
         * odwolujac sie do technik z bogatszych lekcji o testach Maven (jesli byly
         * poznane w tym rozdziale). Wypisz zaktualizowany plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ValidateMavenVersionEquivalence {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode, ktora sprawdza (prostym porownaniem tekstowym), ze finalny
         * pom.xml z Zadania 15/19 zawiera WSZYSTKIE kluczowe elementy z teoretycznej
         * wersji lekcji (groupId, gson, shade plugin, profile dev/prod) - wypisz raport
         * "kompletnosci" Twojej wersji Maven kapstonu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – WERSJA GRADLE, KROK PO KROKU (21-30)

    static class Exercise21_GenerateSettingsGradleWithCatalogReference {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj String settings.gradle dla 'build-lab' z rootProject.name i
         * dependencyResolutionManagement { repositories { mavenCentral() } } - z
         * komentarzem odwolujacym sie do version catalog (Lesson21_GradleDependencyManagement)
         * jako "TODO: dodac gradle/libs.versions.toml". Wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenerateBuildGradleJavaApplication {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj String build.gradle z pluginami 'java' + 'application', group/version,
         * i dependencies{} z gson (jako surowy String 'g:a:v', BEZ jeszcze version
         * catalog) - jak najprostsza mozliwa wersja. Wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AddApplicationMainClassAndTestBlock {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz build.gradle z Zadania 22 o application { mainClass = '...' } i test {
         * useJUnitPlatform() } (patrz Lesson22_GradleTestingAndCoverage dla pelnej
         * konfiguracji, jesli chcesz dodac wiecej - np. testLogging). Wypisz zaktualizowany plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AddShadowPluginAndConfig {
        /*
         * 🧪 Zadanie 24:
         * Rozszerz build.gradle z Zadania 23 o plugin Shadow (com.gradleup.shadow) i blok
         * shadowJar{} z archiveClassifier i Main-Class w manifescie - jak w teorii lekcji.
         * Wypisz zaktualizowany plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AddCustomPrintInfoTask {
        /*
         * 🧪 Zadanie 25:
         * Rozszerz build.gradle z Zadania 24 o custom task 'printInfo' (tasks.register)
         * wypisujacy nazwe projektu, wersje i sciezke do Main-Class - jak w lekcji, ale z
         * WLASNA, dodatkowa linia informacji (np. liczba zaleznosci). Wypisz KOMPLETNY,
         * finalny build.gradle (odpowiadajacy 1:1 wersji z teorii lekcji, zbudowany przez
         * Ciebie krok po kroku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigrateGsonToVersionCatalog {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj gradle/libs.versions.toml z wpisem dla gson (i opcjonalnie
         * junit-jupiter), i ZAKTUALIZUJ dependencies{} z build.gradle (Zadanie 25), zeby
         * uzywac "implementation libs.gson" zamiast surowego Stringa - odwolujac sie do
         * technik z Lesson21_GradleDependencyManagement. Wypisz oba pliki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GradleJUnit5TestClassForSameLogic {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj String KLASY TESTOWEJ JUnit 5 (analogiczna do Zadania 16, moze byc
         * dosłownie tym samym kodem - JUnit 5 dziala identycznie pod Maven i Gradle) i
         * dodaj komentarz wyjasniajacy, ze KONFIGURACJA (build.gradle test{} vs pom.xml
         * surefire) sie rozni, ale KOD testu jest identyczny miedzy narzedziami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GradleCommandsCheatsheet {
        /*
         * 🧪 Zadanie 28:
         * Wypisz "sciagawke" komend ./gradlew dla tego projektu: './gradlew build',
         * './gradlew test', './gradlew shadowJar', './gradlew run', './gradlew printInfo' -
         * kazda z jednozdaniowym opisem, co faktycznie sie stanie (wszystkie oznaczone
         * jako ILUSTRACYJNE - ten wrapper nie istnieje w tym repo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GradleVsMavenVsAntTargetMappingForThisProject {
        /*
         * 🧪 Zadanie 29:
         * Wypisz TABELE mapujaca KAZDY task z Twojej wersji Gradle (Zadania 22-26) na
         * odpowiadajacy target Ant (Zadania 8-10) I fazes/plugin Maven (Zadania 11-15) -
         * dla TEGO KONKRETNEGO projektu, laczac WSZYSTKIE trzy wersje, ktore napisales w
         * tym zadaniu w JEDNA, finalna tabele porownawcza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneOfCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zadanie finalne calego rozdzialu: zbierz WSZYSTKIE 3 kompletne wersje "Build
         * Lab" napisane przez Ciebie w tym pliku (Ant z Zadan 1-10, Maven z Zadan 11-20,
         * Gradle z Zadan 21-29) i napisz metode generateFinalReport(), ktora wypisuje
         * JEDEN, spojny raport koncowy: (1) potwierdza, ze wszystkie 3 wersje opisuja TEN
         * SAM projekt (te same klasy, ta sama logika biznesowa), (2) wypisuje finalna
         * tresc WSZYSTKICH wygenerowanych plikow (build.xml, pom.xml, build.gradle +
         * settings.gradle + libs.versions.toml) jeden po drugim z naglowkami, (3)
         * zamyka rozdzial krotkim podsumowaniem (3-4 zdania) tego, czego nauczyles sie
         * w calym rozdziale _11_buildtools o Ant/Maven/Gradle.
         */
        public static void main(String[] args) { }
    }
}
