package com.example.javaquest._03_collections.Lesson12_StreamsTerminal;

public class _Exercises_Lesson12_StreamsTerminal {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MinMaxNumbers {
        /*
         * 🧪 Zadanie 1:
         * List<Integer> numbers = {7,2,9,4,1,8,3}.
         * Znajdź min i max używając stream().min(Comparator) / max(Comparator).
         * Wypisz oba wyniki (Optional -> orElse).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CountElements {
        /*
         * 🧪 Zadanie 2:
         * List<String> words = {"a","bb","ccc","dd","e"}.
         * Wypisz liczbę wszystkich elementów (count()).
         * Wypisz liczbę elementów o długości > 1 (filter + count()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SumReduce {
        /*
         * 🧪 Zadanie 3:
         * List<Integer> numbers = {1,2,3,4,5}.
         * Policz sumę przez reduce(0, (a,b) -> a+b).
         * Policz sumę przez reduce(0, Integer::sum).
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReduceNoIdentity {
        /*
         * 🧪 Zadanie 4:
         * List<Integer> numbers = {5,3,8,1,9}.
         * Użyj reduce(BinaryOperator) BEZ wartości startowej,
         * żeby znaleźć maksimum: (a,b) -> a > b ? a : b.
         * Wynik to Optional<Integer> – wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ProductReduce {
        /*
         * 🧪 Zadanie 5:
         * List<Integer> numbers = {1,2,3,4}.
         * Policz iloczyn wszystkich elementów przez reduce(1, (a,b) -> a*b).
         * Wypisz wynik (powinno być 24).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FindFirstMatch {
        /*
         * 🧪 Zadanie 6:
         * List<String> names = {"Anna","Bartek","Celina","Ala"}.
         * Znajdź pierwsze imię zaczynające się na "A" (filter + findFirst).
         * Wypisz wynik (Optional -> orElse("brak")).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindAnyMatch {
        /*
         * 🧪 Zadanie 7:
         * List<Integer> numbers = {2,4,6,7,8}.
         * Znajdź dowolny element > 5 (filter + findAny).
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AnyAllNoneMatch {
        /*
         * 🧪 Zadanie 8:
         * List<Integer> numbers = {2,4,6,8}.
         * Sprawdź: anyMatch(n -> n % 2 != 0) – czy jest liczba nieparzysta.
         * allMatch(n -> n % 2 == 0) – czy wszystkie parzyste.
         * noneMatch(n -> n < 0) – czy żadna nie jest ujemna.
         * Wypisz wszystkie 3 wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MapMethodRef {
        /*
         * 🧪 Zadanie 9:
         * List<String> words = {"1","22","333"}.
         * Zmapuj każdy element na jego długość używając referencji do metody String::length.
         * Wypisz wyniki przez forEach(System.out::println).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConcatReduce {
        /*
         * 🧪 Zadanie 10:
         * List<String> words = {"Java","is","fun"}.
         * Połącz słowa w jedno zdanie rozdzielone spacją używając reduce
         * (bez String.join, bez Collectors).
         * Wypisz wynik: "Java is fun".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MaxByLength {
        /*
         * 🧪 Zadanie 11:
         * List<String> words = {"kot","hipopotam","pies","słoń","żyrafa"}.
         * Znajdź najdłuższe słowo używając max(Comparator.comparingInt(String::length)).
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CountVowels {
        /*
         * 🧪 Zadanie 12:
         * String sentence = "ala ma kota i psa".
         * Policz liczbę samogłosek (a,e,i,o,u,y) w zdaniu używając strumienia
         * po znakach (chars()) z filter + count.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AverageManual {
        /*
         * 🧪 Zadanie 13:
         * List<Integer> numbers = {4,8,15,16,23,42}.
         * Oblicz średnią BEZ metody average() – użyj reduce (suma) i count()
         * osobno, a potem podziel ręcznie.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindFirstEven {
        /*
         * 🧪 Zadanie 14:
         * List<Integer> numbers = {3,7,9,11,12,15}.
         * Znajdź pierwszą parzystą liczbę (filter + findFirst).
         * Jeśli brak – rzuć wyjątek przez orElseThrow(() -> new RuntimeException(...)).
         * Obsłuż wyjątek w try/catch i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AllPositive {
        /*
         * 🧪 Zadanie 15:
         * List<Integer> transactions = {100,-50,200,30,-10}.
         * Sprawdź allMatch(n -> n >= 0).
         * Jeśli false – wypisz (filter + forEach) wszystkie ujemne transakcje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ReduceToMax {
        /*
         * 🧪 Zadanie 16:
         * List<Integer> ages = {23,45,12,67,34,8}.
         * Znajdź najstarszy wiek używając reduce(Integer::max).
         * Wypisz wynik (Optional -> orElse(0)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MatchCombination {
        /*
         * 🧪 Zadanie 17:
         * List<String> passwords = {"abc12345","qwertyui","pass word","12345678"}.
         * Sprawdź: allMatch – każde hasło ma długość >= 8.
         * anyMatch – jakieś hasło zawiera cyfrę (użyj matches lub anyMatch po znakach).
         * noneMatch – żadne hasło nie zawiera spacji.
         * Wypisz wszystkie 3 wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReduceConcatUpper {
        /*
         * 🧪 Zadanie 18:
         * List<String> names = {"anna","bartek","celina"}.
         * Zmapuj na wielkie litery (String::toUpperCase), a potem połącz
         * przecinkiem przez reduce w jeden String: "ANNA, BARTEK, CELINA".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CountDistinctByCondition {
        /*
         * 🧪 Zadanie 19:
         * List<Integer> numbers = {1,5,5,8,8,8,9,12,12,3}.
         * Policz liczbę unikalnych wartości większych niż 5
         * (distinct + filter + count).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FindFirstOrDefault {
        /*
         * 🧪 Zadanie 20:
         * List<Integer> numbers = {2,4,9,15,22,30}.
         * Znajdź pierwszą liczbę podzielną przez 7 (filter + findFirst).
         * Jeśli brak takiej liczby, użyj orElse(-1).
         * Przetestuj z listą, która taką liczbę zawiera i bez niej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WordFrequencyMax {
        /*
         * 🧪 Zadanie 21:
         * List<String> words = {"kot","pies","kot","ptak","kot","pies"}.
         * Zbuduj ręcznie Map<String,Integer> częstości (bez Collectors.groupingBy),
         * a następnie znajdź najczęstsze słowo używając
         * entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReduceCustomObject {
        /*
         * 🧪 Zadanie 22:
         * Klasa Product(name, price). Lista 5 produktów z różnymi cenami.
         * Policz łączną wartość koszyka używając trójargumentowego
         * reduce(0.0, (acc, p) -> acc + p.getPrice(), Double::sum).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ParallelSumConsistency {
        /*
         * 🧪 Zadanie 23:
         * List<Integer> numbers z zakresu 1..1_000_000 (IntStream.rangeClosed + boxed).
         * Policz sumę sekwencyjnie przez reduce i równolegle (parallelStream + reduce).
         * Zmierz czas obu (System.nanoTime()) i porównaj, czy wyniki są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ShortCircuitDemo {
        /*
         * 🧪 Zadanie 24:
         * List<Integer> numbers = {1,2,3,4,5,6,7,8,9,10}.
         * Użyj peek() żeby wypisać każdy sprawdzany element, a potem anyMatch(n -> n == 4).
         * Zaobserwuj i skomentuj (w konsoli), że strumień zatrzymuje się po znalezieniu
         * pasującego elementu (short-circuit) – nie sprawdza wszystkich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MinMaxCustomComparator {
        /*
         * 🧪 Zadanie 25:
         * Klasa Employee(name, salary). Lista 6 pracowników.
         * Znajdź pracownika z najniższą i najwyższą pensją używając
         * min/max(Comparator.comparingDouble(Employee::getSalary)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ReduceBuildString {
        /*
         * 🧪 Zadanie 26:
         * List<Integer> numbers = {1,2,3,4,5}.
         * Zbuduj String w formacie "[1, 2, 3, 4, 5]" używając WYŁĄCZNIE reduce
         * (bez String.join, bez Collectors, bez StringBuilder w pętli for).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FindFirstInSortedStream {
        /*
         * 🧪 Zadanie 27:
         * List<Integer> numbers = {9,3,7,1,8,2,15,4}.
         * Posortuj strumień (sorted()) i znajdź pierwszy element większy
         * od podanego progu (np. 7), używając filter + findFirst.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombineAnyAllNoneWithMap {
        /*
         * 🧪 Zadanie 28:
         * Map<String,Integer> stock = {"jabłka":12,"banany":0,"gruszki":5,"śliwki":0}.
         * Na entrySet().stream() sprawdź:
         * anyMatch – czy jest towar z ilością 0 (brak w magazynie),
         * allMatch – czy wszystkie ilości są nieujemne,
         * i wypisz (filter + forEach) towary z ilością < 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TwoListReduceMerge {
        /*
         * 🧪 Zadanie 29:
         * List<Integer> a = {1,2,3,4}, b = {10,20,30,40} (ta sama długość).
         * Użyj IntStream.range(0, a.size()) żeby zbudować listę sum
         * element-po-elemencie: {11,22,33,44}.
         * Następnie policz sumę całkowitą tej listy przez reduce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TopNWithoutSortedOrCollectors {
        /*
         * 🧪 Zadanie 30:
         * List<Integer> numbers = {5,1,9,3,8,2,7,4,6}.
         * Znajdź 3 największe elementy BEZ użycia sorted() i Collectors –
         * powtarzaj max() na kopii listy i usuwaj znaleziony element (pętla + reduce/max).
         * Wypisz znalezione top 3 w kolejności malejącej.
         */
        public static void main(String[] args) { }
    }
}
