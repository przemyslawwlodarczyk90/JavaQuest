package com.example.javaquest._03_collections.Lesson16_LinkedHashMap;

public class _Exercises_Lesson16_LinkedHashMap {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicInsertionOrder {
        /*
         * 🧪 Zadanie 1:
         * LinkedHashMap<String,Integer>: dodaj "Zebra"->1, "Anna"->2, "Marek"->3, "Beata"->4.
         * Wypisz mapę – sprawdź, że kolejność wypisania kluczy odpowiada kolejności put().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HashMapVsLinkedHashMap {
        /*
         * 🧪 Zadanie 2:
         * Wstaw te same 5 par klucz-wartość najpierw do HashMap, potem do LinkedHashMap.
         * Wypisz obie mapy i porównaj kolejność iteracji kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BasicOperations {
        /*
         * 🧪 Zadanie 3:
         * LinkedHashMap<String,Integer> ceny = {"Chleb"->5, "Mleko"->4, "Masło"->9}.
         * Wywołaj get("Mleko"), getOrDefault("Ser", 0), containsKey("Masło"), remove("Chleb").
         * Wypisz mapę po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PutIfAbsent {
        /*
         * 🧪 Zadanie 4:
         * LinkedHashMap<String,Integer> punkty = {"Ala"->10, "Ola"->20}.
         * Wywołaj putIfAbsent("Ala", 999) oraz putIfAbsent("Ela", 30).
         * Wypisz mapę i wyjaśnij (komentarzem), dlaczego wartość Ali się nie zmieniła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IterateEntrySet {
        /*
         * 🧪 Zadanie 5:
         * LinkedHashMap<String,String> stolice = {"PL"->"Warszawa","DE"->"Berlin","FR"->"Paryż"}.
         * Zaiteruj przez entrySet() pętlą for-each i wypisz "klucz -> wartość" dla każdej pary.
         * Wypisz osobno keySet() i values().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ForEachLambda {
        /*
         * 🧪 Zadanie 6:
         * LinkedHashMap<String,Integer> wiek = {"Jan"->30,"Kasia"->25,"Piotr"->40}.
         * Użyj metody forEach((k, v) -> ...) do wypisania każdej pary w formacie "Jan ma 30 lat".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SizeIsEmptyClear {
        /*
         * 🧪 Zadanie 7:
         * LinkedHashMap: dodaj 4 pary, wypisz size() i isEmpty().
         * Wywołaj clear(), sprawdź size() i isEmpty() ponownie.
         * Dodaj nową parę po clear() i wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConstructFromMap {
        /*
         * 🧪 Zadanie 8:
         * Istniejący HashMap<String,Integer> z 5 parami.
         * Zbuduj z niego LinkedHashMap konstruktorem kopiującym.
         * Wypisz oba – zauważ, że kolejność LinkedHashMap zależy od kolejności iteracji HashMap w momencie kopiowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MergeCounter {
        /*
         * 🧪 Zadanie 9:
         * Tablica String[] słowa = {"kot","pies","kot","ryba","pies","kot"}.
         * Zlicz wystąpienia każdego słowa metodą merge(word, 1, Integer::sum) w LinkedHashMap.
         * Wypisz wynik w kolejności pierwszego napotkania słowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NullKeyAllowed {
        /*
         * 🧪 Zadanie 10:
         * LinkedHashMap<String,Integer> dane = {"A"->1,"B"->2}.
         * Dodaj wpis z kluczem null: put(null, 99). Sprawdź get(null) i wypisz całą mapę.
         * (LinkedHashMap, tak jak HashMap, dopuszcza jeden null-klucz).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AccessOrderBasics {
        /*
         * 🧪 Zadanie 11:
         * Utwórz LinkedHashMap<String,Integer>(16, 0.75f, true) – tryb access-order.
         * Dodaj "A"->1, "B"->2, "C"->3. Wywołaj get("A"), a następnie wypisz keySet()
         * żeby zaobserwować, że "A" przeskoczyło na koniec kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InsertionVsAccessOrderComparison {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj dwie mapy z tymi samymi operacjami put/get: jedną w trybie insertion-order,
         * drugą w trybie access-order. Wykonaj sekwencję: put A,B,C, get(B), put D.
         * Wypisz obie mapy i porównaj różnice w kolejności kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WordFrequencyOrdered {
        /*
         * 🧪 Zadanie 13:
         * Tekst: "ala ma kota ala ma psa kot i pies to zwierzeta ala".
         * Policz częstość każdego słowa w LinkedHashMap<String,Integer> (merge)
         * i wypisz wynik w kolejności pierwszego wystąpienia słowa w tekście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SimpleLRUCacheSkeleton {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj klasę SimpleLRUCache<K,V> opakowującą LinkedHashMap z accessOrder=true
         * i nadpisaną metodą removeEldestEntry zwracającą true gdy size() > capacity.
         * Skonstruuj cache o pojemności 3 i dodaj 3 elementy – wypisz stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LRUEvictionDemo {
        /*
         * 🧪 Zadanie 15:
         * Użyj cache z Zadania 14 (pojemność 3): dodaj "P1","P2","P3", odczytaj get("P1"),
         * potem dodaj "P4". Wypisz zawartość cache po każdym kroku i wskaż, który element
         * został usunięty i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TopNRecentItems {
        /*
         * 🧪 Zadanie 16:
         * Symuluj listę ostatnio oglądanych produktów jako LinkedHashMap<String,Integer>
         * (klucz=produkt, wartość=licznik wyświetleń) w trybie access-order z limitem 5 pozycji
         * (removeEldestEntry). Dodaj 8 odczytów produktów i wypisz finalny stan (top 5 najświeższych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MapValuesTransformation {
        /*
         * 🧪 Zadanie 17:
         * LinkedHashMap<String,Integer> ceny_netto = {"A"->100,"B"->200,"C"->300}.
         * Zbuduj nową LinkedHashMap<String,Double> ceny_brutto z cenami powiększonymi o 23% VAT,
         * zachowując kolejność kluczy z mapy źródłowej. Użyj forEach lub computeIfAbsent.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GroupByFirstLetterOrdered {
        /*
         * 🧪 Zadanie 18:
         * Lista imion: {"Adam","Beata","Anna","Bartek","Celina","Alicja"}.
         * Zbuduj LinkedHashMap<Character, List<String>> grupującą imiona po pierwszej literze,
         * zachowując kolejność liter według pierwszego napotkanego imienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RemoveEldestCustomRule {
        /*
         * 🧪 Zadanie 19:
         * Nadpisz removeEldestEntry tak, żeby usuwała najstarszy wpis TYLKO gdy jego wartość
         * (Integer licznik) jest mniejsza niż 2, niezależnie od rozmiaru mapy.
         * Przetestuj na kilku wstawieniach i wypisz efekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareIterationAfterReinsert {
        /*
         * 🧪 Zadanie 20:
         * LinkedHashMap insertion-order {"A"->1,"B"->2,"C"->3}.
         * Usuń "B" i dodaj ponownie put("B", 2). Wypisz mapę i zauważ, że "B" trafiło na koniec
         * (ponowne wstawienie liczy się jako nowy wpis w kolejności wstawiania).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLRUCacheClass {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj kompletną klasę LRUCache<K,V> z metodami put(k,v), get(k) zwracającą V lub null,
         * opartą o LinkedHashMap(accessOrder=true) i removeEldestEntry z parametryzowaną pojemnością.
         * Przetestuj scenariusz z 6 operacjami put/get na pojemności 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LFUApproximationWithLinkedHashMap {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj strukturę zliczającą dostęp do kluczy (Map<String,Integer> licznik)
         * połączoną z LinkedHashMap<String,String> danymi w trybie access-order, symulując
         * prosty cache zbliżony do LFU (usuwanie na podstawie najrzadziej używanych, w przybliżeniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SessionExpiryTracker {
        /*
         * 🧪 Zadanie 23:
         * Symuluj tracker aktywnych sesji użytkowników: LinkedHashMap<String, Long> (userId -> timestamp)
         * w trybie access-order. Metoda touch(userId) aktualizuje timestamp i przesuwa na koniec.
         * Metoda expireOld(maxSessions) usuwa najstarsze sesje, gdy przekroczono limit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PageCacheSimulation {
        /*
         * 🧪 Zadanie 24:
         * Symuluj cache stron pamięci (page cache) z algorytmem LRU o pojemności 4 stron.
         * Podaj sekwencję żądań stron {1,2,3,4,1,2,5,1,2,3,4,5} i policz liczbę "cache miss"
         * (strona nieobecna w cache w momencie żądania) używając LinkedHashMap.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_OrderedMultiLevelGrouping {
        /*
         * 🧪 Zadanie 25:
         * Lista zamówień Zamowienie(klient, kategoria, kwota).
         * Zbuduj zagnieżdżoną strukturę LinkedHashMap<String, LinkedHashMap<String, Double>>
         * (klient -> kategoria -> suma kwot), zachowując kolejność pierwszego wystąpienia
         * klientów i kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareLRUvsFIFOEviction {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj dwa cache o pojemności 3: jeden LRU (accessOrder=true), drugi FIFO
         * (accessOrder=false, usuwa zawsze najstarszy wpis niezależnie od odczytów).
         * Wykonaj identyczną sekwencję put/get na obu i porównaj różnice w tym, co zostało wyeksmitowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MemoizationWithLinkedHashMap {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj memoizację funkcji silnia(n) (rekurencyjnej) z użyciem LinkedHashMap
         * jako cache o limitowanej pojemności (np. 5 ostatnio liczonych wyników, LRU).
         * Policz silnia dla kilku powtarzających się argumentów i wypisz stan cache po każdym wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionLedgerOrderedByAccount {
        /*
         * 🧪 Zadanie 28:
         * Lista transakcji Transakcja(konto, kwota, timestamp) w losowej kolejności czasowej.
         * Zbuduj LinkedHashMap<String, List<Transakcja>> grupujący transakcje po koncie,
         * zachowując kolejność kont według pierwszej transakcji, a w każdej liście – kolejność
         * transakcji tego konta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GraphTraversalOrderRecordedInMap {
        /*
         * 🧪 Zadanie 29:
         * Graf Map<Integer, List<Integer>>. Wykonaj BFS od wierzchołka startowego, zapisując
         * do LinkedHashMap<Integer,Integer> (wierzchołek -> odległość od startu) w kolejności odwiedzin.
         * Wypisz mapę na końcu – kolejność kluczy powinna odzwierciedlać kolejność BFS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TwoLevelLRUWithEvictionLog {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj cache LRU o pojemności 3 (LinkedHashMap + removeEldestEntry), który
         * dodatkowo loguje każdą eksmisję do osobnej listy (List<String> evictionLog: "usunięto klucz X").
         * Wykonaj sekwencję 10 operacji put/get i wypisz finalny stan cache oraz pełny log eksmisji.
         */
        public static void main(String[] args) { }
    }
}
