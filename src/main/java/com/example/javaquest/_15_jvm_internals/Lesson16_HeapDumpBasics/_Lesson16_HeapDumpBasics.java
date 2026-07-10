package com.example.javaquest._15_jvm_internals.Lesson16_HeapDumpBasics;

import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson16_HeapDumpBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: HEAP DUMP - PRAWDZIWY ZRZUT STERTY JVM ===");

        /*
         * ============================================================
         * 📦 CO TO JEST HEAP DUMP I PO CO SIE GO ROBI
         * ============================================================
         * - Heap dump (plik .hprof) to KOMPLETNY, BINARNY zrzut calej
         *   sterty JVM w danym momencie - WSZYSTKIE zywe (lub wszystkie,
         *   zaleznie od opcji) obiekty, ich pola, rozmiary oraz
         *   referencje MIEDZY nimi (kto trzyma referencje do kogo).
         * - Glowne zastosowanie: analiza WYCIEKOW PAMIECI (nawiazanie do
         *   Lesson15_MemoryLeaks) - kiedy samo obserwowanie "used" rosnie
         *   w czasie (MemoryMXBean, Lesson06) nie wystarcza, potrzeba
         *   zobaczyc DOKLADNIE, jakie obiekty zapychaja sterte i KTO
         *   je trzyma (root -> ... -> obiekt-wyciek).
         * - Drugie zastosowanie: analiza pojedynczego OutOfMemoryError -
         *   mozna nawet skonfigurowac JVM (-XX:+HeapDumpOnOutOfMemoryError),
         *   zeby SAMA zrobila heap dump w momencie OOM.
         * - Heap dump to STATYCZNY snapshot ("zdjecie") - w odroznieniu
         *   od Java Flight Recorder (Lesson18), ktory rejestruje zdarzenia
         *   W CZASIE.
         */
        System.out.println("Heap dump to pelny zrzut binarny sterty JVM - wszystkie obiekty + ich referencje.\n");

        /*
         * ============================================================
         * 🔹 JAK ZROBIC HEAP DUMP Z POZIOMU KODU: HotSpotDiagnosticMXBean
         * ============================================================
         * - java.lang.management udostepnia standardowe MXBeany (MemoryMXBean,
         *   ThreadMXBean - Lesson17), ale generowanie heap dumpu to funkcja
         *   SPECYFICZNA dla HotSpota (implementacji JVM od Oracle/OpenJDK) -
         *   dostepna przez com.sun.management.HotSpotDiagnosticMXBean.
         * - ManagementFactory.getPlatformMXBean(Class) pozwala pobrac DOWOLNY
         *   MXBean zarejestrowany w tej JVM po jego typie - tutaj pobieramy
         *   wlasnie HotSpotDiagnosticMXBean.
         * - Metoda dumpHeap(String filePath, boolean live):
         *     * filePath - sciezka docelowa; PLIK NIE MOZE JUZ ISTNIEC,
         *       inaczej dumpHeap rzuci IOException.
         *     * live=true  -> zrzuca TYLKO obiekty osiagalne (zywe) -
         *       JVM najpierw robi pelny GC, zeby usunac smieci ze zrzutu.
         *     * live=false -> zrzuca WSZYSTKIE obiekty na stercie, rowniez
         *       te juz nieosiagalne, ale jeszcze nie posprzatane przez GC.
         */
        demonstrateHeapDump();

        /*
         * ============================================================
         * 🔍 CZYM OTWORZYC PLIK .hprof - NARZEDZIA GUI
         * ============================================================
         * Plik .hprof to format binarny - nie da sie go sensownie czytac
         * jako tekst. Do analizy sluza dedykowane narzedzia GUI:
         *
         * - JDK Mission Control (JMC) - oficjalne narzedzie Oracle/OpenJDK,
         *   umie tez otwierac heap dumpy (obok plikow .jfr z Lesson18).
         * - VisualVM - lzejsze, historycznie dolaczane do JDK, dalej
         *   dostepne jako osobny projekt (visualvm.github.io).
         * - Eclipse Memory Analyzer (Eclipse MAT) - najbardziej
         *   wyspecjalizowane narzedzie do analizy WYCIEKOW pamieci,
         *   kluczowe pojecia:
         *     * "Dominator Tree" - drzewo pokazujace, ktory obiekt
         *       "dominuje" nad innymi, tzn. gdyby zniknal, tamte obiekty
         *       staly by sie nieosiagalne (czesto wskazuje realne "korzenie"
         *       wycieku, np. jedna wielka mapa/cache).
         *     * "Retained Size" - ile pamieci FAKTYCZNIE zwolniloby sie,
         *       gdyby ten JEDEN obiekt zniknal (w odroznieniu od "Shallow
         *       Size" - rozmiaru samego obiektu, bez tego co on trzyma).
         *     * "Leak Suspects Report" - automatyczny raport MAT, ktory
         *       sam probuje wskazac najbardziej podejrzane "duze" grupy
         *       obiektow.
         */
        explainAnalysisTools();

        /*
         * ============================================================
         * 📌 ALTERNATYWY: jmap I jcmd (Z LINII KOMEND, BEZ ZMIANY KODU)
         * ============================================================
         * Heap dump mozna tez zrobic z ZEWNATRZ dzialajacego procesu,
         * bez zadnego kodu w aplikacji - przydatne na produkcji, gdy
         * aplikacji nie mozna zatrzymac/zmodyfikowac:
         *
         *   jmap -dump:live,format=b,file=heap.hprof <PID>
         *   jcmd <PID> GC.heap_dump heap.hprof
         *
         * `jcmd` jest nowszym, zalecanym narzedziem (jmap bywa oznaczany
         * jako "experimental"/deprecated w niektorych wersjach JDK). Oba
         * wymagaja znajomosci PID procesu JVM (np. z `jps`).
         */
        explainCliAlternatives();

        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static void demonstrateHeapDump() {
        System.out.println("=== HotSpotDiagnosticMXBean.dumpHeap(...) - PRAWDZIWY PLIK .hprof ===");

        // Troche "smieci" na stercie, zeby zrzut mial co pokazac.
        List<String> garbage = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) {
            garbage.add("obiekt-testowy-numer-" + i);
        }
        System.out.println("Zaalokowano " + garbage.size() + " tymczasowych obiektow String na potrzeby demo.");

        try {
            Path tempDir = Files.createTempDirectory("javaquest-lesson16-heapdump");
            Path dumpFile = tempDir.resolve("lesson16-heap.hprof");

            HotSpotDiagnosticMXBean diagnosticBean =
                    ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);

            // liveOnly = true -> tylko zywe obiekty (JVM najpierw robi pelny GC)
            diagnosticBean.dumpHeap(dumpFile.toString(), true);

            boolean exists = Files.exists(dumpFile);
            long sizeBytes = exists ? Files.size(dumpFile) : 0L;

            System.out.println("Plik .hprof utworzony: " + exists);
            System.out.println("Sciezka: " + dumpFile.toAbsolutePath());
            System.out.println("Rozmiar: " + formatBytes(sizeBytes));
            System.out.println("UWAGA: plik CELOWO NIE jest usuwany - otworz go recznie w JDK Mission Control,");
            System.out.println("VisualVM lub Eclipse MAT, zeby zobaczyc realna zawartosc sterty tej JVM.");
        } catch (IOException e) {
            System.out.println("Nie udalo sie utworzyc heap dumpu: " + e.getMessage());
        }

        // Pozwalamy referencji "garbage" pozostac do konca metody, zeby na 100%
        // byla jeszcze zywa (osiagalna) w momencie robienia zrzutu powyzej.
        System.out.println("(referencja do listy garbage nadal zywa: " + (garbage.size() > 0) + ")");
    }

    private static void explainAnalysisTools() {
        System.out.println("\n=== NARZEDZIA DO ANALIZY .hprof (koncepcyjnie - GUI) ===");
        System.out.println(" - JDK Mission Control (JMC): oficjalne narzedzie Oracle/OpenJDK");
        System.out.println(" - VisualVM: lzejsze, historycznie dolaczane do JDK");
        System.out.println(" - Eclipse Memory Analyzer (MAT): 'Dominator Tree', 'Retained Size', 'Leak Suspects Report'");
    }

    private static void explainCliAlternatives() {
        System.out.println("\n=== ALTERNATYWY CLI (bez zmiany kodu aplikacji) ===");
        System.out.println(" jmap -dump:live,format=b,file=heap.hprof <PID>   (starsze, czasem 'experimental')");
        System.out.println(" jcmd <PID> GC.heap_dump heap.hprof               (nowsze, zalecane)");
        System.out.println(" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=... (automatyczny zrzut przy OOM)");
    }

    private static String formatBytes(long bytes) {
        if (bytes < 0) {
            return "n/a";
        }
        double mb = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB (%d B)", mb, bytes);
    }
}
