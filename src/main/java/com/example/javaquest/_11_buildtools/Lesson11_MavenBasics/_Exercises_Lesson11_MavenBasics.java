package com.example.javaquest._11_buildtools.Lesson11_MavenBasics;

public class _Exercises_Lesson11_MavenBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateStandardStructure {
        /*
         * 🧪 Zadanie 1:
         * W terminalu utwórz ręcznie katalog "hello-maven" ze standardową strukturą
         * Maven: src/main/java, src/main/resources, src/test/java, src/test/resources.
         * Nie twórz jeszcze pom.xml - sam katalog i podkatalogi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HandWrittenPomXml {
        /*
         * 🧪 Zadanie 2:
         * W katalogu z zadania 1 napisz ręcznie plik pom.xml z minimalnymi elementami:
         * modelVersion 4.0.0, groupId "com.example", artifactId "hello-maven",
         * version "1.0.0", packaging "jar".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FirstJavaClass {
        /*
         * 🧪 Zadanie 3:
         * W src/main/java/com/example utwórz klasę App.java z metodą main, która wypisuje
         * "Hello, Maven!". Zwróć uwagę, że pakiet katalogów odpowiada groupId.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunMvnValidate {
        /*
         * 🧪 Zadanie 4:
         * W terminalu, w katalogu projektu z zadań 1-3, uruchom "mvn validate".
         * Zapisz w komentarzu, co Maven wypisał i co zmieniło się na dysku (sprawdź, czy
         * powstał katalog target/).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RunMvnCompile {
        /*
         * 🧪 Zadanie 5:
         * Uruchom w terminalu "mvn compile". Sprawdź zawartość target/classes - powinien
         * się tam znaleźć skompilowany App.class w odpowiedniej strukturze pakietów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GavExplanation {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wypisz na konsoli (System.out.println) wyjaśnienie skrótu GAV
         * (groupId, artifactId, version) i podaj przykładowe koordynaty dla biblioteki
         * Gson (com.google.code.gson:gson:2.11.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TargetGitignore {
        /*
         * 🧪 Zadanie 7:
         * W projekcie z zadań 1-5 utwórz plik .gitignore zawierający wpis "target/".
         * Wypisz w komentarzu, dlaczego katalogu target/ nie commituje się do repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PhaseListPrint {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz na konsoli wszystkie 7 faz cyklu "default" w poprawnej
         * kolejności (validate, compile, test, package, verify, install, deploy) razem
         * z jednozdaniowym opisem każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RunMvnPackage {
        /*
         * 🧪 Zadanie 9:
         * Uruchom w terminalu "mvn package" w projekcie z zadań 1-5. Sprawdź, że w target/
         * powstał plik hello-maven-1.0.0.jar. Uruchom go: "java -jar target/hello-maven-1.0.0.jar"
         * (dodaj wcześniej Main-Class do manifestu przez maven-jar-plugin, jeśli potrzeba -
         * albo po prostu zaobserwuj błąd braku Main-Class i zapisz go w komentarzu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PropertiesJavaVersion {
        /*
         * 🧪 Zadanie 10:
         * Dodaj do pom.xml z zadania 2 sekcję <properties> z <maven.compiler.source>21</...>
         * i <maven.compiler.target>21</...>. Uruchom "mvn compile" i sprawdź w terminalu,
         * że kompilacja wciąż działa.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MvnCleanExplained {
        /*
         * 🧪 Zadanie 11:
         * Uruchom w terminalu "mvn clean" w projekcie z wcześniejszych zadań. Sprawdź, że
         * katalog target/ zniknął. Wypisz w komentarzu, że "clean" NIE jest częścią cyklu
         * "default", lecz osobnego cyklu życia "clean".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MvnCleanPackageChain {
        /*
         * 🧪 Zadanie 12:
         * Uruchom w terminalu "mvn clean package" (dwie fazy z DWÓCH różnych cykli życia
         * podane jedna za drugą). Zapisz w komentarzu, w jakiej kolejności Maven je wykonuje
         * i dlaczego to częsty, bezpieczny sposób budowania "od zera".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GoalDirectInvocation {
        /*
         * 🧪 Zadanie 13:
         * Uruchom w terminalu bezpośrednio goal, z pominięciem fazy: "mvn compiler:compile".
         * Porównaj wynik/output z "mvn compile" i zapisz w komentarzu różnicę (czy fazy
         * wcześniejsze też się wykonały).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MvnTestWithoutTests {
        /*
         * 🧪 Zadanie 14:
         * W projekcie bez żadnych klas testowych uruchom "mvn test". Zapisz w komentarzu
         * komunikat Mavena o liczbie znalezionych/uruchomionych testów (0) - to pokazuje,
         * że faza "test" działa nawet bez testów, tylko nie ma nic do zrobienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FirstJUnitTest {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do pom.xml zależność junit-jupiter w scope "test" (zobacz przykład z lekcji).
         * Napisz w src/test/java prostą klasę AppTest z jedną metodą oznaczoną @Test, która
         * sprawdza proste założenie (np. 2+2==4). Uruchom "mvn test" i zapisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PackagingPomVsJar {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wypisz na konsoli różnicę między packaging="jar" (domyślne,
         * kompilowalny artefakt) a packaging="pom" (projekt-agregator bez własnego kodu,
         * używany np. w projektach wielomodułowych - zobaczysz w Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InstallToLocalRepo {
        /*
         * 🧪 Zadanie 17:
         * Uruchom w terminalu "mvn install" w projekcie z wcześniejszych zadań. Znajdź na
         * dysku, w katalogu ~/.m2/repository/com/example/hello-maven/1.0.0/, zainstalowany
         * plik .jar oraz .pom. Zapisz w komentarzu pełną ścieżkę, którą znalazłeś.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VersionBumpAndReinstall {
        /*
         * 🧪 Zadanie 18:
         * Zmień version w pom.xml na "1.1.0", uruchom "mvn install" ponownie. Sprawdź w
         * ~/.m2/repository, że powstał NOWY podkatalog "1.1.0" obok "1.0.0" - obie wersje
         * współistnieją w lokalnym repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PhaseVsGoalTable {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wypisz tabelę (kolejne linie println) łączącą 4 fazy (compile,
         * test, package, install) z domyślnie podwiązanym do nich goalem i pluginem, np.
         * "compile -> compiler:compile (maven-compiler-plugin)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SnapshotVsRelease {
        /*
         * 🧪 Zadanie 20:
         * Zmień version w pom.xml na "1.0.0-SNAPSHOT". Bez terminala wypisz wyjaśnienie
         * różnicy między wersją SNAPSHOT (rozwojowa, może się zmieniać) a wersją "release"
         * (stabilna, niezmienna, np. "1.0.0").
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiPhaseTimingExperiment {
        /*
         * 🧪 Zadanie 21:
         * W terminalu uruchom po kolei (osobno, z "mvn clean" między nimi): validate,
         * compile, test, package. Dla każdej zapisz w komentarzu, jakie NOWE pliki/katalogi
         * pojawiły się w target/ w porównaniu z poprzednim krokiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomJarNameViaFinalName {
        /*
         * 🧪 Zadanie 22:
         * Dodaj do pom.xml sekcję <build><finalName>moja-aplikacja</finalName></build>.
         * Uruchom "mvn package" i sprawdź, że plik .jar w target/ nazywa się teraz
         * moja-aplikacja.jar (bez wersji w nazwie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SkipTestsFlag {
        /*
         * 🧪 Zadanie 23:
         * Dodaj do projektu test, który celowo NIE PRZECHODZI (np. assertEquals(1, 2)).
         * Uruchom "mvn package" (powinno się nie udać przez failing test), a następnie
         * "mvn package -DskipTests" (powinno się udać, testy pominięte). Zapisz różnicę
         * w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OfflineModeExperiment {
        /*
         * 🧪 Zadanie 24:
         * Uruchom "mvn compile -o" (tryb offline, tylko lokalne repozytorium/cache).
         * Jeśli wszystkie zależności są już w ~/.m2, budowanie powinno się udać bez
         * dostępu do internetu. Zapisz w komentarzu, kiedy taki tryb jest przydatny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_VerboseVsQuiet {
        /*
         * 🧪 Zadanie 25:
         * Porównaj w terminalu output "mvn compile" (domyślny), "mvn compile -q" (quiet,
         * mniej logów) i "mvn compile -X" (debug, dużo więcej logów). Zapisz w komentarzu,
         * do czego przydałby się każdy z tych trybów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LifecyclePhaseDiagramPrint {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz metodę printLifecycleDiagram(), która wypisuje ASCII-diagram
         * strzałek "validate -> compile -> test -> package -> verify -> install -> deploy"
         * i pod nim listę wyjaśnień. Wywołaj tę metodę z main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReproduceFromScratch {
        /*
         * 🧪 Zadanie 27:
         * W NOWYM, czystym katalogu odtwórz cały projekt hello-maven od zera: struktura
         * katalogów, pom.xml, klasa App.java, klasa testowa. Uruchom "mvn clean install"
         * jednym poleceniem i zapisz w komentarzu, że to odtworzyło CAŁY wynik pracy
         * z zadań 1-18 jednym poleceniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareWithAntBuildXml {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz w komentarzu porównanie (min. 4 punkty) między build.xml
         * z lekcji o Ancie (rozdział 03-10 tego kursu) a pom.xml z tej lekcji - czym różni
         * się filozofia "opisz kroki" (Ant) od "opisz co masz, reszta jest konwencją" (Maven).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BrokenPomDiagnostics {
        /*
         * 🧪 Zadanie 29:
         * Celowo wprowadź błąd składniowy do pom.xml (np. usuń domykający tag </project>
         * albo wpisz nieistniejący element). Uruchom "mvn validate" i zapisz w komentarzu
         * dokładny komunikat błędu, jaki wypisał Maven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLifecycleCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie: w projekcie hello-maven (naprawionym po zadaniu 29) dodaj
         * drugą klasę z prostą logiką biznesową (np. kalkulator) + test jednostkowy dla niej.
         * Uruchom PEŁNY cykl: "mvn clean validate compile test package verify install" jednym
         * poleceniem, obserwując w terminalu wszystkie fazy po kolei. Zapisz w komentarzu
         * ostateczną lokalizację zainstalowanego artefaktu w ~/.m2/repository.
         */
        public static void main(String[] args) { }
    }
}
