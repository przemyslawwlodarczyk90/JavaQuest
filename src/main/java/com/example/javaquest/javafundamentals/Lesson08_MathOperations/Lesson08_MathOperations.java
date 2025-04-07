package com.example.javaquest.javafundamentals.Lesson08_MathOperations;

public class Lesson08_MathOperations {

    public static void main(String[] args) {

        /*
         * ==================================================================
         * ➕🧮 KLASA Math – OPERACJE MATEMATYCZNE W JAVIE
         * ==================================================================
         * Klasa Math zawiera zestaw metod statycznych do operacji matematycznych:
         * - wartości absolutne, potęgowanie, pierwiastki
         * - zaokrąglanie
         * - wartości trygonometryczne
         * - losowanie
         * Wszystkie metody są statyczne – używamy ich bez tworzenia obiektu.
         */

        // =========================
        // 📏 PODSTAWOWE OPERACJE
        // =========================
        System.out.println("abs(-5): " + Math.abs(-5)); // wartość bezwzględna
        System.out.println("max(10, 20): " + Math.max(10, 20)); // większa z dwóch
        System.out.println("min(10, 20): " + Math.min(10, 20)); // mniejsza z dwóch

        // =========================
        // 🔼 POTĘGOWANIE I PIERWIASTKI
        // =========================
        System.out.println("pow(2, 3): " + Math.pow(2, 3)); // 2^3 = 8.0
        System.out.println("sqrt(25): " + Math.sqrt(25)); // pierwiastek kwadratowy
        System.out.println("cbrt(27): " + Math.cbrt(27)); // pierwiastek sześcienny

        // =========================
        // 📐 TRYGINOMETRIA
        // =========================
        double angle = Math.toRadians(45); // konwersja stopni na radiany
        System.out.println("sin(45°): " + Math.sin(angle));
        System.out.println("cos(45°): " + Math.cos(angle));
        System.out.println("tan(45°): " + Math.tan(angle));

        // odwrotne funkcje trygonometryczne
        System.out.println("asin(0.5): " + Math.toDegrees(Math.asin(0.5)));
        System.out.println("acos(0.5): " + Math.toDegrees(Math.acos(0.5)));
        System.out.println("atan(1): " + Math.toDegrees(Math.atan(1)));

        // =========================
        // 🔁 ZAOKRĄGLANIE
        // =========================
        System.out.println("ceil(5.3): " + Math.ceil(5.3)); // w górę do najbliższej liczby całkowitej
        System.out.println("floor(5.7): " + Math.floor(5.7)); // w dół do najbliższej liczby całkowitej
        System.out.println("round(5.5): " + Math.round(5.5)); // zaokrąglenie klasyczne
        System.out.println("rint(5.5): " + Math.rint(5.5)); // zaokrąglenie do najbliższej liczby zmiennoprzecinkowej

        // =========================
        // 📉 LOGARYTMY I EXP
        // =========================
        System.out.println("log(100): " + Math.log(100)); // log naturalny
        System.out.println("log10(100): " + Math.log10(100)); // log dziesiętny
        System.out.println("exp(1): " + Math.exp(1)); // stała e^1

        // =========================
        // 🎲 LOSOWOŚĆ
        // =========================
        System.out.println("random(): " + Math.random()); // losowa liczba od 0.0 do 1.0
        int randomInt = (int) (Math.random() * 10) + 1; // losowa liczba od 1 do 10
        System.out.println("Losowa liczba 1-10: " + randomInt);

        // =========================
        // 📌 STAŁE MATEMATYCZNE
        // =========================
        System.out.println("PI: " + Math.PI);
        System.out.println("E: " + Math.E);

        // =========================
        // ⏱️ PRZYKŁAD – WARTOŚĆ BEZWZGLĘDNA RÓŻNICY
        // =========================
        int expected = 100;
        int actual = 87;
        int diff = Math.abs(expected - actual);
        System.out.println("Różnica absolutna: " + diff);
    }
}
