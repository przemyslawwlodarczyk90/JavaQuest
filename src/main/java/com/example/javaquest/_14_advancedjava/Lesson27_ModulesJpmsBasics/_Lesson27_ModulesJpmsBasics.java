package com.example.javaquest._14_advancedjava.Lesson27_ModulesJpmsBasics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class _Lesson27_ModulesJpmsBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 27: JPMS - podstawy modulow Javy ===\n");

        /*
         * ============================================================
         * 📦 JPMS - JAVA PLATFORM MODULE SYSTEM (JDK 9, 2017)
         * ============================================================
         * Przed Java 9 caly "swiat" Javy skladal sie z DWOCH pojec:
         *  - PAKIETY (package) - grupowanie logiczne klas,
         *  - CLASSPATH - plaska lista katalogow/JAR-ow, z ktorych JVM
         *    laduje klasy, BEZ zadnej wiedzy o zaleznosciach miedzy nimi.
         *
         * To rodzilo realne problemy w duzych projektach/samym JDK:
         *  - SLABA ENKAPSULACJA - "public" oznaczalo "widoczne dla
         *    DOSLOWNIE kazdego" na calym classpath, nawet z zupelnie
         *    innego JAR-a. Biblioteki musialy oznaczac klasy jako
         *    "wewnetrzne" tylko PRZEZ KONWENCJE (np. pakiet ".internal"),
         *    a nie faktyczny mechanizm jezyka - nic nie stalo na
         *    przeszkodzie, zeby ktos jednak z nich skorzystal.
         *  - NIEPEWNA KONFIGURACJA - brakujaca zaleznosc ujawniala sie
         *    dopiero w RUNTIME, przy pierwszym uzyciu (NoClassDefFoundError),
         *    a nie przy starcie aplikacji. Dwa JAR-y mogly tez zawierac
         *    TA SAMA klase (konflikt wersji) - i wygrywala ta, ktora
         *    byla PIERWSZA na classpath, bez zadnego ostrzezenia.
         *  - MONOLITYCZNY JDK - samo srodowisko uruchomieniowe (rt.jar)
         *    bylo jednym, gigantycznym blokiem - nie dalo sie dostarczyc
         *    "malej" Javy bez modulow AWT/Swing/CORBA, ktorych aplikacja
         *    serwerowa nigdy nie uzyje.
         *
         * JPMS to rozwiazuje wprowadzajac NOWA jednostke, WIEKSZA niz
         * pakiet: MODUL. Modul to nazwana, samoopisujaca sie grupa
         * pakietow z jawnie zadeklarowanymi:
         *  - ZALEZNOSCIAMI (czego POTRZEBUJE - "requires"),
         *  - PUBLICZNYM API (co UDOSTEPNIA innym - "exports").
         *
         * Trzy oficjalne cele JPMS: SILNA ENKAPSULACJA (silniejsza niz
         * "public" - patrz Lekcja 28, opens), NIEZAWODNA KONFIGURACJA
         * (brakujaca/konfliktujaca zaleznosc to blad PRZY STARCIE, nie
         * w trakcie dzialania), oraz SKALOWALNA PLATFORMA (sam JDK jest
         * dzis podzielony na ~70 modulow - narzedzie jlink potrafi
         * zbudowac WLASNY, minimalny runtime tylko z modulow, ktorych
         * aplikacja faktycznie potrzebuje).
         */

        /*
         * ============================================================
         * 🔹 ANATOMIA module-info.java
         * ============================================================
         * Kazdy modul to katalog z plikiem module-info.java w KORZENIU
         * drzewa pakietow. Minimalna postac:
         *
         *     module com.example.greeting.api {
         *         requires com.example.other.module;
         *         exports com.example.greeting.api;
         *     }
         *
         * - NAZWA MODULU - konwencja jak odwrocona domena (jak nazwy
         *   pakietow), ale to ZUPELNIE OSOBNA przestrzen nazw niz
         *   pakiety - modul "com.example.foo" moze eksportowac pakiety
         *   o zupelnie innych nazwach.
         * - requires X - "ja POTRZEBUJE modulu X, zeby dzialac" (zarowno
         *   przy kompilacji jak i w runtime). Buduje to GRAF CZYTELNOSCI
         *   (readability graph) miedzy modulami - jesli A wymaga B,
         *   mowimy ze "A CZYTA B" (A reads B).
         * - exports Y - "moj pakiet Y jest CZESCIA MOJEGO PUBLICZNEGO API,
         *   widoczna dla kazdego modulu, ktory mnie 'requires'". Pakiety
         *   NIE wymienione w exports SA SILNIE UKRYTE - nawet ich klasy
         *   publiczne sa NIEWIDOCZNE spoza modulu (zobaczymy to zaraz
         *   jako prawdziwy blad kompilacji).
         *
         * WAZNE: to WSZYSTKO dzieje sie na poziomie MODULU-PATH, nie
         * classpath. Ten kurs (jak wiekszosc projektow na Maven/Spring
         * Boot) dziala na classpath - o tym, co to oznacza, wiecej
         * nizej ("modul nienazwany").
         */

        /*
         * ============================================================
         * 🔍 DEMO: BUDUJEMY I URUCHAMIAMY PRAWDZIWY 2-MODULOWY PROJEKT
         * ============================================================
         * Poniewaz CALY projekt javaQuest kompiluje sie na classpath
         * (BRAK module-info.java gdziekolwiek w src/main/java - i tak
         * ma zostac, patrz Lekcja 28), nie mozemy pokazac JPMS "z
         * wnetrza" tej samej klasy. Zamiast tego - dokladnie jak w
         * rozdziale _11_buildtools - wygenerujemy PRAWDZIWY, OSOBNY
         * mini-projekt modulowy w katalogu tymczasowym, skompilujemy go
         * PRAWDZIWA komenda javac (z opcja --module-source-path) i
         * uruchomimy PRAWDZIWA komenda java (z opcja --module-path),
         * obie jako podprocesy (ProcessBuilder), przechwytujac i
         * wypisujac ich RZECZYWISTY output.
         *
         * Struktura, ktora zaraz zbudujemy na dysku:
         *
         *   src/
         *     greeting.api/
         *       module-info.java      (exports pakiet "api")
         *       api/Greeter.java
         *       api/internal/Secret.java   (NIE eksportowany!)
         *     greeting.app/
         *       module-info.java      (requires greeting.api)
         *       app/Main.java
         */

        Path workDir = Files.createTempDirectory("lesson27-jpms");
        try {
            Path srcDir = workDir.resolve("src");
            Path apiModuleDir = srcDir.resolve("greeting.api");
            Path appModuleDir = srcDir.resolve("greeting.app");
            Files.createDirectories(apiModuleDir.resolve("api").resolve("internal"));
            Files.createDirectories(appModuleDir.resolve("app"));

            // MODUL 1: greeting.api - eksportuje TYLKO pakiet "api",
            // pakiet "api.internal" pozostaje silnie ukryty.
            Files.writeString(apiModuleDir.resolve("module-info.java"), """
                    module greeting.api {
                        exports api;
                    }
                    """);
            Files.writeString(apiModuleDir.resolve("api").resolve("Greeter.java"), """
                    package api;

                    public class Greeter {
                        public String greet(String name) {
                            return "Witaj z modulu greeting.api, " + name + "!";
                        }
                    }
                    """);
            Files.writeString(apiModuleDir.resolve("api").resolve("internal").resolve("Secret.java"), """
                    package api.internal;

                    // Ta klasa jest PUBLICZNA, ale jej pakiet NIE jest w "exports"
                    // powyzej - dla modulow spoza greeting.api jest NIEWIDOCZNA,
                    // mimo publicznego modyfikatora dostepu.
                    public class Secret {
                        public static String hidden() {
                            return "szczegol implementacyjny, ktory nie powinien wyciec";
                        }
                    }
                    """);

            // MODUL 2: greeting.app - wymaga greeting.api, uzywa TYLKO
            // eksportowanego pakietu "api".
            Files.writeString(appModuleDir.resolve("module-info.java"), """
                    module greeting.app {
                        requires greeting.api;
                    }
                    """);
            Files.writeString(appModuleDir.resolve("app").resolve("Main.java"), """
                    package app;

                    import api.Greeter;

                    public class Main {
                        public static void main(String[] args) {
                            Greeter greeter = new Greeter();
                            System.out.println(greeter.greet("Kursancie"));
                        }
                    }
                    """);

            String javaHome = System.getProperty("java.home");
            String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
            String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

            Path outDir = workDir.resolve("out");
            Files.createDirectories(outDir);

            System.out.println("--- Kompilacja 2 modulow (javac --module-source-path) ---");
            // KROK 1: --module-source-path mowi javac "tu jest KORZEN
            // drzewa modulow" - kazdy podkatalog to jeden modul. --module
            // wymienia, KTORE moduly maja zostac skompilowane (moga tez
            // byc podane wprost jako pliki .java, ale przy wielu modulach
            // wygodniej wskazac je po nazwie).
            int compileExit = runProcess("javac (kompilacja greeting.api + greeting.app)",
                    javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", outDir.toString(),
                    "--module", "greeting.api,greeting.app");
            System.out.println("Kod wyjscia kompilacji: " + compileExit + "\n");

            System.out.println("--- Uruchomienie (java --module-path --module) ---");
            // KROK 2: --module-path wskazuje katalog(i) ZAWIERAJACY
            // skompilowane moduly, --module <modul>/<klasa.z.main> mowi,
            // ktory modul i ktora klasa maja zostac uruchomione.
            int runExit = runProcess("java (uruchomienie greeting.app/app.Main)",
                    javaExe,
                    "--module-path", outDir.toString(),
                    "--module", "greeting.app/app.Main");
            System.out.println("Kod wyjscia uruchomienia: " + runExit + "\n");

            /*
             * ============================================================
             * 🔍 DEMO BLEDU: PROBA UZYCIA NIE-EKSPORTOWANEGO PAKIETU
             * ============================================================
             * Podmieniamy Main.java w module greeting.app tak, aby
             * importowal klase z pakietu api.internal (NIE wymienionego
             * w exports modulu greeting.api). To NIE jest blad w runtime -
             * to PRAWDZIWY BLAD KOMPILACJI, wykryty na etapie javac,
             * dokladnie tak, jak obiecuje "niezawodna konfiguracja" JPMS
             * (blad wykryty jak najwczesniej, nie gdzies w produkcji).
             */
            Files.writeString(appModuleDir.resolve("app").resolve("Main.java"), """
                    package app;

                    import api.Greeter;
                    import api.internal.Secret;

                    public class Main {
                        public static void main(String[] args) {
                            Greeter greeter = new Greeter();
                            System.out.println(greeter.greet("Kursancie"));
                            System.out.println(Secret.hidden());
                        }
                    }
                    """);

            Path failOutDir = workDir.resolve("out-fail");
            Files.createDirectories(failOutDir);
            System.out.println("--- Demo bledu: import nie-eksportowanego pakietu api.internal ---");
            int failCompileExit = runProcess("javac (oczekiwany BLAD kompilacji)",
                    javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", failOutDir.toString(),
                    "--module", "greeting.api,greeting.app");
            System.out.println("Kod wyjscia (oczekiwane != 0): " + failCompileExit);
            System.out.println("Real komunikat bledu (powyzej) to dokladnie: \"package api.internal is not visible\"");
            System.out.println("- silna enkapsulacja JPMS dziala nawet dla klas PUBLICZNYCH w nie-eksportowanym pakiecie.\n");

        } finally {
            deleteRecursively(workDir);
        }

        /*
         * ============================================================
         * 🔹 MODUL NIENAZWANY (UNNAMED MODULE) - GDZIE ZYJE TEN KURS
         * ============================================================
         * Gdy uruchamiasz Javy w "starym" trybie - klasy/JAR-y na
         * CLASSPATH (opcja -cp / -classpath, TAK jak caly ten projekt
         * javaQuest) - JVM i tak wewnetrznie uzywa JPMS, ale WSZYSTKIE
         * te klasy trafiaja do jednego, specjalnego "MODULU NIENAZWANEGO"
         * (unnamed module):
         *  - CZYTA (requires) kazdy inny modul w systemie,
         *  - EKSPORTUJE WSZYSTKIE swoje pakiety KAZDEMU,
         *  - nie ma zadnej silnej enkapsulacji miedzy klasami wewnatrz
         *    niego - dziala DOKLADNIE tak, jak klasyczny classpath sprzed
         *    Javy 9.
         *
         * To jest fundament WSTECZNEJ KOMPATYBILNOSCI JPMS - miliony
         * istniejacych projektow (jak nasz javaQuest) dzialaja bez ZADNEJ
         * zmiany, bo domyslnie caly kod na classpath ladnie miesci sie w
         * tym jednym, "otwartym" module. Wlasnie dlatego w tym projekcie
         * NIGDZIE nie ma pliku module-info.java - i to jest CELOWE
         * (wiecej o tej decyzji w Lekcji 28).
         */

        /*
         * ============================================================
         * 🔹 MODULY AUTOMATYCZNE (AUTOMATIC MODULES)
         * ============================================================
         * Co jesli chcesz uruchomic aplikacje modulowa (z wlasnym
         * module-info.java), ale jedna z jej bibliotek (stary JAR z
         * Maven Central) NIE MA jeszcze wlasnego module-info.java?
         * JPMS ma na to odpowiedz posrednia miedzy "modulem nienazwanym"
         * a "prawdziwym modulem": MODUL AUTOMATYCZNY.
         *
         * Gdy taki "goly" JAR (bez module-info.class) trafi na
         * MODULE PATH (nie classpath!), JVM automatycznie tworzy dla
         * niego modul:
         *  - NAZWA wyprowadzona z nazwy pliku JAR (np. "gson-2.10.1.jar"
         *    -> modul "gson"), chyba ze JAR ma w manifescie atrybut
         *    "Automatic-Module-Name" - wtedy uzywana jest ta nazwa,
         *  - CZYTA wszystkie inne moduly (jak modul nienazwany),
         *  - EKSPORTUJE WSZYSTKIE swoje pakiety (pelna widocznosc, bez
         *    silnej enkapsulacji - jak za starych, classpathowych czasow).
         *
         * To swiadomy "pomost" (bridge) ulatwiajacy STOPNIOWA migracje
         * duzych projektow do pelnej modularnosci - nie trzeba czekac,
         * az WSZYSTKIE zaleznosci dostana wlasny module-info.java na raz.
         */

        System.out.println("\n=== KONIEC LEKCJI 27 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JPMS (JDK 9) wprowadza MODUL - jednostke wieksza niz pakiet,
         *   z jawnymi zaleznosciami (requires) i jawnym publicznym API
         *   (exports) - odpowiedz na slaba enkapsulacje i niepewna
         *   konfiguracje starego, plaskiego classpath.
         * - module-info.java w korzeniu drzewa pakietow modulu:
         *   "module nazwa { requires X; exports Y; }".
         * - Pakiety NIE wymienione w exports sa SILNIE UKRYTE - nawet ich
         *   klasy publiczne sa niewidoczne spoza modulu (prawdziwy blad
         *   kompilacji powyzej: "package api.internal is not visible").
         * - Kompilacja modulowa: javac --module-source-path <src> -d <out>
         *   --module <mod1>,<mod2>. Uruchomienie: java --module-path <out>
         *   --module <modul>/<pelna.klasa.Main>.
         * - MODUL NIENAZWANY (unnamed module) to miejsce, w ktorym zyje
         *   caly kod uruchamiany na CLASSPATH (jak caly ten kurs) - czyta
         *   wszystko, eksportuje wszystko wszystkim, brak silnej
         *   enkapsulacji - pelna wsteczna kompatybilnosc.
         * - MODUL AUTOMATYCZNY to "pomost" dla "golych" JAR-ow (bez
         *   module-info.java) umieszczonych na module path - nazwa z
         *   nazwy pliku (albo atrybutu Automatic-Module-Name), pelna
         *   widocznosc jak modul nienazwany.
         * - Lekcja 28 pokaze zaawansowane dyrektywy (opens, exports...to,
         *   uses/provides) oraz uczciwie wyjasni, dlaczego SAM ten kurs
         *   pozostaje na classpath.
         */
    }

    /**
     * Uruchamia PRAWDZIWY proces systemowy (javac albo java), wypisuje
     * dokladna komende, przechwytuje i wypisuje jego CALY output
     * (stdout+stderr polaczone), i zwraca kod wyjscia.
     */
    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (InputStream in = process.getInputStream()) {
            byte[] output = in.readAllBytes();
            if (output.length > 0) {
                System.out.print(new String(output));
            }
        }

        return process.waitFor();
    }

    /**
     * Rekurencyjnie usuwa caly katalog tymczasowy (od najglebszych plikow
     * do korzenia) - sprzatanie po demo, wywolywane w bloku finally.
     */
    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    // Ignorujemy pojedyncze bledy sprzatania katalogu tymczasowego.
                }
            });
        }
    }
}
