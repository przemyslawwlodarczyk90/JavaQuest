package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_01 {

    static class User {
        Long id;
        String name;

        User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return id + ": " + name;
        }
    }

    /**
     * ZADANIE:
     * 1) Zaimplementuj metodę findById, która:
     *    - przeszukuje listę użytkowników,
     *    - jeśli znajdzie użytkownika o podanym id → zwróć Optional<User>,
     *    - jeśli nie znajdzie → Optional.empty().
     *
     * 2) W main:
     *    - spróbuj znaleźć usera o id=2 i wydrukuj go używając ifPresent,
     *    - spróbuj znaleźć usera o id=99 i wypisz "Nie znaleziono".
     */
    public static Optional<User> findById(List<User> users, Long id) {
        // TODO: zaimplementuj stream + filter + findFirst

        Optional<User> first = users.stream()
                .filter(a -> a.id.equals(id))
                .findFirst();
        return first;
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User(1L, "Kuba"),
                new User(2L, "Dorota"),
                new User(3L, "Przemek")
        );

        Optional<User> found = findById(users, 2L);
        found.ifPresent(u -> System.out.println("Znaleziono: " + u));

        Optional<User> notFound = findById(users, 99L);
        System.out.println(notFound.orElse(new User(0L, "Nie znaleziono")));
    }
}
