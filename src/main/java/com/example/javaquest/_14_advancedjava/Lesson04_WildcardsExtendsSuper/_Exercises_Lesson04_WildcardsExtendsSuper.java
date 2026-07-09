package com.example.javaquest._14_advancedjava.Lesson04_WildcardsExtendsSuper;

public class _Exercises_Lesson04_WildcardsExtendsSuper {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainInvarianceOfGenerics {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego List<Integer> NIE jest podtypem List<Number>,
         * mimo ze Integer JEST podtypem Number - co by sie moglo stac,
         * gdyby Java na to pozwalala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WritePrintSizeMethodWithUnboundedWildcard {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `static int printSize(java.util.List<?> list)` (jak
         * w teorii) i przetestuj ja z List<String> i List<Integer> - wypisz
         * wyniki dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteSumMethodWithUpperBoundedWildcard {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode `static double sumOfAnyNumberList(java.util.List<? extends Number> list)`
         * (jak w teorii) i przetestuj ja z List<Integer> {1,2,3} oraz
         * List<Double> {1.5, 2.5}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyAddFailsOnExtendsWildcard {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: majac `List<? extends Number> list`, napisz w
         * komentarzu linie `list.add(5);` i wyjasnij, dlaczego kompilator ja
         * odrzuca - co konkretnie mogloby pojsc zle, gdyby na to pozwolono.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteAddIntegersMethodWithLowerBoundedWildcard {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `static void addIntegers(java.util.List<? super Integer> list)`
         * (jak w teorii) i przetestuj ja z List<Integer>, List<Number> i
         * List<Object> - wypisz zawartosc kazdej listy po wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyGetReturnsObjectOnSuperWildcard {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: majac `List<? super Integer> list`, wyjasnij w
         * komentarzu, dlaczego `list.get(0)` zwraca typ Object (a nie
         * Integer), mimo ze wiemy, iz przed chwila dodalismy tam Integer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteUnboundedWildcardPrintMethod {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode `static void printAsObjects(java.util.List<?> list)`
         * (jak w teorii), ktora wypisuje kazdy element w petli for-each jako
         * Object - przetestuj z List<String> i List<Double>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ClassifyWildcardsInGivenSignatures {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: dla ponizszych 3 sygnatur metod napisz w komentarzu,
         * czy wildcard jest unbounded, upper-bounded czy lower-bounded:
         * (a) `void m1(List<?> list)`
         * (b) `void m2(List<? extends String> list)`
         * (c) `void m3(List<? super String> list)`
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TryAddingNullToExtendsWildcardList {
        /*
         * 🧪 Zadanie 9:
         * Stworz `List<? extends Number> list` (wskazujaca na istniejaca
         * List<Integer>) i dodaj do niej `null` (jedyna dozwolona wartosc
         * do dodania) - w komentarzu wyjasnij, dlaczego akurat null jest
         * wyjatkiem od reguly "nie mozna nic dodac".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRawListWithUnboundedWildcardList {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu porownaj `List` (raw type) z
         * `List<?>` - obie "nie wiedza" jaki jest typ elementow, ale jedna
         * jest bezpieczna typowo a druga nie. Wyjasnij roznice.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteCopyMethodUsingExtendsAndSuper {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode generyczna
         * `static <T> void copy(java.util.List<? extends T> source, java.util.List<? super T> destination)`
         * (klasyczny wzorzec z java.util.Collections.copy) kopiujaca
         * wszystkie elementy z source do destination - przetestuj kopiujac
         * List<Integer> do List<Number>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteMaxOfListWithExtendsWildcard {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode
         * `static double maxOfNumberList(java.util.List<? extends Number> list)`
         * zwracajaca najwieksza wartosc jako double (bez modyfikowania
         * listy) - przetestuj z List<Integer> i List<Double>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteFillListMethodWithSuperWildcard {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode generyczna
         * `static <T> void fillWithValue(java.util.List<? super T> list, T value, int count)`,
         * ktora dodaje `value` do listy `count` razy - przetestuj wypelniajac
         * List<Object> wartoscia typu String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhyThisMethodNeedsTNotWildcard {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: majac metode
         * `static <T> T firstOrDefault(List<T> list, T defaultValue)`
         * (zwraca pierwszy element listy albo defaultValue, gdy lista pusta),
         * wyjasnij w komentarzu, dlaczego NIE da sie tu uzyc wildcardu `?`
         * zamiast <T> - co dokladnie by sie zepsulo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteCountMatchingMethodWithWildcard {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode `static int countNonNull(java.util.List<?> list)`
         * zwracajaca liczbe elementow rownych != null - przetestuj z liste
         * zawierajaca kilka wartosci null wymieszanych ze Stringami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildStackOfNumbersAcceptingSubtypes {
        /*
         * 🧪 Zadanie 16:
         * Majac prosta klase `class NumberStack { private List<Number> data; void pushAll(List<? extends Number> source) {...} }`
         * (do zaimplementowania samodzielnie), zaimplementuj pushAll tak, by
         * przyjmowal List<Integer> LUB List<Double> i dokladal wszystkie ich
         * elementy do wewnetrznej listy Number - przetestuj oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainProducerExtendsIntuitionInOwnWords {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: w komentarzu (min. 4 zdania) wyjasnij intuicje
         * "producenta" (`? extends X`) na przykladzie NIE z listy, np.
         * pojemnika z owocami - dlaczego mozna z niego BRAC owoce jako
         * Fruit, ale nie mozna do niego wkladac dowolnego owocu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainConsumerSuperIntuitionInOwnWords {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: analogicznie do Zadania 17, wyjasnij intuicje
         * "konsumenta" (`? super X`) na przykladzie kosza na smieci -
         * dlaczego mozna do niego wrzucac konkretny typ smieci, ale przy
         * wyciaganiu nie wiadomo dokladnie co sie wyciagnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WritePrintAllWithBoundedWildcardOnCustomType {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj prosty interfejs `interface Shape { double area(); }`
         * oraz 2 klasy implementujace go (np. Circle, Square). Napisz
         * metode `static double totalArea(java.util.List<? extends Shape> shapes)`
         * sumujaca pola wszystkich figur - przetestuj z lista mieszajaca
         * Circle i Square.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareThreeVersionsOfSameMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz TRZY wersje tej samej metody liczacej rozmiar listy:
         * `static int sizeRaw(List list)`, `static int sizeUnbounded(List<?> list)`,
         * `static <T> int sizeGeneric(List<T> list)` - w komentarzu porownaj
         * bezpieczenstwo typowe i ostrzezenia kompilatora (warnings) dla
         * kazdej wersji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementGenericCopyAllMethodFully {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj od zera metode generyczna
         * `static <T> void copyAll(java.util.List<? extends T> src, java.util.List<? super T> dst)`
         * (bez uzycia Collections.copy) - przetestuj kopiujac List<Double>
         * do pustej List<Number> i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildEventBusWithSuperWildcardListener {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj klase `class EventBus<T>` z metoda
         * `void subscribe(java.util.function.Consumer<? super T> listener)`
         * (Consumer<? super T> to standardowy wzorzec z JDK) - przetestuj
         * dla EventBus<String>, subskrybujac Consumer<Object> (bo Object
         * jest nadtypem String, wiec pasuje jako "? super String").
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildFactoryWithExtendsWildcardSupplier {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj metode generyczna
         * `static <T> T createFromSupplier(java.util.function.Supplier<? extends T> supplier)`
         * (Supplier<? extends T> - kolejny standardowy wzorzec JDK) - przetestuj,
         * przekazujac Supplier<Integer> i przypisujac wynik do zmiennej typu Number.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMergeListsMethodWithWildcards {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode generyczna
         * `static <T> java.util.List<T> mergeLists(java.util.List<? extends T> a, java.util.List<? extends T> b)`
         * zwracajaca NOWA liste zawierajaca wszystkie elementy z obu list -
         * przetestuj laczac List<Integer> i List<Double> do List<Number>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExplainWhyPecsAppliesToCollectionsAddAll {
        /*
         * 🧪 Zadanie 25:
         * Znajdz (lub przypomnij sobie z rozdzialu _03_collections) sygnature
         * metody `Collections.addAll(Collection<? super T> c, T... elements)`
         * z JDK - w komentarzu wyjasnij, dlaczego pierwszy parametr uzywa
         * `? super T`, a nie po prostu `Collection<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildGenericPrinterAcceptingAnyComparableList {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode
         * `static void printSorted(java.util.List<? extends Comparable<?>> list)`,
         * ktora wypisuje elementy listy w kolejnosci posortowanej (skopiuj
         * liste, posortuj kopie, wypisz) - przetestuj z List<Integer> i
         * List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignGenericStackWithPushAllPopAllWildcards {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj klase generyczna `class Stack<T>` (oparta na
         * java.util.ArrayList<T>) z metodami:
         * `void pushAll(java.util.Collection<? extends T> src)` (dodaje
         * wszystkie elementy z src) oraz
         * `void popAll(java.util.Collection<? super T> dst)` (przenosi
         * wszystkie elementy stosu do dst) - przetestuj obiema metodami dla
         * Stack<Number>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeWhyListOfListExtendsNumberIsTricky {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: majac `List<List<? extends Number>> listOfLists`,
         * wyjasnij w komentarzu, dlaczego NIE mozna wywolac
         * `listOfLists.get(0).add(5)` (podwojny poziom "producenta") - i
         * jakie operacje sa nadal dozwolone na takiej strukturze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGenericMinMaxPairMethodWithWildcard {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode
         * `static Pair<Double, Double> minMax(java.util.List<? extends Number> list)`
         * (zdefiniuj prosta klase Pair<A,B> lub uzyj tablicy dwuelementowej
         * double[]) zwracajaca jednoczesnie minimum i maksimum listy jako
         * double - przetestuj z List<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneGenericLibraryWithBothWildcards {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj klase `class Library<T>` (np.
         * biblioteka ksiazek/mediow typu T) z wewnetrzna
         * `java.util.List<T> items`, oraz metodami:
         * `void addAll(java.util.Collection<? extends T> newItems)`
         * (PRODUCENT - dodaje elementy z zewnatrz),
         * `void exportTo(java.util.Collection<? super T> target)`
         * (KONSUMENT - eksportuje wszystkie elementy do docelowej kolekcji),
         * `int size()`. Przetestuj kompleksowo dla Library<String>: dodaj
         * elementy z List<String>, wyeksportuj do List<Object>, wypisz
         * rozmiar i zawartosc obu kolekcji na koniec.
         */
        public static void main(String[] args) { }
    }
}
