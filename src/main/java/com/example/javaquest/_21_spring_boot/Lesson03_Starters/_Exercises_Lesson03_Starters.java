package com.example.javaquest._21_spring_boot.Lesson03_Starters;

public class _Exercises_Lesson03_Starters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatStarterIsInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest "starter"
         * Spring Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CheckPresenceOfOwnChosenClass {
        /*
         * 🧪 Zadanie 2:
         * Uzyj `Class.forName(...)` (jak w teorii), zeby sprawdzic
         * obecnosc WLASNEJ, wybranej klasy z biblioteki dostarczonej
         * przez ktoregos ze starterow tego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListWhatStarterDataJpaWouldBring {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala (dokumentacja): wymien, JAKIE biblioteki
         * dostarczylby `spring-boot-starter-data-jpa` (zapowiedz
         * `_23_spring_data_jpa`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyStarterPomIsAlmostEmpty {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij, dlaczego plik POM samego startera
         * (np. `spring-boot-starter-web`) jest "prawie pusty".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareManualDependencyListWithStarterEquivalent {
        /*
         * 🧪 Zadanie 5:
         * Policz linie kodu/wpisow potrzebne RECZNIE (bez startera) vs
         * 1 wpis STARTERA - dla WLASNEGO przykladu 4 bibliotek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainBomConceptInOwnWords {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, czym jest BOM (Bill of Materials) w
         * kontekscie Maven/Spring Boot.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindExceptionsWithExplicitVersionsInPomXml {
        /*
         * 🧪 Zadanie 7:
         * Sprawdz w `pom.xml` tego projektu, KTORE zaleznosci MAJA
         * jawna wersje (WYJATKI od zarzadzania przez BOM) - DLACZEGO
         * (patrz notatki w CLAUDE.md o Lombok/jbcrypt/jjwt)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListAllStartersUsedInThisProjectFromMemory {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien z pamieci WSZYSTKIE 5 starterow z tej
         * lekcji i ich przeznaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainTransitiveDependencyConceptWithStarters {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: powiaz "starter" z pojeciem zaleznosci
         * TRANZYTYWNEJ z `_19_security_basics/Lesson20`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListCommonStarterNamesFromDocumentation {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja Spring Boot): wymien 5 innych,
         * popularnych starterow SPOZA tego projektu (np. starter-
         * security, starter-actuator).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_RunMavenDependencyTreeAndAnalyzeStarterContribution {
        /*
         * 🧪 Zadanie 11:
         * W terminalu uruchom `mvnw.cmd dependency:tree` - znajdz
         * WSZYSTKIE biblioteki wciagniete PRZEZ `spring-boot-starter-web`
         * (zagniezdzone POD nim w drzewie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementClasspathScannerForKnownLibraries {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj `checkClassPresent` z teorii o skanowanie LISTY 10+
         * znanych klas naraz - wygeneruj raport "co jest, czego brak".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareStarterVersionAcrossBootVersions {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala (dokumentacja): sprawdz, jak zmienialaby sie
         * wersja Tomcata wciagana przez `spring-boot-starter-web` miedzy
         * Boot 2.x a 3.x (powiaz z `_20_spring_core/Lesson02`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomStarterLikeAggregatorPom {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj (opisz jako tekst `pom.xml`) WLASNY, "firmowy"
         * starter agregujacy 3 czesto uzywane RAZEM zaleznosci Twojego
         * zespolu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainExclusionsMechanismForStarters {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala (dokumentacja): wyjasnij, jak WYKLUCZYC
         * pojedyncza, niechciana biblioteke wciagnieta przez starter
         * (element `<exclusions>` w Maven).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareLoggingStarterWithManualLogbackSetup {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: porownaj domyslne logowanie z `spring-boot-
         * starter` z RECZNA konfiguracja Logbacka z `_13_libraries/
         * Lesson16`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDependencyPresenceHealthCheck {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj metode "health check" PRZY STARCIE aplikacji -
         * sprawdza, czy WYMAGANE klasy (z listy) sa na classpath, rzuca
         * czytelny blad jesli brak.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhyOverridingBomVersionIsRisky {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjasnij, DLACZEGO recznie nadpisanie wersji
         * zarzadzanej przez BOM (np. jawne podanie innej wersji Jacksona)
         * MOZE byc ryzykowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareStarterTestWithManualTestSetup {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: sprawdz (dokumentacja), co DOKLADNIE wciaga
         * `spring-boot-starter-test` (JUnit 5, Mockito, AssertJ,
         * Hamcrest, JSONassert) - PRZYSZLY temat testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildStarterPurposeReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" 10 najpopularniejszych starterow
         * Spring Boota (spoza tego projektu TEZ) z KROTKIM opisem
         * KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRealCustomStarterProject {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj (opisz strukture plikow) PRAWDZIWY, WLASNY starter
         * Maven - osobny modul z `pom.xml` agregujacym zaleznosci +
         * `META-INF/spring/...AutoConfiguration.imports` (zapowiedz
         * `Lesson15_CustomAutoConfigurationAndStarters`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AnalyzeTransitiveConflictResolutionWithStarters {
        /*
         * 🧪 Zadanie 22:
         * Zbadaj (teoretycznie, na podstawie `_11_buildtools`) jak Maven
         * rozwiazuje KONFLIKT wersji, gdy 2 startery wciagaja RÓZNE
         * wersje TEJ SAMEJ biblioteki (zasada "najblizszej" definicji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDependencyGraphVisualizerForStarters {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj metode BUDUJACA (recznie, z zasymulowanych danych)
         * graf "starter -> biblioteki, ktore wciaga" - wypisz go jako
         * drzewo tekstowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureClasspathSizeImpactPerStarter {
        /*
         * 🧪 Zadanie 24:
         * Zmierz (w przyblizeniu, przez liczbe plikow .jar w lokalnym
         * repozytorium Maven) "koszt" (liczba plikow JAR) dodania
         * `spring-boot-starter-web` do PUSTEGO projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementBomVersionComparisonAcrossReleases {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: porownaj (dokumentacja release notes) wersje
         * kluczowych bibliotek zarzadzanych przez BOM Boot 3.3 vs 3.4
         * (ten kurs) - co sie ZMIENILO?
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignInternalStarterNamingConvention {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (opisz) konwencje nazewnicza dla WEWNETRZNYCH
         * starterow firmowych (np. `firma-spring-boot-starter-*`) -
         * uzasadnij wybor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSecurityAuditForStarterDependencies {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson20_
         * DependencyAndSupplyChainSecurity` - zaimplementuj skan
         * WSZYSTKICH bibliotek wciagnietych przez startery pod katem
         * znanych CVE (symulowana baza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareStarterApproachWithGradlePlatform {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: powiaz z `_11_buildtools/Lesson21_
         * GradleDependencyManagement` - jak Gradle realizuje TA SAMA
         * ideee (BOM/platform) co Maven?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementStarterMigrationGuideGenerator {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj generator "przewodnika migracji" - dla listy
         * "starych" zaleznosci (recznie dobranych) zaproponuj ODPOWIEDNI
         * starter Boota jako zamiennik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteStarterEcosystemAnalysisCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna analize "ekosystemu starterow" TEGO projektu -
         * lista starterow (Lesson02) + ich przeznaczenie (teoria) + realnie
         * obecne klasy na classpath (Zadanie 12) + rekomendacje BOM -
         * jeden spojny raport.
         */
        public static void main(String[] args) { }
    }
}
