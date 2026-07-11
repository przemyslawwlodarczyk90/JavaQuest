package com.example.javaquest._23_spring_data_jpa.Lesson06_PaginationAndSorting;

public class _Exercises_Lesson06_PaginationAndSorting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPageableVsManualPagination {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_22_spring_web/Lesson12`): wyjasnij,
         * CZYM `Pageable` rozni sie OD RECZNEGO stronicowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnPageableQueryMethod {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA metode `findByPole(String pole,
         * Pageable pageable)` zwracajaca `Page<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementPageRequestOf {
        /*
         * 🧪 Zadanie 3:
         * Uzyj `PageRequest.of(numer, rozmiar)` DO pobrania KONKRETNEJ
         * strony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementSortByOneField {
        /*
         * 🧪 Zadanie 4:
         * Uzyj `Sort.by("pole")` W `PageRequest.of(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementSortDirection {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `Sort.by(Sort.Direction.DESC, "pole")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InspectPageMetadata {
        /*
         * 🧪 Zadanie 6:
         * Wypisz WSZYSTKIE metadane `Page<T>` (`getNumber()`/`getSize()`/
         * `getTotalElements()`/`getTotalPages()`/`isFirst()`/`isLast()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementSliceQueryMethod {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj WLASNA metode zwracajaca `Slice<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareEmptyPageVsOutOfRangePage {
        /*
         * 🧪 Zadanie 8:
         * Zazadaj strony POZA zakresem - zweryfikuj, ze `Page<T>` MA
         * PUSTA `content`, NIE wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementFindAllWithPageable {
        /*
         * 🧪 Zadanie 9:
         * Uzyj WBUDOWANEJ `findAll(Pageable)` (Z `JpaRepository`, BEZ
         * WLASNEJ metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySortIsSeparateFromPageable {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `Sort` MOZE byc uzyty
         * NIEZALEZNIE OD `Pageable` (np. `findAll(Sort)` BEZ
         * stronicowania).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementMultiFieldSortChain {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `Sort.by(...).and(Sort.by(...))` Z 3+ polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPageableFromRestController {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_22_spring_web` - zaimplementuj kontroler
         * PRZYJMUJACY `Pageable` BEZPOSREDNIO jako parametr metody
         * (Spring MVC AUTOMATYCZNIE parsuje `?page=`/`?size=`/`?sort=`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementPageMapToDto {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `page.map(entity -> toDto(entity))` - przeksztalc
         * `Page<Entity>` NA `Page<Dto>` BEZ utraty metadanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementUnpagedRequest {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `Pageable.unpaged()` - zweryfikuj, ze ZWRACA
         * WSZYSTKIE wyniki (BEZ stronicowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPageableWithQueryAnnotation {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z Lesson05 - dodaj `Pageable` DO metody `@Query` (Z
         * `countQuery` DLA natywnego zapytania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureCountQueryOverheadOnPage {
        /*
         * 🧪 Zadanie 16:
         * Wlacz `show-sql` I zweryfikuj, ILE zapytan GENERUJE `Page<T>`
         * (2: dane + COUNT) wzgledem `Slice<T>` (1: TYLKO dane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCustomPageImplConstruction {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj RECZNIE `Page<T>` (przez `PageImpl<>`) Z listy JUZ
         * POSIADANEJ W pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementPageableDefaultInController {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_22_spring_web` - uzyj `@PageableDefault(size = 20)`
         * NA parametrze kontrolera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureLargeOffsetPerformanceDegradation {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas zapytania DLA MALEGO `OFFSET` (strona 0) wzgledem
         * DUZEGO (strona 1000) - powiaz Z RÓZNICA offset/limit vs
         * kursorowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildPageableSortCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" `Pageable`/`Sort`/`Page`/`Slice`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementKeysetPaginationWithSpringData {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala (dokumentacja): sprawdz `ScrollPosition`/
         * `Window<T>` (Spring Data 3+) - stronicowanie "keyset" ZAMIAST
         * offset/limit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementPageableValidationWithMaxSize {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_22_spring_web/Lesson08` - zwaliduj, ze `size`
         * PRZEKAZANY OD klienta NIE PRZEKRACZA rozsadnego limitu (np.
         * 100).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementPageableWithProjectionCombined {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z Lesson11 - polacz `Pageable` Z PROJEKCJA
         * (interfejsem) ZAMIAST calej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCachingOfExpensivePagedQueries {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_13_libraries/Lesson27` - zbuforuj WYNIK DLA
         * KONKRETNEJ kombinacji `Pageable` (KLUCZ cache'a).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRestApiWithFullPaginationEnvelope {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_22_spring_web/Lesson12` - zbuduj kontroler
         * zwracajacy `Page<Dto>` BEZPOSREDNIO jako JSON (Jackson
         * SERIALIZUJE `Page` DOMYSLNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSortWhitelistToPreventInjection {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson13` - ZWALIDUJ nazwe
         * pola sortowania OD klienta WZGLEDEM "bialej listy"
         * DOZWOLONYCH pol (zapobiega manipulacji zapytaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementParallelPageFetchingForReport {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_05_multithreading` - pobierz WIELE stron
         * ROWNOLEGLE (osobne watki) DLA raportu WYMAGAJACEGO calego
         * zbioru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementPageableWithMultiTenantFiltering {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_17_architecture/Lesson06_BoundedContexts` - dodaj
         * FILTR "tenant" DO KAZDEGO zapytania stronicowanego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareSpringDataPaginationWithManualImplementation {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj kod POTRZEBNY DO stronicowania W
         * `_22_spring_web/Lesson12` (RECZNE) Z tym rozdzialem
         * (`Pageable`) - ILE kodu OSZCZEDZA Spring Data?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSortableFilterablePaginatedApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE API Z pelnym stronicowaniem+sortowaniem+
         * walidacja (Zadanie 22) + biala lista sortowania (Zadanie 26)
         * - jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
