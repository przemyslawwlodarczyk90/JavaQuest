package com.example.javaquest._25_unit_testing.Lesson19_CodeCoverageBasics;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;
import org.jacoco.core.analysis.ICounter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class _Lesson19_CodeCoverageBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 19: Pokrycie kodu testami (code coverage) - JaCoCo ===");

        /*
         * ============================================================
         * 📦 Pokrycie kodu = METRYKA, NIE CEL SAM W SOBIE
         * ============================================================
         * "Code coverage" MIERZY, JAKI PROCENT linii/galezi/metod
         * ZOSTAL WYKONANY przez testy. WYSOKIE pokrycie NIE OZNACZA
         * "dobrych" testow (MOZNA wywolac kod BEZ zadnej asercji I
         * uzyskac 100% pokrycia BEZ sprawdzenia CZEGOKOLWIEK) - ALE
         * NISKIE pokrycie NIEZAWODNIE pokazuje kod, KTORY W OGOLE NIE
         * jest testowany.
         *
         * 🔍 W TEJ lekcji uzywamy PRAWDZIWEGO API `org.jacoco.core`
         * (NIE Maven pluginu) DO faktycznego ZINSTRUMENTOWANIA klasy
         * W LOCIE (bajtkod dostaje "sondy" zliczajace wykonanie),
         * URUCHOMIENIA jej, I ANALIZY wynikow - TEN SAM duch "embeduj
         * i naprawdę uruchom" co PMD/SpotBugs W
         * `_16_clean_code/Lesson20`.
         */
        System.out.println("Pokrycie = METRYKA (ile kodu wykonaly testy), NIE dowod POPRAWNOSCI. JaCoCo instrumentuje bajtkod SONDAMI zliczajacymi wykonanie.");

        demonstratePartialCoverage();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Pokrycie LINII - ILE linii kodu ZOSTALO wykonanych.
         * - Pokrycie GALEZI (branch) - ILE GALEZI `if`/`switch`
         *   ZOSTALO wykonanych (BARDZIEJ wymagajace NIZ pokrycie
         *   linii - LINIA `if (x) A else B` MOZE byc "pokryta", ALE
         *   TYLKO JEDNA galaz FAKTYCZNIE wykonana).
         * - W PRAWDZIWYM projekcie: `jacoco-maven-plugin` (`prepare-
         *   agent` + `report`) - JVM agent ZBIERA dane PODCZAS
         *   `mvn test`, WYNIK trafia DO `target/site/jacoco/index.html`.
         * - 100% pokrycia NIE GWARANTUJE braku bledow (BRAK asercji =
         *   BRAK sprawdzenia, TYLKO wykonanie) - ROZSADNY cel TO
         *   raczej "WYSOKIE pokrycie KRYTYCZNEJ logiki biznesowej",
         *   NIE "100% wszedzie za wszelka cene".
         * - Narzedzia POKREWNE: JaCoCo (Java, uzyte tutaj), Istanbul/
         *   nyc (JavaScript), coverage.py (Python) - IDEA identyczna
         *   WSZEDZIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    /**
     * Klasa "pod testem" - CELOWO ma galaz `if`/`else`, ktorej TYLKO CZESC
     * zostanie wykonana w tym demo, zeby zademonstrowac CZESCIOWE (yellow) pokrycie.
     */
    public static class DiscountEligibilityChecker implements Runnable {
        public String lastResult;

        @Override
        public void run() {
            // CELOWO wywolujemy TYLKO galaz "ELIGIBLE" - galaz "NOT_ELIGIBLE"
            // ponizej NIGDY nie zostanie wykonana w tym demo.
            lastResult = classify(150);
        }

        public String classify(int orderTotal) {
            if (orderTotal >= 100) {
                return "ELIGIBLE";
            } else {
                return "NOT_ELIGIBLE";
            }
        }
    }

    private static void demonstratePartialCoverage() throws Exception {
        String targetName = DiscountEligibilityChecker.class.getName();

        // 1. IRuntime - "srodowisko" JaCoCo, ktore bedzie zbieralo dane wykonania.
        IRuntime runtime = new LoggerRuntime();

        // 2. Instrumenter - TWORZY zmodyfikowana wersje bajtkodu klasy Z DODANYMI
        //    "sondami" (probes) zliczajacymi, KTORE linie/galezie zostaly wykonane.
        Instrumenter instrumenter = new Instrumenter(runtime);
        byte[] instrumented;
        try (InputStream original = DiscountEligibilityChecker.class.getResourceAsStream(
                "/" + targetName.replace('.', '/') + ".class")) {
            instrumented = instrumenter.instrument(original, targetName);
        }

        // 3. Uruchamiamy runtime (rejestruje sie jako odbiorca danych z sond).
        RuntimeData data = new RuntimeData();
        runtime.startup(data);

        // 4. Ladujemy ZINSTRUMENTOWANA wersje klasy przez WLASNY ClassLoader
        //    (dzialajacy na bajtach w pamieci, NIE na oryginalnym pliku .class).
        MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
        memoryClassLoader.addDefinition(targetName, instrumented);
        Class<?> instrumentedClass = memoryClassLoader.loadClass(targetName);

        // 5. Uruchamiamy zinstrumentowana klase - TERAZ kazde wykonanie linii/galezi
        //    jest ZLICZANE przez sondy JaCoCo.
        Runnable instance = (Runnable) instrumentedClass.getDeclaredConstructor().newInstance();
        instance.run();

        // 6. Zbieramy dane wykonania i zamykamy runtime.
        ExecutionDataStore executionData = new ExecutionDataStore();
        SessionInfoStore sessionInfos = new SessionInfoStore();
        data.collect(executionData, sessionInfos, false);
        runtime.shutdown();

        // 7. Analizujemy ORYGINALNY (nie-zinstrumentowany) bajtkod razem Z zebranymi
        //    danymi wykonania - to daje FAKTYCZNE informacje o pokryciu.
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
        try (InputStream original = DiscountEligibilityChecker.class.getResourceAsStream(
                "/" + targetName.replace('.', '/') + ".class")) {
            analyzer.analyzeClass(original, targetName);
        }

        for (IClassCoverage classCoverage : coverageBuilder.getClasses()) {
            System.out.println("\nPokrycie klasy: " + classCoverage.getName());
            printCounter("instrukcje", classCoverage.getInstructionCounter());
            printCounter("galezie", classCoverage.getBranchCounter());
            printCounter("linie", classCoverage.getLineCounter());
            printCounter("metody", classCoverage.getMethodCounter());

            for (int line = classCoverage.getFirstLine(); line <= classCoverage.getLastLine(); line++) {
                ILine lineCoverage = classCoverage.getLine(line);
                if (lineCoverage.getStatus() == ICounter.EMPTY) {
                    continue;
                }
                System.out.println("  linia " + line + ": " + statusName(lineCoverage.getStatus()));
            }
        }
    }

    private static void printCounter(String unit, ICounter counter) {
        int missed = counter.getMissedCount();
        int total = counter.getTotalCount();
        int covered = total - missed;
        double percent = total == 0 ? 0.0 : (100.0 * covered / total);
        System.out.printf("  pokrycie (%s): %d/%d (%.1f%%)%n", unit, covered, total, percent);
    }

    private static String statusName(int status) {
        return switch (status) {
            case ICounter.NOT_COVERED -> "NIEPOKRYTA (czerwona)";
            case ICounter.PARTLY_COVERED -> "CZESCIOWO pokryta (zolta)";
            case ICounter.FULLY_COVERED -> "W PELNI pokryta (zielona)";
            default -> "brak danych";
        };
    }

    /** ClassLoader ladujacy zinstrumentowana klase BEZPOSREDNIO z tablicy bajtow w pamieci. */
    private static class MemoryClassLoader extends ClassLoader {
        private final Map<String, byte[]> definitions = new HashMap<>();

        void addDefinition(String name, byte[] bytes) {
            definitions.put(name, bytes);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            byte[] bytes = definitions.get(name);
            if (bytes != null) {
                return defineClass(name, bytes, 0, bytes.length);
            }
            return super.loadClass(name, resolve);
        }
    }
}
