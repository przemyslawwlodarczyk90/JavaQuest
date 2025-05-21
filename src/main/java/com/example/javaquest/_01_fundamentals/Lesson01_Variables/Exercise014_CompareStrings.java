package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise014_CompareStrings {
    public static void main(String[] args) {
        // 🔁 Stwórz 2 Stringi: jeden przez "", drugi przez new String().
        // Porównaj je za pomocą == i .equals(), wypisz wyniki.

        String a = "abc";                 // literał - trafia do String poola
        String b = new String("abc");     // nowy obiekt na stercie

        System.out.println("a == b: " + (a == b));         // false (różne obiekty)
        System.out.println("a.equals(b): " + a.equals(b)); // true (ta sama zawartość)
    }
}
