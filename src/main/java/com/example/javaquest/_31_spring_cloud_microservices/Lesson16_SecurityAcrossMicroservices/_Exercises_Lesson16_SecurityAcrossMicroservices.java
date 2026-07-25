package com.example.javaquest._31_spring_cloud_microservices.Lesson16_SecurityAcrossMicroservices;

public class _Exercises_Lesson16_SecurityAcrossMicroservices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyAuthenticationIsNotJustGatewayConcern {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij, DLACZEGO uwierzytelnianie NIE JEST TYLKO sprawa bramy (Gateway). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ForwardAuthorizationHeaderToDownstreamService {
        /* 🧪 Zadanie 2: Przekaz naglowek `Authorization` DO serwisu ponizej (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise03_ValidateJwtIndependentlyInTwoServices {
        /* 🧪 Zadanie 3: Zweryfikuj JWT NIEZALEZNIE W DWOCH serwisach (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReturnUnauthorizedWhenTokenMissing {
        /* 🧪 Zadanie 4: Zwroc 401, GDY token jest NIEOBECNY. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReturnUnauthorizedWhenTokenExpired {
        /* 🧪 Zadanie 5: Zwroc 401, GDY token jest WYGASLY (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhatDefenseInDepthMeans {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, CZYM jest "defense in depth" W kontekscie tej lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyDownstreamServiceShouldNotBlindlyTrustGateway {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO serwis ponizej NIE POWINIEN "na slepo ufac" bramie. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExtractUsernameFromJwtClaims {
        /* 🧪 Zadanie 8: Wyodrebnij nazwe uzytkownika Z claimow JWT. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CallDownstreamServiceDirectlyBypassingGateway {
        /* 🧪 Zadanie 9: Wywolaj serwis ponizej BEZPOSREDNIO (Z POMINIECIEM bramy) I zweryfikuj, ze WCIAZ WYMAGA tokenu. */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareTokenPropagationWithCreatingNewTokenPerHop {
        /* 🧪 Zadanie 10: Bez terminala - porownaj propagacje TEGO SAMEGO tokenu Z tworzeniem NOWEGO tokenu NA KAZDYM "skoku". */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PropagateTokenThroughThreeServiceChain {
        /* 🧪 Zadanie 11: Propaguj token PRZEZ LANCUCH 3 serwisow (A->B->C). */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddRoleBasedAuthorizationToDownstreamService {
        /* 🧪 Zadanie 12: Dodaj autoryzacje OPARTA NA rolach DO serwisu ponizej (powiazanie Z `_19_security_basics/Lesson07`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementServiceToServiceAuthenticationSeparateFromUserToken {
        /* 🧪 Zadanie 13: Zaimplementuj OSOBNE uwierzytelnianie serwis-DO-serwisu (NIE token uzytkownika, tzw. "client credentials"). */
        public static void main(String[] args) { }
    }

    static class Exercise14_LogAuthenticationFailuresWithTraceIdCorrelation {
        /* 🧪 Zadanie 14: Loguj NIEUDANE uwierzytelnienia Z korelacja `traceId` (powiazanie Z Lesson11). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementTokenRefreshBeforeExpirationInChainedCall {
        /* 🧪 Zadanie 15: Zaimplementuj odswiezenie tokenu PRZED wygasnieciem W lancuchowym wywolaniu. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareSymmetricVsAsymmetricJwtSigningForMicroservices {
        /* 🧪 Zadanie 16: Bez terminala - porownaj podpis symetryczny (HS256, jak W lekcji) Z asymetrycznym (RS256) DLA mikroserwisow. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementJwkBasedValidationInsteadOfSharedSecret {
        /* 🧪 Zadanie 17: Zaimplementuj walidacje OPARTA NA JWK (klucz publiczny) ZAMIAST WSPOLNEGO sekretu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleClockSkewBetweenServicesForTokenExpiration {
        /* 🧪 Zadanie 18: Obsluz roznice zegarow (clock skew) MIEDZY serwisami PRZY sprawdzaniu wygasniecia tokenu. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCircuitBreakerAroundDownstreamAuthCall {
        /* 🧪 Zadanie 19: Dodaj circuit breaker (Lesson10) WOKOL wywolania serwisu ponizej. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignMutualTlsAsAlternativeToJwtPropagation {
        /* 🧪 Zadanie 20: Zbadaj I zaprojektuj mTLS (mutual TLS) jako ALTERNATYWE DLA propagacji JWT. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementTokenExchangePatternForDelegatedAccess {
        /* 🧪 Zadanie 21: Zaimplementuj wzorzec "token exchange" DLA delegowanego dostepu (OAuth2 Token Exchange). */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignZeroTrustNetworkArchitectureForMicroservices {
        /* 🧪 Zadanie 22: Zaprojektuj architekture "zero trust" DLA sieci mikroserwisow. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementServiceMeshStyleSidecarAuthEnforcement {
        /* 🧪 Zadanie 23: Zaprojektuj (koncepcyjnie) wymuszanie autoryzacji NA POZIOMIE sidecar (service mesh, np. Istio). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignAuditTrailAcrossMultipleServicesForComplianceRequirements {
        /* 🧪 Zadanie 24: Zaprojektuj dziennik audytu ROZCIAGNIETY NA WIELE serwisow (powiazanie Z `_19_security_basics/Lesson19`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRateLimitingPerAuthenticatedUserAcrossServices {
        /* 🧪 Zadanie 25: Zaimplementuj rate limiting PER uzytkownik (NIE PER IP) W WIELU serwisach naraz. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignSecretRotationStrategyForSharedSigningKey {
        /* 🧪 Zadanie 26: Zaprojektuj strategie ROTACJI klucza podpisujacego (BEZ przerwy W dzialaniu WSZYSTKICH serwisow naraz). */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementClaimsBasedFieldLevelAuthorization {
        /* 🧪 Zadanie 27: Zaimplementuj autoryzacje NA POZIOMIE POLA (field-level) OPARTA NA claimach tokenu. */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareCentralizedVsDecentralizedTokenValidationArchitectures {
        /* 🧪 Zadanie 28: Bez terminala - porownaj CENTRALNA walidacje tokenu (1 serwis) Z ZDECENTRALIZOWANA (kazdy sam). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignIncidentResponsePlanForCompromisedSharedSigningKey {
        /* 🧪 Zadanie 29: Zaprojektuj plan reakcji NA incydent PRZY skompromitowanym WSPOLNYM kluczu podpisu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionCrossServiceSecurityArchitectureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" bezpieczenstwa MIEDZYSERWISOWEGO. */
        public static void main(String[] args) { }
    }
}
