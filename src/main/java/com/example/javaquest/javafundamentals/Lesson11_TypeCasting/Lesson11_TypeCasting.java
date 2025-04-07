package com.example.javaquest.javafundamentals.Lesson11_TypeCasting;

public class Lesson11_TypeCasting {

    /**
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

    public static void main(String[] args) {

        // ============================================================
        // 🔹 1. KONWERSJE NIEJAWNE (IMPLICIT/WIDENING)
        // ============================================================
        // Zachodzą automatycznie, gdy nie ma ryzyka utraty danych

        int myInt = 100;
        long myLong = myInt;           // int → long
        float myFloat = myLong;        // long → float
        double myDouble = myFloat;     // float → double

        System.out.println("int: " + myInt);
        System.out.println("long: " + myLong);
        System.out.println("float: " + myFloat);
        System.out.println("double: " + myDouble);

        // ============================================================
        // 🔹 2. KONWERSJE JAWNE (EXPLICIT/NARROWING)
        // ============================================================
        // Gdy może nastąpić utrata danych – trzeba jawnie rzutować

        double d = 9.78;
        int i = (int) d; // utracimy część po przecinku

        System.out.println("double przed rzutowaniem: " + d);
        System.out.println("int po rzutowaniu: " + i);

        long bigLong = 1_000_000_000_000L;
        int smallerInt = (int) bigLong; // utrata wartości – przepełnienie
        System.out.println("long: " + bigLong + " → int (overflow): " + smallerInt);

        // ============================================================
        // 🔹 3. KONWERSJE TYPÓW OBIEKTOWYCH (UPCAST / DOWNCAST)
        // ============================================================

        Animal animal = new Dog(); // upcasting – zawsze bezpieczny
        animal.makeSound();

        // Dog dog = new Animal(); // ❌ błąd kompilacji (niedozwolony downcast bez rzutowania)
        Dog dog = (Dog) animal;    // downcasting – musi być jawny i bezpieczny
        dog.bark();

        // Uwaga: rzutowanie niepoprawne → ClassCastException
        Animal cat = new Cat();
        // Dog notADog = (Dog) cat; // ❌ rzuci wyjątek w czasie działania

        // ============================================================
        // 🔹 4. KONWERSJE STRING ↔ LICZBA
        // ============================================================

        String text = "123";
        int parsed = Integer.parseInt(text);
        double parsedDouble = Double.parseDouble("3.14");
        System.out.println("parsowanie int: " + parsed);
        System.out.println("parsowanie double: " + parsedDouble);

        int number = 42;
        String numberAsText = String.valueOf(number);
        System.out.println("int jako String: " + numberAsText);

        // ============================================================
        // 🔹 5. AUTOBOKSOWANIE I RĘCZNE ODWROTNE KONWERSJE
        // ============================================================

        Integer boxed = 10; // autoboxing (int → Integer)
        int unboxed = boxed; // unboxing (Integer → int)

        System.out.println("Autoboxing: " + boxed);
        System.out.println("Unboxing: " + unboxed);

        // ============================================================
        // 🔹 6. KONWERSJE Z CHAR
        // ============================================================

        char c = 'A';
        int code = c;  // char → int (kod ASCII/Unicode)
        char fromInt = (char) 66; // int → char
        System.out.println("Kod znaku 'A': " + code);
        System.out.println("Znak dla 66: " + fromInt);

        // ============================================================
        // 🔹 7. KONWERSJE BOOLEAN → NIE DZIAŁAJĄ!
        // ============================================================
        // boolean nie konwertuje się do liczby ani odwrotnie
        // boolean b = (boolean) 1; // ❌ Błąd
    }
}

class Animal {
    void makeSound() {
        System.out.println("Zwierzę wydaje dźwięk");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Hau hau!");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("Miau miau!");
    }
}
