package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_09 {

    /*
     * ĆWICZENIE 9:
     * Utwórz endpoint GET pod adresem /api/users/search?name=Kuba
     * który wyszuka użytkownika o podanym imieniu
     * i zwróci go jako JSON.
     *
     * Jeśli użytkownika nie ma → zwróć null.
     *
     * Przykład:
     *   GET http://localhost:8080/api/users/search?name=Dorota
     *
     *   → { "id": 2, "name": "Dorota" }
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

    @GetMapping("/search")
    public User getUserByName(@RequestParam String name) {
        // TODO: zaimplementuj tutaj (np. przez stream().filter()...)
        return users.stream()
                .filter(a->a.name.equals(name))
                .findFirst()
                .orElse(null);
    }
}
