package com.example.javaquest._11_buildtools.Lesson16_MavenTestingAndCoverage;

public class _Exercises_Lesson16_MavenTestingAndCoverage {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateProjectWithJunit5 {
        /*
         * 🧪 Zadanie 1:
         * Utwórz nowy projekt Maven z zależnością org.junit.jupiter:junit-jupiter (scope
         * test) i maven-surefire-plugin. Napisz jedną prostą klasę testową CalculatorTest
         * (2-3 metody @Test). Uruchom "mvn test" w terminalu i zapisz w komentarzu liczbę
         * uruchomionych testów z outputu Surefire.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddSlowTestAndExclude {
        /*
         * 🧪 Zadanie 2:
         * W projekcie z zadania 1 dodaj klasę PaymentSlowTest (nazwa kończąca się na
         * "SlowTest"). Skonfiguruj w pom.xml surefire z <excludes><exclude>** /*SlowTest.java
         * </exclude></excludes>. Uruchom "mvn test" i zapisz w komentarzu, że
         * PaymentSlowTest NIE pojawia się w wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IncludesOnlyPattern {
        /*
         * 🧪 Zadanie 3:
         * W tym samym projekcie skonfiguruj <includes><include>** /*Test.java</include>
         * </includes> tak, aby jawnie ograniczyć surefire tylko do klas *Test (bez
         * domyślnych *Tests/*TestCase). Uruchom "mvn test" i zapisz w komentarzu wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TagFastAndSlow {
        /*
         * 🧪 Zadanie 4:
         * Dodaj do metod testowych z zadania 1 adnotacje @Tag("fast") i @Tag("slow") (z
         * org.junit.jupiter.api.Tag). Bez terminala: wypisz na konsoli (System.out.println)
         * te dwie adnotacje jako fragment kodu, z jednozdaniowym wyjaśnieniem różnicy
         * względem filtrowania po nazwie pliku z zadania 2-3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GroupsConfiguration {
        /*
         * 🧪 Zadanie 5:
         * Skonfiguruj w pom.xml surefire z <groups>fast</groups> (uruchamiaj tylko testy
         * oznaczone @Tag("fast")). Uruchom "mvn test" w projekcie z zadania 4 i zapisz w
         * komentarzu, które metody się wykonały.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExcludedGroupsConfiguration {
        /*
         * 🧪 Zadanie 6:
         * Zamień konfigurację z zadania 5 na <excludedGroups>slow</excludedGroups>.
         * Uruchom "mvn test" i zapisz w komentarzu, czy wynik jest taki sam jak przy
         * <groups>fast</groups> - wyjaśnij dlaczego (w tym małym przykładzie z tylko
         * dwoma tagami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddFailsafePlugin {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do projektu maven-failsafe-plugin skonfigurowany z goalami
         * integration-test i verify (jak w lekcji). Utwórz klasę CalculatorIT (sufiks IT).
         * Uruchom "mvn verify" w terminalu i zapisz w komentarzu, w której fazie (z nazwy
         * w outputcie) uruchomił się CalculatorIT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunTestVsVerify {
        /*
         * 🧪 Zadanie 8:
         * W projekcie z zadania 7 uruchom najpierw "mvn test", a potem osobno "mvn verify".
         * Zapisz w komentarzu różnicę - że "mvn test" NIE odpala CalculatorIT (tylko
         * surefire), a "mvn verify" odpala oba (surefire + failsafe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddJacocoPlugin {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do projektu z zadania 1 jacoco-maven-plugin z dwoma egzekucjami
         * (prepare-agent, report) - jak w lekcji. Uruchom "mvn test" w terminalu i
         * sprawdź, czy plik target/site/jacoco/index.html powstał. Zapisz wynik w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MissingPrepareAgentPitfall {
        /*
         * 🧪 Zadanie 10:
         * W projekcie z zadania 9 USUŃ egzekucję "prepare-agent" (zostaw tylko "report").
         * Uruchom "mvn test" ponownie i zapisz w komentarzu, że raport HTML powstaje, ale
         * pokazuje 0% pokrycia - klasyczna pułapka z lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OpenJacocoHtmlReport {
        /*
         * 🧪 Zadanie 11:
         * Napraw projekt z zadania 10 (przywróć prepare-agent), uruchom "mvn test" i
         * otwórz target/site/jacoco/index.html w przeglądarce. Zapisz w komentarzu
         * procent pokrycia linii dla klasy Calculator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TagOnClassVsMethod {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj projekt z zadania 4-6 tak, aby JEDNA klasa testowa miała @Tag("integration")
         * na poziomie KLASY, a jedna z jej metod dodatkowo @Tag("slow") na poziomie METODY.
         * Uruchom "mvn test -Dgroups=slow" w terminalu i zapisz w komentarzu, która metoda
         * się wykonała (dowód, że <groups> działa też na poziomie metody, nie tylko klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FailsafePostIntegrationTestPhase {
        /*
         * 🧪 Zadanie 13:
         * W projekcie z zadania 7 dodaj do failsafe dodatkowy goal podpięty pod fazę
         * "post-integration-test" (np. wypisujący komunikat przez maven-antrun-plugin albo
         * komentarz w kodzie, jeśli nie chcesz dodawać kolejnego pluginu). Uruchom "mvn
         * verify" i zapisz w komentarzu kolejność faz w outputcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FailsafeDoesNotStopBuildImmediately {
        /*
         * 🧪 Zadanie 14:
         * Zmień CalculatorIT tak, aby jedna asercja celowo zawodziła. Uruchom "mvn verify"
         * i zapisz w komentarzu, że build kończy się niepowodzeniem dopiero na etapie
         * "failsafe:verify", a NIE natychmiast po nieudanym teście integracyjnym (w
         * przeciwieństwie do surefire) - porównaj z zachowaniem z zadania 1, gdzie
         * nieudany test unit przerywa "mvn test" od razu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_JacocoRuleMinimumCoverage {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do jacoco-maven-plugin egzekucję z goalem "check" i regułą minimalnego
         * pokrycia linii (np. 0.80). Uruchom "mvn verify" na projekcie z niewystarczającym
         * pokryciem i zapisz w komentarzu treść błędu, jaki zwraca Maven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SurefireReportsXml {
        /*
         * 🧪 Zadanie 16:
         * Po "mvn test" otwórz wygenerowane pliki w target/surefire-reports/ (pliki .txt i
         * .xml). Bez terminala: wypisz na konsoli, jakie informacje (min. 3) zawiera taki
         * plik XML surefire (nazwa klasy, czas wykonania, liczba testów/błędów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FailsafeReportsDirectory {
        /*
         * 🧪 Zadanie 17:
         * Sprawdź katalog target/failsafe-reports/ po "mvn verify" z projektu z zadania 7.
         * Zapisz w komentarzu, czy struktura raportów różni się od target/surefire-reports/
         * z zadania 16 (nazwy plików, zawartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CombineTagsAndFailsafe {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj projekt łączący WSZYSTKIE mechanizmy z lekcji: testy unit z @Tag,
         * surefire z <groups>, test integracyjny *IT obsługiwany przez failsafe, oraz
         * jacoco mierzące pokrycie. Uruchom "mvn verify" w terminalu i zapisz w komentarzu
         * pełną kolejność wykonanych goali z outputu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SkipTestsFlag {
        /*
         * 🧪 Zadanie 19:
         * Uruchom w tym samym projekcie "mvn package -DskipTests" oraz osobno
         * "mvn package -Dmaven.test.skip=true". Zapisz w komentarzu różnicę między tymi
         * dwiema flagami (druga pomija też KOMPILACJĘ klas testowych, nie tylko ich
         * uruchomienie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SurefireVsFailsafeTablePrint {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: odtwórz (jako println, własnymi słowami, nie kopiuj 1:1 z lekcji)
         * tabelę porównawczą surefire vs failsafe z min. 4 kryteriami (konwencja nazw,
         * domyślna faza, kolejność względem "package", zachowanie przy błędzie).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiModuleTestingSetup {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz projekt wielomodułowy z Lesson14 (core/persistence/app) tak, aby moduł
         * "core" miał testy jednostkowe (surefire) a moduł "app" miał dodatkowo test
         * integracyjny *IT (failsafe) korzystający z klas modułu "core". Uruchom
         * "mvn verify" w katalogu głównym i zapisz w komentarzu wynik dla obu modułów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AggregatedJacocoReport {
        /*
         * 🧪 Zadanie 22:
         * W projekcie wielomodułowym z zadania 21 dodaj do modułu-agregatora (parent pom)
         * egzekucję jacoco-maven-plugin z goalem "report-aggregate", zbierającą pokrycie ze
         * WSZYSTKICH modułów naraz. Uruchom "mvn verify" i zapisz w komentarzu ścieżkę do
         * zagregowanego raportu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ParallelTestExecution {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj w surefire <parallel>methods</parallel> i <threadCount>4</threadCount>.
         * Napisz 5 testów z krótkim Thread.sleep(200) każdy i uruchom "mvn test" - porównaj
         * w komentarzu czas wykonania z wersją bez <parallel> (sekwencyjną).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RerunFailingTestsFlag {
        /*
         * 🧪 Zadanie 24:
         * Napisz test, który losowo (np. co drugie uruchomienie) zawodzi (symulacja testu
         * "flaky"). Skonfiguruj surefire z <rerunFailingTestsCount>2</rerunFailingTestsCount>
         * i uruchom "mvn test" kilka razy w terminalu. Zapisz w komentarzu, czy build
         * kończy się sukcesem mimo sporadycznych niepowodzeń pojedynczych prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_JacocoExcludePatterns {
        /*
         * 🧪 Zadanie 25:
         * Dodaj do konfiguracji jacoco-maven-plugin sekcję <excludes> wykluczającą klasy
         * generowane (np. "** /dto/**", "** /*Generated.class"). Uruchom "mvn test" i zapisz
         * w komentarzu, jak zmieniła się liczba klas objętych analizą w raporcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FailsafeAgainstEmbeddedH2 {
        /*
         * 🧪 Zadanie 26:
         * Napisz test integracyjny UserRepositoryIT łączący się z bazą H2 in-memory
         * (wzorzec z _08_sql/Lesson01 tego kursu) - prawdziwy przykład tego, czemu taki test
         * jest "integracyjny", a nie jednostkowy. Uruchom "mvn verify" i zapisz w
         * komentarzu wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CiPipelineTestStageSketch {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wypisz szkic (jako println, 3-4 kroki) typowego etapu "test" w
         * pipeline CI (np. GitHub Actions) używającego "mvn verify" tego projektu -
         * z uwzględnieniem publikacji raportu JaCoCo jako artefaktu builda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MutationTestingMentionPitest {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wypisz krótkie wyjaśnienie (min. 3 zdania), czym różni się mutation
         * testing (np. plugin PIT/pitest-maven) od zwykłego pokrycia linii mierzonego przez
         * JaCoCo - że wysokie pokrycie NIE gwarantuje dobrych asercji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestNamingConventionAudit {
        /*
         * 🧪 Zadanie 29:
         * W KORZENIU repozytorium tego kursu sprawdź (bez uruchamiania, samą lekturą kodu w
         * src/test/), czy istnieją tam jakiekolwiek klasy testowe. Bez terminala: wypisz w
         * komentarzu wniosek i krótkie uzasadnienie, jakiej konwencji nazw (*Test/*IT)
         * należałoby użyć, gdyby ten kurs miał testy jednostkowe i integracyjne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTestingPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie: zbuduj projekt Maven łączący WSZYSTKIE elementy tej
         * lekcji - testy jednostkowe z @Tag, surefire z <groups>/<excludedGroups>, testy
         * integracyjne *IT obsługiwane przez failsafe (z jednym łączącym się z H2 in-memory),
         * jacoco z prepare-agent/report/check (minimalne pokrycie). Uruchom
         * "mvn clean verify" i napisz w komentarzu krótki raport tekstowy podsumowujący
         * wynik każdego etapu (surefire, failsafe, jacoco).
         */
        public static void main(String[] args) { }
    }
}
