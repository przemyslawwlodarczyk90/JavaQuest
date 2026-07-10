package com.example.javaquest._15_jvm_internals.Lesson18_JavaFlightRecorderBasics;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Recording;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class _Lesson18_JavaFlightRecorderBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: JAVA FLIGHT RECORDER (JFR) - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST JAVA FLIGHT RECORDER
         * ============================================================
         * - JFR to WBUDOWANY w JDK (od Java 11 w pelni darmowy i otwarty -
         *   wczesniej byl czescia komercyjnego "Oracle Flight Recorder")
         *   mechanizm rejestrowania ZDARZEN (events) zachodzacych w JVM
         *   i aplikacji: alokacje, pauzy GC (Lesson08-12), kompilacje JIT
         *   (Lesson13-14), obciazenie CPU, operacje I/O, wyjatki i wiele
         *   innych - a takze WLASNE zdarzenia zdefiniowane przez programiste.
         * - KLUCZOWA cecha: bardzo NISKI narzut (typowo <1-2%), dzieki
         *   czemu JFR mozna zostawic wlaczony CIAGLE, rowniez na
         *   PRODUKCJI - w odroznieniu od tradycyjnych profilerow
         *   (np. instrumentujacych bajtkod), ktore zwykle wlacza sie
         *   TYLKO na chwile, do jednorazowego profilowania (patrz Lesson19).
         * - Wynik nagrania to plik .jfr - binarny "dziennik zdarzen"
         *   (w odroznieniu od .hprof z Lesson16, ktory jest STATYCZNYM
         *   zrzutem sterty w jednym momencie, .jfr opisuje, co dzialo
         *   sie W CZASIE, przez cale trwanie nagrania).
         */
        System.out.println("JFR = wbudowany, niskonarzutowy rejestrator zdarzen JVM - dziala tez na produkcji.\n");

        /*
         * ============================================================
         * 🔹 jdk.jfr.Recording - PROGRAMOWE STEROWANIE NAGRANIEM
         * ============================================================
         * - Recording reprezentuje POJEDYNCZE nagranie - tworzone,
         *   konfigurowane (enable/disable konkretnych typow zdarzen),
         *   startowane i zatrzymywane w calosci z poziomu kodu Javy.
         * - enable("nazwa.zdarzenia") wlacza WBUDOWANE zdarzenie JDK,
         *   np. "jdk.CPULoad" (okresowy pomiar obciazenia CPU) albo
         *   "jdk.GarbageCollection" (kazde wykonanie GC).
         * - Mozna tez wlaczyc WLASNE zdarzenie, definiujac klase
         *   rozszerzajaca jdk.jfr.Event (ponizej: ComputationEvent) -
         *   dokladnie tak, jak biblioteki/frameworki dodaja WLASNE
         *   zdarzenia do JFR obok wbudowanych zdarzen JDK.
         */
        demonstrateJfrRecording();

        /*
         * ============================================================
         * 🔍 CZYM OTWORZYC PLIK .jfr - JDK Mission Control (JMC)
         * ============================================================
         * - JDK Mission Control (JMC) to oficjalne narzedzie GUI Oracle/
         *   OpenJDK do analizy plikow .jfr - pokazuje m.in. wykresy
         *   obciazenia CPU/pamieci w czasie, timeline pauz GC, hot methods
         *   (jesli wlaczono profilowanie metod), automatyczne "reguly"
         *   wykrywajace typowe problemy (np. zbyt czeste GC, zbyt duzy
         *   alokacyjny "churn").
         * - JMC umie tez URUCHAMIAC i zarzadzac nagraniami zdalnie (przez
         *   JMX), a nie tylko otwierac gotowe pliki .jfr.
         * - Bundled CLI `jfr` (ten sam katalog bin/ co java/javac) pozwala
         *   szybko podejrzec zawartosc pliku BEZ GUI - patrz nizej.
         */
        explainJmc();

        /*
         * ============================================================
         * 📌 BUNDLED CLI: `jfr summary <plik>`
         * ============================================================
         * JDK dostarcza narzedzie linii komend `jfr` (w tym samym
         * katalogu co `java`/`javac`), ktore m.in. potrafi wypisac
         * szybkie podsumowanie nagrania bez otwierania JMC - przydatne
         * np. na serwerze bez GUI. Ponizej uruchamiamy je na PRAWDZIWYM
         * pliku wygenerowanym powyzej.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    /**
     * Wlasne zdarzenie JFR - obok wbudowanych zdarzen JDK (np. jdk.CPULoad)
     * mozna definiowac WLASNE, domenowe zdarzenia rozszerzajac jdk.jfr.Event.
     */
    @Label("Operacja obliczeniowa JavaQuest")
    @Description("Wlasne zdarzenie JFR emitowane podczas demo obciazenia w Lekcji 18")
    @Category({"JavaQuest", "Lesson18"})
    private static class ComputationEvent extends Event {
        @Label("Nazwa operacji")
        String operationName;

        @Label("Wynik operacji")
        long result;
    }

    private static void demonstrateJfrRecording() {
        System.out.println("=== jdk.jfr.Recording - PRAWDZIWE NAGRANIE .jfr ===");

        Path dumpFile = null;
        try (Recording recording = new Recording()) {
            recording.enable("jdk.CPULoad").withPeriod(Duration.ofMillis(100));
            recording.enable("jdk.GarbageCollection");
            recording.enable(ComputationEvent.class).withStackTrace();
            recording.setName("Lesson18-Demo");

            recording.start();
            System.out.println("Nagranie JFR wystartowane - wykonuje krotkie obciazenie (~1s)...");

            runShortWorkloadAndEmitEvents();

            recording.stop();
            System.out.println("Nagranie JFR zatrzymane.");

            Path tempDir = Files.createTempDirectory("javaquest-lesson18-jfr");
            dumpFile = tempDir.resolve("lesson18-recording.jfr");
            recording.dump(dumpFile);

            boolean exists = Files.exists(dumpFile);
            long sizeBytes = exists ? Files.size(dumpFile) : 0L;

            System.out.println("Plik .jfr utworzony: " + exists);
            System.out.println("Sciezka: " + dumpFile.toAbsolutePath());
            System.out.println("Rozmiar: " + formatBytes(sizeBytes));
            System.out.println("UWAGA: plik CELOWO NIE jest usuwany - otworz go recznie w JDK Mission Control.");
        } catch (IOException e) {
            System.out.println("Nie udalo sie wykonac nagrania JFR: " + e);
        }

        if (dumpFile != null) {
            runJfrSummaryTool(dumpFile);
        }
    }

    private static void runShortWorkloadAndEmitEvents() {
        long start = System.currentTimeMillis();
        long budgetMillis = 1000;
        List<byte[]> garbage = new ArrayList<>();
        int iteration = 0;

        while (System.currentTimeMillis() - start < budgetMillis) {
            garbage.add(new byte[10_000]);
            if (garbage.size() > 500) {
                garbage.clear(); // pozwol GC posprzatac, zeby wygenerowac zdarzenia jdk.GarbageCollection
            }

            ComputationEvent event = new ComputationEvent();
            event.begin();
            long result = fibonacciIterative(1000 + iteration);
            event.operationName = "fibonacci-" + iteration;
            event.result = result;
            event.commit();

            iteration++;
        }

        System.out.println("Obciazenie zakonczone - wykonano " + iteration + " iteracji, wyemitowano tyle samo ComputationEvent.");
    }

    private static long fibonacciIterative(int n) {
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            long next = (a + b) % 1_000_000_007L;
            a = b;
            b = next;
        }
        return a;
    }

    private static void runJfrSummaryTool(Path jfrFile) {
        System.out.println("\n=== `jfr summary <plik>` (bundled CLI z JDK) ===");
        String jfrTool = System.getProperty("java.home") + File.separator + "bin" + File.separator + "jfr";

        try {
            ProcessBuilder builder = new ProcessBuilder(jfrTool, "summary", jfrFile.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();

            List<String> outputLines = new ArrayList<>();
            try (var reader = process.inputReader()) {
                String line;
                while ((line = reader.readLine()) != null && outputLines.size() < 20) {
                    outputLines.add(line);
                }
            }

            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                System.out.println("Narzedzie `jfr summary` nie zdazylo sie zakonczyc w 10s - przerwano.");
                return;
            }

            System.out.println("Fragment realnego outputu `jfr summary` (pierwsze " + outputLines.size() + " linii):");
            for (String line : outputLines) {
                System.out.println("   " + line);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Nie udalo sie uruchomic narzedzia `jfr` (" + jfrTool + "): " + e.getMessage());
        }
    }

    private static void explainJmc() {
        System.out.println("\n=== JDK Mission Control (JMC) - GUI do analizy .jfr ===");
        System.out.println(" - wykresy CPU/pamieci w czasie, timeline pauz GC");
        System.out.println(" - automatyczne reguly wykrywajace typowe problemy");
        System.out.println(" - moze tez zdalnie sterowac nagraniami przez JMX, nie tylko otwierac gotowe pliki");
    }

    private static String formatBytes(long bytes) {
        if (bytes < 0) {
            return "n/a";
        }
        double mb = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB (%d B)", mb, bytes);
    }
}
