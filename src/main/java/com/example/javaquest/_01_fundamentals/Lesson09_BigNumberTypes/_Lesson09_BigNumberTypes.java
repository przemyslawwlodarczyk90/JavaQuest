package com.example.javaquest._01_fundamentals.Lesson09_BigNumberTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class _Lesson09_BigNumberTypes {

    public static void main(String[] args) {

        /*
         * ========================================================================
         * 💡 BIGINTEGER I BIGDECIMAL – DO LICZB NIEOGRANICZONYCH I PRECYZYJNYCH
         * ========================================================================
         * - BigInteger: do operacji na bardzo dużych liczbach całkowitych (np. 1000 cyfr).
         * - BigDecimal: do dokładnych obliczeń na liczbach zmiennoprzecinkowych (np. finanse).
         * - Obie klasy są immutable – operacje tworzą nowy obiekt.
         * - Nie używamy operatorów (+, -, *, /), tylko metod: add, subtract, multiply, divide itd.
         */

        // ====================================================
        // 🔢 BigInteger – DUŻE LICZBY CAŁKOWITE (np. kryptografia)
        // ====================================================

        BigInteger big1 = new BigInteger("123456789012345678901234567890");
        BigInteger big2 = new BigInteger("987654321098765432109876543210");

        System.out.println("big1: " + big1);
        System.out.println("big2: " + big2);

        // PODSTAWOWE OPERACJE
        BigInteger sum = big1.add(big2);
        BigInteger difference = big2.subtract(big1);
        BigInteger product = big1.multiply(big2);
        BigInteger quotient = big2.divide(new BigInteger("3"));
        BigInteger modulo = big2.mod(new BigInteger("1000"));

        System.out.println("➕ Suma: " + sum);
        System.out.println("➖ Różnica: " + difference);
        System.out.println("✖ Mnożenie: " + product);
        System.out.println("➗ Dzielenie: " + quotient);
        System.out.println("% Modulo: " + modulo);

        // OPERACJE ZAAWANSOWANE
        System.out.println("Potęgowanie: big1^2 = " + big1.pow(2));
        System.out.println("GCD (największy wspólny dzielnik): " + big1.gcd(big2));
        System.out.println("Czy big1 jest liczbą pierwszą?: " + big1.isProbablePrime(20));

        // KONWERSJE
        int asInt = big1.intValue();
        long asLong = big2.longValue();
        System.out.println("Konwersja do int: " + asInt);
        System.out.println("Konwersja do long: " + asLong);

        // PORÓWNANIA
        System.out.println("Czy big1 < big2?: " + (big1.compareTo(big2) < 0));
        System.out.println("Czy big1 == big1?: " + big1.equals(big1));

        // ====================================================
        // 💰 BigDecimal – PRECYZYJNE LICZBY ZMIENNOPRZECINKOWE
        // ====================================================

        BigDecimal d1 = new BigDecimal("10.75");
        BigDecimal d2 = new BigDecimal("3.20");

        // PODSTAWOWE DZIAŁANIA
        BigDecimal dSum = d1.add(d2);
        BigDecimal dSub = d1.subtract(d2);
        BigDecimal dMul = d1.multiply(d2);
        BigDecimal dDiv = d1.divide(d2, 4, RoundingMode.HALF_UP); // 4 miejsca po przecinku

        System.out.println("➕ Suma: " + dSum);
        System.out.println("➖ Różnica: " + dSub);
        System.out.println("✖ Iloczyn: " + dMul);
        System.out.println("➗ Dzielenie (4 miejsca): " + dDiv);

        // ZAOKRĄGLANIE
        BigDecimal rounded = dDiv.setScale(2, RoundingMode.HALF_DOWN);
        System.out.println("Zaokrąglone do 2 miejsc: " + rounded);

        // PORÓWNANIA
        BigDecimal compare1 = new BigDecimal("2.50");
        BigDecimal compare2 = new BigDecimal("2.5000");

        System.out.println("compareTo(): " + compare1.compareTo(compare2)); // 0
        System.out.println("equals(): " + compare1.equals(compare2));       // false (różna skala)
        System.out.println("equals po stripTrailingZeros(): " + compare1.stripTrailingZeros().equals(compare2.stripTrailingZeros())); // true

        // INNE METODY
        BigDecimal vatRate = new BigDecimal("0.23");
        BigDecimal priceNet = new BigDecimal("100.00");
        BigDecimal priceGross = priceNet.multiply(BigDecimal.ONE.add(vatRate));
        System.out.println("Cena brutto z VAT 23%: " + priceGross);

        BigDecimal powResult = new BigDecimal("2.5").pow(3);
        System.out.println("2.5^3 = " + powResult);

        // OBSŁUGA WYJĄTKÓW PRZY DZIELENIU
        try {
            BigDecimal zero = BigDecimal.ZERO;
            BigDecimal error = d1.divide(zero);
        } catch (ArithmeticException e) {
            System.out.println("❌ Błąd: dzielenie przez zero! " + e.getMessage());
        }

        // ====================================================
        // 📌 KIEDY UŻYWAĆ?
        // ====================================================
        // BigInteger:
        // - obliczenia kryptograficzne
        // - liczby większe niż Long.MAX_VALUE lub mniejsze niż Long.MIN_VALUE
        // - np. generowanie dużych kluczy RSA
        //
        // BigDecimal:
        // - obliczenia finansowe, podatki, waluty
        // - uniknięcie błędów zaokrągleń znanych z float/double
        // - operacje wymagające kontroli precyzji
    }
}
