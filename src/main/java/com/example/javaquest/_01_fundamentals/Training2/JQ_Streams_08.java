package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_08 — "Znajdź użytkownika po mailu"
 *
 * ZADANIE:
 * Masz listę użytkowników (User).
 * 1) Znajdź użytkownika, którego email to "test@example.com".
 * 2) Zwróć Optional<User>.
 *
 * Podpowiedź: użyj filter + findFirst.
 */
public class JQ_Streams_08 {

    static class User {
        String name;
        String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        public String toString() {
            return name + " (" + email + ")";
        }
    }

    public static Optional<User> findByEmail(List<User> users, String email) {
        // TODO: filter + findFirst
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Kuba", "kuba@example.com"),
                new User("Dorota", "dorota@example.com"),
                new User("Przemek", "test@example.com")
        );

        System.out.println(findByEmail(users, "test@example.com"));
        // Optional[Przemek (test@example.com)]
    }
}
