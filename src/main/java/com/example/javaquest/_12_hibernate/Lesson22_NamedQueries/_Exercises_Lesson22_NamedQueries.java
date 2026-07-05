package com.example.javaquest._12_hibernate.Lesson22_NamedQueries;

public class _Exercises_Lesson22_NamedQueries {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicNamedQuery {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Customer (id, name, city) z @NamedQuery(name =
         * "Customer.findByCity", query = "from Customer c where c.city = :city") na
         * H2 ("jdbc:h2:mem:l22ex01;DB_CLOSE_DELAY=-1"). Zapisz 3 klientow i uzyj
         * nazwanego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NamedQueryWithMultipleParameters {
        /*
         * 🧪 Zadanie 2:
         * Dodaj drugie @NamedQuery z DWOMA parametrami (np. city i min. dlugosc
         * nazwy). Uzyj setParameter() dwukrotnie przed getResultList().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NamedQueriesAnnotationForMultiple {
        /*
         * 🧪 Zadanie 3:
         * Uzyj @NamedQueries({...}) zeby zdefiniowac DWA rozne named query na JEDNEJ
         * encji naraz. Wywolaj oba i porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NamedQueryTypoDetectedAtStartup {
        /*
         * 🧪 Zadanie 4:
         * Wprowadz CELOWO literowke w nazwie pola w @NamedQuery (np. "c.ciyt" zamiast
         * "c.city"). Zbuduj SessionFactory i zapisz w komentarzu blad, jaki
         * wystapi JUZ przy STARCIE (fail-fast), a nie dopiero przy wywolaniu zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NamedNativeQueryBasic {
        /*
         * 🧪 Zadanie 5:
         * Dodaj @NamedNativeQuery(name = "Customer.findAllNative", query = "select *
         * from customer", resultClass = Customer.class). Uzyj go przez createNamedQuery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareNamedVsAdHocQuery {
        /*
         * 🧪 Zadanie 6:
         * Napisz TE SAMA funkcjonalnosc RAZ przez named query, RAZ przez ad-hoc
         * createQuery(hqlString) - porownaj w komentarzu roznice w kodzie wolajacym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NamedQueryReturnsTypedResult {
        /*
         * 🧪 Zadanie 7:
         * Uzyj createNamedQuery("Customer.findByCity", Customer.class) (TypedQuery)
         * zamiast wersji bez typu - zweryfikuj brak potrzeby rzutowania wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NamedQueryWithOrderBy {
        /*
         * 🧪 Zadanie 8:
         * Dodaj @NamedQuery z ORDER BY ("from Customer c order by c.name") - wypisz
         * posortowana liste klientow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NamedQueryMissingParameterError {
        /*
         * 🧪 Zadanie 9:
         * Wywolaj named query z parametrem (:city), ale ZAPOMNIJ wywolac
         * setParameter() przed getResultList() - zapisz w komentarzu blad,
         * jaki Hibernate zwraca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllNamedQueriesOnEntity {
        /*
         * 🧪 Zadanie 10:
         * Za pomoca refleksji (Customer.class.getAnnotationsByType(NamedQuery.class))
         * wypisz WSZYSTKIE nazwy named queries zdefiniowane na Twojej encji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_NamedQueryWithJoinFetch {
        /*
         * 🧪 Zadanie 11:
         * Dodaj Order (@ManyToOne Customer). Napisz @NamedQuery z JOIN FETCH
         * dociagajacym Customer razem z zamowieniami - odpowiednik wzorca z Lesson15/19.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NamedQueryForAggregateReport {
        /*
         * 🧪 Zadanie 12:
         * Napisz @NamedQuery zwracajacy AGREGACJE (count/sum, podglad z Lesson19) -
         * np. "Customer.countByCity" zliczajacy klientow w konkretnym miescie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NamedQueryVersusRepositoryPattern {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj klase "CustomerRepository" z metodami OPAKOWUJACYMI wywolania
         * named queries (metody publiczne, wewnatrz createNamedQuery) - kod
         * wolajacy nie widzi bezposrednio nazw zapytan jako Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NamedNativeQueryWithSqlResultSetMapping {
        /*
         * 🧪 Zadanie 14:
         * Polacz @NamedNativeQuery z @SqlResultSetMapping (podglad z Lesson21) -
         * zdefiniuj nazwane natywne zapytanie zwracajace DTO zamiast pelnej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OverridingNamedQueryAtRuntimeNotPossible {
        /*
         * 🧪 Zadanie 15:
         * Sprobuj (bezskutecznie) zmienic TRESC named query w runtime (bez ponownego
         * budowania SessionFactory) - zapisz w komentarzu, ze named queries sa
         * USTALONE przy starcie SessionFactory i niemodyfikowalne pozniej (w
         * przeciwienstwie do Criteria API z Lesson20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NamedQueryHintsSetCacheable {
        /*
         * 🧪 Zadanie 16:
         * Dodaj @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value =
         * "true")}) do @NamedQuery (podglad przed Lesson24) - wypisz w komentarzu, po
         * co takie hinty przy nazwanych zapytaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SharedNamedQueriesAcrossMultipleServices {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj DWA rozne serwisy (np. CustomerService i ReportService) uzywajace
         * TEGO SAMEGO named query "Customer.findByCity" - zademonstruj reuse bez
         * duplikacji logiki zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NamedQueryNamingConventionAudit {
        /*
         * 🧪 Zadanie 18:
         * Zbierz WSZYSTKIE named queries z Twoich cwiczen tej lekcji i sprawdz
         * (recznie/w komentarzu), czy wszystkie trzymaja sie konwencji
         * "NazwaEncji.opisAkcji" - popraw te, ktore odbiegaja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NamedQueryPerformanceVsAdHoc {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas 1000 wywolan TEGO SAMEGO zapytania: raz jako named query, raz
         * jako ad-hoc createQuery(String) - zapisz w komentarzu, czy zaobserwowales
         * roznice (Hibernate cache'uje sparsowane plany zapytan niezaleznie od zrodla).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MigrateAdHocQueriesToNamedQueriesRefactor {
        /*
         * 🧪 Zadanie 20:
         * Wez 3 zapytania ad-hoc (createQuery ze Stringiem) z poprzednich lekcji i
         * PRZEBUDUJ je na @NamedQuery przy odpowiednich encjach - porownaj w
         * komentarzu organizacje kodu przed i po.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullQueryLibraryWithNamedQueries {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNA "biblioteke" 8+ named queries dla systemu
         * Customer/Order (podstawowe wyszukiwanie, agregacje, join fetch) -
         * WSZYSTKIE zdefiniowane przy odpowiednich encjach, uzyte przez WLASNE
         * klasy repository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ValidateAllNamedQueriesAtStartupTest {
        /*
         * 🧪 Zadanie 22:
         * Napisz "test startowy" - zbuduj SessionFactory ze WSZYSTKIMI named queries
         * z Twojego systemu i zweryfikuj (bez wyjatku), ze SessionFactory zbudowala
         * sie poprawnie - to DOWOD, ze wszystkie zapytania sa syntaktycznie poprawne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_NamedQueryVersusStoredProcedureComparison {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: napisz porownanie (min. 3 punkty) @NamedQuery vs procedury
         * skladowane bazy danych (stored procedures) - gdzie zyje logika, kto ja
         * utrzymuje, przenosnosc miedzy bazami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DynamicFallbackWhenNamedQueryInsufficient {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj repository z METODAMI opartymi na named queries DLA typowych
         * przypadkow, ale z DODATKOWA metoda "search" uzywajaca Criteria API
         * (Lesson20) dla przypadkow DYNAMICZNYCH - zademonstruj, kiedy uzyc ktorej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NamedQueryWithComplexJoinAndAggregation {
        /*
         * 🧪 Zadanie 25:
         * Napisz JEDNO, ZLOZONE @NamedQuery laczace JOIN FETCH, GROUP BY, HAVING i
         * ORDER BY naraz (jak raport z Lesson19, ale zdefiniowany jako named query
         * przy encji zamiast ad-hoc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestingNamedQueriesInIsolation {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode "testNamedQuery(SessionFactory, String queryName, Map<String,
         * Object> params, int expectedCount)" - generyczny "test" sprawdzajacy, czy
         * dane nazwane zapytanie zwraca oczekiwana liczbe wynikow. Uzyj jej dla 3
         * roznych named queries.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_NamedQueryDocumentationGenerator {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode (przez refleksje) generujaca CZYTELNA "dokumentacje" (println)
         * wszystkich named queries zdefiniowanych na danej klasie encji - nazwa +
         * tresc zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigratingLegacyHqlStringsToNamedQueriesAcrossModule {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (kod + komentarz) plan migracji WIEKSZEGO, wyobrazonego modulu
         * (10+ zapytan ad-hoc rozrzuconych po serwisach) na named queries - z
         * priorytetyzacja (ktore zapytania migrowac NAJPIERW i dlaczego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnNamedQueryBestPractices {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami zalety i wady named queries wzgledem
         * ad-hoc HQL i Criteria API - kiedy ktore podejscie wybrac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullNamedQueriesCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system Customer/Order z: biblioteka
         * named queries (Zadanie 21), automatyczna walidacja startowa (Zadanie 22),
         * repository z fallbackiem na Criteria (Zadanie 24), i wygenerowana
         * dokumentacja (Zadanie 27) - zademonstruj caly system dzialajacy razem.
         */
        public static void main(String[] args) { }
    }
}
