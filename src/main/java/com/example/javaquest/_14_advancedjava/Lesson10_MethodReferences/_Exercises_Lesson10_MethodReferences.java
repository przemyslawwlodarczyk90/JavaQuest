package com.example.javaquest._14_advancedjava.Lesson10_MethodReferences;

public class _Exercises_Lesson10_MethodReferences {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StaticMethodReferenceParseInt {
        /*
         * 🧪 Zadanie 1:
         * Utwórz java.util.function.Function<String, Integer> DWOMA
         * sposobami - jako lambdę (s -> Integer.parseInt(s)) i jako
         * referencję do metody statycznej (Integer::parseInt). Sprawdź
         * obie na wejściu "77" i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StaticMethodReferenceParseDouble {
        /*
         * 🧪 Zadanie 2:
         * Utwórz java.util.function.Function<String, Double> jako
         * referencję Double::parseDouble. Sprawdź dla "3.14" i wypisz
         * wynik pomnożony przez 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StaticMethodReferenceMathAbs {
        /*
         * 🧪 Zadanie 3:
         * Utwórz java.util.function.UnaryOperator<Integer> jako referencję
         * Math::abs. Sprawdź dla -15 i 15 - wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BoundReferenceListAdd {
        /*
         * 🧪 Zadanie 4:
         * Utwórz java.util.List<String> o nazwie basket. Utwórz
         * java.util.function.Consumer<String> jako referencję
         * basket::add. Wywołaj accept() trzykrotnie z różnymi produktami i
         * wypisz zawartość basket.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BoundReferenceOnStringVariable {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj String text = "Ala ma kota". Utwórz
         * java.util.function.Supplier<Integer> jako referencję
         * text::length (metoda instancyjna KONKRETNEGO, już istniejącego
         * obiektu - bound reference). Wypisz wynik get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UnboundReferenceStringIsEmpty {
        /*
         * 🧪 Zadanie 6:
         * Utwórz java.util.function.Predicate<String> jako referencję
         * String::isEmpty (niezwiązana - pierwszy parametr staje się
         * odbiorcą wywołania). Sprawdź dla "" i "abc".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UnboundReferenceStringLengthInMap {
        /*
         * 🧪 Zadanie 7:
         * Utwórz java.util.List<String> z co najmniej 4 słowami różnej
         * długości. Za pomocą list.stream().map(String::length).toList()
         * zbuduj listę długości i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConstructorReferenceArrayListSupplier {
        /*
         * 🧪 Zadanie 8:
         * Utwórz java.util.function.Supplier<java.util.List<String>> jako
         * referencję ArrayList::new. Wywołaj get() dwukrotnie, dodaj różne
         * elementy do każdego wyniku i pokaż (przez ==), że to dwa
         * niezależne obiekty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConstructorReferenceStringBuilderFromString {
        /*
         * 🧪 Zadanie 9:
         * Utwórz java.util.function.Function<String, StringBuilder> jako
         * referencję StringBuilder::new (konstruktor przyjmujący String).
         * Sprawdź dla "start" i dopisz do wyniku ".append(\"-koniec\")".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SideBySideLambdaVsReferenceValueOf {
        /*
         * 🧪 Zadanie 10:
         * Zapisz DWIE zmienne typu java.util.function.Function<String,
         * Integer> realizujące Integer.valueOf(String) - jedną jako
         * lambdę, drugą jako referencję do metody statycznej. Sprawdź, że
         * obie zwracają tę samą wartość dla "999", i wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SortWithComparatorMethodReference {
        /*
         * 🧪 Zadanie 11:
         * Utwórz java.util.List<String> z elementami "Zosia", "ala",
         * "Basia", "ela" (celowo różna wielkość liter). Posortuj metodą
         * list.sort(String::compareToIgnoreCase) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ForEachWithSystemOutPrintln {
        /*
         * 🧪 Zadanie 12:
         * Utwórz java.util.List<Integer> z pięcioma liczbami. Wypisz
         * każdą przez list.forEach(System.out::println) - to bound
         * reference do metody instancyjnej konkretnego obiektu
         * System.out. Wyjaśnij w komentarzu, dlaczego to jest rodzaj 2
         * (bound), a nie rodzaj 1 (statyczna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TwoParamUnboundReferenceConcat {
        /*
         * 🧪 Zadanie 13:
         * Utwórz java.util.function.BiFunction<String, String, String>
         * jako referencję String::concat (niezwiązana, dwuparametrowa -
         * pierwszy parametr to odbiorca, drugi to argument metody
         * concat). Sprawdź dla ("Ala", " ma kota").
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TwoArgConstructorReference {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj prostą klasę Point z polami int x, int y i
         * konstruktorem Point(int x, int y) oraz metodą toString(). Utwórz
         * java.util.function.BiFunction<Integer, Integer, Point> jako
         * referencję Point::new. Sprawdź dla (3, 7) i wypisz wynikowy
         * Point.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ArrayConstructorReferenceGeneric {
        /*
         * 🧪 Zadanie 15:
         * Utwórz java.util.function.IntFunction<Integer[]> jako referencję
         * Integer[]::new. Wywołaj apply(4), wypełnij tablicę wartościami
         * 10, 20, 30, 40 w pętli i wypisz ją (java.util.Arrays.toString).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BoundReferenceToCustomObjectMethod {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj klasę Counter z polem int value i metodą
         * increment() (bez argumentów, zwiększa value o 1 i wypisuje nową
         * wartość). Utwórz obiekt Counter i Runnable jako referencję
         * counterInstance::increment. Wywołaj run() trzykrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ComparatorComparingWithMethodReference {
        /*
         * 🧪 Zadanie 17:
         * Utwórz java.util.List<String> słów o różnej długości. Zbuduj
         * java.util.Comparator<String> przez
         * Comparator.comparingInt(String::length) i posortuj listę.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WhyCannotConvertLambdaWithExtraLogic {
        /*
         * 🧪 Zadanie 18:
         * Zapisz lambdę Function<String, String> trimmedUpper = s ->
         * s.trim().toUpperCase() (DWA wywołania metod, nie jedno). W
         * komentarzu wyjaśnij, dlaczego TEJ lambdy NIE da się zapisać
         * jako pojedynczej method reference (wymagałoby dwóch kroków, a
         * "::" wskazuje dokładnie JEDNĄ metodę/konstruktor).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifyConvertibleVsNotInList {
        /*
         * 🧪 Zadanie 19:
         * Dane są TRZY lambdy: (1) s -> s.toLowerCase(), (2) s -> s + "!",
         * (3) s -> Integer.parseInt(s) * 2. Dla KAŻDEJ zapisz w
         * komentarzu, czy da się ją zamienić na method reference - jeśli
         * TAK, zapisz jaką (i zaimplementuj jako realny kod obok
         * oryginalnej lambdy do porównania); jeśli NIE, wyjaśnij dlaczego
         * (dodatkowa logika/argument/operacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_StaticTwoArgReferenceIntegerSum {
        /*
         * 🧪 Zadanie 20:
         * Utwórz java.util.function.BinaryOperator<Integer> DWOMA
         * sposobami - jako lambdę (a, b) -> a + b i jako referencję do
         * metody statycznej Integer::sum. Porównaj wyniki dla (100, 250).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ChainMapFilterWithMethodReferences {
        /*
         * 🧪 Zadanie 21:
         * Utwórz java.util.List<String> zawierającą kilka słów, w tym
         * puste i "białoznakowe" (np. "   "). Zbuduj potok
         * .stream().map(String::trim).filter(java.util.function.
         * Predicate.not(String::isBlank)).toList() - użyj
         * Predicate.not(...) do zanegowania referencji String::isBlank.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConstructorReferenceToInterfaceImplementation {
        /*
         * 🧪 Zadanie 22:
         * Zdefiniuj interfejs Shape z metodą double area(). Zdefiniuj
         * klasę Square implements Shape z polem double side i
         * konstruktorem Square(double side). Utwórz
         * java.util.function.Function<Double, Shape> jako referencję
         * Square::new (typ docelowy to interfejs Shape, nie konkretna
         * klasa!). Sprawdź area() dla side = 5.0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ComparatorThenComparingWithReferencesOnCustomClass {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj klasę Person z polami String lastName, String
         * firstName i getterami. Utwórz listę kilku osób (z powtarzającymi
         * się nazwiskami). Zbuduj Comparator<Person> przez
         * Comparator.comparing(Person::getLastName).
         *   thenComparing(Person::getFirstName) i posortuj listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OverloadedStaticMethodReferenceResolution {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj klasę Formatter z DWIEMA przeciążonymi metodami
         * statycznymi: String format(int x) i String format(double x).
         * Przypisz Formatter::format do java.util.function.
         * Function<Integer, String> ORAZ osobno do Function<Double,
         * String> - kompilator sam wybierze WŁAŚCIWE przeciążenie na
         * podstawie typu docelowego. Sprawdź obie i wyjaśnij w komentarzu
         * mechanizm doboru przeciążenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConstructorReferenceWithCheckedException {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj interfejs funkcyjny CheckedSupplier<T> z metodą T
         * get() throws Exception (jak CheckedFunction z Lesson08).
         * Zdefiniuj klasę RiskyResource, której konstruktor
         * RiskyResource(String name) throws java.io.IOException (rzuca
         * wyjątek, gdy name jest puste). Przypisz RiskyResource::new do
         * zmiennej CheckedSupplier<RiskyResource> (zwykły
         * java.util.function.Supplier by tego NIE przyjął - jego get() nie
         * deklaruje throws). Przetestuj z poprawną i pustą nazwą,
         * łapiąc wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BoundReferenceToThisInsideInstanceMethod {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj klasę Logger z polem String prefix i metodą
         * instancyjną void log(String message) (wypisuje prefix +
         * message). Dodaj metodę Runnable asDeferredLog(String message),
         * która zwraca this::log ZAWINIĘTE tak, by dawało się użyć jako
         * Runnable (podpowiedź: () -> this.log(message) albo Consumer
         * this::log użyte inaczej niż Runnable - dobierz odpowiedni
         * interfejs funkcyjny). Zademonstruj wywołanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CollectBoundReferencesFromLoopOverObjects {
        /*
         * 🧪 Zadanie 27:
         * Zdefiniuj klasę Task z polem String name i metodą run()
         * (wypisuje "Wykonuję: " + name). Utwórz java.util.List<Task> z
         * TRZEMA różnymi zadaniami. W pętli for-each zbierz DO
         * java.util.List<Runnable> referencje task::run dla KAŻDEGO
         * obiektu Task (bound reference - inny obiekt w każdej iteracji,
         * więc BRAK problemu z effectively final znanego z Lesson09).
         * Wykonaj wszystkie zebrane Runnable.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PrimitiveArrayConstructorReference {
        /*
         * 🧪 Zadanie 28:
         * Utwórz java.util.function.IntFunction<int[]> jako referencję do
         * konstruktora tablicy prymitywnej int[]::new. Wywołaj apply(5),
         * wypełnij tablicę kwadratami indeksów (0, 1, 4, 9, 16) i wypisz
         * ją przez java.util.Arrays.toString.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManualReduceWithBinaryOperatorReference {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tablicę int[] liczb (co najmniej 5 elementów). NIE
         * używając Stream.reduce, napisz WŁASNĄ pętlę akumulującą wynik,
         * wywołując w KAŻDEJ iteracji java.util.function.
         * BinaryOperator<Integer> jako referencję Integer::sum (zamiast
         * ręcznego "+"). Wypisz sumę końcową.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RepositoryPatternWithFactoryAndMapperReferences {
        /*
         * 🧪 Zadanie 30:
         * Zdefiniuj interfejsy funkcyjne Factory<T> { T create(); } oraz
         * Mapper<T, R> { R map(T t); }. Zdefiniuj klasę Product z polami
         * String name, double price i metodą String describe() (np. name
         * + " - " + price + " zł"). Zbuduj: Factory<Product> jako
         * referencję do konstruktora Product::new (z domyślnymi
         * wartościami przez konstruktor bezargumentowy LUB przez lambdę,
         * jeśli konstruktor wymaga argumentów - dobierz sam), oraz
         * Mapper<Product, String> jako referencję Product::describe
         * (unbound instance reference). Utwórz kilka produktów przez
         * Factory, zmapuj je Mapperem na opisy i wypisz. W komentarzu
         * podsumuj, KTÓRY z 4 rodzajów method reference reprezentuje
         * każde użycie w tym zadaniu.
         */
        public static void main(String[] args) { }
    }
}
