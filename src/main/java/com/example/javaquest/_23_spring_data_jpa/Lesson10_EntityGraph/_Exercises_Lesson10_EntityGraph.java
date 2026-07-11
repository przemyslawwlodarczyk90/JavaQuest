package com.example.javaquest._23_spring_data_jpa.Lesson10_EntityGraph;

public class _Exercises_Lesson10_EntityGraph {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainEntityGraphVsJoinFetch {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z Lesson09): wyjasnij, CZYM
         * `@EntityGraph` rozni sie OD `JOIN FETCH` W `@Query`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnAdHocEntityGraph {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY `@EntityGraph(attributePaths = {...})`
         * NA metodzie repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnNamedEntityGraph {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY `@NamedEntityGraph` NA encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareQueryCountWithAndWithoutEntityGraph {
        /*
         * 🧪 Zadanie 4:
         * Powiaz z Lesson09 (`StatementInspector`) - porownaj LICZBE
         * zapytan Z I BEZ `@EntityGraph`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementEntityGraphOnFindById {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `@EntityGraph` NA WLASNYM `findById(...)`
         * (PRZESLONIETYM Z JpaRepository).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementMultipleAttributePaths {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `attributePaths = {"pole1", "pole2"}` - ZALADUJ 2 RÓZNE
         * relacje NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementNestedAttributePath {
        /*
         * 🧪 Zadanie 7:
         * Uzyj `attributePaths = {"orders.items"}` - ZALADUJ
         * ZAGNIEZDZONA relacje (2 poziomy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementEntityGraphTypeLoad {
        /*
         * 🧪 Zadanie 8:
         * Uzyj `type = EntityGraphType.LOAD` (DOMYSLNY) - wyjasnij
         * RÓZNICE wzgledem `FETCH`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementEntityGraphTypeFetch {
        /*
         * 🧪 Zadanie 9:
         * Uzyj `type = EntityGraphType.FETCH` - zweryfikuj RÓZNICE W
         * zachowaniu (POLA NIE WYMIENIONE W grafie staja sie LAZY,
         * NIEZALEZNIE OD ich DOMYSLNEGO fetch type).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToPreferEntityGraphOverJoinFetch {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, KIEDY `@EntityGraph` jest
         * WYGODNIEJSZY niz `JOIN FETCH` (np. GDY metoda JUZ MA WBUDOWANE
         * `@Query`/query method).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementEntityGraphWithPagination {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z Lesson06 - polacz `@EntityGraph` Z `Pageable` -
         * zweryfikuj, CZY WYSTEPUJE OSTRZEZENIE (podobne DO
         * `JOIN FETCH` + `Pageable` Z Lesson09, Zadanie 16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSubgraphForDeepNesting {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@NamedSubgraph` DLA ZAGNIEZDZONEJ relacji Z WLASNYMI
         * atrybutami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDynamicEntityGraphViaJpaEntityGraph {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `JpaEntityGraph`/programowego budowania grafu (Spring
         * Data) ZAMIAST STATYCZNEJ adnotacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementMultipleEntityGraphsOnSameEntity {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj 2 RÓZNE `@NamedEntityGraph` NA TEJ SAMEJ encji -
         * DLA RÓZNYCH scenariuszy uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementEntityGraphWithQueryMethod {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z Lesson04 - dodaj `@EntityGraph` DO ISTNIEJACEJ
         * query method (`findByCategory`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareEntityGraphImpactOnGeneratedSql {
        /*
         * 🧪 Zadanie 16:
         * Wlacz `show-sql` - PORÓWNAJ WYGENEROWANY SQL Z `@EntityGraph`
         * Z RECZNYM `JOIN FETCH`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementEntityGraphOverridingDefaultFetchType {
        /*
         * 🧪 Zadanie 17:
         * Zweryfikuj, ze `@EntityGraph` MOZE "WYMUSIC" EAGER LADOWANIE
         * DLA pola, KTORE domyslnie jest LAZY (BEZ zmiany adnotacji NA
         * encji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareEntityGraphReadabilityWithComplexJoinFetch {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: NAPISZ TO SAMO zapytanie (3 relacje) OBOMA
         * sposobami - OCEN CZYTELNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureEntityGraphPerformanceEquivalence {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas WYKONANIA `@EntityGraph` wzgledem RECZNEGO `JOIN
         * FETCH` - zweryfikuj, ze WYDAJNOSC jest RÓWNOWAZNA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildEntityGraphPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" wzorcow `@EntityGraph`
         * (ad-hoc/nazwany/zagniezdzony/Z Pageable).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementConditionalEntityGraphSelection {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WYBOR RÓZNEGO `@EntityGraph` W ZALEZNOSCI OD
         * PARAMETRU wywolania (np. "pelny widok" vs "podstawowy widok").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementEntityGraphWithCriteriaApi {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_12_hibernate/Lesson20` - uzyj grafu encji Z
         * Criteria API (BEZ Spring Data, poziom "golego" JPA).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEntityGraphForApiResponseOptimization {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_22_spring_web` - dobierz `@EntityGraph` W
         * ZALEZNOSCI OD tego, JAKIE pola ZADA klient (np. przez
         * `?include=orders`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementEntityGraphCachingConsiderations {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_12_hibernate/Lesson24` - wyjasnij interakcje
         * MIEDZY `@EntityGraph` A drugopoziomowym cache.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementEntityGraphMetricsComparison {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_21_spring_boot/Lesson13` - zbierz metryki
         * (LICZBA zapytan/czas) DLA endpointow UZYWAJACYCH
         * `@EntityGraph` wzgledem tych, KTORE go NIE UZYWAJA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementEntityGraphForReportGeneration {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj RAPORT (3+ POZIOMY relacji) UZYWAJACY `@EntityGraph`
         * NA KAZDYM poziomie - JEDNO zapytanie, KOMPLETNY graf danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementEntityGraphVersusDtoProjectionTradeoffs {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z Lesson11 - zestaw KOMPROMISY `@EntityGraph`
         * (pelne encje) wzgledem PROJEKCJI DTO (TYLKO potrzebne pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementLargeGraphMemoryFootprintAnalysis {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_15_jvm_internals` - zmierz ZUZYCIE pamieci PRZY
         * ZALADOWANIU DUZEGO grafu encji PRZEZ `@EntityGraph`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareEntityGraphAcrossJpaProviders {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: sprawdz, CZY `@EntityGraph`/`@NamedEntityGraph`
         * SA CZESCIA STANDARDU JPA (przenosne MIEDZY dostawcami) CZY
         * SPECYFICZNE DLA Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteOptimizedGraphLoadingApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE API Z ZOPTYMALIZOWANYM ladowaniem grafow
         * (`@EntityGraph` PER endpoint) - zweryfikowane
         * `StatementInspector` DLA KAZDEGO endpointu - jeden spojny
         * system.
         */
        public static void main(String[] args) { }
    }
}
