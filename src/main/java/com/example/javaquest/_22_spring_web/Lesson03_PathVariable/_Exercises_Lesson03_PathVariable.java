package com.example.javaquest._22_spring_web.Lesson03_PathVariable;

public class _Exercises_Lesson03_PathVariable {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPathVariablePurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, do czego sluzy `@PathVariable` i
         * kiedy uzyc go zamiast `@RequestParam`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnPathVariableEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint z 1 `@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementTwoPathVariablesInOnePath {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj endpoint z 2 `@PathVariable` w JEDNEJ sciezce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseExplicitNameWhenParameterNameDiffers {
        /*
         * 🧪 Zadanie 4:
         * Uzyj `@PathVariable("inna-nazwa")` gdy nazwa parametru Javy
         * ROZNI SIE od nazwy w `{...}`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConvertToLongType {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `@PathVariable long id` - zweryfikuj konwersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConvertToEnum {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj `@PathVariable` typu `enum` (np. status
         * zamowienia) - zweryfikuj automatyczna konwersje z tekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TriggerTypeMismatchAndInspectError {
        /*
         * 🧪 Zadanie 7:
         * Wyslij NIEPRAWIDLOWA wartosc (np. tekst zamiast liczby) -
         * zbadaj DOKLADNY komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementOptionalPathVariableWithRequiredFalse {
        /*
         * 🧪 Zadanie 8:
         * Uzyj `@PathVariable(required = false)` z 2 wzorcami sciezki
         * (`@GetMapping({"/x", "/x/{id}"})`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithManualParsingFromChapter18 {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z `_18_rest_api/Lesson09` - porownaj RECZNE parsowanie
         * sciezki (surowy `HttpServer`) z `@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyPathVariableCannotBeOptionalByDefault {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `@PathVariable` jest
         * DOMYSLNIE wymagany (w odroznieniu od `@RequestParam` z
         * wartoscia domyslna).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementPathVariableWithRegexConstraint {
        /*
         * 🧪 Zadanie 11:
         * Uzyj REGEX w segmencie sciezki (`{id:[0-9]+}`) - zweryfikuj
         * odrzucenie (404) DLA nie-numerycznych wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementDateTypePathVariable {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `@PathVariable LocalDate` - skonfiguruj FORMAT
         * przez `@DateTimeFormat`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomConverterForPathVariable {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNY `Converter<String, T>` dla WYMYSLONEGO
         * typu (powiaz z `_21_spring_boot/Lesson08`, Zadanie 21) -
         * zarejestruj dla `@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementMapOfAllPathVariables {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `@PathVariable Map<String, String>` - odbierz WSZYSTKIE
         * zmienne sciezki NARAZ, bez wymieniania ich osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareEncodedVsDecodedPathVariable {
        /*
         * 🧪 Zadanie 15:
         * Wyslij ZAKODOWANA wartosc (np. spacja jako `%20`) w segmencie
         * sciezki - zweryfikuj, czy `@PathVariable` otrzymuje wartosc
         * ZDEKODOWANA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementHierarchicalResourcePathStructure {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj 3-poziomowa hierarchie zasobow
         * (`/users/{userId}/orders/{orderId}/items/{itemId}`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementPathVariableValidationWithBeanValidation {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_19_security_basics/Lesson17` - dodaj `@Positive`/
         * `@Min` NA `@PathVariable` (z `@Validated` na klasie
         * kontrolera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementSlashContainingPathVariable {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): sprawdz, jak obsluzyc
         * `@PathVariable` ZAWIERAJACY ukosnik (`/`) - jakie SA
         * ograniczenia?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasurePathMatchingPerformanceWithManyVariables {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj sciezke z 5+ zmiennymi - zmierz czas dopasowania I
         * ekstrakcji WSZYSTKICH wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildPathVariableTypeConversionReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele obslugiwanych typow
         * konwersji `@PathVariable` (String/int/long/UUID/enum/LocalDate).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomPathVariableResolver {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala (zaawansowana dokumentacja): sprawdz
         * `HandlerMethodArgumentResolver` - jak Spring MVC ROZWIAZUJE
         * `@PathVariable` "pod maska"?
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomArgumentResolverForDomainObject {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY `HandlerMethodArgumentResolver`
         * KONWERTUJACY `@PathVariable` WPROST na obiekt domenowy (np.
         * pobrany z repozytorium NA PODSTAWIE id).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementResourceExistenceValidationBeforeControllerLogic {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z Zadaniem 22 - jesli zasob o podanym `@PathVariable`
         * NIE ISTNIEJE, zwroc 404 PRZED wejsciem do logiki kontrolera
         * (przez resolver, nie `if` w metodzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementApiVersioningViaPathVariablePrefix {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj wersjonowanie API PRZEZ `@PathVariable` (np.
         * `/{version}/orders/{id}`) - RÓZNA logika w zaleznosci od
         * wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSecurityValidationOnPathVariableOwnership {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics` - zaimplementuj sprawdzenie, ze
         * `userId` z `@PathVariable` ODPOWIADA "zalogowanemu"
         * (symulowanemu) uzytkownikowi - odrzuc CUDZE zasoby (403).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCachingKeyBasedOnPathVariables {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_13_libraries/Lesson27` (Caffeine) - uzyj
         * `@PathVariable` jako KLUCZA cache'a dla kosztownej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementPathVariableBasedRateLimiting {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj rate limiting
         * PER `@PathVariable` (np. per uzytkownik, nie GLOBALNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGraphQlStyleNestedResourceResolution {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj GLEBOKO zagniezdzona strukture zasobow (4+
         * poziomy `@PathVariable`) Z WALIDACJA relacji miedzy
         * poziomami (np. "czy item NALEZY do tego zamowienia?").
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRestfulPathDesignWithRpcStyleParameters {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (powiaz z `_18_rest_api/Lesson19`): porownaj
         * projekt sciezki z `@PathVariable` (RESTful) z RPC-stylowym
         * przekazywaniem WSZYSTKIEGO jako parametrow zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteResourceHierarchyApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne API z WIELOPOZIOMOWA hierarchia zasobow,
         * WALIDACJA (Zadanie 17), autoryzacja wlasnosci (Zadanie 25),
         * cache (Zadanie 26) - jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
