package com.example.javaquest._11_buildtools.Lesson22_GradleTestingAndCoverage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson22_GradleTestingAndCoverage {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 22: GRADLE - TESTY I POKRYCIE KODU (JACOCO) ===");

        /*
         * ============================================================
         * 📦 KONFIGURACJA BLOKU test { }
         * ============================================================
         * Task "test" (z pluginu "java", poznany w Lekcji 16) odpala
         * testy jednostkowe - ale domyslna konfiguracja rzadko wystarcza
         * w realnym projekcie. Blok "test { }" w build.gradle pozwala
         * go dostosowac:
         *
         * - useJUnitPlatform()  -> WŁĄCZA JUnit 5 (JUnit Platform) jako
         *   silnik testowy. BEZ tej linii Gradle domyślnie szuka testów
         *   JUnit 4 - to jedna z najczęstszych "pułapek" (testy JUnit 5
         *   po cichu NIE są odpalane, bo nikt nie dodał tej linii).
         * - include/exclude    -> filtrowanie, KTÓRE klasy testowe mają
         *   (include) albo NIE MAJĄ (exclude) być odpalone, po wzorcu
         *   nazwy pliku .class (np. tylko "*Test.class", bez "*IT.class"
         *   - testy integracyjne uruchamiane osobno).
         * - maxParallelForks    -> liczba RÓWNOLEGŁYCH procesów JVM
         *   odpalających testy - domyślnie 1 (sekwencyjnie); zwiększenie
         *   (np. do liczby rdzeni procesora) ZNACZĄCO skraca czas dla
         *   dużych zestawów testów, kosztem większego zużycia RAM/CPU.
         */

        String testBlockSnippet = """
                test {
                    useJUnitPlatform()

                    filter {
                        includeTestsMatching "*Test"
                        excludeTestsMatching "*SlowTest"
                    }

                    maxParallelForks = Runtime.runtime.availableProcessors().intdiv(2) ?: 1

                    testLogging {
                        events "passed", "skipped", "failed"
                        exceptionFormat "full"
                    }
                }
                """;

        System.out.println("\n=== KONFIGURACJA test { } (build.gradle) ===");
        System.out.println(testBlockSnippet);
        System.out.println("useJUnitPlatform()  - WLACZA JUnit 5 (bez tego: Gradle szuka JUnit 4!).");
        System.out.println("filter{}            - kontrola, KTORE testy odpalic (np. bez *SlowTest).");
        System.out.println("maxParallelForks    - liczba rownoleglych procesow JVM z testami.");
        System.out.println("testLogging         - co i jak wypisywac na konsole podczas testow.");

        /*
         * ============================================================
         * 🔹 RAPORTY TESTÓW - GDZIE ICH SZUKAĆ
         * ============================================================
         * Po odpaleniu "./gradlew test" Gradle generuje DWA rodzaje
         * raportow w katalogu build/ (analogicznie do target/surefire-reports
         * w Mavenie, ale w innej lokalizacji i formacie prezentacji):
         *
         * - build/test-results/test/*.xml   -> RAPORTY XML (format
         *   zgodny z JUnit/Surefire XML) - to WŁAŚNIE te pliki czyta
         *   CI (Jenkins/GitHub Actions/GitLab CI), żeby wyświetlić
         *   "X testów przeszło, Y nie przeszło" w interfejsie pipeline'u.
         * - build/reports/tests/test/index.html -> RAPORT HTML,
         *   czytelny dla CZŁOWIEKA w przeglądarce - klikalne drzewo
         *   klas testowych, kolory (zielony/czerwony), pełne stack
         *   trace'y niepowodzeń.
         */

        System.out.println("\n=== LOKALIZACJE RAPORTOW TESTOW (po ./gradlew test) ===");
        System.out.println("build/test-results/test/*.xml         - XML, czytany przez CI (jak surefire-reports w Mavenie).");
        System.out.println("build/reports/tests/test/index.html   - HTML, czytelny dla czlowieka w przegladarce.");

        /*
         * ============================================================
         * 🔍 PLUGIN JACOCO - POKRYCIE KODU TESTAMI
         * ============================================================
         * JaCoCo (Java Code Coverage) mierzy, JAKI PROCENT kodu
         * produkcyjnego zostal FAKTYCZNIE wykonany przez testy (linie,
         * rozgałęzienia if/else, metody...). W Gradle wystarczy dodać
         * plugin "jacoco" - JaCoCo instrumentuje bytecode w trakcie
         * odpalania testow (bez zmiany zrodel), a potem generuje raport.
         *
         * Kluczowy task: "jacocoTestReport" - generuje raport HTML/XML/CSV
         * NA PODSTAWIE danych zebranych przez task "test" (dependsOn test -
         * musisz odpalic testy PRZED generowaniem raportu pokrycia, inaczej
         * raport bedzie pusty/nieaktualny).
         *
         * Mozna rowniez ustawic "verification" - regule WYMUSZAJACA
         * minimalny prog pokrycia (np. 80%) - jesli kod spada pod ten
         * prog, task "jacocoTestCoverageVerification" ZAWODZI, co (jesli
         * podwiazane do "check"/"build") ZATRZYMUJE cały build - typowy
         * "quality gate" znany z CI.
         */

        String jacocoSnippet = """
                plugins {
                    id 'java'
                    id 'jacoco'
                }

                jacoco {
                    toolVersion = "0.8.12"
                }

                jacocoTestReport {
                    dependsOn test
                    reports {
                        xml.required = true
                        html.required = true
                        html.outputLocation = layout.buildDirectory.dir('reports/jacoco')
                    }
                }

                jacocoTestCoverageVerification {
                    dependsOn jacocoTestReport
                    violationRules {
                        rule {
                            limit {
                                minimum = 0.80  // 80% minimalnego pokrycia linii
                            }
                        }
                    }
                }

                check {
                    dependsOn jacocoTestCoverageVerification
                }
                """;

        Path tempDir = Files.createTempDirectory("javaquest-lesson19-jacoco");
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, jacocoSnippet);

        System.out.println("\n=== PLUGIN JACOCO (build.gradle, zapisany na dysku) ===");
        System.out.println("Sciezka: " + buildGradlePath);
        System.out.println(jacocoSnippet);
        System.out.println("jacocoTestReport               - generuje raport HTML/XML NA PODSTAWIE danych z testu.");
        System.out.println("jacocoTestCoverageVerification - 'quality gate': zawodzi, jesli pokrycie < progu (tu 80%).");
        System.out.println("check { dependsOn ... }         - podwiazuje weryfikacje do standardowego taska 'check'/'build'.");

        /*
         * ============================================================
         * 🔍 ILUSTRACYJNY PRZYKŁAD OUTPUTU jacocoTestReport
         * ============================================================
         * Ponizszy fragment to ILUSTRACYJNY (NIE naprawde wygenerowany
         * przez ./gradlew - ten wrapper nie istnieje w tym repo) przyklad
         * tego, co widac w konsoli/w build/reports/jacoco/index.html po
         * odpaleniu "./gradlew test jacocoTestReport".
         */

        String illustrativeJacocoOutput = """
                > Task :test
                12 tests completed, 12 succeeded

                > Task :jacocoTestReport
                (raport zapisany w build/reports/jacoco/test/html/index.html)

                Element        | Instructions | Branches | Lines  | Methods
                ---------------|--------------|----------|--------|--------
                com.example.model.User      | 92%  | 80%  | 95%  | 100%
                com.example.service.Orders  | 76%  | 60%  | 78%  | 90%
                CALKOWITE POKRYCIE PROJEKTU:  84%  | 70%  | 86%  | 95%
                """;

        System.out.println("\n=== ILUSTRACYJNY OUTPUT './gradlew test jacocoTestReport' ===");
        System.out.println("(To NIE jest realny output - ten wrapper nie istnieje w tym repo. Format");
        System.out.println(" nasladuje prawdziwy JaCoCo/Gradle, wylacznie w celach edukacyjnych.)");
        System.out.println(illustrativeJacocoOutput);

        Files.deleteIfExists(buildGradlePath);
        Files.deleteIfExists(tempDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - test { } - useJUnitPlatform() (WYMAGANE dla JUnit 5!), filter
         *   include/exclude, maxParallelForks (rownolegle testy), testLogging.
         * - Raporty: build/test-results/test/*.xml (czytany przez CI),
         *   build/reports/tests/test/index.html (HTML dla czlowieka).
         * - Plugin 'jacoco' - mierzy pokrycie kodu testami; task
         *   jacocoTestReport generuje raport (dependsOn test), a
         *   jacocoTestCoverageVerification moze dzialac jako quality gate
         *   z minimalnym progiem pokrycia, podwiazany do 'check'/'build'.
         */

        System.out.println("\n=== KONIEC LEKCJI 22 ===");
    }
}
