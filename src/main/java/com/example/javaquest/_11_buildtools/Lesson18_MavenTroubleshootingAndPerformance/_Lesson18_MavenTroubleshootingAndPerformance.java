package com.example.javaquest._11_buildtools.Lesson18_MavenTroubleshootingAndPerformance;

public class _Lesson18_MavenTroubleshootingAndPerformance {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: MAVEN - TROUBLESHOOTING I WYDAJNOSC ===");
        System.out.println("Ostatnia lekcja bloku Maven - jak DIAGNOZOWAC problemy z buildem i przyspieszac go.");

        /*
         * ============================================================
         * 📦 -X (DEBUG) I -e (STACK TRACE) - PIERWSZY KROK DIAGNOZY
         * ============================================================
         * Domyslny output "mvn" jest CELOWO skrocony - pokazuje tylko
         * najwazniejsze linie. Gdy build sie wywraca, PIERWSZE co warto
         * zrobic to powtorzyc komende z:
         * - "-e" (--errors) - pelny stack trace bledu (zamiast
         *   skroconego "[ERROR] ... -> [Help 1]").
         * - "-X" (--debug) - PELNY log debugowania: kazda faza, kazdy
         *   plugin, kazda rozdzielona zaleznosc, efektywna konfiguracja.
         *   Bardzo dlugi output, ale pokazuje DOKLADNIE co Maven robi
         *   krok po kroku.
         */
        System.out.println("\n=== -e i -X - PIERWSZY KROK DIAGNOZY ===");
        System.out.println("mvn package         -> skrocony blad: '[ERROR] Failed to execute goal ... -> [Help 1]'");
        System.out.println("mvn package -e      -> PELNY stack trace wyjatku Javy, ktory spowodowal blad.");
        System.out.println("mvn package -X      -> PELNY log debug (kazda faza/plugin/zaleznosc) - bardzo dlugi output.");

        /*
         * ============================================================
         * 🔹 mvn dependency:tree - ROZWIAZYWANIE KONFLIKTOW WERSJI
         * ============================================================
         * Gdy dwie zaleznosci transytywne ciagna RUZNE wersje tej samej
         * biblioteki (np. jedna biblioteka chce Guava 30, druga Guava
         * 32), Maven wybiera JEDNA wedlug reguly "nearest wins"
         * (najblizsza w drzewie zaleznosci wygrywa, a przy remisie
         * glebokosci - ta zadeklarowana WCZESNIEJ w pom.xml). To moze
         * prowadzic do NoSuchMethodError w runtime, jesli wybrana wersja
         * nie ma metody, ktorej oczekuje inny kod. "mvn dependency:tree"
         * pokazuje CALE drzewo z oznaczeniem "(conflict)" na
         * odrzuconych wersjach.
         */
        String dependencyTreeOutput = """
                [INFO] com.example:app:jar:1.0.0
                [INFO] +- com.google.guava:guava:jar:32.1.3-jre:compile
                [INFO] \\- com.example:legacy-lib:jar:2.0.0:compile
                [INFO]    \\- (com.google.guava:guava:jar:30.1-jre:compile - omitted for conflict with 32.1.3-jre)
                """;
        System.out.println("\n=== mvn dependency:tree - KONFLIKT WERSJI ===");
        System.out.println(dependencyTreeOutput);
        System.out.println("'omitted for conflict with X' -> Maven ODRZUCIL ta wersje, uzyl X (nearest wins).");
        System.out.println("Naprawa: jawna zaleznosc w <dependencyManagement> WYMUSZAJACA konkretna wersje (Lesson14).");

        /*
         * ============================================================
         * 🔍 mvn help:effective-pom - CO NAPRAWDE WIDZI MAVEN
         * ============================================================
         * Twoj pom.xml to czesto tylko FRAGMENT prawdziwej konfiguracji
         * - reszta pochodzi z <parent>, super pom (domyslny, wbudowany w
         * Mavena) i pluginManagement. "mvn help:effective-pom" wypisuje
         * PELNA, SCALONA konfiguracje, jaka Maven faktycznie uzywa - to
         * pierwszy krok, gdy jakas wartosc "skads sie bierze" i nie
         * wiadomo skad.
         */
        System.out.println("\n=== mvn help:effective-pom ===");
        System.out.println("Pokazuje PELNA, scalona konfiguracje (Twoj pom.xml + parent + super pom + pluginManagement).");
        System.out.println("Uzyteczne, gdy wersja pluginu/zaleznosci 'bierze sie skads', a nie widac tego w pom.xml.");

        /*
         * ============================================================
         * 🔹 --offline I "Could not resolve dependencies"
         * ============================================================
         * Flaga "-o" (--offline) mowi Mavenowi, zeby NIE probowal
         * laczyc sie z zadnym zdalnym repozytorium - korzysta WYLACZNIE
         * z lokalnego cache ~/.m2/repository. Uzyteczne np. w samolocie
         * albo gdy firmowy proxy jest niedostepny. Typowy blad
         * poczatkujacego: uzycie -o na CZYSTYM ~/.m2 (np. nowy komputer,
         * nowy kontener CI) - wtedy Maven NIE MA zaleznosci w cache i
         * zwraca "Could not resolve dependencies" mimo poprawnego pom.xml.
         */
        String offlineErrorOutput = """
                [ERROR] Failed to execute goal on project app: Could not resolve dependencies
                [ERROR] Cannot access central (https://repo.maven.apache.org/maven2) in offline mode
                """;
        System.out.println("\n=== BLAD ILUSTRACYJNY: --offline BEZ WYPELNIONEGO CACHE ===");
        System.out.println(offlineErrorOutput);
        System.out.println("Naprawa: najpierw jeden build ONLINE (wypelnia ~/.m2), dopiero potem mozna pracowac -o.");

        /*
         * ============================================================
         * 🔹 -U (--update-snapshots) - "STARY" SNAPSHOT W CACHE
         * ============================================================
         * Zaleznosci SNAPSHOT (Lesson17) sa NADPISYWALNE - ale Maven z
         * wydajnosci NIE sprawdza za kazdym razem, czy na serwerze jest
         * nowsza wersja SNAPSHOT (domyslnie raz dziennie). Jesli kolega
         * z zespolu zrobil "mvn deploy" nowszego SNAPSHOTa 5 minut temu,
         * a Ty wciaz masz stary w lokalnym cache, Twoj build uzyje
         * STAREGO kodu bez zadnego bledu - cichy, mylacy problem. "-U"
         * WYMUSZA sprawdzenie repozytoriow zdalnych, ignorujac lokalny
         * cache dla SNAPSHOTow.
         */
        System.out.println("\n=== -U (--update-snapshots) - WYMUSZENIE SWIEZYCH SNAPSHOTOW ===");
        System.out.println("mvn test          -> moze uzyc STAREGO SNAPSHOTa z lokalnego cache (bez ostrzezenia!).");
        System.out.println("mvn test -U       -> WYMUSZA sprawdzenie zdalnego repo, pobiera nowszy SNAPSHOT jesli istnieje.");

        /*
         * ============================================================
         * 🔍 -T (RownoLEGLE BUDOWANIE MODULOW) - WYDAJNOSC
         * ============================================================
         * W projekcie wielomodulowym (Lesson14: core/persistence/app)
         * Maven domyslnie buduje moduly SEKWENCYJNIE, w kolejnosci
         * wynikajacej z ich zaleznosci. Flaga "-T" wlacza budowanie
         * ROWNOLEGLE modulow, ktore NIE zaleza od siebie nawzajem -
         * na wielordzeniowym procesorze moze to znaczaco skrocic czas
         * builda duzego projektu wielomodulowego.
         */
        String parallelBuildSnippet = """
                mvn install -T 4          # 4 watki robocze
                mvn install -T 1C         # 1 watek NA RDZEN procesora (zalecane, skaluje sie automatycznie)
                """;
        System.out.println("\n=== -T - ROWNOLEGLE BUDOWANIE MODULOW ===");
        System.out.println(parallelBuildSnippet);
        System.out.println("UWAGA: rownolegle buildy moga ujawnic UKRYTE bledy w pluginach niebedacych thread-safe -");
        System.out.println("(np. dwa moduly pisza jednoczesnie do tego samego pliku) - testuj przed uzyciem w CI.");

        /*
         * ============================================================
         * 🔹 MAVEN_OPTS - PAMIEC JVM DLA SAMEGO MAVENA
         * ============================================================
         * Sam proces Maven (nie Twoja aplikacja!) to zwykla JVM - w
         * bardzo duzych projektach (setki modulow, tysiace zaleznosci)
         * moze zabraknac domyslnej pamieci sterty, co objawia sie
         * "OutOfMemoryError" W TRAKCIE SAMEGO BUILDU (nie w kodzie
         * aplikacji). Zmienna srodowiskowa MAVEN_OPTS pozwala zwiekszyc
         * limity pamieci dla procesu Mavena.
         */
        String mavenOptsSnippet = """
                # Windows (PowerShell):
                $env:MAVEN_OPTS = "-Xmx2g -Xms512m"

                # Linux/macOS:
                export MAVEN_OPTS="-Xmx2g -Xms512m"
                """;
        System.out.println("\n=== MAVEN_OPTS - PAMIEC JVM DLA PROCESU MAVENA ===");
        System.out.println(mavenOptsSnippet);
        System.out.println("Rozni sie od pamieci JVM Twojej APLIKACJI (ktora konfigurujesz osobno przy 'java -jar').");

        printCommonErrorsTable();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - "-e" (stack trace) i "-X" (pelny debug) - pierwszy krok przy
         *   kazdym niejasnym bledzie builda.
         * - "mvn dependency:tree" - ujawnia konflikty wersji ("omitted
         *   for conflict with X"), naprawiane przez dependencyManagement.
         * - "mvn help:effective-pom" - pelna, scalona konfiguracja
         *   (Twoj pom.xml + parent + super pom).
         * - "-o/--offline" wymaga JUZ WYPELNIONEGO ~/.m2 - inaczej
         *   "Could not resolve dependencies".
         * - "-U/--update-snapshots" - wymusza sprawdzenie nowszych
         *   SNAPSHOTow zamiast cichego uzycia starego cache.
         * - "-T" - rownolegle budowanie niezaleznych modulow (uwaga na
         *   pluginy niebedace thread-safe).
         * - MAVEN_OPTS - pamiec JVM SAMEGO PROCESU MAVENA, osobna od
         *   pamieci aplikacji.
         */

        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void printCommonErrorsTable() {
        System.out.println("\n=== NAJCZESTSZE BLEDY MAVENA I ICH PRZYCZYNY ===");
        String format = "%-38s | %-38s%n";
        System.out.printf(format, "Blad", "Typowa przyczyna");
        System.out.println("-".repeat(80));
        System.out.printf(format, "Could not resolve dependencies", "brak internetu / zla nazwa artefaktu / -o na pustym cache");
        System.out.printf(format, "Plugin ... not found", "literowka w <artifactId> pluginu albo brak wersji");
        System.out.printf(format, "NoSuchMethodError (runtime)", "konflikt wersji zaleznosci - patrz dependency:tree");
        System.out.printf(format, "Non-resolvable parent POM", "parent pom nie jest ani w ~/.m2, ani osiagalny zdalnie");
        System.out.printf(format, "OutOfMemoryError (sam build)", "za malo pamieci dla procesu Mavena - patrz MAVEN_OPTS");
    }
}
