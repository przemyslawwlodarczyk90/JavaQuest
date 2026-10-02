package com.example.javaquest.liveCodeVersion._24_spring_security.Lesson16_SecurityExceptionHandling;

public class _Exercises_Lesson16_SecurityExceptionHandling {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainEntryPointVsAccessDeniedHandler {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij ROZNICE miedzy `AuthenticationEntryPoint`
         * A `AccessDeniedHandler`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddTimestampToErrorResponse {
        /*
         * 🧪 Zadanie 2:
         * Dodaj pole "timestamp" DO JSON-owej odpowiedzi bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddInstancePathToErrorResponse {
        /*
         * 🧪 Zadanie 3:
         * Dodaj pole "instance" (sciezka zadania) DO odpowiedzi,
         * powiazanie Z `_18_rest_api/Lesson12`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestBothHandlersReturnJsonContentType {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj naglowek `Content-Type: application/json` W OBU
         * odpowiedziach (401 I 403).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareWithLesson12DefaultBehavior {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: porownaj TA lekcje Z Lesson12 (BRAK
         * WLASNEGO entry pointu) - CO SIE ZMIENILO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestExpiredTokenGoesToEntryPoint {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj, ze WYGASLY token TEZ trafia DO
         * `AuthenticationEntryPoint` (401), NIE `AccessDeniedHandler`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyControllerAdviceDoesNotCatchSecurityExceptions {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, DLACZEGO `@RestControllerAdvice`
         * (`_22_spring_web/Lesson09`) NIE PRZECHWYCI wyjatkow Security
         * (POWSTAJA W filtrach, PRZED Spring MVC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddThirdEndpointWithDifferentRole {
        /*
         * 🧪 Zadanie 8:
         * Dodaj TRZECI endpoint Z INNA rola I zweryfikuj TEN SAM
         * format bledu 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestMalformedTokenGoesToEntryPoint {
        /*
         * 🧪 Zadanie 9:
         * Wyslij CALKOWICIE ZEPSUTY token (losowy string) - zweryfikuj
         * 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentAllPossibleErrorResponsesForApi {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: udokumentuj WSZYSTKIE mozliwe odpowiedzi
         * bledu (401/403/400/404/500) DLA TEGO API.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementDifferentMessagesForDifferentAuthErrors {
        /*
         * 🧪 Zadanie 11:
         * Rozroznij W `AuthenticationEntryPoint` PRZYCZYNE bledu (BRAK
         * tokenu vs NIEPRAWIDLOWY token) I zwroc INNY komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementLoggingInsideHandlers {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_19_security_basics/Lesson19` - dodaj logowanie
         * (BEZPIECZNE, BEZ tokenu W logu) W OBU obsluzycielach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseObjectMapperForJsonSerialization {
        /*
         * 🧪 Zadanie 13:
         * Zastap RECZNA konkatenacje String Z `ObjectMapper`
         * (Jackson, JUZ obecny transytywnie) DO serializacji bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementLocalizedErrorMessages {
        /*
         * 🧪 Zadanie 14:
         * Dodaj obsluge naglowka `Accept-Language` DO zwracania
         * komunikatu bledu W POLSKIM lub angielskim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestHandlersWithConcurrentRequests {
        /*
         * 🧪 Zadanie 15:
         * Wyslij 20 ROWNOLEGLYCH zadan wywolujacych OBA handlery -
         * zweryfikuj POPRAWNOSC WSZYSTKICH odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddCorrelationIdToErrorResponse {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_18_rest_api` - dodaj unikalny "correlationId" DO
         * KAZDEJ odpowiedzi bledu (do korelacji Z logami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRateLimitedErrorResponses {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_18_rest_api/Lesson16` - dodaj naglowek
         * `Retry-After` DO odpowiedzi 401 PO WIELU nieudanych probach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareWithDefaultSpringBootErrorController {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: powiaz z `_21_spring_boot/Lesson11_
         * ErrorHandlingBasics` - porownaj domyslny `/error` Boota Z
         * WLASNYMI obsluzycielami Security.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementSecurityHeadersInErrorResponse {
        /*
         * 🧪 Zadanie 19:
         * Powiaz z `_19_security_basics/Lesson12_SecurityHeaders` -
         * dodaj naglowki bezpieczenstwa (`X-Content-Type-Options`) TEZ
         * DO odpowiedzi bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignConsistentErrorContractAcrossChapters {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: porownaj format bledu TU Z `_18_rest_api/
         * Lesson12` I `_22_spring_web/Lesson10` - zaprojektuj JEDEN,
         * SPOJNY kontrakt DLA CALEGO systemu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProblemDetailInsteadOfManualJson {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_22_spring_web/Lesson10` - uzyj
         * `org.springframework.http.ProblemDetail` (wbudowany W Spring
         * 6) ZAMIAST recznego JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignErrorHandlingForDistributedTracing {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_21_spring_boot/Lesson13_ObservabilityMicrometer
         * AndTracing` - dodaj trace ID DO KAZDEJ odpowiedzi bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSecurityAuditIntegration {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics/Lesson19` - zapisz KAZDY 401/403
         * DO lancucha skrotow (tamper-evident audit trail).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignGracefulDegradationOnHandlerFailure {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zaprojektuj FALLBACK, GDY SAM
         * `AuthenticationEntryPoint`/`AccessDeniedHandler` RZUCI
         * wyjatek (co POWINNO sie stac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDifferentiatedResponsesForApiVsBrowser {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj handler ROZROZNIAJACY zadanie API (JSON) OD
         * zadania przegladarki (`Accept: text/html` -> przekierowanie
         * DO `/login`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BenchmarkCustomHandlersOverhead {
        /*
         * 🧪 Zadanie 26:
         * Zmierz narzut WLASNYCH obsluzycieli WZGLEDEM DOMYSLNYCH
         * (1000 zadan bledu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSecurityExceptionMetrics {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_21_spring_boot/Lesson12_SpringBootActuator` -
         * DODAJ WLASNA metryke Micrometer LICZACA 401/403 WEDLUG typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignSecurityIncidentDetectionFromErrorPatterns {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj wykrywanie ANOMALII (np. 50x 403 Z
         * JEDNEGO IP W 1 minute = mozliwy atak) NA PODSTAWIE wzorcow
         * bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareErrorHandlingWithGraphQlOrGrpc {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: powiaz z `_18_rest_api/Lesson19_RestVsRpcVsGraphQL` -
         * porownaj obsluge bledow REST (kody statusu) Z GraphQL/gRPC
         * (WLASNE kody bledow W ciele odpowiedzi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullProductionGradeErrorHandlingSuite {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PRODUKCYJNY zestaw: `ProblemDetail` + audyt + metryki +
         * korelacja + lokalizacja - Z TESTAMI dla WSZYSTKICH scenariuszy
         * bledow.
         */
        public static void main(String[] args) { }
    }
}
