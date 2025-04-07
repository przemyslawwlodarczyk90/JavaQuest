package com.example.javaquest.Lesson02_Operators;

public class Lesson02_Operators {

    public static void main(String[] args) {

        // =====================================================
        // 🔢 OPERATORY ARYTMETYCZNE
        // =====================================================
        int a = 10;
        int b = 3;

        System.out.println("Dodawanie: " + (a + b));        // 13
        System.out.println("Odejmowanie: " + (a - b));      // 7
        System.out.println("Mnożenie: " + (a * b));         // 30
        System.out.println("Dzielenie: " + (a / b));        // 3 (dzielenie całkowite)
        System.out.println("Modulo (reszta): " + (a % b));  // 1

        double dzielenie = 10.0 / 3.0;
        System.out.println("Dzielenie zmiennoprzecinkowe: " + dzielenie);

        // =====================================================
        // 🔄 INKREMENTACJA / DEKREMENTACJA
        // =====================================================

        int n = 5;

        System.out.println("Post-inkrementacja: " + (n++)); // 5 (najpierw używa, potem zwiększa)
        System.out.println("Po inkrementacji: " + n);       // 6

        System.out.println("Pre-inkrementacja: " + (++n));  // 7 (najpierw zwiększa, potem używa)

        System.out.println("Post-dekrementacja: " + (n--)); // 7
        System.out.println("Po dekrementacji: " + n);       // 6

        System.out.println("Pre-dekrementacja: " + (--n));  // 5

        // =====================================================
        // 🟰 OPERATORY PRZYPISANIA
        // =====================================================

        int x = 4;
        x += 2;  // x = x + 2 → 6
        x *= 3;  // x = x * 3 → 18
        x -= 5;  // x = x - 5 → 13
        x /= 2;  // x = x / 2 → 6
        x %= 4;  // x = x % 4 → 2

        System.out.println("Wynik po różnych przypisaniach: " + x);

        // =====================================================
        // 🧮 OPERATORY PORÓWNANIA
        // =====================================================

        int p = 10;
        int q = 20;

        System.out.println("p == q: " + (p == q));  // false
        System.out.println("p != q: " + (p != q));  // true
        System.out.println("p > q: " + (p > q));    // false
        System.out.println("p < q: " + (p < q));    // true
        System.out.println("p >= q: " + (p >= q));  // false
        System.out.println("p <= q: " + (p <= q));  // true

        // =====================================================
        // ⚙️ OPERATORY LOGICZNE (bool)
        // =====================================================

        boolean isSunny = true;
        boolean isWarm = false;

        System.out.println("AND (&&): " + (isSunny && isWarm));   // false
        System.out.println("OR  (||): " + (isSunny || isWarm));   // true
        System.out.println("NOT (!): " + (!isSunny));             // false

        // Operatory bitowe (działają też logicznie dla boolean)
        System.out.println("AND (&): " + (isSunny & isWarm));     // false
        System.out.println("OR  (|): " + (isSunny | isWarm));     // true
        System.out.println("XOR (^): " + (isSunny ^ isWarm));     // true

        // Przykład logiczny:
        int age = 25;
        boolean hasId = true;
        if (age >= 18 && hasId) {
            System.out.println("Osoba może wejść.");
        }



        // =====================================================
        // 🧪 EKSPERYMENTALNE PRZYKŁADY
        // =====================================================

        // Czy liczba jest parzysta?
        int number = 8;
        boolean isEven = (number % 2 == 0);
        System.out.println("Czy " + number + " jest parzysta? " + isEven);

        // Czy wartość mieści się w przedziale?
        int temperature = 22;
        if (temperature >= 20 && temperature <= 25) {
            System.out.println("Temperatura jest komfortowa.");
        }

        // Negacja warunku
        boolean isClosed = false;
        if (!isClosed) {
            System.out.println("Drzwi są otwarte.");
        }
    }
}
