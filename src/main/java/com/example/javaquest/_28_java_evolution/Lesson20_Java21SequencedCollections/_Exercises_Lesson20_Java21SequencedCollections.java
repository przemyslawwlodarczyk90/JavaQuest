package com.example.javaquest._28_java_evolution.Lesson20_Java21SequencedCollections;

public class _Exercises_Lesson20_Java21SequencedCollections {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseGetFirstAndGetLastOnArrayList {
        /* 🧪 Zadanie 1: Uzyj `getFirst()` I `getLast()` NA `ArrayList`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseAddFirstAndAddLastOnArrayList {
        /* 🧪 Zadanie 2: Uzyj `addFirst()` I `addLast()` NA `ArrayList`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseRemoveFirstAndRemoveLastOnArrayList {
        /* 🧪 Zadanie 3: Uzyj `removeFirst()` I `removeLast()` NA `ArrayList`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetReversedViewOfListAndPrintIt {
        /* 🧪 Zadanie 4: Uzyskaj WIDOK odwrocony (`reversed()`) listy I WYPISZ go. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseGetFirstOnLinkedHashSetWithoutIterator {
        /* 🧪 Zadanie 5: Uzyj `getFirst()` NA `LinkedHashSet` BEZ iteratora. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseFirstEntryAndLastEntryOnLinkedHashMap {
        /* 🧪 Zadanie 6: Uzyj `firstEntry()` I `lastEntry()` NA `LinkedHashMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_UsePutFirstOnLinkedHashMap {
        /* 🧪 Zadanie 7: Uzyj `putFirst()` NA `LinkedHashMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareOldStyleGetZeroWithNewGetFirst {
        /* 🧪 Zadanie 8: Porownaj STARY styl `get(0)`/`get(size()-1)` Z NOWYM `getFirst()`/`getLast()`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyHashMapDoesNotImplementSequencedMap {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, DLACZEGO zwykly `HashMap` NIE IMPLEMENTUJE `SequencedMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatJep431SolvedInCollectionsFramework {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, JAKA LUKE W Collections Framework WYPELNIL JEP 431. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DemonstrateReversedIsViewNotCopyByMutatingOriginal {
        /* 🧪 Zadanie 11: Zademonstruj, ze `reversed()` TO WIDOK (NIE kopia) - ZMODYFIKUJ oryginal I sprawdz widok. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseSequencedCollectionOnTreeSetAndTreeMap {
        /* 🧪 Zadanie 12: Uzyj metod SequencedCollection NA `TreeSet`/`TreeMap` (JUZ MIALY CZESC tego API PRZED Java 21, TERAZ formalnie). */
        public static void main(String[] args) { }
    }

    static class Exercise13_BuildLruCacheLikeStructureUsingSequencedMapMethods {
        /* 🧪 Zadanie 13: Zbuduj PROSTA strukture PODOBNA DO LRU cache uzywajac metod `SequencedMap` (powiazanie Z `_13_libraries/Lesson27` Caffeine). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementUndoStackUsingSequencedCollectionMethods {
        /* 🧪 Zadanie 14: Zaimplementuj PROSTY stos "cofnij" (undo) uzywajac `addFirst()`/`removeFirst()`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareArrayDequeWithNewSequencedCollectionMethods {
        /* 🧪 Zadanie 15: Powiaz z `_03_collections/Lesson18_Deque` - porownaj METODY `ArrayDeque` (`addFirst` itd. JUZ ISTNIALY) Z NOWYM interfejsem `SequencedCollection`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_IterateReversedViewOfLinkedHashSetInForEachLoop {
        /* 🧪 Zadanie 16: ITERUJ PO odwroconym WIDOKU `LinkedHashSet` W petli `for-each`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_MigrateOldGetIteratorNextPatternToNewGetFirstMethod {
        /* 🧪 Zadanie 17: ZMIGRUJ STARY wzorzec `iterator().next()` NA NOWY `getFirst()`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildHistoryTrackerUsingSequencedMapPreservingInsertionOrder {
        /* 🧪 Zadanie 18: Zbuduj "tracker historii" (klucz->wartosc, Z ZACHOWANA kolejnoscia wstawiania) uzywajac `SequencedMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareReversedViewPerformanceWithManualCollectionsReverse {
        /* 🧪 Zadanie 19: Porownaj `reversed()` (WIDOK) Z `Collections.reverse()` (MUTACJA W MIEJSCU) - ROZNICE semantyki. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhichExistingCollectionsInThisCourseAlreadyImplementSequencedInterfaces {
        /* 🧪 Zadanie 20: Bez terminala - przeanalizuj (Z pamieci), KTORE kolekcje UZYWANE W tym kursie (`_03_collections`) JUZ IMPLEMENTUJA SequencedCollection/Set/Map. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildBrowserHistoryNavigatorUsingSequencedCollectionMethods {
        /* 🧪 Zadanie 21: Zbuduj nawigator HISTORII przegladarki (wstecz/dalej) uzywajac metod SequencedCollection. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDoublyEndedPriorityQueueLikeStructureUsingSequencedSet {
        /* 🧪 Zadanie 22: Zaimplementuj strukture PODOBNA DO kolejki priorytetowej Z DWOMA koncami uzywajac `SequencedSet` (`TreeSet`). */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildEventLogWithFirstAndLastAccessOptimizedViaSequencedMap {
        /* 🧪 Zadanie 23: Zbuduj dziennik zdarzen Z ZOPTYMALIZOWANYM dostepem DO pierwszego/ostatniego wpisu przez `SequencedMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareCustomImplementationOfFirstLastAccessWithBuiltInSequencedApi {
        /* 🧪 Zadanie 24: Napisz WLASNA implementacje dostepu DO pierwszego/ostatniego elementu (PRZED-Java-21 styl), POTEM porownaj Z WBUDOWANYM API. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementPaletteRotationUsingReversedViewAndAddFirst {
        /* 🧪 Zadanie 25: Zaimplementuj "rotacje" palety elementow (przesuwanie) uzywajac `reversed()` + `addFirst()`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildCacheEvictionStrategyUsingSequencedMapFirstEntryRemoval {
        /* 🧪 Zadanie 26: Zbuduj strategie EWIKCJI cache (usuwanie NAJSTARSZEGO wpisu) uzywajac `SequencedMap.pollFirstEntry()`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMigrationGuideForLegacyCodeUsingIteratorNextToSequencedApi {
        /* 🧪 Zadanie 27: Zaprojektuj PRZEWODNIK migracji istniejacego kodu (`iterator().next()`) NA NOWE API SequencedCollection. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRecentItemsTrackerWithBoundedSizeUsingSequencedMap {
        /* 🧪 Zadanie 28: Zaimplementuj tracker "OSTATNIO uzywanych" elementow (Z ograniczonym rozmiarem) uzywajac `SequencedMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildBidirectionalIterationDemoComparingListIteratorWithReversedView {
        /* 🧪 Zadanie 29: Porownaj DWUKIERUNKOWA iteracje przez `ListIterator` (STARY sposob) Z `reversed()` (NOWY sposob). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullApiWrapperExposingSequencedOperationsForCustomDomainCollection {
        /* 🧪 Zadanie 30: Zaprojektuj WLASNA, DOMENOWA strukture danych IMPLEMENTUJACA `SequencedCollection` (custom kolekcja Z WLASNA logika). */
        public static void main(String[] args) { }
    }
}
