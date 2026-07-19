package com.example.javaquest._27_spring_test.Lesson13_TestingSecuredEndpoints;

public class _Exercises_Lesson13_TestingSecuredEndpoints {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TestPublicEndpointWithoutAuthentication {
        /* 🧪 Zadanie 1: Przetestuj PUBLICZNY endpoint BEZ `@WithMockUser`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_TestSecuredEndpointReturns401WithoutAuth {
        /* 🧪 Zadanie 2: Przetestuj zabezpieczony endpoint BEZ uwierzytelnienia (401). */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestSecuredEndpointWithWithMockUser {
        /* 🧪 Zadanie 3: Przetestuj zabezpieczony endpoint Z `@WithMockUser`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestForbiddenWithWrongRole {
        /* 🧪 Zadanie 4: Przetestuj 403 PRZY ZLEJ roli. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWithMockUserDefaultValues {
        /* 🧪 Zadanie 5: Bez terminala - wymien DOMYSLNE wartosci `@WithMockUser` (username/role). */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseWithAnonymousUserExplicitly {
        /* 🧪 Zadanie 6: Uzyj `@WithAnonymousUser` JAWNIE. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestMultipleRolesWithWithMockUser {
        /* 🧪 Zadanie 7: Uzyj `@WithMockUser(roles = {"USER", "ADMIN"})` (WIELE rol). */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareWithMockUserWithRealLoginFromChapter24 {
        /* 🧪 Zadanie 8: Bez terminala - porownaj `@WithMockUser` Z PRAWDZIWYM logowaniem Z `_24_spring_security/Lesson17`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestMethodSecurityWithPreAuthorize {
        /* 🧪 Zadanie 9: Powiaz z `_20_spring_core/Lesson17`+`_24_spring_security/Lesson10` - przetestuj `@PreAuthorize` Z SpEL. */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentWhySpringAutoconfigureExcludeNeedsOverride {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO trzeba nadpisac `spring.autoconfigure.exclude` W tej lekcji. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestWithMockUserCustomAuthorities {
        /* 🧪 Zadanie 11: Uzyj `@WithMockUser(authorities = "SPECIFIC_PERMISSION")` (NIE role). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomWithUserDetailsAnnotation {
        /* 🧪 Zadanie 12: Zbadaj `@WithUserDetails(...)` (LADUJE PRAWDZIWEGO uzytkownika Z `UserDetailsService`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestCsrfProtectionInMockMvc {
        /* 🧪 Zadanie 13: Powiaz z `_24_spring_security/Lesson14` - przetestuj CSRF W MockMvc (`.with(csrf())`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestJwtBasedAuthenticationWithMockMvc {
        /* 🧪 Zadanie 14: Powiaz z `_24_spring_security/Lesson12` - przetestuj JWT PRZEZ WLASNY naglowek Authorization. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCustomSecurityMockMvcRequestPostProcessor {
        /* 🧪 Zadanie 15: Zbuduj WLASNY `RequestPostProcessor` symulujacy uwierzytelnienie. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestCorsPreflightOnSecuredEndpoint {
        /* 🧪 Zadanie 16: Powiaz z `_24_spring_security/Lesson14` - przetestuj CORS preflight NA zabezpieczonym endpoincie. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CombineWithMockUserWithMockitoBean {
        /* 🧪 Zadanie 17: Powiaz z Lesson08 - polacz `@WithMockUser` Z `@MockitoBean` serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestSecurityExceptionHandlerCustomization {
        /* 🧪 Zadanie 18: Powiaz z `_24_spring_security/Lesson16` - przetestuj WLASNY `AuthenticationEntryPoint`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestMultipleUsersWithDifferentPermissionsInOneTestClass {
        /* 🧪 Zadanie 19: Napisz 4+ testow Z ROZNYMI uzytkownikami/rolami W JEDNEJ klasie. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullSecurityTestSuiteForRestApi {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet testow bezpieczenstwa DLA API (WSZYSTKIE role/sciezki). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomSecurityContextFactory {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNA `WithSecurityContextFactory` DLA WLASNEJ adnotacji `@WithCustomUser`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestOAuth2ResourceServerWithMockJwt {
        /* 🧪 Zadanie 22: Powiaz z `_24_spring_security/Lesson15` - przetestuj OAuth2 Resource Server `.with(jwt())`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestRateLimitingCombinedWithSecurity {
        /* 🧪 Zadanie 23: Powiaz z `_18_rest_api/Lesson16` - przetestuj rate limiting NA zabezpieczonym endpoincie. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSecurityRegressionTestSuiteAcrossAllEndpoints {
        /* 🧪 Zadanie 24: Zbuduj (PRZEZ refleksje) test SKANUJACY WSZYSTKIE endpointy POD KATEM brakujacej ochrony. */
        public static void main(String[] args) { }
    }

    static class Exercise25_TestSecurityHeadersPresenceOnAllResponses {
        /* 🧪 Zadanie 25: Powiaz z `_19_security_basics/Lesson12` - zweryfikuj naglowki bezpieczenstwa NA WSZYSTKICH odpowiedziach. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementPenetrationTestStyleAuthorizationBypassAttempts {
        /* 🧪 Zadanie 26: Zaprojektuj testy PROBUJACE ominac autoryzacje (manipulacja rolami/tokenem). */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestSecurityConfigurationAcrossMultipleFilterChains {
        /* 🧪 Zadanie 27: Powiaz z `_24_spring_security/Lesson02` - przetestuj WIELE `SecurityFilterChain` NARAZ. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildSecurityTestFixtureForCommonUserScenarios {
        /* 🧪 Zadanie 28: Zbuduj WSPOLDZIELONA "fixture" typowych scenariuszy uzytkownikow DLA CALEGO API. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementAuditLogVerificationForSecurityEvents {
        /* 🧪 Zadanie 29: Powiaz z `_19_security_basics/Lesson19` - zweryfikuj wpisy audytu PRZY probach nieautoryzowanego dostepu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullSecurityTestingStandardForOrganization {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania bezpieczenstwa DLA organizacji. */
        public static void main(String[] args) { }
    }
}
