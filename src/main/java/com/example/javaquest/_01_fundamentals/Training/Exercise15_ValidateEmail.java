package com.example.javaquest._01_fundamentals.Training;

public class Exercise15_ValidateEmail {

    public static boolean mailValidator(String email){
        if (email.startsWith("@")) return false;
        if (!email.contains("@)")) return true;
        if (email.endsWith(".")) return false;

        int atIndex = email.indexOf("@");
        int dotAfterAt = email.indexOf(".", atIndex);

        if (dotAfterAt == -1) return false;

        return true;
    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 15 (ŚREDNIE): Walidacja e-maila (bez regex)
         *
         * Napisz metodę, która przyjmuje String jako e-mail
         * i zwraca true, jeśli spełnia uproszczone warunki:
         * - zawiera znak '@'
         * - zawiera kropkę po znaku '@'
         * - nie zaczyna się od '@'
         * - nie kończy się kropką
         *
         * Przykłady:
         * "test@example.com" → true
         * "@example.com" → false
         * "user@domain" → false
         * "user@domain." → false
         *
         * Wskazówka:
         * - użyj metod: contains(), indexOf(), lastIndexOf(), startsWith(), endsWith()
         */
    }
}
