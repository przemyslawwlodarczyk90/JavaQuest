package com.example.javaquest._29_spring_reactive.Lesson14_ReactiveSecurity;

public class _Exercises_Lesson14_ReactiveSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ConfigureSecurityWebFilterChainWithPermitAllForOneEndpoint {
        /* 🧪 Zadanie 1: Skonfiguruj `SecurityWebFilterChain` Z `permitAll()` DLA 1 endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ProtectEndpointRequiringAuthenticationUsingAnyExchangeAuthenticated {
        /* 🧪 Zadanie 2: Zabezpiecz endpoint WYMAGAJACY uwierzytelnienia uzywajac `anyExchange().authenticated()`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateMapReactiveUserDetailsServiceWithSingleUser {
        /* 🧪 Zadanie 3: Stworz `MapReactiveUserDetailsService` Z 1 uzytkownikiem. */
        public static void main(String[] args) { }
    }

    static class Exercise04_SendGetRequestWithBasicAuthHeaderToProtectedEndpoint {
        /* 🧪 Zadanie 4: Wyslij zadanie GET Z naglowkiem Basic Auth DO chronionego endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ObserveUnauthorizedResponseWithoutCredentials {
        /* 🧪 Zadanie 5: Zaobserwuj odpowiedz 401 BEZ poswiadczen. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareEnableWebFluxSecurityWithEnableWebSecurity {
        /* 🧪 Zadanie 6: Powiaz z `_24_spring_security` - porownaj `@EnableWebFluxSecurity` Z `@EnableWebSecurity`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyPasswordEncoderIsIdenticalInBothStacks {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO `PasswordEncoder` jest IDENTYCZNY W OBU stosach (servlet/reactive). */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddRoleBasedAuthorizationUsingHasRoleForSpecificPath {
        /* 🧪 Zadanie 8: Dodaj autoryzacje OPARTA NA roli uzywajac `hasRole(...)` DLA KONKRETNEJ sciezki. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConfigureMultipleUsersWithDifferentRoles {
        /* 🧪 Zadanie 9: Skonfiguruj WIELU uzytkownikow Z ROZNYMI rolami. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDifferenceBetweenPathMatchersAndRequestMatchers {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij ROZNICE MIEDZY `pathMatchers` (reactive) A `requestMatchers` (servlet). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomAuthenticationEntryPointForReactiveSecurity {
        /* 🧪 Zadanie 11: Zaimplementuj WLASNY `ServerAuthenticationEntryPoint` DLA reaktywnego Security. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMethodSecurityUsingPreAuthorizeInReactiveController {
        /* 🧪 Zadanie 12: Zaimplementuj `@PreAuthorize` W REAKTYWNYM kontrolerze (`@EnableReactiveMethodSecurity`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureCorsForReactiveSecurityWebFilterChain {
        /* 🧪 Zadanie 13: Powiaz z `_24_spring_security/Lesson14` - skonfiguruj CORS DLA `SecurityWebFilterChain`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementJwtAuthenticationForReactiveEndpointsUsingReactiveJwtDecoder {
        /* 🧪 Zadanie 14: Powiaz z `_24_spring_security/Lesson15` - zaimplementuj uwierzytelnienie JWT DLA REAKTYWNYCH endpointow uzywajac `ReactiveJwtDecoder`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConfigureStatelessSecurityForReactiveApiUsingNoSessionManagement {
        /* 🧪 Zadanie 15: Skonfiguruj bezstanowe bezpieczenstwo DLA REAKTYWNEGO API. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomReactiveAuthenticationManager {
        /* 🧪 Zadanie 16: Zaimplementuj WLASNY `ReactiveAuthenticationManager`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestSecuredReactiveEndpointUsingWebTestClientWithMockUser {
        /* 🧪 Zadanie 17: Powiaz z `_27_spring_test/Lesson13` - przetestuj zabezpieczony REAKTYWNY endpoint uzywajac `WebTestClient` + `@WithMockUser`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCustomAccessDeniedHandlerForReactiveSecurity {
        /* 🧪 Zadanie 18: Zaimplementuj WLASNY `ServerAccessDeniedHandler`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureFormLoginForReactiveWebApplication {
        /* 🧪 Zadanie 19: Skonfiguruj logowanie formularzowe (`formLogin()`) DLA REAKTYWNEJ aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareSecurityConfigurationLinesOfCodeBetweenServletAndReactiveStacks {
        /* 🧪 Zadanie 20: Powiaz z `_24_spring_security` - porownaj ILOSC linii konfiguracji Security MIEDZY stosem servlet A reactive. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOAuth2ResourceServerConfigurationForReactiveApi {
        /* 🧪 Zadanie 21: Zbuduj PELNA konfiguracje OAuth2 Resource Server DLA REAKTYWNEGO API. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementReactiveRateLimitingIntegratedWithSecurityWebFilterChain {
        /* 🧪 Zadanie 22: Zaimplementuj REAKTYWNE ograniczanie tempa ZINTEGROWANE Z `SecurityWebFilterChain`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildMultiTenantReactiveSecurityConfigurationWithDynamicUserDetailsService {
        /* 🧪 Zadanie 23: Zbuduj WIELODZIERZAWCZA konfiguracje Security Z DYNAMICZNYM `ReactiveUserDetailsService`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAuditLoggingForAuthenticationEventsInReactiveStack {
        /* 🧪 Zadanie 24: Powiaz z `_19_security_basics/Lesson19` - zaimplementuj audyt zdarzen uwierzytelnienia W stosie reaktywnym. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildCustomReactiveSecurityContextRepositoryForStatelessTokenBasedAuth {
        /* 🧪 Zadanie 25: Zbuduj WLASNE `ServerSecurityContextRepository` DLA bezstanowego uwierzytelnienia OPARTEGO NA tokenie. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementReactiveSecurityIntegrationTestSuiteCoveringAllAuthorizationRules {
        /* 🧪 Zadanie 26: Zbuduj PAKIET testow integracyjnych POKRYWAJACY WSZYSTKIE reguly autoryzacji. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignSecurityArchitectureForReactiveMicroservicesPropagatingIdentityAcrossServices {
        /* 🧪 Zadanie 27: Powiaz z `_31_spring_cloud_microservices/Lesson16` - zaprojektuj architekture bezpieczenstwa PROPAGUJACA TOZSAMOSC MIEDZY reaktywnymi mikroserwisami. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFineGrainedAuthorizationUsingCustomReactiveAuthorizationManager {
        /* 🧪 Zadanie 28: Zaimplementuj SZCZEGOLOWA autoryzacje uzywajac WLASNEGO `ReactiveAuthorizationManager`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullSecuredReactiveApiCombiningJwtRolesAndRateLimiting {
        /* 🧪 Zadanie 29: Zbuduj PELNE, zabezpieczone REAKTYWNE API LACZACE JWT+role+rate limiting. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMigrationGuideFromServletSecurityToReactiveSecurityForLargeApplication {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY przewodnik migracji Z servletowego Security NA reaktywne DLA DUZEJ aplikacji. */
        public static void main(String[] args) { }
    }
}
