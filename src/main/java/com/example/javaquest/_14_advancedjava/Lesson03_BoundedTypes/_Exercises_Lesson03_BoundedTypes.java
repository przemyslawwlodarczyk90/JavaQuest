package com.example.javaquest._14_advancedjava.Lesson03_BoundedTypes;

public class _Exercises_Lesson03_BoundedTypes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyUnboundedTFailsOnDoubleValue {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij, dlaczego metoda generyczna
         * `static <T> double sum(List<T> list)` (BEZ ograniczenia) nie moze
         * wewnatrz wywolac `element.doubleValue()` - jaki blad zglosi
         * kompilator i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteSumOfNumbersMethod {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode generyczna
         * `static <T extends Number> double sumOfNumbers(java.util.List<T> list)`
         * (jak w teorii) i przetestuj ja z List<Integer> {1, 2, 3} oraz
         * List<Double> {1.5, 2.5} - wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhyExtendsNotImplementsForClassBound {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: w komentarzu wyjasnij, dlaczego zapis
         * `<T extends Number>` uzywa slowa `extends`, mimo ze Number to
         * KLASA a nie interfejs - jakie znaczenie ma tu slowo `extends`
         * w kontekscie generyków (w odroznieniu od dziedziczenia klas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateNumericBoxWithInteger {
        /*
         * 🧪 Zadanie 4:
         * Odtworz klase NumericBox<T extends Number> z teorii - stworz
         * NumericBox<Integer> z wartoscia 77 i wypisz wynik asDouble().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateNumericBoxWithDouble {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac tej samej klasy NumericBox<T extends Number> co w Zadaniu 4,
         * stworz NumericBox<Double> z wartoscia 9.99 i wypisz wynik asDouble().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyNumericBoxOfStringFailsToCompile {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu napisz linie kodu
         * `NumericBox<String> box = new NumericBox<>("tekst");` i wyjasnij,
         * dlaczego kompilator odrzuci ta linie (odwolaj sie do ograniczenia
         * <T extends Number> z definicji klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteGenericMaxMethod {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode generyczna
         * `static <T extends Comparable<T>> T max(T a, T b)` (jak w teorii)
         * i przetestuj ja dla dwoch liczb Integer oraz dwoch napisow String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteGenericMinMethod {
        /*
         * 🧪 Zadanie 8:
         * Analogicznie do max(), napisz metode generyczna
         * `static <T extends Comparable<T>> T min(T a, T b)` zwracajaca
         * MNIEJSZA z dwoch wartosci - przetestuj dla Double i String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListNumberSubtypesThatCompile {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wymien co najmniej 5 klas z JDK, ktore
         * sa podtypami Number (a wiec pasuja jako T w <T extends Number>) -
         * np. Integer, Double...
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteAverageOfNumbersMethod {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode generyczna
         * `static <T extends Number> double average(java.util.List<T> list)`
         * zwracajaca srednia arytmetyczna elementow listy - przetestuj z
         * List<Integer> {2, 4, 6, 8}.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteMultipleBoundMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode generyczna
         * `static <T extends Comparable<T> & java.io.Serializable> String
         * describe(T value)` (jak w teorii) i przetestuj ja dla String i
         * dla Integer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainBoundOrderingRule {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala: w komentarzu wyjasnij zasade kolejnosci ograniczen
         * w `<T extends A & B & C>` - dlaczego klasa (jesli wystepuje) musi
         * byc PIERWSZA, a interfejsy moga byc w dowolnej kolejnosci po niej.
         * Podaj przyklad POPRAWNEJ i NIEPOPRAWNEJ kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteFindMaxInListMethod {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode generyczna
         * `static <T extends Comparable<T>> T findMax(java.util.List<T> list)`,
         * ktora zwraca najwieksza wartosc z listy (bez uzycia
         * Collections.max()) - przetestuj z List<Integer> i List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteBoundedClassWithComparableConstraint {
        /*
         * 🧪 Zadanie 14:
         * Napisz WLASNA klase generyczna
         * `class SortedPair<T extends Comparable<T>>` z konstruktorem
         * przyjmujacym dwie wartosci T i metoda `T bigger()` zwracajaca
         * wieksza z nich (uzyj compareTo) - przetestuj z Integer i String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareUnboundedAndBoundedSumMethods {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj metode `static double sumAsObjects(java.util.List<Object> list)`,
         * ktora probuje zsumowac elementy castujac je na Number (rzutowanie
         * w runtime) - porownaj w komentarzu z metoda sumOfNumbers()
         * z Zadania 2: ktora wersja jest bezpieczniejsza i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteGenericClampMethod {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode generyczna
         * `static <T extends Comparable<T>> T clamp(T value, T min, T max)`,
         * ktora "przycina" wartosc do przedzialu [min, max] - przetestuj dla
         * Integer (np. clamp(15, 0, 10) powinno zwrocic 10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteBoundedClassNumericRange {
        /*
         * 🧪 Zadanie 17:
         * Napisz klase generyczna `class NumericRange<T extends Number & Comparable<T>>`
         * z polami min i max (typu T) oraz metoda `boolean contains(T value)`
         * sprawdzajaca, czy value miesci sie w przedziale [min, max] -
         * przetestuj dla NumericRange<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhyNoLowerBoundOnTypeParameter {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: w komentarzu wyjasnij, dlaczego Java NIE pozwala
         * napisac `<T super Integer>` przy definiowaniu klasy/metody
         * generycznej (w odroznieniu od wildcardow `? super X`, ktore
         * poznasz w Lekcji 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteGenericMethodSumIfAllPositive {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode generyczna
         * `static <T extends Number> double sumIfAllPositive(java.util.List<T> list)`,
         * ktora sumuje elementy TYLKO jesli WSZYSTKIE sa > 0 (uzyj
         * doubleValue()), w przeciwnym razie rzuca IllegalArgumentException -
         * przetestuj oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareBoundedClassVsBoundedMethod {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: w komentarzu porownaj ograniczenie na poziomie
         * KLASY (`class NumericBox<T extends Number>`) z ograniczeniem na
         * poziomie METODY (`static <T extends Number> double sum(...)`) -
         * kiedy wybrac jedno, a kiedy drugie podejscie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildBoundedGenericStatisticsClass {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase generyczna `class Statistics<T extends Number>` z
         * konstruktorem przyjmujacym `java.util.List<T>` oraz metodami sum(),
         * average(), max() (jako double) - przetestuj z Statistics<Integer>
         * i Statistics<Double>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_WriteGenericMethodSortListInPlace {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode generyczna
         * `static <T extends Comparable<T>> void bubbleSort(T[] array)`,
         * ktora sortuje TABLICE metoda babelkowa (bubble sort) korzystajac
         * z compareTo() - przetestuj z Integer[] i String[].
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignBoundedRepositoryForComparableEntities {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj klase generyczna
         * `class SortedRepository<T extends Comparable<T>>` przechowujaca
         * wewnetrznie `java.util.List<T>`, z metoda `add(T item)`, ktora
         * wstawia element TAK, by lista pozostawala posortowana (insertion
         * sort) - przetestuj dodajac kilka Integerow w losowej kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteGenericMethodWithThreeBounds {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode generyczna z TRZEMA ograniczeniami
         * `static <T extends Number & Comparable<T> & java.io.Serializable>
         * T maxOfThree(T a, T b, T c)` zwracajaca najwieksza z trzech
         * wartosci - przetestuj dla Integer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementGenericBinarySearchWithBound {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode generyczna
         * `static <T extends Comparable<T>> int binarySearch(T[] sortedArray, T target)`
         * (klasyczne wyszukiwanie binarne, wlasna implementacja - nie
         * korzystaj z Arrays.binarySearch) - przetestuj na posortowanej
         * tablicy Integer[].
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildBoundedPairWithSumMethod {
        /*
         * 🧪 Zadanie 26:
         * Napisz klase generyczna `class NumberPair<T extends Number>` z
         * dwoma polami typu T i metoda `double sum()` zwracajaca sume obu
         * wartosci jako double - przetestuj dla NumberPair<Integer> i
         * NumberPair<Double>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainErasureImpactOnBoundedTypes {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: w komentarzu napisz, jaki typ (bounded) zostanie
         * uzyty przez kompilator PO type erasure dla `<T extends Number>`
         * (podpowiedz: to NIE bedzie Object) - wyjasnij, dlaczego wybor
         * ograniczenia wplywa na to, co dzieje sie "pod spodem" po kompilacji.
         * (Type erasure poznasz szczegolowo w kolejnych lekcjach rozdzialu.)
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_WriteGenericMethodMedianOfNumbers {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode generyczna
         * `static <T extends Number & Comparable<T>> double median(java.util.List<T> list)`
         * zwracajaca mediane listy (posortuj kopie listy, wez srodkowy
         * element lub srednia dwoch srodkowych) - przetestuj z listami
         * parzystej i nieparzystej dlugosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignGenericValidatorInterfaceWithBound {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj interfejs generyczny
         * `interface RangeValidator<T extends Comparable<T>>` z metoda
         * `boolean isInRange(T value, T min, T max)` (moze miec implementacje
         * domyslna - default method) - zaimplementuj go i przetestuj dla Integer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneNumericAggregator {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj klase generyczna
         * `class NumericAggregator<T extends Number & Comparable<T>>` z
         * wewnetrzna `java.util.List<T>`, metodami `add(T value)`, `sum()`,
         * `average()`, `min()`, `max()` (wszystkie jako double, oprocz
         * min()/max() ktore moga zwracac T) - przetestuj kompleksowo dla
         * NumericAggregator<Integer> z co najmniej 5 wartosciami, wypisujac
         * wszystkie statystyki.
         */
        public static void main(String[] args) { }
    }
}
