package com.example.javaquest._11_buildtools.Lesson09_AntIvy;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class _Lesson09_AntIvy {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 9: Ant + Ivy - zarzadzanie zaleznosciami ===\n");

        /*
         * ============================================================
         * 📦 PROBLEM: ANT NIE ZARZADZA ZALEZNOSCIAMI
         * ============================================================
         * W Lekcji 5 (AntClasspath) zaleznosci zewnetrzne (JAR-y bibliotek)
         * trafialy do katalogu lib/ RECZNIE - programista sam je znajdowal,
         * sciagal i wklejal do projektu. To dziala dla 2-3 bibliotek, ale
         * przy prawdziwym projekcie (dziesiatki bibliotek, kazda z WLASNYMI
         * zaleznosciami przechodnimi) rodzi klasyczne problemy:
         *
         *  - RECZNE JAR-y W lib/ - nikt "automatycznie" nie wie, jakiej
         *    wersji jest dany plik, jesli ktos zapomni to zapisac gdzies
         *    obok (np. w nazwie pliku).
         *  - BRAK ZALEZNOSCI PRZECHODNICH (transitive) - biblioteka A
         *    czesto sama wymaga biblioteki B; recznie trzeba znalezc i
         *    dograc WSZYSTKIE zaleznosci A, B, C... w lancuchu.
         *  - KONFLIKTY WERSJI - dwie biblioteki moga wymagac dwoch roznych
         *    wersji tej samej trzeciej biblioteki; reczne lib/ nie ma
         *    zadnego mechanizmu wykrywania/rozwiazywania takich konfliktow.
         *  - DUPLIKATY - latwo przypadkiem wkleic dwa razy ten sam JAR pod
         *    inna nazwa pliku (np. commons-lang-2.6.jar i
         *    commons-lang-old.jar).
         *
         * Apache Ivy to biblioteka do zarzadzania zaleznosciami, ktora
         * DOPISUJE Antowi wlasnie to, czego brakuje - w duchu bardzo
         * podobnym do tego, co pozniej zobaczymy jako WBUDOWANY mechanizm
         * Mavena (Lekcja 12).
         */

        /*
         * ============================================================
         * 🔹 CZYM JEST IVY? ivy.xml, resolve, retrieve, cache
         * ============================================================
         * - ivy.xml - plik opisujacy zaleznosci TWOJEGO modulu (analog
         *   pom.xml w Mavenie, ale wazacy o wiele mniej funkcji).
         * - RESOLVE - Ivy czyta ivy.xml, kontaktuje sie z repozytorium
         *   (domyslnie Maven Central), znajduje kazda zaleznosc I JEJ
         *   zaleznosci przechodnie, sciaga potrzebne JAR-y do LOKALNEGO
         *   CACHE (domyslnie ~/.ivy2/cache) - raz sciagniete, nie trzeba
         *   sciagac ponownie przy kazdym buildzie.
         * - RETRIEVE - kopiuje pliki z cache do katalogu projektu (np.
         *   lib/), zgodnie z podanym wzorcem nazw (pattern).
         *
         * Konfiguracja zaleznosci w ivy.xml (element <dependency>):
         *   <dependency org="commons-lang" name="commons-lang" rev="2.6"/>
         * - org (organisation) - grupa/wydawca biblioteki (jak groupId
         *   w Mavenie).
         * - name - nazwa modulu/artefaktu (jak artifactId w Mavenie).
         * - rev (revision) - wersja (jak version w Mavenie).
         * - conf (configuration) - do jakiej "konfiguracji" (np. default,
         *   compile, test - Ivy pozwala definiowac wlasne nazwane grupy
         *   zaleznosci) dana zaleznosc nalezy.
         */

        /*
         * ============================================================
         * 🔍 IVY + ANT - taskdef antlib, ivy:resolve, ivy:retrieve
         * ============================================================
         * Ivy NIE zastepuje Anta - DOPISUJE mu nowe taski. W XML-u
         * deklarujemy przestrzen nazw "antlib:org.apache.ivy.ant" -
         * Ant sam odnajduje definicje taskow ivy:resolve/ivy:retrieve
         * wewnatrz jara ivy-2.5.2.jar (ktory jest juz zaleznoscia tego
         * projektu w pom.xml), bez potrzeby recznego wskazywania
         * classpath - bo ivy.jar i tak jest na classpath calej naszej
         * aplikacji (a wiec i tego embedded Anta).
         *
         * ⚠️ TA LEKCJA UDERZA W PRAWDZIWA SIEC (Maven Central) - zgodnie
         * z zasada z rozdzialu _06_networking: limit czasu + przyjazny
         * komunikat fallback, gdyby internetu nie bylo. Watek rozwiazujacy
         * zaleznosc jest wątkiem DAEMON, wiec main() zawsze zakonczy sie
         * w rozsadnym czasie, nawet jesli polaczenie sieciowe "zawiesi sie"
         * bez odpowiedzi (klasyczne blokujace IO nie reaguje na przerwanie).
         */

        resolveWithIvy();
        printApproachComparison();

        System.out.println("\n=== KONIEC LEKCJI 9 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Reczne lib/*.jar (Lekcja 5) nie ma zadnego mechanizmu
         *   zaleznosci przechodnich, wersjonowania czy wykrywania
         *   konfliktow - dziala tylko dla malych projektow.
         * - Apache Ivy dodaje Antowi to, czego mu brakuje: ivy.xml opisuje
         *   zaleznosci (org/name/rev/conf), <ivy:resolve> je znajduje i
         *   sciaga (z zaleznosciami przechodnimi!) do lokalnego cache,
         *   <ivy:retrieve> kopiuje je do projektu wedlug wzorca.
         * - Ivy integruje sie z Antem przez wlasne taski Anta (namespace
         *   antlib:org.apache.ivy.ant) - dokladajac logike zarzadzania
         *   zaleznosciami DO istniejacego mechanizmu targetow/tasków.
         * - Maven (Lekcja 12) idzie o krok dalej - dependency management
         *   jest tam WBUDOWANY w sam model pom.xml, bez potrzeby
         *   dodatkowego narzedzia jak Ivy.
         */
    }

    private static void resolveWithIvy() throws Exception {
        System.out.println("--- Rozwiazywanie zaleznosci commons-lang:commons-lang:2.6 przez Ivy ---\n");

        Path baseDir = Files.createTempDirectory("lesson09-ivy");

        Files.writeString(baseDir.resolve("ivy.xml"), """
                <ivy-module version="2.0">
                    <info organisation="com.example.javaquest" module="lesson09-demo"/>
                    <dependencies>
                        <dependency org="commons-lang" name="commons-lang" rev="2.6"/>
                    </dependencies>
                </ivy-module>
                """);

        Path buildFile = baseDir.resolve("build.xml");
        Files.writeString(buildFile, """
                <project name="ivy-demo" default="resolve" basedir="." xmlns:ivy="antlib:org.apache.ivy.ant">

                    <target name="resolve">
                        <ivy:resolve file="ivy.xml"/>
                        <!-- (-[classifier]) jest WAZNE: commons-lang publikuje w tej samej
                             konfiguracji "default" nie tylko glowny jar, ale i osobne
                             artefakty -sources/-javadoc - bez tokenu [classifier] we
                             wzorcu wszystkie trafialyby do JEDNEGO pliku (kolizja nazw). -->
                        <ivy:retrieve pattern="lib/[conf]/[artifact]-[revision](-[classifier]).[ext]"/>
                    </target>

                </project>
                """);

        Project project = new Project();
        project.init();
        project.setBaseDir(baseDir.toFile());

        DefaultLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(logger);

        ProjectHelper.configureProject(project, buildFile.toFile());

        System.out.println("Limit czasu na odpowiedz z Maven Central: 20 sekund.\n");

        // Watek roboczy jest DAEMON - jesli polaczenie sieciowe zawiesi sie
        // na blokujacym IO (co nie reaguje na Thread.interrupt()), main()
        // i tak zakonczy sie samoistnie, bo JVM nie czeka na wątki daemon.
        ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "ivy-resolve");
            thread.setDaemon(true);
            return thread;
        });

        Callable<Exception> resolveTask = () -> {
            try {
                project.executeTarget("resolve");
                return null;
            } catch (Exception e) {
                return e;
            }
        };

        Future<Exception> future = executor.submit(resolveTask);

        try {
            Exception failure = future.get(20, TimeUnit.SECONDS);
            if (failure == null) {
                System.out.println("\nIvy rozwiazal i pobral zaleznosc - zawartosc katalogu lib/:");
                listRetrievedFiles(baseDir.resolve("lib"));
            } else {
                printFriendlyFallback(failure.getMessage());
            }
        } catch (TimeoutException e) {
            System.out.println("\nPrzekroczono limit czasu (20s) - brak odpowiedzi z Maven Central.");
            printFriendlyFallback("timeout polaczenia sieciowego");
        } finally {
            executor.shutdownNow();
        }
    }

    private static void printFriendlyFallback(String reason) {
        System.out.println("Nie udalo sie rozwiazac zaleznosci przez Ivy (przyczyna: " + reason + ").");
        System.out.println("Gdyby polaczenie z Maven Central sie powiodlo, Ivy:");
        System.out.println("  1) sciagnalby commons-lang-2.6.jar (i ewentualne zaleznosci przechodnie)");
        System.out.println("     do lokalnego cache (domyslnie ~/.ivy2/cache),");
        System.out.println("  2) skopiowalby go (ivy:retrieve) do lokalnego katalogu lib/default/,");
        System.out.println("  3) przy KOLEJNYM buildzie uzylby juz cache - bez ponownego sciagania.");
        System.out.println("To jest DOKLADNIE ten problem, ktorego reczny lib/*.jar z Lekcji 5 NIE rozwiazuje.");
    }

    private static void listRetrievedFiles(Path libDir) throws Exception {
        if (!Files.exists(libDir)) {
            System.out.println("  (katalog lib/ nie zostal utworzony)");
            return;
        }
        try (Stream<Path> walk = Files.walk(libDir)) {
            List<Path> files = walk.filter(Files::isRegularFile).sorted().toList();
            for (Path file : files) {
                System.out.println("  " + libDir.relativize(file) + " (" + Files.size(file) + " B)");
            }
        }
    }

    private static void printApproachComparison() {
        System.out.println("\n--- Trzy podejscia do zaleznosci w tym kursie ---");
        System.out.println("Lekcja 05 (reczny lib/*.jar): programista SAM sciaga kazdy JAR i wkleja go do lib/;");
        System.out.println("                               zero automatycznych zaleznosci przechodnich.");
        System.out.println("Lekcja 09 (Ant + Ivy, ta lekcja): ivy.xml opisuje 'co', Ivy SAM znajduje i sciaga");
        System.out.println("                               (wraz z zaleznosciami przechodnimi) z Maven Central.");
        System.out.println("Lekcja 12 (Maven, pozniej): zarzadzanie zaleznosciami WBUDOWANE w sam pom.xml -");
        System.out.println("                               nie potrzeba dodatkowego narzedzia jak Ivy.");
    }
}
