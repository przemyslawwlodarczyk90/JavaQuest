package com.example.javaquest._01_fundamentals.Lesson11_TypeCasting;

public class _Lesson11_TypeCasting {

    public static void main(String[] args) {

        /*
         * ========================================================================
         * 🔄 KONWERSJE I RZUTOWANIA TYPÓW W JAVIE
         * ========================================================================
         * ➤ Java pozwala na dwa główne rodzaje konwersji:
         *    ✅ konwersja niejawna (implicit/widening)
         *    ✅ konwersja jawna (explicit/narrowing)
         *
         * ➤ Dotyczy zarówno typów prymitywnych, jak i obiektów.
         * ➤ Niektóre konwersje są automatyczne, inne wymagają jawnego rzutowania.
         */

        /*
         * ============================================================
         * 🔹 1. KONWERSJE NIEJAWNE (IMPLICIT/WIDENING)
         * ============================================================
         * Zachodzą automatycznie, gdy nie ma ryzyka utraty danych.
         * Kolejność: byte → short → int → long → float → double
         */

        int myInt = 100;
        long myLong = myInt;           // int → long
        float myFloat = myLong;        // long → float
        double myDouble = myFloat;     // float → double

        System.out.println("int: " + myInt);
        System.out.println("long: " + myLong);
        System.out.println("float: " + myFloat);
        System.out.println("double: " + myDouble);

        /*
         * ============================================================
         * 🔹 2. KONWERSJE JAWNE (EXPLICIT/NARROWING)
         * ============================================================
         * Gdy może nastąpić utrata danych – trzeba jawnie rzutować.
         * Ryzyko: utrata cyfr po przecinku lub przepełnienie (overflow).
         */

        double d = 9.78;
        int i = (int) d; // utracimy część po przecinku

        System.out.println("double przed rzutowaniem: " + d);
        System.out.println("int po rzutowaniu: " + i);

        long bigLong = 1_000_000_000_000L;
        int smallerInt = (int) bigLong; // utrata wartości – przepełnienie
        System.out.println("long: " + bigLong + " → int (overflow): " + smallerInt);

        /*
         * ============================================================
         * 🔹 3. KONWERSJE TYPÓW OBIEKTOWYCH (UPCAST / DOWNCAST)
         * ============================================================
         * - upcasting: zawsze bezpieczny (podklasa → nadklasa)
         * - downcasting: wymaga jawnego rzutowania, może rzucić ClassCastException
         */

        CastAnimal animal = new CastDog(); // upcasting – zawsze bezpieczny
        animal.makeSound();

        // CastDog dog = new CastAnimal(); // ❌ błąd kompilacji (niedozwolony downcast bez rzutowania)
        CastDog dog = (CastDog) animal;    // downcasting – musi być jawny i bezpieczny
        dog.bark();

        // Uwaga: rzutowanie niepoprawne → ClassCastException
        CastAnimal cat = new CastCat();
        // CastDog notADog = (CastDog) cat; // ❌ rzuci wyjątek w czasie działania

        /*
         * ============================================================
         * 🔹 4. KONWERSJE STRING ↔ LICZBA
         * ============================================================
         */

        String text = "123";
        int parsed = Integer.parseInt(text);
        double parsedDouble = Double.parseDouble("3.14");
        System.out.println("parsowanie int: " + parsed);
        System.out.println("parsowanie double: " + parsedDouble);

        int number = 42;
        String numberAsText = String.valueOf(number);
        System.out.println("int jako String: " + numberAsText);

        /*
         * ============================================================
         * 🔹 5. AUTOBOXING I UNBOXING
         * ============================================================
         * - autoboxing: prymityw → wrapper (np. int → Integer)
         * - unboxing:   wrapper → prymityw (np. Integer → int)
         */

        Integer boxed = 10; // autoboxing (int → Integer)
        int unboxed = boxed; // unboxing (Integer → int)

        System.out.println("Autoboxing: " + boxed);
        System.out.println("Unboxing: " + unboxed);

        /*
         * ============================================================
         * 🔹 6. KONWERSJE Z CHAR
         * ============================================================
         */

        char c = 'A';
        int code = c;  // char → int (kod ASCII/Unicode)
        char fromInt = (char) 66; // int → char
        System.out.println("Kod znaku 'A': " + code);
        System.out.println("Znak dla 66: " + fromInt);

        /*
         * ============================================================
         * 🔹 7. KONWERSJE BOOLEAN → NIE DZIAŁAJĄ!
         * ============================================================
         * boolean nie konwertuje się do liczby ani odwrotnie.
         * boolean b = (boolean) 1; // ❌ Błąd kompilacji
         */
    }
}

class CastAnimal {
    void makeSound() {
        System.out.println("Zwierzę wydaje dźwięk");
    }
}

class CastDog extends CastAnimal {
    void bark() {
        System.out.println("Hau hau!");
    }
}

class CastCat extends CastAnimal {
    void meow() {
        System.out.println("Miau miau!");
    }
}
