package com.example.javaquest._22_spring_web.Lesson04_RequestParam;

public class _Exercises_Lesson04_RequestParam {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRequestParamVsPathVariable {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy `@RequestParam` a
         * `@PathVariable` (Lesson03) - kiedy uzyc ktorego?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementRequiredRequestParam {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint z WYMAGANYM `@RequestParam`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOptionalWithDefaultValue {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `@RequestParam(defaultValue = "...")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementOptionalAsJavaOptional {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `@RequestParam Optional<String>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerMissingRequiredParamError {
        /*
         * 🧪 Zadanie 5:
         * Wyslij zadanie BEZ wymaganego parametru - zapisz DOKLADNY
         * komunikat bledu 400.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementBooleanRequestParam {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj `@RequestParam boolean` (np. `?active=true`) -
         * zweryfikuj konwersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementListRequestParam {
        /*
         * 🧪 Zadanie 7:
         * Odtworz demo `List<String>` z teorii dla WLASNEGO przykladu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareCommaSeparatedVsRepeatedParamSyntax {
        /*
         * 🧪 Zadanie 8:
         * Wyslij WARTOSCI rozdzielone przecinkiem (`?tag=a,b,c`) ZAMIAST
         * powtorzonego klucza - zweryfikuj, CZY Spring TEZ to
         * rozpoznaje jako liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementMultipleIndependentRequestParams {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj endpoint z 4+ NIEZALEZNYMI `@RequestParam`
         * (mieszanka wymaganych/opcjonalnych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRequestParamIsBetterForFiltering {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_18_rest_api/Lesson10`): wyjasnij,
         * dlaczego filtrowanie/sortowanie/stronicowanie uzywa
         * `@RequestParam`, NIE `@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementMapOfAllRequestParams {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@RequestParam Map<String, String>` - odbierz WSZYSTKIE
         * parametry zapytania NARAZ, bez wymieniania ich osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomDateTimeFormatForParam {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `@RequestParam LocalDate` z WLASNYM formatem
         * (`@DateTimeFormat(pattern = "...")`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementEnumRequestParamWithInvalidValueHandling {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `@RequestParam` typu `enum` - zweryfikuj
         * zachowanie DLA NIEPRAWIDLOWEJ wartosci (nie pasujacej do
         * zadnej stalej enum).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementBindingToDtoObjectFromMultipleParams {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj metode przyjmujaca OBIEKT (DTO) ZAMIAST
         * WIELU osobnych `@RequestParam` - Spring AUTOMATYCZNIE
         * wiaze pola obiektu z parametrami zapytania (bez adnotacji na
         * KAZDYM polu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementValidationOnRequestParamValues {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_19_security_basics/Lesson17` - dodaj `@Min`/`@Max`
         * NA `@RequestParam` (z `@Validated` na klasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementPaginationWithRequestParams {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_18_rest_api/Lesson10` - zaimplementuj PELNE
         * stronicowanie (`page`/`size`/`sort`) przez `@RequestParam`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareRequestParamNameMismatchHandling {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `@RequestParam("inna-nazwa")` gdy nazwa parametru Javy
         * ROZNI SIE od klucza w zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConditionalLogicBasedOnParamPresence {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj logike ROZGALEZIAJACA SIE w zaleznosci od TEGO,
         * KTORE z 3 opcjonalnych parametrow SA obecne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureRequestParamParsingOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas parsowania zapytania z 20+ parametrami - jaki
         * jest narzut?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildRequestParamPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" wzorcow `@RequestParam` z tej
         * lekcji (wymagany/domyslny/Optional/lista/Map).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomArgumentResolverForComplexQuery {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `HandlerMethodArgumentResolver`
         * PARSUJACY zlozone zapytanie (np. `?filter=status:eq:active`)
         * na WLASNY obiekt "kryteriow wyszukiwania".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementSqlInjectionSafeQueryBuilding {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_19_security_basics/Lesson13` - zaimplementuj
         * BEZPIECZNE budowanie zapytania SQL Z parametrow `@RequestParam`
         * (PreparedStatement, NIE konkatenacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDynamicFilteringWithSpecificationPattern {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj DYNAMICZNE filtrowanie (WIELE opcjonalnych
         * `@RequestParam`) BUDUJACE zapytanie w locie - TYLKO OBECNE
         * filtry sa STOSOWANE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRequestParamBasedFeatureToggle {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_21_spring_boot/Lesson04` - zaimplementuj feature
         * toggle STEROWANY parametrem zapytania (np. `?beta=true`)
         * ZAMIAST konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementComplexValidationAcrossMultipleParams {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj walidacje "krzyzowa" MIEDZY 2 parametrami (np.
         * "dataOd musi byc PRZED dataDo") - powiaz z
         * `_19_security_basics/Lesson17`, Zadanie 12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRequestParamAuditLogging {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj (z
         * SANITYZACJA, bez wrazliwych danych) WSZYSTKIE parametry
         * KAZDEGO zadania do dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRateLimitingBasedOnQueryComplexity {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj rate limiting
         * PROPORCJONALNY do "zlozonosci" zapytania (liczba filtrow =
         * wyzszy "koszt").
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGraphQlStyleFieldSelectionViaParam {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj selekcje pol odpowiedzi PRZEZ parametr
         * (`?fields=id,name`) - zwroc TYLKO WYBRANE pola obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareQueryParamApproachWithGraphQlConceptually {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (powiaz z `_18_rest_api/Lesson19`): porownaj
         * ELASTYCZNOSC filtrowania przez `@RequestParam` z prawdziwym
         * GraphQL - jakie SA GRANICE podejscia REST?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAdvancedSearchApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne API wyszukiwania - DYNAMICZNE filtry
         * (Zadanie 23) + walidacja (Zadanie 25) + stronicowanie
         * (Zadanie 16) + audyt (Zadanie 26) - jeden spojny, dzialajacy
         * endpoint.
         */
        public static void main(String[] args) { }
    }
}
