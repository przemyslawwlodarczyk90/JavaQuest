package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise27_LoopGCDWhile {
    public static void main(String[] args) {
        // 🔧 Oblicz największy wspólny dzielnik (GCD) dwóch liczb np. 48 i 18 używając pętli while

        int a = 48;
        int b = 18;

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        System.out.println("GCD: " + a); // wynik: 6
    }
}
