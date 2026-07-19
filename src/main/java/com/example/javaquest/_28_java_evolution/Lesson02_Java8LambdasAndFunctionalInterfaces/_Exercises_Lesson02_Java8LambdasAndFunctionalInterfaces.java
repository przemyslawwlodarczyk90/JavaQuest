package com.example.javaquest._28_java_evolution.Lesson02_Java8LambdasAndFunctionalInterfaces;

public class _Exercises_Lesson02_Java8LambdasAndFunctionalInterfaces {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RewriteAnonymousClassAsLambda {
        /* 🧪 Zadanie 1: PRZEPISZ anonimowa klase `Runnable` NA lambde. */
        public static void main(String[] args) { }
    }

    static class Exercise02_WritePredicateLambda {
        /* 🧪 Zadanie 2: Napisz `Predicate<Integer>` sprawdzajacy PARZYSTOSC. */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteFunctionLambda {
        /* 🧪 Zadanie 3: Napisz `Function<String, Integer>` zwracajacy DLUGOSC Stringa. */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteSupplierLambda {
        /* 🧪 Zadanie 4: Napisz `Supplier<String>` GENERUJACY losowy tekst. */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteConsumerLambda {
        /* 🧪 Zadanie 5: Napisz `Consumer<String>` WYPISUJACY tekst NA konsole. */
        public static void main(String[] args) { }
    }

    static class Exercise06_DefineOwnFunctionalInterfaceWithAnnotation {
        /* 🧪 Zadanie 6: Zdefiniuj WLASNY `@FunctionalInterface`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseMethodReferenceInsteadOfLambda {
        /* 🧪 Zadanie 7: Zastap lambde REFERENCJA DO metody (`::`). */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareLineCountAnonymousVsLambda {
        /* 🧪 Zadanie 8: Porownaj LICZBE linii anonimowej klasy WZGLEDEM lambdy DLA TEJ SAMEJ logiki. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhatMakesInterfaceFunctional {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, CO CZYNI interfejs "funkcyjnym" (DOKLADNIE 1 metoda abstrakcyjna). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFourCoreFunctionalInterfacesFromJavaUtilFunction {
        /* 🧪 Zadanie 10: Wymien 4 GLOWNE interfejsy Z `java.util.function`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ComposeTwoFunctionsWithAndThen {
        /* 🧪 Zadanie 11: Zlacz 2 `Function` PRZEZ `.andThen(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_CombinePredicatesWithAndOr {
        /* 🧪 Zadanie 12: Polacz 2 `Predicate` PRZEZ `.and(...)`/`.or(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseBiFunctionForTwoArgumentLogic {
        /* 🧪 Zadanie 13: Uzyj `BiFunction<A,B,R>` DLA logiki Z 2 argumentami. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainEffectivelyFinalRequirementForLambdaCapture {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij wymog "effectively final" DLA zmiennych PRZECHWYCONYCH przez lambde. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseUnaryOperatorForSameTypeTransformation {
        /* 🧪 Zadanie 15: Uzyj `UnaryOperator<T>` DLA transformacji TEGO SAMEGO typu. */
        public static void main(String[] args) { }
    }

    static class Exercise16_RewriteComparatorWithLambdaAndComparingMethod {
        /* 🧪 Zadanie 16: PRZEPISZ `Comparator` NA lambde I `Comparator.comparing(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseConstructorReferenceForObjectCreation {
        /* 🧪 Zadanie 17: Uzyj REFERENCJI DO konstruktora (`Klasa::new`). */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareLambdaWithInnerClassCapturingBehavior {
        /* 🧪 Zadanie 18: Bez terminala - porownaj przechwytywanie zmiennych PRZEZ lambde Z klasa WEWNETRZNA. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCustomTriFunctionInterface {
        /* 🧪 Zadanie 19: Zaimplementuj WLASNY interfejs `TriFunction<A,B,C,R>` (BRAK gotowego W JDK). */
        public static void main(String[] args) { }
    }

    static class Exercise20_MigrateLegacyCallbackStyleCodeToLambdas {
        /* 🧪 Zadanie 20: PRZEPISZ (HIPOTETYCZNY) STARY kod Z callbackami NA lambdy. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFunctionalInterfaceWithDefaultMethod {
        /* 🧪 Zadanie 21: Powiaz z Lesson04 - zaimplementuj interfejs funkcyjny Z METODA DOMYSLNA. */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildFluentApiUsingChainedFunctionalInterfaces {
        /* 🧪 Zadanie 22: Zbuduj fluentne API OPARTE NA lancuchowaniu interfejsow funkcyjnych. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementMemoizationUsingFunctionAndMap {
        /* 🧪 Zadanie 23: Zaimplementuj memoizacje (`Function` + `Map` cache). */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareLambdaPerformanceWithAnonymousClass {
        /* 🧪 Zadanie 24: Zmierz (przyblizona) roznice WYDAJNOSCI lambdy WZGLEDEM anonimowej klasy. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementStrategyPatternUsingFunctionalInterfaces {
        /* 🧪 Zadanie 25: Powiaz z `_02_oop/Lesson15` - zaimplementuj wzorzec Strategy PRZEZ interfejsy funkcyjne. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainInvokeDynamicBytecodeBehindLambdas {
        /* 🧪 Zadanie 26: Bez terminala - opisz (KONCEPCYJNIE) `invokedynamic` I bajtkod GENEROWANY DLA lambd. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRecursiveLambdaUsingHelperField {
        /* 🧪 Zadanie 27: Zaimplementuj REKURENCYJNA lambde (lambda NIE MOZE odwolac sie DO SIEBIE bezposrednio - potrzebny "trick"). */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildEventListenerSystemUsingConsumerInterfaces {
        /* 🧪 Zadanie 28: Powiaz z `_20_spring_core/Lesson20` - zbuduj PROSTY system zdarzen OPARTY NA `Consumer`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCurryingWithNestedFunctions {
        /* 🧪 Zadanie 29: Zaimplementuj "currying" (`Function<A, Function<B, R>>`). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullFunctionalStyleRefactoringOfImperativeCode {
        /* 🧪 Zadanie 30: PRZEPISZ WIEKSZY, IMPERATYWNY fragment kodu NA STYL funkcyjny (lambdy+Streamy). */
        public static void main(String[] args) { }
    }
}
