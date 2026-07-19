package com.example.javaquest._24_spring_security.Lesson02_SecurityFilterChain;

public class _Exercises_Lesson02_SecurityFilterChain {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSecurityFilterChainPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, DO CZEGO sluzy bean `SecurityFilterChain`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdPublicEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Dodaj TRZECI endpoint `/public/status` I zweryfikuj, ze TEZ
         * jest publiczny (juz dopasowany przez `/public/**`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddSecondPrivateEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Dodaj DRUGI prywatny endpoint I zweryfikuj, ze WYMAGA
         * logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ChangePermitAllPatternToSpecificPath {
        /*
         * 🧪 Zadanie 4:
         * Zmien `/public/**` NA KONKRETNA sciezke (`/public/info`
         * DOKLADNIE) I zweryfikuj, ze `/public/status` PRZESTAJE byc
         * publiczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainOrderOfRulesMatters {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO kolejnosc regul W
         * `authorizeHttpRequests` MA znaczenie (pierwsza dopasowana
         * WYGRYWA).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddDenyAllRuleForSpecificPath {
        /*
         * 🧪 Zadanie 6:
         * Dodaj regule `denyAll()` DLA konkretnej sciezki - zweryfikuj
         * 403 NAWET Z poprawnymi poswiadczeniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RemoveHttpBasicAndObserveBehavior {
        /*
         * 🧪 Zadanie 7:
         * USUN `.httpBasic(...)` I zaobserwuj, JAK zmienia sie
         * zachowanie DLA prywatnego endpointu (formularz logowania
         * ZAMIAST Basic Auth - zapowiedz Lesson04/08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestPermitAllWithTrailingSlash {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, czy `/public/` (Z UKOSNIKIEM NA KONCU) TEZ jest
         * dopasowane PRZEZ `/public/**`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithLesson01DefaultBehavior {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj TA lekcje Z Lesson01 - CO SIE
         * ZMIENILO poprzez dodanie WLASNEGO `SecurityFilterChain`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TestUnmatchedPathDefaultsToAuthenticated {
        /*
         * 🧪 Zadanie 10:
         * Dodaj endpoint POZA `/public/**` I `/private/**` (np. `/inny`)
         * I zweryfikuj, ze TRAFIA W `.anyRequest().authenticated()`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChainMultiplePermitAllPatterns {
        /*
         * 🧪 Zadanie 11:
         * Dodaj WIELE wzorcow `permitAll()` (np. `/public/**`,
         * `/health`, `/status`) W JEDNYM `authorizeHttpRequests`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RestrictByHttpMethod {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `requestMatchers(HttpMethod.GET, "/public/**")` DO
         * ograniczenia regul TYLKO DO metody GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddTwoSecurityFilterChainsWithOrder {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: opisz, jak DODAC 2 NIEZALEZNE
         * `SecurityFilterChain` (rozne `@Order`) DLA roznych czesci API
         * (np. `/api/**` I `/admin/**`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DisableCsrfExplicitly {
        /*
         * 🧪 Zadanie 14:
         * Dodaj `.csrf(csrf -> csrf.disable())` I wyjasnij, KIEDY jest
         * to uzasadnione (zapowiedz Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseAnonymousAccessExplicitly {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `.anonymous(Customizer.withDefaults())` I sprawdz, JAK
         * niezalogowany uzytkownik jest reprezentowany W kontekscie
         * bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestNonExistentEndpointWithAuth {
        /*
         * 🧪 Zadanie 16:
         * Wyslij zadanie DO NIEISTNIEJACEGO endpointu Z poprawnymi
         * poswiadczeniami - zweryfikuj KOLEJNOSC (Security NAJPIERW,
         * 404 DOPIERO POTEM).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainRequestMatchersVsAntMatchers {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, DLACZEGO `requestMatchers(...)`
         * jest UZYWANE W TYM kursie ZAMIAST starszego `antMatchers(...)`
         * (zapowiedz Lesson03).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddCustomAuthenticationEntryPoint {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: zapowiedz Lesson16 - opisz, GDZIE W
         * `SecurityFilterChain` MOZNA podpiac WLASNY
         * `AuthenticationEntryPoint`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestWithQueryParametersOnPublicPath {
        /*
         * 🧪 Zadanie 19:
         * Zweryfikuj, czy `/public/info?debug=true` (Z parametrem
         * zapytania) DALEJ jest dopasowane PRZEZ `/public/**`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareHttpSecurityDslWithRawServletFilterConfig {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: powiaz z `_07_servlets/Lesson14` - porownaj
         * deklaratywny `HttpSecurity` DSL Z RECZNA konfiguracja
         * `FilterRegistrationBean`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignMultiChainArchitectureForMonolith {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_17_architecture/Lesson17_ModularMonolith` -
         * zaprojektuj WIELE `SecurityFilterChain` DLA roznych "modulow"
         * (np. `/api/public/**` bezstanowe, `/admin/**` Z sesja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomAccessDecisionLogic {
        /*
         * 🧪 Zadanie 22:
         * Uzyj `.access(AuthorizationManager)` (zamiast `hasRole(...)`)
         * DO wlasnej, programowej logiki decyzyjnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestSecurityFilterChainWithConcurrentRequests {
        /*
         * 🧪 Zadanie 23:
         * Wyslij 20 ROWNOLEGLYCH zadan (mix publicznych/prywatnych) -
         * zweryfikuj POPRAWNOSC WSZYSTKICH odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExplainSecurityFilterChainBeanOrderingWithMultipleChains {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: wyjasnij, jak Spring Security DECYDUJE, KTORY
         * `SecurityFilterChain` obsluzy DANE zadanie, gdy jest ICH
         * WIELE (`securityMatcher(...)` + `@Order`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasurePerformanceOfRequestMatcherEvaluation {
        /*
         * 🧪 Zadanie 25:
         * Zmierz narzut ewaluacji `requestMatchers(...)` PRZY DUZEJ
         * liczbie regul (np. 50 wzorcow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IntegrateWithCustomFilterBeforeSecurityFilter {
        /*
         * 🧪 Zadanie 26:
         * Dodaj WLASNY `Filter` PRZED filtrami Security
         * (`.addFilterBefore(...)`) - np. logowanie KAZDEGO zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignSecurityConfigForMultiTenantApi {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zaprojektuj `SecurityFilterChain` DLA API
         * wielotenantowego (roznicowanie regul PO prefiksie
         * `/tenant/{id}/**`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareLambdaDslWithDeprecatedFluentApi {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: powiaz z Lesson03 - porownaj OBECNY lambda DSL
         * Z USUNIETYM stylem fluent (`.authorizeRequests().antMatchers(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestSecurityFilterChainStartupFailureScenarios {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj BLEDNA konfiguracje (2 sprzeczne `SecurityFilterChain`
         * BEZ `@Order`/`securityMatcher`) - zaobserwuj blad startu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildProductionLikeFilterChainWithMultipleConcerns {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj REALISTYCZNY `SecurityFilterChain` LACZACY: publiczne
         * sciezki, chronione API, WLASNY filtr logujacy I obsluge
         * bledow 401/403 (podsumowanie Lesson02).
         */
        public static void main(String[] args) { }
    }
}
