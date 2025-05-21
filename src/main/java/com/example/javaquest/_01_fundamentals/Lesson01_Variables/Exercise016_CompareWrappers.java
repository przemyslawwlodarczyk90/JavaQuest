package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise016_CompareWrappers {
    public static void main(String[] args) {
        // 🔄 Stwórz 2 obiekty typu Integer i porównaj je za pomocą == i .equals().
        // Sprawdź też jak zachowuje się autoboxing (np. Integer x = 127 vs 128).


        Integer x1 = 127;
        Integer x2 = 127;

        Integer y1 = 128;
        Integer y2 = 128;

        System.out.println("x1 == x2: " + (x1 == x2));         // true (ta sama referencja z cache)
        System.out.println("x1.equals(x2): " + x1.equals(x2)); // true

        System.out.println("y1 == y2: " + (y1 == y2));         // false (poza cache — nowe obiekty)
        System.out.println("y1.equals(y2): " + y1.equals(y2)); // true
    }
}