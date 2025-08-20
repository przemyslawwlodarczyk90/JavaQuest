package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

public class JQ_Streams_09 {

    static class User {
        String name;
        String role; // np. "USER", "ADMIN"

        User(String name, String role) {
            this.name = name;
            this.role = role;
        }
    }

    /**
     * ZADANIE:
     * 1) Sprawdź, czy w liście użytkowników jest chociaż jeden ADMIN.
     * 2) Zwróć true/false.
     * Podpowiedź: anyMatch
     */
    public static boolean hasAdmin(List<User> users) {
       return users.stream()
               .anyMatch(a->a.role.equals("ADMIN"));
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Kuba", "USER"),
                new User("Dorota", "USER"),
                new User("Przemek", "ADMIN")
        );

        System.out.println(hasAdmin(users)); // true
    }
}
