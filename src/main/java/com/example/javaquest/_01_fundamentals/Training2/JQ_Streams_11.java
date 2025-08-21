package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

public class JQ_Streams_11 {

    static class User {
        String name;
        boolean active;

        User(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        @Override
        public String toString() {
            return name + " (active=" + active + ")";
        }
    }

    /**
     * ZADANIE:
     * 1) Zwróć listę **aktywnych użytkowników** (active == true).
     * 2) Posortuj ich po nazwie alfabetycznie.
     * 3) Zwróć jako List<User>.
     */
    public static List<User> getActiveUsers(List<User> users) {
        return users.stream()
                .filter(u -> u.active) // tylko aktywni
                .sorted(Comparator.comparing(u -> u.name)) // sortuj po nazwie
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Kuba", true),
                new User("Dorota", false),
                new User("Przemek", true),
                new User("Ania", true)
        );

        System.out.println(getActiveUsers(users));
        // [Ania (active=true), Kuba (active=true), Przemek (active=true)]
    }
}
