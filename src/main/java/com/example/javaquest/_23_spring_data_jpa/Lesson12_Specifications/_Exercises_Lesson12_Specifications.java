package com.example.javaquest._23_spring_data_jpa.Lesson12_Specifications;

public class _Exercises_Lesson12_Specifications {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSpecificationPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_22_spring_web/Lesson13`): wyjasnij,
         * CZYM `Specification<T>` rozni sie OD RECZNEGO `Predicate<T>`
         * W PAMIECI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnSingleSpecification {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA `Specification<T>` (1 warunek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementJpaSpecificationExecutor {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `JpaSpecificationExecutor<T>` DO WLASNEGO repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementAndCombination {
        /*
         * 🧪 Zadanie 4:
         * Polacz 2 `Specification` PRZEZ `.and(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementOrCombination {
        /*
         * 🧪 Zadanie 5:
         * Polacz 2 `Specification` PRZEZ `.or(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementNotSpecification {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `Specification.not(...)` DO negacji warunku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementDynamicOptionalFilterBuilder {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj WLASNY builder LACZACY 3+ opcjonalne filtry.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareGeneratedSqlForCombinedSpecifications {
        /*
         * 🧪 Zadanie 8:
         * Wlacz `show-sql` - zweryfikuj WYGENEROWANY `WHERE` DLA
         * POLACZONYCH `Specification`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementSpecificationWithPageable {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z Lesson06 - uzyj `findAll(Specification, Pageable)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToPreferSpecificationOverQueryMethod {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, KIEDY `Specification` JEST LEPSZYM
         * wyborem NIZ query method (Lesson04) LUB `@Query` (Lesson05).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementSpecificationWithJoin {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z Lesson07 - zaimplementuj `Specification` Z `JOIN`
         * DO POWIAZANEJ encji (`root.join("customer")`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSpecificationWithSubquery {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `Specification` Z PODZAPYTANIEM
         * (`query.subquery(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementSpecificationWithFetch {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z Lesson09/10 - dolacz `root.fetch("orders")` W
         * `Specification` (rozwiazanie N+1 RAZEM Z DYNAMICZNYMI
         * filtrami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSpecificationWithDistinct {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `query.distinct(true)` W `Specification` (przydatne PRZY
         * JOIN Z kolekcja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementSpecificationReusableComponents {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj BIBLIOTEKE WIELOKROTNEGO uzytku malych `Specification`
         * (KAZDA - 1 warunek), SKLADANYCH W RÓZNYCH kombinacjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSpecificationWithDateRange {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj `Specification` FILTRUJACA PO zakresie dat
         * (`between`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementSpecificationForRestApiSearchEndpoint {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_22_spring_web` - zbuduj endpoint REST
         * PRZYJMUJACY WIELE opcjonalnych `@RequestParam`, BUDUJACY
         * `Specification` DYNAMICZNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareSpecificationWithQueryDsl {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): porownaj `Specification`
         * (Criteria API) Z QueryDSL - jakie SA RÓZNICE skladni/
         * bezpieczenstwa TYPOW?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureSpecificationOverheadVsStaticQuery {
        /*
         * 🧪 Zadanie 19:
         * Zmierz narzut DYNAMICZNEGO budowania `Specification` wzgledem
         * STATYCZNEGO `@Query`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSpecificationPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" wzorcow `Specification`
         * (and/or/not/join/subquery/pageable).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementGenericSpecificationBuilder {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj GENERYCZNY builder `Specification<T>` (powiaz
         * Z `_14_advancedjava/Lesson02`) DZIALAJACY DLA DOWOLNEJ encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementSpecificationFromJsonQueryLanguage {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj parser PROSTEGO jezyka zapytan (JSON
         * `{"field":"price","op":"gt","value":100}`) NA
         * `Specification<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSecurityFilteredSpecification {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics/Lesson07_Rbac` - DOLACZ
         * AUTOMATYCZNIE `Specification` OGRANICZAJACA WYNIKI DO danych
         * DOZWOLONYCH DLA zalogowanego uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSpecificationSqlInjectionSafety {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson13` - zweryfikuj, ze
         * WARTOSCI W `Specification` (Criteria API) SA ZAWSZE
         * bindowane (BEZPIECZNE PRZED SQL injection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSpecificationCachingStrategy {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_13_libraries/Lesson27` - zbuforuj WYNIK
         * CZESTO powtarzanej kombinacji `Specification`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSpecificationWithProjectionCombined {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z Lesson11 - polacz `Specification` (dynamiczne
         * filtrowanie) Z PROJEKCJA (TYLKO potrzebne pola) - zweryfikuj
         * KOMPATYBILNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSpecificationAuditLogging {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj UZYTA
         * kombinacje filtrow (BEZ wrazliwych wartosci) DO dziennika
         * audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFullTextSearchViaSpecification {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj PROSTE wyszukiwanie pelnotekstowe (WIELE pol
         * NARAZ, `LIKE` LUB `OR` na WIELU kolumnach) PRZEZ
         * `Specification`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareSpecificationsAcrossDatabaseDialects {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: wyjasnij, DLACZEGO `Specification` (Criteria
         * API) jest PRZENOSNA MIEDZY bazami danych, W ODROZNIENIU OD
         * `@Query(nativeQuery = true)` Z Lesson05.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAdvancedSearchEngineWithSpecificationsCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY "silnik wyszukiwania" - DYNAMICZNE
         * `Specification` + stronicowanie (Lesson06) + projekcje
         * (Lesson11) - jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
