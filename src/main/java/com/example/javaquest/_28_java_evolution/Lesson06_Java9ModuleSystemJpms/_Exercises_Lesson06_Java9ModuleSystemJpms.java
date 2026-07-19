package com.example.javaquest._28_java_evolution.Lesson06_Java9ModuleSystemJpms;

public class _Exercises_Lesson06_Java9ModuleSystemJpms {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InspectModuleOfDifferentJdkClass {
        /* 🧪 Zadanie 1: Zbadaj `.getModule()` INNEJ klasy JDK (np. `java.sql.Connection`). */
        public static void main(String[] args) { }
    }

    static class Exercise02_CheckIfCurrentClassModuleIsNamed {
        /* 🧪 Zadanie 2: Sprawdz `.isNamed()` DLA WLASNEJ klasy. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListExportedPackagesOfJavaSqlModule {
        /* 🧪 Zadanie 3: Wypisz `exports` modulu `java.sql`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhatUnnamedModuleMeans {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, CZYM jest "unnamed module". */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareModuleInfoWithMavenPomDependencies {
        /* 🧪 Zadanie 5: Bez terminala - porownaj `module-info.java` (`requires`) Z `pom.xml` (`<dependency>`). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListAllModulesInBootLayer {
        /* 🧪 Zadanie 6: Powiaz z `_14_advancedjava/Lesson27` - wypisz WSZYSTKIE moduly Z `ModuleLayer.boot()`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckIfModuleCanReadAnotherModule {
        /* 🧪 Zadanie 7: Sprawdz `.canRead(...)` MIEDZY 2 modulami. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyJdkWasSplitIntoModules {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij GLOWNY powod PODZIALU JDK NA moduly W Javie 9. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListThreeBenefitsOfJpms {
        /* 🧪 Zadanie 9: Wymien 3 KORZYSCI JPMS (enkapsulacja/rozmiar/niezawodna konfiguracja). */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentWhereFullJpmsTheoryLivesInThisCourse {
        /* 🧪 Zadanie 10: Bez terminala - wskaz, GDZIE W tym kursie jest PELNA teoria PISANIA WLASNYCH modulow. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InspectModuleDescriptorRequiresOfJavaSql {
        /* 🧪 Zadanie 11: Zbadaj `requires` modulu `java.sql` (`ModuleDescriptor.requires()`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_CheckIfPackageIsOpenedForReflection {
        /* 🧪 Zadanie 12: Sprawdz, CZY dany pakiet jest `opens` DLA refleksji (`.isOpen(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareStrongEncapsulationWithPublicModifier {
        /* 🧪 Zadanie 13: Bez terminala - porownaj SILNA enkapsulacje JPMS (`exports`) Z ZWYKLYM `public`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ListModulesRequiredTransitivelyByJavaSql {
        /* 🧪 Zadanie 14: Zbadaj `requires transitive` NA przykladzie modulu JDK. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainAutomaticModulesForNonModularJars {
        /* 🧪 Zadanie 15: Bez terminala - wyjasnij "automatic modules" DLA JAR-ow BEZ `module-info.java`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareClasspathHellWithModulePathReliability {
        /* 🧪 Zadanie 16: Bez terminala - porownaj "classpath hell" (JAR hell) Z NIEZAWODNA konfiguracja module path. */
        public static void main(String[] args) { }
    }

    static class Exercise17_InspectServiceProvidersDeclaredByJavaBase {
        /* 🧪 Zadanie 17: Powiaz z `_14_advancedjava/Lesson26` - zbadaj `uses`/`provides` modulu `java.base`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainJlinkCustomRuntimeImageConcept {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij koncepcje `jlink` (WLASNY, ZMINIMALIZOWANY runtime). */
        public static void main(String[] args) { }
    }

    static class Exercise19_DocumentWhyThisProjectStaysOnClasspath {
        /* 🧪 Zadanie 19: Bez terminala - podsumuj (Z `CLAUDE.md`) DECYZJE, DLACZEGO ten kurs POZOSTAJE NA classpath. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignMigrationPlanFromClasspathToModulePath {
        /* 🧪 Zadanie 20: Zaprojektuj (HIPOTETYCZNY) PLAN migracji WIEKSZEGO projektu Z classpath NA module path. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildCustomModuleLayerProgrammatically {
        /* 🧪 Zadanie 21: Powiaz z `_14_advancedjava/Lesson27-28` - zbuduj WLASNY `ModuleLayer` programowo (subprocess). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementModuleReflectionInspectorTool {
        /* 🧪 Zadanie 22: Zbuduj narzedzie WYPISUJACE PELNA strukture (exports/requires/opens) DOWOLNEGO modulu JDK. */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareJpmsWithOsgiModularitySystems {
        /* 🧪 Zadanie 23: Bez terminala - porownaj JPMS Z OSGi (STARSZY system modularnosci Javy, KONCEPCYJNIE). */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnalyzeRealWorldJpmsAdoptionChallenges {
        /* 🧪 Zadanie 24: Bez terminala - przeanalizuj (Z wiedzy) DLACZEGO WIELE bibliotek WCIAZ NIE UZYWA JPMS W PELNI. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildJlinkBasedMinimalRuntimeDemo {
        /* 🧪 Zadanie 25: Powiaz z `_11_buildtools` - zbuduj (PRZEZ `ProcessBuilder`) demo `jlink` TWORZACEGO minimalny runtime. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementServiceLoaderAcrossModuleBoundaries {
        /* 🧪 Zadanie 26: Powiaz z `_14_advancedjava/Lesson26` - zaimplementuj `ServiceLoader` DZIALAJACY MIEDZY prawdziwymi modulami. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignModularMonolithUsingJpmsBoundaries {
        /* 🧪 Zadanie 27: Powiaz z `_17_architecture/Lesson17` - zaprojektuj modularny monolit Z GRANICAMI wymuszonymi PRZEZ JPMS. */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeSecurityBenefitsOfStrongEncapsulation {
        /* 🧪 Zadanie 28: Powiaz z `_19_security_basics` - przeanalizuj korzysci bezpieczenstwa SILNEJ enkapsulacji JPMS. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullMultiModuleProjectWithThreeModulesAndDependencies {
        /* 🧪 Zadanie 29: Powiaz z `_14_advancedjava/Lesson27-28` - zbuduj PELNY projekt Z 3 MODULAMI I zaleznosciami MIEDZY nimi. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullJpmsAdoptionStrategyForLargeLegacyCodebase {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA strategie adopcji JPMS DLA DUZEGO, ISTNIEJACEGO (legacy) kodu. */
        public static void main(String[] args) { }
    }
}
