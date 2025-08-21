package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_05 {

    /*
     * ĆWICZENIE 5:
     * Utwórz endpoint GET pod adresem /api/users
     * który zwróci listę użytkowników w formacie JSON.
     *
     * Przykład:
     *   GET http://localhost:8080/api/users
     *
     *   → [
     *        { "id": 1, "name": "Kuba" },
     *        { "id": 2, "name": "Dorota" },
     *        { "id": 3, "name": "Przemek" }
     *      ]
     *
     * Podpowiedź: metoda powinna zwracać List<User>,
     * a Spring Boot automatycznie zamieni ją na JSON.
     */

    static class User {
        public int id;
        public String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        // TODO: zaimplementuj tutaj
        return null;
    }
}
