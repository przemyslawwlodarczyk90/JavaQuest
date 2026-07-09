package com.example.javaquest._14_advancedjava.Lesson30_CapstoneAdvancedJava;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

/**
 * LEKCJA 30: KAPSTON ROZDZIALU _14_advancedjava - MINI SYSTEM WYRAZEN ARYTMETYCZNYCH.
 *
 * Ostatnia lekcja calego rozdzialu laczy w JEDNYM, spojnym programie kilka
 * technik poznanych po drodze:
 * - sealed interface + rekordy jako drzewo wyrazen (Lekcje 19, 21),
 * - switch z rekordowymi wzorcami do ewaluacji drzewa (Lekcja 21),
 * - generyczny `Result<T>` (Success/Failure) do zwracania wartosc-albo-blad (Lekcje 1-7),
 * - interfejsy funkcyjne z java.util.function jako "wpinane" zachowanie (Lekcja 11),
 * - wlasna adnotacja `@Experimental` + odrobina refleksji, ktora ja wykrywa (Lekcje 12-15),
 * - niezmiennosc i obronna kopia w srodowisku zmiennych (Lekcje 24-25).
 */
public class _Lesson30_CapstoneAdvancedJava {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 30: KAPSTON ROZDZIALU _14_advancedjava - MINI SYSTEM WYRAZEN ===");

        /*
         * ============================================================
         * 📦 1. DRZEWO WYRAZENIA: SEALED + REKORDY (Lekcje 19, 21)
         * ============================================================
         * (2 * x) + sqrt(y) - zamodelowane jako drzewo NIEZMIENNYCH
         * rekordow implementujacych zamkniety interfejs Expr.
         */
        Expr expression = new Add(
                new Multiply(new Literal(2), new Variable("x")),
                new Call("sqrt", new Variable("y")));
        System.out.println("Wyrazenie: (2 * x) + sqrt(y)");

        /*
         * ============================================================
         * 📦 2. SRODOWISKO ZMIENNYCH: NIEZMIENNOSC + OBRONNA KOPIA (Lekcje 24-25)
         * ============================================================
         */
        Map<String, Double> bindings = new HashMap<>();
        bindings.put("x", 3.0);
        bindings.put("y", 16.0);
        Environment env = new Environment(bindings);

        // Zmieniamy ORYGINALNA mape PO utworzeniu Environment - dzieki obronnej
        // kopii w konstruktorze, Environment tego NIE widzi.
        bindings.put("x", 999.0);
        System.out.println("Environment.x po zmianie oryginalnej mapy zewnetrznej: " + env.get("x")
                + " (nie 999.0 - obronna kopia zadzialala)");

        /*
         * ============================================================
         * 🔹 3. REJESTR FUNKCJI: PLUGGABLE BEHAVIOR + ADNOTACJA + REFLEKSJA
         * (Lekcja 11: java.util.function, Lekcje 12-15: adnotacje + refleksja)
         * ============================================================
         * Funkcje matematyczne sa "wpinane" jako DoubleUnaryOperator -
         * albo referencja do metody (Math::sqrt), albo lambda, albo (jak
         * ponizej) osobna, NAZWANA klasa oznaczona @Experimental - co
         * pozwala rejestrowi WYKRYC ja refleksja przy rejestracji.
         */
        FunctionRegistry functions = new FunctionRegistry();
        functions.register("sqrt", Math::sqrt);
        functions.register("fastInvSqrt", new FastInverseSqrt());

        /*
         * ============================================================
         * 🔍 4. EWALUACJA: GENERYCZNY Result<T> + SWITCH Z REKORDOWYMI WZORCAMI
         * (Lekcje 1-7: generics, Lekcje 19-22: sealed/pattern matching/switch)
         * ============================================================
         */
        Result<Double> result = evaluate(expression, env, functions);
        switch (result) {
            case Result.Success<Double> success -> System.out.println("Wynik: " + success.value());
            case Result.Failure<Double> failure -> System.out.println("Blad: " + failure.message());
        }

        // Przypadek bledu nr 1 - nieznana zmienna. Result.Failure zamiast wyjatku.
        Expr unknownVariable = new Variable("nieistniejaca");
        System.out.println("Proba uzycia nieznanej zmiennej: " + evaluate(unknownVariable, env, functions));

        // Przypadek bledu nr 2 - nieznana funkcja.
        Expr unknownFunction = new Call("cosinus", new Literal(1));
        System.out.println("Proba wywolania nieznanej funkcji: " + evaluate(unknownFunction, env, functions));

        // Zlozone wyrazenie z Negate i zagniezdzonymi wywolaniami.
        Expr negated = new Negate(new Add(new Literal(10), new Variable("x")));
        System.out.println("Wynik -(10 + x) [x przed zmiana=3.0]: " + evaluate(negated, env, functions));

        /*
         * ============================================================
         * 🔮 5. JAK TO ROZWINAC: SYSTEM WTYCZEK PRZEZ SPI (Lekcje 26-28)
         * ============================================================
         * FunctionRegistry rejestruje funkcje RECZNIE w main() - ale to
         * dokladnie to miejsce, gdzie wpiac ServiceLoader (Lekcja 26).
         * Wystarczyloby zdefiniowac interfejs dostawcy, np.:
         *
         *     public interface NamedFunctionProvider {
         *         String name();
         *         DoubleUnaryOperator function();
         *     }
         *
         * a implementacje (nowe funkcje matematyczne, np. "log", "cbrt")
         * dostarczac jako OSOBNE moduly/JAR-y z plikiem
         * `META-INF/services/...NamedFunctionProvider` (Lekcja 26) albo
         * jako `provides ... with ...` w module-info.java (Lekcje 27-28).
         * W main() zamiast recznych wywolan `functions.register(...)`
         * byloby wtedy jedno: `ServiceLoader.load(NamedFunctionProvider.class)
         * .forEach(p -> functions.register(p.name(), p.function()))` -
         * a DODANIE nowej funkcji do systemu wymagaloby TYLKO dorzucenia
         * nowego JAR-a na classpath/modulepath, bez zmiany ANI jednej
         * linii w tym pliku. To sedno rozdzielenia "co system potrafi" od
         * "co jest w nim aktualnie zainstalowane".
         */
        System.out.println("\n=== ROZSZERZENIE: system wtyczek przez SPI (Lekcje 26-28) - patrz komentarz w kodzie ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU _14_advancedjava
         * ============================================================
         * Rozdzial _14_advancedjava (30 lekcji) przeszedl przez piec
         * duzych klastrow tematycznych:
         *
         * 1. GENERICS (Lekcje 1-7) - bezpieczenstwo typow w compile-time
         *    zamiast raw types/Object; wariancja i wildcardy (PECS).
         * 2. PROGRAMOWANIE FUNKCYJNE (Lekcje 8-11) - lambdy, referencje do
         *    metod, wbudowane interfejsy funkcyjne jako "wpinane" zachowanie.
         * 3. ADNOTACJE, REFLEKSJA I MECHANIZMY DYNAMICZNE (Lekcje 12-18) -
         *    wlasne adnotacje, introspekcja w runtime, dynamiczne proxy,
         *    method handles jako szybsza alternatywa dla refleksji.
         * 4. NOWOCZESNY SYSTEM TYPOW (Lekcje 19-22) - sealed classes,
         *    pattern matching (instanceof i switch), rekordowe wzorce,
         *    switch expressions - domykanie hierarchii i exhaustiveness.
         * 5. WNIOSKOWANIE TYPOW, NIEZMIENNOSC I MODULARNOSC (Lekcje 23-28) -
         *    var, niezmiennosc i obronne kopiowanie jako domyslny wybor
         *    projektowy, ServiceLoader/SPI i JPMS jako fundament
         *    rozszerzalnych, modularnych systemow.
         *
         * Ten kapston (Lekcja 30) pokazal, ze te piec klastrow NIE sa
         * odizolowanymi ciekawostkami jezykowymi - naturalnie sklada sie
         * je w JEDEN, spojny program: generyczny Result<T> opakowuje
         * bledy, sealed+rekordy modeluja domene, funkcyjne interfejsy
         * wpinaja zachowanie, adnotacja+refleksja oznaczaja i wykrywaja
         * eksperymentalne fragmenty, a niezmiennosc chroni stan przed
         * przypadkowa mutacja.
         *
         * _14_advancedjava to (stan na 2026-07-09) NAJNOWSZY rozdzial
         * calego kursu javaQuest - i tym samym zamykamy go tutaj.
         */

        System.out.println("\n=== KONIEC LEKCJI 30 - KONIEC ROZDZIALU _14_advancedjava ===");
    }

    // ============================================================
    // 🔍 EWALUACJA WYRAZENIA - SWITCH Z REKORDOWYMI WZORCAMI (Lekcja 21)
    // ============================================================

    private static Result<Double> evaluate(Expr expr, Environment env, FunctionRegistry functions) {
        return switch (expr) {
            case Literal(double value) -> new Result.Success<>(value);

            case Variable(String name) -> env.has(name)
                    ? new Result.Success<>(env.get(name))
                    : new Result.Failure<>("Nieznana zmienna: " + name);

            case Add(Expr left, Expr right) ->
                    combine(evaluate(left, env, functions), evaluate(right, env, functions), Double::sum);

            case Multiply(Expr left, Expr right) ->
                    combine(evaluate(left, env, functions), evaluate(right, env, functions), (a, b) -> a * b);

            case Negate(Expr inner) -> evaluate(inner, env, functions).map(value -> -value);

            case Call(String functionName, Expr argument) -> evaluate(argument, env, functions)
                    .flatMap(value -> functions.apply(functionName, value));
        };
        // Uwaga: switch jest EXHAUSTYWNY bez 'default' - bo Expr jest sealed
        // (permits Literal, Variable, Add, Multiply, Negate, Call) i kompilator
        // wie, ze to WSZYSTKIE mozliwe warianty (Lekcja 19-22).
    }

    private static Result<Double> combine(Result<Double> left, Result<Double> right, DoubleBinaryOperator operator) {
        return left.flatMap(l -> right.map(r -> operator.applyAsDouble(l, r)));
    }
}

// ==================== DRZEWO WYRAZEN: SEALED + REKORDY (Lekcje 19, 21) ====================

sealed interface Expr permits Literal, Variable, Add, Multiply, Negate, Call {
}

record Literal(double value) implements Expr {
}

record Variable(String name) implements Expr {
}

record Add(Expr left, Expr right) implements Expr {
}

record Multiply(Expr left, Expr right) implements Expr {
}

record Negate(Expr expr) implements Expr {
}

record Call(String functionName, Expr argument) implements Expr {
}

// ==================== GENERYCZNY Result<T>: WARTOSC-ALBO-BLAD (Lekcje 1-7, 19-22) ====================

sealed interface Result<T> permits Result.Success, Result.Failure {

    record Success<T>(T value) implements Result<T> {
    }

    record Failure<T>(String message) implements Result<T> {
    }

    default <R> Result<R> map(Function<T, R> mapper) {
        return switch (this) {
            case Success(var value) -> new Success<>(mapper.apply(value));
            case Failure(var message) -> new Failure<>(message);
        };
    }

    default <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
        return switch (this) {
            case Success(var value) -> mapper.apply(value);
            case Failure(var message) -> new Failure<>(message);
        };
    }
}

// ==================== SRODOWISKO ZMIENNYCH: NIEZMIENNOSC + OBRONNA KOPIA (Lekcje 24-25) ====================

final class Environment {

    private final Map<String, Double> variables;

    Environment(Map<String, Double> variables) {
        // Map.copyOf robi ObRONNA kopie ORAZ zwraca NIEMODYFIKOWALNA mape -
        // podwojne zabezpieczenie: ani zewnetrzna mutacja zrodla, ani wewnetrzna
        // proba zmiany po fakcie nie zepsuja stanu tego obiektu.
        this.variables = Map.copyOf(variables);
    }

    boolean has(String name) {
        return variables.containsKey(name);
    }

    double get(String name) {
        return variables.get(name);
    }
}

// ==================== ADNOTACJA @Experimental (Lekcje 12-14) ====================

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Experimental {
    String reason() default "brak dodatkowego opisu";
}

// ==================== REJESTR FUNKCJI: PLUGGABLE BEHAVIOR + REFLEKSJA (Lekcje 11, 15) ====================

final class FunctionRegistry {

    private final Map<String, DoubleUnaryOperator> functions = new HashMap<>();

    void register(String name, DoubleUnaryOperator function) {
        // Odrobina refleksji (Lekcja 15): sprawdzamy, czy KONKRETNA klasa
        // implementujaca funkcje jest oznaczona @Experimental. Zwykle
        // referencje do metod/lambdy (jak Math::sqrt) generuja syntetyczne
        // klasy bez adnotacji - wykryje to TYLKO nazwana klasa, taka jak
        // FastInverseSqrt ponizej.
        Experimental experimental = function.getClass().getAnnotation(Experimental.class);
        if (experimental != null) {
            System.out.println("UWAGA (refleksja wykryla @Experimental): funkcja '" + name
                    + "' jest eksperymentalna - " + experimental.reason());
        }
        functions.put(name, function);
    }

    Result<Double> apply(String name, double argument) {
        DoubleUnaryOperator function = functions.get(name);
        if (function == null) {
            return new Result.Failure<>("Nieznana funkcja: " + name);
        }
        return new Result.Success<>(function.applyAsDouble(argument));
    }
}

@Experimental(reason = "Przyblizony algorytm (uproszczona demonstracja), niesprawdzony na duzych wartosciach")
final class FastInverseSqrt implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double x) {
        // Uproszczona wersja - tylko dla celow demonstracyjnych tego kapstonu,
        // NIE prawdziwy "fast inverse square root" z bitowymi sztuczkami.
        return 1.0 / Math.sqrt(x);
    }
}
