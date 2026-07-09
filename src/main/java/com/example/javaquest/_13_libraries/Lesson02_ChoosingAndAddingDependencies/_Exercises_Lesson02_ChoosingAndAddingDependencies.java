package com.example.javaquest._13_libraries.Lesson02_ChoosingAndAddingDependencies;

public class _Exercises_Lesson02_ChoosingAndAddingDependencies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteMavenDependencyBlockForCommonsIo {
        /*
         * 🧪 Zadanie 1:
         * W komentarzu napisz poprawny blok <dependency> dla Mavena dla
         * biblioteki "commons-io:commons-io:2.18.0" (uzywanej w tym projekcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteGradleDependencyLineForCaffeine {
        /*
         * 🧪 Zadanie 2:
         * W komentarzu napisz odpowiednik w Gradle (implementation '...') dla
         * zaleznosci "com.github.ben-manes.caffeine:caffeine:3.1.8".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OpenPomXmlAndListAllDependencies {
        /*
         * 🧪 Zadanie 3:
         * Otworz pom.xml TEGO projektu i wypisz w komentarzu WSZYSTKIE
         * bezposrednie zaleznosci (groupId:artifactId) w sekcji <dependencies>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IdentifyGroupIdArtifactIdVersion {
        /*
         * 🧪 Zadanie 4:
         * Dla wpisu "org.apache.poi:poi-ooxml:5.3.0" rozbij w komentarzu na
         * groupId, artifactId, version i wyjasnij jednym zdaniem, co kazda
         * czesc oznacza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FindLombokScopeInPomXml {
        /*
         * 🧪 Zadanie 5:
         * Otworz pom.xml, znajdz zaleznosc lombok i zapisz w komentarzu jej
         * <scope> oraz wyjasnij (na podstawie teorii lekcji), dlaczego akurat
         * TEN scope zostal uzyty dla Lomboka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RunDependencyTreeInTerminal {
        /*
         * 🧪 Zadanie 6:
         * W terminalu, w katalogu projektu, uruchom "mvnw.cmd dependency:tree"
         * (lub "mvnw.cmd dependency:tree -Dincludes=com.google.guava") - w
         * komentarzu zapisz fragment outputu dotyczacy Guava.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainCompileVsProvidedScope {
        /*
         * 🧪 Zadanie 7:
         * W komentarzu wyjasnij roznice miedzy scope "compile" a "provided" -
         * podaj po jednym przykladzie biblioteki z KAZDEGO scope z tego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SearchMvnrepositoryForOkhttp {
        /*
         * 🧪 Zadanie 8:
         * Na mvnrepository.com wyszukaj "com.squareup.okhttp3:okhttp" - w
         * komentarzu zapisz najnowsza dostepna wersje i porownaj z wersja
         * uzyta w pom.xml tego projektu (4.12.0) - czy jest nowsza?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteExclusionBlockExample {
        /*
         * 🧪 Zadanie 9:
         * W komentarzu napisz przykladowy blok <exclusions> wykluczajacy
         * zaleznosc "org.slf4j:slf4j-log4j12" z hipotetycznej biblioteki
         * "org.example:legacy-lib:2.0.0".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifyBomInThisProject {
        /*
         * 🧪 Zadanie 10:
         * Otworz pom.xml tego projektu i znajdz sekcje <parent> - w komentarzu
         * zapisz, jaki BOM jest uzyty i wymien 2 zaleznosci z pom.xml, ktore
         * NIE maja jawnie podanej wersji (bo zarzadza nia ten BOM).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompareVersionsAcrossTwoDependencies {
        /*
         * 🧪 Zadanie 11:
         * Uruchom "mvnw.cmd dependency:tree" w terminalu i sprawdz, czy w
         * calym drzewie wystepuja DWIE ROZNE wersje TEJ SAMEJ biblioteki
         * (np. slf4j-api) - zapisz wynik w komentarzu (jesli nie znajdziesz
         * konfliktu w tym projekcie, opisz jak wygladalby taki przypadek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TraceTransitiveDependencyOrigin {
        /*
         * 🧪 Zadanie 12:
         * Na podstawie "mvnw.cmd dependency:tree" znajdz, KTORA bezposrednia
         * zaleznosc projektu ciagnie za soba "org.yaml:snakeyaml" - zapisz w
         * komentarzu pelna sciezke w drzewie (od korzenia do snakeyaml).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignExclusionForConflictingLogger {
        /*
         * 🧪 Zadanie 13:
         * Zalozmy, ze hipotetyczna biblioteka "acme-client:3.0.0" ciagnie
         * "log4j:log4j:1.2.17" (stara, podatna wersja), a projekt uzywa juz
         * logback. Napisz w komentarzu PELNY blok <dependency> z <exclusions>
         * rozwiazujacy ten konflikt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareMavenCentralVsGithubStars {
        /*
         * 🧪 Zadanie 14:
         * Dla biblioteki Picocli sprawdz LICZBE gwiazdek na GitHubie ORAZ
         * liczbe "Used By" na mvnrepository.com - w komentarzu porownaj oba
         * sygnaly popularnosci i oceń, czy prowadza do tego samego wniosku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteBomImportForHypotheticalProject {
        /*
         * 🧪 Zadanie 15:
         * Bez rodzica typu starter-parent, w komentarzu napisz, jak
         * zaimportowac BOM "com.fasterxml.jackson:jackson-bom:2.18.0" przez
         * <dependencyManagement> z <scope>import</scope> (skladnia z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_EvaluateRuntimeScopeUseCase {
        /*
         * 🧪 Zadanie 16:
         * Wymysl (w komentarzu) realistyczny przyklad zaleznosci, ktora
         * powinna miec scope "runtime" (nie "compile") w projekcie - uzasadnij,
         * dlaczego kod NIE importuje jej wprost, a mimo to jest potrzebna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AuditPomXmlForMissingVersions {
        /*
         * 🧪 Zadanie 17:
         * Przejrzyj CALY pom.xml tego projektu i w komentarzu wypisz WSZYSTKIE
         * zaleznosci BEZ jawnie podanej wersji (zarzadzane przez BOM) - min. 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SimulateVersionConflictResolution {
        /*
         * 🧪 Zadanie 18:
         * Projekt A (bezposrednia zaleznosc) wymaga guava:28.0, a projekt B
         * (tez bezposrednia zaleznosc) wymaga guava:33.4.0-jre. W komentarzu
         * opisz, ktora wersja "wygra" wg reguly Mavena (nearest wins) przy
         * roznych ulozeniach w pom.xml, i jak wymusic KONKRETNA wersje recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareGradleImplementationVsApi {
        /*
         * 🧪 Zadanie 19:
         * Wyszukaj informacje o roznicy miedzy "implementation" a "api" w
         * Gradle (konfiguracje zaleznosci) - w komentarzu wyjasnij, kiedy
         * uzyc ktorej (wplyw na classpath modulow zaleznych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignDependencyUpgradeChecklist {
        /*
         * 🧪 Zadanie 20:
         * Napisz w komentarzu checkliste (min. 6 punktow) do bezpiecznej
         * aktualizacji WERSJI MAJOR biblioteki w projekcie (np. Guava 33 ->
         * 34) - co sprawdzic PRZED, PODCZAS i PO aktualizacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullDependencyTreeAuditOfThisProject {
        /*
         * 🧪 Zadanie 21:
         * Uruchom "mvnw.cmd dependency:tree > tree.txt" w terminalu (poza
         * kodem) i przeanalizuj PELNE drzewo tego projektu - w komentarzu
         * zapisz: liczbe zaleznosci BEZPOSREDNICH vs liczbe WSZYSTKICH
         * (razem z transytywnymi) oraz najglebszy poziom zagniezdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiModuleBomStrategy {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj (w komentarzu, min. 5 krokow) strategie zarzadzania
         * wersjami dla hipotetycznego projektu wielomodulowego (5 modulow) BEZ
         * uzycia starter-parent - jak zbudowac WLASNY BOM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ResolveHypotheticalTripleConflict {
        /*
         * 🧪 Zadanie 23:
         * 3 rozne zaleznosci bezposrednie projektu ciagna TRZY rozne wersje
         * biblioteki "commons-collections4" (4.1, 4.2, 4.4). W komentarzu
         * napisz PELNE rozwiazanie: jak zdiagnozowac (dependency:tree) i jak
         * wymusic JEDNA, konkretna wersje (dependencyManagement).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteFullExclusionChainScenario {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj hipotetyczny lancuch: biblioteka A ciagnie B, B ciagnie
         * niechciana C. W komentarzu napisz PELNY pom.xml (fragment) z
         * wykluczeniem C na poziomie deklaracji A (nie B - wyjasnij dlaczego
         * musi byc na A).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareLicenseAndScopeAcrossAllChapterLibraries {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj (w komentarzu) tabele dla WSZYSTKICH bibliotek z pom.xml
         * dodanych na potrzeby rozdzialu _13_libraries (Lombok, Commons,
         * Guava, OkHttp, Guice, MapStruct, POI, Jsoup, Caffeine, Picocli) z
         * kolumnami: scope, licencja, czy jest transytywna gdzie indziej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignDependencyGovernanceForTeam {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (w komentarzu, min. 8 punktow) proces "governance"
         * zaleznosci dla zespolu 10 programistow - automatyczne skanowanie CVE,
         * polityke akceptacji nowych bibliotek, cykliczny audyt dependency:tree.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigratePomDependencyToExplicitBomImport {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj (w komentarzu) migracje pom.xml TEGO projektu z <parent>
         * spring-boot-starter-parent NA zwykly rodzica + jawny import BOM-u
         * spring-boot-dependencies przez <dependencyManagement> - napisz
         * pelny fragment XML i wyjasnij RYZYKA takiej migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimulateSecurityScanOfDependencyTree {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala (lub z narzedziem typu "mvn org.owasp:dependency-check-maven:check"
         * jesli chcesz sprobowac naprawde): opisz w komentarzu, jak dzialaloby
         * skanowanie CALEGO drzewa zaleznosci projektu pod katem znanych CVE i
         * co zrobilbys, gdyby raport wykazal krytyczna podatnosc w zaleznosci
         * TRANZYTYWNEJ (nie bezposredniej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkBuildTimeImpactOfDependencies {
        /*
         * 🧪 Zadanie 29:
         * Zmierz w terminalu czas "mvnw.cmd clean compile" DLA CALEGO projektu
         * (z wszystkimi zaleznosciami z rozdzialow 01-13) - w komentarzu zapisz
         * wynik i zastanow sie, jak rosnaca liczba zaleznosci wplynela na czas
         * pobierania/kompilacji w porownaniu z hipotetycznym malym projektem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteFullDependencyManagementPlaybook {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz kompletny "playbook zarzadzania
         * zaleznosciami" (min. 10 punktow, w komentarzu) laczacy WSZYSTKIE
         * elementy lekcji - czytanie mvnrepository, dependency:tree, scope,
         * exclusions, BOM, audyt licencji i CVE - jako dokument, ktory
         * nowy czlonek zespolu przeczytalby PRZED pierwszym dodaniem
         * zaleznosci do projektu.
         */
        public static void main(String[] args) { }
    }
}
