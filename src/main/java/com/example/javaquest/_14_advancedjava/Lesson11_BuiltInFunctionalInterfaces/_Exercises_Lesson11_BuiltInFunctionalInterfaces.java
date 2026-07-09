package com.example.javaquest._14_advancedjava.Lesson11_BuiltInFunctionalInterfaces;

public class _Exercises_Lesson11_BuiltInFunctionalInterfaces {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FunctionSquare {
        /*
         * 🧪 Zadanie 1:
         * Utwórz java.util.function.Function<Integer, Integer> o nazwie
         * square, zwracającą kwadrat argumentu. Sprawdź dla 12
         * (spodziewane 144).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BiFunctionRectangleArea {
        /*
         * 🧪 Zadanie 2:
         * Utwórz java.util.function.BiFunction<Double, Double, Double> o
         * nazwie rectangleArea, obliczającą pole prostokąta (szerokość *
         * wysokość). Sprawdź dla (4.5, 2.0) (spodziewane 9.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PredicateIsEven {
        /*
         * 🧪 Zadanie 3:
         * Utwórz java.util.function.Predicate<Integer> o nazwie isEven.
         * Sprawdź dla liczb 4, 7, 0, -6 i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BiPredicateEqualsIgnoreCase {
        /*
         * 🧪 Zadanie 4:
         * Utwórz java.util.function.BiPredicate<String, String> o nazwie
         * sameIgnoringCase, sprawdzającą równość dwóch Stringów bez
         * względu na wielkość liter. Sprawdź dla ("Java", "JAVA") i
         * ("Java", "Python").
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SupplierFixedMessage {
        /*
         * 🧪 Zadanie 5:
         * Utwórz java.util.function.Supplier<String> o nazwie
         * welcomeMessage, zwracającą stały napis powitalny. Wywołaj get()
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConsumerFormattedPrint {
        /*
         * 🧪 Zadanie 6:
         * Utwórz java.util.function.Consumer<String> o nazwie
         * printBracketed, wypisującą argument w nawiasach kwadratowych
         * (np. "[tekst]"). Wywołaj accept("witaj") i accept("świecie").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BiConsumerRepeatPrint {
        /*
         * 🧪 Zadanie 7:
         * Utwórz java.util.function.BiConsumer<String, Integer> o nazwie
         * printNTimes, wypisującą podany napis N razy (każdy w osobnej
         * linii). Wywołaj accept("Java", 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UnaryOperatorReverseString {
        /*
         * 🧪 Zadanie 8:
         * Utwórz java.util.function.UnaryOperator<String> o nazwie
         * reverse, odwracającą kolejność znaków (np. przez new
         * StringBuilder(s).reverse().toString()). Sprawdź dla "Ala".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BinaryOperatorMax {
        /*
         * 🧪 Zadanie 9:
         * Utwórz java.util.function.BinaryOperator<Integer> o nazwie max,
         * zwracającą większą z dwóch liczb (Math.max). Sprawdź dla (17,
         * 42) i (100, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FunctionVsUnaryOperatorComparison {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj DWUKROTNIE tę samą operację podwajania Integer -
         * raz jako java.util.function.Function<Integer, Integer>, raz
         * jako java.util.function.UnaryOperator<Integer>. Sprawdź, że obie
         * dają ten sam wynik dla 21, i zapisz w komentarzu, kiedy warto
         * wybrać UnaryOperator zamiast Function.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AndThenChainOfThreeFunctions {
        /*
         * 🧪 Zadanie 11:
         * Utwórz TRZY Function<String, String>: trim (String::trim),
         * upper (String::toUpperCase) i exclaim (s -> s + "!"). Złóż je w
         * JEDEN łańcuch metodą andThen (trim.andThen(upper).andThen(exclaim))
         * i zastosuj do "   ala ma kota   ".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ComposeVsAndThenDifferentOrder {
        /*
         * 🧪 Zadanie 12:
         * Utwórz Function<Integer, Integer> addFive (x -> x + 5) i
         * multiplyByTwo (x -> x * 2). Oblicz WYNIK DWÓCH RÓŻNYCH złożeń
         * dla wejścia 3: addFive.andThen(multiplyByTwo) ORAZ
         * addFive.compose(multiplyByTwo). Wypisz oba wyniki i wyjaśnij w
         * komentarzu, dlaczego się różnią.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PredicateAndOrNegateChain {
        /*
         * 🧪 Zadanie 13:
         * Utwórz java.util.List<Integer> z liczbami od -5 do 10. Zbuduj
         * Predicate<Integer> isPositive i isEven, złóż je w
         * isPositive.and(isEven), isPositive.or(isEven) i isEven.negate().
         * Dla KAŻDEGO z trzech predykatów przefiltruj listę (ręczną pętlą
         * lub stream().filter()) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FunctionIdentityInManualMap {
        /*
         * 🧪 Zadanie 14:
         * Utwórz java.util.List<String> słów. Zbuduj java.util.Map<String,
         * String> ręczną pętlą, gdzie kluczem i wartością jest TO SAMO
         * słowo - użyj Function.identity() jako "funkcji wartości"
         * (map.put(word, Function.identity().apply(word))), żeby pokazać
         * jej typowe zastosowanie (np. jako argument w
         * Collectors.toMap). Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BiFunctionAndThen {
        /*
         * 🧪 Zadanie 15:
         * Utwórz BiFunction<Integer, Integer, Integer> sum (a, b -> a + b)
         * i Function<Integer, String> describe (x -> "wynik = " + x).
         * Złóż je metodą sum.andThen(describe) (BiFunction też ma
         * andThen! - przyjmuje Function stosowaną do WYNIKU). Sprawdź dla
         * (10, 15).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PrimitiveIntPipeline {
        /*
         * 🧪 Zadanie 16:
         * Utwórz java.util.function.IntPredicate isPositive, IntUnaryOperator
         * square oraz IntBinaryOperator add. Zbuduj mini-pipeline: dla
         * dwóch liczb wejściowych oblicz ich sumę (add), sprawdź czy jest
         * dodatnia (isPositive), a jeśli tak - wypisz jej kwadrat (square).
         * Sprawdź dla (-10, 3) i (7, 8).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ToIntFunctionManualSortKey {
        /*
         * 🧪 Zadanie 17:
         * Utwórz java.util.function.ToIntFunction<String> o nazwie
         * lengthKey (String::length). Utwórz java.util.List<String> słów o
         * różnej długości i posortuj ją RĘCZNIE (prostym sortowaniem
         * bąbelkowym LUB przez Collections.sort z Comparatorem
         * zbudowanym ręcznie z lengthKey.applyAsInt(...) w porównaniu -
         * NIE używaj Comparator.comparingInt). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_IntFunctionGenerateLabels {
        /*
         * 🧪 Zadanie 18:
         * Utwórz java.util.function.IntFunction<String> o nazwie
         * labelFor, zwracającą "Element-" + indeks. W pętli od 0 do 4
         * wywołaj apply(i) i wypisz wygenerowane etykiety.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PredicateChainManualFilterLoop {
        /*
         * 🧪 Zadanie 19:
         * Utwórz java.util.List<String> nazw (mieszanka krótkich i
         * długich, część zaczynających się wielką literą). Zbuduj
         * Predicate<String> isLongEnough (length >= 4) i
         * startsWithUpperCase, złóż je przez and(). NIE używając
         * .stream() - napisz RĘCZNĄ pętlę for-each z if (predicate.test(s))
         * budującą nową listę wyników. Wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_UnaryOperatorAssignableToFunctionVariable {
        /*
         * 🧪 Zadanie 20:
         * Utwórz UnaryOperator<Integer> increment (x -> x + 1). Przypisz
         * TĘ SAMĄ referencję do zmiennej typu Function<Integer, Integer>
         * (dozwolone, bo UnaryOperator<T> extends Function<T,T>). Wywołaj
         * apply(5) przez obie zmienne i w komentarzu wyjaśnij tę relację
         * dziedziczenia.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ValidationPipelineWithMultiplePredicates {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj Predicate<String> walidujący nazwę użytkownika: notNull
         * (s != null), notBlank (!s.isBlank()), maxLength20 (s.length() <=
         * 20) - złóż WSZYSTKIE trzy przez and() w jeden
         * Predicate<String> isValidUsername (uważaj na kolejność, żeby
         * notNull było PIERWSZE - and() jest leniwe i nie wywoła kolejnych
         * testów, jeśli poprzedni zwróci false, co chroni przed
         * NullPointerException). Przetestuj na null, "", "ab", poprawnej
         * nazwie i za długim ciągu znaków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ThreeStepFunctionPipelineWithFallback {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj Function<String, Integer> parse, próbujące
         * Integer.parseInt (w razie NumberFormatException zwracające 0 -
         * obsłuż wyjątek WEWNĄTRZ lambdy z ciałem blokowym). Złóż je z
         * Function<Integer, Integer> validate (zamienia liczby ujemne na
         * 0, metodą Math.max(0, x)) i Function<Integer, String> format
         * ("Wynik: " + x) przez andThen (parse.andThen(validate).
         * andThen(format)). Sprawdź dla "42", "-7" i "abc".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConsumerAndThenChain {
        /*
         * 🧪 Zadanie 23:
         * Utwórz java.util.List<String> logStorage (pusta lista). Zbuduj
         * Consumer<String> logToConsole (System.out::println) i
         * Consumer<String> logToList (logStorage::add). Złóż je przez
         * logToConsole.andThen(logToList) (Consumer TEŻ ma andThen -
         * wykonuje OBA efekty uboczne po kolei, na TYM SAMYM argumencie).
         * Wywołaj accept() dla trzech różnych komunikatów i na końcu
         * wypisz zawartość logStorage.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BiPredicateChainForCredentialsValidation {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj BiPredicate<String, String> usernameNotEmpty ((u, p) ->
         * !u.isBlank()) i passwordLongEnough ((u, p) -> p.length() >= 8).
         * Złóż je przez and() w jeden BiPredicate<String, String>
         * isValidLogin. Przetestuj dla trzech par (login, hasło) - jednej
         * poprawnej i dwóch niepoprawnych (osobno łamiących każdy z
         * warunków).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BinaryOperatorMaxByMinByStaticFactories {
        /*
         * 🧪 Zadanie 25:
         * Zapoznaj się z metodami statycznymi BinaryOperator.maxBy(Comparator)
         * i BinaryOperator.minBy(Comparator). Utwórz
         * BinaryOperator<Integer> maxOp = BinaryOperator.maxBy(Integer::compareTo)
         * (użyj referencji do metody z Lesson10!). Napisz RĘCZNĄ pętlę po
         * int[] tablicy liczb, akumulując wynik przez maxOp.apply(...) w
         * KAŻDEJ iteracji (bez Stream.reduce). Wypisz maksimum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WeightedScoreWithToIntAndToDoubleFunction {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj prostą klasę Student z polami String name, int
         * examScore, double attendanceRate. Utwórz java.util.function.
         * ToIntFunction<Student> examKey (Student::getExamScore) i
         * java.util.function.ToDoubleFunction<Student> attendanceKey
         * (Student::getAttendanceRate). Napisz metodę obliczającą "wynik
         * ważony" = examKey.applyAsInt(s) * 0.7 + attendanceKey.
         * applyAsDouble(s) * 30 dla listy kilku studentów, wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MemoizingSupplierWrapper {
        /*
         * 🧪 Zadanie 27:
         * Napisz klasę generyczną CachedSupplier<T> implementującą
         * java.util.function.Supplier<T>, opakowującą inny Supplier<T> -
         * PIERWSZE wywołanie get() faktycznie woła opakowany supplier i
         * ZAPAMIĘTUJE wynik w polu instancji, KOLEJNE wywołania zwracają
         * zapamiętaną wartość BEZ ponownego liczenia (wypisz w środku
         * komunikat "liczę..." tylko przy pierwszym wywołaniu, żeby to
         * udowodnić). Przetestuj wywołując get() trzykrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PredicateNotWithPrimitiveIntPredicateNegate {
        /*
         * 🧪 Zadanie 28:
         * Utwórz java.util.function.Predicate<String> isBlank
         * (String::isBlank) i zbuduj jego negację DWOMA sposobami -
         * przez isBlank.negate() ORAZ przez java.util.function.
         * Predicate.not(isBlank) - porównaj wyniki dla "  " i "tekst".
         * Osobno utwórz IntPredicate isNegative (x -> x < 0) i jego
         * negację przez isNegative.negate() - sprawdź dla -3 i 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManualStreamLikePipelineWithoutStreams {
        /*
         * 🧪 Zadanie 29:
         * Utwórz java.util.List<String> surowych danych (mieszanka
         * poprawnych słów i pustych/białoznakowych wpisów). Zbuduj OSOBNO
         * zmienne: Predicate<String> filterCondition (niepuste po trim),
         * Function<String, String> mapFunction (trim + toUpperCase),
         * Consumer<String> outputAction (System.out::println). Napisz
         * RĘCZNĄ pętlę for-each realizującą DOKŁADNIE to, co zrobiłby
         * .stream().filter(...).map(...).forEach(...) - czyli filtruj
         * przez filterCondition.test(...), przekształcaj przez
         * mapFunction.apply(...), wykonuj przez outputAction.accept(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RuleEngineCombiningAllFamilies {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini "silnik reguł" dla liczb całkowitych, łączący
         * WSZYSTKIE cztery rodziny interfejsów: (1) Predicate<Integer>
         * condition, złożony z kilku prostszych predykatów przez
         * and()/or()/negate() (np. "dodatnia i podzielna przez 3, albo
         * dokładnie 0"), (2) BiFunction<Integer, String, String> action,
         * formatujący komunikat na podstawie liczby i etykiety reguły,
         * (3) Supplier<String> fallbackMessage, zwracany, gdy warunek
         * condition NIE jest spełniony. Napisz metodę statyczną String
         * evaluate(int value, Predicate<Integer> condition,
         * BiFunction<Integer, String, String> action, Supplier<String>
         * fallback, String label), realizującą logikę if/else, i
         * przetestuj dla co najmniej 4 różnych liczb.
         */
        public static void main(String[] args) { }
    }
}
