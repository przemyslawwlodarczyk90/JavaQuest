package com.example.javaquest._01_fundamentals.Lesson11_TypeCasting;

public class _Exercises_Lesson11_TypeCasting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_WideningConversion {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj int x = 100.
         * Przypisz go do: long, float i double (konwersja niejawna – widening).
         * Wypisz wszystkie cztery zmienne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_NarrowingConversion {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj double d = 9.99.
         * Rzutuj go jawnie na int: int i = (int) d.
         * Wypisz oba. Co się stało z częścią dziesiętną?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_IntToChar {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj char c = 'A'.
         * Wypisz jej kod ASCII (int) bez rzutowania.
         * Następnie zadeklaruj int code = 66 i skonwertuj go na char: (char) code.
         * Wypisz znak.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ParseStringToInt {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj String text = "42".
         * Zamień na int używając Integer.parseInt().
         * Dodaj 8 do wyniku i wypisz 50.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_ParseStringToDouble {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj String pi = "3.14159".
         * Zamień na double używając Double.parseDouble().
         * Wypisz zaokrąglony wynik (Math.round()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_IntToString {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj int number = 2025.
         * Zamień na String trzema sposobami:
         * - String.valueOf(number)
         * - Integer.toString(number)
         * - "" + number (konkatenacja)
         * Wypisz wszystkie trzy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_Autoboxing {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj int primitive = 42.
         * Autoboxuj do Integer wrapper = primitive.
         * Unboxuj z powrotem: int back = wrapper.
         * Wypisz wszystkie trzy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_LongOverflow {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj long bigValue = 10_000_000_000L.
         * Rzutuj jawnie na int: (int) bigValue.
         * Wypisz oba i wyjaśnij w komentarzu, dlaczego wynik int jest inny.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_WideningChain {
        /*
         * 🧪 Zadanie 9:
         * Zadeklaruj byte b = 100.
         * Przypisz do short, następnie do int, long, float, double – krok po kroku.
         * Wypisz każdy typ.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_BooleanNoConversion {
        /*
         * 🧪 Zadanie 10:
         * Wyjaśnij w komentarzu, że boolean w Javie NIE daje się rzutować na int ani odwrotnie.
         * Pokaż, jak sprawdzić boolean vs. 0/1 przez if:
         * boolean isActive = true;
         * int statusCode = isActive ? 1 : 0;
         * Wypisz statusCode.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_ParseMultipleTypes {
        /*
         * 🧪 Zadanie 11:
         * Sparsuj i wypisz:
         * - Long.parseLong("9876543210")
         * - Float.parseFloat("3.14")
         * - Boolean.parseBoolean("true")
         * - Byte.parseByte("127")
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_NumberFormatException {
        /*
         * 🧪 Zadanie 12:
         * Spróbuj sparsować Integer.parseInt("abc").
         * Obsłuż NumberFormatException i wypisz komunikat: "Nieprawidłowy format liczby: abc".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_Instanceof {
        /*
         * 🧪 Zadanie 13:
         * Stwórz Object obj = "Hello".
         * Sprawdź instanceof: czy to String, Integer, Object?
         * Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_SafeDowncast {
        /*
         * 🧪 Zadanie 14:
         * Stwórz Object obj = Integer.valueOf(42).
         * Sprawdź przez instanceof przed downcastem: if (obj instanceof Integer) { Integer i = (Integer) obj; ... }
         * Wypisz wartość po bezpiecznym rzutowaniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ClassCastException {
        /*
         * 🧪 Zadanie 15:
         * Stwórz Object obj = "Hello".
         * Spróbuj rzutować na Integer: (Integer) obj.
         * Obsłuż ClassCastException i wypisz komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_MixedArithmetic {
        /*
         * 🧪 Zadanie 16:
         * Oblicz: int + double, long + float, int + long.
         * Wypisz wyniki i ich typy. Wyjaśnij w komentarzu, do jakiego typu Java konwertuje wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CharArithmetic {
        /*
         * 🧪 Zadanie 17:
         * Zadeklaruj char c = 'a'.
         * Oblicz c + 1 i wypisz wynik (dostaniesz int 98, nie 'b').
         * Aby dostać 'b', użyj: (char) (c + 1).
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_WrapperAutoboxComparison {
        /*
         * 🧪 Zadanie 18:
         * Stwórz Double d1 = 3.14, double d2 = 3.14.
         * Porównaj d1 == d2 (autoboxing/unboxing przy porównaniu) i d1.equals(d2).
         * Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_StringToCharArray {
        /*
         * 🧪 Zadanie 19:
         * Zadeklaruj String word = "Hello".
         * Skonwertuj do char[] używając .toCharArray().
         * Zwiększ każdy znak o 1 (np. 'H' → 'I') i zbuduj nowy String z tablicy.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_NumberToHexAndBinary {
        /*
         * 🧪 Zadanie 20:
         * Zadeklaruj int n = 255.
         * Wypisz:
         * - Integer.toBinaryString(n) – w systemie binarnym
         * - Integer.toHexString(n) – w systemie hex
         * - Integer.toOctalString(n) – w systemie ósemkowym
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_UpcastingHierarchy {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj klasy wewnętrzne: static class Shape {}, static class Circle extends Shape {}.
         * Stwórz Shape s = new Circle() (upcasting).
         * Wypisz s.getClass().getSimpleName() – jaki typ jest w środku?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_DowncastingWithInstanceof {
        /*
         * 🧪 Zadanie 22:
         * Stwórz tablicę Shape[] shapes = {new Circle(), new Shape(), new Circle()}.
         * W pętli sprawdź instanceof i, jeśli to Circle, wywołaj metodę circle.draw() (zdefiniuj ją).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
        static class Shape { void show() { System.out.println("Shape"); } }
        static class Circle extends Shape { void draw() { System.out.println("Rysuję koło"); } }
    }

    static class Exercise23_PatternMatchingInstanceof {
        /*
         * 🧪 Zadanie 23:
         * Stwórz Object[] objects = {"Hello", 42, 3.14, true, 'Z'}.
         * W pętli sprawdź typ każdego elementu przez instanceof i wypisz go z typem.
         * Użyj instanceof String s → bez dodatkowego rzutowania (Java 16+).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_CastInMathExpression {
        /*
         * 🧪 Zadanie 24:
         * Oblicz: int a = 7, b = 2.
         * Wypisz: a / b (= 3, całkowitoliczbowe) i (double) a / b (= 3.5, zmiennoprzecinkowe).
         * Wyjaśnij różnicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_ManualBoxingUnboxing {
        /*
         * 🧪 Zadanie 25:
         * Stwórz Integer wrapped = Integer.valueOf(100).
         * Wypisz: wrapped.intValue(), wrapped.doubleValue(), wrapped.longValue().
         * Sprawdź Integer.MAX_VALUE i Integer.MIN_VALUE.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ConvertStringToAllTypes {
        /*
         * 🧪 Zadanie 26:
         * Zadeklaruj String num = "127".
         * Konwertuj i wypisz: Byte.parseByte(num), Short.parseShort(num),
         * Integer.parseInt(num), Long.parseLong(num), Float.parseFloat(num), Double.parseDouble(num).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_TruncationVsRounding {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj double d = 9.99.
         * Porównaj (int) d (obcięcie) vs Math.round(d) (zaokrąglenie).
         * Wypisz oba wyniki i wyjaśnij różnicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_NarrowingLossDemo {
        /*
         * 🧪 Zadanie 28:
         * Zadeklaruj int i = 300.
         * Rzutuj na byte: (byte) i.
         * Wypisz wynik i wyjaśnij modular arithmetic:
         * 300 % 256 = 44 (lub 300 - 256 = 44).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_PolymorphicCasting {
        /*
         * 🧪 Zadanie 29:
         * Stwórz hierarchię: Vehicle → Car → ElectricCar.
         * Stwórz Vehicle v = new ElectricCar().
         * Sprawdź instanceof na każdym poziomie (Vehicle, Car, ElectricCar).
         * Bezpiecznie rzutuj do ElectricCar i wywołaj metodę specyficzną dla tej klasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
        static class Vehicle {}
        static class Car extends Vehicle { void honk() { System.out.println("Beep!"); } }
        static class ElectricCar extends Car { void charge() { System.out.println("Ładuję..."); } }
    }

    static class Exercise30_TypeCastingChallenge {
        /*
         * 🧪 Zadanie 30:
         * Masz tablicę Object[] mixed = {1, "two", 3.0, '4', true, 5L}.
         * W pętli:
         * - jeśli Number → wypisz wartość + 100 (rzutuj na Number i użyj .doubleValue())
         * - jeśli String → wypisz długość
         * - jeśli Character → wypisz kod ASCII (rzutuj do int)
         * - jeśli Boolean → odwróć i wypisz
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
