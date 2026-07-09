package com.example.javaquest._14_advancedjava.Lesson11_BuiltInFunctionalInterfaces;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

public class _Lesson11_BuiltInFunctionalInterfaces {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 WPROWADZENIE
         * ============================================================
         * W Lesson08 nauczyłeś się pisać WŁASNE interfejsy funkcyjne, w
         * Lesson09 poznałeś składnię lambdy, w Lesson10 - referencje do
         * metod. Po drodze regularnie "pożyczaliśmy" gotowe interfejsy z
         * pakietu java.util.function (Function, Supplier, BiFunction...),
         * ale nigdy nie zrobiliśmy im pełnego, systematycznego przeglądu.
         * Ta lekcja to WŁAŚNIE ten przegląd - "szwedzki stół" gotowych
         * interfejsów funkcyjnych, których biblioteka standardowa
         * dostarcza tak, żebyś sam nie musiał wynajdywać koła (a robisz to
         * tylko wtedy, gdy naprawdę czegoś brakuje, jak TriFunction w
         * Lesson08).
         *
         * Cztery "rodziny" - różnią się TYM, CO robią z argumentem/
         * wynikiem:
         *   Function  - PRZEKSZTAŁCA wejście w wyjście (T -> R)
         *   Predicate - TESTUJE wejście, zwraca boolean (T -> boolean)
         *   Supplier  - DOSTARCZA wartość, BEZ wejścia (() -> T)
         *   Consumer  - KONSUMUJE wejście, BEZ wyniku (T -> void)
         */

        /*
         * ============================================================
         * 🔹 Function<T, R> i BiFunction<T, U, R>
         * ============================================================
         * Function<T, R>.apply(T t): R - przekształca wartość typu T na
         * wartość typu R.
         */
        Function<String, Integer> length = String::length;
        System.out.println("length.apply(\"Ala ma kota\") = " + length.apply("Ala ma kota"));

        // BiFunction<T, U, R>.apply(T t, U u): R - to samo, ale z DWOMA argumentami wejściowymi
        BiFunction<String, String, Integer> commonPrefixLength = (a, b) -> {
            int i = 0;
            while (i < a.length() && i < b.length() && a.charAt(i) == b.charAt(i)) {
                i++;
            }
            return i;
        };
        System.out.println("commonPrefixLength(\"programista\", \"program\") = "
                + commonPrefixLength.apply("programista", "program"));

        /*
         * ============================================================
         * 🔹 Predicate<T> i BiPredicate<T, U>
         * ============================================================
         * Predicate<T>.test(T t): boolean - "pytanie" o wartość, zawsze
         * zwraca true/false. Bardzo często używane jako warunek filtracji.
         */
        Predicate<String> isBlank = String::isBlank;
        System.out.println("isBlank.test(\"   \") = " + isBlank.test("   "));
        System.out.println("isBlank.test(\"abc\") = " + isBlank.test("abc"));

        // BiPredicate<T, U>.test(T t, U u): boolean - test z DWOMA argumentami
        BiPredicate<String, Integer> isLongerThan = (s, n) -> s.length() > n;
        System.out.println("isLongerThan.test(\"Java\", 3) = " + isLongerThan.test("Java", 3));

        /*
         * ============================================================
         * 🔹 Supplier<T>
         * ============================================================
         * Supplier<T>.get(): T - BEZ argumentów, dostarcza (produkuje)
         * wartość. Przeciwieństwo Consumer.
         */
        Supplier<Double> randomValue = Math::random;
        System.out.println("randomValue.get() = " + randomValue.get() + " (0.0-1.0)");

        /*
         * ============================================================
         * 🔹 Consumer<T> i BiConsumer<T, U>
         * ============================================================
         * Consumer<T>.accept(T t): void - PRZYJMUJE wartość, NIC nie
         * zwraca (efekt uboczny - np. wypisanie, zapis, mutacja).
         */
        Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
        printUpper.accept("java jest super");

        // BiConsumer<T, U>.accept(T t, U u): void - dwa argumenty, bez wyniku
        BiConsumer<String, Integer> printRepeated = (s, times) -> {
            for (int i = 0; i < times; i++) {
                System.out.print(s);
            }
            System.out.println();
        };
        printRepeated.accept("ha", 3);

        /*
         * ============================================================
         * 🔹 UnaryOperator<T> i BinaryOperator<T>
         * ============================================================
         * To SPECJALIZACJE Function/BiFunction dla przypadku, gdy typ
         * WYNIKU jest TAKI SAM jak typ (typy) WEJŚCIA:
         *   UnaryOperator<T>  extends Function<T, T>
         *   BinaryOperator<T> extends BiFunction<T, T, T>
         *
         * Sens: sama sygnatura mówi czytelnikowi "wejście i wyjście są
         * tego samego typu" - np. "operacja NA liczbie zwracająca liczbę",
         * bez dodatkowego parametru generycznego na wynik.
         */
        UnaryOperator<Integer> negate = x -> -x;
        System.out.println("negate.apply(9) = " + negate.apply(9));

        BinaryOperator<Integer> multiply = (a, b) -> a * b;
        System.out.println("multiply.apply(6, 7) = " + multiply.apply(6, 7));

        /*
         * ============================================================
         * 🔍 KOMPOZYCJA - ŁĄCZENIE FUNKCJI W ŁAŃCUCHY
         * ============================================================
         * Function.andThen(after) - najpierw TA funkcja, POTEM "after"
         * (kolejność jak w tekście: this -> after)
         */
        Function<Integer, Integer> addTen = x -> x + 10;
        Function<Integer, Integer> square = x -> x * x;

        Function<Integer, Integer> addThenSquare = addTen.andThen(square);
        System.out.println("addThenSquare.apply(2) = " + addThenSquare.apply(2)
                + " (czyli (2+10)^2 = 144)");

        // Function.compose(before) - najpierw "before", POTEM ta funkcja
        // (kolejność ODWROTNA niż andThen: before -> this)
        Function<Integer, Integer> squareThenAdd = addTen.compose(square);
        System.out.println("squareThenAdd.apply(2) = " + squareThenAdd.apply(2)
                + " (czyli 2^2 + 10 = 14)");

        // Predicate.and / .or / .negate - łączenie warunków logicznych
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> isEven = x -> x % 2 == 0;

        Predicate<Integer> isPositiveAndEven = isPositive.and(isEven);
        Predicate<Integer> isPositiveOrEven = isPositive.or(isEven);
        Predicate<Integer> isNotPositive = isPositive.negate();

        System.out.println("isPositiveAndEven.test(4)  = " + isPositiveAndEven.test(4));
        System.out.println("isPositiveAndEven.test(-4) = " + isPositiveAndEven.test(-4));
        System.out.println("isPositiveOrEven.test(-4)  = " + isPositiveOrEven.test(-4));
        System.out.println("isNotPositive.test(-4)     = " + isNotPositive.test(-4));

        // Function.identity() - gotowa funkcja x -> x, przydatna jako
        // "wartość domyślna" w kontekstach, gdzie API wymaga Function
        // (np. Collectors.toMap(keyFn, Function.identity()))
        Function<String, String> identity = Function.identity();
        System.out.println("identity.apply(\"bez zmian\") = " + identity.apply("bez zmian"));

        /*
         * ============================================================
         * 🔍 SPECJALIZACJE PRYMITYWNE - PO CO?
         * ============================================================
         * Function<T, R>, Predicate<T> itd. są GENERYCZNE - dla typu
         * prymitywnego jak int wymagałyby AUTOBOXINGU (int -> Integer i z
         * powrotem) przy KAŻDYM wywołaniu. To dodatkowy narzut wydajnościowy
         * (tworzenie obiektów Integer) w kodzie, który może być wywoływany
         * MILIONY razy (np. wewnątrz strumienia przetwarzającego duży
         * zbiór danych). Dlatego java.util.function dostarcza gotowe
         * specjalizacje dla int/long/double, które operują na
         * PRYMITYWACH BEZPOŚREDNIO - bez boxowania.
         *
         * Konwencja nazewnictwa:
         *   IntXxx      - PRZYJMUJE prymityw int jako wejście
         *   ToIntXxx    - ZWRACA prymityw int jako wynik
         *   (analogicznie dla Long/Double)
         */
        IntPredicate isEvenInt = x -> x % 2 == 0; // zamiast Predicate<Integer>
        System.out.println("isEvenInt.test(8) = " + isEvenInt.test(8));

        IntUnaryOperator doubleInt = x -> x * 2; // zamiast UnaryOperator<Integer>
        System.out.println("doubleInt.applyAsInt(21) = " + doubleInt.applyAsInt(21));

        IntBinaryOperator addInts = (a, b) -> a + b; // zamiast BinaryOperator<Integer>
        System.out.println("addInts.applyAsInt(3, 4) = " + addInts.applyAsInt(3, 4));

        IntSupplier randomInt = () -> (int) (Math.random() * 100); // zamiast Supplier<Integer>
        System.out.println("randomInt.getAsInt() = " + randomInt.getAsInt() + " (0-99)");

        IntConsumer printSquare = x -> System.out.println(x + "^2 = " + (x * x)); // zamiast Consumer<Integer>
        printSquare.accept(9);

        ToIntFunction<String> parseLength = String::length; // T -> int (zamiast Function<String, Integer>)
        System.out.println("parseLength.applyAsInt(\"Java\") = " + parseLength.applyAsInt("Java"));

        IntFunction<String> intToBinaryString = Integer::toBinaryString; // int -> R (zamiast Function<Integer, String>)
        System.out.println("intToBinaryString.apply(10) = " + intToBinaryString.apply(10));

        /*
         * Te same specjalizacje istnieją dla long (LongPredicate,
         * LongUnaryOperator, ToLongFunction...) i double (DoublePredicate,
         * DoubleUnaryOperator, ToDoubleFunction...) - identyczna logika,
         * inny typ prymitywny. Są nawet "mieszane" warianty dla dwóch
         * RÓŻNYCH typów, np. IntToDoubleFunction (int -> double) czy
         * ObjIntConsumer<T> (T + int -> void) - ale to już rzadziej
         * spotykane szczegóły, sięgniesz po nie, gdy realnie zajdzie
         * potrzeba (a wtedy JavaDoc pakietu java.util.function jest
         * najlepszym punktem odniesienia).
         */

        List<Integer> numbers = List.of(3, -1, 8, 0, -5, 12);
        long positiveCount = numbers.stream().filter(x -> x > 0).count();
        System.out.println("Liczba dodatnich w " + numbers + " = " + positiveCount);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE LEKCJI 11 (i całego bloku "Programowanie funkcyjne")
         * ============================================================
         * - Function<T,R>/BiFunction<T,U,R> - przekształcają wejście w
         *   wyjście.
         * - Predicate<T>/BiPredicate<T,U> - testują wejście, zwracają
         *   boolean.
         * - Supplier<T> - dostarcza wartość bez wejścia.
         * - Consumer<T>/BiConsumer<T,U> - konsumują wejście, bez wyniku
         *   (efekt uboczny).
         * - UnaryOperator<T>/BinaryOperator<T> - specjalizacje Function/
         *   BiFunction, gdy typ wyniku = typ wejścia.
         * - Kompozycja: andThen (this -> after), compose (before -> this),
         *   and/or/negate na Predicate, Function.identity().
         * - Specjalizacje prymitywne (IntXxx/ToIntXxx i analogi dla
         *   long/double) unikają kosztu autoboxingu w kodzie wykonywanym
         *   bardzo często.
         *
         * To zamyka blok "Programowanie funkcyjne" (Lesson08-11):
         * Lesson08 - reguła SAM i własne interfejsy funkcyjne, Lesson09 -
         * pełna składnia lambdy i reguły domykania się nad zmiennymi,
         * Lesson10 - referencje do metod jako skrót dla lambdy, Lesson11 -
         * systematyczny przegląd gotowych interfejsów z java.util.function.
         * Mając ten fundament, streamy i API kolekcji (poznane wcześniej w
         * _03_collections) przestają być "magią" - to zwykłe wywołania
         * metod przyjmujących te właśnie interfejsy.
         *
         * Kolejny blok kursu (Lesson12+) to ADNOTACJE (annotations) -
         * zupełnie inny temat: jak czytać, pisać i przetwarzać własne
         * adnotacje, meta-adnotacje (@Retention, @Target) oraz podstawy
         * refleksji potrzebnej, by je odczytać w czasie działania programu.
         */

        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }
}
