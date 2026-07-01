package com.example.javaquest._03_collections.Lesson03_CollectionMethods;

public class _Exercises_Lesson03_CollectionMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_Sort {
        /*
         * 🧪 Zadanie 1:
         * Lista: {5, 2, 8, 1, 9, 3}. Posortuj rosnąco i malejąco.
         * Lista imion: {"Zofia","Adam","Maria"}. Posortuj alfabetycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_Reverse {
        /*
         * 🧪 Zadanie 2:
         * Lista: {1, 2, 3, 4, 5}. Odwróć ją (Collections.reverse).
         * Sprawdź czy po odwróceniu jest posortowana malejąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_Shuffle {
        /*
         * 🧪 Zadanie 3:
         * Lista kart: {"As","Król","Dama","Walet","10","9"}.
         * Tasuj 3 razy i wypisz każdy wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MinMax {
        /*
         * 🧪 Zadanie 4:
         * Lista: {7, 2, 9, 1, 5, 8, 3}.
         * Znajdź min i max przez Collections.min/max.
         * Znajdź min i max z własnym Comparatorem (po absolutnej wartości).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_Frequency {
        /*
         * 🧪 Zadanie 5:
         * Lista: {"java","python","java","c++","java","python"}.
         * Zlicz częstość każdego języka przez Collections.frequency.
         * Wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_Fill {
        /*
         * 🧪 Zadanie 6:
         * Stwórz listę 10 elementów (dowolne). Wypełnij ją przez Collections.fill("X").
         * Wypisz przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_Copy {
        /*
         * 🧪 Zadanie 7:
         * Lista src: {1, 2, 3}. Lista dest: {0, 0, 0, 0, 0}.
         * Skopiuj src do dest (Collections.copy).
         * Wypisz dest – co stało się z pozostałymi elementami?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BinarySearch {
        /*
         * 🧪 Zadanie 8:
         * Lista posortowana: {2, 4, 6, 8, 10, 12, 14}.
         * Szukaj: 8 (jest), 7 (brak), 14 (jest), 1 (brak).
         * Wypisz indeksy i interpretację wyników ujemnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NcopiesSwap {
        /*
         * 🧪 Zadanie 9:
         * Stwórz listę 5 kopii "hello" przez Collections.nCopies.
         * Zamień miejscami element 0 i 4 (Collections.swap).
         * Wypisz przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_Unmodifiable {
        /*
         * 🧪 Zadanie 10:
         * Stwórz mutable listę. Opakuj w unmodifiableList.
         * Spróbuj dodać element do unmodifiable – złap wyjątek.
         * Sprawdź czy modyfikacja oryginału wpływa na unmodifiable.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SortByLength {
        /*
         * 🧪 Zadanie 11:
         * Lista słów: {"banana","fig","apple","kiwi","cherry","date"}.
         * Posortuj: po długości rosnąco, a przy tej samej długości alphabetycznie.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TopBottomN {
        /*
         * 🧪 Zadanie 12:
         * Lista: {3,1,4,1,5,9,2,6,5,3}.
         * Bez modyfikacji oryginału: znajdź 3 największe i 3 najmniejsze elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RotateRight {
        /*
         * 🧪 Zadanie 13:
         * Rotuj listę {1..8} o 3 w prawo (Collections.rotate).
         * Wypisz wynik. Rotuj o -3 (czyli 3 w lewo) i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_Disjoint {
        /*
         * 🧪 Zadanie 14:
         * Sprawdź 3 pary list czy są rozłączne (Collections.disjoint).
         * A={1,2,3}, B={4,5,6} → true
         * A={1,2,3}, B={3,4,5} → false
         * A={"java"}, B={"python","java"} → false
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MixedSort {
        /*
         * 🧪 Zadanie 15:
         * Lista obiektów Person z polami name i age.
         * Posortuj: najpierw po wieku malejąco, przy równym wieku po nazwisku.
         * Przetestuj z 5 osobami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_Replicate {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę replicate(List, n) → nowa lista z każdym elementem
         * powtórzonym n razy. [a,b,c], n=3 → [a,a,a,b,b,b,c,c,c].
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SortByMultiple {
        /*
         * 🧪 Zadanie 17:
         * Lista produktów z polami: name, category, price.
         * Posortuj: najpierw po kategorii, w kategorii po cenie, przy tej samej cenie po nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FindKth {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę findKthSmallest(List<Integer>, k) bez pełnego sortowania.
         * Używaj Collections.sort na kopii. Przetestuj dla k=1,3,5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_Median {
        /*
         * 🧪 Zadanie 19:
         * Lista: {3,1,4,1,5,9,2,6}.
         * Oblicz medianę: posortuj, jeśli n parzyste → średnia dwóch środkowych.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MergeSorted {
        /*
         * 🧪 Zadanie 20:
         * Scal N posortowanych list w jedną posortowaną bez Stream API.
         * Wskazówka: użyj PriorityQueue lub wielokrotne addAll + sort.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExternalSort {
        /*
         * 🧪 Zadanie 21:
         * Symuluj external sort: podziel listę na 3 "fragmenty", posortuj każdy,
         * scal je w jedną posortowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_StableSort {
        /*
         * 🧪 Zadanie 22:
         * Collections.sort jest stabilne. Udowodnij to: posortuj obiekty po polu A,
         * potem po polu B – obiekty z równym B powinny być w kolejności z pierwszego sortowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RankList {
        /*
         * 🧪 Zadanie 23:
         * Lista wyników: {85, 92, 78, 92, 88, 65}.
         * Przypisz rangi: najwyższy wynik = ranga 1 (przy równości ta sama ranga).
         * Wypisz każdy wynik z rangą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_Percentile {
        /*
         * 🧪 Zadanie 24:
         * Oblicz percentyle (25., 50., 75., 90.) dla listy 20 losowych liczb.
         * Wypisz wartości tych percentyli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GroupBySort {
        /*
         * 🧪 Zadanie 25:
         * Lista: {1,5,3,2,7,4,6,8}. Podziel na dwie grupy: ≤4 i >4.
         * Każdą grupę posortuj osobno. Scal: najpierw posortowana mniejsza, potem większa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ShufflePredict {
        /*
         * 🧪 Zadanie 26:
         * Użyj Collections.shuffle z Random(42) (seeded).
         * Pokaż że dwa shuffle z tym samym seed dają ten sam wynik.
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_Anagram {
        /*
         * 🧪 Zadanie 27:
         * Napisz isAnagram(String a, String b) → boolean.
         * Wskazówka: podziel na znaki, posortuj obie listy, porównaj.
         * Przetestuj: "listen"/"silent", "hello"/"world".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_Inversion {
        /*
         * 🧪 Zadanie 28:
         * Policz inwersje w liście {3,1,2}: pary (i,j) gdzie i<j ale list[i]>list[j].
         * Wypisz liczby inwersji dla kilku list.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_Zigzag {
        /*
         * 🧪 Zadanie 29:
         * Zorganizuj listę {1,5,3,2,7,4} w "zigzag": a < b > c < d > e...
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CustomSort {
        /*
         * 🧪 Zadanie 30:
         * Posortuj listę Stringów w specjalnej kolejności:
         * najpierw liczby, potem wielkie litery, potem małe litery.
         * Wewnątrz każdej grupy naturalnie.
         * Wejście: {"b", "A", "1", "c", "3", "B", "2", "a"}
         */
        public static void main(String[] args) { }
    }
}
