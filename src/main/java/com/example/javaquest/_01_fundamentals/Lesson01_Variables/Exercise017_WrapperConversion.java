package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise017_WrapperConversion {
    public static void main(String[] args) {
        // 🔁 Zamień Stringa \"123\" na int, a int na String.
        // Użyj Integer.parseInt(), Integer.valueOf() oraz String.valueOf().


        String str = "123";

        int parsed = Integer.parseInt(str);        // String → int
        Integer boxed = Integer.valueOf(str);      // String → Integer (autoboxing)

        int number = 124;
        String converted = String.valueOf(number); // int → String

        System.out.println("parseInt: " + parsed);
        System.out.println("valueOf: " + boxed);
        System.out.println("String.valueOf: " + converted);
    }
}