package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise019_CheckDefaultPrimitiveValues {

    static int i;
    static boolean flag;
    static char letter;

    public static void main(String[] args) {
        // 📋 Wypisz domyślne wartości pól prymitywnych w klasie (bez przypisywania).
        // Pokaż różnicę między polem a zmienną lokalną.

        System.out.println("Domyślna wartość int: " + i);           // 0
        System.out.println("Domyślna wartość boolean: " + flag);     // false
        System.out.println("Domyślna wartość char: [" + letter + "]"); // \u0000 (niewidoczny)

        // 🧪 Próba użycia zmiennej lokalnej bez inicjalizacji — to się nie skompiluje:
        /*
        int localInt;
        System.out.println("Local int: " + localInt); // ❌ błąd kompilacji
        */
    }
}
