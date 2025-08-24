package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ćwiczenie 7:
 * Endpoint GET /userWithAge/{age}, który zwraca użytkownika po wieku.
 *
 * Zadanie:
 * - Wywołaj: http://localhost:8080/userWithAge/30
 * - Sprawdź co zwróci dla wieku, którego nie ma.
 * - Dodaj obsługę sytuacji, gdzie może być kilku użytkowników w tym samym wieku.
 */
@RestController
public class Exercise7Controller {

    record User(int id, String name, String email, int age) {}

    private final List<User> users = List.of(
            new User(1, "Alice", "alice@example.com", 25),
            new User(2, "Bob", "bob@example.com", 30),
            new User(3, "Charlie", "charlie@example.com", 35)
    );

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return users.stream()
                .filter(u -> u.id() == id)
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Nie znaleziono użytkownika o ID " + id));
    }
}

