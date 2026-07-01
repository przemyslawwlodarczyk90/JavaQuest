package com.example.javaquest._01_fundamentals.Lesson09_BigNumberTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class _Exercises_Lesson09_BigNumberTypes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_CreateBigInteger {
        /*
         * 🧪 Zadanie 1:
         * Stwórz BigInteger o wartości 123456789012345678901234567890.
         * Wypisz go.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_BigIntegerAdd {
        /*
         * 🧪 Zadanie 2:
         * Stwórz BigInteger a = "100000000000000000000" i b = "999999999999999999999".
         * Dodaj je i wypisz wynik (.add()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_BigIntegerSubtract {
        /*
         * 🧪 Zadanie 3:
         * Stwórz BigInteger a = "987654321098765432109876543210" i b = "123456789012345678901234567890".
         * Odejmij b od a i wypisz wynik (.subtract()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_BigIntegerMultiply {
        /*
         * 🧪 Zadanie 4:
         * Stwórz BigInteger a = "123456789" i b = "987654321".
         * Pomnóż je i wypisz wynik (.multiply()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_BigIntegerDivide {
        /*
         * 🧪 Zadanie 5:
         * Stwórz BigInteger big = "100000000000000000000" i divisor = "3".
         * Podziel big przez divisor (.divide()) i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_BigDecimalAdd {
        /*
         * 🧪 Zadanie 6:
         * Stwórz BigDecimal a = "10.75" i b = "3.20".
         * Dodaj je i wypisz wynik. Wyjaśnij w komentarzu, dlaczego używamy BigDecimal zamiast double.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_BigDecimalSubtract {
        /*
         * 🧪 Zadanie 7:
         * Stwórz BigDecimal price = "199.99" i discount = "25.50".
         * Odejmij rabat od ceny i wypisz wynik (.subtract()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_BigDecimalMultiply {
        /*
         * 🧪 Zadanie 8:
         * Stwórz BigDecimal price = "49.99" i quantity = "12".
         * Oblicz łączną cenę (.multiply()) i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_BigDecimalDivide {
        /*
         * 🧪 Zadanie 9:
         * Stwórz BigDecimal a = "10.75" i b = "3.20".
         * Podziel a przez b z 4 miejscami po przecinku i RoundingMode.HALF_UP.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_BigDecimalSetScale {
        /*
         * 🧪 Zadanie 10:
         * Stwórz BigDecimal value = "3.14159265358979".
         * Zaokrąglij do 2 miejsc po przecinku używając .setScale(2, RoundingMode.HALF_UP).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_BigIntegerModulo {
        /*
         * 🧪 Zadanie 11:
         * Stwórz BigInteger n = "987654321098765432109876543210".
         * Oblicz n mod 1000 (.mod()) i wypisz ostatnie 3 cyfry tej liczby.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_BigIntegerPower {
        /*
         * 🧪 Zadanie 12:
         * Oblicz 2 do potęgi 100 używając BigInteger.TWO.pow(100) lub new BigInteger("2").pow(100).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_BigIntegerGcd {
        /*
         * 🧪 Zadanie 13:
         * Stwórz BigInteger a = "123456789" i b = "987654321".
         * Oblicz ich NWD (Greatest Common Divisor) używając .gcd().
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_BigIntegerCompareTo {
        /*
         * 🧪 Zadanie 14:
         * Stwórz BigInteger big1 = "100000000000000000000" i big2 = "99999999999999999999".
         * Porównaj je przez .compareTo() i wypisz która jest większa.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_BigDecimalCompare {
        /*
         * 🧪 Zadanie 15:
         * Stwórz BigDecimal a = "2.50" i b = "2.5000".
         * Porównaj przez .equals() – co zwraca? (false – różna skala)
         * Porównaj przez .compareTo() – co zwraca? (0 – ta sama wartość)
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_BigDecimalVATCalculation {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj BigDecimal netPrice = "1250.00" i vatRate = "0.23" (23% VAT).
         * Oblicz cenę brutto: netPrice * (1 + vatRate).
         * Użyj BigDecimal.ONE.add(vatRate) i .multiply().
         * Wypisz wynik z 2 miejscami po przecinku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_BigIntegerToConversions {
        /*
         * 🧪 Zadanie 17:
         * Stwórz BigInteger big = "999999999".
         * Wypisz konwersję do: int (.intValue()), long (.longValue()), double (.doubleValue()).
         * Sprawdź też BigInteger big2 = "9999999999999999999999" i skonwertuj do int – co się stanie?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_BigDecimalStripTrailingZeros {
        /*
         * 🧪 Zadanie 18:
         * Stwórz BigDecimal a = "2.50000".
         * Wypisz .stripTrailingZeros().toPlainString() – powinno dać "2.5".
         * Sprawdź też "100.00" → "100".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_BigDecimalRoundingModes {
        /*
         * 🧪 Zadanie 19:
         * Stwórz BigDecimal value = "2.567".
         * Zaokrąglij do 2 miejsc po przecinku używając różnych RoundingMode:
         * - HALF_UP → 2.57
         * - HALF_DOWN → 2.57
         * - FLOOR → 2.56
         * - CEILING → 2.57
         * Wypisz wszystkie wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_BigIntegerIsPrime {
        /*
         * 🧪 Zadanie 20:
         * Stwórz BigInteger candidate = "999999999999999989" (dużą liczbę).
         * Sprawdź, czy jest prawdopodobnie pierwsza: .isProbablePrime(50).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_CurrencyConversion {
        /*
         * 🧪 Zadanie 21:
         * Masz BigDecimal amount = "1500.00" (PLN) i kurs = "4.2345" (PLN/EUR).
         * Przelicz na EUR dzieląc amount przez kurs z dokładnością 4 miejsc i HALF_UP.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_Factorial {
        /*
         * 🧪 Zadanie 22:
         * Oblicz silnię 50! używając BigInteger.
         * Zacznij od BigInteger.ONE i mnóż przez każdą liczbę od 2 do 50 w pętli.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_BigDecimalInterestCalculation {
        /*
         * 🧪 Zadanie 23:
         * Oblicz odsetki proste:
         * Kwota = "10000.00", oprocentowanie = "0.05" (5% rocznie), lata = "3".
         * Wzór: odsetki = kwota * oprocentowanie * lata.
         * Wypisz odsetki i sumę końcową (kwota + odsetki).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_SumLargeSeries {
        /*
         * 🧪 Zadanie 24:
         * Oblicz sumę liczb od 1 do 1000000 używając BigInteger.
         * Użyj pętli for i .add(). Wypisz wynik i porównaj z wzorem n*(n+1)/2.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_BigDecimalDivisionPrecision {
        /*
         * 🧪 Zadanie 25:
         * Podziel BigDecimal a = "1.0" przez b = "3.0" z różnymi skalami: 5, 10, 20 miejsc (HALF_UP).
         * Wypisz wyniki.
         * Następnie spróbuj podzielić bez scale – co się stanie? Obsłuż ArithmeticException.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_FibonacciBig {
        /*
         * 🧪 Zadanie 26:
         * Oblicz 100. liczbę ciągu Fibonacciego używając BigInteger.
         * Użyj pętli i dwóch zmiennych (prev, curr). Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_BigIntegerBitLength {
        /*
         * 🧪 Zadanie 27:
         * Stwórz BigInteger n = "1024" i oblicz jego długość bitową (.bitLength()).
         * Wypisz wynik (powinno być 11 – bo 2^10 = 1024 = 0100...0 = 11 bitów).
         * Sprawdź też .bitCount() – ile bitów jest ustawionych na 1.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_BigDecimalComparatorSort {
        /*
         * 🧪 Zadanie 28:
         * Masz listę cen: "19.99", "5.49", "99.00", "12.50", "3.99".
         * Stwórz listę BigDecimal i posortuj ją używając .compareTo().
         * Wypisz posortowane ceny.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_HighPrecisionPi {
        /*
         * 🧪 Zadanie 29:
         * Oblicz wartość π z 20 miejscami po przecinku używając szeregu Leibniza:
         * π/4 = 1 - 1/3 + 1/5 - 1/7 + ...
         * Użyj BigDecimal z odpowiednią skalą.
         * (Wskazówka: użyj 1000+ wyrazów szeregu dla dokładności)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MortgageCalculator {
        /*
         * 🧪 Zadanie 30:
         * Oblicz miesięczną ratę kredytu hipotecznego:
         * kwota = "500000.00" PLN, oprocentowanie roczne = "6.5%" = "0.065"/12 miesięczne,
         * liczba miesięcy = 360 (30 lat).
         * Wzór: rata = P * (r * (1+r)^n) / ((1+r)^n - 1)
         * Użyj BigDecimal i Math.pow() (lub BigDecimal.pow()). Wypisz miesięczną ratę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
