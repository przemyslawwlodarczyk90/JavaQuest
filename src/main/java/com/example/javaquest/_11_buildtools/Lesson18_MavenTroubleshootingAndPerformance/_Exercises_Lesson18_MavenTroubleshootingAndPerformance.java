package com.example.javaquest._11_buildtools.Lesson18_MavenTroubleshootingAndPerformance;

public class _Exercises_Lesson18_MavenTroubleshootingAndPerformance {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TriggerTypoPluginError {
        /*
         * 🧪 Zadanie 1:
         * Utwórz nowy projekt Maven i celowo wpisz błędny <artifactId> pluginu w
         * <build><plugins> (literówkę). Uruchom "mvn compile" w terminalu i zapisz w
         * komentarzu dokładną treść błędu, jaki zwrócił Maven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunWithErrorsFlag {
        /*
         * 🧪 Zadanie 2:
         * W projekcie z zadania 1 uruchom "mvn compile -e" i porównaj output z zadaniem 1.
         * Zapisz w komentarzu, jakich dodatkowych informacji dostarcza flaga "-e" (pełny
         * stack trace zamiast skróconego komunikatu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RunWithDebugFlag {
        /*
         * 🧪 Zadanie 3:
         * Uruchom w tym samym projekcie "mvn compile -X" i zapisz w komentarzu (w kilku
         * zdaniach), jak bardzo dłuższy jest ten output względem "mvn compile -e" - i jaką
         * jedną nową informację (np. skanowane repozytoria) zauważyłeś.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EffectivePomFirstLook {
        /*
         * 🧪 Zadanie 4:
         * W KORZENIU repozytorium tego kursu uruchom "mvnw.cmd help:effective-pom" i
         * zapisz w komentarzu, ile linii miał wygenerowany plik (w przybliżeniu) w
         * porównaniu z liczbą linii w samym pom.xml projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DependencyTreeBasic {
        /*
         * 🧪 Zadanie 5:
         * W KORZENIU repozytorium tego kursu uruchom "mvnw.cmd dependency:tree" i zapisz
         * w komentarzu 3 zależności bezpośrednie (poziom 0, bez wcięcia) widoczne w
         * wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TriggerNonResolvableParent {
        /*
         * 🧪 Zadanie 6:
         * Utwórz nowy projekt Maven z sekcją <parent> wskazującą na NIEISTNIEJĄCY
         * groupId/artifactId/version. Uruchom "mvn validate" i zapisz w komentarzu treść
         * błędu "Non-resolvable parent POM".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_OfflineOnEmptyCacheError {
        /*
         * 🧪 Zadanie 7:
         * Utwórz nowy projekt Maven z zależnością, której NIGDY wcześniej nie pobierałeś
         * (dowolna niszowa biblioteka z Maven Central). Uruchom od razu "mvn compile -o"
         * (offline) i zapisz w komentarzu treść błędu "Could not resolve dependencies".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixOfflineByOnlineFirst {
        /*
         * 🧪 Zadanie 8:
         * W projekcie z zadania 7 uruchom najpierw "mvn compile" (online, wypełnia cache),
         * a potem "mvn compile -o" (offline). Zapisz w komentarzu, że drugi raz build
         * przeszedł bez internetu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MavenOptsMemorySet {
        /*
         * 🧪 Zadanie 9:
         * Ustaw w terminalu zmienną środowiskową MAVEN_OPTS (np. "$env:MAVEN_OPTS =
         * '-Xmx1g'" w PowerShell) i uruchom "mvn -version". Zapisz w komentarzu, czy Maven
         * wypisał informację o użytych opcjach JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CommonErrorsTablePrint {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: odtwórz (jako println, własnymi słowami) tabelę z lekcji
         * zestawiającą min. 3 typowe błędy Mavena z ich przyczynami.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CreateVersionConflict {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj projekt z DWIEMA zależnościami, które transytywnie ciągną różne wersje
         * tej samej biblioteki (np. dwie różne wersje com.google.guava:guava przez dwie
         * różne biblioteki-pośredniki, albo jawnie zadeklaruj sam konflikt jako dwie
         * bezpośrednie zależności o różnych wersjach). Uruchom "mvn dependency:tree" i
         * zapisz w komentarzu linię z "(conflict)"/"omitted for conflict".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixConflictWithDependencyManagement {
        /*
         * 🧪 Zadanie 12:
         * W projekcie z zadania 11 dodaj <dependencyManagement> wymuszające konkretną
         * wersję konfliktowej biblioteki (wzorzec z Lesson14). Uruchom
         * "mvn dependency:tree" ponownie i zapisz w komentarzu, że konflikt zniknął (albo
         * że teraz jawnie widać wymuszoną wersję).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NearestWinsDemonstration {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: wypisz na konsoli wyjaśnienie reguły "nearest wins" (najbliższa
         * w drzewie zależności wygrywa) na konkretnym przykładzie z zadania 11 - która
         * wersja została wybrana i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StaleSnapshotSimulation {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj DWA małe projekty Maven: bibliotekę z wersją "1.0-SNAPSHOT" (zainstalowaną
         * przez "mvn install") oraz aplikację zależną od niej. Zmień coś w bibliotece,
         * zrób "mvn install" ponownie, ale w aplikacji uruchom zwykłe "mvn compile" (bez
         * -U). Zapisz w komentarzu, czy Maven użył nowej wersji z ~/.m2, czy starszej z
         * innego źródła cache (opisz zaobserwowane zachowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UpdateSnapshotsFlag {
        /*
         * 🧪 Zadanie 15:
         * W projekcie z zadania 14 uruchom "mvn compile -U" i zapisz w komentarzu
         * wyjaśnienie, kiedy dokładnie ta flaga ma znaczenie (repozytorium ZDALNE, nie
         * lokalny "mvn install" z zadania 14 - opisz różnicę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ParallelBuildMultiModule {
        /*
         * 🧪 Zadanie 16:
         * W projekcie wielomodułowym z Lesson14 (core/persistence/app) uruchom
         * "mvn install" (sekwencyjnie) i zmierz czas (np. "Measure-Command" w PowerShell).
         * Potem uruchom "mvn install -T 1C" i porównaj czas w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThisRepoParallelBuild {
        /*
         * 🧪 Zadanie 17:
         * W KORZENIU repozytorium tego kursu uruchom "mvnw.cmd compile -T 1C" i zapisz w
         * komentarzu, czy kompilacja się powiodła (ten projekt ma jeden moduł, więc
         * spodziewaj się braku realnego przyspieszenia - opisz dlaczego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EffectivePomInheritedProperty {
        /*
         * 🧪 Zadanie 18:
         * W projekcie z <parent> definiującym właściwość (np. <java.version>) uruchom
         * "mvn help:effective-pom" w module potomnym i zapisz w komentarzu, że
         * odziedziczona właściwość jest widoczna w wyniku, mimo że nie ma jej w pom.xml
         * modułu potomnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HelpDescribePlugin {
        /*
         * 🧪 Zadanie 19:
         * Uruchom w terminalu "mvn help:describe -Dplugin=org.apache.maven.plugins:maven-surefire-plugin
         * -Dfull" i zapisz w komentarzu, ile goali (celów) wypisał ten plugin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DependencyAnalyzeUnusedDeclared {
        /*
         * 🧪 Zadanie 20:
         * W projekcie z kilkoma zależnościami uruchom "mvn dependency:analyze" i zapisz w
         * komentarzu sekcje "Unused declared dependencies" oraz "Used undeclared
         * dependencies" - wyjaśnij, co każda z nich oznacza.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_OutOfMemoryDuringBuild {
        /*
         * 🧪 Zadanie 21:
         * Ustaw celowo bardzo niski limit pamięci "$env:MAVEN_OPTS = '-Xmx16m'" i uruchom
         * "mvn compile" w większym projekcie (np. w korzeniu tego kursu). Zapisz w
         * komentarzu, czy wystąpił OutOfMemoryError w trakcie samego builda (nie w
         * kodzie aplikacji) - a potem przywróć normalny MAVEN_OPTS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ThreadSafetyIssueWithParallelBuild {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wypisz wyjaśnienie (min. 3 zdania) na czym polega ryzyko
         * budowania równoległego "-T" z pluginem, który NIE jest thread-safe - np. dwa
         * moduły próbujące jednocześnie zapisać do tego samego wspólnego pliku poza
         * własnym katalogiem target/.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DependencyTreeWithScopeFilter {
        /*
         * 🧪 Zadanie 23:
         * W KORZENIU repozytorium tego kursu uruchom
         * "mvnw.cmd dependency:tree -Dscope=runtime" i osobno "-Dscope=test". Zapisz w
         * komentarzu różnicę w liczbie wypisanych zależności między obiema komendami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExcludeTransitiveDependency {
        /*
         * 🧪 Zadanie 24:
         * Znajdź (przez dependency:tree) zależność transytywną, której NIE chcesz w
         * projekcie (np. konfliktujący logger). Dodaj <exclusions> w pom.xml, wykluczając
         * ją z konkretnej zależności bezpośredniej. Uruchom "mvn dependency:tree"
         * ponownie i zapisz w komentarzu, że zniknęła z drzewa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildTimeProfilingManual {
        /*
         * 🧪 Zadanie 25:
         * Bez wtyczek profilujących: uruchom "mvn clean install" z opcją "-Dmaven.plugin
         * .validation=VERBOSE" (albo po prostu z "-X") i przejrzyj log w poszukiwaniu
         * najdłużej trwających goali. Zapisz w komentarzu, który etap (surefire, compiler,
         * itd.) zajął najwięcej czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CorruptedLocalRepoSimulation {
        /*
         * 🧪 Zadanie 26 (OSTROŻNIE - dotyka realnego ~/.m2):
         * Znajdź w ~/.m2/repository jeden mały, rzadko używany artefakt testowy i usuń
         * tylko jego katalog wersji (NIE cały ~/.m2!). Uruchom "mvn compile" w projekcie,
         * który go potrzebuje, i zapisz w komentarzu, czy Maven automatycznie pobrał go
         * ponownie z repozytorium zdalnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiModuleDependencyConflictAcrossModules {
        /*
         * 🧪 Zadanie 27:
         * W projekcie wielomodułowym (core/persistence/app) doprowadź do sytuacji, w
         * której moduł "persistence" i moduł "app" deklarują różne wersje tej samej
         * zależności zewnętrznej. Uruchom "mvn dependency:tree" w module "app" i zapisz w
         * komentarzu, która wersja "wygrała" w finalnym classpath aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CiCacheStrategyExplanation {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wypisz wyjaśnienie (min. 3 zdania), dlaczego pipeline'y CI
         * (GitHub Actions itp.) zwykle CACHE'UJĄ katalog ~/.m2/repository między
         * uruchomieniami - jaki wpływ ma to na czas builda i kiedy taki cache trzeba
         * unieważnić (zmiana wersji zależności).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AntGradleTroubleshootingComparisonPrint {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: wypisz porównanie (min. 3 punkty) diagnozowania problemów w
         * Mavenie (ta lekcja: -X, dependency:tree, effective-pom) względem Gradle
         * (Lesson25_GradleTroubleshootingAndPerformance tego kursu: --profile, --offline,
         * --refresh-dependencies) - podobieństwa i różnice w narzędziach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTroubleshootingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie na koniec bloku Maven: weź projekt wielomodułowy z
         * Lesson14, celowo wprowadź DO NIEGO trzy problemy naraz (konflikt wersji,
         * literówkę w nazwie pluginu, zbyt niski MAVEN_OPTS), a następnie zdiagnozuj i
         * napraw każdy z nich po kolei, używając narzędzi z tej lekcji (-e/-X,
         * dependency:tree, help:effective-pom). Napisz w komentarzu krótki raport
         * tekstowy: jaki był każdy problem, jak go zdiagnozowałeś i jak naprawiłeś.
         */
        public static void main(String[] args) { }
    }
}
