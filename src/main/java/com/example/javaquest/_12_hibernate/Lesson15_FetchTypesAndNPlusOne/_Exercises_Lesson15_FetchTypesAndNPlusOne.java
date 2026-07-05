package com.example.javaquest._12_hibernate.Lesson15_FetchTypesAndNPlusOne;

public class _Exercises_Lesson15_FetchTypesAndNPlusOne {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefaultManyToOneIsEager {
        /*
         * 🧪 Zadanie 1:
         * Utworz Customer (id, name) i Order (id, @ManyToOne customer BEZ jawnego
         * fetch) na H2 ("jdbc:h2:mem:l15ex01;DB_CLOSE_DELAY=-1"). Odczytaj Order i
         * sprawdz w show_sql, ze Customer zaladowal sie OD RAZU (domyslny EAGER).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplicitLazyOnManyToOne {
        /*
         * 🧪 Zadanie 2:
         * Ustaw @ManyToOne(fetch = FetchType.LAZY) na Order.customer. Odczytaj Order
         * i sprawdz w show_sql, ze Customer NIE zaladowal sie od razu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DefaultOneToManyIsLazy {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do Customer @OneToMany(mappedBy="customer") List<Order> BEZ jawnego
         * fetch. Odczytaj Customer i sprawdz w show_sql, ze lista zamowien NIE
         * zaladowala sie od razu (domyslny LAZY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerNPlusOneManually {
        /*
         * 🧪 Zadanie 4:
         * Zapisz 5 klientow, kazdy z 2 zamowieniami. Odczytaj WSZYSTKICH klientow i w
         * petli wywolaj getOrders().size() dla kazdego - policz w show_sql DOKLADNA
         * liczbe wykonanych zapytan SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JoinFetchSolvesNPlusOne {
        /*
         * 🧪 Zadanie 5:
         * Napraw N+1 z Zadania 4 uzywajac HQL (podglad przed Lesson18)
         * "select distinct c from Customer c join fetch c.orders" - policz zapytania
         * ponownie i porownaj w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LazyProxyClassName {
        /*
         * 🧪 Zadanie 6:
         * Odczytaj Order z LAZY Customer, ale NIE dotykaj pol customer - wypisz
         * customer.getClass().getName() i zapisz w komentarzu, ze to nie dokladnie
         * Customer.class (proxy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EagerOnOneToMany {
        /*
         * 🧪 Zadanie 7:
         * Zmien @OneToMany na fetch = FetchType.EAGER. Odczytaj Customer i sprawdz w
         * show_sql, ze zamowienia zaladowaly sie OD RAZU (zmiana domyslnego zachowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountSqlQueriesWithStatistics {
        /*
         * 🧪 Zadanie 8:
         * Wlacz hibernate.generate_statistics=true. Powtorz scenariusz N+1 z Zadania
         * 4 i odczytaj sessionFactory.getStatistics().getQueryExecutionCount() zamiast
         * recznego liczenia w logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LazyInitializationExceptionOutsideSession {
        /*
         * 🧪 Zadanie 9:
         * Odczytaj Customer z LAZY orders, zamknij Session, a POTEM sprobuj
         * getOrders().size() - zapisz w komentarzu wyjatek LazyInitializationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareTotalTimeEagerVsLazy {
        /*
         * 🧪 Zadanie 10:
         * Zmierz czas odczytu 10 klientow z ich zamowieniami: raz EAGER (jeden JOIN),
         * raz LAZY + N+1 (bez join fetch) - zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_JoinFetchWithWhereClause {
        /*
         * 🧪 Zadanie 11:
         * Napisz HQL laczacy join fetch Z warunkiem WHERE (np. "join fetch c.orders o
         * where o.total > 100") - zweryfikuj, ze zaladowaly sie TYLKO pasujace
         * zamowienia (uwaga na potencjalne zniekstalcenie kolekcji - opisz w
         * komentarzu ryzyko tego wzorca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleJoinFetchInOneQuery {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz model o Order -> OrderItem (@OneToMany). Napisz HQL z DWOMA join
         * fetch naraz (customer.orders ORAZ orders.items) - zweryfikuj JEDNO
         * zapytanie ladujace caly graf.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EntityGraphNamedDefinition {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj @NamedEntityGraph na Customer wskazujacy attributeNode "orders".
         * Uzyj go przez hint "jakarta.persistence.fetchgraph" w find()/query -
         * zweryfikuj dociagniecie zamowien BEZ zmiany samego HQL/JPQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DifferentEntityGraphsPerUseCase {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj DWA rozne @NamedEntityGraph na Customer (jeden dociagajacy
         * TYLKO orders, drugi dociagajacy orders+items). Uzyj obu w roznych
         * miejscach kodu i porownaj wygenerowany SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BatchFetchSizeAnnotation {
        /*
         * 🧪 Zadanie 15:
         * Ustaw @BatchSize(size = 10) (adnotacja Hibernate) na kolekcji orders.
         * Powtorz scenariusz N+1 (Zadanie 4) dla 20 klientow i policz zapytania -
         * zapisz w komentarzu, jak BatchSize redukuje liczbe zapytan (grupuje po N
         * rodzicow naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SubselectFetchStrategy {
        /*
         * 🧪 Zadanie 16:
         * Ustaw @Fetch(FetchMode.SUBSELECT) (Hibernate) na kolekcji orders. Powtorz
         * N+1 i zapisz w komentarzu, ze Hibernate uzyl JEDNEGO dodatkowego zapytania
         * z podzapytaniem zamiast N osobnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareFetchModesTable {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj tabele porownawcza (println) liczby zapytan SQL dla 20 klientow z:
         * brakiem optymalizacji (N+1), join fetch, @BatchSize(10), @Fetch(SUBSELECT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_LazyManyToOneWithBytecodeEnhancementNote {
        /*
         * 🧪 Zadanie 18:
         * Zweryfikuj (przez show_sql) ze fetch=LAZY na @ManyToOne (Zadanie 2)
         * FAKTYCZNIE dziala leniwie (w odroznieniu od LAZY na @OneToOne z Lesson11,
         * ktore czesto ladowalo sie eagerly) - zapisz w komentarzu, dlaczego
         * @ManyToOne LAZY jest bardziej niezawodne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PaginationWithLazyCollections {
        /*
         * 🧪 Zadanie 19:
         * Odczytaj STRONE klientow (setFirstResult/setMaxResults) z LAZY orders -
         * zapisz w komentarzu, dlaczego join fetch + paginacja RAZEM sa
         * problematyczne (Hibernate paginuje w pamieci, nie w SQL, przy join fetch
         * kolekcji "wiele").
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureMemoryImpactOfEagerLoading {
        /*
         * 🧪 Zadanie 20:
         * Zaladuj 1000 klientow z EAGER orders (kazdy z 10 zamowieniami) vs LAZY (bez
         * dotykania kolekcji) - zapisz w komentarzu subiektywna obserwacje roznicy w
         * czasie/zasobach.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullEntityGraphBasedRepository {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj "CustomerRepository" z RUZNYMI metodami dla roznych potrzeb:
         * findBasic() (bez dociagania), findWithOrders() (entity graph), findFull()
         * (orders+items) - kazda uzywa INNEGO @NamedEntityGraph lub join fetch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DetectNPlusOneAutomaticallyWithStatistics {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode "detectNPlusOne(SessionFactory, Runnable operacja)" ktora
         * wykonuje operacje i PORUWNUJE liczbe zapytan PRZED/PO - jesli liczba
         * zapytan > pewnego progu, wypisuje ostrzezenie "mozliwy problem N+1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CombineBatchSizeWithJoinFetchInDifferentPaths {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj scenariusz z DWOMA sciezkami dostepu do tych samych danych - jedna
         * uzywa join fetch (dla pelnego raportu), druga polega na @BatchSize (dla
         * przegladania listy z paginacja) - zademonstruj OBIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RealWorldPerformanceRegression {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj scenariusz z 100 klientami po 20 zamowien. Zmierz i porownaj
         * DOKLADNIE 4 podejscia (brak optymalizacji, join fetch, BatchSize,
         * SUBSELECT) - wypisz tabele z czasami i liczba zapytan dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_EntityGraphWithNestedAttributeNodes {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj @NamedEntityGraph z ZAGNIEZDZONYM @NamedAttributeNode
         * (subgraph dla orders.items) - dociagnij CALY graf (customer+orders+items)
         * JEDNYM entity graph, bez pisania recznego join fetch HQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DynamicFetchStrategySelection {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode wybierajaca strategie ladowania W ZALEZNOSCI od parametru
         * (np. "summary" -> bez dociagania, "detailed" -> join fetch) - zademonstruj
         * obie sciezki na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LazyLoadingInWebRequestSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj typowy problem webowy: "kontroler" otwiera Session, zwraca
         * obiekt, Session sie zamyka, a "widok" probuje dostac sie do LAZY
         * kolekcji - zademonstruj LazyInitializationException i napraw go przez
         * wczesniejsze join fetch w "kontrolerze".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_OpenSessionInViewAntiPatternDiscussion {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz wyjasnienie (min. 4 zdania) wzorca "Open Session in
         * View" (utrzymywanie Session otwartej przez caly czas obslugi zadania HTTP,
         * zeby LAZY dzialalo w widoku) - i dlaczego jest to POWSZECHNIE uznawane za
         * ANTY-wzorzec (ukryte zapytania, dluzsze transakcje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnFetchStrategyDecisionTree {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz "drzewo decyzyjne" (min. 5 punktow, w komentarzu) -
         * jak wybrac miedzy LAZY+join fetch, @BatchSize, @Fetch(SUBSELECT) i EAGER
         * dla konkretnej relacji, w zaleznosci od jej charakterystyki (rozmiar
         * kolekcji, czestotliwosc uzycia, kontekst zapytania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullFetchOptimizationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system Customer->Order->OrderItem z
         * @NamedEntityGraph (Zadanie 25), @BatchSize dla przegladania (Zadanie 15),
         * i metoda "detectNPlusOne" (Zadanie 22) monitorujaca WSZYSTKIE operacje -
         * zademonstruj pelny scenariusz z raportem wydajnosci na koniec.
         */
        public static void main(String[] args) { }
    }
}
