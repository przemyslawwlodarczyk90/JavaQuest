package com.example.javaquest._24_spring_security.Lesson14_CorsAndCsrfInSpringSecurity;

public class _Exercises_Lesson14_CorsAndCsrfInSpringSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCorsConfigurationSourceRole {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij role `CorsConfigurationSource` W
         * `.cors(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddSecondAllowedOrigin {
        /*
         * 🧪 Zadanie 2:
         * Dodaj DRUGA dozwolona domene DO `setAllowedOrigins(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestRequestWithoutOriginHeader {
        /*
         * 🧪 Zadanie 3:
         * Wyslij zadanie BEZ naglowka `Origin` W OGOLE - sprawdz
         * zachowanie (CORS dotyczy TYLKO zadan cross-origin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddAllowedMethodsForPost {
        /*
         * 🧪 Zadanie 4:
         * Dodaj "POST" DO `setAllowedMethods(...)` I dodaj endpoint
         * POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareWithChapter19ManualCors {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: powiaz z `_19_security_basics/Lesson09` -
         * porownaj RECZNA implementacje CORS Z `.cors(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestPreflightOptionsRequest {
        /*
         * 🧪 Zadanie 6:
         * Wyslij REALNE zadanie `OPTIONS` (preflight) I zbadaj
         * odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyCsrfEnabledByDefault {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, DLACZEGO CSRF jest WLACZONY
         * DOMYSLNIE W Spring Security.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestCsrfHeaderNameValue {
        /*
         * 🧪 Zadanie 8:
         * Wypisz DOKLADNA wartosc `headerName` zwracana PRZEZ
         * `/api/csrf-token`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareFormBasedVsHeaderBasedCsrf {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj token CSRF W ukrytym POLU formularza
         * (Lesson04/08/11) Z tokenem W naglowku (tu) - KIEDY ktore
         * podejscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TestActionEndpointWithWrongToken {
        /*
         * 🧪 Zadanie 10:
         * Wyslij ZLY (LOSOWY) token W naglowku CSRF - zweryfikuj 403.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigureAllowedHeadersExplicitly {
        /*
         * 🧪 Zadanie 11:
         * Dodaj `setAllowedHeaders(...)` DO konfiguracji CORS (np.
         * `Authorization`, `Content-Type`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureAllowCredentialsForCors {
        /*
         * 🧪 Zadanie 12:
         * Ustaw `setAllowCredentials(true)` I wyjasnij, KIEDY jest to
         * WYMAGANE (ciasteczka W zadaniach cross-origin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestMaxAgeCachingOfPreflightResponse {
        /*
         * 🧪 Zadanie 13:
         * Ustaw `setMaxAge(...)` I sprawdz naglowek
         * `Access-Control-Max-Age` W odpowiedzi preflight.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementPerPathCorsConfiguration {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj ROZNE konfiguracje CORS DLA `/api/public/**` I
         * `/api/admin/**` (2 wpisy W `UrlBasedCorsConfigurationSource`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CombineCorsWithAuthentication {
        /*
         * 🧪 Zadanie 15:
         * Polacz CORS Z chronionym (`authenticated()`) endpointem -
         * zweryfikuj, ze CORS dziala PRZED sprawdzeniem uwierzytelnienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainCsrfCookieRepositoryForSpa {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: opisz `CookieCsrfTokenRepository` jako
         * ALTERNATYWE DLA tokenu W odpowiedzi JSON (typowy wzorzec
         * "double submit cookie" DLA SPA).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCookieCsrfTokenRepository {
        /*
         * 🧪 Zadanie 17:
         * Skonfiguruj `CookieCsrfTokenRepository.withHttpOnlyFalse()` I
         * zweryfikuj, ze token trafia DO ciasteczka (odczytywalnego
         * PRZEZ JavaScript).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestCsrfWithMultipleSequentialActions {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj 3 kolejne akcje `POST` Z TYM SAMYM tokenem - zweryfikuj,
         * ze token NIE JEST jednorazowy (DOMYSLNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareCorsWithCsrfPurposes {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij ROZNICE miedzy CORS (KTO MOZE
         * odczytac odpowiedz) A CSRF (KTO MOZE WYWOLAC akcje) - CZESTO
         * MYLONE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignCorsPolicyForMultiEnvironmentApp {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj RUCHOMA (@Value) liste dozwolonych originow
         * (dev/staging/prod ROZNE domeny).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDynamicCorsOriginValidation {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `CorsConfigurationSource` Z DYNAMICZNA
         * walidacja originow (np. PODDOMENY WEDLUG wzorca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignCsrfStrategyForMicroservices {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: powiaz z `_17_architecture/Lesson19` -
         * zaprojektuj strategie CSRF W architekturze mikroserwisow (API
         * Gateway + backend serwisy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomCsrfTokenRequestHandler {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNY `CsrfTokenRequestAttributeHandler` Z
         * DODATKOWA logika (np. logowanie KAZDEJ probie walidacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkCorsPreflightOverhead {
        /*
         * 🧪 Zadanie 24:
         * Zmierz DODATKOWY narzut zadania OPTIONS (preflight) PRZED
         * KAZDYM "prawdziwym" zadaniem cross-origin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignSecureCorsForPublicApi {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaprojektuj polityke CORS DLA PUBLICZNEGO API
         * (dowolna domena MOZE czytac, ale NIE modyfikowac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCsrfExemptionForWebhooks {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj WYJATEK OD CSRF DLA endpointow webhook (zewnetrzne
         * systemy NIE MAJA sesji/tokenu) - `requireCsrfProtectionMatcher(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestCorsWithWildcardOriginAndCredentials {
        /*
         * 🧪 Zadanie 27:
         * SPROBUJ POLACZYC `allowedOrigins("*")` Z
         * `allowCredentials(true)` - zaobserwuj BLAD konfiguracji
         * (specyfikacja CORS TEGO ZABRANIA).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditCsrfAndCorsViolationsToSecurityLog {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDE
         * odrzucenie CORS/CSRF Z pelnym kontekstem (Origin, IP, sciezka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareCorsCsrfWithModernSameSiteCookies {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: powiaz z `_19_security_basics/Lesson04` -
         * porownaj CSRF (token) Z atrybutem ciasteczka `SameSite=Strict`
         * jako WSPOLCZESNA, DODATKOWA linia obrony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullCrossOriginSpaBackendDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNE demo: "frontend" (INNY port/origin, symulowany)
         * wywolujacy "backend" PRZEZ CORS, Z poprawna obsluga CSRF DLA
         * WSZYSTKICH operacji zapisu.
         */
        public static void main(String[] args) { }
    }
}
