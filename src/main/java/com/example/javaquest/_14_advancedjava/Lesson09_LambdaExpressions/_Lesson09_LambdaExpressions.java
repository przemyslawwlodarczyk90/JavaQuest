package com.example.javaquest._14_advancedjava.Lesson09_LambdaExpressions;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

public class _Lesson09_LambdaExpressions {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 WPROWADZENIE
         * ============================================================
         * W Lesson08 poznałeś interfejsy funkcyjne - "cel" (target type),
         * do którego lambda musi pasować. Ale samą SKŁADNIĘ wyrażenia
         * lambda potraktowaliśmy tam po macoszemu (input -> input.trim()
         * i tyle). W tej lekcji bierzemy lupę i patrzymy WYŁĄCZNIE na
         * gramatykę lambdy - wszystkie warianty listy parametrów, dwa
         * rodzaje ciała, reguły domykania się nad zmiennymi (capture) i
         * subtelną różnicę między lambdą a klasą anonimową dotyczącą
         * słowa kluczowego "this".
         *
         * Interfejsy funkcyjne używane niżej pochodzą z java.util.function
         * (Function, BiFunction, Supplier, IntUnaryOperator) - to
         * biblioteka standardowa, którą szczegółowo zwiedzimy w Lesson11.
         * Tu służą tylko jako "haczyki", na których zawieszamy lambdy.
         */

        /*
         * ============================================================
         * 🔹 FORMY LISTY PARAMETRÓW
         * ============================================================
         * Lambda ma postać: (lista-parametrów) -> ciało
         *
         * 1) JEDEN parametr, BEZ jawnego typu -> nawiasy MOŻNA pominąć
         */
        Function<Integer, Integer> doubleIt = x -> x * 2;
        System.out.println("doubleIt(21) = " + doubleIt.apply(21));

        // 2) WIELE parametrów -> nawiasy są WYMAGANE, typy wnioskowane
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("add(7, 5) = " + add.apply(7, 5));

        // 3) ZERO parametrów -> puste nawiasy są WYMAGANE
        Supplier<Integer> answer = () -> 42;
        System.out.println("answer.get() = " + answer.get());

        // 4) Jawny typ parametru -> nawiasy są WYMAGANE, nawet dla JEDNEGO parametru
        IntUnaryOperator square = (int x) -> x * x;
        System.out.println("square(6) = " + square.applyAsInt(6));

        /*
         * 🔍 Reguła "wszystko albo nic" dla typów parametrów:
         * albo WSZYSTKIE parametry mają jawny typ, albo ŻADEN. Nie da się
         * mieszać - poniższy kod (CELOWO w komentarzu) NIE skompiluje się:
         *
         *   BiFunction<Integer, Integer, Integer> broken =
         *       (int a, b) -> a + b; // BŁĄD KOMPILACJI
         *
         *   Błąd: "cannot infer types for lambda parameters
         *          (actual and formal argument lists differ in length)"
         *          / dokładniej: "lambda parameters must all have
         *          inferred types or all have declared types"
         */

        /*
         * ============================================================
         * 🔹 CIAŁO LAMBDY: WYRAŻENIE vs BLOK
         * ============================================================
         * Ciało wyrażeniowe (expression body): pojedyncze wyrażenie, BEZ
         * nawiasów klamrowych, BEZ "return", BEZ średnika na końcu -
         * wartość wyrażenia STAJE SIĘ wartością zwracaną.
         */
        Function<Integer, String> classifyShort = x -> x % 2 == 0 ? "parzysta" : "nieparzysta";
        System.out.println("classifyShort(7) = " + classifyShort.apply(7));

        /*
         * Ciało blokowe (block body): nawiasy klamrowe {}, DOWOLNA liczba
         * instrukcji, JEŻELI metoda ma zwracać wartość - "return" jest
         * OBOWIĄZKOWY (kompilator NIE domyśli się go tak jak przy ciele
         * wyrażeniowym), każda instrukcja kończy się średnikiem jak w
         * zwykłej metodzie.
         */
        Function<Integer, String> classifyVerbose = x -> {
            String parity = (x % 2 == 0) ? "parzysta" : "nieparzysta";
            String sign = (x < 0) ? "ujemna" : "nieujemna";
            return parity + ", " + sign;
        };
        System.out.println("classifyVerbose(-4) = " + classifyVerbose.apply(-4));

        /*
         * ============================================================
         * 🔍 EFFECTIVELY FINAL - REGUŁY PRZECHWYTYWANIA ZMIENNYCH
         * ============================================================
         * Lambda może "domknąć się" (capture) nad lokalną zmienną z
         * otaczającego zasięgu, ale TYLKO jeśli ta zmienna jest final
         * albo "effectively final" - czyli PO INICJALIZACJI nigdy więcej
         * nie jest przypisywana ponownie. To NIE musi być jawnie
         * oznaczone słowem "final" - wystarczy, że kompilator widzi, że
         * i tak nigdy się nie zmienia.
         */
        int base = 10; // nigdy więcej nie przypisywana -> effectively final
        Runnable printBase = () -> System.out.println("base w lambdzie = " + base);
        printBase.run();

        /*
         * Gdyby PO utworzeniu lambdy (albo nawet przed, ale w sposób,
         * który zmienia wartość po jej zdefiniowaniu w kodzie źródłowym)
         * pojawiło się ponowne przypisanie do "base", np.:
         *
         *   int base = 10;
         *   Runnable printBase = () -> System.out.println(base);
         *   base = 20; // <-- TO przypisanie łamie "effectively final"
         *
         * kompilator zgłosiłby błąd DOKŁADNIE na linii wewnątrz lambdy,
         * gdzie zmienna jest używana:
         *
         *   error: local variables referenced from a lambda expression
         *          must be final or effectively final
         *
         * Powód: lambda może "przeżyć" metodę, w której powstała (np.
         * zostać zwrócona i wywołana znacznie później) - w tym czasie
         * lokalna zmienna metody dawno już nie istnieje na stosie. Java
         * radzi sobie z tym, KOPIUJĄC wartość zmiennej do lambdy w
         * momencie jej utworzenia. Gdyby zmienna mogła się zmieniać,
         * lambda i kod otaczający "widziałyby" różne wartości - reguła
         * effectively final eliminuje tę niespójność u źródła.
         */

        // Typowa pułapka: klasyczna pętla for z licznikiem NIE jest
        // effectively final (indeks zmienia się w każdej iteracji), więc
        // NIE MOŻNA go bezpośrednio użyć w lambdzie tworzonej w pętli:
        //
        //   for (int i = 0; i < 3; i++) {
        //       Runnable r = () -> System.out.println(i); // BŁĄD KOMPILACJI
        //   }
        //
        // Rozwiązanie - skopiować do NOWEJ zmiennej lokalnej wewnątrz pętli
        // (ta kopia JEST effectively final, bo powstaje na nowo w każdej
        // iteracji i nigdy się potem nie zmienia):
        for (int i = 0; i < 3; i++) {
            final int snapshot = i; // effectively final kopia
            Runnable r = () -> System.out.println("snapshot = " + snapshot);
            r.run();
        }

        // Pętla for-each NIE ma tego problemu - zmienna pętli jest
        // tworzona od nowa w każdej iteracji, więc jest effectively final
        // "za darmo":
        for (String s : java.util.List.of("Ala", "Ola", "Ela")) {
            Runnable r = () -> System.out.println("for-each: " + s);
            r.run();
        }

        // Obejście, gdy NAPRAWDĘ potrzebujesz mutowalnego stanu w lambdzie:
        // tablica jednoelementowa - REFERENCJA do tablicy jest effectively
        // final (nigdy nie przypisujemy nowej tablicy do "counter"), a
        // zawartość tablicy (counter[0]) już MOŻNA swobodnie zmieniać.
        int[] counter = {0};
        Runnable increment = () -> counter[0]++;
        increment.run();
        increment.run();
        increment.run();
        System.out.println("counter[0] po 3x increment = " + counter[0]);

        // Pola instancji (nie lokalne zmienne!) NIE podlegają regule
        // effectively final w ogóle - lambda trzyma referencję do CAŁEGO
        // obiektu (this), więc pole może się zmieniać dowolnie.
        MutableFieldHolder holder = new MutableFieldHolder();
        Runnable printField = () -> System.out.println("holder.value = " + holder.value);
        holder.value = 100;
        printField.run(); // wypisze 100, bo pole (nie lokalna zmienna) jest mutowalne

        /*
         * ============================================================
         * 🔍 ZNACZENIE "this" WEWNĄTRZ LAMBDY
         * ============================================================
         * To jedna z najważniejszych RÓŻNIC między lambdą a klasą
         * anonimową:
         *
         * - Lambda NIE tworzy WŁASNEGO "this" - jest LEKSYKALNIE
         *   domknięta, więc "this" wewnątrz lambdy oznacza DOKŁADNIE to
         *   samo, co "this" tuż PRZED lambdą (czyli instancję klasy
         *   OTACZAJĄCEJ).
         * - Klasa anonimowa TWORZY WŁASNĄ instancję z WŁASNYM "this" -
         *   wewnątrz jej metod "this" wskazuje na TĘ anonimową instancję,
         *   nie na obiekt zewnętrzny.
         *
         * Zobacz klasę Greeter (zdefiniowaną na końcu pliku) - metoda
         * asLambda() i asAnonymousClass() różnią się TYLKO tym jednym
         * aspektem.
         */
        Greeter greeter = new Greeter("Kapitan");
        System.out.println("--- lambda ---");
        greeter.asLambda().run();
        System.out.println("--- klasa anonimowa ---");
        greeter.asAnonymousClass().run();

        /*
         * ============================================================
         * 📌 LAMBDA vs KLASA ANONIMOWA - PORÓWNANIE
         * ============================================================
         *                          LAMBDA              KLASA ANONIMOWA
         * "this" wewnątrz          instancja           WŁASNA instancja
         *                          OTACZAJĄCA          (Greeter$1 itp.)
         * Liczba metod             dokładnie 1          dowolna liczba
         *                          (SAM interfejsu)      (implementuje
         *                                                interfejs/klasę
         *                                                abstrakcyjną w
         *                                                pełni)
         * Własne pola/stan         NIE (brak własnej     TAK (może mieć
         *                          instancji)             pola instancji)
         * Typ docelowy             MUSI być interfejsem   interfejs LUB
         *                          funkcyjnym             klasa abstrakcyjna
         *                                                 LUB zwykła klasa
         * Kompilacja                bez osobnego .class   osobny plik .class
         *                          na lambdę (invoke-      (Outer$1.class)
         *                          dynamic - lambda
         *                          "materializuje się"
         *                          dopiero w runtime)
         * Zwięzłość                bardzo zwięzła         rozwlekła
         * effectively final        WYMAGANE dla           WYMAGANE dla
         * przy przechwytywaniu     zmiennych lokalnych     zmiennych lokalnych
         *                          (ta sama reguła po obu stronach!)
         *
         * Wniosek: lambda to NIE jest "cukier składniowy" nad klasą
         * anonimową w sensie 1:1 - różnią się semantyką "this" i sposobem
         * kompilacji. Lambdy wybieramy, gdy target to interfejs funkcyjny
         * i nie potrzebujemy własnego stanu/wielu metod; klasę anonimową
         * (albo zwykłą klasę), gdy potrzeba więcej niż jednej metody,
         * własnych pól, albo dziedziczenia po klasie abstrakcyjnej.
         */

        System.out.println("\n=== KONIEC LEKCJI 09 ===");
    }
}

/**
 * Pomocnicza klasa z mutowalnym polem instancji - demonstruje, że pola
 * (w przeciwieństwie do lokalnych zmiennych) NIE podlegają regule
 * effectively final przy przechwytywaniu przez lambdę.
 */
class MutableFieldHolder {
    int value = 1;
}

/**
 * Demonstruje różnicę w znaczeniu "this" między lambdą a klasą anonimową -
 * obie metody zwracają Runnable realizujący (z pozoru) to samo zadanie.
 */
class Greeter {
    private final String name;

    Greeter(String name) {
        this.name = name;
    }

    Runnable asLambda() {
        // "this" tutaj to "this" metody asLambda(), czyli instancja Greeter
        return () -> System.out.println(
                "this.getClass() = " + this.getClass().getSimpleName()
                        + ", name = " + this.name);
    }

    Runnable asAnonymousClass() {
        return new Runnable() {
            @Override
            public void run() {
                // "this" tutaj to WŁASNA instancja tej anonimowej klasy
                // (Greeter$1), NIE instancja Greeter! Dlatego "name" trzeba
                // pobrać przez Greeter.this.name, a nie przez this.name -
                // this.name szukałoby pola "name" w samej klasie anonimowej.
                System.out.println(
                        "this.getClass() = " + this.getClass().getSimpleName()
                                + ", name = " + Greeter.this.name);
            }
        };
    }
}
