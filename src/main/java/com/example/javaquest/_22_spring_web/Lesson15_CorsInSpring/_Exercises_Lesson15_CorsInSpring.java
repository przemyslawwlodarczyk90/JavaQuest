package com.example.javaquest._22_spring_web.Lesson15_CorsInSpring;

public class _Exercises_Lesson15_CorsInSpring {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCorsPurposeInSpringContext {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_19_security_basics/Lesson09`):
         * wyjasnij, JAK Spring MVC AUTOMATYZUJE to, co TAM robiono
         * recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnCrossOriginAnnotation {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint Z `@CrossOrigin(origins = "...")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnGlobalCorsConfiguration {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNA GLOBALNA konfiguracje CORS przez
         * `WebMvcConfigurer`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestAllowedOriginSucceeds {
        /*
         * 🧪 Zadanie 4:
         * Wyslij zadanie Z DOZWOLONEGO originu - zweryfikuj naglowek
         * `Access-Control-Allow-Origin`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestDisallowedOriginMissingHeader {
        /*
         * 🧪 Zadanie 5:
         * Wyslij zadanie Z NIEDOZWOLONEGO originu - zweryfikuj BRAK
         * naglowka `Access-Control-Allow-Origin`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementPreflightOptionsRequest {
        /*
         * 🧪 Zadanie 6:
         * Wyslij PRAWDZIWE zadanie `OPTIONS` (preflight) - zapisz
         * WSZYSTKIE naglowki `Access-Control-*` W odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementAllowedMethodsRestriction {
        /*
         * 🧪 Zadanie 7:
         * Ogranicz `allowedMethods` TYLKO DO `GET` - zweryfikuj
         * preflight DLA `DELETE`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementAllowCredentialsConfiguration {
        /*
         * 🧪 Zadanie 8:
         * Wlacz `allowCredentials(true)` - wyjasnij, DLACZEGO WYMAGA
         * KONKRETNEGO originu (NIE `*`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWildcardOriginWithSpecificOrigin {
        /*
         * 🧪 Zadanie 9:
         * Porownaj `allowedOrigins("*")` Z KONKRETNA lista originow -
         * jakie SA RYZYKA `*`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCorsIsBrowserOnlyMechanism {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_19_security_basics/Lesson09`):
         * wyjasnij, dlaczego CORS to mechanizm PRZEGLADARKI, NIE
         * "prawdziwe" zabezpieczenie serwera.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementDifferentCorsPerPathPattern {
        /*
         * 🧪 Zadanie 11:
         * Skonfiguruj RÓZNE reguly CORS DLA RÓZNYCH wzorcow sciezek
         * (`/api/public/**` vs `/api/admin/**`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementExposedHeadersConfiguration {
        /*
         * 🧪 Zadanie 12:
         * Skonfiguruj `exposedHeaders(...)` - udostepnij WLASNY
         * naglowek (np. `X-Total-Count`) JS PO STRONIE klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCorsConfigurationSourceBean {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNY bean `CorsConfigurationSource`
         * (ZAMIAST `WebMvcConfigurer`) - powiaz Z przyszlym
         * `_24_spring_security`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementMaxAgeCachingOfPreflight {
        /*
         * 🧪 Zadanie 14:
         * Ustaw `maxAge(...)` - wyjasnij, JAK WPLYWA NA
         * CZESTOTLIWOSC zadan `OPTIONS`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCorsForMultipleEnvironments {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_20_spring_core/Lesson15_Profiles` - skonfiguruj
         * RÓZNE dozwolone originy DLA `dev`/`prod` (WLASNOSCI PER
         * profil).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCorsWithCustomOriginPatterns {
        /*
         * 🧪 Zadanie 16:
         * Uzyj `allowedOriginPatterns("https://*.example.com")` -
         * zweryfikuj dopasowanie SUBDOMEN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCorsErrorDiagnostics {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj LOGOWANIE odrzuconych originow (do
         * DIAGNOSTYKI, NIE blokowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareCorsFilterWithWebMvcConfigurer {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): porownaj `CorsFilter`
         * (Servlet-owy) Z `WebMvcConfigurer.addCorsMappings` - KIEDY
         * ktorego uzyc?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasurePreflightOverheadOnRequestLatency {
        /*
         * 🧪 Zadanie 19:
         * Zmierz DODATKOWY czas ZADANIA Z preflightem (2 zadania)
         * wzgledem BEZ preflightu (1 zadanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildCorsConfigurationCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" konfiguracji CORS
         * (origins/methods/headers/credentials/maxAge).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDynamicOriginValidationFromDatabase {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_10_dao` - zaimplementuj DYNAMICZNA liste
         * dozwolonych originow (wczytywana Z "bazy", NIE NA sztywno W
         * kodzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCorsSecurityAuditLogging {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj KAZDA
         * probe zadania Z NIEDOZWOLONEGO originu DO dziennika audytu
         * bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCorsForWebSocketHandshake {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala (dokumentacja): sprawdz, CZY I JAK CORS
         * DOTYCZY polaczen WebSocket - CZYM RÓZNI SIE OD zwyklego
         * HTTP?
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementStrictCspAlongsideCors {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson12_SecurityHeaders` -
         * polacz KONFIGURACJE CORS Z restrykcyjnym `Content-Security-Policy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCorsTestSuiteForCiPipeline {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj (opisowo) zestaw testow CORS DO uruchamiania W
         * PIPELINE CI (dozwolone/niedozwolone originy/metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCorsForMultiTenantApplication {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_17_architecture/Lesson06_BoundedContexts` -
         * zaimplementuj RÓZNE dozwolone originy DLA RÓZNYCH "tenantow"
         * (KLIENTOW) TEJ SAMEJ aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCorsBypassAttemptDetection {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson09` - zademonstruj
         * PROBE OBEJSCIA CORS (np. serwer-do-serwera BEZ przegladarki)
         * - DLACZEGO CORS TEGO NIE ZATRZYMUJE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCorsConfigurationTestedAgainstRealBrowser {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: opisz, JAK ZWERYFIKOWALBYS konfiguracje CORS
         * W PRAWDZIWEJ przegladarce (DevTools, zakladka Network).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareCorsWithServerSideProxyPattern {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj CORS Z ALTERNATYWNYM podejsciem
         * (proxy PO STRONIE serwera frontendowego, "ten sam origin
         * dla przegladarki") - kiedy CO wybrac?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiOriginSecureApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE API Z BEZPIECZNA konfiguracja CORS - RÓZNE
         * reguly PER sciezka (Zadanie 11) + credentials (Zadanie 8) +
         * audyt (Zadanie 22) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
