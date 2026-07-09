package com.example.javaquest._14_advancedjava.Lesson02_GenericClassesAndMethods;

import java.util.ArrayList;
import java.util.List;

public class _Lesson02_GenericClassesAndMethods {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: KLASY I METODY GENERYCZNE ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 1 I ZAPOWIEDZ
         * ============================================================
         * - W Lekcji 1 poznales najprostsza mozliwa klase generyczna Box<T> -
         *   jeden parametr typu, jedna wartosc.
         * - Ta lekcja idzie dalej: klasy z WIELOMA parametrami typu (Pair<K,V>),
         *   metody generyczne (niezalezne od tego, czy klasa jest generyczna),
         *   wnioskowanie typow (type inference) przy wywolaniu, generyczne
         *   konstruktory i generyczne interfejsy.
         */
        System.out.println("\nDzisiaj: wiele parametrow typu, metody generyczne, wnioskowanie typow, interfejsy generyczne.");

        /*
         * ============================================================
         * 🔹 KLASA GENERYCZNA Z WIELOMA PARAMETRAMI TYPU: Pair<K, V>
         * ============================================================
         * - Klasa moze miec WIECEJ niz jeden parametr typu - oddzielone
         *   przecinkiem w <...>, np. <K, V> jak w Map<K, V>.
         * - Pair<K, V> to klasyczny przyklad - przechowuje DWIE powiazane
         *   wartosci (np. klucz i wartosc, albo dowolna para: imie+wiek,
         *   wspolrzedne x+y).
         * - K i V moga byc RÓZNYMI typami (np. Pair<String, Integer>) albo
         *   tym samym typem (Pair<Integer, Integer>) - to decyduje kod
         *   uzywajacy klasy, nie sama klasa.
         */
        demonstratePairClass();

        /*
         * ============================================================
         * 🔍 METODY GENERYCZNE - NIEZALEZNE OD TEGO, CZY KLASA JEST GENERYCZNA
         * ============================================================
         * - Metoda MOZE miec WLASNY parametr typu, nawet jesli klasa, w
         *   ktorej sie znajduje, NIE jest generyczna (tak jak metody
         *   statyczne ponizej sa w zwyklej, negenerycznej klasie).
         * - Skladnia: parametr typu deklarujesz PRZED typem zwracanym:
         *     `static <T> T firstNonNull(T a, T b)`
         *   Ten <T> na poczatku to deklaracja "ta metoda ma wlasny parametr
         *   typu T" - dopiero potem uzywasz T jako typu parametrow/wyniku.
         * - Metoda generyczna moze byc STATYCZNA lub INSTANCYJNA - obie formy
         *   sa pokazane nizej.
         */
        demonstrateGenericMethods();

        /*
         * ============================================================
         * 🔹 WNIOSKOWANIE TYPOW (TYPE INFERENCE) PRZY WYWOLANIU
         * ============================================================
         * - Przy wywolywaniu metody generycznej NIE musisz (zwykle) podawac
         *   jawnie typu - kompilator SAM go wywnioskuje z typow argumentow.
         *     `firstNonNull("a", "b")`   -> T wywnioskowane jako String
         *     `firstNonNull(1, 2)`       -> T wywnioskowane jako Integer
         * - Analogicznie diamond operator `new Pair<>(...)` (od Javy 7) -
         *   kompilator wywnioskuje typy argumentow z typu zmiennej docelowej
         *   lub z argumentow konstruktora - nie trzeba powtarzac
         *   `new Pair<String, Integer>(...)`.
         * - Jesli wnioskowanie zawiedzie (rzadkie, niejednoznaczne przypadki),
         *   mozna podac typ JAWNIE: `Lesson02...<String>firstNonNull(a, b)`
         *   (skladnia rzadko uzywana w praktyce).
         */
        demonstrateTypeInference();

        /*
         * ============================================================
         * 🔍 GENERYCZNY KONSTRUKTOR
         * ============================================================
         * - Sam KONSTRUKTOR tez moze miec wlasny parametr typu, niezalezny
         *   od parametrow typu klasy (rzadziej spotykane, ale wazne do
         *   rozpoznania w cudzym kodzie).
         * - Przyklad nizej: klasa Wrapper (NIE generyczna) ma konstruktor
         *   generyczny, ktory przyjmuje dowolny typ T i wypisuje jego opis -
         *   ale NIE przechowuje go jako pole (bo klasa nie jest generyczna).
         */
        demonstrateGenericConstructor();

        /*
         * ============================================================
         * 📌 INTERFEJS GENERYCZNY: Container<T>
         * ============================================================
         * - Rowniez INTERFEJSY moga byc generyczne - to fundament wielu API
         *   JDK (np. List<E>, Comparable<T>, Comparator<T>).
         * - Ponizej: prosty interfejs Container<T> z metodami put(T)/get(),
         *   zaimplementowany przez klase SimpleContainer<T>.
         * - Klasa implementujaca MOZE albo przekazac dalej swoj wlasny
         *   parametr typu (jak tutaj: `class SimpleContainer<T> implements
         *   Container<T>`), albo ZWIAZAC go z konkretnym typem (np.
         *   `class StringContainer implements Container<String>`).
         */
        demonstrateGenericInterface();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Klasy generyczne moga miec WIELE parametrow typu: <K, V>, <A, B, C>...
         * - Metody generyczne deklaruja WLASNY parametr typu przed typem
         *   zwracanym: `static <T> T method(...)` - dzialaja tez w klasach
         *   niegenerycznych.
         * - Kompilator zwykle SAM wnioskuje typy (type inference) - diamond
         *   operator <> i wywolania metod generycznych.
         * - Interfejsy tez moga byc generyczne (Container<T>) - fundament
         *   API takiego jak List<E>, Comparable<T>.
         * - W kolejnej lekcji (Lesson03): jak OGRANICZYC, jakie typy wolno
         *   podstawic pod T (bounded types) - i dlaczego czasem T "za bardzo
         *   ogolne" przeszkadza (np. gdy potrzebujesz wywolac .doubleValue()
         *   na T).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstratePairClass() {
        System.out.println("\n=== KLASA GENERYCZNA Z DWOMA PARAMETRAMI: Pair<K, V> ===");

        Pair<String, Integer> nameAndAge = new Pair<>("Kasia", 29);
        System.out.println("Pair<String, Integer>: " + nameAndAge);

        Pair<Integer, Integer> coordinates = new Pair<>(10, 20);
        System.out.println("Pair<Integer, Integer>: " + coordinates);

        System.out.println("nameAndAge.getKey() = " + nameAndAge.getKey());
        System.out.println("nameAndAge.getValue() = " + nameAndAge.getValue());
    }

    private static void demonstrateGenericMethods() {
        System.out.println("\n=== METODY GENERYCZNE ===");

        String firstString = firstNonNull(null, "domyslny");
        Integer firstInt = firstNonNull(null, 42);
        System.out.println("firstNonNull(null, \"domyslny\") = " + firstString);
        System.out.println("firstNonNull(null, 42) = " + firstInt);

        List<String> names = List.of("Beata", "Adam", "Celina");
        String first = new _Lesson02_GenericClassesAndMethods().firstElement(names);
        System.out.println("firstElement(names) [metoda instancyjna] = " + first);
    }

    /**
     * Metoda generyczna statyczna - zwraca pierwszy argument, jesli nie jest
     * null, w przeciwnym razie drugi. Typ T jest wspolny dla obu parametrow
     * i wyniku - kompilator to wymusi.
     */
    private static <T> T firstNonNull(T a, T b) {
        return (a != null) ? a : b;
    }

    /**
     * Metoda generyczna INSTANCYJNA (klasa _Lesson02_... sama nie jest
     * generyczna, ale ta konkretna metoda ma wlasny parametr typu T).
     */
    private <T> T firstElement(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Lista jest pusta");
        }
        return list.get(0);
    }

    private static void demonstrateTypeInference() {
        System.out.println("\n=== WNIOSKOWANIE TYPOW (TYPE INFERENCE) ===");

        // Diamond operator - kompilator wnioskuje <String, Integer> z lewej strony
        Pair<String, Integer> inferred = new Pair<>("wywnioskowany", 1);
        System.out.println("new Pair<>(\"wywnioskowany\", 1) -> " + inferred);

        // Wywolanie metody generycznej bez podawania typu jawnie
        String result = firstNonNull("A", "B");
        System.out.println("firstNonNull(\"A\", \"B\") -> T wywnioskowane jako String -> " + result);

        // Jawne podanie typu (rzadko potrzebne) - dla ilustracji skladni
        String resultExplicit = _Lesson02_GenericClassesAndMethods.<String>firstNonNull(null, "jawny typ");
        System.out.println("<String>firstNonNull(null, \"jawny typ\") [jawnie] -> " + resultExplicit);
    }

    private static void demonstrateGenericConstructor() {
        System.out.println("\n=== GENERYCZNY KONSTRUKTOR (w klasie NIEgenerycznej) ===");

        Wrapper wrapperFromString = new Wrapper("tekst");
        Wrapper wrapperFromInt = new Wrapper(2026);

        System.out.println(wrapperFromString.getDescription());
        System.out.println(wrapperFromInt.getDescription());
    }

    private static void demonstrateGenericInterface() {
        System.out.println("\n=== INTERFEJS GENERYCZNY: Container<T> ===");

        Container<String> container = new SimpleContainer<>();
        container.put("pierwsza wartosc");
        System.out.println("container.get() = " + container.get());

        container.put("nadpisana wartosc");
        System.out.println("po ponownym put(): container.get() = " + container.get());
    }

    /**
     * Prosta klasa generyczna z DWOMA parametrami typu - klucz (K) i
     * wartosc (V), analogicznie do Map.Entry<K, V> z JDK.
     */
    static class Pair<K, V> {
        private final K key;
        private final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    /**
     * Klasa NIE generyczna, ale z generycznym KONSTRUKTOREM - parametr typu
     * T nalezy do konstruktora, nie do calej klasy, wiec NIE moze byc
     * przechowany jako pole typu T.
     */
    static class Wrapper {
        private final String description;

        <T> Wrapper(T value) {
            this.description = "Wrapper(" + value.getClass().getSimpleName() + "): " + value;
        }

        String getDescription() {
            return description;
        }
    }

    /**
     * Generyczny interfejs - fundament API takiego jak List<E>, Comparable<T>.
     */
    interface Container<T> {
        void put(T value);

        T get();
    }

    /**
     * Implementacja przekazujaca swoj wlasny parametr typu T dalej do
     * interfejsu generycznego (Container<T>).
     */
    static class SimpleContainer<T> implements Container<T> {
        private final List<T> storage = new ArrayList<>();

        @Override
        public void put(T value) {
            storage.clear(); // przechowujemy tylko ostatnia wartosc, dla prostoty demo
            storage.add(value);
        }

        @Override
        public T get() {
            if (storage.isEmpty()) {
                throw new IllegalStateException("Container jest pusty - nie wywolano put()");
            }
            return storage.get(0);
        }
    }
}
