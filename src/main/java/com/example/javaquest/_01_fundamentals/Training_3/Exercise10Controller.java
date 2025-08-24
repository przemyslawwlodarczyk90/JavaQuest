package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Ćwiczenie 10:
 * Endpoint POST /users, który dodaje nowego użytkownika.
 *
 * Zadanie:
 * - Wyślij żądanie POST do http://localhost:8080/users
 *   z JSON-em w body, np.:
 *
 *   {
 *     "id": 4,
 *     "name": "Diana",
 *     "email": "diana@example.com",
 *     "age": 28
 *   }
 *
 * - Powinno dodać użytkownika do listy i zwrócić 201 Created.
 * - Spróbuj wysłać kilku różnych użytkowników.
 * - (Opcjonalnie) sprawdź, co się stanie, jeśli wyślesz tego samego `id` dwa razy.
 */
@RestController
public class Exercise10Controller {

    record User(int id, String name, String email, int age) {}

    private final List<User> users = new ArrayList<>(List.of(
            new User(1, "Alice", "alice@example.com", 25),
            new User(2, "Bob", "bob@example.com", 30),
            new User(3, "Charlie", "charlie@example.com", 35)
    ));

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        users.add(user);
        return ResponseEntity.status(201).body(user);
    }

    // pomocniczy GET żeby sprawdzić czy użytkownicy się zapisują
    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }
}
