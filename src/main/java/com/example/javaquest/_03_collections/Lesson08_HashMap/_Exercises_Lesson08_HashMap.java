package com.example.javaquest._03_collections.Lesson08_HashMap;

public class _Exercises_Lesson08_HashMap {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicPutGet {
        /*
         * 🧪 Zadanie 1:
         * HashMap<String,Integer>: dodaj pary "Anna"->28, "Bartek"->34, "Celina"->22.
         * Wypisz całą mapę i jej rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OverwriteValue {
        /*
         * 🧪 Zadanie 2:
         * HashMap<String,Integer>: put("x", 1), potem put("x", 2).
         * Wypisz get("x") – sprawdź że wartość została nadpisana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PutIfAbsent {
        /*
         * 🧪 Zadanie 3:
         * Mapa z kluczem "Anna"->95. Wywołaj putIfAbsent("Anna", 0) i putIfAbsent("Ewa", 85).
         * Wypisz mapę – sprawdź, która wartość się zmieniła, a która nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetVsGetOrDefault {
        /*
         * 🧪 Zadanie 4:
         * Mapa z 3 wpisami. Wywołaj get("brakujacyKlucz") i getOrDefault("brakujacyKlucz", -1).
         * Wypisz oba wyniki i porównaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ContainsKeyValue {
        /*
         * 🧪 Zadanie 5:
         * Mapa Map<String,Integer> {"a":1,"b":2,"c":3}.
         * Sprawdź containsKey("b"), containsKey("z"), containsValue(3), containsValue(9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RemoveEntries {
        /*
         * 🧪 Zadanie 6:
         * Mapa {"a":1,"b":2,"c":3}.
         * Wywołaj remove("b") i wypisz zwróconą wartość oraz mapę.
         * Wywołaj remove("a", 99) (zła wartość – nie powinno usunąć) i remove("c", 3) (powinno usunąć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ClearIsEmpty {
        /*
         * 🧪 Zadanie 7:
         * Mapa z 4 wpisami. Sprawdź isEmpty() przed i po wywołaniu clear().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_KeysValuesEntries {
        /*
         * 🧪 Zadanie 8:
         * Mapa {"a":1,"b":2,"c":3}.
         * Wypisz osobno wynik keySet(), values() i entrySet().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IterationStyles {
        /*
         * 🧪 Zadanie 9:
         * Ta sama mapa {"a":1,"b":2,"c":3}.
         * Iteruj po niej pętlą for-each po entrySet() oraz metodą forEach z lambdą – wypisz oba razy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PutAllMerge {
        /*
         * 🧪 Zadanie 10:
         * Mapa1 {"a":1,"b":2}, Mapa2 {"b":20,"c":3}.
         * Wywołaj mapa1.putAll(mapa2) i wypisz wynikową mapę (sprawdź co się stało z kluczem "b").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WordCount {
        /*
         * 🧪 Zadanie 11:
         * Tekst: "ala ma kota kot ma ale ala ma psa".
         * Zlicz wystąpienia każdego słowa używając merge lub compute. Wypisz wynikową mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CharFrequency {
        /*
         * 🧪 Zadanie 12:
         * String "mississippi".
         * Zlicz wystąpienia każdego znaku w HashMap<Character,Integer> używając merge.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GroupByLength {
        /*
         * 🧪 Zadanie 13:
         * Lista słów {"kot","pies","ryba","słoń","mysz","koń"}.
         * Pogrupuj je w Map<Integer, List<String>> wg długości słowa, używając computeIfAbsent.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_InvertMap {
        /*
         * 🧪 Zadanie 14:
         * Map<String,Integer> {"jeden":1,"dwa":2,"trzy":3} (wartości unikalne).
         * Odwróć mapę na Map<Integer,String> {1:"jeden",2:"dwa",3:"trzy"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MinMaxKey {
        /*
         * 🧪 Zadanie 15:
         * Map<String,Integer> wyników {"Anna":95,"Bartek":78,"Celina":88,"Damian":72}.
         * Znajdź klucz o maksymalnej i minimalnej wartości (bez użycia Streams – iteracja entrySet).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SumOfValues {
        /*
         * 🧪 Zadanie 16:
         * Map<String,Double> cen produktów {"chleb":4.5,"mleko":3.2,"masło":8.9}.
         * Oblicz sumę wszystkich wartości iterując po values().
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FilterMap {
        /*
         * 🧪 Zadanie 17:
         * Map<String,Integer> wyników {"Anna":95,"Bartek":45,"Celina":88,"Damian":30}.
         * Stwórz nową mapę zawierającą tylko wpisy z wartością >= 60.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SortMapByValue {
        /*
         * 🧪 Zadanie 18:
         * Ta sama mapa wyników co wyżej.
         * Przekonwertuj entrySet() na listę i posortuj malejąco po wartości
         * (Comparator.comparing na Map.Entry – powiąż z lekcją o Comparatorach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ModeOfArray {
        /*
         * 🧪 Zadanie 19:
         * Tablica int[] {1,3,2,3,4,3,2,5,3}.
         * Zlicz częstotliwość każdej wartości w HashMap i znajdź modę (najczęstszą wartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ComputeVsMerge {
        /*
         * 🧪 Zadanie 20:
         * Lista zakupów klienta: {"chleb","mleko","chleb","jajka","mleko","chleb"}.
         * Zlicz wystąpienia produktów raz metodą compute(), a raz metodą merge() –
         * dwie osobne mapy, porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TwoSumIndices {
        /*
         * 🧪 Zadanie 21:
         * Tablica {2,7,11,15}, target=9.
         * Używając HashMap<Integer,Integer> (wartość -> indeks) znajdź indeksy dwóch liczb
         * sumujących się do target w czasie O(n).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GroupAnagrams {
        /*
         * 🧪 Zadanie 22:
         * Lista słów {"eat","tea","tan","ate","nat","bat"}.
         * Pogrupuj anagramy w HashMap<String, List<String>>, gdzie kluczem są posortowane litery słowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimpleLRUCache {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj uproszczony LRU Cache o pojemności 3, oparty na HashMap
         * (+ dodatkowa struktura śledząca kolejność użycia, np. LinkedList kluczy).
         * Metody: get(key), put(key,value) – usuwają najdawniej używany element po przekroczeniu limitu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_GraphBFS {
        /*
         * 🧪 Zadanie 24:
         * Graf jako HashMap<Integer, List<Integer>> (lista sąsiedztwa) z 6 wierzchołkami.
         * Zaimplementuj przeszukiwanie BFS od wierzchołka startowego i wypisz kolejność odwiedzin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NestedMapReport {
        /*
         * 🧪 Zadanie 25:
         * Sprzedaż jako HashMap<String, HashMap<String,Integer>> – sklep -> (produkt -> ilość).
         * Np. "Sklep1": {"chleb":10,"mleko":5}, "Sklep2": {"chleb":3,"masło":7}.
         * Wygeneruj raport: łączna sprzedaż każdego produktu we wszystkich sklepach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FibonacciMemoization {
        /*
         * 🧪 Zadanie 26:
         * Napisz rekurencyjną funkcję liczącą n-ty wyraz ciągu Fibonacciego (n=50)
         * z memoizacją w HashMap<Integer,Long> (computeIfAbsent), by uniknąć powtórnych obliczeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TopKFrequentReport {
        /*
         * 🧪 Zadanie 27:
         * Symulowany strumień danych: tablica 30 losowych liczb z zakresu 1-10.
         * Zlicz wystąpienia w HashMap, a następnie wypisz raport top-3 najczęściej występujących
         * wartości (połączenie HashMap + sortowanie wg wartości).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SubarraySumEqualsK {
        /*
         * 🧪 Zadanie 28:
         * Tablica {1,2,3,-3,1,1,1,4,2,-3}, k=3.
         * Znajdź liczbę podtablic sumujących się do k, używając HashMap<Integer,Integer>
         * (prefix-sum -> liczba wystąpień).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_InventorySystem {
        /*
         * 🧪 Zadanie 29:
         * Klasa Product(name, price). System magazynowy HashMap<String, Integer> (nazwa -> ilość)
         * oraz HashMap<String, Product> (nazwa -> dane produktu).
         * Metody: addStock(name, qty) (merge), removeStock(name, qty) (usuń wpis gdy ilość spadnie do 0),
         * totalValue() – suma (cena * ilość) wszystkich produktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BiDirectionalMap {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj klasę BiMap<K,V> opartą na dwóch wewnętrznych HashMap
         * (key->value i value->key), gwarantującą unikalność w obu kierunkach.
         * Metody: put(k,v), getByKey(k), getByValue(v), remove(k).
         * Przetestuj na parach kod kraju <-> nazwa kraju, np. "PL"<->"Polska".
         */
        public static void main(String[] args) { }
    }
}
