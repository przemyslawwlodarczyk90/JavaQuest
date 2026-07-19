package com.example.javaquest._27_spring_test.Lesson18_ArchUnitForArchitectureTesting;

public class _Exercises_Lesson18_ArchUnitForArchitectureTesting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ImportPackageWithClassFileImporter {
        /* 🧪 Zadanie 1: Zaimportuj WLASNY pakiet `ClassFileImporter`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteSimpleNoClassesRule {
        /* 🧪 Zadanie 2: Napisz PROSTA regule `noClasses().that()...should()...`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CheckRuleAgainstCompliantCode {
        /* 🧪 Zadanie 3: Sprawdz regule PRZECIW ZGODNEMU Z NIA kodowi. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CheckRuleAgainstViolatingCode {
        /* 🧪 Zadanie 4: Sprawdz TA SAMA regule PRZECIW ZLAMANEMU kodowi I przechwyc `AssertionError`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyArchUnitReadsBytecodeNotSource {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO ArchUnit czyta BAJTKOD, NIE zrodla. */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteRuleForbiddingFieldInjection {
        /* 🧪 Zadanie 6: Powiaz z `_20_spring_core/Lesson11` - napisz regule ZAKAZUJACA `@Autowired` NA POLU. */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteRuleRequiringNamingConvention {
        /* 🧪 Zadanie 7: Napisz regule WYMAGAJACA, ze klasy `@Repository` MAJA nazwe KONCZACA sie NA "Repository". */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountImportedClassesInPackage {
        /* 🧪 Zadanie 8: Policz LICZBE zaimportowanych klas W WYBRANYM pakiecie. */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteRuleForbiddingSystemOutInProductionCode {
        /* 🧪 Zadanie 9: Napisz regule ZAKAZUJACA `System.out.println` (KONCEPCYJNIE - w tym kursie WYSTEPUJE CELOWO). */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentDifferenceBetweenArchUnitAndCheckstyle {
        /* 🧪 Zadanie 10: Bez terminala - porownaj ArchUnit Z Checkstyle/PMD (`_16_clean_code/Lesson20`). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteLayeredArchitectureRuleWithThreeLayers {
        /* 🧪 Zadanie 11: Zbadaj `layeredArchitecture()` DSL DLA 3 warstw (Controller/Service/Repository). */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteRuleForbiddingCyclicDependenciesBetweenPackages {
        /* 🧪 Zadanie 12: Napisz regule ZAKAZUJACA CYKLI MIEDZY pakietami (`slices().matching(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteRuleEnforcingDependencyDirection {
        /* 🧪 Zadanie 13: Powiaz z `_17_architecture/Lesson10` - napisz regule WYMUSZAJACA kierunek zaleznosci "TYLKO DO SRODKA". */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteRuleForbiddingDirectExceptionCatchingOfRuntimeException {
        /* 🧪 Zadanie 14: Napisz regule ZAKAZUJACA lapania GOLEGO `RuntimeException`/`Exception`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImportOnlyTestClassesAndCheckNamingConvention {
        /* 🧪 Zadanie 15: Powiaz z `_25_unit_testing/Lesson18` - napisz regule SPRAWDZAJACA konwencje nazewnicza testow. */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteRuleForbiddingUtilityClassesFromBeingInstantiated {
        /* 🧪 Zadanie 16: Napisz regule WYMAGAJACA, ze klasy "utility" (WYLACZNIE statyczne metody) MAJA PRYWATNY konstruktor. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CombineMultipleRulesIntoCompositeRule {
        /* 🧪 Zadanie 17: Polacz WIELE regul W JEDNA (`.and()`/kolekcja regul). */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteRuleForbiddingJodaTimeUsage {
        /* 🧪 Zadanie 18: Napisz regule ZAKAZUJACA uzycia PRZESTARZALEJ biblioteki (KONCEPCYJNIE, np. Joda-Time). */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestArchUnitRuleAsRealJUnit5ArchTest {
        /* 🧪 Zadanie 19: Zbadaj `@AnalyzeClasses`+`@ArchTest` (natywna integracja Z JUnit 5). */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullArchitectureRuleSuiteForLayeredApp {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet regul architektonicznych DLA warstwowej aplikacji. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomArchCondition {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `ArchCondition<JavaClass>` DLA NIESTANDARDOWEJ reguly. */
        public static void main(String[] args) { }
    }

    static class Exercise22_WriteRuleValidatingHexagonalArchitecturePorts {
        /* 🧪 Zadanie 22: Powiaz z `_17_architecture/Lesson12_PortsAndAdapters` - napisz regule WALIDUJACA porty/adaptery. */
        public static void main(String[] args) { }
    }

    static class Exercise23_WriteRuleForbiddingBoundedContextLeakage {
        /* 🧪 Zadanie 23: Powiaz z `_17_architecture/Lesson06_BoundedContexts` - napisz regule ZAPOBIEGAJACA "przeciekaniu" MIEDZY kontekstami. */
        public static void main(String[] args) { }
    }

    static class Exercise24_IntegrateArchUnitIntoCiPipelineAsGate {
        /* 🧪 Zadanie 24: Powiaz z `_26_integration_testing/Lesson14` - zaprojektuj "bramke" CI Z regulami ArchUnit. */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteRuleAnalyzingActualCourseChapterDependencies {
        /* 🧪 Zadanie 25: Zaimportuj WLASNY, WIEKSZY fragment TEGO kursu I zbadaj RZECZYWISTE zaleznosci. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementFreezingArchitectureViolationsForLegacyCode {
        /* 🧪 Zadanie 26: Zbadaj `FreezingArchRule` (ZAMRAZANIE ISTNIEJACYCH naruszen W legacy kodzie). */
        public static void main(String[] args) { }
    }

    static class Exercise27_WriteRuleEnforcingModularMonolithBoundaries {
        /* 🧪 Zadanie 27: Powiaz z `_17_architecture/Lesson17_ModularMonolith` - napisz regule WYMUSZAJACA GRANICE modulow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildArchitectureFitnessFunctionDashboard {
        /* 🧪 Zadanie 28: Zaprojektuj (koncepcyjnie) "fitness function" dashboard SLEDZACY naruszenia W czasie. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCustomImportOptionExcludingGeneratedCode {
        /* 🧪 Zadanie 29: Zbadaj `ImportOption` WYKLUCZAJACA WYGENEROWANY kod Z analizy. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullArchitectureGovernanceStandardUsingArchUnit {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard zarzadzania architektura OPARTY NA ArchUnit DLA organizacji. */
        public static void main(String[] args) { }
    }
}
