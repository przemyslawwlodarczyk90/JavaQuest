package com.example.javaquest._03_collections.Lesson06_TreeSet;

public class _Exercises_Lesson06_TreeSet {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicAdd {
        /*
         * 🧪 Zadanie 1:
         * TreeSet<Integer>: dodaj 7,2,9,2,5,1 (duplikat 2 ma zostać zignorowany).
         * Wypisz zawartość (powinna być posortowana rosnąco) i rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StringSort {
        /*
         * 🧪 Zadanie 2:
         * TreeSet<String>: dodaj "pies","kot","aardvark","zebra".
         * Wypisz zawartość – powinna być alfabetyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FirstLast {
        /*
         * 🧪 Zadanie 3:
         * TreeSet<Integer> z {10,20,30,40,50}.
         * Wypisz first() i last().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FloorCeiling {
        /*
         * 🧪 Zadanie 4:
         * Ten sam zbiór {10,20,30,40,50}.
         * Wypisz floor(25), ceiling(25), floor(10), ceiling(50).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LowerHigher {
        /*
         * 🧪 Zadanie 5:
         * Ten sam zbiór {10,20,30,40,50}.
         * Wypisz lower(30), higher(30), lower(10) (powinno być null), higher(50) (powinno być null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_HeadTailSet {
        /*
         * 🧪 Zadanie 6:
         * Ten sam zbiór {10,20,30,40,50}.
         * Wypisz headSet(30), headSet(30,true), tailSet(30), tailSet(30,false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SubSet {
        /*
         * 🧪 Zadanie 7:
         * Ten sam zbiór {10,20,30,40,50}.
         * Wypisz subSet(20,40) oraz subSet(20,true,40,true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DescendingView {
        /*
         * 🧪 Zadanie 8:
         * TreeSet<Integer> {5,3,8,1,9}.
         * Wypisz descendingSet(). Następnie przejdź po zbiorze descendingIterator()
         * i wypisz elementy malejąco pętlą while.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PollFirstLast {
        /*
         * 🧪 Zadanie 9:
         * TreeSet<Integer> {5,3,8,1,9}.
         * Wywołaj pollFirst(), wypisz zwróconą wartość i stan zbioru.
         * Wywołaj pollLast(), wypisz zwróconą wartość i stan zbioru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ComparatorSort {
        /*
         * 🧪 Zadanie 10:
         * TreeSet<String> z Comparatorem sortującym po długości słowa
         * (Comparator.comparingInt(String::length)).
         * Dodaj "banana","fig","apple","kiwi","cherry". Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TopThreeScores {
        /*
         * 🧪 Zadanie 11:
         * TreeSet<Integer> wyników: {56,78,90,45,88,72,99,61}.
         * Znajdź 3 najwyższe wyniki używając descendingSet() lub pollLast() (na kopii zbioru).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RangeCount {
        /*
         * 🧪 Zadanie 12:
         * TreeSet<Integer> liczb od 1 do 100 co 3 (3,6,9,...,99).
         * Policz ile elementów mieści się w przedziale [20,50] używając subSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ClosestValue {
        /*
         * 🧪 Zadanie 13:
         * TreeSet<Integer> {10,25,37,48,60}. Dla wartości target=40
         * znajdź najbliższą liczbę w zbiorze porównując floor(target) i ceiling(target).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ComparablePerson {
        /*
         * 🧪 Zadanie 14:
         * Klasa Person(name, age) implementująca Comparable<Person> po polu age.
         * Dodaj do TreeSet<Person> osoby: ("Anna",30), ("Bartek",25), ("Celina",40).
         * Wypisz zbiór – powinien być posortowany po wieku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_HashSetVsTreeSetOrder {
        /*
         * 🧪 Zadanie 15:
         * Te same dane {5,1,4,2,3} dodaj do HashSet<Integer> i TreeSet<Integer>.
         * Wypisz oba zbiory obok siebie i porównaj kolejność iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MergeSortedSets {
        /*
         * 🧪 Zadanie 16:
         * TreeSet A={1,3,5,7}, TreeSet B={2,3,6,7,8}.
         * Scal je (addAll) w nowy TreeSet – wynik ma być posortowany, bez duplikatów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_Median {
        /*
         * 🧪 Zadanie 17:
         * TreeSet<Integer> {12,7,19,3,25,8,15} (7 elementów).
         * Znajdź medianę (środkowy element po posortowaniu) korzystając z iteratora TreeSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_KNearest {
        /*
         * 🧪 Zadanie 18:
         * TreeSet<Integer> {5,10,15,20,25,30,35}. Dla target=22 i k=3
         * znajdź 3 elementy zbioru najbliższe wartości target, używając headSet/tailSet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RemoveRange {
        /*
         * 🧪 Zadanie 19:
         * TreeSet<Integer> liczb od 1 do 50.
         * Usuń wszystkie elementy z przedziału [20,30] wywołując clear() na widoku subSet(20,true,30,true)
         * (widok jest "live" – zmiana wpływa na oryginalny zbiór).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MultiFieldComparator {
        /*
         * 🧪 Zadanie 20:
         * Klasa Product(name, price). TreeSet<Product> z komparatorem: cena malejąco,
         * przy remisie nazwa rosnąco. Dodaj 2 produkty o tej samej cenie i różnych nazwach –
         * sprawdź że oba trafiły do zbioru (comparator nie zwraca 0 dla różnych obiektów).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_KthSmallestLargest {
        /*
         * 🧪 Zadanie 21:
         * TreeSet<Integer> {17,4,29,8,33,1,19,22}.
         * Znajdź k-ty najmniejszy i k-ty największy element (np. k=3) bez modyfikacji
         * oryginalnego zbioru (pracuj na kopii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NextAvailableSlot {
        /*
         * 🧪 Zadanie 22:
         * System rezerwacji: TreeSet<Integer> zajętych godzin (w minutach od północy)
         * {540,600,660,720}. Napisz metodę findNextFreeSlot(requested), która zwraca
         * najbliższy wolny termin >= requested, sprawdzając ceiling/higher.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_Leaderboard {
        /*
         * 🧪 Zadanie 23:
         * Klasa Player(name, score) z Comparatorem sortującym malejąco po score
         * (przy remisie po nazwie). TreeSet<Player> jako ranking.
         * Metoda getRank(player) zwracająca pozycję w rankingu (użyj headSet.size()+1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MergeIntervals {
        /*
         * 🧪 Zadanie 24:
         * Lista przedziałów [[1,3],[2,6],[8,10],[15,18]] jako klasa Interval(start,end).
         * TreeSet<Interval> posortowany po start. Scal nakładające się przedziały
         * w wynikową listę [[1,6],[8,10],[15,18]].
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MaxNonOverlapping {
        /*
         * 🧪 Zadanie 25:
         * Te same przedziały co w zadaniu 24 plus [[3,5]].
         * Znajdź maksymalną liczbę nienachodzących się przedziałów (interval scheduling),
         * korzystając z TreeSet posortowanego po końcu (end) przedziału.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NearestFreeSeat {
        /*
         * 🧪 Zadanie 26:
         * Sala z miejscami 1-100. TreeSet<Integer> zajętych miejsc {5,6,7,50,51,52,90}.
         * Dla podanego preferowanego miejsca (np. 6) znajdź najbliższe wolne miejsce
         * przeszukując floor/ceiling na przemian w obu kierunkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_OrderStatistics {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj klasę OrderStatisticsSet opakowującą TreeSet<Integer> z metodami:
         * rank(x) – liczba elementów <= x (użyj headSet(x,true).size()),
         * select(k) – k-ty najmniejszy element.
         * Przetestuj na zbiorze {23,5,67,12,45,8,34,19}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SortedSetOperations {
        /*
         * 🧪 Zadanie 28:
         * TreeSet A={5,10,15,20,25}, TreeSet B={15,20,25,30,35}.
         * Oblicz union, intersection, symmetric difference – zachowując posortowaną kolejność wyniku.
         * Porównaj z takim samym działaniem na HashSet (kolejność wyniku będzie inna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TimeBasedBooking {
        /*
         * 🧪 Zadanie 29:
         * System rezerwacji spotkań: TreeSet<Interval> (start,end) posortowany po start.
         * Metoda book(start,end) dodaje przedział TYLKO jeśli nie nachodzi na istniejące
         * (sprawdź sąsiadów przez floor/ceiling po sztucznym Interval(start,start)).
         * Przetestuj dodanie spotkań [9,10],[10,11],[9,30,10,30] (konflikt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SparseArrayNearest {
        /*
         * 🧪 Zadanie 30:
         * Rzadka tablica: TreeSet<Integer> zawiera indeksy "wypełnionych" komórek
         * spośród zakresu 0-1000 (np. {3,17,42,256,999}).
         * Napisz metodę findNearestFilled(index), która zwraca najbliższy wypełniony
         * indeks (mniejszą odległość) do podanego, rozstrzygając remisy na korzyść mniejszego indeksu.
         */
        public static void main(String[] args) { }
    }
}
