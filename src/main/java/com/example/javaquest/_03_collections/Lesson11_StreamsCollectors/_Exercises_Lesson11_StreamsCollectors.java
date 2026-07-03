package com.example.javaquest._03_collections.Lesson11_StreamsCollectors;

public class _Exercises_Lesson11_StreamsCollectors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ToListBasic {
        /*
         * 🧪 Zadanie 1:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Użyj filter(n -> n.startsWith("A")) i collect(Collectors.toList()).
         * Wypisz wynikową listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ToSetDedup {
        /*
         * 🧪 Zadanie 2:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Anna","Beata").
         * Użyj collect(Collectors.toSet()) i wypisz rozmiar zbioru (bez duplikatów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ToUnmodifiableList {
        /*
         * 🧪 Zadanie 3:
         * List<Integer> numbers = List.of(1,2,3,4,5).
         * Zbierz przez collect(Collectors.toUnmodifiableList()) i spróbuj dodać
         * element do wyniku – przechwyć UnsupportedOperationException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_JoiningPlain {
        /*
         * 🧪 Zadanie 4:
         * List<String> names = List.of("Anna","Bartek","Celina").
         * Użyj collect(Collectors.joining()) (bez separatora) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JoiningWithSeparator {
        /*
         * 🧪 Zadanie 5:
         * List<String> names = List.of("Anna","Bartek","Celina").
         * Użyj collect(Collectors.joining(", ")) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JoiningFull {
        /*
         * 🧪 Zadanie 6:
         * List<String> names = List.of("Anna","Bartek","Celina").
         * Użyj collect(Collectors.joining(", ", "[", "]")) i wypisz wynik,
         * np. "[Anna, Bartek, Celina]".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountingMatching {
        /*
         * 🧪 Zadanie 7:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Policz ile imion ma długość > 4 używając filter + collect(Collectors.counting()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SummingInt {
        /*
         * 🧪 Zadanie 8:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam").
         * Policz sumę długości wszystkich imion przez collect(Collectors.summingInt(String::length)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AveragingInt {
        /*
         * 🧪 Zadanie 9:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam").
         * Policz średnią długość imion przez collect(Collectors.averagingInt(String::length)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SummarizingInt {
        /*
         * 🧪 Zadanie 10:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Użyj collect(Collectors.summarizingInt(String::length)) i wypisz
         * min, max, average oraz sum ze statystyk.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ToMapSimple {
        /*
         * 🧪 Zadanie 11:
         * List<String> names = List.of("Anna","Bartek","Celina").
         * Zbuduj Map<String,Integer> nazwa->długość przez
         * collect(Collectors.toMap(n -> n, String::length)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ToMapWithMerge {
        /*
         * 🧪 Zadanie 12:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Anna","Beata").
         * Zbuduj Map<Integer,String> długość->imiona (klucze mogą się powtarzać),
         * użyj toMap z funkcją scalającą (existing, replacement) -> existing + ", " + replacement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GroupingByLength {
        /*
         * 🧪 Zadanie 13:
         * List<String> names = List.of("Anna","Bartek","Celina","Adam","Beata").
         * Pogrupuj imiona według długości przez collect(Collectors.groupingBy(String::length)).
         * Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_GroupingByCounting {
        /*
         * 🧪 Zadanie 14:
         * Ta sama lista imion co w zadaniu 13.
         * Użyj groupingBy(String::length, Collectors.counting()), aby policzyć
         * ile imion ma daną długość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GroupingByJoining {
        /*
         * 🧪 Zadanie 15:
         * Ta sama lista imion co w zadaniu 13.
         * Pogrupuj po pierwszej literze (n -> n.charAt(0)) z downstream
         * collectorem Collectors.joining(", ") – zwróć Map<Character,String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PartitioningByEvenOdd {
        /*
         * 🧪 Zadanie 16:
         * List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10).
         * Podziel na parzyste/nieparzyste przez collect(Collectors.partitioningBy(n -> n % 2 == 0)).
         * Wypisz obie grupy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PartitioningByCounting {
        /*
         * 🧪 Zadanie 17:
         * Ta sama lista liczb co w zadaniu 16.
         * Użyj partitioningBy(n -> n % 2 == 0, Collectors.counting()), aby uzyskać
         * Map<Boolean,Long> z liczebnością każdej grupy zamiast samych elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedGroupingBy {
        /*
         * 🧪 Zadanie 18:
         * List<String> names = List.of("Anna","Adam","Bartek","Beata","Celina","Cyryl").
         * Pogrupuj dwupoziomowo: najpierw po pierwszej literze, potem po długości
         * (groupingBy(n -> n.charAt(0), groupingBy(String::length))).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ToMapSortedTreeMap {
        /*
         * 🧪 Zadanie 19:
         * List<String> names = List.of("Celina","Anna","Bartek","Adam").
         * Zbuduj posortowaną alfabetycznie Map<String,Integer> (nazwa->długość)
         * używając toMap z dodatkowym argumentem – dostawcą mapy TreeMap::new.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GroupingByMapping {
        /*
         * 🧪 Zadanie 20:
         * List<String> names = List.of("Anna","Adam","Bartek","Beata","Celina").
         * Pogrupuj po pierwszej literze, ale zamiast pełnych imion zbierz tylko
         * ich długości: groupingBy(n -> n.charAt(0), Collectors.mapping(String::length, Collectors.toList())).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AvgSalaryPerDepartment {
        /*
         * 🧪 Zadanie 21:
         * Klasa Employee(name, department, salary). Lista 6 pracowników z 3 działów.
         * Oblicz średnie wynagrodzenie w każdym dziale przez
         * groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_HighestPaidPerDepartment {
        /*
         * 🧪 Zadanie 22:
         * Ta sama lista Employee co w zadaniu 21.
         * Znajdź najlepiej opłacanego pracownika w każdym dziale przez
         * groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PassFailAverageGrade {
        /*
         * 🧪 Zadanie 23:
         * Klasa Student(name, grade). Lista 8 studentów z ocenami 0-100, próg zaliczenia = 50.
         * Podziel partitioningBy(s -> s.getGrade() >= 50), a następnie dla każdej grupy
         * policz średnią ocenę (Collectors.averagingDouble).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SumOrdersPerCustomerSorted {
        /*
         * 🧪 Zadanie 24:
         * Klasa Order(customer, total). Lista 8 zamówień od kilku klientów.
         * Zsumuj wartość zamówień per klient (groupingBy + summingDouble),
         * a następnie posortuj wynikową mapę malejąco po sumie i wypisz ranking klientów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnagramGroups {
        /*
         * 🧪 Zadanie 25:
         * Lista słów: {"eat","tea","tan","ate","nat","bat"}.
         * Pogrupuj anagramy przez groupingBy z kluczem = posortowane znaki słowa
         * (np. chars sorted -> String), używając wyłącznie Stream+Collectors (bez ręcznej pętli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiLevelCategorySubcategory {
        /*
         * 🧪 Zadanie 26:
         * Klasa Product(name, category, subcategory). Lista 8 produktów.
         * Zbuduj Map<String, Map<String, Long>> category -> subcategory -> liczba produktów,
         * używając zagnieżdżonego groupingBy(category, groupingBy(subcategory, counting())).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_Top3FrequentWords {
        /*
         * 🧪 Zadanie 27:
         * Tekst: "ala ma kota kot ma ale ala lubi kota kota ma ala".
         * Podziel na słowa, policz częstość (groupingBy + counting()), posortuj malejąco
         * po liczbie wystąpień i wypisz 3 najczęstsze słowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CustomCollectorOf {
        /*
         * 🧪 Zadanie 28:
         * List<Integer> numbers = List.of(4,8,15,16,23,42).
         * Napisz własny Collector przy pomocy Collector.of(...), który zbiera
         * elementy do Stringa rozdzielonego przecinkami wraz z numerem porządkowym
         * każdego elementu, np. "1:4, 2:8, 3:15...".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SummarizingStatsPerCategory {
        /*
         * 🧪 Zadanie 29:
         * Klasa Item(name, category, price). Lista 8 produktów w 3 kategoriach.
         * Dla każdej kategorii policz DoubleSummaryStatistics (min, max, avg, sum)
         * przez groupingBy(category, Collectors.summarizingDouble(Item::getPrice)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_NestedGroupingPartitioningReport {
        /*
         * 🧪 Zadanie 30:
         * Klasa Transaction(date, category, amount). Lista 10 transakcji w kilku kategoriach.
         * Zbuduj raport Map<String, Map<Boolean, List<Transaction>>>: najpierw grupując
         * po kategorii (groupingBy), a następnie dzieląc każdą grupę na transakcje
         * powyżej i poniżej progu kwoty 100 (partitioningBy amount > 100). Wypisz raport.
         */
        public static void main(String[] args) { }
    }
}
