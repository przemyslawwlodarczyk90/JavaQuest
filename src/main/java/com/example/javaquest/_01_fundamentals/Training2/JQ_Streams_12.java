package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

public class JQ_Streams_12 {

    static class User {
        String name;

        User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * ZADANIE:
     * 1) Zaimplementuj prostą paginację:
     *    - parametry: page (numer strony, od 0), size (ile elementów na stronę),
     *    - użyj skip + limit,
     *    - jeśli strona wykracza poza dane → zwróć pustą listę.
     *
     * Przykład:
     * Wejście: [A, B, C, D, E], page=1, size=2
     * Wynik: [C, D]
     */
    public static List<User> getPage(List<User> users, int page, int size) {
        // TODO: skip + limit
        return users.stream()
                .skip((long)page*size)
                .limit(size)
                .toList();
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("A"), new User("B"), new User("C"),
                new User("D"), new User("E")
        );

        System.out.println(getPage(users, 0, 2)); // [A, B]
        System.out.println(getPage(users, 1, 2)); // [C, D]
        System.out.println(getPage(users, 2, 2)); // [E]
        System.out.println(getPage(users, 3, 2)); // []
    }
}
