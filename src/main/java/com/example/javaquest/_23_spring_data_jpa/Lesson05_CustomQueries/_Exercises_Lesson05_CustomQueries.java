package com.example.javaquest._23_spring_data_jpa.Lesson05_CustomQueries;

public class _Exercises_Lesson05_CustomQueries {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhenToUseQueryAnnotation {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z Lesson04): wyjasnij, KIEDY nazwa
         * metody PRZESTAJE wystarczac I trzeba uzyc `@Query`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnJpqlQuery {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNE zapytanie `@Query` W JPQL Z `@Param`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnNativeQuery {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNE zapytanie `@Query(nativeQuery = true)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementPositionalParameters {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj zapytanie Z parametrami POZYCYJNYMI (`?1`, `?2`)
         * ZAMIAST nazwanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementModifyingUpdateQuery {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj WLASNE `@Modifying @Query("UPDATE ...")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementModifyingDeleteQuery {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj WLASNE `@Modifying @Query("DELETE ...")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TriggerModifyingWithoutTransactionalAndObserve {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj `@Modifying` zapytanie BEZ jawnej transakcji - zapisz
         * DOKLADNE zachowanie (wyjatek CZY dzialanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementJpqlJoinQuery {
        /*
         * 🧪 Zadanie 8:
         * Powiaz z Lesson07 (relacje) - zaimplementuj `@Query` Z `JOIN`
         * miedzy 2 encjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareJpqlWithNativeSqlSyntax {
        /*
         * 🧪 Zadanie 9:
         * Zestaw obok siebie TO SAMO zapytanie W JPQL I SQL - JAKIE SA
         * ROZNICE skladni?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyNativeQueryLimitsPortability {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `nativeQuery = true`
         * OGRANICZA PRZENOSNOSC MIEDZY RÓZNYMI bazami danych.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementQueryReturningDtoViaConstructorExpression {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `@Query("SELECT new pakiet.Dto(p.name, p.price)
         * FROM Product p")` - powiaz Z `_12_hibernate/Lesson19`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCountQuery {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `@Query` Z funkcja agregujaca `COUNT`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementSubqueryInJpql {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `@Query` Z PODZAPYTANIEM (`WHERE ... IN (SELECT
         * ...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementNativeQueryWithResultSetMapping {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z `_12_hibernate/Lesson21` - zaimplementuj natywne
         * zapytanie Z `@SqlResultSetMapping`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementModifyingWithClearAutomatically {
        /*
         * 🧪 Zadanie 15:
         * Dodaj `@Modifying(clearAutomatically = true)` - wyjasnij (i
         * zademonstruj), CO to ROBI Z PIERWSZOPOZIOMOWYM cache (powiaz
         * Z `_12_hibernate/Lesson23`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementQueryWithLikeOperator {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj `@Query` Z operatorem `LIKE` I
         * konkatenacja `CONCAT('%', :fragment, '%')`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementQueryReturningOptional {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj `@Query` zwracajace `Optional<T>` ZAMIAST
         * `List<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareNamedQueryWithInlineQuery {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z Lesson09 (`@NamedQuery`) - porownaj `@Query`
         * WPISANE BEZPOSREDNIO W repozytorium Z `@NamedQuery` NA
         * encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureNativeQueryVsJpqlPerformance {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas WYKONANIA TEGO SAMEGO zapytania JAKO JPQL wzgledem
         * SQL natywnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildCustomQueryPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" wzorcow `@Query`
         * (JPQL/natywne/modyfikujace/DTO/podzapytania).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDynamicNativeQueryWithSpEL {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_20_spring_core/Lesson17_SpelBasics` - uzyj
         * wyrazenia SpEL (`#{#entityName}`) W `@Query`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementBulkUpdatePerformanceComparison {
        /*
         * 🧪 Zadanie 22:
         * Zmierz czas AKTUALIZACJI 1000 wierszy PRZEZ `@Modifying
         * @Query` (JEDNO zapytanie) wzgledem PETLI `save()` (Lesson03).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSqlInjectionSafeDynamicQuery {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics/Lesson13` - zweryfikuj, ze
         * PARAMETRY `@Query` SA BEZPIECZNE PRZED SQL injection (ZAWSZE
         * bindowane, NIGDY konkatenowane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementNativeQueryWithPagination {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z Lesson06 - zaimplementuj `@Query(nativeQuery = true,
         * countQuery = "...")` DLA stronicowania natywnego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementStoredProcedureCall {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala (dokumentacja): sprawdz `@Procedure` - JAK
         * WYWOLAC procedure skladowana PRZEZ Spring Data JPA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementQueryHintsForPerformance {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_12_hibernate/Lesson23` - dodaj `@QueryHints`
         * (np. `org.hibernate.readOnly`) DO metody repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAuditedBulkUpdateWithLogging {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj LICZBE
         * zmienionych wierszy PO KAZDYM `@Modifying` zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementConditionalQueryWithSpecificationFallback {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z Lesson12 (`Specification`) - porownaj `@Query` Z
         * WIELOMA opcjonalnymi WARUNKAMI (skomplikowane) Z
         * `Specification` (elastyczniejsze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareQueryMaintainabilityAtScale {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: NAPISZ repozytorium Z 10+ metodami `@Query` -
         * OCEN CZYTELNOSC/UTRZYMYWALNOSC PRZY DUZEJ skali.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteReportingQueriesCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY zestaw zapytan RAPORTUJACYCH (agregacje,
         * joiny, DTO, natywne) DLA WYMYSLONEGO systemu magazynowego -
         * jeden spojny zestaw.
         */
        public static void main(String[] args) { }
    }
}
