package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_07 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstGmail(List<String> emails), która:
     * 1. Szuka pierwszego adresu e-mail, który kończy się na "@gmail.com".
     * 2. Jeśli znajdzie → zwraca go w oryginalnej postaci.
     * 3. Jeśli nie znajdzie → zwraca napis "brak gmaila".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), orElse().
     */

    public static String getFirstGmail(List<String> emails) {
        // TODO: zaimplementuj tutaj
        return emails.stream()
                .filter(a -> a.endsWith("@gmail.com"))
                .findFirst()
                .orElse("brak gmaila");
    }

    public static void main(String[] args) {
        List<String> emails1 = Arrays.asList("kuba@onet.pl", "dorota@gmail.com", "przemek@yahoo.com");
        List<String> emails2 = Arrays.asList("ala@wp.pl", "jan@o2.pl");

        System.out.println(getFirstGmail(emails1)); // powinno zwrócić "dorota@gmail.com"
        System.out.println(getFirstGmail(emails2)); // powinno zwrócić "brak gmaila"
    }
}
