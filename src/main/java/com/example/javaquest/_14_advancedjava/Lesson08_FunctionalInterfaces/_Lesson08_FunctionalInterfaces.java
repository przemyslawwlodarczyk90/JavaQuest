package com.example.javaquest._14_advancedjava.Lesson08_FunctionalInterfaces;

import java.util.List;

public class _Lesson08_FunctionalInterfaces {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z _02_oop/Lesson08_Interfaces
         * ============================================================
         * W rozdziale _02_oop, przy okazji ogólnej lekcji o interfejsach,
         * poznałeś już PODSTAWY: interfejs funkcyjny to interfejs z
         * DOKŁADNIE JEDNĄ metodą abstrakcyjną, można go zaimplementować
         * przez wyrażenie lambda, a @FunctionalInterface to adnotacja
         * "pilnująca" tej reguły.
         *
         * W tej lekcji wchodzimy w to ZNACZNIE głębiej:
         * - dokładna definicja "jednej metody abstrakcyjnej" (co się LICZY,
         *   a co NIE liczy do tego limitu - default, static, metody z Object),
         * - pisanie WŁASNYCH interfejsów funkcyjnych od zera (biblioteka
         *   standardowa nie ma np. trójargumentowego Function),
         * - realny sens @FunctionalInterface i co się dzieje, gdy złamiesz
         *   regułę SAM.
         */

        /*
         * ============================================================
         * 🔹 REGUŁA SAM (Single Abstract Method) - DOKŁADNIE
         * ============================================================
         * Interfejs jest "funkcyjny", gdy ma DOKŁADNIE JEDNĄ metodę
         * abstrakcyjną, którą trzeba dostarczyć, żeby móc go
         * zaimplementować. Ale UWAGA - nie każda metoda w ciele interfejsu
         * liczy się do tego limitu:
         *
         * ✅ LICZY SIĘ do limitu SAM:
         *    - zwykła metoda abstrakcyjna (bez ciała, bez default/static)
         *
         * ❌ NIE LICZY SIĘ do limitu SAM:
         *    - metody default (mają własną implementację)
         *    - metody static (należą do interfejsu, nie do instancji)
         *    - metody, które i tak odziedziczysz/nadpiszesz z Object
         *      (equals, hashCode, toString) - NAWET jeśli redeklarujesz
         *      je jawnie jako abstrakcyjne w interfejsie! Powód: KAŻDA
         *      klasa w Javie i tak dostarcza te metody (z Object albo
         *      nadpisane), więc redeklaracja niczego nowego nie wymaga
         *      od implementującej lambdy.
         *
         * Zobacz interfejs StringProcessor (zdefiniowany na końcu pliku) -
         * ma JEDNĄ "prawdziwą" metodę abstrakcyjną (process), redeklarację
         * equals() z Object (nie liczy się), jedną metodę default i jedną
         * static - a mimo to jest w pełni poprawnym interfejsem funkcyjnym.
         */

        StringProcessor trimmer = input -> input.trim();
        StringProcessor toUpper = input -> input.toUpperCase();

        System.out.println("Trim: [" + trimmer.process("  Ala ma kota  ") + "]");

        // metoda default - NIE liczyła się do SAM, ale jest dostępna na każdej implementacji
        System.out.println("processTwice: " + toUpper.processTwice("ab"));

        // metoda default łącząca dwa procesory (podobnie jak Function.andThen - poznamy w Lesson11)
        StringProcessor trimThenUpper = trimmer.andThen(toUpper);
        System.out.println("trim + upper: [" + trimThenUpper.process("  ala ma kota  ") + "]");

        // metoda static interfejsu - wywoływana przez InterfaceName.metoda()
        StringProcessor identity = StringProcessor.identity();
        System.out.println("identity: [" + identity.process("bez zmian") + "]");

        /*
         * ============================================================
         * 🔹 WŁASNE INTERFEJSY FUNKCYJNE OD ZERA
         * ============================================================
         * java.util.function (Lesson11) pokrywa większość typowych
         * przypadków - Function<T,R> (1 argument), BiFunction<T,U,R>
         * (2 argumenty) - ale NIE MA trójargumentowego odpowiednika!
         * Gdy standardowa biblioteka czegoś nie oferuje, piszesz WŁASNY
         * interfejs funkcyjny - to zwykły interfejs z jedną metodą
         * abstrakcyjną, nic więcej nie jest potrzebne.
         *
         * TriFunction<A, B, C, R> (zdefiniowany na końcu pliku):
         */

        TriFunction<Integer, Integer, Integer, Integer> sum3 = (a, b, c) -> a + b + c;
        System.out.println("sum3(1, 2, 3) = " + sum3.apply(1, 2, 3));

        TriFunction<String, String, String, String> concat3 = (a, b, c) -> a + b + c;
        System.out.println("concat3 = " + concat3.apply("Ala", " ma ", "kota"));

        /*
         * Kolejny własny interfejs: CheckedFunction<T, R> - odpowiednik
         * Function<T,R>, ale metoda MOŻE rzucić checked exception. Zwykły
         * java.util.function.Function tego nie pozwala (jego apply() nie
         * deklaruje "throws"), więc gdy lambda wewnątrz musi wywołać coś
         * "ryzykownego" (np. parsowanie), własny interfejs funkcyjny z
         * deklaracją "throws" bywa wygodniejszy niż łapanie wyjątku
         * wewnątrz lambdy.
         */

        CheckedFunction<String, Integer> parseNumber = Integer::parseInt; // podgląd method reference (Lesson10)

        try {
            System.out.println("parseNumber(\"42\") = " + parseNumber.apply("42"));
            System.out.println("parseNumber(\"abc\") = " + parseNumber.apply("abc")); // rzuci NumberFormatException
        } catch (Exception e) {
            System.out.println("Złapano wyjątek: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔍 @FunctionalInterface - REALNY SENS
         * ============================================================
         * @FunctionalInterface NIE jest wymagana, żeby interfejs z jedną
         * metodą abstrakcyjną dało się zapisać jako lambdę - to zadziała
         * i BEZ tej adnotacji. Jej prawdziwa rola to WERYFIKACJA W CZASIE
         * KOMPILACJI: jeśli oznaczysz interfejs jako @FunctionalInterface,
         * a ktoś (Ty, kolega z zespołu, przyszły Ty za pół roku) doda
         * DRUGĄ metodę abstrakcyjną, kompilator NATYCHMIAST zgłosi błąd -
         * zamiast dopiero w miejscu użycia lambdy, gdzieś daleko w kodzie.
         *
         * To jest best practice, dokumentacja intencji "ten interfejs MA
         * pozostać jednometodowy", wymuszona przez kompilator.
         *
         * Poniższy kod (CELOWO w komentarzu, bo NIE skompilowałby się)
         * pokazuje co się stanie, gdy złamiesz regułę SAM:
         *
         *   @FunctionalInterface
         *   interface Broken {
         *       void methodA();
         *       void methodB(); // <-- DRUGA metoda abstrakcyjna
         *   }
         *
         *   Błąd kompilacji:
         *   "Broken is not a functional interface
         *    multiple non-overriding abstract methods found in
         *    interface Broken"
         *
         * Bez @FunctionalInterface ten sam kod (interfejs z dwiema
         * metodami abstrakcyjnymi) skompilowałby się BEZ PROBLEMU - po
         * prostu przestałby być interfejsem funkcyjnym (nie dałoby się
         * go zapisać jako lambdę, trzeba by użyć klasy/klasy anonimowej).
         * Błąd pojawiłby się dopiero PÓŹNIEJ, w miejscu próby użycia go
         * jako lambdy - dużo mniej czytelny komunikat, dalej od źródła
         * problemu. @FunctionalInterface przenosi ten błąd bliżej
         * przyczyny.
         */

        List<StringProcessor> pipeline = List.of(trimmer, toUpper, identity);
        String result = "  ala ma kota  ";
        for (StringProcessor step : pipeline) {
            result = step.process(result);
        }
        System.out.println("Wynik pipeline'u: [" + result + "]");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Interfejs funkcyjny = DOKŁADNIE JEDNA metoda abstrakcyjna.
         * - default i static metody w interfejsie NIE liczą się do tego
         *   limitu - można ich mieć dowolnie wiele.
         * - Metody odziedziczone/redeklarowane z Object (equals, hashCode,
         *   toString) też NIE liczą się do limitu SAM, nawet redeklarowane
         *   jawnie jako abstrakcyjne.
         * - java.util.function nie pokrywa wszystkiego (np. brak
         *   trójargumentowego Function) - własny interfejs funkcyjny to
         *   zwykły interfejs z jedną metodą abstrakcyjną, żadnej magii.
         * - @FunctionalInterface nie jest wymagana, ale to best practice -
         *   przenosi błąd złamania reguły SAM na etap kompilacji, w
         *   miejscu deklaracji interfejsu, a nie dopiero w miejscu użycia.
         * - Własny interfejs funkcyjny może deklarować "throws" w swojej
         *   metodzie abstrakcyjnej (czego wbudowane Function/Supplier/itd.
         *   nie robią) - przydatne przy checked exceptions.
         */

        System.out.println("\n=== KONIEC LEKCJI 08 ===");
    }
}

/**
 * Interfejs funkcyjny z jedną "prawdziwą" metodą abstrakcyjną (process),
 * redeklaracją equals() z Object (nie liczy się do SAM), jedną metodą
 * default i jedną static - mimo to w pełni poprawny interfejs funkcyjny.
 */
@FunctionalInterface
interface StringProcessor {

    String process(String input); // JEDYNA metoda licząca się do SAM

    // Redeklaracja metody z Object - NIE liczy się do limitu SAM, bo
    // KAŻDA klasa implementująca ten interfejs i tak dostarcza equals()
    // (odziedziczone z Object albo nadpisane).
    @Override
    boolean equals(Object obj);

    // metoda default - nie liczy się do SAM
    default String processTwice(String input) {
        return process(process(input));
    }

    // metoda default łącząca dwa procesory w jeden (wzorzec podobny do
    // Function.andThen, które poznamy w Lesson11)
    default StringProcessor andThen(StringProcessor after) {
        return input -> after.process(this.process(input));
    }

    // metoda static - nie liczy się do SAM, wywoływana jako
    // StringProcessor.identity()
    static StringProcessor identity() {
        return input -> input;
    }
}

/**
 * Własny interfejs funkcyjny trójargumentowy - java.util.function nie ma
 * odpowiednika Function dla trzech argumentów wejściowych.
 */
@FunctionalInterface
interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}

/**
 * Własny interfejs funkcyjny, którego metoda MOŻE rzucić checked exception -
 * java.util.function.Function tego nie pozwala.
 */
@FunctionalInterface
interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
