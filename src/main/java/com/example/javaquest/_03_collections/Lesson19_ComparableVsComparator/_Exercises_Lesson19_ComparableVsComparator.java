package com.example.javaquest._03_collections.Lesson19_ComparableVsComparator;

public class _Exercises_Lesson19_ComparableVsComparator {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ComparableIntBox {
        /*
         * 🧪 Zadanie 1:
         * Klasa Box(int size) implementująca Comparable<Box> po polu size.
         * Stwórz listę Box(5), Box(2), Box(8), Box(1).
         * Posortuj Collections.sort() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ComparableStringPerson {
        /*
         * 🧪 Zadanie 2:
         * Klasa Person(String name, int age) implementująca Comparable<Person>
         * po polu name (alfabetycznie). Lista: ("Zosia",20), ("Adam",30), ("Beata",25).
         * Posortuj i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareToManually {
        /*
         * 🧪 Zadanie 3:
         * Dwa obiekty Person z zadania 2: p1=("Adam",30), p2=("Zosia",20).
         * Wypisz wynik p1.compareTo(p2) i p2.compareTo(p1) oraz zinterpretuj
         * znak wyniku (dodatni/ujemny/zero).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExternalComparatorAge {
        /*
         * 🧪 Zadanie 4:
         * Użyj klasy Person z zadania 2. Stwórz zewnętrzny Comparator<Person>
         * sortujący po wieku (age) rosnąco, bez zmiany klasy Person.
         * Posortuj listę tym Comparatorem i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ComparatorComparingLambda {
        /*
         * 🧪 Zadanie 5:
         * Lista Stringów: {"banan","kiwi","gruszka","fig"}.
         * Posortuj Comparator.comparing(String::length) – po długości rosnąco.
         * Posortuj Comparator.comparing(String::length).reversed() – malejąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NaturalOrderingInteger {
        /*
         * 🧪 Zadanie 6:
         * Lista Integer: {5,3,9,1,7}.
         * Wypisz naturalne sortowanie (Collections.sort, bo Integer implementuje Comparable).
         * Wypisz sortowanie malejące przez Comparator.reverseOrder().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReversedComparator {
        /*
         * 🧪 Zadanie 7:
         * Lista Person z zadania 2 posortowana Comparator.comparing(Person::getName).
         * Odwróć kolejność metodą .reversed() na tym Comparatorze i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SortArrayWithComparator {
        /*
         * 🧪 Zadanie 8:
         * Tablica Integer[] {8,3,6,1,9}.
         * Posortuj Arrays.sort(array, Comparator) malejąco.
         * Wypisz przed i po sortowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ClassWithoutComparable {
        /*
         * 🧪 Zadanie 9:
         * Klasa Product(String name, double price) BEZ implementacji Comparable.
         * Spróbuj Collections.sort(list) – zaobserwuj błąd kompilacji/wyjątek.
         * Napraw problem, sortując przez zewnętrzny Comparator zamiast tego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TreeSetWithComparator {
        /*
         * 🧪 Zadanie 10:
         * Klasa Product z zadania 9 (bez Comparable).
         * Stwórz TreeSet<Product> z Comparatorem po cenie (price) rosnąco.
         * Dodaj kilka produktów i wypisz TreeSet w kolejności ceny.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThenComparingTwoFields {
        /*
         * 🧪 Zadanie 11:
         * Klasa Student(String lastName, String firstName, double grade).
         * Posortuj listę studentów Comparator.comparing(lastName)
         * .thenComparing(firstName) – po nazwisku, potem imieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThenComparingReversed {
        /*
         * 🧪 Zadanie 12:
         * Klasa Student z zadania 11. Posortuj po ocenie malejąco (thenComparing
         * z reversed na komparatorze cząstkowym), a przy remisie – po nazwisku rosnąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleSortStrategies {
        /*
         * 🧪 Zadanie 13:
         * Lista Employee(name, department, salary) z 6 elementami.
         * Posortuj tę samą listę na 3 różne sposoby (osobne Comparatory):
         * po department, po salary malejąco, po name.
         * Wypisz wynik każdego sortowania osobno (nie mieszaj Comparable i Comparator).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ComparableVsComparatorSameClass {
        /*
         * 🧪 Zadanie 14:
         * Klasa Movie(String title, int year, double rating) implementująca
         * Comparable<Movie> po roku (year) – naturalne porządkowanie.
         * Dodatkowo stwórz zewnętrzny Comparator po rating malejąco.
         * Posortuj tę samą listę oboma sposobami i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NullSafeComparator {
        /*
         * 🧪 Zadanie 15:
         * Lista String zawierająca null: {"banan", null, "jabłko", null, "kiwi"}.
         * Posortuj używając Comparator.nullsFirst(Comparator.naturalOrder()).
         * Posortuj używając Comparator.nullsLast(...) i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ComparingIntPrimitive {
        /*
         * 🧪 Zadanie 16:
         * Klasa Item(String name, int quantity).
         * Użyj Comparator.comparingInt(Item::getQuantity) zamiast comparing()
         * z boxingiem – wyjaśnij w komentarzu różnicę wydajnościową.
         * Posortuj listę 5 itemów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PriorityQueueWithComparator {
        /*
         * 🧪 Zadanie 17:
         * PriorityQueue<Task> gdzie Task(String name, int priority).
         * Użyj Comparatora (priorytet malejąco – najpierw najważniejsze zadania).
         * Dodaj 5 zadań o różnych priorytetach i zdejmuj poll() aż pusta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareToContractViolation {
        /*
         * 🧪 Zadanie 18:
         * Klasa BuggyPoint(x,y) z BŁĘDNYM compareTo (np. tylko po x,
         * niezgodne z equals). Pokaż problem: dodaj do TreeSet dwa punkty
         * o tym samym x ale różnym y – zaobserwuj że TreeSet potraktuje je jako duplikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ComparatorChainThreeFields {
        /*
         * 🧪 Zadanie 19:
         * Klasa Order(String customer, LocalDate/String date, double amount).
         * Posortuj Comparator.comparing(customer).thenComparing(date)
         * .thenComparing(amount, reverseOrder()) – trzy poziomy sortowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SortListOfListsBySize {
        /*
         * 🧪 Zadanie 20:
         * List<List<Integer>> zawierająca kilka list o różnych rozmiarach.
         * Posortuj zewnętrzną listę Comparator.comparing(List::size)
         * a przy remisie porównaj sumę elementów (thenComparing z lambdą).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LeaderboardRanking {
        /*
         * 🧪 Zadanie 21:
         * Klasa Player(String name, int score, long timeMillis) implementująca
         * Comparable<Player>: wyższy score wygrywa, przy remisie krótszy czas wygrywa.
         * Zbuduj ranking TOP 5 z listy 10 graczy i wypisz z numeracją miejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiCriteriaProductSearch {
        /*
         * 🧪 Zadanie 22:
         * Klasa Product(name, category, price, rating).
         * Zaimplementuj metodę sortProducts(List<Product>, String criteria)
         * która na podstawie stringa ("price_asc","rating_desc","name") wybiera
         * odpowiedni Comparator i sortuje listę (bez Comparable w klasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CustomSortThenBinarySearch {
        /*
         * 🧪 Zadanie 23:
         * Klasa Employee(id, name, salary) implementująca Comparable po id.
         * Posortuj listę po salary Comparatorem, następnie użyj
         * Collections.binarySearch(list, key, comparator) żeby znaleźć
         * pracownika o konkretnej pensji w tej samej kolejności sortowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TreeMapWithCustomComparator {
        /*
         * 🧪 Zadanie 24:
         * TreeMap<String, Integer> z Comparatorem sortującym klucze
         * po długości Stringa, a przy remisie alfabetycznie
         * (Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).
         * Dodaj kilka par i wypisz mapę w tej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StableSortDemonstration {
        /*
         * 🧪 Zadanie 25:
         * Lista Person(name, age) z kilkoma osobami o tym samym age.
         * Posortuj najpierw po name, potem (stabilnie) po age –
         * pokaż że kolejność osób o tym samym age z pierwszego sortowania
         * jest zachowana (stabilność Collections.sort/List.sort).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TopKElementsWithComparator {
        /*
         * 🧪 Zadanie 26:
         * Lista 20 losowych liczb całkowitych. Znajdź 5 największych elementów
         * używając PriorityQueue (min-heap o rozmiarze 5) z odpowiednim Comparatorem,
         * zamiast sortować całą listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MergeIntervalsComparator {
        /*
         * 🧪 Zadanie 27:
         * Lista przedziałów int[]{start,end}: {1,3},{2,6},{8,10},{15,18}.
         * Posortuj Comparator.comparingInt(i -> i[0]), następnie scal
         * nakładające się przedziały. Oczekiwany wynik: {1,6},{8,10},{15,18}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ComparatorWithExternalWeights {
        /*
         * 🧪 Zadanie 28:
         * Klasa Candidate(name, experienceYears, testScore).
         * Napisz Comparator ważony: finalScore = experienceYears*0.4 + testScore*0.6,
         * sortujący malejąco po finalScore (bez dodawania pola finalScore do klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SortingImmutableRecordsComparable {
        /*
         * 🧪 Zadanie 29:
         * Record (lub zwykła finalna klasa) Version(int major, int minor, int patch)
         * implementujący Comparable<Version> porównujący kolejno major, minor, patch
         * (semantic versioning). Posortuj listę wersji: "2.1.0","1.9.9","2.0.5","1.10.0".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CustomSortAlgorithmWithComparator {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj własny algorytm sortowania bąbelkowego (bubble sort)
         * generyczny: <T> void bubbleSort(T[] array, Comparator<T> comparator).
         * Przetestuj na tablicy Integer (naturalOrder) i tablicy Person
         * (Comparator po wieku) – bez używania Collections.sort/Arrays.sort.
         */
        public static void main(String[] args) { }
    }
}
