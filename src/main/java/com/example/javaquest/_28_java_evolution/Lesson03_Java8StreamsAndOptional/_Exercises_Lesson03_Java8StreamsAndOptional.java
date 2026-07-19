package com.example.javaquest._28_java_evolution.Lesson03_Java8StreamsAndOptional;

public class _Exercises_Lesson03_Java8StreamsAndOptional {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RewriteLoopAsStream {
        /* 🧪 Zadanie 1: PRZEPISZ prosta petle NA Stream. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseFilterAndCollect {
        /* 🧪 Zadanie 2: Uzyj `.filter()` + `.collect(Collectors.toList())`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseMapToTransformElements {
        /* 🧪 Zadanie 3: Uzyj `.map()` DO transformacji elementow. */
        public static void main(String[] args) { }
    }

    static class Exercise04_WrapNullableReturnInOptional {
        /* 🧪 Zadanie 4: Owin metode MOGACA zwrocic `null` W `Optional`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseOptionalOrElseForDefaultValue {
        /* 🧪 Zadanie 5: Uzyj `.orElse(...)` DLA wartosci domyslnej. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseOptionalIsPresentAndIfPresent {
        /* 🧪 Zadanie 6: Uzyj `.isPresent()`/`.ifPresent()`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareNullCheckWithOptionalCheck {
        /* 🧪 Zadanie 7: Napisz TEN SAM kod DWOMA sposobami (null-check vs Optional). */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseStreamCountAndSum {
        /* 🧪 Zadanie 8: Uzyj `.count()`/`.sum()` NA Streamie. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyOptionalPreventsNullPointerException {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, JAK `Optional` ZAPOBIEGA `NullPointerException`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListStreamOperationsLearnedInChapter03 {
        /* 🧪 Zadanie 10: Bez terminala - wymien 5 operacji Stream Z `_03_collections/Lesson16-19`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseCollectorsGroupingBy {
        /* 🧪 Zadanie 11: Powiaz z `_03_collections/Lesson18` - uzyj `Collectors.groupingBy(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseOptionalMapAndFlatMapChaining {
        /* 🧪 Zadanie 12: Powiaz z `_03_collections/Lesson21` - uzyj `Optional.map().flatMap()`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseParallelStreamAndMeasureSpeedup {
        /* 🧪 Zadanie 13: Powiaz z `_05_multithreading` - uzyj `.parallelStream()` I zmierz PRZYSPIESZENIE. */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseReduceForCustomAggregation {
        /* 🧪 Zadanie 14: Uzyj `.reduce(...)` DLA WLASNEJ agregacji. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseOptionalOrElseThrowWithCustomException {
        /* 🧪 Zadanie 15: Uzyj `.orElseThrow(() -> new WlasnyWyjatek())`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildStreamFromMultipleSources {
        /* 🧪 Zadanie 16: Zbuduj Stream Z `Stream.concat(...)` 2 zrodel. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseCollectorsToMapForIndexing {
        /* 🧪 Zadanie 17: Uzyj `Collectors.toMap(...)` DO INDEKSOWANIA listy. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainLazyEvaluationOfStreams {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij LENIWA ewaluacje Streamow (operacje POSREDNIE vs TERMINALNE). */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorNestedNullChecksToOptionalChain {
        /* 🧪 Zadanie 19: PRZEPISZ ZAGNIEZDZONE sprawdzenia `null` NA LANCUCH `Optional`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareStreamVsForLoopPerformanceForLargeCollection {
        /* 🧪 Zadanie 20: Porownaj WYDAJNOSC Stream WZGLEDEM petli DLA DUZEJ kolekcji. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomCollector {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `Collector` (`Collector.of(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildInfiniteStreamWithLimit {
        /* 🧪 Zadanie 22: Zbuduj NIESKONCZONY Stream (`Stream.iterate(...)`) Z `.limit(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementOptionalChainForDeeplyNestedObjectGraph {
        /* 🧪 Zadanie 23: Zaimplementuj LANCUCH `Optional` DLA GLEBOKO zagniezdzonego grafu obiektow. */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareStreamWithLoopsForReadabilityInComplexPipeline {
        /* 🧪 Zadanie 24: Zbuduj ZLOZONY pipeline (5+ operacji) I porownaj CZYTELNOSC Z RECZNA petla. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementStreamBasedValidationPipeline {
        /* 🧪 Zadanie 25: Zaimplementuj PIPELINE walidacji OPARTY NA Streamach (zbierajacy WSZYSTKIE bledy). */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildStatisticalSummaryUsingSummaryStatistics {
        /* 🧪 Zadanie 26: Uzyj `IntSummaryStatistics`/`DoubleSummaryStatistics`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementLazyOptionalComputationWithSupplier {
        /* 🧪 Zadanie 27: Uzyj `Optional.orElseGet(Supplier)` DLA LENIWEGO obliczenia wartosci domyslnej. */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareOptionalWithNullObjectPattern {
        /* 🧪 Zadanie 28: Bez terminala - powiaz z `_02_oop/Lesson15` - porownaj `Optional` Z wzorcem Null Object. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementStreamBasedETLPipeline {
        /* 🧪 Zadanie 29: Powiaz z `_04_io` - zaimplementuj PROSTY pipeline ETL (Extract-Transform-Load) NA Streamach. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullFunctionalRefactoringGuideForLegacyImperativeCodebase {
        /* 🧪 Zadanie 30: Zaprojektuj PRZEWODNIK refaktoryzacji IMPERATYWNEGO kodu NA Streamy+Optional DLA zespolu. */
        public static void main(String[] args) { }
    }
}
