package com.example.javaquest._15_jvm_internals.Lesson10_G1GcDeepDive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson10_G1GcDeepDive {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== LEKCJA 10: G1 GC W SZCZEGOLACH ===");

        /*
         * ============================================================
         * 🔹 PRZYPOMNIENIE Z Lesson09: DLACZEGO WLASNIE G1?
         * ============================================================
         * - G1 (Garbage First) to DOMYSLNY kolektor od Javy 9 - kompromis
         *   miedzy throughput a latency, z celem PRZEWIDYWALNYCH,
         *   krotkich pauz nawet na duzych stertach.
         * - Ta lekcja rozbiera G1 na czesci pierwsze - jak DOKLADNIE
         *   osiaga ten kompromis, ktorego Serial/Parallel (jednolite
         *   generacje) nie potrafily.
         */
        System.out.println("G1 = kompromis throughput/latency, z celem PRZEWIDYWALNYCH pauz na duzych stertach.");

        /*
         * ============================================================
         * 📌 REGIONY (REGIONS) - FUNDAMENTALNA ROZNICA WZGLEDEM SERIAL/PARALLEL
         * ============================================================
         * - Serial/Parallel dziela sterte na 2 CIAGLE bloki pamieci
         *   (mloda generacja + stara generacja) o STALYCH granicach.
         * - G1 dzieli CALA sterte na WIELE malych, RONWYCH regionow
         *   (typowo 1-32 MB kazdy, liczba regionow ~2048, rozmiar dobierany
         *   automatycznie na podstawie rozmiaru sterty).
         * - KAZDY region moze peini role Eden, Survivor ALBO Old - te
         *   role nie sa przypisane na sztywno do adresu pamieci, tylko
         *   dynamicznie, w zaleznosci od aktualnych potrzeb.
         * - Efekt: G1 NIE MUSI sprzatac CALEJ mlodej lub CALEJ starej
         *   generacji naraz - moze wybrac DOWOLNY podzbior regionow do
         *   posprzatania w danym cyklu.
         */
        System.out.println("\nRegiony: sterta podzielona na ~2048 malych, rownych czesci - kazda moze byc Eden/Survivor/Old.");

        /*
         * ============================================================
         * 🔍 "GARBAGE FIRST" - WYBOR REGIONOW Z NAJWIEKSZA ILOSCIA SMIECI
         * ============================================================
         * - Skad nazwa "Garbage First"? G1 sledzi (w przyblizeniu), ile
         *   "smiecia" (nieosiagalnych obiektow) jest w KAZDYM regionie.
         * - Przy kazdym cyklu GC, G1 WYBIERA do sprzatniecia regiony z
         *   NAJWIEKSZA iloscia smiecia PIERWSZE - bo tam odzysk pamieci
         *   "kosztuje" (czas GC) najmniej wzgledem "zysku" (odzyskana pamiec).
         * - Ten wybrany zestaw regionow do sprzatniecia w danym cyklu to
         *   tzw. COLLECTION SET (CSet).
         */
        System.out.println("Nazwa 'Garbage First': G1 sprzata NAJPIERW regiony z najwieksza iloscia smiecia (najlepszy zysk/koszt).");

        /*
         * ============================================================
         * 📌 REMEMBERED SETS (RSETS) - SLEDZENIE REFERENCJI MIEDZY REGIONAMI
         * ============================================================
         * - Problem: skoro region moze byc sprzatany NIEZALEZNIE od
         *   innych, skad G1 wie, czy obiekt w regionie A jest osiagalny
         *   z referencji trzymanej w regionie B (spoza analizowanego zestawu)?
         * - Rozwiazanie: KAZDY region ma wlasny "remembered set" (RSet) -
         *   strukture zapamietujaca, KTORE obiekty z INNYCH regionow
         *   wskazuja NA obiekty W TYM regionie.
         * - Dzieki RSet, G1 NIE MUSI przeszukiwac CALEJ sterty, zeby
         *   sprawdzic osiagalnosc obiektow w jednym regionie - wystarczy
         *   przejrzec jego (znacznie mniejszy) RSet. To wlasnie
         *   UMOZLIWIA sprzatanie POJEDYNCZYCH regionow zamiast calych generacji.
         * - Koszt: aktualizacja RSet przy KAZDYM zapisie referencji
         *   (write barrier) - to ta "dodatkowa ksiegowosc", ktora
         *   odpowiada za nieco wiekszy footprint G1 wzgledem Serial/Parallel.
         */
        System.out.println("Remembered Sets: kazdy region 'pamieta', kto z INNYCH regionow do niego wskazuje.");

        /*
         * ============================================================
         * 🔍 MIXED COLLECTIONS - MLODE + CZESC STARYCH REGIONOW NARAZ
         * ============================================================
         * - Zwykly cykl G1 ("Pause Young") sprzata TYLKO regiony mlode
         *   (Eden + Survivor) - jak minor GC w Serial/Parallel.
         * - Gdy zajetosc starych regionow przekroczy prog
         *   (-XX:InitiatingHeapOccupancyPercent, domyslnie 45%), G1
         *   uruchamia WSPOLBIEZNY cykl oznaczania (concurrent marking) -
         *   identyfikuje, KTORE stare regiony maja najwiecej smiecia.
         * - Nastepnie wykonuje MIXED COLLECTION - pojedynczy cykl GC,
         *   ktory sprzata regiony MLODE ORAZ WYBRANY PODZBIOR regionow
         *   STARYCH (te z najwieksza iloscia smiecia) NARAZ - stad nazwa
         *   "mixed" (mieszany).
         * - To fundamentalnie inne podejscie niz "Full GC sprzata WSZYSTKO
         *   naraz" - G1 stara sie NIGDY nie dojsc do pelnego Full GC,
         *   sprzatajac stara generacje STOPNIOWO, malymi kawalkami.
         */
        System.out.println("Mixed Collection: mlode regiony + WYBRANE stare regiony (z najwieksza iloscia smiecia) naraz.");

        /*
         * ============================================================
         * 📌 CEL PAUZY: -XX:MaxGCPauseMillis
         * ============================================================
         * - -XX:MaxGCPauseMillis=<ms> (domyslnie 200ms) to NIE twardy
         *   limit, tylko CEL (soft goal), do ktorego G1 stara sie dazyc.
         * - G1 uzywa tego celu, zeby DYNAMICZNIE dobrac liczbe regionow do
         *   sprzatniecia w JEDNYM cyklu - jesli chcesz krotsze pauzy,
         *   ustawiasz nizsza wartosc, a G1 bedzie sprzatal MNIEJ regionow
         *   na raz (czesciej, ale krocej).
         * - Kompromis: zbyt niska wartosc MaxGCPauseMillis moze zmusic G1
         *   do sprzatania MNIEJ regionow niz potrzeba, co przy duzym
         *   tempie alokacji moze prowadzic do "Evacuation Failure" albo
         *   ostatecznie do Full GC (najgorszy przypadek, ktoremu G1
         *   probuje zapobiec).
         */
        System.out.println("-XX:MaxGCPauseMillis (domyslnie 200ms) to CEL (soft goal), nie twardy limit pauzy.");

        /*
         * ============================================================
         * 📌 REALNE DEMO: CHILD JVM Z G1 I SZCZEGOLOWYM LOGIEM -Xlog:gc*
         * ============================================================
         * - Uruchamiamy maly, samodzielny program (jak w Lesson09) w
         *   procesie potomnym z jawnym -XX:+UseG1GC oraz -Xlog:gc* (log
         *   ze WSZYSTKICH tagow zwiazanych z GC, nie tylko podstawowym
         *   -Xlog:gc) i skromnym heapem (-Xmx24m), zeby WYMUSIC wiecej
         *   niz jedna pauze mlodego GC w krotkim czasie.
         * - UCZCIWA UWAGA: w tak krotkim, bounded demo NIE MA GWARANCJI
         *   zobaczenia realnej fazy "mixed"/concurrent marking (potrzebuje
         *   wiecej czasu i danych niz bezpieczny, kilkusekundowy przyklad) -
         *   ponizszy kod komentuje FAKTYCZNIE zaobserwowane linie logu
         *   (najczesciej "Pause Young"), zamiast obiecywac cos, czego
         *   nie moze zagwarantowac w tak krotkim oknie czasowym.
         */
        runG1DeepDiveDemo();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - G1 dzieli sterte na regiony (nie sztywne generacje) - kazdy
         *   region moze byc Eden/Survivor/Old dynamicznie.
         * - Remembered Sets pozwalaja sprzatac POJEDYNCZE regiony bez
         *   przeszukiwania calej sterty.
         * - Collection Set to zestaw regionow wybranych do sprzatniecia w
         *   danym cyklu - "Garbage First" = najpierw te z najwieksza
         *   iloscia smiecia.
         * - Mixed Collection = mlode + wybrane stare regiony naraz -
         *   stopniowe sprzatanie starej generacji, unikanie Full GC.
         * - -XX:MaxGCPauseMillis to CEL (nie gwarancja), na podstawie
         *   ktorego G1 dobiera, ile regionow sprzatnac na raz.
         * - To byla ostatnia lekcja tego bloku (Lesson06-10) - kolejne
         *   lekcje rozdzialu (Lesson11+) rozwijaja temat pamieci i
         *   wydajnosci JVM dalej.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void runG1DeepDiveDemo() throws IOException, InterruptedException {
        System.out.println("\n=== REALNE URUCHOMIENIE: G1 GC Z -Xlog:gc* NA MALYM HEAPIE ===");

        Path tempDir = Files.createTempDirectory("lesson10-g1-demo");
        Path sourceFile = tempDir.resolve("G1DemoApp.java");
        Files.writeString(sourceFile, buildG1DemoAppSource(), StandardCharsets.UTF_8);

        String javaExecutable = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        try {
            String output = runProcessAndCaptureOutput(
                    "G1 GC, -Xmx24m, -Xlog:gc*",
                    javaExecutable, "-Xmx24m", "-XX:+UseG1GC", "-Xlog:gc*:stdout:time,level,tags",
                    sourceFile.toString());

            long pauseYoungLines = output.lines().filter(line -> line.contains("Pause Young")).count();
            long concurrentStartLines = output.lines().filter(line -> line.contains("Concurrent Start")).count();
            long mixedLines = output.lines().filter(line -> line.contains("Mixed")).count();
            long fullGcLines = output.lines().filter(line -> line.contains("Pause Full")).count();

            System.out.println("\n--- ANALIZA ZAOBSERWOWANEGO LOGU ---");
            System.out.println("Linii 'Pause Young'            : " + pauseYoungLines);
            System.out.println("Linii 'Concurrent Start'        : " + concurrentStartLines
                    + (concurrentStartLines > 0 ? " (rozpoczecie wspolbieznego oznaczania - IHOP przekroczony)" : " (nie wystapilo w tym krotkim demo - to normalne)"));
            System.out.println("Linii 'Mixed'                   : " + mixedLines
                    + (mixedLines > 0 ? " (G1 sprzatal takze CZESC starych regionow)" : " (brak - demo za krotkie/za maly heap na pelny cykl mixed)"));
            System.out.println("Linii 'Pause Full' (Full GC)    : " + fullGcLines
                    + (fullGcLines > 0 ? " (G1 NIE zdazyl unikac Full GC - heap byl za maly na te alokacje)" : " (G1 uniknal Full GC - dobry znak)"));
            System.out.println("\nUwaga: dokladna liczba i rodzaj wystapionych faz zalezy od JDK/JIT/systemu -");
            System.out.println("to jest REALNY, a nie z gory zalozony wynik tego konkretnego uruchomienia.");
        } finally {
            Files.deleteIfExists(sourceFile);
            Files.deleteIfExists(tempDir);
        }
    }

    private static String buildG1DemoAppSource() {
        return """
                import java.util.ArrayList;
                import java.util.List;

                public class G1DemoApp {
                    public static void main(String[] args) {
                        List<byte[]> retained = new ArrayList<>();
                        long checksum = 0;
                        for (int i = 0; i < 500_000; i++) {
                            byte[] chunk = new byte[64];
                            checksum += chunk.length + (i % 3);
                            if (i % 250 == 0 && retained.size() < 2000) {
                                retained.add(chunk); // czesc obiektow celowo dluzej zyjaca
                            }
                        }
                        System.out.println("G1DemoApp zakonczony, checksum=" + checksum + ", retained=" + retained.size());
                    }
                }
                """;
    }

    /**
     * Uruchamia podany program jako proces potomny (child JVM), zwraca jego
     * polaczony output (stdout+stderr) jako String. Zawsze ograniczony
     * czasowo - przy przekroczeniu limitu proces jest zabijany, zeby main()
     * nigdy sie nie zawiesil.
     */
    private static String runProcessAndCaptureOutput(String description, String... command)
            throws IOException, InterruptedException {
        System.out.println("\n>>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        if (!output.isEmpty()) {
            System.out.print(output);
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            System.out.println("(TIMEOUT - proces zabity)");
        }
        return output;
    }
}
