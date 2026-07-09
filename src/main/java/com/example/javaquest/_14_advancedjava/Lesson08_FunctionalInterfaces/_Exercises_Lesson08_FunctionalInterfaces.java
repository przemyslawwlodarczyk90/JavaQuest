package com.example.javaquest._14_advancedjava.Lesson08_FunctionalInterfaces;

public class _Exercises_Lesson08_FunctionalInterfaces {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CustomOperationInterface {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj interfejs funkcyjny IntOperation z metodą abstrakcyjną
         * int apply(int a, int b). Utwórz lambdę realizującą dodawanie i
         * wypisz wynik apply(7, 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GreeterInterface {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj interfejs funkcyjny Greeter z metodą String greet(String
         * name). Zaimplementuj lambdą zwracającą "Cześć, " + name + "!" i
         * wywołaj dla imienia "Ola".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TemperatureConverterInterface {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj interfejs funkcyjny TemperatureConverter z metodą double
         * convert(double celsius). Zaimplementuj lambdą przeliczającą
         * Celsjusz na Fahrenheita (C * 9/5 + 32) i sprawdź dla 100.0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InterfaceWithDefaultMethod {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj interfejs funkcyjny Doubler z JEDNĄ metodą abstrakcyjną
         * int doubleIt(int x) ORAZ metodą default int quadrupleIt(int x)
         * wywołującą doubleIt(doubleIt(x)). Zaimplementuj lambdą i sprawdź
         * quadrupleIt(3) (spodziewane 12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_StaticFactoryMethodOnInterface {
        /*
         * 🧪 Zadanie 5:
         * Do interfejsu Doubler z Zadania 4 dodaj metodę statyczną
         * Doubler identity() zwracającą lambdę x -> x. Wywołaj
         * Doubler.identity().doubleIt(9) i wypisz wynik (spodziewane 18,
         * bo doubleIt na tożsamości i tak wywołuje logikę implementacji -
         * przemyśl to w komentarzu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TriFunctionMultiplyThree {
        /*
         * 🧪 Zadanie 6:
         * Użyj interfejsu TriFunction<A,B,C,R> z lekcji, aby zaimplementować
         * mnożenie trzech liczb całkowitych. Sprawdź apply(2, 3, 4)
         * (spodziewane 24).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ValidatorInterface {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj interfejs funkcyjny Validator<T> z metodą boolean
         * isValid(T value). Zaimplementuj lambdą sprawdzającą, czy Integer
         * jest liczbą dodatnią. Sprawdź dla wartości -5, 0 i 8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CorrectFunctionalInterfaceCompiles {
        /*
         * 🧪 Zadanie 8:
         * Zdefiniuj poprawny interfejs @FunctionalInterface Counter z
         * JEDNĄ metodą int next(int current). Zaimplementuj lambdą
         * zwiększającą wartość o 1 i wywołaj 5 razy w pętli, wypisując
         * kolejne wartości startując od 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SecondAbstractMethodBreaksCompilation {
        /*
         * 🧪 Zadanie 9:
         * W KOMENTARZU (nie w realnym kodzie - to się NIE skompiluje)
         * zapisz interfejs @FunctionalInterface BrokenCalculator z DWIEMA
         * metodami abstrakcyjnymi: int add(int a, int b) oraz int
         * subtract(int a, int b). Zapisz w komentarzu dokładny komunikat
         * błędu kompilacji, jaki by się pojawił.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RedeclaredToStringStillSAM {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj interfejs @FunctionalInterface Labeled z JEDNĄ metodą
         * abstrakcyjną String label() ORAZ jawnie redeklarowaną abstrakcyjną
         * metodą String toString() (z Object). Zaimplementuj lambdą
         * (możliwe TYLKO dla label - toString() nie liczy się do SAM) i
         * wypisz w komentarzu wyjaśnienie, dlaczego to nadal jest 1 metoda
         * abstrakcyjna z punktu widzenia lambdy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_QuadFunctionInterface {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj własny interfejs funkcyjny QuadFunction<A,B,C,D,R> z
         * metodą R apply(A a, B b, C c, D d) (cztery argumenty - biblioteka
         * standardowa nie ma takiego). Zaimplementuj lambdą sumującą 4
         * liczby całkowite i sprawdź apply(1, 2, 3, 4) (spodziewane 10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ChainedDefaultMethodsCustomInterface {
        /*
         * 🧪 Zadanie 12:
         * Do interfejsu StringProcessor z lekcji użyj metody default
         * andThen() do zbudowania ŁAŃCUCHA TRZECH procesorów (trim ->
         * upper -> dodanie "!" na końcu). Zastosuj do "   ala ma kota   "
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CheckedSupplierParsingRisk {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj interfejs funkcyjny CheckedSupplier<T> z metodą
         * T get() throws Exception. Zaimplementuj lambdą parsującą
         * Integer.parseInt("not-a-number") - złap wyjątek w try-catch i
         * wypisz jego typ oraz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManualPipelineWithoutBuiltInCompose {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj interfejs IntTransformer z metodą int transform(int x).
         * Utwórz TRZY lambdy (dodaj 10, pomnóż razy 2, odejmij 5) i
         * ZŁÓŻ je RĘCZNIE (bez metody default andThen) w pętli for po
         * tablicy IntTransformer[]. Sprawdź wynik dla wejścia 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BoundedGenericFunctionalInterface {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj interfejs funkcyjny MaxFinder<T extends Comparable<T>>
         * z metodą T max(T a, T b). Zaimplementuj lambdą (użyj
         * compareTo) i sprawdź dla dwóch Integerów oraz dwóch Stringów
         * (osobne instancje MaxFinder dla każdego typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ArrayOfValidatorsAppliedInLoop {
        /*
         * 🧪 Zadanie 16:
         * Użyj interfejsu Validator<T> z Zadania 7. Utwórz tablicę
         * Validator<Integer>[] z TRZEMA różnymi walidacjami (dodatnia,
         * parzysta, większa niż 100). Dla liczby 48 wypisz wynik KAŻDEGO
         * walidatora z osobna (w pętli po tablicy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_StrategyPatternWithFieldFunctionalInterface {
        /*
         * 🧪 Zadanie 17:
         * Utwórz klasę PriceCalculator z polem prywatnym typu
         * TriFunction<Double, Double, Integer, Double> (cena bazowa, rabat
         * procentowy, ilość) ustawianym przez konstruktor. Zademonstruj
         * DWIE różne strategie liczenia ceny (np. z rabatem i bez) przez
         * podanie różnych lambd do konstruktora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CustomInterfaceExtendingRunnable {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj interfejs @FunctionalInterface NamedTask extends
         * Runnable z DODATKOWĄ metodą default String describe() (zwraca
         * stały opis). Zaimplementuj lambdą (implementuje TYLKO run() -
         * jedyną "prawdziwą" metodę abstrakcyjną odziedziczoną z Runnable)
         * i wywołaj obie metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FactoryMethodReturningDifferentImplementations {
        /*
         * 🧪 Zadanie 19:
         * Napisz statyczną metodę pomocniczą IntOperation operationFor(String
         * symbol) (z Zadania 1), zwracającą RÓŻNE lambdy w zależności od
         * symbolu ("+", "-", "*", "/") przez switch. Wywołaj dla wszystkich
         * czterech symboli z argumentami (10, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_StructurallyIdenticalInterfacesNotInterchangeable {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj DWA interfejsy funkcyjne o IDENTYCZNEJ sygnaturze
         * metody: InterfaceA { int calc(int x); } i InterfaceB { int
         * calc(int x); }. Przypisz TĘ SAMĄ lambdę do zmiennych obu typów
         * OSOBNO (działa), ale w komentarzu wyjaśnij, dlaczego NIE można
         * bezpośrednio przypisać zmiennej typu InterfaceA do zmiennej typu
         * InterfaceB bez rzutowania/nowej lambdy (typowanie nominalne, nie
         * strukturalne).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ChainOfResponsibilityWithNextDefault {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj interfejs @FunctionalInterface Handler<T> z metodą
         * boolean handle(T request) ORAZ metodą default Handler<T>
         * orElse(Handler<T> next) (jeśli handle() zwróci false, wywołaj
         * next.handle()). Zbuduj łańcuch 3 handlerów obsługujących różne
         * zakresy liczb i przetestuj dla kilku wartości wejściowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TriPredicateWithAndOrDefaults {
        /*
         * 🧪 Zadanie 22:
         * Zdefiniuj interfejs TriPredicate<A,B,C> z metodą boolean
         * test(A a, B b, C c) ORAZ własnymi metodami default and(...) i
         * or(...) (analogicznie do wbudowanego Predicate, poznamy w
         * Lesson11). Zbuduj dwa TriPredicate i połącz je przez and().
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RetrySafeWrapperForCheckedFunction {
        /*
         * 🧪 Zadanie 23:
         * Napisz statyczną metodę generyczną <T, R> Function-podobną
         * (własny interfejs SafeFunction<T,R> zwracający R lub null) safe(
         * CheckedFunction<T,R> risky), która OPAKOWUJE CheckedFunction z
         * lekcji - w razie wyjątku loguje komunikat i zwraca null zamiast
         * go propagować. Przetestuj na parsowaniu "123" i "xyz".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MiniEventListenerSystem {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj interfejs funkcyjny EventListener<T> z metodą void
         * onEvent(T event). Utwórz klasę EventBus z listą
         * List<EventListener<String>> i metodą publish(String event)
         * wywołującą WSZYSTKICH zarejestrowanych słuchaczy. Zarejestruj
         * DWIE lambdy-słuchaczy (np. logger i licznik zdarzeń) i opublikuj
         * 3 zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConfigurerDslPattern {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj interfejs funkcyjny Configurer<T> z metodą void
         * configure(T target). Utwórz prostą klasę ServerConfig (pola:
         * String host, int port, boolean sslEnabled - z setterami).
         * Napisz metodę statyczną ServerConfig build(Configurer<ServerConfig>
         * configurer) tworzącą instancję, wywołującą configurer.configure(...)
         * i zwracającą gotowy obiekt. Użyj lambdy do skonfigurowania hosta,
         * portu i SSL w jednym wywołaniu build(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_InheritedGenericAbstractMethodStillSAM {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj interfejs generyczny bazowy Source<T> { T get(); } (NIE
         * @FunctionalInterface, ale i tak ma jedną metodę abstrakcyjną).
         * Zdefiniuj @FunctionalInterface StringSource extends Source<String>
         * (bez dodawania nowych metod abstrakcyjnych). Zaimplementuj lambdą
         * i sprawdź, że kompiluje się i działa - w komentarzu wyjaśnij
         * dlaczego to nadal SAM mimo dziedziczenia z innego interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ComposeTriFunctionWithBiFunctionManually {
        /*
         * 🧪 Zadanie 27:
         * Użyj TriFunction<Integer,Integer,Integer,Integer> liczącej sumę
         * trzech liczb ORAZ zwykłego java.util.function.BiFunction<Integer,
         * Integer,Integer> liczącego iloczyn dwóch liczb. Ręcznie (bez
         * gotowej metody compose) podaj wynik TriFunction jako PIERWSZY
         * argument BiFunction (drugi - dowolna stała), wypisz wynik
         * końcowy dla wejść (1, 2, 3) i mnożnika 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ReplaceAnonymousClassWithLambdaComparison {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj interfejs Comparator2<T> { int compare2(T a, T b); }.
         * Zaimplementuj DWA warianty porównania Stringów wg długości - RAZ
         * jako klasa anonimowa, RAZ jako lambda. Wypisz obie wersje działania
         * na tych samych danych i skomentuj różnicę w liczbie linii kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CommandPatternWithUndoStack {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj interfejs funkcyjny Command { void execute(); }. Utwórz
         * prostą klasę TextBuffer (pole String content, metoda append(String)).
         * Zbuduj listę List<Command> reprezentującą 3 operacje append na tym
         * samym buforze (lambdy domykające się nad tym samym obiektem
         * TextBuffer) i wykonaj je po kolei, wypisując zawartość bufora po
         * KAŻDYM poleceniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RuleEngineDesignAndSamViolationFix {
        /*
         * 🧪 Zadanie 30:
         * NAJPIERW w komentarzu zaprojektuj interfejs Rule<T> z DWIEMA
         * metodami: boolean matches(T t) oraz String describe() - oznaczony
         * @FunctionalInterface. Zapisz w komentarzu, że to się NIE
         * skompiluje (złamana reguła SAM - dwie metody abstrakcyjne). Potem
         * NAPRAW projekt: rozbij na DWA osobne interfejsy - Rule<T> (tylko
         * matches) i Describable (tylko describe) - i zaimplementuj klasę
         * (NIE lambdę, bo dwie metody) realizującą OBA interfejsy dla
         * reguły "liczba parzysta większa niż 10". Przetestuj na kilku
         * liczbach.
         */
        public static void main(String[] args) { }
    }
}
