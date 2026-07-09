package com.example.javaquest._14_advancedjava.Lesson02_GenericClassesAndMethods;

public class _Exercises_Lesson02_GenericClassesAndMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreatePairClassAndUseIt {
        /*
         * 🧪 Zadanie 1:
         * Odtworz (lub skopiuj) klase Pair<K, V> z teorii lekcji - stworz
         * Pair<String, Integer> reprezentujaca ("Marek", 35) i wypisz
         * getKey() oraz getValue().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreatePairWithSameTypeForBoth {
        /*
         * 🧪 Zadanie 2:
         * Uzywajac Pair<K, V> z Zadania 1, stworz Pair<Integer, Integer>
         * reprezentujaca wspolrzedne (x=5, y=10) i wypisz je w formacie
         * "x=5, y=10".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteGenericMethodMax {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode generyczna `static <T> T firstNonNull(T a, T b)`
         * (jak w teorii) i przetestuj ja z parami: (null, "domyslny") oraz
         * (10, 20) - wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseDiamondOperatorExplicitly {
        /*
         * 🧪 Zadanie 4:
         * Stworz zmienna `Pair<String, String> p = new Pair<>("a", "b");`
         * uzywajac diamond operator, a w komentarzu napisz, jak wygladalaby
         * ta sama linia BEZ diamond operator (przed Java 7).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteGenericInterfaceContainerImplementation {
        /*
         * 🧪 Zadanie 5:
         * Odtworz interfejs Container<T> i klase SimpleContainer<T> z teorii -
         * stworz Container<Integer>, wywolaj put(100), potem get() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteStaticGenericMethodIsEqual {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode generyczna `static <T> boolean isEqual(T a, T b)`
         * uzywajaca a.equals(b) (z obsluga null) - przetestuj dla Stringow i
         * dla Integerow, wypisujac wyniki true/false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CreatePairOfPairs {
        /*
         * 🧪 Zadanie 7:
         * Uzywajac Pair<K, V>, stworz Pair<Pair<String, Integer>, String>
         * (para zagniezdzona - klucz to inna para, wartosc to String) i
         * wypisz jej zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteGenericConstructorClass {
        /*
         * 🧪 Zadanie 8:
         * Odtworz klase Wrapper z generycznym konstruktorem z teorii lekcji -
         * stworz 2 instancje Wrapper (z String i z Double) i wypisz ich opisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainTypeInferenceInComment {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wyjasnij, jak kompilator wnioskuje typ T
         * w wywolaniu `firstNonNull("x", "y")` - jaki mechanizm za tym stoi
         * (bez podawania <String> jawnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareGenericVsRawInterfaceUsage {
        /*
         * 🧪 Zadanie 10:
         * Stworz surowy (raw) Container (bez <>) i sprobuj wlozyc do niego
         * String, potem Integer - w komentarzu wyjasnij, dlaczego to sie
         * kompiluje (i dlaczego to ZLA praktyka, mimo ze dziala).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteGenericMethodSwapPairSides {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode generyczna
         * `static <K, V> Pair<V, K> swap(Pair<K, V> pair)`, ktora zwraca NOWA
         * pare z zamienionymi miejscami key i value - przetestuj na
         * Pair<String, Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteThreeParameterTripleClass {
        /*
         * 🧪 Zadanie 12:
         * Napisz WLASNA klase generyczna Triple<A, B, C> (analogicznie do
         * Pair<K,V>, ale z trzema polami/parametrami typu) z konstruktorem i
         * metoda toString() - przetestuj z Triple<String, Integer, Boolean>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteGenericMethodToPairList {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode generyczna
         * `static <K, V> Pair<K, V> of(K key, V value)` (statyczna metoda
         * fabrykujaca, wzor znany z Map.entry()) - przetestuj i porownaj z
         * bezposrednim `new Pair<>(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementContainerWithHistory {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNA klase HistoryContainer<T> implements Container<T>
         * (interfejs z teorii), ktora - w odroznieniu od SimpleContainer -
         * PAMIETA wszystkie wstawione wartosci (java.util.List<T> jako
         * historia) i ma dodatkowa metode getHistory() zwracajaca liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteGenericMethodMapListToOtherType {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode generyczna z DWOMA parametrami typu
         * `static <T, R> java.util.List<R> mapList(java.util.List<T> input,
         * java.util.function.Function<T, R> mapper)` - przetestuj przez
         * zamiane List<String> na List<Integer> (dlugosci Stringow).
         * (Function poznasz szczegolowo w Lekcji 11, tu wystarczy uzyc lambdy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareOverloadingVsGenericMethod {
        /*
         * 🧪 Zadanie 16:
         * Napisz 3 przeciazone metody `printType(String)`, `printType(Integer)`,
         * `printType(Double)` ORAZ jedna generyczna
         * `static <T> void printTypeGeneric(T value)` - w komentarzu porownaj
         * liczbe linii kodu i lepsza skalowalnosc podejscia generycznego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildKeyValueRegistryUsingPair {
        /*
         * 🧪 Zadanie 17:
         * Uzywajac Pair<K, V>, stworz java.util.List<Pair<String, Integer>>
         * reprezentujaca "inwentarz" (nazwa produktu -> ilosc), dodaj 4
         * wpisy i wypisz sume wszystkich ilosci (petla po liscie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteGenericMethodWithConstructorReference {
        /*
         * 🧪 Zadanie 18:
         * Napisz generyczna metode fabrykujaca
         * `static <T> Container<T> emptyContainer()` zwracajaca nowy,
         * pusty SimpleContainer<T> - wywolaj ja przypisujac wynik do
         * Container<String> oraz Container<Integer> w tym samym main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainDifferenceGenericClassVsGenericMethod {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: w komentarzu wyjasnij roznice miedzy "klasa generyczna"
         * (parametr typu na poziomie CALEJ klasy, np. Pair<K,V>) a "metoda
         * generyczna" (parametr typu tylko dla JEDNEJ metody, np.
         * firstNonNull) - podaj przyklad sytuacji, gdzie wolisz jedno od drugiego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementComparablePairByFirstElement {
        /*
         * 🧪 Zadanie 20:
         * Rozszerz (lub napisz nowa wersje) Pair<K, V> tak, by V bylo
         * ograniczone (na razie eksperymentalnie, bez `extends` z Lekcji 3)
         * do Integer - napisz metode `int sumIfBothIntegers(Pair<Integer,
         * Integer> pair)` zwracajaca sume key+value.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildGenericBinaryTreeNode {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase generyczna TreeNode<T> z polem T value oraz
         * TreeNode<T> left, right (moga byc null) - zbuduj recznie maly
         * drzewiasty przyklad (3 wezly) i wypisz je metoda in-order (rekurencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_WriteGenericMethodZipTwoLists {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode generyczna
         * `static <A, B> java.util.List<Pair<A, B>> zip(java.util.List<A>
         * left, java.util.List<B> right)`, ktora laczy dwie listy w liste
         * Pair (do dlugosci krotszej listy) - przetestuj z List<String> i
         * List<Integer> roznej dlugosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementGenericCacheWithContainer {
        /*
         * 🧪 Zadanie 23:
         * Napisz klase generyczna KeyValueCache<K, V> oparta na
         * java.util.HashMap<K, V> - metody put(K,V), get(K) (zwraca
         * java.util.Optional<V>), containsKey(K), size() - przetestuj dla
         * KeyValueCache<String, Pair<Integer, Integer>>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteGenericMethodReduceList {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode generyczna
         * `static <T> T reduce(java.util.List<T> list, T identity,
         * java.util.function.BinaryOperator<T> operator)` - przetestuj z
         * List<Integer> licząc sume oraz z List<String> laczac napisy
         * (wystarczy uzyc lambdy, BinaryOperator poznasz dokladnie w Lekcji 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignGenericEventBusSkeleton {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj klase generyczna EventBus<T> z metodami
         * subscribe(java.util.function.Consumer<T> listener) i publish(T event)
         * (wewnetrznie: lista subskrybentow, publish wywoluje wszystkich) -
         * przetestuj dla EventBus<String> z 2 subskrybentami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareContainerImplementationsPolymorphically {
        /*
         * 🧪 Zadanie 26:
         * Majac interfejs Container<T> z DWOMA implementacjami (SimpleContainer
         * i HistoryContainer z Zadania 14), napisz metode
         * `static void demo(Container<String> container)` uzywajaca TYLKO
         * interfejsu - wywolaj ja dla obu implementacji, pokazujac polimorfizm.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WriteGenericBuilderPatternClass {
        /*
         * 🧪 Zadanie 27:
         * Napisz klase generyczna ResultBuilder<T> z metodami
         * withValue(T value) (zwraca this, fluent API) i build() (zwraca T) -
         * uzyj jej do zbudowania wartosci String w stylu
         * `new ResultBuilder<String>().withValue("wynik").build()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGenericLinkedListFromScratch {
        /*
         * 🧪 Zadanie 28:
         * Napisz WLASNA, minimalna generyczna liste wiazana MyLinkedList<T>
         * (wlasny wezel Node<T> z polami value/next, metody addFirst(T),
         * addLast(T), toStringList() do wypisania calej zawartosci) - NIE
         * korzystaj z java.util.LinkedList.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_WriteGenericMethodPartitionList {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode generyczna
         * `static <T> Pair<java.util.List<T>, java.util.List<T>> partition(
         * java.util.List<T> list, java.util.function.Predicate<T> predicate)`,
         * ktora dzieli liste na 2 (spelniajace i niespelniajace warunku) i
         * zwraca je jako Pair - przetestuj dzielac liczby na parzyste/nieparzyste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignGenericRepositoryInterfaceAndImplementation {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj generyczny interfejs
         * Repository<T, ID> z metodami save(ID id, T entity), findById(ID id)
         * (Optional<T>), findAll() (List<T>), deleteById(ID id) - zaimplementuj
         * go jako InMemoryRepository<T, ID> oparty na java.util.HashMap<ID, T>
         * i przetestuj z Repository<String, Integer> (np. "baza" imion pod
         * numerycznym ID). Ten wzorzec zobaczysz ponownie w praktyce, gdy
         * kurs polaczy generyki z warstwa DAO (rozdzial _10_dao).
         */
        public static void main(String[] args) { }
    }
}
