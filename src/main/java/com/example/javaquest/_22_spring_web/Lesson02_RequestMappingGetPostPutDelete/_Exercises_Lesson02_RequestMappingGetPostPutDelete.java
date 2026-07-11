package com.example.javaquest._22_spring_web.Lesson02_RequestMappingGetPostPutDelete;

public class _Exercises_Lesson02_RequestMappingGetPostPutDelete {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainOldVsNewMappingStyle {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy starym i nowym stylem
         * mapowania metod HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnGetMappingEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint `@GetMapping`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnPostMappingEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY endpoint `@PostMapping`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementOwnPutDeletePatchEndpoints {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNE endpointy `@PutMapping`/`@DeleteMapping`/
         * `@PatchMapping`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RewriteOldStyleToNewStyle {
        /*
         * 🧪 Zadanie 5:
         * Przepisz `@RequestMapping(method = RequestMethod.POST)` na
         * `@PostMapping` - zweryfikuj IDENTYCZNE zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementMultipleMethodsOnSamePathDifferentVerbs {
        /*
         * 🧪 Zadanie 6:
         * Zdefiniuj TA SAMA sciezke z RÓZNYMI metodami HTTP (GET zwraca
         * dane, DELETE usuwa) w JEDNEJ klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddClassLevelPrefixToOwnController {
        /*
         * 🧪 Zadanie 7:
         * Dodaj `@RequestMapping("/api/notes")` NA KLASIE do WLASNEGO
         * kontrolera z Zadania 2-4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestWrongHttpMethodOnExistingPath {
        /*
         * 🧪 Zadanie 8:
         * Wyslij ZLA metode HTTP (np. DELETE na sciezke MAJACA tylko
         * GET) - zweryfikuj status 405 Method Not Allowed.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPatchHasNoDefaultHttpServletMethod {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z `_07_servlets/Lesson08` - wyjasnij, dlaczego
         * `HttpServlet` NIE MA `doPatch()`, a Spring MVC ROZWIAZUJE to
         * inaczej (przez `@PatchMapping`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRequestMappingValueVsPath {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja): sprawdz, czy atrybuty `value` i
         * `path` w `@RequestMapping` sa ROWNOWAZNE.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementMultiplePathsForSameMethod {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@GetMapping({"/sciezka1", "/sciezka2"})` - JEDNA metoda
         * OBSLUGUJACA WIELE sciezek naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPathPatternWithWildcard {
        /*
         * 🧪 Zadanie 12:
         * Uzyj wzorca sciezki z gwiazdka (`/files/*`/`/files/**`) -
         * zweryfikuj RÓZNICE (1 segment vs DOWOLNA liczba segmentow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementConsumesAttributeRestriction {
        /*
         * 🧪 Zadanie 13:
         * Dodaj `consumes = "application/json"` do `@PostMapping` -
         * zweryfikuj odrzucenie INNEGO Content-Type (415).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementProducesAttributeRestriction {
        /*
         * 🧪 Zadanie 14:
         * Dodaj `produces = "application/json"` - powiaz z
         * `_18_rest_api/Lesson07_ContentNegotiation`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementHeadersAttributeCondition {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `headers = "X-Api-Version=2"` w `@GetMapping` - RÓZNE
         * metody dla RÓZNYCH wersji API (przez naglowek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementParamsAttributeCondition {
        /*
         * 🧪 Zadanie 16:
         * Uzyj `params = "format=xml"` - RÓZNA metoda w zaleznosci od
         * parametru zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareRequestMappingAmbiguityErrors {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj DWIE metody z TA SAMA sciezka I TA SAMA metoda HTTP
         * (bez rozroznienia) - powiaz z bledem "Ambiguous mapping" z
         * `_21_spring_boot/Lesson11`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementNestedClassLevelAndMethodLevelPaths {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj kontroler z prefiksem klasy + WIELOMA metodami o
         * ROZNYCH sciezkach zagniezdzonych (`/api/orders/{id}/items`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureRoutingPerformanceWithManyEndpoints {
        /*
         * 🧪 Zadanie 19:
         * Zarejestruj 50 endpointow - zmierz czas ROZWIAZANIA
         * konkretnego zadania (routing) - czy LICZBA endpointow WPLYWA
         * na wydajnosc?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildHttpMethodShortcutReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" WSZYSTKICH skrotow `@XMapping` z
         * ODPOWIADAJACA metoda HTTP.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomRequestMappingHandlerMapping {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala (zaawansowana dokumentacja): sprawdz, jak
         * ROZSZERZYC `RequestMappingHandlerMapping` o WLASNA logike
         * dopasowania (poza standardowymi adnotacjami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomHttpMethodViaComposedAnnotation {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNA, "zlozona" adnotacje (meta-oznaczona
         * `@RequestMapping`) - np. `@GetJson` = `@GetMapping` +
         * `produces = "application/json"` NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementVersionedApiUsingPathPrefixStrategy {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_18_rest_api/Lesson14_Versioning` - zaimplementuj
         * `/api/v1/...` i `/api/v2/...` W TYM SAMYM projekcie, dla TEGO
         * SAMEGO zasobu, z RÓZNA struktura odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnalyzeHandlerMappingRegistryProgrammatically {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z Zadaniem 23 z Lesson01 (analiza
         * `RequestMappingHandlerMapping`) - zbuduj PELNY raport
         * WSZYSTKICH tras w aplikacji (metoda + sciezka + klasa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRequestMappingWithRegexPathVariable {
        /*
         * 🧪 Zadanie 25:
         * Uzyj REGEX w `@PathVariable` (np. `/orders/{id:[0-9]+}`) -
         * zweryfikuj, ze NIE-numeryczne ID daje 404 (nie 400).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareServletMappingWithSpringMvcMapping {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: powiaz z `_07_servlets/Lesson01-04` - jak
         * `DispatcherServlet` (JEDEN serwlet obslugujacy WSZYSTKO)
         * ROZNI SIE od wielu osobnych serwletow (surowe Servlet API)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFallbackCatchAllEndpoint {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj "catch-all" endpoint (`/**`) obslugujacy
         * NIEZNANE sciezki WLASNA logika ZAMIAST domyslnego 404 Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementApiGatewayStyleRoutingSimulation {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj prosty "API Gateway" - kontroler PRZEKAZUJACY
         * (proxy) zadania do INNEGO, symulowanego "mikroserwisu" (lokalny
         * `HttpServer`, powiaz z `_06_networking`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureImpactOfManyOverlappingPathPatterns {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj kontroler z 20+ NAKLADAJACYMI SIE wzorcami sciezek -
         * zweryfikuj, KTORY wzorzec WYGRYWA (specyficznosc dopasowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteRoutingArchitectureCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna architekture routingu DUZEJ aplikacji -
         * wersjonowanie + prefiksy modulow + catch-all + raport WSZYSTKICH
         * tras (Zadanie 24) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
