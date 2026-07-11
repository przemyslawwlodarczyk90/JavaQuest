package com.example.javaquest._22_spring_web.Lesson16_InterceptorsAndWebMvcConfigurer;

public class _Exercises_Lesson16_InterceptorsAndWebMvcConfigurer {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainInterceptorVsFilterDifference {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_07_servlets/Lesson14`): wyjasnij
         * ROZNICE miedzy `HandlerInterceptor` a `Filter`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnLoggingInterceptor {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY interceptor logujacy KAZDE zadanie
         * (metoda + sciezka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RegisterInterceptorViaWebMvcConfigurer {
        /*
         * 🧪 Zadanie 3:
         * Zarejestruj WLASNY interceptor Z Zadania 2 przez
         * `WebMvcConfigurer`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementPreHandlePostHandleAfterCompletionOrder {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj interceptor drukujacy WSZYSTKIE 3 metody cyklu
         * zycia - zweryfikuj KOLEJNOSC wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementShortCircuitingInterceptor {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj interceptor ZWRACAJACY `false` W `preHandle` PRZY
         * OKRESLONYM warunku - zweryfikuj, ze kontroler NIE ZOSTAJE
         * wywolany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementPathPatternRestriction {
        /*
         * 🧪 Zadanie 6:
         * Ogranicz interceptor DO WYBRANYCH sciezek przez
         * `addPathPatterns(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementExcludePathPatterns {
        /*
         * 🧪 Zadanie 7:
         * Wylacz interceptor DLA WYBRANYCH sciezek przez
         * `excludePathPatterns(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementMultipleInterceptorsOrdering {
        /*
         * 🧪 Zadanie 8:
         * Zarejestruj 2+ interceptory NARAZ - zweryfikuj KOLEJNOSC
         * wywolania (kolejnosc `addInterceptor(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementAfterCompletionExceptionHandling {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj interceptor SPRAWDZAJACY parametr `Exception ex`
         * W `afterCompletion` - zaloguj INACZEJ PRZY sukcesie vs bledzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToUseInterceptorOverFilter {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, KIEDY wybrac Interceptor NAD Filter
         * (I ODWROTNIE) - jakie SA kryteria decyzji?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementRequestTimingInterceptorWithThreshold {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz interceptor CZASOWY - zaloguj OSTRZEZENIE GDY zadanie
         * PRZEKROCZY 100ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementHandlerMethodIntrospection {
        /*
         * 🧪 Zadanie 12:
         * W interceptorze rzutuj `handler` NA `HandlerMethod` - wypisz
         * NAZWE metody I klasy kontrolera OBSLUGUJACEGO zadanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomAnnotationDrivenInterceptor {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNA adnotacje (np. `@RequireApiKey`) -
         * interceptor SPRAWDZA jej OBECNOSC PRZEZ `HandlerMethod`
         * (`getMethodAnnotation(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRequestIdPropagationInterceptor {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj interceptor GENERUJACY unikalny "request ID" W
         * `preHandle`, dolaczajacy go DO odpowiedzi (naglowek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementMdcLoggingContextInterceptor {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_13_libraries/Lesson17_MdcAndLoggingBestPractices` -
         * ustaw MDC (SLF4J) W `preHandle`, WYCZYSC W `afterCompletion`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementInterceptorAccessingRequestAttributes {
        /*
         * 🧪 Zadanie 16:
         * Przekaz DANE Z `preHandle` DO kontrolera PRZEZ
         * `request.setAttribute(...)` - odczytaj W kontrolerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementConditionalInterceptorBasedOnProfile {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_20_spring_core/Lesson15` - zarejestruj interceptor
         * TYLKO W profilu `dev` (np. szczegolowe logowanie DEBUG).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareInterceptorWithAopAroundAdvice {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_20_spring_core/Lesson21-22` (AOP) - porownaj
         * `HandlerInterceptor` Z `@Around` aspektem - jakie SA
         * PODOBIENSTWA/RÓZNICE zakresu dzialania?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureInterceptorOverheadOnRequestLatency {
        /*
         * 🧪 Zadanie 19:
         * Zmierz DODATKOWY narzut interceptora NA CZAS obslugi zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildInterceptorPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" typowych zastosowan interceptorow
         * (logowanie/autoryzacja/timing/request ID/MDC).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRateLimitingInterceptor {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj rate
         * limiting PRZEZ interceptor (ZAMIAST W KAZDEJ metodzie
         * kontrolera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementJwtValidationInterceptor {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_19_security_basics/Lesson05` - zwaliduj token JWT
         * W `preHandle`, ODRZUC nieprawidlowe PRZED kontrolerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementMetricsCollectionInterceptor {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_21_spring_boot/Lesson13` (Micrometer) - zlicz
         * CZAS I LICZBE zadan PER endpoint PRZEZ interceptor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCircuitBreakerStyleInterceptor {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj PROSTY "wylacznik" (circuit breaker) - PO N
         * kolejnych bledach, interceptor ODRZUCA nowe zadania NA
         * OKRESLONY czas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAuditTrailInterceptor {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics/Lesson19` - zaimplementuj
         * PELNY dziennik audytu (uzytkownik/sciezka/status/czas) PRZEZ
         * interceptor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementInterceptorOrderWithMultiplePriorityLevels {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj 3+ interceptory Z JAWNA KOLEJNOSCIA
         * (bezpieczenstwo -> logowanie -> metryki) - zweryfikuj
         * FAKTYCZNA kolejnosc wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementInterceptorForApiVersionDeprecationWarning {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_18_rest_api/Lesson14` - dodaj naglowek
         * `X-API-Deprecated` DO ODPOWIEDZI DLA PRZESTARZALEJ wersji API,
         * PRZEZ interceptor (NIE W KAZDYM kontrolerze osobno).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDistributedTracingInterceptor {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_21_spring_boot/Lesson13` (tracing) - propaguj
         * "trace ID" MIEDZY interceptorem A LOGAMI CALEGO zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareInterceptorApproachAcrossFrameworks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj `HandlerInterceptor` (Spring) Z
         * middleware W Express.js/ASP.NET - jakie SA PODOBIENSTWA
         * koncepcyjne?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteCrossCuttingInterceptorChainCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY lancuch interceptorow - autoryzacja
         * (Zadanie 22) + rate limiting (Zadanie 21) + metryki (Zadanie
         * 23) + audyt (Zadanie 25) - jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
