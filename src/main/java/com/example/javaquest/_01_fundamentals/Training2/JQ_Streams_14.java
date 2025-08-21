package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_14 — "Czy wszyscy mają poprawny email?"
 *
 * ZADANIE:
 * 1) Zaimplementuj metodę, która zwraca true, jeśli KAŻDY użytkownik ma:
 *    - email niepusty (nie null, nie sam whitespace)
 *    - zawiera znak '@'
 * 2) Użyj Stream API + allMatch (bez kolektorów).
 *
 * Przykład:
 * users = [
 *   User("Kuba", "kuba@example.com"),
 *   User("Dorota", "dorota@example.com"),
 *   User("Przemek", " ")
 * ]
 * Wynik: false
 */
public class JQ_Streams_14 {

    static class User {
        String name;
        String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    public static boolean allUsersHaveValidEmail(List<User> users) {
        // TODO: użyj allMatch; pamiętaj o null-checku i trim()
        return false;
    }

    public static void main(String[] args) {
        List<User> users1 = Arrays.asList(
                new User("Kuba", "kuba@example.com"),
                new User("Dorota", "dorota@example.com")
        );
        System.out.println(allUsersHaveValidEmail(users1)); // true

        List<User> users2 = Arrays.asList(
                new User("Kuba", "kuba@example.com"),
                new User("Przemek", " ")
        );
        System.out.println(allUsersHaveValidEmail(users2)); // false
    }
}
