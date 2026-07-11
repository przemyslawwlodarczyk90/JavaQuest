package com.example.javaquest._21_spring_boot.Lesson11_ErrorHandlingBasics;

public class _Exercises_Lesson11_ErrorHandlingBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainBasicErrorControllerRole {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij role `BasicErrorController` w Spring
         * Boot.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddOwnEndpointThrowingException {
        /*
         * 🧪 Zadanie 2:
         * Dodaj WLASNY endpoint rzucajacy `RuntimeException` - odtworz
         * demo z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TriggerNotFoundWithResponseStatusException {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY endpoint zwracajacy 404 przez
         * `ResponseStatusException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerBadRequestWithResponseStatusException {
        /*
         * 🧪 Zadanie 4:
         * To samo dla statusu 400 (Bad Request) z WLASNYM komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InspectDefaultErrorJsonFields {
        /*
         * 🧪 Zadanie 5:
         * Wypisz WSZYSTKIE pola domyslnego JSON-a bledu - porownaj z
         * RFC 7807 z `_18_rest_api/Lesson12`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyStackTraceIsHiddenByDefault {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego pole "trace" (stack trace)
         * NIE jest domyslnie widoczne w odpowiedzi - powiaz z
         * bezpieczenstwem (`_19_security_basics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EnableStackTraceInResponse {
        /*
         * 🧪 Zadanie 7:
         * Ustaw `server.error.include-stacktrace=always` - zweryfikuj,
         * ze stack trace TERAZ pojawia sie w odpowiedzi (i wyjasnij,
         * DLACZEGO to NIEBEZPIECZNE na produkcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestNonExistentEndpointReturns404 {
        /*
         * 🧪 Zadanie 8:
         * Wywolaj NIEISTNIEJACY endpoint - zweryfikuj domyslna
         * odpowiedz 404 Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareErrorPageForBrowserVsApi {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala (dokumentacja): Boot zwraca INNA odpowiedz dla
         * przegladarki (HTML "Whitelabel Error Page") niz dla klienta
         * API (JSON) - jak to ROZPOZNAJE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhitelabelErrorPageOrigin {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, czym jest "Whitelabel Error Page" i
         * jak ja WYLACZYC (`server.error.whitelabel.enabled=false`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomErrorAttributesClass {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNA klase `ErrorAttributes` (rozszerzajaca
         * `DefaultErrorAttributes`) DODAJACA WLASNE pole do odpowiedzi
         * bledu (np. "requestId").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomErrorControllerReplacingDefault {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj WLASNY kontroler `/error` (interfejs
         * `ErrorController`) CALKOWICIE ZASTEPUJACY `BasicErrorController`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestMultipleExceptionTypesAndCompareStatusCodes {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj 4+ endpointy rzucajace RÓZNE typy wyjatkow -
         * porownaj otrzymane statusy HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementResponseStatusExceptionWithHeaders {
        /*
         * 🧪 Zadanie 14:
         * Dodaj WLASNE naglowki do odpowiedzi bledu (przez
         * `ResponseStatusException` + `ResponseEntityExceptionHandler` -
         * lub prostsze podejscie) - zapowiedz `_22_spring_web/Lesson09`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareValidationErrorResponseWithBasicError {
        /*
         * 🧪 Zadanie 15:
         * Wywolaj endpoint z NIEPRAWIDLOWYM cialem zadania (Bean
         * Validation, powiaz z `_19_security_basics/Lesson17`) -
         * porownaj STRUKTURE odpowiedzi bledu z prostym `RuntimeException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementErrorPathCustomization {
        /*
         * 🧪 Zadanie 16:
         * Zmien domyslna sciezke bledu (`server.error.path`, domyslnie
         * `/error`) na WLASNA - zweryfikuj dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementConditionalErrorDetailBasedOnProfile {
        /*
         * 🧪 Zadanie 17:
         * Skonfiguruj RÓZNY poziom szczegolowosci bledu per profil
         * (Lesson06) - "dev" = pelne szczegoly, "prod" = minimalne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestErrorHandlingWithMalformedJson {
        /*
         * 🧪 Zadanie 18:
         * Wyslij CELOWO ZEPSUTY JSON w ciele zadania POST - zweryfikuj
         * odpowiedz bledu (400 Bad Request, komunikat parsowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementErrorLoggingForEveryUnhandledException {
        /*
         * 🧪 Zadanie 19:
         * Powiaz z `Lesson10_LoggingInSpringBoot` - zweryfikuj, ze
         * KAZDY nieobsluzony wyjatek jest AUTOMATYCZNIE logowany przez
         * Boota (jakim poziomem?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildErrorHandlingBehaviorReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele - dla KAZDEGO typu
         * wyjatku z tej lekcji, jaki status/JSON otrzymujesz.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProblemDetailBasedErrorResponse {
        /*
         * 🧪 Zadanie 21:
         * Uzyj `ProblemDetail` (wbudowane w Spring 6, powiaz z
         * `_22_spring_web/Lesson10`) ZAMIAST domyslnego formatu Boota -
         * porownaj STRUKTURE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementGlobalExceptionHandlerPreview {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WCZESNA wersje `@ControllerAdvice`+
         * `@ExceptionHandler` (pelna lekcja: `_22_spring_web/Lesson09`) -
         * obsluz 3 rozne typy wyjatkow WLASNA logika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSecurityAwareErrorHandlingHidingInternals {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics` - zaimplementuj obsluge bledu,
         * ktora NIGDY nie ujawnia szczegolow implementacyjnych (nazwy
         * klas/pakietow) uzytkownikowi koncowemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementErrorRateMonitoringAspect {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj `@Aspect` (powiaz z `_20_spring_core/Lesson21`)
         * ZLICZAJACY wystapienia KAZDEGO typu wyjatku w kontrolerach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCircuitBreakerStyleErrorHandling {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj mechanizm "circuit breaker" (powiaz z
         * `_18_rest_api/Lesson16`) - po N kolejnych bledach, NASTEPNE
         * zadania sa ODRZUCANE natychmiast (503).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareErrorHandlingAcrossReactiveVsServletStack {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala (dokumentacja): sprawdz, jak wyglada obsluga
         * bledow w WebFlux (`ErrorWebExceptionHandler`) - CZYM rozni sie
         * od Servlet stack (ten kurs)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRequestIdCorrelationInErrorResponse {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `Lesson10_LoggingInSpringBoot`, MDC - dolacz "request
         * ID" DO KAZDEJ odpowiedzi bledu, zeby uzytkownik mogl podac go
         * przy zgloszeniu problemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFullErrorHandlingAuditTrail {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_19_security_basics/Lesson19` - zaimplementuj
         * dziennik audytu WSZYSTKICH bledow 5xx (BEZ wrazliwych
         * szczegolow) do analizy pozniejszej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGracefulDegradationOnDownstreamFailure {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj kontroler, ktory PRZY BLEDZIE zewnetrznej
         * zaleznosci (symulowanej) zwraca CZESCIOWA, "degradowana"
         * odpowiedz ZAMIAST calkowitego bledu 500.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteProductionGradeErrorHandlingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system obslugi bledow gotowy do produkcji -
         * WLASNY format (RFC 7807), logowanie + audyt, ukrywanie
         * szczegolow, request ID - zweryfikuj na 5+ typach bledow.
         */
        public static void main(String[] args) { }
    }
}
