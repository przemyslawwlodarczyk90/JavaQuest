package com.example.javaquest._25_unit_testing.Lesson06_AssertJForCollectionsAndMaps;

public class _Exercises_Lesson06_AssertJForCollectionsAndMaps {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteHasSizeAssertion {
        /*
         * 🧪 Zadanie 1:
         * Napisz test Z `.hasSize(n)` NA liscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteContainsAssertion {
        /*
         * 🧪 Zadanie 2:
         * Napisz test Z `.contains(...)` I `.doesNotContain(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainContainsVsContainsExactly {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij ROZNICE `.contains(...)` A
         * `.containsExactly(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteContainsExactlyInAnyOrder {
        /*
         * 🧪 Zadanie 4:
         * Napisz test `.containsExactlyInAnyOrder(...)` NA liscie W
         * INNEJ kolejnosci NIZ oczekiwana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteEmptyAndNotEmptyAssertions {
        /*
         * 🧪 Zadanie 5:
         * Napisz testy `.isEmpty()` I `.isNotEmpty()` NA 2 roznych
         * listach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMapContainsEntryAssertion {
        /*
         * 🧪 Zadanie 6:
         * Napisz test `.containsEntry(klucz, wartosc)` NA mapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteMapContainsKeyAndValueAssertions {
        /*
         * 🧪 Zadanie 7:
         * Napisz test `.containsKey(...)` I `.containsValue(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteExtractingSingleField {
        /*
         * 🧪 Zadanie 8:
         * Napisz test `.extracting(...)` wyciagajacy JEDNO pole Z
         * WLASNEGO rekordu/klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithManualLoopVerification {
        /*
         * 🧪 Zadanie 9:
         * Napisz TA SAMA weryfikacje RECZNA petla `for` +
         * `assertTrue` - porownaj Z AssertJ (Zadanie 8).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteHasSameSizeAsAssertion {
        /*
         * 🧪 Zadanie 10:
         * Napisz test `.hasSameSizeAs(inna_lista)`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteExtractingMultipleFieldsWithTuple {
        /*
         * 🧪 Zadanie 11:
         * Napisz test `.extracting(pole1, pole2)` + `.contains(tuple(...))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteAllMatchAndAnyMatchAssertions {
        /*
         * 🧪 Zadanie 12:
         * Napisz testy `.allMatch(predicate)` I `.anyMatch(predicate)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteNoneMatchAssertion {
        /*
         * 🧪 Zadanie 13:
         * Napisz test `.noneMatch(predicate)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteFilteredOnAssertion {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `.filteredOn(predicate)` DO sprawdzenia PODZBIORU
         * listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteContainsOnlyOnceAssertion {
        /*
         * 🧪 Zadanie 15:
         * Napisz test `.containsOnlyOnce(...)` (element WYSTEPUJE
         * DOKLADNIE 1 raz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteIsSortedAssertion {
        /*
         * 🧪 Zadanie 16:
         * Napisz test `.isSorted()` NA posortowanej liscie -
         * zweryfikuj PORAZKE NA nieposortowanej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteMapIsEmptyAndHasSizeAssertions {
        /*
         * 🧪 Zadanie 17:
         * Napisz testy `.isEmpty()`/`.hasSize(n)` NA mapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteContainsExactlyEntriesInAnyOrderForMap {
        /*
         * 🧪 Zadanie 18:
         * Napisz test `.containsExactlyInAnyOrderEntriesOf(inna_mapa)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteNestedCollectionAssertion {
        /*
         * 🧪 Zadanie 19:
         * Napisz test NA liscie LIST (`List<List<Integer>>`) -
         * sprawdz rozmiar zewnetrznej I wewnetrznych list.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignAssertionForRepositoryQueryResult {
        /*
         * 🧪 Zadanie 20:
         * Powiaz z `_23_spring_data_jpa` - napisz test AssertJ
         * sprawdzajacy wynik SYMULOWANEGO zapytania repozytorium (lista
         * DTO).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomElementComparator {
        /*
         * 🧪 Zadanie 21:
         * Uzyj `.usingElementComparator(...)` DO porownania elementow
         * WEDLUG WLASNEJ logiki (np. TYLKO nazwa produktu, BEZ ceny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_WriteRecursiveComparisonForListOfObjects {
        /*
         * 🧪 Zadanie 22:
         * Uzyj `.usingRecursiveFieldByFieldElementComparator()` DO
         * porownania LISTY obiektow BEZ nadpisywania `equals()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BenchmarkExtractingVsManualStream {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_03_collections` - porownaj `.extracting(...)` Z
         * RECZNYM `stream().map(...).toList()` - CZYTELNOSC I WYDAJNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomListAssertClass {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WLASNA klase `ProductListAssert extends
         * AbstractListAssert<...>` Z metoda DOMENOWA (np.
         * `.hasTotalValueOf(kwota)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteMapValuesSatisfyAssertion {
        /*
         * 🧪 Zadanie 25:
         * Uzyj `.hasValueSatisfying(...)` DO sprawdzenia WARTOSCI POD
         * konkretnym kluczem ZLOZONA logika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementDeepEqualityForComplexGraph {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_23_spring_data_jpa` - napisz test porownujacy
         * DRZEWO obiektow (Author->Book->...) `usingRecursiveComparison()`
         * Z IGNOROWANIEM pol (np. `id`, `createdDate`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAssertionsForPaginatedResults {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_23_spring_data_jpa/Lesson06` - napisz test
         * sprawdzajacy `Page<T>` (zawartosc + `totalElements` +
         * `totalPages`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementSetSpecificAssertions {
        /*
         * 🧪 Zadanie 28:
         * Napisz testy DEDYKOWANE `Set` (`.hasSameElementsAs(...)`) -
         * porownaj Z `List`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureAssertionPerformanceOnLargeCollections {
        /*
         * 🧪 Zadanie 29:
         * Zmierz CZAS `.containsExactlyInAnyOrder(...)` NA liscie
         * 100 000 elementow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullCollectionAssertionSuiteForOrderSystem {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY zestaw testow DLA systemu zamowien (lista
         * pozycji, mapa produkt->ilosc) POKRYWAJACY WSZYSTKIE poznane
         * metody kolekcji/map.
         */
        public static void main(String[] args) { }
    }
}
