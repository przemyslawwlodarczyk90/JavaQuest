package com.example.javaquest.operators02;

public class Lesson02_Operators {

    public static void main(String[] args) {

        // ========================
        // OPERATORY ARYTMETYCZNE
        // ========================
        int a = 10;
        int b = 3;

        System.out.println("Dodawanie: " + (a + b));        // 13
        System.out.println("Odejmowanie: " + (a - b));      // 7
        System.out.println("Mnożenie: " + (a * b));         // 30
        System.out.println("Dzielenie: " + (a / b));        // 3 (dzielenie całkowite)
        System.out.println("Modulo: " + (a % b));           // 1 (reszta z dzielenia)

        // ========================
        // OPERATORY PRZYPISANIA
        // ========================
        int x = 5;
        x += 3;  // x = x + 3
        System.out.println("x po += 3: " + x); // 8

        x *= 2;  // x = x * 2
        System.out.println("x po *= 2: " + x); // 16

        // ========================
        // OPERATORY PORÓWNANIA
        // ========================
        int p = 10;
        int q = 20;

        System.out.println("p == q: " + (p == q));  // false
        System.out.println("p != q: " + (p != q));  // true
        System.out.println("p > q: " + (p > q));    // false
        System.out.println("p < q: " + (p < q));    // true
        System.out.println("p >= q: " + (p >= q));  // false
        System.out.println("p <= q: " + (p <= q));  // true

        // ========================
        // OPERATORY LOGICZNE
        // ========================
        boolean isSunny = true;
        boolean isWarm = false;

        System.out.println("isSunny && isWarm: " + (isSunny && isWarm));  // false
        System.out.println("isSunny || isWarm: " + (isSunny || isWarm)); // true
        System.out.println("!isSunny: " + (!isSunny));                   // false

        // ========================
        // OPERATOR TERNARNY
        // ========================
        int age = 18;
        String result = (age >= 18) ? "Pełnoletni" : "Niepełnoletni";
        System.out.println("Wynik ternarny: " + result); // "Pełnoletni"

        // ========================
        // INKREMENTACJA / DEKREMENTACJA
        // ========================
        int n = 5;

        // Post-inkrementacja: wartość zostaje użyta, a potem zwiększona
        System.out.println("n (post-inkrementacja): " + (n++)); // 5
        System.out.println("n po post-inkrementacji: " + n);   // 6

        // Pre-inkrementacja: wartość zostaje najpierw zwiększona, a potem użyta
        System.out.println("n (pre-inkrementacja): " + (++n)); // 7

        // Post-dekrementacja: wartość zostaje użyta, a potem zmniejszona
        System.out.println("n (post-dekrementacja): " + (n--)); // 7
        System.out.println("n po post-dekrementacji: " + n);    // 6

        // Pre-dekrementacja: wartość zostaje najpierw zmniejszona, a potem użyta
        System.out.println("n (pre-dekrementacja): " + (--n)); // 5
    }
}