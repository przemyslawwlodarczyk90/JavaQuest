package com.example.javaquest.Lesson01_Variables;

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

        // Całkowite
        byte aByte = 127;              // zakres: -128 do 127
        short aShort = 32_000;
        int anInt = 1_000_000;
        long aLong = 1_000_000_000L;

        // Zmiennoprzecinkowe
        float aFloat = 3.14f;
        double aDouble = 3.1415926535;

        // Znak i logiczny
        char aChar = 'Z';
        boolean isAdult = true;

        System.out.println("Przykład prymitywnego boolean: " + isAdult);
        System.out.println("Przykład prymitywnego double: " + aDouble);

        // ==========================================================
        // TYPY REFERENCYJNE (np. String, tablice, klasy)
        // ==========================================================

        String name = "Przemek";  // String to obiekt (referencyjny)
        int[] scores = {90, 80, 70}; // tablica to też typ referencyjny

        System.out.println("Imię: " + name);
        System.out.println("Pierwszy element tablicy scores: " + scores[0]);

        // Tablica jako obiekt ma metodę toString w klasie Arrays:
        System.out.println("Wszystkie elementy tablicy: " + Arrays.toString(scores));

        // ==========================================================
        // AUTObOXING i UNBOXING (int → Integer, boolean → Boolean itd.)
        // ==========================================================

        Integer boxedInt = anInt;           // autoboxing
        int unboxedInt = boxedInt;          // unboxing

        Boolean isOk = Boolean.TRUE;
        if (isOk) {
            System.out.println("Autoboxing działa: Boolean.TRUE");
        }

        // ==========================================================
        // VAR - skrócona deklaracja zmiennej (od Javy 10)
        // ==========================================================

        var city = "Otwock";     // typ inferred: String
        var age = 35;            // typ inferred: int
        System.out.println("Miasto: " + city + ", wiek: " + age);

        // ==========================================================
        // NULL - brak referencji (dotyczy tylko typów referencyjnych)
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

        System.out.println("== (czy ten sam obiekt?): " + (s1 == s2));           // false
        System.out.println(".equals (czy takie same dane?): " + s1.equals(s2));  // true

        // ==========================================================
        // TWORZENIE OBIEKTU I DOSTĘP DO POLA INSTANCYJNEGO
        // ==========================================================

        Lesson01_Variables lesson = new Lesson01_Variables(); // obiekt klasy
        lesson.showGlobalVariable(); // wywołanie metody instancyjnej

        // Statyczna zmienna (może być użyta bez tworzenia obiektu)
        System.out.println("Zmienna statyczna (globalCounter): " + Lesson01_Variables.globalCounter);
    }

    // Metoda instancyjna – dostęp do pól instancyjnych (czyli "nie-static")
    void showGlobalVariable() {
        System.out.println("Zmienna instancyjna: " + globalText);
    }
}
