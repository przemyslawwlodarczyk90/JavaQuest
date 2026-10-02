package com.example.javaquest.liveCodeVersion._01_fundamentals.Lesson02_Operators;

public class _Lesson02_Operators {

    public static void main(String[] args) {

        /*
         * =====================================================
         * 🔢 OPERATORY ARYTMETYCZNE
         * =====================================================
         * Służą do wykonywania podstawowych operacji matematycznych.
         * Działają na typach liczbowych (int, double, itd.).
         */
        int a = 10;
        int b = 3;

        System.out.println("Dodawanie: " + (a + b));        // 13
        System.out.println("Odejmowanie: " + (a - b));      // 7
        System.out.println("Mnożenie: " + (a * b));         // 30
        System.out.println("Dzielenie (całkowite): " + (a / b));  // 3
        System.out.println("Modulo (reszta z dzielenia): " + (a % b));  // 1

        double dzielenie = 10.0 / 3.0;
        System.out.println("Dzielenie zmiennoprzecinkowe: " + dzielenie); // 3.333...

        // Przykład praktyczny
        int suma = 7 + 5; // 12
        int roznica = 9 - 4; // 5
        int iloczyn = 6 * 7; // 42
        int iloraz = 8 / 2; // 4
        int reszta = 13 % 5; // 3

        /*
         * =====================================================
         * 🔄 INKREMENTACJA / DEKREMENTACJA
         * =====================================================
         * ++ zwiększa wartość o 1; -- zmniejsza wartość o 1.
         * Post (++n) i pre (n++) różnią się momentem wykonania.
         */
        int n = 5;

        System.out.println("Post-inkrementacja: " + (n++)); // 5 (potem 6)
        System.out.println("Po inkrementacji: " + n);       // 6
        System.out.println("Pre-inkrementacja: " + (++n));  // 7
        System.out.println("Post-dekrementacja: " + (n--)); // 7 (potem 6)
        System.out.println("Po dekrementacji: " + n);       // 6
        System.out.println("Pre-dekrementacja: " + (--n));  // 5

        /*
         * =====================================================
         * 🟰 OPERATORY PRZYPISANIA
         * =====================================================
         * Umożliwiają skróconą formę przypisania z operacją.
         */
        int x = 4;
        x += 2;  // x = x + 2 → 6
        x *= 3;  // x = x * 3 → 18
        x -= 5;  // x = x - 5 → 13
        x /= 2;  // x = x / 2 → 6
        x %= 4;  // x = x % 4 → 2

        System.out.println("Wynik po różnych przypisaniach: " + x);

        /*
         * =====================================================
         * 🧮 OPERATORY PORÓWNANIA
         * =====================================================
         * Zwracają wartość typu boolean (true/false).
         */
        int p = 10;
        int q = 20;

        System.out.println("p == q: " + (p == q));  // false
        System.out.println("p != q: " + (p != q));  // true
        System.out.println("p > q: " + (p > q));    // false
        System.out.println("p < q: " + (p < q));    // true
        System.out.println("p >= q: " + (p >= q));  // false
        System.out.println("p <= q: " + (p <= q));  // true

        /*
         * =====================================================
         * ⚙️ OPERATORY LOGICZNE (BOOL)
         * =====================================================
         * Działają na wartościach logicznych (boolean).
         */
        boolean isSunny = true;
        boolean isWarm = false;

        System.out.println("AND (&&): " + (isSunny && isWarm));   // false
        System.out.println("OR  (||): " + (isSunny || isWarm));   // true
        System.out.println("NOT (!): " + (!isSunny));             // false

        // Operatory bitowe działające również na boolean:
        System.out.println("AND (&): " + (isSunny & isWarm));     // false
        System.out.println("OR  (|): " + (isSunny | isWarm));     // true
        System.out.println("XOR (^): " + (isSunny ^ isWarm));     // true

        // Praktyczny przykład:
        int age = 25;
        boolean hasId = true;
        if (age >= 18 && hasId) {
            System.out.println("Osoba może wejść.");
        }

        /*
         * =====================================================
         * 🧪 EKSPERYMENTALNE PRZYKŁADY
         * =====================================================
         */

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

        // Czy liczba mieści się poza przedziałem?
        int speed = 130;
        if (speed < 50 || speed > 120) {
            System.out.println("Prędkość poza zakresem!");
        }

        // Złożony warunek logiczny
        boolean loggedIn = true;
        boolean hasPermission = false;
        if (loggedIn && !hasPermission) {
            System.out.println("Zalogowany, ale brak uprawnień.");
        }
    }
}