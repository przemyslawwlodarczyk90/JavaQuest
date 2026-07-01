package com.example.javaquest._01_fundamentals.Lesson01_Variables;

import java.util.Arrays;

public class _Lesson01_Variables {

    // Zmienna instancyjna - należy do obiektu klasy, nie jest statyczna
    String globalText = "To jest zmienna instancyjna (czyli należąca do obiektu)";

    // Zmienna statyczna - wspólna dla wszystkich obiektów tej klasy
    static int globalCounter = 0;

    // Zmienna stała - niezmienna wartość, dobra praktyka: UPPER_SNAKE_CASE
    static final double PI = 3.14159;

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔠 ZMIENNE LOKALNE I INSTANCYJNE
         * ============================================================
         * - Zmienna lokalna: dostępna tylko w bloku (metodzie), w której jest zadeklarowana.
         * - Zmienna instancyjna (pole): należy do obiektu, dostępna przez referencję.
         * - Zmienna statyczna: wspólna dla wszystkich obiektów klasy.
         * - Stała (final): nie można zmienić po inicjalizacji, UPPER_SNAKE_CASE.
         */

        int localNumber = 10; // tylko w tym bloku (metodzie main)
        String localMessage = "To jest zmienna lokalna";

        System.out.println("Zmienna lokalna: " + localMessage);
        System.out.println("Stała PI: " + PI);

        /*
         * ============================================================
         * 🔢 TYPY PRYMITYWNE
         * ============================================================
         * Przechowują wartość bezpośrednio (nie referencję).
         * Są szybkie i zajmują mało pamięci.
         *
         * byte   → 8 bit,  zakres: -128 do 127
         * short  → 16 bit, zakres: -32768 do 32767
         * int    → 32 bit, zakres: ~-2 mld do 2 mld
         * long   → 64 bit, bardzo duże liczby (sufiks L)
         * float  → 32 bit, liczba zmiennoprzecinkowa (sufiks f)
         * double → 64 bit, dokładniejsza liczba zmiennoprzecinkowa
         * char   → 16 bit, pojedynczy znak Unicode
         * boolean→ true lub false
         */

        byte aByte = 127;
        short aShort = 32_000;
        int anInt = 1_000_000;
        long aLong = 1_000_000_000L;
        float aFloat = 3.14f;
        double aDouble = 3.1415926535;
        char aChar = 'Z';
        boolean isAdult = true;

        System.out.println("Przykład prymitywnego boolean: " + isAdult);
        System.out.println("Przykład prymitywnego double: " + aDouble);

        /*
         * ============================================================
         * 📦 TYPY REFERENCYJNE
         * ============================================================
         * Przechowują adres (referencję) do obiektu w pamięci heap.
         * Przykłady: String, tablice, obiekty klas.
         */

        String name = "Przemek";
        int[] scores = {90, 80, 70};
        System.out.println("Imię: " + name);
        System.out.println("Pierwszy element tablicy: " + scores[0]);
        System.out.println("Cała tablica: " + Arrays.toString(scores));

        /*
         * ============================================================
         * 🎁 WRAPPER CLASSES – OBIEKTOWE ODPOWIEDNIKI TYPÓW PROSTYCH
         * ============================================================
         * Każdy prymitywny typ ma swój odpowiednik klasowy:
         *   byte    → Byte
         *   short   → Short
         *   int     → Integer
         *   long    → Long
         *   float   → Float
         *   double  → Double
         *   char    → Character
         *   boolean → Boolean
         *
         * Używane m.in. w kolekcjach (List<Integer>, Map<String, Double>).
         */

        Integer intWrapper = Integer.valueOf(anInt);
        Double doubleWrapper = Double.valueOf(aDouble);
        Boolean boolWrapper = Boolean.valueOf(isAdult);
        Character charWrapper = Character.valueOf(aChar);

        System.out.println("Integer wrapper: " + intWrapper);
        System.out.println("Double wrapper: " + doubleWrapper);
        System.out.println("Boolean wrapper: " + boolWrapper);
        System.out.println("Character wrapper: " + charWrapper);

        System.out.println("Maksymalna wartość Integer: " + Integer.MAX_VALUE);
        System.out.println("Minimalna wartość Short: " + Short.MIN_VALUE);
        System.out.println("Zamiana tekstu na int: " + Integer.parseInt("123"));
        System.out.println("Zamiana tekstu na boolean: " + Boolean.parseBoolean("true"));
        System.out.println("Zamiana int na tekst: " + Integer.toString(999));

        char znak = '7';
        System.out.println("Czy '7' to cyfra? " + Character.isDigit(znak));
        System.out.println("Czy 'a' to litera? " + Character.isLetter('a'));

        /*
         * ============================================================
         * 🔄 AUTOBOXING I UNBOXING
         * ============================================================
         * - Autoboxing: automatyczna konwersja prymitywu → wrapper (int → Integer)
         * - Unboxing:   automatyczna konwersja wrapper → prymityw (Integer → int)
         *
         * Java robi to automatycznie, ale wiąże się z narzutem wydajnościowym.
         * Uwaga: unboxing null-a rzuca NullPointerException!
         */

        Integer boxedInt = anInt;       // autoboxing: int → Integer
        int unboxedInt = boxedInt;      // unboxing: Integer → int

        Double boxedDouble = 5.5;        // autoboxing
        double unboxedDouble = boxedDouble; // unboxing

        Boolean isOk = Boolean.TRUE;
        if (isOk) {
            System.out.println("Autoboxing działa – wartość Boolean.TRUE");
        }

        /*
         * ============================================================
         * 🔵 VAR – SKRÓCONA DEKLARACJA ZMIENNEJ (od Javy 10)
         * ============================================================
         * Kompilator sam wywnioskuje typ zmiennej na podstawie wartości po prawej stronie.
         * Działa tylko dla zmiennych lokalnych – nie dla pól klas.
         */

        var city = "Otwock";
        var age = 35;
        System.out.println("Miasto: " + city + ", wiek: " + age);

        /*
         * ============================================================
         * ❌ NULL – BRAK REFERENCJI
         * ============================================================
         * - Tylko typy referencyjne mogą być null.
         * - Prymitywy nie mogą być null.
         * - Wywołanie metody na null-u → NullPointerException.
         */

        String undefined = null;
        if (undefined == null) {
            System.out.println("Zmienna 'undefined' nie wskazuje na żaden obiekt.");
        }

        /*
         * ============================================================
         * 🔍 PORÓWNYWANIE: == vs .equals()
         * ============================================================
         * - == sprawdza, czy dwie zmienne wskazują na TEN SAM obiekt (identyczność).
         * - .equals() sprawdza, czy dwie zmienne mają TĄ SAMĄ zawartość (równość).
         *
         * Dla prymitywów == porównuje wartości.
         * Dla obiektów == porównuje adresy w pamięci.
         */

        String s1 = "Hello";
        String s2 = new String("Hello");
        System.out.println("== (czy ten sam obiekt?): " + (s1 == s2));
        System.out.println(".equals (czy te same dane?): " + s1.equals(s2));

        /*
         * ============================================================
         * 🏷️ DOSTĘP DO POLA INSTANCYJNEGO I STATYCZNEGO
         * ============================================================
         */

        _Lesson01_Variables lesson = new _Lesson01_Variables();
        lesson.showGlobalVariable();
        System.out.println("Zmienna statyczna (globalCounter): " + _Lesson01_Variables.globalCounter);
    }

    void showGlobalVariable() {
        System.out.println("Zmienna instancyjna: " + globalText);
    }
}
