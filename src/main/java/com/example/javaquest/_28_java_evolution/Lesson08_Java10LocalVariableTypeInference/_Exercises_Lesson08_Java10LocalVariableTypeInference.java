package com.example.javaquest._28_java_evolution.Lesson08_Java10LocalVariableTypeInference;

public class _Exercises_Lesson08_Java10LocalVariableTypeInference {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DeclareVariableWithVarForSimpleType {
        /* 🧪 Zadanie 1: Zadeklaruj zmienna `var` DLA prostego typu (`String`/`int`). */
        public static void main(String[] args) { }
    }

    static class Exercise02_DeclareVariableWithVarForGenericType {
        /* 🧪 Zadanie 2: Zadeklaruj `var` DLA DLUGIEGO typu generycznego. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseVarInForEachLoop {
        /* 🧪 Zadanie 3: Uzyj `var` W petli `for-each`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ObserveCompileErrorWhenVarWithoutInitializer {
        /* 🧪 Zadanie 4: Zaobserwuj BLAD kompilacji PRZY `var x;` BEZ inicjalizacji. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ObserveCompileErrorWhenAssigningWrongTypeToVar {
        /* 🧪 Zadanie 5: Zaobserwuj BLAD kompilacji PRZY PROBIE przypisania ZLEGO typu DO `var`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainVarIsNotDynamicTyping {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, DLACZEGO `var` TO NIE dynamiczne typowanie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareVerboseTypeWithVarSideBySide {
        /* 🧪 Zadanie 7: Napisz TA SAMA deklaracje 2 sposobami OBOK siebie - porownaj. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseVarWithDiamondOperator {
        /* 🧪 Zadanie 8: Uzyj `var` Z operatorem diamentowym (`<>`). */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhereVarCannotBeUsed {
        /* 🧪 Zadanie 9: Bez terminala - wymien miejsca, GDZIE `var` NIE MOZE byc uzyty (pola, parametry ZWYKLYCH metod, typ zwracany). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListReasonsToAvoidVarForReadability {
        /* 🧪 Zadanie 10: Bez terminala - wypisz sytuacje, GDZIE `var` POGARSZA czytelnosc. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseVarWithStreamPipelineIntermediateResults {
        /* 🧪 Zadanie 11: Uzyj `var` DLA WYNIKOW POSREDNICH pipeline'u Stream. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseVarInTryWithResourcesDeclaration {
        /* 🧪 Zadanie 12: Uzyj `var` W deklaracji try-with-resources. */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareVarReadabilityWithMethodReturningInterfaceType {
        /* 🧪 Zadanie 13: Porownaj CZYTELNOSC `var` DLA metody ZWRACAJACEJ INTERFEJS (typ WYNIKOWY MNIEJ oczywisty). */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseVarWithAnonymousClassPreservingAdditionalMethods {
        /* 🧪 Zadanie 14: Uzyj `var` Z anonimowa klasa (`var` ZACHOWUJE DODATKOWE metody, W ODROZNIENIU OD JAWNEGO typu interfejsu). */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorVerboseCodeToUseVarConsistently {
        /* 🧪 Zadanie 15: PRZEPISZ fragment kodu KONSEKWENTNIE uzywajac `var` TAM, GDZIE to POPRAWIA czytelnosc. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainVarInteractionWithFinalKeyword {
        /* 🧪 Zadanie 16: Powiaz z `_02_oop/Lesson10` - wyjasnij `final var x = ...`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseVarForLambdaParameterJava11Preview {
        /* 🧪 Zadanie 17: Zapowiedz Lesson09 - zbadaj `var` W parametrach lambdy (OD Javy 11). */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareIdeInferredTypeHintsWithVar {
        /* 🧪 Zadanie 18: Bez terminala - opisz, JAK IDE POMAGA odczytac WYWNIOSKOWANY typ `var`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DocumentTeamStyleGuideForVarUsage {
        /* 🧪 Zadanie 19: Zaprojektuj (jako komentarz) STYLE GUIDE uzycia `var` DLA zespolu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_AnalyzeVarUsageAcrossThisCourseCode {
        /* 🧪 Zadanie 20: Bez terminala - przeanalizuj (Z pamieci) GDZIE W tym kursie `var` byl JUZ uzywany. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementArchUnitRuleRestrictingVarUsage {
        /* 🧪 Zadanie 21: Powiaz z `_27_spring_test/Lesson18` - zaprojektuj (koncepcyjnie) regule OGRANICZAJACA nadmierne `var`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_AnalyzeBytecodeShowingVarIsPurelyCompileTime {
        /* 🧪 Zadanie 22: Powiaz z `_15_jvm_internals/Lesson02` - zbadaj (`javap`) BAJTKOD dowodzacy, ze `var` znika PO kompilacji. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildComplexGenericCodeComparingReadabilityWithAndWithoutVar {
        /* 🧪 Zadanie 23: Zbuduj ZLOZONY kod generyczny (3+ poziomy zagniezdzenia typow) I porownaj Z/BEZ `var`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomLinterDetectingOverusedVar {
        /* 🧪 Zadanie 24: Zbuduj PROSTY "linter" (analiza tekstu) WYKRYWAJACY NADUZYWANIE `var` DLA NIEJASNYCH typow. */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareVarWithTypeInferenceInOtherLanguages {
        /* 🧪 Zadanie 25: Bez terminala - porownaj `var` Javy Z wnioskowaniem typu W Kotlinie/C#/TypeScript (KONCEPCYJNIE). */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignCodeReviewChecklistFocusedOnVarUsage {
        /* 🧪 Zadanie 26: Zaprojektuj LISTE kontrolna CODE REVIEW SKUPIONA NA uzyciu `var`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRefactoringToolAutomaticallySuggestingVar {
        /* 🧪 Zadanie 27: Zaprojektuj (koncepcyjnie) narzedzie SUGERUJACE, GDZIE `var` POPRAWILBY czytelnosc. */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeImpactOfVarOnCodeReviewDifficulty {
        /* 🧪 Zadanie 28: Bez terminala - przeanalizuj WPLYW `var` NA TRUDNOSC code review (BRAK typu W DIFFIE). */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildEducationalDemoShowingVarWithTenDifferentTypes {
        /* 🧪 Zadanie 29: Zbuduj DEMO Z 10 ROZNYMI typami wywnioskowanymi PRZEZ `var` NARAZ. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullVarAdoptionPolicyForLargeLegacyCodebase {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA polityke adopcji `var` W DUZYM, ISTNIEJACYM (legacy) kodzie. */
        public static void main(String[] args) { }
    }
}
