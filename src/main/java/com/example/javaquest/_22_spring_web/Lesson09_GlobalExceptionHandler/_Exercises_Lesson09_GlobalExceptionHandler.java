package com.example.javaquest._22_spring_web.Lesson09_GlobalExceptionHandler;

public class _Exercises_Lesson09_GlobalExceptionHandler {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainControllerAdvicePurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, do czego sluzy `@RestControllerAdvice`
         * i CZYM rozni sie od LOKALNEGO `@ExceptionHandler`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnCustomException {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY wyjatek `RuntimeException` (np.
         * `InsufficientStockException`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementLocalExceptionHandler {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj LOKALNY `@ExceptionHandler` dla WLASNEGO
         * wyjatku Z Zadania 2, NA 1 kontrolerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementGlobalControllerAdvice {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `@RestControllerAdvice` obslugujacy WLASNY
         * wyjatek GLOBALNIE, DLA WIELU kontrolerow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementMultipleExceptionHandlersInOneAdvice {
        /*
         * 🧪 Zadanie 5:
         * Dodaj 3+ metody `@ExceptionHandler` W JEDNEJ klasie
         * `@RestControllerAdvice` (RÓZNE typy wyjatkow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementResponseStatusOnExceptionHandler {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `@ResponseStatus` NA metodzie `@ExceptionHandler` -
         * zweryfikuj WLASCIWY kod statusu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareLocalOverridesGlobalPriority {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj ten sam typ wyjatku obslugiwany LOKALNIE I
         * GLOBALNIE - zweryfikuj, KTORY handler WYGRYWA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementCatchAllExceptionHandler {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj handler `@ExceptionHandler(Exception.class)`
         * jako "siatke bezpieczenstwa" DLA WSZYSTKICH nieobsluzonych
         * bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestUnhandledExceptionGivesDefault500 {
        /*
         * 🧪 Zadanie 9:
         * TYMCZASOWO usun `@RestControllerAdvice` - zweryfikuj DOMYSLNY
         * (nieopisany) format bledu 500 Spring Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCentralizedHandlingReducesDuplication {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_17_architecture/Lesson16`): wyjasnij,
         * jak `@RestControllerAdvice` REDUKUJE duplikacje kodu wzgledem
         * `try/catch` W KAZDEJ metodzie kontrolera.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementExceptionHierarchyWithCommonBaseClass {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj HIERARCHIE wlasnych wyjatkow (wspolna klasa
         * bazowa `ApiException`) - JEDEN handler obslugujacy CALA
         * hierarchie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementExceptionHandlerForSpecificController {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@RestControllerAdvice(basePackageClasses = ...)` -
         * ogranicz zasieg advice DO WYBRANYCH kontrolerow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementExceptionHandlerWithRequestContextAccess {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `@ExceptionHandler` PRZYJMUJACY DODATKOWO
         * `HttpServletRequest` - dolacz sciezke zadania DO odpowiedzi
         * bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementExceptionHandlerLoggingWithSeverityLevels {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z `_21_spring_boot/Lesson10_Logging` - zaloguj RÓZNE
         * poziomy (WARN dla bledow klienta 4xx, ERROR dla bledow
         * serwera 5xx) W handlerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementHandlerForTypeMismatchException {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_22_spring_web/Lesson03` - dodaj `@ExceptionHandler`
         * dla `MethodArgumentTypeMismatchException` (blad konwersji
         * `@PathVariable`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementHandlerForNoHandlerFoundException {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj obsluge NIEISTNIEJACEJ sciezki (404) PRZEZ
         * WLASNY handler ZAMIAST domyslnej strony bledu Spring Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementHandlerForHttpMessageNotReadableException {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z Lesson05 - dodaj `@ExceptionHandler` dla
         * `HttpMessageNotReadableException` (zle sformatowany JSON) Z
         * CZYTELNYM komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementExceptionHandlerReturningDifferentContentTypes {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_18_rest_api/Lesson07_ContentNegotiation` -
         * zaimplementuj handler zwracajacy RÓZNY format (JSON/zwykly
         * tekst) W ZALEZNOSCI OD naglowka `Accept`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureExceptionHandlingOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz narzut czasowy WYRZUCENIA I OBSLUZENIA wyjatku
         * wzgledem NORMALNEGO zwrotu wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildExceptionHandlerPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" NAJCZESTSZYCH wyjatkow Spring MVC
         * i ICH zalecanych statusow (walidacja/404/zly JSON/zla metoda).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProblemDetailBasedExceptionHandling {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z Lesson10/`_18_rest_api/Lesson12` (RFC 7807) -
         * przepisz WSZYSTKIE handlery na zwracanie `ProblemDetail`
         * (wbudowana klasa Springa 6+).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementExceptionHandlerExtendingResponseEntityExceptionHandler {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz `ResponseEntityExceptionHandler` (bazowa klasa
         * Springa) ZAMIAST pisac wszystko od zera - PRZESLON WYBRANE
         * metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCorrelationIdInErrorResponses {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_21_spring_boot/Lesson13_Observability` - dolacz
         * unikalny "correlation ID" DO KAZDEJ odpowiedzi bledu (do
         * korelacji Z logami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSecuritySanitizedErrorMessages {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson19` - zweryfikuj, ze
         * handlery NIE UJAWNIAJA wewnetrznych szczegolow (stack trace,
         * nazwy klas) W ODPOWIEDZI 500 (TYLKO w logach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRateLimitedErrorLogging {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj OGRANICZENIE
         * czestotliwosci LOGOWANIA (nie samej obslugi) POWTARZAJACYCH
         * SIE identycznych bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementExceptionHandlerMetricsIntegration {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_21_spring_boot/Lesson13` (Micrometer) - zlicz
         * WYSTAPIENIA KAZDEGO typu wyjatku PRZEZ `Counter`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFallbackHandlerChainWithOrdering {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WIELE klas `@RestControllerAdvice` Z `@Order` -
         * zweryfikuj KOLEJNOSC proby dopasowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAsyncExceptionHandling {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj obsluge wyjatku RZUCONEGO W kodzie
         * ASYNCHRONICZNYM (`@Async`, `CompletableFuture`) - CZY
         * `@ExceptionHandler` DZIALA tu tak samo?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareGlobalHandlingAcrossFrameworksConceptually {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj `@RestControllerAdvice` (Spring) Z
         * globalna obsluga bledow W innych frameworkach (Express.js
         * middleware, ASP.NET exception filters) - jakie SA
         * PODOBIENSTWA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteGlobalErrorHandlingArchitectureCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNA architekture obslugi bledow - hierarchia
         * wyjatkow (Zadanie 11) + RFC 7807 (Zadanie 21) + correlation ID
         * (Zadanie 23) + metryki (Zadanie 26) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
