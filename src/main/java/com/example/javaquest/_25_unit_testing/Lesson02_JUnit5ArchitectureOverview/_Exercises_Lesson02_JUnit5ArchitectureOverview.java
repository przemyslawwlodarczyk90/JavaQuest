package com.example.javaquest._25_unit_testing.Lesson02_JUnit5ArchitectureOverview;

public class _Exercises_Lesson02_JUnit5ArchitectureOverview {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeJUnit5Modules {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij role Platform/Jupiter/Vintage.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListDiscoveredEnginesOnThisClasspath {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `ServiceLoader.load(TestEngine.class)` I wypisz
         * WSZYSTKIE znalezione silniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhyVintageIsAbsent {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij, DLACZEGO `junit-vintage-engine` NIE
         * jest zaleznoscia tego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteAndRunSimpleJupiterTest {
        /*
         * 🧪 Zadanie 4:
         * Napisz WLASNA klase Z 1 metoda `@Test` I uruchom JA przez
         * Launcher API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainLauncherDelegationModel {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO `Launcher` SAM "nic nie
         * wie" O `@Test`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareTestEngineIdValues {
        /*
         * 🧪 Zadanie 6:
         * Wypisz DOKLADNA wartosc `TestEngine.getId()` DLA silnika
         * Jupiter (oczekiwane: "junit-jupiter").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainServiceLoaderMechanism {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: powiaz z `_14_advancedjava/Lesson26_
         * ServiceLoaderAndSpi` - wyjasnij, jak `ServiceLoader` ZNAJDUJE
         * `JupiterTestEngine` BEZ jawnej rejestracji W KODZIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteTestClassWithTwoTestMethods {
        /*
         * 🧪 Zadanie 8:
         * Napisz klase Z 2 metodami `@Test` I zweryfikuj
         * `getTestsFoundCount() == 2`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPluginArchitectureMatters {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO architektura "wtyczkowa"
         * (Platform + wymienne silniki) jest ELASTYCZNA (Cucumber/
         * Spock jako DODATKOWE silniki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LocateJupiterEngineJarOnClasspath {
        /*
         * 🧪 Zadanie 10:
         * Znajdz (PROGRAMOWO, przez `Class.getProtectionDomain()`)
         * SCIEZKE DO pliku `.jar` zawierajacego `JupiterTestEngine`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomTestEngineSkeleton {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala: opisz (koncepcyjnie) KROKI implementacji
         * WLASNEGO, minimalnego `TestEngine` (metody `getId`/`discover`/
         * `execute`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RegisterMultipleListeners {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj 2 `TestExecutionListener` NARAZ NA JEDNYM
         * `Launcher`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseSelectMethodInsteadOfSelectClass {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `DiscoverySelectors.selectMethod(...)` DO uruchomienia
         * TYLKO JEDNEJ metody testowej (NIE calej klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseSelectUriOrSelectClasspathRoot {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `DiscoverySelectors.selectPackage(...)` DO uruchomienia
         * WSZYSTKICH testow W CALYM PAKIECIE tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainDiscoveryVsExecutionPhases {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij ROZNICE miedzy FAZA "discovery"
         * (znajdz testy) A "execution" (URUCHOM je) W `Launcher`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementFilterByTag {
        /*
         * 🧪 Zadanie 16:
         * Uzyj `LauncherDiscoveryRequestBuilder.filters(...)` DO
         * odfiltrowania testow WEDLUG tagu (zapowiedz Lesson12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareLauncherApiWithMavenSurefire {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, DLACZEGO `mvn test` (Surefire) I
         * TWOJE IDE UZYWAJA TEGO SAMEGO Launcher API "pod maska".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureDiscoveryVsExecutionTime {
        /*
         * 🧪 Zadanie 18:
         * Zmierz OSOBNO CZAS fazy discovery I execution DLA 10 klas
         * testowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainEngineIsolationBenefits {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, DLACZEGO IZOLACJA silnikow (Jupiter
         * NIE wie NIC O Vintage) UPRASZCZA rozwoj JUnit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignHybridProjectWithVintageAndJupiter {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zaprojektuj (koncepcyjnie) STOPNIOWA migracje
         * projektu Z JUnit 4 NA JUnit 5 (OBA silniki JEDNOCZESNIE).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementMinimalCustomTestEngine {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj FAKTYCZNIE dzialajacy, MINIMALNY WLASNY
         * `TestEngine` (np. wykonujacy metody Z adnotacja `@MyTest`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RegisterCustomEngineViaServiceLoader {
        /*
         * 🧪 Zadanie 22:
         * Zarejestruj WLASNY silnik Z Exercise21 PRZEZ
         * `META-INF/services/org.junit.platform.engine.TestEngine`
         * (powiazanie Z `_14_advancedjava/Lesson26`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareTestEngineExecutionModels {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: porownaj MODEL wykonania Jupiter (drzewo
         * kontenerow/testow) Z prostszym, WLASNYM silnikiem (plaska
         * lista).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementParallelTestDiscoveryAcrossPackages {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj ROWNOLEGLE odkrywanie testow Z WIELU pakietow
         * (powiazanie Z `_05_multithreading`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignEngineForDataDrivenTests {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaprojektuj WLASNY silnik DEDYKOWANY testom
         * sterowanym danymi (np. Z plikow CSV) - JAK wygladalby JEGO
         * model odkrywania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BenchmarkLauncherApiWithHundredsOfClasses {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj 200 klas testowych (programowo, `ToolProvider.
         * getSystemJavaCompiler()`, powiazanie Z `_11_buildtools`) I
         * zmierz CZAS discovery+execution.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementTestEngineWithSetupTeardownHooks {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz WLASNY silnik Z Exercise21 O obsluge "setup"/
         * "teardown" (zapowiedz Lesson04).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareJUnitPlatformWithTestNGArchitecture {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj architekture JUnit Platform Z
         * TestNG (alternatywny framework testowy Javy) - kompromisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignReportingPipelineFromLauncherEvents {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj WLASNY `TestExecutionListener` generujacy
         * RAPORT W formacie JSON (ZAMIAST tekstowego podsumowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullCustomTestFrameworkDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNE demo: WLASNY `TestEngine` + WLASNA adnotacja +
         * WLASNY listener raportujacy - URUCHOMIONE OBOK standardowego
         * Jupiter W JEDNYM `Launcher`.
         */
        public static void main(String[] args) { }
    }
}
