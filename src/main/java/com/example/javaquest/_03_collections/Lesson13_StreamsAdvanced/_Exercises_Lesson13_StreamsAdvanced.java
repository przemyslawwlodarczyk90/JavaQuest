package com.example.javaquest._03_collections.Lesson13_StreamsAdvanced;

public class _Exercises_Lesson13_StreamsAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LimitFirstN {
        /*
         * 🧪 Zadanie 1:
         * Użyj Stream.iterate(1, n -> n + 1) żeby wygenerować liczby naturalne,
         * ogranicz do pierwszych 10 elementów przez limit(10) i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SkipFirstN {
        /*
         * 🧪 Zadanie 2:
         * List<Integer> numbers = zakres 1..15 (IntStream.rangeClosed(1,15).boxed()).
         * Pomiń pierwsze 5 elementów przez skip(5) i wypisz resztę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_Pagination {
        /*
         * 🧪 Zadanie 3:
         * Lista 20 elementów (np. liczby 1..20).
         * Wypisz stronę nr 3 przy rozmiarze strony 4, używając skip + limit
         * (skip((strona-1)*rozmiar).limit(rozmiar)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IteratePowers {
        /*
         * 🧪 Zadanie 4:
         * Użyj Stream.iterate(1, n -> n * 3) żeby wygenerować pierwsze 10
         * potęg liczby 3 (1, 3, 9, 27, ...). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IterateWithPredicate {
        /*
         * 🧪 Zadanie 5:
         * Użyj Stream.iterate(0, n -> n < 50, n -> n + 7) (wariant z warunkiem
         * zakończenia, Java 9+) żeby wygenerować liczby 0,7,14,... dopóki < 50.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GenerateConstant {
        /*
         * 🧪 Zadanie 6:
         * Użyj Stream.generate(() -> "x") z limit(5) żeby wypisać 5 razy "x".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GenerateRandomInts {
        /*
         * 🧪 Zadanie 7:
         * Użyj Stream.generate z Random (np. () -> random.nextInt(100))
         * i limit(5), żeby wypisać 5 losowych liczb całkowitych z zakresu 0-99.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FlatMapBasic {
        /*
         * 🧪 Zadanie 8:
         * List<List<String>> nested = {{"a","b"},{"c"},{"d","e","f"}}.
         * Spłaszcz do jednego Stream<String> używając flatMap(List::stream)
         * i wypisz wszystkie elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IntStreamRange {
        /*
         * 🧪 Zadanie 9:
         * Użyj IntStream.range(1, 10) żeby wypisać liczby od 1 do 9 (bez 10).
         * Następnie IntStream.rangeClosed(1, 10) żeby wypisać od 1 do 10 (z 10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IntStreamSumAvg {
        /*
         * 🧪 Zadanie 10:
         * Użyj IntStream.rangeClosed(1, 20) żeby policzyć sumę (sum())
         * i średnią (average().getAsDouble()) liczb od 1 do 20.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FibonacciIterate {
        /*
         * 🧪 Zadanie 11:
         * Użyj Stream.iterate(new int[]{0,1}, arr -> new int[]{arr[1], arr[0]+arr[1]})
         * żeby wygenerować pierwsze 15 liczb Fibonacciego (mapuj na arr[0]).
         * Wypisz ciąg.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FlatMapSentences {
        /*
         * 🧪 Zadanie 12:
         * List<String> sentences = {"Java jest super", "Strumienie są potężne",
         * "Kod jest czytelny"}.
         * Użyj flatMap(s -> Stream.of(s.split(" "))) żeby rozbić na słowa,
         * distinct(), i policz ile słów ma długość > 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FlatMapCharacters {
        /*
         * 🧪 Zadanie 13:
         * List<String> words = {"kot","pies","kaczka"}.
         * Użyj flatMap żeby zamienić każde słowo na Stream<Character>
         * (np. word.chars().mapToObj(c -> (char) c)).
         * Policz liczbę unikalnych liter (distinct + count).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_InfiniteFilterLimit {
        /*
         * 🧪 Zadanie 14:
         * Użyj Stream.iterate(1, n -> n + 1) (nieskończony), odfiltruj parzyste
         * (filter(n -> n % 2 == 0)) i weź pierwsze 10 takich liczb (limit(10)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GenerateSequentialIds {
        /*
         * 🧪 Zadanie 15:
         * Użyj Stream.generate z licznikiem (np. AtomicInteger zaczynający od 1000)
         * żeby wygenerować 5 kolejnych identyfikatorów: 1000, 1001, 1002, ...
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MapToObjRange {
        /*
         * 🧪 Zadanie 16:
         * Użyj IntStream.rangeClosed(1, 10).mapToObj(n -> "Rok " + n) żeby
         * wygenerować listę Stringów "Rok 1", "Rok 2", ..., "Rok 10".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MixedPipeline {
        /*
         * 🧪 Zadanie 17:
         * List<String> numberStrings = {"3","8","15","4","22","7"}.
         * Użyj mapToInt(Integer::parseInt), odfiltruj liczby parzyste,
         * policz sumę pozostałych (sum()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FlatMapMatrix {
        /*
         * 🧪 Zadanie 18:
         * int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}}.
         * Użyj Arrays.stream(matrix).flatMapToInt(Arrays::stream) żeby
         * spłaszczyć macierz do jednego IntStream i policzyć sumę wszystkich elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SkipLimitCombo {
        /*
         * 🧪 Zadanie 19:
         * IntStream.rangeClosed(1, 100).
         * Wyciągnij dokładnie elementy od 11 do 20 (skip(10).limit(10)).
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GenerateFiniteSequence {
        /*
         * 🧪 Zadanie 20:
         * Użyj Stream.generate ze stanowym Supplierem (np. AtomicInteger licznik)
         * żeby wygenerować kwadraty liczb 1..10 (1, 4, 9, 16, ..., 100), limit(10).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FlatMapGroupWords {
        /*
         * 🧪 Zadanie 21:
         * Map<String, List<String>> categories = {"owoce": {"jabłko","banan"},
         * "warzywa": {"marchew","banan"}, "zboża": {"pszenica"}}.
         * Użyj flatMap(Collection::stream) na values() żeby zebrać wszystkie
         * słowa z każdej kategorii w jeden posortowany, unikalny Stream<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LazyEvaluationDemo {
        /*
         * 🧪 Zadanie 22:
         * Użyj Stream.iterate(1, n -> n + 1).peek(n -> System.out.println("generuję " + n))
         * połączone z limit(5), żeby pokazać (w konsoli) że mimo nieskończonego
         * źródła generowanych jest dokładnie 5 elementów – strumienie są leniwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PrimeGenerator {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę pomocniczą isPrime(int n).
         * Użyj Stream.iterate(2, n -> n + 1).filter(this::isPrime) żeby
         * wygenerować pierwsze 20 liczb pierwszych (limit(20)). Wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FlatMapPairs {
        /*
         * 🧪 Zadanie 24:
         * List<Integer> list1 = {1,2,3}, list2 = {"A","B"} (jako String).
         * Użyj flatMap żeby wygenerować wszystkie pary w formacie "1-A","1-B",
         * "2-A","2-B","3-A","3-B" (iloczyn kartezjański).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RunningTotal {
        /*
         * 🧪 Zadanie 25:
         * List<Integer> numbers = {2,5,1,7,3}.
         * Użyj IntStream.range(0, numbers.size()) i sum przy pomocy sublist/limit,
         * żeby wygenerować sumy narastające: {2,7,8,15,18}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NestedFlatMapDeep {
        /*
         * 🧪 Zadanie 26:
         * List<List<List<Integer>>> deep = {{{1,2},{3}},{{4,5,6}},{{7},{8,9}}}.
         * Użyj podwójnego flatMap żeby całkowicie spłaszczyć strukturę
         * do jednego Stream<Integer> i policzyć sumę wszystkich elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_InfiniteStreamTakeWhile {
        /*
         * 🧪 Zadanie 27:
         * Użyj Stream.iterate(1, n -> n + 1) (nieskończony) razem z takeWhile
         * (Java 9+), żeby brać liczby dopóki ich kwadrat jest mniejszy niż 500.
         * Wypisz uzyskaną sekwencję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CustomBatching {
        /*
         * 🧪 Zadanie 28:
         * List<Integer> numbers = 23 elementy (np. 1..23).
         * Podziel listę na paczki (batche) po 5 elementów, używając
         * IntStream.range(0, numbers.size(), 5)-podobnej logiki (subList + limit).
         * Wypisz każdą paczkę osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GenerateLimitedRandomUnique {
        /*
         * 🧪 Zadanie 29:
         * Użyj Stream.generate z Random do produkowania liczb 1-50, filtruj
         * duplikaty ręcznie (np. sprawdzając HashSet z distinct() nie wystarczy
         * dla generate) tak, żeby zebrać dokładnie 10 UNIKALNYCH losowych liczb.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MergeTwoInfiniteStreams {
        /*
         * 🧪 Zadanie 30:
         * Stream A = Stream.iterate(0, n -> n + 2) (liczby parzyste),
         * Stream B = Stream.iterate(1, n -> n + 2) (liczby nieparzyste).
         * Weź pierwsze 10 z każdego (limit(10)) i wypisz je "przeplecione"
         * na przemian: A0, B0, A1, B1, ... (użyj IntStream.range do indeksowania
         * po tablicach zebranych z obu strumieni).
         */
        public static void main(String[] args) { }
    }
}
