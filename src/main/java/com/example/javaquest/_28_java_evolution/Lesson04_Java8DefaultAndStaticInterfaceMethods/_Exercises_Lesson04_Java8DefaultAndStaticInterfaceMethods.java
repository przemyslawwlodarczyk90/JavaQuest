package com.example.javaquest._28_java_evolution.Lesson04_Java8DefaultAndStaticInterfaceMethods;

public class _Exercises_Lesson04_Java8DefaultAndStaticInterfaceMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddDefaultMethodToOwnInterface {
        /* 🧪 Zadanie 1: Dodaj METODE `default` DO WLASNEGO interfejsu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddStaticFactoryMethodToInterface {
        /* 🧪 Zadanie 2: Dodaj METODE `static` (fabryke) DO interfejsu. */
        public static void main(String[] args) { }
    }

    static class Exercise03_OverrideDefaultMethodInImplementingClass {
        /* 🧪 Zadanie 3: NADPISZ metode `default` W KLASIE implementujacej. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CallSuperDefaultMethodFromOverride {
        /* 🧪 Zadanie 4: Wywolaj `Interfejs.super.metoda()` Z NADPISANIA. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyDefaultMethodsWereIntroduced {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij GLOWNY powod wprowadzenia metod `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddSecondClassImplementingInterfaceWithoutOverride {
        /* 🧪 Zadanie 6: Dodaj DRUGA klase implementujaca BEZ nadpisywania metody `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListJdkExamplesOfDefaultMethods {
        /* 🧪 Zadanie 7: Wypisz 3 przyklady metod `default` Z SAMEGO JDK. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareStaticInterfaceMethodWithUtilityClass {
        /* 🧪 Zadanie 8: Bez terminala - porownaj metode `static` W interfejsie Z osobna klasa "Utils". */
        public static void main(String[] args) { }
    }

    static class Exercise09_CallStaticMethodDirectlyOnInterface {
        /* 🧪 Zadanie 9: Wywolaj metode `static` BEZPOSREDNIO NA interfejsie. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDiamondProblemWithDefaultMethods {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij "diamond problem" PRZY WIELU interfejsach Z TA SAMA metoda `default`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ResolveDiamondProblemByExplicitOverride {
        /* 🧪 Zadanie 11: Rozwiaz "diamond problem" JAWNYM nadpisaniem I `InterfaceA.super.x()`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainPriorityOfClassMethodOverInterfaceDefault {
        /* 🧪 Zadanie 12: Bez terminala - wyjasnij, DLACZEGO metoda Z KLASY MA PIERWSZENSTWO NAD `default` Z interfejsu. */
        public static void main(String[] args) { }
    }

    static class Exercise13_BuildFunctionalInterfaceWithDefaultHelperMethod {
        /* 🧪 Zadanie 13: Powiaz z Lesson02 - zbuduj `@FunctionalInterface` Z DODATKOWA metoda `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_RefactorAbstractClassToInterfaceWithDefaultMethods {
        /* 🧪 Zadanie 14: PRZEPISZ klase ABSTRAKCYJNA NA interfejs Z metodami `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseDefaultMethodToProvideTemplateMethodPattern {
        /* 🧪 Zadanie 15: Powiaz z `_02_oop/Lesson15` - zaimplementuj wzorzec Template Method PRZEZ metody `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainWhyInterfacesStillCannotHaveState {
        /* 🧪 Zadanie 16: Bez terminala - wyjasnij, DLACZEGO interfejsy NADAL NIE MOGA miec STANU (pol instancyjnych). */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildInterfaceHierarchyWithMultipleDefaultMethods {
        /* 🧪 Zadanie 17: Zbuduj HIERARCHIE interfejsow (dziedziczenie interfejsow) Z WIELOMA metodami `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareJava8InterfaceEvolutionWithOtherLanguages {
        /* 🧪 Zadanie 18: Bez terminala - porownaj EWOLUCJE interfejsow Javy 8 Z podobnymi mechanizmami W INNYCH jezykach. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementMultipleInterfacesWithConflictingDefaults {
        /* 🧪 Zadanie 19: Zaimplementuj klase Z 2 interfejsami MAJACYMI KONFLIKTUJACE metody `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignApiEvolutionStrategyUsingDefaultMethods {
        /* 🧪 Zadanie 20: Zaprojektuj strategie EWOLUCJI WLASNEGO API PRZEZ metody `default`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildMixinStyleCompositionUsingMultipleInterfaces {
        /* 🧪 Zadanie 21: Zbuduj kompozycje W STYLU "mixin" PRZEZ WIELE interfejsow Z metodami `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFluentBuilderUsingDefaultMethodsOnInterface {
        /* 🧪 Zadanie 22: Zaimplementuj fluentny Builder OPARTY NA interfejsie Z metodami `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_AnalyzeRealJdkSourceForDefaultMethodUsage {
        /* 🧪 Zadanie 23: Bez terminala - przeanalizuj (Z pamieci) `java.util.Collection`/`java.util.Map` POD KATEM metod `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementVisitorPatternUsingDefaultMethods {
        /* 🧪 Zadanie 24: Powiaz z `_02_oop/Lesson15` - zaimplementuj wzorzec Visitor PRZEZ metody `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildBackwardCompatibleLibraryApiEvolutionDemo {
        /* 🧪 Zadanie 25: Zbuduj DEMO ewolucji WLASNEJ "biblioteki" (interfejs+2 implementacje) DODAJAC NOWA metode `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainBridgeMethodsGeneratedByCompilerForDefaultMethods {
        /* 🧪 Zadanie 26: Bez terminala - opisz (KONCEPCYJNIE) "bridge methods" GENEROWANE przez kompilator. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementStrategyPatternCombiningStaticFactoryAndDefaultMethods {
        /* 🧪 Zadanie 27: Powiaz z `_02_oop/Lesson15` - zaimplementuj Strategy LACZAC fabryke `static` Z zachowaniem `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareDefaultMethodsWithTraitsInOtherLanguages {
        /* 🧪 Zadanie 28: Bez terminala - porownaj metody `default` Z "traits"/"mixins" W INNYCH jezykach (KONCEPCYJNIE). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignPluginArchitectureUsingDefaultMethodExtensionPoints {
        /* 🧪 Zadanie 29: Powiaz z `_17_architecture` - zaprojektuj architekture wtyczek Z PUNKTAMI rozszerzenia `default`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullInterfaceEvolutionGuideForPublicLibraryMaintainers {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY przewodnik EWOLUCJI interfejsow PUBLICZNEJ biblioteki DLA maintainerow. */
        public static void main(String[] args) { }
    }
}
