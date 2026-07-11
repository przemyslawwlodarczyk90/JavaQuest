package com.example.javaquest._22_spring_web.Lesson07_DtoInRestApi;

public class _Exercises_Lesson07_DtoInRestApi {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyNotExposeEntityDirectly {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_17_architecture/Lesson07`): wyjasnij,
         * dlaczego NIE nalezy zwracac encji WPROST z kontrolera REST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnEntityAndResponseDto {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA encje + Response DTO + funkcje mapujaca
         * `toDto(entity)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnRequestDtoForCreation {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNE Request DTO dla operacji tworzenia
         * (POST) - INNY ksztalt niz Response DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareFieldsOfRequestVsResponseDto {
        /*
         * 🧪 Zadanie 4:
         * Zestaw obok siebie pola Request DTO i Response DTO - KTORE
         * pola sa WSPOLNE, a KTORE ROZNE i DLACZEGO?
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementUpdateRequestDtoWithDifferentShape {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj Request DTO dla AKTUALIZACJI (PUT/PATCH) - ktore
         * pola SA/NIE SA edytowalne wzgledem DTO tworzenia?
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementListResponseDto {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj endpoint zwracajacy LISTE Response DTO
         * (`List<UserResponseDto>`), NIE liste encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TriggerLeakByAccidentallyReturningEntity {
        /*
         * 🧪 Zadanie 7:
         * CELOWO zwroc encje zamiast DTO - zapisz DOKLADNIE, KTORE pola
         * "wyciekly" w odpowiedzi JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementNestedDtoForRelatedEntity {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj Response DTO ZAWIERAJACY ZAGNIEZDZONE DTO
         * (np. `OrderDto` z `CustomerSummaryDto` wewnatrz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareManualMappingReadability {
        /*
         * 🧪 Zadanie 9:
         * Napisz mapper RECZNY dla encji Z 8+ polami - oceń CZYTELNOSC
         * wzgledem MAPSTRUCT (`_13_libraries/Lesson21`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDtoRoleInApiVersioning {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_18_rest_api/Lesson14_Versioning`):
         * wyjasnij, jak DTO UMOZLIWIAJA wersjonowanie API BEZ zmiany
         * encji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementDtoWithComputedField {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj Response DTO Z polem OBLICZONYM (nie istniejacym
         * WPROST w encji, np. "wiek konta w dniach").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMultipleEntitiesToOneAggregateDto {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj Response DTO agregujacy dane Z WIELU encji
         * (np. `OrderSummaryDto` = zamowienie + klient + suma pozycji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDtoMapperAsSeparateClass {
        /*
         * 🧪 Zadanie 13:
         * Wydziel mapper (`UserMapper`) jako OSOBNA klase (nie statyczna
         * metode w kontrolerze) - zarejestruj jako `@Component`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementBidirectionalMapping {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj mapper DWUKIERUNKOWY (`toDto`/`toEntity`) dla
         * OPERACJI AKTUALIZACJI (wczytaj encje, ZASTOSUJ DTO, zapisz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPartialUpdateWithOptionalDtoFields {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj Request DTO dla PATCH Z polami `Optional<T>` -
         * zaktualizuj TYLKO OBECNE pola, POZOSTALE zostaw BEZ ZMIAN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDtoForDifferentUserRoles {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj DWA RÓZNE Response DTO dla TEJ SAMEJ encji -
         * `AdminUserDto` (wiecej pol) vs `PublicUserDto` (mniej pol).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDtoValidationSeparateFromEntityInvariants {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z Lesson08 - wyjasnij (i zaimplementuj), CZYM rozni sie
         * walidacja NA DTO (`@NotBlank` na granicy API) od NIEZMIENNIKOW
         * encji (konstruktor rzucajacy wyjatek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDtoToEntityMapperUsingConstructor {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj mapper WYKORZYSTUJACY konstruktor encji
         * (walidujacy niezmienniki), NIE settery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureMappingOverheadForLargeObjectGraphs {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas mapowania encji Z GLEBOKO ZAGNIEZDZONYM grafem
         * obiektow (3+ poziomy) na DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildDtoMappingPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" wzorcow DTO z tej lekcji
         * (Request/Response/agregat/rolowy/czesciowy).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementMapStructBasedMapperForThisLesson {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_13_libraries/Lesson21-22` - przepisz RECZNY mapper
         * z tej lekcji NA interfejs MapStruct - porownaj kod
         * WYGENEROWANY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDtoProjectionDirectlyFromRepository {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_10_dao`/`_12_hibernate` - zaimplementuj PROJEKCJE
         * DTO WPROST z zapytania (bez posredniego ladowania calej encji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDtoVersioningStrategyV1VsV2 {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_18_rest_api/Lesson14` - zaimplementuj `UserDtoV1` i
         * `UserDtoV2` (rozne pola) dla TEJ SAMEJ encji, wybierane
         * DYNAMICZNIE po wersji API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementDtoFieldMaskingBasedOnPermissions {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson07_Rbac` - ZAMASKUJ
         * (wyzeruj/ukryj) WYBRANE pola DTO W ZALEZNOSCI OD uprawnien
         * wywolujacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDtoSchemaValidationAgainstOpenApi {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z Lesson18/`_18_rest_api/Lesson18` - zweryfikuj, ze
         * STRUKTURA DTO ODPOWIADA WYGENEROWANEJ specyfikacji OpenAPI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementDtoWithCircularReferencePrevention {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj DTO ZAPOBIEGAJACY nieskonczonej petli
         * serializacji (np. `OrderDto` <-> `CustomerDto` <-> lista
         * zamowien) - rozwiaz przez ODPOWIEDNI ksztalt DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementDtoAuditTrailComparison {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zaimplementuj
         * PORÓWNANIE starego i nowego DTO PRZED zapisem, zaloguj TYLKO
         * ZMIENIONE pola do dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGenericDtoMapperInterface {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj GENERYCZNY interfejs `Mapper<E, D>` (powiaz z
         * `_14_advancedjava/Lesson02`) - wielokrotnego uzytku dla RÓZNYCH
         * par encja/DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareDtoPatternWithGraphQlFieldSelection {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (powiaz z `_18_rest_api/Lesson19`): porownaj
         * SZTYWNY ksztalt DTO (REST) z DYNAMICZNYM wyborem pol
         * (GraphQL) - jakie SA kompromisy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteDtoLayeredApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne mini-API Z pelna warstwa DTO - Request/
         * Response/rolowy/czesciowy/agregat (Zadania 3/6/16/15/12) -
         * jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
