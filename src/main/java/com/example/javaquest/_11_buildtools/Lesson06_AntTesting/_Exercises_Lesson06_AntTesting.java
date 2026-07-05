package com.example.javaquest._11_buildtools.Lesson06_AntTesting;

public class _Exercises_Lesson06_AntTesting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LocateJunitJarOnClasspath {
        /*
         * 🧪 Zadanie 1:
         * Uzywajac Class.forName("junit.framework.TestCase").getProtectionDomain().getCodeSource().getLocation(),
         * znajdz i wypisz na konsoli PRAWDZIWA sciezke do pliku junit.jar dostepnego w tym
         * projekcie (dostarczonego transytywnie przez ant-junit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TestTaskMechanicsList {
        /*
         * 🧪 Zadanie 2:
         * Bez uzywania Anta: wypisz na konsoli 4 elementy konfiguracji tasku <junit>
         * wspomniane w lekcji (printsummary, haltonfailure, fork, formatter) razem z
         * jednozdaniowym opisem dzialania kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SinglePassingTestRun {
        /*
         * 🧪 Zadanie 3:
         * Wygeneruj src/Adder.java (metoda add) i test/AdderTest.java (klasa extends
         * junit.framework.TestCase, jedna metoda "testAdd()" z assertEquals - patrz
         * wyjasnienie stylu testow w teorii lekcji) w osobnych katalogach. Skompiluj oba
         * (produkcyjny bez JUnita, test z classpath = klasy produkcyjne + junit.jar) i
         * uruchom testem <junit> z formatter type="plain" usefile="false". Zweryfikuj
         * komunikat "Tests run: 1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SeparateTestAndMainOutputDirs {
        /*
         * 🧪 Zadanie 4:
         * Skompiluj klase produkcyjna do build/classes i klase testowa do
         * build/test-classes (DWA osobne <javac>). Zweryfikuj (Files.exists), ze klasa
         * testowa NIGDY nie ladowala do build/classes, tylko do wlasnego katalogu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JunitDependencyExplanation {
        /*
         * 🧪 Zadanie 5:
         * Bez uzywania Anta: wypisz wyjasnienie (2-3 linie), skad w tym projekcie bierze sie
         * junit.jar (transytywna zaleznosc ant-junit z pom.xml) i jak trzeba by to
         * skonfigurowac w SAMODZIELNYM projekcie Ant bez Mavena (recznie do lib/).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintSummaryOutputCapture {
        /*
         * 🧪 Zadanie 6:
         * Uruchom prosty test JUnit przez <junit printsummary="yes">, przechwycajac
         * output Anta do WLASNEGO PrintStream (ByteArrayOutputStream), a nastepnie wyszukaj
         * w przechwyconym tekscie linie zawierajaca "Tests run" i wypisz TYLKO ta linie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoTestClassesBatchtest {
        /*
         * 🧪 Zadanie 7:
         * Wygeneruj DWIE klasy testowe (np. AdderTest i SubtractorTest), obie w test/.
         * Skonfiguruj <batchtest><fileset dir="build/test-classes" includes="**\/*Test.class"/></batchtest>
         * i zweryfikuj, ze OBIE klasy zostaly znalezione i wykonane automatycznie (bez
         * recznego wymieniania ich nazw).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_XmlFormatterReportFile {
        /*
         * 🧪 Zadanie 8:
         * Uruchom test z <formatter type="xml"/> i <batchtest todir="build/test-reports">.
         * Po wykonaniu, zweryfikuj (Files.exists + sprawdzenie, ze rozmiar > 0), ze
         * wygenerowal sie plik raportu XML (nazwa zaczynajaca sie od "TEST-").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ForkTrueVsFalseExplanation {
        /*
         * 🧪 Zadanie 9:
         * Bez uzywania Anta: wypisz wyjasnienie roznicy miedzy fork="true" (kazdy test w
         * OSOBNYM procesie JVM - izolacja, ale wolniej) i fork="false" (testy w TYM SAMYM
         * procesie co Ant - szybciej, ale mniej izolowane, np. System.exit() w tescie
         * moglby zabic caly build).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TestClasspathReuseFromLesson5 {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj <path id="test.classpath"> zlozony z <pathelement location> (klasy
         * produkcyjne) i <pathelement location> (junit.jar znaleziony jak w Zadaniu 1).
         * Uzyj go w <javac classpathref> DLA TESTU i w <junit><classpath><path refid>.
         * Zweryfikuj poprawna kompilacje i wykonanie testu z jednej, wspoldzielonej definicji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullPipelineCompileTestPackage {
        /*
         * 🧪 Zadanie 11:
         * Odtworz caly wzorzec z teorii lekcji (compile -> compile-test -> test -> package)
         * dla WLASNEGO, innego niz w lekcji przykladu klasy (np. StringReverser). Wykonaj
         * "package" i zweryfikuj, ze JAR powstal WYLACZNIE po przejsciu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FailingTestBlocksPackage {
        /*
         * 🧪 Zadanie 12:
         * Odtworz scenariusz z teorii: dopisz do dzialajacego zestawu testow JEDEN
         * celowo padajacy test (assertEquals z zla oczekiwana wartoscia). Wykonaj "package"
         * (haltonfailure="true") i zlap BuildException, zweryfikuj, ze plik JAR-a NIE
         * powstal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_HaltonfailureFalseContinuesBuild {
        /*
         * 🧪 Zadanie 13:
         * Powtorz scenariusz z Zadania 12, ale z haltonfailure="false" (i failureproperty="tests.failed").
         * Zweryfikuj, ze build DOKANCZA sie (nie leci BuildException), ale property
         * "tests.failed" zostaje ustawiona - wypisz jej wartosc po buildzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExceptionThrowingTestVsAssertionFailure {
        /*
         * 🧪 Zadanie 14:
         * Napisz DWA rozne "zle" testy: jeden z niepowodzeniem asercji (assertEquals nie
         * zgadza sie), drugi wywolujacy wyjatek NIEZWIAZANY z asercja (np. dzielenie przez
         * zero -> ArithmeticException wewnatrz metody testowanej). Uruchom oba przez
         * <junit printsummary="yes"> i wypisz, jak <junit> raportuje kazdy z tych dwoch
         * przypadkow (failures vs errors).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestClasspathMissingJunitJar {
        /*
         * 🧪 Zadanie 15:
         * Sprobuj skompilowac klase testowa rozszerzajaca junit.framework.TestCase z
         * classpath, ktory NIE zawiera junit.jar (pusty <path>). Zlap BuildException przy
         * kompilacji i wypisz jego komunikat ("cannot find symbol: class TestCase" albo
         * podobny) jako dowod, ze JUnit rowniez trzeba dodac do classpath jak kazda inna
         * biblioteke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleFormattersSimultaneously {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj <junit> z DWOMA formatterami naraz: "plain" (usefile="false", na
         * konsole) ORAZ "xml" (do pliku). Uruchom testy i zweryfikuj, ze OBA sposoby
         * raportowania zadzialaly (output na konsoli + plik XML na dysku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestDependsOnCompileNotViceVersa {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode verifyTargetOrder(Project project), ktora przez
         * project.getReference/getTargets sprawdza atrybuty depends targetow "compile-test" i
         * "test" i wypisuje lancuch zaleznosci jako tekst (np. "test <- compile-test <-
         * compile <- init"), potwierdzajac poprawna kolejnosc z teorii lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestResourcesSeparateFromMainResources {
        /*
         * 🧪 Zadanie 18:
         * Utworz resources/ (dla src) i test-resources/ (dla test) z ROZNYMI plikami
         * config.properties (rozne wartosci). Skopiuj kazdy do WLASCIWEGO katalogu
         * wynikowego (build/classes vs build/test-classes) i zweryfikuj, ze test korzysta
         * z WLASNEJ (testowej) konfiguracji, nie produkcyjnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestCountAssertionFromAntLog {
        /*
         * 🧪 Zadanie 19:
         * Uruchom zestaw 5 testow (mieszanka przechodzacych/niepowodzenia, z
         * haltonfailure="false"). Przechwyc log Anta do String (jak w Zadaniu 6) i
         * napisz prosty parser wyciagajacy z linii "Tests run: X, Failures: Y, Errors: Z"
         * liczby X, Y, Z, a potem wypisz je jako 3 osobne zmienne int.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RerunOnlyFailedTestsSimulation {
        /*
         * 🧪 Zadanie 20:
         * Uruchom testy z <batchtest> na 3 klasach testowych, z ktorych 1 zawiera padajacy
         * test. Na podstawie (przechwyconego) logu zidentyfikuj NAZWE klasy z bledem i
         * wykonaj DRUGI przebieg <junit>, tym razem z <test name="..."> (pojedynczy test,
         * nie batchtest) wskazujacym TYLKO na te jedna, problematyczna klase.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullTddCycleSimulation {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj cykl TDD: (1) napisz test dla metody, ktora jeszcze NIE istnieje w
         * klasie produkcyjnej (kompilacja produkcyjna "compile" przejdzie, ale
         * "compile-test" sie NIE powiedzie - brak metody), zlap blad kompilacji; (2)
         * dopisz brakujaca metode do klasy produkcyjnej; (3) zrekompiluj wszystko od nowa i
         * zweryfikuj, ze teraz "test" przechodzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestSuiteAggregationReport {
        /*
         * 🧪 Zadanie 22:
         * Uruchom testy dla 3 niezaleznych klas produkcyjnych (kazda z wlasna klasa
         * testowa) w JEDNYM przebiegu <junit> z batchtest. Po zakonczeniu, sparsuj
         * wygenerowane pliki XML (formatter type="xml") z build/test-reports/ (proste
         * przeszukanie tekstu pliku) i wypisz zbiorczy raport "3/3 klasy testowe: WSZYSTKIE
         * testy przeszly" albo liste tych, ktore nie przeszly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ParametrizedTestDataDriven {
        /*
         * 🧪 Zadanie 23:
         * Napisz klase testowa (extends junit.framework.TestCase - patrz teoria lekcji) z
         * jedna metoda "testXxx()", ktora w PETLI (bez zadnego dedykowanego mechanizmu
         * parametryzacji - to wykracza poza podstawowy <junit> task) iteruje po tablicy
         * przypadkow testowych (np. int[][] {{2,3,5},{10,20,30}}) i dla kazdego wywoluje
         * assertEquals - zweryfikuj, ze JEDEN test faktycznie sprawdza WIELE przypadkow
         * danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CustomBuildListenerForTestResults {
        /*
         * 🧪 Zadanie 24:
         * Napisz WLASNY org.apache.tools.ant.BuildListener, ktory w metodzie
         * messageLogged(BuildEvent) wykrywa linie zawierajace "Tests run:" (pochodzace z
         * <junit>) i zlicza je w polu int. Podepnij go pod Project razem z DefaultLogger,
         * wykonaj testy dla 2 klas testowych i wypisz koncowa liczbe wykrytych podsumowan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConditionalPackagingOnTestOutcome {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj build.xml z targetem "test" (haltonfailure="false",
         * failureproperty="tests.failed") i targetem "package" uzywajacym
         * <fail if="tests.failed" message="Testy nie przeszly - pakowanie przerwane"/> jako
         * PIERWSZY task w tym targecie. Zweryfikuj dla 2 scenariuszy (testy OK / testy
         * failed), ze <fail> poprawnie blokuje pakowanie TYLKO w drugim przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestTimingMeasurement {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj klase testowa z testem, ktory (kontrolowanie, bez realnego sleep
         * dluzszego niz 1s) wykonuje troche pracy (np. petla obliczajaca duza liczbe
         * iteracji). Uruchom <junit> z formatter "plain" i sparsuj z outputu Anta czas
         * wykonania testu (linia z sekundami w nawiasie) - wypisz go jako osobna wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiModuleTestDependencyChain {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj 2 "moduly" (core/ i app/), gdzie testy modulu app/ TESTUJA rowniez kod z
         * core/ (import klasy z core na classpath testu). Wykonaj pelny build obu modulow w
         * poprawnej kolejnosci (core skompilowany i przetestowany PRZED app) i zweryfikuj
         * koncowy sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_JunitVsManualAssertionComparison {
        /*
         * 🧪 Zadanie 28:
         * Napisz TEN SAM przypadek testowy dwoma sposobami: (1) jako klasyczny test JUnit
         * (extends TestCase) uruchomiony przez <junit>, (2) jako reczna metoda w main() z
         * "if (!oczekiwane.equals(wynik))
         * throw new AssertionError(...)". Porownaj i wypisz, o ile mniej kodu/konfiguracji
         * wymaga podejscie z <junit> przy WIELU testach (batchtest) w porownaniu do
         * recznego wywolywania kazdej metody testowej osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FlakyTestRetrySimulation {
        /*
         * 🧪 Zadanie 29:
         * Napisz test korzystajacy ze statycznego licznika (pole static int w klasie
         * testowej), ktory "sztucznie" nie przechodzi przy PIERWSZYM wywolaniu, a przechodzi
         * przy DRUGIM (symulacja "flaky test"). Uruchom <junit> raz (test nie przejdzie -
         * haltonfailure="false"), a potem URUCHOM PONOWNIE (nowy Project, licznik statyczny
         * zresetowany w nowym procesie JVM dzieki fork="true") - zweryfikuj, ze wynik moze
         * sie roznic miedzy przebiegami i skomentuj, czemu "flaky testy" sa problematyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_AntTestingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, realistyczny mini-projekt: 2 klasy produkcyjne + 2 klasy
         * testowe (batchtest), pelny lancuch clean/init/compile/compile-test/test/package,
         * formatter "plain" + "xml", <fail if="tests.failed"> chroniacy target "package".
         * Wykonaj build DWA RAZY: (1) z poprawnymi testami - zweryfikuj sukces i istnienie
         * JAR-a, (2) z jednym uszkodzonym testem (dopisanym miedzy przebiegami) - zweryfikuj
         * zablokowane pakowanie. Wypisz koncowy raport podsumowujacy oba przebiegi.
         */
        public static void main(String[] args) { }
    }
}
