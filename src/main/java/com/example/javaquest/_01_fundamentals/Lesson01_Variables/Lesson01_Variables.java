package com.example.javaquest._01_fundamentals.Lesson01_Variables;

import java.util.Arrays;

public class Lesson01_Variables {

    // ==========================================================
    // ZMIENNE INSTANCYJNE (POLA KLASY, tzw. "globalne")
    // ==========================================================

    // Zmienna instancyjna - należy do obiektu klasy, nie jest statyczna
    String globalText = "To jest zmienna instancyjna (czyli należąca do obiektu)";

    // Zmienna statyczna - wspólna dla wszystkich obiektów tej klasy
    static int globalCounter = 0;

    // Zmienna stała - niezmienna wartość, dobra praktyka: UPPER_SNAKE_CASE
    static final double PI = 3.14159;

    public static void main(String[] args) {

        // ==========================================================
        // ZMIENNE LOKALNE
        // ==========================================================

        int localNumber = 10; // tylko w tym bloku (metodzie main)
        String localMessage = "To jest zmienna lokalna";

        System.out.println("Zmienna lokalna: " + localMessage);
        System.out.println("Stała PI: " + PI);

        // ==========================================================
        // TYPY PRYMITYWNE (niewielkie, szybkie, przechowują wartość)
        // ==========================================================

        byte aByte = 127;
        short aShort = 32_000;
        int anInt = 1_000_000;
        long aLong = 1_000_000_000L;
        float aFloat = 3.14f;
        double aDouble = 3.1415926535;
        char aChar = 'Z';
        boolean isAdult = true;

        System.out.println("Przykład prymitywnego boolean: " + isAdult);
        System.out.println("Przykład prymitywnego double: " + aDouble);

        // ==========================================================
        // TYPY REFERENCYJNE
        // ==========================================================

        String name = "Przemek";
        int[] scores = {90, 80, 70};
        System.out.println("Imię: " + name);
        System.out.println("Pierwszy element tablicy: " + scores[0]);
        System.out.println("Cała tablica: " + Arrays.toString(scores));

        // ==========================================================
        // WRAPPER CLASSES – obiektowe odpowiedniki typów prostych
        // ==========================================================

        // Każdy prymitywny typ ma swój odpowiednik klasowy:
        // byte    → Byte
        // short   → Short
        // int     → Integer
        // long    → Long
        // float   → Float
        // double  → Double
        // char    → Character
        // boolean → Boolean

        Integer intWrapper = Integer.valueOf(anInt);     // zamiana int na Integer
        Double doubleWrapper = Double.valueOf(aDouble);  // zamiana double na Double
        Boolean boolWrapper = Boolean.valueOf(isAdult);  // zamiana boolean na Boolean
        Character charWrapper = Character.valueOf(aChar); // char → Character

        System.out.println("Integer wrapper: " + intWrapper);
        System.out.println("Double wrapper: " + doubleWrapper);
        System.out.println("Boolean wrapper: " + boolWrapper);
        System.out.println("Character wrapper: " + charWrapper);

        // Przykłady użycia metod klas wrapper:
        System.out.println("Maksymalna wartość Integer: " + Integer.MAX_VALUE);
        System.out.println("Minimalna wartość Short: " + Short.MIN_VALUE);
        System.out.println("Zamiana tekstu na int: " + Integer.parseInt("123"));
        System.out.println("Zamiana tekstu na boolean: " + Boolean.parseBoolean("true"));
        System.out.println("Zamiana int na tekst: " + Integer.toString(999));

        // Użycie Character:
        char znak = '7';
        System.out.println("Czy '7' to cyfra? " + Character.isDigit(znak));
        System.out.println("Czy 'a' to litera? " + Character.isLetter('a'));

        // ==========================================================
        // AUTOBOXING i UNBOXING
        // ==========================================================

        Integer boxedInt = anInt;       // autoboxing: int → Integer
        int unboxedInt = boxedInt;      // unboxing: Integer → int

        Double boxedDouble = 5.5;        // autoboxing
        double unboxedDouble = boxedDouble; // unboxing

        Boolean isOk = Boolean.TRUE;
        if (isOk) {
            System.out.println("Autoboxing działa – wartość Boolean.TRUE");
        }

        // Można także używać wrapperów w kolekcjach (np. List<Integer>)
        // bo kolekcje w Javie nie obsługują typów prymitywnych

        // ==========================================================
        // VAR – skrócona deklaracja zmiennej (od Javy 10)
        // ==========================================================

        var city = "Otwock";
        var age = 35;
        System.out.println("Miasto: " + city + ", wiek: " + age);

        // ==========================================================
        // NULL – brak referencji
        // ==========================================================

        String undefined = null;
        if (undefined == null) {
            System.out.println("Zmienna 'undefined' nie wskazuje na żaden obiekt.");
        }

        // ==========================================================
        // PORÓWNYWANIE: == vs .equals()
        // ==========================================================

        String s1 = "Hello";
        String s2 = new String("Hello");
        System.out.println("== (czy ten sam obiekt?): " + (s1 == s2));
        System.out.println(".equals (czy te same dane?): " + s1.equals(s2));

        // ==========================================================
        // DOSTĘP DO POLA INSTANCYJNEGO I STATYCZNEGO
        // ==========================================================

        Lesson01_Variables lesson = new Lesson01_Variables();
        lesson.showGlobalVariable();
        System.out.println("Zmienna statyczna (globalCounter): " + Lesson01_Variables.globalCounter);
    }

    void showGlobalVariable() {
        System.out.println("Zmienna instancyjna: " + globalText);
    }
}
