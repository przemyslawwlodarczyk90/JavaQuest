package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_02 {

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

    // 🔹 ZADANIE:
    // 1) Zaimplementuj metodę findByIdOrThrow:
    //    - szukaj usera po id,
    //    - jeśli znajdziesz → zwróć usera,
    //    - jeśli nie znajdziesz → rzuć NoSuchElementException z komunikatem "User not found".
    //
    // 2) W main:
    //    - znajdź usera o id=2 → wypisz,
    //    - znajdź usera o id=99 → ma polecieć wyjątek.
    public static User findByIdOrThrow(List<User> users, Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.id, id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
        // (nie używaj NPE – tu lepszy NoSuchElementException)
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User(1L, "Kuba"),
                new User(2L, "Dorota"),
                new User(3L, "Przemek")
        );

        System.out.println(findByIdOrThrow(users, 2L)); // 2: Dorota

        System.out.println(findByIdOrThrow(users, 99L)); // powinien polecieć wyjątek
    }
}
