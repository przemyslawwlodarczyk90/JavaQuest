package com.example.javaquest._15_jvm_internals.Lesson05_ClasspathVsModulepath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class _Lesson05_ClasspathVsModulepath {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: CLASSPATH vs MODULE PATH (Z PERSPEKTYWY CLASSLOADINGU) ===\n");

        /*
         * ============================================================
         * 🔹 UWAGA: TO NIE JEST POWTORKA KURSU O JPMS
         * ============================================================
         * Rozdzial `_14_advancedjava` (Lekcje 27-28) uczyl JPMS jako
         * NARZEDZIA JEZYKOWEGO - module-info.java, requires/exports/opens/
         * uses/provides, jlink itd. Ta lekcja NIE powtarza tamtego
         * materialu. Interesuje nas WYLACZNIE JEDNO pytanie: jak obecnosc
         * (albo brak) modulow WPLYWA na sam MECHANIZM CLASSLOADINGU
         * poznany w Lekcjach 3-4 - czyli na to, GDZIE klasy "zyja" i CO
         * jest dla nich WIDOCZNE.
         */
        System.out.println("(Kontekst: JPMS jako mechanizm jezykowy byl w _14_advancedjava/Lesson27-28.");
        System.out.println(" Tutaj: WYLACZNIE wplyw na classloading/widocznosc, nie skladnia modulow.)\n");

        /*
         * ============================================================
         * 🔹 CLASSPATH - "PLASKI SWIAT" BEZ SILNEJ ENKAPSULACJI
         * ============================================================
         * Na classpath (tak dziala CALY ten kurs javaQuest) WSZYSTKIE
         * klasy ze wszystkich JAR-ow/katalogow trafiaja do JEDNEGO,
         * "MODULU NIENAZWANEGO" (unnamed module - wspomnianego w
         * `_14_advancedjava/Lesson27`). Z perspektywy classloadingu:
         *
         *  - KAZDA publiczna klasa jest widoczna dla KAZDEJ innej klasy na
         *    classpath, niezaleznie z jakiego JAR-a pochodzi - "public"
         *    znaczy "publiczne dla dowolnego kodu w tym samym loaderze".
         *  - SPLIT PACKAGES sa mozliwe: DWA RÓZNE JAR-y moga zdefiniowac
         *    klasy w TYM SAMYM pakiecie (np. "com.example.util") - JVM nie
         *    protestuje, po prostu obie trafiaja do tego samego
         *    Application Class Loadera, ktory znajdzie ktoras z nich jako
         *    pierwsza (kolejnosc zalezna od kolejnosci wpisow na
         *    classpath - potencjalne zrodlo trudnych do zdiagnozowania
         *    bledow).
         *  - Refleksja (setAccessible(true)) domyslnie moze "przebic sie"
         *    przez private/protected wewnatrz tego samego modulu
         *    nienazwanego bez dodatkowych barier.
         *
         * To dokladnie ten model, ktory znasz z calego dotychczasowego
         * kursu - Application Class Loader laduje WSZYSTKO z classpath do
         * jednej, plaskiej przestrzeni.
         */
        explainClasspathModel();

        /*
         * ============================================================
         * 🔹 MODULE PATH - SILNA ENKAPSULACJA NA POZIOMIE CLASSLOADERA
         * ============================================================
         * Gdy uruchamiasz kod jako PRAWDZIWE moduly (--module-path zamiast
         * -cp/-classpath), JVM tworzy dla nich INNY zestaw class loaderow
         * (patrz `_14_advancedjava/Lesson28`: application module path ma
         * wlasny loader, odrebny od unnamed module). Kluczowa roznica z
         * perspektywy classloadingu:
         *
         *  - Pakiet NIE wymieniony w "exports" jest SILNIE UKRYTY - nawet
         *    jego klasy PUBLICZNE sa niewidoczne SPOZA modulu, i to na
         *    poziomie samego mechanizmu ladowania/widocznosci klas, a NIE
         *    tylko konwencji. Zwykle wywolanie (import + kompilacja) da
         *    blad kompilacji ("package X is not visible") - ale co WAZNE
         *    dla tej lekcji: nawet REFLEKSJA (Class.forName +
         *    getDeclaredMethod + setAccessible) natrafi na barier w
         *    runtime (IllegalAccessException/InaccessibleObjectException),
         *    chyba ze modul jawnie "opens" dany pakiet.
         *  - SPLIT PACKAGES SA ZABRONIONE - dwa moduly NIE MOGA
         *    eksportowac/zawierac tego samego pakietu - JVM zglosi blad
         *    JUZ PRZY STARCIE (nie w trakcie dzialania), dokladnie zgodnie
         *    z celem JPMS "niezawodna konfiguracja".
         *
         * Ponizej zbudujemy WLASNY, prosty (celowo prostszy niz w Lekcji
         * 27/28) przyklad skupiony TYLKO na tym: czy klasa z
         * nieeksportowanego pakietu jest dostepna przez REFLEKSJE.
         */
        explainModulePathModel();

        /*
         * ============================================================
         * 🔍 DEMO: TEN SAM KOD, DWA SPOSOBY URUCHOMIENIA
         * ============================================================
         * Budujemy klase "Secret" (pakiet secretlib.internal, publiczna
         * metoda hiddenValue()) oraz klase "ReflectionProbe" (pakiet
         * probe), ktora WYLACZNIE przez refleksje (Class.forName po
         * nazwie, bez zadnego "import Secret") probuje wywolac
         * Secret.hiddenValue(). Ten sam, NIEZMIENIONY kod uruchomimy:
         *  - Raz NA CLASSPATH (-cp, bez zadnych modulow) - oczekujemy
         *    SUKCESU (brak silnej enkapsulacji miedzy pakietami).
         *  - Raz NA MODULE PATH jako DWA OSOBNE MODULY ("secretlib" i
         *    "probe", gdzie secretlib NIE eksportuje/opens swojego
         *    pakietu internal) - oczekujemy BLEDU dostepu w runtime.
         *    (Gdyby obie klasy byly w JEDNYM module, silna enkapsulacja
         *    w ogole by sie nie ujawnila - dlatego to musza byc naprawde
         *    dwa moduly.)
         */
        Path workDir = Files.createTempDirectory("lesson05-classpath-vs-modulepath");
        try {
            runClasspathVariant(workDir);
            runModulePathVariant(workDir);
        } finally {
            deleteRecursively(workDir);
        }

        System.out.println("\n=== KONIEC LEKCJI 5 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CLASSPATH: wszystkie klasy trafiaja do jednego "modulu
         *   nienazwanego" - brak silnej enkapsulacji, kazda klasa public
         *   jest widoczna dla kazdej innej (takze przez refleksje), split
         *   packages sa mozliwe (i moga cichcem "wygrywac" wg kolejnosci
         *   na classpath).
         * - MODULE PATH: kazdy modul dostaje wlasny loader z faktyczna
         *   silna enkapsulacja - pakiet spoza "exports" jest niewidoczny
         *   nawet dla refleksji (chyba ze modul uzyje "opens"), a split
         *   packages sa BLEDEM WYKRYWANYM PRZY STARCIE.
         * - Ten sam kod (ReflectionProbe probujacy dostac sie do
         *   Secret.hiddenValue()) zachowuje sie INACZEJ w zaleznosci
         *   WYLACZNIE od tego, czy uruchomimy go jako classpath, czy jako
         *   moduly - to jest DOWOD, ze silna enkapsulacja JPMS dziala na
         *   poziomie samego classloadingu/runtime, nie tylko kompilacji.
         * - To byla ostatnia z 5 lekcji klastra "classloading i bajtkod"
         *   tego rozdzialu - kolejne lekcje (6-7) schodza do obszarow
         *   pamieci JVM (heap/stack/metaspace) "od srodka".
         */
    }

    private static void explainClasspathModel() {
        System.out.println("[Model classpath]");
        System.out.println("Jeden 'modul nienazwany' - brak silnej enkapsulacji, split packages mozliwe.\n");
    }

    private static void explainModulePathModel() {
        System.out.println("[Model module path]");
        System.out.println("Kazdy modul - wlasny loader, silna enkapsulacja (nawet dla refleksji), split packages zabronione.\n");
    }

    /**
     * Buduje i uruchamia mini-projekt (2 pakiety NA TYM SAMYM CLASSPATH,
     * bez zadnego module-info.java) - klasa ReflectionProbe siega
     * WYLACZNIE przez refleksje (Class.forName po nazwie) do klasy Secret
     * w "innym" pakiecie. Oczekiwany wynik: refleksja SIE UDAJE, bo na
     * classpath NIE MA zadnej silnej enkapsulacji miedzy pakietami.
     */
    private static void runClasspathVariant(Path workDir) throws IOException, InterruptedException {
        System.out.println("--- WARIANT A: CLASSPATH (-cp), BEZ modulow ---");

        Path srcDir = workDir.resolve("classpath-src");
        Path internalDir = srcDir.resolve("secretlib").resolve("internal");
        Path probeDir = srcDir.resolve("probe");
        Files.createDirectories(internalDir);
        Files.createDirectories(probeDir);

        writeSecretSource(internalDir);
        writeProbeSource(probeDir);

        Path outDir = workDir.resolve("classpath-out");
        Files.createDirectories(outDir);

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        int compileExit = runProcess("javac (classpath, bez modulow)",
                javacExe,
                "-d", outDir.toString(),
                internalDir.resolve("Secret.java").toString(),
                probeDir.resolve("ReflectionProbe.java").toString());
        System.out.println("Kod wyjscia kompilacji: " + compileExit);

        int runExit = runProcess("java (classpath, -cp)",
                javaExe, "-cp", outDir.toString(), "probe.ReflectionProbe");
        System.out.println("Kod wyjscia uruchomienia: " + runExit
                + " (oczekiwane: 0 - refleksja miala SIE UDAC, brak silnej enkapsulacji)\n");
    }

    /**
     * Buduje TEN SAM kod (ReflectionProbe siegajacy przez Class.forName do
     * Secret), ale tym razem Secret i ReflectionProbe zyja w DWOCH
     * OSOBNYCH MODULACH JPMS ("secretlib" i "probe"). Modul "secretlib"
     * celowo NIE eksportuje (ani nie "opens") pakietu "secretlib.internal".
     * WAZNE: silna enkapsulacja JPMS dziala MIEDZY modulami - gdyby obie
     * klasy byly w JEDNYM module, refleksja zadzialalaby normalnie (dlatego
     * to musza byc NAPRAWDE DWA odrebne moduly, nie dwa pakiety w jednym).
     * Oczekiwany wynik: Class.forName znajdzie klase (samo ladowanie nie
     * jest blokowane), ale wywolanie method.invoke(...) zakonczy sie
     * bledem dostepu w runtime.
     */
    private static void runModulePathVariant(Path workDir) throws IOException, InterruptedException {
        System.out.println("--- WARIANT B: MODULE PATH (--module-path), DWA MODULY ---");

        Path srcDir = workDir.resolve("modulepath-src");
        Path secretlibModuleDir = srcDir.resolve("secretlib");
        Path probeModuleDir = srcDir.resolve("probe");
        Path internalDir = secretlibModuleDir.resolve("secretlib").resolve("internal");
        Path probeDir = probeModuleDir.resolve("probe");
        Files.createDirectories(internalDir);
        Files.createDirectories(probeDir);

        // Modul secretlib: NIE eksportuje "secretlib.internal" NIKOMU -
        // pakiet jest silnie ukryty przed wszystkimi innymi modulami.
        Files.writeString(secretlibModuleDir.resolve("module-info.java"), """
                module secretlib {
                }
                """);
        // Modul probe: wymaga secretlib (moze go "czytac"), ale to NIE
        // wystarczy, zeby refleksyjnie dostac sie do nie-eksportowanego
        // pakietu "secretlib.internal" - to jest sedno tego demo.
        Files.writeString(probeModuleDir.resolve("module-info.java"), """
                module probe {
                    requires secretlib;
                }
                """);

        writeSecretSource(internalDir);
        writeProbeSource(probeDir);

        Path outDir = workDir.resolve("modulepath-out");
        Files.createDirectories(outDir);

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        int compileExit = runProcess("javac (--module-source-path, 2 moduly)",
                javacExe,
                "--module-source-path", srcDir.toString(),
                "-d", outDir.toString(),
                "--module", "secretlib,probe");
        System.out.println("Kod wyjscia kompilacji: " + compileExit);

        int runExit = runProcess("java (--module-path)",
                javaExe,
                "--module-path", outDir.toString(),
                "--module", "probe/probe.ReflectionProbe");
        System.out.println("Kod wyjscia uruchomienia: " + runExit
                + " (oczekiwane: != 0 - refleksja miala SIE NIE UDAC, silna enkapsulacja JPMS)");
        System.out.println("Realny blad (powyzej) to zwykle IllegalAccessException: \"module secretlib does not open/export secretlib.internal to module probe\".\n");
    }

    /**
     * Zapisuje zrodlo klasy Secret (pakiet "secretlib.internal") - klasa
     * PUBLICZNA z publiczna metoda, ktora na module path bedzie zyla w
     * MODULE "secretlib", nieeksportujacym tego pakietu na zewnatrz.
     */
    private static void writeSecretSource(Path internalDir) throws IOException {
        Files.writeString(internalDir.resolve("Secret.java"), """
                package secretlib.internal;

                public class Secret {
                    public String hiddenValue() {
                        return "tajna wartosc z pakietu 'secretlib.internal'";
                    }
                }
                """);
    }

    /**
     * Zapisuje zrodlo klasy ReflectionProbe (pakiet "probe") - klasa z
     * main(), ktora WYLACZNIE przez refleksje (Class.forName po nazwie,
     * bez zadnego "import secretlib.internal.Secret") probuje wywolac
     * Secret.hiddenValue(). Ten sam, niezmieniony kod dziala inaczej w
     * zaleznosci od tego, czy uruchomimy go na classpath, czy na module
     * path z dwoma osobnymi modulami.
     */
    private static void writeProbeSource(Path probeDir) throws IOException {
        Files.writeString(probeDir.resolve("ReflectionProbe.java"), """
                package probe;

                import java.lang.reflect.Method;

                public class ReflectionProbe {
                    public static void main(String[] args) {
                        try {
                            Class<?> secretClass = Class.forName(
                                    "secretlib.internal.Secret", true, ReflectionProbe.class.getClassLoader());
                            Object secretInstance = secretClass.getDeclaredConstructor().newInstance();
                            Method method = secretClass.getMethod("hiddenValue");
                            Object result = method.invoke(secretInstance);
                            System.out.println("SUKCES refleksji: " + result);
                        } catch (Exception e) {
                            System.out.println("BLAD refleksji: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                            // Sygnalizujemy niepowodzenie kodem wyjscia != 0,
                            // zeby bylo widac to tez w kodzie wyjscia procesu.
                            System.exit(1);
                        }
                    }
                }
                """);
    }

    /**
     * Uruchamia PRAWDZIWY proces systemowy (javac/java) z limitem czasu
     * 10 sekund - jesli proces nie zdazy, zostaje zabity, zeby main()
     * zawsze konczyl sie samoistnie.
     */
    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes());
        }
        if (!output.isEmpty()) {
            System.out.print(output);
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            System.out.println("(TIMEOUT - proces zabity)");
            return -1;
        }
        return process.exitValue();
    }

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
