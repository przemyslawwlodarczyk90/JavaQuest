package com.example.javaquest._03_collections.Lesson07_Comparator;

public class _Exercises_Lesson07_Comparator {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LambdaByLength {
        /*
         * 🧪 Zadanie 1:
         * Lista słów {"pies","kot","słoń","mrówka"}.
         * Napisz Comparator<String> jako lambdę porównującą po długości i posortuj listę rosnąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ComparingVsLambda {
        /*
         * 🧪 Zadanie 2:
         * Ta sama lista co wyżej. Zapisz Comparator dwoma sposobami:
         * Comparator.comparingInt(String::length) oraz lambda (a,b) -> ...
         * Posortuj listę oboma i porównaj wynik (powinien być identyczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReverseOrderInts {
        /*
         * 🧪 Zadanie 3:
         * Lista {5,3,8,1,9}. Posortuj malejąco używając Comparator.reverseOrder().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NaturalOrderStrings {
        /*
         * 🧪 Zadanie 4:
         * Lista {"banan","jabłko","gruszka","ananas"}.
         * Posortuj rosnąco używając Comparator.naturalOrder().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ThenComparing {
        /*
         * 🧪 Zadanie 5:
         * Lista {"aa","bb","a","cc","b"}.
         * Posortuj po długości, a przy remisie alfabetycznie – użyj thenComparing.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReversedChain {
        /*
         * 🧪 Zadanie 6:
         * Lista {"banana","fig","apple","kiwi","cherry"}.
         * Posortuj malejąco po długości używając Comparator.comparingInt(String::length).reversed().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PersonByAge {
        /*
         * 🧪 Zadanie 7:
         * Klasa Person(name, age). Lista 5 osób z różnym wiekiem.
         * Posortuj rosnąco po wieku używając Comparator.comparingInt(Person::getAge).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PersonByName {
        /*
         * 🧪 Zadanie 8:
         * Ta sama lista Person co wyżej.
         * Posortuj alfabetycznie po imieniu używając Comparator.comparing(Person::getName).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PersonAgeDescThenName {
        /*
         * 🧪 Zadanie 9:
         * Ta sama lista Person, w tym dwie osoby w tym samym wieku.
         * Posortuj malejąco po wieku, a przy remisie rosnąco po imieniu (thenComparing).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NullsFirstLast {
        /*
         * 🧪 Zadanie 10:
         * Lista {"c", null, "a", null, "b"}.
         * Posortuj z Comparator.nullsFirst(Comparator.naturalOrder())
         * oraz z Comparator.nullsLast(Comparator.naturalOrder()) – wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ProductPriceAscDesc {
        /*
         * 🧪 Zadanie 11:
         * Klasa Product(name, price, quantity). Lista 5 produktów.
         * Posortuj po cenie rosnąco, a następnie tę samą listę po cenie malejąco. Porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StudentsGradeThenName {
        /*
         * 🧪 Zadanie 12:
         * Klasa Student(name, grade). Lista 6 studentów, kilku z tą samą oceną.
         * Posortuj malejąco po ocenie, a przy remisie rosnąco alfabetycznie po imieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LengthThenReverseAlpha {
        /*
         * 🧪 Zadanie 13:
         * Lista {"bb","aa","c","dd","a"}.
         * Posortuj po długości rosnąco, a przy remisie odwrotnie alfabetycznie
         * (thenComparing(Comparator.reverseOrder())).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SortPairsBySum {
        /*
         * 🧪 Zadanie 14:
         * Tablica int[][] par {{3,4},{1,1},{5,0},{2,2}}.
         * Posortuj wg sumy elementów pary rosnąco, używając lambdy w Comparator.comparingInt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TreeSetComparatorZero {
        /*
         * 🧪 Zadanie 15:
         * Klasa Person(name, age). TreeSet<Person> z komparatorem tylko po age.
         * Dodaj dwie różne osoby w tym samym wieku – sprawdź, że TreeSet uznał je za "duplikat"
         * (bo compare zwraca 0) i druga osoba nie została dodana. Wyjaśnij problem w komentarzu do wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DepartmentThenSalaryDesc {
        /*
         * 🧪 Zadanie 16:
         * Klasa Employee(department, salary). Lista 8 pracowników z kilku działów.
         * Posortuj alfabetycznie po dziale, a w ramach działu malejąco po pensji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CaseInsensitiveSort {
        /*
         * 🧪 Zadanie 17:
         * Lista {"Banana","apple","Cherry","aVocado"}.
         * Posortuj ignorując wielkość liter używając String.CASE_INSENSITIVE_ORDER.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreeLevelSort {
        /*
         * 🧪 Zadanie 18:
         * Klasa Person(name, age, city). Lista 7 osób.
         * Posortuj po city alfabetycznie, potem po age rosnąco, potem po name rosnąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GenericSortMethod {
        /*
         * 🧪 Zadanie 19:
         * Napisz generyczną metodę <T> void sortAndPrint(List<T> list, Comparator<T> cmp).
         * Użyj jej dla List<Integer> (sortowanie malejące) i List<String> (sortowanie po długości).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PriorityFromMap {
        /*
         * 🧪 Zadanie 20:
         * Map<String,Integer> priorytetów zadań {"pilne":1,"ważne":2,"zwykłe":3}.
         * Lista zadań (Stringów-nazw priorytetów z powtórzeniami).
         * Posortuj listę zadań według wartości priorytetu pobranej z mapy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ManagersFirstThenSalary {
        /*
         * 🧪 Zadanie 21:
         * Klasa Employee(name, isManager, salary). Lista 8 pracowników (część managerów).
         * Napisz Comparator, który stawia managerów przed pracownikami,
         * a w ramach grupy sortuje malejąco po pensji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ComparatorChainClass {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj własną klasę ComparatorChain<T> przyjmującą List<Comparator<T>>
         * i stosującą je po kolei (pierwszy niezerowy wynik decyduje) – działanie podobne do thenComparing.
         * Przetestuj na liście Product (najpierw po cenie, potem po nazwie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TaskDependencyOrder {
        /*
         * 🧪 Zadanie 23:
         * Lista zadań z polem "poziom zależności" (int level) wyliczonym wcześniej.
         * Posortuj zadania tak, by zadania o niższym poziomie (mniej zależności) były pierwsze,
         * a przy remisie zachowany był porządek nazw – użyj Comparator do implementacji prostego
         * sortowania topologicznego "po poziomach".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TopKExpensive {
        /*
         * 🧪 Zadanie 24:
         * Lista 20 produktów (Product: name, price).
         * Znajdź k=5 najdroższych produktów, sortując malejąco po cenie i biorąc pierwsze k
         * (lub korzystając z PriorityQueue z odpowiednim Comparatorem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StabilitySort {
        /*
         * 🧪 Zadanie 25:
         * Lista Person(name, age), kilka osób w tym samym wieku, w konkretnej kolejności wstawienia.
         * Posortuj List.sort(Comparator.comparingInt(Person::getAge)) i sprawdź,
         * czy osoby o tym samym wieku zachowują oryginalną wzajemną kolejność (stabilność sortowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SemanticVersionComparator {
        /*
         * 🧪 Zadanie 26:
         * Lista wersji jako Stringi {"1.2.10","1.2.9","1.10.0","1.2.2","2.0.0"}.
         * Napisz Comparator<String> porównujący wersje semantycznie (numerycznie po segmentach),
         * nie leksykograficznie – tak by "1.2.9" był przed "1.2.10".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FileSystemSort {
        /*
         * 🧪 Zadanie 27:
         * Klasa FileEntry(name, isDirectory, size). Lista wpisów (foldery i pliki wymieszane).
         * Posortuj tak, by foldery były przed plikami, a w ramach grupy alfabetycznie po nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_KNearestPoints {
        /*
         * 🧪 Zadanie 28:
         * Klasa Point(x,y). Lista 10 punktów.
         * Napisz Comparator porównujący punkty po odległości euklidesowej od (0,0)
         * i znajdź k=3 najbliższe punkty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MergeIntervalsByComparator {
        /*
         * 🧪 Zadanie 29:
         * Klasa Interval(start,end). Lista przedziałów [[1,3],[2,6],[8,10],[15,18]].
         * Posortuj listę Comparatorem po polu start, a następnie zaimplementuj
         * algorytm scalania nakładających się przedziałów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_PlayerRankingWeighted {
        /*
         * 🧪 Zadanie 30:
         * Klasa Player(name, wins, losses, score). Lista 8 graczy.
         * Zbuduj Comparator łączący kilka kryteriów: najpierw score malejąco,
         * potem współczynnik wins/(wins+losses) malejąco, na końcu nazwa rosnąco.
         * Posortuj listę graczy tym komparatorem.
         */
        public static void main(String[] args) { }
    }
}
