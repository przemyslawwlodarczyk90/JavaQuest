package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

public class JQ_Streams_10 {

    static class User {
        String name;
        String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    /**
     * ZADANIE:
     * 1) Z listy użytkowników wyciągnij same adresy email.
     * 2) Zwróć jako List<String>.
     * Podpowiedź: map + collect(toList()).
     */
    public static List<String> getEmails(List<User> users) {

        return  users.stream()
                .map(a ->a.email )
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Kuba", "kuba@example.com"),
                new User("Dorota", "dorota@example.com"),
                new User("Przemek", "przemek@example.com")
        );

        System.out.println(getEmails(users));
        // [kuba@example.com, dorota@example.com, przemek@example.com]
    }
}
