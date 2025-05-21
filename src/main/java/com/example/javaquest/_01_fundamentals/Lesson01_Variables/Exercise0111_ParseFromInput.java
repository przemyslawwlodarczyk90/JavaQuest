package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class Exercise0111_ParseFromInput {
    public static void main(String[] args) {
        // 🧮 String z wartością liczbową
        String liczba = "42";
        String przecinek = "42.5";
        String logiczne = "true";
        String blad = "abc";

        try {
            int x = Integer.parseInt(liczba);
            System.out.println("int: " + x);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny format dla int: " + liczba);
        }

        try {
            double y = Double.parseDouble(przecinek);
            System.out.println("double: " + y);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny format dla double: " + przecinek);
        }

        boolean z = Boolean.parseBoolean(logiczne); // Nie rzuca wyjątku!
        System.out.println("boolean: " + z);

        try {
            int niepoprawna = Integer.parseInt(blad);
            System.out.println("int: " + niepoprawna);
        } catch (NumberFormatException e) {
            System.out.println("❌ Błąd: \"" + blad + "\" nie da się przekonwertować na int.");
        }
    }
}
