package com.example.javaquest._03_collections.Lesson01_ArrayList;

public class _Exercises_Lesson01_ArrayList {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateAndAdd {
        /*
         * 🧪 Zadanie 1:
         * Stwórz ArrayList<String> z imionami: "Anna", "Bartek", "Celina".
         * Dodaj "Damian" na końcu i "Zofia" na pozycji 1.
         * Wypisz całą listę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_AccessElements {
        /*
         * 🧪 Zadanie 2:
         * Masz listę liczb: {10, 20, 30, 40, 50}.
         * Wypisz: pierwszy element, ostatni element, element o indeksie 2.
         * Wypisz indeks liczby 30 i czy lista zawiera 25.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_ModifyElements {
        /*
         * 🧪 Zadanie 3:
         * Lista: {"java", "python", "c++"}.
         * Zastąp element na indeksie 1 przez "kotlin".
         * Wypisz stary element (ten który był) i nową listę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_RemoveElements {
        /*
         * 🧪 Zadanie 4:
         * Lista: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}.
         * Usuń element o indeksie 0, usuń wartość 6 (przez obiekt).
         * Użyj removeIf aby usunąć wszystkie parzyste.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_SizeAndEmpty {
        /*
         * 🧪 Zadanie 5:
         * Stwórz pustą listę. Sprawdź isEmpty() – wypisz wynik.
         * Dodaj 5 elementów. Sprawdź size() i isEmpty() – wypisz wyniki.
         * Wyczyść listę (clear()) i znowu sprawdź.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_SubList {
        /*
         * 🧪 Zadanie 6:
         * Lista: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}.
         * Pobierz podlistę [2, 7) – wypisz ją.
         * Podlista to widok – wyczyść ją (subList.clear()).
         * Wypisz oryginał – jakie elementy zostały?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_AddAll {
        /*
         * 🧪 Zadanie 7:
         * Lista A: {"a", "b", "c"}, Lista B: {"d", "e", "f"}.
         * Dodaj całą listę B do A (addAll).
         * Sprawdź czy A zawiera wszystkie elementy B (containsAll).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ToArray {
        /*
         * 🧪 Zadanie 8:
         * Lista: {"raz", "dwa", "trzy"}.
         * Konwertuj na String[] przez toArray(new String[0]).
         * Wypisz tablicę używając Arrays.toString().
         * Sprawdź czy modyfikacja tablicy wpływa na listę (sprawdź listę).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_IndexOf {
        /*
         * 🧪 Zadanie 9:
         * Lista: {"a", "b", "c", "b", "a", "b"}.
         * Znajdź: indexOf("b"), lastIndexOf("b"), indexOf("z").
         * Wypisz wyniki i wyjaśnij co oznaczają.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_CopyList {
        /*
         * 🧪 Zadanie 10:
         * Stwórz listę oryginalną z 5 elementami.
         * Stwórz kopię przez new ArrayList<>(oryginalna).
         * Dodaj element do kopii i zmodyfikuj jeden element.
         * Sprawdź czy oryginał się zmienił – wypisz oba.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ShoppingCart {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj prosty koszyk zakupów jako ArrayList<String>.
         * Operacje: addItem(String), removeItem(String), getTotal() (liczba pozycji),
         * displayCart(), clearCart().
         * Przetestuj wszystkie operacje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_UniqueList {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę addUnique(ArrayList<Integer>, int) która dodaje
         * element tylko jeśli nie istnieje w liście.
         * Przetestuj z: 1, 2, 3, 2, 4, 3, 5 – wynik powinien to być lista unikalnych.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_SortAndSearch {
        /*
         * 🧪 Zadanie 13:
         * Lista: {64, 34, 25, 12, 22, 11, 90}.
         * Posortuj rosnąco (Collections.sort).
         * Znajdź pozycję 25 przez Collections.binarySearch.
         * Posortuj malejąco przez list.sort(Comparator.reverseOrder()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_Rotate {
        /*
         * 🧪 Zadanie 14:
         * Lista: {1, 2, 3, 4, 5}.
         * Wykonaj obrót o 2 w prawo (Collections.rotate).
         * Wypisz wynik (powinno być {4, 5, 1, 2, 3}).
         * Wykonaj obrót o -1 i wypisz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_Disjoint {
        /*
         * 🧪 Zadanie 15:
         * Sprawdź czy dwie listy mają wspólne elementy (Collections.disjoint).
         * Lista A: {1, 2, 3}, Lista B: {4, 5, 6} – disjoint?
         * Lista A: {1, 2, 3}, Lista C: {3, 4, 5} – disjoint?
         * Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_Batch {
        /*
         * 🧪 Zadanie 16:
         * Lista A: {1, 2, 3, 4, 5, 6}.
         * Lista B: {2, 4, 6}.
         * Utwórz kopię A. Usuń z niej elementy B (removeAll).
         * Utwórz kopię A. Zachowaj tylko elementy B (retainAll).
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_Replace {
        /*
         * 🧪 Zadanie 17:
         * Lista: {"hello", "world", "java"}.
         * Użyj list.replaceAll(String::toUpperCase) aby zamienić na wielkie.
         * Potem replaceAll aby odwrócić każde słowo (StringBuilder reverse).
         * Wypisz po każdej operacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_TopN {
        /*
         * 🧪 Zadanie 18:
         * Lista: {5, 2, 8, 1, 9, 3, 7, 4, 6, 10}.
         * Znajdź top 3 największe wartości bez sortowania oryginału.
         * Wskazówka: utwórz kopię, posortuj, weź subList.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_Flatten {
        /*
         * 🧪 Zadanie 19:
         * Masz listę list: [[1,2,3], [4,5], [6,7,8,9]].
         * Spłaszcz do jednej listy [1,2,3,4,5,6,7,8,9] bez Stream API.
         * Użyj zagnieżdżonych pętli i addAll.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Pagination {
        /*
         * 🧪 Zadanie 20:
         * Masz listę 20 elementów (liczby 1-20).
         * Napisz metodę getPage(List, pageNumber, pageSize) zwracającą
         * odpowiednią stronę jako nową listę.
         * Wypisz strony 1, 2, 3 przy pageSize=5.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SparseArray {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prostą macierz rzadką (sparse matrix) jako
         * ArrayList<int[]> gdzie int[] = {row, col, value}.
         * Metody: set(r,c,v), get(r,c), getRow(r) → lista wartości.
         * Przetestuj z macierzą 5x5 z 5 nie-zerowymi elementami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_Merge {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę merge(ArrayList<Integer> a, ArrayList<Integer> b)
         * → nowa ArrayList posortowanych elementów z obu list (merge sort merge step).
         * Zakłada że a i b są już posortowane.
         * Przetestuj: a={1,3,5,7}, b={2,4,6,8} → {1,2,3,4,5,6,7,8}
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Matrix {
        /*
         * 🧪 Zadanie 23:
         * Stwórz macierz NxN jako ArrayList<ArrayList<Integer>>.
         * Wypełnij ją liczbami 1..N² (row-major).
         * Wypisz jako tabelkę. Napisz transpose() → nowa macierz transponowana.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_RunLengthEncoding {
        /*
         * 🧪 Zadanie 24:
         * Napisz RLE kompresję używając ArrayList.
         * Input: "aaabbccdddd" → Lista par [{a,3},{b,2},{c,2},{d,4}]
         * Napisz też decode(List<int[]>) → String.
         * Przetestuj encode i decode.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_FrequencyMap {
        /*
         * 🧪 Zadanie 25:
         * Dana lista słów. Zlicz ich częstości bez HashMap (tylko ArrayList).
         * Wypisz słowa z częstościami od najczęstszych do rzadkich.
         * Wskazówka: ArrayList par [słowo, liczba], szukaj przez contains.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ObserverList {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj Event System:
         * EventBus z ArrayList<EventListener>, addEventListener, removeEventListener, emit(event).
         * Interfejs EventListener z onEvent(String).
         * Dodaj 3 listenerów, wyemituj zdarzenie, usuń jeden, wyemituj ponownie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_VersionHistory {
        /*
         * 🧪 Zadanie 27:
         * Klasa VersionHistory<T> przechowuje historię wersji obiektu.
         * Metody: save(T), undo() → T, redo() → T, getHistory().
         * Przetestuj na historii edycji dokumentu (String).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_MultiMap {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj MultiMap<K,V> – jeden klucz → wiele wartości.
         * Wewnętrznie ArrayList<K[]> albo ArrayList<Object[]>.
         * Metody: put(k,v), getAll(k) → List<V>, remove(k), size().
         * Przetestuj: jeden klucz z 3 wartościami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_TwoSum {
        /*
         * 🧪 Zadanie 29:
         * Dany ArrayList liczb i target. Znajdź wszystkie pary (i,j) gdzie
         * list[i] + list[j] = target (i < j).
         * Zwróć jako ArrayList<int[]> (pary indeksów).
         * Przetestuj: {2,7,11,15,1,8}, target=9 → {(0,1),(4,5)}
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_DynamicCircularBuffer {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj CircularBuffer<T> na bazie ArrayList z ograniczoną pojemnością.
         * Gdy pełny i dodajesz → usuwa najstarszy (FIFO).
         * Metody: add(T), size(), isFull(), get(index), toList().
         * Pojemność 3: add(1,2,3,4,5) → [3,4,5].
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
