package com.example.javaquest._28_java_evolution.Lesson07_Java9SmallerFeatures;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson07_Java9SmallerFeatures {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: Java 9 (2017) - mniejsze, ale przydatne funkcje ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Java 9 TO NIE TYLKO JPMS (Lesson06)
         * ============================================================
         * OBOK NAJWIEKSZEJ zmiany (moduly), Java 9 wprowadzila
         * KILKA MNIEJSZYCH, ALE CODZIENNIE PRZYDATNYCH funkcji.
         */
        System.out.println("Java 9 (2017) obok JPMS: try-with-resources NA zmiennych 'effectively final', prywatne metody interfejsu, List.of/Map.of/Set.of, JShell.");

        demonstrateTryWithResourcesOnEffectivelyFinal();
        demonstratePrivateInterfaceMethods();
        demonstrateFactoryMethodsForCollections();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - try-with-resources NA JUZ ISTNIEJACEJ zmiennej
         *   "effectively final" (`_02_oop/Lesson10`) - NIE TRZEBA
         *   deklarowac NOWEJ zmiennej W nawiasach `try(...)`.
         * - Prywatne metody W interfejsach - metody `default`
         *   (Lesson04) MOGA teraz WSPOLDZIELIC WSPOLNY, PRYWATNY kod
         *   (BEZ ujawniania go publicznie).
         * - `List.of(...)`/`Map.of(...)`/`Set.of(...)` -
         *   NIEZMIENNE (immutable) kolekcje "jedna linijka" (BEZ
         *   `Collections.unmodifiableList(new ArrayList<>(...))`).
         * - JShell (`jshell` W terminalu) - INTERAKTYWNY REPL DLA
         *   Javy (eksperymentowanie BEZ tworzenia calego projektu).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateTryWithResourcesOnEffectivelyFinal() throws Exception {
        System.out.println("\n--- try-with-resources NA 'effectively final' zmiennej (BEZ nowej deklaracji) ---");

        BufferedReader reader = new BufferedReader(new StringReader("linia1\nlinia2"));
        // PRZED Java 9: try (BufferedReader r = reader) { ... } - WYMAGANA NOWA zmienna "r".
        // OD Java 9: MOZNA uzyc ISTNIEJACEJ zmiennej "effectively final" WPROST.
        try (reader) {
            String firstLine = reader.readLine();
            System.out.println("Odczytano (BEZ nowej deklaracji zmiennej W try(...)): " + firstLine);
            assertThat(firstLine).isEqualTo("linia1");
        }
    }

    interface Validator {
        boolean isValid(String input);

        default boolean isValidAndNotEmpty(String input) {
            return logAndCheck(input) && isValid(input);
        }

        default boolean isValidOrDefault(String input, boolean defaultValue) {
            if (!logAndCheck(input)) {
                return defaultValue;
            }
            return isValid(input);
        }

        // PRYWATNA metoda interfejsu (Java 9) - WSPOLNY kod DLA metod `default` POWYZEJ,
        // BEZ ujawniania go jako PUBLICZNEGO API interfejsu.
        private boolean logAndCheck(String input) {
            boolean nonEmpty = input != null && !input.isBlank();
            System.out.println("  [prywatna metoda interfejsu] sprawdzam niepustosc '" + input + "': " + nonEmpty);
            return nonEmpty;
        }
    }

    private static void demonstratePrivateInterfaceMethods() {
        System.out.println("\n--- Prywatne metody interfejsu (Java 9) - WSPOLNY kod dla metod default ---");
        Validator lengthValidator = input -> input.length() > 3;

        boolean result1 = lengthValidator.isValidAndNotEmpty("Java");
        boolean result2 = lengthValidator.isValidOrDefault("", true);

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        System.out.println("Obie metody default WSPOLDZIELA prywatna logike 'logAndCheck' - BEZ duplikacji kodu.");
    }

    private static void demonstrateFactoryMethodsForCollections() {
        System.out.println("\n--- List.of/Map.of/Set.of (Java 9) - NIEZMIENNE kolekcje 'jedna linijka' ---");

        List<String> immutableList = List.of("Java", "Kotlin", "Scala");
        Map<String, Integer> immutableMap = Map.of("Java", 1995, "Kotlin", 2011);
        Set<String> immutableSet = Set.of("A", "B", "C");

        System.out.println("List.of(...): " + immutableList);
        System.out.println("Map.of(...): " + immutableMap);
        System.out.println("Set.of(...): " + immutableSet);

        assertThatThrownBy(() -> immutableList.add("Rust"))
                .isInstanceOf(UnsupportedOperationException.class);
        System.out.println("PROBA .add(...) NA List.of(...) POPRAWNIE rzucila UnsupportedOperationException (NAPRAWDE niezmienne, NIE tylko 'widok').");
    }
}
