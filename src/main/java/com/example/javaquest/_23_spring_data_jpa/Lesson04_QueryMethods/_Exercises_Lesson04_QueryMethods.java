package com.example.javaquest._23_spring_data_jpa.Lesson04_QueryMethods;

public class _Exercises_Lesson04_QueryMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainQueryMethodParsing {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, JAK Spring Data PARSUJE nazwe
         * metody NA zapytanie JPQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSimpleFindByField {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA metode `findByPole(...)` DLA WLASNEJ
         * encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementFindByTwoFieldsAnd {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `findByPoleAndInnePole(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementFindByTwoFieldsOr {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `findByPoleOrInnePole(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementGreaterThanLessThan {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `findByPoleGreaterThan(...)` I
         * `findByPoleLessThan(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementContainingStartingEndingWith {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj `findByPoleContaining`/`StartingWith`/
         * `EndingWith`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementCountByAndExistsBy {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj `countByPole(...)` I `existsByPole(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementOrderByAscDesc {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj `findByPoleOrderByInnePoleAsc/Desc(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementIsNullIsNotNull {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj `findByPoleIsNull()`/`findByPoleIsNotNull()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenNameGetsTooLongForMethod {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (zapowiedz Lesson05): wyjasnij, KIEDY nazwa
         * metody staje sie ZBYT DLUGA/nieczytelna I trzeba przejsc NA
         * `@Query`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementBetweenForDateRange {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `findByDataBetween(...)` (`LocalDate`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFindFirstOrderBy {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `findFirstByPoleOrderByInnePoleAsc(...)` -
         * zwroc TYLKO 1 wynik (`Optional<T>`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementNotEqualOperator {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `findByPoleNot(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementInAndNotInOperators {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj `findByPoleIn(...)` I `findByPoleNotIn(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementTrueFalseBooleanQuery {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj `findByAktywnyTrue()`/`findByAktywnyFalse()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDeleteByQueryMethod {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj `deleteByPole(...)` - powiaz z Lesson03
         * (zachowanie usuniecia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementNestedPropertyQuery {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z Lesson07 (relacje) - zaimplementuj
         * `findByPowiazanaEncja_Pole(...)` (nawigacja PRZEZ relacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDistinctQuery {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj `findDistinctByPole(...)` - powiaz z
         * `_03_collections` (unikalnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureQueryMethodPerformanceVsManualJpql {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas wykonania query method wzgledem RECZNEGO HQL
         * (`_12_hibernate/Lesson18`) DLA TEGO SAMEGO zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildQueryMethodKeywordsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" WSZYSTKICH slow kluczowych query
         * methods Z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementComplexMultiConditionQuery {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj ZLOZONE zapytanie Z 4+ warunkami
         * (And/Or/GreaterThan/Containing NARAZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementQueryMethodWithPageable {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z Lesson06 - zaimplementuj `findByPole(String pole,
         * Pageable pageable)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementQueryMethodReturningProjectionInterface {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z Lesson11 - zaimplementuj query method zwracajaca
         * INTERFEJS projekcji (NIE calej encji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementQueryMethodPerformanceRegressionTest {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj TEST WYKRYWAJACY, ze WYGENEROWANE zapytanie NIE
         * ZMIENILO SIE W SPOSOB pogarszajacy wydajnosc (POWIAZANIE Z
         * `_15_jvm_internals`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementQueryMethodEdgeCaseWithSpecialCharacters {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics/Lesson13` - zweryfikuj, CZY
         * query methods SA BEZPIECZNE PRZED SQL injection (parametry
         * SA AUTOMATYCZNIE bindowane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAsyncQueryMethod {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_05_multithreading/Lesson32_CompletableFuture` -
         * zaimplementuj `@Async` metode repozytorium zwracajaca
         * `CompletableFuture<List<T>>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementStreamableQueryMethod {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj metode zwracajaca `Streamable<T>` (Spring Data)
         * - powiaz Z `Stream<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementQueryMethodWithLimit {
        /*
         * 🧪 Zadanie 28:
         * Uzyj `Limit` (Spring Data 3.2+, `findByPole(String pole,
         * Limit limit)`) - powiaz Z `findTopN...`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareQueryMethodReadabilityAtScale {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: NAPISZ 10 RÓZNYCH query methods DLA JEDNEJ
         * encji - OCEN CZYTELNOSC interfejsu PRZY DUZEJ liczbie metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAdvancedSearchQueryMethodsCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj repozytorium Z 8+ query methods POKRYWAJACYMI
         * WSZYSTKIE poznane operatory - jeden spojny, kompletny
         * interfejs wyszukiwania.
         */
        public static void main(String[] args) { }
    }
}
