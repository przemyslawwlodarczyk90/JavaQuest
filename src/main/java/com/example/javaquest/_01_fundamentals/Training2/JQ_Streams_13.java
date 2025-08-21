package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

public class JQ_Streams_13 {

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

    /**
     * ZADANIE:
     * 1) Pobierz z listy użytkowników wszystkie adresy email.
     * 2) Usuń duplikaty (distinct).
     * 3) Posortuj alfabetycznie.
     * 4) Zwróć List<String>.
     *
     * Przykład:
     * Wejście: [A("a@x.com"), B("b@x.com"), C("a@x.com")]
     * Wynik: ["a@x.com", "b@x.com"]
     */
    public static List<String> getUniqueEmails(List<User> users) {
        // TODO: map -> distinct -> sorted -> toList
        return users.stream()
                .map(a->a.email)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Kuba", "kuba@example.com"),
                new User("Dorota", "dorota@example.com"),
                new User("Przemek", "kuba@example.com")
        );

        System.out.println(getUniqueEmails(users));
        // [dorota@example.com, kuba@example.com]
    }
}
