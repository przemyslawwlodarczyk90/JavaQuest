package com.example.javaquest.variables01;

public class Lesson01_Variables {

    // ========================
    // ZMIENNE INSTANCYJNE (GLOBALNE)
    // ========================
    // Zmienna instancyjna - należy do obiektu klasy
    String globalText = "To jest zmienna globalna (instancyjna)";

    // Zmienna statyczna - współdzielona przez wszystkie obiekty klasy
    static int globalCounter = 0;

    public static void main(String[] args) {

        // ========================
        // ZMIENNE LOKALNE
        // ========================
        // Są widoczne tylko w obrębie metody/bloku, w którym zostały zadeklarowane
        int localNumber = 10;
        String localMessage = "To jest zmienna lokalna";

        // ========================
        // TYPY PRYMITYWNE
        // ========================

        // Całkowite:
        byte smallNumber = 100;              // 1 bajt, zakres: -128 do 127
        short shortNumber = 10000;           // 2 bajty
        int integerNumber = 100000;          // 4 bajty (najczęściej używany)
        long longNumber = 1000000000L;       // 8 bajtów (L na końcu oznacza long)

        // Zmiennoprzecinkowe:
        float floatNumber = 5.75f;           // 4 bajty (f na końcu)
        double doubleNumber = 19.99;         // 8 bajtów (domyślny typ dla liczb zmiennoprzecinkowych)

        // Pojedynczy znak:
        char letter = 'A';                   // Używany do przechowywania znaków Unicode

        // Logiczny:
        boolean isJavaFun = true;            // true/false

        // ========================
        // TYPY ZŁOŻONE (REFERENCYJNE)
        // ========================

        String name = "Przemek";             // Obiekt typu String (klasa wbudowana w JDK)
        int[] numbers = {1, 2, 3};           // Tablica (referencyjny typ danych)

        // ========================
        // WYŚWIETLANIE WARTOŚCI
        // ========================

        System.out.println("Zmienna lokalna: " + localMessage);
        System.out.println("Liczba całkowita: " + integerNumber);
        System.out.println("Zmienna logiczna: " + isJavaFun);
        System.out.println("Tablica: " + numbers[0]);

        // ========================
        // DOSTĘP DO ZMIENNYCH INSTANCYJNYCH
        // ========================

        Lesson01_Variables lesson = new Lesson01_Variables(); // tworzenie obiektu klasy
        lesson.showGlobalVariable(); // wywołanie metody instancyjnej

        // Dostęp do zmiennej statycznej
        System.out.println("Zmienna statyczna (globalna): " + Lesson01_Variables.globalCounter);
    }

    // Metoda instancyjna - ma dostęp do zmiennych obiektowych (nie statycznych)
    void showGlobalVariable() {
        System.out.println("Zmienna globalna (instancyjna): " + globalText);
    }
}
