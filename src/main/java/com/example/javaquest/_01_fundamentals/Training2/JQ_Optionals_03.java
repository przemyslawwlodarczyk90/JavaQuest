package com.example.javaquest._01_fundamentals.Training2;

import java.util.Optional;

public class JQ_Optionals_03 {

    static class User {
        Long id;
        String email;

        User(Long id, String email) {
            this.id = id;
            this.email = email;
        }
    }
    /*
     * ĆWICZENIE:
     * Napisz metodę getUserEmail(User user), która:
     * 1. Jeśli user.email nie jest nullem → zwróci email.
     * 2. Jeśli user.email jest nullem → zwróci napis "brak emaila".
     *
     * Użyj Optional.ofNullable() i orElse().
     */

    // metoda do ćwiczenia
    public static String getUserEmail(User user) {
        return Optional.ofNullable(user.email)
                .orElse("brak emaila");
    }

    public static void main(String[] args) {
        User u1 = new User(1L, "kuba@test.com");
        User u2 = new User(2L, null);

        System.out.println(getUserEmail(u1)); // kuba@test.com
        System.out.println(getUserEmail(u2)); // brak emaila
    }
}
