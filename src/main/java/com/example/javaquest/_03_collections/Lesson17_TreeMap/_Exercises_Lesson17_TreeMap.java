package com.example.javaquest._03_collections.Lesson17_TreeMap;

public class _Exercises_Lesson17_TreeMap {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSortedInsert {
        /*
         * 🧪 Zadanie 1:
         * TreeMap<String,Integer>: dodaj "Zosia"->1, "Adam"->2, "Marek"->3, "Beata"->4.
         * Wypisz mapę – sprawdź, że klucze są posortowane alfabetycznie, mimo innej kolejności wstawiania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HashMapVsTreeMap {
        /*
         * 🧪 Zadanie 2:
         * Wstaw te same 5 par klucz-wartość (Integer klucze: 50, 10, 30, 20, 40) najpierw
         * do HashMap, potem do TreeMap. Wypisz obie i porównaj kolejność kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BasicOperations {
        /*
         * 🧪 Zadanie 3:
         * TreeMap<Integer,String> mapa = {3->"c", 1->"a", 2->"b"}.
         * Wywołaj get(2), containsKey(5), remove(1). Wypisz mapę po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FirstAndLastKey {
        /*
         * 🧪 Zadanie 4:
         * TreeMap<Integer,String> {15->"a", 3->"b", 42->"c", 8->"d"}.
         * Wypisz firstKey(), lastKey(), firstEntry() i lastEntry().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FloorCeilingBasics {
        /*
         * 🧪 Zadanie 5:
         * TreeMap<Integer,String> {10->"a", 20->"b", 30->"c", 40->"d"}.
         * Sprawdź floorKey(25), ceilingKey(25), floorKey(10), ceilingKey(40).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LowerHigherBasics {
        /*
         * 🧪 Zadanie 6:
         * TreeMap<Integer,String> {10->"a", 20->"b", 30->"c"}.
         * Sprawdź lowerKey(20) (powinno pominąć 20) i higherKey(20) (powinno pominąć 20).
         * Sprawdź też lowerKey(10) i higherKey(30) – co zwracają dla granicznych kluczy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NullKeyThrows {
        /*
         * 🧪 Zadanie 7:
         * TreeMap<String,Integer> mapa = {"A"->1}.
         * Spróbuj wywołać put(null, 5) w bloku try-catch i wypisz nazwę złapanego wyjątku
         * (NullPointerException) – wyjaśnij komentarzem dlaczego TreeMap nie akceptuje null-klucza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IterateSorted {
        /*
         * 🧪 Zadanie 8:
         * TreeMap<String,Integer> {"Wtorek"->2,"Poniedziałek"->1,"Środa"->3}.
         * Zaiteruj przez entrySet() pętlą for-each i wypisz pary w kolejności posortowanej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_KeySetAndValues {
        /*
         * 🧪 Zadanie 9:
         * TreeMap<Integer,String> {5->"e",1->"a",3->"c",2->"b",4->"d"}.
         * Wypisz keySet() (powinno być posortowane 1,2,3,4,5) oraz values()
         * (posortowane według kluczy: a,b,c,d,e).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SizeIsEmptyClear {
        /*
         * 🧪 Zadanie 10:
         * TreeMap: dodaj 4 pary, wypisz size() i isEmpty().
         * Wywołaj clear(), sprawdź size() i isEmpty() ponownie.
         * Dodaj nową parę po clear() i wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_HeadTailSubMap {
        /*
         * 🧪 Zadanie 11:
         * TreeMap<Integer,String> {10->"a",20->"b",30->"c",40->"d",50->"e"}.
         * Wypisz headMap(30), tailMap(30), subMap(20, 50).
         * Zaznacz w komentarzu które granice są inclusive a które exclusive.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InclusiveHeadTailSubMap {
        /*
         * 🧪 Zadanie 12:
         * Ten sam TreeMap co w Zadaniu 11.
         * Użyj przeciążonych wersji: headMap(30, true), tailMap(30, false),
         * subMap(20, true, 50, true). Porównaj wyniki z Zadaniem 11.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CustomComparatorDescending {
        /*
         * 🧪 Zadanie 13:
         * Utwórz TreeMap<String,Integer> z Comparator.reverseOrder() – klucze posortowane malejąco.
         * Dodaj "Ala"->1,"Bartek"->2,"Celina"->3. Wypisz mapę i sprawdź firstKey()/lastKey().
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ComparatorByLength {
        /*
         * 🧪 Zadanie 14:
         * Utwórz TreeMap<String,Integer> z komparatorem sortującym po długości Stringa,
         * a przy równej długości alfabetycznie (thenComparing).
         * Dodaj "Ala","Bartek","Ola","Marek","Ewa" z dowolnymi wartościami. Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DescendingMapAndKeySet {
        /*
         * 🧪 Zadanie 15:
         * TreeMap<Integer,String> {1->"a",2->"b",3->"c",4->"d"}.
         * Wypisz descendingMap() oraz descendingKeySet().
         * Sprawdź, że oryginalna mapa scores pozostała niezmieniona (rosnąca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PollFirstLast {
        /*
         * 🧪 Zadanie 16:
         * TreeMap<Integer,String> {5->"e",1->"a",3->"c"}.
         * Wywołaj pollFirstEntry() i pollLastEntry() – wypisz zwrócone wpisy
         * oraz stan mapy po każdym wywołaniu (elementy są usuwane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FrequencyMapSorted {
        /*
         * 🧪 Zadanie 17:
         * Tablica String[] słowa = {"banan","jabłko","gruszka","jabłko","banan","banan"}.
         * Policz częstość każdego słowa w TreeMap<String,Integer> (merge) –
         * wynik będzie automatycznie posortowany alfabetycznie po słowach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RangeQuerySalary {
        /*
         * 🧪 Zadanie 18:
         * TreeMap<Integer,String> pracownicy = {3000->"Ala",4500->"Bartek",5200->"Celina",
         * 3800->"Damian",6000->"Ewa"} (klucz = pensja).
         * Znajdź wszystkich pracowników z pensją w przedziale [3800, 5500] używając subMap.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ClosestKeyLookup {
        /*
         * 🧪 Zadanie 19:
         * TreeMap<Integer,String> punkty_cenowe = {100->"tani",250->"średni",500->"drogi",1000->"luksusowy"}.
         * Napisz metodę znajdującą najbliższą kategorię cenową dla podanej ceny (np. 300)
         * porównując wynik floorKey i ceilingKey względem odległości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NavigableSetFromKeys {
        /*
         * 🧪 Zadanie 20:
         * TreeMap<Integer,String> mapa z 6 kluczami.
         * Pobierz navigableKeySet(), wypisz go rosnąco i malejąco (descendingSet()).
         * Sprawdź higher/lower na navigableKeySet zamiast bezpośrednio na mapie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TimeBasedEventLookup {
        /*
         * 🧪 Zadanie 21:
         * TreeMap<Long,String> zdarzenia (timestamp -> opis) z kilkunastoma wpisami.
         * Napisz getEventNear(long timestamp) zwracającą najbliższe zdarzenie (floor lub ceiling,
         * wybierając to bliższe w czasie). Przetestuj dla timestampu spoza zbioru kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LeaderboardTopN {
        /*
         * 🧪 Zadanie 22:
         * TreeMap<Integer,String> wyniki (wynik -> gracz), gdzie klucz to wynik gry.
         * Użyj descendingMap() lub descendingKeySet(), żeby wypisać top 3 najlepszych graczy.
         * Dodaj obsługę remisów – dwóch graczy z tym samym wynikiem (przemyśl strukturę wartości).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IntervalMerging {
        /*
         * 🧪 Zadanie 23:
         * Lista przedziałów czasowych [start, end] (int[][]).
         * Zbuduj TreeMap<Integer,Integer> (start -> end) i połącz zachodzące na siebie przedziały
         * iterując po posortowanych kluczach (entrySet w kolejności rosnącej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WordDictionaryRangeSearch {
        /*
         * 🧪 Zadanie 24:
         * TreeMap<String,String> słownik (słowo -> definicja) z kilkunastoma hasłami.
         * Zaimplementuj wyszukiwanie "wszystkie słowa zaczynające się na dany prefiks"
         * korzystając z subMap(prefix, prefix + Character.MAX_VALUE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SlidingWindowMedianWithTreeMap {
        /*
         * 🧪 Zadanie 25:
         * Dla tablicy liczb i rozmiaru okna k, policz medianę każdego przesuwającego się okna
         * używając TreeMap<Integer,Integer> jako zrównoważonej struktury (klucz=liczba, wartość=licznik wystąpień)
         * zamiast sortowania okna za każdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CurrencyExchangeRangeRates {
        /*
         * 🧪 Zadanie 26:
         * TreeMap<Double,Double> progi_kwot (minimalna kwota -> stawka prowizji), np.
         * 0.0->0.05, 1000.0->0.03, 5000.0->0.01.
         * Dla podanej kwoty transakcji znajdź właściwą stawkę prowizji metodą floorEntry.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GraphDijkstraWithTreeMap {
        /*
         * 🧪 Zadanie 27:
         * Prosty graf ważony Map<Integer, Map<Integer,Integer>>.
         * Zaimplementuj uproszczony algorytm Dijkstry, używając TreeMap<Integer,Integer>
         * (odległość -> wierzchołek) jako zastępstwa kolejki priorytetowej (klucz = odległość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombineTreeMapWithLinkedHashMap {
        /*
         * 🧪 Zadanie 28:
         * Lista zamówień Zamowienie(data, klient, kwota).
         * Zbuduj TreeMap<LocalDate, LinkedHashMap<String,Double>> (data -> klient -> suma kwot),
         * gdzie daty są posortowane chronologicznie, a klienci w każdej dacie zachowują
         * kolejność pierwszego wystąpienia tego dnia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RangeSumQueries {
        /*
         * 🧪 Zadanie 29:
         * TreeMap<Integer,Integer> sprzedaz_dzienna (dzień_miesiąca -> kwota sprzedaży).
         * Napisz metodę sumInRange(from, to) zwracającą sumę sprzedaży w danym zakresie dni
         * używając subMap(from, true, to, true) i sumowania wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MultiKeyPriorityScheduler {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj prosty scheduler zadań: TreeMap<Integer, List<String>> (priorytet -> lista zadań
         * o tym priorytecie, niższy numer = wyższy priorytet). Dodaj metody addTask(priorytet, zadanie)
         * i pollNextTask() zwracającą i usuwającą zadanie o najwyższym priorytecie (najmniejszy klucz),
         * usuwając też pusty wpis mapy gdy lista się wyczerpie.
         */
        public static void main(String[] args) { }
    }
}
