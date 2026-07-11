package com.example.javaquest._21_spring_boot.Lesson02_ProjectSetup;

public class _Exercises_Lesson02_ProjectSetup {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatSpringInitializrDoes {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, co robi Spring Initializr i jakie
         * pliki generuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListStandardMavenDirectories {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wymien standardowe katalogi Maven Standard
         * Directory Layout (powiaz z `_11_buildtools`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyDirectoriesExistProgrammatically {
        /*
         * 🧪 Zadanie 3:
         * Odtworz kod z teorii sprawdzajacy istnienie katalogow - dodaj
         * sprawdzenie WLASNEGO, DODATKOWEGO katalogu (np.
         * `src/main/resources/static`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParsePomXmlAndCountAllDependencies {
        /*
         * 🧪 Zadanie 4:
         * Rozbuduj parser z teorii, zeby liczyl WSZYSTKIE zaleznosci
         * (nie tylko startery).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListNonStarterDependencies {
        /*
         * 🧪 Zadanie 5:
         * Wypisz zaleznosci, ktore NIE sa starterami (np. jbcrypt, jjwt
         * z `_19_security_basics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainMavenVsGradleChoice {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: powiaz z `_11_buildtools` - jakie SA glowne
         * roznice miedzy wygenerowanym projektem Maven a Gradle z
         * Initializr?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyGeneratedMainApplicationClassPattern {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: opisz, jak WYGLADA (nazwa, adnotacje) klasa
         * `@SpringBootApplication` generowana przez Initializr dla
         * projektu o nazwie "MyApp".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainGroupIdArtifactIdVersionChoice {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij znaczenie pol "Group"/"Artifact" w
         * formularzu Initializr - powiaz z koordynatami Maven z
         * `_11_buildtools`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CheckJavaVersionDeclaredInThisProject {
        /*
         * 🧪 Zadanie 9:
         * Sparsuj `pom.xml` (rozbudowa Zadania 4) i wypisz zadeklarowana
         * wersje Javy (`<java.version>`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainPackagingJarVsWar {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij roznice miedzy pakowaniem "jar" a
         * "war" w Initializr - ktore jest DOMYSLNE i DLACZEGO dla
         * Spring Boota?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_BuildMiniProjectGeneratorFromScratch {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode GENERUJACA (jako String) minimalny `pom.xml`
         * (na wzor tego, co dala Initializr) dla podanego groupId/
         * artifactId/listy starterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GenerateMainApplicationClassSourceCode {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj generator z Zadania 11 o generowanie ZRODLA klasy
         * `@SpringBootApplication` dla podanej nazwy pakietu/klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteGeneratedFilesToTempDirectory {
        /*
         * 🧪 Zadanie 13:
         * Zapisz wygenerowane pliki z Zadania 11/12 do katalogu
         * tymczasowego (`Files.createTempDirectory`) - wypisz sciezke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidateGeneratedPomXmlIsWellFormed {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj WLASNY, wygenerowany `pom.xml` z powrotem (jak w
         * teorii) - zweryfikuj, ze jest POPRAWNYM XML-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareGeneratedProjectWithRealOne {
        /*
         * 🧪 Zadanie 15:
         * Porownaj strukture WYGENEROWANEGO (Zadanie 11-13) projektu z
         * REALNA struktura tego kursu - jakie ROZNICE zauwazasz?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDependencyVersionExtractor {
        /*
         * 🧪 Zadanie 16:
         * Rozbuduj parser pom.xml o odczyt WERSJI zaleznosci (gdy jest
         * JAWNIE podana) - odroznij od "zarzadzanych przez BOM".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainParentPomBomMechanism {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, jak `spring-boot-starter-parent`
         * (widoczny w `pom.xml` tego projektu) zarzadza wersjami BEZ
         * jawnego podawania ich w KAZDEJ zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ListPluginsDeclaredInThisPomXml {
        /*
         * 🧪 Zadanie 18:
         * Sparsuj sekcje `<plugins>` w `pom.xml` tego projektu - wypisz
         * WSZYSTKIE zadeklarowane wtyczki Maven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareThisProjectStructureWithMultiModule {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: powiaz z `_11_buildtools/Lesson14_MavenAdvanced` -
         * jak wygladalby ten projekt jako multi-module (osobny modul na
         * kazdy rozdzial)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildProjectSetupChecklistDocument {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) checkliste "co sprawdzic PO wygenerowaniu
         * projektu z Initializr, PRZED pierwszym commitem".
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullProjectScaffoldingTool {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNE narzedzie "scaffoldingowe" - generuje CALA
         * strukture katalogow + pom.xml + klase startowa + prosty test -
         * na podstawie listy parametrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementStarterRecommendationEngine {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prosty "silnik rekomendacji" starterow - na
         * podstawie listy "chce zrobic X" (np. "REST API", "baza danych")
         * zaproponuj ODPOWIEDNIE startery (zapowiedz Lesson03).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ValidateGeneratedProjectCompilesForReal {
        /*
         * 🧪 Zadanie 23:
         * Wygeneruj PELNY, MINIMALNY projekt (Zadanie 21) i sprobuj go
         * SKOMPILOWAC PRZEZ `ProcessBuilder` wywolujacy `mvn compile`
         * (wzorem `_11_buildtools`) - zweryfikuj sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareInitializrApiWithWebFormConceptually {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: Spring Initializr ma TEZ API (nie tylko
         * formularz webowy, uzywane np. przez IDE) - opisz, jak
         * WYGLADALBY skrypt automatyzujacy generowanie projektu przez
         * to API (bez faktycznego wywolania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementProjectStructureLinter {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj "linter" struktury projektu - sprawdza, czy
         * WSZYSTKIE oczekiwane katalogi/pliki (pom.xml, klasa startowa,
         * application.properties) istnieja i sa NA WLASCIWYM miejscu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareGradleKotlinDslWithGroovyDsl {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala (powiaz z `_11_buildtools/Lesson19-25`): Initializr
         * pozwala wybrac Gradle Groovy LUB Gradle Kotlin DSL - jakie SA
         * roznice?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementMigrationFromXmlToProgrammaticGeneration {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj odwrotnosc parsera z teorii - GENERATOR
         * przyjmujacy Mape/liste obiektow i TWORZACY z nich `pom.xml`
         * (round-trip: parsuj -> modyfikuj -> zapisz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeRealWorldTeamProjectSetupConventions {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj (opisz) firmowe "standardy zakladania
         * projektu" (naming, wymagane startery bazowe, wymagane wtyczki) -
         * dla zespolu uzywajacego Spring Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementDependencyConflictDetectorAfterGeneration {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj (Mapy "co dostarcza kazdy starter") wykrywanie
         * POTENCJALNYCH konfliktow zaleznosci PRZED faktycznym
         * wygenerowaniem projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteProjectBootstrapPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pipeline" bootstrapowania projektu -
         * generowanie (Zadanie 21) + walidacja struktury (Zadanie 25) +
         * proba kompilacji (Zadanie 23) - jeden spojny, dzialajacy
         * proces "od pomyslu do dzialajacego szkieletu".
         */
        public static void main(String[] args) { }
    }
}
