package com.example.javaquest._16_clean_code.Lesson20_StaticAnalysisTools;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugCollectionBugReporter;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.DetectorFactoryCollection;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.Project;
import edu.umd.cs.findbugs.config.UserPreferences;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.lang.rule.RuleSet;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson20_StaticAnalysisTools {

    /** Celowo "brudny" przykladowy kod - zrodlo do analizy przez PMD i SpotBugs w tej lekcji. */
    private static final String BAD_CODE_SOURCE = """
            public class BadCodeSample {
                private int unusedField = 42;

                public boolean sameString(String a, String b) {
                    return a == b;
                }

                public void deadStore() {
                    int x = 5;
                    x = 10;
                    System.out.println("done");
                }
            }
            """;

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 20: NARZEDZIA STATYCZNEJ ANALIZY KODU ===");

        /*
         * ============================================================
         * 📦 PO CO AUTOMATYZOWAC TO, CZEGO NAUCZYLES SIE W LESSON14?
         * ============================================================
         * - Lesson14 nauczyl Cie ROZPOZNAWAC code smelle "na oko" - to
         *   dziala, ale jest WOLNE i ZALEZNE od tego, czy akurat SPOJRZYSZ
         *   na dany fragment kodu.
         * - Narzedzia statycznej analizy (static analysis) CZYTAJA caly
         *   kod projektu AUTOMATYCZNIE (bez uruchamiania programu) i
         *   zglaszaja setki mozliwych problemow w SEKUNDY - nieuzywane
         *   pola, martwy kod, potencjalne NullPointerException, zle
         *   porownania Stringow i dziesiatki innych wzorcow.
         * - W tej lekcji embedujemy DWA prawdziwe, przemyslowe narzedzia
         *   (PMD i SpotBugs) bezposrednio w `main()` - ten sam wzorzec
         *   "embeduj i naprawde uruchom" co embedowany Ant w
         *   `_11_buildtools` czy embedowany Tomcat w `_07_servlets`.
         */
        System.out.println("Static analysis = automatyczne wykrywanie problemow BEZ uruchamiania programu, w sekundy.");

        /*
         * ============================================================
         * 🔹 PRZYGOTOWANIE: "BRUDNY" KOD DO ANALIZY
         * ============================================================
         * - Ponizszy kod (BAD_CODE_SOURCE) zawiera CELOWO 3 realne
         *   problemy: nieuzywane pole (`unusedField`), porownanie
         *   Stringow przez `==` zamiast `.equals()` (klasyczny blad
         *   poczatkujacych), oraz "martwy zapis" (przypisanie do `x`,
         *   ktore jest natychmiast nadpisane, wiec pierwsza wartosc
         *   nigdy nie jest uzyta).
         * - Zapisujemy go do TYMCZASOWEGO pliku `.java` na dysku - OBA
         *   narzedzia dzialaja na prawdziwych plikach/skompilowanych
         *   klasach, nie na Stringu w pamieci.
         */
        Path workDir = Files.createTempDirectory("lesson20-static-analysis");
        Path sourceFile = workDir.resolve("BadCodeSample.java");
        Files.writeString(sourceFile, BAD_CODE_SOURCE);
        System.out.println("\nPrzykladowy 'brudny' kod zapisany do: " + sourceFile);

        /*
         * ============================================================
         * 🔍 PMD: ANALIZA NA POZIOMIE KODU ZRODLOWEGO
         * ============================================================
         * - PMD (Programming Mistake Detector) analizuje kod ZRODLOWY
         *   (`.java`), budujac drzewo skladniowe (podobne do tego, co
         *   robi `javac` - ale bez generowania bytecode'u) i przeszukujac
         *   je regulami (rules) pogrupowanymi w KATEGORIE (`bestpractices`,
         *   `errorprone`, `codestyle`, `design`, `performance`).
         * - Uzywamy tu WBUDOWANEGO, oficjalnego zestawu regul
         *   `category/java/bestpractices.xml` (dostarczonego wewnatrz
         *   samej zaleznosci `pmd-java` - BEZ potrzeby polaczenia z
         *   internetem) - PMD API (wersja 7.x) to `PmdAnalysis` +
         *   `PMDConfiguration` + `RuleSetLoader`.
         */
        runPmdAnalysis(sourceFile);

        /*
         * ============================================================
         * 🔹 SPOTBUGS: ANALIZA NA POZIOMIE SKOMPILOWANEGO BYTECODE'U
         * ============================================================
         * - SpotBugs (nastepca historycznego FindBugs) dziala INACZEJ niz
         *   PMD - analizuje SKOMPILOWANE pliki `.class` (bytecode), NIE
         *   kod zrodlowy. Dzieki temu wykrywa INNA kategorie problemow -
         *   takich, ktore ujawniaja sie dopiero na poziomie faktycznych
         *   instrukcji bytecode'u (np. specyficzne wzorce porownan czy
         *   uzycia API), a nie samej struktury skladniowej.
         * - Wymaga to NAJPIERW skompilowania naszego przykladowego kodu -
         *   uzywamy `ToolProvider.getSystemJavaCompiler()`, DOKLADNIE tej
         *   samej techniki "kompiluj w locie", ktora poznales w
         *   `_11_buildtools/Lesson01-02` i `_14_advancedjava/Lesson14`.
         */
        runSpotBugsAnalysis(workDir, sourceFile);

        /*
         * ============================================================
         * 🔍 PMD VS SPOTBUGS - KIEDY KTORE?
         * ============================================================
         * - PMD: szybszy (nie wymaga kompilacji), lepszy do regul
         *   STYLISTYCZNYCH/strukturalnych (nazewnictwo, zlozonosc metod,
         *   nieuzywany kod, dlugosc metod - por. Lesson04-06/Lesson14).
         * - SpotBugs: wolniejszy (wymaga bytecode'u), lepszy do
         *   wykrywania REALNYCH bledow (potencjalny NullPointerException,
         *   zle uzycie API, problemy z rownoscia/hashCode, wycieki
         *   zasobow) - patrzy na to, co program NAPRAWDE zrobi.
         * - W praktyce projekty czesto uzywaja OBU narzedzi razem -
         *   pokrywaja sie czesciowo, ale kazde lapie rzeczy, ktorych
         *   drugie nie widzi.
         */
        System.out.println("\nPMD (kod zrodlowy, szybki, styl/struktura) + SpotBugs (bytecode, wolniejszy, realne bledy) - czesto RAZEM.");

        /*
         * ============================================================
         * 🔹 FALSZYWE ALARMY (FALSE POSITIVES) I OGRANICZENIA
         * ============================================================
         * - ZADNE narzedzie statycznej analizy nie jest doskonale -
         *   moze zglaszac "falszywy alarm" (false positive - problem,
         *   ktorego naprawde nie ma w danym kontekscie) LUB przeoczyc
         *   realny problem (false negative).
         * - Oba narzedzia pozwalaja na TLUMIENIE (suppression) konkretnych
         *   ostrzezen w konkretnym miejscu (adnotacje/komentarze) - warto
         *   uzywac tego OSZCZEDNIE i z KOMENTARZEM wyjasniajacym, DLACZEGO
         *   dane ostrzezenie jest tu falszywym alarmem, a nie "po prostu
         *   uciszac" wszystko.
         * - Statyczna analiza to NARZEDZIE WSPIERAJACE, nie ZASTEPUJACE
         *   przegladu kodu przez czlowieka (patrz Lesson22: Code Review) -
         *   automat wylapie mechaniczne wzorce, ale NIE oceni, czy
         *   rozwiazanie ma SENS BIZNESOWY.
         */
        System.out.println("\nNarzedzia statycznej analizy WSPIERAJA, ale NIE ZASTEPUJA code review (Lesson22).");

        /*
         * ============================================================
         * 📌 INTEGRACJA Z BUILDEM (KROTKO)
         * ============================================================
         * - W realnym projekcie te narzedzia NIE sa uruchamiane recznie z
         *   `main()` (jak w tej lekcji, dla celow dydaktycznych) - zamiast
         *   tego dodaje sie je jako PLUGINY do buildu (`maven-pmd-plugin`,
         *   `spotbugs-maven-plugin` dla Mavena, odpowiedniki dla Gradle -
         *   patrz `_11_buildtools`), ktore uruchamiaja analize
         *   AUTOMATYCZNIE przy kazdym `mvn verify`/w pipeline CI, a build
         *   moze nawet "failowac" przy przekroczeniu progu naruszen.
         */
        System.out.println("\nW realnym projekcie: pluginy buildu (maven-pmd-plugin/spotbugs-maven-plugin) w CI, nie reczne uruchomienie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Static analysis automatyzuje wykrywanie code smelli/bledow z
         *   Lesson14 na skale calego projektu, w sekundy.
         * - PMD: analiza kodu zrodlowego, regulki wg kategorii
         *   (bestpractices/errorprone/codestyle/design).
         * - SpotBugs: analiza bytecode'u, wykrywa REALNE bledy (np.
         *   porownanie Stringow przez ==, nieuzywane pola).
         * - Oba maja ograniczenia (false positives) i NIE zastepuja
         *   code review czlowieka.
         * - W praktyce: pluginy buildu w CI, nie reczne uruchamianie.
         * - W kolejnej lekcji (Lesson21): praca z kodem LEGACY i dlug
         *   techniczny - co robic, gdy narzedzia z tej lekcji zglaszaja
         *   SETKI naruszen w istniejacym, starym kodzie.
         */
        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    private static void runPmdAnalysis(Path sourceFile) {
        System.out.println("\n=== PMD: ANALIZA KODU ZRODLOWEGO (category/java/bestpractices.xml) ===");

        PMDConfiguration configuration = new PMDConfiguration();
        try (PmdAnalysis pmd = PmdAnalysis.create(configuration)) {
            pmd.files().addFile(sourceFile);

            RuleSet bestPractices = pmd.newRuleSetLoader().loadFromResource("category/java/bestpractices.xml");
            pmd.addRuleSet(bestPractices);

            Report report = pmd.performAnalysisAndCollectReport();
            var violations = report.getViolations();

            if (violations.isEmpty()) {
                System.out.println("PMD nie znalazlo naruszen (nieoczekiwane dla tego przykladu, ale mozliwe w innej wersji regul).");
            } else {
                for (RuleViolation violation : violations) {
                    System.out.println("  [" + violation.getRule().getName() + "] linia "
                            + violation.getLocation().getStartLine() + ": " + violation.getDescription());
                }
                System.out.println("Liczba naruszen PMD: " + violations.size());
            }
        }
    }

    private static void runSpotBugsAnalysis(Path workDir, Path sourceFile) throws Exception {
        System.out.println("\n=== SPOTBUGS: ANALIZA SKOMPILOWANEGO BYTECODE'U ===");

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, null, "-d", workDir.toString(), sourceFile.toString());
        if (compileResult != 0) {
            System.out.println("Kompilacja przykladowego kodu nie powiodla sie (kod wyjscia=" + compileResult + ") - pomijam SpotBugs.");
            return;
        }

        Project project = new Project();
        project.addFile(workDir.toString());

        BugCollectionBugReporter bugReporter = new BugCollectionBugReporter(project);
        bugReporter.setPriorityThreshold(Priorities.LOW_PRIORITY);

        try (FindBugs2 engine = new FindBugs2()) {
            engine.setBugReporter(bugReporter);
            engine.setProject(project);
            engine.setDetectorFactoryCollection(DetectorFactoryCollection.instance());
            engine.setUserPreferences(UserPreferences.createDefaultUserPreferences());
            engine.finishSettings();
            engine.execute();
        }

        BugCollection bugs = bugReporter.getBugCollection();
        int count = 0;
        for (BugInstance bug : bugs) {
            System.out.println("  [" + bug.getType() + "] " + bug.getMessage());
            count++;
        }

        if (count == 0) {
            System.out.println("SpotBugs nie znalazl bledow (nieoczekiwane dla tego przykladu, ale mozliwe w innej wersji detektorow).");
        } else {
            System.out.println("Liczba bledow SpotBugs: " + count);
        }
    }
}
