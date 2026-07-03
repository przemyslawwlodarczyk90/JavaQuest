package com.example.javaquest._03_collections.Lesson10_StreamsIntro;

public class _Exercises_Lesson10_StreamsIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StreamFromList {
        /*
         * 🧪 Zadanie 1:
         * List<String> names = List.of("Anna", "Bartek", "Celina", "Adam").
         * Utwórz stream przez names.stream() i wypisz każdy element metodą forEach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StreamOfValues {
        /*
         * 🧪 Zadanie 2:
         * Utwórz Stream<String> bezpośrednio przez Stream.of("java", "python", "c++").
         * Wypisz elementy przez forEach(System.out::println).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StreamFromArray {
        /*
         * 🧪 Zadanie 3:
         * String[] arr = {"x", "y", "z", "w"}.
         * Utwórz stream przez Arrays.stream(arr) i wypisz elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FilterStartsWith {
        /*
         * 🧪 Zadanie 4:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Użyj filter, aby zostawić tylko imiona zaczynające się na "A", i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MapUppercase {
        /*
         * 🧪 Zadanie 5:
         * List<String> names = List.of("anna","bartek","celina").
         * Użyj map(String::toUpperCase) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MapSquares {
        /*
         * 🧪 Zadanie 6:
         * List<Integer> numbers = List.of(1,2,3,4,5).
         * Użyj map(n -> n*n), aby uzyskać kwadraty, i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SortedNatural {
        /*
         * 🧪 Zadanie 7:
         * List<String> names = List.of("Celina","Adam","Bartek","Anna").
         * Użyj sorted() (naturalny porządek) i wypisz posortowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SortedByLength {
        /*
         * 🧪 Zadanie 8:
         * List<String> names = List.of("Bartek","Fig","Anna","Kiwi","Celina").
         * Użyj sorted z Comparatorem porównującym długość Stringów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DistinctIntegers {
        /*
         * 🧪 Zadanie 9:
         * List<Integer> withDups = List.of(1,2,2,3,3,3,4,1).
         * Użyj distinct() i wypisz unikalne wartości w kolejności występowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountMatching {
        /*
         * 🧪 Zadanie 10:
         * List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10).
         * Użyj filter(n -> n % 2 == 0) i count(), aby policzyć liczby parzyste.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullPipeline {
        /*
         * 🧪 Zadanie 11:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Zbuduj pipeline: filter(długość > 4) -> map(toUpperCase) -> sorted() -> toList().
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PeekDebug {
        /*
         * 🧪 Zadanie 12:
         * List<Integer> numbers = List.of(1,2,3,4,5,6).
         * Użyj peek() przed i po filter(n -> n % 2 == 0), aby wypisać,
         * które elementy wchodzą i wychodzą z filtra, a na końcu count().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LimitSkip {
        /*
         * 🧪 Zadanie 13:
         * List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10).
         * Zaimplementuj paginację: skip(3).limit(4) – wypisz "stronę" wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MatchMethods {
        /*
         * 🧪 Zadanie 14:
         * List<Integer> numbers = List.of(2,4,6,8,10).
         * Sprawdź: allMatch(n -> n % 2 == 0), anyMatch(n -> n > 9),
         * noneMatch(n -> n < 0). Wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FindFirstFindAny {
        /*
         * 🧪 Zadanie 15:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam").
         * Znajdź pierwszy element dłuższy niż 5 znaków przez filter + findFirst().
         * Wypisz wynik jako Optional i jego wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MinMaxComparator {
        /*
         * 🧪 Zadanie 16:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Znajdź najkrótsze i najdłuższe imię używając min()/max()
         * z Comparator.comparingInt(String::length).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ReduceSum {
        /*
         * 🧪 Zadanie 17:
         * List<Integer> numbers = List.of(1,2,3,4,5).
         * Policz sumę elementów używając reduce(0, Integer::sum).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReduceLongestString {
        /*
         * 🧪 Zadanie 18:
         * List<String> words = List.of("kot","hipopotam","pies","słoń").
         * Znajdź najdłuższe słowo używając reduce z lambdą porównującą długości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FlatMapNestedLists {
        /*
         * 🧪 Zadanie 19:
         * List<List<String>> nested = List.of(List.of("a","b"), List.of("c","d","e"), List.of("f")).
         * Użyj flatMap, aby spłaszczyć do pojedynczego Stream<String>, i wypisz elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ComplexQuery {
        /*
         * 🧪 Zadanie 20:
         * List<Integer> numbers = List.of(1,2,2,3,4,4,5,6,7,8,9,10).
         * Znajdź 3 pierwsze unikalne kwadraty liczb parzystych, posortowane rosnąco:
         * filter(parzyste) -> map(kwadrat) -> distinct() -> sorted() -> limit(3).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_PersonFilterSortMap {
        /*
         * 🧪 Zadanie 21:
         * Klasa Person(name, age). Lista 6 osób w różnym wieku (niektóre < 18).
         * Znajdź imiona osób pełnoletnich (age >= 18), posortowane po wieku rosnąco,
         * pipeline: filter -> sorted(Comparator.comparingInt(Person::getAge)) -> map(getName) -> toList().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LongWordCountIgnoreCase {
        /*
         * 🧪 Zadanie 22:
         * Tekst: "Java Streams sa bardzo przydatnym narzedziem do przetwarzania danych".
         * Podziel na słowa, zignoruj wielkość liter, policz ile słów ma więcej niż 5 liter
         * (bez użycia Collectors – tylko filter + count).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MergeTwoListsDedupSort {
        /*
         * 🧪 Zadanie 23:
         * List<Integer> a = List.of(5,3,1,4), b = List.of(4,6,3,2).
         * Połącz strumienie przez Stream.concat, usuń duplikaty (distinct)
         * i posortuj wynik (sorted).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FibonacciIterate {
        /*
         * 🧪 Zadanie 24:
         * Użyj Stream.iterate, aby wygenerować pierwsze 10 liczb Fibonacciego
         * (startując od int[]{0,1}, generując kolejne pary), a następnie
         * zmapuj do pojedynczych wartości i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RandomGenerateLimit {
        /*
         * 🧪 Zadanie 25:
         * Użyj Stream.generate(Math::random) z limit(5), aby wygenerować
         * 5 losowych liczb typu double, i wypisz je zaokrąglone do 2 miejsc po przecinku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FlatMapOrdersItems {
        /*
         * 🧪 Zadanie 26:
         * Klasa Order(id, List<String> items). 3 zamówienia z różnymi listami produktów.
         * Użyj flatMap, aby uzyskać płaski Stream<String> wszystkich zamówionych
         * produktów ze wszystkich zamówień, następnie distinct() i sorted().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PrimesWithIntStream {
        /*
         * 🧪 Zadanie 27:
         * Używając IntStream.rangeClosed(2, 50) i filter sprawdzającego pierwszość
         * (np. pomocnicza metoda isPrime), znajdź wszystkie liczby pierwsze do 50.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_Flatten2DArray {
        /*
         * 🧪 Zadanie 28:
         * int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}}.
         * Użyj Arrays.stream + flatMap, aby spłaszczyć macierz do IntStream,
         * i wypisz sumę wszystkich elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LazinessProof {
        /*
         * 🧪 Zadanie 29:
         * List<Integer> numbers = List.of(1,2,3,4,5).
         * Zbuduj pipeline z map(...) i filter(...) zawierającymi System.out.println
         * wewnątrz lambd (bez wywoływania operacji terminalnej) – pokaż, że nic się
         * nie wypisuje, dopóki nie dodasz .forEach() lub .toList() na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ProductSearch {
        /*
         * 🧪 Zadanie 30:
         * Klasa Product(name, category, price, rating). Lista 8 produktów.
         * Zaimplementuj wyszukiwarkę: filter po category=="Electronics" i rating >= 4.0,
         * sorted malejąco po rating, następnie limit(3) – wypisz TOP 3 najlepsze produkty.
         */
        public static void main(String[] args) { }
    }
}
