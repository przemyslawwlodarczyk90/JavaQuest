package com.example.javaquest._28_java_evolution.Lesson22_Java24To25LatestFeatures;

public class _Exercises_Lesson22_Java24To25LatestFeatures {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseStreamGatherWithWindowFixedOnListOfNumbers {
        /* 🧪 Zadanie 1: Uzyj `stream().gather(Gatherers.windowFixed(n))` NA liscie liczb (W PODPROCESIE Z --release 24). */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseGatherersWindowSlidingForOverlappingWindows {
        /* 🧪 Zadanie 2: Uzyj `Gatherers.windowSliding(n)` DLA nakladajacych sie okien. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateScopedValueAndReadItInsideRunLambda {
        /* 🧪 Zadanie 3: Stworz `ScopedValue` I odczytaj JA wewnatrz `run()` (--release 25). */
        public static void main(String[] args) { }
    }

    static class Exercise04_ObserveNoSuchElementExceptionWhenReadingScopedValueOutsideScope {
        /* 🧪 Zadanie 4: Zaobserwuj `NoSuchElementException` PRZY odczycie ScopedValue POZA jej zasiegiem. */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteCompactSourceFileHelloWorldWithoutClassDeclaration {
        /* 🧪 Zadanie 5: Napisz "Hello World" JAKO Compact Source File (BEZ deklaracji klasy). */
        public static void main(String[] args) { }
    }

    static class Exercise06_RunCompactSourceFileDirectlyWithJavaCommand {
        /* 🧪 Zadanie 6: Uruchom Compact Source File BEZPOSREDNIO przez `java Plik.java` (BEZ `javac`). */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyStructuredConcurrencyIsStillPreviewInJava25 {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij (Z pamieci tej lekcji), DLACZEGO structured concurrency JEST WCIAZ preview NAWET W Javie 25. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareStreamGatherersWithBuiltInMapFilterReduce {
        /* 🧪 Zadanie 8: Bez terminala - porownaj MOZLIWOSCI Stream Gatherers Z WBUDOWANYMI `map`/`filter`/`reduce`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyScopedValuesAreBetterThanThreadLocalForVirtualThreads {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, DLACZEGO Scoped Values SA LEPSZE OD `ThreadLocal` DLA watkow wirtualnych (Lesson19). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainTargetAudienceForCompactSourceFilesFeature {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLA KOGO PRZEZNACZONA jest funkcja Compact Source Files. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomGathererUsingGathererOfMethod {
        /* 🧪 Zadanie 11: Zaimplementuj WLASNY `Gatherer` uzywajac `Gatherer.of(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ChainMultipleGatherersInSingleStreamPipeline {
        /* 🧪 Zadanie 12: POLACZ WIELE `gather()` W JEDNYM pipeline strumienia. */
        public static void main(String[] args) { }
    }

    static class Exercise13_PassScopedValueThroughNestedMethodCallsWithoutParameterPassing {
        /* 🧪 Zadanie 13: Przekaz ScopedValue PRZEZ WIELE poziomow zagniezdzonych wywolan metod BEZ jawnego przekazywania parametru. */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseScopedValueInsideVirtualThreadPerTaskExecutor {
        /* 🧪 Zadanie 14: Powiaz z `Lesson19` - uzyj ScopedValue WEWNATRZ `newVirtualThreadPerTaskExecutor()`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareInstanceMainMethodWithTraditionalPublicStaticVoidMain {
        /* 🧪 Zadanie 15: Powiaz z `_01_fundamentals/Lesson01` - porownaj `void main()` Z TRADYCYJNYM `public static void main(String[] args)`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseIoPrintlnInCompactSourceFile {
        /* 🧪 Zadanie 16: Uzyj `IO.println(...)` (klasa `java.lang.IO`, JEP 512) W Compact Source File - PAMIETAJ, ze metoda WYMAGA kwalifikacji nazwa klasy, NIE dziala samo `println(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseModuleImportDeclarationToImportEntireModule {
        /* 🧪 Zadanie 17: Uzyj `import module java.base;` DO zaimportowania CALEGO modulu naraz (JEP 511). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementFlexibleConstructorBodyWithValidationBeforeSuperCall {
        /* 🧪 Zadanie 18: Zaimplementuj konstruktor Z WALIDACJA PRZED wywolaniem `super(...)` (JEP 513). */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareGathererBasedSlidingWindowWithManualImplementation {
        /* 🧪 Zadanie 19: Porownaj `Gatherers.windowSliding()` Z RECZNA implementacja OKNA przesuwnego (STARY sposob). */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSubprocessTestMatrixAcrossRelease21To25 {
        /* 🧪 Zadanie 20: Zbuduj MACIERZ testowa URUCHAMIAJACA POROWNYWALNY kod Z `--release` 21 DO 25 I ZESTAWIAJACA WYNIKI. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildDataProcessingPipelineUsingCustomGathererForRunningAverage {
        /* 🧪 Zadanie 21: Zbuduj pipeline przetwarzania danych Z WLASNYM `Gatherer` liczacym SREDNIA KROCZACA. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRequestContextPropagationUsingScopedValuesAcrossServiceLayers {
        /* 🧪 Zadanie 22: Zaimplementuj propagacje KONTEKSTU zadania (np. requestId) PRZEZ WARSTWY serwisu uzywajac ScopedValue (powiazanie Z `_17_architecture`). */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareThreadLocalMemoryLeakRiskWithScopedValueSafety {
        /* 🧪 Zadanie 23: Powiaz z `_05_multithreading/Lesson30` - zademonstruj (koncepcyjnie) RYZYKO wycieku `ThreadLocal` W puli watkow WOBEC bezpieczenstwa ScopedValue. */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildEducationalScriptUsingCompactSourceFilesForBeginnerExercises {
        /* 🧪 Zadanie 24: Zbuduj PROSTY skrypt edukacyjny (Compact Source File) DLA poczatkujacych, demonstrujacy podstawowe konstrukcje. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementComplexMultiStageStreamPipelineUsingChainedGatherers {
        /* 🧪 Zadanie 25: Zaimplementuj ZLOZONY, WIELOETAPOWY pipeline strumieniowy uzywajac LANCUCHA WLASNYCH `Gatherer`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ResearchAndSummarizeCurrentStatusOfStructuredConcurrencyInLatestJdk {
        /* 🧪 Zadanie 26: (WYMAGA dostepu DO internetu/dokumentacji) Zbadaj I podsumuj AKTUALNY status structured concurrency W NAJNOWSZYM JDK (MOZE juz byc finalny W wersji NOWSZEJ NIZ ta lekcja). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMigrationPlanFromThreadLocalToScopedValuesForExistingWebFramework {
        /* 🧪 Zadanie 27: Zaprojektuj PLAN migracji ISTNIEJACEGO frameworka webowego Z `ThreadLocal` NA ScopedValue. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildBenchmarkComparingPerformanceOfGathererBasedVsTraditionalStreamOperations {
        /* 🧪 Zadanie 28: Zbuduj BENCHMARK porownujacy WYDAJNOSC operacji OPARTYCH NA Gatherer Z tradycyjnymi operacjami strumieniowymi. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFullEtlPipelineCombiningGatherersScopedValuesAndVirtualThreads {
        /* 🧪 Zadanie 29: Zaimplementuj PELNY pipeline ETL LACZACY Gatherers + ScopedValue + watki wirtualne (Lesson19) W JEDNYM demo. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullAdoptionRoadmapForJava24And25FeaturesInProductionCodebase {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA mape drogowa ADOPCJI funkcji Z Javy 24-25 W ISTNIEJACYM, PRODUKCYJNYM projekcie. */
        public static void main(String[] args) { }
    }
}
