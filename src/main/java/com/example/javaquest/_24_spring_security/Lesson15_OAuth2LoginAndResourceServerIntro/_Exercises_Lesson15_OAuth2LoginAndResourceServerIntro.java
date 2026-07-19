package com.example.javaquest._24_spring_security.Lesson15_OAuth2LoginAndResourceServerIntro;

public class _Exercises_Lesson15_OAuth2LoginAndResourceServerIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainResourceServerVsClientDifference {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij ROZNICE miedzy "OAuth2 Client"
         * (logowanie PRZEZ Google) A "OAuth2 Resource Server"
         * (walidacja tokenow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CompareWithLesson12CustomFilter {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: porownaj ILOSC kodu W Lesson12 (WLASNY filtr)
         * Z TA lekcja (natywny `.oauth2ResourceServer(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddThirdRoleToken {
        /*
         * 🧪 Zadanie 3:
         * Wygeneruj token Z rola "MODERATOR" I dodaj endpoint
         * `hasRole("MODERATOR")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestTokenWithoutRoleClaim {
        /*
         * 🧪 Zadanie 4:
         * Wygeneruj token BEZ claim "role" - zweryfikuj zachowanie NA
         * `/api/secure` (BEZ WYMOGU roli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyDefaultConverterExpectsScope {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO DOMYSLNY
         * `JwtGrantedAuthoritiesConverter` czyta claim "scope"/"scp"
         * (standard OAuth2), NIE "role".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestExpiredTokenWithResourceServer {
        /*
         * 🧪 Zadanie 6:
         * Wygeneruj WYGASLY token I zweryfikuj status 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareEntryPointBehaviorWithLesson12 {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, DLACZEGO TU dostajesz 401 (BEZ
         * tokenu), a W Lesson12 dostawales 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainAuthorizationCodeFlowSteps {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz WSZYSTKIE 6 krokow authorization code
         * flow Z komentarza W tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithChapter19ManualOAuth2Flow {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: powiaz z `_19_security_basics/Lesson06` -
         * porownaj RECZNY authorization server Z TYM, CO Spring ROBI
         * automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRequiredYamlPropertiesForGoogleLogin {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz WSZYSTKIE wlasciwosci `application.yml`
         * potrzebne DO logowania PRZEZ Google.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomJwtAuthenticationConverter {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNY `Converter<Jwt, AbstractAuthenticationToken>`
         * (ZAMIAST `JwtGrantedAuthoritiesConverter`) Z DODATKOWA logika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestMultipleRolesInSingleToken {
        /*
         * 🧪 Zadanie 12:
         * Zmien claim "role" NA liste rol I dostosuj konwerter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareNimbusWithJjwtParsing {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: porownaj `NimbusJwtDecoder` (TU) Z
         * `Jwts.parser()` (Lesson12) - OBA weryfikuja TEN SAM token,
         * INNYMI bibliotekami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementJwkSetUriBasedDecoder {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: opisz, jak wygladalby `NimbusJwtDecoder.
         * withJwkSetUri(...)` W PRAWDZIWEJ integracji Z zewnetrznym
         * authorization serverem (np. Keycloak/Auth0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestResourceServerWithRsaSignedTokens {
        /*
         * 🧪 Zadanie 15:
         * Zamien HMAC NA RSA (powiazanie Z Lesson12 Zadanie 21) -
         * uzyj `NimbusJwtDecoder.withPublicKey(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementScopeBasedAuthorizationAlongsideRole {
        /*
         * 🧪 Zadanie 16:
         * Dodaj DRUGI claim "scope" OBOK "role" I skonfiguruj OBA W
         * autoryzacji (`hasAuthority("SCOPE_read")`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignOidcUserInfoMapping {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: opisz, jak `OidcUser`/`OAuth2User` MAPUJE
         * dane Z Google (imie/email/zdjecie) NA WLASNY model
         * uzytkownika aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCustomOAuth2UserService {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz, PO CO implementuje sie WLASNY
         * `OAuth2UserService` (np. AUTOMATYCZNE tworzenie konta W
         * WLASNEJ bazie PRZY PIERWSZYM logowaniu PRZEZ Google).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareGoogleFacebookGithubProviders {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj konfiguracje DLA 3 dostawcow
         * (Google/GitHub/Facebook) - CO SIE ROZNI, CO jest WSPOLNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestResourceServerPerformanceVsCustomFilter {
        /*
         * 🧪 Zadanie 20:
         * Porownaj WYDAJNOSC natywnego Resource Servera Z WLASNYM
         * filtrem Z Lesson12 (1000 zadan KAZDY).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementOpaqueTokenIntrospection {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala: opisz ALTERNATYWE DLA JWT - token
         * nieprzezroczysty (opaque) Z introspekcja (`.opaqueToken(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiProviderResourceServer {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj Resource Server AKCEPTUJACY tokeny OD WIELU
         * roznych wystawcow (multi-issuer).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomBearerTokenResolver {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNY `BearerTokenResolver` (np. token Z
         * parametru zapytania ZAMIAST naglowka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignTokenExchangeForMicroservices {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: powiaz z `_17_architecture/Lesson19` -
         * zaprojektuj "token exchange" (wymiana tokenu uzytkownika NA
         * token miedzyserwisowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementPkceFlowExplanation {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: wyjasnij PKCE (Proof Key for Code Exchange) -
         * DLACZEGO jest WYMAGANY DLA aplikacji mobilnych/SPA (public
         * client, BEZ client_secret).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareOidcWithPlainOAuth2 {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: powiaz z `_19_security_basics/Lesson06` -
         * przypomnij ROZNICE OpenID Connect (tozsamosc, `id_token`) OD
         * czystego OAuth2 (autoryzacja, `access_token`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignTokenCachingForResourceServer {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_13_libraries/Lesson27` - zaprojektuj cache DLA
         * `NimbusJwtDecoder` PRZY uzyciu `withJwkSetUri(...)` (unikanie
         * powtarzalnego pobierania kluczy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAudienceValidatorForResourceServer {
        /*
         * 🧪 Zadanie 28:
         * Dodaj WLASNY `OAuth2TokenValidator<Jwt>` sprawdzajacy claim
         * "aud" (audience) - odrzuc tokeny NIE PRZEZNACZONE DLA TEGO
         * API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignSecurityForBothOAuth2LoginAndJwtApi {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj aplikacje LACZACA OAuth2 Login (panel webowy,
         * sesje) I OAuth2 Resource Server (API, bezstanowe) W 2
         * NIEZALEZNYCH `SecurityFilterChain`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullOAuth2ArchitectureDiagramAndJustification {
        /*
         * 🧪 Zadanie 30:
         * Bez terminala: narysuj (ASCII) PELNA architekture: SPA ->
         * OAuth2 Login (Google) -> WLASNE API (Resource Server) ->
         * mikroserwisy (token exchange) - Z UZASADNIENIEM KAZDEGO
         * elementu.
         */
        public static void main(String[] args) { }
    }
}
