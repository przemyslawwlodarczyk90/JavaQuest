package com.example.javaquest._24_spring_security.Lesson03_SecurityConfigEvolutionOldVsNew;

public class _Exercises_Lesson03_SecurityConfigEvolutionOldVsNew {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TryImportingWebSecurityConfigurerAdapter {
        /*
         * 🧪 Zadanie 1:
         * SPROBUJ dodac `import ...WebSecurityConfigurerAdapter;` DO
         * pliku W TYM projekcie - zaobserwuj DOKLADNY blad kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyAdapterWasRemoved {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij (WLASNYMI slowami), DLACZEGO
         * `WebSecurityConfigurerAdapter` zostal USUNIETY (dziedziczenie
         * vs kompozycja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyVersionFromCodeSnippet {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: majac fragment kodu Z `.antMatchers(...)`,
         * okresl PRZYBLIZONA wersje Security, POD KTORA zostal napisany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RewriteOldStyleSnippetToNew {
        /*
         * 🧪 Zadanie 4:
         * Przepisz podany (W komentarzu) fragment
         * `.authorizeRequests().antMatchers(...)` NA
         * `.authorizeHttpRequests().requestMatchers(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainEnableMethodSecurityDefault {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, CO oznacza "prePostEnabled=true JUZ
         * DOMYSLNIE" W `@EnableMethodSecurity`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddDenyAllFallbackRule {
        /*
         * 🧪 Zadanie 6:
         * Zmien `.anyRequest().denyAll()` W `ModernSecurityConfig` NA
         * `.anyRequest().authenticated()` I porownaj zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestUnauthenticatedRequestToDenyAllPath {
        /*
         * 🧪 Zadanie 7:
         * Wyslij zadanie DO sciezki objetej `denyAll()` Z poprawnymi
         * poswiadczeniami - zweryfikuj, ze WCIAZ dostajesz 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListThreeDeprecatedApisFromThisLesson {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz WSZYSTKIE 3 przestarzale API omowione W
         * tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SearchOnlineTutorialAndClassifyItsAge {
        /*
         * 🧪 Zadanie 9:
         * Znajdz (opisowo) fragment tutoriala Security W internecie I
         * sklasyfikuj JEGO wiek NA PODSTAWIE uzytego API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMixingOldAndNewApiIsDangerous {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO mieszanie
         * `authorizeRequests()` Z `authorizeHttpRequests()` W JEDNEJ
         * konfiguracji jest NIEBEZPIECZNE (blad runtime, NIE kompilacji).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildTimelineOfSpringSecurityVersions {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala: zbuduj OS CZASU (Security 2.x -> 6.x) Z
         * KLUCZOWYMI zmianami API OMOWIONYMI W tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MapSpringBootToSpringSecurityVersions {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_20_spring_core/Lesson02` - dopasuj wersje Spring
         * Boot (2.x/3.x) DO odpowiadajacych wersji Spring Security.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyJavaxVsJakartaInSecurityContext {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: wyjasnij, CZY/JAK zmiana `javax.*`->`jakarta.*`
         * (powiazanie Z `_20_spring_core/Lesson02`) dotyczy Spring
         * Security.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteMigrationGuideForLegacyProject {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: napisz KROTKI przewodnik migracji DLA projektu
         * Z `WebSecurityConfigurerAdapter` DO Security 6.x.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareCompileTimeVsRuntimeBreakage {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: porownaj (Z przykladami Z TEJ lekcji), KTORE
         * zmiany API dajA blad KOMPILACJI, A KTORE blad RUNTIME.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestHttpMethodRestrictedRule {
        /*
         * 🧪 Zadanie 16:
         * Wyslij zadanie POST DO `/api/ping` (regula ograniczona DO
         * GET) I zweryfikuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainWhyRequestMatchersAcceptsHttpMethod {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, PO CO `requestMatchers(HttpMethod,
         * String)` przyjmuje METODE HTTP jako DODATKOWY parametr.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RewriteEnableGlobalMethodSecuritySnippet {
        /*
         * 🧪 Zadanie 18:
         * Przepisz podany (W komentarzu)
         * `@EnableGlobalMethodSecurity(prePostEnabled = true)` NA
         * `@EnableMethodSecurity`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignCodeReviewChecklistForSecurityVersion {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: zaprojektuj CHECKLISTE code-review (powiazanie
         * Z `_16_clean_code/Lesson22`) WYKRYWAJACA przestarzale API
         * Security W PR-ach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainBackwardCompatibilityWindow {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij ROZNICE miedzy "deprecated" A
         * "usunietym" API NA przykladzie `antMatchers()` (deprecated,
         * WCIAZ dziala) vs `WebSecurityConfigurerAdapter` (usuniete,
         * NIE dziala).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AuditThisEntireProjectForDeprecatedSecurityApi {
        /*
         * 🧪 Zadanie 21:
         * Przeszukaj (opisowo) CALY projekt POD KATEM przypadkowego
         * uzycia przestarzalego API Security - potwierdz, ze GO NIE MA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignAutomatedDetectionOfLegacyApiUsage {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_16_clean_code/Lesson20_StaticAnalysisTools`
         * (PMD/SpotBugs) - zaprojektuj WLASNA regule wykrywajaca
         * przestarzale API Security.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareSpringSecurityEvolutionWithOtherFrameworks {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: porownaj TEMPO zmian API W Spring Security Z
         * INNYM znanym Ci frameworkiem/biblioteka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PredictNextDeprecationBasedOnPattern {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: NA PODSTAWIE wzorca (fluent API -> lambda DSL)
         * zastanow sie, CO MOZE byc NASTEPNE zmienione W Security 7.0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteCompatibilityShimForLegacyCode {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: opisz (koncepcyjnie), jak MOZNA by napisac
         * WLASNA metode pomocnicza TLUMACZACA stary styl konfiguracji
         * NA nowy (bez faktycznego uzywania usunietej klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignVersionAwareDocumentationTemplate {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: zaprojektuj SZABLON dokumentacji WEWNETRZNEJ
         * (jak ADR Z `_17_architecture/Lesson02`) jawnie ZAZNACZAJACY
         * wersje API W KAZDYM przykladzie kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareThreeGenerationsOfConfigSideBySide {
        /*
         * 🧪 Zadanie 27:
         * Napisz (W komentarzu, BEZ kompilowania) WSZYSTKIE 3 generacje
         * TEJ SAMEJ konfiguracji (adapter/fluent/lambda) OBOK siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainWhyLambdaDslImprovesTestability {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wyjasnij, DLACZEGO konfiguracja PRZEZ bean
         * (kompozycja) jest LATWIEJSZA DO TESTOWANIA NIZ dziedziczenie
         * PO adapterze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ResearchWhyJdkProxyVsCglibMattersHistorically {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_20_spring_core/Lesson21_SpringAopFundamentals` -
         * wyjasnij, DLACZEGO Security 6.x preferuje kompozycje
         * (interfejsy) ZAMIAST dziedziczenia PO klasach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteFullMigrationReportForFictionalLegacyApp {
        /*
         * 🧪 Zadanie 30:
         * Bez terminala: napisz PELNY raport migracji (fikcyjnej)
         * aplikacji Z Boot 2.5 (Security 5.5, `WebSecurityConfigurerAdapter`)
         * DO Boot 3.4 (Security 6.x) - WSZYSTKIE kroki I ryzyka.
         */
        public static void main(String[] args) { }
    }
}
