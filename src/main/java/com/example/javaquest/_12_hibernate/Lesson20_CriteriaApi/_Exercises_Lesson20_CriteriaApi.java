package com.example.javaquest._12_hibernate.Lesson20_CriteriaApi;

public class _Exercises_Lesson20_CriteriaApi {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicCriteriaQuery {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Car (id, brand, model, price) na H2
         * ("jdbc:h2:mem:l20ex01;DB_CLOSE_DELAY=-1"). Zapisz 5 samochodow. Zbuduj
         * CriteriaQuery<Car> zwracajaca WSZYSTKIE (bez zadnych warunkow) - odpowiednik
         * "from Car".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SingleEqualsPredicate {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj Criteria z JEDNYM predykatem cb.equal(root.get("brand"), "Toyota") -
         * znajdz samochody TEJ konkretnej marki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GreaterThanPredicate {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj Criteria z cb.gt(root.get("price"), 50000.0) - znajdz samochody
         * drozsze niz podana kwota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LikePredicate {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj Criteria z cb.like(root.get("model"), "%X%") - znajdz modele
         * zawierajace litere "X".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrderByAscendingCriteria {
        /*
         * 🧪 Zadanie 5:
         * Dodaj do CriteriaQuery sortowanie query.orderBy(cb.asc(root.get("price"))) -
         * zweryfikuj rosnace sortowanie wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_OrderByDescendingCriteria {
        /*
         * 🧪 Zadanie 6:
         * Ta sama lista, ale posortowana malejaco (cb.desc(...)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AndCombinationOfTwoPredicates {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj Criteria laczaca DWA predykaty przez cb.and(pred1, pred2) - marka
         * ORAZ cena ponizej progu naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_OrCombinationOfTwoPredicates {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj Criteria laczaca DWA predykaty przez cb.or(pred1, pred2) - marka A
         * LUB marka B.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EmptyPredicatesListReturnsAll {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj Criteria z PUSTA lista predykatow (cb.and(new Predicate[0])) -
         * zweryfikuj, ze zwraca WSZYSTKIE wiersze (brak filtra = brak ograniczenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareCriteriaWithEquivalentHql {
        /*
         * 🧪 Zadanie 10:
         * Napisz TO SAMO zapytanie (marka + cena < X) RAZ przez Criteria API, RAZ
         * przez HQL (podglad z Lesson18) - porownaj w komentarzu liczbe linii kodu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DynamicSearchWithTwoOptionalFilters {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode "searchCars(Session, String brand, Double maxPrice)"
         * budujaca liste predykatow WARUNKOWO (if brand != null ... if maxPrice !=
         * null ...) - jak w lekcji, ale dla WLASNEJ encji Car.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DynamicSearchWithThreeOptionalFilters {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz metode z Zadania 11 o TRZECI opcjonalny filtr (model). Przetestuj
         * WSZYSTKIE kombinacje (0, 1, 2, 3 podane filtry).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CriteriaOnRelatedEntityField {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do Car @ManyToOne Dealer (nazwa, miasto). Zbuduj Criteria z
         * predykatem na polu POWIAZANEJ encji: root.get("dealer").get("city").
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CriteriaAggregateFunctionCount {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj CriteriaQuery<Long> zwracajaca cb.count(root) - odpowiednik
         * "select count(c) from Car c".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CriteriaGroupBy {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj Criteria z query.groupBy(root.get("brand")) i selekcja marki +
         * count - odpowiednik "group by brand" z Lesson19.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CriteriaDistinctSelect {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj Criteria z query.select(root.get("brand")).distinct(true) -
         * zwroc UNIKALNE marki (bez duplikatow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CriteriaBetweenPredicate {
        /*
         * 🧪 Zadanie 17:
         * Uzyj cb.between(root.get("price"), min, max) - znajdz samochody w
         * konkretnym przedziale cenowym (odpowiednik BETWEEN z HQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CriteriaInPredicate {
        /*
         * 🧪 Zadanie 18:
         * Uzyj root.get("brand").in("Toyota", "Honda", "Mazda") - znajdz samochody
         * NALEZACE do jednej z podanych marek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CriteriaIsNullPredicate {
        /*
         * 🧪 Zadanie 19:
         * Zapisz samochody, z ktorych CZESC ma dealer=null. Uzyj cb.isNull(root.get
         * ("dealer")) - znajdz samochody BEZ przypisanego dealera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorHqlSearchMethodToCriteria {
        /*
         * 🧪 Zadanie 20:
         * Wez metode wyszukujaca napisana w HQL (z Lesson18 lub 19, np. z
         * konkatenacja Stringow dla opcjonalnych filtrow) i PRZEBUDUJ ja na Criteria
         * API - porownaj w komentarzu czytelnosc obu wersji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullDynamicSearchFormBackend {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY "backend formularza wyszukiwania" z KLASA "CarSearchCriteria"
         * (pola: brand, minPrice, maxPrice, dealerCity - wszystkie opcjonalne) i
         * metoda "search(Session, CarSearchCriteria)" budujaca predykaty na podstawie
         * WYPELNIONYCH pol tej klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CriteriaWithSubqueryEquivalent {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj Criteria z PODZAPYTANIEM (query.subquery()) - odpowiednik "gdzie
         * cena wieksza niz srednia" z Lesson19, ale przez Criteria API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CriteriaWithJoinFetch {
        /*
         * 🧪 Zadanie 23:
         * Uzyj root.fetch("dealer", JoinType.LEFT) w Criteria API - odpowiednik
         * "join fetch" z HQL, zapobiegajacy N+1 przy dostepie do dealer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MetamodelStyleConstantsWithoutGenerator {
        /*
         * 🧪 Zadanie 24:
         * BEZ generowania prawdziwego metamodelu (hibernate-jpamodelgen): napisz
         * WLASNA klase "Car_" ze statycznymi Stringami ("BRAND = \"brand\"") -
         * uzyj ich zamiast "surowych" Stringow w root.get(...), symulujac czesciowe
         * bezpieczenstwo typow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerformanceCriteriaVsHqlComparison {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas 1000 wywolan TEGO SAMEGO zapytania: raz zbudowanego przez
         * Criteria API (budowa obiektu za kazdym razem), raz przez gotowy String HQL
         * - zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CriteriaUpdateBulkOperation {
        /*
         * 🧪 Zadanie 26:
         * Uzyj CriteriaUpdate (cb.createCriteriaUpdate) zamiast HQL bulk update
         * (Lesson19) - zaimplementuj "podwyzsz wszystkie ceny o 5%" przez Criteria API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CriteriaDeleteBulkOperation {
        /*
         * 🧪 Zadanie 27:
         * Uzyj CriteriaDelete (cb.createCriteriaDelete) - zaimplementuj "usun
         * wszystkie samochody tansze niz X" przez Criteria API zamiast HQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ComplexMultiEntityJoinCriteria {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz model o TRZECIA encje (np. Sale - sprzedaz konkretnego Car przez
         * konkretnego Dealer). Zbuduj Criteria laczaca WSZYSTKIE 3 encje (Car,
         * Dealer, Sale) w jednym zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnCriteriaVsHqlDecisionCriteria {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, KIEDY w realnym projekcie wybralbys
         * Criteria API zamiast HQL - z konkretnym przykladem formularza wyszukiwania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCriteriaApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system wyszukiwania samochodow
         * laczacy: dynamiczne filtry (Zadanie 21), join fetch (Zadanie 23), CriteriaUpdate
         * dla masowej operacji (Zadanie 26), i "quasi-metamodel" (Zadanie 24) - w
         * jednej, spojnej klasie "CarSearchService".
         */
        public static void main(String[] args) { }
    }
}
