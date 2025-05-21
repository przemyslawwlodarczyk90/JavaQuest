package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise0110_UseFinalVariable {
    public static void main(String[] args) {
        // 🔒 Zmienna final — wartość stała, niezmienna po przypisaniu

        final int x = 42;
        System.out.println("Wartość x: " + x);

        // ❌ Próba zmiany wartości — błąd kompilacji
        // x = 100; // error: cannot assign a value to final variable 'x'
    }
}
