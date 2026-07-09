package com.example.javaquest._14_advancedjava.Lesson10_MethodReferences;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class _Lesson10_MethodReferences {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 WPROWADZENIE
         * ============================================================
         * W Lesson09 poznałeś dokładną składnię lambdy. Bardzo częsty
         * przypadek szczególny: CIAŁO lambdy to TYLKO wywołanie JEDNEJ,
         * JUŻ ISTNIEJĄCEJ metody, której argumenty to DOKŁADNIE parametry
         * lambdy, przekazane w TEJ SAMEJ kolejności, bez żadnej dodatkowej
         * logiki. Dla takiego przypadku Java oferuje method reference
         * (referencję do metody) - jeszcze krótszy zapis niż lambda,
         * operator "::".
         *
         * Method reference NIE jest nową funkcjonalnością - to czysty
         * "cukier składniowy" nad lambdą: kompilator i tak generuje
         * odpowiednik lambdy, tylko Ty nie musisz go pisać ręcznie. Są
         * DOKŁADNIE 4 rodzaje method reference - każdy z inną postacią po
         * lewej stronie "::".
         */

        /*
         * ============================================================
         * 🔹 RODZAJ 1: STATYCZNA METODA - ClassName::staticMethod
         * ============================================================
         * Wskazujesz metodę statyczną. Parametry lambdy trafiają WPROST
         * jako argumenty tej metody statycznej, w tej samej kolejności.
         */
        Function<String, Integer> parseLambda = s -> Integer.parseInt(s);
        Function<String, Integer> parseRef = Integer::parseInt; // równoważne

        System.out.println("parseLambda(\"123\") = " + parseLambda.apply("123"));
        System.out.println("parseRef(\"456\")    = " + parseRef.apply("456"));

        BinaryOperator<Integer> maxLambda = (a, b) -> Math.max(a, b);
        BinaryOperator<Integer> maxRef = Math::max; // równoważne

        System.out.println("maxLambda(7, 12) = " + maxLambda.apply(7, 12));
        System.out.println("maxRef(7, 12)    = " + maxRef.apply(7, 12));

        /*
         * ============================================================
         * 🔹 RODZAJ 2: ZWIĄZANA METODA INSTANCYJNA (bound)
         * particularObject::instanceMethod
         * ============================================================
         * Wskazujesz metodę KONKRETNEGO, już istniejącego obiektu
         * ("związanego" z referencją w momencie jej utworzenia - stąd
         * "bound"). Parametry lambdy trafiają jako argumenty TEJ metody,
         * wywoływanej NA TYM konkretnym obiekcie.
         */
        List<String> names = new ArrayList<>();

        Consumer<String> addLambda = s -> names.add(s);
        Consumer<String> addRef = names::add; // równoważne - "names" jest ZWIĄZANE już teraz

        addRef.accept("Ala");
        addLambda.accept("Ola");
        System.out.println("names po dodaniu przez oba: " + names);

        // Kolejny przykład bound reference - obiekt to lokalna zmienna String
        String greeting = "Cześć, Javo!";
        Supplier<String> upperLambda = () -> greeting.toUpperCase();
        Supplier<String> upperRef = greeting::toUpperCase; // równoważne

        System.out.println("upperLambda.get() = " + upperLambda.get());
        System.out.println("upperRef.get()    = " + upperRef.get());

        /*
         * ============================================================
         * 🔹 RODZAJ 3: NIEZWIĄZANA METODA INSTANCYJNA (unbound)
         * ClassName::instanceMethod
         * ============================================================
         * Wygląda PODOBNIE do rodzaju 1 (nazwa klasy przed "::"), ale
         * "instanceMethod" to metoda INSTANCYJNA, nie statyczna - a klasa
         * nie ma tu żadnego KONKRETNEGO obiektu. Zamiast tego PIERWSZY
         * parametr lambdy staje się OBIEKTEM, na którym metoda jest
         * wywoływana (czyli pełni rolę "this"), a POZOSTAŁE parametry to
         * argumenty tej metody. Stąd "niezwiązana" - obiekt, na którym
         * metoda zostanie wywołana, jest nieznany do momentu faktycznego
         * użycia (dopiero w chwili wywołania apply/test/itd. dostarczasz
         * "odbiorcę" jako pierwszy argument).
         */
        Function<String, String> upperCaseLambda = s -> s.toUpperCase();
        Function<String, String> upperCaseRef = String::toUpperCase; // równoważne

        System.out.println("upperCaseLambda(\"ala\") = " + upperCaseLambda.apply("ala"));
        System.out.println("upperCaseRef(\"ala\")    = " + upperCaseRef.apply("ala"));

        // Typowe użycie w strumieniu - pierwszy (i tu jedyny) parametr String
        // staje się odbiorcą wywołania toUpperCase()
        List<String> upperNames = names.stream()
                .map(String::toUpperCase) // zamiast .map(s -> s.toUpperCase())
                .toList();
        System.out.println("upperNames = " + upperNames);

        // Z DWOMA parametrami: pierwszy = odbiorca (obiekt, na którym
        // wywołujemy metodę), drugi = argument tej metody
        BiFunction<String, String, Boolean> startsWithLambda = (s, prefix) -> s.startsWith(prefix);
        BiFunction<String, String, Boolean> startsWithRef = String::startsWith; // równoważne

        System.out.println("startsWithLambda(\"Ala ma kota\", \"Ala\") = "
                + startsWithLambda.apply("Ala ma kota", "Ala"));
        System.out.println("startsWithRef(\"Ala ma kota\", \"Ala\")    = "
                + startsWithRef.apply("Ala ma kota", "Ala"));

        /*
         * ============================================================
         * 🔹 RODZAJ 4: REFERENCJA DO KONSTRUKTORA - ClassName::new
         * ============================================================
         * Parametry lambdy trafiają wprost do KONSTRUKTORA wskazanej
         * klasy - dokładnie ten sam mechanizm dopasowania, co dla metody
         * statycznej, tylko zamiast wywołania metody mamy "new".
         */
        Supplier<List<String>> listFactoryLambda = () -> new ArrayList<>();
        Supplier<List<String>> listFactoryRef = ArrayList::new; // równoważne

        List<String> freshList1 = listFactoryLambda.get();
        List<String> freshList2 = listFactoryRef.get();
        System.out.println("freshList1 == freshList2 ? " + (freshList1 == freshList2) + " (zawsze false - to DWA nowe obiekty)");

        Function<String, StringBuilder> sbFactoryLambda = s -> new StringBuilder(s);
        Function<String, StringBuilder> sbFactoryRef = StringBuilder::new; // równoważne

        System.out.println("sbFactoryLambda(\"abc\") = " + sbFactoryLambda.apply("abc"));
        System.out.println("sbFactoryRef(\"abc\")    = " + sbFactoryRef.apply("abc"));

        // Referencja do konstruktora TABLICY - szczególny przypadek,
        // przydatny np. jako "generator" tablicy o zadanym rozmiarze
        // w Stream.toArray(generator)
        IntFunction<String[]> arrayFactoryLambda = n -> new String[n];
        IntFunction<String[]> arrayFactoryRef = String[]::new; // równoważne

        System.out.println("arrayFactoryLambda(3).length = " + arrayFactoryLambda.apply(3).length);
        System.out.println("arrayFactoryRef(5).length    = " + arrayFactoryRef.apply(5).length);

        /*
         * ============================================================
         * 📌 KIEDY METHOD REFERENCE CZYTA SIĘ LEPIEJ NIŻ LAMBDA?
         * ============================================================
         * Zasada kciuka: użyj "::", gdy ciało lambdy sprowadza się
         * DOKŁADNIE do jednego z poniższych wzorców - BEZ żadnej
         * dodatkowej logiki, transformacji argumentów czy zmiany ich
         * kolejności:
         *
         *   x -> Klasa.metodaStatyczna(x, ...)      -> Klasa::metodaStatyczna
         *   x -> znanyObiekt.metoda(x, ...)         -> znanyObiekt::metoda
         *   (x, ...) -> x.metoda(...)               -> Klasa::metoda
         *   (args) -> new Klasa(args)               -> Klasa::new
         *
         * Zalety: krócej, mniej "szumu" składniowego (brak sztucznych
         * nazw parametrów), czytelnik od razu widzi, JAKA konkretna
         * metoda/konstruktor jest wywoływana - samo imię metody niesie
         * już pełną informację.
         *
         * Kiedy ZOSTAĆ przy lambdzie (method reference NIE pasuje):
         * - trzeba PRZETWORZYĆ argument przed wywołaniem, np.
         *   s -> s.trim().toUpperCase() (dwa wywołania, nie jedno),
         * - trzeba ZMIENIĆ kolejność albo LICZBĘ argumentów względem
         *   sygnatury docelowego interfejsu funkcyjnego,
         * - trzeba dodać WARUNEK, np. s -> s == null ? "" :
         *   s.toUpperCase(),
         * - potrzebna dodatkowa STAŁA wewnątrz, np. x -> x * factor,
         *   gdzie "factor" to zmienna spoza wywoływanej metody.
         *
         * Innymi słowy: method reference to skrót TYLKO dla "czystego
         * przekazania dalej" (delegacji) - w chwili, gdy pojawia się
         * JAKAKOLWIEK dodatkowa logika, lambda jest jedyną opcją.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Method reference (operator "::") to skrót dla lambdy, której
         *   ciało to WYŁĄCZNIE wywołanie jednej już istniejącej metody
         *   lub konstruktora - kompilator generuje ten sam kod, co dla
         *   równoważnej lambdy.
         * - 4 rodzaje: (1) ClassName::staticMethod - metoda statyczna,
         *   (2) particularObject::instanceMethod - metoda KONKRETNEGO
         *   obiektu (bound), (3) ClassName::instanceMethod - metoda
         *   dowolnego obiektu podanego jako PIERWSZY parametr (unbound),
         *   (4) ClassName::new - referencja do konstruktora (także
         *   konstruktora tablicy, Type[]::new).
         * - Wybieraj method reference, gdy lambda sprowadza się do
         *   "czystej delegacji" bez dodatkowej logiki - inaczej zostań
         *   przy lambdzie.
         *
         * W następnej lekcji (Lesson11) robimy pełny przegląd
         * java.util.function - wbudowanych interfejsów funkcyjnych
         * (Function, Predicate, Supplier, Consumer i ich warianty), na
         * których lambdy i method reference z tej i poprzedniej lekcji
         * były "zawieszane". To zamyka blok "Programowanie funkcyjne"
         * (Lesson08-11) - kolejny blok kursu to adnotacje (Lesson12+).
         */

        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }
}
