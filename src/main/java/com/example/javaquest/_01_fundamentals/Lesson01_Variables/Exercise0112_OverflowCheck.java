package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise0112_OverflowCheck {
    public static void main(String[] args) {
        // 🧨 Przepełnienie dla byte
        byte b = 127; // maksymalna wartość dla byte
        b++; // inkrementacja — przepełnienie
        System.out.println("byte overflow: " + b); // -128

        // 🧨 Przepełnienie dla short
        short s = 32_767; // maksymalna wartość dla short
        s++; // inkrementacja — przepełnienie
        System.out.println("short overflow: " + s); // -32_768

        // Wartości po overflow są liczone „od zera” — zachowanie cykliczne
    }
}
