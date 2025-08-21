package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_08 {

    /*
     * ĆWICZENIE 8:
     * Utwórz endpoint GET pod adresem /api/users/{id}
     * który wyszuka użytkownika o podanym ID w liście
     * i zwróci go jako JSON.
     *
     * Jeśli użytkownika nie ma → zwróć null albo pusty obiekt.
     *
     * Przykład:
     *   GET http://localhost:8080/api/users/1
     *
     *   → { "id": 1, "name": "Kuba" }
     */

    static class User {
        public int id;
        public String name;

        User() {}
        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "Kuba"),
            new User(2, "Dorota"),
            new User(3, "Przemek")
    ));

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        // TODO: zaimplementuj tutaj (np. przez stream().filter()...)
        return (User) users.stream()
                .filter(a->a.id==id)
                .findFirst()
                .orElse(null);
    }
}
