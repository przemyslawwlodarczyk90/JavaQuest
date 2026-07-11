package com.example.javaquest._23_spring_data_jpa.Lesson11_Projections;

public class _Exercises_Lesson11_Projections {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyProjectionsExist {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego CZASEM lepiej zwrocic
         * PROJEKCJE ZAMIAST calej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnClosedProjection {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY INTERFEJS ZAMKNIETY (2+ metody
         * `getXxx()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnOpenProjection {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY INTERFEJS OTWARTY Z `@Value("#{...}")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementOwnDtoProjection {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNA PROJEKCJE KLASOWA (rekord Z
         * konstruktorem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementDynamicProjectionMethod {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj WLASNA DYNAMICZNA metode `<T> List<T>
         * metoda(..., Class<T> type)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareProjectionWithFullEntity {
        /*
         * 🧪 Zadanie 6:
         * Zestaw obok siebie WYNIK projekcji I calej encji DLA TEGO
         * SAMEGO zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementProjectionWithQueryMethod {
        /*
         * 🧪 Zadanie 7:
         * Powiaz z Lesson04 - uzyj PROJEKCJI Z query method
         * (`findByCategory` zwracajace projekcje ZAMIAST encji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementProjectionWithCustomQuery {
        /*
         * 🧪 Zadanie 8:
         * Powiaz z Lesson05 - uzyj PROJEKCJI Z `@Query` (JPQL zwracajace
         * INTERFEJS projekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementProjectionOnRelatedEntityField {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z Lesson07 - zaimplementuj PROJEKCJE Z POLEM Z
         * POWIAZANEJ encji (`getCustomerName()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainClosedVsOpenSqlOptimizationDifference {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego TYLKO projekcja ZAMKNIETA
         * MOZE zoptymalizowac WYGENEROWANE zapytanie SQL (DO TYLKO
         * potrzebnych kolumn), A OTWARTA NIE.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementNestedClosedProjection {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj ZAGNIEZDZONA projekcje ZAMKNIETA (interfejs
         * ZAWIERAJACY INNY interfejs projekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ComparePageableWithProjection {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z Lesson06 - polacz `Pageable` Z PROJEKCJA -
         * zweryfikuj DZIALANIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementProjectionFactoryProgrammatically {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `ProjectionFactory` (Spring Data) DO RECZNEGO
         * utworzenia projekcji Z ISTNIEJACEJ encji (BEZ zapytania DO
         * bazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareGeneratedSqlForClosedProjection {
        /*
         * 🧪 Zadanie 14:
         * Wlacz `show-sql` - zweryfikuj, CZY projekcja ZAMKNIETA
         * FAKTYCZNIE GENERUJE `SELECT` Z MNIEJSZA liczba kolumn.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementListOfProjectionsMappedToDtoManually {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_22_spring_web/Lesson07` - zmapuj LISTE projekcji
         * NA LISTE WLASNYCH DTO REST API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementProjectionWithAggregateFunction {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z Lesson05 - zaimplementuj PROJEKCJE Z WYNIKIEM
         * funkcji agregujacej (`AVG`/`SUM`) W `@Query`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDtoProjectionWithComputedField {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj DTO Z polem OBLICZONYM (NIE WPROST Z bazy, np.
         * "cena Z VAT").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareOpenProjectionPerformanceCost {
        /*
         * 🧪 Zadanie 18:
         * Zmierz czas WYKONANIA projekcji OTWARTEJ (LADUJE CALA encje)
         * wzgledem ZAMKNIETEJ (TYLKO potrzebne kolumny) DLA 1000
         * rekordow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementProjectionForListEndpointInController {
        /*
         * 🧪 Zadanie 19:
         * Powiaz z `_22_spring_web` - uzyj PROJEKCJI BEZPOSREDNIO jako
         * TYPU zwracanego endpointu REST (BEZ dodatkowego mapowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildProjectionPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" 3 rodzajow projekcji
         * (zamknieta/otwarta/klasowa) + dynamicznej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProjectionForComplexReportingQuery {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj PROJEKCJE DLA ZLOZONEGO raportu (JOIN + agregacja +
         * GROUP BY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementProjectionVersioningForApiEvolution {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_18_rest_api/Lesson14` - zaimplementuj 2 RÓZNE
         * projekcje (V1/V2) DLA TEJ SAMEJ encji, ROZNIACE SIE polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSecurityFilteredProjection {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics/Lesson07_Rbac` - dobierz
         * PROJEKCJE (WIECEJ/MNIEJ pol) W ZALEZNOSCI OD uprawnien
         * wywolujacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementProjectionCachingStrategy {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_13_libraries/Lesson27` - zbuforuj WYNIK
         * projekcji (LZEJSZY obiekt = TANSZE przechowywanie W cache).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementBenchmarkProjectionVsEntityVsNativeQuery {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_15_jvm_internals` - zbuduj benchmark PORÓWNUJACY
         * projekcje/pelna encje/natywne SQL DLA TEGO SAMEGO zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementProjectionWithCollectionField {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj projekcje Z KOLEKCJA (`List<InnaProjekcja>`) -
         * powiaz Z Lesson07 (relacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomProjectionFactoryBean {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala (dokumentacja): sprawdz, JAK dostosowac
         * `SpelAwareProxyProjectionFactory` - KIEDY jest to POTRZEBNE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementProjectionForGraphQlLikeFieldSelection {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala (powiaz z `_18_rest_api/Lesson19`): zaprojektuj
         * mechanizm WYBORU PROJEKCJI NA PODSTAWIE parametru zapytania
         * (`?fields=name,price`) - jak GraphQL, ale W REST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareProjectionsAcrossOrmFrameworks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj PROJEKCJE Spring Data Z PODOBNYMI
         * mechanizmami W innych ORM (np. `select()` W jOOQ/MyBatis
         * resultMap).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiViewApiWithProjectionsCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE API Z WIELOMA "widokami" TEGO SAMEGO zasobu
         * (podsumowanie/szczegoly/administracyjny) PRZEZ RÓZNE
         * projekcje - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
