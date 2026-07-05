package com.example.javaquest._12_hibernate.Lesson21_NativeSqlQueries;

public class _Exercises_Lesson21_NativeSqlQueries {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicNativeQueryToEntity {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Product (id, name, price) na H2
         * ("jdbc:h2:mem:l21ex01;DB_CLOSE_DELAY=-1"). Zapisz 3 produkty. Uzyj
         * createNativeQuery("select * from product", Product.class) zeby odczytac
         * WSZYSTKIE jako encje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NativeQueryWithWhere {
        /*
         * 🧪 Zadanie 2:
         * Napisz native query z WHERE ("select * from product where price > ?1",
         * Product.class) i parametrem pozycyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NativeQueryWithNamedParameter {
        /*
         * 🧪 Zadanie 3:
         * Ta sama funkcjonalnosc, ale z parametrem NAZWANYM (":minPrice" zamiast "?1").
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NativeQueryReturningScalar {
        /*
         * 🧪 Zadanie 4:
         * Uzyj createNativeQuery("select count(*) from product") BEZ podania klasy
         * encji - zwroci Object (liczbe). Wypisz wynik po rzutowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NativeQueryWithSingleColumn {
        /*
         * 🧪 Zadanie 5:
         * Uzyj createNativeQuery("select name from product", String.class) - zwroc
         * TYLKO nazwy produktow jako liste Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExecuteUpdateNativeSql {
        /*
         * 🧪 Zadanie 6:
         * Uzyj createNativeQuery("update product set price = price * 1.1").executeUpdate()
         * (bez SELECT) - zweryfikuj podwyzszenie wszystkich cen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NativeQueryWithOrderBy {
        /*
         * 🧪 Zadanie 7:
         * Napisz native query z ORDER BY - odczytaj produkty posortowane po cenie
         * malejaco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_H2SpecificFunctionUpper {
        /*
         * 🧪 Zadanie 8:
         * Uzyj native query z funkcja SQL UPPER() na nazwie produktu - zwroc nazwy
         * WIELKIMI LITERAMI bezposrednio z zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_H2SpecificFunctionFormatDatetime {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do Product pole createdAt (LocalDateTime). Uzyj natywnej funkcji H2
         * FORMATDATETIME() w native query zeby sformatowac date wedlug wlasnego wzorca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareNativeAndHqlResultsIdentical {
        /*
         * 🧪 Zadanie 10:
         * Napisz TO SAMO zapytanie (filtr po cenie) RAZ jako native SQL, RAZ jako
         * HQL (podglad z Lesson18) - porownaj oba wyniki (powinny byc IDENTYCZNE).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SqlResultSetMappingBasic {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj @SqlResultSetMapping z @ColumnResult dla 2 kolumn (name, price).
         * Uzyj go w createNativeQuery(sql, "nazwaMapowania") - zwerfyfikuj wynik
         * jako Object[].
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NativeQueryWithJoin {
        /*
         * 🧪 Zadanie 12:
         * Dodaj Category (@ManyToOne w Product). Napisz native SQL z REALNYM JOIN
         * (nie HQL "join") laczacym product i category - zwroc kombinacje nazwa
         * produktu + nazwa kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NativeQueryWithGroupByHaving {
        /*
         * 🧪 Zadanie 13:
         * Napisz native SQL z GROUP BY i HAVING - "kategorie z wiecej niz 2
         * produktami" - zwerfyfikuj wynik zgodny z odpowiednikiem HQL z Lesson19.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NativeQueryPortabilityRisk {
        /*
         * 🧪 Zadanie 14:
         * Uzyj funkcji SQL specyficznej DLA H2 (np. LISTAGG lub GROUP_CONCAT - sprawdz
         * co dostepne w Twojej wersji H2). Zapisz w komentarzu, ze RUZNE bazy maja
         * ROZNE nazwy tej samej funkcji (np. PostgreSQL: string_agg).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NativeQueryWithLimit {
        /*
         * 🧪 Zadanie 15:
         * Napisz native SQL z LIMIT (skladnia specyficzna, nie standardowa) - "top 3
         * najdrozsze produkty" - porownaj z setMaxResults(3) na HQL (przenosne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_StoredProcedureLikeQuery {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj prosta funkcje SQL w H2 (CREATE ALIAS lub CREATE FUNCTION,
         * sprawdz skladnie H2) i wywolaj ja przez native query - zapisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConstructorResultForDto {
        /*
         * 🧪 Zadanie 17:
         * Utworz record ProductSummary(String name, double price). Uzyj
         * @SqlResultSetMapping z @ConstructorResult (podglad zaawansowany) zeby
         * zmapowac wynik native query BEZPOSREDNIO na obiekt ProductSummary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NativeQueryVsCriteriaComparison {
        /*
         * 🧪 Zadanie 18:
         * Napisz TO SAMO zapytanie (filtr + sort) RAZ jako native SQL, RAZ jako
         * Criteria API (Lesson20) - porownaj w komentarzu zlozonosc/czytelnosc obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NativeQueryWithCteCommonTableExpression {
        /*
         * 🧪 Zadanie 19:
         * Napisz native SQL z WITH (Common Table Expression, wspierane przez H2) -
         * konstrukcja NIEDOSTEPNA w standardowym HQL - zwerfyfikuj poprawnosc dzialania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExceptionHandlingInvalidNativeSql {
        /*
         * 🧪 Zadanie 20:
         * Napisz CELOWO bledny SQL (literowka w nazwie tabeli) w native query.
         * Opakuj w try-catch i zapisz w komentarzu typ wyjatku (rozny od bledow HQL
         * - to blad bazy danych, nie parsera HQL).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ComplexAnalyticalReportNativeSql {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj RAPORT analityczny wymagajacy funkcji okienkowej SQL (window
         * function, np. RANK() OVER (...)) - NIEDOSTEPNEJ w standardowym HQL -
         * zademonstruj ranking produktow wedlug ceny w ramach kazdej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_HybridApproachNativeForReadHqlForWrite {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj serwis uzywajacy NATIVE SQL dla skomplikowanych raportow
         * (odczyt) i HQL/encje dla zwyklego CRUD (zapis) - zademonstruj OBIE sciezki
         * w jednym scenariuszu biznesowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DatabaseSpecificDialectSwitchSimulation {
        /*
         * 🧪 Zadanie 23:
         * Napisz DWIE wersje TEGO SAMEGO native query - jedna zgodna ze skladnia H2,
         * druga (jako komentarz, bez wykonania) zgodna z PostgreSQL - pokaz roznice
         * skladniowe dla tej samej logiki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceNativeVsHqlForComplexReport {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj zlozony raport (agregacje + JOIN + GROUP BY) i zmierz czas
         * wykonania RAZ jako native SQL, RAZ jako rownowazny HQL - zapisz oba czasy w
         * komentarzu (dla H2 roznica moze byc minimalna, ale opisz JAK sprawdzic to
         * na produkcyjnej bazie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NativeQueryWithMultipleResultSetMappings {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj DWIE rozne @SqlResultSetMapping na tej samej encji, dla DWOCH
         * roznych "ksztaltow" wynikow (np. pelne dane vs tylko podsumowanie) - uzyj
         * obu w roznych metodach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SafeguardingAgainstSqlInjectionInNativeQuery {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj metode przyjmujaca "niebezpieczny" input uzytkownika (String z
         * cudzyslowami/apostrofami) i zademonstruj, ze uzycie PARAMETRU (?1/:name) w
         * native query jest bezpieczne, w odroznieniu od sklejania Stringow w SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigratingLegacyStoredProcedureCall {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj wywolanie "starej procedury skladowanej" (uzyj prostej funkcji H2
         * z Zadania 16 jako namiastki) przez
         * session.createStoredProcedureQuery(...) (jesli dostepne) lub native query -
         * zademonstruj integracje z istniejaca logika bazy danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullReportingModuleWithNativeAndHqlMix {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj klase "ProductReportService" z 4 metodami - 2 uzywajace native SQL
         * (dla funkcji specyficznych H2), 2 uzywajace HQL (dla przenosnej logiki
         * biznesowej) - z komentarzem uzasadniajacym KAZDY wybor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnNativeSqlTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy native SQL jest UZASADNIONYM
         * wyborem mimo utraty przenosnosci - z konkretnym przykladem z Twojego
         * doswiadczenia lub wyobrazonego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullNativeSqlCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY modul raportowy dla sklepu
         * laczacy: mapowanie na encje (Zadanie 1), @SqlResultSetMapping dla DTO
         * (Zadanie 17), funkcje okienkowe (Zadanie 21), i bezpieczne parametryzowane
         * zapytania (Zadanie 26) - w jednej, spojnej klasie serwisowej.
         */
        public static void main(String[] args) { }
    }
}
