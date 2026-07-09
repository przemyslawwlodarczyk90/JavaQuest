package com.example.javaquest._14_advancedjava.Lesson03_BoundedTypes;

import java.io.Serializable;
import java.util.List;

public class _Lesson03_BoundedTypes {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: TYPY OGRANICZONE (BOUNDED TYPES) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 1-2 I ZAPOWIEDZ
         * ============================================================
         * - W Lekcjach 1-2 poznales <T> jako "dowolny typ" - kompilator nie
         *   zakladal o T NIC poza tym, ze jest jakims typem referencyjnym
         *   (Object, String, Integer, wlasna klasa...).
         * - Problem: skoro T moze byc DOWOLNYM typem, kompilator pozwala
         *   Ci wywolac na zmiennej typu T TYLKO metody z klasy Object
         *   (toString(), equals(), hashCode()...). Nic wiecej - bo nic
         *   wiecej nie jest gwarantowane.
         * - Ta lekcja pokazuje, jak to OGRANICZYC (bound) - powiedziec
         *   kompilatorowi "T to nie byle co, T MUSI byc podtypem X" - i
         *   dzieki temu odblokowac wywolywanie metod z X na zmiennych typu T.
         */
        System.out.println("\nDzisiaj: ograniczanie T (extends), wiele ograniczen naraz, praktyczna metoda max().");

        /*
         * ============================================================
         * 🔹 PROBLEM: T "ZA OGOLNE" - NIE MOZNA WYWOLAC .doubleValue()
         * ============================================================
         * - Wyobraz sobie metode, ktora ma zsumowac liczby z listy generycznej.
         * - Z <T> (bez ograniczen) NIE da sie tego napisac - kompilator nie
         *   wie, ze T ma metode doubleValue() (bo T moglby byc np. String,
         *   ktory tej metody nie ma w ogole).
         * - Ponizsza metoda demonstrateUnboundedProblem() pokazuje probe
         *   (zakomentowana, bo NIE skompilowalaby sie) i wyjasnia dlaczego.
         */
        demonstrateUnboundedProblem();

        /*
         * ============================================================
         * 🔍 ROZWIAZANIE: GORNE OGRANICZENIE <T extends Number>
         * ============================================================
         * - Skladnia `<T extends Number>` mowi kompilatorowi: "T moze byc
         *   TYLKO klasa Number albo jej PODKLASA" (Integer, Double, Long,
         *   BigDecimal, ...).
         * - UWAGA na slowo kluczowe: mimo ze Number to KLASA (nie interfejs),
         *   uzywamy `extends` (nie `implements`!) - w kontekscie generyków
         *   `extends` oznacza "jest podtypem", niezaleznie czy chodzi o
         *   klase czy interfejs. To jedno z najczestszych zrodel pomylek
         *   dla poczatkujacych.
         * - Dzieki temu ograniczeniu kompilator WIE, ze kazda zmienna typu
         *   T ma WSZYSTKIE metody z Number - w tym doubleValue(), intValue()
         *   itd. - i pozwala je wywolywac.
         * - "Gorne ograniczenie" (upper bound) - bo T moze byc Number albo
         *   czyms "nizej" w hierarchii (bardziej szczegolowym podtypem),
         *   ale nigdy "wyzej" (np. Object).
         */
        demonstrateUpperBound();

        /*
         * ============================================================
         * 🔹 GENERYCZNA KLASA Z OGRANICZENIEM: NumericBox<T extends Number>
         * ============================================================
         * - Ograniczenie mozna nalozyc rowniez na parametr typu CALEJ klasy,
         *   nie tylko pojedynczej metody - patrz klasa NumericBox<T extends
         *   Number> na dole pliku.
         * - Skutek: `new NumericBox<String>(...)` juz w ogole NIE skompiluje
         *   sie - String nie jest podtypem Number. Blad zlapany na etapie
         *   tworzenia obiektu, jeszcze przed jakimkolwiek uzyciem.
         */
        demonstrateBoundedClass();

        /*
         * ============================================================
         * 🔍 WIELOKROTNE OGRANICZENIA: <T extends Comparable<T> & Serializable>
         * ============================================================
         * - Parametr typu moze miec WIECEJ niz jedno ograniczenie naraz -
         *   oddzielone znakiem `&` (nie przecinkiem! przecinek jest
         *   zarezerwowany do oddzielania KOLEJNYCH parametrow typu, np. <K, V>).
         * - Przyklad: `<T extends Comparable<T> & Serializable>` oznacza
         *   "T musi implementowac Comparable<T> ORAZ Serializable jednoczesnie".
         * - WAZNA ZASADA SKLADNIOWA: jesli wsrod ograniczen jest KLASA (nie
         *   interfejs), MUSI byc wymieniona JAKO PIERWSZA. Po niej moze byc
         *   dowolna liczba interfejsow. Java pozwala na WIELE interfejsow,
         *   ale co najwyzej JEDNA klase (bo Java nie ma wielodziedziczenia
         *   klas) - np. `<T extends Number & Comparable<T> & Serializable>`
         *   jest poprawne, ale `<T extends Comparable<T> & Number>` juz NIE
         *   (klasa Number musialaby byc pierwsza).
         */
        demonstrateMultipleBounds();

        /*
         * ============================================================
         * 📌 PRAKTYCZNA METODA GENERYCZNA: static <T extends Comparable<T>> T max(T a, T b)
         * ============================================================
         * - To jeden z najbardziej klasycznych przykladow ograniczonego
         *   typu w praktyce - metoda max() musi umiec PORÓWNAC a i b, wiec
         *   T musi implementowac Comparable<T> (miec metode compareTo()).
         * - Bez ograniczenia `<T extends Comparable<T>>` wywolanie
         *   `a.compareTo(b)` w ogole by sie nie skompilowalo - zwykly <T>
         *   nie gwarantuje istnienia metody compareTo().
         * - Takiej samej sztuczki uzywa m.in. `Collections.max()` z JDK.
         */
        demonstrateGenericMax();

        /*
         * ============================================================
         * 🔍 DOLNE OGRANICZENIE NA PARAMETRZE TYPU? - TU JESZCZE NIE
         * ============================================================
         * - Mozna by pomyslec o "dolnym ograniczeniu" (T MUSI byc NADTYPEM
         *   czegos) na parametrze typu klasy/metody - ale Java tego NIE
         *   udostepnia bezposrednio dla `<T>`. Zamiast tego dolne ograniczenia
         *   (`super`) sa zarezerwowane dla WILDCARDÓW (`? super X`) w
         *   typach PARAMETROW metod - to bedzie tematem calej nastepnej
         *   lekcji (Lesson04).
         */
        System.out.println("\n(Dolne ograniczenia typu 'super' to temat Lekcji 4 - wildcardy.)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bez ograniczen <T> daje dostep TYLKO do metod z Object.
         * - Gorne ograniczenie `<T extends X>` (X = klasa LUB interfejs -
         *   zawsze slowo `extends`) odblokowuje metody z X na zmiennych typu T.
         * - Wielokrotne ograniczenia: `<T extends A & B & C>` - co najwyzej
         *   JEDNA klasa (musi byc PIERWSZA), reszta to interfejsy.
         * - Klasyczny wzorzec: `<T extends Comparable<T>>` - gdy metoda musi
         *   porownywac wartosci typu T.
         * - W kolejnej lekcji (Lesson04): wildcardy `?`, `? extends X` i
         *   `? super X` - inny mechanizm ograniczania typow, uzywany w
         *   typach PARAMETROW metod (nie przy definiowaniu wlasnych klas
         *   generycznych).
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateUnboundedProblem() {
        System.out.println("\n=== PROBLEM: NIEOGRANICZONE T NIE MA METOD Number ===");

        // Ponizsza metoda generyczna BEZ ograniczenia nie moglaby wywolac
        // n.doubleValue() - bo kompilator nie wie, ze T to liczba:
        //
        //     static <T> double sumUnbounded(List<T> list) {
        //         double total = 0;
        //         for (T element : list) {
        //             total += element.doubleValue(); // BLAD KOMPILACJI!
        //         }                                     // T nie ma doubleValue()
        //         return total;
        //     }
        //
        System.out.println("Z <T> (bez ograniczen) NIE mozna wywolac element.doubleValue() - kompilator nie");
        System.out.println("gwarantuje, ze T jest liczba. Potrzebne jest gorne ograniczenie <T extends Number>.");
    }

    private static void demonstrateUpperBound() {
        System.out.println("\n=== GORNE OGRANICZENIE: <T extends Number> ===");

        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        List<Double> doubles = List.of(1.5, 2.5, 3.0);

        System.out.println("sumOfNumbers(List<Integer>) = " + sumOfNumbers(integers));
        System.out.println("sumOfNumbers(List<Double>) = " + sumOfNumbers(doubles));
        // sumOfNumbers(List.of("a", "b")); // <-- TO NIE SKOMPILUJE SIE - String nie jest Number
    }

    /**
     * T ograniczone do Number (i jego podtypow) - dzieki temu wolno wywolac
     * n.doubleValue() wewnatrz metody, mimo ze T jest parametrem generycznym.
     */
    private static <T extends Number> double sumOfNumbers(List<T> list) {
        double total = 0;
        for (T n : list) {
            total += n.doubleValue(); // dostepne DZIEKI ograniczeniu extends Number
        }
        return total;
    }

    private static void demonstrateBoundedClass() {
        System.out.println("\n=== KLASA GENERYCZNA Z OGRANICZENIEM: NumericBox<T extends Number> ===");

        NumericBox<Integer> intBox = new NumericBox<>(42);
        NumericBox<Double> doubleBox = new NumericBox<>(3.14);
        // NumericBox<String> stringBox = new NumericBox<>("nie liczba"); // BLAD KOMPILACJI

        System.out.println("intBox.asDouble() = " + intBox.asDouble());
        System.out.println("doubleBox.asDouble() = " + doubleBox.asDouble());
        System.out.println("NumericBox<String> nie skompilowalby sie - String nie jest podtypem Number.");
    }

    private static void demonstrateMultipleBounds() {
        System.out.println("\n=== WIELOKROTNE OGRANICZENIA: <T extends Comparable<T> & Serializable> ===");

        System.out.println("describeIfComparableAndSerializable(\"Ala\") = "
                + describeIfComparableAndSerializable("Ala"));
        System.out.println("describeIfComparableAndSerializable(123) = "
                + describeIfComparableAndSerializable(123));

        System.out.println("String i Integer spelniaja OBA ograniczenia naraz (implementuja Comparable I Serializable).");
        System.out.println("Kolejnosc w deklaracji <T extends Comparable<T> & Serializable> jest tu poprawna,");
        System.out.println("bo oba ograniczenia to INTERFEJSY - gdyby wsrod nich byla klasa, musialaby byc PIERWSZA.");
    }

    /**
     * Wiele ograniczen naraz, oddzielonych `&`: T musi implementowac zarowno
     * Comparable<T> (umie sie porownywac), jak i Serializable (umie sie
     * zserializowac). String i Integer spelniaja oba warunki.
     */
    private static <T extends Comparable<T> & Serializable> String describeIfComparableAndSerializable(T value) {
        return value + " (porownywalny i serializowalny, klasa: " + value.getClass().getSimpleName() + ")";
    }

    private static void demonstrateGenericMax() {
        System.out.println("\n=== PRAKTYCZNA METODA: static <T extends Comparable<T>> T max(T a, T b) ===");

        System.out.println("max(10, 25) = " + max(10, 25));
        System.out.println("max(\"banan\", \"ananas\") = " + max("banan", "ananas"));
        System.out.println("max(3.14, 2.71) = " + max(3.14, 2.71));
    }

    /**
     * T ograniczone do Comparable<T> - dzieki temu wolno wywolac
     * a.compareTo(b) wewnatrz metody. Dziala dla kazdego typu, ktory
     * "umie sie porownywac" - Integer, String, Double, wlasne klasy...
     */
    private static <T extends Comparable<T>> T max(T a, T b) {
        return (a.compareTo(b) >= 0) ? a : b;
    }

    /**
     * Klasa generyczna z gornym ograniczeniem na poziomie CALEJ klasy - T
     * moze byc TYLKO Number albo jego podtypem. Dzieki temu kazda metoda
     * wewnatrz klasy moze bez problemu wywolywac metody z Number na `value`.
     */
    static class NumericBox<T extends Number> {
        private final T value;

        NumericBox(T value) {
            this.value = value;
        }

        double asDouble() {
            return value.doubleValue(); // dostepne DZIEKI ograniczeniu <T extends Number>
        }
    }
}
