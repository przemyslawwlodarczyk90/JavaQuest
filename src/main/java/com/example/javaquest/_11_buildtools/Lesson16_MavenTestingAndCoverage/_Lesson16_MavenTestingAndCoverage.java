package com.example.javaquest._11_buildtools.Lesson16_MavenTestingAndCoverage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson16_MavenTestingAndCoverage {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 16: MAVEN - TESTY I POKRYCIE KODU (SUREFIRE/FAILSAFE/JACOCO) ===");
        System.out.println("Lesson13 poznala maven-surefire-plugin 'z domyslna konfiguracja' - ta lekcja");
        System.out.println("idzie GLEBIEJ: filtrowanie testow, testy integracyjne (failsafe), pokrycie kodu (JaCoCo).");

        Path tempDir = Files.createTempDirectory("javaquest-lesson16-maven");

        /*
         * ============================================================
         * 📦 FILTROWANIE TESTÓW - <includes>/<excludes> W SUREFIRE
         * ============================================================
         * Domyślnie maven-surefire-plugin odpala WSZYSTKIE klasy o
         * nazwach *Test, Test*, *Tests, *TestCase (patrz Lesson13). Ale
         * czasem chcemy WYKLUCZYĆ pewne testy z domyślnego "mvn test"
         * (np. testy wolne/niestabilne) albo odpalić TYLKO konkretną
         * grupę. <includes>/<excludes> pozwala na to na poziomie
         * WZORCÓW NAZW PLIKÓW - prostsze niż grupowanie przez adnotacje
         * (patrz niżej), ale mniej elastyczne.
         */
        String includesExcludesSnippet = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <includes>
                            <include>**/*Test.java</include>
                        </includes>
                        <excludes>
                            <!-- Wolne testy wykluczone z domyslnego 'mvn test' -
                                 odpalane osobno, np. na noc w CI -->
                            <exclude>**/*SlowTest.java</exclude>
                        </excludes>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== FILTROWANIE TESTOW: <includes>/<excludes> ===");
        System.out.println(includesExcludesSnippet);
        System.out.println("includes - WZORCE plikow, ktore MAJA zostac odpalone (domyslnie *Test/*Tests/*TestCase).");
        System.out.println("excludes - WZORCE plikow WYKLUCZONE mimo, ze pasowalyby do 'includes' (np. wolne testy).");

        /*
         * ============================================================
         * 🔹 GRUPY/TAGI TESTÓW - JUnit 5 @Tag + SUREFIRE <groups>
         * ============================================================
         * Bardziej elastyczna alternatywa dla filtrowania po NAZWIE
         * pliku: JUnit 5 pozwala oznaczyć KLASĘ albo POJEDYNCZĄ METODĘ
         * testową adnotacją @Tag("nazwa") - niezależnie od tego, jak
         * nazywa się plik. Surefire odczytuje te tagi przez
         * <groups>/<excludedGroups> (odpowiedniki JUnit Platform "tags").
         *
         * Przykład kodu testowego (poglądowy, nie generujemy go tu jako
         * osobny plik - to ilustracja konwencji):
         *
         *   @Tag("integration")
         *   class OrderServiceIT {
         *       @Test
         *       @Tag("slow")
         *       void processesLargeOrder() { ... }
         *   }
         *
         * Ta sama klasa MOŻE mieć testy z różnymi tagami na poziomie
         * METODY - <groups> w surefire filtruje na podstawie
         * WYKONYWANYCH METOD testowych, nie tylko całych klas.
         */
        String tagsSnippet = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <groups>fast,unit</groups>
                        <excludedGroups>slow,flaky</excludedGroups>
                    </configuration>
                </plugin>
                """;
        System.out.println("\n=== GRUPY/TAGI: JUnit 5 @Tag + SUREFIRE <groups> ===");
        System.out.println(tagsSnippet);
        System.out.println("@Tag(\"fast\")/@Tag(\"slow\") w kodzie testowym + <groups>/<excludedGroups> w pom.xml -");
        System.out.println("elastyczniejsze niz filtrowanie po nazwie pliku, bo dziala nawet na poziomie METODY.");

        /*
         * ============================================================
         * 🔍 MAVEN-FAILSAFE-PLUGIN - TESTY INTEGRACYJNE
         * ============================================================
         * Testy JEDNOSTKOWE (unit tests, surefire) testują pojedynczą
         * klasę/metodę w IZOLACJI (bez bazy danych, bez sieci, bez
         * plików). Testy INTEGRACYJNE (integration tests) sprawdzają
         * WSPÓŁPRACĘ wielu komponentów naraz - np. serwis + realna baza
         * H2, serwis + prawdziwe wywołanie HTTP.
         *
         * Maven rozdziela te dwa rodzaje testów na DWA różne pluginy z
         * ważnego powodu: testy integracyjne są zwykle WOLNIEJSZE i
         * mniej stabilne, więc chcemy móc je odpalić NIEZALEŻNIE od
         * "mvn test" (który powinien być szybki, żeby dawać częsty
         * feedback). maven-failsafe-plugin:
         *
         * - rozpoznaje klasy testowe po KONWENCJI NAZW *IT (Integration
         *   Test), *ITCase, IT* - odróżniając je od *Test (surefire).
         * - jest PODWIĄZANY do INNYCH faz cyklu życia: goal
         *   "failsafe:integration-test" -> faza "integration-test",
         *   goal "failsafe:verify" -> faza "verify" (patrz Lesson11 -
         *   te fazy są PO "package"! - bo testy integracyjne często
         *   uruchamiają się NA już spakowanym artefakcie).
         * - KLUCZOWA różnica względem surefire: failsafe UMYŚLNIE nie
         *   przerywa builda natychmiast, gdy test integracyjny zawiedzie
         *   w fazie "integration-test" - dopiero goal "verify" sprawdza
         *   wynik i przerywa build. To pozwala WCIĄŻ wykonać fazę
         *   "post-integration-test" (np. zamknięcie testowej bazy/
         *   serwera) NAWET gdy test zawiódł - surefire przerwałby build
         *   od razu.
         */
        String failsafeSnippet = """
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-failsafe-plugin</artifactId>
                    <executions>
                        <execution>
                            <goals>
                                <goal>integration-test</goal>
                                <goal>verify</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                """;
        Path failsafePomSnippetPath = tempDir.resolve("failsafe-snippet.xml");
        Files.writeString(failsafePomSnippetPath, failsafeSnippet);

        System.out.println("\n=== MAVEN-FAILSAFE-PLUGIN - TESTY INTEGRACYJNE (*IT) ===");
        System.out.println("Sciezka: " + failsafePomSnippetPath);
        System.out.println(failsafeSnippet);
        System.out.println("Konwencja nazw: *IT / *ITCase / IT* (surefire ich NIE dotyka - inny wzorzec niz *Test).");
        System.out.println("Fazy: failsafe:integration-test (faza integration-test) i failsafe:verify (faza verify) -");
        System.out.println("OBIE PO fazie 'package' (Lesson11) - testy integracyjne dzialaja na juz spakowanym artefakcie.");
        System.out.println("WAZNE: failsafe NIE przerywa builda natychmiast po nieudanym tescie - dopiero 'verify' to robi,");
        System.out.println("zeby faza 'post-integration-test' (np. zamkniecie testowej bazy) zdazyla sie wykonac.");

        printSurefireVsFailsafeTable();

        /*
         * ============================================================
         * 🔹 JACOCO-MAVEN-PLUGIN - POKRYCIE KODU TESTAMI
         * ============================================================
         * JaCoCo (Java Code Coverage) mierzy, jaki PROCENT kodu
         * produkcyjnego faktycznie WYKONAŁY testy (linie, rozgałęzienia,
         * metody, klasy). W Mavenie działa przez INSTRUMENTACJĘ bytecode
         * w trakcie odpalania testów - stąd DWA kluczowe goale:
         *
         * - "jacoco:prepare-agent" -> MUSI się wykonać PRZED testami
         *   (zwykle podwiązany do fazy "initialize" albo bez jawnej
         *   fazy - wykonuje się automatycznie na starcie testów).
         *   Dołącza JVM agent (-javaagent), który "podsłuchuje", jakie
         *   linie kodu faktycznie się wykonały podczas "mvn test".
         *   BEZ tego goala JaCoCo NIE ZBIERZE żadnych danych - to
         *   najczęstsza pułapka początkujących ("dlaczego raport jest
         *   pusty?").
         * - "jacoco:report" -> generuje CZYTELNY raport (HTML + XML +
         *   CSV) na podstawie danych zebranych przez prepare-agent,
         *   zwykle podwiązany do fazy "test" (wykonuje się PO testach,
         *   ale w tej samej fazie) albo "verify".
         *
         * Raport HTML domyślnie ląduje w:
         *   target/site/jacoco/index.html
         */
        String jacocoSnippet = """
                <plugin>
                    <groupId>org.jacoco</groupId>
                    <artifactId>jacoco-maven-plugin</artifactId>
                    <version>0.8.12</version>
                    <executions>
                        <execution>
                            <!-- MUSI wykonac sie PRZED testami - dolacza -javaagent,
                                 bez tego raport bedzie PUSTY -->
                            <id>prepare-agent</id>
                            <goals>
                                <goal>prepare-agent</goal>
                            </goals>
                        </execution>
                        <execution>
                            <!-- Generuje raport PO odpaleniu testow -->
                            <id>report</id>
                            <phase>test</phase>
                            <goals>
                                <goal>report</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                """;

        Path pomPath = tempDir.resolve("pom.xml");
        String fullPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>com.example</groupId>
                    <artifactId>testing-coverage-demo</artifactId>
                    <version>1.0.0</version>

                    <build>
                        <plugins>
                %s
                        </plugins>
                    </build>
                </project>
                """.formatted(indent(jacocoSnippet, 12));
        Files.writeString(pomPath, fullPom);

        System.out.println("\n=== JACOCO-MAVEN-PLUGIN - POKRYCIE KODU (zapisany na dysku) ===");
        System.out.println("Sciezka: " + pomPath);
        System.out.println(jacocoSnippet);
        System.out.println("prepare-agent -> MUSI wykonac sie PRZED testami (dolacza -javaagent, zbiera dane).");
        System.out.println("report        -> generuje raport HTML/XML/CSV PO testach.");
        System.out.println("Domyslna lokalizacja raportu HTML: target/site/jacoco/index.html");

        /*
         * ============================================================
         * 🔍 ILUSTRACYJNY PRZYKŁAD OUTPUTU "mvn verify"
         * ============================================================
         * Ponizszy fragment to ILUSTRACYJNY (nie realnie wywolany "mvn")
         * przyklad tego, co widac w konsoli po "mvn verify" w projekcie
         * z testami jednostkowymi, integracyjnymi i JaCoCo - patrz
         * CLAUDE.md, sekcja _11_buildtools, dla wyjasnienia, czemu
         * lekcje Maven nie odpalaja realnego 'mvn'.
         */
        String illustrativeOutput = """
                [INFO] --- surefire:3.2.5:test (default-test) ---
                [INFO] Running com.example.OrderServiceTest
                [INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
                [INFO] --- jacoco:0.8.12:report (report) ---
                [INFO] Analyzed bundle 'testing-coverage-demo' with 4 classes
                [INFO] --- failsafe:3.2.5:integration-test (default) ---
                [INFO] Running com.example.OrderServiceIT
                [INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
                [INFO] --- failsafe:3.2.5:verify (default) ---
                [INFO] BUILD SUCCESS
                """;
        System.out.println("\n=== ILUSTRACYJNY OUTPUT 'mvn verify' ===");
        System.out.println("(To NIE jest realny output - ten kod NIE odpala prawdziwego 'mvn', patrz CLAUDE.md.)");
        System.out.println(illustrativeOutput);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - <includes>/<excludes> w surefire filtruja testy po WZORCU
         *   NAZWY PLIKU (np. wykluczenie *SlowTest.java z 'mvn test').
         * - JUnit 5 @Tag + surefire <groups>/<excludedGroups> - bardziej
         *   elastyczne filtrowanie, dziala nawet na poziomie METODY.
         * - maven-failsafe-plugin obsluguje TESTY INTEGRACYJNE (*IT),
         *   podwiazany do faz integration-test/verify (PO 'package') -
         *   rozdzielony od surefire (unit testy, faza test), bo testy
         *   integracyjne sa wolniejsze/mniej stabilne i musza dzialac
         *   NA juz spakowanym artefakcie. Nie przerywa builda natychmiast
         *   po niepowodzeniu - dopiero goal 'verify' to robi.
         * - jacoco-maven-plugin: goal 'prepare-agent' (PRZED testami,
         *   inaczej raport bedzie pusty!) + goal 'report' (PO testach) -
         *   raport HTML domyslnie w target/site/jacoco/index.html.
         */

        Files.deleteIfExists(failsafePomSnippetPath);
        Files.deleteIfExists(pomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static void printSurefireVsFailsafeTable() {
        System.out.println("\n=== SUREFIRE (unit) vs FAILSAFE (integration) ===");
        String format = "%-22s | %-25s | %-25s%n";
        System.out.printf(format, "", "maven-surefire-plugin", "maven-failsafe-plugin");
        System.out.println("-".repeat(78));
        System.out.printf(format, "Konwencja nazw", "*Test, Test*, *Tests", "*IT, *ITCase, IT*");
        System.out.printf(format, "Faza domyslna", "test", "integration-test + verify");
        System.out.printf(format, "Kiedy wzgledem package", "PRZED package", "PO package");
        System.out.printf(format, "Przerywa build od razu?", "TAK (od razu przy bledzie)", "NIE (dopiero 'verify')");
    }

    private static String indent(String xml, int spaces) {
        String pad = " ".repeat(spaces);
        return xml.trim().lines()
                .map(line -> pad + line)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
